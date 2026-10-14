/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.validation;

import java.util.List;

import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * An editor whose document is validated and whose problems are shown in the {@link ValidationView}.
 */
public interface ValidationSource {

	/** The problems found by the most recent validation. */
	List<HlomProblem> getProblems();

	/** Validates the document now. */
	void validateNow();

	/** Shows the element or XML position a problem refers to. */
	void reveal(HlomProblem problem);

	/** Registers a listener that is notified (in the UI thread) after each validation. */
	void addProblemsListener(Runnable listener);

	void removeProblemsListener(Runnable listener);
}
