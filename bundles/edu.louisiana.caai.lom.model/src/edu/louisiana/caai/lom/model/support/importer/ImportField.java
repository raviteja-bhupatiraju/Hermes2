/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import edu.louisiana.caai.lom.model.lom.LomPackage;

/**
 * The Healthcare LOM fields a CSV column can be imported into, with their labels, the header names they are
 * recognized by and how a cell value is applied to a record. Fields are applied in declaration order, so that
 * e.g. contributors exist before a publication date is attached to them.
 */
public enum ImportField {

	// General
	TITLE(Category.GENERAL, "Title", "1.2 General.Title", "Hand Hygiene for Clinical Staff", false,
			(b, v) -> b.title(v), "title", "name", "course title", "course name", "activity title", "activity name",
			"resource title", "resource name", "record title", "item title"),
	DESCRIPTION(Category.GENERAL, "Description", "1.4 General.Description", "Learn the five moments...", false,
			RecordBuilder::description, "description", "summary", "abstract", "overview", "synopsis", "details",
			"course description", "activity description", "about"),
	KEYWORDS(Category.GENERAL, "Keywords", "1.5 General.Keyword; separate with ; and tag with [MeSH:D…]",
			"Sepsis [MeSH:D018805]; triage", true, RecordBuilder::keywords, "keywords", "keyword", "tags", "tag",
			"subjects", "subject", "topics", "topic", "mesh", "mesh terms", "mesh headings", "mesh keywords"),
	LANGUAGE(Category.GENERAL, "Language", "1.3 General.Language (en, en-US, English, Español)", "en-US", false,
			RecordBuilder::language, "language", "lang", "locale", "language code"),
	IDENTIFIER(Category.GENERAL, "Identifier", "1.1 General.Identifier: URL, DOI:…, ISBN …, catalog:entry",
			"CAAI:CME-2026-014", true, RecordBuilder::identifier, "identifier", "id", "course id", "course code",
			"activity id", "activity code", "code", "catalog number", "doi", "isbn", "uri", "urn", "record id"),

	// Life cycle
	VERSION(Category.LIFE_CYCLE, "Version", "2.1 LifeCycle.Version", "2.0", false, RecordBuilder::version, "version",
			"edition", "revision", "release"),
	STATUS(Category.LIFE_CYCLE, "Status", "2.2 LifeCycle.Status: draft, final, revised, unavailable", "final", false,
			RecordBuilder::status, "status", "state", "lifecycle status", "publication status"),
	AUTHOR(Category.LIFE_CYCLE, "Author(s)", "2.3 Contribute (role author); Name <email>; …",
			"Dr. Jane Doe <jdoe@example.org>", true, (b, v) -> b.contributors("author", v), "author", "authors",
			"faculty", "presenter", "presenters", "speaker", "speakers", "instructor", "instructors", "creator",
			"created by", "by", "planner", "course director", "lead author"),
	PUBLISHER(Category.LIFE_CYCLE, "Publisher", "2.3 Contribute (role publisher)", "UL Lafayette College of Nursing",
			true, (b, v) -> b.contributors("publisher", v), "publisher", "published by", "institution",
			"publishing organization", "owner", "department", "school", "organisation", "organization"),
	REVIEWER(Category.LIFE_CYCLE, "Reviewer(s)", "2.3 Contribute (healthcare role reviewer)", "Peer Review Committee",
			true, (b, v) -> b.contributors("reviewer", v), "reviewer", "reviewers", "reviewed by", "peer reviewer",
			"content reviewer"),
	DATE(Category.LIFE_CYCLE, "Publication date", "2.3.3 Contribute.Date of the publisher (or author)", "2026-03-01",
			false, RecordBuilder::publicationDate, "date", "publication date", "published", "date published",
			"publish date", "created", "date created", "last updated", "updated"),

