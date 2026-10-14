/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomCompleteness;
import edu.louisiana.caai.lom.model.support.HlomCompleteness.Category;
import edu.louisiana.caai.lom.model.support.HlomCompleteness.Item;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * The completeness assessment shown on the overview page.
 */
class CompletenessTest {

	private static final String COURSE = "insulin-pump-therapy-course.xml";
	private static final String IMAGE = "medical-image-asset.xml";

	@Test
	void emptyTemplateScoresLow() {
		HlomCompleteness completeness = HlomCompleteness.assess(HlomDocuments.createDocument(null, "en-US"));
		assertTrue(completeness.score() < 30, "score " + completeness.score());
		List<String> next = labels(completeness.nextSteps(5));
		assertTrue(next.containsAll(List.of("Title", "Description", "Keywords")), next.toString());
		assertTrue(item(completeness, "LOMv1.0 schema").filled());
		assertTrue(item(completeness, "HEALTHCARE_LOMv1 schema").filled());
		assertTrue(item(completeness, "Language").filled());
	}

	@Test
	void templateIsNeitherOfferingNorAsset() {
		HlomCompleteness completeness = HlomCompleteness.assess(HlomDocuments.createDocument("Title", "en-US"));
		assertFalse(completeness.isEducationalOffering());
		assertFalse(completeness.isMediaAsset());
		assertFalse(completeness.category(HlomCompleteness.HEALTHCARE_EDUCATION).applicable());
		assertFalse(completeness.category(HlomCompleteness.HEALTHCARE_ASSET).applicable());
		assertEquals(100, completeness.category(HlomCompleteness.HEALTHCARE_ASSET).score());
		assertTrue(item(completeness, "Title").filled());
	}

	@Test
	void missingDocumentScoresZero() {
		assertEquals(0, HlomCompleteness.assess((DocumentRoot) null).score());
	}

	@TestFactory
	Stream<DynamicTest> samplesScoreHigherThanTemplate() {
		int template = HlomCompleteness.assess(HlomDocuments.createDocument("Title", "en-US")).score();
		return TestResources.SAMPLES.stream().map(name -> DynamicTest.dynamicTest(name, () -> {
			HlomCompleteness completeness = HlomCompleteness.assess(sample(name));
			assertTrue(completeness.score() >= 60 && completeness.score() > template,
					name + " score " + completeness.score() + " (template " + template + ")");
			for (Category category : completeness.categories()) {
				assertTrue(category.score() >= 0 && category.score() <= 100, category.label());
			}
		}));
	}

	@Test
	void courseIsAnEducationalOffering() throws IOException {
		HlomCompleteness completeness = HlomCompleteness.assess(sample(COURSE));
		assertTrue(completeness.isEducationalOffering());
		assertFalse(completeness.isMediaAsset());
		Category education = completeness.category(HlomCompleteness.HEALTHCARE_EDUCATION);
		assertTrue(education.applicable());
		assertTrue(item(completeness, "Target audience").filled());
		assertTrue(item(completeness, "Credit details").filled());
		assertTrue(item(completeness, "Commercial support disclosed").filled());
		assertTrue(item(completeness, "Financial relationships disclosed").filled());
		assertTrue(item(completeness, "Expiration date").filled());
		assertTrue(item(completeness, "Terminology-coded keywords").filled());
		assertTrue(item(completeness, "Objectives or competencies").filled());
		// The sample leaves off-label use unanswered.
		Item offLabel = item(completeness, "Off-label use disclosed");
		assertFalse(offLabel.filled());
		assertEquals(HlomCompleteness.HEALTHCARE_EDUCATION, offLabel.pageId());
		assertTrue(completeness.nextSteps(10).contains(offLabel));
	}

	@Test
	void imageIsAMediaAsset() throws IOException {
		HlomCompleteness completeness = HlomCompleteness.assess(sample(IMAGE));
		assertTrue(completeness.isMediaAsset());
		assertFalse(completeness.isEducationalOffering());
		assertFalse(completeness.category(HlomCompleteness.HEALTHCARE_EDUCATION).applicable());
		assertTrue(item(completeness, "Annotated").filled());
		assertTrue(item(completeness, "Image dimensions").filled());
		assertTrue(labels(completeness.nextSteps(20)).contains("Medical image type"));
		// Learning time only matters for educational offerings.
		assertNull(find(completeness, "Typical learning time"));
	}

	@Test
	void professionalContextMakesAnOffering() throws IOException {
		DocumentRoot root = parse("""
				<educational><context><source>HEALTHCARE_LOMv1</source>\
				<value>continuing professional development</value></context></educational>""");
		HlomCompleteness completeness = HlomCompleteness.assess(root);
		assertTrue(completeness.isEducationalOffering());
		assertFalse(item(completeness, "Target audience").filled());
		assertNotNull(find(completeness, "Typical learning time"));
	}

	@Test
	void mediaResourceTypeMakesAnAsset() throws IOException {
		DocumentRoot root = parse("""
				<educational><learningResourceType><source>HEALTHCARE_LOMv1</source>\
				<value>video</value></learningResourceType></educational>""");
		HlomCompleteness completeness = HlomCompleteness.assess(root);
		assertTrue(completeness.isMediaAsset());
		assertFalse(item(completeness, "Medical image type").filled());
	}

