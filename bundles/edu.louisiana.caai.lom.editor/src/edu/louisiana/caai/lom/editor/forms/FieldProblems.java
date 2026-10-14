/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * The bound fields of a form (shared by a {@link FormSupport} and all supports derived from it with
 * {@link FormSupport#withAnchor}), used to show validation problems next to the fields they concern.
 */
final class FieldProblems {

	/** How a field decides whether a problem concerns it. */
	enum Match {
		/** The problem's element is the field's owner element (and, if known, names the field's feature). */
		VALUE,
		/** The problem's element is the field's element or one of its descendants (vocabulary, language string). */
		ELEMENT,
		/** The problem's element is (inside) one of the elements listed by the field. */
		LIST
	}

	private record Field(Control control, IObservableValue<EObject> anchor, ModelPath path,
			EStructuralFeature feature, Match match) {
	}

	private final List<Field> fields = new ArrayList<>();
	private final Map<Control, ControlDecoration> decorations = new IdentityHashMap<>();
	private final Set<IObservableValue<EObject>> anchors = Collections.newSetFromMap(new IdentityHashMap<>());
	private List<HlomProblem> problems = List.of();
	private boolean applyPending;

	/**
	 * Registers a field.
	 *
	 * @param control the control to decorate (its parent needs a few pixels of space on the left)
	 * @param path    path from the anchor to the field's owner element
	 * @param feature the feature shown by the field (for {@link Match#LIST}: the listed reference or attribute)
	 */
	void register(Control control, IObservableValue<EObject> anchor, ModelPath path, EStructuralFeature feature,
			Match match) {
		Field field = new Field(control, anchor, path, feature, match);
		fields.add(field);
		control.addDisposeListener(_ -> {
			fields.remove(field);
			ControlDecoration decoration = decorations.remove(control);
			if (decoration != null) {
				decoration.dispose();
			}
		});
		if (anchors.add(anchor)) {
			// The detail fields of a master list show another element when the selection changes.
			anchor.addValueChangeListener(_ -> scheduleApply());
		}
	}

	/** Shows the problems; replaces the previously shown ones. */
	void show(List<HlomProblem> newProblems) {
		problems = newProblems == null ? List.of() : List.copyOf(newProblems);
		apply();
	}

	private void scheduleApply() {
		Display display = Display.getCurrent();
		if (applyPending || display == null) {
			return;
		}
		applyPending = true;
		display.asyncExec(() -> {
			applyPending = false;
			apply();
		});
	}

	private void apply() {
		Map<EObject, Integer> owners = new IdentityHashMap<>();
		List<EObject> resolved = new ArrayList<>(fields.size());
		for (Field field : fields) {
			EObject anchor = field.anchor().getValue();
			EObject owner = anchor == null ? null : field.path().resolve(anchor);
			resolved.add(owner);
			if (owner != null && field.match() == Match.VALUE) {
				owners.merge(owner, 1, Integer::sum);
			}
		}
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			if (field.control().isDisposed()) {
				continue;
			}
			EObject owner = resolved.get(i);
			List<HlomProblem> matching = new ArrayList<>();
			if (owner != null) {
				for (HlomProblem problem : problems) {
					if (problem.object() != null && problem.severity() != Severity.INFO
							&& matches(field, owner, owners.getOrDefault(owner, 0), problem)) {
						matching.add(problem);
					}
				}
			}
			decorate(field.control(), matching);
		}
	}

	private static boolean matches(Field field, EObject owner, int fieldsOfOwner, HlomProblem problem) {
		EObject object = problem.object();
		return switch (field.match()) {
		case ELEMENT -> object == owner || EcoreUtil.isAncestor(owner, object);
		case LIST -> field.feature() instanceof EReference reference
				&& owner.eClass().getEAllStructuralFeatures().contains(reference)
						? contains(owner.eGet(reference), object)
						: object == owner && namesFeature(problem.path(), field.feature(), fieldsOfOwner);
		case VALUE -> object == owner && namesFeature(problem.path(), field.feature(), fieldsOfOwner);
		};
	}

	/** Whether a reference value (a list or a single element) is or contains the object. */
	private static boolean contains(Object value, EObject object) {
		for (Object item : value instanceof List<?> list ? list : Collections.singletonList(value)) {
			if (item instanceof EObject element && (element == object || EcoreUtil.isAncestor(element, object))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Whether a problem of the owner element concerns the field's feature: if the problem's XML path is known,
	 * its last step must name the feature; otherwise only a field that is alone on its element is concerned.
	 */
	private static boolean namesFeature(String path, EStructuralFeature feature, int fieldsOfOwner) {
		if (path != null && feature != null) {
			String last = path.substring(path.lastIndexOf('/') + 1).replaceFirst("\\[.*$", "").replaceFirst("^@", "");
			int colon = last.indexOf(':');
			if (colon >= 0) {
				last = last.substring(colon + 1);
			}
			if (last.equals(ExtendedMetaData.INSTANCE.getName(feature)) || last.equals(feature.getName())) {
				return true;
			}
		}
		return fieldsOfOwner <= 1;
	}

	private void decorate(Control control, List<HlomProblem> matching) {
		ControlDecoration decoration = decorations.get(control);
		if (matching.isEmpty()) {
			if (decoration != null) {
				decoration.hide();
				decoration.setDescriptionText(null);
			}
			return;
		}
		boolean error = matching.stream().anyMatch(p -> p.severity() == Severity.ERROR);
		if (decoration == null) {
			decoration = new ControlDecoration(control, SWT.LEFT | SWT.TOP);
			decoration.setMarginWidth(1);
			decoration.setShowHover(true);
			decorations.put(control, decoration);
		}
		decoration.setImage(image(error));
		StringBuilder text = new StringBuilder();
		for (HlomProblem problem : matching) {
			if (!text.isEmpty()) {
				text.append('\n');
			}
			text.append(problem.message());
		}
		decoration.setDescriptionText(text.toString());
		decoration.show();
	}

	private static Image image(boolean error) {
		Image badge = FormWidgets.icon(error ? "error-badge" : "warning-badge");
		if (badge != null) {
			return badge;
		}
		return FieldDecorationRegistry.getDefault()
				.getFieldDecoration(error ? FieldDecorationRegistry.DEC_ERROR : FieldDecorationRegistry.DEC_WARNING)
				.getImage();
	}
}
