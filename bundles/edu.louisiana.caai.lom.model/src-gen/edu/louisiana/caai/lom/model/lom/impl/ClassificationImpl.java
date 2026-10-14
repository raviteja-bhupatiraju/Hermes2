/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.Description;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Purpose;
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
 * An implementation of the model object '<em><b>Classification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getTaxonPath <em>Taxon Path</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassificationImpl extends EObjectImpl implements Classification {
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
	protected ClassificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getClassification();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.CLASSIFICATION__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Purpose> getPurpose() {
		return getGroup().list(LomPackage.eINSTANCE.getClassification_Purpose());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaxonPath> getTaxonPath() {
		return getGroup().list(LomPackage.eINSTANCE.getClassification_TaxonPath());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Description> getDescription() {
		return getGroup().list(LomPackage.eINSTANCE.getClassification_Description());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Keyword> getKeyword() {
		return getGroup().list(LomPackage.eINSTANCE.getClassification_Keyword());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getClassification_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.CLASSIFICATION__ANY_ATTRIBUTE);
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
		case LomPackage.CLASSIFICATION__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__PURPOSE:
			return ((InternalEList<?>) getPurpose()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__TAXON_PATH:
			return ((InternalEList<?>) getTaxonPath()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__DESCRIPTION:
			return ((InternalEList<?>) getDescription()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__KEYWORD:
			return ((InternalEList<?>) getKeyword()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.CLASSIFICATION__ANY_ATTRIBUTE:
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
		case LomPackage.CLASSIFICATION__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.CLASSIFICATION__PURPOSE:
			return getPurpose();
		case LomPackage.CLASSIFICATION__TAXON_PATH:
			return getTaxonPath();
		case LomPackage.CLASSIFICATION__DESCRIPTION:
			return getDescription();
		case LomPackage.CLASSIFICATION__KEYWORD:
			return getKeyword();
		case LomPackage.CLASSIFICATION__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.CLASSIFICATION__ANY_ATTRIBUTE:
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
		case LomPackage.CLASSIFICATION__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.CLASSIFICATION__PURPOSE:
			getPurpose().clear();
			getPurpose().addAll((Collection<? extends Purpose>) newValue);
			return;
		case LomPackage.CLASSIFICATION__TAXON_PATH:
			getTaxonPath().clear();
			getTaxonPath().addAll((Collection<? extends TaxonPath>) newValue);
			return;
		case LomPackage.CLASSIFICATION__DESCRIPTION:
			getDescription().clear();
			getDescription().addAll((Collection<? extends Description>) newValue);
			return;
		case LomPackage.CLASSIFICATION__KEYWORD:
			getKeyword().clear();
			getKeyword().addAll((Collection<? extends Keyword>) newValue);
			return;
		case LomPackage.CLASSIFICATION__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.CLASSIFICATION__ANY_ATTRIBUTE:
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
		case LomPackage.CLASSIFICATION__GROUP:
			getGroup().clear();
			return;
		case LomPackage.CLASSIFICATION__PURPOSE:
			getPurpose().clear();
			return;
		case LomPackage.CLASSIFICATION__TAXON_PATH:
			getTaxonPath().clear();
			return;
		case LomPackage.CLASSIFICATION__DESCRIPTION:
			getDescription().clear();
			return;
		case LomPackage.CLASSIFICATION__KEYWORD:
			getKeyword().clear();
			return;
		case LomPackage.CLASSIFICATION__ANY:
			getAny().clear();
			return;
		case LomPackage.CLASSIFICATION__ANY_ATTRIBUTE:
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
		case LomPackage.CLASSIFICATION__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.CLASSIFICATION__PURPOSE:
			return !getPurpose().isEmpty();
		case LomPackage.CLASSIFICATION__TAXON_PATH:
			return !getTaxonPath().isEmpty();
		case LomPackage.CLASSIFICATION__DESCRIPTION:
			return !getDescription().isEmpty();
		case LomPackage.CLASSIFICATION__KEYWORD:
			return !getKeyword().isEmpty();
		case LomPackage.CLASSIFICATION__ANY:
			return !getAny().isEmpty();
		case LomPackage.CLASSIFICATION__ANY_ATTRIBUTE:
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

} //ClassificationImpl
