/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.forms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Field;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Repeat;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.SectionSpec;

/**
 * The lists of a document type that can be shown as tables: the repeated elements of its form pages (such as the
 * events, sequence blocks and competency objects of a Curriculum Inventory), with the simple fields of their items as
 * columns.
 */
public final class ListSpecs {

	/**
	 * A column.
	 *
	 * @param path  the path of the value, relative to an item
	 * @param label the column title
	 */
	public record Column(String path, String label) {
	}

	/**
	 * A list.
	 *
	 * @param label   the name of the list (such as {@code Events})
	 * @param path    the path of the items from the root element
	 * @param columns the columns
	 */
	public record ListSpec(String label, String path, List<Column> columns) {

		public ListSpec {
			columns = List.copyOf(columns);
		}
	}

	private ListSpecs() {
	}

	/** The lists of a document type, in the order of its pages (each path once). */
	public static List<ListSpec> of(MedbiqDocumentType type) {
		Map<String, ListSpec> lists = new LinkedHashMap<>();
		for (PageSpec page : type.content().pages(type)) {
			for (SectionSpec section : page.sections()) {
				for (FieldSpec field : section.fields()) {
					if (field instanceof Repeat repeat) {
						String path = section.path().isEmpty() ? repeat.path() : section.path() + "/" + repeat.path();
						List<Column> columns = new ArrayList<>();
						if (repeat.itemLabel() != null && !repeat.itemLabel().contains("|")) {
							columns.add(new Column(repeat.itemLabel(), label(repeat.itemLabel(), null)));
						}
						for (FieldSpec detail : repeat.details()) {
							if (detail instanceof Field simple && !simple.path().equals(PageSpec.CONTENT)
									&& columns.stream().noneMatch(column -> column.path().equals(simple.path()))) {
								columns.add(new Column(simple.path(), label(simple.path(), simple.label())));
							}
						}
						if (!columns.isEmpty()) {
							lists.putIfAbsent(path, new ListSpec(repeat.label() != null ? repeat.label()
									: FieldLabels.words(last(repeat.path())), path, columns));
						}
					}
				}
			}
		}
		return List.copyOf(lists.values());
	}

	private static String label(String path, String label) {
		if (label != null) {
			// "Age (e.g. P58Y)" is the column "Age".
			int paren = label.indexOf(" (");
			return paren > 0 ? label.substring(0, paren) : label;
		}
		String name = last(path);
		return FieldLabels.words(name.startsWith("@") ? name.substring(1) : name);
	}

	private static String last(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}
}
