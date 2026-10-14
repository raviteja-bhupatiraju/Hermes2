/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.fixes;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * A quick fix for an {@link HlomProblem}. Fixes mutate the model directly; editors make them undoable by
 * recording the changes (for example with an EMF {@code ChangeCommand}). Fixes are idempotent: applying a fix
 * whose problem no longer exists changes nothing.
 * <p>
 * Some problems can't be repaired without information only the author has (a disclosure text, a MeSH ID);
 * for those a <em>navigation</em> fix leads to the field instead of inventing content.
 */
public interface HlomFix {

	/** The kind of a fix. */
	enum Kind {
		/** Changes the document. */
		CHANGE,
		/** Only shows the element the author has to complete; {@link HlomFix#apply} does nothing. */
		NAVIGATE
	}

	/** A short label for menus, e.g. "Set the language to en-US". */
	String label();

	/** A sentence explaining what the fix does. */
	String description();

	/** Whether this fix can repair the given problem. */
	boolean appliesTo(HlomProblem problem);

	/** Repairs the problem by modifying the model. */
	void apply(DocumentRoot root, HlomProblem problem);

	/** Whether this fix changes the document or only navigates to it. */
	default Kind kind() {
		return Kind.CHANGE;
	}

	/**
	 * Whether the fix is unambiguous and safe enough to be applied by "Fix All" without the author choosing
	 * between alternatives.
	 */
	default boolean isAutomatic() {
		return false;
	}

	/** The element to show after applying the fix (or to navigate to), or {@code null}. */
	default EObject target(HlomProblem problem) {
		return problem.object();
	}
}
