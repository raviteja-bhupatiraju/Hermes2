/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import edu.louisiana.caai.lom.model.address.AddressPackage;

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
 * 
 *          This work is licensed under the Creative Commons Attribution-ShareAlike
 *          License.  To view a copy of this license, see the file license.txt,
 *          visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *          Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *       
 * 
 *          This component schema defintion defines global element declarations for 
 *          each of the data elements defined in the LOMv1.0 base schema.  This component
 *          schema definition is used to check for the uniqueness of elements declared
 *          to be unique within their parent elements by the presence of the 
 *          "uniqueElementName" attribute.  The XML Schema constraint "unique" is used 
 *          to enforce uniqueness constraints.
 * 
 *          Duplicate declarations are included as comments for completeness.  These 
 *          declarations should remain commented out or they can be removed completely.
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
 *         ** 08/24/2004:  1) Updated those complexTypes that referenced exteding the **
 *         **              LangString complexType to map to the new name for that     **
 *         **              complexType - LanguageString:                              **       
 *         **                 1.4 Description                                         **       
 *         **                 5.10 Description                                        **       
 *         **              2)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **              3)Updated annotation describing file to include language   **
 *         **                updates found in Draft 2 Working Draft 2                 **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Updated "language" elements to remove reference to       **
 *         **                types that were removed (e.g, languageIdOrNone).         **
 *         *****************************************************************************
 *       
 * <!-- end-model-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareFactory
 * @model kind="package"
 * @generated
 */
