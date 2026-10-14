/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;

/**
 * Rates how completely a record is described, from 0 (empty) to 100 (every recommended element present).
 * Pluggable so that the library and the reports show a completeness score only when a scorer is installed,
 * e.g. {@code HlomLibrary.setCompletenessScorer(HlomCompleteness::score)}.
 */
@FunctionalInterface
public interface CompletenessScorer {

	/** The completeness of a document, 0 to 100. */
	int score(DocumentRoot root);
}
