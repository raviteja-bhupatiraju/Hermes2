/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Educational</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getInteractivityType <em>Interactivity Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getLearningResourceType <em>Learning Resource Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getInteractivityLevel <em>Interactivity Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getSemanticDensity <em>Semantic Density</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getIntendedEndUserRole <em>Intended End User Role</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getContext <em>Context</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getTypicalAgeRange <em>Typical Age Range</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getDifficulty <em>Difficulty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getTypicalLearningTime <em>Typical Learning Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getLanguage <em>Language</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Educational#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational()
 * @model extendedMetaData="name='educational' kind='elementOnly'"
 * @generated
 */
public interface Educational extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Interactivity Type</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.InteractivityType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interactivity Type</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_InteractivityType()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='interactivityType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<InteractivityType> getInteractivityType();

	/**
	 * Returns the value of the '<em><b>Learning Resource Type</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LearningResourceType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Learning Resource Type</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_LearningResourceType()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='learningResourceType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LearningResourceType> getLearningResourceType();

	/**
	 * Returns the value of the '<em><b>Interactivity Level</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.InteractivityLevel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interactivity Level</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_InteractivityLevel()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='interactivityLevel' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<InteractivityLevel> getInteractivityLevel();

	/**
	 * Returns the value of the '<em><b>Semantic Density</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.SemanticDensity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Semantic Density</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_SemanticDensity()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='semanticDensity' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<SemanticDensity> getSemanticDensity();

	/**
	 * Returns the value of the '<em><b>Intended End User Role</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRole}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intended End User Role</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_IntendedEndUserRole()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='intendedEndUserRole' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<IntendedEndUserRole> getIntendedEndUserRole();

	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Context}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Context()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='context' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Context> getContext();

	/**
	 * Returns the value of the '<em><b>Typical Age Range</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.TypicalAgeRange}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typical Age Range</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_TypicalAgeRange()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='typicalAgeRange' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<TypicalAgeRange> getTypicalAgeRange();

	/**
	 * Returns the value of the '<em><b>Difficulty</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Difficulty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Difficulty</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Difficulty()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='difficulty' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Difficulty> getDifficulty();

	/**
	 * Returns the value of the '<em><b>Typical Learning Time</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.TypicalLearningTime}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typical Learning Time</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_TypicalLearningTime()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='typicalLearningTime' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<TypicalLearningTime> getTypicalLearningTime();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Description()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LanguageString> getDescription();

	/**
	 * Returns the value of the '<em><b>Language</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LanguageId}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Language()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='language' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LanguageId> getLanguage();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':12' processing='lax' group='#group:0'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getEducational_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':13' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Educational
