/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.XmlOutline;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Value;

/**
 * Replaces the matches of a {@link Query search} in the field values of a document. In XML text only the changed
 * values are rewritten (the layout, comments and the other values stay as they are); JSON documents are written in
 * their canonical form. Values that can't be changed in place (mixed content) or would become invalid for their
 * type (a number, a date) are left alone and reported.
 */
public final class DocumentReplace {

	/**
	 * A changed value.
	 *
	 * @param path   the element path of the value
	 * @param label  the field label
	 * @param before the value before
	 * @param after  the value after
	 */
	public record Change(String path, String label, String before, String after) {
	}

	/**
	 * The result of a replacement.
	 *
	 * @param text    the new text of the document (the old text if nothing changed)
	 * @param changes the changed values
	 * @param skipped the values that matched but were left alone, with the reason
	 */
	public record Result(String text, List<Change> changes, List<String> skipped) {

		public Result {
			changes = List.copyOf(changes);
			skipped = List.copyOf(skipped);
		}
	}

	private DocumentReplace() {
	}

	/**
	 * Replaces the matches in the values of a document.
	 *
	 * @param text        the document text (XML or JSON)
	 * @param uri         the document URI
	 * @param query       what to replace (its pattern; with a regular expression, the replacement may refer to
	 *                    groups as {@code $1})
	 * @param replacement the replacement
	 * @param selected    which values (by element path) to change
	 */
	public static Result replace(String text, URI uri, Query query, String replacement, Predicate<String> selected) {
		Resource resource = DocumentCheck.load(text, uri);
		if (!resource.getErrors().isEmpty() || resource.getContents().isEmpty()) {
			return new Result(text, List.of(), List.of("The document can't be read; repair it first."));
		}
		Pattern pattern = query.pattern();
		String quoted = query.regex() ? replacement : Matcher.quoteReplacement(replacement);
		List<Value> values = new ArrayList<>();
		for (EObject root : resource.getContents()) {
			DocumentSearch.values(root, value -> {
				if (query.includes(value.names(), value.attribute()) && selected.test(value.path())
						&& pattern.matcher(value.text()).find()) {
					values.add(value);
				}
			});
		}
		boolean json = text.stripLeading().startsWith("{");
		List<Change> changes = new ArrayList<>();
		List<String> skipped = new ArrayList<>();
		XmlOutline outline = json ? null : XmlOutline.of(text);
		record Edit(int start, int end, String text) {
		}
		List<Edit> edits = new ArrayList<>();
		for (Value value : values) {
			String after;
			try {
				after = pattern.matcher(value.text()).replaceAll(quoted);
			} catch (RuntimeException e) {
				return new Result(text, List.of(), List.of("Invalid replacement: " + e.getMessage()));
			}
			if (after.equals(value.text())) {
				continue;
			}
			try {
				// Also checks that the new text is a valid value of the field's type.
				value.setter().accept(after);
			} catch (RuntimeException e) {
				skipped.add(value.label() + ": \"" + after + "\" is not a valid value");
				continue;
			}
			if (!json) {
				int[] range = valueRange(text, outline, value);
				if (range == null) {
					skipped.add(value.label() + ": can't be changed in place (edit it in the editor)");
					continue;
				}
				edits.add(new Edit(range[0], range[1], escape(after, value.attribute() != null)));
			}
			changes.add(new Change(value.path(), value.label(), value.text(), after));
		}
		if (changes.isEmpty()) {
			return new Result(text, changes, skipped);
		}
		if (json) {
			try {
				return new Result(HlomResources.toJson(resource), changes, skipped);
			} catch (IOException e) {
				return new Result(text, List.of(), List.of("The document can't be written: " + e.getMessage()));
			}
		}
		StringBuilder out = new StringBuilder(text);
		edits.sort(Comparator.comparingInt(Edit::start).reversed());
		for (Edit edit : edits) {
			out.replace(edit.start(), edit.end(), edit.text());
		}
		return new Result(out.toString(), changes, skipped);
	}

	/**
	 * The offsets of a value in XML text: the content of a leaf element or an attribute value; {@code null} if it
	 * isn't there as plain text.
	 */
	private static int[] valueRange(String text, XmlOutline outline, Value value) {
		String path = value.path();
		String attribute = null;
		int at = path.lastIndexOf("/@");
		if (at >= 0) {
			attribute = path.substring(at + 2);
			attribute = attribute.substring(attribute.indexOf(':') + 1);
			path = path.substring(0, at);
		}
		XmlOutline.Element element = outline.element(ElementPath.parse(path).toString());
		if (element == null) {
			return null;
		}
		int[] range;
		if (attribute != null) {
			String tag = text.substring(element.nameEnd(), element.tagEnd());
			Matcher matcher = Pattern.compile("(?:^|\\s)(?:[\\w.-]+:)?" + Pattern.quote(attribute) + "\\s*=\\s*(['\"])(.*?)\\1",
					Pattern.DOTALL).matcher(tag);
			if (!matcher.find()) {
				return null;
			}
			range = new int[] { element.nameEnd() + matcher.start(2), element.nameEnd() + matcher.end(2) };
		} else {
			int close = text.lastIndexOf("</", element.end() - 1);
			if (close < element.tagEnd()) {
				return null;
			}
			range = new int[] { element.tagEnd(), close };
			String content = text.substring(range[0], range[1]);
			if (content.contains("<") && !(content.strip().startsWith("<![CDATA[") && content.strip().endsWith("]]>")
					&& content.indexOf("<![CDATA[", content.indexOf("<![CDATA[") + 1) < 0)) {
				return null;
			}
		}
		String written = unescape(text.substring(range[0], range[1]));
		return written.strip().equals(value.text().strip()) ? range : null;
	}

	private static String escape(String text, boolean attribute) {
		String escaped = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		return attribute ? escaped.replace("\"", "&quot;").replace("'", "&apos;") : escaped;
	}

	private static String unescape(String text) {
		String content = text.strip();
		if (content.startsWith("<![CDATA[") && content.endsWith("]]>")) {
			return content.substring(9, content.length() - 3);
		}
		Matcher matcher = Pattern.compile("&(#x[0-9a-fA-F]+|#[0-9]+|lt|gt|amp|quot|apos);").matcher(text);
		StringBuilder out = new StringBuilder();
		while (matcher.find()) {
			String entity = matcher.group(1);
			String replacement = switch (entity) {
				case "lt" -> "<";
				case "gt" -> ">";
				case "amp" -> "&";
				case "quot" -> "\"";
				case "apos" -> "'";
				default -> Character.toString(entity.startsWith("#x") ? Integer.parseInt(entity.substring(2), 16)
						: Integer.parseInt(entity.substring(1)));
			};
			matcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		matcher.appendTail(out);
		return out.toString();
	}
}
