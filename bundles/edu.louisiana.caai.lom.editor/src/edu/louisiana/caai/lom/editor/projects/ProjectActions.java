/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * The project commands of the File and Window menus and the start page.
 */
public final class ProjectActions {

	private ProjectActions() {
	}

	/** Opens the New Project wizard; the new project is shown in the Projects view. */
	public static void newProject(IWorkbenchWindow window) {
		NewProjectWizard.open(window);
	}

	/** Shows the Projects view and asks for a project folder to open. */
	public static void openProject(IWorkbenchWindow window) {
		ProjectsView view = ProjectsView.show(window);
		if (view != null) {
			view.openProject();
		}
	}
}
