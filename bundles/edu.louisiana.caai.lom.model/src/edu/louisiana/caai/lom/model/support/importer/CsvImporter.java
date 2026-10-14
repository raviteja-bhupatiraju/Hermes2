/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Identifier;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomTemplates.Template;
import edu.louisiana.caai.lom.model.support.HlomValidator;

/**
 * Converts the rows of a CSV table into Healthcare LOM records: each row starts from a {@link Template}, the
 * mapped cells are applied by their {@link ImportField}, and the result is validated with {@link HlomValidator}.
 * Bad cells never abort an import; they are reported per row as {@link ImportMessage}s.
 *
 * <pre>
 * CsvTable table = CsvReader.read(path);
 * CsvImporter importer = new CsvImporter(table, ColumnMapping.auto(table.headers()), HlomTemplates.CME_COURSE);
 * List&lt;ImportedRecord&gt; records = importer.preview();
 * importer.writeAll(folder, "{title}");
 * </pre>
 */
public final class CsvImporter {

	/** The default file name pattern. */
	public static final String DEFAULT_PATTERN = "{title}";

	/** The placeholders understood in file name patterns. */
	public static final List<String> PATTERN_TOKENS = List.of("{title}", "{row}", "{n}", "{id}", "{kind}",
			"{template}", "{date}", "{column:Header}");

	private static final Pattern TOKEN = Pattern.compile("\\{([a-zA-Z]+)(?::([^}]*))?}");
	private static final int MAX_SLUG = 60;

	/** Progress callback of long-running operations. */
	public interface Progress {

		/** Reports that {@code done} of {@code total} items are finished; {@code label} names the next one. */
		void worked(int done, int total, String label);

		/** Whether the operation should stop. */
		default boolean isCanceled() {
			return false;
		}
	}

	/**
	 * The outcome of writing records.
	 *
	 * @param written  the files written
	 * @param failures messages for records that could not be written
	 * @param canceled whether writing was canceled
	 */
	public record WriteResult(List<Path> written, List<String> failures, boolean canceled) {
	}

	private final CsvTable table;
	private final ColumnMapping mapping;
	private final Template template;
	private String defaultLanguage = HlomDocuments.DEFAULT_LANGUAGE;
	private boolean removePlaceholders = true;
	private boolean identifierFromLocation = true;
	private boolean validate = true;
	private List<ImportedRecord> records;

	/**
	 * Creates an importer.
	 *
	 * @param template the template every record starts from ({@code null}: the blank record)
	 */
	public CsvImporter(CsvTable table, ColumnMapping mapping, Template template) {
		this.table = table;
		this.mapping = mapping;
		this.template = template == null ? HlomTemplates.BLANK : template;
	}

	/** Previews a table with an automatic mapping. */
	public static List<ImportedRecord> preview(CsvTable table, ColumnMapping mapping, Template template) {
		return new CsvImporter(table, mapping, template).preview();
	}

	/** Sets the language of records without a language column (default {@code en-US}). */
	public CsvImporter defaultLanguage(String language) {
		this.defaultLanguage = language == null || language.isBlank() ? HlomDocuments.DEFAULT_LANGUAGE : language;
		return this;
	}

	/** Whether to remove the template's placeholder texts ("Author Name", ...) not replaced by data (default yes). */
	public CsvImporter removePlaceholders(boolean remove) {
		this.removePlaceholders = remove;
		return this;
	}

	/** Whether to use the location URL as identifier of records without one (default yes). */
	public CsvImporter identifierFromLocation(boolean derive) {
		this.identifierFromLocation = derive;
		return this;
	}

	/** Whether to validate the generated records (default yes). */
	public CsvImporter validate(boolean enabled) {
		this.validate = enabled;
		return this;
	}

	/** The table being imported. */
	public CsvTable table() {
		return table;
	}

	/** The column mapping. */
	public ColumnMapping mapping() {
		return mapping;
	}

	/** The template records start from. */
	public Template template() {
		return template;
	}

	/** Converts and validates all rows. */
	public List<ImportedRecord> preview() {
		return preview(null);
	}

	/** Converts and validates all rows, reporting progress; stops early if canceled. */
	public List<ImportedRecord> preview(Progress progress) {
		List<ImportedRecord> result = new ArrayList<>();
		for (int row = 0; row < table.rowCount(); row++) {
			if (progress != null) {
				if (progress.isCanceled()) {
					break;
				}
				progress.worked(row, table.rowCount(), "Row " + (row + 1));
			}
			result.add(importRow(row));
		}
		if (progress != null) {
			progress.worked(table.rowCount(), table.rowCount(), "");
		}
		records = result;
		return result;
	}

