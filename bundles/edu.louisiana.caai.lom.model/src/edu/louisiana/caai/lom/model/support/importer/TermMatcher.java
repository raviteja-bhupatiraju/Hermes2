/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Matches free spreadsheet values against a closed list of terms: case and punctuation insensitive, with
 * friendly synonyms ("slides", "CT", "self-paced", "nurses"), plurals and unambiguous abbreviations.
 */
public final class TermMatcher {

	/**
	 * A matched term.
	 *
	 * @param term  the canonical term
	 * @param exact whether the input was the term itself (ignoring case, spacing and punctuation)
	 */
	public record Match(String term, boolean exact) {
	}

	private static final Map<String, Map<String, String>> SYNONYMS = new HashMap<>();

	static {
		synonyms("LearningResourceType", "slides", "slide", "slideshow", "slide", "slide deck", "slide", "presentation",
				"slide", "powerpoint", "slide", "ppt", "slide", "pptx", "slide", "deck", "slide", "quiz",
				"self assessment", "self test", "self assessment", "self check", "self assessment", "posttest",
				"exam", "post test", "exam", "pretest", "self assessment", "test", "exam", "examination", "exam",
				"assessment", "self assessment", "survey", "questionnaire", "evaluation", "questionnaire", "text",
				"narrative text", "article", "narrative text", "reading", "narrative text", "handout", "narrative text",
				"brochure", "narrative text", "leaflet", "narrative text", "booklet", "narrative text", "pdf",
				"narrative text", "document", "narrative text", "photo", "image", "picture", "image", "photograph",
				"image", "illustration", "figure", "chart", "graph", "infographic", "diagram", "flowchart", "diagram",
				"algorithm", "diagram", "webinar", "video", "recording", "video", "movie", "video", "screencast", "video",
				"film", "video", "podcast", "audio", "sound", "audio", "case", "case study", "cases", "case study",
				"case based", "case study", "case discussion", "case study", "vignette", "case study", "sim",
				"simulation", "simulator", "simulation", "skills lab", "simulation", "hands on", "exercise",
				"practice", "exercise", "drill", "exercise", "workshop", "exercise", "talk", "lecture", "grand rounds",
				"lecture", "keynote", "lecture", "seminar", "lecture", "course", "tutorial", "module", "tutorial",
				"online module", "tutorial", "e learning", "tutorial", "elearning", "tutorial", "guide", "reference",
				"guideline", "reference", "glossary", "reference", "atlas", "reference", "forum", "collaborative forum",
				"discussion", "collaborative forum", "discussion board", "collaborative forum", "serious game", "game",
				"gamified", "game", "vp", "virtual patient", "animated", "animation", "3d animation", "animation");
		synonyms("Context", "cme", "continuing professional development", "ce", "continuing professional development",
				"cpd", "continuing professional development", "cne", "continuing professional development",
				"continuing education", "continuing professional development", "continuing medical education",
				"continuing professional development", "professional development", "continuing professional development",
				"gme", "graduate professional education", "graduate medical education",
				"graduate professional education", "undergraduate medical education", "undergraduate education", "residency", "graduate professional education", "fellowship",
				"graduate professional education", "graduate", "graduate professional education", "postgraduate",
				"graduate professional education", "ume", "undergraduate education", "medical school",
				"undergraduate education", "nursing school", "undergraduate education", "undergraduate",
				"undergraduate education", "university", "higher education", "college", "higher education", "patient",
				"patient education", "patients", "patient education", "caregiver", "caregiver education", "caregivers",
				"caregiver education", "family", "caregiver education", "k 12", "school", "high school",
				"secondary education", "primary school", "primary education", "elementary", "primary education",
				"vocational", "vocational training", "trade school", "vocational training", "staff training",
				"training", "in service", "training", "inservice", "training", "orientation", "training", "on the job",
				"training");
		synonyms("IntendedEndUserRole", "student", "learner", "students", "learner", "trainee", "learner", "resident",
				"learner", "residents", "learner", "participant", "learner", "participants", "learner", "attendee",
				"learner", "clinician", "learner", "clinicians", "learner", "faculty", "teacher", "instructor",
				"teacher", "educator", "teacher", "preceptor", "teacher", "trainer", "teacher", "facilitator",
				"teacher", "administrator", "manager", "admin", "manager", "course director", "manager", "writer",
				"author", "content author", "author");
		synonyms("Status", "published", "final", "active", "final", "released", "final", "live", "final", "approved",
				"final", "complete", "final", "completed", "final", "in progress", "draft", "wip", "draft",
				"work in progress", "draft", "pending", "draft", "under review", "draft", "new", "draft", "updated",
				"revised", "revision", "revised", "retired", "unavailable", "withdrawn", "unavailable", "archived",
				"unavailable", "inactive", "unavailable", "expired", "unavailable");
		synonyms("Difficulty", "beginner", "easy", "introductory", "easy", "basic", "easy", "novice", "easy",
				"foundational", "easy", "simple", "easy", "intermediate", "medium", "moderate", "medium", "average",
				"medium", "advanced", "difficult", "hard", "difficult", "challenging", "difficult", "expert",
				"very difficult", "very hard", "very difficult", "trivial", "very easy");
		synonyms("InteractivityType", "interactive", "active", "hands on", "active", "passive", "expositive",
				"didactic", "expositive", "read only", "expositive", "blended", "mixed", "hybrid", "mixed");
		synonyms("YesNo", "y", "yes", "true", "yes", "t", "yes", "1", "yes", "x", "yes", "on", "yes", "available",
				"yes", "required", "yes", "si", "yes", "ja", "yes", "oui", "yes", "accredited", "yes", "paid", "yes",
				"fee", "yes", "n", "no", "false", "no", "f", "no", "0", "no", "off", "no", "none", "no",
				"not available", "no", "free", "no", "no charge", "no", "free of charge", "no", "nein", "no", "non",
				"no", "not accredited", "no");
		synonyms("MedicalImageType", "ct", "computed axial tomography scan", "cat", "computed axial tomography scan",
				"cat scan", "computed axial tomography scan", "ct scan", "computed axial tomography scan",
				"computed tomography", "computed axial tomography scan", "mri", "magnetic resonance image", "mr",
				"magnetic resonance image", "magnetic resonance imaging", "magnetic resonance image", "ecg",
				"electrocardiogram", "ekg", "electrocardiogram", "12 lead", "electrocardiogram", "xray", "radiograph",
				"x ray", "radiograph", "cxr", "radiograph", "plain film", "radiograph", "radiography", "radiograph",
				"us", "ultrasound", "sonogram", "ultrasound", "sonography", "ultrasound", "echo", "ultrasound",
				"echocardiogram", "ultrasound", "pocus", "ultrasound", "doppler", "ultrasound", "photo", "photograph",
				"clinical photo", "photograph", "dermatology photo", "photograph", "endoscopy", "endoscopic image",
				"colonoscopy", "endoscopic image", "laparoscopy", "endoscopic image", "histology", "micrograph",
				"microscopy", "micrograph", "histopathology", "micrograph", "slide", "micrograph", "pathology slide",
				"micrograph", "pet", "nuclear medicine scan", "pet ct", "nuclear medicine scan", "spect",
				"nuclear medicine scan", "scintigraphy", "nuclear medicine scan", "bone scan", "nuclear medicine scan",
				"mammography", "mammogram", "angiography", "angiogram", "cath", "angiogram");
		synonyms("Orientation", "ap", "coronal", "pa", "coronal", "frontal", "coronal", "lateral", "sagittal",
				"lat", "sagittal", "side", "sagittal", "cross section", "transverse", "cross sectional", "transverse",
				"long axis", "longitudinal", "short axis", "transverse");
		synonyms("SpecimenType", "biopsy", "tissue", "histology", "tissue", "cytology", "cell", "cells", "cell",
				"smear", "cell", "blood smear", "cell", "whole organ", "organ", "gross", "organ", "system",
				"organ system", "mitochondria", "organelle");
		synonyms("CreditType", "continuing medical education", "CME", "ama pra", "CME", "physician", "CME",
				"continuing education", "CE", "nursing", "CNE", "continuing nursing education", "CNE", "ancc", "CNE",
				"contact hours", "CNE", "pharmacy", "CPE", "acpe", "CPE", "continuing pharmacy education", "CPE",
				"health education", "CHES", "continuing professional development", "CPD", "moc", "CPD");
		synonyms("CreditUnit", "credits", "Credit", "credit", "Credit", "cr", "Credit", "ama pra category 1 credit",
				"Credit", "category 1 credit", "Credit", "hours", "Hour", "hrs", "Hour", "hr", "Hour", "h", "Hour",
				"contact hours", "Contact Hour", "contact hr", "Contact Hour", "contact", "Contact Hour", "credit hours",
				"Credit Hour", "credit hr", "Credit Hour", "points", "Point", "pts", "Point", "units", "Unit",
				"ceus", "CEU", "continuing education units", "CEU", "cognates", "Cognate", "cechs", "CECH", "cehs",
				"CEH");
		synonyms("Pacing", "self paced", "learner paced", "self directed", "learner paced", "on demand",
				"learner paced", "enduring", "learner paced", "asynchronous", "learner paced", "learner", "learner paced",
				"scheduled", "provider paced", "instructor led", "provider paced", "instructor paced",
				"provider paced", "synchronous", "provider paced", "live", "provider paced", "cohort", "provider paced");
		synonyms("ParticipationModality", "conference", "conference/workshop", "workshop", "conference/workshop",
				"in person", "conference/workshop", "face to face", "conference/workshop", "f2f",
				"conference/workshop", "seminar", "conference/workshop", "symposium", "conference/workshop",
				"meeting", "conference/workshop", "classroom", "conference/workshop", "live event",
				"conference/workshop", "online", "technology based", "web", "technology based", "web based",
				"technology based", "internet", "technology based", "e learning", "technology based", "elearning",
				"technology based", "virtual", "technology based", "webinar", "technology based", "computer",
				"technology based", "mobile", "technology based", "app", "technology based", "lms",
				"technology based", "on the job", "on the job", "ojt", "on the job", "clinical", "on the job",
				"workplace", "on the job", "bedside", "on the job", "journal", "print", "book", "print",
				"printed", "print", "paper", "print", "handout", "print", "monograph", "print");
		synonyms("ActivityDelivery", "enduring", "not live", "on demand", "not live", "recorded", "not live",
				"self paced", "not live", "asynchronous", "not live", "online module", "not live", "home study",
				"not live", "no", "not live", "in person", "live", "webinar", "live", "live webinar", "live",
				"synchronous", "live", "real time", "live", "virtual live", "live", "yes", "live");
		synonyms("AudienceCategory", "professionals", "professional", "clinician", "professional", "clinicians",
				"professional", "provider", "professional", "providers", "professional", "hcp", "professional",
				"health professional", "professional", "healthcare professional", "professional", "staff",
				"professional", "public", "general", "general public", "general", "lay", "general", "lay public",
				"general", "consumer", "general", "consumers", "general", "community", "general", "everyone",
				"general", "patients", "patient", "family", "caregiver", "families", "caregiver", "carer",
				"caregiver", "carers", "caregiver", "parents", "caregiver", "caregivers", "caregiver");
		synonyms("Sponsorship", "directly provided", "direct", "direct providership", "direct", "jointly provided",
				"joint", "joint providership", "joint", "jointly", "joint", "co sponsored", "joint", "cosponsored",
				"joint", "co provided", "joint");
		synonyms("Profession", "md", "physician", "do", "physician", "doctor", "physician", "doctors", "physician",
				"physicians", "physician", "mds", "physician", "hospitalist", "physician", "surgeon", "physician",
				"rn", "registered nurse", "rns", "registered nurse", "nurse", "registered nurse", "nurses",
				"registered nurse", "bsn", "registered nurse", "np", "advanced practice nurse", "nps",
				"advanced practice nurse", "nurse practitioner", "advanced practice nurse", "nurse practitioners",
				"advanced practice nurse", "aprn", "advanced practice nurse", "crna", "advanced practice nurse",
				"cns", "advanced practice nurse", "nurse midwife", "advanced practice nurse", "lpn",
				"licensed practical nurse", "lvn", "licensed practical nurse", "pa", "physician assistant", "pas",
				"physician assistant", "pa c", "physician assistant", "physician assistants", "physician assistant",
				"pharmacists", "pharmacist", "pharmd", "pharmacist", "rph", "pharmacist", "pharmacy technician",
				"pharmacy professional", "dentists", "dentist", "dds", "dentist", "dmd", "dentist", "dental hygienist",
				"dental professional", "dietitian", "dietician", "dietitians", "dietician", "rd", "dietician",
				"nutritionist", "dietician", "ems", "emergency medical services professional", "emt",
				"emergency medical services professional", "paramedic", "emergency medical services professional",
				"paramedics", "emergency medical services professional", "first responder",
				"emergency medical services professional", "pt", "physical therapist", "physiotherapist",
				"physical therapist", "physical therapists", "physical therapist", "ot", "occupational therapist",
				"occupational therapists", "occupational therapist", "rt", "respiratory therapy professional",
				"respiratory therapist", "respiratory therapy professional", "respiratory therapists",
				"respiratory therapy professional", "slp", "speech, language, audiology professional",
				"speech therapist", "speech, language, audiology professional", "speech language pathologist",
				"speech, language, audiology professional", "audiologist", "speech, language, audiology professional",
				"social workers", "social worker", "msw", "social worker", "lcsw", "social worker", "psychologists",
				"psychologist", "counselor", "mental health counselor", "therapist", "mental health counselor",
				"lab", "laboratory professional", "medical technologist", "laboratory professional", "radiographer",
				"medical imaging professional", "radiologic technologist", "medical imaging professional",
				"sonographer", "medical imaging professional", "rad tech", "medical imaging professional", "ma",
				"medical assistant", "chiropractors", "chiropractor", "optometrists", "optometrist", "podiatrists",
				"podiatrist", "vet", "veterinarian", "vets", "veterinarian", "dvm", "veterinarian", "veterinarians",
				"veterinarian", "epidemiologists", "epidemiologist", "health educators", "health educator",
				"public health", "public health professional", "allied health", "allied health professional",
				"nursing", "nursing professional", "pharmacy", "pharmacy professional", "dental",
				"dental professional", "medical", "medical professional");
		synonyms("Specialty", "family medicine", "family practice", "fm", "family practice", "primary care",
				"family practice", "general practice", "family practice", "gp", "family practice", "im",
				"internal medicine", "general internal medicine", "internal medicine", "hospital medicine",
				"internal medicine", "em", "emergency medicine", "emergency", "emergency medicine", "er",
				"emergency medicine", "ed", "emergency medicine", "icu", "critical care medicine", "critical care",
				"critical care medicine", "intensive care", "critical care medicine", "peds", "pediatrics",
				"paediatrics", "pediatrics", "pediatric", "pediatrics", "nicu", "neonatal-perinatal medicine",
				"neonatology", "neonatal-perinatal medicine", "ob gyn", "obstetrics and gynecology", "obgyn",
				"obstetrics and gynecology", "obstetrics", "obstetrics and gynecology", "gynecology",
				"obstetrics and gynecology", "general surgery", "surgery-general", "surgery", "surgery-general",
				"radiology", "radiology-diagnostic", "diagnostic radiology", "radiology-diagnostic", "imaging",
				"radiology-diagnostic", "pathology", "pathology-anatomic and clinical", "ortho", "orthopaedic surgery",
				"orthopedics", "orthopaedic surgery", "orthopedic surgery", "orthopaedic surgery", "oncology",
				"hematology and oncology", "hematology", "hematology and oncology", "heme onc",
				"hematology and oncology", "ent", "otolaryngology", "pulmonary", "pulmonology",
				"pulmonary medicine", "pulmonology", "respiratory medicine", "pulmonology", "id", "infectious disease",
				"infectious diseases", "infectious disease", "geriatrics", "geriatric medicine", "gerontology",
				"geriatric medicine", "pm r", "physical medicine and rehabilitation", "pmr",
				"physical medicine and rehabilitation", "rehabilitation", "physical medicine and rehabilitation",
				"neurosurgery", "neurological surgery", "gi", "gastroenterology", "hepatology", "gastroenterology",
				"cardiovascular", "cardiology", "cardiovascular disease", "cardiology", "heart", "cardiology",
				"diabetes", "endocrinology", "endocrine", "endocrinology", "kidney", "nephrology", "renal",
				"nephrology", "derm", "dermatology", "eye", "ophthalmology", "ophthalmic", "ophthalmology", "anesthesia",
				"anesthesiology", "anaesthesia", "anesthesiology", "anaesthesiology", "anesthesiology", "psych",
				"psychiatry", "mental health", "psychiatry", "behavioral health", "psychiatry", "genetics",
				"medical genetics", "allergy", "allergy/immunology", "immunology", "allergy/immunology", "rheum",
				"rheumatology", "sports", "sports medicine", "urologic", "urology", "nuclear", "nuclear medicine",
				"radiation therapy", "radiation oncology", "plastics", "plastic surgery", "thoracic",
				"thoracic surgery", "cardiothoracic surgery", "thoracic surgery", "colorectal surgery",
				"colon and rectal surgery", "preventive", "preventive medicine", "public health",
				"preventive medicine", "adolescent", "adolescent medicine", "developmental", "developmental-behavioral",
				"neuro", "neurology");
		synonyms("Format", "pdf", "application/pdf", "html", "text/html", "web", "text/html", "web page",
				"text/html", "website", "text/html", "url", "text/html", "online", "text/html", "jpg", "image/jpeg",
				"jpeg", "image/jpeg", "png", "image/png", "gif", "image/gif", "svg", "image/svg+xml", "tif",
				"image/tiff", "tiff", "image/tiff", "dicom", "application/dicom", "dcm", "application/dicom", "mp4",
				"video/mp4", "video", "video/mp4", "webm", "video/webm", "mp3", "audio/mpeg", "audio", "audio/mpeg",
				"podcast", "audio/mpeg", "wav", "audio/wav", "zip", "application/zip", "scorm", "application/zip",
				"xml", "application/xml", "txt", "text/plain", "text", "text/plain", "pptx",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation", "powerpoint",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation", "slides",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation", "docx",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "word",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "print", "non-digital",
				"paper", "non-digital", "in person", "non-digital", "live", "non-digital", "non digital",
				"non-digital", "physical", "non-digital");
	}

