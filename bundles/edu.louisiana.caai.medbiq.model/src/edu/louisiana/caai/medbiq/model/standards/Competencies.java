/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.standards;

import static edu.louisiana.caai.medbiq.model.forms.PageSpec.field;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.group;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.page;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.repeat;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.section;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.suggest;
import static edu.louisiana.caai.medbiq.model.forms.PageSpec.text;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.AnyType;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.medbiq.model.DocumentGraph;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.SectionSpec;

/**
 * Competency Framework 1.0, Competency Object 1.0 and Performance Framework 1.0 (all describe themselves with
 * Healthcare LOM): titles and descriptions, the form pages, the rules of the specifications, search fields and help.
 * <p>
 * Sources: MEDBIQ CF.10.1-2012 Competency Framework Specifications and its implementation guidelines, the Competency
 * Object Specifications (2011) and ANSI/MEDBIQ PF.10.1-2015 Performance Framework Specifications, © MedBiquitous
 * Consortium, quoted under the MedBiquitous XML Public License.
 */
public final class Competencies implements StandardContent {

	/** The type id of Competency Framework 1.0. */
	public static final String FRAMEWORK = "competency-framework-v1";
	/** The type id of Competency Object 1.0. */
	public static final String OBJECT = "competency-object-v1";
	/** The type id of Performance Framework 1.0. */
	public static final String PERFORMANCE = "performance-framework-v1";

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;
	private static final String TITLE = "lom/general/title/string";
	private static final String SKOS = "http://www.w3.org/2004/02/skos/core#";
	private static final String CO_NAMESPACE = "http://ns.medbiq.org/competencyobject/v1/";
	private static final String CF_NAMESPACE = "http://ns.medbiq.org/competencyframework/v1/";
	/** The recommended labels of additional information (Performance Framework 7.5.3). */
	private static final List<String> LABELS = List.of("assessment method", "background", "example", "note",
			"reference", "resource");

	// ----------------------------------------------------------------------------------------------------
	// Titles and descriptions
	// ----------------------------------------------------------------------------------------------------

	@Override
	public String title(MedbiqDocumentType type, EObject rootElement) {
		return Paths.text(rootElement, TITLE);
	}

	@Override
	public String description(MedbiqDocumentType type, EObject rootElement) {
		String description = Paths.text(rootElement, "lom/general/description/string");
		if (description != null) {
			return description;
		}
		return switch (type.id()) {
			case FRAMEWORK -> count(Paths.objects(rootElement, "Includes").size(), "included competency",
					"included competencies") + ", " + count(Paths.objects(rootElement, "Relation").size(), "relation",
							"relations");
			case OBJECT -> {
				String status = enumText(rootElement, "Status");
				yield (status == null ? "Active" : status) + " competency object";
			}
			case PERFORMANCE -> count(Paths.objects(rootElement, "Component").size(), "component", "components") + ", "
					+ count(Paths.objects(rootElement, "PerformanceScale").size(), "performance scale",
							"performance scales");
			default -> null;
		};
	}

	private static String count(int n, String one, String many) {
		return n + " " + (n == 1 ? one : many);
	}

	// ----------------------------------------------------------------------------------------------------
	// Pages
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<PageSpec> pages(MedbiqDocumentType type) {
		return switch (type.id()) {
			case FRAMEWORK -> FRAMEWORK_PAGES;
			case OBJECT -> OBJECT_PAGES;
			case PERFORMANCE -> PERFORMANCE_PAGES;
			default -> List.of();
		};
	}

	/** The description from the embedded Healthcare LOM: what names, identifies and credits the document. */
	private static SectionSpec lomSection(String what) {
		return section("Description", "Title, description and identifiers of the " + what
				+ " (Healthcare LOM). The title and an identifier with the catalog URI are required.", "lom",
				field("general/title", "Title"), text("general/description", "Description"),
				field("general/keyword", "Keywords"), field("general/language", "Languages"),
				repeat("general/identifier", "Identifiers", "entry", field("catalog", "Catalog"),
						field("entry", "Entry")),
				repeat("lifeCycle/contribute", "Contributors (role publisher for the publisher)", "entity",
						field("role/value", "Role"), text("entity", "Entity (vCard)")));
	}

	/** The life cycle, educational context and rights of the embedded Healthcare LOM. */
	private static SectionSpec lifeCycleSection(String what) {
		return section("Status and Rights", "Version, life-cycle status (draft or final recommended), educational "
				+ "context and copyright of the " + what + ".", "lom", field("lifeCycle/version", "Version"),
				group("lifeCycle/status", "Life-cycle status", field("source", "Source (LOMv1.0)"),
						field("value", "Status")),
				group("educational/context", "Educational context",
						field("source", "Source (HEALTHCARE_LOMv1)"), field("value", "Context")),
				group("rights/copyrightAndOtherRestrictions", "Copyright and other restrictions",
						field("source", "Source (LOMv1.0)"), field("value", "Restricted")),
				text("rights/description", "Rights description"));
	}

	private static SectionSpec datesSection(String what) {
		return section("Dates", "When this version of the " + what + " became (or becomes) effective and when it was "
				+ "(or will be) retired.", "", field("EffectiveDate", "Effective date"),
				field("RetiredDate", "Retired date"));
	}

	private static SectionSpec versionsSection(String what) {
		return section("Versions", "The " + what + "s this one supersedes (Replaces) and the one that supersedes it "
				+ "(Is replaced by), as URIs (based on the Dublin Core terms).", "", field("Replaces", "Replaces"),
				field("IsReplacedBy", "Is replaced by"));
	}

	private static final FieldSpec[] IDENTIFIER = { field("Catalog", "Catalog"), field("Entry", "Entry") };

	private static final List<PageSpec> FRAMEWORK_PAGES = List.of(
			page("description", "Description", "Framework", "Title, description, identifiers and publisher",
					"general", lomSection("competency framework"), lifeCycleSection("competency framework")),
			page("versioning", "Dates and Versions", "Framework",
					"Effective and retired dates, replaced and replacing frameworks", "lifecycle",
					datesSection("competency framework"), versionsSection("competency framework")),
			page("supporting", "Supporting Information", "Framework",
					"Links to or embedded rationale and introductory material", "annotation",
					section("Supporting Information", "Links to or includes supporting information, such as the "
							+ "rationale of the framework, its intended use or the changes of this version. Each item "
							+ "is either a link or formatted text (XHTML, edited on the XML page).", "",
							repeat("SupportingInformation", "Supporting information", "Link", field("Link", "Link"),
									field("div", "Formatted text")))),
			page("includes", "Included Competencies", "Competencies",
					"The competency objects and frameworks the framework includes", "classification",
					section("Includes", "Every competency of the framework, including each competency object or "
							+ "framework named in a relation. Use the catalog URI and the competency object's URI "
							+ "for MedBiquitous competency objects.", "",
							repeat("Includes", "Included competencies", "Entry", IDENTIFIER))),
			page("relations", "Relations", "Competencies",
					"Broader, narrower and related competencies (SKOS)", "relation",
					section("Relations", "Each relation says that the competency of Reference 1 has a broader, "
							+ "narrower or related concept: the competency of Reference 2. Both must be included; "
							+ "cycles and hierarchical conflicts are forbidden.", "",
							repeat("Relation", "Relations", "Reference1/Entry",
									group("Reference1", "Reference 1", IDENTIFIER),
									field("Relationship", "Relationship"),
									group("Reference2", "Reference 2", IDENTIFIER)))));

