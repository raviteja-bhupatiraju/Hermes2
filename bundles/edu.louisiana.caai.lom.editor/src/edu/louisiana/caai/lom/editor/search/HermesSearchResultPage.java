/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Shows the matches of a {@link HermesSearchQuery}: projects, documents and the matching field values (as a tree)
 * or the field values (as a list). Opening a match opens the document at the field.
 */
public final class HermesSearchResultPage extends AbstractTextSearchViewPage {

	/** The id of the page (see plugin.xml). */
	public static final String ID = "edu.louisiana.caai.lom.editor.search.resultPage";

	/** The style of matched text (the search view's highlight colour). */
	private static final StyledString.Styler HIGHLIGHT = StyledString.createColorRegistryStyler(null,
			"org.eclipse.search.ui.match.highlight");

	private Content content;

	public HermesSearchResultPage() {
		super(FLAG_LAYOUT_FLAT | FLAG_LAYOUT_TREE);
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setUseHashlookup(true);
		content = new Content(true);
		viewer.setContentProvider(content);
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new Labels()));
		viewer.setComparator(new Order());
	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {
		viewer.setUseHashlookup(true);
		content = new Content(false);
		viewer.setContentProvider(content);
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new Labels()));
		viewer.setComparator(new Order());
	}

	@Override
	protected void elementsChanged(Object[] objects) {
		refresh();
	}

	@Override
	protected void clear() {
		refresh();
	}

	private void refresh() {
		if (content != null && getViewer() != null) {
			content.rebuild();
			getViewer().refresh();
		}
	}

	/** Replace All... and Replace Selected... (for the results of Search Documents). */
	@Override
	protected void fillContextMenu(org.eclipse.jface.action.IMenuManager menu) {
		super.fillContextMenu(menu);
		AbstractTextSearchResult result = getInput();
		if (result == null || !(result.getQuery() instanceof HermesSearchQuery) || result.getMatchCount() == 0) {
			return;
		}
		menu.appendToGroup(org.eclipse.search.ui.IContextMenuConstants.GROUP_REORGANIZE,
				new org.eclipse.jface.action.Action(Messages.HermesSearchResultPage_ReplaceAll) {
					@Override
					public void run() {
						ReplaceWizard.open(getSite().getShell(), result, List.of());
					}
				});
		List<FieldElement> selected = selectedFields();
		if (!selected.isEmpty()) {
			menu.appendToGroup(org.eclipse.search.ui.IContextMenuConstants.GROUP_REORGANIZE,
					new org.eclipse.jface.action.Action(Messages.HermesSearchResultPage_ReplaceSelected) {
						@Override
						public void run() {
							ReplaceWizard.open(getSite().getShell(), result, selected);
						}
					});
		}
	}

	/** The values selected in the view: selected fields, and all fields of selected documents and projects. */
	private List<FieldElement> selectedFields() {
		AbstractTextSearchResult result = getInput();
		Set<FieldElement> fields = new LinkedHashSet<>();
		if (getViewer().getSelection() instanceof IStructuredSelection selection) {
			for (Object item : selection.toList()) {
				for (Object element : result.getElements()) {
					if (element instanceof FieldElement field && (field.equals(item)
							|| item instanceof IFile file && field.file().equals(file)
							|| item instanceof IProject project && field.file().getProject().equals(project))) {
						fields.add(field);
					}
				}
			}
		}
		return List.copyOf(fields);
	}

	@Override
	protected void showMatch(Match match, int currentOffset, int currentLength, boolean activate) {
		if (match.getElement() instanceof FieldElement element) {
			open(element.file(), element.path(), activate);
		}
	}

	@Override
	protected void handleOpen(OpenEvent event) {
		if (event.getSelection() instanceof IStructuredSelection selection && selection.getFirstElement() instanceof IFile file) {
			open(file, null, true);
			return;
		}
		super.handleOpen(event);
	}

	private void open(IFile file, String path, boolean activate) {
		IWorkbenchPage page = getSite().getPage();
		IEditorPart editor = page.findEditor(new FileEditorInput(file));
		if (editor == null) {
			HlomEditorAdvisor.openEditor(page.getWorkbenchWindow().getWorkbench(),
					URI.createPlatformResourceURI(file.getFullPath().toString(), true));
			editor = page.findEditor(new FileEditorInput(file));
		} else if (activate) {
			page.activate(editor);
		} else {
			page.bringToTop(editor);
		}
		if (path != null && editor instanceof HlomMetadataEditor hlom) {
			hlom.revealPath(path);
		}
	}

	/** Projects, documents and field values; or only the field values (flat layout). */
	private final class Content implements ITreeContentProvider {
		private final boolean tree;
		private final Map<Object, Set<Object>> children = new HashMap<>();

		Content(boolean tree) {
			this.tree = tree;
		}

		void rebuild() {
			children.clear();
			AbstractTextSearchResult result = getInput();
			if (result == null || !tree) {
				return;
			}
			for (Object element : result.getElements()) {
				if (element instanceof FieldElement field && result.getMatchCount(field) > 0) {
					add(field.file(), field);
					add(field.file().getProject(), field.file());
					add(result, field.file().getProject());
				}
			}
		}

		private void add(Object parent, Object child) {
			children.computeIfAbsent(parent, _ -> new LinkedHashSet<>()).add(child);
		}

		@Override
		public Object[] getElements(Object input) {
			if (!(input instanceof AbstractTextSearchResult result)) {
				return new Object[0];
			}
			if (!tree) {
				return Arrays.stream(result.getElements()).filter(e -> result.getMatchCount(e) > 0).toArray();
			}
			if (children.isEmpty()) {
				rebuild();
			}
			return children.getOrDefault(result, Set.of()).toArray();
		}

		@Override
		public Object[] getChildren(Object parent) {
			return children.getOrDefault(parent, Set.of()).toArray();
		}

		@Override
		public Object getParent(Object element) {
			return switch (element) {
				case FieldElement field -> field.file();
				case IFile file -> file.getProject();
				default -> null;
			};
		}

		@Override
		public boolean hasChildren(Object element) {
			return !(element instanceof FieldElement);
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			children.clear();
		}
	}

	/** Projects and documents by name; field values in document order. */
	private static final class Order extends ViewerComparator {
		private static final Comparator<FieldElement> FIELDS = Comparator
				.comparing((FieldElement f) -> f.file().getFullPath().toString()).thenComparingInt(FieldElement::order);

		@Override
		public int category(Object element) {
			return element instanceof FieldElement ? 1 : 0;
		}

		@Override
		public int compare(Viewer viewer, Object a, Object b) {
			if (a instanceof FieldElement x && b instanceof FieldElement y) {
				return FIELDS.compare(x, y);
			}
			return super.compare(viewer, a, b);
		}
	}

	private final class Labels extends ColumnLabelProvider implements IStyledLabelProvider {
		@Override
		public StyledString getStyledText(Object element) {
			AbstractTextSearchResult result = getInput();
			return switch (element) {
				case IProject project -> new StyledString(HermesWorkspace.name(project))
						.append(count(project, result), StyledString.COUNTER_STYLER);
				case IFile file -> {
					StyledString text = new StyledString(file.getName());
					LibraryEntry entry = DocumentIndex.get(file);
					if (entry != null && !entry.title().equals(file.getName())) {
						text.append("  " + entry.title(), StyledString.QUALIFIER_STYLER);
					}
					yield text.append(count(file, result), StyledString.COUNTER_STYLER);
				}
				case FieldElement field -> fieldText(field, result);
				default -> new StyledString(String.valueOf(element));
			};
		}

		private StyledString fieldText(FieldElement field, AbstractTextSearchResult result) {
			StyledString text = new StyledString(field.label() + ": ", StyledString.QUALIFIER_STYLER);
			// Whitespace is collapsed to single spaces of the same length, so match offsets stay valid.
			String value = field.text().replaceAll("\\s", " ");
			List<Match> matches = result == null ? List.of()
					: Arrays.stream(result.getMatches(field)).sorted(Comparator.comparingInt(Match::getOffset)).toList();
			// Show the text around the first match when the value is long.
			int start = 0;
			if (value.length() > 160 && !matches.isEmpty() && matches.getFirst().getOffset() > 60) {
				start = matches.getFirst().getOffset() - 40;
				text.append("…");
			}
			int end = Math.min(value.length(), start + 200);
			int position = start;
			for (Match match : matches) {
				int from = Math.max(match.getOffset(), position);
				int to = Math.min(match.getOffset() + match.getLength(), end);
				if (from >= end || to <= from) {
					continue;
				}
				text.append(value.substring(position, from));
				text.append(value.substring(from, to), HIGHLIGHT);
				position = to;
			}
			text.append(value.substring(Math.min(position, end), end));
			if (end < value.length()) {
				text.append("…");
			}
			if (getLayout() == FLAG_LAYOUT_FLAT) {
				text.append("  – " + field.file().getFullPath().makeRelative(), StyledString.QUALIFIER_STYLER);
			}
			return text;
		}

		private static String count(Object container, AbstractTextSearchResult result) {
			if (result == null) {
				return "";
			}
			int matches = 0;
			for (Object element : result.getElements()) {
				if (element instanceof FieldElement field
						&& (field.file().equals(container) || field.file().getProject().equals(container))) {
					matches += result.getMatchCount(field);
				}
			}
			return " (" + matches + (matches == 1 ? " match)" : " matches)");
		}

		@Override
		public String getText(Object element) {
			return getStyledText(element).getString();
		}

		@Override
		public Image getImage(Object element) {
			return switch (element) {
				case IProject _ -> HlomTheme.image("icons/projects/project.png");
				case IFile file -> HlomTheme.image(file.getName().toLowerCase(Locale.ROOT).endsWith(".json")
						? "icons/pages/json.png" : "icons/pages/xml.png");
				case FieldElement _ -> HlomTheme.image("icons/pages/tree.png");
				default -> null;
			};
		}

		@Override
		public String getToolTipText(Object element) {
			return element instanceof FieldElement field ? field.path() + "\n\n" + field.text() : null;
		}
	}
}
