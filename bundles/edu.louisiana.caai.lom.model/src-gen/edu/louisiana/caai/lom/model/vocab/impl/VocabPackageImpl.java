/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.vocab.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;

import edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl;

import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl;

import edu.louisiana.caai.lom.model.lom.LomPackage;

import edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl;

import edu.louisiana.caai.lom.model.vocab.VocabFactory;
import edu.louisiana.caai.lom.model.vocab.VocabPackage;

import edu.louisiana.caai.lom.model.vocab.util.VocabValidator;

import org.eclipse.emf.common.util.Enumerator;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VocabPackageImpl extends EPackageImpl implements VocabPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType aggregationLevelEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType contextEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType copyrightAndOtherRestrictionsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType costEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType difficultyEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType intendedEndUserRoleEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType interactivityLevelEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType interactivityTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType kindEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType learningResourceTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType purposeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType roleEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType roleMetaEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType semanticDensityEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sourceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType statusEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType structureEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeEDataType = null;

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
	 * @see edu.louisiana.caai.lom.model.vocab.VocabPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VocabPackageImpl() {
		super(eNS_URI, VocabFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link VocabPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VocabPackage init() {
		if (isInited)
			return (VocabPackage) EPackage.Registry.INSTANCE.getEPackage(VocabPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredVocabPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		VocabPackageImpl theVocabPackage = registeredVocabPackage instanceof VocabPackageImpl
				? (VocabPackageImpl) registeredVocabPackage
				: new VocabPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarevocabPackage.eNS_URI);
		HealthcarevocabPackageImpl theHealthcarevocabPackage = (HealthcarevocabPackageImpl) (registeredPackage instanceof HealthcarevocabPackageImpl
				? registeredPackage
				: HealthcarevocabPackage.eINSTANCE);

		// Load packages
		theLomPackage.loadPackage();

		// Create package meta-data objects
		theVocabPackage.createPackageContents();
		theHealthcarePackage.createPackageContents();
		theAddressPackage.createPackageContents();
		theHealthcarevocabPackage.createPackageContents();

		// Initialize created meta-data
		theVocabPackage.initializePackageContents();
		theHealthcarePackage.initializePackageContents();
		theAddressPackage.initializePackageContents();
		theHealthcarevocabPackage.initializePackageContents();

		// Fix loaded packages
		theLomPackage.fixPackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theVocabPackage, new EValidator.Descriptor() {
			@Override
			public EValidator getEValidator() {
				return VocabValidator.INSTANCE;
			}
		});

		// Mark meta-data to indicate it can't be changed
		theVocabPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VocabPackage.eNS_URI, theVocabPackage);
		return theVocabPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getAggregationLevel() {
		return aggregationLevelEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getContext() {
		return contextEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCopyrightAndOtherRestrictions() {
		return copyrightAndOtherRestrictionsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCost() {
		return costEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDifficulty() {
		return difficultyEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIntendedEndUserRole() {
		return intendedEndUserRoleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getInteractivityLevel() {
		return interactivityLevelEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getInteractivityType() {
		return interactivityTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getKind() {
		return kindEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getLearningResourceType() {
		return learningResourceTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getName_() {
		return nameEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPurpose() {
		return purposeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRole() {
		return roleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRoleMeta() {
		return roleMetaEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSemanticDensity() {
		return semanticDensityEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSource() {
		return sourceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getStatus() {
		return statusEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getStructure() {
		return structureEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getType() {
		return typeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VocabFactory getVocabFactory() {
		return (VocabFactory) getEFactoryInstance();
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

		// Create data types
		aggregationLevelEDataType = createEDataType(AGGREGATION_LEVEL);
		contextEDataType = createEDataType(CONTEXT);
		copyrightAndOtherRestrictionsEDataType = createEDataType(COPYRIGHT_AND_OTHER_RESTRICTIONS);
		costEDataType = createEDataType(COST);
		difficultyEDataType = createEDataType(DIFFICULTY);
		intendedEndUserRoleEDataType = createEDataType(INTENDED_END_USER_ROLE);
		interactivityLevelEDataType = createEDataType(INTERACTIVITY_LEVEL);
		interactivityTypeEDataType = createEDataType(INTERACTIVITY_TYPE);
		kindEDataType = createEDataType(KIND);
		learningResourceTypeEDataType = createEDataType(LEARNING_RESOURCE_TYPE);
		nameEDataType = createEDataType(NAME);
		purposeEDataType = createEDataType(PURPOSE);
		roleEDataType = createEDataType(ROLE);
		roleMetaEDataType = createEDataType(ROLE_META);
		semanticDensityEDataType = createEDataType(SEMANTIC_DENSITY);
		sourceEDataType = createEDataType(SOURCE);
		statusEDataType = createEDataType(STATUS);
		structureEDataType = createEDataType(STRUCTURE);
		typeEDataType = createEDataType(TYPE);
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

		// Initialize data types
		initEDataType(aggregationLevelEDataType, Enumerator.class, "AggregationLevel", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(contextEDataType, Enumerator.class, "Context", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(copyrightAndOtherRestrictionsEDataType, Enumerator.class, "CopyrightAndOtherRestrictions",
				IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(costEDataType, Enumerator.class, "Cost", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(difficultyEDataType, Enumerator.class, "Difficulty", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(intendedEndUserRoleEDataType, Enumerator.class, "IntendedEndUserRole", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(interactivityLevelEDataType, Enumerator.class, "InteractivityLevel", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(interactivityTypeEDataType, Enumerator.class, "InteractivityType", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(kindEDataType, Enumerator.class, "Kind", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(learningResourceTypeEDataType, Enumerator.class, "LearningResourceType", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameEDataType, Enumerator.class, "Name", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(purposeEDataType, Enumerator.class, "Purpose", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(roleEDataType, Enumerator.class, "Role", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(roleMetaEDataType, Enumerator.class, "RoleMeta", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(semanticDensityEDataType, Enumerator.class, "SemanticDensity", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sourceEDataType, Enumerator.class, "Source", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(statusEDataType, Enumerator.class, "Status", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(structureEDataType, Enumerator.class, "Structure", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(typeEDataType, Enumerator.class, "Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
		addAnnotation(aggregationLevelEDataType, source, new String[] { "name", "aggregationLevel", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#aggregationLevelValues" });
		addAnnotation(contextEDataType, source, new String[] { "name", "context", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#contextValues http://ns.medbiq.org/lom/vocab/v1/#contextValues" });
		addAnnotation(copyrightAndOtherRestrictionsEDataType, source,
				new String[] { "name", "copyrightAndOtherRestrictions", "memberTypes",
						"http://ltsc.ieee.org/xsd/LOM#copyrightAndOtherRestrictionsValues" });
		addAnnotation(costEDataType, source,
				new String[] { "name", "cost", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#costValues" });
		addAnnotation(difficultyEDataType, source,
				new String[] { "name", "difficulty", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#difficultyValues" });
		addAnnotation(intendedEndUserRoleEDataType, source, new String[] { "name", "intendedEndUserRole", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#intendedEndUserRoleValues" });
		addAnnotation(interactivityLevelEDataType, source, new String[] { "name", "interactivityLevel", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#interactivityLevelValues" });
		addAnnotation(interactivityTypeEDataType, source, new String[] { "name", "interactivityType", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#interactivityTypeValues" });
		addAnnotation(kindEDataType, source,
				new String[] { "name", "kind", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#kindValues" });
		addAnnotation(learningResourceTypeEDataType, source, new String[] { "name", "learningResourceType",
				"memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#learningResourceTypeValues http://ns.medbiq.org/lom/vocab/v1/#learningResourceTypeValues" });
		addAnnotation(nameEDataType, source,
				new String[] { "name", "name", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#nameValues" });
		addAnnotation(purposeEDataType, source, new String[] { "name", "purpose", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#purposeValues http://ns.medbiq.org/lom/vocab/v1/#purposeValues" });
		addAnnotation(roleEDataType, source, new String[] { "name", "role", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#roleValues http://ns.medbiq.org/lom/vocab/v1/#roleValues" });
		addAnnotation(roleMetaEDataType, source,
				new String[] { "name", "roleMeta", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#roleMetaValues" });
		addAnnotation(semanticDensityEDataType, source, new String[] { "name", "semanticDensity", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#semanticDensityValues" });
		addAnnotation(sourceEDataType, source, new String[] { "name", "source", "memberTypes",
				"http://ltsc.ieee.org/xsd/LOM#sourceValues http://ns.medbiq.org/lom/vocab/v1/#sourceValues" });
		addAnnotation(statusEDataType, source,
				new String[] { "name", "status", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#statusValues" });
		addAnnotation(structureEDataType, source,
				new String[] { "name", "structure", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#structureValues" });
		addAnnotation(typeEDataType, source,
				new String[] { "name", "type", "memberTypes", "http://ltsc.ieee.org/xsd/LOM#typeValues" });
	}

} //VocabPackageImpl
