/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.pages;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.forms.FormSupport;
import edu.louisiana.caai.lom.editor.forms.ValueList;
import edu.louisiana.caai.lom.editor.help.HelpContexts;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MinimalDocuments;
import edu.louisiana.caai.medbiq.model.Paths;
import edu.louisiana.caai.medbiq.model.References;
import edu.louisiana.caai.medbiq.model.forms.MedbiqPages;
import edu.louisiana.caai.medbiq.model.forms.PageSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Auto;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Field;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.FieldSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Group;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Repeat;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.SectionSpec;
import edu.louisiana.caai.medbiq.model.forms.PageSpec.Widget;

/**
 * A form page of a document type of the MedBiquitous standards, rendered from a {@link PageSpec}: the fields are
 * found by element path in the model and get the widgets their types call for, bound to the model like the
 * Healthcare LOM pages (undo, validation markers, help).
 */
public final class SpecPage extends AbstractLomPage {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;
	/** From this depth, nested elements of automatic forms are lists (edited in a master/detail block). */
	private static final int MAX_AUTO_DEPTH = 2;
	/** From this depth, automatic forms only show simple values (further content is edited in the tree). */
	private static final int MAX_DETAIL_DEPTH = 4;
	/** The most fields an automatic form shows for an element. */
	private static final int MAX_AUTO_FIELDS = 40;
	/** Namespaces of rich content (XHTML), edited on the XML page rather than in forms. */
	private static final java.util.Set<String> RICH_CONTENT = java.util.Set.of("http://www.w3.org/1999/xhtml");

	private final MedbiqDocumentType type;
	private final PageSpec spec;
	private final EClass rootClass;

	public SpecPage(MedbiqDocumentType type, PageSpec spec, EClass rootClass) {
		this.type = type;
		this.spec = spec;
		this.rootClass = rootClass;
	}

	@Override
	public String getId() {
		return spec.id();
	}

	@Override
	public String getTitle() {
		return spec.title();
	}

	@Override
	public String getDescription() {
		return spec.description();
	}

	/** The icon name of the page ({@code icons/pages/<icon>.png} of the editor). */
	public String getIcon() {
		return spec.icon() == null ? "general" : spec.icon();
	}

	/** The element paths (relative to the root element) of the sections of this page. */
	public List<String> coveredPaths() {
		return spec.sections().stream().map(SectionSpec::path).toList();
	}

	/** The navigation group. */
	@Override
	public String getGroupLabel() {
		return spec.category();
	}

	@Override
	public Category getCategory() {
		return Category.DESCRIPTION;
	}

	@Override
	public boolean isHealthcare() {
		return false;
	}

	@Override
	public String iconPath() {
		return "icons/pages/" + getIcon() + ".png";
	}

	@Override
	protected String helpContext() {
		return HelpContexts.page(type.id() + "_" + spec.id());
	}

	@Override
	protected void createContent(FormSupport form, Composite body) {
		List<SectionSpec> sections = spec.sections();
		Composite left = column(form, body);
		Composite right = sections.size() > 1 ? column(form, body) : null;
		for (int i = 0; i < sections.size(); i++) {
			SectionSpec section = sections.get(i);
			Composite target = right == null || i % 2 == 0 ? left : right;
			if (right != null && sections.size() % 2 == 1 && i == sections.size() - 1 && hasLists(section)) {
				target = wide(form, body);
			}
			Composite client = form.section(target, section.title(), section.description(), 2);
			Located base = locate(rootClass, ModelPath.self(), section.path());
			if (base == null || !(base.feature() == null || base.feature() instanceof EReference)) {
				missing(form, client, section.path());
				continue;
			}
			ModelPath path = base.feature() == null ? base.ownerPath() : base.ownerPath().then((EReference) base.feature());
			EClass eClass = base.feature() == null ? rootClass : ((EReference) base.feature()).getEReferenceType();
			render(form, client, path, eClass, section.fields(), 0);
		}
		for (Control column : body.getChildren()) {
			if (column instanceof Composite composite) {
				FormSupport.alignLabels(composite);
			}
		}
	}

	/**
	 * A label without its format hint, and the hint: "Age (e.g. P58Y)" is the label "Age" with the hint
	 * "e.g. P58Y", shown as placeholder and tooltip (labels name fields; formats belong to the field).
	 */
	record Labeled(String label, String hint) {

