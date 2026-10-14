/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.search;

import java.util.List;
import java.util.Set;

/** The field groups a search of Healthcare LOM documents can be limited to. */
public final class HlomSearchFields {

	public static final FieldGroup TITLES = new FieldGroup("title", "Titles", Set.of("title"), Set.of("general"));
	public static final FieldGroup DESCRIPTIONS = FieldGroup.of("description", "Descriptions", "description",
			"offLabelDescription", "commercialSupportAcknowledgement", "relevantFinancialRelationshipDisclosure",
			"clinicalHistory");
	public static final FieldGroup KEYWORDS = FieldGroup.of("keyword", "Keywords", "keyword");
	public static final FieldGroup CONTRIBUTORS = FieldGroup.of("contributor", "Contributors and sponsors",
			"contribute", "activitySponsorship", "accreditedProvider", "nonAccreditedProvider", "contact");
	public static final FieldGroup IDENTIFIERS = FieldGroup.of("identifier", "Identifiers and locations",
			"identifier", "location", "resource");
	public static final FieldGroup CLASSIFICATIONS = FieldGroup.of("classification", "Classifications",
			"classification");
	public static final FieldGroup CREDITS = FieldGroup.of("credit", "Credits and accreditation", "credits",
			"accreditingBody", "activityCertification", "creditsAvailable");
	public static final FieldGroup AUDIENCE = FieldGroup.of("audience", "Audience", "targetAudience", "specialty",
			"profession", "intendedEndUserRole", "context", "audienceCategory", "readingLevel");

	/** All groups, in the order shown to users. */
	public static final List<FieldGroup> ALL = List.of(TITLES, DESCRIPTIONS, KEYWORDS, CONTRIBUTORS, IDENTIFIERS,
			CLASSIFICATIONS, CREDITS, AUDIENCE);

	private HlomSearchFields() {
	}

	/** The group with an id (also in the plural, such as {@code keywords}, or by its label), or {@code null}. */
	public static FieldGroup byId(String id) {
		String key = id.strip().toLowerCase(java.util.Locale.ROOT);
		return ALL.stream()
				.filter(group -> group.id().equals(key) || (group.id() + "s").equals(key)
						|| group.label().toLowerCase(java.util.Locale.ROOT).equals(key))
				.findFirst().orElse(null);
	}
}
