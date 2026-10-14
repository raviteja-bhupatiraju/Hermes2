/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import java.util.Locale;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

/**
 * The context help ids (F1) of the views, editor pages and dialogs, and of the fields of the form pages. The
 * contexts are defined in {@code contexts.xml} and {@code contexts_fields.xml} of the help bundle
 * ({@code edu.louisiana.caai.lom.help}).
 */
public final class HelpContexts {

	private static final String PREFIX = "edu.louisiana.caai.lom.editor.";

	public static final String PROJECTS_VIEW = PREFIX + "projects_view";
	public static final String VALIDATION_VIEW = PREFIX + "validation_view";
	public static final String LIBRARY_VIEW = PREFIX + "library_view";
	public static final String COMPARE_VIEW = PREFIX + "compare_view";
	public static final String GUIDE_VIEW = PREFIX + "guide_view";
	public static final String START_PAGE = PREFIX + "start_page";
	public static final String SEARCH_PAGE = PREFIX + "search_page";

	public static final String EDITOR = PREFIX + "editor";
	public static final String PAGE_TREE = PREFIX + "page_tree";
	public static final String PAGE_XML = PREFIX + "page_xml";
	public static final String PAGE_JSON = PREFIX + "page_json";

	public static final String NEW_DOCUMENT_WIZARD = PREFIX + "new_document_wizard";
	public static final String PROJECT_DIALOG = PREFIX + "project_dialog";
	public static final String CSV_IMPORT_WIZARD = PREFIX + "csv_import_wizard";
	public static final String TERMINOLOGY_LOOKUP = PREFIX + "terminology_lookup";
	public static final String VCARD_DIALOG = PREFIX + "vcard_dialog";
	public static final String TRANSLATIONS_DIALOG = PREFIX + "translations_dialog";
	public static final String FIX_ALL_DIALOG = PREFIX + "fix_all_dialog";

	public static final String PREFERENCES_GENERAL = PREFIX + "preferences_general";
	public static final String PREFERENCES_VALIDATION = PREFIX + "preferences_validation";
	public static final String PREFERENCES_MESH = PREFIX + "preferences_mesh";
	public static final String PREFERENCES_TERMINOLOGIES = PREFIX + "preferences_terminologies";

	private HelpContexts() {
	}

	/** The context of a form page, from its page id (such as {@code general} or {@code healthcareEducation}). */
	public static String page(String pageId) {
		return PREFIX + "page_" + id(pageId);
	}

	/** The context of a form field, from its help key in {@link edu.louisiana.caai.lom.model.support.FieldHelp}. */
	public static String field(String helpKey) {
		return PREFIX + "field_" + id(helpKey);
	}

	/** A context id part: letters, digits and underscores (camel case becomes snake case). */
	static String id(String key) {
		StringBuilder id = new StringBuilder();
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if (Character.isUpperCase(c) && i > 0 && Character.isLowerCase(key.charAt(i - 1))) {
				id.append('_');
			}
			id.append(Character.isLetterOrDigit(c) ? Character.toLowerCase(c) : '_');
		}
		return id.toString().toLowerCase(Locale.ROOT);
	}

	/** Sets the context help of a control (does nothing without a running workbench, e.g. in tests). */
	public static void set(Control control, String contextId) {
		if (control != null && !control.isDisposed() && PlatformUI.isWorkbenchRunning()) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(control, contextId);
		}
	}
}
