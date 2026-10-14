/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.medbiq.model.standards.CurriculumInventory;

/**
 * The calendar of a Curriculum Inventory: its sequence blocks with dates, by academic level, with the events that
 * have dates of their own in each block. Shown by the editor's timeline view.
 *
 * @param lanes  the academic levels with the blocks that start in them, in level order (blocks without a level
 *               last)
 * @param undated the number of sequence blocks without dates (not on the timeline)
 */
public record Timeline(List<Lane> lanes, int undated) {

	/**
	 * An academic level (or "No academic level") with its blocks.
	 *
	 * @param label the level
	 * @param bars  the blocks, by start date
	 */
	public record Lane(String label, List<Bar> bars) {
	}

	/**
	 * An element with dates.
	 *
	 * @param label    its title (or identifier)
	 * @param start    the first day
	 * @param end      the last day
	 * @param element  the element (a sequence block, or the SequenceBlockEvent of an event)
	 * @param children the events of a block that have dates of their own
	 */
	public record Bar(String label, LocalDate start, LocalDate end, EObject element, List<Bar> children) {
	}

	public Timeline {
		lanes = List.copyOf(lanes);
	}

	/** Whether there is nothing to show. */
	public boolean isEmpty() {
		return lanes.stream().allMatch(lane -> lane.bars().isEmpty());
	}

	/** The timeline of a document; empty for documents other than Curriculum Inventories. */
	public static Timeline of(MedbiqDocumentType type, EObject documentRoot) {
		EObject root = documentRoot == null ? null : Paths.rootElement(documentRoot);
		if (type == null || root == null
				|| !(type.id().equals(CurriculumInventory.V1) || type.id().equals(CurriculumInventory.V10))) {
			return new Timeline(List.of(), 0);
		}
		boolean v1 = type.id().equals(CurriculumInventory.V1);
		References.Kind levels = type.references().stream().filter(kind -> kind.targets().equals("AcademicLevels/Level"))
				.findFirst().orElse(null);
		Map<String, String> eventTitles = new LinkedHashMap<>();
		for (EObject event : Paths.objects(root, "Events/Event")) {
			String id = Paths.text(event, "@id");
			if (id != null) {
				eventTitles.put(id, Paths.text(event, "Title"));
			}
		}
		References.Kind eventKind = References.kind(type.references(), null, "EventReference");
		Map<String, List<Bar>> byLevel = new LinkedHashMap<>();
		for (EObject level : Paths.objects(root, "AcademicLevels/Level")) {
			byLevel.put(levelLabel(level), new ArrayList<>());
		}
		int undated = 0;
		for (EObject block : Paths.objects(root, "Sequence/SequenceBlock")) {
			EObject dates = Paths.objects(block, "Timing/Dates").stream().findFirst().orElse(null);
			LocalDate start = dates == null ? null : Paths.date(Paths.text(dates, "StartDate"));
			LocalDate end = dates == null ? null : Paths.date(Paths.text(dates, "EndDate"));
			if (start == null || end == null) {
				undated++;
				continue;
			}
			List<Bar> events = new ArrayList<>();
			for (EObject blockEvent : Paths.objects(block, "SequenceBlockEvent")) {
				LocalDate eventStart = Paths.date(Paths.text(blockEvent, "StartDate"));
				LocalDate eventEnd = Paths.date(Paths.text(blockEvent, "EndDate"));
				if (eventStart != null) {
					String key = eventKind == null ? null : eventKind.key(Paths.text(blockEvent, "EventReference"));
					String title = key == null ? null : eventTitles.get(key);
					events.add(new Bar(title != null ? title : key != null ? key : "Event", eventStart,
							eventEnd != null ? eventEnd : eventStart, blockEvent, List.of()));
				}
			}
			events.sort(Comparator.comparing(Bar::start));
			String title = Paths.text(block, "Title");
			Bar bar = new Bar(title != null ? title : String.valueOf(Paths.text(block, "@id")), start, end, block,
					events);
			String reference = v1 ? Paths.text(block, "Level")
					: Paths.text(block, "SequenceBlockLevels/StartingAcademicLevel");
			References.Target target = levels == null || reference == null ? null
					: References.resolve(root, levels, reference);
			String lane = target != null ? levelLabel(target.element()) : "No academic level";
			byLevel.computeIfAbsent(lane, _ -> new ArrayList<>()).add(bar);
		}
		List<Lane> lanes = new ArrayList<>();
		byLevel.forEach((label, bars) -> {
			bars.sort(Comparator.comparing(Bar::start));
			lanes.add(new Lane(label, bars));
		});
		return new Timeline(lanes, undated);
	}

	private static String levelLabel(EObject level) {
		String label = Paths.text(level, "Label");
		String number = Paths.text(level, "@number");
		return label != null ? label : "Level " + number;
	}
}
