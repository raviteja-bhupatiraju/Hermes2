/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor;

import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;

/**
 * Item providers of the documents without generated item providers (the MedBiquitous standards besides Healthcare
 * LOM). Like those of Healthcare LOM, the labels name the element as the standard does, followed by what
 * identifies it ("Event — Cell Biology and Metabolism Lectures"), instead of the model's type names ("Event Type
 * E101"); the document root is named after its standard.
 */
public final class DocumentItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory {

	/** The names of the values that identify an element best, most telling first. */
	private static final List<String> NAMING = List.of("Title", "title", "Name", "name", "FormattedName",
			"Label", "label", "Description");

	public DocumentItemProviderAdapterFactory() {
		reflectiveItemProviderAdapter = new DocumentItemProvider(this);
	}

	@Override
	public Adapter createAdapter(Notifier target) {
		return reflectiveItemProviderAdapter;
	}

	private static final class DocumentItemProvider extends ReflectiveItemProvider {

		DocumentItemProvider(DocumentItemProviderAdapterFactory factory) {
			super(factory);
		}

		@Override
		public String getText(Object object) {
			if (!(object instanceof EObject eObject)) {
				return super.getText(object);
			}
			ExtendedMetaData metaData = ExtendedMetaData.INSTANCE;
			EClass eClass = eObject.eClass();
			if (metaData.getDocumentRoot(eClass.getEPackage()) == eClass) {
				DocumentType type = DocumentTypes.ofDocumentRoot(eObject);
				return type != null ? type.label() + " document" : "Document";
			}
			EStructuralFeature containing = eObject.eContainingFeature();
			String name = containing == null ? eClass.getName().replaceFirst("Type$", "")
					: metaData.getName(containing);
			String summary = summary(eObject);
			return summary == null ? words(name) : words(name) + " — " + summary;
		}

		/** What identifies an element: its title or name, else its first value. */
		private String summary(EObject eObject) {
			EClass eClass = eObject.eClass();
			for (String naming : NAMING) {
				EStructuralFeature feature = ExtendedMetaData.INSTANCE.getElement(eClass, null, naming);
				if (feature == null) {
					feature = eClass.getEStructuralFeature(naming);
				}
				String text = text(eObject, feature);
				if (text != null) {
					return text;
				}
			}
			return text(eObject, getLabelFeature(eClass));
		}

		private static String text(EObject eObject, EStructuralFeature feature) {
			if (!(feature instanceof EAttribute) || feature.isMany()
					|| feature.getEType().getInstanceClass() == FeatureMap.Entry.class) {
				return null;
			}
			Object value = eObject.eGet(feature);
			if (value == null) {
				return null;
			}
			String text = value.toString().strip().replaceAll("\\s+", " ");
			if (text.isEmpty()) {
				return null;
			}
			return text.length() > 80 ? text.substring(0, 79) + "…" : text;
		}

		/** An element name as words: "AcademicLevels" is "Academic Levels", "curriculumInventory" is "Curriculum Inventory". */
		private static String words(String name) {
			String spaced = name.replaceAll("([a-z0-9])([A-Z])", "$1 $2").replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2");
			return spaced.isEmpty() ? spaced : spaced.substring(0, 1).toUpperCase(Locale.ROOT) + spaced.substring(1);
		}
	}
}