		private static final Pattern HINT = Pattern
				.compile("(.*\\S)\\s*\\(([^()]*(?:e\\.g\\.|such as|optional|ISO 8601)[^()]*|[^()\\s]*:[^()\\s]*)\\)");

		static Labeled of(String label) {
			Matcher matcher = HINT.matcher(label);
			return matcher.matches() ? new Labeled(matcher.group(1), matcher.group(2)) : new Labeled(label, null);
		}
	}

	private static boolean hasLists(SectionSpec section) {
		return section.fields().stream().anyMatch(f -> f instanceof Repeat);
	}

	// ----------------------------------------------------------------------------------------------------
	// Rendering
	// ----------------------------------------------------------------------------------------------------

	private void render(FormSupport form, Composite parent, ModelPath base, EClass eClass, List<FieldSpec> fields,
			int depth) {
		for (FieldSpec field : fields) {
			switch (field) {
				case Auto auto -> renderAuto(form, parent, base, eClass, auto.path(), depth);
				case Field simple -> renderField(form, parent, base, eClass, simple);
				case Group group -> renderGroup(form, parent, base, eClass, group, depth);
				case Repeat repeat -> renderRepeat(form, parent, base, eClass, repeat, depth);
			}
		}
	}

	private void renderAuto(FormSupport form, Composite parent, ModelPath base, EClass eClass, String path, int depth) {
		ModelPath target = base;
		EClass targetClass = eClass;
		if (!path.isEmpty()) {
			Located located = locate(eClass, base, path);
			if (located == null || !(located.feature() instanceof EReference reference)) {
				missing(form, parent, path);
				return;
			}
			target = located.ownerPath().then(reference);
			targetClass = reference.getEReferenceType();
		}
		render(form, parent, target, targetClass, autoFields(targetClass, depth), depth + 1);
	}

	/**
	 * The fields of all content of a class: simple values, groups for nested elements and master/detail blocks for
	 * repeated ones; deeper levels show fewer kinds of content, so that the forms stay small.
	 */
	private static List<FieldSpec> autoFields(EClass eClass, int depth) {
		List<FieldSpec> fields = new ArrayList<>();
		if (MedbiqPages.contentAttribute(eClass) != null) {
			fields.add(PageSpec.field(PageSpec.CONTENT, Messages.SpecPage_Value));
		}
		for (EStructuralFeature feature : MedbiqPages.elementsAndAttributes(eClass)) {
			if (fields.size() >= MAX_AUTO_FIELDS) {
				break;
			}
			String name = MedbiqPages.name(feature);
			if (MedbiqPages.isSimple(feature)) {
				fields.add(PageSpec.field(name));
			} else if (feature instanceof EReference reference && isLom(reference.getEReferenceType())) {
				fields.add(new Group(name, Messages.SpecPage_DescriptionHealthcareLOM, LOM_FIELDS));
			} else if (isRichContent(feature) || depth >= MAX_DETAIL_DEPTH) {
				continue;
			} else if (feature.isMany() || depth >= MAX_AUTO_DEPTH) {
				fields.add(PageSpec.repeat(name, null, null));
			} else {
				fields.add(PageSpec.group(name, null));
			}
		}
		return fields;
	}

	/** The fields of embedded Healthcare LOM metadata ({@code lom}): its description and identification. */
	static final List<FieldSpec> LOM_FIELDS = List.of(PageSpec.field("general/title", "Title"),
			PageSpec.text("general/description", "Description"), PageSpec.field("general/keyword", "Keywords"),
			PageSpec.repeat("general/identifier", "Identifiers", "entry"),
			PageSpec.field("general/language", "Languages"),
			PageSpec.repeat("lifeCycle/contribute", "Contributors", "entity"));

	private static boolean isLom(EClass eClass) {
		return eClass == LomPackage.eINSTANCE.getLom();
	}

	/** Whether a class is a vocabulary element of Healthcare LOM (source and value, edited with a choice). */
	private static boolean isLomVocabulary(EClass eClass) {
		return eClass.getEPackage() == LomPackage.eINSTANCE && !Vocabularies.terms(eClass).isEmpty();
	}

