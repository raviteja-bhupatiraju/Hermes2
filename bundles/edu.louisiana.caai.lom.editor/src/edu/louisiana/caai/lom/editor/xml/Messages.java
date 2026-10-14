/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.xml.messages";

	public static String XmlChecker_CheckingTheXML;
	public static String XmlContentAssistProcessor_Also;
	public static String XmlContentAssistProcessor_EndTag;
	public static String XmlContentAssistProcessor_HLOMDocument;
	public static String XmlContentAssistProcessor_LomXmlnsXmlnsXsiHttp;
	public static String XmlContentAssistProcessor_NoProposalsAtThisPosition;
	public static String XmlContentAssistProcessor_TheRootElementOfA;
	public static String XmlContentAssistProcessor_TheRootElementOfA2;
	public static String XmlDocumentation_BrBrBValuesB;
	public static String XmlDocumentation_HealthcareExtension;
	public static String XmlDocumentation_InAllCtrlSpaceLists;
	public static String XmlDocumentation_InSchemaOrder;
	public static String XmlDocumentation_NbspI;
	public static String XmlDocumentation_NbspINotDefinedBy;
	public static String XmlQuickAssistProcessor_ApplyTheXMLChangesTo;
	public static String XmlQuickAssistProcessor_Problem;
	public static String XmlQuickAssistProcessor_TheProblemsReferToThe;
	public static String XmlReferenceHyperlinks_Document;
	public static String XmlReferenceHyperlinks_GoTo;
	public static String XmlReferenceHyperlinks_Open;
	public static String XmlSourceEditor_QuickFix;
	public static String XmlSourceEditor_ToggleCoMment;
	public static String XmlSourceViewerConfiguration_ProposalsFromTheSchemas;
	public static String XmlSourceViewerConfiguration_QuickFixesOfTheProblems;
	public static String XmlTerminology_XmlnsHx;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
