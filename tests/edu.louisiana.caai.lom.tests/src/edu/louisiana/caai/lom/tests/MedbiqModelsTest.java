/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqModels;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.MinimalDocuments;

/** Tests the models, schemas and document types of the MedBiquitous standards besides Healthcare LOM. */
class MedbiqModelsTest {

	@BeforeAll
	static void init() {
		MedbiqStandards.init();
	}

	/** A minimal document of a type built from its model, as XML. */
	static String minimalXml(MedbiqDocumentType type) throws Exception {
		EPackage ePackage = type.ePackage();
		EClass documentRootClass = ExtendedMetaData.INSTANCE.getDocumentRoot(ePackage);
		EObject documentRoot = EcoreUtil.create(documentRootClass);
		EStructuralFeature rootFeature = ExtendedMetaData.INSTANCE.getElement(documentRootClass, type.namespace(),
				type.rootElement());
		EObject root = MinimalDocuments.create(((EReference) rootFeature).getEReferenceType());
		documentRoot.eSet(rootFeature, root);
		Resource resource = type.createResourceSet().createResource(URI.createURI("minimal.xml"));
		resource.getContents().add(documentRoot);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resource.save(out, null);
		return out.toString(StandardCharsets.UTF_8);
	}

	@Test
	void modelsLoadAndRegister() {
		assertTrue(MedbiqModels.packages().size() >= 20, () -> "packages: " + MedbiqModels.packages().keySet());
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			assertNotNull(type.ePackage(), type.id());
			assertSame(type, DocumentTypes.byId(type.id()));
			assertSame(type, DocumentTypes.of(type.namespace(), type.rootElement()));
		}
		assertSame(HlomDocumentType.INSTANCE, DocumentTypes.all().getFirst());
	}

	@Test
	void schemasCompile() throws Exception {
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			assertNotNull(type.schema(), type.id());
		}
	}

	/** Writes the minimal documents and their problems to target/minimal (the starting point of templates). */
	@Test
	void minimalDocumentsAreRecognizedAndChecked() throws Exception {
		Path folder = Path.of("target", "minimal");
		Files.createDirectories(folder);
		List<String> report = new ArrayList<>();
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			String xml = minimalXml(type);
			Files.writeString(folder.resolve(type.id() + ".xml"), xml);
			DocumentType detected = DocumentTypes.detect(xml);
			assertSame(type, detected, () -> type.id() + ":\n" + xml);
			Result result = DocumentCheck.check(xml, URI.createURI("minimal.xml"));
			assertSame(type, result.type());
			assertNotNull(result.documentRoot(), type.id());
			report.add("== " + type.id() + " (" + result.count(Severity.ERROR) + " errors)");
			result.problems().forEach(p -> report.add("   " + p.range() + " " + p.problem().message() + " " + p.path()));
		}
		Files.writeString(folder.resolve("report.txt"), String.join("\n", report));
		assertFalse(report.isEmpty());
	}

	/** Every bundled sample is a valid document without any problem (errors, warnings or infos). */
	@Test
	void everySampleHasNoProblems() {
		List<String> files = MedbiqSamples.files("samples");
		assertTrue(files.size() >= 20, () -> "samples: " + files);
		List<String> problems = new ArrayList<>();
		for (String file : files) {
			Result result = MedbiqSamples.check(MedbiqSamples.text(file));
			assertTrue(result.type() instanceof MedbiqDocumentType, file);
			result.problems().forEach(p -> problems.add(file + ": " + p.problem().severity() + " " + p.problem().message()
					+ " " + p.path()));
		}
		assertTrue(problems.isEmpty(), () -> String.join("\n", problems));
	}

	/** New documents are valid and keep none of the template's own comments. */
	@Test
	void newDocumentsAreValidWithoutTemplateComments() {
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			String xml = type.newDocumentXml("New document", "en");
			assertFalse(xml.contains("Template of"), () -> type.id() + ":\n" + xml);
			assertFalse(xml.contains("${"), () -> type.id() + ":\n" + xml);
			Result result = DocumentCheck.check(xml, URI.createURI("new.xml"));
			assertEquals(0, result.count(Severity.ERROR), () -> type.id() + ": " + result.problems());
		}
	}

	@Test
	void otherXmlIsNotADocument() {
		assertEquals(null, DocumentTypes.detect("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"/>"));
		assertSame(MedbiqStandards.byId("curriculum-inventory-v10"),
				DocumentTypes.detect("<ci:CurriculumInventory xmlns:ci='http://ns.medbiq.org/curriculuminventory/v10/'>"));
	}
}
