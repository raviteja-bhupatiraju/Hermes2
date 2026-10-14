/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.forms.MedbiqPages;

/**
 * The landing page of documents of the MedBiquitous standards besides Healthcare LOM: the title and description,
 * the standard and version, the parts of the document with their counts, and the validation results linking to
 * the problems. Rebuilt after model changes and validations.
 */
public final class DocumentOverviewPage extends AbstractOverviewPage {

	private static final int TOP_PROBLEMS = 5;

	private final MedbiqDocumentType type;
	private Composite hero;
	private Composite facts;
	private Composite validation;

	public DocumentOverviewPage(MedbiqDocumentType type) {
		this.type = type;
	}

	@Override
	public String getId() {
		return OverviewPage.ID;
	}

	@Override
	public Category getCategory() {
		return Category.OVERVIEW;
	}

	@Override
	public String getTitle() {
		return Messages.DocumentOverviewPage_Overview;
	}

	@Override
	public String getDescription() {
		return Messages.DocumentOverviewPage_SummaryAndValidationStatusOf;
	}

	@Override
	public boolean isHealthcare() {
		return false;
	}

	@Override
	protected List<Composite> createCards(Composite left, Composite right) {
		hero = OverviewWidgets.card(left, HlomTheme.TEAL);
		facts = OverviewWidgets.card(left, null);
		validation = OverviewWidgets.card(right, null);
		return List.of(hero, facts, validation);
	}

	@Override
	protected void fillCards() {
		EObject element = form.anchor().getValue();
		createHero(element);
		createFacts(element);
		createValidation();
	}

	private void createHero(EObject element) {
		EObject documentRoot = element == null ? null : element.eContainer();
		cellLabel(hero, type.standard().toUpperCase(java.util.Locale.ROOT) + "  ·  " + type.version(),
				OverviewWidgets.font(-1, SWT.BOLD), HlomTheme.TEAL, false);
		String title = documentRoot == null ? null : type.title(documentRoot);
		cellLabel(hero, title == null || title.isBlank() ? "(untitled)" : title, OverviewWidgets.font(6, SWT.BOLD),
				HlomTheme.INK, true);
		String description = documentRoot == null ? null : type.description(documentRoot);
		if (description != null && !description.isBlank()) {
			cellLabel(hero, abbreviate(description, 480), OverviewWidgets.font(0, SWT.NORMAL), HlomTheme.SLATE, true);
		}
		Hyperlink specification = new Hyperlink(hero, SWT.NONE);
		styleLink(specification, NLS.bind(Messages.DocumentOverviewPage_TheStandardMedbiqOrg, type.standard()), HlomTheme.TEAL);
		specification.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Program.launch(type.specificationUrl());
			}
		});
	}

	/** The parts of the root element with their counts. */
	private void createFacts(EObject element) {
		cellLabel(facts, Messages.DocumentOverviewPage_Contents, OverviewWidgets.font(1, SWT.BOLD), HlomTheme.INK, false);
		Composite grid = surface(facts);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(16, 6).applyTo(grid);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 4).applyTo(grid);
		if (element == null) {
			cellLabel(grid, Messages.DocumentOverviewPage_TheDocumentHasNoContent, OverviewWidgets.font(0, SWT.NORMAL), HlomTheme.MUTED, true);
			return;
		}
		for (EStructuralFeature feature : MedbiqPages.elementsAndAttributes(element.eClass())) {
			if (MedbiqPages.isSimple(feature) && !feature.isMany()) {
				continue;
			}
			Object value = element.eGet(feature);
			int count = value instanceof List<?> list ? list.size() : value == null ? 0 : 1;
			String name = FieldLabels.words(MedbiqPages.name(feature).replace("@", ""));
			cellLabel(grid, name, OverviewWidgets.font(0, SWT.NORMAL), HlomTheme.SLATE, false);
			cellLabel(grid, count == 0 ? "–" : feature.isMany() ? Integer.toString(count) : "✓",
					OverviewWidgets.font(0, SWT.BOLD), count == 0 ? HlomTheme.MUTED : HlomTheme.INK, false);
		}
	}

	private void createValidation() {
		cellLabel(validation, Messages.DocumentOverviewPage_Validation, OverviewWidgets.font(1, SWT.BOLD), HlomTheme.INK, false);
		ValidationSource source = validationSource();
		List<HlomProblem> problems = source == null ? List.of() : source.getProblems();
		long errors = problems.stream().filter(p -> p.severity() == Severity.ERROR).count();
		long warnings = problems.stream().filter(p -> p.severity() == Severity.WARNING).count();
		if (errors == 0 && warnings == 0) {
			cellLabel(validation, NLS.bind(Messages.DocumentOverviewPage_ConformsToTheSchemasAnd, type.label()),
					OverviewWidgets.font(0, SWT.BOLD), OverviewWidgets.GREEN, true);
			return;
		}
		cellLabel(validation, errors + (errors == 1 ? " error, " : " errors, ") + warnings
				+ (warnings == 1 ? " warning" : " warnings"), OverviewWidgets.font(0, SWT.BOLD),
				errors > 0 ? HlomTheme.ACCENT : OverviewWidgets.AMBER, false);
		List<HlomProblem> top = new ArrayList<>(problems);
		top.sort(Comparator.comparing(HlomProblem::severity));
		for (HlomProblem problem : top.subList(0, Math.min(TOP_PROBLEMS, top.size()))) {
			Hyperlink link = new Hyperlink(validation, SWT.WRAP);
			styleLink(link, abbreviate(problem.message(), 160), HlomTheme.INK);
			link.setToolTipText(problem.origin().label() + (problem.path() != null ? "  " + problem.path() : ""));
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(link);
			link.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					if (source != null) {
						source.reveal(problem);
					}
				}
			});
		}
		if (problems.size() > TOP_PROBLEMS) {
			cellLabel(validation, NLS.bind(Messages.DocumentOverviewPage_AndMore, (problems.size() - TOP_PROBLEMS)), OverviewWidgets.font(-1, SWT.NORMAL),
					HlomTheme.MUTED, false);
		}
	}

	/** A label filling the width of its grid cell. */
	private static Label cellLabel(Composite parent, String text, org.eclipse.swt.graphics.Font font,
			org.eclipse.swt.graphics.RGB color, boolean wrap) {
		Label label = AbstractOverviewPage.label(parent, text, font, color, wrap);
		GridDataFactory.fillDefaults().grab(true, false).hint(wrap ? 100 : SWT.DEFAULT, SWT.DEFAULT).applyTo(label);
		return label;
	}

}
