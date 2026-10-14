/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/** Tests the Activity Report 1.0 and 2.0 content: templates, samples, titles, pages and rules. */
class ActivityReportTest {

	private static final String FOLDER = "samples/activity-report/";
	private static final String CME_V1 = FOLDER + "cme-course-completions-v1.xml";
	private static final String CME_V2 = FOLDER + "cme-course-completions-v2.xml";
	private static final String PROGRESS_V1 = FOLDER + "registration-in-progress-v1.xml";
	private static final String PROGRESS_V2 = FOLDER + "registration-in-progress-v2.xml";
	private static final String REMS_V2 = FOLDER + "rems-participants-v2.xml";

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	// ----------------------------------------------------------------------------------------------------
	// Templates, samples, titles and pages
	// ----------------------------------------------------------------------------------------------------

	@Test
	void templatesAreValidAndTitled() {
		for (String type : List.of("activity-report-v1", "activity-report-v2")) {
			String xml = MedbiqSamples.template(type, "Sepsis Essentials");
			assertFalse(xml.contains("${"), type);
			Result result = MedbiqSamples.assertValid(type, xml);
			assertEquals(type, result.type().id());
			assertEquals(List.of(), rulesProblems(result), type);
			assertEquals("Activity report: Sepsis Essentials", result.type().title(result.documentRoot()), type);
		}
	}

