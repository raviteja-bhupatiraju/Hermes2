/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.forms;

import java.util.List;

/**
 * A form page of a document type, described by element paths: the editor renders the fields from the model's
 * metadata (text, dates, numbers, enumerations, lists of values, master/detail blocks for repeated elements).
 * Paths are local names separated by {@code /}, relative to the root element (pages, sections) or to the element of
 * the enclosing section or list (fields); attributes are written {@code @name}.
 *
 * @param id          the page id (unique within the type; used for icons, help contexts and navigation)
 * @param title       the title in the navigation
 * @param category    the navigation group, such as {@code Description}
 * @param description a one-line description (navigation tooltip), or {@code null}
 * @param icon        the page icon: an icon name of the editor ({@code icons/pages/<icon>.png})
 * @param sections    the sections
 */
public record PageSpec(String id, String title, String category, String description, String icon,
		List<SectionSpec> sections) {

	public PageSpec {
		sections = List.copyOf(sections);
	}

	/**
	 * A titled section.
	 *
	 * @param title       the title
	 * @param description a short explanation, or {@code null}
	 * @param path        the element the fields are relative to (relative to the root element; empty for the root)
	 * @param fields      the fields
	 */
	public record SectionSpec(String title, String description, String path, List<FieldSpec> fields) {

		public SectionSpec {
			fields = List.copyOf(fields);
		}
	}

	/** A field of a section or of a list's details. */
	public sealed interface FieldSpec permits Field, Repeat, Group, Auto {

		/** The path of the element or attribute, relative to the enclosing element. */
		String path();
	}

	/** The path of a field holding the text of the enclosing element itself (an element with simple content). */
	public static final String CONTENT = ".";

	/** How a simple value is edited. */
	public enum Widget {
		/** From the type: enumerations as choices, dates as date pickers, other values as text. */
		AUTO,
		/** A multi-line text. */
		MULTILINE
	}

	/**
	 * A simple value: an attribute, an element with simple content, or a list of them (if the element repeats).
	 *
	 * @param label       the label, or {@code null} for the element name in words
	 * @param widget      how the value is edited
	 * @param suggestions values offered while typing (for open vocabularies)
	 */
	public record Field(String path, String label, Widget widget, List<String> suggestions) implements FieldSpec {

		public Field {
			suggestions = List.copyOf(suggestions);
		}
	}

	/**
	 * A repeated element edited in a master/detail block.
	 *
	 * @param label     the label of the block, or {@code null} for the element name in words
	 * @param itemLabel the path (relative to an item) of the text naming an item in the list (alternatives
	 *                  separated by {@code |}: the first with a text), or {@code null}
	 * @param details   the fields of the selected item; empty for all of its simple content
	 */
	public record Repeat(String path, String label, String itemLabel, List<FieldSpec> details) implements FieldSpec {

		public Repeat {
			details = List.copyOf(details);
		}
	}

	/**
	 * A nested element shown as a group of fields.
	 *
	 * @param label  the label of the group, or {@code null} for the element name in words
	 * @param fields the fields; empty for all of its content
	 */
	public record Group(String path, String label, List<FieldSpec> fields) implements FieldSpec {

		public Group {
			fields = List.copyOf(fields);
		}
	}

	/** All content of an element (its attributes and child elements, recursively), as the model defines it. */
	public record Auto(String path) implements FieldSpec {
	}

	// ----------------------------------------------------------------------------------------------------
	// Shorthands for the definitions
	// ----------------------------------------------------------------------------------------------------

	public static PageSpec page(String id, String title, String category, String description, String icon,
			SectionSpec... sections) {
		return new PageSpec(id, title, category, description, icon, List.of(sections));
	}

	public static SectionSpec section(String title, String description, String path, FieldSpec... fields) {
		return new SectionSpec(title, description, path, List.of(fields));
	}

	public static Field field(String path) {
		return new Field(path, null, Widget.AUTO, List.of());
	}

	public static Field field(String path, String label) {
		return new Field(path, label, Widget.AUTO, List.of());
	}

	public static Field text(String path, String label) {
		return new Field(path, label, Widget.MULTILINE, List.of());
	}

	public static Field suggest(String path, String label, String... suggestions) {
		return new Field(path, label, Widget.AUTO, List.of(suggestions));
	}

	public static Repeat repeat(String path, String label, String itemLabel, FieldSpec... details) {
		return new Repeat(path, label, itemLabel, List.of(details));
	}

	public static Group group(String path, String label, FieldSpec... fields) {
		return new Group(path, label, List.of(fields));
	}

	public static Auto auto(String path) {
		return new Auto(path);
	}
}
