/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import edu.louisiana.caai.lom.editor.validation.ValidationView;

/**
 * The "Library" perspective: the metadata library across the top, the editor below it and the validation
 * problems of the active record at the bottom.
 */
public class LibraryPerspective implements IPerspectiveFactory {

	public static final String ID = "edu.louisiana.caai.lom.editor.library.LibraryPerspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		String editorArea = layout.getEditorArea();
		IFolderLayout top = layout.createFolder("library", IPageLayout.TOP, 0.48f, editorArea);
		top.addView(LibraryView.ID);
		IFolderLayout bottom = layout.createFolder("problems", IPageLayout.BOTTOM, 0.72f, editorArea);
		bottom.addView(ValidationView.ID);
		IFolderLayout right = layout.createFolder("outline", IPageLayout.RIGHT, 0.74f, editorArea);
		right.addView(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(LibraryView.ID);
		layout.addShowViewShortcut(ValidationView.ID);
		layout.addPerspectiveShortcut(ID);
	}
}
