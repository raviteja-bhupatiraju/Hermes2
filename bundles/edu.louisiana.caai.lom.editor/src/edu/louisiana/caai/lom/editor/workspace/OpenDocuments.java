/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.ide.IDE;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * The problems of documents opened from outside the Hermes projects, in the Problems view. While such a document is
 * open, it is linked into the hidden project {@value #PROJECT}, so that the problems its editor shows can be markers
 * of a workspace file: the Problems view lists them under the document's name (in a folder named after the
 * document's folder) and opens them in the editor. The link is removed when the editor closes; removing a link
 * leaves the document itself alone.
 */
public final class OpenDocuments {

	/** The hidden project of the links. */
	public static final String PROJECT = "Open Documents";

	/** The problems to show for each document, or nothing for documents that were closed (in order of the calls). */
	private static final Map<Path, Optional<List<HlomProblem>>> pending = new LinkedHashMap<>();

	private static final WorkspaceJob job = new WorkspaceJob("Updating the Problems view") {
		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			Map<Path, Optional<List<HlomProblem>>> changes;
			synchronized (pending) {
				changes = new LinkedHashMap<>(pending);
				pending.clear();
			}
			IProject project = project();
			changes.forEach((document, problems) -> {
				try {
					if (problems.isPresent()) {
						show(project, document, problems.get());
					} else {
						remove(project, document);
					}
				} catch (CoreException e) {
					HlomEditorPlugin.INSTANCE.log(e);
				}
			});
			return Status.OK_STATUS;
		}
	};

	static {
		job.setSystem(true);
		job.setRule(HermesWorkspace.root());
	}

	/** Whether the stale links of an earlier session were removed. */
	private static boolean cleaned;

	private OpenDocuments() {
	}

	/** Shows the problems of an open document (in the background). */
	public static void show(Path document, List<HlomProblem> problems) {
		update(document, Optional.of(List.copyOf(problems)));
	}

	/** Removes the problems of a closed document (in the background). */
	public static void remove(Path document) {
		update(document, Optional.empty());
	}

	private static void update(Path document, Optional<List<HlomProblem>> problems) {
		synchronized (pending) {
			pending.remove(document);
			pending.put(document, problems);
		}
		job.schedule(100);
	}

	private static IProject project() throws CoreException {
		IProject project = HermesWorkspace.root().getProject(PROJECT);
		if (!project.exists()) {
			project.create(HermesWorkspace.workspace().newProjectDescription(PROJECT), IResource.HIDDEN, null);
		}
		if (!project.isOpen()) {
			project.open(null);
		}
		if (!cleaned) {
			cleaned = true;
			// The links of an earlier session that ended before its editors closed.
			for (IResource member : project.members()) {
				if (member instanceof IFolder folder) {
					for (IResource link : folder.members()) {
						if (link.isLinked()) {
							link.delete(IResource.NONE, null);
						}
					}
					removeIfEmpty(folder);
				}
			}
		}
		return project;
	}

	private static void show(IProject project, Path document, List<HlomProblem> problems) throws CoreException {
		IFile file = link(project, document);
		if (file == null) {
			return;
		}
		file.deleteMarkers(HermesWorkspace.PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
		for (HlomProblem problem : problems) {
			Map<String, Object> attributes = HermesWorkspace.markerAttributes(problem);
			// The problems are those of the editor, not of the file as saved: they go with the editor.
			attributes.put(IMarker.TRANSIENT, true);
			attributes.put(IDE.EDITOR_ID_ATTR, HermesWorkspace.EDITOR_ID);
			file.createMarker(HermesWorkspace.PROBLEM_MARKER, attributes);
		}
	}

	private static void remove(IProject project, Path document) throws CoreException {
		IFile file = find(project, document);
		if (file != null) {
			IContainer folder = file.getParent();
			// Deleting a link deletes the link only, never the document it links to.
			file.delete(IResource.NONE, null);
			if (folder instanceof IFolder linkFolder) {
				removeIfEmpty(linkFolder);
			}
		}
	}

	private static void removeIfEmpty(IFolder folder) throws CoreException {
		if (folder.exists() && folder.members(IContainer.INCLUDE_HIDDEN).length == 0) {
			folder.delete(IResource.NONE, null);
		}
	}

	/** The link to a document, or {@code null}. */
	private static IFile find(IProject project, Path document) {
		return Stream.of(HermesWorkspace.root().findFilesForLocationURI(document.toUri(), IContainer.INCLUDE_HIDDEN))
				.filter(file -> project.equals(file.getProject()) && file.isLinked())
				.findFirst()
				.orElse(null);
	}

	/** The link to a document, made if needed: {@code <folder name>/<file name>}, or {@code null}. */
	private static IFile link(IProject project, Path document) throws CoreException {
		IFile existing = find(project, document);
		if (existing != null) {
			return existing;
		}
		Path parent = document.getParent();
		String folderName = parent == null || parent.getFileName() == null ? "Documents"
				: parent.getFileName().toString();
		for (int i = 1; i < 100; i++) {
			IFolder folder = project.getFolder(i == 1 ? folderName : folderName + " (" + i + ")");
			IFile file = folder.getFile(document.getFileName().toString());
			if (!file.exists()) {
				if (!folder.exists()) {
					folder.create(IResource.NONE, true, null);
				}
				file.createLink(IPath.fromPath(document), IResource.ALLOW_MISSING_LOCAL, null);
				return file;
			}
		}
		return null;
	}
}
