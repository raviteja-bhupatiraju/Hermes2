/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomRules;

/**
 * The Healthcare LOM rules that the schemas don't enforce.
 */
class RulesTest {

	private DocumentRoot root;
	private HealthcareEducationType education;

	@BeforeEach
	void createDocument() {
		root = HlomDocuments.createDocument("Title", "en-US");
		education = HealthcareFactory.eINSTANCE.createHealthcareEducationType();
		root.getLom().getHealthcareMetadata().getFirst().setHealthcareEducation(education);
	}

	@Test
	void templateHasNoErrorsOrWarnings() {
		List<HlomProblem> problems = HlomRules.check(root);
		assertTrue(problems.stream().allMatch(p -> p.severity() == Severity.INFO), TestResources.describe(problems));
	}

	@Test
	void missingHealthcareMetadataSchemaIsAnError() {
		root.getLom().getMetaMetadata().getFirst().getMetadataSchema()
				.removeIf(schema -> HlomDocuments.HEALTHCARE_METADATA_SCHEMA.equals(schema.getValue()));
		HlomProblem problem = find(Severity.ERROR, HlomDocuments.HEALTHCARE_METADATA_SCHEMA);
		assertSame(root.getLom().getMetaMetadata().getFirst(), problem.object());
		assertSame(Origin.RULES, problem.origin());
	}

	@Test
	void missingHealthcareMetadataIsAnError() {
		root.getLom().getHealthcareMetadata().clear();
		HlomProblem problem = find(Severity.ERROR, "healthcareMetadata");
		assertSame(root.getLom(), problem.object());
	}

	@Test
	void unsetOffLabelUseIsNotReported() {
		assertFalse(education.isSetOffLabelUse());
		assertTrue(matching(Severity.WARNING, "Off-label").isEmpty());
	}

	@Test
	void offLabelUseWithoutDescriptionIsAWarning() {
		education.setOffLabelUse(YesNoType.NO);
		assertTrue(matching(Severity.WARNING, "Off-label").isEmpty());
		education.setOffLabelUse(YesNoType.YES);
		assertSame(education, find(Severity.WARNING, "Off-label").object());
	}

	@Test
	void startAfterEndIsAnError() throws DatatypeConfigurationException {
		DatatypeFactory factory = DatatypeFactory.newInstance();
		education.setStartDateTime(factory.newXMLGregorianCalendar("2026-03-01T09:00:00"));
		education.setEndDateTime(factory.newXMLGregorianCalendar("2026-03-02T09:00:00"));
		assertTrue(matching(Severity.ERROR, "starts after it ends").isEmpty());
		education.setStartDateTime(factory.newXMLGregorianCalendar("2026-03-03T09:00:00"));
		assertSame(education, find(Severity.ERROR, "starts after it ends").object());
	}

	private List<HlomProblem> matching(Severity severity, String text) {
		return HlomRules.check(root).stream()
				.filter(p -> p.severity() == severity && p.message().contains(text))
				.toList();
	}

	private HlomProblem find(Severity severity, String text) {
		List<HlomProblem> found = matching(severity, text);
		assertFalse(found.isEmpty(), () -> "no " + severity + " containing \"" + text + "\" in"
				+ TestResources.describe(HlomRules.check(root)));
		return found.getFirst();
	}
}
