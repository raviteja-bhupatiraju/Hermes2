/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.edit.labels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

import edu.louisiana.caai.lom.edit.HlomEditPlugin;

/**
 * Friendly, content-aware labels for Healthcare LOM objects, e.g. {@code "Life Cycle — version 1.0 · final"} or
 * {@code "Contributor — author: John Doe"}. Used by all generated item providers' {@code getText}.
 * <p>
 * When created with an adapter factory, every object consulted for a label is adapted so that changes to it are
 * reported and {@link HlomLabelUpdates} can refresh the labels of its ancestors.
 */
public final class HlomLabels {

	/** Separates an element name from its summary. */
	public static final String DASH = " — ";

	/** Separates the parts of a summary. */
	public static final String DOT = " · ";

	/** Values longer than this are truncated with an ellipsis. */
	public static final int MAX_LENGTH = 60;

	private static final String ELLIPSIS = "…";
	private static final String PATH = " › ";
	private static final String HLOM_SOURCE = "HEALTHCARE_LOMv1";

	/** Classes used for several elements; these are named after the element (containment feature). */
	private static final Set<String> GENERIC_CLASSES = Set.of("LanguageString", "DateTime", "DurationBase", "VCard",
			"LanguageId");

	private final AdapterFactory tracker;

	private HlomLabels(AdapterFactory tracker) {
		this.tracker = tracker;
	}

	/**
	 * The label of an object, adapting the objects it depends on with the root of the given factory (may be
	 * {@code null}).
	 */
	public static String getText(AdapterFactory adapterFactory, Object object) {
		AdapterFactory root = adapterFactory instanceof ComposeableAdapterFactory composeable
				? composeable.getRootAdapterFactory()
				: adapterFactory;
		return new HlomLabels(root).label(object);
	}

	/** The label of an object. */
	public static String getText(Object object) {
		return new HlomLabels(null).label(object);
	}

	/** The display name of a class ({@code _UI_<Class>_type}). */
	public static String typeName(EClass eClass) {
		return string("_UI_" + eClass.getName() + "_type", humanize(eClass.getName()));
	}

	/** The display name of a feature ({@code _UI_<Class>_<feature>_feature}). */
	public static String featureName(EStructuralFeature feature) {
		String fallback = humanize(ExtendedMetaData.INSTANCE.getName(feature));
		EClass owner = feature.getEContainingClass();
		return owner == null ? fallback
				: string("_UI_" + owner.getName() + "_" + feature.getName() + "_feature", fallback);
	}

	/** Collapses white space and truncates to {@link #MAX_LENGTH} characters with an ellipsis. */
	public static String truncate(String text) {
		if (text == null) {
			return "";
		}
		String clean = text.replaceAll("\\s+", " ").trim();
		return clean.length() <= MAX_LENGTH ? clean : clean.substring(0, MAX_LENGTH - 1).trim() + ELLIPSIS;
	}

	/** The formatted name (FN) of a vCard, or the cleaned text if it is not a vCard. */
	public static String vCardName(String vCard) {
		if (vCard == null || vCard.isBlank()) {
			return "";
		}
		if (!vCard.toUpperCase().contains("BEGIN:VCARD")) {
			return truncate(vCard);
		}
		String name = null;
		String org = null;
		for (String line : vCard.split("\\r?\\n")) {
			String trimmed = line.trim();
			int colon = trimmed.indexOf(':');
			if (colon < 0) {
				continue;
			}
			String key = trimmed.substring(0, colon).toUpperCase();
			int semicolon = key.indexOf(';');
			if (semicolon >= 0) {
				key = key.substring(0, semicolon);
			}
			String value = unescape(trimmed.substring(colon + 1));
			if (key.equals("FN") && !value.isBlank()) {
				return truncate(value);
			} else if (key.equals("N") && name == null) {
				String[] parts = trimmed.substring(colon + 1).split(";", -1);
				String given = parts.length > 1 ? unescape(parts[1]) : "";
				name = (given + " " + unescape(parts[0])).trim();
			} else if (key.equals("ORG") && org == null) {
				org = value.replace(';', ' ');
			}
		}
		if (name != null && !name.isBlank()) {
			return truncate(name);
		}
		return org != null && !org.isBlank() ? truncate(org) : "vCard";
	}

