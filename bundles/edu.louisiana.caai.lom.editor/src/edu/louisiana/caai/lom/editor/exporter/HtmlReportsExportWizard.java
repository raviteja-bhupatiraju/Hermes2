/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.exporter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.library.HtmlExport;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.HtmlReport;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * File &gt; Export &gt; Hermes &gt; HTML Reports: a printable catalog card per document and a sortable library
 * report ({@code index.html}) linking them, for the selected projects, folders and documents.
 */
public final class HtmlReportsExportWizard extends Wizard implements IExportWizard {

	private static final String CARDS = "cards";
	private static final String LIBRARY = "library";
	private static final String OPEN = "open";

	private IStructuredSelection selection;
	private Page page;

	public HtmlReportsExportWizard() {
		setWindowTitle(Messages.HtmlReportsExportWizard_Export);
		setNeedsProgressMonitor(true);
		IDialogSettings settings = HlomEditorPlugin.getPlugin().getDialogSettings();
		setDialogSettings(settings.getSection("HtmlReportsExport") != null ? settings.getSection("HtmlReportsExport")
				: settings.addNewSection("HtmlReportsExport"));
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
		boolean cards = page.cards.getSelection();
		boolean library = page.library.getSelection();
		boolean open = page.open.getSelection();
		page.saveSettings();
		getDialogSettings().put(CARDS, cards);
		getDialogSettings().put(LIBRARY, library);
		getDialogSettings().put(OPEN, open);
		Path[] first = new Path[1];
		try {
			getContainer().run(true, true, monitor -> {
				SubMonitor progress = SubMonitor.convert(monitor, Messages.HtmlReportsExportWizard_ExportingHTMLReports, documents.size() + 1);
				try {
					Files.createDirectories(destination);
					List<LibraryEntry> entries = new ArrayList<>();
					Set<String> names = new HashSet<>();
					for (IFile file : documents) {
						if (progress.isCanceled()) {
							throw new OperationCanceledException();
						}
						progress.subTask(file.getName());
						LibraryEntry entry = HlomLibrary.load(HermesWorkspace.location(file));
						entries.add(entry);
						if (cards) {
							Path card = destination.resolve(unique(names, entry.summary().title() != null
									? entry.summary().title() : file.getName()));
							Files.writeString(card, HtmlReport.document(entry), StandardCharsets.UTF_8);
							first[0] = first[0] == null ? card : first[0];
						}
						progress.worked(1);
					}
					if (library) {
						Path index = destination.resolve("index.html");
						Files.writeString(index, HtmlReport.library(entries, destination.toString()),
								StandardCharsets.UTF_8);
						first[0] = index;
					}
					progress.worked(1);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}
			});
		} catch (InvocationTargetException e) {
			Errors.show(getShell(), Messages.HtmlReportsExportWizard_ExportHTMLReports, Messages.HtmlReportsExportWizard_TheReportsCouldNotBe, e.getCause());
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		if (open && first[0] != null) {
			Program.launch(first[0].toString());
		}
		return true;
	}

	/** A file name for a card that no other card of the export has. */
	private static String unique(Set<String> names, String title) {
		String name = HtmlExport.fileName(title);
		String base = name.substring(0, name.length() - ".html".length());
		for (int i = 2; !names.add(name) || name.equals("index.html"); i++) {
			name = base + "-" + i + ".html";
		}
		return name;
	}

	private final class Page extends DocumentExportPage {
		Button cards;
		Button library;
		Button open;

		Page(IStructuredSelection selection) {
			super("htmlReports", Messages.HtmlReportsExportWizard_HTMLReports,
					Messages.HtmlReportsExportWizard_ExportAPrintableCatalogCard, selection);
		}

		@Override
		protected void createOptions(Composite group) {
			cards = check(group, Messages.HtmlReportsExportWizard_ACatalogCardPerDocument, CARDS);
			library = check(group, Messages.HtmlReportsExportWizard_ALibraryReportOfAll, LIBRARY);
			open = check(group, Messages.HtmlReportsExportWizard_OpenTheReportInThe, OPEN);
			cards.addListener(SWT.Selection, _ -> updatePageCompletion());
			library.addListener(SWT.Selection, _ -> updatePageCompletion());
		}

		private Button check(Composite group, String label, String key) {
			Button button = new Button(group, SWT.CHECK);
			button.setText(label);
			button.setSelection(getDialogSettings().get(key) == null || getDialogSettings().getBoolean(key));
			GridDataFactory.fillDefaults().applyTo(button);
			return button;
		}

		@Override
		protected boolean validateOptionsGroup() {
			if (!cards.getSelection() && !library.getSelection()) {
				setErrorMessage(Messages.HtmlReportsExportWizard_ChooseTheCatalogCardsThe);
				return false;
			}
			return true;
		}
	}
}
