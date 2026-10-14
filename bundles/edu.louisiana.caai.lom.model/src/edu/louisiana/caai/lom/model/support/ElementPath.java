/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

/**
 * The path of an XML element from the document root, such as {@code /lom/general/keyword[2]/string}, as used in
 * problem markers and search results. Indexes count the elements of the same feature (1-based, {@code [1]} is
 * left out). Model objects and paths convert in both directions, independent of the generated model.
 *
 * @param steps the elements from the root element down
 */
public record ElementPath(List<Step> steps) {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;
	private static final Pattern STEP = Pattern.compile("([^/\\[\\]]+)(?:\\[(\\d+)])?");

	/**
	 * An element of a path.
	 *
	 * @param namespace the namespace URI, empty if unknown (a parsed path)
	 * @param name      the local name
	 * @param index     the 0-based index among the elements of the same feature
	 */
	public record Step(String namespace, String name, int index) {
	}

	public ElementPath {
		steps = List.copyOf(steps);
	}

	/** The path of a model object (empty for a document root or an object without XML element names). */
	public static ElementPath of(EObject object) {
		List<Step> steps = new ArrayList<>();
		for (EObject o = object; o != null && o.eContainer() != null; o = o.eContainer()) {
			EStructuralFeature feature = o.eContainmentFeature();
			String name = feature == null ? null : META.getName(feature);
			if (name == null) {
				break;
			}
			int index = o.eContainer().eGet(feature) instanceof List<?> siblings ? Math.max(0, siblings.indexOf(o)) : 0;
			String namespace = META.getNamespace(feature);
			steps.addFirst(new Step(namespace == null ? "" : namespace, name, index));
		}
		return new ElementPath(steps);
	}

	/** Parses the {@link #toString()} form; namespaces are not part of it. */
	public static ElementPath parse(String path) {
		List<Step> steps = new ArrayList<>();
		for (String part : path.split("/")) {
			if (part.isEmpty() || part.startsWith("@")) {
				continue;
			}
			Matcher matcher = STEP.matcher(part);
			if (!matcher.matches()) {
				break;
			}
			String name = matcher.group(1);
			int colon = name.indexOf(':');
			steps.add(new Step("", colon >= 0 ? name.substring(colon + 1) : name,
					matcher.group(2) == null ? 0 : Integer.parseInt(matcher.group(2)) - 1));
		}
		return new ElementPath(steps);
	}

	/** Whether the path has no elements. */
	public boolean isEmpty() {
		return steps.isEmpty();
	}

	/** The local names of the elements. */
	public List<String> names() {
		return steps.stream().map(Step::name).toList();
	}

	/** The path of a child element. */
	public ElementPath child(String namespace, String name, int index) {
		List<Step> child = new ArrayList<>(steps);
		child.add(new Step(namespace, name, index));
		return new ElementPath(child);
	}

	/**
	 * The deepest model object along the path, starting at a document root; {@code null} if not even the first
	 * step exists. Steps without a namespace match any element of that local name.
	 */
	public EObject resolve(EObject documentRoot) {
		EObject current = documentRoot;
		EObject found = null;
		for (Step step : steps) {
			EStructuralFeature feature = feature(current, step);
			if (feature == null) {
				break;
			}
			Object value = current.eGet(feature);
			Object next = value instanceof List<?> list ? (step.index() < list.size() ? list.get(step.index()) : null)
					: step.index() == 0 ? value : null;
			if (!(next instanceof EObject object)) {
				break;
			}
			current = object;
			found = object;
		}
		return found;
	}

	private static EStructuralFeature feature(EObject object, Step step) {
		if (!step.namespace().isEmpty()) {
			return META.getElement(object.eClass(), step.namespace(), step.name());
		}
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE && step.name().equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder path = new StringBuilder();
		for (Step step : steps) {
			path.append('/').append(step.name());
			if (step.index() > 0) {
				path.append('[').append(step.index() + 1).append(']');
			}
		}
		return path.isEmpty() ? "/" : path.toString();
	}
}
