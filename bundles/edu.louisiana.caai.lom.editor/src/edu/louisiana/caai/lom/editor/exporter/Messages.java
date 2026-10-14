/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.exporter;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.exporter.messages";

	public static String DocumentExportPage_BRowse;
	public static String DocumentExportPage_ChooseTheFolderToExport;
	public static String DocumentExportPage_Documents;
	public static String DocumentExportPage_HermesExport;
	public static String DocumentExportPage_SelectTheProjectsFoldersOr;
	public static String DocumentExportPage_TheDestinationIsAFile;
	public static String DocumentExportPage_ToFolder;
	public static String DocumentsExportWizard_AsJSONHealthcareLOMOther;
	public static String DocumentsExportWizard_AsXML;
	public static String DocumentsExportWizard_CopyDocumentsToAFolder;
	public static String DocumentsExportWizard_DocumentsAsXMLOrJSON;
	public static String DocumentsExportWizard_Export;
	public static String DocumentsExportWizard_ExportDocuments;
	public static String DocumentsExportWizard_ExportingDocuments;
	public static String DocumentsExportWizard_InTheirCurrentFormat;
	public static String DocumentsExportWizard_NotReadableRepairItFirst;
	public static String DocumentsExportWizard_OverwriteExistingFilesWithoutWarning;
	public static String DocumentsExportWizard_SkippedExists;
	public static String DocumentsExportWizard_TheDocumentsCouldNotBe;
	public static String HtmlReportsExportWizard_ACatalogCardPerDocument;
	public static String HtmlReportsExportWizard_ALibraryReportOfAll;
	public static String HtmlReportsExportWizard_ChooseTheCatalogCardsThe;
	public static String HtmlReportsExportWizard_Export;
	public static String HtmlReportsExportWizard_ExportAPrintableCatalogCard;
	public static String HtmlReportsExportWizard_ExportHTMLReports;
	public static String HtmlReportsExportWizard_ExportingHTMLReports;
	public static String HtmlReportsExportWizard_HTMLReports;
	public static String HtmlReportsExportWizard_OpenTheReportInThe;
	public static String HtmlReportsExportWizard_TheReportsCouldNotBe;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
