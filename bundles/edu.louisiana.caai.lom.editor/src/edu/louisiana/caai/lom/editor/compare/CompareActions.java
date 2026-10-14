/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import org.eclipse.osgi.util.NLS;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import edu.louisiana.caai.lom.editor.workspace.EditorInputs;

/**
 * The compare commands: compare the active document with a file or with its saved version, or compare two
 * files. Each opens the {@link CompareView}; the nested classes are the menu and tool bar action delegates.
 */
public final class CompareActions {

	private static final String TITLE = "Compare";
	private static final String[] EXTENSIONS = { "*.xml;*.json;*.lom", "*.*" };
	private static final String[] EXTENSION_NAMES = { "MedBiquitous documents (*.xml, *.json, *.lom)",
			"All files (*.*)" };

	private CompareActions() {
	}

	/** The active editor's document (live model) compared with a file chosen by the user. */
	public static void compareWithFile(IWorkbenchWindow window) {
		CompareSide editor = activeEditorSide(window);
		if (editor == null) {
			MessageDialog.openInformation(window.getShell(), TITLE,
					Messages.CompareActions_OpenAMedBiquitousDocumentTo);
			return;
		}
		String file = chooseFile(window.getShell(), NLS.bind(Messages.CompareActions_CompareWith, editor.fileName()),
				parentOf(editor.path()));
		if (file != null) {
			CompareView.open(window, editor, CompareSide.file(file));
		}
	}

	/** The saved version of the active document (left) compared with its live, possibly unsaved model (right). */
	public static void compareWithSaved(IWorkbenchWindow window) {
		CompareSide editor = activeEditorSide(window);
		if (editor == null) {
			MessageDialog.openInformation(window.getShell(), TITLE,
					Messages.CompareActions_OpenAMedBiquitousDocumentTo2);
			return;
		}
		Resource resource = editor.resource();
		java.nio.file.Path saved = resource.getURI() == null ? null : EditorInputs.pathOf(resource.getURI());
		if (saved == null || !java.nio.file.Files.isRegularFile(saved)) {
			MessageDialog.openInformation(window.getShell(), TITLE,
					"“" + editor.fileName() + "” has not been saved yet, so there is no saved version to compare with.");
			return;
		}
		CompareView.open(window, CompareSide.saved(saved.toString()), editor);
	}

	/** Two files chosen by the user (both may be selected at once in the first dialog). */
	public static void compareFiles(IWorkbenchWindow window) {
		Shell shell = window.getShell();
		FileDialog dialog = dialog(shell, SWT.OPEN | SWT.MULTI, Messages.CompareActions_SelectTwoMedBiquitousDocuments, null);
		if (dialog.open() == null) {
			return;
		}
		String[] names = dialog.getFileNames();
		String first = new File(dialog.getFilterPath(), names[0]).getPath();
		String second = names.length > 1 ? new File(dialog.getFilterPath(), names[1]).getPath()
				: chooseFile(shell, NLS.bind(Messages.CompareActions_CompareWith, names[0]), dialog.getFilterPath());
		if (second != null) {
			CompareView.open(window, CompareSide.file(first), CompareSide.file(second));
		}
	}

	/** Compares two files without asking, e.g. from other views. */
	public static CompareView compareFiles(IWorkbenchWindow window, String left, String right) {
		return CompareView.open(window, CompareSide.file(left), CompareSide.file(right));
	}

	private static CompareSide activeEditorSide(IWorkbenchWindow window) {
		if (window == null || window.getActivePage() == null) {
			return null;
		}
		IEditorPart editor = window.getActivePage().getActiveEditor();
		return editor == null ? null : CompareSide.editor(editor);
	}

	private static String chooseFile(Shell shell, String title, String folder) {
		return dialog(shell, SWT.OPEN, title, folder).open();
	}

	private static FileDialog dialog(Shell shell, int style, String title, String folder) {
		FileDialog dialog = new FileDialog(shell, style);
		dialog.setText(title);
		dialog.setFilterExtensions(EXTENSIONS);
		dialog.setFilterNames(EXTENSION_NAMES);
		if (folder != null) {
			dialog.setFilterPath(folder);
		}
		return dialog;
	}

	private static String parentOf(String path) {
		File parent = path == null ? null : new File(path).getParentFile();
		return parent != null && parent.isDirectory() ? parent.getPath() : null;
	}

	/** Base class of the action delegates. */
	private abstract static class Delegate implements IWorkbenchWindowActionDelegate {
		private IWorkbenchWindow window;

		@Override
		public void init(IWorkbenchWindow workbenchWindow) {
			this.window = workbenchWindow;
		}

		@Override
		public void run(IAction action) {
			if (window != null) {
				run(window);
			}
		}

		abstract void run(IWorkbenchWindow workbenchWindow);

		@Override
		public void selectionChanged(IAction action, ISelection selection) {
			// Always enabled; the commands explain when they need an open document.
		}

		@Override
		public void dispose() {
			window = null;
		}
	}

	/** File &gt; Compare &gt; Compare With File... */
	public static class CompareWithFileAction extends Delegate {
		@Override
		void run(IWorkbenchWindow workbenchWindow) {
			compareWithFile(workbenchWindow);
		}
	}

	/** File &gt; Compare &gt; Compare With Saved Version (also on the tool bar). */
	public static class CompareWithSavedAction extends Delegate {
		@Override
		void run(IWorkbenchWindow workbenchWindow) {
			compareWithSaved(workbenchWindow);
		}
	}

	/** File &gt; Compare &gt; Compare Two Files... */
	public static class CompareFilesAction extends Delegate {
		@Override
		void run(IWorkbenchWindow workbenchWindow) {
			compareFiles(workbenchWindow);
		}
	}
}
