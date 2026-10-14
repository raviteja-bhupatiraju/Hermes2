/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.osgi.util.NLS;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;

/**
 * Search &gt; Referring Documents: the documents of the projects that name a document by one of its identifiers
 * (the competency frameworks including a competency object, the records relating to a learning object, the
 * curriculum inventories with a competency object), in the Search view.
 */
public final class ReferringDocumentsQuery implements ISearchQuery {

	private final String name;
	private final IFile file;
	private final List<DocumentLinks.Identifier> identifiers;
	private final HermesSearchResult result = new HermesSearchResult(this);

	/**
	 * @param name        the name of the document, for the label of the search
	 * @param file        the document's file (left out of the results), or {@code null}
	 * @param identifiers the identifiers of the document
	 */
	public ReferringDocumentsQuery(String name, IFile file, List<DocumentLinks.Identifier> identifiers) {
		this.name = name;
		this.file = file;
		this.identifiers = List.copyOf(identifiers);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		result.removeAll();
		int order = 0;
		for (DocumentIndex.Referrer referrer : DocumentIndex.referrers(identifiers, file)) {
			String text = referrer.link().target().label();
			FieldElement element = new FieldElement(referrer.document().file(), referrer.link().path(),
					referrer.link().label(), text, order++);
			int start = text.lastIndexOf(referrer.link().target().entry());
			result.addMatch(new FieldMatch(element, Math.max(0, start), referrer.link().target().entry().length()));
		}
		return Status.OK_STATUS;
	}

	@Override
	public String getLabel() {
		return NLS.bind(Messages.ReferringDocumentsQuery_DocumentsReferringTo, name);
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ISearchResult getSearchResult() {
		return result;
	}
}
