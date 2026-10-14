/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import org.eclipse.osgi.util.NLS;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Validates the Healthcare LOM documents (XML and JSON) of a Hermes project whenever they change, reports their
 * problems as {@value HermesWorkspace#PROBLEM_MARKER} markers and keeps the {@link DocumentIndex} up to date.
 */
public final class HermesBuilder extends IncrementalProjectBuilder {

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		IResourceDelta delta = kind == FULL_BUILD ? null : getDelta(project);
		Set<IFile> changed = new LinkedHashSet<>();
		try {
			if (delta == null || !DocumentIndex.isIndexed(project)) {
				fullBuild(project, changed, monitor);
			} else {
				incrementalBuild(delta, changed, monitor);
			}
		} finally {
			DocumentIndex.changed(changed);
		}
		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		getProject().deleteMarkers(HermesWorkspace.PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		DocumentIndex.forget(getProject());
	}

	private void fullBuild(IProject project, Set<IFile> changed, IProgressMonitor monitor) throws CoreException {
		Set<IFile> candidates = new LinkedHashSet<>();
		project.accept(resource -> switch (resource) {
			case IFile file -> {
				if (isCandidate(file)) {
					candidates.add(file);
				}
				yield false;
			}
			case IContainer container -> !isSkipped(container);
			default -> false;
		});
		SubMonitor progress = SubMonitor.convert(monitor, NLS.bind(Messages.HermesBuilder_Validating, HermesWorkspace.name(project)),
				candidates.size());
		for (IFile file : candidates) {
			checkCanceled(progress);
			progress.subTask(file.getProjectRelativePath().toString());
			validate(file);
			changed.add(file);
			progress.worked(1);
		}
		// Documents that are gone or no candidates any more (renamed to another extension, moved below a skipped folder).
		for (DocumentIndex.Document document : DocumentIndex.documents(project)) {
			if (!candidates.contains(document.file())) {
				DocumentIndex.remove(document.file());
				changed.add(document.file());
			}
		}
		DocumentIndex.markIndexed(project);
	}

	private void incrementalBuild(IResourceDelta delta, Set<IFile> changed, IProgressMonitor monitor)
			throws CoreException {
		Set<IFile> validate = new LinkedHashSet<>();
		delta.accept(child -> switch (child.getResource()) {
			case IFile file when child.getKind() == IResourceDelta.REMOVED -> {
				DocumentIndex.remove(file);
				changed.add(file);
				yield false;
			}
			case IFile file when HermesProject.PROJECT_FILE.equals(file.getName())
					&& file.getParent() instanceof IProject owner -> {
				HermesWorkspace.forgetMetadata(owner);
				yield false;
			}
			case IFile file when (child.getFlags() & (IResourceDelta.CONTENT | IResourceDelta.REPLACED)) != 0
					|| child.getKind() == IResourceDelta.ADDED -> {
				validate.add(file);
				yield false;
			}
			case IFile _ -> false;
			case IContainer container -> !isSkipped(container);
			default -> true;
		});
		SubMonitor progress = SubMonitor.convert(monitor, validate.size());
		for (IFile file : validate) {
			checkCanceled(progress);
			if (isCandidate(file)) {
				validate(file);
			} else {
				file.deleteMarkers(HermesWorkspace.PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
				DocumentIndex.remove(file);
			}
			changed.add(file);
			progress.worked(1);
		}
	}

	private void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled() || isInterrupted()) {
			throw new OperationCanceledException();
		}
	}

	/** Whether a folder is left out of the project (hidden, build output and dependency folders). */
	public static boolean isSkipped(IContainer container) {
		if (container instanceof IProject) {
			return false;
		}
		String name = container.getName();
		return name.startsWith(".") || name.equals("target") || name.equals("bin") || name.equals("node_modules")
				|| container.isDerived() || container.isTeamPrivateMember();
	}

	/** Whether a file has an HLOM extension (it is a document if its content is HLOM, see {@link #isDocument}). */
	private static boolean isCandidate(IFile file) {
		Path location = HermesWorkspace.location(file);
		return location != null && !HermesProject.PROJECT_FILE.equals(file.getName())
				&& HlomLibrary.hasHlomExtension(location) && file.exists();
	}

	/** Whether a file is a Healthcare LOM document (reads the beginning of the file). */
	public static boolean isDocument(IFile file) {
		return DocumentIndex.get(file) != null || isCandidate(file) && HlomLibrary.isHlomDocument(HermesWorkspace.location(file));
	}

	private static void validate(IFile file) throws CoreException {
		file.deleteMarkers(HermesWorkspace.PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
		Path location = HermesWorkspace.location(file);
		if (!HlomLibrary.isHlomDocument(location)) {
			DocumentIndex.remove(file);
			return;
		}
		// The same check as the command line tool and the language server: lines are those of the file as written.
		LibraryEntry entry = HlomLibrary.load(location);
		boolean showInfos = HlomPreferences.isShowInfoProblems();
		for (HlomProblem problem : entry.problems()) {
			if (problem.severity() == Severity.INFO && !showInfos) {
				continue;
			}
			file.createMarker(HermesWorkspace.PROBLEM_MARKER, HermesWorkspace.markerAttributes(problem));
		}
		DocumentIndex.put(file, entry);
	}
}
