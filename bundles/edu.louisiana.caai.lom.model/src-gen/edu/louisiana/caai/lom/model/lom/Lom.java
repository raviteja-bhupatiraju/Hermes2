/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import edu.louisiana.caai.lom.model.healthcare.CustomElementsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lom</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getGeneral <em>General</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getLifeCycle <em>Life Cycle</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getMetaMetadata <em>Meta Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getTechnical <em>Technical</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getEducational <em>Educational</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getRights <em>Rights</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getRelation <em>Relation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getAnnotation <em>Annotation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getClassification <em>Classification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getHealthcareMetadata <em>Healthcare Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getCustomElements <em>Custom Elements</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Lom#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom()
 * @model extendedMetaData="name='lom' kind='elementOnly'"
 * @generated
 */
public interface Lom extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>General</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.General}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>General</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_General()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='general' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<General> getGeneral();

	/**
	 * Returns the value of the '<em><b>Life Cycle</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LifeCycle}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Life Cycle</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_LifeCycle()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='lifeCycle' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LifeCycle> getLifeCycle();

	/**
	 * Returns the value of the '<em><b>Meta Metadata</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.MetaMetadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Metadata</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_MetaMetadata()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='metaMetadata' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<MetaMetadata> getMetaMetadata();

	/**
	 * Returns the value of the '<em><b>Technical</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Technical}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Technical</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Technical()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='technical' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Technical> getTechnical();

	/**
	 * Returns the value of the '<em><b>Educational</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Educational}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Educational</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Educational()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='educational' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Educational> getEducational();

	/**
	 * Returns the value of the '<em><b>Rights</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Rights}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rights</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Rights()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rights' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Rights> getRights();

	/**
	 * Returns the value of the '<em><b>Relation</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Relation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Relation()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='relation' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Relation> getRelation();

	/**
	 * Returns the value of the '<em><b>Annotation</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotation</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Annotation()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='annotation' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Annotation> getAnnotation();

	/**
	 * Returns the value of the '<em><b>Classification</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Classification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classification</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_Classification()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='classification' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Classification> getClassification();

	/**
	 * Returns the value of the '<em><b>Healthcare Metadata</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Healthcare Metadata</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_HealthcareMetadata()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='healthcareMetadata' namespace='http://ns.medbiq.org/lom/extend/v1/' group='#group:0'"
	 * @generated
	 */
	EList<HealthcareMetadataType> getHealthcareMetadata();

	/**
	 * Returns the value of the '<em><b>Custom Elements</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.healthcare.CustomElementsType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Elements</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_CustomElements()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='customElements' namespace='http://ns.medbiq.org/lom/extend/v1/' group='#group:0'"
	 * @generated
	 */
	EList<CustomElementsType> getCustomElements();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLom_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':12' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Lom
