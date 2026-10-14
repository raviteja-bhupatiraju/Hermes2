/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.check;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.check.TextPositions.Range;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;

/**
 * Checks the text of a document (XML or JSON) of any registered {@link DocumentType} like the editor does —
 * well-formedness, XML Schema, model constraints and the standard's rules — and places every problem at its range
 * in that text. Shared by the editor's background validation, the library, the command line tool and the
 * language server.
 * <p>
 * XML text is validated against the schemas as written (so schema problems have their exact positions); JSON text
 * is converted to XML for the schema check. Problems known only by their element are placed at the element's
 * start tag (XML) or member (JSON).
 */
public final class DocumentCheck {

	/**
	 * A problem placed in the text.
	 *
	 * @param problem the problem (its model object, if any, belongs to {@link Result#resource()})
	 * @param range   the range in the text, or {@code null} if unknown
	 */
	public record Located(HlomProblem problem, Range range) {

		/** The element path of the problem, or {@code null}. */
		public String path() {
			return problem.path();
		}
	}

	/**
	 * The result of a check.
	 *
	 * @param type     the type of the document (Healthcare LOM if the text isn't a known type)
	 * @param resource the loaded document (empty if the text could not be read)
	 * @param problems the problems in document order
	 */
	public record Result(DocumentType type, Resource resource, List<Located> problems) {

		/** The document root of any type, or {@code null} if the text could not be read. */
		public EObject documentRoot() {
			EObject first = resource.getContents().isEmpty() ? null : resource.getContents().getFirst();
			return first != null && DocumentTypes.ofDocumentRoot(first) != null ? first : null;
		}

		/** The Healthcare LOM document root, or {@code null} if the text isn't a readable HLOM document. */
		public DocumentRoot root() {
			return HlomResources.getDocumentRoot(resource);
		}

		/** The number of problems of a severity. */
		public long count(Severity severity) {
			return problems.stream().filter(p -> p.problem().severity() == severity).count();
		}
	}

	private DocumentCheck() {
	}

	/**
	 * Whether text (possibly incomplete, as typed) is meant to be a document of a registered type: XML whose first
	 * element is the root element of a type (in its namespace), or JSON with the root member of a type.
	 */
	public static boolean isDocument(String text) {
		return DocumentTypes.detect(text) != null;
	}

	/**
	 * Loads the text of a document (XML or JSON) with the resource set of its type, without checking it; what can't
	 * be read is in the resource's errors.
	 */
	public static Resource load(String text, URI uri) {
		DocumentType detected = DocumentTypes.detect(text);
		DocumentType type = detected != null ? detected : HlomDocumentType.INSTANCE;
		Resource resource = type.createResourceSet().createResource(uri);
		try {
			resource.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), HlomResources.loadOptions());
		} catch (IOException | RuntimeException _) {
			// Recorded in the resource's errors (or the resource stays empty).
		}
		return resource;
	}

	/**
	 * Checks the text of a document.
	 *
	 * @param text the document text
	 * @param uri  the URI of the document (its extension decides nothing: text starting with <code>{</code> is JSON)
	 */
	public static Result check(String text, URI uri) {
		boolean json = text.stripLeading().startsWith("{");
		DocumentType detected = DocumentTypes.detect(text);
		DocumentType type = detected != null ? detected : HlomDocumentType.INSTANCE;
		Resource resource = type.createResourceSet().createResource(uri);
		List<HlomProblem> problems = new ArrayList<>();
		List<HlomProblem> loadErrors = new ArrayList<>();
		try {
			resource.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), HlomResources.loadOptions());
		} catch (IOException | RuntimeException e) {
			if (resource.getErrors().isEmpty()) {
				loadErrors.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, HlomLibrary.loadMessage(e.getMessage()),
						null, null, -1, -1));
			}
		}
		for (Resource.Diagnostic error : resource.getErrors()) {
			loadErrors.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, HlomLibrary.loadMessage(error.getMessage()),
					null, null, error.getLine(), error.getColumn()));
		}
		EObject first = resource.getContents().isEmpty() ? null : resource.getContents().getFirst();
		EObject root = first != null && DocumentTypes.ofDocumentRoot(first) == type ? first : null;
		if (!json) {
			// The schemas check the text as written, also when values could not be read into the model; load errors
			// at the lines of schema problems report the same problems.
			List<HlomProblem> schema = type.validateSchema(text.getBytes(StandardCharsets.UTF_8), root);
			problems.addAll(schema);
			Set<Integer> lines = new HashSet<>();
			schema.forEach(problem -> lines.add(problem.line()));
			loadErrors.stream().filter(error -> !lines.contains(error.line()) && (error.line() > 0 || schema.isEmpty()))
					.forEach(problems::add);
		} else {
			problems.addAll(loadErrors);
		}
		if (root == null) {
			if (problems.isEmpty()) {
				problems.add(new HlomProblem(Severity.ERROR, Origin.MODEL, json
						? "The document has no \"" + type.rootElement() + "\" member."
						: "The document is not a MedBiquitous document of a known type (such as <lom> or <CurriculumInventory>).",
						null, null, -1, -1));
			}
			return locate(text, type, resource, problems, json);
		}
		if (json) {
			// Lines of schema problems of the converted XML don't exist in the JSON text; their paths do.
			try {
				for (HlomProblem problem : type.validateSchema(HlomResources.toBytes(resource), root)) {
					problems.add(new HlomProblem(problem.severity(), problem.origin(), problem.message(),
							problem.object(), problem.path(), -1, -1));
				}
			} catch (IOException e) {
				problems.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA,
						"The document could not be converted for the schema check: " + e.getMessage(), null, null, -1, -1));
			}
		}
		problems.addAll(HlomValidator.validateModel(root, null));
		problems.addAll(type.checkRules(root));
		return locate(text, type, resource, problems, json);
	}

	private static Result locate(String text, DocumentType type, Resource resource, List<HlomProblem> problems,
			boolean json) {
		TextPositions positions = TextPositions.of(text);
		List<Located> located = new ArrayList<>();
		for (HlomProblem problem : problems) {
			HlomProblem withPath = problem;
			if (problem.path() == null && problem.object() != null && problem.object().eResource() != null) {
				EObject object = problem.object();
				withPath = new HlomProblem(problem.severity(), problem.origin(), problem.message(), object,
						ElementPath.of(object).toString(), problem.line(), problem.column());
			}
			Range range = null;
			if (withPath.line() > 0 && !json || withPath.line() > 0 && withPath.path() == null) {
				range = Range.at(text, withPath.line(), withPath.column());
			} else if (withPath.path() != null) {
				range = positions.find(withPath.path());
			}
			located.add(new Located(withPath, range));
		}
		located.sort(Comparator.comparingInt((Located l) -> l.range() == null ? Integer.MAX_VALUE : l.range().startLine())
				.thenComparingInt(l -> l.range() == null ? 0 : l.range().startColumn()));
		return new Result(type, resource, List.copyOf(located));
	}
}
