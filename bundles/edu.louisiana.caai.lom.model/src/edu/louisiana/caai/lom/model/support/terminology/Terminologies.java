/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.terminology;

import java.io.IOException;
import java.util.List;

import edu.louisiana.caai.lom.model.support.mesh.UmlsClient;

/**
 * The terminologies available offline. Their indexes ship with the application, so lookups need no network:
 * <ul>
 * <li>{@link #MESH}: Medical Subject Headings (NLM), all descriptors with entry terms, scope notes and trees</li>
 * <li>{@link #ICD_10_CM}: ICD-10-CM (NCHS), all chapters, blocks, categories and codes</li>
 * <li>{@link #ACGME}: the ACGME core competencies</li>
 * <li>{@link #BLOOM}: the revised Bloom's taxonomy (cognitive processes and knowledge dimensions)</li>
 * <li>{@link #AAMC_CI}: the AAMC/MedBiquitous Curriculum Inventory instructional and assessment methods and
 * resource types</li>
 * </ul>
 * SNOMED CT and LOINC require a license of each user: they are searched online with the user's UMLS API key
 * ({@link #SNOMED_CT}, {@link #LOINC}).
 */
public final class Terminologies {

	/** Medical Subject Headings. */
	public static final Terminology MESH = new Terminology("MeSH", "Medical Subject Headings", "mesh.tsv.gz",
			List.of("Medical Subject Headings", "MSH", "NLM MeSH"));

	/** ICD-10-CM. */
	public static final Terminology ICD_10_CM = new Terminology("ICD-10-CM",
			"International Classification of Diseases, Tenth Revision, Clinical Modification", "icd10cm.tsv.gz",
			List.of("ICD-10", "ICD10", "ICD10CM", "ICD-10 CM"));

	/** ACGME core competencies. */
	public static final Terminology ACGME = new Terminology("ACGME", "ACGME Core Competencies", "acgme.tsv.gz",
			List.of("ACGME Competencies", "ACGME Core Competency"));

	/** Revised Bloom's taxonomy. */
	public static final Terminology BLOOM = new Terminology("Bloom", "Bloom's Taxonomy (revised)", "bloom.tsv.gz",
			List.of("Bloom's Taxonomy", "Blooms Taxonomy", "Bloom's", "Revised Bloom's Taxonomy"));

	/** AAMC Curriculum Inventory standardized vocabulary. */
	public static final Terminology AAMC_CI = new Terminology("AAMC CI",
			"AAMC Curriculum Inventory", "aamc-ci.tsv.gz",
			List.of("AAMC Curriculum Inventory", "Curriculum Inventory", "AAMC",
					"Curriculum Inventory Standardized Vocabulary"));

	/** SNOMED CT (US Edition), searched online with the user's UMLS license (see {@link UmlsClient}). */
	public static final Terminology SNOMED_CT = online(UmlsClient.Source.SNOMED_CT, "SNOMED CT",
			"Systematized Nomenclature of Medicine Clinical Terms", List.of("SNOMED", "SNOMEDCT", "SNOMED-CT",
					"SNOMEDCT_US", "SCT"));

	/** LOINC, searched online with the user's UMLS license (see {@link UmlsClient}). */
	public static final Terminology LOINC = online(UmlsClient.Source.LOINC, "LOINC",
			"Logical Observation Identifiers Names and Codes", List.of("LNC"));

	private static final List<Terminology> ALL = List.of(MESH, ICD_10_CM, ACGME, BLOOM, AAMC_CI, SNOMED_CT, LOINC);

	private static Terminology online(UmlsClient.Source source, String id, String title, List<String> aliases) {
		return new Terminology(id, title, aliases, new Terminology.Online() {
			@Override
			public boolean isAvailable() {
				return UmlsClient.shared().isConfigured();
			}

			@Override
			public List<Concept> search(String text, int limit) throws IOException {
				return UmlsClient.shared().search(source, text, limit);
			}

			@Override
			public String source() {
				return "UMLS Terminology Services (U.S. National Library of Medicine)";
			}

			@Override
			public String license() {
				return "Used under your own UMLS license; " + id + " content is not included in Hermes 2.";
			}
		});
	}

	private Terminologies() {
	}

	/** All terminologies (the online ones also when their service isn't set up). */
	public static List<Terminology> all() {
		return ALL;
	}

	/** The terminologies that can be used now: the offline ones, and the online ones with a license key. */
	public static List<Terminology> available() {
		return ALL.stream().filter(Terminology::isAvailable).toList();
	}

	/** The terminologies used to code keywords ({@code hx:source}): MeSH, ICD-10-CM, and SNOMED CT and LOINC online. */
	public static List<Terminology> keywordTerminologies() {
		return List.of(MESH, ICD_10_CM, SNOMED_CT, LOINC).stream().filter(Terminology::isAvailable).toList();
	}

	/** The terminology a source value (keyword source, taxon path source) names, or {@code null}. */
	public static Terminology forSource(String source) {
		for (Terminology terminology : ALL) {
			if (terminology.isNamedBy(source)) {
				return terminology;
			}
		}
		return null;
	}

	/** The terminology with an id, or {@code null}. */
	public static Terminology byId(String id) {
		for (Terminology terminology : ALL) {
			if (terminology.getId().equals(id)) {
				return terminology;
			}
		}
		return null;
	}
}
