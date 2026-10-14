#!/usr/bin/env python3
"""Builds the offline terminology indexes of Hermes 2 (bundles/edu.louisiana.caai.lom.model/terminology/*.tsv.gz;
MeSH and ICD-10-CM in bundles/edu.louisiana.caai.terminology.mesh and .icd10cm).

Sources (download them first; see README.md in this folder):
  --mesh   desc<year>.gz     MeSH descriptors, https://nlmpubs.nlm.nih.gov/projects/mesh/MESH_FILES/xmlmesh/
  --icd    icd10cm-table and index-<year>.zip and icd10cm-Code Descriptions-<year>.zip,
           https://ftp.cdc.gov/pub/Health_Statistics/NCHS/Publications/ICD10CM/<year>/
  --aamc   text of the Curriculum Inventory vocabulary PDF (March 2016),
           https://www.medbiq.org/media/3241/download?attachment, extracted with pypdf
ACGME core competencies and the revised Bloom's taxonomy are small and defined in this script.

Output format (UTF-8, gzip): header lines "#key<TAB>value" (name, title, version, source, license), then one
concept per line:
  code  label  synonyms  parents  tree-numbers  definition  related  kind
List columns are separated by "|"; text never contains tabs, line breaks or "|".
"""
import argparse
import gzip
import os
import re
import sys
import xml.etree.ElementTree as ET
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
OUT = os.path.join(os.path.dirname(HERE), "terminology")
# MeSH and ICD-10-CM are released every year: they are fragments of their own, versioned and updated separately.
BUNDLES = os.path.dirname(os.path.dirname(HERE))
OWN_BUNDLE = {"mesh": "edu.louisiana.caai.terminology.mesh", "icd10cm": "edu.louisiana.caai.terminology.icd10cm"}


def clean(text):
    return re.sub(r"\s+", " ", (text or "").replace("|", "/")).strip()


def write(name, header, concepts):
    folder = os.path.join(BUNDLES, OWN_BUNDLE[name], "terminology") if name in OWN_BUNDLE else OUT
    os.makedirs(folder, exist_ok=True)
    path = os.path.join(folder, name + ".tsv.gz")
    with gzip.open(path, "wt", encoding="utf-8", compresslevel=9, newline="\n") as out:
        for key, value in header.items():
            out.write(f"#{key}\t{clean(value)}\n")
        for c in concepts:
            row = [c["code"], c["label"], "|".join(map(clean, c.get("synonyms", []))),
                   "|".join(c.get("parents", [])), "|".join(c.get("tree", [])), c.get("definition", ""),
                   "|".join(c.get("related", [])), c.get("kind", "")]
            out.write("\t".join(clean(v) if i not in (2, 3, 4, 6) else v for i, v in enumerate(row)) + "\n")
    print(f"{name}: {len(concepts)} concepts, {os.path.getsize(path) / 1e6:.1f} MB")


# --------------------------------------------------------------------------------------------------------------
# MeSH
# --------------------------------------------------------------------------------------------------------------

