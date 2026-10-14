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

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * The undoable model edits behind the form pages ({@link ModelCommands}, {@link ModelPath}).
 */
class ModelCommandsTest {

	private static final LomPackage LOM = LomPackage.eINSTANCE;
	private static final HealthcarePackage HX = HealthcarePackage.eINSTANCE;

	/** lom/general/title/string */
	private static final ModelPath TITLE_STRING = ModelPath.of(LOM.getLom_General(), LOM.getGeneral_Title(),
			LOM.getLanguageString_String());

	/** lom/healthcareMetadata/healthcareEducation */
	private static final ModelPath EDUCATION = ModelPath.of(LOM.getLom_HealthcareMetadata(),
			HX.getHealthcareMetadataType_HealthcareEducation());

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain domain;
	private CommandStack stack;
	private Lom lom;
	private HealthcareMetadataType healthcare;

	@BeforeEach
	void createDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		stack = new BasicCommandStack();
		domain = new AdapterFactoryEditingDomain(adapterFactory, stack, HlomResources.createResourceSet());
		Resource resource = domain.getResourceSet().createResource(URI.createURI("memory:/test.xml"));
		DocumentRoot root = LomFactory.eINSTANCE.createDocumentRoot();
		lom = LomFactory.eINSTANCE.createLom();
		root.setLom(lom);
		healthcare = HealthcareFactory.eINSTANCE.createHealthcareMetadataType();
		lom.getHealthcareMetadata().add(healthcare);
		resource.getContents().add(root);
	}

	@AfterEach
	void dispose() {
		adapterFactory.dispose();
	}

	@Test
	void ensureCreatesMissingChainInOneCommand() {
		ModelPath.Ensured ensured = TITLE_STRING.ensure(domain, lom);
		assertTrue(ensured.isNew());
		assertTrue(ensured.target() instanceof LangString);
		assertTrue(lom.getGeneral().isEmpty(), "nothing is attached before the command executes");

		assertTrue(ModelCommands.execute(domain, ensured.command()));
		assertSame(ensured.target(), TITLE_STRING.resolve(lom));
		assertEquals(4, TITLE_STRING.resolveChain(lom).size());

		ModelPath.Ensured again = TITLE_STRING.ensure(domain, lom);
		assertFalse(again.isNew());
		assertSame(ensured.target(), again.target());

		stack.undo();
		assertTrue(lom.getGeneral().isEmpty());
		assertFalse(stack.canUndo());
	}

	@Test
	void setValueCreatesElementsAndUndoRemovesThem() {
		execute(ModelCommands.setValue(domain, lom, TITLE_STRING, LOM.getLangString_Value(), "Hello"));
		assertEquals("Hello", ModelCommands.getValue(lom, TITLE_STRING, LOM.getLangString_Value()));
		assertEquals(1, lom.getGeneral().size());
		assertEquals(1, lom.getGeneral().getFirst().getTitle().size());

		execute(ModelCommands.setValue(domain, lom, TITLE_STRING, LOM.getLangString_Value(), "World"));
		assertEquals("World", ModelCommands.getValue(lom, TITLE_STRING, LOM.getLangString_Value()));
		assertEquals(1, lom.getGeneral().getFirst().getTitle().getFirst().getString().size());

		assertNull(ModelCommands.setValue(domain, lom, TITLE_STRING, LOM.getLangString_Value(), "World"),
				"setting the current value is a no-op");

		stack.undo();
		assertEquals("Hello", ModelCommands.getValue(lom, TITLE_STRING, LOM.getLangString_Value()));
		stack.undo();
		assertTrue(lom.getGeneral().isEmpty(), "one undo removes all created elements");
		assertFalse(stack.canUndo());

		stack.redo();
		assertEquals("Hello", ModelCommands.getValue(lom, TITLE_STRING, LOM.getLangString_Value()));
	}

	@Test
	void clearingValuePrunesEmptyElements() {
		execute(ModelCommands.setValue(domain, lom, TITLE_STRING, LOM.getLangString_Value(), "Hello"));
		execute(ModelCommands.setValue(domain, lom, TITLE_STRING, LOM.getLangString_Value(), ""));
		assertTrue(lom.getGeneral().isEmpty(), "empty string/title/general are removed");
		assertNotNull(lom.eContainer(), "lom stays");

		stack.undo();
		assertEquals("Hello", ModelCommands.getValue(lom, TITLE_STRING, LOM.getLangString_Value()));

		assertNull(ModelCommands.setValue(domain, lom,
				ModelPath.of(LOM.getLom_Educational(), LOM.getEducational_Description(),
						LOM.getLanguageString_String()),
				LOM.getLangString_Value(), null), "clearing a missing value is a no-op");
	}

	@Test
	void clearingValueKeepsNonEmptyParents() {
		DocumentRoot root = HlomDocuments.createDocument("Hello", "en-US");
		domain.getResourceSet().getResources().getFirst().getContents().set(0, root);
		Lom templateLom = root.getLom();

		execute(ModelCommands.setValue(domain, templateLom, TITLE_STRING, LOM.getLangString_Value(), null));
		assertNull(ModelCommands.getValue(templateLom, TITLE_STRING, LOM.getLangString_Value()));
		assertEquals(1, templateLom.getGeneral().size(), "general still holds the language");
		assertEquals(1, templateLom.getGeneral().getFirst().getLanguage().size());
	}

	@Test
	void clearingNeverPrunesHealthcareMetadata() {
		execute(ModelCommands.setValue(domain, lom, EDUCATION, HX.getHealthcareEducationType_Contact(), "Jane"));
		assertNotNull(healthcare.getHealthcareEducation());
		assertEquals("Jane", healthcare.getHealthcareEducation().getContact());

		execute(ModelCommands.setValue(domain, lom, EDUCATION, HX.getHealthcareEducationType_Contact(), null));
		assertNull(healthcare.getHealthcareEducation(), "the empty education element is removed");
		assertEquals(1, lom.getHealthcareMetadata().size(), "healthcareMetadata is required and stays");
		assertSame(healthcare, lom.getHealthcareMetadata().getFirst());
	}

	@Test
	void enumerationIsUnsetByNull() {
		execute(ModelCommands.setValue(domain, lom, EDUCATION, HX.getHealthcareEducationType_Contact(), "Jane"));
		execute(ModelCommands.setValue(domain, lom, EDUCATION, HX.getHealthcareEducationType_OffLabelUse(),
				YesNoType.YES));
		HealthcareEducationType education = healthcare.getHealthcareEducation();
		assertTrue(education.isSetOffLabelUse());
		assertEquals(YesNoType.YES,
				ModelCommands.getValue(lom, EDUCATION, HX.getHealthcareEducationType_OffLabelUse()));

		execute(ModelCommands.setValue(domain, lom, EDUCATION, HX.getHealthcareEducationType_OffLabelUse(), null));
		assertFalse(education.isSetOffLabelUse());
		assertNull(ModelCommands.getValue(lom, EDUCATION, HX.getHealthcareEducationType_OffLabelUse()),
				"an unset enumeration reads as null, not as its default literal");
		assertSame(education, healthcare.getHealthcareEducation(), "education still holds the contact");

		stack.undo();
		assertTrue(education.isSetOffLabelUse());
		assertEquals(YesNoType.YES, education.getOffLabelUse());
	}

	@Test
	void setVocabularySetsSourceAndValue() {
		ModelPath structure = ModelPath.of(LOM.getLom_General(), LOM.getGeneral_Structure());
		Term hierarchical = Vocabularies.findTerm(LOM.getStructure(), "hierarchical");
		execute(ModelCommands.setVocabulary(domain, lom, structure, hierarchical));
		EObject vocab = structure.resolve(lom);
		assertEquals("hierarchical", Vocabularies.getValue(vocab));
		assertEquals(Vocabularies.LOM_SOURCE, Vocabularies.getSource(vocab));

		assertNull(ModelCommands.setVocabulary(domain, lom, structure, hierarchical), "unchanged term is a no-op");

		execute(ModelCommands.setVocabulary(domain, lom, structure,
				Vocabularies.findTerm(LOM.getStructure(), "linear")));
		assertSame(vocab, structure.resolve(lom), "the existing element is reused");
		assertEquals("linear", Vocabularies.getValue(vocab));
		assertEquals(1, ((List<?>) vocab.eGet(Vocabularies.valueReference(vocab.eClass()))).size());
		assertEquals(1, ((List<?>) vocab.eGet(Vocabularies.sourceReference(vocab.eClass()))).size());

		stack.undo();
		assertEquals("hierarchical", Vocabularies.getValue(vocab));
		assertEquals(Vocabularies.LOM_SOURCE, Vocabularies.getSource(vocab));

		execute(ModelCommands.setVocabulary(domain, lom, structure, null));
		assertTrue(lom.getGeneral().isEmpty(), "removing the only structure prunes general");
	}

	@Test
	void setVocabularyUsesHealthcareSource() {
		ModelPath role = ModelPath.of(LOM.getLom_LifeCycle(), LOM.getLifeCycle_Contribute(),
				LOM.getContribute_Role());
		execute(ModelCommands.setVocabulary(domain, lom, role, Vocabularies.findTerm(LOM.getRole(), "reviewer")));
		EObject vocab = role.resolve(lom);
		assertEquals("reviewer", Vocabularies.getValue(vocab));
		assertEquals(Vocabularies.HEALTHCARE_SOURCE, Vocabularies.getSource(vocab));

		execute(ModelCommands.setVocabulary(domain, lom, role, Vocabularies.findTerm(LOM.getRole(), "author")));
		assertEquals("author", Vocabularies.getValue(vocab));
		assertEquals(Vocabularies.LOM_SOURCE, Vocabularies.getSource(vocab));
	}

	@Test
	void toggleVocabularyAddsAndRemovesTerms() {
		ModelPath educational = ModelPath.of(LOM.getLom_Educational());
		Term virtualPatient = Vocabularies.findTerm(LOM.getLearningResourceType(), "virtual patient");
		Term exercise = Vocabularies.findTerm(LOM.getLearningResourceType(), "exercise");

		execute(ModelCommands.toggleVocabulary(domain, lom, educational,
				LOM.getEducational_LearningResourceType(), virtualPatient, true));
		assertNull(ModelCommands.toggleVocabulary(domain, lom, educational,
				LOM.getEducational_LearningResourceType(), virtualPatient, true), "already present");
		execute(ModelCommands.toggleVocabulary(domain, lom, educational,
				LOM.getEducational_LearningResourceType(), exercise, true));
		assertEquals(List.of("virtual patient", "exercise"), ModelCommands.vocabularyValues(
				educational.resolve(lom), LOM.getEducational_LearningResourceType()));
		assertEquals(Vocabularies.HEALTHCARE_SOURCE,
				Vocabularies.getSource(lom.getEducational().getFirst().getLearningResourceType().getFirst()));

		execute(ModelCommands.toggleVocabulary(domain, lom, educational,
				LOM.getEducational_LearningResourceType(), virtualPatient, false));
		assertEquals(List.of("exercise"), ModelCommands.vocabularyValues(educational.resolve(lom),
				LOM.getEducational_LearningResourceType()));
		assertNull(ModelCommands.toggleVocabulary(domain, lom, educational,
				LOM.getEducational_LearningResourceType(), virtualPatient, false), "already absent");

		stack.undo();
		assertEquals(List.of("virtual patient", "exercise"), ModelCommands.vocabularyValues(
				educational.resolve(lom), LOM.getEducational_LearningResourceType()));
	}

	private void execute(Command command) {
		assertNotNull(command, "expected a command");
		assertTrue(ModelCommands.execute(domain, command), "command must be executable");
	}
}
