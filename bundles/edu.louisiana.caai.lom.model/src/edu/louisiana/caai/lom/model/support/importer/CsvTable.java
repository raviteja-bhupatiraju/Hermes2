/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A parsed CSV file: the header row and the data rows (blank rows removed).
 *
 * @param headers   the column names (trimmed; unnamed columns are called "Column n")
 * @param rows      the data rows; a row may be shorter or longer than the header
 * @param delimiter the field delimiter that was used
 * @param charset   the character set the file was decoded with, or {@code null} if parsed from text
 */
public record CsvTable(List<String> headers, List<List<String>> rows, char delimiter, Charset charset) {

	/** Creates a table with immutable copies of the lists. */
	public CsvTable {
		headers = List.copyOf(headers);
		List<List<String>> copy = new ArrayList<>(rows.size());
		for (List<String> row : rows) {
			copy.add(List.copyOf(row));
		}
		rows = List.copyOf(copy);
	}

	/** The number of columns (the header length). */
	public int columnCount() {
		return headers.size();
	}

	/** The number of data rows. */
	public int rowCount() {
		return rows.size();
	}

	/** The trimmed value of a cell; empty if the row has no such cell. */
	public String cell(int row, int column) {
		List<String> cells = rows.get(row);
		return column < cells.size() && cells.get(column) != null ? cells.get(column).strip() : "";
	}

	/** Up to {@code max} distinct, non-blank values of a column, in row order. */
	public List<String> sampleValues(int column, int max) {
		List<String> values = new ArrayList<>();
		for (int row = 0; row < rows.size() && values.size() < max; row++) {
			String value = cell(row, column);
			if (!value.isEmpty() && !values.contains(value)) {
				values.add(value);
			}
		}
		return values;
	}

	/** A human readable name of the delimiter, e.g. "Comma". */
	public static String delimiterName(char delimiter) {
		return switch (delimiter) {
			case ',' -> "Comma";
			case ';' -> "Semicolon";
			case '\t' -> "Tab";
			case '|' -> "Vertical bar";
			default -> "'" + delimiter + "'";
		};
	}
}
