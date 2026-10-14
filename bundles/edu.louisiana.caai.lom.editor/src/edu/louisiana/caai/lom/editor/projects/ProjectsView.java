/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonNavigator;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;

/**
 * The project manager: the Hermes projects of the workspace with their folders and documents (XML and JSON), a
 * Common Navigator view (see the navigator extensions in plugin.xml). Each document shows its title and validation
 * state (from the background validation, see {@link HermesWorkspace}); documents can be created, imported (also by
 * dropping files), opened, renamed, moved, deleted (undoably), organized in folders and converted between XML and
 * JSON. The elements are the workspace resources, so the Problems view, the Team menu (Git), Show In and Link with
 * Editor work with them.
 *
 * @see ProjectsContent
 * @see ProjectsLabels
 * @see ProjectsActionProvider
 * @see ProjectOperations
 */
public class ProjectsView extends CommonNavigator {

	/** The view id (also the id of the navigator viewer). */
	public static final String ID = "edu.louisiana.caai.lom.editor.projects.ProjectsView";

	private ProjectOperations operations;

	/** Shows the view (restoring the start page if it is maximized) and returns it, or {@code null}. */
	public static ProjectsView show(IWorkbenchWindow window) {
		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null) {
			return null;
		}
		for (var reference : page.getViewReferences()) {
			if (page.getPartState(reference) == IWorkbenchPage.STATE_MAXIMIZED) {
				page.setPartState(reference, IWorkbenchPage.STATE_RESTORED);
			}
		}
		try {
			return (ProjectsView) page.showView(ID);
		} catch (PartInitException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return null;
		}
	}

	/** Whether projects are open (the start page then leaves room for this view). */
	public static boolean hasProjects() {
		return !HermesWorkspace.projects().isEmpty();
	}

	@Override
	protected Object getInitialInput() {
		return HermesWorkspace.root();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		operations = new ProjectOperations(getViewSite(), getCommonViewer());
		Tree tree = getCommonViewer().getTree();
		HelpContexts.set(tree, HelpContexts.PROJECTS_VIEW);
		// The tool tip of the item under the mouse: title, location and validation state.
		tree.addListener(SWT.MouseHover, event -> {
			TreeItem item = tree.getItem(new Point(event.x, event.y));
			tree.setToolTipText(item == null ? null : ProjectsLabels.toolTip(item.getData()));
		});
		tree.addListener(SWT.KeyDown, event -> {
			if (event.character == SWT.CR) {
				operations.open(selectedFiles());
			}
		});
		getCommonViewer().setExpandedElements(HermesWorkspace.projects().toArray());
	}

	/** The operations on the projects, folders and documents of the view. */
	ProjectOperations operations() {
		return operations;
	}

	private List<IFile> selectedFiles() {
		return ((IStructuredSelection) getCommonViewer().getSelection()).toList().stream()
				.filter(IFile.class::isInstance).map(IFile.class::cast).toList();
	}

	/** A double click opens documents and expands or collapses projects and folders. */
	@Override
	protected void handleDoubleClick(DoubleClickEvent event) {
		Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
		if (element instanceof IContainer container) {
			getCommonViewer().setExpandedState(container, !getCommonViewer().getExpandedState(container));
		} else if (element instanceof IFile file) {
			operations.open(List.of(file));
		}
	}

	/** Shows a project (opening it in the workspace if needed) and selects it. */
	public void addProject(HermesProject project) {
		operations.addProject(project);
	}

	/** Asks for the properties of a new project and creates it. */
	public void newProject() {
		operations.newProject();
	}

	/** Asks for a project folder and opens it. */
	public void openProject() {
		operations.openProject();
	}

	/**
	 * Updates the tree; with {@code revalidate}, first re-reads the project folders from the disk and validates
	 * all their documents again (in the background).
	 */
	public void refresh(boolean revalidate) {
		if (getCommonViewer() == null || getCommonViewer().getControl().isDisposed()) {
			return;
		}
		if (revalidate) {
			operations.revalidate();
		}
		getCommonViewer().refresh();
	}
}
