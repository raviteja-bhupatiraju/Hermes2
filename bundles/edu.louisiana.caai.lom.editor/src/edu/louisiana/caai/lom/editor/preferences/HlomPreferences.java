/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import java.io.File;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import edu.louisiana.caai.lom.editor.pages.VCardDialog;
import edu.louisiana.caai.lom.model.support.HlomDocuments;

/**
 * Typed access to the preferences of Hermes 2 (instance scope
 * {@value #QUALIFIER}). Changes made in <i>Window &gt; Preferences</i> are reported to listeners added with
 * {@link #addListener(IPropertyChangeListener)}.
 */
public final class HlomPreferences {

	/** The preference node (the editor bundle's symbolic name). */
	public static final String QUALIFIER = "edu.louisiana.caai.lom.editor";

	/** Language of new documents and new text values (string, e.g. {@code en-US}). */
	public static final String DEFAULT_LANGUAGE = "defaultLanguage";
	/** Full name of the user (string). */
	public static final String AUTHOR_NAME = "author.name";
	/** Organization of the user (string). */
	public static final String AUTHOR_ORGANIZATION = "author.organization";
	/** Email address of the user (string). */
	public static final String AUTHOR_EMAIL = "author.email";
	/** Whether new documents get the user as author and meta-metadata creator (boolean). */
	public static final String ADD_AUTHOR_TO_NEW_DOCUMENTS = "author.addToNewDocuments";
	/** Whether documents are validated after each change (boolean). */
	public static final String VALIDATE_ON_CHANGE = "validation.onChange";
	/** Delay between the last change and the validation in milliseconds (int). */
	public static final String VALIDATION_DELAY = "validation.delay";
	/** Whether problems of severity INFO are shown (boolean). */
	public static final String SHOW_INFO_PROBLEMS = "validation.showInfo";
	/** Whether MeSH descriptors can be looked up online (boolean). */
	public static final String MESH_ENABLED = "mesh.enabled";
	/** Base URL of the NLM MeSH RDF/lookup service, ending with a slash (string). */
	public static final String MESH_BASE_URL = "mesh.baseUrl";

	/** Whether a UMLS API key is in the secure storage (see {@link UmlsKey}). */
	public static final String UMLS_KEY_SET = "umls.keySet";

	/** The address of the update site (p2 repository) of the application, or empty. */
	public static final String UPDATE_SITE = "updates.site";
	/** Folder of the document library (string, absolute path). */
	public static final String LIBRARY_FOLDER = "library.folder";
	/** Whether new documents are created in the JSON form instead of XML (boolean). */
	public static final String NEW_DOCUMENTS_JSON = "newDocuments.json";
	/** The project folders shown in the Projects view (string, absolute paths separated by line breaks). */
	public static final String PROJECTS = "projects";

	/** Default of {@link #VALIDATION_DELAY}. */
	public static final int DEFAULT_VALIDATION_DELAY = 600;
	/** Default of {@link #MESH_BASE_URL}. */
	public static final String DEFAULT_MESH_BASE_URL = "https://id.nlm.nih.gov/mesh/";

	private static ScopedPreferenceStore store;

	private HlomPreferences() {
	}

	/** The preference store of the editor (shared). */
	public static synchronized IPreferenceStore store() {
		if (store == null) {
			store = new ScopedPreferenceStore(InstanceScope.INSTANCE, QUALIFIER);
		}
		return store;
	}

	/** Writes the default values into a default-scope node; called by {@link HlomPreferenceInitializer}. */
	static void initializeDefaults(IEclipsePreferences node) {
		node.put(DEFAULT_LANGUAGE, HlomDocuments.DEFAULT_LANGUAGE);
		node.put(AUTHOR_NAME, "");
		node.put(AUTHOR_ORGANIZATION, "");
		node.put(AUTHOR_EMAIL, "");
		node.putBoolean(ADD_AUTHOR_TO_NEW_DOCUMENTS, true);
		node.putBoolean(VALIDATE_ON_CHANGE, true);
		node.putInt(VALIDATION_DELAY, DEFAULT_VALIDATION_DELAY);
		node.putBoolean(SHOW_INFO_PROBLEMS, true);
		// MeSH lookups use the offline index; the service is only an opt-in fallback for unknown descriptor IDs.
		node.putBoolean(MESH_ENABLED, false);
		node.put(MESH_BASE_URL, DEFAULT_MESH_BASE_URL);
		node.put(LIBRARY_FOLDER, defaultLibraryFolder().getAbsolutePath());
		node.putBoolean(NEW_DOCUMENTS_JSON, false);
		node.put(PROJECTS, "");
	}

