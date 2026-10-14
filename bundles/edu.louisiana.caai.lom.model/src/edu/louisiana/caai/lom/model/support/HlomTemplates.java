/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;

import edu.louisiana.caai.lom.model.address.AddressFactory;
import edu.louisiana.caai.lom.model.address.CountryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.CreditTypeType;
import edu.louisiana.caai.lom.model.healthcare.CreditUnitType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType;
import edu.louisiana.caai.lom.model.healthcare.OrientationType;
import edu.louisiana.caai.lom.model.healthcare.PacingType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.ContributeMeta;
import edu.louisiana.caai.lom.model.lom.Date;
import edu.louisiana.caai.lom.model.lom.DateTimeValue;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.Entity;
import edu.louisiana.caai.lom.model.lom.Format;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.lom.Rights;
import edu.louisiana.caai.lom.model.lom.Technical;
import edu.louisiana.caai.lom.model.lom.TypicalLearningTime;

/**
 * Named starting points for new Healthcare LOM records. Every template produces a schema-valid, conformant
 * document (meta-metadata declaring both schemas, one {@code healthcareMetadata}) with sensible placeholders
 * that the author replaces.
 */
public final class HlomTemplates {

	/** A document template. */
	public interface Template {

		/** Stable identifier, e.g. {@code cme-course}. */
		String id();

		/** Short display name. */
		String label();

		/** One or two sentences describing what the template contains. */
		String description();

		/** Name of the editor page icon representing the template ({@code icons/pages/<iconId>.png}). */
		String iconId();

		/** Creates a new document with the given title (may be empty) and language. */
		DocumentRoot create(String title, String language);
	}

	/** Builds the template content into a document created by {@link HlomDocuments#createDocument}. */
	@FunctionalInterface
	private interface Content {
		void fill(Builder builder);
	}

