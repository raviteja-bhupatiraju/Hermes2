/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A small JSON (RFC 8259) reader and writer that keeps the order of object members and the line of every value,
 * so that problems found in a document derived from JSON text can be reported at the right line.
 * <p>
 * Values are {@link JObject}, {@link JArray}, {@link JString}, {@link JNumber}, {@link JBoolean} and
 * {@link JNull}.
 */
public final class Json {

	private Json() {
	}

	/** A JSON value and the 1-based line it starts at (0 for values that were not parsed). */
	public sealed interface Value permits JObject, JArray, JString, JNumber, JBoolean, JNull {
		int line();
	}

	/** An object member: its name, the line of the name and the value. */
	public record Member(String name, int line, Value value) {
	}

	/** A JSON object; members keep their order. A repeated name keeps the last value. */
	public record JObject(Map<String, Member> members, int line) implements Value {

		public JObject() {
			this(new LinkedHashMap<>(), 0);
		}

		/** The value of a member, or {@code null}. */
		public Value get(String name) {
			Member member = members.get(name);
			return member == null ? null : member.value();
		}

		/** Adds or replaces a member. */
		public JObject put(String name, Value value) {
			members.put(name, new Member(name, 0, value));
			return this;
		}

		/** Adds or replaces a string member. */
		public JObject put(String name, String value) {
			return put(name, new JString(value, 0));
		}
	}

	/** A JSON array. */
	public record JArray(List<Value> items, int line) implements Value {

		public JArray() {
			this(new ArrayList<>(), 0);
		}
	}

	/** A JSON string. */
	public record JString(String value, int line) implements Value {
	}

	/** A JSON number, kept in its literal form so that no precision is lost. */
	public record JNumber(String literal, int line) implements Value {
	}

	/** {@code true} or {@code false}. */
	public record JBoolean(boolean value, int line) implements Value {
	}

	/** {@code null}. */
	public record JNull(int line) implements Value {
	}

	/** A syntax error, with its 1-based position. */
	public static final class JsonException extends Exception {
		private static final long serialVersionUID = 1L;
		private final int line;
		private final int column;

		public JsonException(String message, int line, int column) {
			super(message + " (line " + line + ", column " + column + ")");
			this.line = line;
			this.column = column;
		}

		public int getLine() {
			return line;
		}