def mesh(path):
    concepts = []
    year = re.search(r"(\d{4})", os.path.basename(path))
    with gzip.open(path, "rb") as stream:
        for _, record in ET.iterparse(stream, events=("end",)):
            if record.tag != "DescriptorRecord":
                continue
            ui = record.findtext("DescriptorUI")
            label = clean(record.findtext("DescriptorName/String"))
            synonyms, definition = [], ""
            for concept in record.iterfind("ConceptList/Concept"):
                if concept.get("PreferredConceptYN") == "Y":
                    definition = clean(concept.findtext("ScopeNote"))
                for term in concept.iterfind("TermList/Term/String"):
                    text = clean(term.text)
                    if text and text != label and text not in synonyms:
                        synonyms.append(text)
            tree = [t.text.strip() for t in record.iterfind("TreeNumberList/TreeNumber")]
            related = [r.text.strip() for r in record.iterfind(
                "SeeRelatedList/SeeRelatedDescriptor/DescriptorReferredTo/DescriptorUI")]
            concepts.append({"code": ui, "label": label, "synonyms": synonyms, "tree": sorted(tree),
                             "definition": definition, "related": related, "kind": "descriptor"})
            record.clear()
    # Parents from the tree numbers (C14.280.647 -> C14.280).
    by_tree = {t: c["code"] for c in concepts for t in c["tree"]}
    for c in concepts:
        parents = []
        for t in c["tree"]:
            parent = by_tree.get(t.rsplit(".", 1)[0]) if "." in t else None
            if parent and parent not in parents:
                parents.append(parent)
        c["parents"] = parents
    write("mesh", {
        "name": "MeSH",
        "title": "Medical Subject Headings",
        "version": year.group(1) if year else "",
        "source": "U.S. National Library of Medicine, https://www.nlm.nih.gov/mesh/",
        "license": "Courtesy of the U.S. National Library of Medicine. MeSH is used under the NLM terms and conditions "
                   "(https://www.nlm.nih.gov/databases/download/terms_and_conditions.html); this index contains a "
                   "subset of the descriptor data (names, entry terms, scope notes, tree numbers, see-also references) "
                   "and is not an official NLM product.",
    }, concepts)


# --------------------------------------------------------------------------------------------------------------
# ICD-10-CM
# --------------------------------------------------------------------------------------------------------------

def icd(tabular_zip, codes_zip):
    tab = zipfile.ZipFile(tabular_zip)
    name = next(n for n in tab.namelist() if re.search(r"tabular-\d{4}\.xml$", n))
    root = ET.fromstring(tab.read(name))
    year = root.findtext("version")
    codes = zipfile.ZipFile(codes_zip)
    codes_name = next(n for n in codes.namelist() if re.search(r"icd10cm-codes-\d{4}\.txt$", n))
    billable = {}
    for line in codes.read(codes_name).decode("utf-8", "replace").splitlines():
        code = line[:8].strip()
        if code:
            billable[code] = clean(line[8:])

    concepts = []

    def notes(element, tag):
        return [clean(n.text) for n in element.iterfind(f"{tag}/note") if clean(n.text)]

    def diag(element, parent, chapter):
        code = element.findtext("name").strip()
        compact = code.replace(".", "")
        concepts.append({"code": code, "label": clean(element.findtext("desc")),
                         "synonyms": notes(element, "inclusionTerm"), "parents": [parent], "tree": [chapter],
                         "definition": "; ".join(notes(element, "includes")),
                         "kind": "billable code" if compact in billable else "category"})
        for child in element.iterfind("diag"):
            diag(child, code, chapter)

    for chapter in root.iterfind("chapter"):
        number = chapter.findtext("name").strip()
        chapter_code = "Chapter " + number
        concepts.append({"code": chapter_code, "label": clean(chapter.findtext("desc")), "kind": "chapter",
                         "tree": [chapter_code]})
        for section in chapter.iterfind("section"):
            section_code = section.get("id")
            concepts.append({"code": section_code, "label": clean(section.findtext("desc")),
                             "parents": [chapter_code], "tree": [chapter_code], "kind": "block"})
            for element in section.iterfind("diag"):
                diag(element, section_code, chapter_code)
    # Codes with a 7th character (and placeholder x) are defined by rules in the tabular list; their titles come
    # from the code file. The parent is the longest listed code they start with.
    listed = {c["code"].replace(".", ""): c for c in concepts}
    for compact, title in billable.items():
        if compact in listed:
            continue
        prefix = compact[:-1]
        while prefix and prefix not in listed:
            prefix = prefix[:-1]
        parent = listed.get(prefix)
        code = compact[:3] + ("." + compact[3:] if len(compact) > 3 else "")
        concept = {"code": code, "label": title, "parents": [parent["code"]] if parent else [],
                   "tree": parent["tree"] if parent else [], "kind": "billable code"}
        concepts.append(concept)
        listed[compact] = concept
    write("icd10cm", {
        "name": "ICD-10-CM",
        "title": "International Classification of Diseases, Tenth Revision, Clinical Modification",
        "version": "FY" + year,
        "source": "National Center for Health Statistics (CDC), https://www.cdc.gov/nchs/icd/icd-10-cm/",
        "license": "ICD-10-CM is published by NCHS and is in the public domain in the United States (ICD-10 "
                   "copyright World Health Organization). Codes, titles and inclusion terms from the tabular list.",
    }, concepts)


