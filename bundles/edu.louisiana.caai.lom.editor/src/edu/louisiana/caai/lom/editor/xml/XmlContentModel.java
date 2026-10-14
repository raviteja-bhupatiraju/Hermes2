/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;

/**
 * What the HLOM schemas allow at a place in the XML text, derived from the EMF {@link ExtendedMetaData} of the
 * generated model: the element (feature) and its type, the child elements, attributes and enumerated values.
 */
final class XmlContentModel {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** The conventional prefixes of the HLOM namespaces. */
	static final Map<String, String> PREFIXES = Map.of(LomPackage.eNS_URI, "", HealthcarePackage.eNS_URI, "hx",
			AddressPackage.eNS_URI, "a");

	/**
	 * The type of the document being edited, bound by the XML page around content assist and hovers; Healthcare LOM
	 * when unbound.
	 */
	static final ScopedValue<DocumentType> TYPE = ScopedValue.newInstance();

	/** The type of the document being edited (see {@link #TYPE}). */
	static DocumentType type() {
		return TYPE.isBound() ? TYPE.get() : HlomDocumentType.INSTANCE;
	}

	/** The conventional prefix of a namespace: of the HLOM namespaces, else of its package. */
	static String prefix(String namespace, String fallback) {
		String prefix = PREFIXES.get(namespace);
		if (prefix != null) {
			return prefix;
		}
		EPackage ePackage = namespace == null ? null : EPackage.Registry.INSTANCE.getEPackage(namespace);
		return ePackage != null && ePackage.getNsPrefix() != null ? ePackage.getNsPrefix() : fallback;
	}

	/** The feature of the element ({@code null} for the document) and its type. */
	final EStructuralFeature feature;
	final EClassifier type;

	private XmlContentModel(EStructuralFeature feature, EClassifier type) {
		this.feature = feature;
		this.type = type;
	}

	/** The content model of the document (whose only child is the root element of its {@link #type()}). */
	static XmlContentModel document() {
		DocumentType type = type();
		EPackage ePackage = type == HlomDocumentType.INSTANCE ? LomPackage.eINSTANCE
				: EPackage.Registry.INSTANCE.getEPackage(type.namespace());
		EClass documentRoot = ePackage == null ? null : META.getDocumentRoot(ePackage);
		return new XmlContentModel(null, documentRoot != null ? documentRoot : LomPackage.eINSTANCE.getDocumentRoot());
	}

	/** The content model of an element, from its path; {@code null} for elements unknown to the schemas. */
	static XmlContentModel of(XmlStructure.Element element) {
		if (element == null) {
			return document();
		}
		XmlContentModel model = document();
		for (XmlStructure.Element step : element.path()) {
			model = model.child(step.namespace(), step.localName());
			if (model == null) {
				return null;
			}
		}
		return model;
	}

	/** The content model of a child element, or {@code null}. */
	XmlContentModel child(String namespace, String localName) {
		if (!(type instanceof EClass eClass) || namespace == null) {
			return null;
		}
		EStructuralFeature child = META.getElement(eClass, namespace.isEmpty() ? null : namespace, localName);
		if (child == null) {
			child = META.getElement(namespace, localName);
		}
		return child == null ? null : new XmlContentModel(child, child.getEType());
	}

	/** The element features allowed as children, in schema order. */
	List<EStructuralFeature> childElements() {
		if (!(type instanceof EClass eClass)) {
			return Collections.emptyList();
		}
		Map<String, EStructuralFeature> result = new LinkedHashMap<>();
		for (EStructuralFeature child : META.getAllElements(eClass)) {
			if (META.getFeatureKind(child) == ExtendedMetaData.ELEMENT_FEATURE && META.getName(child) != null) {
				result.putIfAbsent(META.getNamespace(child) + "#" + META.getName(child), child);
			}
		}
		return new ArrayList<>(result.values());
	}

