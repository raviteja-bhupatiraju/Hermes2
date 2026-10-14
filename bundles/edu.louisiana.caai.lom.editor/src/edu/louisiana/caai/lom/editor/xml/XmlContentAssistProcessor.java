/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Schema driven content assist of the XML page. The open elements before the caret are found by a lightweight
 * scan ({@link XmlStructure}) and mapped to the model ({@link XmlContentModel}) to propose:
 * <ul>
 * <li>the child elements allowed in the current element (with the namespace prefix in scope, declaring it if
 * needed), inserted with their end tag and, for vocabularies and language strings, a skeleton;</li>
 * <li>the end tag of the current element;</li>
 * <li>vocabulary terms in {@code <value>} (updating the sibling {@code <source>}), the sources in
 * {@code <source>} and the enumerated values of the healthcare elements;</li>
 * <li>language tags in {@code language="..."} and {@code <language>}, and the values recommended by the
 * specification for some free-text elements;</li>
 * <li>attribute names inside a start tag.</li>
 * </ul>
 */
final class XmlContentAssistProcessor implements IContentAssistProcessor {

	private static final char[] ACTIVATION_CHARACTERS = { '<', '"', '/' };

	/** The maximum number of terminology concepts proposed. */
	private static final int CONCEPT_LIMIT = 40;

	private static final Map<String, Supplier<List<String>>> SUGGESTIONS = Map.of(
			"format", () -> HlomSuggestions.MIME_TYPES,
			"metadataSchema", () -> HlomSuggestions.METADATA_SCHEMAS,
			"accreditingBody", () -> HlomSuggestions.ACCREDITING_BODIES,
			"activityCertification", () -> HlomSuggestions.ACTIVITY_CERTIFICATIONS,
			"readingLevel", () -> HlomSuggestions.READING_LEVELS,
			"profession", HlomSuggestions::professionTerms,
			"specialty", () -> HlomSuggestions.SPECIALTIES);

	private String errorMessage;

	/** The type of the edited document. */
	private final Supplier<DocumentType> type;

	XmlContentAssistProcessor(Supplier<DocumentType> type) {
		this.type = type;
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		errorMessage = null;
		String text = viewer.getDocument().get();
		List<XmlCompletionProposal> proposals = ScopedValue.where(XmlContentModel.TYPE, type.get())
				.call(() -> compute(text, offset));
		if (proposals.isEmpty()) {
			errorMessage = Messages.XmlContentAssistProcessor_NoProposalsAtThisPosition;
		}
		return proposals.toArray(ICompletionProposal[]::new);
	}

	/** Computes the proposals for a caret offset in a text. */
	static List<XmlCompletionProposal> compute(String text, int offset) {
		HlomResources.initStandalone();
		String before = text.substring(0, offset);
		XmlStructure structure = XmlStructure.parse(before);
		if (structure.inSpecial) {
			return Collections.emptyList();
		}
		Context context = new Context(text, offset, structure.open, detectIndentUnit(text), XmlStructure.parse(text));
		if (structure.partialTag >= 0) {
			String tag = before.substring(structure.partialTag);
			if (tag.startsWith("</")) {
				context.endTag(structure.partialTag);
			} else if (tag.startsWith("<!") || tag.startsWith("<?")) {
				return Collections.emptyList();
			} else if (XmlStructure.name(tag, 1).length() == tag.length() - 1) {
				context.elements(structure.partialTag, tag.substring(1), structure.roots.isEmpty());
			} else {
				context.insideTag(structure.partialTag, tag);
			}
		} else {
			int contentStart = before.lastIndexOf('>') + 1;
			context.content(contentStart, structure.roots.isEmpty());
		}
		Collections.sort(context.proposals);
		return context.proposals;
	}

	/** The indentation unit used in a text: the smallest indentation of a line starting with a tag. */
	static String detectIndentUnit(String text) {
		String unit = null;
		int lineStart = 0;
		while (lineStart < text.length()) {
			int i = lineStart;
			while (i < text.length() && (text.charAt(i) == ' ' || text.charAt(i) == '\t')) {
				i++;
			}
			if (i > lineStart && i < text.length() && text.charAt(i) == '<'
					&& (unit == null || i - lineStart < unit.length())) {
				unit = text.substring(lineStart, i);
			}
			int newline = text.indexOf('\n', lineStart);
			lineStart = newline < 0 ? text.length() : newline + 1;
		}
		return unit == null ? " ".repeat(XmlSourceViewerConfiguration.INDENT_WIDTH) : unit;
	}