public interface HealthcarePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "healthcare";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://ns.medbiq.org/lom/extend/v1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hx";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HealthcarePackage eINSTANCE = edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl <em>Activity Location Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityLocationType()
	 * @generated
	 */
	int ACTIVITY_LOCATION_TYPE = 0;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__ID = AddressPackage.ADDRESS_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__ORGANIZATION = AddressPackage.ADDRESS_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Street Address Line</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__STREET_ADDRESS_LINE = AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__CITY = AddressPackage.ADDRESS_TYPE__CITY;

	/**
	 * The feature id for the '<em><b>State Or Province</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__STATE_OR_PROVINCE = AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE;

	/**
	 * The feature id for the '<em><b>Postal Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__POSTAL_CODE = AddressPackage.ADDRESS_TYPE__POSTAL_CODE;

	/**
	 * The feature id for the '<em><b>Region</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__REGION = AddressPackage.ADDRESS_TYPE__REGION;

	/**
	 * The feature id for the '<em><b>District</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__DISTRICT = AddressPackage.ADDRESS_TYPE__DISTRICT;

	/**
	 * The feature id for the '<em><b>Country</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__COUNTRY = AddressPackage.ADDRESS_TYPE__COUNTRY;

	/**
	 * The feature id for the '<em><b>Address Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__ADDRESS_CATEGORY = AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY;

	/**
	 * The feature id for the '<em><b>Restrictions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__RESTRICTIONS = AddressPackage.ADDRESS_TYPE__RESTRICTIONS;

	/**
	 * The feature id for the '<em><b>Room</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__ROOM = AddressPackage.ADDRESS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Building</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__BUILDING = AddressPackage.ADDRESS_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME = AddressPackage.ADDRESS_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Activity Location Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE_FEATURE_COUNT = AddressPackage.ADDRESS_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Activity Location Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_LOCATION_TYPE_OPERATION_COUNT = AddressPackage.ADDRESS_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl <em>Credits Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditsType()
	 * @generated
	 */
	int CREDITS_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Accrediting Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__ACCREDITING_BODY = 0;

	/**
	 * The feature id for the '<em><b>Activity Certification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__ACTIVITY_CERTIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Credit Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__CREDIT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Credit Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__CREDIT_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Pacing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__PACING = 4;

	/**
	 * The feature id for the '<em><b>Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__ACCREDITED_PROVIDER = 5;

	/**
	 * The feature id for the '<em><b>Non Accredited Provider</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__NON_ACCREDITED_PROVIDER = 6;

	/**
	 * The feature id for the '<em><b>Release Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__RELEASE_DATE = 7;

	/**
	 * The feature id for the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__EXPIRATION_DATE = 8;

	/**
	 * The feature id for the '<em><b>Number Of Credits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE__NUMBER_OF_CREDITS = 9;

	/**
	 * The number of structural features of the '<em>Credits Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Credits Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDITS_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.CustomElementsTypeImpl <em>Custom Elements Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.CustomElementsTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCustomElementsType()
	 * @generated
	 */
	int CUSTOM_ELEMENTS_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_ELEMENTS_TYPE__ANY = 0;

	/**
	 * The number of structural features of the '<em>Custom Elements Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_ELEMENTS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Custom Elements Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_ELEMENTS_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 3;

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
	 * The feature id for the '<em><b>Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACCREDITED_PROVIDER = 3;

	/**
	 * The feature id for the '<em><b>Accrediting Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACCREDITING_BODY = 4;

	/**
	 * The feature id for the '<em><b>Activity Certification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY_CERTIFICATION = 5;

	/**
	 * The feature id for the '<em><b>Activity Delivery</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY_DELIVERY = 6;

	/**
	 * The feature id for the '<em><b>Activity Format</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY_FORMAT = 7;

	/**
	 * The feature id for the '<em><b>Activity Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY_LOCATION = 8;

	/**
	 * The feature id for the '<em><b>Activity Sponsorship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP = 9;

	/**
	 * The feature id for the '<em><b>Annotated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ANNOTATED = 10;

	/**
	 * The feature id for the '<em><b>Audience Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUDIENCE_CATEGORY = 11;

	/**
	 * The feature id for the '<em><b>Building</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUILDING = 12;

	/**
	 * The feature id for the '<em><b>Clinical History</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CLINICAL_HISTORY = 13;

	/**
	 * The feature id for the '<em><b>Commercial Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMMERCIAL_SUPPORT = 14;

	/**
	 * The feature id for the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT = 15;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONTACT = 16;

	/**
	 * The feature id for the '<em><b>Credits</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CREDITS = 17;

	/**
	 * The feature id for the '<em><b>Credits Available</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CREDITS_AVAILABLE = 18;

	/**
	 * The feature id for the '<em><b>Credit Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CREDIT_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Credit Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CREDIT_UNIT = 20;

	/**
	 * The feature id for the '<em><b>Custom Elements</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CUSTOM_ELEMENTS = 21;

	/**
	 * The feature id for the '<em><b>End Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__END_DATE_TIME = 22;

	/**
	 * The feature id for the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPIRATION_DATE = 23;

	/**
	 * The feature id for the '<em><b>File Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FILE_HEIGHT = 24;

	/**
	 * The feature id for the '<em><b>File Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FILE_WIDTH = 25;

	/**
	 * The feature id for the '<em><b>Healthcare Asset</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HEALTHCARE_ASSET = 26;

	/**
	 * The feature id for the '<em><b>Healthcare Education</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HEALTHCARE_EDUCATION = 27;

	/**
	 * The feature id for the '<em><b>Healthcare Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HEALTHCARE_METADATA = 28;

	/**
	 * The feature id for the '<em><b>Magnification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAGNIFICATION = 29;

	/**
	 * The feature id for the '<em><b>Medical Image Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE = 30;

	/**
	 * The feature id for the '<em><b>Non Accredited Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER = 31;

	/**
	 * The feature id for the '<em><b>Number Of Credits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NUMBER_OF_CREDITS = 32;

	/**
	 * The feature id for the '<em><b>Off Label Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION = 33;

	/**
	 * The feature id for the '<em><b>Off Label Use</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OFF_LABEL_USE = 34;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ORIENTATION = 35;

	/**
	 * The feature id for the '<em><b>Pacing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PACING = 36;

	/**
	 * The feature id for the '<em><b>Participation Modality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPATION_MODALITY = 37;

	/**
	 * The feature id for the '<em><b>Profession</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROFESSION = 38;

	/**
	 * The feature id for the '<em><b>Reading Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__READING_LEVEL = 39;

	/**
	 * The feature id for the '<em><b>Release Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELEASE_DATE = 40;

	/**
	 * The feature id for the '<em><b>Relevant Financial Relationship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP = 41;

	/**
	 * The feature id for the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE = 42;

	/**
	 * The feature id for the '<em><b>Room</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ROOM = 43;

	/**
	 * The feature id for the '<em><b>Specialty</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SPECIALTY = 44;

	/**
	 * The feature id for the '<em><b>Specimen Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SPECIMEN_TYPE = 45;

	/**
	 * The feature id for the '<em><b>Start Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__START_DATE_TIME = 46;

	/**
	 * The feature id for the '<em><b>Target Audience</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TARGET_AUDIENCE = 47;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 48;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl <em>Asset Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareAssetType()
	 * @generated
	 */
	int HEALTHCARE_ASSET_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Annotated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__ANNOTATED = 0;

	/**
	 * The feature id for the '<em><b>Clinical History</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY = 1;

	/**
	 * The feature id for the '<em><b>Magnification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__MAGNIFICATION = 2;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__ORIENTATION = 3;

	/**
	 * The feature id for the '<em><b>Medical Image Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Specimen Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE = 5;

	/**
	 * The feature id for the '<em><b>File Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__FILE_HEIGHT = 6;

	/**
	 * The feature id for the '<em><b>File Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__FILE_WIDTH = 7;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME = 8;

	/**
	 * The number of structural features of the '<em>Asset Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Asset Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_ASSET_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl <em>Education Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareEducationType()
	 * @generated
	 */
	int HEALTHCARE_EDUCATION_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Expiration Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE = 0;

	/**
	 * The feature id for the '<em><b>Credits Available</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE = 1;

	/**
	 * The feature id for the '<em><b>Credits</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__CREDITS = 2;

	/**
	 * The feature id for the '<em><b>Target Audience</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE = 3;

	/**
	 * The feature id for the '<em><b>Activity Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION = 4;

	/**
	 * The feature id for the '<em><b>Start Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME = 5;

	/**
	 * The feature id for the '<em><b>End Date Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME = 6;

	/**
	 * The feature id for the '<em><b>Activity Sponsorship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP = 7;

	/**
	 * The feature id for the '<em><b>Participation Modality</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY = 8;

	/**
	 * The feature id for the '<em><b>Activity Delivery</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY = 9;

	/**
	 * The feature id for the '<em><b>Activity Format</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT = 10;

	/**
	 * The feature id for the '<em><b>Off Label Use</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE = 11;

	/**
	 * The feature id for the '<em><b>Off Label Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION = 12;

	/**
	 * The feature id for the '<em><b>Commercial Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT = 13;

	/**
	 * The feature id for the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT = 14;

	/**
	 * The feature id for the '<em><b>Relevant Financial Relationship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP = 15;

	/**
	 * The feature id for the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE = 16;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__CONTACT = 17;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME = 18;

	/**
	 * The number of structural features of the '<em>Education Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE_FEATURE_COUNT = 19;

	/**
	 * The number of operations of the '<em>Education Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_EDUCATION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl <em>Metadata Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareMetadataType()
	 * @generated
	 */
	int HEALTHCARE_METADATA_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Healthcare Education</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION = 0;

	/**
	 * The feature id for the '<em><b>Healthcare Asset</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET = 1;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME = 2;

	/**
	 * The number of structural features of the '<em>Metadata Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_METADATA_TYPE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Metadata Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTHCARE_METADATA_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl <em>Target Audience Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getTargetAudienceType()
	 * @generated
	 */
	int TARGET_AUDIENCE_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Audience Category</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Profession</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE__PROFESSION = 1;

	/**
	 * The feature id for the '<em><b>Specialty</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE__SPECIALTY = 2;

	/**
	 * The feature id for the '<em><b>Reading Level</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE__READING_LEVEL = 3;

	/**
	 * The feature id for the '<em><b>Unique Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME = 4;

	/**
	 * The number of structural features of the '<em>Target Audience Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Target Audience Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_AUDIENCE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType <em>Activity Delivery Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityDeliveryType()
	 * @generated
	 */
	int ACTIVITY_DELIVERY_TYPE = 8;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType <em>Activity Sponsorship Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivitySponsorshipType()
	 * @generated
	 */
	int ACTIVITY_SPONSORSHIP_TYPE = 9;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType <em>Audience Category Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getAudienceCategoryType()
	 * @generated
	 */
	int AUDIENCE_CATEGORY_TYPE = 10;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType <em>Credit Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditTypeType()
	 * @generated
	 */
	int CREDIT_TYPE_TYPE = 11;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType <em>Credit Unit Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditUnitType()
	 * @generated
	 */
	int CREDIT_UNIT_TYPE = 12;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType <em>Medical Image Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getMedicalImageTypeType()
	 * @generated
	 */
	int MEDICAL_IMAGE_TYPE_TYPE = 13;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.OrientationType <em>Orientation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getOrientationType()
	 * @generated
	 */
	int ORIENTATION_TYPE = 14;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.PacingType <em>Pacing Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getPacingType()
	 * @generated
	 */
	int PACING_TYPE = 15;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType <em>Participation Modality Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getParticipationModalityType()
	 * @generated
	 */
	int PARTICIPATION_MODALITY_TYPE = 16;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType <em>Specimen Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getSpecimenTypeType()
	 * @generated
	 */
	int SPECIMEN_TYPE_TYPE = 17;

	/**
	 * The meta object id for the '{@link edu.louisiana.caai.lom.model.healthcare.YesNoType <em>Yes No Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getYesNoType()
	 * @generated
	 */
	int YES_NO_TYPE = 18;

	/**
	 * The meta object id for the '<em>Activity Delivery Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityDeliveryTypeObject()
	 * @generated
	 */
	int ACTIVITY_DELIVERY_TYPE_OBJECT = 19;

	/**
	 * The meta object id for the '<em>Activity Sponsorship Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivitySponsorshipTypeObject()
	 * @generated
	 */
	int ACTIVITY_SPONSORSHIP_TYPE_OBJECT = 20;

	/**
	 * The meta object id for the '<em>Audience Category Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getAudienceCategoryTypeObject()
	 * @generated
	 */
	int AUDIENCE_CATEGORY_TYPE_OBJECT = 21;

	/**
	 * The meta object id for the '<em>Credit Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditTypeTypeObject()
	 * @generated
	 */
	int CREDIT_TYPE_TYPE_OBJECT = 22;

	/**
	 * The meta object id for the '<em>Credit Unit Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditUnitTypeObject()
	 * @generated
	 */
	int CREDIT_UNIT_TYPE_OBJECT = 23;

	/**
	 * The meta object id for the '<em>Medical Image Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getMedicalImageTypeTypeObject()
	 * @generated
	 */
	int MEDICAL_IMAGE_TYPE_TYPE_OBJECT = 24;

	/**
	 * The meta object id for the '<em>Orientation Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getOrientationTypeObject()
	 * @generated
	 */
	int ORIENTATION_TYPE_OBJECT = 25;

	/**
	 * The meta object id for the '<em>Pacing Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getPacingTypeObject()
	 * @generated
	 */
	int PACING_TYPE_OBJECT = 26;

	/**
	 * The meta object id for the '<em>Participation Modality Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getParticipationModalityTypeObject()
	 * @generated
	 */
	int PARTICIPATION_MODALITY_TYPE_OBJECT = 27;

	/**
	 * The meta object id for the '<em>Specimen Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getSpecimenTypeTypeObject()
	 * @generated
	 */
	int SPECIMEN_TYPE_TYPE_OBJECT = 28;

	/**
	 * The meta object id for the '<em>Yes No Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getYesNoTypeObject()
	 * @generated
	 */
	int YES_NO_TYPE_OBJECT = 29;

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType <em>Activity Location Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity Location Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityLocationType
	 * @generated
	 */
	EClass getActivityLocationType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getRoom <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Room</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getRoom()
	 * @see #getActivityLocationType()
	 * @generated
	 */
	EAttribute getActivityLocationType_Room();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getBuilding <em>Building</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Building</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getBuilding()
	 * @see #getActivityLocationType()
	 * @generated
	 */
	EAttribute getActivityLocationType_Building();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityLocationType#getUniqueElementName()
	 * @see #getActivityLocationType()
	 * @generated
	 */
	EAttribute getActivityLocationType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType <em>Credits Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Credits Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType
	 * @generated
	 */
	EClass getCreditsType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditingBody <em>Accrediting Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accrediting Body</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditingBody()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_AccreditingBody();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getActivityCertification <em>Activity Certification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activity Certification</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getActivityCertification()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_ActivityCertification();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType <em>Credit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditType()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_CreditType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit <em>Credit Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit Unit</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getCreditUnit()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_CreditUnit();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing <em>Pacing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pacing</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getPacing()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_Pacing();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditedProvider <em>Accredited Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accredited Provider</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getAccreditedProvider()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_AccreditedProvider();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getNonAccreditedProvider <em>Non Accredited Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Non Accredited Provider</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getNonAccreditedProvider()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_NonAccreditedProvider();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getReleaseDate <em>Release Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Release Date</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getReleaseDate()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_ReleaseDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getExpirationDate <em>Expiration Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiration Date</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getExpirationDate()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_ExpirationDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.CreditsType#getNumberOfCredits <em>Number Of Credits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Credits</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditsType#getNumberOfCredits()
	 * @see #getCreditsType()
	 * @generated
	 */
	EAttribute getCreditsType_NumberOfCredits();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.CustomElementsType <em>Custom Elements Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Custom Elements Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CustomElementsType
	 * @generated
	 */
	EClass getCustomElementsType();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.CustomElementsType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CustomElementsType#getAny()
	 * @see #getCustomElementsType()
	 * @generated
	 */
	EAttribute getCustomElementsType_Any();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditedProvider <em>Accredited Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accredited Provider</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditedProvider()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AccreditedProvider();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditingBody <em>Accrediting Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accrediting Body</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAccreditingBody()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AccreditingBody();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityCertification <em>Activity Certification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activity Certification</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityCertification()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ActivityCertification();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityDelivery <em>Activity Delivery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activity Delivery</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityDelivery()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ActivityDelivery();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityFormat <em>Activity Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Activity Format</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityFormat()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ActivityFormat();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityLocation <em>Activity Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Activity Location</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivityLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ActivityLocation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivitySponsorship <em>Activity Sponsorship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activity Sponsorship</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getActivitySponsorship()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ActivitySponsorship();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAnnotated <em>Annotated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Annotated</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAnnotated()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Annotated();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAudienceCategory <em>Audience Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Audience Category</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getAudienceCategory()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AudienceCategory();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getBuilding <em>Building</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Building</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getBuilding()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Building();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getClinicalHistory <em>Clinical History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clinical History</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getClinicalHistory()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ClinicalHistory();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupport <em>Commercial Support</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commercial Support</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupport()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CommercialSupport();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Commercial Support Acknowledgement</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCommercialSupportAcknowledgement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_CommercialSupportAcknowledgement();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getContact()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Contact();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCredits <em>Credits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Credits</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCredits()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Credits();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditsAvailable <em>Credits Available</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credits Available</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditsAvailable()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CreditsAvailable();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditType <em>Credit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CreditType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditUnit <em>Credit Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit Unit</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCreditUnit()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CreditUnit();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCustomElements <em>Custom Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Custom Elements</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getCustomElements()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_CustomElements();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getEndDateTime <em>End Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getEndDateTime()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_EndDateTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getExpirationDate <em>Expiration Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiration Date</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getExpirationDate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ExpirationDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileHeight <em>File Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Height</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileHeight()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_FileHeight();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileWidth <em>File Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Width</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getFileWidth()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_FileWidth();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareAsset <em>Healthcare Asset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Healthcare Asset</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareAsset()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HealthcareAsset();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareEducation <em>Healthcare Education</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Healthcare Education</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareEducation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HealthcareEducation();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareMetadata <em>Healthcare Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Healthcare Metadata</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getHealthcareMetadata()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HealthcareMetadata();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMagnification <em>Magnification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Magnification</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMagnification()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Magnification();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMedicalImageType <em>Medical Image Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Medical Image Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getMedicalImageType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_MedicalImageType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNonAccreditedProvider <em>Non Accredited Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Accredited Provider</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNonAccreditedProvider()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_NonAccreditedProvider();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNumberOfCredits <em>Number Of Credits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Credits</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getNumberOfCredits()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_NumberOfCredits();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelDescription <em>Off Label Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Off Label Description</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelDescription()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OffLabelDescription();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelUse <em>Off Label Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Off Label Use</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOffLabelUse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_OffLabelUse();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getOrientation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Orientation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getPacing <em>Pacing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pacing</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getPacing()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Pacing();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getParticipationModality <em>Participation Modality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Participation Modality</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getParticipationModality()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ParticipationModality();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getProfession <em>Profession</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Profession</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getProfession()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Profession();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReadingLevel <em>Reading Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reading Level</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReadingLevel()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ReadingLevel();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReleaseDate <em>Release Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Release Date</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getReleaseDate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_ReleaseDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relevant Financial Relationship</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationship()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_RelevantFinancialRelationship();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relevant Financial Relationship Disclosure</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRelevantFinancialRelationshipDisclosure()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RelevantFinancialRelationshipDisclosure();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRoom <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Room</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getRoom()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Room();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecialty <em>Specialty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Specialty</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecialty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Specialty();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecimenType <em>Specimen Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specimen Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getSpecimenType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_SpecimenType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getStartDateTime <em>Start Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getStartDateTime()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_StartDateTime();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getTargetAudience <em>Target Audience</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target Audience</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.DocumentRoot#getTargetAudience()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TargetAudience();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType <em>Asset Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asset Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType
	 * @generated
	 */
	EClass getHealthcareAssetType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated <em>Annotated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Annotated</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getAnnotated()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_Annotated();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getClinicalHistory <em>Clinical History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clinical History</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getClinicalHistory()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EReference getHealthcareAssetType_ClinicalHistory();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMagnification <em>Magnification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Magnification</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMagnification()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_Magnification();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getOrientation()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_Orientation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType <em>Medical Image Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Medical Image Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getMedicalImageType()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_MedicalImageType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType <em>Specimen Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specimen Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getSpecimenType()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_SpecimenType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileHeight <em>File Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Height</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileHeight()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_FileHeight();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileWidth <em>File Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Width</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getFileWidth()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_FileWidth();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType#getUniqueElementName()
	 * @see #getHealthcareAssetType()
	 * @generated
	 */
	EAttribute getHealthcareAssetType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType <em>Education Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Education Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType
	 * @generated
	 */
	EClass getHealthcareEducationType();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getExpirationDate <em>Expiration Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiration Date</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getExpirationDate()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_ExpirationDate();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable <em>Credits Available</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credits Available</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCreditsAvailable()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_CreditsAvailable();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCredits <em>Credits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Credits</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCredits()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_Credits();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getTargetAudience <em>Target Audience</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target Audience</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getTargetAudience()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_TargetAudience();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityLocation <em>Activity Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Activity Location</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityLocation()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_ActivityLocation();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getStartDateTime <em>Start Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getStartDateTime()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_StartDateTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getEndDateTime <em>End Date Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date Time</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getEndDateTime()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_EndDateTime();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship <em>Activity Sponsorship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activity Sponsorship</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivitySponsorship()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_ActivitySponsorship();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getParticipationModality <em>Participation Modality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Participation Modality</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getParticipationModality()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_ParticipationModality();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityDelivery <em>Activity Delivery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Activity Delivery</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityDelivery()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_ActivityDelivery();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityFormat <em>Activity Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activity Format</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getActivityFormat()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_ActivityFormat();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse <em>Off Label Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Off Label Use</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelUse()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_OffLabelUse();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelDescription <em>Off Label Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Off Label Description</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getOffLabelDescription()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_OffLabelDescription();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport <em>Commercial Support</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commercial Support</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupport()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_CommercialSupport();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupportAcknowledgement <em>Commercial Support Acknowledgement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Commercial Support Acknowledgement</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getCommercialSupportAcknowledgement()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_CommercialSupportAcknowledgement();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship <em>Relevant Financial Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relevant Financial Relationship</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationship()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_RelevantFinancialRelationship();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationshipDisclosure <em>Relevant Financial Relationship Disclosure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Relevant Financial Relationship Disclosure</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getRelevantFinancialRelationshipDisclosure()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EReference getHealthcareEducationType_RelevantFinancialRelationshipDisclosure();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getContact()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_Contact();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType#getUniqueElementName()
	 * @see #getHealthcareEducationType()
	 * @generated
	 */
	EAttribute getHealthcareEducationType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType
	 * @generated
	 */
	EClass getHealthcareMetadataType();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareEducation <em>Healthcare Education</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Healthcare Education</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareEducation()
	 * @see #getHealthcareMetadataType()
	 * @generated
	 */
	EReference getHealthcareMetadataType_HealthcareEducation();

	/**
	 * Returns the meta object for the containment reference '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareAsset <em>Healthcare Asset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Healthcare Asset</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getHealthcareAsset()
	 * @see #getHealthcareMetadataType()
	 * @generated
	 */
	EReference getHealthcareMetadataType_HealthcareAsset();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType#getUniqueElementName()
	 * @see #getHealthcareMetadataType()
	 * @generated
	 */
	EAttribute getHealthcareMetadataType_UniqueElementName();

	/**
	 * Returns the meta object for class '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType <em>Target Audience Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Audience Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType
	 * @generated
	 */
	EClass getTargetAudienceType();

	/**
	 * Returns the meta object for the attribute list '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getAudienceCategory <em>Audience Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Audience Category</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getAudienceCategory()
	 * @see #getTargetAudienceType()
	 * @generated
	 */
	EAttribute getTargetAudienceType_AudienceCategory();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getProfession <em>Profession</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profession</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getProfession()
	 * @see #getTargetAudienceType()
	 * @generated
	 */
	EReference getTargetAudienceType_Profession();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getSpecialty <em>Specialty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specialty</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getSpecialty()
	 * @see #getTargetAudienceType()
	 * @generated
	 */
	EReference getTargetAudienceType_Specialty();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getReadingLevel <em>Reading Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reading Level</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getReadingLevel()
	 * @see #getTargetAudienceType()
	 * @generated
	 */
	EReference getTargetAudienceType_ReadingLevel();

	/**
	 * Returns the meta object for the attribute '{@link edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName <em>Unique Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Element Name</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.TargetAudienceType#getUniqueElementName()
	 * @see #getTargetAudienceType()
	 * @generated
	 */
	EAttribute getTargetAudienceType_UniqueElementName();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType <em>Activity Delivery Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Activity Delivery Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @generated
	 */
	EEnum getActivityDeliveryType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType <em>Activity Sponsorship Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Activity Sponsorship Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @generated
	 */
	EEnum getActivitySponsorshipType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType <em>Audience Category Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Audience Category Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @generated
	 */
	EEnum getAudienceCategoryType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType <em>Credit Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Credit Type Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @generated
	 */
	EEnum getCreditTypeType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType <em>Credit Unit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Credit Unit Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @generated
	 */
	EEnum getCreditUnitType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType <em>Medical Image Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Medical Image Type Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @generated
	 */
	EEnum getMedicalImageTypeType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.OrientationType <em>Orientation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Orientation Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @generated
	 */
	EEnum getOrientationType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.PacingType <em>Pacing Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Pacing Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @generated
	 */
	EEnum getPacingType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType <em>Participation Modality Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Participation Modality Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @generated
	 */
	EEnum getParticipationModalityType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType <em>Specimen Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Specimen Type Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @generated
	 */
	EEnum getSpecimenTypeType();

	/**
	 * Returns the meta object for enum '{@link edu.louisiana.caai.lom.model.healthcare.YesNoType <em>Yes No Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Yes No Type</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @generated
	 */
	EEnum getYesNoType();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType <em>Activity Delivery Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Activity Delivery Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType"
	 *        extendedMetaData="name='activityDeliveryType:Object' baseType='activityDeliveryType'"
	 * @generated
	 */
	EDataType getActivityDeliveryTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType <em>Activity Sponsorship Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Activity Sponsorship Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType"
	 *        extendedMetaData="name='activitySponsorshipType:Object' baseType='activitySponsorshipType'"
	 * @generated
	 */
	EDataType getActivitySponsorshipTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType <em>Audience Category Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Audience Category Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType"
	 *        extendedMetaData="name='audienceCategoryType:Object' baseType='audienceCategoryType'"
	 * @generated
	 */
	EDataType getAudienceCategoryTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType <em>Credit Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Credit Type Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.CreditTypeType"
	 *        extendedMetaData="name='creditTypeType:Object' baseType='creditTypeType'"
	 * @generated
	 */
	EDataType getCreditTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType <em>Credit Unit Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Credit Unit Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.CreditUnitType"
	 *        extendedMetaData="name='creditUnitType:Object' baseType='creditUnitType'"
	 * @generated
	 */
	EDataType getCreditUnitTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType <em>Medical Image Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Medical Image Type Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType"
	 *        extendedMetaData="name='medicalImageTypeType:Object' baseType='medicalImageTypeType'"
	 * @generated
	 */
	EDataType getMedicalImageTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.OrientationType <em>Orientation Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Orientation Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.OrientationType"
	 *        extendedMetaData="name='orientationType:Object' baseType='orientationType'"
	 * @generated
	 */
	EDataType getOrientationTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.PacingType <em>Pacing Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pacing Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.PacingType"
	 *        extendedMetaData="name='pacingType:Object' baseType='pacingType'"
	 * @generated
	 */
	EDataType getPacingTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType <em>Participation Modality Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Participation Modality Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType"
	 *        extendedMetaData="name='participationModalityType:Object' baseType='participationModalityType'"
	 * @generated
	 */
	EDataType getParticipationModalityTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType <em>Specimen Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Specimen Type Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType"
	 *        extendedMetaData="name='specimenTypeType:Object' baseType='specimenTypeType'"
	 * @generated
	 */
	EDataType getSpecimenTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link edu.louisiana.caai.lom.model.healthcare.YesNoType <em>Yes No Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Yes No Type Object</em>'.
	 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
	 * @model instanceClass="edu.louisiana.caai.lom.model.healthcare.YesNoType"
	 *        extendedMetaData="name='yesNoType:Object' baseType='yesNoType'"
	 * @generated
	 */
	EDataType getYesNoTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HealthcareFactory getHealthcareFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl <em>Activity Location Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.ActivityLocationTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityLocationType()
		 * @generated
		 */
		EClass ACTIVITY_LOCATION_TYPE = eINSTANCE.getActivityLocationType();

		/**
		 * The meta object literal for the '<em><b>Room</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY_LOCATION_TYPE__ROOM = eINSTANCE.getActivityLocationType_Room();

		/**
		 * The meta object literal for the '<em><b>Building</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY_LOCATION_TYPE__BUILDING = eINSTANCE.getActivityLocationType_Building();

		/**
		 * The meta object literal for the '<em><b>Unique Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY_LOCATION_TYPE__UNIQUE_ELEMENT_NAME = eINSTANCE.getActivityLocationType_UniqueElementName();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl <em>Credits Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.CreditsTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditsType()
		 * @generated
		 */
		EClass CREDITS_TYPE = eINSTANCE.getCreditsType();

		/**
		 * The meta object literal for the '<em><b>Accrediting Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__ACCREDITING_BODY = eINSTANCE.getCreditsType_AccreditingBody();

		/**
		 * The meta object literal for the '<em><b>Activity Certification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__ACTIVITY_CERTIFICATION = eINSTANCE.getCreditsType_ActivityCertification();

		/**
		 * The meta object literal for the '<em><b>Credit Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__CREDIT_TYPE = eINSTANCE.getCreditsType_CreditType();

		/**
		 * The meta object literal for the '<em><b>Credit Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__CREDIT_UNIT = eINSTANCE.getCreditsType_CreditUnit();

		/**
		 * The meta object literal for the '<em><b>Pacing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__PACING = eINSTANCE.getCreditsType_Pacing();

		/**
		 * The meta object literal for the '<em><b>Accredited Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__ACCREDITED_PROVIDER = eINSTANCE.getCreditsType_AccreditedProvider();

		/**
		 * The meta object literal for the '<em><b>Non Accredited Provider</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__NON_ACCREDITED_PROVIDER = eINSTANCE.getCreditsType_NonAccreditedProvider();

		/**
		 * The meta object literal for the '<em><b>Release Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__RELEASE_DATE = eINSTANCE.getCreditsType_ReleaseDate();

		/**
		 * The meta object literal for the '<em><b>Expiration Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__EXPIRATION_DATE = eINSTANCE.getCreditsType_ExpirationDate();

		/**
		 * The meta object literal for the '<em><b>Number Of Credits</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDITS_TYPE__NUMBER_OF_CREDITS = eINSTANCE.getCreditsType_NumberOfCredits();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.CustomElementsTypeImpl <em>Custom Elements Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.CustomElementsTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCustomElementsType()
		 * @generated
		 */
		EClass CUSTOM_ELEMENTS_TYPE = eINSTANCE.getCustomElementsType();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_ELEMENTS_TYPE__ANY = eINSTANCE.getCustomElementsType_Any();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.DocumentRootImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Accredited Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ACCREDITED_PROVIDER = eINSTANCE.getDocumentRoot_AccreditedProvider();

		/**
		 * The meta object literal for the '<em><b>Accrediting Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ACCREDITING_BODY = eINSTANCE.getDocumentRoot_AccreditingBody();

		/**
		 * The meta object literal for the '<em><b>Activity Certification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ACTIVITY_CERTIFICATION = eINSTANCE.getDocumentRoot_ActivityCertification();

		/**
		 * The meta object literal for the '<em><b>Activity Delivery</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ACTIVITY_DELIVERY = eINSTANCE.getDocumentRoot_ActivityDelivery();

		/**
		 * The meta object literal for the '<em><b>Activity Format</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ACTIVITY_FORMAT = eINSTANCE.getDocumentRoot_ActivityFormat();

		/**
		 * The meta object literal for the '<em><b>Activity Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ACTIVITY_LOCATION = eINSTANCE.getDocumentRoot_ActivityLocation();

		/**
		 * The meta object literal for the '<em><b>Activity Sponsorship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ACTIVITY_SPONSORSHIP = eINSTANCE.getDocumentRoot_ActivitySponsorship();

		/**
		 * The meta object literal for the '<em><b>Annotated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ANNOTATED = eINSTANCE.getDocumentRoot_Annotated();

		/**
		 * The meta object literal for the '<em><b>Audience Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AUDIENCE_CATEGORY = eINSTANCE.getDocumentRoot_AudienceCategory();

		/**
		 * The meta object literal for the '<em><b>Building</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__BUILDING = eINSTANCE.getDocumentRoot_Building();

		/**
		 * The meta object literal for the '<em><b>Clinical History</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CLINICAL_HISTORY = eINSTANCE.getDocumentRoot_ClinicalHistory();

		/**
		 * The meta object literal for the '<em><b>Commercial Support</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__COMMERCIAL_SUPPORT = eINSTANCE.getDocumentRoot_CommercialSupport();

		/**
		 * The meta object literal for the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT = eINSTANCE
				.getDocumentRoot_CommercialSupportAcknowledgement();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CONTACT = eINSTANCE.getDocumentRoot_Contact();

		/**
		 * The meta object literal for the '<em><b>Credits</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CREDITS = eINSTANCE.getDocumentRoot_Credits();

		/**
		 * The meta object literal for the '<em><b>Credits Available</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CREDITS_AVAILABLE = eINSTANCE.getDocumentRoot_CreditsAvailable();

		/**
		 * The meta object literal for the '<em><b>Credit Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CREDIT_TYPE = eINSTANCE.getDocumentRoot_CreditType();

		/**
		 * The meta object literal for the '<em><b>Credit Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CREDIT_UNIT = eINSTANCE.getDocumentRoot_CreditUnit();

		/**
		 * The meta object literal for the '<em><b>Custom Elements</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CUSTOM_ELEMENTS = eINSTANCE.getDocumentRoot_CustomElements();

		/**
		 * The meta object literal for the '<em><b>End Date Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__END_DATE_TIME = eINSTANCE.getDocumentRoot_EndDateTime();

		/**
		 * The meta object literal for the '<em><b>Expiration Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__EXPIRATION_DATE = eINSTANCE.getDocumentRoot_ExpirationDate();

		/**
		 * The meta object literal for the '<em><b>File Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__FILE_HEIGHT = eINSTANCE.getDocumentRoot_FileHeight();

		/**
		 * The meta object literal for the '<em><b>File Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__FILE_WIDTH = eINSTANCE.getDocumentRoot_FileWidth();

		/**
		 * The meta object literal for the '<em><b>Healthcare Asset</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HEALTHCARE_ASSET = eINSTANCE.getDocumentRoot_HealthcareAsset();

		/**
		 * The meta object literal for the '<em><b>Healthcare Education</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HEALTHCARE_EDUCATION = eINSTANCE.getDocumentRoot_HealthcareEducation();

		/**
		 * The meta object literal for the '<em><b>Healthcare Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HEALTHCARE_METADATA = eINSTANCE.getDocumentRoot_HealthcareMetadata();

		/**
		 * The meta object literal for the '<em><b>Magnification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MAGNIFICATION = eINSTANCE.getDocumentRoot_Magnification();

		/**
		 * The meta object literal for the '<em><b>Medical Image Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MEDICAL_IMAGE_TYPE = eINSTANCE.getDocumentRoot_MedicalImageType();

		/**
		 * The meta object literal for the '<em><b>Non Accredited Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__NON_ACCREDITED_PROVIDER = eINSTANCE.getDocumentRoot_NonAccreditedProvider();

		/**
		 * The meta object literal for the '<em><b>Number Of Credits</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__NUMBER_OF_CREDITS = eINSTANCE.getDocumentRoot_NumberOfCredits();

		/**
		 * The meta object literal for the '<em><b>Off Label Description</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__OFF_LABEL_DESCRIPTION = eINSTANCE.getDocumentRoot_OffLabelDescription();

		/**
		 * The meta object literal for the '<em><b>Off Label Use</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__OFF_LABEL_USE = eINSTANCE.getDocumentRoot_OffLabelUse();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ORIENTATION = eINSTANCE.getDocumentRoot_Orientation();

		/**
		 * The meta object literal for the '<em><b>Pacing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PACING = eINSTANCE.getDocumentRoot_Pacing();

		/**
		 * The meta object literal for the '<em><b>Participation Modality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PARTICIPATION_MODALITY = eINSTANCE.getDocumentRoot_ParticipationModality();

		/**
		 * The meta object literal for the '<em><b>Profession</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROFESSION = eINSTANCE.getDocumentRoot_Profession();

		/**
		 * The meta object literal for the '<em><b>Reading Level</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__READING_LEVEL = eINSTANCE.getDocumentRoot_ReadingLevel();

		/**
		 * The meta object literal for the '<em><b>Release Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__RELEASE_DATE = eINSTANCE.getDocumentRoot_ReleaseDate();

		/**
		 * The meta object literal for the '<em><b>Relevant Financial Relationship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP = eINSTANCE
				.getDocumentRoot_RelevantFinancialRelationship();

		/**
		 * The meta object literal for the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE = eINSTANCE
				.getDocumentRoot_RelevantFinancialRelationshipDisclosure();

		/**
		 * The meta object literal for the '<em><b>Room</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ROOM = eINSTANCE.getDocumentRoot_Room();

		/**
		 * The meta object literal for the '<em><b>Specialty</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SPECIALTY = eINSTANCE.getDocumentRoot_Specialty();

		/**
		 * The meta object literal for the '<em><b>Specimen Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__SPECIMEN_TYPE = eINSTANCE.getDocumentRoot_SpecimenType();

		/**
		 * The meta object literal for the '<em><b>Start Date Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__START_DATE_TIME = eINSTANCE.getDocumentRoot_StartDateTime();

		/**
		 * The meta object literal for the '<em><b>Target Audience</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TARGET_AUDIENCE = eINSTANCE.getDocumentRoot_TargetAudience();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl <em>Asset Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareAssetTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareAssetType()
		 * @generated
		 */
		EClass HEALTHCARE_ASSET_TYPE = eINSTANCE.getHealthcareAssetType();

		/**
		 * The meta object literal for the '<em><b>Annotated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__ANNOTATED = eINSTANCE.getHealthcareAssetType_Annotated();

		/**
		 * The meta object literal for the '<em><b>Clinical History</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_ASSET_TYPE__CLINICAL_HISTORY = eINSTANCE.getHealthcareAssetType_ClinicalHistory();

		/**
		 * The meta object literal for the '<em><b>Magnification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__MAGNIFICATION = eINSTANCE.getHealthcareAssetType_Magnification();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__ORIENTATION = eINSTANCE.getHealthcareAssetType_Orientation();

		/**
		 * The meta object literal for the '<em><b>Medical Image Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__MEDICAL_IMAGE_TYPE = eINSTANCE.getHealthcareAssetType_MedicalImageType();

		/**
		 * The meta object literal for the '<em><b>Specimen Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__SPECIMEN_TYPE = eINSTANCE.getHealthcareAssetType_SpecimenType();

		/**
		 * The meta object literal for the '<em><b>File Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__FILE_HEIGHT = eINSTANCE.getHealthcareAssetType_FileHeight();

		/**
		 * The meta object literal for the '<em><b>File Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__FILE_WIDTH = eINSTANCE.getHealthcareAssetType_FileWidth();

		/**
		 * The meta object literal for the '<em><b>Unique Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_ASSET_TYPE__UNIQUE_ELEMENT_NAME = eINSTANCE.getHealthcareAssetType_UniqueElementName();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl <em>Education Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareEducationTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareEducationType()
		 * @generated
		 */
		EClass HEALTHCARE_EDUCATION_TYPE = eINSTANCE.getHealthcareEducationType();

		/**
		 * The meta object literal for the '<em><b>Expiration Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__EXPIRATION_DATE = eINSTANCE.getHealthcareEducationType_ExpirationDate();

		/**
		 * The meta object literal for the '<em><b>Credits Available</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__CREDITS_AVAILABLE = eINSTANCE
				.getHealthcareEducationType_CreditsAvailable();

		/**
		 * The meta object literal for the '<em><b>Credits</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__CREDITS = eINSTANCE.getHealthcareEducationType_Credits();

		/**
		 * The meta object literal for the '<em><b>Target Audience</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__TARGET_AUDIENCE = eINSTANCE.getHealthcareEducationType_TargetAudience();

		/**
		 * The meta object literal for the '<em><b>Activity Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__ACTIVITY_LOCATION = eINSTANCE
				.getHealthcareEducationType_ActivityLocation();

		/**
		 * The meta object literal for the '<em><b>Start Date Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__START_DATE_TIME = eINSTANCE.getHealthcareEducationType_StartDateTime();

		/**
		 * The meta object literal for the '<em><b>End Date Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__END_DATE_TIME = eINSTANCE.getHealthcareEducationType_EndDateTime();

		/**
		 * The meta object literal for the '<em><b>Activity Sponsorship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__ACTIVITY_SPONSORSHIP = eINSTANCE
				.getHealthcareEducationType_ActivitySponsorship();

		/**
		 * The meta object literal for the '<em><b>Participation Modality</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__PARTICIPATION_MODALITY = eINSTANCE
				.getHealthcareEducationType_ParticipationModality();

		/**
		 * The meta object literal for the '<em><b>Activity Delivery</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__ACTIVITY_DELIVERY = eINSTANCE
				.getHealthcareEducationType_ActivityDelivery();

		/**
		 * The meta object literal for the '<em><b>Activity Format</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__ACTIVITY_FORMAT = eINSTANCE.getHealthcareEducationType_ActivityFormat();

		/**
		 * The meta object literal for the '<em><b>Off Label Use</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_USE = eINSTANCE.getHealthcareEducationType_OffLabelUse();

		/**
		 * The meta object literal for the '<em><b>Off Label Description</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__OFF_LABEL_DESCRIPTION = eINSTANCE
				.getHealthcareEducationType_OffLabelDescription();

		/**
		 * The meta object literal for the '<em><b>Commercial Support</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT = eINSTANCE
				.getHealthcareEducationType_CommercialSupport();

		/**
		 * The meta object literal for the '<em><b>Commercial Support Acknowledgement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__COMMERCIAL_SUPPORT_ACKNOWLEDGEMENT = eINSTANCE
				.getHealthcareEducationType_CommercialSupportAcknowledgement();

		/**
		 * The meta object literal for the '<em><b>Relevant Financial Relationship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP = eINSTANCE
				.getHealthcareEducationType_RelevantFinancialRelationship();

		/**
		 * The meta object literal for the '<em><b>Relevant Financial Relationship Disclosure</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_EDUCATION_TYPE__RELEVANT_FINANCIAL_RELATIONSHIP_DISCLOSURE = eINSTANCE
				.getHealthcareEducationType_RelevantFinancialRelationshipDisclosure();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__CONTACT = eINSTANCE.getHealthcareEducationType_Contact();

		/**
		 * The meta object literal for the '<em><b>Unique Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_EDUCATION_TYPE__UNIQUE_ELEMENT_NAME = eINSTANCE
				.getHealthcareEducationType_UniqueElementName();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl <em>Metadata Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcareMetadataTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getHealthcareMetadataType()
		 * @generated
		 */
		EClass HEALTHCARE_METADATA_TYPE = eINSTANCE.getHealthcareMetadataType();

		/**
		 * The meta object literal for the '<em><b>Healthcare Education</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_METADATA_TYPE__HEALTHCARE_EDUCATION = eINSTANCE
				.getHealthcareMetadataType_HealthcareEducation();

		/**
		 * The meta object literal for the '<em><b>Healthcare Asset</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEALTHCARE_METADATA_TYPE__HEALTHCARE_ASSET = eINSTANCE.getHealthcareMetadataType_HealthcareAsset();

		/**
		 * The meta object literal for the '<em><b>Unique Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTHCARE_METADATA_TYPE__UNIQUE_ELEMENT_NAME = eINSTANCE
				.getHealthcareMetadataType_UniqueElementName();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl <em>Target Audience Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.TargetAudienceTypeImpl
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getTargetAudienceType()
		 * @generated
		 */
		EClass TARGET_AUDIENCE_TYPE = eINSTANCE.getTargetAudienceType();

		/**
		 * The meta object literal for the '<em><b>Audience Category</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET_AUDIENCE_TYPE__AUDIENCE_CATEGORY = eINSTANCE.getTargetAudienceType_AudienceCategory();

		/**
		 * The meta object literal for the '<em><b>Profession</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_AUDIENCE_TYPE__PROFESSION = eINSTANCE.getTargetAudienceType_Profession();

		/**
		 * The meta object literal for the '<em><b>Specialty</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_AUDIENCE_TYPE__SPECIALTY = eINSTANCE.getTargetAudienceType_Specialty();

		/**
		 * The meta object literal for the '<em><b>Reading Level</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_AUDIENCE_TYPE__READING_LEVEL = eINSTANCE.getTargetAudienceType_ReadingLevel();

		/**
		 * The meta object literal for the '<em><b>Unique Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET_AUDIENCE_TYPE__UNIQUE_ELEMENT_NAME = eINSTANCE.getTargetAudienceType_UniqueElementName();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType <em>Activity Delivery Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityDeliveryType()
		 * @generated
		 */
		EEnum ACTIVITY_DELIVERY_TYPE = eINSTANCE.getActivityDeliveryType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType <em>Activity Sponsorship Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivitySponsorshipType()
		 * @generated
		 */
		EEnum ACTIVITY_SPONSORSHIP_TYPE = eINSTANCE.getActivitySponsorshipType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType <em>Audience Category Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getAudienceCategoryType()
		 * @generated
		 */
		EEnum AUDIENCE_CATEGORY_TYPE = eINSTANCE.getAudienceCategoryType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.CreditTypeType <em>Credit Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditTypeType()
		 * @generated
		 */
		EEnum CREDIT_TYPE_TYPE = eINSTANCE.getCreditTypeType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.CreditUnitType <em>Credit Unit Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditUnitType()
		 * @generated
		 */
		EEnum CREDIT_UNIT_TYPE = eINSTANCE.getCreditUnitType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType <em>Medical Image Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getMedicalImageTypeType()
		 * @generated
		 */
		EEnum MEDICAL_IMAGE_TYPE_TYPE = eINSTANCE.getMedicalImageTypeType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.OrientationType <em>Orientation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getOrientationType()
		 * @generated
		 */
		EEnum ORIENTATION_TYPE = eINSTANCE.getOrientationType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.PacingType <em>Pacing Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getPacingType()
		 * @generated
		 */
		EEnum PACING_TYPE = eINSTANCE.getPacingType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType <em>Participation Modality Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getParticipationModalityType()
		 * @generated
		 */
		EEnum PARTICIPATION_MODALITY_TYPE = eINSTANCE.getParticipationModalityType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType <em>Specimen Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getSpecimenTypeType()
		 * @generated
		 */
		EEnum SPECIMEN_TYPE_TYPE = eINSTANCE.getSpecimenTypeType();

		/**
		 * The meta object literal for the '{@link edu.louisiana.caai.lom.model.healthcare.YesNoType <em>Yes No Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getYesNoType()
		 * @generated
		 */
		EEnum YES_NO_TYPE = eINSTANCE.getYesNoType();

		/**
		 * The meta object literal for the '<em>Activity Delivery Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivityDeliveryTypeObject()
		 * @generated
		 */
		EDataType ACTIVITY_DELIVERY_TYPE_OBJECT = eINSTANCE.getActivityDeliveryTypeObject();

		/**
		 * The meta object literal for the '<em>Activity Sponsorship Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ActivitySponsorshipType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getActivitySponsorshipTypeObject()
		 * @generated
		 */
		EDataType ACTIVITY_SPONSORSHIP_TYPE_OBJECT = eINSTANCE.getActivitySponsorshipTypeObject();

		/**
		 * The meta object literal for the '<em>Audience Category Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getAudienceCategoryTypeObject()
		 * @generated
		 */
		EDataType AUDIENCE_CATEGORY_TYPE_OBJECT = eINSTANCE.getAudienceCategoryTypeObject();

		/**
		 * The meta object literal for the '<em>Credit Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.CreditTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditTypeTypeObject()
		 * @generated
		 */
		EDataType CREDIT_TYPE_TYPE_OBJECT = eINSTANCE.getCreditTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Credit Unit Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.CreditUnitType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getCreditUnitTypeObject()
		 * @generated
		 */
		EDataType CREDIT_UNIT_TYPE_OBJECT = eINSTANCE.getCreditUnitTypeObject();

		/**
		 * The meta object literal for the '<em>Medical Image Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getMedicalImageTypeTypeObject()
		 * @generated
		 */
		EDataType MEDICAL_IMAGE_TYPE_TYPE_OBJECT = eINSTANCE.getMedicalImageTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Orientation Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.OrientationType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getOrientationTypeObject()
		 * @generated
		 */
		EDataType ORIENTATION_TYPE_OBJECT = eINSTANCE.getOrientationTypeObject();

		/**
		 * The meta object literal for the '<em>Pacing Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.PacingType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getPacingTypeObject()
		 * @generated
		 */
		EDataType PACING_TYPE_OBJECT = eINSTANCE.getPacingTypeObject();

		/**
		 * The meta object literal for the '<em>Participation Modality Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getParticipationModalityTypeObject()
		 * @generated
		 */
		EDataType PARTICIPATION_MODALITY_TYPE_OBJECT = eINSTANCE.getParticipationModalityTypeObject();

		/**
		 * The meta object literal for the '<em>Specimen Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.SpecimenTypeType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getSpecimenTypeTypeObject()
		 * @generated
		 */
		EDataType SPECIMEN_TYPE_TYPE_OBJECT = eINSTANCE.getSpecimenTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Yes No Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.louisiana.caai.lom.model.healthcare.YesNoType
		 * @see edu.louisiana.caai.lom.model.healthcare.impl.HealthcarePackageImpl#getYesNoTypeObject()
		 * @generated
		 */
		EDataType YES_NO_TYPE_OBJECT = eINSTANCE.getYesNoTypeObject();

	}

} //HealthcarePackage