	/** The records of the last {@link #preview()}, or {@code null}. */
	public List<ImportedRecord> records() {
		return records;
	}

	/** Converts one row (0-based) into a record. */
	public ImportedRecord importRow(int row) {
		List<ImportMessage> messages = new ArrayList<>();
		String title = firstValue(row, ImportField.TITLE);
		String language = defaultLanguage;
		String languageValue = firstValue(row, ImportField.LANGUAGE);
		if (!languageValue.isEmpty()) {
			String tag = ValueParsers.language(languageValue);
			if (tag != null) {
				language = tag;
			}
		}
		if (title.isEmpty()) {
			messages.add(new ImportMessage(Severity.WARNING, null, ImportField.TITLE,
					mapping.isMapped(ImportField.TITLE) ? "The row has no title." : "No column is mapped to the title."));
		}
		DocumentRoot root = template.create(title, language);
		RecordBuilder builder = new RecordBuilder(root, language, messages);
		boolean titleSeen = false;
		for (ImportField field : ImportField.values()) {
			for (int column : mapping.columns(field)) {
				String value = table.cell(row, column);
				if (value.isEmpty()) {
					continue;
				}
				if (field == ImportField.TITLE && !titleSeen) {
					titleSeen = true;
					continue;
				}
				builder.at(table.headers().get(column), field);
				try {
					field.apply(builder, value);
				} catch (RuntimeException e) {
					builder.error("\"" + value + "\" could not be imported: " + e.getMessage());
				}
			}
		}
		builder.finish(removePlaceholders, identifierFromLocation);
		List<HlomProblem> problems = new ArrayList<>();
		if (validate) {
			Resource resource = HlomResources.createResourceSet()
					.createResource(URI.createURI("memory:/row-" + (row + 1) + ".xml"));
			resource.getContents().add(root);
			try {
				problems.addAll(HlomValidator.validate(resource));
			} catch (RuntimeException e) {
				problems.add(new HlomProblem(Severity.ERROR, HlomProblem.Origin.MODEL,
						"The record could not be validated: " + e.getMessage(), null, null, -1, -1));
			}
			resource.getContents().clear();
		}
		return new ImportedRecord(row + 1, root, messages, problems);
	}

