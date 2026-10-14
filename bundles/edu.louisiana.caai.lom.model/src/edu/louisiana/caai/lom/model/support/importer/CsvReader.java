/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads CSV files as defined by RFC 4180 and as written by spreadsheet programs: quoted fields with embedded
 * delimiters, quotes ({@code ""}) and line breaks, CRLF/LF/CR line ends, a leading byte order mark, and comma,
 * semicolon, tab or vertical bar delimiters (detected automatically). The character set is detected from the
 * byte order mark, else UTF-8 if the bytes are valid UTF-8, else Windows-1252 (Excel "CSV" on Windows).
 */
public final class CsvReader {

	/** The delimiters that are detected automatically, in order of preference. */
	public static final List<Character> DELIMITERS = List.of(',', ';', '\t', '|');

	/** The Windows ANSI character set used by Excel for plain "CSV" files. */
	public static final Charset WINDOWS_1252 = Charset.forName("windows-1252");

	private static final int SNIFF_LENGTH = 64 * 1024;

	private CsvReader() {
	}

	/** Reads a file, detecting the character set and the delimiter. */
	public static CsvTable read(Path file) throws IOException {
		return read(Files.readAllBytes(file), null, null);
	}

	/**
	 * Parses CSV bytes.
	 *
	 * @param charset   the character set, or {@code null} to detect it
	 * @param delimiter the delimiter, or {@code null} to detect it
	 */
	public static CsvTable read(byte[] bytes, Charset charset, Character delimiter) {
		Charset actual = charset != null ? charset : detectCharset(bytes);
		String text = decode(bytes, actual);
		char actualDelimiter = delimiter != null ? delimiter : detectDelimiter(text);
		CsvTable table = parse(text, actualDelimiter);
		return new CsvTable(table.headers(), table.rows(), actualDelimiter, actual);
	}

	/** Decodes bytes, dropping a byte order mark and replacing malformed input. */
	public static String decode(byte[] bytes, Charset charset) {
		String text = new String(bytes, charset);
		return text.startsWith("﻿") ? text.substring(1) : text;
	}

	/** Detects the character set: byte order mark, valid UTF-8, else Windows-1252. */
	public static Charset detectCharset(byte[] bytes) {
		if (bytes.length >= 3 && (bytes[0] & 0xFF) == 0xEF && (bytes[1] & 0xFF) == 0xBB && (bytes[2] & 0xFF) == 0xBF) {
			return StandardCharsets.UTF_8;
		}
		if (bytes.length >= 2 && (bytes[0] & 0xFF) == 0xFF && (bytes[1] & 0xFF) == 0xFE) {
			return StandardCharsets.UTF_16LE;
		}
		if (bytes.length >= 2 && (bytes[0] & 0xFF) == 0xFE && (bytes[1] & 0xFF) == 0xFF) {
			return StandardCharsets.UTF_16BE;
		}
		try {
			StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
					.onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bytes));
			return StandardCharsets.UTF_8;
		} catch (CharacterCodingException _) {
			return WINDOWS_1252;
		}
	}

	/**
	 * Detects the delimiter: the candidate that splits the header into the most columns while giving the most
	 * rows the same number of fields as the header. Defaults to comma.
	 */
	public static char detectDelimiter(String text) {
		String sample = text.length() > SNIFF_LENGTH ? text.substring(0, SNIFF_LENGTH) : text;
		char best = ',';
		long bestScore = -1;
		for (char candidate : DELIMITERS) {
			List<List<String>> records = parseRecords(sample, candidate, 50);
			if (records.isEmpty() || records.getFirst().size() < 2) {
				continue;
			}
			int columns = records.getFirst().size();
			int consistent = 0;
			for (List<String> record : records) {
				if (record.size() == columns) {
					consistent++;
				}
			}
			long score = consistent * 1000L + columns;
			if (score > bestScore) {
				bestScore = score;
				best = candidate;
			}
		}
		return best;
	}

	/** Parses CSV text: the first non-blank record is the header, blank records are skipped. */
	public static CsvTable parse(String text, char delimiter) {
		String content = text.startsWith("﻿") ? text.substring(1) : text;
		List<List<String>> records = parseRecords(content, delimiter, Integer.MAX_VALUE);
		List<String> headers = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>();
		for (List<String> record : records) {
			if (headers.isEmpty()) {
				for (int i = 0; i < record.size(); i++) {
					String header = record.get(i).strip();
					headers.add(header.isEmpty() ? "Column " + (i + 1) : header);
				}
			} else {
				rows.add(record);
			}
		}
		return new CsvTable(headers, rows, delimiter, null);
	}

	/** Splits text into records of fields (RFC 4180); records whose fields are all blank are dropped. */
	public static List<List<String>> parseRecords(String text, char delimiter, int maxRecords) {
		List<List<String>> records = new ArrayList<>();
		List<String> record = new ArrayList<>();
		StringBuilder field = new StringBuilder();
		boolean quoted = false;
		boolean fieldStarted = false;
		int length = text.length();
		for (int i = 0; i < length && records.size() < maxRecords; i++) {
			char c = text.charAt(i);
			if (quoted) {
				if (c == '"') {
					if (i + 1 < length && text.charAt(i + 1) == '"') {
						field.append('"');
						i++;
					} else {
						quoted = false;
					}
				} else {
					field.append(c);
				}
			} else if (c == '"' && (!fieldStarted || field.toString().isBlank())) {
				// A quote opens a quoted field only at its start (leading blanks are tolerated).
				field.setLength(0);
				quoted = true;
				fieldStarted = true;
			} else if (c == delimiter) {
				record.add(field.toString());
				field.setLength(0);
				fieldStarted = false;
			} else if (c == '\r' || c == '\n') {
				if (c == '\r' && i + 1 < length && text.charAt(i + 1) == '\n') {
					i++;
				}
				record.add(field.toString());
				field.setLength(0);
				fieldStarted = false;
				addRecord(records, record);
				record = new ArrayList<>();
			} else {
				field.append(c);
				fieldStarted = true;
			}
		}
		if (records.size() < maxRecords && (fieldStarted || field.length() > 0 || !record.isEmpty())) {
			record.add(field.toString());
			addRecord(records, record);
		}
		return records;
	}

	private static void addRecord(List<List<String>> records, List<String> record) {
		for (String value : record) {
			if (!value.isBlank()) {
				records.add(record);
				return;
			}
		}
	}

	/** Quotes a value for CSV output if it contains the delimiter, a quote or a line break. */
	public static String quote(String value, char delimiter) {
		if (value == null) {
			return "";
		}
		if (value.indexOf(delimiter) >= 0 || value.indexOf('"') >= 0 || value.indexOf('\n') >= 0
				|| value.indexOf('\r') >= 0) {
			return '"' + value.replace("\"", "\"\"") + '"';
		}
		return value;
	}
}
