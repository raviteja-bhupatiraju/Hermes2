/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.AggregationLevel;
import edu.louisiana.caai.lom.model.lom.Coverage;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Identifier;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Structure;
import edu.louisiana.caai.lom.model.lom.Title;

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
 * An implementation of the model object '<em><b>General</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getCoverage <em>Coverage</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getStructure <em>Structure</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getAggregationLevel <em>Aggregation Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GeneralImpl extends EObjectImpl implements General {
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
	protected static final Object UNIQUE_ELEMENT_NAME_EDEFAULT = "general";

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
	protected GeneralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getGeneral();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.GENERAL__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Identifier> getIdentifier() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Identifier());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Title> getTitle() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Title());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageId> getLanguage() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Language());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getDescription() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Description());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Keyword> getKeyword() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Keyword());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Coverage> getCoverage() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Coverage());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Structure> getStructure() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_Structure());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AggregationLevel> getAggregationLevel() {
		return getGroup().list(LomPackage.eINSTANCE.getGeneral_AggregationLevel());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getGeneral_Any());
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
			eNotify(new ENotificationImpl(this, Notification.SET, LomPackage.GENERAL__UNIQUE_ELEMENT_NAME,
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, LomPackage.GENERAL__UNIQUE_ELEMENT_NAME,
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
			anyAttribute = new BasicFeatureMap(this, LomPackage.GENERAL__ANY_ATTRIBUTE);
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
		case LomPackage.GENERAL__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__IDENTIFIER:
			return ((InternalEList<?>) getIdentifier()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__TITLE:
			return ((InternalEList<?>) getTitle()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__LANGUAGE:
			return ((InternalEList<?>) getLanguage()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__DESCRIPTION:
			return ((InternalEList<?>) getDescription()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__KEYWORD:
			return ((InternalEList<?>) getKeyword()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__COVERAGE:
			return ((InternalEList<?>) getCoverage()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__STRUCTURE:
			return ((InternalEList<?>) getStructure()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__AGGREGATION_LEVEL:
			return ((InternalEList<?>) getAggregationLevel()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.GENERAL__ANY_ATTRIBUTE:
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
		case LomPackage.GENERAL__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.GENERAL__IDENTIFIER:
			return getIdentifier();
		case LomPackage.GENERAL__TITLE:
			return getTitle();
		case LomPackage.GENERAL__LANGUAGE:
			return getLanguage();
		case LomPackage.GENERAL__DESCRIPTION:
			return getDescription();
		case LomPackage.GENERAL__KEYWORD:
			return getKeyword();
		case LomPackage.GENERAL__COVERAGE:
			return getCoverage();
		case LomPackage.GENERAL__STRUCTURE:
			return getStructure();
		case LomPackage.GENERAL__AGGREGATION_LEVEL:
			return getAggregationLevel();
		case LomPackage.GENERAL__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.GENERAL__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		case LomPackage.GENERAL__ANY_ATTRIBUTE:
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
		case LomPackage.GENERAL__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.GENERAL__IDENTIFIER:
			getIdentifier().clear();
			getIdentifier().addAll((Collection<? extends Identifier>) newValue);
			return;
		case LomPackage.GENERAL__TITLE:
			getTitle().clear();
			getTitle().addAll((Collection<? extends Title>) newValue);
			return;
		case LomPackage.GENERAL__LANGUAGE:
			getLanguage().clear();
			getLanguage().addAll((Collection<? extends LanguageId>) newValue);
			return;
		case LomPackage.GENERAL__DESCRIPTION:
			getDescription().clear();
			getDescription().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case LomPackage.GENERAL__KEYWORD:
			getKeyword().clear();
			getKeyword().addAll((Collection<? extends Keyword>) newValue);
			return;
		case LomPackage.GENERAL__COVERAGE:
			getCoverage().clear();
			getCoverage().addAll((Collection<? extends Coverage>) newValue);
			return;
		case LomPackage.GENERAL__STRUCTURE:
			getStructure().clear();
			getStructure().addAll((Collection<? extends Structure>) newValue);
			return;
		case LomPackage.GENERAL__AGGREGATION_LEVEL:
			getAggregationLevel().clear();
			getAggregationLevel().addAll((Collection<? extends AggregationLevel>) newValue);
			return;
		case LomPackage.GENERAL__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.GENERAL__UNIQUE_ELEMENT_NAME:
			setUniqueElementName(newValue);
			return;
		case LomPackage.GENERAL__ANY_ATTRIBUTE:
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
		case LomPackage.GENERAL__GROUP:
			getGroup().clear();
			return;
		case LomPackage.GENERAL__IDENTIFIER:
			getIdentifier().clear();
			return;
		case LomPackage.GENERAL__TITLE:
			getTitle().clear();
			return;
		case LomPackage.GENERAL__LANGUAGE:
			getLanguage().clear();
			return;
		case LomPackage.GENERAL__DESCRIPTION:
			getDescription().clear();
			return;
		case LomPackage.GENERAL__KEYWORD:
			getKeyword().clear();
			return;
		case LomPackage.GENERAL__COVERAGE:
			getCoverage().clear();
			return;
		case LomPackage.GENERAL__STRUCTURE:
			getStructure().clear();
			return;
		case LomPackage.GENERAL__AGGREGATION_LEVEL:
			getAggregationLevel().clear();
			return;
		case LomPackage.GENERAL__ANY:
			getAny().clear();
			return;
		case LomPackage.GENERAL__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		case LomPackage.GENERAL__ANY_ATTRIBUTE:
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
		case LomPackage.GENERAL__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.GENERAL__IDENTIFIER:
			return !getIdentifier().isEmpty();
		case LomPackage.GENERAL__TITLE:
			return !getTitle().isEmpty();
		case LomPackage.GENERAL__LANGUAGE:
			return !getLanguage().isEmpty();
		case LomPackage.GENERAL__DESCRIPTION:
			return !getDescription().isEmpty();
		case LomPackage.GENERAL__KEYWORD:
			return !getKeyword().isEmpty();
		case LomPackage.GENERAL__COVERAGE:
			return !getCoverage().isEmpty();
		case LomPackage.GENERAL__STRUCTURE:
			return !getStructure().isEmpty();
		case LomPackage.GENERAL__AGGREGATION_LEVEL:
			return !getAggregationLevel().isEmpty();
		case LomPackage.GENERAL__ANY:
			return !getAny().isEmpty();
		case LomPackage.GENERAL__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		case LomPackage.GENERAL__ANY_ATTRIBUTE:
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

} //GeneralImpl
