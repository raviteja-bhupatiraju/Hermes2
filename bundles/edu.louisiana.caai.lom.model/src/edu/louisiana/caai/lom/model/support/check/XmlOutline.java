/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The elements of XML text with their offsets, from a tolerant scan of the tags (comments, CDATA sections,
 * processing instructions and declarations are skipped; unclosed elements end at the end of the text). Works on
 * text that isn't well-formed, as typed in an editor.
 */
public final class XmlOutline {

	/**
	 * An element.
	 *
	 * @param name      the qualified name as written
	 * @param localName the name without prefix
	 * @param path      the element path ({@link edu.louisiana.caai.lom.model.support.ElementPath#toString()} form)
	 * @param start     the offset of {@code <}
	 * @param nameStart the offset of the name
	 * @param nameEnd   the offset after the name
	 * @param tagEnd    the offset after the {@code >} of the start tag
	 * @param end       the offset after the end tag (or the start tag of an empty element)
	 * @param children  the child elements
	 */
	public record Element(String name, String localName, String path, int start, int nameStart, int nameEnd,
			int tagEnd, int end, List<Element> children) {
	}

	private final String text;
	private final List<Element> roots = new ArrayList<>();
	private final Map<String, Element> byPath = new HashMap<>();

	private XmlOutline(String text) {
		this.text = text;
		scan();
	}

	/** Scans XML text. */
	public static XmlOutline of(String text) {
		return new XmlOutline(text);
	}

	/** The top-level elements (normally one). */
	public List<Element> roots() {
		return roots;
	}

	/** The element at a path (without prefixes, {@code [1]} left out), or {@code null}. */
	public Element element(String path) {
		return byPath.get(path);
	}

	/** The innermost element containing an offset (its tags or content), or {@code null}. */
	public Element elementAt(int offset) {
		Element found = null;
		List<Element> level = roots;
		boolean deeper = true;
		while (deeper) {
			deeper = false;
			for (Element element : level) {
				if (element.start() <= offset && offset < Math.max(element.end(), element.tagEnd())) {
					found = element;
					level = element.children();
					deeper = true;
					break;
				}
			}
		}
		return found;
	}

	/** The name of the attribute at an offset inside a start tag, or {@code null}. */
	public String attributeAt(Element element, int offset) {
		if (element == null || offset < element.nameEnd() || offset >= element.tagEnd()) {
			return null;
		}
		String tag = text.substring(element.nameEnd(), element.tagEnd());
		java.util.regex.Matcher matcher = java.util.regex.Pattern
				.compile("([\\w.:-]+)\\s*=\\s*(\"[^\"]*\"|'[^']*')").matcher(tag);
		while (matcher.find()) {
			if (element.nameEnd() + matcher.start() <= offset && offset < element.nameEnd() + matcher.end()) {
				String name = matcher.group(1);
				int colon = name.indexOf(':');
				return colon >= 0 ? name.substring(colon + 1) : name;
			}
		}
		return null;
	}

	/** A mutable element while scanning. */
	private static final class Open {
		final String name;
		final String localName;
		final String path;
		final int start;
		final int nameStart;
		final int nameEnd;
		final int tagEnd;
		final List<Element> children = new ArrayList<>();
		final Map<String, Integer> counts = new HashMap<>();

		Open(String name, String path, int start, int nameStart, int nameEnd, int tagEnd) {
			this.name = name;
			int colon = name.indexOf(':');
			this.localName = colon >= 0 ? name.substring(colon + 1) : name;
			this.path = path;
			this.start = start;
			this.nameStart = nameStart;
			this.nameEnd = nameEnd;
			this.tagEnd = tagEnd;
		}

		Element close(int end) {
			return new Element(name, localName, path, start, nameStart, nameEnd, tagEnd, end, List.copyOf(children));
		}
	}

	private void scan() {
		List<Open> stack = new ArrayList<>();
		Map<String, Integer> rootCounts = new HashMap<>();
		int i = 0;
		while (i < text.length()) {
			int open = text.indexOf('<', i);
			if (open < 0 || open + 1 >= text.length()) {
				break;
			}
			if (text.startsWith("<!--", open)) {
				i = skipTo(open, "-->");
			} else if (text.startsWith("<![CDATA[", open)) {
				i = skipTo(open, "]]>");
			} else if (text.startsWith("<?", open)) {
				i = skipTo(open, "?>");
			} else if (text.startsWith("<!", open)) {
				i = skipTo(open, ">");
			} else if (text.charAt(open + 1) == '/') {
				int end = skipTo(open, ">");
				String name = text.substring(open + 2, Math.max(open + 2, end - 1)).strip();
				// Close up to the matching element (tolerating missing end tags).
				int match = -1;
				for (int k = stack.size() - 1; k >= 0; k--) {
					if (stack.get(k).name.equals(name)) {
						match = k;
						break;
					}
				}
				if (match >= 0) {
					while (stack.size() > match) {
						finish(stack, end);
					}
				}
				i = end;
			} else {
				int nameEnd = open + 1;
				while (nameEnd < text.length() && !Character.isWhitespace(text.charAt(nameEnd))
						&& text.charAt(nameEnd) != '>' && text.charAt(nameEnd) != '/' && text.charAt(nameEnd) != '<') {
					nameEnd++;
				}
				if (nameEnd == open + 1) {
					i = open + 1;
					continue;
				}
				String name = text.substring(open + 1, nameEnd);
				int colon = name.indexOf(':');
				String local = colon >= 0 ? name.substring(colon + 1) : name;
				int tagEnd = tagEnd(nameEnd);
				Map<String, Integer> counts = stack.isEmpty() ? rootCounts : stack.getLast().counts;
				int index = counts.merge(local, 1, Integer::sum) - 1;
				String parentPath = stack.isEmpty() ? "" : stack.getLast().path;
				String path = parentPath + '/' + (index == 0 ? local : local + '[' + (index + 1) + ']');
				Open element = new Open(name, path, open, open + 1, nameEnd, tagEnd);
				stack.add(element);
				boolean empty = tagEnd >= 2 && text.charAt(tagEnd - 1) == '>' && text.charAt(tagEnd - 2) == '/';
				if (empty) {
					finish(stack, tagEnd);
				}
				i = tagEnd;
			}
		}
		while (!stack.isEmpty()) {
			finish(stack, text.length());
		}
	}

	private void finish(List<Open> stack, int end) {
		Element element = stack.removeLast().close(end);
		byPath.putIfAbsent(element.path(), element);
		if (stack.isEmpty()) {
			roots.add(element);
		} else {
			stack.getLast().children.add(element);
		}
	}

	private int skipTo(int from, String end) {
		int index = text.indexOf(end, from);
		return index < 0 ? text.length() : index + end.length();
	}

	/** The offset after the {@code >} of a start tag (quoted values may contain {@code >}); stops at a {@code <}. */
	private int tagEnd(int from) {
		char quote = 0;
		for (int i = from; i < text.length(); i++) {
			char c = text.charAt(i);
			if (quote != 0) {
				if (c == quote) {
					quote = 0;
				}
			} else if (c == '"' || c == '\'') {
				quote = c;
			} else if (c == '>') {
				return i + 1;
			} else if (c == '<') {
				return i;
			}
		}
		return text.length();
	}
}
