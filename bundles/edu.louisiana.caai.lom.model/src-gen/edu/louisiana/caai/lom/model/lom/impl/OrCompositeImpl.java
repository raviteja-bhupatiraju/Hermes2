/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MaximumVersion;
import edu.louisiana.caai.lom.model.lom.MinimumVersion;
import edu.louisiana.caai.lom.model.lom.Name;
import edu.louisiana.caai.lom.model.lom.OrComposite;
import edu.louisiana.caai.lom.model.lom.Type;

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
 * An implementation of the model object '<em><b>Or Composite</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getMinimumVersion <em>Minimum Version</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getMaximumVersion <em>Maximum Version</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OrCompositeImpl extends EObjectImpl implements OrComposite {
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
	protected OrCompositeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getOrComposite();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.OR_COMPOSITE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getType() {
		return getGroup().list(LomPackage.eINSTANCE.getOrComposite_Type());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Name> getName() {
		return getGroup().list(LomPackage.eINSTANCE.getOrComposite_Name());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MinimumVersion> getMinimumVersion() {
		return getGroup().list(LomPackage.eINSTANCE.getOrComposite_MinimumVersion());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MaximumVersion> getMaximumVersion() {
		return getGroup().list(LomPackage.eINSTANCE.getOrComposite_MaximumVersion());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getOrComposite_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE);
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
		case LomPackage.OR_COMPOSITE__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__TYPE:
			return ((InternalEList<?>) getType()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__NAME:
			return ((InternalEList<?>) getName()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__MINIMUM_VERSION:
			return ((InternalEList<?>) getMinimumVersion()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__MAXIMUM_VERSION:
			return ((InternalEList<?>) getMaximumVersion()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE:
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
		case LomPackage.OR_COMPOSITE__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.OR_COMPOSITE__TYPE:
			return getType();
		case LomPackage.OR_COMPOSITE__NAME:
			return getName();
		case LomPackage.OR_COMPOSITE__MINIMUM_VERSION:
			return getMinimumVersion();
		case LomPackage.OR_COMPOSITE__MAXIMUM_VERSION:
			return getMaximumVersion();
		case LomPackage.OR_COMPOSITE__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE:
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
		case LomPackage.OR_COMPOSITE__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.OR_COMPOSITE__TYPE:
			getType().clear();
			getType().addAll((Collection<? extends Type>) newValue);
			return;
		case LomPackage.OR_COMPOSITE__NAME:
			getName().clear();
			getName().addAll((Collection<? extends Name>) newValue);
			return;
		case LomPackage.OR_COMPOSITE__MINIMUM_VERSION:
			getMinimumVersion().clear();
			getMinimumVersion().addAll((Collection<? extends MinimumVersion>) newValue);
			return;
		case LomPackage.OR_COMPOSITE__MAXIMUM_VERSION:
			getMaximumVersion().clear();
			getMaximumVersion().addAll((Collection<? extends MaximumVersion>) newValue);
			return;
		case LomPackage.OR_COMPOSITE__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE:
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
		case LomPackage.OR_COMPOSITE__GROUP:
			getGroup().clear();
			return;
		case LomPackage.OR_COMPOSITE__TYPE:
			getType().clear();
			return;
		case LomPackage.OR_COMPOSITE__NAME:
			getName().clear();
			return;
		case LomPackage.OR_COMPOSITE__MINIMUM_VERSION:
			getMinimumVersion().clear();
			return;
		case LomPackage.OR_COMPOSITE__MAXIMUM_VERSION:
			getMaximumVersion().clear();
			return;
		case LomPackage.OR_COMPOSITE__ANY:
			getAny().clear();
			return;
		case LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE:
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
		case LomPackage.OR_COMPOSITE__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.OR_COMPOSITE__TYPE:
			return !getType().isEmpty();
		case LomPackage.OR_COMPOSITE__NAME:
			return !getName().isEmpty();
		case LomPackage.OR_COMPOSITE__MINIMUM_VERSION:
			return !getMinimumVersion().isEmpty();
		case LomPackage.OR_COMPOSITE__MAXIMUM_VERSION:
			return !getMaximumVersion().isEmpty();
		case LomPackage.OR_COMPOSITE__ANY:
			return !getAny().isEmpty();
		case LomPackage.OR_COMPOSITE__ANY_ATTRIBUTE:
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

} //OrCompositeImpl
