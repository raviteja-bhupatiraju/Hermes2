/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcarevocab;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 *  This is a MedBiquitous defined set of LOM custom vocabularies for healthcare.
 * <!-- end-model-doc -->
 * @see edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabFactory
 * @model kind="package"
 * @generated
 */
public interface HealthcarevocabPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "healthcarevocab";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://ns.medbiq.org/lom/vocab/v1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hv";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HealthcarevocabPackage eINSTANCE = edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.ContextValues <em>Context Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getContextValues()
	 * @generated
	 */
	int CONTEXT_VALUES = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues <em>Learning Resource Type Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getLearningResourceTypeValues()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VALUES = 1;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues <em>Purpose Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getPurposeValues()
	 * @generated
	 */
	int PURPOSE_VALUES = 2;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.RoleValues <em>Role Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getRoleValues()
	 * @generated
	 */
	int ROLE_VALUES = 3;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.SourceValues <em>Source Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getSourceValues()
	 * @generated
	 */
	int SOURCE_VALUES = 4;

	/**
	 * The meta object id for the '<em>Context Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getContextValuesObject()
	 * @generated
	 */
	int CONTEXT_VALUES_OBJECT = 5;

	/**
	 * The meta object id for the '<em>Learning Resource Type Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getLearningResourceTypeValuesObject()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VALUES_OBJECT = 6;

	/**
	 * The meta object id for the '<em>Purpose Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getPurposeValuesObject()
	 * @generated
	 */
	int PURPOSE_VALUES_OBJECT = 7;

	/**
	 * The meta object id for the '<em>Role Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getRoleValuesObject()
	 * @generated
	 */
	int ROLE_VALUES_OBJECT = 8;

	/**
	 * The meta object id for the '<em>Source Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getSourceValuesObject()
	 * @generated
	 */
	int SOURCE_VALUES_OBJECT = 9;

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcarevocab.ContextValues <em>Context Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Context Values</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
	 * @generated
	 */
	EEnum getContextValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues <em>Learning Resource Type Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Learning Resource Type Values</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
	 * @generated
	 */
	EEnum getLearningResourceTypeValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues <em>Purpose Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Purpose Values</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
	 * @generated
	 */
	EEnum getPurposeValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcarevocab.RoleValues <em>Role Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Role Values</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
	 * @generated
	 */
	EEnum getRoleValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcarevocab.SourceValues <em>Source Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Source Values</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
	 * @generated
	 */
	EEnum getSourceValues();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcarevocab.ContextValues <em>Context Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Context Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcarevocab.ContextValues"
	 *        extendedMetaData="name='contextValues:Object' baseType='contextValues'"
	 * @generated
	 */
	EDataType getContextValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues <em>Learning Resource Type Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Learning Resource Type Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues"
	 *        extendedMetaData="name='learningResourceTypeValues:Object' baseType='learningResourceTypeValues'"
	 * @generated
	 */
	EDataType getLearningResourceTypeValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues <em>Purpose Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Purpose Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues"
	 *        extendedMetaData="name='purposeValues:Object' baseType='purposeValues'"
	 * @generated
	 */
	EDataType getPurposeValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcarevocab.RoleValues <em>Role Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Role Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcarevocab.RoleValues"
	 *        extendedMetaData="name='roleValues:Object' baseType='roleValues'"
	 * @generated
	 */
	EDataType getRoleValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcarevocab.SourceValues <em>Source Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Source Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcarevocab.SourceValues"
	 *        extendedMetaData="name='sourceValues:Object' baseType='sourceValues'"
	 * @generated
	 */
	EDataType getSourceValuesObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HealthcarevocabFactory getHealthcarevocabFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.ContextValues <em>Context Values</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getContextValues()
		 * @generated
		 */
		EEnum CONTEXT_VALUES = eINSTANCE.getContextValues();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues <em>Learning Resource Type Values</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getLearningResourceTypeValues()
		 * @generated
		 */
		EEnum LEARNING_RESOURCE_TYPE_VALUES = eINSTANCE.getLearningResourceTypeValues();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues <em>Purpose Values</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getPurposeValues()
		 * @generated
		 */
		EEnum PURPOSE_VALUES = eINSTANCE.getPurposeValues();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.RoleValues <em>Role Values</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getRoleValues()
		 * @generated
		 */
		EEnum ROLE_VALUES = eINSTANCE.getRoleValues();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcarevocab.SourceValues <em>Source Values</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getSourceValues()
		 * @generated
		 */
		EEnum SOURCE_VALUES = eINSTANCE.getSourceValues();

		/**
		 * The meta object literal for the '<em>Context Values Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.ContextValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getContextValuesObject()
		 * @generated
		 */
		EDataType CONTEXT_VALUES_OBJECT = eINSTANCE.getContextValuesObject();

		/**
		 * The meta object literal for the '<em>Learning Resource Type Values Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.LearningResourceTypeValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getLearningResourceTypeValuesObject()
		 * @generated
		 */
		EDataType LEARNING_RESOURCE_TYPE_VALUES_OBJECT = eINSTANCE.getLearningResourceTypeValuesObject();

		/**
		 * The meta object literal for the '<em>Purpose Values Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.PurposeValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getPurposeValuesObject()
		 * @generated
		 */
		EDataType PURPOSE_VALUES_OBJECT = eINSTANCE.getPurposeValuesObject();

		/**
		 * The meta object literal for the '<em>Role Values Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.RoleValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getRoleValuesObject()
		 * @generated
		 */
		EDataType ROLE_VALUES_OBJECT = eINSTANCE.getRoleValuesObject();

		/**
		 * The meta object literal for the '<em>Source Values Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.SourceValues
		 * @see edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl#getSourceValuesObject()
		 * @generated
		 */
		EDataType SOURCE_VALUES_OBJECT = eINSTANCE.getSourceValuesObject();

	}

} //HealthcarevocabPackage
