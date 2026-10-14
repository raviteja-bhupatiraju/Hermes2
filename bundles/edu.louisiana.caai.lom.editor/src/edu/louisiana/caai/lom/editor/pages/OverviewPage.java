/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.pages.OverviewFacts.Fact;
import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.CategoryBar;
import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.ChipFlow;
import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.CountBadge;
import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.ScoreRing;
import edu.louisiana.caai.lom.editor.pages.OverviewWidgets.StepNumber;
import edu.louisiana.caai.lom.editor.validation.ValidationSource;
import edu.louisiana.caai.lom.editor.validation.ValidationView;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.support.HlomCompleteness;
import edu.louisiana.caai.lom.model.support.HlomCompleteness.Item;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * The landing page of the editor: a read-only dashboard summarizing the record (title, kind, description,
 * keywords, key facts), its completeness against HLOM best practice ({@link HlomCompleteness}), the validation
 * results and the most valuable next steps, each linking to the page where it is edited.
 * <p>
 * The dashboard is rebuilt after model changes and validations while it is visible, and when it is shown again.
 */
public class OverviewPage extends AbstractOverviewPage {

	/** Page id. */
	public static final String ID = "overview";

	private static final int NEXT_STEPS = 5;
	private static final int TOP_PROBLEMS = 3;
	private static final int DESCRIPTION_LENGTH = 420;

	private Composite hero;
	private Composite facts;
	private Composite completeness;
	private Composite validation;
	private Composite nextSteps;

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Category getCategory() {
		return Category.OVERVIEW;
	}

	@Override
	public String getTitle() {
		return Messages.OverviewPage_Overview;
	}

	@Override
	public String getDescription() {
		return Messages.OverviewPage_SummaryCompletenessAndValidationStatus;
	}

	@Override
	protected List<Composite> createCards(Composite left, Composite right) {
		hero = OverviewWidgets.card(left, HlomTheme.ACCENT);
		facts = OverviewWidgets.card(left, null);
		completeness = OverviewWidgets.card(right, null);
		validation = OverviewWidgets.card(right, null);
		nextSteps = OverviewWidgets.card(left, null);
		return List.of(hero, facts, completeness, validation, nextSteps);
	}

	/** Fills the dashboard from the current document and validation results. */
	@Override
	protected void fillCards() {
		Lom lom = form.anchor().getValue() instanceof Lom l ? l : LomFactory.eINSTANCE.createLom();
		HlomCompleteness assessment = HlomCompleteness.assess(lom);
		OverviewFacts digest = new OverviewFacts(lom);
		createHero(digest, assessment);
		createFacts(digest, assessment);
		createCompleteness(assessment);
		createValidation();
		createNextSteps(assessment);
	}

	// ----------------------------------------------------------------------------------------------------
	// Hero
	// ----------------------------------------------------------------------------------------------------

