/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.fixes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * Finds and applies {@link HlomFix quick fixes} for validation problems. Plain Java, usable without OSGi.
 */
public final class HlomFixes {

	/** A fix chosen for a problem. */
	public record Planned(HlomProblem problem, HlomFix fix) {
	}

	private HlomFixes() {
	}

	/** The fixes applicable to a problem: changes first, then navigation; empty if there are none. */
	public static List<HlomFix> forProblem(HlomProblem problem) {
		List<HlomFix> result = new ArrayList<>();
		if (problem == null) {
			return result;
		}
		for (HlomFix fix : StandardFixes.candidates(problem)) {
			if (fix.appliesTo(problem)) {
				result.add(fix);
			}
		}
		result.sort(Comparator.comparing(HlomFix::kind));
		return result;
	}

	/** Whether the problem has a fix that changes the document. */
	public static boolean hasChangeFix(HlomProblem problem) {
		return forProblem(problem).stream().anyMatch(fix -> fix.kind() == HlomFix.Kind.CHANGE);
	}

	/** The fix "Fix All" would apply to a problem, or {@code null}. */
	public static HlomFix automaticFix(HlomProblem problem) {
		return forProblem(problem).stream().filter(fix -> fix.kind() == HlomFix.Kind.CHANGE && fix.isAutomatic())
				.findFirst().orElse(null);
	}

	/**
	 * The automatic fixes for a list of problems, at most one per problem; duplicates (the same fix for the same
	 * element) are dropped.
	 */
	public static List<Planned> automaticFixes(List<HlomProblem> problems) {
		List<Planned> plan = new ArrayList<>();
		Set<String> seen = new HashSet<>();
		for (HlomProblem problem : problems) {
			HlomFix fix = automaticFix(problem);
			if (fix != null && seen.add(fix.label() + '@' + System.identityHashCode(problem.object()))) {
				plan.add(new Planned(problem, fix));
			}
		}
		return plan;
	}

	/** Applies all automatic fixes and returns what was applied. */
	public static List<Planned> fixAll(DocumentRoot root, List<HlomProblem> problems) {
		List<Planned> plan = automaticFixes(problems);
		apply(root, plan);
		return plan;
	}

	/** Applies planned fixes in order. */
	public static void apply(DocumentRoot root, List<Planned> plan) {
		for (Planned planned : plan) {
			if (planned.fix().appliesTo(planned.problem())) {
				planned.fix().apply(root, planned.problem());
			}
		}
	}

	/**
	 * The language of the document: the first General.Language, else the first Meta-Metadata.Language, else
	 * {@value HlomDocuments#DEFAULT_LANGUAGE}.
	 */
	public static String documentLanguage(DocumentRoot root) {
		Lom lom = root == null ? null : root.getLom();
		if (lom != null) {
			if (!lom.getGeneral().isEmpty()) {
				String language = first(lom.getGeneral().getFirst().getLanguage());
				if (language != null) {
					return language;
				}
			}
			if (!lom.getMetaMetadata().isEmpty()) {
				String language = first(lom.getMetaMetadata().getFirst().getLanguage());
				if (language != null) {
					return language;
				}
			}
		}
		return HlomDocuments.DEFAULT_LANGUAGE;
	}

	private static String first(List<LanguageId> languages) {
		for (LanguageId language : languages) {
			if (language.getValue() != null && !language.getValue().isBlank()) {
				return language.getValue().trim();
			}
		}
		return null;
	}

	/** The document root containing an element, or {@code null}. */
	static DocumentRoot rootOf(EObject element) {
		return element != null && EcoreUtil.getRootContainer(element) instanceof DocumentRoot root ? root : null;
	}
}
