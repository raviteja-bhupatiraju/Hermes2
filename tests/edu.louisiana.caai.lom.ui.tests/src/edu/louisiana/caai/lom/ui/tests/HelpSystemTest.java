/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IHelpResource;
import org.eclipse.help.IToc;
import org.eclipse.help.ITopic;
import org.eclipse.swt.widgets.Control;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.support.FieldHelp;
import edu.louisiana.caai.lom.model.support.HlomDocuments;

/** Tests the integration with the Eclipse help system: user guide, context help (F1) and topic links. */
class HelpSystemTest extends AbstractUiTest {

	private static final String HELP_BUNDLE = "edu.louisiana.caai.lom.help";

	private static IToc userGuide() {
		return Arrays.stream(HelpSystem.getTocs()).filter(toc -> toc.getLabel().equals("Hermes 2 User Guide"))
				.findFirst().orElseThrow(() -> new AssertionError("the user guide is not in the help system"));
	}

	/** A help href (/bundle/path#anchor or PLUGINS_ROOT/bundle/path) resolves to a file of a bundle. */
	private static void assertResolves(String href, String where) {
		String path = href.replaceFirst("#.*$", "").replaceFirst("^PLUGINS_ROOT/", "/").replaceFirst("^\\.\\./", "/");
		String[] parts = path.replaceFirst("^/", "").split("/", 2);
		Bundle bundle = parts.length == 2 ? Platform.getBundle(parts[0]) : null;
		URL entry = bundle == null ? null : bundle.getEntry(parts[1]);
		if (entry == null) {
			entry = Platform.getBundle(HELP_BUNDLE).getEntry(path.replaceFirst("^/", ""));
		}
		assertNotNull(entry, where + ": " + href + " doesn't resolve");
	}

	private static void collect(ITopic[] topics, List<ITopic> all) {
		for (ITopic topic : topics) {
			all.add(topic);
			collect(topic.getSubtopics(), all);
		}
	}

	@Test
	void userGuideTopicsExist() {
		IToc toc = userGuide();
		List<ITopic> topics = new ArrayList<>();
		collect(toc.getTopics(), topics);
		assertTrue(topics.size() >= 25, () -> "topics: " + topics.size());
		for (ITopic topic : topics) {
			assertResolves(topic.getHref(), "toc " + topic.getLabel());
		}
	}

	@Test
	void everyContextIdHasHelp() throws Exception {
		List<String> missing = new ArrayList<>();
		for (Field field : HelpContexts.class.getFields()) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == String.class) {
				String id = (String) field.get(null);
				IContext context = HelpSystem.getContext(id);
				if (context == null || context.getText() == null || context.getText().isBlank()) {
					missing.add(id);
					continue;
				}
				for (IHelpResource topic : context.getRelatedTopics()) {
					assertResolves(topic.getHref(), id);
				}
			}
		}
		for (String page : List.of("overview", "general", "lifecycle", "metametadata", "technical", "educational",
				"rights", "relation", "annotation", "classification", "healthcare-education", "healthcare-asset")) {
			if (HelpSystem.getContext(HelpContexts.page(page)) == null) {
				missing.add(HelpContexts.page(page));
			}
		}
		assertTrue(missing.isEmpty(), () -> "contexts without help: " + missing);
	}

	@Test
	void fieldsHaveContextHelp() {
		for (String key : List.of("title", "keyword", "creditType", "metaIdentifier", "accreditingBody")) {
			assertNotNull(FieldHelp.text(key), key);
			IContext context = HelpSystem.getContext(HelpContexts.field(key));
			assertNotNull(context, key);
			assertEquals(FieldHelp.text(key), context.getText(), key);
		}
	}

	@Test
	void formFieldsDeclareTheirContext() throws Exception {
		HlomMetadataEditor editor = open(save(HlomDocuments.createDocument("Help Test", "en"), "help-test.xml"));
		setActivePage(editor, pageIndex(editor, "general"));
		dispatch(200);
		Control page = pageControl(editor, pageIndex(editor, "general"));
		List<Control> withHelp = find((org.eclipse.swt.widgets.Composite) page, Control.class,
				control -> control.getData("org.eclipse.ui.help") instanceof String);
		assertFalse(withHelp.isEmpty(), "no field of the General page has context help");
		assertTrue(withHelp.stream().anyMatch(c -> HelpContexts.field("title").equals(c.getData("org.eclipse.ui.help"))),
				"the title field has no context help");
		page().closeEditor(editor, false);
	}
}
