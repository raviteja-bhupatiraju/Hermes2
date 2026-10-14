/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;

/**
 * Opening a valid document and visiting every page: nothing is logged, no dialog appears and the document
 * stays unchanged.
 */
class EditorPagesTest extends AbstractUiTest {

	@Test
	void allPagesCanBeShownWithoutChangingTheDocument() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		byte[] original = Files.readAllBytes(file);
		HlomMetadataEditor editor = open(file);
		recordModelChanges(editor);
		dispatch(500);
		assertEquals(List.of(), modelChanges, "the document changed while idle on the first page");

		assertEquals(0, activePage(editor), "a valid document opens on the first page");
		assertFalse(editor.isDirty(), "opening must not dirty the editor");

		int count = pageCount(editor);
		assertTrue(count >= 14, "12 form pages, tree and XML expected, found " + count);
		for (int i = 0; i < count; i++) {
			setActivePage(editor, i);
			dispatch(100);
			assertEquals(i, activePage(editor), "page " + pageText(editor, i) + " could not be activated");
			assertFalse(editor.isDirty(), "showing page " + pageText(editor, i) + " dirtied the editor");
			assertEquals(List.of(), modelChanges, "showing page " + pageText(editor, i) + " changed the document");
		}
		// Back to the first page, then give delayed bindings and the validation timer time to run.
		setActivePage(editor, 0);
		dispatch(HlomPreferences.getValidationDelay() + 1000);
		assertFalse(editor.isDirty(), "visiting the pages dirtied the editor");
		assertFalse(editor.getEditingDomain().getCommandStack().canUndo(),
				"visiting the pages executed a command: " + editor.getEditingDomain().getCommandStack().getUndoCommand());

		editor.validateNow();
		assertEquals(0, errors(editor.getProblems()).size(), describe(editor.getProblems()));
		assertNoDialogs();

		editor.getSite().getPage().closeEditor(editor, false);
		dispatch(100);
		assertTrue(java.util.Arrays.equals(original, Files.readAllBytes(file)), "the file must not be touched");
	}

	@Test
	void showPageNavigatesById() {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		for (String id : new String[] { "general", "lifecycle", "educational", "rights", "classification",
				"relation", "annotation", "healthcare-education", "healthcare-asset", "metametadata", "technical",
				"tree", "xml", "overview" }) {
			editor.showPage(id);
			dispatch(50);
			assertEquals(pageIndex(editor, id), activePage(editor), "showPage(" + id + ")");
		}
		assertFalse(editor.isDirty());
		assertNoDialogs();
	}

	/** The page list works with the keyboard: it takes the focus, the arrow keys move and Enter opens a page. */
	@Test
	void pageListWorksWithTheKeyboard() {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		dispatch(100);
		Canvas panel = find(editor.getSite().getShell(), Canvas.class,
				canvas -> canvas.getClass().getSimpleName().equals("NavigationPanel")).getFirst();
		assertTrue(panel.setFocus(), "the page list takes the keyboard focus");
		dispatch(50);
		assertEquals(0, activePage(editor));
		key(panel, SWT.ARROW_DOWN, (char) 0);
		key(panel, SWT.ARROW_DOWN, (char) 0);
		assertEquals(0, activePage(editor), "moving the focus doesn't open a page");
		key(panel, SWT.CR, SWT.CR);
		dispatch(100);
		assertEquals(2, activePage(editor), "Enter opens the focused page");
		key(panel, SWT.END, (char) 0);
		key(panel, ' ', ' ');
		dispatch(100);
		assertEquals(pageCount(editor) - 1, activePage(editor), "End and Space open the last page");
		assertEquals(ACC.ROLE_LIST, role(panel), "screen readers see a list");
		assertFalse(editor.isDirty());
		assertNoDialogs();
	}

	private static void key(Canvas control, int keyCode, char character) {
		Event event = new Event();
		event.keyCode = keyCode;
		event.character = character;
		control.notifyListeners(SWT.KeyDown, event);
		dispatch(20);
	}

	/** The accessible role the control reports. */
	private static int role(Canvas control) {
		org.eclipse.swt.accessibility.AccessibleControlEvent event = new org.eclipse.swt.accessibility.AccessibleControlEvent(
				control.getAccessible());
		event.childID = ACC.CHILDID_SELF;
		Object listeners = field(control.getAccessible(), "accessibleControlListeners");
		for (Object listener : (java.util.List<?>) listeners) {
			((org.eclipse.swt.accessibility.AccessibleControlListener) listener).getRole(event);
		}
		return event.detail;
	}
}
