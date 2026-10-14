/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.address.AddressType;
import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Identifier;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.Rights;
import edu.louisiana.caai.lom.model.lom.Technical;
import edu.louisiana.caai.lom.model.lom.VCard;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * A flat, presentation-ready digest of an HLOM record: what a catalog card, a library table or a search index
 * needs, extracted once from the EMF model. All strings are trimmed; missing values are {@code null} (single
 * values) or empty lists, never {@code null} lists.
 *
 * @param title                 the title (first non-blank string), or {@code null}
 * @param description           the first description, or {@code null}
 * @param languages             the languages of the resource ({@code general/language})
 * @param kind                  whether this is an educational offering, an image asset or another learning object
 * @param identifiers           identifiers as {@code catalog: entry}
 * @param keywords              the keywords with their MeSH (or other) source and id
 * @param contributors          life cycle contributors with role and formatted name (vCard {@code FN})
 * @param version               the version, or {@code null}
 * @param status                the life cycle status, or {@code null}
 * @param learningResourceTypes educational learning resource types
 * @param contexts              educational contexts
 * @param objectives            classification descriptions with purpose {@code educational objective}
 * @param credits               the credits that can be earned
 * @param audienceCategories    target audience categories
 * @param professions           target professions
 * @param specialties           target specialties
 * @param releaseDate           the (first) credit release date as ISO text, or {@code null}
 * @param expirationDate        the expiration date of the offering or its first credit, or {@code null}
 * @param startDate             start of a live activity, or {@code null}
 * @param endDate               end of a live activity, or {@code null}
 * @param city                  activity location city, or {@code null}
 * @param country               activity location country, or {@code null}
 * @param formats               technical MIME formats
 * @param locations             technical locations (URLs)
 * @param cost                  rights cost ({@code yes}/{@code no}), or {@code null}
 * @param rights                the rights description, or {@code null}
 * @param details               further kind-specific facts (delivery, modality, image properties, ...)
 */
