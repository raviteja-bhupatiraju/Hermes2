/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import edu.louisiana.caai.lom.model.support.terminology.Concept;

/**
 * Searches licensed terminologies (SNOMED CT, LOINC) online with the UMLS Terminology Services (UTS) REST API of the
 * U.S. National Library of Medicine, using the API key of the user's own UMLS license
 * (<a href="https://uts.nlm.nih.gov/uts/profile">uts.nlm.nih.gov</a>). Without a key nothing is requested.
 */
public final class UmlsClient {

	/** The UTS REST API. */
	public static final String BASE_URL = "https://uts-ws.nlm.nih.gov/rest";

	/** A source vocabulary of the UMLS. */
	public enum Source {
		/** SNOMED CT, US Edition. */
		SNOMED_CT("SNOMEDCT_US", "SNOMED CT"),
		/** LOINC. */
		LOINC("LNC", "LOINC");

		private final String abbreviation;
		private final String label;

		Source(String abbreviation, String label) {
			this.abbreviation = abbreviation;
			this.label = label;
		}

		/** The UMLS source abbreviation (SAB). */
		public String abbreviation() {
			return abbreviation;
		}

		/** The name, as written in documents. */
		public String label() {
			return label;
		}
	}

	private static volatile UmlsClient shared = new UmlsClient(
			new HttpMeshTransport(Duration.ofSeconds(10), MeshClient.USER_AGENT), () -> null, BASE_URL);

	private final MeshTransport transport;
	private final Supplier<String> apiKey;
	private final java.util.function.BooleanSupplier hasKey;
	private final String baseUrl;

	/**
	 * @param transport the HTTP layer
	 * @param apiKey    supplies the user's UMLS API key ({@code null} or blank if there is none)
	 * @param baseUrl   the API address
	 */
	public UmlsClient(MeshTransport transport, Supplier<String> apiKey, String baseUrl) {
		this(transport, apiKey, null, baseUrl);
	}

	/**
	 * @param hasKey whether a key is set, without reading it (reading a stored key may ask for a password), or
	 *               {@code null} to read the key
	 */
	public UmlsClient(MeshTransport transport, Supplier<String> apiKey, java.util.function.BooleanSupplier hasKey,
			String baseUrl) {
		this.transport = transport;
		this.apiKey = apiKey;
		this.hasKey = hasKey;
		this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
	}

	/** The client used by the application. */
	public static UmlsClient shared() {
		return shared;
	}

	/**
	 * Sets the API key of the client used by the application: read when a search needs it (for example from secure
	 * storage), and whether there is one.
	 */
	public static void configureShared(Supplier<String> apiKey, java.util.function.BooleanSupplier hasKey) {
		shared = new UmlsClient(new HttpMeshTransport(Duration.ofSeconds(10), MeshClient.USER_AGENT), apiKey, hasKey,
				BASE_URL);
	}

	/** Whether a key is set (the online terminologies are offered). */
	public boolean isConfigured() {
		return hasKey != null ? hasKey.getAsBoolean() : key() != null;
	}

	private String key() {
		String key = apiKey.get();
		return key == null || key.isBlank() ? null : key.strip();
	}

	/** Searches the concepts of a source by words or code. */
	public List<Concept> search(Source source, String text, int limit) throws IOException {
		String key = requireKey();
		String uri = baseUrl + "/search/current?string=" + encode(text) + "&sabs=" + source.abbreviation()
				+ "&returnIdType=code&searchType=words&pageSize=" + Math.clamp(limit, 1, 100) + "&apiKey=" + encode(key);
		Object json = get(uri);
		List<Concept> concepts = new ArrayList<>();
		for (Object result : Json.list(Json.member(json, "result"), "results")) {
			String code = Json.string(result, "ui");
			String name = Json.string(result, "name");
			if (code != null && name != null && !code.equals("NONE")) {
				concepts.add(concept(source, code, name));
			}
		}
		return concepts;
	}

	/** The concept of a code, or {@code null} if the source has no such code. */
	public Concept get(Source source, String code) throws IOException {
		String key = requireKey();
		String uri = baseUrl + "/content/current/source/" + source.abbreviation() + "/" + encode(code.strip())
				+ "?apiKey=" + encode(key);
		try {
			Object json = get(uri);
			String name = Json.string(Json.member(json, "result"), "name");
			return name == null ? null : concept(source, code.strip(), name);
		} catch (NotFound _) {
			return null;
		}
	}

	/** Checks the API key with a small search; throws an exception explaining what is wrong. */
	public void check() throws IOException {
		search(Source.SNOMED_CT, "fever", 1);
	}

	private static Concept concept(Source source, String code, String name) {
		return new Concept(code, name, List.of(), List.of(), List.of(), "", List.of(), source.label() + " concept");
	}

	private String requireKey() throws IOException {
		String key = key();
		if (key == null) {
			throw new IOException("No UMLS API key is set (Preferences > Healthcare LOM > Terminologies).");
		}
		return key;
	}

	private static final class NotFound extends IOException {
		private static final long serialVersionUID = 1L;

		NotFound() {
			super("Not found");
		}
	}

	private Object get(String uri) throws IOException {
		MeshTransport.Response response = transport.get(URI.create(uri));
		return switch (response.status()) {
			case 200 -> Json.parse(response.body());
			case 401, 403 -> throw new IOException("The UMLS API key was not accepted; check it in your UTS profile "
					+ "(https://uts.nlm.nih.gov/uts/profile).");
			case 404 -> throw new NotFound();
			default -> throw new IOException("The UMLS Terminology Services answered with status " + response.status()
					+ ".");
		};
	}

	private static String encode(String text) {
		return URLEncoder.encode(text, StandardCharsets.UTF_8);
	}
}
