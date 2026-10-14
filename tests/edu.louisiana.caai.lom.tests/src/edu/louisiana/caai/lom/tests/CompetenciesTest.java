/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
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
 * Tests the content of Competency Framework 1.0, Competency Object 1.0 and Performance Framework 1.0: templates,
 * samples, titles, pages, search fields, help and the rules.
 */
class CompetenciesTest {

	private static final String CF = "competency-framework-v1";
	private static final String CO = "competency-object-v1";
	private static final String PF = "performance-framework-v1";
	private static final String FOLDER = "samples/competencies/";
	private static final String FRAMEWORK = "core-competencies-framework.xml";
	private static final String OBJECT = "competency-patient-care.xml";
	private static final String PERFORMANCE = "milestones-performance-framework.xml";
	private static final String SKOS = "http://www.w3.org/2004/02/skos/core#";

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
	void templatesAreValidAndTitled() {
		for (String type : List.of(CF, CO, PF)) {
			Result result = MedbiqSamples.assertValid(type, MedbiqSamples.template(type, "Milestones & levels"));
			assertEquals(type, result.type().id());
			assertEquals("Milestones & levels", title(result));
			assertTrue(rules(result, Severity.WARNING).isEmpty(), () -> type + ": " + rules(result, Severity.WARNING));
		}
	}

