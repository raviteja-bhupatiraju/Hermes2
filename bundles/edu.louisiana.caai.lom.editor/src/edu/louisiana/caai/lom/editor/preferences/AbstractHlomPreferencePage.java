/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;

/**
 * Base of the MedBiquitous Editor preference pages: sections with an accent bar, a heading and a description, and
 * fields bound to keys of {@link HlomPreferences#store()} (loaded, validated, restored to defaults and stored by
 * this class).
 */
public abstract class AbstractHlomPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/** A control bound to a preference key. */
	protected interface Field {
		/** Shows the current (or the default) value. */
		void load(IPreferenceStore store, boolean defaults);

		/** Stores the shown value. */
		void store(IPreferenceStore store);

		/** An error message for an invalid value, or {@code null}. */
		default String validate() {
			return null;
		}
	}

	private final List<Field> fields = new ArrayList<>();

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(HlomPreferences.store());
	}

	@Override
	protected final Control createContents(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 6).applyTo(page);
		HelpContexts.set(page, switch (this) {
			case ValidationPreferencePage _ -> HelpContexts.PREFERENCES_VALIDATION;
			case MeshPreferencePage _ -> HelpContexts.PREFERENCES_MESH;
			case TerminologiesPreferencePage _ -> HelpContexts.PREFERENCES_TERMINOLOGIES;
			default -> HelpContexts.PREFERENCES_GENERAL;
		});
		createSections(page);
		for (Field field : fields) {
			field.load(getPreferenceStore(), false);
		}
		loaded();
		validate();
		return page;
	}

	/** Creates the sections of the page with {@link #section(Composite, String, String)}. */
	protected abstract void createSections(Composite page);

	/** Called after all fields have been loaded (e.g. to update enablement). */
	protected void loaded() {
	}

	/**
	 * Adds a section: an accent bar, a bold heading, a separator and an optional description. Returns the body
	 * (a three-column grid: label, control, extra).
	 */
	protected Composite section(Composite page, String title, String description) {
		Composite header = new Composite(page, SWT.NONE);
		boolean first = page.getChildren().length == 1;
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(8, 2).extendedMargins(0, 0, first ? 2 : 14, 0)
				.applyTo(header);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(header);

		Canvas bar = new Canvas(header, SWT.NONE);
		bar.addPaintListener(event -> {
			event.gc.setBackground(HlomTheme.color(HlomTheme.ACCENT));
			event.gc.fillRoundRectangle(0, 1, 4, bar.getSize().y - 2, 3, 3);
		});
		GridDataFactory.fillDefaults().hint(4, 1).applyTo(bar);

		Label heading = new Label(header, SWT.NONE);
		heading.setText(title);
		heading.setFont(HlomTheme.sectionFont());
		heading.setForeground(HlomTheme.color(HlomTheme.INK));

		Canvas line = new Canvas(header, SWT.NONE);
		line.addPaintListener(event -> {
			event.gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			int y = line.getSize().y / 2 + 1;
			event.gc.drawLine(0, y, line.getSize().x, y);
		});
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 1).applyTo(line);

		if (description != null) {
			hint(page, description, 1).setLayoutData(GridDataFactory.fillDefaults().grab(true, false)
					.indent(12, 0).hint(420, SWT.DEFAULT).create());
		}

		Composite body = new Composite(page, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(10, 7).extendedMargins(12, 0, 4, 0).applyTo(body);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(body);
		return body;
	}

	/** A wrapping, secondary-colored text spanning {@code span} columns. */
	protected static Label hint(Composite parent, String text, int span) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(text);
		label.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).span(span, 1).hint(380, SWT.DEFAULT).applyTo(label);
		return label;
	}

	/** A field label in the first column. */
	protected static Label label(Composite body, String text) {
		Label label = new Label(body, SWT.NONE);
		label.setText(text);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(label);
		return label;
	}

	/** A small image with a text, e.g. for legends. */
	protected static Composite imageLabel(Composite parent, Image image, String title, String text) {
		Composite row = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(8, 0).applyTo(row);
		Label icon = new Label(row, SWT.NONE);
		icon.setImage(image);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(icon);
		Label label = new Label(row, SWT.WRAP);
		label.setText(title + " – " + text);
		GridDataFactory.fillDefaults().grab(true, false).hint(360, SWT.DEFAULT).applyTo(label);
		return row;
	}

	/** A single-line text bound to a string preference; {@code validator} returns an error or {@code null}. */
	protected Text text(Composite body, String label, String key, String message, Function<String, String> validator) {
		label(body, label);
		Text text = new Text(body, SWT.BORDER | SWT.SINGLE);
		if (message != null) {
			text.setMessage(message);
		}
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(260, SWT.DEFAULT).applyTo(text);
		text.addModifyListener(_ -> validate());
		fields.add(new Field() {
			@Override
			public void load(IPreferenceStore store, boolean defaults) {
				text.setText(defaults ? store.getDefaultString(key) : store.getString(key));
			}

			@Override
			public void store(IPreferenceStore store) {
				store.setValue(key, text.getText().strip());
			}

			@Override
			public String validate() {
				return validator == null || !text.isEnabled() ? null : validator.apply(text.getText().strip());
			}
		});
		return text;
	}

	/** An editable combo with suggestions, bound to a string preference. */
	protected Combo combo(Composite body, String label, String key, List<String> items,
			Function<String, String> validator) {
		label(body, label);
		Combo combo = new Combo(body, SWT.DROP_DOWN | SWT.BORDER);
		combo.setItems(items.toArray(String[]::new));
		combo.setVisibleItemCount(Math.min(14, items.size()));
		GridDataFactory.swtDefaults().hint(140, SWT.DEFAULT).applyTo(combo);
		combo.addModifyListener(_ -> validate());
		fields.add(new Field() {
			@Override
			public void load(IPreferenceStore store, boolean defaults) {
				combo.setText(defaults ? store.getDefaultString(key) : store.getString(key));
			}

			@Override
			public void store(IPreferenceStore store) {
				store.setValue(key, combo.getText().strip());
			}

			@Override
			public String validate() {
				return validator == null ? null : validator.apply(combo.getText().strip());
			}
		});
		return combo;
	}

	/** A check box spanning the whole row, bound to a boolean preference. */
	protected Button check(Composite body, String label, String key) {
		Button button = new Button(body, SWT.CHECK);
		button.setText(label);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(button);
		fields.add(new Field() {
			@Override
			public void load(IPreferenceStore store, boolean defaults) {
				button.setSelection(defaults ? store.getDefaultBoolean(key) : store.getBoolean(key));
			}

			@Override
			public void store(IPreferenceStore store) {
				store.setValue(key, button.getSelection());
			}
		});
		return button;
	}

	/** A spinner with a unit label, bound to an integer preference. */
	protected Spinner spinner(Composite body, String label, String key, int min, int max, int increment,
			String unit) {
		label(body, label);
		Spinner spinner = new Spinner(body, SWT.BORDER);
		spinner.setValues(min, min, max, 0, increment, increment * 10);
		GridDataFactory.swtDefaults().hint(70, SWT.DEFAULT).applyTo(spinner);
		Label unitLabel = new Label(body, SWT.NONE);
		unitLabel.setText(unit);
		unitLabel.setForeground(HlomTheme.color(HlomTheme.SLATE));
		fields.add(new Field() {
			@Override
			public void load(IPreferenceStore store, boolean defaults) {
				spinner.setSelection(defaults ? store.getDefaultInt(key) : store.getInt(key));
			}

			@Override
			public void store(IPreferenceStore store) {
				store.setValue(key, spinner.getSelection());
			}
		});
		return spinner;
	}

	/** A text with a "Browse..." button, bound to a folder path preference. */
	protected Text directory(Composite body, String label, String key, String dialogMessage) {
		label(body, label);
		Text text = new Text(body, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().grab(true, false).hint(240, SWT.DEFAULT).applyTo(text);
		text.addModifyListener(_ -> validate());
		Button browse = new Button(body, SWT.PUSH);
		browse.setText(Messages.AbstractHlomPreferencePage_Browse);
		browse.addListener(SWT.Selection, _ -> {
			DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
			dialog.setMessage(dialogMessage);
			dialog.setFilterPath(text.getText());
			String folder = dialog.open();
			if (folder != null) {
				text.setText(folder);
			}
		});
		fields.add(new Field() {
			@Override
			public void load(IPreferenceStore store, boolean defaults) {
				text.setText(defaults ? store.getDefaultString(key) : store.getString(key));
			}

			@Override
			public void store(IPreferenceStore store) {
				store.setValue(key, text.getText().strip());
			}

			@Override
			public String validate() {
				return text.getText().isBlank() ? label.replace("&", "").replace(":", "") + " must not be empty." : null;
			}
		});
		return text;
	}

	/** Revalidates all fields and shows the first error. */
	protected void validate() {
		String error = null;
		for (Field field : fields) {
			error = field.validate();
			if (error != null) {
				break;
			}
		}
		setErrorMessage(error);
		setValid(error == null);
	}

	@Override
	protected void performDefaults() {
		for (Field field : fields) {
			field.load(getPreferenceStore(), true);
		}
		loaded();
		validate();
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		for (Field field : fields) {
			field.store(getPreferenceStore());
		}
		return true;
	}
}
