/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli.lsp;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JsonException;
import edu.louisiana.caai.lom.model.support.json.Json.Value;

/**
 * The base protocol of the Language Server Protocol: JSON-RPC 2.0 messages with a {@code Content-Length} header,
 * over a pair of streams. Reading is done by one thread; writing is synchronized.
 */
public final class JsonRpc {

	/** JSON-RPC error codes. */
	public static final int PARSE_ERROR = -32700;
	public static final int INVALID_REQUEST = -32600;
	public static final int METHOD_NOT_FOUND = -32601;
	public static final int INVALID_PARAMS = -32602;
	public static final int INTERNAL_ERROR = -32603;
	public static final int SERVER_NOT_INITIALIZED = -32002;
	public static final int REQUEST_FAILED = -32803;

	private final InputStream in;
	private final OutputStream out;

	public JsonRpc(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * Reads the next message.
	 *
	 * @return the message object
	 * @throws EOFException  at the end of the input
	 * @throws JsonException if the content is not JSON (the message is skipped)
	 */
	public JObject read() throws IOException, JsonException {
		int length = -1;
		while (true) {
			String header = readLine();
			if (header == null) {
				throw new EOFException();
			}
			if (header.isEmpty()) {
				if (length >= 0) {
					break;
				}
				continue;
			}
			int colon = header.indexOf(':');
			if (colon > 0 && header.substring(0, colon).strip().toLowerCase(Locale.ROOT).equals("content-length")) {
				length = Integer.parseInt(header.substring(colon + 1).strip());
			}
		}
		byte[] content = in.readNBytes(length);
		if (content.length < length) {
			throw new EOFException();
		}
		Value value = Json.parse(new String(content, StandardCharsets.UTF_8));
		if (!(value instanceof JObject message)) {
			throw new JsonException("A message must be a JSON object.", 1, 1);
		}
		return message;
	}

	private String readLine() throws IOException {
		ByteArrayOutputStream line = new ByteArrayOutputStream();
		int c;
		while ((c = in.read()) >= 0) {
			if (c == '\n') {
				String text = line.toString(StandardCharsets.US_ASCII);
				return text.endsWith("\r") ? text.substring(0, text.length() - 1) : text;
			}
			line.write(c);
		}
		return line.size() == 0 ? null : line.toString(StandardCharsets.US_ASCII);
	}

	/** Writes a message. */
	public synchronized void write(JObject message) throws IOException {
		message.put("jsonrpc", "2.0");
		byte[] content = Json.writeCompact(message).getBytes(StandardCharsets.UTF_8);
		out.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
		out.write(content);
		out.flush();
	}

	/** Sends a notification. */
	public void notify(String method, Value params) throws IOException {
		write(new JObject().put("method", method).put("params", params));
	}

	/** Sends the result of a request. */
	public void respond(Value id, Value result) throws IOException {
		write(new JObject().put("id", id).put("result", result == null ? new Json.JNull(0) : result));
	}

	/** Sends an error response. */
	public void error(Value id, int code, String message) throws IOException {
		write(new JObject().put("id", id == null ? new Json.JNull(0) : id).put("error",
				new JObject().put("code", new Json.JNumber(Integer.toString(code), 0)).put("message", message)));
	}
}
