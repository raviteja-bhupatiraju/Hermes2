/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType;
import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.CreditTypeType;
import edu.louisiana.caai.lom.model.healthcare.CreditUnitType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.CustomElementsType;
import edu.louisiana.caai.lom.model.healthcare.DocumentRoot;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType;
import edu.louisiana.caai.lom.model.healthcare.OrientationType;
import edu.louisiana.caai.lom.model.healthcare.PacingType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getAccreditedProvider <em>Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getAccreditingBody <em>Accrediting Body</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getActivityCertification <em>Activity Certification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getActivityDelivery <em>Activity Delivery</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getActivityFormat <em>Activity Format</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getActivityLocation <em>Activity Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getActivitySponsorship <em>Activity Sponsorship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getAnnotated <em>Annotated</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getAudienceCategory <em>Audience Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getBuilding <em>Building</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getClinicalHistory <em>Clinical History</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCommercialSupport <em>Commercial Support</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getContact <em>Contact</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCredits <em>Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCreditsAvailable <em>Credits Available</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCreditType <em>Credit Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCreditUnit <em>Credit Unit</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getCustomElements <em>Custom Elements</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getEndDateTime <em>End Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getExpirationDate <em>Expiration Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getFileHeight <em>File Height</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getFileWidth <em>File Width</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getHealthcareAsset <em>Healthcare Asset</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getHealthcareEducation <em>Healthcare Education</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getHealthcareMetadata <em>Healthcare Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getMagnification <em>Magnification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getMedicalImageType <em>Medical Image Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getNonAccreditedProvider <em>Non Accredited Provider</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getNumberOfCredits <em>Number Of Credits</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getOffLabelDescription <em>Off Label Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getOffLabelUse <em>Off Label Use</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getPacing <em>Pacing</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getParticipationModality <em>Participation Modality</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getProfession <em>Profession</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getReadingLevel <em>Reading Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getRoom <em>Room</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getSpecialty <em>Specialty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getSpecimenType <em>Specimen Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getStartDateTime <em>Start Date Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl#getTargetAudience <em>Target Audience</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

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
	 * The default value of the '{@link #getAccreditingBody() <em>Accrediting Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccreditingBody()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCREDITING_BODY_EDEFAULT = null;

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
	 * The default value of the '{@link #getActivityDelivery() <em>Activity Delivery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityDelivery()
	 * @generated
	 * @ordered
	 */
	protected static final ActivityDeliveryType ACTIVITY_DELIVERY_EDEFAULT = ActivityDeliveryType.LIVE;

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
	 * The default value of the '{@link #getAnnotated() <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotated()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType ANNOTATED_EDEFAULT = YesNoType.YES;

	/**
	 * The default value of the '{@link #getAudienceCategory() <em>Audience Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAudienceCategory()
	 * @generated
	 * @ordered
	 */
	protected static final AudienceCategoryType AUDIENCE_CATEGORY_EDEFAULT = AudienceCategoryType.GENERAL;

	/**
	 * The default value of the '{@link #getBuilding() <em>Building</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuilding()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILDING_EDEFAULT = null;

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
	 * The default value of the '{@link #getContact() <em>Contact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTACT_EDEFAULT = null;

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
	 * The default value of the '{@link #getCreditType() <em>Credit Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditType()
	 * @generated
	 * @ordered
	 */
	protected static final CreditTypeType CREDIT_TYPE_EDEFAULT = CreditTypeType.CME;

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
	 * The default value of the '{@link #getEndDateTime() <em>End Date Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDateTime()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar END_DATE_TIME_EDEFAULT = null;

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
	 * The default value of the '{@link #getFileHeight() <em>File Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileHeight()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger FILE_HEIGHT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFileWidth() <em>File Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileWidth()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger FILE_WIDTH_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getMagnification() <em>Magnification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMagnification()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger MAGNIFICATION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getMedicalImageType() <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMedicalImageType()
	 * @generated
	 * @ordered
	 */
	protected static final MedicalImageTypeType MEDICAL_IMAGE_TYPE_EDEFAULT = MedicalImageTypeType.ANGIOGRAM;

	/**
	 * The default value of the '{@link #getNonAccreditedProvider() <em>Non Accredited Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonAccreditedProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String NON_ACCREDITED_PROVIDER_EDEFAULT = null;

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
	 * The default value of the '{@link #getOffLabelUse() <em>Off Label Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffLabelUse()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType OFF_LABEL_USE_EDEFAULT = YesNoType.YES;

	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final OrientationType ORIENTATION_EDEFAULT = OrientationType.AXIAL;

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
	 * The default value of the '{@link #getParticipationModality() <em>Participation Modality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParticipationModality()
	 * @generated
	 * @ordered
	 */
	protected static final ParticipationModalityType PARTICIPATION_MODALITY_EDEFAULT = ParticipationModalityType.CONFERENCE_WORKSHOP;

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
	 * The default value of the '{@link #getRelevantFinancialRelationship() <em>Relevant Financial Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelevantFinancialRelationship()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT = YesNoType.YES;

	/**
	 * The default value of the '{@link #getRoom() <em>Room</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoom()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOM_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecimenType() <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecimenType()
	 * @generated
	 * @ordered
	 */
	protected static final SpecimenTypeType SPECIMEN_TYPE_EDEFAULT = SpecimenTypeType.CELL;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, HealthcarePackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String, String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
					EStringToStringMapEntryImpl.class, this, HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String, String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
					EStringToStringMapEntryImpl.class, this, HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAccreditedProvider() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACCREDITED_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAccreditedProvider(String newAccreditedProvider) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACCREDITED_PROVIDER,
				newAccreditedProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAccreditingBody() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACCREDITING_BODY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAccreditingBody(String newAccreditingBody) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACCREDITING_BODY,
				newAccreditingBody);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getActivityCertification() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityCertification(String newActivityCertification) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION,
				newActivityCertification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivityDeliveryType getActivityDelivery() {
		return (ActivityDeliveryType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_DELIVERY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityDelivery(ActivityDeliveryType newActivityDelivery) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_DELIVERY,
				newActivityDelivery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getActivityFormat() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_FORMAT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivityFormat(LanguageString newActivityFormat, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_FORMAT,
				newActivityFormat, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityFormat(LanguageString newActivityFormat) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_FORMAT,
				newActivityFormat);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivityLocationType getActivityLocation() {
		return (ActivityLocationType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivityLocation(ActivityLocationType newActivityLocation,
			NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_LOCATION,
				newActivityLocation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityLocation(ActivityLocationType newActivityLocation) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_LOCATION,
				newActivityLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivitySponsorshipType getActivitySponsorship() {
		return (ActivitySponsorshipType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP,
				true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivitySponsorship(ActivitySponsorshipType newActivitySponsorship) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP,
				newActivitySponsorship);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getAnnotated() {
		return (YesNoType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ANNOTATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnnotated(YesNoType newAnnotated) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ANNOTATED, newAnnotated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AudienceCategoryType getAudienceCategory() {
		return (AudienceCategoryType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__AUDIENCE_CATEGORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAudienceCategory(AudienceCategoryType newAudienceCategory) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__AUDIENCE_CATEGORY,
				newAudienceCategory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBuilding() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__BUILDING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuilding(String newBuilding) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__BUILDING, newBuilding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getClinicalHistory() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CLINICAL_HISTORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClinicalHistory(LanguageString newClinicalHistory, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__CLINICAL_HISTORY,
				newClinicalHistory, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClinicalHistory(LanguageString newClinicalHistory) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CLINICAL_HISTORY,
				newClinicalHistory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getCommercialSupport() {
		return (YesNoType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__COMMERCIAL_SUPPORT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommercialSupport(YesNoType newCommercialSupport) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__COMMERCIAL_SUPPORT,
				newCommercialSupport);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getCommercialSupportAcknowledgement() {
		return (LanguageString) getMixed()
				.get(HealthcarePackage.Literals.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommercialSupportAcknowledgement(
			LanguageString newCommercialSupportAcknowledgement, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(
				HealthcarePackage.Literals.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
				newCommercialSupportAcknowledgement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommercialSupportAcknowledgement(LanguageString newCommercialSupportAcknowledgement) {
		((FeatureMap.Internal) getMixed()).set(
				HealthcarePackage.Literals.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT,
				newCommercialSupportAcknowledgement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContact() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CONTACT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContact(String newContact) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CONTACT, newContact);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditsType getCredits() {
		return (CreditsType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCredits(CreditsType newCredits, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDITS,
				newCredits, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCredits(CreditsType newCredits) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDITS, newCredits);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getCreditsAvailable() {
		return (YesNoType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDITS_AVAILABLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditsAvailable(YesNoType newCreditsAvailable) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDITS_AVAILABLE,
				newCreditsAvailable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditTypeType getCreditType() {
		return (CreditTypeType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDIT_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditType(CreditTypeType newCreditType) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDIT_TYPE, newCreditType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditUnitType getCreditUnit() {
		return (CreditUnitType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDIT_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreditUnit(CreditUnitType newCreditUnit) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CREDIT_UNIT, newCreditUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomElementsType getCustomElements() {
		return (CustomElementsType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__CUSTOM_ELEMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomElements(CustomElementsType newCustomElements, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__CUSTOM_ELEMENTS,
				newCustomElements, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCustomElements(CustomElementsType newCustomElements) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__CUSTOM_ELEMENTS,
				newCustomElements);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getEndDateTime() {
		return (XMLGregorianCalendar) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__END_DATE_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndDateTime(XMLGregorianCalendar newEndDateTime) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__END_DATE_TIME, newEndDateTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getExpirationDate() {
		return (XMLGregorianCalendar) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__EXPIRATION_DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpirationDate(XMLGregorianCalendar newExpirationDate) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__EXPIRATION_DATE,
				newExpirationDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getFileHeight() {
		return (BigInteger) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__FILE_HEIGHT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileHeight(BigInteger newFileHeight) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__FILE_HEIGHT, newFileHeight);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getFileWidth() {
		return (BigInteger) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__FILE_WIDTH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileWidth(BigInteger newFileWidth) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__FILE_WIDTH, newFileWidth);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareAssetType getHealthcareAsset() {
		return (HealthcareAssetType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_ASSET, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHealthcareAsset(HealthcareAssetType newHealthcareAsset, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_ASSET,
				newHealthcareAsset, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthcareAsset(HealthcareAssetType newHealthcareAsset) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_ASSET,
				newHealthcareAsset);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareEducationType getHealthcareEducation() {
		return (HealthcareEducationType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_EDUCATION,
				true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHealthcareEducation(HealthcareEducationType newHealthcareEducation,
			NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed())
				.basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_EDUCATION, newHealthcareEducation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthcareEducation(HealthcareEducationType newHealthcareEducation) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_EDUCATION,
				newHealthcareEducation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareMetadataType getHealthcareMetadata() {
		return (HealthcareMetadataType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_METADATA,
				true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHealthcareMetadata(HealthcareMetadataType newHealthcareMetadata,
			NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed())
				.basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_METADATA, newHealthcareMetadata, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthcareMetadata(HealthcareMetadataType newHealthcareMetadata) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__HEALTHCARE_METADATA,
				newHealthcareMetadata);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getMagnification() {
		return (BigInteger) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__MAGNIFICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMagnification(BigInteger newMagnification) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__MAGNIFICATION,
				newMagnification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MedicalImageTypeType getMedicalImageType() {
		return (MedicalImageTypeType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE,
				true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMedicalImageType(MedicalImageTypeType newMedicalImageType) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE,
				newMedicalImageType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNonAccreditedProvider() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNonAccreditedProvider(String newNonAccreditedProvider) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER,
				newNonAccreditedProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getNumberOfCredits() {
		return (BigDecimal) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__NUMBER_OF_CREDITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfCredits(BigDecimal newNumberOfCredits) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__NUMBER_OF_CREDITS,
				newNumberOfCredits);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getOffLabelDescription() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOffLabelDescription(LanguageString newOffLabelDescription,
			NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(
				HealthcarePackage.Literals.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION, newOffLabelDescription, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOffLabelDescription(LanguageString newOffLabelDescription) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION,
				newOffLabelDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getOffLabelUse() {
		return (YesNoType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__OFF_LABEL_USE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOffLabelUse(YesNoType newOffLabelUse) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__OFF_LABEL_USE, newOffLabelUse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OrientationType getOrientation() {
		return (OrientationType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ORIENTATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOrientation(OrientationType newOrientation) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ORIENTATION, newOrientation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PacingType getPacing() {
		return (PacingType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__PACING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPacing(PacingType newPacing) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__PACING, newPacing);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParticipationModalityType getParticipationModality() {
		return (ParticipationModalityType) getMixed()
				.get(HealthcarePackage.Literals.DOCUMENT_ROOT__PARTICIPATION_MODALITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParticipationModality(ParticipationModalityType newParticipationModality) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__PARTICIPATION_MODALITY,
				newParticipationModality);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getProfession() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__PROFESSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProfession(LanguageString newProfession, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__PROFESSION,
				newProfession, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProfession(LanguageString newProfession) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__PROFESSION, newProfession);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getReadingLevel() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__READING_LEVEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadingLevel(LanguageString newReadingLevel, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__READING_LEVEL,
				newReadingLevel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReadingLevel(LanguageString newReadingLevel) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__READING_LEVEL,
				newReadingLevel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getReleaseDate() {
		return (XMLGregorianCalendar) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__RELEASE_DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReleaseDate(XMLGregorianCalendar newReleaseDate) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__RELEASE_DATE, newReleaseDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getRelevantFinancialRelationship() {
		return (YesNoType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP,
				true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelevantFinancialRelationship(YesNoType newRelevantFinancialRelationship) {
		((FeatureMap.Internal) getMixed()).set(
				HealthcarePackage.Literals.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP,
				newRelevantFinancialRelationship);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getRelevantFinancialRelationshipDisclosure() {
		return (LanguageString) getMixed()
				.get(HealthcarePackage.Literals.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelevantFinancialRelationshipDisclosure(
			LanguageString newRelevantFinancialRelationshipDisclosure, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(
				HealthcarePackage.Literals.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE,
				newRelevantFinancialRelationshipDisclosure, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelevantFinancialRelationshipDisclosure(LanguageString newRelevantFinancialRelationshipDisclosure) {
		((FeatureMap.Internal) getMixed()).set(
				HealthcarePackage.Literals.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE,
				newRelevantFinancialRelationshipDisclosure);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRoom() {
		return (String) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__ROOM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRoom(String newRoom) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__ROOM, newRoom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getSpecialty() {
		return (LanguageString) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__SPECIALTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecialty(LanguageString newSpecialty, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__SPECIALTY,
				newSpecialty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpecialty(LanguageString newSpecialty) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__SPECIALTY, newSpecialty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecimenTypeType getSpecimenType() {
		return (SpecimenTypeType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__SPECIMEN_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpecimenType(SpecimenTypeType newSpecimenType) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__SPECIMEN_TYPE,
				newSpecimenType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getStartDateTime() {
		return (XMLGregorianCalendar) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__START_DATE_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartDateTime(XMLGregorianCalendar newStartDateTime) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__START_DATE_TIME,
				newStartDateTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetAudienceType getTargetAudience() {
		return (TargetAudienceType) getMixed().get(HealthcarePackage.Literals.DOCUMENT_ROOT__TARGET_AUDIENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetAudience(TargetAudienceType newTargetAudience, NotificationChain msgs) {
		return ((FeatureMap.Internal) getMixed()).basicAdd(HealthcarePackage.Literals.DOCUMENT_ROOT__TARGET_AUDIENCE,
				newTargetAudience, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetAudience(TargetAudienceType newTargetAudience) {
		((FeatureMap.Internal) getMixed()).set(HealthcarePackage.Literals.DOCUMENT_ROOT__TARGET_AUDIENCE,
				newTargetAudience);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case HealthcarePackage.DOCUMENT_ROOT__MIXED:
			return ((InternalEList<?>) getMixed()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
			return ((InternalEList<?>) getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
			return ((InternalEList<?>) getXSISchemaLocation()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_FORMAT:
			return basicSetActivityFormat(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_LOCATION:
			return basicSetActivityLocation(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__CLINICAL_HISTORY:
			return basicSetClinicalHistory(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return basicSetCommercialSupportAcknowledgement(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS:
			return basicSetCredits(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__CUSTOM_ELEMENTS:
			return basicSetCustomElements(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_ASSET:
			return basicSetHealthcareAsset(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_EDUCATION:
			return basicSetHealthcareEducation(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_METADATA:
			return basicSetHealthcareMetadata(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION:
			return basicSetOffLabelDescription(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__PROFESSION:
			return basicSetProfession(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__READING_LEVEL:
			return basicSetReadingLevel(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return basicSetRelevantFinancialRelationshipDisclosure(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__SPECIALTY:
			return basicSetSpecialty(null, msgs);
		case HealthcarePackage.DOCUMENT_ROOT__TARGET_AUDIENCE:
			return basicSetTargetAudience(null, msgs);
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
		case HealthcarePackage.DOCUMENT_ROOT__MIXED:
			if (coreType)
				return getMixed();
			return ((FeatureMap.Internal) getMixed()).getWrapper();
		case HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
			if (coreType)
				return getXMLNSPrefixMap();
			else
				return getXMLNSPrefixMap().map();
		case HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
			if (coreType)
				return getXSISchemaLocation();
			else
				return getXSISchemaLocation().map();
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITED_PROVIDER:
			return getAccreditedProvider();
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITING_BODY:
			return getAccreditingBody();
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION:
			return getActivityCertification();
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_DELIVERY:
			return getActivityDelivery();
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_FORMAT:
			return getActivityFormat();
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_LOCATION:
			return getActivityLocation();
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP:
			return getActivitySponsorship();
		case HealthcarePackage.DOCUMENT_ROOT__ANNOTATED:
			return getAnnotated();
		case HealthcarePackage.DOCUMENT_ROOT__AUDIENCE_CATEGORY:
			return getAudienceCategory();
		case HealthcarePackage.DOCUMENT_ROOT__BUILDING:
			return getBuilding();
		case HealthcarePackage.DOCUMENT_ROOT__CLINICAL_HISTORY:
			return getClinicalHistory();
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT:
			return getCommercialSupport();
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return getCommercialSupportAcknowledgement();
		case HealthcarePackage.DOCUMENT_ROOT__CONTACT:
			return getContact();
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS:
			return getCredits();
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS_AVAILABLE:
			return getCreditsAvailable();
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_TYPE:
			return getCreditType();
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_UNIT:
			return getCreditUnit();
		case HealthcarePackage.DOCUMENT_ROOT__CUSTOM_ELEMENTS:
			return getCustomElements();
		case HealthcarePackage.DOCUMENT_ROOT__END_DATE_TIME:
			return getEndDateTime();
		case HealthcarePackage.DOCUMENT_ROOT__EXPIRATION_DATE:
			return getExpirationDate();
		case HealthcarePackage.DOCUMENT_ROOT__FILE_HEIGHT:
			return getFileHeight();
		case HealthcarePackage.DOCUMENT_ROOT__FILE_WIDTH:
			return getFileWidth();
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_ASSET:
			return getHealthcareAsset();
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_EDUCATION:
			return getHealthcareEducation();
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_METADATA:
			return getHealthcareMetadata();
		case HealthcarePackage.DOCUMENT_ROOT__MAGNIFICATION:
			return getMagnification();
		case HealthcarePackage.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE:
			return getMedicalImageType();
		case HealthcarePackage.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER:
			return getNonAccreditedProvider();
		case HealthcarePackage.DOCUMENT_ROOT__NUMBER_OF_CREDITS:
			return getNumberOfCredits();
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION:
			return getOffLabelDescription();
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_USE:
			return getOffLabelUse();
		case HealthcarePackage.DOCUMENT_ROOT__ORIENTATION:
			return getOrientation();
		case HealthcarePackage.DOCUMENT_ROOT__PACING:
			return getPacing();
		case HealthcarePackage.DOCUMENT_ROOT__PARTICIPATION_MODALITY:
			return getParticipationModality();
		case HealthcarePackage.DOCUMENT_ROOT__PROFESSION:
			return getProfession();
		case HealthcarePackage.DOCUMENT_ROOT__READING_LEVEL:
			return getReadingLevel();
		case HealthcarePackage.DOCUMENT_ROOT__RELEASE_DATE:
			return getReleaseDate();
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP:
			return getRelevantFinancialRelationship();
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return getRelevantFinancialRelationshipDisclosure();
		case HealthcarePackage.DOCUMENT_ROOT__ROOM:
			return getRoom();
		case HealthcarePackage.DOCUMENT_ROOT__SPECIALTY:
			return getSpecialty();
		case HealthcarePackage.DOCUMENT_ROOT__SPECIMEN_TYPE:
			return getSpecimenType();
		case HealthcarePackage.DOCUMENT_ROOT__START_DATE_TIME:
			return getStartDateTime();
		case HealthcarePackage.DOCUMENT_ROOT__TARGET_AUDIENCE:
			return getTargetAudience();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case HealthcarePackage.DOCUMENT_ROOT__MIXED:
			((FeatureMap.Internal) getMixed()).set(newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
			((EStructuralFeature.Setting) getXMLNSPrefixMap()).set(newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
			((EStructuralFeature.Setting) getXSISchemaLocation()).set(newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITED_PROVIDER:
			setAccreditedProvider((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITING_BODY:
			setAccreditingBody((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION:
			setActivityCertification((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_DELIVERY:
			setActivityDelivery((ActivityDeliveryType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_FORMAT:
			setActivityFormat((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_LOCATION:
			setActivityLocation((ActivityLocationType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP:
			setActivitySponsorship((ActivitySponsorshipType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ANNOTATED:
			setAnnotated((YesNoType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__AUDIENCE_CATEGORY:
			setAudienceCategory((AudienceCategoryType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__BUILDING:
			setBuilding((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CLINICAL_HISTORY:
			setClinicalHistory((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT:
			setCommercialSupport((YesNoType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			setCommercialSupportAcknowledgement((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CONTACT:
			setContact((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS:
			setCredits((CreditsType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS_AVAILABLE:
			setCreditsAvailable((YesNoType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_TYPE:
			setCreditType((CreditTypeType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_UNIT:
			setCreditUnit((CreditUnitType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CUSTOM_ELEMENTS:
			setCustomElements((CustomElementsType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__END_DATE_TIME:
			setEndDateTime((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__EXPIRATION_DATE:
			setExpirationDate((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__FILE_HEIGHT:
			setFileHeight((BigInteger) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__FILE_WIDTH:
			setFileWidth((BigInteger) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_ASSET:
			setHealthcareAsset((HealthcareAssetType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_EDUCATION:
			setHealthcareEducation((HealthcareEducationType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_METADATA:
			setHealthcareMetadata((HealthcareMetadataType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__MAGNIFICATION:
			setMagnification((BigInteger) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE:
			setMedicalImageType((MedicalImageTypeType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER:
			setNonAccreditedProvider((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__NUMBER_OF_CREDITS:
			setNumberOfCredits((BigDecimal) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION:
			setOffLabelDescription((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_USE:
			setOffLabelUse((YesNoType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ORIENTATION:
			setOrientation((OrientationType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PACING:
			setPacing((PacingType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PARTICIPATION_MODALITY:
			setParticipationModality((ParticipationModalityType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PROFESSION:
			setProfession((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__READING_LEVEL:
			setReadingLevel((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEASE_DATE:
			setReleaseDate((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP:
			setRelevantFinancialRelationship((YesNoType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			setRelevantFinancialRelationshipDisclosure((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ROOM:
			setRoom((String) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__SPECIALTY:
			setSpecialty((LanguageString) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__SPECIMEN_TYPE:
			setSpecimenType((SpecimenTypeType) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__START_DATE_TIME:
			setStartDateTime((XMLGregorianCalendar) newValue);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__TARGET_AUDIENCE:
			setTargetAudience((TargetAudienceType) newValue);
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
		case HealthcarePackage.DOCUMENT_ROOT__MIXED:
			getMixed().clear();
			return;
		case HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
			getXMLNSPrefixMap().clear();
			return;
		case HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
			getXSISchemaLocation().clear();
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITED_PROVIDER:
			setAccreditedProvider(ACCREDITED_PROVIDER_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITING_BODY:
			setAccreditingBody(ACCREDITING_BODY_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION:
			setActivityCertification(ACTIVITY_CERTIFICATION_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_DELIVERY:
			setActivityDelivery(ACTIVITY_DELIVERY_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_FORMAT:
			setActivityFormat((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_LOCATION:
			setActivityLocation((ActivityLocationType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP:
			setActivitySponsorship(ACTIVITY_SPONSORSHIP_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ANNOTATED:
			setAnnotated(ANNOTATED_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__AUDIENCE_CATEGORY:
			setAudienceCategory(AUDIENCE_CATEGORY_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__BUILDING:
			setBuilding(BUILDING_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CLINICAL_HISTORY:
			setClinicalHistory((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT:
			setCommercialSupport(COMMERCIAL_SUPPORT_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			setCommercialSupportAcknowledgement((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CONTACT:
			setContact(CONTACT_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS:
			setCredits((CreditsType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS_AVAILABLE:
			setCreditsAvailable(CREDITS_AVAILABLE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_TYPE:
			setCreditType(CREDIT_TYPE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_UNIT:
			setCreditUnit(CREDIT_UNIT_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__CUSTOM_ELEMENTS:
			setCustomElements((CustomElementsType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__END_DATE_TIME:
			setEndDateTime(END_DATE_TIME_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__EXPIRATION_DATE:
			setExpirationDate(EXPIRATION_DATE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__FILE_HEIGHT:
			setFileHeight(FILE_HEIGHT_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__FILE_WIDTH:
			setFileWidth(FILE_WIDTH_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_ASSET:
			setHealthcareAsset((HealthcareAssetType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_EDUCATION:
			setHealthcareEducation((HealthcareEducationType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_METADATA:
			setHealthcareMetadata((HealthcareMetadataType) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__MAGNIFICATION:
			setMagnification(MAGNIFICATION_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE:
			setMedicalImageType(MEDICAL_IMAGE_TYPE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER:
			setNonAccreditedProvider(NON_ACCREDITED_PROVIDER_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__NUMBER_OF_CREDITS:
			setNumberOfCredits(NUMBER_OF_CREDITS_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION:
			setOffLabelDescription((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_USE:
			setOffLabelUse(OFF_LABEL_USE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ORIENTATION:
			setOrientation(ORIENTATION_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PACING:
			setPacing(PACING_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PARTICIPATION_MODALITY:
			setParticipationModality(PARTICIPATION_MODALITY_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__PROFESSION:
			setProfession((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__READING_LEVEL:
			setReadingLevel((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEASE_DATE:
			setReleaseDate(RELEASE_DATE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP:
			setRelevantFinancialRelationship(RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			setRelevantFinancialRelationshipDisclosure((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__ROOM:
			setRoom(ROOM_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__SPECIALTY:
			setSpecialty((LanguageString) null);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__SPECIMEN_TYPE:
			setSpecimenType(SPECIMEN_TYPE_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__START_DATE_TIME:
			setStartDateTime(START_DATE_TIME_EDEFAULT);
			return;
		case HealthcarePackage.DOCUMENT_ROOT__TARGET_AUDIENCE:
			setTargetAudience((TargetAudienceType) null);
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
		case HealthcarePackage.DOCUMENT_ROOT__MIXED:
			return mixed != null && !mixed.isEmpty();
		case HealthcarePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
			return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
		case HealthcarePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
			return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITED_PROVIDER:
			return ACCREDITED_PROVIDER_EDEFAULT == null ? getAccreditedProvider() != null
					: !ACCREDITED_PROVIDER_EDEFAULT.equals(getAccreditedProvider());
		case HealthcarePackage.DOCUMENT_ROOT__ACCREDITING_BODY:
			return ACCREDITING_BODY_EDEFAULT == null ? getAccreditingBody() != null
					: !ACCREDITING_BODY_EDEFAULT.equals(getAccreditingBody());
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_CERTIFICATION:
			return ACTIVITY_CERTIFICATION_EDEFAULT == null ? getActivityCertification() != null
					: !ACTIVITY_CERTIFICATION_EDEFAULT.equals(getActivityCertification());
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_DELIVERY:
			return getActivityDelivery() != ACTIVITY_DELIVERY_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_FORMAT:
			return getActivityFormat() != null;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_LOCATION:
			return getActivityLocation() != null;
		case HealthcarePackage.DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP:
			return getActivitySponsorship() != ACTIVITY_SPONSORSHIP_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__ANNOTATED:
			return getAnnotated() != ANNOTATED_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__AUDIENCE_CATEGORY:
			return getAudienceCategory() != AUDIENCE_CATEGORY_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__BUILDING:
			return BUILDING_EDEFAULT == null ? getBuilding() != null : !BUILDING_EDEFAULT.equals(getBuilding());
		case HealthcarePackage.DOCUMENT_ROOT__CLINICAL_HISTORY:
			return getClinicalHistory() != null;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT:
			return getCommercialSupport() != COMMERCIAL_SUPPORT_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT:
			return getCommercialSupportAcknowledgement() != null;
		case HealthcarePackage.DOCUMENT_ROOT__CONTACT:
			return CONTACT_EDEFAULT == null ? getContact() != null : !CONTACT_EDEFAULT.equals(getContact());
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS:
			return getCredits() != null;
		case HealthcarePackage.DOCUMENT_ROOT__CREDITS_AVAILABLE:
			return getCreditsAvailable() != CREDITS_AVAILABLE_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_TYPE:
			return getCreditType() != CREDIT_TYPE_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__CREDIT_UNIT:
			return getCreditUnit() != CREDIT_UNIT_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__CUSTOM_ELEMENTS:
			return getCustomElements() != null;
		case HealthcarePackage.DOCUMENT_ROOT__END_DATE_TIME:
			return END_DATE_TIME_EDEFAULT == null ? getEndDateTime() != null
					: !END_DATE_TIME_EDEFAULT.equals(getEndDateTime());
		case HealthcarePackage.DOCUMENT_ROOT__EXPIRATION_DATE:
			return EXPIRATION_DATE_EDEFAULT == null ? getExpirationDate() != null
					: !EXPIRATION_DATE_EDEFAULT.equals(getExpirationDate());
		case HealthcarePackage.DOCUMENT_ROOT__FILE_HEIGHT:
			return FILE_HEIGHT_EDEFAULT == null ? getFileHeight() != null
					: !FILE_HEIGHT_EDEFAULT.equals(getFileHeight());
		case HealthcarePackage.DOCUMENT_ROOT__FILE_WIDTH:
			return FILE_WIDTH_EDEFAULT == null ? getFileWidth() != null : !FILE_WIDTH_EDEFAULT.equals(getFileWidth());
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_ASSET:
			return getHealthcareAsset() != null;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_EDUCATION:
			return getHealthcareEducation() != null;
		case HealthcarePackage.DOCUMENT_ROOT__HEALTHCARE_METADATA:
			return getHealthcareMetadata() != null;
		case HealthcarePackage.DOCUMENT_ROOT__MAGNIFICATION:
			return MAGNIFICATION_EDEFAULT == null ? getMagnification() != null
					: !MAGNIFICATION_EDEFAULT.equals(getMagnification());
		case HealthcarePackage.DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE:
			return getMedicalImageType() != MEDICAL_IMAGE_TYPE_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER:
			return NON_ACCREDITED_PROVIDER_EDEFAULT == null ? getNonAccreditedProvider() != null
					: !NON_ACCREDITED_PROVIDER_EDEFAULT.equals(getNonAccreditedProvider());
		case HealthcarePackage.DOCUMENT_ROOT__NUMBER_OF_CREDITS:
			return NUMBER_OF_CREDITS_EDEFAULT == null ? getNumberOfCredits() != null
					: !NUMBER_OF_CREDITS_EDEFAULT.equals(getNumberOfCredits());
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION:
			return getOffLabelDescription() != null;
		case HealthcarePackage.DOCUMENT_ROOT__OFF_LABEL_USE:
			return getOffLabelUse() != OFF_LABEL_USE_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__ORIENTATION:
			return getOrientation() != ORIENTATION_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__PACING:
			return getPacing() != PACING_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__PARTICIPATION_MODALITY:
			return getParticipationModality() != PARTICIPATION_MODALITY_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__PROFESSION:
			return getProfession() != null;
		case HealthcarePackage.DOCUMENT_ROOT__READING_LEVEL:
			return getReadingLevel() != null;
		case HealthcarePackage.DOCUMENT_ROOT__RELEASE_DATE:
			return RELEASE_DATE_EDEFAULT == null ? getReleaseDate() != null
					: !RELEASE_DATE_EDEFAULT.equals(getReleaseDate());
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP:
			return getRelevantFinancialRelationship() != RELEVANT_FINANCIAL_RELATIONSHIP_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE:
			return getRelevantFinancialRelationshipDisclosure() != null;
		case HealthcarePackage.DOCUMENT_ROOT__ROOM:
			return ROOM_EDEFAULT == null ? getRoom() != null : !ROOM_EDEFAULT.equals(getRoom());
		case HealthcarePackage.DOCUMENT_ROOT__SPECIALTY:
			return getSpecialty() != null;
		case HealthcarePackage.DOCUMENT_ROOT__SPECIMEN_TYPE:
			return getSpecimenType() != SPECIMEN_TYPE_EDEFAULT;
		case HealthcarePackage.DOCUMENT_ROOT__START_DATE_TIME:
			return START_DATE_TIME_EDEFAULT == null ? getStartDateTime() != null
					: !START_DATE_TIME_EDEFAULT.equals(getStartDateTime());
		case HealthcarePackage.DOCUMENT_ROOT__TARGET_AUDIENCE:
			return getTargetAudience() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
