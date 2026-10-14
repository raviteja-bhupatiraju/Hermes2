/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.function.Consumer;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * Small, resolution independent icons of the content assist proposals, drawn in the palette of
 * {@link HlomTheme}: a glyph is rendered as an antialiased alpha mask and filled with a palette color.
 */
public final class XmlImages {

	/** Proposal kinds. */
	public enum Kind {
		/** An IEEE LOM element. */
		ELEMENT(HlomTheme.SLATE),
		/** A healthcare (or address) extension element. */
		HEALTHCARE_ELEMENT(HlomTheme.TEAL),
		/** An end tag. */
		END_TAG(HlomTheme.MUTED),
		/** An attribute. */
		ATTRIBUTE(HlomTheme.AMBER),
		/** A LOM vocabulary term or other enumerated value. */
		TERM(HlomTheme.ACCENT),
		/** A healthcare vocabulary term. */
		HEALTHCARE_TERM(HlomTheme.TEAL),
		/** A vocabulary source. */
		SOURCE(HlomTheme.INK),
		/** A language tag. */
		LANGUAGE(HlomTheme.SLATE),
		/** A suggested free-text value. */
		SUGGESTION(HlomTheme.MUTED),
		/** A concept of an offline terminology (MeSH descriptor, ICD-10-CM code, taxon). */
		CONCEPT(HlomTheme.TEAL);

		final RGB rgb;

		Kind(RGB rgb) {
			this.rgb = rgb;
		}
	}

	private static final String PREFIX = "edu.louisiana.caai.lom.editor.xml.image.";
	private static final int SIZE = 16;

	private XmlImages() {
	}

	/** The (shared) icon of a proposal kind. */
	static Image get(Kind kind) {
		ImageRegistry registry = JFaceResources.getImageRegistry();
		String key = PREFIX + kind.name();
		Image image = registry.get(key);
		if (image == null) {
			registry.put(key, ImageDescriptor.createFromImageDataProvider(provider(kind)));
			image = registry.get(key);
		}
		return image;
	}

	private static ImageDataProvider provider(Kind kind) {
		return zoom -> {
			int size = SIZE * zoom / 100;
			return render(size, kind.rgb, gc -> {
				gc.setTransform(null);
				float s = size / 16f;
				org.eclipse.swt.graphics.Transform transform = new org.eclipse.swt.graphics.Transform(gc.getDevice());
				transform.scale(s, s);
				gc.setTransform(transform);
				draw(gc, kind);
				gc.setTransform(null);
				transform.dispose();
			});
		};
	}

	/** Draws the glyph of a kind in white on black, in a 16x16 coordinate space. */
	private static void draw(GC gc, Kind kind) {
		Display display = gc.getDevice() instanceof Display d ? d : Display.getDefault();
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.setLineCap(SWT.CAP_ROUND);
		gc.setLineJoin(SWT.JOIN_ROUND);
		switch (kind) {
		case ELEMENT, HEALTHCARE_ELEMENT, END_TAG -> {
			gc.setLineWidth(2);
			gc.drawPolyline(new int[] { 5, 4, 1, 8, 5, 12 });
			gc.drawPolyline(new int[] { 11, 4, 15, 8, 11, 12 });
			if (kind == Kind.END_TAG) {
				gc.drawLine(9, 3, 7, 13);
			} else {
				gc.fillOval(6, 6, 4, 4);
			}
		}
		case ATTRIBUTE -> {
			gc.setLineWidth(2);
			gc.drawLine(3, 6, 13, 6);
			gc.drawLine(3, 10, 13, 10);
		}
		case TERM, HEALTHCARE_TERM -> gc.fillRoundRectangle(2, 2, 12, 12, 5, 5);
		case SOURCE -> gc.fillPolygon(new int[] { 8, 1, 15, 8, 8, 15, 1, 8 });
		case LANGUAGE -> {
			gc.setLineWidth(1);
			gc.drawOval(1, 1, 13, 13);
			gc.drawOval(5, 1, 5, 13);
			gc.drawLine(1, 8, 14, 8);
			gc.drawLine(2, 4, 13, 4);
			gc.drawLine(2, 11, 13, 11);
		}
		case SUGGESTION -> gc.fillOval(3, 3, 10, 10);
		case CONCEPT -> {
			// A small hierarchy: a node with two children.
			gc.fillRoundRectangle(1, 1, 7, 5, 2, 2);
			gc.fillRoundRectangle(8, 6, 7, 4, 2, 2);
			gc.fillRoundRectangle(8, 11, 7, 4, 2, 2);
			gc.setLineWidth(1);
			gc.drawPolyline(new int[] { 4, 6, 4, 13, 8, 13 });
			gc.drawLine(4, 8, 8, 8);
		}
		}
		if (kind == Kind.TERM || kind == Kind.HEALTHCARE_TERM) {
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.setLineWidth(2);
			gc.drawPolyline(new int[] { 5, 8, 7, 10, 11, 6 });
		}
	}

	/** Renders a white-on-black mask and turns it into an image of the given color with that mask as alpha. */
	private static ImageData render(int size, RGB rgb, Consumer<GC> painter) {
		Display display = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
		Image mask = new Image(display, size, size);
		GC gc = new GC(mask);
		try {
			gc.setAntialias(SWT.ON);
			gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.fillRectangle(0, 0, size, size);
			painter.accept(gc);
		} finally {
			gc.dispose();
		}
		ImageData maskData = mask.getImageData(100);
		mask.dispose();
		ImageData data = new ImageData(size, size, 24, new PaletteData(0xFF0000, 0x00FF00, 0x0000FF));
		int pixel = data.palette.getPixel(rgb);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				data.setPixel(x, y, pixel);
				RGB m = maskData.palette.getRGB(maskData.getPixel(x, y));
				data.setAlpha(x, y, Math.max(m.red, Math.max(m.green, m.blue)));
			}
		}
		return data;
	}
}
