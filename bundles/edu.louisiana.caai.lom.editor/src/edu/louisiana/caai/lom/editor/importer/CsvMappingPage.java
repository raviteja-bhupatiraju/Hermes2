/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.importer.ColumnMapping;
import edu.louisiana.caai.lom.model.support.importer.CsvTable;
import edu.louisiana.caai.lom.model.support.importer.ImportField;

/**
 * Page 2: which HLOM field each CSV column is imported into. Columns are matched by name when the file is read;
 * every mapping can be changed in place. Ignored columns are greyed out.
 */
class CsvMappingPage extends WizardPage {

	/** The row of a column in the mapping table. */
	private record ColumnRow(int column, String header, String samples) {
	}

	/** The choice that ignores a column. */
	private static final Object IGNORE = new Object() {
		@Override
		public String toString() {
			return "— Ignore this column —";
		}
	};

	private final CsvImportWizard wizard;
	private TableViewer viewer;
	private ImportStats stats;
	private Label help;
	private CsvTable shownTable;
	private final TableColumnLayout columnLayout = new TableColumnLayout();

	CsvMappingPage(CsvImportWizard wizard) {
		super("csvMapping");
		this.wizard = wizard;
		setTitle(Messages.CsvMappingPage_MapColumnsToHLOMFields);
		setDescription(Messages.CsvMappingPage_ColumnsWereMatchedByTheir);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 6).spacing(8, 8).applyTo(composite);

		Composite top = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(8, 0).applyTo(top);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(top);
		stats = new ImportStats(top);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(stats);
		Button auto = new Button(top, SWT.PUSH);
		auto.setText(Messages.CsvMappingPage_MatchByName);
		auto.setToolTipText(Messages.CsvMappingPage_MapTheColumnsAgainFrom);
		auto.addListener(SWT.Selection, _ -> {
			wizard.setMapping(ColumnMapping.auto(wizard.table().headers()));
			refresh();
		});
		GridDataFactory.fillDefaults().align(SWT.END, SWT.END).applyTo(auto);
		Button clear = new Button(top, SWT.PUSH);
		clear.setText(Messages.CsvMappingPage_IgnoreAll);
		clear.addListener(SWT.Selection, _ -> {
			wizard.setMapping(new ColumnMapping(wizard.table().columnCount()));
			refresh();
		});
		GridDataFactory.fillDefaults().align(SWT.END, SWT.END).applyTo(clear);

		Composite tableArea = new Composite(composite, SWT.NONE);
		tableArea.setLayout(columnLayout);
		GridDataFactory.fillDefaults().grab(true, true).hint(760, 330).applyTo(tableArea);
		viewer = new TableViewer(tableArea, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.V_SCROLL);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		ColumnViewerToolTipSupport.enableFor(viewer);

