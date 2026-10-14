/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * Lays out children left to right, wrapping into rows, each child vertically centered in its row.
 * <p>
 * Unlike {@code RowLayout}, the preferred size without a width hint uses the composite's current width, so
 * that a token list in a grab-horizontal grid cell wraps instead of widening the form.
 */
final class WrapLayout extends Layout {

	/** Width assumed before the composite has been laid out once. */
	private static final int INITIAL_WIDTH = 320;

	private final int spacing;

	WrapLayout(int spacing) {
		this.spacing = spacing;
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		int width = wHint != SWT.DEFAULT ? wHint : composite.getSize().x > 0 ? composite.getSize().x : INITIAL_WIDTH;
		int height = arrange(composite, width, false);
		return new Point(wHint != SWT.DEFAULT ? wHint : Math.min(width, preferredWidth(composite)),
				hHint != SWT.DEFAULT ? hHint : height);
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		Rectangle area = composite.getClientArea();
		arrange(composite, area.width, true);
	}

	private int preferredWidth(Composite composite) {
		int width = 0;
		for (Control child : composite.getChildren()) {
			width += child.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + spacing;
		}
		return Math.max(0, width - spacing);
	}

	/** Places (or only measures) the children; returns the total height. */
	private int arrange(Composite composite, int width, boolean move) {
		Control[] children = composite.getChildren();
		Point[] sizes = new Point[children.length];
		for (int i = 0; i < children.length; i++) {
			sizes[i] = size(children[i], width);
		}
		int y = 0;
		int rowStart = 0;
		while (rowStart < children.length) {
			int x = 0;
			int rowHeight = 0;
			int rowEnd = rowStart;
			while (rowEnd < children.length && (rowEnd == rowStart || x + sizes[rowEnd].x <= width)) {
				x += sizes[rowEnd].x + spacing;
				rowHeight = Math.max(rowHeight, sizes[rowEnd].y);
				rowEnd++;
			}
			if (move) {
				int cx = 0;
				for (int i = rowStart; i < rowEnd; i++) {
					children[i].setBounds(cx, y + (rowHeight - sizes[i].y) / 2, Math.min(sizes[i].x, width), sizes[i].y);
					cx += sizes[i].x + spacing;
				}
			}
			y += rowHeight + spacing;
			rowStart = rowEnd;
		}
		return Math.max(0, y - spacing);
	}

	private static Point size(Control control, int width) {
		Object data = control.getLayoutData();
		Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (data instanceof Point hint) {
			// A fixed width, e.g. of an input field.
			size = new Point(hint.x == SWT.DEFAULT ? size.x : hint.x, size.y);
		}
		return new Point(Math.min(size.x, width), size.y);
	}
}
