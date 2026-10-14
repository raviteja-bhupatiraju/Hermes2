/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.Contribute;
import edu.louisiana.caai.lom.model.lom.Date;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Role;
import edu.louisiana.caai.lom.model.lom.VCard;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getRole <em>Role</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getDate <em>Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContributeImpl extends EObjectImpl implements Contribute {
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
	protected ContributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getContribute();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.CONTRIBUTE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Role> getRole() {
		return getGroup().list(LomPackage.eINSTANCE.getContribute_Role());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VCard> getEntity() {
		return getGroup().list(LomPackage.eINSTANCE.getContribute_Entity());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Date> getDate() {
		return getGroup().list(LomPackage.eINSTANCE.getContribute_Date());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getContribute_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.CONTRIBUTE__ANY_ATTRIBUTE);
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
		case LomPackage.CONTRIBUTE__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE__ROLE:
			return ((InternalEList<?>) getRole()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE__ENTITY:
			return ((InternalEList<?>) getEntity()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE__DATE:
			return ((InternalEList<?>) getDate()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.CONTRIBUTE__ROLE:
			return getRole();
		case LomPackage.CONTRIBUTE__ENTITY:
			return getEntity();
		case LomPackage.CONTRIBUTE__DATE:
			return getDate();
		case LomPackage.CONTRIBUTE__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.CONTRIBUTE__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.CONTRIBUTE__ROLE:
			getRole().clear();
			getRole().addAll((Collection<? extends Role>) newValue);
			return;
		case LomPackage.CONTRIBUTE__ENTITY:
			getEntity().clear();
			getEntity().addAll((Collection<? extends VCard>) newValue);
			return;
		case LomPackage.CONTRIBUTE__DATE:
			getDate().clear();
			getDate().addAll((Collection<? extends Date>) newValue);
			return;
		case LomPackage.CONTRIBUTE__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.CONTRIBUTE__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE__GROUP:
			getGroup().clear();
			return;
		case LomPackage.CONTRIBUTE__ROLE:
			getRole().clear();
			return;
		case LomPackage.CONTRIBUTE__ENTITY:
			getEntity().clear();
			return;
		case LomPackage.CONTRIBUTE__DATE:
			getDate().clear();
			return;
		case LomPackage.CONTRIBUTE__ANY:
			getAny().clear();
			return;
		case LomPackage.CONTRIBUTE__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.CONTRIBUTE__ROLE:
			return !getRole().isEmpty();
		case LomPackage.CONTRIBUTE__ENTITY:
			return !getEntity().isEmpty();
		case LomPackage.CONTRIBUTE__DATE:
			return !getDate().isEmpty();
		case LomPackage.CONTRIBUTE__ANY:
			return !getAny().isEmpty();
		case LomPackage.CONTRIBUTE__ANY_ATTRIBUTE:
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
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //ContributeImpl
