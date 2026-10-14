/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Hit;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.lom.model.support.search.HlomSearchFields;

/**
 * Tests the search of field values ({@link DocumentSearch}) and element paths ({@link ElementPath}).
 */
class DocumentSearchTest {

	private static Resource insulin() throws Exception {
		return HlomResources.load(TestResources.sample("insulin-pump-therapy-course.xml"));
	}

	private static List<Hit> search(Query query) throws Exception {
		return DocumentSearch.search(insulin(), query);
	}

	@Test
	void findsTextInAllFieldsIgnoringCase() throws Exception {
		List<Hit> hits = search(Query.of("INSULIN"));
		List<String> paths = hits.stream().map(hit -> hit.value().path()).toList();
		assertTrue(paths.contains("/lom/general/title/string"), paths::toString);
		assertTrue(paths.contains("/lom/general/description/string"), paths::toString);
		assertTrue(paths.contains("/lom/general/keyword/string"), paths::toString);
		Hit title = hits.stream().filter(hit -> hit.value().path().equals("/lom/general/title/string")).findFirst()
				.orElseThrow();
		assertEquals("Insulin Pump Therapy", title.value().text());
		assertEquals(0, title.matches().getFirst()[0]);
		assertEquals(7, title.matches().getFirst()[1]);
		assertEquals("General › Title", title.value().label());
	}

	@Test
	void repeatedElementsHaveIndexes() throws Exception {
		List<Hit> hits = search(Query.of("diabetes"));
		assertTrue(hits.stream().anyMatch(hit -> hit.value().path().equals("/lom/general/keyword[2]/string")),
				() -> hits.stream().map(hit -> hit.value().path()).toList().toString());
	}

	@Test
	void attributeValuesAreSearched() throws Exception {
		List<Hit> hits = search(Query.of("D003920"));
		assertEquals(1, hits.size());
		assertEquals("/lom/general/keyword[2]/@id", hits.getFirst().value().path());
		assertEquals("id", hits.getFirst().value().attribute());
	}

	@Test
	void fieldGroupsLimitTheSearch() throws Exception {
		Query titles = new Query("insulin", false, false, false, List.of(HlomSearchFields.TITLES));
		List<Hit> hits = search(titles);
		assertEquals(1, hits.size());
		assertEquals("/lom/general/title/string", hits.getFirst().value().path());

		Query contributors = new Query("American Academy", false, false, false, List.of(HlomSearchFields.CONTRIBUTORS));
		// Two contributors (lifeCycle and metaMetadata) and the accredited provider.
		assertEquals(3, search(contributors).size());
		Query keywords = new Query("American Academy", false, false, false, List.of(HlomSearchFields.KEYWORDS));
		assertTrue(search(keywords).isEmpty());
	}

	@Test
	void caseSensitiveWholeWordAndRegex() throws Exception {
		assertTrue(search(new Query("INSULIN PUMP", true, false, false, List.of())).isEmpty());
		assertFalse(search(new Query("Insulin Pump", true, false, false, List.of())).isEmpty());
		// "infusion" is a whole word in "insulin infusion systems" but "fusion" is not.
		assertFalse(search(new Query("infusion", false, false, true, List.of())).isEmpty());
		assertTrue(search(new Query("fusion", false, false, true, List.of())).isEmpty());
		List<Hit> regex = search(new Query("D00\\d{4}", true, true, false, List.of()));
		assertEquals(2, regex.size());
		assertThrows(PatternSyntaxException.class, () -> new Query("(", false, true, false, List.of()).pattern());
	}

	@Test
	void multipleMatchesInOneValue() throws Exception {
		Hit hit = search(Query.of("in")).stream()
				.filter(h -> h.value().path().equals("/lom/general/description/string")).findFirst().orElseThrow();
		assertTrue(hit.matches().size() > 3);
	}

	@Test
	void elementPathsRoundTrip() throws Exception {
		Resource resource = insulin();
		DocumentRoot root = HlomResources.getDocumentRoot(resource);
		Keyword keyword = root.getLom().getGeneral().getFirst().getKeyword().get(1);
		ElementPath path = ElementPath.of(keyword);
		assertEquals("/lom/general/keyword[2]", path.toString());
		assertSame(keyword, path.resolve(root));
		// Parsed paths have no namespaces; attributes and prefixes are ignored.
		EObject resolved = ElementPath.parse("/lom/general/keyword[2]/@hx:id").resolve(root);
		assertSame(keyword, resolved);
		assertSame(keyword.getString().getFirst(), ElementPath.parse("/lom/general/keyword[2]/string").resolve(root));
		// A path that only partly exists resolves to the deepest existing element.
		assertSame(root.getLom().getGeneral().getFirst(), ElementPath.parse("/lom/general/nothing").resolve(root));
	}

	@Test
	void labels() {
		assertEquals("Healthcare Metadata › Healthcare Education › Credits",
				FieldLabels.label(List.of("lom", "healthcareMetadata", "healthcareEducation", "credits"), null));
		assertEquals("General › Keyword › ID", FieldLabels.label(List.of("lom", "general", "keyword"), "id"));
		assertEquals("LOM", FieldLabels.words("lom"));
	}
}
