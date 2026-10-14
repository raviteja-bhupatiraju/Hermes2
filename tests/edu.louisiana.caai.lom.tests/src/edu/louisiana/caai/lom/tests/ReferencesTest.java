/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.medbiq.model.DocumentGraph;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.References.Kind;
import edu.louisiana.caai.medbiq.model.References.Occurrence;
import edu.louisiana.caai.medbiq.model.References.Target;

/** The references within documents: kinds, targets, resolution, occurrences and key locations. */
class ReferencesTest {

	private static final String SAMPLE_V10 = "samples/curriculum-inventory/bayou-state-md-2024-2025-v10.xml";
	private static final String SAMPLE_V1 = "samples/curriculum-inventory/pelican-bay-md-2015-2016-v1.xml";
	private static final String PERFORMANCE = "samples/competencies/milestones-performance-framework.xml";

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	private static EObject root(Result result) {
		return Paths.rootElement(result.documentRoot());
	}

	private static List<Kind> kinds(Result result) {
		return ((MedbiqDocumentType) result.type()).references();
	}

	@Test
	void keysAreReadFromXPathAndPlainReferences() {
		Kind event = new Kind("Event", "EventReference", "Events/Event", "@id", "Title",
				"/CurriculumInventory/Events/Event[@id='%s']");
		assertEquals("E1", event.key("/CurriculumInventory/Events/Event[@id='E1']"));
		assertEquals("E 2", event.key(" /CurriculumInventory/Events/Event[@id = \"E 2\"] "));
		assertNull(event.key("E1"));
		assertEquals("/CurriculumInventory/Events/Event[@id='E3']", event.format("E3"));
		Kind plain = new Kind("Scale", "PerformanceScaleReference", "PerformanceScale", "@id", null, "%s");
		assertEquals("S1", plain.key(" S1 "));
		assertEquals("S1", plain.format("S1"));
	}

	@Test
	void referencesOfTheSamplesResolve() {
		for (String sample : List.of(SAMPLE_V10, SAMPLE_V1, PERFORMANCE)) {
			Result result = MedbiqSamples.check(MedbiqSamples.text(sample));
			EObject root = root(result);
			List<Kind> kinds = kinds(result);
			assertFalse(kinds.isEmpty(), sample);
			List<Occurrence> occurrences = References.occurrences(root, kinds);
			assertFalse(occurrences.isEmpty(), sample + " has references");
			assertEquals(List.of(), References.unresolved(root, kinds), sample);
			for (Occurrence occurrence : occurrences) {
				Target target = References.resolve(root, occurrence.kind(), occurrence.text());
				assertNotNull(target, occurrence.text());
				assertTrue(References.referencesTo(root, target).contains(occurrence));
			}
		}
	}

	@Test
	void curriculumInventoryTargetsHaveLabelsAndKeyLocations() {
		Result result = MedbiqSamples.check(MedbiqSamples.text(SAMPLE_V10));
		EObject root = root(result);
		for (Kind kind : kinds(result)) {
			List<Target> targets = References.targets(root, kind);
			assertFalse(targets.isEmpty(), kind.label());
			for (Target target : targets) {
				assertNotNull(target.label());
				References.KeyLocation location = References.keyLocation(target);
				assertNotNull(location, kind.label() + " " + target.key());
				assertEquals(target.key(), String.valueOf(location.owner().eGet(location.attribute())).strip());
			}
		}
		// The academic level of version 1 is a reference only within a sequence block.
		Result v1 = MedbiqSamples.check(MedbiqSamples.text(SAMPLE_V1));
		List<Occurrence> levels = References.occurrences(root(v1), kinds(v1)).stream()
				.filter(occurrence -> occurrence.kind().element().equals("Level")).toList();
		assertTrue(levels.stream().allMatch(occurrence -> "SequenceBlock".equals(References.elementName(
				occurrence.owner()))), "levels of AcademicLevels are no references");
	}

	@Test
	void brokenReferencesAreFound() {
		String text = MedbiqSamples.text(PERFORMANCE);
		String scale = text.replaceFirst("<PerformanceScaleReference>([^<]*)</PerformanceScaleReference>",
				"<PerformanceScaleReference>missing-scale</PerformanceScaleReference>");
		Result result = MedbiqSamples.check(scale);
		List<Occurrence> unresolved = References.unresolved(root(result), kinds(result));
		assertEquals(1, unresolved.size(), () -> unresolved.toString());
		assertEquals("missing-scale", unresolved.getFirst().key());
	}

	@Test
	void documentsHaveIdentifiersAndLinksToOtherDocuments() {
		Result framework = MedbiqSamples.check(MedbiqSamples.text("samples/competencies/core-competencies-framework.xml"));
		DocumentLinks frameworkLinks = DocumentLinks.of(framework.type(), framework.documentRoot());
		assertFalse(frameworkLinks.identifiers().isEmpty(), "the framework's identifiers");
		List<DocumentLinks.Link> includes = frameworkLinks.links();
		assertTrue(includes.stream().anyMatch(link -> link.target().entry()
				.equals("https://gme.lakeside.example.org/competencies/patient-care")), () -> includes.toString());
		assertTrue(includes.stream().allMatch(link -> link.label().equals("Included competency")));

		Result object = MedbiqSamples.check(MedbiqSamples.text("samples/competencies/competency-patient-care.xml"));
		DocumentLinks objectLinks = DocumentLinks.of(object.type(), object.documentRoot());
		assertTrue(objectLinks.isIdentifiedBy(new DocumentLinks.Identifier("URI",
				"https://gme.lakeside.example.org/competencies/patient-care/")), "a trailing slash doesn't matter");
		assertTrue(objectLinks.isIdentifiedBy(new DocumentLinks.Identifier(null, "PC")));
		assertFalse(objectLinks.isIdentifiedBy(new DocumentLinks.Identifier("ISBN", "PC")), "other catalog");
	}

	@Test
	void documentsAreGraphsOfTheirElementsAndReferences() {
		Result ci = MedbiqSamples.check(MedbiqSamples.text(SAMPLE_V10));
		DocumentGraph graph = DocumentGraph.of((MedbiqDocumentType) ci.type(), ci.documentRoot());
		assertTrue(graph.kinds().containsAll(List.of("Event", "Sequence block", "Competency object")), () -> graph.kinds()
				.toString());
		assertTrue(graph.edges().stream().anyMatch(edge -> edge.source().kind().equals("Sequence block")
				&& edge.target().kind().equals("Event")), "blocks reference their events");
		int references = References.occurrences(root(ci), kinds(ci)).size();
		assertTrue(graph.edges().size() >= references * 9 / 10, graph.edges().size() + " of " + references);

		Result framework = MedbiqSamples.check(MedbiqSamples.text("samples/competencies/core-competencies-framework.xml"));
		DocumentGraph competencies = DocumentGraph.of((MedbiqDocumentType) framework.type(), framework.documentRoot());
		int relations = Paths.objects(root(framework), "Relation").size();
		assertTrue(relations > 0);
		assertEquals(relations, competencies.edges().size(), () -> competencies.edges().toString());
		assertTrue(competencies.edges().stream().anyMatch(edge -> edge.label().equals("narrower")
				|| edge.label().equals("broader")));
	}
}
