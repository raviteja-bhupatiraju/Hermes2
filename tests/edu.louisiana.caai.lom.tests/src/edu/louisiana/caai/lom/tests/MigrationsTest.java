/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Migrations;
import edu.louisiana.caai.medbiq.model.Migrations.Migration;
import edu.louisiana.caai.medbiq.model.Paths;

/** The conversions of documents to newer versions of their standards ({@link Migrations}). */
class MigrationsTest {

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	private static String errors(Result result) {
		return result.problems().stream().map(Located::problem).filter(p -> p.severity() == Severity.ERROR)
				.map(p -> p.message()).collect(Collectors.joining("\n"));
	}

	@Test
	void curriculumInventoryOneBecomesV10() throws Exception {
		Result v1 = MedbiqSamples.check(MedbiqSamples.text("samples/curriculum-inventory/pelican-bay-md-2015-2016-v1.xml"));
		List<Migration> migrations = Migrations.of(v1.type().id());
		assertEquals(1, migrations.size());
		Migrations.Result migrated = Migrations.migrate(migrations.getFirst(), v1.documentRoot());
		Result v10 = MedbiqSamples.check(migrated.xml());
		assertEquals("curriculum-inventory-v10", v10.type().id());
		assertEquals("", errors(v10), "valid as v10");
		assertTrue(migrated.notes().stream().anyMatch(note -> note.contains("SequenceBlockLevels")), migrated.notes()
				.toString());
		// Nothing is lost: the same events, blocks and their levels.
		assertEquals(Paths.objects(Paths.rootElement(v1.documentRoot()), "Events/Event").size(),
				Paths.objects(Paths.rootElement(v10.documentRoot()), "Events/Event").size());
		assertEquals(Paths.texts(Paths.rootElement(v1.documentRoot()), "Sequence/SequenceBlock/Level"),
				Paths.texts(Paths.rootElement(v10.documentRoot()), "Sequence/SequenceBlock/SequenceBlockLevels/"
						+ "StartingAcademicLevel"));
	}

	@Test
	void activityReportOneBecomesTwo() throws Exception {
		for (String sample : List.of("samples/activity-report/cme-course-completions-v1.xml",
				"samples/activity-report/registration-in-progress-v1.xml")) {
			Result v1 = MedbiqSamples.check(MedbiqSamples.text(sample));
			Migrations.Result migrated = Migrations.migrate(Migrations.of(v1.type().id()).getFirst(), v1.documentRoot());
			Result v2 = MedbiqSamples.check(migrated.xml());
			assertEquals("activity-report-v2", v2.type().id());
			assertFalse(Paths.text(Paths.rootElement(v2.documentRoot()), "ReportingStartDate") == null, sample);
			// Left to the author: the participants, which version 1.0 doesn't have, and identifier domains that are no
			// internet domains (version 2.0 requires idd:<domain>:<kind>).
			String errors = errors(v2);
			assertTrue(errors.lines().allMatch(line -> line.contains("articipant") || line.contains("IDDomainType")
					|| line.contains("idd:")),
					sample + ":\n" + errors);
			assertTrue(migrated.notes().stream().anyMatch(note -> note.contains("Participant")));
		}
	}

	@Test
	void onlyOlderVersionsHaveMigrations() {
		assertTrue(Migrations.of("curriculum-inventory-v10").isEmpty());
		assertTrue(Migrations.of("activity-report-v2").isEmpty());
		assertTrue(Migrations.of("hlom").isEmpty());
	}
}
