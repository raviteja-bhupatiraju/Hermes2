/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import org.eclipse.osgi.util.NLS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelContext;
import edu.louisiana.caai.lom.editor.binding.ModelPath;
import edu.louisiana.caai.lom.editor.help.HelpSupport;
import edu.louisiana.caai.lom.model.lom.LanguageString;
import edu.louisiana.caai.lom.model.lom.LomPackage;
import edu.louisiana.caai.lom.model.support.FieldHelp;
import edu.louisiana.caai.lom.model.support.HlomDocuments;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomSuggestions;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.Vocabularies.Term;

/**
 * Builds data-bound form fields for HLOM elements.
 * <p>
 * Every field is bound to a value at the end of a {@link ModelPath} relative to an <em>anchor</em> observable
 * (the {@code lom} element for top-level fields, or the selected element in a master/detail block). Typing
 * into a field whose elements don't exist yet creates them; clearing it removes elements that became empty.
 * Bound fields are registered so that {@link #showProblems} can mark the fields that validation problems
 * refer to.
 */
public final class FormSupport {

	/** Marker for "no value" in combo boxes. */
	static final Object NOT_SET = new Object() {
		@Override
		public String toString() {
			return "";
		}
	};

	/** Suffix marking MedBiquitous healthcare terms in vocabulary combo boxes. */
	static final String HEALTHCARE_MARK = "  \u00b7  HLOM";


	private static final int TEXT_DELAY = 400;

	/** Minimum width of the label column, so that the fields of stacked sections line up. */
	private static final int LABEL_WIDTH = 110;

	/** Maximum width of the label column; longer labels wrap, leaving the width to the fields. */
	private static final int MAX_LABEL_WIDTH = 150;

	/** Width of the language combo of multilingual fields. */
	private static final int LANGUAGE_WIDTH = 70;

	private final FormToolkit toolkit;
	private final ModelContext context;
	private final DataBindingContext bindingContext;
	private final IObservableValue<EObject> anchor;
	private final Consumer<ISelection> selectionSink;
	private final FieldProblems fields;

	/**
	 * @param selectionSink receives the elements selected in master lists, so that the editor can show them
	 *                      in the Properties view; may be {@code null}
	 */
	public FormSupport(FormToolkit toolkit, ModelContext context, DataBindingContext bindingContext,
			IObservableValue<EObject> anchor, Consumer<ISelection> selectionSink) {
		this(toolkit, context, bindingContext, anchor, selectionSink, new FieldProblems());
	}

	private FormSupport(FormToolkit toolkit, ModelContext context, DataBindingContext bindingContext,
			IObservableValue<EObject> anchor, Consumer<ISelection> selectionSink, FieldProblems fields) {
		this.toolkit = toolkit;
		this.context = context;
		this.bindingContext = bindingContext;
		this.anchor = anchor;
		this.selectionSink = selectionSink;
		this.fields = fields;
	}

	/** A form support for fields relative to another anchor (e.g. the selection of a master list). */
	public FormSupport withAnchor(IObservableValue<EObject> newAnchor) {
		return new FormSupport(toolkit, context, bindingContext, newAnchor, selectionSink, fields);
	}

	public Consumer<ISelection> selectionSink() {
		return selectionSink;
	}

	public FormToolkit toolkit() {
		return toolkit;
	}

	public ModelContext context() {
		return context;
	}

	public DataBindingContext bindingContext() {
		return bindingContext;
	}

	public IObservableValue<EObject> anchor() {
		return anchor;
	}

	// ----------------------------------------------------------------------------------------------------
	// Layout
	// ----------------------------------------------------------------------------------------------------

	/** Creates a titled section and returns its client composite (a grid with label/field columns). */
	public Composite section(Composite parent, String title, String description, int columns) {
		int style = ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
		if (description != null) {
			style |= Section.DESCRIPTION;
		}
		Section section = toolkit.createSection(parent, style);
		section.setText(title);
		if (description != null) {
			section.setDescription(description);
			Control descriptionControl = section.getDescriptionControl();
			if (descriptionControl != null) {
				descriptionControl.setForeground(HlomTheme.color(HlomTheme.SLATE));
			}
		}
		GridDataFactory.fillDefaults().grab(true, false).applyTo(section);
		Composite client = toolkit.createComposite(section);
		// Left margin leaves room for the problem markers of the first column.
		GridLayoutFactory.fillDefaults().numColumns(columns).extendedMargins(6, 4, 8, 6).spacing(8, 8)
				.applyTo(client);
		section.setClient(client);
		return client;
	}

