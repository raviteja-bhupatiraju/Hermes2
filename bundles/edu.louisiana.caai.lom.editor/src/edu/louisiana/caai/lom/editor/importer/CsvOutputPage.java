/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import org.eclipse.osgi.util.NLS;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.importer.CsvImporter;
import edu.louisiana.caai.lom.model.support.importer.ImportedRecord;

/**
 * Page 4: the output folder, the file name pattern (with a live preview of the names) and what to show
 * afterwards.
 */
class CsvOutputPage extends WizardPage {

	/** A planned file. */
	private record Planned(ImportedRecord record, String name, boolean exists) {
	}

	private static final String[] PATTERNS = { "{title}", "{n}-{title}", "{id}", "{kind}-{title}",
			"{date}-{title}", "{template}-{row}" };

	private final CsvImportWizard wizard;
	private Text folderText;
	private Combo patternCombo;
	private TableViewer names;
	private Button overwriteButton;
	private Button libraryButton;
	private Button openButton;
	private Label summary;
	private boolean folderInitialized;
	private final TableColumnLayout columnLayout = new TableColumnLayout();

	CsvOutputPage(CsvImportWizard wizard) {
		super("csvOutput");
		this.wizard = wizard;
		setTitle(Messages.CsvOutputPage_SaveTheRecords);
		setDescription(Messages.CsvOutputPage_ChooseTheFolderAndHow);
	}

	@Override
	public void createControl(Composite parent) {
		IDialogSettings settings = wizard.settings();
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).margins(10, 10).spacing(10, 8).applyTo(composite);

