/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.model.lom.Relation;
import edu.louisiana.caai.lom.model.lom.Resource;
import edu.louisiana.caai.lom.model.support.Vocabularies;

/**
 * 7 Relation: relationships between this learning object and other (target) learning objects.
 */
public class RelationPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.RelationPage_Relation;
	}

	@Override
	public String getHeading() {
		return "7 Relation";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		Composite area = wide(form, body);
		Composite relations = form.section(area, Messages.RelationPage_Relations,
				Messages.RelationPage_OtherLearningObjectsRelatedTo, 2);
		form.masterDetail(relations, ModelPath.self(), lom().getLom_Relation(), RelationPage::relationLabel,
				() -> lom().getLomFactory().createRelation(), RelationPage::createDetail);
	}

	private static void createDetail(FormSupport detail, Composite parent) {
		detail.vocabCombo(parent, Messages.RelationPage_Kind, ModelPath.of(lom().getRelation_Kind()), "kind");

		ModelPath resource = ModelPath.of(lom().getRelation_Resource());
		Composite target = detail.section(parent, Messages.RelationPage_TargetResource, Messages.RelationPage_CatalogEntriesIdentifyingTheTarget, 2);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(target.getParent());
		detail.masterDetail(target, resource, lom().getResource_Identifier(), GeneralPage::identifierLabel,
				() -> lom().getLomFactory().createIdentifier(), (identifier, fields) -> {
					identifier.text(fields, Messages.RelationPage_Catalog, ModelPath.of(lom().getIdentifier_Catalog()),
							lom().getCatalog_Value(), "resource");
					identifier.text(fields, Messages.RelationPage_Entry, ModelPath.of(lom().getIdentifier_Entry()), lom().getEntry_Value(),
							"resource");
				});
		detail.langList(target, Messages.RelationPage_Description, resource, lom().getResource_Description(), null, false, "resource");
	}

	/** Labels a relation by its kind and the entry of the target's first identifier. */
	static String relationLabel(EObject object) {
		Relation relation = (Relation) object;
		String kind = relation.getKind().isEmpty() ? null : Vocabularies.getValue(relation.getKind().getFirst());
		String entry = null;
		if (!relation.getResource().isEmpty()) {
			Resource resource = relation.getResource().getFirst();
			if (!resource.getIdentifier().isEmpty() && !resource.getIdentifier().getFirst().getEntry().isEmpty()) {
				entry = resource.getIdentifier().getFirst().getEntry().getFirst().getValue();
			}
		}
		if (kind == null) {
			return entry;
		}
		return entry == null || entry.isBlank() ? kind : kind + ": " + entry;
	}
}
