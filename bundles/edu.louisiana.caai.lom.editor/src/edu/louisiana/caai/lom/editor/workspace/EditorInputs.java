/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.nio.file.Path;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;

/**
 * Editor inputs of documents. Documents of Hermes projects are opened as workspace files (so that their markers,
 * the Problems view and the workspace's change tracking apply); other documents by their URI.
 */
public final class EditorInputs {

	private EditorInputs() {
	}

	/** The input to open a document with: a workspace file input for documents of Hermes projects. */
	public static IEditorInput inputFor(URI uri) {
		IFile file = fileOf(uri);
		return file != null ? new FileEditorInput(file) : new URIEditorInput(uri);
	}

	/** The URI the editor's resource has for a document (a platform resource URI for documents of projects). */
	public static URI resourceUriFor(URI uri) {
		IFile file = fileOf(uri);
		return file != null ? URI.createPlatformResourceURI(file.getFullPath().toString(), true) : uri;
	}

	/** The workspace file of a document of a Hermes project (refreshed if it changed on the disk), or {@code null}. */
	public static IFile fileOf(URI uri) {
		IFile file = switch (uri) {
			case URI u when u.isPlatformResource() && u.segmentCount() > 2 -> HermesWorkspace.root().getFile(
					IPath.fromPortableString(u.toPlatformString(true)));
			case URI u when u.isFile() -> HermesWorkspace.fileAt(Path.of(u.toFileString()));
			default -> null;
		};
		if (file == null || !HermesWorkspace.isHermesProject(file.getProject())) {
			return null;
		}
		if (!file.isSynchronized(IResource.DEPTH_ZERO)) {
			try {
				file.refreshLocal(IResource.DEPTH_ZERO, null);
			} catch (CoreException e) {
				HlomEditorPlugin.INSTANCE.log(e);
			}
		}
		return file;
	}

	/** The file system path of an editor input, or {@code null} if it isn't a local file. */
	public static Path pathOf(IEditorInput input) {
		if (input instanceof IFileEditorInput fileInput) {
			return HermesWorkspace.location(fileInput.getFile());
		}
		try {
			URI uri = EditUIUtil.getURI(input);
			return uri == null ? null : pathOf(uri);
		} catch (RuntimeException _) {
			return null;
		}
	}

	/** The file system path of a file or platform resource URI, or {@code null}. */
	public static Path pathOf(URI uri) {
		if (uri.isFile()) {
			return HermesWorkspace.canonical(Path.of(uri.toFileString()));
		}
		if (uri.isPlatformResource() && uri.segmentCount() > 2) {
			return HermesWorkspace.location(HermesWorkspace.root().getFile(IPath.fromPortableString(uri.toPlatformString(true))));
		}
		return null;
	}
}
