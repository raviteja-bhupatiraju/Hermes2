/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import edu.louisiana.caai.lom.model.support.ElementPath;

/**
 * Searches the text values (element content and attribute values) of a document, independent of the generated
 * model: every element and attribute value is visited in document order.
 */
public final class DocumentSearch {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/**
	 * What to search for.
	 *
	 * @param text          the text or regular expression
	 * @param caseSensitive whether case matters
	 * @param regex         whether {@code text} is a regular expression ({@link java.util.regex.Pattern} syntax)
	 * @param wholeWord     whether matches must be whole words
	 * @param fields        the field groups to search in; empty for all values
	 */
	public record Query(String text, boolean caseSensitive, boolean regex, boolean wholeWord,
			List<FieldGroup> fields) {

		public Query {
			fields = List.copyOf(fields);
		}

		/** A plain, case-insensitive query in all fields. */
		public static Query of(String text) {
			return new Query(text, false, false, false, List.of());
		}

		/** The pattern of the query; throws {@link PatternSyntaxException} for an invalid regular expression. */
		public Pattern pattern() {
			String expression = regex ? text : Pattern.quote(text);
			if (wholeWord) {
				expression = "(?<![\\p{L}\\p{N}_])(?:" + expression + ")(?![\\p{L}\\p{N}_])";
			}
			int flags = Pattern.UNICODE_CASE | (caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
			return Pattern.compile(expression, flags);
		}

		/** Whether a value at a path is searched. */
		public boolean includes(List<String> names, String attribute) {
			return fields.isEmpty() || fields.stream().anyMatch(field -> field.matches(names, attribute));
		}

		/** A short description, such as {@code "sepsis" in titles, keywords}. */
		public String describe() {
			String label = '"' + text + '"';
			if (!fields.isEmpty()) {
				label += " in " + String.join(", ", fields.stream().map(f -> f.label().toLowerCase(Locale.ROOT)).toList());
			}
			return label;
		}
	}

	/**
	 * A text value of a document.
	 *
	 * @param path      the element path ({@link ElementPath#toString()} form; attributes end with {@code /@name})
	 * @param element   the model object holding the value, or {@code null} for the document root
	 * @param names     the local names of the element path
	 * @param attribute the attribute name, or {@code null} for element content
	 * @param text      the value
	 * @param setter    writes a new text into the model (converted to the value's type; throws
	 *                  {@link RuntimeException} if the text isn't a valid value)
	 */
	public record Value(String path, EObject element, List<String> names, String attribute, String text,
			Consumer<String> setter) {

		/** A readable label of the field, such as {@code General › Keyword › String}. */
		public String label() {
			return FieldLabels.label(names, attribute);
		}
	}

	/**
	 * A searched value with matches.
	 *
	 * @param value   the value
	 * @param matches the matched ranges ({@code [offset, length]} in {@link Value#text()})
	 */
	public record Hit(Value value, List<int[]> matches) {
	}

	private DocumentSearch() {
	}

	/** Searches the contents of a resource. */
	public static List<Hit> search(Resource resource, Query query) {
		Pattern pattern = query.pattern();
		List<Hit> hits = new ArrayList<>();
		for (EObject root : resource.getContents()) {
			values(root, value -> {
				if (!query.includes(value.names(), value.attribute())) {
					return;
				}
				List<int[]> matches = find(pattern, value.text());
				if (!matches.isEmpty()) {
					hits.add(new Hit(value, matches));
				}
			});
		}
		return hits;
	}

	/** The matched ranges of a pattern in a text (empty matches are left out). */
	public static List<int[]> find(Pattern pattern, String text) {
		List<int[]> matches = new ArrayList<>();
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			if (matcher.end() > matcher.start()) {
				matches.add(new int[] { matcher.start(), matcher.end() - matcher.start() });
			}
		}
		return matches;
	}