	// Technical
	FORMAT(Category.TECHNICAL, "Format", "4.1 Technical.Format: MIME type or pdf, html, video, …", "text/html", true,
			RecordBuilder::format, "format", "mime type", "mime", "media type", "file type", "file format",
			"content format"),
	LOCATION(Category.TECHNICAL, "Location (URL)", "4.3 Technical.Location", "https://learn.example.org/course/14",
			true, RecordBuilder::location, "location", "url", "link", "web address", "website", "access url",
			"course url", "launch url", "web link", "address url", "uri link"),
	SIZE(Category.TECHNICAL, "Size", "4.2 Technical.Size in bytes (or 2.4 MB)", "2.4 MB", false, RecordBuilder::size,
			"size", "file size", "bytes", "filesize"),
	DURATION(Category.TECHNICAL, "Media duration", "4.7 Technical.Duration (running time of audio or video)",
			"12 min", false, RecordBuilder::technicalDuration, "duration", "running time", "video length",
			"media duration", "runtime", "play time"),

	// Educational
	LEARNING_RESOURCE_TYPE(Category.EDUCATIONAL, "Learning resource type",
			"5.2 Educational.LearningResourceType: lecture, video, case study, self assessment, …", "video; case study",
			true, (b, v) -> b.educationalVocab("learningResourceType", LomPackage.eINSTANCE.getLearningResourceType(),
					"LearningResourceType", "learning resource type", true, v),
			"learning resource type", "learning resource types", "resource type", "type", "content type",
			"activity type", "item type", "material type", "kind"),
	INTERACTIVITY_TYPE(Category.EDUCATIONAL, "Interactivity type", "5.1 Educational.InteractivityType", "mixed", false,
			(b, v) -> b.educationalVocab("interactivityType", LomPackage.eINSTANCE.getInteractivityType(),
					"InteractivityType", "interactivity type", false, v),
			"interactivity type", "interactivity", "interaction"),
	CONTEXT(Category.EDUCATIONAL, "Context", "5.6 Educational.Context: CME, GME, patient education, …",
			"continuing professional development", true,
			(b, v) -> b.educationalVocab("context", LomPackage.eINSTANCE.getContext(), "Context", "context", true, v),
			"context", "educational context", "setting", "education level", "educational level", "level",
			"learning context"),
	INTENDED_END_USER_ROLE(Category.EDUCATIONAL, "Intended end user role",
			"5.5 Educational.IntendedEndUserRole: learner, teacher, author, manager", "learner", true,
			(b, v) -> b.educationalVocab("intendedEndUserRole", LomPackage.eINSTANCE.getIntendedEndUserRole(),
					"IntendedEndUserRole", "intended end user role", true, v),
			"intended end user role", "end user role", "intended user", "intended users", "user role", "role"),
	DIFFICULTY(Category.EDUCATIONAL, "Difficulty", "5.8 Educational.Difficulty: easy … very difficult",
			"intermediate", false,
			(b, v) -> b.educationalVocab("difficulty", LomPackage.eINSTANCE.getDifficulty(), "Difficulty",
					"difficulty", false, v),
			"difficulty", "level of difficulty", "complexity", "difficulty level", "skill level"),
	TYPICAL_LEARNING_TIME(Category.EDUCATIONAL, "Typical learning time",
			"5.9 Educational.TypicalLearningTime: minutes, 1h30 or PT1H30M", "90", false, RecordBuilder::learningTime,
			"typical learning time", "learning time", "time", "minutes", "length", "seat time", "estimated time",
			"time to complete", "completion time", "duration minutes", "contact time", "hours"),

	// Rights
	COST(Category.RIGHTS, "Cost", "6.1 Rights.Cost: yes/no, free or a price", "free", false, RecordBuilder::cost,
			"cost", "price", "fee", "free", "tuition", "registration fee"),
	COPYRIGHT(Category.RIGHTS, "Copyright / license",
			"6.2 Rights.CopyrightAndOtherRestrictions: yes/no, or the license text (6.3 Description)", "CC BY-NC 4.0",
			false, RecordBuilder::copyright, "copyright", "copyright and other restrictions", "restrictions",
			"license", "licence", "rights", "terms of use", "usage rights"),

	// Classification
	OBJECTIVES(Category.CLASSIFICATION, "Learning objectives",
			"9 Classification (purpose educational objective), one per objective", "Recognize sepsis; Start the bundle",
			true, RecordBuilder::objectives, "learning objectives", "objectives", "objective", "learning outcomes",
			"outcomes", "goals", "learning goals"),
	COMPETENCIES(Category.CLASSIFICATION, "Competencies", "9 Classification (purpose competency), ACGME core competencies",
			"Patient Care; Medical Knowledge", true, RecordBuilder::competencies, "competencies", "competency",
			"acgme competencies", "core competencies", "acgme"),

