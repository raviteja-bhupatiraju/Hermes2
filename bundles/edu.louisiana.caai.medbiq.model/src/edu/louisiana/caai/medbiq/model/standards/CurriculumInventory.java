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

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;

/**
 * Curriculum Inventory 1.0 (ANSI/MEDBIQ CI.10.1-2013) and the AAMC point release "v10" (2022 upload season): the
 * pages of a curriculum report (report, institution and program, events, expectations, academic levels, sequence,
 * integration), the rules of the specification that the schemas leave out (references that must resolve, unique
 * identifiers, levels, dates and sequence block structure) and the help of the elements.
 * <p>
 * The two versions differ in a few places: v10 replaces the {@code Level} of a sequence block with
 * {@code SequenceBlockLevels} ({@code StartingAcademicLevel} and {@code EndingAcademicLevel}), makes the report's
 * {@code Description} optional and adds the {@code instructionalMethodDuration} attribute of instructional methods.
 */
public final class CurriculumInventory implements StandardContent {

	/** The id of the document type of version 1.0. */
	public static final String V1 = "curriculum-inventory-v1";
	/** The id of the document type of the AAMC point release "v10". */
	public static final String V10 = "curriculum-inventory-v10";

	/**
	 * A term of the Curriculum Inventory Standardized Instructional and Assessment Methods and Resource Types
	 * (AAMC/MedBiquitous, March 2016).
	 *
	 * @param code the identifier ({@code IM…} instructional methods, {@code AM…} assessment methods, {@code RE…}
	 *             resource types), used as {@code sourceID}
	 * @param name the term
	 */
	public record Term(String code, String name) {
	}

	/** The standardized vocabulary (wording as published; © 2016 AAMC, used under the MedBiquitous license). */
	public static final List<Term> VOCABULARY = List.of(
			term("IM001", "Case-Based Instruction/Learning"),
			term("IM002", "Clinical Experience - Ambulatory"),
			term("IM003", "Clinical Experience - Inpatient"),
			term("IM004", "Concept Mapping"),
			term("IM005", "Conference"),
			term("IM006", "Demonstration"),
			term("IM007", "Discussion, Large Group (>12)"),
			term("IM008", "Discussion, Small Group [≤12]"),
			term("IM009", "Games"),
			term("IM010", "Independent Learning"),
			term("IM011", "Journal Club"),
			term("IM012", "Laboratory"),
			term("IM013", "Lecture"),
			term("IM014", "Mentorship"),
			term("IM015", "Patient Presentation - Faculty"),
			term("IM016", "Patient Presentation - Learner"),
			term("IM017", "Peer Teaching"),
			term("IM018", "Preceptorship"),
			term("IM019", "Problem-Based Learning (PBL)"),
			term("IM020", "Reflection"),
			term("IM021", "Research"),
			term("IM022", "Role Play/Dramatization"),
			term("IM023", "Self-Directed Learning"),
			term("IM024", "Service Learning Activity"),
			term("IM025", "Simulation"),
			term("IM026", "Team-Based Learning (TBL)"),
			term("IM027", "Team-Building"),
			term("IM028", "Tutorial"),
			term("IM029", "Ward Rounds"),
			term("IM030", "Workshop"),
			term("IM031", "Patient Presentation - Patient"),
			term("AM001", "Clinical Documentation Review"),
			term("AM002", "Clinical Performance Rating/Checklist"),
			term("AM003", "Exam - Institutionally Developed, Clinical Performance"),
			term("AM004", "Exam - Institutionally Developed, Written/Computer-based"),
			term("AM005", "Exam - Institutionally Developed, Oral"),
			term("AM006", "Exam - Licensure, Clinical Performance"),
			term("AM007", "Exam - Licensure, Written/Computer-based"),
			term("AM008", "Exam - Nationally Normed/Standardized, Subject"),
			term("AM009", "Multisource Assessment"),
			term("AM010", "Narrative Assessment"),
			term("AM011", "Oral Patient Presentation"),
			term("AM012", "Participation"),
			term("AM013", "Peer Assessment"),
			term("AM014", "Portfolio-Based Assessment"),
			term("AM016", "Research or Project Assessment"),
			term("AM017", "Self-Assessment"),
			term("AM018", "Stimulated Recall"),
			term("AM019", "Exam - Institutionally Developed, Laboratory, Practical"),
			term("RE001", "Animation"),
			term("RE002", "Audience Response System"),
			term("RE003", "Audio"),
			term("RE004", "Cadaver"),
			term("RE005", "Clinical Case"),
			term("RE006", "Distance Learning - Asynchronous"),
			term("RE007", "Distance Learning - Synchronous"),
			term("RE008", "Educational Technology"),
			term("RE009", "Electronic Health/Medical Record (EHR/EMR)"),
			term("RE010", "Film/Video"),
			term("RE011", "Key Feature"),
			term("RE012", "Mannequin"),
			term("RE013", "Medical Images"),
			term("RE014", "Mobile Application"),
			term("RE015", "Patient - Receiving Clinical Care"),
			term("RE016", "Patient - Teaching"),
			term("RE017", "Plastinated Specimens"),
			term("RE018", "Written or Visual Media (or Digital Equivalent)"),
			term("RE019", "Scenario"),
			term("RE020", "Searchable Electronic Database"),
			term("RE021", "Standardized/Simulated Patient (SP)"),
			term("RE022", "Task Trainer"),
			term("RE023", "Ultrasound"),
			term("RE024", "Virtual Patient"),
			term("RE025", "Virtual/Computerized Laboratory"),
			term("RE026", "Wet Laboratory"));

	private static final Map<String, Term> BY_CODE = new HashMap<>();
	private static final Map<String, Term> BY_NAME = new HashMap<>();

	static {
		for (Term term : VOCABULARY) {
			BY_CODE.put(term.code(), term);
			BY_NAME.put(normalized(term.name()), term);
		}
	}

	private static Term term(String code, String name) {
		return new Term(code, name);
	}

	/** The terms of a part of the vocabulary ({@code IM}, {@code AM} or {@code RE}). */
	public static List<String> terms(String prefix) {
		return VOCABULARY.stream().filter(t -> t.code().startsWith(prefix)).map(Term::name).sorted().toList();
	}

	private static boolean isV1(MedbiqDocumentType type) {
		return V1.equals(type.id());
	}

	// ----------------------------------------------------------------------------------------------------
	// Titles and descriptions
	// ----------------------------------------------------------------------------------------------------

	@Override
	public String title(MedbiqDocumentType type, EObject rootElement) {
		String title = Paths.text(rootElement, "Title");
		return title == null ? null : title.replaceAll("\\s+", " ");
	}

