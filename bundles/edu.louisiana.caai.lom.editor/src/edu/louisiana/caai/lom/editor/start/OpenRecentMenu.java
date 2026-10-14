/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 * The dynamic File &gt; Open Recent menu: one entry per remembered document (with mnemonic and folder), and
 * "Clear Recent Documents".
 */
public class OpenRecentMenu extends CompoundContributionItem implements IWorkbenchContribution {

	private IServiceLocator serviceLocator;

	/** Creates the menu contribution (instantiated by the workbench). */
	public OpenRecentMenu() {
	}

	/** Creates the menu contribution with an id. */
	public OpenRecentMenu(String id) {
		super(id);
	}

	@Override
	public void initialize(IServiceLocator locator) {
		this.serviceLocator = locator;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		List<IContributionItem> items = new ArrayList<>();
		List<String> paths = RecentDocuments.get();
		if (paths.isEmpty()) {
			Action empty = new Action(Messages.OpenRecentMenu_NoRecentDocuments) {
				// Placeholder entry.
			};
			empty.setEnabled(false);
			items.add(new ActionContributionItem(empty));
			return items.toArray(new IContributionItem[0]);
		}
		int index = 1;
		for (String path : paths) {
			File file = new File(path);
			String mnemonic = index < 10 ? "&" + index + "  " : index == 10 ? "1&0 " : "";
			String folder = file.getParent() == null ? "" : "  —  " + abbreviate(file.getParent());
			Action action = new Action(escape(mnemonic + file.getName() + folder)) {
				@Override
				public void run() {
					StartActions.openRecent(window(), path);
				}
			};
			action.setToolTipText(path);
			items.add(new ActionContributionItem(action));
			index++;
		}
		items.add(new Separator());
		items.add(new ActionContributionItem(new Action(Messages.OpenRecentMenu_ClearRecentDocuments) {
			@Override
			public void run() {
				RecentDocuments.clear();
			}
		}));
		return items.toArray(new IContributionItem[0]);
	}

	private IWorkbenchWindow window() {
		IWorkbenchWindow window = serviceLocator != null ? serviceLocator.getService(IWorkbenchWindow.class) : null;
		return window != null ? window : PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	/** Shortens long folder paths in the middle. */
	static String abbreviate(String path) {
		return abbreviate(path, 48);
	}

	/** Shortens a path in the middle to at most {@code max} characters, keeping the drive and the last folders. */
	static String abbreviate(String path, int max) {
		if (path.length() <= max) {
			return path;
		}
		int head = Math.min(path.indexOf(java.io.File.separatorChar) + 1, max / 3);
		head = head <= 0 ? max / 3 : head;
		return path.substring(0, head) + "…" + path.substring(path.length() - (max - head - 1));
	}

	private static String escape(String text) {
		// Keep the leading mnemonic, escape ampersands in file names.
		int start = text.startsWith("&") || text.startsWith("1&") ? text.indexOf(' ') : 0;
		return text.substring(0, start) + text.substring(start).replace("&", "&&");
	}
}
