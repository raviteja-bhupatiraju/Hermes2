/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.wizards;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.start.Card;
import edu.louisiana.caai.lom.editor.start.RecentDocuments;
import edu.louisiana.caai.lom.editor.start.StartView;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * Lets the user pick the standard of the new document and a starting point from a gallery of cards (the Healthcare
 * LOM templates, or the template of another MedBiquitous standard), and enter its title, language and file. The file
 * name follows the title until it is edited.
 */
public class NewHlomDocumentPage extends WizardPage {

	private static final String DEFAULT_NAME = "metadata";

	private final List<Card> cards = new ArrayList<>();

	private Template template;

	/** The standard of the new document when it isn't Healthcare LOM, else {@code null}. */
	private MedbiqDocumentType otherType;

	private Composite gallery;

	private Text fileText;

	private Text titleText;

	private Combo languageCombo;

	private Button xmlButton;

	private Button jsonButton;

	/** The folder new files go to by default, or {@code null} for the folder of the most recent document. */
	private File initialFolder;

	/** Whether the document is created in the JSON form. */
	private boolean json = edu.louisiana.caai.lom.editor.preferences.HlomPreferences.isJsonDefaultFormat();

	private boolean fileEdited;

	private boolean updatingFile;

	/** Creates the page with the blank record selected. */
	public NewHlomDocumentPage() {
		this(HlomTemplates.BLANK);
	}

	/** Creates the page with a template selected. */
	public NewHlomDocumentPage(Template template) {
		super("newHlomDocument");
		this.template = template == null ? HlomTemplates.BLANK : template;
		setTitle(Messages.NewHlomDocumentPage_NewMedBiquitousDocument);
		setDescription(Messages.NewHlomDocumentPage_ChooseAStartingPointThen);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 10).spacing(8, 8).applyTo(composite);

		Composite standardRow = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(10, 0).applyTo(standardRow);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(standardRow);
		Label standardLabel = new Label(standardRow, SWT.NONE);
		standardLabel.setText(Messages.NewHlomDocumentPage_Standard);
		standardLabel.setFont(HlomTheme.boldFont());
		standardLabel.setForeground(HlomTheme.color(HlomTheme.INK));
		Combo standardCombo = new Combo(standardRow, SWT.READ_ONLY);
		List<MedbiqDocumentType> others = MedbiqStandards.TYPES;
		standardCombo.add(NLS.bind(Messages.NewHlomDocumentPage_LearningObjectMetadata, HlomDocumentType.INSTANCE.label()));
		for (MedbiqDocumentType type : others) {
			standardCombo.add(type.label());
		}
		standardCombo.select(otherType == null ? 0 : others.indexOf(otherType) + 1);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(standardCombo);
		standardCombo.addListener(SWT.Selection, _ -> {
			int index = standardCombo.getSelectionIndex();
			selectStandard(index <= 0 ? null : others.get(index - 1));
		});

		Label galleryLabel = new Label(composite, SWT.NONE);
		galleryLabel.setText(Messages.NewHlomDocumentPage_StartFrom);
		galleryLabel.setFont(HlomTheme.boldFont());
		galleryLabel.setForeground(HlomTheme.color(HlomTheme.INK));

		gallery = new Composite(composite, SWT.NONE);
		gallery.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(true).margins(12, 12).spacing(12, 12)
				.applyTo(gallery);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(gallery);
		gallery.addPaintListener(event -> {
			Rectangle area = gallery.getClientArea();
			event.gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			event.gc.drawRectangle(0, 0, area.width - 1, area.height - 1);
		});
		fillGallery();

		Label spacer = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().hint(1, 4).applyTo(spacer);

		Composite fields = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(10, 8).applyTo(fields);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(fields);

		new Label(fields, SWT.NONE).setText(Messages.NewHlomDocumentPage_Title);
		titleText = new Text(fields, SWT.BORDER);
		titleText.setMessage(Messages.NewHlomDocumentPage_EGInsulinPumpTherapy);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(titleText);

