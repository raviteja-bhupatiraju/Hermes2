/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

import edu.louisiana.caai.lom.editor.HlomTheme;

/** The "Validation" preference page: live validation and the problems shown. */
public class ValidationPreferencePage extends AbstractHlomPreferencePage {

	/** The page id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.preferences.validation";

	private Button onChange;
	private Spinner delay;

	/** Creates the page. */
	public ValidationPreferencePage() {
		setDescription(Messages.ValidationPreferencePage_HowDocumentsAreCheckedAgainst);
	}

	@Override
	protected void createSections(Composite page) {
		Composite live = section(page, Messages.ValidationPreferencePage_LiveValidation,
				Messages.ValidationPreferencePage_ValidateTheActiveDocumentIn);
		onChange = check(live, Messages.ValidationPreferencePage_ValidateAfterEachChange, HlomPreferences.VALIDATE_ON_CHANGE);
		delay = spinner(live, Messages.ValidationPreferencePage_DelayAfterTheLastChange, HlomPreferences.VALIDATION_DELAY, 100, 10000, 100,
				"milliseconds");
		onChange.addListener(SWT.Selection, _ -> loaded());

		Composite problems = section(page, Messages.ValidationPreferencePage_Problems,
				Messages.ValidationPreferencePage_TheValidationViewListsSchema);
		check(problems, Messages.ValidationPreferencePage_ShowInformationalHintsBestPractices, HlomPreferences.SHOW_INFO_PROBLEMS);
		Composite legend = new Composite(problems, SWT.NONE);
		GridDataFactory.fillDefaults().span(3, 1).indent(0, 6).applyTo(legend);
		GridLayoutFactory.fillDefaults().spacing(0, 6).applyTo(legend);
		imageLabel(legend, HlomTheme.image("icons/actions/error.png"), Messages.ValidationPreferencePage_Error,
				Messages.ValidationPreferencePage_TheDocumentDoesNotConform);
		imageLabel(legend, HlomTheme.image("icons/actions/warning.png"), Messages.ValidationPreferencePage_Warning,
				Messages.ValidationPreferencePage_ProbablyAMistakeEG);
		imageLabel(legend, HlomTheme.image("icons/actions/info.png"), Messages.ValidationPreferencePage_Info,
				Messages.ValidationPreferencePage_ABestPracticeEG);
	}

	@Override
	protected void loaded() {
		delay.setEnabled(onChange.getSelection());
	}
}
