/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.NewSearchUI;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.search.FieldElement;
import edu.louisiana.caai.lom.editor.search.HermesSearchQuery;
import edu.louisiana.caai.lom.editor.search.ReplaceRefactoring;
import edu.louisiana.caai.lom.editor.search.HermesSearchResult;
import edu.louisiana.caai.lom.editor.search.HermesSearchResultPage;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.HlomSearchFields;

/** Tests the search of project documents (Search dialog page, query, result page and opening matches). */
class SearchTest extends AbstractUiTest {

	private Path folder;
	private IFile insulin;
	private IFile credits;

	private void createProject() throws Exception {
		folder = tempDir.resolve("Search Project");
		HermesProject project = HermesProject.create(folder, "Search Project", "", Format.XML);
		List<Path> documents = project.importFiles(List.of(copyResource("resources/samples/insulin-pump-therapy-course.xml"),
				copyResource("resources/samples/course-with-multiple-credit-types.xml")), folder);
		HermesWorkspace.open(folder, null);
		insulin = HermesWorkspace.fileAt(documents.get(0));
		credits = HermesWorkspace.fileAt(documents.get(1));
		assertNotNull(insulin);
		waitUntil("the documents are indexed",
				() -> DocumentIndex.get(insulin) != null && DocumentIndex.get(credits) != null, TIMEOUT);
	}

	private void closeProject() throws CoreException {
		IProject project = folder == null ? null : HermesWorkspace.projectAt(folder);
		if (project != null) {
			HermesWorkspace.close(project, null);
		}
	}

	private static HermesSearchResult run(Query query) {
		HermesSearchQuery search = HermesSearchQuery.inWorkspace(query);
		IStatus status = search.run(new NullProgressMonitor());
		assertTrue(status.isOK(), status::toString);
		return (HermesSearchResult) search.getSearchResult();
	}

	private static org.eclipse.jface.viewers.StructuredViewer viewer(HermesSearchResultPage page) {
		return (org.eclipse.jface.viewers.StructuredViewer) invoke(page,
				org.eclipse.search.ui.text.AbstractTextSearchViewPage.class, "getViewer", new Class<?>[0]);
	}

	private static Set<IFile> files(HermesSearchResult result) {
		return Arrays.stream(result.getElements()).map(e -> ((FieldElement) e).file()).collect(Collectors.toSet());
	}

	@Test
	void searchesTheDocumentsOfProjects() throws Exception {
		try {
			createProject();
			HermesSearchResult result = run(Query.of("insulin"));
			assertEquals(Set.of(insulin), files(result));
			assertTrue(result.getMatchCount() >= 4, () -> "matches: " + result.getMatchCount());
			assertTrue(result.getLabel().contains("in 1 document"), result.getLabel());

			// Limited to titles.
			HermesSearchResult titles = run(new Query("insulin", false, false, false, List.of(HlomSearchFields.TITLES)));
			assertEquals(1, titles.getMatchCount());
			FieldElement title = (FieldElement) titles.getElements()[0];
			assertEquals("/lom/general/title/string", title.path());
			assertEquals("General › Title", title.label());

			// An invalid expression is reported as an error status, not an exception.
			HermesSearchQuery invalid = HermesSearchQuery.inWorkspace(new Query("([", false, true, false, List.of()));
			assertEquals(IStatus.ERROR, invalid.run(new NullProgressMonitor()).getSeverity());
		} finally {
			closeProject();
		}
	}

	@Test
	void unsavedChangesAreSearched() throws Exception {
		try {
			createProject();
			HlomMetadataEditor editor = open(HermesWorkspace.location(credits));
			var root = HlomResources.getDocumentRoot(editor.getEditingDomain().getResourceSet().getResources().getFirst());
			LangString title = root.getLom().getGeneral().getFirst().getTitle().getFirst().getString().getFirst();
			editor.getEditingDomain().getCommandStack().execute(SetCommand.create(editor.getEditingDomain(), title,
					LomPackage.eINSTANCE.getLangString_Value(), "Zebrafish Genetics"));
			dispatch(100);
			assertTrue(editor.isDirty());
			HermesSearchResult result = run(Query.of("zebrafish"));
			assertEquals(Set.of(credits), files(result));
			page().closeEditor(editor, false);
		} finally {
			closeProject();
		}
	}

	@Test
	void openingAMatchRevealsTheField() throws Exception {
		try {
			createProject();
			HermesSearchQuery query = HermesSearchQuery.inWorkspace(
					new Query("D003920", false, false, false, List.of()));
			NewSearchUI.runQueryInForeground(null, query);
			dispatch(200);
			ISearchResultViewPart view = NewSearchUI.getSearchResultView();
			assertNotNull(view, "the Search view is shown");
			HermesSearchResultPage resultPage = assertInstanceOf(HermesSearchResultPage.class, view.getActivePage());
			// The page shows the results asynchronously.
			waitUntil("the results are shown", () -> viewer(resultPage) instanceof org.eclipse.jface.viewers.TreeViewer tree
					&& tree.getTree().getItemCount() > 0, TIMEOUT);
			resultPage.gotoNextMatch();
			waitUntil("the document is opened", () -> page().getActiveEditor() instanceof HlomMetadataEditor, TIMEOUT);
			HlomMetadataEditor editor = (HlomMetadataEditor) page().getActiveEditor();
			waitUntil("the keyword is selected", () -> editor.getSelection() instanceof IStructuredSelection selection
					&& selection.getFirstElement() instanceof Keyword keyword && "D003920".equals(keyword.getId()),
					TIMEOUT);
			assertTrue(editor.revealPath("/lom/general/keyword[1]"));
			dispatch(100);
			assertNoDialogs();
			page().closeEditor(editor, false);
			page().hideView(view);
		} finally {
			closeProject();
		}
	}

	@Test
	void replacingChangesOnlyTheMatchedValuesAndCanBeUndone() throws Exception {
		try {
			createProject();
			String before = java.nio.file.Files.readString(HermesWorkspace.location(insulin), StandardCharsets.UTF_8);
			HermesSearchResult result = run(new Query("Pump Therapy", false, false, false, List.of()));
			List<FieldElement> fields = Arrays.stream(result.getElements()).map(FieldElement.class::cast).toList();
			assertFalse(fields.isEmpty());
			ReplaceRefactoring refactoring = new ReplaceRefactoring(new Query("Pump Therapy", false, false, false,
					List.of()), fields);
			refactoring.setReplacement("Pump Course");
			org.eclipse.ltk.core.refactoring.PerformRefactoringOperation operation =
					new org.eclipse.ltk.core.refactoring.PerformRefactoringOperation(refactoring,
							org.eclipse.ltk.core.refactoring.CheckConditionsOperation.ALL_CONDITIONS);
			org.eclipse.core.resources.ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());
			assertFalse(operation.getConditionStatus().hasFatalError(), operation.getConditionStatus().toString());
			String after = java.nio.file.Files.readString(HermesWorkspace.location(insulin), StandardCharsets.UTF_8);
			assertTrue(after.contains("Insulin Pump Course"), after);
			assertEquals(before.lines().count(), after.lines().count(), "the layout is kept");
			waitUntil("the document is validated again", () -> "Insulin Pump Course".equals(
					DocumentIndex.get(insulin).title()), TIMEOUT);

			org.eclipse.ltk.core.refactoring.RefactoringCore.getUndoManager().performUndo(null,
					new NullProgressMonitor());
			assertEquals(before, java.nio.file.Files.readString(HermesWorkspace.location(insulin), StandardCharsets.UTF_8));
			assertNoDialogs();
		} finally {
			closeProject();
		}
	}
}
