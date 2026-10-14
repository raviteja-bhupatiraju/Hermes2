/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.AnyType;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;

/**
 * Semantic comparison of two HLOM documents.
 * <p>
 * Both element trees are walked in parallel by EMF reflection, using the XML names from
 * {@link ExtendedMetaData}, so every element of the schemas (including healthcare extensions and unknown
 * extension content) is covered without per-element code. Repeated elements are matched by meaning rather
 * than position: equal content first (so reordering is not a change), then by a key (language strings by
 * language, vocabulary terms by value, keywords by MeSH id or text, identifiers by catalog and entry,
 * contributors by role and name, credits by credit type), and finally by position. Simple values are
 * compared by their whitespace-normalized string form; namespace declarations, {@code xsi:schemaLocation}
 * and XML formatting are ignored.
 */
public final class HlomDiff {

	private static final ExtendedMetaData EMD = ExtendedMetaData.INSTANCE;
	private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
	private static final String TEXT = "#text";
	private static final int MAX_SUMMARY = 160;

	private enum SlotKind {
		ELEMENT, ATTRIBUTE, TEXT
	}

	/** The values of one element or attribute name of an object: {@link EObject}s or strings. */
	private record Slot(String local, String qname, SlotKind kind, List<Object> values) {
	}

	/** Where the walk is: category, label and path of the current element. */
	private record Context(DiffCategory category, String label, String path) {
		Context child(String fieldLabel, String segment) {
			return new Context(category, label.isEmpty() ? fieldLabel : label + Difference.SEPARATOR + fieldLabel,
					path + "/" + segment);
		}
	}

	/** A pair of matched list indices; {@code -1} for a missing side. */
	private record Match(int left, int right) {
	}

	private final Map<String, String> prefixes = new HashMap<>();

	private HlomDiff(DocumentRoot left, DocumentRoot right) {
		// The schema prefixes win, so that paths don't depend on the prefixes a document happens to declare.
		prefixes.put(HealthcarePackage.eNS_URI, HealthcarePackage.eNS_PREFIX);
		prefixes.put(HealthcarevocabPackage.eNS_URI, HealthcarevocabPackage.eNS_PREFIX);
		prefixes.put(AddressPackage.eNS_URI, AddressPackage.eNS_PREFIX);
		for (DocumentRoot root : new DocumentRoot[] { left, right }) {
			if (root != null) {
				root.getXMLNSPrefixMap().forEach(entry -> {
					if (entry.getKey() != null && !entry.getKey().isEmpty() && entry.getValue() != null) {
						prefixes.putIfAbsent(entry.getValue(), entry.getKey());
					}
				});
			}
		}
	}

	/**
	 * Compares two documents. Either may be {@code null} (everything is then added or removed).
	 *
	 * @param left  the base document ("before")
	 * @param right the document compared with it ("after")
	 */
	public static DiffResult compare(DocumentRoot left, DocumentRoot right) {
		HlomDiff diff = new HlomDiff(left, right);
		List<Difference> out = new ArrayList<>();
		diff.compareContents(left == null ? null : left.getLom(), right == null ? null : right.getLom(),
				new Context(null, "", "/lom"), out);
		return new DiffResult(out);
	}

	/** Compares the documents of two HLOM resources. */
	public static DiffResult compare(Resource left, Resource right) {
		return compare(HlomResources.getDocumentRoot(left), HlomResources.getDocumentRoot(right));
	}

	// ------------------------------------------------------------------------------------------------------
	// Walking
	// ------------------------------------------------------------------------------------------------------

