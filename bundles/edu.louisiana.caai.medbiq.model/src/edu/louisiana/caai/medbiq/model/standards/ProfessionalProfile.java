/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import static edu.louisiana.caai.medbiq.model.forms.PageSpec.field;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.group;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.page;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.repeat;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.section;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.suggest;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Group;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Repeat;

/**
 * Healthcare Professional Profile 1.0 (ANSI/MEDBIQ PP.10.1-2008, with the MedBiquitous Name 1.0 and Address 1.0
 * schemas): a {@code Members} document describes one or more healthcare professionals — identifiers, name,
 * addresses, education, postgraduate training, specialty certification, licensure, disciplinary actions, academic
 * appointments, occupation, personal information and memberships. The rules are in
 * {@link ProfessionalProfileRules}.
 */
public final class ProfessionalProfile implements StandardContent {

	@Override
	public String title(MedbiqDocumentType type, EObject rootElement) {
		List<EObject> members = Paths.objects(rootElement, "Member");
		if (members.size() == 1) {
			EObject member = members.getFirst();
			String name = displayName(member);
			if (name != null) {
				return name;
			}
			String id = Paths.text(member, "UniqueID");
			if (id != null) {
				return "Professional profile " + id;
			}
		}
		return members.size() + (members.size() == 1 ? " professional profile" : " professional profiles");
	}

	@Override
	public String description(MedbiqDocumentType type, EObject rootElement) {
		List<EObject> members = Paths.objects(rootElement, "Member");
		if (members.isEmpty()) {
			return null;
		}
		if (members.size() == 1) {
			EObject member = members.getFirst();
			// The current occupation (the first without an end date), its specialties and practice or institution.
			List<EObject> occupations = Paths.objects(member, "OccupationInfo");
			EObject occupation = occupations.stream().filter(o -> Paths.text(o, "EndDate") == null).findFirst()
					.orElse(occupations.isEmpty() ? null : occupations.getFirst());
			Set<String> parts = new LinkedHashSet<>();
			if (occupation != null) {
				first(occupation, "Occupation", "OccupationTitle").ifPresent(parts::add);
				parts.addAll(Paths.texts(occupation, "Specialty"));
				first(occupation, "Practice", "InstitutionInfo/InstitutionName").ifPresent(parts::add);
			} else {
				parts.addAll(Paths.texts(member, "TrainingInfo/ProgramInfo/Specialty"));
			}
			return parts.isEmpty() ? null : String.join(" · ", parts);
		}
		List<String> names = members.stream().map(ProfessionalProfile::displayName)
				.filter(name -> name != null).toList();
		if (names.isEmpty()) {
			return null;
		}
		String shown = String.join(", ", names.subList(0, Math.min(3, names.size())));
		return "Professional profiles of " + shown + (members.size() > 3 ? " and " + (members.size() - 3)
				+ " more" : "");
	}

	/**
	 * The name of a member as it is written: the {@code FormattedName}, else the given and family names with the
	 * generation identifier and degrees; {@code null} if the member has no name.
	 */
	public static String displayName(EObject member) {
		List<EObject> names = Paths.objects(member, "Name");
		if (names.isEmpty()) {
			return null;
		}
		EObject name = names.getFirst();
		String formatted = Paths.text(name, "FormattedName");
		if (formatted != null) {
			return formatted;
		}
		List<String> parts = new ArrayList<>(Paths.texts(name, "GivenName"));
		parts.addAll(Paths.texts(name, "FamilyName"));
		parts.addAll(Paths.texts(name, "GenerationIdentifier"));
		if (parts.isEmpty()) {
			return null;
		}
		List<String> degrees = Paths.texts(name, "Degree");
		return String.join(" ", parts) + (degrees.isEmpty() ? "" : ", " + String.join(", ", degrees));
	}