	@Test
	void creditDetailsOnlyWhenCreditsAvailable() {
		DocumentRoot root = HlomDocuments.createDocument("Title", "en-US");
		HealthcareEducationType education = HealthcareFactory.eINSTANCE.createHealthcareEducationType();
		root.getLom().getHealthcareMetadata().getFirst().setHealthcareEducation(education);
		assertFalse(item(HlomCompleteness.assess(root), "Credits available").filled());
		assertNull(find(HlomCompleteness.assess(root), "Credit details"));

		education.setCreditsAvailable(YesNoType.NO);
		assertTrue(item(HlomCompleteness.assess(root), "Credits available").filled());
		assertNull(find(HlomCompleteness.assess(root), "Credit details"));

		education.setCreditsAvailable(YesNoType.YES);
		assertFalse(item(HlomCompleteness.assess(root), "Credit details").filled());
	}

	@Test
	void disclosuresMustBeAnsweredAndExplained() {
		DocumentRoot root = HlomDocuments.createDocument("Title", "en-US");
		HealthcareEducationType education = HealthcareFactory.eINSTANCE.createHealthcareEducationType();
		root.getLom().getHealthcareMetadata().getFirst().setHealthcareEducation(education);
		assertFalse(item(HlomCompleteness.assess(root), "Off-label use disclosed").filled());
		education.setOffLabelUse(YesNoType.YES);
		assertFalse(item(HlomCompleteness.assess(root), "Off-label use disclosed").filled());
		education.setOffLabelUse(YesNoType.NO);
		assertTrue(item(HlomCompleteness.assess(root), "Off-label use disclosed").filled());
	}

	@Test
	void uncodedKeywordsAreRecommendedToBeCoded() throws IOException {
		DocumentRoot root = parse("");
		root.getLom().getGeneral().getFirst().getKeyword().clear();
		DocumentRoot coded = parse("");
		assertFalse(item(HlomCompleteness.assess(root), "Terminology-coded keywords").filled());
		assertTrue(item(HlomCompleteness.assess(coded), "Terminology-coded keywords").filled());
		DocumentRoot plain = parseGeneral("<keyword><string language=\"en\">insulin</string></keyword>");
		assertTrue(item(HlomCompleteness.assess(plain), "Keywords").filled());
		assertFalse(item(HlomCompleteness.assess(plain), "Terminology-coded keywords").filled());
	}

	@Test
	void nextStepsAreOrderedByWeightAndLimited() {
		HlomCompleteness completeness = HlomCompleteness.assess(HlomDocuments.createDocument(null, "en-US"));
		List<Item> steps = completeness.nextSteps(4);
		assertEquals(4, steps.size());
		for (int i = 1; i < steps.size(); i++) {
			assertTrue(steps.get(i - 1).weight() >= steps.get(i).weight(), steps.toString());
		}
		assertTrue(steps.stream().noneMatch(Item::filled));
		assertTrue(completeness.nextSteps(0).isEmpty());
		assertEquals(completeness.items().stream().filter(i -> !i.filled()).count(),
				completeness.nextSteps(1000).size());
	}

	@Test
	void assessmentDoesNotModifyTheDocument() throws IOException {
		DocumentRoot root = sample(COURSE);
		DocumentRoot copy = EcoreUtil.copy(root);
		HlomCompleteness.assess(root);
		assertTrue(EcoreUtil.equals(copy, root));
	}

	private static DocumentRoot sample(String name) throws IOException {
		return HlomResources.getDocumentRoot(HlomResources.load(TestResources.sample(name)));
	}

	/** A document with a coded keyword in general, followed by the given content. */
	private static DocumentRoot parse(String content) throws IOException {
		return parseDocument("<general><keyword hx:source=\"MeSH\" hx:id=\"D007328\"><string language=\"en\">insulin"
				+ "</string></keyword></general>" + content);
	}

	private static DocumentRoot parseGeneral(String generalContent) throws IOException {
		return parseDocument("<general>" + generalContent + "</general>");
	}

	private static DocumentRoot parseDocument(String content) throws IOException {
		String xml = "<lom xmlns=\"http://ltsc.ieee.org/xsd/LOM\" xmlns:hx=\"http://ns.medbiq.org/lom/extend/v1/\">"
				+ content + "<hx:healthcareMetadata/></lom>";
		DocumentRoot root = HlomResources.getDocumentRoot(HlomResources.parse(xml, URI.createURI("memory:/test.xml")));
		assertNotNull(root);
		return root;
	}

	private static List<String> labels(List<Item> items) {
		return items.stream().map(Item::label).toList();
	}

	private static Item find(HlomCompleteness completeness, String label) {
		return completeness.items().stream().filter(i -> i.label().equals(label)).findFirst().orElse(null);
	}

	private static Item item(HlomCompleteness completeness, String label) {
		Item item = find(completeness, label);
		assertNotNull(item, () -> "no item " + label + " in " + labels(completeness.items()));
		return item;
	}
}
