/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.IReplacePage;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;

/**
 * The page of the Search dialog that searches the field values of the documents of Hermes projects: text or a
 * regular expression, optionally limited to field groups such as titles or keywords.
 */
public final class HermesSearchPage extends DialogPage implements ISearchPage, IReplacePage {

	/** The id of the page (see plugin.xml). */
	public static final String ID = "edu.louisiana.caai.lom.editor.search.page";

	private static final String SETTINGS = "HermesSearchPage";
	private static final String HISTORY = "history";
	private static final int HISTORY_SIZE = 12;

	private ISearchPageContainer container;
	private Combo text;
	private Button caseSensitive;
	private Button regex;
	private Button wholeWord;
	private Button allFields;
	private final Map<FieldGroup, Button> fieldButtons = new LinkedHashMap<>();
	private Label error;

	@Override
	public void setContainer(ISearchPageContainer container) {
		this.container = container;
	}

	@Override
	public void createControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.SEARCH_PAGE);
		Composite page = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(page);

		Label label = new Label(page, SWT.NONE);
		label.setText(Messages.HermesSearchPage_ContainingTextInTheField);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(label);
		text = new Combo(page, SWT.DROP_DOWN | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).hint(380, SWT.DEFAULT).applyTo(text);
		text.setItems(history());
		text.setText(initialText());
		text.addModifyListener(_ -> validate());

		Composite options = new Composite(page, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(options);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).span(1, 2).applyTo(options);
		caseSensitive = check(options, Messages.HermesSearchPage_CaseSensitive);
		wholeWord = check(options, Messages.HermesSearchPage_WholeWord);
		regex = check(options, Messages.HermesSearchPage_RegularEXpression);
		regex.addListener(SWT.Selection, _ -> validate());

		error = new Label(page, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(error);

		Group fields = new Group(page, SWT.NONE);
		fields.setText(Messages.HermesSearchPage_SearchIn);
		GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(false).applyTo(fields);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(fields);
		allFields = new Button(fields, SWT.CHECK);
		allFields.setText(Messages.HermesSearchPage_AllFields);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(allFields);
		allFields.addListener(SWT.Selection, _ -> {
			fieldButtons.values().forEach(button -> button.setEnabled(!allFields.getSelection()));
			validate();
		});
		for (DocumentType type : DocumentTypes.all()) {
			List<FieldGroup> groups = type.searchFields().stream().filter(g -> !fieldButtons.containsKey(g)).toList();
			if (groups.isEmpty()) {
				continue;
			}
			Label standard = new Label(fields, SWT.NONE);
			standard.setText(type.standard());
			standard.setFont(edu.louisiana.caai.lom.editor.HlomTheme.boldFont());
			GridDataFactory.fillDefaults().span(3, 1).indent(0, fieldButtons.isEmpty() ? 2 : 8).applyTo(standard);
			for (FieldGroup group : groups) {
				Button button = check(fields, group.label());
				button.addListener(SWT.Selection, _ -> validate());
				fieldButtons.put(group, button);
			}
			int rest = groups.size() % 3;
			if (rest != 0) {
				GridDataFactory.fillDefaults().span(4 - rest, 1).applyTo(fieldButtons.get(groups.getLast()));
			}
		}

		restore();
		setControl(page);
		validate();
	}

	private static Button check(Composite parent, String label) {
		Button button = new Button(parent, SWT.CHECK);
		button.setText(label);
		return button;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			text.setFocus();
			validate();
		}
	}

	/** The selected text of the active editor's focus, if any, else the last search. */
	private String initialText() {
		ISelection selection = container == null ? null : container.getSelection();
		if (selection instanceof org.eclipse.jface.text.ITextSelection textSelection) {
			String selected = textSelection.getText();
			if (selected != null && !selected.isBlank() && selected.indexOf('\n') < 0) {
				return selected.strip();
			}
		}
		String[] history = history();
		return history.length > 0 ? history[0] : "";
	}

	private void validate() {
		String message = null;
		boolean selectedFields = allFields.getSelection()
				|| fieldButtons.values().stream().anyMatch(Button::getSelection);
		if (text.getText().isEmpty()) {
			message = "";
		} else if (regex.getSelection()) {
			try {
				java.util.regex.Pattern.compile(text.getText());
			} catch (PatternSyntaxException e) {
				message = NLS.bind(Messages.HermesSearchPage_InvalidRegularExpression, e.getDescription());
			}
		}
		if (message == null && !selectedFields) {
			message = Messages.HermesSearchPage_ChooseTheFieldsToSearch;
		}
		error.setText(message == null ? "" : message);
		error.getParent().layout(true);
		if (container != null) {
			container.setPerformActionEnabled(message == null);
		}
	}

	@Override
	public boolean performAction() {
		NewSearchUI.runQueryInBackground(createQuery());
		return true;
	}

	/** Replace...: searches now, then asks for the replacement and previews the changes. */
	@Override
	public boolean performReplace() {
		HermesSearchQuery query = createQuery();
		if (!NewSearchUI.runQueryInForeground(container.getRunnableContext(), query).isOK()) {
			return false;
		}
		NewSearchUI.activateSearchResultView();
		HermesSearchResult result = (HermesSearchResult) query.getSearchResult();
		if (result.getMatchCount() == 0) {
			org.eclipse.jface.dialogs.MessageDialog.openInformation(getShell(), Messages.HermesSearchPage_Replace, NLS.bind(Messages.HermesSearchPage_NoMatchesOfWereFound, query.query().describe()));
			return true;
		}
		getShell().getDisplay().asyncExec(() -> ReplaceWizard.open(
				org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), result, List.of()));
		return true;
	}

	private HermesSearchQuery createQuery() {
		List<FieldGroup> groups = allFields.getSelection() ? List.of()
				: fieldButtons.entrySet().stream().filter(e -> e.getValue().getSelection()).map(Map.Entry::getKey)
						.toList();
		Query query = new Query(text.getText(), caseSensitive.getSelection(), regex.getSelection(),
				wholeWord.getSelection(), groups);
		Scope scope = scope();
		save();
		return new HermesSearchQuery(query, scope.resources(), scope.label());
	}

	private record Scope(List<IResource> resources, String label) {
	}

	/** The resources of the scope chosen in the dialog. */
	private Scope scope() {
		return switch (container.getSelectedScope()) {
			case ISearchPageContainer.SELECTION_SCOPE -> new Scope(selectedResources(), Messages.HermesSearchPage_SelectedResources);
			case ISearchPageContainer.SELECTED_PROJECTS_SCOPE -> {
				List<IResource> projects = new ArrayList<>(
						HermesSearchQuery.projectsNamed(container.getSelectedProjectNames()));
				yield new Scope(projects, projects.size() == 1 ? "project " + HermesWorkspace.name(projects.getFirst().getProject())
						: "selected projects");
			}
			case ISearchPageContainer.WORKING_SET_SCOPE -> {
				IWorkingSet[] sets = container.getSelectedWorkingSets();
				Set<IResource> resources = new LinkedHashSet<>();
				for (IWorkingSet set : sets == null ? new IWorkingSet[0] : sets) {
					for (var element : set.getElements()) {
						IResource resource = Adapters.adapt(element, IResource.class);
						if (resource != null) {
							resources.add(resource);
						}
					}
				}
				yield new Scope(List.copyOf(resources), NLS.bind(Messages.HermesSearchPage_WorkingSets, String.join(", ", Arrays.stream(sets == null ? new IWorkingSet[0] : sets).map(IWorkingSet::getLabel).toList())));
			}
			default -> new Scope(List.copyOf(HermesWorkspace.projects()), "workspace");
		};
	}

	/** The resources of the selection (or the document of the active editor). */
	private List<IResource> selectedResources() {
		Set<IResource> resources = new LinkedHashSet<>();
		if (container.getSelection() instanceof IStructuredSelection selection) {
			for (Object element : selection) {
				IResource resource = Adapters.adapt(element, IResource.class);
				if (resource != null) {
					resources.add(resource);
				}
			}
		}
		if (resources.isEmpty() && PlatformUI.isWorkbenchRunning()) {
			var window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IEditorPart editor = window == null || window.getActivePage() == null ? null
					: window.getActivePage().getActiveEditor();
			if (editor != null && editor.getEditorInput() instanceof IFileEditorInput input) {
				resources.add(input.getFile());
			}
		}
		return List.copyOf(resources);
	}

	private IDialogSettings settings() {
		IDialogSettings settings = HlomEditorPlugin.getPlugin().getDialogSettings();
		IDialogSettings section = settings.getSection(SETTINGS);
		return section != null ? section : settings.addNewSection(SETTINGS);
	}

	private String[] history() {
		String[] history = settings().getArray(HISTORY);
		return history == null ? new String[0] : history;
	}

	private void restore() {
		IDialogSettings settings = settings();
		caseSensitive.setSelection(settings.getBoolean("caseSensitive"));
		regex.setSelection(settings.getBoolean("regex"));
		wholeWord.setSelection(settings.getBoolean("wholeWord"));
		String[] fields = settings.getArray("fields");
		allFields.setSelection(fields == null || fields.length == 0);
		Set<String> selected = fields == null ? Set.of() : Set.of(fields);
		fieldButtons.forEach((group, button) -> {
			button.setSelection(selected.contains(group.id()));
			button.setEnabled(!allFields.getSelection());
		});
	}

	private void save() {
		IDialogSettings settings = settings();
		settings.put("caseSensitive", caseSensitive.getSelection());
		settings.put("regex", regex.getSelection());
		settings.put("wholeWord", wholeWord.getSelection());
		settings.put("fields", allFields.getSelection() ? new String[0]
				: fieldButtons.entrySet().stream().filter(e -> e.getValue().getSelection())
						.map(e -> e.getKey().id()).toArray(String[]::new));
		List<String> history = new ArrayList<>();
		history.add(text.getText());
		for (String entry : history()) {
			if (!history.contains(entry) && history.size() < HISTORY_SIZE) {
				history.add(entry);
			}
		}
		settings.put(HISTORY, history.toArray(String[]::new));
	}
}