	/** The proposal computation for one invocation. */
	private static final class Context {

		final String text;
		final int offset;
		final XmlStructure.Element open;
		final String unit;
		final List<XmlCompletionProposal> proposals = new ArrayList<>();
		/** The structure of the whole text (also after the caret). */
		final XmlStructure full;

		Context(String text, int offset, XmlStructure.Element open, String unit, XmlStructure full) {
			this.text = text;
			this.offset = offset;
			this.open = open;
			this.unit = unit;
			this.full = full;
		}

		/** The element of the whole text that starts where an element of the text before the caret starts. */
		XmlStructure.Element counterpart(XmlStructure.Element element) {
			if (element == null) {
				return null;
			}
			for (XmlStructure.Element candidate : full.elements) {
				if (candidate.start == element.start) {
					return candidate;
				}
			}
			return null;
		}

		/**
		 * Whether a child element can be inserted at {@code at}: an element that may occur once isn't proposed
		 * again, and in a sequence only the elements fitting between the previous and the next sibling are.
		 */
		boolean allowedHere(XmlContentModel model, EStructuralFeature feature, int index, int at) {
			XmlStructure.Element container = counterpart(open);
			if (container == null) {
				return true;
			}
			String namespace = XmlContentModel.namespace(feature);
			String local = XmlContentModel.name(feature);
			boolean single = XmlContentModel.isSingle(feature);
			boolean ordered = model.isOrdered();
			int lower = -1;
			int upper = Integer.MAX_VALUE;
			for (XmlStructure.Element sibling : container.children) {
				String siblingNamespace = sibling.namespace() == null ? "" : sibling.namespace();
				if (single && local.equals(sibling.localName()) && namespace.equals(siblingNamespace)) {
					return false;
				}
				if (ordered) {
					int siblingIndex = model.indexOf(siblingNamespace, sibling.localName());
					if (siblingIndex >= 0 && sibling.start < at) {
						lower = Math.max(lower, siblingIndex);
					} else if (siblingIndex >= 0 && sibling.start >= offset) {
						upper = Math.min(upper, siblingIndex);
					}
				}
			}
			return index >= lower && index <= upper;
		}

		/** The number of name characters directly after the caret. */
		int nameSuffix() {
			int i = offset;
			while (i < text.length() && XmlStructure.isNameChar(text.charAt(i))) {
				i++;
			}
			return i - offset;
		}

		/** The leading whitespace of the line containing an offset. */
		String indentOf(int at) {
			int lineStart = text.lastIndexOf('\n', at - 1) + 1;
			int i = lineStart;
			while (i < text.length() && (text.charAt(i) == ' ' || text.charAt(i) == '\t')) {
				i++;
			}
			return text.substring(lineStart, Math.min(i, Math.max(lineStart, at)));
		}

		/** Proposes the end tag of the open element, replacing from {@code start} (a {@code </}). */
		void endTag(int start) {
			if (open == null) {
				return;
			}
			int suffix = nameSuffix();
			if (offset + suffix < text.length() && text.charAt(offset + suffix) == '>') {
				suffix++;
			}
			String endTag = "</" + open.name + ">";
			proposals.add(new XmlCompletionProposal(endTag, Messages.XmlContentAssistProcessor_EndTag, XmlImages.Kind.END_TAG, start, offset, suffix,
					endTag, endTag.length(), null, 0, endTag));
		}

		/** Text content: values of the element, or child elements and the end tag. */
		void content(int contentStart, boolean noRoot) {
			String typed = text.substring(contentStart, offset);
			XmlContentModel model = open == null ? XmlContentModel.document() : XmlContentModel.of(open);
			String trimmed = typed.stripLeading();
			int valueStart = offset - trimmed.length();
			if (model != null && open != null && !model.hasChildElements()) {
				values(model, valueStart);
			}
			String name = trailingName(typed);
			if (name.length() == trimmed.length()) {
				elements(offset - name.length(), name, noRoot);
			}
			if (open != null && (trimmed.isEmpty() || !proposals.isEmpty() || name.length() == trimmed.length())) {
				String endTag = "</" + open.name + ">";
				proposals.add(new XmlCompletionProposal(endTag, Messages.XmlContentAssistProcessor_EndTag, XmlImages.Kind.END_TAG, offset, offset, 0,
						endTag, endTag.length(), null, 10_000, ""));
			}
		}

