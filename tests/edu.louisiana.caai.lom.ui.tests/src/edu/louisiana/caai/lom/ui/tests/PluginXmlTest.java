/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.commands.ICommandService;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

/**
 * The extensions the bundles of the application declare in their plugin.xml: the classes they name exist, the
 * commands of their menus and key bindings are defined, and their texts are translated (no {@code %key} left).
 */
class PluginXmlTest extends AbstractUiTest {

	/** The bundles of the application with extensions. */
	private static final List<String> BUNDLES = List.of("edu.louisiana.caai.lom.editor", "edu.louisiana.caai.lom.model",
			"edu.louisiana.caai.medbiq.model", "edu.louisiana.caai.lom.edit", "edu.louisiana.caai.lom.help");

	/** The attributes that name classes. */
	private static final Set<String> CLASS_ATTRIBUTES = Set.of("class", "contentProvider", "labelProvider",
			"defaultHandler", "matchingStrategy", "contributorClass");

	/** The attributes that are texts for the user. */
	private static final Set<String> TEXT_ATTRIBUTES = Set.of("name", "label", "description", "tooltip");

	private static List<IConfigurationElement> elements(String bundle) {
		List<IConfigurationElement> elements = new ArrayList<>();
		for (IExtension extension : Platform.getExtensionRegistry().getExtensions(bundle)) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				collect(element, elements);
			}
		}
		return elements;
	}

	private static void collect(IConfigurationElement element, List<IConfigurationElement> elements) {
		elements.add(element);
		for (IConfigurationElement child : element.getChildren()) {
			collect(child, elements);
		}
	}

	@Test
	void classesOfTheExtensionsExist() {
		List<String> missing = new ArrayList<>();
		for (String name : BUNDLES) {
			Bundle bundle = Platform.getBundle(name);
			assertNotNull(bundle, name);
			for (IConfigurationElement element : elements(name)) {
				for (String attribute : element.getAttributeNames()) {
					String value = element.getAttribute(attribute);
					if (CLASS_ATTRIBUTES.contains(attribute) && value != null && value.startsWith("edu.louisiana.")) {
						String className = value.contains(":") ? value.substring(0, value.indexOf(':')) : value;
						try {
							bundle.loadClass(className);
						} catch (ClassNotFoundException _) {
							missing.add(name + ": " + element.getName() + " " + attribute + "=" + value);
						}
					}
				}
			}
		}
		assertEquals(List.of(), missing, "classes named in plugin.xml that don't exist");
	}

	@Test
	void commandsOfMenusAndBindingsAreDefined() {
		ICommandService commands = window().getService(ICommandService.class);
		List<String> undefined = new ArrayList<>();
		for (String name : BUNDLES) {
			for (IConfigurationElement element : elements(name)) {
				String id = element.getAttribute("commandId");
				if (id != null && !commands.getCommand(id).isDefined()) {
					undefined.add(name + ": " + element.getName() + " commandId=" + id);
				}
			}
		}
		assertEquals(List.of(), undefined, "commands used in plugin.xml that aren't defined");
	}

	@Test
	void textsAreTranslated() {
		List<String> untranslated = new ArrayList<>();
		for (String name : BUNDLES) {
			for (IConfigurationElement element : elements(name)) {
				for (String attribute : element.getAttributeNames()) {
					String value = element.getAttribute(attribute);
					if (TEXT_ATTRIBUTES.contains(attribute) && value != null && value.startsWith("%")) {
						untranslated.add(name + ": " + element.getName() + " " + attribute + "=" + value);
					}
				}
			}
			String bundleName = Platform.getBundle(name).getHeaders().get("Bundle-Name");
			if (bundleName == null || bundleName.startsWith("%")) {
				untranslated.add(name + ": Bundle-Name=" + bundleName);
			}
		}
		assertEquals(List.of(), untranslated, "texts whose key isn't in plugin.properties");
	}

	/** The steps of the cheat sheets run commands that exist (p2's are installed with the product only). */
	@Test
	void cheatSheetsRunExistingCommands() throws Exception {
		ICommandService commands = window().getService(ICommandService.class);
		Bundle help = Platform.getBundle("edu.louisiana.caai.lom.help");
		java.util.Enumeration<java.net.URL> sheets = help.findEntries("cheatsheets", "*.xml", false);
		assertNotNull(sheets);
		List<String> problems = new ArrayList<>();
		int count = 0;
		for (java.net.URL sheet : java.util.Collections.list(sheets)) {
			count++;
			org.w3c.dom.Document document;
			try (java.io.InputStream in = sheet.openStream()) {
				document = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			}
			org.w3c.dom.NodeList list = document.getElementsByTagName("command");
			for (int i = 0; i < list.getLength(); i++) {
				String serialization = ((org.w3c.dom.Element) list.item(i)).getAttribute("serialization");
				if (serialization.startsWith("org.eclipse.equinox.p2.")) {
					continue;
				}
				try {
					if (!commands.deserialize(serialization).getCommand().isDefined()) {
						problems.add(sheet.getPath() + ": " + serialization);
					}
				} catch (org.eclipse.core.commands.common.NotDefinedException
						| org.eclipse.core.commands.SerializationException e) {
					problems.add(sheet.getPath() + ": " + serialization + " (" + e.getMessage() + ")");
				}
			}
		}
		assertEquals(4, count, "cheat sheets");
		assertEquals(List.of(), problems);
	}
}
