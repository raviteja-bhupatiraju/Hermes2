/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JBoolean;
import edu.louisiana.caai.lom.model.support.json.Json.JNull;
import edu.louisiana.caai.lom.model.support.json.Json.JNumber;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JString;
import edu.louisiana.caai.lom.model.support.json.Json.JsonException;
import edu.louisiana.caai.lom.model.support.json.Json.Member;
import edu.louisiana.caai.lom.model.support.json.Json.Value;

/**
 * Converts HLOM documents between their XML form and their JSON form.
 * <p>
 * The JSON form follows the naming of the MedBiquitous Healthcare LOM JSON Schemas: every element becomes a
 * member named after the element ({@code "general"}, {@code "title"}, {@code "healthcareEducation"}, ...),
 * elements that may repeat are arrays, multilingual strings are {@code {"language": "en", "content": "..."}}
 * objects and vocabulary values are {@code {"source": "LOMv1.0", "value": "author"}} objects. In detail:
 * <ul>
 * <li>The document is an object with the {@code "lom"} member (and {@code "$schema"}).</li>
 * <li>An element with only text is a string, or a number/boolean when its schema type is numeric/boolean.</li>
 * <li>An element of a type with text content and attributes is an object with the attributes and
 * {@code "content"}.</li>
 * <li>An element with child elements is an object; its attributes declared by the schema are members with the
 * attribute name, other attributes are prefixed with {@code @}.</li>
 * <li>Elements and attributes the HLOM schemas don't define are kept with a {@code prefix:} name; prefixes
 * other than {@code hx} (healthcare extensions), {@code a} (address) and {@code xsi} are declared in
 * {@code "$namespaces"}.</li>
 * </ul>
 * The structure is taken from the EMF model (generated from the HLOM XML Schemas), so the conversion is
 * lossless for HLOM content and element order is restored from the schema when converting back to XML.
 * Comments and processing instructions are not kept.
 */
public final class HlomJson {

	/** The top-level JSON Schema published by MedBiquitous, referenced as {@code "$schema"}. */
	public static final String SCHEMA_URL = "https://ns.medbiq.org/jsonSchema/healthcare-learning-object-metadata/healthcarelom.schema.json";

	/** Location of the HLOM XML Schema written to {@code xsi:schemaLocation} of the XML form. */
	public static final String XML_SCHEMA_URL = "https://ns.medbiq.org/lom/healthcarelom.xsd";

	/** Member holding the text of an element that also has attributes. */
	public static final String CONTENT = "content";

	private static final String SCHEMA_MEMBER = "$schema";
	private static final String NAMESPACES_MEMBER = "$namespaces";
	private static final String SCHEMA_LOCATION_MEMBER = "$schemaLocation";
	private static final String DEFAULT_SCHEMA_LOCATION = LomPackage.eNS_URI + " " + XML_SCHEMA_URL;
	private static final String XSI_NS = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
	private static final Pattern JSON_NUMBER = Pattern.compile("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][+-]?[0-9]+)?");

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	/** The XML form of a JSON document and, for each line of it, the line of the JSON text it came from. */
	public record XmlForm(String xml, int[] jsonLines) {

		/** The JSON line an XML line was produced from (both 1-based), or the XML line if unknown. */
		public int jsonLine(int xmlLine) {
			return xmlLine >= 1 && xmlLine < jsonLines.length && jsonLines[xmlLine] > 0 ? jsonLines[xmlLine] : xmlLine;
		}
	}

	private HlomJson() {
	}

	/** The prefixes known without declaration. */
	private static Map<String, String> standardPrefixes() {
		Map<String, String> prefixes = new LinkedHashMap<>();
		prefixes.put("lom", LomPackage.eNS_URI);
		prefixes.put("hx", HealthcarePackage.eNS_URI);
		prefixes.put("a", AddressPackage.eNS_URI);
		prefixes.put("xsi", XSI_NS);
		return prefixes;
	}

	/**
	 * Whether a text looks like a JSON document rather than XML: its first character (after white space and a byte
	 * order mark) is <code>{</code>.
	 */
	public static boolean looksLikeJson(byte[] start, int length) {
		for (int i = 0; i < length; i++) {
			int b = start[i] & 0xFF;
			if (b == 0xEF || b == 0xBB || b == 0xBF || b == ' ' || b == '\t' || b == '\r' || b == '\n') {
				continue;
			}
			return b == '{';
		}
		return false;
	}

