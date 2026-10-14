/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.lom.Identifier;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * 1 General: identification, title, description, keywords and classification of the learning object as a
 * whole.
 */
public class GeneralPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.GeneralPage_General;
	}

	@Override
	public String getHeading() {
		return "1 General";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath general = ModelPath.of(lom().getLom_General());

		Composite left = column(form, body);
		Composite description = form.section(left, Messages.GeneralPage_TitleAndDescription,
				Messages.GeneralPage_WhatTheLearningObjectIs, 2);
		form.langText(description, Messages.GeneralPage_Title, general.then(lom().getGeneral_Title()), false, "title");
		form.langList(description, Messages.GeneralPage_Description, general, lom().getGeneral_Description(), null, false,
				"description");
		form.langList(description, Messages.GeneralPage_Keywords, general, lom().getGeneral_Keyword(), null, true, "keyword");
		form.langList(description, Messages.GeneralPage_Coverage, general, lom().getGeneral_Coverage(), null, false, "coverage");

		Composite right = column(form, body);
		Composite identification = form.section(right, Messages.GeneralPage_Identification, Messages.GeneralPage_CatalogEntriesIdentifyingThisObject,
				2);
		form.masterDetail(identification, general, lom().getGeneral_Identifier(), GeneralPage::identifierLabel,
				() -> lom().getLomFactory().createIdentifier(), (detail, parent) -> {
					detail.text(parent, Messages.GeneralPage_Catalog, ModelPath.of(lom().getIdentifier_Catalog()),
							lom().getCatalog_Value(), "identifier");
					detail.text(parent, Messages.GeneralPage_Entry, ModelPath.of(lom().getIdentifier_Entry()), lom().getEntry_Value(),
							"identifier");
				});

		Composite characteristics = form.section(right, Messages.GeneralPage_Characteristics, null, 2);
		form.valueList(characteristics, Messages.GeneralPage_Languages, ValueList.Kind.ELEMENTS, general, lom().getGeneral_Language(),
				lom().getLanguageId_Value(), HlomSuggestions.LANGUAGES, "language");
		form.vocabCombo(characteristics, Messages.GeneralPage_Structure, general.then(lom().getGeneral_Structure()), "structure");
		form.vocabCombo(characteristics, Messages.GeneralPage_AggregationLevel, general.then(lom().getGeneral_AggregationLevel()),
				"aggregationLevel");
	}

	static String identifierLabel(EObject object) {
		Identifier identifier = (Identifier) object;
		String catalog = identifier.getCatalog().isEmpty() ? "" : identifier.getCatalog().getFirst().getValue();
		String entry = identifier.getEntry().isEmpty() ? "" : identifier.getEntry().getFirst().getValue();
		return (catalog == null ? "" : catalog + ": ") + (entry == null ? "" : entry);
	}
}
