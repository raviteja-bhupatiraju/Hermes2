/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.compare;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.compare.CompareTree.CategoryNode;
import edu.louisiana.caai.lom.editor.compare.CompareTree.Column;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.compare.ChangeKind;
import edu.louisiana.caai.lom.model.support.compare.DiffResult;
import edu.louisiana.caai.lom.model.support.compare.DocumentDiff;
import edu.louisiana.caai.lom.model.support.compare.HlomDiff;

/**
 * The semantic comparison of two MedBiquitous documents as a content viewer of the Eclipse compare framework: every
 * comparison of documents — <em>Compare With &gt; Each Other</em>, <em>Local History</em>, the Git history and
 * Synchronize view of EGit — shows the changed fields grouped by category (repeated elements matched by meaning,
 * so reordering is no change). The compare editor's <em>Switch Compare Viewer</em> menu still offers the text
 * comparison for line-by-line merging.
 */
public final class SemanticCompareViewer extends Viewer {

	/** Creates the viewer for the {@code org.eclipse.compare.contentMergeViewers} extension. */
	public static final class Creator implements IViewerCreator {
		@Override
		public Viewer createViewer(Composite parent, CompareConfiguration configuration) {
			return new SemanticCompareViewer(parent, configuration);
		}
	}

	private final CompareConfiguration configuration;
	private final Composite control;
	private final Label summary;
	private final Button unchanged;
	private final TreeViewer tree;
	private Object input;
	private DiffResult result;

	public SemanticCompareViewer(Composite parent, CompareConfiguration configuration) {
		this.configuration = configuration;
		control = new Composite(parent, SWT.NONE);
		control.setData(CompareUI.COMPARE_VIEWER_TITLE, "Semantic Comparison");
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(control);

		Composite bar = new Composite(control, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(8, 6).spacing(12, 0).applyTo(bar);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(bar);
		summary = new Label(bar, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(summary);
		unchanged = new Button(bar, SWT.CHECK);
		unchanged.setText(Messages.SemanticCompareViewer_ShowUnchangedFields);
		unchanged.addListener(SWT.Selection, _ -> refresh());

		Composite treeComposite = new Composite(control, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(treeComposite);
		TreeColumnLayout layout = new TreeColumnLayout();
		treeComposite.setLayout(layout);
		tree = new TreeViewer(treeComposite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.getTree().setHeaderVisible(true);
		tree.getTree().setLinesVisible(true);
		tree.setContentProvider(new CompareTree.ContentProvider());
		column(layout, Messages.SemanticCompareViewer_Field, 34, Column.FIELD);
		column(layout, configuration == null ? "Left" : configuration.getLeftLabel(null), 33, Column.LEFT);
		column(layout, configuration == null ? "Right" : configuration.getRightLabel(null), 33, Column.RIGHT);
		ColumnViewerToolTipSupport.enableFor(tree);
	}

	private void column(TreeColumnLayout layout, String title, int weight, Column column) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(tree, SWT.LEFT);
		viewerColumn.getColumn().setText(title == null ? "" : title);
		viewerColumn.setLabelProvider(new CompareTree.LabelProvider(column));
		layout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(weight, 120, true));
	}

	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public Object getInput() {
		return input;
	}

	@Override
	public void setInput(Object input) {
		this.input = input;
		result = null;
		if (input instanceof ICompareInput compare) {
			if (configuration != null) {
				tree.getTree().getColumn(1).setText(nonNull(configuration.getLeftLabel(compare)));
				tree.getTree().getColumn(2).setText(nonNull(configuration.getRightLabel(compare)));
			}
			result = compare(compare.getLeft(), compare.getRight());
		}
		refresh();
	}

	private static String nonNull(String text) {
		return text == null ? "" : text;
	}

	/** Compares two sides (either may be missing: an added or deleted document). */
	static DiffResult compare(ITypedElement left, ITypedElement right) {
		Resource leftResource = load(left, "left");
		Resource rightResource = load(right, "right");
		DocumentRoot leftRoot = HlomResources.getDocumentRoot(leftResource);
		DocumentRoot rightRoot = HlomResources.getDocumentRoot(rightResource);
		boolean hlom = (leftRoot != null || leftResource.getContents().isEmpty())
				&& (rightRoot != null || rightResource.getContents().isEmpty()) && (leftRoot != null || rightRoot != null);
		return hlom ? HlomDiff.compare(leftResource, rightResource) : DocumentDiff.compare(leftResource, rightResource);
	}

	private static Resource load(ITypedElement element, String side) {
		String text = "";
		if (element instanceof IStreamContentAccessor accessor) {
			try (InputStream in = accessor.getContents()) {
				text = in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
			} catch (IOException | CoreException _) {
				text = "";
			}
		}
		if (text.startsWith("﻿")) {
			text = text.substring(1);
		}
		String name = element == null ? side : element.getName();
		return DocumentCheck.load(text, URI.createURI("compare:/" + side + "/" + URI.encodeSegment(name, false)));
	}

	@Override
	public void refresh() {
		if (control.isDisposed()) {
			return;
		}
		List<CategoryNode> nodes = CompareTree.nodes(result, unchanged.getSelection(), null);
		tree.setInput(nodes);
		tree.expandToLevel(2);
		if (result == null) {
			summary.setText(Messages.SemanticCompareViewer_TheDocumentsCouldNotBe);
		} else if (result.isIdentical()) {
			summary.setText(Messages.SemanticCompareViewer_TheDocumentsHaveTheSame);
		} else {
			summary.setText(NLS.bind(Messages.SemanticCompareViewer_ChangedAddedRemoved, new Object[] { result.count(ChangeKind.CHANGED), result.count(ChangeKind.ADDED), result.count(ChangeKind.REMOVED) }));
		}
		summary.setForeground(HlomTheme.color(HlomTheme.SLATE));
		summary.getParent().layout();
	}

	@Override
	public ISelection getSelection() {
		return tree.getSelection();
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		tree.setSelection(selection == null ? StructuredSelection.EMPTY : selection, reveal);
	}
}
