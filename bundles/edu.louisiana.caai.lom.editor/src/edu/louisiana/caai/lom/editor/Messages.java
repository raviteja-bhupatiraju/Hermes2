/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.messages";

	public static String Errors_At;
	public static String HlomMetadataEditor_AllElementsOfTheDocument;
	public static String HlomMetadataEditor_CannotSave;
	public static String HlomMetadataEditor_CouldNotBeSavedYour;
	public static String HlomMetadataEditor_DocumentTree;
	public static String HlomMetadataEditor_Edit;
	public static String HlomMetadataEditor_FileChanged;
	public static String HlomMetadataEditor_FileDeleted;
	public static String HlomMetadataEditor_IsReadOnlyYourChanges;
	public static String HlomMetadataEditor_ItMustBeAnObject;
	public static String HlomMetadataEditor_NotLoaded;
	public static String HlomMetadataEditor_Saved;
	public static String HlomMetadataEditor_Source;
	public static String HlomMetadataEditor_TheCanTBeApplied;
	public static String HlomMetadataEditor_TheDocumentCouldNotBe;
	public static String HlomMetadataEditor_TheDocumentHasNoRoot;
	public static String HlomMetadataEditor_TheDocumentInTheJSON;
	public static String HlomMetadataEditor_TheFileWasChangedOutside;
	public static String HlomMetadataEditor_TheFileWasChangedOutside2;
	public static String HlomMetadataEditor_TheFileWasDeletedOr;
	public static String HlomMetadataEditor_TheJSONTextHasErrors;
	public static String HlomMetadataEditor_TheRootElementMustBe;
	public static String HlomMetadataEditor_TheTextIsNotA;
	public static String HlomMetadataEditor_TheXMLSourceOfThe;
	public static String HlomMetadataEditor_TheXMLTextHasErrors;
	public static String HlomMetadataEditor_Tree;
	public static String HlomMetadataEditor_Validating;
	public static String NavigationPanel_Errors;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
