/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * Small building blocks shared by the form components: compact icon toolbars, content-sized tables, the hint
 * font and form reflow.
 */
public final class FormWidgets {

	/** Extra pixels per table row, for comfortable reading. */
	static final int ROW_PADDING = 6;

	/**
	 * The rows a list on a form page shows at least (also when it has fewer entries, so that it reads as a list with
	 * room for more) and at most before it scrolls.
	 */
	static final int LIST_MIN_ROWS = 4;
	static final int LIST_MAX_ROWS = 10;

	private static final String ROW_HEIGHT = FormWidgets.class.getName() + ".rowHeight";
	private static final String REFLOW_PENDING = FormWidgets.class.getName() + ".reflow";
	private static final String FILL_COLUMN = FormWidgets.class.getName() + ".fillColumn";
	private static final String DESIRED_WIDTH = FormWidgets.class.getName() + ".desiredWidth";
	private static final int MIN_FILL_WIDTH = 40;
	private static final String SMALL_FONT = "edu.louisiana.caai.lom.forms.small";

	private FormWidgets() {
	}

	/** An action icon ({@code icons/actions/<name>.png}), or {@code null} if it is missing. */
	public static Image icon(String name) {
		return HlomTheme.image("icons/actions/" + name + ".png");
	}

	/** A slim flat toolbar with the background of its parent. */
	public static ToolBar toolBar(FormToolkit toolkit, Composite parent) {
		ToolBar bar = new ToolBar(parent, SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);
		toolkit.adapt(bar);
		bar.setForeground(HlomTheme.color(HlomTheme.SLATE));
		return bar;
	}

	/**
	 * A toolbar button showing an action icon; if the icon is missing it shows the fallback text instead.
	 */
	public static ToolItem toolItem(ToolBar bar, String icon, String fallbackText, String toolTip, Runnable action) {
		ToolItem item = new ToolItem(bar, SWT.PUSH);
		Image image = icon(icon);
		if (image != null) {
			item.setImage(image);
		} else {
			item.setText(fallbackText);
		}
		item.setToolTipText(toolTip);
		item.addListener(SWT.Selection, _ -> action.run());
		return item;
	}

	/** A small font for hints and secondary information. */
	public static Font smallFont() {
		FontRegistry registry = JFaceResources.getFontRegistry();
		if (!registry.hasValueFor(SMALL_FONT)) {
			FontDescriptor base = JFaceResources.getDefaultFontDescriptor();
			int height = base.getFontData()[0].getHeight();
			registry.put(SMALL_FONT, base.setHeight(Math.max(7, height - 1)).getFontData());
		}
		return registry.get(SMALL_FONT);
	}

	/** A small bold font for group titles. */
	public static Font smallBoldFont() {
		smallFont();
		return JFaceResources.getFontRegistry().getBold(SMALL_FONT);
	}

	/** Styles a table as a clean list: no grid lines, comfortable row height. */
	static void styleTable(Table table) {
		table.setLinesVisible(false);
		int rowHeight = table.getItemHeight() + ROW_PADDING;
		table.setData(ROW_HEIGHT, rowHeight);
		table.addListener(SWT.MeasureItem, event -> event.height = Math.max(event.height, rowHeight));
	}

	/** Paints a muted message into a table while it has no rows. */
	static void emptyMessage(Table table, String message) {
		table.addListener(SWT.Paint, event -> {
			if (table.getItemCount() > 0 || message == null) {
				return;
			}
			GC gc = event.gc;
			gc.setForeground(HlomTheme.color(HlomTheme.MUTED));
			int top = (table.getHeaderVisible() ? table.getHeaderHeight() : 0) + 4;
			gc.drawText(message, 6, top, true);
		});
	}

	/**
	 * Sizes a table to its rows: at least {@code min} and at most {@code max} rows are visible, more rows
	 * scroll. Returns whether the height changed (the caller should then {@link #reflow} the form).
	 */
	static boolean fitRows(Table table, int min, int max) {
		return fitRows(table, table, min, max);
	}

	/** Like {@link #fitRows(Table, int, int)}, but sets the height of {@code sized} (the table or its holder). */
	static boolean fitRows(Table table, Control sized, int min, int max) {
		GridData gridData;
		if (sized.getLayoutData() instanceof GridData existing) {
			gridData = existing;
		} else {
			gridData = GridDataFactory.fillDefaults().grab(true, false).create();
			sized.setLayoutData(gridData);
		}
		Object stored = table.getData(ROW_HEIGHT);
		int rowHeight = Math.max(table.getItemHeight(), stored instanceof Integer h ? h : 0);
		int rows = Math.max(min, Math.min(max, table.getItemCount()));
		int height = rows * rowHeight + (table.getHeaderVisible() ? table.getHeaderHeight() : 0)
				+ 2 * table.getBorderWidth() + 2;
		ScrollBar horizontal = table.getHorizontalBar();
		if (horizontal != null && horizontal.isVisible()) {
			height += horizontal.getSize().y;
		}
		fitColumns(table);
		if (gridData.heightHint == height) {
			return false;
		}
		gridData.heightHint = height;
		return true;
	}

