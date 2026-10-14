/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * Recording the author of a new document ({@link HlomTemplates#addAuthor}).
 */
class AuthorTest {

	private static final String VCARD = "BEGIN:VCARD\nVERSION:3.0\nFN:Jane Smith\nORG:CAAI\nEND:VCARD";

	@Test
	void addsAuthorAndCreatorToBlankRecord() {
		DocumentRoot root = HlomTemplates.BLANK.create("Test", "en-US");
		HlomTemplates.addAuthor(root, VCARD, "2026-10-12");
		Lom lom = root.getLom();

		assertEquals(1, lom.getLifeCycle().size());
		assertEquals("author", Vocabularies.getValue(lom.getLifeCycle().getFirst().getContribute().getFirst().getRole().getFirst()));
		assertEquals(VCARD, lom.getLifeCycle().getFirst().getContribute().getFirst().getEntity().getFirst().getValue());
		assertEquals("creator",
				Vocabularies.getValue(lom.getMetaMetadata().getFirst().getContribute().getFirst().getRole().getFirst()));
		// The life cycle follows the general category.
		assertEquals(LomPackage.eINSTANCE.getLom_General(), lom.getGroup().getEStructuralFeature(0));
		assertEquals(LomPackage.eINSTANCE.getLom_LifeCycle(), lom.getGroup().getEStructuralFeature(1));
		assertNoErrors(root);
	}

	@Test
	void prependsAuthorToExistingLifeCycle() {
		DocumentRoot root = HlomTemplates.CME_COURSE.create("Course", "en-US");
		int contributors = root.getLom().getLifeCycle().getFirst().getContribute().size();
		HlomTemplates.addAuthor(root, VCARD, "2026-10-12");
		assertEquals(contributors + 1, root.getLom().getLifeCycle().getFirst().getContribute().size());
		assertEquals(VCARD, root.getLom().getLifeCycle().getFirst().getContribute().getFirst().getEntity().getFirst().getValue());
		assertNoErrors(root);
	}

	private static void assertNoErrors(DocumentRoot root) {
		Resource resource = HlomResources.createResourceSet().createResource(URI.createURI("memory:/author.xml"));
		resource.getContents().add(root);
		List<HlomProblem> errors = HlomValidator.validate(resource).stream()
				.filter(p -> p.severity() == HlomProblem.Severity.ERROR).toList();
		assertTrue(errors.isEmpty(), () -> "Unexpected errors: " + errors);
	}
}
