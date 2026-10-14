/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.editor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.ide.IDE;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.edit.ui.util.EditUIUtil;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.start.RecentDocuments;
import edu.louisiana.caai.lom.editor.start.StartView;
import edu.louisiana.caai.lom.editor.start.StartViewManager;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.editor.validation.ValidationView;
import edu.louisiana.caai.lom.editor.wizards.NewHlomDocumentWizard;
import edu.louisiana.caai.lom.editor.workspace.EditorInputs;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.lom.presentation.LomEditor;

/**
 * Customized {@link WorkbenchAdvisor} for the RCP application.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class HlomEditorAdvisor extends WorkbenchAdvisor {
	/**
	 * The default file extension filters for use in dialogs.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final String[] FILE_EXTENSION_FILTERS = getFileExtensionFilters();

	/**
	 * Returns the default file extension filters. This method should only be used to initialize {@link #FILE_EXTENSION_FILTERS}.
	 * <!-- begin-user-doc -->
	 * Only HLOM documents are edited by this application.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private static String[] getFileExtensionFilters() {
		List<String> result = new UniqueEList<String>();
		result.addAll(LomEditor.FILE_EXTENSION_FILTERS);
		return result.toArray(new String[0]);
	}

	/**
	 * This looks up a string in the plugin's plugin.properties file.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static String getString(String key) {
		return HlomEditorPlugin.INSTANCE.getString(key);
	}

	/**
	 * This looks up a string in plugin.properties, making a substitution.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static String getString(String key, Object s1) {
		return edu.louisiana.caai.lom.editor.HlomEditorPlugin.INSTANCE.getString(key, new Object[] { s1 });
	}

	/**
	 * Key binding context of the application windows; its bindings (e.g. M1+N for a new document) take precedence
	 * over the default workbench bindings.
	 */
	public static final String CONTEXT_ID = "edu.louisiana.caai.lom.editor.context";

	/**
	 * RCP's application
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Application implements IApplication {
		/**
		 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public Object start(IApplicationContext context) throws Exception {
			WorkbenchAdvisor workbenchAdvisor = new HlomEditorAdvisor();
			Display display = PlatformUI.createDisplay();
			// Files the operating system asks to open (double click, Open With, a second launch with a file).
			display.addListener(SWT.OpenDocument, event -> {
				if (event.text != null) {
					FILES_TO_OPEN.add(event.text);
				}
			});
			try {
				int returnCode = PlatformUI.createAndRunWorkbench(display, workbenchAdvisor);
				if (returnCode == PlatformUI.RETURN_RESTART) {
					return IApplication.EXIT_RESTART;
				} else {
					return IApplication.EXIT_OK;
				}
			} finally {
				display.dispose();
			}
		}

		/**
		 * @see org.eclipse.equinox.app.IApplication#stop()
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public void stop() {
			// Do nothing.
		}
	}

	/**
	 * RCP's perspective
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Perspective implements IPerspectiveFactory {
		/**
		 * Perspective ID
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final String ID_PERSPECTIVE = "edu.louisiana.caai.lom.editor.HlomEditorAdvisorPerspective";

		/**
		 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
		 * <!-- begin-user-doc -->
		 * Adds the validation view below the editor area, with the Problems and Library views (and a place for the Search view),
		 * and the application's views to Window > Show View.
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		@Override
		public void createInitialLayout(IPageLayout layout) {
			layout.setEditorAreaVisible(true);
			layout.addPerspectiveShortcut(ID_PERSPECTIVE);

			// The project manager on the left.
			IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, (float) 0.2, layout.getEditorArea());
			left.addView(edu.louisiana.caai.lom.editor.projects.ProjectsView.ID);

			IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.75, layout.getEditorArea());
			right.addView(IPageLayout.ID_OUTLINE);

			IFolderLayout bottonRight = layout.createFolder("bottonRight", IPageLayout.BOTTOM, (float) 0.50, "right");
			bottonRight.addView(IPageLayout.ID_PROP_SHEET);

			IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.82, layout.getEditorArea());
			bottom.addView(edu.louisiana.caai.lom.editor.validation.ValidationView.ID);
			bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
			bottom.addView(edu.louisiana.caai.lom.editor.library.LibraryView.ID);
			bottom.addPlaceholder(org.eclipse.search.ui.NewSearchUI.SEARCH_VIEW_ID);

			// Window > Show View
			layout.addShowViewShortcut(edu.louisiana.caai.lom.editor.projects.ProjectsView.ID);
			layout.addShowViewShortcut(edu.louisiana.caai.lom.editor.validation.ValidationView.ID);
			layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
			layout.addShowViewShortcut(edu.louisiana.caai.lom.editor.library.LibraryView.ID);
			layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
			layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
			layout.addShowViewShortcut(org.eclipse.search.ui.NewSearchUI.SEARCH_VIEW_ID);
			layout.addShowViewShortcut("edu.louisiana.caai.lom.editor.compare.CompareView");
			layout.addShowViewShortcut("edu.louisiana.caai.lom.editor.help.GuideView");
			// Navigate > Show In
			layout.addShowInPart(edu.louisiana.caai.lom.editor.projects.ProjectsView.ID);
			// The views of Git (when EGit is installed): repositories, staging and the history of a document.
			layout.addShowViewShortcut("org.eclipse.egit.ui.RepositoriesView");
			layout.addShowViewShortcut("org.eclipse.egit.ui.StagingView");
			layout.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView");
			layout.addPerspectiveShortcut("org.eclipse.egit.ui.GitRepositoryExploring");

			// The start page takes the place of the editor area while no document is open (StartViewManager).
			layout.addView(StartView.ID, IPageLayout.LEFT, 0.5f, layout.getEditorArea());
			layout.getViewLayout(StartView.ID).setCloseable(false);
			layout.getViewLayout(StartView.ID).setMoveable(false);
			layout.addPerspectiveShortcut(edu.louisiana.caai.lom.editor.library.LibraryPerspective.ID);
		}
	}

	/**
	 * RCP's window advisor
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class WindowAdvisor extends WorkbenchWindowAdvisor {
		/**
		 * @see WorkbenchWindowAdvisor#WorkbenchWindowAdvisor(org.eclipse.ui.application.IWorkbenchWindowConfigurer)
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public WindowAdvisor(IWorkbenchWindowConfigurer configurer) {
			super(configurer);
		}

		/**
		 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowOpen()
		 * <!-- begin-user-doc -->
		 * Larger initial window with a tool bar.
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		@Override
		public void preWindowOpen() {
			IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
			configurer.setInitialSize(new Point(1280, 860));
			configurer.setShowCoolBar(true);
			configurer.setShowStatusLine(true);
			configurer.setTitle(getString("_UI_Application_title"));
		}

		/**
		 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
			return new WindowActionBarAdvisor(configurer);
		}

		/**
		 * Opens the window maximized (also over a restored size): the editor pages and views are laid out for a
		 * large window.
		 */
		@Override
		public void postWindowCreate() {
			org.eclipse.swt.widgets.Shell shell = getWindowConfigurer().getWindow().getShell();
			if (Boolean.getBoolean(OFFSCREEN_PROPERTY)) {
				// Automated tests: off the screen, in the size of a typical window (dialogs open over it, too).
				shell.setBounds(-30000, -30000, 1600, 1000);
			} else {
				shell.setMaximized(true);
			}
		}

		/** Opens the window off the screen (automated UI tests, which need no real input). */
		static final String OFFSCREEN_PROPERTY = "hermes.offscreen";

		/**
		 * Activates the application key binding context.
		 */
		@Override
		public void postWindowOpen() {
			IContextService contextService = getWindowConfigurer().getWindow().getService(IContextService.class);
			if (contextService != null) {
				contextService.activateContext(CONTEXT_ID);
			}
			IWorkbenchWindow window = getWindowConfigurer().getWindow();
			updateLayout(window);
			RecentDocuments.track(window);
			StartViewManager.install(window);
			placeHeapStatus(window, 0);
		}

		/**
		 * The version of the perspective's layout ({@link Perspective#createInitialLayout}); to be increased when
		 * views, shortcuts or Show In targets are added, so that existing users get them.
		 */
		static final int LAYOUT_VERSION = 2;

		/** The preference of the layout version the user's saved layout was made with. */
		private static final String LAYOUT_VERSION_PREFERENCE = "layoutVersion";

		/**
		 * Resets the perspective once if the layout the workbench restored was made by an older version of the
		 * application: the workbench restores saved layouts as they were, without the views and shortcuts added
		 * since (as Window &gt; Reset Perspective would show them).
		 */
		private static void updateLayout(IWorkbenchWindow window) {
			org.eclipse.jface.preference.IPreferenceStore preferences = HlomEditorPlugin.getPlugin().getPreferenceStore();
			if (preferences.getInt(LAYOUT_VERSION_PREFERENCE) >= LAYOUT_VERSION) {
				return;
			}
			// On a first start, the layout is the initial one already and resetting it changes nothing.
			IWorkbenchPage page = window.getActivePage();
			if (page != null && page.getPerspective() != null
					&& Perspective.ID_PERSPECTIVE.equals(page.getPerspective().getId())) {
				page.resetPerspective();
			}
			preferences.setValue(LAYOUT_VERSION_PREFERENCE, LAYOUT_VERSION);
		}

		/** How often (every 250 ms) the heap status is checked after the window opened. */
		private static final int HEAP_STATUS_CHECKS = 40;

		/** The ids of the status line and of the heap status in the window model (bottom trim bar). */
		private static final String STATUS_LINE_ID = "org.eclipse.ui.StatusLine";
		private static final String HEAP_STATUS_ID = "org.eclipse.ui.HeapStatus";

		/**
		 * Places the heap status at the right end of the status bar, as in the Eclipse IDE. The status line, which
		 * fills the bar, is added to the window model after the window opened, behind the heap status, which would
		 * thus stay at the left: once the status line exists, the heap status is moved behind it.
		 */
		private static void placeHeapStatus(IWorkbenchWindow window, int attempt) {
			if (window.getShell() == null || window.getShell().isDisposed()) {
				return;
			}
			MWindow model = window.getService(MWindow.class);
			EModelService models = window.getService(EModelService.class);
			MUIElement heap = model == null || models == null ? null : models.find(HEAP_STATUS_ID, model);
			MUIElement statusLine = model == null || models == null ? null : models.find(STATUS_LINE_ID, model);
			if (heap instanceof MTrimElement heapElement && statusLine != null
					&& (Object) heap.getParent() instanceof MTrimBar bar && (Object) statusLine.getParent() == bar) {
				org.eclipse.emf.common.util.EList<MTrimElement> children = (org.eclipse.emf.common.util.EList<MTrimElement>) bar
						.getChildren();
				int heapIndex = children.indexOf(heapElement);
				int statusIndex = children.indexOf(statusLine);
				if (heapIndex < statusIndex) {
					children.move(statusIndex, heapIndex);
				}
				// The renderer keeps the widgets of moved elements: order them as the model.
				if (heap.getWidget() instanceof org.eclipse.swt.widgets.Control heapControl
						&& statusLine.getWidget() instanceof org.eclipse.swt.widgets.Control statusControl
						&& heapControl.getParent() == statusControl.getParent()) {
					heapControl.moveBelow(statusControl);
					heapControl.getParent().layout(true);
				}
				return;
			}
			if (attempt < HEAP_STATUS_CHECKS) {
				window.getShell().getDisplay().timerExec(250, () -> placeHeapStatus(window, attempt + 1));
			}
		}
	}

	/**
	 * RCP's action bar advisor
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class WindowActionBarAdvisor extends ActionBarAdvisor {
		/**
		 * @see ActionBarAdvisor#ActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public WindowActionBarAdvisor(IActionBarConfigurer configurer) {
			super(configurer);
		}

		/**
		 * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		protected void fillMenuBar(IMenuManager menuBar) {
			IWorkbenchWindow window = getActionBarConfigurer().getWindowConfigurer().getWindow();
			menuBar.add(createFileMenu(window));
			menuBar.add(createEditMenu(window));
			menuBar.add(createNavigateMenu(window));
			menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			menuBar.add(createWindowMenu(window));
			menuBar.add(createHelpMenu(window));
		}

		/**
		 * Creates the 'File' menu.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected IMenuManager createFileMenu(IWorkbenchWindow window) {
			IMenuManager menu = new MenuManager(getString("_UI_Menu_File_label"), IWorkbenchActionConstants.M_FILE);
			menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));

			IMenuManager newMenu = new MenuManager(getString("_UI_Menu_New_label"), "new");
			newMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			newMenu.add(new Separator());
			// The new wizards of all plug-ins (File > New > Other..., Ctrl+N's dialog in the IDE).
			IAction newWizards = ActionFactory.NEW.create(window);
			newWizards.setText("&Other…");
			addToMenuAndRegister(newMenu, newWizards);

			menu.add(newMenu);
			menu.add(new Separator());
			menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(new Separator());
			// The import and export wizards of all plug-ins (CSV records, projects from Git, ...).
			IAction importWizards = ActionFactory.IMPORT.create(window);
			importWizards.setText("&Import…");
			addToMenuAndRegister(menu, importWizards);
			IAction exportWizards = ActionFactory.EXPORT.create(window);
			exportWizards.setText("E&xport…");
			addToMenuAndRegister(menu, exportWizards);
			menu.add(new GroupMarker(IWorkbenchActionConstants.IMPORT_EXT));
			menu.add(new Separator());
			// Handled by the views (the Projects view: files and folders).
			addToMenuAndRegister(menu, ActionFactory.RENAME.create(window));
			addToMenuAndRegister(menu, ActionFactory.REFRESH.create(window));
			menu.add(new Separator());
			addToMenuAndRegister(menu, ActionFactory.CLOSE.create(window));
			addToMenuAndRegister(menu, ActionFactory.CLOSE_ALL.create(window));
			menu.add(new Separator());
			addToMenuAndRegister(menu, ActionFactory.SAVE.create(window));
			addToMenuAndRegister(menu, ActionFactory.SAVE_AS.create(window));
			addToMenuAndRegister(menu, ActionFactory.SAVE_ALL.create(window));
			menu.add(new Separator());
			// Handled by the views (the Projects view: the property pages of projects, Alt+Enter).
			addToMenuAndRegister(menu, ActionFactory.PROPERTIES.create(window));
			menu.add(new Separator());
			addToMenuAndRegister(menu, ActionFactory.QUIT.create(window));
			menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_END));
			return menu;
		}

		/**
		 * Creates the 'Edit' menu.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected IMenuManager createEditMenu(IWorkbenchWindow window) {
			IMenuManager menu = new MenuManager(getString("_UI_Menu_Edit_label"), IWorkbenchActionConstants.M_EDIT);
			menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_START));

			addToMenuAndRegister(menu, ActionFactory.UNDO.create(window));
			addToMenuAndRegister(menu, ActionFactory.REDO.create(window));
			menu.add(new GroupMarker(IWorkbenchActionConstants.UNDO_EXT));
			menu.add(new Separator());

			addToMenuAndRegister(menu, ActionFactory.CUT.create(window));
			addToMenuAndRegister(menu, ActionFactory.COPY.create(window));
			addToMenuAndRegister(menu, ActionFactory.PASTE.create(window));
			menu.add(new GroupMarker(IWorkbenchActionConstants.CUT_EXT));
			menu.add(new Separator());

			addToMenuAndRegister(menu, ActionFactory.DELETE.create(window));
			addToMenuAndRegister(menu, ActionFactory.SELECT_ALL.create(window));
			menu.add(new Separator());
			// Find/Replace in the text pages (XML, JSON).
			addToMenuAndRegister(menu, ActionFactory.FIND.create(window));
			menu.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
			menu.add(new Separator());

			menu.add(new GroupMarker(IWorkbenchActionConstants.ADD_EXT));

			menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_END));
			menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			return menu;
		}

		/** The 'Navigate' menu: Show In (the document of the active editor in the Projects view, ...). */
		protected IMenuManager createNavigateMenu(IWorkbenchWindow window) {
			IMenuManager menu = new MenuManager("&Navigate", IWorkbenchActionConstants.M_NAVIGATE);
			menu.add(new GroupMarker(IWorkbenchActionConstants.NAV_START));
			MenuManager showIn = new MenuManager("Sho&w In", "showIn");
			showIn.add(ContributionItemFactory.VIEWS_SHOW_IN.create(window));
			menu.add(showIn);
			menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(new GroupMarker(IWorkbenchActionConstants.NAV_END));
			return menu;
		}

		/**
		 * Creates the 'Window' menu.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected IMenuManager createWindowMenu(IWorkbenchWindow window) {
			IMenuManager menu = new MenuManager(getString("_UI_Menu_Window_label"), IWorkbenchActionConstants.M_WINDOW);

			addToMenuAndRegister(menu, ActionFactory.OPEN_NEW_WINDOW.create(window));
			menu.add(new Separator());
			// The views and perspectives, as in the Eclipse IDE: Show View lists the views of the perspective and
			// Other... all views.
			MenuManager perspectives = new MenuManager("Open &Perspective", "openPerspective");
			perspectives.add(ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window));
			menu.add(perspectives);
			MenuManager views = new MenuManager("Show &View", "showView");
			views.add(ContributionItemFactory.VIEWS_SHORTLIST.create(window));
			menu.add(views);
			IAction reset = ActionFactory.RESET_PERSPECTIVE.create(window);
			reset.setText("&Reset Perspective…");
			addToMenuAndRegister(menu, reset);
			menu.add(new Separator());
			menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(ContributionItemFactory.OPEN_WINDOWS.create(window));

			menu.add(new Separator());
			IAction preferences = ActionFactory.PREFERENCES.create(window);
			preferences.setText("&Preferences\u2026");
			addToMenuAndRegister(menu, preferences);
			return menu;
		}

		/**
		 * Creates the 'Help' menu.
		 * <!-- begin-user-doc -->
		 * Starts with the help system's user guide, help search and context help.
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected IMenuManager createHelpMenu(IWorkbenchWindow window) {
			IMenuManager menu = new MenuManager(getString("_UI_Menu_Help_label"), IWorkbenchActionConstants.M_HELP);
			IAction contents = ActionFactory.HELP_CONTENTS.create(window);
			contents.setText("&User Guide");
			addToMenuAndRegister(menu, contents);
			IAction search = ActionFactory.HELP_SEARCH.create(window);
			search.setText("Searc&h Help…");
			addToMenuAndRegister(menu, search);
			IAction dynamic = ActionFactory.DYNAMIC_HELP.create(window);
			dynamic.setText("&Context Help");
			addToMenuAndRegister(menu, dynamic);
			menu.add(new Separator());
			menu.add(new GroupMarker(IWorkbenchActionConstants.HELP_START));
			menu.add(new GroupMarker(IWorkbenchActionConstants.HELP_END));
			menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			return menu;
		}

		/**
		 * Adds the specified action to the given menu and also registers the action with the
		 * action bar configurer, in order to activate its key binding.
		 * <!-- begin-user-doc -->
		 * Uses {@link #register(IAction)} so that the action can be reused in the tool bar and is disposed with the
		 * window.
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected void addToMenuAndRegister(IMenuManager menuManager, IAction action) {
			menuManager.add(action);
			if (getAction(action.getId()) == null) {
				register(action);
			} else {
				getActionBarConfigurer().registerGlobalAction(action);
			}
		}

		/**
		 * Fills the main tool bar: New, Open, Save, Undo, Redo and Validate.
		 */
		@Override
		protected void fillCoolBar(ICoolBarManager coolBar) {
			IWorkbenchWindow window = getActionBarConfigurer().getWindowConfigurer().getWindow();
			ISharedImages images = window.getWorkbench().getSharedImages();

			ToolBarManager toolBar = new ToolBarManager(coolBar.getStyle());
			toolBar.add(new GroupMarker("file"));
			toolBar.add(new NewDocumentToolAction(window, images));
			toolBar.add(new OpenToolAction(window, images));
			toolBar.add(withIcon(getOrCreateAction(ActionFactory.SAVE, window), "save"));
			toolBar.add(new Separator("edit"));
			toolBar.add(withIcon(getOrCreateAction(ActionFactory.UNDO, window), "undo"));
			toolBar.add(withIcon(getOrCreateAction(ActionFactory.REDO, window), "redo"));
			toolBar.add(new Separator("validation"));
			toolBar.add(new ValidateToolAction(window, images));
			toolBar.add(new GroupMarker("quality"));
			toolBar.add(new Separator("workspace"));
			toolBar.add(new ToolAction("library", "Show the metadata library", () -> showView(window,
					edu.louisiana.caai.lom.editor.library.LibraryView.ID)));
			toolBar.add(new ToolAction("import-csv", "Create records from the rows of a CSV file",
					() -> edu.louisiana.caai.lom.editor.importer.CsvImportWizard.open(window, null)));
			toolBar.add(new ToolAction("export-html", "Export the active record as an HTML catalog card",
					() -> edu.louisiana.caai.lom.editor.library.HtmlExport.exportActiveDocument(window)));
			toolBar.add(new ToolAction("guide", "Open the HLOM Guide",
					edu.louisiana.caai.lom.editor.help.HelpSupport::showGuide));
			toolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
			coolBar.add(new ToolBarContributionItem(toolBar, "edu.louisiana.caai.lom.editor.toolbar"));
		}

		/** Gives a workbench action the application's tool bar icon. */
		private static IAction withIcon(IAction action, String name) {
			ImageDescriptor icon = toolbarIcon(name);
			if (icon != null) {
				action.setImageDescriptor(icon);
				action.setDisabledImageDescriptor(ImageDescriptor.createWithFlags(icon, SWT.IMAGE_DISABLE));
			}
			return action;
		}

		private static void showView(IWorkbenchWindow window, String viewId) {
			try {
				if (window.getActivePage() != null) {
					window.getActivePage().showView(viewId);
				}
			} catch (PartInitException exception) {
				HlomEditorPlugin.INSTANCE.log(exception);
			}
		}

		/**
		 * Returns the workbench action registered by the menus, creating and registering it if necessary.
		 */
		private IAction getOrCreateAction(ActionFactory factory, IWorkbenchWindow window) {
			IAction action = getAction(factory.getId());
			if (action == null) {
				action = factory.create(window);
				register(action);
			}
			return action;
		}
	}

	/** An icon of the application's tool bar ({@code icons/toolbar/<name>.png}), or {@code null}. */
	static ImageDescriptor toolbarIcon(String name) {
		java.net.URL url = HlomEditorAdvisor.class.getResource("/icons/toolbar/" + name + ".png");
		return url == null ? null : ImageDescriptor.createFromURL(url);
	}

	/** A simple tool bar action with an application icon. */
	private static class ToolAction extends Action {
		private final Runnable runnable;

		ToolAction(String icon, String toolTip, Runnable runnable) {
			super(toolTip);
			this.runnable = runnable;
			setId("edu.louisiana.caai.lom.editor.toolbar." + icon);
			setToolTipText(toolTip);
			setImageDescriptor(toolbarIcon(icon));
		}

		@Override
		public void run() {
			runnable.run();
		}
	}

	/**
	 * Tool bar action that opens the new document wizard (of any MedBiquitous standard).
	 */
	private static class NewDocumentToolAction extends Action {
		private final IWorkbenchWindow window;

		NewDocumentToolAction(IWorkbenchWindow window, ISharedImages images) {
			super("New MedBiquitous Document");
			this.window = window;
			setId("edu.louisiana.caai.lom.editor.toolbar.new");
			setToolTipText("New MedBiquitous Document");
			setImageDescriptor(toolbarIcon("new"));
		}

		@Override
		public void run() {
			NewHlomDocumentWizard.open(window);
		}
	}

	/**
	 * Tool bar action that opens an existing document (same as File &gt; Open...).
	 */
	private static class OpenToolAction extends Action {
		private final IWorkbenchWindow window;

		OpenToolAction(IWorkbenchWindow window, ISharedImages images) {
			super(getString("_UI_Menu_Open_label"));
			this.window = window;
			setId("edu.louisiana.caai.lom.editor.toolbar.open");
			setToolTipText("Open MedBiquitous Document");
			setImageDescriptor(toolbarIcon("open"));
		}

		@Override
		public void run() {
			String[] filePaths = openFilePathDialog(window.getShell(), SWT.OPEN, null);
			if (filePaths.length > 0) {
				openEditor(window.getWorkbench(), URI.createFileURI(filePaths[0]));
			}
		}
	}

	/**
	 * Tool bar action that shows the validation view and validates the active document.
	 */
	private static class ValidateToolAction extends Action {
		private final IWorkbenchWindow window;

		ValidateToolAction(IWorkbenchWindow window, ISharedImages images) {
			super("&Validate");
			this.window = window;
			setId("edu.louisiana.caai.lom.editor.toolbar.validate");
			setToolTipText("Validate the active document and show the problems");
			setImageDescriptor(toolbarIcon("validate"));
		}

		@Override
		public void run() {
			IWorkbenchPage page = window.getActivePage();
			if (page == null) {
				return;
			}
			IEditorPart editor = page.getActiveEditor();
			try {
				page.showView(ValidationView.ID);
			} catch (PartInitException exception) {
				HlomEditorPlugin.INSTANCE.log(exception);
			}
			if (editor instanceof ValidationSource source) {
				source.validateNow();
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static String[] openFilePathDialog(Shell shell, int style, String[] fileExtensionFilters) {
		return openFilePathDialog(shell, style, fileExtensionFilters, (style & SWT.OPEN) != 0, (style & SWT.OPEN) != 0,
				(style & SWT.SAVE) != 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static String[] openFilePathDialog(Shell shell, int style, String[] fileExtensionFilters,
			boolean includeGroupFilter, boolean includeAllFilter, boolean addExtension) {
		FileDialog fileDialog = new FileDialog(shell, style);
		if (fileExtensionFilters == null) {
			fileExtensionFilters = FILE_EXTENSION_FILTERS;
		}

		// If requested, augment the file extension filters by adding a group of all the other filters (*.ext1;*.ext2;...)
		// at the beginning and/or an all files wildcard (*.*) at the end.
		//
		includeGroupFilter &= fileExtensionFilters.length > 1;
		int offset = includeGroupFilter ? 1 : 0;

		if (includeGroupFilter || includeAllFilter) {
			int size = fileExtensionFilters.length + offset + (includeAllFilter ? 1 : 0);
			String[] allFilters = new String[size];
			StringBuilder group = includeGroupFilter ? new StringBuilder() : null;

			for (int i = 0; i < fileExtensionFilters.length; i++) {
				if (includeGroupFilter) {
					if (i != 0) {
						group.append(';');
					}
					group.append(fileExtensionFilters[i]);
				}
				allFilters[i + offset] = fileExtensionFilters[i];
			}

			if (includeGroupFilter) {
				allFilters[0] = group.toString();
			}
			if (includeAllFilter) {
				allFilters[allFilters.length - 1] = "*.*";
			}

			fileDialog.setFilterExtensions(allFilters);
		} else {
			fileDialog.setFilterExtensions(fileExtensionFilters);
		}
		fileDialog.open();

		String[] filenames = fileDialog.getFileNames();
		String[] result = new String[filenames.length];
		String path = fileDialog.getFilterPath() + File.separator;
		String extension = null;

		// If extension adding requested, get the dotted extension corresponding to the selected filter.
		//
		if (addExtension) {
			int i = fileDialog.getFilterIndex();
			if (i != -1 && (!includeAllFilter || i != fileExtensionFilters.length)) {
				i = includeGroupFilter && i == 0 ? 0 : i - offset;
				String filter = fileExtensionFilters[i];
				int dot = filter.lastIndexOf('.');
				if (dot == 1 && filter.charAt(0) == '*') {
					extension = filter.substring(dot);
				}
			}
		}

		// Build the result by adding the selected path and, if needed, auto-appending the extension.
		//
		for (int i = 0; i < filenames.length; i++) {
			String filename = path + filenames[i];
			if (extension != null) {
				int dot = filename.lastIndexOf('.');
				if (dot == -1 || !Arrays.asList(fileExtensionFilters).contains("*" + filename.substring(dot))) {
					filename += extension;
				}
			}
			result[i] = filename;
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Documents of Hermes projects open as workspace files (see {@link EditorInputs}).
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public static boolean openEditor(IWorkbench workbench, URI uri) {
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		if (workbenchWindow == null && workbench.getWorkbenchWindowCount() > 0) {
			// The application isn't active (a file opened from the operating system).
			workbenchWindow = workbench.getWorkbenchWindows()[0];
		}
		if (workbenchWindow == null || workbenchWindow.getActivePage() == null) {
			return false;
		}
		IWorkbenchPage page = workbenchWindow.getActivePage();

		IEditorDescriptor editorDescriptor = EditUIUtil.getDefaultEditor(uri, null);
		if (editorDescriptor == null) {
			MessageDialog.openError(workbenchWindow.getShell(), getString("_UI_Error_title"),
					getString("_WARN_No_Editor", uri.lastSegment()));
			return false;
		} else {
			try {
				page.openEditor(EditorInputs.inputFor(uri), editorDescriptor.getId());
			} catch (PartInitException exception) {
				org.eclipse.jface.dialogs.ErrorDialog.openError(workbenchWindow.getShell(),
						getString("_UI_OpenEditorError_label"), null, exception.getStatus());
				return false;
			}
		}
		return true;
	}

	/**
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#getInitialWindowPerspectiveId()
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getInitialWindowPerspectiveId() {
		return Perspective.ID_PERSPECTIVE;
	}

	/**
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#initialize(org.eclipse.ui.application.IWorkbenchConfigurer)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * Also connects the completeness assessment to the library and HTML reports.
	 * @generated NOT
	 */
	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(true);
		IDE.registerAdapters();
		// The severity icons of the Problems view, which the IDE application would declare.
		declareImage(configurer, "IMG_OBJS_ERROR_PATH", "icons/actions/error.png");
		declareImage(configurer, "IMG_OBJS_WARNING_PATH", "icons/actions/warning.png");
		declareImage(configurer, "IMG_OBJS_INFO_PATH", "icons/actions/info.png");
		edu.louisiana.caai.lom.model.support.report.HlomLibrary
				.setCompletenessScorer(root -> edu.louisiana.caai.lom.model.support.HlomCompleteness.assess(root).score());
		Runnable configureMesh = () -> edu.louisiana.caai.lom.model.support.mesh.MeshClient.configureShared(
				edu.louisiana.caai.lom.editor.preferences.HlomPreferences.getMeshBaseUrl(),
				edu.louisiana.caai.lom.editor.preferences.HlomPreferences.isMeshEnabled());
		configureMesh.run();
		edu.louisiana.caai.lom.editor.preferences.HlomPreferences.addListener(event -> configureMesh.run());
	}

	private static void declareImage(IWorkbenchConfigurer configurer, String symbolicName, String path) {
		java.net.URL url = HlomEditorAdvisor.class.getResource("/" + path);
		if (url != null) {
			configurer.declareImage(symbolicName, ImageDescriptor.createFromURL(url), true);
		}
	}

	/** The workspace is the input of the pages (for the Problems view and other resource views). */
	@Override
	public IAdaptable getDefaultPageInput() {
		return HermesWorkspace.root();
	}

	/** Saves the workspace (projects, markers) after the workbench closed. */
	@Override
	public void postShutdown() {
		HermesWorkspace.save();
		super.postShutdown();
	}

	/**
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#createWorkbenchWindowAdvisor(org.eclipse.ui.application.IWorkbenchWindowConfigurer)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		return new WindowAdvisor(configurer);
	}

	/**
	 * Opens the HLOM documents (*.xml, *.lom) passed as command line arguments.
	 */
	@Override
	public void postStartup() {
		super.postStartup();
		HermesWorkspace.startup();
		edu.louisiana.caai.lom.editor.update.UpdateSites.register();
		edu.louisiana.caai.lom.editor.preferences.UmlsKey.configure();
		IWorkbench workbench = getWorkbenchConfigurer().getWorkbench();
		if (workbench.getActiveWorkbenchWindow() == null) {
			return;
		}
		for (String argument : Platform.getApplicationArgs()) {
			if (argument != null && !argument.isEmpty() && !argument.startsWith("-")) {
				openFile(workbench, argument);
			}
		}
	}

	/** Files to open from the operating system ({@link SWT#OpenDocument}), opened when the workbench is idle. */
	private static final java.util.Queue<String> FILES_TO_OPEN = new java.util.concurrent.ConcurrentLinkedQueue<>();

	/** Opens the files the operating system asked to open, once the workbench window exists. */
	@Override
	public void eventLoopIdle(Display display) {
		IWorkbench workbench = getWorkbenchConfigurer().getWorkbench();
		if (!FILES_TO_OPEN.isEmpty() && workbench.getWorkbenchWindowCount() > 0 && !workbench.isClosing()) {
			for (String path = FILES_TO_OPEN.poll(); path != null; path = FILES_TO_OPEN.poll()) {
				if (openFile(workbench, path)) {
					Shell shell = workbench.getWorkbenchWindows()[0].getShell();
					if (shell.getMinimized()) {
						shell.setMinimized(false);
					}
					shell.forceActive();
				}
			}
		}
		super.eventLoopIdle(display);
	}

	/** Opens a document file (.xml, .json or .lom); whether it was opened. */
	private static boolean openFile(IWorkbench workbench, String path) {
		String name = path.toLowerCase(Locale.ROOT);
		File file = new File(path);
		return (name.endsWith(".xml") || name.endsWith(".json") || name.endsWith(".lom")) && file.isFile()
				&& openEditor(workbench, URI.createFileURI(file.getAbsolutePath()));
	}
}
