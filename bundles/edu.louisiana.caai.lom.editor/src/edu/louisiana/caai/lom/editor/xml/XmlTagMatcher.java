/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;

/**
 * Highlights the start and end tag of the element whose tag name contains the caret.
 */
final class XmlTagMatcher {

	private final ISourceViewer viewer;
	private final Supplier<IAnnotationModel> annotationModel;
	private final CaretListener caretListener = _ -> update();
	private Annotation[] current = new Annotation[0];
	private XmlStructure structure;
	private long structureStamp = -1;

	XmlTagMatcher(ISourceViewer viewer, Supplier<IAnnotationModel> annotationModel) {
		this.viewer = viewer;
		this.annotationModel = annotationModel;
		viewer.getTextWidget().addCaretListener(caretListener);
	}

	/** Recomputes the highlight for the current caret position. */
	void update() {
		StyledText widget = viewer.getTextWidget();
		IDocument document = viewer.getDocument();
		IAnnotationModel model = annotationModel.get();
		if (widget == null || widget.isDisposed() || document == null
				|| !(model instanceof IAnnotationModelExtension extension)) {
			return;
		}
		int offset = widget.getCaretOffset();
		if (viewer instanceof ITextViewerExtension5 extension5) {
			offset = extension5.widgetOffset2ModelOffset(offset);
		}
		Map<Annotation, Position> added = new HashMap<>();
		XmlStructure.Element element = offset < 0 ? null : structure(document).elementAtTagName(offset);
		if (element != null && element.endTagStart >= 0) {
			added.put(new XmlAnnotations.MatchingTagAnnotation(),
					new Position(element.nameStart(), element.name.length()));
			added.put(new XmlAnnotations.MatchingTagAnnotation(),
					new Position(element.endNameStart(), element.name.length()));
		}
		if (current.length == 0 && added.isEmpty()) {
			return;
		}
		extension.replaceAnnotations(current, added);
		current = added.keySet().toArray(Annotation[]::new);
	}

	private XmlStructure structure(IDocument document) {
		long stamp = document instanceof IDocumentExtension4 extension ? extension.getModificationStamp() : -2;
		if (structure == null || stamp != structureStamp || stamp == -2) {
			structure = XmlStructure.parse(document.get());
			structureStamp = stamp;
		}
		return structure;
	}

	void dispose() {
		StyledText widget = viewer.getTextWidget();
		if (widget != null && !widget.isDisposed()) {
			widget.removeCaretListener(caretListener);
		}
	}
}
