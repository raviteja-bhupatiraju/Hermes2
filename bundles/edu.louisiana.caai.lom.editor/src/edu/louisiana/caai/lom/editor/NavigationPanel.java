/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * The editor's page navigation: a sidebar listing the pages in groups (Overview, Description, Healthcare,
 * Record, Source) with icons and problem badges. Replaces the row of tabs of a plain multi-page editor.
 * <p>
 * Like a list, it takes the keyboard focus (Tab): the arrow keys, Home and End move the focus between the pages
 * and Enter or Space opens the focused page (Ctrl+Page Up/Down switch pages from anywhere in the editor). Screen
 * readers see a list of the pages with their problem counts.
 */
final class NavigationPanel extends Canvas {

	/** A navigation entry. */
	static final class Item {
		final int pageIndex;
		final String label;
		final String group;
		final Image icon;
		final boolean healthcare;
		final String tooltip;
		int errors;
		int warnings;
		Rectangle bounds;

		Item(int pageIndex, String label, String group, Image icon, boolean healthcare, String tooltip) {
			this.pageIndex = pageIndex;
			this.label = label;
			this.group = group;
			this.icon = icon;
			this.healthcare = healthcare;
			this.tooltip = tooltip;
		}
	}

	private static final int WIDTH = 210;
	private static final int ITEM_HEIGHT = 30;
	private static final int GROUP_HEIGHT = 30;
	private static final int PADDING = 10;

	private final List<Item> items = new ArrayList<>();
	private final IntConsumer onSelect;
	/** Gives the focus to the page (after a click: the mouse doesn't take the focus away from the pages). */
	private final Runnable focusPage;
	private Item selected;
	private Item hovered;
	/** The entry with the keyboard focus (shown while the panel has the focus). */
	private Item focused;

