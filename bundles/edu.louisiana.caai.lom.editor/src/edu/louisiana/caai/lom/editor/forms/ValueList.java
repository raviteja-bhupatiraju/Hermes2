/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.chips.Chips;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.editor.binding.ModelCommands;
import edu.louisiana.caai.lom.editor.binding.ModelPath;

/**
 * An editable list of simple values, shown as removable tokens ("chips") followed by an input field. The
 * values are either
 * <ul>
 * <li>{@link Kind#ELEMENTS elements} with a single content attribute (e.g. {@code technical/format}), or</li>
 * <li>{@link Kind#VALUES values} of a multi-valued attribute (e.g. {@code credits/nonAccreditedProvider}).</li>
 * </ul>
 * New values are typed (or picked from suggestions) and added with Enter; a token's close button removes it,
 * double-clicking it edits it, and its context menu also moves it.
 */
public final class ValueList {

	/** How the values are stored. */
	public enum Kind {
		ELEMENTS, VALUES
	}

	/**
	 * How the values of a list are shown and chosen when they stand for something else, such as references to
	 * elements of the document: tokens show the referenced element, the input offers the possible targets and a
	 * token opens its target.
	 */
	public interface Presentation {

		/** A value that can be added, by its label. */
		record Choice(String label, String value) {
		}

		/** The text of a token for a value. */
		String display(String value);

		/** The values that can be added. */
		List<Choice> choices();

		/** Shows what a value stands for (if it can). */
		void open(String value);

		/** The label of the menu item opening a value, such as "Go to Event". */
		String openLabel();
	}

	/** A value: its position and either the element or the plain value. */
	private record Row(int index, Object item) {
	}

	/** The narrowest a token gets before its text is shortened no further. */
	private static final int MIN_CHIP_WIDTH = 60;

	private final FormSupport form;
	private final Kind kind;
	private final ModelPath ownerPath;
	private final EStructuralFeature feature;
	private final EAttribute contentAttribute;
	private final List<String> suggestions;
	private final Presentation presentation;
	private final Composite composite;
	private final Composite flow;
	private final Combo input;
	private final List<Chips> chips = new ArrayList<>();
	/** The full text shown by each token (shortened to the width of the list when it doesn't fit). */
	private final List<String> chipTexts = new ArrayList<>();
	private List<Object> shown;
	private final Runnable refresher = this::refresh;

	ValueList(FormSupport form, Composite parent, Kind kind, ModelPath ownerPath, Object feature,
			EAttribute contentAttribute, List<String> suggestions) {
		this(form, parent, kind, ownerPath, feature, contentAttribute, suggestions, null);
	}

	ValueList(FormSupport form, Composite parent, Kind kind, ModelPath ownerPath, Object feature,
			EAttribute contentAttribute, List<String> suggestions, Presentation presentation) {
		this.form = form;
		this.presentation = presentation;
		this.kind = kind;
		this.ownerPath = ownerPath;
		this.feature = (EStructuralFeature) feature;
		this.contentAttribute = kind == Kind.ELEMENTS ? contentAttribute : (EAttribute) feature;
		this.suggestions = suggestions == null ? List.of() : List.copyOf(suggestions);

		// The tokens wrap in the full width of the field; the input fills the row below them.
		composite = form.toolkit().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(4, 6).applyTo(composite);
		flow = form.toolkit().createComposite(composite);
		flow.setLayout(new WrapLayout(6));
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(150, SWT.DEFAULT).applyTo(flow);
		flow.addListener(SWT.Resize, _ -> fitChips());

		input = new Combo(composite, SWT.DROP_DOWN);
		form.toolkit().adapt(input, true, false);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).hint(150, SWT.DEFAULT)
				.applyTo(input);
		input.setVisibleItemCount(Math.clamp(this.suggestions.size(), 5, 20));
		input.setToolTipText(presentation != null ? "Choose or type a value and press Enter to add it"
				: this.suggestions.isEmpty() ? "Type a value and press Enter to add it"
						: "Type or choose a value and press Enter to add it");
		if (presentation != null) {
			// The choices (such as the elements a reference can name) change with the document.
			input.addListener(SWT.FocusIn, _ -> updateChoices());
			input.addListener(SWT.MouseDown, _ -> updateChoices());
		}
		input.addListener(SWT.DefaultSelection, _ -> addValue());
		ToolBar inputBar = FormWidgets.toolBar(form.toolkit(), composite);
		FormWidgets.toolItem(inputBar, "add", Messages.ValueList_Add, Messages.ValueList_AddTheValueEnter, this::addValue);

