# MedBiquitous Editor: covering all published MedBiquitous standards

Hermes 2 started as an editor for Healthcare LOM (HLOM) v1. This note describes how the other published
MedBiquitous standards are added, so that every standard gets a full editor (forms, validation with rules and quick
fixes, templates, overview, search, JSON, help) and the application becomes the *MedBiquitous Editor*.

## Standards and versions

Only versions MedBiquitous publishes as standards are supported (working drafts are not). Schemas come from
<http://ns.medbiq.org/> (mirrored in `xsd/mirror/`, including files only the Wayback Machine still has).

| Standard | Version(s) | Root element | Namespace |
| --- | --- | --- | --- |
| Healthcare LOM | 1.0 | `lom` | `http://ltsc.ieee.org/xsd/LOM` (+ `hx`) |
| Curriculum Inventory | 1.0, "v10" (AAMC 2022 point release) | `CurriculumInventory` | `…/curriculuminventory/v1/`, `…/v10/` |
| Competency Framework | 1.0 | `CompetencyFramework` | `…/competencyframework/v1/` |
| Competency Object | 1.0 | `CompetencyObject` | `…/competencyobject/v1/` |
| Performance Framework | 1.0 | `PerformanceFramework` | `…/performanceframework/v1/` |
| Activity Report | 1.0, 2.0 (point release) | `ActivityReports` | `…/activityreport/v1/`, `…/v2/` |
| Medical Education Metrics | 1.0 (metrics), Survey Items 1.0 | `MedicalEducationMetrics`, `SurveyItems` | `…/metrics/v1/`, `…/surveyitem/v1/` |
| Healthcare Professional Profile | 1.0 | `Members` (`Name`, `Address`) | `…/member/v1/` |
| Virtual Patient | 1.0 | `VirtualPatientData`, `ActivityModel`, `DataAvailabilityModel` | `…/virtualpatientdata/v1/`, … |
| Disclosure of Interests | 2.0 | `FinancialInterestPackages` | `…/Disclosure/v2/` — **pending**: imports `common/v3/common.xsd`, which is no longer published |
| Educational Achievement | 1.0 | `EducationalAchievement` | `…/educationalachievement/v1/` — **pending**: imports PESC CoreMain 1.12.0 and AcademicRecord 1.7.0, which PESC no longer publishes |

Not included: working drafts (HLOM 2.0, Curriculum Inventory 2.0, Professional Profile 2.0, Disclosure 1.0,
MEMS 2.0), Educational Trajectory (a LEAP2A/Atom profile with a small extension schema), xAPI (charter only) and
the web service descriptions (WSDL).

## Models

`tools/schematool` converts all root schemas in one run of the EMF XSD importer (so every namespace has one
package) and writes `bundles/edu.louisiana.caai.medbiq.model/model/*.ecore`; it also copies the schemas and
everything they import to `schemas/` (laid out by URL host and path). References into the HLOM namespaces point to
the generated HLOM packages, so documents mix HLOM elements (`lom:lom` in Curriculum Inventory, Competency
Object, Activity Report…) with the generated HLOM model.

The new standards use **dynamic EMF**: the `.ecore` models are loaded at start and registered by namespace; no
code is generated. All editing infrastructure (forms, tree, Properties, JSON, search, diff) works reflectively.

## Shared infrastructure

- `DocumentTypes` (model support): the registry of document types — HLOM and each new standard — with detection
  (root element and namespace), loading, schema validation (JAXP, bundled schemas, offline), rules, quick fixes,
  summaries, completeness, templates, search field groups and field help. `DocumentCheck`, the builder, the
  library, search, the command line tool and the language server dispatch through it.
- A declarative form engine: pages → sections → fields by element path, rendered from the EMF metadata (text,
  multi-line, numbers, dates, enumerations, booleans, nested single elements, master/detail lists), with the
  binding, undo and validation markers of the HLOM pages.
- The generic editor hosts the overview, the form pages, tree, XML and JSON pages of any document type.

## Compatibility

Bundle ids, the project nature and the workspace location stay as they are (`edu.louisiana.caai.lom.*`, `.project`
files of existing projects reference the nature id); only the product name, branding and texts change.
