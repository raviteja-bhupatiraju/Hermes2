/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.terminology.TerminologySupport;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;

/**
 * Coded keywords and taxon paths from the offline terminologies, as undoable commands.
 */
class TerminologyCommandsTest {

	private static final LomPackage LOM = LomPackage.eINSTANCE;

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain domain;
	private BasicCommandStack stack;
	private Lom lom;

	@BeforeEach
	void createDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		stack = new BasicCommandStack();
		domain = new AdapterFactoryEditingDomain(adapterFactory, stack, HlomResources.createResourceSet());
		Resource resource = domain.getResourceSet().createResource(URI.createURI("memory:/test.xml"));
		DocumentRoot root = HlomDocuments.createDocument("Terminology", "en");
		lom = root.getLom();
		resource.getContents().add(root);
	}

	@AfterEach
	void dispose() {
		adapterFactory.dispose();
	}

	@Test
	void codedKeywordsAreAddedOnceAndUndoable() {
		var concepts = List.of(Terminologies.ICD_10_CM.get("A41.9"), Terminologies.ICD_10_CM.get("R65.20"));
		var command = TerminologySupport.addKeywords(domain, lom, ModelPath.of(LOM.getLom_General()),
				LOM.getGeneral_Keyword(), Terminologies.ICD_10_CM, concepts, "en");
		stack.execute(command);
		List<Keyword> keywords = lom.getGeneral().getFirst().getKeyword();
		assertEquals(2, keywords.size());
		assertEquals("ICD-10-CM", keywords.getFirst().getSource());
		assertEquals("A41.9", keywords.getFirst().getId());
		assertEquals("Sepsis, unspecified organism", keywords.getFirst().getString().getFirst().getValue());
		assertNull(TerminologySupport.addKeywords(domain, lom, ModelPath.of(LOM.getLom_General()),
				LOM.getGeneral_Keyword(), Terminologies.ICD_10_CM, concepts, "en"), "already present");
		stack.undo();
		assertTrue(lom.getGeneral().getFirst().getKeyword().isEmpty());
	}

	@Test
	void taxonPathIsTheConceptsHierarchy() {
		Classification classification = LomFactory.eINSTANCE.createClassification();
		TaxonPath path = LomFactory.eINSTANCE.createTaxonPath();
		path.getTaxon().add(LomFactory.eINSTANCE.createTaxon());
		classification.getTaxonPath().add(path);
		lom.getClassification().add(classification);

		stack.execute(TerminologySupport.fillTaxonPath(domain, path, Terminologies.BLOOM,
				Terminologies.BLOOM.get("4.1"), "en"));
		assertEquals("Bloom's Taxonomy (revised)", path.getSource().getFirst().getString().getFirst().getValue());
		assertEquals(2, path.getTaxon().size(), "Analyze > Differentiating");
		Taxon first = path.getTaxon().getFirst();
		assertEquals("4", first.getId().getFirst().getValue());
		assertEquals("Analyze", first.getEntry().getFirst().getString().getFirst().getValue());
		assertEquals("4.1", path.getTaxon().get(1).getId().getFirst().getValue());
		assertEquals(Terminologies.BLOOM, TerminologySupport.terminologyOf(path));

		stack.undo();
		assertTrue(path.getSource().isEmpty());
		assertEquals(1, path.getTaxon().size(), "the original (empty) taxon is back");
	}
}
