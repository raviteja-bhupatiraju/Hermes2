/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import org.eclipse.emf.ecore.EObject;

/**
 * A validation problem found in an HLOM document.
 *
 * @param severity the severity
 * @param origin   which validator reported the problem
 * @param message  a human readable message
 * @param object   the model element the problem refers to, or {@code null} if unknown
 * @param path     the XML element path of the problem (for example {@code /lom/general/title}), or {@code null}
 * @param line     the line in the serialized XML (1-based), or {@code -1} if unknown
 * @param column   the column in the serialized XML (1-based), or {@code -1} if unknown
 */
public record HlomProblem(Severity severity, Origin origin, String message, EObject object, String path, int line,
		int column) {

	/** Problem severity. */
	public enum Severity {
		ERROR, WARNING, INFO
	}

	/** The check that reported a problem. */
	public enum Origin {
		/** Validation of the XML against the HLOM XML schemas. */
		SCHEMA("XML Schema"),
		/** EMF model constraints (data types, patterns, multiplicities). */
		MODEL("Model"),
		/** The best practice rules of the standard that the schemas don't enforce. */
		RULES("Rules");

		private final String label;

		Origin(String label) {
			this.label = label;
		}

		public String label() {
			return label;
		}
	}
}
