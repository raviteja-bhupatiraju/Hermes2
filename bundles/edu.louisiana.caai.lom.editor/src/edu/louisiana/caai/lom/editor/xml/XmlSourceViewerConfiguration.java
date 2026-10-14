/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.HlomDocumentType;

/**
 * Syntax highlighting, indentation, auto-edit behavior, content assist and problem hovers of the
 * {@link XmlSourceEditor}.
 */
public class XmlSourceViewerConfiguration extends SourceViewerConfiguration {

	/** The type of the edited document. */
	private final java.util.function.Supplier<DocumentType> type;

	/** The number of spaces inserted for a tab. */
	public static final int INDENT_WIDTH = 2;

	private static final String INDENT = " ".repeat(INDENT_WIDTH);

	/** The handler of quick fixes (Ctrl+1), or {@code null} for viewers without them. */
	private final java.util.function.Supplier<XmlSourceEditor.QuickFixHandler> quickFixes;

	/** A configuration without quick fixes. */
	public XmlSourceViewerConfiguration() {
		this(null);
	}

	/** A configuration whose quick fixes (Ctrl+1) are applied by a handler (supplied lazily, may supply null). */
	public XmlSourceViewerConfiguration(java.util.function.Supplier<XmlSourceEditor.QuickFixHandler> quickFixes) {
		this(quickFixes, () -> HlomDocumentType.INSTANCE);
	}

	/** A configuration for documents of a type (supplied lazily: the editor knows it once the file is loaded). */
	public XmlSourceViewerConfiguration(java.util.function.Supplier<XmlSourceEditor.QuickFixHandler> quickFixes,
			java.util.function.Supplier<DocumentType> type) {
		this.quickFixes = quickFixes;
		this.type = type;
	}

	@Override
	public org.eclipse.jface.text.quickassist.IQuickAssistAssistant getQuickAssistAssistant(ISourceViewer sourceViewer) {
		if (quickFixes == null) {
			return null;
		}
		org.eclipse.jface.text.quickassist.QuickAssistAssistant assistant = new org.eclipse.jface.text.quickassist.QuickAssistAssistant();
		assistant.setQuickAssistProcessor(new XmlQuickAssistProcessor(quickFixes));
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		assistant.setProposalSelectorBackground(HlomTheme.color(HlomTheme.SURFACE));
		assistant.setProposalSelectorForeground(HlomTheme.color(HlomTheme.INK));
		assistant.setStatusLineVisible(true);
		assistant.setStatusMessage(Messages.XmlSourceViewerConfiguration_QuickFixesOfTheProblems);
		return assistant;
	}

	/** Links to the targets of references (Ctrl+click), and to web addresses. */
	@Override
	public org.eclipse.jface.text.hyperlink.IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		return new org.eclipse.jface.text.hyperlink.IHyperlinkDetector[] { new XmlReferenceHyperlinks(type),
				new org.eclipse.jface.text.hyperlink.URLHyperlinkDetector() };
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		String[] types = new String[XmlPartitionScanner.PARTITION_TYPES.length + 1];
		types[0] = IDocument.DEFAULT_CONTENT_TYPE;
		System.arraycopy(XmlPartitionScanner.PARTITION_TYPES, 0, types, 1, XmlPartitionScanner.PARTITION_TYPES.length);
		return types;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		add(reconciler, IDocument.DEFAULT_CONTENT_TYPE, new XmlContentScanner());
		add(reconciler, XmlPartitionScanner.XML_TAG, new XmlTagScanner());
		add(reconciler, XmlPartitionScanner.XML_COMMENT, uniform(XmlColors.COMMENT, SWT.ITALIC));
		add(reconciler, XmlPartitionScanner.XML_PI, uniform(XmlColors.PI, SWT.NORMAL));
		add(reconciler, XmlPartitionScanner.XML_CDATA, uniform(XmlColors.CDATA, SWT.NORMAL));
		add(reconciler, XmlPartitionScanner.XML_DECLARATION, uniform(XmlColors.DECLARATION, SWT.NORMAL));
		return reconciler;
	}

	private static void add(PresentationReconciler reconciler, String contentType, ITokenScanner scanner) {
		DefaultDamagerRepairer repairer = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(repairer, contentType);
		reconciler.setRepairer(repairer, contentType);
	}

	/** A scanner returning one token for the whole partition. */
	private static ITokenScanner uniform(String colorKey, int style) {
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setDefaultReturnToken(XmlColors.token(colorKey, style));
		return scanner;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		return new IAutoEditStrategy[] { new DefaultIndentLineAutoEditStrategy(), XmlSourceViewerConfiguration::tabToSpaces,
				new XmlTagEditing() };
	}

	/** Replaces typed tabs with spaces. */
	private static void tabToSpaces(IDocument document, DocumentCommand command) {
		if (command.text != null && command.length == 0 && "\t".equals(command.text)) {
			command.text = INDENT;
		}
	}

	@Override
	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		return new String[] { INDENT, "\t", "" };
	}

	@Override
	public int getTabWidth(ISourceViewer sourceViewer) {
		return INDENT_WIDTH;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		XmlContentAssistProcessor processor = new XmlContentAssistProcessor(type);
		for (String contentType : getConfiguredContentTypes(sourceViewer)) {
			assistant.setContentAssistProcessor(processor, contentType);
		}
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(150);
		assistant.enableAutoInsert(true);
		assistant.enableColoredLabels(true);
		assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		assistant.setProposalSelectorBackground(HlomTheme.color(HlomTheme.SURFACE));
		assistant.setProposalSelectorForeground(HlomTheme.color(HlomTheme.INK));
		assistant.setStatusLineVisible(true);
		assistant.setStatusMessage(NLS.bind(Messages.XmlSourceViewerConfiguration_ProposalsFromTheSchemas, type.get().label()));
		return assistant;
	}

	@Override
	public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		return XmlProblemHover.informationControlCreator();
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new XmlProblemHover(type);
	}

	@Override
	public IAnnotationHover getOverviewRulerAnnotationHover(ISourceViewer sourceViewer) {
		return new XmlProblemHover(type);
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return new XmlProblemHover(type);
	}
}
