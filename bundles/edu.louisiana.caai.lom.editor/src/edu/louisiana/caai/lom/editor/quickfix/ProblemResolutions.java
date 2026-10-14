/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;

/**
 * The quick fixes of {@value HermesWorkspace#PROBLEM_MARKER} markers in the Problems view (Ctrl+1, <em>Quick
 * Fix</em> in the context menu): the same {@link HlomFix fixes} as in the editor. A fix of a document open in an
 * editor runs as an undoable command of that editor; otherwise the document is fixed and saved (the previous
 * version stays in the local history), and the builder validates it again. Fixes can be applied to several
 * markers at once ("Select All" in the Quick Fix dialog).
 */
public final class ProblemResolutions implements IMarkerResolutionGenerator2 {

	/** The checks of files by modification stamp, so that the Problems view's queries don't revalidate. */
	private static final Map<IFile, Checked> CHECKS = new ConcurrentHashMap<>();

	private record Checked(long stamp, String text, DocumentCheck.Result result) {
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return !fixes(marker).isEmpty();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		List<IMarkerResolution> resolutions = new ArrayList<>();
		for (HlomFix fix : fixes(marker)) {
			resolutions.add(fix.kind() == HlomFix.Kind.NAVIGATE ? new Navigate(fix) : new Change(fix.label(), fix));
		}
		return resolutions.toArray(IMarkerResolution[]::new);
	}

	/** The fixes of a marker's problem (as of the saved file). */
	private static List<HlomFix> fixes(IMarker marker) {
		HlomProblem problem = problem(marker);
		return problem == null ? List.of() : HlomFixes.forProblem(problem);
	}

	/** The problem of the saved file a marker reports, or {@code null}. */
	static HlomProblem problem(IMarker marker) {
		if (!(marker.getResource() instanceof IFile file)) {
			return null;
		}
		Checked checked = check(file);
		return checked == null ? null : find(placed(checked.result()), marker);
	}

	/** The problems of a check at the lines and columns of the file as written (those of the markers). */
	private static List<HlomProblem> placed(DocumentCheck.Result result) {
		return result.problems().stream().map(ProblemResolutions::placed).toList();
	}

	private static HlomProblem placed(Located located) {
		HlomProblem problem = located.problem();
		return located.range() == null ? problem : new HlomProblem(problem.severity(), problem.origin(),
				problem.message(), problem.object(), problem.path(), located.range().line(), located.range().column());
	}

	/** The problem of a list that a marker reports: the same message at the same element (or line). */
	static HlomProblem find(List<HlomProblem> problems, IMarker marker) {
		String message = marker.getAttribute(IMarker.MESSAGE, "");
		String path = marker.getAttribute(HermesWorkspace.ELEMENT_PATH, (String) null);
		int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		HlomProblem byMessage = null;
		for (HlomProblem problem : problems) {
			if (!message.equals(problem.message())) {
				continue;
			}
			String problemPath = problem.path() != null || problem.object() == null || problem.object().eResource() == null
					? problem.path() : ElementPath.of(problem.object()).toString();
			if (path != null && path.equals(problemPath) || path == null && line > 0 && line == problem.line()) {
				return problem;
			}
			byMessage = byMessage == null ? problem : byMessage;
		}
		return path == null && line <= 0 ? byMessage : null;
	}

	private static Checked check(IFile file) {
		Path location = HermesWorkspace.location(file);
		if (location == null || !file.exists()) {
			return null;
		}
		long stamp = file.getModificationStamp();
		Checked checked = CHECKS.get(file);
		if (checked != null && checked.stamp() == stamp) {
			return checked;
		}
		try {
			String text = Files.readString(location, StandardCharsets.UTF_8);
			if (text.startsWith("﻿")) {
				text = text.substring(1);
			}
			checked = new Checked(stamp, text, DocumentCheck.check(text, URI.createFileURI(location.toString())));
			CHECKS.put(file, checked);
			return checked;
		} catch (IOException | RuntimeException e) {
			return null;
		}
	}

	private static IWorkbenchPage page() {
		IWorkbenchWindow window = PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				: null;
		return window == null ? null : window.getActivePage();
	}

