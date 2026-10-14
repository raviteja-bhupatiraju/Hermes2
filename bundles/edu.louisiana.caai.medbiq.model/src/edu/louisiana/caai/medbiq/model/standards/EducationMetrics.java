/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import static edu.louisiana.caai.medbiq.model.forms.PageSpec.auto;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.field;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.group;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.page;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.repeat;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.section;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.suggest;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;

/**
 * Medical Education Metrics (MEMS) 1.0 (ANSI/MEDBIQ ME.10.1-2009, type {@value #METRICS}) and the MEMS Survey Items
 * 1.0 (type {@value #SURVEY_ITEMS}).
 * <p>
 * A metrics report ({@code MedicalEducationMetrics}) holds the evaluation data of one educational activity: the
 * reporting period, the activity (Healthcare LOM and financial data), how participants evaluated it (survey item data
 * and comparisons), knowledge assessment results and participation metrics. A survey items document
 * ({@code SurveyItems}) defines survey questions and their response structures, identified by source and id, which
 * metrics reports refer to. Survey items published by MedBiquitous (source {@value #MEDBIQ}) are known to the rules,
 * so that references to them can be resolved.
 */
public final class EducationMetrics implements StandardContent {

	/** The type id of metrics reports. */
	public static final String METRICS = "medical-education-metrics-v1";

	/** The type id of survey items documents. */
	public static final String SURVEY_ITEMS = "survey-items-v1";

