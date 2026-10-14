/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Severity icons of validation problems.
 */
public final class ProblemImages {

	private ProblemImages() {
	}

	/** The workbench's error, warning or info icon. */
	public static Image severity(Severity severity) {
		String key = switch (severity) {
			case ERROR -> ISharedImages.IMG_OBJS_ERROR_TSK;
			case WARNING -> ISharedImages.IMG_OBJS_WARN_TSK;
			default -> ISharedImages.IMG_OBJS_INFO_TSK;
		};
		return PlatformUI.getWorkbench().getSharedImages().getImage(key);
	}
}
