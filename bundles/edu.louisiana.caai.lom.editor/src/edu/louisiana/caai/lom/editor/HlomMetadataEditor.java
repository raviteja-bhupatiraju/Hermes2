/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.opal.notifier.Notifier;
import org.eclipse.nebula.widgets.opal.notifier.NotifierColorsFactory.NotifierTheme;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.ide.IGotoMarker;

import edu.louisiana.caai.lom.editor.binding.ModelChangeBroadcaster;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.editor.json.JsonSourceEditor;
import edu.louisiana.caai.lom.editor.binding.ModelContext;
import edu.louisiana.caai.lom.editor.pages.AbstractLomPage;
import edu.louisiana.caai.lom.editor.pages.EditorServices;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;
import edu.louisiana.caai.lom.editor.quickfix.QuickFixes;
import edu.louisiana.caai.lom.editor.pages.AnnotationPage;
import edu.louisiana.caai.lom.editor.pages.ClassificationPage;
import edu.louisiana.caai.lom.editor.pages.EducationalPage;
import edu.louisiana.caai.lom.editor.pages.GeneralPage;
import edu.louisiana.caai.lom.editor.pages.HealthcareAssetPage;
import edu.louisiana.caai.lom.editor.pages.HealthcareEducationPage;
import edu.louisiana.caai.lom.editor.pages.LifeCyclePage;
import edu.louisiana.caai.lom.editor.pages.MetaMetadataPage;
import edu.louisiana.caai.lom.editor.pages.OverviewPage;
import edu.louisiana.caai.lom.editor.pages.RelationPage;
import edu.louisiana.caai.lom.editor.pages.RightsPage;
import edu.louisiana.caai.lom.editor.pages.TechnicalPage;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.OpenDocuments;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.editor.xml.XmlSourceEditor;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.presentation.LomEditor;
import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.forms.MedbiqPages;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.lom.editor.pages.DocumentOverviewPage;
import edu.louisiana.caai.lom.editor.pages.SpecPage;
import edu.louisiana.caai.lom.model.support.json.HlomJson;

/**
 * The Healthcare LOM editor: data-bound form pages for each LOM category and the healthcare extensions, a
 * tree of the complete document, and the XML and JSON source, all editing the same EMF model through one
 * command stack (undo/redo, dirty state, outline and property sheet stay in sync). Documents are stored as XML
 * or, for files with the extension {@code .json}, in the JSON form.
 * <p>
 * Extends the EMF-generated {@link LomEditor}, reusing its editing domain, outline, property sheet, save and
 * problem handling.
 */
public class HlomMetadataEditor extends LomEditor implements ValidationSource, EditorServices, IGotoMarker {

	/** Editor id as registered in plugin.xml. */
	public static final String ID = "edu.louisiana.caai.lom.editor.HlomMetadataEditor";

	private FormToolkit toolkit;
	private NavigationPanel navigation;
	private ModelChangeBroadcaster broadcaster;
	private final WritableValue<EObject> lomAnchor = new WritableValue<>(null, EObject.class);
	private final List<AbstractLomPage> formPages = new ArrayList<>();
	private ViewerPane treePane;
	private int treePageIndex = -1;
	private XmlSourceEditor xmlEditor;
	private int xmlPageIndex = -1;
	/** The XML page holds text that hasn't been applied to the model (e.g. the original text of a broken file). */
	private boolean xmlPendingApply;
	private JsonSourceEditor jsonEditor;
	private int jsonPageIndex = -1;
	/** The JSON page holds text that hasn't been applied to the model (e.g. the original text of a broken file). */
	private boolean jsonPendingApply;
	/** The problems reported while loading the file; for a JSON file their lines are lines of the JSON text. */
	private List<HlomProblem> loadProblems = Collections.emptyList();
	private int currentPage = -1;
	private boolean changingPage;
	/** Set once the pages are shown; selections made while building them are not propagated. */
	private boolean pagesReady;

	private List<HlomProblem> problems = Collections.emptyList();
	/** Whether the last save failed: the editor stays dirty although the command stack was marked saved. */
	private boolean saveFailed;
	/** The state of the file when the editor last read or wrote it, or {@code null} if unknown. */
	private DiskState diskState;
	/** Whether the editor is writing its file (its own changes are not changes from outside). */
	private boolean saving;
	/** Whether a change of the file from outside is being handled. */
	private boolean checkingDisk;
	/** Checks the file after workspace changes of it (a Git checkout, another program with auto-refresh). */
	private final IResourceChangeListener diskListener = event -> {
		if (getEditorInput() instanceof IFileEditorInput input && event.getDelta() != null
				&& event.getDelta().findMember(input.getFile().getFullPath()) != null) {
			org.eclipse.swt.widgets.Display display = getSite().getShell().getDisplay();
			if (!display.isDisposed()) {
				display.asyncExec(this::checkDiskIfActive);
			}
		}
	};
	/** Checks the file when the user comes back to the application. */
	private final ShellListener shellListener = ShellListener.shellActivatedAdapter(_ -> checkDiskIfActive());
	/** The document from outside the Hermes projects whose problems are shown in the Problems view. */
	private Path openDocument;
	/** The type of the edited document (Healthcare LOM or another MedBiquitous standard). */
	private DocumentType documentType = HlomDocumentType.INSTANCE;
	private final List<Runnable> problemsListeners = new CopyOnWriteArrayList<>();
	private boolean validationScheduled;
	/** Counts the validations; the result of a background validation is shown only if no other one started since. */
	private long validationRun;

	/** The type of the edited document. */
	public DocumentType documentType() {
		return documentType;
	}