	private static final List<PageSpec> OBJECT_PAGES = List.of(
			page("description", "Description", "Competency", "The statement, its identifiers and publisher",
					"general", lomSection("competency object (the title is the statement)"),
					lifeCycleSection("competency object")),
			page("status", "Status and Versions", "Competency", "Active or retired; replaced and replacing objects",
					"lifecycle",
					section("Status", "Whether the competency object is in use (Active) or Retired.", "",
							field("Status", "Status")),
					versionsSection("competency object")),
			page("categories", "Categories", "Competency", "Categories of the competency (like Atom categories)",
					"classification",
					section("Categories", "Categories of the competency, such as its kind (role, outcome, objective) "
							+ "or domain. The term is required; the scheme is a URI of the categorization scheme.", "",
							repeat("Category", "Categories", "@term",
									suggest("@term", "Term", "competency domain", "sub-competency", "role",
											"learning outcome", "learning objective", "measurable outcomes", "topic"),
									field("@scheme", "Scheme (URI)"), field("@label", "Label")))),
			page("references", "References and Supporting Information", "Competency",
					"Literature references and supporting information", "annotation",
					section("References", "References to the literature related to this competency, one per "
							+ "reference.", "References", field("Reference", "References")),
					section("Supporting Information", "A link to, or formatted text (XHTML, edited on the XML page) "
							+ "with, a longer or formatted description of the competency.", "SupportingInformation",
							field("Link", "Link"), field("div", "Formatted text"))));

	private static final FieldSpec[] ADDITIONAL = { field("@position", "Position"),
			suggest("Label", "Labels", LABELS.toArray(String[]::new)), field("Text", "Texts"),
			field("div", "Formatted text") };

	private static final List<PageSpec> PERFORMANCE_PAGES = List.of(
			page("description", "Description", "Framework", "Title, description, identifiers and publisher",
					"general", lomSection("performance framework"), lifeCycleSection("performance framework")),
			page("versioning", "Dates and Versions", "Framework",
					"Effective and retired dates, replaced and replacing frameworks", "lifecycle",
					datesSection("performance framework"), versionsSection("performance framework")),
			page("supporting", "Supporting Information", "Framework",
					"Rationale and intended use: references or formatted text", "annotation",
					section("Supporting Information", "Includes or points to supporting information, such as the "
							+ "rationale of the framework and its intended use: a reference (RDF, edited on the XML "
							+ "page) or formatted text (XHTML, edited on the XML page).", "",
							repeat("SupportingInformation", "Supporting information", null,
									group("Reference", "Reference (format)"), field("div", "Formatted text")))),
			page("scales", "Performance Scales", "Performance", "The numeric scales of the performance levels",
					"overview",
					section("Performance Scales", "Each scale has an id (referenced by the performance level sets) "
							+ "and the scores of its least and most competent ends; lower numbers may mean higher "
							+ "competence.", "",
							repeat("PerformanceScale", "Performance scales", "@id", field("@id", "Id"),
									field("LeastCompetent", "Least competent"),
									field("MostCompetent", "Most competent")))),
			page("components", "Components", "Performance",
					"Components, their competencies, authors, thresholds and nested components", "healthcare-education",
					section("Components", "A component describes a spectrum of performance levels, usually for one "
							+ "competency (referenced with RDF, edited on the XML page), or groups nested components. "
							+ "Its performance levels are on the Performance Levels page.", "",
							repeat("Component", "Components", "Title", field("@id", "Id"), field("Title", "Titles"),
									field("Abbreviation", "Abbreviations"), field("Author", "Authors"),
									field("Reviewer", "Reviewers"),
									field("ComponentReference", "Nested components (ids)"),
									repeat("Threshold", "Thresholds", "Title", field("Title", "Titles"),
											text("Description", "Descriptions"),
											field("MinimumAcceptableScore", "Minimum acceptable score")),
									repeat("AdditionalInformation", "Additional information", "Label",
											ADDITIONAL)))),
			page("levels", "Performance Levels", "Performance",
					"The performance level sets: levels, scores and indicators", "educational",
					section("Performance Levels", "The performance level set of each component: the scale it uses "
							+ "and two or more levels, each with a display order, a score (a single value or a "
							+ "range), labels and indicators (behavioral markers).", "",
							repeat("Component", "Components", "Title",
									group("PerformanceLevelSet", "Performance level set",
											field("PerformanceScaleReference", "Performance scale (id)"),
											repeat("PerformanceLevel", "Performance levels", "Label",
													field("DisplayOrder", "Display order"),
													field("Score/SingleValue", "Score (single value)"),
													group("Score/Range", "Score range", field("MinScore", "Min score"),
															field("MaxScore", "Max score")),
													field("Label", "Labels"),
													repeat("Indicator", "Indicators", "Description",
															field("@id", "Id"), text("Description", "Descriptions"),
															repeat("AdditionalInformation", "Additional information",
																	"Label", ADDITIONAL)),
													repeat("AdditionalInformation", "Additional information",
															"Label", ADDITIONAL)))))));

	// ----------------------------------------------------------------------------------------------------
	// Search fields
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<FieldGroup> searchFields(MedbiqDocumentType type) {
		return switch (type.id()) {
			case FRAMEWORK -> List.of(FieldGroup.of("cf-includes", "Included competencies", "Includes"),
					FieldGroup.of("cf-relations", "Relations", "Relation"),
					FieldGroup.of("cf-supporting", "Supporting information", "SupportingInformation"),
					FieldGroup.of("cf-versions", "Replaced and replacing frameworks", "Replaces", "IsReplacedBy"));
			case OBJECT -> List.of(FieldGroup.of("co-categories", "Categories", "Category"),
					FieldGroup.of("co-references", "Literature references", "References"),
					FieldGroup.of("co-status", "Status", "Status"),
					FieldGroup.of("co-supporting", "Supporting information", "SupportingInformation"),
					FieldGroup.of("co-versions", "Replaced and replacing objects", "Replaces", "IsReplacedBy"));
			case PERFORMANCE -> List.of(
					new FieldGroup("pf-components", "Component titles and abbreviations",
							Set.of("Title", "Abbreviation"), Set.of("Component")),
					FieldGroup.of("pf-levels", "Performance levels", "PerformanceLevel"),
					FieldGroup.of("pf-indicators", "Indicators", "Indicator"),
					FieldGroup.of("pf-thresholds", "Thresholds", "Threshold"),
					FieldGroup.of("pf-additional", "Additional information", "AdditionalInformation"),
					FieldGroup.of("pf-people", "Authors and reviewers", "Author", "Reviewer"),
					FieldGroup.of("pf-supporting", "Supporting information", "SupportingInformation"),
					FieldGroup.of("pf-versions", "Replaced and replacing frameworks", "Replaces", "IsReplacedBy"));
			default -> List.of();
		};
	}

