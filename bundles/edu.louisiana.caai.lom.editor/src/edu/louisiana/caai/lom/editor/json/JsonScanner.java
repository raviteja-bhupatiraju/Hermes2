/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.json;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * Syntax highlighting of JSON text: member names, string values, numbers, literals and punctuation. A string is
 * a member name when the next non-blank character after it is a colon.
 */
final class JsonScanner implements ITokenScanner {

	private static final String PREFIX = "edu.louisiana.caai.lom.editor.json.";

	static final String KEY = "key";
	static final String STRING = "string";
	static final String NUMBER = "number";
	static final String LITERAL = "literal";
	static final String PUNCTUATION = "punctuation";
	static final String LINE_NUMBER = "lineNumber";

	private final IToken key = token(KEY, SWT.BOLD);
	private final IToken string = token(STRING, SWT.NORMAL);
	private final IToken number = token(NUMBER, SWT.NORMAL);
	private final IToken literal = token(LITERAL, SWT.BOLD);
	private final IToken punctuation = token(PUNCTUATION, SWT.NORMAL);

	private IDocument document;
	private int offset;
	private int end;
	private int tokenOffset;

	/** The (registered) color for the given key. Must be called in the UI thread. */
	static Color color(String name) {
		ColorRegistry registry = JFaceResources.getColorRegistry();
		String id = PREFIX + name;
		if (!registry.hasValueFor(id)) {
			registry.put(id, switch (name) {
				case KEY -> new RGB(63, 95, 127);
				case STRING -> new RGB(42, 0, 255);
				case NUMBER -> new RGB(0, 128, 96);
				case LITERAL -> new RGB(127, 0, 85);
				case PUNCTUATION -> new RGB(100, 100, 100);
				case LINE_NUMBER -> new RGB(120, 120, 120);
				default -> new RGB(0, 0, 0);
			});
		}
		return registry.get(id);
	}

	private static IToken token(String name, int style) {
		return new Token(new TextAttribute(color(name), null, style));
	}

	@Override
	public void setRange(IDocument document, int offset, int length) {
		this.document = document;
		this.offset = offset;
		this.end = offset + length;
	}

	private int read() {
		try {
			return offset < end ? document.getChar(offset++) : ICharacterScanner.EOF;
		} catch (org.eclipse.jface.text.BadLocationException _) {
			return ICharacterScanner.EOF;
		}
	}

	private int peek() {
		try {
			return offset < end ? document.getChar(offset) : ICharacterScanner.EOF;
		} catch (org.eclipse.jface.text.BadLocationException _) {
			return ICharacterScanner.EOF;
		}
	}

	@Override
	public IToken nextToken() {
		tokenOffset = offset;
		int c = read();
		if (c == ICharacterScanner.EOF) {
			return Token.EOF;
		}
		if (Character.isWhitespace(c)) {
			while (peek() != ICharacterScanner.EOF && Character.isWhitespace(peek())) {
				offset++;
			}
			return Token.WHITESPACE;
		}
		if (c == '"') {
			while (true) {
				int next = read();
				if (next == ICharacterScanner.EOF || next == '"' || next == '\n') {
					break;
				}
				if (next == '\\') {
					read();
				}
			}
			return isFollowedByColon() ? key : string;
		}
		if (c == '-' || Character.isDigit(c)) {
			while (peek() != ICharacterScanner.EOF && "0123456789.eE+-".indexOf(peek()) >= 0) {
				offset++;
			}
			return number;
		}
		if (Character.isLetter(c)) {
			while (peek() != ICharacterScanner.EOF && Character.isLetter(peek())) {
				offset++;
			}
			return literal;
		}
		return punctuation;
	}

	private boolean isFollowedByColon() {
		try {
			int length = document.getLength();
			for (int i = offset; i < length; i++) {
				char c = document.getChar(i);
				if (!Character.isWhitespace(c)) {
					return c == ':';
				}
			}
		} catch (org.eclipse.jface.text.BadLocationException _) {
			// end of the document
		}
		return false;
	}

	@Override
	public int getTokenOffset() {
		return tokenOffset;
	}

	@Override
	public int getTokenLength() {
		return offset - tokenOffset;
	}
}
