/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.lom.Educational;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * 5 Educational: the key educational and pedagogic characteristics of the learning object. Educational
 * elements may repeat (one per intended use), so they are edited in a master/detail block.
 */
public class EducationalPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.EducationalPage_Educational;
	}

	@Override
	public String getHeading() {
		return "5 Educational";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		Composite area = wide(form, body);
		Composite uses = form.section(area, Messages.EducationalPage_EducationalUses,
				Messages.EducationalPage_EducationalCharacteristicsAddOneEntry, 2);
		form.masterDetail(uses, ModelPath.self(), lom().getLom_Educational(), EducationalPage::educationalLabel,
				() -> lom().getLomFactory().createEducational(), EducationalPage::createDetail);
	}

	private static void createDetail(FormSupport detail, Composite parent) {
		ModelPath self = ModelPath.self();
		detail.vocabCombo(parent, Messages.EducationalPage_InteractivityType, ModelPath.of(lom().getEducational_InteractivityType()),
				"interactivityType");
		detail.vocabChecks(parent, Messages.EducationalPage_LearningResourceTypes, self, lom().getEducational_LearningResourceType(), 3,
				"learningResourceType");
		detail.vocabCombo(parent, Messages.EducationalPage_InteractivityLevel, ModelPath.of(lom().getEducational_InteractivityLevel()),
				"interactivityLevel");
		detail.vocabCombo(parent, Messages.EducationalPage_SemanticDensity, ModelPath.of(lom().getEducational_SemanticDensity()),
				"semanticDensity");
		detail.vocabChecks(parent, Messages.EducationalPage_IntendedEndUserRoles, self, lom().getEducational_IntendedEndUserRole(), 3,
				"intendedEndUserRole");
		detail.vocabChecks(parent, Messages.EducationalPage_Contexts, self, lom().getEducational_Context(), 3, "context");
		detail.langList(parent, Messages.EducationalPage_TypicalAgeRange, self, lom().getEducational_TypicalAgeRange(), null, false,
				"typicalAgeRange");
		detail.vocabCombo(parent, Messages.EducationalPage_Difficulty, ModelPath.of(lom().getEducational_Difficulty()), "difficulty");
		detail.text(parent, Messages.EducationalPage_TypicalLearningTime,
				ModelPath.of(lom().getEducational_TypicalLearningTime()).then(lom().getDurationBase_Duration()),
				lom().getDurationValue_Value(), "typicalLearningTime");
		detail.toolkit().createLabel(parent, "");
		detail.hint(parent, Messages.EducationalPage_ISO8601DurationEG);
		detail.langList(parent, Messages.EducationalPage_Descriptions, self, lom().getEducational_Description(), null, false,
				"educationalDescription");
		detail.valueList(parent, Messages.EducationalPage_Languages, ValueList.Kind.ELEMENTS, self, lom().getEducational_Language(),
				lom().getLanguageId_Value(), HlomSuggestions.LANGUAGES, "educationalLanguage");
	}

	/** Labels an educational element by its first learning resource type and context, or its position. */
	static String educationalLabel(EObject object) {
		Educational educational = (Educational) object;
		String type = firstValue(educational.getLearningResourceType());
		String context = firstValue(educational.getContext());
		if (type != null) {
			return context == null ? type : type + " (" + context + ")";
		}
		if (context != null) {
			return context;
		}
		EObject container = educational.eContainer();
		int index = container == null ? 0 : ((List<?>) container.eGet(lom().getLom_Educational())).indexOf(educational);
		return NLS.bind(Messages.EducationalPage_EducationalUse, (index + 1));
	}

	private static String firstValue(List<? extends EObject> vocabularies) {
		return vocabularies.isEmpty() ? null : Vocabularies.getValue(vocabularies.getFirst());
	}
}
