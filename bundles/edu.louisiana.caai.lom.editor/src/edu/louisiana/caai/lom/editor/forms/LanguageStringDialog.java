/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * Edits all language variants of a LanguageString element. Changes are applied as one undoable command
 * when the dialog is closed with OK.
 */
public final class LanguageStringDialog extends TitleAreaDialog {

	/** A working copy of one string. */
	private static final class Entry {
		String language;
		String text;

		Entry(String language, String text) {
			this.language = language;
			this.text = text;
		}
	}

	private final EditingDomain domain;
	private final EObject anchor;
	private final ModelPath languageStringPath;
	private final String title;
	private final List<Entry> entries = new ArrayList<>();
	private TableViewer viewer;

	public LanguageStringDialog(Shell parentShell, EditingDomain domain, EObject anchor, ModelPath languageStringPath,
			String title) {
		super(parentShell);
		this.domain = domain;
		this.anchor = anchor;
		this.languageStringPath = languageStringPath;
		this.title = title;
		EObject existing = languageStringPath.resolve(anchor);
		if (existing instanceof LanguageString languageString) {
			for (LangString string : languageString.getString()) {
				entries.add(new Entry(string.getLanguage(), string.getValue()));
			}
		}
		if (entries.isEmpty()) {
			entries.add(new Entry(HlomDocuments.DEFAULT_LANGUAGE, ""));
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.LanguageStringDialog_Translations);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		HelpContexts.set(parent, HelpContexts.TRANSLATIONS_DIALOG);
		setTitle(title);
		setMessage(Messages.LanguageStringDialog_EachEntryShouldSayThe);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite composite = new Composite(area, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.swtDefaults().spacing(0, 4).applyTo(composite);

		ToolBar bar = new ToolBar(composite, SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);
		FormWidgets.toolItem(bar, "add", Messages.LanguageStringDialog_Add, Messages.LanguageStringDialog_AddALanguage, () -> {
			Entry entry = new Entry("", "");
			entries.add(entry);
			viewer.refresh();
			viewer.editElement(entry, 0);
		});
		FormWidgets.toolItem(bar, "remove", Messages.LanguageStringDialog_Remove, Messages.LanguageStringDialog_RemoveTheSelectedLanguage, () -> {
			Object selected = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (selected != null) {
				entries.remove(selected);
				viewer.refresh();
			}
		});

		Table table = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setHeaderVisible(true);
		FormWidgets.styleTable(table);
		GridDataFactory.fillDefaults().grab(true, true).hint(520, 200).applyTo(table);
		viewer = new TableViewer(table);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		ComboBoxViewerCellEditor languageEditor = new ComboBoxViewerCellEditor(table, SWT.DROP_DOWN);
		languageEditor.setContentProvider(ArrayContentProvider.getInstance());
		languageEditor.setLabelProvider(new LabelProvider());
		languageEditor.setInput(HlomSuggestions.LANGUAGES);
		column(Messages.LanguageStringDialog_Language, 90, true, languageEditor);
		column(Messages.LanguageStringDialog_Text, 420, false, new TextCellEditor(table, SWT.MULTI | SWT.WRAP));
		viewer.setInput(entries);
		return area;
	}

	private void column(String name, int width, boolean language, CellEditor cellEditor) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(name);
		column.getColumn().setWidth(width);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Entry entry = (Entry) element;
				String value = language ? entry.language : entry.text;
				return value == null ? "" : value;
			}
		});
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
				Entry entry = (Entry) element;
				String value = language ? entry.language : entry.text;
				return value == null ? "" : value;
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (value == null && cellEditor instanceof ComboBoxViewerCellEditor combo) {
					value = ((CCombo) combo.getControl()).getText();
				}
				Entry entry = (Entry) element;
				if (language) {
					entry.language = value == null ? "" : value.toString().trim();
				} else {
					entry.text = value == null ? "" : value.toString();
				}
				viewer.update(element, null);
			}
		});
	}

	@Override
	protected void okPressed() {
		applyChanges();
		super.okPressed();
	}

	private void applyChanges() {
		List<LangString> strings = new ArrayList<>();
		for (Entry entry : entries) {
			if (entry.text != null && !entry.text.isBlank()) {
				strings.add(HlomDocuments.langString(entry.text,
						entry.language == null || entry.language.isBlank() ? null : entry.language));
			}
		}
		EObject existing = languageStringPath.resolve(anchor);
		CompoundCommand command = new CompoundCommand(Messages.LanguageStringDialog_EditTranslations);
		if (existing instanceof LanguageString languageString) {
			if (!languageString.getString().isEmpty()) {
				command.append(new RemoveCommand(domain, languageString, LomPackage.eINSTANCE.getLanguageString_String(),
						new ArrayList<>(languageString.getString())));
			}
			if (!strings.isEmpty()) {
				command.append(new AddCommand(domain, languageString, LomPackage.eINSTANCE.getLanguageString_String(),
						strings));
			} else if (!languageStringPath.isEmpty()) {
				// No text left: remove the element itself.
				command = new CompoundCommand(Messages.LanguageStringDialog_EditTranslations);
				command.append(ModelCommands.remove(domain, languageString));
			}
		} else if (!strings.isEmpty()) {
			ModelPath.Ensured ensured = languageStringPath.ensure(domain, anchor);
			((LanguageString) ensured.target()).getString().addAll(strings);
			command.append(ensured.command());
		}
		if (!command.isEmpty()) {
			ModelCommands.execute(domain, command);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}
