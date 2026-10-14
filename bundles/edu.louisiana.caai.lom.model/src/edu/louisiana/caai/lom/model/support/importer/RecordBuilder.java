/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;

import edu.louisiana.caai.lom.model.address.AddressFactory;
import edu.louisiana.caai.lom.model.address.CountryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType;
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
import edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.Catalog;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.Date;
import edu.louisiana.caai.lom.model.lom.DateTimeValue;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.DurationBase;
import edu.louisiana.caai.lom.model.lom.DurationValue;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.Entity;
import edu.louisiana.caai.lom.model.lom.Entry;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Id;
import edu.louisiana.caai.lom.model.lom.Identifier;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Rights;
import edu.louisiana.caai.lom.model.lom.Size;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;
import edu.louisiana.caai.lom.model.lom.Technical;
import edu.louisiana.caai.lom.model.lom.VCard;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.mesh.MeshClient;

/**
 * Applies spreadsheet values to one Healthcare LOM record created from a template. Values of a field replace
 * the template's defaults for that field; repeated fields (keywords, authors, professions, ...) accept several
 * values separated by {@code ;} or {@code |}. Every interpretation, correction or rejected value is recorded
 * as an {@link ImportMessage}; nothing throws on bad input.
 */
public final class RecordBuilder {

	/** Placeholder texts of the {@link edu.louisiana.caai.lom.model.support.HlomTemplates templates}. */
	static final Set<String> TEMPLATE_PLACEHOLDERS = Set.of(
			"Describe the purpose, scope and learning objectives of the course.", "Describe what the image shows.",
			"Explain in plain language what the patient will learn.",
			"Describe the workshop agenda and learning objectives.", "Author Name", "Publishing Organization",
			"Faculty Name", "Organizing Institution", "Accredited Provider", "Course contact, e-mail address",
			"Workshop contact, e-mail address", "Venue Name", "Room", "Building",
			"State the license or terms of use of the image.");

	/** The ACGME core competencies with their customary letters. */
	static final List<String> ACGME_COMPETENCIES = List.of("Patient Care", "Medical Knowledge",
			"Practice-based Learning and Improvement", "Interpersonal and Communication Skills", "Professionalism",
			"Systems-based Practice");

	private static final Pattern KEYWORD_SOURCE = Pattern
			.compile("(?i)^(?:(.*?)\\s*[\\[(]\\s*)?([A-Za-z][A-Za-z0-9 -]{0,15}?)\\s*:\\s*([A-Za-z0-9.\\-]+)\\s*[\\])]?\\s*(.*)$");
	private static final Pattern NAME_AND_EMAIL = Pattern
			.compile("^(.*?)\\s*[<(\\[]\\s*(?:mailto:)?([^<>()\\[\\]\\s]+@[^<>()\\[\\]\\s]+)\\s*[>)\\]]\\s*$");
	private static final Pattern EMAIL = Pattern.compile("[^\\s@]+@[^\\s@]+\\.[^\\s@]+");
	private static final Pattern DIMENSIONS = Pattern.compile("(\\d+)\\s*[x×X*,]\\s*(\\d+)(\\s*px)?");

	private static final LomFactory LOM = LomFactory.eINSTANCE;
	private static final LomPackage LOM_PACKAGE = LomPackage.eINSTANCE;
	private static final HealthcareFactory HEALTHCARE = HealthcareFactory.eINSTANCE;

	private final DocumentRoot root;
	private final Lom lom;
	private final String language;
	private final List<ImportMessage> messages;
	private final Set<Object> replaced = new HashSet<>();
	private final Set<String> setByCsv = new HashSet<>();
	private String column;
	private ImportField field;

	RecordBuilder(DocumentRoot root, String language, List<ImportMessage> messages) {
		this.root = root;
		this.lom = root.getLom();
		this.language = language;
		this.messages = messages;
	}

	/** The record being built. */
	public DocumentRoot root() {
		return root;
	}

	/** The language of the record's texts. */
	public String language() {
		return language;
	}

	/** Sets the column and field that subsequent messages refer to. */
	void at(String columnName, ImportField importField) {
		this.column = columnName;
		this.field = importField;
	}

	void info(String message) {
		add(Severity.INFO, message);
	}

	void warn(String message) {
		add(Severity.WARNING, message);
	}

	void error(String message) {
		add(Severity.ERROR, message);
	}

	private void add(Severity severity, String message) {
		ImportMessage importMessage = new ImportMessage(severity, column, field, message);
		if (!messages.contains(importMessage)) {
			messages.add(importMessage);
		}
	}

	/** Returns {@code true} the first time it is called for a key (the template's values are then replaced). */
	private boolean first(Object key) {
		return replaced.add(key);
	}

	// ---------------------------------------------------------------------------------------------------------
	// Structure

	General general() {
		if (lom.getGeneral().isEmpty()) {
			insert(lom, "general", LOM.createGeneral());
		}
		return lom.getGeneral().getFirst();
	}

	LifeCycle lifeCycle() {
		if (lom.getLifeCycle().isEmpty()) {
			insert(lom, "lifeCycle", LOM.createLifeCycle());
		}
		return lom.getLifeCycle().getFirst();
	}

	Technical technical() {
		if (lom.getTechnical().isEmpty()) {
			insert(lom, "technical", LOM.createTechnical());
		}
		return lom.getTechnical().getFirst();
	}

	Educational educational() {
		if (lom.getEducational().isEmpty()) {
			insert(lom, "educational", LOM.createEducational());
		}
		return lom.getEducational().getFirst();
	}

	Rights rights() {
		if (lom.getRights().isEmpty()) {
			insert(lom, "rights", LOM.createRights());
		}
		return lom.getRights().getFirst();
	}

	HealthcareMetadataType healthcare() {
		if (lom.getHealthcareMetadata().isEmpty()) {
			insert(lom, "healthcareMetadata", HEALTHCARE.createHealthcareMetadataType());
		}
		return lom.getHealthcareMetadata().getFirst();
	}

