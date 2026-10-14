/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.Chip;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.Technical;
import edu.louisiana.caai.lom.model.lom.VCard;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * Read-only digest of an HLOM record for the {@link OverviewPage}: title, description, keywords and the key facts,
 * as display strings.
 */
final class OverviewFacts {

	/** A labeled fact; {@code value} is {@code null} if the record doesn't say. */
	record Fact(String label, String value, String pageId) {
	}

	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

	private final Lom lom;
	private final General general;
	private final LifeCycle lifeCycle;
	private final Technical technical;
	private final HealthcareEducationType education;
	private final HealthcareAssetType asset;

	OverviewFacts(Lom lom) {
		this.lom = lom;
		general = first(lom.getGeneral());
		lifeCycle = first(lom.getLifeCycle());
		technical = first(lom.getTechnical());
		HealthcareMetadataType healthcare = first(lom.getHealthcareMetadata());
		education = healthcare == null ? null : healthcare.getHealthcareEducation();
		asset = healthcare == null ? null : healthcare.getHealthcareAsset();
	}

	/** The title, or {@code null}. */
	String title() {
		return general == null || general.getTitle().isEmpty() ? null : text(general.getTitle().getFirst());
	}

	/** The first description, or {@code null}. */
	String description() {
		return general == null ? null
				: general.getDescription().stream().map(OverviewFacts::text).filter(Objects::nonNull).findFirst()
						.orElse(null);
	}

	/** The keywords as chips; coded keywords show their terminology and id. */
	List<Chip> keywords() {
		List<Chip> chips = new ArrayList<>();
		if (general != null) {
			for (Keyword keyword : general.getKeyword()) {
				String text = text(keyword);
				if (text == null) {
					continue;
				}
				String source = blankToNull(keyword.getSource());
				String id = blankToNull(keyword.getId());
				String detail = id != null ? (source != null ? source + " " + id : id) : source;
				chips.add(new Chip(text, detail, source != null && id != null));
			}
		}
		return chips;
	}

	/** The languages of the resource. */
	List<String> languages() {
		return general == null ? List.of()
				: general.getLanguage().stream().map(l -> blankToNull(l.getValue())).filter(Objects::nonNull)
						.distinct().toList();
	}

	/**
	 * A one-line characterization, e.g. "Educational offering · CME 1.5 Credit · Learner paced" or "Medical image
	 * asset · radiograph".
	 */
	String kind(boolean offering, boolean media) {
		List<String> parts = new ArrayList<>();
		if (offering) {
			parts.add(Messages.OverviewFacts_EducationalOffering);
			if (education != null) {
				for (CreditsType credits : education.getCredits()) {
					String amount = creditAmount(credits);
					if (amount != null) {
						parts.add(amount);
					}
				}
				education.getCredits().stream().filter(CreditsType::isSetPacing)
						.map(c -> capitalize(c.getPacing().getLiteral())).distinct().forEach(parts::add);
				education.getActivityDelivery().stream().map(d -> capitalize(d.getLiteral())).distinct()
						.forEach(parts::add);
			}
		} else if (media) {
			Set<String> types = vocabularyValues(e -> e.getLearningResourceType().stream());
			String kind = types.contains("video") ? "Medical video asset"
					: types.contains("animation") ? "Medical animation asset" : "Medical image asset";
			parts.add(kind);
			if (asset != null && asset.isSetMedicalImageType()) {
				parts.add(asset.getMedicalImageType().getLiteral());
			}
			if (technical != null) {
				technical.getFormat().stream().map(f -> blankToNull(f.getValue())).filter(Objects::nonNull)
						.findFirst().ifPresent(parts::add);
			}
		} else {
			Set<String> types = vocabularyValues(e -> e.getLearningResourceType().stream());
			parts.add(types.isEmpty() ? "Learning resource" : capitalize(String.join(", ", types)));
		}
		return String.join("  ·  ", parts);
	}

