/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.compare.ChangeKind;
import edu.louisiana.caai.lom.model.support.compare.DiffCategory;
import edu.louisiana.caai.lom.model.support.compare.DiffResult;
import edu.louisiana.caai.lom.model.support.compare.Difference;

/**
 * The tree of the compare view: categories with their fields, added or removed compound elements with their
 * fields as children; content and label providers for the columns Field, Left and Right.
 */
final class CompareTree {

	private static final int MAX_VALUE = 300;
	private static final String ABSENT = "—";

	/** A category with its (filtered) rows. */
	record CategoryNode(DiffCategory category, List<Row> rows, int changes) {
	}

	/** A compared field; {@code name} is its label relative to its parent row or category. */
	record Row(Difference difference, String name, List<Row> children) {
	}

	private CompareTree() {
	}

	/** Builds the category nodes of a result, keeping unchanged fields if asked and fields matching the filter. */
	static List<CategoryNode> nodes(DiffResult result, boolean showUnchanged, String filter) {
		List<CategoryNode> nodes = new ArrayList<>();
		if (result == null) {
			return nodes;
		}
		String needle = filter == null ? "" : filter.strip().toLowerCase(Locale.ROOT);
		for (var entry : result.byCategory(showUnchanged).entrySet()) {
			List<Row> rows = new ArrayList<>();
			int changes = 0;
			boolean categoryMatches = !needle.isEmpty() && entry.getKey().label().toLowerCase(Locale.ROOT).contains(needle);
			for (Difference difference : entry.getValue()) {
				Row row = row(difference, null, needle, categoryMatches);
				if (row != null) {
					rows.add(row);
					if (difference.isChange()) {
						changes++;
					}
				}
			}
			if (!rows.isEmpty()) {
				nodes.add(new CategoryNode(entry.getKey(), rows, changes));
			}
		}
		return nodes;
	}

	private static Row row(Difference difference, Difference parent, String needle, boolean parentMatches) {
		boolean matches = parentMatches || needle.isEmpty() || matches(difference, needle);
		List<Row> children = new ArrayList<>();
		for (Difference child : difference.children()) {
			Row row = row(child, difference, needle, matches);
			if (row != null) {
				children.add(row);
			}
		}
		if (!matches && children.isEmpty()) {
			return null;
		}
		return new Row(difference, name(difference, parent), children);
	}

	private static boolean matches(Difference difference, String needle) {
		return contains(difference.label(), needle) || contains(difference.leftValue(), needle)
				|| contains(difference.rightValue(), needle) || contains(difference.path(), needle);
	}

	private static boolean contains(String text, String needle) {
		return text != null && text.toLowerCase(Locale.ROOT).contains(needle);
	}

	/** The label of a field relative to its parent, e.g. {@code Number of credits} under {@code Credits 2}. */
	private static String name(Difference difference, Difference parent) {
		if (parent != null && difference.label().startsWith(parent.label())) {
			String rest = difference.label().substring(parent.label().length()).strip();
			if (rest.startsWith("›")) {
				rest = rest.substring(1).strip();
			}
			if (!rest.isEmpty()) {
				return rest;
			}
		}
		return difference.field();
	}