	HealthcareEducationType education() {
		HealthcareMetadataType healthcare = healthcare();
		if (healthcare.getHealthcareEducation() == null) {
			healthcare.setHealthcareEducation(HEALTHCARE.createHealthcareEducationType());
		}
		return healthcare.getHealthcareEducation();
	}

	HealthcareAssetType asset() {
		HealthcareMetadataType healthcare = healthcare();
		if (healthcare.getHealthcareAsset() == null) {
			healthcare.setHealthcareAsset(HEALTHCARE.createHealthcareAssetType());
		}
		return healthcare.getHealthcareAsset();
	}

	TargetAudienceType audience() {
		HealthcareEducationType education = education();
		if (education.getTargetAudience() == null) {
			education.setTargetAudience(HEALTHCARE.createTargetAudienceType());
		}
		return education.getTargetAudience();
	}

	ActivityLocationType activityLocation() {
		HealthcareEducationType education = education();
		if (education.getActivityLocation() == null) {
			education.setActivityLocation(HEALTHCARE.createActivityLocationType());
		}
		return education.getActivityLocation();
	}

	/** The credits entry at an index, created (empty) if needed. */
	CreditsType credits(int index) {
		List<CreditsType> credits = education().getCredits();
		while (credits.size() <= index) {
			credits.add(HEALTHCARE.createCreditsType());
		}
		return credits.get(index);
	}

	/**
	 * Adds a child element at its schema position: LOM elements live in a feature map ({@code group}) that
	 * defines the XML order, so the child is inserted after the last sibling of the same or an earlier kind.
	 */
	@SuppressWarnings("unchecked")
	static void insert(EObject parent, String featureName, Object child) {
		EClass eClass = parent.eClass();
		EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
		if (feature == null) {
			throw new IllegalArgumentException(eClass.getName() + " has no feature " + featureName);
		}
		EStructuralFeature groupFeature = ExtendedMetaData.INSTANCE.getGroup(feature);
		if (groupFeature != null) {
			FeatureMap group = (FeatureMap) parent.eGet(groupFeature);
			int rank = eClass.getFeatureID(feature);
			int index = 0;
			for (int i = 0; i < group.size(); i++) {
				if (eClass.getFeatureID(group.getEStructuralFeature(i)) <= rank) {
					index = i + 1;
				}
			}
			group.add(index, feature, child);
		} else if (feature.isMany()) {
			((List<Object>) parent.eGet(feature)).add(child);
		} else {
			parent.eSet(feature, child);
		}
	}

	/** Removes all values of a multi-valued feature (also from its feature map group). */
	@SuppressWarnings("unchecked")
	private static void clear(EObject parent, String featureName) {
		((List<Object>) parent.eGet(parent.eClass().getEStructuralFeature(featureName))).clear();
	}

	@SuppressWarnings("unchecked")
	private static List<EObject> list(EObject parent, String featureName) {
		return (List<EObject>) parent.eGet(parent.eClass().getEStructuralFeature(featureName));
	}

	<T extends LanguageString> T text(T element, String text) {
		element.getString().add(HlomDocuments.langString(text, language));
		return element;
	}

	/** Creates a vocabulary element ({@code source} and {@code value}) for a term of the class's vocabulary. */
	@SuppressWarnings("unchecked")
	static EObject vocab(EClass vocabClass, String term) {
		Vocabularies.Term found = Vocabularies.findTerm(vocabClass, term);
		if (found == null) {
			throw new IllegalArgumentException("Unknown term " + term + " for " + vocabClass.getName());
		}
		EObject vocab = EcoreUtil.create(vocabClass);
		EReference sourceReference = Vocabularies.sourceReference(vocabClass);
		EReference valueReference = Vocabularies.valueReference(vocabClass);
		EObject source = EcoreUtil.create(sourceReference.getEReferenceType());
		source.eSet(Vocabularies.contentAttribute(source.eClass()),
				EcoreUtil.createFromString(Vocabularies.sourceDataType(), found.source()));
		((List<EObject>) vocab.eGet(sourceReference)).add(source);
		EObject value = EcoreUtil.create(valueReference.getEReferenceType());
		value.eSet(Vocabularies.contentAttribute(value.eClass()), found.literal());
		((List<EObject>) vocab.eGet(valueReference)).add(value);
		return vocab;
	}

	// ---------------------------------------------------------------------------------------------------------
	// Value helpers

	/**
	 * Splits a cell into values at {@code ;}, {@code |} and line breaks. A cell without those separators is
	 * split at commas only if {@code commas} is set.
	 */
	static List<String> split(String value, boolean commas) {
		List<String> result = new ArrayList<>();
		String[] parts = value.split("\\s*[;|\\n\\r]+\\s*");
		if (parts.length == 1 && commas && value.contains(",")) {
			parts = value.split("\\s*,\\s*");
		}
		for (String part : parts) {
			String trimmed = part.strip();
			if (!trimmed.isEmpty()) {
				result.add(trimmed);
			}
		}
		return result;
	}

	/** The terms of a vocabulary class as strings. */
	private static List<String> termValues(EClass vocabClass) {
		return Vocabularies.terms(vocabClass).stream().map(Vocabularies.Term::value).toList();
	}

	/** Matches a value against terms, recording interpretations and rejections; {@code null} if rejected. */
	private String match(List<String> terms, String vocabulary, String value, String what) {
		TermMatcher.Match match = TermMatcher.match(terms, vocabulary, value);
		if (match == null) {
			warn("\"" + value + "\" is not a known " + what + " and was ignored. Allowed: " + abbreviate(terms) + ".");
			return null;
		}
		if (!match.exact()) {
			info("\"" + value + "\" was read as \"" + match.term() + "\".");
		}
		return match.term();
	}

	private static String abbreviate(List<String> terms) {
		String joined = String.join(", ", terms);
		return joined.length() > 160 ? joined.substring(0, 157) + "..." : joined;
	}

