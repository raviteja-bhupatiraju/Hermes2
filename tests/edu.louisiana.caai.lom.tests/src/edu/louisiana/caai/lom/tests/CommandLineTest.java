/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.louisiana.caai.lom.cli.Hermes;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;

/** Tests the {@code hermes} command line tool. */
class CommandLineTest {

	@TempDir
	Path folder;

	private String out;
	private String err;

	private int hermes(String... args) {
		ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
		ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
		int code = new Hermes(new PrintStream(outBytes, true, StandardCharsets.UTF_8),
				new PrintStream(errBytes, true, StandardCharsets.UTF_8)).run(List.of(args));
		out = outBytes.toString(StandardCharsets.UTF_8);
		err = errBytes.toString(StandardCharsets.UTF_8);
		return code;
	}

	private Path copy(String sample, String name) throws Exception {
		Path target = folder.resolve(name);
		Files.copy(Path.of(TestResources.sample(sample).toFileString()), target);
		return target;
	}

	private Path broken() throws Exception {
		Path file = copy("insulin-pump-therapy-course.xml", "broken.xml");
		String text = Files.readString(file, StandardCharsets.UTF_8);
		Files.writeString(file, text.replace("<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>", "")
				.replace("<string language=\"en\">Insulin Pump Therapy</string>", "<string>Insulin Pump Therapy</string>"),
				StandardCharsets.UTF_8);
		return file;
	}

	@Test
	void validateReportsProblemsAndExitCodes() throws Exception {
		copy("insulin-pump-therapy-course.xml", "valid.xml");
		assertEquals(Hermes.OK, hermes("validate", folder.toString()), err);
		assertTrue(out.contains("1 document checked, 0 invalid"), out);

		Path broken = broken();
		assertEquals(Hermes.FAILED, hermes("validate", broken.toString()));
		assertTrue(out.contains(broken + ":"), out);
		assertTrue(out.contains(": error: Meta-Metadata must declare the metadata schema \"HEALTHCARE_LOMv1\""), out);
		assertTrue(out.contains(": warning: "), out);
		// Only errors shown; never failing.
		assertEquals(Hermes.OK, hermes("validate", "--min-severity", "error", "--fail-on", "never", "--quiet",
				broken.toString()));
		assertFalse(out.contains("warning"), out);
		assertFalse(out.contains("checked"), out);
		assertEquals(Hermes.FAILED, hermes("validate", "--fail-on=warning", folder.toString()));
	}

	@Test
	void jsonAndSarifReports() throws Exception {
		Path broken = broken();
		hermes("validate", "--format", "json", broken.toString());
		JArray documents = assertInstanceOf(JArray.class, Json.parse(out));
		JObject document = (JObject) documents.items().getFirst();
		assertEquals(new Json.JBoolean(false, 0).value(), ((Json.JBoolean) document.get("valid")).value());
		assertFalse(((JArray) document.get("problems")).items().isEmpty());

		Path report = folder.resolve("report.sarif");
		hermes("validate", "--format", "sarif", "--output", report.toString(), broken.toString());
		JObject sarif = (JObject) Json.parse(Files.readString(report, StandardCharsets.UTF_8));
		assertEquals("2.1.0", ((Json.JString) sarif.get("version")).value());
		JObject run = (JObject) ((JArray) sarif.get("runs")).items().getFirst();
		JArray results = (JArray) run.get("results");
		assertFalse(results.items().isEmpty());
		JObject first = (JObject) results.items().getFirst();
		assertTrue(first.get("locations") instanceof JArray);
	}

	@Test
	void convertFormatAndFix() throws Exception {
		Path xml = copy("insulin-pump-therapy-course.xml", "course.xml");
		Path json = folder.resolve("course.json");
		assertEquals(Hermes.OK, hermes("convert", xml.toString(), json.toString()), err);
		assertTrue(Files.readString(json, StandardCharsets.UTF_8).contains("\"lom\""));
		assertEquals(Hermes.USAGE, hermes("convert", xml.toString(), json.toString()), "existing output");
		assertEquals(Hermes.OK, hermes("validate", json.toString()), out);
		assertEquals(Hermes.OK, hermes("convert", json.toString()));
		assertTrue(out.startsWith("<?xml"), out);

		assertEquals(Hermes.FAILED, hermes("format", "--check", xml.toString()));
		assertEquals(Hermes.OK, hermes("format", xml.toString()));
		assertEquals(Hermes.OK, hermes("format", "--check", xml.toString()), out);

		Path broken = broken();
		assertEquals(Hermes.OK, hermes("fix", "--dry-run", broken.toString()), out);
		assertTrue(out.contains("HEALTHCARE_LOMv1"), out);
		assertEquals(Hermes.FAILED, hermes("validate", broken.toString()), "dry run leaves the file");
		assertEquals(Hermes.OK, hermes("fix", broken.toString()), out);
		assertEquals(Hermes.OK, hermes("validate", "--fail-on", "warning", broken.toString()), out);
	}

