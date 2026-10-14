/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.search.DocumentReplace;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;

/**
 * Replaces the matches of a search in the field values of the documents (Search &gt; Search Documents &gt;
 * <em>Replace...</em>, or <em>Replace All/Selected</em> in the Search view): one text change per document, previewed
 * before it is applied and undoable as a whole (Edit &gt; Undo). Only the changed values of XML documents are
 * rewritten.
 */
public final class ReplaceRefactoring extends Refactoring {

	private final Query query;
	/** The values to change by document (their element paths; all matching values of a document if empty). */
	private final Map<IFile, Set<String>> selection = new LinkedHashMap<>();
	private String replacement = "";
	private final List<Change> changes = new ArrayList<>();

	/**
	 * @param query    the search
	 * @param elements the matched values to change
	 */
	public ReplaceRefactoring(Query query, List<FieldElement> elements) {
		this.query = query;
		for (FieldElement element : elements) {
			selection.computeIfAbsent(element.file(), _ -> new LinkedHashSet<>()).add(element.path());
		}
	}

	public Query query() {
		return query;
	}

	/** The number of documents with values to change. */
	public int documentCount() {
		return selection.size();
	}

	/** The number of values to change. */
	public int valueCount() {
		return selection.values().stream().mapToInt(Set::size).sum();
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement == null ? "" : replacement;
	}

	/** The files of the documents to change. */
	public Set<IFile> files() {
		return selection.keySet();
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.ReplaceRefactoring_Replace, query.describe());
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor monitor) {
		if (selection.isEmpty()) {
			return RefactoringStatus.createFatalErrorStatus(Messages.ReplaceRefactoring_ThereAreNoMatchesTo);
		}
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor monitor) {
		RefactoringStatus status = new RefactoringStatus();
		changes.clear();
		SubMonitor progress = SubMonitor.convert(monitor, Messages.ReplaceRefactoring_ComputingTheReplacements, selection.size());
		for (Map.Entry<IFile, Set<String>> entry : selection.entrySet()) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}
			IFile file = entry.getKey();
			Path location = HermesWorkspace.location(file);
			String name = file.getFullPath().makeRelative().toString();
			if (location == null || !file.exists()) {
				status.addWarning(NLS.bind(Messages.ReplaceRefactoring_NoLongerExists, name));
				continue;
			}
			String text;
			try {
				text = Files.readString(location, StandardCharsets.UTF_8);
			} catch (IOException e) {
				status.addError(NLS.bind(Messages.ReplaceRefactoring_CanTBeRead, name, e.getMessage()));
				continue;
			}
			String bom = text.startsWith("﻿") ? "﻿" : "";
			DocumentReplace.Result result = DocumentReplace.replace(text.substring(bom.length()),
					URI.createFileURI(location.toString()), query, replacement, entry.getValue()::contains);
			for (String skipped : result.skipped()) {
				status.addWarning(name + ": " + skipped);
			}
			if (!result.changes().isEmpty()) {
				TextFileChange change = new TextFileChange(name + " (" + result.changes().size()
						+ (result.changes().size() == 1 ? " value)" : " values)"), file);
				change.setEdit(new ReplaceEdit(0, text.length(), bom + result.text()));
				change.setTextType(file.getFileExtension() == null ? "xml" : file.getFileExtension());
				changes.add(change);
			}
			progress.worked(1);
		}
		if (changes.isEmpty() && !status.hasError()) {
			status.addFatalError(Messages.ReplaceRefactoring_NoValueChangesTheReplacement);
		}
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor monitor) {
		CompositeChange change = new CompositeChange(getName(), changes.toArray(Change[]::new));
		change.markAsSynthetic();
		return change;
	}
}
