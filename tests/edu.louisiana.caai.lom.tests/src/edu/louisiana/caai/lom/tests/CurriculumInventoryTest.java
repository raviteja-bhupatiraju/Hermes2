/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Auto;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Field;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Group;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Repeat;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.SectionSpec;
import edu.louisiana.caai.medbiq.model.standards.CurriculumInventory;

/** Tests the Curriculum Inventory content: templates, samples, titles, pages, search fields and rules. */
class CurriculumInventoryTest {

	private static final String V1 = "curriculum-inventory-v1";
	private static final String V10 = "curriculum-inventory-v10";
	private static final String SAMPLE_V10 = "samples/curriculum-inventory/bayou-state-md-2024-2025-v10.xml";
	private static final String SAMPLE_V1 = "samples/curriculum-inventory/pelican-bay-md-2015-2016-v1.xml";
	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	// ----------------------------------------------------------------------------------------------------
	// Templates and samples
	// ----------------------------------------------------------------------------------------------------

	@Test
	void templatesAreValidWithoutProblemsAndTitled() {
		for (String typeId : List.of(V1, V10)) {
			templateIsValidWithoutProblemsAndTitled(typeId);
		}
	}

	private static void templateIsValidWithoutProblemsAndTitled(String typeId) {
		String xml = MedbiqSamples.template(typeId, "Curriculum 2026-2027 & beyond");
		Result result = MedbiqSamples.assertValid(typeId, xml);
		assertEquals(typeId, result.type().id());
		assertTrue(result.problems().isEmpty(), () -> typeId + ": " + result.problems());
		assertEquals("Curriculum 2026-2027 & beyond", result.type().title(result.documentRoot()));
	}

