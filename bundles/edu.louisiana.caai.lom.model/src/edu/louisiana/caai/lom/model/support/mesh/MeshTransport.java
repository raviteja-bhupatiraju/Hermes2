/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.io.IOException;
import java.net.URI;

/**
 * The HTTP layer of the {@link MeshClient}: performs GET requests. Replaceable for tests and for clients that
 * need a proxy or another HTTP stack.
 */
@FunctionalInterface
public interface MeshTransport {

	/** The status and body of an HTTP response. */
	record Response(int status, String body) {
	}

	/**
	 * Performs a GET request. Implementations should apply a timeout and throw an {@link IOException} when the
	 * server can't be reached; an interrupted request is reported as {@link java.io.InterruptedIOException}.
	 */
	Response get(URI uri) throws IOException;
}
