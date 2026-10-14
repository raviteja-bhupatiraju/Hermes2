/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/** Access to the samples and templates of the MedBiquitous standards bundle, and checks of documents. */
final class MedbiqSamples {

	private static final String BUNDLE = "edu.louisiana.caai.medbiq.model";

	private MedbiqSamples() {
	}

	private static Bundle bundle() {
		Bundle bundle = FrameworkUtil.getBundle(MedbiqStandards.class);
		assertNotNull(bundle, BUNDLE);
		return bundle;
	}

	/** The text of a file of the standards bundle, such as {@code samples/curriculum-inventory/x.xml}. */
	static String text(String path) {
		Bundle bundle = bundle();
		URL entry = bundle.getEntry(path);
		assertNotNull(entry, "missing " + path);
		try (InputStream in = entry.openStream()) {
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/** The paths of the XML files in a folder of the standards bundle (such as {@code samples/curriculum-inventory}). */
	static List<String> files(String folder) {
		Bundle bundle = bundle();
		List<String> paths = new ArrayList<>();
		Enumeration<URL> entries = bundle.findEntries(folder, "*.xml", true);
		for (URL entry : entries == null ? Collections.<URL>emptyList() : Collections.list(entries)) {
			paths.add(entry.getPath().replaceFirst("^/", ""));
		}
		Collections.sort(paths);
		return paths;
	}

	/** A new document of a type from its template. */
	static String template(String typeId, String title) {
		MedbiqStandards.init();
		MedbiqDocumentType type = MedbiqStandards.byId(typeId);
		assertNotNull(type, typeId);
		return type.newDocumentXml(title, "en");
	}

	/** Checks a document's text like the editor does. */
	static Result check(String text) {
		MedbiqStandards.init();
		return DocumentCheck.check(text, URI.createURI("test.xml"));
	}

	/** Asserts that a document has no errors (warnings are allowed). */
	static Result assertValid(String name, String text) {
		Result result = check(text);
		String errors = result.problems().stream().filter(p -> p.problem().severity() == Severity.ERROR)
				.map(p -> p.range() + " " + p.problem().message() + " " + p.path()).collect(Collectors.joining("\n"));
		assertTrue(errors.isEmpty(), () -> name + ":\n" + errors);
		return result;
	}

	/** The messages of the problems of a severity. */
	static List<String> messages(Result result, Severity severity) {
		return result.problems().stream().filter(p -> p.problem().severity() == severity)
				.map(p -> p.problem().message()).toList();
	}
}
