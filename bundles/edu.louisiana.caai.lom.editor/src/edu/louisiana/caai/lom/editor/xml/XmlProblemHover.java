/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * Shows the messages of the problems at a line (vertical and overview ruler) or at an offset (text), in a
 * wrapping information control styled in the theme colors.
 */
final class XmlProblemHover implements IAnnotationHover, ITextHover {

	/** The type of the edited document (for the documentation of elements). */
	private final java.util.function.Supplier<edu.louisiana.caai.lom.model.support.type.DocumentType> type;

	XmlProblemHover() {
		this(() -> edu.louisiana.caai.lom.model.support.type.HlomDocumentType.INSTANCE);
	}

	XmlProblemHover(java.util.function.Supplier<edu.louisiana.caai.lom.model.support.type.DocumentType> type) {
		this.type = type;
	}

	/** Background of hovers and of the content assist documentation popup. */
	private static final org.eclipse.swt.graphics.RGB HOVER_BACKGROUND = new org.eclipse.swt.graphics.RGB(0xF8, 0xFA,
			0xFC);

	/** Creates wrapping, HTML capable information controls in the theme colors. */
	static IInformationControlCreator informationControlCreator() {
		return parent -> {
			DefaultInformationControl control = new DefaultInformationControl(parent, (String) null);
			control.setBackgroundColor(HlomTheme.color(HOVER_BACKGROUND));
			control.setForegroundColor(HlomTheme.color(HlomTheme.INK));
			return control;
		};
	}

	/** Escapes text for the HTML capable information control. */
	static String escape(String text) {
		return text == null ? null : text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	@Override
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		IDocument document = sourceViewer.getDocument();
		try {
			IRegion line = document.getLineInformation(lineNumber);
			return format(sourceViewer.getAnnotationModel(), position -> {
				try {
					return document.getLineOfOffset(position.offset) == lineNumber
							|| position.overlapsWith(line.getOffset(), line.getLength());
				} catch (BadLocationException _) {
					return false;
				}
			});
		} catch (BadLocationException _) {
			return null;
		}
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (!(textViewer instanceof ISourceViewer sourceViewer)) {
			return null;
		}
		String problems = format(sourceViewer.getAnnotationModel(), position -> position.offset <= hoverRegion.getOffset()
				&& hoverRegion.getOffset() <= position.offset + position.length);
		if (problems != null || textViewer.getDocument() == null) {
			return problems;
		}
		// Without problems: the documentation of the element, attribute or code under the mouse.
		String text = textViewer.getDocument().get();
		return ScopedValue.where(XmlContentModel.TYPE, type.get())
				.call(() -> XmlDocumentation.at(text, hoverRegion.getOffset()));
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 0);
	}

	private static String format(IAnnotationModel model, Predicate<Position> filter) {
		if (model == null) {
			return null;
		}
		List<String> messages = new ArrayList<>();
		for (Iterator<Annotation> it = model.getAnnotationIterator(); it.hasNext();) {
			Annotation annotation = it.next();
			if (annotation instanceof XmlAnnotations.ProblemAnnotation problem && problem.getText() != null) {
				Position position = model.getPosition(annotation);
				if (position != null && filter.test(position)) {
					String label = XmlAnnotations.ERROR.equals(problem.getType()) ? "Error"
							: XmlAnnotations.WARNING.equals(problem.getType()) ? "Warning" : "Info";
					String text = "<b>" + label + "</b> " + escape(problem.getText());
					if (!messages.contains(text)) {
						messages.add(text);
					}
				}
			}
		}
		return messages.isEmpty() ? null : String.join("<br>", messages);
	}
}
