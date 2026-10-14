/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Audience Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getAudienceCategory <em>Audience Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getProfession <em>Profession</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getSpecialty <em>Specialty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getReadingLevel <em>Reading Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType()
 * @model extendedMetaData="name='targetAudienceType' kind='elementOnly'"
 * @generated
 */
public interface TargetAudienceType extends EObject {
	/**
	 * Returns the value of the '<em><b>Audience Category</b></em>' attribute list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType}.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience Category</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType_AudienceCategory()
	 * @model unique="false"
	 *        extendedMetaData="kind='element' name='audienceCategory' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AudienceCategoryType> getAudienceCategory();

	/**
	 * Returns the value of the '<em><b>Profession</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profession</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType_Profession()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='profession' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LanguageString> getProfession();

	/**
	 * Returns the value of the '<em><b>Specialty</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specialty</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType_Specialty()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='specialty' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LanguageString> getSpecialty();

	/**
	 * Returns the value of the '<em><b>Reading Level</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reading Level</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType_ReadingLevel()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='readingLevel' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LanguageString> getReadingLevel();

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"targetAudience"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getTargetAudienceType_UniqueElementName()
	 * @model default="targetAudience" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	String getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // TargetAudienceType
