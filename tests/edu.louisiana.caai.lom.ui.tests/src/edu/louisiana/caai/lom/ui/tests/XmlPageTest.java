/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static edu.louisiana.caai.lom.ui.tests.FormEditingTest.NEW_TITLE;
import static edu.louisiana.caai.lom.ui.tests.FormEditingTest.TITLE;
import static edu.louisiana.caai.lom.ui.tests.FormEditingTest.modelTitle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.xml.XmlSourceEditor;

/**
 * The XML page: text edits are applied to the model when leaving the page (as one undoable command), and the
 * page shows model changes.
 */
class XmlPageTest extends AbstractUiTest {

	@Test
	void xmlEditIsAppliedWhenLeavingThePage() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("xml");
		dispatch(100);
		XmlSourceEditor xml = xmlEditor(editor);
		assertTrue(xml.getText().contains(">" + TITLE + "<"), "the XML page shows the document");
		assertFalse(editor.isDirty());

		replace(xml, ">" + TITLE + "<", ">" + NEW_TITLE + "<");
		assertTrue(xml.isModified());
		assertTrue(editor.isDirty(), "an XML edit dirties the editor");
		assertEquals(TITLE, modelTitle(editor), "the model changes only when the XML is applied");

		editor.showPage("general");
		dispatch(100);
		assertEquals(pageIndex(editor, "general"), activePage(editor));
		assertEquals(NEW_TITLE, modelTitle(editor), "applied on page switch");
		FormEditingTest.titleText(editor, NEW_TITLE);
		assertTrue(editor.isDirty());

		editor.getEditingDomain().getCommandStack().undo();
		dispatch(100);
		assertEquals(TITLE, modelTitle(editor), "the XML edit is one undoable command");
		assertFalse(editor.isDirty());

