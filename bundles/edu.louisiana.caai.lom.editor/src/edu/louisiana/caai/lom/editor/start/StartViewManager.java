/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;

/**
 * Shows the {@link StartView} in place of the (hidden) editor area while no editor is open, and hides it as soon
 * as a document is opened.
 */
public final class StartViewManager {

	private StartViewManager() {
	}

	/** Installs the manager in a window and brings the page into the right state. */
	public static void install(IWorkbenchWindow window) {
		IPartListener2 listener = new IPartListener2() {
			@Override
			public void partOpened(IWorkbenchPartReference reference) {
				if (reference instanceof IEditorReference) {
					schedule(window);
				}
			}

			@Override
			public void partClosed(IWorkbenchPartReference reference) {
				if (reference instanceof IEditorReference) {
					schedule(window);
				}
			}
		};
		window.getPartService().addPartListener(listener);
		update(window);
	}

	/** Shows the start page (Help &gt; Start Page) regardless of the open editors. */
	public static void showStartPage(IWorkbenchWindow window) {
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		try {
			page.showView(StartView.ID);
		} catch (PartInitException exception) {
			HlomEditorPlugin.INSTANCE.log(exception);
		}
	}

	private static void schedule(IWorkbenchWindow window) {
		Display display = window.getShell() != null ? window.getShell().getDisplay() : Display.getDefault();
		display.asyncExec(() -> update(window));
	}

	/** Shows the start page if no editor is open, hides it otherwise. */
	public static void update(IWorkbenchWindow window) {
		if (window.getShell() == null || window.getShell().isDisposed() || window.getWorkbench().isClosing()) {
			return;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		boolean editorsOpen = page.getEditorReferences().length > 0;
		IViewReference start = page.findViewReference(StartView.ID);
		if (editorsOpen) {
			if (start != null && page.getPartState(start) == IWorkbenchPage.STATE_MAXIMIZED) {
				page.setPartState(start, IWorkbenchPage.STATE_RESTORED);
			}
			if (!page.isEditorAreaVisible()) {
				page.setEditorAreaVisible(true);
			}
			if (start != null) {
				page.hideView(start);
			}
		} else {
			if (page.isEditorAreaVisible()) {
				page.setEditorAreaVisible(false);
			}
			try {
				page.showView(StartView.ID, null, IWorkbenchPage.VIEW_ACTIVATE);
				// Full window: the outline, properties and validation views have nothing to show yet. With open
				// projects, the Projects view stays visible next to the start page.
				if (!edu.louisiana.caai.lom.editor.projects.ProjectsView.hasProjects()) {
					window.getShell().getDisplay().timerExec(300, () -> maximize(window));
				}
			} catch (PartInitException exception) {
				HlomEditorPlugin.INSTANCE.log(exception);
			}
		}
	}

	private static void maximize(IWorkbenchWindow window) {
		IWorkbenchPage page = window.getActivePage();
		if (page == null || page.getEditorReferences().length > 0) {
			return;
		}
		IViewReference shown = page.findViewReference(StartView.ID);
		if (shown != null && page.getPartState(shown) != IWorkbenchPage.STATE_MAXIMIZED) {
			page.setPartState(shown, IWorkbenchPage.STATE_MAXIMIZED);
		}
	}
}
