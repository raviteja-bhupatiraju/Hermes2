/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.terminology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * A terminology available offline: all its concepts are loaded from an index shipped with the application
 * ({@code terminology/<file>.tsv.gz} in this bundle, built by {@code terminology-src/build_terminologies.py}).
 * Loading is lazy and happens once; afterwards searches take a few milliseconds and need no network.
 */
public final class Terminology {

	private static final Pattern SPLIT = Pattern.compile("[^\\p{L}\\p{N}]+");
	private static final Pattern DIACRITICS = Pattern.compile("\\p{M}+");

	private final String id;
	private final String title;
	private final String resource;
	private final List<String> aliases;
	private volatile Data data;
	private Map<String, String> header;

	/** The loaded index. */
	private record Data(Map<String, String> header, List<Concept> concepts, Map<String, Concept> byCode,
			Map<String, List<Concept>> children, String[][] searchText) {
	}

	/** A search result: the concept and the matched name (the label or a synonym). */
	public record Match(Concept concept, String matchedName) {
	}

	/**
	 * A terminology searched online instead of from an index (licensed terminologies such as SNOMED CT, with the
	 * user's own license): it has no hierarchy to browse, and only concepts found by searches are known offline.
	 */
	public interface Online {

		/** Whether the service can be used (for example, a license key is set). */
		boolean isAvailable();

		/** Searches concepts by words or code. */
		List<Concept> search(String text, int limit) throws IOException;

		/** The publisher and service. */
		String source();

		/** The license notice. */
		String license();
	}

	private static final Data EMPTY = new Data(Map.of(), List.of(), Map.of(), Map.of(), new String[0][]);

	private final Online online;
	/** The concepts found online, by code. */
	private final Map<String, Concept> found = new java.util.concurrent.ConcurrentHashMap<>();

	Terminology(String id, String title, String resource, List<String> aliases) {
		this(id, title, resource, aliases, null);
	}

	Terminology(String id, String title, List<String> aliases, Online online) {
		this(id, title, null, aliases, online);
	}

	private Terminology(String id, String title, String resource, List<String> aliases, Online online) {
		this.id = id;
		this.title = title;
		this.resource = resource;
		this.aliases = List.copyOf(aliases);
		this.online = online;
	}

	/** Whether the terminology is searched online (it has no offline index). */
	public boolean isOnline() {
		return online != null;
	}

	/** Whether the terminology can be used: offline ones always, online ones when their service is set up. */
	public boolean isAvailable() {
		return online == null || online.isAvailable();
	}

	/** The identifier, also used as keyword source, e.g. {@code MeSH}, {@code ICD-10-CM}. */
	public String getId() {
		return id;
	}

	/** Whether a source value (keyword source, taxon path source) names this terminology. */
	public boolean isNamedBy(String source) {
		if (source == null || source.isBlank()) {
			return false;
		}
		String normalized = normalize(source);
		if (normalized.equals(normalize(id)) || normalized.equals(normalize(getTitle()))) {
			return true;
		}
		return aliases.stream().anyMatch(alias -> normalize(alias).equals(normalized));
	}

	/** The full name, e.g. "Medical Subject Headings" (known without loading the index). */
	public String getTitle() {
		return title;
	}

	/** The version, e.g. "2026" ("online" for online terminologies). */
	public String getVersion() {
		return online != null ? "online" : header("version", "");
	}

	/** The publisher and URL. */
	public String getSource() {
		return online != null ? online.source() : header("source", "");
	}

	/** The license or attribution notice. */
	public String getLicense() {
		return online != null ? online.license() : header("license", "");
	}

	private String header(String key, String fallback) {
		Data loaded = data;
		String value = (loaded != null ? loaded.header() : header()).get(key);
		return value == null || value.isBlank() ? fallback : value;
	}