	/** The form pages, in tab order: those of the document type. */
	protected List<AbstractLomPage> createFormPages() {
		if (documentType instanceof MedbiqDocumentType type) {
			EClass rootClass = rootClassOf(type);
			List<AbstractLomPage> pages = new ArrayList<>();
			pages.add(new DocumentOverviewPage(type));
			for (PageSpec spec : MedbiqPages.of(type)) {
				pages.add(new SpecPage(type, spec, rootClass));
			}
			return pages;
		}
		// Grouped as shown in the navigation: description, healthcare extensions, record and technical data.
		return List.of(new OverviewPage(), new GeneralPage(), new LifeCyclePage(), new EducationalPage(), new RightsPage(),
				new ClassificationPage(), new RelationPage(), new AnnotationPage(), new HealthcareEducationPage(),
				new HealthcareAssetPage(), new MetaMetadataPage(), new TechnicalPage());
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	/** Places the page navigation to the left of the pages. */
	@Override
	protected Composite createPageContainer(Composite parent) {
		Composite shell = new Composite(parent, SWT.NONE);
		shell.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(0, 0).applyTo(shell);
		navigation = new NavigationPanel(shell, this::setActivePage, this::setFocus);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(navigation);
		Composite pages = new Composite(shell, SWT.NONE);
		pages.setLayout(new FillLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(pages);
		return pages;
	}

	@Override
	public void createPages() {
		createModel();
		Resource resource = mainResource();
		documentType = detectType(resource);

		broadcaster = new ModelChangeBroadcaster(getSite().getShell().getDisplay());
		broadcaster.observe(resource);
		broadcaster.addListener(this::modelChanged);
		updateAnchor();

		ModelContext context = new ModelContext(editingDomain, broadcaster);
		toolkit = new FormToolkit(getContainer().getDisplay());
		HlomTheme.configure(toolkit);
		for (AbstractLomPage page : createFormPages()) {
			page.init(this);
			Control control = page.createControl(getContainer(), toolkit, context, lomAnchor, this::formSelection);
			int index = addPage(control);
			Image icon = HlomTheme.image(page.iconPath());
			setPageText(index, page.getTitle());
			setPageImage(index, icon);
			formPages.add(page);
			navigation.add(new NavigationPanel.Item(index, page.getTitle(), page.getGroupLabel(), icon,
					page.isHealthcare(), page.getDescription()));
		}
		createTreePage();
		createXmlPage();
		if (documentType.supportsJson()) {
			createJsonPage();
		}
		showSource(resource);
		navigation.add(new NavigationPanel.Item(treePageIndex, Messages.HlomMetadataEditor_DocumentTree, Messages.HlomMetadataEditor_Source,
				HlomTheme.image("icons/pages/tree.png"), false, Messages.HlomMetadataEditor_AllElementsOfTheDocument));
		if (xmlPageIndex >= 0) {
			navigation.add(new NavigationPanel.Item(xmlPageIndex, "XML", Messages.HlomMetadataEditor_Source, HlomTheme.image("icons/pages/xml.png"),
					false, Messages.HlomMetadataEditor_TheXMLSourceOfThe));
		}
		if (jsonPageIndex >= 0) {
			navigation.add(new NavigationPanel.Item(jsonPageIndex, "JSON", Messages.HlomMetadataEditor_Source, HlomTheme.image("icons/pages/json.png"),
					false, Messages.HlomMetadataEditor_TheDocumentInTheJSON));
		}
		if (getContainer() instanceof CTabFolder tabs) {
			// The navigation sidebar replaces the tab row.
			HlomTheme.decorate(tabs);
			tabs.setTabHeight(0);
			tabs.setBorderVisible(false);
		}
		navigation.getParent().layout(true);

		editingDomain.getCommandStack().addCommandStackListener(event -> scheduleValidation());
		rememberDiskState();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(diskListener, IResourceChangeEvent.POST_CHANGE);
		getSite().getShell().addShellListener(shellListener);

		getSite().getShell().getDisplay().asyncExec(() -> {
			if (!getContainer().isDisposed()) {
				setActivePage(xmlPendingApply ? xmlPageIndex : jsonPendingApply ? jsonPageIndex : 0);
				pagesReady = true;
				updateProblemIndication();
				validateNow();
			}
		});

		java.io.File screenshots = ScreenshotSupport.folder();
		if (screenshots != null) {
			String[] names = new String[Math.max(xmlPageIndex, jsonPageIndex) + 1];
			for (int i = 0; i < names.length; i++) {
				names[i] = getPageText(i);
			}
			ScreenshotSupport.capture(getSite().getShell(), screenshots, names, this::setActivePage, this::isDirty);
		}
	}

	private void createTreePage() {
		treePane = new ViewerPane(getSite().getPage(), HlomMetadataEditor.this) {
			@Override
			public Viewer createViewer(Composite composite) {
				return new TreeViewer(new Tree(composite, SWT.MULTI));
			}

			@Override
			public void requestActivation() {
				super.requestActivation();
				setCurrentViewerPane(this);
			}
		};
		treePane.createControl(getContainer());
		selectionViewer = (TreeViewer) treePane.getViewer();
		selectionViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		selectionViewer.setUseHashlookup(true);
		selectionViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		selectionViewer.setInput(editingDomain.getResourceSet());
		if (mainResource() != null) {
			selectionViewer.expandToLevel(4);
			selectionViewer.setSelection(new StructuredSelection(mainResource()), true);
		}
		treePane.setTitle(editingDomain.getResourceSet());
		new AdapterFactoryTreeEditor(selectionViewer.getTree(), adapterFactory);
		createContextMenuFor(selectionViewer);
		treePageIndex = addPage(treePane.getControl());
		setPageText(treePageIndex, Messages.HlomMetadataEditor_Tree);
		HelpContexts.set(treePane.getControl(), HelpContexts.PAGE_TREE);
	}

	private void createXmlPage() {
		xmlEditor = new XmlSourceEditor();
		xmlEditor.setDocumentType(documentType);
		try {
			xmlPageIndex = addPage(xmlEditor, XmlSourceEditor.createInput(getEditorInput().getName()));
			setPageText(xmlPageIndex, "XML");
		} catch (PartInitException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			xmlEditor = null;
			return;
		}
		xmlEditor.addModificationListener(() -> firePropertyChange(IEditorPart.PROP_DIRTY));
		xmlEditor.addCaretListener(this::xmlCaretMoved);
		xmlEditor.setQuickFixHandler(new XmlSourceEditor.QuickFixHandler() {
			@Override
			public boolean isSynchronized() {
				return !xmlEditor.isModified() && !xmlPendingApply;
			}

			@Override
			public void synchronize() {
				if (applyXml(true)) {
					validateNow();
				}
			}

			@Override
			public void apply(HlomProblem problem, edu.louisiana.caai.lom.model.support.fixes.HlomFix fix) {
				QuickFixes.apply(HlomMetadataEditor.this, fix, problem);
			}
		});
	}

	// ----------------------------------------------------------------------------------------------------
	// XML caret and selection
	// ----------------------------------------------------------------------------------------------------

	/** The XML caret offset waiting to be mapped to the model (debounced), or -1. */
	private int pendingCaret = -1;
	/** Set while the selection is being synchronized, to avoid echoes between the XML page and the outline. */
	private boolean syncingSelection;

	/** Selects the model element at the XML caret (outline, properties), shortly after the caret stops. */
	private void xmlCaretMoved(int offset) {
		if (syncingSelection || getActivePage() != xmlPageIndex) {
			return;
		}
		boolean scheduled = pendingCaret >= 0;
		pendingCaret = offset;
		if (!scheduled) {
			getSite().getShell().getDisplay().timerExec(250, () -> {
				int caret = pendingCaret;
				pendingCaret = -1;
				if (getContainer().isDisposed() || xmlEditor == null || xmlEditor.isModified() || xmlPendingApply) {
					return; // the text doesn't correspond to the model while it has unapplied edits
				}
				EObject element = XmlSourceEditor.resolve(documentRoot(),
						xmlEditor.elementPathAt(caret));
				if (element != null) {
					syncingSelection = true;
					try {
						StructuredSelection selection = new StructuredSelection(element);
						setSelection(selection);
						if (contentOutlineViewer != null && !contentOutlineViewer.getControl().isDisposed()) {
							contentOutlineViewer.setSelection(selection, true);
						}
					} finally {
						syncingSelection = false;
					}
				}
			});
		}
	}

	/** On the XML page, an element selected in the outline is revealed in the text. */
	@Override
	public void handleContentOutlineSelection(ISelection selection) {
		if (getActivePage() == xmlPageIndex && xmlEditor != null) {
			if (!syncingSelection && selection instanceof org.eclipse.jface.viewers.IStructuredSelection structured
					&& structured.getFirstElement() instanceof EObject element && !xmlEditor.isModified()
					&& !xmlPendingApply) {
				syncingSelection = true;
				try {
					xmlEditor.revealElement(element);
				} finally {
					syncingSelection = false;
				}
			}
			return;
		}
		super.handleContentOutlineSelection(selection);
	}

	private void createJsonPage() {
		jsonEditor = new JsonSourceEditor();
		try {
			jsonPageIndex = addPage(jsonEditor, JsonSourceEditor.createInput(getEditorInput().getName()));
			setPageText(jsonPageIndex, "JSON");
		} catch (PartInitException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			jsonEditor = null;
			return;
		}
		jsonEditor.addModificationListener(() -> firePropertyChange(IEditorPart.PROP_DIRTY));
	}

	/**
	 * Fills the source pages. If the file couldn't be read completely, its original text is shown on the page of
	 * its format (XML or JSON) so that it can be repaired.
	 */
	private void showSource(Resource resource) {
		if (resource != null && (!resource.getErrors().isEmpty() || documentRoot() == null)) {
			String original = readOriginal(resource);
			if (original != null) {
				byte[] start = original.getBytes(StandardCharsets.UTF_8);
				if (HlomJson.looksLikeJson(start, Math.min(start.length, 64)) && jsonEditor != null) {
					jsonEditor.setText(original);
					jsonPendingApply = true;
				} else if (xmlEditor != null) {
					xmlEditor.setText(original);
					xmlPendingApply = true;
				}
				return;
			}
		}
		refreshXml();
		refreshJson();
	}

	/** Whether the document is stored as JSON. */
	private boolean isJsonDocument() {
		Resource resource = mainResource();
		return resource != null && HlomResources.isJson(resource.getURI());
	}

	private String readOriginal(Resource resource) {
		try (InputStream stream = editingDomain.getResourceSet().getURIConverter()
				.createInputStream(resource.getURI())) {
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException _) {
			return null;
		}
	}

	/** The resource being edited. */
	protected Resource mainResource() {
		List<Resource> resources = editingDomain.getResourceSet().getResources();
		return resources.isEmpty() ? null : resources.getFirst();
	}

	/** The document root of the edited document (of its type), or {@code null} if the file couldn't be read. */
	public EObject documentRoot() {
		Resource resource = mainResource();
		if (resource == null || resource.getContents().isEmpty()) {
			return null;
		}
		EObject first = resource.getContents().getFirst();
		return DocumentTypes.ofDocumentRoot(first) != null ? first : null;
	}

	/** The type of a loaded document (from its root element, or from the text of a broken file). */
	private DocumentType detectType(Resource resource) {
		if (resource == null) {
			return HlomDocumentType.INSTANCE;
		}
		DocumentType type = DocumentTypes.of(resource);
		if (type == null) {
			String original = readOriginal(resource);
			type = original == null ? null : DocumentTypes.detect(original);
		}
		return type != null ? type : HlomDocumentType.INSTANCE;
	}

	private static EClass rootClassOf(MedbiqDocumentType type) {
		EClass documentRoot = ExtendedMetaData.INSTANCE.getDocumentRoot(type.ePackage());
		return ((EReference) ExtendedMetaData.INSTANCE.getElement(documentRoot, type.namespace(), type.rootElement()))
				.getEReferenceType();
	}

	private void updateAnchor() {
		EObject documentRoot = documentRoot();
		EObject root = documentRoot instanceof DocumentRoot hlom ? hlom.getLom()
				: documentRoot == null ? null : Paths.rootElement(documentRoot);
		if (lomAnchor.getValue() != root) {
			lomAnchor.setValue(root);
		}
	}

	private void modelChanged() {
		updateAnchor();
		if (getActivePage() == xmlPageIndex && xmlEditor != null && !xmlEditor.isModified() && !xmlPendingApply) {
			refreshXml();
		}
		if (getActivePage() == jsonPageIndex && jsonEditor != null && !jsonEditor.isModified() && !jsonPendingApply) {
			refreshJson();
		}
	}

	private void formSelection(ISelection selection) {
		if (pagesReady) {
			setSelection(selection);
		}
	}

	private boolean isFormPage(int index) {
		return index >= 0 && index < formPages.size();
	}

	@Override
	protected void pageChange(int newPageIndex) {
		if (changingPage) {
			super.pageChange(newPageIndex);
			return;
		}
		int previous = currentPage;
		if (isFormPage(previous)) {
			formPages.get(previous).commit();
		}
		if (previous == xmlPageIndex && newPageIndex != xmlPageIndex && !applyXml(true)) {
			// Stay on the XML page so the user can fix the text.
			stayOn(xmlPageIndex);
			return;
		}
		if (previous == jsonPageIndex && newPageIndex != jsonPageIndex && !applyJson(true)) {
			stayOn(jsonPageIndex);
			return;
		}
		super.pageChange(newPageIndex);
		currentPage = newPageIndex;
		if (navigation != null) {
			navigation.select(newPageIndex);
		}

		HlomActionBarContributor contributor = getActionBarContributor() instanceof HlomActionBarContributor c ? c : null;
		if (newPageIndex == xmlPageIndex && xmlEditor != null) {
			if (!xmlEditor.isModified() && !xmlPendingApply) {
				refreshXml();
				// Show the element selected on the previous page (form, tree or outline).
				if (getSelection() instanceof org.eclipse.jface.viewers.IStructuredSelection structured
						&& structured.getFirstElement() instanceof EObject element && element.eResource() != null) {
					getSite().getShell().getDisplay().asyncExec(() -> {
						if (xmlEditor != null && getActivePage() == xmlPageIndex) {
							syncingSelection = true;
							try {
								xmlEditor.revealElement(element);
							} finally {
								syncingSelection = false;
							}
						}
					});
				}
			}
			setCurrentViewer(null);
			if (contributor != null) {
				contributor.useTextActions(xmlEditor);
			}
		} else if (newPageIndex == jsonPageIndex && jsonEditor != null) {
			if (!jsonEditor.isModified() && !jsonPendingApply) {
				refreshJson();
			}
			setCurrentViewer(null);
			if (contributor != null) {
				contributor.useTextActions(jsonEditor);
			}
		} else {
			if (contributor != null) {
				contributor.useModelActions();
			}
			if (newPageIndex == treePageIndex) {
				setCurrentViewerPane(treePane);
			} else {
				setCurrentViewer(null);
			}
		}
		if (isFormPage(newPageIndex)) {
			formPages.get(newPageIndex).refresh();
		}
	}

	private void stayOn(int pageIndex) {
		changingPage = true;
		try {
			setActivePage(pageIndex);
		} finally {
			changingPage = false;
		}
	}

	/**
	 * Load problems are shown on the XML page (with the original text) and in the Validation view, so the
	 * generated "Problems" page is not added: activating it would leave the XML page and try to apply the
	 * broken text, asking the user about XML errors right after opening.
	 */
	@Override
	protected void updateProblemIndication() {
		// Nothing to do.
	}

	/** The outline shows the document expanded, like the tree page. */
	@Override
	public org.eclipse.ui.views.contentoutline.IContentOutlinePage getContentOutlinePage() {
		boolean created = contentOutlinePage != null;
		org.eclipse.ui.views.contentoutline.IContentOutlinePage page = super.getContentOutlinePage();
		if (!created) {
			getSite().getShell().getDisplay().timerExec(300, () -> {
				if (contentOutlineViewer != null && !contentOutlineViewer.getControl().isDisposed()) {
					contentOutlineViewer.expandToLevel(4);
				}
			});
		}
		return page;
	}

	@Override
	protected void showTabs() {
		// Always several pages; keep the tab labels.
	}

	@Override
	protected void hideTabs() {
		// Always several pages; keep the tab labels.
	}

	@Override
	public void setFocus() {
		int page = getActivePage();
		if (page == treePageIndex && treePane != null) {
			treePane.setFocus();
		} else if (page == xmlPageIndex && xmlEditor != null) {
			xmlEditor.setFocus();
		} else if (page == jsonPageIndex && jsonEditor != null) {
			jsonEditor.setFocus();
		} else if (page >= 0) {
			getControl(page).setFocus();
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// XML synchronization
	// ----------------------------------------------------------------------------------------------------

	private void refreshXml() {
		Resource resource = mainResource();
		if (xmlEditor == null || resource == null) {
			return;
		}
		try {
			xmlEditor.setText(HlomResources.toXml(resource));
		} catch (IOException e) {
			HlomEditorPlugin.INSTANCE.log(e);
		}
	}

	/**
	 * Parses the XML page text and, if it is a valid HLOM document, replaces the model content with it (as one
	 * undoable command).
	 *
	 * @param interactive whether to ask the user what to do if the text can't be parsed
	 * @return {@code false} if the text has errors and the user wants to keep editing it
	 */
	private boolean applyXml(boolean interactive) {
		if (xmlEditor == null || (!xmlEditor.isModified() && !xmlPendingApply)) {
			return true;
		}
		Boolean applied = applyText(xmlEditor.getText(), "XML", NLS.bind(Messages.HlomMetadataEditor_TheRootElementMustBe, documentType.rootElement(), documentType.namespace()), interactive);
		if (applied == null) {
			// Discarded.
			xmlPendingApply = false;
			refreshXml();
			return true;
		}
		if (applied) {
			xmlPendingApply = false;
			xmlEditor.markSynchronized();
		}
		return applied;
	}

	private void refreshJson() {
		Resource resource = mainResource();
		if (jsonEditor == null || resource == null) {
			return;
		}
		try {
			jsonEditor.setText(HlomResources.getDocumentRoot(resource) == null ? "" : HlomResources.toJson(resource));
		} catch (IOException e) {
			HlomEditorPlugin.INSTANCE.log(e);
		}
	}

	/** Like {@link #applyXml(boolean)}, for the JSON page. */
	private boolean applyJson(boolean interactive) {
		if (jsonEditor == null || (!jsonEditor.isModified() && !jsonPendingApply)) {
			return true;
		}
		Boolean applied = applyText(jsonEditor.getText(), "JSON", Messages.HlomMetadataEditor_ItMustBeAnObject,
				interactive);
		if (applied == null) {
			jsonPendingApply = false;
			refreshJson();
			return true;
		}
		if (applied) {
			jsonPendingApply = false;
			jsonEditor.markSynchronized();
		}
		return applied;
	}

	/**
	 * Parses the text of a source page (XML or JSON) and, if it is a valid HLOM document, replaces the model
	 * content with it (as one undoable command).
	 *
	 * @return {@code true} if applied, {@code false} if the text has errors and the user wants to keep editing it,
	 *         {@code null} if it has errors and the user chose to discard the changes
	 */
	private Boolean applyText(String text, String format, String requirement, boolean interactive) {
		Resource resource = mainResource();
		String error = null;
		Resource parsed = null;
		try {
			if (documentType == HlomDocumentType.INSTANCE) {
				parsed = HlomResources.parse(text, resource.getURI());
			} else {
				parsed = documentType.createResourceSet().createResource(resource.getURI());
				parsed.load(new java.io.ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), HlomResources.loadOptions());
			}
		} catch (IOException | RuntimeException e) {
			error = e.getMessage();
		}
		EObject newRoot = parsed == null || parsed.getContents().isEmpty() ? null : parsed.getContents().getFirst();
		if (newRoot != null && DocumentTypes.ofDocumentRoot(newRoot) != documentType) {
			newRoot = null;
		}
		if (error == null && newRoot == null) {
			error = NLS.bind(Messages.HlomMetadataEditor_TheTextIsNotA, documentType.label(), requirement);
		}
		if (error != null) {
			if (!interactive) {
				return false;
			}
			boolean discard = MessageDialog.openQuestion(getSite().getShell(), format + " Errors",
					NLS.bind(Messages.HlomMetadataEditor_TheCanTBeApplied, new Object[] { format, error, format, format }));
			return discard ? null : false;
		}

		CompoundCommand command = new CompoundCommand(NLS.bind(Messages.HlomMetadataEditor_Edit, format));
		if (!resource.getContents().isEmpty()) {
			command.append(new RemoveCommand(editingDomain, resource.getContents(),
					new ArrayList<>(resource.getContents())));
		}
		command.append(new AddCommand(editingDomain, resource.getContents(), newRoot));
		editingDomain.getCommandStack().execute(command);
		resource.getErrors().clear();
		resource.getWarnings().clear();
		updateAnchor();
		broadcaster.fire();
		return true;
	}

	/**
	 * The current content of the document as text, including edits not applied to the model yet: the text of a
	 * modified source page (XML or JSON), else the model serialized as XML; {@code null} if there is no content.
	 */
	public String currentXml() {
		if (xmlEditor != null && (xmlEditor.isModified() || xmlPendingApply)) {
			return xmlEditor.getText();
		}
		if (jsonEditor != null && (jsonEditor.isModified() || jsonPendingApply)) {
			return jsonEditor.getText();
		}
		Resource resource = mainResource();
		if (resource == null || documentRoot() == null) {
			return null;
		}
		try {
			return HlomResources.toXml(resource);
		} catch (IOException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return null;
		}
	}

	/**
	 * Applies the edits of the source pages (XML, JSON) to the model, so that commands see the current document.
	 *
	 * @return whether the model is up to date (false if a source page has text that can't be applied)
	 */
	public boolean applySourcePages() {
		boolean xml = xmlEditor == null || !xmlEditor.isModified() && !xmlPendingApply || applyXml(true);
		boolean json = jsonEditor == null || !jsonEditor.isModified() && !jsonPendingApply || applyJson(true);
		return xml && json;
	}

	/**
	 * Shows the element at an element path (see {@link ElementPath}), as far as the path exists; applies pending
	 * source page edits first.
	 *
	 * @return whether an element was found
	 */
	public boolean revealPath(String path) {
		if (!pagesReady) {
			getSite().getShell().getDisplay().asyncExec(() -> {
				if (getContainer() != null && !getContainer().isDisposed()) {
					revealPath(path);
				}
			});
			return true;
		}
		applyXml(false);
		applyJson(false);
		Resource resource = mainResource();
		EObject root = resource == null ? null : documentRoot();
		EObject element = root == null ? null : ElementPath.parse(path).resolve(root);
		if (element == null) {
			return false;
		}
		reveal(element);
		return true;
	}

	// ----------------------------------------------------------------------------------------------------
	// Dirty state and saving
	// ----------------------------------------------------------------------------------------------------

	@Override
	public boolean isDirty() {
		return super.isDirty() || saveFailed || (xmlEditor != null && (xmlEditor.isModified() || xmlPendingApply))
				|| (jsonEditor != null && (jsonEditor.isModified() || jsonPendingApply));
	}

	/** Commits pending form edits and XML text; returns {@code false} if the XML can't be applied. */
	private boolean commitAll() {
		int page = getActivePage();
		if (isFormPage(page)) {
			formPages.get(page).commit();
		}
		if (!applyXml(false)) {
			MessageDialog.openError(getSite().getShell(), Messages.HlomMetadataEditor_CannotSave,
					Messages.HlomMetadataEditor_TheXMLTextHasErrors);
			return false;
		}
		if (!applyJson(false)) {
			MessageDialog.openError(getSite().getShell(), Messages.HlomMetadataEditor_CannotSave,
					Messages.HlomMetadataEditor_TheJSONTextHasErrors);
			return false;
		}
		return true;
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		if (commitAll()) {
			Resource resource = mainResource();
			// The generated save skips read-only files without a word (the read-only state is cached: read it again).
			if (editingDomain.getResourceToReadOnlyMap() != null) {
				editingDomain.getResourceToReadOnlyMap().remove(resource);
			}
			if (editingDomain.isReadOnly(resource)) {
				saveFailed = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
				MessageDialog.openError(getSite().getShell(), Messages.HlomMetadataEditor_CannotSave, NLS.bind(Messages.HlomMetadataEditor_IsReadOnlyYourChanges, getEditorInput().getName()));
				return;
			}
			resourceToDiagnosticMap.remove(resource);
			saving = true;
			try {
				super.doSave(progressMonitor);
			} finally {
				saving = false;
			}
			// The generated save records a failure (a read-only file, a full disk) instead of reporting it.
			org.eclipse.emf.common.util.Diagnostic failure = resourceToDiagnosticMap.get(resource);
			saveFailed = failure != null && failure.getSeverity() == org.eclipse.emf.common.util.Diagnostic.ERROR;
			if (saveFailed) {
				firePropertyChange(IEditorPart.PROP_DIRTY);
				ErrorDialog.openError(getSite().getShell(), Messages.HlomMetadataEditor_CannotSave,
						NLS.bind(Messages.HlomMetadataEditor_CouldNotBeSavedYour, getEditorInput().getName()),
						BasicDiagnostic.toIStatus(failure));
				return;
			}
			rememberDiskState();
			if (xmlEditor != null && getActivePage() == xmlPageIndex) {
				refreshXml();
			}
			if (jsonEditor != null && getActivePage() == jsonPageIndex) {
				refreshJson();
			}
			firePropertyChange(IEditorPart.PROP_DIRTY);
			validateNow();
			if (!isDirty()) {
				notifySaved();
			}
		}
	}

	@Override
	public void doSaveAs() {
		if (commitAll()) {
			super.doSaveAs();
		}
	}

	/** Saving into a Hermes project makes the document a workspace file of it. */
	@Override
	protected void doSaveAs(URI uri, IEditorInput editorInput) {
		super.doSaveAs(EditorInputs.resourceUriFor(uri),
				editorInput instanceof URIEditorInput ? EditorInputs.inputFor(uri) : editorInput);
	}

	// ----------------------------------------------------------------------------------------------------
	// Changes of the file outside of the editor
	// ----------------------------------------------------------------------------------------------------

	/** The modification time and size of a file. */
	private record DiskState(FileTime modified, long size) {
		/** The state of a file, or {@code null} if it doesn't exist. */
		static DiskState of(Path file) {
			try {
				return new DiskState(Files.getLastModifiedTime(file), Files.size(file));
			} catch (IOException _) {
				return null;
			}
		}
	}

	private void rememberDiskState() {
		Path file = EditorInputs.pathOf(getEditorInput());
		diskState = file == null ? null : DiskState.of(file);
	}

	/** Checks the file, as Eclipse's editors do, when the editor is activated. */
	@Override
	protected void handleActivate() {
		super.handleActivate();
		checkDisk();
	}

	private void checkDiskIfActive() {
		if (getContainer() != null && !getContainer().isDisposed() && getSite().getPage().getActiveEditor() == this) {
			checkDisk();
		}
	}

	/**
	 * Takes over changes of the file made outside of the editor (another program, a Git checkout or pull): the new
	 * content replaces the document if the editor has no unsaved changes, or if the user agrees to discard them. If
	 * the file was deleted, the user can close the editor or keep the document (to save it again).
	 */
	public void checkDisk() {
		Path file = EditorInputs.pathOf(getEditorInput());
		if (saving || checkingDisk || !pagesReady || file == null || diskState == null) {
			return;
		}
		DiskState now = DiskState.of(file);
		if (diskState.equals(now)) {
			return;
		}
		checkingDisk = true;
		try {
			String name = getEditorInput().getName();
			if (now == null) {
				diskState = null;
				if (MessageDialog.openQuestion(getSite().getShell(), Messages.HlomMetadataEditor_FileDeleted, NLS.bind(Messages.HlomMetadataEditor_TheFileWasDeletedOr, name))) {
					getSite().getPage().closeEditor(this, false);
				} else {
					saveFailed = true;
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
				return;
			}
			diskState = now;
			if (isDirty() && !MessageDialog.openQuestion(getSite().getShell(), Messages.HlomMetadataEditor_FileChanged, NLS.bind(Messages.HlomMetadataEditor_TheFileWasChangedOutside, name))) {
				return;
			}
			reload(file);
		} finally {
			checkingDisk = false;
		}
	}

	/** Replaces the document with the content of its file. */
	private void reload(Path file) {
		String text;
		try {
			text = readText(Files.readAllBytes(file));
		} catch (IOException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return;
		}
		Boolean applied = applyText(text, isJsonDocument() ? "JSON" : "XML", "", false);
		if (!Boolean.TRUE.equals(applied)) {
			MessageDialog.openWarning(getSite().getShell(), Messages.HlomMetadataEditor_FileChanged, NLS.bind(Messages.HlomMetadataEditor_TheFileWasChangedOutside2, getEditorInput().getName(), documentType.label()));
			return;
		}
		// The file's content is the saved state: nothing to undo or to save.
		editingDomain.getCommandStack().flush();
		saveFailed = false;
		xmlPendingApply = false;
		jsonPendingApply = false;
		refreshXml();
		refreshJson();
		firePropertyChange(IEditorPart.PROP_DIRTY);
		validateNow();
	}

	/** The text of a file: in the encoding its XML declaration names, else UTF-8. */
	private static String readText(byte[] bytes) {
		String head = new String(bytes, 0, Math.min(bytes.length, 200), StandardCharsets.ISO_8859_1);
		java.util.regex.Matcher matcher = java.util.regex.Pattern
				.compile("^\\s*<\\?xml[^>]*encoding\\s*=\\s*[\"']([A-Za-z0-9._-]+)[\"']").matcher(head);
		java.nio.charset.Charset charset = StandardCharsets.UTF_8;
		if (matcher.find()) {
			try {
				charset = java.nio.charset.Charset.forName(matcher.group(1));
			} catch (IllegalArgumentException _) {
				// Not a known encoding: UTF-8.
			}
		}
		String text = new String(bytes, charset);
		return text.startsWith("\uFEFF") ? text.substring(1) : text;
	}

	// ----------------------------------------------------------------------------------------------------
	// Validation
	// ----------------------------------------------------------------------------------------------------

	/** Validates the document in the background, a moment after the last change (as set in the preferences). */
	private void scheduleValidation() {
		if (!HlomPreferences.isValidateOnChange()) {
			return;
		}
		if (!validationScheduled && !getContainer().isDisposed()) {
			validationScheduled = true;
			getContainer().getDisplay().timerExec(HlomPreferences.getValidationDelay(), () -> {
				validationScheduled = false;
				validateInBackground();
			});
		}
	}

	/** Validates the document now and shows the problems (Validate, explicit requests). */
	@Override
	public void validateNow() {
		Resource resource = mainResource();
		if (resource == null || getContainer() == null || getContainer().isDisposed()) {
			return;
		}
		validationRun++;
		List<HlomProblem> loaded = loadProblems(resource);
		AdapterFactoryItemDelegator labels = new AdapterFactoryItemDelegator(adapterFactory);
		showProblems(loaded, withoutHiddenInfos(loaded, validate(documentType, resource, documentRoot(), labels)));
	}

	/**
	 * Validates a copy of the document in a background job, so that typing isn't slowed down by the validation of
	 * large documents; the problems are shown unless the document changed or was validated again meanwhile.
	 */
	private void validateInBackground() {
		Resource resource = mainResource();
		if (resource == null || getContainer() == null || getContainer().isDisposed()) {
			return;
		}
		long run = ++validationRun;
		List<HlomProblem> loaded = loadProblems(resource);
		ValidationSnapshot snapshot = ValidationSnapshot.of(resource, documentRoot());
		if (snapshot == null) {
			validateNow();
			return;
		}
		DocumentType type = documentType;
		org.eclipse.swt.widgets.Display display = getContainer().getDisplay();
		Job job = Job.create(NLS.bind(Messages.HlomMetadataEditor_Validating, getPartName()), _ -> {
			ComposedAdapterFactory labelFactory = createAdapterFactory();
			List<HlomProblem> found;
			try {
				found = validate(type, snapshot.resource(), snapshot.root(), new AdapterFactoryItemDelegator(labelFactory));
			} finally {
				labelFactory.dispose();
			}
			List<HlomProblem> result = withoutHiddenInfos(loaded, found.stream().map(snapshot::original).toList());
			if (!display.isDisposed()) {
				display.asyncExec(() -> {
					if (run == validationRun && getContainer() != null && !getContainer().isDisposed()) {
						showProblems(loaded, result);
					}
				});
			}
			return Status.OK_STATUS;
		});
		job.setSystem(true);
		job.schedule();
	}

	/** The problems of loading the file. */
	private static List<HlomProblem> loadProblems(Resource resource) {
		List<HlomProblem> loaded = new ArrayList<>();
		for (Resource.Diagnostic diagnostic : resource.getErrors()) {
			loaded.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, NLS.bind(Messages.HlomMetadataEditor_NotLoaded, diagnostic.getMessage()), null,
					null, diagnostic.getLine(), diagnostic.getColumn()));
		}
		return List.copyOf(loaded);
	}

	/** The load problems and the problems found, without the informational ones if they are hidden. */
	private static List<HlomProblem> withoutHiddenInfos(List<HlomProblem> loaded, List<HlomProblem> found) {
		List<HlomProblem> result = new ArrayList<>(loaded);
		boolean showInfos = HlomPreferences.isShowInfoProblems();
		for (HlomProblem problem : found) {
			if (showInfos || problem.severity() != Severity.INFO) {
				result.add(problem);
			}
		}
		return result;
	}

	/** Shows the problems in the pages, the Validation view and the Problems view. */
	private void showProblems(List<HlomProblem> loaded, List<HlomProblem> result) {
		loadProblems = loaded;
		problems = Collections.unmodifiableList(result);
		if (xmlEditor != null) {
			// The load problems of a JSON file are at lines of the JSON text.
			xmlEditor.setProblems(isJsonDocument() ? problems.stream().filter(p -> !loadProblems.contains(p)).toList()
					: problems);
		}
		for (int i = 0; i < formPages.size(); i++) {
			List<HlomProblem> pageProblems = problemsOf(formPages.get(i).getId());
			formPages.get(i).showProblems(pageProblems);
			navigation.setProblems(i, (int) pageProblems.stream().filter(p -> p.severity() == Severity.ERROR).count(),
					(int) pageProblems.stream().filter(p -> p.severity() == Severity.WARNING).count());
		}
		for (Runnable listener : problemsListeners) {
			listener.run();
		}
		showInProblemsView();
	}

	/**
	 * Shows the problems of a document from outside the Hermes projects in the Problems view (those of the projects'
	 * documents are reported by their builder, as saved).
	 */
	private void showInProblemsView() {
		Path document = getEditorInput() instanceof IFileEditorInput ? null : EditorInputs.pathOf(getEditorInput());
		if (openDocument != null && !openDocument.equals(document)) {
			OpenDocuments.remove(openDocument);
		}
		openDocument = document;
		if (document != null) {
			OpenDocuments.show(document, problems);
		}
	}

	/**
	 * All checks of a document type: XML Schema, model constraints and the standard's rules. Uses only its
	 * arguments, so that it can run in the background (on a copy of the document).
	 */
	private static List<HlomProblem> validate(DocumentType documentType, Resource resource, EObject root,
			AdapterFactoryItemDelegator labels) {
		if (documentType == HlomDocumentType.INSTANCE) {
			return HlomValidator.validate(resource, labels::getText);
		}
		List<HlomProblem> result = new ArrayList<>();
		if (root == null) {
			result.add(new HlomProblem(Severity.ERROR, Origin.MODEL, NLS.bind(Messages.HlomMetadataEditor_TheDocumentHasNoRoot, documentType.rootElement()), null, null, -1, -1));
			return result;
		}
		try {
			result.addAll(documentType.validateSchema(HlomResources.toBytes(resource), root));
		} catch (IOException e) {
			result.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, NLS.bind(Messages.HlomMetadataEditor_TheDocumentCouldNotBe, e.getMessage()), null, null, -1, -1));
		}
		result.addAll(HlomValidator.validateModel(root, labels::getText));
		result.addAll(documentType.checkRules(root));
		return result;
	}

