/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.undo.AbstractWorkspaceOperation;
import org.eclipse.ui.ide.undo.CreateFolderOperation;
import org.eclipse.ui.ide.undo.DeleteResourcesOperation;
import org.eclipse.ui.ide.undo.MoveResourcesOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.start.RecentDocuments;
import edu.louisiana.caai.lom.editor.wizards.NewHlomDocumentWizard;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;

/**
 * The operations of the Projects view on projects, folders and documents. Creating folders, renaming, moving and
 * deleting are the workbench's undoable workspace operations (Edit &gt; Undo, with the deleted files kept in the
 * local history); they run in the background. Open editors of moved or deleted files are closed first (and
 * reopened at the new location).
 */
public final class ProjectOperations {

	private final IViewSite site;
	private final TreeViewer viewer;

	ProjectOperations(IViewSite site, TreeViewer viewer) {
		this.site = site;
		this.viewer = viewer;
	}

	private Shell shell() {
		return site.getShell();
	}

	private IWorkbenchPage page() {
		return site.getPage();
	}

	// ----------------------------------------------------------------------------------------------------
	// Selection and editors
	// ----------------------------------------------------------------------------------------------------

	/** Selects resources (once they are in the tree), expanding their folders. */
	void select(List<? extends IResource> resources) {
		List<IResource> existing = resources.stream().filter(r -> r != null && r.exists()).map(IResource.class::cast)
				.toList();
		if (existing.isEmpty() || viewer.getControl().isDisposed()) {
			return;
		}
		existing.stream().filter(IFile.class::isInstance).map(IFile.class::cast).forEach(ProjectsContent::rememberDocument);
		viewer.refresh();
		viewer.setSelection(new StructuredSelection(existing), true);
	}

	/** The open editors of files at or below a resource. */
	private List<IEditorPart> editorsOf(IResource resource) {
		Path location = HermesWorkspace.location(resource);
		List<IEditorPart> editors = new ArrayList<>();
		if (location == null) {
			return editors;
		}
		for (IEditorReference reference : page().getEditorReferences()) {
			IEditorPart editor = reference.getEditor(false);
			Path file = editor == null ? null : EditorInputs.pathOf(editor.getEditorInput());
			if (file != null && file.startsWith(location)) {
				editors.add(editor);
			}
		}
		return editors;
	}

	/**
	 * Closes the editors of files at or below the resources before they are moved or deleted. Refuses (and tells
	 * the user) if one of them has unsaved changes.
	 *
	 * @return the files whose editors were closed, or {@code null} if refused
	 */
	private List<Path> closeEditors(List<? extends IResource> resources, String operation) {
		List<IEditorPart> editors = resources.stream().flatMap(resource -> editorsOf(resource).stream()).toList();
		for (IEditorPart editor : editors) {
			if (editor.isDirty()) {
				MessageDialog.openInformation(shell(), operation,
						"\"" + editor.getTitle() + "\" has unsaved changes. Save or close it first.");
				return null;
			}
		}
		List<Path> closed = new ArrayList<>();
		for (IEditorPart editor : editors) {
			closed.add(EditorInputs.pathOf(editor.getEditorInput()));
			page().closeEditor(editor, false);
		}
		return closed;
	}

	void openFile(Path file) {
		HlomEditorAdvisor.openEditor(site.getWorkbenchWindow().getWorkbench(),
				URI.createFileURI(file.toAbsolutePath().toString()));
	}

	void open(List<IFile> files) {
		files.stream().map(HermesWorkspace::location).forEach(this::openFile);
	}

