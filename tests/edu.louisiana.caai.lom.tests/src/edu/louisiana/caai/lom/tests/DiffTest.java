/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.compare.ChangeKind;
import edu.louisiana.caai.lom.model.support.compare.DiffCategory;
import edu.louisiana.caai.lom.model.support.compare.DiffResult;
import edu.louisiana.caai.lom.model.support.compare.Difference;
import edu.louisiana.caai.lom.model.support.compare.HlomDiff;

/**
 * Tests the semantic comparison of HLOM documents ({@link HlomDiff}).
 */
class DiffTest {

	private static final String INSULIN = "insulin-pump-therapy-course.xml";
	private static final String CREDITS = "course-with-multiple-credit-types.xml";

	private static final String KEYWORD_INFUSION = """
			<keyword hx:source="MeSH" hx:id="D007332">
			         <string language="en">insulin infusion systems</string>
			      </keyword>""";
	private static final String KEYWORD_DIABETES = """
			<keyword hx:source="MeSH" hx:id="D003920">
			         <string language="en">diabetes mellitus</string>
			      </keyword>""";

	private static String xml(String sample) throws IOException {
		Path path = Path.of(TestResources.sample(sample).toFileString());
		return Files.readString(path, StandardCharsets.UTF_8).replace("\r\n", "\n");
	}

	private static DocumentRoot parse(String xml) throws IOException {
		return HlomResources.getDocumentRoot(HlomResources.parse(xml, URI.createURI("memory:/test.xml")));
	}

	private static DiffResult compare(String left, String right) throws IOException {
		return HlomDiff.compare(parse(left), parse(right));
	}

	private static String replace(String xml, String target, String replacement) {
		assertTrue(xml.contains(target), "sample must contain " + target);
		return xml.replace(target, replacement);
	}

	private static Difference find(DiffResult result, String label) {
		return result.find(label).orElseThrow(() -> new AssertionError("no field " + label + " in" + describe(result)));
	}

	private static String describe(DiffResult result) {
		return result.differences().stream().map(Difference::toString).collect(Collectors.joining("\n", "\n", ""));
	}

	@Test
	void identicalDocumentsHaveNoDifferences() throws IOException {
		for (String sample : TestResources.SAMPLES) {
			DiffResult result = compare(xml(sample), xml(sample));
			assertTrue(result.isIdentical(), sample + describe(result));
			assertTrue(result.differences().isEmpty());
			assertFalse(result.all().isEmpty(), "unchanged fields are reported for 'show unchanged'");
			assertEquals(0, result.count(ChangeKind.CHANGED) + result.count(ChangeKind.ADDED)
					+ result.count(ChangeKind.REMOVED));
		}
	}

	@Test
	void unchangedFieldsHaveLabelsAndPaths() throws IOException {
		DiffResult result = compare(xml(INSULIN), xml(INSULIN));
		Difference title = find(result, "General › Title (en)");
		assertEquals(ChangeKind.UNCHANGED, title.kind());
		assertEquals(DiffCategory.GENERAL, title.category());
		assertEquals("Title (en)", title.field());
		assertEquals("/lom/general/title/string[@language='en']", title.path());
		assertEquals("Insulin Pump Therapy", title.leftValue());
		Difference credits = find(result, "Healthcare Education › Credits › Number of credits");
		assertEquals(DiffCategory.HEALTHCARE_EDUCATION, credits.category());
		assertEquals("/lom/hx:healthcareMetadata/hx:healthcareEducation/hx:credits/hx:numberOfCredits", credits.path());
		assertEquals("1.5", credits.leftValue());
		Difference contributor = find(result, "Life Cycle › Contributor 2 › Entity");
		assertEquals("American Academy of Family Physicians", contributor.leftValue());
		assertEquals("final", find(result, "Life Cycle › Status").leftValue());
		// Reformatted whitespace is not a change
		assertEquals("American Academy of Family Physicians",
				find(result, "Healthcare Education › Credits › Accredited provider").leftValue());
	}

	@Test
	void detectsChangedTitle() throws IOException {
		String left = xml(INSULIN);
		String right = replace(left, ">Insulin Pump Therapy<", ">Insulin Pump Therapy for Clinicians<");
		DiffResult result = compare(left, right);
		assertEquals(1, result.differences().size(), describe(result));
		Difference title = result.differences().getFirst();
		assertEquals("General › Title (en)", title.label());
		assertEquals(ChangeKind.CHANGED, title.kind());
		assertEquals("Insulin Pump Therapy", title.leftValue());
		assertEquals("Insulin Pump Therapy for Clinicians", title.rightValue());
		assertNotNull(title.left());
		assertNotNull(title.right());
		assertEquals(1, result.count(ChangeKind.CHANGED));
	}

	@Test
	void detectsAddedKeyword() throws IOException {
		String left = xml(INSULIN);
		String right = replace(left, "</general>", """
				   <keyword hx:source="MeSH" hx:id="D007328">
				         <string language="en">insulin</string>
				      </keyword>
				   </general>""");
		DiffResult result = compare(left, right);
		assertEquals(1, result.differences().size(), describe(result));
		Difference keyword = result.differences().getFirst();
		assertEquals("General › Keyword 3", keyword.label());
		assertEquals(ChangeKind.ADDED, keyword.kind());
		assertNull(keyword.leftValue());
		assertEquals("insulin (MeSH D007328)", keyword.rightValue());
		assertTrue(keyword.right() instanceof Keyword);
		assertNull(keyword.left());
		assertEquals(1, result.count(ChangeKind.ADDED));
		// the fields of the added keyword
		Set<String> children = keyword.children().stream().map(Difference::label).collect(Collectors.toSet());
		assertTrue(children.contains("General › Keyword 3 (en)"), children.toString());
		assertTrue(children.contains("General › Keyword 3 › Term ID"), children.toString());
	}

