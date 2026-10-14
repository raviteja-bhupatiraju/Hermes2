/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage
 * @generated
 */
public interface HealthcareFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HealthcareFactory eINSTANCE = edu.louisiana.caai.lom.model.healthcare.impl.HealthcareFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Activity Location Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity Location Type</em>'.
	 * @generated
	 */
	ActivityLocationType createActivityLocationType();

	/**
	 * Returns a new object of class '<em>Credits Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Credits Type</em>'.
	 * @generated
	 */
	CreditsType createCreditsType();

	/**
	 * Returns a new object of class '<em>Custom Elements Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Custom Elements Type</em>'.
	 * @generated
	 */
	CustomElementsType createCustomElementsType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Asset Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Asset Type</em>'.
	 * @generated
	 */
	HealthcareAssetType createHealthcareAssetType();

	/**
	 * Returns a new object of class '<em>Education Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Education Type</em>'.
	 * @generated
	 */
	HealthcareEducationType createHealthcareEducationType();

	/**
	 * Returns a new object of class '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metadata Type</em>'.
	 * @generated
	 */
	HealthcareMetadataType createHealthcareMetadataType();

	/**
	 * Returns a new object of class '<em>Target Audience Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Target Audience Type</em>'.
	 * @generated
	 */
	TargetAudienceType createTargetAudienceType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	HealthcarePackage getHealthcarePackage();

} //HealthcareFactory