	/** Creates a label for a field, with the help text as tooltip. */
	public Label label(Composite parent, String text, String helpKey) {
		Label label = toolkit.createLabel(parent, text, SWT.WRAP);
		label.setData(FIELD_LABEL, Boolean.TRUE);
		label.setForeground(HlomTheme.color(HlomTheme.SLATE));
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(labelWidth(label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x), SWT.DEFAULT).applyTo(label);
		String help = help(helpKey);
		if (help != null) {
			label.setToolTipText(help);
			HelpSupport.setHelpKey(label, helpKey);
		}
		return label;
	}

	/** Marks the labels of fields (see {@link #alignLabels}). */
	private static final String FIELD_LABEL = FormSupport.class.getName() + ".fieldLabel";

	/**
	 * Gives the field labels of all sections in a column of a page the same width, so that the fields of the
	 * sections line up (labels in the details of master/detail blocks are aligned within their block).
	 */
	public static void alignLabels(Composite column) {
		List<Label> labels = new ArrayList<>();
		collectLabels(column, labels);
		int width = labels.stream().mapToInt(l -> l.computeSize(SWT.DEFAULT, SWT.DEFAULT).x).max().orElse(0);
		for (Label label : labels) {
			if (label.getLayoutData() instanceof org.eclipse.swt.layout.GridData data) {
				data.widthHint = labelWidth(width);
			}
		}
	}

	private static int labelWidth(int textWidth) {
		return Math.clamp(textWidth, LABEL_WIDTH, MAX_LABEL_WIDTH);
	}

	private static void collectLabels(Composite composite, List<Label> labels) {
		for (Control child : composite.getChildren()) {
			if (child instanceof Section section) {
				if (section.getClient() instanceof Composite client) {
					// The fields of a section and of its groups (blocks have grids of their own)
					for (Control field : client.getChildren()) {
						if (field instanceof Label label && label.getData(FIELD_LABEL) != null) {
							labels.add(label);
						}
					}
				}
			} else if (child instanceof Composite nested) {
				collectLabels(nested, labels);
			}
		}
	}

	/**
	 * Shows the format hint of a field: as the placeholder of an empty single-line text field, and in the tooltip.
	 */
	public static void applyHint(Control control, String hint) {
		if (control == null || control.isDisposed() || hint == null) {
			return;
		}
		if (control instanceof Text text && (text.getStyle() & SWT.SINGLE) != 0) {
			text.setMessage(hint);
		}
		String tip = control.getToolTipText();
		control.setToolTipText(tip == null ? capitalize(hint) : capitalize(hint) + "\n\n" + tip);
	}

