/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.views;

import org.eclipse.osgi.util.NLS;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SugiyamaLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.medbiq.model.DocumentGraph;
import edu.louisiana.caai.medbiq.model.DocumentGraph.Edge;
import edu.louisiana.caai.medbiq.model.DocumentGraph.Node;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;

/**
 * Window &gt; Document Graph: the elements of the active document and the references between them as a graph (the
 * sequence blocks of a Curriculum Inventory with their events, nested blocks, competency objects and levels; the
 * components and scales of a performance framework; the competencies of a framework with their broader, narrower
 * and related competencies). Double-clicking an element shows it in the editor; the view follows the selection of
 * the editor.
 */
public final class DocumentGraphView extends DocumentViewPart implements IZoomableWorkbenchPart {

	/** The id of the view. */
	public static final String ID = "edu.louisiana.caai.lom.editor.views.DocumentGraphView";

	/** The colors of the kinds of elements, in order of appearance. */
	private static final List<RGB> PALETTE = List.of(new RGB(0x25, 0x63, 0xEB), HlomTheme.TEAL, HlomTheme.AMBER,
			new RGB(0x7C, 0x3A, 0xED), HlomTheme.ACCENT, new RGB(0x05, 0x96, 0x69), HlomTheme.SLATE);

	private GraphViewer viewer;
	private DocumentGraph graph = new DocumentGraph(List.of(), List.of());
	private final Set<String> hiddenKinds = new LinkedHashSet<>();
	private final Map<String, Color> colors = new HashMap<>();
	private IMenuManager kindsMenu;

