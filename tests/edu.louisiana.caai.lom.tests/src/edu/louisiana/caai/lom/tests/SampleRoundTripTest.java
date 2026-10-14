/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import edu.louisiana.caai.lom.model.healthcare.CreditUnitType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;

/**
 * Loads, validates and round-trips the corrected sample documents.
 */
class SampleRoundTripTest {

	@TestFactory
	Stream<DynamicTest> samplesLoadWithoutErrors() {
		return TestResources.SAMPLES.stream().map(name -> DynamicTest.dynamicTest(name, () -> {
			Resource resource = HlomResources.load(TestResources.sample(name));
			assertTrue(resource.getErrors().isEmpty(), () -> name + ": " + resource.getErrors());
			assertNotNull(HlomResources.getDocumentRoot(resource), name);
		}));
	}

	@TestFactory
	Stream<DynamicTest> samplesHaveNoValidationErrors() {
		return TestResources.SAMPLES.stream().map(name -> DynamicTest.dynamicTest(name, () -> {
			List<HlomProblem> problems = HlomValidator.validate(HlomResources.load(TestResources.sample(name)));
			List<HlomProblem> errors = TestResources.withSeverity(problems, Severity.ERROR);
			assertTrue(errors.isEmpty(), () -> name + TestResources.describe(errors));
		}));
	}

	@TestFactory
	Stream<DynamicTest> samplesRoundTripStably() {
		return TestResources.SAMPLES.stream().map(name -> DynamicTest.dynamicTest(name, () -> {
			String first = HlomResources.toXml(HlomResources.load(TestResources.sample(name)));
			Resource reparsed = HlomResources.parse(first, URI.createURI("memory:/" + name));
			assertTrue(reparsed.getErrors().isEmpty(), () -> name + ": " + reparsed.getErrors());
			String second = HlomResources.toXml(reparsed);
			assertEquals(first, second, name);
			assertTrue(second.contains("http://ns.medbiq.org/lom/extend/v1/"), name);
		}));
	}

	@Test
	void creditsAndKeywordsSurviveRoundTrip() throws IOException {
		Lom lom = roundTrip("course-with-multiple-credit-types.xml");

		List<Keyword> keywords = lom.getGeneral().getFirst().getKeyword();
		assertEquals(2, keywords.size());
		assertEquals("arsenic", keywords.getFirst().getString().getFirst().getValue());
		// hx:source/hx:id (as in the specification samples) match the LOM anyAttribute wildcard.
		assertEquals("MeSH", keywords.getFirst().getSource());
		assertEquals("D001151", keywords.getFirst().getId());
		assertEquals("D019550", keywords.get(1).getId());

		HealthcareEducationType education = lom.getHealthcareMetadata().getFirst().getHealthcareEducation();
		assertNotNull(education);
		assertTrue(education.isSetCreditsAvailable());
		assertEquals(YesNoType.YES, education.getCreditsAvailable());
		List<CreditsType> credits = education.getCredits();
		assertEquals(2, credits.size());
		assertEquals("ACCME", credits.getFirst().getAccreditingBody());
		assertEquals(CreditUnitType.CREDIT, credits.getFirst().getCreditUnit());
		assertEquals(new BigDecimal("1.5"), credits.getFirst().getNumberOfCredits());
		assertEquals(CreditUnitType.CONTACT_HOUR, credits.get(1).getCreditUnit());
		assertEquals(new BigDecimal("1.7"), credits.get(1).getNumberOfCredits());
		assertEquals(2, education.getTargetAudience().getProfession().size());
	}

	@Test
	void assetSurvivesRoundTrip() throws IOException {
		Lom lom = roundTrip("medical-image-asset.xml");
		HealthcareAssetType asset = lom.getHealthcareMetadata().getFirst().getHealthcareAsset();
		assertNotNull(asset);
		assertEquals(YesNoType.YES, asset.getAnnotated());
		assertEquals(2, lom.getGeneral().getFirst().getKeyword().size());
	}

	@Test
	void keywordAttributesAreMappedToModel() throws IOException {
		Keyword keyword = roundTrip("course-with-multiple-credit-types.xml").getGeneral().getFirst().getKeyword().getFirst();
		assertEquals("MeSH", keyword.getSource());
		assertEquals("D001151", keyword.getId());
	}

	@Test
	void keywordAttributesRoundTripAsQualified() throws IOException {
		String xml = HlomResources
				.toXml(HlomResources.load(TestResources.sample("course-with-multiple-credit-types.xml")));
		assertTrue(xml.contains("hx:source=\"MeSH\""), xml);
		assertTrue(xml.contains("hx:id=\"D001151\""), xml);
	}

	private static Lom roundTrip(String name) throws IOException {
		String xml = HlomResources.toXml(HlomResources.load(TestResources.sample(name)));
		DocumentRoot root = HlomResources.getDocumentRoot(HlomResources.parse(xml, URI.createURI("memory:/" + name)));
		assertNotNull(root);
		return root.getLom();
	}
}
