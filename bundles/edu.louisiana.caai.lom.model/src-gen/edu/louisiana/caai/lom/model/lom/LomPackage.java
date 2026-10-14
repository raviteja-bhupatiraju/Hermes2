/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *          This work is licensed under the Creative Commons Attribution-ShareAlike
 *          License.  To view a copy of this license, see the file license.txt,
 *          visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *          Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *       
 * 
 *          This component schema defintion defines global schema data type declarations
 *          for data types defined in the LOMv1.0 base schema.
 *       
 * 
 *         *****************************************************************************
 *         **                                                                         ** 
 *         ** NOTE THIS CHANGE HISTORY IS USED TO DOCUMENT CHANGES TO THE XML SCHEMA  **
 *         ** DEFINITION DURING THE BALLOTING PROCESS.  THIS CHANGE HISTORY WILL BE   **
 *         ** REMOVED ONCE THE FINAL VERSION OF THE STANDARD IS PUBLISHED.  A NEW     **
 *         ** CHANGE HISTORY WILL BE INCLUDED TO INDICATE THE INITIAL RELEASE THAT    **
 *         ** MAPS TO THE FINAL STANDARD.                                             **
 *         **                                                                         **
 *         *****************************************************************************
 *         **                            CHANGE HISTORY                               **
 *         *****************************************************************************
 *         ** 11/14/2003:  1)Updated xs:pattern for the DurationString.  The pattern  **
 *         **                did not permit zero values for each of the date and      **
 *         **                time components.                                         **
 *         **                                                                         **
 *         ** 03/15/2004:  1)Changed the restriction type for LanguageIdNone from     **
 *         **                xs:string to xs:token                                    **
 *         **                                                                         ** 
 *         ** 08/24/2004:  1) To correct a XML Binding to Marshalling issue, a change **
 *         **              is needed to resolve a naming convention for two complex   **
 *         **              types:                                                     **       
 *         **                1.1) Changed the name of the LangString complexType      **
 *         **                to LanguageString                                        **
 *         **                1.2) Change the  name of the langString completType to   **
 *         **                LangString                                               **
 *         **              2)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **                                                                         ** 
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Removed the LanguageIdOrNone and LanguageIdNone type     **
 *         **                declarations.  The types were not needed.  The           **
 *         **                LanguageId type was updated to support attribute         **
 *         **                extensions.  The LanguageId will now vaidate any         **
 *         **                language defined by 1484.12.3 and the token "none".      **
 *         **              3)Updated VCard to support attribute extensions.           **
 *         **              4)Updated LanguageString to reference the xs:language      **
 *         **                data type.                                               **       
 *         **              5)Updated DateTimeValue to support attribute extensions.   **
 *         **              6)Updated DurationValue to support attribute extensions.   **
 *         *****************************************************************************
 * 
 * 	24 May 2007 	NOTE: The Size and Duration types have been renamed SizeBase 
 * 			and DurationBase to prevent errors with web services 
 * 			development tools. This change does not affect the resulting 
 * 			XML document. The change applies only to MedBiquitous 
 * 			Healthcare LOM schema files.
 * 
 *       
 * 
 *        This work is licensed under the Creative Commons Attribution-ShareAlike
 *        License.  To view a copy of this license, see the file license.txt,
 *        visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *        Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *     
 * 
 *        This component schema defines the XML Schema content model groups customElements 
 *        and customAttributes to support validation of extension XML elements and attributes. 
 * 
 *        This component XSD should be used if extensions are to be supported in LOM 
 *        XML instances.
 *     
 * 
 *         *****************************************************************************
 *         **                                                                         ** 
 *         ** NOTE THIS CHANGE HISTORY IS USED TO DOCUMENT CHANGES TO THE XML SCHEMA  **
 *         ** DEFINITION DURING THE BALLOTING PROCESS.  THIS CHANGE HISTORY WILL BE   **
 *         ** REMOVED ONCE THE FINAL VERSION OF THE STANDARD IS PUBLISHED.  A NEW     **
 *         ** CHANGE HISTORY WILL BE INCLUDED TO INDICATE THE INITIAL RELEASE THAT    **
 *         ** MAPS TO THE FINAL STANDARD.                                             **
 *         **                                                                         **
 *         *****************************************************************************
 *         **                           CHANGE HISTORY                                **
 *         *****************************************************************************
 *         ** 03/15/2004:  1)Updated annoation describing purpose and design of the   **
 *         **                XSD.                                                     **
 *         **                                                                         **
 *         ** 08/24/2004:  1)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Added the customAttributes declaration to support        **
 *         **                attribute extensions to all LOM data elements.           **
 *         *****************************************************************************
 *     
 * 
 *          This work is licensed under the Creative Commons Attribution-ShareAlike
 *          License.  To view a copy of this license, see the file license.txt,
 *          visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *          Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *       
 * 
 *          This file represents a composite schema for validation of the following:
 *          1) The use of custom vocabulary source/value pairs and 
 *             LOMv1.0 base schema (i.e., 1484.12.1-2002) vocabulary source/value pairs
 *          2) Uniqueness constraints defined by LOMv1.0 base schema
 *          3) The use of XML element/attribute extensions to the LOMv1.0 base schema
 *       
 * 
 *         *****************************************************************************
 *         **                                                                         ** 
 *         ** NOTE THIS CHANGE HISTORY IS USED TO DOCUMENT CHANGES TO THE XML SCHEMA  **
 *         ** DEFINITION DURING THE BALLOTING PROCESS.  THIS CHANGE HISTORY WILL BE   **
 *         ** REMOVED ONCE THE FINAL VERSION OF THE STANDARD IS PUBLISHED.  A NEW     **
 *         ** CHANGE HISTORY WILL BE INCLUDED TO INDICATE THE INITIAL RELEASE THAT    **
 *         ** MAPS TO THE FINAL STANDARD.                                             **
 *         **                                                                         **
 *         *****************************************************************************
 *         **                            CHANGE HISTORY                               **
 *         *****************************************************************************
 *         ** 11/14/2003:  1)Updated comment describing vocab/strict.xsd.  Indicated  **
 *         **                that the strict.xsd is used to validate vocabularies     **
 *         **                defined in the LOM V1.0 Base Schema.                     **
 *         **              2)Moved included schema elementNames.xsd just before       **
 *         **                elementTypes.xsd.                                        **
 *         **              3)Moved the element declaration for the top-level lom      **
 *         **                metadata element to a separate file (rootElement.xsd)    **
 *         **                and included this file just after elementTypes.xsd.      **
 *         **              4)Moved the XML Schema import statements before the XML    **
 *         **                Schema include statements.                               **
 *         **              5)Moved the element group declaration named                **
 *         **                lom:customElements to a separate file (anyElement.xsd)   **
 *         **                and included this new file just before the XML Schema    **
 *         **                import statments.                                        **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         *****************************************************************************
 *       
 * 
 *          This work is licensed under the Creative Commons Attribution-ShareAlike
 *          License.  To view a copy of this license, see the file license.txt,
 *          visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *          Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *       
 * 
 *          This component schema definition provides the element group declaration and the
 *          attribute group declaration used for extension XML elements and attributes.
 *       
 * 
 *         *****************************************************************************
 *         **                                                                         ** 
 *         ** NOTE THIS CHANGE HISTORY IS USED TO DOCUMENT CHANGES TO THE XML SCHEMA  **
 *         ** DEFINITION DURING THE BALLOTING PROCESS.  THIS CHANGE HISTORY WILL BE   **
 *         ** REMOVED ONCE THE FINAL VERSION OF THE STANDARD IS PUBLISHED.  A NEW     **
 *         ** CHANGE HISTORY WILL BE INCLUDED TO INDICATE THE INITIAL RELEASE THAT    **
 *         ** MAPS TO THE FINAL STANDARD.                                             **
 *         **                                                                         **
 *         *****************************************************************************
 *         **                            CHANGE HISTORY                               **
 *         *****************************************************************************
 *         ** 08/24/2004:  1)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **                                                                         ** 
 *         ** 09/16/2004:  1)Added a attributeGroup customAttributes to support       **
 *         **                attribute extensions                                     **
 *         **                                                                         ** 
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **                                                                         ** 
 *         ** 10/11/2004   1)Removed attributeGroup customAttributes declaration.     **
 *         *****************************************************************************
 *       
 * 
 *        This work is licensed under the Creative Commons Attribution-ShareAlike
 *        License.  To view a copy of this license, see the file license.txt,
 *        visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *        Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *     
 * 
 *        This component schema defines attribute group declarations for LOM data
 *        elements defined in the LOMv1.0 base schema to support schema-based 
 *        validation of the uniqueness constraints with a LOM XML instance by 
 *        introducing the attribute "uniqueElementName" for each LOM data element
 *        that appears with a multiplicity of at most one.
 * 
 *        NOTE: For most applications, enforcing the uniqueness constraints using the 
 *        unique/strict.xsd component XSD is desirable. Although adding the attribute 
 *        "uniqueElementName" is undesirable, it is unlikely to cause problems.
 * 
 *        Duplicate declarations are included as comments for completeness.  These 
 *        declarations should remain commented out or they can be removed completely.
 *     
 * 
 *         *****************************************************************************
 *         **                                                                         ** 
 *         ** NOTE THIS CHANGE HISTORY IS USED TO DOCUMENT CHANGES TO THE XML SCHEMA  **
 *         ** DEFINITION DURING THE BALLOTING PROCESS.  THIS CHANGE HISTORY WILL BE   **
 *         ** REMOVED ONCE THE FINAL VERSION OF THE STANDARD IS PUBLISHED.  A NEW     **
 *         ** CHANGE HISTORY WILL BE INCLUDED TO INDICATE THE INITIAL RELEASE THAT    **
 *         ** MAPS TO THE FINAL STANDARD.                                             **
 *         **                                                                         **
 *         *****************************************************************************
 *         **                            CHANGE HISTORY                               **
 *         *****************************************************************************
 *         ** 03/15/2004:  1)Updated annoation describing purpose and design of the   **
 *         **                XSD.                                                     **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         *****************************************************************************
 *       
 * <!-- end-model-doc -->
 * @see edu.louisiana.caai.lom.model.lom.LomFactory
 * @model kind="package"
 * @generated
 */
