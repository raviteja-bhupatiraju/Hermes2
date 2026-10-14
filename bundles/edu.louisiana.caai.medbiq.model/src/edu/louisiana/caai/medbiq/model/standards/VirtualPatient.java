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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqModels;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/**
 * MedBiquitous Virtual Patient 1.0 (ANSI/MEDBIQ VP.10.1-2010): the Virtual Patient Data (VPD), the Activity Model
 * (AM) and the Data Availability Model (DAM) of a virtual patient package.
 * <p>
 * The three documents refer to one another with XPath expressions: activity nodes show DAM nodes
 * ({@code /DataAvailabilityModel/DAMNode[@id='x']}), DAM node items point into the VPD
 * ({@code /VirtualPatientData/InterviewItem[@id='y']}) or to media resources of the package manifest. The rules
 * check the references within a document, the element names of VPD paths against the VPD model and, when the
 * document lies in a package folder next to the other documents (named {@value #VPD_FILE}, {@value #DAM_FILE} as the
 * specification requires), the referenced ids in those documents.
 */
public final class VirtualPatient implements StandardContent {

	/** The type id of Virtual Patient Data documents. */
	public static final String VPD = "virtual-patient-data-v1";
	/** The type id of Activity Models. */
	public static final String AM = "virtual-patient-activity-model-v1";
	/** The type id of Data Availability Models. */
	public static final String DAM = "virtual-patient-data-availability-model-v1";

	/** The file of the Virtual Patient Data in a virtual patient package (specification 2.3). */
	public static final String VPD_FILE = "virtualpatientdata.xml";
	/** The file of the Data Availability Model in a virtual patient package (specification 2.3). */
	public static final String DAM_FILE = "dataavailabilitymodel.xml";

