/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;

/**
 * Healthcare Asset: healthcare specific metadata for images, multimedia files and other assets that contribute
 * to an educational offering.
 */
public class HealthcareAssetPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.HealthcareAssetPage_HealthcareAsset;
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath asset = ModelPath.of(lom().getLom_HealthcareMetadata())
				.then(hx().getHealthcareMetadataType_HealthcareAsset());

		Composite left = column(form, body);
		Composite subject = form.section(left, Messages.HealthcareAssetPage_Subject, Messages.HealthcareAssetPage_ThePatientSubjectOrSpecimen,
				2);
		form.langText(subject, Messages.HealthcareAssetPage_ClinicalHistory, asset.then(hx().getHealthcareAssetType_ClinicalHistory()), true,
				"clinicalHistory");
		form.enumCombo(subject, Messages.HealthcareAssetPage_SpecimenType, asset, hx().getHealthcareAssetType_SpecimenType(), "specimenType");

		Composite right = column(form, body);
		Composite image = form.section(right, Messages.HealthcareAssetPage_Image, Messages.HealthcareAssetPage_CharacteristicsOfTheImageS, 2);
		form.enumCombo(image, Messages.HealthcareAssetPage_MedicalImageType, asset, hx().getHealthcareAssetType_MedicalImageType(),
				"medicalImageType");
		form.enumCombo(image, Messages.HealthcareAssetPage_Annotated, asset, hx().getHealthcareAssetType_Annotated(), "annotated");
		form.enumCombo(image, Messages.HealthcareAssetPage_Orientation, asset, hx().getHealthcareAssetType_Orientation(), "orientation");
		form.text(image, Messages.HealthcareAssetPage_Magnification, asset, hx().getHealthcareAssetType_Magnification(), "magnification");
		form.text(image, Messages.HealthcareAssetPage_FileWidth, asset, hx().getHealthcareAssetType_FileWidth(), "fileWidth");
		form.text(image, Messages.HealthcareAssetPage_FileHeight, asset, hx().getHealthcareAssetType_FileHeight(), "fileHeight");
		form.toolkit().createLabel(image, "");
		Label hint = form.hint(image, Messages.HealthcareAssetPage_WidthAndHeightInPixels);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(hint);
	}
}
