/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * A copy of a document to validate in the background while the original stays editable, with the originals of the
 * copied objects, so that the problems found refer to the edited objects.
 *
 * @param resource the copy, in a resource set of its own
 * @param root     the copy of the document root, or {@code null}
 */
record ValidationSnapshot(Resource resource, EObject root, Map<EObject, EObject> originals) {

	/** Copies a document (in the UI thread), or returns {@code null} if its kind of resource can't be created. */
	static ValidationSnapshot of(Resource original, EObject root) {
		Resource.Factory factory = original.getResourceSet() == null ? null
				: original.getResourceSet().getResourceFactoryRegistry().getFactory(original.getURI());
		if (factory == null) {
			factory = Resource.Factory.Registry.INSTANCE.getFactory(original.getURI());
		}
		Resource copy = factory == null ? null : factory.createResource(original.getURI());
		if (copy == null) {
			return null;
		}
		EcoreUtil.Copier copier = new EcoreUtil.Copier();
		Collection<EObject> contents = copier.copyAll(original.getContents());
		copier.copyReferences();
		new ResourceSetImpl().getResources().add(copy);
		copy.getContents().addAll(contents);
		Map<EObject, EObject> originals = new IdentityHashMap<>();
		copier.forEach((object, copied) -> originals.put(copied, object));
		return new ValidationSnapshot(copy, root == null ? null : copier.get(root), originals);
	}

	/** A problem found in the copy, referring to the original object. */
	HlomProblem original(HlomProblem problem) {
		if (problem.object() == null) {
			return problem;
		}
		return new HlomProblem(problem.severity(), problem.origin(), problem.message(), originals.get(problem.object()),
				problem.path(), problem.line(), problem.column());
	}
}
