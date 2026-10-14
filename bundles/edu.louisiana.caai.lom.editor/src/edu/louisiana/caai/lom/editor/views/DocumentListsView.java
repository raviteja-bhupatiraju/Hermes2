/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.views;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultGridLayer;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.ListSpecs;
import edu.louisiana.caai.medbiq.model.forms.ListSpecs.Column;
import edu.louisiana.caai.medbiq.model.forms.ListSpecs.ListSpec;

/**
 * Window &gt; Document Lists: a list of the active document (such as the events, sequence blocks or competency
 * objects of a Curriculum Inventory) as a table (Eclipse NatTable), one row per item and one column per field.
 * Simple values are edited in the cells (each change undoable in the editor); double-clicking a row number shows the
 * item in the editor.
 */
public final class DocumentListsView extends DocumentViewPart {

	/** The id of the view. */
	public static final String ID = "edu.louisiana.caai.lom.editor.views.DocumentListsView";

	private Composite body;
	private Combo choice;
	private Composite tableHolder;
	private NatTable table;
	private List<ListSpec> lists = List.of();
	private String selectedPath;
	private final List<EObject> rows = new ArrayList<>();
	/** The list and editor of the table shown (a change of the document only updates its rows). */
	private ListSpec shownSpec;
	private HlomMetadataEditor shownEditor;

	@Override
	protected Control createContent(Composite parent) {
		body = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(6, 6).applyTo(body);
		new Label(body, SWT.NONE).setText(Messages.DocumentListsView_List);
		choice = new Combo(body, SWT.READ_ONLY | SWT.DROP_DOWN);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(choice);
		choice.addListener(SWT.Selection, _ -> {
			int index = choice.getSelectionIndex();
			selectedPath = index < 0 ? null : lists.get(index).path();
			refresh();
		});
		tableHolder = new Composite(body, SWT.NONE);
		tableHolder.setLayout(new org.eclipse.swt.layout.FillLayout());
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(tableHolder);
		return body;
	}

	@Override
	protected String show(HlomMetadataEditor editor) {
		if (!(editor.documentType() instanceof MedbiqDocumentType type)) {
			return Messages.DocumentListsView_TheListsOfADocument;
		}
		lists = ListSpecs.of(type);
		if (lists.isEmpty()) {
			return NLS.bind(Messages.DocumentListsView_ThisDocumentHasNoLists, type.label());
		}
		ListSpec spec = lists.stream().filter(list -> list.path().equals(selectedPath)).findFirst()
				.orElse(lists.getFirst());
		selectedPath = spec.path();
		choice.setItems(lists.stream().map(ListSpec::label).toArray(String[]::new));
		choice.select(lists.indexOf(spec));
		EObject root = Paths.rootElement(editor.documentRoot());
		rows.clear();
		rows.addAll(Paths.objects(root, spec.path()));
		if (table != null && !table.isDisposed() && spec.equals(shownSpec) && editor == shownEditor) {
			table.refresh();
		} else {
			createTable(editor, spec);
			shownSpec = spec;
			shownEditor = editor;
		}
		return null;
	}

	private void createTable(HlomMetadataEditor editor, ListSpec spec) {
		if (table != null) {
			table.dispose();
		}
		List<Column> columns = spec.columns();
		IColumnAccessor<EObject> accessor = new IColumnAccessor<>() {
			@Override
			public Object getDataValue(EObject row, int column) {
				return String.join("; ", Paths.texts(row, columns.get(column).path()));
			}

			@Override
			public void setDataValue(EObject row, int column, Object value) {
				set(editor.getEditingDomain(), row, columns.get(column).path(), value == null ? "" : value.toString());
			}

			@Override
			public int getColumnCount() {
				return columns.size();
			}
		};
		IDataProvider bodyData = new ListDataProvider<>(rows, accessor);
		IDataProvider headerData = new IDataProvider() {
			@Override
			public Object getDataValue(int column, int row) {
				return columns.get(column).label();
			}

			@Override
			public void setDataValue(int column, int row, Object value) {
				// The column titles are fixed.
			}

			@Override
			public int getColumnCount() {
				return columns.size();
			}

			@Override
			public int getRowCount() {
				return 1;
			}
		};
		DefaultGridLayer grid = new DefaultGridLayer(bodyData, headerData);
		table = new NatTable(tableHolder, grid, false);
		table.addConfiguration(new DefaultNatTableStyleConfiguration());
		table.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry registry) {
				registry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, new EditableRule() {
					@Override
					public boolean isEditable(int column, int row) {
						return row >= 0 && row < rows.size() && editableFeature(rows.get(row), columns.get(column).path()) != null;
					}
				});
			}

			@Override
			public void configureUiBindings(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry registry) {
				registry.registerDoubleClickBinding(new MouseEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, 1),
						(natTable, event) -> {
							int row = natTable.getRowIndexByPosition(natTable.getRowPositionByY(event.y));
							if (row >= 0 && row < rows.size() && editor() != null) {
								getSite().getPage().activate(editor());
								editor().reveal(rows.get(row));
							}
						});
			}
		});
		table.configure();
		tableHolder.layout(true);
	}

	/** The attribute a value at a path is edited with, if it is one simple value of the item; else {@code null}. */
	static EAttribute editableFeature(EObject item, String path) {
		if (path.contains("/")) {
			return null;
		}
		EStructuralFeature feature = Paths.feature(item, path);
		return feature instanceof EAttribute attribute && !attribute.isMany() ? attribute : null;
	}

	/** Sets a simple value of an item as an undoable command; invalid values are ignored. */
	public static void set(EditingDomain domain, EObject item, String path, String text) {
		EAttribute attribute = editableFeature(item, path);
		if (attribute == null) {
			return;
		}
		Object value;
		try {
			value = text.isBlank() ? SetCommand.UNSET_VALUE : ModelCommands.fromString(attribute, text.strip());
		} catch (RuntimeException _) {
			return;
		}
		Command command = SetCommand.create(domain, item, attribute, value);
		if (command.canExecute()) {
			domain.getCommandStack().execute(command);
		}
	}

	@Override
	protected String emptyMessage() {
		return Messages.DocumentListsView_OpenADocumentToSee;
	}

	/** The rows shown, for tests. */
	public List<EObject> rows() {
		return List.copyOf(rows);
	}

	/** Shows a list by its path, for tests. */
	public void showList(String path) {
		selectedPath = path;
		refresh();
	}

	@Override
	public void setFocus() {
		if (choice != null && !choice.isDisposed()) {
			choice.setFocus();
		}
	}
}
