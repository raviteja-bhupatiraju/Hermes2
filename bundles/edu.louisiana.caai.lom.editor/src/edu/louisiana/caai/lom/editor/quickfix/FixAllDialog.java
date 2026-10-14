/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;

/**
 * Confirms "Fix All": lists each automatic fix with the problem it repairs; fixes can be deselected.
 */
class FixAllDialog extends TitleAreaDialog {

	private final List<HlomFixes.Planned> plan;
	private final List<HlomFixes.Planned> selected = new ArrayList<>();
	private CheckboxTableViewer viewer;

	FixAllDialog(Shell parentShell, List<HlomFixes.Planned> plan) {
		super(parentShell);
		this.plan = plan;
		setHelpAvailable(false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.FixAllDialog_FixAll);
		newShell.setImage(QuickFixImages.fixAll());
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(880, 480);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		HelpContexts.set(parent, HelpContexts.FIX_ALL_DIALOG);
		Composite area = (Composite) super.createDialogArea(parent);
		setTitle(NLS.bind(Messages.FixAllDialog_FixAutomatically, plan.size(), (plan.size() == 1 ? " problem" : " problems")));
		setMessage(Messages.FixAllDialog_TheSelectedFixesAreApplied);
		setTitleImage(HlomTheme.image("icons/library/validate@2x.png"));

		Composite body = new Composite(area, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 10).applyTo(body);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);

		Composite tableComposite = new Composite(body, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		tableComposite.setLayout(layout);
		GridDataFactory.fillDefaults().grab(true, true).hint(600, 220).applyTo(tableComposite);
		viewer = CheckboxTableViewer.newCheckList(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		ColumnViewerToolTipSupport.enableFor(viewer);

		column(layout, Messages.FixAllDialog_Fix, 45, planned -> planned.fix().label(), planned -> planned.fix().description(), true);
		column(layout, Messages.FixAllDialog_Problem, 55, planned -> planned.problem().message(), planned -> planned.problem().message(),
				false);
		viewer.setInput(plan);
		viewer.setAllChecked(true);
		viewer.addCheckStateListener(_ -> updateButtons());

		Composite buttons = new Composite(body, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(buttons);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(buttons);
		Button all = new Button(buttons, SWT.PUSH);
		all.setText(Messages.FixAllDialog_SelectAll);
		all.addListener(SWT.Selection, _ -> {
			viewer.setAllChecked(true);
			updateButtons();
		});
		Button none = new Button(buttons, SWT.PUSH);
		none.setText(Messages.FixAllDialog_DeselectAll);
		none.addListener(SWT.Selection, _ -> {
			viewer.setAllChecked(false);
			updateButtons();
		});
		Label hint = new Label(buttons, SWT.WRAP);
		hint.setText(Messages.FixAllDialog_ProblemsThatNeedYourInput);
		hint.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.END, SWT.CENTER).applyTo(hint);
		return area;
	}

	private void column(TableColumnLayout layout, String title, int weight,
			java.util.function.Function<HlomFixes.Planned, String> text,
			java.util.function.Function<HlomFixes.Planned, String> toolTip, boolean withIcon) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		layout.setColumnData(column.getColumn(), new ColumnWeightData(weight, 80));
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return text.apply((HlomFixes.Planned) element);
			}

			@Override
			public String getToolTipText(Object element) {
				return toolTip.apply((HlomFixes.Planned) element);
			}

			@Override
			public Image getImage(Object element) {
				if (withIcon) {
					return QuickFixImages.bulb();
				}
				HlomProblem problem = ((HlomFixes.Planned) element).problem();
				return ProblemImages.severity(problem.severity());
			}
		});
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Messages.FixAllDialog_ApplyFixes, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private void updateButtons() {
		int count = viewer.getCheckedElements().length;
		getButton(IDialogConstants.OK_ID).setEnabled(count > 0);
		getButton(IDialogConstants.OK_ID).setText(count == 1 ? "&Apply 1 Fix" : "&Apply " + count + " Fixes");
		getButton(IDialogConstants.OK_ID).getParent().layout();
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		updateButtons();
		return contents;
	}

	@Override
	protected void okPressed() {
		selected.clear();
		for (Object element : viewer.getCheckedElements()) {
			selected.add((HlomFixes.Planned) element);
		}
		super.okPressed();
	}

	/** The fixes the user kept selected (valid after OK). */
	List<HlomFixes.Planned> getSelected() {
		return selected;
	}
}