	/** The key facts in display order; healthcare education facts only for offerings, image facts only for assets. */
	List<Fact> facts(boolean offering, boolean media) {
		List<Fact> facts = new ArrayList<>();
		facts.add(new Fact(Messages.OverviewFacts_VersionStatus, versionAndStatus(), "lifecycle"));
		facts.add(new Fact(Messages.OverviewFacts_Contributors, contributors(), "lifecycle"));
		facts.add(new Fact(Messages.OverviewFacts_Published, releaseDate(), "lifecycle"));
		if (offering) {
			facts.add(new Fact(Messages.OverviewFacts_Expires, expirationDate(), "healthcare-education"));
			facts.add(new Fact(Messages.OverviewFacts_TargetAudience, audience(), "healthcare-education"));
			facts.add(new Fact(Messages.OverviewFacts_Credits, credits(), "healthcare-education"));
			facts.add(new Fact(Messages.OverviewFacts_Location, location(), "healthcare-education"));
		}
		if (media) {
			facts.add(new Fact(Messages.OverviewFacts_Image, image(), "healthcare-asset"));
		}
		facts.add(new Fact(Messages.OverviewFacts_ResourceType, join(vocabularyValues(e -> e.getLearningResourceType().stream())),
				"educational"));
		facts.add(new Fact(Messages.OverviewFacts_Context, join(vocabularyValues(e -> e.getContext().stream())), "educational"));
		facts.add(new Fact(Messages.OverviewFacts_Format, format(), "technical"));
		return facts;
	}

	private String versionAndStatus() {
		if (lifeCycle == null) {
			return null;
		}
		String version = lifeCycle.getVersion().stream().map(OverviewFacts::text).filter(Objects::nonNull)
				.findFirst().orElse(null);
		String status = lifeCycle.getStatus().stream().map(Vocabularies::getValue).filter(Objects::nonNull)
				.findFirst().orElse(null);
		if (version == null && status == null) {
			return null;
		}
		return version == null ? capitalize(status)
				: status == null ? "Version " + version : "Version " + version + "  ·  " + capitalize(status);
	}

	private String contributors() {
		if (lifeCycle == null) {
			return null;
		}
		List<String> names = new ArrayList<>();
		for (Contribute contribute : lifeCycle.getContribute()) {
			String role = contribute.getRole().stream().map(Vocabularies::getValue).filter(Objects::nonNull)
					.findFirst().orElse(null);
			for (VCard entity : contribute.getEntity()) {
				String name = name(entity);
				if (name != null) {
					names.add(role == null ? name : name + " (" + role + ")");
				}
			}
		}
		if (names.isEmpty()) {
			return null;
		}
		if (names.size() > 4) {
			int more = names.size() - 3;
			names = new ArrayList<>(names.subList(0, 3));
			names.add(NLS.bind(Messages.OverviewFacts_AndMore, more));
		}
		return String.join("\n", names);
	}

	/** The formatted name of a vCard entity, or the plain text if it isn't a vCard. */
	static String name(VCard entity) {
		String value = blankToNull(entity.getValue());
		if (value == null) {
			return null;
		}
		String name = VCardDialog.formattedName(value);
		if (name != null && !name.isBlank()) {
			return name;
		}
		return value.toUpperCase(Locale.ROOT).contains("BEGIN:VCARD") ? null : value.strip();
	}

	private String releaseDate() {
		String published = null;
		String any = null;
		if (lifeCycle != null) {
			for (Contribute contribute : lifeCycle.getContribute()) {
				String date = contribute.getDate().stream().flatMap(d -> d.getDateTime().stream())
						.map(v -> blankToNull(v.getValue())).filter(Objects::nonNull).findFirst().orElse(null);
				if (date == null) {
					continue;
				}
				boolean publisher = contribute.getRole().stream().map(Vocabularies::getValue)
						.anyMatch("publisher"::equals);
				if (publisher && published == null) {
					published = date;
				}
				if (any == null) {
					any = date;
				}
			}
		}
		String date = published != null ? published : any;
		if (date == null && education != null) {
			date = education.getCredits().stream().map(CreditsType::getReleaseDate).filter(Objects::nonNull)
					.map(XMLGregorianCalendar::toXMLFormat).findFirst().orElse(null);
		}
		return date == null ? null : formatDate(date);
	}