	// Healthcare education
	CREDITS_AVAILABLE(Category.EDUCATION, "Credits available", "creditsAvailable: yes/no", "yes", false,
			RecordBuilder::creditsAvailable, "credits available", "credit available", "accredited", "cme available",
			"offers credit", "ce available", "credit eligible"),
	CREDIT_TYPE(Category.CREDITS, "Credit type", "credits/creditType: CME, CE, CNE, CPE, CHES, CPD (one per credit)",
			"CME; CNE", true, RecordBuilder::creditType, "credit type", "credit types", "type of credit", "cme type"),
	NUMBER_OF_CREDITS(Category.CREDITS, "Number of credits", "credits/numberOfCredits (one per credit)", "1.5; 2",
			true, RecordBuilder::numberOfCredits, "number of credits", "credits", "credit", "credit amount",
			"max credits", "maximum credits", "credit hours", "cme credits", "ce credits", "contact hours",
			"credit value"),
	CREDIT_UNIT(Category.CREDITS, "Credit unit", "credits/creditUnit: Credit, Contact Hour, Hour, CEU, …",
			"Credit; Contact Hour", true, RecordBuilder::creditUnit, "credit unit", "credit units", "unit", "units"),
	ACCREDITING_BODY(Category.CREDITS, "Accrediting body", "credits/accreditingBody: ACCME, ANCC, ACPE, …",
			"ACCME; ANCC", true, RecordBuilder::accreditingBody, "accrediting body", "accreditor", "accreditation body",
			"accredited by", "accreditation", "accrediting organization"),
	ACTIVITY_CERTIFICATION(Category.CREDITS, "Activity certification",
			"credits/activityCertification: AMA PRA Category 1, AAFP Prescribed, …", "AMA PRA Category 1", true,
			RecordBuilder::activityCertification, "activity certification", "certification", "credit designation",
			"credit category", "designation", "category"),
	ACCREDITED_PROVIDER(Category.CREDITS, "Accredited provider", "credits/accreditedProvider",
			"UL Lafayette Office of CME", true, RecordBuilder::accreditedProvider, "accredited provider", "provider",
			"cme provider", "ce provider", "providership"),
	PACING(Category.CREDITS, "Pacing", "credits/pacing: learner paced (self-paced), provider paced", "self-paced",
			true, RecordBuilder::pacing, "pacing", "pace", "paced", "self paced"),
	RELEASE_DATE(Category.CREDITS, "Credit release date", "credits/releaseDate", "2026-01-15", true,
			RecordBuilder::releaseDate, "release date", "credit release date", "approval date", "valid from",
			"accreditation date", "credit start date"),
	EXPIRATION_DATE(Category.EDUCATION, "Expiration date", "expirationDate (also used for the credits)", "2027-01-14",
			false, RecordBuilder::expirationDate, "expiration date", "expiration", "expires", "expiry", "expiry date",
			"valid until", "valid through", "termination date", "credit expiration date", "end of credit"),
	START_DATE_TIME(Category.EDUCATION, "Start date/time", "startDateTime of a live activity", "2026-11-14 08:00",
			false, RecordBuilder::startDateTime, "start", "start date", "start date time", "start time", "starts",
			"begins", "event date", "session date", "event start", "from"),
	END_DATE_TIME(Category.EDUCATION, "End date/time", "endDateTime of a live activity", "2026-11-14 17:00", false,
			RecordBuilder::endDateTime, "end", "end date", "end date time", "end time", "ends", "until", "event end",
			"to"),
	AUDIENCE_CATEGORY(Category.AUDIENCE, "Audience category", "audienceCategory: professional, patient, caregiver, general",
			"professional", true, RecordBuilder::audienceCategory, "audience category", "audience", "target audience",
			"audience type", "audiences", "for"),
	PROFESSION(Category.AUDIENCE, "Profession(s)", "profession: physician, registered nurse, pharmacist, …",
			"physician; registered nurse", true, RecordBuilder::professions, "profession", "professions",
			"target profession", "target professions", "disciplines", "discipline", "audience profession"),
	SPECIALTY(Category.AUDIENCE, "Specialty(ies)", "specialty: cardiology, emergency medicine, …",
			"emergency medicine", true, RecordBuilder::specialties, "specialty", "specialties", "speciality",
			"specialities", "clinical specialty", "medical specialty", "field", "service line"),
	READING_LEVEL(Category.AUDIENCE, "Reading level", "readingLevel: grade 1 … grade 12, above grade 12", "grade 8",
			false, RecordBuilder::readingLevel, "reading level", "grade level", "readability", "literacy level"),
	PARTICIPATION_MODALITY(Category.EDUCATION, "Participation modality",
			"participationModality: conference/workshop, technology based (online), on the job, print", "online", true,
			RecordBuilder::participationModality, "participation modality", "modality", "delivery format",
			"delivery mode", "mode", "delivery method", "format of delivery"),
	ACTIVITY_DELIVERY(Category.EDUCATION, "Activity delivery", "activityDelivery: live, not live (enduring)",
			"enduring", true, RecordBuilder::activityDelivery, "activity delivery", "delivery", "live or enduring",
			"live enduring", "live"),
	ACTIVITY_FORMAT(Category.EDUCATION, "Activity format", "activityFormat (free text, e.g. course, journal CME)",
			"online course", true, RecordBuilder::activityFormat, "activity format", "format of activity",
			"activity kind"),
	SPONSORSHIP(Category.EDUCATION, "Activity sponsorship", "activitySponsorship: direct, joint", "direct", false,
			RecordBuilder::sponsorship, "activity sponsorship", "sponsorship", "joint providership",
			"sponsorship type"),
	COMMERCIAL_SUPPORT(Category.EDUCATION, "Commercial support",
			"commercialSupport: yes/no, or the acknowledgement text", "no", false, RecordBuilder::commercialSupport,
			"commercial support", "industry support", "commercial funding", "grant support", "funding"),
	FINANCIAL_RELATIONSHIP(Category.EDUCATION, "Financial relationships",
			"relevantFinancialRelationship: yes/no, or the disclosure", "none", false,
			RecordBuilder::financialRelationship, "relevant financial relationship", "financial relationship",
			"financial relationships", "disclosure", "disclosures", "conflict of interest", "coi"),
	OFF_LABEL_USE(Category.EDUCATION, "Off-label use", "offLabelUse: yes/no, or the description", "no", false,
			RecordBuilder::offLabelUse, "off label use", "off label", "unapproved use", "off label discussion"),
	CONTACT(Category.EDUCATION, "Contact", "contact (name, e-mail, phone)", "cme@example.org", false,
			RecordBuilder::contact, "contact", "contact email", "email", "e mail", "contact info",
			"contact information", "questions", "contact person"),
	VENUE(Category.LOCATION, "Venue / organization", "activityLocation/organization", "Lourdes Simulation Center",
			true, RecordBuilder::venue, "venue", "facility", "site", "hospital", "location name", "venue name",
			"conference center", "campus"),
	BUILDING(Category.LOCATION, "Building", "activityLocation/building", "Wharton Hall", false, RecordBuilder::building,
			"building"),
	ROOM(Category.LOCATION, "Room", "activityLocation/room", "Sim Lab 2", false, RecordBuilder::room, "room",
			"room number", "suite"),
	CITY(Category.LOCATION, "City", "activityLocation/city", "Lafayette", false, RecordBuilder::city, "city", "town",
			"municipality"),
	STATE(Category.LOCATION, "State / province", "activityLocation/stateOrProvince", "LA", false,
			RecordBuilder::state, "state", "province", "state province", "state or province", "region"),
	POSTAL_CODE(Category.LOCATION, "Postal code", "activityLocation/postalCode", "70503", false,
			RecordBuilder::postalCode, "postal code", "zip", "zip code", "postcode"),
	COUNTRY(Category.LOCATION, "Country", "activityLocation/country (name or ISO code)", "United States", false,
			RecordBuilder::country, "country", "nation", "country code"),

