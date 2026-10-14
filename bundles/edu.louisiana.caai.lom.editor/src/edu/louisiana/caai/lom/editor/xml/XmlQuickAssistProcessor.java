/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.editor.quickfix.QuickFixImages;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixes;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;

/**
 * Quick fixes in the XML text (Ctrl+1): the fixes of the model problems shown on the caret's line, the same ones
 * the Validation view offers. They change the model through the enclosing editor; the XML page then shows the
 * changed document.
 */
final class XmlQuickAssistProcessor implements IQuickAssistProcessor {

	private final Supplier<XmlSourceEditor.QuickFixHandler> handler;

	XmlQuickAssistProcessor(Supplier<XmlSourceEditor.QuickFixHandler> handler) {
		this.handler = handler;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public boolean canFix(Annotation annotation) {
		return annotation instanceof XmlAnnotations.ProblemAnnotation problem && problem.problem != null
				&& !QuickFixes.offered(problem.problem).isEmpty();
	}

	@Override
	public boolean canAssist(IQuickAssistInvocationContext context) {
		return false;
	}

	@Override
	public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext context) {
		XmlSourceEditor.QuickFixHandler fixes = handler.get();
		ISourceViewer viewer = context.getSourceViewer();
		if (fixes == null || viewer == null) {
			return null;
		}
		List<HlomProblem> problems = problemsAt(viewer, context.getOffset());
		List<ICompletionProposal> proposals = new ArrayList<>();
		if (problems.isEmpty()) {
			return null;
		}
		if (!fixes.isSynchronized()) {
			proposals.add(new Proposal(Messages.XmlQuickAssistProcessor_ApplyTheXMLChangesTo,
					Messages.XmlQuickAssistProcessor_TheProblemsReferToThe,
					QuickFixImages.navigate(), fixes::synchronize));
			return proposals.toArray(ICompletionProposal[]::new);
		}
		for (HlomProblem problem : problems) {
			for (HlomFix fix : QuickFixes.offered(problem)) {
				String label = problems.size() > 1 ? fix.label() + "  (" + shorten(problem.message()) + ")" : fix.label();
				proposals.add(new Proposal(label, NLS.bind(Messages.XmlQuickAssistProcessor_Problem, fix.description(), problem.message()),
						fix.kind() == HlomFix.Kind.NAVIGATE ? QuickFixImages.navigate() : QuickFixImages.bulb(),
						() -> fixes.apply(problem, fix)));
			}
		}
		return proposals.isEmpty() ? null : proposals.toArray(ICompletionProposal[]::new);
	}

	/** The model problems annotated on the line of an offset. */
	private static List<HlomProblem> problemsAt(ISourceViewer viewer, int offset) {
		List<HlomProblem> problems = new ArrayList<>();
		IAnnotationModel model = viewer.getAnnotationModel();
		IDocument document = viewer.getDocument();
		if (model == null || document == null) {
			return problems;
		}
		try {
			int line = document.getLineOfOffset(offset);
			for (Iterator<Annotation> it = model.getAnnotationIterator(); it.hasNext();) {
				if (it.next() instanceof XmlAnnotations.ProblemAnnotation annotation && annotation.problem != null) {
					Position position = model.getPosition(annotation);
					if (position != null && !position.isDeleted() && (position.includes(offset)
							|| document.getLineOfOffset(position.getOffset()) == line)
							&& !problems.contains(annotation.problem)) {
						problems.add(annotation.problem);
					}
				}
			}
		} catch (BadLocationException _) {
			// no problems at an invalid offset
		}
		return problems;
	}

	private static String shorten(String message) {
		return message.length() <= 60 ? message : message.substring(0, 57) + "…";
	}

	/** A quick fix proposal; it runs after the popup has closed, as the fix may replace the XML text. */
	private record Proposal(String label, String description, Image image, Runnable action)
			implements ICompletionProposal {

		@Override
		public void apply(IDocument document) {
			Display.getCurrent().asyncExec(action);
		}

		@Override
		public Point getSelection(IDocument document) {
			return null;
		}

		@Override
		public String getAdditionalProposalInfo() {
			return XmlProblemHover.escape(description).replace("\n", "<br>");
		}

		@Override
		public String getDisplayString() {
			return label;
		}

		@Override
		public Image getImage() {
			return image;
		}

		@Override
		public IContextInformation getContextInformation() {
			return null;
		}
	}
}
