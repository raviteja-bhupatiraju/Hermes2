/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.model.lom.Annotation;

/**
 * 8 Annotation: comments on the educational use of the learning object, with who made them and when.
 */
public class AnnotationPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.AnnotationPage_Annotation;
	}

	@Override
	public String getHeading() {
		return "8 Annotation";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		Composite area = wide(form, body);
		Composite annotations = form.section(area, Messages.AnnotationPage_Annotations,
				Messages.AnnotationPage_CommentsOnTheEducationalUse, 2);
		form.masterDetail(annotations, ModelPath.self(), lom().getLom_Annotation(), AnnotationPage::annotationLabel,
				() -> lom().getLomFactory().createAnnotation(), AnnotationPage::createDetail);
	}

	private static void createDetail(FormSupport detail, Composite parent) {
		ModelPath entity = ModelPath.of(lom().getAnnotation_Entity());
		detail.multiText(parent, Messages.AnnotationPage_EntityVCard, entity, lom().getVCard_Value(), "entity", 4);
		detail.toolkit().createLabel(parent, "");
		Button compose = detail.toolkit().createButton(parent, Messages.AnnotationPage_ComposeVCard, SWT.PUSH);
		compose.setToolTipText(Messages.AnnotationPage_CreateTheVCardFromName);
		compose.addListener(SWT.Selection, _ -> {
			EObject anchor = detail.anchor().getValue();
			VCardDialog dialog = new VCardDialog(parent.getShell());
			if (anchor != null && dialog.open() == VCardDialog.OK && dialog.getVCard() != null) {
				ModelCommands.execute(detail.context().domain(), ModelCommands.setValue(detail.context().domain(),
						anchor, entity, lom().getVCard_Value(), dialog.getVCard()));
			}
		});
		detail.bindEnabled(compose);
		detail.text(parent, Messages.AnnotationPage_Date, ModelPath.of(lom().getAnnotation_Date()).then(lom().getDateTime_DateTime()),
				lom().getDateTimeValue_Value(), "date");
		detail.toolkit().createLabel(parent, "");
		detail.hint(parent, "YYYY-MM-DD");
		detail.langText(parent, Messages.AnnotationPage_Description, ModelPath.of(lom().getAnnotation_Description()), true, "annotation");
	}

	/** Labels an annotation by the annotator's name and the date. */
	static String annotationLabel(EObject object) {
		Annotation annotation = (Annotation) object;
		String name = annotation.getEntity().isEmpty() ? null : fullName(annotation.getEntity().getFirst().getValue());
		EObject dateTime = annotation.getDate().isEmpty() ? null
				: ModelPath.first(annotation.getDate().getFirst(), lom().getDateTime_DateTime());
		Object date = dateTime == null ? null : dateTime.eGet(lom().getDateTimeValue_Value());
		if (name == null) {
			return date == null ? null : date.toString();
		}
		return date == null ? name : name + " (" + date + ")";
	}

	/** The formatted name ({@code FN}) of a vCard, or {@code null}. */
	private static String fullName(String vCard) {
		if (vCard == null) {
			return null;
		}
		for (String line : vCard.split("\\R")) {
			String trimmed = line.trim();
			int colon = trimmed.indexOf(':');
			if (colon >= 2 && trimmed.regionMatches(true, 0, "FN", 0, 2)
					&& (colon == 2 || trimmed.charAt(2) == ';')) {
				return trimmed.substring(colon + 1).trim();
			}
		}
		return null;
	}
}
