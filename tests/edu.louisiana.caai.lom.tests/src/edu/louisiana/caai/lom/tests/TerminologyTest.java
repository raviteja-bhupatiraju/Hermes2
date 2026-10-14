/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.mesh.MeshClient;
import edu.louisiana.caai.lom.model.support.mesh.MeshDetails;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;
import edu.louisiana.caai.lom.model.support.mesh.MeshTransport;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;
import edu.louisiana.caai.lom.model.support.terminology.TerminologyValidator;

/**
 * The offline terminologies shipped with the application.
 */
class TerminologyTest {

	/** A transport that fails the test if the network is used. */
	private static final MeshTransport NO_NETWORK = uri -> {
		throw new AssertionError("network used: " + uri);
	};

	@Test
	void allTerminologiesLoad() {
		for (Terminology terminology : Terminologies.all()) {
			if (terminology.isOnline()) {
				continue; // SNOMED CT and LOINC: searched online with the user's UMLS license (UmlsClientTest).
			}
			assertTrue(terminology.size() > 0, terminology.getId());
			assertFalse(terminology.getVersion().isBlank(), terminology.getId());
			assertFalse(terminology.getLicense().isBlank(), terminology.getId());
		}
		assertTrue(Terminologies.MESH.size() > 30_000, "all MeSH descriptors");
		assertTrue(Terminologies.ICD_10_CM.size() > 90_000, "all ICD-10-CM codes");
		assertEquals(6, Terminologies.ACGME.size());
		assertEquals(75 + 3, Terminologies.AAMC_CI.size(), "75 terms in 3 groups");
	}

	@Test
	void meshSearchAndDetails() {
		Terminology mesh = Terminologies.MESH;
		List<Terminology.Match> matches = mesh.search("myocardial infarction", 10);
		assertEquals("D009203", matches.getFirst().concept().code());
		// Entry terms find their descriptor.
		assertTrue(mesh.search("heart attack", 10).stream().anyMatch(m -> m.concept().code().equals("D009203")));
		// Partially typed words.
		assertTrue(mesh.search("myocard infarc", 10).stream().anyMatch(m -> m.concept().code().equals("D009203")));
		Concept sepsis = mesh.get("D018805");
		assertEquals("Sepsis", sepsis.label());
		assertFalse(sepsis.definition().isBlank());
		assertFalse(sepsis.treeNumbers().isEmpty());
		assertFalse(mesh.path(sepsis).isEmpty());
	}

	@Test
	void icdSearchHierarchyAndSeventhCharacterCodes() {
		Terminology icd = Terminologies.ICD_10_CM;
		Concept a419 = icd.get("A41.9");
		assertEquals("Sepsis, unspecified organism", a419.label());
		assertEquals("billable code", a419.kind());
		List<Concept> path = icd.path(a419);
		assertEquals("Chapter 1", path.getFirst().code());
		assertEquals("A41.9", path.getLast().code());
		assertTrue(icd.search("sepsis", 20).stream().anyMatch(m -> m.concept().code().equals("A41.9")));
		// Codes with a 7th character are included (from the code file).
		Concept s72 = icd.get("S72.001A");
		assertNotNull(s72);
		assertEquals("S72.001", s72.parents().getFirst());
		assertSame(a419, icd.search("A41.9", 5).getFirst().concept(), "a code finds its concept first");
	}

	@Test
	void educationalTaxonomies() {
		assertEquals("IM013", Terminologies.AAMC_CI.search("lecture", 5).getFirst().concept().code());
		assertFalse(Terminologies.AAMC_CI.get("IM013").definition().isBlank());
		assertEquals("4", Terminologies.BLOOM.search("analyze", 5).getFirst().concept().code());
		assertEquals("4", Terminologies.BLOOM.get("4.1").parents().getFirst());
		// Coded a-f as in the HLOM specification's examples (b = Medical Knowledge); abbreviations are synonyms.
		assertEquals("b", Terminologies.ACGME.search("medical knowledge", 5).getFirst().concept().code());
		assertEquals("b", Terminologies.ACGME.search("MK", 5).getFirst().concept().code());
		assertSame(Terminologies.ICD_10_CM, Terminologies.forSource("ICD-10"));
		assertSame(Terminologies.ACGME, Terminologies.forSource("ACGME Core Competencies"));
		assertSame(Terminologies.BLOOM, Terminologies.forSource("Bloom's Taxonomy"));
		assertSame(Terminologies.AAMC_CI, Terminologies.forSource("AAMC Curriculum Inventory"));
		// Licensed terminologies are known by name (searched online), but never checked offline.
		assertSame(Terminologies.SNOMED_CT, Terminologies.forSource("SNOMED CT"));
		assertNull(Terminologies.forSource("UMLS"));
	}

	@Test
	void meshClientWorksOffline() throws Exception {
		MeshClient client = new MeshClient(Terminologies.MESH, NO_NETWORK, MeshClient.DEFAULT_BASE_URL);
		client.setOffline(true);
		List<MeshTerm> terms = client.search("Myocardial Infarction", 5);
		assertEquals(new MeshTerm("D009203", "Myocardial Infarction"), terms.getFirst());
		MeshDetails details = client.details("D009203");
		assertNotNull(details.scopeNote());
		assertTrue(details.entryTerms().stream().anyMatch(term -> term.equalsIgnoreCase("Heart Attack")));
		assertFalse(details.treeNumbers().isEmpty());
		assertNull(client.details("D999999"), "unknown descriptor, offline");
		assertTrue(MeshClient.shared().hasOfflineIndex());
	}

	@Test
	void unknownCodesAreReported() {
		DocumentRoot root = HlomDocuments.createDocument("Codes", "en");
		Keyword good = keyword("Sepsis", "ICD-10-CM", "A41.9");
		Keyword bad = keyword("Nothing", "ICD-10", "Z99.999X");
		Keyword mesh = keyword("Sepsis", "MeSH", "D018805");
		Keyword unknownMesh = keyword("Unknown", "MeSH", "D999999");
		root.getLom().getGeneral().getFirst().getKeyword().addAll(List.of(good, bad, mesh, unknownMesh));
		List<HlomProblem> problems = TerminologyValidator.check(root);
		assertEquals(2, problems.size(), problems.toString());
		assertSame(bad, problems.getFirst().object());
		assertSame(unknownMesh, problems.get(1).object());
	}

	@Test
	void samplesUseValidCodes() throws Exception {
		for (String name : TestResources.SAMPLES) {
			DocumentRoot root = edu.louisiana.caai.lom.model.support.HlomResources
					.getDocumentRoot(edu.louisiana.caai.lom.model.support.HlomResources.load(TestResources.sample(name)));
			List<HlomProblem> problems = TerminologyValidator.check(root);
			assertTrue(problems.isEmpty(), () -> name + TestResources.describe(problems));
		}
	}

	private static Keyword keyword(String text, String source, String id) {
		Keyword keyword = LomFactory.eINSTANCE.createKeyword();
		keyword.getString().add(HlomDocuments.langString(text, "en"));
		keyword.setSource(source);
		keyword.setId(id);
		return keyword;
	}
}
