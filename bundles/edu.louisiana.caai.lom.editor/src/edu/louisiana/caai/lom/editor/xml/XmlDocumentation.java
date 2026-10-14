/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EStructuralFeature;

import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * The documentation shown when hovering over the XML text: for element and attribute names the help of the
 * specification, the occurrence and the allowed values; for terminology codes (keyword {@code hx:id}, taxon
 * ids) and the text of coded keywords the concept with its definition; for language tags the language.
 */
final class XmlDocumentation {

	private static final Pattern ATTRIBUTE = Pattern.compile("([\\w.:-]+)\\s*=\\s*([\"'])(.*?)\\2", Pattern.DOTALL);
	private static final int MAX_VALUES = 12;

	private XmlDocumentation() {
	}

	/** The documentation (HTML) for an offset of a text, or {@code null}. */
	static String at(String text, int offset) {
		XmlStructure structure = XmlStructure.parse(text);
		XmlStructure.Element named = structure.elementAtTagName(offset);
		if (named != null) {
			return element(named);
		}
		XmlStructure.Element element = structure.enclosing(offset);
		if (element == null) {
			return null;
		}
		if (offset < element.startTagEnd) {
			return attribute(text, element, offset);
		}
		if (element.endTagStart >= 0 && offset <= element.endTagStart) {
			return content(text, element);
		}
		return null;
	}

	private static String element(XmlStructure.Element element) {
		StringBuilder html = new StringBuilder("<b>").append(escape(element.name)).append("</b>");
		XmlContentModel model = XmlContentModel.of(element);
		XmlContentModel parent = element.parent == null ? XmlContentModel.document() : XmlContentModel.of(element.parent);
		if (model != null && model.feature != null) {
			html.append(Messages.XmlDocumentation_NbspI).append(occurrence(model.feature)).append("</i>");
			if (HealthcarePackage.eNS_URI.equals(XmlContentModel.namespace(model.feature))) {
				html.append(Messages.XmlDocumentation_HealthcareExtension);
			}
			if (parent != null && parent.isOrdered() && element.parent != null) {
				html.append(Messages.XmlDocumentation_InSchemaOrder);
			}
		} else {
			html.append(Messages.XmlDocumentation_NbspINotDefinedBy).append(escape(XmlContentModel.type().label()))
					.append(" schemas</i>");
		}
		String help = XmlContentModel.type() == edu.louisiana.caai.lom.model.support.type.HlomDocumentType.INSTANCE
				? FormSupport.help(element.localName())
				: XmlContentModel.type().fieldHelp(java.util.List.of(element.localName()));
		if (help != null) {
			html.append("<br>").append(escape(help));
		}
		if (model != null) {
			List<Vocabularies.Term> values = XmlContentModel.enumeratedValues(model.contentType());
			if (!values.isEmpty()) {
				html.append(Messages.XmlDocumentation_BrBrBValuesB).append(values(values));
			} else if (model.isVocabulary()) {
				XmlContentModel value = model.child(edu.louisiana.caai.lom.model.lom.LomPackage.eNS_URI, "value");
				if (value != null) {
					html.append(Messages.XmlDocumentation_BrBrBValuesB).append(values(XmlContentModel.enumeratedValues(value.contentType())));
				}
			}
		}
		return html.toString();
	}

	private static String occurrence(EStructuralFeature feature) {
		boolean single = XmlContentModel.isSingle(feature);
		boolean required = feature.getLowerBound() > 0;
		return required ? (single ? "required, once" : "required, repeatable") : single ? "optional, at most once"
				: "optional, repeatable";
	}

	private static String values(List<Vocabularies.Term> terms) {
		StringBuilder list = new StringBuilder();
		for (int i = 0; i < terms.size() && i < MAX_VALUES; i++) {
			list.append(i == 0 ? "" : ", ").append(escape(terms.get(i).value()));
		}
		if (terms.size() > MAX_VALUES) {
			list.append(", … (").append(terms.size()).append(Messages.XmlDocumentation_InAllCtrlSpaceLists);
		}
		return list.toString();
	}

