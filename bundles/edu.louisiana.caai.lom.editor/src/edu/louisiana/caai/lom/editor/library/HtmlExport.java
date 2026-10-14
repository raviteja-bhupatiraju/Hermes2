/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import edu.louisiana.caai.lom.editor.Errors;
import java.util.List;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorPart;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import org.eclipse.ui.IWorkbenchWindow;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.report.CompletenessScorer;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.HtmlReport;

/**
 * Saves generated HTML reports: asks for a file, writes it as UTF-8 and opens it in the system browser.
 */
public final class HtmlExport {

	private HtmlExport() {
	}

	/**
	 * Asks where to save an HTML report, writes it and opens it in the browser.
	 *
	 * @param suggestedName file name proposed in the save dialog (sanitized, {@code .html} is appended)
	 * @param folder        initial folder of the dialog, or {@code null}
	 * @return the saved file, or {@code null} if canceled or failed
	 */
	public static Path saveAndOpen(Shell shell, String title, String suggestedName, Path folder, String html) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE | SWT.SHEET);
		dialog.setText(title);
		dialog.setFilterExtensions(new String[] { "*.html", "*.*" });
		dialog.setFilterNames(new String[] { Messages.HtmlExport_HTMLDocumentsHtml, Messages.HtmlExport_AllFiles });
		dialog.setFileName(fileName(suggestedName));
		dialog.setOverwrite(true);
		if (folder != null) {
			dialog.setFilterPath(folder.toString());
		}
		String selected = dialog.open();
		if (selected == null) {
			return null;
		}
		Path file = Path.of(selected);
		if (!file.getFileName().toString().contains(".")) {
			file = file.resolveSibling(file.getFileName() + ".html");
		}
		return write(shell, file, html) ? file : null;
	}

	/** Writes the HTML and opens it in the system browser; reports failures in a dialog. */
	public static boolean write(Shell shell, Path file, String html) {
		try {
			Files.writeString(file, html, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Errors.show(shell, Messages.HtmlExport_ExportAsHTML, Messages.HtmlExport_TheReportCouldNotBe, e);
			return false;
		}
		Program.launch(file.toAbsolutePath().toString());
		return true;
	}

	/** A file system friendly name derived from a title, e.g. {@code insulin-pump-therapy.html}. */
	public static String fileName(String title) {
		String base = title == null ? "" : title.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "-")
				.replaceAll("(^-+|-+$)", "");
		if (base.length() > 60) {
			base = base.substring(0, 60).replaceAll("-+$", "");
		}
		return (base.isEmpty() ? "hlom-record" : base) + ".html";
	}

	/** Exports the document of the window's active editor. */
	public static void exportActiveDocument(IWorkbenchWindow window) {
		if (window == null || window.getActivePage() == null) {
			return;
		}
		IEditorPart editor = window.getActivePage().getActiveEditor();
		Resource resource = editor instanceof IEditingDomainProvider provider
				&& !provider.getEditingDomain().getResourceSet().getResources().isEmpty()
						? provider.getEditingDomain().getResourceSet().getResources().getFirst()
						: null;
		DocumentRoot root = HlomResources.getDocumentRoot(resource);
		if (root == null) {
			MessageDialog.openInformation(window.getShell(), Messages.HtmlExport_ExportAsHTML,
					Messages.HtmlExport_OpenAHealthcareLOMDocument);
			return;
		}
		List<HlomProblem> problems = editor instanceof ValidationSource source ? source.getProblems()
				: HlomValidator.validate(resource);
		CompletenessScorer scorer = HlomLibrary.getCompletenessScorer();
		int completeness = scorer == null ? -1 : scorer.score(root);
		URI uri = resource.getURI();
		Path file = uri == null ? null : EditorInputs.pathOf(uri);
		String source = uri == null ? null : file != null ? file.toString() : uri.toString();
		HlomSummary summary = HlomSummary.of(root);
		String html = HtmlReport.document(summary, problems, source, completeness);
		Path folder = file != null ? file.getParent() : null;
		String name = summary.title() != null ? summary.title()
				: uri != null && uri.lastSegment() != null ? uri.trimFileExtension().lastSegment() : null;
		HtmlExport.saveAndOpen(window.getShell(), Messages.HtmlExport_ExportAsHTML, name, folder, html);
	}
}
