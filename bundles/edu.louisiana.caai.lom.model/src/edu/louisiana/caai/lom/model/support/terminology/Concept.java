/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.terminology;

import java.util.List;
import java.util.Objects;

/**
 * A concept of an offline {@link Terminology}.
 *
 * @param code        the code, e.g. {@code D009203} (MeSH), {@code A41.9} (ICD-10-CM), {@code IM013} (AAMC)
 * @param label       the preferred name
 * @param synonyms    entry terms, inclusion terms and other synonyms
 * @param parents     codes of the broader concepts
 * @param treeNumbers positions in the hierarchy (MeSH tree numbers; the chapter for ICD-10-CM)
 * @param definition  the definition or scope note, or {@code ""}
 * @param related     codes of related concepts (MeSH "see also")
 * @param kind        what the concept is, e.g. {@code descriptor}, {@code billable code}, {@code competency}
 */
public record Concept(String code, String label, List<String> synonyms, List<String> parents,
		List<String> treeNumbers, String definition, List<String> related, String kind) {

	public Concept {
		Objects.requireNonNull(code, "code");
		Objects.requireNonNull(label, "label");
		synonyms = List.copyOf(synonyms);
		parents = List.copyOf(parents);
		treeNumbers = List.copyOf(treeNumbers);
		definition = Objects.requireNonNullElse(definition, "");
		related = List.copyOf(related);
		kind = Objects.requireNonNullElse(kind, "");
	}

	@Override
	public String toString() {
		return label + " [" + code + "]";
	}
}
