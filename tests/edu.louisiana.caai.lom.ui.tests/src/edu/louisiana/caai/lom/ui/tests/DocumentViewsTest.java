/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.views.CurriculumTimelineView;
import edu.louisiana.caai.lom.editor.views.DocumentGraphView;
import edu.louisiana.caai.lom.editor.views.DocumentListsView;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Paths;

/** The views of the active document: graph, timeline and lists. */
class DocumentViewsTest extends AbstractUiTest {

	private static final String SAMPLE = "samples/curriculum-inventory/bayou-state-md-2024-2025-v10.xml";

	private HlomMetadataEditor openSample() throws Exception {
		MedbiqStandards.init();
		Path file = tempDir.resolve("views.xml");
		try (InputStream in = org.osgi.framework.FrameworkUtil.getBundle(MedbiqStandards.class).getEntry(SAMPLE)
				.openStream()) {
			Files.copy(in, file);
		}
		return open(file);
	}

	@Test
	void theViewsShowTheActiveCurriculumInventory() throws Exception {
		HlomMetadataEditor editor = openSample();
		DocumentGraphView graph = (DocumentGraphView) page().showView(DocumentGraphView.ID);
		CurriculumTimelineView timeline = (CurriculumTimelineView) page().showView(CurriculumTimelineView.ID);
		DocumentListsView lists = (DocumentListsView) page().showView(DocumentListsView.ID);
		page().activate(editor);
		dispatch(200);
		try {
			assertFalse(graph.graph().nodes().isEmpty(), "graph nodes");
			assertFalse(graph.graph().edges().isEmpty(), "graph edges");
			assertTrue(timeline.timeline() != null && !timeline.timeline().isEmpty(), "timeline bars");

			lists.showList("Events/Event");
			dispatch(100);
			EObject root = Paths.rootElement(editor.documentRoot());
			assertEquals(Paths.objects(root, "Events/Event").size(), lists.rows().size());
			assertFalse(editor.isDirty(), "showing changes nothing");

			// A cell edit is an undoable change of the editor.
			EObject first = lists.rows().getFirst();
			String title = Paths.text(first, "Title");
			DocumentListsView.set(editor.getEditingDomain(), first, "Title", "Renamed in the table");
			dispatch(600);
			assertEquals("Renamed in the table", Paths.text(first, "Title"));
			assertTrue(editor.isDirty());
			editor.getEditingDomain().getCommandStack().undo();
			assertEquals(title, Paths.text(first, "Title"));
			assertNoDialogs();
		} finally {
			page().hideView(graph);
			page().hideView(timeline);
			page().hideView(lists);
			page().closeEditor(editor, false);
		}
	}
}
