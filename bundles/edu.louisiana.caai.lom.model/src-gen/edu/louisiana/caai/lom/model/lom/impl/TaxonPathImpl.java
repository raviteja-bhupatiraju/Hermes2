/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Source;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;

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
 * An implementation of the model object '<em><b>Taxon Path</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl#getSource <em>Source</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl#getTaxon <em>Taxon</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaxonPathImpl extends EObjectImpl implements TaxonPath {
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
	protected TaxonPathImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getTaxonPath();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.TAXON_PATH__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Source> getSource() {
		return getGroup().list(LomPackage.eINSTANCE.getTaxonPath_Source());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Taxon> getTaxon() {
		return getGroup().list(LomPackage.eINSTANCE.getTaxonPath_Taxon());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getTaxonPath_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.TAXON_PATH__ANY_ATTRIBUTE);
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
		case LomPackage.TAXON_PATH__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON_PATH__SOURCE:
			return ((InternalEList<?>) getSource()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON_PATH__TAXON:
			return ((InternalEList<?>) getTaxon()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON_PATH__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.TAXON_PATH__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON_PATH__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.TAXON_PATH__SOURCE:
			return getSource();
		case LomPackage.TAXON_PATH__TAXON:
			return getTaxon();
		case LomPackage.TAXON_PATH__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.TAXON_PATH__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON_PATH__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.TAXON_PATH__SOURCE:
			getSource().clear();
			getSource().addAll((Collection<? extends Source>) newValue);
			return;
		case LomPackage.TAXON_PATH__TAXON:
			getTaxon().clear();
			getTaxon().addAll((Collection<? extends Taxon>) newValue);
			return;
		case LomPackage.TAXON_PATH__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.TAXON_PATH__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON_PATH__GROUP:
			getGroup().clear();
			return;
		case LomPackage.TAXON_PATH__SOURCE:
			getSource().clear();
			return;
		case LomPackage.TAXON_PATH__TAXON:
			getTaxon().clear();
			return;
		case LomPackage.TAXON_PATH__ANY:
			getAny().clear();
			return;
		case LomPackage.TAXON_PATH__ANY_ATTRIBUTE:
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
		case LomPackage.TAXON_PATH__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.TAXON_PATH__SOURCE:
			return !getSource().isEmpty();
		case LomPackage.TAXON_PATH__TAXON:
			return !getTaxon().isEmpty();
		case LomPackage.TAXON_PATH__ANY:
			return !getAny().isEmpty();
		case LomPackage.TAXON_PATH__ANY_ATTRIBUTE:
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

} //TaxonPathImpl
