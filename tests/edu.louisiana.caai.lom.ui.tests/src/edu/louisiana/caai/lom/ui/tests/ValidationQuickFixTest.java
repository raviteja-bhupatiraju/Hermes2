/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixes;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MetadataSchema;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;

/**
 * Validation in the editor: documents that can't be loaded completely open on the XML page with their problems,
 * and quick fixes run as undoable commands.
 */
class ValidationQuickFixTest extends AbstractUiTest {

	@Test
	void invalidDocumentOpensOnXmlPage() {
		HlomMetadataEditor editor = open(copyResource("resources/invalid/opioid-stewardship-draft.xml"));
		dispatch(500);
		assertNoDialogs();
		List<String> pages = new java.util.ArrayList<>();
		for (int i = 0; i < pageCount(editor); i++) {
			pages.add(pageText(editor, i));
		}
		// The source pages (XML, JSON) are the last pages; no "Problems" page is added.
		assertEquals(pageIndex(editor, "json") + 1, pages.size(), "no extra pages: " + pages);
		assertEquals(pageIndex(editor, "xml"), activePage(editor),
				"a document with load errors opens on the XML page, active page is " + pageText(editor, activePage(editor)));
		assertTrue(XmlPageTest.xmlEditor(editor).getText().contains("CNEX"), "the XML page shows the original text");
		editor.validateNow();
		List<HlomProblem> errors = errors(editor.getProblems());
		assertFalse(errors.isEmpty(), "load problems are reported");
		assertTrue(errors.stream().anyMatch(p -> p.message().contains("CNEX")), describe(errors));
		assertNoDialogs();
	}

	@Test
	void quickFixDeclaresMissingSchemaAndCanBeUndone() throws Exception {
		DocumentRoot root = HlomTemplates.CME_COURSE.create("Quick Fix Course", "en-US");
		root.getLom().getMetaMetadata().getFirst().getMetadataSchema()
				.removeIf(s -> HlomDocuments.HEALTHCARE_METADATA_SCHEMA.equals(s.getValue()));
		Path file = save(root, "quick-fix.xml");

		HlomMetadataEditor editor = open(file);
		editor.validateNow();
		HlomProblem problem = schemaProblem(editor);
		assertNotNull(problem, "missing HEALTHCARE_LOMv1 is reported:" + describe(editor.getProblems()));
		assertTrue(QuickFixes.canModify(editor));

		List<HlomFix> fixes = QuickFixes.offered(problem);
		HlomFix fix = fixes.stream().filter(f -> f.kind() == HlomFix.Kind.CHANGE).findFirst().orElse(null);
		assertNotNull(fix, "a fix that changes the document is offered: " + fixes.stream().map(HlomFix::label).toList());
		assertEquals("Declare metadata schema " + HlomDocuments.HEALTHCARE_METADATA_SCHEMA, fix.label());

		QuickFixes.apply(editor, fix, problem);
		dispatch(100);
		editor.validateNow();
		assertEquals(null, schemaProblem(editor), "fixed");
		assertTrue(editor.isDirty());
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		assertTrue(stack.canUndo());

		stack.undo();
		dispatch(100);
		editor.validateNow();
		assertNotNull(schemaProblem(editor), "undo restores the problem");
		assertFalse(editor.isDirty());

		stack.redo();
		editor.validateNow();
		assertEquals(null, schemaProblem(editor), "redo fixes again");

		// "Go to Element" (navigation fix) shows the Meta-Metadata page.
		stack.undo();
		editor.validateNow();
		HlomProblem again = schemaProblem(editor);
		QuickFixes.apply(editor, QuickFixes.goToElement(), again);
		dispatch(100);
		assertEquals(pageIndex(editor, "metametadata"), activePage(editor), "Go to Element");
		assertFalse(editor.isDirty(), "navigation doesn't change the document");
		assertNoDialogs();
	}

	/** Any command triggers validation after the configured delay. */
	@Test
	void validationRunsAfterEdits() {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		editor.validateNow();
		assertEquals(List.of(), errors(editor.getProblems()));
		int[] runs = { 0 };
		editor.addProblemsListener(() -> runs[0]++);
		MetadataSchema healthcare = healthcareSchema(editor);
		EditingDomain domain = editor.getEditingDomain();
		domain.getCommandStack().execute(RemoveCommand.create(domain, healthcare.eContainer(),
				LomPackage.eINSTANCE.getMetaMetadata_MetadataSchema(), healthcare));
		assertTrue(domain.getCommandStack().canUndo(), "the remove command was executed");
		assertTrue(editor.isDirty());
		waitUntil("validation after the change", () -> runs[0] > 0, 5000);
		assertNotNull(schemaProblem(editor), "the change is validated automatically");
	}

	/**
	 * Deleting an element like the tree page's Delete action does: a DeleteCommand on the tree item, which is a
	 * feature map entry wrapper (metadataSchema elements are children of the {@code group} feature map).
	 */
	@Test
	void deleteCommandRemovesMetadataSchema() {
		HlomMetadataEditor editor = open(copyResource("resources/samples/insulin-pump-therapy-course.xml"));
		MetadataSchema healthcare = healthcareSchema(editor);
		EditingDomain domain = editor.getEditingDomain();
		Object treeItem = domain.getChildren(healthcare.eContainer()).stream()
				.filter(child -> AdapterFactoryEditingDomain.unwrap(child) == healthcare).findFirst().orElseThrow();
		Command delete = DeleteCommand.create(domain, List.of(treeItem));
		assertTrue(delete.canExecute(), "Delete is enabled for a metadataSchema element");
		domain.getCommandStack().execute(delete);
		assertNull(healthcare.eContainer(), "deleted");
		domain.getCommandStack().undo();
		assertNotNull(healthcare.eContainer(), "undo");
	}

	private static MetadataSchema healthcareSchema(HlomMetadataEditor editor) {
		DocumentRoot root = HlomResources
				.getDocumentRoot(editor.getEditingDomain().getResourceSet().getResources().getFirst());
		return root.getLom().getMetaMetadata().getFirst().getMetadataSchema().stream()
				.filter(s -> HlomDocuments.HEALTHCARE_METADATA_SCHEMA.equals(s.getValue())).findFirst().orElseThrow();
	}

	private static HlomProblem schemaProblem(HlomMetadataEditor editor) {
		return editor.getProblems().stream()
				.filter(p -> p.message().contains("\"" + HlomDocuments.HEALTHCARE_METADATA_SCHEMA + "\""))
				.findFirst().orElse(null);
	}
}
