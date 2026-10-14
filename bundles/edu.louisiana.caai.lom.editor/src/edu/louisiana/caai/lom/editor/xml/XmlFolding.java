/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

/**
 * Folding of the XML page: elements and comments spanning several lines can be collapsed. Regions that still
 * exist after a change keep their collapsed state.
 */
final class XmlFolding {

	private final ProjectionViewer viewer;

	XmlFolding(ProjectionViewer viewer) {
		this.viewer = viewer;
	}

	/** Updates the folding regions from the structure of the current document text. */
	void update(XmlStructure structure) {
		ProjectionAnnotationModel model = viewer.getProjectionAnnotationModel();
		IDocument document = viewer.getDocument();
		if (model == null || document == null) {
			return;
		}
		Set<Position> wanted = new LinkedHashSet<>();
		for (XmlStructure.Element element : structure.elements) {
			if (element.end > 0 && !element.empty) {
				add(document, element.start, element.end, wanted);
			}
		}
		for (XmlStructure.Range comment : structure.comments) {
			add(document, comment.offset(), comment.end(), wanted);
		}
		List<Annotation> deletions = new ArrayList<>();
		for (Iterator<Annotation> it = model.getAnnotationIterator(); it.hasNext();) {
			Annotation annotation = it.next();
			if (annotation instanceof ProjectionAnnotation) {
				Position position = model.getPosition(annotation);
				if (position == null || position.isDeleted() || !wanted.remove(new Position(position.offset,
						position.length))) {
					deletions.add(annotation);
				}
			}
		}
		Map<Annotation, Position> additions = new HashMap<>();
		for (Position position : wanted) {
			additions.put(new ProjectionAnnotation(), position);
		}
		if (!deletions.isEmpty() || !additions.isEmpty()) {
			model.modifyAnnotations(deletions.toArray(Annotation[]::new), additions, null);
		}
	}

	/** Adds the whole-line region from the line of {@code start} to the line of {@code end - 1}, if several. */
	private static void add(IDocument document, int start, int end, Set<Position> regions) {
		try {
			if (end > document.getLength()) {
				return;
			}
			int startLine = document.getLineOfOffset(start);
			int endLine = document.getLineOfOffset(Math.max(start, end - 1));
			if (endLine > startLine) {
				int offset = document.getLineOffset(startLine);
				int regionEnd = document.getLineOffset(endLine) + document.getLineLength(endLine);
				regions.add(new Position(offset, regionEnd - offset));
			}
		} catch (BadLocationException _) {
			// outdated structure: ignore
		}
	}
}