		private static String trailingName(String typed) {
			int i = typed.length();
			while (i > 0 && XmlStructure.isNameChar(typed.charAt(i - 1))) {
				i--;
			}
			return typed.substring(i);
		}

		/** Child element proposals, replacing from {@code start} (a {@code <} or the typed name). */
		void elements(int start, String typed, boolean noRoot) {
			XmlContentModel model = open == null ? XmlContentModel.document() : XmlContentModel.of(open);
			if (model == null || (open == null && !noRoot)) {
				return;
			}
			boolean bracket = start < text.length() && text.charAt(start) == '<';
			int suffix = nameSuffix();
			String indent = indentOf(start);
			if (open == null && XmlContentModel.type() != HlomDocumentType.INSTANCE) {
				DocumentType type = XmlContentModel.type();
				String prefix = XmlContentModel.prefix(type.namespace(), "m");
				String qname = prefix + ":" + type.rootElement();
				String head = "<" + qname + " xmlns:" + prefix + "=\"" + type.namespace() + "\"\n" + unit
						+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + unit + "xsi:schemaLocation=\""
						+ type.namespace() + " " + type.schemaLocation() + "\">\n" + indent + unit;
				add(new XmlCompletionProposal(type.rootElement(), type.label() + " document", XmlImages.Kind.ELEMENT,
						start, offset, suffix, head + "\n" + indent + "</" + qname + ">", head.length(),
						NLS.bind(Messages.XmlContentAssistProcessor_TheRootElementOfA, type.label()), 0, "<" + qname, type.rootElement()), typed);
				return;
			}
			if (open == null) {
				String lom = NLS.bind(Messages.XmlContentAssistProcessor_LomXmlnsXmlnsXsiHttp, new Object[] { LomPackage.eNS_URI, unit, unit, HealthcarePackage.eNS_URI, unit, AddressPackage.eNS_URI, unit, LomPackage.eNS_URI, HlomResources.HLOM_SCHEMA_URL, indent, unit });
				String replacement = lom + "\n" + indent + "</lom>";
				add(new XmlCompletionProposal("lom", Messages.XmlContentAssistProcessor_HLOMDocument, XmlImages.Kind.ELEMENT, start, offset, suffix,
						replacement, lom.length(), Messages.XmlContentAssistProcessor_TheRootElementOfA2, 0, "<lom", "lom"),
						typed);
				return;
			}
			int rank = 0;
			int index = -1;
			for (EStructuralFeature feature : model.childElements()) {
				index++;
				if (!allowedHere(model, feature, index, start)) {
					continue;
				}
				String namespace = XmlContentModel.namespace(feature);
				String local = XmlContentModel.name(feature);
				String prefix = open.prefixOf(namespace);
				String declaration = "";
				if (prefix == null) {
					prefix = XmlContentModel.prefix(namespace, "ns");
					declaration = " xmlns" + (prefix.isEmpty() ? "" : ":" + prefix) + "=\"" + namespace + "\"";
				}
				String qname = prefix.isEmpty() ? local : prefix + ":" + local;
				XmlContentModel child = model.child(namespace, local);
				String lomPrefix = prefixFor(LomPackage.eNS_URI, namespace, prefix);
				String startTag = "<" + qname + declaration + ">";
				String endTag = "</" + qname + ">";
				String head;
				String tail;
				boolean retrigger = false;
				String qualifier = null;
				if (child != null && child.isVocabulary()) {
					head = startTag + "\n" + indent + unit + "<" + lomPrefix + "source>" + Vocabularies.LOM_SOURCE + "</"
							+ lomPrefix + "source>\n" + indent + unit + "<" + lomPrefix + "value>";
					tail = "</" + lomPrefix + "value>\n" + indent + endTag;
					retrigger = true;
					qualifier = "vocabulary";
				} else if (child != null && child.isLangStringContainer()) {
					head = startTag + "\n" + indent + unit + "<" + lomPrefix + "string language=\""
							+ HlomDocuments.DEFAULT_LANGUAGE + "\">";
					tail = "</" + lomPrefix + "string>\n" + indent + endTag;
					qualifier = "text";
				} else if (child != null && child.hasChildElements()) {
					head = startTag + "\n" + indent + unit;
					tail = "\n" + indent + endTag;
				} else {
					head = startTag;
					tail = endTag;
					retrigger = child != null && (!XmlContentModel.enumeratedValues(child.contentType()).isEmpty()
							|| "language".equals(local) || SUGGESTIONS.containsKey(local));
				}
				XmlImages.Kind kind = LomPackage.eNS_URI.equals(namespace) ? XmlImages.Kind.ELEMENT
						: XmlImages.Kind.HEALTHCARE_ELEMENT;
				XmlCompletionProposal proposal = new XmlCompletionProposal(qname, qualifier, kind, start, offset,
						suffix, head + tail, head.length(), FormSupport.help(local), rank++, "<" + qname, "<" + local,
						qname, local);
				add(retrigger ? proposal.retrigger() : proposal, bracket ? "<" + typed : typed);
			}
		}

