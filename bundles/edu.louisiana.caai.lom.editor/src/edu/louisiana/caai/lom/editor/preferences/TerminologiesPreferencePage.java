/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import org.eclipse.osgi.util.NLS;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Lists the terminologies available offline with their versions, sources and license notices (the notices
 * their licenses require to be shown to users).
 */
public class TerminologiesPreferencePage extends AbstractHlomPreferencePage {

	/** The page id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.preferences.terminologies";

	/** Creates the page. */
	public TerminologiesPreferencePage() {
		setDescription(Messages.TerminologiesPreferencePage_TheseTerminologiesAreIncludedIn);
	}

	private Text keyText;
	private Label keyStatus;

	@Override
	protected void createSections(Composite page) {
		Composite umls = section(page, Messages.TerminologiesPreferencePage_SNOMEDCTAndLOINCUMLS, Messages.TerminologiesPreferencePage_WithTheAPIKeyOf);
		label(umls, Messages.TerminologiesPreferencePage_UMLSAPIKey);
		keyText = new Text(umls, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
		keyText.setMessage(UmlsKey.isSet() ? "(stored; type to replace, clear to remove)" : "from your UTS profile");
		GridDataFactory.fillDefaults().grab(true, false).hint(260, SWT.DEFAULT).applyTo(keyText);
		Button test = new Button(umls, SWT.PUSH);
		test.setText(Messages.TerminologiesPreferencePage_Test);
		test.addListener(SWT.Selection, _ -> testKey());
		new Label(umls, SWT.NONE);
		Link profile = new Link(umls, SWT.WRAP);
		profile.setText(Messages.TerminologiesPreferencePage_GetOrViewYourKey);
		profile.addListener(SWT.Selection, event -> org.eclipse.swt.program.Program.launch(event.text));
		GridDataFactory.fillDefaults().span(2, 1).applyTo(profile);
		new Label(umls, SWT.NONE);
		keyStatus = new Label(umls, SWT.WRAP);
		keyStatus.setText(UmlsKey.isSet() ? "A key is stored." : "No key is stored.");
		keyStatus.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(380, SWT.DEFAULT).applyTo(keyStatus);
		keyText.addModifyListener(_ -> keyEdited = true);

		for (Terminology terminology : Terminologies.all()) {
			Composite section = section(page, terminology.getTitle() + " (" + terminology.getId() + ")", null);
			row(section, Messages.TerminologiesPreferencePage_Version, terminology.getVersion());
			row(section, Messages.TerminologiesPreferencePage_Source, terminology.getSource());
			Label license = hint(section, terminology.getLicense(), 3);
			license.setForeground(HlomTheme.color(HlomTheme.SLATE));
		}
	}

	private static void row(Composite section, String label, String value) {
		label(section, label);
		Label text = new Label(section, SWT.WRAP);
		text.setText(value);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(380, SWT.DEFAULT).applyTo(text);
	}

	private boolean keyEdited;

	/** Checks the typed (or stored) key with a small search. */
	private void testKey() {
		String typed = keyText.getText().strip();
		String key = typed.isEmpty() ? UmlsKey.get() : typed;
		if (key == null || key.isBlank()) {
			keyStatus.setText(Messages.TerminologiesPreferencePage_TypeTheAPIKeyOf);
			return;
		}
		keyStatus.setText(Messages.TerminologiesPreferencePage_CheckingTheKey);
		keyStatus.getParent().layout(true);
		try {
			new edu.louisiana.caai.lom.model.support.mesh.UmlsClient(new edu.louisiana.caai.lom.model.support.mesh.
					HttpMeshTransport(java.time.Duration.ofSeconds(10), Messages.TerminologiesPreferencePage_Hermes2), () -> key,
					edu.louisiana.caai.lom.model.support.mesh.UmlsClient.BASE_URL).check();
			keyStatus.setText(Messages.TerminologiesPreferencePage_TheKeyWorksSNOMEDCT);
		} catch (java.io.IOException e) {
			keyStatus.setText(e.getMessage());
		}
	}

	@Override
	public boolean performOk() {
		if (keyEdited) {
			try {
				UmlsKey.set(keyText.getText());
				keyEdited = false;
			} catch (org.eclipse.equinox.security.storage.StorageException | java.io.IOException e) {
				setErrorMessage(NLS.bind(Messages.TerminologiesPreferencePage_TheKeyCouldNotBe, e.getMessage()));
				return false;
			}
		}
		return super.performOk();
	}

	@Override
	protected void loaded() {
		// The key is not shown: it stays in the secure storage.
	}
}
