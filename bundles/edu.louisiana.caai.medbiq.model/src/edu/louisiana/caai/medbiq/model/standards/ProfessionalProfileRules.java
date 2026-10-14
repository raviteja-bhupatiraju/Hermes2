/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.medbiq.model.Paths;

/**
 * The rules of Healthcare Professional Profile 1.0 beyond its schemas: content the specification requires but the
 * schema leaves optional, identifiers, ordered dates, the consistency of statuses and dates, and the formats the
 * specifications prescribe for countries (ISO 3166), telephone numbers (ITU E.164) and email addresses (RFC 2822).
 */
final class ProfessionalProfileRules {

	private static final Pattern NPI_DOMAIN = Pattern.compile("(?i).*\\b(NPI|National Provider Identifier)\\b.*");
	private static final Pattern PROGRAM_YEAR = Pattern.compile("year \\d");
	private static final Pattern PHONE_FORMATTING = Pattern.compile("[\\s().\\-/+]");
	private static final Pattern ANGLE_ADDRESS = Pattern.compile("<([^>]*)>");
	private static final Pattern US_ZIP = Pattern.compile("\\d{5}(-\\d{4})?");
	private static final Pattern CA_POSTAL = Pattern.compile("[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d");
	private static final Set<String> ISO_ALPHA2 = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);
	private static final Set<String> ISO_ALPHA3 = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3);
	/** USPS codes of the states, the District of Columbia, the territories and the armed forces. */
	private static final Set<String> US_STATE_CODES = Set.of("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
			"GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
			"NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
			"UT", "VT", "VA", "WA", "WV", "WI", "WY", "DC", "AS", "GU", "MP", "PR", "VI", "UM", "AA", "AE", "AP");
	private static final Set<String> US_STATE_NAMES = Set.of("alabama", "alaska", "arizona", "arkansas",
			"california", "colorado", "connecticut", "delaware", "florida", "georgia", "hawaii", "idaho", "illinois",
			"indiana", "iowa", "kansas", "kentucky", "louisiana", "maine", "maryland", "massachusetts", "michigan",
			"minnesota", "mississippi", "missouri", "montana", "nebraska", "nevada", "new hampshire", "new jersey",
			"new mexico", "new york", "north carolina", "north dakota", "ohio", "oklahoma", "oregon", "pennsylvania",
			"rhode island", "south carolina", "south dakota", "tennessee", "texas", "utah", "vermont", "virginia",
			"washington", "west virginia", "wisconsin", "wyoming", "district of columbia", "american samoa", "guam",
			"northern mariana islands", "puerto rico", "virgin islands", "u.s. virgin islands");
	private static final Set<String> CA_PROVINCE_CODES = Set.of("AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU",
			"ON", "PE", "QC", "SK", "YT");
	private static final Set<String> CA_PROVINCE_NAMES = Set.of("alberta", "british columbia", "manitoba",
			"new brunswick", "newfoundland and labrador", "nova scotia", "northwest territories", "nunavut",
			"ontario", "prince edward island", "quebec", "québec", "saskatchewan", "yukon");

	private final List<HlomProblem> problems = new ArrayList<>();
	private final LocalDate today = LocalDate.now();

	private ProfessionalProfileRules() {
	}

	/** The problems of a document (given its document root). */
	static List<HlomProblem> check(EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		if (root == null) {
			return List.of();
		}
		ProfessionalProfileRules rules = new ProfessionalProfileRules();
		rules.checkMembers(Paths.objects(root, "Member"));
		return rules.problems;
	}

	private void add(Severity severity, String message, EObject object) {
		problems.add(new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1));
	}

	// ----------------------------------------------------------------------------------------------------
	// Members, names and identifiers
	// ----------------------------------------------------------------------------------------------------

	private void checkMembers(List<EObject> members) {
		Map<String, EObject> ids = new HashMap<>();
		for (int i = 0; i < members.size(); i++) {
			EObject member = members.get(i);
			String name = ProfessionalProfile.displayName(member);
			String label = name != null ? name : "Member " + (i + 1);
			checkName(member, label);
			checkIdentifiers(member, label, ids);
			LocalDate asOf = date(member, "ModifiedDate");
			if (asOf != null && asOf.isAfter(today)) {
				add(Severity.WARNING, "The ModifiedDate " + asOf + " of " + label + " is in the future: it is “the date "
						+ "that this member record was last updated”.", member);
				asOf = null;
			}
			if (asOf == null) {
				asOf = today;
			}
			checkPersonal(member, label);
			checkEducation(member, label);
			checkCertification(member, asOf);
			checkLicensure(member, label, asOf);
			checkAppointments(member);
			checkOccupation(member);
			checkMemberships(member);
			checkContact(member);
		}
	}

	private void checkName(EObject member, String label) {
		List<EObject> names = Paths.objects(member, "Name");
		if (names.isEmpty()) {
			add(Severity.ERROR, label + " has no Name: the Professional Profile specification requires the Name of "
					+ "each Member. Add the member's name (at least the FamilyName).", member);
			return;
		}
		EObject name = names.getFirst();
		if (text(name, "FamilyName") == null && text(name, "FormattedName") == null) {
			add(Severity.WARNING, "The Name of " + label + " has neither a FamilyName nor a FormattedName. Give the "
					+ "family name (“If an individual has one name only, use FamilyName to indicate that name”).",
					name);
		}
	}

	private void checkIdentifiers(EObject member, String label, Map<String, EObject> documentIds) {
		List<EObject> ids = Paths.objects(member, "UniqueID");
		if (ids.isEmpty()) {
			add(Severity.WARNING, label + " has no UniqueID. Identify each member with “a unique alphanumeric "
					+ "identifier” and its domain (such as an AAMC id, a National Provider Identifier or your "
					+ "organization's id), so that partners can match the profile.", member);
		}
		Set<String> own = new HashSet<>();
		for (EObject id : ids) {
			String value = Paths.content(id) == null ? "" : Paths.content(id).strip();
			String domain = text(id, "@domain");
			if (domain == null) {
				add(Severity.WARNING, "The UniqueID " + value + " of " + label + " has an empty domain: “Use of the "
						+ "domain attribute is required when using the UniqueID element.” Name the community, society "
						+ "or organization that is the source of the id.", id);
				domain = "";
			}
			if (NPI_DOMAIN.matcher(domain).matches()) {
				checkNpi(id, value, label);
			}
			String key = domain.toLowerCase(Locale.ROOT) + '\u0000' + value;
			if (!own.add(key)) {
				add(Severity.WARNING, "The UniqueID " + value + " (" + domain + ") of " + label + " is repeated. "
						+ "Remove the duplicate.", id);
			} else {
				EObject other = documentIds.putIfAbsent(key, member);
				if (other != null && other != member) {
					add(Severity.ERROR, "The UniqueID " + value + " (" + domain + ") of " + label + " also identifies "
							+ "another Member of the document: a UniqueID identifies a single member. Correct the id "
							+ "or merge the two members.", id);
				}
			}
		}
	}

	/**
	 * National Provider Identifiers have 10 digits, the last a Luhn check digit computed with the prefix 80840
	 * (CMS, NPI check digit; the Professional Profile Implementation Guidelines 3.1).
	 */
	private void checkNpi(EObject id, String value, String label) {
		if (!value.matches("\\d{10}")) {
			add(Severity.WARNING, "The National Provider Identifier " + value + " of " + label + " is not a "
					+ "“10-position, intelligence-free numeric identifier (10-digit number)”.", id);
		} else if (!luhn("80840" + value)) {
			add(Severity.WARNING, "The National Provider Identifier " + value + " of " + label + " has a wrong "
					+ "check digit. Check the number.", id);
		}
	}

	static boolean luhn(String digits) {
		int sum = 0;
		boolean twice = false;
		for (int i = digits.length() - 1; i >= 0; i--) {
			int digit = digits.charAt(i) - '0';
			if (twice) {
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
			twice = !twice;
		}
		return sum % 10 == 0;
	}

	// ----------------------------------------------------------------------------------------------------
	// Personal information
	// ----------------------------------------------------------------------------------------------------

	private void checkPersonal(EObject member, String label) {
		for (EObject personal : Paths.objects(member, "PersonalInfo")) {
			LocalDate birth = date(personal, "BirthDate");
			LocalDate deceased = date(personal, "DeceasedDate");
			String status = text(personal, "PhysicalStatus");
			EObject birthDate = firstObject(personal, "BirthDate");
			if (birth != null && birth.isAfter(today)) {
				add(Severity.ERROR, "The BirthDate " + birth + " of " + label + " is in the future.", birthDate);
			}
			if (birth != null && deceased != null && deceased.isBefore(birth)) {
				add(Severity.ERROR, "The DeceasedDate " + deceased + " of " + label + " is before the BirthDate "
						+ birth + ".", firstObject(personal, "DeceasedDate"));
			}
			if (deceased != null && "Living".equals(status)) {
				add(Severity.ERROR, "The PhysicalStatus of " + label + " is Living, but there is a DeceasedDate. Set "
						+ "the PhysicalStatus to Deceased or remove the date.", personal);
			}
			if ("Deceased".equals(status) && deceased == null) {
				add(Severity.INFO, "The PhysicalStatus of " + label + " is Deceased: add the DeceasedDate if it is "
						+ "known.", personal);
			}
			if ("Living".equals(status)) {
				for (EObject notice : Paths.objects(member, "DisciplinaryInfo/BoardOrder/NoticeOrAction/BoardNotice")) {
					if (text(notice, "DeathDate") != null) {
						add(Severity.WARNING, "A board notice of " + label + " gives a DeathDate, but the "
								+ "PhysicalStatus is Living. Correct the status or the notice.", notice);
					}
				}
			}
			if (birth != null) {
				checkAfterBirth(member, birth, label);
			}
			for (EObject tax : Paths.objects(personal, "TaxNumber")) {
				String restrictions = text(tax, "@restrictions");
				if (!"Confidential".equals(restrictions) && !"Restricted".equals(restrictions)) {
					add(Severity.INFO, "The TaxNumber of " + label + " is not marked Confidential or Restricted. A "
							+ "tax number such as a social security number is usually confidential (“may not be "
							+ "shared or published”); set its restrictions.", tax);
				}
			}
			for (String url : Paths.texts(personal, "URL")) {
				checkUrl(personal, url, label);
			}
		}
	}

	private void checkAfterBirth(EObject member, LocalDate birth, String label) {
		record Dated(String path, String date, String what) {
		}
		List<Dated> dated = List.of(new Dated("EducationInfo", "StartDate", "education"),
				new Dated("TrainingInfo", "StartDate", "training"),
				new Dated("LicenseInfo/License", "InitialLicensureDate", "license"),
				new Dated("OccupationInfo", "StartDate", "occupation"));
		for (Dated entry : dated) {
			for (EObject object : Paths.objects(member, entry.path())) {
				LocalDate date = date(object, entry.date());
				if (date != null && date.isBefore(birth)) {
					add(Severity.WARNING, "The " + entry.date() + " " + date + " of a " + entry.what() + " entry of "
							+ label + " is before the BirthDate " + birth + ".", object);
				}
			}
		}
	}

	private void checkUrl(EObject owner, String url, String label) {
		try {
			URI uri = new URI(url);
			if (!uri.isAbsolute() || uri.getHost() == null && !"mailto".equalsIgnoreCase(uri.getScheme())) {
				add(Severity.WARNING, "The URL " + url + " of " + label + " is not an absolute web address. Write "
						+ "the full address with its scheme, for example https://www.example.org/.", owner);
			}
		} catch (URISyntaxException e) {
			add(Severity.WARNING, "The URL " + url + " of " + label + " is not a valid address: " + e.getReason()
					+ ".", owner);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Education, training, certification, licensure, appointments, occupation, memberships
	// ----------------------------------------------------------------------------------------------------

	private void checkEducation(EObject member, String label) {
		for (EObject education : Paths.objects(member, "EducationInfo")) {
			String what = "The education" + describe(education, "Degree", "EducationalCertificate",
					"InstitutionInfo/InstitutionName");
			order(education, "StartDate", "EndDate", what);
			order(education, "StartDate", "GraduationDate", what);
			if (text(education, "Degree") == null && text(education, "EducationalCertificate") == null
					&& text(education, "InstitutionInfo/InstitutionName") == null) {
				add(Severity.INFO, "An EducationInfo of " + label + " names neither a Degree, a certificate nor the "
						+ "institution. Say what the education was.", education);
			}
			if ("Deceased".equals(text(education, "EducationStatus"))) {
				checkDeceasedStatus(member, education, "EducationStatus", label);
			}
		}
		for (EObject training : Paths.objects(member, "TrainingInfo")) {
			String what = "The training" + describe(training, "ProgramInfo/ProgramName", "ProgramInfo/Specialty");
			order(training, "StartDate", "EndDate", what);
			order(training, "StartDate", "CompletionDate", what);
			String status = text(training, "TrainingStatus");
			if (status != null && PROGRAM_YEAR.matcher(status).matches()
					&& text(training, "CompletionDate") != null) {
				add(Severity.WARNING, what + " has the TrainingStatus “" + status + "” but a CompletionDate. For a "
						+ "completed program use Completed Specialty Training or Completed Preliminary Training.",
						training);
			}
			if ("Deceased".equals(status)) {
				checkDeceasedStatus(member, training, "TrainingStatus", label);
			}
		}
	}

	private void checkDeceasedStatus(EObject member, EObject entry, String element, String label) {
		if ("Living".equals(text(member, "PersonalInfo/PhysicalStatus"))) {
			add(Severity.WARNING, "The " + element + " of " + label + " is Deceased, but the PhysicalStatus is "
					+ "Living. Correct one of them.", entry);
		}
	}

	private void checkCertification(EObject member, LocalDate asOf) {
		for (EObject certification : Paths.objects(member, "CertificationInfo")) {
			if (text(certification, "CertificationBoard") == null
					&& text(certification, "CertificationOrganization") == null
					&& Paths.objects(certification, "CertificateInfo").isEmpty()) {
				add(Severity.WARNING, "A CertificationInfo names neither the CertificationBoard nor a certificate. "
						+ "Give “the board certifying the medical professional”.", certification);
			}
			for (EObject certificate : Paths.objects(certification, "CertificateInfo")) {
				String name = describe(certificate, "CertificateName");
				for (EObject issuance : Paths.objects(certificate, "CertificateIssuance")) {
					String what = "The issuance" + describe(issuance, "CertificationID") + " of the certificate"
							+ name;
					order(issuance, "CertificationIssueDate", "CertificationExpireDate", what);
					LocalDate expires = date(issuance, "CertificationExpireDate");
					if ("Lifetime".equals(text(issuance, "CertificationDuration")) && expires != null) {
						add(Severity.WARNING, what + " has a Lifetime duration but an expiration date. Use "
								+ "Time-limited or remove the CertificationExpireDate.", issuance);
					}
					if ("Active".equals(text(issuance, "CertificationStatus")) && expires != null
							&& expires.isBefore(asOf)) {
						add(Severity.WARNING, what + " is Active but expired on " + expires + ". Update the "
								+ "CertificationStatus (for example Expired) or the dates.", issuance);
					}
				}
			}
		}
	}

	private void checkLicensure(EObject member, String label, LocalDate asOf) {
		Set<String> licenses = new HashSet<>();
		for (EObject license : Paths.objects(member, "LicenseInfo/License")) {
			String entity = text(license, "LicensureEntity");
			String number = text(license, "LicenseNumber");
			String what = "The license " + (number == null ? "" : number + " ") + "of " + label
					+ (entity == null ? "" : " (" + entity + ")");
			order(license, "InitialLicensureDate", "LicenseExpirationDate", what);
			LocalDate expires = date(license, "LicenseExpirationDate");
			if ("active".equalsIgnoreCase(text(license, "LicenseStatus")) && expires != null
					&& expires.isBefore(asOf)) {
				add(Severity.WARNING, what + " is Active but expired on " + expires + ". Update the LicenseStatus or "
						+ "the LicenseExpirationDate.", license);
			}
			if (entity != null && number != null && !licenses.add(entity.toLowerCase(Locale.ROOT) + '\u0000' + number)) {
				add(Severity.WARNING, what + " is listed twice. Remove the duplicate License.", license);
			}
		}
		for (EObject order : Paths.objects(member, "DisciplinaryInfo/BoardOrder")) {
			for (EObject span : Paths.objects(order, "NoticeOrAction/BoardAction/ActionSpan")) {
				order(span, "ActionEffectiveDate", "ActionExpirationDate", "The board action");
				order(span, "TermFromDate", "TermToDate", "The term of the board action");
				checkTerm(span);
			}
			for (EObject appeal : Paths.objects(order, "NoticeOrAction/BoardAction/BoardOrderAppeal")) {
				LocalDate notice = date(appeal, "NoticeOfAppealDate");
				LocalDate judgement = date(appeal, "Judgement/JudgementDate");
				if (notice != null && judgement != null && judgement.isBefore(notice)) {
					add(Severity.ERROR, "The appeal judgement (" + judgement + ") is dated before the notice of "
							+ "appeal (" + notice + "). Check the dates.", appeal);
				}
			}
		}
	}

	/** The term duration of a board action must agree with its from and to dates (within a month). */
	private void checkTerm(EObject span) {
		String text = text(span, "TermDuration");
		LocalDate from = date(span, "TermFromDate");
		LocalDate to = date(span, "TermToDate");
		if (text == null || from == null || to == null) {
			return;
		}
		try {
			Duration duration = DatatypeFactory.newInstance().newDuration(text);
			if (duration.getSign() < 0) {
				return;
			}
			LocalDate end = from.plusYears(duration.getYears()).plusMonths(duration.getMonths())
					.plusDays(duration.getDays());
			if (Math.abs(ChronoUnit.DAYS.between(end, to)) > 31) {
				add(Severity.WARNING, "The TermDuration " + text + " of the board action does not agree with its "
						+ "term from " + from + " to " + to + ". Check the duration or the dates.", span);
			}
		} catch (DatatypeConfigurationException | IllegalArgumentException | UnsupportedOperationException e) {
			// Invalid durations are reported by the schema validation.
		}
	}

	private void checkAppointments(EObject member) {
		for (String kind : List.of("FacultyAppointment", "ChairAppointment", "DivisionOrSectionAppointment",
				"AdministrativeAppointment")) {
			for (EObject appointment : Paths.objects(member, "AcademicAppointmentInfo/" + kind)) {
				String what = "The " + words(kind) + describe(appointment, "Title");
				order(appointment, "EffectiveDate", "EndDate", what);
				if (kind.equals("FacultyAppointment")) {
					String endowed = text(appointment, "Endowed");
					String title = text(appointment, "EndowedTitle");
					if ("No".equals(endowed) && title != null) {
						add(Severity.WARNING, what + " has an EndowedTitle but is not Endowed. Set Endowed to Yes or "
								+ "remove the title.", appointment);
					} else if ("Yes".equals(endowed) && title == null) {
						add(Severity.INFO, what + " is endowed: give the EndowedTitle (for example William Stewart "
								+ "Halsted Professor of Surgery).", appointment);
					}
					currentFirst(appointment, "Rank", what);
					currentFirst(appointment, "TenureStatus", what);
				}
			}
		}
	}

	/** Repeated ranks and tenure statuses list the current one first (the latest effective date). */
	private void currentFirst(EObject appointment, String element, String what) {
		List<EObject> values = Paths.objects(appointment, element);
		if (values.size() < 2) {
			return;
		}
		LocalDate first = parse(text(values.getFirst(), "@effectiveDate"));
		for (EObject value : values.subList(1, values.size())) {
			LocalDate date = parse(text(value, "@effectiveDate"));
			if (first != null && date != null && date.isAfter(first)) {
				add(Severity.WARNING, what + " lists the " + element + " " + Paths.content(value) + " (effective "
						+ date + ") after an earlier one: “If more than one " + words(element) + " is specified, list the current "
						+ words(element) + " first.”", value);
				return;
			}
		}
	}

	private void checkOccupation(EObject member) {
		for (EObject occupation : Paths.objects(member, "OccupationInfo")) {
			order(occupation, "StartDate", "EndDate", "The occupation" + describe(occupation, "Occupation",
					"OccupationTitle", "Practice", "InstitutionInfo/InstitutionName"));
		}
	}

	private void checkMemberships(EObject member) {
		for (EObject membership : Paths.objects(member, "MembershipInfo")) {
			String organization = text(membership, "MembershipOrganization");
			if (organization == null && text(membership, "Membership") == null) {
				add(Severity.WARNING, "A MembershipInfo names no organization. Give the MembershipOrganization (“the "
						+ "name of the organization to which the user belongs”) or the Membership.", membership);
			}
			String what = "The membership" + (organization == null ? "" : " in " + organization);
			order(membership, "MemberSinceDate", "FellowshipDate", what);
			for (EObject component : Paths.objects(membership, "ComponentGroup")) {
				order(component, "MemberSinceDate", "MemberUntilDate", "The membership in the component"
						+ describe(component, "ComponentGroupName"));
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Addresses, countries, telephone numbers and email addresses (anywhere in a member)
	// ----------------------------------------------------------------------------------------------------

	private void checkContact(EObject member) {
		var contents = member.eAllContents();
		while (contents.hasNext()) {
			EObject object = contents.next();
			String type = ExtendedMetaData.INSTANCE.getName(object.eClass());
			if (has(object, "CountryName") && has(object, "CountryCode")) {
				checkCountry(object);
			}
			if (has(object, "StreetAddressLine") && has(object, "StateOrProvince")) {
				checkAddress(object);
			} else if ("ContactNumberType".equals(type)) {
				checkPhone(object);
			} else if ("EmailAddressType".equals(type)) {
				checkEmail(object);
			}
		}
	}

	private static boolean has(EObject object, String name) {
		return Paths.feature(object, name) != null;
	}

	private void checkCountry(EObject country) {
		String name = text(country, "CountryName");
		String code = text(country, "CountryCode");
		String element = ExtendedMetaData.INSTANCE.getName(country.eContainingFeature());
		if (name == null && code == null) {
			add(Severity.WARNING, "The " + element + " has neither a CountryName nor a CountryCode. Give the ISO 3166 "
					+ "code (such as US or CH) or the name of the country.", country);
		} else if (code != null && !isIsoCountry(code)) {
			String upper = code.toUpperCase(Locale.ROOT);
			add(Severity.WARNING, isIsoCountry(upper)
					? "The CountryCode " + code + " should be written in capitals: " + upper + " (ISO 3166)."
					: "The CountryCode " + code + " is not an ISO 3166 alpha code. Use the two-letter code of the "
							+ "country (for example US, CA, GB or CH).", country);
		}
	}

	private static boolean isIsoCountry(String code) {
		return code.length() == 2 && ISO_ALPHA2.contains(code) || code.length() == 3 && ISO_ALPHA3.contains(code);
	}

	private void checkAddress(EObject address) {
		String country = country(address);
		String state = text(address, "StateOrProvince");
		String postal = text(address, "PostalCode");
		if ("US".equals(country)) {
			if (state != null && !US_STATE_CODES.contains(state.toUpperCase(Locale.ROOT))
					&& !US_STATE_NAMES.contains(state.toLowerCase(Locale.ROOT))) {
				add(Severity.WARNING, "The StateOrProvince " + state + " is not a state, district or territory of "
						+ "the United States. Use the postal abbreviation (such as MD) or the name of the state.",
						address);
			}
			if (postal != null && !US_ZIP.matcher(postal).matches()) {
				add(Severity.WARNING, "The PostalCode " + postal + " is not a United States ZIP code (5 digits, "
						+ "optionally with 4 more: 21287 or 21287-0005).", address);
			}
		} else if ("CA".equals(country)) {
			if (state != null && !CA_PROVINCE_CODES.contains(state.toUpperCase(Locale.ROOT))
					&& !CA_PROVINCE_NAMES.contains(state.toLowerCase(Locale.ROOT))) {
				add(Severity.WARNING, "The StateOrProvince " + state + " is not a province or territory of Canada. "
						+ "Use the postal abbreviation (such as BC) or the name of the province.", address);
			}
			if (postal != null && !CA_POSTAL.matcher(postal).matches()) {
				add(Severity.WARNING, "The PostalCode " + postal + " is not a Canadian postal code (such as "
						+ "K1A 0B1).", address);
			}
		}
	}

	/** US or CA when an address is in the United States or Canada (by code or name), else {@code null}. */
	private static String country(EObject address) {
		String code = text(address, "Country/CountryCode");
		String name = text(address, "Country/CountryName");
		String value = (code != null ? code : name == null ? "" : name).toUpperCase(Locale.ROOT).replace(".", "");
		return switch (value) {
			case "US", "USA", "UNITED STATES", "UNITED STATES OF AMERICA" -> "US";
			case "CA", "CAN", "CANADA" -> "CA";
			default -> null;
		};
	}

	private void checkPhone(EObject phone) {
		String number = text(phone, "TelephoneNumber");
		Object code = Paths.values(phone, "CountryCode").stream().findFirst().orElse(null);
		BigInteger countryCode = code instanceof BigInteger integer ? integer
				: code == null ? null : parseInteger(code.toString());
		if (countryCode != null && (countryCode.signum() <= 0 || countryCode.compareTo(BigInteger.valueOf(999)) > 0)) {
			add(Severity.WARNING, "The telephone CountryCode " + code + " is not a country calling code: “the numeric "
					+ "telephone code used to reach telephone numbers within a particular country” has 1 to 3 digits "
					+ "(ITU E.164), for example 1 or 44.", phone);
			countryCode = null;
		}
		if (number == null) {
			return;
		}
		String digits = PHONE_FORMATTING.matcher(number).replaceAll("");
		if (!digits.matches("\\d+")) {
			add(Severity.WARNING, "The TelephoneNumber " + number + " contains characters other than digits. Give "
					+ "“a telephone, fax, or pager number, including area or city code” and the Extension separately.",
					phone);
			return;
		}
		if (!digits.equals(number)) {
			add(Severity.INFO, "The TelephoneNumber " + number + " is formatted: the specification defines it as an "
					+ "integer (digits only, such as 4105551212).", phone);
		}
		int length = digits.length() + (countryCode == null ? 0 : countryCode.toString().length());
		if (digits.length() > 14 || length > 15) {
			add(Severity.WARNING, "The TelephoneNumber " + number + " is too long: it has up to 14 digits, and at most "
					+ "15 with the country code (ITU E.164).", phone);
		} else if (BigInteger.ONE.equals(countryCode) && digits.length() != 10) {
			add(Severity.WARNING, "The TelephoneNumber " + number + " has country code 1 (North America) but not 10 "
					+ "digits (area code and number).", phone);
		}
	}

	private static BigInteger parseInteger(String text) {
		try {
			return new BigInteger(text.strip());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** RFC 2822 addresses: the schema checks the pattern, but not the dots of the local part and domain. */
	private void checkEmail(EObject email) {
		String text = Paths.content(email);
		if (text == null) {
			return;
		}
		Matcher angle = ANGLE_ADDRESS.matcher(text);
		String address = (angle.find() ? angle.group(1) : text).strip();
		int at = address.lastIndexOf('@');
		if (at < 0) {
			return;
		}
		for (String part : List.of(address.substring(0, at), address.substring(at + 1))) {
			if (!part.startsWith("\"") && (part.startsWith(".") || part.endsWith(".") || part.contains(".."))) {
				add(Severity.WARNING, "The email address " + address + " is not valid: in RFC 2822 a dot may not "
						+ "begin or end the name or the domain, nor follow another dot.", email);
				return;
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Dates
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Reports an end date before a start date. Dates of a lower accuracy ({@code MonthYear}, {@code Year}) are
	 * compared at that accuracy.
	 */
	private void order(EObject owner, String start, String end, String what) {
		LocalDate from = date(owner, start);
		LocalDate to = date(owner, end);
		if (from == null || to == null) {
			return;
		}
		String accuracy = lowest(text(owner, start + "/@accuracy"), text(owner, end + "/@accuracy"));
		boolean wrong = switch (accuracy) {
			case "Year" -> to.getYear() < from.getYear();
			case "MonthYear" -> YearMonth.from(to).isBefore(YearMonth.from(from));
			default -> to.isBefore(from);
		};
		if (wrong) {
			EObject at = firstObject(owner, end);
			add(Severity.ERROR, what + " has the " + end + " " + to + " before the " + start + " " + from
					+ ". Correct the dates.", at != null ? at : owner);
		}
	}

	private static String lowest(String a, String b) {
		if ("Year".equals(a) || "Year".equals(b)) {
			return "Year";
		}
		return "MonthYear".equals(a) || "MonthYear".equals(b) ? "MonthYear" : "DayMonthYear";
	}

	private static LocalDate date(EObject owner, String path) {
		return parse(text(owner, path));
	}

	private static LocalDate parse(String text) {
		if (text == null || text.length() < 10) {
			return null;
		}
		try {
			return LocalDate.parse(text.substring(0, 10));
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Labels
	// ----------------------------------------------------------------------------------------------------

	/**
	 * The first text at a path, with enumeration values as their literals ({@link Paths#text} reads the values of
	 * dynamic enumerations as model objects).
	 */
	private static String text(EObject owner, String path) {
		for (Object value : Paths.values(owner, path)) {
			if (value instanceof Enumerator enumerator) {
				return enumerator.getLiteral();
			}
		}
		return Paths.text(owner, path);
	}

	private static EObject firstObject(EObject owner, String path) {
		List<EObject> objects = Paths.objects(owner, path);
		return objects.isEmpty() ? null : objects.getFirst();
	}

	/** The first text of the paths, quoted after a space, or an empty string. */
	private static String describe(EObject object, String... paths) {
		for (String path : paths) {
			String text = text(object, path);
			if (text != null) {
				return " “" + text + "”";
			}
		}
		return "";
	}

	/** An element name in words: {@code FacultyAppointment} becomes {@code faculty appointment}. */
	private static String words(String name) {
		return name.replaceAll("(?<=[a-z])(?=[A-Z])", " ").toLowerCase(Locale.ROOT);
	}
}