	// ---------------------------------------------------------------- dispatch

	private String label(Object object) {
		if (object instanceof FeatureMap.Entry entry) {
			return label(entry.getValue());
		}
		if (!(object instanceof EObject eObject)) {
			return object == null ? "" : truncate(String.valueOf(object));
		}
		EClass eClass = eObject.eClass();
		String className = eClass.getName();
		if (className.equals("LangString")) {
			return langString(eObject);
		}
		if (className.endsWith("Value") && hasAttribute(eClass, "value")) {
			String value = value(eObject, "value");
			return value.isEmpty() ? typeName(eClass) : truncate(value);
		}
		String name = elementName(eObject);
		String summary = summary(eObject);
		return summary == null || summary.isBlank() ? name : name + DASH + summary;
	}

	/** The element name: the containment feature for generic classes, otherwise the class. */
	private static String elementName(EObject eObject) {
		EClass eClass = eObject.eClass();
		EStructuralFeature containment = eObject.eContainmentFeature();
		if (containment != null && GENERIC_CLASSES.contains(eClass.getName())) {
			return featureName(containment);
		}
		return typeName(eClass);
	}

	private String summary(EObject eObject) {
		EClass eClass = eObject.eClass();
		return switch (eClass.getName()) {
		case "DocumentRoot", "HealthcareMetadataType", "CustomElementsType" -> null;
		case "Lom" -> text(one(one(eObject, "general"), "title"));
		case "General" -> firstNonEmpty(text(one(eObject, "title")), identifier(one(eObject, "identifier")));
		case "LifeCycle" -> {
			String version = text(one(eObject, "version"));
			yield join(DOT, version.isEmpty() ? "" : "version " + version, vocab(one(eObject, "status")));
		}
		case "Contribute", "ContributeMeta" -> contribute(eObject);
		case "MetaMetadata" -> truncate(join(", ", values(all(eObject, "metadataSchema"))));
		case "Technical" -> firstNonEmpty(truncate(join(", ", values(all(eObject, "format")))),
				value(one(eObject, "location"), "value"));
		case "Educational" -> truncate(join(DOT, join(", ", vocabs(all(eObject, "learningResourceType"))),
				join(", ", vocabs(all(eObject, "context")))));
		case "Rights" -> rights(eObject);
		case "Relation" -> colon(vocab(one(eObject, "kind")), resource(one(eObject, "resource")));
		case "Resource" -> resource(eObject);
		case "Annotation" -> annotation(eObject);
		case "Classification" -> colon(vocab(one(eObject, "purpose")), firstNonEmpty(text(one(eObject, "description")),
				taxonPath(one(eObject, "taxonPath")), text(one(eObject, "keyword"))));
		case "TaxonPath" -> taxonPath(eObject);
		case "Taxon" -> taxon(eObject);
		case "Identifier" -> identifier(eObject);
		case "Requirement" ->
			truncate(join(" | ", all(eObject, "orComposite").stream().map(this::orComposite).toList()));
		case "OrComposite" -> orComposite(eObject);
		case "Keyword" -> keyword(eObject);
		case "DateTime", "Date" ->
			firstNonEmpty(value(one(eObject, "dateTime"), "value"), text(one(eObject, "description")));
		case "DurationBase", "Duration", "TypicalLearningTime" ->
			firstNonEmpty(value(one(eObject, "duration"), "value"), text(one(eObject, "description")));
		case "VCard", "Entity" -> vCardName(rawValue(eObject, "value"));
		case "Size" -> {
			String size = value(eObject, "value");
			yield size.isEmpty() ? "" : size + " bytes";
		}
		case "HealthcareEducationType" -> healthcareEducation(eObject);
		case "CreditsType" -> join(DOT, credit(eObject), value(eObject, "accreditingBody"));
		case "TargetAudienceType" -> targetAudience(eObject);
		case "HealthcareAssetType" -> firstNonEmpty(value(eObject, "medicalImageType"), value(eObject, "specimenType"),
				text(one(eObject, "clinicalHistory")), value(eObject, "orientation"));
		case "ActivityLocationType", "AddressType" -> address(eObject);
		case "CountryType" -> {
			String name = value(eObject, "countryName");
			String code = value(eObject, "countryCode");
			yield name.isEmpty() ? code : code.isEmpty() ? name : name + " (" + code + ")";
		}
		default -> genericSummary(eObject, eClass);
		};
	}

