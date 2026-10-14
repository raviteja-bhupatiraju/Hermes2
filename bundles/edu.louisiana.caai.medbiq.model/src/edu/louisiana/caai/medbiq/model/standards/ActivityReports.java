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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;

/**
 * Activity Report 1.0 ({@code activity-report-v1}) and its 2.0 point release ({@code activity-report-v2}): reports
 * of the registration for, completion of and expiration of learning and certification activities of healthcare
 * professionals, with the credits received. Version 2.0 adds a reporting period, report identifiers ({@code arid}),
 * anonymous learner demographics ({@code Participants}), activity status, launch details and regulatory
 * information (Opioid REMS).
 */
public final class ActivityReports implements StandardContent {

	private static final String V2 = "activity-report-v2";

	private static final String REPORT = "Report";
	private static final String ACTIVITIES = "Activities";

	/** The recommended values of {@code LaunchAction} (Activity Report specification 1.54, section 10). */
	private static final List<String> LAUNCH_ACTIONS = List.of("Noaction", "Activity", "CE", "Registry");

	/** The recommended values of {@code activityCertification} (Activity Report Implementation Guidelines, 3.2). */
	private static final String[] CERTIFICATIONS = { "AMA PRA Category 1 Credit", "AMA PRA Category 2 Credit",
			"AAFP Prescribed", "AAFP Elective", "AAP", "AAP Prep", "ACEP", "ACOG", "AOA", "APA Category 1",
			"CECBEMS First Responder", "CECBEMS Basic", "CECBEMS Advanced", "CECBEMS Operational", "CECBEMS Educator",
			"CECBEMS Administrator", "CPHQ", "EBAC CE Credit Hours", "NCHEC Category 1", "NCHEC Category 2",
			"Synergy CERPS A", "Synergy CERPS B", "Synergy CERPS C", "RCP" };

	private static final Pattern ARID = Pattern.compile("arid:[^:\\s]+:\\S+");
	private static final Pattern CCID = Pattern.compile("ccid:[^:\\s]+:\\S+");

	private static boolean v2(MedbiqDocumentType type) {
		return V2.equals(type.id());
	}

	// ----------------------------------------------------------------------------------------------------
	// Titles and descriptions
	// ----------------------------------------------------------------------------------------------------

	@Override
	public String title(MedbiqDocumentType type, EObject rootElement) {
		List<EObject> reports = Paths.objects(rootElement, "ActivityReport");
		Set<String> titles = new LinkedHashSet<>();
		for (EObject report : reports) {
			for (EObject activity : Paths.objects(report, "Activity")) {
				String title = activityTitle(activity);
				if (title != null) {
					titles.add(title);
				}
			}
		}
		if (titles.size() == 1) {
			return "Activity report: " + titles.iterator().next();
		}
		int count = reports.size();
		return count + (count == 1 ? " activity report" : " activity reports");
	}

	/**
	 * The title of an activity: the Healthcare LOM title of its module if it has one module (then the module
	 * describes the whole activity), else its {@code ActivityName}.
	 */
	static String activityTitle(EObject activity) {
		List<EObject> modules = Paths.objects(activity, "Module");
		if (modules.size() == 1) {
			String lomTitle = Paths.text(modules.getFirst(), "Metadata/lom/general/title/string");
			if (lomTitle != null) {
				return lomTitle;
			}
		}
		return Paths.text(activity, "ActivityName");
	}

	@Override
	public String description(MedbiqDocumentType type, EObject rootElement) {
		List<EObject> reports = Paths.objects(rootElement, "ActivityReport");
		Set<String> organizations = new LinkedHashSet<>(Paths.texts(rootElement, "ActivityReport/ReportingOrganization"));
		int people = Paths.objects(rootElement, "ActivityReport/Member").size()
				+ Paths.objects(rootElement, "ActivityReport/Participants/Participant").size();
		Map<String, Integer> statuses = new TreeMap<>();
		Map<String, BigDecimal> credits = new TreeMap<>();
		for (EObject module : Paths.objects(rootElement, "ActivityReport/Activity/Module")) {
			String status = status(module);
			if (status != null) {
				statuses.merge(status.toLowerCase(Locale.ROOT), 1, Integer::sum);
			}
			for (EObject received : Paths.objects(module, "CreditCertificate/CreditReceived")) {
				BigDecimal number = Paths.decimal(Paths.text(received, "numberOfCredits"));
				if (number != null) {
					String unit = Objects.requireNonNullElse(Paths.text(received, "creditUnit"), "credits");
					credits.merge(unit, number, BigDecimal::add);
				}
			}
		}
		List<String> parts = new ArrayList<>();
		parts.add(reports.size() + (reports.size() == 1 ? " report" : " reports")
				+ (organizations.isEmpty() ? "" : " from " + String.join(", ", organizations)) + " on " + people
				+ (people == 1 ? " participant" : " participants"));
		if (v2(type)) {
			String start = Paths.text(rootElement, "ReportingStartDate");
			String end = Paths.text(rootElement, "ReportingEndDate");
			if (start != null && end != null) {
				parts.add("reporting period " + day(start) + " to " + day(end));
			}
		}
		if (!statuses.isEmpty()) {
			parts.add("modules: " + String.join(", ", statuses.entrySet().stream()
					.map(e -> e.getValue() + " " + e.getKey()).toList()));
		}
		if (!credits.isEmpty()) {
			parts.add("credits received: " + String.join(", ", credits.entrySet().stream()
					.map(e -> e.getValue().stripTrailingZeros().toPlainString() + " " + e.getKey()).toList()));
		}
		return String.join("; ", parts) + ".";
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		boolean v2 = v2(type);
		List<PageSpec> pages = new ArrayList<>();
		pages.add(header(v2));
		pages.add(participants(v2));
		pages.add(activities(v2));
		pages.add(status(v2));
		pages.add(credits());
		if (v2) {
			pages.add(regulation());
		}
		pages.add(pointOfCare());
		return pages;
	}

