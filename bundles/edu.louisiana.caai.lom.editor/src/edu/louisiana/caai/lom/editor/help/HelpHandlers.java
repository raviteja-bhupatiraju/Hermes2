/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.handlers.HandlerUtil;

/** Default handlers of the Help menu commands (declared in plugin.xml). */
public final class HelpHandlers {

	private HelpHandlers() {
	}

	/** Opens the HLOM Guide at its overview (command {@code edu.louisiana.caai.lom.editor.help.guide}). */
	public static class ShowGuide extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			String topic = event.getParameter("edu.louisiana.caai.lom.editor.help.topic");
			if (topic == null) {
				HelpSupport.showGuide();
			} else {
				HelpSupport.showHelp(topic);
			}
			return null;
		}
	}

	/** Opens the HLOM Guide at the focused field's topic (command {@code ...help.contextHelp}). */
	public static class ContextHelp extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			HelpSupport.showContextHelp();
			return null;
		}
	}

	/** Opens the specification PDF (command {@code ...help.specification}). */
	public static class OpenSpecification extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			HelpSupport.openSpecification(HandlerUtil.getActiveShell(event));
			return null;
		}
	}

	/** Opens the MedBiquitous website (command {@code ...help.website}). */
	public static class OpenWebsite extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			HelpSupport.openWebsite();
			return null;
		}
	}

	/** Shows the About dialog (command {@code ...help.about}). */
	public static class About extends AbstractHandler {
		@Override
		public Object execute(ExecutionEvent event) {
			new AboutDialog(HandlerUtil.getActiveShell(event)).open();
			return null;
		}
	}
}
