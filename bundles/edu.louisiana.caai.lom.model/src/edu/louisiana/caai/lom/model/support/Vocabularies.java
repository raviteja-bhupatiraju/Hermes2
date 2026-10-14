/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;
import edu.louisiana.caai.lom.model.lom.LomPackage;

/**
 * Support for LOM vocabulary elements: {@code <role><source>LOMv1.0</source><value>author</value></role>}.
 * <p>
 * In the HLOM schemas every vocabulary value type is a union of the IEEE LOM enumeration and, for some
 * elements, a MedBiquitous healthcare enumeration. The allowed terms and their {@code source} are derived
 * from that union, so the UI never hard-codes vocabularies.
 */
public final class Vocabularies {

	/** Source of the IEEE LOM v1.0 base vocabularies. */
	public static final String LOM_SOURCE = "LOMv1.0";

	/** Source of the MedBiquitous Healthcare LOM custom vocabularies. */
	public static final String HEALTHCARE_SOURCE = "HEALTHCARE_LOMv1";

	/** A vocabulary term with the source it belongs to. */
	public record Term(String value, String source, Enumerator literal) {
		@Override
		public String toString() {
			return HEALTHCARE_SOURCE.equals(source) ? value + "  (Healthcare)" : value;
		}
	}

	private Vocabularies() {
	}

	/** Whether the class is a LOM vocabulary type (has {@code source} and {@code value} child elements). */
	public static boolean isVocabulary(EClass eClass) {
		return sourceReference(eClass) != null && valueReference(eClass) != null;
	}

	/** The {@code source} containment reference of a vocabulary class. */
	public static EReference sourceReference(EClass vocabClass) {
		return reference(vocabClass, "source");
	}

	/** The {@code value} containment reference of a vocabulary class. */
	public static EReference valueReference(EClass vocabClass) {
		return reference(vocabClass, "value");
	}

	private static EReference reference(EClass eClass, String name) {
		EStructuralFeature feature = eClass.getEStructuralFeature(name);
		return feature instanceof EReference reference && reference.isContainment() ? reference : null;
	}

	/** The simple-content attribute holding the actual value of a {@code source} or {@code value} element class. */
	public static EAttribute contentAttribute(EClass simpleContentClass) {
		EStructuralFeature feature = simpleContentClass.getEStructuralFeature("value");
		return feature instanceof EAttribute attribute ? attribute : null;
	}

	/** All terms allowed for a vocabulary class, LOM terms first, then healthcare terms. */
	public static List<Term> terms(EClass vocabClass) {
		EReference valueReference = valueReference(vocabClass);
		if (valueReference == null) {
			return Collections.emptyList();
		}
		EAttribute content = contentAttribute(valueReference.getEReferenceType());
		if (content == null) {
			return Collections.emptyList();
		}
		List<Term> result = new ArrayList<>();
		for (EEnum eEnum : memberEnums(content.getEAttributeType())) {
			String source = sourceOf(eEnum);
			for (EEnumLiteral literal : eEnum.getELiterals()) {
				result.add(new Term(literal.getLiteral(), source, literal.getInstance()));
			}
		}
		return result;
	}

	/** The enumerations that make up a (possibly union) vocabulary data type. */
	public static List<EEnum> memberEnums(EDataType dataType) {
		List<EEnum> result = new ArrayList<>();
		collectEnums(dataType, result);
		return result;
	}

	private static void collectEnums(EDataType dataType, List<EEnum> result) {
		if (dataType instanceof EEnum eEnum) {
			if (!result.contains(eEnum)) {
				result.add(eEnum);
			}
			return;
		}
		List<EDataType> members = ExtendedMetaData.INSTANCE.getMemberTypes(dataType);
		if (!members.isEmpty()) {
			for (EDataType member : members) {
				collectEnums(member, result);
			}
			return;
		}
		EDataType baseType = ExtendedMetaData.INSTANCE.getBaseType(dataType);
		if (baseType != null) {
			collectEnums(baseType, result);
		}
	}

	/** The vocabulary source an enumeration belongs to. */
	public static String sourceOf(EEnum eEnum) {
		return eEnum.getEPackage() == HealthcarevocabPackage.eINSTANCE ? HEALTHCARE_SOURCE : LOM_SOURCE;
	}

	/** Finds the term for a value string, or {@code null} if it isn't part of the vocabulary. */
	public static Term findTerm(EClass vocabClass, String value) {
		for (Term term : terms(vocabClass)) {
			if (term.value().equals(value)) {
				return term;
			}
		}
		return null;
	}

	/** The first value of a vocabulary element as a string, or {@code null}. */
	public static String getValue(EObject vocab) {
		return firstContent(vocab, valueReference(vocab.eClass()));
	}

	/** The first source of a vocabulary element as a string, or {@code null}. */
	public static String getSource(EObject vocab) {
		return firstContent(vocab, sourceReference(vocab.eClass()));
	}

	private static String firstContent(EObject vocab, EReference reference) {
		if (vocab == null || reference == null) {
			return null;
		}
		List<?> elements = (List<?>) vocab.eGet(reference);
		if (elements.isEmpty()) {
			return null;
		}
		EObject element = (EObject) elements.getFirst();
		Object value = element.eGet(contentAttribute(element.eClass()));
		if (value == null) {
			return null;
		}
		return value instanceof Enumerator enumerator ? enumerator.getLiteral() : value.toString();
	}

	/** The {@code source} data type (union of LOM and healthcare sources). */
	public static EDataType sourceDataType() {
		return Objects.requireNonNull(contentAttribute(LomPackage.eINSTANCE.getSourceValue())).getEAttributeType();
	}
}
