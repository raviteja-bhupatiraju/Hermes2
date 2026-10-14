/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.mesh;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LanguageId;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.lom.MetaMetadata;
import edu.louisiana.caai.lom.model.support.mesh.MeshClient;
import edu.louisiana.caai.lom.model.support.mesh.MeshTerm;
import edu.louisiana.caai.lom.model.support.mesh.MeshTermValidator;

/**
 * Adds MeSH descriptors as keywords ({@code lom/general/keyword} with {@code hx:source="MeSH"} and
 * {@code hx:id}) through one undoable command.
 */
public final class MeshKeywordSupport {

	/** Language used when the document declares none. */
	public static final String FALLBACK_LANGUAGE = "en";

	private static final LomPackage LOM = LomPackage.eINSTANCE;

	private MeshKeywordSupport() {
	}

	/** The {@code lom} element of a document root or {@code lom} element, or {@code null}. */
	public static Lom lom(EObject object) {
		return switch (object) {
			case Lom lom -> lom;
			case DocumentRoot root -> root.getLom();
			case null, default -> null;
		};
	}

	/** The descriptor IDs of the MeSH keywords already in {@code lom/general}. */
	public static Set<String> existingIds(EObject lomOrRoot) {
		Set<String> ids = new LinkedHashSet<>();
		Lom lom = lom(lomOrRoot);
		if (lom != null) {
			for (General general : lom.getGeneral()) {
				for (Keyword keyword : general.getKeyword()) {
					if (MeshTermValidator.isMeshSource(keyword.getSource()) && keyword.getId() != null) {
						ids.add(keyword.getId().strip());
					}
				}
			}
		}
		return ids;
	}

	/**
	 * The language for new keywords: the first language of {@code general}, else of {@code metaMetadata},
	 * else {@value #FALLBACK_LANGUAGE}.
	 */
	public static String defaultLanguage(EObject lomOrRoot) {
		Lom lom = lom(lomOrRoot);
		if (lom != null) {
			for (General general : lom.getGeneral()) {
				String language = first(general.getLanguage());
				if (language != null) {
					return language;
				}
			}
			for (MetaMetadata metaMetadata : lom.getMetaMetadata()) {
				String language = first(metaMetadata.getLanguage());
				if (language != null) {
					return language;
				}
			}
		}
		return FALLBACK_LANGUAGE;
	}

	private static String first(List<LanguageId> languages) {
		for (LanguageId language : languages) {
			if (language.getValue() != null && !language.getValue().isBlank()
					&& !"none".equals(language.getValue().strip())) {
				return language.getValue().strip();
			}
		}
		return null;
	}

	/** Creates a detached keyword element for a descriptor. */
	public static Keyword createKeyword(MeshTerm term, String language) {
		Keyword keyword = LomFactory.eINSTANCE.createKeyword();
		keyword.setSource(MeshClient.SOURCE);
		keyword.setId(term.id());
		LangString string = LomFactory.eINSTANCE.createLangString();
		string.setValue(term.label());
		if (language != null && !language.isBlank()) {
			string.setLanguage(language.strip());
		}
		keyword.getString().add(string);
		return keyword;
	}

	/**
	 * Creates the command adding keywords for the terms to {@code lom/general} (creating {@code general} if
	 * missing). Terms whose ID is already a MeSH keyword, and duplicates within the list, are skipped.
	 *
	 * @param lomOrRoot the {@code lom} element or the document root
	 * @param language  the language of the keyword text, or {@code null} for {@link #defaultLanguage}
	 * @return the command, or {@code null} if there is nothing to add
	 */
	public static Command createAddCommand(EditingDomain domain, EObject lomOrRoot, List<MeshTerm> terms,
			String language) {
		Lom lom = lom(lomOrRoot);
		if (lom == null || terms == null || terms.isEmpty()) {
			return null;
		}
		String lang = language != null ? language : defaultLanguage(lom);
		Set<String> ids = existingIds(lom);
		List<Keyword> keywords = new ArrayList<>();
		for (MeshTerm term : terms) {
			if (term != null && ids.add(term.id())) {
				keywords.add(createKeyword(term, lang));
			}
		}
		if (keywords.isEmpty()) {
			return null;
		}
		CompoundCommand compound = new CompoundCommand(
				keywords.size() == 1 ? "Add MeSH Keyword" : "Add " + keywords.size() + " MeSH Keywords");
		ModelPath.Ensured general = ModelPath.of(LOM.getLom_General()).ensure(domain, lom);
		if (general.isNew()) {
			// The new general element is detached until the command runs, so it can be filled directly.
			((General) general.target()).getKeyword().addAll(keywords);
			compound.append(general.command());
		} else {
			compound.append(new AddCommand(domain, general.target(), LOM.getGeneral_Keyword(), keywords));
		}
		return compound;
	}

	/**
	 * Adds keywords for the terms through the domain's command stack (see {@link #createAddCommand}).
	 *
	 * @return the keywords added (empty if all terms were already present)
	 */
	public static List<Keyword> addKeywords(EditingDomain domain, EObject lom, List<MeshTerm> terms,
			String language) {
		Command command = createAddCommand(domain, lom, terms, language);
		if (!ModelCommands.execute(domain, command)) {
			return List.of();
		}
		List<Keyword> added = new ArrayList<>();
		for (Object result : command.getAffectedObjects()) {
			collectKeywords(result, added);
		}
		return added;
	}

	private static void collectKeywords(Object object, List<Keyword> added) {
		switch (object) {
			case Keyword keyword -> added.add(keyword);
			case General general -> added.addAll(general.getKeyword());
			case null, default -> {
			}
		}
	}
}
