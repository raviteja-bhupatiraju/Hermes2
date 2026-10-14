/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;

/**
 * Entry points of the in-app help: the {@link GuideView HLOM Guide}, contextual help for form fields, the
 * specification PDF and the MedBiquitous website.
 * <p>
 * Form fields declare their topic with {@link #setHelpKey(Control, String)}: F1 shows the field's context help
 * (Help view) and Shift+F1 opens the guide at that topic.
 */
public final class HelpSupport {

	/** Control data key holding the help topic of a control (an element key such as {@code creditType}). */
	public static final String HELP_KEY = "edu.louisiana.caai.lom.editor.help.key";

	/** The Healthcare LOM page of the MedBiquitous website. */
	public static final String WEBSITE = "https://www.medbiq.org/standards/healthcare-learning-object-metadata";

	private HelpSupport() {
	}

	/** Opens (or activates) the HLOM Guide in the active window; returns {@code null} if there is none. */
	public static GuideView showGuide() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null) {
			return null;
		}
		try {
			return (GuideView) page.showView(GuideView.ID);
		} catch (PartInitException exception) {
			HlomEditorPlugin.INSTANCE.log(exception);
			return null;
		}
	}

	/**
	 * Opens the HLOM Guide at the topic of an element key as used in {@link edu.louisiana.caai.lom.model.support.FieldHelp} (e.g.
	 * {@code creditType}, {@code keyword}), or at any other topic id accepted by {@link GuideView#showTopic(String)}.
	 */
	public static void showHelp(String elementKey) {
		GuideView view = showGuide();
		if (view != null) {
			view.showTopic(elementKey);
		}
	}

	/**
	 * Declares the help topic of a control (and its children): the HLOM Guide topic for {@link #showContextHelp()}
	 * and the field's context of the help system (F1).
	 */
	public static void setHelpKey(Control control, String elementKey) {
		if (elementKey == null || elementKey.startsWith(edu.louisiana.caai.lom.editor.forms.FormSupport.LITERAL_HELP)) {
			return; // Fields of other standards: their help text is the tooltip.
		}
		control.setData(HELP_KEY, elementKey);
		HelpContexts.set(control, HelpContexts.field(elementKey));
	}

	/** The help topic of a control or of its nearest ancestor that has one, or {@code null}. */
	public static String findHelpKey(Control control) {
		for (Control current = control; current != null; current = current.getParent()) {
			if (current.getData(HELP_KEY) instanceof String key) {
				return key;
			}
		}
		return null;
	}

	/** Opens the guide at the topic of the focused control, or at the overview. */
	public static void showContextHelp() {
		Display display = Display.getCurrent();
		String key = display == null ? null : findHelpKey(display.getFocusControl());
		showHelp(key);
	}

	/** Opens the bundled specification PDF with the system's PDF viewer. */
	public static void openSpecification(Shell shell) {
		try {
			File pdf = HelpContent.file(HelpContent.SPECIFICATION);
			if (!Program.launch(pdf.getAbsolutePath())) {
				MessageDialog.openInformation(shell, Messages.HelpSupport_Specification,
						NLS.bind(Messages.HelpSupport_NoApplicationIsRegisteredTo, pdf.getAbsolutePath()));
			}
		} catch (IOException exception) {
			Errors.show(shell, Messages.HelpSupport_Specification, Messages.HelpSupport_TheSpecificationCouldNotBe, exception);
		}
	}

	/** Opens the MedBiquitous Healthcare LOM web page in the system browser. */
	public static void openWebsite() {
		Program.launch(WEBSITE);
	}
}