	@Override
	public String description(MedbiqDocumentType type, EObject rootElement) {
		String description = Paths.text(rootElement, "Description");
		if (description != null) {
			return description.replaceAll("\\s+", " ");
		}
		List<String> parts = new ArrayList<>();
		String program = Paths.text(rootElement, "Program/ProgramName");
		String institution = Paths.text(rootElement, "Institution/InstitutionName");
		if (program != null || institution != null) {
			parts.add(program == null ? institution : institution == null ? program : program + ", " + institution);
		}
		parts.add(Paths.count(Paths.objects(rootElement, "Events/Event").size(), "event"));
		parts.add(Paths.count(Paths.objects(rootElement, "Sequence/SequenceBlock").size(), "sequence block"));
		parts.add(Paths.count(Paths.objects(rootElement, "Expectations/CompetencyObject").size(), "competency object"));
		String start = Paths.text(rootElement, "ReportingStartDate");
		String end = Paths.text(rootElement, "ReportingEndDate");
		if (start != null && end != null) {
			parts.add(start + " to " + end);
		}
		return String.join("; ", parts);
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		boolean v1 = isV1(type);
		return List.of(
				page("report", "Report", "Report", "The curriculum report: identifier, title, dates and language",
						"general",
						section("Report", "The Curriculum Inventory report and the period it covers.", "",
								field("Title"), field("ReportID", "Report ID"),
								field("ReportID/@domain", "Report ID domain"), field("ReportDate"),
								field("ReportingStartDate"), field("ReportingEndDate"), field("Language"),
								text("Description", v1 ? "Description" : "Description (optional in v10)"),
								field("SupportingLink"))),
				page("program", "Institution and Program", "Report",
						"The institution whose curriculum is described and its educational program",
						"healthcare-education",
						section("Institution", "Uses the InstitutionInfoType of the Healthcare Professional Profile.",
								"Institution", field("InstitutionName"), field("InstitutionID", "Institution ID"),
								field("InstitutionID/@domain", "Institution ID domain"),
								group("Address", "Address", field("StreetAddressLine"), field("City"),
										field("StateOrProvince"), field("PostalCode"),
										field("Country/CountryCode", "Country code"),
										field("Country/CountryName", "Country name"))),
						section("Program", "The educational program the report describes.", "Program",
								field("ProgramName"), field("ProgramID", "Program ID"),
								field("ProgramID/@domain", "Program ID domain"), field("EducationalContext"),
								suggest("Profession", "Profession", "Physician", "Registered Nurse", "Pharmacist",
										"Physician Assistant", "Dentist"),
								field("Profession/@source", "Profession source"),
								field("Profession/@sourceID", "Profession source ID"), field("Specialty"),
								field("Specialty/@source", "Specialty source"),
								field("Specialty/@sourceID", "Specialty source ID"))),
				page("events", "Events", "Curriculum",
						"The education and assessment events (lectures, labs, clinical experiences, exams…)",
						"educational",
						section("Events", "Each event is a single education or assessment activity; sequence blocks "
								+ "reference events by their id.", "Events",
								repeat("Event", "Events", "Title", eventFields(v1)))),
				page("expectations", "Expectations", "Curriculum",
						"Competency objects (competencies, objectives, outcomes) and competency frameworks",
						"classification",
						section("Competency objects", "Events, sequence blocks and integration blocks reference a "
								+ "competency object by its identifier (lom/general/identifier/entry).", "Expectations",
								repeat("CompetencyObject", "Competency objects", "lom/general/title/string",
										field("lom", "Competency (Healthcare LOM)"), field("Status"),
										repeat("Category", "Categories", "@term", field("@term"), field("@scheme"),
												field("@label")),
										field("References/Reference", "References"),
										field("SupportingInformation/Link", "Supporting information link"))),
						section("Competency frameworks", "How the competency objects are organized.", "Expectations",
								repeat("CompetencyFramework", "Competency frameworks", "lom/general/title/string",
										field("lom", "Framework (Healthcare LOM)"), field("EffectiveDate"),
										field("RetiredDate"),
										repeat("Includes", "Includes", "Entry", field("Catalog"), field("Entry")),
										repeat("Relation", "Relations", "Reference1/Entry",
												field("Reference1/Entry", "Reference 1"), field("Relationship"),
												field("Reference2/Entry", "Reference 2"))))),
				page("levels", "Academic Levels", "Curriculum",
						"The levels (years, phases) of the program; sequence blocks reference them by number",
						"lifecycle",
						section("Academic levels", "LevelsInProgram must equal the number of levels.",
								"AcademicLevels", field("LevelsInProgram"),
								repeat("Level", "Levels", "Label", field("@number"), field("Label"),
										text("Description", "Description")))),
				page("sequence", "Sequence", "Curriculum",
						"Sequence blocks (years, phases, courses, clerkships) and the events they contain",
						"relation",
						section("Sequence", "How the curriculum is organized over time.", "Sequence",
								text("Description", "Description")),
						section("Sequence blocks", "Nest blocks with sequence block references (for example the "
								+ "courses of a year); list a block's events with sequence block events.", "Sequence",
								repeat("SequenceBlock", "Sequence blocks", "Title", blockFields(v1)))),
				page("integration", "Integration", "Curriculum",
						"Integration blocks: themes woven through the curriculum", "annotation",
						section("Integration", "Integrating concepts (themes) and the competency objects, events and "
								+ "sequence blocks that belong to them.", "Integration",
								text("Description", "Description"),
								repeat("IntegrationBlock", "Integration blocks", "Title", field("@id", "Id"),
										field("Title"), text("Description", "Description"),
										field("CompetencyObjectReference", "Competency object references"),
										field("EventReference", "Event references"),
										field("SequenceBlockReference", "Sequence block references")))));
	}

	private static FieldSpec[] eventFields(boolean v1) {
		List<FieldSpec> fields = new ArrayList<>(List.of(field("@id", "Id"), field("Title"), field("EventDuration"),
				text("Description", "Description"), field("Keyword", "Keywords"), field("Interprofessional"),
				field("CompetencyObjectReference", "Competency object references"),
				new PageSpec.Field("InstructionalMethod", "Instructional methods", PageSpec.Widget.AUTO, terms("IM")),
				v1 ? repeat("InstructionalMethod", "Instructional method details", null, field("@primary"),
						field("@source"), field("@sourceID", "Source ID"))
						: repeat("InstructionalMethod", "Instructional method details", null, field("@primary"),
								field("@instructionalMethodDuration", "Duration"), field("@source"),
								field("@sourceID", "Source ID")),
				new PageSpec.Field("AssessmentMethod", "Assessment methods", PageSpec.Widget.AUTO, terms("AM")),
				repeat("AssessmentMethod", "Assessment method details", null, field("@purpose"), field("@source"),
						field("@sourceID", "Source ID")),
				new PageSpec.Field("ResourceType", "Resource types", PageSpec.Widget.AUTO, terms("RE")),
				repeat("ResourceType", "Resource type details", null, field("@source"),
						field("@sourceID", "Source ID"))));
		return fields.toArray(FieldSpec[]::new);
	}

	private static FieldSpec[] blockFields(boolean v1) {
		return new FieldSpec[] { field("@id", "Id"), field("@required"), field("@order"), field("@minimum"),
				field("@maximum"), field("@track"), field("Title"), text("Description", "Description"),
				group("Timing", "Timing", field("Duration"),
						repeat("Dates", "Dates", "StartDate", field("StartDate"), field("EndDate"))),
				v1 ? field("Level", "Academic level")
						: group("SequenceBlockLevels", "Academic levels", field("StartingAcademicLevel"),
								field("EndingAcademicLevel")),
				field("ClerkshipModel"), field("CompetencyObjectReference", "Competency object references"),
				text("Precondition", "Precondition"), text("Postcondition", "Postcondition"),
				repeat("SequenceBlockEvent", "Events of the block", "EventReference", field("@required"),
						field("EventReference"), field("StartDate"), field("EndDate")),
				field("SequenceBlockReference", "Nested sequence blocks"),
				repeat("SequenceBlockReference", "Order of nested blocks", null, field("@order")) };
	}

