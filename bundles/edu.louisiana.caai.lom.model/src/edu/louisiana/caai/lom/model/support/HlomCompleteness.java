/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.lom.Rights;
import edu.louisiana.caai.lom.model.lom.Technical;
import edu.louisiana.caai.lom.model.lom.VCard;

/**
 * How completely an HLOM record is described, measured against a list of recommended elements.
 * <p>
 * The Healthcare LOM specification makes almost every element optional; the recommended items here follow IEEE
 * LOM best practice (title, description, keywords, language, lifecycle, technical and rights information) and the
 * healthcare requirements mapped in the HLOM specification (terminology-coded keywords, the HLOM metadata schemas,
 * target audience, credits, disclosures, expiration, image descriptors). Each item has a weight (1 = nice to have,
 * 3 = essential); category and overall scores are the weighted share of filled items, from 0 to 100.
 * <p>
 * Applicability rules:
 * <ul>
 * <li>The record describes an <em>educational offering</em> if it has {@code healthcareEducation}, or an
 * {@code educational/context} of professional education (undergraduate or graduate professional education,
 * continuing professional development). Only then do the healthcare education items, the typical learning time
 * and the educational objectives (classification) apply.</li>
 * <li>Credit details apply only when {@code creditsAvailable} is {@code yes}.</li>
 * <li>The record describes a <em>media asset</em> if it has {@code healthcareAsset}, or a learning resource type
 * of image, video or animation. Only then do the healthcare asset items apply.</li>
 * <li>Relation and annotation are never required and are not assessed.</li>
 * </ul>
 * The assessment only reads the document.
 */
public final class HlomCompleteness {

	/** Page id of the general category. */
	public static final String GENERAL = "general";
	/** Page id of the lifecycle category. */
	public static final String LIFECYCLE = "lifecycle";
	/** Page id of the meta-metadata category. */
	public static final String META_METADATA = "metametadata";
	/** Page id of the technical category. */
	public static final String TECHNICAL = "technical";
	/** Page id of the educational category. */
	public static final String EDUCATIONAL = "educational";
	/** Page id of the rights category. */
	public static final String RIGHTS = "rights";
	/** Page id of the classification category. */
	public static final String CLASSIFICATION = "classification";
	/** Page id of the healthcare education extension. */
	public static final String HEALTHCARE_EDUCATION = "healthcare-education";
	/** Page id of the healthcare asset extension. */
	public static final String HEALTHCARE_ASSET = "healthcare-asset";

	/** Educational contexts that denote professional education (HLOM vocabulary). */
	public static final Set<String> PROFESSIONAL_CONTEXTS = Set.of("undergraduate professional education",
			"graduate professional education", "continuing professional development");

	/** Learning resource types that denote a media asset. */
	public static final Set<String> MEDIA_RESOURCE_TYPES = Set.of("image", "video", "animation");

	/**
	 * A recommended element.
	 *
	 * @param label  short name, e.g. "Description"
	 * @param pageId the editor page where the element is edited
	 * @param filled whether the document provides it
	 * @param hint   what to do when it is missing
	 * @param weight importance from 1 (nice to have) to 3 (essential)
	 */
	public record Item(String label, String pageId, boolean filled, String hint, int weight) {
	}

	/**
	 * The recommended elements of one category.
	 *
	 * @param pageId     the editor page of the category
	 * @param label      the category name
	 * @param healthcare whether the category is a MedBiquitous healthcare extension
	 * @param items      the applicable items; empty if the category does not apply to this document
	 */
	public record Category(String pageId, String label, boolean healthcare, List<Item> items) {

		/** Whether any item applies to the document. */
		public boolean applicable() {
			return !items.isEmpty();
		}

		/** Number of filled items. */
		public int filledCount() {
			return (int) items.stream().filter(Item::filled).count();
		}

		/** Weighted share of filled items, 0-100 (100 if the category does not apply). */
		public int score() {
			return HlomCompleteness.score(items);
		}
	}

	private final List<Category> categories;
	private final boolean educationalOffering;
	private final boolean mediaAsset;

	private HlomCompleteness(List<Category> categories, boolean educationalOffering, boolean mediaAsset) {
		this.categories = Collections.unmodifiableList(categories);
		this.educationalOffering = educationalOffering;
		this.mediaAsset = mediaAsset;
	}

	/** Assesses a document (an empty assessment with score 0 if it has no {@code lom} element). */
	public static HlomCompleteness assess(DocumentRoot root) {
		return assess(root == null ? null : root.getLom());
	}