	@Test
	void samplesAreValidWithoutProblems() {
		List<String> files = MedbiqSamples.files("samples/curriculum-inventory");
		assertTrue(files.contains(SAMPLE_V10) && files.contains(SAMPLE_V1), () -> "samples: " + files);
		Set<String> types = new HashSet<>();
		for (String file : files) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			assertTrue(result.problems().isEmpty(), () -> file + ": " + result.problems());
			assertNotNull(result.type().title(result.documentRoot()), file);
			assertNotNull(result.type().description(result.documentRoot()), file);
			types.add(result.type().id());
		}
		assertEquals(Set.of(V1, V10), types);
	}

	@Test
	void titleAndDescriptionOfSample() {
		Result result = MedbiqSamples.check(MedbiqSamples.text(SAMPLE_V10));
		assertEquals("Bayou State University College of Medicine M.D. Curriculum 2024-2025",
				result.type().title(result.documentRoot()));
		String description = result.type().description(result.documentRoot());
		assertTrue(description.startsWith("A four-year systems-based curriculum: two preclinical years"), description);
		assertFalse(description.contains("\n"), description);
	}

	@Test
	void descriptionSummarizesWithoutDescriptionElement() {
		String xml = MedbiqSamples.template(V10, "Summary").replaceFirst("\\s*<Description>[^<]*</Description>", "");
		Result result = MedbiqSamples.assertValid("v10 without description", xml);
		String description = result.type().description(result.documentRoot());
		assertTrue(description.contains("M.D.") && description.contains("1 event")
				&& description.contains("1 sequence block"), description);
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages, search fields, help
	// ----------------------------------------------------------------------------------------------------

	@Test
	void pagePathsExistInTheModel() {
		for (String typeId : List.of(V1, V10)) {
			pagePathsExistInTheModel(typeId);
		}
	}

	private static void pagePathsExistInTheModel(String typeId) {
		MedbiqDocumentType type = MedbiqStandards.byId(typeId);
		List<PageSpec> pages = type.content().pages(type);
		assertEquals(List.of("report", "program", "events", "expectations", "levels", "sequence", "integration"),
				pages.stream().map(PageSpec::id).toList());
		EClass documentRoot = META.getDocumentRoot(type.ePackage());
		EClass root = ((EReference) META.getElement(documentRoot, type.namespace(), type.rootElement()))
				.getEReferenceType();
		for (PageSpec page : pages) {
			for (SectionSpec section : page.sections()) {
				EClass sectionClass = section.path().isEmpty() ? root : classAt(root, section.path(), typeId);
				for (FieldSpec field : section.fields()) {
					checkField(sectionClass, field, typeId + "/" + page.id());
				}
			}
		}
	}

	@Test
	void versionSpecificFields() {
		assertTrue(hasPath(V1, "sequence", "Level") && !hasPath(V1, "sequence", "SequenceBlockLevels"));
		assertTrue(hasPath(V10, "sequence", "SequenceBlockLevels") && !hasPath(V10, "sequence", "Level"));
		assertTrue(hasPath(V10, "events", "@instructionalMethodDuration")
				&& !hasPath(V1, "events", "@instructionalMethodDuration"));
	}

	@Test
	void searchFieldsAndHelp() {
		for (String typeId : List.of(V1, V10)) {
			MedbiqDocumentType type = MedbiqStandards.byId(typeId);
			List<FieldGroup> groups = type.searchFields();
			assertFalse(groups.isEmpty());
			assertTrue(groups.stream().allMatch(g -> g.id().startsWith("ci-")), () -> groups.toString());
			assertTrue(groups.stream().anyMatch(g -> g.matches(List.of("CurriculumInventory", "Events", "Event",
					"InstructionalMethod"), null) && g.id().equals("ci-methods")));
			for (String element : List.of("Event", "SequenceBlock", "EventReference", "CompetencyObjectReference",
					"LevelsInProgram", "@primary", "@purpose", "IntegrationBlock", "Timing")) {
				assertNotNull(type.fieldHelp(List.of(element)), typeId + " " + element);
			}
			assertTrue(type.fieldHelp(List.of("Event", "Title")).contains("event"));
		}
		assertNotNull(MedbiqStandards.byId(V10).fieldHelp(List.of("StartingAcademicLevel")));
	}

	@Test
	void vocabularyTerms() {
		assertEquals(31, CurriculumInventory.terms("IM").size());
		assertEquals(18, CurriculumInventory.terms("AM").size());
		assertEquals(26, CurriculumInventory.terms("RE").size());
		assertTrue(CurriculumInventory.terms("IM").contains("Lecture"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules
	// ----------------------------------------------------------------------------------------------------

	@Test
	void duplicateIds() {
		assertReports(v10("id=\"E102\"", "id=\"E101\""), Severity.ERROR,
				"The event id 'E101' is used by more than one");
		assertReports(v10("id=\"MSK\" required", "id=\"FOM\" required"), Severity.ERROR,
				"The sequence block id 'FOM' is used by more than one");
		assertReports(v10("<IntegrationBlock id=\"ANAT\">", "<IntegrationBlock id=\"HSS\">"), Severity.ERROR,
				"The integration block id 'HSS' is used by more than one");
		assertReports(v10("<lom:entry>https://medicine.bayoustate.example/objectives/FOM-2</lom:entry>",
				"<lom:entry>https://medicine.bayoustate.example/objectives/FOM-1</lom:entry>"), Severity.ERROR,
				"is used by more than one competency object");
	}

	@Test
	void referencesMustResolve() {
		assertReports(v10("Event[@id='E107']", "Event[@id='E999']"), Severity.ERROR, "references the event 'E999'");
		assertReports(v10("objectives/CVP-1']", "objectives/XXX']"), Severity.ERROR,
				"references the competency object 'https://medicine.bayoustate.example/objectives/XXX'");
		assertReports(v10("SequenceBlock[@id='GHE']", "SequenceBlock[@id='NOPE']"), Severity.ERROR,
				"references the sequence block 'NOPE'");
		assertReports(v10("Level[@number='4']</EndingAcademicLevel>", "Level[@number='5']</EndingAcademicLevel>"),
				Severity.ERROR, "references academic level 5");
		assertReports(v1("Level[@number='4']</Level>", "Level[@number='5']</Level>"), Severity.ERROR,
				"references academic level 5");
		String next = "</EventReference>\n\t\t\t<EventReference>";
		assertReports(v10("<EventReference>/CurriculumInventory/Events/Event[@id='E111']" + next,
				"<EventReference>/CurriculumInventory/Events/Event[@id='E000']" + next), Severity.ERROR,
				"The integration block 'HSS'");
	}

	@Test
	void academicLevels() {
		assertReports(v10("<LevelsInProgram>4<", "<LevelsInProgram>5<"), Severity.ERROR,
				"LevelsInProgram is 5 but there are 4 Levels");
		assertReports(v10("<Level number=\"4\">", "<Level number=\"3\">").replace("Level[@number='4']",
				"Level[@number='3']"), Severity.WARNING, "is used by more than one level");
		assertReports(v10("Level[@number='1']</StartingAcademicLevel>\n\t\t\t\t<EndingAcademicLevel>"
				+ "/CurriculumInventory/AcademicLevels/Level[@number='2']", "Level[@number='2']</StartingAcademicLevel>"
				+ "\n\t\t\t\t<EndingAcademicLevel>/CurriculumInventory/AcademicLevels/Level[@number='1']"),
				Severity.ERROR, "starts at academic level 2 but ends at level 1");
	}

	@Test
	void dates() {
		assertReports(v10("<ReportingStartDate>2024-07-01<", "<ReportingStartDate>2025-07-01<"), Severity.ERROR,
				"The ReportingStartDate (2025-07-01) is after the ReportingEndDate");
		assertReports(v10("<StartDate>2024-07-29</StartDate>\n\t\t\t\t\t<EndDate>2024-10-25</EndDate>",
				"<StartDate>2024-06-03</StartDate>\n\t\t\t\t\t<EndDate>2024-10-25</EndDate>"), Severity.WARNING,
				"outside the reporting period");
		assertReports(v10("<StartDate>2024-10-25</StartDate>\n\t\t\t\t<EndDate>2024-10-25</EndDate>",
				"<StartDate>2024-10-25</StartDate>\n\t\t\t\t<EndDate>2024-10-20</EndDate>"), Severity.ERROR,
				"starts (2024-10-25) after it ends (2024-10-20)");
		assertReports(v10("<ReportDate>2025-08-15<", "<ReportDate>2024-01-15<"), Severity.WARNING,
				"before the reporting period starts");
	}

	@Test
	void events() {
		assertReports(v10("primary=\"true\" source=\"AAMC\" sourceID=\"IM012\"",
				"primary=\"false\" source=\"AAMC\" sourceID=\"IM012\""), Severity.ERROR,
				"has no primary instructional method");
		assertReports(v10("primary=\"false\" source=\"AAMC\" sourceID=\"IM010\"",
				"primary=\"true\" source=\"AAMC\" sourceID=\"IM010\""), Severity.WARNING,
				"instructional methods marked as primary");
		assertReports(v10("<EventDuration>PT18H<", "<EventDuration>PT10H<"), Severity.WARNING,
				"more than its EventDuration (PT10H)");
		assertReports(v10("Event[@id='E107']", "Event[@id='E106']"), Severity.WARNING,
				"The event 'E107' (Musculoskeletal and Skin Final Examination) is not part of any sequence block");
		assertReports(v10("Event[@id='E107']", "Event[@id='E106']"), Severity.WARNING,
				"lists the event 'E106' more than once");
	}

	@Test
	void vocabulary() {
		assertReports(v10("sourceID=\"IM012\" instructionalMethodDuration=\"PT18H\">Laboratory",
				"sourceID=\"IM013\" instructionalMethodDuration=\"PT18H\">Laboratory"), Severity.WARNING,
				"which is 'Lecture' in the Curriculum Inventory standardized vocabulary");
		assertReports(v10("source=\"AAMC\" sourceID=\"IM030\" instructionalMethodDuration=\"PT2H\">Workshop",
				"instructionalMethodDuration=\"PT2H\">Skills Session"), Severity.INFO,
				"'Skills Session' is not a term of the Curriculum Inventory");
		assertReports(v10("source=\"AAMC\" sourceID=\"RE004\">Cadaver", "source=\"AAMC\" sourceID=\"AM004\">Cadaver"),
				Severity.WARNING, "which is not a resource type code");
		assertReports(v10("source=\"AAMC\" sourceID=\"RE004\">Cadaver", ">Cadaver"), Severity.INFO,
				"has no sourceID");
	}

	@Test
	void sequenceBlocks() {
		assertReports(v10("<SequenceBlockReference order=\"2\">", "<SequenceBlockReference>"), Severity.ERROR,
				"has no order attribute; it must be used if the order of the parent sequence block is Ordered");
		assertReports(v10("<EndDate>2024-12-13</EndDate>\n\t\t\t</SequenceBlockEvent>",
				"<EndDate>2024-12-13</EndDate>\n\t\t\t</SequenceBlockEvent>\n\t\t\t<SequenceBlockReference>"
						+ "/CurriculumInventory/Sequence/SequenceBlock[@id='Y1']</SequenceBlockReference>"),
				Severity.ERROR, "contains itself through nested sequence blocks");
		assertReports(v10("maximum=\"2\"", "minimum=\"3\" maximum=\"2\""), Severity.ERROR,
				"has a minimum (3) greater than its maximum (2)");
		assertReports(v10("maximum=\"2\"", "maximum=\"3\""), Severity.WARNING, "allows a maximum of 3");
		assertReports(v10("<Duration>P70D</Duration>", "<Duration>P70D</Duration>\n\t\t\t\t<Duration>P71D</Duration>"),
				Severity.ERROR, "has more than one Duration");
		assertReports(v10("id=\"GHE\" required=\"Optional\"", "id=\"GHE\" required=\"Required In Track\""),
				Severity.WARNING, "is 'Required In Track' but is not nested in a track");
		assertReports(v10("<SequenceBlock id=\"FOM\" required=\"Required\">",
				"<SequenceBlock id=\"FOM\" required=\"Required\" order=\"Parallel\">"), Severity.INFO,
				"has order, minimum or maximum attributes but no nested sequence blocks");
		String undated = v1("<Title>Main Track</Title>\n\t\t\t<Timing>\n\t\t\t\t<Dates>\n\t\t\t\t\t"
				+ "<StartDate>2015-07-06</StartDate>\n\t\t\t\t\t<EndDate>2016-06-24</EndDate>\n\t\t\t\t</Dates>",
				"<Title>Main Track</Title>\n\t\t\t<Timing>\n\t\t\t\t<Duration>P6M</Duration>");
		assertReports(undated, Severity.INFO, "the parent sequence block should indicate dates");
		assertReports(v10("<StartDate>2024-11-04</StartDate>", "<StartDate>2024-07-01</StartDate>"),
				Severity.WARNING, "is not within the dates of sequence block 'Y1'");
	}

	@Test
	void expectationsAndReport() {
		String template = MedbiqSamples.template(V10, "T");
		assertReports(template.replaceFirst("(?s)\\s*<lom:identifier>.*?</lom:identifier>", ""), Severity.WARNING,
				"has no identifier (lom/general/identifier/entry)");
		assertReports(template.replaceFirst("(?s)\\s*<Sequence>.*</Sequence>", ""), Severity.WARNING,
				"The report has no Sequence");
		assertReports(template.replace("<ReportID domain=\"idd:example.edu:curriculuminventory\">", "<ReportID>"),
				Severity.INFO, "ReportID has no domain attribute");
		assertReports(template.replace("<m:InstitutionName>Name of the school</m:InstitutionName>", ""),
				Severity.WARNING, "has no InstitutionName");
		String objectives = "<cf:Entry>https://medicine.bayoustate.example/objectives/";
		assertReports(v10(objectives + "CVP-1</cf:Entry>\n\t\t\t</cf:Includes>",
				objectives + "OTHER</cf:Entry>\n\t\t\t</cf:Includes>"), Severity.INFO,
				"includes 'https://medicine.bayoustate.example/objectives/OTHER'");
	}

	// ----------------------------------------------------------------------------------------------------
	// Helpers
	// ----------------------------------------------------------------------------------------------------

	private static String v10(String from, String to) {
		return replace(MedbiqSamples.text(SAMPLE_V10), from, to);
	}

	private static String v1(String from, String to) {
		return replace(MedbiqSamples.text(SAMPLE_V1), from, to);
	}

	/** Replaces the first occurrence (line endings of the sample normalized to \n). */
	private static String replace(String text, String from, String to) {
		String normalized = text.replace("\r\n", "\n");
		int index = normalized.indexOf(from);
		assertTrue(index >= 0, () -> "not in the sample: " + from);
		return normalized.substring(0, index) + to + normalized.substring(index + from.length());
	}

	/** Asserts that the rules report a problem of a severity whose message contains a text. */
	private static void assertReports(String xml, Severity severity, String fragment) {
		Result result = MedbiqSamples.check(xml);
		List<String> messages = result.problems().stream()
				.filter(p -> p.problem().origin() == Origin.RULES && p.problem().severity() == severity)
				.map(p -> p.problem().message()).toList();
		assertTrue(messages.stream().anyMatch(m -> m.contains(fragment)),
				() -> "expected " + severity + " '" + fragment + "', got: " + result.problems().stream()
						.map(p -> p.problem().severity() + " " + p.problem().message()).toList());
	}

	private static boolean hasPath(String typeId, String pageId, String name) {
		MedbiqDocumentType type = MedbiqStandards.byId(typeId);
		return type.content().pages(type).stream().filter(p -> p.id().equals(pageId))
				.flatMap(p -> p.sections().stream()).flatMap(s -> s.fields().stream()).anyMatch(f -> contains(f, name));
	}

	private static boolean contains(FieldSpec field, String name) {
		if (field.path().equals(name) || field.path().endsWith("/" + name)) {
			return true;
		}
		List<FieldSpec> children = switch (field) {
			case Repeat repeat -> repeat.details();
			case Group group -> group.fields();
			case Field _, Auto _ -> List.of();
		};
		return children.stream().anyMatch(child -> contains(child, name));
	}

	private static void checkField(EClass eClass, FieldSpec field, String where) {
		switch (field) {
			case Field simple -> assertNotNull(feature(eClass, simple.path()), where + ": " + simple.path());
			case Auto auto -> {
				if (!auto.path().isEmpty()) {
					classAt(eClass, auto.path(), where);
				}
			}
			case Group group -> {
				EClass target = classAt(eClass, group.path(), where);
				group.fields().forEach(f -> checkField(target, f, where + "/" + group.path()));
			}
			case Repeat repeat -> {
				EClass target = classAt(eClass, repeat.path(), where);
				repeat.details().forEach(f -> checkField(target, f, where + "/" + repeat.path()));
				if (repeat.itemLabel() != null) {
					assertNotNull(feature(target, repeat.itemLabel()), where + ": item label " + repeat.itemLabel());
				}
			}
		}
	}

	/** The class of the element at a path. */
	private static EClass classAt(EClass start, String path, String where) {
		EStructuralFeature feature = feature(start, path);
		assertTrue(feature instanceof EReference, () -> where + ": " + path + " is not an element with content");
		return ((EReference) feature).getEReferenceType();
	}

	/** The feature at the end of a path of XML names ({@code @} for attributes), or {@code null}. */
	private static EStructuralFeature feature(EClass start, String path) {
		EClass eClass = start;
		EStructuralFeature feature = null;
		for (String step : path.split("/")) {
			if (eClass == null) {
				return null;
			}
			feature = step(eClass, step);
			if (feature == null) {
				return null;
			}
			eClass = feature instanceof EReference reference ? reference.getEReferenceType() : null;
		}
		return feature;
	}

	private static EStructuralFeature step(EClass eClass, String step) {
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
}
