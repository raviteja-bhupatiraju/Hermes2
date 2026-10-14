/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;

/**
 * Drag and drop in the Projects view: files dropped from the file manager on a project or folder are copied into
 * it (as Add Existing Documents); documents and folders dragged within the view are moved (undoable).
 */
public final class ProjectsDropAssistant extends CommonDropAdapterAssistant {

	@Override
	public boolean isSupportedType(TransferData type) {
		return FileTransfer.getInstance().isSupportedType(type) || super.isSupportedType(type);
	}

	/** The folder a drop on an element goes to (a file's folder), or the only project if dropped on empty space. */
	private static IContainer folder(Object target) {
		List<IProject> projects = HermesWorkspace.projects();
		return switch (target) {
			case IContainer container when !(container instanceof org.eclipse.core.resources.IWorkspaceRoot) -> container;
			case IResource resource when resource.getParent() != null -> resource.getParent();
			case null, default -> projects.size() == 1 ? projects.getFirst() : null;
		};
	}

	private static List<IResource> dragged() {
		return LocalSelectionTransfer.getTransfer().getSelection() instanceof IStructuredSelection selection
				? selection.toList().stream().filter(IResource.class::isInstance).map(IResource.class::cast).toList()
				: List.of();
	}

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData type) {
		IContainer folder = folder(target);
		if (folder == null) {
			return Status.CANCEL_STATUS;
		}
		if (FileTransfer.getInstance().isSupportedType(type)) {
			getCommonDropAdapter().overrideOperation(DND.DROP_COPY);
			return Status.OK_STATUS;
		}
		List<IResource> resources = dragged();
		boolean movable = !resources.isEmpty() && resources.stream().allMatch(resource -> !(resource instanceof IProject)
				&& resource.getProject().equals(folder.getProject()) && !resource.getParent().equals(folder)
				&& !resource.getFullPath().isPrefixOf(folder.getFullPath()));
		if (movable) {
			getCommonDropAdapter().overrideOperation(DND.DROP_MOVE);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter adapter, DropTargetEvent event, Object target) {
		IContainer folder = folder(target);
		ProjectsView view = view();
		if (folder == null || view == null) {
			return Status.CANCEL_STATUS;
		}
		if (FileTransfer.getInstance().isSupportedType(event.currentDataType) && event.data instanceof String[] names) {
			view.operations().importInto(folder, Stream.of(names).map(Path::of).toList());
			return Status.OK_STATUS;
		}
		List<IResource> resources = dragged();
		if (!resources.isEmpty()) {
			view.operations().moveInto(resources, folder);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private static ProjectsView view() {
		var window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPart part = window == null || window.getActivePage() == null ? null
				: window.getActivePage().findView(ProjectsView.ID);
		return part instanceof ProjectsView view ? view : null;
	}
}