	/**
	 * Lets one column of a table take all width the other (fixed width) columns leave, so that the columns
	 * always fit the visible area exactly and no horizontal scroll bar appears. Unlike JFace's
	 * {@code TableColumnLayout}, this uses the table's actual client area (which excludes a visible vertical
	 * scroll bar), so rounding at fractional display scales can't make the columns overflow.
	 */
	static void fillColumn(Table table, int fill) {
		table.setData(FILL_COLUMN, fill);
		Listener fit = _ -> fitColumns(table);
		table.addListener(SWT.Resize, fit);
		// The vertical scroll bar appears and disappears without a resize event; re-check when painting.
		table.addListener(SWT.Paint, fit);
	}

	private static void fitColumns(Table table) {
		if (table.isDisposed() || !(table.getData(FILL_COLUMN) instanceof Integer fill)) {
			return;
		}
		TableColumn[] columns = table.getColumns();
		int available = table.getClientArea().width;
		if (fill >= columns.length || available <= 0) {
			return;
		}
		// The other columns keep the width they were created with, unless the table is too narrow for them; then
		// all columns shrink proportionally. The columns never exceed the visible width (one pixel to spare).
		available -= 1;
		int[] desired = new int[columns.length];
		int others = 0;
		for (int i = 0; i < columns.length; i++) {
			if (i != fill) {
				if (columns[i].getData(DESIRED_WIDTH) instanceof Integer width) {
					desired[i] = width;
				} else {
					desired[i] = columns[i].getWidth();
					columns[i].setData(DESIRED_WIDTH, desired[i]);
				}
				others += desired[i];
			}
		}
		desired[fill] = Math.max(MIN_FILL_WIDTH, available - others);
		int total = others + desired[fill];
		for (int i = 0; i < columns.length; i++) {
			int width = total <= available ? desired[i] : desired[i] * available / total;
			if (columns[i].getWidth() != width) {
				columns[i].setWidth(width);
			}
		}
	}

	/**
	 * Shows the vertical scroll bar of a multi-line text only while its text doesn't fit, instead of a
	 * permanently visible (and mostly disabled) bar.
	 */
	public static void autoScrollBar(Text text) {
		ScrollBar bar = text.getVerticalBar();
		if (bar == null) {
			return;
		}
		Listener update = _ -> {
			if (text.isDisposed()) {
				return;
			}
			int needed = text.getLineCount() * text.getLineHeight();
			boolean overflow = needed > text.getClientArea().height;
			if (bar.getVisible() != overflow) {
				bar.setVisible(overflow);
			}
		};
		text.addListener(SWT.Modify, update);
		text.addListener(SWT.Resize, update);
		update.handleEvent(null);
	}

	/**
	 * Re-lays out a control after its size changed and reflows the enclosing scrolled form, so that the page
	 * grows or shrinks with it. Calls are coalesced into one reflow per event loop turn.
	 */
	/**
	 * Lets a grid of equally wide items (check boxes) use as many columns as fit the page, at most
	 * {@code maxColumns}, instead of a fixed number that may be wider than the page. Updated when the form is resized.
	 */
	static void wrapColumns(Composite grid, int maxColumns) {
		ScrolledForm form = null;
		for (Composite parent = grid.getParent(); parent != null; parent = parent.getParent()) {
			if (parent instanceof ScrolledForm scrolledForm) {
				form = scrolledForm;
				break;
			}
		}
		if (form == null || !(grid.getLayout() instanceof org.eclipse.swt.layout.GridLayout layout)) {
			return;
		}
		ScrolledForm page = form;
		org.eclipse.swt.widgets.Listener update = _ -> {
			if (grid.isDisposed() || page.isDisposed() || page.getClientArea().width <= 0) {
				return;
			}
			int itemWidth = 1;
			for (Control child : grid.getChildren()) {
				itemWidth = Math.max(itemWidth, child.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
			}
			// The width from the grid's left edge to the right edge of the page (with the page's right margin).
			int left = page.getBody().toControl(grid.toDisplay(0, 0)).x;
			int available = page.getClientArea().width - Math.max(0, left) - 24;
			int columns = Math.max(1, Math.min(maxColumns,
					(available + layout.horizontalSpacing) / (itemWidth + layout.horizontalSpacing)));
			if (columns != layout.numColumns) {
				layout.numColumns = columns;
				reflow(grid);
			}
		};
		page.addListener(SWT.Resize, update);
		grid.getParent().addListener(SWT.Resize, update);
		grid.addListener(SWT.Dispose, _ -> {
			if (!page.isDisposed()) {
				page.removeListener(SWT.Resize, update);
			}
		});
		grid.getDisplay().asyncExec(() -> update.handleEvent(null));
	}

	public static void reflow(Control control) {
		if (control == null || control.isDisposed()) {
			return;
		}
		control.requestLayout();
		ScrolledForm form = null;
		for (Composite parent = control.getParent(); parent != null; parent = parent.getParent()) {
			if (parent instanceof ScrolledForm scrolledForm) {
				form = scrolledForm;
				break;
			}
		}
		if (form == null || form.getData(REFLOW_PENDING) != null) {
			return;
		}
		ScrolledForm target = form;
		target.setData(REFLOW_PENDING, Boolean.TRUE);
		target.getDisplay().asyncExec(() -> {
			if (!target.isDisposed()) {
				target.setData(REFLOW_PENDING, null);
				target.reflow(true);
			}
		});
	}
}