	private String expirationDate() {
		if (education == null) {
			return null;
		}
		XMLGregorianCalendar date = education.getExpirationDate();
		if (date == null) {
			date = education.getCredits().stream().map(CreditsType::getExpirationDate).filter(Objects::nonNull)
					.findFirst().orElse(null);
		}
		if (date == null) {
			return null;
		}
		String text = formatDate(date.toXMLFormat());
		try {
			LocalDate local = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
			if (local.isBefore(LocalDate.now())) {
				text += "  (expired)";
			}
		} catch (RuntimeException _) {
			// Incomplete date: show as is.
		}
		return text;
	}

	private String audience() {
		TargetAudienceType audience = education == null ? null : education.getTargetAudience();
		if (audience == null) {
			return null;
		}
		List<String> parts = new ArrayList<>();
		String professions = join(texts(audience.getProfession().stream()));
		String specialties = join(texts(audience.getSpecialty().stream()));
		if (professions != null) {
			parts.add(capitalize(professions));
		}
		if (specialties != null) {
			parts.add(capitalize(specialties));
		}
		if (parts.isEmpty()) {
			String categories = join(audience.getAudienceCategory().stream().map(Enumerator::getLiteral)
					.collect(Collectors.toCollection(LinkedHashSet::new)));
			if (categories != null) {
				parts.add(capitalize(categories));
			}
		}
		return parts.isEmpty() ? null : String.join("  ·  ", parts);
	}

	private String credits() {
		if (education == null) {
			return null;
		}
		if (education.isSetCreditsAvailable() && education.getCreditsAvailable() == YesNoType.NO) {
			return Messages.OverviewFacts_NoCreditsOffered;
		}
		List<String> lines = new ArrayList<>();
		for (CreditsType credits : education.getCredits()) {
			List<String> parts = new ArrayList<>();
			String amount = creditAmount(credits);
			if (amount != null) {
				parts.add(amount);
			}
			String body = blankToNull(credits.getAccreditingBody());
			String certification = blankToNull(credits.getActivityCertification());
			String origin = Stream.of(body, certification).filter(Objects::nonNull).collect(Collectors.joining(", "));
			if (!origin.isEmpty()) {
				parts.add(origin);
			}
			if (credits.getReleaseDate() != null || credits.getExpirationDate() != null) {
				parts.add((credits.getReleaseDate() == null ? "" : formatDate(credits.getReleaseDate().toXMLFormat()))
						+ " – " + (credits.getExpirationDate() == null ? ""
								: formatDate(credits.getExpirationDate().toXMLFormat())));
			}
			if (!parts.isEmpty()) {
				lines.add(String.join("  ·  ", parts));
			}
		}
		if (lines.isEmpty()) {
			return education.isSetCreditsAvailable() ? "Credits available" : null;
		}
		return String.join("\n", lines);
	}

	private static String creditAmount(CreditsType credits) {
		BigDecimal number = credits.getNumberOfCredits();
		String type = credits.isSetCreditType() ? credits.getCreditType().getLiteral() : null;
		String unit = credits.isSetCreditUnit() ? credits.getCreditUnit().getLiteral() : null;
		if (number == null && type == null) {
			return null;
		}
		return Stream.of(type, number == null ? null : number.stripTrailingZeros().toPlainString(), unit)
				.filter(Objects::nonNull).collect(Collectors.joining(" "));
	}

	private String location() {
		ActivityLocationType location = education == null ? null : education.getActivityLocation();
		if (location == null) {
			boolean online = education != null && education.getParticipationModality().stream()
					.anyMatch(m -> "technology based".equals(m.getLiteral()));
			return online ? "Online (technology based)" : null;
		}
		String country = location.getCountry() == null ? null
				: Stream.of(location.getCountry().getCountryName(), location.getCountry().getCountryCode())
						.map(OverviewFacts::blankToNull).filter(Objects::nonNull).findFirst().orElse(null);
		String place = Stream.of(blankToNull(location.getBuilding()), blankToNull(location.getCity()),
				blankToNull(location.getStateOrProvince()), country).filter(Objects::nonNull)
				.collect(Collectors.joining(", "));
		return place.isEmpty() ? null : place;
	}

