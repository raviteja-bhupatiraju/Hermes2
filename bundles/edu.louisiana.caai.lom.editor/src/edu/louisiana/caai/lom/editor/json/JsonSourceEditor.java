/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.json;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.source.ModelSourceEditor;
import edu.louisiana.caai.lom.editor.xml.XmlAnnotations;
import edu.louisiana.caai.lom.editor.xml.XmlViewerSupport;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.json.Json;

/**
 * The JSON page of the document editor: the document in the JSON form of the MedBiquitous Healthcare LOM JSON
 * Schemas (see {@code HlomJson}), with syntax highlighting, line numbers, undo/redo, find/replace, content assist,
 * live syntax checking and formatting (Ctrl+Shift+F).
 */
public class JsonSourceEditor extends ModelSourceEditor {

	private static final int CHECK_DELAY = 300;

	private boolean checkScheduled;
	private AnnotationPainter painter;

	public JsonSourceEditor() {
		super(HelpContexts.PAGE_JSON);
		setSourceViewerConfiguration(new Configuration());
	}

	/** Re-indents the text, if it is valid JSON. */
	@Override
	public void format() {
		IDocument document = getDocument();
		if (document == null) {
			return;
		}
		try {
			String formatted = Json.write(Json.parse(document.get()));
			if (!formatted.equals(document.get())) {
				// A user edit (undoable), not a replacement of the text.
				document.replace(0, document.getLength(), formatted);
			}
		} catch (Json.JsonException | BadLocationException _) {
			check();
		}
	}

	@Override
	protected IVerticalRuler createVerticalRuler() {
		return XmlViewerSupport.createVerticalRuler();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		painter = XmlAnnotations.installPainter(getSourceViewer());
		check();
	}

	@Override
	protected void textChanged() {
		ISourceViewer viewer = getSourceViewer();
		if (checkScheduled || viewer == null || viewer.getTextWidget() == null || viewer.getTextWidget().isDisposed()) {
			return;
		}
		checkScheduled = true;
		viewer.getTextWidget().getDisplay().timerExec(CHECK_DELAY, () -> {
			checkScheduled = false;
			check();
		});
	}

	/** Checks the syntax of the text and shows the first error. */
	private void check() {
		IDocument document = getDocument();
		IAnnotationModel annotations = getAnnotationModel();
		if (document == null || annotations == null) {
			return;
		}
		List<Annotation> old = new ArrayList<>();
		annotations.getAnnotationIterator().forEachRemaining(old::add);
		old.forEach(annotations::removeAnnotation);
		if (document.getLength() == 0) {
			return;
		}
		try {
			Json.parse(document.get());
		} catch (Json.JsonException e) {
			try {
				IRegion line = document.getLineInformation(Math.clamp(e.getLine() - 1, 0, document.getNumberOfLines() - 1));
				int offset = line.getOffset() + Math.clamp(e.getColumn() - 1, 0, line.getLength());
				int length = Math.max(1, line.getOffset() + line.getLength() - offset);
				if (offset + length > document.getLength()) {
					offset = Math.max(0, document.getLength() - 1);
					length = Math.min(1, document.getLength());
				}
				annotations.addAnnotation(
						new XmlAnnotations.ProblemAnnotation(HlomProblem.Severity.ERROR, e.getMessage(), null),
						new Position(offset, length));
			} catch (BadLocationException _) {
				// The document changed meanwhile; checked again after the change.
			}
		}
	}

	@Override
	public void dispose() {
		if (painter != null) {
			painter.dispose();
			painter = null;
		}
		super.dispose();
	}

	/** Highlighting, content assist and the hover of the problem ruler. */
	private static final class Configuration extends SourceViewerConfiguration {
		@Override
		public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
			PresentationReconciler reconciler = new PresentationReconciler();
			DefaultDamagerRepairer repairer = new DefaultDamagerRepairer(new JsonScanner());
			reconciler.setDamager(repairer, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(repairer, IDocument.DEFAULT_CONTENT_TYPE);
			return reconciler;
		}

		@Override
		public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
			ContentAssistant assistant = new ContentAssistant();
			assistant.setContentAssistProcessor(new JsonContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
			assistant.enableAutoActivation(true);
			assistant.setAutoActivationDelay(200);
			assistant.enableAutoInsert(true);
			assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
			assistant.setInformationControlCreator(parent -> new DefaultInformationControl(parent, (String) null));
			assistant.setProposalSelectorBackground(HlomTheme.color(HlomTheme.SURFACE));
			assistant.setStatusLineVisible(true);
			assistant.setStatusMessage(Messages.JsonSourceEditor_ProposalsFromTheHealthcareLOM);
			return assistant;
		}

		@Override
		public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
			return new DefaultAnnotationHover();
		}

		@Override
		public int getTabWidth(ISourceViewer sourceViewer) {
			return 2;
		}
	}
}