		form.context().broadcaster().addListener(refresher);
		IValueChangeListener<EObject> anchorListener = _ -> refresh();
		form.anchor().addValueChangeListener(anchorListener);
		composite.addDisposeListener(_ -> {
			form.context().broadcaster().removeListener(refresher);
			form.anchor().removeValueChangeListener(anchorListener);
		});
		form.bindEnabled(composite);
		refresh();
	}

	public Control control() {
		return composite;
	}

	private EditingDomain domain() {
		return form.context().domain();
	}

	private EObject owner() {
		EObject anchor = form.anchor().getValue();
		return anchor == null ? null : ownerPath.resolve(anchor);
	}

	private Object value(Row row) {
		return kind == Kind.ELEMENTS ? ((EObject) row.item()).eGet(contentAttribute) : row.item();
	}

	private String text(Row row) {
		return ModelCommands.toString(contentAttribute, value(row));
	}

	private List<Row> rows() {
		List<Row> rows = new ArrayList<>();
		EObject owner = owner();
		if (owner != null && owner.eClass().getEAllStructuralFeatures().contains(feature)) {
			List<?> items = (List<?>) owner.eGet(feature);
			for (int i = 0; i < items.size(); i++) {
				rows.add(new Row(i, items.get(i)));
			}
		}
		return rows;
	}

	/** Rebuilds the tokens when the values changed. */
	private void refresh() {
		if (composite.isDisposed()) {
			return;
		}
		List<Row> rows = rows();
		List<Object> signature = new ArrayList<>();
		for (Row row : rows) {
			signature.add(row.item());
			signature.add(text(row));
		}
		if (signature.equals(shown)) {
			return;
		}
		shown = signature;
		chips.forEach(Chips::dispose);
		chips.clear();
		chipTexts.clear();
		List<String> present = new ArrayList<>();
		for (Row row : rows) {
			chips.add(createChip(row));
			present.add(text(row));
		}
		// Without tokens the list is only its input row.
		((GridData) flow.getLayoutData()).exclude = rows.isEmpty();
		flow.setVisible(!rows.isEmpty());
		fitChips();
		// Offer only the suggestions that aren't values yet.
		if (presentation != null) {
			updateChoices();
		} else {
			String typed = input.getText();
			input.setItems(suggestions.stream().filter(s -> !present.contains(s)).toArray(String[]::new));
			input.setText(typed);
		}
		flow.layout(true);
		FormWidgets.reflow(composite);
	}

	/** Offers the choices of the presentation that aren't values yet. */
	private void updateChoices() {
		if (input.isDisposed()) {
			return;
		}
		List<String> present = rows().stream().map(this::text).toList();
		String typed = input.getText();
		String[] items = presentation.choices().stream().filter(choice -> !present.contains(choice.value()))
				.map(Presentation.Choice::label).toArray(String[]::new);
		if (!java.util.Arrays.equals(items, input.getItems())) {
			input.setItems(items);
			input.setVisibleItemCount(Math.clamp(items.length, 5, 20));
			input.setText(typed);
		}
	}

	private Chips createChip(Row row) {
		String text = text(row);
		boolean vcard = text.contains("BEGIN:VCARD");
		Chips chip = new Chips(flow, SWT.CLOSE);
		chip.setBackground(flow.getBackground());
		chip.setChipsBackground(HlomTheme.color(HlomTheme.BAR));
		chip.setHoverBackground(HlomTheme.color(HlomTheme.BORDER));
		chip.setBorderColor(HlomTheme.color(HlomTheme.BORDER));
		chip.setHoverBorderColor(HlomTheme.color(HlomTheme.SLATE));
		chip.setForeground(HlomTheme.color(HlomTheme.INK));
		chip.setHoverForeground(HlomTheme.color(HlomTheme.INK));
		chip.setCloseButtonForeground(HlomTheme.color(HlomTheme.SLATE));
		chip.setCloseButtonBackground(HlomTheme.color(HlomTheme.BAR));
		chip.setCloseButtonHoverForeground(HlomTheme.color(HlomTheme.SURFACE));
		chip.setCloseButtonHoverBackground(HlomTheme.color(HlomTheme.ACCENT));
		if (vcard && FormWidgets.icon("vcard") != null) {
			chip.setImage(FormWidgets.icon("vcard"));
		}
		String shown = (presentation != null ? presentation.display(text) : vcard ? vcardName(text) : text).strip()
				.replaceAll("\\s+", " ");
		chipTexts.add(shown);
		chip.setText(shown);
		chip.setToolTipText(text.strip() + (presentation != null
				? "\n\nCtrl+click: " + presentation.openLabel() + "; double-click to edit; right-click for more actions."
				: "\n\nDouble-click to edit; right-click for more actions."));
		if (presentation != null) {
			chip.addListener(SWT.MouseUp, event -> {
				if ((event.stateMask & SWT.MOD1) != 0) {
					presentation.open(text);
				}
			});
		}
		chip.addCloseListener(_ -> remove(row));
		chip.addListener(SWT.MouseDoubleClick, _ -> edit(row));
		chip.setMenu(createMenu(chip, row));
		return chip;
	}

	private Menu createMenu(Chips chip, Row row) {
		Menu menu = new Menu(chip);
		if (presentation != null) {
			menuItem(menu, presentation.openLabel(), () -> presentation.open(text(row)));
		}
		menuItem(menu, Messages.ValueList_Edit, () -> edit(row));
		new MenuItem(menu, SWT.SEPARATOR);
		MenuItem left = menuItem(menu, Messages.ValueList_MoveLeft, () -> move(row, -1));
		MenuItem right = menuItem(menu, Messages.ValueList_MoveRight, () -> move(row, 1));
		new MenuItem(menu, SWT.SEPARATOR);
		menuItem(menu, Messages.ValueList_Remove, () -> remove(row));
		menu.addListener(SWT.Show, _ -> {
			left.setEnabled(row.index() > 0);
			right.setEnabled(row.index() < chips.size() - 1);
		});
		return menu;
	}

	private static MenuItem menuItem(Menu menu, String text, Runnable action) {
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		item.addListener(SWT.Selection, _ -> action.run());
		return item;
	}

	/**
	 * Shortens the text of tokens wider than the list, in the middle: references and paths differ at their ends
	 * ({@code /CurriculumInventory/Events/Event[@id='E12']}). The tooltip shows the full text.
	 */
	private void fitChips() {
		int width = flow.getClientArea().width;
		if (width <= 0 || chips.isEmpty()) {
			return;
		}
		boolean changed = false;
		GC gc = new GC(flow);
		try {
			for (int i = 0; i < chips.size(); i++) {
				Chips chip = chips.get(i);
				String full = chipTexts.get(i);
				gc.setFont(chip.getFont());
				// The width of the token around its text: padding, image and close button.
				int chrome = chip.computeSize(SWT.DEFAULT, SWT.DEFAULT).x - gc.textExtent(chip.getText()).x;
				String fitted = fit(gc, full, Math.max(MIN_CHIP_WIDTH, width - chrome));
				if (!fitted.equals(chip.getText())) {
					chip.setText(fitted);
					changed = true;
				}
			}
		} finally {
			gc.dispose();
		}
		if (changed) {
			flow.layout(true);
			FormWidgets.reflow(composite);
		}
	}

	/** The text, with an ellipsis in place of its middle if it is wider than {@code width}. */
	static String fit(GC gc, String text, int width) {
		if (gc.textExtent(text).x <= width) {
			return text;
		}
		int low = 0;
		int high = text.length();
		String best = "\u2026";
		while (low <= high) {
			int keep = (low + high) / 2;
			int head = (keep + 1) / 2;
			String candidate = text.substring(0, head) + "\u2026" + text.substring(text.length() - (keep - head));
			if (gc.textExtent(candidate).x <= width) {
				best = candidate;
				low = keep + 1;
			} else {
				high = keep - 1;
			}
		}
		return best;
	}

	/** The formatted name of a vCard, or its first meaningful line. */
	private static String vcardName(String vcard) {
		String[] lines = vcard.split("\\R");
		for (String line : lines) {
			String trimmed = line.strip();
			if (trimmed.regionMatches(true, 0, "FN:", 0, 3) || trimmed.regionMatches(true, 0, "FN;", 0, 3)) {
				return trimmed.substring(trimmed.indexOf(':') + 1).strip();
			}
		}
		for (String line : lines) {
			String trimmed = line.strip();
			if (!trimmed.isEmpty() && !trimmed.equalsIgnoreCase("BEGIN:VCARD")
					&& !trimmed.regionMatches(true, 0, "VERSION:", 0, 8)) {
				return trimmed;
			}
		}
		return vcard;
	}

	private void addValue() {
		EObject anchor = form.anchor().getValue();
		String text = input.getText().trim();
		if (anchor == null || text.isEmpty()) {
			return;
		}
		if (presentation != null) {
			for (Presentation.Choice choice : presentation.choices()) {
				if (choice.label().equals(text)) {
					text = choice.value();
					break;
				}
			}
		}
		Object value;
		try {
			value = ModelCommands.fromString(contentAttribute, text);
		} catch (RuntimeException _) {
			input.setFocus();
			return;
		}
		Command command;
		if (kind == Kind.ELEMENTS) {
			EObject element = EcoreUtil.create(((EReference) feature).getEReferenceType());
			element.eSet(contentAttribute, value);
			command = ModelCommands.addElement(domain(), anchor, ownerPath, (EReference) feature, element);
		} else {
			ModelPath.Ensured ensured = ownerPath.ensure(domain(), anchor);
			if (ensured.isNew()) {
				@SuppressWarnings("unchecked")
				List<Object> values = (List<Object>) ensured.target().eGet(feature);
				values.add(value);
				command = ensured.command();
			} else {
				command = new AddCommand(domain(), ensured.target(), feature, value);
			}
		}
		if (ModelCommands.execute(domain(), command)) {
			input.setText("");
			input.setFocus();
		}
	}

	private Command removeCommand(Row row, EObject owner) {
		return kind == Kind.ELEMENTS ? ModelCommands.remove(domain(), (EObject) row.item())
				: new RemoveCommand(domain(), owner, feature, List.of(row.item()));
	}

	private void remove(Row row) {
		EObject owner = owner();
		if (owner != null) {
			ModelCommands.execute(domain(), removeCommand(row, owner));
		}
	}

	private void move(Row row, int delta) {
		EObject owner = owner();
		if (owner == null) {
			return;
		}
		int target = row.index() + delta;
		if (target < 0 || target >= ((List<?>) owner.eGet(feature)).size()) {
			return;
		}
		ModelCommands.execute(domain(), new MoveCommand(domain(), owner, feature, row.item(), target));
	}

	/** Edits a value in a small dialog; an empty value removes it. */
	private void edit(Row row) {
		EObject owner = owner();
		if (owner == null) {
			return;
		}
		String current = text(row);
		boolean multiLine = current.contains("\n");
		InputDialog dialog = new ValueDialog(composite.getShell(), current, multiLine);
		if (dialog.open() != Window.OK) {
			return;
		}
		String text = multiLine ? dialog.getValue().replace("\r\n", "\n").strip() : dialog.getValue().trim();
		if (Objects.equals(text, multiLine ? current.strip() : current)) {
			return;
		}
		Command command;
		if (text.isEmpty()) {
			command = removeCommand(row, owner);
		} else {
			Object value;
			try {
				value = ModelCommands.fromString(contentAttribute, text);
			} catch (RuntimeException _) {
				return;
			}
			command = kind == Kind.ELEMENTS ? new SetCommand(domain(), (EObject) row.item(), contentAttribute, value)
					: new SetCommand(domain(), owner, feature, value, row.index());
		}
		ModelCommands.execute(domain(), command);
	}

	/** Edits one value, validated against the value's data type. */
	private final class ValueDialog extends InputDialog {
		private final boolean multiLine;

		ValueDialog(Shell shell, String value, boolean multiLine) {
			super(shell, Messages.ValueList_EditValue, Messages.ValueList_ValueLeaveEmptyToRemove, value, text -> {
				if (text == null || text.isBlank()) {
					return null;
				}
				var status = FormSupport.validate(contentAttribute, text);
				return status.isOK() ? null : status.getMessage();
			});
			this.multiLine = multiLine;
		}

		@Override
		protected int getInputTextStyle() {
			return multiLine ? SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP : super.getInputTextStyle();
		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Control area = super.createDialogArea(parent);
			if (getText().getLayoutData() instanceof GridData data) {
				data.widthHint = multiLine ? 440 : 360;
				if (multiLine) {
					data.heightHint = getText().getLineHeight() * 10;
				}
			}
			return area;
		}
	}
}