	private String genericSummary(EObject eObject, EClass eClass) {
		if (isA(eClass, "LanguageString")) {
			return languageString(eObject);
		}
		if (isVocabulary(eClass)) {
			String value = vocab(eObject);
			String source = value(one(eObject, "source"), "value");
			return HLOM_SOURCE.equals(source) && !value.isEmpty() ? value + DOT + "HLOM" : value;
		}
		if (hasAttribute(eClass, "value")) {
			return truncate(value(eObject, "value"));
		}
		return null;
	}

	// ---------------------------------------------------------------- summaries

	private String contribute(EObject contribute) {
		String role = vocab(one(contribute, "role"));
		List<EObject> entities = all(contribute, "entity");
		String entity = entities.isEmpty() ? "" : vCardName(rawValue(entities.getFirst(), "value"));
		if (entities.size() > 1) {
			entity += " (+" + (entities.size() - 1) + ")";
		}
		return colon(role, entity);
	}

	private String rights(EObject rights) {
		String cost = vocab(one(rights, "cost"));
		if (!cost.isEmpty()) {
			return "cost: " + cost;
		}
		String copyright = vocab(one(rights, "copyrightAndOtherRestrictions"));
		return copyright.isEmpty() ? text(one(rights, "description")) : "copyright: " + copyright;
	}

	private String resource(EObject resource) {
		return resource == null ? ""
				: firstNonEmpty(value(one(one(resource, "identifier"), "entry"), "value"),
						text(one(resource, "description")));
	}

	private String annotation(EObject annotation) {
		String entity = vCardName(rawValue(one(annotation, "entity"), "value"));
		String date = value(one(one(annotation, "date"), "dateTime"), "value");
		if (date.isEmpty()) {
			return firstNonEmpty(entity, text(one(annotation, "description")));
		}
		return entity.isEmpty() ? date : entity + " (" + date + ")";
	}

	private String taxonPath(EObject taxonPath) {
		if (taxonPath == null) {
			return "";
		}
		List<String> parts = new ArrayList<>();
		parts.add(text(one(taxonPath, "source")));
		for (EObject taxon : all(taxonPath, "taxon")) {
			parts.add(firstNonEmpty(text(one(taxon, "entry")), value(one(taxon, "id"), "value")));
		}
		return truncate(join(PATH, parts));
	}

	private String taxon(EObject taxon) {
		return colon(value(one(taxon, "id"), "value"), text(one(taxon, "entry")));
	}

	private String identifier(EObject identifier) {
		return identifier == null ? ""
				: colon(value(one(identifier, "catalog"), "value"), value(one(identifier, "entry"), "value"));
	}

	private String orComposite(EObject composite) {
		String result = colon(vocab(one(composite, "type")), vocab(one(composite, "name")));
		String min = value(one(composite, "minimumVersion"), "value");
		String max = value(one(composite, "maximumVersion"), "value");
		if (!min.isEmpty()) {
			result += " ≥ " + min;
		}
		if (!max.isEmpty()) {
			result += " ≤ " + max;
		}
		return result.trim();
	}

