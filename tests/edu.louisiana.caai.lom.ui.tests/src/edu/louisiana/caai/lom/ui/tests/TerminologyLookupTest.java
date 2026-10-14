/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;

/**
 * The offline terminology lookup dialog.
 */
class TerminologyLookupTest extends AbstractUiTest {

	@Test
	void searchAndChooseOffline() {
		TerminologyLookupDialog dialog = new TerminologyLookupDialog(window().getShell(), "Look Up", "&Use",
				Terminologies.all(), Terminologies.ICD_10_CM, false);
		dialog.setBlockOnOpen(false);
		// The dialog is driven by the test; the watcher would close it as an unexpected modal dialog.
		dialogs.stop();
		dialog.open();
		try {
			Text search = (Text) field(dialog, "search");
			waitUntil("the terminology is loaded", search::isEnabled, TIMEOUT);
			Tree tree = (Tree) find(dialog.getShell(), Tree.class, _ -> true).getFirst();
			assertTrue(tree.getItemCount() >= 20, "the ICD-10-CM chapters are shown for browsing");

			search.setText("sepsis unspecified");
			waitUntil("results", () -> tree.getItemCount() > 0 && tree.getItem(0).getText(1).equals("A41.9"), TIMEOUT);
			Button ok = dialog.getShell() == null ? null
					: find(dialog.getShell(), Button.class, b -> IDialogConstants.OK_ID == (Integer) b.getData()).getFirst();
			assertTrue(ok.isEnabled());
			ok.notifyListeners(SWT.Selection, null);
			dispatch(100);
			List<Concept> result = dialog.getResult();
			assertEquals(1, result.size());
			assertEquals("A41.9", result.getFirst().code());
			assertEquals(Terminologies.ICD_10_CM, dialog.getResultTerminology());
		} finally {
			if (dialog.getShell() != null && !dialog.getShell().isDisposed()) {
				dialog.close();
			}
			dialogs.start();
		}
	}
}