	/** The source of the survey items of the MedBiquitous Survey Items specification. */
	static final String MEDBIQ = "medbiq.org";

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** The point elements of a scale, in order. */
	static final List<String> POINTS = List.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
			"Ten");

	private static final String LOM_TITLE = "ActivityDescription/lom/general/title/string";
	private static final String EDUCATION = "ActivityDescription/lom/healthcareMetadata/healthcareEducation";

	private static boolean isSurveyItems(MedbiqDocumentType type) {
		return SURVEY_ITEMS.equals(type.id());
	}

	// ----------------------------------------------------------------------------------------------------
	// Titles and descriptions
	// ----------------------------------------------------------------------------------------------------

	@Override
	public String title(MedbiqDocumentType type, EObject rootElement) {
		if (isSurveyItems(type)) {
			return surveySummary(rootElement);
		}
		String title = Paths.text(rootElement, LOM_TITLE);
		if (title != null) {
			return title;
		}
		String start = Paths.text(rootElement, "ReportDescription/ReportingStartDate");
		String end = Paths.text(rootElement, "ReportDescription/ReportingEndDate");
		return start == null || end == null ? "Medical education metrics report"
				: "Medical education metrics, " + start + " to " + end;
	}

	@Override
	public String description(MedbiqDocumentType type, EObject rootElement) {
		if (isSurveyItems(type)) {
			List<String> questions = Paths.texts(rootElement, "SurveyItem/SurveyQuestion");
			return questions.isEmpty() ? null : "First question: " + questions.getFirst();
		}
		List<String> parts = new ArrayList<>();
		String start = Paths.text(rootElement, "ReportDescription/ReportingStartDate");
		String end = Paths.text(rootElement, "ReportDescription/ReportingEndDate");
		if (start != null && end != null) {
			parts.add("reporting period " + start + " to " + end);
		}
		String category = Paths.text(rootElement, "ReportDescription/RespondentCategory");
		if (category != null) {
			parts.add("respondents: " + category);
		}
		String registered = Paths.text(rootElement, "ParticipationMetrics/RegisteredParticipants");
		if (registered != null) {
			parts.add(registered + " registered participants");
		}
		String completing = Paths.text(rootElement, "ParticipationMetrics/NumberOfParticipantsCompletingActivity");
		if (completing != null) {
			parts.add(completing + " completed");
		}
		int surveyItems = Paths.objects(rootElement, "ParticipantActivityEvaluation/SurveyItemData").size()
				+ Paths.objects(rootElement, "ParticipantActivityEvaluation/ComparisonData/SurveyItemDataForComparison")
						.size();
		if (surveyItems > 0) {
			parts.add(plural(surveyItems, "survey item"));
		}
		int assessment = Paths.objects(rootElement, "KnowledgeAssessment/MultipleChoiceAssessmentItemData").size()
				+ Paths.objects(rootElement, "KnowledgeAssessment/AssessmentScoreData").size();
		if (assessment > 0) {
			parts.add(plural(assessment, "assessment result"));
		}
		if (parts.isEmpty()) {
			return null;
		}
		String text = String.join(", ", parts);
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	private static String surveySummary(EObject root) {
		int items = Paths.objects(root, "SurveyItem").size();
		int comparisons = Paths.objects(root, "Comparison").size();
		String summary = plural(items, "survey item");
		if (comparisons > 0) {
			summary += " and " + plural(comparisons, "comparison");
		}
		return summary;
	}

	private static String plural(int count, String noun) {
		return count + " " + noun + (count == 1 ? "" : "s");
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		return isSurveyItems(type) ? surveyPages() : metricsPages();
	}

	private static List<PageSpec> metricsPages() {
		return List.of(
				page("report", "Report Description", "Report", "The scope of the report: reporting period and respondents",
						"general",
						section("Reporting period", "ReportDescription provides details about the scope of the report "
								+ "data: the first and last day of the reporting period.", "ReportDescription",
								field("ReportingStartDate", "Start date"), field("ReportingEndDate", "End date")),
						section("Respondents", "Use a respondent category when the report covers only some of the "
								+ "participants, for example when reporting evaluation data by profession.",
								"ReportDescription", field("RespondentCategory", "Respondent category"),
								field("RespondentCategory/@source", "Vocabulary (source)"),
								field("RespondentCategory/@id", "Term id"))),
				page("activity", "Activity Description", "Activity",
						"The educational activity evaluated: Healthcare LOM metadata, credits, intent and finances",
						"healthcare-education",
						section("Activity", "The activity is described with Healthcare LOM; identify it uniquely with "
								+ "an identifier (a URL or URN).", "ActivityDescription",
								field("lom", "Description (Healthcare LOM)")),
						section("Credits offered", "The continuing education credits the activity offers "
								+ "(Healthcare LOM healthcare education metadata).", EDUCATION,
								field("creditsAvailable", "Credits available"),
								repeat("credits", "Credits", "creditType", field("creditType", "Credit type"),
										field("numberOfCredits", "Number of credits"), field("creditUnit", "Unit"),
										field("accreditingBody", "Accrediting body"),
										field("activityCertification", "Certification"), field("pacing"),
										field("accreditedProvider", "Accredited provider"),
										field("releaseDate", "Release date"),
										field("expirationDate", "Expiration date")),
								field("startDateTime", "Starts"), field("endDateTime", "Ends"),
								field("participationModality", "Participation modality"),
								field("activityDelivery", "Delivery"), field("commercialSupport", "Commercial support")),
						section("Intent and outcomes measured", "Whether the activity is designed to change "
								+ "competence, performance or patient outcomes, and whether such changes were measured.",
								"ActivityDescription",
								field("DesignedToChangeCompetence", "Designed to change competence"),
								field("DesignedToChangePerformance", "Designed to change performance"),
								field("DesignedToChangePatientOutcomes", "Designed to change patient outcomes"),
								field("ChangesInCompetenceMeasured", "Changes in competence measured"),
								field("ChangesInPerformanceMeasured", "Changes in performance measured"),
								field("ChangesInPatientOutcomesMeasured", "Changes in patient outcomes measured")),
						section("Finances", "Amounts are integers in the currency given (ISO 4217 code such as USD); "
								+ "list commercial support per source.", "ActivityDescription",
								repeat("CommercialSupportAmount", "Commercial support", "@supportSource",
										field("@supportSource", "Support source"), field("@currency", "Currency")),
								field("AdvertisingAndExhibitIncome", "Advertising and exhibit income"),
								field("AdvertisingAndExhibitIncome/@currency", "Currency"),
								field("OtherIncome", "Other income"), field("OtherIncome/@currency", "Currency"),
								field("Expenses"), field("Expenses/@currency", "Currency"))),
				page("participation", "Participation Metrics", "Metrics",
						"How many learners took part and how many credits were awarded", "classification",
						section("Participants", "Counts of the reporting period: clinicians targeted, registered, "
								+ "completing the activity and receiving credit.", "ParticipationMetrics",
								field("TargetedAudience", "Targeted audience"),
								field("RegisteredParticipants", "Registered participants"),
								field("NumberOfParticipantsCompletingActivity", "Participants completing the activity"),
								field("NumberOfParticipantsReceivingCredit", "Participants receiving credit"),
								field("NumberOfDistinctHostsOrVisitors", "Distinct hosts or visitors"),
								field("NumberOfSuccessfulPageRequests", "Successful page requests"),
								text("Comments", "Comments")),
						section("Credits awarded", "One entry per type of credit, with the total number of credits "
								+ "awarded for the activity.", "ParticipationMetrics",
								repeat("CreditsAwarded", "Credits awarded", "creditType",
										field("creditType", "Credit type"),
										field("numberOfCredits", "Number of credits awarded"),
										field("creditUnit", "Unit"), field("accreditingBody", "Accrediting body"),
										field("activityCertification", "Certification"),
										repeat("CreditFocus", "Credit focus", "Description", field("Description"),
												field("Code"), field("NumberOfCredits", "Number of credits")))),
						section("Participants by category, profession and specialty", "The number of participants "
								+ "is the element's value (edited in the tree or on the XML page); the category, "
								+ "profession or specialty is its attribute.", "ParticipationMetrics",
								repeat("ParticipantsByCategory", "By category", "@category",
										field("@category", "Category")),
								repeat("ParticipantsByProfession", "By profession", "@profession",
										suggest("@profession", "Profession", "physician", "nurse", "nurse practitioner",
												"physician assistant", "pharmacist", "psychologist", "dentist")),
								repeat("ParticipantsBySpecialty", "By specialty", "@specialty",
										field("@specialty", "Specialty")))),
				page("evaluation", "Participant Activity Evaluation", "Metrics",
						"How participants evaluated the activity: survey questions and their responses", "annotation",
						section("Survey item data", "Each survey question with the learners' responses; source and id "
								+ "refer to a standardized survey item (such as the MedBiquitous survey items, source "
								+ "medbiq.org).", "ParticipantActivityEvaluation",
								repeat("SurveyItemData", "Survey items", "SurveyQuestion", surveyItemDataFields(false))),
						section("Comparison data", "Two or more related survey items, often asked at different times "
								+ "(for example expectations before and perceptions after the activity).",
								"ParticipantActivityEvaluation",
								repeat("ComparisonData", "Comparisons", "SurveyItemDataForComparison/SurveyQuestion",
										repeat("SurveyItemDataForComparison", "Survey items compared", "SurveyQuestion",
												surveyItemDataFields(true))))),
				page("assessment", "Knowledge Assessment", "Metrics",
						"How participants responded to multiple choice assessment items, and assessment scores",
						"educational",
						section("Multiple choice assessment items", "Each question with its response options, whether "
								+ "they are correct and how many learners chose them.", "KnowledgeAssessment",
								repeat("MultipleChoiceAssessmentItemData", "Assessment items", "Question",
										text("Question", "Question"), field("Timing"),
										field("Timing/@duration", "Duration (such as P30D)"),
										repeat("AssessmentResponseData/AssessmentResponseItemData", "Responses",
												"ResponseText", field("ResponseText", "Response"),
												field("@correct", "Correct"),
												field("TotalRespondents", "Respondents"),
												field("@source", "Source"), field("@id", "Id")),
										field("@source", "Source"), field("@id", "Id"),
										repeat("Classification", "Classification (objectives, competencies)", null))),
						section("Assessment scores", "The score of each learner on an assessment, with the scoring "
								+ "method and when the assessment was taken.", "KnowledgeAssessment",
								repeat("AssessmentScoreData", "Assessments", "AssessmentName",
										field("AssessmentName", "Assessment name"),
										field("ScoreMethod", "Score method"), field("ScoreValue", "Scores (one per learner)"),
										field("Timing"), field("Timing/@duration", "Duration (such as P30D)"),
										text("Description", "Description"), field("@source", "Source"),
										field("@id", "Id"),
										repeat("Classification", "Classification (objectives, competencies)", null)))),
				page("extensions", "Extensible Information", "Extensions",
						"Elements defined by the organization implementing the standard", "technical",
						section("Extensible information", "XtensibleInfo contains sub-elements defined by the "
								+ "organization implementing the schema, in other namespaces; edit them on the XML "
								+ "page.", "", auto("XtensibleInfo"))));
	}

	/** The fields of survey item data (in a metrics report). */
	private static FieldSpec[] surveyItemDataFields(boolean comparison) {
		List<FieldSpec> fields = new ArrayList<>(List.of(text("SurveyQuestion", "Question"), field("@source", "Source"),
				field("@id", "Id")));
		if (comparison) {
			fields.add(field("Timing"));
			fields.add(field("Timing/@duration", "Duration (such as P1D)"));
		}
		fields.add(group("ResponseData/YesNoData", "Yes/no responses", field("Yes", "Yes (respondents)"),
				field("No", "No (respondents)")));
		List<FieldSpec> scale = new ArrayList<>(List.of(field("@type", "Scale type"), field("@points", "Points")));
		for (int i = 0; i < POINTS.size(); i++) {
			scale.add(field(POINTS.get(i), "Point " + (i + 1) + " (respondents)"));
			scale.add(field(POINTS.get(i) + "/@label", "Point " + (i + 1) + " label"));
		}
		fields.add(group("ResponseData/ScaleData", "Scale responses", scale.toArray(FieldSpec[]::new)));
		FieldSpec[] responseItem = { field("ResponseText", "Response"), field("TotalRespondents", "Respondents"),
				field("@source", "Source"), field("@id", "Id"),
				repeat("FreeTextData", "Write-in responses", "TextResponse", text("TextResponse", "Text"),
						field("AnalysisCategory", "Analysis category")) };
		fields.add(repeat("ResponseData/MultipleChoiceSelectOneData/ResponseItemData", "Multiple choice (select one)",
				"ResponseText", responseItem));
		fields.add(repeat("ResponseData/MultipleChoiceSelectManyData/ResponseItemData",
				"Multiple choice (select many)", "ResponseText", responseItem));
		fields.add(repeat("ResponseData/FreeTextData", "Free text responses", "TextResponse",
				text("TextResponse", "Text"), field("AnalysisCategory", "Analysis category"),
				field("AnalysisCategory/@source", "Category vocabulary"), field("AnalysisCategory/@id", "Category id")));
		return fields.toArray(FieldSpec[]::new);
	}

	private static List<PageSpec> surveyPages() {
		return List.of(
				page("items", "Survey Items", "Survey Items", "Survey questions and their response structures",
						"annotation",
						section("Survey items", "Each item has a question and one response structure (yes/no, scale, "
								+ "multiple choice or free text); source and id identify it in a vocabulary.", "",
								repeat("SurveyItem", "Survey items", "SurveyQuestion", surveyItemFields(false)))),
				page("comparisons", "Comparisons", "Survey Items",
						"Sets of related survey items, asked at different times", "relation",
						section("Comparisons", "A comparison groups related survey items, with the intended timing of "
								+ "each in relation to the learning activity.", "",
								repeat("Comparison", "Comparisons", "SurveyItemForComparison/SurveyQuestion",
										repeat("SurveyItemForComparison", "Survey items compared", "SurveyQuestion",
												surveyItemFields(true))))));
	}

	/** The fields of a survey item (in a survey items document). */
	private static FieldSpec[] surveyItemFields(boolean comparison) {
		List<FieldSpec> fields = new ArrayList<>(List.of(text("SurveyQuestion", "Question"), field("@source", "Source"),
				field("@id", "Id")));
		if (comparison) {
			fields.add(field("IntendedTiming", "Intended timing"));
			fields.add(field("IntendedTiming/@duration", "Duration (such as P3M)"));
		}
		fields.add(group("Responses/YesNo", "Yes/no", field("Yes/@id", "Yes id"), field("Yes/@source", "Yes source"),
				field("No/@id", "No id"), field("No/@source", "No source")));
		List<FieldSpec> scale = new ArrayList<>(List.of(
				suggest("@type", "Scale type", "likert", "Low/High", "unsafe/safe", "ineffective/effective"),
				field("@points", "Points (3 to 10)")));
		for (int i = 0; i < POINTS.size(); i++) {
			scale.add(field(POINTS.get(i) + "/@label", "Point " + (i + 1) + " label"));
			scale.add(field(POINTS.get(i) + "/@id", "Point " + (i + 1) + " id"));
		}
		fields.add(group("Responses/Scale", "Scale", scale.toArray(FieldSpec[]::new)));
		fields.add(field("Responses/MultipleChoiceSelectOne/ResponseItem", "Multiple choice (select one)"));
		fields.add(repeat("Responses/MultipleChoiceSelectOne/ResponseItem", "Select one: response identifiers", null,
				field("@source", "Source"), field("@id", "Id")));
		fields.add(field("Responses/MultipleChoiceSelectMany/ResponseItem", "Multiple choice (select many)"));
		fields.add(repeat("Responses/MultipleChoiceSelectMany/ResponseItem", "Select many: response identifiers", null,
				field("@source", "Source"), field("@id", "Id")));
		return fields.toArray(FieldSpec[]::new);
	}

	// ----------------------------------------------------------------------------------------------------
	// Search fields
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		if (isSurveyItems(type)) {
			return List.of(FieldGroup.of("si-questions", "Survey questions", "SurveyQuestion"),
					FieldGroup.of("si-responses", "Response options", "ResponseItem", "One", "Two", "Three", "Four",
							"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Yes", "No"),
					FieldGroup.of("si-scales", "Scales", "Scale"),
					FieldGroup.of("si-comparisons", "Comparisons", "Comparison"));
		}
		return List.of(
				new FieldGroup("mems-activity", "Activity title and description", Set.of("title", "description"),
						Set.of("ActivityDescription")),
				FieldGroup.of("mems-questions", "Survey and assessment questions", "SurveyQuestion", "Question"),
				FieldGroup.of("mems-responses", "Responses", "ResponseText", "TextResponse"),
				FieldGroup.of("mems-categories", "Respondent and analysis categories", "RespondentCategory",
						"AnalysisCategory"),
				FieldGroup.of("mems-assessments", "Assessments", "AssessmentScoreData", "MultipleChoiceAssessmentItemData"),
				FieldGroup.of("mems-participants", "Participants by category, profession and specialty",
						"ParticipantsByCategory", "ParticipantsByProfession", "ParticipantsBySpecialty"),
				FieldGroup.of("mems-credits", "Credits awarded", "CreditsAwarded"),
				FieldGroup.of("mems-support", "Commercial support", "CommercialSupportAmount"),
				FieldGroup.of("mems-comments", "Comments", "Comments"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Help
	// ----------------------------------------------------------------------------------------------------

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		Map<String, String> help = new LinkedHashMap<>();
		if (isSurveyItems(type)) {
			surveyHelp(help);
		} else {
			metricsHelp(help);
		}
		// The forms look attributes up by their plain name: register them under both names.
		for (Map.Entry<String, String> entry : List.copyOf(help.entrySet())) {
			if (entry.getKey().startsWith("@")) {
				help.putIfAbsent(entry.getKey().substring(1), entry.getValue());
			}
		}
		return help;
	}

	private static void metricsHelp(Map<String, String> help) {
		help.put("MedicalEducationMetrics", "The root element. It contains sub-elements that define evaluation data for "
				+ "a health professions educational activity (one activity per document).");
		help.put("ReportDescription", "Contains subelements that provide details about the scope of the report.");
		help.put("RespondentCategory", "Identifies a respondent category for the metrics information contained in "
				+ "this dataset. This is useful when reporting evaluation data by profession or other category.");
		help.put("ReportingStartDate", "The first day of the reporting period for the metrics described for this "
				+ "educational activity.");
		help.put("ReportingEndDate", "The last day of the reporting period for the metrics described for this "
				+ "educational activity.");
		help.put("ActivityDescription", "Contains elements that describe and identify the educational activity that "
				+ "is being evaluated: Healthcare LOM metadata (title, identifier, credits, commercial support, intent) "
				+ "and financial data. To uniquely identify the activity, use a Healthcare LOM identifier, such as a URL.");
		help.put("lom", "Healthcare Learning Object Metadata describing the title, number and type of credits, subject "
				+ "and other aspects of the educational activity.");
		help.put("CommercialSupportAmount", "The amount of commercial support received from a single source, as an "
				+ "integer in the currency given.");
		help.put("@supportSource", "The organization that is the source of the commercial support.");
		help.put("@currency", "The currency of the amount indicated. Recommended values are ISO 4217 3-letter "
				+ "alphabetic codes, for example USD, CAD or EUR; values are limited to 3-character strings.");
		help.put("AdvertisingAndExhibitIncome", "The amount of advertising and exhibit income received for this "
				+ "activity.");
		help.put("OtherIncome", "The amount of income from sources other than commercial support, advertising, and "
				+ "exhibits.");
		help.put("Expenses", "The total expenses of the activity.");
		help.put("DesignedToChangeCompetence", "Whether the activity is designed to change competence (true or "
				+ "false).");
		help.put("DesignedToChangePerformance", "Whether the activity is designed to change performance (true or "
				+ "false).");
		help.put("DesignedToChangePatientOutcomes", "Whether the activity is designed to change patient outcomes "
				+ "(true or false).");
		help.put("ChangesInCompetenceMeasured", "Whether changes in competence were measured as part of activity "
				+ "evaluation.");
		help.put("ChangesInPerformanceMeasured", "Whether changes in performance were measured as part of activity "
				+ "evaluation.");
		help.put("ChangesInPatientOutcomesMeasured", "Whether changes in patient outcomes were measured as part of "
				+ "activity evaluation.");
		help.put("ParticipantActivityEvaluation", "Defines how participants evaluated the educational activity: survey "
				+ "item data and comparison data.");
		help.put("SurveyItemData", "A survey question and learner responses to that question. It may reference a "
				+ "question from a standardized question set with source and id.");
		help.put("SurveyQuestion", "The text of the survey question.");
		help.put("ResponseData", "The response data for this question: yes/no, scale, multiple choice (select one or "
				+ "many) or free text data.");
		help.put("YesNoData", "How many participants answered yes and no to the question.");
		help.put("Yes", "How many participants answered Yes to the question.");
		help.put("No", "How many participants answered No to the question.");
		help.put("ScaleData", "Response data for scale questions, including Likert scale questions. Scales should only "
				+ "use subelements corresponding to the number of points in the scale.");
		help.put("@type", "The type of scale, for example likert, unsafe/safe, ineffective/effective.");
		help.put("@points", "The number of points in the scale: an integer from 3 to 10.");
		help.put("@label", "A label for this point on the scale, for example Strongly Agree. Labels should be used for "
				+ "the first and last point on a scale at a minimum.");
		for (int i = 0; i < POINTS.size(); i++) {
			help.put(POINTS.get(i), "The number of individuals indicating point " + (i + 1) + " as their response on "
					+ "a scale question" + (i < 3 ? " (required)." : " (only on scales of " + (i + 1) + " or more "
							+ "points)."));
		}
		help.put("MultipleChoiceSelectOneData", "Response data for a multiple choice question in which the learner may "
				+ "select only one answer.");
		help.put("MultipleChoiceSelectManyData", "Response data for a multiple choice question in which the learner "
				+ "may select multiple answers.");
		help.put("ResponseItemData", "A response option of a multiple choice question and the number of respondents "
				+ "that selected it.");
		help.put("ResponseText", "The text of the response option.");
		help.put("TotalRespondents", "The number of respondents that selected this response item.");
		help.put("FreeTextData", "A response written in the respondent's own words, with the category assigned to it "
				+ "by content analysis; also used for write-in answers of multiple choice questions.");
		help.put("TextResponse", "The text of the individual's response.");
		help.put("AnalysisCategory", "A category assigned to the free text response as a result of content analysis.");
		help.put("ComparisonData", "Response data for two or more complementary or related survey questions, often "
				+ "asked at different points in time (for example expectations before and perceptions after an "
				+ "activity).");
		help.put("SurveyItemDataForComparison", "A single survey item and corresponding learner responses in a set of "
				+ "related survey items grouped for comparison.");
		help.put("Timing", "When, in relation to the learning event, the item or assessment was presented to the "
				+ "learner: Before, During or After.");
		help.put("@duration", "The amount of time before or after the learning activity the item was presented to the "
				+ "learner, as an XML Schema duration (P1D is one day, P30D thirty days).");
		help.put("KnowledgeAssessment", "How participants responded to multiple choice assessment items, and "
				+ "assessment scores.");
		help.put("MultipleChoiceAssessmentItemData", "The set of responses corresponding to a single multiple choice "
				+ "assessment item.");
		help.put("Question", "The question or prompt of the assessment item.");
		help.put("AssessmentResponseData", "The possible responses to the question and how many learners indicated "
				+ "each response.");
		help.put("AssessmentResponseItemData", "A response option of an assessment item, the number of learners who "
				+ "gave it and whether it is correct.");
		help.put("@correct", "Whether this response item is a correct response to the question (required).");
		help.put("Classification", "The educational objective or competency associated with the assessment item or "
				+ "assessment (Healthcare LOM classification).");
		help.put("AssessmentScoreData", "The assessment scores for a particular assessment, with the scoring method, "
				+ "assessment timing and classification.");
		help.put("AssessmentName", "The name of the assessment instrument.");
		help.put("ScoreMethod", "The method used to measure and report the scores (PESC TestScoreMethodType): Scaled, "
				+ "Graded, Standard, Raw, Percent (of items correct), Mastery, Adjective, Stanine, Percentile, "
				+ "NormalCurve (equivalent), Equated (pre-test and post-test were different) or Local.");
		help.put("ScoreValue", "One learner's score on the assessment, reflecting the score method; for example, with "
				+ "Percent the value is a number between 0 and 100.");
		help.put("Description", "Further information about the assessment. If the score method is Local, the local "
				+ "method may be defined here.");
		help.put("ParticipationMetrics", "Data on the number of learners participating in the activity and the extent "
				+ "of their participation, including how many credits were awarded.");
		help.put("TargetedAudience", "How many clinicians received promotional materials for this educational "
				+ "activity or were otherwise targeted to participate in it.");
		help.put("RegisteredParticipants", "How many clinicians registered to participate in the educational "
				+ "activity.");
		help.put("NumberOfParticipantsReceivingCredit", "The number of participants that received continuing "
				+ "education credits for participating in this activity.");
		help.put("CreditsAwarded", "The number and type of credits awarded for the activity (Activity Report "
				+ "CreditReceivedType); repeat it for each type of credit.");
		help.put("CreditFocus", "A focus of the credits awarded, such as a topic required by a licensing board.");
		help.put("NumberOfDistinctHostsOrVisitors", "How many different computers requested pages in the online course "
				+ "or resource (from web server reporting software).");
		help.put("NumberOfSuccessfulPageRequests", "The number of requests for pages in the course or resource (from web "
				+ "server reporting software).");
		help.put("NumberOfParticipantsCompletingActivity", "How many individuals completed the course or activity, as "
				+ "tracked by a learning management system or by the educator's other criteria.");
		help.put("ParticipantsByCategory", "The number of participants in a category (the category attribute).");
		help.put("@category", "The participant category described.");
		help.put("ParticipantsByProfession", "The number of participants of a profession (the profession attribute).");
		help.put("@profession", "The profession described; see the Healthcare LOM specification for recommended "
				+ "values.");
		help.put("ParticipantsBySpecialty", "The number of participants of a specialty (the specialty attribute).");
		help.put("@specialty", "The specialty described; see the Healthcare LOM specification for recommended values.");
		help.put("Comments", "Comments or further explanation regarding any of the participation metrics.");
		help.put("XtensibleInfo", "Sub-elements defined by the organization implementing the schema; candidates for "
				+ "inclusion in a revision of Medical Education Metrics.");
		help.put("@source", "A source vocabulary or instrument for the item, response or term (for example medbiq.org "
				+ "for the MedBiquitous survey items).");
		help.put("@id", "The identifier of the item, response or term in the source vocabulary referenced.");
		// Types the schema defines but no element of MEMS 1.0 uses.
		help.put("Response", "A free text response with its analysis category (TopicsToLearnMoreAboutType, which no "
				+ "element of MEMS 1.0 uses).");
		help.put("@respondentCategory", "A respondent category (RespondentsByCategoryType, which no element of MEMS "
				+ "1.0 uses).");
	}

	private static void surveyHelp(Map<String, String> help) {
		help.put("SurveyItems", "The root element. It contains survey items and comparisons (sets of related survey "
				+ "items).");
		help.put("SurveyItem", "A single survey item: a question and its response structure, identified in a source "
				+ "vocabulary by source and id.");
		help.put("SurveyQuestion", "The text of the survey question.");
		help.put("Responses", "The potential answers or response structure associated with the survey item: yes/no, "
				+ "scale, multiple choice (select one or many) or free text.");
		help.put("YesNo", "The response structure for a yes/no question, with an identifier for each response.");
		help.put("Yes", "The Yes response to a yes/no question (an empty element with source and id).");
		help.put("No", "The No response to a yes/no question (an empty element with source and id).");
		help.put("Scale", "The response structure of a scale, such as a Likert or confidence level scale, of three to "
				+ "ten points.");
		help.put("@type", "The type of scale, for example likert, unsafe/safe, ineffective/effective.");
		help.put("@points", "The number of points in the scale: an integer from 3 to 10.");
		help.put("@label", "A label for this point on the scale, for example Strongly Agree. Labels should be used for "
				+ "the first and last point on a scale at a minimum.");
		for (int i = 0; i < POINTS.size(); i++) {
			help.put(POINTS.get(i), "The label and identifier of point " + (i + 1) + " of the scale"
					+ (i < 3 ? " (required)." : " (only on scales of " + (i + 1) + " or more points)."));
		}
		help.put("MultipleChoiceSelectOne", "The potential responses to a multiple choice question where the learner "
				+ "selects one answer only.");
		help.put("MultipleChoiceSelectMany", "The potential responses to a multiple choice question where the learner "
				+ "may select one or more answers.");
		help.put("ResponseItem", "One of the response choices of a multiple choice question (its text), with source "
				+ "and id.");
		help.put("FreeText", "A free text response structure (an empty element).");
		help.put("Comparison", "A set of related survey items, such as expectations before and perceptions after an "
				+ "activity.");
		help.put("SurveyItemForComparison", "A single survey item within a set of survey items grouped for comparison, "
				+ "with its intended timing.");
		help.put("IntendedTiming", "The timing of the survey item in relation to the learning activity: Before, "
				+ "During or After.");
		help.put("@duration", "The time gap between the learning activity and the delivery of the survey item, as an "
				+ "XML Schema duration: with After and P3M, the item is delivered 3 months after the activity.");
		help.put("@source", "A source vocabulary or instrument for the survey item or response (medbiq.org for the "
				+ "MedBiquitous survey items).");
		help.put("@id", "The identifier of the survey item or response in the source vocabulary referenced.");
	}

	// ----------------------------------------------------------------------------------------------------
	// The MedBiquitous survey items (source medbiq.org)
	// ----------------------------------------------------------------------------------------------------

	/**
	 * A survey item of the MedBiquitous Survey Items specification.
	 *
	 * @param question  the question
	 * @param responses the kind of responses (YesNo, Scale, MultipleChoiceSelectOne, ...)
	 * @param points    the points of a scale, or 0
	 */
	record StandardItem(String question, String responses, int points) {
	}

	/**
	 * A response of the MedBiquitous Survey Items specification.
	 *
	 * @param item  the id of its survey item, or {@code null} if the specification gives the item no id
	 * @param kind  {@code Yes}, {@code No}, a point element ({@code One} ...) or {@code ResponseItem}
	 * @param label the label or text, or {@code null}
	 */
	record StandardResponse(String item, String kind, String label) {
	}

	/** The identified survey items of the MedBiquitous Survey Items specification (source medbiq.org), by id. */
	static final Map<String, StandardItem> MEDBIQ_ITEMS = Map.of(
			"10", new StandardItem("The course achieved its educational objectives.", "Scale", 5),
			"70", new StandardItem("Thinking about my expectations for this CME activity, it was important that it "
					+ "provide fair and balanced content.", "Scale", 7),
			"150", new StandardItem("Thinking about my participation in this CME activity, I thought it was able to "
					+ "provide fair and balanced content.", "Scale", 7));

	/** The responses of the MedBiquitous Survey Items specification (source medbiq.org), by id. */
	static final Map<String, StandardResponse> MEDBIQ_RESPONSES = standardResponses();

	private static Map<String, StandardResponse> standardResponses() {
		Map<String, StandardResponse> responses = new HashMap<>();
		String[] likert = { "Strongly agree", "Agree", "Neutral", "Disagree", "Strongly disagree" };
		for (int i = 0; i < likert.length; i++) {
			responses.put(String.valueOf(20 + 10 * i), new StandardResponse("10", POINTS.get(i), likert[i]));
		}
		for (int i = 0; i < 7; i++) {
			String label = i == 0 ? "Low" : i == 6 ? "High" : null;
			responses.put(String.valueOf(80 + 10 * i), new StandardResponse("70", POINTS.get(i), label));
			responses.put(String.valueOf(150 + 10 * i), new StandardResponse("150", POINTS.get(i), label));
		}
		responses.put("220", new StandardResponse(null, "Yes", null));
		responses.put("230", new StandardResponse(null, "No", null));
		String[] practice = { "Hospital based", "Community based - more than 10 physicians",
				"Community-based - less than 10 physicians", "Other" };
		for (int i = 0; i < practice.length; i++) {
			responses.put(String.valueOf(240 + 10 * i), new StandardResponse(null, "ResponseItem", practice[i]));
		}
		String[] barriers = { "Cultural", "Economic", "Lack of clinician training", "Access to care" };
		for (int i = 0; i < barriers.length; i++) {
			responses.put(String.valueOf(280 + 10 * i), new StandardResponse(null, "ResponseItem", barriers[i]));
		}
		return Map.copyOf(responses);
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<HlomProblem> rules(MedbiqDocumentType type, EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		if (root == null) {
			return List.of();
		}
		Checker checker = new Checker();
		if (isSurveyItems(type)) {
			checkSurveyItems(checker, root);
		} else {
			checkMetrics(checker, root);
		}
		return checker.problems;
	}

	/** Collects the problems of a document. */
	private static final class Checker {

		final List<HlomProblem> problems = new ArrayList<>();

		/** The first response seen per source and id, for the consistency of response identifiers. */
		final Map<String, EObject> responses = new HashMap<>();

		void error(EObject object, String message) {
			add(Severity.ERROR, object, message);
		}

		void warning(EObject object, String message) {
			add(Severity.WARNING, object, message);
		}

		void info(EObject object, String message) {
			add(Severity.INFO, object, message);
		}

		void add(Severity severity, EObject object, String message) {
			problems.add(new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1));
		}
	}

	// ----- Metrics reports -----

	private static void checkMetrics(Checker checker, EObject root) {
		checkReportPeriod(checker, root);
		checkActivity(checker, root);
		checkNegativeValues(checker, root);
		BigDecimal participants = checkParticipation(checker, root);
		checkCredits(checker, root);
		checkEvaluation(checker, root, participants);
		checkAssessment(checker, root, participants);
	}

	private static void checkReportPeriod(Checker checker, EObject root) {
		EObject report = first(root, "ReportDescription");
		if (report == null) {
			return;
		}
		LocalDate start = Paths.date(Paths.text(report, "ReportingStartDate"));
		LocalDate end = Paths.date(Paths.text(report, "ReportingEndDate"));
		if (start != null && end != null && end.isBefore(start)) {
			checker.error(report, "The ReportingEndDate (" + end + ") is before the ReportingStartDate (" + start
					+ "): the reporting period runs from its first to its last day. Correct one of the dates.");
		}
		if (start != null && start.isAfter(LocalDate.now().plusDays(1))) {
			checker.warning(report, "The reporting period starts on " + start + ", in the future: a metrics report "
					+ "describes data collected during the reporting period. Check the ReportingStartDate.");
		}
		if (end == null) {
			return;
		}
		for (EObject credits : Paths.objects(root, EDUCATION + "/credits")) {
			LocalDate release = Paths.date(Paths.text(credits, "releaseDate"));
			if (release != null && release.isAfter(end)) {
				checker.warning(credits, "The activity's credits were released on " + release + ", after the end of "
						+ "the reporting period (" + end + "): no participation can be reported for the period. Check "
						+ "the releaseDate and the ReportingEndDate.");
			}
		}
		String startTime = Paths.text(root, EDUCATION + "/startDateTime");
		LocalDate activityStart = startTime == null || startTime.length() < 10 ? null : Paths.date(startTime.substring(0, 10));
		if (activityStart != null && activityStart.isAfter(end)) {
			checker.warning(first(root, EDUCATION), "The activity starts on " + activityStart + ", after the end of the "
					+ "reporting period (" + end + "). Check the startDateTime and the ReportingEndDate.");
		}
	}

	private static void checkActivity(Checker checker, EObject root) {
		EObject activity = first(root, "ActivityDescription");
		if (activity == null) {
			return;
		}
		EObject lom = first(activity, "lom");
		if (lom != null && Paths.text(lom, "general/title/string") == null) {
			checker.warning(lom, "The activity has no title: give the title of the educational activity in the "
					+ "Healthcare LOM general title.");
		}
		if (lom != null && Paths.text(lom, "general/identifier/entry") == null) {
			checker.warning(lom, "The activity has no identifier: \"To uniquely identify an educational activity, use "
					+ "the identifier entry element within Healthcare LOM\" (for example the URL of the activity).");
		}
		for (String name : List.of("CommercialSupportAmount", "AdvertisingAndExhibitIncome", "OtherIncome",
				"Expenses")) {
			for (EObject amount : Paths.objects(activity, name)) {
				String currency = Paths.text(amount, "@currency");
				BigDecimal value = Paths.decimal(Paths.content(amount));
				if (currency == null) {
					if (value != null && value.signum() != 0) {
						checker.warning(amount, name + " has no currency: give the currency of the amount (an ISO "
								+ "4217 code such as USD).");
					}
				} else if (currency.length() != 3) {
					checker.error(amount, "The currency \"" + currency + "\" of " + name + " is not valid: \"valid "
							+ "values are limited to 3-character strings\" (ISO 4217 codes such as USD, CAD, EUR).");
				} else if (!currency.matches("[A-Z]{3}")) {
					checker.warning(amount, "The currency \"" + currency + "\" of " + name + " is not an ISO 4217 "
							+ "code: \"recommended values are ISO 4217 3-letter alphabetic codes\" (USD, CAD, EUR).");
				}
			}
		}
		List<EObject> support = Paths.objects(activity, "CommercialSupportAmount");
		for (EObject amount : support) {
			if (Paths.text(amount, "@supportSource") == null) {
				checker.warning(amount, "CommercialSupportAmount has no supportSource: it indicates the amount of "
						+ "commercial support received from a single source; name the organization.");
			}
		}
		BigDecimal supportTotal = support.stream().map(a -> Paths.decimal(Paths.content(a))).filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		if ("no".equals(Paths.literal(root, EDUCATION + "/commercialSupport")) && supportTotal.signum() > 0) {
			checker.warning(support.getFirst(), "The Healthcare LOM metadata says the activity has no commercial "
					+ "support, but CommercialSupportAmount reports " + supportTotal + ". Correct one of them.");
		}
		if (bool(activity, "ChangesInCompetenceMeasured") && first(root, "KnowledgeAssessment") == null
				&& Paths.objects(root, "ParticipantActivityEvaluation/ComparisonData").isEmpty()) {
			checker.info(activity, "ChangesInCompetenceMeasured is true, but the report has neither a "
					+ "KnowledgeAssessment nor ComparisonData: consider reporting the measurement.");
		}
	}

	/** The element and attribute names of counts and amounts, which can't be negative. */
	private static final Set<String> NON_NEGATIVE = Set.of("TargetedAudience", "RegisteredParticipants",
			"NumberOfParticipantsReceivingCredit", "NumberOfDistinctHostsOrVisitors", "NumberOfSuccessfulPageRequests",
			"NumberOfParticipantsCompletingActivity", "ParticipantsByCategory", "ParticipantsByProfession",
			"ParticipantsBySpecialty", "Yes", "No", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
			"Nine", "Ten", "TotalRespondents", "CommercialSupportAmount", "AdvertisingAndExhibitIncome", "OtherIncome",
			"Expenses", "numberOfCredits", "NumberOfCredits");

	private static void checkNegativeValues(Checker checker, EObject root) {
		for (EObject object : Stream.concat(Stream.of(root), stream(root)).toList()) {
			for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
				String name = META.getName(feature);
				if (META.getFeatureKind(feature) != ExtendedMetaData.ELEMENT_FEATURE || !NON_NEGATIVE.contains(name)) {
					continue;
				}
				for (Object value : Paths.values(object, name)) {
					String text = value instanceof EObject child ? Paths.content(child) : String.valueOf(value);
					BigDecimal number = Paths.decimal(text);
					if (number != null && number.signum() < 0) {
						checker.error(value instanceof EObject child ? child : object, name + " is " + number
								+ ": counts, credits and amounts can't be negative.");
					}
				}
			}
		}
	}

	/** Checks the participation metrics; returns the largest participant count reported, or {@code null}. */
	private static BigDecimal checkParticipation(Checker checker, EObject root) {
		EObject metrics = first(root, "ParticipationMetrics");
		if (metrics == null) {
			return null;
		}
		BigDecimal targeted = Paths.decimal(Paths.text(metrics, "TargetedAudience"));
		BigDecimal registered = Paths.decimal(Paths.text(metrics, "RegisteredParticipants"));
		BigDecimal completing = Paths.decimal(Paths.text(metrics, "NumberOfParticipantsCompletingActivity"));
		BigDecimal credit = Paths.decimal(Paths.text(metrics, "NumberOfParticipantsReceivingCredit"));
		BigDecimal hosts = Paths.decimal(Paths.text(metrics, "NumberOfDistinctHostsOrVisitors"));
		BigDecimal pages = Paths.decimal(Paths.text(metrics, "NumberOfSuccessfulPageRequests"));
		if (Stream.of(targeted, registered, completing, credit, hosts, pages).allMatch(Objects::isNull)
				&& Paths.objects(metrics, "ParticipantsByCategory").isEmpty()
				&& Paths.objects(metrics, "ParticipantsByProfession").isEmpty()) {
			checker.warning(metrics, "ParticipationMetrics contains no data: report at least the "
					+ "RegisteredParticipants or the NumberOfParticipantsCompletingActivity.");
		}
		if (larger(completing, registered)) {
			checker.warning(metrics, "NumberOfParticipantsCompletingActivity (" + completing + ") is larger than "
					+ "RegisteredParticipants (" + registered + "): only participants can complete the activity. "
					+ "Check the counts.");
		}
		if (larger(credit, registered)) {
			checker.warning(metrics, "NumberOfParticipantsReceivingCredit (" + credit + ") is larger than "
					+ "RegisteredParticipants (" + registered + "): only participants can receive credit. Check the "
					+ "counts.");
		}
		if (larger(credit, completing)) {
			checker.info(metrics, "NumberOfParticipantsReceivingCredit (" + credit + ") is larger than "
					+ "NumberOfParticipantsCompletingActivity (" + completing + "): credit is usually awarded for "
					+ "completing the activity. Check the counts, or explain them in Comments.");
		}
		if (larger(registered, targeted)) {
			checker.info(metrics, "RegisteredParticipants (" + registered + ") is larger than the TargetedAudience ("
					+ targeted + "), the clinicians who received promotional materials or were otherwise targeted. "
					+ "Check the counts.");
		}
		if (larger(hosts, pages)) {
			checker.warning(metrics, "NumberOfSuccessfulPageRequests (" + pages + ") is smaller than "
					+ "NumberOfDistinctHostsOrVisitors (" + hosts + "): every host or visitor requested at least one "
					+ "page. Check the web server statistics.");
		}
		BigDecimal participants = Stream.of(registered, completing, credit).filter(Objects::nonNull)
				.max(BigDecimal::compareTo).orElse(null);
		checkBreakdown(checker, metrics, "ParticipantsByCategory", "category", participants, Severity.WARNING);
		checkBreakdown(checker, metrics, "ParticipantsByProfession", "profession", participants, Severity.WARNING);
		checkBreakdown(checker, metrics, "ParticipantsBySpecialty", "specialty", participants, Severity.INFO);
		return participants;
	}

	private static void checkBreakdown(Checker checker, EObject metrics, String name, String attribute,
			BigDecimal participants, Severity overSeverity) {
		List<EObject> counts = Paths.objects(metrics, name);
		Set<String> seen = new HashSet<>();
		BigDecimal sum = BigDecimal.ZERO;
		for (EObject count : counts) {
			String value = Paths.text(count, "@" + attribute);
			if (value == null) {
				checker.error(count, name + " has no " + attribute + " attribute: the count is meaningless without "
						+ "the " + attribute + " it describes.");
			} else if (!seen.add(value.toLowerCase(Locale.ROOT))) {
				checker.warning(count, "The " + attribute + " \"" + value + "\" is counted twice in " + name
						+ ": give one count per " + attribute + ".");
			}
			BigDecimal number = Paths.decimal(Paths.content(count));
			if (number != null) {
				sum = sum.add(number);
			}
		}
		if (participants != null && sum.compareTo(participants) > 0) {
			checker.add(overSeverity, counts.getLast(), "The " + name + " counts add up to " + sum + ", more than the "
					+ participants + " participants reported. Check the counts" + (overSeverity == Severity.INFO
							? " (participants with several specialties are counted once per specialty)." : "."));
		}
	}

	private static void checkCredits(Checker checker, EObject root) {
		EObject metrics = first(root, "ParticipationMetrics");
		if (metrics == null) {
			return;
		}
		List<EObject> awarded = Paths.objects(metrics, "CreditsAwarded");
		BigDecimal receiving = Paths.decimal(Paths.text(metrics, "NumberOfParticipantsReceivingCredit"));
		BigDecimal total = BigDecimal.ZERO;
		for (EObject credits : awarded) {
			String type = Paths.literal(credits, "creditType");
			BigDecimal number = Paths.decimal(Paths.text(credits, "numberOfCredits"));
			if (number == null) {
				checker.warning(credits, "CreditsAwarded has no numberOfCredits: it defines the number of credits "
						+ "and type of credit awarded for the activity.");
				continue;
			}
			if (type == null) {
				checker.warning(credits, "CreditsAwarded has no creditType: it defines the number of credits and type "
						+ "of credit awarded (CME, CE, CNE, ...).");
			}
			total = total.add(number);
			BigDecimal offered = offeredCredits(root, type);
			if (receiving != null && offered != null && number.compareTo(receiving.multiply(offered)) > 0) {
				checker.warning(credits, number + " " + (type == null ? "" : type + " ") + "credits were awarded, "
						+ "more than the " + receiving + " participants receiving credit can have earned with the "
						+ offered + " credits the activity offers. Check numberOfCredits (the total for all "
						+ "participants).");
			}
		}
		if (total.signum() > 0 && receiving != null && receiving.signum() == 0) {
			checker.warning(awarded.getFirst(), total + " credits were awarded, but "
					+ "NumberOfParticipantsReceivingCredit is 0. Correct one of them.");
		}
		if (!awarded.isEmpty() && receiving == null) {
			checker.info(awarded.getFirst(), "Credits were awarded: report the NumberOfParticipantsReceivingCredit "
					+ "as well.");
		}
		if ("no".equals(Paths.literal(root, EDUCATION + "/creditsAvailable"))
				&& (total.signum() > 0 || receiving != null && receiving.signum() > 0)) {
			checker.warning(first(root, EDUCATION), "The Healthcare LOM metadata says no credits are available for "
					+ "the activity, but the participation metrics report credits awarded. Correct one of them.");
		}
	}

	/** The largest number of credits the activity offers of a type (any type if {@code null}), or {@code null}. */
	private static BigDecimal offeredCredits(EObject root, String type) {
		return Paths.objects(root, EDUCATION + "/credits").stream()
				.filter(c -> type == null || type.equals(Paths.literal(c, "creditType")))
				.map(c -> Paths.decimal(Paths.text(c, "numberOfCredits"))).filter(Objects::nonNull)
				.max(BigDecimal::compareTo).orElse(null);
	}

	private static void checkEvaluation(Checker checker, EObject root, BigDecimal participants) {
		EObject evaluation = first(root, "ParticipantActivityEvaluation");
		if (evaluation == null) {
			return;
		}
		Map<String, EObject> references = new HashMap<>();
		for (EObject item : Paths.objects(evaluation, "SurveyItemData")) {
			checkSurveyItemData(checker, item, participants);
			String key = key(item);
			if (key != null && references.putIfAbsent(key, item) != null) {
				checker.warning(item, "The survey item " + describe(item) + " is reported twice: report the "
						+ "responses to a survey item once, or use ComparisonData for items asked at different times.");
			}
		}
		for (EObject comparison : Paths.objects(evaluation, "ComparisonData")) {
			List<EObject> items = Paths.objects(comparison, "SurveyItemDataForComparison");
			if (items.size() < 2) {
				checker.warning(comparison, "ComparisonData contains " + plural(items.size(), "survey item") + ": it "
						+ "contains response data for \"two or more complementary or related survey questions\".");
			}
			for (EObject item : items) {
				checkSurveyItemData(checker, item, participants);
				checkDuration(checker, first(item, "Timing"));
			}
		}
	}

	private static void checkSurveyItemData(Checker checker, EObject item, BigDecimal participants) {
		String question = Paths.text(item, "SurveyQuestion");
		if (question == null && !Paths.values(item, "SurveyQuestion").isEmpty()) {
			checker.error(item, "The SurveyQuestion is blank: give the text of the survey question.");
		}
		checkReference(checker, item, question);
		EObject data = first(item, "ResponseData");
		if (data == null) {
			return;
		}
		EObject yesNo = first(data, "YesNoData");
		if (yesNo != null) {
			for (EObject answer : Stream.of(first(yesNo, "Yes"), first(yesNo, "No")).filter(Objects::nonNull).toList()) {
				checkResponseReference(checker, item, answer);
			}
			checkRespondents(checker, item, yesNo, sum(Paths.texts(yesNo, "Yes"), Paths.texts(yesNo, "No")),
					participants);
		}
		EObject scale = first(data, "ScaleData");
		if (scale != null) {
			List<EObject> points = checkScale(checker, scale, false);
			for (EObject point : points) {
				checkResponseReference(checker, item, point);
			}
			checkRespondents(checker, item, scale,
					points.stream().map(p -> Paths.decimal(Paths.content(p))).filter(Objects::nonNull)
							.reduce(BigDecimal.ZERO, BigDecimal::add), participants);
		}
		EObject one = first(data, "MultipleChoiceSelectOneData");
		if (one != null) {
			List<EObject> options = checkOptions(checker, item, one);
			checkRespondents(checker, item, one, options.stream().map(o -> Paths.decimal(Paths.text(o, "TotalRespondents")))
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add), participants);
		}
		EObject many = first(data, "MultipleChoiceSelectManyData");
		if (many != null) {
			for (EObject option : checkOptions(checker, item, many)) {
				checkRespondents(checker, item, option, Paths.decimal(Paths.text(option, "TotalRespondents")), participants);
			}
		}
		List<EObject> freeText = Paths.objects(data, "FreeTextData");
		if (participants != null && freeText.size() > participants.intValue()) {
			checker.warning(freeText.getLast(), "The question " + quote(question) + " has " + freeText.size()
					+ " free text responses, more than the " + participants + " participants reported.");
		}
		checkResponseKind(checker, item, data);
	}

	/** Checks the response items of multiple choice data; returns them. */
	private static List<EObject> checkOptions(Checker checker, EObject item, EObject choice) {
		List<EObject> options = Paths.objects(choice, "ResponseItemData");
		Set<String> texts = new HashSet<>();
		for (EObject option : options) {
			String text = Paths.text(option, "ResponseText");
			if (text != null && !texts.add(normalize(text))) {
				checker.warning(option, "The response \"" + text + "\" is listed twice: give each response option "
						+ "once, with the number of respondents that selected it.");
			}
			checkResponseReference(checker, item, option);
		}
		return options;
	}

	private static void checkRespondents(Checker checker, EObject item, EObject object, BigDecimal respondents,
			BigDecimal participants) {
		if (respondents != null && participants != null && respondents.compareTo(participants) > 0) {
			checker.warning(object, "The question " + quote(Paths.text(item, "SurveyQuestion")) + " has " + respondents
					+ " respondents, more than the " + participants + " participants reported in "
					+ "ParticipationMetrics. Check the response counts.");
		}
	}

	/** Checks that the responses of a referenced MedBiquitous survey item have its structure. */
	private static void checkResponseKind(Checker checker, EObject item, EObject data) {
		if (!MEDBIQ.equals(Paths.text(item, "@source"))) {
			return;
		}
		StandardItem standard = MEDBIQ_ITEMS.get(Paths.text(item, "@id"));
		if (standard == null) {
			return;
		}
		String kind = Stream.of("YesNoData", "ScaleData", "MultipleChoiceSelectOneData", "MultipleChoiceSelectManyData",
				"FreeTextData").filter(n -> !Paths.objects(data, n).isEmpty()).findFirst()
				.map(n -> n.substring(0, n.length() - 4)).orElse(null);
		if (kind != null && !kind.equals(standard.responses())) {
			checker.warning(data, "The MedBiquitous survey item " + Paths.text(item, "@id") + " has "
					+ standard.responses() + " responses, but the data are " + kind + "Data. Report the responses "
					+ "of the survey item, or remove its source and id.");
		} else if (kind != null && standard.points() > 0) {
			BigDecimal points = Paths.decimal(Paths.text(data, "ScaleData/@points"));
			if (points != null && points.intValue() != standard.points()) {
				checker.warning(data, "The MedBiquitous survey item " + Paths.text(item, "@id") + " has a scale of "
						+ standard.points() + " points, but the data have " + points.toPlainString() + " points.");
			}
		}
	}

	private static void checkAssessment(Checker checker, EObject root, BigDecimal participants) {
		EObject assessment = first(root, "KnowledgeAssessment");
		if (assessment == null) {
			return;
		}
		for (EObject item : Paths.objects(assessment, "MultipleChoiceAssessmentItemData")) {
			List<EObject> responses = Paths.objects(item, "AssessmentResponseData/AssessmentResponseItemData");
			String question = Paths.text(item, "Question");
			if (!responses.isEmpty()
					&& responses.stream().noneMatch(r -> "true".equals(Paths.text(r, "@correct")))) {
				checker.warning(item, "No response of the assessment item " + quote(question) + " is correct: mark the "
						+ "correct response with correct=\"true\".");
			}
			Set<String> texts = new HashSet<>();
			for (EObject response : responses) {
				String text = Paths.text(response, "ResponseText");
				if (text != null && !texts.add(normalize(text))) {
					checker.warning(response, "The response \"" + text + "\" is listed twice in the assessment item "
							+ quote(question) + ".");
				}
				BigDecimal count = Paths.decimal(Paths.text(response, "TotalRespondents"));
				if (count != null && participants != null && count.compareTo(participants) > 0) {
					checker.warning(response, "The response \"" + text + "\" has " + count + " respondents, more than "
							+ "the " + participants + " participants reported in ParticipationMetrics.");
				}
			}
			checkDuration(checker, first(item, "Timing"));
		}
		for (EObject scores : Paths.objects(assessment, "AssessmentScoreData")) {
			checkScores(checker, scores, participants);
			checkDuration(checker, first(scores, "Timing"));
		}
	}

	private static void checkScores(Checker checker, EObject scores, BigDecimal participants) {
		String method = Paths.literal(scores, "ScoreMethod");
		String name = quote(Paths.text(scores, "AssessmentName"));
		List<String> values = Paths.texts(scores, "ScoreValue");
		if (participants != null && values.size() > participants.intValue()) {
			checker.warning(scores, "The assessment " + name + " has " + values.size() + " scores, more than the "
					+ participants + " participants reported: \"ScoreValue indicates one learner's score on the "
					+ "assessment\".");
		}
		if ("Local".equals(method) && Paths.text(scores, "Description") == null) {
			checker.info(scores, "The assessment " + name + " uses a Local score method: define the local method in "
					+ "the Description.");
		}
		record Range(BigDecimal min, BigDecimal max, boolean integer, String meaning) {
		}
		Range range = switch (method == null ? "" : method) {
			case "Percent" -> new Range(BigDecimal.ZERO, BigDecimal.valueOf(100), false,
					"a percent of items correct is a number between 0 and 100");
			case "Percentile" -> new Range(BigDecimal.ZERO, BigDecimal.valueOf(100), false,
					"a percentile is a number between 0 and 100");
			case "Stanine" -> new Range(BigDecimal.ONE, BigDecimal.valueOf(9), true,
					"a stanine is a score from 1 to 9 on a nine-point standard scale");
			case "NormalCurve" -> new Range(BigDecimal.ONE, BigDecimal.valueOf(99), false,
					"a normal curve equivalent ranges from 1 to 99");
			case "Scaled", "Standard", "Raw", "Equated" -> new Range(null, null, false, "a " + method.toLowerCase(
					Locale.ROOT) + " score is a number");
			default -> null;
		};
		if (range == null) {
			return;
		}
		for (String value : values) {
			BigDecimal number = Paths.decimal(value);
			boolean valid = number != null && (range.min() == null || number.compareTo(range.min()) >= 0)
					&& (range.max() == null || number.compareTo(range.max()) <= 0)
					&& (!range.integer() || number.stripTrailingZeros().scale() <= 0);
			if (!valid) {
				checker.error(scores, "The score \"" + value + "\" of the assessment " + name + " doesn't fit the "
						+ "ScoreMethod " + method + ": " + range.meaning() + ".");
			}
		}
	}

	// ----- Survey items documents -----

	private static void checkSurveyItems(Checker checker, EObject root) {
		List<EObject> items = Paths.objects(root, "SurveyItem");
		List<EObject> comparisons = Paths.objects(root, "Comparison");
		if (items.isEmpty() && comparisons.isEmpty()) {
			checker.warning(root, "The document defines no survey items: add a SurveyItem or a Comparison.");
		}
		Map<String, EObject> ids = new HashMap<>();
		List<EObject> all = new ArrayList<>(items);
		for (EObject comparison : comparisons) {
			List<EObject> compared = Paths.objects(comparison, "SurveyItemForComparison");
			if (compared.size() < 2) {
				checker.warning(comparison, "The Comparison contains " + plural(compared.size(), "survey item")
						+ ": it contains \"a set of related survey items\"; add the items it is compared with.");
			}
			all.addAll(compared);
		}
		for (EObject item : all) {
			String question = Paths.text(item, "SurveyQuestion");
			if (question == null && !Paths.values(item, "SurveyQuestion").isEmpty()) {
				checker.error(item, "The SurveyQuestion is blank: give the text of the survey question.");
			}
			String key = key(item);
			if (key != null && ids.putIfAbsent(key, item) != null) {
				checker.error(item, "The survey item id " + describe(item) + " is used by another survey item: the id "
						+ "\"indicates the item identifier in the source vocabulary referenced\" and must identify one "
						+ "item.");
			}
			checkReference(checker, item, question);
			EObject responses = first(item, "Responses");
			if (responses == null) {
				continue;
			}
			EObject yesNo = first(responses, "YesNo");
			if (yesNo != null) {
				Stream.of(first(yesNo, "Yes"), first(yesNo, "No")).filter(Objects::nonNull)
						.forEach(answer -> checkResponseReference(checker, item, answer));
			}
			EObject scale = first(responses, "Scale");
			if (scale != null) {
				for (EObject point : checkScale(checker, scale, true)) {
					checkResponseReference(checker, item, point);
				}
			}
			for (String name : List.of("MultipleChoiceSelectOne", "MultipleChoiceSelectMany")) {
				EObject choice = first(responses, name);
				if (choice == null) {
					continue;
				}
				List<EObject> options = Paths.objects(choice, "ResponseItem");
				if (options.size() < 2) {
					checker.warning(choice, name + " has " + plural(options.size(), "response item") + ": a multiple "
							+ "choice question offers at least two response choices.");
				}
				Set<String> texts = new HashSet<>();
				for (EObject option : options) {
					String text = Paths.content(option);
					if (text == null || text.isBlank()) {
						checker.error(option, "A ResponseItem is empty: give the text of the response choice.");
					} else if (!texts.add(normalize(text))) {
						checker.warning(option, "The response choice \"" + text.strip() + "\" is listed twice.");
					}
					checkResponseReference(checker, item, option);
				}
			}
			if (MEDBIQ.equals(Paths.text(item, "@source")) && MEDBIQ_ITEMS.containsKey(Paths.text(item, "@id"))) {
				StandardItem standard = MEDBIQ_ITEMS.get(Paths.text(item, "@id"));
				String kind = Stream.of("YesNo", "Scale", "MultipleChoiceSelectOne", "MultipleChoiceSelectMany",
						"FreeText").filter(n -> first(responses, n) != null).findFirst().orElse(null);
				if (kind != null && !kind.equals(standard.responses())) {
					checker.warning(responses, "The MedBiquitous survey item " + Paths.text(item, "@id") + " has "
							+ standard.responses() + " responses, not " + kind + ".");
				}
			}
		}
	}

	// ----- Shared checks -----

	/**
	 * Checks a scale (ScaleData of a report, or Scale of a survey item): its points, the point elements used and
	 * their labels. Returns the point elements.
	 */
	private static List<EObject> checkScale(Checker checker, EObject scale, boolean definition) {
		String name = definition ? "Scale" : "ScaleData";
		List<EObject> points = new ArrayList<>();
		int last = 0;
		for (int i = 0; i < POINTS.size(); i++) {
			EObject point = first(scale, POINTS.get(i));
			if (point != null) {
				points.add(point);
				last = i + 1;
			}
		}
		if (definition && Paths.text(scale, "@type") == null) {
			checker.warning(scale, "The Scale has no type: indicate the type of scale, for example likert, "
					+ "unsafe/safe, ineffective/effective.");
		}
		String pointsText = Paths.text(scale, "@points");
		BigDecimal declared = Paths.decimal(pointsText);
		if (pointsText == null) {
			if (definition) {
				checker.warning(scale, "The Scale has no points attribute: indicate the number of points in the scale "
						+ "(" + last + ").");
			}
		} else if (declared == null || declared.stripTrailingZeros().scale() > 0 || declared.intValue() < 3
				|| declared.intValue() > 10) {
			checker.error(scale, "The points of the " + name + " are \"" + pointsText + "\": the value \"must be an "
					+ "integer that is equal to or higher than 3 and no higher than 10\".");
		} else {
			int count = declared.intValue();
			if (last > count) {
				checker.error(scale, "The " + name + " has " + count + " points but uses the point " + POINTS.get(last - 1)
						+ ": \"scales should only use subelements corresponding to the number of points in the "
						+ "scale\". Correct the points attribute or remove the extra points.");
			} else if (last < count) {
				checker.add(definition ? Severity.ERROR : Severity.WARNING, scale, "The " + name + " has " + count
						+ " points but only " + plural(last, "point element") + ": "
						+ (definition ? "define a label and identifier for each point of the scale."
								: "give the number of respondents for each point, 0 where nobody chose it."));
			}
		}
		if (!points.isEmpty()) {
			EObject firstPoint = points.getFirst();
			EObject lastPoint = points.getLast();
			if (Paths.text(firstPoint, "@label") == null || Paths.text(lastPoint, "@label") == null) {
				checker.warning(scale, "Label the first and last point of the " + name + ": \"labels should be used "
						+ "for the first and last point on a scale at a minimum\".");
			}
		}
		return points;
	}

	/** Checks the source and id of a survey item, and resolves references to the MedBiquitous survey items. */
	private static void checkReference(Checker checker, EObject item, String question) {
		String source = Paths.text(item, "@source");
		String id = Paths.text(item, "@id");
		if (id != null && source == null) {
			checker.info(item, "The survey item has an id (" + id + ") but no source: the id \"indicates the item "
					+ "identifier in the source vocabulary referenced\"; name the source.");
		}
		if (!MEDBIQ.equals(source) || id == null) {
			return;
		}
		StandardItem standard = MEDBIQ_ITEMS.get(id);
		if (standard == null) {
			checker.warning(item, "The survey item " + id + " of source " + MEDBIQ + " is not defined: the "
					+ "MedBiquitous survey items are " + String.join(", ", MEDBIQ_ITEMS.keySet().stream().sorted(
							(a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b))).toList())
					+ ". Correct the id, or use the source of your own survey items.");
		} else if (question != null && !normalize(question).equals(normalize(standard.question()))) {
			checker.info(item, "The question differs from the MedBiquitous survey item " + id + ": \""
					+ standard.question() + "\".");
		}
	}

	/** Checks the identifier of a response: the MedBiquitous responses, and one response per identifier. */
	private static void checkResponseReference(Checker checker, EObject item, EObject response) {
		String source = Paths.text(response, "@source");
		String id = Paths.text(response, "@id");
		if (id == null) {
			return;
		}
		String kind = responseKind(response);
		String label = responseLabel(response);
		if (MEDBIQ.equals(source)) {
			StandardResponse standard = MEDBIQ_RESPONSES.get(id);
			String itemId = MEDBIQ.equals(Paths.text(item, "@source")) ? Paths.text(item, "@id") : null;
			if (standard == null) {
				checker.warning(response, "The response " + id + " of source " + MEDBIQ + " is not defined in the "
						+ "MedBiquitous survey items. Correct the id, or use the source of your own responses.");
			} else if (!standard.kind().equals(kind)
					|| standard.label() != null && label != null && !normalize(standard.label()).equals(normalize(label))) {
				checker.warning(response, "The response " + id + " of source " + MEDBIQ + " is \""
						+ (standard.label() == null ? standard.kind() : standard.label()) + "\" ("
						+ standard.kind() + ") in the MedBiquitous survey items, not \""
						+ (label == null ? kind : label) + "\". Correct the id.");
			} else if (standard.item() != null && itemId != null && MEDBIQ_ITEMS.containsKey(itemId)
					&& !standard.item().equals(itemId)) {
				checker.warning(response, "The response " + id + " belongs to the MedBiquitous survey item "
						+ standard.item() + ", not to the survey item " + itemId + ". Correct the id.");
			}
			return;
		}
		String key = (source == null ? "" : source) + "\u0000" + id;
		EObject previous = checker.responses.putIfAbsent(key, response);
		if (previous == null) {
			return;
		}
		String previousLabel = responseLabel(previous);
		if (!responseKind(previous).equals(kind)
				|| previousLabel != null && label != null && !normalize(previousLabel).equals(normalize(label))) {
			checker.warning(response, "The response id " + id + (source == null ? "" : " of source " + source)
					+ " identifies another response (\"" + (previousLabel == null ? responseKind(previous)
							: previousLabel) + "\"): a response identifier must identify one response.");
		}
	}

	private static String responseKind(EObject response) {
		String name = name(response);
		return switch (name) {
			case "ResponseItem", "ResponseItemData", "AssessmentResponseItemData" -> "ResponseItem";
			default -> name;
		};
	}

	private static String responseLabel(EObject response) {
		return switch (responseKind(response)) {
			case "ResponseItem" -> name(response).equals("ResponseItem") ? blankToNull(Paths.content(response))
					: Paths.text(response, "ResponseText");
			case "Yes", "No" -> null;
			default -> Paths.text(response, "@label");
		};
	}

	private static void checkDuration(Checker checker, EObject timing) {
		String duration = timing == null ? null : Paths.text(timing, "@duration");
		if (duration == null) {
			return;
		}
		try {
			DatatypeFactory.newInstance().newDuration(duration);
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
			checker.warning(timing, "The duration \"" + duration + "\" is not an XML Schema duration: write it like "
					+ "P1D (one day) or P30D (thirty days).");
		} catch (DatatypeConfigurationException e) {
			// No datatype factory: the duration can't be checked.
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Helpers
	// ----------------------------------------------------------------------------------------------------

	private static EObject first(EObject start, String path) {
		List<EObject> objects = Paths.objects(start, path);
		return objects.isEmpty() ? null : objects.getFirst();
	}

	private static Stream<EObject> stream(EObject root) {
		List<EObject> all = new ArrayList<>();
		root.eAllContents().forEachRemaining(all::add);
		return all.stream();
	}

	/** The XML name of an element. */
	private static String name(EObject object) {
		EStructuralFeature feature = object.eContainingFeature();
		return feature == null ? "" : META.getName(feature);
	}

	/** The source and id of a survey item as a key, or {@code null} without id. */
	private static String key(EObject item) {
		String id = Paths.text(item, "@id");
		return id == null ? null : Objects.requireNonNullElse(Paths.text(item, "@source"), "") + "\u0000" + id;
	}

	private static String describe(EObject item) {
		String source = Paths.text(item, "@source");
		return Paths.text(item, "@id") + (source == null ? "" : " of source " + source);
	}

	private static String quote(String text) {
		if (text == null) {
			return "(without text)";
		}
		String line = text.strip().replaceAll("\\s+", " ");
		return "\"" + (line.length() > 60 ? line.substring(0, 57) + "..." : line) + "\"";
	}

	private static String normalize(String text) {
		return text.strip().replaceAll("\\s+", " ").replaceAll("[.?!]$", "").toLowerCase(Locale.ROOT);
	}

	private static String blankToNull(String text) {
		return text == null || text.isBlank() ? null : text.strip();
	}

	private static boolean bool(EObject start, String path) {
		return "true".equals(Paths.text(start, path));
	}

	@SafeVarargs
	private static BigDecimal sum(List<String>... lists) {
		BigDecimal sum = BigDecimal.ZERO;
		for (List<String> list : lists) {
			for (String text : list) {
				BigDecimal number = Paths.decimal(text);
				if (number != null) {
					sum = sum.add(number);
				}
			}
		}
		return sum;
	}

	/** Whether both numbers are known and the first is larger. */
	private static boolean larger(BigDecimal a, BigDecimal b) {
		return a != null && b != null && a.compareTo(b) > 0;
	}

}
