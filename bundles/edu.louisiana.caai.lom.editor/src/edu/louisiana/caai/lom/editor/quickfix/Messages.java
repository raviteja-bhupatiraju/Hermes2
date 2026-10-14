/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.quickfix.messages";

	public static String FixAllDialog_ApplyFixes;
	public static String FixAllDialog_DeselectAll;
	public static String FixAllDialog_Fix;
	public static String FixAllDialog_FixAll;
	public static String FixAllDialog_FixAutomatically;
	public static String FixAllDialog_Problem;
	public static String FixAllDialog_ProblemsThatNeedYourInput;
	public static String FixAllDialog_SelectAll;
	public static String FixAllDialog_TheSelectedFixesAreApplied;
	public static String ProblemResolutions_ApplyingQuickFixes;
	public static String QuickFixPopup_EnterToApplyEscTo;
	public static String QuickFixPopup_NoQuickFixesAvailable;
	public static String QuickFixPopup_QuickFix;
	public static String QuickFixes_FixAll;
	public static String QuickFixes_GoToElement;
	public static String QuickFixes_NoneOfTheCurrentProblems;
	public static String QuickFixes_QuickFix;
	public static String QuickFixes_ShowsTheElementTheProblem;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
