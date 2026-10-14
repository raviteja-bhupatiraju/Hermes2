/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Audience Category Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getAudienceCategoryType()
 * @model extendedMetaData="name='audienceCategoryType'"
 * @generated
 */
public enum AudienceCategoryType implements Enumerator {
	/**
	 * The '<em><b>General</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERAL_VALUE
	 * @generated
	 * @ordered
	 */
	GENERAL(0, "general", "general"),

	/**
	 * The '<em><b>Patient</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATIENT_VALUE
	 * @generated
	 * @ordered
	 */
	PATIENT(1, "patient", "patient"),

	/**
	 * The '<em><b>Caregiver</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CAREGIVER_VALUE
	 * @generated
	 * @ordered
	 */
	CAREGIVER(2, "caregiver", "caregiver"),

	/**
	 * The '<em><b>Professional</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROFESSIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	PROFESSIONAL(3, "professional", "professional");

	/**
	 * The '<em><b>General</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERAL
	 * @model name="general"
	 * @generated
	 * @ordered
	 */
	public static final int GENERAL_VALUE = 0;

	/**
	 * The '<em><b>Patient</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATIENT
	 * @model name="patient"
	 * @generated
	 * @ordered
	 */
	public static final int PATIENT_VALUE = 1;

	/**
	 * The '<em><b>Caregiver</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CAREGIVER
	 * @model name="caregiver"
	 * @generated
	 * @ordered
	 */
	public static final int CAREGIVER_VALUE = 2;

	/**
	 * The '<em><b>Professional</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROFESSIONAL
	 * @model name="professional"
	 * @generated
	 * @ordered
	 */
	public static final int PROFESSIONAL_VALUE = 3;

	/**
	 * An array of all the '<em><b>Audience Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AudienceCategoryType[] VALUES_ARRAY = new AudienceCategoryType[] { GENERAL, PATIENT, CAREGIVER,
			PROFESSIONAL, };

	/**
	 * A public read-only list of all the '<em><b>Audience Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AudienceCategoryType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Audience Category Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AudienceCategoryType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AudienceCategoryType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Audience Category Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AudienceCategoryType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AudienceCategoryType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Audience Category Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AudienceCategoryType get(int value) {
		switch (value) {
		case GENERAL_VALUE:
			return GENERAL;
		case PATIENT_VALUE:
			return PATIENT;
		case CAREGIVER_VALUE:
			return CAREGIVER;
		case PROFESSIONAL_VALUE:
			return PROFESSIONAL;
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
	private AudienceCategoryType(int value, String name, String literal) {
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

} //AudienceCategoryType
