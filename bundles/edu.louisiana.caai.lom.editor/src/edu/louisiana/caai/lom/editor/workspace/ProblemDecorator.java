/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.workspace;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Decorates documents, folders and projects with the highest severity of their problems (an error or warning
 * overlay, like the problem decorations of the IDE). A workbench decorator: it can be turned off in
 * <em>Preferences &gt; General &gt; Appearance &gt; Label Decorations</em> and combines with the decorations of
 * other plug-ins (Git).
 */
public final class ProblemDecorator extends LabelProvider implements ILightweightLabelDecorator, IResourceChangeListener {

	/** The id of the decorator. */
	public static final String ID = "edu.louisiana.caai.lom.editor.problemDecorator";

	public ProblemDecorator() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (!(element instanceof IResource resource) || !resource.isAccessible()) {
			return;
		}
		int severity;
		try {
			severity = resource.findMaxProblemSeverity(HermesWorkspace.PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		} catch (CoreException _) {
			return;
		}
		String image = switch (severity) {
			case IMarker.SEVERITY_ERROR -> ISharedImages.IMG_DEC_FIELD_ERROR;
			case IMarker.SEVERITY_WARNING -> ISharedImages.IMG_DEC_FIELD_WARNING;
			default -> null;
		};
		if (image != null) {
			decoration.addOverlay(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(image),
					IDecoration.BOTTOM_LEFT);
		}
	}

	/** Updates the decorations of the resources whose problems changed, and of their folders and projects. */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IMarkerDelta[] deltas = event.findMarkerDeltas(HermesWorkspace.PROBLEM_MARKER, true);
		if (deltas.length == 0) {
			return;
		}
		Set<IResource> changed = new LinkedHashSet<>();
		for (IMarkerDelta delta : deltas) {
			for (IResource resource = delta.getResource(); resource != null
					&& resource.getType() != IResource.ROOT; resource = resource.getParent()) {
				if (!changed.add(resource)) {
					break;
				}
			}
		}
		Display display = PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench().getDisplay() : null;
		if (display != null && !display.isDisposed()) {
			display.asyncExec(() -> fireLabelProviderChanged(new LabelProviderChangedEvent(this, changed.toArray())));
		}
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
}
