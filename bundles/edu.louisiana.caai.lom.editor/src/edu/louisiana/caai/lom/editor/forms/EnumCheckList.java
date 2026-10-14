/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;

/**
 * Check boxes for a multi-valued enumeration attribute, e.g. {@code participationModality}.
 */
public final class EnumCheckList {

	private final FormSupport form;
	private final ModelPath ownerPath;
	private final EAttribute attribute;
	private final Composite composite;
	private final List<Button> buttons = new ArrayList<>();
	private final Runnable refresher = this::refresh;

	EnumCheckList(FormSupport form, Composite parent, ModelPath ownerPath, EAttribute attribute, int columns) {
		this.form = form;
		this.ownerPath = ownerPath;
		this.attribute = attribute;
		composite = form.toolkit().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(columns).spacing(16, 4).applyTo(composite);
		for (EEnumLiteral literal : FormSupport.enumOf(attribute.getEAttributeType()).getELiterals()) {
			Button button = form.toolkit().createButton(composite, literal.getLiteral(), SWT.CHECK);
			button.setData(literal.getInstance());
			button.addListener(SWT.Selection, _ -> toggle(button.getData(), button.getSelection()));
			buttons.add(button);
		}
		FormWidgets.wrapColumns(composite, columns);
		form.context().broadcaster().addListener(refresher);
		IValueChangeListener<EObject> anchorListener = _ -> refresh();
		form.anchor().addValueChangeListener(anchorListener);
		composite.addDisposeListener(_ -> {
			form.context().broadcaster().removeListener(refresher);
			form.anchor().removeValueChangeListener(anchorListener);
		});
		form.bindEnabled(composite);
		refresh();
	}

	public Control control() {
		return composite;
	}

	private void toggle(Object literal, boolean selected) {
		EObject anchor = form.anchor().getValue();
		if (anchor == null) {
			return;
		}
		var domain = form.context().domain();
		EObject owner = ownerPath.resolve(anchor);
		Command command = null;
		if (selected) {
			ModelPath.Ensured ensured = ownerPath.ensure(domain, anchor);
			if (ensured.isNew()) {
				@SuppressWarnings("unchecked")
				List<Object> values = (List<Object>) ensured.target().eGet(attribute);
				values.add(literal);
				command = ensured.command();
			} else if (!((List<?>) ensured.target().eGet(attribute)).contains(literal)) {
				command = new AddCommand(domain, ensured.target(), attribute, literal);
			}
		} else if (owner != null && ((List<?>) owner.eGet(attribute)).contains(literal)) {
			command = new RemoveCommand(domain, owner, attribute, literal);
		}
		ModelCommands.execute(domain, command);
	}

	private void refresh() {
		if (composite.isDisposed()) {
			return;
		}
		EObject anchor = form.anchor().getValue();
		EObject owner = anchor == null ? null : ownerPath.resolve(anchor);
		List<?> values = owner == null ? List.of() : (List<?>) owner.eGet(attribute);
		for (Button button : buttons) {
			button.setSelection(values.contains(button.getData()));
		}
	}
}
