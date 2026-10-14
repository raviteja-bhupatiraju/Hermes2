/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * Creates the resources of MedBiquitous documents: XML read and written with the extended metadata of the
 * schemas (registered for the standards' content types in plugin.xml). Loading registers the models first.
 */
public final class MedbiqResourceFactory extends XMLResourceFactoryImpl {

	@Override
	public Resource createResource(URI uri) {
		MedbiqStandards.init();
		XMLResource resource = new MedbiqResource(uri);
		resource.getDefaultLoadOptions().putAll(HlomResources.loadOptions());
		// The models have no references between documents: href attributes (XHTML links in Virtual Patient texts)
		// are values, not proxies.
		resource.getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		Map<Object, Object> save = resource.getDefaultSaveOptions();
		save.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		save.put(XMLResource.OPTION_ENCODING, "UTF-8");
		save.put(XMLResource.OPTION_FORMATTED, Boolean.TRUE);
		save.put(XMLResource.OPTION_LINE_DELIMITER, "\n");
		return resource;
	}

	/**
	 * A document resource that writes the document's own {@code xsi:schemaLocation} (the models have their
	 * namespaces as URIs, so EMF adds no locations of its own) and leaves out default values.
	 */
	private static final class MedbiqResource extends XMLResourceImpl {

		MedbiqResource(URI uri) {
			super(uri);
		}

		@Override
		public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
			Map<Object, Object> effective = options == null ? new HashMap<>() : new HashMap<>(options);
			effective.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			effective.remove(XMLResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION);
			effective.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.FALSE);
			super.doSave(outputStream, effective);
		}
	}
}