	private void createHero(OverviewFacts digest, HlomCompleteness assessment) {
		boolean healthcareKind = assessment.isMediaAsset() && !assessment.isEducationalOffering();
		label(hero, digest.kind(assessment.isEducationalOffering(), assessment.isMediaAsset()),
				OverviewWidgets.font(-1, SWT.BOLD), healthcareKind ? HlomTheme.TEAL : HlomTheme.ACCENT, false);

		String title = digest.title();
		if (title != null) {
			Label heading = label(hero, title, OverviewWidgets.font(8, SWT.BOLD), HlomTheme.INK, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(200, SWT.DEFAULT).indent(0, 2).applyTo(heading);
		} else {
			Label heading = label(hero, Messages.OverviewPage_UntitledResource, OverviewWidgets.font(8, SWT.BOLD), HlomTheme.MUTED, false);
			GridDataFactory.fillDefaults().indent(0, 2).applyTo(heading);
			link(hero, Messages.OverviewPage_AddATitle, HlomCompleteness.GENERAL, HlomTheme.ACCENT);
		}

		String description = digest.description();
		if (description != null) {
			Label text = label(hero, abbreviate(description, DESCRIPTION_LENGTH), OverviewWidgets.font(0, SWT.NORMAL),
					HlomTheme.SLATE, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(200, SWT.DEFAULT).indent(0, 4).applyTo(text);
			if (description.length() > DESCRIPTION_LENGTH) {
				text.setToolTipText(description);
			}
		} else {
			Label text = label(hero, Messages.OverviewPage_NoDescriptionYet, null, HlomTheme.MUTED, false);
			GridDataFactory.fillDefaults().indent(0, 4).applyTo(text);
		}

		List<OverviewWidgets.Chip> keywords = digest.keywords();
		if (!keywords.isEmpty()) {
			ChipFlow chips = new ChipFlow(hero, keywords);
			GridDataFactory.fillDefaults().grab(true, false).hint(200, SWT.DEFAULT).indent(0, 8).applyTo(chips);
		}

		StringBuilder meta = new StringBuilder();
		List<String> languages = digest.languages();
		append(meta, languages.isEmpty() ? null : (languages.size() == 1 ? "Language " : "Languages ")
				+ String.join(", ", languages));
		append(meta, digest.identifier());
		append(meta, digest.resourceLocation());
		if (!meta.isEmpty()) {
			Label line = label(hero, meta.toString(), OverviewWidgets.font(-1, SWT.NORMAL), HlomTheme.MUTED, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(200, SWT.DEFAULT).indent(0, 6).applyTo(line);
		}
	}

	private static void append(StringBuilder builder, String part) {
		if (part != null) {
			builder.append(builder.isEmpty() ? "" : "   ·   ").append(part);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Key facts
	// ----------------------------------------------------------------------------------------------------

	private void createFacts(OverviewFacts digest, HlomCompleteness assessment) {
		header(facts, Messages.OverviewPage_KeyFacts, null);
		Composite grid = surface(facts);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).spacing(28, 14).applyTo(grid);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 4).applyTo(grid);
		for (Fact fact : digest.facts(assessment.isEducationalOffering(), assessment.isMediaAsset())) {
			Composite cell = surface(grid);
			GridLayoutFactory.fillDefaults().spacing(0, 3).applyTo(cell);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(cell);
			label(cell, fact.label().toUpperCase(Locale.ROOT), OverviewWidgets.font(-2, SWT.BOLD),
					HlomTheme.MUTED, false);
			if (fact.value() != null) {
				Label value = label(cell, fact.value(), null, HlomTheme.INK, true);
				GridDataFactory.fillDefaults().grab(true, false).hint(120, SWT.DEFAULT).applyTo(value);
			} else {
				Hyperlink add = link(cell, Messages.OverviewPage_NotSpecified, fact.pageId(), HlomTheme.MUTED);
				add.setToolTipText(NLS.bind(Messages.OverviewPage_OpenThePage, pageName(fact.pageId())));
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Completeness
	// ----------------------------------------------------------------------------------------------------

	private void createCompleteness(HlomCompleteness assessment) {
		header(completeness, Messages.OverviewPage_Completeness, Messages.OverviewPage_RecommendedHealthcareLOMElements);

		Composite summary = surface(completeness);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(18, 0).applyTo(summary);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 6).applyTo(summary);
		int score = assessment.score();
		ScoreRing ring = new ScoreRing(summary, HlomTheme.ACCENT);
		ring.setScore(score);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(ring);

		Composite text = surface(summary);
		GridLayoutFactory.fillDefaults().spacing(0, 4).applyTo(text);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(text);
		label(text, rating(score), OverviewWidgets.font(4, SWT.BOLD), HlomTheme.INK, false);
		List<Item> items = assessment.items();
		long filled = items.stream().filter(Item::filled).count();
		Label count = label(text, NLS.bind(Messages.OverviewPage_OfRecommendedElementsPresent, filled, items.size()), null,
				HlomTheme.SLATE, true);
		GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(count);
		Label profile = label(text, NLS.bind(Messages.OverviewPage_Profile, profile(assessment)), OverviewWidgets.font(-1, SWT.NORMAL),
				HlomTheme.MUTED, true);
		GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(profile);

		Composite bars = surface(completeness);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(bars);
		GridDataFactory.fillDefaults().grab(true, false).indent(-8, 10).applyTo(bars);
		for (HlomCompleteness.Category category : assessment.categories()) {
			boolean applicable = category.applicable();
			String detail = applicable ? category.filledCount() + " / " + category.items().size() : "Not applicable";
			RGB rgb = category.healthcare() ? HlomTheme.TEAL : HlomTheme.ACCENT;
			Image icon = HlomTheme.image("icons/pages/" + category.pageId() + ".png");
			CategoryBar bar = new CategoryBar(bars, icon, category.label(), detail, category.score(), rgb, applicable,
					() -> showPage(category.pageId()));
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(bar);
			bar.setToolTipText(barTooltip(category));
		}
	}

	private static String rating(int score) {
		if (score >= 90) {
			return Messages.OverviewPage_Excellent;
		}
		if (score >= 70) {
			return Messages.OverviewPage_Good;
		}
		if (score >= 45) {
			return Messages.OverviewPage_Fair;
		}
		return Messages.OverviewPage_NeedsWork;
	}

	private static String profile(HlomCompleteness assessment) {
		if (assessment.isEducationalOffering() && assessment.isMediaAsset()) {
			return Messages.OverviewPage_EducationalOfferingWithMediaAsset;
		}
		if (assessment.isEducationalOffering()) {
			return Messages.OverviewPage_EducationalOffering;
		}
		return assessment.isMediaAsset() ? "media asset" : "learning resource";
	}

	private static String barTooltip(HlomCompleteness.Category category) {
		if (!category.applicable()) {
			return category.healthcare() && category.pageId().equals(HlomCompleteness.HEALTHCARE_ASSET)
					? "Applies to image, video and animation assets."
					: category.healthcare() ? "Applies to educational offerings." : null;
		}
		List<String> missing = category.items().stream().filter(i -> !i.filled()).map(Item::label).toList();
		return missing.isEmpty() ? category.label() + ": all recommended elements present"
				: "Missing: " + String.join(", ", missing);
	}

	// ----------------------------------------------------------------------------------------------------
	// Validation
	// ----------------------------------------------------------------------------------------------------

	private void createValidation() {
		Composite head = header(validation, Messages.OverviewPage_Validation, null);
		Hyperlink show = new Hyperlink(head, SWT.NONE);
		styleLink(show, Messages.OverviewPage_ShowProblems, HlomTheme.ACCENT);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).grab(true, false).applyTo(show);
		show.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				showProblemsView();
			}
		});

		ValidationSource source = validationSource();
		List<HlomProblem> problems = source == null ? List.of() : source.getProblems();
		int errors = count(problems, Severity.ERROR);
		int warnings = count(problems, Severity.WARNING);
		int infos = count(problems, Severity.INFO);

		Composite badges = surface(validation);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(18, 0).applyTo(badges);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 4).applyTo(badges);
		new CountBadge(badges, errors, Messages.OverviewPage_Error, Messages.OverviewPage_Errors, HlomTheme.ACCENT);
		new CountBadge(badges, warnings, Messages.OverviewPage_Warning, Messages.OverviewPage_Warnings, OverviewWidgets.AMBER);
		new CountBadge(badges, infos, Messages.OverviewPage_Info, Messages.OverviewPage_Infos, OverviewWidgets.BLUE);

		if (errors == 0 && warnings == 0) {
			Label ok = label(validation, Messages.OverviewPage_ConformsToTheHealthcareLOM,
					OverviewWidgets.font(0, SWT.BOLD), OverviewWidgets.GREEN, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, 6).applyTo(ok);
		}

		List<HlomProblem> top = problems.stream().sorted(Comparator.comparing(HlomProblem::severity))
				.limit(TOP_PROBLEMS).toList();
		if (!top.isEmpty()) {
			Composite list = surface(validation);
			GridLayoutFactory.fillDefaults().numColumns(2).spacing(8, 8).applyTo(list);
			GridDataFactory.fillDefaults().grab(true, false).indent(0, 8).applyTo(list);
			for (HlomProblem problem : top) {
				Label icon = new Label(list, SWT.NONE);
				icon.setBackground(color(HlomTheme.SURFACE));
				icon.setImage(severityImage(problem.severity()));
				GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).indent(0, 1).applyTo(icon);
				Hyperlink link = new Hyperlink(list, SWT.WRAP);
				styleLink(link, abbreviate(problem.message(), 160), HlomTheme.INK);
				link.setToolTipText(problem.origin().label() + (problem.path() != null ? "  " + problem.path() : ""));
				GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(link);
				link.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						reveal(problem);
					}
				});
			}
			if (problems.size() > TOP_PROBLEMS) {
				Label more = label(validation, NLS.bind(Messages.OverviewPage_AndMore, (problems.size() - TOP_PROBLEMS)),
						OverviewWidgets.font(-1, SWT.NORMAL), HlomTheme.MUTED, false);
				GridDataFactory.fillDefaults().indent(24, 0).applyTo(more);
			}
		}
	}

	private static int count(List<HlomProblem> problems, Severity severity) {
		return (int) problems.stream().filter(p -> p.severity() == severity).count();
	}

	private static Image severityImage(Severity severity) {
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		return images.getImage(switch (severity) {
		case ERROR -> ISharedImages.IMG_OBJS_ERROR_TSK;
		case WARNING -> ISharedImages.IMG_OBJS_WARN_TSK;
		case INFO -> ISharedImages.IMG_OBJS_INFO_TSK;
		});
	}

	private void reveal(HlomProblem problem) {
		if (services() == null) {
			return;
		}
		if (problem.object() != null) {
			services().reveal(problem.object());
		} else {
			services().validation().reveal(problem);
		}
	}

	private static void showProblemsView() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page != null) {
			try {
				page.showView(ValidationView.ID);
			} catch (PartInitException _) {
				// The view is part of this bundle; nothing sensible to do if it can't be opened.
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Next steps
	// ----------------------------------------------------------------------------------------------------

	private void createNextSteps(HlomCompleteness assessment) {
		header(nextSteps, Messages.OverviewPage_NextSteps, Messages.OverviewPage_TheMostValuableAdditionsTo);
		List<Item> steps = assessment.nextSteps(NEXT_STEPS);
		if (steps.isEmpty()) {
			Label done = label(nextSteps, Messages.OverviewPage_NothingLeftToRecommendAll,
					OverviewWidgets.font(0, SWT.BOLD), OverviewWidgets.GREEN, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, 4).applyTo(done);
			return;
		}
		Composite list = surface(nextSteps);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(12, 12).applyTo(list);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 6).applyTo(list);
		int number = 1;
		for (Item step : steps) {
			boolean healthcare = step.pageId().startsWith("healthcare");
			RGB rgb = healthcare ? HlomTheme.TEAL : HlomTheme.ACCENT;
			StepNumber dot = new StepNumber(list, number++, rgb);
			GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).applyTo(dot);
			Composite text = surface(list);
			GridLayoutFactory.fillDefaults().spacing(0, 1).applyTo(text);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
			Composite line = surface(text);
			GridLayoutFactory.fillDefaults().numColumns(2).spacing(8, 0).applyTo(line);
			Hyperlink link = new Hyperlink(line, SWT.NONE);
			styleLink(link, step.label(), HlomTheme.INK);
			link.setFont(OverviewWidgets.font(0, SWT.BOLD));
			link.setToolTipText(NLS.bind(Messages.OverviewPage_OpenThePage, pageName(step.pageId())));
			link.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					showPage(step.pageId());
				}
			});
			label(line, pageName(step.pageId()), OverviewWidgets.font(-1, SWT.NORMAL), rgb, false);
			Label hint = label(text, step.hint(), null, HlomTheme.SLATE, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(hint);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Building blocks
	// ----------------------------------------------------------------------------------------------------

	private void showPage(String pageId) {
		if (services() != null) {
			services().showPage(pageId);
		}
	}

	/** The display name of a page. */
	private static String pageName(String pageId) {
		return switch (pageId) {
		case "lifecycle" -> Messages.OverviewPage_Lifecycle;
		case "metametadata" -> Messages.OverviewPage_MetaMetadata;
		case "healthcare-education" -> Messages.OverviewPage_HealthcareEducation;
		case "healthcare-asset" -> Messages.OverviewPage_HealthcareAsset;
		default -> OverviewFacts.capitalize(pageId);
		};
	}

	/** A card header: a bold title, an optional subtitle; returns the header row (two columns). */
	private static Composite header(Composite card, String title, String subtitle) {
		Composite head = surface(card);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(head);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(head);
		label(head, title, HlomTheme.sectionFont(), HlomTheme.INK, false);
		if (subtitle != null) {
			Label sub = label(card, subtitle, OverviewWidgets.font(-1, SWT.NORMAL), HlomTheme.MUTED, true);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, -4).applyTo(sub);
		}
		return head;
	}

	private Hyperlink link(Composite parent, String text, String pageId, RGB rgb) {
		Hyperlink link = new Hyperlink(parent, SWT.NONE);
		styleLink(link, text, rgb);
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				showPage(pageId);
			}
		});
		return link;
	}

	private static Color color(RGB rgb) {
		return HlomTheme.color(rgb);
	}
}
