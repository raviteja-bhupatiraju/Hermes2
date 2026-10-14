/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * The library's heading: name and folder on the left, summary statistics (records, valid, warnings, errors,
 * credits) as figures on the right, painted in the theme colors.
 */
final class LibraryHeader extends Canvas {

	private static final int MARGIN = 14;
	private static final int STAT_GAP = 26;

	private String folder;
	private String activity;
	private int records;
	private int shown;
	private int valid;
	private int warnings;
	private int errors;
	private String credits = "";

	LibraryHeader(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		setBackground(HlomTheme.color(HlomTheme.SURFACE));
		addPaintListener(event -> paint(event.gc));
		addListener(SWT.Resize, _ -> redraw());
	}

	/** Updates the statistics from the library and the number of records currently shown. */
	void update(String folderText, List<LibraryEntry> entries, int shownCount) {
		folder = folderText;
		records = entries.size();
		shown = shownCount;
		valid = (int) entries.stream().filter(e -> e.status() == LibraryEntry.Status.VALID).count();
		warnings = (int) entries.stream().filter(e -> e.status() == LibraryEntry.Status.WARNINGS).count();
		errors = (int) entries.stream().filter(e -> e.status() == LibraryEntry.Status.ERRORS).count();
		Map<String, BigDecimal> totals = HlomSummary.totals(entries.stream().map(LibraryEntry::summary).toList());
		List<String> parts = new ArrayList<>();
		totals.forEach((key, value) -> parts.add(value.stripTrailingZeros().toPlainString() + " " + key));
		credits = String.join("  ·  ", parts);
		redraw();
	}

	/** Shows a progress text (e.g. "Scanning…") instead of the folder, or the folder again for {@code null}. */
	void setActivity(String text) {
		activity = text;
		redraw();
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		GC gc = new GC(this);
		try {
			gc.setFont(HlomTheme.headingFont());
			int heading = gc.getFontMetrics().getHeight();
			gc.setFont(getFont());
			int line = gc.getFontMetrics().getHeight();
			return new Point(wHint == SWT.DEFAULT ? 600 : wHint, MARGIN + heading + 4 + line + MARGIN);
		} finally {
			gc.dispose();
		}
	}

	private void paint(GC gc) {
		Rectangle area = getClientArea();
		gc.setAdvanced(true);
		gc.setTextAntialias(SWT.ON);
		gc.setAntialias(SWT.ON);

		// Accent keyline at the bottom, as in the editor's form headings.
		gc.setBackground(HlomTheme.color(HlomTheme.BORDER));
		gc.fillRectangle(0, area.height - 1, area.width, 1);
		gc.setBackground(HlomTheme.color(HlomTheme.ACCENT));
		gc.fillRectangle(0, area.height - 2, 56, 2);

		// Statistics, right to left.
		int x = area.width - MARGIN;
		List<Stat> stats = new ArrayList<>();
		if (!credits.isEmpty()) {
			stats.add(new Stat(credits, Messages.LibraryHeader_CreditsOffered, HlomTheme.INK, true));
		}
		stats.add(new Stat(Integer.toString(errors), Messages.LibraryHeader_WithErrors, errors > 0 ? HlomTheme.ACCENT : HlomTheme.MUTED,
				false));
		stats.add(new Stat(Integer.toString(warnings), Messages.LibraryHeader_WithWarnings, warnings > 0 ? HlomTheme.AMBER : HlomTheme.MUTED,
				false));
		stats.add(new Stat(Integer.toString(valid), "valid", valid > 0 ? HlomTheme.TEAL : HlomTheme.MUTED, false));
		stats.add(new Stat(shown == records ? Integer.toString(records) : shown + " / " + records,
				records == 1 ? "record" : shown == records ? "records" : "records shown", HlomTheme.INK, false));
		// Not enough room: drop the least important figures (credits first, the record count last).
		while (!stats.isEmpty() && stats.stream().mapToInt(s -> s.width(gc) + STAT_GAP).sum() > area.width - 300) {
			stats.removeFirst();
		}
		int leftLimit = 0;
		for (Stat stat : stats) {
			int width = stat.width(gc);
			x -= width;
			stat.paint(gc, x, MARGIN - 2);
			x -= STAT_GAP;
			leftLimit = x;
		}

		// Title and folder.
		Image icon = HlomTheme.image("icons/library/library.png");
		int textX = MARGIN;
		gc.setFont(HlomTheme.headingFont());
		int headingHeight = gc.getFontMetrics().getHeight();
		if (icon != null) {
			Rectangle bounds = icon.getBounds();
			gc.drawImage(icon, MARGIN, MARGIN + (headingHeight - bounds.height) / 2);
			textX += bounds.width + 8;
		}
		gc.setForeground(HlomTheme.color(HlomTheme.INK));
		gc.drawText(Messages.LibraryHeader_MetadataLibrary, textX, MARGIN, true);
		gc.setFont(getFont());
		gc.setForeground(HlomTheme.color(activity != null ? HlomTheme.TEAL : HlomTheme.SLATE));
		String second = activity != null ? activity : folder != null ? folder : "No folder chosen";
		int available = (leftLimit > 0 ? leftLimit : area.width - MARGIN) - MARGIN;
		gc.drawText(LibraryCells.shorten(gc, second, available), MARGIN, MARGIN + headingHeight + 4, true);
	}

	/** A figure with a caption below it. */
	private record Stat(String value, String label, RGB color, boolean small) {

		int width(GC gc) {
			gc.setFont(small ? HlomTheme.sectionFont() : HlomTheme.headingFont());
			int valueWidth = gc.textExtent(value).x;
			gc.setFont(HlomTheme.badgeFont());
			return Math.max(valueWidth, gc.textExtent(label.toUpperCase()).x);
		}

		void paint(GC gc, int x, int y) {
			Font valueFont = small ? HlomTheme.sectionFont() : HlomTheme.headingFont();
			gc.setFont(HlomTheme.headingFont());
			int bigHeight = gc.getFontMetrics().getHeight();
			gc.setFont(valueFont);
			int height = gc.getFontMetrics().getHeight();
			gc.setForeground(HlomTheme.color(color));
			gc.drawText(value, x, y + (bigHeight - height), true);
			gc.setFont(HlomTheme.badgeFont());
			Color muted = HlomTheme.color(HlomTheme.SLATE);
			gc.setForeground(muted);
			gc.drawText(label.toUpperCase(), x, y + bigHeight + 2, true);
		}
	}
}