	private TermMatcher() {
	}

	private static void synonyms(String vocabulary, String... pairs) {
		Map<String, String> map = SYNONYMS.computeIfAbsent(vocabulary, _ -> new HashMap<>());
		for (int i = 0; i + 1 < pairs.length; i += 2) {
			map.put(normalize(pairs[i]), pairs[i + 1]);
		}
	}

	/** The synonyms of a vocabulary (normalized synonym to term), possibly empty. */
	public static Map<String, String> synonyms(String vocabulary) {
		return Collections.unmodifiableMap(SYNONYMS.getOrDefault(vocabulary, Map.of()));
	}

	/** Lower case, {@code &} as "and", runs of punctuation and blanks as one space. */
	public static String normalize(String text) {
		if (text == null) {
			return "";
		}
		return text.toLowerCase(Locale.ROOT).replace("&", " and ").replaceAll("[^\\p{L}\\p{N}]+", " ").strip();
	}

	private static String compact(String text) {
		return normalize(text).replace(" ", "");
	}

	/**
	 * Finds the term for a value.
	 *
	 * @param terms      the allowed terms
	 * @param vocabulary the synonym table to use (see {@link #synonyms(String)}), or {@code null}
	 * @param value      the spreadsheet value
	 * @return the match, or {@code null} if the value doesn't (unambiguously) match a term
	 */
	public static Match match(Collection<String> terms, String vocabulary, String value) {
		String normalized = normalize(value);
		if (normalized.isEmpty()) {
			return null;
		}
		Map<String, String> synonyms = vocabulary == null ? Map.of() : SYNONYMS.getOrDefault(vocabulary, Map.of());
		Match match = direct(terms, synonyms, normalized);
		if (match != null) {
			return match;
		}
		for (String singular : singulars(normalized)) {
			match = direct(terms, synonyms, singular);
			if (match != null) {
				return new Match(match.term(), false);
			}
		}
		if (normalized.length() >= 3) {
			String unique = unique(terms, term -> normalize(term).startsWith(normalized));
			if (unique != null) {
				return new Match(unique, false);
			}
		}
		if (normalized.length() >= 4) {
			String unique = unique(terms, term -> {
				String candidate = normalize(term);
				return candidate.length() >= 4 && (candidate.contains(normalized) || containsWord(normalized, candidate));
			});
			if (unique != null) {
				return new Match(unique, false);
			}
			// Synonyms within longer values: "in-person workshop", "live webinar series".
			String target = null;
			for (Map.Entry<String, String> entry : synonyms.entrySet()) {
				if (entry.getKey().length() >= 4 && containsWord(normalized, entry.getKey())) {
					if (target != null && !target.equalsIgnoreCase(entry.getValue())) {
						return null;
					}
					target = entry.getValue();
				}
			}
			if (target != null) {
				for (String term : terms) {
					if (term.equalsIgnoreCase(target)) {
						return new Match(term, false);
					}
				}
			}
		}
		return null;
	}

