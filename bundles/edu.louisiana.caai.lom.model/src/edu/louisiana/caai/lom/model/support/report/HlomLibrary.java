/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.lom.model.support.json.HlomJson;
import edu.louisiana.caai.lom.model.support.json.Json;

/**
 * Finds, loads and validates the HLOM records in a folder tree. OSGi-free, so that it backs the library view as
 * well as batch reports and tests.
 */
public final class HlomLibrary {

	private static volatile CompletenessScorer completenessScorer;

	private HlomLibrary() {
	}

	/** Sets the scorer used for {@link LibraryEntry#completeness()}, or {@code null} for none. */
	public static void setCompletenessScorer(CompletenessScorer scorer) {
		completenessScorer = scorer;
	}

	/** The completeness scorer, or {@code null}. */
	public static CompletenessScorer getCompletenessScorer() {
		return completenessScorer;
	}

	/** Whether the file has an extension HLOM records are stored with ({@code .xml}, {@code .lom}, {@code .json}). */
	public static boolean hasHlomExtension(Path file) {
		String name = file.getFileName() == null ? "" : file.getFileName().toString().toLowerCase(Locale.ROOT);
		return name.endsWith(".xml") || name.endsWith(".lom") || name.endsWith(".json");
	}

	/**
	 * Lists the files below a folder that may be HLOM records, judged by extension only. Hidden folders and
	 * build output ({@code target}, {@code bin}, {@code node_modules}) are skipped.
	 *
	 * @param canceled polled while walking; the walk stops early when it returns {@code true}
	 */
	public static List<Path> findCandidates(Path folder, BooleanSupplier canceled) throws IOException {
		List<Path> result = new ArrayList<>();
		if (!Files.isDirectory(folder)) {
			return result;
		}
		Files.walkFileTree(folder, new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				if (canceled.getAsBoolean()) {
					return FileVisitResult.TERMINATE;
				}
				String name = dir.getFileName() == null ? "" : dir.getFileName().toString();
				boolean skip = !dir.equals(folder) && (name.startsWith(".") || name.equals("target")
						|| name.equals("bin") || name.equals("node_modules"));
				return skip ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				if (attrs.isRegularFile() && hasHlomExtension(file)) {
					result.add(file);
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				return FileVisitResult.CONTINUE;
			}
		});
		Collections.sort(result);
		return result;
	}

	/**
	 * Quick check whether a file is a MedBiquitous document of a registered {@link DocumentType}: its root element
	 * is the root element of a type, in the type's namespace (such as {@code lom} in the IEEE LOM namespace). Only
	 * the beginning of the file is parsed; DTDs and external entities are not processed. A JSON document is one if
	 * it is an object whose member is the root element of a type stored as JSON (such as {@code "lom"}).
	 */
	public static boolean isHlomDocument(Path file) {
		return documentType(file) != null;
	}

	/** The type of a document file (see {@link #isHlomDocument(Path)}), or {@code null}. */
	public static DocumentType documentType(Path file) {
		if (isJsonDocument(file)) {
			try {
				if (Json.parse(Files.readString(file, StandardCharsets.UTF_8)) instanceof Json.JObject document) {
					for (DocumentType type : DocumentTypes.all()) {
						if (type.supportsJson() && document.get(type.rootElement()) instanceof Json.JObject) {
							return type;
						}
					}
				}
			} catch (IOException | Json.JsonException | RuntimeException _) {
				return null;
			}
			return null;
		}
		XMLInputFactory factory = XMLInputFactory.newFactory();
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		try (InputStream in = Files.newInputStream(file)) {
			XMLStreamReader reader = factory.createXMLStreamReader(in);
			try {
				while (reader.hasNext()) {
					if (reader.next() == XMLStreamConstants.START_ELEMENT) {
						String namespace = reader.getNamespaceURI();
						return DocumentTypes.of(namespace == null ? "" : namespace, reader.getLocalName());
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException | XMLStreamException | RuntimeException _) {
			return null;
		}
		return null;
	}

	/** Whether a file holds JSON text (its first character is <code>{</code>). */
	private static boolean isJsonDocument(Path file) {
		try (InputStream in = Files.newInputStream(file)) {
			byte[] start = in.readNBytes(64);
			return HlomJson.looksLikeJson(start, start.length);
		} catch (IOException _) {
			return false;
		}
	}

	/**
	 * Loads, validates and summarizes a record. Never throws: unreadable files become entries with errors. The
	 * problems are those of {@link DocumentCheck}, with lines and columns in the file as written (XML or JSON).
	 */
	public static LibraryEntry load(Path file) {
		String text;
		try {
			text = Files.readString(file, StandardCharsets.UTF_8);
		} catch (IOException | RuntimeException e) {
			return unreadable(file, e);
		}
		if (text.startsWith("﻿")) {
			text = text.substring(1);
		}
		DocumentCheck.Result result = DocumentCheck.check(text, URI.createFileURI(file.toAbsolutePath().toString()));
		Resource resource = result.resource();
		List<HlomProblem> problems = new ArrayList<>();
		for (DocumentCheck.Located located : result.problems()) {
			HlomProblem problem = located.problem();
			int line = located.range() == null ? problem.line() : located.range().line();
			int column = located.range() == null ? problem.column() : located.range().column();
			problems.add(new HlomProblem(problem.severity(), problem.origin(), problem.message(), null, problem.path(),
					line, column));
		}
		DocumentRoot root = result.root();
		EObject documentRoot = result.documentRoot();
		int completeness = -1;
		CompletenessScorer scorer = completenessScorer;
		if (scorer != null && root != null) {
			try {
				completeness = Math.clamp(scorer.score(root), 0, 100);
			} catch (RuntimeException _) {
				completeness = -1;
			}
		}
		Instant modified = null;
		long size = 0;
		try {
			modified = Files.getLastModifiedTime(file).toInstant();
			size = Files.size(file);
		} catch (IOException _) {
			// Unknown.
		}
		HlomSummary summary = root != null || documentRoot == null ? HlomSummary.of(root)
				: HlomSummary.basic(result.type().title(documentRoot), result.type().description(documentRoot));
		DocumentLinks links = DocumentLinks.of(result.type(), documentRoot);
		resource.unload();
		return new LibraryEntry(file, summary, List.copyOf(problems), modified, size, completeness, result.type(),
				links);
	}

	/** An EMF load message without the location decorations ({@code (uri, line, column)}), which the problem records. */
	public static String loadMessage(String message) {
		if (message == null) {
			return "The document could not be read.";
		}
		// SAXParseException#toString(): "...publicId: ...; systemId: ...; lineNumber: 1; columnNumber: 52; text"
		String text = message.replaceFirst("(?s)^.*?columnNumber:\\s*-?\\d+;\\s*", "");
		return text.replaceFirst("\\s*\\([a-zA-Z][a-zA-Z0-9+.-]*:[^()]*,\\s*-?\\d+,\\s*-?\\d+\\)\\s*$", "");
	}

	/** The entry of a file that could not be read. */
	private static LibraryEntry unreadable(Path file, Exception e) {
		HlomProblem problem = new HlomProblem(Severity.ERROR, Origin.SCHEMA, "The file could not be read: "
				+ e.getMessage(), null, null, -1, -1);
		return new LibraryEntry(file, HlomSummary.of((DocumentRoot) null), List.of(problem), null, 0, -1, null);
	}

	/** Finds and loads all records below a folder. */
	public static List<LibraryEntry> scan(Path folder) throws IOException {
		List<LibraryEntry> entries = new ArrayList<>();
		for (Path file : findCandidates(folder, () -> false)) {
			if (isHlomDocument(file)) {
				entries.add(load(file));
			}
		}
		return entries;
	}
}
