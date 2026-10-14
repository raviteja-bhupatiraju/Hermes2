/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.validation;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.validation.messages";

	public static String ValidationView_Check;
	public static String ValidationView_CopyMessage;
	public static String ValidationView_Description;
	public static String ValidationView_Element;
	public static String ValidationView_FixAll;
	public static String ValidationView_GoToElement;
	public static String ValidationView_HasAQuickFix;
	public static String ValidationView_HaveQuickFixes;
	public static String ValidationView_Line;
	public static String ValidationView_NoQuickFixes;
	public static String ValidationView_QuickFix;
	public static String ValidationView_QuickFixAvailableCtrl1;
	public static String ValidationView_QuickFixCtrl1;
	public static String ValidationView_QuickFixesCtrl1;
	public static String ValidationView_ValidateNow;
	public static String ValidationView_ValidateTheDocumentNow;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
