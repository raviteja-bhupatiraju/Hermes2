/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/**
 * Tests the content of Medical Education Metrics (MEMS) 1.0 and the MEMS Survey Items 1.0: templates, samples,
 * titles, pages, search fields, help and the rules.
 */
class EducationMetricsTest {

	private static final String METRICS = "medical-education-metrics-v1";
	private static final String SURVEY = "survey-items-v1";
	private static final String FOLDER = "samples/education-metrics/";
	private static final String ONLINE = "online-cme-course-report.xml";
	private static final String WORKSHOP = "live-workshop-report.xml";
	private static final String MEDBIQ_ITEMS = "medbiq-survey-items.xml";
	private static final String PROVIDER_ITEMS = "provider-evaluation-items.xml";

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	private static String sample(String file) {
		return MedbiqSamples.text(FOLDER + file);
	}

	private static String title(Result result) {
		return result.type().title(result.resource().getContents().getFirst());
	}

	/** The messages of the rule problems of a severity. */
	private static List<String> rules(Result result, Severity severity) {
		return result.problems().stream()
				.filter(p -> p.problem().origin() == Origin.RULES && p.problem().severity() == severity)
				.map(p -> p.problem().message()).toList();
	}

	/** Asserts that a document has a rule problem of a severity whose message contains a text. */
	private static void assertProblem(String text, Severity severity, String fragment) {
		Result result = MedbiqSamples.check(text);
		List<String> messages = rules(result, severity);
		assertTrue(messages.stream().anyMatch(m -> m.contains(fragment)),
				() -> "expected " + severity + " '" + fragment + "' in " + messages);
	}

	private static String replace(String text, String target, String replacement) {
		assertTrue(text.contains(target), () -> "sample lacks: " + target);
		return text.replaceFirst(Pattern.quote(target), Matcher.quoteReplacement(replacement));
	}

	// ----------------------------------------------------------------------------------------------------
	// Templates, samples, titles, pages, search fields, help
	// ----------------------------------------------------------------------------------------------------

	@Test
	void templatesAreValid() {
		Result metrics = MedbiqSamples.assertValid(METRICS, MedbiqSamples.template(METRICS, "Sepsis & shock update"));
		assertEquals(METRICS, metrics.type().id());
		assertEquals("Sepsis & shock update", title(metrics));
		assertTrue(MedbiqSamples.messages(metrics, Severity.WARNING).isEmpty(),
				() -> "warnings: " + MedbiqSamples.messages(metrics, Severity.WARNING));
		Result items = MedbiqSamples.assertValid(SURVEY, MedbiqSamples.template(SURVEY, "Evaluation items"));
		assertEquals(SURVEY, items.type().id());
		assertEquals("1 survey item", title(items));
		assertTrue(MedbiqSamples.messages(items, Severity.WARNING).isEmpty(),
				() -> "warnings: " + MedbiqSamples.messages(items, Severity.WARNING));
	}

