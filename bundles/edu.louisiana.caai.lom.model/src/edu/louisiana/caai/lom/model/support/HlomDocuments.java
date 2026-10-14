/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.lom.MetadataSchema;
import edu.louisiana.caai.lom.model.lom.Title;

/**
 * Creates new HLOM documents.
 */
public final class HlomDocuments {

	/** Default language for new multilingual strings. */
	public static final String DEFAULT_LANGUAGE = "en-US";

	/** Metadata schema identifier of the IEEE LOM base schema. */
	public static final String LOM_METADATA_SCHEMA = "LOMv1.0";

	/** Metadata schema identifier of the Healthcare LOM profile. */
	public static final String HEALTHCARE_METADATA_SCHEMA = "HEALTHCARE_LOMv1";

	private HlomDocuments() {
	}

	/**
	 * Creates a new document with a title, the document language, the meta-metadata that identifies it as a
	 * Healthcare LOM record and the (required) healthcareMetadata element.
	 */
	public static DocumentRoot createDocument(String title, String language) {
		LomFactory factory = LomFactory.eINSTANCE;
		DocumentRoot root = factory.createDocumentRoot();
		root.getXMLNSPrefixMap().put("", LomPackage.eNS_URI);
		root.getXMLNSPrefixMap().put("hx", HealthcarePackage.eNS_URI);
		root.getXMLNSPrefixMap().put("a", AddressPackage.eNS_URI);
		root.getXSISchemaLocation().put(LomPackage.eNS_URI, HlomResources.HLOM_SCHEMA_URL);

		Lom lom = factory.createLom();
		root.setLom(lom);

		General general = factory.createGeneral();
		lom.getGeneral().add(general);
		if (title != null && !title.isBlank()) {
			Title titleElement = factory.createTitle();
			titleElement.getString().add(langString(title, language));
			general.getTitle().add(titleElement);
		}
		general.getLanguage().add(languageId(language));

		MetaMetadata metaMetadata = factory.createMetaMetadata();
		lom.getMetaMetadata().add(metaMetadata);
		metaMetadata.getMetadataSchema().add(metadataSchema(LOM_METADATA_SCHEMA));
		metaMetadata.getMetadataSchema().add(metadataSchema(HEALTHCARE_METADATA_SCHEMA));
		metaMetadata.getLanguage().add(languageId(language));

		// Required once in every Healthcare LOM record.
		lom.getHealthcareMetadata().add(HealthcareFactory.eINSTANCE.createHealthcareMetadataType());
		return root;
	}

	/** Creates a language-tagged string. */
	public static LangString langString(String value, String language) {
		LangString string = LomFactory.eINSTANCE.createLangString();
		string.setLanguage(language);
		string.setValue(value);
		return string;
	}

	private static LanguageId languageId(String language) {
		LanguageId languageId = LomFactory.eINSTANCE.createLanguageId();
		languageId.setValue(language);
		return languageId;
	}

	private static MetadataSchema metadataSchema(String value) {
		MetadataSchema schema = LomFactory.eINSTANCE.createMetadataSchema();
		schema.setValue(value);
		return schema;
	}
}
