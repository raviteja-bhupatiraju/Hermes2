/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelContext;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * A form page of the HLOM editor, showing one LOM category (or healthcare extension) as data-bound fields.
 * <p>
 * Subclasses implement {@link #createContent}; all fields are bound relative to the document's {@code lom}
 * element (the page anchor), using paths such as {@code ModelPath.of(lom().getLom_General())}.
 */
public abstract class AbstractLomPage {

	/** Navigation groups of the pages. */
	public enum Category {
		OVERVIEW(Messages.AbstractLomPage_Overview), DESCRIPTION(Messages.AbstractLomPage_Description), HEALTHCARE(Messages.AbstractLomPage_Healthcare), RECORD(Messages.AbstractLomPage_Record);

		private final String label;

		Category(String label) {
			this.label = label;
		}

		public String label() {
			return label;
		}
	}

	private DataBindingContext bindingContext;
	private ScrolledForm form;
	private EditorServices services;
	private FormSupport formSupport;

	/** The page label (navigation and tab). */
	public abstract String getTitle();

	/**
	 * The page id, used for navigation and for the page icon {@code icons/pages/<id>.png}. Derived from the class
	 * name: {@code LifeCyclePage} becomes {@code lifecycle}, {@code HealthcareAssetPage} becomes
	 * {@code healthcare-asset}.
	 */
	public String getId() {
		String name = getClass().getSimpleName().replaceFirst("Page$", "");
		if (name.startsWith("Healthcare") && name.length() > "Healthcare".length()) {
			return "healthcare-" + name.substring("Healthcare".length()).toLowerCase(Locale.ROOT);
		}
		return name.toLowerCase(Locale.ROOT);
	}

	/** The icon of the page (a path in the editor bundle). */
	public String iconPath() {
		return "icons/pages/" + getId() + ".png";
	}

	/** The context help id of the page. */
	protected String helpContext() {
		return HelpContexts.page(getId());
	}

	/** The label of the navigation group of the page. */
	public String getGroupLabel() {
		return getCategory().label();
	}

	/** The navigation group of the page. */
	public Category getCategory() {
		String id = getId();
		if (id.startsWith("healthcare")) {
			return Category.HEALTHCARE;
		}
		if (id.equals("metametadata") || id.equals("technical")) {
			return Category.RECORD;
		}
		return Category.DESCRIPTION;
	}

	/** A one-line description of the page, shown in the navigation tooltip; may be {@code null}. */
	public String getDescription() {
		return null;
	}

	/** Whether the page shows the MedBiquitous healthcare extensions (teal accent). */
	public boolean isHealthcare() {
		return getCategory() == Category.HEALTHCARE;
	}

	/** Called by the editor before {@link #createControl}. */
	public void init(EditorServices editorServices) {
		this.services = editorServices;
	}

	/** The editor's services (navigation, validation). */
	protected EditorServices services() {
		return services;
	}

	/** The heading shown at the top of the page. */
	public String getHeading() {
		return getTitle();
	}

	/** Creates the fields. {@code body} is a two-column grid; use {@link #column} for each column. */
	protected abstract void createContent(FormSupport form, Composite body);

	/**
	 * Creates the page control.
	 *
	 * @param anchor        observable holding the document's {@code lom} element
	 * @param selectionSink receives elements selected in master lists (for the Properties view)
	 */
	public final Control createControl(Composite parent, FormToolkit toolkit, ModelContext context,
			IObservableValue<EObject> anchor, Consumer<ISelection> selectionSink) {
		bindingContext = new DataBindingContext();
		form = toolkit.createScrolledForm(parent);
		form.setText(getHeading());
		HlomTheme.decorate(toolkit, form, isHealthcare(), HlomTheme.image(iconPath()));
		HelpContexts.set(form, helpContext());
		Composite body = form.getBody();
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).margins(10, 10).spacing(16, 10).applyTo(body);
		formSupport = new FormSupport(toolkit, context, bindingContext, anchor, selectionSink);
		createContent(formSupport, body);
		// Two columns side by side only when both fit; otherwise they stack (instead of a horizontal scroll bar).
		form.addListener(SWT.Resize, _ -> updateColumns());
		updateColumns();
		form.reflow(true);
		return form;
	}

	/** The width (in points) from which the page shows its two columns side by side. */
	private static final int TWO_COLUMN_WIDTH = 940;

	private void updateColumns() {
		if (form == null || form.isDisposed()) {
			return;
		}
		int width = form.getClientArea().width;
		if (width <= 0) {
			return;
		}
		org.eclipse.swt.layout.GridLayout layout = (org.eclipse.swt.layout.GridLayout) form.getBody().getLayout();
		int columns = width >= TWO_COLUMN_WIDTH ? 2 : 1;
		if (layout.numColumns != columns) {
			// Areas spanning both columns (span 2) span the single column then.
			layout.numColumns = columns;
			form.reflow(true);
		}
	}

	/** Creates a column in the page body; sections added to it stack vertically. */
	protected static Composite column(FormSupport form, Composite body) {
		Composite column = form.toolkit().createComposite(body);
		GridLayoutFactory.fillDefaults().spacing(0, 10).applyTo(column);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(column);
		return column;
	}

	/** Creates a full-width area spanning both columns. */
	protected static Composite wide(FormSupport form, Composite body) {
		Composite area = form.toolkit().createComposite(body);
		GridLayoutFactory.fillDefaults().spacing(0, 10).applyTo(area);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(area);
		return area;
	}

	/**
	 * Commits field edits that are still pending (text fields commit after a short typing pause). Only fields
	 * with a pending edit are written, so visiting a page never changes the document.
	 */
	public void commit() {
		if (bindingContext != null) {
			for (Binding binding : bindingContext.getBindings()) {
				if (binding.getTarget().isStale()) {
					binding.updateTargetToModel();
				}
			}
		}
	}

	/** Re-reads all field values from the model. */
	public void refresh() {
		if (bindingContext != null) {
			bindingContext.updateTargets();
		}
		if (form != null && !form.isDisposed()) {
			form.reflow(true);
		}
	}

	/** Summarizes the validation problems of this page in the page heading. */
	public void showProblems(List<HlomProblem> problems) {
		if (form == null || form.isDisposed()) {
			return;
		}
		formSupport.showProblems(problems);
		long errors = problems.stream().filter(p -> p.severity() == HlomProblem.Severity.ERROR).count();
		long warnings = problems.stream().filter(p -> p.severity() == HlomProblem.Severity.WARNING).count();
		if (errors > 0) {
			form.setMessage(count(errors, "error") + (warnings > 0 ? ", " + count(warnings, "warning") : "")
					+ " on this page", IMessageProvider.ERROR);
		} else if (warnings > 0) {
			form.setMessage(NLS.bind(Messages.AbstractLomPage_OnThisPage, count(warnings, "warning")), IMessageProvider.WARNING);
		} else {
			form.setMessage(null, IMessageProvider.NONE);
		}
	}

	private static String count(long count, String noun) {
		return count + " " + noun + (count == 1 ? "" : "s");
	}

	public void dispose() {
		if (bindingContext != null) {
			bindingContext.dispose();
			bindingContext = null;
		}
	}

	protected static LomPackage lom() {
		return LomPackage.eINSTANCE;
	}

	protected static HealthcarePackage hx() {
		return HealthcarePackage.eINSTANCE;
	}
}