		/** The prefix (with colon) to use for a namespace, given a prefix just chosen for another one. */
		private String prefixFor(String namespace, String chosenNamespace, String chosenPrefix) {
			String prefix = namespace.equals(chosenNamespace) ? chosenPrefix : open.prefixOf(namespace);
			if (prefix == null) {
				prefix = XmlContentModel.prefix(namespace, "");
			}
			return prefix.isEmpty() ? "" : prefix + ":";
		}

		/** Values of the open element's text content, replacing from {@code start} to the end of the value. */
		void values(XmlContentModel model, int start) {
			int end = offset;
			while (end < text.length() && text.charAt(end) != '<' && text.charAt(end) != '\n') {
				end++;
			}
			while (end > offset && Character.isWhitespace(text.charAt(end - 1))) {
				end--;
			}
			String typed = text.substring(start, offset);
			String local = open.localName();
			XmlStructure.Element parent = open.parent;
			if (terminologyValues(start, end, typed)) {
				return;
			}
			boolean vocabularyValue = "value".equals(local) && parent != null
					&& XmlContentModel.of(parent) != null && XmlContentModel.of(parent).isVocabulary();
			int rank = 0;
			for (Vocabularies.Term term : XmlContentModel.enumeratedValues(model.contentType())) {
				boolean healthcare = Vocabularies.HEALTHCARE_SOURCE.equals(term.source());
				XmlImages.Kind kind;
				String qualifier = null;
				if ("source".equals(local)) {
					kind = XmlImages.Kind.SOURCE;
				} else if (vocabularyValue) {
					kind = healthcare ? XmlImages.Kind.HEALTHCARE_TERM : XmlImages.Kind.TERM;
					qualifier = healthcare ? "Healthcare" : "LOM";
				} else {
					kind = HealthcarePackage.eNS_URI.equals(open.namespace()) ? XmlImages.Kind.HEALTHCARE_TERM
							: XmlImages.Kind.TERM;
				}
				XmlCompletionProposal proposal = new XmlCompletionProposal(term.value(), qualifier, kind, start, offset,
						end - offset, term.value(), term.value().length(), null, rank++, term.value());
				if (vocabularyValue && term.source() != null) {
					proposal.afterApply((document, at) -> updateSource(document, at, term.source()));
				}
				add(proposal, typed);
			}
			if (!proposals.isEmpty()) {
				return;
			}
			String suggestionKey = "string".equals(local) && parent != null ? parent.localName() : local;
			if ("language".equals(local)) {
				languages(start, offset, end - offset, "");
			} else if (SUGGESTIONS.containsKey(suggestionKey)) {
				for (String value : SUGGESTIONS.get(suggestionKey).get()) {
					add(new XmlCompletionProposal(value, null, XmlImages.Kind.SUGGESTION, start, offset, end - offset,
							value, value.length(), null, rank++, value), typed);
				}
			}
		}

