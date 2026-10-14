/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Life Cycle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getVersion <em>Version</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getStatus <em>Status</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getContribute <em>Contribute</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle()
 * @model extendedMetaData="name='lifeCycle' kind='elementOnly'"
 * @generated
 */
public interface LifeCycle extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Version}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_Version()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='version' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Version> getVersion();

	/**
	 * Returns the value of the '<em><b>Status</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Status}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_Status()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='status' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Status> getStatus();

	/**
	 * Returns the value of the '<em><b>Contribute</b></em>' containment reference list.
	 * The list contents are of type {@link edu.louisiana.caai.lom.model.lom.Contribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contribute</em>' containment reference list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_Contribute()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='contribute' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Contribute> getContribute();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':4' processing='lax' group='#group:0'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"lifeCycle"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_UniqueElementName()
	 * @model default="lifeCycle" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	Object getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @generated
	 */
	void setUniqueElementName(Object value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @generated
	 */
	boolean isSetUniqueElementName();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLifeCycle_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':6' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // LifeCycle
