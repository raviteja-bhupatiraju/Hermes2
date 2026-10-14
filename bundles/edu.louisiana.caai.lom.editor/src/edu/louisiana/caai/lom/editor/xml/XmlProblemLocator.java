/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Position;

/**
 * Turns the line/column of a parser or schema problem, or the element of a model problem, into the range of
 * text to underline.
 */
final class XmlProblemLocator {

	private final String text;
	private final int[] lineOffsets;

	XmlProblemLocator(String text) {
		this.text = text;
		List<Integer> offsets = new ArrayList<>();
		offsets.add(0);
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n' || (c == '\r' && (i + 1 >= text.length() || text.charAt(i + 1) != '\n'))) {
				offsets.add(i + 1);
			}
		}
		lineOffsets = offsets.stream().mapToInt(Integer::intValue).toArray();
	}

	private int lineStart(int line) {
		return lineOffsets[line];
	}

	/** The end of a (0-based) line, excluding the line delimiter. */
	private int lineEnd(int line) {
		int end = line + 1 < lineOffsets.length ? lineOffsets[line + 1] : text.length();
		while (end > lineOffsets[line] && (text.charAt(end - 1) == '\n' || text.charAt(end - 1) == '\r')) {
			end--;
		}
		return end;
	}

	private int lineOf(int offset) {
		int low = 0;
		int high = lineOffsets.length - 1;
		while (low < high) {
			int mid = (low + high + 1) >>> 1;
			if (lineOffsets[mid] <= offset) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	/**
	 * The range of a problem reported at a 1-based line and column (the column usually points just after the
	 * offending tag): the tag ending there, else the rest of the line, else the word before the column.
	 */
	Position locate(int line, int column) {
		if (text.isEmpty()) {
			return new Position(0, 0);
		}
		int lineIndex = Math.clamp(line - 1, 0, lineOffsets.length - 1);
		int start = lineStart(lineIndex);
		int end = lineEnd(lineIndex);
		if (column < 1) {
			return trimmed(start, end);
		}
		int p = Math.min(start + column - 1, end);
		if (p > 0 && text.charAt(p - 1) == '>') {
			int lt = text.lastIndexOf('<', p - 1);
			if (lt >= 0) {
				if (lt + 1 < text.length() && text.charAt(lt + 1) == '/') {
					// an end tag: a problem with the element's value; underline the value if on one line
					int contentStart = text.lastIndexOf('>', lt - 1) + 1;
					Position content = trimmed(contentStart, lt);
					if (content.length > 0 && text.substring(content.offset, content.offset + content.length)
							.indexOf('\n') < 0) {
						return content;
					}
				}
				return new Position(lt, Math.min(p, lineEnd(lineOf(lt))) - lt);
			}
		}
		Position rest = trimmed(p, end);
		if (rest.length > 0) {
			return rest;
		}
		int q = p;
		while (q > 0 && Character.isWhitespace(text.charAt(q - 1))) {
			q--;
		}
		int wordStart = q;
		while (wordStart > 0 && !Character.isWhitespace(text.charAt(wordStart - 1)) && q - wordStart < 80) {
			wordStart--;
		}
		if (q > wordStart) {
			return new Position(wordStart, q - wordStart);
		}
		return new Position(Math.min(p, text.length() - 1), 1);
	}

	/** The range of an element's start tag (on its first line). */
	Position locate(XmlStructure.Element element) {
		int end = Math.min(element.startTagEnd, lineEnd(lineOf(element.start)));
		return new Position(element.start, Math.max(1, end - element.start));
	}

	private Position trimmed(int start, int end) {
		int s = start;
		int e = end;
		while (s < e && Character.isWhitespace(text.charAt(s))) {
			s++;
		}
		while (e > s && Character.isWhitespace(text.charAt(e - 1))) {
			e--;
		}
		return new Position(s, e - s);
	}
}