	/** Visits the text values of an object and its contents in document order. */
	public static void values(EObject object, Consumer<Value> visitor) {
		ElementPath path = ElementPath.of(object);
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (feature.isDerived() || !object.eIsSet(feature)) {
				continue;
			}
			if (feature instanceof EReference reference) {
				if (reference.isContainment()) {
					contents(object.eGet(reference), visitor);
				}
				continue;
			}
			EAttribute attribute = (EAttribute) feature;
			if (FeatureMapUtil.isFeatureMap(attribute)) {
				entries(object, path, (FeatureMap) object.eGet(attribute), new HashMap<>(), visitor);
				continue;
			}
			Object value = object.eGet(attribute);
			if (value instanceof List<?> list) {
				for (int i = 0; i < list.size(); i++) {
					int index = i;
					@SuppressWarnings("unchecked")
					List<Object> values = (List<Object>) list;
					single(object, path, attribute, list.get(i), i,
							text -> values.set(index, convert(attribute, text)), visitor);
				}
			} else {
				single(object, path, attribute, value, 0, text -> object.eSet(attribute, convert(attribute, text)),
						visitor);
			}
		}
	}

	private static void contents(Object value, Consumer<Value> visitor) {
		if (value instanceof EObject child) {
			values(child, visitor);
		} else if (value instanceof List<?> list) {
			for (Object item : list) {
				if (item instanceof EObject child) {
					values(child, visitor);
				}
			}
		}
	}

	private static void entries(EObject object, ElementPath path, FeatureMap map, Map<EStructuralFeature, Integer> counts,
			Consumer<Value> visitor) {
		for (int i = 0; i < map.size(); i++) {
			FeatureMap.Entry entry = map.get(i);
			EStructuralFeature feature = entry.getEStructuralFeature();
			Object value = entry.getValue();
			int index = counts.merge(feature, 1, Integer::sum) - 1;
			int position = i;
			switch (value) {
				case null -> {
				}
				case EObject child -> values(child, visitor);
				case FeatureMap nested when FeatureMapUtil.isFeatureMap(feature) -> entries(object, path, nested, counts,
						visitor);
				default -> {
					if (feature == XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT
							|| feature == XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__CDATA) {
						text(object, path, null, String.valueOf(value), text -> map.setValue(position, text), visitor);
					} else if (feature instanceof EAttribute attribute
							&& feature != XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__COMMENT
							&& feature != XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__PROCESSING_INSTRUCTION) {
						single(object, path, attribute, value, index,
								text -> map.setValue(position, convert(attribute, text)), visitor);
					}
				}
			}
		}
	}

	private static Object convert(EAttribute attribute, String text) {
		return attribute.getEAttributeType().getInstanceClass() == String.class ? text
				: EcoreUtil.createFromString(attribute.getEAttributeType(), text);
	}

	private static void single(EObject object, ElementPath path, EAttribute attribute, Object value, int index,
			Consumer<String> setter, Consumer<Value> visitor) {
		if (value == null) {
			return;
		}
		String text = value instanceof String s ? s : EcoreUtil.convertToString(attribute.getEAttributeType(), value);
		if (text == null || text.isBlank()) {
			return;
		}
		switch (META.getFeatureKind(attribute)) {
			case ExtendedMetaData.ATTRIBUTE_FEATURE -> text(object, path, META.getName(attribute), text, setter, visitor);
			case ExtendedMetaData.SIMPLE_FEATURE -> text(object, path, null, text, setter, visitor);
			case ExtendedMetaData.ELEMENT_FEATURE -> {
				String namespace = META.getNamespace(attribute);
				ElementPath child = path.child(namespace == null ? "" : namespace, META.getName(attribute), index);
				visitor.accept(new Value(child.toString(), object, child.names(), null, text, setter));
			}
			default -> {
				// Not part of the XML (unannotated features) or wildcards handled by the caller.
			}
		}
	}

	private static void text(EObject object, ElementPath path, String attribute, String text, Consumer<String> setter,
			Consumer<Value> visitor) {
		String location = attribute == null ? path.toString() : path + "/@" + attribute;
		visitor.accept(new Value(location, object, path.names(), attribute, text, setter));
	}
}