	private static boolean isLanguageString(EClass eClass) {
		return LomPackage.eINSTANCE.getLanguageString().isSuperTypeOf(eClass);
	}

	private static boolean isRichContent(EStructuralFeature feature) {
		return feature instanceof EReference reference && reference.getEReferenceType().getEPackage() != null
				&& RICH_CONTENT.contains(reference.getEReferenceType().getEPackage().getNsURI());
	}

	private void renderField(FormSupport form, Composite parent, ModelPath base, EClass eClass, Field field) {
		if (field.path().isEmpty() || field.path().equals(PageSpec.CONTENT)) {
			// The text of the element itself (an element with simple content and attributes).
			EAttribute content = MedbiqPages.contentAttribute(eClass);
			if (content == null) {
				missing(form, parent, field.path());
				return;
			}
			String label = field.label() != null ? field.label() : "Value";
			FormSupport.applyHint(value(form, parent, Labeled.of(label).label(), base, content, field,
					help(List.of(META.getName(eClass)))), Labeled.of(label).hint());
			return;
		}
		Located located = locate(eClass, base, field.path());
		if (located == null) {
			missing(form, parent, field.path());
			return;
		}
		Labeled labeled = Labeled.of(field.label() != null ? field.label() : label(field.path()));
		String label = labeled.label();
		String help = help(located, field.path());
		References.Kind referenceKind = References.kind(type.references(), null, last(field.path()));
		if (referenceKind != null && renderReference(form, parent, located, referenceKind, label, help)) {
			return;
		}
		switch (located.feature()) {
			case EAttribute attribute when attribute.isMany() -> form.valueList(parent, label, ValueList.Kind.VALUES,
					located.ownerPath(), attribute, null, field.suggestions(), help);
			case EAttribute attribute -> FormSupport.applyHint(
					value(form, parent, label, located.ownerPath(), attribute, field, help), labeled.hint());
			case EReference reference when MedbiqPages.contentAttribute(reference.getEReferenceType()) != null
					&& MedbiqPages.isSimple(reference) -> {
				EAttribute content = MedbiqPages.contentAttribute(reference.getEReferenceType());
				if (reference.isMany()) {
					form.valueList(parent, label, ValueList.Kind.ELEMENTS, located.ownerPath(), reference, content,
							field.suggestions(), help);
				} else {
					FormSupport.applyHint(value(form, parent, label, located.ownerPath().then(reference), content, field,
							help), labeled.hint());
				}
			}
			case EReference reference when isLomVocabulary(reference.getEReferenceType()) -> form.vocabCombo(parent,
					label, located.ownerPath().then(reference), help);
			case EReference reference when isLanguageString(reference.getEReferenceType()) -> {
				String last = field.path().substring(field.path().lastIndexOf('/') + 1);
				if (last.equals("keyword") || last.equals("coverage")) {
					form.langList(parent, label, located.ownerPath(), reference, field.suggestions(), last.equals("keyword"),
							help);
				} else {
					FormSupport.applyHint(form.langText(parent, label, located.ownerPath().then(reference),
							field.widget() == Widget.MULTILINE || isLongText(label), help), labeled.hint());
				}
			}
			case EReference reference when isLom(reference.getEReferenceType()) -> renderGroup(form, parent, base, eClass,
					new Group(field.path(), field.label() != null ? field.label() : "Description (Healthcare LOM)",
							LOM_FIELDS), 0);
			case EReference reference when isRichContent(reference) -> {
				Label hint = form.hint(parent, NLS.bind(Messages.SpecPage_FormattedTextXHTMLEditedOn, label));
				GridDataFactory.fillDefaults().span(2, 1).applyTo(hint);
			}
			case EReference reference when reference.isMany() -> renderRepeat(form, parent, base, eClass,
					PageSpec.repeat(field.path(), label, null), 0);
			case EReference _ -> renderGroup(form, parent, base, eClass, PageSpec.group(field.path(), label), 0);
			default -> missing(form, parent, field.path());
		}
	}