	private static String capitalize(String text) {
		return text.isEmpty() ? text : Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	/** Creates a hint text below/after a field (small, muted). */
	public Label hint(Composite parent, String text) {
		Label label = toolkit.createLabel(parent, text, SWT.WRAP);
		label.setForeground(HlomTheme.color(HlomTheme.MUTED));
		label.setFont(FormWidgets.smallFont());
		return label;
	}

	/** Prefix of help "keys" that are the help text itself (fields of documents without a help file). */
	public static final String LITERAL_HELP = "=";

	/** Returns the help text for a key (or the text of a {@link #LITERAL_HELP literal} key), or {@code null}. */
	public static String help(String key) {
		if (key != null && key.startsWith(LITERAL_HELP)) {
			return key.length() > LITERAL_HELP.length() ? key.substring(LITERAL_HELP.length()) : null;
		}
		return FieldHelp.text(key);
	}

	/** Sets the help text as tooltip and the field's help topic (F1 and Shift+F1). */
	private void tooltip(Control control, String helpKey) {
		String help = help(helpKey);
		if (help != null) {
			control.setToolTipText(help);
			HelpSupport.setHelpKey(control, helpKey);
		}
	}

	/** Aligns a label with the first line of a tall field. */
	private static void alignTop(Label label) {
		if (label.getLayoutData() instanceof org.eclipse.swt.layout.GridData data) {
			data.verticalAlignment = SWT.BEGINNING;
			data.verticalIndent = 3;
		}
	}

	/** Lays out a field so that it spans the remaining columns of a grid row. */
	private static void fill(Control control, int span) {
		GridDataFactory.fillDefaults().grab(true, false).span(span, 1).align(SWT.FILL, SWT.CENTER).hint(150, SWT.DEFAULT)
				.applyTo(control);
	}

	private static int remainingColumns(Composite parent) {
		return Math.max(1, ((GridLayout) parent.getLayout()).numColumns - 1);
	}

	// ----------------------------------------------------------------------------------------------------
	// Simple values
	// ----------------------------------------------------------------------------------------------------

	/**
	 * A single-line text field for an attribute of any simple type (string, number, ...). Input is converted
	 * and validated against the attribute's data type, including XML Schema facets such as patterns.
	 * <p>
	 * Attributes of type {@code xsd:date} or {@code xsd:dateTime} get a {@link #date date picker} instead; the
	 * method then returns {@code null}.
	 */
	public Text text(Composite parent, String labelText, ModelPath path, EAttribute attribute, String helpKey) {
		if (DateProperty.isDate(attribute)) {
			date(parent, labelText, path, attribute, helpKey);
			return null;
		}
		label(parent, labelText, helpKey);
		Text text = toolkit.createText(parent, "", SWT.SINGLE);
		int chars = fieldChars(attribute);
		if (chars > 0) {
			GridDataFactory.fillDefaults().span(remainingColumns(parent), 1).align(SWT.BEGINNING, SWT.CENTER)
					.hint(charsWidth(text, chars), SWT.DEFAULT).applyTo(text);
		} else {
			fill(text, remainingColumns(parent));
		}
		tooltip(text, helpKey);
		bindText(text, path, attribute);
		return text;
	}

	/**
	 * The width (in characters) of a field for values of a short type: numbers, durations, language codes,
	 * identifiers and strings of a limited length. Other values (texts, URIs, ...) get the full width ({@code -1}).
	 */
	static int fieldChars(EAttribute attribute) {
		ExtendedMetaData metaData = ExtendedMetaData.INSTANCE;
		if (metaData.getFeatureKind(attribute) == ExtendedMetaData.ATTRIBUTE_FEATURE
				&& metaData.getName(attribute).equalsIgnoreCase("id")) {
			// Identifier attributes, also those the schemas declare as plain strings
			return 30;
		}
		EDataType type = attribute.getEAttributeType();
		Class<?> instanceClass = type.getInstanceClass();
		if (instanceClass != null && (Number.class.isAssignableFrom(instanceClass) || instanceClass == int.class
				|| instanceClass == long.class || instanceClass == short.class || instanceClass == byte.class
				|| instanceClass == double.class || instanceClass == float.class)) {
			return 14;
		}
		if (instanceClass == javax.xml.datatype.Duration.class) {
			return 18;
		}
		for (EDataType t = type; t != null; t = metaData.getBaseType(t)) {
			int maxLength = metaData.getMaxLengthFacet(t);
			if (maxLength > 0) {
				return maxLength <= 60 ? Math.max(maxLength + 2, 8) : -1;
			}
			if (t.getEPackage() == org.eclipse.emf.ecore.xml.type.XMLTypePackage.eINSTANCE) {
				return switch (metaData.getName(t)) {
					case "language" -> 12;
					case "ID", "IDREF", "NCName" -> 30;
					default -> -1;
				};
			}
		}
		return -1;
	}

	private static int charsWidth(Control control, int chars) {
		org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(control);
		try {
			return (int) Math.ceil(gc.getFontMetrics().getAverageCharacterWidth() * chars) + 8;
		} finally {
			gc.dispose();
		}
	}

	/**
	 * A check box for a boolean attribute, in the field column with the label as its text (Windows and Eclipse
	 * convention). Clearing it sets false (an explicit value, also for required elements).
	 */
	public Button check(Composite parent, String labelText, ModelPath path, EAttribute attribute, String helpKey) {
		toolkit.createLabel(parent, "");
		Button button = toolkit.createButton(parent, labelText, SWT.CHECK);
		GridDataFactory.fillDefaults().span(remainingColumns(parent), 1).align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(button);
		tooltip(button, helpKey);
		IObservableValue<Boolean> target = WidgetProperties.buttonSelection().observe(button);
		IObservableValue<Object> model = context.value(path, attribute).observeDetail(anchor);
		bindingContext.bindValue(target, model,
				UpdateValueStrategy.create(IConverter.create(Boolean.class, Object.class,
						v -> Boolean.TRUE.equals(v) ? Boolean.TRUE : Boolean.FALSE)),
				UpdateValueStrategy.create(IConverter.create(Object.class, Boolean.class, Boolean.TRUE::equals)));
		bindEnabled(button);
		fields.register(button, anchor, path, attribute, FieldProblems.Match.VALUE);
		return button;
	}

	/** Whether an attribute holds a boolean (edited with a {@link #check check box}). */
	public static boolean isBoolean(EAttribute attribute) {
		Class<?> instanceClass = attribute.getEAttributeType().getInstanceClass();
		return instanceClass == boolean.class || instanceClass == Boolean.class;
	}

	/**
	 * A date picker for an {@code xsd:date} attribute (date only) or an {@code xsd:dateTime} attribute (date
	 * and time), with a button to clear the value.
	 */
	public CDateTime date(Composite parent, String labelText, ModelPath path, EAttribute attribute, String helpKey) {
		label(parent, labelText, helpKey);
		Composite row = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(4, 0).applyTo(row);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(row);

		boolean dateOnly = DateProperty.isDateOnly(attribute);
		CDateTime picker = new CDateTime(row, CDT.BORDER | CDT.DROP_DOWN | CDT.DATE_MEDIUM
				| (dateOnly ? 0 : CDT.TIME_SHORT | CDT.CLOCK_24_HOUR));
		toolkit.adapt(picker, true, false);
		picker.setPattern(dateOnly ? "yyyy-MM-dd" : "yyyy-MM-dd  HH:mm");
		picker.setNullText(Messages.FormSupport_NotSet);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).hint(dateOnly ? 170 : 200, SWT.DEFAULT)
				.applyTo(picker);
		tooltip(picker, helpKey);

		ToolBar bar = FormWidgets.toolBar(toolkit, row);
		ToolItem clear = FormWidgets.toolItem(bar, "remove", Messages.FormSupport_Clear, "Clear the " + (dateOnly ? "date" : "date and time"),
				() -> {
					EObject anchorObject = anchor.getValue();
					if (anchorObject != null) {
						ModelCommands.execute(context.domain(),
								ModelCommands.setValue(context.domain(), anchorObject, path, attribute, null));
					}
				});
		bindDate(picker, path, attribute);
		Runnable updateClear = () -> {
			if (!picker.isDisposed()) {
				clear.setEnabled(picker.getSelection() != null);
			}
		};
		picker.addListener(SWT.Selection, _ -> updateClear.run());
		Runnable refresher = () -> picker.getDisplay().asyncExec(updateClear);
		context.broadcaster().addListener(refresher);
		picker.addDisposeListener(_ -> context.broadcaster().removeListener(refresher));
		anchor.addValueChangeListener(_ -> updateClear.run());
		updateClear.run();
		bindEnabled(row);
		fields.register(row, anchor, path, attribute, FieldProblems.Match.VALUE);
		return picker;
	}

