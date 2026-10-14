/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.impl;

import edu.louisiana.caai.lom.model.address.AddressPackage;

import edu.louisiana.caai.lom.model.address.impl.AddressPackageImpl;

import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;

import edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import edu.louisiana.caai.lom.model.healthcarevocab.impl.HealthcarevocabPackageImpl;

import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;

import edu.louisiana.caai.lom.model.lom.util.LomValidator;

import edu.louisiana.caai.lom.model.vocab.VocabPackage;

import edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl;

import java.io.IOException;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LomPackageImpl extends EPackageImpl implements LomPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected String packageFilename = "lom.ecore";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aggregationLevelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aggregationLevelValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aggregationLevelVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass catalogEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contributeMetaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass copyrightAndOtherRestrictionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass copyrightAndOtherRestrictionsValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass copyrightAndOtherRestrictionsVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass costEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass costValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass costVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coverageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass descriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass difficultyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass difficultyValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass difficultyVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass educationalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryTaxonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass installationRemarksEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intendedEndUserRoleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intendedEndUserRoleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intendedEndUserRoleVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityLevelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityLevelValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityLevelVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass keywordEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kindValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass langStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityTypeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactivityTypeVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kindEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kindVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass learningResourceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass learningResourceTypeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass learningResourceTypeVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lifeCycleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lomEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass maximumVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metadataSchemaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass minimumVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass orCompositeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass otherPlatformRequirementsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass purposeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass purposeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass purposeVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rightsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleMetaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleMetaValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleMetaVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass semanticDensityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass semanticDensityValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass semanticDensityVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sizeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structureValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structureVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taxonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taxonPathEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass technicalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass titleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeVocabEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typicalAgeRangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typicalLearningTimeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vCardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum aggregationLevelValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum contextValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum copyrightAndOtherRestrictionsValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum costValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum difficultyValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum intendedEndUserRoleValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum interactivityLevelValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum interactivityTypeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum kindValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum learningResourceTypeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum nameValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum purposeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum roleMetaValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum roleValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum semanticDensityValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sourceValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum statusValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum structureValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typeValuesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType aggregationLevelValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType characterStringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType contextValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType copyrightAndOtherRestrictionsValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType costValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dateTimeStringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType difficultyValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType durationStringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType interactivityLevelValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType intendedEndUserRoleValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType interactivityTypeValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType kindValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType learningResourceTypeValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType mimeTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType purposeValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType roleMetaValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType roleValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType semanticDensityValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sizeBaseEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sourceValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType statusValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType structureValuesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeValuesObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.louisiana.caai.lom.model.lom.LomPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LomPackageImpl() {
		super(eNS_URI, LomFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link LomPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @generated
	 */
	public static LomPackage init() {
		if (isInited)
			return (LomPackage) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLomPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LomPackageImpl theLomPackage = registeredLomPackage instanceof LomPackageImpl
				? (LomPackageImpl) registeredLomPackage
				: new LomPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarePackage.eNS_URI);
		HealthcarePackageImpl theHealthcarePackage = (HealthcarePackageImpl) (registeredPackage instanceof HealthcarePackageImpl
				? registeredPackage
				: HealthcarePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AddressPackage.eNS_URI);
		AddressPackageImpl theAddressPackage = (AddressPackageImpl) (registeredPackage instanceof AddressPackageImpl
				? registeredPackage
				: AddressPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(VocabPackage.eNS_URI);
		VocabPackageImpl theVocabPackage = (VocabPackageImpl) (registeredPackage instanceof VocabPackageImpl
				? registeredPackage
				: VocabPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HealthcarevocabPackage.eNS_URI);
		HealthcarevocabPackageImpl theHealthcarevocabPackage = (HealthcarevocabPackageImpl) (registeredPackage instanceof HealthcarevocabPackageImpl
				? registeredPackage
				: HealthcarevocabPackage.eINSTANCE);

		// Load packages
		theLomPackage.loadPackage();

		// Create package meta-data objects
		theHealthcarePackage.createPackageContents();
		theAddressPackage.createPackageContents();
		theVocabPackage.createPackageContents();
		theHealthcarevocabPackage.createPackageContents();

		// Initialize created meta-data
		theHealthcarePackage.initializePackageContents();
		theAddressPackage.initializePackageContents();
		theVocabPackage.initializePackageContents();
		theHealthcarevocabPackage.initializePackageContents();

		// Fix loaded packages
		theLomPackage.fixPackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theLomPackage, new EValidator.Descriptor() {
			@Override
			public EValidator getEValidator() {
				return LomValidator.INSTANCE;
			}
		});

		// Mark meta-data to indicate it can't be changed
		theLomPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LomPackage.eNS_URI, theLomPackage);
		return theLomPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAggregationLevel() {
		if (aggregationLevelEClass == null) {
			aggregationLevelEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(0);
		}
		return aggregationLevelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevel_UniqueElementName() {
		return (EAttribute) getAggregationLevel().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAggregationLevelValue() {
		if (aggregationLevelValueEClass == null) {
			aggregationLevelValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(1);
		}
		return aggregationLevelValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelValue_Value() {
		return (EAttribute) getAggregationLevelValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelValue_UniqueElementName() {
		return (EAttribute) getAggregationLevelValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelValue_AnyAttribute() {
		return (EAttribute) getAggregationLevelValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAggregationLevelVocab() {
		if (aggregationLevelVocabEClass == null) {
			aggregationLevelVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(4);
		}
		return aggregationLevelVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelVocab_Group() {
		return (EAttribute) getAggregationLevelVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAggregationLevelVocab_Source() {
		return (EReference) getAggregationLevelVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAggregationLevelVocab_Value() {
		return (EReference) getAggregationLevelVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelVocab_Any() {
		return (EAttribute) getAggregationLevelVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAggregationLevelVocab_AnyAttribute() {
		return (EAttribute) getAggregationLevelVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotation() {
		if (annotationEClass == null) {
			annotationEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(5);
		}
		return annotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnotation_Group() {
		return (EAttribute) getAnnotation().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotation_Entity() {
		return (EReference) getAnnotation().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotation_Date() {
		return (EReference) getAnnotation().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotation_Description() {
		return (EReference) getAnnotation().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnotation_Any() {
		return (EAttribute) getAnnotation().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnotation_AnyAttribute() {
		return (EAttribute) getAnnotation().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCatalog() {
		if (catalogEClass == null) {
			catalogEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(6);
		}
		return catalogEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCatalog_Value() {
		return (EAttribute) getCatalog().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCatalog_UniqueElementName() {
		return (EAttribute) getCatalog().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCatalog_AnyAttribute() {
		return (EAttribute) getCatalog().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getClassification() {
		if (classificationEClass == null) {
			classificationEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(8);
		}
		return classificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getClassification_Group() {
		return (EAttribute) getClassification().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClassification_Purpose() {
		return (EReference) getClassification().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClassification_TaxonPath() {
		return (EReference) getClassification().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClassification_Description() {
		return (EReference) getClassification().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClassification_Keyword() {
		return (EReference) getClassification().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getClassification_Any() {
		return (EAttribute) getClassification().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getClassification_AnyAttribute() {
		return (EAttribute) getClassification().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContext() {
		if (contextEClass == null) {
			contextEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(9);
		}
		return contextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextValue() {
		if (contextValueEClass == null) {
			contextValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(10);
		}
		return contextValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextValue_Value() {
		return (EAttribute) getContextValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextValue_UniqueElementName() {
		return (EAttribute) getContextValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextValue_AnyAttribute() {
		return (EAttribute) getContextValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextVocab() {
		if (contextVocabEClass == null) {
			contextVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(13);
		}
		return contextVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextVocab_Group() {
		return (EAttribute) getContextVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContextVocab_Source() {
		return (EReference) getContextVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContextVocab_Value() {
		return (EReference) getContextVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextVocab_Any() {
		return (EAttribute) getContextVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextVocab_AnyAttribute() {
		return (EAttribute) getContextVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContribute() {
		if (contributeEClass == null) {
			contributeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(14);
		}
		return contributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContribute_Group() {
		return (EAttribute) getContribute().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContribute_Role() {
		return (EReference) getContribute().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContribute_Entity() {
		return (EReference) getContribute().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContribute_Date() {
		return (EReference) getContribute().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContribute_Any() {
		return (EAttribute) getContribute().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContribute_AnyAttribute() {
		return (EAttribute) getContribute().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContributeMeta() {
		if (contributeMetaEClass == null) {
			contributeMetaEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(15);
		}
		return contributeMetaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContributeMeta_Group() {
		return (EAttribute) getContributeMeta().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContributeMeta_Role() {
		return (EReference) getContributeMeta().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContributeMeta_Entity() {
		return (EReference) getContributeMeta().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContributeMeta_Date() {
		return (EReference) getContributeMeta().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContributeMeta_Any() {
		return (EAttribute) getContributeMeta().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContributeMeta_AnyAttribute() {
		return (EAttribute) getContributeMeta().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCopyrightAndOtherRestrictions() {
		if (copyrightAndOtherRestrictionsEClass == null) {
			copyrightAndOtherRestrictionsEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(16);
		}
		return copyrightAndOtherRestrictionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictions_UniqueElementName() {
		return (EAttribute) getCopyrightAndOtherRestrictions().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCopyrightAndOtherRestrictionsValue() {
		if (copyrightAndOtherRestrictionsValueEClass == null) {
			copyrightAndOtherRestrictionsValueEClass = (EClass) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(17);
		}
		return copyrightAndOtherRestrictionsValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsValue_Value() {
		return (EAttribute) getCopyrightAndOtherRestrictionsValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsValue_UniqueElementName() {
		return (EAttribute) getCopyrightAndOtherRestrictionsValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsValue_AnyAttribute() {
		return (EAttribute) getCopyrightAndOtherRestrictionsValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCopyrightAndOtherRestrictionsVocab() {
		if (copyrightAndOtherRestrictionsVocabEClass == null) {
			copyrightAndOtherRestrictionsVocabEClass = (EClass) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(20);
		}
		return copyrightAndOtherRestrictionsVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsVocab_Group() {
		return (EAttribute) getCopyrightAndOtherRestrictionsVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCopyrightAndOtherRestrictionsVocab_Source() {
		return (EReference) getCopyrightAndOtherRestrictionsVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCopyrightAndOtherRestrictionsVocab_Value() {
		return (EReference) getCopyrightAndOtherRestrictionsVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsVocab_Any() {
		return (EAttribute) getCopyrightAndOtherRestrictionsVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCopyrightAndOtherRestrictionsVocab_AnyAttribute() {
		return (EAttribute) getCopyrightAndOtherRestrictionsVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCost() {
		if (costEClass == null) {
			costEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(21);
		}
		return costEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCost_UniqueElementName() {
		return (EAttribute) getCost().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCostValue() {
		if (costValueEClass == null) {
			costValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(22);
		}
		return costValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostValue_Value() {
		return (EAttribute) getCostValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostValue_UniqueElementName() {
		return (EAttribute) getCostValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostValue_AnyAttribute() {
		return (EAttribute) getCostValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCostVocab() {
		if (costVocabEClass == null) {
			costVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(25);
		}
		return costVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostVocab_Group() {
		return (EAttribute) getCostVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCostVocab_Source() {
		return (EReference) getCostVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCostVocab_Value() {
		return (EReference) getCostVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostVocab_Any() {
		return (EAttribute) getCostVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCostVocab_AnyAttribute() {
		return (EAttribute) getCostVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCoverage() {
		if (coverageEClass == null) {
			coverageEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(26);
		}
		return coverageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDate() {
		if (dateEClass == null) {
			dateEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(27);
		}
		return dateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDate_UniqueElementName() {
		return (EAttribute) getDate().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDate_AnyAttribute() {
		return (EAttribute) getDate().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateTime() {
		if (dateTimeEClass == null) {
			dateTimeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(28);
		}
		return dateTimeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTime_Group() {
		return (EAttribute) getDateTime().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDateTime_DateTime() {
		return (EReference) getDateTime().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDateTime_Description() {
		return (EReference) getDateTime().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTime_Any() {
		return (EAttribute) getDateTime().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateTimeValue() {
		if (dateTimeValueEClass == null) {
			dateTimeValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(30);
		}
		return dateTimeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTimeValue_Value() {
		return (EAttribute) getDateTimeValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTimeValue_UniqueElementName() {
		return (EAttribute) getDateTimeValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTimeValue_AnyAttribute() {
		return (EAttribute) getDateTimeValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDescription() {
		if (descriptionEClass == null) {
			descriptionEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(31);
		}
		return descriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDescription_UniqueElementName() {
		return (EAttribute) getDescription().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDifficulty() {
		if (difficultyEClass == null) {
			difficultyEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(32);
		}
		return difficultyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficulty_UniqueElementName() {
		return (EAttribute) getDifficulty().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDifficultyValue() {
		if (difficultyValueEClass == null) {
			difficultyValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(33);
		}
		return difficultyValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyValue_Value() {
		return (EAttribute) getDifficultyValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyValue_UniqueElementName() {
		return (EAttribute) getDifficultyValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyValue_AnyAttribute() {
		return (EAttribute) getDifficultyValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDifficultyVocab() {
		if (difficultyVocabEClass == null) {
			difficultyVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(36);
		}
		return difficultyVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyVocab_Group() {
		return (EAttribute) getDifficultyVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDifficultyVocab_Source() {
		return (EReference) getDifficultyVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDifficultyVocab_Value() {
		return (EReference) getDifficultyVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyVocab_Any() {
		return (EAttribute) getDifficultyVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficultyVocab_AnyAttribute() {
		return (EAttribute) getDifficultyVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDuration() {
		if (durationEClass == null) {
			durationEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(37);
		}
		return durationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDuration_UniqueElementName() {
		return (EAttribute) getDuration().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDuration_AnyAttribute() {
		return (EAttribute) getDuration().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDurationBase() {
		if (durationBaseEClass == null) {
			durationBaseEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(38);
		}
		return durationBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationBase_Group() {
		return (EAttribute) getDurationBase().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDurationBase_Duration() {
		return (EReference) getDurationBase().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDurationBase_Description() {
		return (EReference) getDurationBase().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationBase_Any() {
		return (EAttribute) getDurationBase().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDurationValue() {
		if (durationValueEClass == null) {
			durationValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(40);
		}
		return durationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationValue_Value() {
		return (EAttribute) getDurationValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationValue_UniqueElementName() {
		return (EAttribute) getDurationValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationValue_AnyAttribute() {
		return (EAttribute) getDurationValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEducational() {
		if (educationalEClass == null) {
			educationalEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(41);
		}
		return educationalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEducational_Group() {
		return (EAttribute) getEducational().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_InteractivityType() {
		return (EReference) getEducational().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_LearningResourceType() {
		return (EReference) getEducational().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_InteractivityLevel() {
		return (EReference) getEducational().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_SemanticDensity() {
		return (EReference) getEducational().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_IntendedEndUserRole() {
		return (EReference) getEducational().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_Context() {
		return (EReference) getEducational().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_TypicalAgeRange() {
		return (EReference) getEducational().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_Difficulty() {
		return (EReference) getEducational().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_TypicalLearningTime() {
		return (EReference) getEducational().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_Description() {
		return (EReference) getEducational().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEducational_Language() {
		return (EReference) getEducational().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEducational_Any() {
		return (EAttribute) getEducational().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEducational_AnyAttribute() {
		return (EAttribute) getEducational().getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEntity() {
		if (entityEClass == null) {
			entityEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(42);
		}
		return entityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEntity_UniqueElementName() {
		return (EAttribute) getEntity().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEntry() {
		if (entryEClass == null) {
			entryEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(43);
		}
		return entryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEntry_Value() {
		return (EAttribute) getEntry().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEntry_UniqueElementName() {
		return (EAttribute) getEntry().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEntry_AnyAttribute() {
		return (EAttribute) getEntry().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEntryTaxon() {
		if (entryTaxonEClass == null) {
			entryTaxonEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(44);
		}
		return entryTaxonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEntryTaxon_UniqueElementName() {
		return (EAttribute) getEntryTaxon().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFormat() {
		if (formatEClass == null) {
			formatEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(45);
		}
		return formatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFormat_Value() {
		return (EAttribute) getFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFormat_AnyAttribute() {
		return (EAttribute) getFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeneral() {
		if (generalEClass == null) {
			generalEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(46);
		}
		return generalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeneral_Group() {
		return (EAttribute) getGeneral().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Identifier() {
		return (EReference) getGeneral().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Title() {
		return (EReference) getGeneral().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Language() {
		return (EReference) getGeneral().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Description() {
		return (EReference) getGeneral().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Keyword() {
		return (EReference) getGeneral().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Coverage() {
		return (EReference) getGeneral().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_Structure() {
		return (EReference) getGeneral().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeneral_AggregationLevel() {
		return (EReference) getGeneral().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeneral_Any() {
		return (EAttribute) getGeneral().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeneral_UniqueElementName() {
		return (EAttribute) getGeneral().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeneral_AnyAttribute() {
		return (EAttribute) getGeneral().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getId() {
		if (idEClass == null) {
			idEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(47);
		}
		return idEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getId_Value() {
		return (EAttribute) getId().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getId_UniqueElementName() {
		return (EAttribute) getId().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getId_AnyAttribute() {
		return (EAttribute) getId().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdentifier() {
		if (identifierEClass == null) {
			identifierEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(48);
		}
		return identifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentifier_Group() {
		return (EAttribute) getIdentifier().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIdentifier_Catalog() {
		return (EReference) getIdentifier().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIdentifier_Entry() {
		return (EReference) getIdentifier().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentifier_Any() {
		return (EAttribute) getIdentifier().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentifier_AnyAttribute() {
		return (EAttribute) getIdentifier().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstallationRemarks() {
		if (installationRemarksEClass == null) {
			installationRemarksEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(49);
		}
		return installationRemarksEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInstallationRemarks_UniqueElementName() {
		return (EAttribute) getInstallationRemarks().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntendedEndUserRole() {
		if (intendedEndUserRoleEClass == null) {
			intendedEndUserRoleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(50);
		}
		return intendedEndUserRoleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntendedEndUserRoleValue() {
		if (intendedEndUserRoleValueEClass == null) {
			intendedEndUserRoleValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(51);
		}
		return intendedEndUserRoleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleValue_Value() {
		return (EAttribute) getIntendedEndUserRoleValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleValue_UniqueElementName() {
		return (EAttribute) getIntendedEndUserRoleValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleValue_AnyAttribute() {
		return (EAttribute) getIntendedEndUserRoleValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntendedEndUserRoleVocab() {
		if (intendedEndUserRoleVocabEClass == null) {
			intendedEndUserRoleVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(53);
		}
		return intendedEndUserRoleVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleVocab_Group() {
		return (EAttribute) getIntendedEndUserRoleVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIntendedEndUserRoleVocab_Source() {
		return (EReference) getIntendedEndUserRoleVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIntendedEndUserRoleVocab_Value() {
		return (EReference) getIntendedEndUserRoleVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleVocab_Any() {
		return (EAttribute) getIntendedEndUserRoleVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIntendedEndUserRoleVocab_AnyAttribute() {
		return (EAttribute) getIntendedEndUserRoleVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityLevel() {
		if (interactivityLevelEClass == null) {
			interactivityLevelEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(54);
		}
		return interactivityLevelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevel_UniqueElementName() {
		return (EAttribute) getInteractivityLevel().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityLevelValue() {
		if (interactivityLevelValueEClass == null) {
			interactivityLevelValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(55);
		}
		return interactivityLevelValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelValue_Value() {
		return (EAttribute) getInteractivityLevelValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelValue_UniqueElementName() {
		return (EAttribute) getInteractivityLevelValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelValue_AnyAttribute() {
		return (EAttribute) getInteractivityLevelValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityLevelVocab() {
		if (interactivityLevelVocabEClass == null) {
			interactivityLevelVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(58);
		}
		return interactivityLevelVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelVocab_Group() {
		return (EAttribute) getInteractivityLevelVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInteractivityLevelVocab_Source() {
		return (EReference) getInteractivityLevelVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInteractivityLevelVocab_Value() {
		return (EReference) getInteractivityLevelVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelVocab_Any() {
		return (EAttribute) getInteractivityLevelVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityLevelVocab_AnyAttribute() {
		return (EAttribute) getInteractivityLevelVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityType() {
		if (interactivityTypeEClass == null) {
			interactivityTypeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(59);
		}
		return interactivityTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityType_UniqueElementName() {
		return (EAttribute) getInteractivityType().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKeyword() {
		if (keywordEClass == null) {
			keywordEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(60);
		}
		return keywordEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKeyword_Id() {
		return (EAttribute) getKeyword().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKeyword_Source() {
		return (EAttribute) getKeyword().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKindValue() {
		if (kindValueEClass == null) {
			kindValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(61);
		}
		return kindValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindValue_Value() {
		return (EAttribute) getKindValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindValue_UniqueElementName() {
		return (EAttribute) getKindValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindValue_AnyAttribute() {
		return (EAttribute) getKindValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLangString() {
		if (langStringEClass == null) {
			langStringEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(62);
		}
		return langStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLangString_Value() {
		return (EAttribute) getLangString().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLangString_Language() {
		return (EAttribute) getLangString().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLangString_AnyAttribute() {
		return (EAttribute) getLangString().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLanguageString() {
		if (languageStringEClass == null) {
			languageStringEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(63);
		}
		return languageStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguageString_Group() {
		return (EAttribute) getLanguageString().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLanguageString_String() {
		return (EReference) getLanguageString().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguageString_Any() {
		return (EAttribute) getLanguageString().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguageString_AnyAttribute() {
		return (EAttribute) getLanguageString().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentRoot() {
		if (documentRootEClass == null) {
			documentRootEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(64);
		}
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute) getDocumentRoot().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference) getDocumentRoot().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference) getDocumentRoot().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Lom() {
		return (EReference) getDocumentRoot().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityTypeValue() {
		if (interactivityTypeValueEClass == null) {
			interactivityTypeValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(66);
		}
		return interactivityTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeValue_Value() {
		return (EAttribute) getInteractivityTypeValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeValue_UniqueElementName() {
		return (EAttribute) getInteractivityTypeValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeValue_AnyAttribute() {
		return (EAttribute) getInteractivityTypeValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteractivityTypeVocab() {
		if (interactivityTypeVocabEClass == null) {
			interactivityTypeVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(69);
		}
		return interactivityTypeVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeVocab_Group() {
		return (EAttribute) getInteractivityTypeVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInteractivityTypeVocab_Source() {
		return (EReference) getInteractivityTypeVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInteractivityTypeVocab_Value() {
		return (EReference) getInteractivityTypeVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeVocab_Any() {
		return (EAttribute) getInteractivityTypeVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteractivityTypeVocab_AnyAttribute() {
		return (EAttribute) getInteractivityTypeVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKind() {
		if (kindEClass == null) {
			kindEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(70);
		}
		return kindEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKind_UniqueElementName() {
		return (EAttribute) getKind().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKindVocab() {
		if (kindVocabEClass == null) {
			kindVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(73);
		}
		return kindVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindVocab_Group() {
		return (EAttribute) getKindVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKindVocab_Source() {
		return (EReference) getKindVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKindVocab_Value() {
		return (EReference) getKindVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindVocab_Any() {
		return (EAttribute) getKindVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKindVocab_AnyAttribute() {
		return (EAttribute) getKindVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLanguage() {
		if (languageEClass == null) {
			languageEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(74);
		}
		return languageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguage_UniqueElementName() {
		return (EAttribute) getLanguage().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLanguageId() {
		if (languageIdEClass == null) {
			languageIdEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(75);
		}
		return languageIdEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguageId_Value() {
		return (EAttribute) getLanguageId().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLanguageId_AnyAttribute() {
		return (EAttribute) getLanguageId().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLearningResourceType() {
		if (learningResourceTypeEClass == null) {
			learningResourceTypeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(76);
		}
		return learningResourceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLearningResourceTypeValue() {
		if (learningResourceTypeValueEClass == null) {
			learningResourceTypeValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(77);
		}
		return learningResourceTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeValue_Value() {
		return (EAttribute) getLearningResourceTypeValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeValue_UniqueElementName() {
		return (EAttribute) getLearningResourceTypeValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeValue_AnyAttribute() {
		return (EAttribute) getLearningResourceTypeValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLearningResourceTypeVocab() {
		if (learningResourceTypeVocabEClass == null) {
			learningResourceTypeVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(80);
		}
		return learningResourceTypeVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeVocab_Group() {
		return (EAttribute) getLearningResourceTypeVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLearningResourceTypeVocab_Source() {
		return (EReference) getLearningResourceTypeVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLearningResourceTypeVocab_Value() {
		return (EReference) getLearningResourceTypeVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeVocab_Any() {
		return (EAttribute) getLearningResourceTypeVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLearningResourceTypeVocab_AnyAttribute() {
		return (EAttribute) getLearningResourceTypeVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLifeCycle() {
		if (lifeCycleEClass == null) {
			lifeCycleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(81);
		}
		return lifeCycleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLifeCycle_Group() {
		return (EAttribute) getLifeCycle().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLifeCycle_Version() {
		return (EReference) getLifeCycle().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLifeCycle_Status() {
		return (EReference) getLifeCycle().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLifeCycle_Contribute() {
		return (EReference) getLifeCycle().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLifeCycle_Any() {
		return (EAttribute) getLifeCycle().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLifeCycle_UniqueElementName() {
		return (EAttribute) getLifeCycle().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLifeCycle_AnyAttribute() {
		return (EAttribute) getLifeCycle().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocation() {
		if (locationEClass == null) {
			locationEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(82);
		}
		return locationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Value() {
		return (EAttribute) getLocation().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_AnyAttribute() {
		return (EAttribute) getLocation().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLom() {
		if (lomEClass == null) {
			lomEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(83);
		}
		return lomEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLom_Group() {
		return (EAttribute) getLom().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_General() {
		return (EReference) getLom().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_LifeCycle() {
		return (EReference) getLom().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_MetaMetadata() {
		return (EReference) getLom().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Technical() {
		return (EReference) getLom().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Educational() {
		return (EReference) getLom().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Rights() {
		return (EReference) getLom().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Relation() {
		return (EReference) getLom().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Annotation() {
		return (EReference) getLom().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_Classification() {
		return (EReference) getLom().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_HealthcareMetadata() {
		return (EReference) getLom().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLom_CustomElements() {
		return (EReference) getLom().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLom_AnyAttribute() {
		return (EAttribute) getLom().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMaximumVersion() {
		if (maximumVersionEClass == null) {
			maximumVersionEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(84);
		}
		return maximumVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaximumVersion_Value() {
		return (EAttribute) getMaximumVersion().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaximumVersion_UniqueElementName() {
		return (EAttribute) getMaximumVersion().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaximumVersion_AnyAttribute() {
		return (EAttribute) getMaximumVersion().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetadataSchema() {
		if (metadataSchemaEClass == null) {
			metadataSchemaEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(85);
		}
		return metadataSchemaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetadataSchema_Value() {
		return (EAttribute) getMetadataSchema().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetadataSchema_AnyAttribute() {
		return (EAttribute) getMetadataSchema().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetaMetadata() {
		if (metaMetadataEClass == null) {
			metaMetadataEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(86);
		}
		return metaMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetaMetadata_Group() {
		return (EAttribute) getMetaMetadata().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaMetadata_Identifier() {
		return (EReference) getMetaMetadata().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaMetadata_Contribute() {
		return (EReference) getMetaMetadata().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaMetadata_MetadataSchema() {
		return (EReference) getMetaMetadata().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaMetadata_Language() {
		return (EReference) getMetaMetadata().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetaMetadata_Any() {
		return (EAttribute) getMetaMetadata().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetaMetadata_UniqueElementName() {
		return (EAttribute) getMetaMetadata().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetaMetadata_AnyAttribute() {
		return (EAttribute) getMetaMetadata().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMinimumVersion() {
		if (minimumVersionEClass == null) {
			minimumVersionEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(88);
		}
		return minimumVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMinimumVersion_Value() {
		return (EAttribute) getMinimumVersion().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMinimumVersion_UniqueElementName() {
		return (EAttribute) getMinimumVersion().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMinimumVersion_AnyAttribute() {
		return (EAttribute) getMinimumVersion().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getName_() {
		if (nameEClass == null) {
			nameEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(89);
		}
		return nameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getName_UniqueElementName() {
		return (EAttribute) getName_().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNameValue() {
		if (nameValueEClass == null) {
			nameValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(90);
		}
		return nameValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameValue_Value() {
		return (EAttribute) getNameValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameValue_UniqueElementName() {
		return (EAttribute) getNameValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameValue_AnyAttribute() {
		return (EAttribute) getNameValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNameVocab() {
		if (nameVocabEClass == null) {
			nameVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(93);
		}
		return nameVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameVocab_Group() {
		return (EAttribute) getNameVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNameVocab_Source() {
		return (EReference) getNameVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNameVocab_Value() {
		return (EReference) getNameVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameVocab_Any() {
		return (EAttribute) getNameVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameVocab_AnyAttribute() {
		return (EAttribute) getNameVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOrComposite() {
		if (orCompositeEClass == null) {
			orCompositeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(94);
		}
		return orCompositeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOrComposite_Group() {
		return (EAttribute) getOrComposite().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOrComposite_Type() {
		return (EReference) getOrComposite().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOrComposite_Name() {
		return (EReference) getOrComposite().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOrComposite_MinimumVersion() {
		return (EReference) getOrComposite().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOrComposite_MaximumVersion() {
		return (EReference) getOrComposite().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOrComposite_Any() {
		return (EAttribute) getOrComposite().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOrComposite_AnyAttribute() {
		return (EAttribute) getOrComposite().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOtherPlatformRequirements() {
		if (otherPlatformRequirementsEClass == null) {
			otherPlatformRequirementsEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(95);
		}
		return otherPlatformRequirementsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPurpose() {
		if (purposeEClass == null) {
			purposeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(96);
		}
		return purposeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurpose_UniqueElementName() {
		return (EAttribute) getPurpose().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPurposeValue() {
		if (purposeValueEClass == null) {
			purposeValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(97);
		}
		return purposeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeValue_Value() {
		return (EAttribute) getPurposeValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeValue_UniqueElementName() {
		return (EAttribute) getPurposeValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeValue_AnyAttribute() {
		return (EAttribute) getPurposeValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPurposeVocab() {
		if (purposeVocabEClass == null) {
			purposeVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(100);
		}
		return purposeVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeVocab_Group() {
		return (EAttribute) getPurposeVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPurposeVocab_Source() {
		return (EReference) getPurposeVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPurposeVocab_Value() {
		return (EReference) getPurposeVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeVocab_Any() {
		return (EAttribute) getPurposeVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPurposeVocab_AnyAttribute() {
		return (EAttribute) getPurposeVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRelation() {
		if (relationEClass == null) {
			relationEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(101);
		}
		return relationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelation_Group() {
		return (EAttribute) getRelation().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelation_Kind() {
		return (EReference) getRelation().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelation_Resource() {
		return (EReference) getRelation().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelation_Any() {
		return (EAttribute) getRelation().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelation_AnyAttribute() {
		return (EAttribute) getRelation().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRequirement() {
		if (requirementEClass == null) {
			requirementEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(102);
		}
		return requirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRequirement_Group() {
		return (EAttribute) getRequirement().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRequirement_OrComposite() {
		return (EReference) getRequirement().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRequirement_Any() {
		return (EAttribute) getRequirement().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRequirement_AnyAttribute() {
		return (EAttribute) getRequirement().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResource() {
		if (resourceEClass == null) {
			resourceEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(103);
		}
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_Group() {
		return (EAttribute) getResource().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResource_Identifier() {
		return (EReference) getResource().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResource_Description() {
		return (EReference) getResource().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_Any() {
		return (EAttribute) getResource().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_UniqueElementName() {
		return (EAttribute) getResource().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_AnyAttribute() {
		return (EAttribute) getResource().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRights() {
		if (rightsEClass == null) {
			rightsEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(104);
		}
		return rightsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRights_Group() {
		return (EAttribute) getRights().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRights_Cost() {
		return (EReference) getRights().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRights_CopyrightAndOtherRestrictions() {
		return (EReference) getRights().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRights_Description() {
		return (EReference) getRights().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRights_Any() {
		return (EAttribute) getRights().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRights_UniqueElementName() {
		return (EAttribute) getRights().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRights_AnyAttribute() {
		return (EAttribute) getRights().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRole() {
		if (roleEClass == null) {
			roleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(105);
		}
		return roleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRole_UniqueElementName() {
		return (EAttribute) getRole().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRoleMeta() {
		if (roleMetaEClass == null) {
			roleMetaEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(106);
		}
		return roleMetaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMeta_UniqueElementName() {
		return (EAttribute) getRoleMeta().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRoleMetaValue() {
		if (roleMetaValueEClass == null) {
			roleMetaValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(107);
		}
		return roleMetaValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaValue_Value() {
		return (EAttribute) getRoleMetaValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaValue_UniqueElementName() {
		return (EAttribute) getRoleMetaValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaValue_AnyAttribute() {
		return (EAttribute) getRoleMetaValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRoleMetaVocab() {
		if (roleMetaVocabEClass == null) {
			roleMetaVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(110);
		}
		return roleMetaVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaVocab_Group() {
		return (EAttribute) getRoleMetaVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRoleMetaVocab_Source() {
		return (EReference) getRoleMetaVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRoleMetaVocab_Value() {
		return (EReference) getRoleMetaVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaVocab_Any() {
		return (EAttribute) getRoleMetaVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleMetaVocab_AnyAttribute() {
		return (EAttribute) getRoleMetaVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRoleValue() {
		if (roleValueEClass == null) {
			roleValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(111);
		}
		return roleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleValue_Value() {
		return (EAttribute) getRoleValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleValue_UniqueElementName() {
		return (EAttribute) getRoleValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleValue_AnyAttribute() {
		return (EAttribute) getRoleValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRoleVocab() {
		if (roleVocabEClass == null) {
			roleVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(114);
		}
		return roleVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleVocab_Group() {
		return (EAttribute) getRoleVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRoleVocab_Source() {
		return (EReference) getRoleVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRoleVocab_Value() {
		return (EReference) getRoleVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleVocab_Any() {
		return (EAttribute) getRoleVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRoleVocab_AnyAttribute() {
		return (EAttribute) getRoleVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSemanticDensity() {
		if (semanticDensityEClass == null) {
			semanticDensityEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(115);
		}
		return semanticDensityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensity_UniqueElementName() {
		return (EAttribute) getSemanticDensity().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSemanticDensityValue() {
		if (semanticDensityValueEClass == null) {
			semanticDensityValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(116);
		}
		return semanticDensityValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityValue_Value() {
		return (EAttribute) getSemanticDensityValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityValue_UniqueElementName() {
		return (EAttribute) getSemanticDensityValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityValue_AnyAttribute() {
		return (EAttribute) getSemanticDensityValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSemanticDensityVocab() {
		if (semanticDensityVocabEClass == null) {
			semanticDensityVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(119);
		}
		return semanticDensityVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityVocab_Group() {
		return (EAttribute) getSemanticDensityVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSemanticDensityVocab_Source() {
		return (EReference) getSemanticDensityVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSemanticDensityVocab_Value() {
		return (EReference) getSemanticDensityVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityVocab_Any() {
		return (EAttribute) getSemanticDensityVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSemanticDensityVocab_AnyAttribute() {
		return (EAttribute) getSemanticDensityVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSize() {
		if (sizeEClass == null) {
			sizeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(120);
		}
		return sizeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSize_Value() {
		return (EAttribute) getSize().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSize_UniqueElementName() {
		return (EAttribute) getSize().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSize_AnyAttribute() {
		return (EAttribute) getSize().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSource() {
		if (sourceEClass == null) {
			sourceEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(122);
		}
		return sourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSource_UniqueElementName() {
		return (EAttribute) getSource().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSourceValue() {
		if (sourceValueEClass == null) {
			sourceValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(123);
		}
		return sourceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSourceValue_Value() {
		return (EAttribute) getSourceValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSourceValue_UniqueElementName() {
		return (EAttribute) getSourceValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSourceValue_AnyAttribute() {
		return (EAttribute) getSourceValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStatus() {
		if (statusEClass == null) {
			statusEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(126);
		}
		return statusEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatus_UniqueElementName() {
		return (EAttribute) getStatus().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStatusValue() {
		if (statusValueEClass == null) {
			statusValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(127);
		}
		return statusValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusValue_Value() {
		return (EAttribute) getStatusValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusValue_UniqueElementName() {
		return (EAttribute) getStatusValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusValue_AnyAttribute() {
		return (EAttribute) getStatusValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStatusVocab() {
		if (statusVocabEClass == null) {
			statusVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(130);
		}
		return statusVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusVocab_Group() {
		return (EAttribute) getStatusVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStatusVocab_Source() {
		return (EReference) getStatusVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStatusVocab_Value() {
		return (EReference) getStatusVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusVocab_Any() {
		return (EAttribute) getStatusVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStatusVocab_AnyAttribute() {
		return (EAttribute) getStatusVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructure() {
		if (structureEClass == null) {
			structureEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(131);
		}
		return structureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructure_UniqueElementName() {
		return (EAttribute) getStructure().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructureValue() {
		if (structureValueEClass == null) {
			structureValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(132);
		}
		return structureValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureValue_Value() {
		return (EAttribute) getStructureValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureValue_UniqueElementName() {
		return (EAttribute) getStructureValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureValue_AnyAttribute() {
		return (EAttribute) getStructureValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructureVocab() {
		if (structureVocabEClass == null) {
			structureVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(135);
		}
		return structureVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureVocab_Group() {
		return (EAttribute) getStructureVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructureVocab_Source() {
		return (EReference) getStructureVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructureVocab_Value() {
		return (EReference) getStructureVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureVocab_Any() {
		return (EAttribute) getStructureVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStructureVocab_AnyAttribute() {
		return (EAttribute) getStructureVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTaxon() {
		if (taxonEClass == null) {
			taxonEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(136);
		}
		return taxonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxon_Group() {
		return (EAttribute) getTaxon().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTaxon_Id() {
		return (EReference) getTaxon().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTaxon_Entry() {
		return (EReference) getTaxon().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxon_Any() {
		return (EAttribute) getTaxon().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxon_AnyAttribute() {
		return (EAttribute) getTaxon().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTaxonPath() {
		if (taxonPathEClass == null) {
			taxonPathEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(137);
		}
		return taxonPathEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxonPath_Group() {
		return (EAttribute) getTaxonPath().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTaxonPath_Source() {
		return (EReference) getTaxonPath().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTaxonPath_Taxon() {
		return (EReference) getTaxonPath().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxonPath_Any() {
		return (EAttribute) getTaxonPath().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaxonPath_AnyAttribute() {
		return (EAttribute) getTaxonPath().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTechnical() {
		if (technicalEClass == null) {
			technicalEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(138);
		}
		return technicalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTechnical_Group() {
		return (EAttribute) getTechnical().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_Format() {
		return (EReference) getTechnical().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_Size() {
		return (EReference) getTechnical().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_Location() {
		return (EReference) getTechnical().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_Requirement() {
		return (EReference) getTechnical().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_InstallationRemarks() {
		return (EReference) getTechnical().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_OtherPlatformRequirements() {
		return (EReference) getTechnical().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTechnical_Duration() {
		return (EReference) getTechnical().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTechnical_Any() {
		return (EAttribute) getTechnical().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTechnical_UniqueElementName() {
		return (EAttribute) getTechnical().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTechnical_AnyAttribute() {
		return (EAttribute) getTechnical().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTitle() {
		if (titleEClass == null) {
			titleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(139);
		}
		return titleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTitle_UniqueElementName() {
		return (EAttribute) getTitle().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getType() {
		if (typeEClass == null) {
			typeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(140);
		}
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getType_UniqueElementName() {
		return (EAttribute) getType().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeValue() {
		if (typeValueEClass == null) {
			typeValueEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(141);
		}
		return typeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeValue_Value() {
		return (EAttribute) getTypeValue().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeValue_UniqueElementName() {
		return (EAttribute) getTypeValue().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeValue_AnyAttribute() {
		return (EAttribute) getTypeValue().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeVocab() {
		if (typeVocabEClass == null) {
			typeVocabEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(144);
		}
		return typeVocabEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeVocab_Group() {
		return (EAttribute) getTypeVocab().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeVocab_Source() {
		return (EReference) getTypeVocab().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeVocab_Value() {
		return (EReference) getTypeVocab().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeVocab_Any() {
		return (EAttribute) getTypeVocab().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeVocab_AnyAttribute() {
		return (EAttribute) getTypeVocab().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypicalAgeRange() {
		if (typicalAgeRangeEClass == null) {
			typicalAgeRangeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(145);
		}
		return typicalAgeRangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypicalLearningTime() {
		if (typicalLearningTimeEClass == null) {
			typicalLearningTimeEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(146);
		}
		return typicalLearningTimeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypicalLearningTime_UniqueElementName() {
		return (EAttribute) getTypicalLearningTime().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypicalLearningTime_AnyAttribute() {
		return (EAttribute) getTypicalLearningTime().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVCard() {
		if (vCardEClass == null) {
			vCardEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(147);
		}
		return vCardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVCard_Value() {
		return (EAttribute) getVCard().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVCard_AnyAttribute() {
		return (EAttribute) getVCard().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersion() {
		if (versionEClass == null) {
			versionEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(148);
		}
		return versionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersion_UniqueElementName() {
		return (EAttribute) getVersion().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAggregationLevelValues() {
		if (aggregationLevelValuesEEnum == null) {
			aggregationLevelValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(2);
		}
		return aggregationLevelValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getContextValues() {
		if (contextValuesEEnum == null) {
			contextValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(11);
		}
		return contextValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getCopyrightAndOtherRestrictionsValues() {
		if (copyrightAndOtherRestrictionsValuesEEnum == null) {
			copyrightAndOtherRestrictionsValuesEEnum = (EEnum) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(18);
		}
		return copyrightAndOtherRestrictionsValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getCostValues() {
		if (costValuesEEnum == null) {
			costValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(23);
		}
		return costValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDifficultyValues() {
		if (difficultyValuesEEnum == null) {
			difficultyValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(34);
		}
		return difficultyValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIntendedEndUserRoleValues() {
		if (intendedEndUserRoleValuesEEnum == null) {
			intendedEndUserRoleValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(52);
		}
		return intendedEndUserRoleValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getInteractivityLevelValues() {
		if (interactivityLevelValuesEEnum == null) {
			interactivityLevelValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(56);
		}
		return interactivityLevelValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getInteractivityTypeValues() {
		if (interactivityTypeValuesEEnum == null) {
			interactivityTypeValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(67);
		}
		return interactivityTypeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getKindValues() {
		if (kindValuesEEnum == null) {
			kindValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(71);
		}
		return kindValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLearningResourceTypeValues() {
		if (learningResourceTypeValuesEEnum == null) {
			learningResourceTypeValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(78);
		}
		return learningResourceTypeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getNameValues() {
		if (nameValuesEEnum == null) {
			nameValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(91);
		}
		return nameValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPurposeValues() {
		if (purposeValuesEEnum == null) {
			purposeValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(98);
		}
		return purposeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRoleMetaValues() {
		if (roleMetaValuesEEnum == null) {
			roleMetaValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(108);
		}
		return roleMetaValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRoleValues() {
		if (roleValuesEEnum == null) {
			roleValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(112);
		}
		return roleValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSemanticDensityValues() {
		if (semanticDensityValuesEEnum == null) {
			semanticDensityValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(117);
		}
		return semanticDensityValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSourceValues() {
		if (sourceValuesEEnum == null) {
			sourceValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(124);
		}
		return sourceValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getStatusValues() {
		if (statusValuesEEnum == null) {
			statusValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(128);
		}
		return statusValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getStructureValues() {
		if (structureValuesEEnum == null) {
			structureValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(133);
		}
		return structureValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTypeValues() {
		if (typeValuesEEnum == null) {
			typeValuesEEnum = (EEnum) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(142);
		}
		return typeValuesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getAggregationLevelValuesObject() {
		if (aggregationLevelValuesObjectEDataType == null) {
			aggregationLevelValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(3);
		}
		return aggregationLevelValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCharacterString() {
		if (characterStringEDataType == null) {
			characterStringEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(7);
		}
		return characterStringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getContextValuesObject() {
		if (contextValuesObjectEDataType == null) {
			contextValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(12);
		}
		return contextValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCopyrightAndOtherRestrictionsValuesObject() {
		if (copyrightAndOtherRestrictionsValuesObjectEDataType == null) {
			copyrightAndOtherRestrictionsValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(19);
		}
		return copyrightAndOtherRestrictionsValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCostValuesObject() {
		if (costValuesObjectEDataType == null) {
			costValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(24);
		}
		return costValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDateTimeString() {
		if (dateTimeStringEDataType == null) {
			dateTimeStringEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(29);
		}
		return dateTimeStringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDifficultyValuesObject() {
		if (difficultyValuesObjectEDataType == null) {
			difficultyValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(35);
		}
		return difficultyValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDurationString() {
		if (durationStringEDataType == null) {
			durationStringEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(39);
		}
		return durationStringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getInteractivityLevelValuesObject() {
		if (interactivityLevelValuesObjectEDataType == null) {
			interactivityLevelValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(57);
		}
		return interactivityLevelValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIntendedEndUserRoleValuesObject() {
		if (intendedEndUserRoleValuesObjectEDataType == null) {
			intendedEndUserRoleValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(65);
		}
		return intendedEndUserRoleValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getInteractivityTypeValuesObject() {
		if (interactivityTypeValuesObjectEDataType == null) {
			interactivityTypeValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(68);
		}
		return interactivityTypeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getKindValuesObject() {
		if (kindValuesObjectEDataType == null) {
			kindValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(72);
		}
		return kindValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getLearningResourceTypeValuesObject() {
		if (learningResourceTypeValuesObjectEDataType == null) {
			learningResourceTypeValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(79);
		}
		return learningResourceTypeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getMimeType() {
		if (mimeTypeEDataType == null) {
			mimeTypeEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(87);
		}
		return mimeTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getNameValuesObject() {
		if (nameValuesObjectEDataType == null) {
			nameValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(92);
		}
		return nameValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPurposeValuesObject() {
		if (purposeValuesObjectEDataType == null) {
			purposeValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(99);
		}
		return purposeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRoleMetaValuesObject() {
		if (roleMetaValuesObjectEDataType == null) {
			roleMetaValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(109);
		}
		return roleMetaValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRoleValuesObject() {
		if (roleValuesObjectEDataType == null) {
			roleValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(113);
		}
		return roleValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSemanticDensityValuesObject() {
		if (semanticDensityValuesObjectEDataType == null) {
			semanticDensityValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE
					.getEPackage(LomPackage.eNS_URI).getEClassifiers().get(118);
		}
		return semanticDensityValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSizeBase() {
		if (sizeBaseEDataType == null) {
			sizeBaseEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI).getEClassifiers()
					.get(121);
		}
		return sizeBaseEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSourceValuesObject() {
		if (sourceValuesObjectEDataType == null) {
			sourceValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(125);
		}
		return sourceValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getStatusValuesObject() {
		if (statusValuesObjectEDataType == null) {
			statusValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(129);
		}
		return statusValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getStructureValuesObject() {
		if (structureValuesObjectEDataType == null) {
			structureValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(134);
		}
		return structureValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTypeValuesObject() {
		if (typeValuesObjectEDataType == null) {
			typeValuesObjectEDataType = (EDataType) EPackage.Registry.INSTANCE.getEPackage(LomPackage.eNS_URI)
					.getEClassifiers().get(143);
		}
		return typeValuesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LomFactory getLomFactory() {
		return (LomFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isLoaded = false;

	/**
	 * Loads the package and any sub-packages from their serialized form.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void loadPackage() {
		if (isLoaded)
			return;
		isLoaded = true;

		URL url = getClass().getResource(packageFilename);
		if (url == null) {
			throw new RuntimeException("Missing serialized package: " + packageFilename);
		}
		URI uri = URI.createURI(url.toString());
		Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
		try {
			resource.load(null);
		} catch (IOException exception) {
			throw new WrappedException(exception);
		}
		initializeFromLoadedEPackage(this, (EPackage) resource.getContents().get(0));
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed)
			return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName("edu.louisiana.caai.lom.model.lom." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //LomPackageImpl
