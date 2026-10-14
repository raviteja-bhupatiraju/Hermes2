/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Which {@link ImportField} each CSV column is imported into ({@code null}: the column is ignored). Mappings
 * can be guessed from the header names with {@link #auto(List)}: exact and near-exact names, abbreviations,
 * prefixed names ({@code lom.general.title}, {@code hx:creditType}) and small typos are recognized.
 */
public final class ColumnMapping {

	/** The lowest score at which a header is mapped automatically. */
	public static final double THRESHOLD = 0.62;

	private final ImportField[] fields;
	private final double[] confidence;

	/** Creates a mapping that ignores all columns. */
	public ColumnMapping(int columns) {
		this.fields = new ImportField[columns];
		this.confidence = new double[columns];
	}

	/** Guesses the field of each column from its header. */
	public static ColumnMapping auto(List<String> headers) {
		ColumnMapping mapping = new ColumnMapping(headers.size());
		record Candidate(int column, ImportField field, double score) {
		}
		List<Candidate> candidates = new ArrayList<>();
		for (int column = 0; column < headers.size(); column++) {
			for (ImportField field : ImportField.values()) {
				double score = score(headers.get(column), field);
				if (score >= THRESHOLD) {
					candidates.add(new Candidate(column, field, score));
				}
			}
		}
		candidates.sort(Comparator.comparingDouble(Candidate::score).reversed()
				.thenComparingInt(Candidate::column));
		Set<ImportField> used = EnumSet.noneOf(ImportField.class);
		Set<Integer> assigned = new HashSet<>();
		for (Candidate candidate : candidates) {
			if (assigned.contains(candidate.column())
					|| (!candidate.field().repeatable() && used.contains(candidate.field()))) {
				continue;
			}
			mapping.fields[candidate.column()] = candidate.field();
			mapping.confidence[candidate.column()] = candidate.score();
			assigned.add(candidate.column());
			used.add(candidate.field());
		}
		return mapping;
	}

	/** How well a header names a field, from 0 (not at all) to 1 (exactly). */
	public static double score(String header, ImportField field) {
		String name = TermMatcher.normalize(header.replaceAll("([a-z])([A-Z])", "$1 $2"));
		if (name.isEmpty()) {
			return 0;
		}
		String compactName = name.replace(" ", "");
		double best = 0;
		List<String> aliases = new ArrayList<>(field.aliases());
		aliases.add(field.label());
		for (String alias : aliases) {
			String a = TermMatcher.normalize(alias);
			String compactAlias = a.replace(" ", "");
			double score;
			if (name.equals(a)) {
				score = 1.0;
			} else if (compactName.equals(compactAlias)) {
				score = 0.98;
			} else if (compactAlias.length() >= 5 && compactName.endsWith(compactAlias)) {
				// Prefixed names: "lom general title", "hx creditType", "course title".
				score = 0.8 + 0.15 * compactAlias.length() / compactName.length();
			} else if ((a.contains(" ") || a.length() >= 4) && containsWords(name, a)) {
				score = 0.7 + 0.2 * a.length() / name.length();
			} else if (name.length() >= 4 && containsWords(a, name)) {
				score = 0.55 + 0.2 * name.length() / a.length();
			} else {
				double similarity = similarity(compactName, compactAlias);
				score = compactAlias.length() >= 5 && similarity >= 0.8 ? 0.9 * similarity : jaccard(name, a);
			}
			best = Math.max(best, score);
		}
		return Math.min(1.0, best);
	}

	private static boolean containsWords(String text, String words) {
		return (" " + text + " ").contains(" " + words + " ");
	}

	private static double jaccard(String a, String b) {
		Set<String> first = new HashSet<>(Arrays.asList(a.split(" ")));
		Set<String> second = new HashSet<>(Arrays.asList(b.split(" ")));
		if (first.size() < 2 && second.size() < 2) {
			return 0;
		}
		Set<String> union = new HashSet<>(first);
		union.addAll(second);
		first.retainAll(second);
		double j = (double) first.size() / union.size();
		return j >= 0.5 ? 0.45 + 0.35 * j : 0;
	}

	/** Levenshtein similarity (1 - distance / longer length). */
	static double similarity(String a, String b) {
		if (a.equals(b)) {
			return 1;
		}
		int[] previous = new int[b.length() + 1];
		int[] current = new int[b.length() + 1];
		for (int j = 0; j <= b.length(); j++) {
			previous[j] = j;
		}
		for (int i = 1; i <= a.length(); i++) {
			current[0] = i;
			for (int j = 1; j <= b.length(); j++) {
				int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
				current[j] = Math.min(Math.min(current[j - 1] + 1, previous[j] + 1), previous[j - 1] + cost);
			}
			int[] swap = previous;
			previous = current;
			current = swap;
		}
		return 1.0 - (double) previous[b.length()] / Math.max(a.length(), b.length());
	}

	/** The number of columns. */
	public int size() {
		return fields.length;
	}

	/** The field of a column, or {@code null} if the column is ignored. */
	public ImportField get(int column) {
		return fields[column];
	}

	/** Maps a column to a field ({@code null} to ignore it). */
	public void set(int column, ImportField field) {
		fields[column] = field;
		confidence[column] = field == null ? 0 : 1;
	}

	/** How confident the automatic mapping of a column was (1 for manual mappings, 0 if ignored). */
	public double confidence(int column) {
		return confidence[column];
	}

	/** The columns mapped to a field. */
	public List<Integer> columns(ImportField field) {
		List<Integer> columns = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] == field) {
				columns.add(i);
			}
		}
		return columns;
	}

	/** Whether any column is mapped to the field. */
	public boolean isMapped(ImportField field) {
		return !columns(field).isEmpty();
	}

	/** The number of mapped columns. */
	public int mappedCount() {
		int count = 0;
		for (ImportField field : fields) {
			if (field != null) {
				count++;
			}
		}
		return count;
	}

	/** A copy of this mapping. */
	public ColumnMapping copy() {
		ColumnMapping copy = new ColumnMapping(fields.length);
		System.arraycopy(fields, 0, copy.fields, 0, fields.length);
		System.arraycopy(confidence, 0, copy.confidence, 0, confidence.length);
		return copy;
	}
}
