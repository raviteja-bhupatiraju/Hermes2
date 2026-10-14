/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.terminology;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Looks up concepts in the offline terminologies (MeSH, ICD-10-CM, ACGME, Bloom's taxonomy, AAMC Curriculum
 * Inventory). With an empty search the hierarchy can be browsed; typing searches codes, names and synonyms. The
 * details pane shows the code, the path in the hierarchy, the definition, synonyms and related concepts.
 * <p>
 * In multi-selection mode concepts are checked (also across searches); otherwise one concept is selected.
 */
public class TerminologyLookupDialog extends TitleAreaDialog {

	private static final int RESULT_LIMIT = 200;
	private static final int SEARCH_DELAY = 150;

	private final List<Terminology> terminologies;
	private final boolean multi;
	private final String title;
	private final String okLabel;
	private Terminology terminology;
	private final Map<String, Concept> checked = new LinkedHashMap<>();

	private Combo terminologyCombo;
	private Text search;
	private TreeViewer viewer;
	private StyledText details;
	private Label status;
	private boolean searchScheduled;
	private int generation;

	private List<Concept> result = List.of();
	private Terminology resultTerminology;

	/**
	 * @param terminologies the terminologies to choose from (the first is preselected unless {@code initial} is set)
	 * @param initial       the terminology to show first, or {@code null}
	 * @param multi         whether several concepts can be chosen
	 */
	public TerminologyLookupDialog(Shell parent, String title, String okLabel, List<Terminology> terminologies,
			Terminology initial, boolean multi) {
		super(parent);
		this.title = title;
		this.okLabel = okLabel;
		this.terminologies = List.copyOf(terminologies);
		this.terminology = initial != null && terminologies.contains(initial) ? initial : terminologies.getFirst();
		this.multi = multi;
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
	}

	/** The chosen concepts (empty if canceled). */
	public List<Concept> getResult() {
		return result;
	}

