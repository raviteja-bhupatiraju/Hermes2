/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.healthcare.CustomElementsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;

import edu.louisiana.caai.lom.model.lom.Annotation;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LifeCycle;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.lom.Relation;
import edu.louisiana.caai.lom.model.lom.Rights;
import edu.louisiana.caai.lom.model.lom.Technical;

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
 * An implementation of the model object '<em><b>Lom</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getGeneral <em>General</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getLifeCycle <em>Life Cycle</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getMetaMetadata <em>Meta Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getTechnical <em>Technical</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getEducational <em>Educational</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getRights <em>Rights</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getRelation <em>Relation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getAnnotation <em>Annotation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getClassification <em>Classification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getHealthcareMetadata <em>Healthcare Metadata</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getCustomElements <em>Custom Elements</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LomImpl extends EObjectImpl implements Lom {
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
	protected LomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getLom();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.LOM__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<General> getGeneral() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_General());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LifeCycle> getLifeCycle() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_LifeCycle());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MetaMetadata> getMetaMetadata() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_MetaMetadata());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Technical> getTechnical() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Technical());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Educational> getEducational() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Educational());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Rights> getRights() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Rights());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Relation> getRelation() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Relation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotation() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Annotation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Classification> getClassification() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_Classification());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HealthcareMetadataType> getHealthcareMetadata() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_HealthcareMetadata());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CustomElementsType> getCustomElements() {
		return getGroup().list(LomPackage.eINSTANCE.getLom_CustomElements());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.LOM__ANY_ATTRIBUTE);
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
		case LomPackage.LOM__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__GENERAL:
			return ((InternalEList<?>) getGeneral()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__LIFE_CYCLE:
			return ((InternalEList<?>) getLifeCycle()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__META_METADATA:
			return ((InternalEList<?>) getMetaMetadata()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__TECHNICAL:
			return ((InternalEList<?>) getTechnical()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__EDUCATIONAL:
			return ((InternalEList<?>) getEducational()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__RIGHTS:
			return ((InternalEList<?>) getRights()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__RELATION:
			return ((InternalEList<?>) getRelation()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__ANNOTATION:
			return ((InternalEList<?>) getAnnotation()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__CLASSIFICATION:
			return ((InternalEList<?>) getClassification()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__HEALTHCARE_METADATA:
			return ((InternalEList<?>) getHealthcareMetadata()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__CUSTOM_ELEMENTS:
			return ((InternalEList<?>) getCustomElements()).basicRemove(otherEnd, msgs);
		case LomPackage.LOM__ANY_ATTRIBUTE:
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
		case LomPackage.LOM__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.LOM__GENERAL:
			return getGeneral();
		case LomPackage.LOM__LIFE_CYCLE:
			return getLifeCycle();
		case LomPackage.LOM__META_METADATA:
			return getMetaMetadata();
		case LomPackage.LOM__TECHNICAL:
			return getTechnical();
		case LomPackage.LOM__EDUCATIONAL:
			return getEducational();
		case LomPackage.LOM__RIGHTS:
			return getRights();
		case LomPackage.LOM__RELATION:
			return getRelation();
		case LomPackage.LOM__ANNOTATION:
			return getAnnotation();
		case LomPackage.LOM__CLASSIFICATION:
			return getClassification();
		case LomPackage.LOM__HEALTHCARE_METADATA:
			return getHealthcareMetadata();
		case LomPackage.LOM__CUSTOM_ELEMENTS:
			return getCustomElements();
		case LomPackage.LOM__ANY_ATTRIBUTE:
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
		case LomPackage.LOM__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.LOM__GENERAL:
			getGeneral().clear();
			getGeneral().addAll((Collection<? extends General>) newValue);
			return;
		case LomPackage.LOM__LIFE_CYCLE:
			getLifeCycle().clear();
			getLifeCycle().addAll((Collection<? extends LifeCycle>) newValue);
			return;
		case LomPackage.LOM__META_METADATA:
			getMetaMetadata().clear();
			getMetaMetadata().addAll((Collection<? extends MetaMetadata>) newValue);
			return;
		case LomPackage.LOM__TECHNICAL:
			getTechnical().clear();
			getTechnical().addAll((Collection<? extends Technical>) newValue);
			return;
		case LomPackage.LOM__EDUCATIONAL:
			getEducational().clear();
			getEducational().addAll((Collection<? extends Educational>) newValue);
			return;
		case LomPackage.LOM__RIGHTS:
			getRights().clear();
			getRights().addAll((Collection<? extends Rights>) newValue);
			return;
		case LomPackage.LOM__RELATION:
			getRelation().clear();
			getRelation().addAll((Collection<? extends Relation>) newValue);
			return;
		case LomPackage.LOM__ANNOTATION:
			getAnnotation().clear();
			getAnnotation().addAll((Collection<? extends Annotation>) newValue);
			return;
		case LomPackage.LOM__CLASSIFICATION:
			getClassification().clear();
			getClassification().addAll((Collection<? extends Classification>) newValue);
			return;
		case LomPackage.LOM__HEALTHCARE_METADATA:
			getHealthcareMetadata().clear();
			getHealthcareMetadata().addAll((Collection<? extends HealthcareMetadataType>) newValue);
			return;
		case LomPackage.LOM__CUSTOM_ELEMENTS:
			getCustomElements().clear();
			getCustomElements().addAll((Collection<? extends CustomElementsType>) newValue);
			return;
		case LomPackage.LOM__ANY_ATTRIBUTE:
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
		case LomPackage.LOM__GROUP:
			getGroup().clear();
			return;
		case LomPackage.LOM__GENERAL:
			getGeneral().clear();
			return;
		case LomPackage.LOM__LIFE_CYCLE:
			getLifeCycle().clear();
			return;
		case LomPackage.LOM__META_METADATA:
			getMetaMetadata().clear();
			return;
		case LomPackage.LOM__TECHNICAL:
			getTechnical().clear();
			return;
		case LomPackage.LOM__EDUCATIONAL:
			getEducational().clear();
			return;
		case LomPackage.LOM__RIGHTS:
			getRights().clear();
			return;
		case LomPackage.LOM__RELATION:
			getRelation().clear();
			return;
		case LomPackage.LOM__ANNOTATION:
			getAnnotation().clear();
			return;
		case LomPackage.LOM__CLASSIFICATION:
			getClassification().clear();
			return;
		case LomPackage.LOM__HEALTHCARE_METADATA:
			getHealthcareMetadata().clear();
			return;
		case LomPackage.LOM__CUSTOM_ELEMENTS:
			getCustomElements().clear();
			return;
		case LomPackage.LOM__ANY_ATTRIBUTE:
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
		case LomPackage.LOM__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.LOM__GENERAL:
			return !getGeneral().isEmpty();
		case LomPackage.LOM__LIFE_CYCLE:
			return !getLifeCycle().isEmpty();
		case LomPackage.LOM__META_METADATA:
			return !getMetaMetadata().isEmpty();
		case LomPackage.LOM__TECHNICAL:
			return !getTechnical().isEmpty();
		case LomPackage.LOM__EDUCATIONAL:
			return !getEducational().isEmpty();
		case LomPackage.LOM__RIGHTS:
			return !getRights().isEmpty();
		case LomPackage.LOM__RELATION:
			return !getRelation().isEmpty();
		case LomPackage.LOM__ANNOTATION:
			return !getAnnotation().isEmpty();
		case LomPackage.LOM__CLASSIFICATION:
			return !getClassification().isEmpty();
		case LomPackage.LOM__HEALTHCARE_METADATA:
			return !getHealthcareMetadata().isEmpty();
		case LomPackage.LOM__CUSTOM_ELEMENTS:
			return !getCustomElements().isEmpty();
		case LomPackage.LOM__ANY_ATTRIBUTE:
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

} //LomImpl
