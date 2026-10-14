/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Access to the test documents shipped in this bundle's {@code resources/} folder.
 */
final class TestResources {

	/** The corrected sample documents, all of which must be valid. */
	static final List<String> SAMPLES = List.of("course-with-multiple-credit-types.xml",
			"insulin-pump-therapy-course.xml", "medical-image-asset.xml");

	private TestResources() {
	}

	/** File URI of a corrected sample in {@code resources/samples/}. */
	static URI sample(String name) {
		return resource("resources/samples/" + name);
	}

	/** File URI of a sample transcribed verbatim from the specification, in {@code resources/spec-samples/}. */
	static URI specSample(String name) {
		return resource("resources/spec-samples/" + name);
	}

	/** Resolves a bundle entry to a file URI (extracting it if the bundle is a jar). */
	static URI resource(String path) {
		Bundle bundle = FrameworkUtil.getBundle(TestResources.class);
		assertNotNull(bundle, "tests must run inside OSGi");
		URL entry = bundle.getEntry(path);
		assertNotNull(entry, "missing test resource " + path);
		try {
			File file = new File(FileLocator.toFileURL(entry).getPath());
			return URI.createFileURI(file.getAbsolutePath());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/** The problems of the given severity. */
	static List<HlomProblem> withSeverity(List<HlomProblem> problems, Severity severity) {
		return problems.stream().filter(p -> p.severity() == severity).toList();
	}

	/** A readable listing of problems for assertion messages. */
	static String describe(List<HlomProblem> problems) {
		return problems.stream()
				.map(p -> p.severity() + " " + p.origin() + " " + p.path() + ":" + p.line() + " " + p.message())
				.collect(Collectors.joining("\n", "\n", ""));
	}
}