# --------------------------------------------------------------------------------------------------------------
# AAMC / MedBiquitous Curriculum Inventory standardized vocabulary (March 2016)
# --------------------------------------------------------------------------------------------------------------

HEADER_NOISE = re.compile(r"Page \d+ of \d+ MedBiquitous Curriculum Inventory Working Group Curriculum Inventory "
                          r"Standards, March 2016 Jump to: Terms List Instructional Methods Assessment Methods Resources")


def aamc(text_path):
    text = open(text_path, encoding="utf-8").read()
    head, _, body = text.partition("Page 2 of 9")
    groups = {"Instructional Methods": "IM", "Assessment Methods": "AM", "Resource Types": "RE"}
    terms = []
    current = None
    pending = None
    for raw in head.splitlines():
        line = raw.strip()
        if line in groups:
            current = groups[line]
            continue
        match = re.match(r"^(IM|AM|RE)(\d{3})\s*[:;]\s*(.*)$", line)
        if match:
            pending = [match.group(1) + match.group(2), match.group(3).strip(), current]
            terms.append(pending)
        elif pending and line and current:
            pending[1] += " " + line  # wrapped term
    body = HEADER_NOISE.sub(" ", " ".join(("Page 2 of 9" + body).split()))
    # Table headings, e.g. "Resource Types Resource Definition / More information".
    body = re.sub(r"(?:Instructional Methods Instructional Method|Assessment Methods Assessment Method|"
                  r"Resource Types Resource) Definition / More information", " ", body)
    body = body.replace("–", "-").replace("—", "-")

    def pattern(term):
        words = re.split(r"\s+|(?<=/)|(?=/)", term.replace("(", " ( ").replace(")", " ) "))
        words = [w for w in words if w.strip()]
        # Commas and hyphen spacing vary between the term list and the definition tables.
        parts = [re.escape(w.rstrip(",")).replace(r"\-", r"\s*-\s*") + ",?" for w in words]
        return re.compile(r"\s*".join(parts), re.IGNORECASE)

    def entry(regex, start):
        """The first occurrence at or after start that begins an entry (not a mention in a list of terms)."""
        for m in regex.finditer(body, start):
            preceding = body[max(0, m.start() - 12):m.start()].rstrip()
            if preceding[-1:] not in (";", ":", ",", "(") and not re.search(r"\b(?:or|and|See also)$", preceding):
                return m
        return None

    positions = []
    cursor = 0
    group_start = {}
    for code, term, group in terms:
        term = re.sub(r"\s+", " ", term).replace(" EHR/EMR)", " (EHR/EMR)").replace("–", "-").strip()
        variants = [term, term.replace("[", "(").replace("]", ")"), term.replace("(", "[").replace(")", "]"),
                    re.sub(r"\s*[\[(].*?[\])]", "", term)]
        found = None
        # Mostly in list order; a few entries are swapped, so fall back to the start of the group's table.
        for start in (cursor, group_start.get(group, 0)):
            for variant in variants:
                found = entry(pattern(variant), start)
                if found:
                    break
            if found:
                break
        if found:
            group_start.setdefault(group, found.start())
            cursor = max(cursor, found.end())
        positions.append((found.start() if found else None, found.end() if found else None, code, term, group))
    starts = sorted(p[0] for p in positions if p[0] is not None)
    labels = {"IM": "instructional method", "AM": "assessment method", "RE": "resource type"}
    concepts, missing = [], []
    for start, end, code, term, group in positions:
        definition, synonyms = "", []
        if start is not None:
            following = [s for s in starts if s > start]
            chunk = body[end:following[0] if following else len(body)]
            parts = re.split(r"(Synonymous with/Includes:|Synonyms:|Does not include:|Note\(s\):|Note:)", chunk)
            # A wrapped part of the heading, e.g. "(Lab)", may precede the definition.
            definition = re.sub(r"^\([^)]{1,12}\)\s*", "", parts[0].strip())
            for i in range(1, len(parts) - 1, 2):
                if parts[i] in ("Synonymous with/Includes:", "Synonyms:"):
                    synonyms += [s.strip(" /") for s in re.split(r";|, or |,", parts[i + 1]) if s.strip(" /")]
        if not definition:
            missing.append(code)
        concepts.append({"code": code, "label": term, "synonyms": synonyms, "definition": definition,
                         "parents": [group], "kind": labels[group]})
    for group, code in (("Instructional Methods", "IM"), ("Assessment Methods", "AM"), ("Resource Types", "RE")):
        concepts.append({"code": code, "label": group, "kind": "group"})
    if missing:
        print("aamc: no definition found for " + ", ".join(missing), file=sys.stderr)
    write("aamc-ci", {
        "name": "AAMC CI",
        "title": "Curriculum Inventory Standardized Instructional and Assessment Methods and Resource Types",
        "version": "March 2016",
        "source": "MedBiquitous Curriculum Inventory Working Group Standardized Vocabulary Subcommittee (2016), "
                  "Association of American Medical Colleges, https://www.medbiq.org/standards/curriculum-data-exchange",
        "license": "Copyright 2016 Association of American Medical Colleges. All Rights Reserved. http://www.medbiq.org "
                   "- used under the MedBiquitous Standards Public License "
                   "(https://www.medbiq.org/standards/medbiquitous-standards-public-license-and-terms-use). Modified: "
                   "the published tables were converted to a searchable index; wording is unchanged.",
    }, concepts)


