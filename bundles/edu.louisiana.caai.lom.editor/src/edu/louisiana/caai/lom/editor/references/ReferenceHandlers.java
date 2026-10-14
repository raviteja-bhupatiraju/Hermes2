/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.references;

import org.eclipse.osgi.util.NLS;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.search.ReferencesSearchQuery;
import edu.louisiana.caai.lom.editor.search.ReferringDocumentsQuery;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.MedbiqPages;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.References.Occurrence;
import edu.louisiana.caai.medbiq.model.References.Target;

/**
 * The commands on the references within a document, for the element selected in the editor (in a form, the tree,
 * the outline or at the caret of the XML page): <em>Search &gt; References</em> (Ctrl+Shift+G) lists the references
 * to it in the Search view, <em>Edit &gt; Rename Identifier</em> (Alt+Shift+R) changes its identifier and all
 * references to it in one undoable step.
 */
public final class ReferenceHandlers {

	private ReferenceHandlers() {
	}

	/** The element of the editor's selection that references can refer to, with the editor's document root. */
	record Selected(HlomMetadataEditor editor, EObject root, Target target) {
	}

	/**
	 * The referenceable element selected in the active editor: the selected element or the nearest element
	 * containing it that is a target of a kind of reference; {@code null} after telling the user why not.
	 */
	static Selected selected(ExecutionEvent event, String command) {
		Shell shell = HandlerUtil.getActiveShell(event);
		IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (!(part instanceof HlomMetadataEditor editor) || !(editor.documentType() instanceof MedbiqDocumentType type)
				|| type.references().isEmpty()) {
			MessageDialog.openInformation(shell, command, Messages.ReferenceHandlers_OpenADocumentWithReferences);
			return null;
		}
		if (!editor.applySourcePages()) {
			return null;
		}
		EObject documentRoot = editor.documentRoot();
		EObject root = documentRoot == null ? null : Paths.rootElement(documentRoot);
		if (root != null && editor.getSelection() instanceof IStructuredSelection selection
				&& selection.getFirstElement() instanceof EObject selectedObject) {
			for (EObject object = selectedObject; object != null; object = object.eContainer()) {
				for (References.Kind kind : type.references()) {
					Target target = References.targetOf(root, kind, object);
					if (target != null) {
						return new Selected(editor, root, target);
					}
				}
			}
		}
		String targets = String.join(", ", type.references().stream().map(kind -> kind.label().toLowerCase())
				.distinct().toList());
		MessageDialog.openInformation(shell, command, NLS.bind(Messages.ReferenceHandlers_SelectAnElementThatReferences, targets));
		return null;
	}

