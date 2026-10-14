/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;

/**
 * Checks how the samples printed in the specification (Appendix 3) behave as transcribed, including their
 * errors.
 */
class SpecSampleTest {

	@Test
	void courseSampleIsRejectedStrictly() {
		assertThrows(IOException.class,
				() -> HlomResources.load(TestResources.specSample("spec-sample1-course.xml")));
	}

	@Test
	void courseSampleLoadsLeniently() {
		Resource resource = HlomResources.loadLenient(TestResources.specSample("spec-sample1-course.xml"));
		assertFalse(resource.getErrors().isEmpty());
		assertTrue(resource.getErrors().stream().anyMatch(e -> e.getMessage().contains("Credits")),
				() -> resource.getErrors().toString());
		DocumentRoot root = HlomResources.getDocumentRoot(resource);
		assertNotNull(root, "the valid part of the document is still available");
		assertEquals(1, root.getLom().getHealthcareMetadata().size());
	}

	/** The yes/no and other enumerations are xsd:string based, so the printed line breaks make them invalid. */
	@Test
	void multipleCreditsSampleLoadsLenientlyWithErrors() {
		Resource resource = HlomResources
				.loadLenient(TestResources.specSample("spec-sample2-multiple-credits.xml"));
		assertFalse(resource.getErrors().isEmpty());
		assertTrue(resource.getErrors().stream().allMatch(e -> e.getLine() > 0 && e.getMessage().contains("not legal")),
				() -> resource.getErrors().toString());
		assertTrue(resource.getErrors().stream().anyMatch(e -> e.getMessage().contains("\n")),
				() -> resource.getErrors().toString());
		assertNotNull(HlomResources.getDocumentRoot(resource));
	}

	@Test
	void assetSampleIsValidAsPrinted() throws IOException {
		Resource resource = HlomResources.load(TestResources.specSample("spec-sample3-asset.xml"));
		assertTrue(resource.getErrors().isEmpty(), () -> resource.getErrors().toString());
		List<HlomProblem> errors = TestResources.withSeverity(HlomValidator.validate(resource), Severity.ERROR);
		assertTrue(errors.isEmpty(), () -> TestResources.describe(errors));
	}
}
