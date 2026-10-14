/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.binding;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import edu.louisiana.caai.lom.model.healthcare.HealthcareMetadataType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * Creates the EMF commands behind every edit made in the form pages. All changes go through the editing
 * domain's command stack, so they are undoable and keep the editor's dirty state, outline and property sheet
 * in sync.
 */
public final class ModelCommands {

	private ModelCommands() {
	}

	/** Executes a command on the domain's command stack (if it can execute). */
	public static boolean execute(EditingDomain domain, Command command) {
		if (command != null && command.canExecute()) {
			domain.getCommandStack().execute(command);
			return true;
		}
		return false;
	}

	/** Reads the value of a feature at the end of a path; multi-valued features yield their first value. */
	public static Object getValue(EObject anchor, ModelPath path, EStructuralFeature feature) {
		EObject owner = path.resolve(anchor);
		if (owner == null || !owner.eClass().getEAllStructuralFeatures().contains(feature)) {
			return null;
		}
		if (feature.isUnsettable() && !owner.eIsSet(feature)) {
			return null;
		}
		Object value = owner.eGet(feature);
		if (feature.isMany()) {
			List<?> list = (List<?>) value;
			return list.isEmpty() ? null : list.getFirst();
		}
		return value;
	}

	/**
	 * Creates the command that sets a value at the end of a path, creating missing elements on the way. Setting
	 * {@code null} or an empty string clears the value and removes elements that become empty.
	 */
	public static Command setValue(EditingDomain domain, EObject anchor, ModelPath path, EStructuralFeature feature,
			Object value) {
		if (Objects.equals(getValue(anchor, path, feature), value)) {
			return null;
		}
		if (isEmptyValue(value)) {
			return clearValue(domain, anchor, path, feature);
		}
		ModelPath.Ensured ensured = path.ensure(domain, anchor);
		EObject owner = ensured.target();
		if (ensured.isNew()) {
			// The owner is detached until the command runs, so it can be modified directly.
			ModelPath.add(owner, feature, value);
			return labeled(ensured.command(), feature);
		}
		Command set;
		if (feature.isMany()) {
			List<?> list = (List<?>) owner.eGet(feature);
			set = list.isEmpty() ? new AddCommand(domain, owner, feature, value)
					: new SetCommand(domain, owner, feature, value, 0);
		} else {
			set = new SetCommand(domain, owner, feature, value);
		}
		return labeled(set, feature);
	}

	private static Command clearValue(EditingDomain domain, EObject anchor, ModelPath path,
			EStructuralFeature feature) {
		List<EObject> chain = path.resolveChain(anchor);
		if (chain.size() != path.steps().size() + 1) {
			return null; // Nothing there to clear.
		}
		EObject owner = chain.getLast();
		EObject removable = topmostRemovable(chain, owner, feature);
		if (removable != null) {
			return labeled(remove(domain, removable), feature);
		}
		Command clear;
		if (feature.isMany()) {
			List<?> list = (List<?>) owner.eGet(feature);
			clear = list.isEmpty() ? null : new RemoveCommand(domain, owner, feature, list.getFirst());
		} else if (feature.isUnsettable() || feature instanceof EReference) {
			clear = new SetCommand(domain, owner, feature, SetCommand.UNSET_VALUE);
		} else {
			clear = new SetCommand(domain, owner, feature, null);
		}
		return labeled(clear, feature);
	}

	/**
	 * Finds the highest element on the path that would be empty once the given feature is cleared, so that
	 * clearing a field doesn't leave empty XML elements behind.
	 */
	private static EObject topmostRemovable(List<EObject> chain, EObject owner, EStructuralFeature cleared) {
		if (chain.size() < 2 || !isEmptyExcept(owner, cleared, null)) {
			return null;
		}
		EObject removable = owner;
		for (int i = chain.size() - 2; i >= 1; i--) {
			EObject parent = chain.get(i);
			if (!isPrunable(parent) || !isEmptyExcept(parent, null, removable)) {
				break;
			}
			removable = parent;
		}
		return isPrunable(removable) ? removable : null;
	}

	/** Elements that must stay even when empty. */
	private static boolean isPrunable(EObject object) {
		return !(object instanceof DocumentRoot || object instanceof Lom || object instanceof HealthcareMetadataType);
	}

