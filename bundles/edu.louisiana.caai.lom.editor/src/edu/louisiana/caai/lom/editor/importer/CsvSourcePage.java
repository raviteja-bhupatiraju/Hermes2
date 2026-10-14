/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import org.eclipse.osgi.util.NLS;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.start.Card;
import edu.louisiana.caai.lom.editor.start.StartView;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.lom.model.support.importer.CsvReader;
import edu.louisiana.caai.lom.model.support.importer.CsvTable;

/**
 * Page 1: the CSV file (delimiter and encoding detected, editable) and the template every record starts from,
 * shown as the card gallery of the new-document wizard.
 */
class CsvSourcePage extends WizardPage {

	private static final Character[] DELIMITERS = { null, ',', ';', '\t', '|' };
	private static final Charset[] CHARSETS = { null, StandardCharsets.UTF_8, CsvReader.WINDOWS_1252,
			StandardCharsets.ISO_8859_1, StandardCharsets.UTF_16 };

	private final CsvImportWizard wizard;
	private final List<Card> cards = new ArrayList<>();
	private Text fileText;
	private Combo delimiterCombo;
	private Combo encodingCombo;
	private CLabel summary;
	private Combo languageCombo;
	private Button placeholdersButton;
	private String loadError;

	CsvSourcePage(CsvImportWizard wizard) {
		super("csvSource");
		this.wizard = wizard;
		setTitle(Messages.CsvSourcePage_ImportFromCSV);
		setDescription(Messages.CsvSourcePage_ChooseASpreadsheetExportedAs);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 10).spacing(8, 8).applyTo(composite);

		heading(composite, Messages.CsvSourcePage_CSVFile);
		Composite fileRow = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(6).spacing(8, 8).applyTo(fileRow);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(fileRow);
		new Label(fileRow, SWT.NONE).setText(Messages.CsvSourcePage_File);
		fileText = new Text(fileRow, SWT.BORDER);
		fileText.setMessage(Messages.CsvSourcePage_ACsvFileWithOne);
		GridDataFactory.fillDefaults().grab(true, false).span(4, 1).hint(420, SWT.DEFAULT).applyTo(fileText);
		Button browse = new Button(fileRow, SWT.PUSH);
		browse.setText(Messages.CsvSourcePage_Browse);
		browse.addListener(SWT.Selection, _ -> browse());

		new Label(fileRow, SWT.NONE).setText(Messages.CsvSourcePage_Delimiter);
		delimiterCombo = new Combo(fileRow, SWT.READ_ONLY);
		GridDataFactory.fillDefaults().hint(170, SWT.DEFAULT).applyTo(delimiterCombo);
		Label encodingLabel = new Label(fileRow, SWT.NONE);
		encodingLabel.setText(Messages.CsvSourcePage_Encoding);
		GridDataFactory.fillDefaults().indent(12, 0).align(SWT.BEGINNING, SWT.CENTER).applyTo(encodingLabel);
		encodingCombo = new Combo(fileRow, SWT.READ_ONLY);
		GridDataFactory.fillDefaults().hint(190, SWT.DEFAULT).applyTo(encodingCombo);
		new Label(fileRow, SWT.NONE);
		new Label(fileRow, SWT.NONE);

		summary = new CLabel(composite, SWT.NONE);
		summary.setBackground(HlomTheme.color(HlomTheme.BAR));
		summary.setForeground(HlomTheme.color(HlomTheme.SLATE));
		summary.setMargins(10, 6, 10, 6);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(summary);

		Label spacer = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().hint(1, 2).applyTo(spacer);
		heading(composite, Messages.CsvSourcePage_StartEachRecordFrom);
		Composite gallery = new Composite(composite, SWT.NONE);
		gallery.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(true).margins(12, 12).spacing(12, 12)
				.applyTo(gallery);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(gallery);
		gallery.addPaintListener(event -> {
			Rectangle area = gallery.getClientArea();
			event.gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			event.gc.drawRectangle(0, 0, area.width - 1, area.height - 1);
		});
		for (Template each : HlomTemplates.all()) {
			Card card = new Card(gallery, Card.HORIZONTAL).icon(StartView.templateIcon(each)).title(each.label())
					.description(each.description()).maxLines(3).accent(HlomTheme.ACCENT);
			card.setData(each);
			card.setSelected(each == wizard.template());
			card.onActivate(() -> selectTemplate(each));
			GridDataFactory.fillDefaults().grab(true, false).hint(230, SWT.DEFAULT).applyTo(card);
			cards.add(card);
		}

