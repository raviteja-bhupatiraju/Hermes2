/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import edu.louisiana.caai.lom.model.support.json.HlomJson;
import edu.louisiana.caai.lom.model.support.json.Json.JsonException;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <p>
 * Reads and writes both forms of HLOM documents: XML, and the JSON form of {@link HlomJson}. A document is read
 * as JSON when its text starts with <code>{</code>; it is written as JSON when its URI has the extension
 * {@code json}. The option {@link #OPTION_FORMAT} overrides the format for saving (e.g. to get the XML form of a
 * JSON document).
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.lom.util.LomResourceFactoryImpl
 * @generated NOT
 */
public class LomResourceImpl extends XMLResourceImpl {

	/** Save option selecting the format: {@link #FORMAT_XML} or {@link #FORMAT_JSON}. */
	public static final String OPTION_FORMAT = "edu.louisiana.caai.lom.format";

	/** Value of {@link #OPTION_FORMAT} for XML. */
	public static final String FORMAT_XML = "xml";

	/** Value of {@link #OPTION_FORMAT} for JSON. */
	public static final String FORMAT_JSON = "json";

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public LomResourceImpl(URI uri) {
		super(uri);
	}

	/** Whether documents with this URI are stored as JSON (the extension is {@code json}). */
	public static boolean isJson(URI uri) {
		return uri != null && FORMAT_JSON.equalsIgnoreCase(uri.fileExtension());
	}

	/** @generated NOT */
	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		BufferedInputStream in = new BufferedInputStream(inputStream);
		in.mark(64);
		byte[] start = new byte[64];
		int length = Math.max(0, in.readNBytes(start, 0, start.length));
		in.reset();
		if (!HlomJson.looksLikeJson(start, length)) {
			super.doLoad(in, options);
			return;
		}
		HlomJson.XmlForm form;
		try {
			form = HlomJson.toXml(new String(in.readAllBytes(), StandardCharsets.UTF_8));
		} catch (JsonException e) {
			LoadDiagnostic diagnostic = new LoadDiagnostic(e.getMessage(), getURI(), e.getLine(), e.getColumn());
			getErrors().add(diagnostic);
			throw new IOWrappedException(diagnostic);
		}
		try {
			super.doLoad(new ByteArrayInputStream(form.xml().getBytes(StandardCharsets.UTF_8)), options);
		} finally {
			// Problems refer to the lines of the JSON text, not of its XML form.
			mapLines(getErrors(), form);
			mapLines(getWarnings(), form);
		}
	}

	private void mapLines(List<Diagnostic> diagnostics, HlomJson.XmlForm form) {
		for (ListIterator<Diagnostic> it = diagnostics.listIterator(); it.hasNext();) {
			Diagnostic diagnostic = it.next();
			if (!(diagnostic instanceof LoadDiagnostic)) {
				int line = diagnostic.getLine() > 0 ? form.jsonLine(diagnostic.getLine()) : diagnostic.getLine();
				it.set(new LoadDiagnostic(stripLocation(diagnostic.getMessage()), getURI(), line, -1));
			}
		}
	}

	/** A parser message without its position in the XML form (SAXParseException style). */
	private static String stripLocation(String message) {
		return message == null ? null : message.replaceFirst("(?s)^.*?columnNumber:\\s*-?\\d+;\\s*", "");
	}

	/** @generated NOT */
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		Object format = options == null ? null : options.get(OPTION_FORMAT);
		boolean json = format == null ? isJson(getURI()) : FORMAT_JSON.equals(format);
		if (!json) {
			super.doSave(outputStream, options);
			return;
		}
		ByteArrayOutputStream xml = new ByteArrayOutputStream();
		super.doSave(xml, options);
		outputStream.write(HlomJson.toJson(xml.toByteArray()).getBytes(StandardCharsets.UTF_8));
	}

	/** A load problem of a JSON document, at a line of the JSON text. */
	public static final class LoadDiagnostic extends Exception implements Diagnostic {
		private static final long serialVersionUID = 1L;
		private final String location;
		private final int line;
		private final int column;

		LoadDiagnostic(String message, URI uri, int line, int column) {
			super(message);
			this.location = uri == null ? null : uri.toString();
			this.line = line;
			this.column = column;
		}

		@Override
		public String getLocation() {
			return location;
		}

		@Override
		public int getLine() {
			return line;
		}

		@Override
		public int getColumn() {
			return column;
		}
	}

} //LomResourceImpl
