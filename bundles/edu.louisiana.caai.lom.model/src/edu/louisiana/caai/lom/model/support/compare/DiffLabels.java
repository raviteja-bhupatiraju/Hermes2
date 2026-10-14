/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.util.Locale;
import java.util.Map;

/**
 * Human labels for HLOM element and attribute names: a table of names that read better spelled out
 * ({@code contribute} → {@code Contributor}), otherwise the camel case name in sentence case
 * ({@code creditsAvailable} → {@code Credits available}).
 */
public final class DiffLabels {

	private static final Map<String, String> LABELS = Map.ofEntries(
			Map.entry("lifeCycle", "Life Cycle"),
			Map.entry("metaMetadata", "Meta-Metadata"),
			Map.entry("healthcareEducation", "Healthcare Education"),
			Map.entry("healthcareAsset", "Healthcare Asset"),
			Map.entry("healthcareMetadata", "Healthcare Metadata"),
			Map.entry("customElements", "Custom elements"),
			Map.entry("contribute", "Contributor"),
			Map.entry("dateTime", "Date/time"),
			Map.entry("id", "ID"),
			Map.entry("orComposite", "Requirement"),
			Map.entry("startDateTime", "Start date/time"),
			Map.entry("endDateTime", "End date/time"),
			Map.entry("offLabelUse", "Off-label use"),
			Map.entry("offLabelDescription", "Off-label description"),
			Map.entry("relevantFinancialRelationship", "Relevant financial relationship"),
			Map.entry("relevantFinancialRelationshipDisclosure", "Financial relationship disclosure"),
			Map.entry("commercialSupportAcknowledgement", "Commercial support acknowledgement"),
			Map.entry("copyrightAndOtherRestrictions", "Copyright and other restrictions"),
			Map.entry("intendedEndUserRole", "Intended end user role"),
			Map.entry("typicalLearningTime", "Typical learning time"),
			Map.entry("medicalImageType", "Medical image type"),
			Map.entry("countryCode", "Country code"),
			Map.entry("streetAddressLine", "Street address"),
			Map.entry("stateOrProvince", "State or province"),
			Map.entry("#text", "Text"));

	private DiffLabels() {
	}

	/** The label of an element or attribute, given its XML local name. */
	public static String label(String name) {
		if (name == null || name.isEmpty()) {
			return "";
		}
		String known = LABELS.get(name);
		if (known != null) {
			return known;
		}
		StringBuilder label = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '_' || c == '-') {
				label.append(' ');
			} else if (i > 0 && Character.isUpperCase(c) && !Character.isUpperCase(name.charAt(i - 1))) {
				label.append(' ').append(Character.toLowerCase(c));
			} else {
				label.append(c);
			}
		}
		String text = label.toString().replaceAll("\\s+", " ").strip();
		return text.substring(0, 1).toUpperCase(Locale.ROOT) + text.substring(1);
	}

	/** The label of an attribute of a keyword ({@code hx:source}, {@code hx:id}). */
	static String keywordAttribute(String name) {
		return switch (name) {
		case "source" -> "Vocabulary";
		case "id" -> "Term ID";
		default -> label(name);
		};
	}
}
