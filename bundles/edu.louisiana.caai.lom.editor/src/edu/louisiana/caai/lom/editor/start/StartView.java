/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.part.ViewPart;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * The branded start page shown while no document is open: a hero banner, action tiles (new, open, sample),
 * template quick starts, the recent documents and a short introduction to the MedBiquitous standards. The layout
 * adapts to the available width (two columns, or one on narrow windows).
 */
public class StartView extends ViewPart {

	/** The view id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.start.StartView";

	private static final String FONT_PREFIX = "edu.louisiana.caai.lom.start.";
	private static final int SIDE_WIDTH = 340;
	private static final int MARGIN = 48;
	private static final int NARROW = 980;

	private ScrolledComposite scroller;
	private Composite content;
	private Composite body;
	private Composite side;
	private Composite actions;
	private Composite templates;
	private Composite recentPanel;
	private Card firstCard;
	private final Runnable recentListener = this::refreshRecent;

	@Override
	public void createPartControl(Composite parent) {
		HelpContexts.set(parent, HelpContexts.START_PAGE);
		scroller = new ScrolledComposite(parent, SWT.V_SCROLL);
		scroller.setExpandHorizontal(true);
		scroller.setExpandVertical(true);
		scroller.getVerticalBar().setIncrement(24);
		scroller.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));

		content = new Composite(scroller, SWT.NONE);
		content.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
		content.setBackgroundMode(SWT.INHERIT_DEFAULT);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(content);
		scroller.setContent(content);

		Canvas hero = new Canvas(content, SWT.DOUBLE_BUFFERED);
		hero.addPaintListener(event -> paintHero(event.gc, hero.getClientArea()));
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 196).applyTo(hero);

		body = new Composite(content, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(MARGIN, 32).spacing(36, 24).applyTo(body);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);

		Composite main = new Composite(body, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(main);
		createActions(main);
		createTemplates(main);

		side = new Composite(body, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 20).applyTo(side);
		GridDataFactory.fillDefaults().hint(SIDE_WIDTH, SWT.DEFAULT).align(SWT.FILL, SWT.BEGINNING).applyTo(side);
		recentPanel = panel(side);
		refreshRecent();
		createAbout(panel(side));

		Label footer = new Label(content, SWT.WRAP);
		footer.setText(Messages.StartView_MedBiquitousStandardsForHealthProfession);
		footer.setForeground(HlomTheme.color(HlomTheme.MUTED));
		GridDataFactory.fillDefaults().grab(true, false).indent(MARGIN, 0).hint(100, SWT.DEFAULT)
				.applyTo(footer);
		Label spacer = new Label(content, SWT.NONE);
		GridDataFactory.fillDefaults().hint(1, 24).applyTo(spacer);

		scroller.addListener(SWT.Resize, _ -> relayout());
		RecentDocuments.addListener(recentListener);
		relayout();
	}

	private IWorkbenchWindow window() {
		return getSite().getWorkbenchWindow();
	}

	// --- Hero ---------------------------------------------------------------------------------------------

	private void paintHero(GC gc, Rectangle area) {
		gc.setAntialias(SWT.ON);
		gc.setTextAntialias(SWT.ON);
		// Soft horizontal wash from white to a hint of teal.
		gc.setForeground(HlomTheme.color(HlomTheme.SURFACE));
		gc.setBackground(HlomTheme.color(Card.blend(HlomTheme.TEAL, HlomTheme.SURFACE, 0.07)));
		gc.fillGradientRectangle(0, 0, area.width, area.height, false);

		// Decorative rings and cross on the right.
		int cx = area.width - 150;
		int cy = area.height / 2 + 10;
		gc.setLineWidth(2);
		for (int i = 0; i < 4; i++) {
			int r = 46 + i * 34;
			gc.setForeground(HlomTheme.color(Card.blend(HlomTheme.TEAL, HlomTheme.SURFACE, 0.16 - i * 0.03)));
			gc.drawOval(cx - r, cy - r, 2 * r, 2 * r);
		}
		gc.setLineWidth(1);
		gc.setBackground(HlomTheme.color(Card.blend(HlomTheme.TEAL, HlomTheme.SURFACE, 0.22)));
		gc.fillRoundRectangle(cx - 9, cy - 28, 18, 56, 6, 6);
		gc.fillRoundRectangle(cx - 28, cy - 9, 56, 18, 6, 6);
		gc.setBackground(HlomTheme.color(HlomTheme.ACCENT));
		gc.fillOval(cx + 70, cy - 78, 12, 12);

		// Bottom keyline.
		gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
		gc.drawLine(0, area.height - 1, area.width, area.height - 1);

		// App mark, title, tagline and standards chips.
		Image mark = HlomTheme.image("icons/app/hlom64.png");
		int x = MARGIN;
		int markSize = 64;
		int top = 40;
		if (mark != null) {
			Rectangle bounds = mark.getBounds();
			gc.drawImage(mark, 0, 0, bounds.width, bounds.height, x, top + 4, markSize, markSize);
		}
		int tx = x + markSize + 24;
		gc.setFont(font("hero", 14, SWT.BOLD));
		gc.setForeground(HlomTheme.color(HlomTheme.INK));
		gc.drawString(Messages.StartView_Hermes2, tx, top - 4, true);
		int titleHeight = Card.lineHeight(gc);
		Point titleExtent = gc.textExtent(Messages.StartView_Hermes2);
		int titleBaseline = top - 4 + titleExtent.y - gc.getFontMetrics().getDescent();
		// The product description next to the name, on the same baseline.
		gc.setFont(font("subtitle", 5, SWT.BOLD));
		gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
		Point subtitleExtent = gc.textExtent(Messages.StartView_MedBiquitousEditor);
		gc.drawString(Messages.StartView_MedBiquitousEditor, tx + titleExtent.x + 16,
				titleBaseline - (subtitleExtent.y - gc.getFontMetrics().getDescent()), true);
		gc.setBackground(HlomTheme.color(HlomTheme.ACCENT));
		gc.fillRoundRectangle(tx, top + titleHeight - 2, 44, 4, 4, 4);

		gc.setFont(font("tagline", 2, SWT.NORMAL));
		gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
		int ty = top + titleHeight + 12;
		int available = Math.clamp(area.width - tx - 300, 240, 720);
		for (String line : Card.wrap(gc, Messages.StartView_CreateValidateAndExchangeMedBiquitous, available, 3)) {
			gc.drawString(line, tx, ty, true);
			ty += Card.lineHeight(gc);
		}

		ty += 12;
		gc.setFont(HlomTheme.badgeFont());
		int chipX = tx;
		for (String chip : new String[] { Messages.StartView_9MedBiquitousStandards, Messages.StartView_HealthcareLOMV1, Messages.StartView_SchemaValidation,
				Messages.StartView_WorksOffline }) {
			Point extent = gc.textExtent(chip);
			int w = extent.x + 20;
			int h = extent.y + 8;
			gc.setBackground(HlomTheme.color(HlomTheme.SURFACE));
			gc.fillRoundRectangle(chipX, ty, w, h, h, h);
			gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			gc.drawRoundRectangle(chipX, ty, w - 1, h - 1, h, h);
			gc.setForeground(HlomTheme.color(HlomTheme.SLATE));
			gc.drawString(chip, chipX + 10, ty + 4, true);
			chipX += w + 8;
		}
	}

	// --- Main column ----------------------------------------------------------------------------------

	private void createActions(Composite parent) {
		heading(parent, Messages.StartView_GetStarted, null);
		actions = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(true).spacing(16, 16).applyTo(actions);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 12).applyTo(actions);

		firstCard = tile(actions, "icons/start/new.png", Messages.StartView_NewDocument,
				Messages.StartView_CreateADocumentOfAny, HlomTheme.ACCENT,
				() -> StartActions.newDocument(window(), (String) null));
		tile(actions, "icons/start/open.png", Messages.StartView_Open, Messages.StartView_OpenAnExistingMetadataFile,
				HlomTheme.ACCENT, () -> StartActions.open(window()));
		tile(actions, "icons/start/project.png", Messages.StartView_NewProject,
				Messages.StartView_ManageManyRecordsTogetherIn, HlomTheme.ACCENT,
				() -> edu.louisiana.caai.lom.editor.projects.ProjectActions.newProject(window()));
		Card sample = tile(actions, "icons/start/sample.png", Messages.StartView_OpenSample,
				Messages.StartView_ExploreCompleteSampleDocumentsOf, HlomTheme.TEAL, null);
		sample.onActivate(() -> showSampleMenu(sample));
	}

	private Card tile(Composite parent, String icon, String title, String description, RGB accent,
			Runnable action) {
		Card card = new Card(parent, Card.VERTICAL).icon(HlomTheme.image(icon)).title(title).description(description)
				.accent(accent).maxLines(3);
		if (action != null) {
			card.onActivate(action);
		}
		GridDataFactory.fillDefaults().grab(true, true).applyTo(card);
		return card;
	}

	private void showSampleMenu(Card card) {
		Menu menu = new Menu(card);
		for (StartActions.Sample sample : StartActions.SAMPLES) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(sample.title() + "\t" + sample.description());
			item.addListener(SWT.Selection, _ -> StartActions.openSample(window(), sample));
		}
		Map<String, List<StartActions.StandardSample>> byStandard = new java.util.LinkedHashMap<>();
		for (StartActions.StandardSample sample : StartActions.standardSamples()) {
			byStandard.computeIfAbsent(sample.standard(), _ -> new java.util.ArrayList<>()).add(sample);
		}
		if (!byStandard.isEmpty()) {
			new MenuItem(menu, SWT.SEPARATOR);
		}
		for (Map.Entry<String, List<StartActions.StandardSample>> entry : byStandard.entrySet()) {
			MenuItem cascade = new MenuItem(menu, SWT.CASCADE);
			cascade.setText(entry.getKey());
			Menu submenu = new Menu(cascade);
			cascade.setMenu(submenu);
			for (StartActions.StandardSample sample : entry.getValue()) {
				MenuItem item = new MenuItem(submenu, SWT.PUSH);
				item.setText(sample.title());
				item.addListener(SWT.Selection, _ -> StartActions.openStandardSample(window(), sample));
			}
		}
		new MenuItem(menu, SWT.SEPARATOR);
		MenuItem folder = new MenuItem(menu, SWT.PUSH);
		folder.setText(NLS.bind(Messages.StartView_SamplesAreCopiedTo, StartActions.samplesFolder().getPath()));
		folder.setEnabled(false);
		Rectangle bounds = card.getBounds();
		Point location = card.getParent().toDisplay(bounds.x, bounds.y + bounds.height);
		menu.setLocation(location);
		menu.setVisible(true);
	}

	private void createTemplates(Composite parent) {
		Label spacer = new Label(parent, SWT.NONE);
		GridDataFactory.fillDefaults().hint(1, 28).applyTo(spacer);
		heading(parent, Messages.StartView_StartFromATemplate,
				Messages.StartView_PreFilledSchemaValidDocuments);
		templates = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).spacing(16, 16).applyTo(templates);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 12).applyTo(templates);
		for (Template template : HlomTemplates.all()) {
			Card card = new Card(templates, Card.HORIZONTAL).icon(templateIcon(template)).title(template.label())
					.description(template.description()).maxLines(4)
					.accent(template == HlomTemplates.BLANK ? HlomTheme.ACCENT : HlomTheme.TEAL)
					.onActivate(() -> StartActions.newDocument(window(), template.id()));
			GridDataFactory.fillDefaults().grab(true, true).applyTo(card);
		}
		for (MedbiqDocumentType type : MedbiqStandards.TYPES) {
			Card card = new Card(templates, Card.HORIZONTAL).icon(HlomTheme.image("icons/pages/general.png"))
					.title(type.standard()).description(type.label() + " document").maxLines(4)
					.accent(HlomTheme.INK).onActivate(() -> StartActions.newDocument(window(), type));
			GridDataFactory.fillDefaults().grab(true, true).applyTo(card);
		}
	}

	/** The large icon of a template ({@code icons/start/template-<id>.png}, else its page icon). */
	public static Image templateIcon(Template template) {
		Image image = HlomTheme.image("icons/start/template-" + template.id() + ".png");
		return image != null ? image : HlomTheme.image("icons/pages/" + template.iconId() + ".png");
	}

