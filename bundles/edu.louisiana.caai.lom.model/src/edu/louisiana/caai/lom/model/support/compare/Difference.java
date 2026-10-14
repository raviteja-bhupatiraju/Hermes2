/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * One compared field of two HLOM documents.
 *
 * @param category   the LOM category or healthcare part the field belongs to
 * @param path       an XML-like path, e.g. {@code /lom/lifeCycle/contribute[2]/role}
 * @param label      the full human label, e.g. {@code Life Cycle › Contributor 2 › Role}
 * @param field      the label without the leading category, e.g. {@code Contributor 2 › Role}
 * @param leftValue  the value in the left document (display form), {@code null} if absent
 * @param rightValue the value in the right document (display form), {@code null} if absent
 * @param kind       how the field changed
 * @param left       the element of the left document holding the field, {@code null} if absent
 * @param right      the element of the right document holding the field, {@code null} if absent
 * @param children   for added or removed compound elements (a credit, a contributor, ...), their fields
 */
public record Difference(DiffCategory category, String path, String label, String field, String leftValue,
		String rightValue, ChangeKind kind, EObject left, EObject right, List<Difference> children) {

	/** Separator of label segments. */
	public static final String SEPARATOR = " › ";

	public Difference {
		children = children == null ? List.of() : List.copyOf(children);
	}

	/** Whether this is an actual difference. */
	public boolean isChange() {
		return kind.isChange();
	}

	/** {@code KIND label: left → right}, for logs and assertion messages. */
	@Override
	public String toString() {
		return kind + " " + label + ": " + (leftValue == null ? "—" : leftValue) + " → "
				+ (rightValue == null ? "—" : rightValue);
	}
}
