/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.splash.BasicSplashHandler;

/**
 * The splash screen with the progress bar and message placed by the product's {@code startup*} properties, like
 * Eclipse's default handler. The launcher's bitmap has only the flat shapes of the splash screen: the launcher
 * treats it as 96 dpi and enlarges it on high resolution displays, which would blur text and logo. Once Java runs,
 * it is replaced by a background with the logo in the display's resolution, and the text is drawn by the platform,
 * as sharp as the progress message.
 */
public final class HlomSplashHandler extends BasicSplashHandler {

	/**
	 * A line of text: its font, left edge and baseline in pixels at 100%, the width it should have (the font size is
	 * chosen to fit it) and its color. The same as {@code SPLASH_TEXT} in {@code icons-src/gen_icons.py}, which draws
	 * the text into the launcher's {@code splash.bmp}.
	 */
	private record Line(String text, String font, int left, int baseline, int width, int color) {
	}

	private static final String SEMIBOLD = "Segoe UI Semibold";
	private static final String REGULAR = "Segoe UI";

	private static final List<Line> LINES = List.of(
			new Line("Hermes 2", SEMIBOLD, 41, 156, 167, 0x1E293B),
			new Line("MedBiquitous Editor", SEMIBOLD, 41, 198, 194, 0x1E293B),
			new Line("Standards for health professions education", REGULAR, 41, 220, 296, 0x475569),
			new Line("Ravi Teja Bhupatiraju", SEMIBOLD, 41, 298, 103, 0x475569),
			new Line("University of Louisiana at Lafayette  ·  Center for Applied Artificial Intelligence (CAAI)",
					REGULAR, 41, 313, 337, 0x94A3B8));

	@Override
	public void init(Shell splash) {
		super.init(splash);
		IProduct product = Platform.getProduct();
		setProgressRect(StringConverter.asRectangle(property(product, IProductConstants.STARTUP_PROGRESS_RECT),
				new Rectangle(10, 10, 300, 15)));
		setMessageRect(StringConverter.asRectangle(property(product, IProductConstants.STARTUP_MESSAGE_RECT),
				new Rectangle(10, 35, 300, 15)));
		setForeground(rgb(parseColor(property(product, IProductConstants.STARTUP_FOREGROUND_COLOR), 0x475569)));

		ImageData normal = load("/icons/app/splash-background.png");
		ImageData large = load("/icons/app/splash-background@2x.png");
		if (normal != null && large != null) {
			// Other zooms are scaled by SWT from the 100% image (an image of another size would not fit the shell).
			Image background = new Image(splash.getDisplay(),
					(ImageDataProvider) zoom -> zoom == 100 ? normal : zoom == 200 ? large : null);
			splash.setBackgroundImage(background);
			List<Font> fonts = new ArrayList<>();
			getContent().addPaintListener(event -> {
				if (fonts.isEmpty()) {
					LINES.forEach(line -> fonts.add(fittedFont(event.display, event.gc, line)));
				}
				for (int i = 0; i < LINES.size(); i++) {
					Line line = LINES.get(i);
					event.gc.setFont(fonts.get(i));
					Color color = new Color(rgb(line.color()));
					event.gc.setForeground(color);
					int ascent = event.gc.getFontMetrics().getAscent();
					event.gc.drawText(line.text(), line.left(), line.baseline() - ascent, SWT.DRAW_TRANSPARENT);
				}
			});
			splash.addDisposeListener(_ -> {
				background.dispose();
				fonts.forEach(Font::dispose);
			});
		}
		getContent();
	}

	/** The font of a line in the (whole point) size whose text width is closest to the line's width. */
	private static Font fittedFont(Display display, GC gc, Line line) {
		Font best = null;
		int bestDifference = Integer.MAX_VALUE;
		for (int points = 6; points <= 40; points++) {
			Font font = new Font(display, new FontData(line.font(), points, SWT.NORMAL));
			gc.setFont(font);
			int difference = Math.abs(gc.textExtent(line.text()).x - line.width());
			if (difference < bestDifference) {
				if (best != null) {
					best.dispose();
				}
				best = font;
				bestDifference = difference;
			} else {
				font.dispose();
			}
		}
		return best;
	}

	private static String property(IProduct product, String key) {
		return product == null ? null : product.getProperty(key);
	}

	private static int parseColor(String hex, int fallback) {
		try {
			return hex == null ? fallback : Integer.parseInt(hex, 16);
		} catch (NumberFormatException _) {
			return fallback;
		}
	}

	private static RGB rgb(int color) {
		return new RGB(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
	}

	private static ImageData load(String path) {
		URL url = HlomSplashHandler.class.getResource(path);
		if (url == null) {
			return null;
		}
		try (InputStream in = url.openStream()) {
			return new ImageData(in);
		} catch (IOException | RuntimeException e) {
			HlomEditorPlugin.INSTANCE.log(e);
			return null;
		}
	}
}
