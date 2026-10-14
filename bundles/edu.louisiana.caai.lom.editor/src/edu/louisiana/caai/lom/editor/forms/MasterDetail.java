/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import org.eclipse.osgi.util.NLS;

import java.util.function.Function;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;

/**
 * A master/detail block for repeating complex elements (contributors, credits, relations, ...): a compact list
 * of the elements with a toolbar on the left and data-bound fields for the selected element on the right.
 */
public final class MasterDetail {

	/** Creates a new element when the user presses Add. */
	@FunctionalInterface
	public interface ElementFactory {
		EObject create();
	}

	/** Builds the detail fields; the given form support is anchored at the selected element. */
	@FunctionalInterface
	public interface DetailBuilder {
		void build(FormSupport detailForm, Composite detail);
	}

	/** The initial width of the list beside the details; the sash between them resizes it. */
	private static final int MASTER_WIDTH = 260;
	private static final int MIN_MASTER_WIDTH = 120;
	private static final int MIN_ROWS = FormWidgets.LIST_MIN_ROWS;
	/** Lists stacked above their details are as short as their elements allow. */
	private static final int NESTED_MIN_ROWS = 2;
	private static final int MAX_ROWS = FormWidgets.LIST_MAX_ROWS;
	private static final String NESTING_KEY = MasterDetail.class.getName();

	private final FormSupport form;
	private final ModelPath ownerPath;
	private final EReference reference;
	private final ElementFactory factory;
	private final Composite composite;
	private final Composite master;
	private final TableViewer viewer;
	private final IObservableValue<EObject> selection;
	private final IObservableList<EObject> elements;
	private final ToolItem removeItem;
	private final ToolItem upItem;
	private final ToolItem downItem;
	private final int minRows;