	private static PageSpec header(boolean v2) {
		List<FieldSpec> fields = new ArrayList<>();
		if (v2) {
			fields.add(field("@arid", "Report set ID (arid)"));
		}
		fields.add(field("DateTimeCreated", "Created"));
		if (v2) {
			fields.add(field("ReportingStartDate", "Reporting period start"));
			fields.add(field("ReportingEndDate", "Reporting period end"));
		}
		return page("report", "Report", REPORT, "When the reports were created" + (v2 ? " and the reporting period" : ""),
				"general", section("Activity reports", v2
						? "ActivityReports contains the reports on registration for, completion of, or expiration of "
								+ "activities of one or more healthcare professionals within a reporting period."
						: "ActivityReports contains the reports on registration for, completion of, or expiration of "
								+ "activities of one or more healthcare professionals.",
						"", fields.toArray(FieldSpec[]::new)));
	}

	private static PageSpec participants(boolean v2) {
		List<FieldSpec> details = new ArrayList<>();
		details.add(field("ReportingOrganization", "Reporting organization"));
		if (v2) {
			details.add(field("@arid", "Report ID (arid)"));
		}
		details.add(group("Member", v2 ? "Member (identified learner)" : "Member", member(v2)));
		if (v2) {
			details.add(group("Participants", "Participants (learner demographics)",
					repeat("Participant", "Participants", "LocalIdentifier", field("LocalIdentifier", "Local identifier"),
							field("LocalIdentifier/@domain", "Identifier domain"),
							field("StateOfPrimaryPractice", "State of primary practice"),
							field("DEARegistration", "DEA registration"), field("Profession"),
							field("PracticeArea", "Practice area"), field("SurgicalProcedures", "Performs surgical procedures"),
							field("TimeInPractice", "Time in practice"))));
		}
		return page("participants", "Participants", REPORT,
				"Who reports, and the person (Member)" + (v2 ? " or learners (Participants)" : "") + " of each report",
				"lifecycle", section("Activity reports", "Each ActivityReport defines the activities of a single person"
						+ (v2 ? " (Member) or the demographics of the learners of an activity (Participants)." : "."), "",
						repeat("ActivityReport", "Activity reports", "Member/Name/FormattedName",
								details.toArray(FieldSpec[]::new))));
	}

	private static FieldSpec[] member(boolean v2) {
		FieldSpec licenses = v2 ? repeat("LicenseInfo/License", "Licenses", "LicenseNumber")
				: repeat("LicenseInfo/License", "Licenses", "LicenseNumber", field("LicensureEntity", "Licensure entity"),
						field("LicenseNumber", "License number"), field("InitialLicensureDate", "Initial licensure date"),
						field("LicenseStatus", "License status"), field("LicenseExpirationDate", "Expiration date"));
		return new FieldSpec[] { field("UniqueID", "Unique IDs"),
				repeat("UniqueID", "Unique ID domains", null, field("@domain", "Domain")),
				field("Name/FormattedName", "Formatted name"), field("Name/Title", "Title"),
				field("Name/GivenName", "Given names"), field("Name/FamilyName", "Family names"),
				field("Name/Degree", "Degrees"), licenses };
	}

	/** The details of each activity report, with the path to its activities. */
	private static PageSpec activityPage(String id, String title, String description, String icon, String help,
			FieldSpec... activityDetails) {
		return page(id, title, ACTIVITIES, description, icon, section(title, help, "",
				repeat("ActivityReport", "Activity reports", "Member/Name/FormattedName",
						field("ReportingOrganization", "Reporting organization"),
						repeat("Activity", "Activities", "ActivityName", activityDetails))));
	}

	private static PageSpec activities(boolean v2) {
		List<FieldSpec> details = new ArrayList<>(List.of(field("ProviderOrganization", "Provider organization"),
				field("ActivityName", "Activity name"), field("@activityID", "Activity ID")));
		if (v2) {
			details.add(field("ActivityInstanceID", "Activity instance ID"));
			details.add(field("ActivityInstanceID/@domain", "Instance ID domain"));
		}
		details.add(repeat("Module", "Modules", "ModuleName", field("ModuleName", "Module name"),
				field("ModuleName/@moduleID", "Module ID"), field("Metadata/location", "Metadata location"),
				field("Metadata/lom", "Description (Healthcare LOM)"),
				group("Metadata/lom/healthcareMetadata/healthcareEducation", "Credits offered (Healthcare LOM)",
						field("creditsAvailable", "Credits available"),
						repeat("credits", "Credits", "activityCertification", field("accreditingBody", "Accrediting body"),
								suggest("activityCertification", "Activity certification", CERTIFICATIONS),
								field("creditType", "Credit type"), field("creditUnit", "Credit unit"),
								field("numberOfCredits", "Number of credits"),
								field("accreditedProvider", "Accredited provider")))));
		return activityPage("activities", "Activities", "The activities of each report: provider, name, modules and "
				+ "their description", "educational", "An Activity identifies the educational or other activity "
						+ "completed or started; its Modules define the parts of the activity (a single module named "
						+ "like the activity if it has no parts).",
				details.toArray(FieldSpec[]::new));
	}