	@Test
	void searchAndInfo() throws Exception {
		copy("insulin-pump-therapy-course.xml", "course.xml");
		assertEquals(Hermes.OK, hermes("search", "--in", "keywords", "diabetes", folder.toString()));
		assertTrue(out.contains("General › Keyword: [diabetes] mellitus"), out);
		assertEquals(Hermes.FAILED, hermes("search", "--in", "titles", "diabetes", folder.toString()));
		assertEquals(Hermes.USAGE, hermes("search", "--in", "nothing", "x", folder.toString()));
		assertEquals(Hermes.OK, hermes("info", folder.resolve("course.xml").toString()));
		assertTrue(out.contains("Title: Insulin Pump Therapy"), out);
		assertTrue(out.contains("Problems: 0 errors"), out);
	}

	@Test
	void replace() throws Exception {
		Path course = copy("insulin-pump-therapy-course.xml", "course.xml");
		String before = Files.readString(course, StandardCharsets.UTF_8);
		assertEquals(Hermes.OK, hermes("replace", "--in", "titles", "--dry-run", "Pump", "Pump Course", folder.toString()));
		assertTrue(out.contains("\"Insulin Pump Therapy\" -> \"Insulin Pump Course Therapy\""), out);
		assertEquals(before, Files.readString(course, StandardCharsets.UTF_8), "a dry run changes nothing");
		assertEquals(Hermes.OK, hermes("replace", "--regex", "(Pump) (Therapy)", "$2 by $1", course.toString()));
		String after = Files.readString(course, StandardCharsets.UTF_8);
		assertTrue(after.contains(">Insulin Therapy by Pump<"), after);
		assertEquals(before.lines().count(), after.lines().count(), "the layout is kept");
		assertEquals(Hermes.FAILED, hermes("replace", "no such text", "x", course.toString()));
		assertEquals(Hermes.USAGE, hermes("replace", "x", course.toString()));
	}

	@Test
	void migrateAndCrosswalk() throws Exception {
		Path ci = folder.resolve("ci-v1.xml");
		Files.writeString(ci, MedbiqSamples.text("samples/curriculum-inventory/pelican-bay-md-2015-2016-v1.xml"),
				StandardCharsets.UTF_8);
		Path v10 = folder.resolve("ci-v10.xml");
		assertEquals(Hermes.OK, hermes("migrate", ci.toString(), v10.toString()));
		assertTrue(err.contains("SequenceBlockLevels"), err);
		assertTrue(Files.readString(v10, StandardCharsets.UTF_8).contains("curriculuminventory/v10/"));
		assertEquals(Hermes.OK, hermes("validate", "--fail-on", "error", v10.toString()));
		assertEquals(Hermes.USAGE, hermes("migrate", v10.toString()), "already the newest version");

		Path course = copy("insulin-pump-therapy-course.xml", "course.xml");
		assertEquals(Hermes.OK, hermes("crosswalk", course.toString()));
		assertTrue(out.contains("\"@type\": \"Course\"") || out.contains("\"@type\":\"Course\""), out);
		assertEquals(Hermes.OK, hermes("crosswalk", "--to", "dc", course.toString()));
		assertTrue(out.contains("<dc:title>Insulin Pump Therapy</dc:title>"), out);
		assertEquals(Hermes.USAGE, hermes("crosswalk", ci.toString()), "only Healthcare LOM");
	}

	@Test
	void usageErrors() {
		assertEquals(Hermes.USAGE, hermes());
		assertEquals(Hermes.USAGE, hermes("frobnicate"));
		assertEquals(Hermes.USAGE, hermes("validate"));
		assertEquals(Hermes.USAGE, hermes("validate", "--format", "pdf", "x.xml"));
		assertEquals(Hermes.USAGE, hermes("validate", "no-such-file.xml"));
		assertTrue(err.contains("No such file"), err);
		assertEquals(Hermes.OK, hermes("help"));
		assertTrue(out.contains("Usage: hermes"), out);
		assertEquals(Hermes.OK, hermes("version"));
	}
}
