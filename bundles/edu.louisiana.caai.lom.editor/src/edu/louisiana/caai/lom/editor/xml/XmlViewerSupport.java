/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.type.DocumentType;

/**
 * The editing support of the XML page that is independent of the workbench, so that it can be used by the
 * {@link XmlSourceEditor} and by plain viewers alike: rulers, problem annotations and live checking, folding,
 * matching tags, formatting and comment toggling. The commands and their key bindings are the editor's actions.
 */
public final class XmlViewerSupport {

	private final ProjectionViewer viewer;
	private final Supplier<IAnnotationModel> annotationModel;
	private final AnnotationPainter painter;
	private final ProjectionSupport projection;
	private final XmlFolding folding;
	private final XmlTagMatcher tagMatcher;
	private final XmlChecker checker;

	/** Creates the vertical ruler: problem icons and line numbers. */
	public static CompositeRuler createVerticalRuler() {
		CompositeRuler ruler = new CompositeRuler();
		ruler.addDecorator(0, XmlAnnotations.createRulerColumn());
		LineNumberRulerColumn lineNumbers = new LineNumberRulerColumn();
		lineNumbers.setForeground(XmlColors.color(XmlColors.LINE_NUMBER));
		ruler.addDecorator(1, lineNumbers);
		return ruler;
	}

	/** Creates the viewer with an overview ruler on the right. */
	static ProjectionViewer createViewer(Composite parent, IVerticalRuler ruler, int styles) {
		return new ProjectionViewer(parent, ruler, XmlAnnotations.createOverviewRuler(), true, styles);
	}

	/**
	 * Installs the support on a configured viewer.
	 *
	 * @param annotationModel the annotation model of the document (not the viewer's visual model)
	 * @param type            the type of the document
	 */
	XmlViewerSupport(ProjectionViewer viewer, Supplier<IAnnotationModel> annotationModel, Supplier<DocumentType> type) {
		this.viewer = viewer;
		this.annotationModel = annotationModel;
		painter = XmlAnnotations.installPainter(viewer);
		projection = new ProjectionSupport(viewer, XmlAnnotations.ACCESS, XmlAnnotations.COLORS);
		for (String problemType : XmlAnnotations.PROBLEM_TYPES) {
			projection.addSummarizableAnnotationType(problemType);
		}
		projection.setHoverControlCreator(parent -> new org.eclipse.jface.text.DefaultInformationControl(parent));
		projection.install();
		viewer.doOperation(ProjectionViewer.TOGGLE);
		folding = new XmlFolding(viewer);
		tagMatcher = new XmlTagMatcher(viewer, annotationModel);
		checker = new XmlChecker(viewer.getTextWidget().getDisplay(), viewer::getDocument, annotationModel, type,
				folding::update);
	}

	/** To be called on every change of the document (including replacing all text). */
	void documentChanged() {
		checker.schedule();
	}

	/** Shows the problems of the model validation. */
	void setProblems(List<HlomProblem> problems) {
		checker.setProblems(problems);
	}

	/** Formats the whole document as one undoable change, keeping the caret line and scroll position. */
	void format() {
		IDocument document = viewer.getDocument();
		if (document == null || !viewer.isEditable()) {
			return;
		}
		String text = document.get();
		String formatted = XmlFormatter.format(text);
		if (formatted.equals(text)) {
			return;
		}
		try {
			ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();
			int caretLine = document.getLineOfOffset(Math.min(selection.getOffset(), document.getLength()));
			IRegion caretLineInfo = document.getLineInformation(caretLine);
			int column = selection.getOffset() - caretLineInfo.getOffset();
			int topIndex = viewer.getTopIndex();
			replaceMinimal(document, text, formatted);
			int line = Math.min(caretLine, document.getNumberOfLines() - 1);
			IRegion info = document.getLineInformation(line);
			String lineText = document.get(info.getOffset(), info.getLength());
			int indent = lineText.length() - lineText.stripLeading().length();
			viewer.setSelectedRange(info.getOffset() + Math.clamp(column, indent, info.getLength()), 0);
			viewer.setTopIndex(topIndex);
		} catch (BadLocationException _) {
			// cannot happen: offsets come from the document
		}
	}

	/** Replaces the differing middle part of a text, as one undoable change. */
	private void replaceMinimal(IDocument document, String text, String replacement) throws BadLocationException {
		int prefix = 0;
		int max = Math.min(text.length(), replacement.length());
		while (prefix < max && text.charAt(prefix) == replacement.charAt(prefix)) {
			prefix++;
		}
		int suffix = 0;
		while (suffix < max - prefix
				&& text.charAt(text.length() - 1 - suffix) == replacement.charAt(replacement.length() - 1 - suffix)) {
			suffix++;
		}
		IRewriteTarget target = viewer instanceof ITextViewerExtension extension ? extension.getRewriteTarget() : null;
		if (target != null) {
			target.beginCompoundChange();
		}
		try {
			document.replace(prefix, text.length() - suffix - prefix,
					replacement.substring(prefix, replacement.length() - suffix));
		} finally {
			if (target != null) {
				target.endCompoundChange();
			}
		}
	}

	/** Comments or uncomments the lines of the selection. */
	void toggleComment() {
		IDocument document = viewer.getDocument();
		if (document == null || !viewer.isEditable()) {
			return;
		}
		try {
			ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();
			int startLine = document.getLineOfOffset(selection.getOffset());
			int endOffset = selection.getOffset() + selection.getLength();
			int endLine = document.getLineOfOffset(endOffset);
			if (endLine > startLine && document.getLineOffset(endLine) == endOffset) {
				endLine--;
			}
			int start = document.getLineOffset(startLine);
			IRegion last = document.getLineInformation(endLine);
			int end = last.getOffset() + last.getLength();
			String block = document.get(start, end - start);
			if (block.isBlank()) {
				return;
			}
			String toggled = XmlFormatter.toggleComment(block);
			document.replace(start, end - start, toggled);
			viewer.setSelectedRange(start, toggled.length());
		} catch (BadLocationException _) {
			// cannot happen: offsets come from the document
		}
	}

	void dispose() {
		checker.dispose();
		tagMatcher.dispose();
		projection.dispose();
		painter.deactivate(false);
	}

	/** The messages of the problems shown, or {@code null} while the current text is not checked yet. */
	List<String> checkedProblems() {
		return checker.checkedProblems();
	}

	/** The checker (package visibility for tests). */
	XmlChecker checker() {
		return checker;
	}

	IAnnotationModel annotationModel() {
		return annotationModel.get();
	}
}