	/**
	 * Runs an undoable workspace operation in the background (with its own scheduling rules); then, in the UI
	 * thread, the follow-up.
	 */
	private void execute(AbstractWorkspaceOperation operation, String title, Runnable after) {
		IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
		var uiInfo = WorkspaceUndoUtil.getUIInfoAdapter(shell());
		Display display = shell().getDisplay();
		Job job = Job.create(operation.getLabel(), monitor -> {
			try {
				IStatus status = history.execute(operation, monitor, uiInfo);
				if (status.isOK() && after != null) {
					display.asyncExec(after);
				}
				return status.getSeverity() == IStatus.ERROR ? status : Status.OK_STATUS;
			} catch (ExecutionException e) {
				display.asyncExec(() -> Errors.show(shell(), title, NLS.bind(Messages.ProjectOperations_Failed, operation.getLabel()), e));
				return Status.OK_STATUS;
			}
		});
		job.setUser(true);
		job.schedule();
	}

	// ----------------------------------------------------------------------------------------------------
	// Projects
	// ----------------------------------------------------------------------------------------------------

	/** Shows a project (opening it in the workspace if needed) and selects it. */
	void addProject(HermesProject project) {
		try {
			IProject opened = HermesWorkspace.open(project.getFolder(), null);
			viewer.refresh();
			viewer.setExpandedState(opened, true);
			viewer.setSelection(new StructuredSelection(opened), true);
		} catch (CoreException e) {
			Errors.show(shell(), Messages.ProjectOperations_OpenProject, Messages.ProjectOperations_TheProjectCouldNotBe, e);
		}
	}

	/** Opens the New Project wizard. */
	void newProject() {
		NewProjectWizard.open(site.getWorkbenchWindow());
	}

	/** Asks for a project folder and opens it. */
	void openProject() {
		DirectoryDialog dialog = new DirectoryDialog(shell());
		dialog.setText(Messages.ProjectOperations_OpenProject);
		dialog.setMessage(Messages.ProjectOperations_ChooseAProjectFolderOr);
		String chosen = dialog.open();
		if (chosen == null) {
			return;
		}
		try {
			Path folder = Path.of(chosen);
			HermesProject project = HermesProject.open(folder);
			if (!HermesProject.isProject(folder)) {
				if (!MessageDialog.openQuestion(shell(), Messages.ProjectOperations_OpenProject, "\"" + folder
						+ "\" is not a project yet.\n\nMake it a project? A project file (" + HermesProject.PROJECT_FILE
						+ ") is written to the folder; its documents are not changed.")) {
					return;
				}
				project.save();
			}
			addProject(project);
		} catch (IOException e) {
			Errors.show(shell(), Messages.ProjectOperations_OpenProject, Messages.ProjectOperations_TheProjectCouldNotBe, e);
		}
	}

	/** Removes projects from the workspace (Close Project); their files stay on the disk. */
	void closeProjects(List<IProject> projects) {
		String what = projects.size() == 1 ? "the project \"" + HermesWorkspace.name(projects.getFirst()) + "\""
				: projects.size() + " projects";
		if (!MessageDialog.openConfirm(shell(), Messages.ProjectOperations_CloseProject,
				NLS.bind(Messages.ProjectOperations_RemoveFromTheProjectsView, what, (projects.size() == 1 ? "it" : "them")))) {
			return;
		}
		if (closeEditors(projects, Messages.ProjectOperations_CloseProject) == null) {
			return;
		}
		for (IProject project : projects) {
			try {
				HermesWorkspace.close(project, null);
			} catch (CoreException e) {
				Errors.show(shell(), Messages.ProjectOperations_CloseProject, "\"" + HermesWorkspace.name(project) + "\" could not be closed.", e);
			}
		}
		viewer.refresh();
	}

	/** Opens the properties of a project on its page (name, description and document format). */
	void editProperties(IProject project) {
		PreferencesUtil.createPropertyDialogOn(shell(), project, ProjectPropertyPage.ID, null, null).open();
		viewer.refresh();
	}

