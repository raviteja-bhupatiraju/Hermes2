/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Healthcare LOM best practice rules that the XML schemas don't (or can't) enforce, such as consistency
 * between a yes/no flag and its explanation.
 */
public final class HlomRules {

	private HlomRules() {
	}

	/** Checks a document and returns the problems found. */
	public static List<HlomProblem> check(DocumentRoot root) {
		List<HlomProblem> problems = new ArrayList<>();
		Lom lom = root.getLom();
		if (lom == null) {
			return problems;
		}
		checkGeneral(lom, problems);
		checkMetaMetadata(lom, problems);
		checkLanguageStrings(lom, problems);
		problems.addAll(edu.louisiana.caai.lom.model.support.mesh.MeshTermValidator.checkKeywordIds(root));
		problems.addAll(edu.louisiana.caai.lom.model.support.terminology.TerminologyValidator.check(root));
		for (HealthcareMetadataType healthcare : lom.getHealthcareMetadata()) {
			HealthcareEducationType education = healthcare.getHealthcareEducation();
			if (education != null) {
				checkEducation(education, problems);
			}
		}
		return problems;
	}

	private static void checkGeneral(Lom lom, List<HlomProblem> problems) {
		General general = lom.getGeneral().isEmpty() ? null : lom.getGeneral().getFirst();
		if (general == null || general.getTitle().isEmpty() || isBlank(general.getTitle().getFirst())) {
			warn(problems, general != null ? general : lom, "The learning object has no title (1.2 General.Title).");
		}
		if (general == null || general.getDescription().isEmpty()) {
			info(problems, general != null ? general : lom,
					"The learning object has no description (1.4 General.Description).");
		}
	}

	/**
	 * HLOM "Additional Requirements": the record must declare conformance with LOM v1.0 and Healthcare LOM v1
	 * in metaMetadata/metadataSchema, and healthcareMetadata must occur once.
	 */
	private static void checkMetaMetadata(Lom lom, List<HlomProblem> problems) {
		MetaMetadata metaMetadata = lom.getMetaMetadata().isEmpty() ? null : lom.getMetaMetadata().getFirst();
		EObject target = metaMetadata != null ? metaMetadata : lom;
		for (String required : new String[] { HlomDocuments.LOM_METADATA_SCHEMA,
				HlomDocuments.HEALTHCARE_METADATA_SCHEMA }) {
			boolean declared = metaMetadata != null && metaMetadata.getMetadataSchema().stream()
					.anyMatch(schema -> schema.getValue() != null && required.equals(schema.getValue().trim()));
			if (!declared) {
				error(problems, target, "Meta-Metadata must declare the metadata schema \"" + required
						+ "\" (HLOM Additional Requirements).");
			}
		}
		if (lom.getHealthcareMetadata().isEmpty()) {
			error(problems, lom, "healthcareMetadata must occur once in a Healthcare LOM record.");
		}
	}

	private static void checkLanguageStrings(Lom lom, List<HlomProblem> problems) {
		for (TreeIterator<EObject> it = lom.eAllContents(); it.hasNext();) {
			EObject object = it.next();
			if (object instanceof LangString string) {
				if (string.getLanguage() == null || string.getLanguage().isBlank()) {
					warn(problems, string, "Text \"" + abbreviate(string.getValue()) + "\" has no language.");
				}
			}
		}
	}

	private static void checkEducation(HealthcareEducationType education, List<HlomProblem> problems) {
		if (is(education.isSetCreditsAvailable(), education.getCreditsAvailable(), YesNoType.YES)
				&& education.getCredits().isEmpty()) {
			warn(problems, education, "Credits are available, but no credit information is given.");
		}
		if (is(education.isSetCreditsAvailable(), education.getCreditsAvailable(), YesNoType.NO)
				&& !education.getCredits().isEmpty()) {
			warn(problems, education, "Credits are marked as not available, but credit information is given.");
		}
		for (CreditsType credits : education.getCredits()) {
			if (credits.getReleaseDate() != null && credits.getExpirationDate() != null
					&& credits.getReleaseDate().compare(credits.getExpirationDate()) > 0) {
				error(problems, credits, "The credit release date is after the credit expiration date.");
			}
		}
		if (education.getStartDateTime() != null && education.getEndDateTime() != null
				&& education.getStartDateTime().compare(education.getEndDateTime()) > 0) {
			error(problems, education, "The activity starts after it ends.");
		}
		if (is(education.isSetOffLabelUse(), education.getOffLabelUse(), YesNoType.YES) && isBlank(education.getOffLabelDescription())) {
			warn(problems, education, "Off-label use is indicated, but no off-label description is given.");
		}
		if (is(education.isSetCommercialSupport(), education.getCommercialSupport(), YesNoType.YES)
				&& isBlank(education.getCommercialSupportAcknowledgement())) {
			warn(problems, education, "Commercial support is indicated, but no acknowledgement is given.");
		}
		if (is(education.isSetRelevantFinancialRelationship(), education.getRelevantFinancialRelationship(),
				YesNoType.YES)
				&& education.getRelevantFinancialRelationshipDisclosure().isEmpty()) {
			warn(problems, education,
					"A relevant financial relationship is indicated, but no disclosure is given.");
		}
	}

	/** Unset enumeration attributes report their first literal, so the set state must be checked as well. */
	private static boolean is(boolean isSet, YesNoType value, YesNoType expected) {
		return isSet && value == expected;
	}

	static boolean isBlank(LanguageString string) {
		return string == null || string.getString().stream().allMatch(s -> s.getValue() == null || s.getValue().isBlank());
	}

	private static String abbreviate(String value) {
		if (value == null) {
			return "";
		}
		return value.length() > 40 ? value.substring(0, 40) + "..." : value;
	}

	private static void error(List<HlomProblem> problems, EObject object, String message) {
		problems.add(new HlomProblem(Severity.ERROR, Origin.RULES, message, object, null, -1, -1));
	}

	private static void warn(List<HlomProblem> problems, EObject object, String message) {
		problems.add(new HlomProblem(Severity.WARNING, Origin.RULES, message, object, null, -1, -1));
	}

	private static void info(List<HlomProblem> problems, EObject object, String message) {
		problems.add(new HlomProblem(Severity.INFO, Origin.RULES, message, object, null, -1, -1));
	}
}
