/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;

/**
 * An overview page: a dashboard of cards in two columns (3:2), rebuilt from the document and its validation
 * results whenever they change (at once if the page is visible, else when it is shown again).
 */
abstract class AbstractOverviewPage extends AbstractLomPage {

	protected FormSupport form;
	private Composite root;
	private List<Composite> cards = List.of();
	private boolean stale;
	private final Runnable changeListener = this::changed;

	/** Creates the (empty) cards in the left and right columns; returns all of them. */
	protected abstract List<Composite> createCards(Composite left, Composite right);

	/** Fills the cards (emptied before) from the current document and validation results. */
	protected abstract void fillCards();

	@Override
	protected final void createContent(FormSupport formSupport, Composite body) {
		this.form = formSupport;
		body.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		root = new Composite(body, SWT.NONE);
		root.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridLayoutFactory.fillDefaults().numColumns(5).equalWidth(true).spacing(16, 16).margins(6, 4).applyTo(root);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(root);
		cards = createCards(stack(root, 3), stack(root, 2));
		for (Composite card : cards) {
			GridDataFactory.fillDefaults().grab(true, false).applyTo(card);
		}
		form.context().broadcaster().addListener(changeListener);
		ValidationSource source = validationSource();
		if (source != null) {
			source.addProblemsListener(changeListener);
		}
		root.addDisposeListener(_ -> removeListeners());
		update();
	}

	private static Composite stack(Composite parent, int span) {
		Composite column = new Composite(parent, SWT.NONE);
		column.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		GridLayoutFactory.fillDefaults().spacing(0, 16).applyTo(column);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).span(span, 1).applyTo(column);
		return column;
	}

	protected ValidationSource validationSource() {
		return services() == null ? null : services().validation();
	}

	private void removeListeners() {
		if (form != null) {
			form.context().broadcaster().removeListener(changeListener);
		}
		ValidationSource source = validationSource();
		if (source != null) {
			source.removeProblemsListener(changeListener);
		}
	}

	/** Model change or validation: rebuild now if visible, otherwise when the page is shown again. */
	private void changed() {
		if (root == null || root.isDisposed()) {
			return;
		}
		if (root.isVisible()) {
			update();
		} else {
			stale = true;
		}
	}

	@Override
	public void refresh() {
		if (stale) {
			update();
		}
		super.refresh();
	}

	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}

	/** Rebuilds the cards. */
	private void update() {
		if (root == null || root.isDisposed()) {
			return;
		}
		stale = false;
		root.setRedraw(false);
		try {
			for (Composite card : cards) {
				for (Control child : card.getChildren()) {
					child.dispose();
				}
			}
			fillCards();
			root.layout(true, true);
			for (Composite parent = root.getParent(); parent != null; parent = parent.getParent()) {
				if (parent instanceof ScrolledForm scrolled) {
					scrolled.reflow(true);
					break;
				}
			}
		} finally {
			root.setRedraw(true);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Widgets of the cards
	// ----------------------------------------------------------------------------------------------------

	static Composite surface(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		return composite;
	}

	/** A label on a card (without layout data). */
	static Label label(Composite parent, String text, Font font, RGB rgb, boolean wrap) {
		Label label = new Label(parent, wrap ? SWT.WRAP : SWT.NONE);
		label.setText(text.replace("&", "&&"));
		label.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		label.setForeground(HlomTheme.color(rgb));
		if (font != null) {
			label.setFont(font);
		}
		return label;
	}

	/** Styles a hyperlink: palette color, underlined while hovered. */
	static void styleLink(Hyperlink link, String text, RGB rgb) {
		link.setText(text);
		link.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		link.setForeground(HlomTheme.color(rgb));
		link.setUnderlined(false);
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkEntered(HyperlinkEvent e) {
				link.setUnderlined(true);
			}

			@Override
			public void linkExited(HyperlinkEvent e) {
				link.setUnderlined(false);
			}
		});
	}

	/** A text shortened to a length at a word boundary, with an ellipsis. */
	static String abbreviate(String text, int max) {
		String flat = text.replaceAll("\\s+", " ").strip();
		if (flat.length() <= max) {
			return flat;
		}
		int cut = flat.lastIndexOf(' ', max - 1);
		return flat.substring(0, cut > max / 2 ? cut : max - 1).strip() + "…";
	}
}