	// ----------------------------------------------------------------------------------------------------
	// XML to JSON
	// ----------------------------------------------------------------------------------------------------

	/** Converts an HLOM XML document (UTF-8 or with an encoding declaration) to JSON text. */
	public static String toJson(byte[] xml) throws IOException {
		Document document;
		try {
			document = newDocumentBuilder().parse(new ByteArrayInputStream(xml));
		} catch (SAXException e) {
			throw new IOException("The XML can't be converted to JSON: " + e.getMessage(), e);
		}
		return Json.write(toJson(document.getDocumentElement()));
	}

	private static DocumentBuilder newDocumentBuilder() throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			factory.setExpandEntityReferences(false);
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	private static JObject toJson(Element root) {
		ToJson converter = new ToJson();
		JObject lom = converter.element(root, typeOf(META.getElement(root.getNamespaceURI(), root.getLocalName())));
		JObject document = new JObject();
		document.put(SCHEMA_MEMBER, SCHEMA_URL);
		String schemaLocation = root.getAttributeNS(XSI_NS, "schemaLocation").trim();
		if (!schemaLocation.isEmpty() && !schemaLocation.equals(DEFAULT_SCHEMA_LOCATION)) {
			// Kept for the XML form; the default is not written.
			document.put(SCHEMA_LOCATION_MEMBER, schemaLocation);
		}
		if (!converter.extraPrefixes.isEmpty()) {
			JObject namespaces = new JObject();
			converter.extraPrefixes.forEach(namespaces::put);
			document.put(NAMESPACES_MEMBER, namespaces);
		}
		document.put(converter.key(root, null), lom);
		return document;
	}

	private static final class ToJson {
		/** Namespace URI to prefix, for the namespaces needing a prefix in member names. */
		private final Map<String, String> prefixOf = new LinkedHashMap<>();
		/** Prefixes declared in "$namespaces". */
		private final Map<String, String> extraPrefixes = new LinkedHashMap<>();

		ToJson() {
			standardPrefixes().forEach((prefix, uri) -> prefixOf.put(uri, prefix));
		}

		/** The member name of an element: its local name, prefixed when the schema doesn't define it here. */
		String key(Element element, EClass parent) {
			String ns = element.getNamespaceURI();
			String local = element.getLocalName();
			boolean known = parent == null ? META.getElement(ns, local) != null && LomPackage.eNS_URI.equals(ns)
					: META.getElement(parent, ns, local) != null && findElement(parent, local) == META.getElement(parent, ns, local);
			return known ? local : prefixed(ns, local, element.getPrefix());
		}

		private String prefixed(String ns, String local, String preferredPrefix) {
			if (ns == null || ns.isEmpty()) {
				return local;
			}
			String prefix = prefixOf.get(ns);
			if (prefix == null) {
				prefix = preferredPrefix == null || preferredPrefix.isEmpty() || prefixOf.containsValue(preferredPrefix)
						? "ns" + (extraPrefixes.size() + 1)
						: preferredPrefix;
				prefixOf.put(ns, prefix);
				extraPrefixes.put(prefix, ns);
			}
			return prefix + ":" + local;
		}

