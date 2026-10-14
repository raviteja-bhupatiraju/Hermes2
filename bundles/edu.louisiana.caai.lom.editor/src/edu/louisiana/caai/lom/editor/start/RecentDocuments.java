/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.service.prefs.BackingStoreException;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;

/**
 * The most recently opened or saved documents (file system paths, newest first), persisted in the instance
 * preferences of the editor bundle.
 */
public final class RecentDocuments {

	/** Preference node (the editor bundle's symbolic name). */
	public static final String NODE = "edu.louisiana.caai.lom.editor";

	/** Preference key holding the newline-separated paths. */
	public static final String KEY = "recentDocuments";

	/** Maximum number of remembered documents. */
	public static final int MAX = 10;

	private static final List<Runnable> LISTENERS = new CopyOnWriteArrayList<>();

	private RecentDocuments() {
	}

	/** The remembered paths, newest first. */
	public static List<String> get() {
		String value = preferences().get(KEY, "");
		List<String> result = new ArrayList<>();
		for (String line : value.split("\n")) {
			String path = line.trim();
			if (!path.isEmpty() && !contains(result, path)) {
				result.add(path);
			}
		}
		return Collections.unmodifiableList(result);
	}

	/** Records a document as most recent. */
	public static void add(String path) {
		if (path == null || path.isBlank()) {
			return;
		}
		String normalized = new File(path.trim()).getAbsolutePath();
		List<String> paths = new ArrayList<>(get());
		paths.removeIf(p -> same(p, normalized));
		paths.add(0, normalized);
		store(paths.size() > MAX ? paths.subList(0, MAX) : paths);
	}

	/** Forgets a document (for example because it no longer exists). */
	public static void remove(String path) {
		List<String> paths = new ArrayList<>(get());
		if (paths.removeIf(p -> same(p, path))) {
			store(paths);
		}
	}

	/** Forgets all documents. */
	public static void clear() {
		store(List.of());
	}

	/** Registers a listener notified (in the UI thread if there is one) whenever the list changes. */
	public static void addListener(Runnable listener) {
		LISTENERS.add(listener);
	}

	/** Removes a listener. */
	public static void removeListener(Runnable listener) {
		LISTENERS.remove(listener);
	}

	/**
	 * Records the documents opened in the window's editors (and their new location after Save As), as long as
	 * their input is a file. Install once per window, e.g. in {@code WorkbenchWindowAdvisor.postWindowOpen()}.
	 */
	public static void track(IWorkbenchWindow window) {
		IPartListener2 listener = new IPartListener2() {
			@Override
			public void partOpened(IWorkbenchPartReference reference) {
				record(reference);
			}

			@Override
			public void partInputChanged(IWorkbenchPartReference reference) {
				record(reference);
			}
		};
		window.getPartService().addPartListener(listener);
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			for (IEditorReference reference : page.getEditorReferences()) {
				record(reference);
			}
		}
	}

	/** The file system path of an editor input, or {@code null} if it isn't a local file. */
	public static String pathOf(IEditorInput input) {
		java.nio.file.Path path = EditorInputs.pathOf(input);
		return path == null ? null : path.toString();
	}

	private static void record(IWorkbenchPartReference reference) {
		if (reference instanceof IEditorReference editorReference) {
			IEditorPart editor = editorReference.getEditor(false);
			IEditorInput input = editor != null ? editor.getEditorInput() : null;
			if (input == null) {
				try {
					input = editorReference.getEditorInput();
				} catch (Exception _) {
					return;
				}
			}
			String path = pathOf(input);
			if (path != null) {
				add(path);
			}
		}
	}

	private static void store(List<String> paths) {
		IEclipsePreferences preferences = preferences();
		preferences.put(KEY, String.join("\n", paths));
		try {
			preferences.flush();
		} catch (BackingStoreException exception) {
			HlomEditorPlugin.INSTANCE.log(exception);
		}
		Display display = Display.getCurrent();
		for (Runnable listener : LISTENERS) {
			if (display == null && Display.getDefault() != null) {
				Display.getDefault().asyncExec(listener);
			} else {
				listener.run();
			}
		}
	}

	private static IEclipsePreferences preferences() {
		return InstanceScope.INSTANCE.getNode(NODE);
	}

	private static boolean contains(List<String> paths, String path) {
		return paths.stream().anyMatch(p -> same(p, path));
	}

	private static boolean same(String a, String b) {
		return File.separatorChar == '\\' ? a.equalsIgnoreCase(b) : a.equals(b);
	}
}
