/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.io.TempDir;

import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.json.HlomJson;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;

/**
 * The JSON form of HLOM documents: conversion from and to XML, loading and saving {@code .json} files.
 */
class JsonFormatTest {

	@TestFactory
	Stream<DynamicTest> samplesRoundTripThroughJson() {
		// The specification samples are not used: some of their values are invalid as published.
		return TestResources.SAMPLES.stream().map(path -> DynamicTest.dynamicTest(path, () -> {
			Resource original = HlomResources.load(TestResources.sample(path));
			String json = HlomResources.toJson(original);
			Resource fromJson = HlomResources.parse(json, URI.createURI("memory:/" + path + ".json"));
			assertTrue(fromJson.getErrors().isEmpty(), () -> path + ": " + fromJson.getErrors() + "\n" + json);
			DocumentRoot root = HlomResources.getDocumentRoot(fromJson);
			assertNotNull(root, path);
			assertTrue(EcoreUtil.equals(HlomResources.getDocumentRoot(original).getLom(), root.getLom()),
					() -> path + ": the model differs after the JSON round trip\n" + json);
			assertEquals(json, HlomResources.toJson(fromJson), path);
		}));
	}

	@TestFactory
	Stream<DynamicTest> jsonSamplesValidateLikeXml() {
		return TestResources.SAMPLES.stream().map(name -> DynamicTest.dynamicTest(name, () -> {
			String json = HlomResources.toJson(HlomResources.load(TestResources.sample(name)));
			List<HlomProblem> problems = HlomValidator
					.validate(HlomResources.parse(json, URI.createURI("memory:/" + name + ".json")));
			List<HlomProblem> errors = TestResources.withSeverity(problems, Severity.ERROR);
			assertTrue(errors.isEmpty(), () -> name + TestResources.describe(errors));
		}));
	}

	@Test
	void jsonUsesSchemaNaming() throws Exception {
		String json = HlomResources
				.toJson(HlomResources.load(TestResources.sample("course-with-multiple-credit-types.xml")));
		Json.JObject document = (Json.JObject) Json.parse(json);
		assertEquals(HlomJson.SCHEMA_URL, ((Json.JString) document.get("$schema")).value());
		Json.JObject lom = assertInstanceOf(Json.JObject.class, document.get("lom"));
		Json.JArray general = assertInstanceOf(Json.JArray.class, lom.get("general"));
		Json.JObject first = (Json.JObject) general.items().getFirst();
		Json.JArray titles = assertInstanceOf(Json.JArray.class, first.get("title"));
		Json.JObject title = (Json.JObject) titles.items().getFirst();
		Json.JObject string = (Json.JObject) ((Json.JArray) title.get("string")).items().getFirst();
		assertNotNull(string.get("language"), json);
		assertNotNull(string.get(HlomJson.CONTENT), json);
		// MeSH keyword attributes (hx:source, hx:id) are plain members.
		Json.JObject keyword = (Json.JObject) ((Json.JArray) first.get("keyword")).items().getFirst();
		assertEquals("MeSH", ((Json.JString) keyword.get("source")).value());
		assertTrue(json.contains("\"healthcareEducation\""), json);
		assertTrue(json.contains("\"numberOfCredits\": 1.5"), json);
		assertFalse(json.contains("\"@"), json);
	}

	@Test
	void jsonFilesAreSavedAndLoaded(@TempDir Path folder) throws IOException {
		Path file = folder.resolve("record.json");
		Resource resource = HlomResources.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(HlomDocuments.createDocument("Sepsis Bundle Basics", "en"));
		resource.save(HlomResources.saveOptions());

		String text = Files.readString(file, StandardCharsets.UTF_8);
		assertTrue(text.startsWith("{"), text);
		assertTrue(text.contains("\"content\": \"Sepsis Bundle Basics\""), text);

		Resource loaded = HlomResources.load(URI.createFileURI(file.toString()));
		assertTrue(loaded.getErrors().isEmpty(), () -> loaded.getErrors().toString());
		Lom lom = HlomResources.getDocumentRoot(loaded).getLom();
		assertEquals("Sepsis Bundle Basics", lom.getGeneral().getFirst().getTitle().getFirst().getString().getFirst().getValue());
		assertTrue(HlomLibrary.hasHlomExtension(file));
		assertTrue(HlomLibrary.isHlomDocument(file));
		assertEquals("Sepsis Bundle Basics", HlomLibrary.load(file).title());
	}

	@Test
	void handWrittenJsonIsAcceptedInAnyOrder() throws IOException {
		String json = """
				{
				  "lom": {
				    "healthcareMetadata": {
				      "healthcareEducation": {
				        "credits": {
				          "numberOfCredits": 2,
				          "creditType": "CME",
				          "accreditingBody": "ACCME"
				        }
				      }
				    },
				    "general": {
				      "title": { "string": { "language": "en", "content": "Hand-written" } },
				      "keyword": { "source": "MeSH", "id": "D018805", "string": { "language": "en", "content": "Sepsis" } }
				    }
				  }
				}
				""";
		Resource resource = HlomResources.parse(json, URI.createURI("memory:/hand.json"));
		assertTrue(resource.getErrors().isEmpty(), () -> resource.getErrors().toString());
		Lom lom = HlomResources.getDocumentRoot(resource).getLom();
		Keyword keyword = lom.getGeneral().getFirst().getKeyword().getFirst();
		assertEquals("D018805", keyword.getId());
		CreditsType credits = lom.getHealthcareMetadata().getFirst().getHealthcareEducation().getCredits().getFirst();
		assertEquals(new BigDecimal("2"), credits.getNumberOfCredits());
		assertEquals("ACCME", credits.getAccreditingBody());
		// The XML form has the schema order, so it is valid against the XML Schema.
		List<HlomProblem> schemaErrors = HlomValidator.validate(resource).stream()
				.filter(p -> p.severity() == Severity.ERROR && p.origin() == HlomProblem.Origin.SCHEMA).toList();
		assertTrue(schemaErrors.isEmpty(), () -> TestResources.describe(schemaErrors));
	}

	@Test
	void syntaxErrorsReportTheJsonLine() {
		String json = """
				{
				  "lom": {
				    "general": {
				      "title":\s
				    }
				  }
				}
				""";
		Json.JsonException error = assertThrows(Json.JsonException.class, () -> HlomJson.toXml(json));
		assertEquals(5, error.getLine());

		Resource resource = HlomResources.createResourceSet().createResource(URI.createURI("memory:/broken.json"));
		assertThrows(IOException.class, () -> resource.load(
				new java.io.ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), HlomResources.loadOptions()));
		assertEquals(5, resource.getErrors().getFirst().getLine());
	}

	@Test
	void invalidValuesReportTheJsonLine() {
		String json = """
				{
				  "lom": {
				    "healthcareMetadata": {
				      "healthcareEducation": {
				        "credits": {
				          "creditType": "NOPE"
				        }
				      }
				    }
				  }
				}
				""";
		Resource parsed = HlomResources.createResourceSet().createResource(URI.createURI("memory:/invalid.json"));
		try {
			parsed.load(new java.io.ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
					HlomResources.loadOptions());
		} catch (IOException _) {
			// The problems are recorded in the resource.
		}
		assertFalse(parsed.getErrors().isEmpty(), "an invalid creditType is an error");
		assertEquals(6, parsed.getErrors().getFirst().getLine(), parsed.getErrors().getFirst().getMessage());
	}
}
