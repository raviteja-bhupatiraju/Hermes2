/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension3;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.TextInvocationContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.json.JsonContentAssistProcessor;
import edu.louisiana.caai.lom.editor.xml.XmlSourceEditor;
import edu.louisiana.caai.lom.editor.xml.XmlSourceViewerConfiguration;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Title;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;

/**
 * Content assist, hovers, tag editing and quick fixes of the XML page, content assist of the JSON page, and the
 * selection synchronization between the XML text and the outline.
 */
class SourceAssistTest extends AbstractUiTest {

	private static final String LOM = "<lom xmlns=\"http://ltsc.ieee.org/xsd/LOM\" xmlns:hx=\"http://ns.medbiq.org/lom/extend/v1/\">";
	private static final String CARET = "|";

	private Shell shell;
	private SourceViewer viewer;
	private XmlSourceViewerConfiguration configuration;

	@BeforeEach
	void createViewer() {
		shell = new Shell(display(), SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		viewer = new SourceViewer(shell, null, SWT.MULTI | SWT.V_SCROLL);
		configuration = new XmlSourceViewerConfiguration();
		viewer.configure(configuration);
		viewer.setDocument(new Document(""));
		// Off the screen like the workbench window during the tests (see the UI test pom).
		int offset = Boolean.getBoolean("hermes.offscreen") ? -30000 : 0;
		shell.setBounds(offset, offset, 800, 600);
		shell.open();
	}

	@AfterEach
	void disposeViewer() {
		shell.dispose();
	}

	/** Sets the text (with "|" marking the caret) and returns the caret offset. */
	private int setText(String textWithCaret) {
		int caret = textWithCaret.indexOf(CARET);
		viewer.getDocument().set(textWithCaret.replace(CARET, ""));
		return caret;
	}

	private ICompletionProposal[] proposals(String textWithCaret) {
		int caret = setText(textWithCaret);
		IContentAssistProcessor processor = configuration.getContentAssistant(viewer)
				.getContentAssistProcessor(IDocument.DEFAULT_CONTENT_TYPE);
		ICompletionProposal[] result = processor.computeCompletionProposals(viewer, caret);
		return result == null ? new ICompletionProposal[0] : result;
	}

	private static List<String> labels(ICompletionProposal[] proposals) {
		List<String> labels = new ArrayList<>();
		for (ICompletionProposal proposal : proposals) {
			String label = proposal.getDisplayString().split("  ")[0];
			if (!label.startsWith("</")) {
				labels.add(label); // the end tag of the current element is always offered as well
			}
		}
		return labels;
	}

	// ----------------------------------------------------------------------------------------------------
	// XML content assist
	// ----------------------------------------------------------------------------------------------------

	@Test
	void elementsThatMayOccurOnceAreNotProposedAgain() {
		List<String> labels = labels(proposals(LOM + "<general><title><string>x</string></title><" + CARET
				+ "</general></lom>"));
		assertTrue(labels.contains("keyword"), labels.toString());
		assertFalse(labels.contains("title"), "title may occur once: " + labels);
	}

	@Test
	void sequencesOnlyProposeElementsFittingThePosition() {
		List<String> labels = labels(proposals(LOM + "<hx:healthcareMetadata><hx:healthcareEducation><hx:credits/><"
				+ CARET + "<hx:offLabelUse>no</hx:offLabelUse></hx:healthcareEducation></hx:healthcareMetadata></lom>"));
		assertTrue(labels.contains("hx:targetAudience"), labels.toString());
		assertTrue(labels.contains("hx:credits"), "credits may repeat: " + labels);
		assertFalse(labels.contains("hx:expirationDate"), "comes before credits: " + labels);
		assertFalse(labels.contains("hx:offLabelDescription"), "comes after offLabelUse: " + labels);
	}

	@Test
	void keywordTextProposesMeshDescriptorsAndSetsTheId() {
		ICompletionProposal[] proposals = proposals(LOM + "<general><keyword hx:source=\"MeSH\"><string language=\"en\">"
				+ "myocardial infarc" + CARET + "</string></keyword></general></lom>");
		List<String> labels = labels(proposals);
		assertEquals("Myocardial Infarction", labels.getFirst(), labels.toString());
		proposals[0].apply(viewer.getDocument());
		String text = viewer.getDocument().get();
		assertTrue(text.contains("hx:id=\"D009203\""), text);
		assertTrue(text.contains(">Myocardial Infarction</string>"), text);
	}

	@Test
	void keywordIdProposesCodesOfTheSource() {
		List<String> labels = labels(proposals(LOM + "<general><keyword hx:source=\"ICD-10-CM\" hx:id=\"" + CARET
				+ "\"><string language=\"en\">sepsis unspecified</string></keyword></general></lom>"));
		assertEquals("A41.9", labels.getFirst(), labels.toString());
	}

	@Test
	void taxonIdsFollowTheHierarchyAndFillTheEntry() {
		String path = "<classification><taxonPath><source><string language=\"en\">Bloom's Taxonomy (revised)</string>"
				+ "</source><taxon><id>4</id></taxon><taxon><id>" + CARET + "</id></taxon></taxonPath></classification>";
		ICompletionProposal[] proposals = proposals(LOM + path + "</lom>");
		assertEquals(List.of("4.1", "4.2", "4.3"), labels(proposals), "the narrower concepts of Analyze");
		proposals[0].apply(viewer.getDocument());
		String text = viewer.getDocument().get();
		assertTrue(text.contains("<id>4.1</id>"), text);
		assertTrue(text.contains(">Differentiating</string>"), text);
	}

	// ----------------------------------------------------------------------------------------------------
	// Hovers and tag editing
	// ----------------------------------------------------------------------------------------------------

	@Test
	void hoversDocumentElementsAndCodes() {
		String text = LOM + "<general><title><string>x</string></title><keyword hx:source=\"ICD-10-CM\" hx:id=\"A41.9\">"
				+ "<string>Sepsis</string></keyword></general></lom>";
		setText(text);
		var hover = configuration.getTextHover(viewer, IDocument.DEFAULT_CONTENT_TYPE);
		String code = hover.getHoverInfo(viewer, new Region(text.indexOf("A41.9") + 2, 0));
		assertNotNull(code);
		assertTrue(code.contains("Sepsis, unspecified organism"), code);
		String title = hover.getHoverInfo(viewer, new Region(text.indexOf("<title") + 2, 0));
		assertNotNull(title);
		assertTrue(title.contains("at most once"), title);
	}

	private void type(int offset, String typed) {
		int widgetOffset = viewer instanceof ITextViewerExtension5 extension ? extension.modelOffset2WidgetOffset(offset)
				: offset;
		viewer.getTextWidget().setSelection(widgetOffset);
		viewer.getTextWidget().insert(typed);
		dispatch(20);
	}

	@Test
	void typingCompletesAndRenamesTags() {
		int caret = setText(LOM + "<general" + CARET + "</lom>");
		type(caret, ">");
		String text = viewer.getDocument().get();
		assertTrue(text.contains("<general></general>"), text);

		// Renaming the start tag renames the end tag.
		type(text.indexOf("<general") + "<general".length(), "X");
		text = viewer.getDocument().get();
		assertTrue(text.contains("<generalX></generalX>"), text);

		// "</" completes the end tag of the open element.
		caret = setText(LOM + "<general><" + CARET);
		type(caret, "/");
		assertTrue(viewer.getDocument().get().endsWith("<general></general>"), viewer.getDocument().get());
	}

	// ----------------------------------------------------------------------------------------------------
	// JSON content assist
	// ----------------------------------------------------------------------------------------------------

	private List<String> jsonProposals(String textWithCaret) {
		int caret = textWithCaret.indexOf(CARET);
		TextViewer text = new TextViewer(shell, SWT.NONE);
		text.setDocument(new Document(textWithCaret.replace(CARET, "")));
		ICompletionProposal[] proposals = new JsonContentAssistProcessor().computeCompletionProposals(text, caret);
		text.getControl().dispose();
		return labels(proposals);
	}

	@Test
	void jsonProposesMembersAndValues() {
		List<String> members = jsonProposals("{\"lom\": {\"general\": {\"title\": {\"string\": []}, \"" + CARET + "}}}");
		assertTrue(members.contains("keyword"), members.toString());
		assertFalse(members.contains("title"), "already present: " + members);

		List<String> status = jsonProposals(
				"{\"lom\": {\"lifeCycle\": {\"status\": {\"source\": \"LOMv1.0\", \"value\": \"" + CARET + "\"}}}}");
		assertTrue(status.contains("final"), status.toString());

		List<String> codes = jsonProposals("{\"lom\": {\"general\": {\"keyword\": [{\"source\": \"ICD-10-CM\", "
				+ "\"id\": \"sepsis unspec" + CARET + "\"}]}}}");
		assertEquals("A41.9", codes.getFirst(), codes.toString());

		List<String> languages = jsonProposals("{\"lom\": {\"general\": {\"title\": {\"string\": [{\"language\": \"e"
				+ CARET + "\"}]}}}}");
		assertTrue(languages.contains("en-US"), languages.toString());
	}

	// ----------------------------------------------------------------------------------------------------
	// Quick fixes and selection in the editor
	// ----------------------------------------------------------------------------------------------------

	private static ISourceViewer sourceViewer(XmlSourceEditor editor) {
		return (ISourceViewer) invoke(editor, AbstractTextEditor.class, "getSourceViewer", new Class<?>[0]);
	}

	@Test
	void quickFixesAndSelectionOnTheXmlPage() throws Exception {
		DocumentRoot root = HlomTemplates.CME_COURSE.create("Quick Fix Course", "en-US");
		root.getLom().getMetaMetadata().getFirst().getMetadataSchema()
				.removeIf(s -> HlomDocuments.HEALTHCARE_METADATA_SCHEMA.equals(s.getValue()));
		Path file = save(root, "xml-quick-fix.xml");
		HlomMetadataEditor editor = open(file);
		editor.showPage("xml");
		dispatch(100);
		editor.validateNow();
		XmlSourceEditor xml = XmlPageTest.xmlEditor(editor);
		ISourceViewer source = sourceViewer(xml);
		IQuickAssistAssistant assistant = ((ISourceViewerExtension3) source).getQuickAssistAssistant();
		int metaMetadata = xml.getText().indexOf("<metaMetadata") + 2;

		ICompletionProposal[][] fixes = new ICompletionProposal[1][];
		waitUntil("quick fixes on the metaMetadata line", () -> {
			fixes[0] = assistant.getQuickAssistProcessor()
					.computeQuickAssistProposals(new TextInvocationContext(source, metaMetadata, -1));
			return fixes[0] != null && Arrays.stream(fixes[0])
					.anyMatch(p -> p.getDisplayString().startsWith("Declare metadata schema"));
		}, TIMEOUT);
		ICompletionProposal declare = Arrays.stream(fixes[0])
				.filter(p -> p.getDisplayString().startsWith("Declare metadata schema")).findFirst().orElseThrow();
		declare.apply(source.getDocument());
		waitUntil("the fix is applied", () -> xml.getText().contains(HlomDocuments.HEALTHCARE_METADATA_SCHEMA), TIMEOUT);

		// The XML caret selects its element in the editor (outline, properties).
		int general = xml.getText().indexOf("<general") + 3;
		source.setSelectedRange(general, 0);
		waitUntil("the caret's element is selected", () -> editor.getSelection() instanceof IStructuredSelection s
				&& s.getFirstElement() instanceof General, TIMEOUT);

		// An element selected in the outline is shown in the XML text.
		DocumentRoot model = HlomResources.getDocumentRoot(
				editor.getEditingDomain().getResourceSet().getResources().getFirst());
		Title title = model.getLom().getGeneral().getFirst().getTitle().getFirst();
		editor.handleContentOutlineSelection(new StructuredSelection(title));
		Region selected = new Region(source.getSelectedRange().x, source.getSelectedRange().y);
		assertEquals("title", source.getDocument().get(selected.getOffset(), selected.getLength()));
		assertInstanceOf(General.class, title.eContainer());
		page().closeEditor(editor, false);
	}
}
