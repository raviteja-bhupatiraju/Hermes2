/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.vocab.impl;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabFactory;
import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;

import edu.louisiana.caai.lom.model.vocab.*;

import org.eclipse.emf.common.util.Enumerator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.util.Diagnostician;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VocabFactoryImpl extends EFactoryImpl implements VocabFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static VocabFactory init() {
		try {
			VocabFactory theVocabFactory = (VocabFactory) EPackage.Registry.INSTANCE.getEFactory(VocabPackage.eNS_URI);
			if (theVocabFactory != null) {
				return theVocabFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VocabFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VocabFactoryImpl() {
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
		case VocabPackage.AGGREGATION_LEVEL:
			return createAggregationLevelFromString(eDataType, initialValue);
		case VocabPackage.CONTEXT:
			return createContextFromString(eDataType, initialValue);
		case VocabPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS:
			return createCopyrightAndOtherRestrictionsFromString(eDataType, initialValue);
		case VocabPackage.COST:
			return createCostFromString(eDataType, initialValue);
		case VocabPackage.DIFFICULTY:
			return createDifficultyFromString(eDataType, initialValue);
		case VocabPackage.INTENDED_END_USER_ROLE:
			return createIntendedEndUserRoleFromString(eDataType, initialValue);
		case VocabPackage.INTERACTIVITY_LEVEL:
			return createInteractivityLevelFromString(eDataType, initialValue);
		case VocabPackage.INTERACTIVITY_TYPE:
			return createInteractivityTypeFromString(eDataType, initialValue);
		case VocabPackage.KIND:
			return createKindFromString(eDataType, initialValue);
		case VocabPackage.LEARNING_RESOURCE_TYPE:
			return createLearningResourceTypeFromString(eDataType, initialValue);
		case VocabPackage.NAME:
			return createNameFromString(eDataType, initialValue);
		case VocabPackage.PURPOSE:
			return createPurposeFromString(eDataType, initialValue);
		case VocabPackage.ROLE:
			return createRoleFromString(eDataType, initialValue);
		case VocabPackage.ROLE_META:
			return createRoleMetaFromString(eDataType, initialValue);
		case VocabPackage.SEMANTIC_DENSITY:
			return createSemanticDensityFromString(eDataType, initialValue);
		case VocabPackage.SOURCE:
			return createSourceFromString(eDataType, initialValue);
		case VocabPackage.STATUS:
			return createStatusFromString(eDataType, initialValue);
		case VocabPackage.STRUCTURE:
			return createStructureFromString(eDataType, initialValue);
		case VocabPackage.TYPE:
			return createTypeFromString(eDataType, initialValue);
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
		case VocabPackage.AGGREGATION_LEVEL:
			return convertAggregationLevelToString(eDataType, instanceValue);
		case VocabPackage.CONTEXT:
			return convertContextToString(eDataType, instanceValue);
		case VocabPackage.COPYRIGHT_AND_OTHER_RESTRICTIONS:
			return convertCopyrightAndOtherRestrictionsToString(eDataType, instanceValue);
		case VocabPackage.COST:
			return convertCostToString(eDataType, instanceValue);
		case VocabPackage.DIFFICULTY:
			return convertDifficultyToString(eDataType, instanceValue);
		case VocabPackage.INTENDED_END_USER_ROLE:
			return convertIntendedEndUserRoleToString(eDataType, instanceValue);
		case VocabPackage.INTERACTIVITY_LEVEL:
			return convertInteractivityLevelToString(eDataType, instanceValue);
		case VocabPackage.INTERACTIVITY_TYPE:
			return convertInteractivityTypeToString(eDataType, instanceValue);
		case VocabPackage.KIND:
			return convertKindToString(eDataType, instanceValue);
		case VocabPackage.LEARNING_RESOURCE_TYPE:
			return convertLearningResourceTypeToString(eDataType, instanceValue);
		case VocabPackage.NAME:
			return convertNameToString(eDataType, instanceValue);
		case VocabPackage.PURPOSE:
			return convertPurposeToString(eDataType, instanceValue);
		case VocabPackage.ROLE:
			return convertRoleToString(eDataType, instanceValue);
		case VocabPackage.ROLE_META:
			return convertRoleMetaToString(eDataType, instanceValue);
		case VocabPackage.SEMANTIC_DENSITY:
			return convertSemanticDensityToString(eDataType, instanceValue);
		case VocabPackage.SOURCE:
			return convertSourceToString(eDataType, instanceValue);
		case VocabPackage.STATUS:
			return convertStatusToString(eDataType, instanceValue);
		case VocabPackage.STRUCTURE:
			return convertStructureToString(eDataType, instanceValue);
		case VocabPackage.TYPE:
			return convertTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createAggregationLevelFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getAggregationLevelValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAggregationLevelToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getAggregationLevelValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getAggregationLevelValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createContextFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getContextValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (Enumerator) HealthcarevocabFactory.eINSTANCE
					.createFromString(HealthcarevocabPackage.Literals.CONTEXT_VALUES, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContextToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getContextValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getContextValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (HealthcarevocabPackage.Literals.CONTEXT_VALUES.isInstance(instanceValue)) {
			try {
				String value = HealthcarevocabFactory.eINSTANCE
						.convertToString(HealthcarevocabPackage.Literals.CONTEXT_VALUES, instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createCopyrightAndOtherRestrictionsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCopyrightAndOtherRestrictionsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE
						.convertToString(LomPackage.eINSTANCE.getCopyrightAndOtherRestrictionsValues(), instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createCostFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getCostValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCostToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getCostValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getCostValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createDifficultyFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getDifficultyValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifficultyToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getDifficultyValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getDifficultyValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createIntendedEndUserRoleFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getIntendedEndUserRoleValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIntendedEndUserRoleToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getIntendedEndUserRoleValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getIntendedEndUserRoleValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createInteractivityLevelFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getInteractivityLevelValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInteractivityLevelToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getInteractivityLevelValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getInteractivityLevelValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createInteractivityTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getInteractivityTypeValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInteractivityTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getInteractivityTypeValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getInteractivityTypeValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createKindFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getKindValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertKindToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getKindValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getKindValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createLearningResourceTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE
					.createFromString(LomPackage.eINSTANCE.getLearningResourceTypeValues(), initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (Enumerator) HealthcarevocabFactory.eINSTANCE
					.createFromString(HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLearningResourceTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getLearningResourceTypeValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE
						.convertToString(LomPackage.eINSTANCE.getLearningResourceTypeValues(), instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES.isInstance(instanceValue)) {
			try {
				String value = HealthcarevocabFactory.eINSTANCE
						.convertToString(HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES, instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createNameFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getNameValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getNameValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getNameValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createPurposeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getPurposeValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (Enumerator) HealthcarevocabFactory.eINSTANCE
					.createFromString(HealthcarevocabPackage.Literals.PURPOSE_VALUES, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPurposeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getPurposeValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getPurposeValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (HealthcarevocabPackage.Literals.PURPOSE_VALUES.isInstance(instanceValue)) {
			try {
				String value = HealthcarevocabFactory.eINSTANCE
						.convertToString(HealthcarevocabPackage.Literals.PURPOSE_VALUES, instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createRoleFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getRoleValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (Enumerator) HealthcarevocabFactory.eINSTANCE
					.createFromString(HealthcarevocabPackage.Literals.ROLE_VALUES, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoleToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getRoleValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getRoleValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (HealthcarevocabPackage.Literals.ROLE_VALUES.isInstance(instanceValue)) {
			try {
				String value = HealthcarevocabFactory.eINSTANCE
						.convertToString(HealthcarevocabPackage.Literals.ROLE_VALUES, instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createRoleMetaFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getRoleMetaValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoleMetaToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getRoleMetaValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getRoleMetaValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createSemanticDensityFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getSemanticDensityValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSemanticDensityToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getSemanticDensityValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getSemanticDensityValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createSourceFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getSourceValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (Enumerator) HealthcarevocabFactory.eINSTANCE
					.createFromString(HealthcarevocabPackage.Literals.SOURCE_VALUES, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSourceToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getSourceValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getSourceValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (HealthcarevocabPackage.Literals.SOURCE_VALUES.isInstance(instanceValue)) {
			try {
				String value = HealthcarevocabFactory.eINSTANCE
						.convertToString(HealthcarevocabPackage.Literals.SOURCE_VALUES, instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createStatusFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getStatusValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStatusToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getStatusValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getStatusValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createStructureFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getStructureValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStructureToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getStructureValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getStructureValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null)
			return null;
		Enumerator result = null;
		RuntimeException exception = null;
		try {
			result = (Enumerator) LomFactory.eINSTANCE.createFromString(LomPackage.eINSTANCE.getTypeValues(),
					initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		} catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null)
			return result;

		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null)
			return null;
		if (LomPackage.eINSTANCE.getTypeValues().isInstance(instanceValue)) {
			try {
				String value = LomFactory.eINSTANCE.convertToString(LomPackage.eINSTANCE.getTypeValues(),
						instanceValue);
				if (value != null)
					return value;
			} catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException(
				"Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VocabPackage getVocabPackage() {
		return (VocabPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VocabPackage getPackage() {
		return VocabPackage.eINSTANCE;
	}

} //VocabFactoryImpl
