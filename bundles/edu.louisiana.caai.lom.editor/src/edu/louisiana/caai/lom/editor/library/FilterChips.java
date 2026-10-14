/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * A row of rounded filter chips in groups; within a group at most one chip is selected (clicking the selected
 * chip again clears it, unless the group requires a selection). Each chip shows a count. Painted, so that it
 * matches the theme on every platform.
 */
final class FilterChips extends Canvas {

	private static final int HEIGHT = 26;
	private static final int PAD = 11;
	private static final int GAP = 6;
	private static final int GROUP_GAP = 18;

	/** A chip: group, key (passed back on selection), label, accent color. */
	static final class Chip {
		final int group;
		final Object key;
		final String label;
		final RGB color;
		int count = -1;
		Rectangle bounds = new Rectangle(0, 0, 0, 0);

		Chip(int group, Object key, String label, RGB color) {
			this.group = group;
			this.key = key;
			this.label = label;
			this.color = color;
		}
	}

	private final List<Chip> chips = new ArrayList<>();
	private final List<Chip> selected = new ArrayList<>();
	private final List<Runnable> listeners = new ArrayList<>();
	private Chip hover;

	FilterChips(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		setBackground(parent.getBackground());
		addPaintListener(event -> paint(event.gc));
		addListener(SWT.MouseMove, this::mouseMove);
		addListener(SWT.MouseExit, _ -> setHover(null));
		addListener(SWT.MouseUp, this::click);
	}

	/** Adds a chip; the first chip of a group added with {@code selected} is initially selected. */
	Chip add(int group, Object key, String label, RGB color, boolean initiallySelected) {
		Chip chip = new Chip(group, key, label, color);
		chips.add(chip);
		if (initiallySelected) {
			selected.add(chip);
		}
		return chip;
	}

	/** The key of the selected chip in a group, or {@code null}. */
	Object selection(int group) {
		for (Chip chip : selected) {
			if (chip.group == group) {
				return chip.key;
			}
		}
		return null;
	}

	/** Sets the count shown on the chip with the given key. */
	void setCount(Object key, int count) {
		chips.stream().filter(c -> Objects.equals(c.key, key)).forEach(c -> c.count = count);
		redraw();
	}

	void addSelectionListener(Runnable listener) {
		listeners.add(listener);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		GC gc = new GC(this);
		try {
			layoutChips(gc);
		} finally {
			gc.dispose();
		}
		int width = chips.isEmpty() ? 0 : chips.getLast().bounds.x + chips.getLast().bounds.width;
		return new Point(wHint == SWT.DEFAULT ? width + 2 : wHint, HEIGHT + 2);
	}

	private void layoutChips(GC gc) {
		int x = 0;
		int group = chips.isEmpty() ? 0 : chips.getFirst().group;
		for (Chip chip : chips) {
			if (chip.group != group) {
				x += GROUP_GAP - GAP;
				group = chip.group;
			}
			gc.setFont(getFont());
			int width = PAD + gc.textExtent(chip.label).x + PAD;
			if (chip.count >= 0) {
				gc.setFont(HlomTheme.badgeFont());
				width += gc.textExtent(Integer.toString(chip.count)).x + 12;
			}
			chip.bounds = new Rectangle(x, 1, width, HEIGHT);
			x += width + GAP;
		}
	}

	private void paint(GC gc) {
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
		layoutChips(gc);
		for (Chip chip : chips) {
			boolean on = selected.contains(chip);
			Rectangle b = chip.bounds;
			int arc = b.height;
			if (on) {
				gc.setBackground(HlomTheme.color(chip.color));
				gc.fillRoundRectangle(b.x, b.y, b.width - 1, b.height - 1, arc, arc);
			} else {
				gc.setBackground(HlomTheme.color(chip == hover ? HlomTheme.BAR : HlomTheme.SURFACE));
				gc.fillRoundRectangle(b.x, b.y, b.width - 1, b.height - 1, arc, arc);
				gc.setForeground(HlomTheme.color(chip == hover ? chip.color : HlomTheme.BORDER));
				gc.drawRoundRectangle(b.x, b.y, b.width - 1, b.height - 1, arc, arc);
			}
			gc.setFont(getFont());
			Point extent = gc.textExtent(chip.label);
			gc.setForeground(HlomTheme.color(on ? HlomTheme.SURFACE : HlomTheme.INK));
			gc.drawText(chip.label, b.x + PAD, b.y + (b.height - extent.y) / 2, true);
			if (chip.count >= 0) {
				gc.setFont(HlomTheme.badgeFont());
				String count = Integer.toString(chip.count);
				Point countExtent = gc.textExtent(count);
				gc.setForeground(HlomTheme.color(on ? HlomTheme.BORDER : HlomTheme.SLATE));
				gc.drawText(count, b.x + PAD + extent.x + 6, b.y + (b.height - countExtent.y) / 2, true);
			}
		}
	}

	private Chip chipAt(int x, int y) {
		return chips.stream().filter(c -> c.bounds.contains(x, y)).findFirst().orElse(null);
	}

	private void mouseMove(Event event) {
		Chip chip = chipAt(event.x, event.y);
		setHover(chip);
		setCursor(chip == null ? null : getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		setToolTipText(chip == null ? null
				: chip.key == null ? "Show all records" : "Show " + chip.label.toLowerCase() + " only");
	}

	private void setHover(Chip chip) {
		if (chip != hover) {
			hover = chip;
			redraw();
		}
	}

	private void click(Event event) {
		Chip chip = chipAt(event.x, event.y);
		if (chip == null || event.button != 1) {
			return;
		}
		boolean wasSelected = selected.contains(chip);
		selected.removeIf(c -> c.group == chip.group);
		if (!wasSelected || chip.key == null) {
			selected.add(chip);
		} else {
			// Deselecting falls back to the group's "all" chip (key null), if there is one.
			chips.stream().filter(c -> c.group == chip.group && c.key == null).findFirst().ifPresent(selected::add);
		}
		redraw();
		listeners.forEach(Runnable::run);
	}
}