	@Test
	void samplesAreValidWithoutRuleProblems() {
		List<String> files = MedbiqSamples.files("samples/activity-report");
		assertEquals(5, files.size(), files::toString);
		for (String file : files) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			assertTrue(result.type().id().startsWith("activity-report-v"), file);
			assertEquals(List.of(), result.problems().stream().map(p -> p.problem().message()).toList(), file);
		}
	}

	@Test
	void titlesAndDescriptions() {
		assertEquals("Activity report: Heart Failure Update 2026", title(CME_V1));
		assertEquals("Activity report: Atrial Fibrillation in Practice 2026", title(CME_V2));
		// Several modules: the activity name, not a module's description
		assertEquals("Activity report: Pediatric Self-Assessment 2026", title(PROGRESS_V1));
		assertEquals("Activity report: Pain Management and Safe Opioid Prescribing", title(REMS_V2));
		String two = MedbiqSamples.text(CME_V1).replace("<ActivityName>Heart Failure Update 2026</ActivityName>",
				"<ActivityName>Other</ActivityName>");
		Result result = MedbiqSamples.check(two);
		assertEquals("3 activity reports", result.type().title(result.documentRoot()));

		Result cme = MedbiqSamples.check(MedbiqSamples.text(CME_V2));
		String description = cme.type().description(cme.documentRoot());
		assertTrue(description.contains("3 reports"), description);
		assertTrue(description.contains("reporting period 2026-01-01 to 2026-06-30"), description);
		assertTrue(description.contains("3 completed"), description);
		assertTrue(description.contains("10.5 Credit"), description);
		Result rems = MedbiqSamples.check(MedbiqSamples.text(REMS_V2));
		assertTrue(rems.type().description(rems.documentRoot()).contains("4 participants"));
	}

	@Test
	void pagesAndSearchFields() {
		var v1 = MedbiqStandards.byId("activity-report-v1");
		var v2 = MedbiqStandards.byId("activity-report-v2");
		assertEquals(List.of("report", "participants", "activities", "status", "credits", "point-of-care"),
				v1.content().pages(v1).stream().map(PageSpec::id).toList());
		assertEquals(List.of("report", "participants", "activities", "status", "credits", "regulation",
				"point-of-care"), v2.content().pages(v2).stream().map(PageSpec::id).toList());
		assertTrue(v1.searchFields().stream().allMatch(group -> group.id().startsWith("ar-")));
		assertTrue(v2.searchFields().stream().anyMatch(group -> group.id().equals("ar-regulation")));
		assertTrue(v2.fieldHelp(List.of("ReportingStartDate")).contains("first day of the reporting period"));
		assertTrue(v1.fieldHelp(List.of("CreditID")).contains("ccid:providerdomain:localidentifier"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules
	// ----------------------------------------------------------------------------------------------------

	@Test
	void reportingPeriodRunsForward() {
		assertProblem(replace(CME_V2, "<ReportingStartDate>2026-01-01", "<ReportingStartDate>2026-12-01"),
				Severity.ERROR, "is after the ReportingEndDate");
	}

	@Test
	void completionDatesWithinReportingPeriod() {
		assertProblem(replace(CME_V2, "<CompletedDateTime>2026-05-11", "<CompletedDateTime>2026-07-11"),
				Severity.WARNING, "is outside the reporting period");
	}

	@Test
	void reportsNotCreatedBeforePeriodOrInFuture() {
		assertProblem(replace(CME_V2, "<DateTimeCreated>2026-07-02", "<DateTimeCreated>2025-12-02"),
				Severity.WARNING, "before their reporting period starts");
		assertProblem(replace(CME_V1, "<DateTimeCreated>2026-03-20", "<DateTimeCreated>2999-03-20"),
				Severity.WARNING, "is in the future");
	}

	@Test
	void completedModuleHasCompletionDate() {
		assertProblem(replace(CME_V1, "<CompletedDateTime>2026-03-15T08:47:00-05:00</CompletedDateTime>", ""),
				Severity.WARNING, "is Completed but has no CompletedDateTime");
		assertProblem(replace(PROGRESS_V1, "<Status>Completed</Status>", "<Status>Registered</Status>"),
				Severity.WARNING, "has a CompletedDateTime but its Status is Registered");
	}

	@Test
	void expiredAndRegisteredModulesHaveDates() {
		assertProblem(replace(PROGRESS_V1, "<ExpiredDateTime>2026-04-01T00:00:00-05:00</ExpiredDateTime>", ""),
				Severity.INFO, "is Expired; give the ExpiredDateTime");
		// The first module still has its StartDateTime; the second has no date left
		assertProblem(replace(PROGRESS_V2, "<RegisteredDateTime>2026-09-16T19:28:00-05:00</RegisteredDateTime>", ""),
				Severity.INFO, "is Registered; give the RegisteredDateTime");
	}

	@Test
	void moduleDatesInOrder() {
		assertProblem(replace(CME_V1, "<RegisteredDateTime>2026-02-02", "<RegisteredDateTime>2026-04-02"),
				Severity.ERROR, "is before the RegisteredDateTime");
		assertProblem(replace(CME_V1, "<EndDateTime>2026-03-14T16:30", "<EndDateTime>2026-03-13T16:30"),
				Severity.ERROR, "the EndDateTime");
		assertProblem(replace(PROGRESS_V1, "<DueDateTime>2026-12-31T23:59:59-06:00",
				"<DueDateTime>2026-05-01T00:00:00-05:00"), Severity.WARNING, "is due (DueDateTime");
	}

	@Test
	void completedModuleReportsCreditsOffered() {
		String text = MedbiqSamples.text(PROGRESS_V1).replaceFirst("(?s)<CreditCertificate>.*?</CreditCertificate>", "");
		assertProblem(text, Severity.WARNING, "but no CreditCertificate is reported");
	}

	@Test
	void creditsOnlyForCompletedModulesWithCredits() {
		assertProblem(replace(PROGRESS_V1, "<Status>Completed</Status>", "<Status>Expired</Status>"),
				Severity.WARNING, "Credits are received for completing a module");
		assertProblem(replace(PROGRESS_V1, "<hx:creditsAvailable>yes", "<hx:creditsAvailable>no"),
				Severity.WARNING, "no credits are available");
	}

	@Test
	void creditsNotAboveCreditsAvailable() {
		String text = MedbiqSamples.text(CME_V2);
		int certificate = text.indexOf("<CreditID>ccid:acadianaheart.org:104233.af02.amapra1");
		int credits = text.lastIndexOf("3.75", certificate);
		text = text.substring(0, credits) + "5" + text.substring(credits + "3.75".length());
		assertProblem(text, Severity.WARNING, "more than the 3.75 credits its Healthcare LOM metadata offers");
	}

	@Test
	void creditsReceivedArePositive() {
		String text = MedbiqSamples.text(PROGRESS_V1);
		int last = text.lastIndexOf("<hx:numberOfCredits>5</hx:numberOfCredits>");
		assertProblem(text.substring(0, last) + "<hx:numberOfCredits>0</hx:numberOfCredits>"
				+ text.substring(last + "<hx:numberOfCredits>5</hx:numberOfCredits>".length()), Severity.WARNING,
				"reports 0 credits received");
	}

	@Test
	void creditFocusWithinCertificate() {
		assertProblem(replace(CME_V1, "<NumberOfCredits>4.5", "<NumberOfCredits>7.5"), Severity.WARNING,
				"more than the 6.5 credits of its credit certificate");
	}

	@Test
	void oneCertificatePerTypeOfCredit() {
		String text = MedbiqSamples.text(PROGRESS_V1);
		Matcher matcher = Pattern.compile("(?s)<CreditCertificate>.*?</CreditCertificate>").matcher(text);
		assertTrue(matcher.find());
		String copy = matcher.group().replace("55120.psa2026.asthma", "55120.psa2026.asthma.copy");
		text = text.substring(0, matcher.end()) + copy + text.substring(matcher.end());
		assertProblem(text, Severity.WARNING, "more than one CreditCertificate for \"AMA PRA Category 1 Credit\"");
	}

	@Test
	void creditIdsAreWellFormedAndUnique() {
		assertProblem(replace(PROGRESS_V1, "ccid:gulfcoastpeds.org:55120.psa2026.asthma", "55120-asthma"),
				Severity.WARNING, "does not use the Credit Certificate Identifier format");
		assertProblem(replace(CME_V1, "ccid:acadianaheart.org:118502.hf01.amapra1",
				"ccid:acadianaheart.org:104233.hf01.amapra1"), Severity.ERROR, "is used more than once");
	}

	@Test
	void aridsAreWellFormedAndUnique() {
		assertProblem(replace(CME_V2, "arid=\"arid:acadianaheart.org:2026H1-0007\"", "arid=\"2026H1-0007\""),
				Severity.WARNING, "does not use the arid Uniform Resource Name format");
		assertProblem(replace(CME_V2, "arid:acadianaheart.org:2026H1-0007-2", "arid:acadianaheart.org:2026H1-0007-1"),
				Severity.ERROR, "is used more than once");
	}

	@Test
	void membersAreIdentified() {
		assertProblem(replace(CME_V1, "<m:UniqueID domain=\"acadianaheart.org\">AHI-118502</m:UniqueID>", ""),
				Severity.WARNING, "The Member has no UniqueID");
		String text = MedbiqSamples.text(PROGRESS_V1).replaceFirst("(?s)<Member>.*?</Member>", "<Member/>");
		assertProblem(text, Severity.ERROR, "The Member is not identified");
	}

	@Test
	void oneMemberPerReport() {
		String text = MedbiqSamples.text(CME_V2);
		Matcher matcher = Pattern.compile("(?s)<Member>.*?</Member>").matcher(text);
		assertTrue(matcher.find());
		text = text.substring(0, matcher.end()) + matcher.group().replace("AHI-104233", "AHI-999999")
				+ text.substring(matcher.end());
		assertProblem(text, Severity.ERROR, "has more than one Member");
	}

	@Test
	void noDuplicates() {
		assertProblem(replace(CME_V1, "AHI-118502", "AHI-104233"), Severity.WARNING, "has more than one ActivityReport");
		assertProblem(replace(REMS_V2, "L-204702", "L-204611"), Severity.WARNING, "is listed more than once");
		assertProblem(replace(PROGRESS_V1, "PSA-2026-NEONATAL", "PSA-2026-ASTHMA"), Severity.WARNING,
				"is reported twice in the activity");
		String text = MedbiqSamples.text(PROGRESS_V2);
		Matcher matcher = Pattern.compile("(?s)<Activity activityID.*?</Activity>").matcher(text);
		assertTrue(matcher.find());
		text = text.substring(0, matcher.end()) + matcher.group() + text.substring(matcher.end());
		assertProblem(text, Severity.WARNING, "is reported twice in the same ActivityReport");
	}

	@Test
	void launchActionRecommendedValues() {
		assertProblem(replace(PROGRESS_V2, "<LaunchAction>Activity", "<LaunchAction>Resume"), Severity.INFO,
				"is not one of the recommended values");
	}

	// ----------------------------------------------------------------------------------------------------
	// Helpers
	// ----------------------------------------------------------------------------------------------------

	private static String title(String sample) {
		Result result = MedbiqSamples.check(MedbiqSamples.text(sample));
		return result.type().title(result.documentRoot());
	}

	private static String replace(String sample, String target, String replacement) {
		String text = MedbiqSamples.text(sample);
		assertTrue(text.contains(target), () -> sample + " does not contain " + target);
		return text.replace(target, replacement);
	}

	private static List<String> rulesProblems(Result result) {
		return result.problems().stream().filter(p -> p.problem().origin() == Origin.RULES)
				.map(p -> p.problem().message()).toList();
	}

	private static void assertProblem(String text, Severity severity, String fragment) {
		Result result = MedbiqSamples.check(text);
		List<String> messages = MedbiqSamples.messages(result, severity);
		assertTrue(messages.stream().anyMatch(message -> message.contains(fragment)),
				() -> "no " + severity + " containing \"" + fragment + "\" in " + rulesProblems(result));
	}
}
