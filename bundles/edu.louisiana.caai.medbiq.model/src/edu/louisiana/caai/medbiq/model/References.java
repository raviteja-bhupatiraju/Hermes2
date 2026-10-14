/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

/**
 * The references within a document: elements whose text identifies another element of the same document, such as
 * the {@code EventReference} of a Curriculum Inventory sequence block ({@code /CurriculumInventory/Events/Event[@id
 * ='X']}) or the {@code PerformanceScaleReference} of a performance level set (the scale's id). Each standard
 * declares its {@link Kind kinds} ({@link edu.louisiana.caai.medbiq.model.standards.StandardContent#references});
 * the editor offers the targets in pickers, links references to their targets, finds the references of an element
 * and renames identifiers together with their references.
 */
public final class References {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** The key in an XPath reference: the quoted value of its last predicate, such as X in {@code [@id='X']}. */
	private static final Pattern PREDICATE = Pattern.compile("\\[[^=\\]]*=\\s*(['\"])(.*?)\\1\\s*]\\s*$");

	/**
	 * A kind of reference.
	 *
	 * @param label    what is referenced, in words (such as {@code Event})
	 * @param element  the local name of the referring element (such as {@code EventReference})
	 * @param targets  the path of the referenced elements from the root element (such as {@code Events/Event})
	 * @param key      the path of the identifying text of a target, relative to it (such as {@code @id})
	 * @param title    the path of a target's title, relative to it (alternatives separated by {@code |}), or
	 *                 {@code null}
	 * @param template the text of a reference with {@code %s} for the key (such as {@code /CurriculumInventory/
	 *                 Events/Event[@id='%s']}), or {@code %s} for references by plain key
	 * @param parent   the local name of the element containing the referring elements, if the name of the referring
	 *                 element alone is ambiguous (such as {@code SequenceBlock} for its {@code Level}), else
	 *                 {@code null}
	 */
	public record Kind(String label, String element, String targets, String key, String title, String template,
			String parent) {

		public Kind(String label, String element, String targets, String key, String title, String template) {
			this(label, element, targets, key, title, template, null);
		}

		/** The text of a reference to a key. */
		public String format(String key) {
			return template.formatted(key);
		}

		/** The key a reference text identifies, or {@code null}. */
		public String key(String reference) {
			if (reference == null || reference.isBlank()) {
				return null;
			}
			if (template.equals("%s")) {
				return reference.strip();
			}
			Matcher matcher = PREDICATE.matcher(reference.strip());
			return matcher.find() ? matcher.group(2) : null;
		}
	}

	/**
	 * A referenced element.
	 *
	 * @param kind    the kind of references to it
	 * @param key     its identifying text
	 * @param title   its title, or {@code null}
	 * @param element the element
	 */
	public record Target(Kind kind, String key, String title, EObject element) {

		/** The text naming the target in pickers: the title with the key, or the key. */
		public String label() {
			return title == null || title.isBlank() || title.equals(key) ? key : title + " (" + key + ")";
		}
	}

	/**
	 * A reference in a document.
	 *
	 * @param kind    the kind of reference
	 * @param owner   the element holding the reference value
	 * @param feature the feature of the value: an attribute of the owner, or the referring element itself
	 * @param index   the position of the value if the feature repeats, else -1
	 * @param text    the text of the reference
	 * @param element the referring element as a model object, if it is one (it has attributes), else {@code null}
	 */
	public record Occurrence(Kind kind, EObject owner, EStructuralFeature feature, int index, String text,
			EObject element) {

		/** The key the reference identifies, or {@code null}. */
		public String key() {
			return kind.key(text);
		}
	}

	private References() {
	}

	/**
	 * The kind of reference of an element by its local name and that of the element containing it, or {@code null}.
	 */
	public static Kind kind(List<Kind> kinds, String parent, String element) {
		for (Kind kind : kinds) {
			if (kind.element().equals(element) && (kind.parent() == null || kind.parent().equals(parent))) {
				return kind;
			}
		}
		return null;
	}

	/** The local name of an element (of the feature containing it), or {@code null} for a root. */
	public static String elementName(EObject object) {
		return object == null || object.eContainmentFeature() == null ? null : META.getName(object.eContainmentFeature());
	}

	/** The kinds of reference whose targets are elements of the given local name. */
	public static List<Kind> kindsOfTarget(List<Kind> kinds, String targetElement) {
		return kinds.stream().filter(kind -> last(kind.targets()).equals(targetElement)).toList();
	}

	/** The elements a kind of reference can refer to in a document (given its root element), in document order. */
	public static List<Target> targets(EObject rootElement, Kind kind) {
		List<Target> targets = new ArrayList<>();
		for (EObject element : Paths.objects(rootElement, kind.targets())) {
			for (String key : Paths.texts(element, kind.key())) {
				targets.add(new Target(kind, key, title(element, kind.title()), element));
			}
		}
		return targets;
	}

	/** The target of an element (if it is one of a kind's targets and has a key), or {@code null}. */
	public static Target targetOf(EObject rootElement, Kind kind, EObject element) {
		for (Target target : targets(rootElement, kind)) {
			if (target.element() == element) {
				return target;
			}
		}
		return null;
	}