	/** A multi-line text field. */
	public Text multiText(Composite parent, String labelText, ModelPath path, EAttribute attribute, String helpKey,
			int lines) {
		Label label = label(parent, labelText, helpKey);
		alignTop(label);
		Text text = toolkit.createText(parent, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1)
				.hint(150, text.getLineHeight() * lines).applyTo(text);
		FormWidgets.autoScrollBar(text);
		tooltip(text, helpKey);
		bindText(text, path, attribute);
		return text;
	}

	/** A text field with suggested values (an editable combo box). */
	public Combo suggestText(Composite parent, String labelText, ModelPath path, EAttribute attribute,
			List<String> suggestions, String helpKey) {
		label(parent, labelText, helpKey);
		Combo combo = new Combo(parent, SWT.DROP_DOWN);
		toolkit.adapt(combo, true, false);
		combo.setItems(suggestions.toArray(String[]::new));
		combo.setVisibleItemCount(Math.clamp(suggestions.size(), 5, 20));
		fill(combo, remainingColumns(parent));
		tooltip(combo, helpKey);
		bindText(combo, path, attribute);
		return combo;
	}

	/**
	 * A single reference to an element of the document: an editable combo box offering the possible targets by
	 * their labels (storing the reference text of the chosen one) and a button showing the referenced element.
	 */
	public Combo referenceText(Composite parent, String labelText, ModelPath path, EAttribute attribute,
			ValueList.Presentation presentation, String helpKey) {
		label(parent, labelText, helpKey);
		Composite row = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(4, 0).applyTo(row);
		fill(row, remainingColumns(parent));
		Combo combo = new Combo(row, SWT.DROP_DOWN);
		toolkit.adapt(combo, true, false);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).hint(150, SWT.DEFAULT).applyTo(combo);
		tooltip(combo, helpKey);
		Runnable choices = () -> {
			String typed = combo.getText();
			String[] items = presentation.choices().stream().map(ValueList.Presentation.Choice::label)
					.toArray(String[]::new);
			if (!java.util.Arrays.equals(items, combo.getItems())) {
				combo.setItems(items);
				combo.setVisibleItemCount(Math.clamp(items.length, 5, 20));
				combo.setText(typed);
			}
		};
		combo.addListener(SWT.FocusIn, _ -> choices.run());
		combo.addListener(SWT.MouseDown, _ -> choices.run());
		IObservableValue<Object> model = context.value(path, attribute).observeDetail(anchor);
		ToolBar bar = FormWidgets.toolBar(toolkit, row);
		FormWidgets.toolItem(bar, "goto", Messages.FormSupport_Go, presentation.openLabel(), () -> {
			Object value = model.getValue();
			if (value != null) {
				presentation.open(ModelCommands.toString(attribute, value));
			}
		});

