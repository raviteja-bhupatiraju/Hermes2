/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.type;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;

/**
 * A kind of MedBiquitous document: a standard in one version, such as Healthcare LOM 1.0 or Curriculum Inventory
 * (AAMC 2022, "v10"). Recognized by its root element; knows how to load, validate and describe its documents.
 * Registered in {@link DocumentTypes}.
 */
public interface DocumentType {

	/** A stable identifier, such as {@code hlom-v1} or {@code curriculum-inventory-v10}. */
	String id();

	/** The name of the standard, such as {@code Curriculum Inventory}. */
	String standard();

	/** The version, such as {@code 1.0}. */
	String version();

	/** The name with the version, as shown to users. */
	default String label() {
		return standard() + " " + version();
	}

	/** The namespace URI of the root element. */
	String namespace();

	/** The local name of the root element. */
	String rootElement();

	/** The web page of the standard. */
	String specificationUrl();

	/** The published location of the root schema, written to {@code xsi:schemaLocation}. */
	String schemaLocation();

	/** Whether documents of this type can be stored in the JSON form. */
	default boolean supportsJson() {
		return false;
	}

	/** A resource set that loads and saves documents of this type (any URI). */
	ResourceSet createResourceSet();

	/** Validates the XML text of a document against the bundled schemas (offline). */
	List<HlomProblem> validateSchema(byte[] xml, EObject documentRoot);

	/** Where documents of this type name other documents by identifier (see {@link DocumentLinks}); none by default. */
	default List<DocumentLinks.LinkPath> linkPaths() {
		return List.of();
	}

	/** The best-practice rules beyond the schemas (and the model constraints); empty by default. */
	default List<HlomProblem> checkRules(EObject documentRoot) {
		return List.of();
	}

	/** The title of a document, or {@code null}. */
	String title(EObject documentRoot);

	/** A short description of a document, or {@code null}. */
	default String description(EObject documentRoot) {
		return null;
	}

	/** The field groups a search can be limited to. */
	default List<FieldGroup> searchFields() {
		return List.of();
	}

	/** The help text of the element at a path (local names from the root), or {@code null}. */
	default String fieldHelp(List<String> names) {
		return null;
	}

	/** The document root of a new, minimal document with a title. */
	EObject createDocument(String title, String language);
}
