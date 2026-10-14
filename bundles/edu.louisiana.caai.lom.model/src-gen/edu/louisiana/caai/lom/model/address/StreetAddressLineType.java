/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Street Address Line Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getValue <em>Value</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions <em>Restrictions</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getStreetAddressLineType()
 * @model extendedMetaData="name='StreetAddressLineType' kind='simple'"
 * @generated
 */
public interface StreetAddressLineType extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getStreetAddressLineType_Value()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Restrictions</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.address.RestrictionsType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Restrictions</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @see #isSetRestrictions()
	 * @see #unsetRestrictions()
	 * @see #setRestrictions(RestrictionsType)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getStreetAddressLineType_Restrictions()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='restrictions'"
	 * @generated
	 */
	RestrictionsType getRestrictions();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Restrictions</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @see #isSetRestrictions()
	 * @see #unsetRestrictions()
	 * @see #getRestrictions()
	 * @generated
	 */
	void setRestrictions(RestrictionsType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRestrictions()
	 * @see #getRestrictions()
	 * @see #setRestrictions(RestrictionsType)
	 * @generated
	 */
	void unsetRestrictions();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions <em>Restrictions</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Restrictions</em>' attribute is set.
	 * @see #unsetRestrictions()
	 * @see #getRestrictions()
	 * @see #setRestrictions(RestrictionsType)
	 * @generated
	 */
	boolean isSetRestrictions();

} // StreetAddressLineType
