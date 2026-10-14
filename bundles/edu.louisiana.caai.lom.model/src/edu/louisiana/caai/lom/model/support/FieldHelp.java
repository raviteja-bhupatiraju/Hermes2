/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * The help texts of the fields of Healthcare LOM documents ({@code field-help.properties}): shown as tooltips and
 * context help in the editor, and as hovers by the language server. Keys are element names ({@code creditType}),
 * prefixed with {@code meta} for the elements of the meta-metadata category ({@code metaIdentifier}).
 */
public final class FieldHelp {

	private static final Properties TEXTS = load();

	private FieldHelp() {
	}

	/** The help text of a key, or {@code null}. */
	public static String text(String key) {
		return key == null ? null : TEXTS.getProperty(key);
	}

	/**
	 * The key of the help text of the element at a path (local names from the root), or {@code null} if there is
	 * none: the innermost element with a text.
	 */
	public static String keyOf(List<String> names) {
		boolean meta = names.contains("metaMetadata");
		for (int i = names.size() - 1; i >= 0; i--) {
			String name = names.get(i);
			if (meta) {
				String metaKey = "meta" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
				if (TEXTS.containsKey(metaKey)) {
					return metaKey;
				}
			}
			if (TEXTS.containsKey(name)) {
				return name;
			}
		}
		return null;
	}

	private static Properties load() {
		Properties properties = new Properties();
		try (InputStream stream = FieldHelp.class.getResourceAsStream("field-help.properties")) {
			if (stream != null) {
				try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
					properties.load(reader);
				}
			}
		} catch (IOException _) {
			// No help available.
		}
		return properties;
	}
}
