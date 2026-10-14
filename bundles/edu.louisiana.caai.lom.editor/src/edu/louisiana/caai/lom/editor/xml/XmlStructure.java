/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The element structure of an XML text, found by a lightweight, error tolerant scan (no XML parser): elements
 * with the offsets of their tags, namespace declarations and comments. Used for content assist, matching tags,
 * folding and to locate problems; it never fails, whatever the text.
 */
final class XmlStructure {

	/** An element: its start tag, and its end tag if there is one. */
	static final class Element {

		final String name;
		final Element parent;
		final List<Element> children = new ArrayList<>();
		/** The namespaces declared on the start tag: prefix ({@code ""} for the default namespace) to URI. */
		final Map<String, String> namespaces = new LinkedHashMap<>();
		/** Offset of the {@code <} of the start tag. */
		final int start;
		/** Offset after the {@code >} of the start tag. */
		final int startTagEnd;
		final boolean empty;
		/** Offset of the {@code </} of the end tag, {@code -1} if there is none. */
		int endTagStart = -1;
		/** Offset after the end tag ({@code startTagEnd} for empty elements), {@code -1} if unclosed. */
		int end = -1;

		Element(String name, Element parent, int start, int startTagEnd, boolean empty) {
			this.name = name;
			this.parent = parent;
			this.start = start;
			this.startTagEnd = startTagEnd;
			this.empty = empty;
			if (empty) {
				end = startTagEnd;
			}
		}

		String prefix() {
			int colon = name.indexOf(':');
			return colon < 0 ? "" : name.substring(0, colon);
		}

		String localName() {
			return name.substring(name.indexOf(':') + 1);
		}

		/** The namespace URI of this element, {@code null} if its prefix is undeclared. */
		String namespace() {
			return namespaceOf(prefix());
		}

		/** The URI an in-scope prefix is bound to, or {@code null}. */
		String namespaceOf(String prefix) {
			for (Element e = this; e != null; e = e.parent) {
				String uri = e.namespaces.get(prefix);
				if (uri != null) {
					return uri;
				}
			}
			return "".equals(prefix) ? "" : null;
		}

		/** A prefix in scope for a namespace URI ({@code ""} for the default namespace), or {@code null}. */
		String prefixOf(String uri) {
			for (Element e = this; e != null; e = e.parent) {
				for (Map.Entry<String, String> entry : e.namespaces.entrySet()) {
					if (entry.getValue().equals(uri) && uri.equals(namespaceOf(entry.getKey()))) {
						return entry.getKey();
					}
				}
			}
			return null;
		}

		int nameStart() {
			return start + 1;
		}

		int endNameStart() {
			return endTagStart + 2;
		}

		boolean isClosed() {
			return end >= 0;
		}

		/** Elements from the root down to this element. */
		List<Element> path() {
			List<Element> path = new ArrayList<>();
			for (Element e = this; e != null; e = e.parent) {
				path.add(e);
			}
			Collections.reverse(path);
			return path;
		}

		/** Index among the siblings with the same name. */
		int indexAmongSameNamed() {
			if (parent == null) {
				return 0;
			}
			int index = 0;
			for (Element sibling : parent.children) {
				if (sibling == this) {
					return index;
				}
				if (sibling.name.equals(name)) {
					index++;
				}
			}
			return index;
		}

		@Override
		public String toString() {
			return name + "@" + start;
		}
	}

	/** An offset range. */
	record Range(int offset, int length) {
		int end() {
			return offset + length;
		}
	}

	final List<Element> roots = new ArrayList<>();
	/** All elements in document order. */
	final List<Element> elements = new ArrayList<>();
	final List<Range> comments = new ArrayList<>();
	/** The innermost element that is still open at the end of the text, or {@code null}. */
	Element open;
	/** Offset of an incomplete tag at the end of the text, or {@code -1}. */
	int partialTag = -1;
	/** Whether the text ends inside a comment, CDATA section, processing instruction or declaration. */
	boolean inSpecial;

	private XmlStructure() {
	}

	/** Scans a text. */
	static XmlStructure parse(String text) {
		XmlStructure structure = new XmlStructure();
		structure.scan(text);
		return structure;
	}

