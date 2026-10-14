/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.wizards;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.editor.start.RecentDocuments;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import org.eclipse.jface.viewers.StructuredSelection;
import edu.louisiana.caai.lom.model.support.HlomResources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import org.eclipse.jface.wizard.WizardDialog;

/**
 * Creates a new Healthcare LOM document from a {@link HlomTemplates template} in the file system, saves it and
 * opens it in the editor.
 */
public class NewHlomDocumentWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;

	private NewHlomDocumentPage page;

	private final String initialTemplateId;

	private File initialFolder;

	private Boolean initialJson;

	private MedbiqDocumentType initialType;

	private java.util.function.Consumer<File> onCreated;

	/** Creates the wizard with the blank record preselected. */
	public NewHlomDocumentWizard() {
		this(null);
	}

	/** Creates the wizard with a template preselected ({@code null} for the blank record). */
	public NewHlomDocumentWizard(String templateId) {
		this.initialTemplateId = templateId;
		setNeedsProgressMonitor(false);
		setWindowTitle(Messages.NewHlomDocumentWizard_NewMedBiquitousDocument);
		URL banner = NewHlomDocumentWizard.class.getResource("/icons/app/hlom64.png");
		setDefaultPageImageDescriptor(banner != null ? ImageDescriptor.createFromURL(banner)
				: ExtendedImageRegistry.INSTANCE.getImageDescriptor(HlomEditorPlugin.INSTANCE.getImage("full/wizban/NewLom")));
	}

	/** Preselects a standard other than Healthcare LOM. */
	public void setInitialType(MedbiqDocumentType type) {
		initialType = type;
	}

	/** Creates the document in a folder by default (e.g. the folder of a project). */
	public void setInitialFolder(File folder) {
		initialFolder = folder;
	}

	/** Preselects the format: {@code true} for JSON, {@code false} for XML. */
	public void setInitialFormat(boolean json) {
		initialJson = json;
	}

	/** Called with the new file after it was created (before it is opened). */
	public void setOnCreated(java.util.function.Consumer<File> listener) {
		onCreated = listener;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		// File > New > Other... with a project, folder or document selected: create the document there.
		if (initialFolder == null && selection != null
				&& Adapters.adapt(selection.getFirstElement(), IResource.class) instanceof IResource resource) {
			IContainer container = resource instanceof IContainer c ? c : resource.getParent();
			java.nio.file.Path location = container == null ? null : HermesWorkspace.location(container);
			if (location != null) {
				initialFolder = location.toFile();
				HermesProject metadata = HermesWorkspace.metadata(container.getProject());
				if (initialJson == null && metadata != null) {
					initialJson = metadata.getFormat() == HermesProject.Format.JSON;
				}
			}
		}
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		HelpContexts.set(pageContainer, HelpContexts.NEW_DOCUMENT_WIZARD);
	}

	@Override
	public void addPages() {
		page = new NewHlomDocumentPage(HlomTemplates.get(initialTemplateId));
		page.setInitialType(initialType);
		page.setInitialFolder(initialFolder);
		if (initialJson != null) {
			page.setInitialFormat(initialJson);
		}
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		File file = page.getFile();
		if (file.isDirectory()) {
			page.setErrorMessage(Messages.NewHlomDocumentWizard_TheLocationRefersToA);
			return false;
		}
		if (file.exists() && !MessageDialog.openQuestion(getShell(), Messages.NewHlomDocumentWizard_FileExists,
				NLS.bind(Messages.NewHlomDocumentWizard_TheFileAlreadyExistsDo, file.getPath()))) {
			return false;
		}
		File parent = file.getAbsoluteFile().getParentFile();
		if (parent != null && !parent.exists() && !parent.mkdirs()) {
			page.setErrorMessage(NLS.bind(Messages.NewHlomDocumentWizard_CannotCreateTheFolder, parent.getPath()));
			return false;
		}

		URI uri = URI.createFileURI(file.getAbsolutePath());
		MedbiqDocumentType other = page.getOtherType();
		if (other != null) {
			try {
				java.nio.file.Files.writeString(file.toPath(), other.newDocumentXml(page.getDocumentTitle(),
						page.getLanguage()), java.nio.charset.StandardCharsets.UTF_8);
				RecentDocuments.add(file.getAbsolutePath());
				if (onCreated != null) {
					onCreated.accept(file.getAbsoluteFile());
				}
			} catch (IOException | RuntimeException exception) {
				Errors.show(getShell(), NLS.bind(Messages.NewHlomDocumentWizard_NewDocument, other.standard()), Messages.NewHlomDocumentWizard_TheDocumentCouldNotBe,
						exception);
				return false;
			}
			if (workbench != null) {
				HlomEditorAdvisor.openEditor(workbench, uri);
			}
			return true;
		}
		try {
			DocumentRoot root = page.getTemplate().create(page.getDocumentTitle(), page.getLanguage());
			String author = HlomPreferences.getNewDocumentContributor();
			if (author != null) {
				HlomTemplates.addAuthor(root, author, java.time.LocalDate.now().toString());
			}
			Resource resource = HlomResources.createResourceSet().createResource(uri);
			resource.getContents().add(root);
			resource.save(HlomResources.saveOptions());
			RecentDocuments.add(file.getAbsolutePath());
			if (onCreated != null) {
				onCreated.accept(file.getAbsoluteFile());
			}
		} catch (IOException | RuntimeException exception) {
			Errors.show(getShell(), Messages.NewHlomDocumentWizard_NewMedBiquitousDocument, Messages.NewHlomDocumentWizard_TheDocumentCouldNotBe, exception);
			return false;
		}

		if (workbench != null) {
			HlomEditorAdvisor.openEditor(workbench, uri);
		}
		return true;
	}

	/** Opens the new-document wizard in a dialog for the given window. */
	public static void open(IWorkbenchWindow window) {
		open(window, (String) null);
	}

	/**
	 * Opens the new-document wizard with a template preselected.
	 *
	 * @param templateId id of a {@link edu.louisiana.caai.lom.model.support.HlomTemplates} template, or
	 *                   {@code null} for the blank record
	 */
	public static void open(IWorkbenchWindow window, String templateId) {
		NewHlomDocumentWizard wizard = new NewHlomDocumentWizard(templateId);
		wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.open();
	}

	/** Opens the new-document wizard with a standard other than Healthcare LOM preselected. */
	public static void open(IWorkbenchWindow window, edu.louisiana.caai.medbiq.model.MedbiqDocumentType type) {
		NewHlomDocumentWizard wizard = new NewHlomDocumentWizard(null);
		wizard.setInitialType(type);
		wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.open();
	}
}
