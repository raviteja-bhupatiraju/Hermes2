/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import java.util.regex.Pattern;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.pages.VCardDialog;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/** The "MedBiquitous Editor" preference page: new documents, author identity and library. */
public class GeneralPreferencePage extends AbstractHlomPreferencePage {

	/** The page id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.preferences.general";

	private static final Pattern LANGUAGE = Pattern.compile("[A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*");
	private static final Pattern EMAIL = Pattern.compile("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");

	private Text name;
	private Text organization;
	private Text email;
	private Button addAuthor;
	private Text preview;

	/** Creates the page. */
	public GeneralPreferencePage() {
		setDescription(Messages.GeneralPreferencePage_DefaultsForNewDocumentsAnd);
	}

	@Override
	protected void createSections(Composite page) {
		Composite documents = section(page, Messages.GeneralPreferencePage_NewDocuments, null);
		combo(documents, Messages.GeneralPreferencePage_DefaultLanguage, HlomPreferences.DEFAULT_LANGUAGE, HlomSuggestions.LANGUAGES,
				value -> value.isEmpty() || LANGUAGE.matcher(value).matches() ? null
						: "The default language must be a language tag such as en-US or fr.");
		hint(documents, Messages.GeneralPreferencePage_LanguageTagISO639Language,
				1);
		addAuthor = check(documents, Messages.GeneralPreferencePage_AddMeAsAuthorAnd,
				HlomPreferences.ADD_AUTHOR_TO_NEW_DOCUMENTS);
		addAuthor.addListener(SWT.Selection, _ -> updatePreview());
		check(documents, Messages.GeneralPreferencePage_CreateNewDocumentsInThe,
				HlomPreferences.NEW_DOCUMENTS_JSON);

		Composite author = section(page, Messages.GeneralPreferencePage_Author,
				Messages.GeneralPreferencePage_YourIdentityAsContributorIt);
		name = text(author, Messages.GeneralPreferencePage_FullName, HlomPreferences.AUTHOR_NAME, Messages.GeneralPreferencePage_EGJaneDoeMD, null);
		organization = text(author, Messages.GeneralPreferencePage_Organization, HlomPreferences.AUTHOR_ORGANIZATION,
				Messages.GeneralPreferencePage_EGUniversityOfLouisiana, null);
		email = text(author, Messages.GeneralPreferencePage_Email, HlomPreferences.AUTHOR_EMAIL, "name@example.org",
				value -> value.isEmpty() || EMAIL.matcher(value).matches() ? null
						: "The email address is not valid.");
		for (Text text : new Text[] { name, organization, email }) {
			text.addModifyListener(_ -> updatePreview());
		}
		Label previewLabel = label(author, "vCard:");
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).indent(0, 4).applyTo(previewLabel);
		preview = new Text(author, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER);
		preview.setFont(JFaceResources.getTextFont());
		preview.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		preview.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(260, SWT.DEFAULT).applyTo(preview);

		Composite library = section(page, Messages.GeneralPreferencePage_Library, Messages.GeneralPreferencePage_FolderInWhichTheLibrary);
		directory(library, Messages.GeneralPreferencePage_Folder, HlomPreferences.LIBRARY_FOLDER, Messages.GeneralPreferencePage_SelectTheFolderOfThe);

		Composite spacer = new Composite(page, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(spacer);
	}

	@Override
	protected void loaded() {
		updatePreview();
	}

	private void updatePreview() {
		if (preview == null || name == null) {
			return;
		}
		String fullName = name.getText().strip();
		String org = organization.getText().strip();
		if (fullName.isEmpty() && org.isEmpty()) {
			preview.setText(Messages.GeneralPreferencePage_EnterYourNameToBe);
		} else {
			preview.setText(VCardDialog.compose(fullName.isEmpty() ? org : fullName, org, email.getText().strip()));
		}
		preview.setForeground(HlomTheme.color(addAuthor.getSelection() ? HlomTheme.SLATE : HlomTheme.MUTED));
		preview.getParent().layout(true);
	}
}
