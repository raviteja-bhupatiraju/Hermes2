/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * {@link MeshTransport} based on {@link HttpClient}, with connect and request timeouts and the editor's
 * User-Agent.
 */
public final class HttpMeshTransport implements MeshTransport {

	private final HttpClient client;
	private final Duration timeout;
	private final String userAgent;

	/** Creates a transport with the given timeout (applied to connecting and to the whole request). */
	public HttpMeshTransport(Duration timeout, String userAgent) {
		this.timeout = timeout;
		this.userAgent = userAgent;
		this.client = HttpClient.newBuilder().connectTimeout(timeout).followRedirects(HttpClient.Redirect.NORMAL)
				.build();
	}

	@Override
	public Response get(URI uri) throws IOException {
		HttpRequest request = HttpRequest.newBuilder(uri).timeout(timeout).header("User-Agent", userAgent)
				.header("Accept", "application/json").GET().build();
		try {
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			return new Response(response.statusCode(), response.body());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			InterruptedIOException interrupted = new InterruptedIOException("Request interrupted: " + uri);
			interrupted.initCause(e);
			throw interrupted;
		}
	}
}
