/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Relation#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Relation#getKind <em>Kind</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Relation#getResource <em>Resource</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Relation#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Relation#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation()
 * @model extendedMetaData="name='relation' kind='elementOnly'"
 * @generated
 */
public interface Relation extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Kind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation_Kind()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='kind' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Kind> getKind();

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Resource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation_Resource()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='resource' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Resource> getResource();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':3' processing='lax' group='#group:0'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getRelation_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':4' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Relation
