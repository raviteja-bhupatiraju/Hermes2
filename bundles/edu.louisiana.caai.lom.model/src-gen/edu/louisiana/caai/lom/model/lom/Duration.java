/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.Duration#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getDuration()
 * @model extendedMetaData="name='duration' kind='elementOnly'"
 * @generated
 */
public interface Duration extends DurationBase {
	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"duration"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getDuration_UniqueElementName()
	 * @model default="duration" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	Object getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName <em>Unique Element Name</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
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
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getDuration_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='!##http://ltsc.ieee.org/xsd/LOM/extend' name=':5' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Duration