	private record SimpleTemplate(String id, String label, String description, String iconId, Content content)
			implements Template {
		@Override
		public DocumentRoot create(String title, String language) {
			String lang = language == null || language.isBlank() ? HlomDocuments.DEFAULT_LANGUAGE : language.trim();
			DocumentRoot root = HlomDocuments.createDocument(title, lang);
			if (content != null) {
				Builder builder = new Builder(root, lang);
				content.fill(builder);
				builder.finish();
			}
			return root;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	/** The minimal record: title, language, meta-metadata and an empty healthcareMetadata. */
	public static final Template BLANK = new SimpleTemplate("blank", "Blank record",
			"A minimal, conformant Healthcare LOM record with just a title and language. Start from scratch.",
			"general", null);

	/** An accredited continuing medical education course. */
	public static final Template CME_COURSE = new SimpleTemplate("cme-course", "CME course",
			"An accredited continuing education course: authors and publisher, learner context, rights, "
					+ "one CME credit entry, physician audience and disclosures.",
			"healthcare-education", HlomTemplates::cmeCourse);

	/** A medical image used as a learning asset. */
	public static final Template MEDICAL_IMAGE = new SimpleTemplate("medical-image", "Medical image asset",
			"A medical image (JPEG) with technical format, image learning resource type and the healthcare "
					+ "asset details: image type, orientation and dimensions.",
			"healthcare-asset", HlomTemplates::medicalImage);

	/** Education material written for patients. */
	public static final Template PATIENT_EDUCATION = new SimpleTemplate("patient-education",
			"Patient education resource",
			"Plain-language material for patients and caregivers: patient education context, patient audience "
					+ "and a grade 8 reading level.",
			"educational", HlomTemplates::patientEducation);

	/** A live, in-person workshop or conference session. */
	public static final Template LIVE_WORKSHOP = new SimpleTemplate("live-workshop", "Live workshop",
			"A live conference or workshop session with venue, country and start and end date and time.",
			"lifecycle", HlomTemplates::liveWorkshop);

	private static final List<Template> ALL = List.of(BLANK, CME_COURSE, MEDICAL_IMAGE, PATIENT_EDUCATION,
			LIVE_WORKSHOP);

	private HlomTemplates() {
	}

	/** All templates, the blank record first. */
	public static List<Template> all() {
		return ALL;
	}

	/** The template with the given id, or {@link #BLANK} if there is none. */
	public static Template get(String id) {
		for (Template template : ALL) {
			if (template.id().equals(id)) {
				return template;
			}
		}
		return BLANK;
	}

	private static void cmeCourse(Builder b) {
		b.description("Describe the purpose, scope and learning objectives of the course.");
		LifeCycle lifeCycle = b.lifeCycle();
		lifeCycle.getVersion().add(b.languageString(LomFactory.eINSTANCE.createVersion(), "1.0"));
		lifeCycle.getStatus().add(b.vocab(LomPackage.eINSTANCE.getStatus(), "draft"));
		lifeCycle.getContribute().add(b.contribute("author", "Author Name"));
		lifeCycle.getContribute().add(b.contribute("publisher", "Publishing Organization"));
		Educational educational = b.educational();
		educational.getLearningResourceType().add(b.vocab(LomPackage.eINSTANCE.getLearningResourceType(), "tutorial"));
		educational.getIntendedEndUserRole().add(b.vocab(LomPackage.eINSTANCE.getIntendedEndUserRole(), "learner"));
		educational.getContext()
				.add(b.vocab(LomPackage.eINSTANCE.getContext(), "continuing professional development"));
		educational.getTypicalLearningTime().add(b.learningTime("PT1H"));
		b.rights("yes", "Copyright (c) the publisher. All rights reserved.");

		HealthcareFactory hf = HealthcareFactory.eINSTANCE;
		HealthcareEducationType education = hf.createHealthcareEducationType();
		education.setCreditsAvailable(YesNoType.YES);
		CreditsType credits = hf.createCreditsType();
		credits.setAccreditingBody("ACCME");
		credits.setCreditType(CreditTypeType.CME);
		credits.setCreditUnit(CreditUnitType.CREDIT);
		credits.setPacing(PacingType.LEARNER_PACED);
		credits.setAccreditedProvider("Accredited Provider");
		credits.setNumberOfCredits(new BigDecimal("1.0"));
		LocalDate today = LocalDate.now();
		credits.setReleaseDate(XMLTypeFactory.eINSTANCE.createDate(today.toString()));
		credits.setExpirationDate(XMLTypeFactory.eINSTANCE.createDate(today.plusYears(1).toString()));
		education.getCredits().add(credits);
		TargetAudienceType audience = hf.createTargetAudienceType();
		audience.getAudienceCategory().add(AudienceCategoryType.PROFESSIONAL);
		audience.getProfession().add(b.languageString(LomFactory.eINSTANCE.createLanguageString(), "physician"));
		education.setTargetAudience(audience);
		education.getParticipationModality().add(ParticipationModalityType.TECHNOLOGY_BASED);
		education.getActivityDelivery().add(ActivityDeliveryType.NOT_LIVE);
		education.setOffLabelUse(YesNoType.NO);
		education.setCommercialSupport(YesNoType.NO);
		education.setRelevantFinancialRelationship(YesNoType.NO);
		education.setContact("Course contact, e-mail address");
		b.healthcare().setHealthcareEducation(education);
	}

	private static void medicalImage(Builder b) {
		b.description("Describe what the image shows.");
		b.lifeCycle().getContribute().add(b.contribute("publisher", "Publishing Organization"));
		Technical technical = b.technical();
		Format format = LomFactory.eINSTANCE.createFormat();
		format.setValue("image/jpeg");
		technical.getFormat().add(format);
		b.educational().getLearningResourceType().add(b.vocab(LomPackage.eINSTANCE.getLearningResourceType(), "image"));
		b.rights("yes", "State the license or terms of use of the image.");

		HealthcareAssetType asset = HealthcareFactory.eINSTANCE.createHealthcareAssetType();
		asset.setAnnotated(YesNoType.NO);
		asset.setOrientation(OrientationType.AXIAL);
		asset.setMedicalImageType(MedicalImageTypeType.PHOTOGRAPH);
		b.healthcare().setHealthcareAsset(asset);
	}

	private static void patientEducation(Builder b) {
		b.description("Explain in plain language what the patient will learn.");
		b.lifeCycle().getContribute().add(b.contribute("publisher", "Publishing Organization"));
		Educational educational = b.educational();
		educational.getLearningResourceType()
				.add(b.vocab(LomPackage.eINSTANCE.getLearningResourceType(), "narrative text"));
		educational.getIntendedEndUserRole().add(b.vocab(LomPackage.eINSTANCE.getIntendedEndUserRole(), "learner"));
		educational.getContext().add(b.vocab(LomPackage.eINSTANCE.getContext(), "patient education"));
		b.rights("no", "Free for patient use.");

		HealthcareFactory hf = HealthcareFactory.eINSTANCE;
		HealthcareEducationType education = hf.createHealthcareEducationType();
		education.setCreditsAvailable(YesNoType.NO);
		TargetAudienceType audience = hf.createTargetAudienceType();
		audience.getAudienceCategory().add(AudienceCategoryType.PATIENT);
		audience.getReadingLevel().add(b.languageString(LomFactory.eINSTANCE.createLanguageString(), "grade 8"));
		education.setTargetAudience(audience);
		education.getParticipationModality().add(ParticipationModalityType.PRINT);
		education.getActivityDelivery().add(ActivityDeliveryType.NOT_LIVE);
		b.healthcare().setHealthcareEducation(education);
	}

	private static void liveWorkshop(Builder b) {
		b.description("Describe the workshop agenda and learning objectives.");
		LifeCycle lifeCycle = b.lifeCycle();
		lifeCycle.getContribute().add(b.contribute("author", "Faculty Name"));
		lifeCycle.getContribute().add(b.contribute("publisher", "Organizing Institution"));
		Educational educational = b.educational();
		educational.getIntendedEndUserRole().add(b.vocab(LomPackage.eINSTANCE.getIntendedEndUserRole(), "learner"));
		educational.getContext()
				.add(b.vocab(LomPackage.eINSTANCE.getContext(), "continuing professional development"));
		b.rights("yes", "Registration required.");

		HealthcareFactory hf = HealthcareFactory.eINSTANCE;
		HealthcareEducationType education = hf.createHealthcareEducationType();
		ActivityLocationType location = hf.createActivityLocationType();
		location.getOrganization().add("Venue Name");
		location.setCity("Lafayette");
		location.setStateOrProvince("LA");
		CountryType country = AddressFactory.eINSTANCE.createCountryType();
		country.setCountryName("United States");
		country.setCountryCode("US");
		location.setCountry(country);
		location.setRoom("Room");
		location.setBuilding("Building");
		education.setActivityLocation(location);
		LocalDate day = LocalDate.now().plusMonths(1);
		education.setStartDateTime(XMLTypeFactory.eINSTANCE.createDateTime(day + "T09:00:00"));
		education.setEndDateTime(XMLTypeFactory.eINSTANCE.createDateTime(day + "T17:00:00"));
		education.getParticipationModality().add(ParticipationModalityType.CONFERENCE_WORKSHOP);
		education.getActivityDelivery().add(ActivityDeliveryType.LIVE);
		education.setContact("Workshop contact, e-mail address");
		b.healthcare().setHealthcareEducation(education);
	}

	/**
	 * Adds the LOM categories in the canonical order (general, lifeCycle, metaMetadata, technical, educational,
	 * rights, healthcareMetadata) so that the XML reads naturally.
	 */
	/**
	 * Records a person as the author of a new document: a dated Life Cycle contribution with role "author" and a
	 * Meta-Metadata contribution with role "creator", both with the given vCard.
	 *
	 * @param vCard the author's vCard (BEGIN:VCARD ... END:VCARD)
	 * @param date  the contribution date in LOM DateTime form, e.g. {@code 2026-10-12}
	 */
	public static void addAuthor(DocumentRoot root, String vCard, String date) {
		Lom lom = root.getLom();
		if (lom == null || vCard == null || vCard.isBlank()) {
			return;
		}
		LomFactory factory = LomFactory.eINSTANCE;
		LomPackage lomPackage = LomPackage.eINSTANCE;

		Contribute contribute = factory.createContribute();
		contribute.getRole().add(Builder.vocabulary(lomPackage.getRole(), "author"));
		contribute.getEntity().add(entity(vCard));
		contribute.getDate().add(date(date));
		if (lom.getLifeCycle().isEmpty()) {
			LifeCycle lifeCycle = factory.createLifeCycle();
			lifeCycle.getContribute().add(contribute);
			// Keep the canonical category order: the life cycle follows the general category.
			int index = 0;
			for (int i = 0; i < lom.getGroup().size(); i++) {
				if (lom.getGroup().getEStructuralFeature(i) == lomPackage.getLom_General()) {
					index = i + 1;
				}
			}
			lom.getGroup().add(index, lomPackage.getLom_LifeCycle(), lifeCycle);
		} else {
			lom.getLifeCycle().getFirst().getContribute().add(0, contribute);
		}

		if (!lom.getMetaMetadata().isEmpty()) {
			ContributeMeta creator = factory.createContributeMeta();
			creator.getRole().add(Builder.vocabulary(lomPackage.getRoleMeta(), "creator"));
			creator.getEntity().add(entity(vCard));
			creator.getDate().add(date(date));
			lom.getMetaMetadata().getFirst().getContribute().add(creator);
		}
	}

	private static Entity entity(String vCard) {
		Entity entity = LomFactory.eINSTANCE.createEntity();
		entity.setValue(vCard);
		return entity;
	}

	private static Date date(String value) {
		Date date = LomFactory.eINSTANCE.createDate();
		DateTimeValue dateTime = LomFactory.eINSTANCE.createDateTimeValue();
		dateTime.setValue(value);
		date.getDateTime().add(dateTime);
		return date;
	}

	private static final class Builder {
		private final LomFactory factory = LomFactory.eINSTANCE;
		private final Lom lom;
		private final String language;
		private final MetaMetadata metaMetadata;
		private final HealthcareMetadataType healthcare;
		private LifeCycle lifeCycle;
		private Technical technical;
		private Educational educational;
		private Rights rights;

		Builder(DocumentRoot root, String language) {
			this.lom = root.getLom();
			this.language = language;
			this.metaMetadata = lom.getMetaMetadata().getFirst();
			this.healthcare = lom.getHealthcareMetadata().getFirst();
			lom.getMetaMetadata().clear();
			lom.getHealthcareMetadata().clear();
		}

		General general() {
			return lom.getGeneral().getFirst();
		}

		void description(String text) {
			general().getDescription().add(languageString(factory.createLanguageString(), text));
		}

		LifeCycle lifeCycle() {
			if (lifeCycle == null) {
				lifeCycle = factory.createLifeCycle();
			}
			return lifeCycle;
		}

		Technical technical() {
			if (technical == null) {
				technical = factory.createTechnical();
			}
			return technical;
		}

		Educational educational() {
			if (educational == null) {
				educational = factory.createEducational();
			}
			return educational;
		}

		void rights(String cost, String description) {
			rights = factory.createRights();
			rights.getCost().add(vocab(LomPackage.eINSTANCE.getCost(), cost));
			rights.getCopyrightAndOtherRestrictions()
					.add(vocab(LomPackage.eINSTANCE.getCopyrightAndOtherRestrictions(), "yes"));
			rights.getDescription().add(languageString(factory.createDescription(), description));
		}

		HealthcareMetadataType healthcare() {
			return healthcare;
		}

		void finish() {
			if (lifeCycle != null) {
				lom.getLifeCycle().add(lifeCycle);
			}
			lom.getMetaMetadata().add(metaMetadata);
			if (technical != null) {
				lom.getTechnical().add(technical);
			}
			if (educational != null) {
				lom.getEducational().add(educational);
			}
			if (rights != null) {
				lom.getRights().add(rights);
			}
			lom.getHealthcareMetadata().add(healthcare);
		}

		<T extends LanguageString> T languageString(T string, String text) {
			string.getString().add(HlomDocuments.langString(text, language));
			return string;
		}

		Contribute contribute(String role, String name) {
			Contribute contribute = factory.createContribute();
			contribute.getRole().add(vocab(LomPackage.eINSTANCE.getRole(), role));
			Entity entity = factory.createEntity();
			entity.setValue("BEGIN:VCARD\nVERSION:3.0\nFN:" + name + "\nEND:VCARD");
			contribute.getEntity().add(entity);
			return contribute;
		}

		TypicalLearningTime learningTime(String isoDuration) {
			TypicalLearningTime time = factory.createTypicalLearningTime();
			var duration = factory.createDurationValue();
			duration.setValue(isoDuration);
			time.getDuration().add(duration);
			return time;
		}

		<T extends EObject> T vocab(EClass vocabClass, String value) {
			return vocabulary(vocabClass, value);
		}

		/** Creates a vocabulary element ({@code source} + {@code value}) for a term of the class's vocabulary. */
		@SuppressWarnings("unchecked")
		static <T extends EObject> T vocabulary(EClass vocabClass, String value) {
			Vocabularies.Term term = Objects.requireNonNull(Vocabularies.findTerm(vocabClass, value),
					() -> "Unknown term " + value + " for " + vocabClass.getName());
			EObject vocab = EcoreUtil.create(vocabClass);
			EReference sourceReference = Vocabularies.sourceReference(vocabClass);
			EReference valueReference = Vocabularies.valueReference(vocabClass);
			((List<EObject>) vocab.eGet(sourceReference)).add(simpleContent(sourceReference.getEReferenceType(),
					EcoreUtil.createFromString(Vocabularies.sourceDataType(), term.source())));
			((List<EObject>) vocab.eGet(valueReference))
					.add(simpleContent(valueReference.getEReferenceType(), term.literal()));
			return (T) vocab;
		}

		private static EObject simpleContent(EClass eClass, Object value) {
			EObject element = EcoreUtil.create(eClass);
			element.eSet(Vocabularies.contentAttribute(eClass), value);
			return element;
		}
	}
}
