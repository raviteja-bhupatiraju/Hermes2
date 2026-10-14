/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.mesh;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.mesh.MeshClient;
import edu.louisiana.caai.lom.model.support.mesh.MeshDetails;
import edu.louisiana.caai.lom.model.support.mesh.MeshException;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;

/**
 * Searches the NLM Medical Subject Headings and lets the user pick descriptors to add as keywords. Queries
 * run in background jobs, 300 ms after the user stops typing; the details of the selected descriptor (scope
 * note, tree numbers, entry terms) are shown next to the results.
 */
public class MeshLookupDialog extends TitleAreaDialog {

	private static final int DEBOUNCE_MS = 300;
	private static final int RESULT_LIMIT = MeshClient.MAX_LIMIT;

	private final MeshClient client;
	private final String initialText;
	private final Set<String> existingIds;

	private Text searchText;
	private TableViewer viewer;
	private StyledText detailsText;
	private CLabel statusLabel;
	private Link retryLink;

	private final Runnable debouncedSearch = this::search;
	private int searchGeneration;
	private int detailsGeneration;
	private String lastQuery;
	private List<MeshTerm> chosen = Collections.emptyList();

	/**
	 * @param existingIds descriptor IDs that are already keywords (shown as such and not added again)
	 */
	public MeshLookupDialog(Shell parentShell, MeshClient client, String initialText, Set<String> existingIds) {
		super(parentShell);
		this.client = client;
		this.initialText = initialText == null ? "" : initialText.strip();
		this.existingIds = existingIds == null ? Set.of() : Set.copyOf(existingIds);
		setHelpAvailable(false);
	}

	/** Opens the dialog and returns the chosen descriptors (empty if cancelled). */
	public static List<MeshTerm> open(Shell shell, String initialText) {
		return open(shell, initialText, Set.of(), MeshClient.shared());
	}

	/** Opens the dialog with descriptors already present marked as such; returns the new chosen descriptors. */
	public static List<MeshTerm> open(Shell shell, String initialText, Set<String> existingIds, MeshClient client) {
		MeshLookupDialog dialog = new MeshLookupDialog(shell, client, initialText, existingIds);
		return dialog.open() == OK ? dialog.getChosenTerms() : List.of();
	}

	/** The descriptors chosen when the dialog was closed with OK. */
	public List<MeshTerm> getChosenTerms() {
		return chosen;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.MeshLookupDialog_MeSHLookup);
		Image icon = HlomTheme.image("icons/mesh/mesh.png");
		if (icon != null) {
			shell.setImage(icon);
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		// A fixed size: the wrapped details text would otherwise ask for the width of its longest paragraph.
		org.eclipse.swt.graphics.Rectangle screen = getShell().getMonitor().getClientArea();
		return new Point(Math.min(convertHorizontalDLUsToPixels(480), screen.width * 9 / 10),
				Math.min(convertVerticalDLUsToPixels(320), screen.height * 9 / 10));
	}

	@Override
	public void create() {
		super.create();
		setTitle(Messages.MeshLookupDialog_AddMeSHKeywords);
		setMessage(Messages.MeshLookupDialog_SearchTheMedicalSubjectHeadings);
		Image banner = HlomTheme.image("icons/mesh/mesh-title.png");
		if (banner != null) {
			setTitleImage(banner);
		}
		updateButtons();
		if (!initialText.isEmpty()) {
			searchText.setText(initialText);
			searchText.selectAll();
			search();
		}
		searchText.setFocus();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite body = new Composite(area, SWT.NONE);
		body.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);
		GridLayoutFactory.fillDefaults().margins(14, 12).spacing(0, 8).applyTo(body);

