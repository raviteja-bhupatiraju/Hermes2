/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.EntryTaxon;
import edu.louisiana.caai.lom.model.lom.Id;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Taxon;

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
 * An implementation of the model object '<em><b>Taxon</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl#getId <em>Id</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaxonImpl extends EObjectImpl implements Taxon {
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
	protected TaxonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getTaxon();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.TAXON__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Id> getId() {
		return getGroup().list(LomPackage.eINSTANCE.getTaxon_Id());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EntryTaxon> getEntry() {
		return getGroup().list(LomPackage.eINSTANCE.getTaxon_Entry());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getTaxon_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.TAXON__ANY_ATTRIBUTE);
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
		case LomPackage.TAXON__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON__ID:
			return ((InternalEList<?>) getId()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON__ENTRY:
			return ((InternalEList<?>) getEntry()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.TAXON__ID:
			return getId();
		case LomPackage.TAXON__ENTRY:
			return getEntry();
		case LomPackage.TAXON__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.TAXON__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.TAXON__ID:
			getId().clear();
			getId().addAll((Collection<? extends Id>) newValue);
			return;
		case LomPackage.TAXON__ENTRY:
			getEntry().clear();
			getEntry().addAll((Collection<? extends EntryTaxon>) newValue);
			return;
		case LomPackage.TAXON__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.TAXON__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON__GROUP:
			getGroup().clear();
			return;
		case LomPackage.TAXON__ID:
			getId().clear();
			return;
		case LomPackage.TAXON__ENTRY:
			getEntry().clear();
			return;
		case LomPackage.TAXON__ANY:
			getAny().clear();
			return;
		case LomPackage.TAXON__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.TAXON__ID:
			return !getId().isEmpty();
		case LomPackage.TAXON__ENTRY:
			return !getEntry().isEmpty();
		case LomPackage.TAXON__ANY:
			return !getAny().isEmpty();
		case LomPackage.TAXON__ANY_ATTRIBUTE:
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

} //TaxonImpl
