/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Status;
import edu.louisiana.caai.lom.model.lom.Version;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Life Cycle</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getContribute <em>Contribute</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LifeCycleImpl extends EObjectImpl implements LifeCycle {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final Object UNIQUE_ELEMENT_NAME_EDEFAULT = "lifeCycle";

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
	protected LifeCycleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getLifeCycle();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.LIFE_CYCLE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Version> getVersion() {
		return getGroup().list(LomPackage.eINSTANCE.getLifeCycle_Version());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Status> getStatus() {
		return getGroup().list(LomPackage.eINSTANCE.getLifeCycle_Status());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Contribute> getContribute() {
		return getGroup().list(LomPackage.eINSTANCE.getLifeCycle_Contribute());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getLifeCycle_Any());
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
			eNotify(new ENotificationImpl(this, Notification.SET, LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME,
					oldUniqueElementName, uniqueElementName, !oldUniqueElementNameESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME,
					oldUniqueElementName, UNIQUE_ELEMENT_NAME_EDEFAULT, oldUniqueElementNameESet));
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
			anyAttribute = new BasicFeatureMap(this, LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE);
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
		case LomPackage.LIFE_CYCLE__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.LIFE_CYCLE__VERSION:
			return ((InternalEList<?>) getVersion()).basicRemove(otherEnd, msgs);
		case LomPackage.LIFE_CYCLE__STATUS:
			return ((InternalEList<?>) getStatus()).basicRemove(otherEnd, msgs);
		case LomPackage.LIFE_CYCLE__CONTRIBUTE:
			return ((InternalEList<?>) getContribute()).basicRemove(otherEnd, msgs);
		case LomPackage.LIFE_CYCLE__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE:
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
		case LomPackage.LIFE_CYCLE__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.LIFE_CYCLE__VERSION:
			return getVersion();
		case LomPackage.LIFE_CYCLE__STATUS:
			return getStatus();
		case LomPackage.LIFE_CYCLE__CONTRIBUTE:
			return getContribute();
		case LomPackage.LIFE_CYCLE__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		case LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE:
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LomPackage.LIFE_CYCLE__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.LIFE_CYCLE__VERSION:
			getVersion().clear();
			getVersion().addAll((Collection<? extends Version>) newValue);
			return;
		case LomPackage.LIFE_CYCLE__STATUS:
			getStatus().clear();
			getStatus().addAll((Collection<? extends Status>) newValue);
			return;
		case LomPackage.LIFE_CYCLE__CONTRIBUTE:
			getContribute().clear();
			getContribute().addAll((Collection<? extends Contribute>) newValue);
			return;
		case LomPackage.LIFE_CYCLE__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME:
			setUniqueElementName(newValue);
			return;
		case LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE:
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
		case LomPackage.LIFE_CYCLE__GROUP:
			getGroup().clear();
			return;
		case LomPackage.LIFE_CYCLE__VERSION:
			getVersion().clear();
			return;
		case LomPackage.LIFE_CYCLE__STATUS:
			getStatus().clear();
			return;
		case LomPackage.LIFE_CYCLE__CONTRIBUTE:
			getContribute().clear();
			return;
		case LomPackage.LIFE_CYCLE__ANY:
			getAny().clear();
			return;
		case LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		case LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE:
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
		case LomPackage.LIFE_CYCLE__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.LIFE_CYCLE__VERSION:
			return !getVersion().isEmpty();
		case LomPackage.LIFE_CYCLE__STATUS:
			return !getStatus().isEmpty();
		case LomPackage.LIFE_CYCLE__CONTRIBUTE:
			return !getContribute().isEmpty();
		case LomPackage.LIFE_CYCLE__ANY:
			return !getAny().isEmpty();
		case LomPackage.LIFE_CYCLE__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		case LomPackage.LIFE_CYCLE__ANY_ATTRIBUTE:
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
		result.append(" (group: ");
		result.append(group);
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

} //LifeCycleImpl
