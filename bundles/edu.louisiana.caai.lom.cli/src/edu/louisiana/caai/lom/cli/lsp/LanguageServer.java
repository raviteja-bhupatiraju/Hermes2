/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli.lsp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.URI;

import edu.louisiana.caai.lom.cli.Hermes;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.FieldHelp;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.check.TextPositions.Range;
import edu.louisiana.caai.lom.model.support.check.XmlOutline;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JBoolean;
import edu.louisiana.caai.lom.model.support.json.Json.JNumber;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JString;
import edu.louisiana.caai.lom.model.support.json.Json.JsonException;
import edu.louisiana.caai.lom.model.support.json.Json.Member;
import edu.louisiana.caai.lom.model.support.json.Json.Value;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;

/**
 * A language server for MedBiquitous documents (Language Server Protocol 3.17 over standard input and output),
 * for editors such as Visual Studio Code, Neovim or Emacs. It validates open documents (XML and JSON) as they are
 * edited — with the same checks as the Hermes 2 editor — and offers hovers with the field help, quick fixes,
 * formatting and an outline.
 */
public final class LanguageServer {

	/** Delay between the last change of a document and its validation. */
	private static final long DEBOUNCE_MILLIS = 250;

	/** An open document. */
	private record Document(String uri, int version, String text) {
	}

	private final JsonRpc rpc;
	private final PrintStream log;
	private final Map<String, Document> documents = new ConcurrentHashMap<>();
	private final Map<String, ScheduledFuture<?>> pending = new ConcurrentHashMap<>();
	private final ScheduledExecutorService validator = Executors.newSingleThreadScheduledExecutor(runnable -> {
		Thread thread = new Thread(runnable, "hermes-validation");
		thread.setDaemon(true);
		return thread;
	});
	private boolean initialized;
	private boolean shutdown;

	LanguageServer(JsonRpc rpc, PrintStream log) {
		this.rpc = rpc;
		this.log = log;
	}

	/**
	 * Serves requests until the client sends {@code exit} or closes the input.
	 *
	 * @return the exit code: 0 after {@code shutdown} and {@code exit}, else 1
	 */
	public static int run(InputStream in, OutputStream out, PrintStream log) {
		HlomResources.initStandalone();
		edu.louisiana.caai.medbiq.model.MedbiqStandards.init();
		LanguageServer server = new LanguageServer(new JsonRpc(in, out), log);
		return server.serve();
	}

	int serve() {
		try {
			while (true) {
				JObject message;
				try {
					message = rpc.read();
				} catch (JsonException e) {
					rpc.error(null, JsonRpc.PARSE_ERROR, e.getMessage());
					continue;
				}
				if (message.get("method") instanceof JString(String method, _)
						&& method.equals("exit")) {
					return shutdown ? 0 : 1;
				}
				handle(message);
			}
		} catch (EOFException _) {
			return shutdown ? 0 : 1;
		} catch (IOException e) {
			log.println("hermes lsp: " + e.getMessage());
			return 1;
		} finally {
			validator.shutdownNow();
		}
	}

	private void handle(JObject message) throws IOException {
		Value id = message.get("id");
		String method = message.get("method") instanceof JString(String name, _) ? name : null;
		JObject params = message.get("params") instanceof JObject object ? object : new JObject();
		if (method == null) {
			return; // A response to a request of ours (none are sent).
		}
		if (id == null) {
			notification(method, params);
			return;
		}
		if (!initialized && !method.equals("initialize")) {
			rpc.error(id, JsonRpc.SERVER_NOT_INITIALIZED, "The server is not initialized.");
			return;
		}
		try {
			Value result = switch (method) {
				case "initialize" -> initialize();
				case "shutdown" -> {
					shutdown = true;
					yield null;
				}
				case "textDocument/hover" -> hover(params);
				case "textDocument/codeAction" -> codeActions(params);
				case "textDocument/formatting" -> formatting(params);
				case "textDocument/documentSymbol" -> symbols(params);
				default -> throw new MethodNotFound(method);
			};
			rpc.respond(id, result);
		} catch (MethodNotFound e) {
			rpc.error(id, JsonRpc.METHOD_NOT_FOUND, "Unsupported method " + e.getMessage());
		} catch (RuntimeException e) {
			log.println("hermes lsp: " + method + " failed: " + e);
			rpc.error(id, JsonRpc.INTERNAL_ERROR, method + " failed: " + e.getMessage());
		}
	}

