/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Offline checks of MeSH keywords: HLOM recommends {@code hx:source="MeSH"} together with the descriptor's
 * unique ID in {@code hx:id} (e.g. {@code D009203}).
 */
public final class MeshTermValidator {

	private MeshTermValidator() {
	}

	/** Whether a keyword source names MeSH (case-insensitive). */
	public static boolean isMeshSource(String source) {
		return source != null && MeshClient.SOURCE.equalsIgnoreCase(source.strip());
	}

	/**
	 * Warns about keywords (in general and in classifications) with source MeSH whose {@code hx:id} is missing
	 * or isn't a descriptor ID ({@code D} followed by 6 or 9 digits).
	 */
	public static List<HlomProblem> checkKeywordIds(DocumentRoot root) {
		List<HlomProblem> problems = new ArrayList<>();
		if (root == null) {
			return problems;
		}
		for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
			if (!(it.next() instanceof Keyword keyword) || !isMeshSource(keyword.getSource())) {
				continue;
			}
			String id = keyword.getId() == null ? "" : keyword.getId().strip();
			String text = text(keyword);
			if (id.isEmpty()) {
				problems.add(warning(keyword, "The MeSH keyword " + text
						+ " has no descriptor ID; add the MeSH unique ID (e.g. D009203) in hx:id."));
			} else if (!MeshClient.isDescriptorId(id)) {
				problems.add(warning(keyword, "The MeSH keyword " + text + " has the ID \"" + id
						+ "\", which is not a MeSH descriptor ID (D followed by 6 or 9 digits, e.g. D009203)."));
			}
		}
		return problems;
	}

	private static String text(Keyword keyword) {
		for (LangString string : keyword.getString()) {
			if (string.getValue() != null && !string.getValue().isBlank()) {
				return "\"" + string.getValue().strip() + "\"";
			}
		}
		return "(without text)";
	}

	private static HlomProblem warning(Keyword keyword, String message) {
		return new HlomProblem(Severity.WARNING, Origin.RULES, message, keyword, null, -1, -1);
	}
}
