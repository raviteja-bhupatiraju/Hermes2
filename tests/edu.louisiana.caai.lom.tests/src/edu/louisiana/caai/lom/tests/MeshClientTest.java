/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.mesh.MeshClient;
import edu.louisiana.caai.lom.model.support.mesh.MeshDetails;
import edu.louisiana.caai.lom.model.support.mesh.MeshException;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;
import edu.louisiana.caai.lom.model.support.mesh.MeshTransport;

/**
 * The MeSH lookup client ({@link MeshClient}) against a fake HTTP layer serving recorded NLM responses.
 */
class MeshClientTest {

	private static final String BASE = "https://mesh.example.org/mesh";

	private static final String MI_SEARCH = """
			[{"resource":"http://id.nlm.nih.gov/mesh/D056988","label":"Anterior Wall Myocardial Infarction"},\
			{"resource":"http://id.nlm.nih.gov/mesh/D056989","label":"Inferior Wall Myocardial Infarction"},\
			{"resource":"http://id.nlm.nih.gov/mesh/D009203","label":"Myocardial Infarction"},\
			{"resource":"http://id.nlm.nih.gov/mesh/D000072658","label":"Non-ST Elevated Myocardial Infarction"}]""";

	private static final String MYOCARDIAL_SEARCH = """
			[{"resource":"http://id.nlm.nih.gov/mesh/D056988","label":"Anterior Wall Myocardial Infarction"},
			 {"resource":"http://id.nlm.nih.gov/mesh/D054084","label":"Myocardial Bridging"},
			 {"resource":"http://id.nlm.nih.gov/mesh/D009203","label":"Myocardial Infarction"},
			 {"resource":"http://id.nlm.nih.gov/mesh/D009202","label":"Cardiomyopathies"}]""";

	private static final String MI_DESCRIPTOR = """


			{
			  "@id" : "http://id.nlm.nih.gov/mesh/D009203",
			  "identifier" : "D009203",
			  "preferredConcept" : "http://id.nlm.nih.gov/mesh/M0014340",
			  "treeNumber" : [ "http://id.nlm.nih.gov/mesh/C14.907.585.500", "http://id.nlm.nih.gov/mesh/C14.280.647.500" ],
			  "label" : { "@language" : "en", "@value" : "Myocardial Infarction" },
			  "@context" : { "treeNumber" : { "@id" : "http://id.nlm.nih.gov/mesh/vocab#treeNumber", "@type" : "@id" } }
			}""";

	private static final String MI_LOOKUP_DETAILS = """
			{"descriptor":"http://id.nlm.nih.gov/mesh/D009203","terms":[\
			{"resource":"http://id.nlm.nih.gov/mesh/T027447","label":"Myocardial Infarction","preferred":true},\
			{"resource":"http://id.nlm.nih.gov/mesh/T855594","label":"Cardiovascular Stroke","preferred":false},\
			{"resource":"http://id.nlm.nih.gov/mesh/T000866941","label":"Heart Attack","preferred":false}],\
			"qualifiers":[{"resource":"http://id.nlm.nih.gov/mesh/Q000097","label":"blood"}],\
			"seealso":[{"resource":"http://id.nlm.nih.gov/mesh/D006342","label":"Heart Rupture, Post-Infarction"}]}""";

	private static final String MI_CONCEPT = """
			{ "@id" : "http://id.nlm.nih.gov/mesh/M0014340",
			  "scopeNote" : { "@language" : "en", "@value" : "NECROSIS of the MYOCARDIUM caused by an obstruction \\"of\\" the blood supply\\u002E\\n" } }""";

	/** Serves canned responses by URL (exact match) and records the requests. */
	private static final class FakeTransport implements MeshTransport {
		final Map<String, Object> responses = new LinkedHashMap<>();
		final List<String> requests = new ArrayList<>();
		final List<URI> rawRequests = new ArrayList<>();

		FakeTransport serve(String pathAndQuery, String body) {
			responses.put(BASE + pathAndQuery, new Response(200, body));
			return this;
		}

		FakeTransport fail(String pathAndQuery, Object responseOrException) {
			responses.put(BASE + pathAndQuery, responseOrException);
			return this;
		}

