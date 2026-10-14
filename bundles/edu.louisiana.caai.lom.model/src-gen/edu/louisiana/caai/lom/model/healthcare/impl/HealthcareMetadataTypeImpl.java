/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl#getHealthcareEducation <em>Healthcare Education</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl#getHealthcareAsset <em>Healthcare Asset</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HealthcareMetadataTypeImpl extends EObjectImpl implements HealthcareMetadataType {
	/**
	 * The cached value of the '{@link #getHealthcareEducation() <em>Healthcare Education</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthcareEducation()
	 * @generated
	 * @ordered
	 */
	protected HealthcareEducationType healthcareEducation;

	/**
	 * The cached value of the '{@link #getHealthcareAsset() <em>Healthcare Asset</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthcareAsset()
	 * @generated
	 * @ordered
	 */
	protected HealthcareAssetType healthcareAsset;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_ELEMENT_NAME_EDEFAULT = "healthcareMetadata";

	/**
	 * The cached value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected String uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;

	/**
	 * This is true if the Unique Element Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean uniqueElementNameESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HealthcareMetadataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.HEALTHCARE_METADATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareEducationType getHealthcareEducation() {
		return healthcareEducation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHealthcareEducation(HealthcareEducationType newHealthcareEducation,
			NotificationChain msgs) {
		HealthcareEducationType oldHealthcareEducation = healthcareEducation;
		healthcareEducation = newHealthcareEducation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION, oldHealthcareEducation,
					newHealthcareEducation);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthcareEducation(HealthcareEducationType newHealthcareEducation) {
		if (newHealthcareEducation != healthcareEducation) {
			NotificationChain msgs = null;
			if (healthcareEducation != null)
				msgs = ((InternalEObject) healthcareEducation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION, null,
						msgs);
			if (newHealthcareEducation != null)
				msgs = ((InternalEObject) newHealthcareEducation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION, null,
						msgs);
			msgs = basicSetHealthcareEducation(newHealthcareEducation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION, newHealthcareEducation,
					newHealthcareEducation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HealthcareAssetType getHealthcareAsset() {
		return healthcareAsset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHealthcareAsset(HealthcareAssetType newHealthcareAsset, NotificationChain msgs) {
		HealthcareAssetType oldHealthcareAsset = healthcareAsset;
		healthcareAsset = newHealthcareAsset;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET, oldHealthcareAsset,
					newHealthcareAsset);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthcareAsset(HealthcareAssetType newHealthcareAsset) {
		if (newHealthcareAsset != healthcareAsset) {
			NotificationChain msgs = null;
			if (healthcareAsset != null)
				msgs = ((InternalEObject) healthcareAsset).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET, null,
						msgs);
			if (newHealthcareAsset != null)
				msgs = ((InternalEObject) newHealthcareAsset).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET, null,
						msgs);
			msgs = basicSetHealthcareAsset(newHealthcareAsset, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET, newHealthcareAsset,
					newHealthcareAsset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUniqueElementName() {
		return uniqueElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUniqueElementName(String newUniqueElementName) {
		String oldUniqueElementName = uniqueElementName;
		uniqueElementName = newUniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					uniqueElementName, !oldUniqueElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUniqueElementName() {
		String oldUniqueElementName = uniqueElementName;
		boolean oldUniqueElementNameESet = uniqueElementNameESet;
		uniqueElementName = UNIQUE_ELEMENT_NAME_EDEFAULT;
		uniqueElementNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
					UNIQUE_ELEMENT_NAME_EDEFAULT, oldUniqueElementNameESet));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION:
			return basicSetHealthcareEducation(null, msgs);
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET:
			return basicSetHealthcareAsset(null, msgs);
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
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION:
			return getHealthcareEducation();
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET:
			return getHealthcareAsset();
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION:
			setHealthcareEducation((HealthcareEducationType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET:
			setHealthcareAsset((HealthcareAssetType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME:
			setUniqueElementName((String) newValue);
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
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION:
			setHealthcareEducation((HealthcareEducationType) null);
			return;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET:
			setHealthcareAsset((HealthcareAssetType) null);
			return;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME:
			unsetUniqueElementName();
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
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION:
			return healthcareEducation != null;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET:
			return healthcareAsset != null;
		case HealthcarePackage.HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME:
			return isSetUniqueElementName();
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
		result.append(" (uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //HealthcareMetadataTypeImpl