	/**
	 * A reference to elements of the document: the targets are offered by their labels, and the referenced
	 * element can be shown. Returns {@code false} if the field isn't a simple value (then it is rendered as usual).
	 */
	private boolean renderReference(FormSupport form, Composite parent, Located located, References.Kind kind,
			String label, String help) {
		ReferencePresentation presentation = new ReferencePresentation(kind, form.context().domain(),
				element -> services().reveal(element));
		switch (located.feature()) {
			case EAttribute attribute when attribute.isMany() -> form.valueList(parent, label, ValueList.Kind.VALUES,
					located.ownerPath(), attribute, null, List.of(), presentation, help);
			case EAttribute attribute -> form.referenceText(parent, label, located.ownerPath(), attribute, presentation,
					help);
			case EReference reference when MedbiqPages.contentAttribute(reference.getEReferenceType()) != null -> {
				EAttribute content = MedbiqPages.contentAttribute(reference.getEReferenceType());
				if (reference.isMany()) {
					form.valueList(parent, label, ValueList.Kind.ELEMENTS, located.ownerPath(), reference, content,
							List.of(), presentation, help);
				} else {
					form.referenceText(parent, label, located.ownerPath().then(reference), content, presentation, help);
				}
			}
			default -> {
				return false;
			}
		}
		return true;
	}

