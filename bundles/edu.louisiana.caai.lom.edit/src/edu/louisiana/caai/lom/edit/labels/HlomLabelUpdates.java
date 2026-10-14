/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.edit.labels;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IViewerNotification;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * Refreshes labels that depend on descendants: {@link HlomLabels} summarizes children (e.g. General shows its
 * title), so any change of an object also updates the label of the object itself and of all its containers.
 * Called by the item provider adapter factories for every notification they forward.
 */
public final class HlomLabelUpdates {

	private HlomLabelUpdates() {
	}

	/** Fires label updates for the notifier and its containers through their item providers. */
	public static void propagate(AdapterFactory rootAdapterFactory, Notification notification) {
		if (rootAdapterFactory == null || notification instanceof LabelUpdate || notification.isTouch()
				|| !(notification.getNotifier() instanceof EObject notifier)) {
			return;
		}
		EObject start = notifier;
		if (notification instanceof IViewerNotification viewerNotification) {
			if (viewerNotification.getElement() != notifier) {
				return; // re-fired by a wrapper for its own element
			}
			if (viewerNotification.isLabelUpdate()) {
				start = notifier.eContainer();
			}
		}
		for (EObject element = start; element != null; element = element.eContainer()) {
			Object provider = rootAdapterFactory.adapt(element, IItemLabelProvider.class);
			if (provider instanceof IChangeNotifier changeNotifier) {
				changeNotifier.fireNotifyChanged(new LabelUpdate(notification, element));
			}
		}
	}

	/** A label update fired by {@link #propagate}; not propagated again. */
	private static final class LabelUpdate extends ViewerNotification {

		LabelUpdate(Notification notification, Object element) {
			super(notification, element, false, true);
		}
	}
}
