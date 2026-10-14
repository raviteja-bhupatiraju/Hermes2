/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.terminology.TerminologyLookupDialog;
import edu.louisiana.caai.lom.editor.terminology.TerminologySupport;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.model.lom.Classification;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.Purpose;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;

/**
 * 9 Classification: where the learning object falls within classification systems such as curricula,
 * competencies (e.g. ACGME), learning outcomes, guidelines, drug lists and levels of evidence.
 */
public class ClassificationPage extends AbstractLomPage {

	@Override
	public String getTitle() {
		return Messages.ClassificationPage_Classification;
	}

	@Override
	public String getHeading() {
		return "9 Classification";
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		Composite area = wide(form, body);
		Composite classifications = form.section(area, Messages.ClassificationPage_Classifications,
				Messages.ClassificationPage_OneEntryPerClassificationPurpose, 2);
		form.masterDetail(classifications, ModelPath.self(), lom().getLom_Classification(),
				ClassificationPage::classificationLabel, () -> lom().getLomFactory().createClassification(),
				ClassificationPage::classificationDetail);
	}

	private static void classificationDetail(FormSupport detail, Composite parent) {
		detail.vocabCombo(parent, Messages.ClassificationPage_Purpose, ModelPath.of(lom().getClassification_Purpose()), "purpose");

		Label taxonPaths = detail.label(parent, Messages.ClassificationPage_TaxonPaths, "taxonPath");
		GridDataFactory.swtDefaults().span(2, 1).applyTo(taxonPaths);
		detail.masterDetail(parent, ModelPath.self(), lom().getClassification_TaxonPath(),
				ClassificationPage::taxonPathLabel, () -> lom().getLomFactory().createTaxonPath(),
				ClassificationPage::taxonPathDetail);

		detail.langText(parent, Messages.ClassificationPage_Description, ModelPath.of(lom().getClassification_Description()), true,
				"classification");
		detail.langList(parent, Messages.ClassificationPage_Keywords, ModelPath.self(), lom().getClassification_Keyword(), null, true,
				"keyword");
	}

	private static void taxonPathDetail(FormSupport detail, Composite parent) {
		detail.suggestText(parent, Messages.ClassificationPage_Source, FormSupport.firstString(ModelPath.of(lom().getTaxonPath_Source())),
				lom().getLangString_Value(), HlomSuggestions.TAXONOMIES, "taxonSource");
		new Label(parent, SWT.NONE).setBackground(parent.getBackground());
		Hyperlink lookUp = detail.toolkit().createHyperlink(parent,
				Messages.ClassificationPage_LookUpInMeSHICD, SWT.NONE);
		lookUp.setToolTipText(Messages.ClassificationPage_ChooseAConceptTheSource);
		lookUp.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent event) {
				if (detail.anchor().getValue() instanceof TaxonPath path) {
					lookUpTaxonPath(detail, path, parent.getShell());
				}
			}
		});
		detail.bindEnabled(lookUp);

		Label taxa = detail.label(parent, Messages.ClassificationPage_TaxaGeneralToSpecific, "taxon");
		GridDataFactory.swtDefaults().span(2, 1).applyTo(taxa);
		detail.masterDetail(parent, ModelPath.self(), lom().getTaxonPath_Taxon(), ClassificationPage::taxonLabel,
				() -> lom().getLomFactory().createTaxon(), (taxonDetail, taxonParent) -> {
					taxonDetail.text(taxonParent, "ID", ModelPath.of(lom().getTaxon_Id()), lom().getId_Value(),
							"taxon");
					taxonDetail.langText(taxonParent, Messages.ClassificationPage_Entry, ModelPath.of(lom().getTaxon_Entry()), false, "taxon");
				});
	}

	/** Fills a taxon path from a concept chosen in an offline terminology. */
	private static void lookUpTaxonPath(FormSupport detail, TaxonPath path, Shell shell) {
		Terminology current = TerminologySupport.terminologyOf(path);
		TerminologyLookupDialog dialog = new TerminologyLookupDialog(shell, Messages.ClassificationPage_LookUpTaxonPath, Messages.ClassificationPage_Use,
				Terminologies.available(), current != null && current.isAvailable() ? current : Terminologies.ACGME, false);
		if (dialog.open() != Window.OK || dialog.getResult().isEmpty()) {
			return;
		}
		ModelCommands.execute(detail.context().domain(), TerminologySupport.fillTaxonPath(detail.context().domain(),
				path, dialog.getResultTerminology(), dialog.getResult().getFirst(), HlomDocuments.DEFAULT_LANGUAGE));
	}

	static String classificationLabel(EObject object) {
		Classification classification = (Classification) object;
		String purpose = "";
		if (!classification.getPurpose().isEmpty()) {
			Purpose vocab = classification.getPurpose().getFirst();
			if (!vocab.getValue().isEmpty()) {
				Enumerator value = vocab.getValue().getFirst().getValue();
				purpose = value == null ? "" : value.getLiteral();
			}
		}
		String source = classification.getTaxonPath().isEmpty() ? ""
				: firstText(classification.getTaxonPath().getFirst().getSource());
		if (purpose.isEmpty()) {
			return source;
		}
		return source.isEmpty() ? purpose : purpose + ": " + source;
	}

	static String taxonPathLabel(EObject object) {
		TaxonPath path = (TaxonPath) object;
		String source = firstText(path.getSource());
		String last = path.getTaxon().isEmpty() ? "" : taxonLabel(path.getTaxon().getLast());
		if (last.isEmpty()) {
			return source;
		}
		return source.isEmpty() ? last : source + " / " + last;
	}

	static String taxonLabel(EObject object) {
		Taxon taxon = (Taxon) object;
		String id = taxon.getId().isEmpty() || taxon.getId().getFirst().getValue() == null ? ""
				: taxon.getId().getFirst().getValue();
		String entry = firstText(taxon.getEntry());
		if (id.isEmpty()) {
			return entry;
		}
		return entry.isEmpty() ? id : id + " " + entry;
	}

	/** The first string of the first LanguageString in a list, or "". */
	private static String firstText(List<? extends LanguageString> strings) {
		if (strings.isEmpty() || strings.getFirst().getString().isEmpty()) {
			return "";
		}
		String value = strings.getFirst().getString().getFirst().getValue();
		return value == null ? "" : value;
	}
}
