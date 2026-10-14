/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JString;
import edu.louisiana.caai.lom.model.support.json.Json.Value;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Contributor;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Credit;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Keyword;

/**
 * Crosswalks of Healthcare LOM records to the metadata of catalogs and the web: unqualified Dublin Core in the
 * {@code oai_dc} format of OAI-PMH repositories, and a schema.org {@code LearningResource} (or {@code Course}) in
 * JSON-LD for search engines and learning registries (LRMI). They follow the published LOM to Dublin Core mapping
 * (IEEE 1484.12.1 Annex B) and the LRMI properties of schema.org; what the target has no element for is left out.
 */
public final class Crosswalks {

	private Crosswalks() {
	}

	/** The record as unqualified Dublin Core ({@code oai_dc:dc}). */
	public static String dublinCore(HlomSummary record) {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\"\n");
		xml.append("    xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n");
		xml.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		xml.append("    xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ ");
		xml.append("http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">\n");
		element(xml, "title", record.title());
		for (Contributor contributor : record.contributors()) {
			String role = contributor.role() == null ? "" : contributor.role().toLowerCase();
			String element = role.equals("author") ? "creator" : role.equals("publisher") ? "publisher" : "contributor";
			element(xml, element, contributor.name());
		}
		for (Keyword keyword : record.keywords()) {
			element(xml, "subject", keyword.text());
		}
		element(xml, "description", record.description());
		for (Contributor contributor : record.contributors()) {
			if (contributor.date() != null) {
				element(xml, "date", contributor.date());
				break;
			}
		}
		for (String type : record.learningResourceTypes()) {
			element(xml, "type", type);
		}
		for (String format : record.formats()) {
			element(xml, "format", format);
		}
		for (String identifier : record.identifiers()) {
			element(xml, "identifier", identifier);
		}
		for (String location : record.locations()) {
			element(xml, "identifier", location);
		}
		for (String language : record.languages()) {
			element(xml, "language", language);
		}
		element(xml, "rights", record.rights());
		xml.append("</oai_dc:dc>\n");
		return xml.toString();
	}

	/** The record as a schema.org learning resource in JSON-LD (a Course for educational offerings with credits). */
	public static String schemaOrg(HlomSummary record) {
		JObject json = new JObject();
		json.put("@context", "https://schema.org");
		boolean course = record.kind() == HlomSummary.Kind.EDUCATIONAL_OFFERING;
		json.put("@type", course ? "Course" : "LearningResource");
		put(json, "name", record.title());
		put(json, "description", record.description());
		if (!record.languages().isEmpty()) {
			put(json, "inLanguage", record.languages().getFirst());
		}
		strings(json, "identifier", record.identifiers());
		if (!record.locations().isEmpty()) {
			put(json, "url", record.locations().getFirst());
		}
		strings(json, "keywords", record.keywords().stream().map(Keyword::text).toList());
		JArray about = new JArray();
		for (Keyword keyword : record.keywords()) {
			if (keyword.id() != null && keyword.source() != null) {
				JObject term = new JObject();
				term.put("@type", "DefinedTerm");
				term.put("name", keyword.text());
				term.put("termCode", keyword.id());
				term.put("inDefinedTermSet", keyword.isMesh() ? "https://id.nlm.nih.gov/mesh/" : keyword.source());
				about.items().add(term);
			}
		}
		if (!about.items().isEmpty()) {
			json.put("about", about);
		}
		people(json, "author", record.contributors(), "author");
		people(json, "publisher", record.contributors(), "publisher");
		strings(json, "learningResourceType", record.learningResourceTypes());
		strings(json, "educationalLevel", record.contexts());
		strings(json, "teaches", record.objectives());
		Set<String> roles = new LinkedHashSet<>(record.audienceCategories());
		roles.addAll(record.professions());
		if (!roles.isEmpty()) {
			JObject audience = new JObject();
			audience.put("@type", "EducationalAudience");
			audience.put("educationalRole", array(new ArrayList<>(roles)));
			json.put("audience", audience);
		}
		strings(json, "encodingFormat", record.formats());
		put(json, "version", record.version());
		put(json, "expires", record.expirationDate());
		if (record.cost() != null) {
			json.put("isAccessibleForFree", new Json.JBoolean("no".equalsIgnoreCase(record.cost()), 0));
		}
		put(json, "usageInfo", record.rights());
		JArray credentials = new JArray();
		for (Credit credit : record.credits()) {
			JObject credential = new JObject();
			credential.put("@type", "EducationalOccupationalCredential");
			credential.put("name", credit.label());
			if (credit.type() != null) {
				credential.put("credentialCategory", credit.type());
			}
			if (credit.accreditingBody() != null) {
				JObject body = new JObject();
				body.put("@type", "Organization");
				body.put("name", credit.accreditingBody());
				credential.put("recognizedBy", body);
			}
			credentials.items().add(credential);
		}
		if (!credentials.items().isEmpty()) {
			json.put("educationalCredentialAwarded", credentials);
		}
		if (record.startDate() != null) {
			JObject instance = new JObject();
			instance.put("@type", "CourseInstance");
			put(instance, "startDate", record.startDate());
			put(instance, "endDate", record.endDate());
			if (record.city() != null || record.country() != null) {
				JObject place = new JObject();
				place.put("@type", "Place");
				put(place, "name", String.join(", ", record.city() == null ? "" : record.city(),
						record.country() == null ? "" : record.country()).replaceAll("^, |, $", ""));
				instance.put("location", place);
			}
			json.put("hasCourseInstance", instance);
		}
		return Json.write(json) + "\n";
	}

	private static void element(StringBuilder xml, String name, String value) {
		if (value != null && !value.isBlank()) {
			xml.append("  <dc:").append(name).append('>').append(escape(value.strip())).append("</dc:").append(name)
					.append(">\n");
		}
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	private static void put(JObject json, String name, String value) {
		if (value != null && !value.isBlank()) {
			json.put(name, value.strip());
		}
	}

	private static void strings(JObject json, String name, List<String> values) {
		List<String> present = values.stream().filter(v -> v != null && !v.isBlank()).map(String::strip).distinct()
				.toList();
		if (present.size() == 1) {
			json.put(name, present.getFirst());
		} else if (!present.isEmpty()) {
			json.put(name, array(present));
		}
	}

	private static JArray array(List<String> values) {
		JArray array = new JArray();
		for (String value : values) {
			array.items().add(new JString(value, 0));
		}
		return array;
	}

	private static void people(JObject json, String name, List<Contributor> contributors, String role) {
		JArray people = new JArray();
		for (Contributor contributor : contributors) {
			if (role.equalsIgnoreCase(contributor.role()) && contributor.name() != null) {
				JObject person = new JObject();
				person.put("@type", role.equals("publisher") ? "Organization" : "Person");
				person.put("name", contributor.name());
				people.items().add(person);
			}
		}
		if (people.items().size() == 1) {
			json.put(name, (Value) people.items().getFirst());
		} else if (!people.items().isEmpty()) {
			json.put(name, people);
		}
	}
}
