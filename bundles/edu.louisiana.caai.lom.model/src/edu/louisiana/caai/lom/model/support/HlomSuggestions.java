/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.util.List;
import java.util.Map;

/**
 * Recommended values for free-text HLOM elements, taken from the Healthcare LOM specification (ANSI/MEDBIQ
 * LO.10.1-2008, v1.1). These are suggestions only: the schemas accept any text for these elements.
 */
public final class HlomSuggestions {

	private HlomSuggestions() {
	}

	/** Recommended accrediting bodies (credits/accreditingBody). */
	public static final List<String> ACCREDITING_BODIES = List.of("ACCME", "AACN", "ACPE", "ANCC", "AANA", "AANP",
			"AAPA", "CCME", "ASHA", "AAA", "FCLB PACE", "AGD PACE", "CDR", "CECBEMS", "AOTA", "COPE", "APA",
			"ARRT RCEEM", "AARC", "ACSM", "BOC", "EBAC", "RCPath", "IACET");

	/** Recommended credit categories (credits/activityCertification). */
	public static final List<String> ACTIVITY_CERTIFICATIONS = List.of("AAFP Prescribed", "AAFP Elective", "AAP",
			"AAP Prep", "ACEP", "ACOG", "AMA PRA Category 1", "AMA PRA Category 2", "AOA", "APA Category 1",
			"CECBEMS First Responder", "CECBEMS Basic", "CECBEMS Advanced", "CECBEMS Operational", "CECBEMS Educator",
			"CECBEMS Administrator", "CPHQ", "EBAC CE Credit Hours", "NCHEC Category 1", "NCHEC Category 2",
			"Synergy CERPS A", "Synergy CERPS B", "Synergy CERPS C", "RCP");

	/** Recommended reading levels (targetAudience/readingLevel). */
	public static final List<String> READING_LEVELS = List.of("grade 1", "grade 2", "grade 3", "grade 4", "grade 5",
			"grade 6", "grade 7", "grade 8", "grade 9", "grade 10", "grade 11", "grade 12", "above grade 12");

	/** Recommended health professions (targetAudience/profession); both levels of the hierarchy are valid. */
	public static final Map<String, List<String>> PROFESSIONS = orderedMap(
			"allied health professional",
			List.of("chiropractor", "dietician", "emergency medical services professional", "laboratory professional",
					"medical assistant", "medical examiner / coroner", "medical imaging professional",
					"mental health counselor", "occupational therapist", "optician", "optometrist",
					"physical therapist", "physician assistant", "podiatrist", "psychologist",
					"rehabilitation professional", "respiratory therapy professional", "social worker",
					"speech, language, audiology professional", "substance abuse counselor"),
			"dental professional", List.of("dentist"),
			"medical professional", List.of("physician"),
			"nursing professional", List.of("advanced practice nurse", "licensed practical nurse", "registered nurse"),
			"pharmacy professional", List.of("pharmacist"),
			"public health professional",
			List.of("environmental health professional", "epidemiologist", "health educator",
					"occupational health and safety professional"),
			"veterinary professional", List.of("veterinarian"));

	/** Recommended health specialties (targetAudience/specialty). */
	public static final List<String> SPECIALTIES = List.of("adolescent medicine", "allergy/immunology",
			"anesthesiology", "cardiology", "colon and rectal surgery", "critical care medicine", "dermatology",
			"developmental-behavioral", "emergency medicine", "endocrinology", "family practice", "gastroenterology",
			"geriatric medicine", "hematology and oncology", "infectious disease", "internal medicine",
			"medical genetics", "neonatal-perinatal medicine", "nephrology", "neurological surgery", "neurology",
			"nuclear medicine", "obstetrics and gynecology", "ophthalmology", "orthopaedic surgery", "otolaryngology",
			"pathology-anatomic and clinical", "pediatrics", "physical medicine and rehabilitation", "plastic surgery",
			"preventive medicine", "psychiatry", "pulmonology", "radiation oncology", "radiology-diagnostic",
			"rheumatology", "sports medicine", "surgery-general", "thoracic surgery", "urology");

