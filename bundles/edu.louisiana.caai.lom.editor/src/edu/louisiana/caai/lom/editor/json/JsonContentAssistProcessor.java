/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.json;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.xml.XmlCompletionProposal;
import edu.louisiana.caai.lom.editor.xml.XmlImages;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.json.HlomJson;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Content assist of the JSON page, driven by the HLOM model like the conversion to XML ({@link HlomJson}):
 * <ul>
 * <li>member names allowed in the current object that are not yet present, inserted with a value skeleton
 * (multilingual text, vocabulary, object or array as the schema requires);</li>
 * <li>values: vocabulary terms and sources, enumerated healthcare values, language tags, values recommended by
 * the specification, keyword sources and terminology codes (offline MeSH, ICD-10-CM and taxonomies).</li>
 * </ul>
 * The position is found by a tolerant scan of the text before the caret, so assist works in incomplete JSON.
 */
public final class JsonContentAssistProcessor implements IContentAssistProcessor {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;
	private static final int CONCEPT_LIMIT = 40;
	private static final char[] ACTIVATION = { '"' };

	private static final Map<String, Supplier<List<String>>> SUGGESTIONS = Map.of(
			"format", () -> HlomSuggestions.MIME_TYPES,
			"metadataSchema", () -> HlomSuggestions.METADATA_SCHEMAS,
			"accreditingBody", () -> HlomSuggestions.ACCREDITING_BODIES,
			"activityCertification", () -> HlomSuggestions.ACTIVITY_CERTIFICATIONS,
			"readingLevel", () -> HlomSuggestions.READING_LEVELS,
			"profession", HlomSuggestions::professionTerms,
			"specialty", () -> HlomSuggestions.SPECIALTIES);

	private String errorMessage;

	// ----------------------------------------------------------------------------------------------------
	// Position
	// ----------------------------------------------------------------------------------------------------

	/** What the caret is on. */
	enum Kind {
		KEY, VALUE, NONE
	}

	/** An open object or array before the caret. */
	private static final class Frame {
		final boolean object;
		/** The member name this container is the value of (for an array element: the array's name). */
		final String key;
		/** Offset of the opening brace or bracket. */
		final int start;
		String pendingKey;
		boolean expectKey;
		boolean afterColon;

		Frame(boolean object, String key, int start) {
			this.object = object;
			this.key = key;
			this.start = start;
			this.expectKey = object;
		}
	}

	/** The caret position: its kind, the member names from the root down, and the typed text. */
	record Position(Kind kind, List<String> path, String key, boolean inString, int typedStart, String typed,
			int objectStart) {
	}

