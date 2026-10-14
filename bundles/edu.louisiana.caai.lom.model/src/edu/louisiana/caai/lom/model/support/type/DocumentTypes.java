/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.type;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import edu.louisiana.caai.lom.model.support.check.XmlOutline;

/**
 * The registry of {@link DocumentType document types}. Healthcare LOM is always registered; the other
 * MedBiquitous standards are registered by their bundle ({@code edu.louisiana.caai.medbiq.model}: by its activator
 * inside Eclipse, by {@code MedbiqStandards.init()} outside).
 */
public final class DocumentTypes {

	private static final List<DocumentType> TYPES = new CopyOnWriteArrayList<>(List.of(HlomDocumentType.INSTANCE));
	private static final Pattern JSON_ROOT = Pattern.compile("\"([A-Za-z_][\\w.-]*)\"\\s*:");

	private DocumentTypes() {
	}

	/** Registers a type (once; a type with the same id is replaced). */
	public static void register(DocumentType type) {
		TYPES.removeIf(existing -> existing.id().equals(type.id()));
		TYPES.add(type);
	}

	/** All registered types, Healthcare LOM first. */
	public static List<DocumentType> all() {
		return List.copyOf(TYPES);
	}

	/** The type with an id, or {@code null}. */
	public static DocumentType byId(String id) {
		return TYPES.stream().filter(type -> type.id().equals(id)).findFirst().orElse(null);
	}

	/** The type of a root element, or {@code null}. */
	public static DocumentType of(String namespace, String rootElement) {
		return TYPES.stream()
				.filter(type -> type.namespace().equals(namespace) && type.rootElement().equals(rootElement))
				.findFirst().orElse(null);
	}

	/** The type of a loaded document, from its root element, or {@code null}. */
	public static DocumentType of(Resource resource) {
		for (EObject object : resource.getContents()) {
			DocumentType type = ofDocumentRoot(object);
			if (type != null) {
				return type;
			}
		}
		return null;
	}

	/** The type of a document root (the root element is its only set element feature), or {@code null}. */
	public static DocumentType ofDocumentRoot(EObject documentRoot) {
		for (var feature : documentRoot.eClass().getEAllStructuralFeatures()) {
			if (ExtendedMetaData.INSTANCE.getFeatureKind(feature) == ExtendedMetaData.ELEMENT_FEATURE
					&& documentRoot.eIsSet(feature)) {
				DocumentType type = of(ExtendedMetaData.INSTANCE.getNamespace(feature),
						ExtendedMetaData.INSTANCE.getName(feature));
				if (type != null) {
					return type;
				}
			}
		}
		return null;
	}

	/**
	 * The type of document text (possibly incomplete, as typed): the first element and the namespace it declares
	 * (XML), or the first member (JSON); {@code null} if it isn't a MedBiquitous document.
	 */
	public static DocumentType detect(String text) {
		String start = text.stripLeading();
		if (start.startsWith("{")) {
			Matcher matcher = JSON_ROOT.matcher(start);
			while (matcher.find()) {
				String name = matcher.group(1);
				if (name.startsWith("$")) {
					continue;
				}
				for (DocumentType type : TYPES) {
					if (type.supportsJson() && type.rootElement().equals(name)) {
						return type;
					}
				}
				return null;
			}
			return null;
		}
		List<XmlOutline.Element> roots = XmlOutline.of(text).roots();
		if (roots.isEmpty()) {
			return null;
		}
		XmlOutline.Element root = roots.getFirst();
		String startTag = text.substring(root.start(), root.tagEnd());
		String prefix = root.name().contains(":") ? root.name().substring(0, root.name().indexOf(':')) : null;
		Matcher declaration = Pattern
				.compile("xmlns" + (prefix == null ? "" : ":" + Pattern.quote(prefix)) + "\\s*=\\s*[\"']([^\"']*)[\"']")
				.matcher(startTag);
		String namespace = declaration.find() ? declaration.group(1) : "";
		return of(namespace, root.localName());
	}
}
