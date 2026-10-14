/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import org.eclipse.osgi.util.NLS;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableItem;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Kind;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Owner-drawn cells of the library table: two-line title cells, kind pills, status icons and completeness bars,
 * with theme-colored selection and hover backgrounds and a hairline row separator.
 */
final class LibraryCells {

	static final RGB SELECTION = new RGB(0xDC, 0xEE, 0xED);
	static final RGB HOVER = new RGB(0xF3, 0xF6, 0xF9);
	static final RGB ACCENT_TINT = new RGB(0xFD, 0xEC, 0xEC);
	static final RGB TEAL_TINT = new RGB(0xE3, 0xF2, 0xF1);

	private static final int PAD = 8;
	private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
	private static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.US);

	private LibraryCells() {
	}

	/** Base class: theme backgrounds, separators and row height. */
	abstract static class Cell extends OwnerDrawLabelProvider {

		/** The table font. */
		protected Font base;

		@Override
		protected void measure(Event event, Object element) {
			GC gc = event.gc;
			gc.setFont(HlomTheme.boldFont());
			int title = gc.getFontMetrics().getHeight();
			gc.setFont(((TableItem) event.item).getParent().getFont());
			event.height = Math.max(event.height, title + gc.getFontMetrics().getHeight() + 12);
		}

		@Override
		protected void erase(Event event, Object element) {
			Rectangle bounds = ((TableItem) event.item).getBounds(event.index);
			boolean selected = (event.detail & SWT.SELECTED) != 0;
			boolean hot = (event.detail & SWT.HOT) != 0;
			GC gc = event.gc;
			gc.setBackground(HlomTheme.color(selected ? SELECTION : hot ? HOVER : HlomTheme.SURFACE));
			gc.fillRectangle(bounds.x, bounds.y, bounds.width, bounds.height);
			gc.setBackground(HlomTheme.color(HlomTheme.BORDER));
			gc.fillRectangle(bounds.x, bounds.y + bounds.height - 1, bounds.width, 1);
			if (selected && event.index == 0) {
				gc.setBackground(HlomTheme.color(HlomTheme.TEAL));
				gc.fillRectangle(bounds.x, bounds.y, 3, bounds.height - 1);
			}
			event.detail &= ~(SWT.SELECTED | SWT.HOT | SWT.FOREGROUND | SWT.BACKGROUND);
		}

		@Override
		protected void paint(Event event, Object element) {
			Rectangle bounds = ((TableItem) event.item).getBounds(event.index);
			GC gc = event.gc;
			base = ((TableItem) event.item).getParent().getFont();
			gc.setAdvanced(true);
			gc.setAntialias(SWT.ON);
			gc.setTextAntialias(SWT.ON);
			paint(gc, (LibraryEntry) element, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height - 1));
		}

		abstract void paint(GC gc, LibraryEntry entry, Rectangle bounds);

		@Override
		public String getToolTipText(Object element) {
			return null;
		}
	}

	/** A single line of text, vertically centered, optionally right aligned and colored. */
	static Cell text(Function<LibraryEntry, String> text, Function<LibraryEntry, RGB> color, boolean right) {
		return new Cell() {
			@Override
			void paint(GC gc, LibraryEntry entry, Rectangle b) {
				String value = text.apply(entry);
				if (value == null || value.isEmpty()) {
					return;
				}
				gc.setFont(base);
				gc.setForeground(HlomTheme.color(color.apply(entry)));
				String shown = shorten(gc, value, b.width - 2 * PAD);
				Point extent = gc.textExtent(shown);
				int x = right ? b.x + b.width - PAD - extent.x : b.x + PAD;
				gc.drawText(shown, x, b.y + (b.height - extent.y) / 2, true);
			}
		};
	}

	/** Bold title with the description excerpt (or file name) in a second, muted line. */
	static Cell title() {
		return new Cell() {
			@Override
			void paint(GC gc, LibraryEntry entry, Rectangle b) {
				gc.setFont(HlomTheme.boldFont());
				int titleHeight = gc.getFontMetrics().getHeight();
				gc.setFont(base);
				int lineHeight = gc.getFontMetrics().getHeight();
				int y = b.y + (b.height - titleHeight - lineHeight - 1) / 2;
				gc.setFont(HlomTheme.boldFont());
				gc.setForeground(HlomTheme.color(entry.summary().title() == null ? HlomTheme.SLATE : HlomTheme.INK));
				gc.drawText(shorten(gc, entry.title(), b.width - 2 * PAD), b.x + PAD, y, true);
				gc.setFont(base);
				gc.setForeground(HlomTheme.color(HlomTheme.MUTED));
				String second = entry.summary().excerpt(240);
				if (second.isEmpty()) {
					second = entry.summary().title() == null ? "No title" : "No description";
				}
				gc.drawText(shorten(gc, second, b.width - 2 * PAD), b.x + PAD, y + titleHeight + 1, true);
			}
		};
	}

	/** The kind as a colored pill. */
	/** Whether a document is of a standard other than Healthcare LOM. */
	static boolean isOtherStandard(LibraryEntry entry) {
		return entry.type() != null && entry.type() != HlomDocumentType.INSTANCE;
	}

	/** The kind of an HLOM record, or the standard of another document. */
	static String kindLabel(LibraryEntry entry) {
		if (isOtherStandard(entry)) {
			return entry.type().standard();
		}
		return switch (entry.summary().kind()) {
			case EDUCATIONAL_OFFERING -> Messages.LibraryCells_Offering;
			case IMAGE_ASSET -> Messages.LibraryCells_ImageAsset;
			case LEARNING_OBJECT -> Messages.LibraryCells_LearningObject;
		};
	}

	static Cell kind() {
		return new Cell() {
			@Override
			void paint(GC gc, LibraryEntry entry, Rectangle b) {
				Kind kind = entry.summary().kind();
				boolean other = isOtherStandard(entry);
				String label = kindLabel(entry);
				RGB foreground = other ? HlomTheme.INK : foreground(kind);
				RGB background = other ? HlomTheme.BAR : switch (kind) {
					case EDUCATIONAL_OFFERING -> ACCENT_TINT;
					case IMAGE_ASSET -> TEAL_TINT;
					case LEARNING_OBJECT -> HlomTheme.BAR;
				};
				gc.setFont(HlomTheme.badgeFont());
				Point extent = gc.textExtent(label);
				int height = extent.y + 6;
				int width = Math.min(extent.x + 16, b.width - 2 * PAD);
				int y = b.y + (b.height - height) / 2;
				gc.setBackground(HlomTheme.color(background));
				gc.fillRoundRectangle(b.x + PAD, y, width, height, height, height);
				gc.setForeground(HlomTheme.color(foreground));
				gc.drawText(shorten(gc, label, width - 16), b.x + PAD + 8, y + 3, true);
			}
		};
	}

	/** The accent color of a kind. */
	static RGB foreground(Kind kind) {
		return switch (kind) {
			case EDUCATIONAL_OFFERING -> HlomTheme.ACCENT;
			case IMAGE_ASSET -> HlomTheme.TEAL;
			case LEARNING_OBJECT -> HlomTheme.SLATE;
		};
	}

	/** The validation status as an icon. */
	static Cell status() {
		return new Cell() {
			@Override
			void paint(GC gc, LibraryEntry entry, Rectangle b) {
				Image image = statusImage(entry);
				if (image != null) {
					Rectangle size = image.getBounds();
					gc.drawImage(image, b.x + (b.width - size.width) / 2, b.y + (b.height - size.height) / 2);
				}
			}
		};
	}

	static Image statusImage(LibraryEntry entry) {
		return HlomTheme.image(switch (entry.status()) {
			case ERRORS -> "icons/actions/error.png";
			case WARNINGS -> "icons/actions/warning.png";
			case VALID -> "icons/actions/ok.png";
		});
	}

	/** A small progress bar with the percentage. */
	static Cell completeness() {
		return new Cell() {
			@Override
			void paint(GC gc, LibraryEntry entry, Rectangle b) {
				if (!entry.hasCompleteness()) {
					return;
				}
				int value = entry.completeness();
				gc.setFont(base);
				String text = value + "%";
				Point extent = gc.textExtent("100%");
				int barWidth = Math.max(10, b.width - 3 * PAD - extent.x);
				int y = b.y + b.height / 2 - 3;
				gc.setBackground(HlomTheme.color(HlomTheme.BORDER));
				gc.fillRoundRectangle(b.x + PAD, y, barWidth, 6, 6, 6);
				RGB color = value >= 80 ? HlomTheme.TEAL : value >= 50 ? HlomTheme.AMBER : HlomTheme.ACCENT;
				gc.setBackground(HlomTheme.color(color));
				gc.fillRoundRectangle(b.x + PAD, y, Math.max(6, barWidth * value / 100), 6, 6, 6);
				gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
				Point actual = gc.textExtent(text);
				gc.drawText(text, b.x + b.width - PAD - actual.x, b.y + (b.height - actual.y) / 2, true);
			}
		};
	}

	/** Relative modification time: {@code Today, 14:05}, {@code Yesterday, 09:12} or {@code 3 Oct 2026}. */
	static String modified(Instant instant) {
		if (instant == null) {
			return "";
		}
		ZonedDateTime time = instant.atZone(ZoneId.systemDefault());
		LocalDate today = LocalDate.now();
		if (time.toLocalDate().equals(today)) {
			return NLS.bind(Messages.LibraryCells_Today, TIME.format(time));
		} else if (time.toLocalDate().equals(today.minusDays(1))) {
			return NLS.bind(Messages.LibraryCells_Yesterday, TIME.format(time));
		}
		return DAY.format(time);
	}

	/** Shortens text with an ellipsis to fit a width. */
	static String shorten(GC gc, String text, int width) {
		if (text == null || width <= 0) {
			return "";
		}
		if (gc.textExtent(text).x <= width) {
			return text;
		}
		int low = 0;
		int high = text.length();
		while (low < high) {
			int mid = (low + high + 1) / 2;
			if (gc.textExtent(text.substring(0, mid) + "…").x <= width) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return text.substring(0, low).stripTrailing() + "…";
	}
}