	/** The editor a file is open in, in any window, or {@code null}. */
	private static IEditorPart openEditor(IFile file) {
		if (!PlatformUI.isWorkbenchRunning()) {
			return null;
		}
		FileEditorInput input = new FileEditorInput(file);
		for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			for (IWorkbenchPage page : window.getPages()) {
				IEditorPart editor = page.findEditor(input);
				if (editor != null) {
					return editor;
				}
			}
		}
		return null;
	}

	/** A fix that changes documents; also applies to the other markers with the same fix. */
	private static final class Change extends WorkbenchMarkerResolution {
		private final String label;
		private final HlomFix fix;

		Change(String label, HlomFix fix) {
			this.label = label;
			this.fix = fix;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public String getDescription() {
			return fix.description();
		}

		@Override
		public Image getImage() {
			return QuickFixImages.bulb();
		}

		@Override
		public IMarker[] findOtherMarkers(IMarker[] markers) {
			List<IMarker> others = new ArrayList<>();
			for (IMarker marker : markers) {
				if (fixes(marker).stream().anyMatch(other -> other.kind() == HlomFix.Kind.CHANGE
						&& other.getClass() == fix.getClass() && Objects.equals(other.label(), label))) {
					others.add(marker);
				}
			}
			return others.toArray(IMarker[]::new);
		}

		@Override
		public void run(IMarker marker) {
			run(new IMarker[] { marker }, null);
		}

		@Override
		public void run(IMarker[] markers, IProgressMonitor monitor) {
			Map<IFile, List<IMarker>> byFile = new LinkedHashMap<>();
			for (IMarker marker : markers) {
				if (marker.exists() && marker.getResource() instanceof IFile file) {
					byFile.computeIfAbsent(file, _ -> new ArrayList<>()).add(marker);
				}
			}
			SubMonitor progress = SubMonitor.convert(monitor, Messages.ProblemResolutions_ApplyingQuickFixes, byFile.size());
			byFile.forEach((file, fileMarkers) -> {
				progress.subTask(file.getName());
				try {
					if (!fixInEditor(file, fileMarkers)) {
						fixFile(file, fileMarkers);
					}
				} catch (CoreException | IOException | RuntimeException e) {
					HlomEditorPlugin.INSTANCE.log(e);
				}
				progress.worked(1);
			});
		}

		/** Fixes the document of an open editor with an undoable command; {@code false} if it isn't open. */
		private boolean fixInEditor(IFile file, List<IMarker> markers) {
			IEditorPart editor = openEditor(file);
			if (!(editor instanceof ValidationSource source) || !QuickFixes.canModify(source)) {
				return false;
			}
			List<HlomFixes.Planned> plan = plan(source.getProblems(), markers);
			QuickFixes.apply(source, plan.size() == 1 ? "Quick Fix: " + label : "Fix " + plan.size() + " Problems",
					plan);
			return true;
		}

		/** Fixes and saves a document that isn't open; the builder then validates it again. */
		private void fixFile(IFile file, List<IMarker> markers) throws CoreException, IOException {
			Checked checked = check(file);
			DocumentRoot root = checked == null ? null : checked.result().root();
			if (root == null) {
				return;
			}
			List<HlomFixes.Planned> plan = plan(placed(checked.result()), markers);
			if (plan.isEmpty()) {
				return;
			}
			HlomFixes.apply(root, plan);
			String fixed = checked.text().stripLeading().startsWith("{") ? HlomResources.toJson(checked.result().resource())
					: HlomResources.toXml(checked.result().resource());
			CHECKS.remove(file);
			file.setContents(new ByteArrayInputStream(fixed.getBytes(StandardCharsets.UTF_8)),
					IResource.KEEP_HISTORY | IResource.FORCE, null);
		}

		private List<HlomFixes.Planned> plan(List<HlomProblem> problems, List<IMarker> markers) {
			List<HlomFixes.Planned> plan = new ArrayList<>();
			for (IMarker marker : markers) {
				HlomProblem problem = find(problems, marker);
				if (problem == null) {
					continue;
				}
				HlomFixes.forProblem(problem).stream()
						.filter(candidate -> candidate.getClass() == fix.getClass() && label.equals(candidate.label()))
						.findFirst().ifPresent(candidate -> plan.add(new HlomFixes.Planned(problem, candidate)));
			}
			return plan;
		}
	}

	/** A fix that only shows the field the author has to complete: opens the document at the marker. */
	private static final class Navigate implements IMarkerResolution2 {
		private final HlomFix fix;

		Navigate(HlomFix fix) {
			this.fix = fix;
		}

		@Override
		public String getLabel() {
			return fix.label();
		}

		@Override
		public String getDescription() {
			return fix.description();
		}

		@Override
		public Image getImage() {
			return QuickFixImages.navigate();
		}

		@Override
		public void run(IMarker marker) {
			IWorkbenchPage page = page();
			if (page != null) {
				try {
					IDE.openEditor(page, marker);
				} catch (PartInitException e) {
					HlomEditorPlugin.INSTANCE.log(e);
				}
			}
		}
	}
}
