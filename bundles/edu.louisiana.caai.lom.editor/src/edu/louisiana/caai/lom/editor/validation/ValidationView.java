/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.validation;

import java.util.List;
import java.util.function.Function;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.quickfix.ProblemImages;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixImages;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixPopup;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixes;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;

/**
 * Lists the validation problems of the active HLOM editor: XML Schema errors, EMF model constraint violations
 * and HLOM best practice warnings. Double-clicking a problem reveals the element (or XML line) it refers to;
 * problems marked with a light bulb have quick fixes (context menu or Ctrl+1), and "Fix All" repairs every
 * automatically fixable problem in one undoable step.
 */
public class ValidationView extends ViewPart {

	public static final String ID = "edu.louisiana.caai.lom.editor.validation.ValidationView";

	private TableViewer viewer;
	private ValidationSource source;
	private final Runnable problemsListener = this::refresh;

	private Action fixAllAction;
	private Action quickFixAction;
	private Action goToAction;
	private Action copyAction;
	private Action validateAction;
	private QuickFixPopup popup;

	private final IPartListener2 partListener = new IPartListener2() {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part instanceof ValidationSource validationSource) {
				setSource(validationSource);
			}
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			if (partRef.getPart(false) == source) {
				setSource(null);
			}
		}
	};

	@Override
	public void createPartControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.VALIDATION_VIEW);
		Composite tableComposite = new Composite(parent, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		tableComposite.setLayout(layout);
		viewer = new TableViewer(tableComposite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		ColumnViewerToolTipSupport.enableFor(viewer);

		createFixColumn(layout);
		column(layout, Messages.ValidationView_Description, 70, HlomProblem::message, true);
		column(layout, Messages.ValidationView_Element, 18, problem -> problem.path() != null ? problem.path() : elementName(problem), false);
		column(layout, Messages.ValidationView_Line, 5, problem -> problem.line() > 0 ? Integer.toString(problem.line()) : "", false);
		column(layout, Messages.ValidationView_Check, 9, problem -> problem.origin().label(), false);

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer v, Object e1, Object e2) {
				HlomProblem p1 = (HlomProblem) e1;
				HlomProblem p2 = (HlomProblem) e2;
				int result = p1.severity().compareTo(p2.severity());
				return result != 0 ? result : Integer.compare(p1.line(), p2.line());
			}
		});
		viewer.addDoubleClickListener(_ -> {
			HlomProblem problem = selectedProblem();
			if (problem != null && source != null) {
				source.reveal(problem);
			}
		});
		viewer.addSelectionChangedListener(_ -> updateActions());

		createActions();
		createContextMenu();
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(fixAllAction);
		toolBar.add(validateAction);
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);

		// Ctrl+1: the text editors' Quick Fix command.
		quickFixAction.setActionDefinitionId(ITextEditorActionDefinitionIds.QUICK_ASSIST);
		IHandlerService handlers = getSite().getService(IHandlerService.class);
		if (handlers != null) {
			handlers.activateHandler(ITextEditorActionDefinitionIds.QUICK_ASSIST, new ActionHandler(quickFixAction));
		}

		getSite().getPage().addPartListener(partListener);
		if (getSite().getPage().getActiveEditor() instanceof ValidationSource validationSource) {
			setSource(validationSource);
		}
		refresh();
	}

	private void createFixColumn(TableColumnLayout layout) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.CENTER);
		column.getColumn().setResizable(false);
		column.getColumn().setImage(QuickFixImages.bulb());
		column.getColumn().setToolTipText(Messages.ValidationView_QuickFixAvailableCtrl1);
		layout.setColumnData(column.getColumn(), new ColumnPixelData(30, false));
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				List<HlomFix> fixes = HlomFixes.forProblem((HlomProblem) element);
				if (fixes.isEmpty()) {
					return null;
				}
				return fixes.getFirst().kind() == HlomFix.Kind.CHANGE ? QuickFixImages.bulb() : QuickFixImages.navigate();
			}

			@Override
			public String getToolTipText(Object element) {
				List<HlomFix> fixes = HlomFixes.forProblem((HlomProblem) element);
				if (fixes.isEmpty()) {
					return null;
				}
				StringBuilder text = new StringBuilder(Messages.ValidationView_QuickFixesCtrl1);
				for (HlomFix fix : fixes) {
					text.append("\n• ").append(fix.label());
				}
				return text.toString();
			}
		});
	}

	private static String elementName(HlomProblem problem) {
		return problem.object() == null ? "" : problem.object().eClass().getName();
	}

	private void column(TableColumnLayout layout, String title, int weight, Function<HlomProblem, String> text,
			boolean withIcon) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		layout.setColumnData(column.getColumn(), new ColumnWeightData(weight, 40));
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return text.apply((HlomProblem) element);
			}

			@Override
			public String getToolTipText(Object element) {
				return withIcon ? ((HlomProblem) element).message() : null;
			}

			@Override
			public Image getImage(Object element) {
				return withIcon ? ProblemImages.severity(((HlomProblem) element).severity()) : null;
			}
		});
	}

	// ----------------------------------------------------------------------------------------------------
	// Actions
	// ----------------------------------------------------------------------------------------------------

	private void createActions() {
		fixAllAction = new Action(Messages.ValidationView_FixAll) {
			@Override
			public void run() {
				QuickFixes.fixAll(getSite().getShell(), source);
			}
		};
		fixAllAction.setImageDescriptor(QuickFixImages.descriptor(QuickFixImages.fixAll()));

		quickFixAction = new Action(Messages.ValidationView_QuickFixCtrl1) {
			@Override
			public void run() {
				openQuickFixPopup();
			}
		};
		quickFixAction.setActionDefinitionId(ITextEditorActionDefinitionIds.QUICK_ASSIST);
		quickFixAction.setImageDescriptor(QuickFixImages.descriptor(QuickFixImages.bulb()));

		goToAction = new Action(Messages.ValidationView_GoToElement) {
			@Override
			public void run() {
				HlomProblem problem = selectedProblem();
				if (problem != null && source != null) {
					QuickFixes.reveal(source, problem.object(), problem);
				}
			}
		};
		goToAction.setImageDescriptor(QuickFixImages.descriptor(QuickFixImages.navigate()));

		copyAction = new Action(Messages.ValidationView_CopyMessage) {
			@Override
			public void run() {
				HlomProblem problem = selectedProblem();
				if (problem != null) {
					copy(problem.severity() + ": " + problem.message()
							+ (problem.path() != null ? " (" + problem.path() + ")" : "")
							+ (problem.line() > 0 ? " [line " + problem.line() + "]" : ""));
				}
			}
		};
		copyAction.setImageDescriptor(
				getSite().getWorkbenchWindow().getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		validateAction = new Action(Messages.ValidationView_ValidateNow) {
			@Override
			public void run() {
				if (source != null) {
					source.validateNow();
				}
			}
		};
		validateAction.setToolTipText(Messages.ValidationView_ValidateTheDocumentNow);
		validateAction.setImageDescriptor(QuickFixImages.descriptor(validateImage()));
	}

	private static Image validateImage() {
		Image image = HlomTheme.image("icons/library/validate.png");
		return image != null ? image : ProblemImages.severity(Severity.INFO);
	}

	private void createContextMenu() {
		MenuManager menu = new MenuManager("#PopupMenu");
		menu.setRemoveAllWhenShown(true);
		menu.addMenuListener(this::fillContextMenu);
		viewer.getControl().setMenu(menu.createContextMenu(viewer.getControl()));
	}

	private void fillContextMenu(IMenuManager menu) {
		HlomProblem problem = selectedProblem();
		if (problem != null) {
			MenuManager fixes = new MenuManager(Messages.ValidationView_QuickFix, QuickFixImages.descriptor(QuickFixImages.bulb()),
					"quickFix");
			List<HlomFix> offered = QuickFixes.offered(problem);
			for (HlomFix fix : offered) {
				fixes.add(fixAction(problem, fix));
			}
			if (offered.isEmpty()) {
				Action none = new Action(Messages.ValidationView_NoQuickFixes) {
				};
				none.setEnabled(false);
				fixes.add(none);
			}
			menu.add(fixes);
			menu.add(quickFixAction);
			menu.add(new Separator());
		}
		menu.add(fixAllAction);
		menu.add(new Separator());
		if (problem != null) {
			menu.add(goToAction);
			menu.add(copyAction);
			menu.add(new Separator());
		}
		menu.add(validateAction);
	}

	private IAction fixAction(HlomProblem problem, HlomFix fix) {
		Action action = new Action(fix.label().replace("&", "&&")) {
			@Override
			public void run() {
				QuickFixes.apply(source, fix, problem);
			}
		};
		action.setToolTipText(fix.description());
		action.setImageDescriptor(QuickFixImages.descriptor(
				fix.kind() == HlomFix.Kind.NAVIGATE ? QuickFixImages.navigate() : QuickFixImages.bulb()));
		action.setEnabled(fix.kind() == HlomFix.Kind.NAVIGATE || QuickFixes.canModify(source));
		return action;
	}

	/** Opens the quick fix popup below the selected problem. */
	private void openQuickFixPopup() {
		HlomProblem problem = selectedProblem();
		if (problem == null || source == null
				|| (popup != null && popup.getShell() != null && !popup.getShell().isDisposed())) {
			return;
		}
		Table table = viewer.getTable();
		Point anchor = null;
		TableItem[] selection = table.getSelection();
		if (selection.length == 1) {
			Rectangle bounds = selection[0].getBounds(1);
			anchor = table.toDisplay(bounds.x, bounds.y + bounds.height + 2);
		}
		popup = new QuickFixPopup(getSite().getShell(), source, problem, anchor);
		popup.open();
	}

	private void copy(String text) {
		Clipboard clipboard = new Clipboard(viewer.getControl().getDisplay());
		try {
			clipboard.setContents(new Object[] { text }, new Transfer[] { TextTransfer.getInstance() });
		} finally {
			clipboard.dispose();
		}
	}

	private HlomProblem selectedProblem() {
		if (viewer == null) {
			return null;
		}
		Object selected = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		return selected instanceof HlomProblem problem ? problem : null;
	}

	private void updateActions() {
		if (fixAllAction == null) {
			return;
		}
		HlomProblem problem = selectedProblem();
		List<HlomProblem> problems = source == null ? List.of() : source.getProblems();
		int fixable = HlomFixes.automaticFixes(problems).size();
		fixAllAction.setEnabled(fixable > 0 && QuickFixes.canModify(source));
		fixAllAction.setToolTipText(fixable == 0 ? "Fix All: no problem can be fixed automatically"
				: "Fix All: repair " + fixable + (fixable == 1 ? " problem" : " problems")
						+ " automatically (one undoable change)");
		quickFixAction.setEnabled(problem != null);
		goToAction.setEnabled(QuickFixes.canReveal(problem));
		copyAction.setEnabled(problem != null);
		validateAction.setEnabled(source != null);
	}

	// ----------------------------------------------------------------------------------------------------

	private void setSource(ValidationSource newSource) {
		if (newSource == source) {
			return;
		}
		if (source != null) {
			source.removeProblemsListener(problemsListener);
		}
		source = newSource;
		if (source != null) {
			source.addProblemsListener(problemsListener);
		}
		refresh();
	}

	private void refresh() {
		if (viewer == null || viewer.getControl().isDisposed()) {
			return;
		}
		List<HlomProblem> problems = source == null ? List.of() : source.getProblems();
		viewer.setInput(problems);
		long errors = problems.stream().filter(p -> p.severity() == Severity.ERROR).count();
		long warnings = problems.stream().filter(p -> p.severity() == Severity.WARNING).count();
		long infos = problems.size() - errors - warnings;
		long fixable = problems.stream().filter(HlomFixes::hasChangeFix).count();
		String summary = errors + plural(errors, " error", " errors") + ", " + warnings
				+ plural(warnings, " warning", " warnings") + ", " + infos + plural(infos, " info", " infos");
		if (fixable > 0) {
			summary += " · " + fixable + plural(fixable, Messages.ValidationView_HasAQuickFix, Messages.ValidationView_HaveQuickFixes) + " (Ctrl+1)";
		}
		setContentDescription(source == null ? "No HLOM document is active."
				: problems.isEmpty() ? "The document is valid." : summary);
		updateActions();
	}

	private static String plural(long count, String singular, String plural) {
		return count == 1 ? singular : plural;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(partListener);
		setSource(null);
		super.dispose();
	}
}