		Composite options = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(10, 6).applyTo(options);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(options);
		new Label(options, SWT.NONE).setText(Messages.CsvSourcePage_DefaultLanguage);
		languageCombo = new Combo(options, SWT.DROP_DOWN);
		languageCombo.setItems(HlomSuggestions.LANGUAGES.toArray(new String[0]));
		languageCombo.setText(wizard.language());
		languageCombo.setToolTipText(Messages.CsvSourcePage_TheLanguageOfRowsWithout);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).applyTo(languageCombo);
		Label languageHint = new Label(options, SWT.NONE);
		languageHint.setText(Messages.CsvSourcePage_ForRowsWithoutALanguage);
		languageHint.setForeground(HlomTheme.color(HlomTheme.MUTED));
		placeholdersButton = new Button(options, SWT.CHECK);
		placeholdersButton.setText(Messages.CsvSourcePage_RemoveTemplatePlaceholdersThatNo);
		placeholdersButton.setSelection(wizard.removePlaceholders());
		GridDataFactory.fillDefaults().span(3, 1).applyTo(placeholdersButton);

		fillChoices();
		fileText.addModifyListener(_ -> fileChanged());
		delimiterCombo.addListener(SWT.Selection, _ -> {
			wizard.setDelimiter(DELIMITERS[delimiterCombo.getSelectionIndex()]);
			reparse();
		});
		encodingCombo.addListener(SWT.Selection, _ -> {
			wizard.setCharset(CHARSETS[encodingCombo.getSelectionIndex()]);
			reparse();
		});
		languageCombo.addModifyListener(_ -> wizard.setLanguage(languageCombo.getText()));
		placeholdersButton.addListener(SWT.Selection,
				_ -> wizard.setRemovePlaceholders(placeholdersButton.getSelection()));

		setControl(composite);
		Path initial = wizard.initialFile();
		if (initial != null) {
			fileText.setText(initial.toString());
		} else {
			validate();
		}
		fileText.setFocus();
	}

	private static void heading(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(HlomTheme.boldFont());
		label.setForeground(HlomTheme.color(HlomTheme.INK));
	}

	private void fillChoices() {
		CsvTable table = wizard.table();
		String detectedDelimiter = table != null && wizard.delimiter() == null
				? " (" + CsvTable.delimiterName(table.delimiter()).toLowerCase() + ")"
				: "";
		int delimiterIndex = delimiterCombo.getSelectionIndex();
		delimiterCombo.setItems(NLS.bind(Messages.CsvSourcePage_DetectAutomatically, detectedDelimiter), Messages.CsvSourcePage_Comma, Messages.CsvSourcePage_Semicolon, Messages.CsvSourcePage_Tab,
				Messages.CsvSourcePage_VerticalBar);
		delimiterCombo.select(Math.max(0, delimiterIndex));
		Charset detected = wizard.effectiveCharset();
		String detectedCharset = detected != null && wizard.charset() == null ? " (" + detected.displayName() + ")" : "";
		int encodingIndex = encodingCombo.getSelectionIndex();
		encodingCombo.setItems(NLS.bind(Messages.CsvSourcePage_DetectAutomatically, detectedCharset), "UTF-8", Messages.CsvSourcePage_Windows1252Excel,
				"ISO-8859-1", "UTF-16");
		encodingCombo.select(Math.max(0, encodingIndex));
	}

	private void browse() {
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
		dialog.setText(Messages.CsvSourcePage_ImportFromCSV);
		dialog.setFilterExtensions(new String[] { Messages.CsvSourcePage_CsvTsvTxt, "*.*" });
		dialog.setFilterNames(new String[] { Messages.CsvSourcePage_CSVFilesCsvTsvTxt, Messages.CsvSourcePage_AllFiles });
		String current = fileText.getText().strip();
		if (!current.isEmpty()) {
			Path path = Path.of(current);
			if (path.getParent() != null) {
				dialog.setFilterPath(path.getParent().toString());
			}
		}
		String selected = dialog.open();
		if (selected != null) {
			fileText.setText(selected);
		}
	}

	private void fileChanged() {
		String text = fileText.getText().strip();
		if (text.isEmpty()) {
			loadError = Messages.CsvSourcePage_ChooseACSVFile;
		} else {
			Path path;
			try {
				path = Path.of(text);
			} catch (RuntimeException _) {
				path = null;
			}
			if (path == null || !Files.isRegularFile(path)) {
				loadError = Messages.CsvSourcePage_TheFileDoesNotExist;
			} else {
				loadError = wizard.load(path);
			}
		}
		fillChoices();
		validate();
	}

	private void reparse() {
		if (wizard.file() != null) {
			loadError = wizard.parse();
		}
		fillChoices();
		validate();
	}

	private void selectTemplate(Template template) {
		wizard.setTemplate(template);
		for (Card card : cards) {
			card.setSelected(card.getData() == template);
		}
		validate();
	}

	private void validate() {
		CsvTable table = wizard.table();
		if (loadError == null && table != null) {
			int mapped = wizard.mapping().mappedCount();
			summary.setImage(HlomTheme.image("icons/actions/ok.png"));
			summary.setText(table.rowCount() + (table.rowCount() == 1 ? " record" : " records") + "   ·   "
					+ table.columnCount() + " columns, " + mapped + " recognized   ·   "
					+ CsvTable.delimiterName(table.delimiter()) + "-separated   ·   " + table.charset().displayName());
			setErrorMessage(null);
			setMessage(NLS.bind(Messages.CsvSourcePage_EachOfTheRowsBecomes, table.rowCount(), wizard.template().label()), IMessageProvider.NONE);
			setPageComplete(true);
		} else {
			summary.setImage(HlomTheme.image(loadError == null ? "icons/actions/info.png" : "icons/actions/warning.png"));
			summary.setText(loadError == null ? "No file chosen yet." : loadError);
			setErrorMessage(fileText.getText().isBlank() ? null : loadError);
			setPageComplete(false);
		}
		summary.getParent().layout(true, true);
	}
}
