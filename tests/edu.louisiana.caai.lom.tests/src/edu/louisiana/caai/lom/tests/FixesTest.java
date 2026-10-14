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

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.address.AddressFactory;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;

/**
 * Quick fixes: each fix is found for the problem the validator reports and makes the problem disappear.
 */
class FixesTest {

	private DocumentRoot root;
	private Resource resource;
	private HealthcareEducationType education;

	@BeforeEach
	void createDocument() {
		root = HlomDocuments.createDocument("Title", "en-US");
		education = HealthcareFactory.eINSTANCE.createHealthcareEducationType();
		root.getLom().getHealthcareMetadata().getFirst().setHealthcareEducation(education);
		resource = HlomResources.createResourceSet().createResource(URI.createURI("memory:/fixes.xml"));
		resource.getContents().add(root);
	}

	@Test
	void missingMetadataSchemasAreDeclared() {
		MetaMetadata metaMetadata = root.getLom().getMetaMetadata().getFirst();
		metaMetadata.getMetadataSchema().clear();
		HlomProblem lom = find("\"" + HlomDocuments.LOM_METADATA_SCHEMA + "\"");
		HlomProblem healthcare = find("\"" + HlomDocuments.HEALTHCARE_METADATA_SCHEMA + "\"");
		assertEquals(List.of("Declare metadata schema LOMv1.0"), labels(lom));
		assertEquals(List.of("Declare metadata schema HEALTHCARE_LOMv1"), labels(healthcare));

		applyOnly(lom);
		assertTrue(matching("metadata schema").size() == 1, describe());
		applyOnly(healthcare);
		assertTrue(matching("metadata schema").isEmpty(), describe());
		assertEquals(2, metaMetadata.getMetadataSchema().size());
	}

	@Test
	void missingMetaMetadataIsCreated() {
		root.getLom().getMetaMetadata().clear();
		HlomFixes.fixAll(root, validate());
		assertTrue(matching("metadata schema").isEmpty(), describe());
		assertEquals(1, root.getLom().getMetaMetadata().size());
	}

	@Test
	void missingHealthcareMetadataIsAdded() {
		root.getLom().getHealthcareMetadata().clear();
		HlomProblem problem = find("healthcareMetadata must occur");
		applyOnly(problem);
		assertTrue(matching("healthcareMetadata must occur").isEmpty(), describe());
		assertEquals(1, root.getLom().getHealthcareMetadata().size());
	}

	@Test
	void textWithoutLanguageGetsTheDocumentLanguage() {
		root.getLom().getGeneral().getFirst().getLanguage().getFirst().setValue("fr-CA");
		LangString title = root.getLom().getGeneral().getFirst().getTitle().getFirst().getString().getFirst();
		title.setLanguage(null);
		HlomProblem problem = find("has no language");
		assertSame(title, problem.object());
		assertEquals(List.of("Set the language to fr-CA"), labels(problem));
		applyOnly(problem);
		assertEquals("fr-CA", title.getLanguage());
		assertTrue(matching("has no language").isEmpty(), describe());
	}

	@Test
	void documentLanguageFallsBackToMetaMetadataAndDefault() {
		root.getLom().getGeneral().getFirst().getLanguage().clear();
		root.getLom().getMetaMetadata().getFirst().getLanguage().getFirst().setValue("de");
		assertEquals("de", HlomFixes.documentLanguage(root));
		root.getLom().getMetaMetadata().getFirst().getLanguage().clear();
		assertEquals(HlomDocuments.DEFAULT_LANGUAGE, HlomFixes.documentLanguage(root));
	}

	@Test
	void creditsNotAvailableButGivenOffersTwoFixes() {
		education.setCreditsAvailable(YesNoType.NO);
		education.getCredits().add(HealthcareFactory.eINSTANCE.createCreditsType());
		HlomProblem problem = find("not available");
		assertEquals(List.of("Remove the credit information", "Mark credits as available"), labels(problem));
		assertNull(HlomFixes.automaticFix(problem), "the author has to choose");

		apply(problem, "Remove the credit information");
		assertTrue(education.getCredits().isEmpty());
		assertTrue(matching("credit information").isEmpty(), describe());

		education.getCredits().add(HealthcareFactory.eINSTANCE.createCreditsType());
		apply(find("not available"), "Mark credits as available");
		assertSame(YesNoType.YES, education.getCreditsAvailable());
		assertEquals(1, education.getCredits().size());
		assertTrue(matching("credit information").isEmpty(), describe());
	}

