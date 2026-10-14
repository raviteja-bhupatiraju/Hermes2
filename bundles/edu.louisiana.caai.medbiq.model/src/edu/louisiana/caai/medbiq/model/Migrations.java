/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.medbiq.model.standards.CurriculumInventory;

/**
 * Converts documents to a newer version of their standard: Curriculum Inventory 1.0 to the AAMC point release v10,
 * Activity Report 1.0 to 2.0. Every element and attribute is copied by name into the model of the new version
 * (which writes them in its schema's order); what changed between the versions is converted, and what the new
 * version requires but the old one doesn't have is filled in where it can be derived, else listed in the notes.
 */
public final class Migrations {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** A conversion from one document type to another. */
	public record Migration(String from, String to) {

		/** The target type. */
		public MedbiqDocumentType target() {
			return MedbiqStandards.byId(to);
		}

		/** A label such as "Activity Report 2.0". */
		public String label() {
			MedbiqDocumentType type = target();
			return type == null ? to : type.standard() + " " + type.version();
		}
	}

	/**
	 * The converted document.
	 *
	 * @param xml   the document in the new version
	 * @param notes what was derived or remains to be done, for the author
	 */
	public record Result(String xml, List<String> notes) {
	}

	private static final List<Migration> ALL = List.of(
			new Migration(CurriculumInventory.V1, CurriculumInventory.V10),
			new Migration("activity-report-v1", "activity-report-v2"));

	private Migrations() {
	}

	/** The conversions of documents of a type (by type id); empty if there is no newer version. */
	public static List<Migration> of(String typeId) {
		return ALL.stream().filter(migration -> migration.from().equals(typeId)).toList();
	}

	/** Converts a document (given its document root) to the newer version. */
	public static Result migrate(Migration migration, EObject documentRoot) throws IOException {
		MedbiqDocumentType target = migration.target();
		EObject sourceRoot = Paths.rootElement(documentRoot);
		if (target == null || sourceRoot == null) {
			throw new IOException("The document can't be converted.");
		}
		List<String> notes = new ArrayList<>();
		EClass rootClass = (EClass) ((EReference) META.getElement(META.getDocumentRoot(target.ePackage()),
				target.namespace(), target.rootElement())).getEReferenceType();
		EObject root = EcoreUtil.create(rootClass);
		copy(sourceRoot, root, notes);
		switch (migration.to()) {
			case CurriculumInventory.V10 -> curriculumLevels(sourceRoot, root, notes);
			case "activity-report-v2" -> activityReportPeriod(sourceRoot, root, notes);
			default -> {
			}
		}
		EClass documentRootClass = META.getDocumentRoot(target.ePackage());
		EObject newDocumentRoot = EcoreUtil.create(documentRootClass);
		newDocumentRoot.eSet(META.getElement(documentRootClass, target.namespace(), target.rootElement()), root);
		Resource resource = target.createResourceSet().createResource(URI.createURI("migrated.xml"));
		resource.getContents().add(newDocumentRoot);
		return new Result(HlomResources.toXml(resource), notes);
	}

	// ----------------------------------------------------------------------------------------------------
	// Copying by name
	// ----------------------------------------------------------------------------------------------------

