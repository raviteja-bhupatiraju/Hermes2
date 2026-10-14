/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * Custom-painted controls of the {@link OverviewPage}: cards, the score ring, category bars, keyword chips and
 * count badges. All painting is anti-aliased and uses the {@link HlomTheme} palette.
 */
final class OverviewWidgets {

	/** Warning amber (not part of the editor palette, used for warning counts only). */
	static final RGB AMBER = new RGB(0xB4, 0x5F, 0x06);
	/** Information blue. */
	static final RGB BLUE = new RGB(0x25, 0x63, 0xEB);
	/** Success green. */
	static final RGB GREEN = new RGB(0x15, 0x80, 0x3D);

	private static final String FONT_PREFIX = "edu.louisiana.caai.lom.overview.";

	private OverviewWidgets() {
	}

	/** A palette color. */
	static Color color(RGB rgb) {
		return HlomTheme.color(rgb);
	}

	/** A light tint of a color (mixed with white). */
	static RGB tint(RGB rgb, double amount) {
		return new RGB(mix(rgb.red, amount), mix(rgb.green, amount), mix(rgb.blue, amount));
	}

	private static int mix(int channel, double amount) {
		return (int) Math.round(channel * amount + 255 * (1 - amount));
	}

	/** A shared font derived from the default font (must not be disposed). */
	static Font font(int grow, int style) {
		FontRegistry registry = JFaceResources.getFontRegistry();
		String key = FONT_PREFIX + grow + "." + style;
		if (!registry.hasValueFor(key)) {
			FontDescriptor base = JFaceResources.getDefaultFontDescriptor();
			int height = base.getFontData()[0].getHeight();
			registry.put(key, base.setHeight(Math.max(6, height + grow)).setStyle(style).getFontData());
		}
		return registry.get(key);
	}

	private static void antialias(GC gc) {
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
	}

	// ----------------------------------------------------------------------------------------------------
	// Card
	// ----------------------------------------------------------------------------------------------------

	/**
	 * A white rounded card on the page background. Returns the card; its content area is laid out with a one-column
	 * grid layout and has the surface color.
	 *
	 * @param stripe color of a stripe along the left edge, or {@code null}
	 */
	static Composite card(Composite parent, RGB stripe) {
		Composite card = new Composite(parent, SWT.NONE);
		card.setBackground(color(HlomTheme.BACKGROUND));
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = stripe != null ? 24 : 20;
		layout.marginLeft = stripe != null ? 4 : 0;
		layout.marginHeight = 18;
		layout.verticalSpacing = 8;
		card.setLayout(layout);
		card.addListener(SWT.Paint, event -> {
			GC gc = event.gc;
			antialias(gc);
			Rectangle area = card.getClientArea();
			gc.setBackground(color(HlomTheme.SURFACE));
			gc.fillRoundRectangle(0, 0, area.width - 1, area.height - 1, 12, 12);
			gc.setForeground(color(HlomTheme.BORDER));
			gc.drawRoundRectangle(0, 0, area.width - 1, area.height - 1, 12, 12);
			if (stripe != null) {
				gc.setClipping(0, 0, 5, area.height);
				gc.setBackground(color(stripe));
				gc.fillRoundRectangle(0, 0, area.width, area.height, 12, 12);
				gc.setClipping((Rectangle) null);
			}
		});
		card.addListener(SWT.Resize, _ -> card.redraw());
		return card;
	}

	// ----------------------------------------------------------------------------------------------------
	// Score ring
	// ----------------------------------------------------------------------------------------------------

	/** A ring showing a score from 0 to 100, with the number in its center. */
	static final class ScoreRing extends Canvas {

		private static final int SIZE = 128;
		private static final int STROKE = 11;
		private int score;
		private final RGB rgb;

