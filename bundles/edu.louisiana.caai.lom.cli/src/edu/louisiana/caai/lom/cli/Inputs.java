/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.louisiana.caai.lom.cli.Arguments.UsageException;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;

/** The documents named on the command line: files as given, folders searched for HLOM documents (XML and JSON). */
final class Inputs {

	private Inputs() {
	}

	/** The documents of the operands, in order and without duplicates. */
	static List<Path> documents(List<String> operands) throws UsageException, IOException {
		if (operands.isEmpty()) {
			throw new UsageException("No files or folders given.");
		}
		Set<Path> documents = new LinkedHashSet<>();
		for (String operand : operands) {
			Path path = Path.of(operand);
			if (Files.isDirectory(path)) {
				for (Path candidate : HlomLibrary.findCandidates(path, () -> false)) {
					if (HlomLibrary.isHlomDocument(candidate)) {
						documents.add(candidate.normalize());
					}
				}
			} else if (Files.isRegularFile(path)) {
				documents.add(path.normalize());
			} else {
				throw new UsageException("No such file or folder: " + operand);
			}
		}
		return List.copyOf(documents);
	}

	/** Reads a document as text (UTF-8; a byte order mark is removed). */
	static String read(Path file) throws IOException {
		String text = Files.readString(file, StandardCharsets.UTF_8);
		return text.startsWith("﻿") ? text.substring(1) : text;
	}

	/** Whether a path names a JSON document by its extension. */
	static boolean isJsonName(Path file) {
		return file.getFileName() != null && file.getFileName().toString().toLowerCase(java.util.Locale.ROOT).endsWith(".json");
	}
}
