/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareAssetType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * One CSV row converted into a Healthcare LOM record, with the notes of the conversion and the validation
 * problems of the result.
 */
public final class ImportedRecord {

	private final int row;
	private final DocumentRoot root;
	private final List<ImportMessage> messages;
	private final List<HlomProblem> problems;
	private boolean included = true;
	private String fileName;

	ImportedRecord(int row, DocumentRoot root, List<ImportMessage> messages, List<HlomProblem> problems) {
		this.row = row;
		this.root = root;
		this.messages = List.copyOf(messages);
		this.problems = List.copyOf(problems);
	}

	/** The 1-based number of the data row (the header is row 0). */
	public int row() {
		return row;
	}

	/** The generated record. */
	public DocumentRoot root() {
		return root;
	}

	/** Notes about how the row's cells were interpreted. */
	public List<ImportMessage> messages() {
		return messages;
	}

	/** Validation problems of the generated record (schema, model and HLOM rules). */
	public List<HlomProblem> problems() {
		return problems;
	}

	/** Whether the record is to be written (initially {@code true}). */
	public boolean isIncluded() {
		return included;
	}

	/** Includes or excludes the record from writing. */
	public void setIncluded(boolean included) {
		this.included = included;
	}

	/** The file name chosen when the record was written or planned, or {@code null}. */
	public String fileName() {
		return fileName;
	}

	void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/** The number of import messages and validation problems of a severity. */
	public int count(Severity severity) {
		return (int) (messages.stream().filter(m -> m.severity() == severity).count()
				+ problems.stream().filter(p -> p.severity() == severity).count());
	}

	/** The worst severity of all messages and problems, or {@code null} if there are none. */
	public Severity severity() {
		for (Severity severity : Severity.values()) {
			if (count(severity) > 0) {
				return severity;
			}
		}
		return null;
	}

	/** All messages and problems as text lines, most severe first. */
	public List<String> describeIssues() {
		List<String> lines = new ArrayList<>();
		for (Severity severity : Severity.values()) {
			for (ImportMessage message : messages) {
				if (message.severity() == severity) {
					lines.add(label(severity) + message.describe());
				}
			}
			for (HlomProblem problem : problems) {
				if (problem.severity() == severity) {
					lines.add(label(severity) + problem.message());
				}
			}
		}
		return lines;
	}

	private static String label(Severity severity) {
		return switch (severity) {
			case ERROR -> "Error: ";
			case WARNING -> "Warning: ";
			case INFO -> "Note: ";
		};
	}

	/** The record title (first title string), or an empty string. */
	public String title() {
		Lom lom = root.getLom();
		if (lom == null || lom.getGeneral().isEmpty() || lom.getGeneral().getFirst().getTitle().isEmpty()) {
			return "";
		}
		for (LangString string : lom.getGeneral().getFirst().getTitle().getFirst().getString()) {
			if (string.getValue() != null && !string.getValue().isBlank()) {
				return string.getValue().strip();
			}
		}
		return "";
	}

	/**
	 * A short description of what the record is, e.g. "CME course", "Live activity", "Medical image · radiograph",
	 * "Patient education" or the learning resource type.
	 */
	public String kind() {
		Lom lom = root.getLom();
		HealthcareMetadataType healthcare = lom.getHealthcareMetadata().isEmpty() ? null
				: lom.getHealthcareMetadata().getFirst();
		HealthcareAssetType asset = healthcare == null ? null : healthcare.getHealthcareAsset();
		HealthcareEducationType education = healthcare == null ? null : healthcare.getHealthcareEducation();
		if (asset != null && asset.isSetMedicalImageType()) {
			return "Medical image · " + asset.getMedicalImageType().getLiteral();
		}
		if (asset != null) {
			return "Healthcare asset";
		}
		if (education != null) {
			if (education.getActivityDelivery().contains(ActivityDeliveryType.LIVE)
					|| education.getParticipationModality().contains(ParticipationModalityType.CONFERENCE_WORKSHOP)
					|| education.getStartDateTime() != null) {
				return "Live activity";
			}
			if (education.getTargetAudience() != null
					&& (education.getTargetAudience().getAudienceCategory().contains(AudienceCategoryType.PATIENT)
							|| education.getTargetAudience().getAudienceCategory()
									.contains(AudienceCategoryType.CAREGIVER))) {
				return "Patient education";
			}
			Set<String> types = new LinkedHashSet<>();
			for (CreditsType credits : education.getCredits()) {
				if (credits.isSetCreditType()) {
					types.add(credits.getCreditType().getLiteral());
				}
			}
			if (!types.isEmpty()) {
				return String.join("/", types) + " course";
			}
		}
		if (!lom.getEducational().isEmpty() && !lom.getEducational().getFirst().getLearningResourceType().isEmpty()) {
			String type = Vocabularies.getValue(lom.getEducational().getFirst().getLearningResourceType().getFirst());
			if (type != null && !type.isEmpty()) {
				return type.substring(0, 1).toUpperCase(Locale.ROOT) + type.substring(1);
			}
		}
		return "Learning resource";
	}

	/** The credits offered, e.g. "1.5 CME Credit · 2 CNE Contact Hour", or an empty string. */
	public String credits() {
		Lom lom = root.getLom();
		if (lom.getHealthcareMetadata().isEmpty()
				|| lom.getHealthcareMetadata().getFirst().getHealthcareEducation() == null) {
			return "";
		}
		List<String> parts = new ArrayList<>();
		for (CreditsType credits : lom.getHealthcareMetadata().getFirst().getHealthcareEducation().getCredits()) {
			StringBuilder part = new StringBuilder();
			BigDecimal number = credits.getNumberOfCredits();
			if (number != null) {
				part.append(number.stripTrailingZeros().toPlainString());
			}
			if (credits.isSetCreditType()) {
				part.append(part.isEmpty() ? "" : " ").append(credits.getCreditType().getLiteral());
			}
			if (credits.isSetCreditUnit() && number != null) {
				part.append(' ').append(credits.getCreditUnit().getLiteral());
			}
			if (!part.isEmpty()) {
				parts.add(part.toString());
			}
		}
		return String.join(" · ", parts);
	}

	@Override
	public String toString() {
		return "Row " + row + ": " + title() + " (" + kind() + ")";
	}
}
