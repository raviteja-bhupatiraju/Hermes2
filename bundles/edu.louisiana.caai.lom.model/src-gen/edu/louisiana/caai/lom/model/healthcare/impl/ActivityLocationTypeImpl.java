/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl;

import edu.louisiana.caai.lom.model.healthcare.ActivityLocationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity Location Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl#getRoom <em>Room</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl#getBuilding <em>Building</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActivityLocationTypeImpl extends AddressTypeImpl implements ActivityLocationType {
	/**
	 * The default value of the '{@link #getRoom() <em>Room</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoom()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRoom() <em>Room</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoom()
	 * @generated
	 * @ordered
	 */
	protected String room = ROOM_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuilding() <em>Building</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuilding()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuilding() <em>Building</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuilding()
	 * @generated
	 * @ordered
	 */
	protected String building = BUILDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_ELEMENT_NAME_EDEFAULT = "activityLocation";

	/**
	 * The cached value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected String uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;

	/**
	 * This is true if the Unique Element Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean uniqueElementNameESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActivityLocationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.ACTIVITY_LOCATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRoom() {
		return room;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRoom(String newRoom) {
		String oldRoom = room;
		room = newRoom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.ACTIVITY_LOCATION_TYPE__ROOM,
					oldRoom, room));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBuilding() {
		return building;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuilding(String newBuilding) {
		String oldBuilding = building;
		building = newBuilding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.ACTIVITY_LOCATION_TYPE__BUILDING,
					oldBuilding, building));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUniqueElementName() {
		return uniqueElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUniqueElementName(String newUniqueElementName) {
		String oldUniqueElementName = uniqueElementName;
		uniqueElementName = newUniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					uniqueElementName, !oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUniqueElementName() {
		String oldUniqueElementName = uniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;
		uniqueElementNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					UNIQUE_ELEMENT_NAME_EDEFAULT, oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUniqueElementName() {
		return uniqueElementNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__ROOM:
			return getRoom();
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__BUILDING:
			return getBuilding();
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__ROOM:
			setRoom((String) newValue);
			return;
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__BUILDING:
			setBuilding((String) newValue);
			return;
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME:
			setUniqueElementName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__ROOM:
			setRoom(ROOM_EDEFAULT);
			return;
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__BUILDING:
			setBuilding(BUILDING_EDEFAULT);
			return;
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__ROOM:
			return ROOM_EDEFAULT == null ? room != null : !ROOM_EDEFAULT.equals(room);
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__BUILDING:
			return BUILDING_EDEFAULT == null ? building != null : !BUILDING_EDEFAULT.equals(building);
		case HealthcarePackage.ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (room: ");
		result.append(room);
		result.append(", building: ");
		result.append(building);
		result.append(", uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ActivityLocationTypeImpl
