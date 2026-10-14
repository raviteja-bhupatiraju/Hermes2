/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;

/**
 * The actions of the Projects view: its tool bar and view menu, the context menu (in the navigator's standard
 * groups, after which other plug-ins add theirs, such as the Team menu of Git), and the handlers of the workbench
 * commands Open, Rename (F2), Delete, Refresh (F5), Undo and Redo while the view is active.
 */
public final class ProjectsActionProvider extends CommonActionProvider {

	private ProjectsView view;
	private UndoRedoActionGroup undoRedo;

	private Action newProjectAction;
	private Action openProjectAction;
	private Action newDocumentAction;
	private Action importAction;
	private Action refreshAction;
	private Action openAction;
	private Action renameAction;
	private Action deleteAction;
	private PropertyDialogAction propertiesAction;

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite workbenchSite
				&& workbenchSite.getPart() instanceof ProjectsView projects) {
			view = projects;
			undoRedo = new UndoRedoActionGroup(workbenchSite.getSite(), WorkspaceUndoUtil.getWorkspaceUndoContext(),
					true);
		}
		newProjectAction = action(Messages.ProjectsActionProvider_NewProject, "icons/projects/new-project.png", Messages.ProjectsActionProvider_CreateAProject,
				() -> operations().newProject());
		openProjectAction = action(Messages.ProjectsActionProvider_OpenProject, "icons/library/folder.png", Messages.ProjectsActionProvider_OpenAProjectFolder,
				() -> operations().openProject());
		newDocumentAction = action(Messages.ProjectsActionProvider_NewDocument, "icons/toolbar/new.png",
				Messages.ProjectsActionProvider_CreateADocumentInThe, () -> operations().newDocument(targetFolder()));
		importAction = action(Messages.ProjectsActionProvider_AddExistingDocuments, "icons/actions/add.png",
				Messages.ProjectsActionProvider_CopyDocumentsIntoTheSelected,
				() -> operations().importFiles(targetFolder()));
		refreshAction = action(Messages.ProjectsActionProvider_Refresh, "icons/library/refresh.png", Messages.ProjectsActionProvider_ReReadTheFoldersAnd,
				() -> operations().revalidate());
		openAction = action(Messages.ProjectsActionProvider_Open, "icons/library/open.png", null, () -> operations().open(selectedFiles()));
		renameAction = action(Messages.ProjectsActionProvider_ReName, "icons/actions/edit.png", null,
				() -> operations().rename(selectedResources().getFirst()));
		deleteAction = action(Messages.ProjectsActionProvider_Delete, "icons/actions/remove.png", null,
				() -> operations().delete(selectedResources()));
		// The workbench commands, with their key bindings (F2, Delete, F5) and menu items (File, Edit).
		renameAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_RENAME);
		deleteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
		refreshAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_REFRESH);
		if (view != null) {
			// The workbench's properties dialog (Alt+Enter, File > Properties) with the project's property pages.
			propertiesAction = new PropertyDialogAction(view.getSite(), site.getStructuredViewer());
			propertiesAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_PROPERTIES);
		}
	}

	private ProjectOperations operations() {
		return view.operations();
	}

	private static ImageDescriptor icon(String path) {
		Image image = HlomTheme.image(path);
		return image == null ? null : ImageDescriptor.createFromImage(image);
	}

	private static Action action(String text, String icon, String tooltip, Runnable run) {
		Action action = new Action(text) {
			@Override
			public void run() {
				run.run();
			}
		};
		if (icon != null) {
			action.setImageDescriptor(icon(icon));
		}
		action.setToolTipText(tooltip);
		return action;
	}

	// ----------------------------------------------------------------------------------------------------
	// Selection
	// ----------------------------------------------------------------------------------------------------

	/** The current selection of the viewer (the action context can be older than the selection, or the input). */
	private IStructuredSelection selection() {
		return (IStructuredSelection) getActionSite().getStructuredViewer().getSelection();
	}

	private List<IResource> selectedResources() {
		return selection().toList().stream()
				.filter(element -> element instanceof IResource && !(element instanceof IWorkspaceRoot))
				.map(IResource.class::cast)
				.toList();
	}

	private List<IFile> selectedFiles() {
		return selectedResources().stream().filter(IFile.class::isInstance).map(IFile.class::cast).toList();
	}

	/** The project of the selection (or the only project), or {@code null}. */
	private IProject selectedProject() {
		List<IResource> resources = selectedResources();
		if (!resources.isEmpty()) {
			return resources.getFirst().getProject();
		}
		List<IProject> projects = HermesWorkspace.projects();
		return projects.size() == 1 ? projects.getFirst() : null;
	}

	/** The folder new documents and imported files go to: the selected folder, project or the file's folder. */
	private IContainer targetFolder() {
		List<IResource> resources = selectedResources();
		if (resources.isEmpty()) {
			return selectedProject();
		}
		return switch (resources.getFirst()) {
			case IContainer container -> container;
			case IResource resource -> resource.getParent();
		};
	}

	// ----------------------------------------------------------------------------------------------------
	// Menus and handlers
	// ----------------------------------------------------------------------------------------------------

	/** Open for files; Rename for one file or folder; Delete for files and folders (projects are closed). */
	private void updateActions() {
		List<IResource> resources = selectedResources();
		openAction.setEnabled(!selectedFiles().isEmpty());
		renameAction.setEnabled(resources.size() == 1 && !(resources.getFirst() instanceof IProject));
		deleteAction.setEnabled(!resources.isEmpty() && resources.stream().noneMatch(IProject.class::isInstance));
	}

	@Override
	public void fillActionBars(IActionBars bars) {
		if (view == null) {
			return;
		}
		updateActions();
		bars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
		bars.setGlobalActionHandler(ActionFactory.RENAME.getId(), renameAction);
		bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
		bars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), refreshAction);
		bars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(), propertiesAction);
		undoRedo.fillActionBars(bars);

		IToolBarManager toolbar = bars.getToolBarManager();
		if (toolbar.find(ProjectsView.ID + ".newProject") == null) {
			newProjectAction.setId(ProjectsView.ID + ".newProject");
			toolbar.add(newProjectAction);
			toolbar.add(openProjectAction);
			toolbar.add(new Separator());
			toolbar.add(newDocumentAction);
			toolbar.add(importAction);
			toolbar.add(new Separator());
			toolbar.add(refreshAction);
			IMenuManager viewMenu = bars.getMenuManager();
			viewMenu.add(newProjectAction);
			viewMenu.add(openProjectAction);
			viewMenu.add(new Separator());
			viewMenu.add(refreshAction);
			viewMenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (view == null) {
			return;
		}
		updateActions();
		List<IResource> resources = selectedResources();
		List<IFile> files = selectedFiles();
		boolean single = resources.size() == 1;
		boolean onlyProjects = !resources.isEmpty() && resources.stream().allMatch(IProject.class::isInstance);
		if (resources.isEmpty()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, newProjectAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, openProjectAction);
		}
		if (selectedProject() != null) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, newDocumentAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, importAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, action(Messages.ProjectsActionProvider_NewFolder, "icons/library/folder.png",
					null, () -> operations().newFolder(targetFolder())));
		}
		if (!files.isEmpty()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN, openAction);
		}
		if (!resources.isEmpty() && !onlyProjects) {
			if (single) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, renameAction);
			}
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, deleteAction);
		}
		if (!files.isEmpty()) {
			if (files.stream().anyMatch(file -> !ProjectsLabels.isJson(file))) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_PORT, action(Messages.ProjectsActionProvider_ConvertToJSON, "icons/pages/json.png",
						Messages.ProjectsActionProvider_SaveTheDocumentInThe,
						() -> operations().convert(files, Format.JSON)));
			}
			if (files.stream().anyMatch(ProjectsLabels::isJson)) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_PORT, action(Messages.ProjectsActionProvider_ConvertToXML, "icons/pages/xml.png",
						Messages.ProjectsActionProvider_SaveTheDocumentAsXML,
						() -> operations().convert(files, Format.XML)));
			}
		}
		if (single) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN_WITH, action(Messages.ProjectsActionProvider_ShowInFileExplorer,
					"icons/library/reveal.png", null, () -> operations().showInFileManager(resources.getFirst())));
		}
		menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, refreshAction);
		if (onlyProjects) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, action(Messages.ProjectsActionProvider_CloseProject, null,
					Messages.ProjectsActionProvider_RemoveFromTheViewThe,
					() -> operations().closeProjects(resources.stream().map(IProject.class::cast).toList())));
			if (single && propertiesAction != null && propertiesAction.isApplicableForSelection()) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES, propertiesAction);
			}
		}
	}

	@Override
	public void dispose() {
		if (undoRedo != null) {
			undoRedo.dispose();
		}
		if (propertiesAction != null) {
			propertiesAction.dispose();
		}
		super.dispose();
	}
}
