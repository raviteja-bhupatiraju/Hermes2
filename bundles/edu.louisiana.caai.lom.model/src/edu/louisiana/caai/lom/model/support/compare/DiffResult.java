/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The outcome of {@link HlomDiff#compare} or {@link DocumentDiff#compare}: every compared field in document order, including unchanged ones
 * (for "show unchanged" views). Added or removed compound elements carry their fields as children.
 */
public final class DiffResult {

	private final List<Difference> all;

	DiffResult(List<Difference> all) {
		this.all = List.copyOf(all);
	}

	/** All compared fields, changed and unchanged. */
	public List<Difference> all() {
		return all;
	}

	/** The actual differences (no {@link ChangeKind#UNCHANGED} fields). */
	public List<Difference> differences() {
		return all.stream().filter(Difference::isChange).toList();
	}

	/** Whether the documents are equivalent. */
	public boolean isIdentical() {
		return all.stream().noneMatch(Difference::isChange);
	}

	/** The number of (top-level) fields of the given kind. */
	public int count(ChangeKind kind) {
		return (int) all.stream().filter(d -> d.kind() == kind).count();
	}

	/** The fields grouped by category, in category order; categories without fields are omitted. */
	public Map<DiffCategory, List<Difference>> byCategory(boolean includeUnchanged) {
		Map<DiffCategory, List<Difference>> found = new LinkedHashMap<>();
		for (Difference difference : all) {
			if (includeUnchanged || difference.isChange()) {
				found.computeIfAbsent(difference.category(), _ -> new ArrayList<>()).add(difference);
			}
		}
		// Healthcare LOM categories in their order; parts of other documents in document order (stable sort).
		Map<DiffCategory, List<Difference>> map = new LinkedHashMap<>();
		found.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().order()))
				.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
		return map;
	}

	/** The field with the given label (searching nested fields too). */
	public Optional<Difference> find(String label) {
		return find(all, label);
	}

	private static Optional<Difference> find(List<Difference> differences, String label) {
		for (Difference difference : differences) {
			if (difference.label().equals(label)) {
				return Optional.of(difference);
			}
			Optional<Difference> nested = find(difference.children(), label);
			if (nested.isPresent()) {
				return nested;
			}
		}
		return Optional.empty();
	}

	/** A plain text report of the differences, one line per field, grouped by category. */
	public String toText(String leftName, String rightName) {
		StringBuilder text = new StringBuilder();
		text.append("Comparison of ").append(leftName).append(" (left) and ").append(rightName).append(" (right)\n");
		text.append(count(ChangeKind.CHANGED)).append(" changed, ").append(count(ChangeKind.ADDED)).append(" added, ")
				.append(count(ChangeKind.REMOVED)).append(" removed\n");
		byCategory(false).forEach((category, differences) -> {
			text.append('\n').append(category.label()).append('\n');
			for (Difference difference : differences) {
				append(text, difference, "  ");
			}
		});
		return text.toString();
	}

	private static void append(StringBuilder text, Difference difference, String indent) {
		String marker = switch (difference.kind()) {
		case ADDED -> "+ ";
		case REMOVED -> "- ";
		case CHANGED -> "~ ";
		case UNCHANGED -> "  ";
		};
		text.append(indent).append(marker).append(difference.field()).append(": ");
		switch (difference.kind()) {
		case ADDED -> text.append(difference.rightValue());
		case REMOVED -> text.append(difference.leftValue());
		default -> text.append(difference.leftValue()).append(" → ").append(difference.rightValue());
		}
		text.append('\n');
		for (Difference child : difference.children()) {
			append(text, child, indent + "    ");
		}
	}
}
