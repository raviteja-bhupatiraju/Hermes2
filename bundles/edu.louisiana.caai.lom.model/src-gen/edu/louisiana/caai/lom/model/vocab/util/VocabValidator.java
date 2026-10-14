/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.vocab.util;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import edu.louisiana.caai.lom.model.lom.AggregationLevelValues;
import edu.louisiana.caai.lom.model.lom.ContextValues;
import edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues;
import edu.louisiana.caai.lom.model.lom.CostValues;
import edu.louisiana.caai.lom.model.lom.DifficultyValues;
import edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues;
import edu.louisiana.caai.lom.model.lom.InteractivityLevelValues;
import edu.louisiana.caai.lom.model.lom.InteractivityTypeValues;
import edu.louisiana.caai.lom.model.lom.KindValues;
import edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.NameValues;
import edu.louisiana.caai.lom.model.lom.PurposeValues;
import edu.louisiana.caai.lom.model.lom.RoleMetaValues;
import edu.louisiana.caai.lom.model.lom.RoleValues;
import edu.louisiana.caai.lom.model.lom.SemanticDensityValues;
import edu.louisiana.caai.lom.model.lom.SourceValues;
import edu.louisiana.caai.lom.model.lom.StatusValues;
import edu.louisiana.caai.lom.model.lom.StructureValues;
import edu.louisiana.caai.lom.model.lom.TypeValues;

import edu.louisiana.caai.lom.model.lom.util.LomValidator;

import edu.louisiana.caai.lom.model.vocab.*;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.vocab.VocabPackage
 * @generated
 */
