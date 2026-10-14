/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.fixes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

import edu.louisiana.caai.lom.model.address.AddressType;
import edu.louisiana.caai.lom.model.healthcare.CreditsType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareFactory;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.lom.MetadataSchema;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;

/**
 * The built-in fixes. Each fix recognizes its problem by the problem's element and a key phrase of the message
 * and re-checks the model state, so a fix never applies once its problem is gone.
 */
final class StandardFixes {

	private StandardFixes() {
	}

	/** The fixes that may apply to a problem (not yet filtered by {@link HlomFix#appliesTo}). */
	static List<HlomFix> candidates(HlomProblem problem) {
		List<HlomFix> fixes = new ArrayList<>();
		fixes.add(new MetadataSchemaFix(HlomDocuments.LOM_METADATA_SCHEMA));
		fixes.add(new MetadataSchemaFix(HlomDocuments.HEALTHCARE_METADATA_SCHEMA));
		fixes.add(new HealthcareMetadataFix());
		if (problem.object() instanceof LangString) {
			fixes.add(new LanguageFix(HlomFixes.documentLanguage(HlomFixes.rootOf(problem.object()))));
		}
		fixes.add(new RemoveCreditsFix());
		fixes.add(new CreditsAvailableFix(YesNoType.YES));
		fixes.add(new AddCreditsFix());
		fixes.add(new CreditsAvailableFix(YesNoType.NO));
		fixes.add(new SwapActivityDatesFix());
		fixes.add(new SwapCreditDatesFix());
		fixes.addAll(NAVIGATION);
		return fixes;
	}

	/** Whether the message contains all phrases (ignoring case). */
	static boolean mentions(HlomProblem problem, String... phrases) {
		String message = problem.message() == null ? "" : problem.message().toLowerCase(Locale.ROOT);
		for (String phrase : phrases) {
			if (!message.contains(phrase.toLowerCase(Locale.ROOT))) {
				return false;
			}
		}
		return true;
	}

	/** Base class holding label, description and the "Fix All" flag. */
	abstract static sealed class Fix implements HlomFix {
		private final String label;
		private final String description;
		private final boolean automatic;

		Fix(String label, String description, boolean automatic) {
			this.label = label;
			this.description = description;
			this.automatic = automatic;
		}

		@Override
		public String label() {
			return label;
		}

		@Override
		public String description() {
			return description;
		}

