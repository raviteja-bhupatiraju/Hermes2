/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Terminology codes in the XML text: which offline terminology a keyword or taxon refers to, and the edits that
 * keep a code and its text together (choosing a MeSH descriptor as keyword text sets {@code hx:id}, choosing a taxon
 * ID fills its entry, and so on).
 */
final class XmlTerminology {

	private XmlTerminology() {
	}

	/** The text content of an element (trimmed), {@code ""} if it has none or isn't closed. */
	static String text(String text, XmlStructure.Element element) {
		if (element == null || element.endTagStart < 0 || element.endTagStart < element.startTagEnd) {
			return "";
		}
		return text.substring(element.startTagEnd, element.endTagStart).strip();
	}

	/** The first child element with a local name, or {@code null}. */
	static XmlStructure.Element child(XmlStructure.Element element, String localName) {
		if (element == null) {
			return null;
		}
		for (XmlStructure.Element child : element.children) {
			if (localName.equals(child.localName())) {
				return child;
			}
		}
		return null;
	}

	/** The value of an attribute (any prefix) in a start tag, or {@code null}. */
	static String attribute(String startTag, String localName) {
		Matcher m = attributePattern(localName).matcher(startTag);
		return m.find() ? m.group(3) : null;
	}

	private static Pattern attributePattern(String localName) {
		// Groups: 1 the qualified name, 2 the quote, 3 the value.
		return Pattern.compile("(?<=\\s)((?:[\\w.-]+:)?" + Pattern.quote(localName) + ")\\s*=\\s*([\"'])(.*?)\\2",
				Pattern.DOTALL);
	}

	/** The start tag of an element. */
	static String startTag(String text, XmlStructure.Element element) {
		return text.substring(element.start, Math.min(text.length(), element.startTagEnd));
	}

	/** The terminology named by a keyword's {@code hx:source}, or {@code null}. */
	static Terminology keywordTerminology(String text, XmlStructure.Element keyword) {
		return Terminologies.forSource(attribute(startTag(text, keyword), "source"));
	}

	/** The terminology named by the source of the taxon path containing a taxon, or {@code null}. */
	static Terminology taxonTerminology(String text, XmlStructure.Element taxon) {
		XmlStructure.Element path = taxon == null ? null : taxon.parent;
		XmlStructure.Element source = child(path, "source");
		return Terminologies.forSource(text(text, child(source, "string")));
	}

	/** Whether an element is a {@code keyword} (of general or of a classification). */
	static boolean isKeyword(XmlStructure.Element element) {
		return element != null && "keyword".equals(element.localName());
	}

	/** Whether an element is a {@code taxon}. */
	static boolean isTaxon(XmlStructure.Element element) {
		return element != null && "taxon".equals(element.localName());
	}

	// ----------------------------------------------------------------------------------------------------
	// Edits applied after a proposal
	// ----------------------------------------------------------------------------------------------------

	/** Replaces a range; returns by how much the text at {@code at} moved. */
	private static int replace(IDocument document, int offset, int length, String replacement, int at)
			throws BadLocationException {
		document.replace(offset, length, replacement);
		return offset < at ? replacement.length() - length : 0;
	}

