/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * A clickable, custom-painted card with an icon, a title and a wrapped description, used for the action tiles of
 * the start page and the template gallery of the new-document wizard. Cards highlight on hover and keyboard focus,
 * can show a selected state (vermilion outline) and are activated by click, Enter or Space.
 */
public class Card extends Canvas {

	/** Icon above the text (tiles). */
	public static final int VERTICAL = 0;

	/** Icon left of the text (compact list cards). */
	public static final int HORIZONTAL = 1;

	private static final int PADDING = 16;
	private static final int ICON_BOX = 44;
	private static final int GAP = 12;
	private static final int RADIUS = 12;
	private static final int DEFAULT_WIDTH = 200;

	private final int orientation;
	private Image icon;
	private String title = "";
	private String description = "";
	private RGB accent = HlomTheme.ACCENT;
	private boolean selected;
	private boolean hover;
	private int maxDescriptionLines = 4;
	private final List<Runnable> actions = new ArrayList<>();

	/** Creates a card in the given orientation ({@link #VERTICAL} or {@link #HORIZONTAL}). */
	public Card(Composite parent, int orientation) {
		super(parent, SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);
		this.orientation = orientation;
		setCursor(getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		addPaintListener(event -> paint(event.gc));
		addListener(SWT.MouseEnter, _ -> setHover(true));
		addListener(SWT.MouseExit, _ -> setHover(false));
		addListener(SWT.MouseUp, event -> {
			if (event.button == 1 && getClientArea().contains(event.x, event.y)) {
				activate();
			}
		});
		addListener(SWT.KeyDown, this::keyDown);
		addListener(SWT.Traverse, event -> {
			if (event.detail == SWT.TRAVERSE_TAB_NEXT || event.detail == SWT.TRAVERSE_TAB_PREVIOUS
					|| event.detail == SWT.TRAVERSE_RETURN) {
				event.doit = true;
			}
		});
		addListener(SWT.FocusIn, _ -> redraw());
		addListener(SWT.FocusOut, _ -> redraw());
		getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent event) {
				event.result = title;
			}

			@Override
			public void getDescription(AccessibleEvent event) {
				event.result = description;
			}
		});
	}

	/** Sets the icon (shared image, not disposed by the card). */
	public Card icon(Image image) {
		this.icon = image;
		redraw();
		return this;
	}

	/** Sets the title. */
	public Card title(String text) {
		this.title = text == null ? "" : text;
		setToolTipText(null);
		redraw();
		return this;
	}

	/** Sets the description. */
	public Card description(String text) {
		this.description = text == null ? "" : text;
		redraw();
		return this;
	}

	/** Sets the accent of the hover and selection outline and the icon box. */
	public Card accent(RGB rgb) {
		this.accent = rgb;
		redraw();
		return this;
	}

	/** Limits the number of description lines (ellipsis after the last one). */
	public Card maxLines(int lines) {
		this.maxDescriptionLines = lines;
		return this;
	}

	/** Adds an action run when the card is activated. */
	public Card onActivate(Runnable action) {
		actions.add(action);
		return this;
	}

	/** Shows or clears the selected state. */
	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			redraw();
		}
	}

	/** The card title. */
	public String getTitle() {
		return title;
	}

	private void activate() {
		if (!isDisposed()) {
			setFocus();
			for (Runnable action : List.copyOf(actions)) {
				action.run();
			}
		}
	}

	private void keyDown(Event event) {
		if (event.character == ' ' || event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {
			activate();
		}
	}

	private void setHover(boolean hover) {
		if (this.hover != hover) {
			this.hover = hover;
			redraw();
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		int width = wHint == SWT.DEFAULT ? DEFAULT_WIDTH : wHint;
		GC gc = new GC(this);
		try {
			int textWidth = textWidth(width);
			gc.setFont(HlomTheme.boldFont());
			int titleHeight = wrap(gc, title, textWidth, 2).size() * Card.lineHeight(gc);
			gc.setFont(getFont());
			int lineHeight = Card.lineHeight(gc);
			int descriptionHeight = description.isEmpty() ? 0
					: 4 + wrap(gc, description, textWidth, maxDescriptionLines).size() * lineHeight;
			int height;
			if (orientation == VERTICAL) {
				height = PADDING + ICON_BOX + GAP + titleHeight + descriptionHeight + PADDING + 2;
			} else {
				height = PADDING + Math.max(ICON_BOX, titleHeight + descriptionHeight) + PADDING;
			}
			return new Point(width, hHint == SWT.DEFAULT ? height : hHint);
		} finally {
			gc.dispose();
		}
	}

	private int textWidth(int width) {
		return Math.max(40, orientation == VERTICAL ? width - 2 * PADDING : width - 2 * PADDING - ICON_BOX - GAP);
	}

	private void paint(GC gc) {
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
		Rectangle area = getClientArea();
		gc.setBackground(getParent().getBackground());
		gc.fillRectangle(area);

		boolean focus = isFocusControl();
		boolean emphasized = hover || focus;
		RGB fill = selected ? blend(accent, HlomTheme.SURFACE, 0.06) : HlomTheme.SURFACE;

		// Soft shadow, card body and outline.
		if (emphasized || selected) {
			gc.setBackground(HlomTheme.color(blend(HlomTheme.INK, getParent().getBackground().getRGB(), 0.07)));
			gc.fillRoundRectangle(1, 3, area.width - 2, area.height - 3, RADIUS, RADIUS);
		}
		gc.setBackground(HlomTheme.color(fill));
		gc.fillRoundRectangle(0, 0, area.width - 1, area.height - 2, RADIUS, RADIUS);
		if (selected) {
			gc.setForeground(HlomTheme.color(accent));
			gc.setLineWidth(2);
			gc.drawRoundRectangle(1, 1, area.width - 3, area.height - 4, RADIUS, RADIUS);
			gc.setLineWidth(1);
		} else {
			gc.setForeground(HlomTheme.color(emphasized ? blend(accent, HlomTheme.BORDER, 0.55) : HlomTheme.BORDER));
			gc.drawRoundRectangle(0, 0, area.width - 1, area.height - 3, RADIUS, RADIUS);
		}

		// Icon box.
		int iconX = PADDING;
		int iconY = PADDING;
		RGB boxColor = selected || emphasized ? blend(accent, HlomTheme.SURFACE, 0.10) : HlomTheme.BAR;
		gc.setBackground(HlomTheme.color(boxColor));
		gc.fillRoundRectangle(iconX, iconY, ICON_BOX, ICON_BOX, 10, 10);
		if (icon != null) {
			Rectangle bounds = icon.getBounds();
			gc.drawImage(icon, iconX + (ICON_BOX - bounds.width) / 2, iconY + (ICON_BOX - bounds.height) / 2);
		}

		// Selection check mark.
		if (selected) {
			int size = 18;
			int cx = area.width - PADDING - size + 6;
			int cy = PADDING - 6;
			gc.setBackground(HlomTheme.color(accent));
			gc.fillOval(cx, cy, size, size);
			gc.setForeground(HlomTheme.color(HlomTheme.SURFACE));
			gc.setLineWidth(2);
			gc.setLineCap(SWT.CAP_ROUND);
			gc.drawPolyline(new int[] { cx + 5, cy + 9, cx + 8, cy + 12, cx + 13, cy + 6 });
			gc.setLineWidth(1);
		}

		// Text.
		int textX = orientation == VERTICAL ? PADDING : PADDING + ICON_BOX + GAP;
		int textY = orientation == VERTICAL ? PADDING + ICON_BOX + GAP : PADDING;
		int textWidth = textWidth(area.width) - (selected && orientation == HORIZONTAL ? 14 : 0);
		gc.setFont(HlomTheme.boldFont());
		gc.setForeground(HlomTheme.color(HlomTheme.INK));
		int titleLineHeight = Card.lineHeight(gc);
		for (String line : wrap(gc, title, textWidth, 2)) {
			gc.drawString(line, textX, textY, true);
			textY += titleLineHeight;
		}
		if (!description.isEmpty()) {
			textY += 4;
			gc.setFont(getFont());
			gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
			int lineHeight = Card.lineHeight(gc);
			for (String line : wrap(gc, description, textWidth, maxDescriptionLines)) {
				gc.drawString(line, textX, textY, true);
				textY += lineHeight;
			}
		}

		// Keyboard focus ring (inside the outline, subtle).
		if (focus && !selected) {
			gc.setForeground(HlomTheme.color(blend(accent, HlomTheme.SURFACE, 0.35)));
			gc.drawRoundRectangle(2, 2, area.width - 5, area.height - 7, RADIUS - 2, RADIUS - 2);
		}
	}

	/** Word-wraps text to a width; the last allowed line ends with an ellipsis if text remains. */
	static List<String> wrap(GC gc, String text, int width, int maxLines) {
		List<String> lines = new ArrayList<>();
		if (text == null || text.isEmpty()) {
			return lines;
		}
		String[] words = text.split("\\s+");
		StringBuilder line = new StringBuilder();
		int i = 0;
		while (i < words.length) {
			String candidate = line.isEmpty() ? words[i] : line + " " + words[i];
			if (gc.textExtent(candidate).x <= width || line.isEmpty()) {
				line.setLength(0);
				line.append(candidate);
				i++;
			} else {
				lines.add(line.toString());
				line.setLength(0);
				if (lines.size() == maxLines) {
					break;
				}
			}
		}
		if (lines.size() < maxLines && !line.isEmpty()) {
			lines.add(line.toString());
		} else if (i < words.length || !line.isEmpty()) {
			String last = lines.getLast();
			while (!last.isEmpty() && gc.textExtent(last + "…").x > width) {
				last = last.substring(0, last.length() - 1);
			}
			lines.set(lines.size() - 1, last.stripTrailing() + "…");
		}
		return lines;
	}

	/** The line height of the GC's current font (measured, robust with monitor-specific scaling). */
	static int lineHeight(GC gc) {
		return gc.textExtent(Messages.Card_Ag).y;
	}

	/** Mixes {@code amount} of {@code color} into {@code base}. */
	static RGB blend(RGB color, RGB base, double amount) {
		return new RGB((int) Math.round(base.red + (color.red - base.red) * amount),
				(int) Math.round(base.green + (color.green - base.green) * amount),
				(int) Math.round(base.blue + (color.blue - base.blue) * amount));
	}
}
