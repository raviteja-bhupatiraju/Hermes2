# Adding the content of a MedBiquitous standard

How the editor support of a MedBiquitous standard (other than Healthcare LOM) is written. Read
`docs/medbiq-expansion.md` first for the architecture.

## What already works for every standard

The schemas are bundled (`bundles/edu.louisiana.caai.medbiq.model/schemas/`), converted to dynamic EMF models
(`model/*.ecore`) and registered as document types (`MedbiqStandards.TYPES`). Without any further content, a
document of the standard can be opened, edited (pages derived from the model, the tree, the XML page), validated
against the schemas and the model constraints (editor, Problems view, `hermes validate`, language server) and
searched.

## What a standard adds

Everything a standard adds lives in **its own files**:

| What | Where |
| --- | --- |
| Titles, descriptions, form pages, rules, search fields, field help | `src/edu/louisiana/caai/medbiq/model/standards/<Class>.java` (implements `StandardContent`) |
| Template of new documents, per document type | `templates/<type id>.xml` |
| Sample documents | `samples/<standard folder>/*.xml` |
| Help topic | `bundles/edu.louisiana.caai.lom.help/html/standards/<topic>.html` |
| Context help of the form pages | `bundles/edu.louisiana.caai.lom.help/contexts_<standard>.xml` |
| Tests | `tests/edu.louisiana.caai.lom.tests/src/edu/louisiana/caai/lom/tests/<Standard>Test.java` |

(paths without a bundle are in `bundles/edu.louisiana.caai.medbiq.model/`).

### Templates

- Valid against the schemas (no errors with `tools/dev-hermes.sh validate`), as small as the schemas allow but
  meaningful: an author can start from it and sees what goes where.
- Placeholders: `${title}` (in the element that names the document, so that `hermes info` shows it as the title),
  `${language}`, `${date}`, `${dateTime}` (ISO 8601 with offset), `${id}` (a UUID).
- Keep `xsi:schemaLocation` with the published schema URL (`http://ns.medbiq.org/...xsd`); use the conventional
  namespace prefixes of the specification; a comment on the first line says what the template is.

### Samples

One to three fictional but realistic documents per document type (no real people, institutions may be fictional
or well-known public bodies), valid, showing the main structures of the standard. A comment at the top says they
are fictional samples created for Hermes 2. Samples that come with the standard (toolkits in `xsd/mirror/`) may be
included unchanged if they are valid (say so in the comment and keep the MedBiquitous copyright notice).

### `StandardContent`

- **Titles and descriptions**: from the element that names a document (`Paths.text(root, "Title")`), else a
  summary ("12 activity reports").
- **Pages** (`PageSpec`): group the content the way the specification presents it, in the order an author fills it
  in. Each page has sections; each section has fields given by element paths (local names, `@attribute`,
  `a/b/c`), relative to the section's element. The editor finds the elements in the model and picks widgets from
  their types: enumerations become choices, dates date pickers, text and numbers text fields (long texts multi-line
  with `PageSpec.text`), repeating simple values lists, repeating elements master/detail blocks (`repeat`, with the
  path of the text naming an item and the detail fields), nested elements groups (`group`); `auto` shows all
  content of an element. Embedded Healthcare LOM metadata (`lom`) gets the LOM fields (title, description,
  keywords, identifiers, contributors) automatically. Page ids are short and unique within the type; page icons are
  names of `bundles/edu.louisiana.caai.lom.editor/icons/pages/*.png` (general, educational, lifecycle, rights,
  relation, annotation, classification, technical, metametadata, healthcare-education, healthcare-asset, overview).
  Paths that don't exist in the model show a note, so check them against the schema (`model/*.ecore`,
  `schemas/`).
- **Rules**: only checks the specification states or that follow from the meaning of the data (references that
  must resolve, date ranges, counts that must match, required-by-the-spec content the schema leaves optional,
  recommended practice). Each problem: `new HlomProblem(Severity.ERROR|WARNING|INFO, Origin.RULES, message,
  offendingObject, null, -1, -1)`; messages are sentences that say what is wrong and what to do, quoting the
  specification's terms. The offending object places the problem in the text and the editor.
- **Search fields**: `FieldGroup`s with ids unique across all standards (prefix them, e.g. `ci-events`).
- **Help**: a text per element (and `@attribute`) from the specification, short (1–3 sentences). Quote the
  specification where it defines the element (it is © MedBiquitous, used under its license, as the HLOM help
  does), otherwise summarize.

### Help topic and contexts

- `html/standards/<topic>.html`: what the standard is for, its document types and versions, the structure of a
  document, how the editor's pages map to it, the rules Hermes checks, the samples and templates, and links to the
  specification. Same HTML conventions as the other topics (doctype, copyright comment, `../style.css`, h1).
- `contexts_<standard>.xml`: one context per form page, id `page_<type id>_<page id>` with `-` written `_` (for
  example `page_curriculum_inventory_v10_events`), with a description and links to the topic.

### Tests

JUnit 5 tests that the templates and samples are valid, that titles are found and that each rule reports a crafted
problem (and not a valid sample). Read samples and templates with `MedbiqSamples` (test helper).

## Checking your work without Maven

```sh
export JAVA_HOME=/c/Apps/Java/jdk-27
DEV_HERMES_OUT=<a folder of your own> tools/dev-hermes.sh validate bundles/edu.louisiana.caai.medbiq.model/samples/<folder>
DEV_HERMES_OUT=<a folder of your own> tools/dev-hermes.sh info <file>
```

compiles the bundle's sources (so it also reports compile errors) and runs the command line tool with them and
with the working copy's templates and models. The rules run as part of `validate`.

## Code conventions

Modern Java (records, `switch` with patterns, `var` where it helps, `List.of`, text blocks), tabs, lines up to
120 characters, the copyright header of the other files, Javadoc on public types and methods.