		/**
		 * Terminology concepts as text content: keyword text (sets {@code hx:id}), taxon path sources, taxon ids
		 * (fill the entry) and taxon entries (fill the id).
		 *
		 * @return whether the position is one of these (no other proposals are added then)
		 */
		boolean terminologyValues(int start, int end, String typed) {
			String local = open.localName();
			XmlStructure.Element parent = counterpart(open.parent);
			if ("string".equals(local) && XmlTerminology.isKeyword(parent)) {
				Terminology terminology = XmlTerminology.keywordTerminology(text, parent);
				String source = XmlTerminology.attribute(XmlTerminology.startTag(text, parent), "source");
				if (terminology == null && source != null && !source.isBlank()) {
					return false; // another terminology (e.g. SNOMED CT): free text
				}
				Terminology used = terminology != null ? terminology : Terminologies.MESH;
				if (typed.strip().length() < 2) {
					return false;
				}
				int rank = 0;
				for (Terminology.Match match : used.search(typed, CONCEPT_LIMIT)) {
					Concept concept = match.concept();
					String qualifier = used.getId() + " " + concept.code()
							+ (match.matchedName().equals(concept.label()) ? "" : " · " + match.matchedName());
					String label = XmlTerminology.escape(concept.label());
					proposals.add(new XmlCompletionProposal(concept.label(), qualifier, XmlImages.Kind.CONCEPT, start,
							offset, end - offset, label, label.length(), conceptInfo(used, concept), rank++,
							concept.label(), match.matchedName(), concept.code()).matchWords()
							.afterApply((document, at) -> XmlTerminology.setKeywordCode(document, at, used, concept.code())));
				}
				return !proposals.isEmpty();
			}
			if ("string".equals(local) && parent != null && "source".equals(parent.localName()) && parent.parent != null
					&& "taxonPath".equals(parent.parent.localName())) {
				List<String> names = new ArrayList<>();
				for (Terminology terminology : Terminologies.all()) {
					names.add(XmlTerminology.sourceName(terminology));
				}
				for (String name : HlomSuggestions.TAXONOMIES) {
					if (!names.contains(name)) {
						names.add(name);
					}
				}
				int rank = 0;
				for (String name : names) {
					Terminology terminology = Terminologies.forSource(name);
					add(new XmlCompletionProposal(name, terminology == null ? null : "offline",
							terminology == null ? XmlImages.Kind.SUGGESTION : XmlImages.Kind.CONCEPT, start, offset,
							end - offset, name, name.length(), terminology == null ? null : terminology.getTitle(), rank++,
							name), typed);
				}
				return true;
			}
			XmlStructure.Element taxon = "id".equals(local) ? parent
					: "string".equals(local) && parent != null && "entry".equals(parent.localName()) ? parent.parent : null;
			if (XmlTerminology.isTaxon(taxon)) {
				Terminology terminology = XmlTerminology.taxonTerminology(text, taxon);
				if (terminology == null) {
					return false;
				}
				boolean id = "id".equals(local);
				List<Concept> concepts = new ArrayList<>();
				if (typed.isBlank()) {
					// Taxa go from general to specific: propose the narrower concepts of the previous taxon.
					String previous = previousTaxonId(taxon);
					Concept broader = previous == null ? null : terminology.get(previous);
					concepts.addAll(broader == null ? terminology.roots() : terminology.children(broader.code()));
				} else {
					terminology.search(typed, CONCEPT_LIMIT).forEach(match -> concepts.add(match.concept()));
				}
				int rank = 0;
				for (Concept concept : concepts.subList(0, Math.min(concepts.size(), CONCEPT_LIMIT))) {
					String replacement = id ? concept.code() : XmlTerminology.escape(concept.label());
					XmlCompletionProposal proposal = new XmlCompletionProposal(id ? concept.code() : concept.label(),
							id ? concept.label() : terminology.getId() + " " + concept.code(), XmlImages.Kind.CONCEPT, start,
							offset, end - offset, replacement, replacement.length(), conceptInfo(terminology, concept),
							rank++, concept.code(), concept.label()).matchWords();
					proposal.afterApply(id ? (document, at) -> XmlTerminology.setTaxonEntry(document, at, concept.label())
							: (document, at) -> XmlTerminology.setTaxonId(document, at, concept.code()));
					proposals.add(proposal);
				}
				return true;
			}
			return false;
		}

