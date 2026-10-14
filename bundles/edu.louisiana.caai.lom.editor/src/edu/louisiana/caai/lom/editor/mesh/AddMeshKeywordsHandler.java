/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.mesh;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler of the "Add MeSH Keywords" command ({@link AddMeshKeywordsAction#ID}): enabled while the active
 * editor holds an HLOM document.
 */
public class AddMeshKeywordsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		AddMeshKeywordsAction.run(HandlerUtil.getActiveEditor(event));
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		Object editor = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(editor instanceof IEditorPart part && AddMeshKeywordsAction.documentRoot(part) != null);
	}
}
