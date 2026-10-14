/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ErrorViewPart;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.compare.CompareView;
import edu.louisiana.caai.lom.editor.help.GuideView;
import edu.louisiana.caai.lom.editor.library.LibraryView;
import edu.louisiana.caai.lom.editor.preferences.GeneralPreferencePage;
import edu.louisiana.caai.lom.editor.preferences.MeshPreferencePage;
import edu.louisiana.caai.lom.editor.preferences.TerminologiesPreferencePage;
import edu.louisiana.caai.lom.editor.preferences.ValidationPreferencePage;
import edu.louisiana.caai.lom.editor.start.StartView;
import edu.louisiana.caai.lom.editor.validation.ValidationView;

/**
 * The application's views open and its preference pages build their controls without errors.
 */
@SuppressWarnings("restriction")
class ViewsSmokeTest extends AbstractUiTest {

	/** System property of {@link LibraryView} naming the folder to show. */
	private static final String LIBRARY_FOLDER_PROPERTY = "hlom.library.folder";

	@Test
	void viewsOpen() throws Exception {
		Path library = copyResource("resources/samples/insulin-pump-therapy-course.xml").getParent();
		System.setProperty(LIBRARY_FOLDER_PROPERTY, library.toString());
		try {
			HlomMetadataEditor editor = open(library.resolve("insulin-pump-therapy-course.xml"));
			for (String id : List.of(ValidationView.ID, LibraryView.ID, CompareView.ID, StartView.ID)) {
				showView(id);
			}
			// The validation view lists the problems of the active editor (the sample has warnings/infos at most).
			IViewPart validation = showView(ValidationView.ID);
			editor.getSite().getPage().activate(editor);
			editor.validateNow();
			dispatch(200);
			assertNotNull(validation.getViewSite());

			// The library scans its folder in a job and lists the sample.
			IViewPart libraryView = showView(LibraryView.ID);
			waitUntil("library lists the sample", () -> find(libraryView.getViewSite().getShell(), Table.class,
					table -> table.getItemCount() > 0 && table.isVisible()).size() > 0, TIMEOUT);
			assertFalse(editor.isDirty());
			assertNoDialogs();
		} finally {
			System.clearProperty(LIBRARY_FOLDER_PROPERTY);
		}
	}

	/** The guide uses an embedded browser, which may be unavailable; the view must then show a fallback. */
	@Test
	void guideViewOpens() throws Exception {
		IViewPart view = page().showView(GuideView.ID);
		dispatch(1000);
		assertNotNull(view);
		assertFalse(view instanceof ErrorViewPart, "GuideView failed to open");
		List<IStatus> browserErrors = loggedErrors.stream()
				.filter(s -> String.valueOf(s.getMessage()).toLowerCase().contains("browser")).toList();
		if (!browserErrors.isEmpty()) {
			System.err.println("[GuideView] tolerated browser errors: " + browserErrors);
			loggedErrors.removeAll(browserErrors);
		}
		page().hideView(view);
		assertNoDialogs();
	}

	@Test
	void preferencePagesCreateTheirControls() {
		Shell shell = new Shell(display(), SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		try {
			for (IWorkbenchPreferencePage page : List.of(new GeneralPreferencePage(), new ValidationPreferencePage(),
					new MeshPreferencePage(), new TerminologiesPreferencePage())) {
				page.init(PlatformUI.getWorkbench());
				page.createControl(shell);
				assertNotNull(page.getControl(), page.getClass().getSimpleName());
				assertTrue(page.isValid(), page.getClass().getSimpleName() + " is valid with the stored values");
				shell.layout(true, true);
				dispatch(50);
				page.dispose();
				if (page.getControl() != null && !page.getControl().isDisposed()) {
					page.getControl().dispose();
				}
			}
		} finally {
			shell.dispose();
		}
		assertNoDialogs();
	}

	private IViewPart showView(String id) throws PartInitException {
		IWorkbenchPage page = page();
		IViewPart view = page.showView(id);
		dispatch(300);
		assertNotNull(view, id);
		assertFalse(view instanceof ErrorViewPart, id + " failed to open");
		assertEquals(view, page.findView(id));
		return view;
	}
}
