/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * The text of a {@link Text} or editable {@link Combo}.
 * <p>
 * Unlike the standard widget property, it doesn't write the text back when it is unchanged: the model
 * echoes every committed edit, and re-setting identical text would move the caret while the user types.
 */
final class TextProperty extends WidgetValueProperty<Control, String> {

	TextProperty() {
		super(SWT.Modify);
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected String doGetValue(Control source) {
		if (source instanceof Text text) {
			return text.getText();
		}
		return ((Combo) source).getText();
	}

	@Override
	protected void doSetValue(Control source, String value) {
		String text = value == null ? "" : value;
		if (text.equals(doGetValue(source))) {
			return;
		}
		if (source instanceof Text widget) {
			widget.setText(text);
		} else {
			((Combo) source).setText(text);
		}
	}
}