	private void compareContents(EObject left, EObject right, Context context, List<Difference> out) {
		Map<String, Slot> leftSlots = slots(left);
		Map<String, Slot> rightSlots = slots(right);
		Set<String> names = new LinkedHashSet<>(leftSlots.keySet());
		names.addAll(rightSlots.keySet());
		for (String name : names) {
			Slot leftSlot = leftSlots.get(name);
			Slot rightSlot = rightSlots.get(name);
			Slot slot = leftSlot != null ? leftSlot : rightSlot;
			List<Object> leftValues = leftSlot == null ? List.of() : leftSlot.values();
			List<Object> rightValues = rightSlot == null ? List.of() : rightSlot.values();
			if (leftValues.isEmpty() && rightValues.isEmpty()) {
				continue;
			}
			switch (slot.kind()) {
			case TEXT -> {
				Context textContext = context.label().isEmpty() ? context.child("Text", "text()") : context;
				out.add(leaf(textContext, first(leftValues), first(rightValues), left, right));
			}
			case ATTRIBUTE -> {
				if ((left instanceof LangString || right instanceof LangString) && slot.local().equals("language")) {
					continue; // the key of language strings, part of their label
				}
				boolean keyword = left instanceof Keyword || right instanceof Keyword;
				String label = keyword ? DiffLabels.keywordAttribute(slot.local()) : DiffLabels.label(slot.local());
				compareList(slot, label, "@" + slot.qname(), leftValues, rightValues, context, left, right, out);
			}
			case ELEMENT -> compareList(slot, DiffLabels.label(slot.local()), slot.qname(), leftValues, rightValues,
					context, left, right, out);
			}
		}
	}

	private void compareList(Slot slot, String label, String segment, List<Object> leftValues,
			List<Object> rightValues, Context context, EObject leftOwner, EObject rightOwner, List<Difference> out) {
		Object sample = !leftValues.isEmpty() ? leftValues.getFirst() : rightValues.getFirst();
		boolean langStrings = sample instanceof LangString;
		int count = Math.max(leftValues.size(), rightValues.size());
		for (Match match : match(leftValues, rightValues)) {
			Object left = match.left() >= 0 ? leftValues.get(match.left()) : null;
			Object right = match.right() >= 0 ? rightValues.get(match.right()) : null;
			int number = (match.left() >= 0 ? match.left() : match.right()) + 1;
			Context child;
			if (langStrings) {
				String language = language(left != null ? left : right);
				child = language.isEmpty()
						? new Context(context.category(), context.label() + (count > 1 ? " " + number : ""),
								context.path() + "/string" + (count > 1 ? "[" + number + "]" : ""))
						: new Context(context.category(), context.label() + " (" + language + ")",
								context.path() + "/string[@language='" + language + "']");
			} else if (context.category() == null && slot.kind() == SlotKind.ELEMENT) {
				// A child of <lom>: a category, the transparent hx:healthcareMetadata or other content
				DiffCategory category = DiffCategory.of(slot.local());
				String categoryLabel = category != null ? category.label() : label;
				String numbered = count > 1 ? " " + number : "";
				String index = count > 1 ? "[" + number + "]" : "";
				child = new Context(category != null ? category : isTransparentName(slot.local()) ? null : DiffCategory.OTHER,
						isTransparentName(slot.local()) ? context.label() : categoryLabel + numbered,
						context.path() + "/" + segment + index);
			} else {
				child = context.child(label + (count > 1 ? " " + number : ""),
						segment + (count > 1 ? "[" + number + "]" : ""));
			}
			if (left instanceof EObject || right instanceof EObject) {
				comparePair((EObject) left, (EObject) right, child, out);
			} else {
				out.add(leaf(child, (String) left, (String) right, leftOwner, rightOwner));
			}
		}
	}

