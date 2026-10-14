/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

/**
 * Tag editing while typing:
 * <ul>
 * <li>typing the {@code >} of a start tag inserts the end tag after the caret (unless the element is already
 * closed further on);</li>
 * <li>typing {@code </} completes the end tag of the open element;</li>
 * <li>changing the name of a start tag changes the name of its end tag as well, and vice versa.</li>
 * </ul>
 */
final class XmlTagEditing implements IAutoEditStrategy {

	private static final int MAX_NAME_EDIT = 64;

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (!command.doit || command.text == null) {
			return;
		}
		try {
			if (command.length == 0 && ">".equals(command.text)) {
				closeStartTag(document, command);
			} else if (command.length == 0 && "/".equals(command.text) && command.offset > 0
					&& document.getChar(command.offset - 1) == '<') {
				completeEndTag(document, command);
			} else if (isName(command.text) && command.text.length() <= MAX_NAME_EDIT) {
				renameMatchingTag(document, command);
			}
		} catch (BadLocationException _) {
			// leave the command as it is
		}
	}

	private static boolean isName(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (!XmlStructure.isNameChar(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/** Typing {@code >}: adds the end tag if the start tag's element would otherwise stay open. */
	private static void closeStartTag(IDocument document, DocumentCommand command) throws BadLocationException {
		String text = document.get();
		String before = text.substring(0, command.offset);
		XmlStructure structure = XmlStructure.parse(before);
		if (structure.inSpecial || structure.partialTag < 0) {
			return;
		}
		String tag = before.substring(structure.partialTag);
		if (tag.length() < 2 || !XmlStructure.isNameChar(tag.charAt(1)) || tag.endsWith("/") || hasOpenQuote(tag)) {
			return;
		}
		String name = XmlStructure.name(tag, 1);
		String after = text.substring(command.offset);
		if (after.startsWith("</" + name + ">")) {
			return;
		}
		// Would the element be closed by an end tag that is already there?
		XmlStructure withTag = XmlStructure.parse(before + ">" + after);
		for (XmlStructure.Element element : withTag.elements) {
			if (element.start == structure.partialTag) {
				if (element.isClosed()) {
					return;
				}
				break;
			}
		}
		command.text = "></" + name + ">";
		command.caretOffset = command.offset + 1;
		command.shiftsCaret = false;
	}

	private static boolean hasOpenQuote(String tag) {
		char quote = 0;
		for (int i = 0; i < tag.length(); i++) {
			char c = tag.charAt(i);
			if (quote != 0) {
				if (c == quote) {
					quote = 0;
				}
			} else if (c == '"' || c == '\'') {
				quote = c;
			}
		}
		return quote != 0;
	}

	/** Typing {@code /} after {@code <}: completes the end tag of the innermost open element. */
	private static void completeEndTag(IDocument document, DocumentCommand command) throws BadLocationException {
		String text = document.get();
		if (command.offset < text.length() && XmlStructure.isNameChar(text.charAt(command.offset))) {
			return;
		}
		XmlStructure structure = XmlStructure.parse(text.substring(0, command.offset - 1));
		if (structure.inSpecial || structure.open == null) {
			return;
		}
		command.text = "/" + structure.open.name + ">";
	}

	/** Mirrors an edit of a start tag name to the end tag name (or the other way round). */
	private static void renameMatchingTag(IDocument document, DocumentCommand command) throws BadLocationException {
		String text = document.get();
		XmlStructure structure = XmlStructure.parse(text);
		for (XmlStructure.Element element : structure.elements) {
			if (element.empty || element.endTagStart < 0) {
				continue;
			}
			int startName = element.nameStart();
			int endName = element.endNameStart();
			int length = element.name.length();
			if (!text.startsWith(element.name, endName) || !text.startsWith(">", endName + length)) {
				continue; // the end tag doesn't match exactly (e.g. another element closed it)
			}
			int mirror = -1;
			if (within(command, startName, length)) {
				mirror = endName + (command.offset - startName);
			} else if (within(command, endName, length)) {
				mirror = startName + (command.offset - endName);
			}
			if (mirror >= 0) {
				command.addCommand(mirror, command.length, command.text, null);
				command.doit = true;
				// JFace executes the additional edit only if the command has an explicit caret position.
				command.caretOffset = command.offset + command.text.length();
				command.shiftsCaret = false;
				return;
			}
		}
	}

	/** Whether an edit lies within a name (inserting at its end included). */
	private static boolean within(DocumentCommand command, int nameStart, int length) {
		return command.offset >= nameStart && command.offset + command.length <= nameStart + length
				&& !(command.length == 0 && command.text.isEmpty());
	}
}
