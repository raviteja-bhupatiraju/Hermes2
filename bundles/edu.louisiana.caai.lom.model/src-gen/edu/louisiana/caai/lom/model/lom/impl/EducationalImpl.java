/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.Context;
import edu.louisiana.caai.lom.model.lom.Difficulty;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.lom.IntendedEndUserRole;
import edu.louisiana.caai.lom.model.lom.InteractivityLevel;
import edu.louisiana.caai.lom.model.lom.InteractivityType;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LearningResourceType;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.SemanticDensity;
import edu.louisiana.caai.lom.model.lom.TypicalAgeRange;
import edu.louisiana.caai.lom.model.lom.TypicalLearningTime;

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
 * An implementation of the model object '<em><b>Educational</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getInteractivityType <em>Interactivity Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getLearningResourceType <em>Learning Resource Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getInteractivityLevel <em>Interactivity Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getSemanticDensity <em>Semantic Density</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getIntendedEndUserRole <em>Intended End User Role</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getContext <em>Context</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getTypicalAgeRange <em>Typical Age Range</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getDifficulty <em>Difficulty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getTypicalLearningTime <em>Typical Learning Time</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EducationalImpl extends EObjectImpl implements Educational {
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
	protected EducationalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getEducational();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.EDUCATIONAL__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InteractivityType> getInteractivityType() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_InteractivityType());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LearningResourceType> getLearningResourceType() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_LearningResourceType());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InteractivityLevel> getInteractivityLevel() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_InteractivityLevel());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SemanticDensity> getSemanticDensity() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_SemanticDensity());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IntendedEndUserRole> getIntendedEndUserRole() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_IntendedEndUserRole());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Context> getContext() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_Context());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypicalAgeRange> getTypicalAgeRange() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_TypicalAgeRange());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Difficulty> getDifficulty() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_Difficulty());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypicalLearningTime> getTypicalLearningTime() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_TypicalLearningTime());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getDescription() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_Description());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageId> getLanguage() {
		return getGroup().list(LomPackage.eINSTANCE.getEducational_Language());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getEducational_Any());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, LomPackage.EDUCATIONAL__ANY_ATTRIBUTE);
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
		case LomPackage.EDUCATIONAL__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__INTERACTIVITY_TYPE:
			return ((InternalEList<?>) getInteractivityType()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__LEARNING_RESOURCE_TYPE:
			return ((InternalEList<?>) getLearningResourceType()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__INTERACTIVITY_LEVEL:
			return ((InternalEList<?>) getInteractivityLevel()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__SEMANTIC_DENSITY:
			return ((InternalEList<?>) getSemanticDensity()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__INTENDED_END_USER_ROLE:
			return ((InternalEList<?>) getIntendedEndUserRole()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__CONTEXT:
			return ((InternalEList<?>) getContext()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__TYPICAL_AGE_RANGE:
			return ((InternalEList<?>) getTypicalAgeRange()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__DIFFICULTY:
			return ((InternalEList<?>) getDifficulty()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__TYPICAL_LEARNING_TIME:
			return ((InternalEList<?>) getTypicalLearningTime()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__DESCRIPTION:
			return ((InternalEList<?>) getDescription()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__LANGUAGE:
			return ((InternalEList<?>) getLanguage()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.EDUCATIONAL__ANY_ATTRIBUTE:
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
		case LomPackage.EDUCATIONAL__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.EDUCATIONAL__INTERACTIVITY_TYPE:
			return getInteractivityType();
		case LomPackage.EDUCATIONAL__LEARNING_RESOURCE_TYPE:
			return getLearningResourceType();
		case LomPackage.EDUCATIONAL__INTERACTIVITY_LEVEL:
			return getInteractivityLevel();
		case LomPackage.EDUCATIONAL__SEMANTIC_DENSITY:
			return getSemanticDensity();
		case LomPackage.EDUCATIONAL__INTENDED_END_USER_ROLE:
			return getIntendedEndUserRole();
		case LomPackage.EDUCATIONAL__CONTEXT:
			return getContext();
		case LomPackage.EDUCATIONAL__TYPICAL_AGE_RANGE:
			return getTypicalAgeRange();
		case LomPackage.EDUCATIONAL__DIFFICULTY:
			return getDifficulty();
		case LomPackage.EDUCATIONAL__TYPICAL_LEARNING_TIME:
			return getTypicalLearningTime();
		case LomPackage.EDUCATIONAL__DESCRIPTION:
			return getDescription();
		case LomPackage.EDUCATIONAL__LANGUAGE:
			return getLanguage();
		case LomPackage.EDUCATIONAL__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.EDUCATIONAL__ANY_ATTRIBUTE:
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
		case LomPackage.EDUCATIONAL__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.EDUCATIONAL__INTERACTIVITY_TYPE:
			getInteractivityType().clear();
			getInteractivityType().addAll((Collection<? extends InteractivityType>) newValue);
			return;
		case LomPackage.EDUCATIONAL__LEARNING_RESOURCE_TYPE:
			getLearningResourceType().clear();
			getLearningResourceType().addAll((Collection<? extends LearningResourceType>) newValue);
			return;
		case LomPackage.EDUCATIONAL__INTERACTIVITY_LEVEL:
			getInteractivityLevel().clear();
			getInteractivityLevel().addAll((Collection<? extends InteractivityLevel>) newValue);
			return;
		case LomPackage.EDUCATIONAL__SEMANTIC_DENSITY:
			getSemanticDensity().clear();
			getSemanticDensity().addAll((Collection<? extends SemanticDensity>) newValue);
			return;
		case LomPackage.EDUCATIONAL__INTENDED_END_USER_ROLE:
			getIntendedEndUserRole().clear();
			getIntendedEndUserRole().addAll((Collection<? extends IntendedEndUserRole>) newValue);
			return;
		case LomPackage.EDUCATIONAL__CONTEXT:
			getContext().clear();
			getContext().addAll((Collection<? extends Context>) newValue);
			return;
		case LomPackage.EDUCATIONAL__TYPICAL_AGE_RANGE:
			getTypicalAgeRange().clear();
			getTypicalAgeRange().addAll((Collection<? extends TypicalAgeRange>) newValue);
			return;
		case LomPackage.EDUCATIONAL__DIFFICULTY:
			getDifficulty().clear();
			getDifficulty().addAll((Collection<? extends Difficulty>) newValue);
			return;
		case LomPackage.EDUCATIONAL__TYPICAL_LEARNING_TIME:
			getTypicalLearningTime().clear();
			getTypicalLearningTime().addAll((Collection<? extends TypicalLearningTime>) newValue);
			return;
		case LomPackage.EDUCATIONAL__DESCRIPTION:
			getDescription().clear();
			getDescription().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case LomPackage.EDUCATIONAL__LANGUAGE:
			getLanguage().clear();
			getLanguage().addAll((Collection<? extends LanguageId>) newValue);
			return;
		case LomPackage.EDUCATIONAL__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.EDUCATIONAL__ANY_ATTRIBUTE:
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
		case LomPackage.EDUCATIONAL__GROUP:
			getGroup().clear();
			return;
		case LomPackage.EDUCATIONAL__INTERACTIVITY_TYPE:
			getInteractivityType().clear();
			return;
		case LomPackage.EDUCATIONAL__LEARNING_RESOURCE_TYPE:
			getLearningResourceType().clear();
			return;
		case LomPackage.EDUCATIONAL__INTERACTIVITY_LEVEL:
			getInteractivityLevel().clear();
			return;
		case LomPackage.EDUCATIONAL__SEMANTIC_DENSITY:
			getSemanticDensity().clear();
			return;
		case LomPackage.EDUCATIONAL__INTENDED_END_USER_ROLE:
			getIntendedEndUserRole().clear();
			return;
		case LomPackage.EDUCATIONAL__CONTEXT:
			getContext().clear();
			return;
		case LomPackage.EDUCATIONAL__TYPICAL_AGE_RANGE:
			getTypicalAgeRange().clear();
			return;
		case LomPackage.EDUCATIONAL__DIFFICULTY:
			getDifficulty().clear();
			return;
		case LomPackage.EDUCATIONAL__TYPICAL_LEARNING_TIME:
			getTypicalLearningTime().clear();
			return;
		case LomPackage.EDUCATIONAL__DESCRIPTION:
			getDescription().clear();
			return;
		case LomPackage.EDUCATIONAL__LANGUAGE:
			getLanguage().clear();
			return;
		case LomPackage.EDUCATIONAL__ANY:
			getAny().clear();
			return;
		case LomPackage.EDUCATIONAL__ANY_ATTRIBUTE:
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
		case LomPackage.EDUCATIONAL__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.EDUCATIONAL__INTERACTIVITY_TYPE:
			return !getInteractivityType().isEmpty();
		case LomPackage.EDUCATIONAL__LEARNING_RESOURCE_TYPE:
			return !getLearningResourceType().isEmpty();
		case LomPackage.EDUCATIONAL__INTERACTIVITY_LEVEL:
			return !getInteractivityLevel().isEmpty();
		case LomPackage.EDUCATIONAL__SEMANTIC_DENSITY:
			return !getSemanticDensity().isEmpty();
		case LomPackage.EDUCATIONAL__INTENDED_END_USER_ROLE:
			return !getIntendedEndUserRole().isEmpty();
		case LomPackage.EDUCATIONAL__CONTEXT:
			return !getContext().isEmpty();
		case LomPackage.EDUCATIONAL__TYPICAL_AGE_RANGE:
			return !getTypicalAgeRange().isEmpty();
		case LomPackage.EDUCATIONAL__DIFFICULTY:
			return !getDifficulty().isEmpty();
		case LomPackage.EDUCATIONAL__TYPICAL_LEARNING_TIME:
			return !getTypicalLearningTime().isEmpty();
		case LomPackage.EDUCATIONAL__DESCRIPTION:
			return !getDescription().isEmpty();
		case LomPackage.EDUCATIONAL__LANGUAGE:
			return !getLanguage().isEmpty();
		case LomPackage.EDUCATIONAL__ANY:
			return !getAny().isEmpty();
		case LomPackage.EDUCATIONAL__ANY_ATTRIBUTE:
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

} //EducationalImpl