	/** Search &gt; References: the references to the selected element in the Search view. */
	public static final class FindReferences extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			Selected selected = selected(event, "References");
			if (selected == null) {
				return null;
			}
			IFile file = fileOf(selected.editor());
			if (file == null) {
				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.ReferenceHandlers_References,
						Messages.ReferenceHandlers_SaveTheDocumentFirstIts);
				return null;
			}
			NewSearchUI.runQueryInForeground(null, new ReferencesSearchQuery(file, selected.root(), selected.target()));
			return null;
		}
	}

	/** Search &gt; Referring Documents: the documents of the projects naming the active document. */
	public static final class ReferringDocuments extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			Shell shell = HandlerUtil.getActiveShell(event);
			if (!(HandlerUtil.getActiveEditor(event) instanceof HlomMetadataEditor editor) || !editor.applySourcePages()) {
				MessageDialog.openInformation(shell, Messages.ReferenceHandlers_ReferringDocuments, Messages.ReferenceHandlers_OpenADocumentToFind);
				return null;
			}
			DocumentLinks links = DocumentLinks.of(editor.documentType(), editor.documentRoot());
			if (links.identifiers().isEmpty()) {
				MessageDialog.openInformation(shell, Messages.ReferenceHandlers_ReferringDocuments, Messages.ReferenceHandlers_ThisDocumentHasNoIdentifier);
				return null;
			}
			NewSearchUI.runQueryInBackground(new ReferringDocumentsQuery(editor.getEditorInput().getName(),
					fileOf(editor), links.identifiers()));
			return null;
		}
	}

	/** Edit &gt; Rename Identifier: renames the selected element's identifier and updates its references. */
	public static final class RenameIdentifier extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			Selected selected = selected(event, "Rename Identifier");
			if (selected == null) {
				return null;
			}
			Target target = selected.target();
			References.KeyLocation location = References.keyLocation(target);
			if (location == null) {
				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.ReferenceHandlers_RenameIdentifier,
						NLS.bind(Messages.ReferenceHandlers_TheIdentifierOfThisCan, target.kind().label().toLowerCase()));
				return null;
			}
			List<Occurrence> references = References.referencesTo(selected.root(), target);
			List<String> taken = References.targets(selected.root(), target.kind()).stream().map(Target::key).toList();
			InputDialog dialog = new InputDialog(HandlerUtil.getActiveShell(event), Messages.ReferenceHandlers_RenameIdentifier,
					NLS.bind(Messages.ReferenceHandlers_NewIdentifierOfTheWill, new Object[] { target.kind().label().toLowerCase(), target.label(), references.size(), (references.size() == 1 ? " reference" : " references") }),
					target.key(), value -> {
						String key = value.strip();
						if (key.isEmpty()) {
							return Messages.ReferenceHandlers_EnterTheIdentifier;
						}
						if (!key.equals(target.key()) && taken.contains(key)) {
							return NLS.bind(Messages.ReferenceHandlers_AnotherHasThisIdentifier, target.kind().label().toLowerCase());
						}
						if (key.contains("'") && !target.kind().template().equals("%s")) {
							return Messages.ReferenceHandlers_TheIdentifierCanTContain;
						}
						return null;
					});
			if (dialog.open() != Window.OK || dialog.getValue().strip().equals(target.key())) {
				return null;
			}
			rename(selected.editor().getEditingDomain(), target, location, references, dialog.getValue().strip());
			return null;
		}
	}

	/** Renames an identifier and its references as one command. */
	public static void rename(EditingDomain domain, Target target, References.KeyLocation location,
			List<Occurrence> references, String key) {
		CompoundCommand command = new CompoundCommand(NLS.bind(Messages.ReferenceHandlers_Rename, target.kind().label(), target.key()));
		command.append(SetCommand.create(domain, location.owner(), location.attribute(),
				ModelCommands.fromString(location.attribute(), key)));
		String text = target.kind().format(key);
		for (Occurrence occurrence : references) {
			if (occurrence.element() != null) {
				EAttribute content = MedbiqPages.contentAttribute(occurrence.element().eClass());
				if (content != null) {
					command.append(SetCommand.create(domain, occurrence.element(), content,
							ModelCommands.fromString(content, text)));
				}
			} else if (occurrence.feature() instanceof EAttribute attribute) {
				command.append(occurrence.index() >= 0
						? new SetCommand(domain, occurrence.owner(), attribute,
								ModelCommands.fromString(attribute, text), occurrence.index())
						: SetCommand.create(domain, occurrence.owner(), attribute,
								ModelCommands.fromString(attribute, text)));
			}
		}
		domain.getCommandStack().execute(command);
	}

	/** The workspace file of an editor's document (also of documents opened from outside the projects), or null. */
	static IFile fileOf(HlomMetadataEditor editor) {
		if (editor.getEditorInput() instanceof IFileEditorInput input) {
			return input.getFile();
		}
		Path path = EditorInputs.pathOf(editor.getEditorInput());
		if (path == null) {
			return null;
		}
		IFile[] files = HermesWorkspace.root().findFilesForLocationURI(path.toUri());
		return files.length == 0 ? null : files[0];
	}
}
