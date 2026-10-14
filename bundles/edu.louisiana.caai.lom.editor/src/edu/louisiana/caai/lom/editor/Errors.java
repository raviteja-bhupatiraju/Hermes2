/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import org.eclipse.osgi.util.NLS;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Reports failures as the workbench does: the problem is logged (Error Log) and shown in an error dialog whose
 * Details list the cause, so that the user can tell what went wrong and report it.
 */
public final class Errors {

	/** The id of the editor bundle, for the statuses. */
	public static final String PLUGIN_ID = "edu.louisiana.caai.lom.editor";

	private Errors() {
	}

	/**
	 * Logs a failure and shows it.
	 *
	 * @param title     the title of the dialog (the operation that failed)
	 * @param message   what failed, for example "The document could not be created."
	 * @param exception the cause
	 */
	public static void show(Shell shell, String title, String message, Throwable exception) {
		IStatus status = status(message, exception);
		StatusManager.getManager().handle(status, StatusManager.LOG);
		ErrorDialog.openError(shell, title, message, status);
	}

	/**
	 * The status of a failure: the status of a {@link CoreException}, else an error with the exception's message
	 * as the reason and its causes and stack trace as details.
	 */
	public static IStatus status(String message, Throwable exception) {
		Throwable cause = exception instanceof InvocationTargetException target && target.getCause() != null
				? target.getCause() : exception;
		if (cause instanceof CoreException core) {
			return core.getStatus();
		}
		MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.ERROR, reason(cause, message), cause);
		for (Throwable t = cause; t != null; t = t.getCause() == t ? null : t.getCause()) {
			status.add(Status.error(t.getClass().getName() + (t.getMessage() == null ? "" : ": " + t.getMessage())));
			for (StackTraceElement element : t.getStackTrace()) {
				status.add(Status.error(NLS.bind(Messages.Errors_At, element)));
			}
		}
		return status;
	}

	private static String reason(Throwable exception, String fallback) {
		String reason = exception == null ? null : exception.getLocalizedMessage();
		return reason == null || reason.isBlank() ? exception == null ? fallback : exception.getClass().getSimpleName()
				: reason;
	}
}
