/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.terminology;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Id;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.Taxon;
import edu.louisiana.caai.lom.model.lom.TaxonPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Checks codes against the offline terminologies: coded keywords ({@code hx:source} naming MeSH or ICD-10-CM
 * with an {@code hx:id}) and the taxa of classification taxon paths whose source names an offline terminology.
 * Unknown codes are warnings. Only terminologies a document uses are loaded.
 */
public final class TerminologyValidator {

	private TerminologyValidator() {
	}

	/** Checks a document. */
	public static List<HlomProblem> check(DocumentRoot root) {
		List<HlomProblem> problems = new ArrayList<>();
		if (root == null) {
			return problems;
		}
		for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
			switch (it.next()) {
			case Keyword keyword -> checkKeyword(keyword, problems);
			case TaxonPath path -> checkTaxonPath(path, problems);
			default -> {
			}
			}
		}
		return problems;
	}

	private static void checkKeyword(Keyword keyword, List<HlomProblem> problems) {
		Terminology terminology = Terminologies.forSource(keyword.getSource());
		String id = keyword.getId() == null ? "" : keyword.getId().strip();
		// Online terminologies (licensed, such as SNOMED CT) can't be checked offline.
		if (terminology == null || terminology.isOnline() || id.isEmpty()) {
			return;
		}
		// The format of MeSH IDs is checked by MeshTermValidator.
		if (terminology == Terminologies.MESH && !edu.louisiana.caai.lom.model.support.mesh.MeshClient.isDescriptorId(id)) {
			return;
		}
		Concept concept = terminology.get(id);
		if (concept == null) {
			problems.add(new HlomProblem(Severity.WARNING, Origin.RULES, "The keyword " + text(keyword) + " has the "
					+ terminology.getId() + " code \"" + id + "\", which is not in " + terminology.getId() + " "
					+ terminology.getVersion() + ".", keyword, null, -1, -1));
		}
	}

	private static void checkTaxonPath(TaxonPath path, List<HlomProblem> problems) {
		Terminology terminology = null;
		for (LanguageString source : path.getSource()) {
			for (LangString string : source.getString()) {
				if (terminology == null) {
					terminology = Terminologies.forSource(string.getValue());
				}
			}
		}
		if (terminology == null || terminology.isOnline()) {
			return;
		}
		for (Taxon taxon : path.getTaxon()) {
			for (Id id : taxon.getId()) {
				String code = id.getValue() == null ? "" : id.getValue().strip();
				if (!code.isEmpty() && terminology.get(code) == null) {
					problems.add(new HlomProblem(Severity.WARNING, Origin.RULES, "The taxon ID \"" + code
							+ "\" is not a code of " + terminology.getTitle() + " (" + terminology.getVersion() + ").",
							taxon, null, -1, -1));
				}
			}
		}
	}

	private static String text(Keyword keyword) {
		for (LangString string : keyword.getString()) {
			if (string.getValue() != null && !string.getValue().isBlank()) {
				return "\"" + string.getValue().strip() + "\"";
			}
		}
		return "(without text)";
	}
}
