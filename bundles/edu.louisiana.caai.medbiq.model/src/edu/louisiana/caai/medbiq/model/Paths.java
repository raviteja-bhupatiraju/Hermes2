/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

/**
 * Reflective access to documents by element names, for models without generated code: {@code values(object,
 * "lom/general/title/string")} follows the elements (and attributes, as {@code @name}) of these local names.
 */
public final class Paths {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	private Paths() {
	}

	/** The feature of an element or attribute ({@code @name}) of an object's class by local name, or {@code null}. */
	public static EStructuralFeature feature(EObject object, String name) {
		boolean attribute = name.startsWith("@");
		String local = attribute ? name.substring(1) : name;
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if ((attribute ? kind == ExtendedMetaData.ATTRIBUTE_FEATURE : kind == ExtendedMetaData.ELEMENT_FEATURE)
					&& local.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	/** The values at a path: model objects and, at the end, simple values (as they are stored). */
	public static List<Object> values(EObject start, String path) {
		List<Object> current = new ArrayList<>(List.of(start));
		for (String step : path.split("/")) {
			if (step.isEmpty()) {
				continue;
			}
			List<Object> next = new ArrayList<>();
			for (Object value : current) {
				if (value instanceof EObject object) {
					EStructuralFeature feature = feature(object, step);
					if (feature != null && object.eIsSet(feature)) {
						Object child = object.eGet(feature);
						if (child instanceof List<?> list) {
							next.addAll(list);
						} else {
							next.add(child);
						}
					}
				}
			}
			current = next;
		}
		return current;
	}

	/** The objects at a path. */
	public static List<EObject> objects(EObject start, String path) {
		return values(start, path).stream().filter(EObject.class::isInstance).map(EObject.class::cast).toList();
	}

	/** The texts at a path: simple values, and the simple content of the objects. */
	public static List<String> texts(EObject start, String path) {
		List<String> texts = new ArrayList<>();
		for (Object value : values(start, path)) {
			String text = switch (value) {
				case null -> null;
				// Enumeration values (EEnumLiterals of dynamic models are also EObjects).
				case Enumerator literal -> literal.getLiteral();
				case EObject object -> content(object);
				default -> value.toString();
			};
			if (text != null && !text.isBlank()) {
				texts.add(text.strip());
			}
		}
		return texts;
	}

	/** The first text at a path, or {@code null}. */
	public static String text(EObject start, String path) {
		List<String> texts = texts(start, path);
		return texts.isEmpty() ? null : texts.getFirst();
	}

	/** The simple content of an element: its value feature, or its mixed text; {@code null} if there is none. */
	public static String content(EObject object) {
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if (kind == ExtendedMetaData.SIMPLE_FEATURE && feature instanceof EAttribute attribute && object.eIsSet(feature)) {
				Object value = object.eGet(feature);
				return switch (value) {
					case String s -> s;
					case Enumerator literal -> literal.getLiteral();
					default -> EcoreUtil.convertToString(attribute.getEAttributeType(), value);
				};
			}
			if (FeatureMapUtil.isFeatureMap(feature) && META.getName(feature) != null
					&& META.getName(feature).equals(":mixed") && object.eIsSet(feature)) {
				StringBuilder text = new StringBuilder();
				for (FeatureMap.Entry entry : (FeatureMap) object.eGet(feature)) {
					if (FeatureMapUtil.isText(entry) || FeatureMapUtil.isCDATA(entry)) {
						text.append(entry.getValue());
					}
				}
				return text.toString();
			}
		}
		return null;
	}

	/** The root element of a document root (its only set element), or {@code null}. */
	public static EObject rootElement(EObject documentRoot) {
		for (EStructuralFeature feature : documentRoot.eClass().getEAllStructuralFeatures()) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE && documentRoot.eIsSet(feature)
					&& documentRoot.eGet(feature) instanceof EObject root) {
				return root;
			}
		}
		return null;
	}

	/**
	 * The first value at a path as text, enumeration values as their literals and elements by their simple
	 * content (stripped; blank values are skipped), or {@code null}.
	 */
	public static String literal(EObject start, String path) {
		for (Object value : values(start, path)) {
			String text = switch (value) {
				case Enumerator enumerator -> enumerator.getLiteral();
				case EObject object -> content(object);
				case null -> null;
				default -> value.toString();
			};
			if (text != null && !text.isBlank()) {
				return text.strip();
			}
		}
		return null;
	}

	/** The date at the start of an XML date or date-time ({@code 2024-05-01...}), or {@code null}. */
	public static LocalDate date(String text) {
		if (text == null || text.length() < 10) {
			return null;
		}
		try {
			return LocalDate.parse(text.substring(0, 10));
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/** A decimal number, or {@code null} if the text isn't one. */
	public static BigDecimal decimal(String text) {
		try {
			return text == null || text.isBlank() ? null : new BigDecimal(text.strip());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** An integer, or {@code null} if the text isn't one. */
	public static Integer integer(String text) {
		try {
			return text == null ? null : Integer.valueOf(text.strip());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** A count with its noun, in the plural unless it is one: "1 event", "3 events", "2 diagnoses". */
	public static String count(int n, String noun) {
		String plural = noun.endsWith("sis") ? noun.substring(0, noun.length() - 3) + "ses"
				: noun.endsWith("x") || noun.endsWith("s") ? noun + "es" : noun + "s";
		return n + " " + (n == 1 ? noun : plural);
	}
}