	@Test
	void samplesAreValidWithoutRuleWarnings() {
		List<String> files = MedbiqSamples.files("samples/competencies");
		assertTrue(files.size() >= 6, () -> "samples: " + files);
		Set<String> types = new HashSet<>();
		for (String file : files) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			types.add(result.type().id());
			assertTrue(rules(result, Severity.WARNING).isEmpty(), () -> file + ": " + rules(result, Severity.WARNING));
			assertNotNull(title(result), file);
		}
		assertEquals(Set.of(CF, CO, PF), types);
	}

	@Test
	void titlesAndDescriptions() {
		Result framework = MedbiqSamples.check(sample(FRAMEWORK));
		assertEquals("Lakeside GME Core Competencies for Residents", title(framework));
		assertEquals("The Competent Physician", title(MedbiqSamples.check(sample("sample_framework.xml"))));
		assertEquals("Patient Care", title(MedbiqSamples.check(sample(OBJECT))));
		assertEquals("Lakeside GME Resident Milestones", title(MedbiqSamples.check(sample(PERFORMANCE))));
		String noDescription = sample(PERFORMANCE).replaceAll("(?s)<lom:description>.*?</lom:description>", "");
		Result performance = MedbiqSamples.check(noDescription);
		assertEquals("4 components, 2 performance scales",
				performance.type().description(performance.resource().getContents().getFirst()));
	}

	@Test
	void pagesSearchFieldsAndHelp() {
		for (String id : List.of(CF, CO, PF)) {
			MedbiqDocumentType type = MedbiqStandards.byId(id);
			List<PageSpec> pages = type.content().pages(type);
			assertTrue(pages.size() >= 4, id);
			assertEquals(pages.size(), pages.stream().map(PageSpec::id).distinct().count(), id);
			assertEquals("description", pages.getFirst().id());
			String prefix = switch (id) {
				case CF -> "cf-";
				case CO -> "co-";
				default -> "pf-";
			};
			for (FieldGroup group : type.searchFields()) {
				assertTrue(group.id().startsWith(prefix), group.id());
			}
			assertNotNull(type.fieldHelp(List.of("lom")), id);
			assertNotNull(type.fieldHelp(List.of("identifier")), id);
		}
		MedbiqDocumentType framework = MedbiqStandards.byId(CF);
		assertEquals(List.of("description", "versioning", "supporting", "includes", "relations"),
				framework.content().pages(framework).stream().map(PageSpec::id).toList());
		for (String element : List.of("Includes", "Relation", "Relationship", "Reference1", "Catalog", "Entry",
				"EffectiveDate", "RetiredDate", "Replaces", "IsReplacedBy", "SupportingInformation", "Link")) {
			assertNotNull(framework.fieldHelp(List.of(element)), element);
		}
		MedbiqDocumentType object = MedbiqStandards.byId(CO);
		for (String element : List.of("Status", "Category", "@term", "@scheme", "@label", "References", "Reference")) {
			assertNotNull(object.fieldHelp(List.of(element)), element);
		}
		MedbiqDocumentType performance = MedbiqStandards.byId(PF);
		for (String element : List.of("PerformanceScale", "LeastCompetent", "MostCompetent", "Component", "Title",
				"Competency", "Threshold", "MinimumAcceptableScore", "AdditionalInformation", "@position",
				"ComponentReference", "PerformanceLevelSet", "PerformanceScaleReference", "PerformanceLevel",
				"DisplayOrder", "Score", "SingleValue", "Range", "MinScore", "MaxScore", "Indicator", "Label")) {
			assertNotNull(performance.fieldHelp(List.of(element)), element);
		}
		assertTrue(performance.fieldHelp(List.of("AdditionalInformation", "Label")).contains("assessment method"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules shared by the three types
	// ----------------------------------------------------------------------------------------------------

	@Test
	void titleAndUriIdentifierAreRequired() {
		String framework = sample(FRAMEWORK);
		assertProblem(framework.replaceAll("(?s)<lom:title>.*?</lom:title>", ""), Severity.ERROR, "has no title");
		assertProblem(replace(framework, "<lom:catalog>URI</lom:catalog>", "<lom:catalog>local</lom:catalog>"),
				Severity.ERROR, "no identifier with the catalog URI");
		assertProblem(replace(framework, "https://gme.lakeside.example.org/competencies/core/2024</lom:entry>",
				"core 2024</lom:entry>"), Severity.ERROR, "is not an absolute URI");
		assertProblem(sample(OBJECT).replaceAll("(?s)<lom:title>.*?</lom:title>", ""), Severity.ERROR,
				"competency object has no title");
		assertProblem(sample(PERFORMANCE).replaceAll("(?s)<lom:title>.*?</lom:title>", ""), Severity.ERROR,
				"performance framework has no title");
		assertProblem(sample(PERFORMANCE).replaceAll("(?s)<lom:contribute>.*?</lom:contribute>", ""), Severity.INFO,
				"names no publisher");
	}

	@Test
	void datesAndVersions() {
		String framework = sample(FRAMEWORK);
		assertProblem(replace(framework, "<EffectiveDate>2024-07-01</EffectiveDate>",
				"<EffectiveDate>2024-07-01</EffectiveDate><RetiredDate>2020-06-30</RetiredDate>"), Severity.ERROR,
				"is after the retired date");
		assertProblem(replace(framework, "<EffectiveDate>2024-07-01</EffectiveDate>",
				"<EffectiveDate>2024-07-01</EffectiveDate><RetiredDate>2025-06-30</RetiredDate>"), Severity.WARNING,
				"names no replacement");
		assertProblem(replace(framework, "</Replaces>",
				"</Replaces><IsReplacedBy>https://gme.lakeside.example.org/competencies/core/2028</IsReplacedBy>"),
				Severity.INFO, "has no retired date");
		assertProblem(replace(framework, "https://gme.lakeside.example.org/competencies/core/2018",
				"https://gme.lakeside.example.org/competencies/core/2024"), Severity.ERROR, "names the competency "
						+ "framework itself");
		assertProblem(replace(framework, "https://gme.lakeside.example.org/competencies/core/2018", "core-2018"),
				Severity.WARNING, "is not an absolute URI");
		assertProblem(replace(framework, "<Link>https://gme.lakeside.example.org/competencies/core/2024/handbook.pdf",
				"<Link>handbook.pdf"), Severity.WARNING, "Link must contain a valid URI");
		assertProblem(replace(sample(PERFORMANCE), "<EffectiveDate>2024-07-01</EffectiveDate>",
				"<EffectiveDate>2024-07-01</EffectiveDate><RetiredDate>2024-01-01</RetiredDate>"), Severity.ERROR,
				"is after the retired date");
	}

	// ----------------------------------------------------------------------------------------------------
	// Competency Framework
	// ----------------------------------------------------------------------------------------------------

	private static final String INCLUDE_PC1 = """
			<Includes>
					<Catalog>URI</Catalog>
					<Entry>https://gme.lakeside.example.org/competencies/patient-care/pc1-history</Entry>
				</Includes>""";

	private static String relation(String first, String relationship, String second) {
		return "<Relation><Reference1><Catalog>URI</Catalog><Entry>https://gme.lakeside.example.org/competencies/"
				+ first + "</Entry></Reference1><Relationship>" + SKOS + relationship + "</Relationship><Reference2>"
				+ "<Catalog>URI</Catalog><Entry>https://gme.lakeside.example.org/competencies/" + second
				+ "</Entry></Reference2></Relation></CompetencyFramework>";
	}

	@Test
	void relationsReferenceIncludedCompetencies() {
		String framework = sample(FRAMEWORK);
		assertTrue(framework.contains(INCLUDE_PC1.strip()), "sample layout changed");
		assertProblem(framework.replace(INCLUDE_PC1.strip(), ""), Severity.ERROR, "does not include");
		assertProblem(replace(framework, "</CompetencyFramework>", relation("professionalism", "related",
				"unknown")), Severity.ERROR, "does not include");
	}

	@Test
	void includesAreUnique() {
		String framework = sample(FRAMEWORK);
		assertProblem(replace(framework, "<Relation>", INCLUDE_PC1.strip() + "<Relation>"), Severity.WARNING,
				"is included more than once");
		assertProblem(replace(framework, "<Entry>https://gme.lakeside.example.org/competencies/medical-knowledge",
				"<Entry>medical knowledge"), Severity.ERROR, "has the catalog URI but is not an absolute URI");
	}

	@Test
	void relationsHaveNoCyclesOrDuplicates() {
		String framework = sample(FRAMEWORK);
		assertProblem(replace(framework, "</CompetencyFramework>", relation("patient-care/pc1-history", "narrower",
				"patient-care")), Severity.ERROR, "part of a cycle");
		assertProblem(replace(framework, "</CompetencyFramework>", relation("professionalism", "related",
				"professionalism")), Severity.ERROR, "to itself");
		assertProblem(replace(framework, "</CompetencyFramework>", relation("patient-care/pc1-history", "broader",
				"patient-care")), Severity.WARNING, "is stated more than once");
	}

	// ----------------------------------------------------------------------------------------------------
	// Competency Object
	// ----------------------------------------------------------------------------------------------------

	@Test
	void statusOfCompetencyObjects() {
		String object = sample(OBJECT);
		assertProblem(replace(object, "<Status>Active</Status>", ""), Severity.WARNING, "has no Status");
		assertProblem(replace(object, "<Status>Active</Status>", "<Status>Retired</Status>"), Severity.WARNING,
				"Retired but names no replacement");
		assertProblem(replace(object, "<Status>Active</Status>", "<Status>Active</Status>"
				+ "<IsReplacedBy>https://gme.lakeside.example.org/competencies/patient-care-2030</IsReplacedBy>"),
				Severity.WARNING, "Active but is replaced");
	}

	@Test
	void categoriesOfCompetencyObjects() {
		String object = sample(OBJECT);
		assertProblem(replace(object, "<Category term=\"clinical\"", "<Category term=\"\""), Severity.ERROR,
				"empty term");
		assertProblem(replace(object, "scheme=\"https://gme.lakeside.example.org/categories/areas\"",
				"scheme=\"areas\""), Severity.WARNING, "is not an absolute URI");
		assertProblem(replace(object, "<References>", "<Category term=\"clinical\" "
				+ "scheme=\"https://gme.lakeside.example.org/categories/areas\"/><References>"), Severity.WARNING,
				"more than once");
	}

	// ----------------------------------------------------------------------------------------------------
	// Performance Framework
	// ----------------------------------------------------------------------------------------------------

	@Test
	void scalesAndScaleReferences() {
		String performance = sample(PERFORMANCE);
		assertProblem(replace(performance, "<MostCompetent>5</MostCompetent>", "<MostCompetent>1</MostCompetent>"),
				Severity.ERROR, "same score");
		assertProblem(replace(performance, "<PerformanceScaleReference>scale_percent</PerformanceScaleReference>",
				"<PerformanceScaleReference>comp_pc</PerformanceScaleReference>"), Severity.ERROR,
				"is not a performance scale");
	}

	@Test
	void nestedComponents() {
		String performance = sample(PERFORMANCE);
		assertProblem(replace(performance, "<ComponentReference>comp_pc2</ComponentReference>",
				"<ComponentReference>comp_pc</ComponentReference>"), Severity.ERROR, "references itself");
		assertProblem(replace(performance, "<ComponentReference>comp_pc2</ComponentReference>",
				"<ComponentReference>scale_milestones</ComponentReference>"), Severity.ERROR,
				"no component has that id");
	}

	@Test
	void performanceLevelsAreOrderedAndScored() {
		String performance = sample(PERFORMANCE);
		assertProblem(replace(performance, "<DisplayOrder>2</DisplayOrder>", "<DisplayOrder>1</DisplayOrder>"),
				Severity.ERROR, "have the display order 1");
		assertProblem(replace(performance, "<SingleValue>5</SingleValue>", "<SingleValue>6</SingleValue>"),
				Severity.ERROR, "is outside its performance scale");
		assertProblem(replace(performance, "<SingleValue>2</SingleValue>", "<SingleValue>4.5</SingleValue>"),
				Severity.WARNING, "do not follow their display order");
		assertProblem(replace(performance, "<SingleValue>2</SingleValue>", "<SingleValue>1</SingleValue>"),
				Severity.WARNING, "same or overlapping scores");
		assertProblem(replace(replace(performance, "<MinScore>0</MinScore>", "<MinScore>59</MinScore>"),
				"<MaxScore>59</MaxScore>", "<MaxScore>0</MaxScore>"), Severity.WARNING, "runs against the scale");
		assertProblem(replace(performance, "<MinimumAcceptableScore>4</MinimumAcceptableScore>",
				"<MinimumAcceptableScore>40</MinimumAcceptableScore>"), Severity.WARNING, "is outside the scale");
	}

	@Test
	void competencyReferences() {
		String performance = sample(PERFORMANCE);
		assertProblem(replace(performance, "<rdf:type rdf:resource=\"http://ns.medbiq.org/competencyframework/v1/\"/>",
				"<rdf:type rdf:resource=\"http://ns.medbiq.org/competencyobject/v1/\"/>"), Severity.WARNING,
				"do not name a competency object and a competency framework");
		assertProblem(replace(performance, "rdf:about=\"https://gme.lakeside.example.org/competencies/core/2024\"",
				"rdf:about=\"https://gme.lakeside.example.org/competencies/patient-care\""), Severity.WARNING,
				"Both competency references");
		assertProblem(replace(performance, "<rdf:Description rdf:about=\"https://gme.lakeside.example.org/"
				+ "competencies/patient-care\"/>", "<rdf:Description/>"), Severity.WARNING, "names no resource");
	}

	@Test
	void additionalInformation() {
		String performance = sample(PERFORMANCE);
		assertProblem(replace(performance, "<Label xml:lang=\"en\">note</Label>",
				"<Label xml:lang=\"en\">assessment method</Label>"), Severity.WARNING, "have the position 1");
		assertProblem(replace(performance, "<Label xml:lang=\"en\">note</Label>", "<Label xml:lang=\"en\">trivia"
				+ "</Label>"), Severity.INFO, "not one of the recommended values");
	}
}
