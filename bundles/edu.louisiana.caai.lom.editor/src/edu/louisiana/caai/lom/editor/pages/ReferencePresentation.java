/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.References.Target;

/**
 * Shows references to elements of the document by the referenced elements (their title and key), offers the
 * elements a reference can name, and shows the referenced element.
 */
final class ReferencePresentation implements ValueList.Presentation {

	private final References.Kind kind;
	private final EditingDomain domain;
	private final Consumer<EObject> reveal;

	ReferencePresentation(References.Kind kind, EditingDomain domain, Consumer<EObject> reveal) {
		this.kind = kind;
		this.domain = domain;
		this.reveal = reveal;
	}

	/** The root element of the edited document, or {@code null}. */
	private EObject root() {
		for (Resource resource : domain.getResourceSet().getResources()) {
			if (!resource.getContents().isEmpty()) {
				EObject root = Paths.rootElement(resource.getContents().getFirst());
				if (root != null) {
					return root;
				}
			}
		}
		return null;
	}

	private Target resolve(String value) {
		EObject root = root();
		return root == null ? null : References.resolve(root, kind, value);
	}

	@Override
	public String display(String value) {
		Target target = resolve(value);
		return target != null ? target.label() : value;
	}

	@Override
	public List<Choice> choices() {
		EObject root = root();
		if (root == null) {
			return List.of();
		}
		return References.targets(root, kind).stream()
				.map(target -> new Choice(target.label(), kind.format(target.key()))).toList();
	}

	@Override
	public void open(String value) {
		Target target = resolve(value);
		if (target != null) {
			reveal.accept(target.element());
		} else {
			Display.getCurrent().beep();
		}
	}

	@Override
	public String openLabel() {
		return NLS.bind(Messages.ReferencePresentation_GoTo, kind.label());
	}
}