	MasterDetail(FormSupport form, Composite parent, ModelPath ownerPath, EReference reference,
			Function<EObject, String> labeler, ElementFactory factory, DetailBuilder details) {
		this.form = form;
		this.ownerPath = ownerPath;
		this.reference = reference;
		this.factory = factory;

		String typeName = typeName(reference);
		// A block nested in the detail of another block stacks its list above the details to save width.
		boolean nested = isNested(parent);
		composite = form.toolkit().createComposite(parent);
		composite.setData(NESTING_KEY, Boolean.TRUE);
		GridLayoutFactory.fillDefaults().numColumns(nested ? 1 : 3).spacing(0, nested ? 8 : 0).applyTo(composite);

		// Master: toolbar above a list sized to its elements; beside the details, the list also fills the height the
		// details take (nothing else uses that column).
		master = form.toolkit().createComposite(composite);
		GridLayoutFactory.fillDefaults().spacing(0, 2).applyTo(master);
		GridDataFactory.fillDefaults().align(SWT.FILL, nested ? SWT.BEGINNING : SWT.FILL).grab(nested, false)
				.hint(nested ? SWT.DEFAULT : MASTER_WIDTH, SWT.DEFAULT).applyTo(master);
		ToolBar bar = FormWidgets.toolBar(form.toolkit(), master);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(bar);
		FormWidgets.toolItem(bar, "add", Messages.MasterDetail_Add, NLS.bind(Messages.MasterDetail_Add2, article(typeName), typeName), this::addElement);
		removeItem = FormWidgets.toolItem(bar, "remove", Messages.MasterDetail_Remove, NLS.bind(Messages.MasterDetail_RemoveTheSelected, typeName),
				this::removeSelected);
		upItem = FormWidgets.toolItem(bar, "up", Messages.MasterDetail_Up, NLS.bind(Messages.MasterDetail_MoveTheSelectedUp, typeName), () -> move(-1));
		downItem = FormWidgets.toolItem(bar, "down", Messages.MasterDetail_Down, NLS.bind(Messages.MasterDetail_MoveTheSelectedDown, typeName),
				() -> move(1));
		form.bindEnabled(bar);

		// The width hint keeps the table's preferred width (the sum of its column widths) from widening the page.
		Composite holder = form.toolkit().createComposite(master);
		GridDataFactory.fillDefaults().grab(true, !nested).hint(100, SWT.DEFAULT).applyTo(holder);
		holder.setLayout(new FillLayout());
		Table table = form.toolkit().createTable(holder, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		FormWidgets.styleTable(table);
		FormWidgets.emptyMessage(table, Messages.MasterDetail_NoneYetClickToAdd);
		viewer = new TableViewer(table);
		ColumnViewerToolTipSupport.enableFor(viewer);
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		FormWidgets.fillColumn(table, 0);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String text = labeler.apply((EObject) element);
				return text == null || text.isBlank() ? "(new " + typeName + ")" : text;
			}

			@Override
			public Color getForeground(Object element) {
				String text = labeler.apply((EObject) element);
				return text == null || text.isBlank() ? HlomTheme.color(HlomTheme.MUTED) : null;
			}

			@Override
			public String getToolTipText(Object element) {
				// The full names of elements the list truncates
				return getText(element);
			}
		});
		ObservableListContentProvider<EObject> contentProvider = new ObservableListContentProvider<>();
		viewer.setContentProvider(contentProvider);
		elements = form.context().list(ownerPath, reference).observeDetail(form.anchor());
		viewer.setInput(elements);
		Runnable refresher = () -> {
			if (!table.isDisposed()) {
				viewer.refresh(true);
				updateActions();
			}
		};
		form.context().broadcaster().addListener(refresher);
		composite.addDisposeListener(_ -> form.context().broadcaster().removeListener(refresher));

		if (!nested) {
			sash();
		}

		Composite detail = form.toolkit().createComposite(composite);
		GridDataFactory.fillDefaults().grab(true, false).indent(nested ? 0 : 4, 0).applyTo(detail);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(8, 8).extendedMargins(8, 0, 0, 0).applyTo(detail);

		selection = ViewerProperties.singleSelection(EObject.class).observe(viewer);
		selection.addValueChangeListener(event -> {
			EObject selected = event.diff.getNewValue();
			if (selected != null && form.selectionSink() != null) {
				form.selectionSink().accept(new StructuredSelection(selected));
			}
			updateActions();
		});
		details.build(form.withAnchor(selection), detail);

		// Select the first element initially and whenever the list was empty before.
		elements.addListChangeListener(_ -> {
			if (selection.getValue() == null && !elements.isEmpty()) {
				viewer.setSelection(new StructuredSelection(elements.getFirst()), true);
			}
			resize();
		});
		if (!elements.isEmpty()) {
			viewer.setSelection(new StructuredSelection(elements.getFirst()), true);
		}
		minRows = nested ? NESTED_MIN_ROWS : MIN_ROWS;
		FormWidgets.fitRows(table, holder, minRows, MAX_ROWS);
		updateActions();
	}

	/**
	 * A sash between the list and the details (drawn as a thin rule), as in Eclipse's master/details blocks:
	 * dragging it gives the list the width its element names need.
	 */
	private void sash() {
		Sash sash = new Sash(composite, SWT.VERTICAL);
		form.toolkit().adapt(sash, false, false);
		GridDataFactory.fillDefaults().grab(false, true).hint(9, SWT.DEFAULT).indent(4, 0).applyTo(sash);
		sash.addPaintListener(event -> {
			Point area = sash.getSize();
			event.gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			event.gc.drawLine(area.x / 2, 0, area.x / 2, area.y);
		});
		sash.addListener(SWT.Selection, event -> {
			GridData data = (GridData) master.getLayoutData();
			int max = Math.max(MIN_MASTER_WIDTH, composite.getClientArea().width * 2 / 3);
			int width = Math.clamp(event.x - master.getBounds().x - 4, MIN_MASTER_WIDTH, max);
			if (data.widthHint != width) {
				data.widthHint = width;
				composite.layout(true, true);
			}
			event.doit = false;
		});
	}

	private static boolean isNested(Composite parent) {
		for (Composite c = parent; c != null; c = c.getParent()) {
			if (c.getData(NESTING_KEY) != null) {
				return true;
			}
		}
		return false;
	}

	public Control control() {
		return composite;
	}

	public TableViewer viewer() {
		return viewer;
	}

	/** A readable name of the element type, e.g. "contribute" for {@code Contribute}. */
	private static String typeName(EReference reference) {
		String name = reference.getEReferenceType().getName().replaceFirst("Type$", "");
		return name.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase(java.util.Locale.ROOT);
	}

	private static String article(String noun) {
		return !noun.isEmpty() && "aeiou".indexOf(noun.charAt(0)) >= 0 ? "an" : "a";
	}

	private EditingDomain domain() {
		return form.context().domain();
	}

	private EObject selected() {
		Object selected = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		return selected instanceof EObject eObject ? eObject : null;
	}

	private void updateActions() {
		if (viewer.getControl().isDisposed()) {
			return;
		}
		EObject selected = selected();
		int index = selected == null ? -1 : elements.indexOf(selected);
		removeItem.setEnabled(selected != null);
		upItem.setEnabled(index > 0);
		downItem.setEnabled(index >= 0 && index < elements.size() - 1);
	}

	/** Adapts the list height to the number of elements and reflows the page. */
	private void resize() {
		if (!viewer.getControl().isDisposed()) {
			FormWidgets.fitRows(viewer.getTable(), viewer.getTable().getParent(), minRows, MAX_ROWS);
			FormWidgets.reflow(composite);
			updateActions();
		}
	}

	private void addElement() {
		EObject anchor = form.anchor().getValue();
		if (anchor == null) {
			return;
		}
		EObject element = factory.create();
		if (ModelCommands.execute(domain(), ModelCommands.addElement(domain(), anchor, ownerPath, reference, element))) {
			// The list updates when the change is broadcast; select the new element afterwards.
			composite.getDisplay().asyncExec(() -> {
				if (!viewer.getControl().isDisposed()) {
					viewer.setSelection(new StructuredSelection(element), true);
				}
			});
		}
	}

	private void removeSelected() {
		EObject selected = selected();
		if (selected != null) {
			ModelCommands.execute(domain(), ModelCommands.remove(domain(), selected));
		}
	}

	private void move(int delta) {
		EObject selected = selected();
		if (selected != null) {
			ModelCommands.execute(domain(), ModelCommands.move(domain(), selected, delta));
		}
	}
}
