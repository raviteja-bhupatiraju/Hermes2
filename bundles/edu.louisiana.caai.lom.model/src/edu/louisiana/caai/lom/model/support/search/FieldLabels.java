/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Readable labels of element paths, such as {@code General › Keyword} for {@code /lom/general/keyword/string}. */
public final class FieldLabels {

	/** Elements that only wrap the value and are left out of labels. */
	private static final Set<String> WRAPPERS = Set.of("string", "value", "langstring", "dateTime", "duration");

	/** Names written in capitals. */
	private static final Set<String> ACRONYMS = Set.of("lom", "url", "uri", "id", "mesh", "cme", "ce");

	private FieldLabels() {
	}

	/** The label of a value at an element path (the root element is left out). */
	public static String label(List<String> names, String attribute) {
		List<String> parts = new ArrayList<>();
		for (int i = 1; i < names.size(); i++) {
			String name = names.get(i);
			if (i == names.size() - 1 && i > 1 && WRAPPERS.contains(name) && attribute == null) {
				continue;
			}
			parts.add(words(name));
		}
		if (attribute != null) {
			parts.add(words(attribute));
		}
		if (parts.isEmpty() && !names.isEmpty()) {
			parts.add(words(names.getFirst()));
		}
		return String.join(" › ", parts);
	}

	/** An XML name in words: {@code healthcareMetadata} becomes {@code Healthcare Metadata}. */
	public static String words(String name) {
		int colon = name.indexOf(':');
		String local = colon >= 0 ? name.substring(colon + 1) : name;
		StringBuilder words = new StringBuilder();
		for (int i = 0; i < local.length(); i++) {
			char c = local.charAt(i);
			if (c == '_' || c == '-' || c == '.') {
				words.append(' ');
				continue;
			}
			if (i > 0 && Character.isUpperCase(c) && Character.isLowerCase(local.charAt(i - 1))) {
				words.append(' ');
			}
			words.append(words.isEmpty() || words.charAt(words.length() - 1) == ' ' ? Character.toUpperCase(c) : c);
		}
		String result = words.toString().strip().replaceAll(" +", " ");
		return ACRONYMS.contains(result.toLowerCase(Locale.ROOT)) ? result.toUpperCase(Locale.ROOT) : result;
	}
}
