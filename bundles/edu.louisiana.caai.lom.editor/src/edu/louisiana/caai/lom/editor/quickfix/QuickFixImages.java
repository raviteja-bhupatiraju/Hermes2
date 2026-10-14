/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.editor.HlomTheme;

/**
 * The quick fix icons: a light bulb (a fix is available), a light bulb with a check badge ("Fix All") and the
 * reveal arrow for navigation fixes. The bulbs are rendered at any zoom level with anti-aliasing, so they stay
 * crisp on high-resolution displays.
 */
public final class QuickFixImages {

	private static final int SIZE = 16;
	private static final int SAMPLES = 4;

	private static final Map<String, Image> CACHE = new HashMap<>();

	private QuickFixImages() {
	}

	/** The light bulb shown for problems with fixes. */
	public static Image bulb() {
		return image("bulb", false);
	}

	/** The light bulb with a check badge, for "Fix All". */
	public static Image fixAll() {
		return image("fixAll", true);
	}

	/** The icon of fixes that only navigate to a field. */
	public static Image navigate() {
		Image image = HlomTheme.image("icons/library/reveal.png");
		return image != null ? image : bulb();
	}

	/** An image descriptor for actions. */
	public static ImageDescriptor descriptor(Image image) {
		return ImageDescriptor.createFromImage(image);
	}

	private static synchronized Image image(String key, boolean badge) {
		Image image = CACHE.get(key);
		if (image == null || image.isDisposed()) {
			Display display = Display.getCurrent();
			ImageDataProvider provider = zoom -> render(zoom / 100.0, badge);
			image = new Image(display, provider);
			CACHE.put(key, image);
			Image created = image;
			display.disposeExec(created::dispose);
		}
		return image;
	}

	/** Renders the icon at a scale factor with SAMPLES x SAMPLES supersampling. */
	static ImageData render(double scale, boolean badge) {
		int size = (int) Math.round(SIZE * scale);
		ImageData data = new ImageData(size, size, 24, new PaletteData(0xFF0000, 0xFF00, 0xFF));
		for (int py = 0; py < size; py++) {
			for (int px = 0; px < size; px++) {
				double r = 0;
				double g = 0;
				double b = 0;
				double a = 0;
				for (int sy = 0; sy < SAMPLES; sy++) {
					for (int sx = 0; sx < SAMPLES; sx++) {
						double x = (px + (sx + 0.5) / SAMPLES) / scale;
						double y = (py + (sy + 0.5) / SAMPLES) / scale;
						int rgba = sample(x, y, badge);
						double alpha = (rgba & 0xFF) / 255.0;
						r += (rgba >>> 24 & 0xFF) * alpha;
						g += (rgba >>> 16 & 0xFF) * alpha;
						b += (rgba >>> 8 & 0xFF) * alpha;
						a += alpha;
					}
				}
				int n = SAMPLES * SAMPLES;
				if (a > 0) {
					data.setPixel(px, py, (int) (r / a) << 16 | (int) (g / a) << 8 | (int) (b / a));
				}
				data.setAlpha(px, py, (int) Math.round(a / n * 255));
			}
		}
		return data;
	}

	/** The color (RGBA) of the icon at a point in 16x16 icon coordinates; topmost shape first. */
	private static int sample(double x, double y, boolean badge) {
		if (badge) {
			double bx = 12;
			double by = 12;
			double d = Math.hypot(x - bx, y - by);
			if (d <= 3.6) {
				boolean check = segmentDistance(x, y, 10.3, 12.1, 11.5, 13.3) < 0.65
						|| segmentDistance(x, y, 11.5, 13.3, 13.8, 10.8) < 0.65;
				return check ? 0xFFFFFFFF : 0x0E7C7BFF;
			}
			if (d <= 4.4) {
				return 0; // a transparent gap separates the badge from the bulb
			}
		}
		// Highlight on the glass.
		if (Math.hypot(x - 6.3, y - 4.6) <= 1.25) {
			return 0xFFFBEBE6;
		}
		double cx = 8;
		double cy = 6.4;
		double glass = Math.hypot(x - cx, y - cy);
		if (glass <= 4.9) {
			if (glass >= 4.0) {
				return 0xB45309FF;
			}
			// Vertical gradient from light amber to amber.
			double t = Math.clamp((y - 1.5) / 9, 0, 1);
			int red = (int) (0xFD + (0xF5 - 0xFD) * t);
			int green = (int) (0xD3 + (0x9E - 0xD3) * t);
			int blue = (int) (0x4D + (0x0B - 0x4D) * t);
			return red << 24 | green << 16 | blue << 8 | 0xFF;
		}
		// Neck and screw base.
		if (y >= 10.4 && y <= 11.6 && x >= 5.9 && x <= 10.1) {
			return 0x64748BFF;
		}
		if (y >= 12.1 && y <= 13.2 && x >= 6.2 && x <= 9.8) {
			return 0x64748BFF;
		}
		if (y >= 13.6 && y <= 14.6 && x >= 7 && x <= 9) {
			return 0x475569FF;
		}
		return 0;
	}

	private static double segmentDistance(double x, double y, double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		double t = Math.clamp(((x - x1) * dx + (y - y1) * dy) / (dx * dx + dy * dy), 0, 1);
		return Math.hypot(x - (x1 + t * dx), y - (y1 + t * dy));
	}
}
