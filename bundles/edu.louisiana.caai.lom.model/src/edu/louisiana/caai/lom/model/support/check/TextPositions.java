/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.check;

import java.util.ArrayList;
import java.util.List;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.Member;
import edu.louisiana.caai.lom.model.support.json.Json.Value;

/**
 * Finds the text range of an element path (see {@link ElementPath}) in the text of a document, in its XML form
 * (by scanning the tags) or its JSON form (by the members). Used to place problems that only know their element
 * at the right line and column.
 */
public sealed interface TextPositions {

	/**
	 * A range of text (0-based lines and columns, end exclusive).
	 *
	 * @param startLine   the line of the first character
	 * @param startColumn the column of the first character
	 * @param endLine     the line after the last character
	 * @param endColumn   the column after the last character
	 */
	record Range(int startLine, int startColumn, int endLine, int endColumn) {

		/** The range of a 1-based line and column (or the whole line if the column is unknown). */
		public static Range at(String text, int line, int column) {
			List<Integer> starts = lineStarts(text);
			int l = Math.clamp(line - 1, 0, starts.size() - 1);
			int lineStart = starts.get(l);
			int lineEnd = l + 1 < starts.size() ? starts.get(l + 1) - 1 : text.length();
			int length = Math.max(0, lineEnd - lineStart - (lineEnd > lineStart && text.charAt(lineEnd - 1) == '\r' ? 1 : 0));
			if (column < 1) {
				int indent = 0;
				while (indent < length && Character.isWhitespace(text.charAt(lineStart + indent))) {
					indent++;
				}
				return new Range(l, indent, l, Math.max(indent, length));
			}
			// Parsers report the column after the problem; mark the token ending there.
			int end = Math.min(column - 1, length);
			int start = end;
			while (start > 0 && !Character.isWhitespace(text.charAt(lineStart + start - 1))
					&& text.charAt(lineStart + start - 1) != '>') {
				start--;
			}
			return start == end ? new Range(l, Math.max(0, end - 1), l, Math.max(end, 1)) : new Range(l, start, l, end);
		}

		/** The range between two offsets of a text. */
		public static Range of(String text, int start, int end) {
			List<Integer> starts = lineStarts(text);
			int[] from = position(starts, start);
			int[] to = position(starts, end);
			return new Range(from[0], from[1], to[0], to[1]);
		}

		/** The start of the range as a 1-based line. */
		public int line() {
			return startLine + 1;
		}

		/** The start of the range as a 1-based column. */
		public int column() {
			return startColumn + 1;
		}

		private static int[] position(List<Integer> starts, int offset) {
			int line = 0;
			int low = 0;
			int high = starts.size() - 1;
			while (low <= high) {
				int middle = (low + high) >>> 1;
				if (starts.get(middle) <= offset) {
					line = middle;
					low = middle + 1;
				} else {
					high = middle - 1;
				}
			}
			return new int[] { line, offset - starts.get(line) };
		}
	}

	/** The range of the element (or attribute) at a path, as far as the path exists; {@code null} if not at all. */
	Range find(String path);

	/** The positions of a document's text: JSON if it starts with <code>{</code>, else XML. */
	static TextPositions of(String text) {
		return text.stripLeading().startsWith("{") ? new JsonPositions(text) : new XmlPositions(text);
	}

