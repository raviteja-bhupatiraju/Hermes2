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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.References.Occurrence;
import edu.louisiana.caai.medbiq.model.References.Target;

/**
 * Search &gt; References (Ctrl+Shift+G): the references to an element of a document (such as the sequence blocks
 * referencing an event), in the Search view. The document is searched as it is edited.
 */
public final class ReferencesSearchQuery implements ISearchQuery {

	private final IFile file;
	private final EObject root;
	private final Target target;
	private final HermesSearchResult result = new HermesSearchResult(this);

	/**
	 * @param file   the document (its workspace file, for opening the matches)
	 * @param root   the root element of the document
	 * @param target the referenced element
	 */
	public ReferencesSearchQuery(IFile file, EObject root, Target target) {
		this.file = file;
		this.root = root;
		this.target = target;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		result.removeAll();
		List<Occurrence> occurrences = References.referencesTo(root, target);
		int order = 0;
		for (Occurrence occurrence : occurrences) {
			EObject at = occurrence.element() != null ? occurrence.element() : occurrence.owner();
			String path = ElementPath.of(at).toString();
			String owner = References.elementName(occurrence.owner());
			String label = (owner == null ? "" : FieldLabels.words(owner) + " › ")
					+ FieldLabels.words(occurrence.kind().element());
			FieldElement element = new FieldElement(file, path, label, occurrence.text(), order++);
			int start = Math.max(0, occurrence.text().lastIndexOf(target.key()));
			result.addMatch(new FieldMatch(element, start, target.key().length()));
		}
		return Status.OK_STATUS;
	}

	@Override
	public String getLabel() {
		return NLS.bind(Messages.ReferencesSearchQuery_ReferencesToIn, new Object[] { target.kind().label(), target.label(), file.getName() });
	}

	@Override
	public boolean canRerun() {
		return false;
	}

	@Override
	public boolean canRunInBackground() {
		return false;
	}

	@Override
	public ISearchResult getSearchResult() {
		return result;
	}
}
