/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;

/**
 * Colors element content: plain text and entity or character references ({@code &amp;}, {@code &#160;}).
 */
final class XmlContentScanner extends RuleBasedScanner {

	XmlContentScanner() {
		setRules(new IRule[] { new EntityRule(XmlColors.token(XmlColors.ENTITY, SWT.NORMAL)) });
	}

	/** Matches {@code &name;} and {@code &#digits;}/{@code &#xhex;}. */
	private static final class EntityRule implements IRule {

		private final IToken token;

		EntityRule(IToken token) {
			this.token = token;
		}

		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			int c = scanner.read();
			if (c != '&') {
				scanner.unread();
				return Token.UNDEFINED;
			}
			int count = 1;
			for (;;) {
				c = scanner.read();
				count++;
				if (c == ';' && count > 2) {
					return token;
				}
				if (c == ICharacterScanner.EOF || !(Character.isLetterOrDigit(c) || c == '#' || c == '_'
						|| c == '-' || c == '.' || c == ':')) {
					for (int i = 0; i < count; i++) {
						scanner.unread();
					}
					return Token.UNDEFINED;
				}
			}
		}
	}
}
