/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.xml.validation.Schema;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xml.sax.SAXException;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.check.SchemaValidation;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.medbiq.model.standards.StandardContent;

/**
 * A MedBiquitous standard version with a dynamic model ({@link MedbiqModels}): documents are read and written as
 * XML with the extended metadata of the schemas, validated against the bundled schemas, and described by paths of
 * element names.
 */
public final class MedbiqDocumentType implements DocumentType {

	/**
	 * What describes a document type.
	 *
	 * @param id               the stable identifier
	 * @param standard         the name of the standard
	 * @param version          the version
	 * @param namespace        the namespace of the root element
	 * @param rootElement      the root element
	 * @param schemaUrl        the URL of the root schema (bundled under {@code schemas/})
	 * @param specificationUrl the web page of the standard
	 * @param template         the template of new documents ({@code templates/}), with {@code ${title}},
	 *                         {@code ${language}}, {@code ${date}}, {@code ${dateTime}} and {@code ${id}} placeholders
	 * @param content          what the standard contributes beyond its schemas
	 */
	public record Definition(String id, String standard, String version, String namespace, String rootElement,
			String schemaUrl, String specificationUrl, String template, StandardContent content) {
	}

	private final Definition definition;
	/** The compiled schema, released when memory is low (it is compiled again when needed). */
	private volatile java.lang.ref.SoftReference<Schema> schema = new java.lang.ref.SoftReference<>(null);
	private volatile Map<String, String> help;

	public MedbiqDocumentType(Definition definition) {
		this.definition = definition;
	}

	/** The definition. */
	public Definition definition() {
		return definition;
	}

	@Override
	public String id() {
		return definition.id();
	}

	@Override
	public String standard() {
		return definition.standard();
	}

	@Override
	public String version() {
		return definition.version();
	}

	@Override
	public String namespace() {
		return definition.namespace();
	}

	@Override
	public String rootElement() {
		return definition.rootElement();
	}

	@Override
	public String specificationUrl() {
		return definition.specificationUrl();
	}

	@Override
	public String schemaLocation() {
		return definition.schemaUrl();
	}

	/** The package of the root element's namespace. */
	public EPackage ePackage() {
		return MedbiqModels.get(definition.namespace());
	}

	@Override
	public ResourceSet createResourceSet() {
		MedbiqModels.packages();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new MedbiqResourceFactory());
		resourceSet.getLoadOptions().putAll(HlomResources.loadOptions());
		return resourceSet;
	}

	/** The compiled schema of the root element. */
	public Schema schema() throws SAXException {
		Schema result = schema.get();
		if (result == null) {
			synchronized (this) {
				result = schema.get();
				if (result == null) {
					result = SchemaValidation.compile(definition.schemaUrl(), MedbiqSchemas::open);
					schema = new java.lang.ref.SoftReference<>(result);
				}
			}
		}
		return result;
	}


	@Override
	public List<HlomProblem> validateSchema(byte[] xml, EObject documentRoot) {
		try {
			return SchemaValidation.validate(schema(), xml, documentRoot);
		} catch (SAXException e) {
			return List.of(new HlomProblem(Severity.ERROR, Origin.SCHEMA, "The schemas of " + label()
					+ " could not be loaded: " + e.getMessage(), null, null, -1, -1));
		}
	}

	/** What the standard contributes beyond its schemas. */
	public StandardContent content() {
		return definition.content();
	}

	@Override
	public List<HlomProblem> checkRules(EObject documentRoot) {
		try {
			return definition.content().rules(this, documentRoot);
		} catch (RuntimeException e) {
			return List.of(new HlomProblem(Severity.WARNING, Origin.RULES, "The rules of " + label()
					+ " could not be checked: " + e, null, null, -1, -1));
		}
	}

	@Override
	public String title(EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		return root == null ? null : definition.content().title(this, root);
	}

	@Override
	public String description(EObject documentRoot) {
		EObject root = Paths.rootElement(documentRoot);
		return root == null ? null : definition.content().description(this, root);
	}

	@Override
	public List<edu.louisiana.caai.lom.model.support.report.DocumentLinks.LinkPath> linkPaths() {
		return content().linkPaths(this);
	}

	/** The kinds of reference within documents of this type. */
	public List<References.Kind> references() {
		return content().references(this);
	}

	@Override
	public List<FieldGroup> searchFields() {
		return definition.content().searchFields(this);
	}

	@Override
	public String fieldHelp(List<String> names) {
		Map<String, String> help = help();
		for (int i = names.size() - 1; i >= 0; i--) {
			String name = names.get(i);
			String qualified = i > 0 ? names.get(i - 1) + "/" + name : name;
			String text = help.getOrDefault(qualified, help.get(name));
			if (text != null) {
				return text;
			}
		}
		return null;
	}

	private Map<String, String> help() {
		Map<String, String> result = help;
		if (result == null) {
			help = result = Map.copyOf(definition.content().help(this));
		}
		return result;
	}

	/** The XML of a new document (from the template). */
	public String newDocumentXml(String title, String language) {
		try (InputStream in = MedbiqModels.open("templates/" + definition.template())) {
			if (in == null) {
				throw new IOException("Template not found: " + definition.template());
			}
			java.time.ZonedDateTime now = java.time.ZonedDateTime.now().withNano(0);
			// The comments describing the template or its placeholders are for the template, not for the new document.
			return new String(in.readAllBytes(), StandardCharsets.UTF_8)
					.replaceAll("(?s)<!--\\s*Template of(?:(?!-->).)*-->\\R?", "")
					.replaceAll("(?s)<!--(?:(?!-->).)*\\$\\{(?:(?!-->).)*-->\\R?", "")
					.replace("${title}", xmlEscape(title == null || title.isBlank() ? "Untitled" : title))
					.replace("${language}", language == null || language.isBlank() ? "en" : language)
					.replace("${date}", now.toLocalDate().toString())
					.replace("${dateTime}", now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")))
					.replace("${id}", java.util.UUID.randomUUID().toString());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public EObject createDocument(String title, String language) {
		Resource resource = createResourceSet().createResource(URI.createURI("new.xml"));
		try {
			resource.load(new ByteArrayInputStream(newDocumentXml(title, language).getBytes(StandardCharsets.UTF_8)),
					null);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return resource.getContents().getFirst();
	}

	private static String xmlEscape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
	}

	@Override
	public String toString() {
		return label();
	}
}
