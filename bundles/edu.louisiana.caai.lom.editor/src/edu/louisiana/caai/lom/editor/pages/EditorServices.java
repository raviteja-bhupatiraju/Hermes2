/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * Services the editor offers to its pages: navigation between pages and access to validation results.
 */
public interface EditorServices {

	/** Page id of the tree page. */
	String TREE = "tree";

	/** Page id of the XML source page. */
	String XML = "xml";

	/** Page id of the JSON source page. */
	String JSON = "json";

	/** Shows a page by its id ({@link AbstractLomPage#getId()}, {@link #TREE}, {@link #XML} or {@link #JSON}). */
	void showPage(String pageId);

	/** Shows the page presenting a model element (falls back to the tree page). */
	void reveal(EObject element);

	/** The validation of the edited document. */
	ValidationSource validation();

	/** The problems of the current validation that concern elements shown on the given page. */
	List<HlomProblem> problemsOf(String pageId);
}
