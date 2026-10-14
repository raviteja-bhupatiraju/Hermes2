/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.address.AddressFactory
 * @model kind="package"
 * @generated
 */
public interface AddressPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "address";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://ns.medbiq.org/address/v1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "a";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AddressPackage eINSTANCE = edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressType()
	 * @generated
	 */
	int ADDRESS_TYPE = 0;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__ID = 0;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__ORGANIZATION = 1;

	/**
	 * The feature id for the '<em><b>Street Address Line</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__STREET_ADDRESS_LINE = 2;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__CITY = 3;

	/**
	 * The feature id for the '<em><b>State Or Province</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__STATE_OR_PROVINCE = 4;

	/**
	 * The feature id for the '<em><b>Postal Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__POSTAL_CODE = 5;

	/**
	 * The feature id for the '<em><b>Region</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__REGION = 6;

	/**
	 * The feature id for the '<em><b>District</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__DISTRICT = 7;

	/**
	 * The feature id for the '<em><b>Country</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__COUNTRY = 8;

	/**
	 * The feature id for the '<em><b>Address Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__ADDRESS_CATEGORY = 9;

	/**
	 * The feature id for the '<em><b>Restrictions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE__RESTRICTIONS = 10;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE_FEATURE_COUNT = 11;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl <em>Country Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getCountryType()
	 * @generated
	 */
	int COUNTRY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Country Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TYPE__COUNTRY_NAME = 0;

	/**
	 * The feature id for the '<em><b>Country Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TYPE__COUNTRY_CODE = 1;

	/**
	 * The number of structural features of the '<em>Country Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Country Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl <em>Street Address Line Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getStreetAddressLineType()
	 * @generated
	 */
	int STREET_ADDRESS_LINE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREET_ADDRESS_LINE_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Restrictions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREET_ADDRESS_LINE_TYPE__RESTRICTIONS = 1;

	/**
	 * The number of structural features of the '<em>Street Address Line Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREET_ADDRESS_LINE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Street Address Line Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREET_ADDRESS_LINE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.impl.DocumentRootImpl
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 3;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ADDRESS = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.AddressCategoryType <em>Category Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressCategoryType()
	 * @generated
	 */
	int ADDRESS_CATEGORY_TYPE = 4;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.address.RestrictionsType <em>Restrictions Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getRestrictionsType()
	 * @generated
	 */
	int RESTRICTIONS_TYPE = 5;

	/**
	 * The meta object id for the '<em>Category Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressCategoryTypeObject()
	 * @generated
	 */
	int ADDRESS_CATEGORY_TYPE_OBJECT = 6;

	/**
	 * The meta object id for the '<em>Non Null String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getNonNullString()
	 * @generated
	 */
	int NON_NULL_STRING = 7;

	/**
	 * The meta object id for the '<em>Restrictions Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getRestrictionsTypeObject()
	 * @generated
	 */
	int RESTRICTIONS_TYPE_OBJECT = 8;

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.address.AddressType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType
	 * @generated
	 */
	EClass getAddressType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getID()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_ID();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.address.AddressType#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Organization</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getOrganization()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_Organization();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.address.AddressType#getStreetAddressLine <em>Street Address Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Street Address Line</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getStreetAddressLine()
	 * @see #getAddressType()
	 * @generated
	 */
	EReference getAddressType_StreetAddressLine();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getCity()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_City();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getStateOrProvince <em>State Or Province</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Or Province</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getStateOrProvince()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_StateOrProvince();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getPostalCode <em>Postal Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Postal Code</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getPostalCode()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_PostalCode();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Region</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getRegion()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_Region();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getDistrict <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>District</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getDistrict()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_District();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.address.AddressType#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Country</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getCountry()
	 * @see #getAddressType()
	 * @generated
	 */
	EReference getAddressType_Country();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory <em>Address Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address Category</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getAddressCategory()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_AddressCategory();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.AddressType#getRestrictions <em>Restrictions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Restrictions</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressType#getRestrictions()
	 * @see #getAddressType()
	 * @generated
	 */
	EAttribute getAddressType_Restrictions();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.address.CountryType <em>Country Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country Type</em>'.
	 * @see edu.louisiana.caai.lom.model.address.CountryType
	 * @generated
	 */
	EClass getCountryType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryName <em>Country Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country Name</em>'.
	 * @see edu.louisiana.caai.lom.model.address.CountryType#getCountryName()
	 * @see #getCountryType()
	 * @generated
	 */
	EAttribute getCountryType_CountryName();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.CountryType#getCountryCode <em>Country Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country Code</em>'.
	 * @see edu.louisiana.caai.lom.model.address.CountryType#getCountryCode()
	 * @see #getCountryType()
	 * @generated
	 */
	EAttribute getCountryType_CountryCode();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType <em>Street Address Line Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Street Address Line Type</em>'.
	 * @see edu.louisiana.caai.lom.model.address.StreetAddressLineType
	 * @generated
	 */
	EClass getStreetAddressLineType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.address.StreetAddressLineType#getValue()
	 * @see #getStreetAddressLineType()
	 * @generated
	 */
	EAttribute getStreetAddressLineType_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions <em>Restrictions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Restrictions</em>'.
	 * @see edu.louisiana.caai.lom.model.address.StreetAddressLineType#getRestrictions()
	 * @see #getStreetAddressLineType()
	 * @generated
	 */
	EAttribute getStreetAddressLineType_Restrictions();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.address.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see edu.louisiana.caai.lom.model.address.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.address.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see edu.louisiana.caai.lom.model.address.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.address.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see edu.louisiana.caai.lom.model.address.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.address.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see edu.louisiana.caai.lom.model.address.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.address.DocumentRoot#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Address</em>'.
	 * @see edu.louisiana.caai.lom.model.address.DocumentRoot#getAddress()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Address();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.address.AddressCategoryType <em>Category Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Category Type</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @generated
	 */
	EEnum getAddressCategoryType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.address.RestrictionsType <em>Restrictions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Restrictions Type</em>'.
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @generated
	 */
	EEnum getRestrictionsType();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.address.AddressCategoryType <em>Category Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Category Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
	 * @model instanceClass="edu.louisiana.caai.lom.model.address.AddressCategoryType"
	 *        extendedMetaData="name='AddressCategoryType:Object' baseType='AddressCategoryType'"
	 * @generated
	 */
	EDataType getAddressCategoryTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Non Null String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Non Null String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='NonNullString' baseType='http://www.eclipse.org/emf/2003/XMLType#string' minLength='1'"
	 * @generated
	 */
	EDataType getNonNullString();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.address.RestrictionsType <em>Restrictions Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Restrictions Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
	 * @model instanceClass="edu.louisiana.caai.lom.model.address.RestrictionsType"
	 *        extendedMetaData="name='RestrictionsType:Object' baseType='RestrictionsType'"
	 * @generated
	 */
	EDataType getRestrictionsTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AddressFactory getAddressFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressType()
		 * @generated
		 */
		EClass ADDRESS_TYPE = eINSTANCE.getAddressType();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__ID = eINSTANCE.getAddressType_ID();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__ORGANIZATION = eINSTANCE.getAddressType_Organization();

		/**
		 * The meta object literal for the '<em><b>Street Address Line</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADDRESS_TYPE__STREET_ADDRESS_LINE = eINSTANCE.getAddressType_StreetAddressLine();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__CITY = eINSTANCE.getAddressType_City();

		/**
		 * The meta object literal for the '<em><b>State Or Province</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__STATE_OR_PROVINCE = eINSTANCE.getAddressType_StateOrProvince();

		/**
		 * The meta object literal for the '<em><b>Postal Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__POSTAL_CODE = eINSTANCE.getAddressType_PostalCode();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__REGION = eINSTANCE.getAddressType_Region();

		/**
		 * The meta object literal for the '<em><b>District</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__DISTRICT = eINSTANCE.getAddressType_District();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADDRESS_TYPE__COUNTRY = eINSTANCE.getAddressType_Country();

		/**
		 * The meta object literal for the '<em><b>Address Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__ADDRESS_CATEGORY = eINSTANCE.getAddressType_AddressCategory();

		/**
		 * The meta object literal for the '<em><b>Restrictions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS_TYPE__RESTRICTIONS = eINSTANCE.getAddressType_Restrictions();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl <em>Country Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getCountryType()
		 * @generated
		 */
		EClass COUNTRY_TYPE = eINSTANCE.getCountryType();

		/**
		 * The meta object literal for the '<em><b>Country Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTRY_TYPE__COUNTRY_NAME = eINSTANCE.getCountryType_CountryName();

		/**
		 * The meta object literal for the '<em><b>Country Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTRY_TYPE__COUNTRY_CODE = eINSTANCE.getCountryType_CountryCode();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl <em>Street Address Line Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getStreetAddressLineType()
		 * @generated
		 */
		EClass STREET_ADDRESS_LINE_TYPE = eINSTANCE.getStreetAddressLineType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREET_ADDRESS_LINE_TYPE__VALUE = eINSTANCE.getStreetAddressLineType_Value();

		/**
		 * The meta object literal for the '<em><b>Restrictions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREET_ADDRESS_LINE_TYPE__RESTRICTIONS = eINSTANCE.getStreetAddressLineType_Restrictions();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.impl.DocumentRootImpl
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ADDRESS = eINSTANCE.getDocumentRoot_Address();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.AddressCategoryType <em>Category Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressCategoryType()
		 * @generated
		 */
		EEnum ADDRESS_CATEGORY_TYPE = eINSTANCE.getAddressCategoryType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.address.RestrictionsType <em>Restrictions Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getRestrictionsType()
		 * @generated
		 */
		EEnum RESTRICTIONS_TYPE = eINSTANCE.getRestrictionsType();

		/**
		 * The meta object literal for the '<em>Category Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.AddressCategoryType
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getAddressCategoryTypeObject()
		 * @generated
		 */
		EDataType ADDRESS_CATEGORY_TYPE_OBJECT = eINSTANCE.getAddressCategoryTypeObject();

		/**
		 * The meta object literal for the '<em>Non Null String</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getNonNullString()
		 * @generated
		 */
		EDataType NON_NULL_STRING = eINSTANCE.getNonNullString();

		/**
		 * The meta object literal for the '<em>Restrictions Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.address.RestrictionsType
		 * @see edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl#getRestrictionsTypeObject()
		 * @generated
		 */
		EDataType RESTRICTIONS_TYPE_OBJECT = eINSTANCE.getRestrictionsTypeObject();

	}

} //AddressPackage
