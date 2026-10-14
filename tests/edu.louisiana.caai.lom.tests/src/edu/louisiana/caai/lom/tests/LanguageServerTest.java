/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.cli.lsp.JsonRpc;
import edu.louisiana.caai.lom.cli.lsp.LanguageServer;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JNumber;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JString;
import edu.louisiana.caai.lom.model.support.json.Json.Value;

/** Tests the language server over piped streams, as an editor would use it. */
class LanguageServerTest {

	private static final String URI = "file:///tmp/course.xml";

	private JsonRpc client;
	private CompletableFuture<Integer> server;
	private final BlockingQueue<JObject> received = new LinkedBlockingQueue<>();
	private Thread reader;
	private int nextId;

	@BeforeEach
	void start() throws Exception {
		PipedOutputStream toServer = new PipedOutputStream();
		PipedInputStream serverIn = new PipedInputStream(toServer, 1 << 20);
		PipedOutputStream fromServer = new PipedOutputStream();
		PipedInputStream clientIn = new PipedInputStream(fromServer, 1 << 20);
		PrintStream log = new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8);
		server = CompletableFuture.supplyAsync(() -> {
			try {
				return LanguageServer.run(serverIn, fromServer, log);
			} finally {
				try {
					fromServer.close();
				} catch (java.io.IOException _) {
					// Closed.
				}
			}
		});
		client = new JsonRpc(clientIn, toServer);
		reader = Thread.ofVirtual().start(() -> {
			try {
				while (true) {
					received.add(client.read());
				}
			} catch (Exception _) {
				// The server closed the stream.
			}
		});
	}

	@AfterEach
	void stop() throws Exception {
		if (!server.isDone()) {
			client.notify("exit", new JObject());
		}
		server.get(10, TimeUnit.SECONDS);
		reader.join(5000);
	}

	private Value request(String method, JObject params) throws Exception {
		int id = ++nextId;
		client.write(new JObject().put("id", number(id)).put("method", method).put("params", params));
		JObject response = await(m -> m.get("id") instanceof JNumber(String literal, _) && Integer.parseInt(literal) == id);
		if (response.get("error") instanceof JObject error) {
			return error;
		}
		return response.get("result");
	}

	private JObject await(Predicate<JObject> condition) throws Exception {
		while (true) {
			JObject message = received.poll(20, TimeUnit.SECONDS);
			assertNotNull(message, "timed out waiting for a message");
			if (condition.test(message)) {
				return message;
			}
		}
	}

	private JArray diagnostics(int version) throws Exception {
		JObject notification = await(m -> m.get("method") instanceof JString(String method, _)
				&& method.equals("textDocument/publishDiagnostics")
				&& ((JObject) m.get("params")).get("version") instanceof JNumber(String v, _)
				&& Integer.parseInt(v) == version);
		return (JArray) ((JObject) notification.get("params")).get("diagnostics");
	}

	private static JNumber number(int value) {
		return new JNumber(Integer.toString(value), 0);
	}

	private static JObject position(int line, int character) {
		return new JObject().put("line", number(line)).put("character", number(character));
	}

	private static JObject document() {
		return new JObject().put("uri", URI);
	}

	private void open(String text) throws Exception {
		client.notify("textDocument/didOpen", new JObject().put("textDocument",
				new JObject().put("uri", URI).put("languageId", "xml").put("version", number(1)).put("text", text)));
	}

	private static String sample() throws Exception {
		return Files.readString(Path.of(TestResources.sample("insulin-pump-therapy-course.xml").toFileString()),
				StandardCharsets.UTF_8);
	}

	@Test
	void validatesHoversFixesAndFormats() throws Exception {
		JObject init = (JObject) request("initialize", new JObject());
		assertInstanceOf(JObject.class, init.get("capabilities"));
		client.notify("initialized", new JObject());

		String text = sample().replace("<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>", "");
		open(text);
		JArray diagnostics = diagnostics(1);
		assertFalse(diagnostics.items().isEmpty());
		JObject diagnostic = (JObject) diagnostics.items().stream()
				.filter(d -> ((JString) ((JObject) d).get("message")).value().contains("HEALTHCARE_LOMv1")).findFirst()
				.orElseThrow();
		assertEquals("1", ((JNumber) diagnostic.get("severity")).literal());

		// Hover over the title's string element.
		int titleLine = (int) text.substring(0, text.indexOf("Insulin Pump Therapy")).chars().filter(c -> c == '\n').count();
		JObject hover = (JObject) request("textDocument/hover", new JObject().put("textDocument", document())
				.put("position", position(titleLine, 12)));
		String markdown = ((JString) ((JObject) hover.get("contents")).get("value")).value();
		assertTrue(markdown.contains("General › Title"), markdown);
		assertTrue(markdown.contains("Name given to this learning object"), markdown);

		// The quick fix adds the metadata schema.
		JArray actions = (JArray) request("textDocument/codeAction", new JObject().put("textDocument", document())
				.put("range", diagnostic.get("range")).put("context", new JObject().put("diagnostics", new JArray())));
		JObject fix = (JObject) actions.items().stream()
				.filter(a -> ((JString) ((JObject) a).get("title")).value().contains("HEALTHCARE_LOMv1")).findFirst()
				.orElseThrow(() -> new AssertionError(actions));
		JObject changes = (JObject) ((JObject) fix.get("edit")).get("changes");
		JObject edit = (JObject) ((JArray) changes.get(URI)).items().getFirst();
		String fixed = ((JString) edit.get("newText")).value();
		assertTrue(fixed.contains("<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>"), fixed);

		// A change is validated again.
		client.notify("textDocument/didChange", new JObject().put("textDocument", document().put("version", number(2)))
				.put("contentChanges", new JArray(new java.util.ArrayList<>(java.util.List.of(new JObject().put("text", fixed))), 0)));
		JArray after = diagnostics(2);
		assertTrue(after.items().stream().noneMatch(d -> ((JString) ((JObject) d).get("message")).value()
				.contains("HEALTHCARE_LOMv1")), after::toString);

		JArray symbols = (JArray) request("textDocument/documentSymbol", new JObject().put("textDocument", document()));
		assertEquals("LOM", ((JString) ((JObject) symbols.items().getFirst()).get("name")).value());
		assertInstanceOf(JArray.class, request("textDocument/formatting", new JObject().put("textDocument", document())));
		JObject unknown = (JObject) request("textDocument/rename", new JObject());
		assertEquals(String.valueOf(JsonRpc.METHOD_NOT_FOUND), ((JNumber) unknown.get("code")).literal());

		request("shutdown", new JObject());
		client.notify("exit", new JObject());
		assertEquals(0, server.get(10, TimeUnit.SECONDS));
	}

	@Test
	void otherDocumentsGetNoDiagnostics() throws Exception {
		request("initialize", new JObject());
		open("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"><modelVersion>4.0.0</modelVersion></project>");
		assertTrue(diagnostics(1).items().isEmpty());
	}
}
