/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;

/**
 * Tests the MedBiquitous Virtual Patient 1.0 content: templates, samples, titles, pages and the rules of the
 * Virtual Patient Data, the Activity Model and the Data Availability Model.
 */
class VirtualPatientTest {

	private static final String VPD = "virtual-patient-data-v1";
	private static final String AM = "virtual-patient-activity-model-v1";
	private static final String DAM = "virtual-patient-data-availability-model-v1";
	private static final String FOLDER = "samples/virtual-patient/";
	private static final String VPD_FILE = "virtualpatientdata.xml";
	private static final String AM_FILE = "activitymodel.xml";
	private static final String DAM_FILE = "dataavailabilitymodel.xml";

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
		assertProblem(MedbiqSamples.check(text), severity, fragment);
	}

	private static void assertProblem(Result result, Severity severity, String fragment) {
		List<String> messages = rules(result, severity);
		assertTrue(messages.stream().anyMatch(m -> m.contains(fragment)),
				() -> "expected " + severity + " '" + fragment + "' in " + messages);
	}

	private static String replace(String text, String target, String replacement) {
		assertTrue(text.contains(target), () -> "sample lacks: " + target);
		return text.replaceFirst(Pattern.quote(target), Matcher.quoteReplacement(replacement));
	}

	// ----------------------------------------------------------------------------------------------------
	// Templates, samples, titles, pages
	// ----------------------------------------------------------------------------------------------------

	@Test
	void templatesAreValidAndTitled() {
		for (String type : List.of(VPD, AM, DAM)) {
			Result result = MedbiqSamples.assertValid(type, MedbiqSamples.template(type, "Chest pain & dyspnea"));
			assertEquals(type, result.type().id());
			assertTrue(rules(result, Severity.WARNING).isEmpty(), () -> type + ": " + rules(result, Severity.WARNING));
			String title = title(result);
			if (type.equals(DAM)) {
				assertEquals("Data availability model (2 DAM nodes)", title);
			} else {
				assertEquals("Chest pain & dyspnea", title);
			}
		}
	}

	@Test
	void samplesAreValidWithoutRuleProblems() {
		List<String> files = MedbiqSamples.files("samples/virtual-patient");
		assertEquals(3, files.size(), () -> files.toString());
		for (String file : files) {
			Result result = MedbiqSamples.assertValid(file, MedbiqSamples.text(file));
			for (Severity severity : Severity.values()) {
				assertTrue(rules(result, severity).isEmpty(), () -> file + ": " + rules(result, severity));
			}
		}
	}

	@Test
	void samplesFormAConsistentPackage(@TempDir Path folder) throws IOException {
		Map<String, Result> results = checkPackage(folder, Map.of());
		for (var entry : results.entrySet()) {
			MedbiqSamples.assertValid(entry.getKey(), sample(entry.getKey()));
			for (Severity severity : Severity.values()) {
				assertTrue(rules(entry.getValue(), severity).isEmpty(),
						() -> entry.getKey() + ": " + rules(entry.getValue(), severity));
			}
		}
	}

	@Test
	void titlesAndDescriptions() {
		Result vpd = MedbiqSamples.check(sample(VPD_FILE));
		assertEquals("Thomas Arden", title(vpd));
		String description = vpd.type().description(vpd.resource().getContents().getFirst());
		assertTrue(description.startsWith("Male, 58 years;"), description);
		assertTrue(description.contains("6 interview items"), description);
		Result am = MedbiqSamples.check(sample(AM_FILE));
		assertEquals("Chest pain in a 58-year-old man", title(am));
		assertEquals("8 activity nodes in 2 node sections, 9 links, 2 counters",
				am.type().description(am.resource().getContents().getFirst()));
		Result dam = MedbiqSamples.check(sample(DAM_FILE));
		assertEquals("Data availability model (10 DAM nodes)", title(dam));
		// Without a name, the patient ID; without demographics, a summary.
		Result id = MedbiqSamples.check(replace(sample(VPD_FILE), "<Name>Thomas Arden</Name>", ""));
		assertEquals("VP-2026-058", title(id));
	}

	@Test
	void pagesSearchFieldsAndHelp() {
		for (String id : List.of(VPD, AM, DAM)) {
			var type = MedbiqStandards.byId(id);
			List<PageSpec> pages = type.content().pages(type);
			assertFalse(pages.isEmpty(), id);
			assertEquals(pages.size(), pages.stream().map(PageSpec::id).distinct().count(), id);
			String prefix = switch (id) {
				case VPD -> "vp-";
				case AM -> "am-";
				default -> "dam-";
			};
			assertFalse(type.searchFields().isEmpty(), id);
			assertTrue(type.searchFields().stream().allMatch(g -> g.id().startsWith(prefix)), id);
			assertNotEquals(null, type.fieldHelp(List.of("XtensibleInfo")), id);
		}
		assertTrue(MedbiqStandards.byId(VPD).fieldHelp(List.of("Age")).contains("P15Y6M"));
		assertTrue(MedbiqStandards.byId(AM).fieldHelp(List.of("Weighting")).contains("100"));
		assertTrue(MedbiqStandards.byId(DAM).fieldHelp(List.of("ItemPath")).contains("/VirtualPatientData"));
	}

	// ----------------------------------------------------------------------------------------------------
	// Virtual Patient Data rules
	// ----------------------------------------------------------------------------------------------------

	@Test
	void vpdDuplicateIds() {
		String vpd = sample(VPD_FILE);
		assertProblem(replace(vpd, "<InterviewItem id=\"i2\">", "<InterviewItem id=\"i1\">"), Severity.ERROR,
				"Two InterviewItem elements have the id 'i1'");
		assertProblem(replace(vpd, "<PhysicalExam id=\"pe1\">", "<PhysicalExam id=\"t1\">"), Severity.WARNING,
				"is also used by");
	}

	@Test
	void vpdOrganizationItemsReferToIds() {
		assertProblem(replace(sample(VPD_FILE), "idRef=\"i6\"", "idRef=\"i60\""), Severity.ERROR,
				"refers to the id 'i60'");
	}

	@Test
	void vpdBreedNeedsSpecies() {
		assertProblem(replace(sample(VPD_FILE), "<Sex>Male</Sex>", "<Sex>Male</Sex><Breed>Beagle</Breed>"),
				Severity.WARNING, "Breed but no Species");
	}

	@Test
	void vpdAuthorDiagnosis() {
		assertProblem(replace(sample(VPD_FILE), "authorDiagnosis=\"true\"", "authorDiagnosis=\"false\""),
				Severity.INFO, "author's final diagnosis");
	}

	@Test
	void vpdTextMarkup() {
		String vpd = sample(VPD_FILE);
		String external = "<img src=\"https://example.org/p.png\" alt=\"x\"/>";
		assertProblem(replace(vpd, "<p>\"It started", external + "<p>\"It started"), Severity.ERROR,
				"is not in the package");
		assertProblem(replace(vpd, "<p>\"It started", "<script type=\"text/javascript\">go()</script><p>\"It started"),
				Severity.ERROR, "script element");
		assertProblem(replace(vpd, "<p>\"It started", "<p onclick=\"go()\">\"It started"), Severity.ERROR,
				"event handler onclick");
		// A local image is fine.
		Result local = MedbiqSamples.check(
				replace(vpd, "<p>\"It started", "<img src=\"media/arden.jpg\" alt=\"x\"/><p>\"It started"));
		assertTrue(rules(local, Severity.ERROR).isEmpty(), () -> rules(local, Severity.ERROR).toString());
	}

	// ----------------------------------------------------------------------------------------------------
	// Activity Model rules
	// ----------------------------------------------------------------------------------------------------

	@Test
	void amDanglingReferences() {
		String am = sample(AM_FILE);
		assertProblem(replace(am, "[@id='n-pci']</ActivityNodeB>", "[@id='n-pcx']</ActivityNodeB>"), Severity.ERROR,
				"leads to the activity node 'n-pcx'");
		assertProblem(replace(am, "[@id='n-discharge']</RuleRedirect>", "[@id='n-gone']</RuleRedirect>"),
				Severity.ERROR, "the activity node 'n-gone'");
		assertProblem(replace(am, "[@id='c-cost']</CounterPath>", "[@id='c-costs']</CounterPath>"), Severity.ERROR,
				"counter 'c-costs'");
		assertProblem(replace(am, "ActivityNode[@id='n-tests']</Operand>", "ActivityNode[@id='n-x']</Operand>"),
				Severity.ERROR, "the activity node 'n-x'");
	}

	@Test
	void amDuplicateIds() {
		assertProblem(replace(sample(AM_FILE), "<ActivityNode id=\"n-pci\"", "<ActivityNode id=\"n-ddx\""),
				Severity.ERROR, "Two activity nodes have the id 'n-ddx'");
		assertProblem(replace(sample(AM_FILE), "<NodeSection id=\"s-management\"", "<NodeSection id=\"s-assessment\""),
				Severity.ERROR, "Two node sections have the id 's-assessment'");
	}

	@Test
	void amEveryNodeReachable() {
		String island = "<ActivityNode id=\"n-island\" label=\"Island\"><Content>"
				+ "/DataAvailabilityModel/DAMNode[@id='dam-pci']</Content></ActivityNode>";
		String am = replace(sample(AM_FILE), "<ActivityNode id=\"n-pci\"", island + "<ActivityNode id=\"n-pci\"");
		assertProblem(am, Severity.WARNING, "'Island' (n-island) cannot be reached");
		// NavigateGlobal makes a node reachable.
		String global = replace(am, "</Content></ActivityNode>",
				"</Content><Rules><NavigateGlobal>on</NavigateGlobal></Rules></ActivityNode>");
		assertTrue(rules(MedbiqSamples.check(global), Severity.WARNING).isEmpty());
	}

	@Test
	void amWeightings() {
		String am = sample(AM_FILE);
		assertProblem(replace(am, "[@id='n-pci']</ActivityNodeB>",
				"[@id='n-pci']</ActivityNodeB><Weighting>60</Weighting>"), Severity.WARNING, "add up to 60");
		assertProblem(replace(am, "[@id='dam-intro']</Content>",
				"[@id='dam-intro']</Content><Rules><Probability>on</Probability></Rules>"), Severity.WARNING,
				"has Probability on");
	}

	@Test
	void amConditionOperands() {
		String am = sample(AM_FILE);
		assertProblem(replace(am, "/VirtualPatientData/DiagnosticTest[@id='t1']", "/VirtualPatientData/Test[@id='t1']"),
				Severity.ERROR, "names 'Test'");
		assertProblem(replace(am, "/VirtualPatientData/DiagnosticTest[@id='t1']", "ecg"), Severity.WARNING,
				"is neither an activity node");
	}

	@Test
	void amLinkLabels() {
		assertProblem(replace(sample(AM_FILE), "<Link label=\"Start the case again\">", "<Link>"), Severity.INFO,
				"has no label");
	}

	// ----------------------------------------------------------------------------------------------------
	// Data Availability Model rules
	// ----------------------------------------------------------------------------------------------------

	@Test
	void damReferencesAndIds() {
		String dam = sample(DAM_FILE);
		assertProblem(replace(dam, "<DAMNode id=\"dam-pci\">", "<DAMNode id=\"dam-discharge\">"), Severity.ERROR,
				"Two DAM nodes have the id 'dam-discharge'");
		assertProblem(replace(dam, "[@id='dam-ecg-feedback']</ItemComment>", "[@id='dam-x']</ItemComment>"),
				Severity.ERROR, "refers to DAMNode 'dam-x'");
	}

	@Test
	void damNoCyclicInclusions() {
		assertProblem(replace(sample(DAM_FILE), "<ItemPath>/VirtualPatientData/Medication[@id='m1']</ItemPath>",
				"<ItemPath>/VirtualPatientData/Medication[@id='m1']</ItemPath>"
						+ "<DAMNodePath>/DataAvailabilityModel/DAMNode[@id='dam-history']</DAMNodePath>"),
				Severity.ERROR, "includes itself");
	}

	@Test
	void damItemPathsUseVpdNames() {
		String dam = sample(DAM_FILE);
		assertProblem(replace(dam, "/VirtualPatientData/PhysicalExam[@id='pe1']",
				"/VirtualPatientData/Exam[@id='pe1']"), Severity.ERROR, "names 'Exam'");
		assertProblem(replace(dam, "CoreDemographics/Name/text()", "CoreDemographics/FullName/text()"), Severity.ERROR,
				"names 'FullName'");
	}

	@Test
	void damAlternativePathAndOrder() {
		String dam = sample(DAM_FILE);
		assertProblem(replace(dam, "<ItemPath>/VirtualPatientData/DiagnosticTest[@id='t2']</ItemPath>",
				"<ItemPath>/VirtualPatientData/DiagnosticTest[@id='t2']</ItemPath>"
						+ "<AlternativePath>/VirtualPatientData/VPDText[@id='v-ddx']</AlternativePath>"),
				Severity.WARNING, "has an AlternativePath");
		String reordered = dam.replaceFirst("(PhysicalExam\\[@id='pe4'\\]</ItemPath>\\s*<ItemOrder>)4", "$13");
		assertNotEquals(dam, reordered);
		assertProblem(reordered, Severity.INFO, "have the ItemOrder 3");
	}

	// ----------------------------------------------------------------------------------------------------
	// Checks across the package
	// ----------------------------------------------------------------------------------------------------

	@Test
	void packageReferences(@TempDir Path folder) throws IOException {
		Map<String, Result> results = checkPackage(folder,
				Map.of(AM_FILE, Map.of("[@id='dam-pci']</Content>", "[@id='dam-pcx']</Content>"), DAM_FILE,
						Map.of("InterviewItem[@id='i6']", "InterviewItem[@id='i66']")));
		assertProblem(results.get(AM_FILE), Severity.WARNING, "refers to DAMNode 'dam-pcx'");
		assertProblem(results.get(DAM_FILE), Severity.WARNING, "selects nothing in the Virtual Patient Data");
		// Without the package's other documents the references are not checked.
		assertTrue(rules(MedbiqSamples.check(replace(sample(AM_FILE), "[@id='dam-pci']</Content>",
				"[@id='dam-pcx']</Content>")), Severity.WARNING).isEmpty());
	}

	/** Writes the three sample documents (with replacements) to a folder and checks each with its file URI. */
	private static Map<String, Result> checkPackage(Path folder, Map<String, Map<String, String>> changes)
			throws IOException {
		Map<String, String> texts = new LinkedHashMap<>();
		for (String file : List.of(VPD_FILE, AM_FILE, DAM_FILE)) {
			String text = sample(file);
			for (var change : changes.getOrDefault(file, Map.of()).entrySet()) {
				text = replace(text, change.getKey(), change.getValue());
			}
			texts.put(file, text);
			Files.writeString(folder.resolve(file), text, StandardCharsets.UTF_8);
		}
		Map<String, Result> results = new LinkedHashMap<>();
		for (var entry : texts.entrySet()) {
			URI uri = URI.createFileURI(folder.resolve(entry.getKey()).toAbsolutePath().toString());
			results.put(entry.getKey(), DocumentCheck.check(entry.getValue(), uri));
		}
		return results;
	}
}