	private static final String VPD_NS = "http://ns.medbiq.org/virtualpatientdata/v1/";
	private static final String XHTML_NS = "http://www.w3.org/1999/xhtml";
	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** A predicate {@code [@name = 'value']} of an XPath step (any of the quotes the specification's examples use). */
	private static final Pattern PREDICATE = Pattern
			.compile("\\[\\s*@([\\w:.-]+)\\s*=\\s*['\"`\u2018\u2019]([^'\"`\u2018\u2019]*)['\"`\u2018\u2019]\\s*]");

	/** Documents of the package read for the cross-document checks, by URI. */
	private static final Map<URI, Sibling> SIBLINGS = new ConcurrentHashMap<>();

	private record Sibling(long timeStamp, EObject root) {
	}

	// ----------------------------------------------------------------------------------------------------
	// Titles and descriptions
	// ----------------------------------------------------------------------------------------------------

	@Override
	public String title(MedbiqDocumentType type, EObject root) {
		return switch (type.id()) {
			case VPD -> firstNonNull(Paths.text(root, "PatientDemographics/CoreDemographics/Name"),
					Paths.text(root, "PatientDemographics/CoreDemographics/PatientID"),
					"Virtual patient data (" + Paths.count(dataElements(root), "data element") + ")");
			case AM -> firstNonNull(Paths.text(root, "ActivityNodes/NodeSection/@label"),
					"Activity model (" + Paths.count(activityNodes(root).size(), "activity node") + ")");
			case DAM -> "Data availability model (" + Paths.count(Paths.objects(root, "DAMNode").size(), "DAM node") + ")";
			default -> type.standard();
		};
	}

	@Override
	public String description(MedbiqDocumentType type, EObject root) {
		return switch (type.id()) {
			case VPD -> vpdDescription(root);
			case AM -> Paths.count(activityNodes(root).size(), "activity node") + " in "
					+ Paths.count(sections(root).size(), "node section") + ", "
					+ Paths.count(Paths.objects(root, "Links/Link").size(), "link") + ", "
					+ Paths.count(Paths.objects(root, "Properties/Counters/Counter").size(), "counter");
			case DAM -> Paths.count(Paths.objects(root, "DAMNode").size(), "DAM node") + " with "
					+ Paths.count(Paths.objects(root, "DAMNode/DAMNodeItem").size(), "item");
			default -> null;
		};
	}

	private static String vpdDescription(EObject root) {
		List<String> patient = new ArrayList<>();
		String sex = Paths.literal(root, "PatientDemographics/CoreDemographics/Sex");
		if (sex != null) {
			patient.add(sex);
		}
		String age = Paths.text(root, "PatientDemographics/CoreDemographics/Age");
		if (age != null) {
			Matcher years = Pattern.compile("P(\\d+)Y.*").matcher(age);
			patient.add(years.matches() ? years.group(1) + " years" : age);
		}
		String species = Paths.text(root, "PatientDemographics/CoreDemographics/Species");
		if (species != null) {
			patient.add(species);
		}
		List<String> counts = new ArrayList<>();
		for (var entry : Map.of("InterviewItem", "interview item", "PhysicalExam", "physical exam", "DiagnosticTest",
				"diagnostic test", "Diagnosis", "diagnosis", "Intervention", "intervention", "Medication", "medication",
				"VPDText", "text").entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
			int n = Paths.objects(root, entry.getKey()).size();
			if (n > 0) {
				counts.add(Paths.count(n, entry.getValue()));
			}
		}
		String data = counts.isEmpty() ? "no clinical data yet" : String.join(", ", counts);
		return patient.isEmpty() ? capitalize(data) : String.join(", ", patient) + "; " + data;
	}

	private static int dataElements(EObject root) {
		int n = Paths.objects(root, "PatientDemographics").size();
		for (String name : List.of("VPDText", "Medication", "InterviewItem", "PhysicalExam", "DiagnosticTest",
				"Diagnosis", "Intervention")) {
			n += Paths.objects(root, name).size();
		}
		return n;
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		return switch (type.id()) {
			case VPD -> vpdPages();
			case AM -> amPages();
			case DAM -> damPages();
			default -> List.of();
		};
	}

	private static final String[] CODE_SOURCES = { "SNOMED CT", "ICD-10", "ICD-10-CM", "MeSH", "UMLS", "LOINC",
			"RxNorm" };

	private static List<PageSpec> vpdPages() {
		return List.of(
				page("patient", "Patient", "Patient", "Name, age, sex and other demographics of the virtual patient",
						"general",
						section("Core demographics", "Key characteristics of the human or animal patient.",
								"PatientDemographics/CoreDemographics", field("Name"), field("PatientID", "Patient ID"),
								field("Age", "Age (duration, e.g. P58Y or P15Y6M)"), field("Sex"), field("Race"),
								field("Species"), field("Breed"), field("@id", "Id")),
						section("Demographic characteristics", "Further characteristics such as occupation or marital "
								+ "status.", "PatientDemographics", field("@id", "Id"),
								repeat("DemographicCharacteristic", "Characteristics", "Title", field("Title"),
										text("Description", "Description"), field("@id", "Id")))),
				page("texts", "Narrative", "Patient", "Chief complaint, history, problems, allergies and narrative "
						+ "texts (VPDText)", "annotation",
						section("Texts", "The formatted text of a VPDText (an XHTML div) is edited on the XML page.",
								"",
								repeat("VPDText", "Texts", "@id", field("@id", "Id"), field("@textType", "Text type"),
										field("div", "Text")))),
				page("history", "History", "Clinical data", "Interview questions and responses, current medications",
						"healthcare-education",
						section("Interview", "Questions the learner may ask and the patient's responses.", "",
								repeat("InterviewItem", "Interview items", "Question", field("@id", "Id"),
										text("Question", "Question"), text("Response", "Response"))),
						section("Medications", "Medications the patient takes.", "",
								repeat("Medication", "Medications", "MedicationName", field("@id", "Id"),
										field("MedicationName", "Medication"),
										suggest("MedicationName/@source", "Vocabulary", CODE_SOURCES),
										field("MedicationName/@sourceID", "Code"), field("Dose"), field("Route"),
										field("Frequency")))),
				page("exam", "Physical Exam", "Clinical data", "Physical examinations and their findings",
						"healthcare-asset",
						section("Physical examinations", null, "",
								repeat("PhysicalExam", "Examinations", "ExamName", field("@id", "Id"),
										field("ExamName", "Exam"), field("Action"), field("Finding"),
										text("Description", "Description"),
										group("LocationOnBody", "Location on body", field("BodyPart", "Body part"),
												suggest("BodyPart/@source", "Vocabulary", CODE_SOURCES),
												field("BodyPart/@sourceID", "Code"),
												field("ProximalOrDistal", "Proximal or distal"),
												field("RightOrLeft", "Right or left"),
												field("FrontOrBack", "Front or back"),
												field("InferiorOrSuperior", "Inferior or superior"))))),
				page("tests", "Diagnostic Tests", "Clinical data", "Laboratory, imaging and other test results",
						"technical",
						section("Diagnostic tests", null, "",
								repeat("DiagnosticTest", "Tests", "TestName", field("@id", "Id"),
										field("TestName", "Test"), field("Result"), field("Unit"),
										field("Normal", "Normal range")))),
				page("diagnoses", "Diagnoses", "Clinical data", "Differential diagnoses and the author's final "
						+ "diagnosis", "classification",
						section("Diagnoses", "Mark the final diagnosis intended by the author with Author diagnosis.",
								"",
								repeat("Diagnosis", "Diagnoses", "DiagnosisName", field("@id", "Id"),
										field("DiagnosisName", "Diagnosis"),
										suggest("DiagnosisName/@source", "Vocabulary", CODE_SOURCES),
										field("DiagnosisName/@sourceID", "Code"), field("Likelihood"),
										field("@authorDiagnosis", "Author diagnosis")))),
				page("interventions", "Interventions", "Clinical data", "Interventions, their appropriateness and "
						+ "results", "educational",
						section("Interventions", null, "",
								repeat("Intervention", "Interventions", "InterventionName", field("@id", "Id"),
										field("InterventionName", "Intervention"), field("Appropriateness"),
										text("Results", "Results"),
										group("Medication", "Medication given", field("MedicationName", "Medication"),
												field("Dose"), field("Route"), field("Frequency"),
												field("@id", "Id"))))),
				page("organization", "Organization", "Structure", "A hierarchy of the data for authoring systems",
						"relation",
						section("Organization", "Sections group items; an item points to a data element by its id.",
								"Organization",
								repeat("Item", "Items", "@label", field("@idRef", "Id of the data element"),
										field("@label", "Label"), suggest("@source", "Vocabulary", CODE_SOURCES),
										field("@sourceID", "Code")),
								repeat("Section", "Sections", null,
										repeat("Item", "Items", "@label", field("@idRef", "Id of the data element"),
												field("@label", "Label"), field("@source", "Vocabulary"),
												field("@sourceID", "Code"))))));
	}

	private static final String NODE_PATH = "/ActivityModel/ActivityNodes/NodeSection/ActivityNode[@id='']";
	private static final String DAM_NODE_PATH = "/DataAvailabilityModel/DAMNode[@id='']";
	private static final String COUNTER_PATH = "/ActivityModel/Properties/Counters/Counter[@id='']";

	private static List<PageSpec> amPages() {
		var counterAction = repeat("CounterActionRule", "Counter actions", "CounterPath",
				field("CounterOperator", "Operator"), field("CounterRuleValue", "Value"),
				suggest("CounterPath", "Counter", COUNTER_PATH), field("CounterRuleEnabled", "Enabled"));
		var rules = repeat("Rule", "Rules", "Relation", field("Relation"), field("Value"),
				suggest("RuleRedirect", "Redirect to", NODE_PATH), text("RuleMessage", "Message"));
		return List.of(
				page("nodes", "Activity Nodes", "Activity", "Node sections and the activity nodes of the paths; the "
						+ "first node starts the activity", "general",
						section("Node sections", "Each activity node shows a DAMNode of the Data Availability Model.",
								"ActivityNodes",
								repeat("NodeSection", "Node sections", "@label", field("@label", "Label"),
										field("@id", "Id"),
										repeat("ActivityNode", "Activity nodes", "@label", field("@label", "Label"),
												field("@id", "Id"), suggest("Content", "Content (DAM node)",
														DAM_NODE_PATH),
												group("Rules", "Rules", field("NavigateGlobal", "Navigate global"),
														field("Probability"), counterAction,
														group("ConditionalRule", "Conditional rule",
																suggest("RuleRedirect", "Redirect to", NODE_PATH),
																text("RuleMessage", "Message"))),
												group("Services/Timer", "Node timer", field("TimerDeltaSeconds",
														"Seconds"), field("TimerDirection", "Direction"),
														field("@isVisible", "Visible"))),
										repeat("NodeSection", "Nested sections", "@label", field("@label", "Label"),
												field("@id", "Id"))))),
				page("links", "Links", "Activity", "The links between activity nodes that form the paths", "relation",
						section("Links", "A link leads from ActivityNodeA to ActivityNodeB.", "Links",
								repeat("Link", "Links", "@label", field("@label", "Label"),
										field("@display", "Display label"),
										suggest("ActivityNodeA", "From node", NODE_PATH),
										suggest("ActivityNodeB", "To node", NODE_PATH),
										field("Weighting", "Weighting (%)"), counterAction))),
				page("properties", "Counters and Timer", "Activity", "Counters and the activity timer", "technical",
						section("Counters", "Scores such as patient health or costs, changed by counter actions.",
								"Properties/Counters",
								repeat("Counter", "Counters", "CounterLabel", field("CounterLabel", "Label"),
										field("@id", "Id"), field("CounterInitValue", "Initial value"),
										field("CounterUnitsPrefix", "Units prefix"),
										field("CounterUnitsSuffix", "Units suffix"), field("@isVisible", "Visible"),
										group("CounterRules", "Counter rules", rules))),
						section("Timer", "A real-time timer that runs through the whole activity.", "Properties/Timer",
								field("TimerDeltaSeconds", "Seconds"), field("TimerDirection", "Direction"),
								field("@isVisible", "Visible"), group("TimerRules", "Timer rules", rules))));
	}

	private static List<PageSpec> damPages() {
		return List.of(page("nodes", "DAM Nodes", "Data", "Groups of patient data and media shown by activity nodes",
				"relation",
				section("DAM nodes", "ItemPath points to /VirtualPatientData/... or to "
						+ "/manifest/resources/resource[@identifier='...'].", "",
						repeat("DAMNode", "DAM nodes", "DAMNodeLabel", field("DAMNodeLabel", "Label"),
								field("@id", "Id"),
								repeat("DAMNodeItem", "Items", "ItemPath", field("@display", "Display"),
										suggest("ItemPath", "Item path", "/VirtualPatientData/VPDText[@id='']",
												"/VirtualPatientData/InterviewItem[@id='']",
												"/VirtualPatientData/PhysicalExam[@id='']",
												"/VirtualPatientData/DiagnosticTest[@id='']",
												"/VirtualPatientData/Diagnosis[@id='']",
												"/VirtualPatientData/Intervention[@id='']",
												"/VirtualPatientData/Medication[@id='']",
												"/VirtualPatientData/PatientDemographics",
												"/VirtualPatientData/PatientDemographics/CoreDemographics/Name/text()",
												"/manifest/resources/resource[@identifier='']"),
										field("ItemOrder", "Order"),
										suggest("ItemComment", "Comment (DAM node)", DAM_NODE_PATH),
										suggest("DAMNodePath", "Included DAM nodes", DAM_NODE_PATH),
										field("AlternativePath", "Alternative path"))))));
	}

	// ----------------------------------------------------------------------------------------------------
	// Search fields
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		return switch (type.id()) {
			case VPD -> List.of(FieldGroup.of("vp-patient", "Patient", "CoreDemographics", "DemographicCharacteristic"),
					FieldGroup.of("vp-texts", "Narrative texts", "VPDText"),
					FieldGroup.of("vp-interview", "Interview items", "InterviewItem"),
					FieldGroup.of("vp-exams", "Physical exams", "PhysicalExam"),
					FieldGroup.of("vp-tests", "Diagnostic tests", "DiagnosticTest"),
					FieldGroup.of("vp-diagnoses", "Diagnoses", "Diagnosis"),
					FieldGroup.of("vp-interventions", "Interventions", "Intervention"),
					FieldGroup.of("vp-medications", "Medications", "Medication"));
			case AM -> List.of(FieldGroup.of("am-nodes", "Activity nodes", "ActivityNode"),
					FieldGroup.of("am-links", "Links", "Link"),
					FieldGroup.of("am-messages", "Rule messages", "RuleMessage"),
					FieldGroup.of("am-counters", "Counters", "Counter"));
			case DAM -> List.of(FieldGroup.of("dam-labels", "DAM node labels", "DAMNodeLabel"),
					FieldGroup.of("dam-paths", "Item paths", "ItemPath", "AlternativePath"),
					FieldGroup.of("dam-references", "DAM node references", "ItemComment", "DAMNodePath"));
			default -> List.of();
		};
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
		List<HlomProblem> problems = new ArrayList<>();
		switch (type.id()) {
			case VPD -> vpdRules(root, problems);
			case AM -> amRules(documentRoot, root, problems);
			case DAM -> damRules(documentRoot, root, problems);
			default -> {
				// No rules for other types.
			}
		}
		return problems;
	}

