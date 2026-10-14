/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.CreditTypeType;
import edu.louisiana.caai.lom.model.healthcare.CreditUnitType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.PacingType;

import java.math.BigDecimal;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Credits Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getAccreditingBody <em>Accrediting Body</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getActivityCertification <em>Activity Certification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getCreditType <em>Credit Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getCreditUnit <em>Credit Unit</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getPacing <em>Pacing</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getAccreditedProvider <em>Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getNonAccreditedProvider <em>Non Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl#getNumberOfCredits <em>Number Of Credits</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreditsTypeImpl extends EObjectImpl implements CreditsType {
	/**
	 * The default value of the '{@link #getAccreditingBody() <em>Accrediting Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccreditingBody()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCREDITING_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccreditingBody() <em>Accrediting Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccreditingBody()
	 * @generated
	 * @ordered
	 */
	protected String accreditingBody = ACCREDITING_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getActivityCertification() <em>Activity Certification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityCertification()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTIVITY_CERTIFICATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActivityCertification() <em>Activity Certification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityCertification()
	 * @generated
	 * @ordered
	 */
	protected String activityCertification = ACTIVITY_CERTIFICATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreditType() <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditType()
	 * @generated
	 * @ordered
	 */
	protected static final CreditTypeType CREDIT_TYPE_EDEFAULT = CreditTypeType.CME;

	/**
	 * The cached value of the '{@link #getCreditType() <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditType()
	 * @generated
	 * @ordered
	 */
	protected CreditTypeType creditType = CREDIT_TYPE_EDEFAULT;

	/**
	 * This is true if the Credit Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean creditTypeESet;

	/**
	 * The default value of the '{@link #getCreditUnit() <em>Credit Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditUnit()
	 * @generated
	 * @ordered
	 */
	protected static final CreditUnitType CREDIT_UNIT_EDEFAULT = CreditUnitType.CECH;

	/**
	 * The cached value of the '{@link #getCreditUnit() <em>Credit Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditUnit()
	 * @generated
	 * @ordered
	 */
	protected CreditUnitType creditUnit = CREDIT_UNIT_EDEFAULT;

	/**
	 * This is true if the Credit Unit attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean creditUnitESet;

	/**
	 * The default value of the '{@link #getPacing() <em>Pacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPacing()
	 * @generated
	 * @ordered
	 */
	protected static final PacingType PACING_EDEFAULT = PacingType.LEARNER_PACED;

	/**
	 * The cached value of the '{@link #getPacing() <em>Pacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPacing()
	 * @generated
	 * @ordered
	 */
	protected PacingType pacing = PACING_EDEFAULT;

	/**
	 * This is true if the Pacing attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean pacingESet;

	/**
	 * The default value of the '{@link #getAccreditedProvider() <em>Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccreditedProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCREDITED_PROVIDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccreditedProvider() <em>Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccreditedProvider()
	 * @generated
	 * @ordered
	 */
	protected String accreditedProvider = ACCREDITED_PROVIDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNonAccreditedProvider() <em>Non Accredited Provider</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonAccreditedProvider()
	 * @generated
	 * @ordered
	 */
	protected EList<String> nonAccreditedProvider;

	/**
	 * The default value of the '{@link #getReleaseDate() <em>Release Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReleaseDate()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar RELEASE_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReleaseDate() <em>Release Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReleaseDate()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar releaseDate = RELEASE_DATE_EDEFAULT;

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
	 * The default value of the '{@link #getNumberOfCredits() <em>Number Of Credits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfCredits()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal NUMBER_OF_CREDITS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumberOfCredits() <em>Number Of Credits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfCredits()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal numberOfCredits = NUMBER_OF_CREDITS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreditsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.CREDITS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAccreditingBody() {
		return accreditingBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAccreditingBody(String newAccreditingBody) {
		String oldAccreditingBody = accreditingBody;
		accreditingBody = newAccreditingBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__ACCREDITING_BODY,
					oldAccreditingBody, accreditingBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getActivityCertification() {
		return activityCertification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityCertification(String newActivityCertification) {
		String oldActivityCertification = activityCertification;
		activityCertification = newActivityCertification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.CREDITS_TYPE__ACTIVITY_CERTIFICATION, oldActivityCertification,
					activityCertification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditTypeType getCreditType() {
		return creditType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditType(CreditTypeType newCreditType) {
		CreditTypeType oldCreditType = creditType;
		creditType = newCreditType == null ? CREDIT_TYPE_EDEFAULT : newCreditType;
		boolean oldCreditTypeESet = creditTypeESet;
		creditTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE,
					oldCreditType, creditType, !oldCreditTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetCreditType() {
		CreditTypeType oldCreditType = creditType;
		boolean oldCreditTypeESet = creditTypeESet;
		creditType = CREDIT_TYPE_EDEFAULT;
		creditTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE,
					oldCreditType, CREDIT_TYPE_EDEFAULT, oldCreditTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetCreditType() {
		return creditTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditUnitType getCreditUnit() {
		return creditUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditUnit(CreditUnitType newCreditUnit) {
		CreditUnitType oldCreditUnit = creditUnit;
		creditUnit = newCreditUnit == null ? CREDIT_UNIT_EDEFAULT : newCreditUnit;
		boolean oldCreditUnitESet = creditUnitESet;
		creditUnitESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT,
					oldCreditUnit, creditUnit, !oldCreditUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetCreditUnit() {
		CreditUnitType oldCreditUnit = creditUnit;
		boolean oldCreditUnitESet = creditUnitESet;
		creditUnit = CREDIT_UNIT_EDEFAULT;
		creditUnitESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT,
					oldCreditUnit, CREDIT_UNIT_EDEFAULT, oldCreditUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetCreditUnit() {
		return creditUnitESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PacingType getPacing() {
		return pacing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPacing(PacingType newPacing) {
		PacingType oldPacing = pacing;
		pacing = newPacing == null ? PACING_EDEFAULT : newPacing;
		boolean oldPacingESet = pacingESet;
		pacingESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__PACING, oldPacing,
					pacing, !oldPacingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetPacing() {
		PacingType oldPacing = pacing;
		boolean oldPacingESet = pacingESet;
		pacing = PACING_EDEFAULT;
		pacingESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, HealthcarePackage.CREDITS_TYPE__PACING, oldPacing,
					PACING_EDEFAULT, oldPacingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetPacing() {
		return pacingESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAccreditedProvider() {
		return accreditedProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAccreditedProvider(String newAccreditedProvider) {
		String oldAccreditedProvider = accreditedProvider;
		accreditedProvider = newAccreditedProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__ACCREDITED_PROVIDER,
					oldAccreditedProvider, accreditedProvider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getNonAccreditedProvider() {
		if (nonAccreditedProvider == null) {
			nonAccreditedProvider = new EDataTypeEList<String>(String.class, this,
					HealthcarePackage.CREDITS_TYPE__NON_ACCREDITED_PROVIDER);
		}
		return nonAccreditedProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getReleaseDate() {
		return releaseDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReleaseDate(XMLGregorianCalendar newReleaseDate) {
		XMLGregorianCalendar oldReleaseDate = releaseDate;
		releaseDate = newReleaseDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__RELEASE_DATE,
					oldReleaseDate, releaseDate));
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
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__EXPIRATION_DATE,
					oldExpirationDate, expirationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getNumberOfCredits() {
		return numberOfCredits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfCredits(BigDecimal newNumberOfCredits) {
		BigDecimal oldNumberOfCredits = numberOfCredits;
		numberOfCredits = newNumberOfCredits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.CREDITS_TYPE__NUMBER_OF_CREDITS,
					oldNumberOfCredits, numberOfCredits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case HealthcarePackage.CREDITS_TYPE__ACCREDITING_BODY:
			return getAccreditingBody();
		case HealthcarePackage.CREDITS_TYPE__ACTIVITY_CERTIFICATION:
			return getActivityCertification();
		case HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE:
			return getCreditType();
		case HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT:
			return getCreditUnit();
		case HealthcarePackage.CREDITS_TYPE__PACING:
			return getPacing();
		case HealthcarePackage.CREDITS_TYPE__ACCREDITED_PROVIDER:
			return getAccreditedProvider();
		case HealthcarePackage.CREDITS_TYPE__NON_ACCREDITED_PROVIDER:
			return getNonAccreditedProvider();
		case HealthcarePackage.CREDITS_TYPE__RELEASE_DATE:
			return getReleaseDate();
		case HealthcarePackage.CREDITS_TYPE__EXPIRATION_DATE:
			return getExpirationDate();
		case HealthcarePackage.CREDITS_TYPE__NUMBER_OF_CREDITS:
			return getNumberOfCredits();
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
		case HealthcarePackage.CREDITS_TYPE__ACCREDITING_BODY:
			setAccreditingBody((String) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__ACTIVITY_CERTIFICATION:
			setActivityCertification((String) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE:
			setCreditType((CreditTypeType) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT:
			setCreditUnit((CreditUnitType) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__PACING:
			setPacing((PacingType) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__ACCREDITED_PROVIDER:
			setAccreditedProvider((String) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__NON_ACCREDITED_PROVIDER:
			getNonAccreditedProvider().clear();
			getNonAccreditedProvider().addAll((Collection<? extends String>) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__RELEASE_DATE:
			setReleaseDate((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__EXPIRATION_DATE:
			setExpirationDate((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.CREDITS_TYPE__NUMBER_OF_CREDITS:
			setNumberOfCredits((BigDecimal) newValue);
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
		case HealthcarePackage.CREDITS_TYPE__ACCREDITING_BODY:
			setAccreditingBody(ACCREDITING_BODY_EDEFAULT);
			return;
		case HealthcarePackage.CREDITS_TYPE__ACTIVITY_CERTIFICATION:
			setActivityCertification(ACTIVITY_CERTIFICATION_EDEFAULT);
			return;
		case HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE:
			unsetCreditType();
			return;
		case HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT:
			unsetCreditUnit();
			return;
		case HealthcarePackage.CREDITS_TYPE__PACING:
			unsetPacing();
			return;
		case HealthcarePackage.CREDITS_TYPE__ACCREDITED_PROVIDER:
			setAccreditedProvider(ACCREDITED_PROVIDER_EDEFAULT);
			return;
		case HealthcarePackage.CREDITS_TYPE__NON_ACCREDITED_PROVIDER:
			getNonAccreditedProvider().clear();
			return;
		case HealthcarePackage.CREDITS_TYPE__RELEASE_DATE:
			setReleaseDate(RELEASE_DATE_EDEFAULT);
			return;
		case HealthcarePackage.CREDITS_TYPE__EXPIRATION_DATE:
			setExpirationDate(EXPIRATION_DATE_EDEFAULT);
			return;
		case HealthcarePackage.CREDITS_TYPE__NUMBER_OF_CREDITS:
			setNumberOfCredits(NUMBER_OF_CREDITS_EDEFAULT);
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
		case HealthcarePackage.CREDITS_TYPE__ACCREDITING_BODY:
			return ACCREDITING_BODY_EDEFAULT == null ? accreditingBody != null
					: !ACCREDITING_BODY_EDEFAULT.equals(accreditingBody);
		case HealthcarePackage.CREDITS_TYPE__ACTIVITY_CERTIFICATION:
			return ACTIVITY_CERTIFICATION_EDEFAULT == null ? activityCertification != null
					: !ACTIVITY_CERTIFICATION_EDEFAULT.equals(activityCertification);
		case HealthcarePackage.CREDITS_TYPE__CREDIT_TYPE:
			return isSetCreditType();
		case HealthcarePackage.CREDITS_TYPE__CREDIT_UNIT:
			return isSetCreditUnit();
		case HealthcarePackage.CREDITS_TYPE__PACING:
			return isSetPacing();
		case HealthcarePackage.CREDITS_TYPE__ACCREDITED_PROVIDER:
			return ACCREDITED_PROVIDER_EDEFAULT == null ? accreditedProvider != null
					: !ACCREDITED_PROVIDER_EDEFAULT.equals(accreditedProvider);
		case HealthcarePackage.CREDITS_TYPE__NON_ACCREDITED_PROVIDER:
			return nonAccreditedProvider != null && !nonAccreditedProvider.isEmpty();
		case HealthcarePackage.CREDITS_TYPE__RELEASE_DATE:
			return RELEASE_DATE_EDEFAULT == null ? releaseDate != null : !RELEASE_DATE_EDEFAULT.equals(releaseDate);
		case HealthcarePackage.CREDITS_TYPE__EXPIRATION_DATE:
			return EXPIRATION_DATE_EDEFAULT == null ? expirationDate != null
					: !EXPIRATION_DATE_EDEFAULT.equals(expirationDate);
		case HealthcarePackage.CREDITS_TYPE__NUMBER_OF_CREDITS:
			return NUMBER_OF_CREDITS_EDEFAULT == null ? numberOfCredits != null
					: !NUMBER_OF_CREDITS_EDEFAULT.equals(numberOfCredits);
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
		result.append(" (accreditingBody: ");
		result.append(accreditingBody);
		result.append(", activityCertification: ");
		result.append(activityCertification);
		result.append(", creditType: ");
		if (creditTypeESet)
			result.append(creditType);
		else
			result.append("<unset>");
		result.append(", creditUnit: ");
		if (creditUnitESet)
			result.append(creditUnit);
		else
			result.append("<unset>");
		result.append(", pacing: ");
		if (pacingESet)
			result.append(pacing);
		else
			result.append("<unset>");
		result.append(", accreditedProvider: ");
		result.append(accreditedProvider);
		result.append(", nonAccreditedProvider: ");
		result.append(nonAccreditedProvider);
		result.append(", releaseDate: ");
		result.append(releaseDate);
		result.append(", expirationDate: ");
		result.append(expirationDate);
		result.append(", numberOfCredits: ");
		result.append(numberOfCredits);
		result.append(')');
		return result.toString();
	}

} //CreditsTypeImpl
