/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.compare.ChangeKind;
import edu.louisiana.caai.lom.model.support.compare.DiffResult;

/**
 * The painted header of the compare view: both records (title, origin and file) side by side and the counts
 * of changed, added and removed fields as colored pills.
 */
final class CompareHeader extends Canvas {

	private static final int MARGIN = 12;
	private static final int GAP = 8;
	private static final String SAMPLE = "AgÉy";

	/** A count pill: its text, color and icon. */
	private record Pill(String text, RGB color, String image) {
	}

	private CompareSide left;
	private CompareSide right;
	private DiffResult result;

	CompareHeader(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		setBackground(HlomTheme.color(HlomTheme.SURFACE));
		addPaintListener(event -> paint(event.gc));
		addListener(SWT.Resize, _ -> redraw());
	}

	void setInput(CompareSide leftSide, CompareSide rightSide, DiffResult diff) {
		this.left = leftSide;
		this.right = rightSide;
		this.result = diff;
		setToolTipText(leftSide == null ? null : leftSide.path() + "\n↔\n" + rightSide.path());
		redraw();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		GC gc = new GC(this);
		try {
			gc.setFont(HlomTheme.sectionFont());
			int title = gc.textExtent(SAMPLE).y;
			gc.setFont(getFont());
			int text = gc.textExtent(SAMPLE).y;
			gc.setFont(HlomTheme.badgeFont());
			int tag = gc.textExtent(SAMPLE).y;
			return new Point(wHint == SWT.DEFAULT ? 600 : wHint, MARGIN + tag + 3 + title + 2 + text + MARGIN + 3);
		} finally {
			gc.dispose();
		}
	}

	private void paint(GC gc) {
		Rectangle area = getClientArea();
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
		gc.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		gc.fillRectangle(area);
		gc.setBackground(HlomTheme.color(HlomTheme.ACCENT));
		gc.fillRectangle(0, area.height - 2, area.width, 2);
		if (left == null || right == null || result == null) {
			return;
		}

		// Count pills, right aligned
		List<Pill> pills = new ArrayList<>();
		if (result.isIdentical()) {
			pills.add(new Pill(Messages.CompareHeader_Identical, HlomTheme.TEAL, "icons/compare/same.png"));
		} else {
			pills.add(new Pill(result.count(ChangeKind.CHANGED) + " changed", HlomTheme.AMBER, "icons/compare/changed.png"));
			pills.add(new Pill(result.count(ChangeKind.ADDED) + " added", HlomTheme.TEAL, "icons/compare/added.png"));
			pills.add(new Pill(result.count(ChangeKind.REMOVED) + " removed", HlomTheme.ACCENT, "icons/compare/removed.png"));
		}
		gc.setFont(HlomTheme.boldFont());
		int pillHeight = gc.textExtent(SAMPLE).y + 10;
		int x = area.width - MARGIN;
		int pillY = (area.height - 2 - pillHeight) / 2;
		for (Pill pill : pills.reversed()) {
			String text = pill.text();
			RGB color = pill.color();
			Image image = HlomTheme.image(pill.image());
			int width = gc.textExtent(text).x + 24 + (image == null ? 0 : 20);
			x -= width;
			gc.setBackground(HlomTheme.color(tint(color)));
			gc.fillRoundRectangle(x, pillY, width, pillHeight, pillHeight, pillHeight);
			gc.setForeground(HlomTheme.color(color));
			gc.drawRoundRectangle(x, pillY, width - 1, pillHeight - 1, pillHeight, pillHeight);
			int textX = x + 12;
			if (image != null) {
				gc.drawImage(image, textX, pillY + (pillHeight - 16) / 2);
				textX += 20;
			}
			gc.drawText(text, textX, pillY + 5, true);
			x -= GAP;
		}

		// The two sides
		int available = x - MARGIN - GAP * 2;
		Image arrow = HlomTheme.image("icons/compare/swap.png");
		int arrowWidth = 16 + GAP * 2;
		int sideWidth = Math.max(80, (available - arrowWidth) / 2);
		paintSide(gc, left, "LEFT", MARGIN, sideWidth, HlomTheme.SLATE);
		if (arrow != null) {
			gc.drawImage(arrow, MARGIN + sideWidth + GAP, (area.height - 2 - 16) / 2);
		}
		paintSide(gc, right, "RIGHT", MARGIN + sideWidth + arrowWidth, sideWidth, HlomTheme.ACCENT);
	}

	private void paintSide(GC gc, CompareSide side, String position, int x, int width, RGB accent) {
		int y = MARGIN - 2;
		// accent bar
		gc.setBackground(HlomTheme.color(accent));
		Rectangle area = getClientArea();
		gc.fillRoundRectangle(x, y, 3, area.height - 2 - 2 * y, 3, 3);
		int textX = x + 10;
		int textWidth = width - 10;

		gc.setFont(HlomTheme.badgeFont());
		String tag = position + " · " + side.origin().tag();
		Point tagSize = gc.textExtent(tag);
		RGB tagColor = side.origin() == CompareSide.Origin.EDITOR ? HlomTheme.TEAL : HlomTheme.SLATE;
		gc.setBackground(HlomTheme.color(tint(tagColor)));
		gc.fillRoundRectangle(textX, y, tagSize.x + 10, tagSize.y + 2, 6, 6);
		gc.setForeground(HlomTheme.color(tagColor));
		gc.drawText(tag, textX + 5, y + 1, true);
		y += tagSize.y + 5;

		gc.setFont(HlomTheme.sectionFont());
		gc.setForeground(HlomTheme.color(HlomTheme.INK));
		String title = shorten(gc, side.title(), textWidth);
		gc.drawText(title, textX, y, true);
		y += gc.textExtent(SAMPLE).y + 2;

		gc.setFont(getFont());
		gc.setForeground(HlomTheme.color(HlomTheme.MUTED));
		gc.drawText(shorten(gc, side.description(), textWidth), textX, y, true);
	}

	/** A pale version of a palette color, for pill backgrounds. */
	static RGB tint(RGB color) {
		return new RGB(255 - (255 - color.red) * 12 / 100, 255 - (255 - color.green) * 12 / 100,
				255 - (255 - color.blue) * 12 / 100);
	}

	private static String shorten(GC gc, String text, int width) {
		if (text == null) {
			return "";
		}
		if (gc.textExtent(text).x <= width) {
			return text;
		}
		String ellipsis = "…";
		int end = text.length();
		while (end > 0 && gc.textExtent(text.substring(0, end) + ellipsis).x > width) {
			end--;
		}
		return text.substring(0, end) + ellipsis;
	}
}