		public int getColumn() {
			return column;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Reading
	// ----------------------------------------------------------------------------------------------------

	/** Parses a complete JSON text. */
	public static Value parse(String text) throws JsonException {
		Parser parser = new Parser(text);
		parser.skipWhitespace();
		Value value = parser.value();
		parser.skipWhitespace();
		if (!parser.atEnd()) {
			throw parser.error("Unexpected text after the end of the JSON value");
		}
		return value;
	}

	private static final class Parser {
		private final String text;
		private int pos;
		private int line = 1;
		private int lineStart;

		Parser(String text) {
			// A byte order mark is not part of the text.
			this.text = text.startsWith("﻿") ? text.substring(1) : text;
		}

		boolean atEnd() {
			return pos >= text.length();
		}

		JsonException error(String message) {
			return new JsonException(message, line, pos - lineStart + 1);
		}

		void skipWhitespace() {
			while (pos < text.length()) {
				char c = text.charAt(pos);
				if (c == '\n') {
					pos++;
					line++;
					lineStart = pos;
				} else if (c == ' ' || c == '\t' || c == '\r') {
					pos++;
				} else {
					return;
				}
			}
		}

		Value value() throws JsonException {
			if (atEnd()) {
				throw error("Unexpected end of the text: a value is missing");
			}
			char c = text.charAt(pos);
			return switch (c) {
				case '{' -> object();
				case '[' -> array();
				case '"' -> new JString(string(), line);
				case 't' -> literal("true", new JBoolean(true, line));
				case 'f' -> literal("false", new JBoolean(false, line));
				case 'n' -> literal("null", new JNull(line));
				default -> {
					if (c == '-' || (c >= '0' && c <= '9')) {
						yield number();
					}
					throw error("Unexpected character '" + c + "'");
				}
			};
		}

		private Value literal(String word, Value value) throws JsonException {
			if (!text.startsWith(word, pos)) {
				throw error("Unknown literal (expected " + word + ")");
			}
			pos += word.length();
			return value;
		}

		private JObject object() throws JsonException {
			int startLine = line;
			pos++;
			Map<String, Member> members = new LinkedHashMap<>();
			skipWhitespace();
			if (!atEnd() && text.charAt(pos) == '}') {
				pos++;
				return new JObject(members, startLine);
			}
			while (true) {
				skipWhitespace();
				if (atEnd() || text.charAt(pos) != '"') {
					throw error("Expected a member name in double quotes");
				}
				int nameLine = line;
				String name = string();
				skipWhitespace();
				if (atEnd() || text.charAt(pos) != ':') {
					throw error("Expected ':' after the member name \"" + name + "\"");
				}
				pos++;
				skipWhitespace();
				members.put(name, new Member(name, nameLine, value()));
				skipWhitespace();
				if (atEnd()) {
					throw error("Unexpected end of the text: '}' is missing");
				}
				char c = text.charAt(pos++);
				if (c == '}') {
					return new JObject(members, startLine);
				}
				if (c != ',') {
					pos--;
					throw error("Expected ',' or '}' in the object");
				}
			}
		}

		private JArray array() throws JsonException {
			int startLine = line;
			pos++;
			List<Value> items = new ArrayList<>();
			skipWhitespace();
			if (!atEnd() && text.charAt(pos) == ']') {
				pos++;
				return new JArray(items, startLine);
			}
			while (true) {
				skipWhitespace();
				items.add(value());
				skipWhitespace();
				if (atEnd()) {
					throw error("Unexpected end of the text: ']' is missing");
				}
				char c = text.charAt(pos++);
				if (c == ']') {
					return new JArray(items, startLine);
				}
				if (c != ',') {
					pos--;
					throw error("Expected ',' or ']' in the array");
				}
			}
		}

		private String string() throws JsonException {
			pos++;
			StringBuilder result = new StringBuilder();
			while (true) {
				if (atEnd()) {
					throw error("Unterminated string");
				}
				char c = text.charAt(pos++);
				if (c == '"') {
					return result.toString();
				}
				if (c == '\n') {
					pos--;
					throw error("Line break in a string (write it as \\n)");
				}
				if (c != '\\') {
					result.append(c);
					continue;
				}
				if (atEnd()) {
					throw error("Unterminated string");
				}
				char escape = text.charAt(pos++);
				switch (escape) {
					case '"' -> result.append('"');
					case '\\' -> result.append('\\');
					case '/' -> result.append('/');
					case 'b' -> result.append('\b');
					case 'f' -> result.append('\f');
					case 'n' -> result.append('\n');
					case 'r' -> result.append('\r');
					case 't' -> result.append('\t');
					case 'u' -> {
						if (pos + 4 > text.length()) {
							throw error("Incomplete \\u escape");
						}
						try {
							result.append((char) Integer.parseInt(text.substring(pos, pos + 4), 16));
						} catch (NumberFormatException _) {
							throw error("Invalid \\u escape");
						}
						pos += 4;
					}
					default -> {
						pos--;
						throw error("Invalid escape '\\" + escape + "'");
					}
				}
			}
		}

		private JNumber number() throws JsonException {
			int start = pos;
			if (text.charAt(pos) == '-') {
				pos++;
			}
			int digits = digits();
			if (digits == 0) {
				throw error("Invalid number");
			}
			if (pos < text.length() && text.charAt(pos) == '.') {
				pos++;
				if (digits() == 0) {
					throw error("Invalid number: digits expected after '.'");
				}
			}
			if (pos < text.length() && (text.charAt(pos) == 'e' || text.charAt(pos) == 'E')) {
				pos++;
				if (pos < text.length() && (text.charAt(pos) == '+' || text.charAt(pos) == '-')) {
					pos++;
				}
				if (digits() == 0) {
					throw error("Invalid number: digits expected in the exponent");
				}
			}
			return new JNumber(text.substring(start, pos), line);
		}

		private int digits() {
			int start = pos;
			while (pos < text.length() && text.charAt(pos) >= '0' && text.charAt(pos) <= '9') {
				pos++;
			}
			return pos - start;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Writing
	// ----------------------------------------------------------------------------------------------------

	/** Writes a value as indented JSON text (two spaces, {@code \n} line ends, a final line end). */
	public static String write(Value value) {
		StringBuilder out = new StringBuilder();
		write(value, out, 0);
		out.append('\n');
		return out.toString();
	}

	/** Writes a value as JSON text without whitespace (for protocols such as JSON-RPC). */
	public static String writeCompact(Value value) {
		StringBuilder out = new StringBuilder();
		writeCompact(value, out);
		return out.toString();
	}

	private static void writeCompact(Value value, StringBuilder out) {
		switch (value) {
			case JObject object -> {
				out.append('{');
				boolean first = true;
				for (Member member : object.members().values()) {
					if (!first) {
						out.append(',');
					}
					first = false;
					quote(member.name(), out);
					out.append(':');
					writeCompact(member.value(), out);
				}
				out.append('}');
			}
			case JArray array -> {
				out.append('[');
				for (int i = 0; i < array.items().size(); i++) {
					if (i > 0) {
						out.append(',');
					}
					writeCompact(array.items().get(i), out);
				}
				out.append(']');
			}
			case JString string -> quote(string.value(), out);
			case JNumber number -> out.append(number.literal());
			case JBoolean bool -> out.append(bool.value());
			case JNull _ -> out.append("null");
		}
	}

	private static void write(Value value, StringBuilder out, int indent) {
		switch (value) {
			case JObject object -> {
				if (object.members().isEmpty()) {
					out.append("{}");
					return;
				}
				out.append('{');
				boolean first = true;
				for (Member member : object.members().values()) {
					out.append(first ? "\n" : ",\n");
					first = false;
					indent(out, indent + 1);
					quote(member.name(), out);
					out.append(": ");
					write(member.value(), out, indent + 1);
				}
				out.append('\n');
				indent(out, indent);
				out.append('}');
			}
			case JArray array -> {
				if (array.items().isEmpty()) {
					out.append("[]");
					return;
				}
				out.append('[');
				boolean first = true;
				for (Value item : array.items()) {
					out.append(first ? "\n" : ",\n");
					first = false;
					indent(out, indent + 1);
					write(item, out, indent + 1);
				}
				out.append('\n');
				indent(out, indent);
				out.append(']');
			}
			case JString string -> quote(string.value(), out);
			case JNumber number -> out.append(number.literal());
			case JBoolean bool -> out.append(bool.value());
			case JNull _ -> out.append("null");
		}
	}

	private static void indent(StringBuilder out, int indent) {
		out.repeat("  ", indent);
	}

	/** Appends a string literal. */
	public static void quote(String value, StringBuilder out) {
		out.append('"');
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
				case '"' -> out.append("\\\"");
				case '\\' -> out.append("\\\\");
				case '\n' -> out.append("\\n");
				case '\r' -> out.append("\\r");
				case '\t' -> out.append("\\t");
				case '\b' -> out.append("\\b");
				case '\f' -> out.append("\\f");
				default -> {
					if (c < 0x20) {
						out.append("\\u%04x".formatted((int) c));
					} else {
						out.append(c);
					}
				}
			}
		}
		out.append('"');
	}
}
