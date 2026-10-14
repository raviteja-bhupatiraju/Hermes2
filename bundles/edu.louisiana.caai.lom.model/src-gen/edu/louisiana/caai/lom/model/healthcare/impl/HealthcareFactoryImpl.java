/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HealthcareFactoryImpl extends EFactoryImpl implements HealthcareFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HealthcareFactory init() {
		try {
			HealthcareFactory theHealthcareFactory = (HealthcareFactory) EPackage.Registry.INSTANCE
					.getEFactory(HealthcarePackage.eNS_URI);
			if (theHealthcareFactory != null) {
				return theHealthcareFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HealthcareFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HealthcareFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE:
			return createActivityLocationType();
		case HealthcarePackage.CREDITS_TYPE:
			return createCreditsType();
		case HealthcarePackage.CUSTOM_ELEMENTS_TYPE:
			return createCustomElementsType();
		case HealthcarePackage.DOCUMENT_ROOT:
			return createDocumentRoot();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE:
			return createHealthcareAssetType();
		case HealthcarePackage.HEALTHCARE_EDUCATION_TYPE:
			return createHealthcareEducationType();
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE:
			return createHealthcareMetadataType();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE:
			return createTargetAudienceType();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case HealthcarePackage.ACTIVITY_DELIVERY_TYPE:
			return createActivityDeliveryTypeFromString(eDataType, initialValue);
		case HealthcarePackage.ACTIVITY_SPONSORSHIP_TYPE:
			return createActivitySponsorshipTypeFromString(eDataType, initialValue);
		case HealthcarePackage.AUDIENCE_CATEGORY_TYPE:
			return createAudienceCategoryTypeFromString(eDataType, initialValue);
		case HealthcarePackage.CREDIT_TYPE_TYPE:
			return createCreditTypeTypeFromString(eDataType, initialValue);
		case HealthcarePackage.CREDIT_UNIT_TYPE:
			return createCreditUnitTypeFromString(eDataType, initialValue);
		case HealthcarePackage.MEDICAL_IMAGE_TYPE_TYPE:
			return createMedicalImageTypeTypeFromString(eDataType, initialValue);
		case HealthcarePackage.ORIENTATION_TYPE:
			return createOrientationTypeFromString(eDataType, initialValue);
		case HealthcarePackage.PACING_TYPE:
			return createPacingTypeFromString(eDataType, initialValue);
		case HealthcarePackage.PARTICIPATION_MODALITY_TYPE:
			return createParticipationModalityTypeFromString(eDataType, initialValue);
		case HealthcarePackage.SPECIMEN_TYPE_TYPE:
			return createSpecimenTypeTypeFromString(eDataType, initialValue);
		case HealthcarePackage.YES_NO_TYPE:
			return createYesNoTypeFromString(eDataType, initialValue);
		case HealthcarePackage.ACTIVITY_DELIVERY_TYPE_OBJECT:
			return createActivityDeliveryTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.ACTIVITY_SPONSORSHIP_TYPE_OBJECT:
			return createActivitySponsorshipTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.AUDIENCE_CATEGORY_TYPE_OBJECT:
			return createAudienceCategoryTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.CREDIT_TYPE_TYPE_OBJECT:
			return createCreditTypeTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.CREDIT_UNIT_TYPE_OBJECT:
			return createCreditUnitTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.MEDICAL_IMAGE_TYPE_TYPE_OBJECT:
			return createMedicalImageTypeTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.ORIENTATION_TYPE_OBJECT:
			return createOrientationTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.PACING_TYPE_OBJECT:
			return createPacingTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.PARTICIPATION_MODALITY_TYPE_OBJECT:
			return createParticipationModalityTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.SPECIMEN_TYPE_TYPE_OBJECT:
			return createSpecimenTypeTypeObjectFromString(eDataType, initialValue);
		case HealthcarePackage.YES_NO_TYPE_OBJECT:
			return createYesNoTypeObjectFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case HealthcarePackage.ACTIVITY_DELIVERY_TYPE:
			return convertActivityDeliveryTypeToString(eDataType, instanceValue);
		case HealthcarePackage.ACTIVITY_SPONSORSHIP_TYPE:
			return convertActivitySponsorshipTypeToString(eDataType, instanceValue);
		case HealthcarePackage.AUDIENCE_CATEGORY_TYPE:
			return convertAudienceCategoryTypeToString(eDataType, instanceValue);
		case HealthcarePackage.CREDIT_TYPE_TYPE:
			return convertCreditTypeTypeToString(eDataType, instanceValue);
		case HealthcarePackage.CREDIT_UNIT_TYPE:
			return convertCreditUnitTypeToString(eDataType, instanceValue);
		case HealthcarePackage.MEDICAL_IMAGE_TYPE_TYPE:
			return convertMedicalImageTypeTypeToString(eDataType, instanceValue);
		case HealthcarePackage.ORIENTATION_TYPE:
			return convertOrientationTypeToString(eDataType, instanceValue);
		case HealthcarePackage.PACING_TYPE:
			return convertPacingTypeToString(eDataType, instanceValue);
		case HealthcarePackage.PARTICIPATION_MODALITY_TYPE:
			return convertParticipationModalityTypeToString(eDataType, instanceValue);
		case HealthcarePackage.SPECIMEN_TYPE_TYPE:
			return convertSpecimenTypeTypeToString(eDataType, instanceValue);
		case HealthcarePackage.YES_NO_TYPE:
			return convertYesNoTypeToString(eDataType, instanceValue);
		case HealthcarePackage.ACTIVITY_DELIVERY_TYPE_OBJECT:
			return convertActivityDeliveryTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.ACTIVITY_SPONSORSHIP_TYPE_OBJECT:
			return convertActivitySponsorshipTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.AUDIENCE_CATEGORY_TYPE_OBJECT:
			return convertAudienceCategoryTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.CREDIT_TYPE_TYPE_OBJECT:
			return convertCreditTypeTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.CREDIT_UNIT_TYPE_OBJECT:
			return convertCreditUnitTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.MEDICAL_IMAGE_TYPE_TYPE_OBJECT:
			return convertMedicalImageTypeTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.ORIENTATION_TYPE_OBJECT:
			return convertOrientationTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.PACING_TYPE_OBJECT:
			return convertPacingTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.PARTICIPATION_MODALITY_TYPE_OBJECT:
			return convertParticipationModalityTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.SPECIMEN_TYPE_TYPE_OBJECT:
			return convertSpecimenTypeTypeObjectToString(eDataType, instanceValue);
		case HealthcarePackage.YES_NO_TYPE_OBJECT:
			return convertYesNoTypeObjectToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActivityLocationType createActivityLocationType() {
		ActivityLocationTypeImpl activityLocationType = new ActivityLocationTypeImpl();
		return activityLocationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreditsType createCreditsType() {
		CreditsTypeImpl creditsType = new CreditsTypeImpl();
		return creditsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomElementsType createCustomElementsType() {
		CustomElementsTypeImpl customElementsType = new CustomElementsTypeImpl();
		return customElementsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareAssetType createHealthcareAssetType() {
		HealthcareAssetTypeImpl healthcareAssetType = new HealthcareAssetTypeImpl();
		return healthcareAssetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareEducationType createHealthcareEducationType() {
		HealthcareEducationTypeImpl healthcareEducationType = new HealthcareEducationTypeImpl();
		return healthcareEducationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareMetadataType createHealthcareMetadataType() {
		HealthcareMetadataTypeImpl healthcareMetadataType = new HealthcareMetadataTypeImpl();
		return healthcareMetadataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetAudienceType createTargetAudienceType() {
		TargetAudienceTypeImpl targetAudienceType = new TargetAudienceTypeImpl();
		return targetAudienceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivityDeliveryType createActivityDeliveryTypeFromString(EDataType eDataType, String initialValue) {
		ActivityDeliveryType result = ActivityDeliveryType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActivityDeliveryTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivitySponsorshipType createActivitySponsorshipTypeFromString(EDataType eDataType, String initialValue) {
		ActivitySponsorshipType result = ActivitySponsorshipType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActivitySponsorshipTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AudienceCategoryType createAudienceCategoryTypeFromString(EDataType eDataType, String initialValue) {
		AudienceCategoryType result = AudienceCategoryType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAudienceCategoryTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreditTypeType createCreditTypeTypeFromString(EDataType eDataType, String initialValue) {
		CreditTypeType result = CreditTypeType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreditTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreditUnitType createCreditUnitTypeFromString(EDataType eDataType, String initialValue) {
		CreditUnitType result = CreditUnitType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreditUnitTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MedicalImageTypeType createMedicalImageTypeTypeFromString(EDataType eDataType, String initialValue) {
		MedicalImageTypeType result = MedicalImageTypeType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMedicalImageTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationType createOrientationTypeFromString(EDataType eDataType, String initialValue) {
		OrientationType result = OrientationType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrientationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PacingType createPacingTypeFromString(EDataType eDataType, String initialValue) {
		PacingType result = PacingType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPacingTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParticipationModalityType createParticipationModalityTypeFromString(EDataType eDataType,
			String initialValue) {
		ParticipationModalityType result = ParticipationModalityType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParticipationModalityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpecimenTypeType createSpecimenTypeTypeFromString(EDataType eDataType, String initialValue) {
		SpecimenTypeType result = SpecimenTypeType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSpecimenTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public YesNoType createYesNoTypeFromString(EDataType eDataType, String initialValue) {
		YesNoType result = YesNoType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertYesNoTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivityDeliveryType createActivityDeliveryTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createActivityDeliveryTypeFromString(HealthcarePackage.Literals.ACTIVITY_DELIVERY_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActivityDeliveryTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertActivityDeliveryTypeToString(HealthcarePackage.Literals.ACTIVITY_DELIVERY_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivitySponsorshipType createActivitySponsorshipTypeObjectFromString(EDataType eDataType,
			String initialValue) {
		return createActivitySponsorshipTypeFromString(HealthcarePackage.Literals.ACTIVITY_SPONSORSHIP_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActivitySponsorshipTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertActivitySponsorshipTypeToString(HealthcarePackage.Literals.ACTIVITY_SPONSORSHIP_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AudienceCategoryType createAudienceCategoryTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createAudienceCategoryTypeFromString(HealthcarePackage.Literals.AUDIENCE_CATEGORY_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAudienceCategoryTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAudienceCategoryTypeToString(HealthcarePackage.Literals.AUDIENCE_CATEGORY_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreditTypeType createCreditTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCreditTypeTypeFromString(HealthcarePackage.Literals.CREDIT_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreditTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCreditTypeTypeToString(HealthcarePackage.Literals.CREDIT_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreditUnitType createCreditUnitTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCreditUnitTypeFromString(HealthcarePackage.Literals.CREDIT_UNIT_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreditUnitTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCreditUnitTypeToString(HealthcarePackage.Literals.CREDIT_UNIT_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MedicalImageTypeType createMedicalImageTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createMedicalImageTypeTypeFromString(HealthcarePackage.Literals.MEDICAL_IMAGE_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMedicalImageTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertMedicalImageTypeTypeToString(HealthcarePackage.Literals.MEDICAL_IMAGE_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationType createOrientationTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createOrientationTypeFromString(HealthcarePackage.Literals.ORIENTATION_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrientationTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertOrientationTypeToString(HealthcarePackage.Literals.ORIENTATION_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PacingType createPacingTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createPacingTypeFromString(HealthcarePackage.Literals.PACING_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPacingTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPacingTypeToString(HealthcarePackage.Literals.PACING_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParticipationModalityType createParticipationModalityTypeObjectFromString(EDataType eDataType,
			String initialValue) {
		return createParticipationModalityTypeFromString(HealthcarePackage.Literals.PARTICIPATION_MODALITY_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParticipationModalityTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertParticipationModalityTypeToString(HealthcarePackage.Literals.PARTICIPATION_MODALITY_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpecimenTypeType createSpecimenTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSpecimenTypeTypeFromString(HealthcarePackage.Literals.SPECIMEN_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSpecimenTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSpecimenTypeTypeToString(HealthcarePackage.Literals.SPECIMEN_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public YesNoType createYesNoTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createYesNoTypeFromString(HealthcarePackage.Literals.YES_NO_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertYesNoTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertYesNoTypeToString(HealthcarePackage.Literals.YES_NO_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcarePackage getHealthcarePackage() {
		return (HealthcarePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HealthcarePackage getPackage() {
		return HealthcarePackage.eINSTANCE;
	}

} //HealthcareFactoryImpl
