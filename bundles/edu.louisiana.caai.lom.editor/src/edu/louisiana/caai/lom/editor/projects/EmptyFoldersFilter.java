/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;

/**
 * A filter of the Projects view (View Menu &gt; Filters and Customization): hides the folders that contain no
 * documents, such as image or attachment folders.
 */
public final class EmptyFoldersFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (!(element instanceof IFolder folder)) {
			return true;
		}
		return DocumentIndex.documents(folder.getProject()).stream()
				.anyMatch(document -> folder.getFullPath().isPrefixOf(document.file().getFullPath()));
	}
}
