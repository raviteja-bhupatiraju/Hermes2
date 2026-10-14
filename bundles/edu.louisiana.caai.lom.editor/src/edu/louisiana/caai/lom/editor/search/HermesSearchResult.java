/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;

import edu.louisiana.caai.lom.editor.HlomTheme;

/** The result of a {@link HermesSearchQuery}: matches in field values, grouped by {@link FieldElement}. */
public final class HermesSearchResult extends AbstractTextSearchResult implements IFileMatchAdapter {

	private final ISearchQuery query;

	HermesSearchResult(ISearchQuery query) {
		this.query = query;
	}

	@Override
	public String getLabel() {
		int count = getMatchCount();
		int documents = (int) Arrays.stream(getElements()).map(e -> ((FieldElement) e).file()).distinct().count();
		return query.getLabel() + " – " + count + (count == 1 ? " match" : " matches") + " in " + documents
				+ (documents == 1 ? " document" : " documents");
	}

	@Override
	public String getTooltip() {
		return getLabel();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.createFromImage(HlomTheme.image("icons/actions/lookup.png"));
	}

	@Override
	public ISearchQuery getQuery() {
		return query;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return null;
	}

	@Override
	public Match[] computeContainedMatches(AbstractTextSearchResult result, IFile file) {
		return Arrays.stream(getElements())
				.filter(element -> ((FieldElement) element).file().equals(file))
				.flatMap(element -> Arrays.stream(getMatches(element)))
				.toArray(Match[]::new);
	}

	@Override
	public IFile getFile(Object element) {
		return element instanceof FieldElement field ? field.file() : null;
	}
}
