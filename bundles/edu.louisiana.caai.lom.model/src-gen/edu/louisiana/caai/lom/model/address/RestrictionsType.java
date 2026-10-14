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
 * A representation of the literals of the enumeration '<em><b>Restrictions Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getRestrictionsType()
 * @model extendedMetaData="name='RestrictionsType'"
 * @generated
 */
public enum RestrictionsType implements Enumerator {
	/**
	 * The '<em><b>Unrestricted</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNRESTRICTED_VALUE
	 * @generated
	 * @ordered
	 */
	UNRESTRICTED(0, "Unrestricted", "Unrestricted"),

	/**
	 * The '<em><b>Restricted</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESTRICTED_VALUE
	 * @generated
	 * @ordered
	 */
	RESTRICTED(1, "Restricted", "Restricted"),

	/**
	 * The '<em><b>Confidential</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFIDENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	CONFIDENTIAL(2, "Confidential", "Confidential");

	/**
	 * The '<em><b>Unrestricted</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNRESTRICTED
	 * @model name="Unrestricted"
	 * @generated
	 * @ordered
	 */
	public static final int UNRESTRICTED_VALUE = 0;

	/**
	 * The '<em><b>Restricted</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESTRICTED
	 * @model name="Restricted"
	 * @generated
	 * @ordered
	 */
	public static final int RESTRICTED_VALUE = 1;

	/**
	 * The '<em><b>Confidential</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFIDENTIAL
	 * @model name="Confidential"
	 * @generated
	 * @ordered
	 */
	public static final int CONFIDENTIAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Restrictions Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final RestrictionsType[] VALUES_ARRAY = new RestrictionsType[] { UNRESTRICTED, RESTRICTED,
			CONFIDENTIAL, };

	/**
	 * A public read-only list of all the '<em><b>Restrictions Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<RestrictionsType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Restrictions Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RestrictionsType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RestrictionsType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Restrictions Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RestrictionsType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RestrictionsType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Restrictions Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RestrictionsType get(int value) {
		switch (value) {
		case UNRESTRICTED_VALUE:
			return UNRESTRICTED;
		case RESTRICTED_VALUE:
			return RESTRICTED;
		case CONFIDENTIAL_VALUE:
			return CONFIDENTIAL;
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
	private RestrictionsType(int value, String name, String literal) {
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

} //RestrictionsType
