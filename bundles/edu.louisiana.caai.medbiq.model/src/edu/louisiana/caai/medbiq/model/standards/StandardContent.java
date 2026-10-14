/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/**
 * What a MedBiquitous standard contributes beyond its schemas, for each of its document types: how documents are
 * titled and described, the form pages of the editor, the best-practice rules, the search field groups and the
 * help texts of the elements. One implementation per standard (see
 * {@link edu.louisiana.caai.medbiq.model.MedbiqStandards}).
 * <p>
 * Element names are XML local names (such as {@code CurriculumInventory} or {@code SequenceBlock}); the model is
 * dynamic, so documents are read with {@link edu.louisiana.caai.medbiq.model.Paths}.
 */
public interface StandardContent {

	/** The title of a document from its root element, or {@code null}. */
	String title(MedbiqDocumentType type, EObject rootElement);

	/** A short description of a document from its root element, or {@code null}. */
	default String description(MedbiqDocumentType type, EObject rootElement) {
		return null;
	}

	/** The form pages of a type (the editor adds an overview, the tree and the XML page); empty to derive them. */
	default List<PageSpec> pages(MedbiqDocumentType type) {
		return List.of();
	}

	/** The rules beyond the schemas, checked on a document (given its document root). */
	default List<HlomProblem> rules(MedbiqDocumentType type, EObject documentRoot) {
		return List.of();
	}

	/** The field groups a search can be limited to. */
	default List<FieldGroup> searchFields(MedbiqDocumentType type) {
		return List.of();
	}

	/** The references within documents of a type: elements identifying other elements of the document. */
	default List<References.Kind> references(MedbiqDocumentType type) {
		return List.of();
	}

	/** Where documents of a type name other documents by identifier (elements with a catalog and an entry). */
	default List<DocumentLinks.LinkPath> linkPaths(MedbiqDocumentType type) {
		return List.of();
	}

	/**
	 * Adds the relations of a document that aren't references by identifier (see {@link References}) to its graph,
	 * such as the broader and narrower competencies of a competency framework; nothing by default.
	 */
	default void graph(MedbiqDocumentType type, org.eclipse.emf.ecore.EObject rootElement,
			edu.louisiana.caai.medbiq.model.DocumentGraph.Builder graph) {
		// No other relations.
	}

	/** The help texts of elements and attributes by local name ({@code name}, {@code @attribute} or {@code parent/name}). */
	default Map<String, String> help(MedbiqDocumentType type) {
		return Map.of();
	}
}
