/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.mesh;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.mesh.messages";

	public static String AddMeshKeywordsAction_AddKeywordsFromTheMedical;
	public static String AddMeshKeywordsAction_AddMeSHKeywords;
	public static String MeshLookupDialog_AddKeywords;
	public static String MeshLookupDialog_AddMeSHKeywords;
	public static String MeshLookupDialog_AlreadyAKeywordOfThis;
	public static String MeshLookupDialog_Descriptor;
	public static String MeshLookupDialog_DescriptorsSelected;
	public static String MeshLookupDialog_EntryTerms;
	public static String MeshLookupDialog_FirstDescriptors;
	public static String MeshLookupDialog_LoadingDetails;
	public static String MeshLookupDialog_LoadingMeSHDetails;
	public static String MeshLookupDialog_MeSHDescriptor;
	public static String MeshLookupDialog_MeSHID;
	public static String MeshLookupDialog_MeSHIsTheNLMControlled;
	public static String MeshLookupDialog_MeSHLookup;
	public static String MeshLookupDialog_MedicalSubjectHeadings;
	public static String MeshLookupDialog_NoDetailsFoundForThis;
	public static String MeshLookupDialog_NoResultsFor;
	public static String MeshLookupDialog_OfflineCheckYourConnection;
	public static String MeshLookupDialog_ScopeNote;
	public static String MeshLookupDialog_SearchMeSHEGMyocardial;
	public static String MeshLookupDialog_SearchTheMedicalSubjectHeadings;
	public static String MeshLookupDialog_SearchingMeSH;
	public static String MeshLookupDialog_SearchingMeSH2;
	public static String MeshLookupDialog_SeeAlso;
	public static String MeshLookupDialog_SelectOneOrMoreAnd;
	public static String MeshLookupDialog_Selected;
	public static String MeshLookupDialog_SourceUSNationalLibrary;
	public static String MeshLookupDialog_TheMeSHServiceIsNot;
	public static String MeshLookupDialog_Tips;
	public static String MeshLookupDialog_TreeNumbers;
	public static String MeshLookupDialog_TypeAtLeastTwoCharacters;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