	@Override
	protected Control createContent(Composite parent) {
		viewer = new GraphViewer(parent, SWT.NONE);
		viewer.setContentProvider(new Content());
		viewer.setLabelProvider(new Labels());
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		viewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm(), false);
		viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(org.eclipse.jface.viewers.Viewer v, Object parentElement, Object element) {
				return !(element instanceof Node node) || !hiddenKinds.contains(node.kind());
			}
		});
		viewer.addDoubleClickListener(event -> {
			if (event.getSelection() instanceof IStructuredSelection selection
					&& selection.getFirstElement() instanceof Node node && editor() != null) {
				getSite().getPage().activate(editor());
				editor().reveal(node.element());
			}
		});
		contributeActions();
		return viewer.getControl();
	}

	private void contributeActions() {
		IToolBarManager toolbar = getViewSite().getActionBars().getToolBarManager();
		IMenuManager menu = getViewSite().getActionBars().getMenuManager();
		layoutAction(menu, Messages.DocumentGraphView_Layered, new SugiyamaLayoutAlgorithm(), true);
		layoutAction(menu, Messages.DocumentGraphView_Tree, new TreeLayoutAlgorithm(), false);
		layoutAction(menu, Messages.DocumentGraphView_Radial, new RadialLayoutAlgorithm(), false);
		layoutAction(menu, Messages.DocumentGraphView_Spring, new SpringLayoutAlgorithm(), false);
		menu.add(new Separator());
		kindsMenu = new org.eclipse.jface.action.MenuManager(Messages.DocumentGraphView_Show);
		menu.add(kindsMenu);
		menu.add(new Separator());
		menu.add(new ZoomContributionViewItem(this));
		toolbar.add(new Action(Messages.DocumentGraphView_ArrangeAgain, org.eclipse.jface.resource.ImageDescriptor.createFromImage(HlomTheme.image("icons/library/refresh.png"))) {
			{
				setToolTipText(Messages.DocumentGraphView_ArrangeTheGraphAgain);
			}

			@Override
			public void run() {
				viewer.applyLayout();
			}
		});
	}

	private void layoutAction(IMenuManager menu, String label, LayoutAlgorithm algorithm, boolean checked) {
		Action action = new Action(label, IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (isChecked()) {
					viewer.setLayoutAlgorithm(algorithm, true);
				}
			}
		};
		action.setChecked(checked);
		menu.add(action);
	}

	@Override
	protected String show(HlomMetadataEditor editor) {
		if (!(editor.documentType() instanceof MedbiqDocumentType type)) {
			return Messages.DocumentGraphView_TheDocumentGraphShowsThe;
		}
		graph = DocumentGraph.of(type, editor.documentRoot());
		if (graph.edges().isEmpty()) {
			return NLS.bind(Messages.DocumentGraphView_ThisDocumentHasNoReferences, type.label());
		}
		updateKinds();
		viewer.setInput(graph);
		viewer.applyLayout();
		return null;
	}

	private void updateKinds() {
		kindsMenu.removeAll();
		for (String kind : graph.kinds()) {
			colors.computeIfAbsent(kind, k -> HlomTheme.color(PALETTE.get(colors.size() % PALETTE.size())));
			Action action = new Action(kind + "s", IAction.AS_CHECK_BOX) {
				@Override
				public void run() {
					if (isChecked()) {
						hiddenKinds.remove(kind);
					} else {
						hiddenKinds.add(kind);
					}
					viewer.refresh();
					viewer.applyLayout();
				}
			};
			action.setChecked(!hiddenKinds.contains(kind));
			kindsMenu.add(action);
		}
	}

	@Override
	protected String emptyMessage() {
		return Messages.DocumentGraphView_OpenADocumentToSee;
	}

	/** Selects the node of an element (if it is shown). */
	public void select(Object element) {
		for (Node node : graph.nodes()) {
			if (node.element() == element) {
				viewer.setSelection(new StructuredSelection(node), true);
				return;
			}
		}
	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	@Override
	public void setFocus() {
		if (viewer != null) {
			viewer.getControl().setFocus();
		}
	}

	/** The graph for tests. */
	public DocumentGraph graph() {
		return graph;
	}

	private final class Content implements IGraphEntityContentProvider {
		@Override
		public Object[] getElements(Object input) {
			return graph.nodes().toArray();
		}

		@Override
		public Object[] getConnectedTo(Object entity) {
			return graph.edges().stream().filter(edge -> edge.source() == entity).map(Edge::target).distinct().toArray();
		}
	}

	private final class Labels extends LabelProvider implements IEntityStyleProvider {
		@Override
		public String getText(Object element) {
			return switch (element) {
				case Node node -> shorten(node.label());
				case EntityConnectionData connection -> edgeLabel(connection);
				default -> "";
			};
		}

		private String edgeLabel(EntityConnectionData connection) {
			for (Edge edge : graph.edges()) {
				if (edge.source() == connection.source && edge.target() == connection.dest) {
					return edge.label().endsWith(" reference") ? "" : edge.label();
				}
			}
			return "";
		}

		private static String shorten(String text) {
			return text.length() <= 40 ? text : text.substring(0, 38) + "…";
		}

		@Override
		public Color getNodeHighlightColor(Object entity) {
			return HlomTheme.color(HlomTheme.BAR);
		}

		@Override
		public Color getBorderColor(Object entity) {
			return entity instanceof Node node ? colors.get(node.kind()) : null;
		}

		@Override
		public Color getBorderHighlightColor(Object entity) {
			return HlomTheme.color(HlomTheme.ACCENT);
		}

		@Override
		public int getBorderWidth(Object entity) {
			return 1;
		}

		@Override
		public Color getBackgroundColour(Object entity) {
			return HlomTheme.color(HlomTheme.SURFACE);
		}

		@Override
		public Color getForegroundColour(Object entity) {
			return entity instanceof Node node ? colors.get(node.kind()) : HlomTheme.color(HlomTheme.INK);
		}

		@Override
		public IFigure getTooltip(Object entity) {
			return entity instanceof Node node
					? new Label(node.kind() + " " + node.key() + (node.title() == null ? "" : "\n" + node.title()))
					: null;
		}

		@Override
		public boolean fisheyeNode(Object entity) {
			return false;
		}
	}
}
