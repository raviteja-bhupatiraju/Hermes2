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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.mesh.MeshTransport;
import edu.louisiana.caai.lom.model.support.mesh.UmlsClient;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;

/** The online search of licensed terminologies with the user's UMLS key ({@link UmlsClient}), without a network. */
class UmlsClientTest {

	private final List<URI> requests = new ArrayList<>();

	private UmlsClient client(int status, String body, String key) {
		MeshTransport transport = uri -> {
			requests.add(uri);
			return new MeshTransport.Response(status, body);
		};
		return new UmlsClient(transport, () -> key, "https://uts.example/rest");
	}

	@Test
	void searchReturnsTheConceptsOfTheSource() throws IOException {
		UmlsClient client = client(200, """
				{"pageSize":25,"pageNumber":1,"result":{"classType":"searchResults","results":[
				{"ui":"22298006","rootSource":"SNOMEDCT_US","uri":"x","name":"Myocardial infarction"},
				{"ui":"NONE","name":"NO RESULTS"}]}}""", "secret key");
		List<Concept> concepts = client.search(UmlsClient.Source.SNOMED_CT, "heart attack", 10);
		assertEquals(1, concepts.size());
		assertEquals("22298006", concepts.getFirst().code());
		assertEquals("Myocardial infarction", concepts.getFirst().label());
		String query = requests.getFirst().toString();
		assertTrue(query.contains("sabs=SNOMEDCT_US") && query.contains("returnIdType=code")
				&& query.contains("string=heart+attack") && query.contains("apiKey=secret+key"), query);
	}

	@Test
	void problemsAreExplained() {
		IOException rejected = assertThrows(IOException.class,
				() -> client(401, "", "bad").search(UmlsClient.Source.LOINC, "glucose", 5));
		assertTrue(rejected.getMessage().contains("not accepted"), rejected.getMessage());
		IOException missing = assertThrows(IOException.class,
				() -> client(200, "{}", null).search(UmlsClient.Source.LOINC, "glucose", 5));
		assertTrue(missing.getMessage().contains("No UMLS API key"), missing.getMessage());
		assertTrue(requests.size() == 1, "nothing is requested without a key");
	}

	@Test
	void codesAreLookedUp() throws IOException {
		assertEquals("Glucose [Mass/volume] in Serum or Plasma",
				client(200, "{\"result\":{\"ui\":\"2345-7\",\"name\":\"Glucose [Mass/volume] in Serum or Plasma\"}}", "k")
						.get(UmlsClient.Source.LOINC, "2345-7").label());
		assertNull(client(404, "", "k").get(UmlsClient.Source.LOINC, "0000-0"));
	}

	@Test
	void onlineTerminologiesAreOfferedOnlyWithAKey() {
		UmlsClient.configureShared(() -> null, () -> false);
		assertFalse(Terminologies.keywordTerminologies().contains(Terminologies.SNOMED_CT));
		assertTrue(Terminologies.SNOMED_CT.isOnline());
		assertTrue(Terminologies.forSource("SNOMED CT") == Terminologies.SNOMED_CT);
		UmlsClient.configureShared(() -> "k", () -> true);
		try {
			assertTrue(Terminologies.keywordTerminologies().containsAll(List.of(Terminologies.SNOMED_CT,
					Terminologies.LOINC)));
		} finally {
			UmlsClient.configureShared(() -> null, () -> false);
		}
	}
}