	/** Finds the caret position in a (possibly incomplete) JSON text. */
	static Position position(String text, int offset) {
		Deque<Frame> stack = new ArrayDeque<>();
		int i = 0;
		int stringStart = -1;
		boolean stringIsKey = false;
		while (i < offset) {
			char c = text.charAt(i);
			Frame top = stack.peek();
			if (c == '"') {
				int j = i + 1;
				StringBuilder value = new StringBuilder();
				while (j < offset && text.charAt(j) != '"') {
					if (text.charAt(j) == '\\' && j + 1 < offset) {
						j++;
					}
					value.append(text.charAt(j));
					j++;
				}
				boolean isKey = top != null && top.object && top.expectKey;
				if (j >= offset) {
					stringStart = i + 1;
					stringIsKey = isKey;
					break;
				}
				if (isKey) {
					top.pendingKey = value.toString();
					top.expectKey = false;
				}
				i = j + 1;
				continue;
			}
			switch (c) {
				case '{', '[' -> {
					String key = top == null ? null : top.object ? top.pendingKey : top.key;
					if (top != null && top.object) {
						top.afterColon = false;
					}
					stack.push(new Frame(c == '{', key, i));
				}
				case '}', ']' -> {
					if (!stack.isEmpty()) {
						stack.pop();
					}
				}
				case ':' -> {
					if (top != null && top.object) {
						top.afterColon = true;
					}
				}
				case ',' -> {
					if (top != null && top.object) {
						top.expectKey = true;
						top.afterColon = false;
						top.pendingKey = null;
					}
				}
				default -> {
					// literals, numbers and white space
				}
			}
			i++;
		}
		List<String> path = new ArrayList<>();
		Frame object = null;
		for (Frame frame : stack.reversed()) {
			if (frame.object) {
				if (frame.key != null) {
					path.add(frame.key);
				}
				object = frame;
			}
		}
		Frame top = stack.peek();
		if (top == null) {
			return new Position(Kind.NONE, path, null, false, offset, "", -1);
		}
		int objectStart = object == null ? -1 : object.start;
		if (stringStart >= 0) {
			String typed = text.substring(stringStart, offset);
			if (stringIsKey) {
				return new Position(Kind.KEY, path, null, true, stringStart, typed, objectStart);
			}
			return new Position(Kind.VALUE, path, top.object ? top.pendingKey : top.key, true, stringStart, typed,
					objectStart);
		}
		int wordStart = offset;
		while (wordStart > 0 && Character.isLetterOrDigit(text.charAt(wordStart - 1))) {
			wordStart--;
		}
		String typed = text.substring(wordStart, offset);
		if (top.object && top.expectKey) {
			return new Position(Kind.KEY, path, null, false, wordStart, typed, objectStart);
		}
		if (top.object && top.afterColon) {
			return new Position(Kind.VALUE, path, top.pendingKey, false, wordStart, typed, objectStart);
		}
		if (!top.object) {
			return new Position(Kind.VALUE, path, top.key, false, wordStart, typed, objectStart);
		}
		return new Position(Kind.NONE, path, null, false, offset, "", objectStart);
	}

	// ----------------------------------------------------------------------------------------------------
	// Model
	// ----------------------------------------------------------------------------------------------------

	/** The class of the object at a path ({@code null} for the document object or unknown objects). */
	static EClass classOf(List<String> path) {
		if (path.isEmpty() || !"lom".equals(path.getFirst())) {
			return null;
		}
		EStructuralFeature lom = META.getElement(LomPackage.eNS_URI, "lom");
		EClass current = lom == null ? null : (EClass) lom.getEType();
		for (int i = 1; i < path.size() && current != null; i++) {
			EStructuralFeature feature = element(current, path.get(i));
			current = feature != null && feature.getEType() instanceof EClass eClass ? eClass : null;
		}
		return current;
	}

	private static String local(String key) {
		return key == null ? null : key.substring(key.indexOf(':') + 1);
	}