	@Override
	public List<HlomProblem> getProblems() {
		return problems;
	}

	@Override
	public void reveal(HlomProblem problem) {
		if (problem.object() != null && problem.object().eResource() != null && treePageIndex >= 0) {
			setActivePage(treePageIndex);
			selectionViewer.setSelection(new StructuredSelection(problem.object()), true);
			selectionViewer.getControl().setFocus();
		} else if (problem.line() > 0 && jsonPageIndex >= 0 && isJsonDocument() && loadProblems.contains(problem)) {
			setActivePage(jsonPageIndex);
			jsonEditor.revealLine(problem.line(), Math.max(1, problem.column()));
		} else if (problem.line() > 0 && xmlPageIndex >= 0) {
			setActivePage(xmlPageIndex);
			xmlEditor.revealLine(problem.line(), Math.max(1, problem.column()));
		}
	}

	/** Shows the problem of a marker of the Problems view (once the pages are ready). */
	@Override
	public void gotoMarker(IMarker marker) {
		if (!pagesReady) {
			getSite().getShell().getDisplay().asyncExec(() -> {
				if (getContainer() != null && !getContainer().isDisposed()) {
					gotoMarker(marker);
				}
			});
			return;
		}
		String message = marker.getAttribute(IMarker.MESSAGE, "");
		String path = marker.getAttribute(HermesWorkspace.ELEMENT_PATH, null);
		int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		problems.stream()
				.filter(problem -> message.equals(problem.message()) && Objects.equals(path, problem.path()))
				.findFirst()
				.or(() -> problems.stream().filter(problem -> line > 0 && problem.line() == line).findFirst())
				.ifPresentOrElse(this::reveal, () -> {
					if (line > 0 && xmlPageIndex >= 0) {
						setActivePage(xmlPageIndex);
						xmlEditor.revealLine(line, 1);
					}
				});
	}

