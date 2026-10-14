/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.util;

import edu.louisiana.caai.lom.model.lom.*;

import java.math.BigInteger;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.lom.LomPackage
 * @generated
 */
public class LomValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final LomValidator INSTANCE = new LomValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "edu.louisiana.caai.lom.model.lom";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LomValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return LomPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		switch (classifierID) {
		case LomPackage.AGGREGATION_LEVEL:
			return validateAggregationLevel((AggregationLevel) value, diagnostics, context);
		case LomPackage.AGGREGATION_LEVEL_VALUE:
			return validateAggregationLevelValue((AggregationLevelValue) value, diagnostics, context);
		case LomPackage.AGGREGATION_LEVEL_VOCAB:
			return validateAggregationLevelVocab((AggregationLevelVocab) value, diagnostics, context);
		case LomPackage.ANNOTATION:
			return validateAnnotation((Annotation) value, diagnostics, context);
		case LomPackage.CATALOG:
			return validateCatalog((Catalog) value, diagnostics, context);
		case LomPackage.CLASSIFICATION:
			return validateClassification((Classification) value, diagnostics, context);
		case LomPackage.CONTEXT:
			return validateContext((Context) value, diagnostics, context);
		case LomPackage.CONTEXT_VALUE:
			return validateContextValue((ContextValue) value, diagnostics, context);
		case LomPackage.CONTEXT_VOCAB:
			return validateContextVocab((ContextVocab) value, diagnostics, context);
		case LomPackage.CONTRIBUTE:
			return validateContribute((Contribute) value, diagnostics, context);
		case LomPackage.CONTRIBUTE_META:
			return validateContributeMeta((ContributeMeta) value, diagnostics, context);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS:
			return validateCopyrightAndOtherRestrictions((CopyrightAndOtherRestrictions) value, diagnostics, context);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE:
			return validateCopyrightAndOtherRestrictionsValue((CopyrightAndOtherRestrictionsValue) value, diagnostics,
					context);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB:
			return validateCopyrightAndOtherRestrictionsVocab((CopyrightAndOtherRestrictionsVocab) value, diagnostics,
					context);
		case LomPackage.COST:
			return validateCost((Cost) value, diagnostics, context);
		case LomPackage.COST_VALUE:
			return validateCostValue((CostValue) value, diagnostics, context);
		case LomPackage.COST_VOCAB:
			return validateCostVocab((CostVocab) value, diagnostics, context);
		case LomPackage.COVERAGE:
			return validateCoverage((Coverage) value, diagnostics, context);
		case LomPackage.DATE:
			return validateDate((Date) value, diagnostics, context);
		case LomPackage.DATE_TIME:
			return validateDateTime((DateTime) value, diagnostics, context);
		case LomPackage.DATE_TIME_VALUE:
			return validateDateTimeValue((DateTimeValue) value, diagnostics, context);
		case LomPackage.DESCRIPTION:
			return validateDescription((Description) value, diagnostics, context);
		case LomPackage.DIFFICULTY:
			return validateDifficulty((Difficulty) value, diagnostics, context);
		case LomPackage.DIFFICULTY_VALUE:
			return validateDifficultyValue((DifficultyValue) value, diagnostics, context);
		case LomPackage.DIFFICULTY_VOCAB:
			return validateDifficultyVocab((DifficultyVocab) value, diagnostics, context);
		case LomPackage.DURATION:
			return validateDuration((Duration) value, diagnostics, context);
		case LomPackage.DURATION_BASE:
			return validateDurationBase((DurationBase) value, diagnostics, context);
		case LomPackage.DURATION_VALUE:
			return validateDurationValue((DurationValue) value, diagnostics, context);
		case LomPackage.EDUCATIONAL:
			return validateEducational((Educational) value, diagnostics, context);
		case LomPackage.ENTITY:
			return validateEntity((Entity) value, diagnostics, context);
		case LomPackage.ENTRY:
			return validateEntry((Entry) value, diagnostics, context);
		case LomPackage.ENTRY_TAXON:
			return validateEntryTaxon((EntryTaxon) value, diagnostics, context);
		case LomPackage.FORMAT:
			return validateFormat((Format) value, diagnostics, context);
		case LomPackage.GENERAL:
			return validateGeneral((General) value, diagnostics, context);
		case LomPackage.ID:
			return validateId((Id) value, diagnostics, context);
		case LomPackage.IDENTIFIER:
			return validateIdentifier((Identifier) value, diagnostics, context);
		case LomPackage.INSTALLATION_REMARKS:
			return validateInstallationRemarks((InstallationRemarks) value, diagnostics, context);
		case LomPackage.INTENDED_END_USER_ROLE:
			return validateIntendedEndUserRole((IntendedEndUserRole) value, diagnostics, context);
		case LomPackage.INTENDED_END_USER_ROLE_VALUE:
			return validateIntendedEndUserRoleValue((IntendedEndUserRoleValue) value, diagnostics, context);
		case LomPackage.INTENDED_END_USER_ROLE_VOCAB:
			return validateIntendedEndUserRoleVocab((IntendedEndUserRoleVocab) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_LEVEL:
			return validateInteractivityLevel((InteractivityLevel) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_LEVEL_VALUE:
			return validateInteractivityLevelValue((InteractivityLevelValue) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_LEVEL_VOCAB:
			return validateInteractivityLevelVocab((InteractivityLevelVocab) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_TYPE:
			return validateInteractivityType((InteractivityType) value, diagnostics, context);
		case LomPackage.KEYWORD:
			return validateKeyword((Keyword) value, diagnostics, context);
		case LomPackage.KIND_VALUE:
			return validateKindValue((KindValue) value, diagnostics, context);
		case LomPackage.LANG_STRING:
			return validateLangString((LangString) value, diagnostics, context);
		case LomPackage.LANGUAGE_STRING:
			return validateLanguageString((LanguageString) value, diagnostics, context);
		case LomPackage.DOCUMENT_ROOT:
			return validateDocumentRoot((DocumentRoot) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_TYPE_VALUE:
			return validateInteractivityTypeValue((InteractivityTypeValue) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_TYPE_VOCAB:
			return validateInteractivityTypeVocab((InteractivityTypeVocab) value, diagnostics, context);
		case LomPackage.KIND:
			return validateKind((Kind) value, diagnostics, context);
		case LomPackage.KIND_VOCAB:
			return validateKindVocab((KindVocab) value, diagnostics, context);
		case LomPackage.LANGUAGE:
			return validateLanguage((Language) value, diagnostics, context);
		case LomPackage.LANGUAGE_ID:
			return validateLanguageId((LanguageId) value, diagnostics, context);
		case LomPackage.LEARNING_RESOURCE_TYPE:
			return validateLearningResourceType((LearningResourceType) value, diagnostics, context);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUE:
			return validateLearningResourceTypeValue((LearningResourceTypeValue) value, diagnostics, context);
		case LomPackage.LEARNING_RESOURCE_TYPE_VOCAB:
			return validateLearningResourceTypeVocab((LearningResourceTypeVocab) value, diagnostics, context);
		case LomPackage.LIFE_CYCLE:
			return validateLifeCycle((LifeCycle) value, diagnostics, context);
		case LomPackage.LOCATION:
			return validateLocation((Location) value, diagnostics, context);
		case LomPackage.LOM:
			return validateLom((Lom) value, diagnostics, context);
		case LomPackage.MAXIMUM_VERSION:
			return validateMaximumVersion((MaximumVersion) value, diagnostics, context);
		case LomPackage.METADATA_SCHEMA:
			return validateMetadataSchema((MetadataSchema) value, diagnostics, context);
		case LomPackage.META_METADATA:
			return validateMetaMetadata((MetaMetadata) value, diagnostics, context);
		case LomPackage.MINIMUM_VERSION:
			return validateMinimumVersion((MinimumVersion) value, diagnostics, context);
		case LomPackage.NAME:
			return validateName((Name) value, diagnostics, context);
		case LomPackage.NAME_VALUE:
			return validateNameValue((NameValue) value, diagnostics, context);
		case LomPackage.NAME_VOCAB:
			return validateNameVocab((NameVocab) value, diagnostics, context);
		case LomPackage.OR_COMPOSITE:
			return validateOrComposite((OrComposite) value, diagnostics, context);
		case LomPackage.OTHER_PLATFORM_REQUIREMENTS:
			return validateOtherPlatformRequirements((OtherPlatformRequirements) value, diagnostics, context);
		case LomPackage.PURPOSE:
			return validatePurpose((Purpose) value, diagnostics, context);
		case LomPackage.PURPOSE_VALUE:
			return validatePurposeValue((PurposeValue) value, diagnostics, context);
		case LomPackage.PURPOSE_VOCAB:
			return validatePurposeVocab((PurposeVocab) value, diagnostics, context);
		case LomPackage.RELATION:
			return validateRelation((Relation) value, diagnostics, context);
		case LomPackage.REQUIREMENT:
			return validateRequirement((Requirement) value, diagnostics, context);
		case LomPackage.RESOURCE:
			return validateResource((Resource) value, diagnostics, context);
		case LomPackage.RIGHTS:
			return validateRights((Rights) value, diagnostics, context);
		case LomPackage.ROLE:
			return validateRole((Role) value, diagnostics, context);
		case LomPackage.ROLE_META:
			return validateRoleMeta((RoleMeta) value, diagnostics, context);
		case LomPackage.ROLE_META_VALUE:
			return validateRoleMetaValue((RoleMetaValue) value, diagnostics, context);
		case LomPackage.ROLE_META_VOCAB:
			return validateRoleMetaVocab((RoleMetaVocab) value, diagnostics, context);
		case LomPackage.ROLE_VALUE:
			return validateRoleValue((RoleValue) value, diagnostics, context);
		case LomPackage.ROLE_VOCAB:
			return validateRoleVocab((RoleVocab) value, diagnostics, context);
		case LomPackage.SEMANTIC_DENSITY:
			return validateSemanticDensity((SemanticDensity) value, diagnostics, context);
		case LomPackage.SEMANTIC_DENSITY_VALUE:
			return validateSemanticDensityValue((SemanticDensityValue) value, diagnostics, context);
		case LomPackage.SEMANTIC_DENSITY_VOCAB:
			return validateSemanticDensityVocab((SemanticDensityVocab) value, diagnostics, context);
		case LomPackage.SIZE:
			return validateSize((Size) value, diagnostics, context);
		case LomPackage.SOURCE:
			return validateSource((Source) value, diagnostics, context);
		case LomPackage.SOURCE_VALUE:
			return validateSourceValue((SourceValue) value, diagnostics, context);
		case LomPackage.STATUS:
			return validateStatus((Status) value, diagnostics, context);
		case LomPackage.STATUS_VALUE:
			return validateStatusValue((StatusValue) value, diagnostics, context);
		case LomPackage.STATUS_VOCAB:
			return validateStatusVocab((StatusVocab) value, diagnostics, context);
		case LomPackage.STRUCTURE:
			return validateStructure((Structure) value, diagnostics, context);
		case LomPackage.STRUCTURE_VALUE:
			return validateStructureValue((StructureValue) value, diagnostics, context);
		case LomPackage.STRUCTURE_VOCAB:
			return validateStructureVocab((StructureVocab) value, diagnostics, context);
		case LomPackage.TAXON:
			return validateTaxon((Taxon) value, diagnostics, context);
		case LomPackage.TAXON_PATH:
			return validateTaxonPath((TaxonPath) value, diagnostics, context);
		case LomPackage.TECHNICAL:
			return validateTechnical((Technical) value, diagnostics, context);
		case LomPackage.TITLE:
			return validateTitle((Title) value, diagnostics, context);
		case LomPackage.TYPE:
			return validateType((Type) value, diagnostics, context);
		case LomPackage.TYPE_VALUE:
			return validateTypeValue((TypeValue) value, diagnostics, context);
		case LomPackage.TYPE_VOCAB:
			return validateTypeVocab((TypeVocab) value, diagnostics, context);
		case LomPackage.TYPICAL_AGE_RANGE:
			return validateTypicalAgeRange((TypicalAgeRange) value, diagnostics, context);
		case LomPackage.TYPICAL_LEARNING_TIME:
			return validateTypicalLearningTime((TypicalLearningTime) value, diagnostics, context);
		case LomPackage.VCARD:
			return validateVCard((VCard) value, diagnostics, context);
		case LomPackage.VERSION:
			return validateVersion((Version) value, diagnostics, context);
		case LomPackage.AGGREGATION_LEVEL_VALUES:
			return validateAggregationLevelValues((AggregationLevelValues) value, diagnostics, context);
		case LomPackage.CONTEXT_VALUES:
			return validateContextValues((ContextValues) value, diagnostics, context);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES:
			return validateCopyrightAndOtherRestrictionsValues((CopyrightAndOtherRestrictionsValues) value, diagnostics,
					context);
		case LomPackage.COST_VALUES:
			return validateCostValues((CostValues) value, diagnostics, context);
		case LomPackage.DIFFICULTY_VALUES:
			return validateDifficultyValues((DifficultyValues) value, diagnostics, context);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES:
			return validateIntendedEndUserRoleValues((IntendedEndUserRoleValues) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES:
			return validateInteractivityLevelValues((InteractivityLevelValues) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_TYPE_VALUES:
			return validateInteractivityTypeValues((InteractivityTypeValues) value, diagnostics, context);
		case LomPackage.KIND_VALUES:
			return validateKindValues((KindValues) value, diagnostics, context);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES:
			return validateLearningResourceTypeValues((LearningResourceTypeValues) value, diagnostics, context);
		case LomPackage.NAME_VALUES:
			return validateNameValues((NameValues) value, diagnostics, context);
		case LomPackage.PURPOSE_VALUES:
			return validatePurposeValues((PurposeValues) value, diagnostics, context);
		case LomPackage.ROLE_META_VALUES:
			return validateRoleMetaValues((RoleMetaValues) value, diagnostics, context);
		case LomPackage.ROLE_VALUES:
			return validateRoleValues((RoleValues) value, diagnostics, context);
		case LomPackage.SEMANTIC_DENSITY_VALUES:
			return validateSemanticDensityValues((SemanticDensityValues) value, diagnostics, context);
		case LomPackage.SOURCE_VALUES:
			return validateSourceValues((SourceValues) value, diagnostics, context);
		case LomPackage.STATUS_VALUES:
			return validateStatusValues((StatusValues) value, diagnostics, context);
		case LomPackage.STRUCTURE_VALUES:
			return validateStructureValues((StructureValues) value, diagnostics, context);
		case LomPackage.TYPE_VALUES:
			return validateTypeValues((TypeValues) value, diagnostics, context);
		case LomPackage.AGGREGATION_LEVEL_VALUES_OBJECT:
			return validateAggregationLevelValuesObject((AggregationLevelValues) value, diagnostics, context);
		case LomPackage.CHARACTER_STRING:
			return validateCharacterString((String) value, diagnostics, context);
		case LomPackage.CONTEXT_VALUES_OBJECT:
			return validateContextValuesObject((ContextValues) value, diagnostics, context);
		case LomPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES_OBJECT:
			return validateCopyrightAndOtherRestrictionsValuesObject((CopyrightAndOtherRestrictionsValues) value,
					diagnostics, context);
		case LomPackage.COST_VALUES_OBJECT:
			return validateCostValuesObject((CostValues) value, diagnostics, context);
		case LomPackage.DATE_TIME_STRING:
			return validateDateTimeString((String) value, diagnostics, context);
		case LomPackage.DIFFICULTY_VALUES_OBJECT:
			return validateDifficultyValuesObject((DifficultyValues) value, diagnostics, context);
		case LomPackage.DURATION_STRING:
			return validateDurationString((String) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_LEVEL_VALUES_OBJECT:
			return validateInteractivityLevelValuesObject((InteractivityLevelValues) value, diagnostics, context);
		case LomPackage.INTENDED_END_USER_ROLE_VALUES_OBJECT:
			return validateIntendedEndUserRoleValuesObject((IntendedEndUserRoleValues) value, diagnostics, context);
		case LomPackage.INTERACTIVITY_TYPE_VALUES_OBJECT:
			return validateInteractivityTypeValuesObject((InteractivityTypeValues) value, diagnostics, context);
		case LomPackage.KIND_VALUES_OBJECT:
			return validateKindValuesObject((KindValues) value, diagnostics, context);
		case LomPackage.LEARNING_RESOURCE_TYPE_VALUES_OBJECT:
			return validateLearningResourceTypeValuesObject((LearningResourceTypeValues) value, diagnostics, context);
		case LomPackage.MIME_TYPE:
			return validateMimeType((String) value, diagnostics, context);
		case LomPackage.NAME_VALUES_OBJECT:
			return validateNameValuesObject((NameValues) value, diagnostics, context);
		case LomPackage.PURPOSE_VALUES_OBJECT:
			return validatePurposeValuesObject((PurposeValues) value, diagnostics, context);
		case LomPackage.ROLE_META_VALUES_OBJECT:
			return validateRoleMetaValuesObject((RoleMetaValues) value, diagnostics, context);
		case LomPackage.ROLE_VALUES_OBJECT:
			return validateRoleValuesObject((RoleValues) value, diagnostics, context);
		case LomPackage.SEMANTIC_DENSITY_VALUES_OBJECT:
			return validateSemanticDensityValuesObject((SemanticDensityValues) value, diagnostics, context);
		case LomPackage.SIZE_BASE:
			return validateSizeBase((BigInteger) value, diagnostics, context);
		case LomPackage.SOURCE_VALUES_OBJECT:
			return validateSourceValuesObject((SourceValues) value, diagnostics, context);
		case LomPackage.STATUS_VALUES_OBJECT:
			return validateStatusValuesObject((StatusValues) value, diagnostics, context);
		case LomPackage.STRUCTURE_VALUES_OBJECT:
			return validateStructureValuesObject((StructureValues) value, diagnostics, context);
		case LomPackage.TYPE_VALUES_OBJECT:
			return validateTypeValuesObject((TypeValues) value, diagnostics, context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevel(AggregationLevel aggregationLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(aggregationLevel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevelValue(AggregationLevelValue aggregationLevelValue,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(aggregationLevelValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevelVocab(AggregationLevelVocab aggregationLevelVocab,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(aggregationLevelVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnnotation(Annotation annotation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(annotation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCatalog(Catalog catalog, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(catalog, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateClassification(Classification classification, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(classification, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContext(Context context, DiagnosticChain diagnostics, Map<Object, Object> theContext) {
		return validate_EveryDefaultConstraint(context, diagnostics, theContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContextValue(ContextValue contextValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(contextValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContextVocab(ContextVocab contextVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(contextVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContribute(Contribute contribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(contribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContributeMeta(ContributeMeta contributeMeta, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(contributeMeta, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictions(CopyrightAndOtherRestrictions copyrightAndOtherRestrictions,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(copyrightAndOtherRestrictions, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictionsValue(
			CopyrightAndOtherRestrictionsValue copyrightAndOtherRestrictionsValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(copyrightAndOtherRestrictionsValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictionsVocab(
			CopyrightAndOtherRestrictionsVocab copyrightAndOtherRestrictionsVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(copyrightAndOtherRestrictionsVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCost(Cost cost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(cost, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCostValue(CostValue costValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(costValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCostVocab(CostVocab costVocab, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(costVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCoverage(Coverage coverage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(coverage, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDate(Date date, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(date, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTime(DateTime dateTime, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dateTime, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeValue(DateTimeValue dateTimeValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dateTimeValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDescription(Description description, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(description, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficulty(Difficulty difficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(difficulty, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficultyValue(DifficultyValue difficultyValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(difficultyValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficultyVocab(DifficultyVocab difficultyVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(difficultyVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDuration(Duration duration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(duration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDurationBase(DurationBase durationBase, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(durationBase, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDurationValue(DurationValue durationValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(durationValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEducational(Educational educational, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(educational, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntity(Entity entity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntry(Entry entry, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entry, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntryTaxon(EntryTaxon entryTaxon, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entryTaxon, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormat(Format format, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(format, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGeneral(General general, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(general, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateId(Id id, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(id, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentifier(Identifier identifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(identifier, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInstallationRemarks(InstallationRemarks installationRemarks, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(installationRemarks, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRole(IntendedEndUserRole intendedEndUserRole, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(intendedEndUserRole, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRoleValue(IntendedEndUserRoleValue intendedEndUserRoleValue,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(intendedEndUserRoleValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRoleVocab(IntendedEndUserRoleVocab intendedEndUserRoleVocab,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(intendedEndUserRoleVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevel(InteractivityLevel interactivityLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityLevel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevelValue(InteractivityLevelValue interactivityLevelValue,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityLevelValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevelVocab(InteractivityLevelVocab interactivityLevelVocab,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityLevelVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityType(InteractivityType interactivityType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKeyword(Keyword keyword, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(keyword, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKindValue(KindValue kindValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(kindValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLangString(LangString langString, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(langString, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLanguageString(LanguageString languageString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(languageString, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityTypeValue(InteractivityTypeValue interactivityTypeValue,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityTypeValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityTypeVocab(InteractivityTypeVocab interactivityTypeVocab,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interactivityTypeVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKind(Kind kind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(kind, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKindVocab(KindVocab kindVocab, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(kindVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLanguage(Language language, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(language, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLanguageId(LanguageId languageId, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(languageId, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceType(LearningResourceType learningResourceType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(learningResourceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceTypeValue(LearningResourceTypeValue learningResourceTypeValue,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(learningResourceTypeValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceTypeVocab(LearningResourceTypeVocab learningResourceTypeVocab,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(learningResourceTypeVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLifeCycle(LifeCycle lifeCycle, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(lifeCycle, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocation(Location location, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(location, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLom(Lom lom, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(lom, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMaximumVersion(MaximumVersion maximumVersion, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(maximumVersion, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetadataSchema(MetadataSchema metadataSchema, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(metadataSchema, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetaMetadata(MetaMetadata metaMetadata, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(metaMetadata, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMinimumVersion(MinimumVersion minimumVersion, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(minimumVersion, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateName(Name name, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(name, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameValue(NameValue nameValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(nameValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameVocab(NameVocab nameVocab, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(nameVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOrComposite(OrComposite orComposite, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(orComposite, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOtherPlatformRequirements(OtherPlatformRequirements otherPlatformRequirements,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(otherPlatformRequirements, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurpose(Purpose purpose, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(purpose, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurposeValue(PurposeValue purposeValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(purposeValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurposeVocab(PurposeVocab purposeVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(purposeVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRelation(Relation relation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(relation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequirement(Requirement requirement, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(requirement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResource(Resource resource, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(resource, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRights(Rights rights, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(rights, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRole(Role role, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(role, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMeta(RoleMeta roleMeta, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleMeta, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMetaValue(RoleMetaValue roleMetaValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleMetaValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMetaVocab(RoleMetaVocab roleMetaVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleMetaVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleValue(RoleValue roleValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleVocab(RoleVocab roleVocab, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensity(SemanticDensity semanticDensity, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(semanticDensity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensityValue(SemanticDensityValue semanticDensityValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(semanticDensityValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensityVocab(SemanticDensityVocab semanticDensityVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(semanticDensityVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSize(Size size, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(size, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSource(Source source, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(source, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceValue(SourceValue sourceValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(sourceValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatus(Status status, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(status, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatusValue(StatusValue statusValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(statusValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatusVocab(StatusVocab statusVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(statusVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructure(Structure structure, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(structure, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructureValue(StructureValue structureValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(structureValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructureVocab(StructureVocab structureVocab, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(structureVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTaxon(Taxon taxon, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(taxon, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTaxonPath(TaxonPath taxonPath, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(taxonPath, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTechnical(Technical technical, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(technical, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTitle(Title title, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(title, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateType(Type type, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(type, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeValue(TypeValue typeValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(typeValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeVocab(TypeVocab typeVocab, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(typeVocab, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypicalAgeRange(TypicalAgeRange typicalAgeRange, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(typicalAgeRange, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypicalLearningTime(TypicalLearningTime typicalLearningTime, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(typicalLearningTime, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVCard(VCard vCard, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(vCard, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersion(Version version, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(version, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevelValues(AggregationLevelValues aggregationLevelValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContextValues(ContextValues contextValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictionsValues(
			CopyrightAndOtherRestrictionsValues copyrightAndOtherRestrictionsValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCostValues(CostValues costValues, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficultyValues(DifficultyValues difficultyValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRoleValues(IntendedEndUserRoleValues intendedEndUserRoleValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevelValues(InteractivityLevelValues interactivityLevelValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityTypeValues(InteractivityTypeValues interactivityTypeValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKindValues(KindValues kindValues, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceTypeValues(LearningResourceTypeValues learningResourceTypeValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameValues(NameValues nameValues, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurposeValues(PurposeValues purposeValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMetaValues(RoleMetaValues roleMetaValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleValues(RoleValues roleValues, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensityValues(SemanticDensityValues semanticDensityValues,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceValues(SourceValues sourceValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatusValues(StatusValues statusValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructureValues(StructureValues structureValues, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeValues(TypeValues typeValues, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevelValuesObject(AggregationLevelValues aggregationLevelValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCharacterString(String characterString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContextValuesObject(ContextValues contextValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictionsValuesObject(
			CopyrightAndOtherRestrictionsValues copyrightAndOtherRestrictionsValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCostValuesObject(CostValues costValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeString(String dateTimeString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateDateTimeString_Pattern(dateTimeString, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateDateTimeString_Pattern
	 */
	public static final PatternMatcher[][] DATE_TIME_STRING__PATTERN__VALUES = new PatternMatcher[][] {
			new PatternMatcher[] { XMLTypeUtil.createPatternMatcher(
					"([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})(\\-(0[1-9]|1[0-2])(\\-(0[1-9]|[1-2][0-9]|3[0-1])(T([0-1][0-9]|2[0-3])(:[0-5][0-9](:[0-5][0-9](\\.[0-9]{1,}(Z|((\\+|\\-)([0-1][0-9]|2[0-3]):[0-5][0-9]))?)?)?)?)?)?)?") } };

	/**
	 * Validates the Pattern constraint of '<em>Date Time String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeString_Pattern(String dateTimeString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validatePattern(LomPackage.eINSTANCE.getDateTimeString(), dateTimeString,
				DATE_TIME_STRING__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficultyValuesObject(DifficultyValues difficultyValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDurationString(String durationString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateDurationString_Pattern(durationString, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateDurationString_Pattern
	 */
	public static final PatternMatcher[][] DURATION_STRING__PATTERN__VALUES = new PatternMatcher[][] {
			new PatternMatcher[] { XMLTypeUtil.createPatternMatcher(
					"P([0-9]{1,}Y){0,1}([0-9]{1,}M){0,1}([0-9]{1,}D){0,1}(T([0-9]{1,}H){0,1}([0-9]{1,}M){0,1}([0-9]{1,}(\\.[0-9]{1,}){0,1}S){0,1}){0,1}") } };

	/**
	 * Validates the Pattern constraint of '<em>Duration String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDurationString_Pattern(String durationString, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return validatePattern(LomPackage.eINSTANCE.getDurationString(), durationString,
				DURATION_STRING__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevelValuesObject(InteractivityLevelValues interactivityLevelValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRoleValuesObject(IntendedEndUserRoleValues intendedEndUserRoleValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityTypeValuesObject(InteractivityTypeValues interactivityTypeValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKindValuesObject(KindValues kindValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceTypeValuesObject(LearningResourceTypeValues learningResourceTypeValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMimeType(String mimeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameValuesObject(NameValues nameValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurposeValuesObject(PurposeValues purposeValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMetaValuesObject(RoleMetaValues roleMetaValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleValuesObject(RoleValues roleValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensityValuesObject(SemanticDensityValues semanticDensityValuesObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSizeBase(BigInteger sizeBase, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = xmlTypeValidator.validateNonNegativeInteger_Min(sizeBase, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceValuesObject(SourceValues sourceValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatusValuesObject(StatusValues statusValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructureValuesObject(StructureValues structureValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeValuesObject(TypeValues typeValuesObject, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //LomValidator
