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
 * A representation of the literals of the enumeration '<em><b>Participation Modality Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getParticipationModalityType()
 * @model extendedMetaData="name='participationModalityType'"
 * @generated
 */
public enum ParticipationModalityType implements Enumerator {
	/**
	 * The '<em><b>Conference Workshop</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFERENCE_WORKSHOP_VALUE
	 * @generated
	 * @ordered
	 */
	CONFERENCE_WORKSHOP(0, "conferenceWorkshop", "conference/workshop"),

	/**
	 * The '<em><b>Technology Based</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNOLOGY_BASED_VALUE
	 * @generated
	 * @ordered
	 */
	TECHNOLOGY_BASED(1, "technologyBased", "technology based"),

	/**
	 * The '<em><b>On The Job</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ON_THE_JOB_VALUE
	 * @generated
	 * @ordered
	 */
	ON_THE_JOB(2, "onTheJob", "on the job"),

	/**
	 * The '<em><b>Print</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRINT_VALUE
	 * @generated
	 * @ordered
	 */
	PRINT(3, "print", "print");

	/**
	 * The '<em><b>Conference Workshop</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFERENCE_WORKSHOP
	 * @model name="conferenceWorkshop" literal="conference/workshop"
	 * @generated
	 * @ordered
	 */
	public static final int CONFERENCE_WORKSHOP_VALUE = 0;

	/**
	 * The '<em><b>Technology Based</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNOLOGY_BASED
	 * @model name="technologyBased" literal="technology based"
	 * @generated
	 * @ordered
	 */
	public static final int TECHNOLOGY_BASED_VALUE = 1;

	/**
	 * The '<em><b>On The Job</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ON_THE_JOB
	 * @model name="onTheJob" literal="on the job"
	 * @generated
	 * @ordered
	 */
	public static final int ON_THE_JOB_VALUE = 2;

	/**
	 * The '<em><b>Print</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRINT
	 * @model name="print"
	 * @generated
	 * @ordered
	 */
	public static final int PRINT_VALUE = 3;

	/**
	 * An array of all the '<em><b>Participation Modality Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ParticipationModalityType[] VALUES_ARRAY = new ParticipationModalityType[] {
			CONFERENCE_WORKSHOP, TECHNOLOGY_BASED, ON_THE_JOB, PRINT, };

	/**
	 * A public read-only list of all the '<em><b>Participation Modality Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ParticipationModalityType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Participation Modality Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ParticipationModalityType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParticipationModalityType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Participation Modality Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ParticipationModalityType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParticipationModalityType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Participation Modality Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ParticipationModalityType get(int value) {
		switch (value) {
		case CONFERENCE_WORKSHOP_VALUE:
			return CONFERENCE_WORKSHOP;
		case TECHNOLOGY_BASED_VALUE:
			return TECHNOLOGY_BASED;
		case ON_THE_JOB_VALUE:
			return ON_THE_JOB;
		case PRINT_VALUE:
			return PRINT;
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
	private ParticipationModalityType(int value, String name, String literal) {
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

} //ParticipationModalityType
