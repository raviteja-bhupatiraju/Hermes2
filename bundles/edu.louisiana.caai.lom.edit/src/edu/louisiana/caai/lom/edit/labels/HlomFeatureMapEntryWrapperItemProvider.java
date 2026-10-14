/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.edit.labels;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.provider.FeatureMapEntryWrapperItemProvider;

/**
 * Wraps a feature map entry (an element of a LOM content group) without the {@code "<feature>"} prefix of the
 * default wrapper: element entries show their value's label, simple entries {@code "Feature — value"}.
 */
public class HlomFeatureMapEntryWrapperItemProvider extends FeatureMapEntryWrapperItemProvider {

	/** Creates a wrapper for the entry at the given index of the owner's feature map attribute. */
	public HlomFeatureMapEntryWrapperItemProvider(FeatureMap.Entry entry, EObject owner, EAttribute attribute,
			int index, AdapterFactory adapterFactory, ResourceLocator resourceLocator) {
		super(entry, owner, attribute, index, adapterFactory, resourceLocator);
	}

	@Override
	protected String addEntryFeature(String text) {
		if (isEntryAttribute()) {
			return HlomLabels.featureName(getEntryFeature()) + HlomLabels.DASH + HlomLabels.truncate(text);
		}
		return text;
	}
}
