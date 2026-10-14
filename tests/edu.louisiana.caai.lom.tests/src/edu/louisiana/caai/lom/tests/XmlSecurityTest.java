/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * Documents can't read local files or other servers through external entities (XXE). The loading options disable
 * them explicitly, independent of the defaults of the Java runtime and EMF (which currently don't expand them either).
 */
class XmlSecurityTest {

	@TempDir
	Path folder;

	private String hostile(Path secret, String document) {
		return "<?xml version=\"1.0\"?>\n<!DOCTYPE " + document + " [<!ENTITY secret SYSTEM \"" + secret.toUri()
				+ "\">]>\n";
	}

	@Test
	void externalEntitiesAreNotRead() throws Exception {
		MedbiqStandards.init();
		Path secret = folder.resolve("secret.txt");
		Files.writeString(secret, "TOP-SECRET-CONTENT", StandardCharsets.UTF_8);
		String hlom = hostile(secret, "lom") + "<lom xmlns=\"http://ltsc.ieee.org/xsd/LOM\"><general><title><string "
				+ "language=\"en\">&secret;</string></title></general></lom>";
		String ci = hostile(secret, "CurriculumInventory")
				+ "<CurriculumInventory xmlns=\"http://ns.medbiq.org/curriculuminventory/v10/\"><Title>&secret;</Title>"
				+ "</CurriculumInventory>";
		for (String text : new String[] { hlom, ci }) {
			Resource resource = DocumentCheck.load(text, URI.createURI("hostile.xml"));
			String loaded = resource.getContents().isEmpty() ? "" : HlomResources.toXml(resource);
			assertFalse(loaded.contains("TOP-SECRET-CONTENT"), loaded);
			DocumentCheck.Result result = DocumentCheck.check(text, URI.createURI("hostile.xml"));
			assertFalse(result.problems().toString().contains("TOP-SECRET-CONTENT"));
		}
	}
}
