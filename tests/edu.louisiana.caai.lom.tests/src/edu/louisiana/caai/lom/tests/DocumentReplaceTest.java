/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.search.DocumentReplace;
import edu.louisiana.caai.lom.model.support.search.DocumentReplace.Result;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.HlomSearchFields;

/** Tests replacing search matches in documents ({@link DocumentReplace}). */
class DocumentReplaceTest {

	private static final URI URI_XML = URI.createURI("replace.xml");

	private static String insulin() throws Exception {
		return Files.readString(Path.of(TestResources.sample("insulin-pump-therapy-course.xml").toFileString()),
				StandardCharsets.UTF_8);
	}

	@Test
	void onlyTheChangedValuesOfXmlAreRewritten() throws Exception {
		String xml = insulin();
		Result result = DocumentReplace.replace(xml, URI_XML, Query.of("insulin"), "Insulin & Co", path -> true);
		assertFalse(result.changes().isEmpty());
		assertEquals(List.of(), result.skipped());
		// Every other line is as it was: the layout and the comments are kept.
		List<String> before = xml.lines().toList();
		List<String> after = result.text().lines().toList();
		assertEquals(before.size(), after.size());
		for (int i = 0; i < before.size(); i++) {
			if (!before.get(i).equals(after.get(i))) {
				assertTrue(before.get(i).toLowerCase().contains("insulin"), "line " + (i + 1) + " changed: " + after.get(i));
			}
		}
		assertTrue(result.text().contains("<string language=\"en\">Insulin &amp; Co Pump Therapy</string>"));
		assertTrue(DocumentCheck.check(result.text(), URI_XML).count(
				edu.louisiana.caai.lom.model.support.HlomProblem.Severity.ERROR) == 0, "still valid");
	}

	@Test
	void replacementIsLimitedToFieldsAndSelectedValues() throws Exception {
		String xml = insulin();
		Query titles = new Query("insulin", false, false, false, List.of(HlomSearchFields.TITLES));
		Result result = DocumentReplace.replace(xml, URI_XML, titles, "INSULIN", path -> true);
		assertEquals(1, result.changes().size(), () -> result.changes().toString());
		assertEquals("INSULIN Pump Therapy", result.changes().getFirst().after());
		Result none = DocumentReplace.replace(xml, URI_XML, Query.of("insulin"), "x", path -> false);
		assertTrue(none.changes().isEmpty());
		assertEquals(xml, none.text());
	}

	@Test
	void regularExpressionsReplaceWithGroupsAndAttributesToo() throws Exception {
		String xml = insulin();
		Query languages = new Query("^en$", true, true, false, List.of());
		Result result = DocumentReplace.replace(xml, URI_XML, languages, "en-US", path -> true);
		assertTrue(result.changes().stream().anyMatch(change -> change.path().endsWith("/@language")));
		assertFalse(result.text().contains("language=\"en\""));
		Query swap = new Query("(Pump) (Therapy)", true, true, false, List.of());
		Result swapped = DocumentReplace.replace(xml, URI_XML, swap, "$2 with a $1", path -> true);
		assertTrue(swapped.text().contains("Insulin Therapy with a Pump"));
	}

	@Test
	void invalidValuesAreSkipped() throws Exception {
		String xml = insulin();
		// The release and expiration dates are xsd:date values: "2006-04-xx" isn't one.
		Result result = DocumentReplace.replace(xml, URI_XML, new Query("-30$", true, true, false, List.of()), "-xx",
				path -> true);
		assertTrue(result.changes().stream().noneMatch(change -> change.after().endsWith("-xx")),
				() -> result.changes().toString());
		assertFalse(result.skipped().isEmpty());
		assertEquals(xml, result.text());
	}

	@Test
	void jsonDocumentsAreWrittenInTheirCanonicalForm() throws Exception {
		String json = HlomResources.toJson(DocumentCheck.load(insulin(), URI_XML));
		Result result = DocumentReplace.replace(json, URI.createURI("replace.json"), Query.of("Pump Therapy"),
				"Pump Course", path -> true);
		assertFalse(result.changes().isEmpty());
		assertTrue(result.text().contains("Insulin Pump Course"));
		assertTrue(result.text().stripLeading().startsWith("{"));
	}
}
