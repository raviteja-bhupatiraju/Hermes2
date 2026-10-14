/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.source;

import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewerExtension6;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * A text page of the document editor (XML, JSON): a text editor on an in-memory copy of the document's text. It
 * never saves and never reports itself dirty; the enclosing editor transfers the text with {@link #setText} and
 * {@link #getText} and tracks the user's changes with {@link #isModified()} and the modification listeners. Offers
 * content assist and formatting (with their workbench commands and key bindings).
 */
public abstract class ModelSourceEditor extends AbstractTextEditor {

	/** Id of the Format action and of its command (Ctrl+Shift+F in the text editor context). */
	public static final String FORMAT_ACTION_ID = "edu.louisiana.caai.lom.editor.xml.format";

	/** The key binding context activated while a text page is active (the workbench's text editing context). */
	public static final String TEXT_EDITOR_CONTEXT_ID = "org.eclipse.ui.textEditorScope";

	private final CopyOnWriteArrayList<Runnable> modificationListeners = new CopyOnWriteArrayList<>();
	private final IDocumentListener documentListener = new IDocumentListener() {
		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			// nothing
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			textChanged();
			if (!settingText) {
				modified = true;
				modificationListeners.forEach(Runnable::run);
			}
		}
	};

	/** The text for a document that has not been created yet. */
	private String pendingText = "";
	private IDocument listenedDocument;
	private boolean settingText;
	private boolean modified;

	/** Subclasses set their {@link SourceViewerConfiguration} (highlighting, content assist, hovers). */
	protected ModelSourceEditor(String helpContextId) {
		setKeyBindingScopes(new String[] { TEXT_EDITOR_CONTEXT_ID });
		setHelpContextId(helpContextId);
		setPreferenceStore(createPreferenceStore());
		setDocumentProvider(new Provider());
	}

	/** An in-memory preference store using the system colors and the JFace text font. */
	private static IPreferenceStore createPreferenceStore() {
		PreferenceStore store = new PreferenceStore();
		store.setDefault(PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT, true);
		store.setDefault(PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT, true);
		store.setDefault(PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT, true);
		store.setDefault(PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT, true);
		store.setDefault(PREFERENCE_NAVIGATION_SMART_HOME_END, true);
		// Ctrl+click follows links (references to elements of the document, web addresses).
		store.setDefault(PREFERENCE_HYPERLINKS_ENABLED, true);
		store.setDefault(PREFERENCE_HYPERLINK_KEY_MODIFIER, "M1");
		return store;
	}

	/** Creates an in-memory input for a text page; the name is only shown as tooltip. */
	public static IEditorInput createInput(String name) {
		return new Input(name);
	}

	/** Creates the document of a text (subclasses add a partitioner). */
	protected IDocument createDocument(String text) {
		return new Document(text);
	}

	/** Called on every change of the text (also by {@link #setText}). */
	protected void textChanged() {
		// nothing by default
	}

	/** Re-indents the text (Format). */
	public abstract void format();

	/** Replaces the whole text. Afterwards {@link #isModified()} is {@code false} and the undo history is empty. */
	public void setText(String text) {
		String newText = text == null ? "" : text;
		pendingText = newText;
		IDocument document = getDocument();
		if (document != null && !newText.equals(document.get())) {
			ISourceViewer viewer = getSourceViewer();
			int topIndex = viewer == null ? 0 : viewer.getTopIndex();
			settingText = true;
			try {
				document.set(newText);
			} finally {
				settingText = false;
			}
			if (viewer instanceof ITextViewerExtension6 extension && extension.getUndoManager() != null) {
				IUndoManager undoManager = extension.getUndoManager();
				undoManager.reset();
			}
			if (viewer != null && viewer.getTextWidget() != null && !viewer.getTextWidget().isDisposed()) {
				viewer.setTopIndex(topIndex);
			}
		}
		modified = false;
	}

	/** The current text. */
	public String getText() {
		IDocument document = getDocument();
		return document == null ? pendingText : document.get();
	}

	/** Whether the user edited the text since the last {@link #setText} or {@link #markSynchronized()}. */
	public boolean isModified() {
		return modified;
	}

	/** Declares the current text to be in sync with the model. */
	public void markSynchronized() {
		modified = false;
	}

	/** Registers a listener that is run (in the UI thread) on every user change of the text. */
	public void addModificationListener(Runnable listener) {
		modificationListeners.addIfAbsent(listener);
	}

	/**
	 * Selects and reveals a position.
	 *
	 * @param line   the 1-based line number
	 * @param column the 1-based column; a value below 1 selects the whole line
	 */
	public void revealLine(int line, int column) {
		IDocument document = getDocument();
		if (document == null || document.getNumberOfLines() == 0) {
			return;
		}
		try {
			IRegion region = document.getLineInformation(Math.clamp(line - 1, 0, document.getNumberOfLines() - 1));
			if (column < 1) {
				selectAndReveal(region.getOffset(), region.getLength());
			} else {
				selectAndReveal(region.getOffset() + Math.min(column - 1, region.getLength()), 0);
			}
		} catch (BadLocationException _) {
			// cannot happen: the line index is clamped
		}
	}

	/** Opens the content assist proposals at the caret. */
	public void showContentAssist() {
		if (getSourceViewer() instanceof ITextOperationTarget target
				&& target.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS)) {
			target.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
		}
	}

	/** The document of the text, or {@code null} before the input is set. */
	protected IDocument getDocument() {
		IEditorInput input = getEditorInput();
		return input == null || getDocumentProvider() == null ? null : getDocumentProvider().getDocument(input);
	}

	/** The annotation model of the document (problems), or {@code null}. */
	protected IAnnotationModel getAnnotationModel() {
		IEditorInput input = getEditorInput();
		return input == null || getDocumentProvider() == null ? null : getDocumentProvider().getAnnotationModel(input);
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		unhookDocument();
		super.doSetInput(input);
		listenedDocument = getDocument();
		if (listenedDocument != null) {
			listenedDocument.addDocumentListener(documentListener);
		}
	}

	private void unhookDocument() {
		if (listenedDocument != null) {
			listenedDocument.removeDocumentListener(documentListener);
			listenedDocument = null;
		}
	}

	/** An action that runs an operation, bound to a workbench command. */
	protected final IAction createAction(String id, String text, String commandId, Runnable run) {
		IAction action = new Action(text) {
			@Override
			public void run() {
				run.run();
			}
		};
		action.setActionDefinitionId(commandId);
		setAction(id, action);
		return action;
	}

	@Override
	protected void createActions() {
		super.createActions();
		createAction(FORMAT_ACTION_ID, Messages.ModelSourceEditor_Format, FORMAT_ACTION_ID, this::format);
		createAction(ITextEditorActionConstants.CONTENT_ASSIST, Messages.ModelSourceEditor_ContentAssist,
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, this::showContentAssist);
	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		if (isEditable()) {
			addAction(menu, ITextEditorActionConstants.GROUP_EDIT, ITextEditorActionConstants.CONTENT_ASSIST);
			addAction(menu, ITextEditorActionConstants.GROUP_EDIT, FORMAT_ACTION_ID);
		}
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		// saved by the enclosing editor
	}

	@Override
	public void doSaveAs() {
		// not supported
	}

	@Override
	public void dispose() {
		unhookDocument();
		modificationListeners.clear();
		super.dispose();
	}

	/** An in-memory document provider. Saving is a no-op: the enclosing editor saves through the EMF model. */
	private final class Provider extends AbstractDocumentProvider {
		@Override
		protected IDocument createDocument(Object element) {
			return ModelSourceEditor.this.createDocument(pendingText);
		}

		@Override
		protected IAnnotationModel createAnnotationModel(Object element) {
			return new AnnotationModel();
		}

		@Override
		protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
			// nothing to do: the outer editor serializes the model
		}

		@Override
		protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
			return null;
		}

		@Override
		public boolean isReadOnly(Object element) {
			return false;
		}

		@Override
		public boolean isModifiable(Object element) {
			return true;
		}

		@Override
		public boolean isDeleted(Object element) {
			return false;
		}
	}

	/** An in-memory editor input; it refers to no file. */
	private static final class Input extends PlatformObject implements IEditorInput {
		private final String name;

		Input(String name) {
			this.name = name == null ? "" : name;
		}

		@Override
		public boolean exists() {
			return false;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return ImageDescriptor.getMissingImageDescriptor();
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public IPersistableElement getPersistable() {
			return null;
		}

		@Override
		public String getToolTipText() {
			return name;
		}
	}
}