	NavigationPanel(Composite parent, IntConsumer onSelect, Runnable focusPage) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.onSelect = onSelect;
		this.focusPage = focusPage;
		setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		addPaintListener(this::paint);
		addListener(SWT.MouseDown, this::mouseDown);
		addListener(SWT.MouseMove, this::mouseMove);
		addListener(SWT.MouseExit, _ -> setHovered(null));
		addListener(SWT.KeyDown, this::keyDown);
		addListener(SWT.Traverse, this::traverse);
		addListener(SWT.FocusIn, _ -> {
			if (focused == null) {
				focused = selected != null ? selected : items.isEmpty() ? null : items.getFirst();
			}
			redraw();
			announceFocus();
		});
		addListener(SWT.FocusOut, _ -> redraw());
		installAccessibility();
	}

	void add(Item item) {
		items.add(item);
		redraw();
	}

	/** Highlights the entry of a page (without notifying the listener). */
	void select(int pageIndex) {
		Item item = find(pageIndex);
		if (item != selected) {
			selected = item;
			focused = item;
			redraw();
			if (item != null) {
				getAccessible().selectionChanged();
			}
		}
	}

	/** Updates the problem badge of a page. */
	void setProblems(int pageIndex, int errors, int warnings) {
		Item item = find(pageIndex);
		if (item != null && (item.errors != errors || item.warnings != warnings)) {
			item.errors = errors;
			item.warnings = warnings;
			redraw();
		}
	}

	private Item find(int pageIndex) {
		for (Item item : items) {
			if (item.pageIndex == pageIndex) {
				return item;
			}
		}
		return null;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		int groups = (int) items.stream().map(i -> i.group).distinct().count();
		return new Point(WIDTH, PADDING * 2 + groups * GROUP_HEIGHT + items.size() * ITEM_HEIGHT);
	}

	private void paint(PaintEvent event) {
		GC gc = event.gc;
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
		Rectangle area = getClientArea();

		// Right-hand separator.
		gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
		gc.drawLine(area.width - 1, 0, area.width - 1, area.height);

		Font itemFont = getFont();
		Font groupFont = HlomTheme.groupFont();
		int y = PADDING;
		String group = null;
		for (Item item : items) {
			if (!item.group.equals(group)) {
				group = item.group;
				gc.setFont(groupFont);
				gc.setForeground(HlomTheme.color(HlomTheme.MUTED));
				gc.drawString(group.toUpperCase(), PADDING + 6, y + GROUP_HEIGHT - gc.getFontMetrics().getHeight() - 4,
						true);
				y += GROUP_HEIGHT;
			}
			item.bounds = new Rectangle(PADDING - 4, y, area.width - 2 * PADDING + 4, ITEM_HEIGHT - 2);
			paintItem(gc, item, itemFont);
			if (item == focused && isFocusControl()) {
				gc.setForeground(HlomTheme.color(HlomTheme.INK));
				gc.drawFocus(item.bounds.x, item.bounds.y, item.bounds.width, item.bounds.height);
			}
			y += ITEM_HEIGHT;
		}
	}

	private void paintItem(GC gc, Item item, Font font) {
		Rectangle b = item.bounds;
		RGB accent = item.healthcare ? HlomTheme.TEAL : HlomTheme.ACCENT;
		if (item == selected) {
			gc.setBackground(HlomTheme.color(HlomTheme.SURFACE));
			gc.fillRoundRectangle(b.x, b.y, b.width, b.height, 8, 8);
			gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			gc.drawRoundRectangle(b.x, b.y, b.width - 1, b.height - 1, 8, 8);
			gc.setBackground(HlomTheme.color(accent));
			gc.fillRoundRectangle(b.x + 1, b.y + 6, 3, b.height - 12, 3, 3);
		} else if (item == hovered) {
			gc.setBackground(HlomTheme.color(HlomTheme.BAR));
			gc.fillRoundRectangle(b.x, b.y, b.width, b.height, 8, 8);
		}
		int x = b.x + 12;
		if (item.icon != null) {
			Rectangle iconBounds = item.icon.getBounds();
			gc.drawImage(item.icon, x, b.y + (b.height - iconBounds.height) / 2);
			x += iconBounds.width + 8;
		}
		gc.setFont(item == selected ? HlomTheme.boldFont() : font);
		gc.setForeground(HlomTheme.color(item == selected ? HlomTheme.INK : HlomTheme.SLATE));
		int textHeight = gc.getFontMetrics().getHeight();
		gc.drawString(item.label, x, b.y + (b.height - textHeight) / 2, true);

		// Problem badge.
		int count = item.errors > 0 ? item.errors : item.warnings;
		if (count > 0) {
			RGB badge = item.errors > 0 ? HlomTheme.ACCENT : HlomTheme.AMBER;
			String text = count > 99 ? "99+" : Integer.toString(count);
			gc.setFont(HlomTheme.badgeFont());
			Point extent = gc.textExtent(text);
			int width = Math.max(extent.x + 10, 18);
			int height = extent.y + 2;
			int bx = b.x + b.width - width - 8;
			int by = b.y + (b.height - height) / 2;
			gc.setBackground(HlomTheme.color(badge));
			gc.fillRoundRectangle(bx, by, width, height, height, height);
			gc.setForeground(HlomTheme.color(HlomTheme.SURFACE));
			gc.drawString(text, bx + (width - extent.x) / 2, by + 1, true);
		}
	}

	private Item itemAt(int x, int y) {
		for (Item item : items) {
			if (item.bounds != null && item.bounds.contains(x, y)) {
				return item;
			}
		}
		return null;
	}

	private void mouseDown(Event event) {
		Item item = itemAt(event.x, event.y);
		if (item != null && event.button == 1) {
			open(item);
			getDisplay().asyncExec(() -> {
				if (!isDisposed() && isFocusControl()) {
					focusPage.run();
				}
			});
		}
	}

	/** Opens the page of an entry, as a click does. */
	private void open(Item item) {
		select(item.pageIndex);
		onSelect.accept(item.pageIndex);
	}

	/** Arrow keys, Home and End move the focus; Enter and Space open the focused page. */
	private void keyDown(Event event) {
		if (items.isEmpty()) {
			return;
		}
		int index = focused == null ? -1 : items.indexOf(focused);
		int target = switch (event.keyCode) {
			case SWT.ARROW_UP, SWT.ARROW_LEFT -> Math.max(0, index - 1);
			case SWT.ARROW_DOWN, SWT.ARROW_RIGHT -> Math.min(items.size() - 1, index + 1);
			case SWT.HOME -> 0;
			case SWT.END -> items.size() - 1;
			default -> -1;
		};
		if (target >= 0) {
			event.doit = false;
			focused = items.get(target);
			redraw();
			announceFocus();
		} else if ((event.character == ' ' || event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR)
				&& focused != null) {
			event.doit = false;
			open(focused);
		}
	}

	/** Tab and Shift+Tab leave the panel; the arrow keys stay in it. */
	private void traverse(Event event) {
		switch (event.detail) {
			case SWT.TRAVERSE_TAB_NEXT, SWT.TRAVERSE_TAB_PREVIOUS, SWT.TRAVERSE_ESCAPE, SWT.TRAVERSE_MNEMONIC -> event.doit = true;
			case SWT.TRAVERSE_ARROW_NEXT, SWT.TRAVERSE_ARROW_PREVIOUS, SWT.TRAVERSE_RETURN -> event.doit = false;
			default -> {
				// keep the default
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Accessibility: a list whose children are the entries
	// ----------------------------------------------------------------------------------------------------

	/** The child id of an entry for the accessibility API (its index), or {@link ACC#CHILDID_SELF}. */
	private int childId(Item item) {
		return item == null ? ACC.CHILDID_SELF : items.indexOf(item);
	}

	private Item item(int childId) {
		return childId >= 0 && childId < items.size() ? items.get(childId) : null;
	}

	private void announceFocus() {
		if (focused != null && isFocusControl()) {
			getAccessible().setFocus(childId(focused));
		}
	}

	/** The name of an entry as read by screen readers: its label and problem counts. */
	private static String accessibleName(Item item) {
		StringBuilder name = new StringBuilder(item.label);
		if (item.errors > 0) {
			name.append(", ").append(item.errors).append(item.errors == 1 ? " error" : " errors");
		}
		if (item.warnings > 0) {
			name.append(", ").append(item.warnings).append(item.warnings == 1 ? " warning" : " warnings");
		}
		return name.toString();
	}

	private void installAccessibility() {
		Accessible accessible = getAccessible();
		accessible.addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent event) {
				Item item = item(event.childID);
				event.result = event.childID == ACC.CHILDID_SELF ? "Pages" : item == null ? null : accessibleName(item);
			}

			@Override
			public void getDescription(AccessibleEvent event) {
				Item item = item(event.childID);
				event.result = item == null ? null : item.tooltip;
			}

			@Override
			public void getKeyboardShortcut(AccessibleEvent event) {
				event.result = event.childID == ACC.CHILDID_SELF ? "Ctrl+Page Down" : null;
			}
		});
		accessible.addAccessibleControlListener(new AccessibleControlAdapter() {
			@Override
			public void getRole(AccessibleControlEvent event) {
				event.detail = event.childID == ACC.CHILDID_SELF ? ACC.ROLE_LIST : ACC.ROLE_LISTITEM;
			}

			@Override
			public void getChildCount(AccessibleControlEvent event) {
				event.detail = event.childID == ACC.CHILDID_SELF ? items.size() : 0;
			}

			@Override
			public void getChildren(AccessibleControlEvent event) {
				Object[] children = new Object[items.size()];
				for (int i = 0; i < children.length; i++) {
					children[i] = i;
				}
				event.children = children;
			}

			@Override
			public void getChildAtPoint(AccessibleControlEvent event) {
				Point point = toControl(event.x, event.y);
				Item item = itemAt(point.x, point.y);
				event.childID = item != null ? childId(item)
						: getClientArea().contains(point) ? ACC.CHILDID_SELF : ACC.CHILDID_NONE;
			}

			@Override
			public void getLocation(AccessibleControlEvent event) {
				Item item = item(event.childID);
				Rectangle bounds = item != null && item.bounds != null ? item.bounds : getClientArea();
				Point location = toDisplay(bounds.x, bounds.y);
				event.x = location.x;
				event.y = location.y;
				event.width = bounds.width;
				event.height = bounds.height;
			}

			@Override
			public void getState(AccessibleControlEvent event) {
				Item item = item(event.childID);
				if (item == null) {
					event.detail = ACC.STATE_FOCUSABLE | (isFocusControl() ? ACC.STATE_FOCUSED : ACC.STATE_NORMAL);
					return;
				}
				int state = ACC.STATE_SELECTABLE | ACC.STATE_FOCUSABLE;
				if (item == selected) {
					state |= ACC.STATE_SELECTED;
				}
				if (item == focused && isFocusControl()) {
					state |= ACC.STATE_FOCUSED;
				}
				event.detail = state;
			}

			@Override
			public void getFocus(AccessibleControlEvent event) {
				event.childID = !isFocusControl() ? ACC.CHILDID_NONE : focused != null ? childId(focused) : ACC.CHILDID_SELF;
			}

			@Override
			public void getSelection(AccessibleControlEvent event) {
				event.childID = selected != null ? childId(selected) : ACC.CHILDID_NONE;
			}

			@Override
			public void getDefaultAction(AccessibleControlEvent event) {
				event.result = event.childID == ACC.CHILDID_SELF ? null : "Open";
			}

			@Override
			public void getValue(AccessibleControlEvent event) {
				event.result = selected == null ? null : selected.label;
			}
		});
	}

	private void mouseMove(Event event) {
		setHovered(itemAt(event.x, event.y));
	}

	private void setHovered(Item item) {
		if (item != hovered) {
			hovered = item;
			setCursor(item == null ? null : getDisplay().getSystemCursor(SWT.CURSOR_HAND));
			setToolTipText(item == null ? null : tooltip(item));
			redraw();
		}
	}

	private static String tooltip(Item item) {
		StringBuilder text = new StringBuilder(item.tooltip != null ? item.tooltip : item.label);
		if (item.errors > 0 || item.warnings > 0) {
			text.append("\n").append(item.errors).append(Messages.NavigationPanel_Errors).append(item.warnings).append(" warnings");
		}
		return text.toString();
	}
}