	private <E extends Enumerator> E matchEnum(List<E> values, String vocabulary, String value, String what) {
		List<String> literals = values.stream().map(Enumerator::getLiteral).toList();
		String term = match(literals, vocabulary, value, what);
		if (term == null) {
			return null;
		}
		for (E each : values) {
			if (each.getLiteral().equals(term)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * Reads yes/no: the usual spellings, {@code none}/{@code n/a}, or a sentence starting with yes or no.
	 * Returns {@code null} if the value is neither.
	 */
	static YesNoType yesNo(String value) {
		TermMatcher.Match match = TermMatcher.match(List.of("yes", "no"), "YesNo", value);
		if (match != null) {
			return YesNoType.get(match.term());
		}
		String normalized = TermMatcher.normalize(value);
		String firstWord = normalized.contains(" ") ? normalized.substring(0, normalized.indexOf(' ')) : normalized;
		return switch (firstWord) {
			case "yes", "y", "true" -> YesNoType.YES;
			case "no", "none", "not", "n", "false", "nil" -> YesNoType.NO;
			default -> normalized.equals("n a") ? YesNoType.NO : null;
		};
	}

	private YesNoType requireYesNo(String value) {
		YesNoType answer = yesNo(value);
		if (answer == null) {
			warn("\"" + value + "\" is neither yes nor no and was ignored.");
		}
		return answer;
	}

	private XMLGregorianCalendar date(String value) {
		ValueParsers.Parsed<LocalDate> parsed = ValueParsers.date(value);
		if (parsed == null) {
			warn("\"" + value + "\" is not a date (use YYYY-MM-DD or MM/DD/YYYY) and was ignored.");
			return null;
		}
		note(parsed.note());
		return XMLTypeFactory.eINSTANCE.createDate(parsed.value().toString());
	}

	private XMLGregorianCalendar dateTime(String value, LocalTime defaultTime) {
		ValueParsers.Parsed<LocalDateTime> parsed = ValueParsers.dateTime(value, defaultTime);
		if (parsed == null) {
			warn("\"" + value + "\" is not a date and time (use YYYY-MM-DD hh:mm) and was ignored.");
			return null;
		}
		note(parsed.note());
		return XMLTypeFactory.eINSTANCE
				.createDateTime(parsed.value().format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss")));
	}

	private void note(String note) {
		if (note != null) {
			info(note.endsWith(".") ? note : note + ".");
		}
	}

	/** Composes a vCard 3.0 for a name, optionally followed by an e-mail address in angle brackets. */
	static String vCard(String text) {
		String name = text.strip();
		String email = null;
		Matcher matcher = NAME_AND_EMAIL.matcher(name);
		if (matcher.matches()) {
			name = matcher.group(1).strip();
			email = matcher.group(2);
		} else if (EMAIL.matcher(name).matches()) {
			email = name;
		}
		StringBuilder builder = new StringBuilder("BEGIN:VCARD\nVERSION:3.0\n");
		builder.append("FN:").append(escapeVCard(name.isEmpty() ? email : name)).append('\n');
		if (email != null) {
			builder.append("EMAIL;TYPE=INTERNET:").append(email).append('\n');
		}
		return builder.append("END:VCARD").toString();
	}

	private static String escapeVCard(String value) {
		return value.replace("\\", "\\\\").replace(",", "\\,").replace(";", "\\;").replace("\n", "\\n");
	}

	// ---------------------------------------------------------------------------------------------------------
	// General

	void title(String value) {
		General general = general();
		if (general.getTitle().isEmpty()) {
			insert(general, "title", text(LOM.createTitle(), value));
		} else {
			general.getTitle().getFirst().getString().add(HlomDocuments.langString(value, language));
			warn("The record already has a title; \"" + value + "\" was added as a second title string.");
		}
	}

	void description(String value) {
		General general = general();
		if (first(ImportField.DESCRIPTION)) {
			clear(general, "description");
		}
		insert(general, "description", text(LOM.createLanguageString(), value));
	}

	void keywords(String value) {
		General general = general();
		for (String keywordText : split(value, true)) {
			Keyword keyword = LOM.createKeyword();
			String label = keywordText;
			Matcher matcher = KEYWORD_SOURCE.matcher(keywordText);
			if (matcher.matches() && !ValueParsers.looksLikeUrl(keywordText)) {
				String source = canonicalSource(matcher.group(2));
				if (source != null) {
					String id = matcher.group(3);
					String before = matcher.group(1) == null ? "" : matcher.group(1).strip();
					String after = matcher.group(4) == null ? "" : matcher.group(4).strip();
					label = !before.isEmpty() ? before : after;
					keyword.setSource(source);
					keyword.setId(id);
					if (MeshClient.SOURCE.equals(source) && !MeshClient.isDescriptorId(id)) {
						warn("\"" + id + "\" is not a MeSH descriptor ID (D followed by 6 or 9 digits).");
					}
					if (label.isEmpty()) {
						label = id;
						info("Keyword " + source + ":" + id + " has no text; write \"Label [" + source + ":" + id
								+ "]\" to add one.");
					}
				}
			}
			String text = label;
			boolean duplicate = general.getKeyword().stream().anyMatch(existing -> existing.getString().stream()
					.anyMatch(string -> string.getValue() != null && string.getValue().equalsIgnoreCase(text)));
			if (!duplicate) {
				insert(general, "keyword", text(keyword, label));
			}
		}
	}

	/** The canonical spelling of a keyword source ("mesh" to "MeSH"), or {@code null} if not a source. */
	private static String canonicalSource(String source) {
		for (String known : HlomSuggestions.KEYWORD_SOURCES) {
			if (known.equalsIgnoreCase(source.strip()) || TermMatcher.normalize(known).equals(TermMatcher.normalize(source))) {
				return known;
			}
		}
		return null;
	}

	void language(String value) {
		General general = general();
		if (first(ImportField.LANGUAGE)) {
			clear(general, "language");
		}
		String tag = ValueParsers.language(value);
		if (tag == null) {
			warn("\"" + value + "\" is not a language (use e.g. en, en-US or English) and was ignored.");
			return;
		}
		LanguageId languageId = LOM.createLanguageId();
		languageId.setValue(tag);
		insert(general, "language", languageId);
	}

	void identifier(String value) {
		General general = general();
		if (first(ImportField.IDENTIFIER)) {
			clear(general, "identifier");
		}
		for (String part : split(value, false)) {
			String catalog;
			String entry;
			String lower = part.toLowerCase(Locale.ROOT);
			if (ValueParsers.looksLikeUrl(part)) {
				ValueParsers.Parsed<String> url = ValueParsers.url(part);
				catalog = "URL";
				entry = url != null ? url.value() : part;
			} else if (lower.startsWith("doi:") || part.matches("10\\.\\d{4,9}/\\S+")) {
				catalog = "DOI";
				entry = lower.startsWith("doi:") ? part.substring(4).strip() : part;
			} else if (lower.startsWith("isbn")) {
				catalog = "ISBN";
				entry = part.substring(4).replaceFirst("^[:\\s-]+", "").strip();
			} else if (part.matches("[A-Za-z][\\w .-]{0,30}:\\s*\\S.*")) {
				int colon = part.indexOf(':');
				catalog = part.substring(0, colon).strip();
				entry = part.substring(colon + 1).strip();
			} else {
				String header = column == null ? "" : TermMatcher.normalize(column);
				catalog = header.isEmpty() || header.equals("identifier") || header.equals("id") ? "local" : column;
				entry = part;
			}
			addIdentifier(general, catalog, entry);
		}
	}

	private static void addIdentifier(General general, String catalogValue, String entryValue) {
		Identifier identifier = LOM.createIdentifier();
		Catalog catalog = LOM.createCatalog();
		catalog.setValue(catalogValue);
		insert(identifier, "catalog", catalog);
		Entry entry = LOM.createEntry();
		entry.setValue(entryValue);
		insert(identifier, "entry", entry);
		insert(general, "identifier", identifier);
	}

	// ---------------------------------------------------------------------------------------------------------
	// Life cycle

	void version(String value) {
		LifeCycle lifeCycle = lifeCycle();
		clear(lifeCycle, "version");
		insert(lifeCycle, "version", text(LOM.createVersion(), value));
	}

	void status(String value) {
		String term = match(termValues(LOM_PACKAGE.getStatus()), "Status", value, "status");
		if (term != null) {
			LifeCycle lifeCycle = lifeCycle();
			clear(lifeCycle, "status");
			insert(lifeCycle, "status", vocab(LOM_PACKAGE.getStatus(), term));
		}
	}

	void contributors(String role, String value) {
		LifeCycle lifeCycle = lifeCycle();
		if (first("contribute:" + role)) {
			lifeCycle.getContribute().removeIf(contribute -> role.equals(roleOf(contribute)));
		}
		// The HLOM binding allows one entity per contribute, so each person gets a contribute of their own.
		for (String name : split(value, false)) {
			Contribute contribute = LOM.createContribute();
			insert(contribute, "role", vocab(LOM_PACKAGE.getRole(), role));
			Entity entity = LOM.createEntity();
			entity.setValue(vCard(name));
			insert(contribute, "entity", entity);
			insert(lifeCycle, "contribute", contribute);
		}
	}

	private static String roleOf(Contribute contribute) {
		return contribute.getRole().isEmpty() ? null : Vocabularies.getValue(contribute.getRole().getFirst());
	}

	void publicationDate(String value) {
		ValueParsers.Parsed<String> parsed = ValueParsers.lomDate(value);
		if (parsed == null) {
			warn("\"" + value + "\" is not a date (use YYYY-MM-DD) and was ignored.");
			return;
		}
		note(parsed.note());
		List<Contribute> contributes = lom.getLifeCycle().isEmpty() ? List.of() : lifeCycle().getContribute();
		Contribute target = contributes.stream().filter(c -> "publisher".equals(roleOf(c))).findFirst()
				.orElse(contributes.stream().filter(c -> "author".equals(roleOf(c))).findFirst()
						.orElse(contributes.isEmpty() ? null : contributes.getFirst()));
		if (target == null) {
			target = LOM.createContribute();
			insert(target, "role", vocab(LOM_PACKAGE.getRole(), "publisher"));
			insert(lifeCycle(), "contribute", target);
			info("No author or publisher is given; the date was recorded for an unnamed publisher.");
		}
		clear(target, "date");
		Date date = LOM.createDate();
		DateTimeValue dateTime = LOM.createDateTimeValue();
		dateTime.setValue(parsed.value());
		insert(date, "dateTime", dateTime);
		insert(date, "description", text(LOM.createDescription(), "Publication date"));
		insert(target, "date", date);
	}

	// ---------------------------------------------------------------------------------------------------------
	// Technical

	void format(String value) {
		Technical technical = technical();
		if (first(ImportField.FORMAT)) {
			clear(technical, "format");
		}
		for (String part : split(value, true)) {
			String mime;
			if (part.matches("[\\w.+-]+/[\\w.+-]+") || part.equals("non-digital")) {
				mime = part.toLowerCase(Locale.ROOT);
			} else {
				mime = match(HlomSuggestions.MIME_TYPES, "Format", part, "file format (MIME type)");
			}
			if (mime != null) {
				var format = LOM.createFormat();
				format.setValue(mime);
				insert(technical, "format", format);
			}
		}
	}

	void location(String value) {
		Technical technical = technical();
		if (first(ImportField.LOCATION)) {
			clear(technical, "location");
		}
		for (String part : split(value, false)) {
			ValueParsers.Parsed<String> url = ValueParsers.url(part);
			var location = LOM.createLocation();
			if (url == null) {
				warn("\"" + part + "\" is not an absolute URL; it was kept as written.");
				location.setValue(part);
			} else {
				note(url.note());
				location.setValue(url.value());
			}
			insert(technical, "location", location);
		}
	}

	void size(String value) {
		ValueParsers.Parsed<Long> bytes = ValueParsers.size(value);
		if (bytes == null) {
			warn("\"" + value + "\" is not a size (use bytes or e.g. 2.4 MB) and was ignored.");
			return;
		}
		note(bytes.note());
		Technical technical = technical();
		clear(technical, "size");
		Size size = LOM.createSize();
		size.setValue(BigInteger.valueOf(bytes.value()));
		insert(technical, "size", size);
	}

	void technicalDuration(String value) {
		String iso = duration(value);
		if (iso != null) {
			Technical technical = technical();
			clear(technical, "duration");
			insert(technical, "duration", durationElement(LOM.createDuration(), iso));
		}
	}

	private String duration(String value) {
		ValueParsers.Parsed<String> parsed = ValueParsers.isoDuration(value);
		if (parsed == null) {
			warn("\"" + value + "\" is not a duration (use minutes, 1h30 or PT1H30M) and was ignored.");
			return null;
		}
		note(parsed.note() == null ? null : parsed.note() + " (" + parsed.value() + ")");
		return parsed.value();
	}

	private static <T extends DurationBase> T durationElement(T element, String iso) {
		DurationValue duration = LOM.createDurationValue();
		duration.setValue(iso);
		insert(element, "duration", duration);
		return element;
	}

	// ---------------------------------------------------------------------------------------------------------
	// Educational

	/** Adds vocabulary terms to an educational element feature, replacing the template's terms. */
	void educationalVocab(String featureName, EClass vocabClass, String vocabulary, String what, boolean many,
			String value) {
		List<String> terms = new ArrayList<>();
		for (String part : many ? split(value, true) : List.of(value)) {
			String term = match(termValues(vocabClass), vocabulary, part, what);
			if (term != null && !terms.contains(term)) {
				terms.add(term);
			}
		}
		if (terms.isEmpty()) {
			return;
		}
		Educational educational = educational();
		if (first(featureName) || !many) {
			clear(educational, featureName);
		}
		for (String term : terms) {
			boolean present = list(educational, featureName).stream()
					.anyMatch(existing -> term.equals(Vocabularies.getValue(existing)));
			if (!present) {
				insert(educational, featureName, vocab(vocabClass, term));
			}
		}
	}

	void learningTime(String value) {
		String iso = duration(value);
		if (iso != null) {
			Educational educational = educational();
			clear(educational, "typicalLearningTime");
			insert(educational, "typicalLearningTime", durationElement(LOM.createTypicalLearningTime(), iso));
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	// Rights

	void cost(String value) {
		YesNoType answer = yesNo(value);
		if (answer == null) {
			ValueParsers.Parsed<BigDecimal> price = ValueParsers.decimal(value);
			if (price != null) {
				answer = price.value().signum() > 0 ? YesNoType.YES : YesNoType.NO;
				info("The price \"" + value + "\" was read as cost = " + answer.getLiteral() + ".");
			}
		}
		if (answer == null) {
			warn("\"" + value + "\" is neither yes, no nor a price and was ignored.");
			return;
		}
		Rights rights = rights();
		clear(rights, "cost");
		insert(rights, "cost", vocab(LOM_PACKAGE.getCost(), answer.getLiteral()));
	}

	void copyright(String value) {
		YesNoType answer = yesNo(value);
		String description = null;
		if (answer == null) {
			answer = YesNoType.YES;
			description = value;
		}
		Rights rights = rights();
		clear(rights, "copyrightAndOtherRestrictions");
		insert(rights, "copyrightAndOtherRestrictions",
				vocab(LOM_PACKAGE.getCopyrightAndOtherRestrictions(), answer.getLiteral()));
		if (description != null) {
			clear(rights, "description");
			insert(rights, "description", text(LOM.createDescription(), description));
			info("The text was recorded as the rights description (copyright and other restrictions = yes).");
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	// Classification

	void objectives(String value) {
		for (String objective : split(value, false)) {
			Classification classification = LOM.createClassification();
			insert(classification, "purpose", vocab(LOM_PACKAGE.getPurpose(), "educational objective"));
			insert(classification, "description", text(LOM.createDescription(), objective));
			insert(lom, "classification", classification);
		}
	}

	void competencies(String value) {
		Classification classification = LOM.createClassification();
		insert(classification, "purpose", vocab(LOM_PACKAGE.getPurpose(), "competency"));
		for (String competency : split(value, true)) {
			TermMatcher.Match match = TermMatcher.match(ACGME_COMPETENCIES, null, competency);
			TaxonPath path = LOM.createTaxonPath();
			insert(path, "source", text(LOM.createSource(), match != null ? "ACGME Core Competencies" : "Competencies"));
			Taxon taxon = LOM.createTaxon();
			if (match != null) {
				Id id = LOM.createId();
				id.setValue(String.valueOf((char) ('a' + ACGME_COMPETENCIES.indexOf(match.term()))));
				insert(taxon, "id", id);
				if (!match.exact()) {
					info("\"" + competency + "\" was read as the ACGME competency \"" + match.term() + "\".");
				}
			}
			insert(taxon, "entry", text(LOM.createEntryTaxon(), match != null ? match.term() : competency));
			insert(path, "taxon", taxon);
			insert(classification, "taxonPath", path);
		}
		if (!classification.getTaxonPath().isEmpty()) {
			insert(lom, "classification", classification);
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	// Healthcare education

	void creditsAvailable(String value) {
		YesNoType answer = requireYesNo(value);
		if (answer != null) {
			education().setCreditsAvailable(answer);
			setByCsv.add("creditsAvailable");
		}
	}

	/** Applies one value per credits entry ({@code CME; CNE}); a single value applies to all entries. */
	private void perCredit(String value, java.util.function.BiConsumer<CreditsType, String> setter) {
		List<String> values = split(value, false);
		if (values.isEmpty()) {
			return;
		}
		setByCsv.add("credits");
		int existing = education().getCredits().size();
		if (values.size() == 1) {
			for (int i = 0; i < Math.max(1, existing); i++) {
				setter.accept(credits(i), values.getFirst());
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				setter.accept(credits(i), values.get(i));
			}
		}
	}

	void creditType(String value) {
		perCredit(value, (credits, part) -> {
			CreditTypeType type = matchEnum(CreditTypeType.VALUES, "CreditType", part, "credit type");
			if (type != null) {
				credits.setCreditType(type);
			}
		});
	}

	void numberOfCredits(String value) {
		perCredit(value, (credits, part) -> {
			ValueParsers.Parsed<BigDecimal> number = ValueParsers.decimal(part);
			if (number == null || number.value().signum() < 0) {
				warn("\"" + part + "\" is not a number of credits and was ignored.");
				return;
			}
			credits.setNumberOfCredits(number.value());
			if (number.note() != null) {
				CreditUnitType unit = TermMatcher.match(
						CreditUnitType.VALUES.stream().map(CreditUnitType::getLiteral).toList(), "CreditUnit",
						number.note()) instanceof TermMatcher.Match match ? CreditUnitType.get(match.term()) : null;
				if (unit != null && !setByCsv.contains("creditUnit")) {
					credits.setCreditUnit(unit);
					info("\"" + part + "\" was read as " + number.value().toPlainString() + " " + unit.getLiteral() + ".");
				} else {
					info("\"" + part + "\" was read as " + number.value().toPlainString() + ".");
				}
			}
		});
	}

	void creditUnit(String value) {
		setByCsv.add("creditUnit");
		perCredit(value, (credits, part) -> {
			CreditUnitType unit = matchEnum(CreditUnitType.VALUES, "CreditUnit", part, "credit unit");
			if (unit != null) {
				credits.setCreditUnit(unit);
			}
		});
	}

	void accreditingBody(String value) {
		perCredit(value, (credits, part) -> credits.setAccreditingBody(recommended(part,
				HlomSuggestions.ACCREDITING_BODIES, "accrediting body")));
	}

	void activityCertification(String value) {
		perCredit(value, (credits, part) -> credits.setActivityCertification(recommended(part,
				HlomSuggestions.ACTIVITY_CERTIFICATIONS, "credit category")));
	}

	/** The recommended spelling of a free-text value, or the value itself (with a note) if not recommended. */
	private String recommended(String value, List<String> recommendations, String what) {
		for (String each : recommendations) {
			if (TermMatcher.normalize(each).equals(TermMatcher.normalize(value))) {
				return each;
			}
		}
		TermMatcher.Match match = TermMatcher.match(recommendations, null, value);
		if (match != null && TermMatcher.normalize(value).length() >= TermMatcher.normalize(match.term()).length()) {
			info("\"" + value + "\" was written as the recommended " + what + " \"" + match.term() + "\".");
			return match.term();
		}
		info("\"" + value + "\" is not one of the recommended values for " + what + "; it was kept as written.");
		return value;
	}

	void accreditedProvider(String value) {
		perCredit(value, CreditsType::setAccreditedProvider);
	}

	void pacing(String value) {
		perCredit(value, (credits, part) -> {
			PacingType pacing = matchEnum(PacingType.VALUES, "Pacing", part, "pacing");
			if (pacing != null) {
				credits.setPacing(pacing);
			}
		});
	}

	void releaseDate(String value) {
		perCredit(value, (credits, part) -> {
			XMLGregorianCalendar date = date(part);
			if (date != null) {
				credits.setReleaseDate(date);
				setByCsv.add("release:" + System.identityHashCode(credits));
			}
		});
	}

	void expirationDate(String value) {
		XMLGregorianCalendar date = date(value);
		if (date != null) {
			education().setExpirationDate(date);
			setByCsv.add("expirationDate");
		}
	}

	void startDateTime(String value) {
		XMLGregorianCalendar dateTime = dateTime(value, LocalTime.of(0, 0));
		if (dateTime != null) {
			education().setStartDateTime(dateTime);
		}
	}

	void endDateTime(String value) {
		XMLGregorianCalendar dateTime = dateTime(value, LocalTime.of(23, 59, 59));
		if (dateTime != null) {
			education().setEndDateTime(dateTime);
		}
	}

	void audienceCategory(String value) {
		List<AudienceCategoryType> categories = new ArrayList<>();
		for (String part : split(value, true)) {
			AudienceCategoryType category = matchEnum(AudienceCategoryType.VALUES, "AudienceCategory", part,
					"audience category");
			if (category != null && !categories.contains(category)) {
				categories.add(category);
			}
		}
		if (!categories.isEmpty()) {
			List<AudienceCategoryType> list = audience().getAudienceCategory();
			if (first(ImportField.AUDIENCE_CATEGORY)) {
				list.clear();
			}
			categories.stream().filter(c -> !list.contains(c)).forEach(list::add);
		}
	}

	void professions(String value) {
		recommendedTexts(audience().getProfession(), ImportField.PROFESSION, value, HlomSuggestions.professionTerms(),
				"Profession", "profession");
	}

	void specialties(String value) {
		recommendedTexts(audience().getSpecialty(), ImportField.SPECIALTY, value, HlomSuggestions.SPECIALTIES,
				"Specialty", "specialty");
	}

	void readingLevel(String value) {
		String level = value.strip();
		Matcher grade = Pattern.compile("(?i)(?:grade\\s*)?(\\d{1,2})(?:st|nd|rd|th)?(?:\\s*grade)?").matcher(level);
		if (grade.matches()) {
			int number = Integer.parseInt(grade.group(1));
			level = number > 12 ? "above grade 12" : "grade " + number;
		} else {
			TermMatcher.Match match = TermMatcher.match(HlomSuggestions.READING_LEVELS, null, level);
			if (match != null) {
				level = match.term();
			}
		}
		if (!level.equals(value.strip())) {
			info("\"" + value + "\" was read as \"" + level + "\".");
		}
		List<LanguageString> list = audience().getReadingLevel();
		list.clear();
		list.add(text(LOM.createLanguageString(), level));
	}

	/** Adds free texts with recommended values (professions, specialties), replacing the template's texts. */
	private void recommendedTexts(List<LanguageString> list, ImportField key, String value,
			List<String> recommendations, String vocabulary, String what) {
		if (first(key)) {
			list.clear();
		}
		List<String> parts = split(value, false);
		if (parts.size() == 1 && parts.getFirst().contains(",")
				&& TermMatcher.match(recommendations, vocabulary, parts.getFirst()) == null) {
			parts = split(value, true);
		}
		for (String part : parts) {
			TermMatcher.Match match = TermMatcher.match(recommendations, vocabulary, part);
			String text = part;
			if (match != null) {
				text = match.term();
				if (!match.exact()) {
					info("\"" + part + "\" was read as \"" + text + "\".");
				}
			} else {
				info("\"" + part + "\" is not a recommended " + what + "; it was kept as written.");
			}
			String finalText = text;
			boolean present = list.stream().anyMatch(existing -> existing.getString().stream()
					.anyMatch(s -> finalText.equalsIgnoreCase(s.getValue())));
			if (!present) {
				list.add(text(LOM.createLanguageString(), text));
			}
		}
	}

	void participationModality(String value) {
		enumList(education().getParticipationModality(), ParticipationModalityType.VALUES, ImportField.PARTICIPATION_MODALITY,
				"ParticipationModality", "participation modality", value);
	}

	void activityDelivery(String value) {
		enumList(education().getActivityDelivery(), ActivityDeliveryType.VALUES, ImportField.ACTIVITY_DELIVERY,
				"ActivityDelivery", "activity delivery", value);
	}

	private <E extends Enumerator> void enumList(List<E> list, List<E> values, ImportField key, String vocabulary,
			String what, String value) {
		List<E> matched = new ArrayList<>();
		for (String part : split(value, true)) {
			E each = matchEnum(values, vocabulary, part, what);
			if (each != null && !matched.contains(each)) {
				matched.add(each);
			}
		}
		if (!matched.isEmpty()) {
			if (first(key)) {
				list.clear();
			}
			matched.stream().filter(e -> !list.contains(e)).forEach(list::add);
		}
	}

	void activityFormat(String value) {
		List<LanguageString> list = education().getActivityFormat();
		if (first(ImportField.ACTIVITY_FORMAT)) {
			list.clear();
		}
		for (String part : split(value, true)) {
			list.add(text(LOM.createLanguageString(), part));
		}
	}

	void sponsorship(String value) {
		ActivitySponsorshipType sponsorship = matchEnum(ActivitySponsorshipType.VALUES, "Sponsorship", value,
				"activity sponsorship");
		if (sponsorship != null) {
			education().setActivitySponsorship(sponsorship);
		}
	}

	void commercialSupport(String value) {
		YesNoType answer = yesNo(value);
		HealthcareEducationType education = education();
		if (answer == null) {
			education.setCommercialSupport(YesNoType.YES);
			education.setCommercialSupportAcknowledgement(text(LOM.createLanguageString(), value));
			info("The text was recorded as the commercial support acknowledgement (commercial support = yes).");
		} else {
			education.setCommercialSupport(answer);
		}
	}

	void financialRelationship(String value) {
		YesNoType answer = yesNo(value);
		HealthcareEducationType education = education();
		if (answer == null) {
			education.setRelevantFinancialRelationship(YesNoType.YES);
			education.getRelevantFinancialRelationshipDisclosure().clear();
			education.getRelevantFinancialRelationshipDisclosure().add(text(LOM.createLanguageString(), value));
			info("The text was recorded as a financial relationship disclosure (relationship = yes).");
		} else {
			education.setRelevantFinancialRelationship(answer);
		}
	}

	void offLabelUse(String value) {
		YesNoType answer = yesNo(value);
		HealthcareEducationType education = education();
		if (answer == null) {
			education.setOffLabelUse(YesNoType.YES);
			education.setOffLabelDescription(text(LOM.createLanguageString(), value));
			info("The text was recorded as the off-label use description (off-label use = yes).");
		} else {
			education.setOffLabelUse(answer);
		}
	}

	void contact(String value) {
		education().setContact(value);
	}

	void venue(String value) {
		ActivityLocationType location = activityLocation();
		if (first(ImportField.VENUE)) {
			location.getOrganization().clear();
		}
		location.getOrganization().addAll(split(value, false));
	}

	void building(String value) {
		activityLocation().setBuilding(value);
	}

	void room(String value) {
		activityLocation().setRoom(value);
	}

	void city(String value) {
		activityLocation().setCity(value);
	}

	void state(String value) {
		activityLocation().setStateOrProvince(value);
	}

	void postalCode(String value) {
		activityLocation().setPostalCode(value);
	}

	void country(String value) {
		CountryType country = AddressFactory.eINSTANCE.createCountryType();
		String code = ValueParsers.country(value);
		if (code != null) {
			country.setCountryCode(code);
			country.setCountryName(ValueParsers.countryName(code));
			if (!ValueParsers.countryName(code).equalsIgnoreCase(value.strip()) && !code.equalsIgnoreCase(value.strip())) {
				info("\"" + value + "\" was read as " + ValueParsers.countryName(code) + " (" + code + ").");
			}
		} else {
			country.setCountryName(value);
			info("\"" + value + "\" is not a known country; only the name was recorded.");
		}
		activityLocation().setCountry(country);
	}

	// ---------------------------------------------------------------------------------------------------------
	// Healthcare asset

	void medicalImageType(String value) {
		MedicalImageTypeType type = matchEnum(MedicalImageTypeType.VALUES, "MedicalImageType", value,
				"medical image type");
		if (type != null) {
			asset().setMedicalImageType(type);
		}
	}

	void orientation(String value) {
		OrientationType orientation = matchEnum(OrientationType.VALUES, "Orientation", value, "orientation");
		if (orientation != null) {
			asset().setOrientation(orientation);
		}
	}

	void specimenType(String value) {
		SpecimenTypeType type = matchEnum(SpecimenTypeType.VALUES, "SpecimenType", value, "specimen type");
		if (type != null) {
			asset().setSpecimenType(type);
		}
	}

	void annotated(String value) {
		YesNoType answer = requireYesNo(value);
		if (answer != null) {
			asset().setAnnotated(answer);
		}
	}

	void clinicalHistory(String value) {
		asset().setClinicalHistory(text(LOM.createLanguageString(), value));
	}

	void magnification(String value) {
		BigInteger number = integer(value, "magnification");
		if (number != null) {
			asset().setMagnification(number);
		}
	}

	void imageWidth(String value) {
		BigInteger number = integer(value, "width in pixels");
		if (number != null) {
			asset().setFileWidth(number);
		}
	}

	void imageHeight(String value) {
		BigInteger number = integer(value, "height in pixels");
		if (number != null) {
			asset().setFileHeight(number);
		}
	}

	void imageDimensions(String value) {
		Matcher matcher = DIMENSIONS.matcher(value.strip());
		if (!matcher.matches()) {
			warn("\"" + value + "\" is not WIDTHxHEIGHT (e.g. 1024x768) and was ignored.");
			return;
		}
		asset().setFileWidth(new BigInteger(matcher.group(1)));
		asset().setFileHeight(new BigInteger(matcher.group(2)));
	}

	private BigInteger integer(String value, String what) {
		ValueParsers.Parsed<BigInteger> parsed = ValueParsers.integer(value);
		if (parsed == null) {
			warn("\"" + value + "\" is not a valid " + what + " (a whole number) and was ignored.");
			return null;
		}
		note(parsed.note());
		return parsed.value();
	}

	// ---------------------------------------------------------------------------------------------------------
	// Finishing

	/**
	 * Completes the record after all cells were applied: removes the template's placeholder texts (if
	 * requested), keeps credits consistent with "credits available" and the expiration date, and derives the
	 * identifier from the location URL when there is none.
	 */
	void finish(boolean removePlaceholders, boolean identifierFromLocation) {
		at(null, null);
		if (removePlaceholders) {
			removePlaceholders();
		}
		HealthcareEducationType education = lom.getHealthcareMetadata().isEmpty() ? null
				: lom.getHealthcareMetadata().getFirst().getHealthcareEducation();
		if (education != null) {
			finishCredits(education);
		}
		if (identifierFromLocation && !lom.getGeneral().isEmpty() && general().getIdentifier().isEmpty()
				&& !lom.getTechnical().isEmpty()) {
			for (var location : technical().getLocation()) {
				if (location.getValue() != null && ValueParsers.looksLikeUrl(location.getValue())) {
					addIdentifier(general(), "URL", location.getValue());
					break;
				}
			}
		}
	}

	private void finishCredits(HealthcareEducationType education) {
		boolean creditsFromCsv = setByCsv.contains("credits");
		if (creditsFromCsv && !setByCsv.contains("creditsAvailable")) {
			education.setCreditsAvailable(YesNoType.YES);
		}
		if (setByCsv.contains("creditsAvailable") && education.getCreditsAvailable() == YesNoType.NO
				&& !education.getCredits().isEmpty()) {
			if (creditsFromCsv) {
				warn("Credits are marked as not available, but credit details are given.");
			} else {
				education.getCredits().clear();
			}
		}
		XMLGregorianCalendar expiration = education.getExpirationDate();
		if (setByCsv.contains("expirationDate") && expiration != null) {
			for (CreditsType credits : education.getCredits()) {
				credits.setExpirationDate((XMLGregorianCalendar) expiration.clone());
				if (credits.getReleaseDate() != null && !setByCsv.contains("release:" + System.identityHashCode(credits))
						&& credits.getReleaseDate().compare(expiration) > 0) {
					credits.setReleaseDate(null);
				}
			}
		}
	}

	private void removePlaceholders() {
		List<EObject> placeholders = new ArrayList<>();
		for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
			EObject object = it.next();
			if (object instanceof LangString string && isPlaceholder(string.getValue())
					&& string.eContainer() instanceof LanguageString owner && owner.eContainer() != null) {
				placeholders.add(owner);
			}
		}
		placeholders.forEach(EcoreUtil::remove);
		if (!lom.getLifeCycle().isEmpty()) {
			lifeCycle().getContribute().removeIf(contribute -> !contribute.getEntity().isEmpty()
					&& contribute.getEntity().stream().allMatch(entity -> isPlaceholder(fn(entity))));
		}
		for (HealthcareMetadataType healthcare : lom.getHealthcareMetadata()) {
			HealthcareEducationType education = healthcare.getHealthcareEducation();
			if (education == null) {
				continue;
			}
			if (isPlaceholder(education.getContact())) {
				education.setContact(null);
			}
			for (CreditsType credits : education.getCredits()) {
				if (isPlaceholder(credits.getAccreditedProvider())) {
					credits.setAccreditedProvider(null);
				}
			}
			ActivityLocationType location = education.getActivityLocation();
			if (location != null) {
				location.getOrganization().removeIf(RecordBuilder::isPlaceholder);
				if (isPlaceholder(location.getRoom())) {
					location.setRoom(null);
				}
				if (isPlaceholder(location.getBuilding())) {
					location.setBuilding(null);
				}
			}
		}
		// Drop categories the placeholders leave empty.
		lom.getLifeCycle().removeIf(lifeCycle -> lifeCycle.getGroup().isEmpty());
		lom.getRights().removeIf(rights -> rights.getGroup().isEmpty());
	}

	private static String fn(VCard vcard) {
		String value = vcard.getValue();
		if (value == null) {
			return null;
		}
		for (String line : value.split("\\r?\\n")) {
			if (line.regionMatches(true, 0, "FN:", 0, 3)) {
				return line.substring(3).strip();
			}
		}
		return null;
	}

	private static boolean isPlaceholder(String value) {
		return value != null && TEMPLATE_PLACEHOLDERS.contains(value.strip());
	}
}