	@Override
	public void addProblemsListener(Runnable listener) {
		problemsListeners.add(listener);
	}

	@Override
	public void removeProblemsListener(Runnable listener) {
		problemsListeners.remove(listener);
	}

	private void notifySaved() {
		long errors = problems.stream().filter(p -> p.severity() == Severity.ERROR).count();
		String status = errors == 0 ? "The document is valid."
				: errors + (errors == 1 ? " error remains." : " errors remain.");
		Notifier.notify(HlomTheme.image("icons/app/hlom32.png"), NLS.bind(Messages.HlomMetadataEditor_Saved, getEditorInput().getName()), status,
				NotifierTheme.GRAY_THEME);
	}

	// ----------------------------------------------------------------------------------------------------
	// EditorServices
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void showPage(String pageId) {
		int index = pageIndex(pageId);
		if (index >= 0) {
			setActivePage(index);
		}
	}

	private int pageIndex(String pageId) {
		if (TREE.equals(pageId)) {
			return treePageIndex;
		}
		if (XML.equals(pageId)) {
			return xmlPageIndex;
		}
		if (JSON.equals(pageId)) {
			return jsonPageIndex;
		}
		for (int i = 0; i < formPages.size(); i++) {
			if (formPages.get(i).getId().equals(pageId)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void reveal(EObject element) {
		String pageId = pageIdOf(element);
		if (pageId != null && pageIndex(pageId) >= 0) {
			showPage(pageId);
			setSelection(new StructuredSelection(element));
		} else if (treePageIndex >= 0 && element != null) {
			setActivePage(treePageIndex);
			selectionViewer.setSelection(new StructuredSelection(element), true);
		}
	}

	@Override
	public ValidationSource validation() {
		return this;
	}

	@Override
	public List<HlomProblem> problemsOf(String pageId) {
		List<HlomProblem> result = new ArrayList<>();
		for (HlomProblem problem : problems) {
			if (pageId.equals(pageIdOf(problem.object()))) {
				result.add(problem);
			}
		}
		return result;
	}

	/**
	 * The form page showing an element: for Healthcare LOM the page of its category (or healthcare part); for the
	 * other standards the page whose section covers the longest part of its element path.
	 */
	String pageIdOf(EObject element) {
		if (element == null) {
			return null;
		}
		if (documentType != HlomDocumentType.INSTANCE) {
			return specPageOf(element);
		}
		return hlomPageIdOf(element);
	}

	private String specPageOf(EObject element) {
		List<String> names = ElementPath.of(element).names();
		String best = null;
		int bestLength = -1;
		for (AbstractLomPage page : formPages) {
			if (page instanceof SpecPage spec) {
				for (String sectionPath : spec.coveredPaths()) {
					List<String> covered = sectionPath.isEmpty() ? List.of() : List.of(sectionPath.split("/"));
					// The names start with the root element.
					if (names.size() > covered.size() && names.subList(1, 1 + covered.size()).equals(covered)
							&& covered.size() > bestLength) {
						best = spec.getId();
						bestLength = covered.size();
					}
				}
			}
		}
		return best;
	}

	/** The HLOM page of an element, determined by the LOM category (or healthcare part) it belongs to. */
	static String hlomPageIdOf(EObject element) {
		EObject child = null;
		for (EObject current = element; current != null; current = current.eContainer()) {
			if (current instanceof Lom) {
				return child == null ? "general" : categoryPage(child);
			}
			child = current;
		}
		return null;
	}

	private static String categoryPage(EObject category) {
		LomPackage lom = LomPackage.eINSTANCE;
		EStructuralFeature feature = category.eContainmentFeature();
		if (feature == lom.getLom_General()) {
			return "general";
		} else if (feature == lom.getLom_LifeCycle()) {
			return "lifecycle";
		} else if (feature == lom.getLom_MetaMetadata()) {
			return "metametadata";
		} else if (feature == lom.getLom_Technical()) {
			return "technical";
		} else if (feature == lom.getLom_Educational()) {
			return "educational";
		} else if (feature == lom.getLom_Rights()) {
			return "rights";
		} else if (feature == lom.getLom_Relation()) {
			return "relation";
		} else if (feature == lom.getLom_Annotation()) {
			return "annotation";
		} else if (feature == lom.getLom_Classification()) {
			return "classification";
		} else if (feature == lom.getLom_HealthcareMetadata()) {
			return category instanceof HealthcareMetadataType healthcare && healthcare.getHealthcareEducation() == null
					&& healthcare.getHealthcareAsset() != null ? "healthcare-asset" : "healthcare-education";
		}
		return null;
	}

	// ----------------------------------------------------------------------------------------------------

	@Override
	public void dispose() {
		for (AbstractLomPage page : formPages) {
			page.dispose();
		}
		if (broadcaster != null) {
			broadcaster.dispose();
		}
		if (toolkit != null) {
			toolkit.dispose();
		}
		problemsListeners.clear();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(diskListener);
		if (!getSite().getShell().isDisposed()) {
			getSite().getShell().removeShellListener(shellListener);
		}
		if (openDocument != null) {
			OpenDocuments.remove(openDocument);
		}
		super.dispose();
	}
}
