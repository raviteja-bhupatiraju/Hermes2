/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Language String</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LanguageString#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LanguageString#getString <em>String</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LanguageString#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LanguageString#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLanguageString()
 * @model extendedMetaData="name='LanguageString' kind='elementOnly'"
 * @generated
 */
public interface LanguageString extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLanguageString_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>String</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.LangString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLanguageString_String()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='string' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LangString> getString();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLanguageString_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':2' processing='lax' group='#group:0'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLanguageString_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':3' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // LanguageString
