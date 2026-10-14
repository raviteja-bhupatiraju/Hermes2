/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.References.Occurrence;
import edu.louisiana.caai.medbiq.model.References.Target;

/**
 * The structure of a document as a graph: its referenceable elements (such as the events, sequence blocks,
 * competency objects and academic levels of a Curriculum Inventory) and the references between them, plus the
 * relations a standard defines in other ways (the broader, narrower and related competencies of a competency
 * framework). Shown by the editor's graph view.
 *
 * @param nodes the elements, in document order
 * @param edges the references and relations
 */
public record DocumentGraph(List<Node> nodes, List<Edge> edges) {

	/**
	 * An element.
	 *
	 * @param kind    what it is, in words (such as {@code Event})
	 * @param key     its identifier (or its position, for elements without one)
	 * @param title   its title, or {@code null}
	 * @param element the element
	 */
	public record Node(String kind, String key, String title, EObject element) {

		/** The text of the node: the title, else the key. */
		public String label() {
			return title == null || title.isBlank() ? key : title;
		}
	}

	/**
	 * A reference or relation.
	 *
	 * @param source the referring element
	 * @param target the referenced element
	 * @param label  the kind of the edge (such as {@code Event reference} or {@code broader})
	 */
	public record Edge(Node source, Node target, String label) {
	}

	public DocumentGraph {
		nodes = List.copyOf(nodes);
		edges = List.copyOf(edges);
	}

	/** The nodes of a kind. */
	public List<Node> nodes(String kind) {
		return nodes.stream().filter(node -> node.kind().equals(kind)).toList();
	}

	/** The kinds of the nodes, in the order they first occur. */
	public List<String> kinds() {
		return nodes.stream().map(Node::kind).distinct().toList();
	}

	/** Builds the graph of a document. */
	public static DocumentGraph of(MedbiqDocumentType type, EObject documentRoot) {
		EObject root = documentRoot == null ? null : Paths.rootElement(documentRoot);
		if (type == null || root == null) {
			return new DocumentGraph(List.of(), List.of());
		}
		Builder builder = new Builder();
		List<References.Kind> kinds = type.references();
		for (References.Kind kind : kinds) {
			for (Target target : References.targets(root, kind)) {
				builder.node(target.kind().label(), target.key(), target.title(), target.element());
			}
		}
		for (Occurrence occurrence : References.occurrences(root, kinds)) {
			Target target = References.resolve(root, occurrence.kind(), occurrence.text());
			Node to = target == null ? null : builder.nodes.get(target.element());
			Node from = builder.source(occurrence.owner());
			if (from != null && to != null && from != to) {
				builder.edges.add(new Edge(from, to, occurrence.kind().label() + " reference"));
			}
		}
		type.content().graph(type, root, builder);
		return new DocumentGraph(new ArrayList<>(builder.nodes.values()), builder.edges);
	}

	/** Collects the nodes and edges; standards add their own relations with it. */
	public static final class Builder {

		private final Map<EObject, Node> nodes = new IdentityHashMap<>();
		private final List<Edge> edges = new ArrayList<>();
		private final Map<String, Node> byKey = new LinkedHashMap<>();

		/** Adds an element (once) and returns its node. */
		public Node node(String kind, String key, String title, EObject element) {
			Node existing = nodes.get(element);
			if (existing != null) {
				return existing;
			}
			Node node = new Node(kind, key, title, element);
			nodes.put(element, node);
			byKey.putIfAbsent(kind + '\u0000' + key, node);
			return node;
		}

		/** The node of a kind by key, or {@code null}. */
		public Node find(String kind, String key) {
			return byKey.get(kind + '\u0000' + key);
		}

		/** Adds an edge. */
		public void edge(Node source, Node target, String label) {
			if (source != null && target != null) {
				edges.add(new Edge(source, target, label));
			}
		}

		/**
		 * The node referring through an element: the nearest node containing it, else the nearest repeated element
		 * containing it (such as an integration block), added as a node of its own.
		 */
		Node source(EObject owner) {
			for (EObject object = owner; object != null; object = object.eContainer()) {
				Node node = nodes.get(object);
				if (node != null) {
					return node;
				}
			}
			for (EObject object = owner; object != null && object.eContainer() != null; object = object.eContainer()) {
				EStructuralFeature containment = object.eContainmentFeature();
				if (containment != null && containment.isMany()) {
					String name = References.elementName(object);
					String key = Paths.text(object, "@id");
					String title = Paths.text(object, "Title");
					int index = ((List<?>) object.eContainer().eGet(containment)).indexOf(object) + 1;
					return node(FieldLabels.words(name), key != null ? key : String.valueOf(index), title, object);
				}
			}
			return null;
		}
	}
}
