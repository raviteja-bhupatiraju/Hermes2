/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.Locale;
import java.util.function.ToIntBiFunction;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension2;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * A content assist proposal of the XML page: replaces the text from a start offset up to the caret (plus a
 * fixed number of characters after the invocation offset) and places the caret inside the inserted text. The
 * proposal stays in the list while the typed text matches it.
 */
public final class XmlCompletionProposal implements ICompletionProposal, ICompletionProposalExtension2,
		ICompletionProposalExtension6, Comparable<XmlCompletionProposal> {

	private static final StyledString.Styler QUALIFIER = new StyledString.Styler() {
		@Override
		public void applyStyles(TextStyle style) {
			style.foreground = HlomTheme.color(HlomTheme.MUTED);
		}
	};

	private final String label;
	private final String qualifier;
	private final XmlImages.Kind kind;
	private final int start;
	private final int invocationOffset;
	private final int suffixLength;
	private final String replacement;
	private final int caret;
	private final String[] matchTexts;
	private final String info;
	private final int rank;
	private boolean retrigger;
	private boolean wordMatch;
	private ToIntBiFunction<IDocument, Integer> afterApply;
	private int shift;

	/**
	 * @param label            the displayed text
	 * @param qualifier        an optional, dimmed description
	 * @param start            the offset where the replaced text begins
	 * @param invocationOffset the caret offset when the proposals were computed
	 * @param suffixLength     the number of characters after the invocation offset that are replaced as well
	 * @param replacement      the inserted text
	 * @param caret            the caret position within the replacement
	 * @param matchTexts       texts the typed text (start to caret) must be a prefix of, ignoring case
	 */
	public XmlCompletionProposal(String label, String qualifier, XmlImages.Kind kind, int start, int invocationOffset,
			int suffixLength, String replacement, int caret, String info, int rank, String... matchTexts) {
		this.label = label;
		this.qualifier = qualifier;
		this.kind = kind;
		this.start = start;
		this.invocationOffset = invocationOffset;
		this.suffixLength = suffixLength;
		this.replacement = replacement;
		this.caret = caret;
		this.info = info;
		this.rank = rank;
		this.matchTexts = matchTexts;
	}

	/** Opens content assist again after the proposal was applied (e.g. to choose the value of a new element). */
	XmlCompletionProposal retrigger() {
		return retrigger(true);
	}

	/** Whether to open content assist again after the proposal was applied. */
	public XmlCompletionProposal retrigger(boolean value) {
		retrigger = value;
		return this;
	}

	/**
	 * Runs an additional edit after the proposal was applied, given the offset of the inserted text; the action
	 * returns by how many characters it moved the inserted text.
	 */
	XmlCompletionProposal afterApply(ToIntBiFunction<IDocument, Integer> action) {
		afterApply = action;
		return this;
	}

	/** Matches typed text word by word instead of as a prefix (for terminology concepts found by a search). */
	public XmlCompletionProposal matchWords() {
		wordMatch = true;
		return this;
	}

	/** Whether a typed text matches this proposal. */
	public boolean matches(String typed) {
		String lower = typed.toLowerCase(Locale.ROOT);
		if (wordMatch) {
			// Every typed word starts a word of one of the texts (e.g. "myocard inf" -> "Myocardial Infarction").
			String[] tokens = lower.strip().split("[^\\p{L}\\p{N}]+");
			for (String text : matchTexts) {
				String candidate = " " + text.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", " ");
				boolean all = true;
				for (String token : tokens) {
					if (!token.isEmpty() && !candidate.contains(" " + token)) {
						all = false;
						break;
					}
				}
				if (all) {
					return true;
				}
			}
			return false;
		}
		for (String text : matchTexts) {
			if (text.toLowerCase(Locale.ROOT).startsWith(lower)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(IDocument document) {
		apply(document, invocationOffset);
	}

	private void apply(IDocument document, int offset) {
		try {
			int end = Math.min(document.getLength(), Math.max(offset, invocationOffset) + suffixLength);
			document.replace(start, end - start, replacement);
			shift = afterApply == null ? 0 : afterApply.applyAsInt(document, start);
		} catch (BadLocationException _) {
			// the document changed in between: ignore
		}
	}

	@Override
	public void apply(ITextViewer viewer, char trigger, int stateMask, int offset) {
		apply(viewer.getDocument(), offset);
		viewer.setSelectedRange(start + shift + caret, 0);
		viewer.revealRange(start + shift + caret, 0);
		if (retrigger && viewer instanceof ISourceViewer sourceViewer) {
			viewer.getTextWidget().getDisplay().asyncExec(() -> {
				if (sourceViewer.getTextWidget() != null && !sourceViewer.getTextWidget().isDisposed()
						&& sourceViewer instanceof org.eclipse.jface.text.ITextOperationTarget target
						&& target.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS)) {
					target.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
				}
			});
		}
	}

	@Override
	public void selected(ITextViewer viewer, boolean smartToggle) {
		// no preview
	}

	@Override
	public void unselected(ITextViewer viewer) {
		// no preview
	}

	@Override
	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		if (offset < start) {
			return false;
		}
		try {
			return matches(document.get(start, offset - start));
		} catch (BadLocationException _) {
			return false;
		}
	}

	@Override
	public Point getSelection(IDocument document) {
		return new Point(start + shift + caret, 0);
	}

	@Override
	public String getAdditionalProposalInfo() {
		return info == null ? null : XmlProblemHover.escape(info).replace("\n", "<br>");
	}

	@Override
	public String getDisplayString() {
		return qualifier == null ? label : label + "  " + qualifier;
	}

	@Override
	public StyledString getStyledDisplayString() {
		StyledString styled = new StyledString(label);
		if (qualifier != null) {
			styled.append("  " + qualifier, QUALIFIER);
		}
		return styled;
	}

	@Override
	public Image getImage() {
		return XmlImages.get(kind);
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	@Override
	public int compareTo(XmlCompletionProposal other) {
		return Integer.compare(rank, other.rank);
	}

	public String label() {
		return label;
	}

	XmlImages.Kind kind() {
		return kind;
	}
}