	private String firstValue(int row, ImportField field) {
		for (int column : mapping.columns(field)) {
			String value = table.cell(row, column);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return "";
	}

	/** Writes the included records of the last preview (previewing first if needed), never overwriting files. */
	public WriteResult writeAll(Path folder, String fileNamePattern) throws IOException {
		if (records == null) {
			preview();
		}
		return write(records, folder, fileNamePattern, false, null);
	}

	/**
	 * Assigns file names to the included records: the pattern with its {@link #PATTERN_TOKENS tokens} replaced
	 * by slugs, {@code .xml} appended, de-duplicated within the batch and (unless {@code overwrite}) against the
	 * files in the folder by appending {@code -2}, {@code -3}, ...
	 *
	 * @param folder the target folder, or {@code null} to ignore existing files
	 * @return the names in record order (only included records)
	 */
	public List<String> planFileNames(List<ImportedRecord> list, Path folder, String fileNamePattern,
			boolean overwrite) {
		Set<String> taken = new HashSet<>();
		List<String> names = new ArrayList<>();
		int n = 0;
		for (ImportedRecord record : list) {
			if (!record.isIncluded()) {
				continue;
			}
			n++;
			String base = fileName(record, n, fileNamePattern);
			String stem = base.substring(0, base.lastIndexOf('.'));
			String extension = base.substring(base.lastIndexOf('.'));
			String name = base;
			for (int i = 2; taken.contains(name.toLowerCase(Locale.ROOT))
					|| (!overwrite && folder != null && Files.exists(folder.resolve(name))); i++) {
				name = stem + "-" + i + extension;
			}
			taken.add(name.toLowerCase(Locale.ROOT));
			record.setFileName(name);
			names.add(name);
		}
		return names;
	}

	/** The file name of a record for a pattern, before de-duplication. */
	public String fileName(ImportedRecord record, int n, String fileNamePattern) {
		String pattern = fileNamePattern == null || fileNamePattern.isBlank() ? DEFAULT_PATTERN : fileNamePattern.strip();
		Matcher matcher = TOKEN.matcher(pattern);
		StringBuilder name = new StringBuilder();
		int last = 0;
		while (matcher.find()) {
			name.append(literal(pattern.substring(last, matcher.start())));
			name.append(slug(token(record, n, matcher.group(1), matcher.group(2))));
			last = matcher.end();
		}
		name.append(literal(pattern.substring(last)));
		String result = name.toString().replaceAll("-{2,}", "-").replaceAll("^[-.]+|[-.]+$", "");
		String lower = result.toLowerCase(Locale.ROOT);
		if (lower.endsWith(".xml") || lower.endsWith(".lom")) {
			result = result.substring(0, result.length() - 4).replaceAll("[-.]+$", "") + result.substring(result.length() - 4);
		} else {
			result = result + ".xml";
		}
		if (result.length() <= 4) {
			result = "record-" + record.row() + ".xml";
		}
		return result;
	}

	private String token(ImportedRecord record, int n, String name, String argument) {
		return switch (name.toLowerCase(Locale.ROOT)) {
			case "title" -> record.title().isEmpty() ? "record-" + record.row() : record.title();
			case "row" -> String.valueOf(record.row());
			case "n" -> "%03d".formatted(n);
			case "id" -> identifier(record);
			case "kind" -> record.kind();
			case "template" -> template.id();
			case "date" -> LocalDate.now().toString();
			case "column", "col" -> columnValue(record, argument);
			default -> "";
		};
	}

	private String columnValue(ImportedRecord record, String header) {
		if (header == null) {
			return "";
		}
		for (int column = 0; column < table.columnCount(); column++) {
			if (table.headers().get(column).equalsIgnoreCase(header.strip())) {
				return table.cell(record.row() - 1, column);
			}
		}
		return "";
	}

	private static String identifier(ImportedRecord record) {
		var lom = record.root().getLom();
		if (lom.getGeneral().isEmpty()) {
			return "";
		}
		for (Identifier identifier : lom.getGeneral().getFirst().getIdentifier()) {
			if (!identifier.getEntry().isEmpty() && identifier.getEntry().getFirst().getValue() != null) {
				String entry = identifier.getEntry().getFirst().getValue();
				return ValueParsers.looksLikeUrl(entry) ? entry.replaceAll("^.*/(?=[^/]+/?$)", "") : entry;
			}
		}
		return "";
	}

	/** Lower-case words joined by dashes, at most 60 characters. */
	public static String slug(String text) {
		String slug = text == null ? "" : java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
				.replaceAll("\\p{M}+", "").toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "-")
				.replaceAll("(^-+|-+$)", "");
		if (slug.length() > MAX_SLUG) {
			slug = slug.substring(0, MAX_SLUG);
			int dash = slug.lastIndexOf('-');
			if (dash > MAX_SLUG / 2) {
				slug = slug.substring(0, dash);
			}
		}
		return slug;
	}

	private static String literal(String text) {
		return text.replaceAll("[\\\\/:*?\"<>|\\s]+", "-");
	}

	/**
	 * Writes the included records into a folder (created if needed).
	 *
	 * @param overwrite whether existing files may be replaced; otherwise new names are chosen
	 * @param progress  optional progress callback
	 */
	public WriteResult write(List<ImportedRecord> list, Path folder, String fileNamePattern, boolean overwrite,
			Progress progress) throws IOException {
		Files.createDirectories(folder);
		planFileNames(list, folder, fileNamePattern, overwrite);
		List<ImportedRecord> included = list.stream().filter(ImportedRecord::isIncluded).toList();
		List<Path> written = new ArrayList<>();
		List<String> failures = new ArrayList<>();
		int done = 0;
		for (ImportedRecord record : included) {
			if (progress != null) {
				if (progress.isCanceled()) {
					return new WriteResult(written, failures, true);
				}
				progress.worked(done, included.size(), record.fileName());
			}
			Path file = folder.resolve(record.fileName());
			Resource resource = HlomResources.createResourceSet()
					.createResource(URI.createFileURI(file.toAbsolutePath().toString()));
			resource.getContents().add(record.root());
			try {
				resource.save(HlomResources.saveOptions());
				written.add(file);
			} catch (IOException | RuntimeException e) {
				failures.add(record.fileName() + ": " + e.getMessage());
			} finally {
				resource.getContents().clear();
			}
			done++;
		}
		if (progress != null) {
			progress.worked(done, included.size(), "");
		}
		return new WriteResult(written, failures, false);
	}
}
