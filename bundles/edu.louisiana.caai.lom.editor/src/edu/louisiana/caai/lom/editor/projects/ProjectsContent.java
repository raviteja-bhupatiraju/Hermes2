/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesBuilder;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;

/**
 * The content of the Projects view (a navigator content extension): the Hermes projects of the workspace, their
 * folders and their documents. Other files are left out. Whether a file is a document comes from the document index
 * of the validation (see {@link DocumentIndex}); files the validation hasn't seen yet are checked in the background,
 * so that the tree never reads files in the UI thread.
 */
public final class ProjectsContent implements ICommonContentProvider {

	/** Whether a file is a document, by modification stamp. */
	private record DocumentCheck(long stamp, boolean document) {
	}

	/** The checks of files the validation hasn't indexed (shared by all viewers: it is a fact of the files). */
	private static final Map<IFile, DocumentCheck> checks = new ConcurrentHashMap<>();

	/** The files to check in the background. */
	private static final Set<IFile> pending = ConcurrentHashMap.newKeySet();

	/** The viewers to refresh after background checks. */
	private static final Set<ProjectsContent> instances = ConcurrentHashMap.newKeySet();

	private static final Job checkJob = Job.create("Recognizing documents", monitor -> {
		boolean found = false;
		for (IFile file : Set.copyOf(pending)) {
			if (monitor.isCanceled()) {
				break;
			}
			pending.remove(file);
			boolean document = HermesBuilder.isDocument(file);
			checks.put(file, new DocumentCheck(file.getModificationStamp(), document));
			found |= document;
		}
		if (found) {
			instances.forEach(ProjectsContent::scheduleRefresh);
		}
		return Status.OK_STATUS;
	});

	static {
		checkJob.setSystem(true);
	}

	private final IResourceChangeListener resourceListener = _ -> scheduleRefresh();
	private final Consumer<Set<IFile>> indexListener = _ -> scheduleRefresh();
	private volatile boolean refreshScheduled;
	private StructuredViewer viewer;

	/** Records that a file is a document (created or imported by the view), so that it is listed at once. */
	static void rememberDocument(IFile file) {
		if (file != null) {
			checks.put(file, new DocumentCheck(file.getModificationStamp(), true));
		}
	}

	/** Forgets the checks (Refresh: the files are checked again). */
	static void forgetChecks() {
		checks.clear();
	}

	/** Whether a file is listed: a document of the index, or one found by a check of its current content. */
	static boolean isDocument(IFile file) {
		if (DocumentIndex.get(file) != null) {
			return true;
		}
		DocumentCheck check = checks.get(file);
		if (check != null && check.stamp() == file.getModificationStamp()) {
			return check.document();
		}
		if (pending.add(file)) {
			checkJob.schedule(50);
		}
		return false;
	}

	@Override
	public void init(ICommonContentExtensionSite site) {
		// Nothing to configure.
	}

	@Override
	public void inputChanged(Viewer newViewer, Object oldInput, Object newInput) {
		if (viewer == null && newViewer instanceof StructuredViewer structured) {
			viewer = structured;
			instances.add(this);
			HermesWorkspace.workspace().addResourceChangeListener(resourceListener, IResourceChangeEvent.POST_CHANGE);
			DocumentIndex.addListener(indexListener);
		}
	}

	@Override
	public void dispose() {
		instances.remove(this);
		HermesWorkspace.workspace().removeResourceChangeListener(resourceListener);
		DocumentIndex.removeListener(indexListener);
		viewer = null;
	}

	/** Refreshes the tree once for a burst of changes, keeping the expanded folders. */
	private void scheduleRefresh() {
		StructuredViewer current = viewer;
		if (refreshScheduled || current == null || current.getControl().isDisposed()) {
			return;
		}
		refreshScheduled = true;
		Display display = current.getControl().getDisplay();
		display.asyncExec(() -> display.timerExec(150, () -> {
			refreshScheduled = false;
			if (viewer != null && !viewer.getControl().isDisposed()) {
				viewer.refresh();
			}
		}));
	}

	@Override
	public Object[] getElements(Object input) {
		return HermesWorkspace.projects().toArray();
	}

	@Override
	public Object[] getChildren(Object element) {
		if (!(element instanceof IContainer container) || !container.isAccessible()) {
			return new Object[0];
		}
		IResource[] members;
		try {
			members = container.members();
		} catch (CoreException _) {
			return new Object[0];
		}
		return Stream.of(members)
				.filter(member -> member instanceof IFolder folder && !HermesBuilder.isSkipped(folder)
						|| member instanceof IFile file && isDocument(file))
				.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return element instanceof IResource resource && !(resource instanceof IProject) ? resource.getParent() : null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return switch (element) {
			case IProject _ -> true;
			case IFolder folder -> {
				try {
					yield folder.isAccessible() && folder.members().length > 0;
				} catch (CoreException _) {
					yield false;
				}
			}
			default -> false;
		};
	}

	@Override
	public void restoreState(IMemento memento) {
		// No state.
	}

	@Override
	public void saveState(IMemento memento) {
		// No state.
	}
}
