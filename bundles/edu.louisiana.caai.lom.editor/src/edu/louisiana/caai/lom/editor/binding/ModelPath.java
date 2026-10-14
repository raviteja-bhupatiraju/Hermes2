/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A navigation path through containment references, starting at an anchor object.
 * <p>
 * Most LOM elements may repeat (the schemas use unbounded choices), but in practice a document has one
 * {@code general}, one {@code title}, and so on. A path therefore always follows the <em>first</em> element
 * of a multi-valued reference. {@link #ensure} creates the missing part of the path, so that a form field
 * can be edited even before the elements it lives in exist.
 */
public final class ModelPath {

	/** One step: a containment reference, the class to instantiate when missing and an optional initializer. */
	public record Step(EReference reference, EClass type, Consumer<EObject> initializer) {
	}

	/** The result of {@link ModelPath#ensure}: the target object and the command adding it (if it was missing). */
	public record Ensured(EObject target, Command command) {
		/** Whether the target is new and only becomes part of the model when {@link #command()} executes. */
		public boolean isNew() {
			return command != null;
		}
	}

	private static final ModelPath EMPTY = new ModelPath(Collections.emptyList());

	private final List<Step> steps;

	private ModelPath(List<Step> steps) {
		this.steps = steps;
	}

	/** The path that resolves to the anchor itself. */
	public static ModelPath self() {
		return EMPTY;
	}

	/** A path following the given references. */
	public static ModelPath of(EReference... references) {
		ModelPath path = EMPTY;
		for (EReference reference : references) {
			path = path.then(reference);
		}
		return path;
	}

	/** Appends a step that instantiates the reference type when missing. */
	public ModelPath then(EReference reference) {
		return then(reference, reference.getEReferenceType(), null);
	}

	/** Appends a step with an initializer for newly created objects. */
	public ModelPath then(EReference reference, Consumer<EObject> initializer) {
		return then(reference, reference.getEReferenceType(), initializer);
	}

	/** Appends a step that instantiates a specific (sub)class when missing. */
	public ModelPath then(EReference reference, EClass type, Consumer<EObject> initializer) {
		List<Step> newSteps = new ArrayList<>(steps);
		newSteps.add(new Step(reference, type, initializer));
		return new ModelPath(Collections.unmodifiableList(newSteps));
	}

	/** Appends all steps of another path. */
	public ModelPath append(ModelPath other) {
		List<Step> newSteps = new ArrayList<>(steps);
		newSteps.addAll(other.steps);
		return new ModelPath(Collections.unmodifiableList(newSteps));
	}

	public List<Step> steps() {
		return steps;
	}

	public boolean isEmpty() {
		return steps.isEmpty();
	}

	/** Follows the path from the anchor; returns {@code null} if any element along the way is missing. */
	public EObject resolve(EObject anchor) {
		EObject current = anchor;
		for (Step step : steps) {
			if (current == null) {
				return null;
			}
			current = first(current, step.reference());
		}
		return current;
	}

	/** The objects along the path, starting with the anchor; stops at the first missing element. */
	public List<EObject> resolveChain(EObject anchor) {
		List<EObject> chain = new ArrayList<>();
		EObject current = anchor;
		chain.add(current);
		for (Step step : steps) {
			current = first(current, step.reference());
			if (current == null) {
				break;
			}
			chain.add(current);
		}
		return chain;
	}

	/**
	 * Resolves the path, creating missing elements. Newly created elements are assembled in memory and attached
	 * by a single command, so the change is one undoable step.
	 */
	public Ensured ensure(EditingDomain domain, EObject anchor) {
		EObject current = anchor;
		int index = 0;
		for (; index < steps.size(); index++) {
			EObject next = first(current, steps.get(index).reference());
			if (next == null) {
				break;
			}
			current = next;
		}
		if (index == steps.size()) {
			return new Ensured(current, null);
		}

		EObject attachTo = current;
		Step attachStep = steps.get(index);
		EObject top = create(attachStep);
		EObject leaf = top;
		for (int i = index + 1; i < steps.size(); i++) {
			Step step = steps.get(i);
			EObject child = create(step);
			add(leaf, step.reference(), child);
			leaf = child;
		}
		Command command = attachStep.reference().isMany()
				? new AddCommand(domain, attachTo, attachStep.reference(), top)
				: new SetCommand(domain, attachTo, attachStep.reference(), top);
		return new Ensured(leaf, command);
	}

	private static EObject create(Step step) {
		EObject object = EcoreUtil.create(step.type());
		if (step.initializer() != null) {
			step.initializer().accept(object);
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	static void add(EObject owner, EStructuralFeature feature, Object value) {
		if (feature.isMany()) {
			((List<Object>) owner.eGet(feature)).add(value);
		} else {
			owner.eSet(feature, value);
		}
	}

	/** The first (or only) value of a reference, or {@code null}. */
	public static EObject first(EObject owner, EReference reference) {
		if (owner == null || !owner.eClass().getEAllStructuralFeatures().contains(reference)) {
			return null;
		}
		Object value = owner.eGet(reference);
		if (value instanceof List<?> list) {
			return list.isEmpty() ? null : (EObject) list.getFirst();
		}
		return (EObject) value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Step step : steps) {
			builder.append('/').append(step.reference().getName());
		}
		return builder.isEmpty() ? "." : builder.toString();
	}
}
