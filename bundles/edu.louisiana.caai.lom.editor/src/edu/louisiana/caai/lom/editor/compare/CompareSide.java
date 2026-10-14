/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import org.eclipse.osgi.util.NLS;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorPart;

import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;

/**
 * One side of a comparison: either a file (re-read on refresh) or the live model of an open editor (which
 * includes unsaved changes).
 */
public final class CompareSide {

	/** Where a side's document comes from; shown as a tag in the header. */
	public enum Origin {
		FILE("FILE"), SAVED("SAVED"), EDITOR("EDITOR");

		private final String tag;

		Origin(String tag) {
			this.tag = tag;
		}

		/** The short tag shown in the header. */
		public String tag() {
			return tag;
		}
	}

	private final Origin origin;
	private final String path;
	private final IEditorPart editor;
	private Resource resource;

	private CompareSide(Origin origin, String path, IEditorPart editor, Resource resource) {
		this.origin = origin;
		this.path = path;
		this.editor = editor;
		this.resource = resource;
	}

	/** A file on disk, loaded leniently. */
	public static CompareSide file(String path) {
		CompareSide side = new CompareSide(Origin.FILE, path, null, null);
		side.reload();
		return side;
	}

	/** The saved version of an editor's document, i.e. its file on disk. */
	public static CompareSide saved(String path) {
		CompareSide side = new CompareSide(Origin.SAVED, path, null, null);
		side.reload();
		return side;
	}

	/** The live, possibly unsaved model of an editor; {@code null} if the editor holds no MedBiquitous document. */
	public static CompareSide editor(IEditorPart editor) {
		Resource resource = editorResource(editor);
		if (resource == null || DocumentTypes.of(resource) == null) {
			return null;
		}
		URI uri = resource.getURI();
		java.nio.file.Path file = uri == null ? null : EditorInputs.pathOf(uri);
		String path = uri == null ? editor.getTitle() : file != null ? file.toString() : uri.toString();
		return new CompareSide(Origin.EDITOR, path, editor, resource);
	}

	/** The first resource of an editor that provides an editing domain, or {@code null}. */
	public static Resource editorResource(IEditorPart editor) {
		if (editor instanceof IEditingDomainProvider provider && provider.getEditingDomain() != null
				&& !provider.getEditingDomain().getResourceSet().getResources().isEmpty()) {
			return provider.getEditingDomain().getResourceSet().getResources().getFirst();
		}
		return null;
	}

	/** Re-reads a file side from disk; editor sides are live and need no reloading. */
	public void reload() {
		if (origin != Origin.EDITOR) {
			URI uri = URI.createFileURI(new File(path).getAbsolutePath());
			try {
				resource = DocumentCheck.load(java.nio.file.Files.readString(new File(path).toPath()), uri);
			} catch (java.io.IOException | RuntimeException _) {
				resource = HlomResources.loadLenient(uri);
			}
		}
	}

	public Origin origin() {
		return origin;
	}

	/** The editor of an {@link Origin#EDITOR} side, otherwise {@code null}. */
	public IEditorPart editor() {
		return editor;
	}

	public Resource resource() {
		return resource;
	}

	/** The Healthcare LOM document, or {@code null} if the file is missing or not an HLOM document. */
	public DocumentRoot root() {
		return HlomResources.getDocumentRoot(resource);
	}

	/** The type of the document, or {@code null} if the file is missing or not a MedBiquitous document. */
	public DocumentType type() {
		return resource == null ? null : DocumentTypes.of(resource);
	}

	/** The file path (or URI) of the document. */
	public String path() {
		return path;
	}

	/** The file name. */
	public String fileName() {
		String name = new File(path).getName();
		return name.isEmpty() ? path : name;
	}

	/** The record title, falling back to the file name. */
	public String title() {
		DocumentRoot root = root();
		if (root != null) {
			return HlomSummary.of(root).titleOr(fileName());
		}
		DocumentType type = type();
		String title = type == null ? null : type.title(resource.getContents().getFirst());
		return title == null || title.isBlank() ? fileName() : title;
	}

	/** A one line description of where the document comes from. */
	public String description() {
		return switch (origin) {
		case EDITOR -> fileName() + (editor != null && editor.isDirty() ? " — editor, unsaved changes" : " — editor");
		case SAVED -> NLS.bind(Messages.CompareSide_SavedOnDisk, fileName());
		case FILE -> {
			File parent = new File(path).getAbsoluteFile().getParentFile();
			yield parent == null ? path : fileName() + " — " + parent.getPath();
		}
		};
	}
}
