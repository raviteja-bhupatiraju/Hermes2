/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The syntax colors of the XML source editor. Colors are kept in the JFace color registry, which owns and
 * disposes them.
 */
final class XmlColors {

	private static final String PREFIX = "edu.louisiana.caai.lom.editor.xml.";

	static final String ELEMENT = "element";
	static final String ATTRIBUTE = "attribute";
	static final String STRING = "string";
	static final String COMMENT = "comment";
	static final String PI = "pi";
	static final String CDATA = "cdata";
	static final String ENTITY = "entity";
	static final String DECLARATION = "declaration";
	static final String LINE_NUMBER = "lineNumber";

	private XmlColors() {
	}

	/** The (registered) color for the given key. Must be called in the UI thread. */
	static Color color(String key) {
		ColorRegistry registry = JFaceResources.getColorRegistry();
		String id = PREFIX + key;
		if (!registry.hasValueFor(id)) {
			registry.put(id, defaultRgb(key));
		}
		return registry.get(id);
	}

	/** A token with the given color and style ({@link SWT#NORMAL}, {@link SWT#BOLD}, {@link SWT#ITALIC}). */
	static IToken token(String key, int style) {
		return new Token(new TextAttribute(color(key), null, style));
	}

	private static RGB defaultRgb(String key) {
		return switch (key) {
		case ELEMENT -> new RGB(63, 127, 127);
		case ATTRIBUTE -> new RGB(127, 0, 127);
		case STRING -> new RGB(42, 0, 255);
		case COMMENT -> new RGB(63, 95, 191);
		case PI -> new RGB(128, 128, 128);
		case CDATA -> new RGB(0, 128, 128);
		case ENTITY -> new RGB(200, 100, 0);
		case DECLARATION -> new RGB(128, 128, 0);
		case LINE_NUMBER -> new RGB(120, 120, 120);
		default -> new RGB(0, 0, 0);
		};
	}
}