	private static PageSpec status(boolean v2) {
		List<FieldSpec> details = new ArrayList<>();
		if (v2) {
			details.add(field("Status", "Activity status"));
			details.add(field("Results", "Activity results"));
		}
		details.add(repeat("Module", "Modules", "ModuleName", field("Status"),
				field("RegisteredDateTime", "Registered"), field("StartDateTime", "Started"),
				field("DueDateTime", "Due"), field("EndDateTime", "Ended"), field("CompletedDateTime", "Completed"),
				field("ExpiredDateTime", "Expired"), field("TimeEngaged", "Time engaged (ISO 8601 duration)"),
				field("Results")));
		return activityPage("status", "Status and Results", "Registration, completion and expiration of each "
				+ "module, with the time engaged and the results", "annotation", "Status is the individual's status in "
						+ "relation to the activity or module: Registered, Completed or Expired. Give the date of "
						+ "the status (RegisteredDateTime, CompletedDateTime, ExpiredDateTime).",
				details.toArray(FieldSpec[]::new));
	}

	private static PageSpec credits() {
		return activityPage("credits", "Credits", "The credits received for completing each module",
				"healthcare-education", "Repeat CreditCertificate for each type or category of credit awarded "
						+ "(for example AMA PRA Category 1 Credit and AAFP Prescribed).",
				repeat("Module", "Modules", "ModuleName", field("Status"),
						repeat("CreditCertificate", "Credit certificates", "CreditReceived/activityCertification",
								field("CreditID", "Credit ID (ccid:providerdomain:localidentifier)"),
								group("CreditReceived", "Credit received",
										field("accreditingBody", "Accrediting body"),
										suggest("activityCertification", "Activity certification", CERTIFICATIONS),
										field("creditType", "Credit type"), field("creditUnit", "Credit unit"),
										field("numberOfCredits", "Number of credits"), field("pacing"),
										field("accreditedProvider", "Accredited provider"),
										field("nonAccreditedProvider", "Non-accredited providers"),
										field("releaseDate", "Release date"), field("expirationDate", "Expiration date"),
										repeat("CreditFocus", "Credit focus", "Description", field("Description"),
												field("Code"), field("NumberOfCredits", "Number of credits"))))));
	}

	private static PageSpec regulation() {
		return activityPage("regulation", "Regulation and Launch", "Commercial support, regulations (such as the "
				+ "Opioid REMS) and how to launch each activity", "rights", "RegulatoryInformation describes the "
						+ "relationship of the activity to regulations; LaunchDetails how to launch the activity and "
						+ "return the learner.",
				field("CommercialSupporter", "Commercial supporter"),
				group("LaunchDetails", "Launch details",
						suggest("LaunchAction", "Launch action", LAUNCH_ACTIONS.toArray(String[]::new)),
						field("ActivityLocation", "Activity location"), field("ReturnLocation", "Return location")),
				group("RegulatoryInformation", "Regulatory information",
						field("CompliantToRegulation", "Compliant to regulations (URIs)"),
						repeat("CompliantToRegulation", "Compliant to regulation: labels", null, field("@label", "Label")),
						field("RelatedToRegulation", "Related to regulations (URIs)"),
						repeat("RelatedToRegulation", "Related to regulation: labels", null, field("@label", "Label")),
						repeat("RegulatoryClassification", "Regulatory classifications", "ComponentTitle",
								field("ClassificationRelation", "Classification relation"),
								field("Regulation", "Regulation (URI)"), field("Regulation/@label", "Regulation label"),
								field("ComponentID", "Component ID"), field("ComponentTitle", "Component title"))));
	}

	private static PageSpec pointOfCare() {
		return activityPage("point-of-care", "Point-of-Care Learning", "The clinical question, the resources used "
				+ "and the application to practice of point-of-care learning", "relation", "PointOfCareLearningData "
						+ "documents practice-based learning in support of specific patient care, as required to "
						+ "claim credit for Internet point-of-care learning.",
				group("PointOfCareLearningData", "Point-of-care learning data", field("@id", "ID"),
						text("ClinicalQuestionOrTopic", "Clinical question or topic"),
						field("SearchParameters", "Search parameters"),
						repeat("ResourceUsed", "Resources used", "Title", field("Title"), field("Collection"),
								field("CollectionID", "Collection ID"), field("CollectionID/@type", "Collection ID type"),
								field("ResourceLocation", "Resource location"), field("Relevant")),
						text("ApplicationToPractice", "Application to practice")));
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
		Rules rules = new Rules(v2(type));
		rules.check(root);
		return rules.problems;
	}

	/** The rules of one check of a document. */
	private static final class Rules {

		final boolean v2;
		final List<HlomProblem> problems = new ArrayList<>();
		final Map<String, EObject> creditIds = new HashMap<>();
		final Map<String, EObject> arids = new HashMap<>();
		final Map<String, EObject> members = new HashMap<>();
		LocalDate periodStart;
		LocalDate periodEnd;

		Rules(boolean v2) {
			this.v2 = v2;
		}

		void report(Severity severity, EObject object, String message) {
			problems.add(new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1));
		}

		void check(EObject root) {
			checkHeader(root);
			for (EObject report : Paths.objects(root, "ActivityReport")) {
				checkReport(report);
			}
		}

		void checkHeader(EObject root) {
			When created = When.parse(Paths.text(root, "DateTimeCreated"));
			if (created != null && created.local().toLocalDate().isAfter(LocalDate.now().plusDays(1))) {
				report(Severity.WARNING, root, "DateTimeCreated (" + created.text() + ") is in the future. Give the "
						+ "date and time this set of reports was created.");
			}
			if (!v2) {
				return;
			}
			checkArid(root, "ActivityReports");
			periodStart = Paths.date(Paths.text(root, "ReportingStartDate"));
			periodEnd = Paths.date(Paths.text(root, "ReportingEndDate"));
			if (periodStart != null && periodEnd != null && periodStart.isAfter(periodEnd)) {
				report(Severity.ERROR, root, "The ReportingStartDate (" + periodStart + ") is after the "
						+ "ReportingEndDate (" + periodEnd + "). The reporting period runs from its first day to its "
						+ "last day; correct or swap the dates.");
				periodStart = periodEnd = null;
			}
			if (created != null && periodStart != null && created.local().toLocalDate().isBefore(periodStart)) {
				report(Severity.WARNING, root, "The reports were created (DateTimeCreated " + created.text()
						+ ") before their reporting period starts (ReportingStartDate " + periodStart + "), so they "
						+ "cannot contain data of the period. Check the dates.");
			}
		}

