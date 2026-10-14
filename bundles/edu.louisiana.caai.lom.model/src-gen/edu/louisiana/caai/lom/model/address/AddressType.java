/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getID <em>ID</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getOrganization <em>Organization</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getStreetAddressLine <em>Street Address Line</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getCity <em>City</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getStateOrProvince <em>State Or Province</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getRegion <em>Region</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getDistrict <em>District</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getCountry <em>Country</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory <em>Address Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.AddressType#getRestrictions <em>Restrictions</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType()
 * @model extendedMetaData="name='AddressType' kind='elementOnly'"
 * @generated
 */
public interface AddressType extends EObject {
	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_ID()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='ID' namespace='##targetNamespace'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_Organization()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='Organization' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getOrganization();

	/**
	 * Returns the value of the '<em><b>Street Address Line</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.address.StreetAddressLineType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Street Address Line</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_StreetAddressLine()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='StreetAddressLine' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<StreetAddressLineType> getStreetAddressLine();

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_City()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='City' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCity();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getCity <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
	void setCity(String value);

	/**
	 * Returns the value of the '<em><b>State Or Province</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Or Province</em>' attribute.
	 * @see #setStateOrProvince(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_StateOrProvince()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='StateOrProvince' namespace='##targetNamespace'"
	 * @generated
	 */
	String getStateOrProvince();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getStateOrProvince <em>State Or Province</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Or Province</em>' attribute.
	 * @see #getStateOrProvince()
	 * @generated
	 */
	void setStateOrProvince(String value);

	/**
	 * Returns the value of the '<em><b>Postal Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Postal Code</em>' attribute.
	 * @see #setPostalCode(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_PostalCode()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='PostalCode' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPostalCode();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getPostalCode <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postal Code</em>' attribute.
	 * @see #getPostalCode()
	 * @generated
	 */
	void setPostalCode(String value);

	/**
	 * Returns the value of the '<em><b>Region</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Region</em>' attribute.
	 * @see #setRegion(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_Region()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='Region' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRegion();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getRegion <em>Region</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Region</em>' attribute.
	 * @see #getRegion()
	 * @generated
	 */
	void setRegion(String value);

	/**
	 * Returns the value of the '<em><b>District</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>District</em>' attribute.
	 * @see #setDistrict(String)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_District()
	 * @model dataType="edu.louisiana.caai.lom.model.address.NonNullString"
	 *        extendedMetaData="kind='element' name='District' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDistrict();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getDistrict <em>District</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>District</em>' attribute.
	 * @see #getDistrict()
	 * @generated
	 */
	void setDistrict(String value);

	/**
	 * Returns the value of the '<em><b>Country</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' containment reference.
	 * @see #setCountry(CountryType)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_Country()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Country' namespace='##targetNamespace'"
	 * @generated
	 */
	CountryType getCountry();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getCountry <em>Country</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' containment reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(CountryType value);

	/**
	 * Returns the value of the '<em><b>Address Category</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.address.AddressCategoryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address Category</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @see #isSetAddressCategory()
	 * @see #unsetAddressCategory()
	 * @see #setAddressCategory(AddressCategoryType)
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_AddressCategory()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='addressCategory'"
	 * @generated
	 */
	AddressCategoryType getAddressCategory();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory <em>Address Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address Category</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @see #isSetAddressCategory()
	 * @see #unsetAddressCategory()
	 * @see #getAddressCategory()
	 * @generated
	 */
	void setAddressCategory(AddressCategoryType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory <em>Address Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAddressCategory()
	 * @see #getAddressCategory()
	 * @see #setAddressCategory(AddressCategoryType)
	 * @generated
	 */
	void unsetAddressCategory();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory <em>Address Category</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Address Category</em>' attribute is set.
	 * @see #unsetAddressCategory()
	 * @see #getAddressCategory()
	 * @see #setAddressCategory(AddressCategoryType)
	 * @generated
	 */
	boolean isSetAddressCategory();

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
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#getAddressType_Restrictions()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='restrictions'"
	 * @generated
	 */
	RestrictionsType getRestrictions();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getRestrictions <em>Restrictions</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getRestrictions <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRestrictions()
	 * @see #getRestrictions()
	 * @see #setRestrictions(RestrictionsType)
	 * @generated
	 */
	void unsetRestrictions();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.address.AddressType#getRestrictions <em>Restrictions</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Restrictions</em>' attribute is set.
	 * @see #unsetRestrictions()
	 * @see #getRestrictions()
	 * @see #setRestrictions(RestrictionsType)
	 * @generated
	 */
	boolean isSetRestrictions();

} // AddressType