	// Healthcare asset
	MEDICAL_IMAGE_TYPE(Category.ASSET, "Medical image type",
			"medicalImageType: radiograph (x-ray), CT, MRI, ultrasound, photograph, …", "x-ray", false,
			RecordBuilder::medicalImageType, "medical image type", "image type", "imaging modality", "study type",
			"image modality", "imaging"),
	ORIENTATION(Category.ASSET, "Orientation", "orientation: axial, coronal (AP/PA), sagittal (lateral), …", "PA",
			false, RecordBuilder::orientation, "orientation", "plane", "view", "projection", "image orientation"),
	SPECIMEN_TYPE(Category.ASSET, "Specimen type", "specimenType: cell, tissue, organ, organ system, organelle",
			"tissue", false, RecordBuilder::specimenType, "specimen type", "specimen", "sample type"),
	MAGNIFICATION(Category.ASSET, "Magnification", "magnification (whole number, e.g. 40x)", "40x", false,
			RecordBuilder::magnification, "magnification", "zoom", "objective power"),
	ANNOTATED(Category.ASSET, "Annotated", "annotated: yes/no", "yes", false, RecordBuilder::annotated, "annotated",
			"annotations", "has annotations", "labeled", "labelled"),
	CLINICAL_HISTORY(Category.ASSET, "Clinical history", "clinicalHistory", "54-year-old with fever and cough",
			false, RecordBuilder::clinicalHistory, "clinical history", "history", "case history", "patient history",
			"clinical context", "clinical information"),
	IMAGE_WIDTH(Category.ASSET, "Image width", "fileWidth in pixels", "2048", false, RecordBuilder::imageWidth,
			"width", "image width", "file width", "pixel width", "width px"),
	IMAGE_HEIGHT(Category.ASSET, "Image height", "fileHeight in pixels", "2500", false, RecordBuilder::imageHeight,
			"height", "image height", "file height", "pixel height", "height px"),
	IMAGE_DIMENSIONS(Category.ASSET, "Image dimensions", "fileWidth x fileHeight, e.g. 2048x2500", "2048x2500",
			false, RecordBuilder::imageDimensions, "dimensions", "image dimensions", "resolution", "image size",
			"pixel dimensions");

