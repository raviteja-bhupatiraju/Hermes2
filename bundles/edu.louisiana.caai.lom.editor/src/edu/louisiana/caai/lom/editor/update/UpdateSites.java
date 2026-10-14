/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.update;

import org.eclipse.osgi.util.NLS;

import java.net.URI;
import java.util.Arrays;

import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.ui.ProvisioningUI;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.preferences.HlomPreferences;

/**
 * Registers the update site of Hermes 2 with p2, so that <em>Help &gt; Check for Updates</em> finds new versions of
 * the application and of the terminologies. The site is the system property {@value #PROPERTY} (for example in
 * {@code Hermes2.ini}) or the preference {@value HlomPreferences#UPDATE_SITE} (for example in a
 * {@code plugin_customization.ini}); without one, sites are added in <em>Preferences &gt; Install/Update &gt;
 * Available Software Sites</em>.
 */
public final class UpdateSites {

	/** The system property naming the update site. */
	public static final String PROPERTY = "hermes.update.site";

	private UpdateSites() {
	}

	/** The configured update site, or {@code null}. */
	public static String configured() {
		String site = System.getProperty(PROPERTY);
		if (site == null || site.isBlank()) {
			site = HlomPreferences.getUpdateSite();
		}
		return site == null || site.isBlank() ? null : site.strip();
	}

	/** Adds the configured update site to the known sites (once); does nothing without p2 (development runs). */
	public static void register() {
		String site = configured();
		if (site == null) {
			return;
		}
		try {
			P2.add(URI.create(site));
		} catch (LinkageError | IllegalArgumentException e) {
			// No p2 in this installation, or an invalid address: updates are not available.
			HlomEditorPlugin.INSTANCE.log(NLS.bind(Messages.UpdateSites_UpdateSiteNotRegistered, e));
		}
	}

	/** The p2 calls, loaded only when p2 is installed. */
	private static final class P2 {
		static void add(URI site) {
			ProvisioningUI ui = ProvisioningUI.getDefaultUI();
			RepositoryTracker tracker = ui.getRepositoryTracker();
			if (!Arrays.asList(tracker.getKnownRepositories(ui.getSession())).contains(site)) {
				tracker.addRepository(site, Messages.UpdateSites_Hermes2, ui.getSession());
			}
		}
	}
}
