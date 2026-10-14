/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.search.ui.text.Match;

/** A match in a field value; offset and length are relative to {@link FieldElement#text()}. */
public final class FieldMatch extends Match {

	public FieldMatch(FieldElement element, int offset, int length) {
		super(element, UNIT_CHARACTER, offset, length);
	}

	@Override
	public FieldElement getElement() {
		return (FieldElement) super.getElement();
	}
}
