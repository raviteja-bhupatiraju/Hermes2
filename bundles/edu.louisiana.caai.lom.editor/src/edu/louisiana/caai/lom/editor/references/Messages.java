/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.references;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.references.messages";

	public static String ReferenceHandlers_AnotherHasThisIdentifier;
	public static String ReferenceHandlers_EnterTheIdentifier;
	public static String ReferenceHandlers_NewIdentifierOfTheWill;
	public static String ReferenceHandlers_OpenADocumentToFind;
	public static String ReferenceHandlers_OpenADocumentWithReferences;
	public static String ReferenceHandlers_References;
	public static String ReferenceHandlers_ReferringDocuments;
	public static String ReferenceHandlers_Rename;
	public static String ReferenceHandlers_RenameIdentifier;
	public static String ReferenceHandlers_SaveTheDocumentFirstIts;
	public static String ReferenceHandlers_SelectAnElementThatReferences;
	public static String ReferenceHandlers_TheIdentifierCanTContain;
	public static String ReferenceHandlers_TheIdentifierOfThisCan;
	public static String ReferenceHandlers_ThisDocumentHasNoIdentifier;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
