/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 * The compiled HLOM v1 XML schema, loaded from the copy bundled in this plug-in ({@code model/schema}).
 * <p>
 * All schema documents are resolved from the class path through a private {@code hlom:} URI scheme, so
 * validation works offline, inside and outside OSGi, and never follows {@code schemaLocation} hints to
 * the network.
 */
public final class HlomSchema {

	/** Class path folder holding the schema documents. */
	static final String SCHEMA_FOLDER = "/model/schema/";

	/** Entry point of the composite HLOM schema. */
	static final String ROOT_SCHEMA = "healthcarelom.xsd";

	private static final String SCHEME_BASE = "hlom:/schema/";

	private static volatile Schema schema;

	private HlomSchema() {
	}

	/** Returns the compiled schema; it is thread-safe and cached. */
	public static Schema get() throws SAXException {
		Schema result = schema;
		if (result == null) {
			synchronized (HlomSchema.class) {
				result = schema;
				if (result == null) {
					schema = result = compile();
				}
			}
		}
		return result;
	}

	private static Schema compile() throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setResourceResolver(new ClasspathResolver());
		InputStream stream = open(SCHEME_BASE + ROOT_SCHEMA);
		if (stream == null) {
			throw new SAXException("HLOM schema not found on the class path: " + SCHEMA_FOLDER + ROOT_SCHEMA);
		}
		return factory.newSchema(new StreamSource(stream, SCHEME_BASE + ROOT_SCHEMA));
	}

	/** Opens a schema document identified by an {@code hlom:} URI, or returns {@code null}. */
	private static InputStream open(String hlomUri) {
		if (hlomUri == null || !hlomUri.startsWith(SCHEME_BASE)) {
			return null;
		}
		return HlomSchema.class.getResourceAsStream(SCHEMA_FOLDER + hlomUri.substring(SCHEME_BASE.length()));
	}

	/** Resolves relative schema references against the bundled schema folder. */
	private static final class ClasspathResolver implements LSResourceResolver {
		@Override
		public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
				String baseURI) {
			if (systemId == null) {
				return null;
			}
			String resolved;
			try {
				resolved = baseURI != null ? URI.create(baseURI).resolve(systemId).toString() : systemId;
			} catch (IllegalArgumentException _) {
				return null;
			}
			InputStream stream = open(resolved);
			// Unknown (e.g. remote) references are refused rather than fetched.
			return new Input(publicId, resolved, baseURI, stream != null ? stream : InputStream.nullInputStream());
		}
	}

	/** Minimal {@link LSInput} backed by a byte stream. */
	private static final class Input implements LSInput {
		private String publicId;
		private String systemId;
		private String baseURI;
		private InputStream byteStream;

		Input(String publicId, String systemId, String baseURI, InputStream byteStream) {
			this.publicId = publicId;
			this.systemId = systemId;
			this.baseURI = baseURI;
			this.byteStream = byteStream;
		}

		@Override
		public Reader getCharacterStream() {
			return null;
		}

		@Override
		public void setCharacterStream(Reader characterStream) {
		}

		@Override
		public InputStream getByteStream() {
			return byteStream;
		}

		@Override
		public void setByteStream(InputStream byteStream) {
			this.byteStream = byteStream;
		}

		@Override
		public String getStringData() {
			return null;
		}

		@Override
		public void setStringData(String stringData) {
		}

		@Override
		public String getSystemId() {
			return systemId;
		}

		@Override
		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		@Override
		public String getPublicId() {
			return publicId;
		}

		@Override
		public void setPublicId(String publicId) {
			this.publicId = publicId;
		}

		@Override
		public String getBaseURI() {
			return baseURI;
		}

		@Override
		public void setBaseURI(String baseURI) {
			this.baseURI = baseURI;
		}

		@Override
		public String getEncoding() {
			return null;
		}

		@Override
		public void setEncoding(String encoding) {
		}

		@Override
		public boolean getCertifiedText() {
			return false;
		}

		@Override
		public void setCertifiedText(boolean certifiedText) {
		}
	}
}
