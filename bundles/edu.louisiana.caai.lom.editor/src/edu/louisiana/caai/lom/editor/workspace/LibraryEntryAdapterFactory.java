/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Adapts the records of the Library view to their workspace files when they belong to a Hermes project, so that
 * the workbench treats them as those files: the Team menu (Git), Show In, the Properties view and the commands on
 * resources work on them. Records of folders outside the projects have no workspace file.
 */
public final class LibraryEntryAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (!(adaptableObject instanceof LibraryEntry entry) || entry.path() == null) {
			return null;
		}
		IFile file = HermesWorkspace.fileAt(entry.path());
		if (file == null || !file.exists()) {
			return null;
		}
		return adapterType.isInstance(file) ? adapterType.cast(file) : null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IFile.class, IResource.class };
	}
}
