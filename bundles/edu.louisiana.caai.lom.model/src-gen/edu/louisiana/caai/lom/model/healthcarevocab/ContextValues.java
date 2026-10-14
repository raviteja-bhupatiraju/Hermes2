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
 * A representation of the literals of the enumeration '<em><b>Context Values</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage#getContextValues()
 * @model extendedMetaData="name='contextValues'"
 * @generated
 */
public enum ContextValues implements Enumerator {
	/**
	 * The '<em><b>Patient Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATIENT_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	PATIENT_EDUCATION(0, "patientEducation", "patient education"),

	/**
	 * The '<em><b>Caregiver Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CAREGIVER_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	CAREGIVER_EDUCATION(1, "caregiverEducation", "caregiver education"),

	/**
	 * The '<em><b>Primary Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMARY_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMARY_EDUCATION(2, "primaryEducation", "primary education"),

	/**
	 * The '<em><b>Secondary Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SECONDARY_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	SECONDARY_EDUCATION(3, "secondaryEducation", "secondary education"),

	/**
	 * The '<em><b>Vocational Training</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOCATIONAL_TRAINING_VALUE
	 * @generated
	 * @ordered
	 */
	VOCATIONAL_TRAINING(4, "vocationalTraining", "vocational training"),

	/**
	 * The '<em><b>Undergraduate Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERGRADUATE_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	UNDERGRADUATE_EDUCATION(5, "undergraduateEducation", "undergraduate education"),

	/**
	 * The '<em><b>Undergraduate Professional Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERGRADUATE_PROFESSIONAL_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	UNDERGRADUATE_PROFESSIONAL_EDUCATION(6, "undergraduateProfessionalEducation",
			"undergraduate professional education"),

	/**
	 * The '<em><b>Graduate Professional Education</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GRADUATE_PROFESSIONAL_EDUCATION_VALUE
	 * @generated
	 * @ordered
	 */
	GRADUATE_PROFESSIONAL_EDUCATION(7, "graduateProfessionalEducation", "graduate professional education"),

	/**
	 * The '<em><b>Continuing Professional Development</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTINUING_PROFESSIONAL_DEVELOPMENT_VALUE
	 * @generated
	 * @ordered
	 */
	CONTINUING_PROFESSIONAL_DEVELOPMENT(8, "continuingProfessionalDevelopment", "continuing professional development");

	/**
	 * The '<em><b>Patient Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATIENT_EDUCATION
	 * @model name="patientEducation" literal="patient education"
	 * @generated
	 * @ordered
	 */
	public static final int PATIENT_EDUCATION_VALUE = 0;

	/**
	 * The '<em><b>Caregiver Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CAREGIVER_EDUCATION
	 * @model name="caregiverEducation" literal="caregiver education"
	 * @generated
	 * @ordered
	 */
	public static final int CAREGIVER_EDUCATION_VALUE = 1;

	/**
	 * The '<em><b>Primary Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMARY_EDUCATION
	 * @model name="primaryEducation" literal="primary education"
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_EDUCATION_VALUE = 2;

	/**
	 * The '<em><b>Secondary Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SECONDARY_EDUCATION
	 * @model name="secondaryEducation" literal="secondary education"
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_EDUCATION_VALUE = 3;

	/**
	 * The '<em><b>Vocational Training</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOCATIONAL_TRAINING
	 * @model name="vocationalTraining" literal="vocational training"
	 * @generated
	 * @ordered
	 */
	public static final int VOCATIONAL_TRAINING_VALUE = 4;

	/**
	 * The '<em><b>Undergraduate Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERGRADUATE_EDUCATION
	 * @model name="undergraduateEducation" literal="undergraduate education"
	 * @generated
	 * @ordered
	 */
	public static final int UNDERGRADUATE_EDUCATION_VALUE = 5;

	/**
	 * The '<em><b>Undergraduate Professional Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERGRADUATE_PROFESSIONAL_EDUCATION
	 * @model name="undergraduateProfessionalEducation" literal="undergraduate professional education"
	 * @generated
	 * @ordered
	 */
	public static final int UNDERGRADUATE_PROFESSIONAL_EDUCATION_VALUE = 6;

	/**
	 * The '<em><b>Graduate Professional Education</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GRADUATE_PROFESSIONAL_EDUCATION
	 * @model name="graduateProfessionalEducation" literal="graduate professional education"
	 * @generated
	 * @ordered
	 */
	public static final int GRADUATE_PROFESSIONAL_EDUCATION_VALUE = 7;

	/**
	 * The '<em><b>Continuing Professional Development</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTINUING_PROFESSIONAL_DEVELOPMENT
	 * @model name="continuingProfessionalDevelopment" literal="continuing professional development"
	 * @generated
	 * @ordered
	 */
	public static final int CONTINUING_PROFESSIONAL_DEVELOPMENT_VALUE = 8;

	/**
	 * An array of all the '<em><b>Context Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ContextValues[] VALUES_ARRAY = new ContextValues[] { PATIENT_EDUCATION, CAREGIVER_EDUCATION,
			PRIMARY_EDUCATION, SECONDARY_EDUCATION, VOCATIONAL_TRAINING, UNDERGRADUATE_EDUCATION,
			UNDERGRADUATE_PROFESSIONAL_EDUCATION, GRADUATE_PROFESSIONAL_EDUCATION,
			CONTINUING_PROFESSIONAL_DEVELOPMENT, };

	/**
	 * A public read-only list of all the '<em><b>Context Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ContextValues> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Context Values</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContextValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContextValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Context Values</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContextValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContextValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Context Values</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContextValues get(int value) {
		switch (value) {
		case PATIENT_EDUCATION_VALUE:
			return PATIENT_EDUCATION;
		case CAREGIVER_EDUCATION_VALUE:
			return CAREGIVER_EDUCATION;
		case PRIMARY_EDUCATION_VALUE:
			return PRIMARY_EDUCATION;
		case SECONDARY_EDUCATION_VALUE:
			return SECONDARY_EDUCATION;
		case VOCATIONAL_TRAINING_VALUE:
			return VOCATIONAL_TRAINING;
		case UNDERGRADUATE_EDUCATION_VALUE:
			return UNDERGRADUATE_EDUCATION;
		case UNDERGRADUATE_PROFESSIONAL_EDUCATION_VALUE:
			return UNDERGRADUATE_PROFESSIONAL_EDUCATION;
		case GRADUATE_PROFESSIONAL_EDUCATION_VALUE:
			return GRADUATE_PROFESSIONAL_EDUCATION;
		case CONTINUING_PROFESSIONAL_DEVELOPMENT_VALUE:
			return CONTINUING_PROFESSIONAL_DEVELOPMENT;
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
	private ContextValues(int value, String name, String literal) {
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

} //ContextValues
