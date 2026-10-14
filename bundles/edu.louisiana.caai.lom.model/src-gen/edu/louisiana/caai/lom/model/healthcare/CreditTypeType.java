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
 * A representation of the literals of the enumeration '<em><b>Credit Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditTypeType()
 * @model extendedMetaData="name='creditTypeType'"
 * @generated
 */
public enum CreditTypeType implements Enumerator {
	/**
	 * The '<em><b>CME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CME_VALUE
	 * @generated
	 * @ordered
	 */
	CME(0, "CME", "CME"),

	/**
	 * The '<em><b>CE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CE_VALUE
	 * @generated
	 * @ordered
	 */
	CE(1, "CE", "CE"),

	/**
	 * The '<em><b>CNE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CNE_VALUE
	 * @generated
	 * @ordered
	 */
	CNE(2, "CNE", "CNE"),

	/**
	 * The '<em><b>CPE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_VALUE
	 * @generated
	 * @ordered
	 */
	CPE(3, "CPE", "CPE"),

	/**
	 * The '<em><b>CHES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHES_VALUE
	 * @generated
	 * @ordered
	 */
	CHES(4, "CHES", "CHES"),

	/**
	 * The '<em><b>CPD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPD_VALUE
	 * @generated
	 * @ordered
	 */
	CPD(5, "CPD", "CPD");

	/**
	 * The '<em><b>CME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CME_VALUE = 0;

	/**
	 * The '<em><b>CE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CE_VALUE = 1;

	/**
	 * The '<em><b>CNE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CNE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CNE_VALUE = 2;

	/**
	 * The '<em><b>CPE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_VALUE = 3;

	/**
	 * The '<em><b>CHES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHES_VALUE = 4;

	/**
	 * The '<em><b>CPD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPD_VALUE = 5;

	/**
	 * An array of all the '<em><b>Credit Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CreditTypeType[] VALUES_ARRAY = new CreditTypeType[] { CME, CE, CNE, CPE, CHES, CPD, };

	/**
	 * A public read-only list of all the '<em><b>Credit Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CreditTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Credit Type Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CreditTypeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CreditTypeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Credit Type Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CreditTypeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CreditTypeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Credit Type Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CreditTypeType get(int value) {
		switch (value) {
		case CME_VALUE:
			return CME;
		case CE_VALUE:
			return CE;
		case CNE_VALUE:
			return CNE;
		case CPE_VALUE:
			return CPE;
		case CHES_VALUE:
			return CHES;
		case CPD_VALUE:
			return CPD;
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
	private CreditTypeType(int value, String name, String literal) {
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

} //CreditTypeType