	@Test
	void creditsAvailableButMissingOffersTwoFixes() {
		education.setCreditsAvailable(YesNoType.YES);
		HlomProblem problem = find("no credit information");
		assertEquals(List.of("Add a credit entry", "Mark credits as not available"), labels(problem));

		apply(problem, "Add a credit entry");
		assertEquals(1, education.getCredits().size());
		assertTrue(matching("credit information").isEmpty(), describe());

		education.getCredits().clear();
		apply(find("no credit information"), "Mark credits as not available");
		assertSame(YesNoType.NO, education.getCreditsAvailable());
		assertTrue(matching("credit information").isEmpty(), describe());
	}

	@Test
	void activityDatesAreSwapped() throws DatatypeConfigurationException {
		XMLGregorianCalendar early = date("2026-03-01T09:00:00");
		XMLGregorianCalendar late = date("2026-03-02T17:00:00");
		education.setStartDateTime(late);
		education.setEndDateTime(early);
		HlomProblem problem = find("starts after it ends");
		assertNotNull(HlomFixes.automaticFix(problem));
		applyOnly(problem);
		assertEquals(early, education.getStartDateTime());
		assertEquals(late, education.getEndDateTime());
		assertTrue(matching("starts after").isEmpty(), describe());
		// Idempotent: a second application changes nothing.
		HlomFixes.forProblem(problem).forEach(fix -> fix.apply(root, problem));
		assertEquals(early, education.getStartDateTime());
	}

	@Test
	void creditDatesAreSwapped() throws DatatypeConfigurationException {
		CreditsType credits = HealthcareFactory.eINSTANCE.createCreditsType();
		credits.setReleaseDate(date("2027-01-01"));
		credits.setExpirationDate(date("2026-01-01"));
		education.getCredits().add(credits);
		HlomProblem problem = find("release date is after");
		assertSame(credits, problem.object());
		applyOnly(problem);
		assertEquals(date("2026-01-01"), credits.getReleaseDate());
		assertTrue(matching("release date is after").isEmpty(), describe());
	}

	@Test
	void disclosuresOnlyOfferNavigation() throws IOException {
		education.setOffLabelUse(YesNoType.YES);
		education.setCommercialSupport(YesNoType.YES);
		education.setRelevantFinancialRelationship(YesNoType.YES);
		for (String phrase : List.of("Off-label", "Commercial support", "financial relationship")) {
			HlomProblem problem = find(phrase);
			List<HlomFix> fixes = HlomFixes.forProblem(problem);
			assertEquals(1, fixes.size(), phrase);
			assertSame(HlomFix.Kind.NAVIGATE, fixes.getFirst().kind());
			assertSame(education, fixes.getFirst().target(problem));
		}
		String before = HlomResources.toXml(resource);
		assertTrue(HlomFixes.fixAll(root, validate()).isEmpty(), "no content is invented");
		assertEquals(before, HlomResources.toXml(resource));
	}

	@Test
	void missingTitleAndMeshIdOfferNavigation() {
		root.getLom().getGeneral().getFirst().getTitle().clear();
		assertEquals(List.of("Go to Title"), labels(find("no title")));

		var keyword = edu.louisiana.caai.lom.model.lom.LomFactory.eINSTANCE.createKeyword();
		keyword.getString().add(HlomDocuments.langString("asthma", "en-US"));
		keyword.setSource("MeSH");
		keyword.setId("12345");
		root.getLom().getGeneral().getFirst().getKeyword().add(keyword);
		HlomProblem mesh = find("MeSH keyword");
		assertEquals(List.of("Go to MeSH Keyword"), labels(mesh));
		assertSame(keyword, HlomFixes.forProblem(mesh).getFirst().target(mesh));
	}

