/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.cli.Arguments.UsageException;
import edu.louisiana.caai.lom.cli.Reports.Checked;
import edu.louisiana.caai.lom.cli.lsp.LanguageServer;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Result;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes;
import edu.louisiana.caai.lom.model.support.fixes.HlomFixes.Planned;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.search.DocumentReplace;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Hit;
import edu.louisiana.caai.lom.model.support.search.DocumentSearch.Query;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.lom.model.support.search.HlomSearchFields;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * The {@code hermes} command line tool: validates, converts, formats, fixes, searches and summarizes MedBiquitous
 * documents, and runs the language server. Runs without OSGi (plain Java with the model and EMF jars).
 */
public final class Hermes {

	/** Exit code: success. */
	public static final int OK = 0;
	/** Exit code: problems found (validate, format --check) or nothing found (search). */
	public static final int FAILED = 1;
	/** Exit code: invalid command line. */
	public static final int USAGE = 2;
	/** Exit code: a file could not be read or written. */
	public static final int IO_ERROR = 3;

	private static final String USAGE_TEXT = """
			Usage: hermes <command> [options] [arguments]

			Commands:
			  validate <file|folder>...   Check documents (XML Schema, constraints, MedBiquitous rules)
			      --format text|json|sarif   Output format (default text)
			      --output <file>            Write the report to a file
			      --min-severity error|warning|info   Report problems from this severity (default info)
			      --fail-on error|warning|never       Exit with 1 for problems from this severity (default error)
			      --quiet                    No summary line (text format)
			  convert <input> [<output>]  Convert between the XML and JSON forms (to standard output without <output>)
			      --to xml|json              Target form (default: the other form, or from the output's extension)
			      --force                    Overwrite an existing output file
			  format <file>...            Rewrite documents in the canonical layout of their form
			      --check                    Only report documents that are not formatted (exit 1)
			  fix <file>...               Apply the automatic quick fixes and save
			      --dry-run                  Only list the fixes
			  search <text> <file|folder>...  Find text in the field values of documents
			      --regex --case-sensitive --whole-word
			      --in <fields>              Limit to field groups: %s
			  replace <text> <with> <file|folder>...  Replace text in the field values of documents (only the
			                              changed values are rewritten; with --regex, $1... insert groups)
			      --regex --case-sensitive --whole-word --in <fields>
			      --dry-run                  Only list the changes
			  migrate <input> [<output>]  Convert a document to the newer version of its standard
			                              (Curriculum Inventory 1.0 to v10, Activity Report 1.0 to 2.0)
			      --force                    Overwrite an existing output file
			  crosswalk <input> [<output>]  Export a Healthcare LOM record as other metadata
			      --to dc|schema.org         Dublin Core (oai_dc XML) or schema.org JSON-LD (default)
			  info <file>...              Summarize documents (title, kind, credits, keywords, problems)
			  lsp                         Run the language server (Language Server Protocol over standard input/output)
			  version                     Show the version
			  help                        Show this help

			Exit codes: 0 success, 1 problems found, 2 invalid command line, 3 file not readable or writable.
			""".formatted(String.join(", ", HlomSearchFields.ALL.stream().map(FieldGroup::id).toList()));

	private final PrintStream out;
	private final PrintStream err;

