/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.exporter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
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
import edu.louisiana.caai.lom.model.support.report.Crosswalks;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;

/**
 * File &gt; Export &gt; MedBiquitous &gt; Metadata Crosswalks: the Healthcare LOM records among the selected
 * documents as unqualified Dublin Core ({@code .dc.xml}, for OAI-PMH repositories) and schema.org JSON-LD
 * ({@code .jsonld}, for web pages, search engines and learning registries), keeping the folder structure.
 */
public final class CrosswalkExportWizard extends Wizard implements IExportWizard {

	private static final String DUBLIN_CORE = "dublinCore";
	private static final String SCHEMA_ORG = "schemaOrg";

	private IStructuredSelection selection;
	private Page page;

	public CrosswalkExportWizard() {
		setWindowTitle("Export");
		setNeedsProgressMonitor(true);
		IDialogSettings settings = HlomEditorPlugin.getPlugin().getDialogSettings();
		setDialogSettings(settings.getSection("CrosswalkExport") != null ? settings.getSection("CrosswalkExport")
				: settings.addNewSection("CrosswalkExport"));
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
		boolean dublinCore = page.dublinCore.getSelection();
		boolean schemaOrg = page.schemaOrg.getSelection();
		page.saveSettings();
		getDialogSettings().put(DUBLIN_CORE, dublinCore);
		getDialogSettings().put(SCHEMA_ORG, schemaOrg);
		int[] counts = new int[2];
		try {
			getContainer().run(true, true, monitor -> {
				SubMonitor progress = SubMonitor.convert(monitor, "Exporting metadata", documents.size());
				for (IFile file : documents) {
					if (progress.isCanceled()) {
						throw new OperationCanceledException();
					}
					progress.subTask(file.getFullPath().toString());
					try {
						Path location = HermesWorkspace.location(file);
						Resource resource = DocumentCheck.load(Files.readString(location, StandardCharsets.UTF_8),
								URI.createFileURI(location.toString()));
						if (HlomResources.getDocumentRoot(resource) == null) {
							counts[1]++;
						} else {
							HlomSummary summary = HlomSummary.of(resource);
							String name = file.getName();
							String base = name.lastIndexOf('.') > 0 ? name.substring(0, name.lastIndexOf('.')) : name;
							Path folder = destination.resolve(file.getParent().getFullPath().makeRelative().toString());
							Files.createDirectories(folder);
							if (dublinCore) {
								Files.writeString(folder.resolve(base + ".dc.xml"), Crosswalks.dublinCore(summary),
										StandardCharsets.UTF_8);
							}
							if (schemaOrg) {
								Files.writeString(folder.resolve(base + ".jsonld"), Crosswalks.schemaOrg(summary),
										StandardCharsets.UTF_8);
							}
							counts[0]++;
						}
						resource.unload();
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
					progress.worked(1);
				}
			});
		} catch (InvocationTargetException e) {
			Errors.show(getShell(), "Export Metadata", "The metadata could not be exported.", e.getCause());
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		if (counts[1] > 0) {
			MessageDialog.openInformation(getShell(), "Export Metadata", counts[0] + " Healthcare LOM records were "
					+ "exported; " + counts[1] + " documents of other standards have no crosswalk and were left out.");
		}
		return true;
	}

	private final class Page extends DocumentExportPage {
		Button dublinCore;
		Button schemaOrg;

		Page(IStructuredSelection selection) {
			super("crosswalks", "Metadata Crosswalks", "Export Healthcare LOM records as Dublin Core (OAI-PMH) and "
					+ "schema.org JSON-LD (web pages, search engines, learning registries).", selection);
		}

		@Override
		protected void createOptions(Composite group) {
			dublinCore = check(group, "&Dublin Core (oai_dc, .dc.xml)", DUBLIN_CORE);
			schemaOrg = check(group, "&schema.org JSON-LD (LRMI, .jsonld)", SCHEMA_ORG);
			dublinCore.addListener(SWT.Selection, _ -> updatePageCompletion());
			schemaOrg.addListener(SWT.Selection, _ -> updatePageCompletion());
		}

		private Button check(Composite group, String label, String key) {
			Button button = new Button(group, SWT.CHECK);
			button.setText(label);
			button.setSelection(getDialogSettings().get(key) == null || getDialogSettings().getBoolean(key));
			return button;
		}

		@Override
		protected boolean validateOptionsGroup() {
			if (!dublinCore.getSelection() && !schemaOrg.getSelection()) {
				setErrorMessage("Choose Dublin Core, schema.org or both.");
				return false;
			}
			return true;
		}
	}
}
