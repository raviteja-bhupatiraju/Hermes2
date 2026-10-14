/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcarevocab.util;

import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class HealthcarevocabXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HealthcarevocabXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		HealthcarevocabPackage.eINSTANCE.eClass();
	}

	/**
	 * Register for "*" and "xml" file extensions the HealthcarevocabResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new HealthcarevocabResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new HealthcarevocabResourceFactoryImpl());
		}
		return registrations;
	}

} //HealthcarevocabXMLProcessor
