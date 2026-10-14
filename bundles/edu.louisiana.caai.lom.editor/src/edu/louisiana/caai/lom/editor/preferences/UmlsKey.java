/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import java.io.IOException;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.model.support.mesh.UmlsClient;

/**
 * The API key of the user's UMLS license, which gives access to SNOMED CT and LOINC online. It is kept encrypted in
 * the Eclipse secure storage (protected by the operating system's key store where there is one); a plain preference
 * only records that a key is set, so that offering the online terminologies never reads the secure storage.
 */
public final class UmlsKey {

	private static final String NODE = "edu.louisiana.caai.lom.editor/umls";
	private static final String KEY = "apiKey";

	/** The key once read in this session. */
	private static volatile String cached;

	private UmlsKey() {
	}

	/** Makes the UMLS client use the stored key (read on its first search). */
	public static void configure() {
		UmlsClient.configureShared(UmlsKey::get, UmlsKey::isSet);
	}

	/** Whether a key is set (without reading it). */
	public static boolean isSet() {
		return HlomPreferences.store().getBoolean(HlomPreferences.UMLS_KEY_SET);
	}

	/** The stored key, or {@code null}. */
	public static String get() {
		String key = cached;
		if (key != null || !isSet()) {
			return key;
		}
		try {
			key = node().get(KEY, null);
		} catch (StorageException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return null;
		}
		cached = key;
		return key;
	}

	/** Stores a key (encrypted); a blank key removes it. */
	public static void set(String key) throws StorageException, IOException {
		ISecurePreferences node = node();
		if (key == null || key.isBlank()) {
			node.remove(KEY);
			cached = null;
			HlomPreferences.store().setValue(HlomPreferences.UMLS_KEY_SET, false);
		} else {
			node.put(KEY, key.strip(), true);
			cached = key.strip();
			HlomPreferences.store().setValue(HlomPreferences.UMLS_KEY_SET, true);
		}
		node.flush();
	}

	private static ISecurePreferences node() {
		return SecurePreferencesFactory.getDefault().node(NODE);
	}
}
