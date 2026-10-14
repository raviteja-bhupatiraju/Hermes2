/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * 3 Meta-Metadata: information about the metadata record itself (who created and validated it, the schemas
 * it conforms to and its language).
 */
public class MetaMetadataPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.MetaMetadataPage_MetaMetadata;
	}

	@Override
	public String getHeading() {
		return "3 Meta-Metadata";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath metaMetadata = ModelPath.of(lom().getLom_MetaMetadata());

		Composite left = column(form, body);
		Composite identification = form.section(left, Messages.MetaMetadataPage_Identification,
				Messages.MetaMetadataPage_CatalogEntriesIdentifyingThisMetadata, 2);
		form.masterDetail(identification, metaMetadata, lom().getMetaMetadata_Identifier(),
				GeneralPage::identifierLabel, () -> lom().getLomFactory().createIdentifier(), (detail, parent) -> {
					detail.text(parent, Messages.MetaMetadataPage_Catalog, ModelPath.of(lom().getIdentifier_Catalog()),
							lom().getCatalog_Value(), "metaIdentifier");
					detail.text(parent, Messages.MetaMetadataPage_Entry, ModelPath.of(lom().getIdentifier_Entry()), lom().getEntry_Value(),
							"metaIdentifier");
				});

		Composite schemas = form.section(left, Messages.MetaMetadataPage_SchemasAndLanguage,
				Messages.MetaMetadataPage_HealthcareLOMRecordsListLOMv1, 2);
		form.valueList(schemas, Messages.MetaMetadataPage_MetadataSchemas, ValueList.Kind.ELEMENTS, metaMetadata,
				lom().getMetaMetadata_MetadataSchema(), lom().getMetadataSchema_Value(),
				HlomSuggestions.METADATA_SCHEMAS, "metadataSchema");
		form.valueList(schemas, Messages.MetaMetadataPage_Language, ValueList.Kind.ELEMENTS, metaMetadata, lom().getMetaMetadata_Language(),
				lom().getLanguageId_Value(), HlomSuggestions.LANGUAGES, "metaLanguage");

		Composite right = wide(form, body);
		Composite contributors = form.section(right, Messages.MetaMetadataPage_Contributors,
				Messages.MetaMetadataPage_PersonsAndOrganizationsThatHave, 2);
		form.masterDetail(contributors, metaMetadata, lom().getMetaMetadata_Contribute(),
				object -> LifeCyclePage.contributeLabel(object, lom().getContributeMeta_Role(),
						lom().getContributeMeta_Entity()),
				() -> lom().getLomFactory().createContributeMeta(),
				(detail, parent) -> LifeCyclePage.contributeDetail(detail, parent, lom().getContributeMeta_Role(),
						lom().getContributeMeta_Entity(), lom().getContributeMeta_Date(), "metaContribute", "entity",
						"date"));
	}
}