	private void heading(Composite parent, String title, String subtitle) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(title);
		label.setFont(font("section", 3, SWT.BOLD));
		label.setForeground(HlomTheme.color(HlomTheme.INK));
		if (subtitle != null) {
			Label sub = new Label(parent, SWT.WRAP);
			sub.setText(subtitle);
			sub.setForeground(HlomTheme.color(HlomTheme.SLATE));
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, 4).applyTo(sub);
		}
	}

	// --- Side column ----------------------------------------------------------------------------------

	/** A white, rounded panel with a border. */
	private Composite panel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		panel.setBackgroundMode(SWT.INHERIT_FORCE);
		panel.addPaintListener(event -> {
			Rectangle area = panel.getClientArea();
			GC gc = event.gc;
			gc.setAntialias(SWT.ON);
			gc.setBackground(HlomTheme.color(HlomTheme.BACKGROUND));
			gc.fillRectangle(area);
			gc.setBackground(HlomTheme.color(HlomTheme.SURFACE));
			gc.fillRoundRectangle(0, 0, area.width - 1, area.height - 1, 12, 12);
			gc.setForeground(HlomTheme.color(HlomTheme.BORDER));
			gc.drawRoundRectangle(0, 0, area.width - 1, area.height - 1, 12, 12);
		});
		GridLayoutFactory.fillDefaults().margins(20, 18).spacing(8, 6).applyTo(panel);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(panel);
		return panel;
	}

	private void refreshRecent() {
		if (recentPanel == null || recentPanel.isDisposed()) {
			return;
		}
		for (Control child : recentPanel.getChildren()) {
			child.dispose();
		}
		List<String> paths = RecentDocuments.get();

		Composite header = new Composite(recentPanel, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(header);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(header);
		Label title = new Label(header, SWT.NONE);
		title.setText(Messages.StartView_RecentDocuments);
		title.setFont(font("panel", 1, SWT.BOLD));
		title.setForeground(HlomTheme.color(HlomTheme.INK));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(title);
		if (!paths.isEmpty()) {
			Hyperlink clear = link(header, Messages.StartView_Clear, HlomTheme.MUTED, () -> RecentDocuments.clear());
			clear.setToolTipText(Messages.StartView_ForgetTheRecentDocuments);
			GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(clear);
		}

		if (paths.isEmpty()) {
			Label empty = new Label(recentPanel, SWT.WRAP);
			empty.setText(Messages.StartView_DocumentsYouCreateOrOpen);
			empty.setForeground(HlomTheme.color(HlomTheme.SLATE));
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, 6).applyTo(empty);
		}
		Image documentIcon = HlomTheme.image("icons/pages/general.png");
		for (String path : paths) {
			File file = new File(path);
			boolean exists = file.isFile();
			Composite row = new Composite(recentPanel, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(2).spacing(10, 0).applyTo(row);
			GridDataFactory.fillDefaults().grab(true, false).indent(0, 6).applyTo(row);
			Label icon = new Label(row, SWT.NONE);
			icon.setImage(documentIcon);
			GridDataFactory.fillDefaults().span(1, 2).align(SWT.CENTER, SWT.BEGINNING).indent(0, 2).applyTo(icon);
			Hyperlink link = link(row, file.getName() + (exists ? "" : "  (missing)"),
					exists ? HlomTheme.INK : HlomTheme.MUTED, () -> StartActions.openRecent(window(), path));
			link.setToolTipText(path);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(link);
			Label folder = new Label(row, SWT.NONE);
			folder.setText(OpenRecentMenu.abbreviate(file.getParent() == null ? "" : file.getParent(), 38));
			folder.setToolTipText(path);
			folder.setForeground(HlomTheme.color(HlomTheme.MUTED));
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(folder);
		}
		recentPanel.layout(true, true);
		relayout();
	}

	private Hyperlink link(Composite parent, String text, RGB color, Runnable action) {
		Hyperlink link = new Hyperlink(parent, SWT.NONE);
		link.setText(text);
		link.setUnderlined(false);
		link.setForeground(HlomTheme.color(color));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkEntered(HyperlinkEvent event) {
				link.setForeground(HlomTheme.color(HlomTheme.ACCENT));
				link.setUnderlined(true);
			}

			@Override
			public void linkExited(HyperlinkEvent event) {
				link.setForeground(HlomTheme.color(color));
				link.setUnderlined(false);
			}

			@Override
			public void linkActivated(HyperlinkEvent event) {
				action.run();
			}
		});
		return link;
	}

	private void createAbout(Composite panel) {
		Label title = new Label(panel, SWT.NONE);
		title.setText(Messages.StartView_TheMedBiquitousStandards);
		title.setFont(font("panel", 1, SWT.BOLD));
		title.setForeground(HlomTheme.color(HlomTheme.INK));
		paragraph(panel, Messages.StartView_XMLStandardsForHealthProfessions,
				HlomTheme.SLATE, 4);
		bullet(panel, HlomTheme.ACCENT, Messages.StartView_HealthcareLOM,
				Messages.StartView_LearningObjectMetadataCoursesAnd);
		bullet(panel, HlomTheme.TEAL, Messages.StartView_CurriculumInventory,
				Messages.StartView_AProgramSEventsExpectations);
		bullet(panel, HlomTheme.TEAL, Messages.StartView_Competencies,
				Messages.StartView_CompetencyFrameworksAndObjectsPerformanc);
		bullet(panel, HlomTheme.TEAL, Messages.StartView_ActivityReportAndMEMS,
				Messages.StartView_ParticipationAndCreditInActivities);
		bullet(panel, HlomTheme.TEAL, Messages.StartView_ProfessionalProfileAndVirtualPatient,
				Messages.StartView_HealthcareProfessionalsProfilesVirtualPa);
		bullet(panel, HlomTheme.AMBER, Messages.StartView_Validation,
				Messages.StartView_AgainstThePublishedSchemasAnd);
	}

	private void paragraph(Composite parent, String text, RGB color, int indent) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(text);
		label.setForeground(HlomTheme.color(color));
		GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).indent(0, indent).applyTo(label);
	}

	private void bullet(Composite parent, RGB color, String title, String text) {
		Composite row = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(10, 0).applyTo(row);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, 6).applyTo(row);
		Canvas dot = new Canvas(row, SWT.NONE);
		dot.addPaintListener(event -> {
			event.gc.setAntialias(SWT.ON);
			event.gc.setBackground(HlomTheme.color(color));
			event.gc.fillOval(0, 0, 8, 8);
		});
		GridDataFactory.fillDefaults().hint(8, 8).align(SWT.CENTER, SWT.BEGINNING).indent(0, 5).applyTo(dot);
		Composite texts = new Composite(row, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 1).applyTo(texts);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(texts);
		Label heading = new Label(texts, SWT.NONE);
		heading.setText(title);
		heading.setFont(HlomTheme.boldFont());
		heading.setForeground(HlomTheme.color(HlomTheme.INK));
		paragraph(texts, text, HlomTheme.SLATE, 0);
	}

	// --- Layout -----------------------------------------------------------------------------------------

	private void relayout() {
		if (scroller == null || scroller.isDisposed()) {
			return;
		}
		int width = scroller.getClientArea().width;
		if (width <= 0) {
			return;
		}
		boolean narrow = width < NARROW;
		((GridLayout) body.getLayout()).numColumns = narrow ? 1 : 2;
		GridData sideData = (GridData) side.getLayoutData();
		sideData.widthHint = narrow ? SWT.DEFAULT : SIDE_WIDTH;
		sideData.grabExcessHorizontalSpace = narrow;
		int mainWidth = width - 2 * MARGIN - (narrow ? 0 : SIDE_WIDTH + 36);
		((GridLayout) actions.getLayout()).numColumns = mainWidth >= 720 ? 4 : mainWidth >= 360 ? 2 : 1;
		((GridLayout) templates.getLayout()).numColumns = Math.clamp((mainWidth + 16) / 300, 1, 3);
		Point size = content.computeSize(width, SWT.DEFAULT);
		scroller.setMinSize(0, size.y);
		content.layout(true, true);
	}

	private static Font font(String key, int grow, int style) {
		FontRegistry registry = JFaceResources.getFontRegistry();
		String id = FONT_PREFIX + key;
		if (!registry.hasValueFor(id)) {
			FontDescriptor base = JFaceResources.getDefaultFontDescriptor();
			int height = base.getFontData()[0].getHeight();
			registry.put(id, base.setHeight(height + grow).setStyle(style).getFontData());
		}
		return registry.get(id);
	}

	@Override
	public void setFocus() {
		if (firstCard != null && !firstCard.isDisposed()) {
			firstCard.setFocus();
		} else if (scroller != null && !scroller.isDisposed()) {
			scroller.setFocus();
		}
	}

	@Override
	public void dispose() {
		RecentDocuments.removeListener(recentListener);
		super.dispose();
	}
}