	/** The groups fields are presented in. */
	public enum Category {
		GENERAL("General"), LIFE_CYCLE("Life cycle"), TECHNICAL("Technical"), EDUCATIONAL("Educational"),
		RIGHTS("Rights"), CLASSIFICATION("Classification"), EDUCATION("Healthcare education"),
		CREDITS("Credits"), AUDIENCE("Target audience"), LOCATION("Activity location"), ASSET("Healthcare asset");

		private final String label;

		Category(String label) {
			this.label = label;
		}

		/** The display name. */
		public String label() {
			return label;
		}
	}

	private final Category category;
	private final String label;
	private final String help;
	private final String example;
	private final boolean repeatable;
	private final BiConsumer<RecordBuilder, String> applier;
	private final List<String> aliases;

	ImportField(Category category, String label, String help, String example, boolean repeatable,
			BiConsumer<RecordBuilder, String> applier, String... aliases) {
		this.category = category;
		this.label = label;
		this.help = help;
		this.example = example;
		this.repeatable = repeatable;
		this.applier = applier;
		this.aliases = List.of(aliases);
	}

	/** The group of the field. */
	public Category category() {
		return category;
	}

	/** The display name, e.g. "Learning resource type". */
	public String label() {
		return label;
	}

	/** The display name with the category, e.g. "Credits › Credit type". */
	public String qualifiedLabel() {
		return category.label() + " › " + label;
	}

	/** Where the value goes in HLOM and which values are accepted. */
	public String help() {
		return help;
	}

	/** An example value. */
	public String example() {
		return example;
	}

	/** Whether several columns may be mapped to the field (and a cell may hold several values). */
	public boolean repeatable() {
		return repeatable;
	}

	/** The (normalized) header names the field is recognized by. */
	public List<String> aliases() {
		return aliases;
	}

	/** Applies a (non-blank) cell value to a record. */
	public void apply(RecordBuilder builder, String value) {
		applier.accept(builder, value);
	}

	/** The fields of a category, in declaration order. */
	public static List<ImportField> of(Category category) {
		List<ImportField> fields = new ArrayList<>();
		for (ImportField field : values()) {
			if (field.category == category) {
				fields.add(field);
			}
		}
		return fields;
	}

	@Override
	public String toString() {
		return label;
	}
}
