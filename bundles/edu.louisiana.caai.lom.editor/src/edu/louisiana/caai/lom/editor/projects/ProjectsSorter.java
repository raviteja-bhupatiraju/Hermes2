/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;

/** The order of the Projects view: projects by name, then in each folder its folders and then its documents. */
public final class ProjectsSorter extends ViewerComparator {

	@Override
	public int category(Object element) {
		return element instanceof IFile ? 1 : 0;
	}

	@Override
	public int compare(Viewer viewer, Object first, Object second) {
		int categories = Integer.compare(category(first), category(second));
		return categories != 0 ? categories : name(first).compareTo(name(second));
	}

	private static String name(Object element) {
		String name = switch (element) {
			case IProject project -> HermesWorkspace.name(project);
			case IResource resource -> resource.getName();
			default -> String.valueOf(element);
		};
		return name.toLowerCase(Locale.ROOT);
	}
}