	@Test
	void samplesAreValidWithoutRuleWarnings() {
		List<String> files = MedbiqSamples.files("samples/education-metrics");
		assertEquals(4, files.size(), files::toString);
		for (String file : files) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			assertTrue(rules(result, Severity.WARNING).isEmpty(), () -> file + ": " + rules(result, Severity.WARNING));
			assertTrue(rules(result, Severity.INFO).isEmpty(), () -> file + ": " + rules(result, Severity.INFO));
		}
	}

	@Test
	void titlesAndDescriptions() {
		Result online = MedbiqSamples.check(sample(ONLINE));
		assertEquals(METRICS, online.type().id());
		assertEquals("Recognizing and Treating Opioid Use Disorder in Primary Care", title(online));
		String description = online.type().description(online.resource().getContents().getFirst());
		assertNotNull(description);
		assertTrue(description.contains("2026-01-01 to 2026-03-31") && description.contains("1840 registered"),
				description);
		Result workshop = MedbiqSamples.check(sample(WORKSHOP));
		assertEquals("Pediatric Asthma Action Plans: A Hands-on Workshop", title(workshop));
		Result items = MedbiqSamples.check(sample(MEDBIQ_ITEMS));
		assertEquals(SURVEY, items.type().id());
		assertEquals("4 survey items and 1 comparison", title(items));
		// Without an activity title, the reporting period names the report.
		String untitled = sample(WORKSHOP).replaceFirst("(?s)<lom:title>.*?</lom:title>", "");
		assertEquals("Medical education metrics, 2025-11-08 to 2025-12-15", title(MedbiqSamples.check(untitled)));
	}

	@Test
	void pagesSearchFieldsAndHelp() {
		Set<String> groupIds = new HashSet<>();
		for (String id : List.of(METRICS, SURVEY)) {
			MedbiqDocumentType type = MedbiqStandards.byId(id);
			List<PageSpec> pages = type.content().pages(type);
			assertFalse(pages.isEmpty(), id);
			Set<String> pageIds = new HashSet<>();
			for (PageSpec page : pages) {
				assertTrue(pageIds.add(page.id()), () -> id + ": duplicate page " + page.id());
				assertFalse(page.sections().isEmpty(), page::id);
			}
			for (FieldGroup group : type.searchFields()) {
				assertTrue(group.id().startsWith(id.equals(METRICS) ? "mems-" : "si-"), group::id);
				assertTrue(groupIds.add(group.id()), group::id);
			}
			Map<String, String> help = type.content().help(type);
			assertNotNull(type.fieldHelp(List.of("SurveyQuestion")), id);
			assertNotNull(type.fieldHelp(List.of("source")), id);
			assertTrue(help.values().stream().noneMatch(String::isBlank), id);
		}
		MedbiqDocumentType metrics = MedbiqStandards.byId(METRICS);
		assertEquals(List.of("report", "activity", "participation", "evaluation", "assessment", "extensions"),
				metrics.content().pages(metrics).stream().map(PageSpec::id).toList());
		for (String name : List.of("ReportingStartDate", "CommercialSupportAmount", "ScaleData", "ScoreMethod",
				"NumberOfParticipantsCompletingActivity", "XtensibleInfo", "Timing", "currency", "correct")) {
			assertNotNull(metrics.fieldHelp(List.of(name)), name);
		}
		MedbiqDocumentType items = MedbiqStandards.byId(SURVEY);
		assertEquals(List.of("items", "comparisons"), items.content().pages(items).stream().map(PageSpec::id).toList());
		for (String name : List.of("SurveyItem", "Responses", "Scale", "IntendedTiming", "points", "label")) {
			assertNotNull(items.fieldHelp(List.of(name)), name);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules of metrics reports
	// ----------------------------------------------------------------------------------------------------

	@Test
	void reportingPeriod() {
		String online = sample(ONLINE);
		assertProblem(replace(online, "<ReportingEndDate>2026-03-31", "<ReportingEndDate>2025-12-31"), Severity.ERROR,
				"is before the ReportingStartDate");
		String future = LocalDate.now().plusYears(1).toString();
		assertProblem(replace(replace(online, "<ReportingStartDate>2026-01-01", "<ReportingStartDate>" + future),
				"<ReportingEndDate>2026-03-31", "<ReportingEndDate>" + future), Severity.WARNING, "in the future");
		assertProblem(replace(online, "<hx:releaseDate>2025-09-15", "<hx:releaseDate>2026-06-01"), Severity.WARNING,
				"after the end of the reporting period");
		assertProblem(replace(sample(WORKSHOP), "<hx:startDateTime>2025-11-08", "<hx:startDateTime>2026-01-10"),
				Severity.WARNING, "The activity starts on 2026-01-10");
	}

	@Test
	void activityDescription() {
		String online = sample(ONLINE);
		assertProblem(online.replaceFirst("(?s)<lom:identifier>.*?</lom:identifier>", ""), Severity.WARNING,
				"has no identifier");
		assertProblem(online.replaceFirst("(?s)<lom:title>.*?</lom:title>", ""), Severity.WARNING, "has no title");
		String workshop = sample(WORKSHOP);
		assertProblem(replace(workshop, "<Expenses currency=\"USD\">", "<Expenses currency=\"Dollar\">"),
				Severity.ERROR, "limited to 3-character strings");
		assertProblem(replace(workshop, "<Expenses currency=\"USD\">", "<Expenses currency=\"usd\">"),
				Severity.WARNING, "is not an ISO 4217 code");
		assertProblem(replace(workshop, "<Expenses currency=\"USD\">", "<Expenses>"), Severity.WARNING,
				"Expenses has no currency");
		assertProblem(replace(workshop, " supportSource=\"Northshore Respiratory Devices, Inc.\"", ""),
				Severity.WARNING, "has no supportSource");
		assertProblem(replace(workshop, "<hx:commercialSupport>yes", "<hx:commercialSupport>no"), Severity.WARNING,
				"no commercial support");
		assertProblem(replace(workshop, "<OtherIncome currency=\"USD\">8400", "<OtherIncome currency=\"USD\">-8400"),
				Severity.ERROR, "can't be negative");
		assertProblem(online.replaceFirst("(?s)<KnowledgeAssessment>.*?</KnowledgeAssessment>", "")
				.replaceFirst("(?s)<ComparisonData>.*?</ComparisonData>", ""), Severity.INFO,
				"ChangesInCompetenceMeasured is true");
	}

	@Test
	void participationCounts() {
		String online = sample(ONLINE);
		assertProblem(replace(online, "<NumberOfParticipantsCompletingActivity>1312",
				"<NumberOfParticipantsCompletingActivity>2000"), Severity.WARNING,
				"only participants can complete the activity");
		assertProblem(replace(online, "<NumberOfParticipantsReceivingCredit>1275",
				"<NumberOfParticipantsReceivingCredit>1900"), Severity.WARNING, "only participants can receive credit");
		assertProblem(replace(online, "<NumberOfParticipantsReceivingCredit>1275",
				"<NumberOfParticipantsReceivingCredit>1400"), Severity.INFO, "credit is usually awarded for completing");
		assertProblem(replace(online, "<TargetedAudience>12000", "<TargetedAudience>1000"), Severity.INFO,
				"larger than the TargetedAudience");
		assertProblem(replace(online, "<NumberOfSuccessfulPageRequests>48760", "<NumberOfSuccessfulPageRequests>2000"),
				Severity.WARNING, "every host or visitor requested at least one page");
		assertProblem(replace(online, "profession=\"physician\">1402", "profession=\"physician\">1502"),
				Severity.WARNING, "The ParticipantsByProfession counts add up to 1940");
		assertProblem(replace(online, "profession=\"nurse practitioner\"", "profession=\"Physician\""),
				Severity.WARNING, "is counted twice");
		assertProblem(replace(online, " category=\"returning participant\"", ""), Severity.ERROR,
				"has no category attribute");
		assertProblem(replace(online, "specialty=\"internal medicine\">590", "specialty=\"internal medicine\">1590"),
				Severity.INFO, "The ParticipantsBySpecialty counts add up to");
		assertProblem(replace(online, "<TotalRespondents>45</TotalRespondents>",
				"<TotalRespondents>-45</TotalRespondents>"), Severity.ERROR, "TotalRespondents is -45");
		String empty = MedbiqSamples.template(METRICS, "Empty").replaceFirst("(?s)<ParticipationMetrics>.*?"
				+ "</ParticipationMetrics>", "<ParticipationMetrics/>");
		assertProblem(empty, Severity.WARNING, "ParticipationMetrics contains no data");
	}

	@Test
	void creditsAwarded() {
		String online = sample(ONLINE);
		assertProblem(replace(online, "<hx:numberOfCredits>1620", "<hx:numberOfCredits>2620"), Severity.WARNING,
				"more than the 1275 participants receiving credit can have earned");
		assertProblem(replace(online, "<hx:numberOfCredits>292.5</hx:numberOfCredits>", ""), Severity.WARNING,
				"CreditsAwarded has no numberOfCredits");
		assertProblem(replace(online, "<hx:creditType>CNE</hx:creditType>\n\t\t\t<hx:creditUnit>Contact Hour",
				"<hx:creditUnit>Contact Hour"), Severity.WARNING, "CreditsAwarded has no creditType");
		assertProblem(replace(online, "<NumberOfParticipantsReceivingCredit>1275",
				"<NumberOfParticipantsReceivingCredit>0"), Severity.WARNING,
				"NumberOfParticipantsReceivingCredit is 0");
		assertProblem(online.replaceFirst("(?s)<NumberOfParticipantsReceivingCredit>.*?"
				+ "</NumberOfParticipantsReceivingCredit>", ""), Severity.INFO,
				"report the NumberOfParticipantsReceivingCredit");
		assertProblem(replace(online, "<hx:creditsAvailable>yes", "<hx:creditsAvailable>no"), Severity.WARNING,
				"no credits are available");
	}

	@Test
	void surveyItemData() {
		String workshop = sample(WORKSHOP);
		assertProblem(replace(workshop, "<Yes>23</Yes>", "<Yes>230</Yes>"), Severity.WARNING,
				"231 respondents, more than the 28 participants");
		assertProblem(replace(workshop, "type=\"not confident/very confident\" points=\"4\"",
				"type=\"not confident/very confident\" points=\"3\""), Severity.ERROR,
				"should only use subelements corresponding to the number of points");
		assertProblem(replace(workshop, "type=\"not confident/very confident\" points=\"4\"",
				"type=\"not confident/very confident\" points=\"5\""), Severity.WARNING,
				"give the number of respondents for each point");
		assertProblem(replace(workshop, "type=\"not confident/very confident\" points=\"4\"",
				"type=\"not confident/very confident\" points=\"4.5\""), Severity.ERROR, "must be an integer");
		assertProblem(replace(workshop, "<Four label=\"Very confident\">", "<Four>"), Severity.WARNING,
				"Label the first and last point of the ScaleData");
		String online = sample(ONLINE);
		assertProblem(replace(online, "<TotalRespondents>874</TotalRespondents>",
				"<TotalRespondents>1874</TotalRespondents>"), Severity.WARNING, "more than the 1840 participants");
		assertProblem(replace(online, "<ResponseText>Neither</ResponseText>",
				"<ResponseText>buprenorphine</ResponseText>"), Severity.WARNING, "is listed twice");
		String single = workshop.replaceFirst("(?s)(<ComparisonData>\\s*<SurveyItemDataForComparison>.*?"
				+ "</SurveyItemDataForComparison>)\\s*<SurveyItemDataForComparison>.*?</SurveyItemDataForComparison>",
				"$1");
		assertProblem(single, Severity.WARNING, "two or more complementary or related survey questions");
		assertProblem(replace(workshop, "<Timing duration=\"P30D\">After</Timing>\n\t\t\t</SurveyItemDataForComparison>",
				"<Timing duration=\"30 days\">After</Timing>\n\t\t\t</SurveyItemDataForComparison>"), Severity.WARNING,
				"is not an XML Schema duration");
		assertProblem(replace(online, "source=\"gulfcoast-ce.example.org\" id=\"E-09\"", "id=\"E-09\""),
				Severity.INFO, "but no source");
		assertProblem(replace(online, "source=\"gulfcoast-ce.example.org\" id=\"E-09\"",
				"source=\"gulfcoast-ce.example.org\" id=\"E-04\""), Severity.WARNING, "is reported twice");
		assertProblem(replace(online, "<SurveyQuestion>The course achieved its educational objectives.",
				"<SurveyQuestion> "), Severity.ERROR, "The SurveyQuestion is blank");
	}

	@Test
	void medbiqSurveyItemReferences() {
		String online = sample(ONLINE);
		assertProblem(replace(online, "<SurveyItemData source=\"medbiq.org\" id=\"10\">",
				"<SurveyItemData source=\"medbiq.org\" id=\"12\">"), Severity.WARNING,
				"The survey item 12 of source medbiq.org is not defined");
		assertProblem(replace(online, "<SurveyQuestion>The course achieved its educational objectives.",
				"<SurveyQuestion>The course met its stated objectives."), Severity.INFO,
				"differs from the MedBiquitous survey item 10");
		assertProblem(replace(online, "<One label=\"Strongly agree\" source=\"medbiq.org\" id=\"20\">",
				"<One label=\"Strongly agree\" source=\"medbiq.org\" id=\"25\">"), Severity.WARNING,
				"The response 25 of source medbiq.org is not defined");
		assertProblem(replace(online, "<One label=\"Strongly agree\" source=\"medbiq.org\" id=\"20\">",
				"<One label=\"Strongly agree\" source=\"medbiq.org\" id=\"30\">"), Severity.WARNING,
				"is \"Agree\" (Two) in the MedBiquitous survey items");
		assertProblem(replace(online, "<One label=\"Low\" source=\"medbiq.org\" id=\"150\">",
				"<One label=\"Low\" source=\"medbiq.org\" id=\"80\">"), Severity.WARNING,
				"belongs to the MedBiquitous survey item 70, not to the survey item 150");
		String yesNo = online.replaceFirst("(?s)(<SurveyItemData source=\"medbiq.org\" id=\"10\">.*?<ResponseData>)"
				+ ".*?(</ResponseData>)", "$1<YesNoData><Yes>1000</Yes><No>246</No></YesNoData>$2");
		assertProblem(yesNo, Severity.WARNING, "has Scale responses, but the data are YesNoData");
	}

	@Test
	void knowledgeAssessment() {
		String workshop = sample(WORKSHOP);
		assertProblem(replace(workshop, "<AssessmentResponseItemData correct=\"true\">",
				"<AssessmentResponseItemData correct=\"false\">"), Severity.WARNING, "No response of the assessment item");
		assertProblem(replace(workshop, "<ScoreValue>100</ScoreValue>", "<ScoreValue>110</ScoreValue>"),
				Severity.ERROR, "a percent of items correct is a number between 0 and 100");
		assertProblem(replace(workshop, "<ScoreMethod>Percent</ScoreMethod>", "<ScoreMethod>Stanine</ScoreMethod>"),
				Severity.ERROR, "a stanine is a score from 1 to 9");
		assertProblem(replace(workshop, "<ScoreMethod>Percent</ScoreMethod>", "<ScoreMethod>Raw</ScoreMethod>")
				.replace("<ScoreValue>60</ScoreValue>", "<ScoreValue>sixty</ScoreValue>"), Severity.ERROR,
				"a raw score is a number");
		assertProblem(replace(workshop, "<ScoreMethod>Percent</ScoreMethod>", "<ScoreMethod>Local</ScoreMethod>")
				.replaceFirst("(?s)<Description>Ten-item.*?</Description>", ""), Severity.INFO,
				"define the local method in the Description");
		assertProblem(replace(workshop, "<RegisteredParticipants>28", "<RegisteredParticipants>20")
				.replace("<NumberOfParticipantsReceivingCredit>24", "<NumberOfParticipantsReceivingCredit>20")
				.replace("<NumberOfParticipantsCompletingActivity>24", "<NumberOfParticipantsCompletingActivity>20"),
				Severity.WARNING, "24 scores, more than the 20 participants");
		assertProblem(replace(workshop, "<TotalRespondents>20</TotalRespondents>",
				"<TotalRespondents>40</TotalRespondents>"), Severity.WARNING, "40 respondents, more than the 28");
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules of survey items documents
	// ----------------------------------------------------------------------------------------------------

	@Test
	void surveyItemsRules() {
		String items = sample(PROVIDER_ITEMS);
		assertProblem(replace(items, "id=\"E-05\"", "id=\"E-04\""), Severity.ERROR, "is used by another survey item");
		assertProblem(replace(items, "<Scale type=\"likert\" points=\"5\">", "<Scale type=\"likert\" points=\"6\">"),
				Severity.ERROR, "define a label and identifier for each point");
		assertProblem(replace(items, "<Scale type=\"likert\" points=\"5\">", "<Scale type=\"likert\" points=\"4\">"),
				Severity.ERROR, "should only use subelements corresponding to the number of points");
		assertProblem(replace(items, "<Scale type=\"likert\" points=\"5\">", "<Scale type=\"likert\" points=\"11\">"),
				Severity.ERROR, "no higher than 10");
		assertProblem(replace(items, "<Scale type=\"likert\" points=\"5\">", "<Scale points=\"5\">"), Severity.WARNING,
				"The Scale has no type");
		assertProblem(replace(items, "<Scale type=\"likert\" points=\"5\">", "<Scale type=\"likert\">"),
				Severity.WARNING, "has no points attribute");
		assertProblem(replace(items, "<One label=\"Strongly agree\" source", "<One source"), Severity.WARNING,
				"Label the first and last point of the Scale");
		assertProblem(replace(items, "id=\"C4-4\"", "id=\"C4-1\""), Severity.WARNING, "identifies another response");
		assertProblem(replace(items, "<SurveyQuestion>The content of the activity is relevant to my practice.",
				"<SurveyQuestion> "), Severity.ERROR, "The SurveyQuestion is blank");
		String comparison = items.replaceFirst("(?s)<SurveyItemForComparison source=\"gulfcoast-ce.example.org\" "
				+ "id=\"C-11b\">.*?</SurveyItemForComparison>", "");
		assertProblem(comparison, Severity.WARNING, "a set of related survey items");
		assertProblem(replace(items, "<SurveyItem source=\"gulfcoast-ce.example.org\" id=\"E-09\">",
				"<SurveyItem id=\"E-09\">"), Severity.INFO, "but no source");
		String medbiq = sample(MEDBIQ_ITEMS);
		assertProblem(replace(medbiq, "<ResponseItem source=\"medbiq.org\" id=\"280\">Cultural",
				"<ResponseItem source=\"medbiq.org\" id=\"280\"> "), Severity.ERROR, "A ResponseItem is empty");
		assertProblem(replace(medbiq, "id=\"290\">Economic", "id=\"290\">Cultural"), Severity.WARNING,
				"is listed twice");
		assertProblem(replace(medbiq, "id=\"290\">Economic", "id=\"295\">Economic"), Severity.WARNING,
				"The response 295 of source medbiq.org is not defined");
		assertProblem(medbiq.replaceFirst("(?s)<ResponseItem source=\"medbiq.org\" id=\"250\">.*?"
				+ "<ResponseItem source=\"medbiq.org\" id=\"270\">Other</ResponseItem>", ""), Severity.WARNING,
				"offers at least two response choices");
		assertProblem(replace(medbiq, "<SurveyItem source=\"medbiq.org\" id=\"10\">",
				"<SurveyItem source=\"medbiq.org\" id=\"11\">"), Severity.WARNING, "is not defined");
		assertProblem(MedbiqSamples.template(SURVEY, "x").replaceFirst("(?s)<SurveyItem>.*</SurveyItem>", ""),
				Severity.WARNING, "defines no survey items");
	}
}
