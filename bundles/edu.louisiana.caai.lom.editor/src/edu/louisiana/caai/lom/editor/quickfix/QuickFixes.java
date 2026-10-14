/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.quickfix;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.editor.pages.EditorServices;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.fixes.HlomFix;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;

/**
 * Applies {@link HlomFix quick fixes} in an editor: each fix (or "Fix All" batch) runs as one EMF
 * {@link ChangeCommand} on the editor's command stack, so it can be undone, and validation reruns afterwards.
 */
public final class QuickFixes {

	private QuickFixes() {
	}

	/**
	 * The fixes offered for a problem in menus and the quick fix popup: the model's fixes, followed by "Go to
	 * Element" unless a navigation fix already leads there.
	 */
	public static List<HlomFix> offered(HlomProblem problem) {
		List<HlomFix> fixes = new ArrayList<>(HlomFixes.forProblem(problem));
		if (fixes.stream().noneMatch(fix -> fix.kind() == HlomFix.Kind.NAVIGATE) && canReveal(problem)) {
			fixes.add(goToElement());
		}
		return fixes;
	}

	/** Whether a problem refers to an element or XML position that can be shown. */
	public static boolean canReveal(HlomProblem problem) {
		return problem != null && (problem.object() != null || problem.line() > 0);
	}

	/** A navigation fix showing the element (or XML line) of any problem. */
	public static HlomFix goToElement() {
		return new HlomFix() {
			@Override
			public String label() {
				return Messages.QuickFixes_GoToElement;
			}

			@Override
			public String description() {
				return Messages.QuickFixes_ShowsTheElementTheProblem;
			}

			@Override
			public boolean appliesTo(HlomProblem problem) {
				return canReveal(problem);
			}

			@Override
			public void apply(DocumentRoot root, HlomProblem problem) {
				// Navigation only.
			}

			@Override
			public Kind kind() {
				return Kind.NAVIGATE;
			}
		};
	}

	/** Whether quick fixes can modify the source's document. */
	public static boolean canModify(ValidationSource source) {
		return documentResource(source) != null;
	}

	/** Applies one fix: changes run as an undoable command, navigation fixes reveal their element. */
	public static void apply(ValidationSource source, HlomFix fix, HlomProblem problem) {
		if (source == null || fix == null || !fix.appliesTo(problem)) {
			return;
		}
		if (fix.kind() == HlomFix.Kind.NAVIGATE) {
			reveal(source, fix.target(problem), problem);
			return;
		}
		execute(source, NLS.bind(Messages.QuickFixes_QuickFix, fix.label()), List.of(new HlomFixes.Planned(problem, fix)));
	}

	/**
	 * Asks for confirmation (listing the fixes, which can be deselected) and applies all automatic fixes as one
	 * command.
	 */
	public static void fixAll(Shell shell, ValidationSource source) {
		if (source == null) {
			return;
		}
		List<HlomFixes.Planned> plan = HlomFixes.automaticFixes(source.getProblems());
		if (plan.isEmpty() || !canModify(source)) {
			MessageDialog.openInformation(shell, Messages.QuickFixes_FixAll,
					Messages.QuickFixes_NoneOfTheCurrentProblems);
			return;
		}
		FixAllDialog dialog = new FixAllDialog(shell, plan);
		if (dialog.open() != Window.OK || dialog.getSelected().isEmpty()) {
			return;
		}
		List<HlomFixes.Planned> selected = dialog.getSelected();
		execute(source, selected.size() == 1 ? "Quick Fix: " + selected.getFirst().fix().label()
				: "Fix " + selected.size() + " Problems", selected);
	}

	/** Shows the element a problem refers to, preferring the form page over the document tree. */
	public static void reveal(ValidationSource source, EObject target, HlomProblem problem) {
		activate(source);
		if (target != null && target.eResource() != null && source instanceof EditorServices services) {
			services.reveal(target);
		} else {
			source.reveal(problem);
		}
	}

	/** Applies planned fixes of the source's current problems as one undoable command. */
	public static void apply(ValidationSource source, String label, List<HlomFixes.Planned> plan) {
		if (source != null && !plan.isEmpty()) {
			execute(source, label, plan);
		}
	}

	private static void execute(ValidationSource source, String label, List<HlomFixes.Planned> plan) {
		Resource resource = documentResource(source);
		EditingDomain domain = source instanceof IEditingDomainProvider provider ? provider.getEditingDomain() : null;
		if (resource == null || domain == null) {
			return;
		}
		DocumentRoot root = HlomResources.getDocumentRoot(resource);
		Command command = new FixCommand(resource, label, root, plan);
		domain.getCommandStack().execute(command);
		activate(source);
	}

	private static void activate(ValidationSource source) {
		if (source instanceof IWorkbenchPart part && PlatformUI.isWorkbenchRunning()) {
			IWorkbenchPage page = part.getSite().getPage();
			if (page != null) {
				page.activate(part);
			}
		}
	}

	/** The resource holding the source's HLOM document, or {@code null}. */
	static Resource documentResource(ValidationSource source) {
		if (!(source instanceof IEditingDomainProvider provider) || provider.getEditingDomain() == null) {
			return null;
		}
		for (Resource resource : provider.getEditingDomain().getResourceSet().getResources()) {
			if (HlomResources.getDocumentRoot(resource) != null) {
				return resource;
			}
		}
		return null;
	}

	/** Records the model changes of the fixes so that they can be undone. */
	private static final class FixCommand extends ChangeCommand {
		private final DocumentRoot root;
		private final List<HlomFixes.Planned> plan;
		private final List<EObject> targets = new ArrayList<>();

		FixCommand(Resource resource, String label, DocumentRoot root, List<HlomFixes.Planned> plan) {
			super(resource);
			this.root = root;
			this.plan = plan;
			setLabel(label);
			setDescription(label);
		}

		@Override
		protected void doExecute() {
			HlomFixes.apply(root, plan);
			for (HlomFixes.Planned planned : plan) {
				EObject target = planned.fix().target(planned.problem());
				if (target != null && target.eResource() != null && !targets.contains(target)) {
					targets.add(target);
				}
			}
		}

		@Override
		public Collection<?> getAffectedObjects() {
			return targets;
		}
	}
}
