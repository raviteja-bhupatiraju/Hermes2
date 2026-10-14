/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Installation Remarks</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getInstallationRemarks()
 * @model extendedMetaData="name='installationRemarks' kind='elementOnly'"
 * @generated
 */
public interface InstallationRemarks extends LanguageString {
	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"installationRemarks"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getInstallationRemarks_UniqueElementName()
	 * @model default="installationRemarks" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	Object getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName <em>Unique Element Name</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(Object)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // InstallationRemarks