public record HlomSummary(String title, String description, List<String> languages, Kind kind,
		List<String> identifiers, List<Keyword> keywords, List<Contributor> contributors, String version, String status,
		List<String> learningResourceTypes, List<String> contexts, List<String> objectives, List<Credit> credits,
		List<String> audienceCategories, List<String> professions, List<String> specialties, String releaseDate,
		String expirationDate, String startDate, String endDate, String city, String country, List<String> formats,
		List<String> locations, String cost, String rights, List<Fact> details) {

	/** What an HLOM record describes. */
	public enum Kind {
		/** A course or activity with {@code healthcareEducation} metadata. */
		EDUCATIONAL_OFFERING("Educational offering"),
		/** A medical image or other media with {@code healthcareAsset} metadata. */
		IMAGE_ASSET("Image asset"),
		/** Any other learning object. */
		LEARNING_OBJECT("Learning object");

		private final String label;

		Kind(String label) {
			this.label = label;
		}

		/** A human readable name. */
		public String label() {
			return label;
		}
	}

	/** A keyword, with its controlled vocabulary source (e.g. {@code MeSH}) and term id if given. */
	public record Keyword(String text, String source, String id) {
		/** Whether the keyword is a MeSH descriptor. */
		public boolean isMesh() {
			return source != null && source.equalsIgnoreCase("MeSH") && id != null;
		}
	}

	/** A contributor: role (e.g. {@code author}), name and optional date. */
	public record Contributor(String role, String name, String date) {
	}

	/** Credits offered for an educational activity. */
	public record Credit(String type, BigDecimal number, String unit, String accreditingBody, String certification,
			String provider) {

		/** For example {@code 1.5 CME Credit}. */
		public String label() {
			return join(" ", number == null ? null : number.stripTrailingZeros().toPlainString(), type, unit);
		}
	}

	/** A labeled fact, e.g. {@code Orientation: sagittal}. */
	public record Fact(String label, String value) {
	}

	/** Summarizes a document; returns an empty summary for {@code null}. */
	public static HlomSummary of(DocumentRoot root) {
		return new Extractor().extract(root == null ? null : root.getLom());
	}

	/** A summary with only a title and a description (for documents of other standards). */
	public static HlomSummary basic(String title, String description) {
		HlomSummary empty = of((DocumentRoot) null);
		return new HlomSummary(title, description, empty.languages(), empty.kind(), empty.identifiers(),
				empty.keywords(), empty.contributors(), empty.version(), empty.status(), empty.learningResourceTypes(),
				empty.contexts(), empty.objectives(), empty.credits(), empty.audienceCategories(), empty.professions(),
				empty.specialties(), empty.releaseDate(), empty.expirationDate(), empty.startDate(), empty.endDate(),
				empty.city(), empty.country(), empty.formats(), empty.locations(), empty.cost(), empty.rights(),
				empty.details());
	}

	/** Summarizes the document of a resource; returns an empty summary if it holds none. */
	public static HlomSummary of(Resource resource) {
		return of(HlomResources.getDocumentRoot(resource));
	}

	/** The title, or the given fallback (typically the file name) if the record has none. */
	public String titleOr(String fallback) {
		return title != null ? title : fallback;
	}

	/** The description shortened to at most {@code max} characters at a word boundary, or {@code ""}. */
	public String excerpt(int max) {
		if (description == null) {
			return "";
		}
		String text = description.replaceAll("\\s+", " ");
		if (text.length() <= max) {
			return text;
		}
		int cut = text.lastIndexOf(' ', max - 1);
		return text.substring(0, cut > max / 2 ? cut : max - 1).strip() + "…";
	}

	/** Contributor names, without duplicates, in document order. */
	public List<String> contributorNames() {
		return contributors.stream().map(Contributor::name).distinct().toList();
	}

	/** Credit totals per {@code type unit} (e.g. {@code CME Credit} → 1.5), in document order. */
	public Map<String, BigDecimal> creditTotals() {
		return totals(List.of(this));
	}

	/** Credit totals per {@code type unit} over many summaries. */
	public static Map<String, BigDecimal> totals(Collection<HlomSummary> summaries) {
		Map<String, BigDecimal> totals = new LinkedHashMap<>();
		for (HlomSummary summary : summaries) {
			for (Credit credit : summary.credits()) {
				if (credit.number() != null) {
					String key = join(" ", credit.type(), credit.unit());
					totals.merge(key.isEmpty() ? "Credits" : key, credit.number(), BigDecimal::add);
				}
			}
		}
		return totals;
	}

	/** Credits as text, e.g. {@code 1.5 CME Credit · 1.7 CNE Contact Hour}, or {@code ""}. */
	public String creditsLabel() {
		return credits.stream().map(Credit::label).filter(s -> !s.isEmpty()).collect(Collectors.joining(" · "));
	}

	/** Audience as text: professions, then specialties in parentheses. */
	public String audienceLabel() {
		String professionText = String.join(", ", professions);
		String specialtyText = String.join(", ", specialties);
		if (professionText.isEmpty()) {
			return specialtyText;
		}
		return specialtyText.isEmpty() ? professionText : professionText + " (" + specialtyText + ")";
	}

	/** City and country, comma separated, or {@code ""}. */
	public String locationLabel() {
		return join(", ", city, country);
	}

	/** Lower-case text of all searchable fields, for filtering. */
	public String searchText() {
		List<String> parts = new ArrayList<>();
		parts.add(title);
		parts.add(description);
		parts.add(kind.label());
		keywords.forEach(k -> {
			parts.add(k.text());
			parts.add(k.id());
		});
		parts.addAll(contributorNames());
		parts.addAll(professions);
		parts.addAll(specialties);
		parts.addAll(contexts);
		parts.addAll(learningResourceTypes);
		parts.addAll(identifiers);
		parts.add(creditsLabel());
		parts.add(locationLabel());
		return parts.stream().filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(" "))
				.toLowerCase(Locale.ROOT);
	}

	static String join(String separator, String... parts) {
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (part != null && !part.isBlank()) {
				if (builder.length() > 0) {
					builder.append(separator);
				}
				builder.append(part.strip());
			}
		}
		return builder.toString();
	}

	/**
	 * Extracts the vCard formatted name ({@code FN}) of a LOM entity; plain text entities are returned as they
	 * are. Handles folded lines and vCard escapes.
	 */
	public static String vcardName(String vcard) {
		if (vcard == null || vcard.isBlank()) {
			return null;
		}
		String unfolded = vcard.replace("\r\n", "\n").replace('\r', '\n').replaceAll("\n[ \t]", "");
		if (!unfolded.strip().toUpperCase(Locale.ROOT).startsWith("BEGIN:VCARD")) {
			return clean(unfolded.replaceAll("\\s+", " "));
		}
		String structuredName = null;
		String organization = null;
		for (String line : unfolded.split("\n")) {
			int colon = line.indexOf(':');
			if (colon < 0) {
				continue;
			}
			String property = line.substring(0, colon).strip().toUpperCase(Locale.ROOT);
			int parameters = property.indexOf(';');
			String name = parameters < 0 ? property : property.substring(0, parameters);
			String value = unescape(line.substring(colon + 1));
			if (name.equals("FN") && !value.isBlank()) {
				return value.strip();
			} else if (name.equals("N")) {
				String[] parts = value.split(";", -1);
				structuredName = join(" ", parts.length > 1 ? parts[1] : null, parts[0]);
			} else if (name.equals("ORG")) {
				organization = value.replace(';', ' ');
			}
		}
		return clean(structuredName != null && !structuredName.isEmpty() ? structuredName : organization);
	}

	private static String unescape(String value) {
		return value.replace("\\n", " ").replace("\\N", " ").replace("\\,", ",").replace("\\;", ";")
				.replace("\\\\", "\\");
	}

	private static String clean(String value) {
		return value == null || value.isBlank() ? null : value.strip();
	}

	/** Walks the model; kept separate so that the record stays a plain value. */
	private static final class Extractor {

		private final List<String> languages = new ArrayList<>();
		private final List<String> identifiers = new ArrayList<>();
		private final List<Keyword> keywords = new ArrayList<>();
		private final List<Contributor> contributors = new ArrayList<>();
		private final Set<String> learningResourceTypes = new LinkedHashSet<>();
		private final Set<String> contexts = new LinkedHashSet<>();
		private final List<String> objectives = new ArrayList<>();
		private final List<Credit> credits = new ArrayList<>();
		private final Set<String> audienceCategories = new LinkedHashSet<>();
		private final Set<String> professions = new LinkedHashSet<>();
		private final Set<String> specialties = new LinkedHashSet<>();
		private final List<String> formats = new ArrayList<>();
		private final List<String> locations = new ArrayList<>();
		private final List<Fact> details = new ArrayList<>();
		private String title;
		private String description;
		private String version;
		private String status;
		private String releaseDate;
		private String expirationDate;
		private String startDate;
		private String endDate;
		private String city;
		private String country;
		private String cost;
		private String rights;
		private Kind kind = Kind.LEARNING_OBJECT;

		HlomSummary extract(Lom lom) {
			if (lom != null) {
				lom.getGeneral().forEach(this::general);
				lom.getLifeCycle().forEach(this::lifeCycle);
				lom.getTechnical().forEach(this::technical);
				lom.getEducational().forEach(this::educational);
				lom.getRights().forEach(this::rights);
				lom.getClassification().forEach(this::classification);
				lom.getHealthcareMetadata().forEach(this::healthcare);
			}
			return new HlomSummary(title, description, List.copyOf(languages), kind, List.copyOf(identifiers),
					List.copyOf(keywords), List.copyOf(contributors), version, status,
					List.copyOf(learningResourceTypes), List.copyOf(contexts), List.copyOf(objectives),
					List.copyOf(credits), List.copyOf(audienceCategories), List.copyOf(professions),
					List.copyOf(specialties), releaseDate, expirationDate, startDate, endDate, city, country,
					List.copyOf(formats), List.copyOf(locations), cost, rights, List.copyOf(details));
		}

		private void general(General general) {
			general.getTitle().forEach(t -> title = first(title, text(t)));
			general.getDescription().forEach(d -> description = first(description, text(d)));
			for (LanguageId language : general.getLanguage()) {
				add(languages, language.getValue());
			}
			for (Identifier identifier : general.getIdentifier()) {
				String catalog = identifier.getCatalog().isEmpty() ? null : identifier.getCatalog().getFirst().getValue();
				String entry = identifier.getEntry().isEmpty() ? null : identifier.getEntry().getFirst().getValue();
				add(identifiers, entry == null ? null : join(": ", catalog, entry));
			}
			for (edu.louisiana.caai.lom.model.lom.Keyword keyword : general.getKeyword()) {
				String text = text(keyword);
				if (text != null) {
					keywords.add(new Keyword(text, clean(keyword.getSource()), clean(keyword.getId())));
				}
			}
		}

		private void lifeCycle(LifeCycle lifeCycle) {
			lifeCycle.getVersion().forEach(v -> version = first(version, text(v)));
			lifeCycle.getStatus().forEach(s -> status = first(status, Vocabularies.getValue(s)));
			for (Contribute contribute : lifeCycle.getContribute()) {
				String role = contribute.getRole().isEmpty() ? null : Vocabularies.getValue(contribute.getRole().getFirst());
				String date = null;
				if (!contribute.getDate().isEmpty() && !contribute.getDate().getFirst().getDateTime().isEmpty()) {
					date = clean(contribute.getDate().getFirst().getDateTime().getFirst().getValue());
				}
				for (VCard entity : contribute.getEntity()) {
					String name = vcardName(entity.getValue());
					if (name != null) {
						contributors.add(new Contributor(role, name, date));
					}
				}
			}
		}

		private void technical(Technical technical) {
			technical.getFormat().forEach(f -> add(formats, f.getValue()));
			technical.getLocation().forEach(l -> add(locations, l.getValue()));
			if (!technical.getSize().isEmpty() && technical.getSize().getFirst().getValue() != null) {
				details.add(new Fact("File size", fileSize(technical.getSize().getFirst().getValue())));
			}
		}

		private void educational(Educational educational) {
			educational.getLearningResourceType().forEach(t -> addTo(learningResourceTypes, Vocabularies.getValue(t)));
			educational.getContext().forEach(c -> addTo(contexts, Vocabularies.getValue(c)));
		}

		private void rights(Rights rightsElement) {
			rightsElement.getCost().forEach(c -> cost = first(cost, Vocabularies.getValue(c)));
			rightsElement.getDescription().forEach(d -> rights = first(rights, text(d)));
		}

		private void classification(Classification classification) {
			String purpose = classification.getPurpose().isEmpty() ? null
					: Vocabularies.getValue(classification.getPurpose().getFirst());
			if ("educational objective".equals(purpose)) {
				classification.getDescription().forEach(d -> add(objectives, text(d)));
			}
		}

		private void healthcare(HealthcareMetadataType metadata) {
			if (metadata.getHealthcareEducation() != null) {
				kind = Kind.EDUCATIONAL_OFFERING;
				education(metadata.getHealthcareEducation());
			} else if (metadata.getHealthcareAsset() != null) {
				if (kind == Kind.LEARNING_OBJECT) {
					kind = Kind.IMAGE_ASSET;
				}
				asset(metadata.getHealthcareAsset());
			}
		}

		private void education(HealthcareEducationType education) {
			expirationDate = first(expirationDate, date(education.getExpirationDate()));
			startDate = first(startDate, date(education.getStartDateTime()));
			endDate = first(endDate, date(education.getEndDateTime()));
			for (CreditsType credit : education.getCredits()) {
				credits.add(new Credit(literal(credit.isSetCreditType(), credit.getCreditType()),
						credit.getNumberOfCredits(), literal(credit.isSetCreditUnit(), credit.getCreditUnit()),
						clean(credit.getAccreditingBody()), clean(credit.getActivityCertification()),
						clean(credit.getAccreditedProvider())));
				releaseDate = first(releaseDate, date(credit.getReleaseDate()));
				expirationDate = first(expirationDate, date(credit.getExpirationDate()));
			}
			TargetAudienceType audience = education.getTargetAudience();
			if (audience != null) {
				for (AudienceCategoryType category : audience.getAudienceCategory()) {
					addTo(audienceCategories, category.getLiteral());
				}
				audience.getProfession().forEach(p -> addTo(professions, text(p)));
				audience.getSpecialty().forEach(s -> addTo(specialties, text(s)));
			}
			AddressType location = education.getActivityLocation();
			if (location != null) {
				city = clean(location.getCity());
				if (location.getCountry() != null) {
					country = first(clean(location.getCountry().getCountryName()),
							clean(location.getCountry().getCountryCode()));
				}
			}
			fact("Activity format", texts(education.getActivityFormat()));
			fact("Delivery", education.getActivityDelivery().stream().map(ActivityDeliveryType::getLiteral)
					.collect(Collectors.joining(", ")));
			fact("Participation", education.getParticipationModality().stream()
					.map(ParticipationModalityType::getLiteral).collect(Collectors.joining(", ")));
			fact("Sponsorship", literal(education.isSetActivitySponsorship(), education.getActivitySponsorship()));
			fact("Commercial support", literal(education.isSetCommercialSupport(), education.getCommercialSupport()));
			fact("Off-label use", literal(education.isSetOffLabelUse(), education.getOffLabelUse()));
			fact("Contact", clean(education.getContact()));
		}

		private void asset(HealthcareAssetType asset) {
			if (asset.getFileWidth() != null && asset.getFileHeight() != null) {
				fact("Image size", asset.getFileWidth() + " × " + asset.getFileHeight() + " px");
			}
			fact("Image type", literal(asset.isSetMedicalImageType(), asset.getMedicalImageType()));
			fact("Orientation", literal(asset.isSetOrientation(), asset.getOrientation()));
			fact("Specimen", literal(asset.isSetSpecimenType(), asset.getSpecimenType()));
			if (asset.getMagnification() != null) {
				fact("Magnification", asset.getMagnification() + "×");
			}
			fact("Annotated", literal(asset.isSetAnnotated(), asset.getAnnotated()));
			fact("Clinical history", text(asset.getClinicalHistory()));
		}

		private void fact(String label, String value) {
			if (value != null && !value.isBlank()) {
				details.add(new Fact(label, value.strip()));
			}
		}

		private static String fileSize(BigInteger bytes) {
			double value = bytes.doubleValue();
			if (value < 1024) {
				return bytes + " bytes";
			}
			String[] units = { "KB", "MB", "GB", "TB" };
			int unit = -1;
			while (value >= 1024 && unit < units.length - 1) {
				value /= 1024;
				unit++;
			}
			return String.format(Locale.ROOT, "%.1f %s", value, units[unit]);
		}

		private static String literal(boolean isSet, Enumerator value) {
			return isSet && value != null ? value.getLiteral() : null;
		}

		private static String date(XMLGregorianCalendar calendar) {
			return calendar == null ? null : calendar.toXMLFormat();
		}

		private static String texts(List<LanguageString> strings) {
			return strings.stream().map(Extractor::text).filter(s -> s != null).collect(Collectors.joining(", "));
		}

		/** The first non-blank string of a multilingual element. */
		private static String text(EObject element) {
			if (element instanceof LanguageString languageString) {
				for (LangString string : languageString.getString()) {
					String value = clean(string.getValue());
					if (value != null) {
						return value.replaceAll("\\s+", " ");
					}
				}
			}
			return null;
		}

		private static String first(String current, String candidate) {
			return current != null ? current : clean(candidate);
		}

		private static void add(List<String> list, String value) {
			String cleaned = clean(value);
			if (cleaned != null) {
				list.add(cleaned);
			}
		}

		private static void addTo(Set<String> set, String value) {
			String cleaned = clean(value);
			if (cleaned != null) {
				set.add(cleaned);
			}
		}
	}
}
