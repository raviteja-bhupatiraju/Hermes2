/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import org.eclipse.osgi.util.NLS;

import java.util.List;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.compare.CompareTree.CategoryNode;
import edu.louisiana.caai.lom.editor.compare.CompareTree.Column;
import edu.louisiana.caai.lom.editor.compare.CompareTree.Row;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.pages.EditorServices;
import edu.louisiana.caai.lom.model.support.compare.DiffResult;
import edu.louisiana.caai.lom.model.support.compare.Difference;
import edu.louisiana.caai.lom.model.support.compare.DocumentDiff;
import edu.louisiana.caai.lom.model.support.compare.HlomDiff;

/**
 * Shows the semantic differences of two HLOM records ({@link HlomDiff}): a header with both records and the
 * change counts, and a tree grouped by LOM category with the columns Field, Left and Right. Editor sides are
 * live: the comparison follows edits and saves. Double-clicking a field reveals it in its open editor.
 */
public class CompareView extends ViewPart {

	public static final String ID = "edu.louisiana.caai.lom.editor.compare.CompareView";

	private static final int REFRESH_DELAY = 350;

	private Composite stack;
	private StackLayout stackLayout;
	private Composite empty;
	private Composite content;
	private CompareHeader header;
	private Label notice;
	private Text filter;
	private TreeViewer viewer;

	private CompareSide left;
	private CompareSide right;
	private DiffResult result;
	private boolean showUnchanged;
	private boolean refreshScheduled;

	private Action showUnchangedAction;
	private Action swapAction;
	private Action refreshAction;
	private Action copyAction;

	private final CommandStackListener commandStackListener = _ -> scheduleRefresh(false);
	private final IPropertyListener dirtyListener = (_, property) -> {
		if (property == IWorkbenchPartConstants.PROP_DIRTY) {
			scheduleRefresh(true); // saved: re-read the saved side
		}
	};