	// ----------------------------------------------------------------------------------------------------
	// Search fields
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		return List.of(FieldGroup.of("ci-titles", "Titles", "Title", "ProgramName", "Label"),
				FieldGroup.of("ci-descriptions", "Descriptions", "Description", "Precondition", "Postcondition"),
				FieldGroup.of("ci-institution", "Institution and program", "Institution", "Program"),
				FieldGroup.of("ci-events", "Events", "Event"),
				FieldGroup.of("ci-methods", "Instructional and assessment methods", "InstructionalMethod",
						"AssessmentMethod"),
				FieldGroup.of("ci-resources", "Resource types", "ResourceType"),
				FieldGroup.of("ci-keywords", "Keywords", "Keyword"),
				FieldGroup.of("ci-expectations", "Expectations (competencies)", "Expectations"),
				FieldGroup.of("ci-levels", "Academic levels", "AcademicLevels"),
				FieldGroup.of("ci-sequence", "Sequence blocks", "Sequence"),
				FieldGroup.of("ci-integration", "Integration blocks", "Integration"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Help
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<DocumentLinks.LinkPath> linkPaths(MedbiqDocumentType type) {
		// The competency objects of the expectations, which may also be documents of their own.
		return List.of(new DocumentLinks.LinkPath("Expectations/CompetencyObject/lom/general/identifier",
				"Competency object"));
	}

	@Override
	public List<References.Kind> references(MedbiqDocumentType type) {
		String level = "/CurriculumInventory/AcademicLevels/Level[@number='%s']";
		List<References.Kind> kinds = new ArrayList<>(List.of(
				new References.Kind("Event", "EventReference", "Events/Event", "@id", "Title",
						"/CurriculumInventory/Events/Event[@id='%s']"),
				new References.Kind("Sequence block", "SequenceBlockReference", "Sequence/SequenceBlock", "@id", "Title",
						"/CurriculumInventory/Sequence/SequenceBlock[@id='%s']"),
				new References.Kind("Competency object", "CompetencyObjectReference", "Expectations/CompetencyObject",
						"lom/general/identifier/entry", "lom/general/title/string",
						"/CurriculumInventory/Expectations/CompetencyObject[lom:lom/lom:general/lom:identifier/lom:entry='%s']")));
		if (isV1(type)) {
			kinds.add(new References.Kind("Academic level", "Level", "AcademicLevels/Level", "@number", "Label", level,
					"SequenceBlock"));
		} else {
			kinds.add(new References.Kind("Academic level", "StartingAcademicLevel", "AcademicLevels/Level", "@number",
					"Label", level));
			kinds.add(new References.Kind("Academic level", "EndingAcademicLevel", "AcademicLevels/Level", "@number",
					"Label", level));
		}
		return kinds;
	}

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		boolean v1 = isV1(type);
		Map<String, String> help = new LinkedHashMap<>();
		help.put("CurriculumInventory", "The root element. It contains sub elements that provide data on a health "
				+ "professions curriculum for a specific period of time.");
		help.put("ReportID", "A unique identifier for this Curriculum Inventory, with a domain attribute naming the "
				+ "organization that is the source of the identifier.");
		help.put("@domain", "The organization that is the source of the unique identifier, in the form "
				+ "idd:domainname:localidentifier (for example idd:aamc.org:program); the domain name must be owned "
				+ "by the issuing organization.");
		help.put("Institution", "The name, identifier, and address of the institution whose curriculum is described "
				+ "(InstitutionInfoType of the Healthcare Professional Profile).");
		help.put("InstitutionName", "The name of the institution whose curriculum is described.");
		help.put("InstitutionID", "An identifier of the institution. For medical schools MedBiquitous recommends the "
				+ "FAIMER IMED identifier with the domain idd:faimer.edu:imed (Implementation Guidelines 3.3).");
		help.put("Address", "The postal address of the institution.");
		help.put("Program", "The name and identifier of the educational program that the Curriculum Inventory "
				+ "describes.");
		help.put("ProgramName", "The name of the educational program. In many cases, this is the degree offered, "
				+ "such as M.D.");
		help.put("ProgramID", "A unique identifier for this educational program, with a domain attribute (for "
				+ "example idd:aamc.org:program).");
		help.put("EducationalContext", "The educational environment for which the curriculum is intended, from the "
				+ "Healthcare LOM vocabulary (for example undergraduate professional education). Values must be in "
				+ "English.");
		help.put("Profession", "The health profession for which the curriculum is intended or which it is intended "
				+ "to produce, for example physician; the Healthcare LOM list of professions is recommended "
				+ "(source HEALTHCARE_LOMv1).");
		help.put("Specialty", "The healthcare specialty within a profession for which the curriculum is intended, "
				+ "for example cardiology (Healthcare LOM list of specialties recommended).");
		help.put("@source", "The taxonomy or vocabulary serving as the source of the term (for the standardized "
				+ "methods and resource types, the AAMC/MedBiquitous Curriculum Inventory vocabulary).");
		help.put("@sourceID", "The unique identifier of the term within the vocabulary, such as IM013 (Lecture) "
				+ "or AM004.");
		help.put("Title", "The title of the curriculum inventory, event, sequence block or integration block.");
		help.put("CurriculumInventory/Title", "The title of the Curriculum Inventory.");
		help.put("Event/Title", "A title for this education or assessment event.");
		help.put("SequenceBlock/Title", "The title of the sequence block.");
		help.put("IntegrationBlock/Title", "The title of the integration block. For example, Rural Health.");
		help.put("ReportDate", "The date the report was created.");
		help.put("ReportingStartDate", "The first day of the reporting period that this Curriculum Inventory "
				+ "covers.");
		help.put("ReportingEndDate", "The last day of the reporting period that this Curriculum Inventory covers.");
		help.put("Language", "The default language of the report contents, for example en-US. It does not apply to "
				+ "enumerated values (EducationalContext, ClerkshipModel) or to vocabulary terms.");
		help.put("Description", "A free text description.");
		help.put("CurriculumInventory/Description", "A free text description of the curriculum"
				+ (v1 ? "." : " (optional in v10)."));
		help.put("Event/Description", "A free text description of the education or assessment event.");
		help.put("Level/Description", "A description of the academic level.");
		help.put("Sequence/Description", "A description of the overall sequence of the curriculum.");
		help.put("SequenceBlock/Description", "A description of the sequence block.");
		help.put("Integration/Description", "A description of the approach to integrating the curriculum.");
		help.put("IntegrationBlock/Description", "A description of the integrating concept.");
		help.put("SupportingLink", "A link (URL or URN) to supporting information, such as a pictorial "
				+ "representation of the curriculum or a document explaining the rationale behind its structure.");
		help.put("Events", "The education and assessment events that comprise the curriculum.");
		help.put("Event", "A single education or assessment event, such as a lecture, problem-based learning "
				+ "activity, virtual patient activity, simulation, seminar, lab or bedside learning activity. A single "
				+ "event may occur in multiple sequence blocks.");
		help.put("@id", "A unique identifier; it must be unique within the scope of this report. References use it "
				+ "in XPath form, such as /CurriculumInventory/Events/Event[@id='E1'].");
		help.put("EventDuration", "The duration of the event in hours and/or minutes, for example PT20H (20 hours) "
				+ "or PT1H30M.");
		help.put("Keyword", "An important concept or topic related to the event (the Healthcare LOM keyword type; "
				+ "MeSH terms with source and id attributes are recommended).");
		help.put("Interprofessional", "Whether this event is designed to engage students of different health "
				+ "professions as teams. The default value is false.");
		help.put("CompetencyObjectReference", "A reference to a competency object (learning objective, competency, "
				+ "learning outcome) of this report: /CurriculumInventory/Expectations/CompetencyObject[lom:lom/"
				+ "lom:general/lom:identifier/lom:entry='X'], all in one line, where X is its identifier.");
		help.put("ResourceType", "A type of resource used in this event. A recommended list is the Curriculum "
				+ "Inventory Standardized Instructional and Assessment Methods and Resource Types (RE codes).");
		help.put("InstructionalMethod", "An instructional method used in this event, preferably from the standardized "
				+ "vocabulary (IM codes). One instructional method must be marked as primary.");
		help.put("@primary", "Whether this instructional method is the primary instructional method of the event; "
				+ "one instructional method must be marked as primary.");
		if (!v1) {
			help.put("@instructionalMethodDuration", "The time spent on this instructional method (added in v10), as "
					+ "hours and/or minutes: PT2H, PT45M or PT1H30M.");
		}
		help.put("AssessmentMethod", "An assessment method used in this event, preferably from the standardized "
				+ "vocabulary (AM codes).");
		help.put("@purpose", "Whether the assessment is used for Formative or Summative assessment (required).");
		help.put("Expectations", "The competencies, learning objectives, and learning outcomes implemented in the "
				+ "curriculum and how they are organized into competency frameworks.");
		help.put("CompetencyObject", "Defines and uniquely identifies a competency, learning objective, learning "
				+ "outcome, etc. (MedBiquitous Competency Object); its identifier is lom/general/identifier/entry.");
		help.put("CompetencyFramework", "A set of related competency definitions and their relationships "
				+ "(MedBiquitous Competency Framework).");
		help.put("lom", "Healthcare LOM metadata: the identifier (catalog and entry), title and description of the "
				+ "competency object or framework.");
		help.put("Status", "Whether the competency object is Active (default) or Retired.");
		help.put("Category", "A category of the competency object, such as a competency domain (term, scheme, "
				+ "label).");
		help.put("@term", "The category term.");
		help.put("@scheme", "The URI of the scheme the category term comes from.");
		help.put("@label", "A human-readable label of the category.");
		help.put("References", "Bibliographic references supporting the competency object.");
		help.put("Reference", "A bibliographic reference.");
		help.put("SupportingInformation", "Supporting information: a link or formatted text.");
		help.put("Link", "A link to supporting information.");
		help.put("EffectiveDate", "The date from which the competency framework is in effect.");
		help.put("RetiredDate", "The date the competency framework was retired.");
		help.put("Replaces", "The identifier (URI) of the object this one replaces.");
		help.put("IsReplacedBy", "The identifier (URI) of the object that replaces this one.");
		help.put("Includes", "A competency object included in the framework, by catalog and entry of its "
				+ "identifier.");
		help.put("Catalog", "The catalog (identification scheme) of the identifier, for example URI.");
		help.put("Entry", "The identifier within the catalog; for competency objects of this report, their "
				+ "lom/general/identifier/entry.");
		help.put("Relation", "A relationship between two competency objects of the framework (SKOS broader, narrower "
				+ "or related).");
		help.put("Relationship", "The kind of relationship: skos:broader, skos:narrower or skos:related.");
		help.put("Reference1", "The first competency object of the relation.");
		help.put("Reference2", "The second competency object of the relation.");
		help.put("AcademicLevels", "The levels within the academic program. Many programs use academic years as "
				+ "levels; others use phases or stages of varying lengths.");
		help.put("LevelsInProgram", "The number of levels in the academic program, from 1 to 10. There must be as "
				+ "many Level elements.");
		help.put("Level", v1 ? "A level of the academic program (in AcademicLevels), or the academic level at which "
				+ "a sequence block occurs (in SequenceBlock): /CurriculumInventory/AcademicLevels/Level[@number='X']."
				: "Descriptive information about a single level of the academic program, such as a year or phase.");
		help.put("SequenceBlock/Level", "The educational level at which this sequence block occurs, as "
				+ "/CurriculumInventory/AcademicLevels/Level[@number='X'] where X is a level of this report.");
		help.put("@number", "The number of the level (1 to 10), indicating its order; level 1 is the first. Each "
				+ "number should be unique and the numbers should be sequential.");
		help.put("Label", "A short descriptive title for this academic level, for example First Year.");
		help.put("Sequence", "How the curriculum is organized over a period of time: a series of sequence blocks, "
				+ "which correspond to courses or other blocks of curricular content.");
		help.put("SequenceBlock", "An organizational component of the curriculum, such as a year, phase, course or "
				+ "module. Blocks can be nested to show ordered courses within a phase, electives, selectives, "
				+ "parallel blocks and tracks.");
		help.put("@required", "SequenceBlock: Required, Optional or Required In Track. SequenceBlockEvent: whether "
				+ "the event is a required part of the sequence block (true or false).");
		help.put("@order", "SequenceBlock: how nested blocks are delivered (Ordered, Unordered, Parallel). "
				+ "SequenceBlockReference: the position of the nested block; required if the parent's order is "
				+ "Ordered.");
		help.put("@minimum", "With nested blocks: the minimum number of nested blocks a learner must take (0 for "
				+ "electives, the number of selectives to take for selectives).");
		help.put("@maximum", "With nested blocks: the maximum number of nested blocks a learner can take.");
		help.put("@track", "Whether this sequence block is a track within the curriculum (default false).");
		help.put("Timing", "The start and end dates of the sequence block, its duration, or both. For a course "
				+ "that occurs several times (such as a clerkship), give the duration and omit the dates.");
		help.put("Duration", "The duration of the sequence block as an XML Schema duration: P12M (12 months), P28D "
				+ "(28 days), PT20H (20 hours).");
		help.put("Dates", "The start and end dates of the sequence block.");
		help.put("StartDate", "The date the sequence block or event began.");
		help.put("EndDate", "The date the sequence block or event ended.");
		if (!v1) {
			help.put("SequenceBlockLevels", "The starting and ending academic levels of the sequence block (v10).");
			help.put("StartingAcademicLevel", "The academic level in which the sequence block begins or the first in "
					+ "which it may occur: /CurriculumInventory/AcademicLevels/Level[@number='X'].");
			help.put("EndingAcademicLevel", "The academic level in which the sequence block ends or the last in "
					+ "which it may occur: /CurriculumInventory/AcademicLevels/Level[@number='X'].");
		}
		help.put("ClerkshipModel", "If the sequence block represents a clerkship, the type of clerkship: integrated "
				+ "or rotation.");
		help.put("Precondition", "Preconditions that must be met for a learner to take this course.");
		help.put("Postcondition", "How the curriculum changes depending on the learner's performance in the "
				+ "sequence block.");
		help.put("SequenceBlockEvent", "An education or assessment event included in the sequence block, by "
				+ "reference, with optional dates.");
		help.put("EventReference", "A reference to an event of this report: /CurriculumInventory/Events/"
				+ "Event[@id='X'], all in one line.");
		help.put("SequenceBlockReference", "A reference to a nested (or, in an integration block, associated) "
				+ "sequence block: /CurriculumInventory/Sequence/SequenceBlock[@id='X'], all in one line.");
		help.put("Integration", "How the curriculum is integrated: integrating concepts, or themes, that apply to "
				+ "many courses, competencies, objectives and events.");
		help.put("IntegrationBlock", "An integrating concept for this curriculum, such as a theme, with the "
				+ "competency objects, events and sequence blocks associated with it.");
		return help;
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<HlomProblem> rules(MedbiqDocumentType type, EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		return root == null ? List.of() : new Checker(isV1(type), root).check();
	}

	/** The value in quotes of a reference such as {@code /CurriculumInventory/Events/Event[@id='E1']}. */
	private static final Pattern REFERENCE = Pattern.compile("\\[[^=\\]]*=\\s*'([^']*)'\\s*]\\s*$");
	private static final Pattern EVENT_DURATION = Pattern.compile("PT(?:(\\d+)H)?(?:(\\d+)M)?");
	private static final Pattern CODE = Pattern.compile("(IM|AM|RE)\\d{3}");

	/** The id, entry or number a reference points to, or {@code null} if it is not a reference. */
	static String target(String reference) {
		if (reference == null) {
			return null;
		}
		Matcher matcher = REFERENCE.matcher(reference.strip());
		return matcher.find() ? matcher.group(1) : null;
	}

	private static String normalized(String term) {
		return term.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]", "");
	}

	/** The checks of one document. */
	private static final class Checker {

		private final boolean v1;
		private final EObject root;
		private final List<HlomProblem> problems = new ArrayList<>();
		private final Map<String, EObject> events = new LinkedHashMap<>();
		private final Map<String, EObject> blocks = new LinkedHashMap<>();
		private final Map<EObject, String> blockIds = new HashMap<>();
		private final Map<String, EObject> competencies = new LinkedHashMap<>();
		private final Map<Integer, EObject> levels = new LinkedHashMap<>();
		private final Set<String> referencedEvents = new HashSet<>();
		private final Set<String> referencedCompetencies = new HashSet<>();
		/** The parents of each sequence block (by id): the blocks that nest it. */
		private final Map<String, List<EObject>> parents = new HashMap<>();
		private LocalDate reportingStart;
		private LocalDate reportingEnd;

		Checker(boolean v1, EObject root) {
			this.v1 = v1;
			this.root = root;
		}

		List<HlomProblem> check() {
			checkReport();
			collect();
			checkLevels();
			checkEvents();
			checkExpectations();
			checkSequence();
			checkIntegration();
			checkUnreferenced();
			return problems;
		}

		private void add(Severity severity, String message, EObject object) {
			problems.add(new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1));
		}

		// Report ------------------------------------------------------------------------------------------

		private void checkReport() {
			reportingStart = Paths.date(Paths.text(root, "ReportingStartDate"));
			reportingEnd = Paths.date(Paths.text(root, "ReportingEndDate"));
			if (reportingStart != null && reportingEnd != null && reportingStart.isAfter(reportingEnd)) {
				add(Severity.ERROR, "The ReportingStartDate (" + reportingStart + ") is after the ReportingEndDate ("
						+ reportingEnd + "); the reporting period runs from its first to its last day.", root);
			}
			LocalDate reportDate = Paths.date(Paths.text(root, "ReportDate"));
			if (reportDate != null && reportingStart != null && reportDate.isBefore(reportingStart)) {
				add(Severity.WARNING, "The ReportDate (" + reportDate + ") is before the reporting period starts ("
						+ reportingStart + "); the ReportDate is the date the report was created.", root);
			}
			for (String path : List.of("ReportID", "Program/ProgramID", "Institution/InstitutionID")) {
				for (EObject id : Paths.objects(root, path)) {
					if (Paths.text(id, "@domain") == null) {
						String name = path.substring(path.lastIndexOf('/') + 1);
						add(Severity.INFO, name + " has no domain attribute; it is recommended to name the "
								+ "organization that issued the identifier (idd:domainname:localidentifier, for "
								+ "example idd:aamc.org:program).", id);
					}
				}
			}
			EObject institution = Paths.objects(root, "Institution").stream().findFirst().orElse(null);
			if (institution != null && Paths.text(institution, "InstitutionName") == null) {
				add(Severity.WARNING, "The Institution has no InstitutionName; Institution contains the name, "
						+ "identifier, and address of the institution whose curriculum is described.", institution);
			}
		}

		// Collecting ids ------------------------------------------------------------------------------------

		private void collect() {
			for (EObject event : Paths.objects(root, "Events/Event")) {
				String id = Paths.text(event, "@id");
				if (id != null && events.putIfAbsent(id, event) != null) {
					add(Severity.ERROR, "The event id '" + id + "' is used by more than one event; the identifier "
							+ "must be unique within the scope of this report.", event);
				}
			}
			for (EObject block : Paths.objects(root, "Sequence/SequenceBlock")) {
				String id = Paths.text(block, "@id");
				if (id != null) {
					blockIds.put(block, id);
					if (blocks.putIfAbsent(id, block) != null) {
						add(Severity.ERROR, "The sequence block id '" + id + "' is used by more than one sequence "
								+ "block; the identifier must be unique within the scope of this report.", block);
					}
				}
			}
			Set<String> integrationIds = new HashSet<>();
			for (EObject block : Paths.objects(root, "Integration/IntegrationBlock")) {
				String id = Paths.text(block, "@id");
				if (id != null && !integrationIds.add(id)) {
					add(Severity.ERROR, "The integration block id '" + id + "' is used by more than one integration "
							+ "block; the identifier must be unique within the scope of this report.", block);
				}
			}
			for (EObject competency : Paths.objects(root, "Expectations/CompetencyObject")) {
				List<String> entries = Paths.texts(competency, "lom/general/identifier/entry");
				if (entries.isEmpty()) {
					add(Severity.WARNING, "The competency object " + label(competency) + " has no identifier "
							+ "(lom/general/identifier/entry), so events, sequence blocks and integration blocks "
							+ "cannot reference it; add an identifier.", competency);
				}
				for (String entry : new LinkedHashSet<>(entries)) {
					EObject other = competencies.putIfAbsent(entry, competency);
					if (other != null && other != competency) {
						add(Severity.ERROR, "The identifier '" + entry + "' is used by more than one competency "
								+ "object; a CompetencyObjectReference must identify a single competency object.",
								competency);
					}
				}
			}
			for (EObject block : Paths.objects(root, "Sequence/SequenceBlock")) {
				for (EObject reference : Paths.objects(block, "SequenceBlockReference")) {
					String target = target(Paths.content(reference));
					if (target != null) {
						parents.computeIfAbsent(target, _ -> new ArrayList<>()).add(block);
					}
				}
			}
		}

		// Academic levels -----------------------------------------------------------------------------------

		private void checkLevels() {
			EObject academicLevels = Paths.objects(root, "AcademicLevels").stream().findFirst().orElse(null);
			if (academicLevels == null) {
				return;
			}
			List<EObject> all = Paths.objects(academicLevels, "Level");
			for (EObject level : all) {
				Integer number = Paths.integer(Paths.text(level, "@number"));
				if (number != null && levels.putIfAbsent(number, level) != null) {
					add(Severity.WARNING, "The academic level number " + number + " is used by more than one level; "
							+ "each level number should be unique.", level);
				}
			}
			Integer count = Paths.integer(Paths.text(academicLevels, "LevelsInProgram"));
			if (count != null && count != all.size()) {
				add(Severity.ERROR, "LevelsInProgram is " + count + " but there " + (all.size() == 1 ? "is " : "are ")
						+ Paths.count(all.size(), "Level") + "; the number of levels specified must correspond to the "
						+ "number provided in LevelsInProgram.", academicLevels);
			}
			for (int n = 1; n <= levels.size(); n++) {
				if (!levels.containsKey(n)) {
					add(Severity.WARNING, "The academic levels are not numbered 1 to " + levels.size() + " (level " + n
							+ " is missing); together the level numbers should create a sequential order starting "
							+ "with 1.", academicLevels);
					break;
				}
			}
		}

		// Events --------------------------------------------------------------------------------------------

		private void checkEvents() {
			for (EObject event : Paths.objects(root, "Events/Event")) {
				String name = "The event " + label(event);
				for (String reference : Paths.texts(event, "CompetencyObjectReference")) {
					checkCompetencyReference(reference, name, event);
				}
				List<EObject> methods = Paths.objects(event, "InstructionalMethod");
				long primary = methods.stream().filter(m -> "true".equals(Paths.text(m, "@primary"))).count();
				if (!methods.isEmpty() && primary == 0) {
					add(Severity.ERROR, name + " has no primary instructional method; one instructional method must "
							+ "be marked as primary (primary=\"true\").", event);
				} else if (primary > 1) {
					add(Severity.WARNING, name + " has " + primary + " instructional methods marked as primary; one "
							+ "instructional method is marked as primary for an event.", event);
				}
				if (!v1) {
					int total = 0;
					for (EObject method : methods) {
						Integer minutes = minutes(Paths.text(method, "@instructionalMethodDuration"));
						total += minutes == null ? 0 : minutes;
					}
					Integer duration = minutes(Paths.text(event, "EventDuration"));
					if (duration != null && total > duration) {
						add(Severity.WARNING, name + " has instructional method durations of " + hours(total)
								+ " in total, more than its EventDuration (" + hours(duration) + ").", event);
					}
				}
				for (EObject method : methods) {
					checkTerm(method, "IM", "instructional method");
				}
				for (EObject method : Paths.objects(event, "AssessmentMethod")) {
					checkTerm(method, "AM", "assessment method");
				}
				for (EObject resource : Paths.objects(event, "ResourceType")) {
					checkTerm(resource, "RE", "resource type");
				}
			}
		}

		/** Checks a term against the standardized vocabulary (recommended, not required). */
		private void checkTerm(EObject element, String prefix, String kind) {
			String text = Paths.content(element);
			if (text == null || text.isBlank()) {
				return;
			}
			text = text.strip();
			String code = Paths.text(element, "@sourceID");
			if (code != null && CODE.matcher(code).matches()) {
				Term term = BY_CODE.get(code);
				if (!code.startsWith(prefix)) {
					add(Severity.WARNING, "The " + kind + " '" + text + "' has the sourceID " + code + ", which is not "
							+ "a" + (prefix.equals("AM") ? "n " : " ") + kind + " code (" + prefix + "…) of the "
							+ "Curriculum Inventory standardized vocabulary.", element);
				} else if (term == null) {
					add(Severity.WARNING, "The sourceID " + code + " of the " + kind + " '" + text + "' is not a code "
							+ "of the Curriculum Inventory standardized vocabulary.", element);
				} else if (!normalized(term.name()).equals(normalized(text))) {
					add(Severity.WARNING, "The " + kind + " '" + text + "' has the sourceID " + code + ", which is '"
							+ term.name() + "' in the Curriculum Inventory standardized vocabulary; correct the term "
							+ "or the sourceID.", element);
				}
				return;
			}
			Term term = BY_NAME.get(normalized(text));
			if (term == null || !term.code().startsWith(prefix)) {
				add(Severity.INFO, "The " + kind + " '" + text + "' is not a term of the Curriculum Inventory "
						+ "Standardized Instructional and Assessment Methods and Resource Types; using an agreed-upon "
						+ "vocabulary is strongly recommended.", element);
			} else if (code == null) {
				add(Severity.INFO, "The " + kind + " '" + text + "' has no sourceID; it is recommended to give the "
						+ "vocabulary code (" + term.code() + ") with the source and sourceID attributes.", element);
			}
		}

		// Expectations --------------------------------------------------------------------------------------

		private void checkExpectations() {
			for (EObject framework : Paths.objects(root, "Expectations/CompetencyFramework")) {
				for (EObject includes : Paths.objects(framework, "Includes")) {
					String entry = Paths.text(includes, "Entry");
					if (entry != null) {
						referencedCompetencies.add(entry);
						if (!competencies.containsKey(entry)) {
							add(Severity.INFO, "The competency framework " + label(framework) + " includes '" + entry
									+ "', which is not the identifier of a competency object of this report; it is "
									+ "recommended to include the competency objects in Expectations.", includes);
						}
					}
				}
			}
			for (EObject competency : Paths.objects(root, "Expectations/CompetencyObject")) {
				if (Paths.text(competency, "lom/general/title/string") == null) {
					add(Severity.INFO, "The competency object " + label(competency) + " has no title "
							+ "(lom/general/title); a title is recommended so that people can recognize it.",
							competency);
				}
			}
		}

		// Sequence ------------------------------------------------------------------------------------------

		private void checkSequence() {
			List<EObject> all = Paths.objects(root, "Sequence/SequenceBlock");
			for (EObject block : all) {
				String name = "The sequence block " + label(block);
				for (String reference : Paths.texts(block, "CompetencyObjectReference")) {
					checkCompetencyReference(reference, name, block);
				}
				checkBlockEvents(block, name);
				checkNested(block, name);
				checkTiming(block, name);
				checkBlockLevels(block, name);
				if ("Required In Track".equals(Paths.literal(block, "@required")) && !inTrack(block, new HashSet<>())) {
					add(Severity.WARNING, name + " is 'Required In Track' but is not nested in a track (a sequence "
							+ "block with track=\"true\"); use Required In Track for blocks within a track.", block);
				}
			}
			checkCycles(all);
		}

		private void checkBlockEvents(EObject block, String name) {
			Set<String> seen = new HashSet<>();
			Dates dates = dates(block);
			for (EObject blockEvent : Paths.objects(block, "SequenceBlockEvent")) {
				String reference = Paths.text(blockEvent, "EventReference");
				String id = target(reference);
				if (id != null) {
					referencedEvents.add(id);
					if (!events.containsKey(id)) {
						add(Severity.ERROR, name + " references the event '" + id + "', which is not an event of this "
								+ "report; EventReference must give the id of an event in Events.", blockEvent);
					} else if (!seen.add(id)) {
						add(Severity.WARNING, name + " lists the event '" + id + "' more than once.", blockEvent);
					}
				}
				LocalDate start = Paths.date(Paths.text(blockEvent, "StartDate"));
				LocalDate end = Paths.date(Paths.text(blockEvent, "EndDate"));
				String event = "The event '" + id + "' of " + name.substring(4);
				if (start != null && end != null && start.isAfter(end)) {
					add(Severity.ERROR, event + " starts (" + start + ") after it ends (" + end + ").", blockEvent);
				} else if (dates != null && (start != null && start.isBefore(dates.start())
						|| end != null && end.isAfter(dates.end()))) {
					add(Severity.WARNING, event + " has dates outside the dates of its sequence block (" + dates.start()
							+ " to " + dates.end() + ").", blockEvent);
				} else if (outsideReportingPeriod(start, end)) {
					add(Severity.WARNING, event + " has dates outside the reporting period (" + reportingStart + " to "
							+ reportingEnd + ").", blockEvent);
				}
			}
		}

		private void checkNested(EObject block, String name) {
			String order = Paths.literal(block, "@order");
			List<EObject> references = Paths.objects(block, "SequenceBlockReference");
			Set<String> orders = new HashSet<>();
			for (EObject reference : references) {
				String id = target(Paths.content(reference));
				if (id != null && !blocks.containsKey(id)) {
					add(Severity.ERROR, name + " references the sequence block '" + id + "', which is not a sequence "
							+ "block of this report; SequenceBlockReference must give the id of a sequence block in "
							+ "Sequence.", reference);
				}
				String position = Paths.text(reference, "@order");
				if ("Ordered".equals(order)) {
					if (position == null) {
						add(Severity.ERROR, "The nested sequence block '" + id + "' of " + name.substring(4)
								+ " has no order attribute; it must be used if the order of the parent sequence block "
								+ "is Ordered.", reference);
					} else if (!orders.add(position)) {
						add(Severity.WARNING, name + " gives the order " + position + " to more than one nested "
								+ "sequence block.", reference);
					}
				}
				EObject child = id == null ? null : blocks.get(id);
				if (child != null) {
					checkChildTiming(block, child, name);
				}
			}
			BigInteger minimum = bigInteger(Paths.text(block, "@minimum"));
			BigInteger maximum = bigInteger(Paths.text(block, "@maximum"));
			if (references.isEmpty()) {
				if (minimum != null || maximum != null || order != null) {
					add(Severity.INFO, name + " has order, minimum or maximum attributes but no nested sequence "
							+ "blocks; these attributes describe nested sequence blocks.", block);
				}
				return;
			}
			if (minimum != null && maximum != null && minimum.compareTo(maximum) > 0) {
				add(Severity.ERROR, name + " has a minimum (" + minimum + ") greater than its maximum (" + maximum
						+ ").", block);
			}
			BigInteger nested = BigInteger.valueOf(references.size());
			if (minimum != null && minimum.compareTo(nested) > 0) {
				add(Severity.ERROR, name + " requires a minimum of " + minimum + " nested sequence blocks but has only "
						+ nested + ".", block);
			}
			if (maximum != null && maximum.compareTo(nested) > 0) {
				add(Severity.WARNING, name + " allows a maximum of " + maximum + " nested sequence blocks but has only "
						+ nested + ".", block);
			}
		}

		private void checkTiming(EObject block, String name) {
			EObject timing = Paths.objects(block, "Timing").stream().findFirst().orElse(null);
			if (timing == null) {
				return;
			}
			List<String> durations = Paths.texts(timing, "Duration");
			List<EObject> dates = Paths.objects(timing, "Dates");
			if (durations.size() > 1 || dates.size() > 1) {
				add(Severity.ERROR, name + " has more than one " + (durations.size() > 1 ? "Duration" : "Dates")
						+ "; Timing gives the dates, the duration, or both, once.", timing);
			}
			for (EObject range : dates) {
				LocalDate start = Paths.date(Paths.text(range, "StartDate"));
				LocalDate end = Paths.date(Paths.text(range, "EndDate"));
				if (start != null && end != null && start.isAfter(end)) {
					add(Severity.ERROR, name + " starts (" + start + ") after it ends (" + end + ").", range);
				} else if (outsideReportingPeriod(start, end)) {
					add(Severity.WARNING, name + " has dates (" + start + " to " + end + ") outside the reporting "
							+ "period (" + reportingStart + " to " + reportingEnd + ").", range);
				}
			}
			if (dates.isEmpty() && parents.containsKey(blockIds.get(block))
					&& parents.get(blockIds.get(block)).stream().allMatch(p -> dates(p) == null)) {
				add(Severity.INFO, name + " gives only a duration and the sequence block that nests it has no dates; "
						+ "the parent sequence block should indicate dates that show the time frame in which the child "
						+ "sequence blocks may occur.", timing);
			}
		}

		private void checkChildTiming(EObject parent, EObject child, String name) {
			Dates outer = dates(parent);
			Dates inner = dates(child);
			if (outer != null && inner != null
					&& (inner.start().isBefore(outer.start()) || inner.end().isAfter(outer.end()))) {
				add(Severity.WARNING, "The nested sequence block " + label(child) + " (" + inner.start() + " to "
						+ inner.end() + ") is not within the dates of " + name.substring(4) + " (" + outer.start()
						+ " to " + outer.end() + ").", child);
			}
			if (!v1) {
				int[] outerLevels = levelRange(parent);
				int[] innerLevels = levelRange(child);
				if (outerLevels != null && innerLevels != null
						&& (innerLevels[0] < outerLevels[0] || innerLevels[1] > outerLevels[1])) {
					add(Severity.WARNING, "The academic levels of the nested sequence block " + label(child) + " ("
							+ innerLevels[0] + " to " + innerLevels[1] + ") are not within those of "
							+ name.substring(4) + " (" + outerLevels[0] + " to " + outerLevels[1] + ").", child);
				}
			}
		}

		private void checkBlockLevels(EObject block, String name) {
			if (v1) {
				for (String reference : Paths.texts(block, "Level")) {
					checkLevelReference(reference, name, block);
				}
				return;
			}
			for (EObject blockLevels : Paths.objects(block, "SequenceBlockLevels")) {
				Integer start = checkLevelReference(Paths.text(blockLevels, "StartingAcademicLevel"), name,
						blockLevels);
				Integer end = checkLevelReference(Paths.text(blockLevels, "EndingAcademicLevel"), name, blockLevels);
				if (start != null && end != null && start > end) {
					add(Severity.ERROR, name + " starts at academic level " + start + " but ends at level " + end
							+ "; the StartingAcademicLevel cannot come after the EndingAcademicLevel.", blockLevels);
				}
			}
		}

		/** Checks a reference to an academic level; returns its number if it resolves. */
		private Integer checkLevelReference(String reference, String name, EObject object) {
			Integer number = Paths.integer(target(reference));
			if (number == null) {
				return null;
			}
			if (!levels.containsKey(number)) {
				add(Severity.ERROR, name + " references academic level " + number + ", which is not a Level of "
						+ "AcademicLevels; the reference must correspond to an academic level defined in this "
						+ "Curriculum Inventory.", object);
				return null;
			}
			return number;
		}

		private int[] levelRange(EObject block) {
			EObject blockLevels = Paths.objects(block, "SequenceBlockLevels").stream().findFirst().orElse(null);
			if (blockLevels == null) {
				return null;
			}
			Integer start = Paths.integer(target(Paths.text(blockLevels, "StartingAcademicLevel")));
			Integer end = Paths.integer(target(Paths.text(blockLevels, "EndingAcademicLevel")));
			return start == null || end == null || start > end ? null : new int[] { start, end };
		}

		private boolean inTrack(EObject block, Set<EObject> visited) {
			if (!visited.add(block)) {
				return false;
			}
			for (EObject parent : parents.getOrDefault(blockIds.get(block), List.of())) {
				if ("true".equals(Paths.text(parent, "@track")) || inTrack(parent, visited)) {
					return true;
				}
			}
			return false;
		}

		private void checkCycles(List<EObject> all) {
			Map<String, Integer> state = new HashMap<>();
			for (EObject block : all) {
				String id = blockIds.get(block);
				if (id != null && !state.containsKey(id)) {
					visit(id, state, new ArrayList<>());
				}
			}
		}

		private void visit(String id, Map<String, Integer> state, List<String> path) {
			state.put(id, 1);
			path.add(id);
			EObject block = blocks.get(id);
			for (EObject reference : Paths.objects(block, "SequenceBlockReference")) {
				String child = target(Paths.content(reference));
				if (child == null || !blocks.containsKey(child)) {
					continue;
				}
				Integer childState = state.get(child);
				if (childState == null) {
					visit(child, state, path);
				} else if (childState == 1) {
					List<String> cycle = new ArrayList<>(path.subList(path.indexOf(child), path.size()));
					cycle.add(child);
					add(Severity.ERROR, "The sequence block '" + child + "' contains itself through nested sequence "
							+ "blocks (" + String.join(" > ", cycle) + "); remove one of the SequenceBlockReferences.",
							reference);
				}
			}
			path.removeLast();
			state.put(id, 2);
		}

		// Integration ---------------------------------------------------------------------------------------

		private void checkIntegration() {
			for (EObject block : Paths.objects(root, "Integration/IntegrationBlock")) {
				String name = "The integration block " + label(block);
				for (String reference : Paths.texts(block, "CompetencyObjectReference")) {
					checkCompetencyReference(reference, name, block);
				}
				for (String reference : Paths.texts(block, "EventReference")) {
					String id = target(reference);
					if (id != null && !events.containsKey(id)) {
						add(Severity.ERROR, name + " references the event '" + id + "', which is not an event of this "
								+ "report.", block);
					}
				}
				for (EObject reference : Paths.objects(block, "SequenceBlockReference")) {
					String id = target(Paths.content(reference));
					if (id != null && !blocks.containsKey(id)) {
						add(Severity.ERROR, name + " references the sequence block '" + id + "', which is not a "
								+ "sequence block of this report.", reference);
					}
				}
			}
		}

		private void checkCompetencyReference(String reference, String name, EObject object) {
			String entry = target(reference);
			if (entry == null) {
				return;
			}
			referencedCompetencies.add(entry);
			if (!competencies.containsKey(entry)) {
				add(Severity.ERROR, name + " references the competency object '" + entry + "', which is not the "
						+ "identifier (lom:entry) of a competency object in Expectations; add the competency object or "
						+ "correct the CompetencyObjectReference.", object);
			}
		}

		// References in general -----------------------------------------------------------------------------

		private void checkUnreferenced() {
			if (Paths.objects(root, "Sequence").isEmpty()) {
				if (!events.isEmpty()) {
					add(Severity.WARNING, "The report has no Sequence, so its events are not organized into sequence "
							+ "blocks (courses, phases, years); add a Sequence with the sequence blocks of the "
							+ "curriculum.", root);
				}
			} else {
				for (Map.Entry<String, EObject> event : events.entrySet()) {
					if (!referencedEvents.contains(event.getKey())) {
						add(Severity.WARNING, "The event " + label(event.getValue()) + " is not part of any sequence "
								+ "block; reference it with a SequenceBlockEvent of the sequence block in which it "
								+ "occurs.", event.getValue());
					}
				}
			}
			for (Map.Entry<String, EObject> competency : competencies.entrySet()) {
				if (!referencedCompetencies.contains(competency.getKey())) {
					add(Severity.INFO, "The competency object " + label(competency.getValue()) + " is not referenced "
							+ "by any event, sequence block, integration block or competency framework.",
							competency.getValue());
				}
			}
		}

		// Helpers -------------------------------------------------------------------------------------------

		private boolean outsideReportingPeriod(LocalDate start, LocalDate end) {
			return reportingStart != null && reportingEnd != null && !reportingStart.isAfter(reportingEnd)
					&& (start != null && (start.isBefore(reportingStart) || start.isAfter(reportingEnd))
							|| end != null && (end.isBefore(reportingStart) || end.isAfter(reportingEnd)));
		}

		/** The dates of a sequence block, if it has a valid range. */
		private static Dates dates(EObject block) {
			for (EObject range : Paths.objects(block, "Timing/Dates")) {
				LocalDate start = Paths.date(Paths.text(range, "StartDate"));
				LocalDate end = Paths.date(Paths.text(range, "EndDate"));
				if (start != null && end != null && !start.isAfter(end)) {
					return new Dates(start, end);
				}
			}
			return null;
		}

		private static String label(EObject object) {
			String id = Paths.text(object, "@id");
			String title = Paths.text(object, "Title");
			if (title == null) {
				title = Paths.text(object, "lom/general/title/string");
			}
			if (title == null) {
				title = Paths.text(object, "lom/general/identifier/entry");
			}
			return id != null && title != null ? "'" + id + "' (" + title + ")"
					: id != null ? "'" + id + "'" : title != null ? "'" + title + "'" : "(untitled)";
		}
	}

	private record Dates(LocalDate start, LocalDate end) {
	}

	private static BigInteger bigInteger(String text) {
		try {
			return text == null ? null : new BigInteger(text.strip());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** The minutes of an event duration such as {@code PT1H30M}, or {@code null}. */
	private static Integer minutes(String duration) {
		if (duration == null) {
			return null;
		}
		Matcher matcher = EVENT_DURATION.matcher(duration.strip());
		if (!matcher.matches() || matcher.group(1) == null && matcher.group(2) == null) {
			return null;
		}
		int hours = matcher.group(1) == null ? 0 : Integer.parseInt(matcher.group(1));
		int minutes = matcher.group(2) == null ? 0 : Integer.parseInt(matcher.group(2));
		return hours * 60 + minutes;
	}

	private static String hours(int minutes) {
		return "PT" + (minutes / 60 > 0 ? minutes / 60 + "H" : "") + (minutes % 60 > 0 || minutes == 0 ? minutes % 60
				+ "M" : "");
	}
}
