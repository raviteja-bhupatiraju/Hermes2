/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.update;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.update.messages";

	public static String UpdateSites_Hermes2;
	public static String UpdateSites_UpdateSiteNotRegistered;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