	// ----------------------------------------------------------------------------------------------------
	// Rules
	// ----------------------------------------------------------------------------------------------------

	@Override
	public List<HlomProblem> rules(MedbiqDocumentType type, EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		if (root == null) {
			return List.of();
		}
		Rules rules = new Rules(root);
		switch (type.id()) {
			case FRAMEWORK -> {
				rules.lom("competency framework", true);
				rules.dates("competency framework");
				rules.versions("competency framework");
				rules.links();
				rules.includesAndRelations();
			}
			case OBJECT -> {
				rules.lom("competency object", false);
				rules.status();
				rules.versions("competency object");
				rules.links();
				rules.categories();
			}
			case PERFORMANCE -> {
				rules.lom("performance framework", true);
				rules.dates("performance framework");
				rules.versions("performance framework");
				rules.performance();
			}
			default -> {
				// Not a type of this standard.
			}
		}
		return rules.problems;
	}

	/** The checks of one document. */
	private static final class Rules {

		final EObject root;
		final List<HlomProblem> problems = new ArrayList<>();

		Rules(EObject root) {
			this.root = root;
		}

		void report(Severity severity, String message, EObject object) {
			problems.add(new HlomProblem(severity, Origin.RULES, message, object, null, -1, -1));
		}

		/** The identifiers, title and recommended metadata of the embedded Healthcare LOM. */
		void lom(String what, boolean framework) {
			EObject lom = Paths.objects(root, "lom").stream().findFirst().orElse(null);
			if (lom == null) {
				return; // The schema reports the missing lom element.
			}
			EObject general = Paths.objects(lom, "general").stream().findFirst().orElse(lom);
			if (Paths.text(root, TITLE) == null) {
				report(Severity.ERROR, "The " + what + " has no title. The specification requires general title "
						+ "(\"Defines the title for this " + what + " in one or more languages\"); add it on the "
						+ "Description page.", general);
			}
			List<EObject> identifiers = Paths.objects(lom, "general/identifier");
			List<EObject> uriIdentifiers = identifiers.stream()
					.filter(id -> "URI".equalsIgnoreCase(norm(Paths.text(id, "catalog")))).toList();
			if (uriIdentifiers.isEmpty()) {
				report(Severity.ERROR, "The " + what + " has no identifier with the catalog URI. For " + what + "s, "
						+ "identifiers must be in the form of a URI (other catalogs may be added for local systems "
						+ "as long as a URI identifier is present); add a general identifier with catalog \"URI\" and "
						+ "the URI where the document is published as entry.", general);
			}
			for (EObject identifier : uriIdentifiers) {
				String entry = Paths.text(identifier, "entry");
				if (entry != null && !isAbsoluteUri(entry)) {
					report(Severity.ERROR, "The identifier \"" + entry + "\" has the catalog URI but is not an "
							+ "absolute URI; use a URI such as https://www.example.org/competencies/framework1.",
							identifier);
				}
			}
			if (Paths.text(lom, "general/description/string") == null) {
				report(Severity.INFO, "The " + what + " has no description. A short description of the " + what
						+ " is recommended.", general);
			}
			if (framework) {
				boolean publisher = Paths.objects(lom, "lifeCycle/contribute").stream()
						.anyMatch(c -> "publisher".equals(Paths.text(c, "role/value")));
				if (!publisher) {
					report(Severity.INFO, "The " + what + " names no publisher. Use a life-cycle contribute with the "
							+ "role publisher and an entity (vCard) describing the publisher (recommended).", lom);
				}
				if (Paths.text(lom, "lifeCycle/status/value") == null) {
					report(Severity.INFO, "The " + what + " has no life-cycle status. Stating the status (draft or "
							+ "final) is recommended.", lom);
				}
			}
		}

		/** EffectiveDate before RetiredDate; a retired framework names its replacement. */
		void dates(String what) {
			LocalDate effective = date(root, "EffectiveDate");
			LocalDate retired = date(root, "RetiredDate");
			if (effective != null && retired != null && effective.isAfter(retired)) {
				report(Severity.ERROR, "The effective date (" + effective + ") is after the retired date (" + retired
						+ "). A " + what + " retires after it becomes effective; correct one of the dates.", root);
			}
			boolean replaced = !Paths.texts(root, "IsReplacedBy").isEmpty();
			if (retired != null && !replaced) {
				boolean past = !retired.isAfter(LocalDate.now());
				report(past ? Severity.WARNING : Severity.INFO, "The " + what + (past ? " was retired on " :
						" will retire on ") + retired + " but names no replacement. If it has been superseded by a "
						+ "more recent version, use IsReplacedBy to point to the version that supersedes it.", root);
			}
			if (replaced && retired == null) {
				report(Severity.INFO, "The " + what + " is replaced by another but has no retired date. Use "
						+ "RetiredDate to indicate when this version will or did retire.", root);
			}
		}

		/** Replaces and IsReplacedBy are URIs other than the document's own identifier and do not overlap. */
		void versions(String what) {
			Set<String> own = new HashSet<>(Paths.texts(root, "lom/general/identifier/entry"));
			List<String> replaces = Paths.texts(root, "Replaces");
			List<String> replacedBy = Paths.texts(root, "IsReplacedBy");
			for (String element : List.of("Replaces", "IsReplacedBy")) {
				for (String uri : Paths.texts(root, element)) {
					if (!isAbsoluteUri(uri)) {
						report(Severity.WARNING, element + " \"" + uri + "\" is not an absolute URI. Implementers must "
								+ "use a URI to represent the related " + what + ".", root);
					}
					if (own.contains(uri)) {
						report(Severity.ERROR, element + " names the " + what + " itself (" + uri + "); it must name "
								+ "another version.", root);
					}
				}
			}
			for (String uri : replaces) {
				if (replacedBy.contains(uri)) {
					report(Severity.ERROR, "\"" + uri + "\" is named both in Replaces and in IsReplacedBy; a version "
							+ "cannot both supersede and be superseded by the same " + what + ".", root);
				}
			}
		}