	private void comparePair(EObject left, EObject right, Context context, List<Difference> out) {
		EObject present = left != null ? left : right;
		if (present instanceof HealthcareMetadataType) {
			compareContents(left, right, context, out); // transparent: its parts are the categories
			return;
		}
		if (left == null || right == null) {
			String text = atomicText(present);
			List<Difference> children = new ArrayList<>();
			if (text == null) {
				compareContents(left, right, context, children);
			}
			String value = text != null ? display(text) : summary(present);
			out.add(difference(context, left == null ? null : value, right == null ? null : value,
					left == null ? ChangeKind.ADDED : ChangeKind.REMOVED, left, right, children));
			return;
		}
		String leftText = atomicText(left);
		String rightText = atomicText(right);
		if (leftText != null && rightText != null) {
			out.add(leaf(context, leftText, rightText, left, right));
			if (isVocabulary(left) && isVocabulary(right)) {
				String leftSource = childText(left, "source");
				String rightSource = childText(right, "source");
				if (leftSource != null && rightSource != null && !normalize(leftSource).equals(normalize(rightSource))) {
					out.add(leaf(context.child("Source", "source"), leftSource, rightSource, left, right));
				}
			}
			return;
		}
		compareContents(left, right, context, out);
	}

	private static boolean isTransparentName(String local) {
		return local.equals("healthcareMetadata");
	}

	private Difference leaf(Context context, String left, String right, EObject leftOwner, EObject rightOwner) {
		ChangeKind kind = left == null ? ChangeKind.ADDED
				: right == null ? ChangeKind.REMOVED
						: normalize(left).equals(normalize(right)) ? ChangeKind.UNCHANGED : ChangeKind.CHANGED;
		return difference(context, display(left), display(right), kind, left == null ? null : leftOwner,
				right == null ? null : rightOwner, List.of());
	}

	private static Difference difference(Context context, String left, String right, ChangeKind kind,
			EObject leftObject, EObject rightObject, List<Difference> children) {
		DiffCategory category = context.category() == null ? DiffCategory.OTHER : context.category();
		String prefix = category.label() + Difference.SEPARATOR;
		String label = context.label();
		String field = label.startsWith(prefix) ? label.substring(prefix.length()) : label;
		return new Difference(category, context.path(), label, field, left, right, kind, leftObject, rightObject,
				children);
	}

	// ------------------------------------------------------------------------------------------------------
	// Reflection
	// ------------------------------------------------------------------------------------------------------

