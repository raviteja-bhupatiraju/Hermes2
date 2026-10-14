/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.search;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.search.messages";

	public static String HermesSearchPage_AllFields;
	public static String HermesSearchPage_CaseSensitive;
	public static String HermesSearchPage_ChooseTheFieldsToSearch;
	public static String HermesSearchPage_ContainingTextInTheField;
	public static String HermesSearchPage_InvalidRegularExpression;
	public static String HermesSearchPage_NoMatchesOfWereFound;
	public static String HermesSearchPage_RegularEXpression;
	public static String HermesSearchPage_Replace;
	public static String HermesSearchPage_SearchIn;
	public static String HermesSearchPage_SelectedResources;
	public static String HermesSearchPage_WholeWord;
	public static String HermesSearchPage_WorkingSets;
	public static String HermesSearchQuery_In;
	public static String HermesSearchQuery_InvalidRegularExpression;
	public static String HermesSearchQuery_Searching;
	public static String HermesSearchResultPage_ReplaceAll;
	public static String HermesSearchResultPage_ReplaceSelected;
	public static String ReferencesSearchQuery_ReferencesToIn;
	public static String ReferringDocumentsQuery_DocumentsReferringTo;
	public static String ReplaceRefactoring_CanTBeRead;
	public static String ReplaceRefactoring_ComputingTheReplacements;
	public static String ReplaceRefactoring_NoLongerExists;
	public static String ReplaceRefactoring_NoValueChangesTheReplacement;
	public static String ReplaceRefactoring_Replace;
	public static String ReplaceRefactoring_ThereAreNoMatchesTo;
	public static String ReplaceWizard_OnlyTheMatchedValuesChange;
	public static String ReplaceWizard_RegularExpression12Insert;
	public static String ReplaceWizard_ReplaceInDocuments;
	public static String ReplaceWizard_With;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
