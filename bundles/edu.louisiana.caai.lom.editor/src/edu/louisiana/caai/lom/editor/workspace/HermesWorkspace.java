/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * Hermes projects in the Eclipse workspace. A Hermes project is a workspace project with the {@link HermesNature}
 * whose content is a folder anywhere on the disk; its name, description and document format stay in the folder's
 * {@value HermesProject#PROJECT_FILE}, so a project folder can be opened again (or by another copy of Hermes) without
 * the workspace. The {@link HermesBuilder} validates the documents in the background and reports their problems as
 * {@value #PROBLEM_MARKER} markers.
 */
public final class HermesWorkspace {

	/** The nature of Hermes projects. */
	public static final String NATURE_ID = "edu.louisiana.caai.lom.editor.hermesNature";

	/** The builder that validates the documents of Hermes projects. */
	public static final String BUILDER_ID = "edu.louisiana.caai.lom.editor.hermesBuilder";

	/** The marker type of validation problems (a problem marker, shown in the Problems view). */
	public static final String PROBLEM_MARKER = "edu.louisiana.caai.lom.editor.problem";

	/** Marker attribute: the XML element path of the problem, for example {@code /lom/general/title}. */
	public static final String ELEMENT_PATH = "elementPath";

	/** Marker attribute: the check that reported the problem ({@code HlomProblem.Origin#label()}). */
	public static final String ORIGIN = "origin";

	/** The editor of the documents. */
	public static final String EDITOR_ID = "edu.louisiana.caai.lom.editor.HlomMetadataEditor";

	/** The project metadata ({@value HermesProject#PROJECT_FILE}), read once per project. */
	private static final Map<IProject, HermesProject> metadata = new ConcurrentHashMap<>();

	private HermesWorkspace() {
	}

	public static IWorkspace workspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkspaceRoot root() {
		return workspace().getRoot();
	}

	// ----------------------------------------------------------------------------------------------------
	// Projects
	// ----------------------------------------------------------------------------------------------------

	/** Whether a project is an open Hermes project. */
	public static boolean isHermesProject(IProject project) {
		try {
			return project.isOpen() && project.hasNature(NATURE_ID);
		} catch (CoreException _) {
			return false;
		}
	}

	/** The open Hermes projects, by name. */
	public static List<IProject> projects() {
		return Stream.of(root().getProjects())
				.filter(HermesWorkspace::isHermesProject)
				.sorted(Comparator.comparing(project -> name(project).toLowerCase(Locale.ROOT)))
				.toList();
	}

	/**
	 * The project's metadata (for a folder without a project file, defaults named after the folder), or
	 * {@code null} if the project folder can't be read.
	 */
	public static HermesProject metadata(IProject project) {
		HermesProject cached = metadata.get(project);
		if (cached != null) {
			return cached;
		}
		Path folder = location(project);
		if (folder == null) {
			return null;
		}
		try {
			HermesProject read = HermesProject.open(folder);
			metadata.put(project, read);
			return read;
		} catch (IOException _) {
			return null;
		}
	}

	/** Forgets the cached metadata of a project (after its project file changed). */
	public static void forgetMetadata(IProject project) {
		metadata.remove(project);
	}

	/** The project's display name (from its project file). */
	public static String name(IProject project) {
		HermesProject read = metadata(project);
		return read == null ? project.getName() : read.getName();
	}

	/** The open Hermes project whose folder is the given one, or {@code null}. */
	public static IProject projectAt(Path folder) {
		Path normalized = canonical(folder);
		return Stream.of(root().getProjects())
				.filter(project -> normalized.equals(location(project)))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Opens a project folder as a Hermes project: creates the workspace project (named after the project, its
	 * content staying in the folder) or opens it again, and makes sure it has the nature and builder.
	 */
	public static IProject open(Path folder, IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 3);
		Path normalized = canonical(folder);
		IProject project = projectAt(normalized);
		if (project == null) {
			project = root().getProject(uniqueName(displayName(normalized)));
			IProjectDescription description = workspace().newProjectDescription(project.getName());
			if (!normalized.equals(location(root()).resolve(project.getName()))) {
				description.setLocation(IPath.fromPath(normalized));
			}
			project.create(description, progress.split(1));
		}
		if (!project.isOpen()) {
			project.open(progress.split(1));
		}
		addNature(project, progress.split(1));
		metadata.remove(project);
		return project;
	}

	/** Removes a project from the workspace; its folder and files are kept. */
	public static void close(IProject project, IProgressMonitor monitor) throws CoreException {
		metadata.remove(project);
		DocumentIndex.forget(project);
		project.delete(IResource.NEVER_DELETE_PROJECT_CONTENT | IResource.FORCE, monitor);
	}

	private static String displayName(Path folder) {
		try {
			return HermesProject.open(folder).getName();
		} catch (IOException _) {
			return folder.getFileName() == null ? "Project" : folder.getFileName().toString();
		}
	}

	/** A valid, unused workspace project name derived from a display name. */
	private static String uniqueName(String name) {
		String base = name.replaceAll("[\\\\/:*?\"<>|]", "_").strip();
		if (base.isEmpty() || !workspace().validateName(base, IResource.PROJECT).isOK()) {
			base = Messages.HermesWorkspace_Project;
		}
		String candidate = base;
		for (int i = 2; root().getProject(candidate).exists(); i++) {
			candidate = base + " (" + i + ")";
		}
		return candidate;
	}

	private static void addNature(IProject project, IProgressMonitor monitor) throws CoreException {
		// One operation, so that the description read is the one changed (another job may add the nature too).
		workspace().run(progress -> {
			IProjectDescription description = project.getDescription();
			if (!description.hasNature(NATURE_ID)) {
				description.setNatureIds(Stream.concat(Stream.of(description.getNatureIds()), Stream.of(NATURE_ID))
						.toArray(String[]::new));
				project.setDescription(description, progress);
			}
		}, root(), IWorkspace.AVOID_UPDATE, monitor); // Changing the natures needs the workspace rule.
	}

	/** Adds the builder to a project description (if it isn't there yet). */
	static void addBuilder(IProjectDescription description) {
		if (Stream.of(description.getBuildSpec()).noneMatch(command -> BUILDER_ID.equals(command.getBuilderName()))) {
			ICommand command = description.newCommand();
			command.setBuilderName(BUILDER_ID);
			description.setBuildSpec(Stream.concat(Stream.of(description.getBuildSpec()), Stream.of(command))
					.toArray(ICommand[]::new));
		}
	}

	/** Removes the builder from a project description. */
	static void removeBuilder(IProjectDescription description) {
		description.setBuildSpec(Stream.of(description.getBuildSpec())
				.filter(command -> !BUILDER_ID.equals(command.getBuilderName()))
				.toArray(ICommand[]::new));
	}

	// ----------------------------------------------------------------------------------------------------
	// Files
	// ----------------------------------------------------------------------------------------------------

	/** The file system location of a resource, or {@code null} if it has none (or the workspace isn't running). */
	public static Path location(IResource resource) {
		IPath location = resource.getLocation();
		return location == null ? null : canonical(location.toPath());
	}

	/**
	 * The real path of a file or folder (long names instead of 8.3 short names, the actual case of the names),
	 * for the parts that exist; paths of the same file compare equal.
	 */
	public static Path canonical(Path path) {
		Path absolute = path.toAbsolutePath().normalize();
		Path existing = absolute;
		while (existing != null && !Files.exists(existing)) {
			existing = existing.getParent();
		}
		if (existing == null) {
			return absolute;
		}
		try {
			return existing.toRealPath().resolve(existing.relativize(absolute));
		} catch (IOException _) {
			return absolute;
		}
	}

	/** The workspace file of an existing or future file in a Hermes project, or {@code null}. */
	public static IFile fileAt(Path path) {
		Path normalized = canonical(path);
		return Stream.of(root().findFilesForLocationURI(normalized.toUri()))
				.filter(file -> isHermesProject(file.getProject()))
				.findFirst()
				.orElse(null);
	}

	/** The workspace folder (or project) of a folder in a Hermes project, or {@code null}. */
	public static IContainer containerAt(Path path) {
		Path normalized = canonical(path);
		return Stream.of(root().findContainersForLocationURI(normalized.toUri()))
				.filter(container -> container.getProject() != null && isHermesProject(container.getProject()))
				.min(Comparator.comparingInt(container -> container.getFullPath().segmentCount()))
				.orElse(null);
	}

	/** Brings the workspace up to date with a file or folder changed outside of it (for example with java.nio). */
	public static void refresh(Path path) {
		IResource resource = Files.isDirectory(path) ? containerAt(path) : fileAt(path);
		if (resource == null && path.getParent() != null) {
			resource = containerAt(path.getParent());
		}
		if (resource != null) {
			try {
				resource.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				HlomEditorPlugin.INSTANCE.log(e);
			}
		}
	}

	/** The attributes of the {@value #PROBLEM_MARKER} marker of a problem. */
	public static Map<String, Object> markerAttributes(HlomProblem problem) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(IMarker.MESSAGE, problem.message());
		attributes.put(IMarker.SEVERITY, switch (problem.severity()) {
			case ERROR -> IMarker.SEVERITY_ERROR;
			case WARNING -> IMarker.SEVERITY_WARNING;
			case INFO -> IMarker.SEVERITY_INFO;
		});
		attributes.put(ORIGIN, problem.origin().label());
		attributes.put(IMarker.SOURCE_ID, "Hermes");
		if (problem.path() != null) {
			attributes.put(ELEMENT_PATH, problem.path());
			attributes.put(IMarker.LOCATION, problem.path());
		}
		if (problem.line() > 0) {
			attributes.put(IMarker.LINE_NUMBER, problem.line());
			attributes.putIfAbsent(IMarker.LOCATION, "line " + problem.line());
		}
		return attributes;
	}

	// ----------------------------------------------------------------------------------------------------
	// Start and shutdown
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Starts the workspace support: moves the projects of earlier versions (kept in the preferences) into the
	 * workspace, validates all projects once (the index of their documents isn't saved) and revalidates when the
	 * validation preferences change.
	 */
	public static void startup() {
		// The document types of the other MedBiquitous standards (recognized by the builder and the library).
		MedbiqStandards.init();
		WorkspaceJob job = new WorkspaceJob(Messages.HermesWorkspace_ValidatingMedBiquitousProjects) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				List<File> earlier = HlomPreferences.getProjects();
				SubMonitor progress = SubMonitor.convert(monitor, earlier.size() + 1);
				for (File folder : earlier) {
					if (folder.isDirectory()) {
						try {
							open(folder.toPath(), progress.split(1));
						} catch (CoreException e) {
							HlomEditorPlugin.INSTANCE.log(e);
						}
					}
				}
				if (!earlier.isEmpty()) {
					HlomPreferences.setProjects(List.of());
				}
				// Projects whose folder is missing (deleted, moved away, or on a drive that isn't attached) are closed,
				// not removed: they open again, with their settings (such as a Git repository), once it is back.
				for (IProject project : root().getProjects()) {
					Path location = location(project);
					boolean present = location != null && Files.isDirectory(location);
					try {
						if (project.isOpen() && !present) {
							project.close(null);
						} else if (!project.isOpen() && present) {
							project.open(null);
						}
					} catch (CoreException e) {
						HlomEditorPlugin.INSTANCE.log(e);
					}
				}
				buildAll(IncrementalProjectBuilder.FULL_BUILD, progress.split(1));
				return Status.OK_STATUS;
			}
		};
		job.setRule(root());
		job.setSystem(true);
		job.schedule();
		workspace().addResourceChangeListener(HermesWorkspace::recognizeProjects, IResourceChangeEvent.POST_CHANGE);
		HlomPreferences.addListener(event -> {
			if (HlomPreferences.SHOW_INFO_PROBLEMS.equals(event.getProperty())) {
				rebuild();
			}
		});
	}

	/**
	 * Makes the projects that other plug-ins add Hermes projects if their folder has a
	 * {@value HermesProject#PROJECT_FILE}: for example a project imported from a Git repository (EGit) as a general
	 * project.
	 */
	private static void recognizeProjects(IResourceChangeEvent event) {
		if (event.getDelta() == null) {
			return;
		}
		List<IProject> found = Stream.of(event.getDelta().getAffectedChildren())
				.filter(child -> child.getKind() == IResourceDelta.ADDED || (child.getFlags() & IResourceDelta.OPEN) != 0)
				.map(IResourceDelta::getResource)
				.filter(IProject.class::isInstance)
				.map(IProject.class::cast)
				.filter(project -> project.isOpen() && !project.isHidden() && !isHermesProject(project)
						&& project.getFile(HermesProject.PROJECT_FILE).exists())
				.toList();
		if (found.isEmpty()) {
			return;
		}
		// The description of a project can't be changed while the workspace notifies its listeners.
		WorkspaceJob job = new WorkspaceJob(Messages.HermesWorkspace_RecognizingMedBiquitousProjects) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				for (IProject project : found) {
					if (project.isOpen() && !isHermesProject(project)) {
						addNature(project, monitor);
						metadata.remove(project);
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(root());
		job.setSystem(true);
		job.schedule();
	}

	/** Validates all documents of all Hermes projects again, in the background. */
	public static void rebuild() {
		WorkspaceJob job = new WorkspaceJob(Messages.HermesWorkspace_ValidatingHealthcareLOMProjects) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				buildAll(IncrementalProjectBuilder.FULL_BUILD, monitor);
				return Status.OK_STATUS;
			}
		};
		job.setRule(workspace().getRuleFactory().buildRule());
		job.schedule();
	}

	private static void buildAll(int kind, IProgressMonitor monitor) throws CoreException {
		List<IProject> projects = projects();
		SubMonitor progress = SubMonitor.convert(monitor, projects.size());
		for (IProject project : projects) {
			project.build(kind, BUILDER_ID, Map.of(), progress.split(1));
		}
	}

	/** Saves the workspace state (call when the application shuts down). */
	public static void save() {
		try {
			workspace().save(true, null);
		} catch (CoreException e) {
			HlomEditorPlugin.INSTANCE.log(e);
		}
	}
}