		ScoreRing(Composite parent, RGB rgb) {
			super(parent, SWT.DOUBLE_BUFFERED);
			this.rgb = rgb;
			setBackground(color(HlomTheme.SURFACE));
			addListener(SWT.Paint, this::paint);
			getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = NLS.bind(Messages.OverviewWidgets_CompletenessPercent, score);
				}
			});
		}

		void setScore(int score) {
			this.score = Math.clamp(score, 0, 100);
			redraw();
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			return new Point(SIZE, SIZE);
		}

		private void paint(Event event) {
			GC gc = event.gc;
			antialias(gc);
			int inset = STROKE / 2 + 2;
			int diameter = SIZE - 2 * inset;
			gc.setLineWidth(STROKE);
			gc.setLineCap(SWT.CAP_ROUND);
			gc.setForeground(color(HlomTheme.BAR));
			gc.drawOval(inset, inset, diameter, diameter);
			if (score > 0) {
				gc.setForeground(color(rgb));
				gc.drawArc(inset, inset, diameter, diameter, 90, -Math.max(2, Math.round(score * 3.6f)));
			}
			gc.setLineWidth(1);

			String number = Integer.toString(score);
			gc.setFont(font(15, SWT.BOLD));
			Point numberSize = gc.textExtent(number);
			gc.setFont(font(1, SWT.BOLD));
			Point percentSize = gc.textExtent("%");
			gc.setFont(font(-1, SWT.NORMAL));
			Point captionSize = gc.textExtent("complete");
			int totalWidth = numberSize.x + percentSize.x + 1;
			int top = (SIZE - numberSize.y - captionSize.y + 6) / 2;
			int x = (SIZE - totalWidth) / 2;
			gc.setForeground(color(HlomTheme.INK));
			gc.setFont(font(15, SWT.BOLD));
			gc.drawText(number, x, top, true);
			gc.setForeground(color(HlomTheme.SLATE));
			gc.setFont(font(1, SWT.BOLD));
			gc.drawText("%", x + numberSize.x + 1, top + numberSize.y - percentSize.y - 4, true);
			gc.setFont(font(-1, SWT.NORMAL));
			gc.setForeground(color(HlomTheme.MUTED));
			gc.drawText("complete", (SIZE - captionSize.x) / 2, top + numberSize.y - 6, true);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Category bar
	// ----------------------------------------------------------------------------------------------------

	/** A clickable row with an icon, a label, a fraction and a progress bar. */
	static final class CategoryBar extends Canvas {

		private static final int BAR_HEIGHT = 6;
		private final Image icon;
		private final String label;
		private final String detail;
		private final int score;
		private final RGB rgb;
		private final boolean applicable;
		private boolean hover;

		/**
		 * @param detail     right-aligned text, e.g. "3 / 4"
		 * @param applicable {@code false} to show the row dimmed
		 * @param action     run when the row is clicked or activated with the keyboard
		 */
		CategoryBar(Composite parent, Image icon, String label, String detail, int score, RGB rgb, boolean applicable,
				Runnable action) {
			super(parent, SWT.DOUBLE_BUFFERED);
			this.icon = icon;
			this.label = label;
			this.detail = detail;
			this.score = score;
			this.rgb = rgb;
			this.applicable = applicable;
			setBackground(color(HlomTheme.SURFACE));
			setCursor(getDisplay().getSystemCursor(SWT.CURSOR_HAND));
			addListener(SWT.Paint, this::paint);
			addListener(SWT.MouseEnter, _ -> setHover(true));
			addListener(SWT.MouseExit, _ -> setHover(false));
			addListener(SWT.MouseUp, e -> {
				if (e.button == 1 && getClientArea().contains(e.x, e.y)) {
					action.run();
				}
			});
			addListener(SWT.KeyDown, e -> {
				if (e.character == SWT.CR || e.character == ' ') {
					action.run();
				}
			});
			addListener(SWT.Traverse, e -> {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
					e.doit = true;
				}
			});
			addListener(SWT.FocusIn, _ -> redraw());
			addListener(SWT.FocusOut, _ -> redraw());
			getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = label + ", " + (applicable ? score + " percent complete" : detail);
				}
			});
		}

		private void setHover(boolean value) {
			hover = value;
			redraw();
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			GC gc = new GC(this);
			try {
				gc.setFont(getFont());
				int text = gc.getFontMetrics().getHeight();
				int width = wHint == SWT.DEFAULT ? 260 : wHint;
				return new Point(width, 8 + Math.max(text, 16) + 6 + BAR_HEIGHT + 8);
			} finally {
				gc.dispose();
			}
		}

		private void paint(Event event) {
			GC gc = event.gc;
			antialias(gc);
			Rectangle area = getClientArea();
			if (hover || isFocusControl()) {
				gc.setBackground(color(tint(HlomTheme.BAR, 0.8)));
				gc.fillRoundRectangle(0, 0, area.width - 1, area.height - 1, 8, 8);
			}
			int pad = 8;
			int textHeight = gc.getFontMetrics().getHeight();
			int line = Math.max(textHeight, 16);
			int x = pad;
			if (icon != null) {
				Rectangle bounds = icon.getBounds();
				gc.drawImage(icon, 0, 0, bounds.width, bounds.height, x, 8 + (line - 16) / 2, 16, 16);
				x += 16 + 8;
			}
			gc.setFont(getFont());
			gc.setForeground(color(applicable ? HlomTheme.INK : HlomTheme.MUTED));
			gc.drawText(label, x, 8 + (line - textHeight) / 2, true);

			String right = applicable ? score + "%" : "";
			gc.setFont(font(0, SWT.BOLD));
			Point rightSize = gc.textExtent(right);
			gc.setForeground(color(applicable ? HlomTheme.INK : HlomTheme.MUTED));
			gc.drawText(right, area.width - pad - rightSize.x, 8 + (line - textHeight) / 2, true);
			gc.setFont(getFont());
			Point detailSize = gc.textExtent(detail);
			gc.setForeground(color(HlomTheme.MUTED));
			gc.drawText(detail, area.width - pad - rightSize.x - (right.isEmpty() ? 0 : 10) - detailSize.x,
					8 + (line - textHeight) / 2, true);

			int barY = 8 + line + 6;
			int barX = icon != null ? pad + 24 : pad;
			int barWidth = area.width - barX - pad;
			gc.setBackground(color(HlomTheme.BAR));
			gc.fillRoundRectangle(barX, barY, barWidth, BAR_HEIGHT, BAR_HEIGHT, BAR_HEIGHT);
			if (applicable && score > 0) {
				gc.setBackground(color(rgb));
				gc.fillRoundRectangle(barX, barY, Math.max(BAR_HEIGHT, barWidth * score / 100), BAR_HEIGHT, BAR_HEIGHT,
						BAR_HEIGHT);
			}
			if (isFocusControl()) {
				gc.setForeground(color(HlomTheme.MUTED));
				gc.setLineDash(new int[] { 1, 2 });
				gc.drawRoundRectangle(0, 0, area.width - 1, area.height - 1, 8, 8);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Chips
	// ----------------------------------------------------------------------------------------------------

	/** A chip: main text and an optional subtle detail (e.g. a MeSH descriptor id). */
	record Chip(String text, String detail, boolean coded) {
	}

	/** A flow of rounded chips that wraps to the available width. */
	static final class ChipFlow extends Canvas {

		private static final int GAP = 6;
		private static final int PAD_X = 10;
		private static final int PAD_Y = 4;
		private final List<Chip> chips;

		ChipFlow(Composite parent, List<Chip> chips) {
			super(parent, SWT.DOUBLE_BUFFERED);
			this.chips = List.copyOf(chips);
			setBackground(color(HlomTheme.SURFACE));
			addListener(SWT.Paint, this::paint);
			addListener(SWT.Resize, _ -> redraw());
			getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = NLS.bind(Messages.OverviewWidgets_Keywords, String.join(", ", chips.stream().map(Chip::text).toList()));
				}
			});
			StringBuilder tooltip = new StringBuilder();
			for (Chip chip : chips) {
				if (chip.detail() != null) {
					tooltip.append(tooltip.isEmpty() ? "" : "\n").append(chip.text()).append(": ").append(chip.detail());
				}
			}
			setToolTipText(tooltip.isEmpty() ? null : tooltip.toString());
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			GC gc = new GC(this);
			try {
				List<Rectangle> boxes = layout(gc, wHint == SWT.DEFAULT ? Integer.MAX_VALUE : wHint);
				int width = 0;
				int height = 0;
				for (Rectangle box : boxes) {
					width = Math.max(width, box.x + box.width);
					height = Math.max(height, box.y + box.height);
				}
				return new Point(wHint == SWT.DEFAULT ? width : wHint, height);
			} finally {
				gc.dispose();
			}
		}

		private List<Rectangle> layout(GC gc, int width) {
			List<Rectangle> boxes = new ArrayList<>();
			int x = 0;
			int y = 0;
			int height = chipHeight(gc);
			for (Chip chip : chips) {
				int chipWidth = chipWidth(gc, chip);
				if (x > 0 && x + chipWidth > width) {
					x = 0;
					y += height + GAP;
				}
				boxes.add(new Rectangle(x, y, chipWidth, height));
				x += chipWidth + GAP;
			}
			return boxes;
		}

		private int chipHeight(GC gc) {
			gc.setFont(getFont());
			return gc.getFontMetrics().getHeight() + 2 * PAD_Y;
		}

		private int chipWidth(GC gc, Chip chip) {
			gc.setFont(getFont());
			int width = gc.textExtent(chip.text()).x + 2 * PAD_X;
			if (chip.detail() != null) {
				gc.setFont(font(-1, SWT.NORMAL));
				width += 6 + gc.textExtent(chip.detail()).x;
			}
			return width;
		}

		private void paint(Event event) {
			GC gc = event.gc;
			antialias(gc);
			List<Rectangle> boxes = layout(gc, getClientArea().width);
			for (int i = 0; i < chips.size(); i++) {
				Chip chip = chips.get(i);
				Rectangle box = boxes.get(i);
				RGB fill = chip.coded() ? tint(HlomTheme.TEAL, 0.09) : HlomTheme.BAR;
				RGB border = chip.coded() ? tint(HlomTheme.TEAL, 0.28) : HlomTheme.BORDER;
				gc.setBackground(color(fill));
				gc.fillRoundRectangle(box.x, box.y, box.width - 1, box.height - 1, box.height, box.height);
				gc.setForeground(color(border));
				gc.drawRoundRectangle(box.x, box.y, box.width - 1, box.height - 1, box.height, box.height);
				gc.setFont(getFont());
				gc.setForeground(color(HlomTheme.INK));
				int textHeight = gc.getFontMetrics().getHeight();
				gc.drawText(chip.text(), box.x + PAD_X, box.y + PAD_Y, true);
				if (chip.detail() != null) {
					int x = box.x + PAD_X + gc.textExtent(chip.text()).x + 6;
					gc.setFont(font(-1, SWT.NORMAL));
					int detailHeight = gc.getFontMetrics().getHeight();
					gc.setForeground(color(chip.coded() ? HlomTheme.TEAL : HlomTheme.MUTED));
					gc.drawText(chip.detail(), x, box.y + PAD_Y + (textHeight - detailHeight) / 2 + 1, true);
				}
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Count badge
	// ----------------------------------------------------------------------------------------------------

	/** A count in a colored pill followed by a label, e.g. "2 Errors"; dimmed when the count is zero. */
	static final class CountBadge extends Canvas {

		private final int count;
		private final String label;
		private final RGB rgb;

		CountBadge(Composite parent, int count, String singular, String plural, RGB rgb) {
			super(parent, SWT.DOUBLE_BUFFERED);
			this.count = count;
			this.label = count == 1 ? singular : plural;
			this.rgb = count == 0 ? HlomTheme.MUTED : rgb;
			setBackground(color(HlomTheme.SURFACE));
			addListener(SWT.Paint, this::paint);
			getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = count + " " + label;
				}
			});
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			GC gc = new GC(this);
			try {
				gc.setFont(font(0, SWT.BOLD));
				Point number = gc.textExtent(Integer.toString(count));
				gc.setFont(getFont());
				Point text = gc.textExtent(label);
				int pill = Math.max(number.x + 16, number.y + 8);
				return new Point(pill + 8 + text.x + 4, number.y + 8);
			} finally {
				gc.dispose();
			}
		}

		private void paint(Event event) {
			GC gc = event.gc;
			antialias(gc);
			gc.setFont(font(0, SWT.BOLD));
			String number = Integer.toString(count);
			Point numberSize = gc.textExtent(number);
			int height = numberSize.y + 8;
			int pill = Math.max(numberSize.x + 16, height);
			gc.setBackground(color(count == 0 ? HlomTheme.BAR : tint(rgb, 0.14)));
			gc.fillRoundRectangle(0, 0, pill, height - 1, height, height);
			gc.setForeground(color(rgb));
			gc.drawText(number, (pill - numberSize.x) / 2, 4, true);
			gc.setFont(getFont());
			gc.setForeground(color(count == 0 ? HlomTheme.MUTED : HlomTheme.SLATE));
			Point labelSize = gc.textExtent(label);
			gc.drawText(label, pill + 8, (height - labelSize.y) / 2, true);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Step number
	// ----------------------------------------------------------------------------------------------------

	/** A small numbered circle for the next steps list. */
	static final class StepNumber extends Canvas {

		private static final int SIZE = 22;
		private final int number;
		private final RGB rgb;

		StepNumber(Composite parent, int number, RGB rgb) {
			super(parent, SWT.DOUBLE_BUFFERED);
			this.number = number;
			this.rgb = rgb;
			setBackground(color(HlomTheme.SURFACE));
			addListener(SWT.Paint, (Listener) this::paint);
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			return new Point(SIZE, SIZE);
		}

		private void paint(Event event) {
			GC gc = event.gc;
			antialias(gc);
			gc.setBackground(color(tint(rgb, 0.12)));
			gc.fillOval(0, 0, SIZE - 1, SIZE - 1);
			gc.setFont(font(-1, SWT.BOLD));
			gc.setForeground(color(rgb));
			String text = Integer.toString(number);
			Point size = gc.textExtent(text);
			gc.drawText(text, (SIZE - size.x) / 2, (SIZE - size.y) / 2, true);
		}
	}
}