	private String keyword(EObject keyword) {
		String text = text(keyword);
		String code = join(" ", value(keyword, "source"), value(keyword, "id"));
		return code.isEmpty() ? text : text.isEmpty() ? code : text + " (" + code + ")";
	}

	private String healthcareEducation(EObject education) {
		List<String> credits = new ArrayList<>();
		for (EObject credit : all(education, "credits")) {
			credits.add(credit(credit));
		}
		return firstNonEmpty(truncate(join(", ", credits)), text(one(education, "activityFormat")));
	}

	private String credit(EObject credits) {
		return join(" ", value(credits, "creditType"), value(credits, "numberOfCredits"), value(credits, "creditUnit"));
	}

	private String targetAudience(EObject audience) {
		List<String> parts = new ArrayList<>();
		for (EObject profession : all(audience, "profession")) {
			parts.add(text(profession));
		}
		for (EObject specialty : all(audience, "specialty")) {
			parts.add(text(specialty));
		}
		return firstNonEmpty(truncate(join(DOT, parts)), value(audience, "audienceCategory"));
	}

	private String address(EObject address) {
		EObject country = one(address, "country");
		String countryName = firstNonEmpty(value(country, "countryName"), value(country, "countryCode"));
		String place = join(", ", value(address, "city"), countryName);
		return firstNonEmpty(truncate(place), value(address, "organization"),
				value(one(address, "streetAddressLine"), "value"), value(address, "building"));
	}

	/** {@code "<text> [lang] (+N translations)"} for a language string. */
	private String languageString(EObject languageString) {
		List<EObject> strings = nonEmptyStrings(languageString);
		if (strings.isEmpty()) {
			return "";
		}
		String result = langStringText(strings.getFirst());
		String language = value(strings.getFirst(), "language");
		if (!language.isEmpty()) {
			result += " [" + language + "]";
		}
		int more = strings.size() - 1;
		if (more > 0) {
			result += " (+" + more + (more == 1 ? " translation)" : " translations)");
		}
		return result;
	}

	private String langString(EObject langString) {
		String text = langStringText(langString);
		String language = value(langString, "language");
		if (text.isEmpty()) {
			text = "(empty)";
		}
		return language.isEmpty() ? text : "[" + language + "] " + text;
	}

	private String langStringText(EObject langString) {
		return truncate(value(langString, "value"));
	}

	/** The first text of a language string (without language), or "". */
	private String text(EObject languageString) {
		if (languageString == null) {
			return "";
		}
		List<EObject> strings = nonEmptyStrings(languageString);
		return strings.isEmpty() ? "" : langStringText(strings.getFirst());
	}

	private List<EObject> nonEmptyStrings(EObject languageString) {
		List<EObject> result = new ArrayList<>();
		for (EObject string : all(languageString, "string")) {
			if (!value(string, "value").isBlank()) {
				result.add(string);
			}
		}
		return result;
	}

	/** The value of a vocabulary element, or "". */
	private String vocab(EObject vocabulary) {
		return vocabulary == null ? "" : value(one(vocabulary, "value"), "value");
	}

	private List<String> vocabs(List<EObject> vocabularies) {
		return vocabularies.stream().map(this::vocab).distinct().toList();
	}

	private List<String> values(List<EObject> objects) {
		return objects.stream().map(o -> value(o, "value")).toList();
	}

	// ---------------------------------------------------------------- reflective access

	/** The contained objects of a reference (adapting them), or an empty list. */
	private List<EObject> all(EObject eObject, String featureName) {
		if (eObject == null) {
			return List.of();
		}
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature(featureName);
		if (!(feature instanceof EReference)) {
			return List.of();
		}
		Object value = eObject.eGet(feature);
		List<EObject> result = new ArrayList<>();
		if (value instanceof Collection<?> collection) {
			for (Object element : collection) {
				if (element instanceof EObject child) {
					result.add(track(child));
				}
			}
		} else if (value instanceof EObject child) {
			result.add(track(child));
		}
		return result;
	}

