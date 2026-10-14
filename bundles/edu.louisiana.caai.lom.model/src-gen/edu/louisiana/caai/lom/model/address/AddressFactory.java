/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.address.AddressPackage
 * @generated
 */
public interface AddressFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AddressFactory eINSTANCE = edu.louisiana.caai.lom.model.address.impl.AddressFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	AddressType createAddressType();

	/**
	 * Returns a new object of class '<em>Country Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Country Type</em>'.
	 * @generated
	 */
	CountryType createCountryType();

	/**
	 * Returns a new object of class '<em>Street Address Line Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Street Address Line Type</em>'.
	 * @generated
	 */
	StreetAddressLineType createStreetAddressLineType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AddressPackage getAddressPackage();

} //AddressFactory
