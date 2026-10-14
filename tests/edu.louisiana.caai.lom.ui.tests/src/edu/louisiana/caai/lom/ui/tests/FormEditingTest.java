/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Text;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;

/**
 * Editing through the form widgets: the delayed text binding writes the model through the command stack, so
 * undo, redo, dirty state and save work as for any other change.
 */
class FormEditingTest extends AbstractUiTest {

	static final String TITLE = "Insulin Pump Therapy";
	static final String NEW_TITLE = "Insulin Pump Therapy for Primary Care";

	@Test
	void titleEditUndoRedoSave() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		CommandStack stack = editor.getEditingDomain().getCommandStack();

		Text title = titleText(editor, TITLE);
		title.setText(NEW_TITLE); // fires SWT.Modify like typing does
		// The binding commits after a 400 ms typing pause.
		dispatch(150);
		assertEquals(TITLE, modelTitle(editor), "committed before the typing pause");
		waitUntil("title committed to the model", () -> NEW_TITLE.equals(modelTitle(editor)), 3000);
		assertTrue(editor.isDirty(), "edit must dirty the editor");
		assertTrue(stack.canUndo());

		stack.undo();
		dispatch(100);
		assertEquals(TITLE, modelTitle(editor), "undo");
		assertEquals(TITLE, title.getText(), "the field shows the restored title");
		dispatch(600); // a re-commit of the restored widget text must not create a new command
		assertFalse(editor.isDirty(), "undo to the saved state");
		assertTrue(stack.canRedo(), "redo must still be possible after undo: " + stack.getUndoCommand());

		stack.redo();
		dispatch(100);
		assertEquals(NEW_TITLE, modelTitle(editor), "redo");
		assertEquals(NEW_TITLE, title.getText());
		assertTrue(editor.isDirty());

		editor.doSave(new NullProgressMonitor());
		dispatch(200);
		assertFalse(editor.isDirty(), "saved");
		String saved = Files.readString(file, StandardCharsets.UTF_8);
		assertTrue(saved.contains(">" + NEW_TITLE + "<"), saved);
		assertReloadsValid(file, NEW_TITLE);
		assertNoDialogs();
	}

	/** Saving right after typing (before the typing pause ends) still saves the typed text. */
	@Test
	void saveCommitsPendingEdit() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);

		Text title = titleText(editor, TITLE);
		title.setText(NEW_TITLE);
		editor.doSave(new NullProgressMonitor());
		dispatch(600);
		assertEquals(NEW_TITLE, modelTitle(editor));
		assertFalse(editor.isDirty(), "the pending edit was saved");
		assertReloadsValid(file, NEW_TITLE);
		assertNoDialogs();
	}

	/** Switching pages right after typing commits the typed text. */
	@Test
	void pageSwitchCommitsPendingEdit() {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		Text title = titleText(editor, TITLE);
		title.setText(NEW_TITLE);
		editor.showPage("lifecycle");
		assertEquals(NEW_TITLE, modelTitle(editor), "committed on page switch");
		dispatch(600);
		assertEquals(1, undoDepth(editor), "exactly one command for the edit");
		assertTrue(editor.isDirty());
	}

	/** The General page's single-line Title field showing the given text. */
	static Text titleText(HlomMetadataEditor editor, String shown) {
		editor.showPage("general");
		dispatch(100);
		List<Text> texts = find(pageControl(editor, pageIndex(editor, "general")), Text.class,
				text -> shown.equals(text.getText()));
		assertEquals(1, texts.size(), "Text widgets showing \"" + shown + "\" on the General page");
		return texts.getFirst();
	}

	static String modelTitle(HlomMetadataEditor editor) {
		Resource resource = editor.getEditingDomain().getResourceSet().getResources().getFirst();
		return title(HlomResources.getDocumentRoot(resource));
	}

	static String title(DocumentRoot root) {
		return root.getLom().getGeneral().getFirst().getTitle().getFirst().getString().getFirst().getValue();
	}

	static int undoDepth(HlomMetadataEditor editor) {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		int depth = 0;
		while (stack.canUndo()) {
			stack.undo();
			depth++;
		}
		for (int i = 0; i < depth; i++) {
			stack.redo();
		}
		return depth;
	}

	static void assertReloadsValid(Path file, String expectedTitle) throws Exception {
		Resource reloaded = HlomResources.load(URI.createFileURI(file.toString()));
		assertEquals(expectedTitle, title(HlomResources.getDocumentRoot(reloaded)));
		assertEquals(List.of(), errors(HlomValidator.validate(reloaded)).stream().map(p -> p.message()).toList(),
				"the saved file must be valid");
	}
}