		searchText = new Text(body, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL | SWT.BORDER);
		searchText.setMessage(Messages.MeshLookupDialog_SearchMeSHEGMyocardial);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(searchText);
		searchText.addModifyListener(_ -> scheduleSearch());
		searchText.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false; // Enter searches right away instead of closing the dialog.
				searchText.getDisplay().timerExec(-1, debouncedSearch);
				search();
			}
		});
		searchText.addListener(SWT.KeyDown, e -> {
			if (e.keyCode == SWT.ARROW_DOWN && viewer.getTable().getItemCount() > 0) {
				e.doit = false;
				viewer.getTable().setFocus();
				if (viewer.getSelection().isEmpty()) {
					viewer.setSelection(new StructuredSelection(viewer.getElementAt(0)), true);
				}
			}
		});

		SashForm sash = new SashForm(body, SWT.HORIZONTAL | SWT.SMOOTH);
		sash.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		sash.setSashWidth(8);
		GridDataFactory.fillDefaults().grab(true, true)
				.hint(convertHorizontalDLUsToPixels(440), convertVerticalDLUsToPixels(170)).applyTo(sash);
		createResults(sash);
		createDetails(sash);
		sash.setWeights(56, 44);

		createStatusLine(body);
		showPlaceholder();
		return area;
	}

	private void createResults(Composite parent) {
		Composite holder = new Composite(parent, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout(true);
		holder.setLayout(layout);
		viewer = new TableViewer(holder, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(false);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn label = new TableViewerColumn(viewer, SWT.LEFT);
		label.getColumn().setText(Messages.MeshLookupDialog_Descriptor);
		label.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MeshTerm) element).label();
			}

			@Override
			public Image getImage(Object element) {
				return isExisting(element) ? HlomTheme.image("icons/actions/ok.png") : null;
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(isExisting(element) ? HlomTheme.MUTED : HlomTheme.INK);
			}

			@Override
			public String getToolTipText(Object element) {
				return isExisting(element) ? "Already a keyword of this document" : null;
			}
		});
		layout.setColumnData(label.getColumn(), new ColumnWeightData(100, 160));

		TableViewerColumn id = new TableViewerColumn(viewer, SWT.LEFT);
		id.getColumn().setText(Messages.MeshLookupDialog_MeSHID);
		id.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MeshTerm) element).id();
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(isExisting(element) ? HlomTheme.MUTED : HlomTheme.TEAL);
			}
		});
		layout.setColumnData(id.getColumn(), new ColumnWeightData(0, convertWidthInCharsToPixels(14)));

		viewer.addSelectionChangedListener(_ -> {
			updateButtons();
			IStructuredSelection selection = viewer.getStructuredSelection();
			if (selection.size() == 1) {
				showDetails((MeshTerm) selection.getFirstElement());
			} else if (selection.size() > 1) {
				showSummary(selection.size());
			} else if (viewer.getTable().getItemCount() > 0) {
				showPlaceholder();
			}
		});
		viewer.addDoubleClickListener(_ -> {
			if (!newSelection().isEmpty()) {
				okPressed();
			}
		});
	}

	private void createDetails(Composite parent) {
		Composite frame = new Composite(parent, SWT.BORDER);
		frame.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		frame.setLayout(new FillLayout());
		detailsText = new StyledText(frame, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		detailsText.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		detailsText.setForeground(HlomTheme.color(HlomTheme.INK));
		detailsText.setMargins(12, 10, 12, 10);
		detailsText.setLineSpacing(3);
		detailsText.setCaret(null);
	}

	private void createStatusLine(Composite parent) {
		Composite line = new Composite(parent, SWT.NONE);
		line.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(line);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(8, 0).applyTo(line);

		statusLabel = new CLabel(line, SWT.NONE);
		statusLabel.setBackground(parent.getBackground());
		statusLabel.setForeground(HlomTheme.color(HlomTheme.SLATE));
		statusLabel.setMargins(0, 2, 0, 2);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.CENTER).applyTo(statusLabel);

		retryLink = new Link(line, SWT.NONE);
		retryLink.setText("<a>Retry</a>");
		retryLink.setBackground(parent.getBackground());
		retryLink.addListener(SWT.Selection, _ -> {
			lastQuery = null;
			search();
		});
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).exclude(true).applyTo(retryLink);
		retryLink.setVisible(false);

		Label attribution = new Label(line, SWT.RIGHT);
		attribution.setText(Messages.MeshLookupDialog_SourceUSNationalLibrary);
		attribution.setBackground(parent.getBackground());
		attribution.setForeground(HlomTheme.color(HlomTheme.MUTED));
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.END, SWT.CENTER).applyTo(attribution);
		setStatus(Messages.MeshLookupDialog_TypeAtLeastTwoCharacters, null, HlomTheme.MUTED, false);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Messages.MeshLookupDialog_AddKeywords, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	// ----------------------------------------------------------------------------------------------------
	// Searching
	// ----------------------------------------------------------------------------------------------------

	private void scheduleSearch() {
		searchText.getDisplay().timerExec(-1, debouncedSearch);
		searchText.getDisplay().timerExec(DEBOUNCE_MS, debouncedSearch);
	}

	private void search() {
		if (searchText == null || searchText.isDisposed()) {
			return;
		}
		String query = searchText.getText().strip();
		if (query.equals(lastQuery)) {
			return;
		}
		lastQuery = query;
		int generation = ++searchGeneration;
		detailsGeneration++;
		if (query.length() < 2) {
			viewer.setInput(List.of());
			showPlaceholder();
			setStatus(Messages.MeshLookupDialog_TypeAtLeastTwoCharacters, null, HlomTheme.MUTED, false);
			updateButtons();
			return;
		}
		setStatus(Messages.MeshLookupDialog_SearchingMeSH, null, HlomTheme.SLATE, false);
		Job job = Job.create(Messages.MeshLookupDialog_SearchingMeSH2, (IProgressMonitor _) -> {
			List<MeshTerm> results;
			MeshException failure = null;
			try {
				results = client.search(query, RESULT_LIMIT);
			} catch (MeshException e) {
				results = List.of();
				failure = e;
			}
			List<MeshTerm> found = results;
			MeshException error = failure;
			asyncExec(() -> {
				if (generation == searchGeneration) {
					showResults(query, found, error);
				}
			});
			return Status.OK_STATUS;
		});
		job.setSystem(true);
		job.schedule();
	}

	private void showResults(String query, List<MeshTerm> results, MeshException error) {
		viewer.setInput(results);
		if (error != null) {
			lastQuery = null; // Allow the same query again.
			showPlaceholder();
			if (error.kind() == MeshException.Kind.OFFLINE) {
				setStatus(Messages.MeshLookupDialog_OfflineCheckYourConnection, "icons/actions/warning.png", HlomTheme.AMBER,
						true);
			} else if (error.kind() != MeshException.Kind.CANCELLED) {
				setStatus(Messages.MeshLookupDialog_TheMeSHServiceIsNot, "icons/actions/warning.png",
						HlomTheme.AMBER, true);
			}
		} else if (results.isEmpty()) {
			showPlaceholder();
			setStatus(NLS.bind(Messages.MeshLookupDialog_NoResultsFor, query), "icons/actions/info.png", HlomTheme.SLATE, false);
		} else {
			String count = results.size() == 1 ? "1 descriptor" : results.size() + " descriptors";
			if (results.size() >= RESULT_LIMIT) {
				count = NLS.bind(Messages.MeshLookupDialog_FirstDescriptors, results.size());
			}
			setStatus(NLS.bind(Messages.MeshLookupDialog_SelectOneOrMoreAnd, count), null, HlomTheme.SLATE, false);
			MeshTerm first = results.stream().filter(term -> !isExisting(term)).findFirst().orElse(results.getFirst());
			viewer.setSelection(new StructuredSelection(first), true);
		}
		updateButtons();
	}

	private void showDetails(MeshTerm term) {
		int generation = ++detailsGeneration;
		renderDetails(term, null, true, null);
		Job job = Job.create(Messages.MeshLookupDialog_LoadingMeSHDetails, (IProgressMonitor _) -> {
			MeshDetails details = null;
			MeshException failure = null;
			try {
				details = client.details(term.id());
			} catch (MeshException e) {
				failure = e;
			}
			MeshDetails loaded = details;
			MeshException error = failure;
			asyncExec(() -> {
				if (generation == detailsGeneration) {
					renderDetails(term, loaded, false, error);
				}
			});
			return Status.OK_STATUS;
		});
		job.setSystem(true);
		job.schedule();
	}

	private void asyncExec(Runnable runnable) {
		Shell shell = getShell();
		if (shell == null || shell.isDisposed()) {
			return;
		}
		shell.getDisplay().asyncExec(() -> {
			if (getShell() != null && !getShell().isDisposed()) {
				runnable.run();
			}
		});
	}

	// ----------------------------------------------------------------------------------------------------
	// Details pane
	// ----------------------------------------------------------------------------------------------------

	/** Builds the text of the details pane with its style ranges. */
	private final class Rich {
		private final StringBuilder text = new StringBuilder();
		private final List<StyleRange> styles = new ArrayList<>();

		Rich append(String string, RGB color, Font font) {
			StyleRange range = new StyleRange(text.length(), string.length(), HlomTheme.color(color), null);
			range.font = font;
			styles.add(range);
			text.append(string);
			return this;
		}

		Rich heading(String string) {
			return append("\n" + string.toUpperCase() + "\n", HlomTheme.SLATE, HlomTheme.groupFont());
		}

		void apply() {
			detailsText.setText(text.toString());
			detailsText.setStyleRanges(styles.toArray(StyleRange[]::new));
			detailsText.setSelection(0);
			detailsText.setTopPixel(0);
		}
	}

	private void renderDetails(MeshTerm term, MeshDetails details, boolean loading, MeshException error) {
		Rich rich = new Rich();
		rich.append(details != null ? details.label() : term.label(), HlomTheme.INK, HlomTheme.sectionFont())
				.append("\n", HlomTheme.INK, null);
		rich.append(term.id(), HlomTheme.TEAL, HlomTheme.boldFont()).append(Messages.MeshLookupDialog_MeSHDescriptor, HlomTheme.MUTED,
				null);
		if (isExisting(term)) {
			rich.append(Messages.MeshLookupDialog_AlreadyAKeywordOfThis, HlomTheme.AMBER, null);
		}
		if (loading) {
			rich.append(Messages.MeshLookupDialog_LoadingDetails, HlomTheme.MUTED, null);
		} else if (error != null) {
			rich.append("\nDetails are not available"
					+ (error.kind() == MeshException.Kind.OFFLINE ? " offline." : " right now."), HlomTheme.AMBER,
					null);
		} else if (details == null) {
			rich.append(Messages.MeshLookupDialog_NoDetailsFoundForThis, HlomTheme.MUTED, null);
		} else {
			rich.heading(Messages.MeshLookupDialog_ScopeNote);
			rich.append(details.scopeNote() != null ? details.scopeNote() : "No scope note.",
					details.scopeNote() != null ? HlomTheme.INK : HlomTheme.MUTED, null).append("\n", HlomTheme.INK,
							null);
			if (!details.treeNumbers().isEmpty()) {
				rich.heading(Messages.MeshLookupDialog_TreeNumbers);
				rich.append(String.join("\n", details.treeNumbers()) + "\n", HlomTheme.INK, null);
			}
			if (!details.entryTerms().isEmpty()) {
				rich.heading(Messages.MeshLookupDialog_EntryTerms);
				rich.append(String.join("  ·  ", details.entryTerms()) + "\n", HlomTheme.INK, null);
			}
			if (!details.seeAlso().isEmpty()) {
				rich.heading(Messages.MeshLookupDialog_SeeAlso);
				rich.append(String.join("  ·  ", details.seeAlso().stream().map(MeshTerm::label).toList()),
						HlomTheme.INK, null);
			}
		}
		rich.apply();
	}

	private void showSummary(int count) {
		detailsGeneration++;
		int adding = newSelection().size();
		Rich rich = new Rich();
		rich.append(NLS.bind(Messages.MeshLookupDialog_DescriptorsSelected, count), HlomTheme.INK, HlomTheme.sectionFont());
		rich.append(adding == count ? "All of them will be added as keywords."
				: adding + " of them will be added; the others are already keywords.", HlomTheme.SLATE, null);
		rich.heading(Messages.MeshLookupDialog_Selected);
		for (Object element : viewer.getStructuredSelection().toList()) {
			MeshTerm term = (MeshTerm) element;
			rich.append(term.label(), isExisting(term) ? HlomTheme.MUTED : HlomTheme.INK, null)
					.append("  " + term.id() + "\n", HlomTheme.TEAL, null);
		}
		rich.apply();
	}

	private void showPlaceholder() {
		Rich rich = new Rich();
		rich.append(Messages.MeshLookupDialog_MedicalSubjectHeadings, HlomTheme.INK, HlomTheme.sectionFont());
		rich.append(Messages.MeshLookupDialog_MeSHIsTheNLMControlled,
				HlomTheme.SLATE, null);
		rich.heading(Messages.MeshLookupDialog_Tips);
		rich.append("""
				Search by name, e.g. “myocardial infarction”, or by descriptor ID, e.g. D009203.
				Hold Ctrl or Shift to select several descriptors.
				Double-click a descriptor to add it right away.""", HlomTheme.SLATE, null);
		rich.apply();
	}

	// ----------------------------------------------------------------------------------------------------

	private void setStatus(String message, String imagePath, RGB color, boolean retry) {
		if (statusLabel == null || statusLabel.isDisposed()) {
			return;
		}
		statusLabel.setText(message);
		statusLabel.setImage(imagePath == null ? null : HlomTheme.image(imagePath));
		statusLabel.setForeground(HlomTheme.color(color));
		((org.eclipse.swt.layout.GridData) retryLink.getLayoutData()).exclude = !retry;
		retryLink.setVisible(retry);
		statusLabel.getParent().layout(true);
	}

	private boolean isExisting(Object element) {
		return element instanceof MeshTerm term && existingIds.contains(term.id());
	}

	private List<MeshTerm> newSelection() {
		List<MeshTerm> terms = new ArrayList<>();
		if (viewer != null && !viewer.getControl().isDisposed()) {
			for (Object element : viewer.getStructuredSelection().toList()) {
				if (!isExisting(element)) {
					terms.add((MeshTerm) element);
				}
			}
		}
		return terms;
	}

	private void updateButtons() {
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok == null || ok.isDisposed()) {
			return;
		}
		int count = newSelection().size();
		ok.setEnabled(count > 0);
		ok.setText(count > 1 ? "Add " + count + " Keywords" : "Add Keyword" + (count == 1 ? "" : "s"));
		if (ok.getLayoutData() instanceof org.eclipse.swt.layout.GridData data) {
			data.widthHint = Math.max(data.widthHint, ok.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		}
		ok.getParent().layout(true);
	}

	@Override
	protected void okPressed() {
		chosen = List.copyOf(newSelection());
		if (chosen.isEmpty()) {
			return;
		}
		super.okPressed();
	}

	@Override
	public boolean close() {
		if (getShell() != null && !getShell().isDisposed()) {
			getShell().getDisplay().timerExec(-1, debouncedSearch);
		}
		searchGeneration++;
		detailsGeneration++;
		return super.close();
	}
}