		/** Link of SupportingInformation must contain a valid URI. */
		void links() {
			for (EObject info : Paths.objects(root, "SupportingInformation")) {
				String link = Paths.text(info, "Link");
				if (link != null && !isAbsoluteUri(link)) {
					report(Severity.WARNING, "The supporting information link \"" + link + "\" is not a valid "
							+ "absolute URI. Link must contain a valid URI.", info);
				}
			}
		}

		/** Competency Framework: unique includes, relations of included competencies, no cycles. */
		void includesAndRelations() {
			Map<String, EObject> included = new LinkedHashMap<>();
			for (EObject include : Paths.objects(root, "Includes")) {
				String key = key(include);
				if (included.putIfAbsent(key, include) != null) {
					report(Severity.WARNING, "The competency " + label(include) + " is included more than once. "
							+ "Identifiers must uniquely identify competencies; remove the duplicate.", include);
				}
				String entry = Paths.text(include, "Entry");
				if ("URI".equalsIgnoreCase(norm(Paths.text(include, "Catalog"))) && entry != null
						&& !isAbsoluteUri(entry)) {
					report(Severity.ERROR, "The included competency \"" + entry + "\" has the catalog URI but is not "
							+ "an absolute URI.", include);
				}
			}
			Map<String, EObject> relations = new HashMap<>();
			Map<String, Set<String>> narrower = new HashMap<>();
			List<Edge> edges = new ArrayList<>();
			for (EObject relation : Paths.objects(root, "Relation")) {
				EObject first = Paths.objects(relation, "Reference1").stream().findFirst().orElse(null);
				EObject second = Paths.objects(relation, "Reference2").stream().findFirst().orElse(null);
				String relationship = enumText(relation, "Relationship");
				if (first == null || second == null || relationship == null) {
					continue; // The schema reports the missing parts.
				}
				for (EObject reference : List.of(first, second)) {
					if (!included.containsKey(key(reference))) {
						report(Severity.ERROR, "The relation references " + label(reference) + ", which the framework "
								+ "does not include. All competencies included in the framework must be referenced "
								+ "using the Includes element, including any competency object or framework "
								+ "referenced in a relation; add it to the included competencies.", reference);
					}
				}
				String a = key(first);
				String b = key(second);
				if (a.equals(b)) {
					report(Severity.ERROR, "The relation relates " + label(first) + " to itself. Cyclical references "
							+ "are forbidden.", relation);
					continue;
				}
				String kind = relationship.strip().startsWith(SKOS) ? relationship.strip().substring(SKOS.length())
						: relationship.strip();
				// a has narrower b, or a has broader b (b has narrower a); related is symmetric.
				String normalized = switch (kind) {
					case "narrower" -> "h " + a + " > " + b;
					case "broader" -> "h " + b + " > " + a;
					default -> "r " + (a.compareTo(b) < 0 ? a + " ~ " + b : b + " ~ " + a);
				};
				if (relations.putIfAbsent(normalized, relation) != null) {
					report(Severity.WARNING, "The relation between " + label(first) + " and " + label(second)
							+ " is stated more than once (a has broader concept b and b has narrower concept a are the "
							+ "same relation; the converse relationship does not need to be encoded). Remove the "
							+ "duplicate.", relation);
				}
				switch (kind) {
					case "narrower" -> edges.add(new Edge(a, b, relation));
					case "broader" -> edges.add(new Edge(b, a, relation));
					default -> {
						// Non-hierarchical.
					}
				}
			}
			for (Edge edge : edges) {
				narrower.computeIfAbsent(edge.broader(), _ -> new LinkedHashSet<>()).add(edge.narrower());
			}
			for (Edge edge : edges) {
				if (reaches(narrower, edge.narrower(), edge.broader())) {
					report(Severity.ERROR, "This relation is part of a cycle of broader and narrower relations: "
							+ display(edge.narrower()) + " is (directly or indirectly) broader than "
							+ display(edge.broader()) + ". Cyclical references are forbidden as are any references "
							+ "that result in a hierarchical conflict.", edge.relation());
				}
			}
		}

		/** Competency Object: Status stated; retired objects name their replacement; active ones are not replaced. */
		void status() {
			String status = enumText(root, "Status");
			boolean replaced = !Paths.texts(root, "IsReplacedBy").isEmpty();
			if (status == null) {
				report(Severity.WARNING, "The competency object has no Status. State whether it is Active or Retired "
						+ "(without Status it is taken to be Active).", root);
			} else if (status.equals("Retired") && !replaced) {
				report(Severity.WARNING, "The competency object is Retired but names no replacement. If it has been "
						+ "superseded, use IsReplacedBy to point to the competency object that supersedes it.", root);
			} else if (status.equals("Active") && replaced) {
				report(Severity.WARNING, "The competency object is Active but is replaced by another (IsReplacedBy). "
						+ "Set the Status to Retired, or remove IsReplacedBy.", root);
			}
		}

		/** Competency Object: categories have a term, a URI scheme and are not repeated. */
		void categories() {
			Set<String> seen = new HashSet<>();
			for (EObject category : Paths.objects(root, "Category")) {
				String term = Paths.text(category, "@term");
				String scheme = Paths.text(category, "@scheme");
				if (term == null) {
					report(Severity.ERROR, "A category has an empty term. The term attribute names the category "
							+ "for this competency object and is required.", category);
					continue;
				}
				if (scheme != null && !isAbsoluteUri(scheme)) {
					report(Severity.WARNING, "The scheme \"" + scheme + "\" of the category \"" + term + "\" is not "
							+ "an absolute URI. The scheme is a URI for the categorization scheme.", category);
				}
				if (!seen.add(term + "\u0000" + norm(scheme))) {
					report(Severity.WARNING, "The category \"" + term + "\"" + (scheme == null ? "" : " of the "
							+ "scheme " + scheme) + " is given more than once; remove the duplicate.", category);
				}
			}
		}

