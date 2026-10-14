/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditedProvider <em>Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditingBody <em>Accrediting Body</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityCertification <em>Activity Certification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityDelivery <em>Activity Delivery</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityFormat <em>Activity Format</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityLocation <em>Activity Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivitySponsorship <em>Activity Sponsorship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAnnotated <em>Annotated</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAudienceCategory <em>Audience Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getBuilding <em>Building</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getClinicalHistory <em>Clinical History</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupport <em>Commercial Support</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getContact <em>Contact</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCredits <em>Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditsAvailable <em>Credits Available</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditType <em>Credit Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditUnit <em>Credit Unit</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCustomElements <em>Custom Elements</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getEndDateTime <em>End Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileHeight <em>File Height</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileWidth <em>File Width</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareAsset <em>Healthcare Asset</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareEducation <em>Healthcare Education</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareMetadata <em>Healthcare Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMagnification <em>Magnification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMedicalImageType <em>Medical Image Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNonAccreditedProvider <em>Non Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNumberOfCredits <em>Number Of Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelDescription <em>Off Label Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelUse <em>Off Label Use</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getPacing <em>Pacing</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getParticipationModality <em>Participation Modality</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getProfession <em>Profession</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReadingLevel <em>Reading Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRoom <em>Room</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecialty <em>Specialty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecimenType <em>Specimen Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getStartDateTime <em>Start Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getTargetAudience <em>Target Audience</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accredited Provider</em>' attribute.
	 * @see #setAccreditedProvider(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_AccreditedProvider()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='accreditedProvider' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAccreditedProvider();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditedProvider <em>Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accredited Provider</em>' attribute.
	 * @see #getAccreditedProvider()
	 * @generated
	 */
	void setAccreditedProvider(String value);

	/**
	 * Returns the value of the '<em><b>Accrediting Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accrediting Body</em>' attribute.
	 * @see #setAccreditingBody(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_AccreditingBody()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='accreditingBody' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAccreditingBody();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditingBody <em>Accrediting Body</em>}' attribute.
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
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ActivityCertification()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activityCertification' namespace='##targetNamespace'"
	 * @generated
	 */
	String getActivityCertification();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityCertification <em>Activity Certification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Certification</em>' attribute.
	 * @see #getActivityCertification()
	 * @generated
	 */
	void setActivityCertification(String value);

	/**
	 * Returns the value of the '<em><b>Activity Delivery</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Delivery</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @see #setActivityDelivery(ActivityDeliveryType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ActivityDelivery()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activityDelivery' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivityDeliveryType getActivityDelivery();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityDelivery <em>Activity Delivery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Delivery</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @see #getActivityDelivery()
	 * @generated
	 */
	void setActivityDelivery(ActivityDeliveryType value);

	/**
	 * Returns the value of the '<em><b>Activity Format</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Format</em>' containment reference.
	 * @see #setActivityFormat(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ActivityFormat()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activityFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getActivityFormat();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityFormat <em>Activity Format</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Format</em>' containment reference.
	 * @see #getActivityFormat()
	 * @generated
	 */
	void setActivityFormat(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Activity Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Location</em>' containment reference.
	 * @see #setActivityLocation(ActivityLocationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ActivityLocation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activityLocation' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivityLocationType getActivityLocation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityLocation <em>Activity Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Location</em>' containment reference.
	 * @see #getActivityLocation()
	 * @generated
	 */
	void setActivityLocation(ActivityLocationType value);

	/**
	 * Returns the value of the '<em><b>Activity Sponsorship</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Sponsorship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see #setActivitySponsorship(ActivitySponsorshipType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ActivitySponsorship()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activitySponsorship' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivitySponsorshipType getActivitySponsorship();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivitySponsorship <em>Activity Sponsorship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Sponsorship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see #getActivitySponsorship()
	 * @generated
	 */
	void setActivitySponsorship(ActivitySponsorshipType value);

	/**
	 * Returns the value of the '<em><b>Annotated</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #setAnnotated(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Annotated()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='annotated' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getAnnotated();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAnnotated <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #getAnnotated()
	 * @generated
	 */
	void setAnnotated(YesNoType value);

	/**
	 * Returns the value of the '<em><b>Audience Category</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience Category</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @see #setAudienceCategory(AudienceCategoryType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_AudienceCategory()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='audienceCategory' namespace='##targetNamespace'"
	 * @generated
	 */
	AudienceCategoryType getAudienceCategory();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAudienceCategory <em>Audience Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Audience Category</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @see #getAudienceCategory()
	 * @generated
	 */
	void setAudienceCategory(AudienceCategoryType value);

	/**
	 * Returns the value of the '<em><b>Building</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Building</em>' attribute.
	 * @see #setBuilding(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Building()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='building' namespace='##targetNamespace'"
	 * @generated
	 */
	String getBuilding();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getBuilding <em>Building</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Building</em>' attribute.
	 * @see #getBuilding()
	 * @generated
	 */
	void setBuilding(String value);

	/**
	 * Returns the value of the '<em><b>Clinical History</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clinical History</em>' containment reference.
	 * @see #setClinicalHistory(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ClinicalHistory()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='clinicalHistory' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getClinicalHistory();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getClinicalHistory <em>Clinical History</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clinical History</em>' containment reference.
	 * @see #getClinicalHistory()
	 * @generated
	 */
	void setClinicalHistory(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Commercial Support</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commercial Support</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #setCommercialSupport(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CommercialSupport()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='commercialSupport' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getCommercialSupport();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupport <em>Commercial Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commercial Support</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #getCommercialSupport()
	 * @generated
	 */
	void setCommercialSupport(YesNoType value);

	/**
	 * Returns the value of the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commercial Support Acknowledgement</em>' containment reference.
	 * @see #setCommercialSupportAcknowledgement(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CommercialSupportAcknowledgement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='commercialSupportAcknowledgement' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getCommercialSupportAcknowledgement();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commercial Support Acknowledgement</em>' containment reference.
	 * @see #getCommercialSupportAcknowledgement()
	 * @generated
	 */
	void setCommercialSupportAcknowledgement(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Contact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' attribute.
	 * @see #setContact(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Contact()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='contact' namespace='##targetNamespace'"
	 * @generated
	 */
	String getContact();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getContact <em>Contact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' attribute.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(String value);

	/**
	 * Returns the value of the '<em><b>Credits</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credits</em>' containment reference.
	 * @see #setCredits(CreditsType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Credits()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='credits' namespace='##targetNamespace'"
	 * @generated
	 */
	CreditsType getCredits();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCredits <em>Credits</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credits</em>' containment reference.
	 * @see #getCredits()
	 * @generated
	 */
	void setCredits(CreditsType value);

	/**
	 * Returns the value of the '<em><b>Credits Available</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credits Available</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #setCreditsAvailable(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CreditsAvailable()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='creditsAvailable' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getCreditsAvailable();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditsAvailable <em>Credits Available</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credits Available</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #getCreditsAvailable()
	 * @generated
	 */
	void setCreditsAvailable(YesNoType value);

	/**
	 * Returns the value of the '<em><b>Credit Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credit Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see #setCreditType(CreditTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CreditType()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='creditType' namespace='##targetNamespace'"
	 * @generated
	 */
	CreditTypeType getCreditType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditType <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credit Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see #getCreditType()
	 * @generated
	 */
	void setCreditType(CreditTypeType value);

	/**
	 * Returns the value of the '<em><b>Credit Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credit Unit</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see #setCreditUnit(CreditUnitType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CreditUnit()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='creditUnit' namespace='##targetNamespace'"
	 * @generated
	 */
	CreditUnitType getCreditUnit();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditUnit <em>Credit Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credit Unit</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see #getCreditUnit()
	 * @generated
	 */
	void setCreditUnit(CreditUnitType value);

	/**
	 * Returns the value of the '<em><b>Custom Elements</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Elements</em>' containment reference.
	 * @see #setCustomElements(CustomElementsType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_CustomElements()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='customElements' namespace='##targetNamespace'"
	 * @generated
	 */
	CustomElementsType getCustomElements();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCustomElements <em>Custom Elements</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Elements</em>' containment reference.
	 * @see #getCustomElements()
	 * @generated
	 */
	void setCustomElements(CustomElementsType value);

	/**
	 * Returns the value of the '<em><b>End Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date Time</em>' attribute.
	 * @see #setEndDateTime(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_EndDateTime()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.DateTime" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='endDateTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getEndDateTime();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getEndDateTime <em>End Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date Time</em>' attribute.
	 * @see #getEndDateTime()
	 * @generated
	 */
	void setEndDateTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiration Date</em>' attribute.
	 * @see #setExpirationDate(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ExpirationDate()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Date" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expirationDate' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getExpirationDate();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getExpirationDate <em>Expiration Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiration Date</em>' attribute.
	 * @see #getExpirationDate()
	 * @generated
	 */
	void setExpirationDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>File Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Height</em>' attribute.
	 * @see #setFileHeight(BigInteger)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_FileHeight()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fileHeight' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getFileHeight();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileHeight <em>File Height</em>}' attribute.
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
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_FileWidth()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fileWidth' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getFileWidth();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileWidth <em>File Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Width</em>' attribute.
	 * @see #getFileWidth()
	 * @generated
	 */
	void setFileWidth(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Healthcare Asset</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Asset</em>' containment reference.
	 * @see #setHealthcareAsset(HealthcareAssetType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_HealthcareAsset()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='healthcareAsset' namespace='##targetNamespace'"
	 * @generated
	 */
	HealthcareAssetType getHealthcareAsset();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareAsset <em>Healthcare Asset</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Healthcare Asset</em>' containment reference.
	 * @see #getHealthcareAsset()
	 * @generated
	 */
	void setHealthcareAsset(HealthcareAssetType value);

	/**
	 * Returns the value of the '<em><b>Healthcare Education</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Education</em>' containment reference.
	 * @see #setHealthcareEducation(HealthcareEducationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_HealthcareEducation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='healthcareEducation' namespace='##targetNamespace'"
	 * @generated
	 */
	HealthcareEducationType getHealthcareEducation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareEducation <em>Healthcare Education</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Healthcare Education</em>' containment reference.
	 * @see #getHealthcareEducation()
	 * @generated
	 */
	void setHealthcareEducation(HealthcareEducationType value);

	/**
	 * Returns the value of the '<em><b>Healthcare Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Metadata</em>' containment reference.
	 * @see #setHealthcareMetadata(HealthcareMetadataType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_HealthcareMetadata()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='healthcareMetadata' namespace='##targetNamespace'"
	 * @generated
	 */
	HealthcareMetadataType getHealthcareMetadata();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareMetadata <em>Healthcare Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Healthcare Metadata</em>' containment reference.
	 * @see #getHealthcareMetadata()
	 * @generated
	 */
	void setHealthcareMetadata(HealthcareMetadataType value);

	/**
	 * Returns the value of the '<em><b>Magnification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Magnification</em>' attribute.
	 * @see #setMagnification(BigInteger)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Magnification()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='magnification' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getMagnification();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMagnification <em>Magnification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Magnification</em>' attribute.
	 * @see #getMagnification()
	 * @generated
	 */
	void setMagnification(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Medical Image Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Medical Image Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see #setMedicalImageType(MedicalImageTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_MedicalImageType()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='medicalImageType' namespace='##targetNamespace'"
	 * @generated
	 */
	MedicalImageTypeType getMedicalImageType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMedicalImageType <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Medical Image Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see #getMedicalImageType()
	 * @generated
	 */
	void setMedicalImageType(MedicalImageTypeType value);

	/**
	 * Returns the value of the '<em><b>Non Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Accredited Provider</em>' attribute.
	 * @see #setNonAccreditedProvider(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_NonAccreditedProvider()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='nonAccreditedProvider' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNonAccreditedProvider();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNonAccreditedProvider <em>Non Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Accredited Provider</em>' attribute.
	 * @see #getNonAccreditedProvider()
	 * @generated
	 */
	void setNonAccreditedProvider(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Credits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Credits</em>' attribute.
	 * @see #setNumberOfCredits(BigDecimal)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_NumberOfCredits()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Decimal" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='numberOfCredits' namespace='##targetNamespace'"
	 * @generated
	 */
	BigDecimal getNumberOfCredits();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNumberOfCredits <em>Number Of Credits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Credits</em>' attribute.
	 * @see #getNumberOfCredits()
	 * @generated
	 */
	void setNumberOfCredits(BigDecimal value);

	/**
	 * Returns the value of the '<em><b>Off Label Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Off Label Description</em>' containment reference.
	 * @see #setOffLabelDescription(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_OffLabelDescription()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='offLabelDescription' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getOffLabelDescription();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelDescription <em>Off Label Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Off Label Description</em>' containment reference.
	 * @see #getOffLabelDescription()
	 * @generated
	 */
	void setOffLabelDescription(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Off Label Use</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Off Label Use</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #setOffLabelUse(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_OffLabelUse()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='offLabelUse' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getOffLabelUse();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelUse <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Off Label Use</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #getOffLabelUse()
	 * @generated
	 */
	void setOffLabelUse(YesNoType value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.OrientationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see #setOrientation(OrientationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Orientation()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='orientation' namespace='##targetNamespace'"
	 * @generated
	 */
	OrientationType getOrientation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(OrientationType value);

	/**
	 * Returns the value of the '<em><b>Pacing</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.PacingType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pacing</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see #setPacing(PacingType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Pacing()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pacing' namespace='##targetNamespace'"
	 * @generated
	 */
	PacingType getPacing();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getPacing <em>Pacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pacing</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see #getPacing()
	 * @generated
	 */
	void setPacing(PacingType value);

	/**
	 * Returns the value of the '<em><b>Participation Modality</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Participation Modality</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @see #setParticipationModality(ParticipationModalityType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ParticipationModality()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='participationModality' namespace='##targetNamespace'"
	 * @generated
	 */
	ParticipationModalityType getParticipationModality();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getParticipationModality <em>Participation Modality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Participation Modality</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @see #getParticipationModality()
	 * @generated
	 */
	void setParticipationModality(ParticipationModalityType value);

	/**
	 * Returns the value of the '<em><b>Profession</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profession</em>' containment reference.
	 * @see #setProfession(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Profession()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='profession' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getProfession();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getProfession <em>Profession</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Profession</em>' containment reference.
	 * @see #getProfession()
	 * @generated
	 */
	void setProfession(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Reading Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reading Level</em>' containment reference.
	 * @see #setReadingLevel(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ReadingLevel()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='readingLevel' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getReadingLevel();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReadingLevel <em>Reading Level</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reading Level</em>' containment reference.
	 * @see #getReadingLevel()
	 * @generated
	 */
	void setReadingLevel(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Release Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Release Date</em>' attribute.
	 * @see #setReleaseDate(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_ReleaseDate()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Date" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='releaseDate' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getReleaseDate();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReleaseDate <em>Release Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Release Date</em>' attribute.
	 * @see #getReleaseDate()
	 * @generated
	 */
	void setReleaseDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Relevant Financial Relationship</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relevant Financial Relationship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #setRelevantFinancialRelationship(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_RelevantFinancialRelationship()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='relevantFinancialRelationship' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getRelevantFinancialRelationship();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relevant Financial Relationship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #getRelevantFinancialRelationship()
	 * @generated
	 */
	void setRelevantFinancialRelationship(YesNoType value);

	/**
	 * Returns the value of the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relevant Financial Relationship Disclosure</em>' containment reference.
	 * @see #setRelevantFinancialRelationshipDisclosure(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_RelevantFinancialRelationshipDisclosure()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='relevantFinancialRelationshipDisclosure' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getRelevantFinancialRelationshipDisclosure();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relevant Financial Relationship Disclosure</em>' containment reference.
	 * @see #getRelevantFinancialRelationshipDisclosure()
	 * @generated
	 */
	void setRelevantFinancialRelationshipDisclosure(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Room</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Room</em>' attribute.
	 * @see #setRoom(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Room()
	 * @model unique="false" dataType="edu.louisiana.caai.lom.model.lom.CharacterString" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='room' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRoom();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRoom <em>Room</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Room</em>' attribute.
	 * @see #getRoom()
	 * @generated
	 */
	void setRoom(String value);

	/**
	 * Returns the value of the '<em><b>Specialty</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specialty</em>' containment reference.
	 * @see #setSpecialty(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_Specialty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='specialty' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getSpecialty();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecialty <em>Specialty</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specialty</em>' containment reference.
	 * @see #getSpecialty()
	 * @generated
	 */
	void setSpecialty(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Specimen Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specimen Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see #setSpecimenType(SpecimenTypeType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_SpecimenType()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='specimenType' namespace='##targetNamespace'"
	 * @generated
	 */
	SpecimenTypeType getSpecimenType();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecimenType <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specimen Type</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see #getSpecimenType()
	 * @generated
	 */
	void setSpecimenType(SpecimenTypeType value);

	/**
	 * Returns the value of the '<em><b>Start Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Date Time</em>' attribute.
	 * @see #setStartDateTime(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_StartDateTime()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.DateTime" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='startDateTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getStartDateTime();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getStartDateTime <em>Start Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Date Time</em>' attribute.
	 * @see #getStartDateTime()
	 * @generated
	 */
	void setStartDateTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Target Audience</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Audience</em>' containment reference.
	 * @see #setTargetAudience(TargetAudienceType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getDocumentRoot_TargetAudience()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='targetAudience' namespace='##targetNamespace'"
	 * @generated
	 */
	TargetAudienceType getTargetAudience();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getTargetAudience <em>Target Audience</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Audience</em>' containment reference.
	 * @see #getTargetAudience()
	 * @generated
	 */
	void setTargetAudience(TargetAudienceType value);

} // DocumentRoot
