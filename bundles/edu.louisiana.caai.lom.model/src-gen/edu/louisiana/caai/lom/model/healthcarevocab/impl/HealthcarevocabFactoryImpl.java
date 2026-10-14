/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcarevocab.impl;

import edu.louisiana.caai.lom.model.healthcarevocab.*;

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
public class HealthcarevocabFactoryImpl extends EFactoryImpl implements HealthcarevocabFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HealthcarevocabFactory init() {
		try {
			HealthcarevocabFactory theHealthcarevocabFactory = (HealthcarevocabFactory) EPackage.Registry.INSTANCE
					.getEFactory(HealthcarevocabPackage.eNS_URI);
			if (theHealthcarevocabFactory != null) {
				return theHealthcarevocabFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HealthcarevocabFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HealthcarevocabFactoryImpl() {
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
		case HealthcarevocabPackage.CONTEXT_VALUES:
			return createContextValuesFromString(eDataType, initialValue);
		case HealthcarevocabPackage.LEARNING_RESOURCE_TYPE_VALUES:
			return createLearningResourceTypeValuesFromString(eDataType, initialValue);
		case HealthcarevocabPackage.PURPOSE_VALUES:
			return createPurposeValuesFromString(eDataType, initialValue);
		case HealthcarevocabPackage.ROLE_VALUES:
			return createRoleValuesFromString(eDataType, initialValue);
		case HealthcarevocabPackage.SOURCE_VALUES:
			return createSourceValuesFromString(eDataType, initialValue);
		case HealthcarevocabPackage.CONTEXT_VALUES_OBJECT:
			return createContextValuesObjectFromString(eDataType, initialValue);
		case HealthcarevocabPackage.LEARNING_RESOURCE_TYPE_VALUES_OBJECT:
			return createLearningResourceTypeValuesObjectFromString(eDataType, initialValue);
		case HealthcarevocabPackage.PURPOSE_VALUES_OBJECT:
			return createPurposeValuesObjectFromString(eDataType, initialValue);
		case HealthcarevocabPackage.ROLE_VALUES_OBJECT:
			return createRoleValuesObjectFromString(eDataType, initialValue);
		case HealthcarevocabPackage.SOURCE_VALUES_OBJECT:
			return createSourceValuesObjectFromString(eDataType, initialValue);
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
		case HealthcarevocabPackage.CONTEXT_VALUES:
			return convertContextValuesToString(eDataType, instanceValue);
		case HealthcarevocabPackage.LEARNING_RESOURCE_TYPE_VALUES:
			return convertLearningResourceTypeValuesToString(eDataType, instanceValue);
		case HealthcarevocabPackage.PURPOSE_VALUES:
			return convertPurposeValuesToString(eDataType, instanceValue);
		case HealthcarevocabPackage.ROLE_VALUES:
			return convertRoleValuesToString(eDataType, instanceValue);
		case HealthcarevocabPackage.SOURCE_VALUES:
			return convertSourceValuesToString(eDataType, instanceValue);
		case HealthcarevocabPackage.CONTEXT_VALUES_OBJECT:
			return convertContextValuesObjectToString(eDataType, instanceValue);
		case HealthcarevocabPackage.LEARNING_RESOURCE_TYPE_VALUES_OBJECT:
			return convertLearningResourceTypeValuesObjectToString(eDataType, instanceValue);
		case HealthcarevocabPackage.PURPOSE_VALUES_OBJECT:
			return convertPurposeValuesObjectToString(eDataType, instanceValue);
		case HealthcarevocabPackage.ROLE_VALUES_OBJECT:
			return convertRoleValuesObjectToString(eDataType, instanceValue);
		case HealthcarevocabPackage.SOURCE_VALUES_OBJECT:
			return convertSourceValuesObjectToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
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
	public ContextValues createContextValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createContextValuesFromString(HealthcarevocabPackage.Literals.CONTEXT_VALUES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContextValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertContextValuesToString(HealthcarevocabPackage.Literals.CONTEXT_VALUES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LearningResourceTypeValues createLearningResourceTypeValuesObjectFromString(EDataType eDataType,
			String initialValue) {
		return createLearningResourceTypeValuesFromString(HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLearningResourceTypeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertLearningResourceTypeValuesToString(HealthcarevocabPackage.Literals.LEARNING_RESOURCE_TYPE_VALUES,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PurposeValues createPurposeValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createPurposeValuesFromString(HealthcarevocabPackage.Literals.PURPOSE_VALUES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPurposeValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPurposeValuesToString(HealthcarevocabPackage.Literals.PURPOSE_VALUES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleValues createRoleValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createRoleValuesFromString(HealthcarevocabPackage.Literals.ROLE_VALUES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRoleValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRoleValuesToString(HealthcarevocabPackage.Literals.ROLE_VALUES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceValues createSourceValuesObjectFromString(EDataType eDataType, String initialValue) {
		return createSourceValuesFromString(HealthcarevocabPackage.Literals.SOURCE_VALUES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSourceValuesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSourceValuesToString(HealthcarevocabPackage.Literals.SOURCE_VALUES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcarevocabPackage getHealthcarevocabPackage() {
		return (HealthcarevocabPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HealthcarevocabPackage getPackage() {
		return HealthcarevocabPackage.eINSTANCE;
	}

} //HealthcarevocabFactoryImpl
