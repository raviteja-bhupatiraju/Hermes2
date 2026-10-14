/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ShowInContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.projects.ProjectsView;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;

/**
 * The Projects view, a Common Navigator: its actions in the tool bar and context menu, undoable deletion and
 * Show In.
 */
class ProjectsViewTest extends AbstractUiTest {

	private ProjectsView view;
	private Path folder;
	private Path document;
	private IFile file;

	@BeforeEach
	void createProject() throws Exception {
		view = ProjectsView.show(window());
		assertNotNull(view);
		folder = tempDir.resolve("Navigator Project");
		HermesProject project = HermesProject.create(folder, "Navigator Project", "", Format.XML);
		document = project.importFiles(List.of(copyResource("resources/samples/insulin-pump-therapy-course.xml")), folder)
				.getFirst();
		view.addProject(project);
		file = HermesWorkspace.fileAt(document);
		assertNotNull(file);
		waitUntil("the document is validated", () -> DocumentIndex.get(file) != null, TIMEOUT);
		waitUntil("the document is listed", () -> select(file), TIMEOUT);
	}

	@AfterEach
	void closeProject() throws Exception {
		IProject project = HermesWorkspace.projectAt(folder);
		if (project != null) {
			HermesWorkspace.close(project, null);
		}
		page().hideView(view);
	}

	/** Selects an element of the view; whether it is selected (it is listed). */
	private boolean select(Object element) {
		view.getCommonViewer().refresh();
		view.getCommonViewer().setSelection(new StructuredSelection(element), true);
		dispatch(50);
		return ((IStructuredSelection) view.getCommonViewer().getSelection()).toList().contains(element);
	}

	@Test
	void toolBarAndContextMenuOfferTheOperations() {
		List<String> tools = new ArrayList<>();
		for (IContributionItem item : view.getViewSite().getActionBars().getToolBarManager().getItems()) {
			if (item instanceof org.eclipse.jface.action.ActionContributionItem action) {
				tools.add(action.getAction().getText().replace("&", "").strip());
			}
		}
		assertTrue(tools.containsAll(List.of("New Project...", "Open Project...", "New Document...", "Refresh")),
				tools.toString());
		assertTrue(tools.contains("Collapse All"), "the navigator's own tools: " + tools);

		List<String> items = contextMenu();
		for (String expected : List.of("Open", "New Document...", "Rename...", "Delete", "Convert to JSON",
				"Show in File Explorer", "Refresh")) {
			assertTrue(items.stream().anyMatch(item -> item.startsWith(expected)), expected + " in " + items);
		}
		assertNoDialogs();
	}

	/** The items of the context menu of the selection. */
	private List<String> contextMenu() {
		Menu menu = view.getCommonViewer().getTree().getMenu();
		menu.notifyListeners(SWT.Show, new Event());
		dispatch(50);
		List<String> items = new ArrayList<>();
		for (MenuItem item : menu.getItems()) {
			items.add(item.getText().replace("&", ""));
		}
		menu.notifyListeners(SWT.Hide, new Event());
		return items;
	}

	@Test
	void deletedDocumentsAreRestoredByUndo() throws Exception {
		byte[] content = Files.readAllBytes(document);
		page().activate(view);
		IActionBars bars = view.getViewSite().getActionBars();
		IAction delete = bars.getGlobalActionHandler(ActionFactory.DELETE.getId());
		assertNotNull(delete, "the view handles Delete");
		assertTrue(delete.isEnabled());

		dialogs.answer("Delete", "OK");
		delete.run();
		waitUntil("the document is deleted", () -> !Files.exists(document), TIMEOUT);
		assertEquals(1, dialogs.seen.size(), "the deletion is confirmed: " + dialogs.seen);

		page().activate(view);
		dispatch(50);
		window().getService(IHandlerService.class).executeCommand("org.eclipse.ui.edit.undo", null);
		waitUntil("Undo restores the document", () -> Files.exists(document), TIMEOUT);
		assertArrayEquals(content, Files.readAllBytes(document), "the restored document is unchanged");
		waitUntil("the restored document is listed again", () -> select(file), TIMEOUT);
	}

	@Test
	void showInSelectsTheDocumentOfAnEditor() {
		HlomMetadataEditor editor = open(document);
		view.getCommonViewer().setSelection(StructuredSelection.EMPTY);
		assertTrue(view.show(new ShowInContext(editor.getEditorInput(), null)), "Show In accepts the editor's document");
		dispatch(50);
		assertEquals(List.of(file), ((IStructuredSelection) view.getCommonViewer().getSelection()).toList());
		page().closeEditor(editor, false);
		assertNoDialogs();
	}
}
