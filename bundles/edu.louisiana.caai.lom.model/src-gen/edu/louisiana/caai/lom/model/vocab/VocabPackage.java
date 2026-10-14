/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.vocab;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

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
 *          This component schema definition provides simple type declarations for LOM
 *          data elements that are defined as Vocabulary data types.
 * 
 *          This component schema definition enforces that vocabulary values are only 
 *          those specified in 1484.12.3 or in a custom vocabulary.  This component 
 *          schema definition enforces adherence to the combined use of 1484.12.3
 *          defined vocabulary values and custom vocabulary values by checking that both
 *          sources and values are taken from either the token set defined in 1484.12.3 or
 *          from a custom vocabulary.
 * 
 *          Organizations are free to define their own namespace for the custom vocabulary
 *          extensions.  For completeness this XSD is set up to use a namespace defined
 *          as xmlns:lx="http://ltsc.ieee.org/xsd/LOM/custom"
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
 * 
 *        This work is licensed under the Creative Commons Attribution-ShareAlike
 *        License.  To view a copy of this license, see the file license.txt,
 *        visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *        Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *     
 * 
 *        This component schema defintion defines global schema data type declarations
 *        for data elements defined in the LOMv1.0 base schema.  This component XSD
 *        defines the aggregation relationship among the LOM data elements.  These aggregation
 *        relationships enforce the LOMv1.0 base schema requirement that elements can only
 *        be present in a LOM XML instance as elements of the aggregate element to which they
 *        belong.
 * 
 *        Duplicate declarations are included as comments for completeness.  These declarations
 *        should remain commented out or they can be removed completely.
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
 *         ** 08/24/2004:  1) Updated those complexTypes that referenced exteding the **
 *         **              LangString complexType to map to the new name for that     **
 *         **              complexType - LanguageString:                              **       
 *         **                 1.2 Title                                               **       
 *         **                 1.4 Description                                         **       
 *         **                 1.5 Keyword                                             **       
 *         **                 1.6 Coverage                                            **       
 *         **                 2.1 Version                                             **       
 *         **                 4.5 Installation Remarks                                **       
 *         **                 4.6 Other Platform Requirements                         **       
 *         **                 5.7 Typical Age Range                                   **       
 *         **                 5.10 Description                                        **       
 *         **                 6.3 Description                                         **       
 *         **                 7.2.2 Description                                       **       
 *         **                 8.3 Description                                         **       
 *         **                 9.2.1 Source                                            **       
 *         **                 9.2.2.2 Entry                                           **       
 *         **                 9.3 Description                                         **       
 *         **                 9.4 Keyword                                             **       
 *         **              2)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **              3)Updated annotation describing file to include language   **
 *         **                updates found in Draft 2 Working Draft 2                 **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         **              2)Updated all complexTypes (non-Vocabulary data typed)     ** 
 *         **                elements to reference the customAttribute attributeGroup **
 *         **                to support attribute extensions.                         **
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
 *          This work is licensed under the Creative Commons Attribution-ShareAlike
 *          License.  To view a copy of this license, see the file license.txt,
 *          visit http://creativecommons.org/licenses/by-sa/2.0 or send a letter to
 *          Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
 *       
 * 
 *          This component schema defintion provides the element name declaration for the
 *          root element for all LOM XML instances.
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
 *          This component schema definition provides global type declarations for the standard
 *          LOMv1.0 vocabulary tokens for those LOM data elements whose values are taken from
 *          a Vocabulary data type.
 * 
 *          This component schema defintion defines the stanard vocabulary value 
 *          declarations as defined in the LOMv1.0 base schema.  These vocabulary
 *          value declarations are used in conjunction with both vocab/custom.xsd and
 *          vocab/strict.xsd.
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
 *         ** 09/22/2003:  1)Updated comment describing this file to state that this  **
 *         **                file is the LOM V1.0 Base Schema vocabulary source and   **
 *         **                value declarations.                                      ** 
 *         **                                                                         **
 *         ** 08/24/2004:  1)Updated the Creative Commons Licensing agreement to      **
 *         **                reference the Attribution-ShareAlike 2.0                 **
 *         **              2)Updated annotation describing file to include language   **
 *         **                updates found in Draft 2 Working Draft 2                 **
 *         **                                                                         **
 *         ** 09/27/2004   1)Updated file documentation to correspond with working    **
 *         **                draft 3.                                                 **
 *         *****************************************************************************
 *       
 *  This is a MedBiquitous defined set of LOM custom vocabularies for healthcare.
 * <!-- end-model-doc -->
 * @see edu.louisiana.caai.lom.model.vocab.VocabFactory
 * @model kind="package"
 * @generated
 */
