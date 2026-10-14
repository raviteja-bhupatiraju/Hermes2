/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.compare.SemanticCompareViewer;

/**
 * The compare framework (Compare With &gt; Each Other, Local History, EGit) shows MedBiquitous documents with the
 * semantic comparison.
 */
class CompareFrameworkTest extends AbstractUiTest {

	private record Side(String name, String text) implements ITypedElement, IStreamContentAccessor {
		@Override
		public String getName() {
			return name;
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public String getType() {
			return "xml";
		}

		@Override
		public InputStream getContents() {
			return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
		}
	}

	@Test
	void documentsAreComparedSemantically() throws Exception {
		Path sample = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		String before = Files.readString(sample, StandardCharsets.UTF_8);
		String after = before.replace("<metadataSchema>SCORM_CAM_v1.3</metadataSchema>", "");
		DiffNode input = new DiffNode(Differencer.CHANGE, null, new Side("before.xml", before), new Side("after.xml", after));
		Shell shell = new Shell(display(), SWT.SHELL_TRIM);
		try {
			Viewer viewer = CompareUI.findContentViewer(null, input, shell, new CompareConfiguration());
			assertInstanceOf(SemanticCompareViewer.class, viewer, "the semantic viewer for MedBiquitous documents");
			viewer.setInput(input);
			Tree tree = tree(viewer.getControl());
			assertTrue(tree.getItemCount() > 0, "the changed category is listed");
			assertNoDialogs();
		} finally {
			shell.dispose();
		}
	}

	private static Tree tree(Control control) {
		if (control instanceof Tree tree) {
			return tree;
		}
		if (control instanceof Composite composite) {
			for (Control child : composite.getChildren()) {
				Tree tree = tree(child);
				if (tree != null) {
					return tree;
				}
			}
		}
		return null;
	}
}
