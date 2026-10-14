/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * The HLOM Guide: the bundled, searchable guide to Healthcare LOM and the editor, rendered with the SWT browser
 * (Edge/WebView2 on Windows if available). Topics are element keys (e.g. {@code creditType}), section ids or page
 * ids ({@code page-vocabularies}); see {@link #showTopic(String)}.
 */
public class GuideView extends ViewPart {

	/** The view id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.help.GuideView";

	private Browser browser;
	private Composite fallback;
	private boolean loaded;
	private String pendingTopic;

	@Override
	public void createPartControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.GUIDE_VIEW);
		parent.setLayout(new FillLayout());
		browser = createBrowser(parent);
		if (browser == null) {
			createFallback(parent);
			return;
		}
		browser.addProgressListener(new ProgressAdapter() {
			@Override
			public void completed(ProgressEvent event) {
				loaded = true;
				if (pendingTopic != null) {
					String topic = pendingTopic;
					pendingTopic = null;
					showTopic(topic);
				}
			}
		});
		browser.addLocationListener(LocationListener.changingAdapter(this::handleLink));
		try {
			browser.setUrl(HelpContent.guideUrl(pendingTopic));
			pendingTopic = null;
		} catch (IOException exception) {
			HlomEditorPlugin.INSTANCE.log(exception);
			browser.setText(NLS.bind(Messages.GuideView_PStyleFontFamilySegoe, exception.getMessage()));
		}
		contributeActions(getViewSite().getActionBars().getToolBarManager());
	}

	private static Browser createBrowser(Composite parent) {
		for (int style : new int[] { SWT.EDGE, SWT.NONE }) {
			try {
				return new Browser(parent, style);
			} catch (SWTError _) {
				// try the next engine
			}
		}
		return null;
	}

	/** Without a browser engine: a short message and a button opening the guide in the system browser. */
	private void createFallback(Composite parent) {
		fallback = new Composite(parent, SWT.NONE);
		fallback.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.swtDefaults().margins(24, 24).spacing(0, 12).applyTo(fallback);
		Label title = new Label(fallback, SWT.NONE);
		title.setText(Messages.GuideView_HLOMGuide);
		title.setFont(HlomTheme.headingFont());
		title.setForeground(HlomTheme.color(HlomTheme.INK));
		title.setBackground(fallback.getBackground());
		Label text = new Label(fallback, SWT.WRAP);
		text.setText(Messages.GuideView_NoEmbeddedBrowserIsAvailable);
		text.setForeground(HlomTheme.color(HlomTheme.SLATE));
		text.setBackground(fallback.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).hint(320, SWT.DEFAULT).applyTo(text);
		Button open = new Button(fallback, SWT.PUSH);
		open.setText(Messages.GuideView_OpenTheHLOMGuide);
		open.addListener(SWT.Selection, _ -> openExternally(null));
	}

	private void handleLink(LocationEvent event) {
		String location = event.location;
		if (location == null || location.startsWith("about:")) {
			return;
		}
		String lower = location.toLowerCase();
		if (lower.startsWith("http:") || lower.startsWith("https:") || lower.startsWith("mailto:")) {
			event.doit = false;
			Program.launch(location);
		} else if (lower.endsWith(".pdf")) {
			event.doit = false;
			HelpSupport.openSpecification(getSite().getShell());
		}
	}

	private void contributeActions(IToolBarManager toolBar) {
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		toolBar.add(action(Messages.GuideView_Back, images, ISharedImages.IMG_TOOL_BACK, () -> browser.back()));
		toolBar.add(action(Messages.GuideView_Forward, images, ISharedImages.IMG_TOOL_FORWARD, () -> browser.forward()));
		toolBar.add(action(Messages.GuideView_Overview, images, ISharedImages.IMG_ETOOL_HOME_NAV, () -> showTopic("page-overview")));
		toolBar.add(new Separator());
		toolBar.add(action(Messages.GuideView_Print, images, ISharedImages.IMG_ETOOL_PRINT_EDIT, () -> browser.execute(Messages.GuideView_WindowPrint)));
		toolBar.add(action(Messages.GuideView_OpenInWebBrowser, images, ISharedImages.IMG_OBJ_ELEMENT, () -> openExternally(null)));
	}

	private static Action action(String text, ISharedImages images, String image, Runnable run) {
		Action action = new Action(text) {
			@Override
			public void run() {
				run.run();
			}
		};
		action.setToolTipText(text);
		action.setImageDescriptor(images.getImageDescriptor(image));
		return action;
	}

	private static void openExternally(String topic) {
		try {
			File guide = HelpContent.file(HelpContent.GUIDE);
			Program.launch(topic == null ? guide.getAbsolutePath() : HelpContent.guideUrl(topic));
		} catch (IOException exception) {
			HlomEditorPlugin.INSTANCE.log(exception);
		}
	}

	/**
	 * Shows a topic: an element key as used in {@link edu.louisiana.caai.lom.model.support.FieldHelp} (e.g. {@code creditType}), a section
	 * id ({@code sec-credits}, {@code cat-general}, {@code vocab-role}, {@code acr-ACCME}) or a page id
	 * ({@code page-overview}, {@code page-healthcare}, {@code page-vocabularies}, {@code page-conformance},
	 * {@code page-acronyms}, {@code page-professions}, {@code page-using}). Unknown topics are searched for.
	 */
	public void showTopic(String anchor) {
		String topic = anchor == null || anchor.isBlank() ? "page-overview" : anchor.strip();
		if (browser == null) {
			if (fallback != null) {
				openExternally(topic);
			}
			return;
		}
		if (!loaded) {
			pendingTopic = topic;
			return;
		}
		String js = javaScriptString(topic);
		browser.execute("if (window.hlomShowTopic) { if (location.hash === '#' + " + js + ") { hlomShowTopic(" + js
				+ "); } else { location.hash = " + js + "; } }");
	}

	/** Searches the guide for a text. */
	public void search(String query) {
		showTopic("search:" + query);
	}

	private static String javaScriptString(String text) {
		StringBuilder builder = new StringBuilder("'");
		for (char c : text.toCharArray()) {
			if (c == '\'' || c == '\\') {
				builder.append('\\').append(c);
			} else if (c < 0x20 || c == '<' || c == '>') {
				builder.append("\\u%04x".formatted((int) c));
			} else {
				builder.append(c);
			}
		}
		return builder.append('\'').toString();
	}

	@Override
	public void setFocus() {
		Control control = browser != null ? browser : fallback;
		if (control != null) {
			control.setFocus();
		}
	}
}
