/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.io.IOException;

/** A failed MeSH lookup. {@link #kind()} tells the user interface what to report. */
public class MeshException extends IOException {

	private static final long serialVersionUID = 1L;

	/** Why a lookup failed. */
	public enum Kind {
		/** The service couldn't be reached (no connection, DNS failure, timeout) or offline mode is on. */
		OFFLINE,
		/** The service answered with an HTTP error or an error message. */
		SERVER,
		/** The response couldn't be understood. */
		INVALID_RESPONSE,
		/** The request was interrupted. */
		CANCELLED
	}

	private final Kind kind;

	public MeshException(Kind kind, String message, Throwable cause) {
		super(message, cause);
		this.kind = kind;
	}

	public MeshException(Kind kind, String message) {
		this(kind, message, null);
	}

	public Kind kind() {
		return kind;
	}
}