	/** The element, attribute and text values of an object by qualified XML name, in schema order. */
	private Map<String, Slot> slots(EObject object) {
		Map<String, Slot> slots = new LinkedHashMap<>();
		if (object == null) {
			return slots;
		}
		if (object instanceof AnyType any) {
			wildcard(any.getAny(), SlotKind.ELEMENT, slots);
			wildcard(any.getAnyAttribute(), SlotKind.ATTRIBUTE, slots);
			StringBuilder text = new StringBuilder();
			for (FeatureMap.Entry entry : any.getMixed()) {
				if (FeatureMapUtil.isText(entry) || FeatureMapUtil.isCDATA(entry)) {
					text.append(entry.getValue());
				}
			}
			if (!text.toString().isBlank()) {
				slot(slots, TEXT, TEXT, SlotKind.TEXT).values().add(text.toString().strip());
			}
			return slots;
		}
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			String name = EMD.getName(feature);
			if (name == null || name.equals("uniqueElementName") || name.equals(":mixed")
					|| feature.isDerived() && !FeatureMapUtil.isFeatureMap(feature) && EMD.getGroup(feature) == null
							&& EMD.getAffiliation(feature) == null) {
				continue;
			}
			int kind = EMD.getFeatureKind(feature);
			switch (kind) {
			case ExtendedMetaData.ELEMENT_FEATURE, ExtendedMetaData.ATTRIBUTE_FEATURE, ExtendedMetaData.SIMPLE_FEATURE -> {
				if (FeatureMapUtil.isFeatureMap(feature)
						|| feature instanceof EReference reference && !reference.isContainment()) {
					continue;
				}
				String namespace = EMD.getNamespace(feature);
				if (kind == ExtendedMetaData.ATTRIBUTE_FEATURE && isIgnoredNamespace(namespace)) {
					continue;
				}
				SlotKind slotKind = kind == ExtendedMetaData.ELEMENT_FEATURE ? SlotKind.ELEMENT
						: kind == ExtendedMetaData.ATTRIBUTE_FEATURE ? SlotKind.ATTRIBUTE : SlotKind.TEXT;
				String qname = slotKind == SlotKind.TEXT ? TEXT : qualified(namespace, name);
				Slot slot = slot(slots, slotKind == SlotKind.TEXT ? TEXT : name, qname, slotKind);
				values(object, feature, slot.values());
			}
			case ExtendedMetaData.ELEMENT_WILDCARD_FEATURE -> wildcard((FeatureMap) object.eGet(feature), SlotKind.ELEMENT,
					slots);
			case ExtendedMetaData.ATTRIBUTE_WILDCARD_FEATURE -> wildcard((FeatureMap) object.eGet(feature),
					SlotKind.ATTRIBUTE, slots);
			default -> {
				// groups and mixed content are covered by their member features
			}
			}
		}
		return slots;
	}

	private static Slot slot(Map<String, Slot> slots, String local, String qname, SlotKind kind) {
		return slots.computeIfAbsent(qname, _ -> new Slot(local, qname, kind, new ArrayList<>()));
	}

	private void wildcard(FeatureMap map, SlotKind kind, Map<String, Slot> slots) {
		if (map == null) {
			return;
		}
		for (FeatureMap.Entry entry : map) {
			EStructuralFeature feature = entry.getEStructuralFeature();
			if (FeatureMapUtil.isText(entry) || FeatureMapUtil.isCDATA(entry) || FeatureMapUtil.isComment(entry)
					|| FeatureMapUtil.isProcessingInstruction(entry)) {
				continue;
			}
			String namespace = EMD.getNamespace(feature);
			if (isIgnoredNamespace(namespace)) {
				continue;
			}
			String name = EMD.getName(feature);
			Slot slot = slot(slots, name, qualified(namespace, name), kind);
			add(feature, entry.getValue(), slot.values());
		}
	}

	private static void values(EObject object, EStructuralFeature feature, List<Object> values) {
		if (feature.isMany()) {
			for (Object value : (List<?>) object.eGet(feature)) {
				add(feature, value, values);
			}
		} else if (object.eIsSet(feature)) {
			add(feature, object.eGet(feature), values);
		}
	}

	private static void add(EStructuralFeature feature, Object value, List<Object> values) {
		if (value instanceof EObject) {
			values.add(value);
		} else {
			String text = text(feature, value);
			if (text != null) {
				values.add(text);
			}
		}
	}

	/** The XML string form of a simple value. */
	private static String text(EStructuralFeature feature, Object value) {
		if (value == null) {
			return null;
		}
		String text;
		if (value instanceof BigDecimal decimal) {
			text = decimal.signum() == 0 ? "0" : decimal.stripTrailingZeros().toPlainString();
		} else if (value instanceof Enumerator enumerator) {
			text = enumerator.getLiteral();
		} else if (value instanceof XMLGregorianCalendar calendar) {
			text = calendar.toXMLFormat();
		} else if (feature instanceof EAttribute attribute) {
			try {
				text = EcoreUtil.convertToString(attribute.getEAttributeType(), value);
			} catch (RuntimeException _) {
				text = value.toString();
			}
		} else {
			text = value.toString();
		}
		return text == null ? null : text.strip();
	}

	private static boolean isIgnoredNamespace(String namespace) {
		return XSI_NS.equals(namespace) || XMLNS_NS.equals(namespace);
	}

	private String qualified(String namespace, String name) {
		if (namespace == null || namespace.isEmpty() || namespace.equals(LomPackage.eNS_URI)) {
			return name;
		}
		String prefix = prefixes.get(namespace);
		if (prefix == null) {
			String[] segments = namespace.replaceAll("[/#]+$", "").split("[/#:]");
			prefix = segments.length == 0 ? "ns" : segments[segments.length - 1];
		}
		return prefix + ":" + name;
	}

	// ------------------------------------------------------------------------------------------------------
	// Values
	// ------------------------------------------------------------------------------------------------------

	/**
	 * The single value an element stands for, or {@code null} for compound elements: the text of simple content
	 * and language strings, the value of vocabulary terms and of date or duration wrappers.
	 */
	private String atomicText(EObject object) {
		if (object == null) {
			return null;
		}
		if (object instanceof LangString string) {
			return string.getValue() == null ? "" : string.getValue().strip();
		}
		Map<String, Slot> slots = slots(object);
		List<Slot> populated = slots.values().stream().filter(s -> !s.values().isEmpty()).toList();
		if (populated.isEmpty()) {
			return slots.containsKey(TEXT) ? "" : null;
		}
		if (populated.size() == 1 && populated.getFirst().values().size() == 1) {
			Slot only = populated.getFirst();
			if (only.kind() == SlotKind.TEXT) {
				return (String) only.values().getFirst();
			}
			if (only.kind() == SlotKind.ELEMENT && (only.local().equals("dateTime") || only.local().equals("duration"))) {
				return valueText(only.values().getFirst());
			}
		}
		if (isVocabulary(slots)) {
			Slot value = slots.get("value");
			boolean onlyTerm = populated.stream().allMatch(s -> s.qname().equals("value") || s.qname().equals("source"));
			if (onlyTerm && value.values().size() == 1) {
				return valueText(value.values().getFirst());
			}
		}
		return null;
	}

	private String valueText(Object value) {
		return value instanceof EObject object ? atomicText(object) : (String) value;
	}

	private boolean isVocabulary(EObject object) {
		return isVocabulary(slots(object));
	}

	private static boolean isVocabulary(Map<String, Slot> slots) {
		Slot source = slots.get("source");
		Slot value = slots.get("value");
		return source != null && value != null && source.kind() == SlotKind.ELEMENT && value.kind() == SlotKind.ELEMENT;
	}

	/** The atomic text of the first child element with the given qualified name. */
	private String childText(EObject object, String qname) {
		Slot slot = slots(object).get(qname);
		if (slot == null || slot.values().isEmpty()) {
			return null;
		}
		return valueText(slot.values().getFirst());
	}

	private static String first(List<Object> values) {
		return values.isEmpty() ? null : (String) values.getFirst();
	}

	private static String language(Object value) {
		String language = value instanceof LangString string ? string.getLanguage() : null;
		return language == null ? "" : language.strip();
	}

	private static String normalize(String text) {
		return text == null ? "" : text.strip().replaceAll("\\s+", " ");
	}

	/** The display form of a value: whitespace collapsed, vCards reduced to their properties. */
	static String display(String text) {
		if (text == null) {
			return null;
		}
		String stripped = text.strip();
		if (stripped.toUpperCase(Locale.ROOT).startsWith("BEGIN:VCARD")) {
			List<String> parts = new ArrayList<>();
			String unfolded = stripped.replace("\r\n", "\n").replace('\r', '\n').replaceAll("\n[ \t]", "");
			boolean hasName = unfolded.toUpperCase(Locale.ROOT).contains("\nFN");
			for (String line : unfolded.split("\n")) {
				int colon = line.indexOf(':');
				if (colon < 0) {
					continue;
				}
				String property = line.substring(0, colon).strip().toUpperCase(Locale.ROOT);
				String value = line.substring(colon + 1).strip();
				if (property.equals("BEGIN") || property.equals("END") || property.startsWith("VERSION")
						|| hasName && (property.equals("N") || property.startsWith("N;")) || value.isEmpty()) {
					continue;
				}
				parts.add(value.replace(";", " ").replaceAll("\\s+", " ").strip());
			}
			if (!parts.isEmpty()) {
				return String.join(" · ", parts);
			}
		}
		return normalize(stripped);
	}

	/** A one line description of a compound element, e.g. {@code 1.5 CME Credit · ACCME}. */
	private String summary(EObject object) {
		if (object instanceof Keyword keyword) {
			String text = strings(keyword);
			if (keyword.getId() != null) {
				text += " (" + (keyword.getSource() != null ? keyword.getSource() + " " : "") + keyword.getId() + ")";
			}
			return text;
		}
		if (object instanceof LanguageString strings) {
			return strings(strings);
		}
		if (object instanceof CreditsType credits) {
			String label = join(" ", childText(credits, "hx:numberOfCredits"), childText(credits, "hx:creditType"),
					childText(credits, "hx:creditUnit"));
			return join(" · ", label, childText(credits, "hx:accreditingBody"));
		}
		String name = containmentName(object);
		if (name.equals("contribute")) {
			List<String> names = new ArrayList<>();
			Slot entities = slots(object).get("entity");
			if (entities != null) {
				for (Object entity : entities.values()) {
					String vcard = valueText(entity);
					String person = HlomSummary.vcardName(vcard);
					if (person != null) {
						names.add(person);
					}
				}
			}
			String role = childText(object, "role");
			String people = String.join(", ", names);
			return role == null ? people : people.isEmpty() ? role : role + ": " + people;
		}
		if (name.equals("identifier")) {
			return join(": ", childText(object, "catalog"), childText(object, "entry"));
		}
		List<String> texts = new ArrayList<>();
		texts(object, texts);
		String summary = String.join(" · ", texts);
		return summary.length() > MAX_SUMMARY ? summary.substring(0, MAX_SUMMARY - 1) + "…" : summary;
	}

	private void texts(EObject object, List<String> texts) {
		for (Slot slot : slots(object).values()) {
			for (Object value : slot.values()) {
				if (texts.size() >= 12) {
					return;
				}
				String text = value instanceof EObject child ? atomicText(child) : (String) value;
				if (text != null) {
					if (!text.isBlank() && !(slot.kind() == SlotKind.ATTRIBUTE && slot.local().equals("language"))) {
						texts.add(display(text));
					}
				} else {
					texts((EObject) value, texts);
				}
			}
		}
	}

	private static String strings(LanguageString strings) {
		List<String> texts = new ArrayList<>();
		for (LangString string : strings.getString()) {
			if (string.getValue() != null && !string.getValue().isBlank()) {
				String language = language(string);
				texts.add(normalize(string.getValue()) + (language.isEmpty() || strings.getString().size() == 1 ? ""
						: " [" + language + "]"));
			}
		}
		return String.join(" | ", texts);
	}

	private static String join(String separator, String... parts) {
		List<String> present = new ArrayList<>();
		for (String part : parts) {
			if (part != null && !part.isBlank()) {
				present.add(part.strip());
			}
		}
		return String.join(separator, present);
	}

	private static String containmentName(EObject object) {
		EStructuralFeature feature = object.eContainingFeature();
		String name = feature == null ? null : EMD.getName(feature);
		return Objects.requireNonNullElse(name, "");
	}

	// ------------------------------------------------------------------------------------------------------
	// Matching
	// ------------------------------------------------------------------------------------------------------

	/**
	 * Matches repeated values: equal content first, then equal keys, then (unless the key decides identity) by
	 * position. Returns the pairs in left document order, with added values after their right predecessor.
	 */
	private List<Match> match(List<Object> left, List<Object> right) {
		Object sample = !left.isEmpty() ? left.getFirst() : right.isEmpty() ? null : right.getFirst();
		boolean langStrings = sample instanceof LangString;
		if (!langStrings && left.size() == 1 && right.size() == 1) {
			return List.of(new Match(0, 0));
		}
		boolean strict = langStrings || sample instanceof Keyword || sample instanceof CreditsType
				|| sample instanceof EObject object && !(object instanceof LanguageString) && isVocabulary(object);
		int[] leftToRight = new int[left.size()];
		Arrays.fill(leftToRight, -1);
		boolean[] usedRight = new boolean[right.size()];

		List<String> leftCanonical = left.stream().map(this::canonical).toList();
		List<String> rightCanonical = right.stream().map(this::canonical).toList();
		pairEqual(leftCanonical, rightCanonical, leftToRight, usedRight);
		List<String> leftKeys = left.stream().map(this::key).toList();
		List<String> rightKeys = right.stream().map(this::key).toList();
		pairEqual(leftKeys, rightKeys, leftToRight, usedRight);
		if (!strict) {
			int j = 0;
			for (int i = 0; i < left.size(); i++) {
				if (leftToRight[i] >= 0) {
					continue;
				}
				while (j < right.size() && usedRight[j]) {
					j++;
				}
				if (j < right.size()) {
					leftToRight[i] = j;
					usedRight[j] = true;
				}
			}
		}

		List<Match> ordered = new ArrayList<>();
		for (int i = 0; i < left.size(); i++) {
			ordered.add(new Match(i, leftToRight[i]));
		}
		for (int j = 0; j < right.size(); j++) {
			if (usedRight[j]) {
				continue;
			}
			// after its right predecessor; without one, after leading removed values
			int at = -1;
			for (int k = 0; k < ordered.size(); k++) {
				if (ordered.get(k).right() >= 0 && ordered.get(k).right() < j) {
					at = k + 1;
				}
			}
			if (at < 0) {
				at = 0;
				while (at < ordered.size() && ordered.get(at).right() < 0) {
					at++;
				}
			}
			ordered.add(at, new Match(-1, j));
		}
		return ordered;
	}

	private static void pairEqual(List<String> left, List<String> right, int[] leftToRight, boolean[] usedRight) {
		for (int i = 0; i < left.size(); i++) {
			if (leftToRight[i] >= 0 || left.get(i) == null) {
				continue;
			}
			for (int j = 0; j < right.size(); j++) {
				if (!usedRight[j] && Objects.equals(left.get(i), right.get(j))) {
					leftToRight[i] = j;
					usedRight[j] = true;
					break;
				}
			}
		}
	}

	/** The identity of a repeated element, {@code null} if it has none beyond its content. */
	private String key(Object value) {
		if (value instanceof LangString string) {
			return language(string).toLowerCase(Locale.ROOT);
		}
		if (value instanceof Keyword keyword) {
			return keyword.getId() != null && !keyword.getId().isBlank()
					? "id:" + keyword.getId().strip().toUpperCase(Locale.ROOT)
					: "text:" + normalize(strings(keyword)).toLowerCase(Locale.ROOT);
		}
		if (value instanceof CreditsType credits) {
			return "credit:" + childText(credits, "hx:creditType");
		}
		if (!(value instanceof EObject object) || value instanceof LanguageString) {
			return null;
		}
		if (isVocabulary(object)) {
			String term = childText(object, "value");
			return term == null ? null : "term:" + normalize(term).toLowerCase(Locale.ROOT);
		}
		String name = containmentName(object);
		if (name.equals("identifier")) {
			return "id:" + normalize(childText(object, "catalog")) + "|" + normalize(childText(object, "entry"));
		}
		if (name.equals("contribute")) {
			return "contribute:" + normalize(childText(object, "role")) + "|" + normalize(summary(object));
		}
		return null;
	}

	/** A canonical form of a value's complete content, for detecting equal (possibly reordered) elements. */
	private String canonical(Object value) {
		if (!(value instanceof EObject object)) {
			return value == null ? null : normalize((String) value);
		}
		if (object instanceof LangString string) {
			return language(string) + "=" + normalize(string.getValue());
		}
		StringBuilder builder = new StringBuilder("{");
		for (Slot slot : slots(object).values()) {
			if (slot.values().isEmpty()) {
				continue;
			}
			builder.append(slot.qname()).append('[');
			for (Object child : slot.values()) {
				builder.append(canonical(child)).append(';');
			}
			builder.append(']');
		}
		return builder.append('}').toString();
	}
}