	/** The element enclosing an offset of the current document, and its ancestor with a local name. */
	private static XmlStructure.Element ancestor(IDocument document, int at, String localName) {
		for (XmlStructure.Element e = XmlStructure.parse(document.get()).enclosing(at); e != null; e = e.parent) {
			if (localName.equals(e.localName())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Sets attributes of the keyword enclosing an offset ({@code hx:source} if missing, {@code hx:id}); returns the
	 * shift of the offset.
	 */
	static int setKeywordCode(IDocument document, int at, Terminology terminology, String code) {
		try {
			XmlStructure.Element keyword = ancestor(document, at, "keyword");
			if (keyword == null) {
				return 0;
			}
			int shift = 0;
			String tag = startTag(document.get(), keyword);
			String prefix = keyword.prefixOf(HealthcarePackage.eNS_URI);
			String declaration = "";
			if (prefix == null || prefix.isEmpty()) {
				prefix = "hx";
				declaration = NLS.bind(Messages.XmlTerminology_XmlnsHx, HealthcarePackage.eNS_URI);
			}
			StringBuilder added = new StringBuilder(declaration);
			String source = attribute(tag, "source");
			if (source == null) {
				added.append(' ').append(prefix).append(":source=\"").append(sourceName(terminology)).append('"');
			}
			Matcher id = attributePattern("id").matcher(tag);
			boolean hasId = id.find();
			if (!hasId) {
				added.append(' ').append(prefix).append(":id=\"").append(code).append('"');
			}
			// Insert at the end of the start tag first: it comes after the id value, so that offset stays valid.
			if (!added.isEmpty()) {
				int close = keyword.start + tag.length() - (tag.endsWith("/>") ? 2 : 1);
				shift += replace(document, close, 0, added.toString(), at);
			}
			if (hasId) {
				shift += replace(document, keyword.start + id.start(3), id.end(3) - id.start(3), code, at);
			}
			return shift;
		} catch (BadLocationException _) {
			return 0;
		}
	}

	/** The value written as source: the id for MeSH and ICD-10-CM, else the name. */
	static String sourceName(Terminology terminology) {
		return terminology == Terminologies.MESH || terminology == Terminologies.ICD_10_CM ? terminology.getId()
				: terminology.getTitle();
	}

	/** Sets the text of the {@code id} of the taxon enclosing an offset; returns the shift of the offset. */
	static int setTaxonId(IDocument document, int at, String code) {
		try {
			XmlStructure.Element taxon = ancestor(document, at, "taxon");
			XmlStructure.Element id = child(taxon, "id");
			if (id == null || id.endTagStart < 0) {
				if (taxon == null) {
					return 0;
				}
				String lom = prefixed(taxon, "");
				return replace(document, taxon.startTagEnd, 0, "<" + lom + "id>" + code + "</" + lom + "id>", at);
			}
			return replace(document, id.startTagEnd, id.endTagStart - id.startTagEnd, code, at);
		} catch (BadLocationException _) {
			return 0;
		}
	}

	/**
	 * Sets the text of the entry of the taxon enclosing an offset (adding the entry after the id if it has none);
	 * returns the shift of the offset.
	 */
	static int setTaxonEntry(IDocument document, int at, String label) {
		try {
			String text = document.get();
			XmlStructure.Element taxon = ancestor(document, at, "taxon");
			if (taxon == null) {
				return 0;
			}
			XmlStructure.Element string = child(child(taxon, "entry"), "string");
			if (string != null && string.endTagStart >= 0) {
				return replace(document, string.startTagEnd, string.endTagStart - string.startTagEnd, escape(label), at);
			}
			XmlStructure.Element id = child(taxon, "id");
			int insertAt = id != null && id.end >= 0 ? id.end : taxon.startTagEnd;
			String indent = indentOf(text, id != null ? id.start : taxon.start);
			String lom = prefixed(taxon, "");
			String entry = "\n" + indent + "<" + lom + "entry>\n" + indent + "  <" + lom + "string language=\""
					+ HlomDocuments.DEFAULT_LANGUAGE + "\">" + escape(label) + "</" + lom + "string>\n" + indent + "</"
					+ lom + "entry>";
			return replace(document, insertAt, 0, entry, at);
		} catch (BadLocationException _) {
			return 0;
		}
	}

	/** The prefix (with colon) of the LOM namespace in scope at an element. */
	private static String prefixed(XmlStructure.Element element, String fallback) {
		String prefix = element.prefixOf(edu.louisiana.caai.lom.model.lom.LomPackage.eNS_URI);
		return prefix == null ? fallback : prefix.isEmpty() ? "" : prefix + ":";
	}

	private static String indentOf(String text, int offset) {
		int lineStart = text.lastIndexOf('\n', offset - 1) + 1;
		int i = lineStart;
		while (i < offset && (text.charAt(i) == ' ' || text.charAt(i) == '\t')) {
			i++;
		}
		return text.substring(lineStart, i);
	}

	/** Escapes text content. */
	static String escape(String value) {
		return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