	// ----- Virtual Patient Data

	private static void vpdRules(EObject root, List<HlomProblem> problems) {
		// Ids: "A unique identifier for this set of data that can be used by other virtual patient components".
		Map<String, List<EObject>> byId = new LinkedHashMap<>();
		for (EObject object : contents(root)) {
			if (object.eClass().getEPackage() == root.eClass().getEPackage()) {
				String id = Paths.text(object, "@id");
				if (id != null && Paths.feature(object, "@id") != null) {
					byId.computeIfAbsent(id, _ -> new ArrayList<>()).add(object);
				}
			}
		}
		for (var entry : byId.entrySet()) {
			List<EObject> objects = entry.getValue();
			for (int i = 1; i < objects.size(); i++) {
				EObject object = objects.get(i);
				String name = elementName(object);
				boolean sameElement = objects.subList(0, i).stream().anyMatch(o -> name.equals(elementName(o)));
				if (sameElement) {
					problems.add(problem(Severity.ERROR, "Two " + name + " elements have the id '" + entry.getKey()
							+ "'. Ids identify the data for the Data Availability Model (/VirtualPatientData/" + name
							+ "[@id='" + entry.getKey() + "']), so give each element a unique id.", object));
				} else {
					problems.add(problem(Severity.WARNING, "The id '" + entry.getKey() + "' of this " + name
							+ " is also used by a " + elementName(objects.getFirst()) + ". An Organization Item "
							+ "refers to \"the unique id of a single virtual patient data element within this "
							+ "dataset\"; give each element its own id.", object));
				}
			}
		}
		// Organization items refer to data elements by id.
		for (EObject organization : Paths.objects(root, "Organization")) {
			for (EObject item : organizationItems(organization)) {
				String idRef = Paths.text(item, "@idRef");
				if (idRef != null && !byId.containsKey(idRef)) {
					problems.add(problem(Severity.ERROR, "The Organization Item " + quoteLabel(item)
							+ "refers to the id '" + idRef + "', which no element of this Virtual Patient Data has. "
							+ "Correct the idRef or remove the item.", item));
				}
			}
		}
		// Breed is "the species subgroup".
		for (EObject core : Paths.objects(root, "PatientDemographics/CoreDemographics")) {
			if (Paths.text(core, "Breed") != null && Paths.text(core, "Species") == null) {
				problems.add(problem(Severity.WARNING, "CoreDemographics gives a Breed but no Species. Breed is the "
						+ "species subgroup of an animal patient; add the Species (for example Felis catus).", core));
			}
		}
		// The author's final diagnosis.
		List<EObject> diagnoses = Paths.objects(root, "Diagnosis");
		if (!diagnoses.isEmpty()
				&& diagnoses.stream().noneMatch(d -> "true".equals(Paths.text(d, "@authorDiagnosis")))) {
			problems.add(problem(Severity.INFO, (diagnoses.size() == 1 ? "The diagnosis is not"
					: "None of the " + diagnoses.size() + " diagnoses is") + " marked as the author's final diagnosis "
					+ "(authorDiagnosis=\"true\"); mark the diagnosis the case is meant to lead to.",
					diagnoses.getFirst()));
		}
		xhtmlRules(root, problems);
	}

