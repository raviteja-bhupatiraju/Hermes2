/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.ui.ide.IDE;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.pages.AbstractLomPage;
import edu.louisiana.caai.lom.editor.pages.DocumentOverviewPage;
import edu.louisiana.caai.lom.editor.pages.SpecPage;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.editor.xml.XmlSourceEditor;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Paths;

/** Tests the editor with the documents of the MedBiquitous standards besides Healthcare LOM. */
class StandardsEditorTest extends AbstractUiTest {

	private Path newDocument(MedbiqDocumentType type, String title) throws Exception {
		Path file = tempDir.resolve(type.id() + ".xml");
		Files.writeString(file, type.newDocumentXml(title, "en"), StandardCharsets.UTF_8);
		return file;
	}

	@SuppressWarnings("unchecked")
	private static List<AbstractLomPage> formPages(HlomMetadataEditor editor) {
		return (List<AbstractLomPage>) field(editor, "formPages");
	}

	@Test
	void everyStandardOpensWithItsPages() throws Exception {
		MedbiqStandards.init();
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			HlomMetadataEditor editor = open(newDocument(type, "Opening " + type.id()));
			assertSame(type, editor.documentType(), type.id());
			List<AbstractLomPage> pages = formPages(editor);
			assertInstanceOf(DocumentOverviewPage.class, pages.getFirst(), type.id());
			assertTrue(pages.stream().anyMatch(SpecPage.class::isInstance), type.id());
			// Every page can be shown without changing the document.
			for (int i = 0; i < pages.size(); i++) {
				setActivePage(editor, i);
				dispatch(30);
			}
			setActivePage(editor, pageIndex(editor, "xml"));
			dispatch(50);
			setActivePage(editor, pageIndex(editor, "tree"));
			dispatch(50);
			assertFalse(editor.isDirty(), type.id() + " changed by viewing");
			editor.validateNow();
			// New documents are valid (they may lack recommended content: warnings and infos)
			assertTrue(editor.getProblems().stream()
					.noneMatch(p -> p.severity() == edu.louisiana.caai.lom.model.support.HlomProblem.Severity.ERROR),
					() -> type.id() + ": " + editor.getProblems());
			page().closeEditor(editor, false);
			dispatch(50);
		}
		assertNoDialogs();
	}

	/** The problems the XML page shows, once it has checked its text. */
	private static List<String> xmlProblems(HlomMetadataEditor editor) {
		editor.showPage("xml");
		XmlSourceEditor xml = XmlPageTest.xmlEditor(editor);
		waitUntil("the XML page checked", () -> xml.checkedProblems() != null, 15_000);
		return xml.checkedProblems();
	}

	/** The XML page reports schema errors of the document's standard (not of Healthcare LOM). */
	@Test
	void xmlPageChecksAgainstTheStandardsSchemas() throws Exception {
		MedbiqStandards.init();
		MedbiqDocumentType type = MedbiqStandards.TYPES.stream()
				.filter(t -> t.rootElement().equals("VirtualPatientData")).findFirst().orElseThrow();
		HlomMetadataEditor editor = open(newDocument(type, "XML check"));
		assertEquals(List.of(), xmlProblems(editor));

		XmlSourceEditor xml = XmlPageTest.xmlEditor(editor);
		XmlPageTest.replace(xml, "<VPDText ", "<VPDTextX ");
		XmlPageTest.replace(xml, "</VPDText>", "</VPDTextX>");
		waitUntil("the XML page checked", () -> xml.checkedProblems() != null, 15_000);
		List<String> problems = xml.checkedProblems();
		assertEquals(1, problems.size(), problems::toString);
		assertTrue(problems.getFirst().contains("VPDTextX"), problems.getFirst());
		page().closeEditor(editor, false);
		dispatch(50);
	}

	/**
	 * The problems of a document opened from outside the projects are markers (in the Problems view) while it is
	 * open; opening a marker shows the open editor.
	 */
	@Test
	void problemsOfOpenDocumentsAreMarkers() throws Exception {
		MedbiqStandards.init();
		MedbiqDocumentType type = MedbiqStandards.TYPES.stream()
				.filter(t -> t.rootElement().equals("VirtualPatientData")).findFirst().orElseThrow();
		Path file = tempDir.resolve("broken-vpd.xml");
		// PatientDemographics without its required id.
		Files.writeString(file, type.newDocumentXml("Markers", "en").replace("<PatientDemographics id=\"pd1\">",
				"<PatientDemographics>"), StandardCharsets.UTF_8);
		HlomMetadataEditor editor = open(file);
		editor.validateNow();
		int problems = editor.getProblems().size();
		assertTrue(problems > 0, "test premise: the document has problems");
		waitUntil("the problems are markers", () -> markers(file).size() == problems, TIMEOUT);
		IMarker marker = markers(file).getFirst();
		assertEquals("broken-vpd.xml", marker.getResource().getName());
		assertEquals(IMarker.SEVERITY_ERROR, marker.getAttribute(IMarker.SEVERITY, -1));

		// Opening the marker, as the Problems view does, shows the editor that is open.
		assertSame(editor, IDE.openEditor(page(), marker));
		assertEquals(1, page().getEditorReferences().length);

		page().closeEditor(editor, false);
		waitUntil("the markers are removed", () -> markers(file).isEmpty(), TIMEOUT);
		assertTrue(Files.exists(file), "removing the link leaves the document");
		assertEquals(0, ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toUri(),
				IContainer.INCLUDE_HIDDEN).length, "the link is removed");
	}

	private static List<IMarker> markers(Path file) {
		Path document = HermesWorkspace.canonical(file);
		try {
			return Stream.of(ResourcesPlugin.getWorkspace().getRoot().findMarkers(HermesWorkspace.PROBLEM_MARKER,
					true, IResource.DEPTH_INFINITE))
					.filter(marker -> document.equals(HermesWorkspace.location(marker.getResource())))
					.toList();
		} catch (CoreException e) {
			throw new IllegalStateException(e);
		}
	}

	@Test
	void everySampleShowsAllPagesWithoutErrors() throws Exception {
		MedbiqStandards.init();
		org.osgi.framework.Bundle bundle = org.osgi.framework.FrameworkUtil.getBundle(MedbiqStandards.class);
		java.util.Enumeration<java.net.URL> entries = bundle.findEntries("samples", "*.xml", true);
		int opened = 0;
		for (java.net.URL entry : java.util.Collections.list(entries)) {
			String name = entry.getPath().substring(entry.getPath().lastIndexOf('/') + 1);
			Path file = tempDir.resolve(opened + "-" + name);
			try (java.io.InputStream in = entry.openStream()) {
				Files.copy(in, file);
			}
			HlomMetadataEditor editor = open(file);
			assertInstanceOf(MedbiqDocumentType.class, editor.documentType(), name);
			List<AbstractLomPage> pages = formPages(editor);
			for (int i = 0; i < pages.size(); i++) {
				setActivePage(editor, i);
				dispatch(30);
			}
			editor.validateNow();
			// The samples are valid without any problem, also as the editor validates them (serialized from its model).
			assertTrue(editor.getProblems().isEmpty(), () -> name + ": " + editor.getProblems());
			// The XML page checks the text against the schemas of the document's standard.
			assertEquals(List.of(), xmlProblems(editor), name + " on the XML page");
			assertFalse(editor.isDirty(), name + " changed by viewing");
			page().closeEditor(editor, false);
			dispatch(50);
			opened++;
		}
		assertTrue(opened >= 15, "samples: " + opened);
		assertNoDialogs();
	}

	@Test
	void editingSavesTheDocument() throws Exception {
		MedbiqStandards.init();
		MedbiqDocumentType type = MedbiqStandards.byId("curriculum-inventory-v10");
		Path file = newDocument(type, "Doctor of Medicine Curriculum");
		HlomMetadataEditor editor = open(file);
		EObject root = Paths.rootElement(editor.documentRoot());
		assertEquals("Doctor of Medicine Curriculum", Paths.text(root, "Title"));
		EStructuralFeature title = edu.louisiana.caai.medbiq.model.Paths.feature(root, "Title");
		editor.getEditingDomain().getCommandStack()
				.execute(SetCommand.create(editor.getEditingDomain(), root, title, "Renamed Curriculum"));
		dispatch(100);
		assertTrue(editor.isDirty());
		editor.doSave(null);
		dispatch(100);
		String saved = Files.readString(file, StandardCharsets.UTF_8);
		assertTrue(saved.contains(">Renamed Curriculum</"), saved);
		assertFalse(saved.contains("Placeholders"), saved);
		assertTrue(saved.contains("http://ns.medbiq.org/curriculuminventory/v10/curriculuminventory.xsd"), saved);
		assertFalse(saved.contains("medbiq:/"), saved);
		page().closeEditor(editor, false);
	}
}