	/** The terminology of the chosen concepts. */
	public Terminology getResultTerminology() {
		return resultTerminology;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	@Override
	protected org.eclipse.swt.graphics.Point getInitialSize() {
		return new org.eclipse.swt.graphics.Point(980, 680);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		HelpContexts.set(parent, HelpContexts.TERMINOLOGY_LOOKUP);
		Composite area = (Composite) super.createDialogArea(parent);
		setTitle(title);
		setMessage(terminologies.stream().anyMatch(Terminology::isOnline)
				? "Search by name, synonym or code, or browse the hierarchy. SNOMED CT and LOINC are searched online with "
						+ "your UMLS license; the others work offline."
				: "Search by name, synonym or code, or browse the hierarchy. All lookups work offline.");
		setTitleImage(HlomTheme.image("icons/app/hlom64.png"));

		Composite body = new Composite(area, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).margins(10, 10).spacing(8, 8).applyTo(body);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);

		if (terminologies.size() > 1) {
			new Label(body, SWT.NONE).setText(Messages.TerminologyLookupDialog_Terminology);
			terminologyCombo = new Combo(body, SWT.READ_ONLY);
			for (Terminology each : terminologies) {
				terminologyCombo.add(each.getTitle() + " (" + each.getId() + ")");
			}
			terminologyCombo.select(terminologies.indexOf(terminology));
			terminologyCombo.addListener(SWT.Selection, _ -> {
				terminology = terminologies.get(terminologyCombo.getSelectionIndex());
				checked.clear();
				load();
			});
		}

		new Label(body, SWT.NONE).setText(Messages.TerminologyLookupDialog_Search);
		search = new Text(body, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		search.setMessage(Messages.TerminologyLookupDialog_EGMyocardialInfarctionSepsis);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(search);
		search.addModifyListener(_ -> scheduleSearch());
		search.addListener(SWT.KeyDown, event -> {
			if (event.keyCode == SWT.ARROW_DOWN && viewer.getTree().getItemCount() > 0) {
				viewer.getTree().setFocus();
				viewer.getTree().setSelection(viewer.getTree().getItem(0));
				showDetails(viewer.getTree().getItem(0).getData());
				event.doit = false;
			}
		});

		SashForm sash = new SashForm(body, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(sash);
		createTree(sash);
		details = new StyledText(sash, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		details.setMargins(10, 8, 10, 8);
		details.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		sash.setWeights(58, 42);

		status = new Label(body, SWT.WRAP);
		status.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(400, SWT.DEFAULT).applyTo(status);

		load();
		search.setFocus();
		return area;
	}

	private void createTree(Composite parent) {
		int style = SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | (multi ? SWT.MULTI : SWT.SINGLE);
		viewer = multi ? new CheckboxTreeViewer(parent, style) : new TreeViewer(parent, style);
		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		viewer.setUseHashlookup(false);
		viewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public Object[] getElements(Object input) {
				return ((List<?>) input).toArray();
			}

			@Override
			public Object[] getChildren(Object element) {
				return element instanceof Concept concept ? terminology.children(concept.code()).toArray() : new Object[0];
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				return element instanceof Concept concept && !terminology.children(concept.code()).isEmpty();
			}
		});
		TreeViewerColumn name = new TreeViewerColumn(viewer, SWT.NONE);
		name.getColumn().setText(Messages.TerminologyLookupDialog_Name);
		name.getColumn().setWidth(400);
		name.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element instanceof Concept concept ? concept.label() : String.valueOf(element);
			}

			@Override
			public Color getForeground(Object element) {
				return element instanceof Concept ? null : HlomTheme.color(HlomTheme.MUTED);
			}
		});
		TreeViewerColumn code = new TreeViewerColumn(viewer, SWT.NONE);
		code.getColumn().setText(Messages.TerminologyLookupDialog_Code);
		code.getColumn().setWidth(110);
		code.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element instanceof Concept concept ? concept.code() : "";
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(HlomTheme.SLATE);
			}
		});
		viewer.addSelectionChangedListener(_ -> showDetails(viewer.getStructuredSelection().getFirstElement()));
		viewer.addDoubleClickListener(_ -> {
			Object element = viewer.getStructuredSelection().getFirstElement();
			if (!(element instanceof Concept concept)) {
				return;
			}
			if (multi) {
				boolean state = !checked.containsKey(concept.code());
				((CheckboxTreeViewer) viewer).setChecked(concept, state);
				toggle(concept, state);
			} else {
				okPressed();
			}
		});
		if (viewer instanceof CheckboxTreeViewer checkboxes) {
			checkboxes.addCheckStateListener(event -> {
				if (event.getElement() instanceof Concept concept) {
					toggle(concept, event.getChecked());
				} else {
					checkboxes.setChecked(event.getElement(), false);
				}
			});
			checkboxes.setCheckStateProvider(new org.eclipse.jface.viewers.ICheckStateProvider() {
				@Override
				public boolean isChecked(Object element) {
					return element instanceof Concept concept && checked.containsKey(concept.code());
				}

				@Override
				public boolean isGrayed(Object element) {
					return false;
				}
			});
		}
	}

	private void toggle(Concept concept, boolean state) {
		if (state) {
			checked.put(concept.code(), concept);
		} else {
			checked.remove(concept.code());
		}
		updateButtons();
	}

	/** Loads the terminology in the background, then shows its top level (or the search results). */
	private void load() {
		int current = ++generation;
		Terminology loading = terminology;
		viewer.setInput(List.of(NLS.bind(Messages.TerminologyLookupDialog_Loading, loading.getTitle())));
		search.setEnabled(false);
		status.setText(NLS.bind(Messages.TerminologyLookupDialog_Loading, loading.getTitle()));
		org.eclipse.swt.widgets.Display display = getShell().getDisplay();
		org.eclipse.core.runtime.jobs.Job job = org.eclipse.core.runtime.jobs.Job.create(NLS.bind(Messages.TerminologyLookupDialog_Loading2, loading.getTitle()),
				_ -> {
			RuntimeException error = null;
			try {
				loading.load();
			} catch (RuntimeException e) {
				error = e;
			}
			RuntimeException failure = error;
			if (!display.isDisposed()) {
				display.asyncExec(() -> {
					if (getShell() == null || getShell().isDisposed() || current != generation) {
						return;
					}
					search.setEnabled(true);
					if (failure != null) {
						setErrorMessage(failure.getMessage());
						return;
					}
					status.setText(loading.isOnline() ? loading.getTitle() + " · online · " + loading.getSource()
							+ " · " + loading.getLicense()
							: loading.getTitle() + " " + loading.getVersion() + " · " + "%,d".formatted(loading.size())
									+ " concepts · offline · " + loading.getSource());
					runSearch();
					search.setFocus();
				});
			}
			return org.eclipse.core.runtime.Status.OK_STATUS;
		});
		job.setSystem(true);
		job.schedule();
	}

	private void scheduleSearch() {
		if (searchScheduled || !terminology.isLoaded()) {
			return;
		}
		searchScheduled = true;
		getShell().getDisplay().timerExec(SEARCH_DELAY, () -> {
			searchScheduled = false;
			if (getShell() != null && !getShell().isDisposed()) {
				runSearch();
			}
		});
	}

	private void runSearch() {
		String text = search.getText().strip();
		if (terminology.isOnline()) {
			searchOnline(text);
			return;
		}
		List<Object> input = new ArrayList<>();
		if (text.isEmpty()) {
			input.addAll(terminology.roots());
		} else {
			for (Terminology.Match match : terminology.search(text, RESULT_LIMIT)) {
				input.add(match.concept());
			}
			if (input.isEmpty()) {
				input.add(NLS.bind(Messages.TerminologyLookupDialog_NoMatchesFor, text));
			}
		}
		show(input);
	}

	/** Searches an online terminology in the background (it has no hierarchy to browse). */
	private void searchOnline(String text) {
		if (text.length() < 2) {
			show(List.of(NLS.bind(Messages.TerminologyLookupDialog_TypeANameOrCode, terminology.getTitle())));
			return;
		}
		int current = ++generation;
		Terminology searched = terminology;
		show(List.of(NLS.bind(Messages.TerminologyLookupDialog_SearchingOnline, searched.getTitle())));
		org.eclipse.swt.widgets.Display display = getShell().getDisplay();
		org.eclipse.core.runtime.jobs.Job job = org.eclipse.core.runtime.jobs.Job.create(NLS.bind(Messages.TerminologyLookupDialog_Searching, searched.getTitle()), _ -> {
			List<Object> input = new ArrayList<>();
			String error = null;
			try {
				for (Terminology.Match match : searched.search(text, RESULT_LIMIT)) {
					input.add(match.concept());
				}
				if (input.isEmpty()) {
					input.add(NLS.bind(Messages.TerminologyLookupDialog_NoMatchesFor, text));
				}
			} catch (RuntimeException e) {
				error = e.getMessage();
			}
			String failure = error;
			if (!display.isDisposed()) {
				display.asyncExec(() -> {
					if (getShell() == null || getShell().isDisposed() || current != generation) {
						return;
					}
					setErrorMessage(failure);
					show(failure == null ? input : List.of());
				});
			}
			return org.eclipse.core.runtime.Status.OK_STATUS;
		});
		job.setSystem(true);
		job.schedule();
	}

	private void show(List<Object> input) {
		viewer.setInput(input);
		if (!input.isEmpty() && input.getFirst() instanceof Concept first) {
			viewer.setSelection(new org.eclipse.jface.viewers.StructuredSelection(first));
		} else {
			showDetails(null);
		}
		updateButtons();
	}

	private void showDetails(Object element) {
		if (!(element instanceof Concept concept)) {
			details.setText("");
			details.setStyleRanges(new StyleRange[0]);
			updateButtons();
			return;
		}
		StringBuilder text = new StringBuilder();
		List<StyleRange> styles = new ArrayList<>();
		append(text, styles, concept.label() + "\n", SWT.BOLD, HlomTheme.INK);
		append(text, styles, terminology.getId() + " " + concept.code()
				+ (concept.kind().isEmpty() ? "" : " · " + concept.kind()) + "\n\n", SWT.NORMAL, HlomTheme.SLATE);
		List<Concept> path = terminology.path(concept);
		if (path.size() > 1) {
			heading(text, styles, Messages.TerminologyLookupDialog_Hierarchy);
			for (int i = 0; i < path.size(); i++) {
				Concept step = path.get(i);
				text.append("  ".repeat(i)).append(i == 0 ? "" : "› ").append(step.label()).append("  ");
				append(text, styles, step.code() + "\n", SWT.NORMAL, HlomTheme.MUTED);
			}
			text.append('\n');
		}
		if (!concept.definition().isBlank()) {
			heading(text, styles, Messages.TerminologyLookupDialog_Definition);
			text.append(concept.definition()).append("\n\n");
		}
		if (!concept.synonyms().isEmpty()) {
			heading(text, styles, Messages.TerminologyLookupDialog_SynonymsAndEntryTerms);
			text.append(String.join("; ", concept.synonyms())).append("\n\n");
		}
		if (!concept.treeNumbers().isEmpty() && terminology.getId().equals("MeSH")) {
			heading(text, styles, Messages.TerminologyLookupDialog_TreeNumbers);
			text.append(String.join(", ", concept.treeNumbers())).append("\n\n");
		}
		if (!concept.related().isEmpty()) {
			heading(text, styles, Messages.TerminologyLookupDialog_SeeAlso);
			List<String> related = new ArrayList<>();
			for (String code : concept.related()) {
				Concept other = terminology.get(code);
				related.add(other == null ? code : other.label() + " (" + code + ")");
			}
			text.append(String.join("; ", related)).append("\n\n");
		}
		append(text, styles, terminology.getLicense(), SWT.ITALIC, HlomTheme.MUTED);
		details.setText(text.toString());
		details.setStyleRanges(styles.toArray(StyleRange[]::new));
		details.setTopIndex(0);
		updateButtons();
	}

	private static void heading(StringBuilder text, List<StyleRange> styles, String heading) {
		append(text, styles, heading.toUpperCase() + "\n", SWT.BOLD, HlomTheme.SLATE);
	}

	private static void append(StringBuilder text, List<StyleRange> styles, String part, int fontStyle,
			org.eclipse.swt.graphics.RGB color) {
		StyleRange range = new StyleRange(text.length(), part.length(), HlomTheme.color(color), null, fontStyle);
		styles.add(range);
		text.append(part);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setText(okLabel);
		updateButtons();
	}

	private void updateButtons() {
		if (getButton(IDialogConstants.OK_ID) == null) {
			return;
		}
		boolean enabled = multi ? !checked.isEmpty() : viewer.getStructuredSelection().getFirstElement() instanceof Concept;
		getButton(IDialogConstants.OK_ID).setEnabled(enabled);
		if (multi) {
			getButton(IDialogConstants.OK_ID).setText(checked.isEmpty() ? okLabel : okLabel + " (" + checked.size() + ")");
			getButton(IDialogConstants.OK_ID).getParent().layout();
		}
	}

	@Override
	protected void okPressed() {
		if (multi) {
			result = List.copyOf(checked.values());
		} else if (viewer.getStructuredSelection().getFirstElement() instanceof Concept concept) {
			result = List.of(concept);
		}
		resultTerminology = terminology;
		super.okPressed();
	}
}
