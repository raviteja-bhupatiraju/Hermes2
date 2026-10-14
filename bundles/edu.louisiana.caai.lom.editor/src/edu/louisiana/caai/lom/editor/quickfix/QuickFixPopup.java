/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import java.util.List;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;

/**
 * The Ctrl+1 popup: lists the fixes of a problem with the selected fix's description; Enter or a double-click
 * applies the fix, Esc closes the popup.
 */
public class QuickFixPopup extends PopupDialog {

	private static final int WIDTH = 460;

	private final ValidationSource source;
	private final HlomProblem problem;
	private final List<HlomFix> fixes;
	private final Point anchor;
	private Table table;
	private Label description;

	/**
	 * @param anchor the display location of the popup's top left corner, or {@code null} to center it on the parent
	 */
	public QuickFixPopup(Shell parent, ValidationSource source, HlomProblem problem, Point anchor) {
		super(parent, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, false, false, Messages.QuickFixPopup_QuickFix,
				Messages.QuickFixPopup_EnterToApplyEscTo);
		this.source = source;
		this.problem = problem;
		this.fixes = QuickFixes.offered(problem);
		this.anchor = anchor;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayoutFactory.fillDefaults().margins(10, 8).spacing(6, 8).numColumns(2).applyTo(composite);

		Label icon = new Label(composite, SWT.NONE);
		icon.setImage(ProblemImages.severity(problem.severity()));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).applyTo(icon);
		Label message = new Label(composite, SWT.WRAP);
		message.setText(escape(problem.message()));
		message.setFont(HlomTheme.boldFont());
		GridDataFactory.fillDefaults().grab(true, false).hint(WIDTH - 50, SWT.DEFAULT).applyTo(message);

		table = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.hint(WIDTH - 20, Math.max(1, fixes.size()) * table.getItemHeight()).applyTo(table);
		for (HlomFix fix : fixes) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(fix.label());
			item.setImage(fix.kind() == HlomFix.Kind.NAVIGATE ? QuickFixImages.navigate() : QuickFixImages.bulb());
			item.setData(fix);
		}
		if (fixes.isEmpty()) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(Messages.QuickFixPopup_NoQuickFixesAvailable);
			item.setForeground(HlomTheme.color(HlomTheme.MUTED));
		}

		Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(separator);
		description = new Label(composite, SWT.WRAP);
		description.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).hint(WIDTH - 20, SWT.DEFAULT).applyTo(description);

		table.addListener(SWT.Selection, _ -> updateDescription());
		table.addListener(SWT.DefaultSelection, _ -> applySelected());
		table.addListener(SWT.KeyDown, e -> {
			if (e.character == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				e.doit = false;
				applySelected();
			}
		});
		table.addListener(SWT.MouseMove, e -> {
			TableItem item = table.getItem(new Point(e.x, e.y));
			if (item != null && table.getSelectionCount() > 0 && table.getSelection()[0] != item) {
				table.setSelection(item);
				updateDescription();
			}
		});
		if (table.getItemCount() > 0) {
			table.setSelection(0);
		}
		updateDescription();
		applyBackground(composite);
		return composite;
	}

	private void applyBackground(Composite composite) {
		composite.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		for (Control child : composite.getChildren()) {
			child.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		}
	}

	private void updateDescription() {
		HlomFix fix = selected();
		description.setText(fix == null ? "Problems that need your input have no automatic fix."
				: escape(fix.description()));
		description.getParent().layout(true);
	}

	private HlomFix selected() {
		TableItem[] selection = table.getSelection();
		return selection.length == 1 && selection[0].getData() instanceof HlomFix fix ? fix : null;
	}

	private void applySelected() {
		HlomFix fix = selected();
		if (fix == null) {
			return;
		}
		close();
		QuickFixes.apply(source, fix, problem);
	}

	@Override
	protected Control getFocusControl() {
		return table;
	}

	@Override
	protected Point getDefaultSize() {
		return getShell().computeSize(WIDTH, SWT.DEFAULT, true);
	}

	@Override
	protected Point getDefaultLocation(Point initialSize) {
		if (anchor == null) {
			return super.getDefaultLocation(initialSize);
		}
		Rectangle bounds = getShell().getMonitor().getClientArea();
		int x = Math.max(bounds.x, Math.min(anchor.x, bounds.x + bounds.width - initialSize.x));
		int y = anchor.y;
		if (y + initialSize.y > bounds.y + bounds.height) {
			y = Math.max(bounds.y, anchor.y - initialSize.y - 24);
		}
		return new Point(x, y);
	}

	/** Labels interpret '&' as a mnemonic. */
	private static String escape(String text) {
		return text == null ? "" : text.replace("&", "&&");
	}
}