		IObservableValue<String> target = new TextProperty().observeDelayed(TEXT_DELAY, combo);
		UpdateValueStrategy<String, Object> toModel = new UpdateValueStrategy<>();
		toModel.setConverter(IConverter.create(String.class, Object.class, input -> {
			String text = input == null ? "" : input.strip();
			Object current = model.getValue();
			if (current != null && presentation.display(ModelCommands.toString(attribute, current)).equals(text)) {
				return current;
			}
			for (ValueList.Presentation.Choice choice : presentation.choices()) {
				if (choice.label().equals(text)) {
					return ModelCommands.fromString(attribute, choice.value());
				}
			}
			return text.isEmpty() ? null : ModelCommands.fromString(attribute, text);
		}));
		UpdateValueStrategy<Object, String> toTarget = new UpdateValueStrategy<>();
		toTarget.setConverter(IConverter.create(Object.class, String.class,
				value -> value == null ? "" : presentation.display(ModelCommands.toString(attribute, value))));
		bindingContext.bindValue(target, model, toModel, toTarget);
		bindEnabled(row);
		fields.register(combo, anchor, path, attribute, FieldProblems.Match.VALUE);
		return combo;
	}

	/**
	 * Binds a text widget (Text or editable Combo) to an attribute with type conversion and validation. A
	 * {@link CDateTime} is bound to a date attribute.
	 */
	public Binding bindText(Control widget, ModelPath path, EAttribute attribute) {
		return bindText(widget, path, attribute, true);
	}

	private Binding bindText(Control widget, ModelPath path, EAttribute attribute, boolean track) {
		if (widget instanceof CDateTime picker) {
			return bindDate(picker, path, attribute);
		}
		IObservableValue<String> target = new TextProperty().observeDelayed(TEXT_DELAY, widget);
		IObservableValue<Object> model = context.value(path, attribute).observeDetail(anchor);
		boolean isString = attribute.getEAttributeType().getInstanceClass() == String.class;

		boolean singleLine = (widget.getStyle() & SWT.MULTI) == 0;

		UpdateValueStrategy<String, Object> toModel = new UpdateValueStrategy<>();
		toModel.setAfterGetValidator(input -> validate(attribute, input));
		toModel.setConverter(IConverter.create(String.class, Object.class, input -> {
			// Windows multi-line text widgets report \r\n line breaks.
			String text = input == null ? "" : input.replace("\r\n", "\n");
			Object current = model.getValue();
			if (current != null && sameText(text, ModelCommands.toString(attribute, current), singleLine)) {
				// Only the widget's representation differs (e.g. line breaks a single-line field can't show).
				return current;
			}
			String value = isString ? text : text.trim();
			return value.isEmpty() ? null : ModelCommands.fromString(attribute, value);
		}));
		UpdateValueStrategy<Object, String> toTarget = new UpdateValueStrategy<>();
		toTarget.setConverter(IConverter.create(Object.class, String.class,
				value -> ModelCommands.toString(attribute, value)));

		Binding binding = bindingContext.bindValue(target, model, toModel, toTarget);
		ControlDecorationSupport.create(binding, SWT.BOTTOM | SWT.LEFT);
		bindEnabled(widget);
		if (track) {
			fields.register(widget, anchor, path, attribute, FieldProblems.Match.VALUE);
		}
		return binding;
	}

	/**
	 * Binds a date picker to an {@code xsd:date} or {@code xsd:dateTime} attribute. Showing a value never
	 * writes it back; clearing the picker unsets the value (removing elements that become empty).
	 */
	private Binding bindDate(CDateTime picker, ModelPath path, EAttribute attribute) {
		IObservableValue<Date> target = new DateProperty().observeDelayed(TEXT_DELAY, picker);
		IObservableValue<Object> model = context.value(path, attribute).observeDetail(anchor);
		UpdateValueStrategy<Date, Object> toModel = new UpdateValueStrategy<>();
		toModel.setConverter(IConverter.create(Date.class, Object.class,
				date -> DateProperty.toValue(attribute, date, model.getValue())));
		UpdateValueStrategy<Object, Date> toTarget = new UpdateValueStrategy<>();
		toTarget.setConverter(IConverter.create(Object.class, Date.class, DateProperty::toDate));
		Binding binding = bindingContext.bindValue(target, model, toModel, toTarget);
		bindEnabled(picker);
		return binding;
	}

	/**
	 * Whether widget text represents the model text. Single-line fields can't show line breaks, so whitespace
	 * differences are ignored for them.
	 */
	static boolean sameText(String widgetText, String modelText, boolean singleLine) {
		String model = modelText.replace("\r\n", "\n");
		if (singleLine) {
			return widgetText.replaceAll("\\s+", " ").trim().equals(model.replaceAll("\\s+", " ").trim());
		}
		return widgetText.equals(model);
	}

	/** Validates text input against an attribute's data type. */
	static IStatus validate(EAttribute attribute, String input) {
		if (input == null || input.isBlank()) {
			return ValidationStatus.ok();
		}
		EDataType type = attribute.getEAttributeType();
		Object value;
		try {
			value = ModelCommands.fromString(attribute, type.getInstanceClass() == String.class ? input : input.trim());
		} catch (RuntimeException _) {
			return ValidationStatus.error("\"" + input.trim() + "\" is not a valid " + typeDescription(type) + ".");
		}
		BasicDiagnostic diagnostic = new BasicDiagnostic();
		if (!Diagnostician.INSTANCE.validate(type, value, diagnostic, Diagnostician.INSTANCE.createDefaultContext())) {
			for (Diagnostic child : diagnostic.getChildren()) {
				return ValidationStatus.error(child.getMessage());
			}
			return ValidationStatus.error(Messages.FormSupport_InvalidValue);
		}
		return ValidationStatus.ok();
	}

	private static String typeDescription(EDataType type) {
		String name = ExtendedMetaData.INSTANCE.getName(type);
		Class<?> instanceClass = type.getInstanceClass();
		if (instanceClass == java.math.BigInteger.class || instanceClass == int.class || instanceClass == Integer.class) {
			return Messages.FormSupport_WholeNumber;
		}
		if (instanceClass == java.math.BigDecimal.class) {
			return "number";
		}
		if (instanceClass == javax.xml.datatype.XMLGregorianCalendar.class) {
			return "date".equals(name) ? "date (YYYY-MM-DD)" : "date and time (YYYY-MM-DDThh:mm:ss)";
		}
		return "value";
	}

	/** A read-only combo box for an enumeration attribute, with an empty entry to unset the value. */
	public ComboViewer enumCombo(Composite parent, String labelText, ModelPath path, EAttribute attribute,
			String helpKey) {
		List<Object> items = new ArrayList<>();
		items.add(NOT_SET);
		EEnum eEnum = enumOf(attribute.getEAttributeType());
		for (EEnumLiteral literal : eEnum.getELiterals()) {
			items.add(literal.getInstance());
		}
		ComboViewer viewer = combo(parent, labelText, items, helpKey);
		IObservableValue<Object> target = ViewerProperties.singleSelection(Object.class).observe(viewer);
		IObservableValue<Object> model = context.value(path, attribute).observeDetail(anchor);
		bindingContext.bindValue(target, model,
				UpdateValueStrategy.create(IConverter.create(Object.class, Object.class, v -> v == NOT_SET ? null : v)),
				UpdateValueStrategy.create(IConverter.create(Object.class, Object.class, v -> v == null ? NOT_SET : v)));
		bindEnabled(viewer.getControl());
		fields.register(viewer.getControl(), anchor, path, attribute, FieldProblems.Match.VALUE);
		return viewer;
	}

	/**
	 * A read-only combo box for a single-valued LOM vocabulary element (sets both source and value).
	 * MedBiquitous healthcare terms are marked with "HLOM".
	 */
	public ComboViewer vocabCombo(Composite parent, String labelText, ModelPath vocabPath, String helpKey) {
		EReference last = vocabPath.steps().getLast().reference();
		List<Object> items = new ArrayList<>();
		items.add(NOT_SET);
		items.addAll(Vocabularies.terms(last.getEReferenceType()));
		ComboViewer viewer = combo(parent, labelText, items, helpKey);
		IObservableValue<Object> target = ViewerProperties.singleSelection(Object.class).observe(viewer);
		IObservableValue<Term> model = context.vocabulary(vocabPath).observeDetail(anchor);
		bindingContext.bindValue(target, model,
				UpdateValueStrategy.create(IConverter.create(Object.class, Term.class, v -> v == NOT_SET ? null : (Term) v)),
				UpdateValueStrategy.create(IConverter.create(Term.class, Object.class,
						v -> v == null ? NOT_SET : items.stream().filter(i -> i instanceof Term t
								&& t.value().equals(v.value())).findFirst().orElse(NOT_SET))));
		bindEnabled(viewer.getControl());
		fields.register(viewer.getControl(), anchor, vocabPath, null, FieldProblems.Match.ELEMENT);
		return viewer;
	}

	private ComboViewer combo(Composite parent, String labelText, List<Object> items, String helpKey) {
		label(parent, labelText, helpKey);
		ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY | SWT.DROP_DOWN);
		toolkit.adapt(viewer.getControl(), true, false);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == NOT_SET) {
					return "";
				}
				if (element instanceof Term term) {
					return VocabularyCheckList.isHealthcare(term) ? term.value() + HEALTHCARE_MARK : term.value();
				}
				return element instanceof Enumerator e ? e.getLiteral() : element.toString();
			}
		});
		viewer.setInput(items);
		viewer.getCombo().setVisibleItemCount(Math.min(20, items.size()));
		fill(viewer.getControl(), remainingColumns(parent));
		tooltip(viewer.getControl(), helpKey);
		return viewer;
	}

	static EEnum enumOf(EDataType type) {
		if (type instanceof EEnum eEnum) {
			return eEnum;
		}
		EDataType base = ExtendedMetaData.INSTANCE.getBaseType(type);
		if (base != null) {
			return enumOf(base);
		}
		throw new IllegalArgumentException(NLS.bind(Messages.FormSupport_NotAnEnumeration, type.getName()));
	}

	// ----------------------------------------------------------------------------------------------------
	// Language strings
	// ----------------------------------------------------------------------------------------------------

	/** Path step into the first {@code string} of a LanguageString, creating it with the default language. */
	public static ModelPath firstString(ModelPath languageStringPath) {
		return languageStringPath.then(LomPackage.eINSTANCE.getLanguageString_String(),
				object -> ((edu.louisiana.caai.lom.model.lom.LangString) object)
						.setLanguage(HlomDocuments.DEFAULT_LANGUAGE));
	}

	/**
	 * A multilingual text field: the text and language of the first string of a LanguageString element, plus a
	 * translate button to edit all translations (showing how many more languages exist).
	 */
	public Text langText(Composite parent, String labelText, ModelPath languageStringPath, boolean multiLine,
			String helpKey) {
		Label label = label(parent, labelText, helpKey);
		Composite row = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(4, 0).applyTo(row);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(row);

		ModelPath stringPath = firstString(languageStringPath);
		Text text = toolkit.createText(row, "", multiLine ? SWT.MULTI | SWT.WRAP | SWT.V_SCROLL : SWT.SINGLE);
		if (multiLine) {
			alignTop(label);
			GridDataFactory.fillDefaults().grab(true, false).hint(150, text.getLineHeight() * 4).applyTo(text);
			FormWidgets.autoScrollBar(text);
		} else {
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).hint(150, SWT.DEFAULT)
					.applyTo(text);
		}
		tooltip(text, helpKey);
		bindText(text, stringPath, LomPackage.eINSTANCE.getLangString_Value(), false);

		Combo language = new Combo(row, SWT.DROP_DOWN);
		toolkit.adapt(language, true, false);
		language.setItems(HlomSuggestions.LANGUAGES.toArray(String[]::new));
		language.setVisibleItemCount(15);
		language.setToolTipText(Messages.FormSupport_LanguageOfTheTextISO);
		GridDataFactory.swtDefaults().hint(LANGUAGE_WIDTH, SWT.DEFAULT)
				.align(SWT.BEGINNING, multiLine ? SWT.BEGINNING : SWT.CENTER).applyTo(language);
		bindText(language, stringPath, LomPackage.eINSTANCE.getLangString_Language(), false);

		ToolBar bar = FormWidgets.toolBar(toolkit, row);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, multiLine ? SWT.BEGINNING : SWT.CENTER).applyTo(bar);
		ToolItem translations = FormWidgets.toolItem(bar, "translate", Messages.FormSupport_Translations,
				Messages.FormSupport_EditTheTextInSeveral, () -> {
					EObject anchorObject = anchor.getValue();
					if (anchorObject != null) {
						new LanguageStringDialog(parent.getShell(), context.domain(), anchorObject, languageStringPath,
								labelText).open();
					}
				});
		boolean hasIcon = translations.getImage() != null;
		Runnable updateCount = () -> {
			if (translations.isDisposed()) {
				return;
			}
			EObject anchorObject = anchor.getValue();
			EObject element = anchorObject == null ? null : languageStringPath.resolve(anchorObject);
			int more = element instanceof LanguageString string ? Math.max(0, string.getString().size() - 1) : 0;
			String count = more > 0 ? "+" + more : "";
			if (hasIcon && !count.equals(translations.getText())) {
				translations.setText(count);
				row.layout(true);
			}
			translations.setToolTipText(more > 0
					? "Also available in " + more + " more language" + (more == 1 ? "" : "s") + " \u2013 edit translations"
					: "Edit the text in several languages");
		};
		context.broadcaster().addListener(updateCount);
		row.addDisposeListener(_ -> context.broadcaster().removeListener(updateCount));
		anchor.addValueChangeListener(_ -> updateCount.run());
		updateCount.run();
		bindEnabled(row);
		fields.register(row, anchor, languageStringPath, null, FieldProblems.Match.ELEMENT);
		return text;
	}

	// ----------------------------------------------------------------------------------------------------
	// Lists
	// ----------------------------------------------------------------------------------------------------

	/** A check box list for a multi-valued vocabulary reference, e.g. learning resource types. */
	public VocabularyCheckList vocabChecks(Composite parent, String labelText, ModelPath ownerPath,
			EReference vocabReference, int columns, String helpKey) {
		alignTop(label(parent, labelText, helpKey));
		VocabularyCheckList list = new VocabularyCheckList(this, parent, ownerPath, vocabReference, columns);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(list.control());
		tooltip(list.control(), helpKey);
		fields.register(list.control(), anchor, ownerPath, vocabReference, FieldProblems.Match.LIST);
		return list;
	}

	/** A check box list for a multi-valued enumeration attribute, e.g. participation modality. */
	public EnumCheckList enumChecks(Composite parent, String labelText, ModelPath ownerPath, EAttribute attribute,
			int columns, String helpKey) {
		alignTop(label(parent, labelText, helpKey));
		EnumCheckList list = new EnumCheckList(this, parent, ownerPath, attribute, columns);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(list.control());
		tooltip(list.control(), helpKey);
		fields.register(list.control(), anchor, ownerPath, attribute, FieldProblems.Match.VALUE);
		return list;
	}

	/**
	 * An editable list of simple values, shown as removable tokens: either elements with a single content
	 * attribute (e.g. technical formats) or a multi-valued string attribute (e.g. non-accredited providers).
	 */
	public ValueList valueList(Composite parent, String labelText, ValueList.Kind kind, ModelPath ownerPath,
			Object feature, EAttribute contentAttribute, List<String> suggestions, String helpKey) {
		return valueList(parent, labelText, kind, ownerPath, feature, contentAttribute, suggestions, null, helpKey);
	}

	/** An editable list of values shown and chosen through a presentation (such as references), or as they are. */
	public ValueList valueList(Composite parent, String labelText, ValueList.Kind kind, ModelPath ownerPath,
			Object feature, EAttribute contentAttribute, List<String> suggestions,
			ValueList.Presentation presentation, String helpKey) {
		alignTop(label(parent, labelText, helpKey));
		ValueList list = new ValueList(this, parent, kind, ownerPath, feature, contentAttribute, suggestions,
				presentation);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(list.control());
		tooltip(list.control(), helpKey);
		fields.register(list.control(), anchor, ownerPath, (EStructuralFeature) feature,
				kind == ValueList.Kind.ELEMENTS ? FieldProblems.Match.LIST : FieldProblems.Match.VALUE);
		return list;
	}

	/** An editable list of multilingual strings, e.g. keywords or target professions. */
	public LanguageStringList langList(Composite parent, String labelText, ModelPath ownerPath,
			EReference reference, List<String> suggestions, boolean keywordAttributes, String helpKey) {
		alignTop(label(parent, labelText, helpKey));
		LanguageStringList list = new LanguageStringList(this, parent, ownerPath, reference, suggestions,
				keywordAttributes, labelText);
		GridDataFactory.fillDefaults().grab(true, false).span(remainingColumns(parent), 1).applyTo(list.control());
		tooltip(list.control(), helpKey);
		fields.register(list.control(), anchor, ownerPath, reference, FieldProblems.Match.LIST);
		return list;
	}

	/** A master/detail block for repeating complex elements. */
	public MasterDetail masterDetail(Composite parent, ModelPath ownerPath, EReference reference,
			Function<EObject, String> labeler, MasterDetail.ElementFactory factory, MasterDetail.DetailBuilder details) {
		MasterDetail block = new MasterDetail(this, parent, ownerPath, reference, labeler, factory, details);
		GridDataFactory.fillDefaults().grab(true, false).span(((GridLayout) parent.getLayout()).numColumns, 1)
				.applyTo(block.control());
		fields.register(block.control(), anchor, ownerPath, reference,
				FieldProblems.Match.LIST);
		return block;
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * Shows validation problems next to the fields of this form (and of the detail forms derived from it): each
	 * field whose model element is the problem's element gets an error or warning decoration with the message.
	 */
	public void showProblems(java.util.List<edu.louisiana.caai.lom.model.support.HlomProblem> problems) {
		fields.show(problems == null ? List.<HlomProblem>of() : problems);
	}

	/** Disables a control while the anchor is missing (e.g. no element is selected in a master list). */
	public void bindEnabled(Control control) {
		Runnable update = () -> {
			if (!control.isDisposed()) {
				control.setEnabled(anchor.getValue() != null);
			}
		};
		anchor.addValueChangeListener(_ -> update.run());
		update.run();
	}

}
