/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.type;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.FieldHelp;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomRules;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.report.HlomSummary;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.model.support.search.FieldGroup;
import edu.louisiana.caai.lom.model.support.search.HlomSearchFields;

/** Healthcare LOM v1 (ANSI/MEDBIQ LO.10.1-2008): the generated model and its own validation and rules. */
public enum HlomDocumentType implements DocumentType {

	INSTANCE;

	@Override
	public String id() {
		return "hlom-v1";
	}

	@Override
	public String standard() {
		return "Healthcare LOM";
	}

	@Override
	public String version() {
		return "1.0";
	}

	@Override
	public String namespace() {
		return HlomResources.LOM_NS;
	}

	@Override
	public String rootElement() {
		return "lom";
	}

	@Override
	public String specificationUrl() {
		return "https://www.medbiq.org/standards/healthcare-learning-object-metadata";
	}

	@Override
	public String schemaLocation() {
		return HlomResources.HLOM_SCHEMA_URL;
	}

	@Override
	public boolean supportsJson() {
		return true;
	}

	@Override
	public ResourceSet createResourceSet() {
		return HlomResources.createResourceSet();
	}

	@Override
	public List<HlomProblem> validateSchema(byte[] xml, EObject documentRoot) {
		return HlomValidator.validateSchema(xml, documentRoot instanceof DocumentRoot root ? root : null);
	}

	@Override
	public List<HlomProblem> checkRules(EObject documentRoot) {
		return documentRoot instanceof DocumentRoot root ? HlomRules.check(root) : List.of();
	}

	@Override
	public String title(EObject documentRoot) {
		return documentRoot instanceof DocumentRoot root ? HlomSummary.of(root).title() : null;
	}

	@Override
	public String description(EObject documentRoot) {
		return documentRoot instanceof DocumentRoot root ? HlomSummary.of(root).description() : null;
	}

	@Override
	public List<DocumentLinks.LinkPath> linkPaths() {
		return List.of(new DocumentLinks.LinkPath("relation/resource/identifier", "Related resource"));
	}

	@Override
	public List<FieldGroup> searchFields() {
		return HlomSearchFields.ALL;
	}

	@Override
	public String fieldHelp(List<String> names) {
		return FieldHelp.text(FieldHelp.keyOf(names));
	}

	@Override
	public EObject createDocument(String title, String language) {
		return HlomDocuments.createDocument(title, language);
	}
}
