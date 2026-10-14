/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.mesh;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

import edu.louisiana.caai.lom.editor.pages.EditorServices;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.model.support.mesh.MeshClient;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;

/**
 * Opens the {@link MeshLookupDialog} for the active HLOM editor and adds the chosen descriptors as keywords of
 * {@code lom/general}. Usable in an editor action bar contributor ({@link #setActiveEditor}) and, through
 * {@link AddMeshKeywordsHandler}, as a command.
 */
public class AddMeshKeywordsAction extends Action {

	/** The action and command id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.mesh.addKeywords";

	private IEditorPart editor;

	public AddMeshKeywordsAction() {
		super(Messages.AddMeshKeywordsAction_AddMeSHKeywords);
		setId(ID);
		setActionDefinitionId(ID);
		setToolTipText(Messages.AddMeshKeywordsAction_AddKeywordsFromTheMedical);
		ImageDescriptor image = ImageDescriptor.createFromURL(
				AddMeshKeywordsAction.class.getResource("/icons/mesh/mesh.png"));
		setImageDescriptor(image);
		setEnabled(false);
	}

	/** Tracks the active editor; the action is enabled for editors holding an HLOM document. */
	public void setActiveEditor(IEditorPart editor) {
		this.editor = editor;
		setEnabled(documentRoot(editor) != null);
	}

	@Override
	public void run() {
		run(editor);
	}

	/**
	 * Opens the dialog for an editor and adds the chosen terms.
	 *
	 * @return the keywords added (empty if cancelled, or if the editor holds no HLOM document)
	 */
	public static List<Keyword> run(IEditorPart editor) {
		DocumentRoot root = documentRoot(editor);
		if (root == null) {
			return List.of();
		}
		EditingDomain domain = ((IEditingDomainProvider) editor).getEditingDomain();
		Shell shell = editor.getSite().getShell();
		MeshClient.configureShared(HlomPreferences.getMeshBaseUrl(), HlomPreferences.isMeshEnabled());
		List<MeshTerm> terms = MeshLookupDialog.open(shell, "", MeshKeywordSupport.existingIds(root),
				MeshClient.shared());
		if (terms.isEmpty()) {
			return List.of();
		}
		List<Keyword> added = MeshKeywordSupport.addKeywords(domain, root, terms, null);
		if (!added.isEmpty() && editor instanceof EditorServices services) {
			services.reveal(added.getFirst());
		}
		return added;
	}

	/** The HLOM document of an editor, or {@code null} if it isn't an editing domain provider for one. */
	public static DocumentRoot documentRoot(IEditorPart editor) {
		if (!(editor instanceof IEditingDomainProvider provider) || provider.getEditingDomain() == null) {
			return null;
		}
		EList<Resource> resources = provider.getEditingDomain().getResourceSet().getResources();
		if (resources.isEmpty()) {
			return null;
		}
		DocumentRoot root = HlomResources.getDocumentRoot(resources.getFirst());
		return root != null && root.getLom() != null ? root : null;
	}
}
