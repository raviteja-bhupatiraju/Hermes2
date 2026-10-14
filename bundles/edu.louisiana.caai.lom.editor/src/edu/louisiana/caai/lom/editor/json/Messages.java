/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.json;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.json.messages";

	public static String JsonContentAssistProcessor_NoProposalsAtThisPosition;
	public static String JsonContentAssistProcessor_TheHLOMJSONSchema;
	public static String JsonContentAssistProcessor_TheRecord;
	public static String JsonSourceEditor_ProposalsFromTheHealthcareLOM;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