		@Override
		public boolean isAutomatic() {
			return automatic;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Meta-Metadata and healthcareMetadata
	// ----------------------------------------------------------------------------------------------------

	/** Declares a missing metadata schema in Meta-Metadata (creating Meta-Metadata if necessary). */
	static final class MetadataSchemaFix extends Fix {
		private final String schema;

		MetadataSchemaFix(String schema) {
			super("Declare metadata schema " + schema,
					"Adds <metadataSchema>" + schema + "</metadataSchema> to Meta-Metadata, as every Healthcare LOM "
							+ "record must declare conformance with LOM v1.0 and Healthcare LOM v1.",
					true);
			this.schema = schema;
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			Lom lom = lomOf(problem.object());
			return mentions(problem, "metadata schema", "\"" + schema + "\"") && lom != null && !declared(lom);
		}

		private boolean declared(Lom lom) {
			return !lom.getMetaMetadata().isEmpty() && lom.getMetaMetadata().getFirst().getMetadataSchema().stream()
					.anyMatch(s -> s.getValue() != null && schema.equals(s.getValue().trim()));
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			Lom lom = root.getLom();
			if (lom == null || declared(lom)) {
				return;
			}
			if (lom.getMetaMetadata().isEmpty()) {
				lom.getMetaMetadata().add(LomFactory.eINSTANCE.createMetaMetadata());
			}
			MetaMetadata metaMetadata = lom.getMetaMetadata().getFirst();
			MetadataSchema element = LomFactory.eINSTANCE.createMetadataSchema();
			element.setValue(schema);
			metaMetadata.getMetadataSchema().add(element);
		}

		@Override
		public EObject target(HlomProblem problem) {
			Lom lom = lomOf(problem.object());
			return lom != null && !lom.getMetaMetadata().isEmpty() ? lom.getMetaMetadata().getFirst() : problem.object();
		}
	}

	/** Adds the required (empty) healthcareMetadata element. */
	static final class HealthcareMetadataFix extends Fix {
		HealthcareMetadataFix() {
			super("Add healthcareMetadata",
					"Adds the healthcareMetadata element that every Healthcare LOM record must contain once; "
							+ "fill in the Healthcare Education or Healthcare Asset pages afterwards.",
					true);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			Lom lom = lomOf(problem.object());
			return mentions(problem, "healthcareMetadata", "must occur") && lom != null
					&& lom.getHealthcareMetadata().isEmpty();
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			Lom lom = root.getLom();
			if (lom != null && lom.getHealthcareMetadata().isEmpty()) {
				lom.getHealthcareMetadata().add(HealthcareFactory.eINSTANCE.createHealthcareMetadataType());
			}
		}

		@Override
		public EObject target(HlomProblem problem) {
			Lom lom = lomOf(problem.object());
			return lom != null && !lom.getHealthcareMetadata().isEmpty() ? lom.getHealthcareMetadata().getFirst()
					: problem.object();
		}
	}

	static Lom lomOf(EObject object) {
		DocumentRoot root = HlomFixes.rootOf(object);
		return root != null ? root.getLom() : null;
	}

	// ----------------------------------------------------------------------------------------------------
	// Language
	// ----------------------------------------------------------------------------------------------------

	/** Tags a text without language with the document language. */
	static final class LanguageFix extends Fix {
		LanguageFix(String language) {
			super("Set the language to " + language,
					"Tags the text with the document language (" + language
							+ "), taken from General.Language or Meta-Metadata.Language.",
					true);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return problem.object() instanceof LangString string && isBlank(string.getLanguage())
					&& mentions(problem, "no language");
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (problem.object() instanceof LangString string && isBlank(string.getLanguage())) {
				string.setLanguage(HlomFixes.documentLanguage(root));
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Credits
	// ----------------------------------------------------------------------------------------------------

	static boolean is(HealthcareEducationType education, YesNoType value) {
		return education.isSetCreditsAvailable() && education.getCreditsAvailable() == value;
	}

	/** "Credits are marked as not available, but credit information is given." */
	static boolean creditsNotAvailableButGiven(HlomProblem problem) {
		return problem.object() instanceof HealthcareEducationType education
				&& mentions(problem, "credit information", "not available") && is(education, YesNoType.NO)
				&& !education.getCredits().isEmpty();
	}

	/** "Credits are available, but no credit information is given." */
	static boolean creditsAvailableButMissing(HlomProblem problem) {
		return problem.object() instanceof HealthcareEducationType education
				&& mentions(problem, "credit information") && !mentions(problem, "not available")
				&& is(education, YesNoType.YES) && education.getCredits().isEmpty();
	}

	/** Removes all credit entries. */
	static final class RemoveCreditsFix extends Fix {
		RemoveCreditsFix() {
			super("Remove the credit information", "Deletes all credit entries, since credits are not available.",
					false);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return creditsNotAvailableButGiven(problem);
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (creditsNotAvailableButGiven(problem)) {
				((HealthcareEducationType) problem.object()).getCredits().clear();
			}
		}
	}

	/** Sets Credits Available to match the credit entries. */
	static final class CreditsAvailableFix extends Fix {
		private final YesNoType value;

		CreditsAvailableFix(YesNoType value) {
			super(value == YesNoType.YES ? "Mark credits as available" : "Mark credits as not available",
					value == YesNoType.YES ? "Sets Credits Available to \"yes\" and keeps the credit entries."
							: "Sets Credits Available to \"no\", since the activity offers no credits.",
					false);
			this.value = value;
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return value == YesNoType.YES ? creditsNotAvailableButGiven(problem) : creditsAvailableButMissing(problem);
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (appliesTo(problem)) {
				((HealthcareEducationType) problem.object()).setCreditsAvailable(value);
			}
		}
	}

	/** Adds an empty credit entry for the author to complete. */
	static final class AddCreditsFix extends Fix {
		AddCreditsFix() {
			super("Add a credit entry",
					"Adds an empty credit entry; enter the accrediting body, credit type and number of credits.",
					false);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return creditsAvailableButMissing(problem);
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (creditsAvailableButMissing(problem)) {
				((HealthcareEducationType) problem.object()).getCredits()
						.add(HealthcareFactory.eINSTANCE.createCreditsType());
			}
		}

		@Override
		public EObject target(HlomProblem problem) {
			if (problem.object() instanceof HealthcareEducationType education && !education.getCredits().isEmpty()) {
				return education.getCredits().getFirst();
			}
			return problem.object();
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Dates
	// ----------------------------------------------------------------------------------------------------

	static boolean after(XMLGregorianCalendar first, XMLGregorianCalendar second) {
		return first != null && second != null && first.compare(second) > 0;
	}

	/** Swaps the activity's start and end date/time. */
	static final class SwapActivityDatesFix extends Fix {
		SwapActivityDatesFix() {
			super("Swap start and end date", "Exchanges the activity's start and end date/time, which were entered "
					+ "in the wrong order.", true);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return problem.object() instanceof HealthcareEducationType education && mentions(problem, "starts after")
					&& after(education.getStartDateTime(), education.getEndDateTime());
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (appliesTo(problem)) {
				HealthcareEducationType education = (HealthcareEducationType) problem.object();
				XMLGregorianCalendar start = education.getStartDateTime();
				education.setStartDateTime(education.getEndDateTime());
				education.setEndDateTime(start);
			}
		}
	}

	/** Swaps a credit entry's release and expiration date. */
	static final class SwapCreditDatesFix extends Fix {
		SwapCreditDatesFix() {
			super("Swap release and expiration date", "Exchanges the credit release and expiration dates, which "
					+ "were entered in the wrong order.", true);
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return problem.object() instanceof CreditsType credits && mentions(problem, "release date")
					&& after(credits.getReleaseDate(), credits.getExpirationDate());
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			if (appliesTo(problem)) {
				CreditsType credits = (CreditsType) problem.object();
				XMLGregorianCalendar release = credits.getReleaseDate();
				credits.setReleaseDate(credits.getExpirationDate());
				credits.setExpirationDate(release);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Navigation
	// ----------------------------------------------------------------------------------------------------

	/** Leads to the element that needs the author's input. */
	static final class NavigationFix extends Fix {
		private final Predicate<HlomProblem> matcher;

		NavigationFix(String label, String description, Predicate<HlomProblem> matcher) {
			super(label, description, false);
			this.matcher = matcher;
		}

		@Override
		public Kind kind() {
			return Kind.NAVIGATE;
		}

		@Override
		public boolean appliesTo(HlomProblem problem) {
			return problem.object() != null && matcher.test(problem);
		}

		@Override
		public void apply(DocumentRoot root, HlomProblem problem) {
			// Navigation only: the editor reveals target(problem).
		}
	}

	private static final List<HlomFix> NAVIGATION = List.of(
			new NavigationFix("Go to Title", "Shows the General page, where you can enter the title.",
					p -> mentions(p, "no title")),
			new NavigationFix("Go to Description", "Shows the General page, where you can describe the resource.",
					p -> mentions(p, "no description", "general.description")),
			new NavigationFix("Go to Off-label Description",
					"Shows the off-label description field; only you can describe the off-label use.",
					p -> p.object() instanceof HealthcareEducationType && mentions(p, "off-label", "description")),
			new NavigationFix("Go to Commercial Support Acknowledgement",
					"Shows the acknowledgement field; enter the commercial supporters.",
					p -> p.object() instanceof HealthcareEducationType && mentions(p, "commercial support",
							"acknowledgement")),
			new NavigationFix("Go to Financial Relationship Disclosure",
					"Shows the disclosure field; enter the relevant financial relationships.",
					p -> p.object() instanceof HealthcareEducationType && mentions(p, "financial relationship",
							"disclosure")),
			new NavigationFix("Go to MeSH Keyword",
					"Shows the keyword; look up its MeSH descriptor ID (e.g. with Add MeSH Keywords).",
					p -> p.object() instanceof Keyword && mentions(p, "mesh")),
			new NavigationFix("Go to Activity Location",
					"Shows the activity location; enter the country (a:Country) of the address.",
					p -> p.object() instanceof AddressType && mentions(p, "country")));

	static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