	/** VPDText markup (specification 5.2.3): local images only, no JavaScript. */
	private static void xhtmlRules(EObject root, List<HlomProblem> problems) {
		for (EObject object : contents(root)) {
			EPackage ePackage = object.eClass().getEPackage();
			if (ePackage == null || !XHTML_NS.equals(ePackage.getNsURI())) {
				continue;
			}
			String name = elementName(object);
			if ("script".equals(name)) {
				problems.add(problem(Severity.ERROR, "A VPDText contains a script element. \"The use of Javascript is "
						+ "not allowed in the XHTML comprising VPDText\"; remove it.", object));
			} else if ("img".equals(name)) {
				String src = Paths.text(object, "@src");
				if (src != null && isExternal(src)) {
					problems.add(problem(Severity.ERROR, "The image " + src + " in a VPDText is not in the package. "
							+ "\"All media references from img tags must be local and point to resources within the "
							+ "package\"; add the image to the package and use a relative path.", object));
				}
			} else if ("a".equals(name)) {
				String href = Paths.text(object, "@href");
				if (href != null && href.strip().toLowerCase(java.util.Locale.ROOT).startsWith("javascript:")) {
					problems.add(problem(Severity.ERROR, "A link in a VPDText runs JavaScript (" + href + "). The "
							+ "use of Javascript is not allowed in VPDText; link to a page or remove the link.",
							object));
				}
			}
			for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
				String attribute = META.getName(feature);
				if (META.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE && attribute != null
						&& attribute.startsWith("on") && object.eIsSet(feature)) {
					problems.add(problem(Severity.ERROR, "The " + name + " element of a VPDText has the event "
							+ "handler " + attribute + ". The use of Javascript is not allowed in VPDText; remove "
							+ "the attribute.", object));
				}
			}
		}
	}

	private static boolean isExternal(String src) {
		String value = src.strip();
		return value.startsWith("//") || value.matches("(?i)[a-z][a-z0-9+.-]*:.*");
	}

	private static List<EObject> organizationItems(EObject container) {
		List<EObject> items = new ArrayList<>(Paths.objects(container, "Item"));
		for (EObject section : Paths.objects(container, "Section")) {
			items.addAll(organizationItems(section));
		}
		return items;
	}

	// ----- Activity Model

	private static void amRules(EObject documentRoot, EObject root, List<HlomProblem> problems) {
		List<EObject> nodes = activityNodes(root);
		Map<String, EObject> nodeById = uniqueIds(nodes, "ActivityNode", "activity node", "Links and rules refer to "
				+ "activity nodes by id", problems);
		uniqueIds(sections(root), "NodeSection", "node section", "The id identifies the section", problems);
		List<EObject> counters = Paths.objects(root, "Properties/Counters/Counter");
		Map<String, EObject> counterById = uniqueIds(counters, "Counter", "counter", "Counter actions refer to "
				+ "counters by id (CounterPath)", problems);

		// Links: "ActivityNodeA links to ActivityNodeB".
		Map<String, List<EObject>> linksFrom = new HashMap<>();
		Map<String, Set<String>> edges = new HashMap<>();
		for (EObject link : Paths.objects(root, "Links/Link")) {
			String from = refId(Paths.text(link, "ActivityNodeA"));
			String to = refId(Paths.text(link, "ActivityNodeB"));
			boolean fromOk = checkNodeRef(from, nodeById, link, "The link " + quoteLabel(link) + "starts at", problems);
			boolean toOk = checkNodeRef(to, nodeById, link, "The link " + quoteLabel(link) + "leads to", problems);
			if (fromOk) {
				linksFrom.computeIfAbsent(from, _ -> new ArrayList<>()).add(link);
			}
			if (fromOk && toOk) {
				edges.computeIfAbsent(from, _ -> new LinkedHashSet<>()).add(to);
			}
			if (Paths.text(link, "@label") == null && !"false".equals(Paths.text(link, "@display"))) {
				problems.add(problem(Severity.INFO, "The link from '" + from + "' to '" + to + "' has no label. The "
						+ "player uses the label to present the link to the learner; add one, or set display to false.",
						link));
			}
		}

		// Redirections (RuleRedirect of counter, timer and conditional rules).
		Set<String> roots = new LinkedHashSet<>();
		for (EObject object : contents(root)) {
			if (Paths.feature(object, "RuleRedirect") == null) {
				continue;
			}
			String target = refId(Paths.text(object, "RuleRedirect"));
			if (target == null || !checkNodeRef(target, nodeById, object, "The RuleRedirect of a "
					+ elementName(object) + " leads to", problems)) {
				continue;
			}
			EObject node = ancestor(object, "ActivityNode");
			if (node != null && Paths.text(node, "@id") != null) {
				edges.computeIfAbsent(Paths.text(node, "@id"), _ -> new LinkedHashSet<>()).add(target);
			} else {
				roots.add(target); // Rules of the whole activity may redirect from any node.
			}
		}

		// Counter actions refer to counters.
		for (EObject object : contents(root)) {
			if (Paths.feature(object, "CounterPath") == null) {
				continue;
			}
			String path = Paths.text(object, "CounterPath");
			String counter = refId(path);
			if (counter != null && !counterById.containsKey(counter)) {
				problems.add(problem(Severity.ERROR, "The counter action refers to the counter '" + counter + "', "
						+ "which Properties/Counters does not define. Define the counter or correct the CounterPath.",
						object));
			}
		}

		// Conditions: operands are activity nodes or VPD elements.
		EObject vpd = sibling(documentRoot, VPD_FILE, VPD);
		for (EObject object : contents(root)) {
			if (Paths.feature(object, "Operand") == null) {
				continue;
			}
			for (String operand : Paths.texts(object, "Operand")) {
				if (operand.startsWith("/ActivityModel")) {
					checkNodeRef(refId(operand), nodeById, object, "The condition operand " + operand + " refers to",
							problems);
				} else if (operand.startsWith("/VirtualPatientData")) {
					checkVpdPath(operand, "The condition operand", vpd, object, problems);
				} else {
					problems.add(problem(Severity.WARNING, "The condition operand '" + operand + "' is neither an "
							+ "activity node (/ActivityModel/ActivityNodes/NodeSection/ActivityNode[@id='...']) nor a "
							+ "VPD element (/VirtualPatientData/...); conditions test visited nodes and triggered "
							+ "data.", object));
				}
			}
		}

		// Content refers to DAM nodes of the package's Data Availability Model.
		EObject dam = sibling(documentRoot, DAM_FILE, DAM);
		if (dam != null) {
			Set<String> damIds = new HashSet<>(Paths.texts(dam, "DAMNode/@id"));
			for (EObject node : nodes) {
				String content = refId(Paths.text(node, "Content"));
				if (content != null && !damIds.contains(content)) {
					problems.add(problem(Severity.WARNING, "The Content of activity node " + quoteLabel(node)
							+ "refers to DAMNode '" + content + "', which the Data Availability Model of this package ("
							+ DAM_FILE + ") does not define.", node));
				}
			}
		}

		// Probabilistic links: "the sum of all the weighting elements of links leaving a particular node should
		// equal to 100".
		for (EObject node : nodeById.values()) {
			String id = Paths.text(node, "@id");
			List<EObject> leaving = linksFrom.getOrDefault(id, List.of());
			List<Integer> weights = leaving.stream().map(l -> Paths.text(l, "Weighting")).filter(Objects::nonNull)
					.map(Paths::integer).filter(Objects::nonNull).toList();
			int sum = weights.stream().mapToInt(Integer::intValue).sum();
			if (!weights.isEmpty() && sum != 100) {
				problems.add(problem(Severity.WARNING, "The weightings of the links leaving activity node "
						+ quoteLabel(node) + "add up to " + sum + ". The sum of all the weighting elements of links "
						+ "leaving a node should equal 100.", node));
			}
			if ("on".equals(Paths.literal(node, "Rules/Probability")) && weights.isEmpty() && !leaving.isEmpty()) {
				problems.add(problem(Severity.WARNING, "Activity node " + quoteLabel(node) + "has Probability on, but "
						+ "none of the links leaving it has a Weighting. Give the links weightings that add up to 100, "
						+ "or turn Probability off.", node));
			}
		}

		// Every node reachable from the first node ("The first node in the Activity Model file is considered to be
		// the first node in the activity").
		if (!nodes.isEmpty()) {
			Set<String> reached = new HashSet<>();
			Deque<String> queue = new ArrayDeque<>();
			String first = Paths.text(nodes.getFirst(), "@id");
			if (first != null) {
				queue.add(first);
			}
			queue.addAll(roots);
			for (EObject node : nodes) {
				if ("on".equals(Paths.literal(node, "Rules/NavigateGlobal")) && Paths.text(node, "@id") != null) {
					queue.add(Paths.text(node, "@id"));
				}
			}
			while (!queue.isEmpty()) {
				String id = queue.poll();
				if (reached.add(id)) {
					queue.addAll(edges.getOrDefault(id, Set.of()));
				}
			}
			for (EObject node : nodes.subList(1, nodes.size())) {
				String id = Paths.text(node, "@id");
				if (id != null && !reached.contains(id)) {
					problems.add(problem(Severity.WARNING, "Activity node " + quoteLabel(node) + "cannot be reached "
							+ "from the first node " + quoteLabel(nodes.getFirst()) + "of the activity: no path of "
							+ "links or rule redirections leads to it and NavigateGlobal is not on. Link to it, turn "
							+ "NavigateGlobal on or remove it.", node));
				}
			}
		}
	}

	private static boolean checkNodeRef(String id, Map<String, EObject> nodeById, EObject object, String what,
			List<HlomProblem> problems) {
		if (id == null) {
			return false;
		}
		if (!nodeById.containsKey(id)) {
			problems.add(problem(Severity.ERROR, what + " the activity node '" + id + "', which this Activity Model "
					+ "does not define. Correct the reference or add the node.", object));
			return false;
		}
		return true;
	}

	/** The activity nodes in document order (also those of nested node sections). */
	static List<EObject> activityNodes(EObject root) {
		List<EObject> nodes = new ArrayList<>();
		for (EObject section : Paths.objects(root, "ActivityNodes/NodeSection")) {
			collectNodes(section, nodes);
		}
		return nodes;
	}

	private static void collectNodes(EObject section, List<EObject> nodes) {
		nodes.addAll(Paths.objects(section, "ActivityNode"));
		for (EObject nested : Paths.objects(section, "NodeSection")) {
			collectNodes(nested, nodes);
		}
	}

	private static List<EObject> sections(EObject root) {
		List<EObject> sections = new ArrayList<>();
		Deque<EObject> queue = new ArrayDeque<>(Paths.objects(root, "ActivityNodes/NodeSection"));
		while (!queue.isEmpty()) {
			EObject section = queue.poll();
			sections.add(section);
			queue.addAll(Paths.objects(section, "NodeSection"));
		}
		return sections;
	}

	// ----- Data Availability Model

	private static void damRules(EObject documentRoot, EObject root, List<HlomProblem> problems) {
		List<EObject> damNodes = Paths.objects(root, "DAMNode");
		Map<String, EObject> byId = uniqueIds(damNodes, "DAMNode", "DAM node", "Activity nodes and other DAM nodes "
				+ "refer to DAM nodes by id", problems);
		EObject vpd = sibling(documentRoot, VPD_FILE, VPD);
		Map<String, Set<String>> includes = new LinkedHashMap<>();
		for (EObject damNode : damNodes) {
			String nodeId = Paths.text(damNode, "@id");
			Map<String, EObject> orders = new HashMap<>();
			for (EObject item : Paths.objects(damNode, "DAMNodeItem")) {
				String itemPath = Paths.text(item, "ItemPath");
				if (itemPath != null && itemPath.startsWith("/VirtualPatientData")) {
					checkVpdPath(itemPath, "The ItemPath", vpd, item, problems);
				}
				String alternative = Paths.text(item, "AlternativePath");
				if (alternative != null) {
					if (alternative.startsWith("/VirtualPatientData")) {
						checkVpdPath(alternative, "The AlternativePath", vpd, item, problems);
					}
					if (itemPath != null && !itemPath.contains("/XtensibleInfo")) {
						problems.add(problem(Severity.WARNING, "This item has an AlternativePath, but its ItemPath "
								+ "does not point to an XtensibleInfo element. \"AlternativePath should only be used "
								+ "if the ItemPath points to an XtensibleInfo element\"; remove it.", item));
					}
				}
				for (String reference : List.of("ItemComment", "DAMNodePath")) {
					for (String path : Paths.texts(item, reference)) {
						String target = refId(path);
						if (target == null) {
							continue;
						}
						if (!byId.containsKey(target)) {
							problems.add(problem(Severity.ERROR, "The " + reference + " of an item of DAM node "
									+ quoteLabel(damNode) + "refers to DAMNode '" + target + "', which this Data "
									+ "Availability Model does not define.", item));
						} else if (nodeId != null) {
							includes.computeIfAbsent(nodeId, _ -> new LinkedHashSet<>()).add(target);
						}
					}
				}
				String order = Paths.text(item, "ItemOrder");
				if (order != null && orders.putIfAbsent(order, item) != null) {
					problems.add(problem(Severity.INFO, "Two items of DAM node " + quoteLabel(damNode) + "have the "
							+ "ItemOrder " + order + "; the player uses ItemOrder to order the items, so give each "
							+ "item its own position.", item));
				}
			}
		}
		// "Cyclic inclusions, referencing a DAMNode that ultimately leads to a DAMNode including itself, are not
		// permitted."
		for (var entry : includes.entrySet()) {
			List<String> cycle = cycle(entry.getKey(), includes);
			if (cycle != null) {
				problems.add(problem(Severity.ERROR, "DAM node '" + entry.getKey() + "' includes itself through "
						+ "DAMNodePath or ItemComment (" + String.join(" \u2192 ", cycle) + "). Cyclic inclusions are "
						+ "not permitted; remove one of the references.", byId.get(entry.getKey())));
			}
		}
	}

	/** The path of a cycle from a node back to itself, or {@code null}. */
	private static List<String> cycle(String start, Map<String, Set<String>> edges) {
		Map<String, String> parent = new HashMap<>();
		Deque<String> queue = new ArrayDeque<>();
		for (String next : edges.getOrDefault(start, Set.of())) {
			if (next.equals(start)) {
				return List.of(start, start);
			}
			if (parent.putIfAbsent(next, start) == null) {
				queue.add(next);
			}
		}
		while (!queue.isEmpty()) {
			String node = queue.poll();
			for (String next : edges.getOrDefault(node, Set.of())) {
				if (next.equals(start)) {
					List<String> path = new ArrayList<>();
					for (String n = node; n != null && !n.equals(start); n = parent.get(n)) {
						path.addFirst(n);
					}
					path.addFirst(start);
					path.add(start);
					return path;
				}
				if (!parent.containsKey(next)) {
					parent.put(next, node);
					queue.add(next);
				}
			}
		}
		return null;
	}

	// ----- XPath references into the Virtual Patient Data

	/**
	 * Checks a path into the Virtual Patient Data: its element names against the VPD model (an error), and, if
	 * the package's VPD is known, that it selects an element there (a warning).
	 */
	private static void checkVpdPath(String path, String what, EObject vpd, EObject object,
			List<HlomProblem> problems) {
		List<Step> steps = steps(path);
		if (steps == null) {
			return;
		}
		String unknown = unknownStep(steps);
		if (unknown != null) {
			problems.add(problem(Severity.ERROR, what + " " + path.strip() + " names " + unknown + ". Paths into the "
					+ "Virtual Patient Data follow its element names, such as "
					+ "/VirtualPatientData/InterviewItem[@id='x'] or "
					+ "/VirtualPatientData/PatientDemographics/CoreDemographics/Name/text().", object));
		} else if (vpd != null && !selects(vpd, steps)) {
			problems.add(problem(Severity.WARNING, what + " " + path.strip() + " selects nothing in the Virtual "
					+ "Patient Data of this package (" + VPD_FILE + "). Check the element name and the id.", object));
		}
	}

	/** A step of an XPath: an element name (or {@code text()}, {@code @attribute}) with an optional predicate. */
	record Step(String name, String attribute, String value) {
	}

	/** The steps of an absolute XPath, or {@code null} if it is not one. */
	static List<Step> steps(String path) {
		String value = path == null ? "" : path.strip();
		if (!value.startsWith("/")) {
			return null;
		}
		List<String> tokens = new ArrayList<>();
		StringBuilder token = new StringBuilder();
		int depth = 0;
		for (int i = 1; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '[') {
				depth++;
			} else if (c == ']') {
				depth--;
			}
			if (c == '/' && depth == 0) {
				tokens.add(token.toString().strip());
				token.setLength(0);
			} else {
				token.append(c);
			}
		}
		tokens.add(token.toString().strip());
		List<Step> steps = new ArrayList<>();
		for (String t : tokens) {
			int bracket = t.indexOf('[');
			String name = (bracket < 0 ? t : t.substring(0, bracket)).strip();
			if (name.isEmpty()) {
				return null;
			}
			Matcher predicate = PREDICATE.matcher(bracket < 0 ? "" : t.substring(bracket));
			steps.add(predicate.find() ? new Step(name, predicate.group(1), predicate.group(2))
					: new Step(name, null, null));
		}
		return steps;
	}

	/** The id in the last {@code [@id='...']} predicate of a path, or {@code null}. */
	static String refId(String path) {
		List<Step> steps = steps(path);
		if (steps == null) {
			return null;
		}
		for (Step step : steps.reversed()) {
			if ("id".equals(step.attribute())) {
				return step.value();
			}
		}
		return null;
	}

	/** A description of the first step of a VPD path that the VPD model doesn't have, or {@code null}. */
	private static String unknownStep(List<Step> steps) {
		EClass eClass = vpdRootClass();
		if (eClass == null || !steps.getFirst().name().equals("VirtualPatientData")) {
			return eClass == null ? null : "the root element " + steps.getFirst().name();
		}
		for (Step step : steps.subList(1, steps.size())) {
			String name = step.name();
			if (name.equals("text()") || name.equals("node()") || name.equals("*") || name.contains(":")) {
				return null;
			}
			EStructuralFeature feature = feature(eClass, name);
			if (feature == null) {
				List<String> names = new ArrayList<>();
				for (EStructuralFeature f : eClass.getEAllStructuralFeatures()) {
					int kind = META.getFeatureKind(f);
					if (kind == ExtendedMetaData.ELEMENT_FEATURE || kind == ExtendedMetaData.ATTRIBUTE_FEATURE) {
						names.add((kind == ExtendedMetaData.ATTRIBUTE_FEATURE ? "@" : "") + META.getName(f));
					}
				}
				return "'" + name + "', which " + META.getName(eClass).replaceFirst("Type$", "") + " does not have "
						+ "(it has " + String.join(", ", names) + ")";
			}
			if (!(feature instanceof EReference reference) || name.equals("XtensibleInfo")
					|| XHTML_NS.equals(META.getNamespace(feature))) {
				return null; // A simple value, an extension or formatted text: nothing more to check.
			}
			eClass = reference.getEReferenceType();
		}
		return null;
	}

	private static EClass vpdRootClass() {
		EPackage ePackage = MedbiqModels.get(VPD_NS);
		if (ePackage == null) {
			return null;
		}
		EClass documentRoot = META.getDocumentRoot(ePackage);
		EStructuralFeature root = documentRoot == null ? null
				: META.getElement(documentRoot, VPD_NS, "VirtualPatientData");
		return root instanceof EReference reference ? reference.getEReferenceType() : null;
	}

	private static EStructuralFeature feature(EClass eClass, String step) {
		boolean attribute = step.startsWith("@");
		String name = attribute ? step.substring(1) : step;
		for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if ((attribute ? kind == ExtendedMetaData.ATTRIBUTE_FEATURE : kind == ExtendedMetaData.ELEMENT_FEATURE)
					&& name.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	/** Whether a path (starting with the root element's name) selects something in a document. */
	static boolean selects(EObject root, List<Step> steps) {
		List<EObject> current = List.of(root);
		for (Step step : steps.subList(1, steps.size())) {
			String name = step.name();
			if (name.equals("text()") || name.equals("node()") || name.startsWith("@") || name.contains(":")) {
				return !current.isEmpty();
			}
			List<EObject> next = new ArrayList<>();
			for (EObject object : current) {
				for (Object value : Paths.values(object, name.equals("*") ? "" : name)) {
					if (!(value instanceof EObject child)) {
						return step.attribute() == null; // A simple value: it exists.
					}
					if (step.attribute() == null || step.value().equals(Paths.text(child, "@" + step.attribute()))) {
						next.add(child);
					}
				}
			}
			if (name.equals("XtensibleInfo")) {
				return !next.isEmpty();
			}
			current = next;
			if (current.isEmpty()) {
				return false;
			}
		}
		return !current.isEmpty();
	}

	// ----- The other documents of the package

	/**
	 * The root element of another document of the package (a file of the given name in the folder of the checked
	 * document), or {@code null} if the document is not in a folder or the file doesn't exist or can't be read.
	 */
	private static EObject sibling(EObject documentRoot, String fileName, String typeId) {
		Resource resource = documentRoot.eResource();
		URI uri = resource == null ? null : resource.getURI();
		if (uri == null || uri.isRelative() || !(uri.isFile() || uri.isPlatform()) || uri.segmentCount() < 2
				|| fileName.equals(uri.lastSegment())) {
			return null;
		}
		MedbiqDocumentType type = MedbiqStandards.byId(typeId);
		if (type == null) {
			return null;
		}
		URI other = uri.trimSegments(1).appendSegment(fileName);
		try {
			ResourceSet resourceSet = type.createResourceSet();
			URIConverter converter = resourceSet.getURIConverter();
			if (!converter.exists(other, null)) {
				SIBLINGS.remove(other);
				return null;
			}
			Object stamp = converter.getAttributes(other, Map.of(URIConverter.OPTION_REQUESTED_ATTRIBUTES,
					Set.of(URIConverter.ATTRIBUTE_TIME_STAMP))).get(URIConverter.ATTRIBUTE_TIME_STAMP);
			long timeStamp = stamp instanceof Long value ? value : URIConverter.NULL_TIME_STAMP;
			Sibling cached = SIBLINGS.get(other);
			if (cached != null && cached.timeStamp() == timeStamp && timeStamp != URIConverter.NULL_TIME_STAMP) {
				return cached.root();
			}
			Resource loaded = resourceSet.getResource(other, true);
			EObject root = loaded.getContents().isEmpty() ? null : Paths.rootElement(loaded.getContents().getFirst());
			if (root == null || root.eClass().getEPackage() != type.ePackage()) {
				return null;
			}
			if (SIBLINGS.size() > 64) {
				SIBLINGS.clear();
			}
			SIBLINGS.put(other, new Sibling(timeStamp, root));
			return root;
		} catch (RuntimeException e) {
			return null; // An unreadable document is reported when it is checked itself.
		}
	}

	// ----- Helpers

	/** Reports duplicate ids among objects; returns the objects by id (the first of each id). */
	private static Map<String, EObject> uniqueIds(List<EObject> objects, String element, String words, String why,
			List<HlomProblem> problems) {
		Map<String, EObject> byId = new LinkedHashMap<>();
		for (EObject object : objects) {
			String id = Paths.text(object, "@id");
			if (id != null && byId.putIfAbsent(id, object) != null) {
				problems.add(problem(Severity.ERROR, "Two " + words + "s have the id '" + id + "'. " + why
						+ ", so each " + element + " needs a unique id.", object));
			}
		}
		return byId;
	}

	private static List<EObject> contents(EObject root) {
		List<EObject> all = new ArrayList<>();
		for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
			all.add(it.next());
		}
		return all;
	}

	private static String elementName(EObject object) {
		EStructuralFeature feature = object.eContainmentFeature();
		String name = feature == null ? null : META.getName(feature);
		return name != null ? name : object.eClass().getName();
	}

	private static EObject ancestor(EObject object, String name) {
		for (EObject o = object.eContainer(); o != null; o = o.eContainer()) {
			if (name.equals(elementName(o))) {
				return o;
			}
		}
		return null;
	}

	/** {@code 'label' } of an object with a label (followed by a space), or its id, or an empty string. */
	private static String quoteLabel(EObject object) {
		String label = Paths.text(object, "@label");
		if (label == null) {
			label = Paths.text(object, "DAMNodeLabel");
		}
		String id = Paths.text(object, "@id");
		if (label != null) {
			return "'" + label + "'" + (id != null ? " (" + id + ")" : "") + " ";
		}
		return id != null ? "'" + id + "' " : "";
	}

	private static HlomProblem problem(Severity severity, String message, EObject object) {
		return new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1);
	}

	private static String capitalize(String text) {
		return text.isEmpty() ? text : Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	@SafeVarargs
	private static <T> T firstNonNull(T... values) {
		for (T value : values) {
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------------------------------
	// Help
	// ----------------------------------------------------------------------------------------------------

	private static final String ID = "A unique identifier for this set of data that can be used by other virtual "
			+ "patient components to control how this set of data is used in an educational activity "
			+ "(/VirtualPatientData/<element>[@id='...']).";
	private static final String SOURCE = "The taxonomy or vocabulary serving as the source of the name, such as "
			+ "SNOMED CT or ICD-10.";
	private static final String SOURCE_ID = "The unique identifier assigned to this taxonomic reference within the "
			+ "taxonomy or vocabulary.";
	private static final String XTENSIBLE = "Elements defined by the organization implementing the schema, in "
			+ "their own namespace. The virtual patient's decision path should not depend on them, as many systems "
			+ "cannot use this data.";

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		Map<String, String> help = new HashMap<>();
		switch (type.id()) {
			case VPD -> vpdHelp(help);
			case AM -> amHelp(help);
			case DAM -> damHelp(help);
			default -> {
				// No help for other types.
			}
		}
		help.put("XtensibleInfo", XTENSIBLE);
		return help;
	}

	private static void vpdHelp(Map<String, String> help) {
		help.put("VirtualPatientData", "The root element: the clinical and demographic data of a virtual patient, "
				+ "the granular textual content the Data Availability Model refers to.");
		help.put("PatientDemographics", "The name, age, sex and other demographic characteristics of the virtual "
				+ "patient.");
		help.put("CoreDemographics", "Key characteristics of the human or animal virtual patient: name, id, age, "
				+ "sex, race, species and breed.");
		help.put("@id", ID);
		help.put("PatientID", "An identifier for the virtual patient, similar to the patient ID used to track a "
				+ "patient within a hospital.");
		help.put("Name", "The full name of the virtual patient, for example John Smith.");
		help.put("Age", "The age of the virtual patient as a duration: 15 years 6 months is P15Y6M, one day and two "
				+ "hours is P1DT2H.");
		help.put("Sex", "The sex of the virtual patient: Male, Female or Indeterminate.");
		help.put("Race", "A population group or classification usually based on physical characteristics.");
		help.put("Species", "The biological classification of an animal patient, for example Felis catus.");
		help.put("Breed", "The species subgroup of an animal patient, for example Standard Poodle.");
		help.put("DemographicCharacteristic", "A further demographic characteristic, such as marital status or "
				+ "occupation.");
		help.put("Title", "The title of the demographic characteristic, for example Marital status.");
		help.put("DemographicCharacteristic/Description", "The demographic characteristic in more detail, for example "
				+ "Divorced.");
		help.put("VPDText", "Narrative or other descriptive text of the virtual patient, formatted with a limited set "
				+ "of XHTML (an xhtml:div, edited on the XML page). No JavaScript; images must be in the package.");
		help.put("@textType", "The function of the text in the virtual patient: chief complaint, history, problem, "
				+ "allergy or narrative (the default).");
		help.put("div", "The formatted text (XHTML div). It may contain paragraphs, lists, headings, tables, images, "
				+ "links and media references; edit it on the XML page.");
		help.put("Medication", "A medication taken by the virtual patient: name, dose, route and frequency.");
		help.put("MedicationName", "The name of the medication, possibly referencing a medical taxonomy or "
				+ "vocabulary.");
		help.put("@source", SOURCE);
		help.put("@sourceID", SOURCE_ID);
		help.put("Dose", "The dosage of the medication, for example 10 mg.");
		help.put("Route", "The route of administration, for example oral.");
		help.put("Frequency", "The frequency of administration, for example daily.");
		help.put("InterviewItem", "A single question and response that is part of the clinical history.");
		help.put("Question", "The interview question that the learner may ask the virtual patient.");
		help.put("Response", "The virtual patient's response to the interview question.");
		help.put("PhysicalExam", "A single physical exam and its findings.");
		help.put("ExamName", "The name of the exam, for example blood pressure.");
		help.put("LocationOnBody", "The precise body location of the physical exam.");
		help.put("BodyPart", "The body part, such as arm, that is the location of the physical exam.");
		help.put("ProximalOrDistal", "The body part's location in reference to the center of the body: Proximal or "
				+ "Distal.");
		help.put("RightOrLeft", "The side of the body part's location: Right or Left.");
		help.put("FrontOrBack", "The facing direction of the body location: Front or Back.");
		help.put("InferiorOrSuperior", "Whether the body location is toward the feet (Inferior) or the head "
				+ "(Superior).");
		help.put("Action", "The action the virtual clinician takes to perform the exam, for example palpate.");
		help.put("Finding", "The findings of the exam, for example normal heartbeat.");
		help.put("PhysicalExam/Description", "The exam in more detail.");
		help.put("Description", "A more detailed description.");
		help.put("DiagnosticTest", "The result of a single diagnostic test.");
		help.put("TestName", "The name of the test.");
		help.put("Unit", "The unit of measure of the test.");
		help.put("Result", "The result of the test.");
		help.put("Normal", "The normal range for the test.");
		help.put("Diagnosis", "A single differential diagnosis, or a final diagnosis intended by the virtual patient "
				+ "author.");
		help.put("@authorDiagnosis", "Whether the author specifies this diagnosis as an appropriate final diagnosis "
				+ "(default false).");
		help.put("DiagnosisName", "The name of the diagnosis, possibly referencing a taxonomy or vocabulary.");
		help.put("Likelihood", "The likelihood of the differential diagnosis being correct: high, medium, low or "
				+ "none.");
		help.put("Intervention", "A single intervention, such as a procedure or a medication given.");
		help.put("InterventionName", "The name of the intervention, for example appendectomy.");
		help.put("Intervention/Medication", "A medication used as the intervention.");
		help.put("Appropriateness", "How appropriate the intervention is: always, ok, never or none.");
		help.put("Results", "The results of the intervention.");
		help.put("Organization", "A hierarchical structure of the data elements (sections and items) that authoring "
				+ "systems importing the data can use.");
		help.put("Section", "A group of data items (a branch of the organization); sections may contain sections.");
		help.put("Item", "A leaf of the organization that points to a single data element by its id.");
		help.put("@idRef", "The unique identifier of the data element within this document.");
		help.put("@label", "A label for this piece of data, such as Chief Complaint.");
		help.put("media", "A reference to a media resource of the package (refPath) with alternate content shown when "
				+ "the player cannot display the media.");
		help.put("@refPath", "An XPath to the media resource in the manifest: "
				+ "/manifest/resources/resource[@identifier='...'].");
	}

	private static void amHelp(Map<String, String> help) {
		help.put("ActivityModel", "The root element: the learning activity of the virtual patient, with the paths "
				+ "through the content and the scoring and timing mechanisms.");
		help.put("Properties", "Properties of the whole activity: counters and the timer.");
		help.put("Counters", "Numeric counters present throughout the activity, for a game-like environment or for "
				+ "assessment.");
		help.put("Counter", "A numeric counter, for example patient health, costs or morale; its value changes with "
				+ "the learner's actions.");
		help.put("Counter/@id", "A unique identifier of the counter, used by counter actions "
				+ "(/ActivityModel/Properties/Counters/Counter[@id='...']).");
		help.put("@isVisible", "Whether the counter or timer is shown to the learner (default true).");
		help.put("CounterLabel", "A label for presenting the counter and its value, for example Patient Health Score.");
		help.put("CounterUnitsSuffix", "A text suffix for the counter value, for example points.");
		help.put("CounterUnitsPrefix", "A text prefix for the counter value, for example $.");
		help.put("CounterInitValue", "The value of the counter at the beginning of the activity.");
		help.put("CounterRules", "Rules that redirect the learner or show a message based on the counter value.");
		help.put("TimerRules", "Rules that redirect the learner or show a message based on the timer value.");
		help.put("Rule", "When the counter or timer value has the given Relation to Value, the player redirects the "
				+ "learner (RuleRedirect) and/or shows RuleMessage.");
		help.put("Relation", "The relation to Value that triggers the rule: eq, neq, leq, lt, geq or gt.");
		help.put("Value", "The value that, with Relation, sets the point at which the rule is triggered.");
		help.put("RuleRedirect", "The activity node the learner is redirected to: "
				+ "/ActivityModel/ActivityNodes/NodeSection/ActivityNode[@id='...']. Without it no redirection "
				+ "occurs.");
		help.put("RuleMessage", "A message shown to the learner when the rule is triggered (or the condition is not "
				+ "met).");
		help.put("Timer", "A real-time timer; under Properties it runs through the whole activity, under an activity "
				+ "node's Services only at that node.");
		help.put("TimerDeltaSeconds", "The number of seconds on the timer at the beginning.");
		help.put("TimerDirection", "Whether the timer counts up or down.");
		help.put("ActivityNodes", "The nodes that form one or more paths through the learning activity.");
		help.put("NodeSection", "A broad section of related activity nodes (or nested sections) that the player uses "
				+ "to organize the presentation.");
		help.put("ActivityNode", "A point on a path of the activity: it presents a DAM node's content. The first "
				+ "node of the file is the first node of the activity; a node without links leaving it is an end "
				+ "node.");
		help.put("@id", "A unique identifier, used by links and rules to refer to this element.");
		help.put("@label", "A text label the player uses to present this element to the learner.");
		help.put("Content", "The DAM node whose data this activity node presents: "
				+ "/DataAvailabilityModel/DAMNode[@id='...'].");
		help.put("Rules", "Rules for accessing this node, changing counters and navigating to other nodes.");
		help.put("CounterActionRule", "Adds to, subtracts from or sets a counter when the learner enters the node or "
				+ "selects the link.");
		help.put("CounterOperator", "The operation on the counter: + (add), - (subtract) or = (set).");
		help.put("CounterRuleValue", "The amount to add or subtract, or the value to which the counter is set.");
		help.put("CounterPath", "The counter the action applies to: "
				+ "/ActivityModel/Properties/Counters/Counter[@id='...'].");
		help.put("CounterRuleEnabled", "Whether the counter action is taken here (on, the default, or off), so that "
				+ "counter rules can be evaluated selectively.");
		help.put("Probability", "If on, the player displays only one of the links leaving this node, chosen by the "
				+ "links' weightings.");
		help.put("ConditionalRule", "A Boolean condition on visited nodes and triggered data that must be true to "
				+ "enter the node, with a redirection and/or message if it is not.");
		help.put("Operator", "The Boolean expression of the condition (And, Or, Nand or Nor).");
		help.put("And", "All operands must have been visited or triggered.");
		help.put("Or", "At least one of the operands must have been visited or triggered.");
		help.put("Nand", "The operands must not all have been visited or triggered.");
		help.put("Nor", "None of the operands may have been visited or triggered.");
		help.put("Operand", "An XPath to an activity node (visited) or a VPD element (triggered).");
		help.put("NavigateGlobal", "If on, learners may go to this node from anywhere in the activity through a "
				+ "navigation menu; if off, only through links.");
		help.put("Services", "Services of the node: a timer that runs only at this node.");
		help.put("Links", "The links between activity nodes that create the paths through the activity.");
		help.put("Link", "A unidirectional link from ActivityNodeA to ActivityNodeB, with its label and weighting.");
		help.put("@display", "Whether the player displays the link's label (default true).");
		help.put("ActivityNodeA", "The activity node the link starts at: "
				+ "/ActivityModel/ActivityNodes/NodeSection/ActivityNode[@id='...'].");
		help.put("ActivityNodeB", "The activity node the link leads to: "
				+ "/ActivityModel/ActivityNodes/NodeSection/ActivityNode[@id='...'].");
		help.put("Weighting", "The percent chance that the link is displayed when Probability is on; the weightings "
				+ "of the links leaving a node should add up to 100.");
	}

	private static void damHelp(Map<String, String> help) {
		help.put("DataAvailabilityModel", "The root element: groups of virtual patient data and media resources "
				+ "(DAM nodes) that the activity model presents, so that data can be aggregated and progressively "
				+ "disclosed.");
		help.put("DAMNode", "A group of virtual patient data and media resources that may be referenced in the "
				+ "activity model or elsewhere in the DAM.");
		help.put("@id", "A unique identifier for this group of data: /DataAvailabilityModel/DAMNode[@id='...'].");
		help.put("DAMNodeLabel", "A text label for this group of data, for authoring; the player ignores it.");
		help.put("DAMNodeItem", "References an element of the VPD, a media resource or another DAM node; repeated to "
				+ "form the group.");
		help.put("@display", "When the data is displayed: immediately (the default), ontrigger (after a user "
				+ "interaction), delayed (at a later point) or ifrequested (later, only if the user ordered it).");
		help.put("ItemPath", "An XPath to the VPD element (/VirtualPatientData/...) or to the media resource "
				+ "(/manifest/resources/resource[@identifier='...']) included in this item.");
		help.put("AlternativePath", "Alternative content in case ItemPath points to an XtensibleInfo extension the "
				+ "player cannot show; only used in that case.");
		help.put("ItemComment", "A DAM node with feedback the learner sees after triggering this item: "
				+ "/DataAvailabilityModel/DAMNode[@id='...']. Cyclic inclusions are not permitted.");
		help.put("DAMNodePath", "A DAM node included in this group (a hierarchical relation): "
				+ "/DataAvailabilityModel/DAMNode[@id='...']. Cyclic inclusions are not permitted.");
		help.put("ItemOrder", "The position of this item relative to the other items of the DAM node when the player "
				+ "displays them.");
	}
}
