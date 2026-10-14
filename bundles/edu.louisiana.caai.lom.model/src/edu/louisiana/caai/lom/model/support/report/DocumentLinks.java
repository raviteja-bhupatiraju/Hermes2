/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.type.DocumentType;

/**
 * How a document is identified and which other documents it names: its identifiers (catalog and entry of its
 * Healthcare LOM description, such as the URI of a competency object) and its links (identifiers of other documents
 * in it, such as the competency objects a competency framework includes or the related resources of a learning
 * object). The workspace resolves links to the documents with those identifiers.
 *
 * @param identifiers the identifiers of the document
 * @param links       the identifiers of other documents named in it
 */
public record DocumentLinks(List<Identifier> identifiers, List<Link> links) {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** No identifiers and no links. */
	public static final DocumentLinks NONE = new DocumentLinks(List.of(), List.of());

	/** An identifier: a catalog (or {@code null}) and an entry. */
	public record Identifier(String catalog, String entry) {

		/** The identifier as {@code catalog: entry}, or the entry. */
		public String label() {
			return catalog == null || catalog.isBlank() ? entry : catalog + ": " + entry;
		}

		/** The entry normalized for lookups (case and a trailing slash of URIs don't matter). */
		public String key() {
			return DocumentLinks.key(entry);
		}
	}

	/**
	 * A link to another document.
	 *
	 * @param label  what is linked, in words (such as {@code Included competency})
	 * @param target the identifier of the linked document
	 * @param path   the element path of the link in the document
	 */
	public record Link(String label, Identifier target, String path) {
	}

	/**
	 * Where a type of document names other documents: elements with a catalog and an entry.
	 *
	 * @param path  the path of the elements from the root element (local names separated by {@code /})
	 * @param label what is linked, in words
	 */
	public record LinkPath(String path, String label) {
	}

	public DocumentLinks {
		identifiers = List.copyOf(identifiers);
		links = List.copyOf(links);
	}

	/** Whether a link names this document (by entry; the catalogs are compared when both have one). */
	public boolean isIdentifiedBy(Identifier target) {
		for (Identifier identifier : identifiers) {
			if (identifier.key().equals(target.key()) && (identifier.catalog() == null || target.catalog() == null
					|| identifier.catalog().equalsIgnoreCase(target.catalog()))) {
				return true;
			}
		}
		return false;
	}

	/** The identifiers and links of a document (given its document root). */
	public static DocumentLinks of(DocumentType type, EObject documentRoot) {
		EObject root = rootElement(documentRoot);
		if (type == null || root == null) {
			return NONE;
		}
		List<Identifier> identifiers = new ArrayList<>();
		EObject lom = "lom".equals(name(root)) ? root : objects(root, "lom").stream().findFirst().orElse(null);
		if (lom != null) {
			for (EObject identifier : objects(lom, "general/identifier")) {
				Identifier read = read(identifier);
				if (read != null) {
					identifiers.add(read);
				}
			}
		}
		List<Link> links = new ArrayList<>();
		for (LinkPath linkPath : type.linkPaths()) {
			for (EObject element : objects(root, linkPath.path())) {
				Identifier read = read(element);
				if (read != null) {
					links.add(new Link(linkPath.label(), read, ElementPath.of(element).toString()));
				}
			}
		}
		return new DocumentLinks(identifiers, links);
	}

	/** The entry of an identifier normalized for lookups (case and a trailing slash of URIs don't matter). */
	public static String key(String entry) {
		String key = entry.strip();
		return (key.endsWith("/") ? key.substring(0, key.length() - 1) : key).toLowerCase(Locale.ROOT);
	}

	private static Identifier read(EObject element) {
		String catalog = text(element, "catalog");
		String entry = text(element, "entry");
		return entry == null ? null : new Identifier(catalog, entry);
	}

	private static EObject rootElement(EObject documentRoot) {
		if (documentRoot == null) {
			return null;
		}
		for (EStructuralFeature feature : documentRoot.eClass().getEAllStructuralFeatures()) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE && documentRoot.eIsSet(feature)
					&& documentRoot.eGet(feature) instanceof EObject root) {
				return root;
			}
		}
		return null;
	}

	private static String name(EObject object) {
		return object.eContainmentFeature() == null ? null : META.getName(object.eContainmentFeature());
	}

	/** The objects at a path of local names (compared ignoring case: {@code Entry} and {@code entry}). */
	private static List<EObject> objects(EObject start, String path) {
		List<EObject> current = List.of(start);
		for (String step : path.split("/")) {
			List<EObject> next = new ArrayList<>();
			for (EObject object : current) {
				for (Object value : values(object, step)) {
					if (value instanceof EObject child) {
						next.add(child);
					}
				}
			}
			current = next;
		}
		return current;
	}

	private static List<Object> values(EObject object, String step) {
		List<Object> values = new ArrayList<>();
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE && object.eIsSet(feature)
					&& step.equalsIgnoreCase(META.getName(feature))) {
				Object value = object.eGet(feature);
				if (value instanceof List<?> list) {
					values.addAll(list);
				} else {
					values.add(value);
				}
				break;
			}
		}
		return values;
	}

	/** The first text of a child element (by local name, ignoring case), stripped, or {@code null}. */
	private static String text(EObject object, String child) {
		for (Object value : values(object, child)) {
			String text = switch (value) {
				case null -> null;
				case EObject element -> content(element);
				case Enumerator literal -> literal.getLiteral();
				default -> value.toString();
			};
			if (text != null && !text.isBlank()) {
				return text.strip();
			}
		}
		return null;
	}

	private static String content(EObject object) {
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if (kind == ExtendedMetaData.SIMPLE_FEATURE && feature instanceof EAttribute attribute && object.eIsSet(feature)) {
				Object value = object.eGet(feature);
				return value instanceof String s ? s : EcoreUtil.convertToString(attribute.getEAttributeType(), value);
			}
			if (FeatureMapUtil.isFeatureMap(feature) && ":mixed".equals(META.getName(feature)) && object.eIsSet(feature)) {
				StringBuilder text = new StringBuilder();
				for (FeatureMap.Entry entry : (FeatureMap) object.eGet(feature)) {
					if (FeatureMapUtil.isText(entry) || FeatureMapUtil.isCDATA(entry)) {
						text.append(entry.getValue());
					}
				}
				return text.toString();
			}
		}
		return null;
	}
}
