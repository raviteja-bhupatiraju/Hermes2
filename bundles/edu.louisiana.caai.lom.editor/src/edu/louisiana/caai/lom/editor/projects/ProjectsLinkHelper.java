/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.nio.file.Path;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;

/**
 * Links the Projects view with the editors (Link with Editor, Navigate &gt; Show In): the document of an editor is
 * its file in the projects, and selecting an open document brings its editor to the top.
 */
public final class ProjectsLinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput input) {
		IFile file = switch (input) {
			case IFileEditorInput fileInput -> fileInput.getFile();
			case null -> null;
			default -> {
				Path path = EditorInputs.pathOf(input);
				yield path == null ? null : HermesWorkspace.fileAt(path);
			}
		};
		return file == null || !HermesWorkspace.isHermesProject(file.getProject()) ? StructuredSelection.EMPTY
				: new StructuredSelection(file);
	}

	@Override
	public void activateEditor(IWorkbenchPage page, IStructuredSelection selection) {
		if (!(selection.getFirstElement() instanceof IFile file)) {
			return;
		}
		Path location = HermesWorkspace.location(file);
		for (IEditorReference reference : page.getEditorReferences()) {
			IEditorPart editor = reference.getEditor(false);
			if (editor != null && location != null && location.equals(EditorInputs.pathOf(editor.getEditorInput()))) {
				page.bringToTop(editor);
				return;
			}
		}
	}
}
