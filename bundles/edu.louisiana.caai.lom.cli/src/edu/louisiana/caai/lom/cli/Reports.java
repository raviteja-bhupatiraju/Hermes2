/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.check.TextPositions.Range;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JNumber;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;

/** Writes validation results as text (one line per problem), JSON or SARIF 2.1.0 (for code scanning tools). */
final class Reports {

	/** The checked problems of a document. */
	record Checked(Path file, List<Located> problems) {
	}

	/** The URL of the tool, written to SARIF. */
	static final String INFORMATION_URI = "https://www.medbiq.org/standards";

	private Reports() {
	}

	/** {@code file:line:column: severity: message [origin]} per problem, then a summary. */
	static String text(List<Checked> results, boolean summary) {
		StringBuilder out = new StringBuilder();
		int errors = 0;
		int warnings = 0;
		int infos = 0;
		int invalid = 0;
		for (Checked checked : results) {
			boolean hasErrors = false;
			for (Located located : checked.problems()) {
				HlomProblem problem = located.problem();
				out.append(checked.file());
				Range range = located.range();
				if (range != null) {
					out.append(':').append(range.line()).append(':').append(range.column());
				}
				out.append(": ").append(severity(problem.severity())).append(": ").append(problem.message());
				out.append(" [").append(problem.origin().label());
				if (problem.path() != null) {
					out.append(' ').append(problem.path());
				}
				out.append("]\n");
				switch (problem.severity()) {
					case ERROR -> {
						errors++;
						hasErrors = true;
					}
					case WARNING -> warnings++;
					case INFO -> infos++;
				}
			}
			if (hasErrors) {
				invalid++;
			}
		}
		if (summary) {
			out.append(results.size()).append(results.size() == 1 ? " document" : " documents").append(" checked, ")
					.append(invalid).append(" invalid: ").append(count(errors, "error")).append(", ")
					.append(count(warnings, "warning")).append(", ").append(count(infos, "info")).append(".\n");
		}
		return out.toString();
	}

	private static String count(int n, String word) {
		return n + " " + word + (n == 1 ? "" : "s");
	}

	static String severity(Severity severity) {
		return switch (severity) {
			case ERROR -> "error";
			case WARNING -> "warning";
			case INFO -> "info";
		};
	}

	/** A JSON array with one object per document: {@code file}, {@code valid} and {@code problems}. */
	static String json(List<Checked> results) {
		JArray documents = new JArray();
		for (Checked checked : results) {
			JArray problems = new JArray();
			boolean valid = true;
			for (Located located : checked.problems()) {
				HlomProblem problem = located.problem();
				valid &= problem.severity() != Severity.ERROR;
				JObject entry = new JObject().put("severity", severity(problem.severity()))
						.put("origin", problem.origin().label()).put("message", problem.message());
				if (problem.path() != null) {
					entry.put("path", problem.path());
				}
				Range range = located.range();
				if (range != null) {
					entry.put("line", number(range.line())).put("column", number(range.column()))
							.put("endLine", number(range.endLine() + 1)).put("endColumn", number(range.endColumn() + 1));
				}
				problems.items().add(entry);
			}
			documents.items().add(new JObject().put("file", checked.file().toString())
					.put("valid", new Json.JBoolean(valid, -1)).put("problems", problems));
		}
		return Json.write(documents) + "\n";
	}

	/** A SARIF 2.1.0 log with one rule per kind of check. */
	static String sarif(List<Checked> results, Path base) {
		Map<Origin, String> rules = new LinkedHashMap<>();
		rules.put(Origin.SCHEMA, "hlom-schema");
		rules.put(Origin.MODEL, "hlom-model");
		rules.put(Origin.RULES, "hlom-rules");
		JArray ruleArray = new JArray();
		for (var rule : rules.entrySet()) {
			ruleArray.items().add(new JObject().put("id", rule.getValue()).put("name", rule.getKey().name().toLowerCase())
					.put("shortDescription", new JObject().put("text", switch (rule.getKey()) {
						case SCHEMA -> "The document must be valid against the MedBiquitous XML Schemas.";
						case MODEL -> "Values must satisfy the data types, patterns and multiplicities of the schemas.";
						case RULES -> "The document should follow the MedBiquitous best practice rules.";
					})));
		}
		JArray resultArray = new JArray();
		for (Checked checked : results) {
			String uri = base == null ? checked.file().toUri().toString()
					: base.toAbsolutePath().relativize(checked.file().toAbsolutePath()).toString().replace('\\', '/');
			for (Located located : checked.problems()) {
				HlomProblem problem = located.problem();
				JObject physical = new JObject().put("artifactLocation", new JObject().put("uri", uri));
				Range range = located.range();
				if (range != null) {
					physical.put("region", new JObject().put("startLine", number(range.line()))
							.put("startColumn", number(range.column())).put("endLine", number(range.endLine() + 1))
							.put("endColumn", number(range.endColumn() + 1)));
				}
				JObject location = new JObject().put("physicalLocation", physical);
				if (problem.path() != null) {
					location.put("logicalLocations", array(new JObject().put("fullyQualifiedName", problem.path())
							.put("kind", "element")));
				}
				resultArray.items().add(new JObject().put("ruleId", rules.get(problem.origin()))
						.put("level", switch (problem.severity()) {
							case ERROR -> "error";
							case WARNING -> "warning";
							case INFO -> "note";
						}).put("message", new JObject().put("text", problem.message()))
						.put("locations", array(location)));
			}
		}
		JObject driver = new JObject().put("name", "Hermes 2").put("informationUri", INFORMATION_URI)
				.put("version", Hermes.version()).put("rules", ruleArray);
		JObject run = new JObject().put("tool", new JObject().put("driver", driver)).put("results", resultArray);
		JObject log = new JObject().put("$schema", "https://json.schemastore.org/sarif-2.1.0.json")
				.put("version", "2.1.0").put("runs", array(run));
		return Json.write(log) + "\n";
	}

	private static JArray array(Json.Value... values) {
		JArray array = new JArray();
		array.items().addAll(List.of(values));
		return array;
	}

	private static JNumber number(int value) {
		return new JNumber(Integer.toString(value), -1);
	}
}
