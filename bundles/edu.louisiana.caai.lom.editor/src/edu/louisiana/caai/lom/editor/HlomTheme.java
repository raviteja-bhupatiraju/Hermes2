/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.net.URL;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * The visual identity of Hermes 2: a calm slate-and-white palette with the University of
 * Louisiana at Lafayette vermilion as accent (teal for the healthcare extension pages). The workbench chrome
 * is styled by the CSS theme {@code css/hlom.css}; this class styles what CSS doesn't reach (Eclipse Forms
 * and the editor's page tabs) and provides the shared images.
 */
public final class HlomTheme {

	/** Brand accent (UL Lafayette vermilion). */
	public static final RGB ACCENT = new RGB(0xCE, 0x18, 0x1E);
	/** Accent of the healthcare extension pages. */
	public static final RGB TEAL = new RGB(0x0E, 0x7C, 0x7B);
	/** Warnings. */
	public static final RGB AMBER = new RGB(0xD9, 0x77, 0x06);
	/** Primary text. */
	public static final RGB INK = new RGB(0x1E, 0x29, 0x3B);
	/** Secondary text and icons. */
	public static final RGB SLATE = new RGB(0x47, 0x55, 0x69);
	/** Hints and disabled text. */
	public static final RGB MUTED = new RGB(0x94, 0xA3, 0xB8);
	/** Window background. */
	public static final RGB BACKGROUND = new RGB(0xF4, 0xF6, 0xF9);
	/** Section title bars. */
	public static final RGB BAR = new RGB(0xEE, 0xF2, 0xF6);
	/** Borders and separators. */
	public static final RGB BORDER = new RGB(0xDD, 0xE3, 0xEA);
	/** Content surfaces. */
	public static final RGB SURFACE = new RGB(0xFF, 0xFF, 0xFF);

	private static final String PREFIX = "edu.louisiana.caai.lom.theme.";
	private static final String HEADING_FONT = PREFIX + "heading";
	private static final String SECTION_FONT = PREFIX + "section";
	private static final String GROUP_FONT = PREFIX + "group";
	private static final String BOLD_FONT = PREFIX + "bold";
	private static final String BADGE_FONT = PREFIX + "badge";

	private HlomTheme() {
	}

	/** A palette color (shared, must not be disposed). */
	public static Color color(RGB rgb) {
		ColorRegistry registry = JFaceResources.getColorRegistry();
		String key = PREFIX + rgb.red + "." + rgb.green + "." + rgb.blue;
		if (!registry.hasValueFor(key)) {
			registry.put(key, rgb);
		}
		return registry.get(key);
	}

	/** The font of page headings. */
	public static Font headingFont() {
		return font(HEADING_FONT, 5, SWT.BOLD);
	}

	/** The font of section titles. */
	public static Font sectionFont() {
		return font(SECTION_FONT, 1, SWT.BOLD);
	}

	/** The font of navigation group headers. */
	public static Font groupFont() {
		return font(GROUP_FONT, -1, SWT.BOLD);
	}

	/** The default font in bold. */
	public static Font boldFont() {
		return font(BOLD_FONT, 0, SWT.BOLD);
	}

	/** The font of count badges. */
	public static Font badgeFont() {
		return font(BADGE_FONT, -2, SWT.BOLD);
	}

	private static Font font(String key, int grow, int style) {
		FontRegistry registry = JFaceResources.getFontRegistry();
		if (!registry.hasValueFor(key)) {
			FontDescriptor base = JFaceResources.getDefaultFontDescriptor();
			int height = base.getFontData()[0].getHeight();
			registry.put(key, base.setHeight(height + grow).setStyle(style).getFontData());
		}
		return registry.get(key);
	}

	/** Applies the palette to a form toolkit: section bars, headings, borders and text. */
	public static void configure(FormToolkit toolkit) {
		FormColors colors = toolkit.getColors();
		colors.setBackground(color(SURFACE));
		colors.setForeground(color(INK));
		colors.createColor(IFormColors.TITLE, INK);
		colors.createColor(IFormColors.BORDER, BORDER);
		colors.createColor(IFormColors.SEPARATOR, BORDER);
		colors.createColor(IFormColors.TB_BG, BAR);
		colors.createColor(IFormColors.TB_BORDER, BORDER);
		colors.createColor(IFormColors.TB_TOGGLE, SLATE);
		colors.createColor(IFormColors.TB_TOGGLE_HOVER, ACCENT);
		colors.createColor(IFormColors.H_GRADIENT_START, SURFACE);
		colors.createColor(IFormColors.H_GRADIENT_END, BACKGROUND);
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE1, BORDER);
		colors.createColor(IFormColors.H_BOTTOM_KEYLINE2, ACCENT);
		colors.createColor(IFormColors.H_HOVER_LIGHT, BAR);
		colors.createColor(IFormColors.H_HOVER_FULL, BORDER);
		toolkit.setBorderStyle(SWT.BORDER);
	}

	/** Styles a page's form: heading font and colors, accent keyline and image. */
	public static void decorate(FormToolkit toolkit, ScrolledForm form, boolean healthcare, Image image) {
		toolkit.decorateFormHeading(form.getForm());
		form.getForm().setFont(headingFont());
		form.getForm().setForeground(color(INK));
		form.getForm().setHeadColor(IFormColors.H_BOTTOM_KEYLINE2, color(healthcare ? TEAL : ACCENT));
		if (image != null) {
			form.setImage(image);
		}
		form.setBackground(color(SURFACE));
	}

	/** Styles the editor's page tabs (the CSS theme styles the workbench part stacks). */
	public static void decorate(CTabFolder tabs) {
		tabs.setSimple(true);
		tabs.setBackground(color(BACKGROUND));
		tabs.setForeground(color(SLATE));
		tabs.setSelectionBackground(color(SURFACE));
		tabs.setSelectionForeground(color(INK));
		tabs.setSelectionBarThickness(2);
		tabs.setTabHeight(Math.max(tabs.getTabHeight(), 26));
	}

	/**
	 * An image from this bundle (e.g. {@code icons/pages/general.png}), cached in the JFace image registry;
	 * {@code @2x} variants are picked up automatically on high-resolution displays. Returns {@code null} if the
	 * image doesn't exist.
	 */
	public static Image image(String path) {
		ImageRegistry registry = JFaceResources.getImageRegistry();
		String key = PREFIX + path;
		Image image = registry.get(key);
		if (image == null) {
			URL url = HlomTheme.class.getResource("/" + path);
			if (url == null) {
				return null;
			}
			registry.put(key, ImageDescriptor.createFromURL(url));
			image = registry.get(key);
		}
		return image;
	}
}
