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
 * A representation of the literals of the enumeration '<em><b>Orientation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getOrientationType()
 * @model extendedMetaData="name='orientationType'"
 * @generated
 */
public enum OrientationType implements Enumerator {
	/**
	 * The '<em><b>Axial</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AXIAL_VALUE
	 * @generated
	 * @ordered
	 */
	AXIAL(0, "axial", "axial"),

	/**
	 * The '<em><b>Coronal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORONAL_VALUE
	 * @generated
	 * @ordered
	 */
	CORONAL(1, "coronal", "coronal"),

	/**
	 * The '<em><b>Horizontal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HORIZONTAL_VALUE
	 * @generated
	 * @ordered
	 */
	HORIZONTAL(2, "horizontal", "horizontal"),

	/**
	 * The '<em><b>Longitudinal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LONGITUDINAL_VALUE
	 * @generated
	 * @ordered
	 */
	LONGITUDINAL(3, "longitudinal", "longitudinal"),

	/**
	 * The '<em><b>Sagittal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SAGITTAL_VALUE
	 * @generated
	 * @ordered
	 */
	SAGITTAL(4, "sagittal", "sagittal"),

	/**
	 * The '<em><b>Transverse</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRANSVERSE_VALUE
	 * @generated
	 * @ordered
	 */
	TRANSVERSE(5, "transverse", "transverse");

	/**
	 * The '<em><b>Axial</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AXIAL
	 * @model name="axial"
	 * @generated
	 * @ordered
	 */
	public static final int AXIAL_VALUE = 0;

	/**
	 * The '<em><b>Coronal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORONAL
	 * @model name="coronal"
	 * @generated
	 * @ordered
	 */
	public static final int CORONAL_VALUE = 1;

	/**
	 * The '<em><b>Horizontal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HORIZONTAL
	 * @model name="horizontal"
	 * @generated
	 * @ordered
	 */
	public static final int HORIZONTAL_VALUE = 2;

	/**
	 * The '<em><b>Longitudinal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LONGITUDINAL
	 * @model name="longitudinal"
	 * @generated
	 * @ordered
	 */
	public static final int LONGITUDINAL_VALUE = 3;

	/**
	 * The '<em><b>Sagittal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SAGITTAL
	 * @model name="sagittal"
	 * @generated
	 * @ordered
	 */
	public static final int SAGITTAL_VALUE = 4;

	/**
	 * The '<em><b>Transverse</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRANSVERSE
	 * @model name="transverse"
	 * @generated
	 * @ordered
	 */
	public static final int TRANSVERSE_VALUE = 5;

	/**
	 * An array of all the '<em><b>Orientation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OrientationType[] VALUES_ARRAY = new OrientationType[] { AXIAL, CORONAL, HORIZONTAL,
			LONGITUDINAL, SAGITTAL, TRANSVERSE, };

	/**
	 * A public read-only list of all the '<em><b>Orientation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OrientationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Orientation Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OrientationType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OrientationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Orientation Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OrientationType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OrientationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Orientation Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OrientationType get(int value) {
		switch (value) {
		case AXIAL_VALUE:
			return AXIAL;
		case CORONAL_VALUE:
			return CORONAL;
		case HORIZONTAL_VALUE:
			return HORIZONTAL;
		case LONGITUDINAL_VALUE:
			return LONGITUDINAL;
		case SAGITTAL_VALUE:
			return SAGITTAL;
		case TRANSVERSE_VALUE:
			return TRANSVERSE;
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
	private OrientationType(int value, String name, String literal) {
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

} //OrientationType