	/** A request of a method the server doesn't implement. */
	private static final class MethodNotFound extends RuntimeException {
		private static final long serialVersionUID = 1L;

		MethodNotFound(String method) {
			super(method, null, false, false);
		}
	}

	private void notification(String method, JObject params) throws IOException {
		switch (method) {
			case "initialized", "$/cancelRequest", "$/setTrace", "workspace/didChangeConfiguration" -> {
				// Nothing to do.
			}
			case "textDocument/didOpen" -> {
				JObject document = object(params, "textDocument");
				String uri = string(document, "uri");
				documents.put(uri, new Document(uri, integer(document, "version"), string(document, "text")));
				schedule(uri, 0);
			}
			case "textDocument/didChange" -> {
				JObject document = object(params, "textDocument");
				String uri = string(document, "uri");
				if (params.get("contentChanges") instanceof JArray changes && !changes.items().isEmpty()
						&& changes.items().getLast() instanceof JObject change) {
					// Full synchronization: the last change has the whole text.
					documents.put(uri, new Document(uri, integer(document, "version"), string(change, "text")));
					schedule(uri, DEBOUNCE_MILLIS);
				}
			}
			case "textDocument/didSave" -> {
				String uri = string(object(params, "textDocument"), "uri");
				if (params.get("text") instanceof JString(String text, _) && documents.get(uri) instanceof Document old) {
					documents.put(uri, new Document(uri, old.version(), text));
				}
				schedule(uri, 0);
			}
			case "textDocument/didClose" -> {
				String uri = string(object(params, "textDocument"), "uri");
				documents.remove(uri);
				ScheduledFuture<?> future = pending.remove(uri);
				if (future != null) {
					future.cancel(false);
				}
				publish(uri, null, new JArray());
			}
			default -> {
				// Unknown notifications are ignored.
			}
		}
	}

	private JObject initialize() {
		initialized = true;
		JObject sync = new JObject().put("openClose", bool(true)).put("change", number(1))
				.put("save", new JObject().put("includeText", bool(false)));
		JObject capabilities = new JObject().put("textDocumentSync", sync).put("hoverProvider", bool(true))
				.put("codeActionProvider", new JObject().put("codeActionKinds", strings("quickfix", "source.fixAll")))
				.put("documentFormattingProvider", bool(true)).put("documentSymbolProvider", bool(true));
		return new JObject().put("capabilities", capabilities).put("serverInfo",
				new JObject().put("name", "Hermes 2 Language Server").put("version", Hermes.version()));
	}

	// ----------------------------------------------------------------------------------------------------
	// Diagnostics
	// ----------------------------------------------------------------------------------------------------

	private void schedule(String uri, long delay) {
		ScheduledFuture<?> previous = pending.put(uri, validator.schedule(() -> validate(uri), delay,
				TimeUnit.MILLISECONDS));
		if (previous != null) {
			previous.cancel(false);
		}
	}

	private void validate(String uri) {
		Document document = documents.get(uri);
		if (document == null) {
			return;
		}
		try {
			if (!DocumentCheck.isDocument(document.text())) {
				// Other XML or JSON files (the client may send all of them) get no diagnostics.
				publish(uri, document.version(), new JArray());
				return;
			}
			Result result = check(document);
			JArray diagnostics = new JArray();
			for (Located located : result.problems()) {
				diagnostics.items().add(diagnostic(located, document.text()));
			}
			result.resource().unload();
			// Skip results of a version that was replaced meanwhile (a newer validation follows).
			if (documents.get(uri) == document) {
				publish(uri, document.version(), diagnostics);
			}
		} catch (IOException | RuntimeException e) {
			log.println("hermes lsp: validation of " + uri + " failed: " + e);
		}
	}

