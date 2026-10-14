/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.address.CountryType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl#getCountryName <em>Country Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.CountryTypeImpl#getCountryCode <em>Country Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CountryTypeImpl extends EObjectImpl implements CountryType {
	/**
	 * The default value of the '{@link #getCountryName() <em>Country Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountryName()
	 * @generated
	 * @ordered
	 */
	protected static final String COUNTRY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCountryName() <em>Country Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountryName()
	 * @generated
	 * @ordered
	 */
	protected String countryName = COUNTRY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCountryCode() <em>Country Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountryCode()
	 * @generated
	 * @ordered
	 */
	protected static final String COUNTRY_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCountryCode() <em>Country Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountryCode()
	 * @generated
	 * @ordered
	 */
	protected String countryCode = COUNTRY_CODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AddressPackage.Literals.COUNTRY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCountryName() {
		return countryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCountryName(String newCountryName) {
		String oldCountryName = countryName;
		countryName = newCountryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.COUNTRY_TYPE__COUNTRY_NAME,
					oldCountryName, countryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCountryCode(String newCountryCode) {
		String oldCountryCode = countryCode;
		countryCode = newCountryCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.COUNTRY_TYPE__COUNTRY_CODE,
					oldCountryCode, countryCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AddressPackage.COUNTRY_TYPE__COUNTRY_NAME:
			return getCountryName();
		case AddressPackage.COUNTRY_TYPE__COUNTRY_CODE:
			return getCountryCode();
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
		case AddressPackage.COUNTRY_TYPE__COUNTRY_NAME:
			setCountryName((String) newValue);
			return;
		case AddressPackage.COUNTRY_TYPE__COUNTRY_CODE:
			setCountryCode((String) newValue);
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
		case AddressPackage.COUNTRY_TYPE__COUNTRY_NAME:
			setCountryName(COUNTRY_NAME_EDEFAULT);
			return;
		case AddressPackage.COUNTRY_TYPE__COUNTRY_CODE:
			setCountryCode(COUNTRY_CODE_EDEFAULT);
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
		case AddressPackage.COUNTRY_TYPE__COUNTRY_NAME:
			return COUNTRY_NAME_EDEFAULT == null ? countryName != null : !COUNTRY_NAME_EDEFAULT.equals(countryName);
		case AddressPackage.COUNTRY_TYPE__COUNTRY_CODE:
			return COUNTRY_CODE_EDEFAULT == null ? countryCode != null : !COUNTRY_CODE_EDEFAULT.equals(countryCode);
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
		result.append(" (countryName: ");
		result.append(countryName);
		result.append(", countryCode: ");
		result.append(countryCode);
		result.append(')');
		return result.toString();
	}

} //CountryTypeImpl