	/**
	 * Whether an object has no content apart from the given feature (which is about to be cleared) and the given
	 * child (which is about to be removed).
	 */
	static boolean isEmptyExcept(EObject object, EStructuralFeature ignoredFeature, EObject ignoredChild) {
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (feature == ignoredFeature || feature.isDerived() || feature.isTransient()
					|| "uniqueElementName".equals(feature.getName())
					// A language tag without text is not content worth keeping.
					|| feature == LomPackage.eINSTANCE.getLangString_Language()) {
				continue;
			}
			if (FeatureMapUtil.isFeatureMap(feature)) {
				for (FeatureMap.Entry entry : (FeatureMap) object.eGet(feature)) {
					if (entry.getEStructuralFeature() != ignoredFeature && entry.getValue() != ignoredChild) {
						return false;
					}
				}
			} else if (feature.isMany()) {
				for (Object value : (List<?>) object.eGet(feature)) {
					if (value != ignoredChild) {
						return false;
					}
				}
			} else if (object.eIsSet(feature) && object.eGet(feature) != ignoredChild) {
				return false;
			}
		}
		return true;
	}

	/** Creates a command removing an object from its container. */
	public static Command remove(EditingDomain domain, EObject object) {
		EObject container = object.eContainer();
		EStructuralFeature feature = object.eContainmentFeature();
		if (container == null || feature == null) {
			return null;
		}
		if (feature.isMany()) {
			return new RemoveCommand(domain, container, feature, object);
		}
		return new SetCommand(domain, container, feature, SetCommand.UNSET_VALUE);
	}

	/** Creates a command removing several objects (each from its own container). */
	public static Command removeAll(EditingDomain domain, Collection<? extends EObject> objects, String label) {
		CompoundCommand compound = new CompoundCommand(label);
		for (EObject object : objects) {
			Command remove = remove(domain, object);
			if (remove != null) {
				compound.append(remove);
			}
		}
		return compound.isEmpty() ? null : compound;
	}

	/**
	 * Creates a command adding a new element to a (multi-valued) reference at the end of a path, creating the
	 * path if needed.
	 */
	public static Command addElement(EditingDomain domain, EObject anchor, ModelPath path, EReference reference,
			EObject element) {
		ModelPath.Ensured ensured = path.ensure(domain, anchor);
		if (ensured.isNew()) {
			ModelPath.add(ensured.target(), reference, element);
			return labeled(ensured.command(), reference);
		}
		return labeled(reference.isMany() ? new AddCommand(domain, ensured.target(), reference, element)
				: new SetCommand(domain, ensured.target(), reference, element), reference);
	}

	/** Creates a command moving an element within its containing list. */
	public static Command move(EditingDomain domain, EObject element, int delta) {
		EObject container = element.eContainer();
		EStructuralFeature feature = element.eContainmentFeature();
		if (container == null || feature == null || !feature.isMany()) {
			return null;
		}
		List<?> list = (List<?>) container.eGet(feature);
		int index = list.indexOf(element) + delta;
		if (index < 0 || index >= list.size()) {
			return null;
		}
		return new MoveCommand(domain, container, feature, element, index);
	}

	// ----------------------------------------------------------------------------------------------------
	// Vocabularies
	// ----------------------------------------------------------------------------------------------------

	/** Creates a detached vocabulary element (e.g. a {@code Role}) holding a term and its source. */
	public static EObject createVocabulary(EClass vocabClass, Term term) {
		EObject vocab = EcoreUtil.create(vocabClass);
		fillVocabulary(vocab, term);
		return vocab;
	}

	private static void fillVocabulary(EObject vocab, Term term) {
		EReference sourceReference = Vocabularies.sourceReference(vocab.eClass());
		EReference valueReference = Vocabularies.valueReference(vocab.eClass());
		ModelPath.add(vocab, sourceReference, simpleContent(sourceReference.getEReferenceType(),
				EcoreUtil.createFromString(Vocabularies.sourceDataType(), term.source())));
		ModelPath.add(vocab, valueReference, simpleContent(valueReference.getEReferenceType(), term.literal()));
	}

	private static EObject simpleContent(EClass eClass, Object value) {
		EObject object = EcoreUtil.create(eClass);
		object.eSet(Vocabularies.contentAttribute(eClass), value);
		return object;
	}

	/**
	 * Creates the command that sets a single-valued vocabulary element at the end of a path to a term (with the
	 * matching source), or removes it when the term is {@code null}.
	 */
	public static Command setVocabulary(EditingDomain domain, EObject anchor, ModelPath vocabPath, Term term) {
		EObject existing = vocabPath.resolve(anchor);
		if (term == null) {
			if (existing == null) {
				return null;
			}
			List<EObject> chain = vocabPath.resolveChain(anchor);
			EObject removable = existing;
			for (int i = chain.size() - 2; i >= 1; i--) {
				EObject parent = chain.get(i);
				if (!isPrunable(parent) || !isEmptyExcept(parent, null, removable)) {
					break;
				}
				removable = parent;
			}
			return remove(domain, removable);
		}
		if (existing != null && Objects.equals(Vocabularies.getValue(existing), term.value())
				&& Objects.equals(Vocabularies.getSource(existing), term.source())) {
			return null;
		}
		ModelPath.Ensured ensured = vocabPath.ensure(domain, anchor);
		EObject vocab = ensured.target();
		if (ensured.isNew()) {
			fillVocabulary(vocab, term);
			return ensured.command();
		}
		// Replace the existing source and value elements.
		CompoundCommand compound = new CompoundCommand(NLS.bind(Messages.ModelCommands_Set, vocab.eClass().getName()));
		EReference sourceReference = Vocabularies.sourceReference(vocab.eClass());
		EReference valueReference = Vocabularies.valueReference(vocab.eClass());
		List<?> sources = new ArrayList<>((List<?>) vocab.eGet(sourceReference));
		List<?> values = new ArrayList<>((List<?>) vocab.eGet(valueReference));
		if (!sources.isEmpty()) {
			compound.append(new RemoveCommand(domain, vocab, sourceReference, sources));
		}
		if (!values.isEmpty()) {
			compound.append(new RemoveCommand(domain, vocab, valueReference, values));
		}
		compound.append(new AddCommand(domain, vocab, sourceReference, simpleContent(
				sourceReference.getEReferenceType(),
				EcoreUtil.createFromString(Vocabularies.sourceDataType(), term.source()))));
		compound.append(new AddCommand(domain, vocab, valueReference,
				simpleContent(valueReference.getEReferenceType(), term.literal())));
		return compound;
	}

	/** The term values of all vocabulary elements in a multi-valued reference. */
	public static List<String> vocabularyValues(EObject owner, EReference vocabReference) {
		List<String> result = new ArrayList<>();
		if (owner != null) {
			for (Object vocab : (List<?>) owner.eGet(vocabReference)) {
				String value = Vocabularies.getValue((EObject) vocab);
				if (value != null) {
					result.add(value);
				}
			}
		}
		return result;
	}

	/**
	 * Creates the command adding or removing a term in a multi-valued vocabulary reference (e.g. the learning
	 * resource types of an educational element).
	 */
	public static Command toggleVocabulary(EditingDomain domain, EObject anchor, ModelPath ownerPath,
			EReference vocabReference, Term term, boolean present) {
		EObject owner = ownerPath.resolve(anchor);
		if (present) {
			if (vocabularyValues(owner, vocabReference).contains(term.value())) {
				return null;
			}
			EObject vocab = createVocabulary(vocabReference.getEReferenceType(), term);
			return addElement(domain, anchor, ownerPath, vocabReference, vocab);
		}
		if (owner == null) {
			return null;
		}
		List<EObject> matching = new ArrayList<>();
		for (Object vocab : (List<?>) owner.eGet(vocabReference)) {
			if (term.value().equals(Vocabularies.getValue((EObject) vocab))) {
				matching.add((EObject) vocab);
			}
		}
		return removeAll(domain, matching, NLS.bind(Messages.ModelCommands_Remove, term.value()));
	}

	// ----------------------------------------------------------------------------------------------------

	static boolean isEmptyValue(Object value) {
		return value == null || (value instanceof String string && string.isEmpty());
	}

	/** Converts a string to the value type of an attribute; throws {@link IllegalArgumentException} if invalid. */
	public static Object fromString(EAttribute attribute, String text) {
		return EcoreUtil.createFromString(attribute.getEAttributeType(), text);
	}

	/** Converts an attribute value to its XML string form. */
	public static String toString(EAttribute attribute, Object value) {
		return value == null ? "" : EcoreUtil.convertToString(attribute.getEAttributeType(), value);
	}

	private static Command labeled(Command command, EStructuralFeature feature) {
		if (command == null) {
			return null;
		}
		CompoundCommand compound = new CompoundCommand(NLS.bind(Messages.ModelCommands_Edit, feature.getName()));
		compound.append(command);
		return compound;
	}
}