	/** The element feature of a class with a member name, or {@code null}. */
	static EStructuralFeature element(EClass type, String key) {
		String local = local(key);
		for (EStructuralFeature feature : META.getAllElements(type)) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE && local.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	private static EStructuralFeature attribute(EClass type, String key) {
		String local = local(key);
		for (EStructuralFeature feature : META.getAllAttributes(type)) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE && local.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	private static boolean isSimpleContent(EClass type) {
		return type != null && META.getContentKind(type) == ExtendedMetaData.SIMPLE_CONTENT;
	}

	private static boolean isLangStringContainer(EClass type) {
		List<EStructuralFeature> elements = META.getAllElements(type);
		return elements.size() == 1 && "string".equals(META.getName(elements.getFirst()));
	}

	/** The JSON value inserted for a new member: a skeleton the schema requires, with "|" marking the caret. */
	private static String skeleton(EStructuralFeature feature) {
		String single;
		if (feature.getEType() instanceof EClass type) {
			if (Vocabularies.isVocabulary(type)) {
				single = "{\"source\": \"" + Vocabularies.LOM_SOURCE + "\", \"value\": \"|\"}";
			} else if (isSimpleContent(type)) {
				single = META.getAllAttributes(type).isEmpty() ? "\"|\""
						: "{\"language\": \"" + HlomDocuments.DEFAULT_LANGUAGE + "\", \"content\": \"|\"}";
			} else if (isLangStringContainer(type)) {
				single = "{\"string\": [{\"language\": \"" + HlomDocuments.DEFAULT_LANGUAGE + "\", \"content\": \"|\"}]}";
			} else {
				single = "{|}";
			}
		} else {
			EDataType type = (EDataType) feature.getEType();
			Class<?> instance = type.getInstanceClass();
			boolean bare = instance != null && (Number.class.isAssignableFrom(instance) || instance.isPrimitive());
			single = bare ? "|" : "\"|\"";
		}
		return feature.isMany() ? "[" + single + "]" : single;
	}

	// ----------------------------------------------------------------------------------------------------
	// Proposals
	// ----------------------------------------------------------------------------------------------------

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		errorMessage = null;
		List<XmlCompletionProposal> proposals = compute(viewer.getDocument().get(), offset);
		if (proposals.isEmpty()) {
			errorMessage = Messages.JsonContentAssistProcessor_NoProposalsAtThisPosition;
		}
		return proposals.toArray(ICompletionProposal[]::new);
	}

	/** Computes the proposals for a caret offset in a text. */
	static List<XmlCompletionProposal> compute(String text, int offset) {
		HlomResources.initStandalone();
		Position position = position(text, offset);
		List<XmlCompletionProposal> proposals = new ArrayList<>();
		if (position.kind() == Kind.KEY) {
			keys(text, offset, position, proposals);
		} else if (position.kind() == Kind.VALUE && position.key() != null) {
			values(text, offset, position, proposals);
		}
		return proposals;
	}

	/** The member names present in the object starting at an offset (at its top level). */
	private static Set<String> presentKeys(String text, int objectStart) {
		Set<String> keys = new LinkedHashSet<>();
		if (objectStart < 0) {
			return keys;
		}
		int depth = 0;
		boolean expectKey = true;
		for (int i = objectStart; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '"') {
				int j = i + 1;
				while (j < text.length() && text.charAt(j) != '"' && text.charAt(j) != '\n') {
					j += text.charAt(j) == '\\' ? 2 : 1;
				}
				if (depth == 1 && expectKey && j < text.length() && text.charAt(j) == '"') {
					keys.add(text.substring(i + 1, j));
					expectKey = false;
				}
				i = j;
			} else if (c == '{' || c == '[') {
				depth++;
			} else if (c == '}' || c == ']') {
				depth--;
				if (depth == 0) {
					break;
				}
			} else if (c == ',' && depth == 1) {
				expectKey = true;
			}
		}
		return keys;
	}

	private static void keys(String text, int offset, Position position, List<XmlCompletionProposal> proposals) {
		Set<String> present = presentKeys(text, position.objectStart());
		// The member name being typed is not "present".
		present.remove(position.typed());
		int start = position.inString() ? position.typedStart() - 1 : position.typedStart();
		int end = keyEnd(text, offset, position.inString());
		// Renaming a member that already has a value: replace only the name.
		int next = end;
		while (next < text.length() && (text.charAt(next) == ' ' || text.charAt(next) == '\t')) {
			next++;
		}
		boolean hasValue = next < text.length() && text.charAt(next) == ':';
		if (position.path().isEmpty()) {
			if (!present.contains("$schema")) {
				proposals.add(proposal("$schema", Messages.JsonContentAssistProcessor_TheHLOMJSONSchema, start, offset, end,
						member("$schema", "\"" + HlomJson.SCHEMA_URL + "\"|", hasValue), null, XmlImages.Kind.END_TAG));
			}
			if (!present.contains("lom")) {
				proposals.add(proposal("lom", Messages.JsonContentAssistProcessor_TheRecord, start, offset, end, member("lom", "{|}", hasValue),
						FormSupport.help("lom"), XmlImages.Kind.ELEMENT));
			}
			filter(proposals, position.typed());
			return;
		}
		EClass type = classOf(position.path());
		if (type == null) {
			return;
		}
		for (EStructuralFeature feature : META.getAllElements(type)) {
			String name = META.getName(feature);
			if (META.getFeatureKind(feature) != ExtendedMetaData.ELEMENT_FEATURE || name == null || present.contains(name)) {
				continue;
			}
			boolean healthcare = !LomPackage.eNS_URI.equals(META.getNamespace(feature));
			proposals.add(proposal(name, feature.isMany() ? "array" : null, start, offset, end,
					member(name, skeleton(feature), hasValue), FormSupport.help(name),
					healthcare ? XmlImages.Kind.HEALTHCARE_ELEMENT : XmlImages.Kind.ELEMENT).retrigger(!hasValue && hasValues(feature)));
		}
		for (EStructuralFeature feature : META.getAllAttributes(type)) {
			String name = META.getName(feature);
			if (META.getFeatureKind(feature) != ExtendedMetaData.ATTRIBUTE_FEATURE || name == null || present.contains(name)
					|| "uniqueElementName".equals(name)) {
				continue;
			}
			proposals.add(proposal(name, "attribute", start, offset, end, member(name, "\"|\"", hasValue),
					FormSupport.help(name), XmlImages.Kind.ATTRIBUTE).retrigger(!hasValue));
		}
		if (isSimpleContent(type) && !META.getAllAttributes(type).isEmpty() && !present.contains(HlomJson.CONTENT)) {
			proposals.add(proposal(HlomJson.CONTENT, "text", start, offset, end, member(HlomJson.CONTENT, "\"|\"", hasValue),
					null, XmlImages.Kind.ELEMENT));
		}
		filter(proposals, position.typed());
	}

