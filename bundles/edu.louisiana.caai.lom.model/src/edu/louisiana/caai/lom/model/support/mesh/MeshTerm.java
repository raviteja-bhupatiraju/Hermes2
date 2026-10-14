/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.util.Objects;

/**
 * A MeSH descriptor found by a search.
 *
 * @param id    the descriptor's unique identifier, e.g. {@code D009203}
 * @param label the descriptor's preferred label, e.g. {@code Myocardial Infarction}
 */
public record MeshTerm(String id, String label) {

	public MeshTerm {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(label, "label");
	}

	@Override
	public String toString() {
		return label + " [" + id + "]";
	}
}
