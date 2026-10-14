/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Credits Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditingBody <em>Accrediting Body</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getActivityCertification <em>Activity Certification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType <em>Credit Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit <em>Credit Unit</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing <em>Pacing</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditedProvider <em>Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getNonAccreditedProvider <em>Non Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getNumberOfCredits <em>Number Of Credits</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType()
 * @model extendedMetaData="name='creditsType' kind='elementOnly'"
 * @generated
 */
public interface CreditsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Accrediting Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accrediting Body</em>' attribute.
	 * @see #setAccreditingBody(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_AccreditingBody()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='accreditingBody' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAccreditingBody();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditingBody <em>Accrediting Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accrediting Body</em>' attribute.
	 * @see #getAccreditingBody()
	 * @generated
	 */
	void setAccreditingBody(String value);

	/**
	 * Returns the value of the '<em><b>Activity Certification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Certification</em>' attribute.
	 * @see #setActivityCertification(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_ActivityCertification()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='activityCertification' namespace='##targetNamespace'"
	 * @generated
	 */
	String getActivityCertification();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getActivityCertification <em>Activity Certification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Certification</em>' attribute.
	 * @see #getActivityCertification()
	 * @generated
	 */
	void setActivityCertification(String value);

	/**
	 * Returns the value of the '<em><b>Credit Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credit Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see #isSetCreditType()
	 * @see #unsetCreditType()
	 * @see #setCreditType(CreditTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_CreditType()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='creditType' namespace='##targetNamespace'"
	 * @generated
	 */
	CreditTypeType getCreditType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credit Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see #isSetCreditType()
	 * @see #unsetCreditType()
	 * @see #getCreditType()
	 * @generated
	 */
	void setCreditType(CreditTypeType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreditType()
	 * @see #getCreditType()
	 * @see #setCreditType(CreditTypeType)
	 * @generated
	 */
	void unsetCreditType();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType <em>Credit Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Credit Type</em>' attribute is set.
	 * @see #unsetCreditType()
	 * @see #getCreditType()
	 * @see #setCreditType(CreditTypeType)
	 * @generated
	 */
	boolean isSetCreditType();

	/**
	 * Returns the value of the '<em><b>Credit Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credit Unit</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see #isSetCreditUnit()
	 * @see #unsetCreditUnit()
	 * @see #setCreditUnit(CreditUnitType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_CreditUnit()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='creditUnit' namespace='##targetNamespace'"
	 * @generated
	 */
	CreditUnitType getCreditUnit();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit <em>Credit Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credit Unit</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see #isSetCreditUnit()
	 * @see #unsetCreditUnit()
	 * @see #getCreditUnit()
	 * @generated
	 */
	void setCreditUnit(CreditUnitType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit <em>Credit Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreditUnit()
	 * @see #getCreditUnit()
	 * @see #setCreditUnit(CreditUnitType)
	 * @generated
	 */
	void unsetCreditUnit();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit <em>Credit Unit</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Credit Unit</em>' attribute is set.
	 * @see #unsetCreditUnit()
	 * @see #getCreditUnit()
	 * @see #setCreditUnit(CreditUnitType)
	 * @generated
	 */
	boolean isSetCreditUnit();

	/**
	 * Returns the value of the '<em><b>Pacing</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.PacingType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pacing</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see #isSetPacing()
	 * @see #unsetPacing()
	 * @see #setPacing(PacingType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_Pacing()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='pacing' namespace='##targetNamespace'"
	 * @generated
	 */
	PacingType getPacing();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing <em>Pacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pacing</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see #isSetPacing()
	 * @see #unsetPacing()
	 * @see #getPacing()
	 * @generated
	 */
	void setPacing(PacingType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing <em>Pacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPacing()
	 * @see #getPacing()
	 * @see #setPacing(PacingType)
	 * @generated
	 */
	void unsetPacing();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing <em>Pacing</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Pacing</em>' attribute is set.
	 * @see #unsetPacing()
	 * @see #getPacing()
	 * @see #setPacing(PacingType)
	 * @generated
	 */
	boolean isSetPacing();

	/**
	 * Returns the value of the '<em><b>Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accredited Provider</em>' attribute.
	 * @see #setAccreditedProvider(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_AccreditedProvider()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='accreditedProvider' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAccreditedProvider();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditedProvider <em>Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accredited Provider</em>' attribute.
	 * @see #getAccreditedProvider()
	 * @generated
	 */
	void setAccreditedProvider(String value);

	/**
	 * Returns the value of the '<em><b>Non Accredited Provider</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Accredited Provider</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_NonAccreditedProvider()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='nonAccreditedProvider' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getNonAccreditedProvider();

	/**
	 * Returns the value of the '<em><b>Release Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Release Date</em>' attribute.
	 * @see #setReleaseDate(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_ReleaseDate()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Date"
	 *        extendedMetaData="kind='element' name='releaseDate' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getReleaseDate();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getReleaseDate <em>Release Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Release Date</em>' attribute.
	 * @see #getReleaseDate()
	 * @generated
	 */
	void setReleaseDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiration Date</em>' attribute.
	 * @see #setExpirationDate(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_ExpirationDate()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Date"
	 *        extendedMetaData="kind='element' name='expirationDate' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getExpirationDate();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getExpirationDate <em>Expiration Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiration Date</em>' attribute.
	 * @see #getExpirationDate()
	 * @generated
	 */
	void setExpirationDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Number Of Credits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Credits</em>' attribute.
	 * @see #setNumberOfCredits(BigDecimal)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getCreditsType_NumberOfCredits()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
	 *        extendedMetaData="kind='element' name='numberOfCredits' namespace='##targetNamespace'"
	 * @generated
	 */
	BigDecimal getNumberOfCredits();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getNumberOfCredits <em>Number Of Credits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Credits</em>' attribute.
	 * @see #getNumberOfCredits()
	 * @generated
	 */
	void setNumberOfCredits(BigDecimal value);

} // CreditsType
