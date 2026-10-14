/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.opal.titledseparator.TitledSeparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * Check boxes for a multi-valued LOM vocabulary element: one check box per term, each checked term being one
 * vocabulary element with its source and value. IEEE LOM terms and MedBiquitous healthcare terms are shown in
 * two titled groups.
 */
public final class VocabularyCheckList {

	private final FormSupport form;
	private final ModelPath ownerPath;
	private final EReference vocabReference;
	private final Composite composite;
	private final List<Button> buttons = new ArrayList<>();
	private final Runnable refresher = this::refresh;

	VocabularyCheckList(FormSupport form, Composite parent, ModelPath ownerPath, EReference vocabReference,
			int columns) {
		this.form = form;
		this.ownerPath = ownerPath;
		this.vocabReference = vocabReference;
		composite = form.toolkit().createComposite(parent);
		GridLayoutFactory.fillDefaults().spacing(0, 4).applyTo(composite);

		List<Term> lomTerms = new ArrayList<>();
		List<Term> healthcareTerms = new ArrayList<>();
		for (Term term : Vocabularies.terms(vocabReference.getEReferenceType())) {
			(isHealthcare(term) ? healthcareTerms : lomTerms).add(term);
		}
		boolean grouped = !lomTerms.isEmpty() && !healthcareTerms.isEmpty();
		if (grouped) {
			separator(Messages.VocabularyCheckList_IEEELOM, HlomTheme.SLATE);
		}
		group(lomTerms, columns);
		if (!healthcareTerms.isEmpty()) {
			separator(Messages.VocabularyCheckList_HealthcareMedBiquitous, HlomTheme.TEAL);
			group(healthcareTerms, columns);
		}

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

	/** Whether a term belongs to the MedBiquitous healthcare vocabularies. */
	static boolean isHealthcare(Term term) {
		return Vocabularies.HEALTHCARE_SOURCE.equals(term.source());
	}

	private void separator(String title, RGB color) {
		TitledSeparator separator = new TitledSeparator(composite, SWT.NONE);
		// The separator creates its label from its colors and font when the text is set.
		separator.setBackground(composite.getBackground());
		separator.setForeground(HlomTheme.color(color));
		separator.setFont(FormWidgets.smallBoldFont());
		separator.setAlignment(SWT.LEFT);
		separator.setText(title);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, buttons.isEmpty() ? 0 : 4).applyTo(separator);
	}

	private void group(List<Term> terms, int columns) {
		if (terms.isEmpty()) {
			return;
		}
		Composite group = form.toolkit().createComposite(composite);
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).spacing(16, 4).applyTo(group);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		for (Term term : terms) {
			Button button = form.toolkit().createButton(group, term.value(), SWT.CHECK);
			button.setData(term);
			button.addListener(SWT.Selection, _ -> toggle(term, button.getSelection()));
			buttons.add(button);
		}
		FormWidgets.wrapColumns(group, columns);
	}

	public Control control() {
		return composite;
	}

	private void toggle(Term term, boolean selected) {
		EObject anchor = form.anchor().getValue();
		if (anchor != null) {
			ModelCommands.execute(form.context().domain(), ModelCommands.toggleVocabulary(form.context().domain(),
					anchor, ownerPath, vocabReference, term, selected));
		}
	}

	private void refresh() {
		if (composite.isDisposed()) {
			return;
		}
		EObject anchor = form.anchor().getValue();
		List<String> values = anchor == null ? List.of()
				: ModelCommands.vocabularyValues(ownerPath.resolve(anchor), vocabReference);
		for (Button button : buttons) {
			button.setSelection(values.contains(((Term) button.getData()).value()));
		}
	}
}
