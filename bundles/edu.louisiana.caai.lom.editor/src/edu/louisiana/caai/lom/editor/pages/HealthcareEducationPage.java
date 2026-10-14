/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * Healthcare Education: the healthcare specific metadata of an educational offering (credits, target audience,
 * schedule, location and disclosures).
 */
public class HealthcareEducationPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.HealthcareEducationPage_HealthcareEducation;
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath edu = ModelPath.of(lom().getLom_HealthcareMetadata())
				.then(hx().getHealthcareMetadataType_HealthcareEducation());

		Composite left = column(form, body);
		Composite offering = form.section(left, Messages.HealthcareEducationPage_EducationalOffering,
				Messages.HealthcareEducationPage_HowTheActivityIsOffered, 2);
		form.text(offering, Messages.HealthcareEducationPage_ExpirationDate, edu, hx().getHealthcareEducationType_ExpirationDate(),
				"expirationDate");
		form.text(offering, Messages.HealthcareEducationPage_Contact, edu, hx().getHealthcareEducationType_Contact(), "contact");
		form.enumCombo(offering, Messages.HealthcareEducationPage_ActivitySponsorship, edu, hx().getHealthcareEducationType_ActivitySponsorship(),
				"activitySponsorship");
		form.enumChecks(offering, Messages.HealthcareEducationPage_ParticipationModality, edu,
				hx().getHealthcareEducationType_ParticipationModality(), 2, "participationModality");
		form.enumChecks(offering, Messages.HealthcareEducationPage_ActivityDelivery, edu, hx().getHealthcareEducationType_ActivityDelivery(), 2,
				"activityDelivery");
		form.langList(offering, Messages.HealthcareEducationPage_ActivityFormat, edu, hx().getHealthcareEducationType_ActivityFormat(), null,
				false, "activityFormat");

		Composite schedule = form.section(left, Messages.HealthcareEducationPage_Schedule, Messages.HealthcareEducationPage_WhenALiveActivityTakes, 2);
		form.text(schedule, Messages.HealthcareEducationPage_StartDateTime, edu, hx().getHealthcareEducationType_StartDateTime(), "startDateTime");
		form.text(schedule, Messages.HealthcareEducationPage_EndDateTime, edu, hx().getHealthcareEducationType_EndDateTime(), "endDateTime");

		ModelPath audience = edu.then(hx().getHealthcareEducationType_TargetAudience());
		Composite target = form.section(left, Messages.HealthcareEducationPage_TargetAudience, null, 2);
		form.enumChecks(target, Messages.HealthcareEducationPage_AudienceCategories, audience, hx().getTargetAudienceType_AudienceCategory(), 2,
				"audienceCategory");
		form.langList(target, Messages.HealthcareEducationPage_Professions, audience, hx().getTargetAudienceType_Profession(),
				HlomSuggestions.professionTerms(), false, "profession");
		form.langList(target, Messages.HealthcareEducationPage_Specialties, audience, hx().getTargetAudienceType_Specialty(),
				HlomSuggestions.SPECIALTIES, false, "specialty");
		form.langList(target, Messages.HealthcareEducationPage_ReadingLevels, audience, hx().getTargetAudienceType_ReadingLevel(),
				HlomSuggestions.READING_LEVELS, false, "readingLevel");

		Composite right = column(form, body);
		createLocation(form, right, edu.then(hx().getHealthcareEducationType_ActivityLocation()));

		Composite disclosures = form.section(right, Messages.HealthcareEducationPage_Disclosures, null, 2);
		form.enumCombo(disclosures, Messages.HealthcareEducationPage_OffLabelUse, edu, hx().getHealthcareEducationType_OffLabelUse(),
				"offLabelUse");
		form.langText(disclosures, Messages.HealthcareEducationPage_OffLabelDescription, edu.then(hx().getHealthcareEducationType_OffLabelDescription()),
				true, "offLabelDescription");
		form.enumCombo(disclosures, Messages.HealthcareEducationPage_CommercialSupport, edu, hx().getHealthcareEducationType_CommercialSupport(),
				"commercialSupport");
		form.langText(disclosures, Messages.HealthcareEducationPage_Acknowledgement,
				edu.then(hx().getHealthcareEducationType_CommercialSupportAcknowledgement()), true,
				"commercialSupportAcknowledgement");
		form.enumCombo(disclosures, Messages.HealthcareEducationPage_RelevantFinancialRelationship, edu,
				hx().getHealthcareEducationType_RelevantFinancialRelationship(), "relevantFinancialRelationship");
		form.langList(disclosures, Messages.HealthcareEducationPage_Disclosures, edu,
				hx().getHealthcareEducationType_RelevantFinancialRelationshipDisclosure(), null, false,
				"relevantFinancialRelationshipDisclosure");

		// Credits get the full width: the detail has many fields.
		Composite wideArea = wide(form, body);
		Composite credits = form.section(wideArea, Messages.HealthcareEducationPage_Credits,
				Messages.HealthcareEducationPage_AddOneCreditsEntryPer, 2);
		form.enumCombo(credits, Messages.HealthcareEducationPage_CreditsAvailable, edu, hx().getHealthcareEducationType_CreditsAvailable(),
				"creditsAvailable");
		form.masterDetail(credits, edu, hx().getHealthcareEducationType_Credits(), HealthcareEducationPage::creditsLabel,
				() -> hx().getHealthcareFactory().createCreditsType(), HealthcareEducationPage::creditsDetail);
	}

	private static void creditsDetail(FormSupport detail, Composite parent) {
		ModelPath self = ModelPath.self();
		detail.suggestText(parent, Messages.HealthcareEducationPage_AccreditingBody, self, hx().getCreditsType_AccreditingBody(),
				HlomSuggestions.ACCREDITING_BODIES, "accreditingBody");
		detail.suggestText(parent, Messages.HealthcareEducationPage_ActivityCertification, self, hx().getCreditsType_ActivityCertification(),
				HlomSuggestions.ACTIVITY_CERTIFICATIONS, "activityCertification");
		ComboViewer creditType = detail.enumCombo(parent, Messages.HealthcareEducationPage_CreditType, self, hx().getCreditsType_CreditType(),
				"creditType");
		creditType.getControl().setToolTipText(acronymTooltip("creditType", hx().getCreditsType_CreditType()));
		ComboViewer creditUnit = detail.enumCombo(parent, Messages.HealthcareEducationPage_CreditUnit, self, hx().getCreditsType_CreditUnit(),
				"creditUnit");
		creditUnit.getControl().setToolTipText(acronymTooltip("creditUnit", hx().getCreditsType_CreditUnit()));
		detail.text(parent, Messages.HealthcareEducationPage_NumberOfCredits, self, hx().getCreditsType_NumberOfCredits(), "numberOfCredits");
		detail.enumCombo(parent, Messages.HealthcareEducationPage_Pacing, self, hx().getCreditsType_Pacing(), "pacing");
		detail.text(parent, Messages.HealthcareEducationPage_AccreditedProvider, self, hx().getCreditsType_AccreditedProvider(),
				"accreditedProvider");
		detail.valueList(parent, Messages.HealthcareEducationPage_NonAccreditedProviders, ValueList.Kind.VALUES, self,
				hx().getCreditsType_NonAccreditedProvider(), null, null, "nonAccreditedProvider");
		detail.text(parent, Messages.HealthcareEducationPage_ReleaseDate, self, hx().getCreditsType_ReleaseDate(), "releaseDate");
		detail.text(parent, Messages.HealthcareEducationPage_ExpirationDate, self, hx().getCreditsType_ExpirationDate(), "creditExpirationDate");
	}

	private static void createLocation(FormSupport form, Composite column, ModelPath loc) {
		AddressPackage a = AddressPackage.eINSTANCE;
		Composite location = form.section(column, Messages.HealthcareEducationPage_ActivityLocation,
				Messages.HealthcareEducationPage_WhereAnInPersonActivity, 2);
		form.text(location, Messages.HealthcareEducationPage_Room, loc, hx().getActivityLocationType_Room(), "room");
		form.text(location, Messages.HealthcareEducationPage_Building, loc, hx().getActivityLocationType_Building(), "building");
		form.valueList(location, Messages.HealthcareEducationPage_Organizations, ValueList.Kind.VALUES, loc, a.getAddressType_Organization(), null,
				null, "activityLocation");
		form.valueList(location, Messages.HealthcareEducationPage_StreetAddressLines, ValueList.Kind.ELEMENTS, loc,
				a.getAddressType_StreetAddressLine(), a.getStreetAddressLineType_Value(), null, "activityLocation");
		form.text(location, Messages.HealthcareEducationPage_City, loc, a.getAddressType_City(), "activityLocation");
		form.text(location, Messages.HealthcareEducationPage_StateProvince, loc, a.getAddressType_StateOrProvince(), "activityLocation");
		form.text(location, Messages.HealthcareEducationPage_PostalCode, loc, a.getAddressType_PostalCode(), "activityLocation");
		form.text(location, Messages.HealthcareEducationPage_Region, loc, a.getAddressType_Region(), "activityLocation");
		form.text(location, Messages.HealthcareEducationPage_District, loc, a.getAddressType_District(), "activityLocation");
		ModelPath country = loc.then(a.getAddressType_Country());
		form.text(location, Messages.HealthcareEducationPage_CountryName, country, a.getCountryType_CountryName(), "country");
		form.text(location, Messages.HealthcareEducationPage_CountryCode, country, a.getCountryType_CountryCode(), "country");
		hint(form, location, Messages.HealthcareEducationPage_TheAddressSchemaRequiresA);
		form.enumCombo(location, Messages.HealthcareEducationPage_AddressCategory, loc, a.getAddressType_AddressCategory(), "activityLocation");
		form.enumCombo(location, Messages.HealthcareEducationPage_Restrictions, loc, a.getAddressType_Restrictions(), "activityLocation");
	}

	/** Adds a hint below the fields of a two-column grid, aligned with the field column. */
	private static void hint(FormSupport form, Composite parent, String text) {
		form.toolkit().createLabel(parent, "");
		Label hint = form.hint(parent, text);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(hint);
	}

	/** Help text of a field followed by the meaning of the acronyms among the enumeration's values. */
	private static String acronymTooltip(String helpKey, EAttribute attribute) {
		StringBuilder text = new StringBuilder();
		String help = FormSupport.help(helpKey);
		if (help != null) {
			text.append(help);
		}
		StringBuilder acronyms = new StringBuilder();
		if (attribute.getEAttributeType() instanceof EEnum eEnum) {
			for (EEnumLiteral literal : eEnum.getELiterals()) {
				String meaning = HlomSuggestions.CREDIT_ACRONYMS.get(literal.getLiteral());
				if (meaning != null) {
					acronyms.append(acronyms.isEmpty() ? "" : ", ").append(literal.getLiteral()).append(" = ")
							.append(meaning);
				}
			}
		}
		if (!acronyms.isEmpty()) {
			text.append(text.isEmpty() ? "" : "\n\n").append(acronyms);
		}
		return text.toString();
	}

	static String creditsLabel(EObject object) {
		CreditsType credits = (CreditsType) object;
		StringBuilder label = new StringBuilder();
		if (credits.isSetCreditType()) {
			label.append(credits.getCreditType().getLiteral());
		}
		BigDecimal number = credits.getNumberOfCredits();
		if (number != null) {
			label.append(label.isEmpty() ? "" : " ").append(number.toPlainString());
		}
		if (credits.isSetCreditUnit()) {
			label.append(label.isEmpty() ? "" : " ").append(credits.getCreditUnit().getLiteral());
		}
		return label.toString();
	}
}