		void checkArid(EObject object, String element) {
			String arid = Paths.text(object, "@arid");
			if (arid == null) {
				return;
			}
			if (!ARID.matcher(arid).matches()) {
				report(Severity.WARNING, object, "The arid of " + element + " (\"" + arid + "\") does not use the arid "
						+ "Uniform Resource Name format arid:domain:localidentifier, where domain is the domain name "
						+ "of the issuing organization.");
			}
			EObject first = arids.putIfAbsent(arid, object);
			if (first != null) {
				report(Severity.ERROR, object, "The arid \"" + arid + "\" is used more than once. An arid is a "
						+ "globally unique identifier of an activity report; give each report its own.");
			}
		}

		void checkReport(EObject report) {
			if (v2) {
				checkArid(report, "ActivityReport");
				if (Paths.objects(report, "Member").size() > 1) {
					report(Severity.ERROR, report, "The ActivityReport has more than one Member. An ActivityReport "
							+ "defines the activities of a single person; put each Member in its own ActivityReport.");
				}
				if (Paths.objects(report, "Participants").size() > 1) {
					report(Severity.ERROR, report, "The ActivityReport has more than one Participants element. List "
							+ "all learners as Participant elements of one Participants element.");
				}
				checkParticipants(report);
			}
			for (EObject member : Paths.objects(report, "Member")) {
				checkMember(member);
			}
			Map<String, EObject> activities = new HashMap<>();
			for (EObject activity : Paths.objects(report, "Activity")) {
				String key = String.join("|", Stream.of("@activityID", "ProviderOrganization", "ActivityName",
						"ActivityInstanceID").map(path -> normalized(Paths.text(activity, path))).toList());
				if (activities.putIfAbsent(key, activity) != null) {
					report(Severity.WARNING, activity, "The activity \"" + Paths.text(activity, "ActivityName")
							+ "\" is reported twice in the same ActivityReport. Report each activity once, with all "
							+ "its Modules.");
				}
				checkActivity(activity);
			}
		}

		void checkParticipants(EObject report) {
			Set<String> identifiers = new LinkedHashSet<>();
			for (EObject participant : Paths.objects(report, "Participants/Participant")) {
				String id = Paths.text(participant, "LocalIdentifier");
				String domain = Paths.text(participant, "LocalIdentifier/@domain");
				if (id != null && !identifiers.add(domain + " " + id)) {
					report(Severity.WARNING, participant, "The participant " + id + " (" + domain + ") is listed more "
							+ "than once. Each Participant describes a single learner; remove the duplicate.");
				}
			}
		}

		void checkMember(EObject member) {
			List<EObject> ids = Paths.objects(member, "UniqueID");
			boolean named = Stream.of("FormattedName", "FamilyName", "GivenName")
					.anyMatch(part -> !Paths.texts(member, "Name/" + part).isEmpty());
			if (ids.isEmpty() && !named) {
				report(Severity.ERROR, member, "The Member is not identified: it has neither a UniqueID nor a Name. "
						+ "Identify the person, preferably by the UniqueID of the sending (and receiving) "
						+ "organization with its domain.");
				return;
			}
			if (ids.isEmpty()) {
				report(Severity.WARNING, member, "The Member has no UniqueID. Indicate the user ID of the sending "
						+ "organization (and of the receiving organization, if identities have been mapped) with "
						+ "UniqueID, using the domain attribute for the organization that is the source of the ID.");
			}
			for (EObject id : ids) {
				String key = Paths.text(id, "@domain") + " " + Paths.content(id);
				EObject first = members.putIfAbsent(key, member);
				if (first != null && first != member) {
					report(Severity.WARNING, member, "The member " + Paths.content(id) + " (" + Paths.text(id, "@domain")
							+ ") has more than one ActivityReport. An ActivityReport defines the activities of a single "
							+ "person; report all activities of this person in one ActivityReport.");
					break;
				}
			}
		}

		void checkActivity(EObject activity) {
			Map<String, EObject> modules = new HashMap<>();
			for (EObject module : Paths.objects(activity, "Module")) {
				String id = Paths.text(module, "ModuleName/@moduleID");
				String key = id != null ? "id " + id : "name " + normalized(Paths.text(module, "ModuleName"));
				if (modules.putIfAbsent(key, module) != null) {
					report(Severity.WARNING, module, "The module \"" + Paths.text(module, "ModuleName") + "\" is "
							+ "reported twice in the activity \"" + Paths.text(activity, "ActivityName") + "\". "
							+ "Report each module once with its current status.");
				}
				checkModule(activity, module);
			}
			if (v2) {
				String action = Paths.text(activity, "LaunchDetails/LaunchAction");
				if (action != null && LAUNCH_ACTIONS.stream().noneMatch(action::equalsIgnoreCase)) {
					report(Severity.INFO, activity, "The LaunchAction \"" + action + "\" is not one of the "
							+ "recommended values Noaction, Activity, CE and Registry.");
				}
			}
		}

