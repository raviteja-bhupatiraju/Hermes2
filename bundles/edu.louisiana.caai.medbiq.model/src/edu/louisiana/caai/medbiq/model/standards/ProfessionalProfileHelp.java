/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import static java.util.Map.entry;

import java.util.Map;

/**
 * The help texts of the elements and attributes of Healthcare Professional Profile 1.0 and of the Name 1.0 and
 * Address 1.0 schemas it uses. The quotations are from the MedBiquitous specifications (Healthcare Professional
 * Profile Specifications Document 1.0, Name and Address Specifications and Description Documents 1.0), Copyright
 * MedBiquitous Consortium, used under the MedBiquitous Consortium XML Public License and Terms of Use.
 */
final class ProfessionalProfileHelp {

	private static final String RESTRICTIONS = "Whether this information may be shared or published: Unrestricted "
			+ "(“may be shared or published”), Restricted (“may be shared in some instances, but not published”) or "
			+ "Confidential (“may not be shared or published”).";
	private static final String ACCURACY = "Which parts of the date are accurate: DayMonthYear, MonthYear or Year.";
	private static final String INSTITUTION = "The institution: “elements that define an institution's name and "
			+ "address.”";
	private static final String CODE_DOMAIN = "The community, society or organization that is the source of the code.";

	/** The texts by element name ({@code name}, {@code @attribute} or {@code parent/name}). */
	static final Map<String, String> TEXTS = Map.ofEntries(
			// Members and Member
			entry("Members", "The root element: “professional profile information for one or more members of a "
					+ "community.”"),
			entry("Member", "“Profile information for a single user or healthcare professional within a "
					+ "community.” Each Member needs a Name; identify it with one or more UniqueIDs."),
			entry("Member/@restrictions", "Whether this member record may be shared with partner organizations or "
					+ "published. " + RESTRICTIONS),
			entry("@restrictions", RESTRICTIONS),
			entry("UniqueID", "“A unique alphanumeric identifier for the member”, such as an AAMC id, a National "
					+ "Provider Identifier (NPI, 10 digits) or the identifier of your own organization."),
			entry("@domain", "“The community, society, or organization that is the source of the unique id.”"),
			entry("UniqueID/@domain", "“Defines the community, society, or organization that is the source of the "
					+ "unique id. Use of the domain attribute is required when using the UniqueID element.” "
					+ "For example AAMC, NPI or California Board of Registered Nursing."),
			entry("ModifiedDate", "“The date that this member record was last updated.”"),
			entry("XtensibleInfo", "Elements of other namespaces “defined by the organization implementing the "
					+ "schema”, to extend the profile (edited on the XML page)."),
			// Name 1.0
			entry("Name", "The member's name (MedBiquitous Name 1.0); the specification requires it for each "
					+ "Member."),
			entry("FormattedName", "“An individual's name with all components in the proper order”, taking “into "
					+ "account cultural issues regarding order”. For example Jane Allen Doe, MD."),
			entry("Name/Title", "“A title of courtesy, honorific title, or professional title to be used with the "
					+ "individual's name. For example, Mr., Ms., Professor.”"),
			entry("GivenName", "“A person's first name or given name.” Repeat it for middle names: “the order is "
					+ "implied by the order of appearance.”"),
			entry("FamilyName", "“A person's last name or family name.” “If an individual has one name only, use "
					+ "FamilyName to indicate that name.”"),
			entry("GenerationIdentifier", "“Distinguishes family members with the same name. For example, Jr, Sr., "
					+ "III.”"),
			entry("Name/Degree", "“Degree abbreviations to be appended to a person's name. For example, MD, PhD, "
					+ "and RN.”"),
			entry("Alias", "“An additional name by which the person is known.”"),
			entry("FormerName", "“A previous name, such as a maiden name.”"),
			// Address 1.0
			entry("Address", "A postal address (MedBiquitous Address 1.0): organization, street address lines, "
					+ "city, state or province, postal code and the required Country."),
			entry("@addressCategory", "“The type of address”: Residential, Business or Undeliverable."),
			entry("ID", "“A unique identifier for a specific address.”"),
			entry("Organization", "“A company, institution, or department that is part of the address”, in the order "
					+ "of a formatted address."),
			entry("StreetAddressLine", "“A single line of a street address. For example, 10 Kensington High "
					+ "Street.” Lines appear in the order of a formatted address."),
			entry("City", "“The name of the city, town, or village included in the address.”"),
			entry("StateOrProvince", "“The name of the state, province, or territorial division within a country”, "
					+ "for example MD or British Columbia."),
			entry("PostalCode", "“The zipcode or other postal code used to facilitate the sorting of mail. For "
					+ "example, 21202.”"),
			entry("Region", "“A nonadministrative division of a country, or a commonly used name for a grouping of "
					+ "countries. For example, Central America.”"),
			entry("District", "“A nonadministrative division of a city, state, province, or country, as part of an "
					+ "address.”"),
			entry("Country", "The country: its name and its ISO 3166 code. Required in an address."),
			entry("CountryName", "“The name of the country”, for example Switzerland."),
			entry("CountryCode", "“The ISO 3166 alpha code for a particular country. For Switzerland, the country "
					+ "code is CH.”"),
			entry("ContactNumber/CountryCode", "“The numeric telephone code used to reach telephone numbers within a "
					+ "particular country. For example, 44.” (ITU E.164, up to 3 digits)"),
			// EducationInfo
			entry("EducationInfo", "“The educational background for a member, including bachelor's degree and "
					+ "medical degree.”"),
			entry("Degree", "“The degree received. For example, Bachelor of Arts, MD. This is not a controlled "
					+ "list.”"),
			entry("@discipline", "“The academic discipline for the degree received. For example, Engineering, "
					+ "Chemistry, etc.”"),
			entry("EducationalCertificate", "“The certificate received. For example, Perfusionist. This is not a "
					+ "controlled list.”"),
			entry("EducationalCertificate/@type", "“The type of certificate. For example, Professional, "
					+ "HighSchool, etc.”"),
			entry("InstitutionInfo", INSTITUTION),
			entry("InstitutionName", "“The name of a medical or educational institution. For example, Johns Hopkins "
					+ "School of Medicine.”"),
			entry("InstitutionID", "“A unique alphanumeric identifier for the institution”; its domain is the "
					+ "organization that is the source of the identifier (for example AAMC)."),
			entry("Distinction", "“Any distinctions received. For example, CumLaude, Honors” or Chief Resident."),
			entry("EducationStatus", "“The status of the individual in relation to the educational program”: "
					+ "Active, Graduated, Leave of Absence, Withdrawn, Dismissed or Deceased."),
			entry("StartDate", "The date the program, training or occupation started."),
			entry("EndDate", "The date the program, training, occupation or appointment ended."),
			entry("EducationInfo/StartDate", "“The date the user started this degree or certificate program.”"),
			entry("EducationInfo/EndDate", "“The date the user completed or ended this degree or certificate "
					+ "program.”"),
			entry("GraduationDate", "“The individual's scheduled graduation date for this degree or certificate "
					+ "program.”"),
			entry("@accuracy", ACCURACY),
			// TrainingInfo
			entry("TrainingInfo", "“A medical professional's postgraduate training, including residency and "
					+ "fellowship.”"),
			entry("ProgramInfo", "“Detailed information about a postgraduate medical program, including name, "
					+ "accreditation, medical specialty, and institution.”"),
			entry("ProgramName", "“The name of the postgraduate program. For example, Johns Hopkins Emergency "
					+ "Medicine Residency Program.”"),
			entry("ProgramName/@type", "“The type of post-graduate program”: Residency, Fellowship or Other."),
			entry("ProgramID", "“A unique alphanumeric identifier for the training program”, with the domain that is "
					+ "its source."),
			entry("Accreditation", "“The accrediting organization for this program. For example, ACGME.”"),
			entry("Specialty", "The medical specialty, for example Cardiology."),
			entry("TrainingStatus", "“The individual's status within the training program”: year 1 to year 9 while "
					+ "in training, Completed Preliminary Training, Completed Specialty Training, Leave of Absence, "
					+ "Withdrawn, Dismissed or Deceased."),
			entry("TrainingInfo/StartDate", "“The date the user started this training program.”"),
			entry("TrainingInfo/EndDate", "“The date the individual completed or ended this training program.”"),
			entry("CompletionDate", "“The individual's completion date for this training program.”"),
			// CertificationInfo
			entry("CertificationInfo", "“A medical professional's certification from a medical specialty board, "
					+ "such as the American Board of Internal Medicine.”"),
			entry("CertificationOrganization", "“The organization certifying the certification board. For example, "
					+ "American Board of Medical Specialties.”"),
			entry("CertificationBoard", "“The board certifying the medical professional. For example, American "
					+ "Board of Pediatrics.”"),
			entry("CertificateStatus", "“The current status of the individual's specialty certification”: Active, "
					+ "Inactive, Expired, Revoked, Suspended or Surrendered."),
			entry("CertificateInfo", "“A particular certification held by the medical professional”, for example "
					+ "Internal Medicine or Geriatric Medicine."),
			entry("CertificateName", "“The name of the certificate. For example, General Surgery.”"),
			entry("@certificateID", "“The identifier for a type of certificate issued by a specific specialty "
					+ "board”, assigned by the certification organization (for example ABMS)."),
			entry("CertificateType", "“The type of certificate”: General Certificate or Subcertificate."),
			entry("CertificateIssuance", "“A specific issuance of certifications for the certificate”: the initial "
					+ "certification, recertifications and maintenance of certification."),
			entry("CertificationID", "“A unique identifier for a specific issuance of a certificate”; a "
					+ "recertification has a different certification ID than the initial certification."),
			entry("CertificationDuration", "“The duration of the certificate”: Lifetime or Time-limited."),
			entry("CertificationOccurrence", "“Whether the certificate is an initial certificate or one issued "
					+ "through a recertification process”: Initial, Recertification or Maintenance of Certification."),
			entry("CertificationIssueDate", "“The date the certificate was issued.”"),
			entry("CertificationExpireDate", "“The date the certificate will expire.”"),
			entry("CertificationStatus", "“Whether or not a certification is active”: Active, Inactive, Expired, "
					+ "Revoked, Suspended or Surrendered."),
			// LicenseInfo
			entry("LicenseInfo", "“The professional licenses held by a healthcare professional.”"),
			entry("License", "“A health professions license issued by a government board.”"),
			entry("LicensureEntity", "“The government entity issuing the license”, for example Maryland Board of "
					+ "Physicians, with an optional code and the domain of the code."),
			entry("LicensureEntity/@code", "“A code defining the licensure entity.”"),
			entry("@code", "A code; its domain is " + CODE_DOMAIN.toLowerCase(java.util.Locale.ROOT)),
			entry("LicenseNumber", "“The alphanumeric string that identifies this issuance of the license.”"),
			entry("InitialLicensureDate", "“The date that this individual was initially licensed by this licensing "
					+ "entity.”"),
			entry("LicenseStatus", "“The current status of the license. For example, Active, Inactive.”"),
			entry("LicenseExpirationDate", "“The date the current license will expire.”"),
			// DisciplinaryInfo
			entry("DisciplinaryInfo", "“Any disciplinary actions or notices for the healthcare professional issued by "
					+ "a licensing board.”"),
			entry("BoardOrder", "“A board notice or disciplinary action in detail.”"),
			entry("DisciplinaryEntity", "“The entity issuing the board notice or action.”"),
			entry("DisciplinaryEntityName", "“The name of the entity issuing the board notice or action”, with an "
					+ "optional code and its domain."),
			entry("ContactInformation", "“Detailed contact information for the disciplinary entity.”"),
			entry("OrderDate", "“The date the order was issued.”"),
			entry("OrderEffectiveDate", "“The date the order is effective.”"),
			entry("ActionForm", "“The type of legal form constituting the notice or action. For example: Affidavit, "
					+ "Death notice, etc.”"),
			entry("NoticeOrAction", "A notice or an action: “Either BoardNotice or BoardAction is required.”"),
			entry("BoardNotice", "“A board notice in detail”, such as a notice of death."),
			entry("NoticeDescription", "“A description of the board notice issued.”"),
			entry("DeathDate", "“The date of death indicated on the board notice, if applicable.”"),
			entry("Comment", "Additional commentary on the notice, action or appeal."),
			entry("BoardAction", "“A disciplinary action in detail.”"),
			entry("@id", "“A unique identifier describing the notice”, action or appeal."),
			entry("ActionDescription", "“A description of the board action issued.”"),
			entry("ActionSpan", "“The effective dates or term of the action.”"),
			entry("ActionEffectiveDate", "“The date the action is effective.”"),
			entry("ActionExpirationDate", "“The date the action expires.”"),
			entry("TermDuration", "“The duration of the action term”, as an XML duration such as P10Y (ten years)."),
			entry("TermFromDate", "“The date the term of the action begins.”"),
			entry("TermToDate", "“The date the term of the action ends.”"),
			entry("ActionStayed", "“Whether the action has been stayed.”"),
			entry("IndefiniteAction", "“Whether the action has been deemed indefinite”: Yes or No."),
			entry("BoardOrderAppeal", "“Any appeals proceeding brought against the action.”"),
			entry("NoticeOfAppealDate", "“The date of the appeal notice.”"),
			entry("Judgement", "“Detailed information about the appeal judgement.”"),
			entry("JudgementDate", "“The date of the judgement.”"),
			entry("JudgementDescription", "“A text description of the judgement.”"),
			entry("CourtName", "“The name of the court hearing the appeal and providing the judgement.”"),
			entry("AppealSource", "“The source of the appeal”; its code defines “the type of entity appealing the "
					+ "action.”"),
			entry("PracticePermitted", "“Whether the individual is permitted to practice under the terms of the "
					+ "appeal.”"),
			// AcademicAppointmentInfo
			entry("AcademicAppointmentInfo", "“Any academic appointments held by a healthcare professional”: "
					+ "faculty, chair, division or section and administrative appointments."),
			entry("FacultyAppointment", "“A single faculty appointment in detail.”"),
			entry("ChairAppointment", "“An individual's appointment as chair of a department.”"),
			entry("DivisionOrSectionAppointment", "“An individual's appointment within a division or section of an "
					+ "academic department.”"),
			entry("AdministrativeAppointment", "“An individual's administrative appointment within a school, "
					+ "hospital, or Veterans Administration organization”, not chair or chief appointments."),
			entry("AdministrativeUnit", "“The academic department for this appointment. For example, Department of "
					+ "Surgery.” (for administrative appointments: the administrative unit, such as Legal Department)"),
			entry("EffectiveDate", "“The start date for this appointment.”"),
			entry("Title", "“The official title of the appointment at the institution”, for example Assistant "
					+ "Professor of Medicine or Chairman, Department of Surgery."),
			entry("DivisionOrSection", "“The academic division or section within the department applicable for this "
					+ "appointment. For example, Cardiology.”"),
			entry("Rank", "“An academic rank associated with this appointment”: Professor, Associate Professor, "
					+ "Assistant Professor, Instructor or Lecturer. “If more than one rank is specified, list the "
					+ "current rank first.”"),
			entry("@effectiveDate", "The date the rank or tenure status “became effective.”"),
			entry("PrimaryOrJoint", "“Whether the appointment is a primary appointment or joint appointment.”"),
			entry("FacultyTrack", "“The faculty track for this appointment.”"),
			entry("TenureStatus", "“A current or past tenure status”: Tenured, Tenure Eligible Track, Not Tenure "
					+ "Eligible Track or Tenure Not Available. “List the current tenure status first.”"),
			entry("AppointmentType", "“The nature of the appointment”: Full-time, Part-time, Volunteer or Emeritus."),
			entry("PercentEffort", "“The percentage of time for appointments that require part-time effort”, an "
					+ "integer between 1 and 100."),
			entry("LocationType", "“The type of location for this faculty appointment”: School-based, Hospital-based "
					+ "or VA-based."),
			entry("Endowed", "“Whether or not this appointment is an endowed appointment”: Yes or No."),
			entry("EndowedTitle", "“The title of the endowed appointment. For example, William Stewart Halsted "
					+ "Professor of Surgery.”"),
			entry("ChairType", "“The type of chair appointment held”: Chair or Co-chair."),
			entry("@term", "“The permanence of the term of office”: Acting, Interim or Permanent."),
			entry("DivisionOrSectionAppointmentType", "“The type of appointment held”: Chief or Co-chief."),
			entry("AdministrativeLocationType", "“The location of the administrative appointment”: School-based, "
					+ "Hospital-based or VA-based."),
			// OccupationInfo
			entry("OccupationInfo", "“A user's occupation with specific elements for physician occupations.”"),
			entry("Occupation", "“A user's occupation as a free-format text field.”"),
			entry("OccupationTitle", "“The user's official title within the institution or practice.”"),
			entry("Privileges", "“The privileges that a user has at the institution listed.”"),
			entry("Practice", "“The name of a medical professional's practice. For example, Doyle Eye Associates.”"),
			entry("OccupationInfo/StartDate", "“The date the user started working in this practice or "
					+ "institution.”"),
			entry("OccupationInfo/EndDate", "“The date the user stopped working at this practice or institution.”"),
			entry("OccupationInfo/Specialty", "“The medical specialty of the user. For example, Cardiology.”"),
			entry("OccupationStatus", "The status of the occupation: Active or Retired."),
			// PersonalInfo
			entry("PersonalInfo", "“Personal information about a member”; its restrictions say whether it may be "
					+ "shared or published."),
			entry("PhysicalStatus", "“The physical status of the person”: Living or Deceased."),
			entry("BirthDate", "“A person's birth date”, with its accuracy and restrictions."),
			entry("DeceasedDate", "“The date of death for the individual.”"),
			entry("Language", "“A language a person speaks, reads, or writes.”"),
			entry("Citizenship", "“The nation of citizenship of an individual”: a country name and ISO 3166 code."),
			entry("@pointInTime", "Describes “citizenship at multiple points in time”: entry to medical school, "
					+ "birth or current."),
			entry("CountryOfResidence", "“The country of residence for an individual.”"),
			entry("Gender", "“The gender of the person”: Male or Female."),
			entry("Race", "“A population group or classification usually based on physical characteristics.”"),
			entry("Ethnicity", "“A population group based on culture, religion, language, nationality, or race.”"),
			entry("ContactNumber", "“A person's telephone, fax, and pager numbers.”"),
			entry("TelephoneNumber", "“A telephone, fax, or pager number, including area or city code” (digits "
					+ "only, up to 14)."),
			entry("Extension", "“A telephone extension used to reach an individual.”"),
			entry("ContactNumber/Description", "“The type of telephone number. For example, home, business, fax, "
					+ "pager, or mobile.”"),
			entry("EmailAddress", "“A person's email address”, conforming to RFC 2822, for example "
					+ "pshelley@nosuchmail.com or Percy Shelley <pshelley@nosuchmail.com>."),
			entry("URL", "“The location for a web page or site for this person. For example, "
					+ "http://www.johnsmithmd.com.”"),
			entry("TaxNumber", "“Tax number such as social security number, for the individual.” Usually "
					+ "Confidential."),
			entry("TaxNumber/@type", "“The type of tax number. For example, Social Security Number or Social "
					+ "Insurance Number.”"),
			entry("FamilyMember", "“A member of the individual's family.”"),
			entry("@relationship", "“The family member's relationship to the individual that is profiled. For "
					+ "example, husband.”"),
			entry("PreferredMethodOfContact", "“The preferred method of contact for this individual. For example, "
					+ "cellphone, telephone, fax, pager, email, postal.”"),
			// MembershipInfo
			entry("MembershipInfo", "“A user's membership in professional medical societies and related "
					+ "organizations.”"),
			entry("Membership", "“Membership in a single society or organization in a free-format text field.”"),
			entry("MembershipOrganization", "“The name of the organization to which the user belongs. For example, "
					+ "American Academy of Ophthalmology.”"),
			entry("@organizationID", "“A unique, alphanumeric identification for the organization.”"),
			entry("MembershipInfo/Country", "“The country of the organization.”"),
			entry("MemberNumber", "“The number used by the organization to identify this member.”"),
			entry("MembershipLevel", "“The membership level of the member. For example, associate, fellow”; its "
					+ "code is the one the organization associates with the level."),
			entry("MembershipRole", "“The member's role in the organization. For example, President, Secretary "
					+ "General.”"),
			entry("MembershipStatus", "“A user's membership status within the organization. For example, active, "
					+ "inactive, retired, emeritus, life.”"),
			entry("MemberSinceDate", "“The date that the member first joined the organization” (or component)."),
			entry("FellowshipDate", "“The date that the member became a fellow within the organization.”"),
			entry("ComponentGroup", "“Membership in chapters, special interest groups, and other components of "
					+ "national and international professional medical societies.”"),
			entry("ComponentGroupGenre", "“The type of component to which the user belongs. For example, Chapter, "
					+ "Special Interest Group, Committee.”"),
			entry("ComponentGroupName", "“The name of the organization component to which the user belongs. For "
					+ "example, Belgian Chapter, Annual Meeting Program Committee.”"),
			entry("ComponentGroupRole", "“The member's role in the organization component. For example, Committee "
					+ "Chair, Secretary.”"),
			entry("MemberUntilDate", "“The date that the individual's membership in the organization component "
					+ "expires.”"));

	private ProfessionalProfileHelp() {
	}
}