		@Override
		public Response get(URI uri) throws IOException {
			String url = URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8);
			requests.add(url);
			rawRequests.add(uri);
			Object response = responses.get(url);
			if (response instanceof IOException exception) {
				throw exception;
			}
			if (response instanceof Response r) {
				return r;
			}
			return new Response(404, "Not found: " + url);
		}

		int count(String fragment) {
			return (int) requests.stream().filter(r -> r.contains(fragment)).count();
		}
	}

	private FakeTransport transport;
	private MeshClient client;

	@BeforeEach
	void createClient() {
		transport = new FakeTransport();
		client = new MeshClient(transport, BASE + "/");
	}

	private static String search(String label) {
		return "/lookup/descriptor?label=" + label + "&match=contains&limit=" + MeshClient.MAX_LIMIT;
	}

	@Test
	void searchRanksExactAndPrefixMatchesFirst() throws Exception {
		transport.serve(search("myocardial infarction"), MI_SEARCH);
		List<MeshTerm> results = client.search("  myocardial   infarction ", 3);
		assertEquals(3, results.size(), "limited to 3");
		assertEquals(new MeshTerm("D009203", "Myocardial Infarction"), results.getFirst());
		assertEquals("D056988", results.get(1).id(), "word matches next, shorter labels first");
		assertEquals(BASE + search("myocardial infarction"), transport.requests.getFirst(), "query is normalized");
	}

	@Test
	void searchEncodesQueryAndUsesMaximumLimit() throws Exception {
		transport.serve(search("crohn's & colitis"), "[]").serve(search("colitis"), "[]");
		client.search("Crohn's & colitis", 10);
		assertEquals(BASE + "/lookup/descriptor?label=crohn%27s%20%26%20colitis&match=contains&limit=50",
				transport.rawRequests.getFirst().toString());
		assertEquals(BASE + search("colitis"), transport.requests.get(1), "falls back to the longest word");
	}

	@Test
	void partialPhraseFallsBackToLongestWordAndPrefixFilter() throws Exception {
		transport.serve(search("myocardial inf"), "[]").serve(search("myocardial"), MYOCARDIAL_SEARCH);
		List<MeshTerm> results = client.search("myocardial inf", 20);
		assertEquals(List.of("D009203", "D056988"), results.stream().map(MeshTerm::id).toList());
	}

	@Test
	void descriptorIdIsResolvedWithLabelLookup() throws Exception {
		transport.serve("/lookup/label?resource=D009203", "[\"Myocardial Infarction\"]")
				.serve("/lookup/label?resource=D999999", "[]");
		assertEquals(List.of(new MeshTerm("D009203", "Myocardial Infarction")), client.search("d009203", 20));
		assertEquals(List.of(), client.search("D999999", 20));
	}

	@Test
	void blankSearchMakesNoRequest() throws Exception {
		assertEquals(List.of(), client.search("   ", 20));
		assertEquals(List.of(), client.search(null, 20));
		assertTrue(transport.requests.isEmpty());
	}

	@Test
	void resultsAreCached() throws Exception {
		transport.serve(search("myocardial infarction"), MI_SEARCH);
		client.search("myocardial infarction", 20);
		client.search("Myocardial infarction ", 20);
		assertEquals(1, transport.requests.size());
		client.clearCache();
		client.search("myocardial infarction", 20);
		assertEquals(2, transport.requests.size());
	}

	@Test
	void offlineModeAnswersFromCacheOnly() throws Exception {
		transport.serve(search("myocardial infarction"), MI_SEARCH);
		client.search("myocardial infarction", 20);
		client.setOffline(true);
		assertTrue(client.isOffline());
		assertEquals(4, client.search("myocardial infarction", 20).size());
		MeshException e = assertThrows(MeshException.class, () -> client.search("diabetes", 20));
		assertEquals(MeshException.Kind.OFFLINE, e.kind());
		assertEquals(1, transport.requests.size(), "no request while offline");
	}

	@Test
	void networkFailuresAreReportedAsOffline() {
		transport.fail(search("asthma"), new ConnectException("Connection refused"))
				.fail(search("gout"), new HttpTimeoutException("request timed out"));
		assertEquals(MeshException.Kind.OFFLINE,
				assertThrows(MeshException.class, () -> client.search("asthma", 5)).kind());
		assertEquals(MeshException.Kind.OFFLINE,
				assertThrows(MeshException.class, () -> client.search("gout", 5)).kind());
	}

	@Test
	void serverErrorsAndInvalidResponses() {
		transport.fail(search("asthma"), new MeshTransport.Response(503, "Service Unavailable"))
				.serve(search("gout"), "<html>maintenance</html>")
				.serve(search("acne"), "{\"error\":{\"limit\":[\"must be less than or equal to 50\"]}}")
				.serve(search("rash"), "{\"unexpected\":true}");
		assertEquals(MeshException.Kind.SERVER,
				assertThrows(MeshException.class, () -> client.search("asthma", 5)).kind());
		assertEquals(MeshException.Kind.INVALID_RESPONSE,
				assertThrows(MeshException.class, () -> client.search("gout", 5)).kind());
		assertEquals(MeshException.Kind.SERVER,
				assertThrows(MeshException.class, () -> client.search("acne", 5)).kind());
		assertEquals(MeshException.Kind.INVALID_RESPONSE,
				assertThrows(MeshException.class, () -> client.search("rash", 5)).kind());
		// Failed responses are not cached.
		transport.serve(search("asthma"), "[]");
		assertDoesNotFail(() -> client.search("asthma", 5));
	}

	@Test
	void detailsCombineDescriptorLookupAndConcept() throws Exception {
		transport.serve("/D009203.json", MI_DESCRIPTOR).serve("/lookup/details?descriptor=D009203", MI_LOOKUP_DETAILS)
				.serve("/M0014340.json", MI_CONCEPT);
		MeshDetails details = client.details("d009203");
		assertEquals("D009203", details.id());
		assertEquals("Myocardial Infarction", details.label());
		assertEquals("NECROSIS of the MYOCARDIUM caused by an obstruction \"of\" the blood supply.",
				details.scopeNote());
		assertEquals(List.of("C14.280.647.500", "C14.907.585.500"), details.treeNumbers());
		assertEquals(List.of("Cardiovascular Stroke", "Heart Attack"), details.entryTerms());
		assertEquals(List.of(new MeshTerm("D006342", "Heart Rupture, Post-Infarction")), details.seeAlso());
		assertEquals(new MeshTerm("D009203", "Myocardial Infarction"), details.term());

		client.details("D009203");
		assertEquals(1, transport.count("/D009203.json"), "details are cached");
	}

	@Test
	void detailsWithoutScopeNote() throws Exception {
		transport.serve("/D009203.json", MI_DESCRIPTOR).serve("/lookup/details?descriptor=D009203", MI_LOOKUP_DETAILS)
				.fail("/M0014340.json", new MeshTransport.Response(500, "error"));
		MeshDetails details = client.details("D009203");
		assertNull(details.scopeNote());
		assertEquals(2, details.treeNumbers().size());
	}

	@Test
	void detailsOfUnknownDescriptor() throws Exception {
		transport.serve("/D999999.json", "{ }");
		assertNull(client.details("D999999"));
		assertThrows(IllegalArgumentException.class, () -> client.details("heart"));
	}

	@Test
	void descriptorIds() {
		assertTrue(MeshClient.isDescriptorId("D009203"));
		assertTrue(MeshClient.isDescriptorId("D000072658"));
		assertFalse(MeshClient.isDescriptorId("D09203"));
		assertFalse(MeshClient.isDescriptorId("D0092031"));
		assertFalse(MeshClient.isDescriptorId("d009203"));
		assertFalse(MeshClient.isDescriptorId("C14.280.647.500"));
		assertFalse(MeshClient.isDescriptorId(null));
		assertEquals("D009203", MeshClient.localName("http://id.nlm.nih.gov/mesh/D009203"));
		assertEquals(BASE, client.getBaseUrl());
	}

	private interface Call {
		void run() throws Exception;
	}

	private static void assertDoesNotFail(Call call) {
		try {
			call.run();
		} catch (Exception e) {
			throw new AssertionError("Unexpected exception", e);
		}
	}
}
