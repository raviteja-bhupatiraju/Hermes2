/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * The Ecore models of the MedBiquitous standards ({@code model/*.ecore}, generated from the schemas by
 * {@code tools/schematool}). They are loaded once and registered by namespace URI in the global package registry,
 * as dynamic packages (no generated code). References to Healthcare LOM classes resolve to the generated HLOM
 * packages.
 */
public final class MedbiqModels {

	/** The scheme of the URIs the models are loaded with (they are read from the class path). */
	static final String SCHEME = "medbiq";

	private static volatile Map<String, EPackage> packages;

	private MedbiqModels() {
	}

	/** The packages by namespace URI, loading and registering them on first use. */
	public static Map<String, EPackage> packages() {
		Map<String, EPackage> result = packages;
		if (result == null) {
			synchronized (MedbiqModels.class) {
				result = packages;
				if (result == null) {
					packages = result = load();
				}
			}
		}
		return result;
	}

	/** The package of a namespace, or {@code null}. */
	public static EPackage get(String namespace) {
		return packages().get(namespace);
	}

	private static Map<String, EPackage> load() {
		HlomResources.initStandalone();
		// EMF's own packages of the XML namespaces must be registered before the models' versions of them: saving
		// xml: attributes uses the generated XMLNamespacePackage, which fails if its namespace has another package.
		org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage.eINSTANCE.eClass();
		org.eclipse.emf.ecore.xml.type.XMLTypePackage.eINSTANCE.eClass();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(SCHEME, new EcoreResourceFactoryImpl());
		resourceSet.getURIConverter().getURIHandlers().addFirst(new ClassPathHandler());
		resourceSet.setPackageRegistry(EPackage.Registry.INSTANCE);
		Map<String, EPackage> loaded = new LinkedHashMap<>();
		try (InputStream index = open("model/index.txt")) {
			if (index == null) {
				throw new IOException("model/index.txt not found");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(index, StandardCharsets.UTF_8));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				if (line.isBlank() || line.startsWith("#")) {
					continue;
				}
				String[] parts = line.strip().split("\\s+");
				Resource resource = resourceSet.getResource(URI.createURI(SCHEME + ":/model/" + parts[0]), true);
				EPackage ePackage = (EPackage) resource.getContents().getFirst();
				loaded.put(ePackage.getNsURI(), ePackage);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("The MedBiquitous models could not be loaded", e);
		}
		// With all references between the models resolved, each model takes its namespace as URI (like generated
		// packages): documents then refer to the namespaces, not to medbiq: URIs (xsi:schemaLocation, hrefs).
		EcoreUtil.resolveAll(resourceSet);
		for (EPackage ePackage : loaded.values()) {
			ePackage.eResource().setURI(URI.createURI(ePackage.getNsURI()));
		}
		for (EPackage ePackage : loaded.values()) {
			// Packages registered by others (e.g. generated ones) win.
			if (!EPackage.Registry.INSTANCE.containsKey(ePackage.getNsURI())) {
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
			}
		}
		return Map.copyOf(loaded);
	}

	/** Opens a resource of this bundle (class path relative to its root), or returns {@code null}. */
	static InputStream open(String path) {
		return MedbiqModels.class.getResourceAsStream("/" + path);
	}

	/** Reads {@code medbiq:/path} URIs from the class path. */
	private static final class ClassPathHandler extends URIHandlerImpl {
		@Override
		public boolean canHandle(URI uri) {
			return SCHEME.equals(uri.scheme());
		}

		@Override
		public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
			InputStream stream = open(uri.path().substring(1));
			if (stream == null) {
				throw new IOException("Not found: " + uri);
			}
			return stream;
		}

		@Override
		public boolean exists(URI uri, Map<?, ?> options) {
			try (InputStream stream = open(uri.path().substring(1))) {
				return stream != null;
			} catch (IOException _) {
				return false;
			}
		}

		@Override
		public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
			return Map.of(URIConverter.ATTRIBUTE_READ_ONLY, Boolean.TRUE);
		}
	}
}
