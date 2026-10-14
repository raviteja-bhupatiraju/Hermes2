/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * Vocabulary terms derived from the union value types of the HLOM schemas.
 */
class VocabulariesTest {

	private static final LomPackage LOM = LomPackage.eINSTANCE;

	@Test
	void roleCombinesLomAndHealthcareTerms() {
		for (EClass eClass : List.of(LOM.getRole(), LOM.getRoleVocab())) {
			assertTrue(Vocabularies.isVocabulary(eClass), eClass.getName());
			Term author = Vocabularies.findTerm(eClass, "author");
			assertNotNull(author, eClass.getName());
			assertEquals(Vocabularies.LOM_SOURCE, author.source());
			Term reviewer = Vocabularies.findTerm(eClass, "reviewer");
			assertNotNull(reviewer, eClass.getName());
			assertEquals(Vocabularies.HEALTHCARE_SOURCE, reviewer.source());
		}
	}

	@Test
	void lomTermsComeFirst() {
		List<Term> terms = Vocabularies.terms(LOM.getRole());
		int lastLom = -1;
		int firstHealthcare = Integer.MAX_VALUE;
		for (int i = 0; i < terms.size(); i++) {
			if (Vocabularies.LOM_SOURCE.equals(terms.get(i).source())) {
				lastLom = i;
			} else {
				firstHealthcare = Math.min(firstHealthcare, i);
			}
		}
		assertTrue(lastLom < firstHealthcare, terms::toString);
	}

	@Test
	void learningResourceTypeIncludesVirtualPatient() {
		Term term = Vocabularies.findTerm(LOM.getLearningResourceType(), "virtual patient");
		assertNotNull(term);
		assertEquals(Vocabularies.HEALTHCARE_SOURCE, term.source());
		Term exercise = Vocabularies.findTerm(LOM.getLearningResourceType(), "exercise");
		assertNotNull(exercise);
		assertEquals(Vocabularies.LOM_SOURCE, exercise.source());
	}

	@Test
	void structureHasOnlyLomTerms() {
		List<Term> terms = Vocabularies.terms(LOM.getStructure());
		assertFalse(terms.isEmpty());
		assertTrue(terms.stream().allMatch(t -> Vocabularies.LOM_SOURCE.equals(t.source())), terms::toString);
		assertNotNull(Vocabularies.findTerm(LOM.getStructure(), "hierarchical"));
	}

	@Test
	void unknownValuesAndNonVocabularies() {
		assertNull(Vocabularies.findTerm(LOM.getRole(), "no such role"));
		assertFalse(Vocabularies.isVocabulary(LOM.getGeneral()));
		assertTrue(Vocabularies.terms(LOM.getGeneral()).isEmpty());
	}
}
