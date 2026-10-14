/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;

/**
 * The Hermes page of a project's properties (<em>Properties</em>, Alt+Enter): the name, description and format of
 * new documents, kept in the project file.
 */
public final class ProjectPropertyPage extends PropertyPage {

	/** The id of the page. */
	public static final String ID = "edu.louisiana.caai.lom.editor.projects.propertyPage";

	private HermesProject metadata;
	private ProjectFields fields;

	@Override
	protected Control createContents(Composite parent) {
		IProject project = Adapters.adapt(getElement(), IProject.class);
		metadata = project == null ? null : HermesWorkspace.metadata(project);
		if (metadata == null) {
			Label label = new Label(parent, SWT.WRAP);
			label.setText(Messages.ProjectPropertyPage_ThisProjectHasNoProject);
			noDefaultAndApplyButton();
			return label;
		}
		fields = new ProjectFields(metadata);
		Control body = fields.create(parent, this::validate);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);
		HelpContexts.set(body, HelpContexts.PROJECT_DIALOG);
		return body;
	}

	private void validate() {
		ProjectFields.Validation validation = fields.validate();
		setErrorMessage(validation.error());
		setMessage(validation.warning(), validation.warning() == null ? IMessageProvider.NONE : IMessageProvider.WARNING);
		setValid(validation.error() == null);
	}

	@Override
	protected void performDefaults() {
		if (fields != null) {
			fields.restoreDefaults();
		}
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		if (fields == null) {
			return true;
		}
		metadata.setName(fields.name());
		metadata.setDescription(fields.description());
		metadata.setFormat(fields.format());
		IProject project = Adapters.adapt(getElement(), IProject.class);
		try {
			metadata.save();
			project.getFile(HermesProject.PROJECT_FILE).refreshLocal(IResource.DEPTH_ZERO, null);
			return true;
		} catch (IOException | CoreException e) {
			Errors.show(getShell(), Messages.ProjectPropertyPage_ProjectProperties, Messages.ProjectPropertyPage_TheProjectFileCouldNot, e);
			return false;
		}
	}
}
