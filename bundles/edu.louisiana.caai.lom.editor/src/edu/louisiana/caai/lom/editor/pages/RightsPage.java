/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;

/**
 * 6 Rights: intellectual property rights and conditions of use of the learning object.
 */
public class RightsPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.RightsPage_Rights;
	}

	@Override
	public String getHeading() {
		return "6 Rights";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath rights = ModelPath.of(lom().getLom_Rights());

		Composite left = column(form, body);
		Composite conditions = form.section(left, Messages.RightsPage_ConditionsOfUse,
				Messages.RightsPage_WhetherUseRequiresPaymentAnd, 2);
		form.vocabCombo(conditions, Messages.RightsPage_Cost, rights.then(lom().getRights_Cost()), "cost");
		form.vocabCombo(conditions, Messages.RightsPage_CopyrightAndOtherRestrictions,
				rights.then(lom().getRights_CopyrightAndOtherRestrictions()), "copyrightAndOtherRestrictions");

		Composite right = column(form, body);
		Composite description = form.section(right, Messages.RightsPage_Description, Messages.RightsPage_CommentsOnTheConditionsOf, 2);
		form.langText(description, Messages.RightsPage_Description, rights.then(lom().getRights_Description()), true,
				"rightsDescription");
	}
}