# --------------------------------------------------------------------------------------------------------------
# ACGME core competencies, Bloom's taxonomy (small, defined here)
# --------------------------------------------------------------------------------------------------------------

def acgme():
    competencies = [
        ("PC", "Patient Care and Procedural Skills", ["Patient Care"],
         "Provide patient care that is compassionate, appropriate, and effective for the treatment of health "
         "problems and the promotion of health, and perform the procedures required for the specialty."),
        ("MK", "Medical Knowledge", [],
         "Demonstrate knowledge of established and evolving biomedical, clinical, epidemiological and social-"
         "behavioral sciences, and apply this knowledge to patient care."),
        ("PBLI", "Practice-based Learning and Improvement", ["Practice-Based Learning"],
         "Investigate and evaluate one's care of patients, appraise and assimilate scientific evidence, and "
         "continuously improve patient care based on constant self-evaluation and lifelong learning."),
        ("ICS", "Interpersonal and Communication Skills", ["Communication Skills"],
         "Demonstrate interpersonal and communication skills that result in the effective exchange of information "
         "and collaboration with patients, their families, and health professionals."),
        ("PROF", "Professionalism", [],
         "Demonstrate a commitment to carrying out professional responsibilities and an adherence to ethical "
         "principles."),
        ("SBP", "Systems-based Practice", ["Systems-Based Practice"],
         "Demonstrate an awareness of and responsiveness to the larger context and system of health care, including "
         "the social determinants of health, and the ability to call effectively on other resources to provide "
         "optimal health care."),
    ]
    # Codes are the letters a-f in the order of the ACGME Outcome Project, as in the taxon paths of the HLOM
    # specification's examples (b = Medical Knowledge); the usual abbreviations are kept as synonyms.
    write("acgme", {
        "name": "ACGME",
        "title": "ACGME Core Competencies",
        "version": "Outcome Project order (as in the HLOM specification)",
        "source": "Accreditation Council for Graduate Medical Education, https://www.acgme.org/",
        "license": "The six core competencies of the ACGME, coded a-f as in the HLOM specification's examples; "
                   "descriptions summarized.",
    }, [{"code": "abcdef"[i], "label": l, "synonyms": [c] + s, "definition": d, "kind": "competency"}
        for i, (c, l, s, d) in enumerate(competencies)])


