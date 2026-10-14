/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import org.eclipse.osgi.util.NLS;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Kind;
import edu.louisiana.caai.lom.model.support.report.HtmlReport;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * The metadata library: all HLOM records below a folder in one table, with their kind, credits, audience and
 * validation status. Records are found and validated in the background; the table can be searched, filtered by
 * kind and status, and sorted by any column. Saving a record in an editor revalidates it here.
 */
public class LibraryView extends ViewPart implements IShowInSource {

	public static final String ID = "edu.louisiana.caai.lom.editor.library.LibraryView";

	/** Preference holding the last library folder. */
	static final String FOLDER_PREFERENCE = "library.folder";

	/** System property naming a folder to show initially (overrides the preference; used for demos and tests). */
	static final String FOLDER_PROPERTY = "hlom.library.folder";

	private static final int KIND_GROUP = 0;
	private static final int STATUS_GROUP = 1;

	private final Map<Path, LibraryEntry> entries = new LinkedHashMap<>();
	private final Set<IEditorPart> observedEditors = new HashSet<>();
	private final Object jobFamily = new Object();

	private Path folder;
	private boolean scanning;
	private LibraryHeader header;
	private FilterChips chips;
	private Text search;
	private TableViewer viewer;
	private Composite stack;
	private Composite tablePage;
	private Composite emptyPage;
	private Label emptyHeading;
	private Label emptyText;
	private Sort sort = new Sort(Column.TITLE, SWT.UP);
	private String[] searchTokens = new String[0];

	private Action openAction;
	private Action validateAction;
	private Action exportCardAction;
	private Action revealAction;
	private Action copyPathAction;

	/** Columns of the table. */
	private enum Column {
		STATUS, TITLE, KIND, CREDITS, AUDIENCE, MODIFIED, ERRORS, WARNINGS, COMPLETENESS, FILE
	}

	/** The current sort column and direction. */
	private record Sort(Column column, int direction) {
	}

	private final IPropertyListener dirtyListener = (source, propId) -> {
		if ((propId == IEditorPart.PROP_DIRTY || propId == IWorkbenchPart.PROP_TITLE)
				&& source instanceof IEditorPart editor && !editor.isDirty()) {
			Path path = pathOf(editor.getEditorInput());
			if (path != null) {
				refreshFile(path);
			}
		}
	};

