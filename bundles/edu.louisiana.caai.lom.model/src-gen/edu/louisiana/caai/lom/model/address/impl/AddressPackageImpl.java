/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address.impl;

import edu.louisiana.caai.lom.model.address.AddressCategoryType;
import edu.louisiana.caai.lom.model.address.AddressFactory;
import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.address.AddressType;
import edu.louisiana.caai.lom.model.address.CountryType;
import edu.louisiana.caai.lom.model.address.DocumentRoot;
import edu.louisiana.caai.lom.model.address.RestrictionsType;
import edu.louisiana.caai.lom.model.address.StreetAddressLineType;

import edu.louisiana.caai.lom.model.address.util.AddressValidator;

import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl;

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
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddressPackageImpl extends EPackageImpl implements AddressPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addressTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass streetAddressLineTypeEClass = null;

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
	private EEnum addressCategoryTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum restrictionsTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType addressCategoryTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nonNullStringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType restrictionsTypeObjectEDataType = null;

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
	 * @see edu.louisiana.caai.lom.model.address.AddressPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AddressPackageImpl() {
		super(eNS_URI, AddressFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AddressPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AddressPackage init() {
		if (isInited)
			return (AddressPackage) EPackage.Registry.INSTANCE.getEPackage(AddressPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAddressPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AddressPackageImpl theAddressPackage = registeredAddressPackage instanceof AddressPackageImpl
				? (AddressPackageImpl) registeredAddressPackage
				: new AddressPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarePackage.eNS_URI);
		HealthcarePackageImpl theHealthcarePackage = (HealthcarePackageImpl) (registeredPackage instanceof HealthcarePackageImpl
				? registeredPackage
				: HealthcarePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI);
		LomPackageImpl theLomPackage = (LomPackageImpl) (registeredPackage instanceof LomPackageImpl ? registeredPackage
				: LomPackage.eINSTANCE);
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
		theAddressPackage.createPackageContents();
		theHealthcarePackage.createPackageContents();
		theVocabPackage.createPackageContents();
		theHealthcarevocabPackage.createPackageContents();

		// Initialize created meta-data
		theAddressPackage.initializePackageContents();
		theHealthcarePackage.initializePackageContents();
		theVocabPackage.initializePackageContents();
		theHealthcarevocabPackage.initializePackageContents();

		// Fix loaded packages
		theLomPackage.fixPackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theAddressPackage, new EValidator.Descriptor() {
			@Override
			public EValidator getEValidator() {
				return AddressValidator.INSTANCE;
			}
		});

		// Mark meta-data to indicate it can't be changed
		theAddressPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AddressPackage.eNS_URI, theAddressPackage);
		return theAddressPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAddressType() {
		return addressTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_ID() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_Organization() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAddressType_StreetAddressLine() {
		return (EReference) addressTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_City() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_StateOrProvince() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_PostalCode() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_Region() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_District() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAddressType_Country() {
		return (EReference) addressTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_AddressCategory() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddressType_Restrictions() {
		return (EAttribute) addressTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCountryType() {
		return countryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCountryType_CountryName() {
		return (EAttribute) countryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCountryType_CountryCode() {
		return (EAttribute) countryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStreetAddressLineType() {
		return streetAddressLineTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreetAddressLineType_Value() {
		return (EAttribute) streetAddressLineTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStreetAddressLineType_Restrictions() {
		return (EAttribute) streetAddressLineTypeEClass.getEStructuralFeatures().get(1);
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
	public EReference getDocumentRoot_Address() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAddressCategoryType() {
		return addressCategoryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRestrictionsType() {
		return restrictionsTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getAddressCategoryTypeObject() {
		return addressCategoryTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getNonNullString() {
		return nonNullStringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRestrictionsTypeObject() {
		return restrictionsTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressFactory getAddressFactory() {
		return (AddressFactory) getEFactoryInstance();
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
		addressTypeEClass = createEClass(ADDRESS_TYPE);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__ID);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__ORGANIZATION);
		createEReference(addressTypeEClass, ADDRESS_TYPE__STREET_ADDRESS_LINE);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__CITY);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__STATE_OR_PROVINCE);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__POSTAL_CODE);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__REGION);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__DISTRICT);
		createEReference(addressTypeEClass, ADDRESS_TYPE__COUNTRY);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__ADDRESS_CATEGORY);
		createEAttribute(addressTypeEClass, ADDRESS_TYPE__RESTRICTIONS);

		countryTypeEClass = createEClass(COUNTRY_TYPE);
		createEAttribute(countryTypeEClass, COUNTRY_TYPE__COUNTRY_NAME);
		createEAttribute(countryTypeEClass, COUNTRY_TYPE__COUNTRY_CODE);

		streetAddressLineTypeEClass = createEClass(STREET_ADDRESS_LINE_TYPE);
		createEAttribute(streetAddressLineTypeEClass, STREET_ADDRESS_LINE_TYPE__VALUE);
		createEAttribute(streetAddressLineTypeEClass, STREET_ADDRESS_LINE_TYPE__RESTRICTIONS);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ADDRESS);

		// Create enums
		addressCategoryTypeEEnum = createEEnum(ADDRESS_CATEGORY_TYPE);
		restrictionsTypeEEnum = createEEnum(RESTRICTIONS_TYPE);

		// Create data types
		addressCategoryTypeObjectEDataType = createEDataType(ADDRESS_CATEGORY_TYPE_OBJECT);
		nonNullStringEDataType = createEDataType(NON_NULL_STRING);
		restrictionsTypeObjectEDataType = createEDataType(RESTRICTIONS_TYPE_OBJECT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(addressTypeEClass, AddressType.class, "AddressType", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAddressType_ID(), this.getNonNullString(), "iD", null, 0, 1, AddressType.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_Organization(), this.getNonNullString(), "organization", null, 0, -1,
				AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getAddressType_StreetAddressLine(), this.getStreetAddressLineType(), null, "streetAddressLine",
				null, 0, -1, AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_City(), this.getNonNullString(), "city", null, 0, 1, AddressType.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_StateOrProvince(), this.getNonNullString(), "stateOrProvince", null, 0, 1,
				AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_PostalCode(), this.getNonNullString(), "postalCode", null, 0, 1,
				AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_Region(), this.getNonNullString(), "region", null, 0, 1, AddressType.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_District(), this.getNonNullString(), "district", null, 0, 1, AddressType.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddressType_Country(), this.getCountryType(), null, "country", null, 1, 1, AddressType.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_AddressCategory(), this.getAddressCategoryType(), "addressCategory", null, 0, 1,
				AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressType_Restrictions(), this.getRestrictionsType(), "restrictions", null, 0, 1,
				AddressType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(countryTypeEClass, CountryType.class, "CountryType", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCountryType_CountryName(), this.getNonNullString(), "countryName", null, 0, 1,
				CountryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCountryType_CountryCode(), this.getNonNullString(), "countryCode", null, 0, 1,
				CountryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(streetAddressLineTypeEClass, StreetAddressLineType.class, "StreetAddressLineType", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStreetAddressLineType_Value(), this.getNonNullString(), "value", null, 0, 1,
				StreetAddressLineType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStreetAddressLineType_Restrictions(), this.getRestrictionsType(), "restrictions", null, 0, 1,
				StreetAddressLineType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		initEReference(getDocumentRoot_Address(), this.getAddressType(), null, "address", null, 0, -2, null,
				IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(addressCategoryTypeEEnum, AddressCategoryType.class, "AddressCategoryType");
		addEEnumLiteral(addressCategoryTypeEEnum, AddressCategoryType.RESIDENTIAL);
		addEEnumLiteral(addressCategoryTypeEEnum, AddressCategoryType.BUSINESS);
		addEEnumLiteral(addressCategoryTypeEEnum, AddressCategoryType.UNDELIVERABLE);

		initEEnum(restrictionsTypeEEnum, RestrictionsType.class, "RestrictionsType");
		addEEnumLiteral(restrictionsTypeEEnum, RestrictionsType.UNRESTRICTED);
		addEEnumLiteral(restrictionsTypeEEnum, RestrictionsType.RESTRICTED);
		addEEnumLiteral(restrictionsTypeEEnum, RestrictionsType.CONFIDENTIAL);

		// Initialize data types
		initEDataType(addressCategoryTypeObjectEDataType, AddressCategoryType.class, "AddressCategoryTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nonNullStringEDataType, String.class, "NonNullString", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(restrictionsTypeObjectEDataType, RestrictionsType.class, "RestrictionsTypeObject",
				IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
		addAnnotation(addressCategoryTypeEEnum, source, new String[] { "name", "AddressCategoryType" });
		addAnnotation(addressCategoryTypeObjectEDataType, source,
				new String[] { "name", "AddressCategoryType:Object", "baseType", "AddressCategoryType" });
		addAnnotation(addressTypeEClass, source, new String[] { "name", "AddressType", "kind", "elementOnly" });
		addAnnotation(getAddressType_ID(), source,
				new String[] { "kind", "element", "name", "ID", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_Organization(), source,
				new String[] { "kind", "element", "name", "Organization", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_StreetAddressLine(), source,
				new String[] { "kind", "element", "name", "StreetAddressLine", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_City(), source,
				new String[] { "kind", "element", "name", "City", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_StateOrProvince(), source,
				new String[] { "kind", "element", "name", "StateOrProvince", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_PostalCode(), source,
				new String[] { "kind", "element", "name", "PostalCode", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_Region(), source,
				new String[] { "kind", "element", "name", "Region", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_District(), source,
				new String[] { "kind", "element", "name", "District", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_Country(), source,
				new String[] { "kind", "element", "name", "Country", "namespace", "##targetNamespace" });
		addAnnotation(getAddressType_AddressCategory(), source,
				new String[] { "kind", "attribute", "name", "addressCategory" });
		addAnnotation(getAddressType_Restrictions(), source,
				new String[] { "kind", "attribute", "name", "restrictions" });
		addAnnotation(countryTypeEClass, source, new String[] { "name", "CountryType", "kind", "elementOnly" });
		addAnnotation(getCountryType_CountryName(), source,
				new String[] { "kind", "element", "name", "CountryName", "namespace", "##targetNamespace" });
		addAnnotation(getCountryType_CountryCode(), source,
				new String[] { "kind", "element", "name", "CountryCode", "namespace", "##targetNamespace" });
		addAnnotation(nonNullStringEDataType, source, new String[] { "name", "NonNullString", "baseType",
				"http://www.eclipse.org/emf/2003/XMLType#string", "minLength", "1" });
		addAnnotation(restrictionsTypeEEnum, source, new String[] { "name", "RestrictionsType" });
		addAnnotation(restrictionsTypeObjectEDataType, source,
				new String[] { "name", "RestrictionsType:Object", "baseType", "RestrictionsType" });
		addAnnotation(streetAddressLineTypeEClass, source,
				new String[] { "name", "StreetAddressLineType", "kind", "simple" });
		addAnnotation(getStreetAddressLineType_Value(), source, new String[] { "name", ":0", "kind", "simple" });
		addAnnotation(getStreetAddressLineType_Restrictions(), source,
				new String[] { "kind", "attribute", "name", "restrictions" });
		addAnnotation(documentRootEClass, source, new String[] { "name", "", "kind", "mixed" });
		addAnnotation(getDocumentRoot_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
		addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source,
				new String[] { "kind", "attribute", "name", "xmlns:prefix" });
		addAnnotation(getDocumentRoot_XSISchemaLocation(), source,
				new String[] { "kind", "attribute", "name", "xsi:schemaLocation" });
		addAnnotation(getDocumentRoot_Address(), source,
				new String[] { "kind", "element", "name", "Address", "namespace", "##targetNamespace" });
	}

} //AddressPackageImpl
