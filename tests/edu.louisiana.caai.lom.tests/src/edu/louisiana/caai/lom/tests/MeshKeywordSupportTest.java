/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.mesh.MeshKeywordSupport;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;
import edu.louisiana.caai.lom.model.support.mesh.MeshTermValidator;

/**
 * Adding MeSH descriptors as keywords ({@link MeshKeywordSupport}) and the offline MeSH ID check
 * ({@link MeshTermValidator}).
 */
class MeshKeywordSupportTest {

	private static final MeshTerm MI = new MeshTerm("D009203", "Myocardial Infarction");
	private static final MeshTerm DIABETES = new MeshTerm("D003920", "Diabetes Mellitus");

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain domain;
	private BasicCommandStack stack;
	private Resource resource;
	private DocumentRoot root;
	private Lom lom;

	@BeforeEach
	void createDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		stack = new BasicCommandStack();
		domain = new AdapterFactoryEditingDomain(adapterFactory, stack, HlomResources.createResourceSet());
		resource = domain.getResourceSet().createResource(URI.createURI("memory:/mesh.xml"));
		root = LomFactory.eINSTANCE.createDocumentRoot();
		lom = LomFactory.eINSTANCE.createLom();
		root.setLom(lom);
		resource.getContents().add(root);
	}

	@AfterEach
	void dispose() {
		adapterFactory.dispose();
	}

	@Test
	void createsGeneralAndKeywordsInOneUndoableCommand() throws IOException {
		List<Keyword> added = MeshKeywordSupport.addKeywords(domain, root, List.of(MI, DIABETES, MI), "en-US");
		assertEquals(2, added.size(), "duplicates in the list are skipped");
		assertEquals(1, lom.getGeneral().size());
		List<Keyword> keywords = lom.getGeneral().getFirst().getKeyword();
		assertEquals(2, keywords.size());
		Keyword keyword = keywords.getFirst();
		assertEquals("MeSH", keyword.getSource());
		assertEquals("D009203", keyword.getId());
		assertEquals("Myocardial Infarction", keyword.getString().getFirst().getValue());
		assertEquals("en-US", keyword.getString().getFirst().getLanguage());

		String xml = HlomResources.toXml(resource);
		assertTrue(xml.contains(":source=\"MeSH\""), xml);
		assertTrue(xml.contains(":id=\"D009203\""), xml);
		assertTrue(xml.contains(">Myocardial Infarction<"), xml);

		stack.undo();
		assertTrue(lom.getGeneral().isEmpty(), "one undo removes everything");
		stack.redo();
		assertEquals(2, lom.getGeneral().getFirst().getKeyword().size());
	}

	@Test
	void addsToExistingGeneralAndSkipsExistingIds() {
		General general = LomFactory.eINSTANCE.createGeneral();
		lom.getGeneral().add(general);
		general.getKeyword().add(MeshKeywordSupport.createKeyword(MI, "en"));
		assertEquals(Set.of("D009203"), MeshKeywordSupport.existingIds(lom));

		assertNull(MeshKeywordSupport.createAddCommand(domain, lom, List.of(MI), "en"), "nothing new to add");
		List<Keyword> added = MeshKeywordSupport.addKeywords(domain, lom, List.of(MI, DIABETES), "en");
		assertEquals(1, added.size());
		assertEquals("D003920", added.getFirst().getId());
		assertSame(general, added.getFirst().eContainer());
		assertEquals(2, general.getKeyword().size());
		assertEquals("Add MeSH Keyword", stack.getUndoCommand().getLabel());
	}

	@Test
	void defaultLanguageComesFromTheDocument() {
		assertEquals("en", MeshKeywordSupport.defaultLanguage(root));
		General general = LomFactory.eINSTANCE.createGeneral();
		LanguageId language = LomFactory.eINSTANCE.createLanguageId();
		language.setValue("fr-CA");
		general.getLanguage().add(language);
		lom.getGeneral().add(general);
		assertEquals("fr-CA", MeshKeywordSupport.defaultLanguage(root));
		MeshKeywordSupport.addKeywords(domain, root, List.of(MI), null);
		assertEquals("fr-CA", general.getKeyword().getFirst().getString().getFirst().getLanguage());
	}

	@Test
	void validatorWarnsAboutInvalidMeshIds() {
		General general = LomFactory.eINSTANCE.createGeneral();
		lom.getGeneral().add(general);
		general.getKeyword().add(MeshKeywordSupport.createKeyword(MI, "en"));
		general.getKeyword().add(MeshKeywordSupport.createKeyword(new MeshTerm("C14.280", "Heart Diseases"), "en"));
		Keyword noId = MeshKeywordSupport.createKeyword(DIABETES, "en");
		noId.setId(null);
		noId.setSource("mesh");
		general.getKeyword().add(noId);
		Keyword other = MeshKeywordSupport.createKeyword(new MeshTerm("X1", "Cardiology"), "en");
		other.setSource("Local");
		general.getKeyword().add(other);

		List<HlomProblem> problems = MeshTermValidator.checkKeywordIds(root);
		assertEquals(2, problems.size(), problems.toString());
		assertTrue(problems.stream().allMatch(p -> p.severity() == Severity.WARNING));
		assertTrue(problems.getFirst().message().contains("C14.280"), problems.getFirst().message());
		assertTrue(problems.get(1).message().contains("no descriptor ID"), problems.get(1).message());
		assertSame(noId, problems.get(1).object());
		assertTrue(MeshTermValidator.checkKeywordIds(null).isEmpty());
	}
}
