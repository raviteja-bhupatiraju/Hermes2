/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.exporter;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;

/**
 * File &gt; Export &gt; Hermes &gt; Documents as XML or JSON: copies the selected documents to a folder (keeping
 * the project and folder structure) in XML, in JSON (for standards with a JSON form), or as they are, in the
 * canonical layout.
 */
public final class DocumentsExportWizard extends Wizard implements IExportWizard {

	private static final String FORMAT = "format";
	private static final String OVERWRITE = "overwrite";

	private enum Target {
		XML, JSON, SAME
	}

	private IStructuredSelection selection;
	private Page page;

	public DocumentsExportWizard() {
		setWindowTitle(Messages.DocumentsExportWizard_Export);
		setNeedsProgressMonitor(true);
		IDialogSettings settings = HlomEditorPlugin.getPlugin().getDialogSettings();
		setDialogSettings(settings.getSection("DocumentsExport") != null ? settings.getSection("DocumentsExport")
				: settings.addNewSection("DocumentsExport"));
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		page = new Page(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		List<IFile> documents = page.documents();
		Path destination = page.destination();
		Target target = page.xml.getSelection() ? Target.XML : page.json.getSelection() ? Target.JSON : Target.SAME;
		boolean overwrite = page.overwrite.getSelection();
		page.saveSettings();
		getDialogSettings().put(FORMAT, target.name());
		getDialogSettings().put(OVERWRITE, overwrite);
		List<String> skipped = new ArrayList<>();
		try {
			getContainer().run(true, true, monitor -> {
				SubMonitor progress = SubMonitor.convert(monitor, Messages.DocumentsExportWizard_ExportingDocuments, documents.size());
				for (IFile file : documents) {
					if (progress.isCanceled()) {
						throw new OperationCanceledException();
					}
					progress.subTask(file.getFullPath().toString());
					try {
						String note = export(file, destination, target, overwrite);
						if (note != null) {
							skipped.add(file.getFullPath().makeRelative() + ": " + note);
						}
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
					progress.worked(1);
				}
			});
		} catch (InvocationTargetException e) {
			Errors.show(getShell(), Messages.DocumentsExportWizard_ExportDocuments, Messages.DocumentsExportWizard_TheDocumentsCouldNotBe, e.getCause());
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		if (!skipped.isEmpty()) {
			MessageDialog.openInformation(getShell(), Messages.DocumentsExportWizard_ExportDocuments, "Some documents were not exported as asked:\n\n"
					+ String.join("\n", skipped.subList(0, Math.min(20, skipped.size())))
					+ (skipped.size() > 20 ? "\n… and " + (skipped.size() - 20) + " more" : ""));
		}
		return true;
	}

	/** Exports one document; returns why it was skipped or changed, or {@code null}. */
	private static String export(IFile file, Path destination, Target target, boolean overwrite) throws IOException {
		Path location = HermesWorkspace.location(file);
		String text = Files.readString(location, StandardCharsets.UTF_8);
		boolean json = text.stripLeading().startsWith("{");
		Resource resource = DocumentCheck.load(text, URI.createFileURI(location.toString()));
		if (!resource.getErrors().isEmpty() || resource.getContents().isEmpty()) {
			return Messages.DocumentsExportWizard_NotReadableRepairItFirst;
		}
		DocumentType type = DocumentTypes.of(resource);
		String note = null;
		boolean toJson = switch (target) {
			case XML -> false;
			case SAME -> json;
			case JSON -> {
				if (type != null && type.supportsJson()) {
					yield true;
				}
				note = "exported as XML (" + (type == null ? "the standard" : type.label()) + " has no JSON form)";
				yield false;
			}
		};
		String name = file.getName();
		String base = name.lastIndexOf('.') > 0 ? name.substring(0, name.lastIndexOf('.')) : name;
		Path folder = destination.resolve(file.getParent().getFullPath().makeRelative().toString());
		Path output = folder.resolve(base + (toJson ? ".json" : ".xml"));
		if (Files.exists(output) && !overwrite) {
			return NLS.bind(Messages.DocumentsExportWizard_SkippedExists, output.getFileName());
		}
		Files.createDirectories(folder);
		Files.writeString(output, toJson ? HlomResources.toJson(resource) : HlomResources.toXml(resource),
				StandardCharsets.UTF_8);
		return note;
	}

	private final class Page extends DocumentExportPage {
		Button xml;
		Button json;
		Button same;
		Button overwrite;

		Page(IStructuredSelection selection) {
			super("documents", Messages.DocumentsExportWizard_DocumentsAsXMLOrJSON,
					Messages.DocumentsExportWizard_CopyDocumentsToAFolder, selection);
		}

		@Override
		protected void createOptions(Composite group) {
			String format = getDialogSettings().get(FORMAT);
			xml = radio(group, Messages.DocumentsExportWizard_AsXML, Target.XML.name().equals(format));
			json = radio(group, Messages.DocumentsExportWizard_AsJSONHealthcareLOMOther, Target.JSON.name().equals(format));
			same = radio(group, Messages.DocumentsExportWizard_InTheirCurrentFormat, format == null || Target.SAME.name().equals(format));
			overwrite = new Button(group, SWT.CHECK);
			overwrite.setText(Messages.DocumentsExportWizard_OverwriteExistingFilesWithoutWarning);
			overwrite.setSelection(getDialogSettings().getBoolean(OVERWRITE));
			GridDataFactory.fillDefaults().indent(0, 6).applyTo(overwrite);
		}

		private Button radio(Composite group, String label, boolean selected) {
			Button button = new Button(group, SWT.RADIO);
			button.setText(label);
			button.setSelection(selected);
			return button;
		}
	}
}
