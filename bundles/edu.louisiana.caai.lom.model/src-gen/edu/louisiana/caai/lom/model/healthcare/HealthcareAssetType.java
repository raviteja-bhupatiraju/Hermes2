/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Asset Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated <em>Annotated</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getClinicalHistory <em>Clinical History</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMagnification <em>Magnification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType <em>Medical Image Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType <em>Specimen Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileHeight <em>File Height</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileWidth <em>File Width</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType()
 * @model extendedMetaData="name='healthcareAssetType' kind='elementOnly'"
 * @generated
 */
public interface HealthcareAssetType extends EObject {
	/**
	 * Returns the value of the '<em><b>Annotated</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetAnnotated()
	 * @see #unsetAnnotated()
	 * @see #setAnnotated(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_Annotated()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='annotated' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getAnnotated();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetAnnotated()
	 * @see #unsetAnnotated()
	 * @see #getAnnotated()
	 * @generated
	 */
	void setAnnotated(YesNoType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAnnotated()
	 * @see #getAnnotated()
	 * @see #setAnnotated(YesNoType)
	 * @generated
	 */
	void unsetAnnotated();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated <em>Annotated</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Annotated</em>' attribute is set.
	 * @see #unsetAnnotated()
	 * @see #getAnnotated()
	 * @see #setAnnotated(YesNoType)
	 * @generated
	 */
	boolean isSetAnnotated();

	/**
	 * Returns the value of the '<em><b>Clinical History</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clinical History</em>' containment reference.
	 * @see #setClinicalHistory(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_ClinicalHistory()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='clinicalHistory' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getClinicalHistory();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getClinicalHistory <em>Clinical History</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clinical History</em>' containment reference.
	 * @see #getClinicalHistory()
	 * @generated
	 */
	void setClinicalHistory(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Magnification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Magnification</em>' attribute.
	 * @see #setMagnification(BigInteger)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_Magnification()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
	 *        extendedMetaData="kind='element' name='magnification' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getMagnification();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMagnification <em>Magnification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Magnification</em>' attribute.
	 * @see #getMagnification()
	 * @generated
	 */
	void setMagnification(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.OrientationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #setOrientation(OrientationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_Orientation()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='orientation' namespace='##targetNamespace'"
	 * @generated
	 */
	OrientationType getOrientation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(OrientationType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationType)
	 * @generated
	 */
	void unsetOrientation();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation <em>Orientation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Orientation</em>' attribute is set.
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationType)
	 * @generated
	 */
	boolean isSetOrientation();

	/**
	 * Returns the value of the '<em><b>Medical Image Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Medical Image Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see #isSetMedicalImageType()
	 * @see #unsetMedicalImageType()
	 * @see #setMedicalImageType(MedicalImageTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_MedicalImageType()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='medicalImageType' namespace='##targetNamespace'"
	 * @generated
	 */
	MedicalImageTypeType getMedicalImageType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Medical Image Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see #isSetMedicalImageType()
	 * @see #unsetMedicalImageType()
	 * @see #getMedicalImageType()
	 * @generated
	 */
	void setMedicalImageType(MedicalImageTypeType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMedicalImageType()
	 * @see #getMedicalImageType()
	 * @see #setMedicalImageType(MedicalImageTypeType)
	 * @generated
	 */
	void unsetMedicalImageType();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType <em>Medical Image Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Medical Image Type</em>' attribute is set.
	 * @see #unsetMedicalImageType()
	 * @see #getMedicalImageType()
	 * @see #setMedicalImageType(MedicalImageTypeType)
	 * @generated
	 */
	boolean isSetMedicalImageType();

	/**
	 * Returns the value of the '<em><b>Specimen Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specimen Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see #isSetSpecimenType()
	 * @see #unsetSpecimenType()
	 * @see #setSpecimenType(SpecimenTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_SpecimenType()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='specimenType' namespace='##targetNamespace'"
	 * @generated
	 */
	SpecimenTypeType getSpecimenType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specimen Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see #isSetSpecimenType()
	 * @see #unsetSpecimenType()
	 * @see #getSpecimenType()
	 * @generated
	 */
	void setSpecimenType(SpecimenTypeType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSpecimenType()
	 * @see #getSpecimenType()
	 * @see #setSpecimenType(SpecimenTypeType)
	 * @generated
	 */
	void unsetSpecimenType();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType <em>Specimen Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Specimen Type</em>' attribute is set.
	 * @see #unsetSpecimenType()
	 * @see #getSpecimenType()
	 * @see #setSpecimenType(SpecimenTypeType)
	 * @generated
	 */
	boolean isSetSpecimenType();

	/**
	 * Returns the value of the '<em><b>File Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Height</em>' attribute.
	 * @see #setFileHeight(BigInteger)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_FileHeight()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
	 *        extendedMetaData="kind='element' name='fileHeight' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getFileHeight();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileHeight <em>File Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Height</em>' attribute.
	 * @see #getFileHeight()
	 * @generated
	 */
	void setFileHeight(BigInteger value);

	/**
	 * Returns the value of the '<em><b>File Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Width</em>' attribute.
	 * @see #setFileWidth(BigInteger)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_FileWidth()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
	 *        extendedMetaData="kind='element' name='fileWidth' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getFileWidth();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileWidth <em>File Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Width</em>' attribute.
	 * @see #getFileWidth()
	 * @generated
	 */
	void setFileWidth(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"healthcareAsset"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareAssetType_UniqueElementName()
	 * @model default="healthcareAsset" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	String getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // HealthcareAssetType
