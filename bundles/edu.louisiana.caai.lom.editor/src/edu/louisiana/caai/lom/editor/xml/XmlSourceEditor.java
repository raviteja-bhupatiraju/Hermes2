/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.source.ModelSourceEditor;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;

/**
 * The XML page of the document editor: syntax highlighting, line numbers, undo/redo, find/replace, schema-aware
 * content assist, problem annotations (live checking of the text plus the model validation results passed to
 * {@link #setProblems(List)}), quick fixes, formatting, comment toggling, matching tag highlighting and folding.
 */
public class XmlSourceEditor extends ModelSourceEditor {

	/** Id of the Toggle Comment action and of its command (to be bound to Ctrl+/). */
	public static final String TOGGLE_COMMENT_ACTION_ID = "edu.louisiana.caai.lom.editor.xml.toggleComment";

	/** Id of the content assist action ({@link ITextEditorActionConstants#CONTENT_ASSIST}, Ctrl+Space). */
	public static final String CONTENT_ASSIST_ACTION_ID = ITextEditorActionConstants.CONTENT_ASSIST;

	/** Id of the Quick Fix action; its command is the text editors' Quick Fix (Ctrl+1). */
	public static final String QUICK_FIX_ACTION_ID = "edu.louisiana.caai.lom.editor.xml.quickFix";

	/** Applies the quick fixes offered in the text (Ctrl+1); provided by the enclosing editor. */
	public interface QuickFixHandler {
		/** Whether the text is in sync with the model, so that the problems shown apply to it. */
		boolean isSynchronized();

		/** Applies the text to the model and validates it again. */
		void synchronize();

		/** Applies a fix of a problem. */
		void apply(HlomProblem problem, HlomFix fix);
	}

	private volatile DocumentType documentType = HlomDocumentType.INSTANCE;
	private QuickFixHandler quickFixHandler;
	private final CopyOnWriteArrayList<IntConsumer> caretListeners = new CopyOnWriteArrayList<>();
	private XmlViewerSupport support;
	/** Problems passed before the viewer existed. */
	private List<HlomProblem> pendingProblems;

	public XmlSourceEditor() {
		super(HelpContexts.PAGE_XML);
		setSourceViewerConfiguration(new XmlSourceViewerConfiguration(() -> quickFixHandler, () -> documentType));
	}

	@Override
	protected IDocument createDocument(String text) {
		Document document = new Document(text);
		IDocumentPartitioner partitioner = new FastPartitioner(new XmlPartitionScanner(),
				XmlPartitionScanner.PARTITION_TYPES);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		return document;
	}

	@Override
	protected void textChanged() {
		if (support != null) {
			support.documentChanged();
		}
	}

	/** Registers a listener notified of caret moves with the caret's document offset. */
	public void addCaretListener(IntConsumer listener) {
		caretListeners.addIfAbsent(listener);
	}

	/**
	 * The element path at an offset: (namespace, local name, index among same-named siblings) for each element
	 * from the root down to the innermost element containing the offset; empty if there is none.
	 */
	public List<String[]> elementPathAt(int offset) {
		XmlStructure.Element element = XmlStructure.parse(getText()).enclosing(offset);
		List<String[]> steps = new ArrayList<>();
		if (element != null) {
			for (XmlStructure.Element step : element.path()) {
				String namespace = step.namespace();
				steps.add(new String[] { namespace == null ? "" : namespace, step.localName(),
						Integer.toString(step.indexAmongSameNamed()) });
			}
		}
		return steps;
	}

	/** Selects the start tag name of the element of a model object (as far as its path exists in the text). */
	public boolean revealElement(EObject object) {
		XmlStructure.Element element = XmlStructure.parse(getText()).find(XmlContentModel.path(object));
		if (element == null) {
			return false;
		}
		selectAndReveal(element.nameStart(), element.name.length());
		return true;
	}

	/** The model object of an element path (see {@link #elementPathAt(int)}), or {@code null}. */
	public static EObject resolve(EObject documentRoot, List<String[]> path) {
		return documentRoot == null || path.isEmpty() ? null : XmlContentModel.resolve(documentRoot, path);
	}

	/**
	 * The messages of the problems shown in the text, or {@code null} while the current text is not checked yet
	 * (it is checked {@value XmlChecker#DELAY} ms after the last change).
	 */
	public List<String> checkedProblems() {
		return support == null ? null : support.checkedProblems();
	}

	/** Sets the type of the edited document (content assist and documentation follow its schemas). */
	public void setDocumentType(DocumentType type) {
		documentType = type == null ? HlomDocumentType.INSTANCE : type;
		textChanged();
	}

	/** Sets the handler of the quick fixes offered in the text (Ctrl+1). */
	public void setQuickFixHandler(QuickFixHandler handler) {
		quickFixHandler = handler;
	}

	/** Opens the quick fixes of the problems on the caret's line. */
	public void showQuickFixes() {
		if (getSourceViewer() instanceof ITextOperationTarget target && target.canDoOperation(ISourceViewer.QUICK_ASSIST)) {
			target.doOperation(ISourceViewer.QUICK_ASSIST);
		}
	}

	@Override
	protected IVerticalRuler createVerticalRuler() {
		return XmlViewerSupport.createVerticalRuler();
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		return XmlViewerSupport.createViewer(parent, ruler, styles);
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		if (getSourceViewer() instanceof ProjectionViewer viewer) {
			// Post selection events are sent for caret moves by the user and by the program, after typing pauses.
			viewer.addPostSelectionChangedListener(event -> {
				if (event.getSelection() instanceof ITextSelection selection) {
					caretListeners.forEach(listener -> listener.accept(selection.getOffset()));
				}
			});
			support = new XmlViewerSupport(viewer, this::getAnnotationModel, () -> documentType);
			support.documentChanged();
			if (pendingProblems != null) {
				support.setProblems(pendingProblems);
				pendingProblems = null;
			}
		}
	}

	@Override
	protected void createActions() {
		super.createActions();
		createAction(TOGGLE_COMMENT_ACTION_ID, Messages.XmlSourceEditor_ToggleCoMment, TOGGLE_COMMENT_ACTION_ID, () -> {
			if (support != null) {
				support.toggleComment();
			}
		});
		createAction(QUICK_FIX_ACTION_ID, Messages.XmlSourceEditor_QuickFix, ITextEditorActionDefinitionIds.QUICK_ASSIST,
				this::showQuickFixes);
	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		if (isEditable()) {
			addAction(menu, ITextEditorActionConstants.GROUP_EDIT, TOGGLE_COMMENT_ACTION_ID);
			addAction(menu, ITextEditorActionConstants.GROUP_EDIT, QUICK_FIX_ACTION_ID);
		}
	}

	/**
	 * Shows the problems of the model validation in the text. Problems of the XML Schema are ignored: the
	 * editor checks the text itself while it is edited. Other problems are shown at their line (if known) or at
	 * the start tag of the element of their model object.
	 */
	public void setProblems(List<HlomProblem> problems) {
		if (support != null) {
			support.setProblems(problems);
		} else {
			pendingProblems = problems;
		}
	}

	/** Formats the document (see {@link #FORMAT_ACTION_ID}). */
	@Override
	public void format() {
		if (support != null) {
			support.format();
		}
	}

	@Override
	public void dispose() {
		if (support != null) {
			support.dispose();
			support = null;
		}
		caretListeners.clear();
		super.dispose();
	}
}
