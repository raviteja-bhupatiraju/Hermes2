/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.core.resources.IFile;

/**
 * A field value of a document with search matches: the element of the matches in the search result.
 *
 * @param file  the document
 * @param path  the element path of the value (see {@link edu.louisiana.caai.lom.model.support.ElementPath})
 * @param label the field label, such as {@code General › Keyword}
 * @param text  the value
 * @param order the position of the value in the document (for sorting)
 */
public record FieldElement(IFile file, String path, String label, String text, int order) {
}
