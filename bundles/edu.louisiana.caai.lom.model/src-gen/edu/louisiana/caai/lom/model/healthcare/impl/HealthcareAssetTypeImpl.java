/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare.impl;

import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType;
import edu.louisiana.caai.lom.model.healthcare.OrientationType;
import edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;

import edu.louisiana.caai.lom.model.lom.LanguageString;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Asset Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getAnnotated <em>Annotated</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getClinicalHistory <em>Clinical History</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getMagnification <em>Magnification</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getMedicalImageType <em>Medical Image Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getSpecimenType <em>Specimen Type</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getFileHeight <em>File Height</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getFileWidth <em>File Width</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl#getUniqueElementName <em>Unique Element Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HealthcareAssetTypeImpl extends EObjectImpl implements HealthcareAssetType {
	/**
	 * The default value of the '{@link #getAnnotated() <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotated()
	 * @generated
	 * @ordered
	 */
	protected static final YesNoType ANNOTATED_EDEFAULT = YesNoType.YES;

	/**
	 * The cached value of the '{@link #getAnnotated() <em>Annotated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotated()
	 * @generated
	 * @ordered
	 */
	protected YesNoType annotated = ANNOTATED_EDEFAULT;

	/**
	 * This is true if the Annotated attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean annotatedESet;

	/**
	 * The cached value of the '{@link #getClinicalHistory() <em>Clinical History</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClinicalHistory()
	 * @generated
	 * @ordered
	 */
	protected LanguageString clinicalHistory;

	/**
	 * The default value of the '{@link #getMagnification() <em>Magnification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMagnification()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger MAGNIFICATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMagnification() <em>Magnification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMagnification()
	 * @generated
	 * @ordered
	 */
	protected BigInteger magnification = MAGNIFICATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final OrientationType ORIENTATION_EDEFAULT = OrientationType.AXIAL;

	/**
	 * The cached value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected OrientationType orientation = ORIENTATION_EDEFAULT;

	/**
	 * This is true if the Orientation attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean orientationESet;

	/**
	 * The default value of the '{@link #getMedicalImageType() <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMedicalImageType()
	 * @generated
	 * @ordered
	 */
	protected static final MedicalImageTypeType MEDICAL_IMAGE_TYPE_EDEFAULT = MedicalImageTypeType.ANGIOGRAM;

	/**
	 * The cached value of the '{@link #getMedicalImageType() <em>Medical Image Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMedicalImageType()
	 * @generated
	 * @ordered
	 */
	protected MedicalImageTypeType medicalImageType = MEDICAL_IMAGE_TYPE_EDEFAULT;

	/**
	 * This is true if the Medical Image Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean medicalImageTypeESet;

	/**
	 * The default value of the '{@link #getSpecimenType() <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecimenType()
	 * @generated
	 * @ordered
	 */
	protected static final SpecimenTypeType SPECIMEN_TYPE_EDEFAULT = SpecimenTypeType.CELL;

	/**
	 * The cached value of the '{@link #getSpecimenType() <em>Specimen Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecimenType()
	 * @generated
	 * @ordered
	 */
	protected SpecimenTypeType specimenType = SPECIMEN_TYPE_EDEFAULT;

	/**
	 * This is true if the Specimen Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean specimenTypeESet;

	/**
	 * The default value of the '{@link #getFileHeight() <em>File Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileHeight()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger FILE_HEIGHT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileHeight() <em>File Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileHeight()
	 * @generated
	 * @ordered
	 */
	protected BigInteger fileHeight = FILE_HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileWidth() <em>File Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileWidth()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger FILE_WIDTH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileWidth() <em>File Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileWidth()
	 * @generated
	 * @ordered
	 */
	protected BigInteger fileWidth = FILE_WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getUniqueElementName() <em>Unique Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_ELEMENT_NAME_EDEFAULT = "healthcareAsset";

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
	protected HealthcareAssetTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HealthcarePackage.Literals.HEALTHCARE_ASSET_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public YesNoType getAnnotated() {
		return annotated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnnotated(YesNoType newAnnotated) {
		YesNoType oldAnnotated = annotated;
		annotated = newAnnotated == null ? ANNOTATED_EDEFAULT : newAnnotated;
		boolean oldAnnotatedESet = annotatedESet;
		annotatedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED,
					oldAnnotated, annotated, !oldAnnotatedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetAnnotated() {
		YesNoType oldAnnotated = annotated;
		boolean oldAnnotatedESet = annotatedESet;
		annotated = ANNOTATED_EDEFAULT;
		annotatedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED,
					oldAnnotated, ANNOTATED_EDEFAULT, oldAnnotatedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetAnnotated() {
		return annotatedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageString getClinicalHistory() {
		return clinicalHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClinicalHistory(LanguageString newClinicalHistory, NotificationChain msgs) {
		LanguageString oldClinicalHistory = clinicalHistory;
		clinicalHistory = newClinicalHistory;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY, oldClinicalHistory, newClinicalHistory);
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
	public void setClinicalHistory(LanguageString newClinicalHistory) {
		if (newClinicalHistory != clinicalHistory) {
			NotificationChain msgs = null;
			if (clinicalHistory != null)
				msgs = ((InternalEObject) clinicalHistory).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY, null, msgs);
			if (newClinicalHistory != null)
				msgs = ((InternalEObject) newClinicalHistory).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY, null, msgs);
			msgs = basicSetClinicalHistory(newClinicalHistory, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY, newClinicalHistory, newClinicalHistory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getMagnification() {
		return magnification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMagnification(BigInteger newMagnification) {
		BigInteger oldMagnification = magnification;
		magnification = newMagnification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__MAGNIFICATION, oldMagnification, magnification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OrientationType getOrientation() {
		return orientation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOrientation(OrientationType newOrientation) {
		OrientationType oldOrientation = orientation;
		orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
		boolean oldOrientationESet = orientationESet;
		orientationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION,
					oldOrientation, orientation, !oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetOrientation() {
		OrientationType oldOrientation = orientation;
		boolean oldOrientationESet = orientationESet;
		orientation = ORIENTATION_EDEFAULT;
		orientationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION, oldOrientation, ORIENTATION_EDEFAULT,
					oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetOrientation() {
		return orientationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MedicalImageTypeType getMedicalImageType() {
		return medicalImageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMedicalImageType(MedicalImageTypeType newMedicalImageType) {
		MedicalImageTypeType oldMedicalImageType = medicalImageType;
		medicalImageType = newMedicalImageType == null ? MEDICAL_IMAGE_TYPE_EDEFAULT : newMedicalImageType;
		boolean oldMedicalImageTypeESet = medicalImageTypeESet;
		medicalImageTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE, oldMedicalImageType, medicalImageType,
					!oldMedicalImageTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMedicalImageType() {
		MedicalImageTypeType oldMedicalImageType = medicalImageType;
		boolean oldMedicalImageTypeESet = medicalImageTypeESet;
		medicalImageType = MEDICAL_IMAGE_TYPE_EDEFAULT;
		medicalImageTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE, oldMedicalImageType,
					MEDICAL_IMAGE_TYPE_EDEFAULT, oldMedicalImageTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMedicalImageType() {
		return medicalImageTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecimenTypeType getSpecimenType() {
		return specimenType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpecimenType(SpecimenTypeType newSpecimenType) {
		SpecimenTypeType oldSpecimenType = specimenType;
		specimenType = newSpecimenType == null ? SPECIMEN_TYPE_EDEFAULT : newSpecimenType;
		boolean oldSpecimenTypeESet = specimenTypeESet;
		specimenTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE, oldSpecimenType, specimenType,
					!oldSpecimenTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetSpecimenType() {
		SpecimenTypeType oldSpecimenType = specimenType;
		boolean oldSpecimenTypeESet = specimenTypeESet;
		specimenType = SPECIMEN_TYPE_EDEFAULT;
		specimenTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE, oldSpecimenType, SPECIMEN_TYPE_EDEFAULT,
					oldSpecimenTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetSpecimenType() {
		return specimenTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getFileHeight() {
		return fileHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileHeight(BigInteger newFileHeight) {
		BigInteger oldFileHeight = fileHeight;
		fileHeight = newFileHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_HEIGHT,
					oldFileHeight, fileHeight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getFileWidth() {
		return fileWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileWidth(BigInteger newFileWidth) {
		BigInteger oldFileWidth = fileWidth;
		fileWidth = newFileWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_WIDTH,
					oldFileWidth, fileWidth));
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
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
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
					HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME, oldUniqueElementName,
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
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY:
			return basicSetClinicalHistory(null, msgs);
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
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED:
			return getAnnotated();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY:
			return getClinicalHistory();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MAGNIFICATION:
			return getMagnification();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION:
			return getOrientation();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE:
			return getMedicalImageType();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE:
			return getSpecimenType();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_HEIGHT:
			return getFileHeight();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_WIDTH:
			return getFileWidth();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME:
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
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED:
			setAnnotated((YesNoType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY:
			setClinicalHistory((LanguageString) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MAGNIFICATION:
			setMagnification((BigInteger) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION:
			setOrientation((OrientationType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE:
			setMedicalImageType((MedicalImageTypeType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE:
			setSpecimenType((SpecimenTypeType) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_HEIGHT:
			setFileHeight((BigInteger) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_WIDTH:
			setFileWidth((BigInteger) newValue);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME:
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
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED:
			unsetAnnotated();
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY:
			setClinicalHistory((LanguageString) null);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MAGNIFICATION:
			setMagnification(MAGNIFICATION_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION:
			unsetOrientation();
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE:
			unsetMedicalImageType();
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE:
			unsetSpecimenType();
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_HEIGHT:
			setFileHeight(FILE_HEIGHT_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_WIDTH:
			setFileWidth(FILE_WIDTH_EDEFAULT);
			return;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME:
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
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ANNOTATED:
			return isSetAnnotated();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY:
			return clinicalHistory != null;
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MAGNIFICATION:
			return MAGNIFICATION_EDEFAULT == null ? magnification != null
					: !MAGNIFICATION_EDEFAULT.equals(magnification);
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__ORIENTATION:
			return isSetOrientation();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE:
			return isSetMedicalImageType();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE:
			return isSetSpecimenType();
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_HEIGHT:
			return FILE_HEIGHT_EDEFAULT == null ? fileHeight != null : !FILE_HEIGHT_EDEFAULT.equals(fileHeight);
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__FILE_WIDTH:
			return FILE_WIDTH_EDEFAULT == null ? fileWidth != null : !FILE_WIDTH_EDEFAULT.equals(fileWidth);
		case HealthcarePackage.HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME:
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
		result.append(" (annotated: ");
		if (annotatedESet)
			result.append(annotated);
		else
			result.append("<unset>");
		result.append(", magnification: ");
		result.append(magnification);
		result.append(", orientation: ");
		if (orientationESet)
			result.append(orientation);
		else
			result.append("<unset>");
		result.append(", medicalImageType: ");
		if (medicalImageTypeESet)
			result.append(medicalImageType);
		else
			result.append("<unset>");
		result.append(", specimenType: ");
		if (specimenTypeESet)
			result.append(specimenType);
		else
			result.append("<unset>");
		result.append(", fileHeight: ");
		result.append(fileHeight);
		result.append(", fileWidth: ");
		result.append(fileWidth);
		result.append(", uniqueElementName: ");
		if (uniqueElementNameESet)
			result.append(uniqueElementName);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //HealthcareAssetTypeImpl
