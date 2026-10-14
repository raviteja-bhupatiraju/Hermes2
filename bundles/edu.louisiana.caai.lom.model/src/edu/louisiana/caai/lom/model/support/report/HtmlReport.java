/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Contributor;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Credit;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Fact;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Keyword;
import edu.louisiana.caai.lom.model.support.report.HlomSummary.Kind;

/**
 * Self-contained HTML reports of HLOM records (no external resources, embedded CSS in the application palette,
 * print friendly): a one-page catalog card of a record and a sortable overview of a whole library.
 */
public final class HtmlReport {

	private static final String GENERATOR = "Hermes 2: MedBiquitous Editor";
	private static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.US);
	private static final DateTimeFormatter DAY_TIME = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.US);

	private static final String CSS = """
			:root{--accent:#CE181E;--teal:#0E7C7B;--ink:#1E293B;--slate:#475569;--muted:#94A3B8;--amber:#D97706;
			--bg:#F4F6F9;--bar:#EEF2F6;--border:#DDE3EA;--surface:#fff;--ok:#0E7C7B}
			*{box-sizing:border-box}
			html{background:var(--bg)}
			body{margin:0;padding:40px 24px 56px;color:var(--ink);font:15px/1.55 "Segoe UI",system-ui,-apple-system,
			"Helvetica Neue",Arial,sans-serif;-webkit-font-smoothing:antialiased}
			a{color:var(--teal);text-decoration:none}a:hover{text-decoration:underline}
			.sheet{max-width:880px;margin:0 auto;background:var(--surface);border:1px solid var(--border);
			border-radius:14px;box-shadow:0 1px 2px rgba(30,41,59,.04),0 12px 32px rgba(30,41,59,.08);overflow:hidden}
			.wide{max-width:1280px}
			.band{height:6px;background:linear-gradient(90deg,var(--accent),#E3484D 55%,var(--teal))}
			.band.edu{background:var(--accent)}.band.asset{background:var(--teal)}.band.lo{background:var(--slate)}
			header.head{padding:28px 36px 22px;border-bottom:1px solid var(--border);display:flex;gap:24px;
			align-items:flex-start}
			header.head .main{flex:1;min-width:0}
			.eyebrow{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
			.pill{display:inline-block;font-size:11.5px;font-weight:600;letter-spacing:.06em;text-transform:uppercase;
			padding:3px 10px;border-radius:999px;background:var(--bar);color:var(--slate);white-space:nowrap}
			.pill.edu{background:#FDECEC;color:var(--accent)}.pill.asset{background:#E3F2F1;color:var(--teal)}
			.pill.lo{background:var(--bar);color:var(--slate)}
			h1{font:600 30px/1.2 Georgia,"Iowan Old Style","Palatino Linotype",serif;margin:0;letter-spacing:-.01em}
			h2{font-size:12px;font-weight:700;letter-spacing:.09em;text-transform:uppercase;color:var(--slate);
			margin:0 0 12px}
			.lead{margin:12px 0 0;color:var(--slate);font-size:16px;max-width:68ch}
			.status{flex:none;text-align:center;min-width:132px;padding:12px 14px;border-radius:12px;
			border:1px solid var(--border);background:var(--bar)}
			.status .big{font-size:15px;font-weight:700}
			.status .small{font-size:12px;color:var(--slate)}
			.status.valid{background:#E3F2F1;border-color:#BFE0DE;color:var(--teal)}
			.status.warnings{background:#FEF3E2;border-color:#F8D9A8;color:#B45309}
			.status.errors{background:#FDECEC;border-color:#F5C2C4;color:var(--accent)}
			.meter{height:6px;border-radius:3px;background:var(--border);margin-top:8px;overflow:hidden}
			.meter i{display:block;height:100%;background:var(--teal)}
			section{padding:22px 36px;border-bottom:1px solid var(--border)}
			section:last-of-type{border-bottom:0}
			dl.facts{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:14px 32px;margin:0}
			dl.facts div{min-width:0}
			dt{font-size:12px;color:var(--muted);font-weight:600;letter-spacing:.04em;text-transform:uppercase}
			dd{margin:2px 0 0;overflow-wrap:anywhere}
			.chips{display:flex;flex-wrap:wrap;gap:8px}
			.chip{display:inline-flex;align-items:center;gap:6px;border:1px solid var(--border);border-radius:999px;
			padding:3px 12px;font-size:13.5px;background:var(--surface);color:var(--ink)}
			.chip small{font-size:11px;color:var(--teal);font-weight:600}
			a.chip:hover{border-color:var(--teal);text-decoration:none}
			table{width:100%;border-collapse:collapse;font-size:14px}
			th{text-align:left;font-size:11.5px;letter-spacing:.06em;text-transform:uppercase;color:var(--slate);
			font-weight:700;padding:8px 10px;border-bottom:2px solid var(--border);background:var(--bar);
			white-space:nowrap}
			td{padding:9px 10px;border-bottom:1px solid var(--border);vertical-align:top}
			tr:last-child td{border-bottom:0}
			td.num,th.num{text-align:right;font-variant-numeric:tabular-nums}
			ol.objectives{margin:0;padding-left:22px}ol.objectives li{margin:3px 0}
			ul.plain{list-style:none;margin:0;padding:0}ul.plain li{margin:3px 0}
			.role{display:inline-block;min-width:92px;color:var(--muted);font-size:12.5px;text-transform:capitalize}
			.problem{display:flex;gap:10px;align-items:baseline;padding:7px 0;border-bottom:1px dashed var(--border)}
			.problem:last-child{border-bottom:0}
			.dot{flex:none;width:9px;height:9px;border-radius:50%;background:var(--muted);transform:translateY(-1px)}
			.dot.error{background:var(--accent)}.dot.warning{background:var(--amber)}.dot.info{background:var(--teal)}
			.where{color:var(--muted);font-size:12.5px;white-space:nowrap}
			.ok{color:var(--teal);font-weight:600}
			footer{padding:14px 36px;background:var(--bar);color:var(--slate);font-size:12.5px;display:flex;
			justify-content:space-between;gap:16px;flex-wrap:wrap}
			.muted{color:var(--muted)}
			.tiles{display:grid;grid-template-columns:repeat(auto-fit,minmax(150px,1fr));gap:14px}
			.tile{border:1px solid var(--border);border-radius:12px;padding:14px 16px;background:var(--surface)}
			.tile .n{font:600 28px/1.1 Georgia,serif}.tile .l{font-size:12.5px;color:var(--slate);margin-top:4px}
			.tile.edu{border-top:3px solid var(--accent)}.tile.asset{border-top:3px solid var(--teal)}
			.tile.lo{border-top:3px solid var(--slate)}.tile.err .n{color:var(--accent)}.tile.warn .n{color:#B45309}
			.tile.valid .n{color:var(--teal)}
			.badge{display:inline-block;font-size:11.5px;font-weight:700;padding:2px 9px;border-radius:999px;
			white-space:nowrap}
			.badge.valid{background:#E3F2F1;color:var(--teal)}.badge.warnings{background:#FEF3E2;color:#B45309}
			.badge.errors{background:#FDECEC;color:var(--accent)}
			.lib th[data-col]{cursor:pointer;user-select:none}
			.lib th[data-col]:after{content:"\\2195";color:var(--muted);margin-left:6px;font-size:11px}
			.lib th.asc:after{content:"\\2191";color:var(--accent)}.lib th.desc:after{content:"\\2193";color:var(--accent)}
			.lib tbody tr:hover td{background:#F8FAFC}
			.lib .t{font-weight:600}.lib .x{color:var(--slate);font-size:12.5px;margin-top:2px}
			.lib .file{color:var(--muted);font-size:12.5px;white-space:nowrap;display:inline-block;max-width:170px;
			overflow:hidden;text-overflow:ellipsis;vertical-align:top}
			.scroll{overflow-x:auto}.lib td.a-col{min-width:150px}
			.lib td,.lib th{padding-left:7px;padding-right:7px}.lib{font-size:13.5px}.lib .bar{width:40px}
			.lib td.num{white-space:nowrap}
			.lib .nowrap{white-space:nowrap}.lib td.t-col{min-width:240px}
			.search{width:100%;max-width:360px;padding:8px 12px;border:1px solid var(--border);border-radius:8px;
			font:inherit;background:var(--surface)}
			.search:focus{outline:2px solid #BFE0DE;border-color:var(--teal)}
			.toolbar{display:flex;justify-content:space-between;align-items:center;gap:16px;margin-bottom:14px;
			flex-wrap:wrap}
			.bar{display:inline-block;height:6px;width:56px;border-radius:3px;background:var(--border);
			vertical-align:middle;margin-right:6px;overflow:hidden}.bar i{display:block;height:100%;background:var(--teal)}
			@media (max-width:700px){dl.facts{grid-template-columns:1fr}header.head{flex-direction:column}
			body{padding:16px 8px}section,header.head,footer{padding-left:20px;padding-right:20px}}
			@media print{html,body{background:#fff}body{padding:0;font-size:12px}
			.sheet{box-shadow:none;border:0;border-radius:0;max-width:none}
			section,.problem,tr,.tile{break-inside:avoid}.search,.noprint{display:none}
			a{color:inherit}.lib th[data-col]:after{content:""}
			*{-webkit-print-color-adjust:exact;print-color-adjust:exact}}
			""";

	private static final String SORT_SCRIPT = """
			(function(){var t=document.getElementById('records');if(!t)return;var b=t.tBodies[0];
			t.querySelectorAll('th[data-col]').forEach(function(h){h.addEventListener('click',function(){
			var i=+h.dataset.col,n=h.dataset.type==='num',d=h.classList.contains('asc')?-1:1;
			t.querySelectorAll('th').forEach(function(x){x.classList.remove('asc','desc')});
			h.classList.add(d>0?'asc':'desc');
			Array.from(b.rows).sort(function(r,s){var a=r.cells[i].dataset.v,c=s.cells[i].dataset.v;
			if(n){return d*((parseFloat(a)||0)-(parseFloat(c)||0))}return d*a.localeCompare(c,undefined,{numeric:true,
			sensitivity:'base'})}).forEach(function(r){b.appendChild(r)})})});
			var q=document.getElementById('q');if(q){q.addEventListener('input',function(){var v=q.value.toLowerCase();
			var shown=0;Array.from(b.rows).forEach(function(r){var m=r.textContent.toLowerCase().indexOf(v)>=0;
			r.style.display=m?'':'none';if(m)shown++});document.getElementById('shown').textContent=shown})}})();
			""";

	private HtmlReport() {
	}

	/** A catalog card of a record with its validation status. */
	public static String document(HlomSummary summary, List<HlomProblem> problems) {
		return document(summary, problems, null, -1);
	}

	/** A catalog card of a library entry. */
	public static String document(LibraryEntry entry) {
		return document(entry.summary(), entry.problems(), entry.path().toString(), entry.completeness());
	}

	/**
	 * A one-page catalog card of a record.
	 *
	 * @param summary      the record
	 * @param problems     its validation problems ({@code null} if it wasn't validated)
	 * @param source       the file or URI the record was read from, or {@code null}
	 * @param completeness completeness score 0 to 100, or negative if unknown
	 */
	public static String document(HlomSummary summary, List<HlomProblem> problems, String source, int completeness) {
		String title = summary.titleOr("Untitled record");
		StringBuilder html = new StringBuilder(8192);
		head(html, title + " – Healthcare LOM record");
		html.append("<main class=\"sheet\">\n<div class=\"band ").append(kindClass(summary.kind())).append("\"></div>\n");

		// Heading with kind, identifiers and validation status.
		html.append("<header class=\"head\"><div class=\"main\"><div class=\"eyebrow\"><span class=\"pill ")
				.append(kindClass(summary.kind())).append("\">").append(esc(summary.kind().label())).append("</span>");
		if (summary.status() != null) {
			html.append("<span class=\"pill\">").append(esc(summary.status())).append("</span>");
		}
		if (summary.version() != null) {
			html.append("<span class=\"pill\">Version ").append(esc(summary.version())).append("</span>");
		}
		html.append("</div>\n<h1>").append(esc(title)).append("</h1>\n");
		if (summary.description() != null) {
			html.append("<p class=\"lead\">").append(esc(summary.description())).append("</p>\n");
		}
		html.append("</div>\n");
		statusBox(html, problems, completeness);
		html.append("</header>\n");

		// Key facts.
		List<String[]> facts = new ArrayList<>();
		fact(facts, "Credits", summary.creditsLabel());
		fact(facts, "Audience", summary.audienceLabel());
		fact(facts, "Audience category", String.join(", ", summary.audienceCategories()));
		fact(facts, "Released", date(summary.releaseDate()));
		fact(facts, "Expires", date(summary.expirationDate()));
		fact(facts, "Starts", date(summary.startDate()));
		fact(facts, "Ends", date(summary.endDate()));
		fact(facts, "Location", summary.locationLabel());
		fact(facts, "Context", String.join(", ", summary.contexts()));
		fact(facts, "Resource type", String.join(", ", summary.learningResourceTypes()));
		fact(facts, "Language", String.join(", ", summary.languages()));
		fact(facts, "Format", String.join(", ", summary.formats()));
		for (Fact detail : summary.details()) {
			fact(facts, detail.label(), detail.value());
		}
		fact(facts, "Cost", summary.cost());
		fact(facts, "Identifier", String.join("\n", summary.identifiers()));
		if (!facts.isEmpty()) {
			html.append("<section><h2>At a glance</h2><dl class=\"facts\">\n");
			for (String[] f : facts) {
				html.append("<div><dt>").append(esc(f[0])).append("</dt><dd>")
						.append(esc(f[1]).replace("\n", "<br>")).append("</dd></div>\n");
			}
			html.append("</dl></section>\n");
		}

		if (!summary.keywords().isEmpty()) {
			html.append("<section><h2>Keywords</h2><div class=\"chips\">\n");
			for (Keyword keyword : summary.keywords()) {
				if (keyword.isMesh()) {
					html.append("<a class=\"chip\" title=\"MeSH descriptor ").append(esc(keyword.id()))
							.append("\" href=\"https://meshb.nlm.nih.gov/record/ui?ui=")
							.append(esc(URLEncoder.encode(keyword.id(), StandardCharsets.UTF_8))).append("\">")
							.append(esc(keyword.text())).append(" <small>MeSH ").append(esc(keyword.id()))
							.append("</small></a>\n");
				} else {
					html.append("<span class=\"chip\">").append(esc(keyword.text()));
					if (keyword.id() != null) {
						html.append(" <small>").append(esc(HlomSummary.join(" ", keyword.source(), keyword.id())))
								.append("</small>");
					}
					html.append("</span>\n");
				}
			}
			html.append("</div></section>\n");
		}

		if (!summary.objectives().isEmpty()) {
			html.append("<section><h2>Educational objectives</h2><ol class=\"objectives\">\n");
			summary.objectives().forEach(o -> html.append("<li>").append(esc(o)).append("</li>\n"));
			html.append("</ol></section>\n");
		}

		if (!summary.credits().isEmpty()) {
			html.append("<section><h2>Credits</h2><table><thead><tr><th>Type</th><th class=\"num\">Amount</th>")
					.append("<th>Unit</th><th>Accrediting body</th><th>Certification</th><th>Provider</th></tr>")
					.append("</thead><tbody>\n");
			for (Credit credit : summary.credits()) {
				html.append("<tr><td>").append(esc(credit.type())).append("</td><td class=\"num\">")
						.append(credit.number() == null ? "" : esc(credit.number().stripTrailingZeros().toPlainString()))
						.append("</td><td>").append(esc(credit.unit())).append("</td><td>")
						.append(esc(credit.accreditingBody())).append("</td><td>").append(esc(credit.certification()))
						.append("</td><td>").append(esc(credit.provider())).append("</td></tr>\n");
			}
			html.append("</tbody></table></section>\n");
		}

		if (!summary.contributors().isEmpty() || !summary.locations().isEmpty() || summary.rights() != null) {
			html.append("<section><dl class=\"facts\">");
			if (!summary.contributors().isEmpty()) {
				html.append("<div><h2>Contributors</h2><ul class=\"plain\">\n");
				for (Contributor contributor : summary.contributors()) {
					html.append("<li><span class=\"role\">").append(esc(contributor.role())).append("</span>")
							.append(esc(contributor.name()));
					if (contributor.date() != null) {
						html.append(" <span class=\"muted\" style=\"white-space:nowrap\">· ")
								.append(esc(date(contributor.date())))
								.append("</span>");
					}
					html.append("</li>\n");
				}
				html.append("</ul></div>");
			}
			html.append("<div>");
			if (!summary.locations().isEmpty()) {
				html.append("<h2>Access</h2><ul class=\"plain\">\n");
				summary.locations().forEach(l -> html.append("<li>").append(link(l)).append("</li>\n"));
				html.append("</ul>");
			}
			if (summary.rights() != null) {
				html.append("<h2 style=\"margin-top:").append(summary.locations().isEmpty() ? "0" : "16px")
						.append("\">Rights</h2><p style=\"margin:0\">").append(esc(summary.rights())).append("</p>");
			}
			html.append("</div></dl></section>\n");
		}

		if (problems != null) {
			html.append("<section><h2>Validation</h2>\n");
			if (problems.isEmpty()) {
				html.append("<p class=\"ok\" style=\"margin:0\">✓ The record conforms to the Healthcare LOM schemas, ")
						.append("model constraints and best practice rules.</p>\n");
			} else {
				List<HlomProblem> sorted = new ArrayList<>(problems);
				sorted.sort(Comparator.comparing(HlomProblem::severity).thenComparingInt(HlomProblem::line));
				for (HlomProblem problem : sorted) {
					String severity = problem.severity().name().toLowerCase(Locale.ROOT);
					html.append("<div class=\"problem\"><span class=\"dot ").append(severity).append("\" title=\"")
							.append(esc(severity)).append("\"></span><span style=\"flex:1\">")
							.append(esc(problem.message())).append("</span><span class=\"where\">")
							.append(esc(where(problem))).append("</span></div>\n");
				}
			}
			html.append("</section>\n");
		}

		footer(html, source);
		html.append("</main>\n</body>\n</html>\n");
		return html.toString();
	}

	/** A sortable overview of many records with summary statistics. */
	public static String library(List<LibraryEntry> entries) {
		return library(entries, null);
	}

	/**
	 * A sortable, filterable overview of many records with summary statistics.
	 *
	 * @param folder the folder the records were found in, shown in the heading, or {@code null}
	 */
	public static String library(List<LibraryEntry> entries, String folder) {
		StringBuilder html = new StringBuilder(16384);
		head(html, "Healthcare LOM library report");
		html.append("<main class=\"sheet wide\">\n<div class=\"band\"></div>\n");
		html.append("<header class=\"head\"><div class=\"main\"><div class=\"eyebrow\"><span class=\"pill\">")
				.append("Library report</span></div><h1>Healthcare LOM metadata library</h1>");
		if (folder != null) {
			html.append("<p class=\"lead\">").append(esc(folder)).append("</p>");
		}
		html.append("</div></header>\n");

		// Statistics.
		long offerings = count(entries, e -> e.summary().kind() == Kind.EDUCATIONAL_OFFERING);
		long assets = count(entries, e -> e.summary().kind() == Kind.IMAGE_ASSET);
		long objects = count(entries, e -> e.summary().kind() == Kind.LEARNING_OBJECT);
		long valid = count(entries, e -> e.status() == LibraryEntry.Status.VALID);
		long withErrors = count(entries, e -> e.errors() > 0);
		int errors = entries.stream().mapToInt(LibraryEntry::errors).sum();
		int warnings = entries.stream().mapToInt(LibraryEntry::warnings).sum();
		html.append("<section><h2>Overview</h2><div class=\"tiles\">\n");
		tile(html, "", entries.size(), entries.size() == 1 ? "record" : "records");
		tile(html, "edu", offerings, "educational offerings");
		tile(html, "asset", assets, "image assets");
		tile(html, "lo", objects, "other learning objects");
		tile(html, "valid", valid, "valid records");
		tile(html, "err", withErrors, "with errors (" + errors + " in total)");
		tile(html, "warn", warnings, warnings == 1 ? "warning" : "warnings");
		html.append("</div>\n");
		Map<String, BigDecimal> totals = HlomSummary.totals(entries.stream().map(LibraryEntry::summary).toList());
		if (!totals.isEmpty()) {
			html.append("<h2 style=\"margin-top:20px\">Credits offered</h2><div class=\"chips\">");
			totals.forEach((key, value) -> html.append("<span class=\"chip\"><b>")
					.append(esc(value.stripTrailingZeros().toPlainString())).append("</b> ").append(esc(key))
					.append("</span>"));
			html.append("</div>\n");
		}
		html.append("</section>\n");

		// Records.
		boolean completeness = entries.stream().anyMatch(LibraryEntry::hasCompleteness);
		html.append("<section><div class=\"toolbar\"><h2 style=\"margin:0\">Records <span class=\"muted\">(")
				.append("<span id=\"shown\">").append(entries.size()).append("</span>)</span></h2>")
				.append("<input id=\"q\" class=\"search noprint\" type=\"search\" placeholder=\"Filter records…\">")
				.append("</div>\n<div class=\"scroll\"><table id=\"records\" class=\"lib\"><thead><tr>");
		int col = 0;
		th(html, "Status", col++, false, false);
		th(html, "Title", col++, false, false);
		th(html, "Kind", col++, false, false);
		th(html, "Credits", col++, true, true);
		th(html, "Audience", col++, false, false);
		th(html, "Modified", col++, false, false);
		th(html, "Errors", col++, true, true);
		th(html, "Warnings", col++, true, true);
		if (completeness) {
			th(html, "Complete", col++, true, true);
		}
		th(html, "File", col, false, false);
		html.append("</tr></thead><tbody>\n");
		List<LibraryEntry> sorted = new ArrayList<>(entries);
		sorted.sort(Comparator.comparing(LibraryEntry::title, String.CASE_INSENSITIVE_ORDER));
		for (LibraryEntry entry : sorted) {
			HlomSummary s = entry.summary();
			String status = entry.status().name().toLowerCase(Locale.ROOT);
			BigDecimal credits = s.credits().stream().map(Credit::number).filter(n -> n != null)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			html.append("<tr>");
			td(html, String.valueOf(entry.status().ordinal()), "<span class=\"badge " + status + "\">"
					+ esc(statusText(entry)) + "</span>", null);
			td(html, entry.title(), "<div class=\"t\">" + esc(entry.title()) + "</div>"
					+ (s.description() == null ? "" : "<div class=\"x\">" + esc(s.excerpt(110)) + "</div>"), "t-col");
			td(html, s.kind().label(), "<span class=\"pill " + kindClass(s.kind()) + "\">" + esc(shortKind(s.kind()))
					+ "</span>", null);
			td(html, credits.toPlainString(), String.join("<br>", s.credits().stream()
					.map(c -> "<span class=\"nowrap\">" + esc(c.label()) + "</span>").toList()), null);
			td(html, s.audienceLabel(), esc(s.audienceLabel()), "a-col");
			td(html, entry.modified() == null ? "" : entry.modified().toString(),
					"<span class=\"nowrap\">" + esc(day(entry.modified())) + "</span>", null);
			td(html, String.valueOf(entry.errors()), entry.errors() == 0 ? "<span class=\"muted\">0</span>"
					: String.valueOf(entry.errors()), "num");
			td(html, String.valueOf(entry.warnings()), entry.warnings() == 0 ? "<span class=\"muted\">0</span>"
					: String.valueOf(entry.warnings()), "num");
			if (completeness) {
				td(html, String.valueOf(entry.completeness()), entry.hasCompleteness()
						? "<span class=\"bar\"><i style=\"width:" + entry.completeness() + "%\"></i></span>"
								+ entry.completeness() + "%"
						: "", "num");
			}
			td(html, entry.fileName(), "<a class=\"file\" title=\"" + esc(entry.path().toString()) + "\" href=\"" + esc(entry.path().toUri().toString()) + "\">"
					+ esc(entry.fileName()) + "</a>", null);
			html.append("</tr>\n");
		}
		html.append("</tbody></table></div>\n");
		if (entries.isEmpty()) {
			html.append("<p class=\"muted\">No Healthcare LOM records were found.</p>\n");
		}
		html.append("</section>\n");
		footer(html, null);
		html.append("</main>\n<script>").append(SORT_SCRIPT).append("</script>\n</body>\n</html>\n");
		return html.toString();
	}

	/** Escapes text for HTML element content and attribute values; {@code null} becomes {@code ""}. */
	public static String esc(String text) {
		if (text == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder(text.length() + 16);
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
				case '&' -> builder.append("&amp;");
				case '<' -> builder.append("&lt;");
				case '>' -> builder.append("&gt;");
				case '"' -> builder.append("&quot;");
				case '\'' -> builder.append("&#39;");
				default -> {
					if (c < 0x20 && c != '\n' && c != '\t' && c != '\r') {
						builder.append(' ');
					} else {
						builder.append(c);
					}
				}
			}
		}
		return builder.toString();
	}

	private static void head(StringBuilder html, String title) {
		html.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
				.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n")
				.append("<meta name=\"generator\" content=\"").append(GENERATOR).append("\">\n<title>")
				.append(esc(title)).append("</title>\n<style>\n").append(CSS).append("</style>\n</head>\n<body>\n");
	}

	private static void footer(StringBuilder html, String source) {
		html.append("<footer><span>Healthcare LOM (MedBiquitous ANSI/MEDBIQ LO.10.1) · generated by ")
				.append(GENERATOR).append(" on ").append(esc(dateTime(Instant.now()))).append("</span>");
		if (source != null) {
			html.append("<span>").append(esc(source)).append("</span>");
		}
		html.append("</footer>\n");
	}

	private static void statusBox(StringBuilder html, List<HlomProblem> problems, int completeness) {
		if (problems == null && completeness < 0) {
			return;
		}
		String css = "";
		String big = "Not validated";
		String small = "";
		if (problems != null) {
			long errors = problems.stream().filter(p -> p.severity() == Severity.ERROR).count();
			long warnings = problems.stream().filter(p -> p.severity() == Severity.WARNING).count();
			if (errors > 0) {
				css = "errors";
				big = plural(errors, "error");
				small = warnings > 0 ? "and " + plural(warnings, "warning") : "must be fixed";
			} else if (warnings > 0) {
				css = "warnings";
				big = plural(warnings, "warning");
				small = "no errors";
			} else {
				css = "valid";
				big = "✓ Valid";
				small = "HLOM v1 conformant";
			}
		}
		html.append("<div class=\"status ").append(css).append("\"><div class=\"big\">").append(esc(big))
				.append("</div><div class=\"small\">").append(esc(small)).append("</div>");
		if (completeness >= 0) {
			int value = Math.min(100, completeness);
			html.append("<div class=\"meter\" title=\"Completeness ").append(value).append("%\"><i style=\"width:")
					.append(value).append("%\"></i></div><div class=\"small\">").append(value)
					.append("% complete</div>");
		}
		html.append("</div>\n");
	}

	private static String statusText(LibraryEntry entry) {
		return switch (entry.status()) {
			case ERRORS -> plural(entry.errors(), "error");
			case WARNINGS -> plural(entry.warnings(), "warning");
			case VALID -> "Valid";
		};
	}

	private static String plural(long count, String noun) {
		return count + " " + noun + (count == 1 ? "" : "s");
	}

	private static void tile(StringBuilder html, String css, long number, String label) {
		html.append("<div class=\"tile ").append(css).append("\"><div class=\"n\">").append(number)
				.append("</div><div class=\"l\">").append(esc(label)).append("</div></div>\n");
	}

	private static void th(StringBuilder html, String label, int col, boolean numeric, boolean right) {
		html.append("<th data-col=\"").append(col).append("\"").append(numeric ? " data-type=\"num\"" : "")
				.append(right ? " class=\"num\"" : "").append(">").append(esc(label)).append("</th>");
	}

	private static void td(StringBuilder html, String sortValue, String content, String css) {
		html.append("<td data-v=\"").append(esc(sortValue == null ? "" : sortValue)).append("\"")
				.append(css == null ? "" : " class=\"" + css + "\"").append(">").append(content).append("</td>");
	}

	private static long count(List<LibraryEntry> entries, Predicate<LibraryEntry> predicate) {
		return entries.stream().filter(predicate).count();
	}

	private static String kindClass(Kind kind) {
		return switch (kind) {
			case EDUCATIONAL_OFFERING -> "edu";
			case IMAGE_ASSET -> "asset";
			case LEARNING_OBJECT -> "lo";
		};
	}

	private static void fact(List<String[]> facts, String label, String value) {
		if (value != null && !value.isBlank()) {
			facts.add(new String[] { label, value });
		}
	}

	private static String where(HlomProblem problem) {
		String location = problem.path() != null ? problem.path() : "";
		if (problem.line() > 0) {
			location = location.isEmpty() ? "line " + problem.line() : location + " · line " + problem.line();
		}
		return HlomSummary.join(" · ", problem.origin().label(), location);
	}

	/** A link for URLs with a safe scheme; other values are shown as text. */
	private static String link(String url) {
		String lower = url.toLowerCase(Locale.ROOT);
		if (lower.startsWith("http://") || lower.startsWith("https://") || lower.startsWith("ftp://")
				|| lower.startsWith("mailto:")) {
			return "<a href=\"" + esc(url) + "\">" + esc(url) + "</a>";
		}
		return esc(url);
	}

	/** Formats an ISO date or date-time ({@code 2007-04-30}) as {@code 30 Apr 2007}; other text as is. */
	static String date(String iso) {
		if (iso == null || iso.isBlank()) {
			return null;
		}
		try {
			if (iso.length() > 10 && iso.charAt(10) == 'T') {
				return LocalDateTime.parse(iso.substring(0, Math.min(iso.length(), 16))).format(DAY_TIME);
			}
			return LocalDate.parse(iso.length() >= 10 ? iso.substring(0, 10) : iso).format(DAY);
		} catch (DateTimeParseException _) {
			return iso;
		}
	}

	private static String shortKind(Kind kind) {
		return switch (kind) {
			case EDUCATIONAL_OFFERING -> "Offering";
			case IMAGE_ASSET -> "Image asset";
			case LEARNING_OBJECT -> "Learning object";
		};
	}

	private static String day(Instant instant) {
		return instant == null ? "" : DAY.format(instant.atZone(ZoneId.systemDefault()));
	}

	private static String dateTime(Instant instant) {
		return instant == null ? "" : DAY_TIME.format(instant.atZone(ZoneId.systemDefault()));
	}
}