	/** Copies the content of an element into the element of the new version, by element and attribute names. */
	private static void copy(EObject source, EObject target, List<String> notes) {
		for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
			// Features derived from a group (choices) are copied with the group's entries, in their order.
			if (feature.isDerived() || feature.isTransient() || !source.eIsSet(feature)) {
				continue;
			}
			if (FeatureMapUtil.isFeatureMap(feature)) {
				copyFeatureMap(source, feature, target, notes);
				continue;
			}
			Object value = source.eGet(feature);
			for (Object item : feature.isMany() ? (Collection<?>) value : List.of(value)) {
				add(source, feature, item, target, notes);
			}
		}
	}

	/** Groups, mixed content and wildcards: elements by name, text as it is, other namespaces as they are. */
	private static void copyFeatureMap(EObject source, EStructuralFeature feature, EObject target, List<String> notes) {
		FeatureMap sourceMap = (FeatureMap) source.eGet(feature);
		for (FeatureMap.Entry entry : sourceMap) {
			EStructuralFeature entryFeature = entry.getEStructuralFeature();
			if (FeatureMapUtil.isText(entry) || FeatureMapUtil.isCDATA(entry) || FeatureMapUtil.isComment(entry)) {
				EStructuralFeature mixed = counterpart(feature, target.eClass());
				if (mixed != null && FeatureMapUtil.isFeatureMap(mixed)) {
					((FeatureMap) target.eGet(mixed)).add(entryFeature, entry.getValue());
				}
			} else if (entryFeature.getEContainingClass() != null && entryFeature.getEContainingClass()
					.getEPackage() == source.eClass().getEPackage()
					|| entryFeature.getEContainingClass() != null && counterpart(entryFeature, target.eClass()) != null) {
				add(source, entryFeature, entry.getValue(), target, notes);
			} else {
				// Wildcard content of other namespaces: kept as it is.
				EStructuralFeature any = counterpart(feature, target.eClass());
				if (any != null && FeatureMapUtil.isFeatureMap(any)) {
					((FeatureMap) target.eGet(any)).add(entryFeature, entry.getValue() instanceof EObject object
							? EcoreUtil.copy(object) : entry.getValue());
				}
			}
		}
	}

	/** Adds a value of a source feature to the feature of the same name of the target element. */
	private static void add(EObject source, EStructuralFeature feature, Object value, EObject target,
			List<String> notes) {
		EStructuralFeature targetFeature = counterpart(feature, target.eClass());
		if (targetFeature == null) {
			dropped(source, feature, notes);
			return;
		}
		Object converted = convert(value, targetFeature, notes);
		if (converted == null) {
			return;
		}
		if (targetFeature.isMany()) {
			@SuppressWarnings("unchecked")
			List<Object> values = (List<Object>) target.eGet(targetFeature);
			values.add(converted);
		} else if (!target.eIsSet(targetFeature)) {
			target.eSet(targetFeature, converted);
		} else {
			notes.add(META.getName(targetFeature) + " occurs only once in the new version; further values were left out.");
		}
	}

	/** The feature of the target class with the same XML name and kind (element or attribute), or {@code null}. */
	private static EStructuralFeature counterpart(EStructuralFeature feature, EClass targetClass) {
		String name = META.getName(feature);
		int kind = META.getFeatureKind(feature);
		for (EStructuralFeature candidate : targetClass.getEAllStructuralFeatures()) {
			if (META.getFeatureKind(candidate) == kind && Objects.equals(META.getName(candidate), name)) {
				return candidate;
			}
		}
		return null;
	}

	private static Object convert(Object value, EStructuralFeature targetFeature, List<String> notes) {
		if (value == null) {
			return null;
		}
		if (targetFeature instanceof EReference reference) {
			if (!(value instanceof EObject source)) {
				return null;
			}
			EClass targetClass = reference.getEReferenceType();
			if (targetClass.isAbstract()) {
				return null;
			}
			EObject copy = EcoreUtil.create(targetClass);
			copy(source, copy, notes);
			afterCopy(META.getName(targetFeature), source, copy, notes);
			return copy;
		}
		EAttribute attribute = (EAttribute) targetFeature;
		String text = switch (value) {
			case Enumerator literal -> literal.getLiteral();
			case EObject object -> Paths.content(object);
			case XMLGregorianCalendar calendar -> calendar.toXMLFormat();
			default -> value instanceof String s ? s : String.valueOf(value);
		};
		if (text == null) {
			return null;
		}
		try {
			return attribute.getEAttributeType().getInstanceClass() == String.class ? text
					: EcoreUtil.createFromString(attribute.getEAttributeType(), text);
		} catch (RuntimeException e) {
			notes.add("\"" + text + "\" is not a valid " + META.getName(attribute) + " in the new version; it was left out.");
			return null;
		}
	}

	/** The identifier domains of version 2 of the member schema: {@code idd:<domain>:<kind>}. */
	private static final java.util.regex.Pattern IDD = java.util.regex.Pattern.compile("idd:\\S*\\.\\S*:\\S*");
	private static final java.util.regex.Pattern DOMAIN_NAME = java.util.regex.Pattern.compile("[\\w-]+(\\.[\\w-]+)+");

	/** Converts what changed in elements whose structure differs between the versions (member schema 1 to 2). */
	private static void afterCopy(String name, EObject source, EObject target, List<String> notes) {
		switch (name) {
			case "LicensureEntity" -> {
				// Version 1: the name is the element's text; version 2: a LicensureEntityName element.
				String text = Paths.content(source);
				if (text != null && !text.isBlank() && Paths.feature(target, "LicensureEntityName") != null) {
					setText(target, "LicensureEntityName", text.strip());
				}
			}
			case "License" -> {
				if (Paths.feature(target, "LicenseNumberAvailability") != null
						&& Paths.text(target, "LicenseNumberAvailability") == null) {
					setText(target, "LicenseNumberAvailability", Paths.text(source, "LicenseNumber") != null
							? "Available" : "Unknown");
				}
			}
			default -> {
			}
		}
		// Identifier domains: "acadianaheart.org" becomes "idd:acadianaheart.org:id".
		EStructuralFeature domain = Paths.feature(target, "@domain");
		String value = domain == null ? null : Paths.text(target, "@domain");
		String pattern = domain instanceof EAttribute attribute ? String.valueOf(META.getPatternFacet(
				attribute.getEAttributeType())) : "";
		if (value != null && pattern.contains("idd:") && !IDD.matcher(value).matches()) {
			if (DOMAIN_NAME.matcher(value).matches()) {
				setText(target, "@domain", "idd:" + value + ":id");
				String note = "Identifier domains became \"idd:<domain>:id\" (version 2.0); replace \"id\" with the "
						+ "kind of identifier (such as learner).";
				if (!notes.contains(note)) {
					notes.add(note);
				}
			} else {
				notes.add("The identifier domain \"" + value + "\" must be written \"idd:<internet domain>:<kind>\" "
						+ "in version 2.0.");
			}
		}
	}

	private static void dropped(EObject source, EStructuralFeature feature, List<String> notes) {
		String name = META.getName(feature);
		String owner = References.elementName(source);
		// Converted by the migration itself.
		if ("Level".equals(name) && "SequenceBlock".equals(owner)) {
			return;
		}
		String note = (owner == null ? "" : owner + "/") + name + " has no counterpart in the new version and was left out.";
		if (!notes.contains(note)) {
			notes.add(note);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Curriculum Inventory 1.0 to v10
	// ----------------------------------------------------------------------------------------------------

	/** The Level of a 1.0 sequence block becomes its starting and ending academic level in v10. */
	private static void curriculumLevels(EObject sourceRoot, EObject root, List<String> notes) {
		List<EObject> sourceBlocks = Paths.objects(sourceRoot, "Sequence/SequenceBlock");
		List<EObject> blocks = Paths.objects(root, "Sequence/SequenceBlock");
		int converted = 0;
		for (int i = 0; i < Math.min(sourceBlocks.size(), blocks.size()); i++) {
			String level = Paths.text(sourceBlocks.get(i), "Level");
			if (level == null) {
				continue;
			}
			EObject block = blocks.get(i);
			EStructuralFeature levelsFeature = Paths.feature(block, "SequenceBlockLevels");
			if (!(levelsFeature instanceof EReference reference)) {
				continue;
			}
			EObject levels = EcoreUtil.create(reference.getEReferenceType());
			setText(levels, "StartingAcademicLevel", level);
			setText(levels, "EndingAcademicLevel", level);
			block.eSet(reference, levels);
			converted++;
		}
		if (converted > 0) {
			notes.add("The academic level of " + converted + (converted == 1 ? " sequence block" : " sequence blocks")
					+ " became its starting and ending academic level (SequenceBlockLevels); set the ending level of "
					+ "blocks that span several levels.");
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Activity Report 1.0 to 2.0
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Version 2.0 requires the reporting period of the reports and the participants of each report: the period is
	 * derived from the dates of the activities (else the creation date); the participants are left to the author.
	 */
	private static void activityReportPeriod(EObject sourceRoot, EObject root, List<String> notes) {
		List<String> dates = new ArrayList<>();
		for (EObject report : Paths.objects(sourceRoot, "ActivityReport")) {
			collectDates(report, dates);
		}
		dates.sort(null);
		String created = Paths.text(sourceRoot, "DateTimeCreated");
		String first = !dates.isEmpty() ? dates.getFirst() : created == null ? null : created.substring(0, 10);
		String last = !dates.isEmpty() ? dates.getLast() : first;
		if (first != null) {
			setText(root, "ReportingStartDate", first);
			setText(root, "ReportingEndDate", last);
			notes.add("The reporting period (" + first + " to " + last + ") was derived from the dates of the "
					+ "activities; check it.");
		} else {
			notes.add("Enter the reporting period (ReportingStartDate and ReportingEndDate).");
		}
		List<EObject> reports = Paths.objects(root, "ActivityReport");
		for (EObject report : reports) {
			EStructuralFeature participants = Paths.feature(report, "Participants");
			if (participants instanceof EReference reference && !report.eIsSet(reference)) {
				EObject empty = EcoreUtil.create(reference.getEReferenceType());
				if (reference.isMany()) {
					@SuppressWarnings("unchecked")
					List<EObject> values = (List<EObject>) report.eGet(reference);
					values.add(empty);
				} else {
					report.eSet(reference, empty);
				}
			}
		}
		if (!reports.isEmpty()) {
			notes.add("Version 2.0 describes the participants of each activity report (Participants, anonymous "
					+ "demographics); add at least one Participant to each report.");
		}
	}

	private static void collectDates(EObject object, List<String> dates) {
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (!object.eIsSet(feature)) {
				continue;
			}
			Object value = object.eGet(feature);
			for (Object item : value instanceof Collection<?> list ? list : List.of(value)) {
				if (item instanceof XMLGregorianCalendar calendar) {
					String text = calendar.toXMLFormat();
					dates.add(text.length() >= 10 ? text.substring(0, 10) : text);
				} else if (item instanceof EObject child && feature instanceof EReference reference
						&& reference.isContainment()) {
					collectDates(child, dates);
				}
			}
		}
	}

	/** Sets the simple content of a child element (creating it) or an attribute ({@code @name}). */
	private static void setText(EObject owner, String name, String text) {
		EStructuralFeature feature = Paths.feature(owner, name);
		if (feature != null && feature.isMany()) {
			return;
		}
		if (feature instanceof EAttribute attribute) {
			owner.eSet(attribute, attribute.getEAttributeType().getInstanceClass() == String.class ? text
					: EcoreUtil.createFromString(attribute.getEAttributeType(), text));
		} else if (feature instanceof EReference reference) {
			EObject element = EcoreUtil.create(reference.getEReferenceType());
			for (EStructuralFeature content : element.eClass().getEAllStructuralFeatures()) {
				if (META.getFeatureKind(content) == ExtendedMetaData.SIMPLE_FEATURE && content instanceof EAttribute a) {
					element.eSet(a, a.getEAttributeType().getInstanceClass() == String.class ? text
							: EcoreUtil.createFromString(a.getEAttributeType(), text));
					break;
				}
			}
			owner.eSet(reference, element);
		}
	}
}
