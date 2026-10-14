/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * A note about how a CSV cell was imported: an interpreted value (info), a value that was ignored or
 * corrected (warning) or a row that could not be imported (error).
 *
 * @param severity the severity
 * @param column   the CSV column the message refers to, or {@code null} for the whole row
 * @param field    the import field, or {@code null}
 * @param message  the message
 */
public record ImportMessage(Severity severity, String column, ImportField field, String message) {

	/** The message prefixed with the column, e.g. {@code Type: "slides" was read as "slide".} */
	public String describe() {
		return column == null || column.isEmpty() ? message : column + ": " + message;
	}

	@Override
	public String toString() {
		return severity + " " + describe();
	}
}
