/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address.impl;

import edu.louisiana.caai.lom.model.address.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddressFactoryImpl extends EFactoryImpl implements AddressFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddressFactory init() {
		try {
			AddressFactory theAddressFactory = (AddressFactory) EPackage.Registry.INSTANCE
					.getEFactory(AddressPackage.eNS_URI);
			if (theAddressFactory != null) {
				return theAddressFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AddressFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case AddressPackage.ADDRESS_TYPE:
			return createAddressType();
		case AddressPackage.COUNTRY_TYPE:
			return createCountryType();
		case AddressPackage.STREET_ADDRESS_LINE_TYPE:
			return createStreetAddressLineType();
		case AddressPackage.DOCUMENT_ROOT:
			return createDocumentRoot();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case AddressPackage.ADDRESS_CATEGORY_TYPE:
			return createAddressCategoryTypeFromString(eDataType, initialValue);
		case AddressPackage.RESTRICTIONS_TYPE:
			return createRestrictionsTypeFromString(eDataType, initialValue);
		case AddressPackage.ADDRESS_CATEGORY_TYPE_OBJECT:
			return createAddressCategoryTypeObjectFromString(eDataType, initialValue);
		case AddressPackage.NON_NULL_STRING:
			return createNonNullStringFromString(eDataType, initialValue);
		case AddressPackage.RESTRICTIONS_TYPE_OBJECT:
			return createRestrictionsTypeObjectFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case AddressPackage.ADDRESS_CATEGORY_TYPE:
			return convertAddressCategoryTypeToString(eDataType, instanceValue);
		case AddressPackage.RESTRICTIONS_TYPE:
			return convertRestrictionsTypeToString(eDataType, instanceValue);
		case AddressPackage.ADDRESS_CATEGORY_TYPE_OBJECT:
			return convertAddressCategoryTypeObjectToString(eDataType, instanceValue);
		case AddressPackage.NON_NULL_STRING:
			return convertNonNullStringToString(eDataType, instanceValue);
		case AddressPackage.RESTRICTIONS_TYPE_OBJECT:
			return convertRestrictionsTypeObjectToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressType createAddressType() {
		AddressTypeImpl addressType = new AddressTypeImpl();
		return addressType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CountryType createCountryType() {
		CountryTypeImpl countryType = new CountryTypeImpl();
		return countryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StreetAddressLineType createStreetAddressLineType() {
		StreetAddressLineTypeImpl streetAddressLineType = new StreetAddressLineTypeImpl();
		return streetAddressLineType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressCategoryType createAddressCategoryTypeFromString(EDataType eDataType, String initialValue) {
		AddressCategoryType result = AddressCategoryType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAddressCategoryTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RestrictionsType createRestrictionsTypeFromString(EDataType eDataType, String initialValue) {
		RestrictionsType result = RestrictionsType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestrictionsTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressCategoryType createAddressCategoryTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createAddressCategoryTypeFromString(AddressPackage.Literals.ADDRESS_CATEGORY_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAddressCategoryTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAddressCategoryTypeToString(AddressPackage.Literals.ADDRESS_CATEGORY_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createNonNullStringFromString(EDataType eDataType, String initialValue) {
		return (String) XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNonNullStringToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RestrictionsType createRestrictionsTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createRestrictionsTypeFromString(AddressPackage.Literals.RESTRICTIONS_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestrictionsTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRestrictionsTypeToString(AddressPackage.Literals.RESTRICTIONS_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressPackage getAddressPackage() {
		return (AddressPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AddressPackage getPackage() {
		return AddressPackage.eINSTANCE;
	}

} //AddressFactoryImpl