		/** The id of the taxon before a taxon in its path, or {@code null}. */
		private String previousTaxonId(XmlStructure.Element taxon) {
			XmlStructure.Element path = taxon.parent;
			XmlStructure.Element previous = null;
			for (XmlStructure.Element sibling : path == null ? List.<XmlStructure.Element>of() : path.children) {
				if (sibling == taxon || sibling.start >= taxon.start) {
					break;
				}
				if (XmlTerminology.isTaxon(sibling)) {
					previous = sibling;
				}
			}
			String id = XmlTerminology.text(text, XmlTerminology.child(previous, "id"));
			return id.isEmpty() ? null : id;
		}

		/** Proposes language tags. */
		void languages(int start, int invocation, int suffix, String closing) {
			String typed = text.substring(start, invocation);
			int rank = 0;
			for (String tag : HlomSuggestions.LANGUAGES) {
				String name = Locale.forLanguageTag(tag).getDisplayName(Locale.ENGLISH);
				add(new XmlCompletionProposal(tag, name, XmlImages.Kind.LANGUAGE, start, invocation, suffix,
						tag + closing, tag.length() + closing.length(), null, rank++, tag, name), typed);
			}
		}

		/** Inside a start tag: attribute values or attribute names. */
		void insideTag(int tagStart, String tag) {
			String tagName = XmlStructure.name(tag, 1);
			XmlContentModel parentModel = open == null ? XmlContentModel.document() : XmlContentModel.of(open);
			int colon = tagName.indexOf(':');
			String prefix = colon < 0 ? "" : tagName.substring(0, colon);
			String namespace = open == null ? null : open.namespaceOf(prefix);
			XmlContentModel model = parentModel == null ? null
					: parentModel.child(namespace == null ? LomPackage.eNS_URI : namespace, tagName.substring(colon + 1));
			// find an unterminated attribute value
			char quote = 0;
			int valueStart = -1;
			for (int i = 1 + tagName.length(); i < tag.length(); i++) {
				char c = tag.charAt(i);
				if (quote != 0) {
					if (c == quote) {
						quote = 0;
					}
				} else if (c == '"' || c == '\'') {
					quote = c;
					valueStart = i + 1;
				}
			}
			if (quote != 0) {
				int eq = tag.lastIndexOf('=', valueStart - 1);
				String attribute = trailingName(tag.substring(0, Math.max(0, eq)).stripTrailing());
				attributeValues(model, attribute, tagStart + valueStart, quote);
			} else {
				String name = trailingName(tag);
				int nameStart = tag.length() - name.length();
				if (nameStart > 1 + tagName.length() && Character.isWhitespace(tag.charAt(nameStart - 1))) {
					attributes(model, tag, offset - name.length(), name);
				}
			}
		}

		private void attributeValues(XmlContentModel model, String attribute, int start, char quote) {
			int end = offset;
			while (end < text.length() && text.charAt(end) != quote && text.charAt(end) != '<'
					&& text.charAt(end) != '>' && text.charAt(end) != '\n') {
				end++;
			}
			boolean closed = end < text.length() && text.charAt(end) == quote;
			String closing = closed ? "" : String.valueOf(quote);
			String local = attribute.substring(attribute.indexOf(':') + 1);
			if ("language".equals(local) || "xml:lang".equals(attribute)) {
				languages(start, offset, end - offset, closing);
				return;
			}
			String typed = text.substring(start, offset);
			List<String> values = new ArrayList<>();
			if (model != null) {
				for (EStructuralFeature feature : model.attributes()) {
					if (local.equals(XmlContentModel.name(feature)) && feature instanceof EAttribute a) {
						EDataType type = a.getEAttributeType();
						XmlContentModel.enumeratedValues(type).forEach(term -> values.add(term.value()));
					}
				}
			}
			if (values.isEmpty() && "source".equals(local)) {
				values.addAll(HlomSuggestions.KEYWORD_SOURCES);
			}
			if (values.isEmpty() && "id".equals(local) && keywordCodes(start, end, closing)) {
				return;
			}
			int rank = 0;
			for (String value : values) {
				add(new XmlCompletionProposal(value, null, XmlImages.Kind.SUGGESTION, start, offset, end - offset,
						value + closing, value.length() + closing.length(), null, rank++, value), typed);
			}
		}