		void checkModule(EObject activity, EObject module) {
			String name = "The module \"" + Paths.text(module, "ModuleName") + "\"";
			String status = status(module);
			When registered = When.parse(Paths.text(module, "RegisteredDateTime"));
			When started = When.parse(Paths.text(module, "StartDateTime"));
			When ended = When.parse(Paths.text(module, "EndDateTime"));
			When due = When.parse(Paths.text(module, "DueDateTime"));
			When completed = When.parse(Paths.text(module, "CompletedDateTime"));
			When expired = When.parse(Paths.text(module, "ExpiredDateTime"));
			List<EObject> certificates = Paths.objects(module, "CreditCertificate");

			if ("Completed".equals(status) && completed == null) {
				report(Severity.WARNING, module, name + " is Completed but has no CompletedDateTime. If the status "
						+ "is completed, use CompletedDateTime to indicate when the individual completed the module.");
			}
			if (completed != null && ("Registered".equals(status) || "Expired".equals(status))) {
				report(Severity.WARNING, module, name + " has a CompletedDateTime but its Status is " + status
						+ ". Set the Status to Completed or remove the CompletedDateTime.");
			}
			if ("Expired".equals(status) && expired == null) {
				report(Severity.INFO, module, name + " is Expired; give the ExpiredDateTime, the date and time the "
						+ "individual's access to the module expired.");
			}
			if ("Registered".equals(status) && registered == null && started == null) {
				report(Severity.INFO, module, name + " is Registered; give the RegisteredDateTime (or the "
						+ "StartDateTime, when the individual started the module).");
			}
			order(module, name, "StartDateTime", started, "EndDateTime", ended);
			order(module, name, "RegisteredDateTime", registered, "CompletedDateTime", completed);
			order(module, name, "StartDateTime", started, "CompletedDateTime", completed);
			order(module, name, "RegisteredDateTime", registered, "ExpiredDateTime", expired);
			if (due != null && registered != null && due.compareTo(registered) < 0) {
				report(Severity.WARNING, module, name + " is due (DueDateTime " + due.text() + ") before the individual "
						+ "registered (RegisteredDateTime " + registered.text() + "). Check the dates.");
			}
			if (v2) {
				inPeriod(module, name, "CompletedDateTime", completed);
				inPeriod(module, name, "ExpiredDateTime", expired);
			}
			checkCredits(module, name, status, certificates);
		}

		void order(EObject module, String name, String first, When before, String second, When after) {
			if (before != null && after != null && before.compareTo(after) > 0) {
				report(Severity.ERROR, module, name + ": the " + second + " (" + after.text() + ") is before the "
						+ first + " (" + before.text() + "). Correct the dates.");
			}
		}

		void inPeriod(EObject module, String name, String element, When when) {
			if (when == null || periodStart == null || periodEnd == null) {
				return;
			}
			LocalDate day = when.local().toLocalDate();
			if (day.isBefore(periodStart) || day.isAfter(periodEnd)) {
				report(Severity.WARNING, module, name + ": the " + element + " (" + when.text() + ") is outside the "
						+ "reporting period " + periodStart + " to " + periodEnd + " (ReportingStartDate, "
						+ "ReportingEndDate). Report it in the period it belongs to, or correct the period.");
			}
		}

		void checkCredits(EObject module, String name, String status, List<EObject> certificates) {
			EObject education = Paths.objects(module, "Metadata/lom/healthcareMetadata/healthcareEducation").stream()
					.findFirst().orElse(null);
			String creditsAvailable = education == null ? null : Paths.text(education, "creditsAvailable");
			List<EObject> offered = education == null ? List.of() : Paths.objects(education, "credits");
			boolean creditsOffered = "yes".equals(creditsAvailable) || offered.stream()
					.anyMatch(credit -> positive(Paths.decimal(Paths.text(credit, "numberOfCredits"))));
			if ("Completed".equals(status) && certificates.isEmpty() && creditsOffered
					&& !"no".equals(creditsAvailable)) {
				report(Severity.WARNING, module, name + " is Completed and its metadata offers credits, but no "
						+ "CreditCertificate is reported. Describe the credits received with a CreditCertificate for "
						+ "each type of credit awarded.");
			}
			if (!certificates.isEmpty() && status != null && !"Completed".equals(status)) {
				report(Severity.WARNING, module, name + " has a CreditCertificate but its Status is " + status + ". "
						+ "Credits are received for completing a module; set the Status to Completed or remove the "
						+ "credits.");
			}
			if (!certificates.isEmpty() && "no".equals(creditsAvailable)) {
				report(Severity.WARNING, module, name + " has a CreditCertificate but its Healthcare LOM metadata says "
						+ "no credits are available (creditsAvailable is no). Correct the metadata or the credits.");
			}
			Set<String> kinds = new LinkedHashSet<>();
			for (EObject certificate : certificates) {
				checkCertificate(name, certificate, offered, kinds);
			}
		}