		new Label(composite, SWT.NONE).setText(Messages.CsvOutputPage_Folder);
		folderText = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).hint(420, SWT.DEFAULT).applyTo(folderText);
		Button browse = new Button(composite, SWT.PUSH);
		browse.setText(Messages.CsvOutputPage_Browse);
		browse.addListener(SWT.Selection, _ -> browse());

		new Label(composite, SWT.NONE).setText(Messages.CsvOutputPage_FileNames);
		patternCombo = new Combo(composite, SWT.DROP_DOWN);
		patternCombo.setItems(PATTERNS);
		patternCombo.setText(settings.get("pattern") != null ? settings.get("pattern") : CsvImporter.DEFAULT_PATTERN);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(patternCombo);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		Label tokens = new Label(composite, SWT.WRAP);
		tokens.setText("Placeholders: {title}  {row}  {n} (001, 002, ...)  {id}  {kind}  {template}  {date}  "
				+ "{column:Header}. The extension .xml is added; duplicate names get -2, -3, ...");
		tokens.setForeground(HlomTheme.color(HlomTheme.MUTED));
		GridDataFactory.fillDefaults().grab(true, false).hint(420, SWT.DEFAULT).span(2, 1).applyTo(tokens);

		Composite tableArea = new Composite(composite, SWT.NONE);
		tableArea.setLayout(columnLayout);
		GridDataFactory.fillDefaults().grab(true, true).span(3, 1).hint(SWT.DEFAULT, 190).applyTo(tableArea);
		names = new TableViewer(tableArea, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		names.getTable().setHeaderVisible(true);
		names.getTable().setLinesVisible(true);
		names.setContentProvider(ArrayContentProvider.getInstance());
		column(Messages.CsvOutputPage_File, 360, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Planned) element).name();
			}

			@Override
			public Image getImage(Object element) {
				return CsvPreviewPage.statusImage(((Planned) element).record().severity());
			}
		});
		column(Messages.CsvOutputPage_Record, 330, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Planned planned = (Planned) element;
				return NLS.bind(Messages.CsvOutputPage_Row, planned.record().row(), planned.record().title());
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(HlomTheme.SLATE);
			}
		});
		column("", 110, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Planned) element).exists() ? "replaces a file" : "new";
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(((Planned) element).exists() ? HlomTheme.AMBER : HlomTheme.TEAL);
			}
		});

		Composite options = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(8, 6).applyTo(options);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).applyTo(options);
		overwriteButton = check(options, Messages.CsvOutputPage_ReplaceExistingFilesWithThe,
				settings.getBoolean("overwrite"));
		libraryButton = check(options, Messages.CsvOutputPage_ShowTheFolderInThe,
				settings.get("showLibrary") == null || settings.getBoolean("showLibrary"));
		openButton = check(options, Messages.CsvOutputPage_OpenTheFirstRecordIn,
				settings.get("openFirst") == null || settings.getBoolean("openFirst"));

		summary = new Label(composite, SWT.WRAP);
		summary.setFont(HlomTheme.boldFont());
		summary.setForeground(HlomTheme.color(HlomTheme.INK));
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).applyTo(summary);

		folderText.addModifyListener(_ -> update());
		patternCombo.addModifyListener(_ -> update());
		overwriteButton.addListener(SWT.Selection, _ -> update());
		setControl(composite);
	}

	/** The remembered folder, else a folder named after the CSV file in the library folder. */
	private Path initialFolder() {
		Path folder = wizard.defaultFolder();
		if (wizard.settings().get("folder") == null && wizard.file() != null) {
			String name = wizard.file().getFileName().toString().replaceFirst("\\.[^.]+$", "");
			folder = folder.resolve(CsvImporter.slug(name).isEmpty() ? "imported" : CsvImporter.slug(name));
		}
		return folder;
	}

	private void column(String title, int width, ColumnLabelProvider labels) {
		TableViewerColumn column = new TableViewerColumn(names, SWT.NONE);
		column.getColumn().setText(title);
		columnLayout.setColumnData(column.getColumn(), new ColumnWeightData(width, 60));
		column.setLabelProvider(labels);
	}

	private static Button check(Composite parent, String text, boolean selected) {
		Button button = new Button(parent, SWT.CHECK);
		button.setText(text);
		button.setSelection(selected);
		return button;
	}

	private void browse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
		dialog.setText(Messages.CsvOutputPage_ImportFromCSV);
		dialog.setMessage(Messages.CsvOutputPage_ChooseTheFolderForThe);
		Path folder = folder();
		if (folder != null) {
			Path existing = folder;
			while (existing != null && !Files.isDirectory(existing)) {
				existing = existing.getParent();
			}
			if (existing != null) {
				dialog.setFilterPath(existing.toString());
			}
		}
		String selected = dialog.open();
		if (selected != null) {
			folderText.setText(selected);
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			if (!folderInitialized) {
				folderInitialized = true;
				folderText.setText(initialFolder().toString());
			}
			update();
		}
	}

	private void update() {
		Path folder = folder();
		String error = null;
		if (folderText.getText().isBlank()) {
			error = Messages.CsvOutputPage_ChooseTheFolderForThe2;
		} else if (folder == null) {
			error = Messages.CsvOutputPage_TheFolderNameIsNot;
		} else if (Files.exists(folder) && !Files.isDirectory(folder)) {
			error = Messages.CsvOutputPage_TheLocationIsAFile;
		}
		List<Planned> planned = new ArrayList<>();
		CsvImporter importer = wizard.importer();
		if (error == null && importer != null) {
			List<ImportedRecord> included = wizard.records().stream().filter(ImportedRecord::isIncluded).toList();
			List<String> fileNames = importer.planFileNames(included, folder, pattern(), overwrite());
			for (int i = 0; i < included.size(); i++) {
				planned.add(new Planned(included.get(i), fileNames.get(i), Files.exists(folder.resolve(fileNames.get(i)))));
			}
		}
		names.setInput(planned);
		long replaced = planned.stream().filter(Planned::exists).count();
		if (error != null) {
			summary.setText("");
			setErrorMessage(error);
			setMessage(null);
		} else {
			setErrorMessage(null);
			summary.setText(planned.size() + (planned.size() == 1 ? " record" : " records") + " will be written to "
					+ folder + (Files.isDirectory(folder) ? "" : " (new folder)") + ".");
			if (replaced > 0) {
				setMessage(replaced + (replaced == 1 ? " existing file is" : " existing files are") + " replaced.",
						IMessageProvider.WARNING);
			} else {
				setMessage(null);
			}
		}
		summary.getParent().layout(true, true);
		setPageComplete(error == null && !planned.isEmpty());
		if (getContainer() != null && getContainer().getCurrentPage() == this) {
			getContainer().updateButtons();
		}
	}

	/** The chosen folder, or {@code null} if the text is not a path. */
	Path folder() {
		try {
			String text = folderText.getText().strip();
			return text.isEmpty() ? null : Path.of(text).toAbsolutePath().normalize();
		} catch (InvalidPathException _) {
			return null;
		}
	}

	String pattern() {
		String pattern = patternCombo.getText().strip();
		return pattern.isEmpty() ? CsvImporter.DEFAULT_PATTERN : pattern;
	}

	boolean overwrite() {
		return overwriteButton.getSelection();
	}

	boolean showLibrary() {
		return libraryButton.getSelection();
	}

	boolean openFirst() {
		return openButton.getSelection();
	}

	/** Sets the folder (used by tests and automation). */
	void setFolder(Path folder) {
		folderText.setText(folder.toString());
	}
}
