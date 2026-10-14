/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.io.InputStream;

/**
 * The bundled copies of the published schemas ({@code schemas/<host>/<path>}, for example
 * {@code schemas/ns.medbiq.org/curriculuminventory/v10/curriculuminventory.xsd}), so that validation works offline
 * and never fetches anything from the network.
 */
public final class MedbiqSchemas {

	private MedbiqSchemas() {
	}

	/** The class path location of a schema URL, or {@code null} for URLs that are not bundled. */
	public static String location(String url) {
		String text = url.strip();
		int scheme = text.indexOf("://");
		if (scheme < 0) {
			return null;
		}
		String rest = text.substring(scheme + 3);
		if (rest.startsWith("dublincore.org/")) {
			rest = "www." + rest;
		}
		int fragment = rest.indexOf('#');
		if (fragment >= 0) {
			rest = rest.substring(0, fragment);
		}
		return "schemas/" + rest.replace("/./", "/");
	}

	/** Opens a bundled schema by URL, or returns {@code null}. */
	public static InputStream open(String url) {
		String location = location(url);
		return location == null ? null : MedbiqModels.open(location);
	}
}
