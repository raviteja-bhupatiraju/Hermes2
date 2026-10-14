/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IViewerNotification;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.edit.labels.HlomLabels;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.LangString;
import edu.louisiana.caai.lom.model.lom.LomFactory;
import edu.louisiana.caai.lom.model.lom.Title;
import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * Checks the friendly labels of the edit bundle's item providers as shown in the document tree, the outline and
 * the Properties view.
 */
class LabelsTest {

	private static final String DASH = " — ";

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryItemDelegator delegator;

	@BeforeEach
	void setUp() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		delegator = new AdapterFactoryItemDelegator(adapterFactory);
	}

	@AfterEach
	void tearDown() {
		adapterFactory.dispose();
	}

	@Test
	void courseTreeHasFriendlyLabels() throws IOException {
		List<String> labels = treeLabels(load("insulin-pump-therapy-course.xml"));
		for (String expected : List.of("Healthcare LOM document", "LOM record" + DASH + "Insulin Pump Therapy",
				"General" + DASH + "Insulin Pump Therapy", "Identifier" + DASH + "URL: http://www.aafp.org/x29766.xml",
				"Catalog" + DASH + "URL", "Title" + DASH + "Insulin Pump Therapy [en]", "[en] Insulin Pump Therapy",
				"Keyword" + DASH + "insulin infusion systems (MeSH D007332)",
				"Life Cycle" + DASH + "version 1.0 · final", "Version" + DASH + "1.0 [en]", "Status" + DASH + "final",
				"LOMv1.0", "final", "Contributor" + DASH + "author: John Doe", "Entity" + DASH + "John Doe",
				"Contributor" + DASH + "publisher: American Academy of Family Physicians", "Date" + DASH + "2004-08-01",
				"Meta-Metadata" + DASH + "LOMv1.0, SCORM_CAM_v1.3, HEALTHCARE_LOMv1",
				"Metadata Schema" + DASH + "HEALTHCARE_LOMv1", "Technical" + DASH + "application/http",
				"Educational" + DASH + "continuing professional development",
				"Context" + DASH + "continuing professional development · HLOM", "Rights" + DASH + "cost: yes",
				"Copyright and Other Restrictions" + DASH + "yes",
				"Classification" + DASH + "competency: ACGME Core Competencies › Medical Knowledge",
				"Taxon" + DASH + "b: Medical Knowledge", "Healthcare metadata",
				"Healthcare education" + DASH + "CME 1.5 Credit", "Credits" + DASH + "CME 1.5 Credit · ACCME",
				"Target audience" + DASH + "physician · family practice", "Profession" + DASH + "physician [en]",
				"Activity Format" + DASH + "course [en]")) {
			assertTrue(labels.contains(expected), () -> "missing \"" + expected + "\" in " + labels);
		}
		String objective = labels.stream()
				.filter(l -> l.startsWith("Classification" + DASH + "educational objective: List the indications"))
				.findFirst().orElseThrow(() -> new AssertionError(labels.toString()));
		assertTrue(objective.endsWith("…"), objective);
	}

	@Test
	void labelsHaveNoFeaturePrefixesOrRawTypeNames() throws IOException {
		for (String sample : TestResources.SAMPLES) {
			for (String label : treeLabels(load(sample))) {
				assertFalse(label.startsWith("<"), label);
				assertFalse(label.contains("Lang String") || label.contains("Document Root")
						|| label.contains("Meta Metadata") || label.contains("Metadata Type"), label);
			}
		}
	}

	@Test
	void assetAndCreditsSummaries() throws IOException {
		List<String> asset = treeLabels(load("medical-image-asset.xml"));
		for (String expected : List.of("Healthcare asset" + DASH + "organ system", "Technical" + DASH + "image/jpeg",
				"Size" + DASH + "135090 bytes", "Educational" + DASH + "image", "Rights" + DASH + "cost: no",
				"Learning Resource Type" + DASH + "image · HLOM",
				"Contributor" + DASH + "publisher: Anystate Medical School")) {
			assertTrue(asset.contains(expected), () -> "missing \"" + expected + "\" in " + asset);
		}
		List<String> course = treeLabels(load("course-with-multiple-credit-types.xml"));
		String education = "Healthcare education" + DASH + "CME 1.5 Credit, CNE 1.7 Contact Hour";
		assertTrue(course.contains(education), () -> course.toString());
		assertTrue(course.contains("Target audience" + DASH + "physician · registered nurse · family practice"),
				() -> course.toString());
	}

	@Test
	void translationsAreCountedAndLabelsFollowChanges() throws IOException {
		DocumentRoot root = load("insulin-pump-therapy-course.xml");
		General general = root.getLom().getGeneral().getFirst();
		Title title = general.getTitle().getFirst();
		treeLabels(root); // creates the wrappers and adapters, as a viewer would
		List<IViewerNotification> notifications = new ArrayList<>();
		adapterFactory.addListener(n -> {
			if (n instanceof IViewerNotification viewerNotification) {
				notifications.add(viewerNotification);
			}
		});

		LangString french = LomFactory.eINSTANCE.createLangString();
		french.setLanguage("fr");
		french.setValue("Traitement par pompe à insuline");
		title.getString().add(french);
		assertEquals("Title" + DASH + "Insulin Pump Therapy [en] (+1 translation)", delegator.getText(title));

		notifications.clear();
		title.getString().getFirst().setValue("Insulin Pumps");
		assertEquals("General" + DASH + "Insulin Pumps", delegator.getText(general));
		assertEquals("LOM record" + DASH + "Insulin Pumps", delegator.getText(root.getLom()));
		assertTrue(notifications.stream().anyMatch(
				n -> n.isLabelUpdate() && AdapterFactoryEditingDomain.unwrap(n.getElement()) == general),
				"the General item is told to update its label");
		assertTrue(notifications.stream().anyMatch(n -> n.isLabelUpdate() && n.getElement() == root.getLom()),
				"the LOM record item is told to update its label");
	}

	@Test
	void createChildLabelsUseFriendlyNames() throws IOException {
		DocumentRoot root = load("insulin-pump-therapy-course.xml");
		EObject lom = root.getLom();
		IEditingDomainItemProvider provider = (IEditingDomainItemProvider) adapterFactory.adapt(lom,
				IEditingDomainItemProvider.class);
		Collection<?> descriptors = provider.getNewChildDescriptors(lom, null, null);
		List<String> texts = new ArrayList<>();
		for (Object descriptor : descriptors) {
			CommandParameter parameter = (CommandParameter) descriptor;
			texts.add(((CreateChildCommand.Helper) provider).getCreateChildText(lom, parameter.getFeature(),
					parameter.getValue(), null));
		}
		assertTrue(texts.contains("General"), texts::toString);
		assertTrue(texts.contains("Meta-Metadata"), texts::toString);
		assertTrue(texts.contains("Healthcare metadata"), texts::toString);
		assertFalse(texts.stream().anyMatch(t -> t.contains("Metadata Type") || t.contains("Meta Metadata")),
				texts::toString);
	}

	@Test
	void helpers() {
		assertEquals("John Doe", HlomLabels.vCardName("BEGIN:VCARD\nVERSION:3.0\nN:Doe;John;;;\nEND:VCARD"));
		assertEquals("Doe, Inc.", HlomLabels.vCardName("BEGIN:VCARD\r\nFN:Doe\\, Inc.\r\nEND:VCARD"));
		assertEquals("Plain text entity", HlomLabels.vCardName("Plain  text\nentity"));
		String truncated = HlomLabels.truncate("x".repeat(100));
		assertEquals(HlomLabels.MAX_LENGTH, truncated.length());
		assertTrue(truncated.endsWith("…"));
	}

	private static DocumentRoot load(String sample) throws IOException {
		return HlomResources.getDocumentRoot(HlomResources.load(TestResources.sample(sample)));
	}

	/** The labels of all tree items below (and including) the document root, as a tree viewer shows them. */
	private List<String> treeLabels(DocumentRoot root) {
		List<String> labels = new ArrayList<>();
		collect(root, labels, 0);
		return labels;
	}

	private void collect(Object item, List<String> labels, int depth) {
		labels.add(delegator.getText(item));
		if (depth > 12) {
			return;
		}
		for (Object child : delegator.getChildren(item)) {
			collect(child, labels, depth + 1);
		}
	}
}