	public Hermes(PrintStream out, PrintStream err) {
		this.out = out;
		this.err = err;
	}

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "true");
		HlomResources.initStandalone();
		MedbiqStandards.init();
		PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
		PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);
		if (args.length > 0 && args[0].equals("lsp")) {
			System.exit(LanguageServer.run(System.in, System.out, err));
		}
		System.exit(new Hermes(out, err).run(Arrays.asList(args)));
	}

	/** Runs a command line and returns the exit code. */
	public int run(List<String> args) {
		if (args.isEmpty()) {
			err.print(USAGE_TEXT);
			return USAGE;
		}
		String command = args.getFirst();
		List<String> rest = args.subList(1, args.size());
		try {
			return switch (command) {
				case "validate", "check" -> validate(Arguments.parse(rest, Set.of("quiet"),
						Set.of("format", "output", "min-severity", "fail-on")));
				case "convert" -> convert(Arguments.parse(rest, Set.of("force"), Set.of("to")));
				case "format" -> format(Arguments.parse(rest, Set.of("check"), Set.of()));
				case "fix" -> fix(Arguments.parse(rest, Set.of("dry-run"), Set.of()));
				case "search" -> search(Arguments.parse(rest, Set.of("regex", "case-sensitive", "whole-word"),
						Set.of("in")));
				case "replace" -> replace(Arguments.parse(rest, Set.of("regex", "case-sensitive", "whole-word", "dry-run"),
						Set.of("in")));
				case "migrate" -> migrate(Arguments.parse(rest, Set.of("force"), Set.of()));
				case "crosswalk" -> crosswalk(Arguments.parse(rest, Set.of("force"), Set.of("to")));
				case "info" -> info(Arguments.parse(rest, Set.of(), Set.of()));
				case "version", "--version", "-v" -> {
					out.println("hermes " + version());
					yield OK;
				}
				case "help", "--help", "-h" -> {
					out.print(USAGE_TEXT);
					yield OK;
				}
				default -> throw new UsageException("Unknown command \"" + command + "\".");
			};
		} catch (UsageException e) {
			err.println("hermes: " + e.getMessage());
			err.println("Run \"hermes help\" for the commands and options.");
			return USAGE;
		} catch (IOException | UncheckedIOException e) {
			err.println("hermes: " + e.getMessage());
			return IO_ERROR;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// validate
	// ----------------------------------------------------------------------------------------------------

	private int validate(Arguments arguments) throws UsageException, IOException {
		String format = arguments.choice("format", "text", "text", "json", "sarif");
		Severity minimum = severity(arguments.choice("min-severity", "info", "error", "warning", "info"));
		String failOn = arguments.choice("fail-on", "error", "error", "warning", "never");
		List<Path> documents = Inputs.documents(arguments.operands());
		List<Checked> results = new ArrayList<>();
		boolean failed = false;
		for (Path file : documents) {
			Result result = DocumentCheck.check(Inputs.read(file), URI.createFileURI(file.toAbsolutePath().toString()));
			List<Located> shown = result.problems().stream()
					.filter(p -> p.problem().severity().ordinal() <= minimum.ordinal()).toList();
			results.add(new Checked(file, shown));
			failed |= result.problems().stream().anyMatch(p -> switch (failOn) {
				case "error" -> p.problem().severity() == Severity.ERROR;
				case "warning" -> p.problem().severity() != Severity.INFO;
				default -> false;
			});
			result.resource().unload();
		}
		String report = switch (format) {
			case "json" -> Reports.json(results);
			case "sarif" -> Reports.sarif(results, Path.of("").toAbsolutePath());
			default -> Reports.text(results, !arguments.has("quiet"));
		};
		write(arguments.get("output", null), report);
		return failed ? FAILED : OK;
	}

	private static Severity severity(String name) {
		return Severity.valueOf(name.toUpperCase(Locale.ROOT));
	}

	private void write(String output, String text) throws IOException {
		if (output == null) {
			out.print(text);
			out.flush();
		} else {
			Files.writeString(Path.of(output), text, StandardCharsets.UTF_8);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// convert, format, fix
	// ----------------------------------------------------------------------------------------------------

	private int convert(Arguments arguments) throws UsageException, IOException {
		List<String> operands = arguments.operands();
		if (operands.isEmpty() || operands.size() > 2) {
			throw new UsageException("convert takes an input file and an optional output file.");
		}
		Path input = Path.of(operands.getFirst());
		Path output = operands.size() == 2 ? Path.of(operands.get(1)) : null;
		Resource resource = load(input);
		String inputText = Inputs.read(input);
		boolean inputJson = inputText.stripLeading().startsWith("{");
		DocumentType type = DocumentTypes.of(resource);
		String defaultTarget = output != null ? (Inputs.isJsonName(output) ? "json" : "xml") : inputJson ? "xml" : "json";
		String target = arguments.choice("to", defaultTarget, "xml", "json");
		if (target.equals("json") && (type == null || !type.supportsJson())) {
			throw new UsageException((type == null ? "The document" : type.label())
					+ " documents have no JSON form (only Healthcare LOM has one).");
		}
		String text = target.equals("json") ? HlomResources.toJson(resource) : HlomResources.toXml(resource);
		if (output == null) {
			out.print(text);
			out.flush();
			return OK;
		}
		if (Files.exists(output) && !arguments.has("force")) {
			throw new UsageException(output + " exists; use --force to overwrite it.");
		}
		Files.writeString(output, text, StandardCharsets.UTF_8);
		err.println("Wrote " + output + " (" + target.toUpperCase(Locale.ROOT) + ").");
		return OK;
	}

	private int migrate(Arguments arguments) throws UsageException, IOException {
		List<String> operands = arguments.operands();
		if (operands.isEmpty() || operands.size() > 2) {
			throw new UsageException("migrate takes an input file and an optional output file.");
		}
		Path input = Path.of(operands.getFirst());
		Resource resource = load(input);
		DocumentType type = DocumentTypes.of(resource);
		List<edu.louisiana.caai.medbiq.model.Migrations.Migration> migrations = type == null ? List.of()
				: edu.louisiana.caai.medbiq.model.Migrations.of(type.id());
		if (migrations.isEmpty()) {
			throw new UsageException(input + " is " + (type == null ? "not a known document" : "of the newest version of "
					+ type.standard()) + "; there is nothing to convert.");
		}
		edu.louisiana.caai.medbiq.model.Migrations.Result result = edu.louisiana.caai.medbiq.model.Migrations
				.migrate(migrations.getFirst(), resource.getContents().getFirst());
		for (String note : result.notes()) {
			err.println("note: " + note);
		}
		return write(arguments, operands, result.xml(), migrations.getFirst().label());
	}

	private int crosswalk(Arguments arguments) throws UsageException, IOException {
		List<String> operands = arguments.operands();
		if (operands.isEmpty() || operands.size() > 2) {
			throw new UsageException("crosswalk takes an input file and an optional output file.");
		}
		String to = arguments.choice("to", "schema.org", "dc", "schema.org");
		Path input = Path.of(operands.getFirst());
		Resource resource = load(input);
		if (HlomResources.getDocumentRoot(resource) == null) {
			throw new UsageException(input + " is not a Healthcare LOM record; crosswalks are defined for Healthcare LOM.");
		}
		edu.louisiana.caai.lom.model.support.report.HlomSummary summary = edu.louisiana.caai.lom.model.support.report.HlomSummary
				.of(resource);
		String text = to.equals("dc") ? edu.louisiana.caai.lom.model.support.report.Crosswalks.dublinCore(summary)
				: edu.louisiana.caai.lom.model.support.report.Crosswalks.schemaOrg(summary);
		return write(arguments, operands, text, to.equals("dc") ? "Dublin Core" : "schema.org JSON-LD");
	}

	/** Prints a text, or writes it to the second operand (refusing to overwrite without --force). */
	private int write(Arguments arguments, List<String> operands, String text, String what)
			throws UsageException, IOException {
		if (operands.size() < 2) {
			out.print(text);
			out.flush();
			return OK;
		}
		Path output = Path.of(operands.get(1));
		if (Files.exists(output) && !arguments.has("force")) {
			throw new UsageException(output + " exists; use --force to overwrite it.");
		}
		Files.writeString(output, text, StandardCharsets.UTF_8);
		err.println("Wrote " + output + " (" + what + ").");
		return OK;
	}

	private int format(Arguments arguments) throws UsageException, IOException {
		boolean check = arguments.has("check");
		int unformatted = 0;
		for (Path file : Inputs.documents(arguments.operands())) {
			String text = Inputs.read(file);
			Resource resource = load(file);
			String formatted = text.stripLeading().startsWith("{") ? HlomResources.toJson(resource)
					: HlomResources.toXml(resource);
			if (formatted.equals(text)) {
				continue;
			}
			unformatted++;
			if (check) {
				out.println(file + ": not formatted");
			} else {
				Files.writeString(file, formatted, StandardCharsets.UTF_8);
				out.println(file + ": formatted");
			}
		}
		return check && unformatted > 0 ? FAILED : OK;
	}

	private int fix(Arguments arguments) throws UsageException, IOException {
		boolean dryRun = arguments.has("dry-run");
		int remaining = 0;
		for (Path file : Inputs.documents(arguments.operands())) {
			String text = Inputs.read(file);
			Result result = DocumentCheck.check(text, URI.createFileURI(file.toAbsolutePath().toString()));
			DocumentRoot root = result.root();
			if (root == null) {
				err.println(file + (result.documentRoot() != null ? ": no automatic fixes for " + result.type().label()
						: ": not a readable document, skipped"));
				remaining++;
				continue;
			}
			List<HlomProblem> problems = result.problems().stream().map(Located::problem).toList();
			List<Planned> plan = HlomFixes.automaticFixes(problems);
			for (Planned planned : plan) {
				out.println(file + ": " + planned.fix().label() + " (" + planned.problem().message() + ")");
			}
			if (!dryRun && !plan.isEmpty()) {
				HlomFixes.apply(root, plan);
				String fixed = text.stripLeading().startsWith("{") ? HlomResources.toJson(result.resource())
						: HlomResources.toXml(result.resource());
				Files.writeString(file, fixed, StandardCharsets.UTF_8);
				Result after = DocumentCheck.check(fixed, URI.createFileURI(file.toAbsolutePath().toString()));
				remaining += (int) after.count(Severity.ERROR);
			} else {
				remaining += (int) result.count(Severity.ERROR) - (dryRun ? plan.size() : 0);
			}
			if (plan.isEmpty()) {
				out.println(file + ": no automatic fixes");
			}
		}
		return remaining > 0 ? FAILED : OK;
	}

	private static Resource load(Path file) throws IOException {
		DocumentType type = DocumentTypes.detect(Inputs.read(file));
		if (type == null) {
			throw new IOException(file + " is not a MedBiquitous document of a known type.");
		}
		Resource resource = type.createResourceSet().getResource(URI.createFileURI(file.toAbsolutePath().toString()),
				true);
		if (DocumentTypes.of(resource) == null) {
			throw new IOException(file + " is not a MedBiquitous document of a known type.");
		}
		return resource;
	}

	// ----------------------------------------------------------------------------------------------------
	// search, info
	// ----------------------------------------------------------------------------------------------------

	private int search(Arguments arguments) throws UsageException, IOException {
		List<String> operands = arguments.operands();
		if (operands.size() < 2) {
			throw new UsageException("search takes a text and at least one file or folder.");
		}
		Query query = query(arguments, operands.getFirst());
		int matches = 0;
		for (Path file : Inputs.documents(operands.subList(1, operands.size()))) {
			Resource resource = DocumentCheck.load(Inputs.read(file), URI.createFileURI(file.toAbsolutePath().toString()));
			for (Hit hit : DocumentSearch.search(resource, query)) {
				matches += hit.matches().size();
				out.println(file + ": " + hit.value().label() + ": " + highlight(hit));
			}
			resource.unload();
		}
		return matches > 0 ? OK : FAILED;
	}

	private int replace(Arguments arguments) throws UsageException, IOException {
		List<String> operands = arguments.operands();
		if (operands.size() < 3) {
			throw new UsageException("replace takes a text, its replacement and at least one file or folder.");
		}
		Query query = query(arguments, operands.getFirst());
		String replacement = operands.get(1);
		boolean dryRun = arguments.has("dry-run");
		int changed = 0;
		for (Path file : Inputs.documents(operands.subList(2, operands.size()))) {
			String text = Inputs.read(file);
			DocumentReplace.Result result = DocumentReplace.replace(text,
					URI.createFileURI(file.toAbsolutePath().toString()), query, replacement, path -> true);
			for (DocumentReplace.Change change : result.changes()) {
				out.println(file + ": " + change.label() + ": \"" + change.before() + "\" -> \"" + change.after() + "\"");
			}
			for (String skipped : result.skipped()) {
				err.println(file + ": skipped " + skipped);
			}
			if (!result.changes().isEmpty()) {
				changed += result.changes().size();
				if (!dryRun) {
					Files.writeString(file, result.text(), StandardCharsets.UTF_8);
				}
			}
		}
		err.println((dryRun ? "Would change " : "Changed ") + changed + (changed == 1 ? " value." : " values."));
		return changed > 0 ? OK : FAILED;
	}

	/** The query of the search options. */
	private static Query query(Arguments arguments, String text) throws UsageException {
		List<FieldGroup> fields = new ArrayList<>();
		for (String id : arguments.get("in", "").split(",")) {
			if (id.isBlank()) {
				continue;
			}
			FieldGroup group = fieldGroup(id.strip());
			if (group == null) {
				throw new UsageException("Unknown field group \"" + id.strip() + "\".");
			}
			fields.add(group);
		}
		Query query = new Query(text, arguments.has("case-sensitive"), arguments.has("regex"),
				arguments.has("whole-word"), fields);
		try {
			query.pattern();
		} catch (PatternSyntaxException e) {
			throw new UsageException("Invalid regular expression: " + e.getDescription());
		}
		return query;
	}

	/** A field group of any document type by id (or label), or {@code null}. */
	private static FieldGroup fieldGroup(String id) {
		FieldGroup hlom = HlomSearchFields.byId(id);
		if (hlom != null) {
			return hlom;
		}
		return DocumentTypes.all().stream().flatMap(type -> type.searchFields().stream())
				.filter(group -> group.id().equalsIgnoreCase(id) || group.label().equalsIgnoreCase(id)).findFirst()
				.orElse(null);
	}

	/** The value with the matches in brackets, whitespace collapsed. */
	private static String highlight(Hit hit) {
		String text = hit.value().text().replaceAll("\\s", " ");
		StringBuilder marked = new StringBuilder();
		int position = 0;
		for (int[] match : hit.matches()) {
			marked.append(text, position, match[0]).append('[').append(text, match[0], match[0] + match[1]).append(']');
			position = match[0] + match[1];
		}
		return marked.append(text.substring(position)).toString().replaceAll(" {2,}", " ").strip();
	}

	private int info(Arguments arguments) throws UsageException, IOException {
		boolean first = true;
		for (Path file : Inputs.documents(arguments.operands())) {
			Result result = DocumentCheck.check(Inputs.read(file), URI.createFileURI(file.toAbsolutePath().toString()));
			if (!first) {
				out.println();
			}
			first = false;
			out.println(file);
			DocumentRoot root = result.root();
			line("Type", result.documentRoot() != null ? result.type().label() : null);
			if (root == null && result.documentRoot() != null) {
				line("Title", result.type().title(result.documentRoot()));
				line("Description", result.type().description(result.documentRoot()));
			}
			if (root != null) {
				HlomSummary summary = HlomSummary.of(root);
				line("Title", summary.titleOr("(untitled)"));
				line("Kind", summary.kind().label());
				line("Languages", String.join(", ", summary.languages()));
				line("Keywords", String.join("; ", summary.keywords().stream().map(k -> k.id() == null ? k.text()
						: k.text() + " [" + k.source() + " " + k.id() + "]").toList()));
				line("Contributors", String.join("; ", summary.contributorNames()));
				line("Credits", summary.creditsLabel());
				line("Audience", summary.audienceLabel());
			}
			line("Problems", result.count(Severity.ERROR) + " errors, " + result.count(Severity.WARNING) + " warnings, "
					+ result.count(Severity.INFO) + " infos");
			result.resource().unload();
		}
		return OK;
	}

	private void line(String label, String value) {
		if (value != null && !value.isBlank()) {
			out.println("  " + label + ": " + value);
		}
	}

	/**
	 * The version of the tool: the {@code Implementation-Version} of the launcher jar ({@code hermes.jar}), else the
	 * {@code Bundle-Version} of the jar holding this class, else {@code "development"}.
	 */
	public static String version() {
		String launcher = Hermes.class.getPackage().getImplementationVersion();
		if (launcher != null) {
			return launcher;
		}
		try {
			java.net.URL self = Hermes.class.getResource("Hermes.class");
			if (self != null && self.openConnection() instanceof java.net.JarURLConnection jar
					&& jar.getManifest() != null) {
				String version = jar.getManifest().getMainAttributes().getValue("Bundle-Version");
				if (version != null && !version.contains("qualifier")) {
					return version;
				}
			}
		} catch (IOException _) {
			// Unknown.
		}
		return "development";
	}
}