	/** Assesses the {@code lom} element of a document. */
	public static HlomCompleteness assess(Lom lom) {
		if (lom == null) {
			lom = LomFactory.eINSTANCE.createLom();
		}
		return new Builder(lom).build();
	}

	/** The categories in editor order, including those that do not apply (with no items). */
	public List<Category> categories() {
		return categories;
	}

	/** The category of a page, or {@code null}. */
	public Category category(String pageId) {
		return categories.stream().filter(c -> c.pageId().equals(pageId)).findFirst().orElse(null);
	}

	/** All applicable items. */
	public List<Item> items() {
		return categories.stream().flatMap(c -> c.items().stream()).toList();
	}

	/** Overall weighted score, 0-100. */
	public int score() {
		return score(items());
	}

	/** Whether the record describes an educational offering (see class comment). */
	public boolean isEducationalOffering() {
		return educationalOffering;
	}

	/** Whether the record describes a media asset (see class comment). */
	public boolean isMediaAsset() {
		return mediaAsset;
	}

	/**
	 * The most valuable missing items: highest weight first, then in editor order.
	 *
	 * @param max the maximum number of items
	 */
	public List<Item> nextSteps(int max) {
		return items().stream().filter(item -> !item.filled())
				.sorted(Comparator.comparingInt(Item::weight).reversed()).limit(Math.max(0, max)).toList();
	}

	static int score(List<Item> items) {
		int total = items.stream().mapToInt(Item::weight).sum();
		if (total == 0) {
			return 100;
		}
		int filled = items.stream().filter(Item::filled).mapToInt(Item::weight).sum();
		return (int) Math.round(filled * 100.0 / total);
	}

	/** Collects the items of a document. */
	private static final class Builder {

		private final Lom lom;
		private final General general;
		private final LifeCycle lifeCycle;
		private final MetaMetadata metaMetadata;
		private final Technical technical;
		private final Educational educational;
		private final Rights rights;
		private final HealthcareEducationType education;
		private final HealthcareAssetType asset;
		private final boolean offering;
		private final boolean media;
		private List<Item> items;
		private String pageId;

		Builder(Lom lom) {
			this.lom = lom;
			general = first(lom.getGeneral());
			lifeCycle = first(lom.getLifeCycle());
			metaMetadata = first(lom.getMetaMetadata());
			technical = first(lom.getTechnical());
			educational = first(lom.getEducational());
			rights = first(lom.getRights());
			HealthcareMetadataType healthcare = first(lom.getHealthcareMetadata());
			education = healthcare == null ? null : healthcare.getHealthcareEducation();
			asset = healthcare == null ? null : healthcare.getHealthcareAsset();
			offering = education != null || educationalValues(e -> e.getContext().stream())
					.anyMatch(PROFESSIONAL_CONTEXTS::contains);
			media = asset != null || educationalValues(e -> e.getLearningResourceType().stream())
					.anyMatch(MEDIA_RESOURCE_TYPES::contains);
		}

		HlomCompleteness build() {
			List<Category> categories = new ArrayList<>();
			categories.add(general());
			categories.add(lifeCycle());
			categories.add(metaMetadata());
			categories.add(technical());
			categories.add(educational());
			categories.add(rights());
			categories.add(classification());
			categories.add(healthcareEducation());
			categories.add(healthcareAsset());
			return new HlomCompleteness(categories, offering, media);
		}

		private Category general() {
			start(GENERAL);
			add("Title", general != null && !general.getTitle().isEmpty() && hasText(general.getTitle().getFirst()), 3,
					"Give the resource a clear, descriptive title.");
			add("Description", general != null && general.getDescription().stream().anyMatch(Builder::hasText), 3,
					"Summarize what the resource covers and who it is for.");
			add("Keywords", general != null && general.getKeyword().stream().anyMatch(Builder::hasText), 3,
					"Add keywords for the main topics.");
			add("Terminology-coded keywords",
					general != null && general.getKeyword().stream()
							.anyMatch(k -> !isBlank(k.getSource()) && !isBlank(k.getId())),
					2, "Code keywords with a medical terminology, e.g. MeSH source and descriptor id.");
			add("Language", general != null && general.getLanguage().stream().anyMatch(l -> !isBlank(l.getValue())),
					2, "State the language of the resource, e.g. en-US.");
			add("Identifier",
					general != null && general.getIdentifier().stream().anyMatch(i -> !i.getEntry().isEmpty()), 1,
					"Add a catalog identifier such as a URL or DOI.");
			return finish("General", false);
		}

