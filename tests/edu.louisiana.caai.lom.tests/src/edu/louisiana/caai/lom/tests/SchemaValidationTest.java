/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;

/**
 * Validation against the HLOM XML schemas and the mapping of schema problems back to model elements.
 */
class SchemaValidationTest {

	@Test
	void newDocumentTemplateIsValid() {
		DocumentRoot root = HlomDocuments.createDocument("T", "en-US");
		List<HlomProblem> errors = TestResources.withSeverity(HlomValidator.validate(attach(root)), Severity.ERROR);
		assertTrue(errors.isEmpty(), () -> TestResources.describe(errors));
	}

	@Test
	void missingCountryIsMappedToActivityLocation() {
		DocumentRoot root = HlomDocuments.createDocument("T", "en-US");
		HealthcareEducationType education = HealthcareFactory.eINSTANCE.createHealthcareEducationType();
		ActivityLocationType location = HealthcareFactory.eINSTANCE.createActivityLocationType();
		location.setCity("Lafayette");
		education.setActivityLocation(location);
		root.getLom().getHealthcareMetadata().getFirst().setHealthcareEducation(education);

		List<HlomProblem> problems = HlomValidator.validate(attach(root));
		HlomProblem problem = problems.stream()
				.filter(p -> p.origin() == Origin.SCHEMA && p.severity() == Severity.ERROR
						&& p.message().contains("Country"))
				.findFirst().orElse(null);
		assertNotNull(problem, () -> TestResources.describe(problems));
		assertSame(location, problem.object(), () -> "mapped to " + problem.object() + " at " + problem.path());
		assertTrue(problem.line() > 0, () -> "line " + problem.line());
		assertTrue(problem.path().endsWith("/activityLocation"), problem.path());
	}

	@Test
	void unknownElementIsMappedToItsParent() throws IOException {
		DocumentRoot root = HlomDocuments.createDocument("T", "en-US");
		String xml = HlomResources.toXml(attach(root));
		int general = xml.indexOf("<general>");
		assertTrue(general >= 0, xml);
		int insert = general + "<general>".length();
		String invalid = xml.substring(0, insert) + "<bogus/>" + xml.substring(insert);

		List<HlomProblem> problems = HlomValidator.validateSchema(invalid.getBytes(StandardCharsets.UTF_8), root);
		assertFalse(problems.isEmpty(), invalid);
		HlomProblem problem = problems.getFirst();
		assertSame(Severity.ERROR, problem.severity());
		assertSame(Origin.SCHEMA, problem.origin());
		assertTrue(problem.message().contains("bogus"), problem.message());
		assertSame(root.getLom().getGeneral().getFirst(), problem.object(), () -> String.valueOf(problem.object()));
		assertTrue(problem.line() > 0, () -> "line " + problem.line());
	}

	@Test
	void malformedXmlIsReportedNotThrown() {
		List<HlomProblem> problems = HlomValidator.validateSchema("<lom".getBytes(StandardCharsets.UTF_8), null);
		assertFalse(problems.isEmpty());
		assertSame(Severity.ERROR, problems.getFirst().severity());
	}

	/** Puts a document root into a resource, as the validator serializes it. */
	static Resource attach(DocumentRoot root) {
		Resource resource = HlomResources.createResourceSet().createResource(URI.createURI("memory:/test.xml"));
		resource.getContents().add(root);
		return resource;
	}
}
