/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.address.AddressType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Location Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getRoom <em>Room</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getBuilding <em>Building</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getActivityLocationType()
 * @model extendedMetaData="name='activityLocationType' kind='elementOnly'"
 * @generated
 */
public interface ActivityLocationType extends AddressType {
	/**
	 * Returns the value of the '<em><b>Room</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Room</em>' attribute.
	 * @see #setRoom(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getActivityLocationType_Room()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='room' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRoom();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getRoom <em>Room</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Room</em>' attribute.
	 * @see #getRoom()
	 * @generated
	 */
	void setRoom(String value);

	/**
	 * Returns the value of the '<em><b>Building</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Building</em>' attribute.
	 * @see #setBuilding(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getActivityLocationType_Building()
	 * @model dataType="edu.louisiana.caai.lom.model.lom.CharacterString"
	 *        extendedMetaData="kind='element' name='building' namespace='##targetNamespace'"
	 * @generated
	 */
	String getBuilding();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getBuilding <em>Building</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Building</em>' attribute.
	 * @see #getBuilding()
	 * @generated
	 */
	void setBuilding(String value);

	/**
	 * Returns the value of the '<em><b>Unique Element Name</b></em>' attribute.
	 * The default value is <code>"activityLocation"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getActivityLocationType_UniqueElementName()
	 * @model default="activityLocation" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueElementName'"
	 * @generated
	 */
	String getUniqueElementName();

	/**
	 * Sets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Element Name</em>' attribute.
	 * @see #isSetUniqueElementName()
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @generated
	 */
	void setUniqueElementName(String value);

	/**
	 * Unsets the value of the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	void unsetUniqueElementName();

	/**
	 * Returns whether the value of the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName <em>Unique Element Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unique Element Name</em>' attribute is set.
	 * @see #unsetUniqueElementName()
	 * @see #getUniqueElementName()
	 * @see #setUniqueElementName(String)
	 * @generated
	 */
	boolean isSetUniqueElementName();

} // ActivityLocationType