	private static Result check(Document document) {
		return DocumentCheck.check(document.text(), URI.createURI(document.uri()));
	}

	private void publish(String uri, Integer version, JArray diagnostics) throws IOException {
		JObject params = new JObject().put("uri", uri).put("diagnostics", diagnostics);
		if (version != null) {
			params.put("version", number(version));
		}
		rpc.notify("textDocument/publishDiagnostics", params);
	}

	private static JObject diagnostic(Located located, String text) {
		HlomProblem problem = located.problem();
		JObject diagnostic = new JObject().put("range", range(located.range() == null ? new Range(0, 0, 0, 0)
				: located.range())).put("severity", number(switch (problem.severity()) {
					case ERROR -> 1;
					case WARNING -> 2;
					case INFO -> 3;
				})).put("source", "hermes").put("code", problem.origin().label()).put("message", problem.message());
		if (problem.path() != null) {
			diagnostic.put("data", new JObject().put("path", problem.path()));
		}
		return diagnostic;
	}

	// ----------------------------------------------------------------------------------------------------
	// Hover
	// ----------------------------------------------------------------------------------------------------

	private Value hover(JObject params) {
		Document document = documents.get(string(object(params, "textDocument"), "uri"));
		if (document == null) {
			return null;
		}
		JObject position = object(params, "position");
		int line = integer(position, "line");
		int character = integer(position, "character");
		List<String> names;
		String attribute = null;
		Range range = null;
		if (document.text().stripLeading().startsWith("{")) {
			names = jsonPathAt(document.text(), line + 1);
		} else {
			int offset = offset(document.text(), line, character);
			XmlOutline outline = XmlOutline.of(document.text());
			XmlOutline.Element element = outline.elementAt(offset);
			if (element == null) {
				return null;
			}
			names = ElementPath.parse(element.path()).names();
			attribute = outline.attributeAt(element, offset);
			range = Range.of(document.text(), element.nameStart(), element.nameEnd());
		}
		if (names.isEmpty()) {
			return null;
		}
		String key = attribute != null && FieldHelp.text(attribute) != null ? attribute : FieldHelp.keyOf(names);
		StringBuilder markdown = new StringBuilder("**").append(FieldLabels.label(names, attribute)).append("**");
		String help = FieldHelp.text(key);
		if (help != null) {
			markdown.append("\n\n").append(help);
		}
		markdown.append("\n\n`").append(String.join("/", names)).append(attribute == null ? "" : "/@" + attribute)
				.append('`');
		JObject hover = new JObject().put("contents",
				new JObject().put("kind", "markdown").put("value", markdown.toString()));
		if (range != null) {
			hover.put("range", range(range));
		}
		return hover;
	}

	/** The member names from the root to the deepest member on a 1-based line of JSON text. */
	static List<String> jsonPathAt(String text, int line) {
		List<String> names = new ArrayList<>();
		try {
			collect(Json.parse(text), line, names);
		} catch (JsonException _) {
			// No path in broken JSON.
		}
		return names;
	}

	private static boolean collect(Value value, int line, List<String> names) {
		switch (value) {
			case JObject object -> {
				Member best = null;
				for (Member member : object.members().values()) {
					if (member.line() <= line && (best == null || member.line() >= best.line())) {
						best = member;
					}
				}
				if (best == null || best.name().startsWith("$")) {
					return false;
				}
				String name = best.name();
				int colon = name.indexOf(':');
				names.add(colon >= 0 ? name.substring(colon + 1) : name);
				collect(best.value(), line, names);
				return true;
			}
			case JArray array -> {
				Value best = null;
				for (Value item : array.items()) {
					if (lineOf(item) <= line) {
						best = item;
					}
				}
				return best != null && collect(best, line, names);
			}
			default -> {
				return false;
			}
		}
	}

	private static int lineOf(Value value) {
		return switch (value) {
			case JObject object -> object.line();
			case JArray array -> array.line();
			case JString string -> string.line();
			case JNumber number -> number.line();
			case JBoolean bool -> bool.line();
			case Json.JNull nul -> nul.line();
		};
	}

