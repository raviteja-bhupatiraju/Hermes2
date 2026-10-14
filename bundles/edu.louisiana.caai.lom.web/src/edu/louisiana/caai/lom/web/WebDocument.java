/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.web;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.medbiq.model.Paths;

/**
 * The document of a browser session: loaded from an upload or created from a type, edited in place, checked and
 * written as XML or JSON, with the model and the checks of the desktop application.
 */
final class WebDocument {

	private final String name;
	private final Resource resource;
	private final DocumentType type;

	private WebDocument(String name, Resource resource, DocumentType type) {
		this.name = name;
		this.resource = resource;
		this.type = type;
	}

	/** Reads a document (XML or JSON); throws if it is not a document of a known type. */
	static WebDocument load(String name, String text) throws IOException {
		DocumentType type = DocumentTypes.detect(text);
		if (type == null) {
			throw new IOException(name + " is not a MedBiquitous document of a known type.");
		}
		Resource resource = DocumentCheck.load(text, URI.createURI(safeName(name)));
		if (resource.getContents().isEmpty()) {
			throw new IOException(name + " could not be read: " + resource.getErrors());
		}
		return new WebDocument(name, resource, type);
	}

	/** A new, minimal document of a type. */
	static WebDocument create(DocumentType type) {
		EObject documentRoot = type.createDocument("Untitled", "en");
		Resource resource = documentRoot.eResource();
		if (resource == null) {
			resource = type.createResourceSet().createResource(URI.createURI("untitled.xml"));
			resource.getContents().add(documentRoot);
		}
		String name = type.rootElement().toLowerCase() + ".xml";
		return new WebDocument(name, resource, type);
	}

	private static String safeName(String name) {
		String file = name.replaceAll("[^\\w.-]+", "_");
		return file.isEmpty() ? "document.xml" : file;
	}

	String name() {
		return name;
	}

	DocumentType type() {
		return type;
	}

	/** The document root (of the model). */
	EObject documentRoot() {
		return resource.getContents().getFirst();
	}

	/** The root element, such as {@code lom} or {@code CurriculumInventory}. */
	EObject rootElement() {
		EObject root = Paths.rootElement(documentRoot());
		return root != null ? root : documentRoot();
	}

	/** The document as XML. */
	String xml() throws IOException {
		return HlomResources.toXml(resource);
	}

	/** The document in the JSON form (Healthcare LOM only). */
	String json() throws IOException {
		return HlomResources.toJson(resource);
	}

	/** Checks the document as written: XML Schema, constraints and rules. */
	List<DocumentCheck.Located> check() {
		try {
			return DocumentCheck.check(xml(), resource.getURI()).problems();
		} catch (IOException e) {
			return List.of();
		}
	}
}
