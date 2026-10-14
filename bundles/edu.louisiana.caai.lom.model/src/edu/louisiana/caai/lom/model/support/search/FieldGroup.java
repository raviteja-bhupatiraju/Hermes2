/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.search;

import java.util.List;
import java.util.Set;

/**
 * A group of fields a search can be limited to, such as titles or keywords: the values whose element path
 * contains one of the given element names, optionally below one of the given ancestors.
 *
 * @param id        a stable identifier (for dialog settings)
 * @param label     the name shown to users
 * @param elements  element names; a value belongs to the group if its path contains one of them
 * @param ancestors element names one of which must also be on the path; empty for any
 */
public record FieldGroup(String id, String label, Set<String> elements, Set<String> ancestors) {

	public FieldGroup {
		elements = Set.copyOf(elements);
		ancestors = Set.copyOf(ancestors);
	}

	/** A group of values in or below elements of the given names. */
	public static FieldGroup of(String id, String label, String... elements) {
		return new FieldGroup(id, label, Set.of(elements), Set.of());
	}

	/** Whether a value at a path belongs to the group. */
	public boolean matches(List<String> names, String attribute) {
		return names.stream().anyMatch(elements::contains)
				&& (ancestors.isEmpty() || names.stream().anyMatch(ancestors::contains));
	}
}
