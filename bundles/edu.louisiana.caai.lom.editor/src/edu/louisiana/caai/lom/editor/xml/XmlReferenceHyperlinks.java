/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.PlatformUI;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.xml.XmlStructure.Element;
import edu.louisiana.caai.lom.model.support.report.DocumentLinks;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.References;

/**
 * Links the references of the XML page to the elements they refer to (Ctrl+click): the text of an
 * {@code EventReference} leads to its {@code Event}. The text is scanned as it is, so links work while the document
 * is being edited (and even when it doesn't parse).
 */
final class XmlReferenceHyperlinks extends AbstractHyperlinkDetector {

	private final Supplier<DocumentType> type;

	XmlReferenceHyperlinks(Supplier<DocumentType> type) {
		this.type = type;
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer viewer, IRegion region, boolean canShowMultipleHyperlinks) {
		if (viewer.getDocument() == null) {
			return null;
		}
		String text = viewer.getDocument().get();
		XmlStructure structure = XmlStructure.parse(text);
		Element element = structure.enclosing(region.getOffset());
		if (element == null || !element.children.isEmpty() || element.endTagStart < 0
				|| region.getOffset() < element.startTagEnd || region.getOffset() > element.endTagStart) {
			return null;
		}
		IHyperlink[] documents = documentLinks(viewer, text, element);
		if (documents != null) {
			return documents;
		}
		if (!(type.get() instanceof MedbiqDocumentType medbiq) || medbiq.references().isEmpty()) {
			return null;
		}
		References.Kind kind = References.kind(medbiq.references(),
				element.parent == null ? null : element.parent.localName(), element.localName());
		String content = text.substring(element.startTagEnd, element.endTagStart);
		String key = kind == null ? null : kind.key(unescape(content));
		if (key == null) {
			return null;
		}
		Element target = find(structure, text, kind, key);
		if (target == null) {
			return null;
		}
		int leading = content.length() - content.stripLeading().length();
		IRegion link = new Region(element.startTagEnd + leading, content.strip().length());
		return new IHyperlink[] { new IHyperlink() {
			@Override
			public IRegion getHyperlinkRegion() {
				return link;
			}

			@Override
			public String getTypeLabel() {
				return kind.label();
			}

			@Override
			public String getHyperlinkText() {
				return NLS.bind(Messages.XmlReferenceHyperlinks_GoTo, kind.label(), key);
			}

			@Override
			public void open() {
				viewer.setSelectedRange(target.nameStart(), target.name.length());
				viewer.revealRange(target.start, target.startTagEnd - target.start);
			}
		} };
	}

	/**
	 * Links the entry of an identifier naming another document of the workspace (an included competency, a related
	 * resource) to that document; {@code null} if it isn't one.
	 */
	private static IHyperlink[] documentLinks(ITextViewer viewer, String text, Element element) {
		if (!element.localName().equalsIgnoreCase("entry") || element.parent == null
				|| element.parent.parent != null && element.parent.parent.localName().equals("general")) {
			return null;
		}
		String content = text.substring(element.startTagEnd, element.endTagStart);
		String entry = unescape(content).strip();
		if (entry.isEmpty()) {
			return null;
		}
		List<DocumentIndex.Document> documents = DocumentIndex.identifiedBy(new DocumentLinks.Identifier(null, entry));
		if (documents.isEmpty()) {
			return null;
		}
		int leading = content.length() - content.stripLeading().length();
		IRegion region = new Region(element.startTagEnd + leading, content.strip().length());
		return documents.stream().map(document -> (IHyperlink) new IHyperlink() {
			@Override
			public IRegion getHyperlinkRegion() {
				return region;
			}

			@Override
			public String getTypeLabel() {
				return Messages.XmlReferenceHyperlinks_Document;
			}

			@Override
			public String getHyperlinkText() {
				String title = document.entry().title();
				return NLS.bind(Messages.XmlReferenceHyperlinks_Open, (title == null || title.isBlank() ? document.file().getName() : title));
			}

			@Override
			public void open() {
				HlomEditorAdvisor.openEditor(PlatformUI.getWorkbench(),
						URI.createPlatformResourceURI(document.file().getFullPath().toString(), true));
			}
		}).toArray(IHyperlink[]::new);
	}

	/** The target element of a reference in the text, or {@code null}. */
	static Element find(XmlStructure structure, String text, References.Kind kind, String key) {
		if (structure.roots.isEmpty()) {
			return null;
		}
		List<Element> candidates = List.of(structure.roots.getFirst());
		for (String step : kind.targets().split("/")) {
			List<Element> next = new ArrayList<>();
			for (Element candidate : candidates) {
				for (Element child : candidate.children) {
					if (child.localName().equals(step)) {
						next.add(child);
					}
				}
			}
			candidates = next;
		}
		for (Element candidate : candidates) {
			if (keys(text, candidate, kind.key()).contains(key)) {
				return candidate;
			}
		}
		return null;
	}

	/** The texts at a path ({@code @attribute} or element local names) below an element. */
	private static List<String> keys(String text, Element element, String path) {
		List<Element> current = List.of(element);
		String[] steps = path.split("/");
		List<String> keys = new ArrayList<>();
		for (int i = 0; i < steps.length; i++) {
			String step = steps[i];
			if (step.startsWith("@")) {
				for (Element e : current) {
					String value = attribute(text.substring(e.start, e.startTagEnd), step.substring(1));
					if (value != null) {
						keys.add(value.strip());
					}
				}
				return keys;
			}
			List<Element> next = new ArrayList<>();
			for (Element e : current) {
				for (Element child : e.children) {
					if (child.localName().equals(step)) {
						next.add(child);
					}
				}
			}
			current = next;
		}
		for (Element e : current) {
			if (e.endTagStart >= 0) {
				keys.add(unescape(text.substring(e.startTagEnd, e.endTagStart)).strip());
			}
		}
		return keys;
	}

	private static String attribute(String startTag, String name) {
		Matcher matcher = Pattern.compile("(?:^|\\s)(?:[\\w.-]+:)?" + Pattern.quote(name) + "\\s*=\\s*(['\"])(.*?)\\1")
				.matcher(startTag);
		return matcher.find() ? unescape(matcher.group(2)) : null;
	}

	private static String unescape(String text) {
		return text.replace("&apos;", "'").replace("&quot;", "\"").replace("&lt;", "<").replace("&gt;", ">")
				.replace("&amp;", "&");
	}
}
