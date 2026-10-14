/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.references.ReferenceHandlers;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.editor.xml.XmlSourceEditor;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.References.Kind;
import edu.louisiana.caai.medbiq.model.References.Target;

/** The references within documents in the editor: renaming identifiers and links on the XML page. */
class ReferencesUiTest extends AbstractUiTest {

	private static final String SAMPLE = "samples/curriculum-inventory/bayou-state-md-2024-2025-v10.xml";

	private Path sample() throws Exception {
		MedbiqStandards.init();
		Path file = tempDir.resolve("references.xml");
		try (InputStream in = org.osgi.framework.FrameworkUtil.getBundle(MedbiqStandards.class).getEntry(SAMPLE)
				.openStream()) {
			Files.copy(in, file);
		}
		return file;
	}

	private static Kind kind(HlomMetadataEditor editor, String element) {
		return References.kind(((MedbiqDocumentType) editor.documentType()).references(), null, element);
	}

	private static ISourceViewer sourceViewer(XmlSourceEditor editor) {
		return (ISourceViewer) invoke(editor, AbstractTextEditor.class, "getSourceViewer", new Class<?>[0]);
	}

	@Test
	void renamingAnIdentifierUpdatesItsReferencesInOneUndoableStep() throws Exception {
		HlomMetadataEditor editor = open(sample());
		EObject root = Paths.rootElement(editor.documentRoot());
		Kind events = kind(editor, "EventReference");
		Target event = References.targets(root, events).stream()
				.filter(target -> References.referencesTo(root, target).size() > 0).findFirst().orElseThrow();
		int count = References.referencesTo(root, event).size();
		String old = event.key();
		ReferenceHandlers.rename(editor.getEditingDomain(), event, References.keyLocation(event),
				References.referencesTo(root, event), "renamed-event");
		dispatch(100);
		Target renamed = References.resolve(root, events, events.format("renamed-event"));
		assertNotNull(renamed, "the event has the new id");
		assertEquals(count, References.referencesTo(root, renamed).size(), "the references follow");
		assertEquals(List.of(), References.unresolved(root, List.of(events)));
		assertTrue(editor.isDirty());

		editor.getEditingDomain().getCommandStack().undo();
		dispatch(100);
		assertNotNull(References.resolve(root, events, events.format(old)), "undone in one step");
		assertEquals(count, References.referencesTo(root, References.resolve(root, events, events.format(old))).size());
		assertFalse(editor.isDirty());
		page().closeEditor(editor, false);
		assertNoDialogs();
	}

	@Test
	void referencesOnTheXmlPageLinkToTheirTargets() throws Exception {
		HlomMetadataEditor editor = open(sample());
		XmlSourceEditor xml = XmlPageTest.xmlEditor(editor);
		setActivePage(editor, pageIndex(editor, "xml"));
		dispatch(100);
		ISourceViewer viewer = sourceViewer(xml);
		String text = viewer.getDocument().get();
		int reference = text.indexOf("<EventReference>") + "<EventReference>".length() + 3;
		IHyperlinkDetector[] detectors = ((org.eclipse.jface.text.source.SourceViewerConfiguration) invoke(xml,
				AbstractTextEditor.class, "getSourceViewerConfiguration", new Class<?>[0])).getHyperlinkDetectors(viewer);
		IHyperlink link = null;
		for (IHyperlinkDetector detector : detectors) {
			IHyperlink[] links = detector.detectHyperlinks(viewer, new Region(reference, 0), false);
			if (links != null && links.length > 0) {
				link = links[0];
				break;
			}
		}
		assertNotNull(link, "the event reference is a link");
		link.open();
		dispatch(50);
		int selected = viewer.getSelectedRange().x;
		assertTrue(text.startsWith("Event", selected) && text.lastIndexOf("<Events>", selected) >= 0,
				"the link selects the event");
		assertFalse(editor.isDirty());
		page().closeEditor(editor, false);
		assertNoDialogs();
	}

	@Test
	void documentsOfProjectsReferToEachOtherByIdentifier() throws Exception {
		MedbiqStandards.init();
		Path folder = tempDir.resolve("Competencies");
		Files.createDirectories(folder);
		for (String name : List.of("core-competencies-framework.xml", "competency-patient-care.xml")) {
			try (InputStream in = org.osgi.framework.FrameworkUtil.getBundle(MedbiqStandards.class)
					.getEntry("samples/competencies/" + name).openStream()) {
				Files.copy(in, folder.resolve(name));
			}
		}
		HermesProject.create(folder, "Competencies", "", HermesProject.Format.XML);
		HermesWorkspace.open(folder, null);
		try {
			DocumentLinks.Identifier patientCare = new DocumentLinks.Identifier("URI",
					"https://gme.lakeside.example.org/competencies/patient-care");
			waitUntil("the documents are indexed", () -> DocumentIndex.identifiedBy(patientCare).size() == 1
					&& !DocumentIndex.referrers(List.of(patientCare), null).isEmpty(), TIMEOUT);
			assertEquals("competency-patient-care.xml",
					DocumentIndex.identifiedBy(patientCare).getFirst().file().getName());
			DocumentIndex.Referrer referrer = DocumentIndex.referrers(List.of(patientCare), null).getFirst();
			assertEquals("core-competencies-framework.xml", referrer.document().file().getName());
			assertEquals("Included competency", referrer.link().label());
		} finally {
			HermesWorkspace.close(HermesWorkspace.projectAt(folder), null);
		}
	}
}
