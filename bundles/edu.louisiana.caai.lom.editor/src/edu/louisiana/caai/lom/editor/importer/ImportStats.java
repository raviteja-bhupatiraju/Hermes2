/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * A row of figures with captions ("6 RECORDS  4 VALID  2 WITH WARNINGS"), painted like the library heading.
 */
class ImportStats extends Canvas {

	/** A figure with its caption and color. */
	record Figure(String value, String label, RGB color) {
	}

	private static final int GAP = 28;

	private List<Figure> figures = List.of();

	ImportStats(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		addPaintListener(event -> paint(event.gc));
	}

	/** Shows new figures. */
	void setFigures(List<Figure> newFigures) {
		figures = List.copyOf(newFigures);
		redraw();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		GC gc = new GC(this);
		try {
			gc.setFont(HlomTheme.headingFont());
			int value = gc.getFontMetrics().getHeight();
			gc.setFont(HlomTheme.badgeFont());
			int caption = gc.getFontMetrics().getHeight();
			return new Point(wHint == SWT.DEFAULT ? 400 : wHint, hHint == SWT.DEFAULT ? value + caption + 6 : hHint);
		} finally {
			gc.dispose();
		}
	}

	private void paint(GC gc) {
		Rectangle area = getClientArea();
		gc.setBackground(getParent().getBackground());
		gc.fillRectangle(area);
		gc.setTextAntialias(SWT.ON);
		int x = 0;
		for (Figure figure : figures) {
			gc.setFont(HlomTheme.headingFont());
			int valueHeight = gc.getFontMetrics().getHeight();
			int valueWidth = gc.textExtent(figure.value()).x;
			gc.setForeground(HlomTheme.color(figure.color()));
			gc.drawText(figure.value(), x, 0, true);
			gc.setFont(HlomTheme.badgeFont());
			String caption = figure.label().toUpperCase(Locale.ROOT);
			int captionWidth = gc.textExtent(caption).x;
			gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
			gc.drawText(caption, x, valueHeight + 2, true);
			x += Math.max(valueWidth, captionWidth) + GAP;
		}
	}
}
