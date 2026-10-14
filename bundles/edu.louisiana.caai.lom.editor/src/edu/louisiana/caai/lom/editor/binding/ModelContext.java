/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.binding;

import java.util.List;

import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.IProperty;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.list.SimpleListProperty;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.core.databinding.observable.IDiff;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * The editing domain and change broadcaster of an editor, and the factory for the data binding properties
 * used by its form pages.
 * <p>
 * The properties read values by walking a {@link ModelPath} and write them by executing commands from
 * {@link ModelCommands}; change events come from the {@link ModelChangeBroadcaster}.
 */
public record ModelContext(EditingDomain domain, ModelChangeBroadcaster broadcaster) {

	/** A value at the end of a path. */
	public SimpleValueProperty<EObject, Object> value(ModelPath path, EStructuralFeature feature) {
		return new BroadcastValueProperty<>(Object.class) {
			@Override
			protected Object doGetValue(EObject source) {
				return ModelCommands.getValue(source, path, feature);
			}

			@Override
			protected void doSetValue(EObject source, Object value) {
				ModelCommands.execute(domain, ModelCommands.setValue(domain, source, path, feature, value));
			}
		};
	}

	/** The object at the end of a path (read-only). */
	public SimpleValueProperty<EObject, EObject> object(ModelPath path) {
		return new BroadcastValueProperty<>(EObject.class) {
			@Override
			protected EObject doGetValue(EObject source) {
				return path.resolve(source);
			}

			@Override
			protected void doSetValue(EObject source, EObject value) {
				throw new UnsupportedOperationException("read-only");
			}
		};
	}

	/** The term of a single-valued vocabulary element at the end of a path. */
	public SimpleValueProperty<EObject, Term> vocabulary(ModelPath vocabPath) {
		EReference last = vocabPath.steps().getLast().reference();
		return new BroadcastValueProperty<>(Term.class) {
			@Override
			protected Term doGetValue(EObject source) {
				EObject vocab = vocabPath.resolve(source);
				String value = vocab == null ? null : Vocabularies.getValue(vocab);
				if (value == null) {
					return null;
				}
				Term term = Vocabularies.findTerm(last.getEReferenceType(), value);
				return term != null ? term : new Term(value, Vocabularies.getSource(vocab), null);
			}

			@Override
			protected void doSetValue(EObject source, Term value) {
				ModelCommands.execute(domain, ModelCommands.setVocabulary(domain, source, vocabPath, value));
			}
		};
	}

	/** The elements of a multi-valued reference at the end of a path (read-only; edit through commands). */
	public SimpleListProperty<EObject, EObject> list(ModelPath path, EReference reference) {
		return new BroadcastListProperty<>(EObject.class) {
			@Override
			@SuppressWarnings("unchecked")
			protected List<EObject> doGetList(EObject source) {
				EObject owner = path.resolve(source);
				if (owner == null || !owner.eClass().getEAllStructuralFeatures().contains(reference)) {
					return List.of();
				}
				Object value = owner.eGet(reference);
				if (value instanceof List<?> list) {
					return List.copyOf((List<EObject>) list);
				}
				return value == null ? List.of() : List.of((EObject) value);
			}
		};
	}

	/** The simple values of a multi-valued attribute at the end of a path (read-only). */
	public SimpleListProperty<EObject, Object> values(ModelPath path, EStructuralFeature feature) {
		return new BroadcastListProperty<>(Object.class) {
			@Override
			protected List<Object> doGetList(EObject source) {
				EObject owner = path.resolve(source);
				if (owner == null) {
					return List.of();
				}
				return List.copyOf((List<?>) owner.eGet(feature));
			}
		};
	}

	/** Base class of value properties that are notified by the broadcaster. */
	private abstract class BroadcastValueProperty<T> extends SimpleValueProperty<EObject, T> {
		private final Class<T> type;

		BroadcastValueProperty(Class<T> type) {
			this.type = type;
		}

		@Override
		public Object getValueType() {
			return type;
		}

		@Override
		public INativePropertyListener<EObject> adaptListener(
				ISimplePropertyListener<EObject, ValueDiff<? extends T>> listener) {
			return new BroadcastListener<>(this, listener);
		}
	}

	/** Base class of read-only list properties that are notified by the broadcaster. */
	private abstract class BroadcastListProperty<E> extends SimpleListProperty<EObject, E> {
		private final Class<E> type;

		BroadcastListProperty(Class<E> type) {
			this.type = type;
		}

		@Override
		public Object getElementType() {
			return type;
		}

		@Override
		protected void doSetList(EObject source, List<E> list, ListDiff<E> diff) {
			throw new UnsupportedOperationException(Messages.ModelContext_ListsAreEditedThroughCommands);
		}

		@Override
		public INativePropertyListener<EObject> adaptListener(
				ISimplePropertyListener<EObject, ListDiff<E>> listener) {
			return new BroadcastListener<>(this, listener);
		}
	}

	/** Translates broadcaster notifications into property change events. */
	private final class BroadcastListener<D extends IDiff> extends NativePropertyListener<EObject, D> {
		private final java.util.Map<EObject, Runnable> registrations = new java.util.IdentityHashMap<>();

		BroadcastListener(IProperty property, ISimplePropertyListener<EObject, D> listener) {
			super(property, listener);
		}

		@Override
		protected void doAddTo(EObject source) {
			Runnable runnable = () -> fireChange(source, null);
			registrations.put(source, runnable);
			broadcaster.addListener(runnable);
		}

		@Override
		protected void doRemoveFrom(EObject source) {
			Runnable runnable = registrations.remove(source);
			if (runnable != null) {
				broadcaster.removeListener(runnable);
			}
		}
	}
}
