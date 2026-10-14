/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcarevocab.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;

import edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl;

import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl;

import edu.louisiana.caai.lom.model.healthcarevocab.ContextValues;
import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabFactory;
import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;
import edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues;
import edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues;
import edu.louisiana.caai.lom.model.healthcarevocab.RoleValues;
import edu.louisiana.caai.lom.model.healthcarevocab.SourceValues;

import edu.louisiana.caai.lom.model.lom.LomPackage;

import edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl;

import edu.louisiana.caai.lom.model.vocab.VocabPackage;

import edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HealthcarevocabPackageImpl extends EPackageImpl implements HealthcarevocabPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum contextValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum learningResourceTypeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum purposeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum roleValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sourceValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType contextValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType learningResourceTypeValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType purposeValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType roleValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sourceValuesObjectEDataType = null;

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
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private HealthcarevocabPackageImpl() {
		super(eNS_URI, HealthcarevocabFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link HealthcarevocabPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static HealthcarevocabPackage init() {
		if (isInited)
			return (HealthcarevocabPackage) EPackage.Registry.INSTANCE.getEPackage(HealthcarevocabPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredHealthcarevocabPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		HealthcarevocabPackageImpl theHealthcarevocabPackage = registeredHealthcarevocabPackage instanceof HealthcarevocabPackageImpl
				? (HealthcarevocabPackageImpl) registeredHealthcarevocabPackage
				: new HealthcarevocabPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarePackage.eNS_URI);
		HealthcarePackageImpl theHealthcarePackage = (HealthcarePackageImpl) (registeredPackage instanceof HealthcarePackageImpl
				? registeredPackage
				: HealthcarePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI);
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

		// Load packages
		theLomPackage.loadPackage();

		// Create package meta-data objects
		theHealthcarevocabPackage.createPackageContents();
		theHealthcarePackage.createPackageContents();
		theAddressPackage.createPackageContents();
		theVocabPackage.createPackageContents();

		// Initialize created meta-data
		theHealthcarevocabPackage.initializePackageContents();
		theHealthcarePackage.initializePackageContents();
		theAddressPackage.initializePackageContents();
		theVocabPackage.initializePackageContents();

		// Fix loaded packages
		theLomPackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theHealthcarevocabPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(HealthcarevocabPackage.eNS_URI, theHealthcarevocabPackage);
		return theHealthcarevocabPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getContextValues() {
		return contextValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLearningResourceTypeValues() {
		return learningResourceTypeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPurposeValues() {
		return purposeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRoleValues() {
		return roleValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSourceValues() {
		return sourceValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getContextValuesObject() {
		return contextValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getLearningResourceTypeValuesObject() {
		return learningResourceTypeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPurposeValuesObject() {
		return purposeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRoleValuesObject() {
		return roleValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSourceValuesObject() {
		return sourceValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcarevocabFactory getHealthcarevocabFactory() {
		return (HealthcarevocabFactory) getEFactoryInstance();
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

		// Create enums
		contextValuesEEnum = createEEnum(CONTEXT_VALUES);
		learningResourceTypeValuesEEnum = createEEnum(LEARNING_RESOURCE_TYPE_VALUES);
		purposeValuesEEnum = createEEnum(PURPOSE_VALUES);
		roleValuesEEnum = createEEnum(ROLE_VALUES);
		sourceValuesEEnum = createEEnum(SOURCE_VALUES);

		// Create data types
		contextValuesObjectEDataType = createEDataType(CONTEXT_VALUES_OBJECT);
		learningResourceTypeValuesObjectEDataType = createEDataType(LEARNING_RESOURCE_TYPE_VALUES_OBJECT);
		purposeValuesObjectEDataType = createEDataType(PURPOSE_VALUES_OBJECT);
		roleValuesObjectEDataType = createEDataType(ROLE_VALUES_OBJECT);
		sourceValuesObjectEDataType = createEDataType(SOURCE_VALUES_OBJECT);
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

		// Initialize enums and add enum literals
		initEEnum(contextValuesEEnum, ContextValues.class, "ContextValues");
		addEEnumLiteral(contextValuesEEnum, ContextValues.PATIENT_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.CAREGIVER_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.PRIMARY_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.SECONDARY_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.VOCATIONAL_TRAINING);
		addEEnumLiteral(contextValuesEEnum, ContextValues.UNDERGRADUATE_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.UNDERGRADUATE_PROFESSIONAL_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.GRADUATE_PROFESSIONAL_EDUCATION);
		addEEnumLiteral(contextValuesEEnum, ContextValues.CONTINUING_PROFESSIONAL_DEVELOPMENT);

		initEEnum(learningResourceTypeValuesEEnum, LearningResourceTypeValues.class, "LearningResourceTypeValues");
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.ANIMATION);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.AUDIO);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.CASE_STUDY);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.COLLABORATIVE_FORUM);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.GAME);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.IMAGE);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.REFERENCE);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.TUTORIAL);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.VIDEO);
		addEEnumLiteral(learningResourceTypeValuesEEnum, LearningResourceTypeValues.VIRTUAL_PATIENT);

		initEEnum(purposeValuesEEnum, PurposeValues.class, "PurposeValues");
		addEEnumLiteral(purposeValuesEEnum, PurposeValues.LEARNING_OUTCOME);
		addEEnumLiteral(purposeValuesEEnum, PurposeValues.CLINICAL_GUIDELINE);
		addEEnumLiteral(purposeValuesEEnum, PurposeValues.DRUG_LIST);
		addEEnumLiteral(purposeValuesEEnum, PurposeValues.LEVEL_OF_EVIDENCE);

		initEEnum(roleValuesEEnum, RoleValues.class, "RoleValues");
		addEEnumLiteral(roleValuesEEnum, RoleValues.OTHER);
		addEEnumLiteral(roleValuesEEnum, RoleValues.REVIEWER);
		addEEnumLiteral(roleValuesEEnum, RoleValues.PROGRAMMER);
		addEEnumLiteral(roleValuesEEnum, RoleValues.PRODUCER);
		addEEnumLiteral(roleValuesEEnum, RoleValues.DIRECTOR);

		initEEnum(sourceValuesEEnum, SourceValues.class, "SourceValues");
		addEEnumLiteral(sourceValuesEEnum, SourceValues.HEALTHCARELO_MV1);

		// Initialize data types
		initEDataType(contextValuesObjectEDataType, ContextValues.class, "ContextValuesObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(learningResourceTypeValuesObjectEDataType, LearningResourceTypeValues.class,
				"LearningResourceTypeValuesObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(purposeValuesObjectEDataType, PurposeValues.class, "PurposeValuesObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(roleValuesObjectEDataType, RoleValues.class, "RoleValuesObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sourceValuesObjectEDataType, SourceValues.class, "SourceValuesObject", IS_SERIALIZABLE,
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
		addAnnotation(contextValuesEEnum, source, new String[] { "name", "contextValues" });
		addAnnotation(contextValuesObjectEDataType, source,
				new String[] { "name", "contextValues:Object", "baseType", "contextValues" });
		addAnnotation(learningResourceTypeValuesEEnum, source, new String[] { "name", "learningResourceTypeValues" });
		addAnnotation(learningResourceTypeValuesObjectEDataType, source,
				new String[] { "name", "learningResourceTypeValues:Object", "baseType", "learningResourceTypeValues" });
		addAnnotation(purposeValuesEEnum, source, new String[] { "name", "purposeValues" });
		addAnnotation(purposeValuesObjectEDataType, source,
				new String[] { "name", "purposeValues:Object", "baseType", "purposeValues" });
		addAnnotation(roleValuesEEnum, source, new String[] { "name", "roleValues" });
		addAnnotation(roleValuesObjectEDataType, source,
				new String[] { "name", "roleValues:Object", "baseType", "roleValues" });
		addAnnotation(sourceValuesEEnum, source, new String[] { "name", "sourceValues" });
		addAnnotation(sourceValuesObjectEDataType, source,
				new String[] { "name", "sourceValues:Object", "baseType", "sourceValues" });
	}

} //HealthcarevocabPackageImpl
