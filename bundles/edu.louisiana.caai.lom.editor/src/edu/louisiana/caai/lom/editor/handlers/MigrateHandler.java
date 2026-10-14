/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.medbiq.model.Migrations;
import edu.louisiana.caai.medbiq.model.Migrations.Migration;

/**
 * File &gt; Convert to Newer Version: writes the active document in the newer version of its standard (Curriculum
 * Inventory v10, Activity Report 2.0) as a new file, opens it and lists what the author still has to do. The
 * original document is not changed.
 */
public final class MigrateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		Shell shell = HandlerUtil.getActiveShell(event);
		if (!(HandlerUtil.getActiveEditor(event) instanceof HlomMetadataEditor editor)) {
			MessageDialog.openInformation(shell, "Convert to Newer Version", "Open the document to convert first.");
			return null;
		}
		List<Migration> migrations = Migrations.of(editor.documentType().id());
		if (migrations.isEmpty()) {
			MessageDialog.openInformation(shell, "Convert to Newer Version", "This " + editor.documentType().label()
					+ " document is of the newest version of its standard.");
			return null;
		}
		if (!editor.applySourcePages() || editor.documentRoot() == null) {
			return null;
		}
		Migration migration = migrations.getFirst();
		Migrations.Result result;
		try {
			result = Migrations.migrate(migration, editor.documentRoot());
		} catch (IOException | RuntimeException e) {
			Errors.show(shell, "Convert to Newer Version", "The document could not be converted.", e);
			return null;
		}
		Path source = EditorInputs.pathOf(editor.getEditorInput());
		String name = editor.getEditorInput().getName();
		String base = name.lastIndexOf('.') > 0 ? name.substring(0, name.lastIndexOf('.')) : name;
		FileDialog dialog = new FileDialog(shell, SWT.SAVE | SWT.SHEET);
		dialog.setText("Save as " + migration.label());
		dialog.setFilterExtensions(new String[] { "*.xml" });
		dialog.setFileName(base + "-" + migration.target().version().replaceAll("[^\\w.]+", "-") + ".xml");
		if (source != null && source.getParent() != null) {
			dialog.setFilterPath(source.getParent().toString());
		}
		dialog.setOverwrite(true);
		String chosen = dialog.open();
		if (chosen == null) {
			return null;
		}
		Path target = Path.of(chosen);
		try {
			Files.writeString(target, result.xml(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			Errors.show(shell, "Convert to Newer Version", "The converted document could not be saved.", e);
			return null;
		}
		HermesWorkspace.refresh(target.getParent());
		HlomEditorAdvisor.openEditor(HandlerUtil.getActiveWorkbenchWindow(event).getWorkbench(),
				URI.createFileURI(target.toString()));
		if (!result.notes().isEmpty()) {
			MessageDialog.openInformation(shell, "Converted to " + migration.label(), "The document was converted to "
					+ migration.label() + ". Please check:\n\n• " + String.join("\n• ", result.notes()));
		}
		return null;
	}
}