		private Category lifeCycle() {
			start(LIFECYCLE);
			add("Version", lifeCycle != null && lifeCycle.getVersion().stream().anyMatch(Builder::hasText), 1,
					"Record the edition or version of the resource.");
			add("Status", lifeCycle != null && lifeCycle.getStatus().stream().anyMatch(s -> Vocabularies.getValue(s) != null),
					1, "Set the status (draft, final, revised).");
			List<Contribute> contributions = lifeCycle == null ? List.of() : lifeCycle.getContribute();
			add("Contributors", contributions.stream().anyMatch(c -> c.getEntity().stream().anyMatch(Builder::hasText)),
					2, "Name the author and publisher.");
			add("Contribution dates",
					contributions.stream().anyMatch(c -> c.getDate().stream()
							.anyMatch(d -> d.getDateTime().stream().anyMatch(v -> !isBlank(v.getValue())))),
					1, "Date the contributions, e.g. the publication or review date.");
			return finish("Lifecycle", false);
		}

		private Category metaMetadata() {
			start(META_METADATA);
			add("LOMv1.0 schema", declares(HlomDocuments.LOM_METADATA_SCHEMA), 3,
					"Declare the metadata schema LOMv1.0 (HLOM conformance).");
			add("HEALTHCARE_LOMv1 schema", declares(HlomDocuments.HEALTHCARE_METADATA_SCHEMA), 3,
					"Declare the metadata schema HEALTHCARE_LOMv1 (HLOM conformance).");
			add("Metadata language",
					metaMetadata != null && metaMetadata.getLanguage().stream().anyMatch(l -> !isBlank(l.getValue())),
					1, "State the language of this metadata record.");
			add("Metadata creator",
					metaMetadata != null
							&& metaMetadata.getContribute().stream().anyMatch(c -> c.getEntity().stream().anyMatch(Builder::hasText)),
					1, "Name who created this metadata record.");
			return finish("Meta-Metadata", false);
		}

		private Category technical() {
			start(TECHNICAL);
			add("Format", technical != null && technical.getFormat().stream().anyMatch(f -> !isBlank(f.getValue())), 2,
					"Give the MIME type, e.g. text/html or image/jpeg.");
			add("Location",
					technical != null && technical.getLocation().stream().anyMatch(l -> !isBlank(l.getValue())), 2,
					"Give the URL where the resource can be accessed.");
			return finish("Technical", false);
		}

		private Category educational() {
			start(EDUCATIONAL);
			add("Learning resource type", educationalValues(e -> e.getLearningResourceType().stream()).findAny().isPresent(),
					2, "Classify the resource, e.g. tutorial, case study or image.");
			add("Context", educationalValues(e -> e.getContext().stream()).findAny().isPresent(), 2,
					"Set the educational context, e.g. continuing professional development.");
			add("Intended end user role", educationalValues(e -> e.getIntendedEndUserRole().stream()).findAny().isPresent(),
					1, "Say who uses the resource, e.g. learner or teacher.");
			if (offering) {
				add("Typical learning time",
						lom.getEducational().stream().flatMap(e -> e.getTypicalLearningTime().stream())
								.anyMatch(t -> t.getDuration().stream().anyMatch(d -> !isBlank(d.getValue()))),
						1, "Estimate how long learners need, e.g. PT1H30M.");
			}
			return finish("Educational", false);
		}

		private Category rights() {
			start(RIGHTS);
			add("Cost", rights != null && rights.getCost().stream().anyMatch(c -> Vocabularies.getValue(c) != null), 2,
					"Say whether use of the resource costs money.");
			add("Copyright", rights != null
					&& rights.getCopyrightAndOtherRestrictions().stream().anyMatch(c -> Vocabularies.getValue(c) != null),
					2, "Say whether copyright or other restrictions apply.");
			add("Conditions of use", rights != null && rights.getDescription().stream().anyMatch(Builder::hasText), 1,
					"Describe the license or conditions of use.");
			return finish("Rights", false);
		}

		private Category classification() {
			start(CLASSIFICATION);
			if (offering) {
				add("Objectives or competencies",
						lom.getClassification().stream().map(c -> purposeOf(c))
								.anyMatch(p -> p != null && Set.of("educational objective", "competency",
										"learning outcome").contains(p)),
						2, "Classify by educational objective, competency (e.g. ACGME) or learning outcome.");
			}
			add("Taxonomy", lom.getClassification().stream().anyMatch(c -> !c.getTaxonPath().isEmpty()), 1,
					"Place the resource in a taxonomy such as MeSH or ACGME Core Competencies.");
			return finish("Classification", false);
		}