		editor.getEditingDomain().getCommandStack().redo();
		editor.showPage("xml");
		dispatch(100);
		assertTrue(xml.getText().contains(">" + NEW_TITLE + "<"), "the XML page shows the model");
		assertFalse(xml.isModified());
		editor.doSave(new NullProgressMonitor());
		dispatch(100);
		assertFalse(editor.isDirty());
		assertTrue(Files.readString(file, StandardCharsets.UTF_8).contains(">" + NEW_TITLE + "<"));
		FormEditingTest.assertReloadsValid(file, NEW_TITLE);
		assertNoDialogs();
	}

	/** Saving while the XML page has unapplied (valid) text saves that text. */
	@Test
	void saveAppliesXmlText() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("xml");
		dispatch(100);
		XmlSourceEditor xml = xmlEditor(editor);
		replace(xml, ">" + TITLE + "<", ">" + NEW_TITLE + "<");
		editor.doSave(new NullProgressMonitor());
		dispatch(100);
		assertFalse(editor.isDirty());
		assertEquals(NEW_TITLE, modelTitle(editor));
		FormEditingTest.assertReloadsValid(file, NEW_TITLE);
		assertNoDialogs();
	}

	/** A change of the file outside of the editor (a Git checkout) replaces the document if it has no changes. */
	@Test
	void externalChangeIsTakenOver() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		dispatch(100);
		String text = Files.readString(file, StandardCharsets.UTF_8);
		Files.writeString(file, text.replace(">" + TITLE + "<", ">" + NEW_TITLE + "<"), StandardCharsets.UTF_8);
		editor.checkDisk();
		dispatch(100);
		assertEquals(NEW_TITLE, modelTitle(editor), "the file's content is taken over");
		assertFalse(editor.isDirty());
		assertTrue(dialogs.seen.isEmpty(), "no question without unsaved changes: " + dialogs.seen);

		// With unsaved changes, the user is asked; closing the question keeps the changes.
		editor.showPage("xml");
		dispatch(100);
		replace(xmlEditor(editor), ">" + NEW_TITLE + "<", ">Edited<");
		Files.writeString(file, text, StandardCharsets.UTF_8);
		editor.checkDisk();
		dispatch(100);
		assertEquals(1, dialogs.seen.size(), dialogs.seen::toString);
		assertTrue(dialogs.seen.getFirst().startsWith("File Changed"), dialogs.seen.getFirst());
		assertTrue(editor.isDirty(), "the unsaved changes are kept");
		assertTrue(xmlEditor(editor).getText().contains(">Edited<"));
	}

	/** A save that fails (a read-only file) is reported, and the editor keeps the changes. */
	@Test
	void failedSaveKeepsTheChanges() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("xml");
		dispatch(100);
		replace(xmlEditor(editor), ">" + TITLE + "<", ">" + NEW_TITLE + "<");
		assertTrue(file.toFile().setWritable(false));
		try {
			editor.doSave(new NullProgressMonitor());
			dispatch(100);
			assertEquals(1, dialogs.seen.size(), "the failure is reported: " + dialogs.seen);
			assertTrue(dialogs.seen.getFirst().startsWith("Cannot Save"), dialogs.seen.getFirst());
			assertTrue(editor.isDirty(), "the changes are not saved");
			assertFalse(Files.readString(file, StandardCharsets.UTF_8).contains(">" + NEW_TITLE + "<"));
		} finally {
			file.toFile().setWritable(true);
		}
		editor.doSave(new NullProgressMonitor());
		dispatch(100);
		assertFalse(editor.isDirty(), "saved once the file is writable");
		assertTrue(Files.readString(file, StandardCharsets.UTF_8).contains(">" + NEW_TITLE + "<"));
	}

	/**
	 * Broken XML keeps the user on the XML page: the editor asks whether to discard the text (the watcher answers
	 * the dialog by closing it, i.e. "keep editing").
	 */
	@Test
	void brokenXmlKeepsTheXmlPage() throws Exception {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		editor.showPage("xml");
		dispatch(100);
		XmlSourceEditor xml = xmlEditor(editor);
		replace(xml, "</title>", "</titel>");

		editor.showPage("general");
		dispatch(500);
		assertEquals(1, dialogs.seen.size(), "one question about the XML errors: " + dialogs.seen);
		assertTrue(dialogs.seen.getFirst().startsWith("XML Errors"), dialogs.seen.getFirst());
		assertEquals(pageIndex(editor, "xml"), activePage(editor), "stays on the XML page");
		assertEquals(TITLE, modelTitle(editor), "the model is unchanged");
		assertTrue(editor.isDirty(), "the unapplied text keeps the editor dirty");

		// Repair the text; leaving the page now works.
		replace(xml, "</titel>", "</title>");
		editor.showPage("general");
		dispatch(100);
		assertEquals(pageIndex(editor, "general"), activePage(editor));
		assertEquals(1, dialogs.seen.size());
	}

	/**
	 * The commands of the XML page (content assist, quick fix, format, toggle comment) are handled by the page
	 * while it is active and have their key bindings, so the workbench dispatches the keys to them.
	 */
	@Test
	void xmlPageCommandsAreBoundAndHandled() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("xml");
		dispatch(200);
		ICommandService commands = window().getService(ICommandService.class);
		IBindingService bindings = window().getService(IBindingService.class);
		for (String id : List.of(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS,
				ITextEditorActionDefinitionIds.QUICK_ASSIST, XmlSourceEditor.FORMAT_ACTION_ID,
				XmlSourceEditor.TOGGLE_COMMENT_ACTION_ID)) {
			assertTrue(commands.getCommand(id).isDefined(), id + " is defined");
			assertTrue(commands.getCommand(id).isHandled(), id + " is handled on the XML page");
			assertTrue(bindings.getActiveBindingsFor(id).length > 0, id + " has a key binding");
		}
		XmlSourceEditor xml = xmlEditor(editor);
		IDocument document = xml.getDocumentProvider().getDocument(xml.getEditorInput());
		int line = document.getLineOfOffset(document.get().indexOf(">" + TITLE + "<"));
		xml.selectAndReveal(document.getLineOffset(line), 0);
		window().getService(IHandlerService.class).executeCommand(XmlSourceEditor.TOGGLE_COMMENT_ACTION_ID, null);
		dispatch(50);
		String commented = document.get(document.getLineOffset(line), document.getLineLength(line)).strip();
		assertTrue(commented.startsWith("<!--"), "the command comments the caret line: " + commented);
		page().closeEditor(editor, false);
	}

	static XmlSourceEditor xmlEditor(HlomMetadataEditor editor) {
		return (XmlSourceEditor) field(editor, "xmlEditor");
	}

	/** Replaces the first occurrence of a string in the XML page's document (like typing). */
	static void replace(XmlSourceEditor xml, String search, String replacement) throws BadLocationException {
		IDocument document = xml.getDocumentProvider().getDocument(xml.getEditorInput());
		int offset = document.get().indexOf(search);
		assertTrue(offset >= 0, "\"" + search + "\" not found in the XML");
		document.replace(offset, search.length(), replacement);
		dispatch(50);
	}
}
