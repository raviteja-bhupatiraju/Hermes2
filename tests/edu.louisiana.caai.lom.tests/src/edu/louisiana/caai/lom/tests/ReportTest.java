/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Kind;
import edu.louisiana.caai.lom.model.support.report.HtmlReport;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Tests the record summaries, the library scanner and the HTML reports.
 */
class ReportTest {

	private static HlomSummary summary(String sample) throws IOException {
		return HlomSummary.of(HlomResources.load(TestResources.sample(sample)));
	}

	@Test
	void summarizesCourse() throws IOException {
		HlomSummary summary = summary("insulin-pump-therapy-course.xml");
		assertEquals("Insulin Pump Therapy", summary.title());
		assertEquals(Kind.EDUCATIONAL_OFFERING, summary.kind());
		assertTrue(summary.description().startsWith("Provides a general introduction"));
		assertEquals(2, summary.keywords().size());
		assertEquals("D007332", summary.keywords().getFirst().id());
		assertTrue(summary.keywords().getFirst().isMesh());
		assertEquals(List.of("John Doe", "American Academy of Family Physicians"), summary.contributorNames());
		assertEquals("author", summary.contributors().getFirst().role());
		assertEquals("1.0", summary.version());
		assertEquals("final", summary.status());
		assertEquals(List.of("continuing professional development"), summary.contexts());
		assertEquals(1, summary.credits().size());
		assertEquals("1.5 CME Credit", summary.credits().getFirst().label());
		assertEquals("ACCME", summary.credits().getFirst().accreditingBody());
		assertEquals(List.of("physician"), summary.professions());
		assertEquals(List.of("family practice"), summary.specialties());
		assertEquals("physician (family practice)", summary.audienceLabel());
		assertEquals("2006-04-30", summary.releaseDate());
		assertEquals("2007-04-30", summary.expirationDate());
		assertEquals(List.of("application/http"), summary.formats());
		assertEquals(2, summary.objectives().size());
		assertEquals("yes", summary.cost());
	}

	@Test
	void summarizesMultipleCredits() throws IOException {
		HlomSummary summary = summary("course-with-multiple-credit-types.xml");
		assertEquals("Arsenic Toxicity", summary.title());
		assertEquals(2, summary.credits().size());
		Map<String, BigDecimal> totals = summary.creditTotals();
		assertEquals(0, new BigDecimal("1.5").compareTo(totals.get("CME Credit")));
		assertEquals(0, new BigDecimal("1.7").compareTo(totals.get("CNE Contact Hour")));
		assertEquals(List.of("physician", "registered nurse"), summary.professions());
		assertTrue(summary.locations().getFirst().startsWith("http://www.atsdr.cdc.gov/"));
		assertEquals(List.of("en"), summary.languages());
	}

	@Test
	void summarizesImageAsset() throws IOException {
		HlomSummary summary = summary("medical-image-asset.xml");
		assertEquals(Kind.IMAGE_ASSET, summary.kind());
		assertEquals("Drainage of Dialysis Fluid from Peritoneal Cavity", summary.title());
		assertEquals(List.of("image"), summary.learningResourceTypes());
		assertEquals(List.of("Anystate Medical School"), summary.contributorNames());
		assertTrue(summary.credits().isEmpty());
		assertTrue(summary.details().stream().anyMatch(f -> f.label().equals("Image size")
				&& f.value().startsWith("806")));
		assertTrue(summary.details().stream().anyMatch(f -> f.value().equals("sagittal")));
	}

	@Test
	void summarizesEmptyAndNewDocuments() {
		HlomSummary empty = HlomSummary.of((DocumentRoot) null);
		assertNull(empty.title());
		assertEquals(Kind.LEARNING_OBJECT, empty.kind());
		assertEquals("file.xml", empty.titleOr("file.xml"));
		assertEquals("", empty.excerpt(10));

		HlomSummary created = HlomSummary.of(HlomDocuments.createDocument("New course", "en-US"));
		assertEquals("New course", created.title());
		assertEquals(List.of("en-US"), created.languages());
	}

	@Test
	void parsesVCardNames() {
		assertEquals("John Doe", HlomSummary.vcardName("BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nEND:VCARD"));
		assertEquals("Doe, Smith & Co", HlomSummary.vcardName("BEGIN:VCARD\r\nFN:Doe\\, Smith & Co\r\nEND:VCARD"));
		assertEquals("Jane Roe", HlomSummary.vcardName("BEGIN:VCARD\nN:Roe;Jane;;;\nEND:VCARD"));
		assertEquals("Plain Organization", HlomSummary.vcardName("  Plain Organization "));
		assertNull(HlomSummary.vcardName(" "));
	}