	/**
	 * Whether a child element may occur at most once: the feature is single-valued, or its type is one of the
	 * elements the LOM strict profile makes unique within their parent (types with a {@code uniqueElementName}
	 * attribute, checked with {@code xs:unique} by the schema).
	 */
	static boolean isSingle(EStructuralFeature child) {
		if (!child.isMany()) {
			return true;
		}
		if (child.getEType() instanceof EClass eClass) {
			for (EStructuralFeature attribute : eClass.getEAllStructuralFeatures()) {
				if ("uniqueElementName".equals(META.getName(attribute))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Whether the children must appear in schema order ({@code xs:sequence}, e.g. the healthcare extensions), as
	 * opposed to the LOM categories, whose children are a repeated choice in any order.
	 */
	boolean isOrdered() {
		if (!(type instanceof EClass)) {
			return false;
		}
		for (EStructuralFeature child : childElements()) {
			if (META.getGroup(child) != null) {
				return false;
			}
		}
		return true;
	}

	/** The position of a child element in schema order, or {@code -1} if it isn't allowed here. */
	int indexOf(String namespace, String localName) {
		List<EStructuralFeature> children = childElements();
		for (int i = 0; i < children.size(); i++) {
			EStructuralFeature child = children.get(i);
			if (localName.equals(name(child)) && namespace(child).equals(namespace == null ? "" : namespace)) {
				return i;
			}
		}
		return -1;
	}

	/** The attribute features of the element (excluding wildcards). */
	List<EStructuralFeature> attributes() {
		if (!(type instanceof EClass eClass)) {
			return Collections.emptyList();
		}
		List<EStructuralFeature> result = new ArrayList<>();
		for (EStructuralFeature attribute : META.getAllAttributes(eClass)) {
			if (META.getFeatureKind(attribute) == ExtendedMetaData.ATTRIBUTE_FEATURE) {
				result.add(attribute);
			}
		}
		return result;
	}

	/** Whether the element has element children (and therefore is written on several lines). */
	boolean hasChildElements() {
		return !childElements().isEmpty();
	}

	/** The data type of the element's text content, or {@code null} if it has none. */
	EDataType contentType() {
		if (type instanceof EDataType dataType) {
			return dataType;
		}
		if (type instanceof EClass eClass && META.getContentKind(eClass) == ExtendedMetaData.SIMPLE_CONTENT
				&& META.getSimpleFeature(eClass) instanceof EAttribute attribute) {
			return attribute.getEAttributeType();
		}
		return null;
	}

	/** Whether the element is a LOM vocabulary ({@code source} and {@code value}). */
	boolean isVocabulary() {
		return type instanceof EClass eClass && Vocabularies.isVocabulary(eClass);
	}

	/** The single child element if the element is a container of language strings ({@code string}). */
	boolean isLangStringContainer() {
		List<EStructuralFeature> children = childElements();
		return children.size() == 1 && "string".equals(META.getName(children.getFirst()));
	}

	/** The enumerated values allowed as content of a data type, with their vocabulary source. */
	static List<Vocabularies.Term> enumeratedValues(EDataType dataType) {
		List<Vocabularies.Term> result = new ArrayList<>();
		if (dataType == null) {
			return result;
		}
		for (EEnum eEnum : Vocabularies.memberEnums(dataType)) {
			String source = Vocabularies.sourceOf(eEnum);
			for (EEnumLiteral literal : eEnum.getELiterals()) {
				result.add(new Vocabularies.Term(literal.getLiteral(), source, literal.getInstance()));
			}
		}
		Class<?> instanceClass = dataType.getInstanceClass();
		if (result.isEmpty() && (instanceClass == boolean.class || instanceClass == Boolean.class)) {
			result.add(new Vocabularies.Term("true", null, null));
			result.add(new Vocabularies.Term("false", null, null));
		}
		return result;
	}

	/** The XML element name of a feature. */
	static String name(EStructuralFeature feature) {
		return META.getName(feature);
	}

	/** The namespace of a feature's element or attribute ({@code ""} for none). */
	static String namespace(EStructuralFeature feature) {
		String namespace = META.getNamespace(feature);
		return namespace == null ? "" : namespace;
	}

	/**
	 * The model object of an element path (see {@link #path(EObject)}), starting at the document root; as far as
	 * the path exists in the model (the deepest object found), or {@code null} if not even its first step does.
	 */
	static EObject resolve(EObject documentRoot, List<String[]> steps) {
		EObject current = documentRoot;
		EObject found = null;
		for (String[] step : steps) {
			EStructuralFeature feature = META.getElement(current.eClass(), step[0].isEmpty() ? null : step[0], step[1]);
			if (feature == null) {
				break;
			}
			Object value = current.eGet(feature);
			int index = Integer.parseInt(step[2]);
			Object next = value instanceof List<?> list ? (index < list.size() ? list.get(index) : null) : value;
			if (!(next instanceof EObject object)) {
				break;
			}
			current = object;
			found = object;
		}
		return found;
	}

	/**
	 * The element path of a model object: (namespace, local name, index among same-named siblings) for each
	 * element from {@code lom} down to the object.
	 */
	static List<String[]> path(EObject object) {
		List<String[]> steps = new ArrayList<>();
		for (EObject o = object; o != null && o.eContainer() != null; o = o.eContainer()) {
			EStructuralFeature feature = o.eContainmentFeature();
			if (feature == null || META.getName(feature) == null) {
				break;
			}
			Object siblings = o.eContainer().eGet(feature);
			int index = siblings instanceof List<?> list ? Math.max(0, list.indexOf(o)) : 0;
			steps.addFirst(new String[] { namespace(feature), META.getName(feature), Integer.toString(index) });
		}
		return steps;
	}
}