	private static Match direct(Collection<String> terms, Map<String, String> synonyms, String normalized) {
		String compact = normalized.replace(" ", "");
		for (String term : terms) {
			if (normalize(term).equals(normalized) || compact(term).equals(compact)) {
				return new Match(term, true);
			}
		}
		String synonym = synonyms.get(normalized);
		if (synonym == null) {
			for (Map.Entry<String, String> entry : synonyms.entrySet()) {
				if (entry.getKey().replace(" ", "").equals(compact)) {
					synonym = entry.getValue();
					break;
				}
			}
		}
		if (synonym != null) {
			for (String term : terms) {
				if (term.equalsIgnoreCase(synonym)) {
					return new Match(term, false);
				}
			}
		}
		return null;
	}

	private static List<String> singulars(String normalized) {
		List<String> result = new ArrayList<>();
		if (normalized.endsWith("ies") && normalized.length() > 4) {
			result.add(normalized.substring(0, normalized.length() - 3) + "y");
		}
		if (normalized.endsWith("es") && normalized.length() > 3) {
			result.add(normalized.substring(0, normalized.length() - 2));
		}
		if (normalized.endsWith("s") && normalized.length() > 2) {
			result.add(normalized.substring(0, normalized.length() - 1));
		}
		return result;
	}

	private static boolean containsWord(String text, String word) {
		return (" " + text + " ").contains(" " + word + " ");
	}

	private static String unique(Collection<String> terms, java.util.function.Predicate<String> test) {
		String found = null;
		for (String term : terms) {
			if (test.test(term)) {
				if (found != null && !found.equals(term)) {
					return null;
				}
				found = term;
			}
		}
		return found;
	}
}
