/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * Formats the XML of the XML page. A valid HLOM document without comments is pretty printed by an EMF round
 * trip (the same serialization the editor writes); any other text only gets its indentation fixed, never
 * touching text content, comments or the inside of tags.
 */
final class XmlFormatter {

	private static final URI URI_FOR_PARSING = URI.createURI("hlom:/format.xml");

	private XmlFormatter() {
	}

	/** The formatted text. */
	static String format(String text) {
		String pretty = roundTrip(text);
		return pretty != null ? pretty : indent(text, " ".repeat(XmlSourceViewerConfiguration.INDENT_WIDTH));
	}

	/** Pretty prints through the model, or returns {@code null} if the text can't be formatted that way. */
	static String roundTrip(String text) {
		if (text.contains("<!--") || text.contains("<![CDATA[") || text.isBlank()) {
			return null; // the model doesn't keep comments and CDATA sections
		}
		try {
			Resource resource = HlomResources.parse(text, URI_FOR_PARSING);
			if (!resource.getErrors().isEmpty() || HlomResources.getDocumentRoot(resource) == null) {
				return null;
			}
			String result = HlomResources.toXml(resource);
			String lineDelimiter = text.contains("\r\n") ? "\r\n" : "\n";
			return "\n".equals(lineDelimiter) ? result : result.replace("\n", lineDelimiter);
		} catch (IOException | RuntimeException _) {
			return null;
		}
	}

	/**
	 * Re-indents lines that start with markup and are separated from the previous markup by whitespace only;
	 * everything else (text content, multi-line tags and comments) is kept as is.
	 */
	static String indent(String text, String unit) {
		StringBuilder out = new StringBuilder(text.length() + 256);
		int depth = 0;
		int previousEnd = 0;
		int i = 0;
		int n = text.length();
		while (i < n) {
			int lt = text.indexOf('<', i);
			if (lt < 0) {
				break;
			}
			int end;
			int depthChange = 0;
			boolean endTag = false;
			if (text.startsWith("<!--", lt)) {
				end = endOf(text, "-->", lt + 4);
			} else if (text.startsWith("<![CDATA[", lt)) {
				end = endOf(text, "]]>", lt + 9);
			} else if (text.startsWith("<?", lt)) {
				end = endOf(text, "?>", lt + 2);
			} else if (text.startsWith("<!", lt)) {
				end = endOf(text, ">", lt + 2);
			} else {
				int gt = XmlStructure.tagEnd(text, lt + 1);
				if (gt < 0) {
					break; // incomplete tag: keep the rest as is
				}
				end = gt + 1;
				if (lt + 1 < n && text.charAt(lt + 1) == '/') {
					endTag = true;
					depthChange = -1;
				} else if (text.charAt(gt - 1) != '/') {
					depthChange = 1;
				}
			}
			if (end < 0) {
				break;
			}
			String between = text.substring(previousEnd, lt);
			int newline = between.lastIndexOf('\n');
			if (newline >= 0 && between.isBlank()) {
				out.append(between, 0, newline + 1);
				out.append(unit.repeat(Math.max(0, endTag ? depth - 1 : depth)));
			} else {
				out.append(between);
			}
			out.append(text, lt, end);
			depth = Math.max(0, depth + depthChange);
			previousEnd = end;
			i = end;
		}
		out.append(text, previousEnd, n);
		return out.toString();
	}

	private static int endOf(String text, String terminator, int from) {
		int index = text.indexOf(terminator, from);
		return index < 0 ? -1 : index + terminator.length();
	}

	/**
	 * Toggles an XML comment around a block of text (whole lines): removes the comment if the block is one
	 * comment, otherwise wraps it into one.
	 */
	static String toggleComment(String block) {
		String trimmed = block.strip();
		if (trimmed.startsWith("<!--") && trimmed.endsWith("-->") && trimmed.length() >= 7
				&& trimmed.indexOf("-->") == trimmed.length() - 3) {
			int open = block.indexOf("<!--");
			int afterOpen = open + 4;
			if (afterOpen < block.length() && block.charAt(afterOpen) == ' ') {
				afterOpen++;
			}
			int close = block.lastIndexOf("-->");
			int beforeClose = close;
			if (beforeClose > afterOpen && block.charAt(beforeClose - 1) == ' ') {
				beforeClose--;
			}
			return block.substring(0, open) + block.substring(afterOpen, beforeClose) + block.substring(close + 3);
		}
		int first = 0;
		while (first < block.length() && Character.isWhitespace(block.charAt(first))) {
			first++;
		}
		int last = block.length();
		while (last > first && Character.isWhitespace(block.charAt(last - 1))) {
			last--;
		}
		return block.substring(0, first) + "<!-- " + block.substring(first, last) + " -->"
				+ block.substring(last);
	}
}