	private static String attribute(String text, XmlStructure.Element element, int offset) {
		String tag = XmlTerminology.startTag(text, element);
		Matcher m = ATTRIBUTE.matcher(tag);
		int nameEnd = 1 + element.name.length();
		while (m.find()) {
			if (m.start() < nameEnd) {
				continue;
			}
			int start = element.start + m.start();
			int valueStart = element.start + m.start(3);
			int valueEnd = element.start + m.end(3);
			String qname = m.group(1);
			String local = qname.substring(qname.indexOf(':') + 1);
			if (offset >= start && offset < valueStart - 1) {
				if (qname.startsWith("xmlns")) {
					return "<b>" + escape(qname) + "</b><br>Declares a namespace prefix.";
				}
				String help = FormSupport.help(XmlTerminology.isKeyword(element) ? "keyword" : local);
				return "<b>" + escape(qname) + "</b> attribute" + (help == null ? "" : "<br>" + escape(help));
			}
			if (offset >= valueStart && offset <= valueEnd) {
				String value = m.group(3).strip();
				if ("language".equals(local)) {
					return language(value);
				}
				if (XmlTerminology.isKeyword(element) && "id".equals(local)) {
					return concept(XmlTerminology.keywordTerminology(text, element), value);
				}
				if (XmlTerminology.isKeyword(element) && "source".equals(local)) {
					Terminology terminology = Terminologies.forSource(value);
					return terminology == null ? null
							: "<b>" + escape(terminology.getTitle()) + "</b> " + escape(terminology.getVersion())
									+ "<br>Codes in hx:id are looked up offline.";
				}
				return null;
			}
		}
		return null;
	}

	private static String content(String text, XmlStructure.Element element) {
		String value = XmlTerminology.text(text, element);
		String local = element.localName();
		XmlStructure.Element parent = element.parent;
		if (value.isEmpty()) {
			return null;
		}
		if ("language".equals(local)) {
			return language(value);
		}
		if ("string".equals(local) && XmlTerminology.isKeyword(parent)) {
			String id = XmlTerminology.attribute(XmlTerminology.startTag(text, parent), "id");
			return id == null ? null : concept(XmlTerminology.keywordTerminology(text, parent), id);
		}
		if ("id".equals(local) && XmlTerminology.isTaxon(parent)) {
			return concept(XmlTerminology.taxonTerminology(text, parent), value);
		}
		if ("string".equals(local) && parent != null && "entry".equals(parent.localName())
				&& XmlTerminology.isTaxon(parent.parent)) {
			String id = XmlTerminology.text(text, XmlTerminology.child(parent.parent, "id"));
			return id.isEmpty() ? null : concept(XmlTerminology.taxonTerminology(text, parent.parent), id);
		}
		return null;
	}

	private static String concept(Terminology terminology, String code) {
		if (terminology == null || code == null || code.isBlank()) {
			return null;
		}
		Concept concept = terminology.get(code.strip());
		if (concept == null) {
			return "<b>" + escape(code) + "</b> is not a code of " + escape(terminology.getTitle()) + " "
					+ escape(terminology.getVersion()) + ".";
		}
		String info = escape(XmlContentAssistProcessor.conceptInfo(terminology, concept)).replace("\n", "<br>");
		int firstBreak = info.indexOf("<br>");
		return firstBreak < 0 ? "<b>" + info + "</b>" : "<b>" + info.substring(0, firstBreak) + "</b>" + info.substring(firstBreak);
	}

	private static String language(String tag) {
		Locale locale = Locale.forLanguageTag(tag);
		String name = locale.getDisplayName(Locale.ENGLISH);
		return name.isEmpty() ? null : "<b>" + escape(tag) + "</b>: " + escape(name);
	}

	private static String escape(String text) {
		return XmlProblemHover.escape(text);
	}
}
