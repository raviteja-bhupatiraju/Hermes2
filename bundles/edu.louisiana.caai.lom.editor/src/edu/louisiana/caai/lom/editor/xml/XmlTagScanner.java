/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

/**
 * Colors the inside of a tag partition: element name (with its brackets), attribute names and quoted
 * attribute values.
 */
final class XmlTagScanner extends RuleBasedScanner {

	/** Detects XML names (including namespace prefixes). */
	static final IWordDetector NAME_DETECTOR = new IWordDetector() {
		@Override
		public boolean isWordStart(char c) {
			return Character.isLetter(c) || c == '_' || c == ':';
		}

		@Override
		public boolean isWordPart(char c) {
			return Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '-' || c == '.';
		}
	};

	XmlTagScanner() {
		IToken element = XmlColors.token(XmlColors.ELEMENT, SWT.BOLD);
		IToken attribute = XmlColors.token(XmlColors.ATTRIBUTE, SWT.NORMAL);
		IToken string = XmlColors.token(XmlColors.STRING, SWT.NORMAL);
		setRules(new IRule[] {
				new WhitespaceRule(Character::isWhitespace),
				new MultiLineRule("\"", "\"", string, (char) 0, true),
				new MultiLineRule("'", "'", string, (char) 0, true),
				new ElementNameRule(element),
				new WordRule(NAME_DETECTOR, attribute) });
	}

	/** Matches {@code <name}, {@code </name}, {@code />} and {@code >}. */
	private static final class ElementNameRule implements IRule {

		private final IToken token;

		ElementNameRule(IToken token) {
			this.token = token;
		}

		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			int c = scanner.read();
			if (c == '>') {
				return token;
			}
			if (c == '/') {
				if (scanner.read() == '>') {
					return token;
				}
				scanner.unread();
				scanner.unread();
				return Token.UNDEFINED;
			}
			if (c != '<') {
				scanner.unread();
				return Token.UNDEFINED;
			}
			c = scanner.read();
			if (c != '/') {
				scanner.unread();
			}
			for (;;) {
				c = scanner.read();
				if (c == ICharacterScanner.EOF || !NAME_DETECTOR.isWordPart((char) c)) {
					scanner.unread();
					return token;
				}
			}
		}
	}
}
