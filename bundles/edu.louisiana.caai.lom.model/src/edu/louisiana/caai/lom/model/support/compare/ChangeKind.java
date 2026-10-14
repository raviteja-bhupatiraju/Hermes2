/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

/** How a field differs between the left and the right document of a comparison. */
public enum ChangeKind {
	/** Present only in the right document. */
	ADDED("Added"),
	/** Present only in the left document. */
	REMOVED("Removed"),
	/** Present in both documents with different values. */
	CHANGED("Changed"),
	/** Present in both documents with the same value. */
	UNCHANGED("Unchanged");

	private final String label;

	ChangeKind(String label) {
		this.label = label;
	}

	/** A human readable name. */
	public String label() {
		return label;
	}

	/** Whether this is an actual difference (anything but {@link #UNCHANGED}). */
	public boolean isChange() {
		return this != UNCHANGED;
	}
}
