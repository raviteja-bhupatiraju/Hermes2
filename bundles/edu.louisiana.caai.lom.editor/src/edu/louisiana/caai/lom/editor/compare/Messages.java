/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.compare.messages";

	public static String CompareActions_CompareWith;
	public static String CompareActions_OpenAMedBiquitousDocumentTo;
	public static String CompareActions_OpenAMedBiquitousDocumentTo2;
	public static String CompareActions_SelectTwoMedBiquitousDocuments;
	public static String CompareHeader_Identical;
	public static String CompareSide_SavedOnDisk;
	public static String CompareView_CollapseAll;
	public static String CompareView_CompareMedBiquitousDocuments;
	public static String CompareView_CompareTwoFiles;
	public static String CompareView_CompareWithFile;
	public static String CompareView_CompareWithSavedVersion;
	public static String CompareView_CopyDifferencesAsText;
	public static String CompareView_CouldNotBeReadAs;
	public static String CompareView_DifferencesCopiedToTheClipboard;
	public static String CompareView_DoubleClickAFieldTo;
	public static String CompareView_ExpandAll;
	public static String CompareView_Field;
	public static String CompareView_FilterFieldsAndValues;
	public static String CompareView_Left;
	public static String CompareView_NoFieldsMatchTheFilter;
	public static String CompareView_OpenTheDocumentInThe;
	public static String CompareView_RefreshReReadFiles;
	public static String CompareView_Right;
	public static String CompareView_ShowUnchangedFields;
	public static String CompareView_SwapLeftAndRight;
	public static String CompareView_TheDocumentsAreOfDifferent;
	public static String SemanticCompareViewer_ChangedAddedRemoved;
	public static String SemanticCompareViewer_Field;
	public static String SemanticCompareViewer_ShowUnchangedFields;
	public static String SemanticCompareViewer_TheDocumentsCouldNotBe;
	public static String SemanticCompareViewer_TheDocumentsHaveTheSame;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
