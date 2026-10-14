/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.projects;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ISetSelectionTarget;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;

/**
 * File &gt; New &gt; Project...: creates a project folder with its project file, adds it to the workspace and
 * selects it in the Projects view.
 */
public final class NewProjectWizard extends Wizard implements INewWizard {

	private static final String MESSAGE = "A project is a folder of MedBiquitous documents (XML and JSON) that you "
			+ "manage together.";

	private IWorkbench workbench;
	private ProjectFields fields;

	public NewProjectWizard() {
		setWindowTitle(Messages.NewProjectWizard_NewProject);
		setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(NewProjectWizard.class.getResource("/icons/app/hlom64.png")));
	}

	/** Opens the wizard in a window. */
	public static void open(IWorkbenchWindow window) {
		NewProjectWizard wizard = new NewProjectWizard();
		wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		new WizardDialog(window.getShell(), wizard).open();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
	}

	@Override
	public void addPages() {
		fields = new ProjectFields(null);
		addPage(new WizardPage("project", Messages.NewProjectWizard_NewProject, null) {
			{
				setMessage(MESSAGE);
			}

			@Override
			public void createControl(Composite parent) {
				Control body = fields.create(parent, this::validate);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(body);
				HelpContexts.set(body, HelpContexts.PROJECT_DIALOG);
				setControl(body);
				validate();
				setErrorMessage(null);
			}

			private void validate() {
				ProjectFields.Validation validation = fields.validate();
				setErrorMessage(validation.error());
				if (validation.warning() != null) {
					setMessage(validation.warning(), IMessageProvider.WARNING);
				} else {
					setMessage(MESSAGE);
				}
				setPageComplete(validation.error() == null);
			}
		});
	}

	@Override
	public boolean performFinish() {
		try {
			HermesProject created = HermesProject.create(fields.folder(), fields.name(), fields.description(),
					fields.format());
			IProject project = HermesWorkspace.open(created.getFolder(), null);
			reveal(project);
			return true;
		} catch (IOException | CoreException e) {
			Errors.show(getShell(), Messages.NewProjectWizard_NewProject, Messages.NewProjectWizard_TheProjectCouldNotBe, e);
			return false;
		}
	}

	private void reveal(IProject project) {
		IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null) {
			return;
		}
		try {
			if (page.showView(ProjectsView.ID) instanceof ISetSelectionTarget target) {
				target.selectReveal(new StructuredSelection(project));
			}
		} catch (PartInitException e) {
			// The project is created; only showing it failed.
		}
	}
}
