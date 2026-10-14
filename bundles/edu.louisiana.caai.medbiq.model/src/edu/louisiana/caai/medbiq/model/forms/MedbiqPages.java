/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.SectionSpec;

/**
 * The form pages of the document types: the pages their standard defines, or pages derived from the model (one
 * page per part of the root element).
 */
public final class MedbiqPages {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	private MedbiqPages() {
	}

	/** The pages of a type: those its standard defines, else pages derived from the model. */
	public static List<PageSpec> of(MedbiqDocumentType type) {
		MedbiqStandards.init();
		List<PageSpec> defined = type.content().pages(type);
		return defined.isEmpty() ? derive(type) : defined;
	}

	/** Pages derived from the model: the simple values of the root element, then one page per child element. */
	public static List<PageSpec> derive(MedbiqDocumentType type) {
		EClass documentRoot = META.getDocumentRoot(type.ePackage());
		EStructuralFeature rootFeature = META.getElement(documentRoot, type.namespace(), type.rootElement());
		EClass root = ((EReference) rootFeature).getEReferenceType();
		List<FieldSpec> general = new ArrayList<>();
		List<PageSpec> pages = new ArrayList<>();
		for (EStructuralFeature feature : elementsAndAttributes(root)) {
			String name = name(feature);
			if (isSimple(feature)) {
				general.add(PageSpec.field(name));
			} else if (feature.isMany()) {
				pages.add(PageSpec.page(name, FieldLabels.words(name), "Content", null, "general",
						PageSpec.section(FieldLabels.words(name), null, "", PageSpec.repeat(name, null, null))));
			} else {
				pages.add(PageSpec.page(name, FieldLabels.words(name), "Content", null, "general",
						PageSpec.section(FieldLabels.words(name), null, name, PageSpec.auto(""))));
			}
		}
		if (!general.isEmpty()) {
			pages.addFirst(new PageSpec("general", "General", "Content", null, "general",
					List.of(new SectionSpec(FieldLabels.words(type.rootElement()), null, "", general))));
		}
		return pages;
	}

	/** The element and attribute features of a class in document order (wildcards and groups left out). */
	public static List<EStructuralFeature> elementsAndAttributes(EClass eClass) {
		List<EStructuralFeature> attributes = new ArrayList<>();
		List<EStructuralFeature> elements = new ArrayList<>();
		for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
			switch (META.getFeatureKind(feature)) {
				case ExtendedMetaData.ATTRIBUTE_FEATURE -> attributes.add(feature);
				case ExtendedMetaData.ELEMENT_FEATURE -> {
					if (!FeatureMapUtil.isFeatureMap(feature)) {
						elements.add(feature);
					}
				}
				default -> {
					// Simple content, wildcards and groups are not separate fields.
				}
			}
		}
		List<EStructuralFeature> all = new ArrayList<>(attributes);
		all.addAll(elements);
		return all;
	}

	/** The XML name of a feature, with {@code @} for attributes. */
	public static String name(EStructuralFeature feature) {
		String name = META.getName(feature);
		return META.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE ? "@" + name : name;
	}

	/** Whether a feature holds simple values: an attribute, or an element of a simple type or with simple content. */
	public static boolean isSimple(EStructuralFeature feature) {
		if (feature instanceof EAttribute) {
			return true;
		}
		return feature instanceof EReference reference && contentAttribute(reference.getEReferenceType()) != null
				&& elementsAndAttributes(reference.getEReferenceType()).stream()
						.noneMatch(f -> META.getFeatureKind(f) == ExtendedMetaData.ELEMENT_FEATURE);
	}

	/** The simple content attribute of a class (an element with simple content), or {@code null}. */
	public static EAttribute contentAttribute(EClass eClass) {
		for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
			if (META.getFeatureKind(feature) == ExtendedMetaData.SIMPLE_FEATURE && feature instanceof EAttribute attribute) {
				return attribute;
			}
		}
		return null;
	}
}
