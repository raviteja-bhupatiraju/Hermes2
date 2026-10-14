/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;

/** Initializes the default values of {@link HlomPreferences}. */
public class HlomPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		HlomPreferences.initializeDefaults(DefaultScope.INSTANCE.getNode(HlomPreferences.QUALIFIER));
	}
}
