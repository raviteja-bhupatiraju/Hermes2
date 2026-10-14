/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.nebula.widgets.opal.notifier.Notifier;
import org.eclipse.nebula.widgets.opal.notifier.NotifierColorsFactory.NotifierTheme;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.library.LibraryView;
import edu.louisiana.caai.lom.editor.start.RecentDocuments;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.lom.model.support.importer.ColumnMapping;
import edu.louisiana.caai.lom.model.support.importer.CsvImporter;
import edu.louisiana.caai.lom.model.support.importer.CsvReader;
import edu.louisiana.caai.lom.model.support.importer.CsvTable;
import edu.louisiana.caai.lom.model.support.importer.ImportedRecord;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * Imports Healthcare LOM records from a CSV file (File &gt; Import from CSV): choose the file and a template,
 * map the columns to HLOM fields, review the generated records and write them into a folder. Writing runs in a
 * job; afterwards the folder can be shown in the {@link LibraryView} and the first record opened.
 */
public class CsvImportWizard extends Wizard implements IImportWizard {

	/** Preference of the library folder (see {@code LibraryView.FOLDER_PREFERENCE}). */
	static final String LIBRARY_FOLDER_PREFERENCE = "library.folder";

	private static final String SETTINGS_SECTION = "CsvImportWizard";

	private IWorkbench workbench;
	private final Path initialFile;
	private final IDialogSettings settings;

	private CsvSourcePage sourcePage;
	private CsvMappingPage mappingPage;
	private CsvPreviewPage previewPage;
	private CsvOutputPage outputPage;

	// Import state shared by the pages.
	private Path file;
	private byte[] bytes;
	private Charset charset;
	private Character delimiter;
	private CsvTable table;
	private ColumnMapping mapping;
	private Template template = HlomTemplates.BLANK;
	private String language = HlomDocuments.DEFAULT_LANGUAGE;
	private boolean removePlaceholders = true;
	private int stamp;
	private int previewStamp = -1;
	private CsvImporter importer;
	private List<ImportedRecord> records = List.of();

	/** Creates the wizard. */
	public CsvImportWizard() {
		this(null);
	}