	/** The target a reference text refers to, or {@code null} if it doesn't resolve. */
	public static Target resolve(EObject rootElement, Kind kind, String reference) {
		String key = kind.key(reference);
		if (key == null) {
			return null;
		}
		for (Target target : targets(rootElement, kind)) {
			if (target.key().equals(key)) {
				return target;
			}
		}
		return null;
	}

	/** All references of the given kinds below an element, in document order. */
	public static List<Occurrence> occurrences(EObject rootElement, List<Kind> kinds) {
		List<Occurrence> occurrences = new ArrayList<>();
		if (kinds.isEmpty()) {
			return occurrences;
		}
		List<EObject> objects = new ArrayList<>();
		objects.add(rootElement);
		for (TreeIterator<EObject> all = rootElement.eAllContents(); all.hasNext();) {
			objects.add(all.next());
		}
		for (EObject object : objects) {
			for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
				if (META.getFeatureKind(feature) != ExtendedMetaData.ELEMENT_FEATURE || !object.eIsSet(feature)) {
					continue;
				}
				Kind kind = kind(kinds, elementName(object), META.getName(feature));
				if (kind == null) {
					continue;
				}
				Object value = object.eGet(feature);
				if (value instanceof List<?> list) {
					for (int i = 0; i < list.size(); i++) {
						add(occurrences, kind, object, feature, i, list.get(i));
					}
				} else {
					add(occurrences, kind, object, feature, -1, value);
				}
			}
		}
		return occurrences;
	}

	private static void add(List<Occurrence> occurrences, Kind kind, EObject owner, EStructuralFeature feature,
			int index, Object value) {
		String text = switch (value) {
			case null -> null;
			case Enumerator literal -> literal.getLiteral();
			case EObject element -> Paths.content(element);
			default -> value.toString();
		};
		if (text != null && !text.isBlank()) {
			occurrences.add(new Occurrence(kind, owner, feature, index, text.strip(),
					value instanceof EObject element ? element : null));
		}
	}

	/** The references to a target in its document. */
	public static List<Occurrence> referencesTo(EObject rootElement, Target target) {
		return occurrences(rootElement, List.of(target.kind())).stream()
				.filter(occurrence -> Objects.equals(occurrence.key(), target.key())).toList();
	}

	/**
	 * The references of a document that don't resolve (their key is no target of their kind), to check documents and
	 * to offer the targets as fixes.
	 */
	public static List<Occurrence> unresolved(EObject rootElement, List<Kind> kinds) {
		List<Occurrence> unresolved = new ArrayList<>();
		for (Occurrence occurrence : occurrences(rootElement, kinds)) {
			if (occurrence.key() != null && resolve(rootElement, occurrence.kind(), occurrence.text()) == null) {
				unresolved.add(occurrence);
			}
		}
		return unresolved;
	}

	/**
	 * The attribute holding the key of a target (such as its {@code id} attribute), if the key is an attribute or
	 * the simple content of an element of the target; with the object owning it.
	 */
	public record KeyLocation(EObject owner, EAttribute attribute) {
	}

	/** Where the key of a target is stored, or {@code null} if it isn't a simple value. */
	public static KeyLocation keyLocation(Target target) {
		String path = target.kind().key();
		int slash = path.lastIndexOf('/');
		EObject owner = target.element();
		if (slash > 0) {
			List<EObject> owners = Paths.objects(owner, path.substring(0, slash));
			owner = null;
			for (EObject candidate : owners) {
				if (Paths.texts(candidate, path.substring(slash + 1)).contains(target.key())) {
					owner = candidate;
					break;
				}
			}
			if (owner == null) {
				return null;
			}
		}
		String last = path.substring(slash + 1);
		EStructuralFeature feature = Paths.feature(owner, last);
		if (feature instanceof EAttribute attribute) {
			return new KeyLocation(owner, attribute);
		}
		// An element with simple content (the one with the key, if it repeats): its content attribute.
		Object value = feature == null ? null : owner.eGet(feature);
		List<?> values = value instanceof List<?> list ? list : java.util.Collections.singletonList(value);
		for (Object candidate : values) {
			if (candidate instanceof EObject element && target.key().equals(strip(Paths.content(element)))) {
				for (EStructuralFeature contentFeature : element.eClass().getEAllStructuralFeatures()) {
					if (META.getFeatureKind(contentFeature) == ExtendedMetaData.SIMPLE_FEATURE
							&& contentFeature instanceof EAttribute content) {
						return new KeyLocation(element, content);
					}
				}
			}
		}
		return null;
	}

	private static String title(EObject element, String paths) {
		if (paths == null) {
			return null;
		}
		for (String path : paths.split("\\|")) {
			String text = Paths.text(element, path.strip());
			if (text != null && !text.isBlank()) {
				return text.strip();
			}
		}
		return null;
	}

	private static String strip(String text) {
		return text == null ? null : text.strip();
	}

	private static String last(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}
}
