/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.workspace.HermesBuilder;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Hit;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;

/**
 * Searches the field values of the documents of Hermes projects. Documents with unsaved changes are searched in
 * their edited state.
 */
public final class HermesSearchQuery implements ISearchQuery {

	private final Query query;
	private final List<IResource> scope;
	private final String scopeLabel;
	private final HermesSearchResult result = new HermesSearchResult(this);

	/**
	 * @param query      what to search for
	 * @param scope      the projects, folders and files to search
	 * @param scopeLabel a description of the scope, such as {@code workspace}
	 */
	public HermesSearchQuery(Query query, List<? extends IResource> scope, String scopeLabel) {
		this.query = query;
		this.scope = List.copyOf(scope);
		this.scopeLabel = scopeLabel;
	}

	/** A query searching all Hermes projects. */
	public static HermesSearchQuery inWorkspace(Query query) {
		return new HermesSearchQuery(query, HermesWorkspace.projects(), "workspace");
	}

	public Query query() {
		return query;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		result.removeAll();
		try {
			query.pattern();
		} catch (PatternSyntaxException e) {
			return Status.error(NLS.bind(Messages.HermesSearchQuery_InvalidRegularExpression, e.getDescription()));
		}
		Set<IFile> files = documents();
		Map<IFile, String> edited = editedDocuments(files);
		SubMonitor progress = SubMonitor.convert(monitor, NLS.bind(Messages.HermesSearchQuery_Searching, query.describe()), files.size());
		for (IFile file : files) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}
			progress.subTask(file.getFullPath().makeRelative().toString());
			search(file, edited.get(file));
			progress.worked(1);
		}
		return Status.OK_STATUS;
	}

	private void search(IFile file, String editedXml) {
		java.nio.file.Path location = HermesWorkspace.location(file);
		if (location == null) {
			return;
		}
		URI uri = URI.createFileURI(location.toString());
		Resource resource;
		try {
			String text = editedXml != null ? editedXml : java.nio.file.Files.readString(location);
			// Loaded like the editor does, by the document's type (Healthcare LOM or another standard).
			resource = DocumentCheck.load(text, uri);
		} catch (java.io.IOException | RuntimeException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return;
		}
		List<Hit> hits = DocumentSearch.search(resource, query);
		int order = 0;
		for (Hit hit : hits) {
			FieldElement element = new FieldElement(file, hit.value().path(), hit.value().label(), hit.value().text(),
					order++);
			for (int[] match : hit.matches()) {
				result.addMatch(new FieldMatch(element, match[0], match[1]));
			}
		}
	}

	/** The HLOM documents in the scope. */
	private Set<IFile> documents() {
		Set<IFile> files = new LinkedHashSet<>();
		for (IResource resource : scope) {
			if (!resource.isAccessible() || !HermesWorkspace.isHermesProject(resource.getProject())) {
				continue;
			}
			try {
				resource.accept(child -> switch (child) {
					case IFile file -> {
						if (HermesBuilder.isDocument(file)) {
							files.add(file);
						}
						yield false;
					}
					case IContainer container -> !HermesBuilder.isSkipped(container);
					default -> false;
				});
			} catch (CoreException e) {
				HlomEditorPlugin.INSTANCE.log(e);
			}
		}
		return files;
	}

	/** The XML of the documents in the scope that are open with unsaved changes. */
	private static Map<IFile, String> editedDocuments(Set<IFile> files) {
		Map<IFile, String> edited = new ConcurrentHashMap<>();
		if (!PlatformUI.isWorkbenchRunning()) {
			return edited;
		}
		Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(() -> {
			for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
				for (IWorkbenchPage page : window.getPages()) {
					for (IEditorReference reference : page.getEditorReferences()) {
						IEditorPart editor = reference.getEditor(false);
						if (editor instanceof HlomMetadataEditor hlom && hlom.isDirty()
								&& hlom.getEditorInput() instanceof IFileEditorInput input
								&& files.contains(input.getFile())) {
							String xml = hlom.currentXml();
							if (xml != null) {
								edited.put(input.getFile(), xml);
							}
						}
					}
				}
			}
		});
		return edited;
	}

	@Override
	public String getLabel() {
		return NLS.bind(Messages.HermesSearchQuery_In, query.describe(), scopeLabel);
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ISearchResult getSearchResult() {
		return result;
	}

	/** The files of the projects in a list of names, for the "selected projects" scope. */
	static List<IProject> projectsNamed(String[] names) {
		List<IProject> projects = new ArrayList<>();
		for (String name : names == null ? new String[0] : names) {
			IProject project = HermesWorkspace.root().getProject(name);
			if (project.isAccessible()) {
				projects.add(project);
			}
		}
		return projects;
	}
}