def bloom():
    processes = [
        ("1", "Remember", "Retrieve relevant knowledge from long-term memory.",
         [("1.1", "Recognizing", ["Identifying"]), ("1.2", "Recalling", ["Retrieving"])]),
        ("2", "Understand", "Construct meaning from instructional messages, including oral, written, and graphic "
         "communication.",
         [("2.1", "Interpreting", ["Clarifying", "Paraphrasing", "Representing", "Translating"]),
          ("2.2", "Exemplifying", ["Illustrating", "Instantiating"]),
          ("2.3", "Classifying", ["Categorizing", "Subsuming"]),
          ("2.4", "Summarizing", ["Abstracting", "Generalizing"]),
          ("2.5", "Inferring", ["Concluding", "Extrapolating", "Interpolating", "Predicting"]),
          ("2.6", "Comparing", ["Contrasting", "Mapping", "Matching"]),
          ("2.7", "Explaining", ["Constructing models"])]),
        ("3", "Apply", "Carry out or use a procedure in a given situation.",
         [("3.1", "Executing", ["Carrying out"]), ("3.2", "Implementing", ["Using"])]),
        ("4", "Analyze", "Break material into its constituent parts and determine how the parts relate to one another "
         "and to an overall structure or purpose.",
         [("4.1", "Differentiating", ["Discriminating", "Distinguishing", "Focusing", "Selecting"]),
          ("4.2", "Organizing", ["Finding coherence", "Integrating", "Outlining", "Parsing", "Structuring"]),
          ("4.3", "Attributing", ["Deconstructing"])]),
        ("5", "Evaluate", "Make judgments based on criteria and standards.",
         [("5.1", "Checking", ["Coordinating", "Detecting", "Monitoring", "Testing"]),
          ("5.2", "Critiquing", ["Judging"])]),
        ("6", "Create", "Put elements together to form a coherent or functional whole; reorganize elements into a new "
         "pattern or structure.",
         [("6.1", "Generating", ["Hypothesizing"]), ("6.2", "Planning", ["Designing"]),
          ("6.3", "Producing", ["Constructing"])]),
    ]
    knowledge = [
        ("A", "Factual Knowledge", "The basic elements students must know to be acquainted with a discipline or solve "
         "problems in it."),
        ("B", "Conceptual Knowledge", "The interrelationships among the basic elements within a larger structure that "
         "enable them to function together."),
        ("C", "Procedural Knowledge", "How to do something, methods of inquiry, and criteria for using skills, "
         "algorithms, techniques, and methods."),
        ("D", "Metacognitive Knowledge", "Knowledge of cognition in general as well as awareness and knowledge of one's "
         "own cognition."),
    ]
    concepts = []
    for code, label, definition, subs in processes:
        concepts.append({"code": code, "label": label, "definition": definition, "kind": "cognitive process"})
        for sub, sub_label, synonyms in subs:
            concepts.append({"code": sub, "label": sub_label, "synonyms": synonyms, "parents": [code],
                             "kind": "cognitive process"})
    for code, label, definition in knowledge:
        concepts.append({"code": code, "label": label, "definition": definition, "kind": "knowledge dimension"})
    write("bloom", {
        "name": "Bloom",
        "title": "Bloom's Taxonomy (revised)",
        "version": "Anderson & Krathwohl, 2001",
        "source": "Anderson, L. W., & Krathwohl, D. R. (Eds.) (2001). A Taxonomy for Learning, Teaching, and "
                  "Assessing: A Revision of Bloom's Taxonomy of Educational Objectives. Longman.",
        "license": "Category names and codes of the revised taxonomy; descriptions summarized.",
    }, concepts)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--mesh")
    parser.add_argument("--icd-tabular")
    parser.add_argument("--icd-codes")
    parser.add_argument("--aamc")
    args = parser.parse_args()
    os.makedirs(OUT, exist_ok=True)
    if args.mesh:
        mesh(args.mesh)
    if args.icd_tabular and args.icd_codes:
        icd(args.icd_tabular, args.icd_codes)
    if args.aamc:
        aamc(args.aamc)
    acgme()
    bloom()


if __name__ == "__main__":
    main()