		private Category healthcareEducation() {
			start(HEALTHCARE_EDUCATION);
			if (offering) {
				TargetAudienceType audience = education == null ? null : education.getTargetAudience();
				add("Target audience",
						audience != null && Stream.concat(audience.getProfession().stream(), audience.getSpecialty().stream())
								.anyMatch(Builder::hasText),
						3, "Name the target professions or specialties, e.g. physician, cardiology.");
				add("Credits available", education != null && education.isSetCreditsAvailable(), 2,
						"Say whether continuing education credits are offered.");
				if (education != null && education.isSetCreditsAvailable()
						&& education.getCreditsAvailable() == YesNoType.YES) {
					add("Credit details", education.getCredits().stream().anyMatch(Builder::hasCreditDetails), 3,
							"Describe the credits: type, number and accrediting body.");
				}
				add("Commercial support disclosed",
						education != null && education.isSetCommercialSupport()
								&& (education.getCommercialSupport() != YesNoType.YES
										|| hasText(education.getCommercialSupportAcknowledgement())),
						2, "Answer whether there is commercial support (and acknowledge it).");
				add("Financial relationships disclosed",
						education != null && education.isSetRelevantFinancialRelationship()
								&& (education.getRelevantFinancialRelationship() != YesNoType.YES
										|| education.getRelevantFinancialRelationshipDisclosure().stream()
												.anyMatch(Builder::hasText)),
						2, "Answer whether faculty have relevant financial relationships (and disclose them).");
				add("Off-label use disclosed",
						education != null && education.isSetOffLabelUse()
								&& (education.getOffLabelUse() != YesNoType.YES
										|| hasText(education.getOffLabelDescription())),
						2, "Answer whether off-label drug use is discussed (and describe it).");
				add("Expiration date", education != null && (education.getExpirationDate() != null
						|| education.getCredits().stream().anyMatch(c -> c.getExpirationDate() != null)), 2,
						"Give the date after which the offering is no longer valid.");
				add("Contact", education != null && !isBlank(education.getContact()), 1,
						"Name a contact for questions about the content.");
			}
			return finish("Healthcare Education", true);
		}

		private Category healthcareAsset() {
			start(HEALTHCARE_ASSET);
			if (media) {
				add("Medical image type", asset != null && asset.isSetMedicalImageType(), 2,
						"Give the image type, e.g. radiograph or micrograph.");
				add("Annotated", asset != null && asset.isSetAnnotated(), 1,
						"Say whether the image is annotated or labeled.");
				add("Orientation or specimen",
						asset != null && (asset.isSetOrientation() || asset.isSetSpecimenType()), 1,
						"Give the orientation or the specimen type shown.");
				add("Image dimensions", asset != null && asset.getFileWidth() != null && asset.getFileHeight() != null,
						1, "Give the width and height in pixels.");
				add("Clinical history", asset != null && hasText(asset.getClinicalHistory()), 1,
						"Describe the clinical history of the patient or subject.");
			}
			return finish("Healthcare Asset", true);
		}

		private void start(String id) {
			pageId = id;
			items = new ArrayList<>();
		}

		private void add(String label, boolean filled, int weight, String hint) {
			items.add(new Item(label, pageId, filled, hint, weight));
		}

		private Category finish(String label, boolean healthcare) {
			return new Category(pageId, label, healthcare, List.copyOf(items));
		}

		private boolean declares(String schema) {
			return metaMetadata != null && metaMetadata.getMetadataSchema().stream()
					.anyMatch(s -> s.getValue() != null && schema.equals(s.getValue().trim()));
		}

		private Stream<String> educationalValues(
				Function<Educational, Stream<? extends EObject>> vocabularies) {
			return lom.getEducational().stream().flatMap(vocabularies).map(Vocabularies::getValue)
					.filter(v -> v != null && !v.isBlank()).map(String::trim);
		}

		private static String purposeOf(Classification classification) {
			return classification.getPurpose().isEmpty() ? null
					: Vocabularies.getValue(classification.getPurpose().getFirst());
		}

		private static boolean hasCreditDetails(CreditsType credits) {
			return credits.isSetCreditType() && credits.getNumberOfCredits() != null;
		}

		private static boolean hasText(LanguageString string) {
			return string != null && string.getString().stream().map(LangString::getValue).anyMatch(Predicate.not(Builder::isBlank));
		}

		private static boolean hasText(VCard vcard) {
			return vcard != null && !isBlank(vcard.getValue());
		}

		private static boolean isBlank(String value) {
			return value == null || value.isBlank();
		}

		private static <T> T first(List<T> list) {
			return list.isEmpty() ? null : list.getFirst();
		}
	}
}