	/** Credit related acronyms (Appendix 1), used as help text. */
	public static final Map<String, String> CREDIT_ACRONYMS = orderedMap("AAA", "American Academy of Audiology",
			"AACN", "American Association of Critical-Care Nurses", "AAFP", "American Academy of Family Physicians",
			"AANA", "American Association of Nurse Anesthetists", "AANP", "American Academy of Nurse Practitioners",
			"AAP", "American Academy of Pediatrics", "AAPA", "American Academy of Physician Assistants", "AARC",
			"American Association for Respiratory Care", "ACCME", "Accreditation Council of Continuing Medical Education",
			"ACEP", "American College of Emergency Physicians", "ACOG", "American College of Obstetrics and Gynecology",
			"ACPE", "Accreditation Council for Pharmacy Education", "ACSM", "American College of Sports Medicine",
			"ADA CERP", "American Dental Association Continuing Education Recognition Program", "AGD PACE",
			"Academy of General Dentistry Program Approval for Continuing Education", "AMA PRA",
			"American Medical Association Physician Recognition Award", "ANCC", "American Nurses Credentialing Center",
			"AOA", "American Osteopathic Association", "AOTA", "American Occupational Therapy Association", "APA",
			"American Psychological Association", "ARRT RCEEM",
			"American Registry of Radiologic Technologists Recognized Continuing Education Evaluation Mechanism", "ASHA",
			"American Speech-Language-Hearing Association", "BOC", "Board of Certification (for athletic trainers)",
			"CCME", "Council on Continuing Medical Education (for osteopathic professionals)", "CDR",
			"Commission on Dietetic Registration", "CECBEMS",
			"Continuing Education Coordinating Board for Emergency Medical Services", "CE", "Continuing Education",
			"CECH", "Continuing Education Contact Hour", "CEH", "Continuing Education Hours", "CERP",
			"Continuing Education Recognition Point (for critical-care nurses)", "CEU", "Continuing Education Units",
			"CHES", "Certified Health Education Specialists", "CME", "Continuing Medical Education", "CNE",
			"Continuing Nursing Education", "COPE", "Council on Optometric Practitioner Education", "CPE",
			"Continuing Pharmacy Education", "CPHQ", "Certified Professional in Healthcare Quality", "EBAC",
			"European Board of Accreditation in Cardiology", "FCLB PACE",
			"Federation of Chiropractic Licensing Boards Providers of Approved Continuing Education", "IACET",
			"International Association for Continuing Education and Training", "NCHEC",
			"National Commission for Health Education Credentialing", "RCPath", "Royal College of Pathologists");

	/** Terminologies for keyword/@hx:source. */
	public static final List<String> KEYWORD_SOURCES = List.of("MeSH", "ICD-10-CM", "SNOMED", "UMLS", "ICD-10",
			"LOINC");

	/** Metadata schema identifiers (metaMetadata/metadataSchema). */
	public static final List<String> METADATA_SCHEMAS = List.of(HlomDocuments.LOM_METADATA_SCHEMA, "SCORM_CAM_v1.3",
			HlomDocuments.HEALTHCARE_METADATA_SCHEMA);

	/** Common language tags (ISO 639 language, optional ISO 3166 region). */
	public static final List<String> LANGUAGES = List.of("en-US", "en", "en-GB", "es", "es-US", "fr", "fr-CA", "de",
			"it", "pt", "pt-BR", "nl", "zh", "zh-CN", "zh-TW", "ja", "ko", "ar", "ru", "hi", "vi", "tl", "pl", "x-none");

	/** Common MIME types (technical/format). */
	public static final List<String> MIME_TYPES = List.of("text/html", "application/pdf", "application/zip",
			"application/xml", "text/plain", "image/jpeg", "image/png", "image/gif", "image/svg+xml", "image/tiff",
			"application/dicom", "video/mp4", "video/webm", "audio/mpeg", "audio/wav",
			"application/vnd.openxmlformats-officedocument.presentationml.presentation",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "non-digital");

	/** Example classification taxonomies (classification/taxonPath/source). */
	public static final List<String> TAXONOMIES = List.of("ACGME Core Competencies", "MeSH", "ICD-10-CM", "SNOMED CT",
			"AAMC Curriculum Inventory", "Bloom's Taxonomy (revised)");

	/** All recommended professions, flattened (top level terms followed by their sub-terms). */
	public static List<String> professionTerms() {
		return PROFESSIONS.entrySet().stream()
				.flatMap(e -> java.util.stream.Stream.concat(java.util.stream.Stream.of(e.getKey()),
						e.getValue().stream().map(v -> v)))
				.toList();
	}

	@SuppressWarnings("unchecked")
	private static <V> Map<String, V> orderedMap(Object... keysAndValues) {
		Map<String, V> map = new java.util.LinkedHashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			map.put((String) keysAndValues[i], (V) keysAndValues[i + 1]);
		}
		return java.util.Collections.unmodifiableMap(map);
	}
}
