/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.handlers;

import java.util.function.Consumer;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.jface.window.Window;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.compare.CompareActions;
import edu.louisiana.caai.lom.editor.importer.CsvImportWizard;
import edu.louisiana.caai.lom.editor.library.HtmlExport;
import edu.louisiana.caai.lom.editor.projects.ProjectActions;
import edu.louisiana.caai.lom.editor.search.HermesSearchPage;
import edu.louisiana.caai.lom.editor.wizards.NewHlomDocumentWizard;

/**
 * The handlers of the application's window commands (menus File, Search and the tool bar; see plugin.xml). Each
 * runs an operation on the window the command was executed in.
 */
public final class WindowHandlers {

	private WindowHandlers() {
	}

	/** A handler that runs an operation on the active window. */
	private abstract static class WindowHandler extends AbstractHandler {
		private final Consumer<IWorkbenchWindow> operation;

		WindowHandler(Consumer<IWorkbenchWindow> operation) {
			this.operation = operation;
		}

		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			operation.accept(HandlerUtil.getActiveWorkbenchWindowChecked(event));
			return null;
		}
	}

	/** File &gt; New &gt; MedBiquitous Document (Ctrl+N). */
	public static final class NewDocument extends WindowHandler {
		public NewDocument() {
			super(NewHlomDocumentWizard::open);
		}
	}

	/** File &gt; Open... (Ctrl+O): opens a document chosen in the file dialog. */
	public static final class Open extends WindowHandler {
		public Open() {
			super(window -> {
				String[] filePaths = HlomEditorAdvisor.openFilePathDialog(window.getShell(), SWT.OPEN, null);
				if (filePaths.length > 0) {
					HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(filePaths[0]));
				}
			});
		}
	}

	/** File &gt; Open URI... (Ctrl+U): opens the documents of the URIs entered in EMF's Load Resource dialog. */
	public static final class OpenUri extends WindowHandler {
		public OpenUri() {
			super(window -> {
				LoadResourceAction.LoadResourceDialog dialog = new LoadResourceAction.LoadResourceDialog(window.getShell());
				if (dialog.open() == Window.OK) {
					for (URI uri : dialog.getURIs()) {
						HlomEditorAdvisor.openEditor(window.getWorkbench(), uri);
					}
				}
			});
		}
	}

	/** File &gt; New &gt; Project... */
	public static final class NewProject extends WindowHandler {
		public NewProject() {
			super(ProjectActions::newProject);
		}
	}

	/** File &gt; Open Project... */
	public static final class OpenProject extends WindowHandler {
		public OpenProject() {
			super(ProjectActions::openProject);
		}
	}

	/** File &gt; Compare &gt; Compare With Saved Version. */
	public static final class CompareWithSaved extends WindowHandler {
		public CompareWithSaved() {
			super(CompareActions::compareWithSaved);
		}
	}

	/** File &gt; Compare &gt; Compare With File... */
	public static final class CompareWithFile extends WindowHandler {
		public CompareWithFile() {
			super(CompareActions::compareWithFile);
		}
	}

	/** File &gt; Compare &gt; Compare Two Files... */
	public static final class CompareFiles extends WindowHandler {
		public CompareFiles() {
			super(CompareActions::compareFiles);
		}
	}

	/** File &gt; Export as HTML... */
	public static final class ExportHtml extends WindowHandler {
		public ExportHtml() {
			super(HtmlExport::exportActiveDocument);
		}
	}

	/** File &gt; Import from CSV... */
	public static final class ImportCsv extends WindowHandler {
		public ImportCsv() {
			super(window -> CsvImportWizard.open(window, null));
		}
	}

	/** Search &gt; Search Documents... (Ctrl+H): the search dialog on the page of the Hermes documents. */
	public static final class OpenSearch extends WindowHandler {
		public OpenSearch() {
			super(window -> NewSearchUI.openSearchDialog(window, HermesSearchPage.ID));
		}
	}
}
