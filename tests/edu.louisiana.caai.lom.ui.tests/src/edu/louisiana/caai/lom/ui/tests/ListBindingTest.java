/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Table;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.terminology.TerminologySupport;
import edu.louisiana.caai.lom.model.lom.Coverage;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;

/**
 * Lists on the form pages show each element once, also when one command adds several elements.
 */
class ListBindingTest extends AbstractUiTest {

	private static final LomPackage LOM = LomPackage.eINSTANCE;

	private static Lom lom(HlomMetadataEditor editor) {
		Resource resource = editor.getEditingDomain().getResourceSet().getResources().getFirst();
		return HlomResources.getDocumentRoot(resource).getLom();
	}

	/** The table of a list on the General page whose first column header is "Text" and that has n columns. */
	private static Table table(HlomMetadataEditor editor, int columns, int index) {
		List<Table> tables = find(pageControl(editor, pageIndex(editor, "general")), Table.class,
				t -> t.getColumnCount() == columns && "Text".equals(t.getColumn(0).getText()));
		return tables.get(index);
	}

	@Test
	void addingSeveralKeywordsShowsEachOnce() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("general");
		dispatch(200);
		Lom lom = lom(editor);
		General general = lom.getGeneral().getFirst();
		int before = general.getKeyword().size();
		Table keywords = table(editor, 4, 0);
		assertEquals(before, keywords.getItemCount(), "rows before");

		var command = TerminologySupport.addKeywords(editor.getEditingDomain(), lom, ModelPath.of(LOM.getLom_General()),
				LOM.getGeneral_Keyword(), Terminologies.MESH,
				List.of(Terminologies.MESH.get("D020517"), Terminologies.MESH.get("D010210")), "en-US");
		ModelCommands.execute(editor.getEditingDomain(), command);
		dispatch(300);
		assertEquals(before + 2, general.getKeyword().size(), "keywords in the model");
		assertEquals(before + 2, keywords.getItemCount(), "rows in the table");

		// Undo and redo.
		editor.getEditingDomain().getCommandStack().undo();
		dispatch(300);
		assertEquals(before, general.getKeyword().size(), "keywords after undo");
		assertEquals(before, keywords.getItemCount(), "rows after undo");
		editor.getEditingDomain().getCommandStack().redo();
		dispatch(300);
		assertEquals(before + 2, general.getKeyword().size(), "keywords after redo");
		assertEquals(before + 2, keywords.getItemCount(), "rows after redo");

		// The MeSH lookup action's command (Add MeSH Keywords).
		var mesh = edu.louisiana.caai.lom.editor.mesh.MeshKeywordSupport.addKeywords(editor.getEditingDomain(), lom,
				List.of(new edu.louisiana.caai.lom.model.support.mesh.MeshTerm("D009203", "Myocardial Infarction"),
						new edu.louisiana.caai.lom.model.support.mesh.MeshTerm("D006973", "Hypertension")),
				null);
		dispatch(300);
		assertEquals(2, mesh.size());
		assertEquals(before + 4, general.getKeyword().size(), "keywords after Add MeSH Keywords");
		assertEquals(before + 4, keywords.getItemCount(), "rows after Add MeSH Keywords");

		// Leaving the page and coming back.
		editor.showPage("overview");
		dispatch(200);
		editor.showPage("general");
		dispatch(300);
		assertEquals(before + 4, keywords.getItemCount(), "rows after switching pages");
		page().closeEditor(editor, false);
	}

	@Test
	void lookUpButtonAddsEachKeywordOnce() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("general");
		dispatch(200);
		General general = lom(editor).getGeneral().getFirst();
		int before = general.getKeyword().size();
		Table keywords = table(editor, 4, 0);
		org.eclipse.swt.widgets.ToolItem lookUp = find(keywords.getParent().getParent(),
				org.eclipse.swt.widgets.ToolBar.class, _ -> true).stream()
				.flatMap(bar -> java.util.Arrays.stream(bar.getItems()))
				.filter(item -> item.getToolTipText() != null && item.getToolTipText().startsWith("Add keywords coded"))
				.findFirst().orElseThrow();

		dialogs.stop();
		try {
			// The modal dialog runs its own event loop inside the click, so a timer drives it from there: once MeSH
			// is loaded it checks two terms and presses Add.
			long deadline = System.currentTimeMillis() + TIMEOUT;
			Runnable driver = new Runnable() {
				@Override
				public void run() {
					edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog dialog = lookupDialog();
					org.eclipse.swt.widgets.Text search = dialog == null ? null
							: (org.eclipse.swt.widgets.Text) field(dialog, "search");
					if (search == null || !search.isEnabled()) {
						if (System.currentTimeMillis() < deadline) {
							display().timerExec(200, this);
						} else if (dialog != null) {
							dialog.close();
						}
						return;
					}
					for (String code : List.of("D020517", "D010210")) {
						invoke(dialog, edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog.class, "toggle",
								new Class<?>[] { edu.louisiana.caai.lom.model.support.terminology.Concept.class,
										boolean.class },
								Terminologies.MESH.get(code), true);
					}
					org.eclipse.swt.widgets.Button add = find(dialog.getShell(), org.eclipse.swt.widgets.Button.class,
							b -> Integer.valueOf(org.eclipse.jface.dialogs.IDialogConstants.OK_ID).equals(b.getData()))
							.getFirst();
					add.notifyListeners(org.eclipse.swt.SWT.Selection, new org.eclipse.swt.widgets.Event());
				}
			};
			display().timerExec(200, driver);
			lookUp.notifyListeners(org.eclipse.swt.SWT.Selection, new org.eclipse.swt.widgets.Event());
			dispatch(500);
		} finally {
			dialogs.start();
		}
		assertEquals(before + 2, general.getKeyword().size(), "keywords in the model");
		assertEquals(before + 2, keywords.getItemCount(), "rows in the table");
		page().closeEditor(editor, false);
	}

	/** The open terminology lookup dialog, or {@code null}. */
	private static edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog lookupDialog() {
		for (org.eclipse.swt.widgets.Shell shell : display().getShells()) {
			if (!shell.isDisposed() && shell.isVisible() && shell.getData() instanceof
					edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog dialog) {
				return dialog;
			}
		}
		return null;
	}

	@Test
	void addingSeveralCoveragesShowsEachOnce() throws Exception {
		Path file = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("general");
		dispatch(200);
		Lom lom = lom(editor);
		General general = lom.getGeneral().getFirst();
		int before = general.getCoverage().size();
		CompoundCommand command = new CompoundCommand("Add coverages");
		for (String text : List.of("Louisiana", "Texas")) {
			Coverage coverage = LomFactory.eINSTANCE.createCoverage();
			coverage.getString().add(HlomDocuments.langString(text, "en-US"));
			command.append(ModelCommands.addElement(editor.getEditingDomain(), lom, ModelPath.of(LOM.getLom_General()),
					LOM.getGeneral_Coverage(), coverage));
		}
		ModelCommands.execute(editor.getEditingDomain(), command);
		dispatch(300);
		assertEquals(before + 2, general.getCoverage().size(), "coverages in the model");
		// Lists without keyword attributes have the columns Text and Language; Coverage is the second (after
		// Description).
		Table coverages = table(editor, 2, 1);
		assertEquals(before + 2, coverages.getItemCount(), "rows in the table");
		page().closeEditor(editor, false);
	}
}