	/** A member name with its value skeleton ("|" marks the caret), or only the name if it has a value. */
	private static String member(String name, String skeleton, boolean hasValue) {
		return hasValue ? "\"" + name + "\"|" : "\"" + name + "\": " + skeleton;
	}

	private static boolean hasValues(EStructuralFeature feature) {
		if (feature.getEType() instanceof EClass type) {
			return Vocabularies.isVocabulary(type) || (isSimpleContent(type) && META.getAllAttributes(type).isEmpty()
					&& META.getSimpleFeature(type) != null && !Vocabularies.memberEnums(
							(EDataType) META.getSimpleFeature(type).getEType()).isEmpty());
		}
		return !Vocabularies.memberEnums((EDataType) feature.getEType()).isEmpty()
				|| SUGGESTIONS.containsKey(META.getName(feature)) || "language".equals(META.getName(feature));
	}

	/** The end of a member name being replaced: its closing quote, colon and the space after it. */
	private static int keyEnd(String text, int offset, boolean inString) {
		int i = offset;
		if (inString) {
			while (i < text.length() && text.charAt(i) != '"' && text.charAt(i) != '\n') {
				i++;
			}
			if (i < text.length() && text.charAt(i) == '"') {
				i++;
				int j = i;
				while (j < text.length() && text.charAt(j) == ' ') {
					j++;
				}
				if (j < text.length() && text.charAt(j) == ':') {
					// The member already has a value: keep it, replace only the name.
					return i;
				}
			}
			return i;
		}
		while (i < text.length() && Character.isLetterOrDigit(text.charAt(i))) {
			i++;
		}
		return i;
	}

