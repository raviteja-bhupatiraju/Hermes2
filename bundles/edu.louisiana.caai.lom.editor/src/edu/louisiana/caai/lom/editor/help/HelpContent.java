/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * The bundled help resources ({@code help/} of the editor bundle): the HLOM Guide and the specification PDF. They
 * are extracted once per bundle version into the plug-in state location, so that browsers and PDF viewers can
 * open them as plain files.
 */
public final class HelpContent {

	/** The guide page. */
	public static final String GUIDE = "guide.html";
	/** The MedBiquitous Healthcare LOM specification v1.1. */
	public static final String SPECIFICATION = "HealthcareLOMSpecificationv1_1_30Sep2009.pdf";

	private static File root;

	private HelpContent() {
	}

	/** The folder with the extracted help files. */
	public static synchronized File root() throws IOException {
		if (root == null || !new File(root, GUIDE).isFile()) {
			Bundle bundle = FrameworkUtil.getBundle(HelpContent.class);
			Path target = Platform.getStateLocation(bundle).toFile().toPath()
					.resolve("help-" + bundle.getVersion() + "-" + bundle.getLastModified());
			Enumeration<URL> entries = bundle.findEntries("help", "*", true);
			while (entries != null && entries.hasMoreElements()) {
				URL entry = entries.nextElement();
				String path = entry.getPath();
				if (path.endsWith("/")) {
					continue;
				}
				Path file = target.resolve(path.substring(path.indexOf("help/") + 5));
				if (!Files.isRegularFile(file)) {
					Files.createDirectories(file.getParent());
					try (InputStream in = entry.openStream()) {
						Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}
			root = target.toFile();
		}
		return root;
	}

	/** An extracted help file, e.g. {@link #SPECIFICATION}. */
	public static File file(String name) throws IOException {
		return new File(root(), name);
	}

	/** The {@code file:} URL of the guide, optionally with a topic anchor. */
	public static String guideUrl(String topic) throws IOException {
		String url = file(GUIDE).toURI().toString();
		return topic == null || topic.isEmpty() ? url : url + "#" + topic;
	}
}