public interface VocabPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "vocab";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://ltsc.ieee.org/xsd/LOM/vocab";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "vocab";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VocabPackage eINSTANCE = edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl.init();

	/**
	 * The meta object id for the '<em>Aggregation Level</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getAggregationLevel()
	 * @generated
	 */
	int AGGREGATION_LEVEL = 0;

	/**
	 * The meta object id for the '<em>Context</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getContext()
	 * @generated
	 */
	int CONTEXT = 1;

	/**
	 * The meta object id for the '<em>Copyright And Other Restrictions</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getCopyrightAndOtherRestrictions()
	 * @generated
	 */
	int COPYRIGHT_AND_OTHER_RESTRICTIONS = 2;

	/**
	 * The meta object id for the '<em>Cost</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getCost()
	 * @generated
	 */
	int COST = 3;

	/**
	 * The meta object id for the '<em>Difficulty</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getDifficulty()
	 * @generated
	 */
	int DIFFICULTY = 4;

	/**
	 * The meta object id for the '<em>Intended End User Role</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getIntendedEndUserRole()
	 * @generated
	 */
	int INTENDED_END_USER_ROLE = 5;

	/**
	 * The meta object id for the '<em>Interactivity Level</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getInteractivityLevel()
	 * @generated
	 */
	int INTERACTIVITY_LEVEL = 6;

	/**
	 * The meta object id for the '<em>Interactivity Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getInteractivityType()
	 * @generated
	 */
	int INTERACTIVITY_TYPE = 7;

	/**
	 * The meta object id for the '<em>Kind</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getKind()
	 * @generated
	 */
	int KIND = 8;

	/**
	 * The meta object id for the '<em>Learning Resource Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getLearningResourceType()
	 * @generated
	 */
	int LEARNING_RESOURCE_TYPE = 9;

	/**
	 * The meta object id for the '<em>Name</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getName_()
	 * @generated
	 */
	int NAME = 10;

	/**
	 * The meta object id for the '<em>Purpose</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getPurpose()
	 * @generated
	 */
	int PURPOSE = 11;

	/**
	 * The meta object id for the '<em>Role</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 12;

	/**
	 * The meta object id for the '<em>Role Meta</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getRoleMeta()
	 * @generated
	 */
	int ROLE_META = 13;

	/**
	 * The meta object id for the '<em>Semantic Density</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getSemanticDensity()
	 * @generated
	 */
	int SEMANTIC_DENSITY = 14;

	/**
	 * The meta object id for the '<em>Source</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 15;

	/**
	 * The meta object id for the '<em>Status</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getStatus()
	 * @generated
	 */
	int STATUS = 16;

	/**
	 * The meta object id for the '<em>Structure</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getStructure()
	 * @generated
	 */
	int STRUCTURE = 17;

	/**
	 * The meta object id for the '<em>Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 18;

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Aggregation Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Aggregation Level</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='aggregationLevel' memberTypes='http://ltsc.ieee.org/xsd/LOM#aggregationLevelValues'"
	 * @generated
	 */
	EDataType getAggregationLevel();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Context</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='context' memberTypes='http://ltsc.ieee.org/xsd/LOM#contextValues http://ns.medbiq.org/lom/vocab/v1/#contextValues'"
	 * @generated
	 */
	EDataType getContext();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Copyright And Other Restrictions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Copyright And Other Restrictions</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='copyrightAndOtherRestrictions' memberTypes='http://ltsc.ieee.org/xsd/LOM#copyrightAndOtherRestrictionsValues'"
	 * @generated
	 */
	EDataType getCopyrightAndOtherRestrictions();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Cost</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='cost' memberTypes='http://ltsc.ieee.org/xsd/LOM#costValues'"
	 * @generated
	 */
	EDataType getCost();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Difficulty</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='difficulty' memberTypes='http://ltsc.ieee.org/xsd/LOM#difficultyValues'"
	 * @generated
	 */
	EDataType getDifficulty();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Intended End User Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Intended End User Role</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='intendedEndUserRole' memberTypes='http://ltsc.ieee.org/xsd/LOM#intendedEndUserRoleValues'"
	 * @generated
	 */
	EDataType getIntendedEndUserRole();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Interactivity Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Interactivity Level</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='interactivityLevel' memberTypes='http://ltsc.ieee.org/xsd/LOM#interactivityLevelValues'"
	 * @generated
	 */
	EDataType getInteractivityLevel();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Interactivity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Interactivity Type</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='interactivityType' memberTypes='http://ltsc.ieee.org/xsd/LOM#interactivityTypeValues'"
	 * @generated
	 */
	EDataType getInteractivityType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Kind</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='kind' memberTypes='http://ltsc.ieee.org/xsd/LOM#kindValues'"
	 * @generated
	 */
	EDataType getKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Learning Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Learning Resource Type</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='learningResourceType' memberTypes='http://ltsc.ieee.org/xsd/LOM#learningResourceTypeValues http://ns.medbiq.org/lom/vocab/v1/#learningResourceTypeValues'"
	 * @generated
	 */
	EDataType getLearningResourceType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='name' memberTypes='http://ltsc.ieee.org/xsd/LOM#nameValues'"
	 * @generated
	 */
	EDataType getName_();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Purpose</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Purpose</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='purpose' memberTypes='http://ltsc.ieee.org/xsd/LOM#purposeValues http://ns.medbiq.org/lom/vocab/v1/#purposeValues'"
	 * @generated
	 */
	EDataType getPurpose();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Role</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='role' memberTypes='http://ltsc.ieee.org/xsd/LOM#roleValues http://ns.medbiq.org/lom/vocab/v1/#roleValues'"
	 * @generated
	 */
	EDataType getRole();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Role Meta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Role Meta</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='roleMeta' memberTypes='http://ltsc.ieee.org/xsd/LOM#roleMetaValues'"
	 * @generated
	 */
	EDataType getRoleMeta();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Semantic Density</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Semantic Density</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='semanticDensity' memberTypes='http://ltsc.ieee.org/xsd/LOM#semanticDensityValues'"
	 * @generated
	 */
	EDataType getSemanticDensity();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Source</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='source' memberTypes='http://ltsc.ieee.org/xsd/LOM#sourceValues http://ns.medbiq.org/lom/vocab/v1/#sourceValues'"
	 * @generated
	 */
	EDataType getSource();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Status</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='status' memberTypes='http://ltsc.ieee.org/xsd/LOM#statusValues'"
	 * @generated
	 */
	EDataType getStatus();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Structure</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='structure' memberTypes='http://ltsc.ieee.org/xsd/LOM#structureValues'"
	 * @generated
	 */
	EDataType getStructure();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 *        extendedMetaData="name='type' memberTypes='http://ltsc.ieee.org/xsd/LOM#typeValues'"
	 * @generated
	 */
	EDataType getType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VocabFactory getVocabFactory();

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
		 * The meta object literal for the '<em>Aggregation Level</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getAggregationLevel()
		 * @generated
		 */
		EDataType AGGREGATION_LEVEL = eINSTANCE.getAggregationLevel();

		/**
		 * The meta object literal for the '<em>Context</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getContext()
		 * @generated
		 */
		EDataType CONTEXT = eINSTANCE.getContext();

		/**
		 * The meta object literal for the '<em>Copyright And Other Restrictions</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getCopyrightAndOtherRestrictions()
		 * @generated
		 */
		EDataType COPYRIGHT_AND_OTHER_RESTRICTIONS = eINSTANCE.getCopyrightAndOtherRestrictions();

		/**
		 * The meta object literal for the '<em>Cost</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getCost()
		 * @generated
		 */
		EDataType COST = eINSTANCE.getCost();

		/**
		 * The meta object literal for the '<em>Difficulty</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getDifficulty()
		 * @generated
		 */
		EDataType DIFFICULTY = eINSTANCE.getDifficulty();

		/**
		 * The meta object literal for the '<em>Intended End User Role</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getIntendedEndUserRole()
		 * @generated
		 */
		EDataType INTENDED_END_USER_ROLE = eINSTANCE.getIntendedEndUserRole();

		/**
		 * The meta object literal for the '<em>Interactivity Level</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getInteractivityLevel()
		 * @generated
		 */
		EDataType INTERACTIVITY_LEVEL = eINSTANCE.getInteractivityLevel();

		/**
		 * The meta object literal for the '<em>Interactivity Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getInteractivityType()
		 * @generated
		 */
		EDataType INTERACTIVITY_TYPE = eINSTANCE.getInteractivityType();

		/**
		 * The meta object literal for the '<em>Kind</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getKind()
		 * @generated
		 */
		EDataType KIND = eINSTANCE.getKind();

		/**
		 * The meta object literal for the '<em>Learning Resource Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getLearningResourceType()
		 * @generated
		 */
		EDataType LEARNING_RESOURCE_TYPE = eINSTANCE.getLearningResourceType();

		/**
		 * The meta object literal for the '<em>Name</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getName_()
		 * @generated
		 */
		EDataType NAME = eINSTANCE.getName_();

		/**
		 * The meta object literal for the '<em>Purpose</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getPurpose()
		 * @generated
		 */
		EDataType PURPOSE = eINSTANCE.getPurpose();

		/**
		 * The meta object literal for the '<em>Role</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getRole()
		 * @generated
		 */
		EDataType ROLE = eINSTANCE.getRole();

		/**
		 * The meta object literal for the '<em>Role Meta</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getRoleMeta()
		 * @generated
		 */
		EDataType ROLE_META = eINSTANCE.getRoleMeta();

		/**
		 * The meta object literal for the '<em>Semantic Density</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getSemanticDensity()
		 * @generated
		 */
		EDataType SEMANTIC_DENSITY = eINSTANCE.getSemanticDensity();

		/**
		 * The meta object literal for the '<em>Source</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getSource()
		 * @generated
		 */
		EDataType SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em>Status</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getStatus()
		 * @generated
		 */
		EDataType STATUS = eINSTANCE.getStatus();

		/**
		 * The meta object literal for the '<em>Structure</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getStructure()
		 * @generated
		 */
		EDataType STRUCTURE = eINSTANCE.getStructure();

		/**
		 * The meta object literal for the '<em>Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see edu.louisiana.caai.lom.model.vocab.impl.VocabPackageImpl#getType()
		 * @generated
		 */
		EDataType TYPE = eINSTANCE.getType();

	}

} //VocabPackage
