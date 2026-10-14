/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * The documents of the Hermes projects with their summary and problems, as validated by the
 * {@link HermesBuilder}. Kept in memory: the builder fills it again after a start.
 */
public final class DocumentIndex {

	/** A document of a project. */
	public record Document(IFile file, LibraryEntry entry) {
	}

	private static final Map<IFile, LibraryEntry> entries = new ConcurrentHashMap<>();
	private static final Set<IProject> indexed = ConcurrentHashMap.newKeySet();
	private static final List<Consumer<Set<IFile>>> listeners = new CopyOnWriteArrayList<>();

	private DocumentIndex() {
	}

	/** The entry of a document, or {@code null} if it isn't (yet) indexed or not an HLOM document. */
	public static LibraryEntry get(IFile file) {
		return entries.get(file);
	}

	/** Whether all documents of a project were indexed since the start. */
	public static boolean isIndexed(IProject project) {
		return indexed.contains(project);
	}

	/** The indexed documents of a project, by path. */
	public static List<Document> documents(IProject project) {
		return entries.entrySet().stream()
				.filter(entry -> project.equals(entry.getKey().getProject()))
				.map(entry -> new Document(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparing(document -> document.file().getFullPath().toString()))
				.toList();
	}

	/** The indexed documents of all projects. */
	public static List<Document> documents() {
		return entries.entrySet().stream()
				.map(entry -> new Document(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparing(document -> document.file().getFullPath().toString()))
				.toList();
	}

	/** The documents identified by an identifier (see {@link DocumentLinks}), such as the competency object of a URI. */
	public static List<Document> identifiedBy(DocumentLinks.Identifier identifier) {
		return documents().stream().filter(document -> document.entry().links().isIdentifiedBy(identifier)).toList();
	}

	/** A link of a document to another one. */
	public record Referrer(Document document, DocumentLinks.Link link) {
	}

	/** The links of the indexed documents to a document with any of the given identifiers. */
	public static List<Referrer> referrers(List<DocumentLinks.Identifier> identifiers, IFile except) {
		DocumentLinks target = new DocumentLinks(identifiers, List.of());
		List<Referrer> referrers = new java.util.ArrayList<>();
		for (Document document : documents()) {
			if (document.file().equals(except)) {
				continue;
			}
			for (DocumentLinks.Link link : document.entry().links().links()) {
				if (target.isIdentifiedBy(link.target())) {
					referrers.add(new Referrer(document, link));
				}
			}
		}
		return referrers;
	}

	/**
	 * Adds a listener told about the files whose entries changed (after each validation run, in the thread of
	 * the builder).
	 */
	public static void addListener(Consumer<Set<IFile>> listener) {
		listeners.add(listener);
	}

	public static void removeListener(Consumer<Set<IFile>> listener) {
		listeners.remove(listener);
	}

	static void put(IFile file, LibraryEntry entry) {
		entries.put(file, entry);
	}

	static void remove(IFile file) {
		entries.remove(file);
	}

	static void markIndexed(IProject project) {
		indexed.add(project);
	}

	/** Forgets the documents of a project (closed, deleted or cleaned). */
	static void forget(IProject project) {
		indexed.remove(project);
		Set<IFile> removed = ConcurrentHashMap.newKeySet();
		entries.keySet().removeIf(file -> project.equals(file.getProject()) && removed.add(file));
		changed(removed);
	}

	static void changed(Set<IFile> files) {
		if (!files.isEmpty()) {
			Set<IFile> copy = Set.copyOf(files);
			listeners.forEach(listener -> listener.accept(copy));
		}
	}
}
