/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.util.List;

/**
 * Details of a MeSH descriptor.
 *
 * @param id          the descriptor's unique identifier, e.g. {@code D009203}
 * @param label       the preferred label
 * @param scopeNote   the scope note (definition) of the preferred concept, or {@code null} if there is none
 * @param treeNumbers the tree numbers locating the descriptor in the MeSH hierarchies, e.g. {@code C14.280.647.500}
 * @param entryTerms  synonyms and other entry terms (without the preferred label)
 * @param seeAlso     related descriptors ("see also" references)
 */
public record MeshDetails(String id, String label, String scopeNote, List<String> treeNumbers,
		List<String> entryTerms, List<MeshTerm> seeAlso) {

	public MeshDetails {
		treeNumbers = List.copyOf(treeNumbers);
		entryTerms = List.copyOf(entryTerms);
		seeAlso = List.copyOf(seeAlso);
	}

	/** This descriptor as a search result. */
	public MeshTerm term() {
		return new MeshTerm(id, label);
	}
}
