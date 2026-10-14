/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.nebula.widgets.chips.Chips;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.pages.AbstractLomPage;

/**
 * The form pages of every sample document fit the editor: no field sticks out of its section, and no token or
 * field label is cut off, in a wide and a narrow window.
 */
class FormLayoutTest extends AbstractUiTest {

	/** Tolerance for rounding at fractional display scales. */
	private static final int SLACK = 2;

	@Test
	void formsFitTheEditorWidth() {
		List<Path> samples = new ArrayList<>();
		samples.addAll(samples("edu.louisiana.caai.lom.editor"));
		samples.addAll(samples("edu.louisiana.caai.medbiq.model"));
		Shell shell = window().getShell();
		Rectangle bounds = shell.getBounds();
		List<String> problems = new ArrayList<>();
		try {
			for (int width : new int[] { 1600, 1000 }) {
				shell.setSize(width, 1000);
				for (Path sample : samples) {
					HlomMetadataEditor editor = open(sample);
					@SuppressWarnings("unchecked")
					List<AbstractLomPage> pages = (List<AbstractLomPage>) field(editor, "formPages");
					for (int i = 0; i < pages.size(); i++) {
						setActivePage(editor, i);
						dispatch(30);
						Control control = pageControl(editor, i);
						if (control instanceof ScrolledForm form) {
							form.reflow(true);
							dispatch(10);
							check(form.getBody(), width + " " + sample.getFileName() + " / " + pageText(editor, i),
									problems);
						}
					}
					page().closeEditor(editor, false);
					dispatch(20);
				}
			}
		} finally {
			shell.setBounds(bounds);
		}
		assertEquals(List.of(), problems, () -> problems.size() + " layout problems:\n" + String.join("\n", problems));
	}

	/** Copies the sample documents of a bundle to the temporary folder. */
	private List<Path> samples(String bundleId) {
		List<URL> entries = Collections.list(Platform.getBundle(bundleId).findEntries("samples", "*.xml", true));
		List<Path> files = new ArrayList<>();
		for (URL entry : entries) {
			Path target = tempDir.resolve(bundleId + "-" + Path.of(entry.getPath()).getFileName());
			try (InputStream in = entry.openStream()) {
				Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			files.add(target);
		}
		files.sort(null);
		return files;
	}

	private static void check(Composite composite, String where, List<String> problems) {
		Rectangle area = composite.getClientArea();
		for (Control child : composite.getChildren()) {
			if (!child.getVisible()) {
				continue;
			}
			Rectangle b = child.getBounds();
			if (b.width > 0 && b.x + b.width > area.width + SLACK && !(composite instanceof ScrolledComposite)) {
				problems.add(where + ": " + describe(child) + " ends at " + (b.x + b.width) + " in "
						+ describe(composite) + " " + area.width + " wide");
			}
			if (child instanceof Chips || child instanceof Label label && (label.getStyle() & SWT.WRAP) == 0
					&& !label.getText().isEmpty() && b.width > 0) {
				Point wanted = child.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if (wanted.x > b.width + SLACK) {
					problems.add(where + ": " + describe(child) + " cut off (" + b.width + " of " + wanted.x + ")");
				}
			}
			if (child instanceof Composite nested && !(child instanceof Chips)) {
				check(nested, where, problems);
			}
		}
	}

	private static String describe(Control control) {
		String text = switch (control) {
			case Label label -> " '" + label.getText() + "'";
			case Chips chips -> " '" + chips.getText() + "'";
			default -> "";
		};
		return control.getClass().getSimpleName() + text;
	}
}
