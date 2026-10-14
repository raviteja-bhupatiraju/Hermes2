/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.VCard;

/**
 * 2 Life Cycle: the history and current state of the learning object and those who have affected it.
 */
public class LifeCyclePage extends AbstractLomPage {

	/** Example shown below vCard entity lists. */
	static final String VCARD_HINT = "People and organizations are stored as vCards; \"Add vCard…\" composes one.";

	@Override
	public String getTitle() {
		return Messages.LifeCyclePage_LifeCycle;
	}

	@Override
	public String getHeading() {
		return Messages.LifeCyclePage_2LifeCycle;
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		ModelPath lifeCycle = ModelPath.of(lom().getLom_LifeCycle());

		Composite left = column(form, body);
		Composite state = form.section(left, Messages.LifeCyclePage_State, Messages.LifeCyclePage_TheEditionAndCompletionStatus,
				2);
		form.langText(state, Messages.LifeCyclePage_Version, lifeCycle.then(lom().getLifeCycle_Version()), false, "version");
		form.vocabCombo(state, Messages.LifeCyclePage_Status, lifeCycle.then(lom().getLifeCycle_Status()), "status");

		Composite right = wide(form, body);
		Composite contributors = form.section(right, Messages.LifeCyclePage_Contributors,
				Messages.LifeCyclePage_PersonsAndOrganizationsThatHave,
				2);
		form.masterDetail(contributors, lifeCycle, lom().getLifeCycle_Contribute(),
				object -> contributeLabel(object, lom().getContribute_Role(), lom().getContribute_Entity()),
				() -> lom().getLomFactory().createContribute(),
				(detail, parent) -> contributeDetail(detail, parent, lom().getContribute_Role(),
						lom().getContribute_Entity(), lom().getContribute_Date(), "role", "entity", "date"));
	}

	/**
	 * Creates the detail fields of a contribution (life cycle or meta-metadata): role, entities with a vCard
	 * helper, date and date description.
	 */
	static void contributeDetail(FormSupport detail, Composite parent, EReference role, EReference entity,
			EReference date, String roleHelp, String entityHelp, String dateHelp) {
		LomPackage lom = LomPackage.eINSTANCE;
		detail.vocabCombo(parent, Messages.LifeCyclePage_Role, ModelPath.of(role), roleHelp);
		detail.valueList(parent, Messages.LifeCyclePage_Entities, ValueList.Kind.ELEMENTS, ModelPath.self(), entity,
				lom.getVCard_Value(), List.of(), entityHelp);

		detail.toolkit().createLabel(parent, "");
		Composite row = detail.toolkit().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(8, 0).applyTo(row);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(row);
		Button add = detail.toolkit().createButton(row, Messages.LifeCyclePage_AddVCard, SWT.PUSH);
		add.setToolTipText(Messages.LifeCyclePage_ComposeAVCardFromName);
		add.addListener(SWT.Selection, _ -> {
			EObject anchor = detail.anchor().getValue();
			if (anchor == null) {
				return;
			}
			VCardDialog dialog = new VCardDialog(parent.getShell());
			if (dialog.open() == VCardDialog.OK && dialog.getVCard() != null) {
				VCard vcard = lom.getLomFactory().createVCard();
				vcard.setValue(dialog.getVCard());
				ModelCommands.execute(detail.context().domain(), ModelCommands.addElement(detail.context().domain(),
						anchor, ModelPath.self(), entity, vcard));
			}
		});
		detail.hint(row, VCARD_HINT);
		detail.bindEnabled(row);

		ModelPath datePath = ModelPath.of(date);
		detail.text(parent, Messages.LifeCyclePage_Date, datePath.then(lom.getDateTime_DateTime()), lom.getDateTimeValue_Value(),
				dateHelp);
		detail.langText(parent, Messages.LifeCyclePage_DateDescription, datePath.then(lom.getDateTime_Description()), false, dateHelp);
	}

	/** Master label of a contribution: role and the formatted name of the first entity. */
	static String contributeLabel(EObject contribute, EReference role, EReference entity) {
		String roles = String.join(", ", ModelCommands.vocabularyValues(contribute, role));
		List<?> entities = (List<?>) contribute.eGet(entity);
		String name = null;
		if (!entities.isEmpty()) {
			String value = ((VCard) entities.getFirst()).getValue();
			name = VCardDialog.formattedName(value);
			if (name == null && value != null) {
				name = value.strip().lines().findFirst().orElse(null);
			}
		}
		if (name == null || name.isBlank()) {
			return roles;
		}
		return roles.isEmpty() ? name : roles + ": " + name;
	}
}