		/**
		 * Codes of the keyword's terminology for {@code hx:id}: matching the typed code or name, or (with nothing
		 * typed) the keyword's text.
		 */
		private boolean keywordCodes(int start, int end, String closing) {
			XmlStructure.Element keyword = null;
			for (XmlStructure.Element candidate : full.elements) {
				if (candidate.start < start && start < candidate.startTagEnd) {
					keyword = candidate;
				}
			}
			if (!XmlTerminology.isKeyword(keyword)) {
				return false;
			}
			Terminology terminology = XmlTerminology.keywordTerminology(text, keyword);
			if (terminology == null) {
				return false;
			}
			String typed = text.substring(start, offset);
			String query = typed.isBlank() ? XmlTerminology.text(text, XmlTerminology.child(keyword, "string")) : typed;
			if (query.isBlank()) {
				return false;
			}
			int rank = 0;
			for (Terminology.Match match : terminology.search(query, CONCEPT_LIMIT)) {
				Concept concept = match.concept();
				String replacement = concept.code() + closing;
				proposals.add(new XmlCompletionProposal(concept.code(), concept.label(), XmlImages.Kind.CONCEPT, start,
						offset, end - offset, replacement, replacement.length(), conceptInfo(terminology, concept), rank++,
						concept.code(), concept.label(), match.matchedName()).matchWords());
			}
			return true;
		}

		private void attributes(XmlContentModel model, String tag, int start, String typed) {
			if (model == null) {
				return;
			}
			int rank = 0;
			for (EStructuralFeature feature : model.attributes()) {
				String namespace = XmlContentModel.namespace(feature);
				String local = XmlContentModel.name(feature);
				String qname = local;
				if (!namespace.isEmpty()) {
					String prefix = open == null ? null : open.prefixOf(namespace);
					if (prefix == null) {
						prefix = XmlContentModel.prefix(namespace, "ns");
					}
					qname = prefix.isEmpty() ? local : prefix + ":" + local;
				}
				if (tag.matches("(?s).*\\s" + java.util.regex.Pattern.quote(qname) + "\\s*=.*")) {
					continue;
				}
				String replacement = qname + "=\"\"";
				add(new XmlCompletionProposal(qname, "attribute", XmlImages.Kind.ATTRIBUTE, start, offset, nameSuffix(),
						replacement, replacement.length() - 1, FormSupport.help(local), rank++, qname, local).retrigger(),
						typed);
			}
		}

		private void add(XmlCompletionProposal proposal, String typed) {
			if (proposal.matches(typed)) {
				proposals.add(proposal);
			}
		}

		/** Sets the {@code <source>} sibling of the value element at an offset; returns the offset shift. */
		private static int updateSource(IDocument document, int at, String source) {
			XmlStructure structure = XmlStructure.parse(document.get());
			XmlStructure.Element value = structure.enclosing(at);
			if (value == null || value.parent == null) {
				return 0;
			}
			for (XmlStructure.Element sibling : value.parent.children) {
				if ("source".equals(sibling.localName()) && sibling.endTagStart > sibling.startTagEnd - 1) {
					try {
						int length = sibling.endTagStart - sibling.startTagEnd;
						String current = document.get(sibling.startTagEnd, length);
						if (current.strip().equals(source)) {
							return 0;
						}
						document.replace(sibling.startTagEnd, length, source);
						return sibling.startTagEnd < at ? source.length() - length : 0;
					} catch (BadLocationException _) {
						return 0;
					}
				}
			}
			return 0;
		}
	}

	/** The documentation of a concept shown next to the proposals and in hovers. */
	static String conceptInfo(Terminology terminology, Concept concept) {
		StringBuilder info = new StringBuilder(concept.label()).append("\n").append(terminology.getId()).append(' ')
				.append(concept.code());
		if (!concept.kind().isEmpty()) {
			info.append(" · ").append(concept.kind());
		}
		List<Concept> path = terminology.path(concept);
		if (path.size() > 1) {
			info.append("\n");
			for (int i = 0; i < path.size() - 1; i++) {
				info.append(i == 0 ? "" : " › ").append(path.get(i).label());
			}
		}
		if (!concept.definition().isBlank()) {
			info.append("\n\n").append(concept.definition());
		}
		if (!concept.synonyms().isEmpty()) {
			List<String> synonyms = concept.synonyms().subList(0, Math.min(8, concept.synonyms().size()));
			info.append(Messages.XmlContentAssistProcessor_Also).append(String.join("; ", synonyms));
		}
		return info.toString();
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return ACTIVATION_CHARACTERS;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
}
