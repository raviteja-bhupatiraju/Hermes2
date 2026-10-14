/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Category Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressCategoryType()
 * @model extendedMetaData="name='AddressCategoryType'"
 * @generated
 */
public enum AddressCategoryType implements Enumerator {
	/**
	 * The '<em><b>Residential</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESIDENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	RESIDENTIAL(0, "Residential", "Residential"),

	/**
	 * The '<em><b>Business</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BUSINESS_VALUE
	 * @generated
	 * @ordered
	 */
	BUSINESS(1, "Business", "Business"),

	/**
	 * The '<em><b>Undeliverable</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDELIVERABLE_VALUE
	 * @generated
	 * @ordered
	 */
	UNDELIVERABLE(2, "Undeliverable", "Undeliverable");

	/**
	 * The '<em><b>Residential</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESIDENTIAL
	 * @model name="Residential"
	 * @generated
	 * @ordered
	 */
	public static final int RESIDENTIAL_VALUE = 0;

	/**
	 * The '<em><b>Business</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BUSINESS
	 * @model name="Business"
	 * @generated
	 * @ordered
	 */
	public static final int BUSINESS_VALUE = 1;

	/**
	 * The '<em><b>Undeliverable</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDELIVERABLE
	 * @model name="Undeliverable"
	 * @generated
	 * @ordered
	 */
	public static final int UNDELIVERABLE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AddressCategoryType[] VALUES_ARRAY = new AddressCategoryType[] { RESIDENTIAL, BUSINESS,
			UNDELIVERABLE, };

	/**
	 * A public read-only list of all the '<em><b>Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AddressCategoryType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Category Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AddressCategoryType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddressCategoryType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Category Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AddressCategoryType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddressCategoryType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Category Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AddressCategoryType get(int value) {
		switch (value) {
		case RESIDENTIAL_VALUE:
			return RESIDENTIAL;
		case BUSINESS_VALUE:
			return BUSINESS;
		case UNDELIVERABLE_VALUE:
			return UNDELIVERABLE;
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
	private AddressCategoryType(int value, String name, String literal) {
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

} //AddressCategoryType
