/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.ContributeMeta;
import edu.louisiana.caai.lom.model.lom.Date;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.RoleMeta;
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
 * An implementation of the model object '<em><b>Contribute Meta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getRole <em>Role</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getDate <em>Date</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContributeMetaImpl extends EObjectImpl implements ContributeMeta {
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
	protected ContributeMetaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getContributeMeta();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.CONTRIBUTE_META__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RoleMeta> getRole() {
		return getGroup().list(LomPackage.eINSTANCE.getContributeMeta_Role());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VCard> getEntity() {
		return getGroup().list(LomPackage.eINSTANCE.getContributeMeta_Entity());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Date> getDate() {
		return getGroup().list(LomPackage.eINSTANCE.getContributeMeta_Date());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getContributeMeta_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE);
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
		case LomPackage.CONTRIBUTE_META__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE_META__ROLE:
			return ((InternalEList<?>) getRole()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE_META__ENTITY:
			return ((InternalEList<?>) getEntity()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE_META__DATE:
			return ((InternalEList<?>) getDate()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE_META__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE_META__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.CONTRIBUTE_META__ROLE:
			return getRole();
		case LomPackage.CONTRIBUTE_META__ENTITY:
			return getEntity();
		case LomPackage.CONTRIBUTE_META__DATE:
			return getDate();
		case LomPackage.CONTRIBUTE_META__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE_META__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.CONTRIBUTE_META__ROLE:
			getRole().clear();
			getRole().addAll((Collection<? extends RoleMeta>) newValue);
			return;
		case LomPackage.CONTRIBUTE_META__ENTITY:
			getEntity().clear();
			getEntity().addAll((Collection<? extends VCard>) newValue);
			return;
		case LomPackage.CONTRIBUTE_META__DATE:
			getDate().clear();
			getDate().addAll((Collection<? extends Date>) newValue);
			return;
		case LomPackage.CONTRIBUTE_META__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE_META__GROUP:
			getGroup().clear();
			return;
		case LomPackage.CONTRIBUTE_META__ROLE:
			getRole().clear();
			return;
		case LomPackage.CONTRIBUTE_META__ENTITY:
			getEntity().clear();
			return;
		case LomPackage.CONTRIBUTE_META__DATE:
			getDate().clear();
			return;
		case LomPackage.CONTRIBUTE_META__ANY:
			getAny().clear();
			return;
		case LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE:
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
		case LomPackage.CONTRIBUTE_META__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.CONTRIBUTE_META__ROLE:
			return !getRole().isEmpty();
		case LomPackage.CONTRIBUTE_META__ENTITY:
			return !getEntity().isEmpty();
		case LomPackage.CONTRIBUTE_META__DATE:
			return !getDate().isEmpty();
		case LomPackage.CONTRIBUTE_META__ANY:
			return !getAny().isEmpty();
		case LomPackage.CONTRIBUTE_META__ANY_ATTRIBUTE:
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

} //ContributeMetaImpl