		new Label(fields, SWT.NONE).setText(Messages.NewHlomDocumentPage_Language);
		languageCombo = new Combo(fields, SWT.DROP_DOWN);
		languageCombo.setItems(HlomSuggestions.LANGUAGES.toArray(new String[0]));
		languageCombo.setText(edu.louisiana.caai.lom.editor.preferences.HlomPreferences.getDefaultLanguage());
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).hint(160, SWT.DEFAULT).span(2, 1).applyTo(languageCombo);

		new Label(fields, SWT.NONE).setText(Messages.NewHlomDocumentPage_FOrmat);
		Composite formats = new Composite(fields, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(16, 0).applyTo(formats);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(formats);
		xmlButton = new Button(formats, SWT.RADIO);
		xmlButton.setText(Messages.NewHlomDocumentPage_XMLXml);
		xmlButton.setSelection(!json);
		jsonButton = new Button(formats, SWT.RADIO);
		jsonButton.setText(Messages.NewHlomDocumentPage_JSONJson);
		jsonButton.setSelection(json);
		jsonButton.setToolTipText(Messages.NewHlomDocumentPage_TheJSONFormOfThe);
		jsonButton.addListener(SWT.Selection, _ -> setJson(jsonButton.getSelection()));
		updateFormats();

		new Label(fields, SWT.NONE).setText(Messages.NewHlomDocumentPage_File);
		fileText = new Text(fields, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).hint(350, SWT.DEFAULT).applyTo(fileText);
		setFile(new File(defaultFolder(), DEFAULT_NAME + extension()));
		Button browseButton = new Button(fields, SWT.PUSH);
		browseButton.setText(Messages.NewHlomDocumentPage_Browse);
		browseButton.addListener(SWT.Selection, _ -> browse());

		fileText.addModifyListener(_ -> {
			if (!updatingFile) {
				fileEdited = true;
			}
			validate();
		});
		titleText.addModifyListener(_ -> {
			followTitle();
			validate();
		});
		languageCombo.addModifyListener(_ -> validate());

		setControl(composite);
		validate();
		titleText.setFocus();
	}

	/** Sets the folder the document is created in by default (e.g. the folder of a project). */
	public void setInitialFolder(File folder) {
		initialFolder = folder;
	}

	/** Preselects the format: JSON or XML. */
	public void setInitialFormat(boolean asJson) {
		json = asJson;
	}

	/** The extension of the selected format, with the dot. */
	private String extension() {
		return json ? ".json" : ".xml";
	}

	/** The cards of the starting points of the selected standard. */
	private void fillGallery() {
		for (Card card : cards) {
			card.dispose();
		}
		cards.clear();
		if (otherType == null) {
			for (Template each : HlomTemplates.all()) {
				Card card = new Card(gallery, Card.HORIZONTAL).icon(StartView.templateIcon(each)).title(each.label())
						.description(each.description()).maxLines(6).accent(HlomTheme.ACCENT);
				card.setData(each);
				card.setSelected(each == template);
				card.onActivate(() -> select(each));
				GridDataFactory.fillDefaults().grab(true, true).hint(250, SWT.DEFAULT).applyTo(card);
				cards.add(card);
			}
		} else {
			Card card = new Card(gallery, Card.HORIZONTAL).icon(HlomTheme.image("icons/pages/general.png"))
					.title(NLS.bind(Messages.NewHlomDocumentPage_New, otherType.standard())).description("A " + otherType.label()
							+ " document with the content the schemas require, ready to be completed.")
					.maxLines(6).accent(HlomTheme.TEAL);
			card.setData(otherType);
			card.setSelected(true);
			GridDataFactory.fillDefaults().grab(true, true).hint(250, SWT.DEFAULT).span(3, 1).applyTo(card);
			cards.add(card);
		}
		gallery.layout(true, true);
	}

	/** Chooses the standard of the new document ({@code null} for Healthcare LOM). */
	private void selectStandard(MedbiqDocumentType type) {
		otherType = type;
		fillGallery();
		updateFormats();
		if (type != null && json) {
			xmlButton.setSelection(true);
			jsonButton.setSelection(false);
			setJson(false);
		}
		validate();
	}

	/** Only Healthcare LOM documents have a JSON form. */
	private void updateFormats() {
		if (jsonButton != null) {
			jsonButton.setEnabled(otherType == null);
		}
	}

	/** Preselects a standard other than Healthcare LOM. */
	public void setInitialType(MedbiqDocumentType type) {
		otherType = type;
		if (type != null) {
			json = false;
		}
	}

	/** The standard of the new document when it isn't Healthcare LOM, else {@code null}. */
	public MedbiqDocumentType getOtherType() {
		return otherType;
	}

	/** Changes the format and the extension of the file name. */
	private void setJson(boolean asJson) {
		if (json == asJson) {
			return;
		}
		json = asJson;
		String path = fileText.getText().trim();
		String lower = path.toLowerCase(Locale.ROOT);
		for (String old : new String[] { ".xml", ".json", ".lom" }) {
			if (lower.endsWith(old)) {
				path = path.substring(0, path.length() - old.length());
				break;
			}
		}
		boolean edited = fileEdited;
		setFile(new File(path + extension()));
		fileEdited = edited;
		validate();
	}

	private void select(Template selection) {
		template = selection;
		for (Card card : cards) {
			card.setSelected(card.getData() == selection);
		}
		validate();
	}

	/** Derives the file name from the title while the user hasn't edited the file. */
	private void followTitle() {
		if (fileEdited) {
			return;
		}
		String slug = titleText.getText().trim().toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "-")
				.replaceAll("(^-+|-+$)", "");
		if (slug.length() > 60) {
			slug = slug.substring(0, 60);
		}
		File current = new File(fileText.getText().trim());
		File folder = current.getParentFile() != null ? current.getParentFile() : defaultFolder();
		setFile(new File(folder, (slug.isEmpty() ? DEFAULT_NAME : slug) + extension()));
	}

	private void setFile(File file) {
		updatingFile = true;
		try {
			fileText.setText(file.getPath());
		} finally {
			updatingFile = false;
		}
	}

	/** The initial folder, else the folder of the most recent document, else the user's documents folder. */
	private File defaultFolder() {
		if (initialFolder != null && initialFolder.isDirectory()) {
			return initialFolder;
		}
		for (String path : RecentDocuments.get()) {
			File parent = new File(path).getParentFile();
			if (parent != null && parent.isDirectory()) {
				return parent;
			}
		}
		File home = new File(System.getProperty("user.home"));
		File documents = new File(home, Messages.NewHlomDocumentPage_Documents);
		return documents.isDirectory() ? documents : home;
	}

	private void browse() {
		String[] paths = HlomEditorAdvisor.openFilePathDialog(getShell(), SWT.SAVE, new String[] { "*" + extension() });
		if (paths.length > 0) {
			fileText.setText(paths[0]);
		}
	}

	private void validate() {
		if (fileText == null) {
			return;
		}
		String path = fileText.getText().trim();
		String lowerPath = path.toLowerCase(Locale.ROOT);
		String error = null;
		String warning = null;
		if (path.isEmpty()) {
			error = Messages.NewHlomDocumentPage_EnterTheLocationOfThe;
		} else if (new File(path).isDirectory()) {
			error = Messages.NewHlomDocumentPage_TheLocationRefersToA;
		} else if (languageCombo.getText().isBlank()) {
			error = NLS.bind(Messages.NewHlomDocumentPage_EnterTheLanguageOfThe, HlomDocuments.DEFAULT_LANGUAGE);
		} else if (new File(path).exists()) {
			warning = Messages.NewHlomDocumentPage_TheFileAlreadyExistsAnd;
		} else if (json && !lowerPath.endsWith(".json")) {
			warning = Messages.NewHlomDocumentPage_DocumentsInTheJSONForm;
		} else if (!json && !lowerPath.endsWith(".xml") && !lowerPath.endsWith(".lom")) {
			warning = lowerPath.endsWith(".json") ? "Files with the extension .json are saved in the JSON form."
					: "Healthcare LOM documents normally use the extension .xml.";
		} else if (titleText.getText().isBlank()) {
			warning = Messages.NewHlomDocumentPage_TheDocumentHasNoTitle;
		}
		setErrorMessage(error);
		if (warning != null) {
			setMessage(warning, IMessageProvider.WARNING);
		} else {
			setMessage((otherType != null ? otherType.label() : "Template: " + template.label()) + "  ·  "
					+ (json ? "JSON" : "XML") + "  ·  " + path,
					IMessageProvider.NONE);
		}
		setPageComplete(error == null);
	}

	/** The selected template. */
	public Template getTemplate() {
		return template;
	}

	/** The selected file. */
	public File getFile() {
		return new File(fileText.getText().trim());
	}

	/** The document title; may be empty. */
	public String getDocumentTitle() {
		return titleText.getText().trim();
	}

	/** The document language. */
	public String getLanguage() {
		String language = languageCombo.getText().trim();
		return language.isEmpty() ? HlomDocuments.DEFAULT_LANGUAGE : language;
	}
}
