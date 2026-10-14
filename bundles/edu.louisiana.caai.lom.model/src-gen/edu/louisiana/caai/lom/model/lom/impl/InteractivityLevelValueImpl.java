/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.InteractivityLevelValue;
import edu.louisiana.caai.lom.model.lom.LomPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.Enumerator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interactivity Level Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelValueImpl#getValue <em>Value</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelValueImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelValueImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InteractivityLevelValueImpl extends EObjectImpl implements InteractivityLevelValue {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Enumerator VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Enumerator value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final Object UNIQUE_ELEMENT_NAME_EDEFAULT = "value";

	/**
	 * The cached value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected Object uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;

	/**
	 * This is true if the Unique Element Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean uniqueElementNameESet;

	/**
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InteractivityLevelValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getInteractivityLevelValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Enumerator getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(Enumerator newValue) {
		Enumerator oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LomPackage.INTERACTIVITY_LEVEL_VALUE__VALUE, oldValue,
					value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getUniqueElementName() {
		return uniqueElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUniqueElementName(Object newUniqueElementName) {
		Object oldUniqueElementName = uniqueElementName;
		uniqueElementName = newUniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME, oldUniqueElementName, uniqueElementName,
					!oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUniqueElementName() {
		Object oldUniqueElementName = uniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;
		uniqueElementNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
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
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE:
			return ((InternalEList<?>) getAnyAttribute()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__VALUE:
			return getValue();
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE:
			if (coreType)
				return getAnyAttribute();
			return ((FeatureMap.Internal) getAnyAttribute()).getWrapper();
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
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__VALUE:
			setValue((Enumerator) newValue);
			return;
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME:
			setUniqueElementName(newValue);
			return;
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE:
			((FeatureMap.Internal) getAnyAttribute()).set(newValue);
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
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE:
			getAnyAttribute().clear();
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
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		case LomPackage.INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE:
			return anyAttribute != null && !anyAttribute.isEmpty();
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
		result.append(", uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //InteractivityLevelValueImpl
