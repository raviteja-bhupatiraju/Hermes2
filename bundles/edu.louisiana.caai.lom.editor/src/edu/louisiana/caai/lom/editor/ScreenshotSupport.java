/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Documentation aid: when the system property {@value #PROPERTY} names a folder, the editor shows each of its
 * pages in turn so that {@code docs/capture-screenshots.ps1} can capture them. For each page the editor writes
 * the page's file name into {@code <folder>/current}; when all pages were shown it writes {@code <folder>/done}
 * (containing {@code dirty=true|false}, since merely viewing pages must not change the document). The capture
 * itself happens outside the application (Win32 PrintWindow), which also works on a locked workstation.
 * Inactive unless the property is set.
 */
final class ScreenshotSupport {

	static final String PROPERTY = "hlom.screenshots";

	private static final int INITIAL_DELAY = 4000;
	private static final int RENDER_DELAY = 1200;
	private static final int CAPTURE_TIME = 1500;

	private ScreenshotSupport() {
	}

	/** The folder to write screenshots to, or {@code null} if screenshots are not requested. */
	static File folder() {
		String folder = System.getProperty(PROPERTY);
		return folder == null || folder.isBlank() ? null : new File(folder);
	}

	/**
	 * Shows each page in turn and announces it for capture.
	 *
	 * @param showPage  activates a page by index
	 * @param pageNames the page names, used in the file names
	 * @param dirty     reports whether the editor is dirty
	 */
	static void capture(Shell shell, File folder, String[] pageNames, IntConsumer showPage, BooleanSupplier dirty) {
		folder.mkdirs();
		Display display = shell.getDisplay();
		shell.setMaximized(true);
		display.timerExec(INITIAL_DELAY, new Runnable() {
			private int page;

			@Override
			public void run() {
				if (shell.isDisposed()) {
					return;
				}
				if (page < pageNames.length) {
					int current = page++;
					showPage.accept(current);
					display.timerExec(RENDER_DELAY, () -> {
						write(new File(folder, "current"), String.format(Locale.ROOT, "%02d-%s", current + 1,
								pageNames[current].toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-")));
						display.timerExec(CAPTURE_TIME, this);
					});
				} else {
					write(new File(folder, "done"), "dirty=" + dirty.getAsBoolean());
				}
			}
		});
	}

	private static void write(File file, String content) {
		try {
			Files.writeString(file.toPath(), content);
		} catch (IOException e) {
			HlomEditorPlugin.INSTANCE.log(e);
		}
	}
}
