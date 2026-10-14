/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/** Tests the Healthcare Professional Profile 1.0 content: template, samples, titles, pages and rules. */
class ProfessionalProfileTest {

	private static final String TYPE = "professional-profile-v1";
	private static final String FOLDER = "samples/professional-profile/";
	private static final String PHYSICIAN = FOLDER + "physician-cardiologist.xml";
	private static final String NURSE = FOLDER + "nurse-practitioner.xml";
	private static final String TOOLKIT = FOLDER + "medbiq-toolkit-sample.xml";

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	// ----------------------------------------------------------------------------------------------------
	// Template, samples, titles and pages
	// ----------------------------------------------------------------------------------------------------

	@Test
	void templateIsValidAndTitled() {
		String xml = MedbiqSamples.template(TYPE, "Jordan Q. Example, MD");
		assertFalse(xml.contains("${"), xml);
		Result result = MedbiqSamples.assertValid(TYPE, xml);
		assertEquals(TYPE, result.type().id());
		assertEquals(List.of(), result.problems().stream().map(p -> p.problem().message()).toList());
		assertEquals("Jordan Q. Example, MD", result.type().title(result.documentRoot()));
	}

	@Test
	void fictionalSamplesAreValidWithoutProblems() {
		List<String> files = MedbiqSamples.files("samples/professional-profile");
		assertEquals(List.of(TOOLKIT, NURSE, PHYSICIAN), files);
		for (String file : List.of(PHYSICIAN, NURSE)) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			assertEquals(TYPE, result.type().id(), file);
			assertEquals(List.of(), result.problems().stream().map(p -> p.problem().message()).toList(), file);
		}
	}

	/**
	 * The toolkit sample is valid against the schemas and has no rule errors. The model check reports its
	 * {@code TrainingStatus} "Completed Specialty Training" against the pattern of the first member type of the
	 * union {@code TrainingStatusType} (a known limitation of the dynamic model), which is left out here.
	 */
	@Test
	void toolkitSampleIsValidAgainstTheSchemas() {
		Result result = MedbiqSamples.check(MedbiqSamples.text(TOOLKIT));
		// Also the union TrainingStatus ("Completed Specialty Training", not only "year N") is valid.
		assertEquals(List.of(), result.problems().stream().map(p -> p.problem().message()).toList());
		assertEquals("Emily Dickinson", result.type().title(result.documentRoot()));
		// Without the ModifiedDate the sample is checked against today: its license expired long ago but is Active.
		Result today = MedbiqSamples.check(replace(TOOLKIT, "<ModifiedDate>2009-06-01</ModifiedDate>",
				""));
		List<String> warnings = MedbiqSamples.messages(today, Severity.WARNING);
		assertTrue(warnings.stream().anyMatch(m -> m.contains("is Active but expired on 2010-06-30")), warnings::toString);
	}

	@Test
	void titlesAndDescriptions() {
		assertEquals("Teodora Ilse Brannigan-Vos, MD, PhD", title(PHYSICIAN));
		assertEquals("Rafael Okafor-Lindqvist, DNP, APRN, FNP-BC", title(NURSE));
		// Without a formatted name: the given and family names with the degrees
		String noFormatted = replace(PHYSICIAN, "<n:FormattedName>Teodora Ilse Brannigan-Vos, MD, PhD</n:FormattedName>",
				"");
		Result result = MedbiqSamples.check(noFormatted);
		assertEquals("Teodora Ilse Brannigan-Vos, MD, PhD", result.type().title(result.documentRoot()));
		// Several members: a count
		String two = MedbiqSamples.text(NURSE).replace("</Members>",
				"<Member><Name><n:FamilyName>Second</n:FamilyName></Name></Member></Members>");
		result = MedbiqSamples.check(two);
		assertEquals("2 professional profiles", result.type().title(result.documentRoot()));
		assertTrue(result.type().description(result.documentRoot()).contains("Rafael Okafor-Lindqvist"));

		Result physician = MedbiqSamples.check(MedbiqSamples.text(PHYSICIAN));
		assertEquals("Physician · Cardiovascular Disease · Internal Medicine · Gulf Coast University Heart and Vascular "
				+ "Clinic", physician.type().description(physician.documentRoot()));
		// The current occupation (without an end date), not the former one
		Result nurse = MedbiqSamples.check(MedbiqSamples.text(NURSE));
		String description = nurse.type().description(nurse.documentRoot());
		assertTrue(description.startsWith("Nurse Practitioner"), description);
		assertFalse(description.contains("Registered Nurse"), description);
	}

	@Test
	void pagesSearchFieldsAndHelp() {
		var type = MedbiqStandards.byId(TYPE);
		assertEquals(List.of("members", "contact", "personal", "education", "certification", "licensure", "occupation",
				"appointments", "memberships"), type.content().pages(type).stream().map(PageSpec::id).toList());
		assertTrue(type.searchFields().stream().allMatch(group -> group.id().startsWith("pp-")));
		assertTrue(type.searchFields().stream().anyMatch(group -> group.id().equals("pp-specialties")));
		assertTrue(type.fieldHelp(List.of("UniqueID", "@domain")).contains("required"));
		assertTrue(type.fieldHelp(List.of("CertificateIssuance")).contains("recertification"));
		assertTrue(type.fieldHelp(List.of("FamilyName")).contains("one name only"));
		assertTrue(type.fieldHelp(List.of("Address", "CountryCode")).contains("ISO 3166"));
		assertTrue(type.fieldHelp(List.of("ContactNumber", "CountryCode")).contains("telephone code"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules: members, names and identifiers
	// ----------------------------------------------------------------------------------------------------

	@Test
	void memberNeedsName() {
		assertProblem(NURSE, between(NURSE, "<Name>", "</Name>"), "", Severity.ERROR, "has no Name");
	}

	@Test
	void nameNeedsFamilyName() {
		String text = MedbiqSamples.text(NURSE)
				.replace("<n:FormattedName>Rafael Okafor-Lindqvist, DNP, APRN, FNP-BC</n:FormattedName>", "")
				.replace("<n:FamilyName>Okafor-Lindqvist</n:FamilyName>\n\t\t\t<n:Degree>", "<n:Degree>");
		assertMessage(text, Severity.WARNING, "neither a FamilyName nor a FormattedName");
	}

	@Test
	void memberNeedsUniqueId() {
		String text = MedbiqSamples.text(NURSE)
				.replace("<UniqueID domain=\"Minnesota Board of Nursing\">R 2209184</UniqueID>", "")
				.replace("<UniqueID domain=\"American Association of Nurse Practitioners\">AANP-5520871</UniqueID>", "");
		assertMessage(text, Severity.WARNING, "has no UniqueID");
	}

	@Test
	void uniqueIdNeedsDomain() {
		assertProblem(NURSE, "domain=\"Minnesota Board of Nursing\"", "domain=\" \"", Severity.WARNING,
				"has an empty domain");
	}

	@Test
	void uniqueIdIdentifiesOneMember() {
		String text = MedbiqSamples.text(NURSE).replace("</Members>", "<Member><UniqueID domain=\"Minnesota Board of "
				+ "Nursing\">R 2209184</UniqueID><Name><n:FamilyName>Other</n:FamilyName></Name></Member></Members>");
		assertMessage(text, Severity.ERROR, "also identifies another Member");
		String repeated = replace(NURSE, "AANP-5520871</UniqueID>", "AANP-5520871</UniqueID><UniqueID "
				+ "domain=\"American Association of Nurse Practitioners\">AANP-5520871</UniqueID>");
		assertMessage(repeated, Severity.WARNING, "is repeated");
	}

	@Test
	void nationalProviderIdentifiers() {
		String valid = replace(NURSE, "domain=\"Minnesota Board of Nursing\">R 2209184", "domain=\"NPI\">1234567893");
		assertEquals(List.of(), rulesProblems(MedbiqSamples.check(valid)));
		assertProblem(NURSE, "domain=\"Minnesota Board of Nursing\">R 2209184", "domain=\"NPI\">1234567890",
				Severity.WARNING, "wrong check digit");
		assertProblem(NURSE, "domain=\"Minnesota Board of Nursing\">R 2209184", "domain=\"NPI\">12345",
				Severity.WARNING, "10-digit number");
	}

	@Test
	void modifiedDateNotInFuture() {
		assertProblem(NURSE, "<ModifiedDate>2026-08-15", "<ModifiedDate>2999-08-15", Severity.WARNING,
				"is in the future");
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules: dates and statuses
	// ----------------------------------------------------------------------------------------------------

	@Test
	void educationAndTrainingDatesInOrder() {
		assertProblem(NURSE, "<EndDate>2012-05-12</EndDate>", "<EndDate>2007-05-12</EndDate>", Severity.ERROR,
				"EndDate 2007-05-12 before the StartDate 2008-08-25");
		assertProblem(PHYSICIAN, "<CompletionDate>2018-06-30", "<CompletionDate>2014-06-30", Severity.ERROR,
				"CompletionDate 2014-06-30 before the StartDate");
		// Dates of a lower accuracy are compared at that accuracy: same month is in order
		String sameMonth = replace(PHYSICIAN, "<EndDate>2005-05-14</EndDate>", "<EndDate>2001-08-01</EndDate>");
		assertEquals(List.of(), rulesProblems(MedbiqSamples.check(sameMonth.replace(
				"<StartDate accuracy=\"MonthYear\">2001-08-01", "<StartDate accuracy=\"MonthYear\">2001-08-20"))));
	}

	@Test
	void trainingInProgramYearIsNotCompleted() {
		assertProblem(NURSE, "<StartDate>2017-08-01</StartDate>",
				"<TrainingStatus>year 1</TrainingStatus><StartDate>2017-08-01</StartDate>",
				Severity.WARNING, "but a CompletionDate");
	}

	@Test
	void certificateDates() {
		assertProblem(NURSE, "<CertificationExpireDate>2027-06-11", "<CertificationExpireDate>2021-06-11",
				Severity.ERROR, "CertificationExpireDate 2021-06-11 before the CertificationIssueDate");
		assertProblem(NURSE, "<CertificationExpireDate>2027-06-11", "<CertificationExpireDate>2026-06-11",
				Severity.WARNING, "is Active but expired on 2026-06-11");
		assertProblem(PHYSICIAN, "<CertificationDuration>Time-limited</CertificationDuration>\n\t\t\t\t\t"
				+ "<CertificationOccurrence>Initial</CertificationOccurrence>\n\t\t\t\t\t"
				+ "<CertificationIssueDate accuracy=\"DayMonthYear\">2018-11-01",
				"<CertificationDuration>Lifetime</CertificationDuration>"
						+ "<CertificationOccurrence>Initial</CertificationOccurrence>"
						+ "<CertificationIssueDate accuracy=\"DayMonthYear\">2018-11-01",
				Severity.WARNING, "Lifetime duration but an expiration date");
	}

	@Test
	void licenseDatesAndDuplicates() {
		assertProblem(PHYSICIAN, "<LicenseExpirationDate>2027-06-30", "<LicenseExpirationDate>2012-06-30",
				Severity.ERROR, "LicenseExpirationDate 2012-06-30 before the InitialLicensureDate");
		assertProblem(PHYSICIAN, "<LicenseExpirationDate>2027-06-30", "<LicenseExpirationDate>2026-06-30",
				Severity.WARNING, "is Active but expired on 2026-06-30");
		assertProblem(NURSE, "<LicenseNumber>APRN 0118342</LicenseNumber>", "<LicenseNumber>R 2209184</LicenseNumber>",
				Severity.WARNING, "is listed twice");
	}

	@Test
	void boardActionDates() {
		String order = """
				<DisciplinaryInfo><BoardOrder>
				<DisciplinaryEntity><DisciplinaryEntityName>Board</DisciplinaryEntityName></DisciplinaryEntity>
				<NoticeOrAction><BoardAction><ActionDescription>Probation</ActionDescription>
				<ActionSpan><ActionEffectiveDate>2020-01-01</ActionEffectiveDate>
				<ActionExpirationDate>2019-01-01</ActionExpirationDate><TermDuration>P2Y</TermDuration>
				<TermFromDate>2020-01-01</TermFromDate><TermToDate>2025-01-01</TermToDate></ActionSpan>
				<BoardOrderAppeal><NoticeOfAppealDate>2020-05-01</NoticeOfAppealDate>
				<Judgement><JudgementDate>2020-04-01</JudgementDate><JudgementDescription>Upheld</JudgementDescription>
				</Judgement></BoardOrderAppeal></BoardAction></NoticeOrAction></BoardOrder></DisciplinaryInfo>
				<AcademicAppointmentInfo>""";
		String text = replace(NURSE, "<AcademicAppointmentInfo>", order);
		assertSchemaValid("board action", text);
		assertMessage(text, Severity.ERROR, "ActionExpirationDate 2019-01-01 before the ActionEffectiveDate");
		assertMessage(text, Severity.WARNING, "TermDuration P2Y of the board action does not agree");
		assertMessage(text, Severity.ERROR, "dated before the notice of appeal");
		String agreeing = text.replace("<TermDuration>P2Y</TermDuration>", "<TermDuration>P5Y</TermDuration>");
		assertFalse(rulesProblems(MedbiqSamples.check(agreeing)).stream().anyMatch(m -> m.contains("TermDuration")));
	}

	@Test
	void appointmentOccupationAndMembershipDates() {
		assertProblem(NURSE, "<EffectiveDate>2021-08-16</EffectiveDate>",
				"<EffectiveDate>2021-08-16</EffectiveDate><EndDate>2020-01-01</EndDate>", Severity.ERROR,
				"faculty appointment “Adjunct Clinical Instructor” has the EndDate 2020-01-01");
		assertProblem(NURSE, "<EndDate>2017-07-28</EndDate>", "<EndDate>2011-07-28</EndDate>", Severity.ERROR,
				"occupation “Registered Nurse” has the EndDate");
		assertProblem(PHYSICIAN, "<FellowshipDate>2020-03-28", "<FellowshipDate>2014-03-28", Severity.ERROR,
				"FellowshipDate 2014-03-28 before the MemberSinceDate");
		assertProblem(PHYSICIAN, "<MemberUntilDate>2026-12-31", "<MemberUntilDate>2020-12-31", Severity.ERROR,
				"MemberUntilDate 2020-12-31 before the MemberSinceDate");
	}

	@Test
	void facultyAppointmentEndowmentAndCurrentRankFirst() {
		assertProblem(NURSE, "<Endowed>No</Endowed>", "<Endowed>No</Endowed><EndowedTitle>Chair</EndowedTitle>",
				Severity.WARNING, "has an EndowedTitle but is not Endowed");
		assertProblem(NURSE, "<Endowed>No</Endowed>", "<Endowed>Yes</Endowed>", Severity.INFO,
				"give the EndowedTitle");
		String swapped = replace(PHYSICIAN,
				"<Rank effectiveDate=\"2023-07-01\">Associate Professor</Rank>\n\t\t\t\t"
						+ "<Rank effectiveDate=\"2018-07-01\">Assistant Professor</Rank>",
				"<Rank effectiveDate=\"2018-07-01\">Assistant Professor</Rank>"
						+ "<Rank effectiveDate=\"2023-07-01\">Associate Professor</Rank>");
		assertMessage(swapped, Severity.WARNING, "list the current rank first");
	}

	@Test
	void personalDataConsistent() {
		assertProblem(PHYSICIAN, ">1983-01-01</BirthDate>", ">2999-01-01</BirthDate>", Severity.ERROR,
				"is in the future");
		assertProblem(PHYSICIAN, ">1983-01-01</BirthDate>", ">1983-01-01</BirthDate><DeceasedDate>1982-01-01"
				+ "</DeceasedDate>", Severity.ERROR, "before the BirthDate");
		assertProblem(PHYSICIAN, ">1983-01-01</BirthDate>", ">1983-01-01</BirthDate><DeceasedDate>2026-01-01"
				+ "</DeceasedDate>", Severity.ERROR, "is Living, but there is a DeceasedDate");
		assertProblem(PHYSICIAN, "<PhysicalStatus>Living</PhysicalStatus>", "<PhysicalStatus>Deceased"
				+ "</PhysicalStatus>", Severity.INFO, "add the DeceasedDate");
		assertProblem(PHYSICIAN, ">1983-01-01</BirthDate>", ">2002-01-01</BirthDate>", Severity.WARNING,
				"before the BirthDate 2002-01-01");
		assertProblem(PHYSICIAN, "<EducationStatus>Graduated</EducationStatus>", "<EducationStatus>Deceased"
				+ "</EducationStatus>", Severity.WARNING, "EducationStatus of Teodora Ilse Brannigan-Vos, MD, PhD is "
						+ "Deceased");
		assertProblem(PHYSICIAN, "<PreferredMethodOfContact>", "<TaxNumber>000-00-0000</TaxNumber>"
				+ "<PreferredMethodOfContact>", Severity.INFO, "TaxNumber");
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules: entries, countries, addresses, telephone numbers, email and web addresses
	// ----------------------------------------------------------------------------------------------------

	@Test
	void entriesNameWhatTheyAre() {
		assertProblem(NURSE, "<MembershipOrganization>American Association of Nurse Practitioners"
				+ "</MembershipOrganization>", "", Severity.WARNING, "names no organization");
		assertProblem(NURSE, between(NURSE, "<CertificationInfo>", "</CertificationInfo>"),
				"<CertificationInfo><CertificateStatus>Active</CertificateStatus></CertificationInfo>",
				Severity.WARNING, "names neither the CertificationBoard nor a certificate");
		assertProblem(NURSE, "<EducationInfo>", "<EducationInfo><Distinction>Honors</Distinction></EducationInfo>"
				+ "<EducationInfo>", Severity.INFO, "names neither a Degree");
	}

	@Test
	void countryCodes() {
		assertProblem(NURSE, "<a:CountryCode>US</a:CountryCode>\n\t\t\t</a:Country>\n\t\t</Address>\n\t\t<Education",
				"<a:CountryCode>us</a:CountryCode></a:Country></Address><Education", Severity.WARNING,
				"should be written in capitals");
		assertProblem(NURSE, "<Citizenship pointInTime=\"current\">\n\t\t\t\t<a:CountryCode>US",
				"<Citizenship pointInTime=\"current\"><a:CountryCode>XQ", Severity.WARNING,
				"is not an ISO 3166 alpha code");
		assertProblem(NURSE, "<Citizenship pointInTime=\"current\">\n\t\t\t\t<a:CountryCode>US</a:CountryCode>",
				"<Citizenship pointInTime=\"current\">", Severity.WARNING, "neither a CountryName nor a CountryCode");
		assertEquals(List.of(), rulesProblems(MedbiqSamples.check(replace(NURSE, "<a:CountryCode>US</a:CountryCode>\n"
				+ "\t\t\t</a:Country>\n\t\t</Address>\n\t\t<Education",
				"<a:CountryCode>USA</a:CountryCode></a:Country></Address><Education"))));
	}

	@Test
	void statesAndPostalCodes() {
		assertProblem(NURSE, "<a:StateOrProvince>MN</a:StateOrProvince>\n\t\t\t<a:PostalCode>55802-1340",
				"<a:StateOrProvince>XY</a:StateOrProvince><a:PostalCode>55802-1340", Severity.WARNING,
				"is not a state, district or territory of the United States");
		assertProblem(NURSE, "<a:PostalCode>55802-1340", "<a:PostalCode>5580", Severity.WARNING,
				"is not a United States ZIP code");
		String canada = replace(NURSE, "<a:StateOrProvince>MN</a:StateOrProvince>\n\t\t\t<a:PostalCode>55802-1340"
				+ "</a:PostalCode>\n\t\t\t<a:Country>\n\t\t\t\t<a:CountryName>United States</a:CountryName>\n\t\t\t\t"
				+ "<a:CountryCode>US</a:CountryCode>", "<a:StateOrProvince>Ontario</a:StateOrProvince>"
						+ "<a:PostalCode>P7B 5E1</a:PostalCode><a:Country><a:CountryCode>CA</a:CountryCode>");
		assertEquals(List.of(), rulesProblems(MedbiqSamples.check(canada)));
		assertMessage(canada.replace("P7B 5E1", "55802"), Severity.WARNING, "is not a Canadian postal code");
		// The state written out in full is accepted
		assertTrue(MedbiqSamples.text(NURSE).contains("<a:StateOrProvince>Minnesota</a:StateOrProvince>"));
	}

	@Test
	void telephoneNumbers() {
		assertProblem(NURSE, "<TelephoneNumber>2185550167", "<TelephoneNumber>218 555 KLAX", Severity.WARNING,
				"contains characters other than digits");
		assertProblem(NURSE, "<TelephoneNumber>2185550167", "<TelephoneNumber>(218) 555-0167", Severity.INFO,
				"is formatted");
		assertProblem(NURSE, "<TelephoneNumber>2185550167", "<TelephoneNumber>5550167", Severity.WARNING,
				"not 10 digits");
		assertProblem(NURSE, "<TelephoneNumber>2185550167", "<TelephoneNumber>218555016712345", Severity.WARNING,
				"is too long");
		assertProblem(NURSE, "<CountryCode>1</CountryCode>\n\t\t\t\t<TelephoneNumber>2185550167",
				"<CountryCode>0</CountryCode><TelephoneNumber>2185550167", Severity.WARNING,
				"is not a country calling code");
	}

	@Test
	void emailAndWebAddresses() {
		assertProblem(NURSE, "r.okafor-lindqvist@example.org", "r..okafor-lindqvist@example.org", Severity.WARNING,
				"is not valid");
		assertProblem(PHYSICIAN, "<URL>https://www.example.edu/medicine/cardiology/brannigan-vos</URL>",
				"<URL>www.example.edu/brannigan-vos</URL>", Severity.WARNING, "is not an absolute web address");
	}

	// ----------------------------------------------------------------------------------------------------
	// Helpers
	// ----------------------------------------------------------------------------------------------------

	private static String title(String sample) {
		Result result = MedbiqSamples.check(MedbiqSamples.text(sample));
		return result.type().title(result.documentRoot());
	}

	private static String replace(String sample, String target, String replacement) {
		String text = MedbiqSamples.text(sample).replace("\r\n", "\n");
		assertTrue(text.contains(target), () -> sample + " does not contain " + target);
		return text.replace(target, replacement);
	}

	/** The text of a sample from the start of {@code from} to the end of {@code to}. */
	private static String between(String sample, String from, String to) {
		String text = MedbiqSamples.text(sample).replace("\r\n", "\n");
		int start = text.indexOf(from);
		int end = text.indexOf(to, start);
		assertTrue(start >= 0 && end > start, () -> sample + " has no " + from + "…" + to);
		return text.substring(start, end + to.length());
	}

	private static List<String> rulesProblems(Result result) {
		return result.problems().stream().filter(p -> p.problem().origin() == Origin.RULES)
				.map(p -> p.problem().message()).toList();
	}

	/**
	 * Replaces a text of a sample, checks that the result is still valid against the schemas and the model, and that
	 * it has a problem of a severity whose message contains a fragment.
	 */
	private static void assertProblem(String sample, String target, String replacement, Severity severity,
			String fragment) {
		Result result = assertSchemaValid(sample + " with " + replacement, replace(sample, target, replacement));
		List<String> messages = MedbiqSamples.messages(result, severity);
		assertTrue(messages.stream().anyMatch(message -> message.contains(fragment)),
				() -> "no " + severity + " containing \"" + fragment + "\" in " + rulesProblems(result));
	}

	/** Checks a document and asserts that it has no errors but those of the rules. */
	private static Result assertSchemaValid(String name, String text) {
		Result result = MedbiqSamples.check(text);
		List<String> invalid = result.problems().stream().filter(p -> p.problem().severity() == Severity.ERROR)
				.filter(p -> p.problem().origin() != Origin.RULES).map(p -> p.problem().message()).toList();
		assertEquals(List.of(), invalid, name);
		return result;
	}

	private static void assertMessage(String text, Severity severity, String fragment) {
		Result result = MedbiqSamples.check(text);
		List<String> messages = MedbiqSamples.messages(result, severity);
		assertTrue(messages.stream().anyMatch(message -> message.contains(fragment)),
				() -> "no " + severity + " containing \"" + fragment + "\" in " + rulesProblems(result));
	}
}