	/** Content of the tree: a list of {@link CategoryNode}s. */
	static final class ContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object input) {
			return input instanceof List<?> list ? list.toArray() : new Object[0];
		}

		@Override
		public Object[] getChildren(Object element) {
			return switch (element) {
				case CategoryNode node -> node.rows().toArray();
				case Row row -> row.children().toArray();
				case null, default -> new Object[0];
			};
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}

	/** Which column a label provider renders. */
	enum Column {
		FIELD, LEFT, RIGHT
	}

	/** Styled labels with change-kind colors: added teal, removed vermilion struck through, changed amber. */
	static final class LabelProvider extends StyledCellLabelProvider {

		private static final RGB ADDED_TINT = new RGB(0xE3, 0xF2, 0xF1);
		private static final RGB REMOVED_TINT = new RGB(0xFB, 0xE8, 0xE9);
		private static final RGB CHANGED_TINT = new RGB(0xFD, 0xF1, 0xE1);

		private final Column column;

		LabelProvider(Column column) {
			this.column = column;
		}

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			StyledString text = new StyledString();
			cell.setImage(null);
			cell.setBackground(null);
			if (element instanceof CategoryNode node) {
				if (column == Column.FIELD) {
					cell.setImage(HlomTheme.image("icons/pages/" + node.category().icon() + ".png"));
					text.append(node.category().label(), styler(node.category().isHealthcare() ? HlomTheme.TEAL : HlomTheme.INK,
							false, HlomTheme.boldFont()));
					String count = node.changes() == 0 ? "  no changes"
							: "  " + node.changes() + (node.changes() == 1 ? " change" : " changes");
					text.append(count, styler(HlomTheme.MUTED, false, null));
				}
				cell.setBackground(HlomTheme.color(HlomTheme.BAR));
			} else if (element instanceof Row row) {
				Difference difference = row.difference();
				ChangeKind kind = difference.kind();
				switch (column) {
				case FIELD -> {
					cell.setImage(HlomTheme.image("icons/compare/" + icon(kind) + ".png"));
					text.append(row.name(), styler(kind == ChangeKind.UNCHANGED ? HlomTheme.SLATE : HlomTheme.INK,
							false, null));
				}
				case LEFT -> {
					String value = difference.leftValue();
					if (value == null) {
						text.append(kind == ChangeKind.UNCHANGED ? "" : ABSENT, styler(HlomTheme.MUTED, false, null));
					} else {
						switch (kind) {
						case REMOVED -> text.append(shorten(value), styler(HlomTheme.ACCENT, true, null));
						case CHANGED -> text.append(shorten(value), styler(HlomTheme.SLATE, true, null));
						default -> text.append(shorten(value), styler(HlomTheme.MUTED, false, null));
						}
					}
					if (kind == ChangeKind.REMOVED) {
						cell.setBackground(HlomTheme.color(REMOVED_TINT));
					} else if (kind == ChangeKind.CHANGED) {
						cell.setBackground(HlomTheme.color(CHANGED_TINT));
					}
				}
				case RIGHT -> {
					String value = difference.rightValue();
					if (value == null) {
						text.append(kind == ChangeKind.UNCHANGED ? "" : ABSENT, styler(HlomTheme.MUTED, false, null));
					} else {
						switch (kind) {
						case ADDED -> text.append(shorten(value), styler(HlomTheme.TEAL, false, null));
						case CHANGED -> text.append(shorten(value), styler(HlomTheme.AMBER, false, HlomTheme.boldFont()));
						default -> text.append(shorten(value), styler(HlomTheme.MUTED, false, null));
						}
					}
					if (kind == ChangeKind.ADDED) {
						cell.setBackground(HlomTheme.color(ADDED_TINT));
					} else if (kind == ChangeKind.CHANGED) {
						cell.setBackground(HlomTheme.color(CHANGED_TINT));
					}
				}
				}
			}
			cell.setText(text.getString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}

		@Override
		public String getToolTipText(Object element) {
			if (element instanceof Row(Difference difference, _, _)) {
				return switch (column) {
				case FIELD -> difference.label() + "\n" + difference.path() + "\n" + difference.kind().label();
				case LEFT -> difference.leftValue();
				case RIGHT -> difference.rightValue();
				};
			}
			return null;
		}

		static String icon(ChangeKind kind) {
			return switch (kind) {
			case ADDED -> "added";
			case REMOVED -> "removed";
			case CHANGED -> "changed";
			case UNCHANGED -> "same";
			};
		}

		private static String shorten(String value) {
			String line = value.replace('\n', ' ').replace('\r', ' ');
			return line.length() > MAX_VALUE ? line.substring(0, MAX_VALUE - 1) + "…" : line;
		}

		private static Styler styler(RGB rgb, boolean strikeout, Font font) {
			Color color = HlomTheme.color(rgb);
			return new Styler() {
				@Override
				public void applyStyles(TextStyle style) {
					style.foreground = color;
					style.strikeout = strikeout;
					if (strikeout) {
						style.strikeoutColor = color;
					}
					if (font != null) {
						style.font = font;
					}
				}
			};
		}
	}
}
