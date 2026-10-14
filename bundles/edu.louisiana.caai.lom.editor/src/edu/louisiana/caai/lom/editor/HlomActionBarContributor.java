/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

import edu.louisiana.caai.lom.editor.mesh.AddMeshKeywordsAction;
import edu.louisiana.caai.lom.model.lom.presentation.LomActionBarContributor;

/**
 * Action bar contributor of the {@link HlomMetadataEditor}. On the XML page the global edit actions (undo,
 * redo, cut, copy, paste, delete, select all, find) operate on the text; on all other pages they operate on
 * the model through the EMF command stack.
 */
public class HlomActionBarContributor extends LomActionBarContributor {

	private static final String[] TEXT_ACTIONS = { ITextEditorActionConstants.UNDO, ITextEditorActionConstants.REDO,
			ITextEditorActionConstants.CUT, ITextEditorActionConstants.COPY, ITextEditorActionConstants.PASTE,
			ITextEditorActionConstants.DELETE, ITextEditorActionConstants.SELECT_ALL,
			ITextEditorActionConstants.FIND };

	private static final String[] GLOBAL_IDS = { ActionFactory.UNDO.getId(), ActionFactory.REDO.getId(),
			ActionFactory.CUT.getId(), ActionFactory.COPY.getId(), ActionFactory.PASTE.getId(),
			ActionFactory.DELETE.getId(), ActionFactory.SELECT_ALL.getId(), ActionFactory.FIND.getId() };

	private final AddMeshKeywordsAction addMeshKeywordsAction = new AddMeshKeywordsAction();

	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
		IMenuManager documentMenu = menuManager.findMenuUsingPath("edu.louisiana.caai.lom.model.lomMenuID");
		if (documentMenu != null) {
			documentMenu.insertBefore("additions", addMeshKeywordsAction);
		}
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.appendToGroup("lom-additions", addMeshKeywordsAction);
	}

	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		addMeshKeywordsAction.setActiveEditor(part);
	}

	/** Routes the global edit actions to a text editor. */
	public void useTextActions(ITextEditor editor) {
		IActionBars bars = getActionBars();
		if (bars == null) {
			return;
		}
		for (int i = 0; i < GLOBAL_IDS.length; i++) {
			bars.setGlobalActionHandler(GLOBAL_IDS[i], editor.getAction(TEXT_ACTIONS[i]));
		}
		bars.updateActionBars();
	}

	/** Routes the global edit actions to the EMF command stack. */
	public void useModelActions() {
		IActionBars bars = getActionBars();
		if (bars == null) {
			return;
		}
		bars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
		bars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
		bars.setGlobalActionHandler(ActionFactory.CUT.getId(), cutAction);
		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
		bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
		bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
		bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), null);
		bars.setGlobalActionHandler(ActionFactory.FIND.getId(), null);
		bars.updateActionBars();
	}
}
