/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Education Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getCreditsAvailable <em>Credits Available</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getCredits <em>Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getTargetAudience <em>Target Audience</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getActivityLocation <em>Activity Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getStartDateTime <em>Start Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getEndDateTime <em>End Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getActivitySponsorship <em>Activity Sponsorship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getParticipationModality <em>Participation Modality</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getActivityDelivery <em>Activity Delivery</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getActivityFormat <em>Activity Format</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getOffLabelUse <em>Off Label Use</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getOffLabelDescription <em>Off Label Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getCommercialSupport <em>Commercial Support</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getContact <em>Contact</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HealthcareEducationTypeImpl extends EObjectImpl implements HealthcareEducationType {
	/**
	 * The default value of the '{@link #getExpirationDate() <em>Expiration Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpirationDate()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar EXPIRATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpirationDate() <em>Expiration Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpirationDate()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar expirationDate = EXPIRATION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreditsAvailable() <em>Credits Available</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditsAvailable()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType CREDITS_AVAILABLE_EDEFAULT = YesNoType.YES;

	/**
	 * The cached value of the '{@link #getCreditsAvailable() <em>Credits Available</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditsAvailable()
	 * @generated
	 * @ordered
	 */
	protected YesNoType creditsAvailable = CREDITS_AVAILABLE_EDEFAULT;

	/**
	 * This is true if the Credits Available attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean creditsAvailableESet;

	/**
	 * The cached value of the '{@link #getCredits() <em>Credits</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCredits()
	 * @generated
	 * @ordered
	 */
	protected EList<CreditsType> credits;

	/**
	 * The cached value of the '{@link #getTargetAudience() <em>Target Audience</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetAudience()
	 * @generated
	 * @ordered
	 */
	protected TargetAudienceType targetAudience;

	/**
	 * The cached value of the '{@link #getActivityLocation() <em>Activity Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityLocation()
	 * @generated
	 * @ordered
	 */
	protected ActivityLocationType activityLocation;

	/**
	 * The default value of the '{@link #getStartDateTime() <em>Start Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartDateTime()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar START_DATE_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartDateTime() <em>Start Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartDateTime()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar startDateTime = START_DATE_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndDateTime() <em>End Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDateTime()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar END_DATE_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndDateTime() <em>End Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDateTime()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar endDateTime = END_DATE_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getActivitySponsorship() <em>Activity Sponsorship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivitySponsorship()
	 * @generated
	 * @ordered
	 */
	protected static final ActivitySponsorshipType ACTIVITY_SPONSORSHIP_EDEFAULT = ActivitySponsorshipType.DIRECT;

	/**
	 * The cached value of the '{@link #getActivitySponsorship() <em>Activity Sponsorship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivitySponsorship()
	 * @generated
	 * @ordered
	 */
	protected ActivitySponsorshipType activitySponsorship = ACTIVITY_SPONSORSHIP_EDEFAULT;

	/**
	 * This is true if the Activity Sponsorship attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean activitySponsorshipESet;

	/**
	 * The cached value of the '{@link #getParticipationModality() <em>Participation Modality</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParticipationModality()
	 * @generated
	 * @ordered
	 */
	protected EList<ParticipationModalityType> participationModality;

	/**
	 * The cached value of the '{@link #getActivityDelivery() <em>Activity Delivery</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityDelivery()
	 * @generated
	 * @ordered
	 */
	protected EList<ActivityDeliveryType> activityDelivery;

	/**
	 * The cached value of the '{@link #getActivityFormat() <em>Activity Format</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityFormat()
	 * @generated
	 * @ordered
	 */
	protected EList<LanguageString> activityFormat;

	/**
	 * The default value of the '{@link #getOffLabelUse() <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffLabelUse()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType OFF_LABEL_USE_EDEFAULT = YesNoType.YES;

	/**
	 * The cached value of the '{@link #getOffLabelUse() <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffLabelUse()
	 * @generated
	 * @ordered
	 */
	protected YesNoType offLabelUse = OFF_LABEL_USE_EDEFAULT;

	/**
	 * This is true if the Off Label Use attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean offLabelUseESet;

	/**
	 * The cached value of the '{@link #getOffLabelDescription() <em>Off Label Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffLabelDescription()
	 * @generated
	 * @ordered
	 */
	protected LanguageString offLabelDescription;

	/**
	 * The default value of the '{@link #getCommercialSupport() <em>Commercial Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommercialSupport()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType COMMERCIAL_SUPPORT_EDEFAULT = YesNoType.YES;

	/**
	 * The cached value of the '{@link #getCommercialSupport() <em>Commercial Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommercialSupport()
	 * @generated
	 * @ordered
	 */
	protected YesNoType commercialSupport = COMMERCIAL_SUPPORT_EDEFAULT;

	/**
	 * This is true if the Commercial Support attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean commercialSupportESet;

	/**
	 * The cached value of the '{@link #getCommercialSupportAcknowledgement() <em>Commercial Support Acknowledgement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommercialSupportAcknowledgement()
	 * @generated
	 * @ordered
	 */
	protected LanguageString commercialSupportAcknowledgement;

	/**
	 * The default value of the '{@link #getRelevantFinancialRelationship() <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelevantFinancialRelationship()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT = YesNoType.YES;

	/**
	 * The cached value of the '{@link #getRelevantFinancialRelationship() <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelevantFinancialRelationship()
	 * @generated
	 * @ordered
	 */
	protected YesNoType relevantFinancialRelationship = RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT;

	/**
	 * This is true if the Relevant Financial Relationship attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean relevantFinancialRelationshipESet;

	/**
	 * The cached value of the '{@link #getRelevantFinancialRelationshipDisclosure() <em>Relevant Financial Relationship Disclosure</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelevantFinancialRelationshipDisclosure()
	 * @generated
	 * @ordered
	 */
	protected EList<LanguageString> relevantFinancialRelationshipDisclosure;

	/**
	 * The default value of the '{@link #getContact() <em>Contact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTACT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContact() <em>Contact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected String contact = CONTACT_EDEFAULT;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_ELEMENT_NAME_EDEFAULT = "healthcareEducation";

	/**
	 * The cached value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected String uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;

	/**
	 * This is true if the Unique Element Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean uniqueElementNameESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HealthcareEducationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.HEALTHCARE_EDUCATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getExpirationDate() {
		return expirationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpirationDate(XMLGregorianCalendar newExpirationDate) {
		XMLGregorianCalendar oldExpirationDate = expirationDate;
		expirationDate = newExpirationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE, oldExpirationDate, expirationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getCreditsAvailable() {
		return creditsAvailable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditsAvailable(YesNoType newCreditsAvailable) {
		YesNoType oldCreditsAvailable = creditsAvailable;
		creditsAvailable = newCreditsAvailable == null ? CREDITS_AVAILABLE_EDEFAULT : newCreditsAvailable;
		boolean oldCreditsAvailableESet = creditsAvailableESet;
		creditsAvailableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE, oldCreditsAvailable,
					creditsAvailable, !oldCreditsAvailableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetCreditsAvailable() {
		YesNoType oldCreditsAvailable = creditsAvailable;
		boolean oldCreditsAvailableESet = creditsAvailableESet;
		creditsAvailable = CREDITS_AVAILABLE_EDEFAULT;
		creditsAvailableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE, oldCreditsAvailable,
					CREDITS_AVAILABLE_EDEFAULT, oldCreditsAvailableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetCreditsAvailable() {
		return creditsAvailableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CreditsType> getCredits() {
		if (credits == null) {
			credits = new EObjectContainmentEList<CreditsType>(CreditsType.class, this,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS);
		}
		return credits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetAudienceType getTargetAudience() {
		return targetAudience;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetAudience(TargetAudienceType newTargetAudience, NotificationChain msgs) {
		TargetAudienceType oldTargetAudience = targetAudience;
		targetAudience = newTargetAudience;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE, oldTargetAudience, newTargetAudience);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetAudience(TargetAudienceType newTargetAudience) {
		if (newTargetAudience != targetAudience) {
			NotificationChain msgs = null;
			if (targetAudience != null)
				msgs = ((InternalEObject) targetAudience).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE, null,
						msgs);
			if (newTargetAudience != null)
				msgs = ((InternalEObject) newTargetAudience).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE, null,
						msgs);
			msgs = basicSetTargetAudience(newTargetAudience, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE, newTargetAudience,
					newTargetAudience));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivityLocationType getActivityLocation() {
		return activityLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivityLocation(ActivityLocationType newActivityLocation,
			NotificationChain msgs) {
		ActivityLocationType oldActivityLocation = activityLocation;
		activityLocation = newActivityLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION, oldActivityLocation,
					newActivityLocation);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityLocation(ActivityLocationType newActivityLocation) {
		if (newActivityLocation != activityLocation) {
			NotificationChain msgs = null;
			if (activityLocation != null)
				msgs = ((InternalEObject) activityLocation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION, null,
						msgs);
			if (newActivityLocation != null)
				msgs = ((InternalEObject) newActivityLocation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION, null,
						msgs);
			msgs = basicSetActivityLocation(newActivityLocation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION, newActivityLocation,
					newActivityLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getStartDateTime() {
		return startDateTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartDateTime(XMLGregorianCalendar newStartDateTime) {
		XMLGregorianCalendar oldStartDateTime = startDateTime;
		startDateTime = newStartDateTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME, oldStartDateTime, startDateTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getEndDateTime() {
		return endDateTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndDateTime(XMLGregorianCalendar newEndDateTime) {
		XMLGregorianCalendar oldEndDateTime = endDateTime;
		endDateTime = newEndDateTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME, oldEndDateTime, endDateTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivitySponsorshipType getActivitySponsorship() {
		return activitySponsorship;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivitySponsorship(ActivitySponsorshipType newActivitySponsorship) {
		ActivitySponsorshipType oldActivitySponsorship = activitySponsorship;
		activitySponsorship = newActivitySponsorship == null ? ACTIVITY_SPONSORSHIP_EDEFAULT : newActivitySponsorship;
		boolean oldActivitySponsorshipESet = activitySponsorshipESet;
		activitySponsorshipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP, oldActivitySponsorship,
					activitySponsorship, !oldActivitySponsorshipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetActivitySponsorship() {
		ActivitySponsorshipType oldActivitySponsorship = activitySponsorship;
		boolean oldActivitySponsorshipESet = activitySponsorshipESet;
		activitySponsorship = ACTIVITY_SPONSORSHIP_EDEFAULT;
		activitySponsorshipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP, oldActivitySponsorship,
					ACTIVITY_SPONSORSHIP_EDEFAULT, oldActivitySponsorshipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetActivitySponsorship() {
		return activitySponsorshipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParticipationModalityType> getParticipationModality() {
		if (participationModality == null) {
			participationModality = new EDataTypeEList<ParticipationModalityType>(ParticipationModalityType.class, this,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY);
		}
		return participationModality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ActivityDeliveryType> getActivityDelivery() {
		if (activityDelivery == null) {
			activityDelivery = new EDataTypeEList<ActivityDeliveryType>(ActivityDeliveryType.class, this,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY);
		}
		return activityDelivery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getActivityFormat() {
		if (activityFormat == null) {
			activityFormat = new EObjectContainmentEList<LanguageString>(LanguageString.class, this,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT);
		}
		return activityFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getOffLabelUse() {
		return offLabelUse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOffLabelUse(YesNoType newOffLabelUse) {
		YesNoType oldOffLabelUse = offLabelUse;
		offLabelUse = newOffLabelUse == null ? OFF_LABEL_USE_EDEFAULT : newOffLabelUse;
		boolean oldOffLabelUseESet = offLabelUseESet;
		offLabelUseESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE, oldOffLabelUse, offLabelUse,
					!oldOffLabelUseESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetOffLabelUse() {
		YesNoType oldOffLabelUse = offLabelUse;
		boolean oldOffLabelUseESet = offLabelUseESet;
		offLabelUse = OFF_LABEL_USE_EDEFAULT;
		offLabelUseESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE, oldOffLabelUse, OFF_LABEL_USE_EDEFAULT,
					oldOffLabelUseESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetOffLabelUse() {
		return offLabelUseESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getOffLabelDescription() {
		return offLabelDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOffLabelDescription(LanguageString newOffLabelDescription,
			NotificationChain msgs) {
		LanguageString oldOffLabelDescription = offLabelDescription;
		offLabelDescription = newOffLabelDescription;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION, oldOffLabelDescription,
					newOffLabelDescription);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOffLabelDescription(LanguageString newOffLabelDescription) {
		if (newOffLabelDescription != offLabelDescription) {
			NotificationChain msgs = null;
			if (offLabelDescription != null)
				msgs = ((InternalEObject) offLabelDescription).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION,
						null, msgs);
			if (newOffLabelDescription != null)
				msgs = ((InternalEObject) newOffLabelDescription).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION,
						null, msgs);
			msgs = basicSetOffLabelDescription(newOffLabelDescription, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION, newOffLabelDescription,
					newOffLabelDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getCommercialSupport() {
		return commercialSupport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommercialSupport(YesNoType newCommercialSupport) {
		YesNoType oldCommercialSupport = commercialSupport;
		commercialSupport = newCommercialSupport == null ? COMMERCIAL_SUPPORT_EDEFAULT : newCommercialSupport;
		boolean oldCommercialSupportESet = commercialSupportESet;
		commercialSupportESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT, oldCommercialSupport,
					commercialSupport, !oldCommercialSupportESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetCommercialSupport() {
		YesNoType oldCommercialSupport = commercialSupport;
		boolean oldCommercialSupportESet = commercialSupportESet;
		commercialSupport = COMMERCIAL_SUPPORT_EDEFAULT;
		commercialSupportESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT, oldCommercialSupport,
					COMMERCIAL_SUPPORT_EDEFAULT, oldCommercialSupportESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetCommercialSupport() {
		return commercialSupportESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getCommercialSupportAcknowledgement() {
		return commercialSupportAcknowledgement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommercialSupportAcknowledgement(
			LanguageString newCommercialSupportAcknowledgement, NotificationChain msgs) {
		LanguageString oldCommercialSupportAcknowledgement = commercialSupportAcknowledgement;
		commercialSupportAcknowledgement = newCommercialSupportAcknowledgement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
					oldCommercialSupportAcknowledgement, newCommercialSupportAcknowledgement);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommercialSupportAcknowledgement(LanguageString newCommercialSupportAcknowledgement) {
		if (newCommercialSupportAcknowledgement != commercialSupportAcknowledgement) {
			NotificationChain msgs = null;
			if (commercialSupportAcknowledgement != null)
				msgs = ((InternalEObject) commercialSupportAcknowledgement).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
						null, msgs);
			if (newCommercialSupportAcknowledgement != null)
				msgs = ((InternalEObject) newCommercialSupportAcknowledgement).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
						null, msgs);
			msgs = basicSetCommercialSupportAcknowledgement(newCommercialSupportAcknowledgement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
					newCommercialSupportAcknowledgement, newCommercialSupportAcknowledgement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getRelevantFinancialRelationship() {
		return relevantFinancialRelationship;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelevantFinancialRelationship(YesNoType newRelevantFinancialRelationship) {
		YesNoType oldRelevantFinancialRelationship = relevantFinancialRelationship;
		relevantFinancialRelationship = newRelevantFinancialRelationship == null
				? RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT
				: newRelevantFinancialRelationship;
		boolean oldRelevantFinancialRelationshipESet = relevantFinancialRelationshipESet;
		relevantFinancialRelationshipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP,
					oldRelevantFinancialRelationship, relevantFinancialRelationship,
					!oldRelevantFinancialRelationshipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetRelevantFinancialRelationship() {
		YesNoType oldRelevantFinancialRelationship = relevantFinancialRelationship;
		boolean oldRelevantFinancialRelationshipESet = relevantFinancialRelationshipESet;
		relevantFinancialRelationship = RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT;
		relevantFinancialRelationshipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP,
					oldRelevantFinancialRelationship, RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT,
					oldRelevantFinancialRelationshipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetRelevantFinancialRelationship() {
		return relevantFinancialRelationshipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getRelevantFinancialRelationshipDisclosure() {
		if (relevantFinancialRelationshipDisclosure == null) {
			relevantFinancialRelationshipDisclosure = new EObjectContainmentEList<LanguageString>(LanguageString.class,
					this, HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE);
		}
		return relevantFinancialRelationshipDisclosure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContact() {
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContact(String newContact) {
		String oldContact = contact;
		contact = newContact;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CONTACT,
					oldContact, contact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUniqueElementName() {
		return uniqueElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUniqueElementName(String newUniqueElementName) {
		String oldUniqueElementName = uniqueElementName;
		uniqueElementName = newUniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					uniqueElementName, !oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUniqueElementName() {
		String oldUniqueElementName = uniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;
		uniqueElementNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					UNIQUE_ELEMENT_NAME_EDEFAULT, oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUniqueElementName() {
		return uniqueElementNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS:
			return ((InternalEList<?>) getCredits()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE:
			return basicSetTargetAudience(null, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION:
			return basicSetActivityLocation(null, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT:
			return ((InternalEList<?>) getActivityFormat()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION:
			return basicSetOffLabelDescription(null, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return basicSetCommercialSupportAcknowledgement(null, msgs);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return ((InternalEList<?>) getRelevantFinancialRelationshipDisclosure()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE:
			return getExpirationDate();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE:
			return getCreditsAvailable();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS:
			return getCredits();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE:
			return getTargetAudience();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION:
			return getActivityLocation();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME:
			return getStartDateTime();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME:
			return getEndDateTime();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP:
			return getActivitySponsorship();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY:
			return getParticipationModality();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY:
			return getActivityDelivery();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT:
			return getActivityFormat();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE:
			return getOffLabelUse();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION:
			return getOffLabelDescription();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT:
			return getCommercialSupport();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return getCommercialSupportAcknowledgement();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP:
			return getRelevantFinancialRelationship();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return getRelevantFinancialRelationshipDisclosure();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CONTACT:
			return getContact();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE:
			setExpirationDate((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE:
			setCreditsAvailable((YesNoType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS:
			getCredits().clear();
			getCredits().addAll((Collection<? extends CreditsType>) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE:
			setTargetAudience((TargetAudienceType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION:
			setActivityLocation((ActivityLocationType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME:
			setStartDateTime((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME:
			setEndDateTime((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP:
			setActivitySponsorship((ActivitySponsorshipType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY:
			getParticipationModality().clear();
			getParticipationModality().addAll((Collection<? extends ParticipationModalityType>) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY:
			getActivityDelivery().clear();
			getActivityDelivery().addAll((Collection<? extends ActivityDeliveryType>) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT:
			getActivityFormat().clear();
			getActivityFormat().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE:
			setOffLabelUse((YesNoType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION:
			setOffLabelDescription((LanguageString) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT:
			setCommercialSupport((YesNoType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			setCommercialSupportAcknowledgement((LanguageString) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP:
			setRelevantFinancialRelationship((YesNoType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			getRelevantFinancialRelationshipDisclosure().clear();
			getRelevantFinancialRelationshipDisclosure().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CONTACT:
			setContact((String) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME:
			setUniqueElementName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE:
			setExpirationDate(EXPIRATION_DATE_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE:
			unsetCreditsAvailable();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS:
			getCredits().clear();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE:
			setTargetAudience((TargetAudienceType) null);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION:
			setActivityLocation((ActivityLocationType) null);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME:
			setStartDateTime(START_DATE_TIME_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME:
			setEndDateTime(END_DATE_TIME_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP:
			unsetActivitySponsorship();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY:
			getParticipationModality().clear();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY:
			getActivityDelivery().clear();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT:
			getActivityFormat().clear();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE:
			unsetOffLabelUse();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION:
			setOffLabelDescription((LanguageString) null);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT:
			unsetCommercialSupport();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			setCommercialSupportAcknowledgement((LanguageString) null);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP:
			unsetRelevantFinancialRelationship();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			getRelevantFinancialRelationshipDisclosure().clear();
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CONTACT:
			setContact(CONTACT_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE:
			return EXPIRATION_DATE_EDEFAULT == null ? expirationDate != null
					: !EXPIRATION_DATE_EDEFAULT.equals(expirationDate);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE:
			return isSetCreditsAvailable();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CREDITS:
			return credits != null && !credits.isEmpty();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE:
			return targetAudience != null;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION:
			return activityLocation != null;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME:
			return START_DATE_TIME_EDEFAULT == null ? startDateTime != null
					: !START_DATE_TIME_EDEFAULT.equals(startDateTime);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME:
			return END_DATE_TIME_EDEFAULT == null ? endDateTime != null : !END_DATE_TIME_EDEFAULT.equals(endDateTime);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP:
			return isSetActivitySponsorship();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY:
			return participationModality != null && !participationModality.isEmpty();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY:
			return activityDelivery != null && !activityDelivery.isEmpty();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT:
			return activityFormat != null && !activityFormat.isEmpty();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE:
			return isSetOffLabelUse();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION:
			return offLabelDescription != null;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT:
			return isSetCommercialSupport();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return commercialSupportAcknowledgement != null;
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP:
			return isSetRelevantFinancialRelationship();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return relevantFinancialRelationshipDisclosure != null
					&& !relevantFinancialRelationshipDisclosure.isEmpty();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__CONTACT:
			return CONTACT_EDEFAULT == null ? contact != null : !CONTACT_EDEFAULT.equals(contact);
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (expirationDate: ");
		result.append(expirationDate);
		result.append(", creditsAvailable: ");
		if (creditsAvailableESet)
			result.append(creditsAvailable);
		else
			result.append("<unset>");
		result.append(", startDateTime: ");
		result.append(startDateTime);
		result.append(", endDateTime: ");
		result.append(endDateTime);
		result.append(", activitySponsorship: ");
		if (activitySponsorshipESet)
			result.append(activitySponsorship);
		else
			result.append("<unset>");
		result.append(", participationModality: ");
		result.append(participationModality);
		result.append(", activityDelivery: ");
		result.append(activityDelivery);
		result.append(", offLabelUse: ");
		if (offLabelUseESet)
			result.append(offLabelUse);
		else
			result.append("<unset>");
		result.append(", commercialSupport: ");
		if (commercialSupportESet)
			result.append(commercialSupport);
		else
			result.append("<unset>");
		result.append(", relevantFinancialRelationship: ");
		if (relevantFinancialRelationshipESet)
			result.append(relevantFinancialRelationship);
		else
			result.append("<unset>");
		result.append(", contact: ");
		result.append(contact);
		result.append(", uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //HealthcareEducationTypeImpl
