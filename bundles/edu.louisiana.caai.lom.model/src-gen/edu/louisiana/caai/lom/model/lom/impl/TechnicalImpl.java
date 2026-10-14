/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.lom.Duration;
import edu.louisiana.caai.lom.model.lom.Format;
import edu.louisiana.caai.lom.model.lom.InstallationRemarks;
import edu.louisiana.caai.lom.model.lom.Location;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.OtherPlatformRequirements;
import edu.louisiana.caai.lom.model.lom.Requirement;
import edu.louisiana.caai.lom.model.lom.Size;
import edu.louisiana.caai.lom.model.lom.Technical;

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
 * An implementation of the model object '<em><b>Technical</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getSize <em>Size</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getRequirement <em>Requirement</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getInstallationRemarks <em>Installation Remarks</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getOtherPlatformRequirements <em>Other Platform Requirements</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getAny <em>Any</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TechnicalImpl extends EObjectImpl implements Technical {
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
	protected static final Object UNIQUE_ELEMENT_NAME_EDEFAULT = "technical";

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
	protected TechnicalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LomPackage.eINSTANCE.getTechnical();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LomPackage.TECHNICAL__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Format> getFormat() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_Format());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Size> getSize() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_Size());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Location> getLocation() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_Location());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Requirement> getRequirement() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_Requirement());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InstallationRemarks> getInstallationRemarks() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_InstallationRemarks());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<OtherPlatformRequirements> getOtherPlatformRequirements() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_OtherPlatformRequirements());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Duration> getDuration() {
		return getGroup().list(LomPackage.eINSTANCE.getTechnical_Duration());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getAny() {
		return (FeatureMap) getGroup().<FeatureMap.Entry>list(LomPackage.eINSTANCE.getTechnical_Any());
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
			eNotify(new ENotificationImpl(this, Notification.SET, LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME,
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME,
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
			anyAttribute = new BasicFeatureMap(this, LomPackage.TECHNICAL__ANY_ATTRIBUTE);
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
		case LomPackage.TECHNICAL__GROUP:
			return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__FORMAT:
			return ((InternalEList<?>) getFormat()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__SIZE:
			return ((InternalEList<?>) getSize()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__LOCATION:
			return ((InternalEList<?>) getLocation()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__REQUIREMENT:
			return ((InternalEList<?>) getRequirement()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__INSTALLATION_REMARKS:
			return ((InternalEList<?>) getInstallationRemarks()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__OTHER_PLATFORM_REQUIREMENTS:
			return ((InternalEList<?>) getOtherPlatformRequirements()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__DURATION:
			return ((InternalEList<?>) getDuration()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__ANY:
			return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
		case LomPackage.TECHNICAL__ANY_ATTRIBUTE:
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
		case LomPackage.TECHNICAL__GROUP:
			if (coreType)
				return getGroup();
			return ((FeatureMap.Internal) getGroup()).getWrapper();
		case LomPackage.TECHNICAL__FORMAT:
			return getFormat();
		case LomPackage.TECHNICAL__SIZE:
			return getSize();
		case LomPackage.TECHNICAL__LOCATION:
			return getLocation();
		case LomPackage.TECHNICAL__REQUIREMENT:
			return getRequirement();
		case LomPackage.TECHNICAL__INSTALLATION_REMARKS:
			return getInstallationRemarks();
		case LomPackage.TECHNICAL__OTHER_PLATFORM_REQUIREMENTS:
			return getOtherPlatformRequirements();
		case LomPackage.TECHNICAL__DURATION:
			return getDuration();
		case LomPackage.TECHNICAL__ANY:
			if (coreType)
				return getAny();
			return ((FeatureMap.Internal) getAny()).getWrapper();
		case LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		case LomPackage.TECHNICAL__ANY_ATTRIBUTE:
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
		case LomPackage.TECHNICAL__GROUP:
			((FeatureMap.Internal) getGroup()).set(newValue);
			return;
		case LomPackage.TECHNICAL__FORMAT:
			getFormat().clear();
			getFormat().addAll((Collection<? extends Format>) newValue);
			return;
		case LomPackage.TECHNICAL__SIZE:
			getSize().clear();
			getSize().addAll((Collection<? extends Size>) newValue);
			return;
		case LomPackage.TECHNICAL__LOCATION:
			getLocation().clear();
			getLocation().addAll((Collection<? extends Location>) newValue);
			return;
		case LomPackage.TECHNICAL__REQUIREMENT:
			getRequirement().clear();
			getRequirement().addAll((Collection<? extends Requirement>) newValue);
			return;
		case LomPackage.TECHNICAL__INSTALLATION_REMARKS:
			getInstallationRemarks().clear();
			getInstallationRemarks().addAll((Collection<? extends InstallationRemarks>) newValue);
			return;
		case LomPackage.TECHNICAL__OTHER_PLATFORM_REQUIREMENTS:
			getOtherPlatformRequirements().clear();
			getOtherPlatformRequirements().addAll((Collection<? extends OtherPlatformRequirements>) newValue);
			return;
		case LomPackage.TECHNICAL__DURATION:
			getDuration().clear();
			getDuration().addAll((Collection<? extends Duration>) newValue);
			return;
		case LomPackage.TECHNICAL__ANY:
			((FeatureMap.Internal) getAny()).set(newValue);
			return;
		case LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME:
			setUniqueElementName(newValue);
			return;
		case LomPackage.TECHNICAL__ANY_ATTRIBUTE:
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
		case LomPackage.TECHNICAL__GROUP:
			getGroup().clear();
			return;
		case LomPackage.TECHNICAL__FORMAT:
			getFormat().clear();
			return;
		case LomPackage.TECHNICAL__SIZE:
			getSize().clear();
			return;
		case LomPackage.TECHNICAL__LOCATION:
			getLocation().clear();
			return;
		case LomPackage.TECHNICAL__REQUIREMENT:
			getRequirement().clear();
			return;
		case LomPackage.TECHNICAL__INSTALLATION_REMARKS:
			getInstallationRemarks().clear();
			return;
		case LomPackage.TECHNICAL__OTHER_PLATFORM_REQUIREMENTS:
			getOtherPlatformRequirements().clear();
			return;
		case LomPackage.TECHNICAL__DURATION:
			getDuration().clear();
			return;
		case LomPackage.TECHNICAL__ANY:
			getAny().clear();
			return;
		case LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
			return;
		case LomPackage.TECHNICAL__ANY_ATTRIBUTE:
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
		case LomPackage.TECHNICAL__GROUP:
			return group != null && !group.isEmpty();
		case LomPackage.TECHNICAL__FORMAT:
			return !getFormat().isEmpty();
		case LomPackage.TECHNICAL__SIZE:
			return !getSize().isEmpty();
		case LomPackage.TECHNICAL__LOCATION:
			return !getLocation().isEmpty();
		case LomPackage.TECHNICAL__REQUIREMENT:
			return !getRequirement().isEmpty();
		case LomPackage.TECHNICAL__INSTALLATION_REMARKS:
			return !getInstallationRemarks().isEmpty();
		case LomPackage.TECHNICAL__OTHER_PLATFORM_REQUIREMENTS:
			return !getOtherPlatformRequirements().isEmpty();
		case LomPackage.TECHNICAL__DURATION:
			return !getDuration().isEmpty();
		case LomPackage.TECHNICAL__ANY:
			return !getAny().isEmpty();
		case LomPackage.TECHNICAL__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
		case LomPackage.TECHNICAL__ANY_ATTRIBUTE:
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

} //TechnicalImpl
