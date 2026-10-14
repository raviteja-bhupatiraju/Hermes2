/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.lom.OrComposite;
import edu.louisiana.caai.lom.model.lom.Requirement;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * 4 Technical: technical requirements and characteristics of the learning object.
 */
public class TechnicalPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.TechnicalPage_Technical;
	}

	@Override
	public String getHeading() {
		return "4 Technical";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath technical = ModelPath.of(lom().getLom_Technical());

		Composite left = column(form, body);
		Composite characteristics = form.section(left, Messages.TechnicalPage_Characteristics,
				Messages.TechnicalPage_DataTypesSizeAndLocation, 2);
		form.valueList(characteristics, Messages.TechnicalPage_Formats, ValueList.Kind.ELEMENTS, technical, lom().getTechnical_Format(),
				lom().getFormat_Value(), HlomSuggestions.MIME_TYPES, "format");
		form.text(characteristics, Messages.TechnicalPage_SizeBytes, technical.then(lom().getTechnical_Size()), lom().getSize_Value(),
				"size");
		form.valueList(characteristics, Messages.TechnicalPage_Locations, ValueList.Kind.ELEMENTS, technical,
				lom().getTechnical_Location(), lom().getLocation_Value(), List.of(), "location");

		Composite duration = form.section(left, Messages.TechnicalPage_Duration, Messages.TechnicalPage_TimeAContinuousLearningObject,
				2);
		ModelPath durationPath = technical.then(lom().getTechnical_Duration());
		form.text(duration, Messages.TechnicalPage_Duration, durationPath.then(lom().getDurationBase_Duration()),
				lom().getDurationValue_Value(), "duration");
		form.toolkit().createLabel(duration, "");
		form.hint(duration, Messages.TechnicalPage_ISO8601EGPT1H30M);
		form.langText(duration, Messages.TechnicalPage_Description, durationPath.then(lom().getDurationBase_Description()), false,
				"duration");

		Composite right = column(form, body);
		Composite requirements = form.section(right, Messages.TechnicalPage_Requirements,
				Messages.TechnicalPage_TechnicalCapabilitiesNecessaryForUsing,
				2);
		form.masterDetail(requirements, technical, lom().getTechnical_Requirement(), TechnicalPage::requirementLabel,
				() -> lom().getLomFactory().createRequirement(), (detail, parent) -> {
					detail.label(parent, Messages.TechnicalPage_Alternatives, "requirement");
					detail.toolkit().createLabel(parent, "");
					detail.masterDetail(parent, ModelPath.self(), lom().getRequirement_OrComposite(),
							TechnicalPage::orCompositeLabel, () -> lom().getLomFactory().createOrComposite(),
							(alternative, fields) -> {
								alternative.vocabCombo(fields, Messages.TechnicalPage_Type, ModelPath.of(lom().getOrComposite_Type()),
										"requirement");
								alternative.vocabCombo(fields, Messages.TechnicalPage_Name, ModelPath.of(lom().getOrComposite_Name()),
										"requirement");
								alternative.text(fields, Messages.TechnicalPage_MinimumVersion,
										ModelPath.of(lom().getOrComposite_MinimumVersion()),
										lom().getMinimumVersion_Value(), "requirement");
								alternative.text(fields, Messages.TechnicalPage_MaximumVersion,
										ModelPath.of(lom().getOrComposite_MaximumVersion()),
										lom().getMaximumVersion_Value(), "requirement");
							});
				});

		Composite remarks = form.section(right, Messages.TechnicalPage_Remarks, null, 2);
		form.langText(remarks, Messages.TechnicalPage_InstallationRemarks, technical.then(lom().getTechnical_InstallationRemarks()), true,
				"installationRemarks");
		form.langText(remarks, Messages.TechnicalPage_OtherPlatformRequirements,
				technical.then(lom().getTechnical_OtherPlatformRequirements()), true, "otherPlatformRequirements");
	}

	static String requirementLabel(EObject object) {
		List<String> labels = ((Requirement) object).getOrComposite().stream()
				.map(TechnicalPage::orCompositeLabel).filter(label -> !label.isBlank()).toList();
		return String.join(Messages.TechnicalPage_Or, labels);
	}

	/** Label of a requirement alternative: "name (type) min-max". */
	static String orCompositeLabel(EObject object) {
		OrComposite composite = (OrComposite) object;
		String type = first(ModelCommands.vocabularyValues(composite, lom().getOrComposite_Type()));
		String name = first(ModelCommands.vocabularyValues(composite, lom().getOrComposite_Name()));
		String min = composite.getMinimumVersion().isEmpty() ? null : composite.getMinimumVersion().getFirst().getValue();
		String max = composite.getMaximumVersion().isEmpty() ? null : composite.getMaximumVersion().getFirst().getValue();
		StringBuilder builder = new StringBuilder(name != null ? name : type != null ? type : "");
		if (name != null && type != null) {
			builder.append(" (").append(type).append(')');
		}
		if (min != null || max != null) {
			builder.append(' ').append(min != null ? min : "").append('-').append(max != null ? max : "");
		}
		return builder.toString().strip();
	}

	private static String first(List<String> values) {
		return values.isEmpty() ? null : values.getFirst();
	}
}
