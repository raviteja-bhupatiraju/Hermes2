/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.List;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog;
import edu.louisiana.caai.lom.editor.terminology.TerminologySupport;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * An editable list of LanguageString elements (e.g. keywords, professions): an input field with a compact
 * toolbar, and a table sized to its rows. Each row shows the text and language of the element's first string
 * (a translate marker shows that more languages exist); the translate action edits all of its strings. For
 * keywords, the HLOM {@code hx:source} and {@code hx:id} attributes are shown as extra columns.
 */
public final class LanguageStringList {

	/** Entry lists are as short as their entries allow (the empty message fits two rows). */
	private static final int MIN_ROWS = 2;
	private static final int MAX_ROWS = FormWidgets.LIST_MAX_ROWS;

	private final FormSupport form;
	private final ModelPath ownerPath;
	private final EReference reference;
	private final Composite composite;
	private final TableViewer viewer;
	private final Combo input;
	private final String title;
	private final Runnable refresher;
	private final ToolItem removeItem;
	private final ToolItem upItem;
	private final ToolItem downItem;
	private final ToolItem translateItem;
	private final IObservableList<EObject> elements;

	LanguageStringList(FormSupport form, Composite parent, ModelPath ownerPath, EReference reference,
			List<String> suggestions, boolean keywordAttributes, String title) {
		this.form = form;
		this.ownerPath = ownerPath;
		this.reference = reference;
		this.title = title;

		composite = form.toolkit().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(4, 4).applyTo(composite);

		input = new Combo(composite, SWT.DROP_DOWN);
		form.toolkit().adapt(input, true, false);
		if (suggestions != null) {
			input.setItems(suggestions.toArray(String[]::new));
			input.setVisibleItemCount(20);
		}
		input.setToolTipText(Messages.LanguageStringList_TypeOrChooseAValue);
		GridDataFactory.fillDefaults().grab(true, false).hint(120, SWT.DEFAULT).align(SWT.FILL, SWT.CENTER)
				.applyTo(input);
		input.addListener(SWT.DefaultSelection, _ -> addValue());

		ToolBar bar = FormWidgets.toolBar(form.toolkit(), composite);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(bar);
		FormWidgets.toolItem(bar, "add", Messages.LanguageStringList_Add, Messages.LanguageStringList_AddTheTypedValueEnter, this::addValue);
		if (keywordAttributes) {
			FormWidgets.toolItem(bar, "lookup", Messages.LanguageStringList_LookUp,
					Messages.LanguageStringList_AddKeywordsCodedWithMeSH, this::lookUpKeywords);
		}
		removeItem = FormWidgets.toolItem(bar, "remove", Messages.LanguageStringList_Remove, Messages.LanguageStringList_RemoveTheSelectedEntry, () -> {
			EObject selected = selected();
			if (selected != null) {
				ModelCommands.execute(domain(), ModelCommands.remove(domain(), selected));
			}
		});
		upItem = FormWidgets.toolItem(bar, "up", Messages.LanguageStringList_Up, Messages.LanguageStringList_MoveTheSelectedEntryUp, () -> move(-1));
		downItem = FormWidgets.toolItem(bar, "down", Messages.LanguageStringList_Down, Messages.LanguageStringList_MoveTheSelectedEntryDown, () -> move(1));
		translateItem = FormWidgets.toolItem(bar, "translate", Messages.LanguageStringList_Translations,
				Messages.LanguageStringList_EditTheSelectedEntryIn, this::editTranslations);

		// The table fills its holder; the text column takes the width the others leave (no horizontal scrolling).
		Composite holder = form.toolkit().createComposite(composite);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(150, SWT.DEFAULT).applyTo(holder);
		holder.setLayout(new FillLayout());
		Table table = form.toolkit().createTable(holder, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		FormWidgets.styleTable(table);
		table.setHeaderVisible(keywordAttributes);
		FormWidgets.emptyMessage(table, Messages.LanguageStringList_NoEntriesYet);
		viewer = new TableViewer(table);
		ColumnViewerToolTipSupport.enableFor(viewer);

		ModelPath stringPath = FormSupport.firstString(ModelPath.self());
		LomPackage lom = LomPackage.eINSTANCE;
		column(Messages.LanguageStringList_Text, row -> str(ModelCommands.getValue(row, stringPath,
				lom.getLangString_Value())), new TextCellEditor(table), stringPath, lom.getLangString_Value(), true);
		TableViewerColumn language = column(Messages.LanguageStringList_Language, row -> str(ModelCommands.getValue(row, stringPath,
				lom.getLangString_Language())), comboEditor(table, HlomSuggestions.LANGUAGES), stringPath,
				lom.getLangString_Language(), false);
		language.getColumn().setWidth(70);
		if (keywordAttributes) {
			TableViewerColumn source = column(Messages.LanguageStringList_Source, row -> str(ModelCommands.getValue(row, ModelPath.self(),
					lom.getKeyword_Source())), comboEditor(table, HlomSuggestions.KEYWORD_SOURCES), ModelPath.self(),
					lom.getKeyword_Source(), false);
			source.getColumn().setWidth(80);
			TableViewerColumn id = column("ID", row -> str(ModelCommands.getValue(row, ModelPath.self(),
					lom.getKeyword_Id())), new TextCellEditor(table), ModelPath.self(), lom.getKeyword_Id(), false);
			id.getColumn().setWidth(80);
		}
		FormWidgets.fillColumn(table, 0);

		ObservableListContentProvider<EObject> contentProvider = new ObservableListContentProvider<>();
		viewer.setContentProvider(contentProvider);
		elements = form.context().list(ownerPath, reference).observeDetail(form.anchor());
		viewer.setInput(elements);
		// Labels depend on nested strings, so refresh them on any model change.
		refresher = () -> {
			if (!table.isDisposed()) {
				viewer.refresh(true);
				updateActions();
			}
		};
		form.context().broadcaster().addListener(refresher);
		composite.addDisposeListener(_ -> form.context().broadcaster().removeListener(refresher));
		elements.addListChangeListener(_ -> resize());
		viewer.addSelectionChangedListener(_ -> updateActions());
		form.bindEnabled(composite);
		fitRows();
		updateActions();
	}

	public Control control() {
		return composite;
	}

	private EditingDomain domain() {
		return form.context().domain();
	}

	private static String str(Object value) {
		return value == null ? "" : value.toString();
	}

	private static CellEditor comboEditor(Table table, List<String> items) {
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(table, SWT.DROP_DOWN);
		editor.setContentProvider(ArrayContentProvider.getInstance());
		editor.setLabelProvider(new LabelProvider());
		editor.setInput(items);
		return editor;
	}

	private static int translations(EObject row) {
		return row instanceof LanguageString string ? string.getString().size() - 1 : 0;
	}

	private TableViewerColumn column(String name, Function<EObject, String> text, CellEditor cellEditor,
			ModelPath path, EAttribute attribute, boolean main) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(name);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String value = text.apply((EObject) element);
				int more = main ? translations((EObject) element) : 0;
				return more > 0 ? value + "   +" + more : value;
			}