	/** Creates the wizard with a CSV file preselected ({@code null}: the last file). */
	public CsvImportWizard(Path csvFile) {
		this.initialFile = csvFile;
		setWindowTitle(Messages.CsvImportWizard_ImportHLOMRecordsFromCSV);
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
		URL banner = CsvImportWizard.class.getResource("/icons/app/hlom64.png");
		if (banner != null) {
			setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(banner));
		}
		IDialogSettings pluginSettings = HlomEditorPlugin.getPlugin().getDialogSettings();
		IDialogSettings section = pluginSettings.getSection(SETTINGS_SECTION);
		settings = section != null ? section : pluginSettings.addNewSection(SETTINGS_SECTION);
		setDialogSettings(settings);
		template = HlomTemplates.get(settings.get("template"));
		if (settings.get("language") != null) {
			language = settings.get("language");
		}
	}

	@Override
	public void init(IWorkbench workbenchToUse, IStructuredSelection selection) {
		this.workbench = workbenchToUse;
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		HelpContexts.set(pageContainer, HelpContexts.CSV_IMPORT_WIZARD);
	}

	@Override
	public void addPages() {
		sourcePage = new CsvSourcePage(this);
		mappingPage = new CsvMappingPage(this);
		previewPage = new CsvPreviewPage(this);
		outputPage = new CsvOutputPage(this);
		addPage(sourcePage);
		addPage(mappingPage);
		addPage(previewPage);
		addPage(outputPage);
	}

	/** The CSV file to propose first: the one passed to the constructor, else the last one used. */
	Path initialFile() {
		if (initialFile != null) {
			return initialFile;
		}
		String last = settings.get("file");
		return last != null && Files.isRegularFile(Path.of(last)) ? Path.of(last) : null;
	}

	IDialogSettings settings() {
		return settings;
	}

	// ---------------------------------------------------------------------------------------------------------
	// State

	/**
	 * Reads a CSV file with the current delimiter and encoding choices.
	 *
	 * @return an error message, or {@code null} if the file was read
	 */
	String load(Path csv) {
		try {
			byte[] content = Files.readAllBytes(csv);
			file = csv;
			bytes = content;
			return parse();
		} catch (IOException | RuntimeException e) {
			file = csv;
			bytes = null;
			table = null;
			mapping = null;
			changed();
			return NLS.bind(Messages.CsvImportWizard_TheFileCouldNotBe, e.getMessage());
		}
	}

	/** Parses the loaded bytes again (after a delimiter or encoding change), keeping the mapping if possible. */
	String parse() {
		if (bytes == null) {
			return Messages.CsvImportWizard_ChooseACSVFile;
		}
		CsvTable previous = table;
		table = CsvReader.read(bytes, charset, delimiter);
		if (mapping == null || previous == null || !previous.headers().equals(table.headers())) {
			mapping = ColumnMapping.auto(table.headers());
		}
		changed();
		if (table.columnCount() == 0) {
			return Messages.CsvImportWizard_TheFileIsEmpty;
		}
		if (table.rowCount() == 0) {
			return Messages.CsvImportWizard_TheFileHasAHeader;
		}
		return null;
	}

	/** Marks the import settings as changed, so the preview is computed again. */
	void changed() {
		stamp++;
	}

	Path file() {
		return file;
	}

	CsvTable table() {
		return table;
	}

	ColumnMapping mapping() {
		return mapping;
	}

	/** Replaces the mapping (e.g. after "auto-map"). */
	void setMapping(ColumnMapping newMapping) {
		mapping = newMapping;
		changed();
	}

	Template template() {
		return template;
	}

	void setTemplate(Template newTemplate) {
		template = newTemplate;
		changed();
	}

	String language() {
		return language;
	}

	void setLanguage(String newLanguage) {
		language = newLanguage == null || newLanguage.isBlank() ? HlomDocuments.DEFAULT_LANGUAGE : newLanguage.strip();
		changed();
	}

	boolean removePlaceholders() {
		return removePlaceholders;
	}

	void setRemovePlaceholders(boolean remove) {
		removePlaceholders = remove;
		changed();
	}

	/** The charset override, or {@code null} for automatic detection. */
	Charset charset() {
		return charset;
	}

	void setCharset(Charset newCharset) {
		charset = newCharset;
	}

	/** The delimiter override, or {@code null} for automatic detection. */
	Character delimiter() {
		return delimiter;
	}

	void setDelimiter(Character newDelimiter) {
		delimiter = newDelimiter;
	}

	/** The detected (or chosen) character set of the loaded file. */
	Charset effectiveCharset() {
		return table != null && table.charset() != null ? table.charset()
				: bytes != null ? CsvReader.detectCharset(bytes) : null;
	}

	/** The generated records of the last preview. */
	List<ImportedRecord> records() {
		return records;
	}

	CsvImporter importer() {
		return importer;
	}

	/** Computes the preview if the settings changed since the last one (with progress, cancelable). */
	void ensurePreview() {
		if (table == null || mapping == null || previewStamp == stamp) {
			return;
		}
		CsvImporter newImporter = new CsvImporter(table, mapping.copy(), template).defaultLanguage(language)
				.removePlaceholders(removePlaceholders);
		List<ImportedRecord> result = new ArrayList<>();
		try {
			getContainer().run(true, true, monitor -> {
				SubMonitor sub = SubMonitor.convert(monitor, Messages.CsvImportWizard_GeneratingAndValidatingRecords, table.rowCount());
				result.addAll(newImporter.preview(new CsvImporter.Progress() {
					private int reported;

					@Override
					public void worked(int done, int total, String label) {
						sub.subTask(label);
						sub.worked(done - reported);
						reported = done;
					}

					@Override
					public boolean isCanceled() {
						return sub.isCanceled();
					}
				}));
			});
		} catch (InvocationTargetException e) {
			Errors.show(getShell(), getWindowTitle(), Messages.CsvImportWizard_TheRecordsCouldNotBe, e);
			return;
		} catch (InterruptedException _) {
			Thread.currentThread().interrupt();
			return;
		}
		if (result.size() < table.rowCount()) {
			return; // Canceled: keep the old preview and try again next time.
		}
		importer = newImporter;
		records = result;
		previewStamp = stamp;
	}

	// ---------------------------------------------------------------------------------------------------------
	// Finish

	@Override
	public boolean canFinish() {
		return getContainer() != null && getContainer().getCurrentPage() == outputPage && outputPage.isPageComplete()
				&& !records.isEmpty();
	}

	@Override
	public boolean performFinish() {
		Path folder = outputPage.folder();
		String pattern = outputPage.pattern();
		boolean overwrite = outputPage.overwrite();
		boolean showLibrary = outputPage.showLibrary();
		boolean openFirst = outputPage.openFirst();
		saveSettings(folder, pattern, overwrite, showLibrary, openFirst);

		List<ImportedRecord> toWrite = List.copyOf(records);
		CsvImporter writer = importer;
		IWorkbenchWindow window = workbench != null ? workbench.getActiveWorkbenchWindow() : null;
		Display display = getShell().getDisplay();
		// A folder of a Hermes project is written under its scheduling rule and refreshed, so that the workspace
		// (Projects view, validation) sees the new records at once.
		org.eclipse.core.resources.IContainer container = HermesWorkspace.containerAt(folder);
		Job job = Job.create(NLS.bind(Messages.CsvImportWizard_ImportingHLOMRecordsFrom, file.getFileName()), (IProgressMonitor monitor) -> {
			long count = toWrite.stream().filter(ImportedRecord::isIncluded).count();
			SubMonitor sub = SubMonitor.convert(monitor, NLS.bind(Messages.CsvImportWizard_WritingRecordsTo, count, folder), (int) count);
			try {
				CsvImporter.WriteResult result = writer.write(toWrite, folder, pattern, overwrite,
						new CsvImporter.Progress() {
							private int reported;

							@Override
							public void worked(int done, int total, String label) {
								sub.subTask(label);
								sub.worked(done - reported);
								reported = done;
							}

							@Override
							public boolean isCanceled() {
								return sub.isCanceled();
							}
						});
				if (container != null) {
					try {
						container.refreshLocal(org.eclipse.core.resources.IResource.DEPTH_INFINITE, null);
					} catch (org.eclipse.core.runtime.CoreException e) {
						HlomEditorPlugin.INSTANCE.log(e);
					}
				}
				display.asyncExec(() -> afterImport(window, folder, toWrite, result, showLibrary, openFirst));
				return result.canceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
			} catch (IOException e) {
				return new Status(IStatus.ERROR, HlomEditorPlugin.getPlugin().getBundle().getSymbolicName(),
						NLS.bind(Messages.CsvImportWizard_TheRecordsCouldNotBe2, folder, e.getMessage()), e);
			}
		});
		job.setUser(true);
		if (container != null) {
			job.setRule(container);
		}
		job.schedule();
		return true;
	}

	private void saveSettings(Path folder, String pattern, boolean overwrite, boolean showLibrary,
			boolean openFirst) {
		if (file != null) {
			settings.put("file", file.toString());
		}
		settings.put("template", template.id());
		settings.put("language", language);
		settings.put("folder", folder.toString());
		settings.put("pattern", pattern);
		settings.put("overwrite", overwrite);
		settings.put("showLibrary", showLibrary);
		settings.put("openFirst", openFirst);
	}

	/** Shows the result: notification, library and first record; reports files that could not be written. */
	private static void afterImport(IWorkbenchWindow window, Path folder, List<ImportedRecord> written,
			CsvImporter.WriteResult result, boolean showLibrary, boolean openFirst) {
		for (Path path : result.written()) {
			LibraryView.fileSaved(path);
		}
		if (window == null || window.getShell() == null || window.getShell().isDisposed()) {
			return;
		}
		if (showLibrary && !result.written().isEmpty()) {
			showLibrary(window, folder);
		}
		if (openFirst && !result.written().isEmpty()) {
			Path first = result.written().getFirst();
			RecentDocuments.add(first.toString());
			HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(first.toString()));
		}
		long withErrors = written.stream().filter(ImportedRecord::isIncluded)
				.filter(r -> r.severity() == Severity.ERROR).count();
		String detail = folder.toString() + (withErrors > 0 ? "\n" + withErrors + " with errors to review." : "");
		Notifier.notify(HlomTheme.image("icons/app/hlom32.png"),
				NLS.bind(Messages.CsvImportWizard_Imported, result.written().size(), (result.written().size() == 1 ? " record" : " records")), detail,
				NotifierTheme.GRAY_THEME);
		if (!result.failures().isEmpty()) {
			MessageDialog.openWarning(window.getShell(), Messages.CsvImportWizard_ImportFromCSV, NLS.bind(Messages.CsvImportWizard_SomeRecordsCouldNotBe, String.join("\n", result.failures())));
		}
	}

	/**
	 * Shows the library on a folder. A library already showing a parent folder only refreshes the new files;
	 * otherwise the library switches to the folder, which becomes the remembered library folder.
	 */
	static void showLibrary(IWorkbenchWindow window, Path folder) {
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		try {
			IViewPart view = page.showView(LibraryView.ID);
			if (view instanceof LibraryView library) {
				Path normalized = folder.toAbsolutePath().normalize();
				if (library.getFolder() == null || !normalized.startsWith(library.getFolder())) {
					library.setFolder(normalized);
					rememberLibraryFolder(normalized);
				}
			}
		} catch (PartInitException e) {
			HlomEditorPlugin.INSTANCE.log(e);
		}
	}

	private static void rememberLibraryFolder(Path folder) {
		IPreferenceStore store = HlomEditorPlugin.getPlugin().getPreferenceStore();
		store.setValue(LIBRARY_FOLDER_PREFERENCE, folder.toString());
		if (store instanceof IPersistentPreferenceStore persistent) {
			try {
				persistent.save();
			} catch (IOException e) {
				HlomEditorPlugin.INSTANCE.log(e);
			}
		}
	}

	/** The default output folder: the last one, else the library folder, else Documents/HLOM Import. */
	Path defaultFolder() {
		String last = settings.get("folder");
		if (last != null && !last.isBlank()) {
			return Path.of(last);
		}
		String library = HlomEditorPlugin.getPlugin().getPreferenceStore().getString(LIBRARY_FOLDER_PREFERENCE);
		if (library != null && !library.isBlank() && Files.isDirectory(Path.of(library))) {
			return Path.of(library);
		}
		File home = new File(System.getProperty("user.home"));
		File documents = new File(home, Messages.CsvImportWizard_Documents);
		return (documents.isDirectory() ? documents : home).toPath().resolve(Messages.CsvImportWizard_HLOMImport);
	}

	/**
	 * Opens the import wizard in a dialog.
	 *
	 * @param csvFile a CSV file to preselect, or {@code null} for the last one used
	 */
	public static void open(IWorkbenchWindow window, Path csvFile) {
		WizardDialog dialog = createDialog(window, csvFile);
		dialog.open();
	}

	/** Creates (but doesn't open) the wizard dialog. */
	public static WizardDialog createDialog(IWorkbenchWindow window, Path csvFile) {
		CsvImportWizard wizard = new CsvImportWizard(csvFile);
		wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.setPageSize(820, 560);
		return dialog;
	}
}