	private static void values(String text, int offset, Position position, List<XmlCompletionProposal> proposals) {
		String key = local(position.key());
		List<String> path = position.path();
		EClass owner = classOf(path);
		List<Choice> choices = new ArrayList<>();
		if ("language".equals(key)) {
			for (String tag : HlomSuggestions.LANGUAGES) {
				choices.add(new Choice(tag, Locale.forLanguageTag(tag).getDisplayName(Locale.ENGLISH), null));
			}
		} else if (owner != null && Vocabularies.isVocabulary(owner) && "value".equals(key)) {
			for (Vocabularies.Term term : Vocabularies.terms(owner)) {
				choices.add(new Choice(term.value(), Vocabularies.HEALTHCARE_SOURCE.equals(term.source()) ? "Healthcare"
						: null, null));
			}
		} else if (owner != null && Vocabularies.isVocabulary(owner) && "source".equals(key)) {
			choices.add(new Choice(Vocabularies.LOM_SOURCE, null, null));
			choices.add(new Choice(Vocabularies.HEALTHCARE_SOURCE, null, null));
		} else if (owner != null && "Keyword".equals(owner.getName()) && "source".equals(key)) {
			for (String source : HlomSuggestions.KEYWORD_SOURCES) {
				Terminology terminology = Terminologies.forSource(source);
				choices.add(new Choice(source, terminology == null ? null : "offline", null));
			}
		} else if (owner != null && "Keyword".equals(owner.getName()) && "id".equals(key)) {
			Terminology terminology = Terminologies.forSource(member(text, position.objectStart(), "source"));
			concepts(terminology, position.typed(), choices, true);
		} else if (path.size() >= 3 && "string".equals(path.getLast()) && HlomJson.CONTENT.equals(key)
				&& "keyword".equals(path.get(path.size() - 2))) {
			int keywordStart = enclosingObjectStart(text, position.objectStart());
			String source = member(text, keywordStart, "source");
			Terminology terminology = source == null ? Terminologies.MESH : Terminologies.forSource(source);
			if (position.typed().strip().length() >= 2) {
				concepts(terminology, position.typed(), choices, false);
			}
		} else if (path.size() >= 3 && "taxon".equals(path.getLast()) && "id".equals(key)) {
			int taxonPathStart = enclosingObjectStart(text, enclosingObjectStart(text, position.objectStart()));
			concepts(taxonTerminology(text, taxonPathStart), position.typed(), choices, true);
		} else if (path.size() >= 3 && "string".equals(path.getLast()) && HlomJson.CONTENT.equals(key)
				&& "source".equals(path.get(path.size() - 2)) && "taxonPath".equals(path.get(path.size() - 3))) {
			for (Terminology terminology : Terminologies.all()) {
				choices.add(new Choice(terminology == Terminologies.MESH || terminology == Terminologies.ICD_10_CM
						? terminology.getId() : terminology.getTitle(), "offline", null));
			}
			for (String taxonomy : HlomSuggestions.TAXONOMIES) {
				if (choices.stream().noneMatch(choice -> choice.value().equals(taxonomy))) {
					choices.add(new Choice(taxonomy, null, null));
				}
			}
		} else if (owner != null) {
			EStructuralFeature feature = element(owner, key);
			if (feature == null) {
				feature = attribute(owner, key);
			}
			EDataType type = feature == null ? null : feature.getEType() instanceof EDataType dataType ? dataType
					: feature.getEType() instanceof EClass content && META.getSimpleFeature(content) != null
							? (EDataType) META.getSimpleFeature(content).getEType() : null;
			if (type != null) {
				for (EEnum eEnum : Vocabularies.memberEnums(type)) {
					for (EEnumLiteral literal : eEnum.getELiterals()) {
						choices.add(new Choice(literal.getLiteral(), null, null));
					}
				}
			}
			String suggestionKey = HlomJson.CONTENT.equals(key) && !path.isEmpty() ? path.getLast() : key;
			if (choices.isEmpty() && SUGGESTIONS.containsKey(suggestionKey)) {
				SUGGESTIONS.get(suggestionKey).get().forEach(value -> choices.add(new Choice(value, null, null)));
			}
		}
		int start = position.typedStart();
		int end = offset;
		if (position.inString()) {
			while (end < text.length() && text.charAt(end) != '"' && text.charAt(end) != '\n') {
				end++;
			}
		}
		for (Choice choice : choices) {
			String value = escape(choice.value());
			String replacement = position.inString() ? value + "|" : "\"" + value + "\"|";
			XmlCompletionProposal proposal = proposal(choice.value(), choice.qualifier(), start, offset, end, replacement, choice.info(),
					choice.qualifier() != null && choice.info() != null ? XmlImages.Kind.HEALTHCARE_TERM : XmlImages.Kind.TERM);
			proposals.add(choice.info() != null ? proposal.matchWords() : proposal);
		}
		filter(proposals, position.typed());
	}

	/** A value to propose. */
	private record Choice(String value, String qualifier, String info) {
	}