		JObject element(Element element, EClass type) {
			JObject object = new JObject();
			attributes(element, type, object);
			Map<String, List<Value>> children = new LinkedHashMap<>();
			Map<String, Boolean> many = new LinkedHashMap<>();
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node instanceof Element child) {
					String key = key(child, type);
					EStructuralFeature feature = type == null ? null
							: META.getElement(type, child.getNamespaceURI(), child.getLocalName());
					if (feature == null) {
						feature = META.getElement(child.getNamespaceURI(), child.getLocalName());
					}
					children.computeIfAbsent(key, _ -> new ArrayList<>()).add(value(child, feature));
					many.merge(key, feature != null && feature.isMany(), Boolean::logicalOr);
				}
			}
			if (children.isEmpty()) {
				String text = element.getTextContent();
				if (text != null && !text.isBlank()) {
					object.put(CONTENT, text);
				}
			}
			for (Map.Entry<String, List<Value>> entry : children.entrySet()) {
				List<Value> values = entry.getValue();
				if (values.size() == 1 && !many.get(entry.getKey())) {
					object.put(entry.getKey(), values.getFirst());
				} else {
					JArray array = new JArray();
					array.items().addAll(values);
					object.put(entry.getKey(), array);
				}
			}
			return object;
		}

		private void attributes(Element element, EClass type, JObject object) {
			NamedNodeMap attributes = element.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr) attributes.item(i);
				String ns = attribute.getNamespaceURI();
				String local = attribute.getLocalName() == null ? attribute.getName() : attribute.getLocalName();
				if (XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(ns) || (XSI_NS.equals(ns) && "schemaLocation".equals(local))) {
					continue;
				}
				boolean declared = type != null && META.getAttribute(type, ns, local) != null
						&& findAttribute(type, local) == META.getAttribute(type, ns, local);
				String key = declared ? local : "@" + prefixed(ns, local, attribute.getPrefix());
				object.put(key, attribute.getValue());
			}
		}

		private Value value(Element element, EStructuralFeature feature) {
			EClass type = typeOf(feature);
			boolean hasChildElements = false;
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
				hasChildElements |= node instanceof Element;
			}
			boolean hasAttributes = false;
			NamedNodeMap attributes = element.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				hasAttributes |= !XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(attributes.item(i).getNamespaceURI());
			}
			boolean simpleContent = type != null && META.getContentKind(type) == ExtendedMetaData.SIMPLE_CONTENT;
			boolean declaresAttributes = type != null && !META.getAllAttributes(type).isEmpty();
			if (!hasChildElements && !hasAttributes && (type == null ? true : simpleContent && !declaresAttributes)) {
				return scalar(element.getTextContent(), dataTypeOf(feature, type));
			}
			return element(element, type);
		}
	}

	/** A text as a JSON string, or as a number or boolean when its type is numeric or boolean. */
	private static Value scalar(String text, EDataType dataType) {
		String value = text == null ? "" : text;
		Class<?> instanceClass = dataType == null ? null : dataType.getInstanceClass();
		if (instanceClass != null) {
			String trimmed = value.trim();
			if (isNumeric(instanceClass) && JSON_NUMBER.matcher(trimmed).matches()) {
				return new JNumber(trimmed, 0);
			}
			if ((instanceClass == Boolean.class || instanceClass == boolean.class)
					&& ("true".equals(trimmed) || "false".equals(trimmed))) {
				return new JBoolean(Boolean.parseBoolean(trimmed), 0);
			}
		}
		return new JString(value, 0);
	}

	private static boolean isNumeric(Class<?> type) {
		return Number.class.isAssignableFrom(type) || type == int.class || type == long.class || type == short.class
				|| type == byte.class || type == double.class || type == float.class;
	}

	private static EClass typeOf(EStructuralFeature feature) {
		return feature != null && feature.getEType() instanceof EClass eClass ? eClass : null;
	}

	/** The data type of the text of an element: the feature's type or the type of its class's simple content. */
	private static EDataType dataTypeOf(EStructuralFeature feature, EClass type) {
		if (feature != null && feature.getEType() instanceof EDataType dataType) {
			return dataType;
		}
		if (type != null) {
			EStructuralFeature simple = META.getSimpleFeature(type);
			if (simple != null && simple.getEType() instanceof EDataType dataType) {
				return dataType;
			}
		}
		return null;
	}

	/** The element feature of a class with a local name (in any namespace), or {@code null}. */
	private static EStructuralFeature findElement(EClass type, String local) {
		for (EStructuralFeature feature : META.getAllElements(type)) {
			if (local.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	/** The attribute feature of a class with a local name (in any namespace), or {@code null}. */
	private static EStructuralFeature findAttribute(EClass type, String local) {
		for (EStructuralFeature feature : META.getAllAttributes(type)) {
			if (local.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------------------------------
	// JSON to XML
	// ----------------------------------------------------------------------------------------------------

	/** Converts JSON text to the XML form of the document. */
	public static XmlForm toXml(String json) throws JsonException {
		Value parsed = Json.parse(json);
		if (!(parsed instanceof JObject document)) {
			throw new JsonException("A Healthcare LOM JSON document must be an object with a \"lom\" member",
					parsed.line(), 1);
		}
		return new ToXml().document(document);
	}

	private static final class ToXml {
		private final StringBuilder xml = new StringBuilder();
		private final List<Integer> lines = new ArrayList<>(List.of(0, 0));
		private final Map<String, String> namespaces = standardPrefixes();
		private int currentJsonLine;

		XmlForm document(JObject document) throws JsonException {
			Value declared = document.get(NAMESPACES_MEMBER);
			if (declared instanceof JObject declarations) {
				for (Member member : declarations.members().values()) {
					if (!(member.value() instanceof JString uri)) {
						throw new JsonException("Namespace \"" + member.name() + "\" must be a string", member.line(), 1);
					}
					namespaces.put(member.name(), uri.value());
				}
			}
			Member lomMember = document.members().get("lom");
			JObject lom;
			int lomLine;
			if (lomMember != null) {
				if (!(lomMember.value() instanceof JObject object)) {
					throw new JsonException("\"lom\" must be an object", lomMember.line(), 1);
				}
				lom = object;
				lomLine = lomMember.line();
			} else if (document.members().containsKey("general") || document.members().containsKey("healthcareMetadata")) {
				// The content of the lom element, without the wrapper.
				lom = document;
				lomLine = document.line();
			} else {
				throw new JsonException("A Healthcare LOM JSON document must have a \"lom\" member", document.line(), 1);
			}

			currentJsonLine = lomLine;
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			newLine(lomLine);
			EClass lomType = typeOf(META.getElement(LomPackage.eNS_URI, "lom"));
			xml.append("<lom xmlns=\"").append(LomPackage.eNS_URI).append('"');
			for (Map.Entry<String, String> namespace : namespaces.entrySet()) {
				if (!"lom".equals(namespace.getKey())) {
					xml.append(" xmlns:").append(namespace.getKey()).append("=\"");
					escape(namespace.getValue(), true);
					xml.append('"');
				}
			}
			String schemaLocation = document.get(SCHEMA_LOCATION_MEMBER) instanceof JString location ? location.value()
					: DEFAULT_SCHEMA_LOCATION;
			xml.append(" xsi:schemaLocation=\"");
			escape(schemaLocation, true);
			xml.append('"');
			content(lom, lomType, LomPackage.eNS_URI, "lom", 1);
			return new XmlForm(xml.toString(), lines.stream().mapToInt(Integer::intValue).toArray());
		}

		private void newLine(int jsonLine) {
			xml.append('\n');
			lines.add(jsonLine);
		}

		/** Writes the attributes, the end of the start tag, the content and the end tag of an element. */
		private void content(JObject object, EClass type, String ns, String tag, int depth) throws JsonException {
			boolean simpleContent = type != null && META.getContentKind(type) == ExtendedMetaData.SIMPLE_CONTENT;
			List<Member> elements = new ArrayList<>();
			Member text = null;
			for (Member member : object.members().values()) {
				String name = member.name();
				if (name.startsWith("$")) {
					continue;
				}
				if (CONTENT.equals(name) && (type == null || simpleContent || findElement(type, CONTENT) == null)) {
					text = member;
				} else if (name.startsWith("@") || (type != null && findAttribute(type, name) != null)
						|| (simpleContent && findElement(type, name) == null)) {
					attribute(member, type);
				} else {
					elements.add(member);
				}
			}
			if (text == null && elements.isEmpty()) {
				xml.append("/>");
				return;
			}
			xml.append('>');
			if (text != null) {
				escape(scalarText(text.value(), text), false);
			} else {
				List<EStructuralFeature> order = type == null ? List.of() : META.getAllElements(type);
				elements.sort(Comparator.comparingInt(member -> {
					EStructuralFeature feature = resolveFeature(member.name(), type);
					int index = feature == null ? -1 : order.indexOf(feature);
					return index < 0 ? Integer.MAX_VALUE : index;
				}));
				for (Member member : elements) {
					element(member, type, ns, depth);
				}
				newLine(currentJsonLine);
				xml.repeat("   ", depth - 1);
			}
			xml.append("</").append(tag).append('>');
		}

		private EStructuralFeature resolveFeature(String key, EClass parent) {
			if (parent == null) {
				return null;
			}
			int colon = key.indexOf(':');
			if (colon < 0) {
				return findElement(parent, key);
			}
			String ns = namespaces.get(key.substring(0, colon));
			return ns == null ? null : META.getElement(parent, ns, key.substring(colon + 1));
		}

		private void attribute(Member member, EClass type) throws JsonException {
			String name = member.name().startsWith("@") ? member.name().substring(1) : member.name();
			String qualified = name;
			int colon = name.indexOf(':');
			if (colon > 0) {
				checkPrefix(name.substring(0, colon), member);
			} else if (type != null) {
				EStructuralFeature feature = findAttribute(type, name);
				String ns = feature == null ? null : META.getNamespace(feature);
				if (ns != null && !ns.isEmpty()) {
					qualified = prefixFor(ns) + ":" + name;
				}
			}
			if (member.value() instanceof JObject || member.value() instanceof JArray) {
				throw new JsonException("The attribute \"" + member.name() + "\" must have a string value", member.line(), 1);
			}
			if (member.value() instanceof JNull) {
				return;
			}
			xml.append(' ').append(qualified).append("=\"");
			escape(scalarText(member.value(), member), true);
			xml.append('"');
		}

		private String prefixFor(String ns) {
			for (Map.Entry<String, String> entry : namespaces.entrySet()) {
				if (entry.getValue().equals(ns) && !"lom".equals(entry.getKey())) {
					return entry.getKey();
				}
			}
			String prefix = "ns" + namespaces.size();
			namespaces.put(prefix, ns);
			return prefix;
		}

		private void checkPrefix(String prefix, Member member) throws JsonException {
			if (!namespaces.containsKey(prefix)) {
				throw new JsonException("Unknown namespace prefix \"" + prefix + "\" in \"" + member.name()
						+ "\" (declare it in \"$namespaces\")", member.line(), 1);
			}
		}

		private void element(Member member, EClass parentType, String parentNs, int depth) throws JsonException {
			String key = member.name();
			String ns;
			String local;
			EStructuralFeature feature;
			int colon = key.indexOf(':');
			if (colon > 0) {
				String prefix = key.substring(0, colon);
				checkPrefix(prefix, member);
				ns = namespaces.get(prefix);
				local = key.substring(colon + 1);
				feature = parentType == null ? null : META.getElement(parentType, ns, local);
			} else {
				local = key;
				feature = parentType == null ? null : findElement(parentType, key);
				ns = feature == null ? parentNs : META.getNamespace(feature);
			}
			if (feature == null) {
				feature = META.getElement(ns, local);
			}
			String tag = ns == null || ns.isEmpty() || LomPackage.eNS_URI.equals(ns) ? local : prefixFor(ns) + ":" + local;
			EClass type = typeOf(feature);
			if (member.value() instanceof JArray array) {
				for (Value item : array.items()) {
					if (item instanceof JArray) {
						throw new JsonException("Nested arrays are not allowed (in \"" + key + "\")", item.line(), 1);
					}
					single(item, item.line() > 0 ? item.line() : member.line(), member, tag, type, ns, depth);
				}
			} else {
				single(member.value(), member.line(), member, tag, type, ns, depth);
			}
		}

		private void single(Value value, int jsonLine, Member member, String tag, EClass type, String ns, int depth)
				throws JsonException {
			if (value instanceof JNull) {
				return;
			}
			currentJsonLine = jsonLine;
			newLine(jsonLine);
			xml.repeat("   ", depth).append('<').append(tag);
			if (value instanceof JObject object) {
				content(object, type, ns, tag, depth + 1);
				currentJsonLine = jsonLine;
			} else {
				String text = scalarText(value, member);
				if (text.isEmpty()) {
					xml.append("/>");
				} else {
					xml.append('>');
					escape(text, false);
					xml.append("</").append(tag).append('>');
				}
			}
		}

		private String scalarText(Value value, Member member) throws JsonException {
			return switch (value) {
				case JString string -> string.value();
				case JNumber number -> number.literal().contains("e") || number.literal().contains("E")
						? new BigDecimal(number.literal()).toPlainString()
						: number.literal();
				case JBoolean bool -> Boolean.toString(bool.value());
				case JNull _ -> "";
				default -> throw new JsonException("\"" + member.name() + "\" must be a string, number or boolean here",
						value.line() > 0 ? value.line() : member.line(), 1);
			};
		}

		/** Appends escaped text; line breaks in the text are mapped to the current JSON line. */
		private void escape(String text, boolean attribute) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				switch (c) {
					case '&' -> xml.append("&amp;");
					case '<' -> xml.append("&lt;");
					case '>' -> xml.append("&gt;");
					case '"' -> xml.append(attribute ? "&quot;" : "\"");
					case '\r' -> xml.append("&#13;");
					case '\t' -> xml.append(attribute ? "&#9;" : "\t");
					case '\n' -> {
						if (attribute) {
							xml.append("&#10;");
						} else {
							newLine(currentJsonLine);
						}
					}
					default -> xml.append(c);
				}
			}
		}
	}
}
