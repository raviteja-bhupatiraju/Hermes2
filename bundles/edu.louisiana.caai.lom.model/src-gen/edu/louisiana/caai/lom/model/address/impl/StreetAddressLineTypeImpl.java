/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.address.RestrictionsType;
import edu.louisiana.caai.lom.model.address.StreetAddressLineType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Street Address Line Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.StreetAddressLineTypeImpl#getRestrictions <em>Restrictions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreetAddressLineTypeImpl extends EObjectImpl implements StreetAddressLineType {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRestrictions() <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestrictions()
	 * @generated
	 * @ordered
	 */
	protected static final RestrictionsType RESTRICTIONS_EDEFAULT = RestrictionsType.UNRESTRICTED;

	/**
	 * The cached value of the '{@link #getRestrictions() <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestrictions()
	 * @generated
	 * @ordered
	 */
	protected RestrictionsType restrictions = RESTRICTIONS_EDEFAULT;

	/**
	 * This is true if the Restrictions attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean restrictionsESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreetAddressLineTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AddressPackage.Literals.STREET_ADDRESS_LINE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.STREET_ADDRESS_LINE_TYPE__VALUE,
					oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RestrictionsType getRestrictions() {
		return restrictions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRestrictions(RestrictionsType newRestrictions) {
		RestrictionsType oldRestrictions = restrictions;
		restrictions = newRestrictions == null ? RESTRICTIONS_EDEFAULT : newRestrictions;
		boolean oldRestrictionsESet = restrictionsESet;
		restrictionsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS,
					oldRestrictions, restrictions, !oldRestrictionsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetRestrictions() {
		RestrictionsType oldRestrictions = restrictions;
		boolean oldRestrictionsESet = restrictionsESet;
		restrictions = RESTRICTIONS_EDEFAULT;
		restrictionsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS, oldRestrictions, RESTRICTIONS_EDEFAULT,
					oldRestrictionsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetRestrictions() {
		return restrictionsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__VALUE:
			return getValue();
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS:
			return getRestrictions();
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
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__VALUE:
			setValue((String) newValue);
			return;
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS:
			setRestrictions((RestrictionsType) newValue);
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
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS:
			unsetRestrictions();
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
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		case AddressPackage.STREET_ADDRESS_LINE_TYPE__RESTRICTIONS:
			return isSetRestrictions();
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
		result.append(" (value: ");
		result.append(value);
		result.append(", restrictions: ");
		if (restrictionsESet)
			result.append(restrictions);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //StreetAddressLineTypeImpl
