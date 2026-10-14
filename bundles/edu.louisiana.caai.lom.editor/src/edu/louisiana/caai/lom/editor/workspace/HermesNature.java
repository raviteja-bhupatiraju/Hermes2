/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/** The nature of Hermes projects: adds the validation {@link HermesBuilder}. */
public final class HermesNature implements IProjectNature {

	private IProject project;

	@Override
	public void configure() throws CoreException {
		IProjectDescription description = project.getDescription();
		HermesWorkspace.addBuilder(description);
		project.setDescription(description, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		IProjectDescription description = project.getDescription();
		HermesWorkspace.removeBuilder(description);
		project.setDescription(description, null);
		project.deleteMarkers(HermesWorkspace.PROBLEM_MARKER, true, IProject.DEPTH_INFINITE);
		DocumentIndex.forget(project);
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
