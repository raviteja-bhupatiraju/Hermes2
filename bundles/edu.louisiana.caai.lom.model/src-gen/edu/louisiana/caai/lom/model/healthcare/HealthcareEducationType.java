/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Education Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable <em>Credits Available</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCredits <em>Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getTargetAudience <em>Target Audience</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityLocation <em>Activity Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getStartDateTime <em>Start Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getEndDateTime <em>End Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship <em>Activity Sponsorship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getParticipationModality <em>Participation Modality</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityDelivery <em>Activity Delivery</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityFormat <em>Activity Format</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse <em>Off Label Use</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelDescription <em>Off Label Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport <em>Commercial Support</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getContact <em>Contact</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType()
 * @model extendedMetaData="name='healthcareEducationType' kind='elementOnly'"
 * @generated
 */
public interface HealthcareEducationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiration Date</em>' attribute.
	 * @see #setExpirationDate(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ExpirationDate()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Date"
	 *        extendedMetaData="kind='element' name='expirationDate' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getExpirationDate();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getExpirationDate <em>Expiration Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiration Date</em>' attribute.
	 * @see #getExpirationDate()
	 * @generated
	 */
	void setExpirationDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Credits Available</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credits Available</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetCreditsAvailable()
	 * @see #unsetCreditsAvailable()
	 * @see #setCreditsAvailable(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_CreditsAvailable()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='creditsAvailable' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getCreditsAvailable();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable <em>Credits Available</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credits Available</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetCreditsAvailable()
	 * @see #unsetCreditsAvailable()
	 * @see #getCreditsAvailable()
	 * @generated
	 */
	void setCreditsAvailable(YesNoType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable <em>Credits Available</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreditsAvailable()
	 * @see #getCreditsAvailable()
	 * @see #setCreditsAvailable(YesNoType)
	 * @generated
	 */
	void unsetCreditsAvailable();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable <em>Credits Available</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Credits Available</em>' attribute is set.
	 * @see #unsetCreditsAvailable()
	 * @see #getCreditsAvailable()
	 * @see #setCreditsAvailable(YesNoType)
	 * @generated
	 */
	boolean isSetCreditsAvailable();

	/**
	 * Returns the value of the '<em><b>Credits</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.CreditsType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credits</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_Credits()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='credits' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<CreditsType> getCredits();

	/**
	 * Returns the value of the '<em><b>Target Audience</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Audience</em>' containment reference.
	 * @see #setTargetAudience(TargetAudienceType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_TargetAudience()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='targetAudience' namespace='##targetNamespace'"
	 * @generated
	 */
	TargetAudienceType getTargetAudience();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getTargetAudience <em>Target Audience</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Audience</em>' containment reference.
	 * @see #getTargetAudience()
	 * @generated
	 */
	void setTargetAudience(TargetAudienceType value);

	/**
	 * Returns the value of the '<em><b>Activity Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Location</em>' containment reference.
	 * @see #setActivityLocation(ActivityLocationType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ActivityLocation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='activityLocation' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivityLocationType getActivityLocation();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityLocation <em>Activity Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Location</em>' containment reference.
	 * @see #getActivityLocation()
	 * @generated
	 */
	void setActivityLocation(ActivityLocationType value);

	/**
	 * Returns the value of the '<em><b>Start Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Date Time</em>' attribute.
	 * @see #setStartDateTime(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_StartDateTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='element' name='startDateTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getStartDateTime();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getStartDateTime <em>Start Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Date Time</em>' attribute.
	 * @see #getStartDateTime()
	 * @generated
	 */
	void setStartDateTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>End Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date Time</em>' attribute.
	 * @see #setEndDateTime(XMLGregorianCalendar)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_EndDateTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='element' name='endDateTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getEndDateTime();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getEndDateTime <em>End Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date Time</em>' attribute.
	 * @see #getEndDateTime()
	 * @generated
	 */
	void setEndDateTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Activity Sponsorship</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Sponsorship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see #isSetActivitySponsorship()
	 * @see #unsetActivitySponsorship()
	 * @see #setActivitySponsorship(ActivitySponsorshipType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ActivitySponsorship()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='activitySponsorship' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivitySponsorshipType getActivitySponsorship();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship <em>Activity Sponsorship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity Sponsorship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see #isSetActivitySponsorship()
	 * @see #unsetActivitySponsorship()
	 * @see #getActivitySponsorship()
	 * @generated
	 */
	void setActivitySponsorship(ActivitySponsorshipType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship <em>Activity Sponsorship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetActivitySponsorship()
	 * @see #getActivitySponsorship()
	 * @see #setActivitySponsorship(ActivitySponsorshipType)
	 * @generated
	 */
	void unsetActivitySponsorship();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship <em>Activity Sponsorship</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Activity Sponsorship</em>' attribute is set.
	 * @see #unsetActivitySponsorship()
	 * @see #getActivitySponsorship()
	 * @see #setActivitySponsorship(ActivitySponsorshipType)
	 * @generated
	 */
	boolean isSetActivitySponsorship();

	/**
	 * Returns the value of the '<em><b>Participation Modality</b></em>' attribute list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType}.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Participation Modality</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ParticipationModality()
	 * @model unique="false"
	 *        extendedMetaData="kind='element' name='participationModality' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ParticipationModalityType> getParticipationModality();

	/**
	 * Returns the value of the '<em><b>Activity Delivery</b></em>' attribute list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType}.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Delivery</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ActivityDelivery()
	 * @model unique="false"
	 *        extendedMetaData="kind='element' name='activityDelivery' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ActivityDeliveryType> getActivityDelivery();

	/**
	 * Returns the value of the '<em><b>Activity Format</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Format</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_ActivityFormat()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='activityFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LanguageString> getActivityFormat();

	/**
	 * Returns the value of the '<em><b>Off Label Use</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Off Label Use</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetOffLabelUse()
	 * @see #unsetOffLabelUse()
	 * @see #setOffLabelUse(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_OffLabelUse()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='offLabelUse' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getOffLabelUse();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Off Label Use</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetOffLabelUse()
	 * @see #unsetOffLabelUse()
	 * @see #getOffLabelUse()
	 * @generated
	 */
	void setOffLabelUse(YesNoType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOffLabelUse()
	 * @see #getOffLabelUse()
	 * @see #setOffLabelUse(YesNoType)
	 * @generated
	 */
	void unsetOffLabelUse();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse <em>Off Label Use</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Off Label Use</em>' attribute is set.
	 * @see #unsetOffLabelUse()
	 * @see #getOffLabelUse()
	 * @see #setOffLabelUse(YesNoType)
	 * @generated
	 */
	boolean isSetOffLabelUse();

	/**
	 * Returns the value of the '<em><b>Off Label Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Off Label Description</em>' containment reference.
	 * @see #setOffLabelDescription(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_OffLabelDescription()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='offLabelDescription' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getOffLabelDescription();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelDescription <em>Off Label Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Off Label Description</em>' containment reference.
	 * @see #getOffLabelDescription()
	 * @generated
	 */
	void setOffLabelDescription(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Commercial Support</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commercial Support</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetCommercialSupport()
	 * @see #unsetCommercialSupport()
	 * @see #setCommercialSupport(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_CommercialSupport()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='commercialSupport' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getCommercialSupport();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport <em>Commercial Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commercial Support</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetCommercialSupport()
	 * @see #unsetCommercialSupport()
	 * @see #getCommercialSupport()
	 * @generated
	 */
	void setCommercialSupport(YesNoType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport <em>Commercial Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCommercialSupport()
	 * @see #getCommercialSupport()
	 * @see #setCommercialSupport(YesNoType)
	 * @generated
	 */
	void unsetCommercialSupport();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport <em>Commercial Support</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Commercial Support</em>' attribute is set.
	 * @see #unsetCommercialSupport()
	 * @see #getCommercialSupport()
	 * @see #setCommercialSupport(YesNoType)
	 * @generated
	 */
	boolean isSetCommercialSupport();

	/**
	 * Returns the value of the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commercial Support Acknowledgement</em>' containment reference.
	 * @see #setCommercialSupportAcknowledgement(LanguageString)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_CommercialSupportAcknowledgement()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='commercialSupportAcknowledgement' namespace='##targetNamespace'"
	 * @generated
	 */
	LanguageString getCommercialSupportAcknowledgement();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commercial Support Acknowledgement</em>' containment reference.
	 * @see #getCommercialSupportAcknowledgement()
	 * @generated
	 */
	void setCommercialSupportAcknowledgement(LanguageString value);

	/**
	 * Returns the value of the '<em><b>Relevant Financial Relationship</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.louisiana.caai.lom.model.healthcare.YesNoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relevant Financial Relationship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetRelevantFinancialRelationship()
	 * @see #unsetRelevantFinancialRelationship()
	 * @see #setRelevantFinancialRelationship(YesNoType)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_RelevantFinancialRelationship()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='relevantFinancialRelationship' namespace='##targetNamespace'"
	 * @generated
	 */
	YesNoType getRelevantFinancialRelationship();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relevant Financial Relationship</em>' attribute.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see #isSetRelevantFinancialRelationship()
	 * @see #unsetRelevantFinancialRelationship()
	 * @see #getRelevantFinancialRelationship()
	 * @generated
	 */
	void setRelevantFinancialRelationship(YesNoType value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRelevantFinancialRelationship()
	 * @see #getRelevantFinancialRelationship()
	 * @see #setRelevantFinancialRelationship(YesNoType)
	 * @generated
	 */
	void unsetRelevantFinancialRelationship();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Relevant Financial Relationship</em>' attribute is set.
	 * @see #unsetRelevantFinancialRelationship()
	 * @see #getRelevantFinancialRelationship()
	 * @see #setRelevantFinancialRelationship(YesNoType)
	 * @generated
	 */
	boolean isSetRelevantFinancialRelationship();

	/**
	 * Returns the value of the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relevant Financial Relationship Disclosure</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_RelevantFinancialRelationshipDisclosure()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='relevantFinancialRelationshipDisclosure' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LanguageString> getRelevantFinancialRelationshipDisclosure();

	/**
	 * Returns the value of the '<em><b>Contact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' attribute.
	 * @see #setContact(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_Contact()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='contact' namespace='##targetNamespace'"
	 * @generated
	 */
	String getContact();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getContact <em>Contact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' attribute.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(String value);

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"healthcareEducation"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getHealthcareEducationType_UniqueElementName()
	 * @model default="healthcareEducation" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	String getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // HealthcareEducationType
