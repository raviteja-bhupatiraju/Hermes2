/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.check;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.ValidatorHandler;

import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * XML Schema validation of any document type with offline schemas: schema documents are opened by a function from
 * their URL (normally from the class path), never from the network; problems carry the element path, line and
 * column, and the model object at the path.
 */
public final class SchemaValidation {

	private SchemaValidation() {
	}

	/**
	 * Compiles a schema.
	 *
	 * @param rootUrl the URL of the root schema document
	 * @param opener  opens schema documents by URL; {@code null} for unknown documents (which are then empty)
	 */
	public static Schema compile(String rootUrl, Function<String, InputStream> opener) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setResourceResolver(new Resolver(opener));
		InputStream root = opener.apply(rootUrl);
		if (root == null) {
			throw new SAXException("Schema not found: " + rootUrl);
		}
		return factory.newSchema(new StreamSource(root, rootUrl));
	}

	/**
	 * Validates XML against a schema.
	 *
	 * @param documentRoot the document root the problems' model objects are looked up in; may be {@code null}
	 */
	public static List<HlomProblem> validate(Schema schema, byte[] xml, EObject documentRoot) {
		List<HlomProblem> problems = new ArrayList<>();
		try {
			ValidatorHandler validator = schema.newValidatorHandler();
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			parserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			parserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			XMLReader reader = parserFactory.newSAXParser().getXMLReader();
			PathTracker tracker = new PathTracker(reader);
			tracker.setContentHandler(validator);
			ErrorHandler collector = new Collector(tracker, documentRoot, problems);
			validator.setErrorHandler(collector);
			tracker.setErrorHandler(collector);
			tracker.parse(new InputSource(new ByteArrayInputStream(xml)));
		} catch (SAXParseException _) {
			// Reported through the error handler (fatal errors are rethrown by the parser).
		} catch (SAXException | ParserConfigurationException | IOException e) {
			problems.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, "Schema validation failed: " + e.getMessage(),
					null, null, -1, -1));
		}
		return problems;
	}

	/** Removes the Xerces error code prefix ("cvc-complex-type.2.4.a: ") and namespace noise from messages. */
	public static String cleanMessage(String message) {
		if (message == null) {
			return "";
		}
		String result = message.replaceFirst("^cvc-[\\w.\\-]+: ", "");
		return result.replaceAll("\\{\"[^\"]*\":([^}]+)\\}", "$1");
	}

	/** Passes SAX events on while tracking the element path. */
	private static final class PathTracker extends XMLFilterImpl {
		private final Deque<ElementPath.Step> stack = new ArrayDeque<>();
		private final Deque<Map<String, Integer>> counts = new ArrayDeque<>();

		PathTracker(XMLReader parent) {
			super(parent);
			counts.push(new HashMap<>());
		}

		ElementPath path() {
			List<ElementPath.Step> steps = new ArrayList<>(stack);
			java.util.Collections.reverse(steps);
			return new ElementPath(steps);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			int index = counts.peek().merge(uri + '#' + localName, 1, Integer::sum) - 1;
			stack.push(new ElementPath.Step(uri, localName, index));
			counts.push(new HashMap<>());
			super.startElement(uri, localName, qName, atts);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			stack.pop();
			counts.pop();
		}
	}

	/** Records SAX errors as problems at the current element path. */
	private record Collector(PathTracker tracker, EObject documentRoot, List<HlomProblem> problems)
			implements ErrorHandler {

		@Override
		public void warning(SAXParseException exception) {
			add(Severity.WARNING, exception);
		}

		@Override
		public void error(SAXParseException exception) {
			add(Severity.ERROR, exception);
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			add(Severity.ERROR, exception);
			throw exception;
		}

		private void add(Severity severity, SAXParseException exception) {
			ElementPath path = tracker.path();
			EObject object = documentRoot == null || path.isEmpty() ? null : path.resolve(documentRoot);
			problems.add(new HlomProblem(severity, Origin.SCHEMA, cleanMessage(exception.getMessage()), object,
					path.isEmpty() ? null : path.toString(), exception.getLineNumber(), exception.getColumnNumber()));
		}
	}

	/** Resolves schema references (relative to their document) through the opener; unknown ones are empty. */
	private record Resolver(Function<String, InputStream> opener) implements LSResourceResolver {

		@Override
		public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
				String baseURI) {
			if (systemId == null) {
				return null;
			}
			String resolved;
			try {
				resolved = baseURI != null ? URI.create(baseURI).resolve(systemId.strip()).toString() : systemId.strip();
			} catch (IllegalArgumentException _) {
				return null;
			}
			InputStream stream = opener.apply(resolved);
			return new Input(publicId, resolved, baseURI, stream != null ? stream : InputStream.nullInputStream());
		}
	}

	/** An {@link LSInput} backed by a byte stream. */
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