		/** Performance Framework: scales, nested components, performance levels, thresholds, references. */
		void performance() {
			Map<String, Scale> scales = new LinkedHashMap<>();
			for (EObject scale : Paths.objects(root, "PerformanceScale")) {
				String id = Paths.text(scale, "@id");
				BigDecimal least = decimal(scale, "LeastCompetent");
				BigDecimal most = decimal(scale, "MostCompetent");
				if (id == null || least == null || most == null) {
					continue; // The schema reports the missing parts.
				}
				if (least.compareTo(most) == 0) {
					report(Severity.ERROR, "The performance scale \"" + id + "\" has the same score (" + least
							+ ") for its least and its most competent end; a scale needs two different scores.",
							scale);
					continue;
				}
				scales.put(id, new Scale(id, least, most, scale));
			}
			Map<String, EObject> components = new LinkedHashMap<>();
			for (EObject component : Paths.objects(root, "Component")) {
				String id = Paths.text(component, "@id");
				if (id != null) {
					components.put(id, component);
				}
			}
			Set<String> usedScales = new HashSet<>();
			Map<String, List<String>> nested = new HashMap<>();
			for (EObject component : components.values()) {
				String id = Paths.text(component, "@id");
				String name = componentName(component);
				for (String reference : Paths.texts(component, "ComponentReference")) {
					if (reference.equals(id)) {
						report(Severity.ERROR, "The component " + name + " references itself as a nested component. "
								+ "ComponentReference is the ID of a nested Component.", component);
					} else if (!components.containsKey(reference)) {
						report(Severity.ERROR, "The component " + name + " references \"" + reference + "\" as a "
								+ "nested component, but no component has that id (ComponentReference is the ID of a "
								+ "nested Component).", component);
					} else {
						nested.computeIfAbsent(id, _ -> new ArrayList<>()).add(reference);
					}
				}
				Scale scale = null;
				EObject set = Paths.objects(component, "PerformanceLevelSet").stream().findFirst().orElse(null);
				if (set != null) {
					String reference = Paths.text(set, "PerformanceScaleReference");
					if (reference != null) {
						scale = scales.get(reference);
						usedScales.add(reference);
						boolean defined = Paths.objects(root, "PerformanceScale").stream()
								.anyMatch(s -> reference.equals(Paths.text(s, "@id")));
						if (!defined) {
							report(Severity.ERROR, "The performance level set of the component " + name
									+ " references \"" + reference + "\", which is not a performance scale. "
									+ "PerformanceScaleReference is the identifier of a PerformanceScale defined in "
									+ "this document.", set);
						}
					}
					levels(name, set, scale);
				}
				thresholds(name, component, scale);
				competencies(name, component);
				additionalInformation(component);
			}
			for (Map.Entry<String, List<String>> entry : nested.entrySet()) {
				for (String child : entry.getValue()) {
					if (reaches(nested, child, entry.getKey())) {
						report(Severity.ERROR, "The nested components of " + componentName(components.get(entry
								.getKey())) + " form a cycle through \"" + child + "\"; a component cannot (directly "
								+ "or indirectly) contain itself.", components.get(entry.getKey()));
						break;
					}
				}
			}
			for (Scale scale : scales.values()) {
				if (!usedScales.contains(scale.id())) {
					report(Severity.INFO, "The performance scale \"" + scale.id() + "\" is not used by any "
							+ "performance level set.", scale.object());
				}
			}
		}

		/** The levels of a performance level set: unique display order, scores on the scale, consistent order. */
		private void levels(String component, EObject set, Scale scale) {
			Map<BigDecimal, EObject> orders = new HashMap<>();
			List<Level> levels = new ArrayList<>();
			for (EObject level : Paths.objects(set, "PerformanceLevel")) {
				BigDecimal order = decimal(level, "DisplayOrder");
				if (order != null && orders.putIfAbsent(order, level) != null) {
					report(Severity.ERROR, "Two performance levels of the component " + component + " have the display "
							+ "order " + order + ". DisplayOrder indicates the order of display of a level in "
							+ "relation to the other levels of the set; give each level its own position.", level);
				}
				BigDecimal single = decimal(level, "Score/SingleValue");
				BigDecimal min = decimal(level, "Score/Range/MinScore");
				BigDecimal max = decimal(level, "Score/Range/MaxScore");
				if (scale != null) {
					for (BigDecimal score : new BigDecimal[] { single, min, max }) {
						if (score != null && !scale.contains(score)) {
							report(Severity.ERROR, "The score " + score + " of a performance level of the component "
									+ component + " is outside its performance scale \"" + scale.id() + "\" ("
									+ scale.least() + " to " + scale.most() + ").", level);
						}
					}
					if (min != null && max != null && min.compareTo(max) != 0
							&& (max.compareTo(min) > 0) != scale.ascending()) {
						report(Severity.WARNING, "The score range " + min + " to " + max + " of a performance level of "
								+ "the component " + component + " runs against the scale \"" + scale.id() + "\" ("
								+ scale.least() + " least competent, " + scale.most() + " most competent). Give the "
								+ "score at the least competent end as MinScore.", level);
					}
				}
				if (order != null && (single != null || min != null && max != null)) {
					BigDecimal low = single != null ? single : min.min(max);
					BigDecimal high = single != null ? single : min.max(max);
					levels.add(new Level(order, low, high, level));
				}
			}
			levels.sort(Comparator.comparing(Level::order));
			int direction = 0;
			for (int i = 1; i < levels.size(); i++) {
				Level previous = levels.get(i - 1);
				Level current = levels.get(i);
				if (current.low().compareTo(previous.high()) <= 0 && previous.low().compareTo(current.high()) <= 0) {
					report(Severity.WARNING, "Performance levels " + previous.order() + " and " + current.order()
							+ " of the component " + component + " have the same or overlapping scores; each level "
							+ "should have its own score.", current.object());
					continue;
				}
				int step = current.low().compareTo(previous.high()) > 0 ? 1 : -1;
				if (direction == 0) {
					direction = step;
				} else if (step != direction) {
					report(Severity.WARNING, "The scores of the performance levels of the component " + component
							+ " do not follow their display order (level " + current.order() + " breaks the order). "
							+ "Order the levels so that the scores consistently rise or fall.", current.object());
					break;
				}
			}
		}

		/** The minimum acceptable score of a threshold lies on the component's scale. */
		private void thresholds(String component, EObject object, Scale scale) {
			if (scale == null) {
				return;
			}
			for (EObject threshold : Paths.objects(object, "Threshold")) {
				BigDecimal score = decimal(threshold, "MinimumAcceptableScore");
				if (score != null && !scale.contains(score)) {
					report(Severity.WARNING, "The minimum acceptable score " + score + " of the threshold \""
							+ Paths.text(threshold, "Title") + "\" of the component " + component + " is outside the "
							+ "scale \"" + scale.id() + "\" (" + scale.least() + " to " + scale.most() + ").",
							threshold);
				}
			}
		}