		column(Messages.CsvMappingPage_CSVColumn, 190, new RowLabels() {
			@Override
			public String getText(Object element) {
				return ((ColumnRow) element).header();
			}

			@Override
			public Font getFont(Object element) {
				return field(element) != null ? HlomTheme.boldFont() : null;
			}
		});
		column(Messages.CsvMappingPage_SampleValues, 270, new RowLabels() {
			@Override
			public String getText(Object element) {
				String samples = ((ColumnRow) element).samples();
				return samples.isEmpty() ? "(empty)" : samples;
			}

			@Override
			public String getToolTipText(Object element) {
				return wizard.table().sampleValues(((ColumnRow) element).column(), 8).stream()
						.map(value -> value.length() > 120 ? value.substring(0, 117) + "..." : value)
						.reduce((a, b) -> a + "\n" + b).orElse(null);
			}
		});
		TableViewerColumn fieldColumn = column(Messages.CsvMappingPage_HLOMField, 250, new RowLabels() {
			@Override
			public String getText(Object element) {
				ImportField field = field(element);
				return field == null ? "Ignored" : field.qualifiedLabel();
			}

			@Override
			public Image getImage(Object element) {
				ImportField field = field(element);
				if (field == null) {
					return null;
				}
				double confidence = wizard.mapping().confidence(((ColumnRow) element).column());
				return HlomTheme.image(confidence >= 0.8 ? "icons/actions/ok.png" : "icons/actions/info.png");
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(field(element) == null ? HlomTheme.MUTED : HlomTheme.TEAL);
			}

			@Override
			public String getToolTipText(Object element) {
				ImportField field = field(element);
				return field == null ? "Click to choose a field" : field.help() + "\nExample: " + field.example();
			}
		});
		column(Messages.CsvMappingPage_Match, 80, new RowLabels() {
			@Override
			public String getText(Object element) {
				ImportField field = field(element);
				if (field == null) {
					return "";
				}
				double confidence = wizard.mapping().confidence(((ColumnRow) element).column());
				return confidence >= 0.999 ? "exact" : confidence >= 0.8 ? "by name" : "check";
			}
		});
		fieldColumn.setEditingSupport(new FieldEditing(viewer));
		TableViewerEditor.create(viewer, new ColumnViewerEditorActivationStrategy(viewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
						|| event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED
								&& (event.keyCode == SWT.CR || event.keyCode == SWT.F2 || event.character == ' '));
			}
		}, ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		viewer.addSelectionChangedListener(_ -> showHelp());

		help = new Label(composite, SWT.WRAP);
		help.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.fillDefaults().grab(true, false).hint(700, SWT.DEFAULT).applyTo(help);

		setControl(composite);
	}

	private TableViewerColumn column(String title, int width, ColumnLabelProvider labels) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		columnLayout.setColumnData(column.getColumn(), new ColumnWeightData(width, 60));
		column.setLabelProvider(labels);
		return column;
	}

	/** Greys out the cells of ignored columns. */
	private class RowLabels extends ColumnLabelProvider {
		@Override
		public Color getForeground(Object element) {
			return HlomTheme.color(field(element) == null ? HlomTheme.MUTED : HlomTheme.INK);
		}
	}

	private ImportField field(Object element) {
		return wizard.mapping().get(((ColumnRow) element).column());
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible && wizard.table() != shownTable) {
			shownTable = wizard.table();
			List<ColumnRow> rows = new ArrayList<>();
			CsvTable table = wizard.table();
			for (int column = 0; column < table.columnCount(); column++) {
				rows.add(new ColumnRow(column, table.headers().get(column),
						String.join("  ·  ", table.sampleValues(column, 3).stream()
								.map(value -> value.replaceAll("\\s+", " "))
								.map(value -> value.length() > 48 ? value.substring(0, 45) + "..." : value).toList())));
			}
			viewer.setInput(rows);
		}
		super.setVisible(visible);
		if (visible) {
			refresh();
			viewer.getTable().setFocus();
		}
	}

	private void refresh() {
		viewer.refresh();
		ColumnMapping mapping = wizard.mapping();
		int mapped = mapping.mappedCount();
		int fields = (int) java.util.Arrays.stream(ImportField.values()).filter(mapping::isMapped).count();
		stats.setFigures(List.of(new ImportStats.Figure(Integer.toString(mapping.size()), "columns", HlomTheme.INK),
				new ImportStats.Figure(Integer.toString(mapped), "mapped", mapped > 0 ? HlomTheme.TEAL : HlomTheme.MUTED),
				new ImportStats.Figure(Integer.toString(mapping.size() - mapped), "ignored", HlomTheme.MUTED),
				new ImportStats.Figure(Integer.toString(fields), Messages.CsvMappingPage_HLOMFields, HlomTheme.INK)));
		if (mapped == 0) {
			setMessage(null);
			setErrorMessage(Messages.CsvMappingPage_MapAtLeastOneColumn);
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			if (!mapping.isMapped(ImportField.TITLE)) {
				setMessage(Messages.CsvMappingPage_NoColumnIsMappedTo,
						IMessageProvider.WARNING);
			} else {
				setMessage(null);
			}
			setPageComplete(true);
		}
		showHelp();
	}

	private void showHelp() {
		Object selected = viewer.getStructuredSelection().getFirstElement();
		ImportField field = selected == null ? null : field(selected);
		if (field != null) {
			help.setText(field.qualifiedLabel() + ": " + field.help() + ".   Example: " + field.example());
		} else {
			help.setText(Messages.CsvMappingPage_ListsInACellAre);
		}
		help.getParent().layout(true, true);
	}

	/** Edits the field of a column with a combo of all fields. */
	private class FieldEditing extends EditingSupport {
		private final ComboBoxViewerCellEditor editor;

		FieldEditing(TableViewer tableViewer) {
			super(tableViewer);
			editor = new ComboBoxViewerCellEditor(tableViewer.getTable(), SWT.READ_ONLY);
			editor.setContentProvider(ArrayContentProvider.getInstance());
			editor.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					return element instanceof ImportField field ? field.qualifiedLabel() : String.valueOf(element);
				}
			});
			List<Object> choices = new ArrayList<>();
			choices.add(IGNORE);
			choices.addAll(List.of(ImportField.values()));
			editor.setInput(choices);
			editor.setActivationStyle(ComboBoxViewerCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
					| ComboBoxViewerCellEditor.DROP_DOWN_ON_KEY_ACTIVATION);
			editor.getViewer().getCCombo().setVisibleItemCount(20);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			ImportField field = field(element);
			return field == null ? IGNORE : field;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (value == null) {
				return;
			}
			ImportField field = value instanceof ImportField chosen ? chosen : null;
			int column = ((ColumnRow) element).column();
			if (wizard.mapping().get(column) != field) {
				ColumnMapping mapping = wizard.mapping().copy();
				if (field != null && !field.repeatable()) {
					for (int other : mapping.columns(field)) {
						mapping.set(other, null);
					}
				}
				mapping.set(column, field);
				wizard.setMapping(mapping);
				refresh();
			}
		}
	}
}
