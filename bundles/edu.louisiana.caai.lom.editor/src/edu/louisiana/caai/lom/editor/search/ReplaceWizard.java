/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ide.IDE;

/**
 * The wizard of {@link ReplaceRefactoring}: the replacement text, then the preview of the changed documents.
 */
public final class ReplaceWizard extends RefactoringWizard {

	public ReplaceWizard(ReplaceRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(Messages.ReplaceWizard_ReplaceInDocuments);
	}

	@Override
	protected void addUserInputPages() {
		addPage(new InputPage());
	}

	/**
	 * Replaces matches of a search result: the given elements, or all if none are given. Documents with unsaved
	 * changes are saved first (after asking).
	 */
	public static void open(Shell shell, AbstractTextSearchResult result, List<FieldElement> elements) {
		if (!(result.getQuery() instanceof HermesSearchQuery query)) {
			return;
		}
		List<FieldElement> selected = new ArrayList<>(elements);
		if (selected.isEmpty()) {
			Arrays.stream(result.getElements()).filter(FieldElement.class::isInstance).map(FieldElement.class::cast)
					.forEach(selected::add);
		}
		ReplaceRefactoring refactoring = new ReplaceRefactoring(query.query(), selected);
		if (!IDE.saveAllEditors(refactoring.files().toArray(IResource[]::new), true)) {
			return;
		}
		try {
			new RefactoringWizardOpenOperation(new ReplaceWizard(refactoring)).run(shell, Messages.ReplaceWizard_ReplaceInDocuments);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private final class InputPage extends UserInputWizardPage {

		InputPage() {
			super("replace");
		}

		@Override
		public void createControl(Composite parent) {
			ReplaceRefactoring refactoring = (ReplaceRefactoring) getRefactoring();
			Composite body = new Composite(parent, SWT.NONE);
			GridLayoutFactory.swtDefaults().numColumns(2).spacing(10, 8).applyTo(body);
			Label summary = new Label(body, SWT.WRAP);
			summary.setText("Replace " + refactoring.query().describe() + " in " + refactoring.valueCount()
					+ (refactoring.valueCount() == 1 ? " value of " : " values of ") + refactoring.documentCount()
					+ (refactoring.documentCount() == 1 ? " document." : " documents."));
			GridDataFactory.fillDefaults().span(2, 1).grab(true, false).hint(420, SWT.DEFAULT).applyTo(summary);
			new Label(body, SWT.NONE).setText(Messages.ReplaceWizard_With);
			Text with = new Text(body, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(with);
			with.addModifyListener(_ -> refactoring.setReplacement(with.getText()));
			if (refactoring.query().regex()) {
				Label hint = new Label(body, SWT.WRAP);
				hint.setText(Messages.ReplaceWizard_RegularExpression12Insert);
				GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(hint);
			}
			Label note = new Label(body, SWT.WRAP);
			note.setText(Messages.ReplaceWizard_OnlyTheMatchedValuesChange);
			GridDataFactory.fillDefaults().span(2, 1).grab(true, false).hint(420, SWT.DEFAULT).indent(0, 8).applyTo(note);
			setControl(body);
			with.setFocus();
		}
	}
}
