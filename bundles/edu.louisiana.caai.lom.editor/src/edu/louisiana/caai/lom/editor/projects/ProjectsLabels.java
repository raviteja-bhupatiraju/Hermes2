/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * The labels of the Projects view: projects with their name and document counts, documents with their title and
 * an icon of their validation state (from the markers of the background validation); the description of the
 * selection in the status line and the tool tips. The workbench decorators (such as the Git state) are added by
 * the navigator.
 */
public final class ProjectsLabels extends LabelProvider implements ICommonLabelProvider, IStyledLabelProvider {

	/** Counts of a project's documents and problems. */
	private record Counts(int documents, int errors, int warnings) {
	}

	@Override
	public void init(ICommonContentExtensionSite site) {
		// Nothing to configure.
	}

	private static Counts counts(IProject project) {
		List<DocumentIndex.Document> documents = DocumentIndex.documents(project);
		int errors = (int) documents.stream().filter(document -> document.entry().errors() > 0).count();
		int warnings = (int) documents.stream()
				.filter(document -> document.entry().errors() == 0 && document.entry().warnings() > 0)
				.count();
		return new Counts(documents.size(), errors, warnings);
	}

	static boolean isJson(IFile file) {
		Path location = HermesWorkspace.location(file);
		return location != null && Format.of(location) == Format.JSON;
	}

	@Override
	public StyledString getStyledText(Object element) {
		return switch (element) {
			case IProject project -> {
				StyledString text = new StyledString(HermesWorkspace.name(project));
				if (DocumentIndex.isIndexed(project)) {
					Counts counts = counts(project);
					String summary = "  " + counts.documents() + (counts.documents() == 1 ? " document" : " documents");
					if (counts.errors() > 0) {
						summary += " · " + counts.errors() + " with errors";
					}
					text.append(summary, StyledString.COUNTER_STYLER);
				}
				yield text;
			}
			case IFile file -> {
				StyledString text = new StyledString(file.getName());
				LibraryEntry entry = DocumentIndex.get(file);
				if (entry != null) {
					String title = entry.title();
					if (title != null && !title.isBlank() && !title.equals(file.getName())) {
						text.append("  " + title, StyledString.QUALIFIER_STYLER);
					}
				}
				yield text;
			}
			case IResource resource -> new StyledString(resource.getName());
			default -> new StyledString(String.valueOf(element));
		};
	}

	@Override
	public String getText(Object element) {
		return getStyledText(element).getString();
	}

	@Override
	public Image getImage(Object element) {
		return switch (element) {
			case IProject _ -> HlomTheme.image("icons/projects/project.png");
			case IFolder _ -> HlomTheme.image("icons/library/folder.png");
			// The problem state is the overlay of the ProblemDecorator.
			case IFile file -> HlomTheme.image(isJson(file) ? "icons/pages/json.png" : "icons/pages/xml.png");
			default -> null;
		};
	}

	/** The status line text of a selected element: its location and state. */
	@Override
	public String getDescription(Object element) {
		String tip = toolTip(element);
		return tip == null ? null : tip.replace('\n', ' ').replace("  ", " ");
	}

	/** The tool tip of an element: its title or description, location and validation state. */
	static String toolTip(Object element) {
		return switch (element) {
			case IProject project -> {
				HermesProject metadata = HermesWorkspace.metadata(project);
				Counts counts = counts(project);
				StringBuilder tip = new StringBuilder(HermesWorkspace.name(project));
				if (metadata != null && !metadata.getDescription().isBlank()) {
					tip.append("\n").append(metadata.getDescription());
				}
				tip.append("\n\n").append(HermesWorkspace.location(project));
				tip.append("\n").append(counts.documents()).append(Messages.ProjectsLabels_Documents).append(counts.errors())
						.append(Messages.ProjectsLabels_WithErrors).append(counts.warnings()).append(Messages.ProjectsLabels_WithWarnings);
				if (metadata != null) {
					tip.append(Messages.ProjectsLabels_NewDocuments).append(metadata.getFormat() == Format.JSON ? "JSON" : "XML");
				}
				yield tip.toString();
			}
			case IFile file -> {
				LibraryEntry entry = DocumentIndex.get(file);
				StringBuilder tip = new StringBuilder();
				if (entry != null) {
					tip.append(entry.title()).append("\n");
				}
				tip.append(HermesWorkspace.location(file));
				tip.append("\n").append(isJson(file) ? "JSON" : "XML");
				if (entry != null) {
					tip.append(" · ").append(entry.errors() == 0 && entry.warnings() == 0 ? "valid"
							: entry.errors() + " errors, " + entry.warnings() + " warnings");
				}
				yield tip.toString();
			}
			case IFolder folder -> String.valueOf(HermesWorkspace.location(folder));
			case null, default -> null;
		};
	}

	@Override
	public void restoreState(IMemento memento) {
		// No state.
	}

	@Override
	public void saveState(IMemento memento) {
		// No state.
	}
}