	/** The offsets at which the lines of a text start. */
	static List<Integer> lineStarts(String text) {
		List<Integer> starts = new ArrayList<>();
		starts.add(0);
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				starts.add(i + 1);
			}
		}
		return starts;
	}

	/** Positions in XML text, from its {@link XmlOutline}. */
	final class XmlPositions implements TextPositions {

		private final String text;
		private final XmlOutline outline;

		XmlPositions(String text) {
			this.text = text;
			this.outline = XmlOutline.of(text);
		}

		@Override
		public Range find(String path) {
			String attribute = null;
			String elements = path;
			int at = path.lastIndexOf("/@");
			if (at >= 0) {
				attribute = path.substring(at + 2);
				int colon = attribute.indexOf(':');
				attribute = colon >= 0 ? attribute.substring(colon + 1) : attribute;
				elements = path.substring(0, at);
			}
			// Normalized: prefixes removed, [1] left out.
			String candidate = ElementPath.parse(elements).toString();
			XmlOutline.Element element = null;
			while (element == null && !candidate.isEmpty() && !candidate.equals("/")) {
				element = outline.element(candidate);
				if (element == null) {
					int slash = candidate.lastIndexOf('/');
					candidate = slash <= 0 ? "" : candidate.substring(0, slash);
					attribute = null;
				}
			}
			if (element == null) {
				return null;
			}
			if (attribute != null) {
				Range range = attribute(element, attribute);
				if (range != null) {
					return range;
				}
			}
			return Range.of(text, element.nameStart(), element.nameEnd());
		}

		private Range attribute(XmlOutline.Element element, String name) {
			String content = text.substring(element.nameEnd(), element.tagEnd());
			java.util.regex.Matcher matcher = java.util.regex.Pattern
					.compile("(?:^|\\s)((?:[\\w.-]+:)?" + java.util.regex.Pattern.quote(name) + ")\\s*=").matcher(content);
			if (!matcher.find()) {
				return null;
			}
			int start = element.nameEnd() + matcher.start(1);
			return Range.of(text, start, start + matcher.group(1).length());
		}
	}

	/** Positions in the JSON form: the lines of the members along the path. */
	final class JsonPositions implements TextPositions {

		private final String text;
		private final Value root;

		JsonPositions(String text) {
			this.text = text;
			Value parsed;
			try {
				parsed = Json.parse(text);
			} catch (Json.JsonException _) {
				parsed = null;
			}
			this.root = parsed;
		}

		@Override
		public Range find(String path) {
			if (!(root instanceof JObject object)) {
				return null;
			}
			String attribute = null;
			String elements = path;
			int at = path.lastIndexOf("/@");
			if (at >= 0) {
				attribute = path.substring(at + 2);
				int colon = attribute.indexOf(':');
				attribute = colon >= 0 ? attribute.substring(colon + 1) : attribute;
				elements = path.substring(0, at);
			}
			Value current = object;
			int line = -1;
			for (ElementPath.Step step : ElementPath.parse(elements).steps()) {
				if (!(current instanceof JObject members)) {
					break;
				}
				Member member = member(members, step.name());
				if (member == null) {
					continue; // The JSON form leaves out some wrapper elements (such as multilingual strings).
				}
				line = member.line();
				current = member.value();
				if (current instanceof JArray array) {
					if (step.index() < array.items().size()) {
						current = array.items().get(step.index());
						if (step.index() > 0) {
							// The first item is shown at the member, later ones at their own line.
							line = lineOf(current, line);
						}
					} else if (!array.items().isEmpty()) {
						current = array.items().getFirst();
					}
				}
			}
			if (attribute != null && current instanceof JObject members) {
				Member member = member(members, attribute);
				if (member == null) {
					member = member(members, "@" + attribute);
				}
				if (member != null) {
					line = member.line();
				}
			}
			return line < 1 ? null : Range.at(text, line, -1);
		}

		private static Member member(JObject object, String name) {
			Member member = object.members().get(name);
			if (member != null) {
				return member;
			}
			for (Member candidate : object.members().values()) {
				String key = candidate.name();
				int colon = key.indexOf(':');
				if (colon >= 0 && key.substring(colon + 1).equals(name)) {
					return candidate;
				}
			}
			return null;
		}

		private static int lineOf(Value value, int fallback) {
			return switch (value) {
				case JObject object -> object.line();
				case JArray array -> array.line();
				case Json.JString string -> string.line();
				case Json.JNumber number -> number.line();
				case Json.JBoolean bool -> bool.line();
				case Json.JNull nul -> nul.line();
				default -> fallback;
			};
		}
	}
}