	@Test
	void detectsRemovedCredit() throws IOException {
		String left = xml(CREDITS);
		int start = left.indexOf("<hx:credits>", left.indexOf("<hx:credits>") + 1);
		int end = left.indexOf("</hx:credits>", start) + "</hx:credits>".length();
		String right = left.substring(0, start) + left.substring(end);
		DiffResult result = compare(left, right);
		assertEquals(1, result.differences().size(), describe(result));
		Difference credit = result.differences().getFirst();
		assertEquals("Healthcare Education › Credits 2", credit.label());
		assertEquals(DiffCategory.HEALTHCARE_EDUCATION, credit.category());
		assertEquals(ChangeKind.REMOVED, credit.kind());
		assertEquals("1.7 CNE Contact Hour · ANCC", credit.leftValue());
		assertNull(credit.rightValue());
		Difference number = find(result, "Healthcare Education › Credits 2 › Number of credits");
		assertEquals(ChangeKind.REMOVED, number.kind());
		assertEquals("1.7", number.leftValue());
		assertEquals(1, result.count(ChangeKind.REMOVED));
	}

	@Test
	void detectsChangedVocabularyValue() throws IOException {
		String left = xml(INSULIN);
		String right = replace(left, "<value>final</value>", "<value>draft</value>");
		DiffResult result = compare(left, right);
		assertEquals(1, result.differences().size(), describe(result));
		Difference status = result.differences().getFirst();
		assertEquals("Life Cycle › Status", status.label());
		assertEquals(DiffCategory.LIFE_CYCLE, status.category());
		assertEquals(ChangeKind.CHANGED, status.kind());
		assertEquals("final", status.leftValue());
		assertEquals("draft", status.rightValue());
	}

	@Test
	void detectsChangedContributorRoleAndCredits() throws IOException {
		String left = xml(CREDITS);
		String right = replace(left, "<value>publisher</value>", "<value>editor</value>");
		right = replace(right, "<hx:numberOfCredits>1.5</hx:numberOfCredits>", "<hx:numberOfCredits>2.0</hx:numberOfCredits>");
		right = replace(right, "<hx:commercialSupport>no</hx:commercialSupport>",
				"<hx:commercialSupport>yes</hx:commercialSupport>");
		DiffResult result = compare(left, right);
		assertEquals(3, result.differences().size(), describe(result));
		Difference role = find(result, "Life Cycle › Contributor 2 › Role");
		assertEquals(ChangeKind.CHANGED, role.kind());
		assertEquals("publisher", role.leftValue());
		assertEquals("editor", role.rightValue());
		Difference credits = find(result, "Healthcare Education › Credits 1 › Number of credits");
		assertEquals("1.5", credits.leftValue());
		assertEquals("2", credits.rightValue());
		assertEquals("yes", find(result, "Healthcare Education › Commercial support").rightValue());
	}

	@Test
	void reorderedKeywordsAreNotChanges() throws IOException {
		String left = xml(INSULIN);
		String right = replace(left, KEYWORD_INFUSION + "\n      " + KEYWORD_DIABETES,
				KEYWORD_DIABETES + "\n      " + KEYWORD_INFUSION);
		assertFalse(left.equals(right));
		DiffResult result = compare(left, right);
		assertTrue(result.isIdentical(), describe(result));
	}

	@Test
	void ignoresNamespaceDeclarationsAndSchemaLocation() throws IOException {
		String left = xml(INSULIN);
		String right = replace(left, "xsi:schemaLocation=\"http://ltsc.ieee.org/xsd/LOM https://ns.medbiq.org/lom/healthcarelom.xsd\"",
				"xmlns:extra=\"urn:example\" xsi:schemaLocation=\"http://ltsc.ieee.org/xsd/LOM lom.xsd\"");
		DiffResult result = compare(left, right);
		assertTrue(result.isIdentical(), describe(result));
	}

	@Test
	void comparesDifferentRecordsByCategory() throws IOException {
		DiffResult result = compare(xml(INSULIN), xml(CREDITS));
		Map<DiffCategory, List<Difference>> categories = result.byCategory(false);
		assertTrue(categories.containsKey(DiffCategory.GENERAL));
		assertTrue(categories.containsKey(DiffCategory.HEALTHCARE_EDUCATION));
		assertEquals("Arsenic Toxicity", find(result, "General › Title (en)").rightValue());
		// The insulin course has one CME credit, the arsenic course a CME and a CNE credit
		assertEquals(ChangeKind.ADDED, find(result, "Healthcare Education › Credits 2").kind());
		assertEquals(ChangeKind.CHANGED,
				find(result, "Healthcare Education › Credits 1 › Activity certification").kind());
		// Language added in the right document
		assertEquals(ChangeKind.ADDED, find(result, "General › Language").kind());
		// Keywords with different MeSH ids are different keywords
		assertEquals(2, result.differences().stream()
				.filter(d -> d.label().startsWith("General › Keyword") && d.kind() == ChangeKind.ADDED).count());
		String text = result.toText("insulin", "arsenic");
		assertTrue(text.contains("Healthcare Education"), text);
		assertTrue(result.count(ChangeKind.CHANGED) > 0);
	}
}
