/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.terminology;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.terminology.messages";

	public static String TerminologyLookupDialog_Code;
	public static String TerminologyLookupDialog_Definition;
	public static String TerminologyLookupDialog_EGMyocardialInfarctionSepsis;
	public static String TerminologyLookupDialog_Hierarchy;
	public static String TerminologyLookupDialog_Loading;
	public static String TerminologyLookupDialog_Loading2;
	public static String TerminologyLookupDialog_Name;
	public static String TerminologyLookupDialog_NoMatchesFor;
	public static String TerminologyLookupDialog_Search;
	public static String TerminologyLookupDialog_Searching;
	public static String TerminologyLookupDialog_SearchingOnline;
	public static String TerminologyLookupDialog_SeeAlso;
	public static String TerminologyLookupDialog_SynonymsAndEntryTerms;
	public static String TerminologyLookupDialog_Terminology;
	public static String TerminologyLookupDialog_TreeNumbers;
	public static String TerminologyLookupDialog_TypeANameOrCode;
	public static String TerminologySupport_Add;
	public static String TerminologySupport_AddCodedKeywords;
	public static String TerminologySupport_SetTaxonPath;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
