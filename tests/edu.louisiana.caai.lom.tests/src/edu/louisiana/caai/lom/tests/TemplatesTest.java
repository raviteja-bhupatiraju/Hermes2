/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * Every document template must produce a valid, conformant Healthcare LOM record.
 */
class TemplatesTest {

	@TestFactory
	Stream<DynamicTest> templatesAreValid() {
		return HlomTemplates.all().stream()
				.map(template -> DynamicTest.dynamicTest(template.id(), () -> assertValid(template)));
	}

	@TestFactory
	Stream<DynamicTest> templatesUseTitleAndLanguage() {
		return HlomTemplates.all().stream()
				.map(template -> DynamicTest.dynamicTest(template.id(), () -> assertTitleAndLanguage(template)));
	}

	private static void assertValid(Template template) throws Exception {
		Resource resource = HlomResources.createResourceSet()
				.createResource(URI.createURI("memory:/" + template.id() + ".xml"));
		resource.getContents().add(template.create("Template Title", "en-US"));
		List<HlomProblem> problems = HlomValidator.validate(resource);
		assertTrue(TestResources.withSeverity(problems, Severity.ERROR).isEmpty(),
				template.id() + TestResources.describe(problems));
		assertTrue(TestResources.withSeverity(problems, Severity.WARNING).isEmpty(),
				template.id() + TestResources.describe(problems));

		// Round trip through XML.
		String xml = HlomResources.toXml(resource);
		DocumentRoot parsed = HlomResources.getDocumentRoot(HlomResources.parse(xml, URI.createURI("memory:/copy.xml")));
		assertNotNull(parsed);
		assertEquals(1, parsed.getLom().getHealthcareMetadata().size());
	}

	private static void assertTitleAndLanguage(Template template) {
		DocumentRoot root = template.create("A Title", "fr");
		assertEquals("A Title", root.getLom().getGeneral().getFirst().getTitle().getFirst().getString().getFirst().getValue());
		assertEquals("fr", root.getLom().getGeneral().getFirst().getLanguage().getFirst().getValue());
		root.getLom().eAllContents().forEachRemaining(object -> {
			if (object instanceof edu.louisiana.caai.lom.model.lom.LangString string) {
				assertEquals("fr", string.getLanguage(), template.id() + ": " + string.getValue());
			}
		});
		assertFalse(template.label().isBlank());
		assertFalse(template.description().isBlank());
		assertNotNull(template.iconId());
	}

	@Test
	void idsAreUniqueAndBlankFirst() {
		Set<String> ids = new HashSet<>();
		HlomTemplates.all().forEach(template -> assertTrue(ids.add(template.id()), template.id()));
		assertSame(HlomTemplates.BLANK, HlomTemplates.all().getFirst());
		assertSame(HlomTemplates.CME_COURSE, HlomTemplates.get("cme-course"));
		assertSame(HlomTemplates.BLANK, HlomTemplates.get("no-such-template"));
	}

	@Test
	void blankEqualsNewDocument() throws Exception {
		Resource blank = HlomResources.createResourceSet().createResource(URI.createURI("memory:/a.xml"));
		blank.getContents().add(HlomTemplates.BLANK.create("T", "en"));
		Resource plain = HlomResources.createResourceSet().createResource(URI.createURI("memory:/b.xml"));
		plain.getContents().add(HlomDocuments.createDocument("T", "en"));
		assertEquals(HlomResources.toXml(plain), HlomResources.toXml(blank));
	}

	@Test
	void cmeCourseContent() {
		DocumentRoot root = HlomTemplates.CME_COURSE.create("CME", "en-US");
		assertEquals("continuing professional development",
				Vocabularies.getValue(root.getLom().getEducational().getFirst().getContext().getFirst()));
		HealthcareMetadataType healthcare = root.getLom().getHealthcareMetadata().getFirst();
		assertEquals(1, healthcare.getHealthcareEducation().getCredits().size());
		assertEquals("physician", healthcare.getHealthcareEducation().getTargetAudience().getProfession().getFirst()
				.getString().getFirst().getValue());
	}

	@Test
	void liveWorkshopHasLocationAndTimes() {
		DocumentRoot root = HlomTemplates.LIVE_WORKSHOP.create("Workshop", "en-US");
		var education = root.getLom().getHealthcareMetadata().getFirst().getHealthcareEducation();
		assertNotNull(education.getActivityLocation().getCountry());
		assertTrue(education.getStartDateTime().compare(education.getEndDateTime()) < 0);
	}
}