public class VocabValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final VocabValidator INSTANCE = new VocabValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "edu.louisiana.caai.lom.model.vocab";

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
	protected LomValidator lomValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VocabValidator() {
		super();
		lomValidator = LomValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return VocabPackage.eINSTANCE;
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
		case VocabPackage.AGGREGATION_LEVEL:
			return validateAggregationLevel((Enumerator) value, diagnostics, context);
		case VocabPackage.CONTEXT:
			return validateContext((Enumerator) value, diagnostics, context);
		case VocabPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS:
			return validateCopyrightAndOtherRestrictions((Enumerator) value, diagnostics, context);
		case VocabPackage.COST:
			return validateCost((Enumerator) value, diagnostics, context);
		case VocabPackage.DIFFICULTY:
			return validateDifficulty((Enumerator) value, diagnostics, context);
		case VocabPackage.INTENDED_END_USER_ROLE:
			return validateIntendedEndUserRole((Enumerator) value, diagnostics, context);
		case VocabPackage.INTERACTIVITY_LEVEL:
			return validateInteractivityLevel((Enumerator) value, diagnostics, context);
		case VocabPackage.INTERACTIVITY_TYPE:
			return validateInteractivityType((Enumerator) value, diagnostics, context);
		case VocabPackage.KIND:
			return validateKind((Enumerator) value, diagnostics, context);
		case VocabPackage.LEARNING_RESOURCE_TYPE:
			return validateLearningResourceType((Enumerator) value, diagnostics, context);
		case VocabPackage.NAME:
			return validateName((Enumerator) value, diagnostics, context);
		case VocabPackage.PURPOSE:
			return validatePurpose((Enumerator) value, diagnostics, context);
		case VocabPackage.ROLE:
			return validateRole((Enumerator) value, diagnostics, context);
		case VocabPackage.ROLE_META:
			return validateRoleMeta((Enumerator) value, diagnostics, context);
		case VocabPackage.SEMANTIC_DENSITY:
			return validateSemanticDensity((Enumerator) value, diagnostics, context);
		case VocabPackage.SOURCE:
			return validateSource((Enumerator) value, diagnostics, context);
		case VocabPackage.STATUS:
			return validateStatus((Enumerator) value, diagnostics, context);
		case VocabPackage.STRUCTURE:
			return validateStructure((Enumerator) value, diagnostics, context);
		case VocabPackage.TYPE:
			return validateType((Enumerator) value, diagnostics, context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevel(Enumerator aggregationLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateAggregationLevel_MemberTypes(aggregationLevel, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Aggregation Level</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAggregationLevel_MemberTypes(Enumerator aggregationLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getAggregationLevelValues().isInstance(aggregationLevel)) {
				if (lomValidator.validateAggregationLevelValues((AggregationLevelValues) aggregationLevel,
						tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getAggregationLevelValues().isInstance(aggregationLevel)) {
				if (lomValidator.validateAggregationLevelValues((AggregationLevelValues) aggregationLevel, null,
						context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContext(Enumerator context, DiagnosticChain diagnostics, Map<Object, Object> theContext) {
		boolean result = validateContext_MemberTypes(context, diagnostics, theContext);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContext_MemberTypes(Enumerator context, DiagnosticChain diagnostics,
			Map<Object, Object> theContext) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getContextValues().isInstance(context)) {
				if (lomValidator.validateContextValues((ContextValues) context, tempDiagnostics, theContext))
					return true;
			}
			if (HealthcarevocabPackage.Literals.CONTEXT_VALUES.isInstance(context)) {
				return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getContextValues().isInstance(context)) {
				if (lomValidator.validateContextValues((ContextValues) context, null, theContext))
					return true;
			}
			if (HealthcarevocabPackage.Literals.CONTEXT_VALUES.isInstance(context)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictions(Enumerator copyrightAndOtherRestrictions,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateCopyrightAndOtherRestrictions_MemberTypes(copyrightAndOtherRestrictions, diagnostics,
				context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Copyright And Other Restrictions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCopyrightAndOtherRestrictions_MemberTypes(Enumerator copyrightAndOtherRestrictions,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues()
					.isInstance(copyrightAndOtherRestrictions)) {
				if (lomValidator.validateCopyrightAndOtherRestrictionsValues(
						(CopyrightAndOtherRestrictionsValues) copyrightAndOtherRestrictions, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues()
					.isInstance(copyrightAndOtherRestrictions)) {
				if (lomValidator.validateCopyrightAndOtherRestrictionsValues(
						(CopyrightAndOtherRestrictionsValues) copyrightAndOtherRestrictions, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCost(Enumerator cost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateCost_MemberTypes(cost, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Cost</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCost_MemberTypes(Enumerator cost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getCostValues().isInstance(cost)) {
				if (lomValidator.validateCostValues((CostValues) cost, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getCostValues().isInstance(cost)) {
				if (lomValidator.validateCostValues((CostValues) cost, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficulty(Enumerator difficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateDifficulty_MemberTypes(difficulty, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Difficulty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficulty_MemberTypes(Enumerator difficulty, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getDifficultyValues().isInstance(difficulty)) {
				if (lomValidator.validateDifficultyValues((DifficultyValues) difficulty, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getDifficultyValues().isInstance(difficulty)) {
				if (lomValidator.validateDifficultyValues((DifficultyValues) difficulty, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRole(Enumerator intendedEndUserRole, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateIntendedEndUserRole_MemberTypes(intendedEndUserRole, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Intended End User Role</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntendedEndUserRole_MemberTypes(Enumerator intendedEndUserRole, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getIntendedEndUserRoleValues().isInstance(intendedEndUserRole)) {
				if (lomValidator.validateIntendedEndUserRoleValues((IntendedEndUserRoleValues) intendedEndUserRole,
						tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getIntendedEndUserRoleValues().isInstance(intendedEndUserRole)) {
				if (lomValidator.validateIntendedEndUserRoleValues((IntendedEndUserRoleValues) intendedEndUserRole,
						null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevel(Enumerator interactivityLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateInteractivityLevel_MemberTypes(interactivityLevel, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Interactivity Level</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityLevel_MemberTypes(Enumerator interactivityLevel, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getInteractivityLevelValues().isInstance(interactivityLevel)) {
				if (lomValidator.validateInteractivityLevelValues((InteractivityLevelValues) interactivityLevel,
						tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getInteractivityLevelValues().isInstance(interactivityLevel)) {
				if (lomValidator.validateInteractivityLevelValues((InteractivityLevelValues) interactivityLevel, null,
						context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityType(Enumerator interactivityType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateInteractivityType_MemberTypes(interactivityType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Interactivity Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInteractivityType_MemberTypes(Enumerator interactivityType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getInteractivityTypeValues().isInstance(interactivityType)) {
				if (lomValidator.validateInteractivityTypeValues((InteractivityTypeValues) interactivityType,
						tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getInteractivityTypeValues().isInstance(interactivityType)) {
				if (lomValidator.validateInteractivityTypeValues((InteractivityTypeValues) interactivityType, null,
						context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKind(Enumerator kind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateKind_MemberTypes(kind, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Kind</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKind_MemberTypes(Enumerator kind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getKindValues().isInstance(kind)) {
				if (lomValidator.validateKindValues((KindValues) kind, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getKindValues().isInstance(kind)) {
				if (lomValidator.validateKindValues((KindValues) kind, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceType(Enumerator learningResourceType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateLearningResourceType_MemberTypes(learningResourceType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Learning Resource Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningResourceType_MemberTypes(Enumerator learningResourceType,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getLearningResourceTypeValues().isInstance(learningResourceType)) {
				if (lomValidator.validateLearningResourceTypeValues((LearningResourceTypeValues) learningResourceType,
						tempDiagnostics, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES.isInstance(learningResourceType)) {
				return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getLearningResourceTypeValues().isInstance(learningResourceType)) {
				if (lomValidator.validateLearningResourceTypeValues((LearningResourceTypeValues) learningResourceType,
						null, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES.isInstance(learningResourceType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateName(Enumerator name, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateName_MemberTypes(name, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateName_MemberTypes(Enumerator name, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getNameValues().isInstance(name)) {
				if (lomValidator.validateNameValues((NameValues) name, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getNameValues().isInstance(name)) {
				if (lomValidator.validateNameValues((NameValues) name, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurpose(Enumerator purpose, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validatePurpose_MemberTypes(purpose, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Purpose</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePurpose_MemberTypes(Enumerator purpose, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getPurposeValues().isInstance(purpose)) {
				if (lomValidator.validatePurposeValues((PurposeValues) purpose, tempDiagnostics, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.PURPOSE_VALUES.isInstance(purpose)) {
				return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getPurposeValues().isInstance(purpose)) {
				if (lomValidator.validatePurposeValues((PurposeValues) purpose, null, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.PURPOSE_VALUES.isInstance(purpose)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRole(Enumerator role, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateRole_MemberTypes(role, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Role</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRole_MemberTypes(Enumerator role, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getRoleValues().isInstance(role)) {
				if (lomValidator.validateRoleValues((RoleValues) role, tempDiagnostics, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.ROLE_VALUES.isInstance(role)) {
				return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getRoleValues().isInstance(role)) {
				if (lomValidator.validateRoleValues((RoleValues) role, null, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.ROLE_VALUES.isInstance(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMeta(Enumerator roleMeta, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateRoleMeta_MemberTypes(roleMeta, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Role Meta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleMeta_MemberTypes(Enumerator roleMeta, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getRoleMetaValues().isInstance(roleMeta)) {
				if (lomValidator.validateRoleMetaValues((RoleMetaValues) roleMeta, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getRoleMetaValues().isInstance(roleMeta)) {
				if (lomValidator.validateRoleMetaValues((RoleMetaValues) roleMeta, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensity(Enumerator semanticDensity, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		boolean result = validateSemanticDensity_MemberTypes(semanticDensity, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Semantic Density</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSemanticDensity_MemberTypes(Enumerator semanticDensity, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getSemanticDensityValues().isInstance(semanticDensity)) {
				if (lomValidator.validateSemanticDensityValues((SemanticDensityValues) semanticDensity, tempDiagnostics,
						context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getSemanticDensityValues().isInstance(semanticDensity)) {
				if (lomValidator.validateSemanticDensityValues((SemanticDensityValues) semanticDensity, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSource(Enumerator source, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateSource_MemberTypes(source, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSource_MemberTypes(Enumerator source, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getSourceValues().isInstance(source)) {
				if (lomValidator.validateSourceValues((SourceValues) source, tempDiagnostics, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.SOURCE_VALUES.isInstance(source)) {
				return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getSourceValues().isInstance(source)) {
				if (lomValidator.validateSourceValues((SourceValues) source, null, context))
					return true;
			}
			if (HealthcarevocabPackage.Literals.SOURCE_VALUES.isInstance(source)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatus(Enumerator status, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateStatus_MemberTypes(status, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Status</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatus_MemberTypes(Enumerator status, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getStatusValues().isInstance(status)) {
				if (lomValidator.validateStatusValues((StatusValues) status, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getStatusValues().isInstance(status)) {
				if (lomValidator.validateStatusValues((StatusValues) status, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructure(Enumerator structure, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateStructure_MemberTypes(structure, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Structure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructure_MemberTypes(Enumerator structure, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getStructureValues().isInstance(structure)) {
				if (lomValidator.validateStructureValues((StructureValues) structure, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getStructureValues().isInstance(structure)) {
				if (lomValidator.validateStructureValues((StructureValues) structure, null, context))
					return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateType(Enumerator type, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateType_MemberTypes(type, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateType_MemberTypes(Enumerator type, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (LomPackage.eINSTANCE.getTypeValues().isInstance(type)) {
				if (lomValidator.validateTypeValues((TypeValues) type, tempDiagnostics, context))
					return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		} else {
			if (LomPackage.eINSTANCE.getTypeValues().isInstance(type)) {
				if (lomValidator.validateTypeValues((TypeValues) type, null, context))
					return true;
			}
		}
		return false;
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

} //VocabValidator
