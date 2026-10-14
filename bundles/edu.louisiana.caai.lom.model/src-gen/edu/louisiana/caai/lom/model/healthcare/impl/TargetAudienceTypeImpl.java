/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.TargetAudienceType;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Target Audience Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl#getAudienceCategory <em>Audience Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl#getProfession <em>Profession</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl#getSpecialty <em>Specialty</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl#getReadingLevel <em>Reading Level</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TargetAudienceTypeImpl extends EObjectImpl implements TargetAudienceType {
	/**
	 * The cached value of the '{@link #getAudienceCategory() <em>Audience Category</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAudienceCategory()
	 * @generated
	 * @ordered
	 */
	protected EList<AudienceCategoryType> audienceCategory;

	/**
	 * The cached value of the '{@link #getProfession() <em>Profession</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfession()
	 * @generated
	 * @ordered
	 */
	protected EList<LanguageString> profession;

	/**
	 * The cached value of the '{@link #getSpecialty() <em>Specialty</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecialty()
	 * @generated
	 * @ordered
	 */
	protected EList<LanguageString> specialty;

	/**
	 * The cached value of the '{@link #getReadingLevel() <em>Reading Level</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadingLevel()
	 * @generated
	 * @ordered
	 */
	protected EList<LanguageString> readingLevel;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_ELEMENT_NAME_EDEFAULT = "targetAudience";

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
	protected TargetAudienceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.TARGET_AUDIENCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AudienceCategoryType> getAudienceCategory() {
		if (audienceCategory == null) {
			audienceCategory = new EDataTypeEList<AudienceCategoryType>(AudienceCategoryType.class, this,
					HealthcarePackage.TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY);
		}
		return audienceCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getProfession() {
		if (profession == null) {
			profession = new EObjectContainmentEList<LanguageString>(LanguageString.class, this,
					HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION);
		}
		return profession;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getSpecialty() {
		if (specialty == null) {
			specialty = new EObjectContainmentEList<LanguageString>(LanguageString.class, this,
					HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY);
		}
		return specialty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LanguageString> getReadingLevel() {
		if (readingLevel == null) {
			readingLevel = new EObjectContainmentEList<LanguageString>(LanguageString.class, this,
					HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL);
		}
		return readingLevel;
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
					HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
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
					HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
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
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION:
			return ((InternalEList<?>) getProfession()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY:
			return ((InternalEList<?>) getSpecialty()).basicRemove(otherEnd, msgs);
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL:
			return ((InternalEList<?>) getReadingLevel()).basicRemove(otherEnd, msgs);
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
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY:
			return getAudienceCategory();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION:
			return getProfession();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY:
			return getSpecialty();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL:
			return getReadingLevel();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME:
			return getUniqueElementName();
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
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY:
			getAudienceCategory().clear();
			getAudienceCategory().addAll((Collection<? extends AudienceCategoryType>) newValue);
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION:
			getProfession().clear();
			getProfession().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY:
			getSpecialty().clear();
			getSpecialty().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL:
			getReadingLevel().clear();
			getReadingLevel().addAll((Collection<? extends LanguageString>) newValue);
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME:
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
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY:
			getAudienceCategory().clear();
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION:
			getProfession().clear();
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY:
			getSpecialty().clear();
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL:
			getReadingLevel().clear();
			return;
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME:
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
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY:
			return audienceCategory != null && !audienceCategory.isEmpty();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__PROFESSION:
			return profession != null && !profession.isEmpty();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__SPECIALTY:
			return specialty != null && !specialty.isEmpty();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__READING_LEVEL:
			return readingLevel != null && !readingLevel.isEmpty();
		case HealthcarePackage.TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME:
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
		result.append(" (audienceCategory: ");
		result.append(audienceCategory);
		result.append(", uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TargetAudienceTypeImpl
