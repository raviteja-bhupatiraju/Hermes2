/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * Splits an XML document into comment, processing instruction, CDATA, declaration and tag partitions; the
 * remaining text (element content) is in the default partition.
 */
public class XmlPartitionScanner extends RuleBasedPartitionScanner {

	public static final String XML_COMMENT = "__xml_comment";
	public static final String XML_PI = "__xml_pi";
	public static final String XML_CDATA = "__xml_cdata";
	public static final String XML_DECLARATION = "__xml_declaration";
	public static final String XML_TAG = "__xml_tag";

	/** The partition types besides {@link IDocument#DEFAULT_CONTENT_TYPE}. */
	public static final String[] PARTITION_TYPES = { XML_COMMENT, XML_PI, XML_CDATA, XML_DECLARATION, XML_TAG };

	public XmlPartitionScanner() {
		setPredicateRules(new IPredicateRule[] {
				new MultiLineRule("<!--", "-->", new Token(XML_COMMENT), (char) 0, true),
				new MultiLineRule("<![CDATA[", "]]>", new Token(XML_CDATA), (char) 0, true),
				new MultiLineRule("<?", "?>", new Token(XML_PI), (char) 0, true),
				new MultiLineRule("<!", ">", new Token(XML_DECLARATION), (char) 0, true),
				new TagRule(new Token(XML_TAG)) });
	}

	/**
	 * Matches a start or end tag: {@code <} up to the next {@code >} outside quoted attribute values. An
	 * unquoted {@code <} or the end of the document also ends the tag, so that an incomplete tag being typed
	 * does not swallow the rest of the document.
	 */
	static final class TagRule implements IPredicateRule {

		private final IToken token;

		TagRule(IToken token) {
			this.token = token;
		}

		@Override
		public IToken getSuccessToken() {
			return token;
		}

		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			return evaluate(scanner, false);
		}

		@Override
		public IToken evaluate(ICharacterScanner scanner, boolean resume) {
			if (!resume) {
				int c = scanner.read();
				if (c != '<') {
					scanner.unread();
					return Token.UNDEFINED;
				}
				int next = scanner.read();
				scanner.unread();
				if (next == '!' || next == '?') {
					scanner.unread();
					return Token.UNDEFINED;
				}
			}
			int quote = 0;
			for (;;) {
				int c = scanner.read();
				if (c == ICharacterScanner.EOF) {
					scanner.unread();
					return token;
				}
				if (quote != 0) {
					if (c == quote) {
						quote = 0;
					}
				} else if (c == '"' || c == '\'') {
					quote = c;
				} else if (c == '>') {
					return token;
				} else if (c == '<') {
					scanner.unread();
					return token;
				}
			}
		}
	}
}
