/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;

import edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl;

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
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType;
import edu.louisiana.caai.lom.model.healthcare.OrientationType;
import edu.louisiana.caai.lom.model.healthcare.PacingType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl;

import edu.louisiana.caai.lom.model.lom.LomPackage;

import edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl;

import edu.louisiana.caai.lom.model.vocab.VocabPackage;

import edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HealthcarePackageImpl extends EPackageImpl implements HealthcarePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityLocationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass creditsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass customElementsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass healthcareAssetTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass healthcareEducationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass healthcareMetadataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass targetAudienceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum activityDeliveryTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum activitySponsorshipTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum audienceCategoryTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum creditTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum creditUnitTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum medicalImageTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum orientationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum pacingTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum participationModalityTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum specimenTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum yesNoTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType activityDeliveryTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType activitySponsorshipTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType audienceCategoryTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType creditTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType creditUnitTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType medicalImageTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType orientationTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pacingTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType participationModalityTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType specimenTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType yesNoTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private HealthcarePackageImpl() {
		super(eNS_URI, HealthcareFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link HealthcarePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static HealthcarePackage init() {
		if (isInited)
			return (HealthcarePackage) EPackage.Registry.INSTANCE.getEPackage(HealthcarePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredHealthcarePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		HealthcarePackageImpl theHealthcarePackage = registeredHealthcarePackage instanceof HealthcarePackageImpl
				? (HealthcarePackageImpl) registeredHealthcarePackage
				: new HealthcarePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI);
		LomPackageImpl theLomPackage = (LomPackageImpl) (registeredPackage instanceof LomPackageImpl ? registeredPackage
				: LomPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AddressPackage.eNS_URI);
		AddressPackageImpl theAddressPackage = (AddressPackageImpl) (registeredPackage instanceof AddressPackageImpl
				? registeredPackage
				: AddressPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(VocabPackage.eNS_URI);
		VocabPackageImpl theVocabPackage = (VocabPackageImpl) (registeredPackage instanceof VocabPackageImpl
				? registeredPackage
				: VocabPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarevocabPackage.eNS_URI);
		HealthcarevocabPackageImpl theHealthcarevocabPackage = (HealthcarevocabPackageImpl) (registeredPackage instanceof HealthcarevocabPackageImpl
				? registeredPackage
				: HealthcarevocabPackage.eINSTANCE);

		// Load packages
		theLomPackage.loadPackage();

		// Create package meta-data objects
		theHealthcarePackage.createPackageContents();
		theAddressPackage.createPackageContents();
		theVocabPackage.createPackageContents();
		theHealthcarevocabPackage.createPackageContents();

		// Initialize created meta-data
		theHealthcarePackage.initializePackageContents();
		theAddressPackage.initializePackageContents();
		theVocabPackage.initializePackageContents();
		theHealthcarevocabPackage.initializePackageContents();

		// Fix loaded packages
		theLomPackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theHealthcarePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(HealthcarePackage.eNS_URI, theHealthcarePackage);
		return theHealthcarePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getActivityLocationType() {
		return activityLocationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActivityLocationType_Room() {
		return (EAttribute) activityLocationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActivityLocationType_Building() {
		return (EAttribute) activityLocationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActivityLocationType_UniqueElementName() {
		return (EAttribute) activityLocationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCreditsType() {
		return creditsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_AccreditingBody() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_ActivityCertification() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_CreditType() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_CreditUnit() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_Pacing() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_AccreditedProvider() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_NonAccreditedProvider() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_ReleaseDate() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_ExpirationDate() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCreditsType_NumberOfCredits() {
		return (EAttribute) creditsTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCustomElementsType() {
		return customElementsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCustomElementsType_Any() {
		return (EAttribute) customElementsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_AccreditedProvider() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_AccreditingBody() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ActivityCertification() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ActivityDelivery() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_ActivityFormat() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_ActivityLocation() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ActivitySponsorship() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Annotated() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_AudienceCategory() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Building() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_ClinicalHistory() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_CommercialSupport() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_CommercialSupportAcknowledgement() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Contact() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Credits() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_CreditsAvailable() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_CreditType() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_CreditUnit() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_CustomElements() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_EndDateTime() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ExpirationDate() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_FileHeight() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_FileWidth() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_HealthcareAsset() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_HealthcareEducation() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_HealthcareMetadata() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Magnification() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_MedicalImageType() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_NonAccreditedProvider() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_NumberOfCredits() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_OffLabelDescription() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_OffLabelUse() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Orientation() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Pacing() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ParticipationModality() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(37);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Profession() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(38);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_ReadingLevel() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(39);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_ReleaseDate() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(40);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_RelevantFinancialRelationship() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(41);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_RelevantFinancialRelationshipDisclosure() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(42);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Room() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(43);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Specialty() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(44);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_SpecimenType() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(45);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_StartDateTime() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(46);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_TargetAudience() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(47);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHealthcareAssetType() {
		return healthcareAssetTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_Annotated() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareAssetType_ClinicalHistory() {
		return (EReference) healthcareAssetTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_Magnification() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_Orientation() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_MedicalImageType() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_SpecimenType() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_FileHeight() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_FileWidth() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareAssetType_UniqueElementName() {
		return (EAttribute) healthcareAssetTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHealthcareEducationType() {
		return healthcareEducationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_ExpirationDate() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_CreditsAvailable() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_Credits() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_TargetAudience() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_ActivityLocation() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_StartDateTime() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_EndDateTime() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_ActivitySponsorship() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_ParticipationModality() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_ActivityDelivery() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_ActivityFormat() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_OffLabelUse() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_OffLabelDescription() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_CommercialSupport() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_CommercialSupportAcknowledgement() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_RelevantFinancialRelationship() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareEducationType_RelevantFinancialRelationshipDisclosure() {
		return (EReference) healthcareEducationTypeEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_Contact() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareEducationType_UniqueElementName() {
		return (EAttribute) healthcareEducationTypeEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHealthcareMetadataType() {
		return healthcareMetadataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareMetadataType_HealthcareEducation() {
		return (EReference) healthcareMetadataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHealthcareMetadataType_HealthcareAsset() {
		return (EReference) healthcareMetadataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthcareMetadataType_UniqueElementName() {
		return (EAttribute) healthcareMetadataTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTargetAudienceType() {
		return targetAudienceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTargetAudienceType_AudienceCategory() {
		return (EAttribute) targetAudienceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTargetAudienceType_Profession() {
		return (EReference) targetAudienceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTargetAudienceType_Specialty() {
		return (EReference) targetAudienceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTargetAudienceType_ReadingLevel() {
		return (EReference) targetAudienceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTargetAudienceType_UniqueElementName() {
		return (EAttribute) targetAudienceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getActivityDeliveryType() {
		return activityDeliveryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getActivitySponsorshipType() {
		return activitySponsorshipTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAudienceCategoryType() {
		return audienceCategoryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getCreditTypeType() {
		return creditTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getCreditUnitType() {
		return creditUnitTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getMedicalImageTypeType() {
		return medicalImageTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOrientationType() {
		return orientationTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPacingType() {
		return pacingTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getParticipationModalityType() {
		return participationModalityTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSpecimenTypeType() {
		return specimenTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getYesNoType() {
		return yesNoTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getActivityDeliveryTypeObject() {
		return activityDeliveryTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getActivitySponsorshipTypeObject() {
		return activitySponsorshipTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getAudienceCategoryTypeObject() {
		return audienceCategoryTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCreditTypeTypeObject() {
		return creditTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCreditUnitTypeObject() {
		return creditUnitTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getMedicalImageTypeTypeObject() {
		return medicalImageTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getOrientationTypeObject() {
		return orientationTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPacingTypeObject() {
		return pacingTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getParticipationModalityTypeObject() {
		return participationModalityTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSpecimenTypeTypeObject() {
		return specimenTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getYesNoTypeObject() {
		return yesNoTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareFactory getHealthcareFactory() {
		return (HealthcareFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		activityLocationTypeEClass = createEClass(ACTIVITY_LOCATION_TYPE);
		createEAttribute(activityLocationTypeEClass, ACTIVITY_LOCATION_TYPE__ROOM);
		createEAttribute(activityLocationTypeEClass, ACTIVITY_LOCATION_TYPE__BUILDING);
		createEAttribute(activityLocationTypeEClass, ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME);

		creditsTypeEClass = createEClass(CREDITS_TYPE);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__ACCREDITING_BODY);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__ACTIVITY_CERTIFICATION);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__CREDIT_TYPE);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__CREDIT_UNIT);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__PACING);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__ACCREDITED_PROVIDER);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__NON_ACCREDITED_PROVIDER);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__RELEASE_DATE);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__EXPIRATION_DATE);
		createEAttribute(creditsTypeEClass, CREDITS_TYPE__NUMBER_OF_CREDITS);

		customElementsTypeEClass = createEClass(CUSTOM_ELEMENTS_TYPE);
		createEAttribute(customElementsTypeEClass, CUSTOM_ELEMENTS_TYPE__ANY);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACCREDITED_PROVIDER);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACCREDITING_BODY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_CERTIFICATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_DELIVERY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_FORMAT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ANNOTATED);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AUDIENCE_CATEGORY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__BUILDING);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CLINICAL_HISTORY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__COMMERCIAL_SUPPORT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CONTACT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CREDITS);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CREDITS_AVAILABLE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CREDIT_TYPE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CREDIT_UNIT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CUSTOM_ELEMENTS);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__END_DATE_TIME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__EXPIRATION_DATE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__FILE_HEIGHT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__FILE_WIDTH);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HEALTHCARE_ASSET);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HEALTHCARE_EDUCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HEALTHCARE_METADATA);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MAGNIFICATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__NUMBER_OF_CREDITS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__OFF_LABEL_USE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ORIENTATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__PACING);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__PARTICIPATION_MODALITY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROFESSION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__READING_LEVEL);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__RELEASE_DATE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ROOM);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SPECIALTY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__SPECIMEN_TYPE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__START_DATE_TIME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TARGET_AUDIENCE);

		healthcareAssetTypeEClass = createEClass(HEALTHCARE_ASSET_TYPE);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__ANNOTATED);
		createEReference(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__MAGNIFICATION);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__ORIENTATION);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__FILE_HEIGHT);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__FILE_WIDTH);
		createEAttribute(healthcareAssetTypeEClass, HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME);

		healthcareEducationTypeEClass = createEClass(HEALTHCARE_EDUCATION_TYPE);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__CREDITS);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT);
		createEReference(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP);
		createEReference(healthcareEducationTypeEClass,
				HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__CONTACT);
		createEAttribute(healthcareEducationTypeEClass, HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME);

		healthcareMetadataTypeEClass = createEClass(HEALTHCARE_METADATA_TYPE);
		createEReference(healthcareMetadataTypeEClass, HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION);
		createEReference(healthcareMetadataTypeEClass, HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET);
		createEAttribute(healthcareMetadataTypeEClass, HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME);

		targetAudienceTypeEClass = createEClass(TARGET_AUDIENCE_TYPE);
		createEAttribute(targetAudienceTypeEClass, TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY);
		createEReference(targetAudienceTypeEClass, TARGET_AUDIENCE_TYPE__PROFESSION);
		createEReference(targetAudienceTypeEClass, TARGET_AUDIENCE_TYPE__SPECIALTY);
		createEReference(targetAudienceTypeEClass, TARGET_AUDIENCE_TYPE__READING_LEVEL);
		createEAttribute(targetAudienceTypeEClass, TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME);

		// Create enums
		activityDeliveryTypeEEnum = createEEnum(ACTIVITY_DELIVERY_TYPE);
		activitySponsorshipTypeEEnum = createEEnum(ACTIVITY_SPONSORSHIP_TYPE);
		audienceCategoryTypeEEnum = createEEnum(AUDIENCE_CATEGORY_TYPE);
		creditTypeTypeEEnum = createEEnum(CREDIT_TYPE_TYPE);
		creditUnitTypeEEnum = createEEnum(CREDIT_UNIT_TYPE);
		medicalImageTypeTypeEEnum = createEEnum(MEDICAL_IMAGE_TYPE_TYPE);
		orientationTypeEEnum = createEEnum(ORIENTATION_TYPE);
		pacingTypeEEnum = createEEnum(PACING_TYPE);
		participationModalityTypeEEnum = createEEnum(PARTICIPATION_MODALITY_TYPE);
		specimenTypeTypeEEnum = createEEnum(SPECIMEN_TYPE_TYPE);
		yesNoTypeEEnum = createEEnum(YES_NO_TYPE);

		// Create data types
		activityDeliveryTypeObjectEDataType = createEDataType(ACTIVITY_DELIVERY_TYPE_OBJECT);
		activitySponsorshipTypeObjectEDataType = createEDataType(ACTIVITY_SPONSORSHIP_TYPE_OBJECT);
		audienceCategoryTypeObjectEDataType = createEDataType(AUDIENCE_CATEGORY_TYPE_OBJECT);
		creditTypeTypeObjectEDataType = createEDataType(CREDIT_TYPE_TYPE_OBJECT);
		creditUnitTypeObjectEDataType = createEDataType(CREDIT_UNIT_TYPE_OBJECT);
		medicalImageTypeTypeObjectEDataType = createEDataType(MEDICAL_IMAGE_TYPE_TYPE_OBJECT);
		orientationTypeObjectEDataType = createEDataType(ORIENTATION_TYPE_OBJECT);
		pacingTypeObjectEDataType = createEDataType(PACING_TYPE_OBJECT);
		participationModalityTypeObjectEDataType = createEDataType(PARTICIPATION_MODALITY_TYPE_OBJECT);
		specimenTypeTypeObjectEDataType = createEDataType(SPECIMEN_TYPE_TYPE_OBJECT);
		yesNoTypeObjectEDataType = createEDataType(YES_NO_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		AddressPackage theAddressPackage = (AddressPackage) EPackage.Registry.INSTANCE
				.getEPackage(AddressPackage.eNS_URI);
		LomPackage theLomPackage = (LomPackage) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		activityLocationTypeEClass.getESuperTypes().add(theAddressPackage.getAddressType());

		// Initialize classes, features, and operations; add parameters
		initEClass(activityLocationTypeEClass, ActivityLocationType.class, "ActivityLocationType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActivityLocationType_Room(), theLomPackage.getCharacterString(), "room", null, 0, 1,
				ActivityLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActivityLocationType_Building(), theLomPackage.getCharacterString(), "building", null, 0, 1,
				ActivityLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActivityLocationType_UniqueElementName(), theXMLTypePackage.getString(), "uniqueElementName",
				"activityLocation", 0, 1, ActivityLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(creditsTypeEClass, CreditsType.class, "CreditsType", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCreditsType_AccreditingBody(), theLomPackage.getCharacterString(), "accreditingBody", null, 0,
				1, CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_ActivityCertification(), theLomPackage.getCharacterString(),
				"activityCertification", null, 0, 1, CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_CreditType(), this.getCreditTypeType(), "creditType", null, 0, 1,
				CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_CreditUnit(), this.getCreditUnitType(), "creditUnit", null, 0, 1,
				CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_Pacing(), this.getPacingType(), "pacing", null, 0, 1, CreditsType.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_AccreditedProvider(), theLomPackage.getCharacterString(), "accreditedProvider",
				null, 0, 1, CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_NonAccreditedProvider(), theLomPackage.getCharacterString(),
				"nonAccreditedProvider", null, 0, -1, CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_ReleaseDate(), theXMLTypePackage.getDate(), "releaseDate", null, 0, 1,
				CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_ExpirationDate(), theXMLTypePackage.getDate(), "expirationDate", null, 0, 1,
				CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreditsType_NumberOfCredits(), theXMLTypePackage.getDecimal(), "numberOfCredits", null, 0, 1,
				CreditsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(customElementsTypeEClass, CustomElementsType.class, "CustomElementsType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomElementsType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, 1,
				CustomElementsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null,
				"xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null,
				"xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AccreditedProvider(), theLomPackage.getCharacterString(), "accreditedProvider",
				null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AccreditingBody(), theLomPackage.getCharacterString(), "accreditingBody", null,
				0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentRoot_ActivityCertification(), theLomPackage.getCharacterString(),
				"activityCertification", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_ActivityDelivery(), this.getActivityDeliveryType(), "activityDelivery", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEReference(getDocumentRoot_ActivityFormat(), theLomPackage.getLanguageString(), null, "activityFormat",
				null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ActivityLocation(), this.getActivityLocationType(), null, "activityLocation",
				null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_ActivitySponsorship(), this.getActivitySponsorshipType(), "activitySponsorship",
				null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Annotated(), this.getYesNoType(), "annotated", null, 0, -2, null, IS_TRANSIENT,
				IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AudienceCategory(), this.getAudienceCategoryType(), "audienceCategory", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentRoot_Building(), theLomPackage.getCharacterString(), "building", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ClinicalHistory(), theLomPackage.getLanguageString(), null, "clinicalHistory",
				null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CommercialSupport(), this.getYesNoType(), "commercialSupport", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_CommercialSupportAcknowledgement(), theLomPackage.getLanguageString(), null,
				"commercialSupportAcknowledgement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Contact(), theLomPackage.getCharacterString(), "contact", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Credits(), this.getCreditsType(), null, "credits", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CreditsAvailable(), this.getYesNoType(), "creditsAvailable", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CreditType(), this.getCreditTypeType(), "creditType", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CreditUnit(), this.getCreditUnitType(), "creditUnit", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_CustomElements(), this.getCustomElementsType(), null, "customElements", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_EndDateTime(), theXMLTypePackage.getDateTime(), "endDateTime", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_ExpirationDate(), theXMLTypePackage.getDate(), "expirationDate", null, 0, -2,
				null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentRoot_FileHeight(), theXMLTypePackage.getInteger(), "fileHeight", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_FileWidth(), theXMLTypePackage.getInteger(), "fileWidth", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HealthcareAsset(), this.getHealthcareAssetType(), null, "healthcareAsset", null,
				0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HealthcareEducation(), this.getHealthcareEducationType(), null,
				"healthcareEducation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HealthcareMetadata(), this.getHealthcareMetadataType(), null,
				"healthcareMetadata", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Magnification(), theXMLTypePackage.getInteger(), "magnification", null, 0, -2,
				null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentRoot_MedicalImageType(), this.getMedicalImageTypeType(), "medicalImageType", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentRoot_NonAccreditedProvider(), theLomPackage.getCharacterString(),
				"nonAccreditedProvider", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_NumberOfCredits(), theXMLTypePackage.getDecimal(), "numberOfCredits", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEReference(getDocumentRoot_OffLabelDescription(), theLomPackage.getLanguageString(), null,
				"offLabelDescription", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_OffLabelUse(), this.getYesNoType(), "offLabelUse", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Orientation(), this.getOrientationType(), "orientation", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Pacing(), this.getPacingType(), "pacing", null, 0, -2, null, IS_TRANSIENT,
				IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_ParticipationModality(), this.getParticipationModalityType(),
				"participationModality", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Profession(), theLomPackage.getLanguageString(), null, "profession", null, 0, -2,
				null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ReadingLevel(), theLomPackage.getLanguageString(), null, "readingLevel", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_ReleaseDate(), theXMLTypePackage.getDate(), "releaseDate", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_RelevantFinancialRelationship(), this.getYesNoType(),
				"relevantFinancialRelationship", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RelevantFinancialRelationshipDisclosure(), theLomPackage.getLanguageString(),
				null, "relevantFinancialRelationshipDisclosure", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Room(), theLomPackage.getCharacterString(), "room", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Specialty(), theLomPackage.getLanguageString(), null, "specialty", null, 0, -2,
				null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_SpecimenType(), this.getSpecimenTypeType(), "specimenType", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_StartDateTime(), theXMLTypePackage.getDateTime(), "startDateTime", null, 0, -2,
				null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEReference(getDocumentRoot_TargetAudience(), this.getTargetAudienceType(), null, "targetAudience", null, 0,
				-2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(healthcareAssetTypeEClass, HealthcareAssetType.class, "HealthcareAssetType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHealthcareAssetType_Annotated(), this.getYesNoType(), "annotated", null, 0, 1,
				HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareAssetType_ClinicalHistory(), theLomPackage.getLanguageString(), null,
				"clinicalHistory", null, 0, 1, HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_Magnification(), theXMLTypePackage.getInteger(), "magnification", null, 0,
				1, HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_Orientation(), this.getOrientationType(), "orientation", null, 0, 1,
				HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_MedicalImageType(), this.getMedicalImageTypeType(), "medicalImageType",
				null, 0, 1, HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_SpecimenType(), this.getSpecimenTypeType(), "specimenType", null, 0, 1,
				HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_FileHeight(), theXMLTypePackage.getInteger(), "fileHeight", null, 0, 1,
				HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_FileWidth(), theXMLTypePackage.getInteger(), "fileWidth", null, 0, 1,
				HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareAssetType_UniqueElementName(), theXMLTypePackage.getString(), "uniqueElementName",
				"healthcareAsset", 0, 1, HealthcareAssetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(healthcareEducationTypeEClass, HealthcareEducationType.class, "HealthcareEducationType",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHealthcareEducationType_ExpirationDate(), theXMLTypePackage.getDate(), "expirationDate", null,
				0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_CreditsAvailable(), this.getYesNoType(), "creditsAvailable", null, 0,
				1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_Credits(), this.getCreditsType(), null, "credits", null, 0, -1,
				HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_TargetAudience(), this.getTargetAudienceType(), null,
				"targetAudience", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_ActivityLocation(), this.getActivityLocationType(), null,
				"activityLocation", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_StartDateTime(), theXMLTypePackage.getDateTime(), "startDateTime",
				null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_EndDateTime(), theXMLTypePackage.getDateTime(), "endDateTime", null,
				0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_ActivitySponsorship(), this.getActivitySponsorshipType(),
				"activitySponsorship", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_ParticipationModality(), this.getParticipationModalityType(),
				"participationModality", null, 0, -1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_ActivityDelivery(), this.getActivityDeliveryType(),
				"activityDelivery", null, 0, -1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_ActivityFormat(), theLomPackage.getLanguageString(), null,
				"activityFormat", null, 0, -1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_OffLabelUse(), this.getYesNoType(), "offLabelUse", null, 0, 1,
				HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_OffLabelDescription(), theLomPackage.getLanguageString(), null,
				"offLabelDescription", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_CommercialSupport(), this.getYesNoType(), "commercialSupport", null,
				0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_CommercialSupportAcknowledgement(), theLomPackage.getLanguageString(),
				null, "commercialSupportAcknowledgement", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getHealthcareEducationType_RelevantFinancialRelationship(), this.getYesNoType(),
				"relevantFinancialRelationship", null, 0, 1, HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareEducationType_RelevantFinancialRelationshipDisclosure(),
				theLomPackage.getLanguageString(), null, "relevantFinancialRelationshipDisclosure", null, 0, -1,
				HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_Contact(), theLomPackage.getCharacterString(), "contact", null, 0, 1,
				HealthcareEducationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareEducationType_UniqueElementName(), theXMLTypePackage.getString(),
				"uniqueElementName", "healthcareEducation", 0, 1, HealthcareEducationType.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(healthcareMetadataTypeEClass, HealthcareMetadataType.class, "HealthcareMetadataType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHealthcareMetadataType_HealthcareEducation(), this.getHealthcareEducationType(), null,
				"healthcareEducation", null, 0, 1, HealthcareMetadataType.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHealthcareMetadataType_HealthcareAsset(), this.getHealthcareAssetType(), null,
				"healthcareAsset", null, 0, 1, HealthcareMetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthcareMetadataType_UniqueElementName(), theXMLTypePackage.getString(),
				"uniqueElementName", "healthcareMetadata", 0, 1, HealthcareMetadataType.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(targetAudienceTypeEClass, TargetAudienceType.class, "TargetAudienceType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTargetAudienceType_AudienceCategory(), this.getAudienceCategoryType(), "audienceCategory",
				null, 0, -1, TargetAudienceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTargetAudienceType_Profession(), theLomPackage.getLanguageString(), null, "profession", null,
				0, -1, TargetAudienceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTargetAudienceType_Specialty(), theLomPackage.getLanguageString(), null, "specialty", null, 0,
				-1, TargetAudienceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTargetAudienceType_ReadingLevel(), theLomPackage.getLanguageString(), null, "readingLevel",
				null, 0, -1, TargetAudienceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTargetAudienceType_UniqueElementName(), theXMLTypePackage.getString(), "uniqueElementName",
				"targetAudience", 0, 1, TargetAudienceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(activityDeliveryTypeEEnum, ActivityDeliveryType.class, "ActivityDeliveryType");
		addEEnumLiteral(activityDeliveryTypeEEnum, ActivityDeliveryType.LIVE);
		addEEnumLiteral(activityDeliveryTypeEEnum, ActivityDeliveryType.NOT_LIVE);

		initEEnum(activitySponsorshipTypeEEnum, ActivitySponsorshipType.class, "ActivitySponsorshipType");
		addEEnumLiteral(activitySponsorshipTypeEEnum, ActivitySponsorshipType.DIRECT);
		addEEnumLiteral(activitySponsorshipTypeEEnum, ActivitySponsorshipType.JOINT);

		initEEnum(audienceCategoryTypeEEnum, AudienceCategoryType.class, "AudienceCategoryType");
		addEEnumLiteral(audienceCategoryTypeEEnum, AudienceCategoryType.GENERAL);
		addEEnumLiteral(audienceCategoryTypeEEnum, AudienceCategoryType.PATIENT);
		addEEnumLiteral(audienceCategoryTypeEEnum, AudienceCategoryType.CAREGIVER);
		addEEnumLiteral(audienceCategoryTypeEEnum, AudienceCategoryType.PROFESSIONAL);

		initEEnum(creditTypeTypeEEnum, CreditTypeType.class, "CreditTypeType");
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CME);
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CE);
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CNE);
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CPE);
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CHES);
		addEEnumLiteral(creditTypeTypeEEnum, CreditTypeType.CPD);

		initEEnum(creditUnitTypeEEnum, CreditUnitType.class, "CreditUnitType");
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CECH);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CEH);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CEU);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.COGNATE);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CONTACT_HOUR);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CREDIT);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.HOUR);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.UNIT);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.CREDIT_HOUR);
		addEEnumLiteral(creditUnitTypeEEnum, CreditUnitType.POINT);

		initEEnum(medicalImageTypeTypeEEnum, MedicalImageTypeType.class, "MedicalImageTypeType");
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.ANGIOGRAM);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.COMPUTED_AXIAL_TOMOGRAPHY_SCAN);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.ELECTROCARDIOGRAM);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.ENDOSCOPIC_IMAGE);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.MAGNETIC_RESONANCE_IMAGE);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.MAMMOGRAM);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.MICROGRAPH);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.NUCLEAR_MEDICINE_SCAN);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.PHOTOGRAPH);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.RADIOGRAPH);
		addEEnumLiteral(medicalImageTypeTypeEEnum, MedicalImageTypeType.ULTRASOUND);

		initEEnum(orientationTypeEEnum, OrientationType.class, "OrientationType");
		addEEnumLiteral(orientationTypeEEnum, OrientationType.AXIAL);
		addEEnumLiteral(orientationTypeEEnum, OrientationType.CORONAL);
		addEEnumLiteral(orientationTypeEEnum, OrientationType.HORIZONTAL);
		addEEnumLiteral(orientationTypeEEnum, OrientationType.LONGITUDINAL);
		addEEnumLiteral(orientationTypeEEnum, OrientationType.SAGITTAL);
		addEEnumLiteral(orientationTypeEEnum, OrientationType.TRANSVERSE);

		initEEnum(pacingTypeEEnum, PacingType.class, "PacingType");
		addEEnumLiteral(pacingTypeEEnum, PacingType.LEARNER_PACED);
		addEEnumLiteral(pacingTypeEEnum, PacingType.PROVIDER_PACED);

		initEEnum(participationModalityTypeEEnum, ParticipationModalityType.class, "ParticipationModalityType");
		addEEnumLiteral(participationModalityTypeEEnum, ParticipationModalityType.CONFERENCE_WORKSHOP);
		addEEnumLiteral(participationModalityTypeEEnum, ParticipationModalityType.TECHNOLOGY_BASED);
		addEEnumLiteral(participationModalityTypeEEnum, ParticipationModalityType.ON_THE_JOB);
		addEEnumLiteral(participationModalityTypeEEnum, ParticipationModalityType.PRINT);

		initEEnum(specimenTypeTypeEEnum, SpecimenTypeType.class, "SpecimenTypeType");
		addEEnumLiteral(specimenTypeTypeEEnum, SpecimenTypeType.CELL);
		addEEnumLiteral(specimenTypeTypeEEnum, SpecimenTypeType.ORGAN);
		addEEnumLiteral(specimenTypeTypeEEnum, SpecimenTypeType.ORGAN_SYSTEM);
		addEEnumLiteral(specimenTypeTypeEEnum, SpecimenTypeType.ORGANELLE);
		addEEnumLiteral(specimenTypeTypeEEnum, SpecimenTypeType.TISSUE);

		initEEnum(yesNoTypeEEnum, YesNoType.class, "YesNoType");
		addEEnumLiteral(yesNoTypeEEnum, YesNoType.YES);
		addEEnumLiteral(yesNoTypeEEnum, YesNoType.NO);

		// Initialize data types
		initEDataType(activityDeliveryTypeObjectEDataType, ActivityDeliveryType.class, "ActivityDeliveryTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(activitySponsorshipTypeObjectEDataType, ActivitySponsorshipType.class,
				"ActivitySponsorshipTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(audienceCategoryTypeObjectEDataType, AudienceCategoryType.class, "AudienceCategoryTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(creditTypeTypeObjectEDataType, CreditTypeType.class, "CreditTypeTypeObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(creditUnitTypeObjectEDataType, CreditUnitType.class, "CreditUnitTypeObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(medicalImageTypeTypeObjectEDataType, MedicalImageTypeType.class, "MedicalImageTypeTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(orientationTypeObjectEDataType, OrientationType.class, "OrientationTypeObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pacingTypeObjectEDataType, PacingType.class, "PacingTypeObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(participationModalityTypeObjectEDataType, ParticipationModalityType.class,
				"ParticipationModalityTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(specimenTypeTypeObjectEDataType, SpecimenTypeType.class, "SpecimenTypeTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(yesNoTypeObjectEDataType, YesNoType.class, "YesNoTypeObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation(activityDeliveryTypeEEnum, source, new String[] { "name", "activityDeliveryType" });
		addAnnotation(activityDeliveryTypeObjectEDataType, source,
				new String[] { "name", "activityDeliveryType:Object", "baseType", "activityDeliveryType" });
		addAnnotation(activityLocationTypeEClass, source,
				new String[] { "name", "activityLocationType", "kind", "elementOnly" });
		addAnnotation(getActivityLocationType_Room(), source,
				new String[] { "kind", "element", "name", "room", "namespace", "##targetNamespace" });
		addAnnotation(getActivityLocationType_Building(), source,
				new String[] { "kind", "element", "name", "building", "namespace", "##targetNamespace" });
		addAnnotation(getActivityLocationType_UniqueElementName(), source,
				new String[] { "kind", "attribute", "name", "uniqueElementName" });
		addAnnotation(activitySponsorshipTypeEEnum, source, new String[] { "name", "activitySponsorshipType" });
		addAnnotation(activitySponsorshipTypeObjectEDataType, source,
				new String[] { "name", "activitySponsorshipType:Object", "baseType", "activitySponsorshipType" });
		addAnnotation(audienceCategoryTypeEEnum, source, new String[] { "name", "audienceCategoryType" });
		addAnnotation(audienceCategoryTypeObjectEDataType, source,
				new String[] { "name", "audienceCategoryType:Object", "baseType", "audienceCategoryType" });
		addAnnotation(creditsTypeEClass, source, new String[] { "name", "creditsType", "kind", "elementOnly" });
		addAnnotation(getCreditsType_AccreditingBody(), source,
				new String[] { "kind", "element", "name", "accreditingBody", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_ActivityCertification(), source,
				new String[] { "kind", "element", "name", "activityCertification", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_CreditType(), source,
				new String[] { "kind", "element", "name", "creditType", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_CreditUnit(), source,
				new String[] { "kind", "element", "name", "creditUnit", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_Pacing(), source,
				new String[] { "kind", "element", "name", "pacing", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_AccreditedProvider(), source,
				new String[] { "kind", "element", "name", "accreditedProvider", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_NonAccreditedProvider(), source,
				new String[] { "kind", "element", "name", "nonAccreditedProvider", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_ReleaseDate(), source,
				new String[] { "kind", "element", "name", "releaseDate", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_ExpirationDate(), source,
				new String[] { "kind", "element", "name", "expirationDate", "namespace", "##targetNamespace" });
		addAnnotation(getCreditsType_NumberOfCredits(), source,
				new String[] { "kind", "element", "name", "numberOfCredits", "namespace", "##targetNamespace" });
		addAnnotation(creditTypeTypeEEnum, source, new String[] { "name", "creditTypeType" });
		addAnnotation(creditTypeTypeObjectEDataType, source,
				new String[] { "name", "creditTypeType:Object", "baseType", "creditTypeType" });
		addAnnotation(creditUnitTypeEEnum, source, new String[] { "name", "creditUnitType" });
		addAnnotation(creditUnitTypeObjectEDataType, source,
				new String[] { "name", "creditUnitType:Object", "baseType", "creditUnitType" });
		addAnnotation(customElementsTypeEClass, source,
				new String[] { "name", "customElements_._type", "kind", "elementOnly" });
		addAnnotation(getCustomElementsType_Any(), source,
				new String[] { "kind", "elementWildcard", "wildcards", "##other", "name", ":0", "processing", "lax" });
		addAnnotation(documentRootEClass, source, new String[] { "name", "", "kind", "mixed" });
		addAnnotation(getDocumentRoot_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
		addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source,
				new String[] { "kind", "attribute", "name", "xmlns:prefix" });
		addAnnotation(getDocumentRoot_XSISchemaLocation(), source,
				new String[] { "kind", "attribute", "name", "xsi:schemaLocation" });
		addAnnotation(getDocumentRoot_AccreditedProvider(), source,
				new String[] { "kind", "element", "name", "accreditedProvider", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_AccreditingBody(), source,
				new String[] { "kind", "element", "name", "accreditingBody", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ActivityCertification(), source,
				new String[] { "kind", "element", "name", "activityCertification", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ActivityDelivery(), source,
				new String[] { "kind", "element", "name", "activityDelivery", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ActivityFormat(), source,
				new String[] { "kind", "element", "name", "activityFormat", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ActivityLocation(), source,
				new String[] { "kind", "element", "name", "activityLocation", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ActivitySponsorship(), source,
				new String[] { "kind", "element", "name", "activitySponsorship", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Annotated(), source,
				new String[] { "kind", "element", "name", "annotated", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_AudienceCategory(), source,
				new String[] { "kind", "element", "name", "audienceCategory", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Building(), source,
				new String[] { "kind", "element", "name", "building", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ClinicalHistory(), source,
				new String[] { "kind", "element", "name", "clinicalHistory", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CommercialSupport(), source,
				new String[] { "kind", "element", "name", "commercialSupport", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CommercialSupportAcknowledgement(), source, new String[] { "kind", "element",
				"name", "commercialSupportAcknowledgement", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Contact(), source,
				new String[] { "kind", "element", "name", "contact", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Credits(), source,
				new String[] { "kind", "element", "name", "credits", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CreditsAvailable(), source,
				new String[] { "kind", "element", "name", "creditsAvailable", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CreditType(), source,
				new String[] { "kind", "element", "name", "creditType", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CreditUnit(), source,
				new String[] { "kind", "element", "name", "creditUnit", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_CustomElements(), source,
				new String[] { "kind", "element", "name", "customElements", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_EndDateTime(), source,
				new String[] { "kind", "element", "name", "endDateTime", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ExpirationDate(), source,
				new String[] { "kind", "element", "name", "expirationDate", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_FileHeight(), source,
				new String[] { "kind", "element", "name", "fileHeight", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_FileWidth(), source,
				new String[] { "kind", "element", "name", "fileWidth", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_HealthcareAsset(), source,
				new String[] { "kind", "element", "name", "healthcareAsset", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_HealthcareEducation(), source,
				new String[] { "kind", "element", "name", "healthcareEducation", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_HealthcareMetadata(), source,
				new String[] { "kind", "element", "name", "healthcareMetadata", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Magnification(), source,
				new String[] { "kind", "element", "name", "magnification", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_MedicalImageType(), source,
				new String[] { "kind", "element", "name", "medicalImageType", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_NonAccreditedProvider(), source,
				new String[] { "kind", "element", "name", "nonAccreditedProvider", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_NumberOfCredits(), source,
				new String[] { "kind", "element", "name", "numberOfCredits", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_OffLabelDescription(), source,
				new String[] { "kind", "element", "name", "offLabelDescription", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_OffLabelUse(), source,
				new String[] { "kind", "element", "name", "offLabelUse", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Orientation(), source,
				new String[] { "kind", "element", "name", "orientation", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Pacing(), source,
				new String[] { "kind", "element", "name", "pacing", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ParticipationModality(), source,
				new String[] { "kind", "element", "name", "participationModality", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Profession(), source,
				new String[] { "kind", "element", "name", "profession", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ReadingLevel(), source,
				new String[] { "kind", "element", "name", "readingLevel", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_ReleaseDate(), source,
				new String[] { "kind", "element", "name", "releaseDate", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_RelevantFinancialRelationship(), source, new String[] { "kind", "element", "name",
				"relevantFinancialRelationship", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_RelevantFinancialRelationshipDisclosure(), source, new String[] { "kind",
				"element", "name", "relevantFinancialRelationshipDisclosure", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Room(), source,
				new String[] { "kind", "element", "name", "room", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_Specialty(), source,
				new String[] { "kind", "element", "name", "specialty", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_SpecimenType(), source,
				new String[] { "kind", "element", "name", "specimenType", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_StartDateTime(), source,
				new String[] { "kind", "element", "name", "startDateTime", "namespace", "##targetNamespace" });
		addAnnotation(getDocumentRoot_TargetAudience(), source,
				new String[] { "kind", "element", "name", "targetAudience", "namespace", "##targetNamespace" });
		addAnnotation(healthcareAssetTypeEClass, source,
				new String[] { "name", "healthcareAssetType", "kind", "elementOnly" });
		addAnnotation(getHealthcareAssetType_Annotated(), source,
				new String[] { "kind", "element", "name", "annotated", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_ClinicalHistory(), source,
				new String[] { "kind", "element", "name", "clinicalHistory", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_Magnification(), source,
				new String[] { "kind", "element", "name", "magnification", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_Orientation(), source,
				new String[] { "kind", "element", "name", "orientation", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_MedicalImageType(), source,
				new String[] { "kind", "element", "name", "medicalImageType", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_SpecimenType(), source,
				new String[] { "kind", "element", "name", "specimenType", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_FileHeight(), source,
				new String[] { "kind", "element", "name", "fileHeight", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_FileWidth(), source,
				new String[] { "kind", "element", "name", "fileWidth", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareAssetType_UniqueElementName(), source,
				new String[] { "kind", "attribute", "name", "uniqueElementName" });
		addAnnotation(healthcareEducationTypeEClass, source,
				new String[] { "name", "healthcareEducationType", "kind", "elementOnly" });
		addAnnotation(getHealthcareEducationType_ExpirationDate(), source,
				new String[] { "kind", "element", "name", "expirationDate", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_CreditsAvailable(), source,
				new String[] { "kind", "element", "name", "creditsAvailable", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_Credits(), source,
				new String[] { "kind", "element", "name", "credits", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_TargetAudience(), source,
				new String[] { "kind", "element", "name", "targetAudience", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_ActivityLocation(), source,
				new String[] { "kind", "element", "name", "activityLocation", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_StartDateTime(), source,
				new String[] { "kind", "element", "name", "startDateTime", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_EndDateTime(), source,
				new String[] { "kind", "element", "name", "endDateTime", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_ActivitySponsorship(), source,
				new String[] { "kind", "element", "name", "activitySponsorship", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_ParticipationModality(), source,
				new String[] { "kind", "element", "name", "participationModality", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_ActivityDelivery(), source,
				new String[] { "kind", "element", "name", "activityDelivery", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_ActivityFormat(), source,
				new String[] { "kind", "element", "name", "activityFormat", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_OffLabelUse(), source,
				new String[] { "kind", "element", "name", "offLabelUse", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_OffLabelDescription(), source,
				new String[] { "kind", "element", "name", "offLabelDescription", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_CommercialSupport(), source,
				new String[] { "kind", "element", "name", "commercialSupport", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_CommercialSupportAcknowledgement(), source, new String[] { "kind",
				"element", "name", "commercialSupportAcknowledgement", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_RelevantFinancialRelationship(), source, new String[] { "kind",
				"element", "name", "relevantFinancialRelationship", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_RelevantFinancialRelationshipDisclosure(), source,
				new String[] { "kind", "element", "name", "relevantFinancialRelationshipDisclosure", "namespace",
						"##targetNamespace" });
		addAnnotation(getHealthcareEducationType_Contact(), source,
				new String[] { "kind", "element", "name", "contact", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareEducationType_UniqueElementName(), source,
				new String[] { "kind", "attribute", "name", "uniqueElementName" });
		addAnnotation(healthcareMetadataTypeEClass, source,
				new String[] { "name", "healthcareMetadataType", "kind", "elementOnly" });
		addAnnotation(getHealthcareMetadataType_HealthcareEducation(), source,
				new String[] { "kind", "element", "name", "healthcareEducation", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareMetadataType_HealthcareAsset(), source,
				new String[] { "kind", "element", "name", "healthcareAsset", "namespace", "##targetNamespace" });
		addAnnotation(getHealthcareMetadataType_UniqueElementName(), source,
				new String[] { "kind", "attribute", "name", "uniqueElementName" });
		addAnnotation(medicalImageTypeTypeEEnum, source, new String[] { "name", "medicalImageTypeType" });
		addAnnotation(medicalImageTypeTypeObjectEDataType, source,
				new String[] { "name", "medicalImageTypeType:Object", "baseType", "medicalImageTypeType" });
		addAnnotation(orientationTypeEEnum, source, new String[] { "name", "orientationType" });
		addAnnotation(orientationTypeObjectEDataType, source,
				new String[] { "name", "orientationType:Object", "baseType", "orientationType" });
		addAnnotation(pacingTypeEEnum, source, new String[] { "name", "pacingType" });
		addAnnotation(pacingTypeObjectEDataType, source,
				new String[] { "name", "pacingType:Object", "baseType", "pacingType" });
		addAnnotation(participationModalityTypeEEnum, source, new String[] { "name", "participationModalityType" });
		addAnnotation(participationModalityTypeObjectEDataType, source,
				new String[] { "name", "participationModalityType:Object", "baseType", "participationModalityType" });
		addAnnotation(specimenTypeTypeEEnum, source, new String[] { "name", "specimenTypeType" });
		addAnnotation(specimenTypeTypeObjectEDataType, source,
				new String[] { "name", "specimenTypeType:Object", "baseType", "specimenTypeType" });
		addAnnotation(targetAudienceTypeEClass, source,
				new String[] { "name", "targetAudienceType", "kind", "elementOnly" });
		addAnnotation(getTargetAudienceType_AudienceCategory(), source,
				new String[] { "kind", "element", "name", "audienceCategory", "namespace", "##targetNamespace" });
		addAnnotation(getTargetAudienceType_Profession(), source,
				new String[] { "kind", "element", "name", "profession", "namespace", "##targetNamespace" });
		addAnnotation(getTargetAudienceType_Specialty(), source,
				new String[] { "kind", "element", "name", "specialty", "namespace", "##targetNamespace" });
		addAnnotation(getTargetAudienceType_ReadingLevel(), source,
				new String[] { "kind", "element", "name", "readingLevel", "namespace", "##targetNamespace" });
		addAnnotation(getTargetAudienceType_UniqueElementName(), source,
				new String[] { "kind", "attribute", "name", "uniqueElementName" });
		addAnnotation(yesNoTypeEEnum, source, new String[] { "name", "yesNoType" });
		addAnnotation(yesNoTypeObjectEDataType, source,
				new String[] { "name", "yesNoType:Object", "baseType", "yesNoType" });
	}

} //HealthcarePackageImpl