	private static void concepts(Terminology terminology, String typed, List<Choice> choices, boolean codes) {
		if (terminology == null || typed.isBlank()) {
			return;
		}
		for (Terminology.Match match : terminology.search(typed, CONCEPT_LIMIT)) {
			Concept concept = match.concept();
			String info = concept.label() + "\n" + terminology.getId() + " " + concept.code()
					+ (concept.definition().isBlank() ? "" : "\n\n" + concept.definition());
			choices.add(codes ? new Choice(concept.code(), concept.label(), info)
					: new Choice(concept.label(), terminology.getId() + " " + concept.code(), info));
		}
	}

	/** The terminology named by the source of a taxon path object. */
	private static Terminology taxonTerminology(String text, int taxonPathStart) {
		if (taxonPathStart < 0) {
			return null;
		}
		int source = text.indexOf("\"source\"", taxonPathStart);
		if (source < 0) {
			return null;
		}
		Matcher m = Pattern.compile("\"content\"\\s*:\\s*\"([^\"]*)\"").matcher(text);
		return m.find(source) ? Terminologies.forSource(m.group(1)) : null;
	}

	/** The string value of a member of the object starting at an offset (at its top level), or {@code null}. */
	private static String member(String text, int objectStart, String name) {
		if (objectStart < 0) {
			return null;
		}
		Matcher m = Pattern.compile("\"" + Pattern.quote(name) + "\"\\s*:\\s*\"([^\"]*)\"").matcher(text);
		int end = objectEnd(text, objectStart);
		int from = objectStart;
		while (m.find(from) && m.start() < end) {
			if (depth(text, objectStart, m.start()) == 1) {
				return m.group(1);
			}
			from = m.end();
		}
		return null;
	}

	private static int depth(String text, int from, int to) {
		int depth = 0;
		boolean inString = false;
		for (int i = from; i < to; i++) {
			char c = text.charAt(i);
			if (c == '"' && (i == 0 || text.charAt(i - 1) != '\\')) {
				inString = !inString;
			} else if (!inString && (c == '{' || c == '[')) {
				depth++;
			} else if (!inString && (c == '}' || c == ']')) {
				depth--;
			}
		}
		return depth;
	}

	private static int objectEnd(String text, int objectStart) {
		int depth = 0;
		boolean inString = false;
		for (int i = objectStart; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '"' && (i == 0 || text.charAt(i - 1) != '\\')) {
				inString = !inString;
			} else if (!inString && (c == '{' || c == '[')) {
				depth++;
			} else if (!inString && (c == '}' || c == ']')) {
				depth--;
				if (depth == 0) {
					return i;
				}
			}
		}
		return text.length();
	}

	/** The start of the object enclosing the object starting at an offset (arrays are skipped), or -1. */
	private static int enclosingObjectStart(String text, int objectStart) {
		if (objectStart <= 0) {
			return -1;
		}
		int depth = 0;
		for (int i = objectStart - 1; i >= 0; i--) {
			char c = text.charAt(i);
			if (c == '}' || c == ']') {
				depth++;
			} else if (c == '[') {
				depth = Math.max(0, depth - 1);
			} else if (c == '{') {
				if (depth == 0) {
					return i;
				}
				depth--;
			}
		}
		return -1;
	}

	private static String escape(String value) {
		return value.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	private static void filter(List<XmlCompletionProposal> proposals, String typed) {
		proposals.removeIf(proposal -> !proposal.matches(typed));
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return ACTIVATION;
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

	/**
	 * A proposal replacing the text from {@code start} to {@code end} (at or after the caret); "|" in the
	 * replacement marks the caret. The typed text matches it with or without its opening quote.
	 */
	private static XmlCompletionProposal proposal(String label, String qualifier, int start, int offset, int end,
			String replacementWithCaret, String info, XmlImages.Kind kind) {
		int caret = replacementWithCaret.indexOf('|');
		String replacement = replacementWithCaret.replace("|", "");
		return new XmlCompletionProposal(label, qualifier, kind, start, offset, end - offset, replacement,
				caret < 0 ? replacement.length() : caret, info, 0, label, "\"" + label,
				qualifier == null ? label : label + " " + qualifier);
	}
}