	@Test
	void excerptsAtWordBoundary() {
		HlomSummary summary = HlomSummary.of(HlomDocuments.createDocument("T", "en"));
		HlomSummary withDescription = new HlomSummary("T", "one two three four five six", List.of(), Kind.LEARNING_OBJECT,
				List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), List.of(), List.of(),
				List.of(), List.of(), null, null, null, null, null, null, List.of(), List.of(), null, null, List.of());
		assertEquals("one two…", withDescription.excerpt(12));
		assertEquals("", summary.excerpt(5));
	}

	@Test
	void cardContainsRecordAndStatus() throws IOException {
		Resource resource = HlomResources.load(TestResources.sample("insulin-pump-therapy-course.xml"));
		HlomSummary summary = HlomSummary.of(resource);
		String html = HtmlReport.document(summary, List.of(), "insulin.xml", 87);
		assertTrue(html.startsWith("<!DOCTYPE html>"));
		assertTrue(html.contains("<h1>Insulin Pump Therapy</h1>"));
		assertTrue(html.contains("Educational offering"));
		assertTrue(html.contains("https://meshb.nlm.nih.gov/record/ui?ui=D007332"));
		assertTrue(html.contains("1.5 CME Credit"));
		assertTrue(html.contains("30 Apr 2007"));
		assertTrue(html.contains("Valid"));
		assertTrue(html.contains("87% complete"));
		assertTrue(html.contains("</html>"));
	}

	@Test
	void cardEscapesMarkup() {
		DocumentRoot root = HlomDocuments.createDocument("<script>alert('x')</script> & \"more\"", "en");
		HlomProblem problem = new HlomProblem(Severity.ERROR, Origin.RULES, "Bad <value> & worse", null,
				"/lom/general", 3, 1);
		String html = HtmlReport.document(HlomSummary.of(root), List.of(problem));
		assertFalse(html.contains("<script>alert"));
		assertTrue(html.contains("&lt;script&gt;alert(&#39;x&#39;)&lt;/script&gt; &amp; &quot;more&quot;"));
		assertTrue(html.contains("Bad &lt;value&gt; &amp; worse"));
		assertTrue(html.contains("1 error"));
		assertEquals("a&lt;b&gt;&amp;&quot;&#39;", HtmlReport.esc("a<b>&\"'"));
		assertEquals("", HtmlReport.esc(null));
	}

	@Test
	void libraryReportOfSamples() throws IOException {
		Path folder = Files.createTempDirectory("hlom-library");
		try {
			scanAndReport(folder);
		} finally {
			try (var files = Files.walk(folder)) {
				files.sorted(java.util.Comparator.reverseOrder()).forEach(p -> p.toFile().delete());
			}
		}
	}

	private static void scanAndReport(Path folder) throws IOException {
		for (String sample : TestResources.SAMPLES) {
			Path target = folder.resolve(sample.startsWith("medical") ? "assets/" + sample : sample);
			Files.createDirectories(target.getParent());
			Files.copy(Path.of(TestResources.sample(sample).toFileString()), target);
		}
		Files.writeString(folder.resolve("not-lom.xml"), "<root xmlns=\"urn:other\"/>", StandardCharsets.UTF_8);
		Files.writeString(folder.resolve("broken.xml"), "<lom xmlns=\"http://ltsc.ieee.org/xsd/LOM\"><general>",
				StandardCharsets.UTF_8);
		Files.writeString(folder.resolve("notes.txt"), "text", StandardCharsets.UTF_8);

		assertFalse(HlomLibrary.isHlomDocument(folder.resolve("not-lom.xml")));
		assertTrue(HlomLibrary.isHlomDocument(folder.resolve("broken.xml")));
		assertEquals(5, HlomLibrary.findCandidates(folder, () -> false).size());

		List<LibraryEntry> entries = HlomLibrary.scan(folder);
		assertEquals(4, entries.size(), entries.toString());
		LibraryEntry broken = entries.stream().filter(e -> e.fileName().equals("broken.xml")).findFirst().orElseThrow();
		assertEquals(LibraryEntry.Status.ERRORS, broken.status());
		assertEquals("broken.xml", broken.title());
		assertTrue(broken.problems().stream().noneMatch(p -> p.message().contains("file:")),
				TestResources.describe(broken.problems()));
		for (LibraryEntry entry : entries) {
			if (entry != broken) {
				assertEquals(0, entry.errors(), entry.fileName() + TestResources.describe(entry.problems()));
				assertTrue(entry.modified() != null && entry.size() > 0);
				assertFalse(entry.hasCompleteness());
			}
		}

		String html = HtmlReport.library(entries, folder.toString());
		assertTrue(html.contains("Healthcare LOM metadata library"));
		assertTrue(html.contains("Arsenic Toxicity"));
		assertTrue(html.contains("Drainage of Dialysis Fluid"));
		assertTrue(html.contains("<b>3</b> CME Credit"), "CME credits total 1.5 + 1.5");
		assertTrue(html.contains("<script>"));
		assertFalse(html.contains(">Complete<"), "no completeness column without a scorer");

		HlomLibrary.setCompletenessScorer(_ -> 42);
		try {
			LibraryEntry scored = HlomLibrary.load(folder.resolve("insulin-pump-therapy-course.xml"));
			assertEquals(42, scored.completeness());
			assertTrue(HtmlReport.library(List.of(scored)).contains(">Complete<"));
		} finally {
			HlomLibrary.setCompletenessScorer(null);
		}
	}
}