	private static java.util.Optional<String> first(EObject object, String... paths) {
		return Stream.of(paths).map(path -> Paths.text(object, path)).filter(text -> text != null).findFirst();
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	private static final String PROFILE = "Profile";
	private static final String QUALIFICATIONS = "Qualifications";
	private static final String CAREER = "Career";

	/** The fields of a MedBiquitous Address (Address 1.0). */
	private static final FieldSpec[] ADDRESS = {
			field("@addressCategory", "Category"),
			field("@restrictions", "Restrictions"),
			field("Organization", "Organizations"),
			field("StreetAddressLine", "Street address lines"),
			field("City"),
			field("StateOrProvince", "State or province"),
			field("PostalCode", "Postal code"),
			field("Region"),
			field("District"),
			field("Country/CountryName", "Country"),
			field("Country/CountryCode", "Country code (ISO 3166)") };

	private static Group institution(String label) {
		return group("InstitutionInfo", label,
				field("InstitutionName", "Name"),
				field("InstitutionID", "Identifier"),
				field("InstitutionID/@domain", "Identifier domain"),
				group("Address", "Address", ADDRESS));
	}

	private static FieldSpec[] contactNumber() {
		return new FieldSpec[] {
				suggest("Description", "Kind", "Office", "Home", "Mobile", "Fax", "Pager"),
				field("CountryCode", "Country calling code"),
				field("TelephoneNumber", "Number"),
				field("Extension"),
				field("@restrictions", "Restrictions") };
	}

	/** The fields every kind of academic appointment has (AppointmentCoreCharacteristicsType). */
	private static FieldSpec[] appointment(FieldSpec... specific) {
		List<FieldSpec> fields = new ArrayList<>(List.of(specific));
		fields.addAll(0, List.of(field("Title")));
		fields.addAll(List.of(field("AdministrativeUnit", "Administrative unit"),
				field("EffectiveDate", "Effective date"), field("EndDate", "End date"),
				institution("Institution")));
		return fields.toArray(FieldSpec[]::new);
	}

	/** The member list of a page: every page edits the members of the document, each with its own details. */
	private static Repeat members(FieldSpec... details) {
		return repeat("Member", "Members", "Name/FamilyName", details);
	}

	private static final Group NAME = group("Name", "Name",
			field("FormattedName", "Formatted name"),
			suggest("Title", "Title", "Dr.", "Prof.", "Mr.", "Ms.", "Mrs."),
			field("GivenName", "Given names"),
			field("FamilyName", "Family names"),
			field("GenerationIdentifier", "Generation identifier"),
			field("Degree", "Degrees"),
			repeat("Alias", "Aliases", "FamilyName"),
			repeat("FormerName", "Former names", "FamilyName"));

	private static final List<PageSpec> PAGES = List.of(
			page("members", "Members", PROFILE,
					"The professionals of the document: identifiers and names", "general",
					section("Members", "Each Member describes one healthcare professional. Identify the member with a "
							+ "UniqueID of a domain (such as AAMC or NPI) and give the name.", "",
							members(field("UniqueID", "Unique IDs"),
									repeat("UniqueID", "Domains of the IDs", null, field("@domain", "Domain")),
									NAME,
									field("@restrictions", "Restrictions"),
									field("ModifiedDate", "Modified date")))),
			page("contact", "Contact", PROFILE, "Addresses, telephone numbers, email and web pages", "annotation",
					section("Contact information", "Postal addresses follow the MedBiquitous Address schema; "
							+ "telephone numbers, email and web pages are part of the personal information.", "",
							members(repeat("Address", "Addresses", "City", ADDRESS),
									repeat("PersonalInfo/ContactNumber", "Telephone numbers", "TelephoneNumber",
											contactNumber()),
									field("PersonalInfo/EmailAddress", "Email addresses"),
									field("PersonalInfo/URL", "Web pages"),
									suggest("PersonalInfo/PreferredMethodOfContact", "Preferred method of contact",
											"email", "telephone", "cellphone", "fax", "pager", "postal")))),
			page("personal", "Personal Information", PROFILE,
					"Birth date, languages, citizenship, gender and other personal data", "metametadata",
					section("Personal information", "Personal data is often restricted: set the restrictions of "
							+ "the information that may not be shared or published.", "",
							members(group("PersonalInfo", "Personal information",
									field("@restrictions", "Restrictions"),
									field("PhysicalStatus", "Physical status"),
									field("BirthDate", "Birth date"),
									field("BirthDate/@accuracy", "Birth date accuracy"),
									field("BirthDate/@restrictions", "Birth date restrictions"),
									field("DeceasedDate", "Deceased date"),
									field("Gender"),
									field("Language", "Languages"),
									repeat("Citizenship", "Citizenship", "CountryCode",
											field("CountryName", "Country"),
											field("CountryCode", "Country code (ISO 3166)"),
											suggest("@pointInTime", "Point in time", "current", "birth",
													"entry to medical school")),
									repeat("CountryOfResidence", "Countries of residence", "CountryCode",
											field("CountryName", "Country"),
											field("CountryCode", "Country code (ISO 3166)")),
									field("Race"),
									field("Ethnicity"),
									field("TaxNumber", "Tax number"),
									suggest("TaxNumber/@type", "Tax number type", "Social Security Number",
											"Social Insurance Number"),
									field("TaxNumber/@restrictions", "Tax number restrictions"),
									field("FamilyMember", "Family members"),
									repeat("FamilyMember", "Relationships of the family members", null,
											suggest("@relationship", "Relationship", "spouse", "husband", "wife",
													"partner", "parent", "child", "sibling")))))),
			page("education", "Education and Training", QUALIFICATIONS,
					"Degrees and certificates, residencies and fellowships", "educational",
					section("Education and postgraduate training", "EducationInfo describes degrees and "
							+ "certificates (such as the bachelor's and medical degree), TrainingInfo postgraduate "
							+ "training such as residency and fellowship.", "",
							members(repeat("EducationInfo", "Education", "Degree",
									field("Degree"),
									field("Degree/@discipline", "Discipline"),
									field("EducationalCertificate", "Certificate"),
									field("EducationalCertificate/@type", "Certificate type"),
									institution("Institution"),
									field("Distinction"),
									field("EducationStatus", "Status"),
									field("StartDate", "Start date"),
									field("StartDate/@accuracy", "Start date accuracy"),
									field("EndDate", "End date"),
									field("EndDate/@accuracy", "End date accuracy"),
									field("GraduationDate", "Graduation date"),
									field("GraduationDate/@accuracy", "Graduation date accuracy")),
									repeat("TrainingInfo", "Postgraduate training", "ProgramInfo/ProgramName",
											group("ProgramInfo", "Program",
													field("ProgramName", "Name"),
													field("ProgramName/@type", "Type"),
													field("ProgramID", "Identifier"),
													field("ProgramID/@domain", "Identifier domain"),
													suggest("Accreditation", "Accreditation", "ACGME", "AOA",
															"RCPSC", "CFPC", "AANP"),
													field("Specialty"),
													institution("Sponsoring institution")),
											field("Distinction"),
											suggest("TrainingStatus", "Training status", "year 1", "year 2",
													"year 3", "year 4", "year 5", "year 6", "year 7",
													"Completed Preliminary Training", "Completed Specialty Training",
													"Leave of Absence", "Withdrawn", "Dismissed", "Deceased"),
											field("StartDate", "Start date"),
											field("StartDate/@accuracy", "Start date accuracy"),
											field("EndDate", "End date"),
											field("EndDate/@accuracy", "End date accuracy"),
											field("CompletionDate", "Completion date"),
											field("CompletionDate/@accuracy", "Completion date accuracy"))))),
			page("certification", "Certification", QUALIFICATIONS,
					"Specialty board certificates and their issuances", "healthcare-education",
					section("Specialty certification", "A CertificationInfo describes the certification by one "
							+ "specialty board; each certificate (such as Internal Medicine or Geriatric Medicine) "
							+ "lists its issuances: the initial certificate, recertifications and maintenance of "
							+ "certification.", "",
							members(repeat("CertificationInfo", "Certifications", "CertificationBoard",
									suggest("CertificationOrganization", "Certification organization",
											"American Board of Medical Specialties",
											"American Osteopathic Association"),
									field("CertificationBoard", "Certification board"),
									field("CertificateStatus", "Certificate status"),
									repeat("CertificateInfo", "Certificates", "CertificateName",
											field("CertificateName", "Name"),
											field("CertificateName/@certificateID", "Certificate ID"),
											field("CertificateType", "Type"),
											repeat("CertificateIssuance", "Issuances", "CertificationIssueDate",
													field("CertificationID", "Certification ID"),
													field("CertificationOccurrence", "Occurrence"),
													field("CertificationDuration", "Duration"),
													field("CertificationIssueDate", "Issue date"),
													field("CertificationIssueDate/@accuracy", "Issue date accuracy"),
													field("CertificationExpireDate", "Expiration date"),
													field("CertificationExpireDate/@accuracy",
															"Expiration date accuracy"),
													field("CertificationStatus", "Status"))))))),
			page("licensure", "Licensure and Discipline", QUALIFICATIONS,
					"Professional licenses and licensing board orders", "rights",
					section("Licenses and board orders", "Licenses are issued by government boards; board orders "
							+ "record the notices and disciplinary actions of a licensing board.", "",
							members(repeat("LicenseInfo/License", "Licenses", "LicensureEntity",
									field("LicensureEntity", "Licensure entity"),
									field("LicensureEntity/@code", "Licensure entity code"),
									field("LicensureEntity/@domain", "Code domain"),
									field("LicenseNumber", "License number"),
									suggest("LicenseStatus", "Status", "Active", "Inactive", "Expired", "Lapsed",
											"Probation", "Suspended", "Revoked", "Surrendered"),
									field("InitialLicensureDate", "Initial licensure date"),
									field("LicenseExpirationDate", "Expiration date")),
									repeat("DisciplinaryInfo/BoardOrder", "Board orders",
											"DisciplinaryEntity/DisciplinaryEntityName",
											group("DisciplinaryEntity", "Disciplinary entity",
													field("DisciplinaryEntityName", "Name"),
													field("DisciplinaryEntityName/@code", "Code"),
													field("DisciplinaryEntityName/@domain", "Code domain"),
													group("ContactInformation", "Contact information",
															field("@restrictions", "Restrictions"),
															group("Address", "Address", ADDRESS),
															group("ContactNumber", "Telephone", contactNumber()),
															field("EmailAddress", "Email addresses"))),
											field("OrderDate", "Order date"),
											field("OrderEffectiveDate", "Order effective date"),
											suggest("ActionForm", "Action form", "Consent order", "Final order",
													"Affidavit", "Death notice", "Fine"),
											group("NoticeOrAction/BoardNotice", "Board notice (either a notice or an "
													+ "action)",
													text("NoticeDescription", "Notice description"),
													field("DeathDate", "Death date"),
													text("Comment", "Comment"),
													field("@id", "Identifier"),
													field("@code", "Code")),
											group("NoticeOrAction/BoardAction", "Board action",
													text("ActionDescription", "Action description"),
													group("ActionSpan", "Action span",
															field("ActionEffectiveDate", "Effective date"),
															field("ActionExpirationDate", "Expiration date"),
															field("TermDuration", "Term duration (such as P10Y)"),
															field("TermFromDate", "Term from"),
															field("TermToDate", "Term to")),
													field("ActionStayed", "Action stayed"),
													field("IndefiniteAction", "Indefinite action"),
													text("Comment", "Comment"),
													field("@id", "Identifier"),
													field("@domain", "Identifier domain"),
													group("BoardOrderAppeal", "Appeal",
															field("NoticeOfAppealDate", "Notice of appeal date"),
															group("Judgement", "Judgement",
																	field("JudgementDate", "Date"),
																	text("JudgementDescription", "Description"),
																	field("@code", "Code"),
																	field("@domain", "Code domain")),
															field("CourtName", "Court"),
															field("AppealSource", "Appeal source"),
															field("PracticePermitted", "Practice permitted"),
															text("Comment", "Comment"),
															field("@id", "Identifier"),
															field("@domain", "Identifier domain"))))))),
			page("occupation", "Occupation and Specialties", CAREER,
					"Occupations, practices, privileges and specialties", "classification",
					section("Occupations", "OccupationInfo describes where and as what a professional works, "
							+ "with the medical specialties; academic positions can also be described in more detail "
							+ "on the Academic Appointments page.", "",
							members(repeat("OccupationInfo", "Occupations", "Occupation",
									suggest("Occupation", "Occupation", "Physician", "Surgeon", "Nurse Practitioner",
											"Registered Nurse", "Physician Assistant", "Pharmacist",
											"Family Physician"),
									field("OccupationTitle", "Title"),
									field("Practice"),
									institution("Institution"),
									field("Specialty", "Specialties"),
									field("Privileges"),
									field("StartDate", "Start date"),
									field("EndDate", "End date"),
									field("OccupationStatus", "Status"))))),
			page("appointments", "Academic Appointments", CAREER,
					"Faculty, chair, division or section and administrative appointments", "lifecycle",
					section("Academic appointments", "Faculty appointments with rank and tenure, department chairs, "
							+ "division or section chiefs and administrative appointments (such as Vice Dean).", "",
							members(repeat("AcademicAppointmentInfo/FacultyAppointment", "Faculty appointments",
									"Title",
									appointment(
											field("DivisionOrSection", "Division or section"),
											field("Rank", "Ranks (current first)"),
											repeat("Rank", "Effective dates of the ranks", null,
													field("@effectiveDate", "Effective date")),
											field("PrimaryOrJoint", "Primary or joint"),
											field("FacultyTrack", "Faculty track"),
											field("TenureStatus", "Tenure status (current first)"),
											repeat("TenureStatus", "Effective dates of the tenure status", null,
													field("@effectiveDate", "Effective date")),
											field("AppointmentType", "Appointment type"),
											field("PercentEffort", "Percent effort"),
											field("LocationType", "Location type"),
											field("Endowed"),
											field("EndowedTitle", "Endowed title"))),
									repeat("AcademicAppointmentInfo/ChairAppointment", "Chair appointments", "Title",
											appointment(field("ChairType", "Chair type"),
													field("ChairType/@term", "Term"))),
									repeat("AcademicAppointmentInfo/DivisionOrSectionAppointment",
											"Division or section appointments", "Title",
											appointment(field("DivisionOrSectionAppointmentType", "Appointment type"),
													field("DivisionOrSectionAppointmentType/@term", "Term"))),
									repeat("AcademicAppointmentInfo/AdministrativeAppointment",
											"Administrative appointments", "Title",
											appointment(field("AdministrativeLocationType", "Location type")))))),
			page("memberships", "Memberships", CAREER,
					"Memberships in professional societies and their components", "relation",
					section("Memberships", "Membership in professional medical societies and related organizations, "
							+ "with the chapters, special interest groups and committees the member belongs to.", "",
							members(repeat("MembershipInfo", "Memberships", "MembershipOrganization",
									field("MembershipOrganization", "Organization"),
									field("MembershipOrganization/@organizationID", "Organization ID"),
									field("Membership", "Membership (free text)"),
									field("Country/CountryName", "Country"),
									field("Country/CountryCode", "Country code (ISO 3166)"),
									field("MemberNumber", "Member number"),
									suggest("MembershipLevel", "Level", "Member", "Associate", "Fellow",
											"Student", "Resident"),
									field("MembershipLevel/@code", "Level code"),
									field("MembershipRole", "Role"),
									suggest("MembershipStatus", "Status", "Active", "Inactive", "Retired",
											"Emeritus", "Life"),
									field("MemberSinceDate", "Member since"),
									field("FellowshipDate", "Fellowship date"),
									repeat("ComponentGroup", "Component groups", "ComponentGroupName",
											suggest("ComponentGroupGenre", "Genre", "Chapter",
													"Special Interest Group", "Committee", "Section", "Council"),
											field("ComponentGroupName", "Name"),
											field("ComponentGroupRole", "Role"),
											field("MemberSinceDate", "Member since"),
											field("MemberUntilDate", "Member until")))))));

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		return PAGES;
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules, search, help
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<HlomProblem> rules(MedbiqDocumentType type, EObject documentRoot) {
		return ProfessionalProfileRules.check(documentRoot);
	}

	private static final List<FieldGroup> SEARCH_FIELDS = List.of(
			FieldGroup.of("pp-names", "Names", "Name"),
			FieldGroup.of("pp-identifiers", "Identifiers and numbers", "UniqueID", "InstitutionID", "ProgramID",
					"LicenseNumber", "MemberNumber", "CertificationID", "TaxNumber"),
			FieldGroup.of("pp-contact", "Addresses and contact information", "Address", "ContactNumber",
					"EmailAddress", "URL", "ContactInformation"),
			FieldGroup.of("pp-education", "Education and training", "EducationInfo", "TrainingInfo"),
			FieldGroup.of("pp-certification", "Specialty certification", "CertificationInfo"),
			FieldGroup.of("pp-licensure", "Licensure and discipline", "LicenseInfo", "DisciplinaryInfo"),
			FieldGroup.of("pp-specialties", "Specialties", "Specialty"),
			FieldGroup.of("pp-institutions", "Institutions and practices", "InstitutionName", "Practice",
					"Organization"),
			FieldGroup.of("pp-occupation", "Occupation and appointments", "OccupationInfo",
					"AcademicAppointmentInfo"),
			FieldGroup.of("pp-memberships", "Memberships", "MembershipInfo"));

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		return SEARCH_FIELDS;
	}

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		return ProfessionalProfileHelp.TEXTS;
	}
}
