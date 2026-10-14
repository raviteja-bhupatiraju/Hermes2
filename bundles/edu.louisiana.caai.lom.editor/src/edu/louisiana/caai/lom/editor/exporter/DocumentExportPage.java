/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.exporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

import edu.louisiana.caai.lom.editor.workspace.HermesBuilder;

/**
 * The page of the export wizards: the workbench's resource selection (projects, folders and documents, with the
 * file type filter), a destination folder and the options of the export.
 */
abstract class DocumentExportPage extends WizardExportResourcesPage {

	private static final String DESTINATION = "destination";

	private Text destinationText;

	DocumentExportPage(String name, String title, String description, IStructuredSelection selection) {
		super(name, selection);
		setTitle(title);
		setDescription(description);
	}

	@Override
	protected void createDestinationGroup(Composite parent) {
		Composite group = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).margins(0, 6).applyTo(group);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		new Label(group, SWT.NONE).setText(Messages.DocumentExportPage_ToFolder);
		destinationText = new Text(group, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(destinationText);
		String previous = getDialogSettings() == null ? null : getDialogSettings().get(DESTINATION);
		destinationText.setText(previous != null ? previous
				: Path.of(System.getProperty("user.home"), Messages.DocumentExportPage_Documents, Messages.DocumentExportPage_HermesExport).toString());
		destinationText.addModifyListener(_ -> updatePageCompletion());
		Button browse = new Button(group, SWT.PUSH);
		browse.setText(Messages.DocumentExportPage_BRowse);
		browse.addListener(SWT.Selection, _ -> {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setText(getTitle());
			dialog.setFilterPath(destinationText.getText());
			String chosen = dialog.open();
			if (chosen != null) {
				destinationText.setText(chosen);
			}
		});
	}

	@Override
	protected void createOptionsGroupButtons(Group group) {
		createOptions(group);
	}

	/** Creates the options of the export (in a grid with one column). */
	protected abstract void createOptions(Composite group);

	@Override
	protected boolean validateDestinationGroup() {
		String text = destinationText == null ? "" : destinationText.getText().strip();
		if (text.isEmpty()) {
			setMessage(Messages.DocumentExportPage_ChooseTheFolderToExport);
			return false;
		}
		Path path = Path.of(text);
		if (Files.isRegularFile(path)) {
			setErrorMessage(Messages.DocumentExportPage_TheDestinationIsAFile);
			return false;
		}
		return true;
	}

	@Override
	protected boolean validateSourceGroup() {
		if (documents().isEmpty()) {
			setMessage(Messages.DocumentExportPage_SelectTheProjectsFoldersOr);
			return false;
		}
		return true;
	}

	@Override
	public void handleEvent(Event event) {
		updatePageCompletion();
	}

	/** The destination folder. */
	Path destination() {
		return Path.of(destinationText.getText().strip());
	}

	/** Remembers the destination for the next export. */
	void saveSettings() {
		if (getDialogSettings() != null) {
			getDialogSettings().put(DESTINATION, destinationText.getText().strip());
		}
	}

	/** The selected MedBiquitous documents. */
	List<IFile> documents() {
		List<IFile> documents = new ArrayList<>();
		for (Object selected : getWhiteCheckedResources()) {
			if (selected instanceof IFile file && file.getType() == IResource.FILE && HermesBuilder.isDocument(file)) {
				documents.add(file);
			}
		}
		return documents;
	}
}