	// ----------------------------------------------------------------------------------------------------
	// Code actions and formatting
	// ----------------------------------------------------------------------------------------------------

	private Value codeActions(JObject params) {
		String uri = string(object(params, "textDocument"), "uri");
		Document document = documents.get(uri);
		JArray actions = new JArray();
		if (document == null) {
			return actions;
		}
		Range requested = range(object(params, "range"));
		Result result = check(document);
		List<Located> problems = result.problems();
		for (int i = 0; i < problems.size(); i++) {
			Located located = problems.get(i);
			if (located.range() == null || !intersects(located.range(), requested)) {
				continue;
			}
			List<HlomFix> fixes = HlomFixes.forProblem(located.problem());
			for (int j = 0; j < fixes.size(); j++) {
				HlomFix fix = fixes.get(j);
				if (fix.kind() != HlomFix.Kind.CHANGE) {
					continue;
				}
				String fixed = applyFix(document, i, j);
				if (fixed == null) {
					continue;
				}
				JObject action = new JObject().put("title", fix.label()).put("kind", "quickfix")
						.put("diagnostics", array(diagnostic(located, document.text())))
						.put("edit", edit(uri, document.text(), fixed));
				if (fix.isAutomatic()) {
					action.put("isPreferred", bool(true));
				}
				actions.items().add(action);
			}
		}
		List<HlomFixes.Planned> automatic = HlomFixes.automaticFixes(problems.stream().map(Located::problem).toList());
		if (!automatic.isEmpty()) {
			HlomFixes.apply(result.root(), automatic);
			String fixed = serialize(result, document.text());
			if (fixed != null) {
				actions.items().add(new JObject().put("title", "Fix all automatically fixable problems ("
						+ automatic.size() + ")").put("kind", "source.fixAll").put("edit", edit(uri, document.text(), fixed)));
			}
		}
		result.resource().unload();
		return actions;
	}

	/** The text after applying the j-th fix of the i-th problem to a fresh copy of the document, or {@code null}. */
	private static String applyFix(Document document, int problemIndex, int fixIndex) {
		Result fresh = check(document);
		try {
			DocumentRoot root = fresh.root();
			if (root == null || problemIndex >= fresh.problems().size()) {
				return null;
			}
			HlomProblem problem = fresh.problems().get(problemIndex).problem();
			List<HlomFix> fixes = HlomFixes.forProblem(problem);
			if (fixIndex >= fixes.size()) {
				return null;
			}
			fixes.get(fixIndex).apply(root, problem);
			return serialize(fresh, document.text());
		} finally {
			fresh.resource().unload();
		}
	}

	/** The document of a result in the form of the original text, or {@code null} if it can't be written. */
	private static String serialize(Result result, String original) {
		if (result.root() == null) {
			return null;
		}
		try {
			return original.stripLeading().startsWith("{") ? HlomResources.toJson(result.resource())
					: HlomResources.toXml(result.resource());
		} catch (IOException _) {
			return null;
		}
	}

	private Value formatting(JObject params) {
		String uri = string(object(params, "textDocument"), "uri");
		Document document = documents.get(uri);
		JArray edits = new JArray();
		if (document == null) {
			return edits;
		}
		Result result = check(document);
		boolean readable = result.root() != null && result.resource().getErrors().isEmpty();
		String formatted = readable ? serialize(result, document.text()) : null;
		result.resource().unload();
		if (formatted != null && !formatted.equals(document.text())) {
			edits.items().add(wholeDocumentEdit(document.text(), formatted));
		}
		return edits;
	}

	private static JObject edit(String uri, String text, String newText) {
		JObject changes = new JObject().put(uri, array(wholeDocumentEdit(text, newText)));
		return new JObject().put("changes", changes);
	}

	private static JObject wholeDocumentEdit(String text, String newText) {
		return new JObject().put("range", range(Range.of(text, 0, text.length()))).put("newText", newText);
	}

