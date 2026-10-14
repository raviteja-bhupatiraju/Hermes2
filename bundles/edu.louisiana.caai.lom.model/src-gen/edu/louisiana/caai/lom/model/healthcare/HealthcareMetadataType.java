/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareEducation <em>Healthcare Education</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareAsset <em>Healthcare Asset</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareMetadataType()
 * @model extendedMetaData="name='healthcareMetadataType' kind='elementOnly'"
 * @generated
 */
public interface HealthcareMetadataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Healthcare Education</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Education</em>' containment reference.
	 * @see #setHealthcareEducation(HealthcareEducationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareMetadataType_HealthcareEducation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='healthcareEducation' namespace='##targetNamespace'"
	 * @generated
	 */
	HealthcareEducationType getHealthcareEducation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareEducation <em>Healthcare Education</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Healthcare Education</em>' containment reference.
	 * @see #getHealthcareEducation()
	 * @generated
	 */
	void setHealthcareEducation(HealthcareEducationType value);

	/**
	 * Returns the value of the '<em><b>Healthcare Asset</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Asset</em>' containment reference.
	 * @see #setHealthcareAsset(HealthcareAssetType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareMetadataType_HealthcareAsset()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='healthcareAsset' namespace='##targetNamespace'"
	 * @generated
	 */
	HealthcareAssetType getHealthcareAsset();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareAsset <em>Healthcare Asset</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Healthcare Asset</em>' containment reference.
	 * @see #getHealthcareAsset()
	 * @generated
	 */
	void setHealthcareAsset(HealthcareAssetType value);

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"healthcareMetadata"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareMetadataType_UniqueElementName()
	 * @model default="healthcareMetadata" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	String getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @generated
	 */
	void setUniqueElementName(String value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // HealthcareMetadataType