	@Test
	void activityLocationWithoutCountryOffersNavigation() {
		ActivityLocationType location = HealthcareFactory.eINSTANCE.createActivityLocationType();
		location.setRoom("Room 101");
		education.setActivityLocation(location);
		List<HlomProblem> problems = matching("Country");
		assertFalse(problems.isEmpty(), describe());
		assertTrue(problems.stream().anyMatch(p -> labels(p).contains("Go to Activity Location")), describe());

		location.setCountry(AddressFactory.eINSTANCE.createCountryType());
		location.getCountry().setCountryCode("US");
		assertTrue(matching("Country").isEmpty(), describe());
	}

	@Test
	void fixAllRepairsEverythingAutomatic() throws DatatypeConfigurationException {
		root.getLom().getMetaMetadata().getFirst().getMetadataSchema().clear();
		root.getLom().getGeneral().getFirst().getTitle().getFirst().getString().getFirst().setLanguage(null);
		education.setStartDateTime(date("2026-03-02T17:00:00"));
		education.setEndDateTime(date("2026-03-01T09:00:00"));
		education.setOffLabelUse(YesNoType.YES);

		List<HlomProblem> problems = validate();
		List<HlomFixes.Planned> plan = HlomFixes.automaticFixes(problems);
		assertEquals(4, plan.size(), plan.toString());
		HlomFixes.fixAll(root, problems);
		List<HlomProblem> after = validate();
		assertTrue(HlomFixes.automaticFixes(after).isEmpty(), describe());
		assertEquals(1, after.stream().filter(p -> p.message().contains("Off-label")).count(),
				"navigation-only problems remain");
	}

	@Test
	void fixAllMakesSpecSampleCleaner() {
		Resource sample = HlomResources.loadLenient(TestResources.specSample("spec-sample1-course.xml"));
		DocumentRoot sampleRoot = HlomResources.getDocumentRoot(sample);
		HlomFixes.fixAll(sampleRoot, HlomValidator.validate(sample));
		assertTrue(HlomFixes.automaticFixes(HlomValidator.validate(sample)).isEmpty());
	}

	@Test
	void fixesAreUndoableWithChangeCommand() throws IOException {
		root.getLom().getMetaMetadata().getFirst().getMetadataSchema().clear();
		root.getLom().getHealthcareMetadata().clear();
		String before = HlomResources.toXml(resource);
		List<HlomProblem> problems = validate();
		BasicCommandStack stack = new BasicCommandStack();
		stack.execute(new ChangeCommand(resource) {
			@Override
			protected void doExecute() {
				HlomFixes.fixAll(root, problems);
			}
		});
		assertTrue(matching("metadata schema").isEmpty(), describe());
		assertTrue(matching("healthcareMetadata").isEmpty(), describe());
		stack.undo();
		assertEquals(before, HlomResources.toXml(resource));
		stack.redo();
		assertTrue(matching("metadata schema").isEmpty(), describe());
	}

	// ----------------------------------------------------------------------------------------------------

	private List<HlomProblem> validate() {
		return HlomValidator.validate(resource);
	}

	private List<HlomProblem> matching(String phrase) {
		Predicate<HlomProblem> matches = p -> p.message().contains(phrase);
		return validate().stream().filter(matches).toList();
	}

	private HlomProblem find(String phrase) {
		List<HlomProblem> problems = matching(phrase);
		assertFalse(problems.isEmpty(), "no problem containing \"" + phrase + "\" in " + describe());
		return problems.getFirst();
	}

	private String describe() {
		return TestResources.describe(validate());
	}

	private static List<String> labels(HlomProblem problem) {
		return HlomFixes.forProblem(problem).stream().map(HlomFix::label).toList();
	}

	/** Applies the only fix of a problem. */
	private void applyOnly(HlomProblem problem) {
		List<HlomFix> fixes = HlomFixes.forProblem(problem);
		assertEquals(1, fixes.size(), fixes.toString());
		fixes.getFirst().apply(root, problem);
	}

	private void apply(HlomProblem problem, String label) {
		HlomFix fix = HlomFixes.forProblem(problem).stream().filter(f -> f.label().equals(label)).findFirst()
				.orElseThrow();
		fix.apply(root, problem);
	}

	private static XMLGregorianCalendar date(String lexical) throws DatatypeConfigurationException {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(lexical);
	}
}