	private EObject one(EObject eObject, String featureName) {
		List<EObject> all = all(eObject, featureName);
		return all.isEmpty() ? null : all.getFirst();
	}

	private EObject track(EObject eObject) {
		if (tracker != null) {
			tracker.adapt(eObject, IItemLabelProvider.class);
		}
		return eObject;
	}

	/** The string value of an attribute (multiple values joined with ", "), or "". */
	private static String value(EObject eObject, String featureName) {
		if (eObject == null) {
			return "";
		}
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature(featureName);
		if (!(feature instanceof EAttribute attribute) || !eObject.eIsSet(attribute)) {
			return "";
		}
		Object value = eObject.eGet(attribute);
		if (value instanceof Collection<?> collection) {
			List<String> parts = new ArrayList<>();
			for (Object element : collection) {
				parts.add(convert(attribute.getEAttributeType(), element));
			}
			return join(", ", parts);
		}
		return convert(attribute.getEAttributeType(), value);
	}

	/** The unnormalized value of a single-valued string attribute (e.g. a vCard with its line breaks), or "". */
	private static String rawValue(EObject eObject, String featureName) {
		EStructuralFeature feature = eObject == null ? null : eObject.eClass().getEStructuralFeature(featureName);
		if (!(feature instanceof EAttribute)) {
			return "";
		}
		Object value = eObject.eGet(feature);
		return value instanceof String string ? string : value(eObject, featureName);
	}

	private static String convert(EDataType type, Object value) {
		if (value == null) {
			return "";
		}
		String result;
		try {
			result = EcoreUtil.convertToString(type, value);
		} catch (RuntimeException _) {
			result = String.valueOf(value);
		}
		return result == null ? "" : result.replaceAll("\\s+", " ").trim();
	}

	private static boolean hasAttribute(EClass eClass, String name) {
		return eClass.getEStructuralFeature(name) instanceof EAttribute;
	}

	private static boolean isA(EClass eClass, String name) {
		if (eClass.getName().equals(name)) {
			return true;
		}
		return eClass.getEAllSuperTypes().stream().anyMatch(s -> s.getName().equals(name));
	}

	private static boolean isVocabulary(EClass eClass) {
		if (eClass.getName().endsWith("Vocab")) {
			return true;
		}
		return eClass.getEAllSuperTypes().stream().anyMatch(s -> s.getName().endsWith("Vocab"));
	}

	// ---------------------------------------------------------------- strings

	private static String colon(String head, String tail) {
		if (head.isEmpty()) {
			return tail;
		}
		return tail.isEmpty() ? head : head + ": " + tail;
	}

	private static String join(String separator, String... parts) {
		return join(separator, List.of(parts));
	}

	private static String join(String separator, List<String> parts) {
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (part != null && !part.isBlank()) {
				if (builder.length() > 0) {
					builder.append(separator);
				}
				builder.append(part.trim());
			}
		}
		return builder.toString();
	}

	private static String firstNonEmpty(String... candidates) {
		for (String candidate : candidates) {
			if (candidate != null && !candidate.isBlank()) {
				return candidate;
			}
		}
		return "";
	}

	private static String unescape(String vCardValue) {
		return vCardValue.replace("\\n", " ").replace("\\N", " ").replace("\\,", ",").replace("\\;", ";")
				.replace("\\\\", "\\").trim();
	}

	/** {@code "creditsAvailable"} becomes {@code "Credits available"}. */
	private static String humanize(String name) {
		if (name == null || name.isEmpty()) {
			return "";
		}
		String spaced = name.replaceAll("([a-z0-9])([A-Z])", "$1 $2").toLowerCase();
		return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
	}

	private static String string(String key, String fallback) {
		try {
			return HlomEditPlugin.INSTANCE.getString(key);
		} catch (MissingResourceException _) {
			return fallback;
		}
	}
}