	private void scan(String s) {
		int n = s.length();
		int i = 0;
		Element current = null;
		while (i < n) {
			int lt = s.indexOf('<', i);
			if (lt < 0) {
				break;
			}
			if (s.startsWith("<!--", lt)) {
				int close = s.indexOf("-->", lt + 4);
				if (close < 0) {
					inSpecial = true;
					break;
				}
				comments.add(new Range(lt, close + 3 - lt));
				i = close + 3;
				continue;
			}
			String terminator = s.startsWith("<![CDATA[", lt) ? "]]>"
					: s.startsWith("<?", lt) ? "?>" : s.startsWith("<!", lt) ? ">" : null;
			if (terminator != null) {
				int close = s.indexOf(terminator, lt + 2);
				if (close < 0) {
					inSpecial = true;
					break;
				}
				i = close + terminator.length();
				continue;
			}
			int gt = tagEnd(s, lt + 1);
			if (gt < 0) {
				int next = s.indexOf('<', lt + 1);
				if (next < 0) {
					partialTag = lt;
					break;
				}
				i = next;
				continue;
			}
			boolean endTag = lt + 1 < n && s.charAt(lt + 1) == '/';
			String name = name(s, endTag ? lt + 2 : lt + 1);
			if (endTag) {
				Element match = null;
				for (Element e = current; e != null; e = e.parent) {
					if (e.name.equals(name)) {
						match = e;
						break;
					}
				}
				if (match == null) {
					match = current; // a mismatched end tag (e.g. a misspelled name) closes the current element
				}
				if (match != null) {
					match.endTagStart = lt;
					match.end = gt + 1;
					current = match.parent;
				}
			} else if (!name.isEmpty()) {
				boolean empty = s.charAt(gt - 1) == '/';
				Element element = new Element(name, current, lt, gt + 1, empty);
				readNamespaces(s, lt + 1 + name.length(), gt, element.namespaces);
				(current == null ? roots : current.children).add(element);
				elements.add(element);
				if (!empty) {
					current = element;
				}
			}
			i = gt + 1;
		}
		open = current;
	}

	/** The offset of the {@code >} ending a tag, or {@code -1} if an unquoted {@code <} or the end comes first. */
	static int tagEnd(String s, int from) {
		char quote = 0;
		for (int i = from; i < s.length(); i++) {
			char c = s.charAt(i);
			if (quote != 0) {
				if (c == quote) {
					quote = 0;
				}
			} else if (c == '"' || c == '\'') {
				quote = c;
			} else if (c == '>') {
				return i;
			} else if (c == '<') {
				return -1;
			}
		}
		return -1;
	}

	/** The XML name starting at an offset (possibly empty). */
	static String name(String s, int from) {
		int i = from;
		while (i < s.length() && isNameChar(s.charAt(i))) {
			i++;
		}
		return s.substring(from, i);
	}

	static boolean isNameChar(char c) {
		return Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '-' || c == '.';
	}

	private static void readNamespaces(String s, int from, int to, Map<String, String> namespaces) {
		int i = from;
		while (i < to) {
			while (i < to && !isNameChar(s.charAt(i))) {
				i++;
			}
			String attribute = name(s, i);
			if (attribute.isEmpty()) {
				break;
			}
			i += attribute.length();
			while (i < to && Character.isWhitespace(s.charAt(i))) {
				i++;
			}
			if (i < to && s.charAt(i) == '=') {
				i++;
				while (i < to && Character.isWhitespace(s.charAt(i))) {
					i++;
				}
				if (i < to && (s.charAt(i) == '"' || s.charAt(i) == '\'')) {
					int close = s.indexOf(s.charAt(i), i + 1);
					if (close < 0 || close > to) {
						break;
					}
					String value = s.substring(i + 1, close);
					if (attribute.equals("xmlns")) {
						namespaces.put("", value);
					} else if (attribute.startsWith("xmlns:")) {
						namespaces.put(attribute.substring(6), value);
					}
					i = close + 1;
				}
			}
		}
	}

	/** The innermost element whose start or end tag name contains the offset (name start to name end). */
	Element elementAtTagName(int offset) {
		for (Element e : elements) {
			int nameStart = e.nameStart();
			if (offset >= nameStart - 1 && offset <= nameStart + e.name.length()) {
				return e;
			}
			if (e.endTagStart >= 0) {
				int endName = e.endNameStart();
				if (offset >= endName - 2 && offset <= endName + e.name.length()) {
					return e;
				}
			}
		}
		return null;
	}

	/** The innermost element containing an offset (between the start of its start tag and its end). */
	Element enclosing(int offset) {
		Element result = null;
		List<Element> candidates = roots;
		boolean found = true;
		while (found) {
			found = false;
			for (Element e : candidates) {
				int end = e.end >= 0 ? e.end : Integer.MAX_VALUE;
				if (offset >= e.start && offset < end) {
					result = e;
					candidates = e.children;
					found = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Finds an element by its path of (namespace, local name, index among same-named siblings) steps from the
	 * root, as far as it exists.
	 */
	Element find(List<String[]> steps) {
		Element result = null;
		List<Element> candidates = roots;
		for (String[] step : steps) {
			int wanted = Integer.parseInt(step[2]);
			Element match = null;
			int index = 0;
			for (Element e : candidates) {
				if (e.localName().equals(step[1]) && step[0].equals(e.namespace())) {
					if (index++ == wanted) {
						match = e;
						break;
					}
				}
			}
			if (match == null) {
				break;
			}
			result = match;
			candidates = match.children;
		}
		return result;
	}
}