	private static boolean intersects(Range a, Range b) {
		return !(a.endLine() < b.startLine() || a.endLine() == b.startLine() && a.endColumn() < b.startColumn()
				|| b.endLine() < a.startLine() || b.endLine() == a.startLine() && b.endColumn() < a.startColumn());
	}

	// ----------------------------------------------------------------------------------------------------
	// Document symbols
	// ----------------------------------------------------------------------------------------------------

	private Value symbols(JObject params) {
		Document document = documents.get(string(object(params, "textDocument"), "uri"));
		JArray symbols = new JArray();
		if (document == null || document.text().stripLeading().startsWith("{")) {
			return symbols;
		}
		for (XmlOutline.Element element : XmlOutline.of(document.text()).roots()) {
			symbols.items().add(symbol(element, document.text()));
		}
		return symbols;
	}

	private static JObject symbol(XmlOutline.Element element, String text) {
		JArray children = new JArray();
		for (XmlOutline.Element child : element.children()) {
			children.items().add(symbol(child, text));
		}
		JObject symbol = new JObject().put("name", FieldLabels.words(element.localName()))
				.put("kind", number(element.children().isEmpty() ? 8 : 19))
				.put("range", range(Range.of(text, element.start(), Math.max(element.end(), element.tagEnd()))))
				.put("selectionRange", range(Range.of(text, element.nameStart(), element.nameEnd())));
		String detail = leafText(element, text);
		if (detail != null) {
			symbol.put("detail", detail);
		}
		if (!children.items().isEmpty()) {
			symbol.put("children", children);
		}
		return symbol;
	}

	/** The text of an element without children (shortened), or {@code null}. */
	private static String leafText(XmlOutline.Element element, String text) {
		if (!element.children().isEmpty() || element.end() <= element.tagEnd()) {
			return null;
		}
		int close = text.lastIndexOf("</", element.end());
		if (close < element.tagEnd()) {
			return null;
		}
		String content = text.substring(element.tagEnd(), close).replaceAll("\\s+", " ").strip();
		if (content.isEmpty()) {
			return null;
		}
		return content.length() > 60 ? content.substring(0, 57) + "…" : content;
	}

	// ----------------------------------------------------------------------------------------------------
	// JSON helpers
	// ----------------------------------------------------------------------------------------------------

	private static JObject object(JObject parent, String name) {
		return parent.get(name) instanceof JObject object ? object : new JObject();
	}

	private static String string(JObject parent, String name) {
		return parent.get(name) instanceof JString(String value, _) ? value : "";
	}

	private static int integer(JObject parent, String name) {
		return parent.get(name) instanceof JNumber(String literal, _) ? (int) Double.parseDouble(literal) : 0;
	}

	private static JNumber number(int value) {
		return new JNumber(Integer.toString(value), 0);
	}

	private static JBoolean bool(boolean value) {
		return new JBoolean(value, 0);
	}

	private static JArray strings(String... values) {
		JArray array = new JArray();
		for (String value : values) {
			array.items().add(new JString(value, 0));
		}
		return array;
	}

	private static JArray array(Value... values) {
		JArray array = new JArray();
		array.items().addAll(List.of(values));
		return array;
	}

	private static JObject range(Range range) {
		return new JObject().put("start", position(range.startLine(), range.startColumn())).put("end",
				position(range.endLine(), range.endColumn()));
	}

	private static Range range(JObject range) {
		JObject start = object(range, "start");
		JObject end = object(range, "end");
		return new Range(integer(start, "line"), integer(start, "character"), integer(end, "line"),
				integer(end, "character"));
	}

	private static JObject position(int line, int character) {
		return new JObject().put("line", number(line)).put("character", number(character));
	}

	/** The offset of a 0-based line and UTF-16 character in a text. */
	static int offset(String text, int line, int character) {
		int offset = 0;
		for (int l = 0; l < line && offset >= 0; l++) {
			offset = text.indexOf('\n', offset);
			offset = offset < 0 ? text.length() : offset + 1;
		}
		return Math.min(text.length(), offset + character);
	}
}
