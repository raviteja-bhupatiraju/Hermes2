/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.compare;

import java.util.List;

import edu.louisiana.caai.lom.model.support.search.FieldLabels;

/**
 * A group of compared fields: the LOM categories and Healthcare LOM parts (in document order), or for documents of
 * other standards a part of their root element. Each has a label and the name of the matching editor page icon
 * ({@code icons/pages/<icon>.png}).
 *
 * @param elementName the XML (local) name of the category element, {@code null} for {@link #OTHER}
 * @param label       a human readable name, e.g. {@code Life Cycle}
 * @param icon        the icon name, e.g. {@code lifecycle} for {@code icons/pages/lifecycle.png}
 */
public record DiffCategory(String elementName, String label, String icon) {

	public static final DiffCategory GENERAL = new DiffCategory("general", "General", "general");
	public static final DiffCategory LIFE_CYCLE = new DiffCategory("lifeCycle", "Life Cycle", "lifecycle");
	public static final DiffCategory META_METADATA = new DiffCategory("metaMetadata", "Meta-Metadata", "metametadata");
	public static final DiffCategory TECHNICAL = new DiffCategory("technical", "Technical", "technical");
	public static final DiffCategory EDUCATIONAL = new DiffCategory("educational", "Educational", "educational");
	public static final DiffCategory RIGHTS = new DiffCategory("rights", "Rights", "rights");
	public static final DiffCategory RELATION = new DiffCategory("relation", "Relation", "relation");
	public static final DiffCategory ANNOTATION = new DiffCategory("annotation", "Annotation", "annotation");
	public static final DiffCategory CLASSIFICATION = new DiffCategory("classification", "Classification",
			"classification");
	public static final DiffCategory HEALTHCARE_EDUCATION = new DiffCategory("healthcareEducation",
			"Healthcare Education", "healthcare-education");
	public static final DiffCategory HEALTHCARE_ASSET = new DiffCategory("healthcareAsset", "Healthcare Asset",
			"healthcare-asset");
	/** Custom elements, extensions and anything else. */
	public static final DiffCategory OTHER = new DiffCategory(null, "Other", "tree");

	/** The Healthcare LOM categories in document order (then {@link #OTHER}). */
	public static final List<DiffCategory> HLOM = List.of(GENERAL, LIFE_CYCLE, META_METADATA, TECHNICAL, EDUCATIONAL,
			RIGHTS, RELATION, ANNOTATION, CLASSIFICATION, HEALTHCARE_EDUCATION, HEALTHCARE_ASSET, OTHER);

	/** Whether this is one of the healthcare extension parts. */
	public boolean isHealthcare() {
		return equals(HEALTHCARE_EDUCATION) || equals(HEALTHCARE_ASSET);
	}

	/** The Healthcare LOM category of a top-level element (by XML local name), {@code null} if it isn't one. */
	public static DiffCategory of(String elementName) {
		for (DiffCategory category : HLOM) {
			if (category.elementName != null && category.elementName.equals(elementName)) {
				return category;
			}
		}
		return null;
	}

	/** The category of a part of the root element of a document of another standard. */
	public static DiffCategory part(String elementName) {
		return new DiffCategory(elementName, FieldLabels.words(elementName), "general");
	}

	/** The position of a category in the Healthcare LOM order, or {@link Integer#MAX_VALUE} for other parts. */
	int order() {
		int index = HLOM.indexOf(this);
		return index >= 0 ? index : Integer.MAX_VALUE;
	}
}
