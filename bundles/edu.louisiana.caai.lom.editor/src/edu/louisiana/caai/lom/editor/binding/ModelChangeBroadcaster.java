/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.binding;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.swt.widgets.Display;

/**
 * Notifies listeners after any change to a document.
 * <p>
 * Form fields observe values deep inside the model, often through elements that don't exist yet. Instead of
 * attaching a listener to every element on every path, the editor attaches one content adapter to the
 * resource and the bound observables simply re-read their values when it fires. Notifications are coalesced
 * and delivered once per event loop turn, after the command that caused them has completed.
 */
public final class ModelChangeBroadcaster {

	private final List<Runnable> listeners = new CopyOnWriteArrayList<>();
	private final Display display;
	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			// Changes of the resource's own state (errors, warnings, modified flag, time stamp) don't change the document.
			boolean resourceState = notification.getNotifier() instanceof Resource
					&& notification.getFeatureID(Resource.class) != Resource.RESOURCE__CONTENTS;
			if (!notification.isTouch() && !resourceState) {
				schedule();
			}
		}
	};
	private boolean scheduled;
	private Resource resource;

	public ModelChangeBroadcaster(Display display) {
		this.display = display;
	}

	/** Starts observing a resource (and stops observing the previous one). */
	public void observe(Resource newResource) {
		if (resource != null) {
			resource.eAdapters().remove(adapter);
		}
		resource = newResource;
		if (resource != null) {
			resource.eAdapters().add(adapter);
		}
	}

	public void addListener(Runnable listener) {
		listeners.add(listener);
	}

	public void removeListener(Runnable listener) {
		listeners.remove(listener);
	}

	/** Notifies the listeners now (synchronously). */
	public void fire() {
		scheduled = false;
		for (Runnable listener : listeners) {
			listener.run();
		}
	}

	private void schedule() {
		if (!scheduled && !display.isDisposed()) {
			scheduled = true;
			display.asyncExec(() -> {
				if (scheduled) {
					fire();
				}
			});
		}
	}

	public void dispose() {
		observe(null);
		listeners.clear();
	}
}