	/** Re-reads the project folders from the disk and validates all their documents again (in the background). */
	void revalidate() {
		ProjectsContent.forgetChecks();
		WorkspaceJob job = new WorkspaceJob(Messages.ProjectOperations_ValidatingTheProjects) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				List<IProject> projects = HermesWorkspace.projects();
				SubMonitor progress = SubMonitor.convert(monitor, projects.size() * 2);
				for (IProject project : projects) {
					project.refreshLocal(IResource.DEPTH_INFINITE, progress.split(1));
					project.build(IncrementalProjectBuilder.FULL_BUILD, HermesWorkspace.BUILDER_ID, java.util.Map.of(),
							progress.split(1));
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(HermesWorkspace.root());
		job.schedule();
	}

	// ----------------------------------------------------------------------------------------------------
	// Documents and folders
	// ----------------------------------------------------------------------------------------------------

	/** Creates a document in a folder with the New Document wizard (in the project's format). */
	void newDocument(IContainer folder) {
		HermesProject project = folder == null ? null : HermesWorkspace.metadata(folder.getProject());
		if (project == null) {
			MessageDialog.openInformation(shell(), Messages.ProjectOperations_NewDocument,
					Messages.ProjectOperations_SelectTheProjectOrA);
			return;
		}
		NewHlomDocumentWizard wizard = new NewHlomDocumentWizard();
		wizard.init(site.getWorkbenchWindow().getWorkbench(), StructuredSelection.EMPTY);
		wizard.setInitialFolder(HermesWorkspace.location(folder).toFile());
		wizard.setInitialFormat(project.getFormat() == Format.JSON);
		wizard.setOnCreated(file -> Display.getDefault().asyncExec(() -> {
			HermesWorkspace.refresh(file.toPath().getParent());
			IFile created = HermesWorkspace.fileAt(file.toPath());
			if (created != null) {
				ProjectsContent.rememberDocument(created);
				viewer.refresh();
				viewer.setExpandedState(created.getParent(), true);
				select(List.of(created));
			}
		}));
		new WizardDialog(shell(), wizard).open();
	}

	/** Asks for documents and copies them into a folder. */
	void importFiles(IContainer folder) {
		if (folder == null) {
			MessageDialog.openInformation(shell(), Messages.ProjectOperations_AddDocuments,
					Messages.ProjectOperations_SelectTheProjectOrA2);
			return;
		}
		FileDialog dialog = new FileDialog(shell(), SWT.OPEN | SWT.MULTI);
		dialog.setText(NLS.bind(Messages.ProjectOperations_AddDocumentsTo, HermesWorkspace.name(folder.getProject())));
		dialog.setFilterExtensions(new String[] { Messages.ProjectOperations_XmlJsonLom, "*.*" });
		dialog.setFilterNames(new String[] { Messages.ProjectOperations_MedBiquitousDocumentsXmlJsonLom, Messages.ProjectOperations_AllFiles });
		if (dialog.open() == null) {
			return;
		}
		importInto(folder, Stream.of(dialog.getFileNames()).map(name -> Path.of(dialog.getFilterPath(), name)).toList());
	}

	/** Copies documents into a folder (files that aren't documents are reported and skipped). */
	void importInto(IContainer folder, List<Path> files) {
		HermesProject project = HermesWorkspace.metadata(folder.getProject());
		Path target = HermesWorkspace.location(folder);
		if (project == null || target == null) {
			return;
		}
		List<Path> documents = files.stream()
				.filter(file -> Files.isRegularFile(file) && HlomLibrary.isHlomDocument(file))
				.toList();
		List<String> skipped = files.stream()
				.filter(file -> !documents.contains(file))
				.map(file -> file.getFileName().toString())
				.toList();
		try {
			List<Path> copies = project.importFiles(documents, target);
			folder.refreshLocal(IResource.DEPTH_ONE, null);
			if (!copies.isEmpty()) {
				viewer.setExpandedState(folder, true);
				select(copies.stream().map(HermesWorkspace::fileAt).toList());
			}
		} catch (IOException | CoreException e) {
			Errors.show(shell(), Messages.ProjectOperations_AddDocuments, Messages.ProjectOperations_TheDocumentsCouldNotBe, e);
		}
		if (!skipped.isEmpty()) {
			MessageDialog.openInformation(shell(), Messages.ProjectOperations_AddDocuments,
					NLS.bind(Messages.ProjectOperations_TheseFilesAreNotMedBiquitous, String.join("\n", skipped)));
		}
	}

	/** Asks for a name and creates a folder (undoable). */
	void newFolder(IContainer parent) {
		if (parent == null) {
			return;
		}
		InputDialog dialog = new InputDialog(shell(), Messages.ProjectOperations_NewFolder, Messages.ProjectOperations_FolderName, "",
				value -> validName(parent, value, null));
		if (dialog.open() != Window.OK) {
			return;
		}
		IFolder folder = parent.getFolder(IPath.fromOSString(dialog.getValue().strip()));
		execute(new CreateFolderOperation(folder, null, Messages.ProjectOperations_NewFolder), Messages.ProjectOperations_NewFolder, () -> {
			viewer.setExpandedState(parent, true);
			select(List.of(folder));
		});
	}

	/** An error message if a name can't be used for a new entry of a folder, else {@code null}. */
	static String validName(IContainer folder, String value, String currentName) {
		String name = value == null ? "" : value.strip();
		if (name.isEmpty()) {
			return Messages.ProjectOperations_EnterAName;
		}
		if (name.matches(".*[\\\\/:*?\"<>|].*")) {
			return Messages.ProjectOperations_TheNameCanTContain;
		}
		Path location = HermesWorkspace.location(folder);
		if (!name.equals(currentName) && location != null && Files.exists(location.resolve(name))
				&& !name.equalsIgnoreCase(currentName)) {
			return "\"" + name + "\" exists already.";
		}
		return null;
	}

	/** Renames a document or folder (undoable); a project's name is edited in its properties. */
	void rename(IResource resource) {
		if (resource instanceof IProject project) {
			editProperties(project);
			return;
		}
		String current = resource.getName();
		boolean file = resource instanceof IFile;
		String extension = file && current.lastIndexOf('.') > 0 ? current.substring(current.lastIndexOf('.')) : "";
		InputDialog dialog = new InputDialog(shell(), file ? "Rename Document" : "Rename Folder", Messages.ProjectOperations_NewName, current,
				value -> {
					String error = validName(resource.getParent(), value, current);
					if (error == null && file && !value.strip().toLowerCase(Locale.ROOT).endsWith(
							extension.toLowerCase(Locale.ROOT))) {
						error = NLS.bind(Messages.ProjectOperations_KeepTheExtensionUseConvert, extension);
					}
					return error;
				});
		if (dialog.open() != Window.OK || dialog.getValue().strip().equals(current)) {
			return;
		}
		IPath target = resource.getFullPath().removeLastSegments(1).append(dialog.getValue().strip());
		move(List.of(resource), List.of(target), Messages.ProjectOperations_Rename);
	}

	/** Moves documents and folders into a folder (undoable; drag and drop in the view). */
	void moveInto(List<IResource> resources, IContainer folder) {
		List<IResource> moved = resources.stream()
				.filter(resource -> !(resource instanceof IProject) && !resource.getParent().equals(folder)
						&& !resource.getFullPath().isPrefixOf(folder.getFullPath()))
				.toList();
		if (!moved.isEmpty()) {
			move(moved, moved.stream().map(resource -> folder.getFullPath().append(resource.getName())).toList(),
					Messages.ProjectOperations_Move);
		}
	}

	private void move(List<IResource> resources, List<IPath> targets, String title) {
		List<Path> oldLocations = resources.stream().map(HermesWorkspace::location).toList();
		List<Path> closed = closeEditors(resources, title);
		if (closed == null) {
			return;
		}
		String label = resources.size() == 1 ? title + " \"" + resources.getFirst().getName() + "\""
				: title + " " + resources.size() + " Items";
		AbstractWorkspaceOperation operation = resources.size() == 1
				? new MoveResourcesOperation(resources.getFirst(), targets.getFirst(), label)
				: new MoveResourcesOperation(resources.toArray(IResource[]::new),
						targets.getFirst().removeLastSegments(1), label);
		execute(operation, title, () -> {
			List<IResource> results = new ArrayList<>();
			for (int i = 0; i < resources.size(); i++) {
				IResource result = HermesWorkspace.root().findMember(targets.get(i));
				results.add(result);
				Path oldLocation = oldLocations.get(i);
				Path newLocation = result == null ? null : HermesWorkspace.location(result);
				for (Path old : closed) {
					if (oldLocation != null && old.startsWith(oldLocation)) {
						RecentDocuments.remove(old.toString());
						if (newLocation != null) {
							openFile(newLocation.resolve(oldLocation.relativize(old)));
						}
					}
				}
			}
			select(results);
		});
	}

	/** Deletes documents and folders (undoable: the files are kept in the local history); projects are closed. */
	void delete(List<IResource> resources) {
		if (resources.isEmpty()) {
			return;
		}
		if (resources.stream().allMatch(IProject.class::isInstance)) {
			closeProjects(resources.stream().map(IProject.class::cast).toList());
			return;
		}
		List<IResource> deleted = resources.stream().filter(resource -> !(resource instanceof IProject)).toList();
		String what = deleted.size() == 1 ? "\"" + deleted.getFirst().getName() + "\"" : deleted.size() + " items";
		boolean folders = deleted.stream().anyMatch(IFolder.class::isInstance);
		if (!MessageDialog.openConfirm(shell(), Messages.ProjectOperations_Delete,
				"Delete " + what + (folders ? " (with all contents of the folders)" : "")
						+ " from the disk?\n\nEdit > Undo restores " + (deleted.size() == 1 ? "it." : "them."))) {
			return;
		}
		if (closeEditors(deleted, Messages.ProjectOperations_Delete) == null) {
			return;
		}
		List<Path> locations = deleted.stream().map(HermesWorkspace::location).toList();
		String label = deleted.size() == 1 ? "Delete \"" + deleted.getFirst().getName() + "\""
				: "Delete " + deleted.size() + " Items";
		execute(new DeleteResourcesOperation(deleted.toArray(IResource[]::new), label, false), Messages.ProjectOperations_Delete,
				() -> locations.stream().filter(java.util.Objects::nonNull)
						.forEach(location -> RecentDocuments.remove(location.toString())));
	}

	/** Converts documents to the other format (XML or JSON), reopening their editors. */
	void convert(List<IFile> selected, Format target) {
		List<IFile> files = selected.stream().filter(file -> ProjectsLabels.isJson(file) != (target == Format.JSON))
				.toList();
		if (files.isEmpty()) {
			return;
		}
		List<Path> closed = closeEditors(files, Messages.ProjectOperations_Convert);
		if (closed == null) {
			return;
		}
		List<IFile> converted = new ArrayList<>();
		List<String> failed = new ArrayList<>();
		for (IFile file : files) {
			Path location = HermesWorkspace.location(file);
			try {
				Path result = HermesProject.convert(location, target).toAbsolutePath().normalize();
				file.getParent().refreshLocal(IResource.DEPTH_ONE, null);
				converted.add(HermesWorkspace.fileAt(result));
				RecentDocuments.remove(location.toString());
				if (closed.contains(location)) {
					openFile(result);
				}
			} catch (IOException | CoreException | RuntimeException e) {
				failed.add(file.getName() + ": " + e.getMessage());
			}
		}
		select(converted);
		if (!failed.isEmpty()) {
			MessageDialog.openWarning(shell(), Messages.ProjectOperations_Convert,
					NLS.bind(Messages.ProjectOperations_SomeDocumentsCouldNotBe, String.join("\n", failed)));
		}
	}

	/** Shows a resource in the file manager of the system (the IDE's command, configurable in the preferences). */
	void showInFileManager(IResource resource) {
		IHandlerService handlers = site.getService(IHandlerService.class);
		try {
			handlers.executeCommand("org.eclipse.ui.ide.showInSystemExplorer", null);
			return;
		} catch (org.eclipse.core.commands.common.CommandException _) {
			// Not available: open the folder.
		}
		Path location = HermesWorkspace.location(resource instanceof IFile ? resource.getParent() : resource);
		if (location != null) {
			Program.launch(location.toString());
		}
	}
}
