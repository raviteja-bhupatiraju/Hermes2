/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.workspace.messages";

	public static String HermesBuilder_Validating;
	public static String HermesWorkspace_Project;
	public static String HermesWorkspace_RecognizingMedBiquitousProjects;
	public static String HermesWorkspace_ValidatingHealthcareLOMProjects;
	public static String HermesWorkspace_ValidatingMedBiquitousProjects;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
