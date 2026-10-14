/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.help.HelpContexts;

/**
 * A dialog composing a minimal vCard 3.0 (full name, organization, email) for contribution entities.
 * <p>
 * After {@code open()} returned {@code OK}, {@link #getVCard()} returns the composed vCard text.
 */
public class VCardDialog extends TitleAreaDialog {

	private Text name;
	private Text organization;
	private Text email;
	private String vcard;

	public VCardDialog(Shell parentShell) {
		super(parentShell);
	}

	/** The composed vCard, or {@code null} if the dialog was cancelled. */
	public String getVCard() {
		return vcard;
	}

	/**
	 * Composes a vCard 3.0 text. Empty or {@code null} organization and email are omitted; the full name is
	 * required by vCard and written even if empty.
	 */
	public static String compose(String name, String org, String email) {
		StringBuilder builder = new StringBuilder(Messages.VCardDialog_BEGINVCARDVERSION30);
		builder.append("FN:").append(escape(name)).append('\n');
		if (org != null && !org.isBlank()) {
			builder.append("ORG:").append(escape(org)).append('\n');
		}
		if (email != null && !email.isBlank()) {
			builder.append(Messages.VCardDialog_EMAILTYPEINTERNET).append(singleLine(email)).append('\n');
		}
		return builder.append("END:VCARD").toString();
	}

	/** The formatted name (the {@code FN} property) of a vCard text, or {@code null} if there is none. */
	public static String formattedName(String vcard) {
		if (vcard == null) {
			return null;
		}
		for (String line : vcard.split("\\r?\\n")) {
			String trimmed = line.strip();
			int colon = trimmed.indexOf(':');
			if (colon > 0) {
				String property = trimmed.substring(0, colon);
				int parameters = property.indexOf(';');
				if (parameters >= 0) {
					property = property.substring(0, parameters);
				}
				if (property.equalsIgnoreCase("FN")) {
					return unescape(trimmed.substring(colon + 1).strip());
				}
			}
		}
		return null;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("vCard");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		HelpContexts.set(parent, HelpContexts.VCARD_DIALOG);
		Composite area = (Composite) super.createDialogArea(parent);
		setTitle(Messages.VCardDialog_ContributorVCard);
		setMessage(Messages.VCardDialog_DescribeTheContributingPersonOr);

		Composite composite = new Composite(area, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).margins(10, 10).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		name = field(composite, Messages.VCardDialog_FullName);
		organization = field(composite, Messages.VCardDialog_Organization);
		email = field(composite, Messages.VCardDialog_Email);
		name.addModifyListener(_ -> validate());
		return area;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		validate();
		return contents;
	}

	private static Text field(Composite parent, String label) {
		Label labelControl = new Label(parent, SWT.NONE);
		labelControl.setText(label + ":");
		Text text = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().grab(true, false).hint(300, SWT.DEFAULT).applyTo(text);
		return text;
	}

	private void validate() {
		boolean valid = name != null && !name.getText().isBlank();
		setErrorMessage(valid ? null : "Enter the full name of the person or organization.");
		if (getButton(IDialogConstants.OK_ID) != null) {
			getButton(IDialogConstants.OK_ID).setEnabled(valid);
		}
	}

	@Override
	protected void okPressed() {
		vcard = compose(name.getText().strip(), organization.getText().strip(), email.getText().strip());
		super.okPressed();
	}

	private static String singleLine(String value) {
		return value == null ? "" : value.replaceAll("[\\r\\n]+", " ").strip();
	}

	/** Escapes a vCard 3.0 text value (RFC 2426, 5. Formal Grammar). */
	private static String escape(String value) {
		return singleLine(value).replace("\\", "\\\\").replace(",", "\\,").replace(";", "\\;");
	}

	private static String unescape(String value) {
		StringBuilder builder = new StringBuilder(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '\\' && i + 1 < value.length()) {
				char next = value.charAt(++i);
				builder.append(next == 'n' || next == 'N' ? ' ' : next);
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}