	private final IPartListener2 partListener = new IPartListener2() {
		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			if (partRef.getPart(false) instanceof IEditorPart editor) {
				observe(editor);
			}
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			if (partRef.getPart(false) instanceof IEditorPart editor && observedEditors.remove(editor)) {
				editor.removePropertyListener(dirtyListener);
			}
		}
	};

	@Override
	public void createPartControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.LIBRARY_VIEW);
		Composite root = new Composite(parent, SWT.NONE);
		root.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(root);

		header = new LibraryHeader(root);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(header);

		Composite filterBar = new Composite(root, SWT.NONE);
		filterBar.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.fillDefaults().numColumns(2).margins(14, 10).spacing(16, 0).applyTo(filterBar);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(filterBar);
		search = new Text(filterBar, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		search.setMessage(Messages.LibraryView_SearchTitlesKeywordsMeSHIds);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).hint(300, SWT.DEFAULT).applyTo(search);
		search.addModifyListener(event -> {
			String text = search.getText();
			search.getDisplay().timerExec(180, () -> {
				if (!search.isDisposed() && text.equals(search.getText())) {
					searchTokens = text.toLowerCase(Locale.ROOT).trim().split("\\s+");
					refreshTable();
				}
			});
		});
		chips = new FilterChips(filterBar);
		chips.add(KIND_GROUP, null, Messages.LibraryView_All, HlomTheme.INK, true);
		chips.add(KIND_GROUP, Kind.EDUCATIONAL_OFFERING, Messages.LibraryView_Offerings, HlomTheme.ACCENT, false);
		chips.add(KIND_GROUP, Kind.IMAGE_ASSET, Messages.LibraryView_ImageAssets, HlomTheme.TEAL, false);
		chips.add(KIND_GROUP, Kind.LEARNING_OBJECT, Messages.LibraryView_LearningObjects, HlomTheme.SLATE, false);
		chips.add(STATUS_GROUP, LibraryEntry.Status.ERRORS, Messages.LibraryView_Errors, HlomTheme.ACCENT, false);
		chips.add(STATUS_GROUP, LibraryEntry.Status.WARNINGS, Messages.LibraryView_Warnings, HlomTheme.AMBER, false);
		chips.add(STATUS_GROUP, LibraryEntry.Status.VALID, Messages.LibraryView_Valid, HlomTheme.TEAL, false);
		chips.addSelectionListener(this::refreshTable);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(chips);

		Label line = new Label(root, SWT.NONE);
		line.setBackground(HlomTheme.color(HlomTheme.BORDER));
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 1).applyTo(line);

		stack = new Composite(root, SWT.NONE);
		stack.setLayout(new StackLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(stack);
		createTable();
		createEmptyPage();

		createActions();
		createMenus();
		getSite().setSelectionProvider(viewer);

		IWorkbenchPage page = getSite().getPage();
		page.addPartListener(partListener);
		for (IEditorReference reference : page.getEditorReferences()) {
			if (reference.getEditor(false) != null) {
				observe(reference.getEditor(false));
			}
		}

		Path initial = initialFolder();
		if (initial != null) {
			setFolder(initial);
		} else {
			updateState();
		}
	}

	private void createTable() {
		tablePage = new Composite(stack, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		tablePage.setLayout(layout);
		viewer = new TableViewer(tablePage, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		table.setForeground(HlomTheme.color(HlomTheme.INK));
		viewer.setUseHashlookup(true);
		viewer.setComparer(new IElementComparer() {
			@Override
			public boolean equals(Object a, Object b) {
				return a instanceof LibraryEntry ea && b instanceof LibraryEntry eb ? ea.path().equals(eb.path())
						: a.equals(b);
			}

			@Override
			public int hashCode(Object element) {
				return element instanceof LibraryEntry entry ? entry.path().hashCode() : element.hashCode();
			}
		});
		viewer.setContentProvider((org.eclipse.jface.viewers.IStructuredContentProvider) input -> entries.values()
				.toArray());

		column(layout, Column.STATUS, "", new ColumnPixelData(34, false), LibraryCells.status(),
				Messages.LibraryView_ValidationStatus);
		column(layout, Column.TITLE, Messages.LibraryView_Title, new ColumnWeightData(36, 220), titleCell(), null);
		column(layout, Column.KIND, Messages.LibraryView_Kind, new ColumnPixelData(150, true), LibraryCells.kind(), null);
		column(layout, Column.CREDITS, Messages.LibraryView_Credits, new ColumnWeightData(14, 90),
				LibraryCells.text(e -> e.summary().creditsLabel(), e -> HlomTheme.INK, false), null);
		column(layout, Column.AUDIENCE, Messages.LibraryView_Audience, new ColumnWeightData(16, 90),
				LibraryCells.text(e -> e.summary().audienceLabel(), e -> HlomTheme.SLATE, false), null);
		column(layout, Column.MODIFIED, Messages.LibraryView_Modified, new ColumnPixelData(132, true),
				LibraryCells.text(e -> LibraryCells.modified(e.modified()), e -> HlomTheme.SLATE, false), null);
		column(layout, Column.ERRORS, Messages.LibraryView_Errors, new ColumnPixelData(64, true),
				LibraryCells.text(e -> Integer.toString(e.errors()),
						e -> e.errors() > 0 ? HlomTheme.ACCENT : HlomTheme.MUTED, true),
				Messages.LibraryView_NumberOfValidationErrors);
		column(layout, Column.WARNINGS, Messages.LibraryView_Warnings, new ColumnPixelData(82, true),
				LibraryCells.text(e -> Integer.toString(e.warnings()),
						e -> e.warnings() > 0 ? HlomTheme.AMBER : HlomTheme.MUTED, true),
				Messages.LibraryView_NumberOfValidationWarnings);
		if (HlomLibrary.getCompletenessScorer() != null) {
			column(layout, Column.COMPLETENESS, Messages.LibraryView_Complete, new ColumnPixelData(110, true),
					LibraryCells.completeness(), Messages.LibraryView_HowCompletelyTheRecordIs);
		}
		column(layout, Column.FILE, Messages.LibraryView_File, new ColumnWeightData(12, 90),
				LibraryCells.text(LibraryEntry::fileName, e -> HlomTheme.MUTED, false), null);
		OwnerDrawLabelProvider.setUpOwnerDraw(viewer);
		ColumnViewerToolTipSupport.enableFor(viewer);

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer v, Object e1, Object e2) {
				int result = comparator(sort.column()).compare((LibraryEntry) e1, (LibraryEntry) e2);
				if (result == 0) {
					result = String.CASE_INSENSITIVE_ORDER.compare(((LibraryEntry) e1).title(),
							((LibraryEntry) e2).title());
				}
				return sort.direction() == SWT.UP ? result : -result;
			}
		});
		viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer v, Object parentElement, Object element) {
				return matches((LibraryEntry) element);
			}
		});
		viewer.addOpenListener(event -> openSelection());
		viewer.addSelectionChangedListener(event -> selectionChanged());
		viewer.setInput(entries);
		updateSortIndicator();
	}

	private OwnerDrawLabelProvider titleCell() {
		return new LibraryCells.Cell() {
			private final LibraryCells.Cell delegate = LibraryCells.title();

			@Override
			void paint(org.eclipse.swt.graphics.GC gc, LibraryEntry entry, org.eclipse.swt.graphics.Rectangle b) {
				delegate.base = base;
				delegate.paint(gc, entry, b);
			}

			@Override
			public String getToolTipText(Object element) {
				return tooltip((LibraryEntry) element);
			}
		};
	}

	private void column(TableColumnLayout layout, Column key, String title, ColumnLayoutData data,
			OwnerDrawLabelProvider cell, String tooltip) {
		boolean numeric = key == Column.ERRORS || key == Column.WARNINGS || key == Column.COMPLETENESS;
		TableViewerColumn column = new TableViewerColumn(viewer, numeric ? SWT.RIGHT : SWT.LEFT);
		column.getColumn().setText(title);
		column.getColumn().setData(key);
		column.getColumn().setMoveable(key != Column.STATUS);
		if (tooltip != null) {
			column.getColumn().setToolTipText(tooltip);
		}
		column.setLabelProvider(cell);
		layout.setColumnData(column.getColumn(), data);
		column.getColumn().addListener(SWT.Selection, event -> {
			sort = new Sort(key, sort.column() == key && sort.direction() == SWT.UP ? SWT.DOWN : SWT.UP);
			updateSortIndicator();
			viewer.refresh();
		});
	}

	private void updateSortIndicator() {
		Table table = viewer.getTable();
		for (TableColumn column : table.getColumns()) {
			if (column.getData() == sort.column()) {
				table.setSortColumn(column);
				table.setSortDirection(sort.direction());
			}
		}
	}

	private static Comparator<LibraryEntry> comparator(Column column) {
		return switch (column) {
			case STATUS -> Comparator.comparing((LibraryEntry e) -> e.status()).reversed();
			case TITLE -> Comparator.comparing(LibraryEntry::title, String.CASE_INSENSITIVE_ORDER);
			case KIND -> Comparator.comparing(LibraryCells::kindLabel);
			case CREDITS -> Comparator.comparing(e -> e.summary().credits().stream()
					.map(HlomSummary.Credit::number).filter(n -> n != null).reduce(BigDecimal.ZERO, BigDecimal::add));
			case AUDIENCE -> Comparator.comparing(e -> e.summary().audienceLabel(), String.CASE_INSENSITIVE_ORDER);
			case MODIFIED -> Comparator.comparing(e -> e.modified() == null ? Instant.EPOCH : e.modified());
			case ERRORS -> Comparator.comparingInt(LibraryEntry::errors);
			case WARNINGS -> Comparator.comparingInt(LibraryEntry::warnings);
			case COMPLETENESS -> Comparator.comparingInt(LibraryEntry::completeness);
			case FILE -> Comparator.comparing(LibraryEntry::fileName, String.CASE_INSENSITIVE_ORDER);
		};
	}

	private void createEmptyPage() {
		emptyPage = new Composite(stack, SWT.NONE);
		emptyPage.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		emptyPage.setLayout(new GridLayout());
		Composite center = new Composite(emptyPage, SWT.NONE);
		center.setBackground(emptyPage.getBackground());
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, true).applyTo(center);
		GridLayoutFactory.fillDefaults().spacing(0, 8).applyTo(center);
		Label icon = new Label(center, SWT.NONE);
		icon.setBackground(center.getBackground());
		icon.setImage(HlomTheme.image("icons/library/library@2x.png"));
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(icon);
		emptyHeading = new Label(center, SWT.CENTER);
		emptyHeading.setFont(HlomTheme.sectionFont());
		emptyHeading.setForeground(HlomTheme.color(HlomTheme.INK));
		emptyHeading.setBackground(center.getBackground());
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(emptyHeading);
		emptyText = new Label(center, SWT.CENTER | SWT.WRAP);
		emptyText.setForeground(HlomTheme.color(HlomTheme.SLATE));
		emptyText.setBackground(center.getBackground());
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).hint(420, SWT.DEFAULT).applyTo(emptyText);
		Button choose = new Button(center, SWT.PUSH);
		choose.setText(Messages.LibraryView_ChooseFolder);
		choose.setImage(HlomTheme.image("icons/library/folder.png"));
		choose.addListener(SWT.Selection, event -> chooseFolder());
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).indent(0, 8).applyTo(choose);
	}

	private void createActions() {
		openAction = action(Messages.LibraryView_Open, "icons/library/open.png", Messages.LibraryView_OpenTheRecordInThe, this::openSelection);
		validateAction = action(Messages.LibraryView_ValidateAgain, "icons/library/validate.png",
				Messages.LibraryView_ReloadAndValidateTheSelected, () -> revalidate(selectedEntries()));
		exportCardAction = action(Messages.LibraryView_ExportHTMLCard, "icons/library/html.png",
				Messages.LibraryView_SaveTheSelectedRecordAs, this::exportCard);
		revealAction = action(Messages.LibraryView_RevealInExplorer, "icons/library/reveal.png",
				Messages.LibraryView_OpenTheFolderContainingThe, this::reveal);
		copyPathAction = action(Messages.LibraryView_CopyPath, null, Messages.LibraryView_CopyTheFilePathTo, this::copyPath);
		selectionChanged();
	}

	private static Action action(String text, String icon, String tooltip, Runnable run) {
		Action action = new Action(text) {
			@Override
			public void run() {
				run.run();
			}
		};
		action.setToolTipText(tooltip);
		if (icon != null) {
			action.setImageDescriptor(descriptor(icon));
		}
		return action;
	}

	private static ImageDescriptor descriptor(String path) {
		java.net.URL url = LibraryView.class.getResource("/" + path);
		return url == null ? null : ImageDescriptor.createFromURL(url);
	}

	private void createMenus() {
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(action(Messages.LibraryView_ChooseFolder, "icons/library/folder.png", Messages.LibraryView_ChooseTheLibraryFolder,
				this::chooseFolder));
		toolBar.add(action(Messages.LibraryView_Refresh, "icons/library/refresh.png", Messages.LibraryView_ScanTheLibraryFolderAgain, this::rescan));
		toolBar.add(new Separator());
		toolBar.add(action(Messages.LibraryView_ExportLibraryReport, "icons/library/report.png",
				Messages.LibraryView_SaveTheRecordsShownAs, this::exportLibrary));

		MenuManager menu = new MenuManager();
		menu.setRemoveAllWhenShown(true);
		menu.addMenuListener(this::fillContextMenu);
		viewer.getTable().setMenu(menu.createContextMenu(viewer.getTable()));
		getSite().registerContextMenu(menu, viewer);
	}

	private void fillContextMenu(IMenuManager menu) {
		menu.add(openAction);
		// Navigate > Show In for records of the projects (the Projects view, the file manager, Git history).
		MenuManager showIn = new MenuManager(Messages.LibraryView_ShoWIn, "showIn");
		showIn.add(ContributionItemFactory.VIEWS_SHOW_IN.create(getSite().getWorkbenchWindow()));
		menu.add(showIn);
		menu.add(new Separator());
		menu.add(validateAction);
		menu.add(exportCardAction);
		menu.add(new Separator());
		menu.add(revealAction);
		menu.add(copyPathAction);
		// Contributions of other plug-ins for records of the projects (adapted to their files), such as the Team
		// menu of Git.
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * Show In: the workspace files of the selected records that belong to projects (the records adapt to their
	 * files, see {@code LibraryEntryAdapterFactory}).
	 */
	@Override
	public ShowInContext getShowInContext() {
		List<IFile> files = selectedEntries().stream().map(entry -> Adapters.adapt(entry, IFile.class))
				.filter(java.util.Objects::nonNull).toList();
		return files.isEmpty() ? null : new ShowInContext(null, new StructuredSelection(files));
	}

	// Folder and scanning

	private Path initialFolder() {
		String property = System.getProperty(FOLDER_PROPERTY);
		String value = property != null && !property.isBlank() ? property : preferences().getString(FOLDER_PREFERENCE);
		if (value == null || value.isBlank()) {
			return null;
		}
		Path path = Path.of(value);
		return Files.isDirectory(path) ? path : null;
	}

	private static IPreferenceStore preferences() {
		return HlomEditorPlugin.getPlugin().getPreferenceStore();
	}

	/** Lets the user choose the library folder. */
	public void chooseFolder() {
		DirectoryDialog dialog = new DirectoryDialog(getSite().getShell());
		dialog.setText(Messages.LibraryView_MetadataLibrary);
		dialog.setMessage(Messages.LibraryView_ChooseTheFolderThatContains);
		if (folder != null) {
			dialog.setFilterPath(folder.toString());
		}
		String selected = dialog.open();
		if (selected != null) {
			setFolder(Path.of(selected));
			IPreferenceStore store = preferences();
			store.setValue(FOLDER_PREFERENCE, selected);
			if (store instanceof IPersistentPreferenceStore persistent) {
				try {
					persistent.save();
				} catch (java.io.IOException e) {
					HlomEditorPlugin.INSTANCE.log(e);
				}
			}
		}
	}

	/** Shows the records below a folder. */
	public void setFolder(Path newFolder) {
		folder = newFolder.toAbsolutePath().normalize();
		rescan();
	}

	/** The library folder, or {@code null}. */
	public Path getFolder() {
		return folder;
	}

	/** Scans the library folder again. */
	public void rescan() {
		if (folder == null) {
			chooseFolder();
			return;
		}
		Job.getJobManager().cancel(jobFamily);
		entries.clear();
		refreshTable();
		schedule(new LibraryScanJob(jobFamily, folder, null, getSite().getShell().getDisplay(), scanListener()));
	}

	/**
	 * Reloads and revalidates a single file, e.g. after it was saved. Files outside the library folder are
	 * ignored; files that are no longer HLOM records are removed.
	 */
	public void refreshFile(Path file) {
		Path normalized = file.toAbsolutePath().normalize();
		if (folder == null || !normalized.startsWith(folder) || !HlomLibrary.hasHlomExtension(normalized)) {
			return;
		}
		schedule(new LibraryScanJob(jobFamily, folder, List.of(normalized), getSite().getShell().getDisplay(),
				scanListener()));
	}

	/** Tells every open library view that a file was saved. */
	public static void fileSaved(Path file) {
		for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			for (IWorkbenchPage page : window.getPages()) {
				IViewPart view = page.findView(ID);
				if (view instanceof LibraryView library) {
					library.refreshFile(file);
				}
			}
		}
	}

	private void revalidate(List<LibraryEntry> selected) {
		if (!selected.isEmpty()) {
			schedule(new LibraryScanJob(jobFamily, folder,
					selected.stream().map(LibraryEntry::path).toList(),
					getSite().getShell().getDisplay(), scanListener()));
		}
	}

	private void schedule(LibraryScanJob job) {
		scanning = true;
		updateState();
		IWorkbenchSiteProgressService progress = getSite().getService(IWorkbenchSiteProgressService.class);
		if (progress != null) {
			progress.schedule(job, 0, true);
		} else {
			job.schedule();
		}
	}

	private LibraryScanJob.Listener scanListener() {
		return new LibraryScanJob.Listener() {
			@Override
			public void loaded(List<LibraryEntry> loaded) {
				if (isDisposed()) {
					return;
				}
				for (LibraryEntry entry : loaded) {
					entries.put(entry.path().toAbsolutePath().normalize(), entry);
				}
				refreshTable();
			}

			@Override
			public void removed(List<Path> files) {
				if (isDisposed()) {
					return;
				}
				files.forEach(file -> entries.remove(file.toAbsolutePath().normalize()));
				refreshTable();
			}

			@Override
			public void progress(String text) {
				if (!isDisposed()) {
					header.setActivity(text);
				}
			}

			@Override
			public void done(boolean canceled) {
				if (isDisposed()) {
					return;
				}
				scanning = Job.getJobManager().find(jobFamily).length > 0;
				if (!scanning) {
					header.setActivity(null);
				}
				updateState();
			}
		};
	}

	private boolean isDisposed() {
		return viewer == null || viewer.getControl().isDisposed();
	}

	// Filtering and presentation

	private boolean matches(LibraryEntry entry) {
		Object kind = chips.selection(KIND_GROUP);
		if (kind != null && entry.summary().kind() != kind) {
			return false;
		}
		Object status = chips.selection(STATUS_GROUP);
		if (status != null && entry.status() != status) {
			return false;
		}
		if (searchTokens.length == 0 || searchTokens.length == 1 && searchTokens[0].isEmpty()) {
			return true;
		}
		String text = entry.summary().searchText() + " " + entry.fileName().toLowerCase(Locale.ROOT);
		return Arrays.stream(searchTokens).allMatch(text::contains);
	}

	private void refreshTable() {
		if (isDisposed()) {
			return;
		}
		viewer.refresh();
		List<LibraryEntry> all = new ArrayList<>(entries.values());
		chips.setCount(null, all.size());
		for (Kind kind : Kind.values()) {
			chips.setCount(kind, (int) all.stream().filter(e -> e.summary().kind() == kind).count());
		}
		for (LibraryEntry.Status status : LibraryEntry.Status.values()) {
			chips.setCount(status, (int) all.stream().filter(e -> e.status() == status).count());
		}
		chips.getParent().layout(true);
		updateState();
	}

	private void updateState() {
		if (isDisposed()) {
			return;
		}
		header.update(folder == null ? null : folder.toString(), new ArrayList<>(entries.values()),
				viewer.getTable().getItemCount());
		Control top;
		if (folder == null) {
			emptyHeading.setText(Messages.LibraryView_BuildYourMetadataLibrary);
			emptyText.setText(Messages.LibraryView_ChooseAFolderToSee);
			top = emptyPage;
		} else if (entries.isEmpty() && !scanning) {
			emptyHeading.setText(Messages.LibraryView_NoMedBiquitousDocumentsFound);
			emptyText.setText(NLS.bind(Messages.LibraryView_NoXmlJsonOrLom, folder));
			top = emptyPage;
		} else {
			top = tablePage;
		}
		StackLayout layout = (StackLayout) stack.getLayout();
		if (layout.topControl != top) {
			layout.topControl = top;
			stack.layout(true, true);
		} else if (top == emptyPage) {
			emptyPage.layout(true, true);
		}
		setTitleToolTip(folder == null ? "Metadata Library" : "Metadata Library: " + folder);
	}

	private String tooltip(LibraryEntry entry) {
		StringBuilder text = new StringBuilder(entry.path().toString());
		List<HlomProblem> problems = entry.problems().stream()
				.filter(p -> p.severity() != HlomProblem.Severity.INFO).toList();
		if (problems.isEmpty()) {
			text.append(Messages.LibraryView_ValidDocument);
		}
		problems.stream().limit(5).forEach(p -> text.append("\n• ").append(p.message()));
		if (problems.size() > 5) {
			text.append(Messages.LibraryView_And).append(problems.size() - 5).append(" more");
		}
		return text.toString();
	}

	// Actions

	private List<LibraryEntry> selectedEntries() {
		return ((IStructuredSelection) viewer.getSelection()).toList().stream()
				.filter(LibraryEntry.class::isInstance).map(LibraryEntry.class::cast).toList();
	}

	private void selectionChanged() {
		List<LibraryEntry> selected = viewer == null ? List.of() : selectedEntries();
		boolean one = selected.size() == 1;
		openAction.setEnabled(!selected.isEmpty());
		validateAction.setEnabled(!selected.isEmpty());
		exportCardAction.setEnabled(one);
		revealAction.setEnabled(one);
		copyPathAction.setEnabled(!selected.isEmpty());
		getViewSite().getActionBars().getStatusLineManager().setMessage(one
				? LibraryCells.statusImage(selected.getFirst()) : null, one ? selected.getFirst().path().toString() : null);
	}

	private void openSelection() {
		for (LibraryEntry entry : selectedEntries()) {
			HlomEditorAdvisor.openEditor(getSite().getWorkbenchWindow().getWorkbench(),
					URI.createFileURI(entry.path().toAbsolutePath().toString()));
		}
	}

	private void exportCard() {
		List<LibraryEntry> selected = selectedEntries();
		if (selected.size() == 1) {
			LibraryEntry entry = selected.getFirst();
			HtmlExport.saveAndOpen(getSite().getShell(), Messages.LibraryView_ExportHTMLCard2, entry.title(), entry.path().getParent(),
					HtmlReport.document(entry));
		}
	}

	private void exportLibrary() {
		List<LibraryEntry> shown = Arrays.stream(viewer.getTable().getItems())
				.map(item -> (LibraryEntry) item.getData()).toList();
		if (shown.isEmpty()) {
			return;
		}
		String name = folder == null || folder.getFileName() == null ? "library"
				: folder.getFileName() + " library report";
		HtmlExport.saveAndOpen(getSite().getShell(), Messages.LibraryView_ExportLibraryReport2, name, folder,
				HtmlReport.library(shown, folder == null ? null : folder.toString()));
	}

	/** Shows the record in the file manager (selected, with the IDE's command), else opens its folder. */
	private void reveal() {
		List<LibraryEntry> selected = selectedEntries();
		if (selected.size() != 1 || selected.getFirst().path().getParent() == null) {
			return;
		}
		if (Adapters.adapt(selected.getFirst(), IFile.class) != null) {
			try {
				getSite().getService(org.eclipse.ui.handlers.IHandlerService.class)
						.executeCommand("org.eclipse.ui.ide.showInSystemExplorer", null);
				return;
			} catch (org.eclipse.core.commands.common.CommandException _) {
				// Not available: open the folder.
			}
		}
		Program.launch(selected.getFirst().path().getParent().toString());
	}

	private void copyPath() {
		String paths = selectedEntries().stream().map(e -> e.path().toString()).collect(Collectors.joining("\n"));
		Clipboard clipboard = new Clipboard(getSite().getShell().getDisplay());
		try {
			clipboard.setContents(new Object[] { paths }, new Transfer[] { TextTransfer.getInstance() });
		} finally {
			clipboard.dispose();
		}
	}

	// Editor saves

	private void observe(IEditorPart editor) {
		if (observedEditors.add(editor)) {
			editor.addPropertyListener(dirtyListener);
		}
	}

	private static Path pathOf(IEditorInput input) {
		return EditorInputs.pathOf(input);
	}

	@Override
	public void setFocus() {
		if (((StackLayout) stack.getLayout()).topControl == tablePage) {
			viewer.getControl().setFocus();
		} else {
			search.setFocus();
		}
	}

	@Override
	public void dispose() {
		Job.getJobManager().cancel(jobFamily);
		getSite().getPage().removePartListener(partListener);
		for (IEditorPart editor : observedEditors) {
			editor.removePropertyListener(dirtyListener);
		}
		observedEditors.clear();
		super.dispose();
	}
}