		void checkCertificate(String name, EObject certificate, List<EObject> offered, Set<String> kinds) {
			String creditId = Paths.text(certificate, "CreditID");
			if (creditId != null) {
				if (!CCID.matcher(creditId).matches()) {
					report(Severity.WARNING, certificate, "The CreditID \"" + creditId + "\" does not use the Credit "
							+ "Certificate Identifier format ccid:providerdomain:localidentifier, where providerdomain "
							+ "is the domain name of the provider of the activity.");
				}
				if (creditIds.putIfAbsent(creditId, certificate) != null) {
					report(Severity.ERROR, certificate, "The CreditID \"" + creditId + "\" is used more than once. A "
							+ "CreditID is a globally unique identifier of the credits awarded to a learner; give each "
							+ "credit certificate its own.");
				}
			}
			EObject received = Paths.objects(certificate, "CreditReceived").stream().findFirst().orElse(null);
			if (received == null) {
				return;
			}
			String certification = Paths.text(received, "activityCertification");
			String kind = normalized(certification) + "|" + normalized(Paths.text(received, "creditType")) + "|"
					+ normalized(Paths.text(received, "creditUnit"));
			if (!kinds.add(kind)) {
				report(Severity.WARNING, certificate, name + " has more than one CreditCertificate for "
						+ (certification == null ? "the same type of credit" : "\"" + certification + "\"")
						+ ". Use one CreditCertificate for each type or category of credit received.");
			}
			BigDecimal number = Paths.decimal(Paths.text(received, "numberOfCredits"));
			if (number == null) {
				report(Severity.WARNING, certificate, name + " has a CreditReceived without numberOfCredits. Give the "
						+ "number of credits the individual received for completing the module.");
				return;
			}
			if (!positive(number)) {
				report(Severity.WARNING, certificate, name + " reports " + plain(number) + " credits received. A "
						+ "CreditCertificate describes credits awarded; give a positive numberOfCredits or remove it.");
			}
			BigDecimal available = available(offered, received);
			if (available != null && number.compareTo(available) > 0) {
				report(Severity.WARNING, certificate, name + " reports " + plain(number) + " credits received"
						+ (certification == null ? "" : " (" + certification + ")") + ", more than the "
						+ plain(available) + " credits its Healthcare LOM metadata offers. Credits received cannot "
						+ "exceed the credits designated for the activity.");
			}
			for (EObject focus : Paths.objects(received, "CreditFocus")) {
				BigDecimal focusCredits = Paths.decimal(Paths.text(focus, "NumberOfCredits"));
				if (focusCredits != null && focusCredits.compareTo(number) > 0) {
					report(Severity.WARNING, focus, "The CreditFocus \"" + Paths.text(focus, "Description") + "\" has "
							+ plain(focusCredits) + " credits, more than the " + plain(number) + " credits of its "
							+ "credit certificate. A credit focus is a category included in the certificate.");
				}
			}
		}