	/**
	 * The default library folder: {@code Documents/MedBiquitous} in the user's home, or the folder of earlier
	 * versions ({@code Documents/Healthcare LOM}) if only that one exists.
	 */
	public static File defaultLibraryFolder() {
		File home = new File(System.getProperty("user.home"));
		File documents = new File(home, Messages.HlomPreferences_Documents);
		File parent = documents.isDirectory() ? documents : home;
		File folder = new File(parent, Messages.HlomPreferences_MedBiquitous);
		File earlier = new File(parent, Messages.HlomPreferences_HealthcareLOM);
		return !folder.exists() && earlier.isDirectory() ? earlier : folder;
	}

	/** Language of new documents, e.g. {@code en-US}. */
	public static String getDefaultLanguage() {
		String language = store().getString(DEFAULT_LANGUAGE).strip();
		return language.isEmpty() ? HlomDocuments.DEFAULT_LANGUAGE : language;
	}

	/** The user's full name, empty if not set. */
	public static String getAuthorName() {
		return store().getString(AUTHOR_NAME).strip();
	}

	/** The user's organization, empty if not set. */
	public static String getAuthorOrganization() {
		return store().getString(AUTHOR_ORGANIZATION).strip();
	}

	/** The user's email address, empty if not set. */
	public static String getAuthorEmail() {
		return store().getString(AUTHOR_EMAIL).strip();
	}

	/** Whether the user should be added as author and meta-metadata creator of new documents. */
	public static boolean isAddAuthorToNewDocuments() {
		return store().getBoolean(ADD_AUTHOR_TO_NEW_DOCUMENTS);
	}

	/**
	 * The user as vCard 3.0 text (FN, ORG, EMAIL), or {@code null} if neither name nor organization is set.
	 */
	public static String getAuthorVCard() {
		String name = getAuthorName();
		String organization = getAuthorOrganization();
		if (name.isEmpty() && organization.isEmpty()) {
			return null;
		}
		return VCardDialog.compose(name.isEmpty() ? organization : name, organization, getAuthorEmail());
	}

	/**
	 * The vCard to add as contributor of new documents: {@link #getAuthorVCard()} if
	 * {@link #isAddAuthorToNewDocuments()} is set, otherwise {@code null}.
	 */
	public static String getNewDocumentContributor() {
		return isAddAuthorToNewDocuments() ? getAuthorVCard() : null;
	}

	/** Whether documents are validated automatically after changes. */
	public static boolean isValidateOnChange() {
		return store().getBoolean(VALIDATE_ON_CHANGE);
	}

	/** Delay between the last change and the automatic validation, in milliseconds (at least 50). */
	public static int getValidationDelay() {
		return Math.max(50, store().getInt(VALIDATION_DELAY));
	}

	/** Whether problems of severity INFO are shown. */
	public static boolean isShowInfoProblems() {
		return store().getBoolean(SHOW_INFO_PROBLEMS);
	}

	/** The address of the update site, or an empty string. */
	public static String getUpdateSite() {
		return store().getString(UPDATE_SITE).strip();
	}

	/** Whether online MeSH lookup is enabled. */
	public static boolean isMeshEnabled() {
		return store().getBoolean(MESH_ENABLED);
	}

	/** Base URL of the MeSH service, always ending with a slash. */
	public static String getMeshBaseUrl() {
		String url = store().getString(MESH_BASE_URL).strip();
		if (url.isEmpty()) {
			url = DEFAULT_MESH_BASE_URL;
		}
		return url.endsWith("/") ? url : url + "/";
	}

	/** Whether new documents are created in the JSON form. */
	public static boolean isJsonDefaultFormat() {
		return store().getBoolean(NEW_DOCUMENTS_JSON);
	}

	/** The folders of the open projects, in the order they were added. */
	public static java.util.List<File> getProjects() {
		java.util.List<File> result = new java.util.ArrayList<>();
		for (String line : store().getString(PROJECTS).split("\\R")) {
			if (!line.isBlank()) {
				result.add(new File(line.strip()));
			}
		}
		return result;
	}

	/** Stores the folders of the open projects. */
	public static void setProjects(java.util.List<File> folders) {
		StringBuilder value = new StringBuilder();
		for (File folder : folders) {
			value.append(folder.getAbsolutePath()).append('\n');
		}
		store().setValue(PROJECTS, value.toString());
		save();
	}

	/** Writes the preferences to disk. */
	private static void save() {
		store();
		if (store.needsSaving()) {
			try {
				store.save();
			} catch (java.io.IOException e) {
				edu.louisiana.caai.lom.editor.HlomEditorPlugin.INSTANCE.log(e);
			}
		}
	}

	/** Adds a listener notified of preference changes (property = one of the keys of this class). */
	public static void addListener(IPropertyChangeListener listener) {
		store().addPropertyChangeListener(listener);
	}

	/** Removes a listener added with {@link #addListener(IPropertyChangeListener)}. */
	public static void removeListener(IPropertyChangeListener listener) {
		store().removePropertyChangeListener(listener);
	}
}
