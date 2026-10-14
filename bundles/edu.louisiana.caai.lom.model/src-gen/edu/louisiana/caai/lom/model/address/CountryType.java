/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Country Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryName <em>Country Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryCode <em>Country Code</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getCountryType()
 * @model extendedMetaData="name='CountryType' kind='elementOnly'"
 * @generated
 */
public interface CountryType extends EObject {
	/**
	 * Returns the value of the '<em><b>Country Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country Name</em>' attribute.
	 * @see #setCountryName(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getCountryType_CountryName()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='CountryName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCountryName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryName <em>Country Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country Name</em>' attribute.
	 * @see #getCountryName()
	 * @generated
	 */
	void setCountryName(String value);

	/**
	 * Returns the value of the '<em><b>Country Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country Code</em>' attribute.
	 * @see #setCountryCode(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getCountryType_CountryCode()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='CountryCode' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCountryCode();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryCode <em>Country Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country Code</em>' attribute.
	 * @see #getCountryCode()
	 * @generated
	 */
	void setCountryCode(String value);

} // CountryType
