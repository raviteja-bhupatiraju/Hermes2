/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.type.DocumentType;

/**
 * A record of a metadata library: a file with its summary and validation result.
 *
 * @param path         the file
 * @param summary      the summary of the record (empty if it could not be read)
 * @param problems     the validation problems, detached from the model ({@link HlomProblem#object()} is
 *                     {@code null})
 * @param modified     the last modification time of the file, or {@code null}
 * @param size         the file size in bytes
 * @param completeness the completeness score 0 to 100, or {@code -1} if no {@link CompletenessScorer} is set
 * @param type         the type of the document, or {@code null} if it could not be read
 * @param links        the identifiers of the document and the identifiers of other documents it names
 */
public record LibraryEntry(Path path, HlomSummary summary, List<HlomProblem> problems, Instant modified, long size,
		int completeness, DocumentType type, DocumentLinks links) {

	/** An entry without identifiers and links. */
	public LibraryEntry(Path path, HlomSummary summary, List<HlomProblem> problems, Instant modified, long size,
			int completeness, DocumentType type) {
		this(path, summary, problems, modified, size, completeness, type, DocumentLinks.NONE);
	}

	/** Overall validation state of a record. */
	public enum Status {
		VALID("Valid"), WARNINGS("Warnings"), ERRORS("Errors");

		private final String label;

		Status(String label) {
			this.label = label;
		}

		/** A human readable name. */
		public String label() {
			return label;
		}
	}

	/** The file name. */
	public String fileName() {
		return path.getFileName() == null ? path.toString() : path.getFileName().toString();
	}

	/** The title of the record, or the file name if it has none. */
	public String title() {
		return summary.titleOr(fileName());
	}

	/** The number of errors. */
	public int errors() {
		return count(Severity.ERROR);
	}

	/** The number of warnings. */
	public int warnings() {
		return count(Severity.WARNING);
	}

	/** Whether a completeness score is available. */
	public boolean hasCompleteness() {
		return completeness >= 0;
	}

	/** Errors, warnings or valid. */
	public Status status() {
		return errors() > 0 ? Status.ERRORS : warnings() > 0 ? Status.WARNINGS : Status.VALID;
	}

	private int count(Severity severity) {
		return (int) problems.stream().filter(p -> p.severity() == severity).count();
	}
}
