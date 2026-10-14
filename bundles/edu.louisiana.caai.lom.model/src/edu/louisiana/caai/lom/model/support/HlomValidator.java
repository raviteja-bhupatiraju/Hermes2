/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.check.SchemaValidation;
import edu.louisiana.caai.lom.model.support.HlomProblem.Origin;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;

/**
 * Validates HLOM documents against the HLOM XML schemas, the EMF model constraints and the HLOM best
 * practice rules.
 */
public final class HlomValidator {

	private HlomValidator() {
	}

	/** Runs all checks on a resource. */
	public static List<HlomProblem> validate(Resource resource) {
		return validate(resource, null);
	}

	/**
	 * Runs all checks on a resource.
	 *
	 * @param labeler optional function providing user-facing labels for model elements in messages
	 */
	public static List<HlomProblem> validate(Resource resource, Function<EObject, String> labeler) {
		List<HlomProblem> problems = new ArrayList<>();
		DocumentRoot root = HlomResources.getDocumentRoot(resource);
		if (root == null) {
			problems.add(new HlomProblem(Severity.ERROR, Origin.MODEL, "The document has no <lom> root element.", null,
					null, -1, -1));
			return problems;
		}
		try {
			problems.addAll(validateSchema(HlomResources.toBytes(resource), root));
		} catch (IOException e) {
			problems.add(new HlomProblem(Severity.ERROR, Origin.SCHEMA, "The document could not be serialized: "
					+ e.getMessage(), null, null, -1, -1));
		}
		problems.addAll(validateModel(root, labeler));
		problems.addAll(HlomRules.check(root));
		return problems;
	}

	/** Validates EMF model constraints (data types, patterns, multiplicities). */
	public static List<HlomProblem> validateModel(EObject root, Function<EObject, String> labeler) {
		Diagnostician diagnostician = new Diagnostician() {
			@Override
			public String getObjectLabel(EObject eObject) {
				return labeler != null ? labeler.apply(eObject) : super.getObjectLabel(eObject);
			}
		};
		List<HlomProblem> problems = new ArrayList<>();
		collect(diagnostician.validate(root), problems);
		return problems;
	}

	private static void collect(Diagnostic diagnostic, List<HlomProblem> problems) {
		if (diagnostic.getChildren().isEmpty()) {
			if (diagnostic.getSeverity() != Diagnostic.OK) {
				EObject object = null;
				// The data holds the invalid object first, but for invalid values also their data type (part of
				// the model, not of the document), which is skipped.
				for (Object data : diagnostic.getData()) {
					if (data instanceof EObject eObject && !(eObject instanceof org.eclipse.emf.ecore.EModelElement)) {
						object = eObject;
						break;
					}
				}
				problems.add(new HlomProblem(severity(diagnostic.getSeverity()), Origin.MODEL, diagnostic.getMessage(),
						object, null, -1, -1));
			}
		} else {
			for (Diagnostic child : diagnostic.getChildren()) {
				collect(child, problems);
			}
		}
	}

	private static Severity severity(int diagnosticSeverity) {
		return switch (diagnosticSeverity) {
			case Diagnostic.ERROR, Diagnostic.CANCEL -> Severity.ERROR;
			case Diagnostic.WARNING -> Severity.WARNING;
			default -> Severity.INFO;
		};
	}

	/**
	 * Validates serialized HLOM XML against the HLOM XML schemas.
	 *
	 * @param xml  the UTF-8 encoded document
	 * @param root the document root the XML was serialized from, used to map problems back to model
	 *             elements; may be {@code null}
	 */
	public static List<HlomProblem> validateSchema(byte[] xml, DocumentRoot root) {
		List<HlomProblem> problems;
		try {
			problems = SchemaValidation.validate(HlomSchema.get(), xml, root);
		} catch (org.xml.sax.SAXException e) {
			return List.of(new HlomProblem(Severity.ERROR, Origin.SCHEMA, "Schema validation failed: " + e.getMessage(),
					null, null, -1, -1));
		}
		if (root == null || root.getLom() == null) {
			return problems;
		}
		// Problems outside the elements of the record belong to the record.
		return problems.stream()
				.map(p -> p.object() != null ? p
						: new HlomProblem(p.severity(), p.origin(), p.message(), root.getLom(), p.path(), p.line(),
								p.column()))
				.toList();
	}
}