public interface LomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "lom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://ltsc.ieee.org/xsd/LOM";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "lom";

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eCONTENT_TYPE = "edu.louisiana.caai.lom.hlom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LomPackage eINSTANCE = edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.AggregationLevelVocabImpl <em>Aggregation Level Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.AggregationLevelVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAggregationLevelVocab()
	 * @generated
	 */
	int AGGREGATION_LEVEL_VOCAB = 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Aggregation Level Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Aggregation Level Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.AggregationLevelImpl <em>Aggregation Level</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.AggregationLevelImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAggregationLevel()
	 * @generated
	 */
	int AGGREGATION_LEVEL = 0;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__GROUP = AGGREGATION_LEVEL_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__SOURCE = AGGREGATION_LEVEL_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__VALUE = AGGREGATION_LEVEL_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__ANY = AGGREGATION_LEVEL_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__ANY_ATTRIBUTE = AGGREGATION_LEVEL_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL__UNIQUE_ELEMENT_NAME = AGGREGATION_LEVEL_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Aggregation Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_FEATURE_COUNT = AGGREGATION_LEVEL_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Aggregation Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_OPERATION_COUNT = AGGREGATION_LEVEL_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.AggregationLevelValueImpl <em>Aggregation Level Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.AggregationLevelValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAggregationLevelValue()
	 * @generated
	 */
	int AGGREGATION_LEVEL_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Aggregation Level Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Aggregation Level Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGGREGATION_LEVEL_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.AnnotationImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 3;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__DATE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ANY = 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ANY_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CatalogImpl <em>Catalog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CatalogImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCatalog()
	 * @generated
	 */
	int CATALOG = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl <em>Classification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ClassificationImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getClassification()
	 * @generated
	 */
	int CLASSIFICATION = 5;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Purpose</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__PURPOSE = 1;

	/**
	 * The feature id for the '<em><b>Taxon Path</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__TAXON_PATH = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Keyword</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__KEYWORD = 4;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__ANY = 5;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__ANY_ATTRIBUTE = 6;

	/**
	 * The number of structural features of the '<em>Classification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Classification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ContextVocabImpl <em>Context Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ContextVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContextVocab()
	 * @generated
	 */
	int CONTEXT_VOCAB = 8;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Context Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Context Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ContextImpl <em>Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ContextImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContext()
	 * @generated
	 */
	int CONTEXT = 6;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__GROUP = CONTEXT_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__SOURCE = CONTEXT_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__VALUE = CONTEXT_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__ANY = CONTEXT_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__ANY_ATTRIBUTE = CONTEXT_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_FEATURE_COUNT = CONTEXT_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OPERATION_COUNT = CONTEXT_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ContextValueImpl <em>Context Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ContextValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContextValue()
	 * @generated
	 */
	int CONTEXT_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Context Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Context Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ContributeImpl <em>Contribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ContributeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContribute()
	 * @generated
	 */
	int CONTRIBUTE = 9;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__ROLE = 1;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__ENTITY = 2;

	/**
	 * The feature id for the '<em><b>Date</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__DATE = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__ANY = 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE__ANY_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>Contribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Contribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl <em>Contribute Meta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ContributeMetaImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContributeMeta()
	 * @generated
	 */
	int CONTRIBUTE_META = 10;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__ROLE = 1;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__ENTITY = 2;

	/**
	 * The feature id for the '<em><b>Date</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__DATE = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__ANY = 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META__ANY_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>Contribute Meta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Contribute Meta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTRIBUTE_META_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsVocabImpl <em>Copyright And Other Restrictions Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB = 13;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Copyright And Other Restrictions Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Copyright And Other Restrictions Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsImpl <em>Copyright And Other Restrictions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCopyrightAndOtherRestrictions()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS = 11;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__GROUP = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__SOURCE = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__VALUE = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__ANY = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__ANY_ATTRIBUTE = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS__UNIQUE_ELEMENT_NAME = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB_FEATURE_COUNT
			+ 0;

	/**
	 * The number of structural features of the '<em>Copyright And Other Restrictions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_FEATURE_COUNT = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Copyright And Other Restrictions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_OPERATION_COUNT = COPYRIGHT_AND_OTHER_RESTRICTIONS_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsValueImpl <em>Copyright And Other Restrictions Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CopyrightAndOtherRestrictionsValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCopyrightAndOtherRestrictionsValue()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE = 12;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Copyright And Other Restrictions Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Copyright And Other Restrictions Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CostVocabImpl <em>Cost Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CostVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCostVocab()
	 * @generated
	 */
	int COST_VOCAB = 16;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Cost Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Cost Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CostImpl <em>Cost</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CostImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCost()
	 * @generated
	 */
	int COST = 14;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__GROUP = COST_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__SOURCE = COST_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__VALUE = COST_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__ANY = COST_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__ANY_ATTRIBUTE = COST_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST__UNIQUE_ELEMENT_NAME = COST_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_FEATURE_COUNT = COST_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Cost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_OPERATION_COUNT = COST_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CostValueImpl <em>Cost Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CostValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCostValue()
	 * @generated
	 */
	int COST_VALUE = 15;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Cost Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Cost Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LanguageStringImpl <em>Language String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LanguageStringImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLanguageString()
	 * @generated
	 */
	int LANGUAGE_STRING = 47;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING__GROUP = 0;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING__STRING = 1;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING__ANY = 2;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING__ANY_ATTRIBUTE = 3;

	/**
	 * The number of structural features of the '<em>Language String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Language String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_STRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.CoverageImpl <em>Coverage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.CoverageImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCoverage()
	 * @generated
	 */
	int COVERAGE = 17;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Coverage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Coverage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DateTimeImpl <em>Date Time</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DateTimeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDateTime()
	 * @generated
	 */
	int DATE_TIME = 19;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Date Time</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME__DATE_TIME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME__ANY = 3;

	/**
	 * The number of structural features of the '<em>Date Time</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Date Time</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DateImpl <em>Date</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DateImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDate()
	 * @generated
	 */
	int DATE = 18;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__GROUP = DATE_TIME__GROUP;

	/**
	 * The feature id for the '<em><b>Date Time</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__DATE_TIME = DATE_TIME__DATE_TIME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__DESCRIPTION = DATE_TIME__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__ANY = DATE_TIME__ANY;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__UNIQUE_ELEMENT_NAME = DATE_TIME_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE__ANY_ATTRIBUTE = DATE_TIME_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Date</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_FEATURE_COUNT = DATE_TIME_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Date</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_OPERATION_COUNT = DATE_TIME_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DateTimeValueImpl <em>Date Time Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DateTimeValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDateTimeValue()
	 * @generated
	 */
	int DATE_TIME_VALUE = 20;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Date Time Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Date Time Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DescriptionImpl <em>Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DescriptionImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDescription()
	 * @generated
	 */
	int DESCRIPTION = 21;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DifficultyVocabImpl <em>Difficulty Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DifficultyVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDifficultyVocab()
	 * @generated
	 */
	int DIFFICULTY_VOCAB = 24;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Difficulty Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Difficulty Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DifficultyImpl <em>Difficulty</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DifficultyImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDifficulty()
	 * @generated
	 */
	int DIFFICULTY = 22;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__GROUP = DIFFICULTY_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__SOURCE = DIFFICULTY_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__VALUE = DIFFICULTY_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__ANY = DIFFICULTY_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__ANY_ATTRIBUTE = DIFFICULTY_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__UNIQUE_ELEMENT_NAME = DIFFICULTY_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_FEATURE_COUNT = DIFFICULTY_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_OPERATION_COUNT = DIFFICULTY_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DifficultyValueImpl <em>Difficulty Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DifficultyValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDifficultyValue()
	 * @generated
	 */
	int DIFFICULTY_VALUE = 23;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Difficulty Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Difficulty Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DurationBaseImpl <em>Duration Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DurationBaseImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDurationBase()
	 * @generated
	 */
	int DURATION_BASE = 26;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE__DURATION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE__ANY = 3;

	/**
	 * The number of structural features of the '<em>Duration Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Duration Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_BASE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DurationImpl <em>Duration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DurationImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDuration()
	 * @generated
	 */
	int DURATION = 25;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__GROUP = DURATION_BASE__GROUP;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__DURATION = DURATION_BASE__DURATION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__DESCRIPTION = DURATION_BASE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__ANY = DURATION_BASE__ANY;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__UNIQUE_ELEMENT_NAME = DURATION_BASE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION__ANY_ATTRIBUTE = DURATION_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Duration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_FEATURE_COUNT = DURATION_BASE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Duration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_OPERATION_COUNT = DURATION_BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DurationValueImpl <em>Duration Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DurationValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDurationValue()
	 * @generated
	 */
	int DURATION_VALUE = 27;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Duration Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Duration Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.EducationalImpl <em>Educational</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.EducationalImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getEducational()
	 * @generated
	 */
	int EDUCATIONAL = 28;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Interactivity Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__INTERACTIVITY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Learning Resource Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__LEARNING_RESOURCE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Interactivity Level</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__INTERACTIVITY_LEVEL = 3;

	/**
	 * The feature id for the '<em><b>Semantic Density</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__SEMANTIC_DENSITY = 4;

	/**
	 * The feature id for the '<em><b>Intended End User Role</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__INTENDED_END_USER_ROLE = 5;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__CONTEXT = 6;

	/**
	 * The feature id for the '<em><b>Typical Age Range</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__TYPICAL_AGE_RANGE = 7;

	/**
	 * The feature id for the '<em><b>Difficulty</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__DIFFICULTY = 8;

	/**
	 * The feature id for the '<em><b>Typical Learning Time</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__TYPICAL_LEARNING_TIME = 9;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__DESCRIPTION = 10;

	/**
	 * The feature id for the '<em><b>Language</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__LANGUAGE = 11;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__ANY = 12;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL__ANY_ATTRIBUTE = 13;

	/**
	 * The number of structural features of the '<em>Educational</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL_FEATURE_COUNT = 14;

	/**
	 * The number of operations of the '<em>Educational</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDUCATIONAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.VCardImpl <em>VCard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.VCardImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getVCard()
	 * @generated
	 */
	int VCARD = 104;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VCARD__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VCARD__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>VCard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VCARD_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>VCard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VCARD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.EntityImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 29;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__VALUE = VCARD__VALUE;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ANY_ATTRIBUTE = VCARD__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__UNIQUE_ELEMENT_NAME = VCARD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = VCARD_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = VCARD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.EntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.EntryImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getEntry()
	 * @generated
	 */
	int ENTRY = 30;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.EntryTaxonImpl <em>Entry Taxon</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.EntryTaxonImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getEntryTaxon()
	 * @generated
	 */
	int ENTRY_TAXON = 31;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Entry Taxon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Entry Taxon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TAXON_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.FormatImpl <em>Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.FormatImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getFormat()
	 * @generated
	 */
	int FORMAT = 32;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.GeneralImpl <em>General</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.GeneralImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getGeneral()
	 * @generated
	 */
	int GENERAL = 33;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__IDENTIFIER = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__TITLE = 2;

	/**
	 * The feature id for the '<em><b>Language</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__LANGUAGE = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Keyword</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__KEYWORD = 5;

	/**
	 * The feature id for the '<em><b>Coverage</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__COVERAGE = 6;

	/**
	 * The feature id for the '<em><b>Structure</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__STRUCTURE = 7;

	/**
	 * The feature id for the '<em><b>Aggregation Level</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__AGGREGATION_LEVEL = 8;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__ANY = 9;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__UNIQUE_ELEMENT_NAME = 10;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL__ANY_ATTRIBUTE = 11;

	/**
	 * The number of structural features of the '<em>General</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>General</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.IdImpl <em>Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.IdImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getId()
	 * @generated
	 */
	int ID = 34;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.IdentifierImpl <em>Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.IdentifierImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIdentifier()
	 * @generated
	 */
	int IDENTIFIER = 35;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER__ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InstallationRemarksImpl <em>Installation Remarks</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InstallationRemarksImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInstallationRemarks()
	 * @generated
	 */
	int INSTALLATION_REMARKS = 36;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Installation Remarks</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Installation Remarks</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTALLATION_REMARKS_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleVocabImpl <em>Intended End User Role Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIntendedEndUserRoleVocab()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE_VOCAB = 39;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Intended End User Role Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Intended End User Role Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleImpl <em>Intended End User Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIntendedEndUserRole()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE = 37;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE__GROUP = INTENDED_END_USER_ROLE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE__SOURCE = INTENDED_END_USER_ROLE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE__VALUE = INTENDED_END_USER_ROLE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE__ANY = INTENDED_END_USER_ROLE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE__ANY_ATTRIBUTE = INTENDED_END_USER_ROLE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Intended End User Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_FEATURE_COUNT = INTENDED_END_USER_ROLE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Intended End User Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_OPERATION_COUNT = INTENDED_END_USER_ROLE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleValueImpl <em>Intended End User Role Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.IntendedEndUserRoleValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIntendedEndUserRoleValue()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE_VALUE = 38;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Intended End User Role Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Intended End User Role Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENDED_END_USER_ROLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelVocabImpl <em>Interactivity Level Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityLevelVocab()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL_VOCAB = 42;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Interactivity Level Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Interactivity Level Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelImpl <em>Interactivity Level</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityLevel()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL = 40;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__GROUP = INTERACTIVITY_LEVEL_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__SOURCE = INTERACTIVITY_LEVEL_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__VALUE = INTERACTIVITY_LEVEL_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__ANY = INTERACTIVITY_LEVEL_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__ANY_ATTRIBUTE = INTERACTIVITY_LEVEL_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL__UNIQUE_ELEMENT_NAME = INTERACTIVITY_LEVEL_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Interactivity Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_FEATURE_COUNT = INTERACTIVITY_LEVEL_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Interactivity Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_OPERATION_COUNT = INTERACTIVITY_LEVEL_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelValueImpl <em>Interactivity Level Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityLevelValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityLevelValue()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL_VALUE = 41;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Interactivity Level Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Interactivity Level Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_LEVEL_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeVocabImpl <em>Interactivity Type Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityTypeVocab()
	 * @generated
	 */
	int INTERACTIVITY_TYPE_VOCAB = 50;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Interactivity Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Interactivity Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeImpl <em>Interactivity Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityType()
	 * @generated
	 */
	int INTERACTIVITY_TYPE = 43;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__GROUP = INTERACTIVITY_TYPE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__SOURCE = INTERACTIVITY_TYPE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__VALUE = INTERACTIVITY_TYPE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__ANY = INTERACTIVITY_TYPE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__ANY_ATTRIBUTE = INTERACTIVITY_TYPE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE__UNIQUE_ELEMENT_NAME = INTERACTIVITY_TYPE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Interactivity Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_FEATURE_COUNT = INTERACTIVITY_TYPE_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Interactivity Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_OPERATION_COUNT = INTERACTIVITY_TYPE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.KeywordImpl <em>Keyword</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.KeywordImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKeyword()
	 * @generated
	 */
	int KEYWORD = 44;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__ID = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD__SOURCE = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Keyword</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Keyword</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEYWORD_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.KindValueImpl <em>Kind Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.KindValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKindValue()
	 * @generated
	 */
	int KIND_VALUE = 45;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Kind Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Kind Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LangStringImpl <em>Lang String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LangStringImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLangString()
	 * @generated
	 */
	int LANG_STRING = 46;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANG_STRING__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANG_STRING__LANGUAGE = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANG_STRING__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Lang String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANG_STRING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Lang String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANG_STRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.DocumentRootImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 48;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Lom</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOM = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeValueImpl <em>Interactivity Type Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.InteractivityTypeValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityTypeValue()
	 * @generated
	 */
	int INTERACTIVITY_TYPE_VALUE = 49;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Interactivity Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Interactivity Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERACTIVITY_TYPE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.KindVocabImpl <em>Kind Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.KindVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKindVocab()
	 * @generated
	 */
	int KIND_VOCAB = 52;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Kind Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Kind Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.KindImpl <em>Kind</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.KindImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKind()
	 * @generated
	 */
	int KIND = 51;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__GROUP = KIND_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__SOURCE = KIND_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__VALUE = KIND_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__ANY = KIND_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__ANY_ATTRIBUTE = KIND_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND__UNIQUE_ELEMENT_NAME = KIND_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_FEATURE_COUNT = KIND_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KIND_OPERATION_COUNT = KIND_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LanguageIdImpl <em>Language Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LanguageIdImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLanguageId()
	 * @generated
	 */
	int LANGUAGE_ID = 54;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_ID__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_ID__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Language Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_ID_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Language Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_ID_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LanguageImpl <em>Language</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LanguageImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 53;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__VALUE = LANGUAGE_ID__VALUE;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__ANY_ATTRIBUTE = LANGUAGE_ID__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__UNIQUE_ELEMENT_NAME = LANGUAGE_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Language</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_FEATURE_COUNT = LANGUAGE_ID_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Language</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_OPERATION_COUNT = LANGUAGE_ID_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeVocabImpl <em>Learning Resource Type Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLearningResourceTypeVocab()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB = 57;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Learning Resource Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Learning Resource Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeImpl <em>Learning Resource Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLearningResourceType()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE = 55;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE__GROUP = LEARNING_RESOURCE_TYPE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE__SOURCE = LEARNING_RESOURCE_TYPE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE__VALUE = LEARNING_RESOURCE_TYPE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE__ANY = LEARNING_RESOURCE_TYPE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE__ANY_ATTRIBUTE = LEARNING_RESOURCE_TYPE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Learning Resource Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_FEATURE_COUNT = LEARNING_RESOURCE_TYPE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Learning Resource Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_OPERATION_COUNT = LEARNING_RESOURCE_TYPE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeValueImpl <em>Learning Resource Type Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LearningResourceTypeValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLearningResourceTypeValue()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VALUE = 56;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Learning Resource Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Learning Resource Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEARNING_RESOURCE_TYPE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl <em>Life Cycle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LifeCycleImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLifeCycle()
	 * @generated
	 */
	int LIFE_CYCLE = 58;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__VERSION = 1;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__STATUS = 2;

	/**
	 * The feature id for the '<em><b>Contribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__CONTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__ANY = 4;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__UNIQUE_ELEMENT_NAME = 5;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE__ANY_ATTRIBUTE = 6;

	/**
	 * The number of structural features of the '<em>Life Cycle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Life Cycle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LocationImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 59;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.LomImpl <em>Lom</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLom()
	 * @generated
	 */
	int LOM = 60;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__GROUP = 0;

	/**
	 * The feature id for the '<em><b>General</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__GENERAL = 1;

	/**
	 * The feature id for the '<em><b>Life Cycle</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__LIFE_CYCLE = 2;

	/**
	 * The feature id for the '<em><b>Meta Metadata</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__META_METADATA = 3;

	/**
	 * The feature id for the '<em><b>Technical</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__TECHNICAL = 4;

	/**
	 * The feature id for the '<em><b>Educational</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__EDUCATIONAL = 5;

	/**
	 * The feature id for the '<em><b>Rights</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__RIGHTS = 6;

	/**
	 * The feature id for the '<em><b>Relation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__RELATION = 7;

	/**
	 * The feature id for the '<em><b>Annotation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__ANNOTATION = 8;

	/**
	 * The feature id for the '<em><b>Classification</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__CLASSIFICATION = 9;

	/**
	 * The feature id for the '<em><b>Healthcare Metadata</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__HEALTHCARE_METADATA = 10;

	/**
	 * The feature id for the '<em><b>Custom Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__CUSTOM_ELEMENTS = 11;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM__ANY_ATTRIBUTE = 12;

	/**
	 * The number of structural features of the '<em>Lom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Lom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.MaximumVersionImpl <em>Maximum Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.MaximumVersionImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getMaximumVersion()
	 * @generated
	 */
	int MAXIMUM_VERSION = 61;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAXIMUM_VERSION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAXIMUM_VERSION__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAXIMUM_VERSION__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Maximum Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAXIMUM_VERSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Maximum Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAXIMUM_VERSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.MetadataSchemaImpl <em>Metadata Schema</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.MetadataSchemaImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getMetadataSchema()
	 * @generated
	 */
	int METADATA_SCHEMA = 62;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SCHEMA__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SCHEMA__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Metadata Schema</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SCHEMA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Metadata Schema</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SCHEMA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.MetaMetadataImpl <em>Meta Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.MetaMetadataImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getMetaMetadata()
	 * @generated
	 */
	int META_METADATA = 63;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__IDENTIFIER = 1;

	/**
	 * The feature id for the '<em><b>Contribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__CONTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Metadata Schema</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__METADATA_SCHEMA = 3;

	/**
	 * The feature id for the '<em><b>Language</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__LANGUAGE = 4;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__ANY = 5;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__UNIQUE_ELEMENT_NAME = 6;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA__ANY_ATTRIBUTE = 7;

	/**
	 * The number of structural features of the '<em>Meta Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Meta Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_METADATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.MinimumVersionImpl <em>Minimum Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.MinimumVersionImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getMinimumVersion()
	 * @generated
	 */
	int MINIMUM_VERSION = 64;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINIMUM_VERSION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINIMUM_VERSION__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINIMUM_VERSION__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Minimum Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINIMUM_VERSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Minimum Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINIMUM_VERSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.NameVocabImpl <em>Name Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.NameVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getNameVocab()
	 * @generated
	 */
	int NAME_VOCAB = 67;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Name Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Name Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.NameImpl <em>Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.NameImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getName_()
	 * @generated
	 */
	int NAME = 65;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__GROUP = NAME_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__SOURCE = NAME_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__VALUE = NAME_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__ANY = NAME_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__ANY_ATTRIBUTE = NAME_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME__UNIQUE_ELEMENT_NAME = NAME_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_FEATURE_COUNT = NAME_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_OPERATION_COUNT = NAME_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.NameValueImpl <em>Name Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.NameValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getNameValue()
	 * @generated
	 */
	int NAME_VALUE = 66;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Name Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Name Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl <em>Or Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.OrCompositeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getOrComposite()
	 * @generated
	 */
	int OR_COMPOSITE = 68;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Minimum Version</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__MINIMUM_VERSION = 3;

	/**
	 * The feature id for the '<em><b>Maximum Version</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__MAXIMUM_VERSION = 4;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__ANY = 5;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE__ANY_ATTRIBUTE = 6;

	/**
	 * The number of structural features of the '<em>Or Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Or Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_COMPOSITE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.OtherPlatformRequirementsImpl <em>Other Platform Requirements</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.OtherPlatformRequirementsImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getOtherPlatformRequirements()
	 * @generated
	 */
	int OTHER_PLATFORM_REQUIREMENTS = 69;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Other Platform Requirements</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Other Platform Requirements</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_PLATFORM_REQUIREMENTS_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.PurposeVocabImpl <em>Purpose Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.PurposeVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getPurposeVocab()
	 * @generated
	 */
	int PURPOSE_VOCAB = 72;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Purpose Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Purpose Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.PurposeImpl <em>Purpose</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.PurposeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getPurpose()
	 * @generated
	 */
	int PURPOSE = 70;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__GROUP = PURPOSE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__SOURCE = PURPOSE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__VALUE = PURPOSE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__ANY = PURPOSE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__ANY_ATTRIBUTE = PURPOSE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE__UNIQUE_ELEMENT_NAME = PURPOSE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Purpose</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_FEATURE_COUNT = PURPOSE_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Purpose</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_OPERATION_COUNT = PURPOSE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.PurposeValueImpl <em>Purpose Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.PurposeValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getPurposeValue()
	 * @generated
	 */
	int PURPOSE_VALUE = 71;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Purpose Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Purpose Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PURPOSE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RelationImpl <em>Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RelationImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRelation()
	 * @generated
	 */
	int RELATION = 73;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__KIND = 1;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__RESOURCE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RequirementImpl <em>Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RequirementImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRequirement()
	 * @generated
	 */
	int REQUIREMENT = 74;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Or Composite</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__OR_COMPOSITE = 1;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__ANY = 2;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__ANY_ATTRIBUTE = 3;

	/**
	 * The number of structural features of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.ResourceImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 75;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__IDENTIFIER = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__ANY = 3;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__UNIQUE_ELEMENT_NAME = 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__ANY_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RightsImpl <em>Rights</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RightsImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRights()
	 * @generated
	 */
	int RIGHTS = 76;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Cost</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__COST = 1;

	/**
	 * The feature id for the '<em><b>Copyright And Other Restrictions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__COPYRIGHT_AND_OTHER_RESTRICTIONS = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__ANY = 4;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__UNIQUE_ELEMENT_NAME = 5;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS__ANY_ATTRIBUTE = 6;

	/**
	 * The number of structural features of the '<em>Rights</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Rights</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RIGHTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleVocabImpl <em>Role Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleVocab()
	 * @generated
	 */
	int ROLE_VOCAB = 82;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Role Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Role Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 77;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__GROUP = ROLE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__SOURCE = ROLE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__VALUE = ROLE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ANY = ROLE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ANY_ATTRIBUTE = ROLE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__UNIQUE_ELEMENT_NAME = ROLE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_FEATURE_COUNT = ROLE_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_OPERATION_COUNT = ROLE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleMetaVocabImpl <em>Role Meta Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleMetaVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleMetaVocab()
	 * @generated
	 */
	int ROLE_META_VOCAB = 80;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Role Meta Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Role Meta Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleMetaImpl <em>Role Meta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleMetaImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleMeta()
	 * @generated
	 */
	int ROLE_META = 78;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__GROUP = ROLE_META_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__SOURCE = ROLE_META_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__VALUE = ROLE_META_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__ANY = ROLE_META_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__ANY_ATTRIBUTE = ROLE_META_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META__UNIQUE_ELEMENT_NAME = ROLE_META_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role Meta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_FEATURE_COUNT = ROLE_META_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Role Meta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_OPERATION_COUNT = ROLE_META_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleMetaValueImpl <em>Role Meta Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleMetaValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleMetaValue()
	 * @generated
	 */
	int ROLE_META_VALUE = 79;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Role Meta Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Role Meta Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_META_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.RoleValueImpl <em>Role Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.RoleValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleValue()
	 * @generated
	 */
	int ROLE_VALUE = 81;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Role Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Role Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SemanticDensityVocabImpl <em>Semantic Density Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SemanticDensityVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSemanticDensityVocab()
	 * @generated
	 */
	int SEMANTIC_DENSITY_VOCAB = 85;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Semantic Density Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Semantic Density Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SemanticDensityImpl <em>Semantic Density</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SemanticDensityImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSemanticDensity()
	 * @generated
	 */
	int SEMANTIC_DENSITY = 83;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__GROUP = SEMANTIC_DENSITY_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__SOURCE = SEMANTIC_DENSITY_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__VALUE = SEMANTIC_DENSITY_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__ANY = SEMANTIC_DENSITY_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__ANY_ATTRIBUTE = SEMANTIC_DENSITY_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY__UNIQUE_ELEMENT_NAME = SEMANTIC_DENSITY_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Semantic Density</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_FEATURE_COUNT = SEMANTIC_DENSITY_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Semantic Density</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_OPERATION_COUNT = SEMANTIC_DENSITY_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SemanticDensityValueImpl <em>Semantic Density Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SemanticDensityValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSemanticDensityValue()
	 * @generated
	 */
	int SEMANTIC_DENSITY_VALUE = 84;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Semantic Density Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Semantic Density Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_DENSITY_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SizeImpl <em>Size</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SizeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSize()
	 * @generated
	 */
	int SIZE = 86;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIZE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIZE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIZE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIZE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIZE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SourceImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 87;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.SourceValueImpl <em>Source Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.SourceValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSourceValue()
	 * @generated
	 */
	int SOURCE_VALUE = 88;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Source Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Source Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StatusVocabImpl <em>Status Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StatusVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStatusVocab()
	 * @generated
	 */
	int STATUS_VOCAB = 91;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Status Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Status Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StatusImpl <em>Status</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StatusImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStatus()
	 * @generated
	 */
	int STATUS = 89;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__GROUP = STATUS_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__SOURCE = STATUS_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__VALUE = STATUS_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__ANY = STATUS_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__ANY_ATTRIBUTE = STATUS_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS__UNIQUE_ELEMENT_NAME = STATUS_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Status</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_FEATURE_COUNT = STATUS_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Status</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_OPERATION_COUNT = STATUS_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StatusValueImpl <em>Status Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StatusValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStatusValue()
	 * @generated
	 */
	int STATUS_VALUE = 90;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Status Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Status Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StructureVocabImpl <em>Structure Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StructureVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStructureVocab()
	 * @generated
	 */
	int STRUCTURE_VOCAB = 94;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Structure Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Structure Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StructureImpl <em>Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StructureImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStructure()
	 * @generated
	 */
	int STRUCTURE = 92;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__GROUP = STRUCTURE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__SOURCE = STRUCTURE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__VALUE = STRUCTURE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__ANY = STRUCTURE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__ANY_ATTRIBUTE = STRUCTURE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE__UNIQUE_ELEMENT_NAME = STRUCTURE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_FEATURE_COUNT = STRUCTURE_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_OPERATION_COUNT = STRUCTURE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.StructureValueImpl <em>Structure Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.StructureValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStructureValue()
	 * @generated
	 */
	int STRUCTURE_VALUE = 93;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Structure Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Structure Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TaxonImpl <em>Taxon</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TaxonImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTaxon()
	 * @generated
	 */
	int TAXON = 95;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON__ID = 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON__ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Taxon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Taxon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl <em>Taxon Path</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TaxonPathImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTaxonPath()
	 * @generated
	 */
	int TAXON_PATH = 96;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Taxon</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH__TAXON = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Taxon Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Taxon Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXON_PATH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl <em>Technical</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TechnicalImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTechnical()
	 * @generated
	 */
	int TECHNICAL = 97;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__SIZE = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Requirement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__REQUIREMENT = 4;

	/**
	 * The feature id for the '<em><b>Installation Remarks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__INSTALLATION_REMARKS = 5;

	/**
	 * The feature id for the '<em><b>Other Platform Requirements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__OTHER_PLATFORM_REQUIREMENTS = 6;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__DURATION = 7;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__ANY = 8;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__UNIQUE_ELEMENT_NAME = 9;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL__ANY_ATTRIBUTE = 10;

	/**
	 * The number of structural features of the '<em>Technical</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL_FEATURE_COUNT = 11;

	/**
	 * The number of operations of the '<em>Technical</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNICAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TitleImpl <em>Title</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TitleImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTitle()
	 * @generated
	 */
	int TITLE = 98;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Title</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Title</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLE_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TypeVocabImpl <em>Type Vocab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TypeVocabImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypeVocab()
	 * @generated
	 */
	int TYPE_VOCAB = 101;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB__ANY = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Type Vocab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOCAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TypeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 99;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__GROUP = TYPE_VOCAB__GROUP;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__SOURCE = TYPE_VOCAB__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__VALUE = TYPE_VOCAB__VALUE;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__ANY = TYPE_VOCAB__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__ANY_ATTRIBUTE = TYPE_VOCAB__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__UNIQUE_ELEMENT_NAME = TYPE_VOCAB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = TYPE_VOCAB_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = TYPE_VOCAB_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TypeValueImpl <em>Type Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TypeValueImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypeValue()
	 * @generated
	 */
	int TYPE_VALUE = 100;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VALUE__UNIQUE_ELEMENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VALUE__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TypicalAgeRangeImpl <em>Typical Age Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TypicalAgeRangeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypicalAgeRange()
	 * @generated
	 */
	int TYPICAL_AGE_RANGE = 102;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Typical Age Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Typical Age Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_AGE_RANGE_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.TypicalLearningTimeImpl <em>Typical Learning Time</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.TypicalLearningTimeImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypicalLearningTime()
	 * @generated
	 */
	int TYPICAL_LEARNING_TIME = 103;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__GROUP = DURATION_BASE__GROUP;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__DURATION = DURATION_BASE__DURATION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__DESCRIPTION = DURATION_BASE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__ANY = DURATION_BASE__ANY;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__UNIQUE_ELEMENT_NAME = DURATION_BASE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME__ANY_ATTRIBUTE = DURATION_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Typical Learning Time</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME_FEATURE_COUNT = DURATION_BASE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Typical Learning Time</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPICAL_LEARNING_TIME_OPERATION_COUNT = DURATION_BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.impl.VersionImpl <em>Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.impl.VersionImpl
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getVersion()
	 * @generated
	 */
	int VERSION = 105;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__GROUP = LANGUAGE_STRING__GROUP;

	/**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__STRING = LANGUAGE_STRING__STRING;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__ANY = LANGUAGE_STRING__ANY;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__ANY_ATTRIBUTE = LANGUAGE_STRING__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__UNIQUE_ELEMENT_NAME = LANGUAGE_STRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_FEATURE_COUNT = LANGUAGE_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_OPERATION_COUNT = LANGUAGE_STRING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValues <em>Aggregation Level Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAggregationLevelValues()
	 * @generated
	 */
	int AGGREGATION_LEVEL_VALUES = 106;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.ContextValues <em>Context Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.ContextValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContextValues()
	 * @generated
	 */
	int CONTEXT_VALUES = 107;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues <em>Copyright And Other Restrictions Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCopyrightAndOtherRestrictionsValues()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES = 108;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.CostValues <em>Cost Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.CostValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCostValues()
	 * @generated
	 */
	int COST_VALUES = 109;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.DifficultyValues <em>Difficulty Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDifficultyValues()
	 * @generated
	 */
	int DIFFICULTY_VALUES = 110;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues <em>Intended End User Role Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIntendedEndUserRoleValues()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE_VALUES = 111;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValues <em>Interactivity Level Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityLevelValues()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL_VALUES = 112;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValues <em>Interactivity Type Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityTypeValues()
	 * @generated
	 */
	int INTERACTIVITY_TYPE_VALUES = 113;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.KindValues <em>Kind Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.KindValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKindValues()
	 * @generated
	 */
	int KIND_VALUES = 114;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues <em>Learning Resource Type Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLearningResourceTypeValues()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VALUES = 115;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.NameValues <em>Name Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.NameValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getNameValues()
	 * @generated
	 */
	int NAME_VALUES = 116;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.PurposeValues <em>Purpose Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getPurposeValues()
	 * @generated
	 */
	int PURPOSE_VALUES = 117;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValues <em>Role Meta Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleMetaValues()
	 * @generated
	 */
	int ROLE_META_VALUES = 118;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.RoleValues <em>Role Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.RoleValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleValues()
	 * @generated
	 */
	int ROLE_VALUES = 119;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValues <em>Semantic Density Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSemanticDensityValues()
	 * @generated
	 */
	int SEMANTIC_DENSITY_VALUES = 120;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.SourceValues <em>Source Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.SourceValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSourceValues()
	 * @generated
	 */
	int SOURCE_VALUES = 121;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.StatusValues <em>Status Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.StatusValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStatusValues()
	 * @generated
	 */
	int STATUS_VALUES = 122;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.StructureValues <em>Structure Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.StructureValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStructureValues()
	 * @generated
	 */
	int STRUCTURE_VALUES = 123;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.lom.TypeValues <em>Type Values</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.TypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypeValues()
	 * @generated
	 */
	int TYPE_VALUES = 124;

	/**
	 * The meta object id for the '<em>Aggregation Level Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getAggregationLevelValuesObject()
	 * @generated
	 */
	int AGGREGATION_LEVEL_VALUES_OBJECT = 125;

	/**
	 * The meta object id for the '<em>Character String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCharacterString()
	 * @generated
	 */
	int CHARACTER_STRING = 126;

	/**
	 * The meta object id for the '<em>Context Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.ContextValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getContextValuesObject()
	 * @generated
	 */
	int CONTEXT_VALUES_OBJECT = 127;

	/**
	 * The meta object id for the '<em>Copyright And Other Restrictions Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCopyrightAndOtherRestrictionsValuesObject()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS_VALUES_OBJECT = 128;

	/**
	 * The meta object id for the '<em>Cost Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.CostValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getCostValuesObject()
	 * @generated
	 */
	int COST_VALUES_OBJECT = 129;

	/**
	 * The meta object id for the '<em>Date Time String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDateTimeString()
	 * @generated
	 */
	int DATE_TIME_STRING = 130;

	/**
	 * The meta object id for the '<em>Difficulty Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDifficultyValuesObject()
	 * @generated
	 */
	int DIFFICULTY_VALUES_OBJECT = 131;

	/**
	 * The meta object id for the '<em>Duration String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getDurationString()
	 * @generated
	 */
	int DURATION_STRING = 132;

	/**
	 * The meta object id for the '<em>Interactivity Level Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityLevelValuesObject()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL_VALUES_OBJECT = 133;

	/**
	 * The meta object id for the '<em>Intended End User Role Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getIntendedEndUserRoleValuesObject()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE_VALUES_OBJECT = 134;

	/**
	 * The meta object id for the '<em>Interactivity Type Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getInteractivityTypeValuesObject()
	 * @generated
	 */
	int INTERACTIVITY_TYPE_VALUES_OBJECT = 135;

	/**
	 * The meta object id for the '<em>Kind Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.KindValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getKindValuesObject()
	 * @generated
	 */
	int KIND_VALUES_OBJECT = 136;

	/**
	 * The meta object id for the '<em>Learning Resource Type Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getLearningResourceTypeValuesObject()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE_VALUES_OBJECT = 137;

	/**
	 * The meta object id for the '<em>Mime Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getMimeType()
	 * @generated
	 */
	int MIME_TYPE = 138;

	/**
	 * The meta object id for the '<em>Name Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.NameValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getNameValuesObject()
	 * @generated
	 */
	int NAME_VALUES_OBJECT = 139;

	/**
	 * The meta object id for the '<em>Purpose Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getPurposeValuesObject()
	 * @generated
	 */
	int PURPOSE_VALUES_OBJECT = 140;

	/**
	 * The meta object id for the '<em>Role Meta Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleMetaValuesObject()
	 * @generated
	 */
	int ROLE_META_VALUES_OBJECT = 141;

	/**
	 * The meta object id for the '<em>Role Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.RoleValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getRoleValuesObject()
	 * @generated
	 */
	int ROLE_VALUES_OBJECT = 142;

	/**
	 * The meta object id for the '<em>Semantic Density Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSemanticDensityValuesObject()
	 * @generated
	 */
	int SEMANTIC_DENSITY_VALUES_OBJECT = 143;

	/**
	 * The meta object id for the '<em>Size Base</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.math.BigInteger
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSizeBase()
	 * @generated
	 */
	int SIZE_BASE = 144;

	/**
	 * The meta object id for the '<em>Source Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.SourceValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getSourceValuesObject()
	 * @generated
	 */
	int SOURCE_VALUES_OBJECT = 145;

	/**
	 * The meta object id for the '<em>Status Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.StatusValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStatusValuesObject()
	 * @generated
	 */
	int STATUS_VALUES_OBJECT = 146;

	/**
	 * The meta object id for the '<em>Structure Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.StructureValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getStructureValuesObject()
	 * @generated
	 */
	int STRUCTURE_VALUES_OBJECT = 147;

	/**
	 * The meta object id for the '<em>Type Values Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.lom.TypeValues
	 * @see edu.louisiana.caai.lom.model.lom.impl.LomPackageImpl#getTypeValuesObject()
	 * @generated
	 */
	int TYPE_VALUES_OBJECT = 148;

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.AggregationLevel <em>Aggregation Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aggregation Level</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevel
	 * @generated
	 */
	EClass getAggregationLevel();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.AggregationLevel#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevel#getUniqueElementName()
	 * @see #getAggregationLevel()
	 * @generated
	 */
	EAttribute getAggregationLevel_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValue <em>Aggregation Level Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aggregation Level Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValue
	 * @generated
	 */
	EClass getAggregationLevelValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getValue()
	 * @see #getAggregationLevelValue()
	 * @generated
	 */
	EAttribute getAggregationLevelValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getUniqueElementName()
	 * @see #getAggregationLevelValue()
	 * @generated
	 */
	EAttribute getAggregationLevelValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValue#getAnyAttribute()
	 * @see #getAggregationLevelValue()
	 * @generated
	 */
	EAttribute getAggregationLevelValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab <em>Aggregation Level Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aggregation Level Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab
	 * @generated
	 */
	EClass getAggregationLevelVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getGroup()
	 * @see #getAggregationLevelVocab()
	 * @generated
	 */
	EAttribute getAggregationLevelVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getSource()
	 * @see #getAggregationLevelVocab()
	 * @generated
	 */
	EReference getAggregationLevelVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getValue()
	 * @see #getAggregationLevelVocab()
	 * @generated
	 */
	EReference getAggregationLevelVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getAny()
	 * @see #getAggregationLevelVocab()
	 * @generated
	 */
	EAttribute getAggregationLevelVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelVocab#getAnyAttribute()
	 * @see #getAggregationLevelVocab()
	 * @generated
	 */
	EAttribute getAggregationLevelVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getGroup()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getEntity()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Entity();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Date</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getDate()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Date();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getDescription()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Description();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getAny()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Annotation#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Annotation#getAnyAttribute()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Catalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Catalog</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Catalog
	 * @generated
	 */
	EClass getCatalog();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Catalog#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Catalog#getValue()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Catalog#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Catalog#getUniqueElementName()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Catalog#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Catalog#getAnyAttribute()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Classification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classification</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification
	 * @generated
	 */
	EClass getClassification();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Classification#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getGroup()
	 * @see #getClassification()
	 * @generated
	 */
	EAttribute getClassification_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Classification#getPurpose <em>Purpose</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Purpose</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getPurpose()
	 * @see #getClassification()
	 * @generated
	 */
	EReference getClassification_Purpose();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Classification#getTaxonPath <em>Taxon Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Taxon Path</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getTaxonPath()
	 * @see #getClassification()
	 * @generated
	 */
	EReference getClassification_TaxonPath();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Classification#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getDescription()
	 * @see #getClassification()
	 * @generated
	 */
	EReference getClassification_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Classification#getKeyword <em>Keyword</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Keyword</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getKeyword()
	 * @see #getClassification()
	 * @generated
	 */
	EReference getClassification_Keyword();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Classification#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getAny()
	 * @see #getClassification()
	 * @generated
	 */
	EAttribute getClassification_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Classification#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Classification#getAnyAttribute()
	 * @see #getClassification()
	 * @generated
	 */
	EAttribute getClassification_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Context <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Context
	 * @generated
	 */
	EClass getContext();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.ContextValue <em>Context Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValue
	 * @generated
	 */
	EClass getContextValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.ContextValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValue#getValue()
	 * @see #getContextValue()
	 * @generated
	 */
	EAttribute getContextValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.ContextValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValue#getUniqueElementName()
	 * @see #getContextValue()
	 * @generated
	 */
	EAttribute getContextValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContextValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValue#getAnyAttribute()
	 * @see #getContextValue()
	 * @generated
	 */
	EAttribute getContextValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.ContextVocab <em>Context Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab
	 * @generated
	 */
	EClass getContextVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContextVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab#getGroup()
	 * @see #getContextVocab()
	 * @generated
	 */
	EAttribute getContextVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.ContextVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab#getSource()
	 * @see #getContextVocab()
	 * @generated
	 */
	EReference getContextVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.ContextVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab#getValue()
	 * @see #getContextVocab()
	 * @generated
	 */
	EReference getContextVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContextVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab#getAny()
	 * @see #getContextVocab()
	 * @generated
	 */
	EAttribute getContextVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContextVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextVocab#getAnyAttribute()
	 * @see #getContextVocab()
	 * @generated
	 */
	EAttribute getContextVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Contribute <em>Contribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute
	 * @generated
	 */
	EClass getContribute();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getGroup()
	 * @see #getContribute()
	 * @generated
	 */
	EAttribute getContribute_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getRole()
	 * @see #getContribute()
	 * @generated
	 */
	EReference getContribute_Role();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getEntity()
	 * @see #getContribute()
	 * @generated
	 */
	EReference getContribute_Entity();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Date</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getDate()
	 * @see #getContribute()
	 * @generated
	 */
	EReference getContribute_Date();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getAny()
	 * @see #getContribute()
	 * @generated
	 */
	EAttribute getContribute_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Contribute#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Contribute#getAnyAttribute()
	 * @see #getContribute()
	 * @generated
	 */
	EAttribute getContribute_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta <em>Contribute Meta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contribute Meta</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta
	 * @generated
	 */
	EClass getContributeMeta();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getGroup()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EAttribute getContributeMeta_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getRole()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EReference getContributeMeta_Role();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getEntity()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EReference getContributeMeta_Entity();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Date</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getDate()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EReference getContributeMeta_Date();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getAny()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EAttribute getContributeMeta_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.ContributeMeta#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContributeMeta#getAnyAttribute()
	 * @see #getContributeMeta()
	 * @generated
	 */
	EAttribute getContributeMeta_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictions <em>Copyright And Other Restrictions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Copyright And Other Restrictions</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictions
	 * @generated
	 */
	EClass getCopyrightAndOtherRestrictions();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictions#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictions#getUniqueElementName()
	 * @see #getCopyrightAndOtherRestrictions()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictions_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue <em>Copyright And Other Restrictions Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Copyright And Other Restrictions Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue
	 * @generated
	 */
	EClass getCopyrightAndOtherRestrictionsValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getValue()
	 * @see #getCopyrightAndOtherRestrictionsValue()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getUniqueElementName()
	 * @see #getCopyrightAndOtherRestrictionsValue()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValue#getAnyAttribute()
	 * @see #getCopyrightAndOtherRestrictionsValue()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab <em>Copyright And Other Restrictions Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Copyright And Other Restrictions Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab
	 * @generated
	 */
	EClass getCopyrightAndOtherRestrictionsVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getGroup()
	 * @see #getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getSource()
	 * @see #getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	EReference getCopyrightAndOtherRestrictionsVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getValue()
	 * @see #getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	EReference getCopyrightAndOtherRestrictionsVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getAny()
	 * @see #getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsVocab#getAnyAttribute()
	 * @see #getCopyrightAndOtherRestrictionsVocab()
	 * @generated
	 */
	EAttribute getCopyrightAndOtherRestrictionsVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Cost <em>Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cost</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Cost
	 * @generated
	 */
	EClass getCost();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Cost#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Cost#getUniqueElementName()
	 * @see #getCost()
	 * @generated
	 */
	EAttribute getCost_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.CostValue <em>Cost Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cost Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValue
	 * @generated
	 */
	EClass getCostValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.CostValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValue#getValue()
	 * @see #getCostValue()
	 * @generated
	 */
	EAttribute getCostValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.CostValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValue#getUniqueElementName()
	 * @see #getCostValue()
	 * @generated
	 */
	EAttribute getCostValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CostValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValue#getAnyAttribute()
	 * @see #getCostValue()
	 * @generated
	 */
	EAttribute getCostValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.CostVocab <em>Cost Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cost Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab
	 * @generated
	 */
	EClass getCostVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CostVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab#getGroup()
	 * @see #getCostVocab()
	 * @generated
	 */
	EAttribute getCostVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.CostVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab#getSource()
	 * @see #getCostVocab()
	 * @generated
	 */
	EReference getCostVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.CostVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab#getValue()
	 * @see #getCostVocab()
	 * @generated
	 */
	EReference getCostVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CostVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab#getAny()
	 * @see #getCostVocab()
	 * @generated
	 */
	EAttribute getCostVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.CostVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostVocab#getAnyAttribute()
	 * @see #getCostVocab()
	 * @generated
	 */
	EAttribute getCostVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Coverage <em>Coverage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Coverage</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Coverage
	 * @generated
	 */
	EClass getCoverage();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Date <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Date
	 * @generated
	 */
	EClass getDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Date#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Date#getUniqueElementName()
	 * @see #getDate()
	 * @generated
	 */
	EAttribute getDate_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Date#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Date#getAnyAttribute()
	 * @see #getDate()
	 * @generated
	 */
	EAttribute getDate_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DateTime <em>Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTime
	 * @generated
	 */
	EClass getDateTime();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DateTime#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTime#getGroup()
	 * @see #getDateTime()
	 * @generated
	 */
	EAttribute getDateTime_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DateTime#getDateTime <em>Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTime#getDateTime()
	 * @see #getDateTime()
	 * @generated
	 */
	EReference getDateTime_DateTime();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DateTime#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTime#getDescription()
	 * @see #getDateTime()
	 * @generated
	 */
	EReference getDateTime_Description();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DateTime#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTime#getAny()
	 * @see #getDateTime()
	 * @generated
	 */
	EAttribute getDateTime_Any();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DateTimeValue <em>Date Time Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTimeValue
	 * @generated
	 */
	EClass getDateTimeValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DateTimeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTimeValue#getValue()
	 * @see #getDateTimeValue()
	 * @generated
	 */
	EAttribute getDateTimeValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DateTimeValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTimeValue#getUniqueElementName()
	 * @see #getDateTimeValue()
	 * @generated
	 */
	EAttribute getDateTimeValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DateTimeValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DateTimeValue#getAnyAttribute()
	 * @see #getDateTimeValue()
	 * @generated
	 */
	EAttribute getDateTimeValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Description
	 * @generated
	 */
	EClass getDescription();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Description#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Description#getUniqueElementName()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Difficulty <em>Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difficulty</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Difficulty
	 * @generated
	 */
	EClass getDifficulty();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Difficulty#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Difficulty#getUniqueElementName()
	 * @see #getDifficulty()
	 * @generated
	 */
	EAttribute getDifficulty_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DifficultyValue <em>Difficulty Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difficulty Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValue
	 * @generated
	 */
	EClass getDifficultyValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DifficultyValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValue#getValue()
	 * @see #getDifficultyValue()
	 * @generated
	 */
	EAttribute getDifficultyValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DifficultyValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValue#getUniqueElementName()
	 * @see #getDifficultyValue()
	 * @generated
	 */
	EAttribute getDifficultyValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DifficultyValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValue#getAnyAttribute()
	 * @see #getDifficultyValue()
	 * @generated
	 */
	EAttribute getDifficultyValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab <em>Difficulty Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difficulty Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab
	 * @generated
	 */
	EClass getDifficultyVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab#getGroup()
	 * @see #getDifficultyVocab()
	 * @generated
	 */
	EAttribute getDifficultyVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab#getSource()
	 * @see #getDifficultyVocab()
	 * @generated
	 */
	EReference getDifficultyVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab#getValue()
	 * @see #getDifficultyVocab()
	 * @generated
	 */
	EReference getDifficultyVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab#getAny()
	 * @see #getDifficultyVocab()
	 * @generated
	 */
	EAttribute getDifficultyVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DifficultyVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyVocab#getAnyAttribute()
	 * @see #getDifficultyVocab()
	 * @generated
	 */
	EAttribute getDifficultyVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Duration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Duration</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Duration
	 * @generated
	 */
	EClass getDuration();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Duration#getUniqueElementName()
	 * @see #getDuration()
	 * @generated
	 */
	EAttribute getDuration_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Duration#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Duration#getAnyAttribute()
	 * @see #getDuration()
	 * @generated
	 */
	EAttribute getDuration_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DurationBase <em>Duration Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Duration Base</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationBase
	 * @generated
	 */
	EClass getDurationBase();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DurationBase#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationBase#getGroup()
	 * @see #getDurationBase()
	 * @generated
	 */
	EAttribute getDurationBase_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DurationBase#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Duration</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationBase#getDuration()
	 * @see #getDurationBase()
	 * @generated
	 */
	EReference getDurationBase_Duration();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.DurationBase#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationBase#getDescription()
	 * @see #getDurationBase()
	 * @generated
	 */
	EReference getDurationBase_Description();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DurationBase#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationBase#getAny()
	 * @see #getDurationBase()
	 * @generated
	 */
	EAttribute getDurationBase_Any();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DurationValue <em>Duration Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Duration Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationValue
	 * @generated
	 */
	EClass getDurationValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DurationValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationValue#getValue()
	 * @see #getDurationValue()
	 * @generated
	 */
	EAttribute getDurationValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.DurationValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationValue#getUniqueElementName()
	 * @see #getDurationValue()
	 * @generated
	 */
	EAttribute getDurationValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DurationValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DurationValue#getAnyAttribute()
	 * @see #getDurationValue()
	 * @generated
	 */
	EAttribute getDurationValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Educational <em>Educational</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Educational</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational
	 * @generated
	 */
	EClass getEducational();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Educational#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getGroup()
	 * @see #getEducational()
	 * @generated
	 */
	EAttribute getEducational_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getInteractivityType <em>Interactivity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interactivity Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getInteractivityType()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_InteractivityType();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getLearningResourceType <em>Learning Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Learning Resource Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getLearningResourceType()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_LearningResourceType();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getInteractivityLevel <em>Interactivity Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interactivity Level</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getInteractivityLevel()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_InteractivityLevel();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getSemanticDensity <em>Semantic Density</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Semantic Density</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getSemanticDensity()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_SemanticDensity();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getIntendedEndUserRole <em>Intended End User Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intended End User Role</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getIntendedEndUserRole()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_IntendedEndUserRole();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getContext()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_Context();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getTypicalAgeRange <em>Typical Age Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Typical Age Range</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getTypicalAgeRange()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_TypicalAgeRange();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getDifficulty <em>Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Difficulty</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getDifficulty()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_Difficulty();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getTypicalLearningTime <em>Typical Learning Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Typical Learning Time</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getTypicalLearningTime()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_TypicalLearningTime();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getDescription()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Educational#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Language</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getLanguage()
	 * @see #getEducational()
	 * @generated
	 */
	EReference getEducational_Language();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Educational#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getAny()
	 * @see #getEducational()
	 * @generated
	 */
	EAttribute getEducational_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Educational#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Educational#getAnyAttribute()
	 * @see #getEducational()
	 * @generated
	 */
	EAttribute getEducational_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Entity#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entity#getUniqueElementName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Entry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entry
	 * @generated
	 */
	EClass getEntry();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Entry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entry#getValue()
	 * @see #getEntry()
	 * @generated
	 */
	EAttribute getEntry_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Entry#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entry#getUniqueElementName()
	 * @see #getEntry()
	 * @generated
	 */
	EAttribute getEntry_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Entry#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Entry#getAnyAttribute()
	 * @see #getEntry()
	 * @generated
	 */
	EAttribute getEntry_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.EntryTaxon <em>Entry Taxon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Taxon</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.EntryTaxon
	 * @generated
	 */
	EClass getEntryTaxon();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.EntryTaxon#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.EntryTaxon#getUniqueElementName()
	 * @see #getEntryTaxon()
	 * @generated
	 */
	EAttribute getEntryTaxon_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Format <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Format</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Format
	 * @generated
	 */
	EClass getFormat();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Format#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Format#getValue()
	 * @see #getFormat()
	 * @generated
	 */
	EAttribute getFormat_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Format#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Format#getAnyAttribute()
	 * @see #getFormat()
	 * @generated
	 */
	EAttribute getFormat_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.General <em>General</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>General</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General
	 * @generated
	 */
	EClass getGeneral();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.General#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getGroup()
	 * @see #getGeneral()
	 * @generated
	 */
	EAttribute getGeneral_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Identifier</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getIdentifier()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Identifier();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Title</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getTitle()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Title();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Language</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getLanguage()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Language();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getDescription()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getKeyword <em>Keyword</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Keyword</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getKeyword()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Keyword();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getCoverage <em>Coverage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Coverage</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getCoverage()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Coverage();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getStructure <em>Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Structure</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getStructure()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_Structure();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.General#getAggregationLevel <em>Aggregation Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aggregation Level</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getAggregationLevel()
	 * @see #getGeneral()
	 * @generated
	 */
	EReference getGeneral_AggregationLevel();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.General#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getAny()
	 * @see #getGeneral()
	 * @generated
	 */
	EAttribute getGeneral_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.General#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getUniqueElementName()
	 * @see #getGeneral()
	 * @generated
	 */
	EAttribute getGeneral_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.General#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.General#getAnyAttribute()
	 * @see #getGeneral()
	 * @generated
	 */
	EAttribute getGeneral_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Id <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Id
	 * @generated
	 */
	EClass getId();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Id#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Id#getValue()
	 * @see #getId()
	 * @generated
	 */
	EAttribute getId_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Id#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Id#getUniqueElementName()
	 * @see #getId()
	 * @generated
	 */
	EAttribute getId_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Id#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Id#getAnyAttribute()
	 * @see #getId()
	 * @generated
	 */
	EAttribute getId_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Identifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifier</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier
	 * @generated
	 */
	EClass getIdentifier();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Identifier#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier#getGroup()
	 * @see #getIdentifier()
	 * @generated
	 */
	EAttribute getIdentifier_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Identifier#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Catalog</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier#getCatalog()
	 * @see #getIdentifier()
	 * @generated
	 */
	EReference getIdentifier_Catalog();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Identifier#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entry</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier#getEntry()
	 * @see #getIdentifier()
	 * @generated
	 */
	EReference getIdentifier_Entry();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Identifier#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier#getAny()
	 * @see #getIdentifier()
	 * @generated
	 */
	EAttribute getIdentifier_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Identifier#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Identifier#getAnyAttribute()
	 * @see #getIdentifier()
	 * @generated
	 */
	EAttribute getIdentifier_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks <em>Installation Remarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Installation Remarks</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InstallationRemarks
	 * @generated
	 */
	EClass getInstallationRemarks();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InstallationRemarks#getUniqueElementName()
	 * @see #getInstallationRemarks()
	 * @generated
	 */
	EAttribute getInstallationRemarks_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRole <em>Intended End User Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intended End User Role</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRole
	 * @generated
	 */
	EClass getIntendedEndUserRole();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue <em>Intended End User Role Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intended End User Role Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue
	 * @generated
	 */
	EClass getIntendedEndUserRoleValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getValue()
	 * @see #getIntendedEndUserRoleValue()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getUniqueElementName()
	 * @see #getIntendedEndUserRoleValue()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValue#getAnyAttribute()
	 * @see #getIntendedEndUserRoleValue()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab <em>Intended End User Role Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intended End User Role Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab
	 * @generated
	 */
	EClass getIntendedEndUserRoleVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getGroup()
	 * @see #getIntendedEndUserRoleVocab()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getSource()
	 * @see #getIntendedEndUserRoleVocab()
	 * @generated
	 */
	EReference getIntendedEndUserRoleVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getValue()
	 * @see #getIntendedEndUserRoleVocab()
	 * @generated
	 */
	EReference getIntendedEndUserRoleVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getAny()
	 * @see #getIntendedEndUserRoleVocab()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleVocab#getAnyAttribute()
	 * @see #getIntendedEndUserRoleVocab()
	 * @generated
	 */
	EAttribute getIntendedEndUserRoleVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevel <em>Interactivity Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Level</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevel
	 * @generated
	 */
	EClass getInteractivityLevel();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevel#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevel#getUniqueElementName()
	 * @see #getInteractivityLevel()
	 * @generated
	 */
	EAttribute getInteractivityLevel_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValue <em>Interactivity Level Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Level Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValue
	 * @generated
	 */
	EClass getInteractivityLevelValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getValue()
	 * @see #getInteractivityLevelValue()
	 * @generated
	 */
	EAttribute getInteractivityLevelValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getUniqueElementName()
	 * @see #getInteractivityLevelValue()
	 * @generated
	 */
	EAttribute getInteractivityLevelValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValue#getAnyAttribute()
	 * @see #getInteractivityLevelValue()
	 * @generated
	 */
	EAttribute getInteractivityLevelValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab <em>Interactivity Level Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Level Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab
	 * @generated
	 */
	EClass getInteractivityLevelVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getGroup()
	 * @see #getInteractivityLevelVocab()
	 * @generated
	 */
	EAttribute getInteractivityLevelVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getSource()
	 * @see #getInteractivityLevelVocab()
	 * @generated
	 */
	EReference getInteractivityLevelVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getValue()
	 * @see #getInteractivityLevelVocab()
	 * @generated
	 */
	EReference getInteractivityLevelVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getAny()
	 * @see #getInteractivityLevelVocab()
	 * @generated
	 */
	EAttribute getInteractivityLevelVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelVocab#getAnyAttribute()
	 * @see #getInteractivityLevelVocab()
	 * @generated
	 */
	EAttribute getInteractivityLevelVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityType <em>Interactivity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityType
	 * @generated
	 */
	EClass getInteractivityType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityType#getUniqueElementName()
	 * @see #getInteractivityType()
	 * @generated
	 */
	EAttribute getInteractivityType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Keyword <em>Keyword</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Keyword</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Keyword
	 * @generated
	 */
	EClass getKeyword();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Keyword#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Keyword#getId()
	 * @see #getKeyword()
	 * @generated
	 */
	EAttribute getKeyword_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Keyword#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Keyword#getSource()
	 * @see #getKeyword()
	 * @generated
	 */
	EAttribute getKeyword_Source();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.KindValue <em>Kind Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kind Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValue
	 * @generated
	 */
	EClass getKindValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.KindValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValue#getValue()
	 * @see #getKindValue()
	 * @generated
	 */
	EAttribute getKindValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.KindValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValue#getUniqueElementName()
	 * @see #getKindValue()
	 * @generated
	 */
	EAttribute getKindValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.KindValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValue#getAnyAttribute()
	 * @see #getKindValue()
	 * @generated
	 */
	EAttribute getKindValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LangString <em>Lang String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lang String</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LangString
	 * @generated
	 */
	EClass getLangString();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LangString#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LangString#getValue()
	 * @see #getLangString()
	 * @generated
	 */
	EAttribute getLangString_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LangString#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LangString#getLanguage()
	 * @see #getLangString()
	 * @generated
	 */
	EAttribute getLangString_Language();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LangString#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LangString#getAnyAttribute()
	 * @see #getLangString()
	 * @generated
	 */
	EAttribute getLangString_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LanguageString <em>Language String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language String</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageString
	 * @generated
	 */
	EClass getLanguageString();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LanguageString#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageString#getGroup()
	 * @see #getLanguageString()
	 * @generated
	 */
	EAttribute getLanguageString_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LanguageString#getString <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>String</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageString#getString()
	 * @see #getLanguageString()
	 * @generated
	 */
	EReference getLanguageString_String();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LanguageString#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageString#getAny()
	 * @see #getLanguageString()
	 * @generated
	 */
	EAttribute getLanguageString_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LanguageString#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageString#getAnyAttribute()
	 * @see #getLanguageString()
	 * @generated
	 */
	EAttribute getLanguageString_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.lom.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.lom.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.lom.DocumentRoot#getLom <em>Lom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lom</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DocumentRoot#getLom()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Lom();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValue <em>Interactivity Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Type Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValue
	 * @generated
	 */
	EClass getInteractivityTypeValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getValue()
	 * @see #getInteractivityTypeValue()
	 * @generated
	 */
	EAttribute getInteractivityTypeValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getUniqueElementName()
	 * @see #getInteractivityTypeValue()
	 * @generated
	 */
	EAttribute getInteractivityTypeValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValue#getAnyAttribute()
	 * @see #getInteractivityTypeValue()
	 * @generated
	 */
	EAttribute getInteractivityTypeValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab <em>Interactivity Type Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interactivity Type Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab
	 * @generated
	 */
	EClass getInteractivityTypeVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getGroup()
	 * @see #getInteractivityTypeVocab()
	 * @generated
	 */
	EAttribute getInteractivityTypeVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getSource()
	 * @see #getInteractivityTypeVocab()
	 * @generated
	 */
	EReference getInteractivityTypeVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getValue()
	 * @see #getInteractivityTypeVocab()
	 * @generated
	 */
	EReference getInteractivityTypeVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getAny()
	 * @see #getInteractivityTypeVocab()
	 * @generated
	 */
	EAttribute getInteractivityTypeVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeVocab#getAnyAttribute()
	 * @see #getInteractivityTypeVocab()
	 * @generated
	 */
	EAttribute getInteractivityTypeVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Kind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kind</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Kind
	 * @generated
	 */
	EClass getKind();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Kind#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Kind#getUniqueElementName()
	 * @see #getKind()
	 * @generated
	 */
	EAttribute getKind_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.KindVocab <em>Kind Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kind Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab
	 * @generated
	 */
	EClass getKindVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.KindVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab#getGroup()
	 * @see #getKindVocab()
	 * @generated
	 */
	EAttribute getKindVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.KindVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab#getSource()
	 * @see #getKindVocab()
	 * @generated
	 */
	EReference getKindVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.KindVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab#getValue()
	 * @see #getKindVocab()
	 * @generated
	 */
	EReference getKindVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.KindVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab#getAny()
	 * @see #getKindVocab()
	 * @generated
	 */
	EAttribute getKindVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.KindVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindVocab#getAnyAttribute()
	 * @see #getKindVocab()
	 * @generated
	 */
	EAttribute getKindVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Language
	 * @generated
	 */
	EClass getLanguage();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Language#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Language#getUniqueElementName()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LanguageId <em>Language Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language Id</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageId
	 * @generated
	 */
	EClass getLanguageId();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LanguageId#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageId#getValue()
	 * @see #getLanguageId()
	 * @generated
	 */
	EAttribute getLanguageId_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LanguageId#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LanguageId#getAnyAttribute()
	 * @see #getLanguageId()
	 * @generated
	 */
	EAttribute getLanguageId_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LearningResourceType <em>Learning Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Learning Resource Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceType
	 * @generated
	 */
	EClass getLearningResourceType();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue <em>Learning Resource Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Learning Resource Type Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue
	 * @generated
	 */
	EClass getLearningResourceTypeValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getValue()
	 * @see #getLearningResourceTypeValue()
	 * @generated
	 */
	EAttribute getLearningResourceTypeValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getUniqueElementName()
	 * @see #getLearningResourceTypeValue()
	 * @generated
	 */
	EAttribute getLearningResourceTypeValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValue#getAnyAttribute()
	 * @see #getLearningResourceTypeValue()
	 * @generated
	 */
	EAttribute getLearningResourceTypeValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab <em>Learning Resource Type Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Learning Resource Type Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab
	 * @generated
	 */
	EClass getLearningResourceTypeVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getGroup()
	 * @see #getLearningResourceTypeVocab()
	 * @generated
	 */
	EAttribute getLearningResourceTypeVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getSource()
	 * @see #getLearningResourceTypeVocab()
	 * @generated
	 */
	EReference getLearningResourceTypeVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getValue()
	 * @see #getLearningResourceTypeVocab()
	 * @generated
	 */
	EReference getLearningResourceTypeVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getAny()
	 * @see #getLearningResourceTypeVocab()
	 * @generated
	 */
	EAttribute getLearningResourceTypeVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeVocab#getAnyAttribute()
	 * @see #getLearningResourceTypeVocab()
	 * @generated
	 */
	EAttribute getLearningResourceTypeVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.LifeCycle <em>Life Cycle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Life Cycle</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle
	 * @generated
	 */
	EClass getLifeCycle();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getGroup()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EAttribute getLifeCycle_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getVersion()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EReference getLifeCycle_Version();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Status</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getStatus()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EReference getLifeCycle_Status();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getContribute <em>Contribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getContribute()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EReference getLifeCycle_Contribute();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getAny()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EAttribute getLifeCycle_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getUniqueElementName()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EAttribute getLifeCycle_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.LifeCycle#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LifeCycle#getAnyAttribute()
	 * @see #getLifeCycle()
	 * @generated
	 */
	EAttribute getLifeCycle_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Location#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Location#getValue()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Location#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Location#getAnyAttribute()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Lom <em>Lom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lom</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom
	 * @generated
	 */
	EClass getLom();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Lom#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getGroup()
	 * @see #getLom()
	 * @generated
	 */
	EAttribute getLom_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getGeneral <em>General</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>General</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getGeneral()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_General();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getLifeCycle <em>Life Cycle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Life Cycle</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getLifeCycle()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_LifeCycle();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getMetaMetadata <em>Meta Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Meta Metadata</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getMetaMetadata()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_MetaMetadata();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getTechnical <em>Technical</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Technical</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getTechnical()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Technical();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getEducational <em>Educational</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Educational</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getEducational()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Educational();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getRights <em>Rights</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rights</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getRights()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Rights();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getRelation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Relation</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getRelation()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Relation();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getAnnotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotation</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getAnnotation()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Annotation();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getClassification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classification</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getClassification()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_Classification();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getHealthcareMetadata <em>Healthcare Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Healthcare Metadata</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getHealthcareMetadata()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_HealthcareMetadata();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Lom#getCustomElements <em>Custom Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Custom Elements</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getCustomElements()
	 * @see #getLom()
	 * @generated
	 */
	EReference getLom_CustomElements();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Lom#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Lom#getAnyAttribute()
	 * @see #getLom()
	 * @generated
	 */
	EAttribute getLom_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.MaximumVersion <em>Maximum Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Maximum Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MaximumVersion
	 * @generated
	 */
	EClass getMaximumVersion();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MaximumVersion#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MaximumVersion#getValue()
	 * @see #getMaximumVersion()
	 * @generated
	 */
	EAttribute getMaximumVersion_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MaximumVersion#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MaximumVersion#getUniqueElementName()
	 * @see #getMaximumVersion()
	 * @generated
	 */
	EAttribute getMaximumVersion_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MaximumVersion#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MaximumVersion#getAnyAttribute()
	 * @see #getMaximumVersion()
	 * @generated
	 */
	EAttribute getMaximumVersion_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.MetadataSchema <em>Metadata Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Schema</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetadataSchema
	 * @generated
	 */
	EClass getMetadataSchema();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MetadataSchema#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetadataSchema#getValue()
	 * @see #getMetadataSchema()
	 * @generated
	 */
	EAttribute getMetadataSchema_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MetadataSchema#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetadataSchema#getAnyAttribute()
	 * @see #getMetadataSchema()
	 * @generated
	 */
	EAttribute getMetadataSchema_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata <em>Meta Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Metadata</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata
	 * @generated
	 */
	EClass getMetaMetadata();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getGroup()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EAttribute getMetaMetadata_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Identifier</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getIdentifier()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EReference getMetaMetadata_Identifier();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getContribute <em>Contribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getContribute()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EReference getMetaMetadata_Contribute();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getMetadataSchema <em>Metadata Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metadata Schema</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getMetadataSchema()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EReference getMetaMetadata_MetadataSchema();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Language</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getLanguage()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EReference getMetaMetadata_Language();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getAny()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EAttribute getMetaMetadata_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getUniqueElementName()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EAttribute getMetaMetadata_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MetaMetadata#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MetaMetadata#getAnyAttribute()
	 * @see #getMetaMetadata()
	 * @generated
	 */
	EAttribute getMetaMetadata_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.MinimumVersion <em>Minimum Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Minimum Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MinimumVersion
	 * @generated
	 */
	EClass getMinimumVersion();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MinimumVersion#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MinimumVersion#getValue()
	 * @see #getMinimumVersion()
	 * @generated
	 */
	EAttribute getMinimumVersion_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.MinimumVersion#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MinimumVersion#getUniqueElementName()
	 * @see #getMinimumVersion()
	 * @generated
	 */
	EAttribute getMinimumVersion_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.MinimumVersion#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.MinimumVersion#getAnyAttribute()
	 * @see #getMinimumVersion()
	 * @generated
	 */
	EAttribute getMinimumVersion_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Name <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Name
	 * @generated
	 */
	EClass getName_();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Name#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Name#getUniqueElementName()
	 * @see #getName_()
	 * @generated
	 */
	EAttribute getName_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.NameValue <em>Name Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValue
	 * @generated
	 */
	EClass getNameValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.NameValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValue#getValue()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.NameValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValue#getUniqueElementName()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.NameValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValue#getAnyAttribute()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.NameVocab <em>Name Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab
	 * @generated
	 */
	EClass getNameVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.NameVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab#getGroup()
	 * @see #getNameVocab()
	 * @generated
	 */
	EAttribute getNameVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.NameVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab#getSource()
	 * @see #getNameVocab()
	 * @generated
	 */
	EReference getNameVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.NameVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab#getValue()
	 * @see #getNameVocab()
	 * @generated
	 */
	EReference getNameVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.NameVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab#getAny()
	 * @see #getNameVocab()
	 * @generated
	 */
	EAttribute getNameVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.NameVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameVocab#getAnyAttribute()
	 * @see #getNameVocab()
	 * @generated
	 */
	EAttribute getNameVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.OrComposite <em>Or Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or Composite</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite
	 * @generated
	 */
	EClass getOrComposite();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getGroup()
	 * @see #getOrComposite()
	 * @generated
	 */
	EAttribute getOrComposite_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getType()
	 * @see #getOrComposite()
	 * @generated
	 */
	EReference getOrComposite_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getName()
	 * @see #getOrComposite()
	 * @generated
	 */
	EReference getOrComposite_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getMinimumVersion <em>Minimum Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Minimum Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getMinimumVersion()
	 * @see #getOrComposite()
	 * @generated
	 */
	EReference getOrComposite_MinimumVersion();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getMaximumVersion <em>Maximum Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Maximum Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getMaximumVersion()
	 * @see #getOrComposite()
	 * @generated
	 */
	EReference getOrComposite_MaximumVersion();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getAny()
	 * @see #getOrComposite()
	 * @generated
	 */
	EAttribute getOrComposite_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.OrComposite#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OrComposite#getAnyAttribute()
	 * @see #getOrComposite()
	 * @generated
	 */
	EAttribute getOrComposite_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.OtherPlatformRequirements <em>Other Platform Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Platform Requirements</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.OtherPlatformRequirements
	 * @generated
	 */
	EClass getOtherPlatformRequirements();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Purpose <em>Purpose</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Purpose</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Purpose
	 * @generated
	 */
	EClass getPurpose();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Purpose#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Purpose#getUniqueElementName()
	 * @see #getPurpose()
	 * @generated
	 */
	EAttribute getPurpose_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.PurposeValue <em>Purpose Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Purpose Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValue
	 * @generated
	 */
	EClass getPurposeValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.PurposeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValue#getValue()
	 * @see #getPurposeValue()
	 * @generated
	 */
	EAttribute getPurposeValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.PurposeValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValue#getUniqueElementName()
	 * @see #getPurposeValue()
	 * @generated
	 */
	EAttribute getPurposeValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.PurposeValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValue#getAnyAttribute()
	 * @see #getPurposeValue()
	 * @generated
	 */
	EAttribute getPurposeValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab <em>Purpose Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Purpose Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab
	 * @generated
	 */
	EClass getPurposeVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab#getGroup()
	 * @see #getPurposeVocab()
	 * @generated
	 */
	EAttribute getPurposeVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab#getSource()
	 * @see #getPurposeVocab()
	 * @generated
	 */
	EReference getPurposeVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab#getValue()
	 * @see #getPurposeVocab()
	 * @generated
	 */
	EReference getPurposeVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab#getAny()
	 * @see #getPurposeVocab()
	 * @generated
	 */
	EAttribute getPurposeVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.PurposeVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeVocab#getAnyAttribute()
	 * @see #getPurposeVocab()
	 * @generated
	 */
	EAttribute getPurposeVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Relation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relation</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation
	 * @generated
	 */
	EClass getRelation();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Relation#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation#getGroup()
	 * @see #getRelation()
	 * @generated
	 */
	EAttribute getRelation_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Relation#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Kind</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation#getKind()
	 * @see #getRelation()
	 * @generated
	 */
	EReference getRelation_Kind();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Relation#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation#getResource()
	 * @see #getRelation()
	 * @generated
	 */
	EReference getRelation_Resource();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Relation#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation#getAny()
	 * @see #getRelation()
	 * @generated
	 */
	EAttribute getRelation_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Relation#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Relation#getAnyAttribute()
	 * @see #getRelation()
	 * @generated
	 */
	EAttribute getRelation_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Requirement
	 * @generated
	 */
	EClass getRequirement();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Requirement#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Requirement#getGroup()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Requirement#getOrComposite <em>Or Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Or Composite</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Requirement#getOrComposite()
	 * @see #getRequirement()
	 * @generated
	 */
	EReference getRequirement_OrComposite();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Requirement#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Requirement#getAny()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Requirement#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Requirement#getAnyAttribute()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Resource#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getGroup()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Resource#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Identifier</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getIdentifier()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Identifier();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Resource#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getDescription()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Description();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Resource#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getAny()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Resource#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getUniqueElementName()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Resource#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Resource#getAnyAttribute()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Rights <em>Rights</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rights</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights
	 * @generated
	 */
	EClass getRights();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Rights#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getGroup()
	 * @see #getRights()
	 * @generated
	 */
	EAttribute getRights_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Rights#getCost <em>Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cost</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getCost()
	 * @see #getRights()
	 * @generated
	 */
	EReference getRights_Cost();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Rights#getCopyrightAndOtherRestrictions <em>Copyright And Other Restrictions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Copyright And Other Restrictions</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getCopyrightAndOtherRestrictions()
	 * @see #getRights()
	 * @generated
	 */
	EReference getRights_CopyrightAndOtherRestrictions();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Rights#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Description</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getDescription()
	 * @see #getRights()
	 * @generated
	 */
	EReference getRights_Description();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Rights#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getAny()
	 * @see #getRights()
	 * @generated
	 */
	EAttribute getRights_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Rights#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getUniqueElementName()
	 * @see #getRights()
	 * @generated
	 */
	EAttribute getRights_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Rights#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Rights#getAnyAttribute()
	 * @see #getRights()
	 * @generated
	 */
	EAttribute getRights_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Role#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Role#getUniqueElementName()
	 * @see #getRole()
	 * @generated
	 */
	EAttribute getRole_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.RoleMeta <em>Role Meta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Meta</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMeta
	 * @generated
	 */
	EClass getRoleMeta();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.RoleMeta#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMeta#getUniqueElementName()
	 * @see #getRoleMeta()
	 * @generated
	 */
	EAttribute getRoleMeta_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValue <em>Role Meta Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Meta Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValue
	 * @generated
	 */
	EClass getRoleMetaValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValue#getValue()
	 * @see #getRoleMetaValue()
	 * @generated
	 */
	EAttribute getRoleMetaValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValue#getUniqueElementName()
	 * @see #getRoleMetaValue()
	 * @generated
	 */
	EAttribute getRoleMetaValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValue#getAnyAttribute()
	 * @see #getRoleMetaValue()
	 * @generated
	 */
	EAttribute getRoleMetaValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab <em>Role Meta Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Meta Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab
	 * @generated
	 */
	EClass getRoleMetaVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getGroup()
	 * @see #getRoleMetaVocab()
	 * @generated
	 */
	EAttribute getRoleMetaVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getSource()
	 * @see #getRoleMetaVocab()
	 * @generated
	 */
	EReference getRoleMetaVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getValue()
	 * @see #getRoleMetaVocab()
	 * @generated
	 */
	EReference getRoleMetaVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getAny()
	 * @see #getRoleMetaVocab()
	 * @generated
	 */
	EAttribute getRoleMetaVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaVocab#getAnyAttribute()
	 * @see #getRoleMetaVocab()
	 * @generated
	 */
	EAttribute getRoleMetaVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.RoleValue <em>Role Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValue
	 * @generated
	 */
	EClass getRoleValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.RoleValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValue#getValue()
	 * @see #getRoleValue()
	 * @generated
	 */
	EAttribute getRoleValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.RoleValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValue#getUniqueElementName()
	 * @see #getRoleValue()
	 * @generated
	 */
	EAttribute getRoleValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValue#getAnyAttribute()
	 * @see #getRoleValue()
	 * @generated
	 */
	EAttribute getRoleValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.RoleVocab <em>Role Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab
	 * @generated
	 */
	EClass getRoleVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab#getGroup()
	 * @see #getRoleVocab()
	 * @generated
	 */
	EAttribute getRoleVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.RoleVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab#getSource()
	 * @see #getRoleVocab()
	 * @generated
	 */
	EReference getRoleVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.RoleVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab#getValue()
	 * @see #getRoleVocab()
	 * @generated
	 */
	EReference getRoleVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab#getAny()
	 * @see #getRoleVocab()
	 * @generated
	 */
	EAttribute getRoleVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.RoleVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleVocab#getAnyAttribute()
	 * @see #getRoleVocab()
	 * @generated
	 */
	EAttribute getRoleVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.SemanticDensity <em>Semantic Density</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Semantic Density</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensity
	 * @generated
	 */
	EClass getSemanticDensity();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.SemanticDensity#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensity#getUniqueElementName()
	 * @see #getSemanticDensity()
	 * @generated
	 */
	EAttribute getSemanticDensity_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValue <em>Semantic Density Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Semantic Density Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValue
	 * @generated
	 */
	EClass getSemanticDensityValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getValue()
	 * @see #getSemanticDensityValue()
	 * @generated
	 */
	EAttribute getSemanticDensityValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getUniqueElementName()
	 * @see #getSemanticDensityValue()
	 * @generated
	 */
	EAttribute getSemanticDensityValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValue#getAnyAttribute()
	 * @see #getSemanticDensityValue()
	 * @generated
	 */
	EAttribute getSemanticDensityValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab <em>Semantic Density Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Semantic Density Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab
	 * @generated
	 */
	EClass getSemanticDensityVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getGroup()
	 * @see #getSemanticDensityVocab()
	 * @generated
	 */
	EAttribute getSemanticDensityVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getSource()
	 * @see #getSemanticDensityVocab()
	 * @generated
	 */
	EReference getSemanticDensityVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getValue()
	 * @see #getSemanticDensityVocab()
	 * @generated
	 */
	EReference getSemanticDensityVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getAny()
	 * @see #getSemanticDensityVocab()
	 * @generated
	 */
	EAttribute getSemanticDensityVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityVocab#getAnyAttribute()
	 * @see #getSemanticDensityVocab()
	 * @generated
	 */
	EAttribute getSemanticDensityVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Size <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Size</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Size
	 * @generated
	 */
	EClass getSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Size#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Size#getValue()
	 * @see #getSize()
	 * @generated
	 */
	EAttribute getSize_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Size#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Size#getUniqueElementName()
	 * @see #getSize()
	 * @generated
	 */
	EAttribute getSize_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Size#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Size#getAnyAttribute()
	 * @see #getSize()
	 * @generated
	 */
	EAttribute getSize_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Source#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Source#getUniqueElementName()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.SourceValue <em>Source Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValue
	 * @generated
	 */
	EClass getSourceValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.SourceValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValue#getValue()
	 * @see #getSourceValue()
	 * @generated
	 */
	EAttribute getSourceValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.SourceValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValue#getUniqueElementName()
	 * @see #getSourceValue()
	 * @generated
	 */
	EAttribute getSourceValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.SourceValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValue#getAnyAttribute()
	 * @see #getSourceValue()
	 * @generated
	 */
	EAttribute getSourceValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Status <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Status
	 * @generated
	 */
	EClass getStatus();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Status#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Status#getUniqueElementName()
	 * @see #getStatus()
	 * @generated
	 */
	EAttribute getStatus_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.StatusValue <em>Status Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValue
	 * @generated
	 */
	EClass getStatusValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.StatusValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValue#getValue()
	 * @see #getStatusValue()
	 * @generated
	 */
	EAttribute getStatusValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.StatusValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValue#getUniqueElementName()
	 * @see #getStatusValue()
	 * @generated
	 */
	EAttribute getStatusValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StatusValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValue#getAnyAttribute()
	 * @see #getStatusValue()
	 * @generated
	 */
	EAttribute getStatusValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.StatusVocab <em>Status Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab
	 * @generated
	 */
	EClass getStatusVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StatusVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab#getGroup()
	 * @see #getStatusVocab()
	 * @generated
	 */
	EAttribute getStatusVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.StatusVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab#getSource()
	 * @see #getStatusVocab()
	 * @generated
	 */
	EReference getStatusVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.StatusVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab#getValue()
	 * @see #getStatusVocab()
	 * @generated
	 */
	EReference getStatusVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StatusVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab#getAny()
	 * @see #getStatusVocab()
	 * @generated
	 */
	EAttribute getStatusVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StatusVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusVocab#getAnyAttribute()
	 * @see #getStatusVocab()
	 * @generated
	 */
	EAttribute getStatusVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Structure <em>Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structure</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Structure
	 * @generated
	 */
	EClass getStructure();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Structure#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Structure#getUniqueElementName()
	 * @see #getStructure()
	 * @generated
	 */
	EAttribute getStructure_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.StructureValue <em>Structure Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structure Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValue
	 * @generated
	 */
	EClass getStructureValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.StructureValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValue#getValue()
	 * @see #getStructureValue()
	 * @generated
	 */
	EAttribute getStructureValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.StructureValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValue#getUniqueElementName()
	 * @see #getStructureValue()
	 * @generated
	 */
	EAttribute getStructureValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StructureValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValue#getAnyAttribute()
	 * @see #getStructureValue()
	 * @generated
	 */
	EAttribute getStructureValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.StructureVocab <em>Structure Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structure Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab
	 * @generated
	 */
	EClass getStructureVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StructureVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab#getGroup()
	 * @see #getStructureVocab()
	 * @generated
	 */
	EAttribute getStructureVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.StructureVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab#getSource()
	 * @see #getStructureVocab()
	 * @generated
	 */
	EReference getStructureVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.StructureVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab#getValue()
	 * @see #getStructureVocab()
	 * @generated
	 */
	EReference getStructureVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StructureVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab#getAny()
	 * @see #getStructureVocab()
	 * @generated
	 */
	EAttribute getStructureVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.StructureVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureVocab#getAnyAttribute()
	 * @see #getStructureVocab()
	 * @generated
	 */
	EAttribute getStructureVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Taxon <em>Taxon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Taxon</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon
	 * @generated
	 */
	EClass getTaxon();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Taxon#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon#getGroup()
	 * @see #getTaxon()
	 * @generated
	 */
	EAttribute getTaxon_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Taxon#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Id</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon#getId()
	 * @see #getTaxon()
	 * @generated
	 */
	EReference getTaxon_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Taxon#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entry</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon#getEntry()
	 * @see #getTaxon()
	 * @generated
	 */
	EReference getTaxon_Entry();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Taxon#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon#getAny()
	 * @see #getTaxon()
	 * @generated
	 */
	EAttribute getTaxon_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Taxon#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Taxon#getAnyAttribute()
	 * @see #getTaxon()
	 * @generated
	 */
	EAttribute getTaxon_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.TaxonPath <em>Taxon Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Taxon Path</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath
	 * @generated
	 */
	EClass getTaxonPath();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TaxonPath#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath#getGroup()
	 * @see #getTaxonPath()
	 * @generated
	 */
	EAttribute getTaxonPath_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.TaxonPath#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath#getSource()
	 * @see #getTaxonPath()
	 * @generated
	 */
	EReference getTaxonPath_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.TaxonPath#getTaxon <em>Taxon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Taxon</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath#getTaxon()
	 * @see #getTaxonPath()
	 * @generated
	 */
	EReference getTaxonPath_Taxon();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TaxonPath#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath#getAny()
	 * @see #getTaxonPath()
	 * @generated
	 */
	EAttribute getTaxonPath_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TaxonPath#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TaxonPath#getAnyAttribute()
	 * @see #getTaxonPath()
	 * @generated
	 */
	EAttribute getTaxonPath_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Technical <em>Technical</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Technical</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical
	 * @generated
	 */
	EClass getTechnical();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Technical#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getGroup()
	 * @see #getTechnical()
	 * @generated
	 */
	EAttribute getTechnical_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Format</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getFormat()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_Format();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Size</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getSize()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_Size();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Location</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getLocation()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_Location();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getRequirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Requirement</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getRequirement()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_Requirement();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getInstallationRemarks <em>Installation Remarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Installation Remarks</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getInstallationRemarks()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_InstallationRemarks();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getOtherPlatformRequirements <em>Other Platform Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Other Platform Requirements</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getOtherPlatformRequirements()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_OtherPlatformRequirements();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.Technical#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Duration</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getDuration()
	 * @see #getTechnical()
	 * @generated
	 */
	EReference getTechnical_Duration();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Technical#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getAny()
	 * @see #getTechnical()
	 * @generated
	 */
	EAttribute getTechnical_Any();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Technical#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getUniqueElementName()
	 * @see #getTechnical()
	 * @generated
	 */
	EAttribute getTechnical_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.Technical#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Technical#getAnyAttribute()
	 * @see #getTechnical()
	 * @generated
	 */
	EAttribute getTechnical_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Title <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Title</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Title
	 * @generated
	 */
	EClass getTitle();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Title#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Title#getUniqueElementName()
	 * @see #getTitle()
	 * @generated
	 */
	EAttribute getTitle_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Type#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Type#getUniqueElementName()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.TypeValue <em>Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValue
	 * @generated
	 */
	EClass getTypeValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.TypeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValue#getValue()
	 * @see #getTypeValue()
	 * @generated
	 */
	EAttribute getTypeValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.TypeValue#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValue#getUniqueElementName()
	 * @see #getTypeValue()
	 * @generated
	 */
	EAttribute getTypeValue_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TypeValue#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValue#getAnyAttribute()
	 * @see #getTypeValue()
	 * @generated
	 */
	EAttribute getTypeValue_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.TypeVocab <em>Type Vocab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Vocab</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab
	 * @generated
	 */
	EClass getTypeVocab();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TypeVocab#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab#getGroup()
	 * @see #getTypeVocab()
	 * @generated
	 */
	EAttribute getTypeVocab_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.TypeVocab#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab#getSource()
	 * @see #getTypeVocab()
	 * @generated
	 */
	EReference getTypeVocab_Source();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.lom.TypeVocab#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab#getValue()
	 * @see #getTypeVocab()
	 * @generated
	 */
	EReference getTypeVocab_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TypeVocab#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab#getAny()
	 * @see #getTypeVocab()
	 * @generated
	 */
	EAttribute getTypeVocab_Any();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TypeVocab#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeVocab#getAnyAttribute()
	 * @see #getTypeVocab()
	 * @generated
	 */
	EAttribute getTypeVocab_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.TypicalAgeRange <em>Typical Age Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typical Age Range</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypicalAgeRange
	 * @generated
	 */
	EClass getTypicalAgeRange();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.TypicalLearningTime <em>Typical Learning Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typical Learning Time</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypicalLearningTime
	 * @generated
	 */
	EClass getTypicalLearningTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.TypicalLearningTime#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypicalLearningTime#getUniqueElementName()
	 * @see #getTypicalLearningTime()
	 * @generated
	 */
	EAttribute getTypicalLearningTime_UniqueElementName();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.TypicalLearningTime#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypicalLearningTime#getAnyAttribute()
	 * @see #getTypicalLearningTime()
	 * @generated
	 */
	EAttribute getTypicalLearningTime_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.VCard <em>VCard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VCard</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.VCard
	 * @generated
	 */
	EClass getVCard();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.VCard#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.VCard#getValue()
	 * @see #getVCard()
	 * @generated
	 */
	EAttribute getVCard_Value();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.lom.VCard#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.VCard#getAnyAttribute()
	 * @see #getVCard()
	 * @generated
	 */
	EAttribute getVCard_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.lom.Version <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Version
	 * @generated
	 */
	EClass getVersion();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.lom.Version#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.Version#getUniqueElementName()
	 * @see #getVersion()
	 * @generated
	 */
	EAttribute getVersion_UniqueElementName();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValues <em>Aggregation Level Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Aggregation Level Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValues
	 * @generated
	 */
	EEnum getAggregationLevelValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.ContextValues <em>Context Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Context Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValues
	 * @generated
	 */
	EEnum getContextValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues <em>Copyright And Other Restrictions Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Copyright And Other Restrictions Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues
	 * @generated
	 */
	EEnum getCopyrightAndOtherRestrictionsValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.CostValues <em>Cost Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cost Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValues
	 * @generated
	 */
	EEnum getCostValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.DifficultyValues <em>Difficulty Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Difficulty Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValues
	 * @generated
	 */
	EEnum getDifficultyValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues <em>Intended End User Role Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Intended End User Role Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues
	 * @generated
	 */
	EEnum getIntendedEndUserRoleValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValues <em>Interactivity Level Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Interactivity Level Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValues
	 * @generated
	 */
	EEnum getInteractivityLevelValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValues <em>Interactivity Type Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Interactivity Type Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValues
	 * @generated
	 */
	EEnum getInteractivityTypeValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.KindValues <em>Kind Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Kind Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValues
	 * @generated
	 */
	EEnum getKindValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues <em>Learning Resource Type Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Learning Resource Type Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues
	 * @generated
	 */
	EEnum getLearningResourceTypeValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.NameValues <em>Name Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Name Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValues
	 * @generated
	 */
	EEnum getNameValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.PurposeValues <em>Purpose Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Purpose Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValues
	 * @generated
	 */
	EEnum getPurposeValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValues <em>Role Meta Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Role Meta Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValues
	 * @generated
	 */
	EEnum getRoleMetaValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.RoleValues <em>Role Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Role Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValues
	 * @generated
	 */
	EEnum getRoleValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValues <em>Semantic Density Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Semantic Density Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValues
	 * @generated
	 */
	EEnum getSemanticDensityValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.SourceValues <em>Source Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Source Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValues
	 * @generated
	 */
	EEnum getSourceValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.StatusValues <em>Status Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Status Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValues
	 * @generated
	 */
	EEnum getStatusValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.StructureValues <em>Structure Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Structure Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValues
	 * @generated
	 */
	EEnum getStructureValues();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.lom.TypeValues <em>Type Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type Values</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValues
	 * @generated
	 */
	EEnum getTypeValues();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.AggregationLevelValues <em>Aggregation Level Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Aggregation Level Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.AggregationLevelValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.AggregationLevelValues"
	 *        extendedMetaData="name='aggregationLevelValues:Object' baseType='aggregationLevelValues'"
	 * @generated
	 */
	EDataType getAggregationLevelValuesObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Character String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Character String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='CharacterString' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getCharacterString();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.ContextValues <em>Context Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Context Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.ContextValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.ContextValues"
	 *        extendedMetaData="name='contextValues:Object' baseType='contextValues'"
	 * @generated
	 */
	EDataType getContextValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues <em>Copyright And Other Restrictions Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Copyright And Other Restrictions Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.CopyrightAndOtherRestrictionsValues"
	 *        extendedMetaData="name='copyrightAndOtherRestrictionsValues:Object' baseType='copyrightAndOtherRestrictionsValues'"
	 * @generated
	 */
	EDataType getCopyrightAndOtherRestrictionsValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.CostValues <em>Cost Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Cost Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.CostValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.CostValues"
	 *        extendedMetaData="name='costValues:Object' baseType='costValues'"
	 * @generated
	 */
	EDataType getCostValuesObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Date Time String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Date Time String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='DateTimeString' baseType='CharacterString' pattern='([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})(\\-(0[1-9]|1[0-2])(\\-(0[1-9]|[1-2][0-9]|3[0-1])(T([0-1][0-9]|2[0-3])(:[0-5][0-9](:[0-5][0-9](\\.[0-9]{1,}(Z|((\\+|\\-)([0-1][0-9]|2[0-3]):[0-5][0-9]))?)?)?)?)?)?)?'"
	 * @generated
	 */
	EDataType getDateTimeString();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.DifficultyValues <em>Difficulty Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Difficulty Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.DifficultyValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.DifficultyValues"
	 *        extendedMetaData="name='difficultyValues:Object' baseType='difficultyValues'"
	 * @generated
	 */
	EDataType getDifficultyValuesObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Duration String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Duration String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='DurationString' baseType='CharacterString' pattern='P([0-9]{1,}Y){0,1}([0-9]{1,}M){0,1}([0-9]{1,}D){0,1}(T([0-9]{1,}H){0,1}([0-9]{1,}M){0,1}([0-9]{1,}(\\.[0-9]{1,}){0,1}S){0,1}){0,1}'"
	 * @generated
	 */
	EDataType getDurationString();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.InteractivityLevelValues <em>Interactivity Level Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Interactivity Level Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityLevelValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.InteractivityLevelValues"
	 *        extendedMetaData="name='interactivityLevelValues:Object' baseType='interactivityLevelValues'"
	 * @generated
	 */
	EDataType getInteractivityLevelValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues <em>Intended End User Role Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Intended End User Role Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.IntendedEndUserRoleValues"
	 *        extendedMetaData="name='intendedEndUserRoleValues:Object' baseType='intendedEndUserRoleValues'"
	 * @generated
	 */
	EDataType getIntendedEndUserRoleValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.InteractivityTypeValues <em>Interactivity Type Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Interactivity Type Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.InteractivityTypeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.InteractivityTypeValues"
	 *        extendedMetaData="name='interactivityTypeValues:Object' baseType='interactivityTypeValues'"
	 * @generated
	 */
	EDataType getInteractivityTypeValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.KindValues <em>Kind Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Kind Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.KindValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.KindValues"
	 *        extendedMetaData="name='kindValues:Object' baseType='kindValues'"
	 * @generated
	 */
	EDataType getKindValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues <em>Learning Resource Type Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Learning Resource Type Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.LearningResourceTypeValues"
	 *        extendedMetaData="name='learningResourceTypeValues:Object' baseType='learningResourceTypeValues'"
	 * @generated
	 */
	EDataType getLearningResourceTypeValuesObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Mime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Mime Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='MimeType' baseType='CharacterString'"
	 * @generated
	 */
	EDataType getMimeType();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.NameValues <em>Name Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.NameValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.NameValues"
	 *        extendedMetaData="name='nameValues:Object' baseType='nameValues'"
	 * @generated
	 */
	EDataType getNameValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.PurposeValues <em>Purpose Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Purpose Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.PurposeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.PurposeValues"
	 *        extendedMetaData="name='purposeValues:Object' baseType='purposeValues'"
	 * @generated
	 */
	EDataType getPurposeValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.RoleMetaValues <em>Role Meta Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Role Meta Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleMetaValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.RoleMetaValues"
	 *        extendedMetaData="name='roleMetaValues:Object' baseType='roleMetaValues'"
	 * @generated
	 */
	EDataType getRoleMetaValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.RoleValues <em>Role Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Role Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.RoleValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.RoleValues"
	 *        extendedMetaData="name='roleValues:Object' baseType='roleValues'"
	 * @generated
	 */
	EDataType getRoleValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.SemanticDensityValues <em>Semantic Density Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Semantic Density Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SemanticDensityValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.SemanticDensityValues"
	 *        extendedMetaData="name='semanticDensityValues:Object' baseType='semanticDensityValues'"
	 * @generated
	 */
	EDataType getSemanticDensityValuesObject();

	/**
	 * Returns the meta object for data type '{@link java.math.BigInteger <em>Size Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Size Base</em>'.
	 * @see java.math.BigInteger
	 * @model instanceClass="java.math.BigInteger"
	 *        extendedMetaData="name='SizeBase' baseType='http://www.eclipse.org/emf/2003/XMLType#nonNegativeInteger'"
	 * @generated
	 */
	EDataType getSizeBase();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.SourceValues <em>Source Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Source Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.SourceValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.SourceValues"
	 *        extendedMetaData="name='sourceValues:Object' baseType='sourceValues'"
	 * @generated
	 */
	EDataType getSourceValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.StatusValues <em>Status Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Status Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StatusValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.StatusValues"
	 *        extendedMetaData="name='statusValues:Object' baseType='statusValues'"
	 * @generated
	 */
	EDataType getStatusValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.StructureValues <em>Structure Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Structure Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.StructureValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.StructureValues"
	 *        extendedMetaData="name='structureValues:Object' baseType='structureValues'"
	 * @generated
	 */
	EDataType getStructureValuesObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.lom.TypeValues <em>Type Values Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Values Object</em>'.
	 * @see edu.louisiana.caai.lom.model.lom.TypeValues
	 * @model instanceClass="edu.louisiana.caai.lom.model.lom.TypeValues"
	 *        extendedMetaData="name='typeValues:Object' baseType='typeValues'"
	 * @generated
	 */
	EDataType getTypeValuesObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LomFactory getLomFactory();

} //LomPackage
