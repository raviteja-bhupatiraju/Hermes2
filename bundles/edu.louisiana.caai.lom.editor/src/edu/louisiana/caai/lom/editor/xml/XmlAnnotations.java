/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.ITextViewerExtension4;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * The annotations of the XML page and their presentation without {@code org.eclipse.ui.editors}: problem
 * annotations (squiggly underline, ruler icon, overview ruler mark, hover) and the matching-tag highlight.
 */
public final class XmlAnnotations {

	private static final String PREFIX = "edu.louisiana.caai.lom.editor.xml.";

	static final String ERROR = PREFIX + "error";
	static final String WARNING = PREFIX + "warning";
	static final String INFO = PREFIX + "info";
	static final String MATCHING_TAG = PREFIX + "matchingTag";

	static final String[] PROBLEM_TYPES = { ERROR, WARNING, INFO };

	private static final String SQUIGGLES = PREFIX + "squiggles";

	/** Background of matching tags (a light tint of the theme's teal). */
	static final RGB MATCHING_TAG_RGB = new RGB(0xC8, 0xE8, 0xE5);

	/** Shared colors backed by the theme's color registry. */
	static final ISharedTextColors COLORS = new ISharedTextColors() {
		@Override
		public Color getColor(RGB rgb) {
			return HlomTheme.color(rgb);
		}

		@Override
		public void dispose() {
			// registry colors are disposed with the display
		}
	};

	/** Access to annotation types, layers and painting. */
	static final IAnnotationAccess ACCESS = new Access();

	private XmlAnnotations() {
	}

	/** The annotation type of a problem severity. */
	static String type(HlomProblem.Severity severity) {
		return switch (severity) {
		case ERROR -> ERROR;
		case WARNING -> WARNING;
		case INFO -> INFO;
		};
	}

	/** The color of an annotation type. */
	static RGB rgb(String type) {
		if (ERROR.equals(type)) {
			return HlomTheme.ACCENT;
		}
		if (WARNING.equals(type)) {
			return HlomTheme.AMBER;
		}
		if (INFO.equals(type)) {
			return HlomTheme.TEAL;
		}
		return MATCHING_TAG_RGB;
	}

	private static int layer(String type) {
		return ERROR.equals(type) ? 6 : WARNING.equals(type) ? 5 : INFO.equals(type) ? 4 : 1;
	}

	/** A problem shown in the XML text. */
	public static final class ProblemAnnotation extends Annotation implements IAnnotationPresentation {

		/** Whether the problem was found in the text itself (rather than in the model). */
		final boolean live;
		/** The problem of the model validation, or {@code null} for problems found in the text. */
		final HlomProblem problem;

		/** @param problem the model problem, or {@code null} for a problem found in the text itself */
		public ProblemAnnotation(HlomProblem.Severity severity, String message, HlomProblem problem) {
			super(type(severity), false, message);
			this.live = problem == null;
			this.problem = problem;
		}

		Image image() {
			String type = getType();
			return HlomTheme.image(ERROR.equals(type) ? "icons/actions/error.png"
					: WARNING.equals(type) ? "icons/actions/warning.png" : "icons/actions/info.png");
		}

		@Override
		public int getLayer() {
			return layer(getType());
		}

		@Override
		public void paint(GC gc, Canvas canvas, Rectangle bounds) {
			Image image = image();
			if (image != null) {
				ImageUtilities.drawImage(image, gc, canvas, bounds, SWT.CENTER, SWT.TOP);
			}
		}
	}

	/** The highlight of a tag matching the one at the caret. */
	static final class MatchingTagAnnotation extends Annotation {
		MatchingTagAnnotation() {
			super(MATCHING_TAG, false, null);
		}
	}

	/** Creates the overview ruler (right side) showing problem marks and a summary header. */
	static OverviewRuler createOverviewRuler() {
		OverviewRuler ruler = new OverviewRuler(ACCESS, 12, COLORS);
		for (String type : PROBLEM_TYPES) {
			ruler.addAnnotationType(type);
			ruler.addHeaderAnnotationType(type);
			ruler.setAnnotationTypeLayer(type, layer(type));
			ruler.setAnnotationTypeColor(type, COLORS.getColor(rgb(type)));
		}
		return ruler;
	}

	/** Creates the ruler column with the problem icons. */
	static AnnotationRulerColumn createRulerColumn() {
		AnnotationRulerColumn column = new AnnotationRulerColumn(16, ACCESS);
		for (String type : PROBLEM_TYPES) {
			column.addAnnotationType(type);
		}
		return column;
	}

	/** Installs the painter of squiggly problem underlines and matching-tag highlights on a viewer. */
	public static AnnotationPainter installPainter(ISourceViewer viewer) {
		AnnotationPainter painter = new AnnotationPainter(viewer, ACCESS);
		painter.addTextStyleStrategy(SQUIGGLES, new AnnotationPainter.UnderlineStrategy(SWT.UNDERLINE_SQUIGGLE));
		for (String type : PROBLEM_TYPES) {
			painter.addAnnotationType(type, SQUIGGLES);
			painter.setAnnotationTypeColor(type, COLORS.getColor(rgb(type)));
		}
		painter.addHighlightAnnotationType(MATCHING_TAG);
		painter.setAnnotationTypeColor(MATCHING_TAG, COLORS.getColor(MATCHING_TAG_RGB));
		if (viewer instanceof ITextViewerExtension2 extension) {
			extension.addPainter(painter);
		}
		if (viewer instanceof ITextViewerExtension4 extension) {
			extension.addTextPresentationListener(painter);
		}
		return painter;
	}

	private static final class Access implements IAnnotationAccess, IAnnotationAccessExtension {

		@Override
		public Object getType(Annotation annotation) {
			return annotation.getType();
		}

		@Override
		public boolean isMultiLine(Annotation annotation) {
			return true;
		}

		@Override
		public boolean isTemporary(Annotation annotation) {
			return true;
		}

		@Override
		public String getTypeLabel(Annotation annotation) {
			String type = annotation.getType();
			return ERROR.equals(type) ? "Error" : WARNING.equals(type) ? "Warning" : INFO.equals(type) ? "Info" : null;
		}

		@Override
		public int getLayer(Annotation annotation) {
			return annotation instanceof IAnnotationPresentation presentation ? presentation.getLayer()
					: layer(annotation.getType());
		}

		@Override
		public void paint(Annotation annotation, GC gc, Canvas canvas, Rectangle bounds) {
			if (annotation instanceof IAnnotationPresentation presentation) {
				presentation.paint(gc, canvas, bounds);
			}
		}

		@Override
		public boolean isPaintable(Annotation annotation) {
			return annotation instanceof IAnnotationPresentation;
		}

		@Override
		public boolean isSubtype(Object annotationType, Object potentialSupertype) {
			return annotationType != null && annotationType.equals(potentialSupertype);
		}

		@Override
		public Object[] getSupertypes(Object annotationType) {
			return new Object[] { annotationType };
		}
	}
}
