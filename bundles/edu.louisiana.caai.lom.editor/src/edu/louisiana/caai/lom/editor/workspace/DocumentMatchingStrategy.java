/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.nio.file.Path;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

/**
 * Matches the editor of a document whichever input it was opened with: a document is the same whether it is opened
 * by its file, as a file of a project or by its link in the {@link OpenDocuments} (from the Problems view).
 */
public final class DocumentMatchingStrategy implements IEditorMatchingStrategy {

	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		try {
			IEditorInput open = editorRef.getEditorInput();
			if (open == null || input == null) {
				return false;
			}
			if (open.equals(input)) {
				return true;
			}
			Path openPath = EditorInputs.pathOf(open);
			return openPath != null && openPath.equals(EditorInputs.pathOf(input));
		} catch (PartInitException _) {
			return false;
		}
	}
}
