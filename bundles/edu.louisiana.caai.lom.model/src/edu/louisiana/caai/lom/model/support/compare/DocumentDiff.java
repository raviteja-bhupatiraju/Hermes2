/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.support.search.DocumentSearch;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Value;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;

/**
 * Compares two documents of any MedBiquitous standard field by field: every text value (element content and
 * attribute value) is matched by its element path, so a value is changed, added, removed or unchanged. Fields are
 * grouped by the part of the root element they belong to. (Healthcare LOM documents are compared with the
 * semantic {@link HlomDiff}, which also matches reordered elements.)
 */
public final class DocumentDiff {

	private DocumentDiff() {
	}

	/** Compares the documents of two resources (either may be empty). */
	public static DiffResult compare(Resource left, Resource right) {
		Map<String, Value> leftValues = values(left);
		Map<String, Value> rightValues = values(right);
		List<String> paths = new ArrayList<>(leftValues.keySet());
		for (String path : rightValues.keySet()) {
			if (!leftValues.containsKey(path)) {
				paths.add(path);
			}
		}
		List<Difference> out = new ArrayList<>();
		for (String path : paths) {
			Value before = leftValues.get(path);
			Value after = rightValues.get(path);
			Value any = before != null ? before : after;
			ChangeKind kind = before == null ? ChangeKind.ADDED : after == null ? ChangeKind.REMOVED
					: before.text().equals(after.text()) ? ChangeKind.UNCHANGED : ChangeKind.CHANGED;
			List<String> names = any.names();
			DiffCategory category = names.size() > 1 ? DiffCategory.part(names.get(1)) : DiffCategory.OTHER;
			String label = FieldLabels.label(names, any.attribute());
			String field = names.size() > 2 ? FieldLabels.label(names.subList(1, names.size()), any.attribute())
					: label;
			out.add(new Difference(category, path, label + index(path), field + index(path),
					before == null ? null : before.text(), after == null ? null : after.text(), kind,
					before == null ? null : before.element(), after == null ? null : after.element(), List.of()));
		}
		return new DiffResult(out);
	}

	/** The positions of repeated elements on a path, such as {@code (2)} for {@code /a/b[2]/c}. */
	private static String index(String path) {
		List<String> positions = new ArrayList<>();
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\[(\\d+)]").matcher(path);
		while (matcher.find()) {
			positions.add(matcher.group(1));
		}
		return positions.isEmpty() ? "" : " (" + String.join(", ", positions) + ")";
	}

	private static Map<String, Value> values(Resource resource) {
		Map<String, Value> values = new LinkedHashMap<>();
		if (resource != null) {
			for (EObject root : resource.getContents()) {
				DocumentSearch.values(root, value -> values.putIfAbsent(value.path(), value));
			}
		}
		return values;
	}
}
