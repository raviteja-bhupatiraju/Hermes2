/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.views;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.nebula.widgets.ganttchart.AbstractSettings;
import org.eclipse.nebula.widgets.ganttchart.GanttChart;
import org.eclipse.nebula.widgets.ganttchart.GanttEvent;
import org.eclipse.nebula.widgets.ganttchart.GanttEventListenerAdapter;
import org.eclipse.nebula.widgets.ganttchart.GanttSection;
import org.eclipse.nebula.widgets.ganttchart.ISettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.Timeline;
import edu.louisiana.caai.medbiq.model.Timeline.Bar;
import edu.louisiana.caai.medbiq.model.Timeline.Lane;

/**
 * Window &gt; Curriculum Timeline: the sequence blocks of the active Curriculum Inventory on a calendar (Eclipse
 * Nebula Gantt chart), one section per academic level, optionally with the dated events of each block.
 * Double-clicking a bar shows the block or event in the editor. The chart is read-only: dates are edited in the
 * forms.
 */
public final class CurriculumTimelineView extends DocumentViewPart {

	/** The id of the view. */
	public static final String ID = "edu.louisiana.caai.lom.editor.views.CurriculumTimelineView";

	private Composite holder;
	private GanttChart chart;
	private boolean showEvents = true;
	private Timeline timeline;

	@Override
	protected Control createContent(Composite parent) {
		holder = new Composite(parent, SWT.NONE);
		holder.setLayout(new org.eclipse.swt.layout.FillLayout());
		Action events = new Action(Messages.CurriculumTimelineView_ShowEvents, IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				showEvents = isChecked();
				refresh();
			}
		};
		events.setChecked(true);
		getViewSite().getActionBars().getMenuManager().add(events);
		return holder;
	}

	@Override
	protected String show(HlomMetadataEditor editor) {
		if (!(editor.documentType() instanceof MedbiqDocumentType type)) {
			return Messages.CurriculumTimelineView_TheTimelineShowsTheSequence;
		}
		timeline = Timeline.of(type, editor.documentRoot());
		if (timeline.isEmpty()) {
			return timeline.lanes().isEmpty() && timeline.undated() == 0
					? "The timeline shows the sequence blocks of a Curriculum Inventory by their dates."
					: "None of the sequence blocks has dates (Timing > Dates).";
		}
		if (chart != null) {
			chart.dispose();
		}
		chart = new GanttChart(holder, SWT.NONE, new Settings());
		for (Lane lane : timeline.lanes()) {
			if (lane.bars().isEmpty()) {
				continue;
			}
			GanttSection section = new GanttSection(chart, lane.label());
			for (Bar bar : lane.bars()) {
				GanttEvent block = event(bar, HlomTheme.TEAL);
				section.addGanttEvent(block);
				if (showEvents) {
					for (Bar child : bar.children()) {
						GanttEvent event = event(child, HlomTheme.SLATE);
						section.addGanttEvent(event);
						chart.addConnection(block, event);
					}
				}
			}
		}
		chart.addGanttEventListener(new GanttEventListenerAdapter() {
			@Override
			public void eventDoubleClicked(GanttEvent event, MouseEvent mouse) {
				if (event.getData() instanceof EObject element && editor() != null) {
					getSite().getPage().activate(editor());
					editor().reveal(element);
				}
			}
		});
		holder.layout(true);
		chart.getGanttComposite().jumpToEarliestEvent();
		return null;
	}

	private GanttEvent event(Bar bar, org.eclipse.swt.graphics.RGB color) {
		GanttEvent event = new GanttEvent(chart, bar.element(), bar.label(), calendar(bar.start()), calendar(bar.end()),
				0);
		event.setStatusColor(HlomTheme.color(color));
		event.setGradientStatusColor(HlomTheme.color(color));
		event.setMoveable(false);
		event.setResizable(false);
		return event;
	}

	private static Calendar calendar(LocalDate date) {
		return GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
	}

	@Override
	protected String emptyMessage() {
		return Messages.CurriculumTimelineView_OpenACurriculumInventoryTo;
	}

	/** The timeline shown, for tests. */
	public Timeline timeline() {
		return timeline;
	}

	@Override
	public void setFocus() {
		if (chart != null && !chart.isDisposed()) {
			chart.setFocus();
		}
	}

	/** Read-only, in months, without the editing menus of the chart. */
	private static final class Settings extends AbstractSettings {
		@Override
		public boolean enableResizing() {
			return false;
		}

		@Override
		public boolean enableDragAndDrop() {
			return false;
		}

		@Override
		public boolean showMenuItemsOnRightClick() {
			return false;
		}

		@Override
		public boolean showDefaultMenuItemsOnEventRightClick() {
			return false;
		}

		@Override
		public boolean allowBlankAreaDragAndDropToMoveDates() {
			return false;
		}

		@Override
		public boolean showBarsIn3D() {
			return false;
		}

		@Override
		public int getInitialView() {
			return ISettings.VIEW_MONTH;
		}

		@Override
		public String getDateFormat() {
			return "yyyy-MM-dd";
		}
	}
}
