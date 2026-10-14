/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.views;

import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.ViewPart;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * A view showing the document of the active editor, like the Outline: it follows the active editor and shows the
 * document again (shortly) after each change. Without a document it explains what it shows.
 */
public abstract class DocumentViewPart extends ViewPart {

	private static final int REFRESH_DELAY = 400;

	private Composite stack;
	private StackLayout layout;
	private Label message;
	private Control content;
	private HlomMetadataEditor editor;
	private boolean refreshScheduled;

	private final CommandStackListener commandListener = _ -> scheduleRefresh();

	private final IPartListener2 partListener = new IPartListener2() {
		@Override
		public void partActivated(IWorkbenchPartReference reference) {
			if (reference.getPart(false) instanceof IEditorPart part) {
				follow(part);
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference reference) {
			partActivated(reference);
		}

		@Override
		public void partClosed(IWorkbenchPartReference reference) {
			if (reference.getPart(false) == editor) {
				follow(null);
			}
		}
	};

	@Override
	public final void createPartControl(Composite parent) {
		stack = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		stack.setLayout(layout);
		message = new Label(stack, SWT.WRAP | SWT.CENTER);
		message.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().applyTo(message);
		content = createContent(stack);
		layout.topControl = message;
		getSite().getPage().addPartListener(partListener);
		follow(getSite().getPage().getActiveEditor());
	}

	/** Creates the controls showing a document. */
	protected abstract Control createContent(Composite parent);

	/**
	 * Shows a document; returns a message to show instead (such as "This view shows Curriculum Inventories"), or
	 * {@code null} if it is shown.
	 */
	protected abstract String show(HlomMetadataEditor editor);

	/** The message without a document. */
	protected abstract String emptyMessage();

	/** The editor whose document is shown, or {@code null}. */
	protected HlomMetadataEditor editor() {
		return editor;
	}

	private void follow(IEditorPart part) {
		HlomMetadataEditor next = part instanceof HlomMetadataEditor hlom ? hlom : null;
		if (next == null && part != null) {
			return; // Another kind of editor: keep showing the last document.
		}
		if (next == editor) {
			return;
		}
		if (editor != null && editor.getEditingDomain() != null) {
			editor.getEditingDomain().getCommandStack().removeCommandStackListener(commandListener);
		}
		editor = next;
		if (editor != null && editor.getEditingDomain() != null) {
			editor.getEditingDomain().getCommandStack().addCommandStackListener(commandListener);
		}
		refresh();
	}

	/** Shows the document again after a short delay (changes come in bursts). */
	protected void scheduleRefresh() {
		if (refreshScheduled || stack == null || stack.isDisposed()) {
			return;
		}
		refreshScheduled = true;
		stack.getDisplay().timerExec(REFRESH_DELAY, () -> {
			refreshScheduled = false;
			refresh();
		});
	}

	/** Shows the document of the followed editor now. */
	protected void refresh() {
		if (stack == null || stack.isDisposed()) {
			return;
		}
		String text = editor == null || editor.documentRoot() == null ? emptyMessage() : show(editor);
		message.setText(text == null ? "" : text);
		layout.topControl = text == null ? content : message;
		stack.layout(true);
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(partListener);
		if (editor != null && editor.getEditingDomain() != null) {
			editor.getEditingDomain().getCommandStack().removeCommandStackListener(commandListener);
		}
		super.dispose();
	}
}
