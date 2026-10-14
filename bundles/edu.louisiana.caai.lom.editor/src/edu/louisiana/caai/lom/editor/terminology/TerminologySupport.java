/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.terminology;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Shell;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.lom.EntryTaxon;
import edu.louisiana.caai.lom.model.lom.Id;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.Source;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.terminology.Concept;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;
import edu.louisiana.caai.lom.model.support.terminology.Terminology;

/**
 * Uses the offline terminologies in documents: coded keywords and classification taxon paths.
 */
public final class TerminologySupport {

	private static final LomPackage LOM = LomPackage.eINSTANCE;

	private TerminologySupport() {
	}

	/**
	 * The value written as keyword {@code hx:source} or taxon path source: the short id for MeSH, ICD-10-CM, SNOMED
	 * CT and LOINC, else the name (e.g. "ACGME Core Competencies").
	 */
	public static String sourceName(Terminology terminology) {
		return terminology == Terminologies.MESH || terminology == Terminologies.ICD_10_CM
				|| terminology == Terminologies.SNOMED_CT || terminology == Terminologies.LOINC ? terminology.getId()
						: terminology.getTitle();
	}

	/** Asks for keywords from the keyword terminologies (MeSH, ICD-10-CM); empty if canceled. */
	public static TerminologyLookupDialog lookupKeywords(Shell shell) {
		TerminologyLookupDialog dialog = new TerminologyLookupDialog(shell, Messages.TerminologySupport_AddCodedKeywords, Messages.TerminologySupport_Add,
				Terminologies.keywordTerminologies(), Terminologies.MESH, true);
		dialog.open();
		return dialog;
	}

	/**
	 * Creates a command adding keywords (text, {@code hx:source}, {@code hx:id}) for concepts to a keyword list.
	 * Concepts already present as keyword (same source and id) are skipped.
	 *
	 * @return the command, or {@code null} if there is nothing to add
	 */
	public static Command addKeywords(EditingDomain domain, EObject anchor, ModelPath ownerPath, EReference reference,
			Terminology terminology, List<Concept> concepts, String language) {
		Set<String> existing = new HashSet<>();
		EObject owner = ownerPath.resolve(anchor);
		if (owner != null && owner.eClass().getEAllStructuralFeatures().contains(reference)
				&& owner.eGet(reference) instanceof List<?> list) {
			for (Object item : list) {
				if (item instanceof Keyword keyword && terminology.isNamedBy(keyword.getSource()) && keyword.getId() != null) {
					existing.add(keyword.getId().strip());
				}
			}
		}
		CompoundCommand compound = new CompoundCommand(concepts.size() == 1 ? "Add Keyword" : "Add Keywords");
		String source = sourceName(terminology);
		for (Concept concept : concepts) {
			if (!existing.add(concept.code())) {
				continue;
			}
			Keyword keyword = LomFactory.eINSTANCE.createKeyword();
			keyword.setSource(source);
			keyword.setId(concept.code());
			keyword.getString().add(HlomDocuments.langString(concept.label(),
					language == null || language.isBlank() ? HlomDocuments.DEFAULT_LANGUAGE : language));
			compound.append(ModelCommands.addElement(domain, anchor, ownerPath, reference, keyword));
		}
		return compound.isEmpty() ? null : compound;
	}

	/**
	 * Creates a command making a taxon path describe a concept: the source names the terminology and the taxa are
	 * the concept's path in the hierarchy, from the top level down to the concept (id = code, entry = name).
	 */
	public static Command fillTaxonPath(EditingDomain domain, TaxonPath path, Terminology terminology, Concept concept,
			String language) {
		String lang = language == null || language.isBlank() ? HlomDocuments.DEFAULT_LANGUAGE : language;
		CompoundCommand compound = new CompoundCommand(Messages.TerminologySupport_SetTaxonPath);
		if (!path.getSource().isEmpty()) {
			compound.append(RemoveCommand.create(domain, path, LOM.getTaxonPath_Source(), new ArrayList<>(path.getSource())));
		}
		if (!path.getTaxon().isEmpty()) {
			compound.append(RemoveCommand.create(domain, path, LOM.getTaxonPath_Taxon(), new ArrayList<>(path.getTaxon())));
		}
		Source source = LomFactory.eINSTANCE.createSource();
		source.getString().add(HlomDocuments.langString(sourceName(terminology), lang));
		compound.append(AddCommand.create(domain, path, LOM.getTaxonPath_Source(), source));
		List<Taxon> taxa = new ArrayList<>();
		for (Concept step : terminology.path(concept)) {
			Taxon taxon = LomFactory.eINSTANCE.createTaxon();
			Id id = LomFactory.eINSTANCE.createId();
			id.setValue(step.code());
			taxon.getId().add(id);
			EntryTaxon entry = LomFactory.eINSTANCE.createEntryTaxon();
			entry.getString().add(HlomDocuments.langString(step.label(), lang));
			taxon.getEntry().add(entry);
			taxa.add(taxon);
		}
		compound.append(AddCommand.create(domain, path, LOM.getTaxonPath_Taxon(), taxa));
		return compound;
	}

	/** The terminology named by a taxon path's source, or {@code null}. */
	public static Terminology terminologyOf(TaxonPath path) {
		for (LanguageString source : path.getSource()) {
			for (LangString string : source.getString()) {
				Terminology terminology = Terminologies.forSource(string.getValue());
				if (terminology != null) {
					return terminology;
				}
			}
		}
		return null;
	}
}