	/** The header of the index (version, source, license), read without loading the concepts. */
	private synchronized Map<String, String> header() {
		if (online != null) {
			return Map.of();
		}
		if (header == null) {
			Map<String, String> result = new LinkedHashMap<>();
			try (InputStream stream = Terminology.class.getResourceAsStream("/terminology/" + resource)) {
				if (stream != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(new GZIPInputStream(stream), StandardCharsets.UTF_8));
					String line;
					while ((line = reader.readLine()) != null && line.startsWith("#")) {
						int tab = line.indexOf('\t');
						if (tab > 0) {
							result.put(line.substring(1, tab), line.substring(tab + 1));
						}
					}
				}
			} catch (IOException _) {
				// No header: the fallbacks are used.
			}
			header = result;
		}
		return header;
	}

	/** Whether the index is loaded (searching loads it). */
	public boolean isLoaded() {
		return data != null;
	}

	/** Loads the index now (e.g. in a background job), so that the first search is fast. */
	public void load() {
		data();
	}

	/** The number of concepts. */
	public int size() {
		return data().concepts().size();
	}

	/** All concepts, in the order of the index. */
	public List<Concept> concepts() {
		return data().concepts();
	}

	/** The concept with a code (case-insensitive), or {@code null} (online: only concepts found by searches). */
	public Concept get(String code) {
		if (code == null) {
			return null;
		}
		if (online != null) {
			return found.get(code.strip());
		}
		Map<String, Concept> byCode = data().byCode();
		String key = code.strip();
		Concept concept = byCode.get(key);
		return concept != null ? concept : byCode.get(key.toUpperCase(Locale.ROOT));
	}

	/** The concepts without broader concept (chapters, groups, top levels). */
	public List<Concept> roots() {
		return data().children().getOrDefault("", List.of());
	}

	/** The narrower concepts of a concept. */
	public List<Concept> children(String code) {
		return data().children().getOrDefault(code, List.of());
	}

	/**
	 * The path from a top-level concept down to a concept (following the first parent), including the concept.
	 */
	public List<Concept> path(Concept concept) {
		List<Concept> path = new ArrayList<>();
		Concept current = concept;
		while (current != null && path.size() < 32 && !path.contains(current)) {
			path.add(0, current);
			current = current.parents().isEmpty() ? null : get(current.parents().getFirst());
		}
		return path;
	}

	/**
	 * Searches concepts by code, name and synonyms. Every word of the text must start a word of the matched name;
	 * results are ranked: exact code, exact name, name starting with the text, whole-word matches, prefixes;
	 * preferred names before synonyms and shorter names before longer ones.
	 */
	public List<Match> search(String text, int limit) {
		String query = normalize(text == null ? "" : text);
		if (query.isEmpty() || limit <= 0) {
			return List.of();
		}
		if (online != null) {
			try {
				List<Match> matches = new ArrayList<>();
				for (Concept concept : online.search(text.strip(), limit)) {
					found.put(concept.code(), concept);
					matches.add(new Match(concept, concept.label()));
				}
				return matches;
			} catch (IOException e) {
				throw new UncheckedIOException(e.getMessage(), e);
			}
		}
		Data loaded = data();
		List<String> tokens = tokens(query);
		if (tokens.isEmpty()) {
			return List.of();
		}
		record Scored(Concept concept, String name, int rank) {
		}
		List<Scored> scored = new ArrayList<>();
		Concept byCode = get(text.strip());
		if (byCode != null) {
			scored.add(new Scored(byCode, byCode.label(), -1));
		}
		List<Concept> concepts = loaded.concepts();
		for (int i = 0; i < concepts.size(); i++) {
			Concept concept = concepts.get(i);
			if (concept == byCode) {
				continue;
			}
			String[] names = loaded.searchText()[i];
			int best = Integer.MAX_VALUE;
			int bestName = -1;
			for (int n = 0; n < names.length; n++) {
				int rank = rank(names[n], query, tokens);
				if (rank >= 0) {
					rank = rank * 2 + (n == 0 ? 0 : 1);
					if (rank < best) {
						best = rank;
						bestName = n;
					}
				}
			}
			if (bestName >= 0) {
				String name = bestName == 0 ? concept.label() : concept.synonyms().get(bestName - 1);
				scored.add(new Scored(concept, name, best));
			}
		}
		scored.sort(Comparator.comparingInt(Scored::rank).thenComparingInt(s -> s.name().length())
				.thenComparing(s -> s.concept().label(), String.CASE_INSENSITIVE_ORDER));
		List<Match> result = new ArrayList<>();
		for (Scored s : scored.subList(0, Math.min(limit, scored.size()))) {
			result.add(new Match(s.concept(), s.name()));
		}
		return result;
	}

	/**
	 * 0 = equal; 1 = starts with the text as whole word(s); 2 = contains it as whole word(s); 3 = starts with it;
	 * 4 = a word starts with it; 5 = every typed word starts a word; -1 = no match.
	 */
	private static int rank(String name, String query, List<String> tokens) {
		if (name.equals(query)) {
			return 0;
		}
		int at = name.indexOf(query);
		if (at >= 0) {
			boolean startWord = at == 0 || !Character.isLetterOrDigit(name.charAt(at - 1));
			int end = at + query.length();
			boolean endWord = end == name.length() || !Character.isLetterOrDigit(name.charAt(end));
			if (at == 0 && endWord) {
				return 1;
			}
			if (startWord && endWord) {
				return 2;
			}
			if (at == 0) {
				return 3;
			}
			if (startWord) {
				return 4;
			}
		}
		return allTokensStartWords(name, tokens) ? 5 : -1;
	}

	private static boolean allTokensStartWords(String name, List<String> tokens) {
		for (String token : tokens) {
			int from = 0;
			boolean found = false;
			while (true) {
				int at = name.indexOf(token, from);
				if (at < 0) {
					break;
				}
				if (at == 0 || !Character.isLetterOrDigit(name.charAt(at - 1))) {
					found = true;
					break;
				}
				from = at + 1;
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	private static List<String> tokens(String text) {
		List<String> tokens = new ArrayList<>();
		for (String token : SPLIT.split(text)) {
			if (!token.isEmpty()) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	/** Lower case without diacritics, white space collapsed. */
	static String normalize(String text) {
		String decomposed = Normalizer.normalize(text, Normalizer.Form.NFD);
		return DIACRITICS.matcher(decomposed).replaceAll("").toLowerCase(Locale.ROOT).strip().replaceAll("\\s+", " ");
	}

	// ----------------------------------------------------------------------------------------------------
	// Loading
	// ----------------------------------------------------------------------------------------------------

	private Data data() {
		if (online != null) {
			return EMPTY;
		}
		Data loaded = data;
		if (loaded == null) {
			synchronized (this) {
				loaded = data;
				if (loaded == null) {
					try {
						data = loaded = read();
					} catch (IOException e) {
						throw new UncheckedIOException("The terminology " + id + " could not be loaded", e);
					}
				}
			}
		}
		return loaded;
	}

	private Data read() throws IOException {
		InputStream stream = Terminology.class.getResourceAsStream("/terminology/" + resource);
		if (stream == null) {
			throw new IOException("Missing terminology index " + resource);
		}
		Map<String, String> header = new LinkedHashMap<>();
		List<Concept> concepts = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new GZIPInputStream(stream, 1 << 16), StandardCharsets.UTF_8), 1 << 16)) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					int tab = line.indexOf('\t');
					if (tab > 0) {
						header.put(line.substring(1, tab), line.substring(tab + 1));
					}
					continue;
				}
				String[] f = line.split("\t", -1);
				if (f.length < 8 || f[0].isEmpty()) {
					continue;
				}
				concepts.add(new Concept(f[0], f[1], list(f[2]), list(f[3]), list(f[4]), f[5], list(f[6]), f[7]));
			}
		}
		Map<String, Concept> byCode = new HashMap<>(concepts.size() * 2);
		for (Concept concept : concepts) {
			byCode.putIfAbsent(concept.code(), concept);
			byCode.putIfAbsent(concept.code().toUpperCase(Locale.ROOT), concept);
		}
		Map<String, List<Concept>> children = new HashMap<>();
		for (Concept concept : concepts) {
			if (concept.parents().isEmpty()) {
				children.computeIfAbsent("", _ -> new ArrayList<>()).add(concept);
			}
			for (String parent : concept.parents()) {
				children.computeIfAbsent(parent, _ -> new ArrayList<>()).add(concept);
			}
		}
		children.replaceAll((_, v) -> Collections.unmodifiableList(v));
		String[][] searchText = new String[concepts.size()][];
		for (int i = 0; i < concepts.size(); i++) {
			Concept concept = concepts.get(i);
			String[] names = new String[concept.synonyms().size() + 1];
			names[0] = normalize(concept.label());
			for (int s = 0; s < concept.synonyms().size(); s++) {
				names[s + 1] = normalize(concept.synonyms().get(s));
			}
			searchText[i] = names;
		}
		return new Data(header, Collections.unmodifiableList(concepts), byCode, children, searchText);
	}

	private static List<String> list(String field) {
		return field.isEmpty() ? List.of() : Arrays.asList(field.split("\\|"));
	}

	@Override
	public String toString() {
		return id;
	}
}
