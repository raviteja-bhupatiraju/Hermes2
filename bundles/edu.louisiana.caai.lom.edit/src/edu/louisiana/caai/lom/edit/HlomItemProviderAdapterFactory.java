/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.edit;

import java.util.MissingResourceException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import edu.louisiana.caai.lom.edit.labels.HlomFeatureMapEntryWrapperItemProvider;
import edu.louisiana.caai.lom.edit.labels.HlomLabelUpdates;
import edu.louisiana.caai.lom.edit.labels.HlomLabels;
import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.healthcare.HealthcarePackage;
import edu.louisiana.caai.lom.model.lom.LomPackage;

/**
 * The item providers of Healthcare LOM documents (the LOM, Healthcare and Address packages): EMF's reflective item
 * providers, which derive the tree children, properties and new child descriptors from the model, with the labels
 * of {@link HlomLabels} (in the words of the specification), the icon of each class, entries of the LOM content
 * groups without their feature prefix and labels that are updated when their descendants change.
 */
public final class HlomItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory {

	public HlomItemProviderAdapterFactory() {
		reflectiveItemProviderAdapter = new HlomItemProvider(this);
	}

	/** The Healthcare LOM packages (other packages, the MedBiquitous standards, have item providers of their own). */
	private static final java.util.Set<String> PACKAGES = java.util.Set.of(LomPackage.eNS_URI, HealthcarePackage.eNS_URI,
			AddressPackage.eNS_URI);

	@Override
	public boolean isFactoryForType(Object type) {
		return type instanceof org.eclipse.emf.ecore.EPackage ePackage ? PACKAGES.contains(ePackage.getNsURI())
				: supportedTypes.contains(type);
	}

	@Override
	public Adapter createAdapter(Notifier target) {
		return reflectiveItemProviderAdapter;
	}

	/** Also updates the labels of the containers, which summarize their descendants. */
	@Override
	public void fireNotifyChanged(Notification notification) {
		super.fireNotifyChanged(notification);
		HlomLabelUpdates.propagate(getRootAdapterFactory(), notification);
	}

	private static final class HlomItemProvider extends ReflectiveItemProvider {

		/** The features of the model that aren't content of the document. */
		private static final java.util.Set<String> NOT_CONTENT = java.util.Set.of("mixed", "xMLNSPrefixMap",
				"xSISchemaLocation");

		HlomItemProvider(HlomItemProviderAdapterFactory factory) {
			super(factory);
		}

		@Override
		public String getText(Object object) {
			return HlomLabels.getText(getAdapterFactory(), object);
		}

		/** The icon of the object's class ({@code full/obj16/<Class>}), else the generic one. */
		@Override
		public Object getImage(Object object) {
			if (object instanceof EObject eObject) {
				try {
					return overlayImage(object, HlomEditPlugin.INSTANCE.getImage("full/obj16/" + eObject.eClass().getName()));
				} catch (MissingResourceException _) {
					// No icon of its own.
				}
			}
			return super.getImage(object);
		}

		@Override
		protected String getFeatureText(Object feature) {
			return feature instanceof EStructuralFeature structural ? HlomLabels.featureName(structural)
					: super.getFeatureText(feature);
		}

		@Override
		protected String getTypeText(Object object) {
			return object instanceof EObject eObject ? HlomLabels.typeName(eObject.eClass())
					: object instanceof EClass eClass ? HlomLabels.typeName(eClass) : super.getTypeText(object);
		}

		/**
		 * The children of the elements, without what isn't content: the comments and whitespace of mixed content and
		 * the namespace declarations of the document root.
		 */
		@Override
		protected java.util.Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
			// One provider serves all classes: the features are computed for each object (as the superclass does).
			if (object instanceof EObject eObject && ExtendedMetaData.INSTANCE
					.getDocumentRoot(eObject.eClass().getEPackage()) == eObject.eClass()) {
				// The root element is one of the element features of the document root (derived from its content).
				return eObject.eClass().getEAllContainments().stream()
						.filter(reference -> !NOT_CONTENT.contains(reference.getName()))
						.toList();
			}
			return super.getChildrenFeatures(object).stream()
					.filter(feature -> !NOT_CONTENT.contains(feature.getName()))
					.toList();
		}

		/** New Child menus name the type of the new element (or the feature of a new value), as the specification. */
		@Override
		public String getCreateChildText(Object owner, Object feature, Object child, java.util.Collection<?> selection) {
			Object childFeature = child instanceof FeatureMap.Entry entry ? entry.getEStructuralFeature() : feature;
			Object value = child instanceof FeatureMap.Entry entry ? entry.getValue() : child;
			return value instanceof EObject eObject ? HlomLabels.typeName(eObject.eClass()) : getFeatureText(childFeature);
		}

		/** Entries of the LOM content groups are shown by their value, without the default "feature" prefix. */
		@Override
		protected Object createWrapper(EObject object, EStructuralFeature feature, Object value, int index) {
			if (FeatureMapUtil.isFeatureMap(feature) && isWrappingNeeded(object)) {
				return new HlomFeatureMapEntryWrapperItemProvider((FeatureMap.Entry) value, object, (EAttribute) feature,
						index, adapterFactory, HlomEditPlugin.INSTANCE);
			}
			return super.createWrapper(object, feature, value, index);
		}
	}
}