			@Override
			public Image getImage(Object element) {
				return main && translations((EObject) element) > 0 ? FormWidgets.icon("translate") : null;
			}

			@Override
			public Color getForeground(Object element) {
				return main ? null : HlomTheme.color(HlomTheme.SLATE);
			}

			@Override
			public String getToolTipText(Object element) {
				int more = main ? translations((EObject) element) : 0;
				return more > 0 ? "Also available in " + more + " more language" + (more == 1 ? "" : "s")
						+ " (edit them with the translate button)" : null;
			}
		});
		if (cellEditor != null) {
			column.setEditingSupport(new EditingSupport(viewer) {
				@Override
				protected CellEditor getCellEditor(Object element) {
					return cellEditor;
				}

				@Override
				protected boolean canEdit(Object element) {
					return true;
				}

				@Override
				protected Object getValue(Object element) {
					return text.apply((EObject) element);
				}

				@Override
				protected void setValue(Object element, Object value) {
					// Editable combo cell editors deliver the typed text when nothing was picked.
					if (value == null && cellEditor instanceof ComboBoxViewerCellEditor combo) {
						value = ((org.eclipse.swt.custom.CCombo) combo.getControl()).getText();
					}
					String newValue = value == null ? "" : value.toString().trim();
					ModelCommands.execute(domain(), ModelCommands.setValue(domain(), (EObject) element, path,
							attribute, newValue.isEmpty() ? null : newValue));
				}
			});
		}
		return column;
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
		translateItem.setEnabled(selected != null);
		upItem.setEnabled(index > 0);
		downItem.setEnabled(index >= 0 && index < elements.size() - 1);
	}

	private void fitRows() {
		FormWidgets.fitRows(viewer.getTable(), viewer.getTable().getParent(), MIN_ROWS, MAX_ROWS);
	}

	/** Adapts the table height to the number of rows and grows or shrinks the page with it. */
	private void resize() {
		if (!viewer.getControl().isDisposed()) {
			fitRows();
			FormWidgets.reflow(composite);
			updateActions();
		}
	}

	private void editTranslations() {
		EObject selected = selected();
		if (selected != null) {
			new LanguageStringDialog(composite.getShell(), domain(), selected, ModelPath.self(), title).open();
		}
	}

	private void move(int delta) {
		EObject selected = selected();
		if (selected != null) {
			ModelCommands.execute(domain(), ModelCommands.move(domain(), selected, delta));
		}
	}

	/** Adds keywords chosen in the terminology lookup, with their source and code. */
	private void lookUpKeywords() {
		EObject anchor = form.anchor().getValue();
		if (anchor == null) {
			return;
		}
		TerminologyLookupDialog dialog = TerminologySupport.lookupKeywords(composite.getShell());
		if (dialog.getResult().isEmpty()) {
			return;
		}
		ModelCommands.execute(domain(), TerminologySupport.addKeywords(domain(), anchor, ownerPath, reference,
				dialog.getResultTerminology(), dialog.getResult(), HlomDocuments.DEFAULT_LANGUAGE));
	}

	private void addValue() {
		EObject anchor = form.anchor().getValue();
		String text = input.getText().trim();
		if (anchor == null || text.isEmpty()) {
			return;
		}
		LanguageString element = (LanguageString) EcoreUtil.create(reference.getEReferenceType());
		element.getString().add(HlomDocuments.langString(text, HlomDocuments.DEFAULT_LANGUAGE));
		if (ModelCommands.execute(domain(), ModelCommands.addElement(domain(), anchor, ownerPath, reference, element))) {
			input.setText("");
			input.setFocus();
		}
	}
}
