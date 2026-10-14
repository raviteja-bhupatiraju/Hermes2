/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcarevocab;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Purpose Values</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage#getPurposeValues()
 * @model extendedMetaData="name='purposeValues'"
 * @generated
 */
public enum PurposeValues implements Enumerator {
	/**
	 * The '<em><b>Learning Outcome</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEARNING_OUTCOME_VALUE
	 * @generated
	 * @ordered
	 */
	LEARNING_OUTCOME(0, "learningOutcome", "learning outcome"),

	/**
	 * The '<em><b>Clinical Guideline</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLINICAL_GUIDELINE_VALUE
	 * @generated
	 * @ordered
	 */
	CLINICAL_GUIDELINE(1, "clinicalGuideline", "clinical guideline"),

	/**
	 * The '<em><b>Drug List</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DRUG_LIST_VALUE
	 * @generated
	 * @ordered
	 */
	DRUG_LIST(2, "drugList", "drug list"),

	/**
	 * The '<em><b>Level Of Evidence</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEVEL_OF_EVIDENCE_VALUE
	 * @generated
	 * @ordered
	 */
	LEVEL_OF_EVIDENCE(3, "levelOfEvidence", "level of evidence");

	/**
	 * The '<em><b>Learning Outcome</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEARNING_OUTCOME
	 * @model name="learningOutcome" literal="learning outcome"
	 * @generated
	 * @ordered
	 */
	public static final int LEARNING_OUTCOME_VALUE = 0;

	/**
	 * The '<em><b>Clinical Guideline</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLINICAL_GUIDELINE
	 * @model name="clinicalGuideline" literal="clinical guideline"
	 * @generated
	 * @ordered
	 */
	public static final int CLINICAL_GUIDELINE_VALUE = 1;

	/**
	 * The '<em><b>Drug List</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DRUG_LIST
	 * @model name="drugList" literal="drug list"
	 * @generated
	 * @ordered
	 */
	public static final int DRUG_LIST_VALUE = 2;

	/**
	 * The '<em><b>Level Of Evidence</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEVEL_OF_EVIDENCE
	 * @model name="levelOfEvidence" literal="level of evidence"
	 * @generated
	 * @ordered
	 */
	public static final int LEVEL_OF_EVIDENCE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Purpose Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PurposeValues[] VALUES_ARRAY = new PurposeValues[] { LEARNING_OUTCOME, CLINICAL_GUIDELINE,
			DRUG_LIST, LEVEL_OF_EVIDENCE, };

	/**
	 * A public read-only list of all the '<em><b>Purpose Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PurposeValues> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Purpose Values</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PurposeValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PurposeValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Purpose Values</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PurposeValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PurposeValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Purpose Values</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PurposeValues get(int value) {
		switch (value) {
		case LEARNING_OUTCOME_VALUE:
			return LEARNING_OUTCOME;
		case CLINICAL_GUIDELINE_VALUE:
			return CLINICAL_GUIDELINE;
		case DRUG_LIST_VALUE:
			return DRUG_LIST;
		case LEVEL_OF_EVIDENCE_VALUE:
			return LEVEL_OF_EVIDENCE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PurposeValues(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //PurposeValues
