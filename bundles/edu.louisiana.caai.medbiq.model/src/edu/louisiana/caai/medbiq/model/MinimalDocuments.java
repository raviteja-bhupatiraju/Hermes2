/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * Creates elements with their required content, from the model: required child elements are created
 * recursively, required simple values get a placeholder that satisfies their type (the first enumeration value,
 * today's date, 1, the pattern's simplest match where it is known). Used for new elements in the editor and as the
 * starting point of templates.
 */
public final class MinimalDocuments {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;
	private static final int MAX_DEPTH = 12;
	private static final java.util.concurrent.atomic.AtomicInteger IDS = new java.util.concurrent.atomic.AtomicInteger();

	private MinimalDocuments() {
	}

	/** A new instance of a class with its required content. */
	public static EObject create(EClass eClass) {
		EObject object = EcoreUtil.create(eClass);
		fill(object, new HashSet<>(), 0);
		return object;
	}

	/** Adds the required content an object is missing. */
	public static void fill(EObject object, Set<EClass> path, int depth) {
		if (depth > MAX_DEPTH || !path.add(object.eClass())) {
			return;
		}
		try {
			for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
				boolean content = META.getFeatureKind(feature) == ExtendedMetaData.SIMPLE_FEATURE;
				if (!(isRequired(feature) || content) || object.eIsSet(feature) || !feature.isChangeable() || feature.isDerived()
						&& !isGroupMember(feature)) {
					continue;
				}
				if (feature instanceof EReference reference && reference.isContainment()) {
					EClass type = reference.getEReferenceType();
					if (type.isAbstract() || type.isInterface()) {
						continue;
					}
					EObject child = EcoreUtil.create(type);
					set(object, reference, child);
					fill(child, path, depth + 1);
				} else if (feature instanceof EAttribute attribute && !FeatureMapUtil.isFeatureMap(attribute)) {
					Object value = placeholder(attribute.getEAttributeType());
					if (value != null) {
						set(object, attribute, value);
					}
				}
			}
		} finally {
			path.remove(object.eClass());
		}
	}

	private static boolean isGroupMember(EStructuralFeature feature) {
		return META.getGroup(feature) != null;
	}

	/** Whether a feature must occur (lower bound above 0, or a required attribute). */
	public static boolean isRequired(EStructuralFeature feature) {
		int kind = META.getFeatureKind(feature);
		return feature.getLowerBound() > 0 && (kind == ExtendedMetaData.ELEMENT_FEATURE
				|| kind == ExtendedMetaData.ATTRIBUTE_FEATURE || kind == ExtendedMetaData.SIMPLE_FEATURE);
	}

	@SuppressWarnings("unchecked")
	private static void set(EObject object, EStructuralFeature feature, Object value) {
		if (feature.isMany()) {
			((List<Object>) object.eGet(feature)).add(value);
		} else {
			object.eSet(feature, value);
		}
	}

	/** A value of a simple type that satisfies it, or {@code null} if none is known. */
	public static Object placeholder(EDataType type) {
		if (type instanceof EEnum eEnum) {
			return eEnum.getELiterals().isEmpty() ? null : eEnum.getELiterals().getFirst().getInstance();
		}
		List<String> enumeration = META.getEnumerationFacet(type);
		if (!enumeration.isEmpty()) {
			return EcoreUtil.createFromString(type, enumeration.getFirst());
		}
		EDataType base = type;
		while (base != null && base.getEPackage() != XMLTypePackage.eINSTANCE) {
			EDataType next = META.getBaseType(base);
			if (next == null) {
				List<EDataType> members = META.getMemberTypes(base);
				next = members.isEmpty() ? null : members.getFirst();
			}
			if (next == null) {
				break;
			}
			if (!META.getEnumerationFacet(next).isEmpty()) {
				return EcoreUtil.createFromString(type, META.getEnumerationFacet(next).getFirst());
			}
			base = next;
		}
		String name = base == null ? "string" : base.getName();
		String literal = switch (name) {
			case "Boolean", "BooleanObject" -> "false";
			case "Date" -> LocalDate.now().toString();
			case "DateTime" -> java.time.OffsetDateTime.now().withNano(0).toString();
			case "Time" -> "00:00:00";
			case "GYear" -> Integer.toString(LocalDate.now().getYear());
			case "Duration" -> "PT1H";
			case "Integer", "Int", "IntObject", "Long", "LongObject", "Short", "ShortObject", "Byte", "ByteObject",
					"PositiveInteger", "NonNegativeInteger", "UnsignedInt", "UnsignedIntObject", "UnsignedLong",
					"UnsignedShort", "UnsignedShortObject", "UnsignedByte", "UnsignedByteObject" -> "1";
			case "Decimal", "Double", "DoubleObject", "Float", "FloatObject" -> "1";
			case "AnyURI" -> "http://example.org/";
			case "Language" -> "en";
			case "ID", "NCName", "Name", "NMTOKEN", "IDREF" -> "id" + IDS.incrementAndGet();
			default -> "…";
		};
		try {
			Object value = EcoreUtil.createFromString(type, literal);
			return value;
		} catch (RuntimeException _) {
			return fallback(name);
		}
	}

	private static Object fallback(String name) {
		return switch (name) {
			case "Integer", "PositiveInteger", "NonNegativeInteger" -> BigInteger.ONE;
			case "Decimal" -> BigDecimal.ONE;
			case "Date" -> {
				try {
					yield DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString());
				} catch (DatatypeConfigurationException _) {
					yield null;
				}
			}
			default -> null;
		};
	}
}