		/** The credits the metadata offers of the kind received: by certification, else by type and unit. */
		static BigDecimal available(List<EObject> offered, EObject received) {
			String certification = normalized(Paths.text(received, "activityCertification"));
			String typeAndUnit = normalized(Paths.text(received, "creditType")) + "|"
					+ normalized(Paths.text(received, "creditUnit"));
			BigDecimal byType = null;
			for (EObject credit : offered) {
				BigDecimal number = Paths.decimal(Paths.text(credit, "numberOfCredits"));
				if (number == null) {
					continue;
				}
				String offeredCertification = normalized(Paths.text(credit, "activityCertification"));
				if (!certification.isEmpty() && certification.equals(offeredCertification)) {
					return number;
				}
				String offeredType = normalized(Paths.text(credit, "creditType")) + "|"
						+ normalized(Paths.text(credit, "creditUnit"));
				if ((certification.isEmpty() || offeredCertification.isEmpty()) && typeAndUnit.equals(offeredType)) {
					byType = byType == null ? number : byType.max(number);
				}
			}
			return byType;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Values
	// ----------------------------------------------------------------------------------------------------

	/**
	 * A date and time as written ({@code xsd:dateTime}), with its offset if it has one.
	 *
	 * @param text   the text
	 * @param local  the local date and time
	 * @param offset the offset, or {@code null}
	 */
	record When(String text, LocalDateTime local, ZoneOffset offset) implements Comparable<When> {

		static When parse(String text) {
			if (text == null) {
				return null;
			}
			try {
				OffsetDateTime dateTime = OffsetDateTime.parse(text.replace("Z", "+00:00"));
				return new When(text, dateTime.toLocalDateTime(), dateTime.getOffset());
			} catch (DateTimeParseException e) {
				try {
					return new When(text, LocalDateTime.parse(text), null);
				} catch (DateTimeParseException e2) {
					return null;
				}
			}
		}

		@Override
		public int compareTo(When other) {
			if (offset != null && other.offset != null) {
				return local.toInstant(offset).compareTo(other.local.toInstant(other.offset));
			}
			return local.compareTo(other.local);
		}
	}

	/**
	 * The status of a module (an enumeration value: {@link Paths#text} reads values of dynamic enumerations, which
	 * are model objects, as having no text).
	 */
	static String status(EObject module) {
		for (Object value : Paths.values(module, "Status")) {
			if (value instanceof Enumerator literal) {
				return literal.getLiteral();
			}
			if (value != null) {
				return value.toString();
			}
		}
		return null;
	}

	private static String day(String text) {
		LocalDate date = Paths.date(text);
		return date == null ? text : date.toString();
	}

	private static boolean positive(BigDecimal number) {
		return number != null && number.signum() > 0;
	}

	private static String plain(BigDecimal number) {
		return number.stripTrailingZeros().toPlainString();
	}

	private static String normalized(String text) {
		return text == null ? "" : text.strip().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
	}

	// ----------------------------------------------------------------------------------------------------
	// Search and help
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		List<FieldGroup> groups = new ArrayList<>(List.of(
				FieldGroup.of("ar-organizations", "Reporting and provider organizations", "ReportingOrganization",
						"ProviderOrganization", "accreditedProvider", "CommercialSupporter"),
				FieldGroup.of("ar-activities", "Activities and modules", "ActivityName", "ModuleName"),
				FieldGroup.of("ar-participants", "Members and participants", "Member", "Participant"),
				FieldGroup.of("ar-status", "Status", "Status"),
				FieldGroup.of("ar-results", "Results", "Results"),
				FieldGroup.of("ar-credits", "Credits received", "CreditCertificate"),
				FieldGroup.of("ar-point-of-care", "Point-of-care learning", "PointOfCareLearningData")));
		if (v2(type)) {
			groups.add(FieldGroup.of("ar-regulation", "Regulatory information", "RegulatoryInformation",
					"CommercialSupporter"));
		}
		return groups;
	}

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		Map<String, String> help = new LinkedHashMap<>(HELP);
		if (v2(type)) {
			help.putAll(HELP_V2);
		}
		return help;
	}

	private static final Map<String, String> HELP = Map.ofEntries(
			Map.entry("ActivityReports", "The root element. It contains information on registration for, completion "
					+ "of, or expiration of learning activities, maintenance of competence activities, or other relevant "
					+ "activities for one or more healthcare professionals."),
			Map.entry("DateTimeCreated", "Defines the date and time this set of reports was created, for example "
					+ "2018-01-23T09:00:00."),
			Map.entry("ActivityReport", "Contains subelements that define the activities of a single person (a "
					+ "physician or other medical professional)."),
			Map.entry("ReportingOrganization", "The name of the organization that is the source of the activity "
					+ "report."),
			Map.entry("Member", "Imported from the MedBiquitous Healthcare Professional Profile: name and profile "
					+ "information for a single person. Identify the person with the UniqueID of the sending (and, if "
					+ "identities were mapped, the receiving) organization."),
			Map.entry("UniqueID", "A unique identifier of the person (Professional Profile). The domain attribute "
					+ "indicates the organization that is the source of the ID."),
			Map.entry("@domain", "The organization or domain that is the source of the identifier."),
			Map.entry("Name", "The person's name (Professional Profile)."),
			Map.entry("FormattedName", "The full name as it should be displayed."),
			Map.entry("GivenName", "A given (first or middle) name; repeat for each given name."),
			Map.entry("FamilyName", "The family name (surname)."),
			Map.entry("Degree", "An academic or professional degree, such as MD or DO."),
			Map.entry("Title", "The title of a learning resource used (ResourceUsed), or a person's title such as Dr. "
					+ "(Name)."),
			Map.entry("LicenseInfo", "The person's licenses to practice (Professional Profile)."),
			Map.entry("Activity", "Identifies the type of educational or other activity completed or started by an "
					+ "individual; for a continuing education activity, the activity itself. It defines one activity "
					+ "and its status for one person."),
			Map.entry("@activityID", "A unique identifier for this activity."),
			Map.entry("ProviderOrganization", "The name of the organization that is the official provider of this "
					+ "activity. For continuing education activities, the accredited CE provider."),
			Map.entry("ActivityName", "The name of the activity completed, for example PREP Self Assessment; for a "
					+ "continuing education activity, the title of the educational activity or course. It may be a "
					+ "broad category whose specific topics are modules."),
			Map.entry("Module", "Defines an educational or other activity and an individual's status in relation to "
					+ "it. If the activity does not consist of multiple modules, use one Module whose ModuleName is "
					+ "identical to the ActivityName."),
			Map.entry("ModuleName", "The name of the module within this activity, for example Asthma within the "
					+ "Pediatrics eQIPP activity."),
			Map.entry("@moduleID", "A unique identifier for this module (its id within the provider organization)."),
			Map.entry("Metadata", "Provides or references descriptive metadata for the activity: a full Healthcare "
					+ "LOM record (lom) or the location of one."),
			Map.entry("lom", "A full learning object metadata record of the activity using Healthcare LOM: title, "
					+ "description, keywords and, in healthcareMetadata, the credits available."),
			Map.entry("location", "A URL or other location reference for a Healthcare LOM metadata record of the "
					+ "activity."),
			Map.entry("Status", "The individual's status in relation to this activity or module. Valid values are "
					+ "Registered, Completed and Expired."),
			Map.entry("StartDateTime", "The date and time the individual started this module."),
			Map.entry("EndDateTime", "The date and time that the individual ended the module."),
			Map.entry("DueDateTime", "The final date and time for this individual to complete this module according "
					+ "to the sending organization (the receiving organization may have a different due date)."),
			Map.entry("RegisteredDateTime", "The date and time the individual registered for this module."),
			Map.entry("CompletedDateTime", "The date and time the individual completed this module."),
			Map.entry("ExpiredDateTime", "The date and time the individual's access to the module expired."),
			Map.entry("TimeEngaged", "The amount of time the participant spent engaged in the activity, as an ISO "
					+ "8601 duration (for example PT1H30M)."),
			Map.entry("Results", "The individual's results for this module or activity, for example pass or fail."),
			Map.entry("CreditCertificate", "Describes and identifies the credits received for completing this "
					+ "module. If a learner received multiple types of credit, repeat CreditCertificate for each type."),
			Map.entry("CreditReceived", "The credits (CME, CE, CPE or other professional credit) the individual "
					+ "received for completing this module; it extends the credits type of Healthcare LOM."),
			Map.entry("CreditID", "A globally unique identifier for the specific credits awarded by the provider to "
					+ "the learner, in the format ccid:providerdomain:localidentifier (for example "
					+ "ccid:aap.org:123654.789456.amapra1)."),
			Map.entry("accreditingBody", "The organization that accredits the provider, for example ACCME."),
			Map.entry("activityCertification", "The category of credit awarded, for example AMA PRA Category 1 "
					+ "Credit or AAFP Prescribed."),
			Map.entry("creditType", "The type of credit awarded: CME, CE, CNE, CPE, CHES or CPD."),
			Map.entry("creditUnit", "The unit of credit awarded: CECH, CEH, CEU, Cognate, Contact Hour, Credit, Hour, "
					+ "Unit, Credit Hour or Point."),
			Map.entry("numberOfCredits", "The number of credits awarded (or, in the metadata, available). It can be "
					+ "a decimal if the credit system accepts fractional credits, for example 1.5."),
			Map.entry("pacing", "Whether the activity is learner paced or provider paced."),
			Map.entry("accreditedProvider", "The accredited provider of the activity."),
			Map.entry("nonAccreditedProvider", "An organization that provides the activity jointly with the "
					+ "accredited provider but is not itself accredited."),
			Map.entry("releaseDate", "The date the activity was released for credit."),
			Map.entry("expirationDate", "The date after which credit can no longer be claimed for the activity."),
			Map.entry("creditsAvailable", "Whether credits are available for the activity (yes or no)."),
			Map.entry("credits", "Credits available for the activity (Healthcare LOM)."),
			Map.entry("CreditFocus", "A category or area of focus included in this credit certificate, such as a "
					+ "subspecialty certificate or category of credit."),
			Map.entry("Description", "A text description or category of the credit focus."),
			Map.entry("Code", "A succinct code matching the description of the credit focus."),
			Map.entry("NumberOfCredits", "How many credits are associated with this credit focus or category."),
			Map.entry("PointOfCareLearningData", "Data about a point of care learning experience: the clinical "
					+ "question, the resources used and the application to practice."),
			Map.entry("@id", "A unique identifier for this set of point of care learning data."),
			Map.entry("ClinicalQuestionOrTopic", "The clinical question asked or topic selected by the learner to "
					+ "begin the search for learning resources at the point of care."),
			Map.entry("SearchParameters", "The search parameters entered by the learner if a collection search was "
					+ "used to find relevant resources at the point of care."),
			Map.entry("ResourceUsed", "A learning resource used as part of this point of care learning experience; "
					+ "repeat for every resource used. If the resource is an article in a collection, use the title of "
					+ "the article."),
			Map.entry("Collection", "The name of the collection that is the source of the article, for example The "
					+ "Cochrane Library."),
			Map.entry("CollectionID", "The unique identifier of the article used by the collection; if the article "
					+ "has a digital object identifier (DOI), it may be used."),
			Map.entry("@type", "The type of identifier used, for example DOI, PubMed or URI."),
			Map.entry("ResourceLocation", "The URL of the article or resource."),
			Map.entry("Relevant", "Whether or not the learner found the resource relevant (true or false)."),
			Map.entry("ApplicationToPractice", "How the learner describes applying the learning resource to his or "
					+ "her practice; it may indicate that the learner was unable to apply the learning."),
			Map.entry("XtensibleInfo", "Subelements defined by the organization implementing the schema. Included "
					+ "for backward compatibility; not the preferred method of extension."));

	private static final Map<String, String> HELP_V2 = Map.ofEntries(
			Map.entry("@arid", "A globally unique identifier for this document or activity report, in the arid "
					+ "Uniform Resource Name format arid:domain:localidentifier, where domain is the domain name of the "
					+ "issuing organization."),
			Map.entry("ReportingStartDate", "Defines the first day of the reporting period for the data in this "
					+ "activity report, for example 2018-01-23."),
			Map.entry("ReportingEndDate", "Defines the last day of the reporting period for the data in this "
					+ "activity report."),
			Map.entry("Participants", "Describes demographic data about the learners that participated in the "
					+ "described activity. An ActivityReport requires a Member and/or Participants."),
			Map.entry("Participant", "Demographic details for a single learner."),
			Map.entry("LocalIdentifier", "An alphanumeric identifier for the learner used internally by the reporting "
					+ "organization; it identifies the learner for auditing but does not allow other organizations to "
					+ "identify the learner. Its domain has the format idd:domainname:localidentifier."),
			Map.entry("StateOfPrimaryPractice", "The state that the learner has identified as being the primary state "
					+ "in which he or she practices."),
			Map.entry("DEARegistration", "Whether the learner is able to prescribe controlled substances and, if so, "
					+ "the type of registration: Individual, Institutional or None."),
			Map.entry("Profession", "The learner's profession, for example Physician, Advanced practice nurse, "
					+ "Physician Assistant, Nurse or Pharmacist."),
			Map.entry("PracticeArea", "The clinical area in which the learner practices."),
			Map.entry("SurgicalProcedures", "Whether the learner performs surgical procedures."),
			Map.entry("TimeInPractice", "The length of time the learner has been in practice, from Trainee (student, "
					+ "intern, resident, fellow) to 21+ years."),
			Map.entry("ActivityInstanceID", "An alphanumeric identifier for this instance of the activity; its domain "
					+ "(idd:domainname:localidentifier) defines the organization that is the source of the identifier."),
			Map.entry("LaunchDetails", "Information required to launch an activity and return the learner to a "
					+ "predefined location."),
			Map.entry("LaunchAction", "The user's desired action while launching the activity. Recommended values: "
					+ "Noaction, Activity (start, resume or review), CE (claim credit or view the certificate) and "
					+ "Registry (access the associated clinical registry)."),
			Map.entry("ActivityLocation", "The location to launch this activity; it may include a token required "
					+ "for access."),
			Map.entry("ReturnLocation", "The location to which the learner will return upon exiting the activity."),
			Map.entry("CommercialSupporter", "The organization providing commercial support for the activity."),
			Map.entry("RegulatoryInformation", "Elements that describe the relationship of this activity to any "
					+ "regulations, such as the FDA Opioid REMS."),
			Map.entry("CompliantToRegulation", "The URI of a regulation whose requirements the activity is compliant "
					+ "to; the label attribute names the regulation (for example ER/LA Opioid REMS)."),
			Map.entry("RelatedToRegulation", "The URI of a regulation the activity is related to; the label "
					+ "attribute names the regulation."),
			Map.entry("@label", "A label for the regulation, for example ER/LA Opioid REMS."),
			Map.entry("RegulatoryClassification", "How the activity relates to a classification system used within "
					+ "the regulation, such as the Blueprint of a Risk Evaluation and Mitigation Strategy."),
			Map.entry("ClassificationRelation", "Whether the activity's post course assessment (Assessment "
					+ "Addresses) or its instruction (Instruction Addresses) addresses the regulation component."),
			Map.entry("Regulation", "The URI of the regulation that contains the classification system used to "
					+ "classify this activity."),
			Map.entry("ComponentID", "The identifier for the component of the classification system, for example "
					+ "II."),
			Map.entry("ComponentTitle", "The title for the component of the classification system."));
}
