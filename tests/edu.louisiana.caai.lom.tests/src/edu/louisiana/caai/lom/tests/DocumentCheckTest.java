/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.check.TextPositions;
import edu.louisiana.caai.lom.model.support.check.TextPositions.Range;
import edu.louisiana.caai.lom.model.support.check.XmlOutline;

/** Tests the checks of document text with positions ({@link DocumentCheck}, {@link TextPositions}). */
class DocumentCheckTest {

	private static final URI URI_XML = URI.createFileURI("/tmp/test.xml");

	private static String sample(String name) throws Exception {
		return Files.readString(Path.of(TestResources.sample(name).toFileString()), StandardCharsets.UTF_8);
	}

	/** The 0-based line of the first occurrence of a text. */
	private static int lineOf(String text, String needle) {
		int offset = text.indexOf(needle);
		assertTrue(offset >= 0, needle);
		return (int) text.substring(0, offset).chars().filter(c -> c == '\n').count();
	}

	private static List<Located> errors(Result result) {
		return result.problems().stream().filter(p -> p.problem().severity() == Severity.ERROR).toList();
	}

	@Test
	void validSamplesHaveNoErrors() throws Exception {
		for (String name : TestResources.SAMPLES) {
			Result result = DocumentCheck.check(sample(name), URI_XML);
			assertTrue(errors(result).isEmpty(), () -> name + ": " + errors(result));
			assertNotNull(result.root());
		}
	}

	@Test
	void schemaProblemsAreAtTheirPositionInTheText() throws Exception {
		String text = sample("insulin-pump-therapy-course.xml").replace("<value>final</value>", "<value>finished</value>");
		Result result = DocumentCheck.check(text, URI_XML);
		Located problem = errors(result).stream().filter(p -> p.problem().origin() == Origin.SCHEMA).findFirst()
				.orElseThrow(() -> new AssertionError(result.problems()));
		assertEquals(lineOf(text, "<value>finished</value>"), problem.range().startLine());
		assertEquals("/lom/lifeCycle/status/value", problem.path());
	}

	@Test
	void ruleProblemsArePlacedAtTheirElement() throws Exception {
		String text = sample("insulin-pump-therapy-course.xml")
				.replace("<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>", "");
		Result result = DocumentCheck.check(text, URI_XML);
		Located problem = result.problems().stream().filter(p -> p.problem().origin() == Origin.RULES
				&& p.problem().message().contains("HEALTHCARE_LOMv1")).findFirst()
				.orElseThrow(() -> new AssertionError(result.problems()));
		assertEquals("/lom/metaMetadata", problem.path());
		assertEquals(lineOf(text, "<metaMetadata>"), problem.range().startLine());
	}

	@Test
	void malformedXmlIsReportedOnce() throws Exception {
		String text = sample("insulin-pump-therapy-course.xml").replace("</general>", "</generalx>");
		Result result = DocumentCheck.check(text, URI_XML);
		assertFalse(errors(result).isEmpty());
		assertTrue(errors(result).stream().allMatch(p -> p.range() != null), result.problems()::toString);
	}

	@Test
	void jsonProblemsAreAtTheirMember() throws Exception {
		var resource = HlomResources.load(TestResources.sample("insulin-pump-therapy-course.xml"));
		String json = HlomResources.toJson(resource);
		assertTrue(errors(DocumentCheck.check(json, URI.createFileURI("/tmp/test.json"))).isEmpty());
		String broken = json.replace("\"HEALTHCARE_LOMv1\"", "\"OTHER\"");
		Result result = DocumentCheck.check(broken, URI.createFileURI("/tmp/test.json"));
		Located problem = result.problems().stream().filter(p -> p.problem().message().contains("HEALTHCARE_LOMv1"))
				.findFirst().orElseThrow(() -> new AssertionError(result.problems()));
		assertNotNull(problem.range());
		assertEquals(lineOf(broken, "\"metaMetadata\""), problem.range().startLine());
	}

	@Test
	void documentsAreRecognized() throws Exception {
		assertTrue(DocumentCheck.isDocument(sample("insulin-pump-therapy-course.xml")));
		assertTrue(DocumentCheck.isDocument("{\n  \"lom\": {}\n}"));
		assertFalse(DocumentCheck.isDocument("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"/>"));
		assertFalse(DocumentCheck.isDocument("{\"name\": \"package\"}"));
		Result other = DocumentCheck.check("<html/>", URI_XML);
		assertNull(other.root());
		assertFalse(errors(other).isEmpty());
	}

	@Test
	void outlineAndPositions() {
		String xml = """
				<lom xmlns="http://ltsc.ieee.org/xsd/LOM" xmlns:hx="x">
				  <!-- <general> in a comment -->
				  <general>
				    <keyword hx:id="D1"><string>a</string></keyword>
				    <keyword hx:id="D2"><string>b</string></keyword>
				  </general>
				</lom>
				""";
		XmlOutline outline = XmlOutline.of(xml);
		assertEquals(1, outline.roots().size());
		XmlOutline.Element second = outline.element("/lom/general/keyword[2]");
		assertNotNull(second);
		assertEquals("keyword", second.localName());
		assertEquals(second, outline.elementAt(xml.indexOf("D2")));
		assertEquals("id", outline.attributeAt(second, xml.indexOf("hx:id=\"D2\"") + 1));
		TextPositions positions = TextPositions.of(xml);
		Range keyword = positions.find("/lom/general/keyword[2]/@hx:id");
		assertEquals(4, keyword.startLine());
		assertEquals(xml.split("\n")[4].indexOf("hx:id"), keyword.startColumn());
		// A path that only partly exists is placed at its deepest existing element.
		assertEquals(2, positions.find("/lom/general/nothing").startLine());
		// Unclosed elements end at the end of the text.
		assertEquals(1, XmlOutline.of("<lom><general>").roots().size());
	}
}