	private static String last(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	/** The widget of a simple value (null for a date picker). */
	private static Control value(FormSupport form, Composite parent, String label, ModelPath owner,
			EAttribute attribute, Field field, String help) {
		EDataType type = attribute.getEAttributeType();
		if (isEnumeration(type)) {
			return form.enumCombo(parent, label, owner, attribute, help).getControl();
		} else if (FormSupport.isBoolean(attribute)) {
			return form.check(parent, label, owner, attribute, help);
		} else if (!field.suggestions().isEmpty()) {
			return form.suggestText(parent, label, owner, attribute, field.suggestions(), help);
		} else if (field.widget() == Widget.MULTILINE || isLongText(label)) {
			return form.multiText(parent, label, owner, attribute, help, 4);
		} else {
			return form.text(parent, label, owner, attribute, help);
		}
	}

	private static boolean isEnumeration(EDataType type) {
		if (type instanceof EEnum) {
			return true;
		}
		EDataType base = META.getBaseType(type);
		return base instanceof EEnum || type.getInstanceClass() != null && type.getInstanceClass().isEnum();
	}

	private static boolean isLongText(String label) {
		String lower = label.toLowerCase(Locale.ROOT);
		return lower.contains("description") || lower.contains("narrative") || lower.contains("comment")
				|| lower.contains("abstract") || lower.contains("statement") || lower.endsWith("text");
	}

	private void renderGroup(FormSupport form, Composite parent, ModelPath base, EClass eClass, Group group, int depth) {
		Located located = locate(eClass, base, group.path());
		if (located == null || !(located.feature() instanceof EReference reference)) {
			missing(form, parent, group.path());
			return;
		}
		Label heading = form.toolkit().createLabel(parent, group.label() != null ? group.label() : label(group.path()));
		heading.setFont(HlomTheme.groupFont());
		heading.setForeground(HlomTheme.color(HlomTheme.INK));
		GridDataFactory.fillDefaults().span(2, 1).indent(0, 6).applyTo(heading);
		String help = help(located);
		if (help != null) {
			heading.setToolTipText(help.substring(FormSupport.LITERAL_HELP.length()));
		}
		ModelPath path = located.ownerPath().then(reference);
		List<FieldSpec> fields = group.fields().isEmpty() ? autoFields(reference.getEReferenceType(), depth + 1)
				: group.fields();
		render(form, parent, path, reference.getEReferenceType(), fields, depth + 1);
	}

	private void renderRepeat(FormSupport form, Composite parent, ModelPath base, EClass eClass, Repeat repeat,
			int depth) {
		Located located = locate(eClass, base, repeat.path());
		if (located == null || !(located.feature() instanceof EReference reference)) {
			missing(form, parent, repeat.path());
			return;
		}
		Label heading = form.toolkit().createLabel(parent, repeat.label() != null ? repeat.label() : label(repeat.path()));
		heading.setFont(HlomTheme.groupFont());
		heading.setForeground(HlomTheme.color(HlomTheme.INK));
		GridDataFactory.fillDefaults().span(2, 1).indent(0, 6).applyTo(heading);
		EClass itemClass = reference.getEReferenceType();
		List<FieldSpec> details = repeat.details().isEmpty() ? autoFields(itemClass, depth + 1) : repeat.details();
		form.masterDetail(parent, located.ownerPath(), reference, item -> itemLabel(item, repeat.itemLabel()),
				() -> MinimalDocuments.create(itemClass),
				(detailForm, detail) -> render(detailForm, detail, ModelPath.self(), itemClass, details, depth + 1));
	}

	/** The label of an item of a list: the text at a path, or the first simple text of the item. */
	static String itemLabel(EObject item, String path) {
		if (path != null) {
			// Alternatives separated by "|": the first that has a text.
			for (String alternative : path.split("\\|")) {
				String text = Paths.text(item, alternative.strip());
				if (text != null && !text.isBlank()) {
					return text;
				}
			}
			return null;
		}
		String content = Paths.content(item);
		if (content != null && !content.isBlank()) {
			return content;
		}
		for (EStructuralFeature feature : MedbiqPages.elementsAndAttributes(item.eClass())) {
			if (MedbiqPages.isSimple(feature) && item.eIsSet(feature)) {
				String text = Paths.text(item, MedbiqPages.name(feature));
				if (text != null && !text.isBlank()) {
					return text;
				}
			}
		}
		return null;
	}

	private static void missing(FormSupport form, Composite parent, String path) {
		Label label = form.hint(parent, NLS.bind(Messages.SpecPage_NoElementInThisVersion, path));
		GridDataFactory.fillDefaults().span(2, 1).applyTo(label);
	}

	private String help(Located located) {
		return help(List.of(META.getName(located.feature())));
	}

	/** The help of a field: by the names on its path, so that {@code parent/name} help applies too. */
	private String help(Located located, String path) {
		List<String> names = new ArrayList<>();
		for (String step : path.split("/")) {
			if (!step.isEmpty()) {
				names.add(step.startsWith("@") ? step : step.replaceFirst("^[^:]*:", ""));
			}
		}
		if (names.isEmpty()) {
			names.add(META.getName(located.feature()));
		}
		return help(names);
	}

	private String help(List<String> names) {
		String text = type.fieldHelp(names);
		return text == null ? null : FormSupport.LITERAL_HELP + text;
	}

	/** A label from the last element of a path. */
	private static String label(String path) {
		String last = path.substring(path.lastIndexOf('/') + 1);
		return FieldLabels.words(last.startsWith("@") ? last.substring(1) : last);
	}

	// ----------------------------------------------------------------------------------------------------
	// Paths
	// ----------------------------------------------------------------------------------------------------

	/**
	 * A path resolved in the model.
	 *
	 * @param ownerPath the model path of the element owning the last feature
	 * @param feature   the last feature, or {@code null} for an empty path
	 */
	record Located(ModelPath ownerPath, EStructuralFeature feature) {
	}

	/** Resolves an element path (relative to an element of a class) to its owner path and last feature. */
	static Located locate(EClass start, ModelPath base, String path) {
		ModelPath owner = base;
		EClass eClass = start;
		String[] steps = path.isEmpty() ? new String[0] : path.split("/");
		if (steps.length == 0) {
			return new Located(base, null);
		}
		for (int i = 0; i < steps.length; i++) {
			EStructuralFeature feature = feature(eClass, steps[i]);
			if (feature == null) {
				return null;
			}
			if (i == steps.length - 1) {
				return new Located(owner, feature);
			}
			if (!(feature instanceof EReference reference) || !reference.isContainment()) {
				return null;
			}
			owner = owner.then(reference);
			eClass = reference.getEReferenceType();
		}
		return null;
	}

	/** The feature of an element or attribute ({@code @name}) by XML name. */
	static EStructuralFeature feature(EClass eClass, String step) {
		boolean attribute = step.startsWith("@");
		String name = attribute ? step.substring(1) : step;
		int colon = name.indexOf(':');
		if (colon >= 0) {
			name = name.substring(colon + 1);
		}
		for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if ((attribute ? kind == ExtendedMetaData.ATTRIBUTE_FEATURE : kind == ExtendedMetaData.ELEMENT_FEATURE)
					&& name.equals(META.getName(feature))) {
				return feature;
			}
		}
		return null;
	}
}