		/**
		 * The competency references of a component and its indicators: two references, one to a competency object,
		 * one to the competency framework that includes it, each naming a resource (rdf:Description rdf:about).
		 */
		private void competencies(String component, EObject object) {
			List<EObject> competencies = new ArrayList<>(Paths.objects(object, "Competency"));
			for (EObject level : Paths.objects(object, "PerformanceLevelSet/PerformanceLevel")) {
				for (EObject indicator : Paths.objects(level, "Indicator")) {
					competencies.addAll(Paths.objects(indicator, "Competency"));
				}
			}
			for (EObject competency : competencies) {
				List<EObject> references = Paths.objects(competency, "Reference");
				if (references.size() != 2) {
					continue; // The schema requires exactly two.
				}
				List<String> abouts = new ArrayList<>();
				Set<String> types = new HashSet<>();
				for (EObject reference : references) {
					List<String> about = rdfAttribute(reference, "about");
					if (about.isEmpty()) {
						report(Severity.WARNING, "A competency reference of the component " + component + " names no "
								+ "resource. Identify the competency object or framework with an rdf:Description "
								+ "element and its rdf:about URI.", reference);
					} else {
						abouts.add(about.getFirst());
					}
					rdfAttribute(reference, "resource").forEach(t -> types.add(norm(t)));
				}
				if (abouts.size() == 2 && abouts.get(0).equals(abouts.get(1))) {
					report(Severity.WARNING, "Both competency references of the component " + component + " name "
							+ abouts.getFirst() + ". References should occur twice: once to reference a competency "
							+ "object, once to reference the relevant competency framework including it.", competency);
				}
				boolean typed = types.stream().anyMatch(t -> t.startsWith("http://ns.medbiq.org/"));
				if (typed && !(types.contains(CO_NAMESPACE) && types.contains(CF_NAMESPACE))) {
					report(Severity.WARNING, "The competency references of the component " + component + " do not "
							+ "name a competency object and a competency framework (rdf:type " + CO_NAMESPACE
							+ " and " + CF_NAMESPACE + "). References should occur twice: once to reference a "
							+ "competency object, once to reference the relevant competency framework.", competency);
				}
			}
		}

		/** Positions of additional information are unique among those with the same label; labels recommended. */
		private void additionalInformation(EObject component) {
			List<EObject> owners = new ArrayList<>(List.of(component));
			for (EObject level : Paths.objects(component, "PerformanceLevelSet/PerformanceLevel")) {
				owners.add(level);
				owners.addAll(Paths.objects(level, "Indicator"));
			}
			for (EObject owner : owners) {
				Set<String> positions = new HashSet<>();
				for (EObject info : Paths.objects(owner, "AdditionalInformation")) {
					String label = Paths.text(info, "Label");
					String position = Paths.text(info, "@position");
					String key = norm(label).toLowerCase(Locale.ROOT) + "\u0000" + position;
					if (position != null && !positions.add(key)) {
						report(Severity.WARNING, "Two items of additional information labeled \"" + label + "\" have "
								+ "the position " + position + ". Position should be unique within the scope of "
								+ "AdditionalInformation elements with the same label.", info);
					}
					if (label != null && !LABELS.contains(label.strip().toLowerCase(Locale.ROOT))) {
						report(Severity.INFO, "The additional information label \"" + label + "\" is not one of the "
								+ "recommended values: " + String.join(", ", LABELS) + ".", info);
					}
				}
			}
		}
	}

	private record Edge(String broader, String narrower, EObject relation) {
	}

	private record Level(BigDecimal order, BigDecimal low, BigDecimal high, EObject object) {
	}

	private record Scale(String id, BigDecimal least, BigDecimal most, EObject object) {

		boolean ascending() {
			return most.compareTo(least) > 0;
		}

		boolean contains(BigDecimal score) {
			return score.compareTo(least.min(most)) >= 0 && score.compareTo(least.max(most)) <= 0;
		}
	}

	/** Whether a node reaches another along the edges (breadth-first). */
	private static boolean reaches(Map<String, ? extends java.util.Collection<String>> edges, String from, String to) {
		Set<String> seen = new HashSet<>();
		ArrayDeque<String> queue = new ArrayDeque<>(List.of(from));
		while (!queue.isEmpty()) {
			String node = queue.poll();
			if (node.equals(to)) {
				return true;
			}
			if (seen.add(node)) {
				java.util.Collection<String> next = edges.get(node);
				if (next != null) {
					queue.addAll(next);
				}
			}
		}
		return false;
	}

	/** The key of an identifier (Catalog and Entry). */
	private static String key(EObject identifier) {
		return norm(Paths.text(identifier, "Catalog")) + "\u0000" + norm(Paths.text(identifier, "Entry"));
	}

	private static String label(EObject identifier) {
		return "\"" + norm(Paths.text(identifier, "Entry")) + "\" (catalog " + norm(Paths.text(identifier, "Catalog"))
				+ ")";
	}

	private static String display(String key) {
		return "\"" + key.substring(key.indexOf('\u0000') + 1) + "\"";
	}

	private static String componentName(EObject component) {
		String title = Paths.text(component, "Title");
		String id = Paths.text(component, "@id");
		return title == null ? "\"" + id + "\"" : "\"" + title + "\" (" + id + ")";
	}

	/**
	 * The first value at a path as text, including enumeration values (in dynamic models they are
	 * {@link org.eclipse.emf.ecore.EEnumLiteral} objects, for which {@link Paths#text} finds no text).
	 */
	private static String enumText(EObject object, String path) {
		for (Object value : Paths.values(object, path)) {
			if (value instanceof Enumerator literal) {
				return literal.getLiteral();
			}
		}
		return Paths.text(object, path);
	}

	private static String norm(String text) {
		return text == null ? "" : text.strip();
	}

	private static boolean isAbsoluteUri(String text) {
		try {
			return new URI(text.strip()).isAbsolute();
		} catch (URISyntaxException e) {
			return false;
		}
	}

	private static LocalDate date(EObject object, String path) {
		return Paths.date(Paths.text(object, path));
	}