	/** Opens the view in the window and compares the two sides. */
	public static CompareView open(IWorkbenchWindow window, CompareSide left, CompareSide right) {
		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null) {
			return null;
		}
		try {
			CompareView view = (CompareView) page.showView(ID);
			view.setInput(left, right);
			return view;
		} catch (PartInitException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return null;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.COMPARE_VIEW);
		stack = new Composite(parent, SWT.NONE);
		stackLayout = new StackLayout();
		stack.setLayout(stackLayout);
		createEmpty(stack);
		createContent(stack);
		stackLayout.topControl = empty;
		createActions();
		updateActions();
	}

	private void createEmpty(Composite parent) {
		empty = new Composite(parent, SWT.NONE);
		empty.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.fillDefaults().margins(24, 24).spacing(0, 8).applyTo(empty);
		Composite center = new Composite(empty, SWT.NONE);
		center.setBackground(empty.getBackground());
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, true).applyTo(center);
		GridLayoutFactory.fillDefaults().spacing(0, 6).applyTo(center);

		Label image = new Label(center, SWT.NONE);
		image.setBackground(empty.getBackground());
		image.setImage(HlomTheme.image("icons/compare/compare@2x.png"));
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(image);
		Label title = new Label(center, SWT.NONE);
		title.setBackground(empty.getBackground());
		title.setFont(HlomTheme.sectionFont());
		title.setForeground(HlomTheme.color(HlomTheme.INK));
		title.setText(Messages.CompareView_CompareMedBiquitousDocuments);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(title);
		Label text = new Label(center, SWT.CENTER | SWT.WRAP);
		text.setBackground(empty.getBackground());
		text.setForeground(HlomTheme.color(HlomTheme.SLATE));
		text.setText("""
				See field by field what differs between two records, or review the unsaved changes of the open document.
				Repeated elements are matched by meaning, so reordering is not a change.""");
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).hint(520, SWT.DEFAULT).applyTo(text);

		Composite buttons = new Composite(center, SWT.NONE);
		buttons.setBackground(empty.getBackground());
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).indent(0, 8).applyTo(buttons);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).spacing(8, 0).applyTo(buttons);
		button(buttons, Messages.CompareView_CompareWithSavedVersion, "icons/compare/compare-saved.png",
				() -> CompareActions.compareWithSaved(getSite().getWorkbenchWindow()));
		button(buttons, Messages.CompareView_CompareWithFile, "icons/compare/compare.png",
				() -> CompareActions.compareWithFile(getSite().getWorkbenchWindow()));
		button(buttons, Messages.CompareView_CompareTwoFiles, "icons/compare/compare-files.png",
				() -> CompareActions.compareFiles(getSite().getWorkbenchWindow()));
	}

	private static void button(Composite parent, String text, String image, Runnable runnable) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		button.setImage(HlomTheme.image(image));
		button.addListener(SWT.Selection, _ -> runnable.run());
	}

	private void createContent(Composite parent) {
		content = new Composite(parent, SWT.NONE);
		content.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(content);

		header = new CompareHeader(content);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(header);

		Composite bar = new Composite(content, SWT.NONE);
		bar.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(bar);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(8, 6).spacing(12, 0).applyTo(bar);
		filter = new Text(bar, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		filter.setMessage(Messages.CompareView_FilterFieldsAndValues);
		GridDataFactory.fillDefaults().hint(260, SWT.DEFAULT).applyTo(filter);
		filter.addModifyListener(_ -> refreshTree());
		notice = new Label(bar, SWT.NONE);
		notice.setBackground(bar.getBackground());
		notice.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(notice);

		Label line = new Label(content, SWT.NONE);
		line.setBackground(HlomTheme.color(HlomTheme.BORDER));
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 1).applyTo(line);

		Composite treeComposite = new Composite(content, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(treeComposite);
		TreeColumnLayout layout = new TreeColumnLayout();
		treeComposite.setLayout(layout);
		viewer = new TreeViewer(treeComposite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(false);
		viewer.setContentProvider(new CompareTree.ContentProvider());
		viewer.setAutoExpandLevel(2);
		column(layout, Messages.CompareView_Field, 34, Column.FIELD);
		column(layout, Messages.CompareView_Left, 33, Column.LEFT);
		column(layout, Messages.CompareView_Right, 33, Column.RIGHT);
		ColumnViewerToolTipSupport.enableFor(viewer);
		viewer.addDoubleClickListener(event -> {
			Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
			switch (element) {
				case CategoryNode _ -> viewer.setExpandedState(element, !viewer.getExpandedState(element));
				case Row row -> reveal(row.difference());
				case null, default -> {
				}
			}
		});
	}

	private void column(TreeColumnLayout layout, String title, int weight, Column column) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		viewerColumn.getColumn().setText(title);
		viewerColumn.setLabelProvider(new CompareTree.LabelProvider(column));
		layout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(weight, 120, true));
	}

	private void createActions() {
		showUnchangedAction = new Action(Messages.CompareView_ShowUnchangedFields, IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				showUnchanged = isChecked();
				refreshTree();
			}
		};
		showUnchangedAction.setImageDescriptor(descriptor("icons/compare/unchanged.png"));
		Action expand = new Action(Messages.CompareView_ExpandAll, descriptor("icons/compare/expand-all.png")) {
			@Override
			public void run() {
				viewer.expandAll();
			}
		};
		Action collapse = new Action(Messages.CompareView_CollapseAll, descriptor("icons/compare/collapse-all.png")) {
			@Override
			public void run() {
				viewer.collapseAll();
			}
		};
		swapAction = new Action(Messages.CompareView_SwapLeftAndRight, descriptor("icons/compare/swap.png")) {
			@Override
			public void run() {
				setInput(right, left);
			}
		};
		refreshAction = new Action(Messages.CompareView_RefreshReReadFiles, descriptor("icons/library/refresh.png")) {
			@Override
			public void run() {
				refresh(true);
			}
		};
		copyAction = new Action(Messages.CompareView_CopyDifferencesAsText, descriptor("icons/compare/copy.png")) {
			@Override
			public void run() {
				copy();
			}
		};
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(showUnchangedAction);
		toolBar.add(new Separator());
		toolBar.add(expand);
		toolBar.add(collapse);
		toolBar.add(new Separator());
		toolBar.add(swapAction);
		toolBar.add(refreshAction);
		toolBar.add(copyAction);

		IMenuManager menu = getViewSite().getActionBars().getMenuManager();
		IWorkbenchWindow window = getSite().getWorkbenchWindow();
		menu.add(action(Messages.CompareView_CompareWithSavedVersion, "icons/compare/compare-saved.png",
				() -> CompareActions.compareWithSaved(window)));
		menu.add(action(Messages.CompareView_CompareWithFile, "icons/compare/compare.png", () -> CompareActions.compareWithFile(window)));
		menu.add(action(Messages.CompareView_CompareTwoFiles, "icons/compare/compare-files.png",
				() -> CompareActions.compareFiles(window)));
		menu.add(new Separator());
		menu.add(showUnchangedAction);
		menu.add(copyAction);
	}

	private static Action action(String text, String image, Runnable runnable) {
		Action action = new Action(text, descriptor(image)) {
			@Override
			public void run() {
				runnable.run();
			}
		};
		return action;
	}

	private static ImageDescriptor descriptor(String path) {
		return ImageDescriptor.createFromURL(CompareView.class.getResource("/" + path));
	}

	private void updateActions() {
		boolean hasInput = left != null && right != null;
		swapAction.setEnabled(hasInput);
		refreshAction.setEnabled(hasInput);
		copyAction.setEnabled(hasInput);
	}

	/** Compares two sides and shows the result. */
	public void setInput(CompareSide leftSide, CompareSide rightSide) {
		unhook(left);
		unhook(right);
		this.left = leftSide;
		this.right = rightSide;
		hook(left);
		hook(right);
		refresh(false);
		updateActions();
	}

	/** The current comparison result, {@code null} without input. */
	public DiffResult getResult() {
		return result;
	}

	private void hook(CompareSide side) {
		if (side != null && side.editor() != null) {
			CommandStack stack = commandStack(side.editor());
			if (stack != null) {
				stack.addCommandStackListener(commandStackListener);
			}
			side.editor().addPropertyListener(dirtyListener);
		}
	}

	private void unhook(CompareSide side) {
		if (side != null && side.editor() != null) {
			CommandStack stack = commandStack(side.editor());
			if (stack != null) {
				stack.removeCommandStackListener(commandStackListener);
			}
			side.editor().removePropertyListener(dirtyListener);
		}
	}

	private static CommandStack commandStack(IEditorPart editor) {
		return editor instanceof IEditingDomainProvider provider && provider.getEditingDomain() != null
				? provider.getEditingDomain().getCommandStack()
				: null;
	}

	private void scheduleRefresh(boolean reload) {
		if (refreshScheduled || viewer == null || viewer.getControl().isDisposed()) {
			return;
		}
		refreshScheduled = true;
		Display.getCurrent().timerExec(REFRESH_DELAY, () -> {
			refreshScheduled = false;
			if (!viewer.getControl().isDisposed()) {
				refresh(reload);
			}
		});
	}

	private void refresh(boolean reload) {
		if (left == null || right == null) {
			result = null;
			stackLayout.topControl = empty;
			stack.layout();
			return;
		}
		if (reload) {
			left.reload();
			right.reload();
		}
		result = left.root() != null || right.root() != null || left.type() == null && right.type() == null
				? HlomDiff.compare(left.root(), right.root())
				: DocumentDiff.compare(left.resource(), right.resource());
		header.setInput(left, right, result);
		setContentDescription("");
		setTitleToolTip(left.fileName() + " ↔ " + right.fileName());
		stackLayout.topControl = content;
		content.layout(true, true);
		stack.layout();
		refreshTree();
	}

	private void refreshTree() {
		if (viewer == null || viewer.getControl().isDisposed()) {
			return;
		}
		List<CategoryNode> nodes = CompareTree.nodes(result, showUnchanged, filter.getText());
		viewer.getControl().setRedraw(false);
		try {
			viewer.setInput(nodes);
		} finally {
			viewer.getControl().setRedraw(true);
		}
		String message;
		if (result == null) {
			message = "";
		} else if (left.type() == null || right.type() == null) {
			CompareSide broken = left.type() == null ? left : right;
			message = NLS.bind(Messages.CompareView_CouldNotBeReadAs, broken.fileName());
			notice.setForeground(HlomTheme.color(HlomTheme.ACCENT));
		} else if (left.type() != right.type()) {
			message = NLS.bind(Messages.CompareView_TheDocumentsAreOfDifferent, left.type().label(), right.type().label());
			notice.setForeground(HlomTheme.color(HlomTheme.ACCENT));
		} else if (result.isIdentical()) {
			message = "The records are identical. " + (showUnchanged ? "" : "Turn on “Show Unchanged Fields” to review all fields.");
			notice.setForeground(HlomTheme.color(HlomTheme.TEAL));
		} else if (nodes.isEmpty()) {
			message = Messages.CompareView_NoFieldsMatchTheFilter;
			notice.setForeground(HlomTheme.color(HlomTheme.SLATE));
		} else {
			message = Messages.CompareView_DoubleClickAFieldTo;
			notice.setForeground(HlomTheme.color(HlomTheme.MUTED));
		}
		notice.setText(message);
		notice.getParent().layout();
	}

	/** Shows the element of a field in the open editor of its document. */
	private void reveal(Difference difference) {
		for (EObject element : new EObject[] { difference.left(), difference.right() }) {
			if (element == null || element.eResource() == null) {
				continue;
			}
			IEditorPart editor = editorOf(element.eResource());
			if (editor != null) {
				getSite().getPage().activate(editor);
				if (editor instanceof EditorServices services) {
					services.reveal(element);
				}
				return;
			}
		}
		getViewSite().getActionBars().getStatusLineManager()
				.setMessage(NLS.bind(Messages.CompareView_OpenTheDocumentInThe, difference.field()));
	}

	private IEditorPart editorOf(Resource resource) {
		for (IEditorReference reference : getSite().getPage().getEditorReferences()) {
			IEditorPart editor = reference.getEditor(false);
			if (editor instanceof IEditingDomainProvider provider && provider.getEditingDomain() != null
					&& provider.getEditingDomain().getResourceSet().getResources().contains(resource)) {
				return editor;
			}
		}
		return null;
	}

	private void copy() {
		if (result == null) {
			return;
		}
		Clipboard clipboard = new Clipboard(viewer.getControl().getDisplay());
		try {
			clipboard.setContents(new Object[] { result.toText(left.title() + " [" + left.fileName() + "]",
					right.title() + " [" + right.fileName() + "]") }, new Transfer[] { TextTransfer.getInstance() });
		} finally {
			clipboard.dispose();
		}
		getViewSite().getActionBars().getStatusLineManager().setMessage(Messages.CompareView_DifferencesCopiedToTheClipboard);
	}

	@Override
	public void setFocus() {
		if (stackLayout.topControl == content) {
			viewer.getControl().setFocus();
		} else {
			empty.setFocus();
		}
	}

	@Override
	public void dispose() {
		unhook(left);
		unhook(right);
		super.dispose();
	}
}