	private String image() {
		if (asset == null) {
			return null;
		}
		List<String> parts = new ArrayList<>();
		BigInteger width = asset.getFileWidth();
		BigInteger height = asset.getFileHeight();
		if (width != null && height != null) {
			parts.add(width + " × " + height + " px");
		}
		if (asset.isSetMedicalImageType()) {
			parts.add(asset.getMedicalImageType().getLiteral());
		}
		if (asset.isSetOrientation()) {
			parts.add(asset.getOrientation().getLiteral());
		}
		if (asset.isSetSpecimenType()) {
			parts.add(asset.getSpecimenType().getLiteral());
		}
		if (asset.getMagnification() != null) {
			parts.add(asset.getMagnification() + "×");
		}
		if (asset.isSetAnnotated()) {
			parts.add(asset.getAnnotated() == YesNoType.YES ? "annotated" : "not annotated");
		}
		return parts.isEmpty() ? null : capitalize(String.join("  ·  ", parts));
	}

	private String format() {
		if (technical == null) {
			return null;
		}
		List<String> parts = new ArrayList<>(technical.getFormat().stream().map(f -> blankToNull(f.getValue()))
				.filter(Objects::nonNull).distinct().toList());
		technical.getSize().stream().map(s -> s.getValue()).filter(Objects::nonNull).findFirst()
				.ifPresent(size -> parts.add(bytes(size)));
		return parts.isEmpty() ? null : String.join("  ·  ", parts);
	}

	/** The first location (URL) of the resource, or {@code null}. */
	String resourceLocation() {
		return technical == null ? null
				: technical.getLocation().stream().map(l -> blankToNull(l.getValue())).filter(Objects::nonNull)
						.findFirst().orElse(null);
	}

	/** The first identifier entry, or {@code null}. */
	String identifier() {
		return general == null ? null
				: general.getIdentifier().stream().flatMap(i -> i.getEntry().stream())
						.map(e -> blankToNull(e.getValue())).filter(Objects::nonNull).findFirst().orElse(null);
	}

	private static String bytes(BigInteger size) {
		double value = size.doubleValue();
		if (value < 1024) {
			return size + " bytes";
		}
		if (value < 1024 * 1024) {
			return String.format(Locale.ROOT, "%.0f KB", value / 1024);
		}
		return String.format(Locale.ROOT, "%.1f MB", value / (1024 * 1024));
	}

	private Set<String> vocabularyValues(java.util.function.Function<Educational, Stream<? extends EObject>> values) {
		return lom.getEducational().stream().flatMap(values).map(Vocabularies::getValue).filter(Objects::nonNull)
				.map(String::strip).filter(v -> !v.isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private static Set<String> texts(Stream<LanguageString> strings) {
		return strings.map(OverviewFacts::text).filter(Objects::nonNull)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/** The first non-blank string of a language string, preferring English. */
	static String text(LanguageString string) {
		if (string == null) {
			return null;
		}
		String fallback = null;
		for (LangString value : string.getString()) {
			String text = blankToNull(value.getValue());
			if (text == null) {
				continue;
			}
			String language = value.getLanguage();
			if (language != null && language.toLowerCase(Locale.ROOT).startsWith("en")) {
				return collapse(text);
			}
			if (fallback == null) {
				fallback = text;
			}
		}
		return fallback == null ? null : collapse(fallback);
	}

	private static String collapse(String text) {
		return text.strip().replaceAll("\\s+", " ");
	}

	private static String join(Set<String> values) {
		return values.isEmpty() ? null : String.join(", ", values);
	}

	private static String formatDate(String date) {
		String day = date.length() >= 10 ? date.substring(0, 10) : date;
		try {
			return LocalDate.parse(day).format(DATE);
		} catch (DateTimeParseException _) {
			return date;
		}
	}

	static String capitalize(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value.strip();
	}

	private static <T> T first(List<T> list) {
		return list.isEmpty() ? null : list.getFirst();
	}
}