	private static BigDecimal decimal(EObject object, String path) {
		List<Object> values = Paths.values(object, path);
		if (values.isEmpty()) {
			return null;
		}
		Object value = values.getFirst();
		String text = switch (value) {
			case BigDecimal decimal -> decimal.toPlainString();
			case EObject element -> Paths.content(element);
			case null -> null;
			default -> value.toString();
		};
		if (text == null) {
			return null;
		}
		try {
			return new BigDecimal(text.strip());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * The values of an attribute (by local name, such as {@code about} or {@code resource}) of the elements below an
	 * element, including wildcard content (RDF elements are read as {@link AnyType} with their attributes in
	 * {@link AnyType#getAnyAttribute()}).
	 */
	private static List<String> rdfAttribute(EObject start, String name) {
		List<String> values = new ArrayList<>();
		Set<EObject> seen = Collections.newSetFromMap(new IdentityHashMap<>());
		ArrayDeque<EObject> queue = new ArrayDeque<>(List.of(start));
		while (!queue.isEmpty()) {
			EObject object = queue.poll();
			if (!seen.add(object)) {
				continue;
			}
			for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
				if (!object.eIsSet(feature)) {
					continue;
				}
				Object value = object.eGet(feature);
				if (FeatureMapUtil.isFeatureMap(feature)) {
					for (FeatureMap.Entry entry : (FeatureMap) value) {
						EStructuralFeature entryFeature = entry.getEStructuralFeature();
						if (entry.getValue() instanceof EObject child) {
							queue.add(child);
						} else if (entryFeature instanceof EAttribute && name.equals(META.getName(entryFeature))
								&& META.getFeatureKind(entryFeature) == ExtendedMetaData.ATTRIBUTE_FEATURE
								&& entry.getValue() != null) {
							values.add(entry.getValue().toString().strip());
						}
					}
				} else if (feature instanceof EReference reference && reference.isContainment()) {
					if (value instanceof List<?> list) {
						list.stream().filter(EObject.class::isInstance).map(EObject.class::cast).forEach(queue::add);
					} else if (value instanceof EObject child) {
						queue.add(child);
					}
				} else if (feature instanceof EAttribute && name.equals(META.getName(feature))
						&& META.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE && value != null) {
					values.add(value.toString().strip());
				}
			}
		}
		return values;
	}

	// ----------------------------------------------------------------------------------------------------
	// Help
	// ----------------------------------------------------------------------------------------------------

	/** The included competencies of a framework and their relations (broader, narrower, related). */
	@Override
	public void graph(MedbiqDocumentType type, EObject rootElement, DocumentGraph.Builder graph) {
		if (!type.id().equals(FRAMEWORK)) {
			return;
		}
		Map<String, DocumentGraph.Node> nodes = new HashMap<>();
		for (EObject include : Paths.objects(rootElement, "Includes")) {
			String entry = Paths.text(include, "Entry");
			if (entry != null) {
				String tail = entry.replaceAll("/+$", "");
				tail = tail.substring(tail.lastIndexOf('/') + 1);
				nodes.put(key(include), graph.node("Competency", entry, tail, include));
			}
		}
		for (EObject relation : Paths.objects(rootElement, "Relation")) {
			EObject first = Paths.objects(relation, "Reference1").stream().findFirst().orElse(null);
			EObject second = Paths.objects(relation, "Reference2").stream().findFirst().orElse(null);
			String relationship = enumText(relation, "Relationship");
			if (first != null && second != null && relationship != null) {
				String kind = relationship.strip().startsWith(SKOS) ? relationship.strip().substring(SKOS.length())
						: relationship.strip();
				graph.edge(nodes.get(key(first)), nodes.get(key(second)), kind);
			}
		}
	}

	@Override
	public List<DocumentLinks.LinkPath> linkPaths(MedbiqDocumentType type) {
		return type.id().equals(FRAMEWORK) ? List.of(new DocumentLinks.LinkPath("Includes", "Included competency"))
				: List.of();
	}

	@Override
	public List<References.Kind> references(MedbiqDocumentType type) {
		if (!type.id().equals(PERFORMANCE)) {
			return List.of();
		}
		return List.of(
				new References.Kind("Component", "ComponentReference", "Component", "@id", "Title", "%s"),
				new References.Kind("Performance scale", "PerformanceScaleReference", "PerformanceScale", "@id", null,
						"%s"));
	}

	@Override
	public Map<String, String> help(MedbiqDocumentType type) {
		Map<String, String> help = new HashMap<>(LOM_HELP);
		String what = switch (type.id()) {
			case OBJECT -> "competency object";
			case PERFORMANCE -> "performance framework";
			default -> "competency framework";
		};
		help.put("lom", "Healthcare LOM metadata: the title, identifiers, description, publisher and other "
				+ "descriptive information about this " + what + ". The title and an identifier with the catalog URI "
				+ "are required.");
		help.put("Replaces", "A related " + what + " that is supplanted, displaced, or superseded by the described "
				+ what + ". Implementers must use a URI to represent it (based on the Dublin Core term replaces).");
		help.put("IsReplacedBy", "A related " + what + " that supplants, displaces, or supersedes the described "
				+ what + ". Implementers must use a URI to represent it (based on the Dublin Core term isReplacedBy).");
		help.put("EffectiveDate", "The date this " + what + " becomes or became effective.");
		help.put("RetiredDate", "The date upon which this " + what + " was (or will be) retired.");
		help.put("div", "Formatted text (an XHTML div, a mix of text and XHTML tags), edited on the XML page.");
		switch (type.id()) {
			case FRAMEWORK -> help.putAll(FRAMEWORK_HELP);
			case OBJECT -> help.putAll(OBJECT_HELP);
			case PERFORMANCE -> help.putAll(PERFORMANCE_HELP);
			default -> {
				// No further help.
			}
		}
		return help;
	}

	private static final Map<String, String> LOM_HELP = Map.ofEntries(
			Map.entry("general", "A container for general metadata elements: identifiers, title, description."),
			Map.entry("identifier", "A unique identifier of the document. At least one identifier must have the "
					+ "catalog URI and a URI as entry; other catalogs may be added for local systems."),
			Map.entry("catalog", "The cataloging system of the identifier. Use URI for the required URI identifier."),
			Map.entry("entry", "The value of the identifier within its catalog; a URI when the catalog is URI."),
			Map.entry("title", "The title of the document in one or more languages (required)."),
			Map.entry("description", "A short description in one or more languages (recommended)."),
			Map.entry("keyword", "Keywords describing the topic of the document."),
			Map.entry("language", "The language(s) of the document's content, such as en."),
			Map.entry("lifeCycle", "The version, status and contributors (such as the publisher) of the document."),
			Map.entry("version", "The edition of the document, such as 2024 or 1.0."),
			Map.entry("status", "The status of the document (LOMv1.0): draft, final, revised or unavailable. Use of "
					+ "draft and final is recommended."),
			Map.entry("contribute", "A person or organization that contributed to the document. Use the role "
					+ "publisher with an entity describing the publisher."),
			Map.entry("role", "The kind of contribution (LOMv1.0 vocabulary); publisher for the publisher."),
			Map.entry("entity", "The contributor as a vCard (BEGIN:VCARD ... END:VCARD)."),
			Map.entry("educational", "The educational aspects, such as the educational context."),
			Map.entry("context", "The educational environment for which the document is intended "
					+ "(HEALTHCARE_LOMv1), such as graduate professional education."),
			Map.entry("rights", "Copyright and other restrictions on the use of the document."),
			Map.entry("copyrightAndOtherRestrictions", "Whether copyright or other restrictions apply (yes or no)."),
			Map.entry("healthcareMetadata", "Healthcare related metadata, such as the target audience."),
			Map.entry("profession", "The health profession for which the document is intended."),
			Map.entry("source", "The vocabulary of the value, such as LOMv1.0 or HEALTHCARE_LOMv1."),
			Map.entry("value", "The value from the vocabulary named by source."));

	private static final Map<String, String> FRAMEWORK_HELP = Map.ofEntries(
			Map.entry("CompetencyFramework", "The root element. It describes a set of related competency definitions "
					+ "and their relationships."),
			Map.entry("SupportingInformation", "Includes or links to supporting information, such as descriptions of "
					+ "the rationale for developing the framework and its intended use; either a Link or an "
					+ "xhtml:div."),
			Map.entry("Link", "A URL or URI reference to a supporting resource, such as a pdf or html file describing "
					+ "the purpose of the framework in detail. Link must contain a valid URI."),
			Map.entry("Includes", "Uniquely identifies a competency included in this framework. All competencies "
					+ "included in the framework must be referenced using Includes, including any competency object "
					+ "or framework referenced in a relation."),
			Map.entry("Catalog", "The identification or cataloging scheme for the entry. URIs are required for "
					+ "MedBiquitous Competency Objects and Competency Frameworks (catalog URI)."),
			Map.entry("Entry", "The value of the identifier within the cataloging scheme specified by Catalog, such "
					+ "as the URI of a competency object."),
			Map.entry("Relation", "A relationship between two components of the framework: a competency may have a "
					+ "broader, narrower or related competency or external framework. Cyclical references and "
					+ "hierarchical conflicts are forbidden."),
			Map.entry("Reference1", "Identifies the competency with a relationship to the component specified in "
					+ "Reference2."),
			Map.entry("Reference2", "Identifies the component that has a relationship to the competency specified in "
					+ "Reference1."),
			Map.entry("Relationship", "The nature of the relationship (SKOS): Reference1 has broader concept "
					+ "(skos:broader), has narrower concept (skos:narrower) or has related concept (skos:related) "
					+ "Reference2. Broader and narrower are converse and need not both be encoded."));

	private static final Map<String, String> OBJECT_HELP = Map.ofEntries(
			Map.entry("CompetencyObject", "The root element. It defines and uniquely identifies a single competency: "
					+ "any abstract statement of learning or performance expectations, such as a competency, learning "
					+ "outcome, learning objective or professional role."),
			Map.entry("Status", "Whether the competency object is Active or Retired (default Active). A retired "
					+ "object should name the object that replaces it."),
			Map.entry("Category", "A category associated with this competency, modeled after the Atom category "
					+ "element."),
			Map.entry("@term", "The category for this competency object (required)."),
			Map.entry("@scheme", "A URI for the categorization scheme. As a best practice, category definitions may "
					+ "be posted at the URI referenced."),
			Map.entry("@label", "A human-readable label for the term that may be displayed in end user applications."),
			Map.entry("References", "Specific references to the literature related to this competency."),
			Map.entry("Reference", "A single reference to the literature related to this competency."),
			Map.entry("SupportingInformation", "Includes or links to supporting information, such as formatted or "
					+ "lengthy descriptions of the competency object; either a Link or an xhtml:div."),
			Map.entry("Link", "A URL or URI reference to a supporting resource, such as a formatted version of the "
					+ "competency. Link must contain a valid URI."));

	private static final Map<String, String> PERFORMANCE_HELP = Map.ofEntries(
			Map.entry("PerformanceFramework", "The root element. It describes one or more sets of performance levels "
					+ "to be used in assessing competence or performance."),
			Map.entry("SupportingInformation", "Includes or points to supporting information, such as descriptions of "
					+ "the rationale for developing the framework and its intended use: a Reference or an xhtml:div."),
			Map.entry("Reference", "A reference to a resource (MedBiquitous common ReferenceType): an rdf:Description "
					+ "with the rdf:about URI, optionally an rdf:type and a dcterms:format."),
			Map.entry("Competency/Reference", "A reference to a competency object or competency framework. "
					+ "References occur twice: once to the competency object, once to the competency framework "
					+ "including it."),
			Map.entry("PerformanceScale", "A numeric scale used in this performance framework. Higher numbers usually "
					+ "indicate higher competence, but lower numbers may indicate higher competence."),
			Map.entry("@id", "A unique identifier, unique within the document (required)."),
			Map.entry("LeastCompetent", "The score indicating the lowest level of competence on this scale."),
			Map.entry("MostCompetent", "The score indicating the highest level of competence on this scale."),
			Map.entry("Component", "A component of this performance framework. Each component describes a spectrum "
					+ "of performance levels and related information, or groups nested components."),
			Map.entry("Title", "The title (one per language). A component's title often mirrors the title of the "
					+ "competency for which the performance levels are detailed."),
			Map.entry("Threshold/Title", "The title of this threshold, for example Progression to Phase 2."),
			Map.entry("Abbreviation", "An abbreviation for the component."),
			Map.entry("Competency", "References a competency in a competency framework to which the component, "
					+ "performance level or indicator applies: two references, to the competency object and to the "
					+ "framework."),
			Map.entry("Author", "An author of this component of the performance framework."),
			Map.entry("Reviewer", "A reviewer of this component of the performance framework."),
			Map.entry("Threshold", "A threshold score for a particular action or decision, such as progression in "
					+ "training or entrustment."),
			Map.entry("Description", "A description (one per language)."),
			Map.entry("Threshold/Description", "A description of the threshold."),
			Map.entry("Indicator/Description", "A description of the indicator or behavioral marker associated with "
					+ "this performance level."),
			Map.entry("MinimumAcceptableScore", "The minimum acceptable score associated with this threshold, on the "
					+ "scale of the component's performance levels."),
			Map.entry("AdditionalInformation", "Additional information, such as background, references, resources, "
					+ "assessment methods, examples and notes, about a component, performance level or indicator."),
			Map.entry("@position", "The position of this additional information in relation to other additional "
					+ "information with the same label; unique among those with the same label."),
			Map.entry("Label", "A label (one per language)."),
			Map.entry("AdditionalInformation/Label", "A label for this additional information. Recommended values: "
					+ "assessment method, background, example, note, reference, and resource."),
			Map.entry("PerformanceLevel/Label", "A label associated with this performance level, for example "
					+ "Aspirational."),
			Map.entry("Text", "The text of the additional information (one per language)."),
			Map.entry("ComponentReference", "The ID of a nested Component. A component has either component "
					+ "references or a performance level set."),
			Map.entry("PerformanceLevelSet", "A spectrum of performance levels, for a specific competency or to be "
					+ "applied generally across competencies."),
			Map.entry("PerformanceScaleReference", "The identifier of a PerformanceScale defined in this document: "
					+ "the scale used for this performance level set."),
			Map.entry("PerformanceLevel", "A single level of performance within the set; together the levels create "
					+ "a scale of performance with behavioral measures (at least two)."),
			Map.entry("DisplayOrder", "A positive integer indicating the order of display for this performance level "
					+ "in relation to the other levels of the set."),
			Map.entry("Score", "The score of this performance level: a single value or a range."),
			Map.entry("SingleValue", "A single score associated with this performance level."),
			Map.entry("Range", "A range of scores associated with this performance level."),
			Map.entry("MinScore", "The minimum score associated with this performance level; it may be lower or "
					+ "higher than MaxScore depending on how the performance scale is defined."),
			Map.entry("MaxScore", "The maximum score associated with this performance level; it may be higher or "
					+ "lower than MinScore depending on how the performance scale is defined."),
			Map.entry("Indicator", "An indicator or behavioral marker associated with this performance level, "
					+ "described in text or referenced from another document."));
}
