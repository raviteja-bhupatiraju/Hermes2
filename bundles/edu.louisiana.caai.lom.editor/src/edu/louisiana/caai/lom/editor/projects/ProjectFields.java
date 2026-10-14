/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;

/**
 * The fields of a project (name, location, description, format of new documents), shared by the New Project
 * wizard and the project's property page.
 */
final class ProjectFields {

	/** The state of the fields: an error (the fields can't be applied), a warning, or neither. */
	record Validation(String error, String warning) {
	}

	private final HermesProject existing;

	private Text nameText;
	private Text locationText;
	private Text descriptionText;
	private Button jsonButton;
	private Label folderLabel;

	/** @param existing the project to edit, or {@code null} for a new one */
	ProjectFields(HermesProject existing) {
		this.existing = existing;
	}

	/** Creates the fields in a parent; {@code onChange} runs after each change. */
	Composite create(Composite parent, Runnable onChange) {
		Composite body = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).spacing(10, 8).applyTo(body);

		new Label(body, SWT.NONE).setText(Messages.ProjectFields_Name);
		nameText = new Text(body, SWT.BORDER);
		nameText.setMessage(Messages.ProjectFields_EGEmergencyMedicineCME);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(380, SWT.DEFAULT).applyTo(nameText);

		if (existing == null) {
			new Label(body, SWT.NONE).setText(Messages.ProjectFields_Location);
			locationText = new Text(body, SWT.BORDER);
			locationText.setText(defaultLocation().getPath());
			GridDataFactory.fillDefaults().grab(true, false).applyTo(locationText);
			Button browse = new Button(body, SWT.PUSH);
			browse.setText(Messages.ProjectFields_Browse);
			browse.addListener(SWT.Selection, _ -> {
				DirectoryDialog dialog = new DirectoryDialog(body.getShell());
				dialog.setText(Messages.ProjectFields_ProjectLocation);
				dialog.setMessage(Messages.ProjectFields_ChooseTheFolderInWhich);
				dialog.setFilterPath(locationText.getText());
				String chosen = dialog.open();
				if (chosen != null) {
					locationText.setText(chosen);
				}
			});
			new Label(body, SWT.NONE);
			folderLabel = new Label(body, SWT.WRAP);
			folderLabel.setForeground(HlomTheme.color(HlomTheme.SLATE));
			GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(380, SWT.DEFAULT).applyTo(folderLabel);
			locationText.addModifyListener(_ -> onChange.run());
		}

		Label descriptionLabel = new Label(body, SWT.NONE);
		descriptionLabel.setText(Messages.ProjectFields_Description);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).indent(0, 3).applyTo(descriptionLabel);
		descriptionText = new Text(body, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).hint(380, 64).applyTo(descriptionText);

		new Label(body, SWT.NONE).setText(Messages.ProjectFields_NewDocuments);
		Composite formats = new Composite(body, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(16, 0).applyTo(formats);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(formats);
		Button xmlButton = new Button(formats, SWT.RADIO);
		xmlButton.setText(Messages.ProjectFields_XMLXml);
		jsonButton = new Button(formats, SWT.RADIO);
		jsonButton.setText(Messages.ProjectFields_JSONJson);

		boolean json = existing != null ? existing.getFormat() == Format.JSON : HlomPreferences.isJsonDefaultFormat();
		xmlButton.setSelection(!json);
		jsonButton.setSelection(json);
		if (existing != null) {
			nameText.setText(existing.getName());
			descriptionText.setText(existing.getDescription());
			new Label(body, SWT.NONE).setText(Messages.ProjectFields_Folder);
			Label path = new Label(body, SWT.WRAP);
			path.setText(existing.getFolder().toString());
			path.setForeground(HlomTheme.color(HlomTheme.SLATE));
			GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(380, SWT.DEFAULT).applyTo(path);
		}
		nameText.addModifyListener(_ -> onChange.run());
		nameText.setFocus();
		return body;
	}

	/** Restores the values of a new project's defaults or of the project being edited. */
	void restoreDefaults() {
		boolean json = existing != null ? existing.getFormat() == Format.JSON : HlomPreferences.isJsonDefaultFormat();
		jsonButton.setSelection(json);
		((Button) jsonButton.getParent().getChildren()[0]).setSelection(!json);
		if (existing != null) {
			nameText.setText(existing.getName());
			descriptionText.setText(existing.getDescription());
		}
	}

	Validation validate() {
		String error = null;
		String warning = null;
		String projectName = nameText.getText().strip();
		if (projectName.isEmpty()) {
			error = Messages.ProjectFields_EnterTheNameOfThe;
		} else if (locationText != null) {
			String location = locationText.getText().strip();
			String folderName = folderName(projectName);
			if (location.isEmpty()) {
				error = Messages.ProjectFields_EnterTheLocationOfThe;
			} else if (folderName.isEmpty()) {
				error = Messages.ProjectFields_TheNameCanTBe;
			} else {
				Path target = Path.of(location, folderName);
				folderLabel.setText(NLS.bind(Messages.ProjectFields_ProjectFolder, target));
				if (HermesProject.isProject(target)) {
					error = Messages.ProjectFields_ThisFolderAlreadyIsA;
				} else if (Files.isRegularFile(target)) {
					error = Messages.ProjectFields_AFileWithThisName;
				} else if (Files.isDirectory(target)) {
					warning = Messages.ProjectFields_TheFolderExistsItsMedBiquitous;
				}
			}
		}
		return new Validation(error, warning);
	}

	String name() {
		return nameText.getText().strip();
	}

	/** The project folder (new projects only). */
	Path folder() {
		return locationText == null ? null : Path.of(locationText.getText().strip(), folderName(name()));
	}

	String description() {
		return descriptionText.getText().strip();
	}

	Format format() {
		return jsonButton.getSelection() ? Format.JSON : Format.XML;
	}

	/** The parent folder of an open project, else {@code Documents/Hermes Projects}. */
	private static File defaultLocation() {
		for (IProject project : HermesWorkspace.projects()) {
			Path location = HermesWorkspace.location(project);
			if (location != null && location.getParent() != null && location.getParent().toFile().isDirectory()) {
				return location.getParent().toFile();
			}
		}
		File home = new File(System.getProperty("user.home"));
		File documents = new File(home, Messages.ProjectFields_Documents);
		return new File(documents.isDirectory() ? documents : home, Messages.ProjectFields_HermesProjects);
	}

	/** A folder name derived from the project name. */
	static String folderName(String projectName) {
		return projectName.strip().replaceAll("[\\\\/:*?\"<>|]+", "-").replaceAll("\\s+", " ").replaceAll("^[.\\s]+|[.\\s]+$",
				"");
	}
}
