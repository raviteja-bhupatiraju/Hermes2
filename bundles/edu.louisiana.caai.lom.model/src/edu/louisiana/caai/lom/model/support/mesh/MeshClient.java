/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Looks up Medical Subject Headings (MeSH) descriptors. The shared client answers from the offline MeSH index
 * shipped with the application ({@link Terminologies#MESH}: all descriptors with entry terms, scope notes, tree
 * numbers and see-also references), so no network is needed; the NLM service is only asked for descriptors the
 * index doesn't know (e.g. ones introduced after its release) when online lookup is enabled.
 * <p>
 * Online, the client uses the NLM MeSH RDF Lookup service ({@code https://id.nlm.nih.gov/mesh/lookup/}).
 * <p>
 * Searches use {@code /lookup/descriptor} (ranked so that exact and prefix matches come first) and fall back
 * to word-prefix matching for partially typed phrases such as {@code "myocardial inf"}; a descriptor ID typed
 * as search text ({@code D009203}) is resolved with {@code /lookup/label}. Details combine
 * {@code /lookup/details} (entry terms, see-also references), the descriptor's JSON-LD (label, tree numbers)
 * and its preferred concept's JSON-LD (scope note).
 * <p>
 * Successful responses are kept in a small in-memory LRU cache. In offline mode only cached responses are
 * used. The client is thread-safe.
 */
public final class MeshClient {

	/** The default service base URL. */
	public static final String DEFAULT_BASE_URL = "https://id.nlm.nih.gov/mesh";
	/** The User-Agent sent with each request. */
	public static final String USER_AGENT = "HealthcareLOMEditor/1.0";
	/** The default connect and request timeout. */
	public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);
	/** The largest number of results the lookup service returns per request. */
	public static final int MAX_LIMIT = 50;
	/** The source value of MeSH keywords in HLOM ({@code hx:source="MeSH"}). */
	public static final String SOURCE = "MeSH";

	private static final Pattern DESCRIPTOR_ID = Pattern.compile("D\\d{6}(\\d{3})?");
	private static final Pattern SPLIT = Pattern.compile("[^\\p{L}\\p{N}]+");
	private static final int CACHE_SIZE = 256;

	private static volatile MeshClient shared;

	private final MeshTransport transport;
	private final String baseUrl;
	/** The offline index, or {@code null} for an online-only client. */
	private final Terminology index;
	private final Map<String, String> cache = new LinkedHashMap<>(64, 0.75f, true) {
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
			return size() > CACHE_SIZE;
		}
	};
	private volatile boolean offline;

	/** Creates a client answering from the offline index, with the NLM service as fallback. */
	public MeshClient() {
		this(Terminologies.MESH, new HttpMeshTransport(DEFAULT_TIMEOUT, USER_AGENT), DEFAULT_BASE_URL);
	}

	/**
	 * Creates a client with a custom transport and base URL (e.g. a mirror; {@code /lookup/...} and
	 * {@code /<id>.json} are resolved against it).
	 */
	public MeshClient(MeshTransport transport, String baseUrl) {
		this(null, transport, baseUrl);
	}

	/**
	 * Creates a client answering from an offline index (may be {@code null}) and asking the service for
	 * descriptors the index doesn't know.
	 */
	public MeshClient(Terminology index, MeshTransport transport, String baseUrl) {
		this.index = index;
		this.transport = Objects.requireNonNull(transport, "transport");
		String base = Objects.requireNonNull(baseUrl, "baseUrl").trim();
		this.baseUrl = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
	}

	/**
	 * Configures the shared client: a new client is created when the base URL changes; a disabled lookup makes
	 * the client answer only from its cache.
	 */
	public static synchronized void configureShared(String baseUrl, boolean enabled) {
		MeshClient client = shared;
		if (baseUrl != null && !baseUrl.isBlank() && (client == null
				|| !client.getBaseUrl().equals(baseUrl.trim().replaceAll("/+$", "")))) {
			client = new MeshClient(Terminologies.MESH, new HttpMeshTransport(DEFAULT_TIMEOUT, USER_AGENT), baseUrl);
			shared = client;
		}
		shared().setOffline(!enabled);
	}

	/** A shared client for the application, so that all dialogs share one cache. */
	public static MeshClient shared() {
		MeshClient client = shared;
		if (client == null) {
			synchronized (MeshClient.class) {
				client = shared;
				if (client == null) {
					shared = client = new MeshClient();
				}
			}
		}
		return client;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	/** Whether the client has the offline MeSH index (lookups then work without network). */
	public boolean hasOfflineIndex() {
		return index != null;
	}

	/** Whether the client doesn't use the network (it answers from the offline index and its cache). */
	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	/** Forgets all cached responses. */
	public void clearCache() {
		synchronized (cache) {
			cache.clear();
		}
	}

	/** Whether a string is a MeSH descriptor unique ID ({@code D} followed by 6 or 9 digits). */
	public static boolean isDescriptorId(String id) {
		return id != null && DESCRIPTOR_ID.matcher(id).matches();
	}

	/** The last segment of a MeSH resource URI ({@code http://id.nlm.nih.gov/mesh/D009203 -> D009203}). */
	public static String localName(String uri) {
		if (uri == null) {
			return null;
		}
		int slash = uri.lastIndexOf('/');
		return slash >= 0 ? uri.substring(slash + 1) : uri;
	}

	// ----------------------------------------------------------------------------------------------------
	// Search
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Searches descriptors whose label contains the text, best matches first.
	 *
	 * @param text  the search text, or a descriptor ID such as {@code D009203}
	 * @param limit the maximum number of results (at most {@link #MAX_LIMIT})
	 * @return the matching descriptors; empty for blank text
	 * @throws MeshException if the service can't be reached or answers with an error
	 */
	public List<MeshTerm> search(String text, int limit) throws MeshException {
		String query = text == null ? "" : text.strip().replaceAll("\\s+", " ");
		int max = Math.clamp(limit, 1, MAX_LIMIT);
		if (query.isEmpty()) {
			return List.of();
		}
		String upper = query.toUpperCase(Locale.ROOT);
		if (index != null) {
			List<MeshTerm> offlineResults = new ArrayList<>();
			for (Terminology.Match match : index.search(query, max)) {
				offlineResults.add(new MeshTerm(match.concept().code(), match.concept().label()));
			}
			// The index holds every descriptor; the service is only asked for an unknown descriptor ID.
			if (!offlineResults.isEmpty() || !isDescriptorId(upper) || offline) {
				return List.copyOf(offlineResults);
			}
		}
		if (isDescriptorId(upper)) {
			MeshTerm term = lookupId(upper);
			return term == null ? List.of() : List.of(term);
		}
		// The service ignores case; lower case requests share cache entries.
		List<MeshTerm> results = searchLabels(query.toLowerCase(Locale.ROOT), "contains");
		List<String> tokens = tokens(query);
		if (results.isEmpty() && !tokens.isEmpty()) {
			// The service matches phrases, so "myocardial inf" finds nothing; search for the longest word
			// and keep the labels in which every typed word starts a word.
			String longest = tokens.stream().max(Comparator.comparingInt(String::length)).orElseThrow();
			if (tokens.size() > 1 || !longest.equals(query.toLowerCase(Locale.ROOT))) {
				results = new ArrayList<>(searchLabels(longest, "contains"));
				results.removeIf(term -> !matchesAllPrefixes(term.label(), tokens));
			}
		}
		List<MeshTerm> ranked = new ArrayList<>(results);
		String lower = query.toLowerCase(Locale.ROOT);
		ranked.sort(Comparator.<MeshTerm>comparingInt(term -> rank(term.label(), lower, tokens))
				.thenComparingInt(term -> term.label().length())
				.thenComparing(MeshTerm::label, String.CASE_INSENSITIVE_ORDER));
		return List.copyOf(ranked.subList(0, Math.min(max, ranked.size())));
	}

	private List<MeshTerm> searchLabels(String label, String match) throws MeshException {
		Object json = getJson("/lookup/descriptor?label=" + encode(label) + "&match=" + match + "&limit=" + MAX_LIMIT);
		if (!(json instanceof List<?> list)) {
			throw invalid("descriptor search", null);
		}
		Set<MeshTerm> terms = new LinkedHashSet<>();
		for (Object item : list) {
			String id = localName(Json.string(item, "resource"));
			String itemLabel = Json.string(item, "label");
			if (isDescriptorId(id) && itemLabel != null && !itemLabel.isBlank()) {
				terms.add(new MeshTerm(id, itemLabel));
			}
		}
		return new ArrayList<>(terms);
	}

	private MeshTerm lookupId(String id) throws MeshException {
		Object json = getJson("/lookup/label?resource=" + id);
		String label = Json.string(json);
		return label == null || label.isBlank() ? null : new MeshTerm(id, label);
	}

	private static List<String> tokens(String text) {
		List<String> tokens = new ArrayList<>();
		for (String token : SPLIT.split(text.toLowerCase(Locale.ROOT))) {
			if (!token.isEmpty()) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	private static boolean matchesAllPrefixes(String label, List<String> tokens) {
		List<String> words = tokens(label);
		return tokens.stream().allMatch(token -> words.stream().anyMatch(word -> word.startsWith(token)));
	}

	/**
	 * 0 = exact; 1 = starts with the text as whole word(s); 2 = contains it as whole word(s); 3 = starts with
	 * it; 4 = a word starts with it; 5 = every typed word starts a word; 6 = other.
	 */
	private static int rank(String label, String lowerQuery, List<String> tokens) {
		String lower = label.toLowerCase(Locale.ROOT);
		if (lower.equals(lowerQuery)) {
			return 0;
		}
		String quoted = Pattern.quote(lowerQuery);
		String before = "(?<![\\p{L}\\p{N}])";
		String after = "(?![\\p{L}\\p{N}])";
		if (Pattern.compile("^" + quoted + after).matcher(lower).find()) {
			return 1;
		}
		if (Pattern.compile(before + quoted + after).matcher(lower).find()) {
			return 2;
		}
		if (lower.startsWith(lowerQuery)) {
			return 3;
		}
		if (Pattern.compile(before + quoted).matcher(lower).find()) {
			return 4;
		}
		return matchesAllPrefixes(label, tokens) ? 5 : 6;
	}

	// ----------------------------------------------------------------------------------------------------
	// Details
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Fetches the details of a descriptor.
	 *
	 * @param id a descriptor ID such as {@code D009203}
	 * @return the details, or {@code null} if there is no such descriptor
	 * @throws MeshException if the service can't be reached or answers with an error
	 */
	public MeshDetails details(String id) throws MeshException {
		String descriptor = id == null ? "" : id.strip().toUpperCase(Locale.ROOT);
		if (!isDescriptorId(descriptor)) {
			throw new IllegalArgumentException("Not a MeSH descriptor ID: " + id);
		}
		if (index != null) {
			Concept concept = index.get(descriptor);
			if (concept != null) {
				List<MeshTerm> related = new ArrayList<>();
				for (String code : concept.related()) {
					Concept other = index.get(code);
					if (other != null) {
						related.add(new MeshTerm(other.code(), other.label()));
					}
				}
				List<String> trees = new ArrayList<>(concept.treeNumbers());
				trees.sort(null);
				return new MeshDetails(concept.code(), concept.label(),
						concept.definition().isBlank() ? null : concept.definition(), trees, concept.synonyms(), related);
			}
			if (offline) {
				return null;
			}
		}
		Object resource = getJson("/" + descriptor + ".json");
		String label = Json.string(resource, "label");
		if (label == null) {
			return null; // The service answers "{ }" for unknown IDs.
		}
		List<String> treeNumbers = new ArrayList<>();
		for (Object tree : Json.list(resource, "treeNumber")) {
			String number = localName(Json.string(tree));
			if (number != null && !number.isEmpty()) {
				treeNumbers.add(number);
			}
		}
		treeNumbers.sort(null);

		List<String> entryTerms = new ArrayList<>();
		List<MeshTerm> seeAlso = new ArrayList<>();
		Object lookup = getJson("/lookup/details?descriptor=" + descriptor);
		for (Object term : Json.list(lookup, "terms")) {
			String termLabel = Json.string(term, "label");
			if (termLabel != null && !termLabel.equals(label) && !entryTerms.contains(termLabel)) {
				entryTerms.add(termLabel);
			}
		}
		for (Object related : Json.list(lookup, "seealso")) {
			String relatedId = localName(Json.string(related, "resource"));
			String relatedLabel = Json.string(related, "label");
			if (isDescriptorId(relatedId) && relatedLabel != null) {
				seeAlso.add(new MeshTerm(relatedId, relatedLabel));
			}
		}

		String scopeNote = null;
		String concept = localName(Json.string(resource, "preferredConcept"));
		if (concept != null && concept.matches("M\\d+")) {
			try {
				scopeNote = Json.string(getJson("/" + concept + ".json"), "scopeNote");
			} catch (MeshException e) {
				if (e.kind() == MeshException.Kind.CANCELLED) {
					throw e;
				}
				// The scope note is optional; show the rest of the details without it.
			}
		}
		return new MeshDetails(descriptor, label, scopeNote == null ? null : scopeNote.strip(), treeNumbers,
				entryTerms, seeAlso);
	}

	// ----------------------------------------------------------------------------------------------------
	// HTTP
	// ----------------------------------------------------------------------------------------------------

	private Object getJson(String pathAndQuery) throws MeshException {
		String url = baseUrl + pathAndQuery;
		String body;
		synchronized (cache) {
			body = cache.get(url);
		}
		if (body == null) {
			body = fetch(url);
		}
		Object json;
		try {
			json = Json.parse(body);
		} catch (IllegalArgumentException e) {
			throw invalid(pathAndQuery, e);
		}
		if (json instanceof Map<?, ?> map && map.containsKey("error")) {
			throw new MeshException(MeshException.Kind.SERVER, "The MeSH service reported an error: " + body);
		}
		synchronized (cache) {
			cache.put(url, body);
		}
		return json;
	}

	private String fetch(String url) throws MeshException {
		if (offline) {
			throw new MeshException(MeshException.Kind.OFFLINE, "Offline mode: no cached MeSH data for " + url);
		}
		MeshTransport.Response response;
		try {
			response = transport.get(URI.create(url));
		} catch (InterruptedIOException e) {
			if (Thread.currentThread().isInterrupted() || !(e instanceof java.net.SocketTimeoutException)) {
				throw new MeshException(MeshException.Kind.CANCELLED, "MeSH request cancelled", e);
			}
			throw new MeshException(MeshException.Kind.OFFLINE, "The MeSH service did not answer in time", e);
		} catch (IOException e) {
			throw new MeshException(MeshException.Kind.OFFLINE, "The MeSH service can't be reached: " + e, e);
		} catch (RuntimeException e) {
			throw new MeshException(MeshException.Kind.OFFLINE, "The MeSH service can't be reached: " + e, e);
		}
		if (response == null || response.body() == null) {
			throw invalid(url, null);
		}
		if (response.status() < 200 || response.status() >= 300) {
			throw new MeshException(MeshException.Kind.SERVER,
					"The MeSH service answered with HTTP status " + response.status());
		}
		return response.body();
	}

	private static MeshException invalid(String what, Throwable cause) {
		return new MeshException(MeshException.Kind.INVALID_RESPONSE, "Unexpected MeSH response (" + what + ")",
				cause);
	}

	private static String encode(String text) {
		return URLEncoder.encode(text, StandardCharsets.UTF_8).replace("+", "%20");
	}
}
