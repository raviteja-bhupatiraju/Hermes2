/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.healthcarevocab.HealthcarevocabPackage;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.util.LomResourceFactoryImpl;
import edu.louisiana.caai.lom.model.lom.util.LomResourceImpl;
import edu.louisiana.caai.lom.model.vocab.VocabPackage;

/**
 * Loading and saving of HLOM documents, usable both inside Eclipse/OSGi and in plain Java
 * (tests, command line tools, a future GraalVM native image).
 * <p>
 * Outside of Eclipse, call {@link #initStandalone()} once; it registers the EMF packages and the
 * resource factory statically, without relying on plug-in extension points or reflection.
 */
public final class HlomResources {

	/** The namespace of the IEEE LOM root element. */
	public static final String LOM_NS = LomPackage.eNS_URI;

	/** Published location of the HLOM v1 composite schema, used for {@code xsi:schemaLocation} in new documents. */
	public static final String HLOM_SCHEMA_URL = "https://ns.medbiq.org/lom/healthcarelom.xsd";

	private static volatile boolean standaloneInitialized;

	private HlomResources() {
	}

	/**
	 * Registers the HLOM packages and resource factory in the global EMF registries when not running inside
	 * Eclipse. Calling this inside Eclipse is harmless.
	 */
	public static synchronized void initStandalone() {
		if (standaloneInitialized) {
			return;
		}
		for (EPackage ePackage : allPackages()) {
			EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
		}
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			Map<String, Object> extensions = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
			extensions.putIfAbsent("xml", new LomResourceFactoryImpl());
			extensions.putIfAbsent("lom", new LomResourceFactoryImpl());
			extensions.putIfAbsent("json", new LomResourceFactoryImpl());
		}
		standaloneInitialized = true;
	}

	/** All generated packages making up the HLOM model. */
	public static EPackage[] allPackages() {
		return new EPackage[] { LomPackage.eINSTANCE, VocabPackage.eINSTANCE, HealthcarePackage.eINSTANCE,
				HealthcarevocabPackage.eINSTANCE, AddressPackage.eINSTANCE };
	}

	/** Whether a URI names a document stored in the JSON form (extension {@code .json}). */
	public static boolean isJson(URI uri) {
		return LomResourceImpl.isJson(uri);
	}

	/**
	 * Creates a resource set that loads every URI as an HLOM document (XML or JSON), independent of global
	 * registrations.
	 */
	public static ResourceSet createResourceSet() {
		initStandalone();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				new LomResourceFactoryImpl());
		return resourceSet;
	}

	/** Options used when saving HLOM documents. */
	public static Map<Object, Object> saveOptions() {
		Map<Object, Object> options = new HashMap<>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		options.put(XMLResource.OPTION_FORMATTED, Boolean.TRUE);
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// Writes the xsi:schemaLocation recorded in the document (DocumentRoot#getXSISchemaLocation).
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		options.put(XMLResource.OPTION_LINE_DELIMITER, "\n");
		return options;
	}

	/** Options used when loading HLOM documents. */
	public static Map<Object, Object> loadOptions() {
		Map<Object, Object> options = new HashMap<>();
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// Preserve unknown (extension) content instead of failing on it.
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
		// Documents come from anywhere (other tools, e-mail, uploads to the web edition): no external entities or
		// DTDs are read, so that a document can't pull in local files or reach other servers (XXE).
		Map<String, Boolean> features = new HashMap<>();
		features.put(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		features.put("http://xml.org/sax/features/external-general-entities", Boolean.FALSE);
		features.put("http://xml.org/sax/features/external-parameter-entities", Boolean.FALSE);
		features.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE);
		options.put(XMLResource.OPTION_PARSER_FEATURES, features);
		return options;
	}

	/** Loads an HLOM document from a URI. */
	public static Resource load(URI uri) throws IOException {
		Resource resource = createResourceSet().createResource(uri);
		resource.load(loadOptions());
		return resource;
	}

	/**
	 * Loads an HLOM document, tolerating invalid values. The returned resource holds whatever could be read;
	 * problems are available from {@link Resource#getErrors()} and {@link Resource#getWarnings()}.
	 */
	public static Resource loadLenient(URI uri) {
		Resource resource = createResourceSet().createResource(uri);
		try {
			resource.load(loadOptions());
		} catch (IOException e) {
			// Recorded in resource.getErrors(); a missing or unreadable file leaves the resource empty.
			if (resource.getErrors().isEmpty()) {
				resource.getErrors().add(new LoadProblem(e));
			}
		}
		return resource;
	}

	/** A load failure that is not tied to a position in the document. */
	private record LoadProblem(Exception exception) implements Resource.Diagnostic {
		@Override
		public String getMessage() {
			return exception.getMessage();
		}

		@Override
		public String getLocation() {
			return null;
		}

		@Override
		public int getLine() {
			return -1;
		}

		@Override
		public int getColumn() {
			return -1;
		}
	}

	/**
	 * Parses HLOM text into a new resource with the given URI (which is not read). Text starting with <code>{</code>
	 * is read as the JSON form, other text as XML.
	 */
	public static Resource parse(String xml, URI uri) throws IOException {
		Resource resource = createResourceSet().createResource(uri);
		resource.load(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), loadOptions());
		return resource;
	}

	/** Serializes a resource to the HLOM JSON form. */
	public static String toJson(Resource resource) throws IOException {
		return new String(toBytes(resource, LomResourceImpl.FORMAT_JSON), StandardCharsets.UTF_8);
	}

	/** Serializes a resource to HLOM XML text (also for documents stored as JSON). */
	public static String toXml(Resource resource) throws IOException {
		return new String(toBytes(resource), StandardCharsets.UTF_8);
	}

	/**
	 * Serializes a resource to UTF-8 encoded HLOM XML. The resource is left as it was: {@link Resource#save(
	 * java.io.OutputStream, Map)} clears the resource's errors and warnings (notifying its adapters), so the
	 * load diagnostics are restored and notifications are suppressed while serializing.
	 */
	public static byte[] toBytes(Resource resource) throws IOException {
		return toBytes(resource, LomResourceImpl.FORMAT_XML);
	}

	/** Serializes a resource in a format ({@link LomResourceImpl#FORMAT_XML} or {@link LomResourceImpl#FORMAT_JSON}). */
	private static byte[] toBytes(Resource resource, String format) throws IOException {
		Map<Object, Object> options = saveOptions();
		options.put(LomResourceImpl.OPTION_FORMAT, format);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		List<Resource.Diagnostic> errors = new ArrayList<>(resource.getErrors());
		List<Resource.Diagnostic> warnings = new ArrayList<>(resource.getWarnings());
		boolean deliver = resource.eDeliver();
		resource.eSetDeliver(false);
		try {
			resource.save(out, options);
		} finally {
			resource.getErrors().clear();
			resource.getErrors().addAll(errors);
			resource.getWarnings().clear();
			resource.getWarnings().addAll(warnings);
			resource.eSetDeliver(deliver);
		}
		return out.toByteArray();
	}

	/** Returns the document root of an HLOM resource, or {@code null} if the resource holds no HLOM document. */
	public static DocumentRoot getDocumentRoot(Resource resource) {
		if (resource != null && !resource.getContents().isEmpty()
				&& resource.getContents().getFirst() instanceof DocumentRoot root) {
			return root;
		}
		return null;
	}
}
