/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.report.Crosswalks;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;

/** The crosswalks of Healthcare LOM records to Dublin Core and schema.org ({@link Crosswalks}). */
class CrosswalksTest {

	private static HlomSummary insulin() throws Exception {
		return HlomSummary.of(HlomResources.load(TestResources.sample("insulin-pump-therapy-course.xml")));
	}

	@Test
	void dublinCoreIsWellFormedOaiDc() throws Exception {
		String xml = Crosswalks.dublinCore(insulin());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
		String dc = "http://purl.org/dc/elements/1.1/";
		assertEquals("Insulin Pump Therapy", document.getElementsByTagNameNS(dc, "title").item(0).getTextContent());
		assertTrue(document.getElementsByTagNameNS(dc, "subject").getLength() >= 2, "keywords as subjects");
		assertEquals(1, document.getElementsByTagNameNS(dc, "description").getLength());
	}

	@Test
	void schemaOrgDescribesTheCourseAndItsCredits() throws Exception {
		String jsonLd = Crosswalks.schemaOrg(insulin());
		Json.JObject json = (Json.JObject) Json.parse(jsonLd);
		assertEquals("https://schema.org", ((Json.JString) json.get("@context")).value());
		assertEquals("Course", ((Json.JString) json.get("@type")).value());
		assertEquals("Insulin Pump Therapy", ((Json.JString) json.get("name")).value());
		assertTrue(jsonLd.contains("\"educationalCredentialAwarded\""), jsonLd);
		assertTrue(jsonLd.contains("\"termCode\": \"D007332\"") || jsonLd.contains("\"termCode\":\"D007332\""),
				"MeSH keywords as defined terms: " + jsonLd);
	}
}
