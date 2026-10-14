/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.*;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LomFactoryImpl extends EFactoryImpl implements LomFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LomFactory init() {
		try {
			LomFactory theLomFactory = (LomFactory) EPackage.Registry.INSTANCE.getEFactory(LomPackage.eNS_URI);
			if (theLomFactory != null) {
				return theLomFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LomFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LomFactoryImpl() {
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
		case LomPackage.AGGREGATION_LEVEL:
			return createAggregationLevel();
		case LomPackage.AGGREGATION_LEVEL_VALUE:
			return createAggregationLevelValue();
		case LomPackage.AGGREGATION_LEVEL_VOCAB:
			return createAggregationLevelVocab();
		case LomPackage.ANNOTATION:
			return createAnnotation();
		case LomPackage.CATALOG:
			return createCatalog();
		case LomPackage.CLASSIFICATION:
			return createClassification();
		case LomPackage.CONTEXT:
			return createContext();
		case LomPackage.CONTEXT_VALUE:
			return createContextValue();
		case LomPackage.CONTEXT_VOCAB:
			return createContextVocab();
		case LomPackage.CONTRIBUTE:
			return createContribute();
		case LomPackage.CONTRIBUTE_META:
			return createContributeMeta();
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS:
			return createCopyrightAndOtherRestrictions();
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE:
			return createCopyrightAndOtherRestrictionsValue();
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB:
			return createCopyrightAndOtherRestrictionsVocab();
		case LomPackage.COST:
			return createCost();
		case LomPackage.COST_VALUE:
			return createCostValue();
		case LomPackage.COST_VOCAB:
			return createCostVocab();
		case LomPackage.COVERAGE:
			return createCoverage();
		case LomPackage.DATE:
			return createDate();
		case LomPackage.DATE_TIME:
			return createDateTime();
		case LomPackage.DATE_TIME_VALUE:
			return createDateTimeValue();
		case LomPackage.DESCRIPTION:
			return createDescription();
		case LomPackage.DIFFICULTY:
			return createDifficulty();
		case LomPackage.DIFFICULTY_VALUE:
			return createDifficultyValue();
		case LomPackage.DIFFICULTY_VOCAB:
			return createDifficultyVocab();
		case LomPackage.DURATION:
			return createDuration();
		case LomPackage.DURATION_BASE:
			return createDurationBase();
		case LomPackage.DURATION_VALUE:
			return createDurationValue();
		case LomPackage.EDUCATIONAL:
			return createEducational();
		case LomPackage.ENTITY:
			return createEntity();
		case LomPackage.ENTRY:
			return createEntry();
		case LomPackage.ENTRY_TAXON:
			return createEntryTaxon();
		case LomPackage.FORMAT:
			return createFormat();
		case LomPackage.GENERAL:
			return createGeneral();
		case LomPackage.ID:
			return createId();
		case LomPackage.IDENTIFIER:
			return createIdentifier();
		case LomPackage.INSTALLATION_REMARKS:
			return createInstallationRemarks();
		case LomPackage.INTENDED_END_USER_ROLE:
			return createIntendedEndUserRole();
		case LomPackage.INTENDED_END_USER_ROLE_VALUE:
			return createIntendedEndUserRoleValue();
		case LomPackage.INTENDED_END_USER_ROLE_VOCAB:
			return createIntendedEndUserRoleVocab();
		case LomPackage.INTERACTIVITY_LEVEL:
			return createInteractivityLevel();
		case LomPackage.INTERACTIVITY_LEVEL_VALUE:
			return createInteractivityLevelValue();
		case LomPackage.INTERACTIVITY_LEVEL_VOCAB:
			return createInteractivityLevelVocab();
		case LomPackage.INTERACTIVITY_TYPE:
			return createInteractivityType();
		case LomPackage.KEYWORD:
			return createKeyword();
		case LomPackage.KIND_VALUE:
			return createKindValue();
		case LomPackage.LANG_STRING:
			return createLangString();
		case LomPackage.LANGUAGE_STRING:
			return createLanguageString();
		case LomPackage.DOCUMENT_ROOT:
			return createDocumentRoot();
		case LomPackage.INTERACTIVITY_TYPE_VALUE:
			return createInteractivityTypeValue();
		case LomPackage.INTERACTIVITY_TYPE_VOCAB:
			return createInteractivityTypeVocab();
		case LomPackage.KIND:
			return createKind();
		case LomPackage.KIND_VOCAB:
			return createKindVocab();
		case LomPackage.LANGUAGE:
			return createLanguage();
		case LomPackage.LANGUAGE_ID:
			return createLanguageId();
		case LomPackage.LEARNING_RESOURCE_TYPE:
			return createLearningResourceType();
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUE:
			return createLearningResourceTypeValue();
		case LomPackage.LEARNING_RESOURCE_TYPE_VOCAB:
			return createLearningResourceTypeVocab();
		case LomPackage.LIFE_CYCLE:
			return createLifeCycle();
		case LomPackage.LOCATION:
			return createLocation();
		case LomPackage.LOM:
			return createLom();
		case LomPackage.MAXIMUM_VERSION:
			return createMaximumVersion();
		case LomPackage.METADATA_SCHEMA:
			return createMetadataSchema();
		case LomPackage.META_METADATA:
			return createMetaMetadata();
		case LomPackage.MINIMUM_VERSION:
			return createMinimumVersion();
		case LomPackage.NAME:
			return createName();
		case LomPackage.NAME_VALUE:
			return createNameValue();
		case LomPackage.NAME_VOCAB:
			return createNameVocab();
		case LomPackage.OR_COMPOSITE:
			return createOrComposite();
		case LomPackage.OTHER_PLATFORM_REQUIREMENTS:
			return createOtherPlatformRequirements();
		case LomPackage.PURPOSE:
			return createPurpose();
		case LomPackage.PURPOSE_VALUE:
			return createPurposeValue();
		case LomPackage.PURPOSE_VOCAB:
			return createPurposeVocab();
		case LomPackage.RELATION:
			return createRelation();
		case LomPackage.REQUIREMENT:
			return createRequirement();
		case LomPackage.RESOURCE:
			return createResource();
		case LomPackage.RIGHTS:
			return createRights();
		case LomPackage.ROLE:
			return createRole();
		case LomPackage.ROLE_META:
			return createRoleMeta();
		case LomPackage.ROLE_META_VALUE:
			return createRoleMetaValue();
		case LomPackage.ROLE_META_VOCAB:
			return createRoleMetaVocab();
		case LomPackage.ROLE_VALUE:
			return createRoleValue();
		case LomPackage.ROLE_VOCAB:
			return createRoleVocab();
		case LomPackage.SEMANTIC_DENSITY:
			return createSemanticDensity();
		case LomPackage.SEMANTIC_DENSITY_VALUE:
			return createSemanticDensityValue();
		case LomPackage.SEMANTIC_DENSITY_VOCAB:
			return createSemanticDensityVocab();
		case LomPackage.SIZE:
			return createSize();
		case LomPackage.SOURCE:
			return createSource();
		case LomPackage.SOURCE_VALUE:
			return createSourceValue();
		case LomPackage.STATUS:
			return createStatus();
		case LomPackage.STATUS_VALUE:
			return createStatusValue();
		case LomPackage.STATUS_VOCAB:
			return createStatusVocab();
		case LomPackage.STRUCTURE:
			return createStructure();
		case LomPackage.STRUCTURE_VALUE:
			return createStructureValue();
		case LomPackage.STRUCTURE_VOCAB:
			return createStructureVocab();
		case LomPackage.TAXON:
			return createTaxon();
		case LomPackage.TAXON_PATH:
			return createTaxonPath();
		case LomPackage.TECHNICAL:
			return createTechnical();
		case LomPackage.TITLE:
			return createTitle();
		case LomPackage.TYPE:
			return createType();
		case LomPackage.TYPE_VALUE:
			return createTypeValue();
		case LomPackage.TYPE_VOCAB:
			return createTypeVocab();
		case LomPackage.TYPICAL_AGE_RANGE:
			return createTypicalAgeRange();
		case LomPackage.TYPICAL_LEARNING_TIME:
			return createTypicalLearningTime();
		case LomPackage.VCARD:
			return createVCard();
		case LomPackage.VERSION:
			return createVersion();
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
		case LomPackage.AGGREGATION_LEVEL_VALUES:
			return createAggregationLevelValuesFromString(eDataType, initialValue);
		case LomPackage.CONTEXT_VALUES:
			return createContextValuesFromString(eDataType, initialValue);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES:
			return createCopyrightAndOtherRestrictionsValuesFromString(eDataType, initialValue);
		case LomPackage.COST_VALUES:
			return createCostValuesFromString(eDataType, initialValue);
		case LomPackage.DIFFICULTY_VALUES:
			return createDifficultyValuesFromString(eDataType, initialValue);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES:
			return createIntendedEndUserRoleValuesFromString(eDataType, initialValue);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES:
			return createInteractivityLevelValuesFromString(eDataType, initialValue);
		case LomPackage.INTERACTIVITY_TYPE_VALUES:
			return createInteractivityTypeValuesFromString(eDataType, initialValue);
		case LomPackage.KIND_VALUES:
			return createKindValuesFromString(eDataType, initialValue);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES:
			return createLearningResourceTypeValuesFromString(eDataType, initialValue);
		case LomPackage.NAME_VALUES:
			return createNameValuesFromString(eDataType, initialValue);
		case LomPackage.PURPOSE_VALUES:
			return createPurposeValuesFromString(eDataType, initialValue);
		case LomPackage.ROLE_META_VALUES:
			return createRoleMetaValuesFromString(eDataType, initialValue);
		case LomPackage.ROLE_VALUES:
			return createRoleValuesFromString(eDataType, initialValue);
		case LomPackage.SEMANTIC_DENSITY_VALUES:
			return createSemanticDensityValuesFromString(eDataType, initialValue);
		case LomPackage.SOURCE_VALUES:
			return createSourceValuesFromString(eDataType, initialValue);
		case LomPackage.STATUS_VALUES:
			return createStatusValuesFromString(eDataType, initialValue);
		case LomPackage.STRUCTURE_VALUES:
			return createStructureValuesFromString(eDataType, initialValue);
		case LomPackage.TYPE_VALUES:
			return createTypeValuesFromString(eDataType, initialValue);
		case LomPackage.AGGREGATION_LEVEL_VALUES_OBJECT:
			return createAggregationLevelValuesObjectFromString(eDataType, initialValue);
		case LomPackage.CHARACTER_STRING:
			return createCharacterStringFromString(eDataType, initialValue);
		case LomPackage.CONTEXT_VALUES_OBJECT:
			return createContextValuesObjectFromString(eDataType, initialValue);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES_OBJECT:
			return createCopyrightAndOtherRestrictionsValuesObjectFromString(eDataType, initialValue);
		case LomPackage.COST_VALUES_OBJECT:
			return createCostValuesObjectFromString(eDataType, initialValue);
		case LomPackage.DATE_TIME_STRING:
			return createDateTimeStringFromString(eDataType, initialValue);
		case LomPackage.DIFFICULTY_VALUES_OBJECT:
			return createDifficultyValuesObjectFromString(eDataType, initialValue);
		case LomPackage.DURATION_STRING:
			return createDurationStringFromString(eDataType, initialValue);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES_OBJECT:
			return createInteractivityLevelValuesObjectFromString(eDataType, initialValue);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES_OBJECT:
			return createIntendedEndUserRoleValuesObjectFromString(eDataType, initialValue);
		case LomPackage.INTERACTIVITY_TYPE_VALUES_OBJECT:
			return createInteractivityTypeValuesObjectFromString(eDataType, initialValue);
		case LomPackage.KIND_VALUES_OBJECT:
			return createKindValuesObjectFromString(eDataType, initialValue);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES_OBJECT:
			return createLearningResourceTypeValuesObjectFromString(eDataType, initialValue);
		case LomPackage.MIME_TYPE:
			return createMimeTypeFromString(eDataType, initialValue);
		case LomPackage.NAME_VALUES_OBJECT:
			return createNameValuesObjectFromString(eDataType, initialValue);
		case LomPackage.PURPOSE_VALUES_OBJECT:
			return createPurposeValuesObjectFromString(eDataType, initialValue);
		case LomPackage.ROLE_META_VALUES_OBJECT:
			return createRoleMetaValuesObjectFromString(eDataType, initialValue);
		case LomPackage.ROLE_VALUES_OBJECT:
			return createRoleValuesObjectFromString(eDataType, initialValue);
		case LomPackage.SEMANTIC_DENSITY_VALUES_OBJECT:
			return createSemanticDensityValuesObjectFromString(eDataType, initialValue);
		case LomPackage.SIZE_BASE:
			return createSizeBaseFromString(eDataType, initialValue);
		case LomPackage.SOURCE_VALUES_OBJECT:
			return createSourceValuesObjectFromString(eDataType, initialValue);
		case LomPackage.STATUS_VALUES_OBJECT:
			return createStatusValuesObjectFromString(eDataType, initialValue);
		case LomPackage.STRUCTURE_VALUES_OBJECT:
			return createStructureValuesObjectFromString(eDataType, initialValue);
		case LomPackage.TYPE_VALUES_OBJECT:
			return createTypeValuesObjectFromString(eDataType, initialValue);
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
		case LomPackage.AGGREGATION_LEVEL_VALUES:
			return convertAggregationLevelValuesToString(eDataType, instanceValue);
		case LomPackage.CONTEXT_VALUES:
			return convertContextValuesToString(eDataType, instanceValue);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES:
			return convertCopyrightAndOtherRestrictionsValuesToString(eDataType, instanceValue);
		case LomPackage.COST_VALUES:
			return convertCostValuesToString(eDataType, instanceValue);
		case LomPackage.DIFFICULTY_VALUES:
			return convertDifficultyValuesToString(eDataType, instanceValue);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES:
			return convertIntendedEndUserRoleValuesToString(eDataType, instanceValue);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES:
			return convertInteractivityLevelValuesToString(eDataType, instanceValue);
		case LomPackage.INTERACTIVITY_TYPE_VALUES:
			return convertInteractivityTypeValuesToString(eDataType, instanceValue);
		case LomPackage.KIND_VALUES:
			return convertKindValuesToString(eDataType, instanceValue);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES:
			return convertLearningResourceTypeValuesToString(eDataType, instanceValue);
		case LomPackage.NAME_VALUES:
			return convertNameValuesToString(eDataType, instanceValue);
		case LomPackage.PURPOSE_VALUES:
			return convertPurposeValuesToString(eDataType, instanceValue);
		case LomPackage.ROLE_META_VALUES:
			return convertRoleMetaValuesToString(eDataType, instanceValue);
		case LomPackage.ROLE_VALUES:
			return convertRoleValuesToString(eDataType, instanceValue);
		case LomPackage.SEMANTIC_DENSITY_VALUES:
			return convertSemanticDensityValuesToString(eDataType, instanceValue);
		case LomPackage.SOURCE_VALUES:
			return convertSourceValuesToString(eDataType, instanceValue);
		case LomPackage.STATUS_VALUES:
			return convertStatusValuesToString(eDataType, instanceValue);
		case LomPackage.STRUCTURE_VALUES:
			return convertStructureValuesToString(eDataType, instanceValue);
		case LomPackage.TYPE_VALUES:
			return convertTypeValuesToString(eDataType, instanceValue);
		case LomPackage.AGGREGATION_LEVEL_VALUES_OBJECT:
			return convertAggregationLevelValuesObjectToString(eDataType, instanceValue);
		case LomPackage.CHARACTER_STRING:
			return convertCharacterStringToString(eDataType, instanceValue);
		case LomPackage.CONTEXT_VALUES_OBJECT:
			return convertContextValuesObjectToString(eDataType, instanceValue);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES_OBJECT:
			return convertCopyrightAndOtherRestrictionsValuesObjectToString(eDataType, instanceValue);
		case LomPackage.COST_VALUES_OBJECT:
			return convertCostValuesObjectToString(eDataType, instanceValue);
		case LomPackage.DATE_TIME_STRING:
			return convertDateTimeStringToString(eDataType, instanceValue);
		case LomPackage.DIFFICULTY_VALUES_OBJECT:
			return convertDifficultyValuesObjectToString(eDataType, instanceValue);
		case LomPackage.DURATION_STRING:
			return convertDurationStringToString(eDataType, instanceValue);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES_OBJECT:
			return convertInteractivityLevelValuesObjectToString(eDataType, instanceValue);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES_OBJECT:
			return convertIntendedEndUserRoleValuesObjectToString(eDataType, instanceValue);
		case LomPackage.INTERACTIVITY_TYPE_VALUES_OBJECT:
			return convertInteractivityTypeValuesObjectToString(eDataType, instanceValue);
		case LomPackage.KIND_VALUES_OBJECT:
			return convertKindValuesObjectToString(eDataType, instanceValue);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES_OBJECT:
			return convertLearningResourceTypeValuesObjectToString(eDataType, instanceValue);
		case LomPackage.MIME_TYPE:
			return convertMimeTypeToString(eDataType, instanceValue);
		case LomPackage.NAME_VALUES_OBJECT:
			return convertNameValuesObjectToString(eDataType, instanceValue);
		case LomPackage.PURPOSE_VALUES_OBJECT:
			return convertPurposeValuesObjectToString(eDataType, instanceValue);
		case LomPackage.ROLE_META_VALUES_OBJECT:
			return convertRoleMetaValuesObjectToString(eDataType, instanceValue);
		case LomPackage.ROLE_VALUES_OBJECT:
			return convertRoleValuesObjectToString(eDataType, instanceValue);
		case LomPackage.SEMANTIC_DENSITY_VALUES_OBJECT:
			return convertSemanticDensityValuesObjectToString(eDataType, instanceValue);
		case LomPackage.SIZE_BASE:
			return convertSizeBaseToString(eDataType, instanceValue);
		case LomPackage.SOURCE_VALUES_OBJECT:
			return convertSourceValuesObjectToString(eDataType, instanceValue);
		case LomPackage.STATUS_VALUES_OBJECT:
			return convertStatusValuesObjectToString(eDataType, instanceValue);
		case LomPackage.STRUCTURE_VALUES_OBJECT:
			return convertStructureValuesObjectToString(eDataType, instanceValue);
		case LomPackage.TYPE_VALUES_OBJECT:
			return convertTypeValuesObjectToString(eDataType, instanceValue);
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
	public AggregationLevel createAggregationLevel() {
		AggregationLevelImpl aggregationLevel = new AggregationLevelImpl();
		return aggregationLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AggregationLevelValue createAggregationLevelValue() {
		AggregationLevelValueImpl aggregationLevelValue = new AggregationLevelValueImpl();
		return aggregationLevelValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AggregationLevelVocab createAggregationLevelVocab() {
		AggregationLevelVocabImpl aggregationLevelVocab = new AggregationLevelVocabImpl();
		return aggregationLevelVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Catalog createCatalog() {
		CatalogImpl catalog = new CatalogImpl();
		return catalog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Classification createClassification() {
		ClassificationImpl classification = new ClassificationImpl();
		return classification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Context createContext() {
		ContextImpl context = new ContextImpl();
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContextValue createContextValue() {
		ContextValueImpl contextValue = new ContextValueImpl();
		return contextValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContextVocab createContextVocab() {
		ContextVocabImpl contextVocab = new ContextVocabImpl();
		return contextVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Contribute createContribute() {
		ContributeImpl contribute = new ContributeImpl();
		return contribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContributeMeta createContributeMeta() {
		ContributeMetaImpl contributeMeta = new ContributeMetaImpl();
		return contributeMeta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CopyrightAndOtherRestrictions createCopyrightAndOtherRestrictions() {
		CopyrightAndOtherRestrictionsImpl copyrightAndOtherRestrictions = new CopyrightAndOtherRestrictionsImpl();
		return copyrightAndOtherRestrictions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CopyrightAndOtherRestrictionsValue createCopyrightAndOtherRestrictionsValue() {
		CopyrightAndOtherRestrictionsValueImpl copyrightAndOtherRestrictionsValue = new CopyrightAndOtherRestrictionsValueImpl();
		return copyrightAndOtherRestrictionsValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CopyrightAndOtherRestrictionsVocab createCopyrightAndOtherRestrictionsVocab() {
		CopyrightAndOtherRestrictionsVocabImpl copyrightAndOtherRestrictionsVocab = new CopyrightAndOtherRestrictionsVocabImpl();
		return copyrightAndOtherRestrictionsVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Cost createCost() {
		CostImpl cost = new CostImpl();
		return cost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CostValue createCostValue() {
		CostValueImpl costValue = new CostValueImpl();
		return costValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CostVocab createCostVocab() {
		CostVocabImpl costVocab = new CostVocabImpl();
		return costVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Coverage createCoverage() {
		CoverageImpl coverage = new CoverageImpl();
		return coverage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date createDate() {
		DateImpl date = new DateImpl();
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateTime createDateTime() {
		DateTimeImpl dateTime = new DateTimeImpl();
		return dateTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateTimeValue createDateTimeValue() {
		DateTimeValueImpl dateTimeValue = new DateTimeValueImpl();
		return dateTimeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Description createDescription() {
		DescriptionImpl description = new DescriptionImpl();
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Difficulty createDifficulty() {
		DifficultyImpl difficulty = new DifficultyImpl();
		return difficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultyValue createDifficultyValue() {
		DifficultyValueImpl difficultyValue = new DifficultyValueImpl();
		return difficultyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultyVocab createDifficultyVocab() {
		DifficultyVocabImpl difficultyVocab = new DifficultyVocabImpl();
		return difficultyVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Duration createDuration() {
		DurationImpl duration = new DurationImpl();
		return duration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationBase createDurationBase() {
		DurationBaseImpl durationBase = new DurationBaseImpl();
		return durationBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationValue createDurationValue() {
		DurationValueImpl durationValue = new DurationValueImpl();
		return durationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Educational createEducational() {
		EducationalImpl educational = new EducationalImpl();
		return educational;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Entry createEntry() {
		EntryImpl entry = new EntryImpl();
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EntryTaxon createEntryTaxon() {
		EntryTaxonImpl entryTaxon = new EntryTaxonImpl();
		return entryTaxon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Format createFormat() {
		FormatImpl format = new FormatImpl();
		return format;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public General createGeneral() {
		GeneralImpl general = new GeneralImpl();
		return general;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Id createId() {
		IdImpl id = new IdImpl();
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Identifier createIdentifier() {
		IdentifierImpl identifier = new IdentifierImpl();
		return identifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InstallationRemarks createInstallationRemarks() {
		InstallationRemarksImpl installationRemarks = new InstallationRemarksImpl();
		return installationRemarks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IntendedEndUserRole createIntendedEndUserRole() {
		IntendedEndUserRoleImpl intendedEndUserRole = new IntendedEndUserRoleImpl();
		return intendedEndUserRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IntendedEndUserRoleValue createIntendedEndUserRoleValue() {
		IntendedEndUserRoleValueImpl intendedEndUserRoleValue = new IntendedEndUserRoleValueImpl();
		return intendedEndUserRoleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IntendedEndUserRoleVocab createIntendedEndUserRoleVocab() {
		IntendedEndUserRoleVocabImpl intendedEndUserRoleVocab = new IntendedEndUserRoleVocabImpl();
		return intendedEndUserRoleVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InteractivityLevel createInteractivityLevel() {
		InteractivityLevelImpl interactivityLevel = new InteractivityLevelImpl();
		return interactivityLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InteractivityLevelValue createInteractivityLevelValue() {
		InteractivityLevelValueImpl interactivityLevelValue = new InteractivityLevelValueImpl();
		return interactivityLevelValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InteractivityLevelVocab createInteractivityLevelVocab() {
		InteractivityLevelVocabImpl interactivityLevelVocab = new InteractivityLevelVocabImpl();
		return interactivityLevelVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InteractivityType createInteractivityType() {
		InteractivityTypeImpl interactivityType = new InteractivityTypeImpl();
		return interactivityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Keyword createKeyword() {
		KeywordImpl keyword = new KeywordImpl();
		return keyword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public KindValue createKindValue() {
		KindValueImpl kindValue = new KindValueImpl();
		return kindValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LangString createLangString() {
		LangStringImpl langString = new LangStringImpl();
		return langString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString createLanguageString() {
		LanguageStringImpl languageString = new LanguageStringImpl();
		return languageString;
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
	public InteractivityTypeValue createInteractivityTypeValue() {
		InteractivityTypeValueImpl interactivityTypeValue = new InteractivityTypeValueImpl();
		return interactivityTypeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InteractivityTypeVocab createInteractivityTypeVocab() {
		InteractivityTypeVocabImpl interactivityTypeVocab = new InteractivityTypeVocabImpl();
		return interactivityTypeVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Kind createKind() {
		KindImpl kind = new KindImpl();
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public KindVocab createKindVocab() {
		KindVocabImpl kindVocab = new KindVocabImpl();
		return kindVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Language createLanguage() {
		LanguageImpl language = new LanguageImpl();
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageId createLanguageId() {
		LanguageIdImpl languageId = new LanguageIdImpl();
		return languageId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LearningResourceType createLearningResourceType() {
		LearningResourceTypeImpl learningResourceType = new LearningResourceTypeImpl();
		return learningResourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LearningResourceTypeValue createLearningResourceTypeValue() {
		LearningResourceTypeValueImpl learningResourceTypeValue = new LearningResourceTypeValueImpl();
		return learningResourceTypeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LearningResourceTypeVocab createLearningResourceTypeVocab() {
		LearningResourceTypeVocabImpl learningResourceTypeVocab = new LearningResourceTypeVocabImpl();
		return learningResourceTypeVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LifeCycle createLifeCycle() {
		LifeCycleImpl lifeCycle = new LifeCycleImpl();
		return lifeCycle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Lom createLom() {
		LomImpl lom = new LomImpl();
		return lom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MaximumVersion createMaximumVersion() {
		MaximumVersionImpl maximumVersion = new MaximumVersionImpl();
		return maximumVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetadataSchema createMetadataSchema() {
		MetadataSchemaImpl metadataSchema = new MetadataSchemaImpl();
		return metadataSchema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetaMetadata createMetaMetadata() {
		MetaMetadataImpl metaMetadata = new MetaMetadataImpl();
		return metaMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MinimumVersion createMinimumVersion() {
		MinimumVersionImpl minimumVersion = new MinimumVersionImpl();
		return minimumVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Name createName() {
		NameImpl name = new NameImpl();
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NameValue createNameValue() {
		NameValueImpl nameValue = new NameValueImpl();
		return nameValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NameVocab createNameVocab() {
		NameVocabImpl nameVocab = new NameVocabImpl();
		return nameVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OrComposite createOrComposite() {
		OrCompositeImpl orComposite = new OrCompositeImpl();
		return orComposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OtherPlatformRequirements createOtherPlatformRequirements() {
		OtherPlatformRequirementsImpl otherPlatformRequirements = new OtherPlatformRequirementsImpl();
		return otherPlatformRequirements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Purpose createPurpose() {
		PurposeImpl purpose = new PurposeImpl();
		return purpose;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PurposeValue createPurposeValue() {
		PurposeValueImpl purposeValue = new PurposeValueImpl();
		return purposeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PurposeVocab createPurposeVocab() {
		PurposeVocabImpl purposeVocab = new PurposeVocabImpl();
		return purposeVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Relation createRelation() {
		RelationImpl relation = new RelationImpl();
		return relation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Requirement createRequirement() {
		RequirementImpl requirement = new RequirementImpl();
		return requirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rights createRights() {
		RightsImpl rights = new RightsImpl();
		return rights;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RoleMeta createRoleMeta() {
		RoleMetaImpl roleMeta = new RoleMetaImpl();
		return roleMeta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RoleMetaValue createRoleMetaValue() {
		RoleMetaValueImpl roleMetaValue = new RoleMetaValueImpl();
		return roleMetaValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RoleMetaVocab createRoleMetaVocab() {
		RoleMetaVocabImpl roleMetaVocab = new RoleMetaVocabImpl();
		return roleMetaVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RoleValue createRoleValue() {
		RoleValueImpl roleValue = new RoleValueImpl();
		return roleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RoleVocab createRoleVocab() {
		RoleVocabImpl roleVocab = new RoleVocabImpl();
		return roleVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SemanticDensity createSemanticDensity() {
		SemanticDensityImpl semanticDensity = new SemanticDensityImpl();
		return semanticDensity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SemanticDensityValue createSemanticDensityValue() {
		SemanticDensityValueImpl semanticDensityValue = new SemanticDensityValueImpl();
		return semanticDensityValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SemanticDensityVocab createSemanticDensityVocab() {
		SemanticDensityVocabImpl semanticDensityVocab = new SemanticDensityVocabImpl();
		return semanticDensityVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Size createSize() {
		SizeImpl size = new SizeImpl();
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Source createSource() {
		SourceImpl source = new SourceImpl();
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SourceValue createSourceValue() {
		SourceValueImpl sourceValue = new SourceValueImpl();
		return sourceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Status createStatus() {
		StatusImpl status = new StatusImpl();
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StatusValue createStatusValue() {
		StatusValueImpl statusValue = new StatusValueImpl();
		return statusValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StatusVocab createStatusVocab() {
		StatusVocabImpl statusVocab = new StatusVocabImpl();
		return statusVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Structure createStructure() {
		StructureImpl structure = new StructureImpl();
		return structure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructureValue createStructureValue() {
		StructureValueImpl structureValue = new StructureValueImpl();
		return structureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructureVocab createStructureVocab() {
		StructureVocabImpl structureVocab = new StructureVocabImpl();
		return structureVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Taxon createTaxon() {
		TaxonImpl taxon = new TaxonImpl();
		return taxon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TaxonPath createTaxonPath() {
		TaxonPathImpl taxonPath = new TaxonPathImpl();
		return taxonPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Technical createTechnical() {
		TechnicalImpl technical = new TechnicalImpl();
		return technical;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Title createTitle() {
		TitleImpl title = new TitleImpl();
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeValue createTypeValue() {
		TypeValueImpl typeValue = new TypeValueImpl();
		return typeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeVocab createTypeVocab() {
		TypeVocabImpl typeVocab = new TypeVocabImpl();
		return typeVocab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypicalAgeRange createTypicalAgeRange() {
		TypicalAgeRangeImpl typicalAgeRange = new TypicalAgeRangeImpl();
		return typicalAgeRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypicalLearningTime createTypicalLearningTime() {
		TypicalLearningTimeImpl typicalLearningTime = new TypicalLearningTimeImpl();
		return typicalLearningTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VCard createVCard() {
		VCardImpl vCard = new VCardImpl();
		return vCard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Version createVersion() {
		VersionImpl version = new VersionImpl();
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AggregationLevelValues createAggregationLevelValuesFromString(EDataType eDataType, String initialValue) {
		AggregationLevelValues result = AggregationLevelValues.get(initialValue);
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
	public String convertAggregationLevelValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextValues createContextValuesFromString(EDataType eDataType, String initialValue) {
		ContextValues result = ContextValues.get(initialValue);
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
	public String convertContextValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CopyrightAndOtherRestrictionsValues createCopyrightAndOtherRestrictionsValuesFromString(EDataType eDataType,
			String initialValue) {
		CopyrightAndOtherRestrictionsValues result = CopyrightAndOtherRestrictionsValues.get(initialValue);
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
	public String convertCopyrightAndOtherRestrictionsValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CostValues createCostValuesFromString(EDataType eDataType, String initialValue) {
		CostValues result = CostValues.get(initialValue);
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
	public String convertCostValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifficultyValues createDifficultyValuesFromString(EDataType eDataType, String initialValue) {
		DifficultyValues result = DifficultyValues.get(initialValue);
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
	public String convertDifficultyValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntendedEndUserRoleValues createIntendedEndUserRoleValuesFromString(EDataType eDataType,
			String initialValue) {
		IntendedEndUserRoleValues result = IntendedEndUserRoleValues.get(initialValue);
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
	public String convertIntendedEndUserRoleValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractivityLevelValues createInteractivityLevelValuesFromString(EDataType eDataType, String initialValue) {
		InteractivityLevelValues result = InteractivityLevelValues.get(initialValue);
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
	public String convertInteractivityLevelValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractivityTypeValues createInteractivityTypeValuesFromString(EDataType eDataType, String initialValue) {
		InteractivityTypeValues result = InteractivityTypeValues.get(initialValue);
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
	public String convertInteractivityTypeValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KindValues createKindValuesFromString(EDataType eDataType, String initialValue) {
		KindValues result = KindValues.get(initialValue);
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
	public String convertKindValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LearningResourceTypeValues createLearningResourceTypeValuesFromString(EDataType eDataType,
			String initialValue) {
		LearningResourceTypeValues result = LearningResourceTypeValues.get(initialValue);
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
	public String convertLearningResourceTypeValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameValues createNameValuesFromString(EDataType eDataType, String initialValue) {
		NameValues result = NameValues.get(initialValue);
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
	public String convertNameValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PurposeValues createPurposeValuesFromString(EDataType eDataType, String initialValue) {
		PurposeValues result = PurposeValues.get(initialValue);
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
	public String convertPurposeValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleMetaValues createRoleMetaValuesFromString(EDataType eDataType, String initialValue) {
		RoleMetaValues result = RoleMetaValues.get(initialValue);
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
	public String convertRoleMetaValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleValues createRoleValuesFromString(EDataType eDataType, String initialValue) {
		RoleValues result = RoleValues.get(initialValue);
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
	public String convertRoleValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticDensityValues createSemanticDensityValuesFromString(EDataType eDataType, String initialValue) {
		SemanticDensityValues result = SemanticDensityValues.get(initialValue);
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
	public String convertSemanticDensityValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceValues createSourceValuesFromString(EDataType eDataType, String initialValue) {
		SourceValues result = SourceValues.get(initialValue);
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
	public String convertSourceValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusValues createStatusValuesFromString(EDataType eDataType, String initialValue) {
		StatusValues result = StatusValues.get(initialValue);
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
	public String convertStatusValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructureValues createStructureValuesFromString(EDataType eDataType, String initialValue) {
		StructureValues result = StructureValues.get(initialValue);
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
	public String convertStructureValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeValues createTypeValuesFromString(EDataType eDataType, String initialValue) {
		TypeValues result = TypeValues.get(initialValue);
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
	public String convertTypeValuesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AggregationLevelValues createAggregationLevelValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createAggregationLevelValuesFromString(LomPackage.eINSTANCE.getAggregationLevelValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAggregationLevelValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAggregationLevelValuesToString(LomPackage.eINSTANCE.getAggregationLevelValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createCharacterStringFromString(EDataType eDataType, String initialValue) {
		return (String) XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCharacterStringToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextValues createContextValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createContextValuesFromString(LomPackage.eINSTANCE.getContextValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContextValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertContextValuesToString(LomPackage.eINSTANCE.getContextValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CopyrightAndOtherRestrictionsValues createCopyrightAndOtherRestrictionsValuesObjectFromString(
			EDataType eDataType, String initialValue) {
		return createCopyrightAndOtherRestrictionsValuesFromString(
				LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCopyrightAndOtherRestrictionsValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCopyrightAndOtherRestrictionsValuesToString(
				LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CostValues createCostValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createCostValuesFromString(LomPackage.eINSTANCE.getCostValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCostValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCostValuesToString(LomPackage.eINSTANCE.getCostValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createDateTimeStringFromString(EDataType eDataType, String initialValue) {
		return createCharacterStringFromString(LomPackage.eINSTANCE.getCharacterString(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDateTimeStringToString(EDataType eDataType, Object instanceValue) {
		return convertCharacterStringToString(LomPackage.eINSTANCE.getCharacterString(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifficultyValues createDifficultyValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createDifficultyValuesFromString(LomPackage.eINSTANCE.getDifficultyValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifficultyValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDifficultyValuesToString(LomPackage.eINSTANCE.getDifficultyValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createDurationStringFromString(EDataType eDataType, String initialValue) {
		return createCharacterStringFromString(LomPackage.eINSTANCE.getCharacterString(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDurationStringToString(EDataType eDataType, Object instanceValue) {
		return convertCharacterStringToString(LomPackage.eINSTANCE.getCharacterString(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractivityLevelValues createInteractivityLevelValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createInteractivityLevelValuesFromString(LomPackage.eINSTANCE.getInteractivityLevelValues(),
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInteractivityLevelValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertInteractivityLevelValuesToString(LomPackage.eINSTANCE.getInteractivityLevelValues(),
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntendedEndUserRoleValues createIntendedEndUserRoleValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createIntendedEndUserRoleValuesFromString(LomPackage.eINSTANCE.getIntendedEndUserRoleValues(),
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIntendedEndUserRoleValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertIntendedEndUserRoleValuesToString(LomPackage.eINSTANCE.getIntendedEndUserRoleValues(),
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InteractivityTypeValues createInteractivityTypeValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createInteractivityTypeValuesFromString(LomPackage.eINSTANCE.getInteractivityTypeValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInteractivityTypeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertInteractivityTypeValuesToString(LomPackage.eINSTANCE.getInteractivityTypeValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KindValues createKindValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createKindValuesFromString(LomPackage.eINSTANCE.getKindValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertKindValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertKindValuesToString(LomPackage.eINSTANCE.getKindValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LearningResourceTypeValues createLearningResourceTypeValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createLearningResourceTypeValuesFromString(LomPackage.eINSTANCE.getLearningResourceTypeValues(),
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLearningResourceTypeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertLearningResourceTypeValuesToString(LomPackage.eINSTANCE.getLearningResourceTypeValues(),
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createMimeTypeFromString(EDataType eDataType, String initialValue) {
		return createCharacterStringFromString(LomPackage.eINSTANCE.getCharacterString(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMimeTypeToString(EDataType eDataType, Object instanceValue) {
		return convertCharacterStringToString(LomPackage.eINSTANCE.getCharacterString(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameValues createNameValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createNameValuesFromString(LomPackage.eINSTANCE.getNameValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertNameValuesToString(LomPackage.eINSTANCE.getNameValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PurposeValues createPurposeValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createPurposeValuesFromString(LomPackage.eINSTANCE.getPurposeValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPurposeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPurposeValuesToString(LomPackage.eINSTANCE.getPurposeValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleMetaValues createRoleMetaValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createRoleMetaValuesFromString(LomPackage.eINSTANCE.getRoleMetaValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoleMetaValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRoleMetaValuesToString(LomPackage.eINSTANCE.getRoleMetaValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleValues createRoleValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createRoleValuesFromString(LomPackage.eINSTANCE.getRoleValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoleValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRoleValuesToString(LomPackage.eINSTANCE.getRoleValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticDensityValues createSemanticDensityValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createSemanticDensityValuesFromString(LomPackage.eINSTANCE.getSemanticDensityValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSemanticDensityValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSemanticDensityValuesToString(LomPackage.eINSTANCE.getSemanticDensityValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createSizeBaseFromString(EDataType eDataType, String initialValue) {
		return (BigInteger) XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.NON_NEGATIVE_INTEGER,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSizeBaseToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.NON_NEGATIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceValues createSourceValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createSourceValuesFromString(LomPackage.eINSTANCE.getSourceValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSourceValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSourceValuesToString(LomPackage.eINSTANCE.getSourceValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusValues createStatusValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createStatusValuesFromString(LomPackage.eINSTANCE.getStatusValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStatusValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertStatusValuesToString(LomPackage.eINSTANCE.getStatusValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructureValues createStructureValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createStructureValuesFromString(LomPackage.eINSTANCE.getStructureValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStructureValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertStructureValuesToString(LomPackage.eINSTANCE.getStructureValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeValues createTypeValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createTypeValuesFromString(LomPackage.eINSTANCE.getTypeValues(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTypeValuesToString(LomPackage.eINSTANCE.getTypeValues(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LomPackage getLomPackage() {
		return (LomPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LomPackage getPackage() {
		return LomPackage.eINSTANCE;
	}

} //LomFactoryImpl
