/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.model.support.ElementPath;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.check.DocumentCheck.Located;
import edu.louisiana.caai.lom.model.support.search.FieldLabels;
import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.medbiq.model.Paths;

/**
 * The page of Hermes 2 Web: a document's elements as a tree, the fields of the selected element as a form, and the
 * problems of the document, checked after every change. Documents are opened from the computer (XML or JSON),
 * created from a type, and downloaded as XML (or JSON for Healthcare LOM).
 */
public final class HermesWebEntryPoint extends AbstractEntryPoint {

	private static final ExtendedMetaData META = ExtendedMetaData.INSTANCE;

	private WebDocument document;
	private Label title;
	private Label status;
	private Combo types;
	private Button downloadJson;
	private TreeViewer tree;
	private ScrolledComposite formScroll;
	private Composite form;
	private TableViewer problems;
	private final List<DocumentType> typeList = new ArrayList<>(DocumentTypes.all());

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		createToolbar(parent);
		SashForm vertical = new SashForm(parent, SWT.VERTICAL);
		vertical.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		SashForm horizontal = new SashForm(vertical, SWT.HORIZONTAL);
		createTree(horizontal);
		createForm(horizontal);
		horizontal.setWeights(35, 65);
		createProblems(vertical);
		vertical.setWeights(75, 25);
		setDocument(WebDocument.create(typeList.getFirst()));
	}

	// ----------------------------------------------------------------------------------------------------
	// Tool bar: new, open, download
	// ----------------------------------------------------------------------------------------------------

	private void createToolbar(Composite parent) {
		Composite bar = new Composite(parent, SWT.NONE);
		bar.setLayout(new GridLayout(7, false));
		bar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		types = new Combo(bar, SWT.READ_ONLY);
		for (DocumentType type : typeList) {
			types.add(type.label());
		}
		types.select(0);
		button(bar, "New", () -> setDocument(WebDocument.create(typeList.get(Math.max(0, types.getSelectionIndex())))));
		button(bar, "Open…", this::open);
		button(bar, "Download XML", () -> download(false));
		downloadJson = button(bar, "Download JSON", () -> download(true));
		title = new Label(bar, SWT.NONE);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		status = new Label(parent, SWT.WRAP);
		status.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}

	private static Button button(Composite parent, String text, Runnable action) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		button.addListener(SWT.Selection, _ -> action.run());
		return button;
	}

	private void open() {
		FileDialog dialog = new FileDialog(getShell(), SWT.TITLE | SWT.SINGLE);
		dialog.setText("Open a MedBiquitous Document");
		dialog.open(returnCode -> {
			String[] files = dialog.getFileNames();
			if (returnCode != SWT.OK || files == null || files.length == 0) {
				return;
			}
			Path file = Path.of(files[0]);
			try {
				String text = Files.readString(file, StandardCharsets.UTF_8);
				if (text.startsWith("﻿")) {
					text = text.substring(1);
				}
				setDocument(WebDocument.load(file.getFileName().toString(), text));
				status.setText("");
			} catch (IOException | RuntimeException e) {
				status.setText(e.getMessage());
			} finally {
				try {
					Files.deleteIfExists(file);
				} catch (IOException _) {
					// A temporary file of the upload.
				}
			}
			status.getParent().layout(true);
		});
	}

	private void download(boolean json) {
		try {
			String base = document.name().replaceAll("\\.[^.]*$", "");
			if (json) {
				DownloadHandler.offer(base + ".json", "application/json", document.json());
			} else {
				DownloadHandler.offer(base + ".xml", "application/xml", document.xml());
			}
		} catch (IOException e) {
			status.setText("The document could not be written: " + e.getMessage());
		}
	}

	private void setDocument(WebDocument next) {
		document = next;
		title.setText(next.name() + " – " + next.type().label());
		downloadJson.setEnabled(next.type().supportsJson());
		tree.setInput(new Object[] { next.rootElement() });
		tree.expandToLevel(2);
		tree.setSelection(new StructuredSelection(next.rootElement()), true);
		validate();
	}

	// ----------------------------------------------------------------------------------------------------
	// Tree of the elements
	// ----------------------------------------------------------------------------------------------------

	private void createTree(Composite parent) {
		tree = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE);
		tree.setContentProvider(new ITreeContentProvider() {
			@Override
			public Object[] getElements(Object input) {
				return (Object[]) input;
			}

			@Override
			public Object[] getChildren(Object element) {
				return ((EObject) element).eContents().toArray();
			}

			@Override
			public Object getParent(Object element) {
				return ((EObject) element).eContainer();
			}

			@Override
			public boolean hasChildren(Object element) {
				return !((EObject) element).eContents().isEmpty();
			}

			@Override
			public void inputChanged(org.eclipse.jface.viewers.Viewer viewer, Object oldInput, Object newInput) {
				// Stateless.
			}

			@Override
			public void dispose() {
				// Nothing to release.
			}
		});
		tree.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return label((EObject) element);
			}
		});
		tree.addSelectionChangedListener(event -> {
			if (event.getStructuredSelection().getFirstElement() instanceof EObject element) {
				showForm(element);
			}
		});
	}

	/** The element name with what identifies it: an id, a title, a label or its text. */
	static String label(EObject element) {
		String name = element.eContainmentFeature() == null ? element.eClass().getName()
				: META.getName(element.eContainmentFeature());
		String detail = null;
		for (String path : List.of("@id", "Title", "title/string", "Label", "Name", "string", "entry", "value")) {
			detail = Paths.text(element, path);
			if (detail != null) {
				break;
			}
		}
		if (detail == null) {
			detail = Paths.content(element);
		}
		if (detail == null || detail.isBlank()) {
			return name;
		}
		detail = detail.strip().replaceAll("\\s+", " ");
		return name + " – " + (detail.length() > 50 ? detail.substring(0, 48) + "…" : detail);
	}

	// ----------------------------------------------------------------------------------------------------
	// Form of the selected element
	// ----------------------------------------------------------------------------------------------------

	private void createForm(Composite parent) {
		formScroll = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		formScroll.setExpandHorizontal(true);
		formScroll.setExpandVertical(true);
		form = new Composite(formScroll, SWT.NONE);
		formScroll.setContent(form);
	}

	private void showForm(EObject element) {
		for (Control child : form.getChildren()) {
			child.dispose();
		}
		form.setLayout(new GridLayout(2, false));
		Label heading = new Label(form, SWT.NONE);
		heading.setText(FieldLabels.words(element.eContainmentFeature() == null ? element.eClass().getName()
				: META.getName(element.eContainmentFeature())));
		heading.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		for (EStructuralFeature feature : element.eClass().getEAllStructuralFeatures()) {
			int kind = META.getFeatureKind(feature);
			if (feature instanceof EAttribute attribute && !FeatureMapUtil.isFeatureMap(attribute)
					&& (kind == ExtendedMetaData.ATTRIBUTE_FEATURE || kind == ExtendedMetaData.ELEMENT_FEATURE
							|| kind == ExtendedMetaData.SIMPLE_FEATURE)) {
				field(element, attribute, kind);
			}
		}
		Composite actions = new Composite(form, SWT.NONE);
		actions.setLayout(new GridLayout(4, false));
		actions.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		for (EStructuralFeature feature : element.eClass().getEAllStructuralFeatures()) {
			if (feature instanceof EReference reference && reference.isContainment()
					&& META.getFeatureKind(reference) == ExtendedMetaData.ELEMENT_FEATURE
					&& !reference.getEReferenceType().isAbstract() && (reference.isMany() || !element.eIsSet(reference))) {
				button(actions, "Add " + FieldLabels.words(META.getName(reference)), () -> addChild(element, reference));
			}
		}
		if (element.eContainer() != null && element != document.rootElement()) {
			button(actions, "Remove this element", () -> {
				EObject parent = element.eContainer();
				EcoreUtil.remove(element);
				changed(parent);
			});
		}
		form.layout(true);
		formScroll.setMinSize(form.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void field(EObject element, EAttribute attribute, int kind) {
		Label label = new Label(form, SWT.NONE);
		String name = kind == ExtendedMetaData.SIMPLE_FEATURE ? "Value" : META.getName(attribute);
		label.setText(FieldLabels.words(name) + (kind == ExtendedMetaData.ATTRIBUTE_FEATURE ? " (attribute)" : "") + ":");
		EDataType type = attribute.getEAttributeType();
		List<String> literals = literals(type);
		if (!literals.isEmpty() && !attribute.isMany()) {
			Combo combo = new Combo(form, SWT.READ_ONLY);
			combo.add("");
			literals.forEach(combo::add);
			Object value = element.eGet(attribute);
			combo.setText(value == null ? "" : text(attribute, value));
			combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			combo.addListener(SWT.Selection, _ -> apply(element, attribute, combo.getText(), label));
			return;
		}
		Text text = new Text(form, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Object value = element.eGet(attribute);
		if (attribute.isMany()) {
			List<String> values = new ArrayList<>();
			for (Object item : (List<?>) value) {
				values.add(text(attribute, item));
			}
			text.setText(String.join("; ", values));
			text.setMessage("values separated by ;");
		} else {
			text.setText(value == null || !element.eIsSet(attribute) ? "" : text(attribute, value));
		}
		text.addListener(SWT.DefaultSelection, _ -> apply(element, attribute, text.getText(), label));
		text.addListener(SWT.FocusOut, _ -> apply(element, attribute, text.getText(), label));
	}

	private static List<String> literals(EDataType type) {
		if (type instanceof EEnum eEnum) {
			return eEnum.getELiterals().stream().map(EEnumLiteral::getLiteral).toList();
		}
		List<String> facet = META.getEnumerationFacet(type);
		if (facet.isEmpty() && META.getBaseType(type) instanceof EEnum base) {
			return base.getELiterals().stream().map(EEnumLiteral::getLiteral).toList();
		}
		return facet;
	}

	private static String text(EAttribute attribute, Object value) {
		return value instanceof Enumerator literal ? literal.getLiteral()
				: value instanceof String s ? s : EcoreUtil.convertToString(attribute.getEAttributeType(), value);
	}

	/** Sets a value from its text (several values separated by ";"); an empty text removes it. */
	private void apply(EObject element, EAttribute attribute, String text, Label label) {
		Object current = element.eGet(attribute);
		try {
			if (attribute.isMany()) {
				List<Object> values = new ArrayList<>();
				for (String part : text.split(";")) {
					if (!part.isBlank()) {
						values.add(convert(attribute, part.strip()));
					}
				}
				if (values.equals(current)) {
					return;
				}
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) element.eGet(attribute);
				list.clear();
				list.addAll(values);
			} else if (text.isBlank()) {
				if (!element.eIsSet(attribute)) {
					return;
				}
				element.eUnset(attribute);
			} else {
				Object value = convert(attribute, text.strip());
				if (value.equals(current) && element.eIsSet(attribute)) {
					return;
				}
				element.eSet(attribute, value);
			}
			label.setForeground(null);
			changed(element);
		} catch (RuntimeException e) {
			label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			status.setText("\"" + text + "\" is not a valid " + META.getName(attribute) + ".");
			status.getParent().layout(true);
		}
	}

	private static Object convert(EAttribute attribute, String text) {
		return attribute.getEAttributeType().getInstanceClass() == String.class ? text
				: EcoreUtil.createFromString(attribute.getEAttributeType(), text);
	}

	private void addChild(EObject element, EReference reference) {
		EClass type = reference.getEReferenceType();
		EObject child = EcoreUtil.create(type);
		if (reference.isMany()) {
			@SuppressWarnings("unchecked")
			List<EObject> list = (List<EObject>) element.eGet(reference);
			list.add(child);
		} else {
			element.eSet(reference, child);
		}
		changed(element);
		tree.setSelection(new StructuredSelection(child), true);
	}

	/** Shows the change (tree and form) and checks the document again. */
	private void changed(EObject element) {
		tree.refresh();
		if (tree.getStructuredSelection().getFirstElement() instanceof EObject selected && selected.eResource() != null) {
			showForm(selected);
		} else {
			tree.setSelection(new StructuredSelection(element), true);
		}
		status.setText("");
		validate();
	}

	// ----------------------------------------------------------------------------------------------------
	// Problems
	// ----------------------------------------------------------------------------------------------------

	private void createProblems(Composite parent) {
		problems = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		problems.getTable().setHeaderVisible(true);
		problems.setContentProvider(ArrayContentProvider.getInstance());
		column("Severity", 90, located -> located.problem().severity().name().toLowerCase());
		column("Problem", 640, located -> located.problem().message());
		column("Element", 300, located -> located.path() == null ? "" : located.path());
		problems.addDoubleClickListener(event -> {
			if (event.getSelection() instanceof IStructuredSelection selection
					&& selection.getFirstElement() instanceof Located located && located.path() != null) {
				EObject element = ElementPath.parse(located.path()).resolve(document.documentRoot());
				if (element != null) {
					tree.setSelection(new StructuredSelection(element), true);
				}
			}
		});
	}

	private void column(String title, int width, java.util.function.Function<Located, String> text) {
		TableViewerColumn column = new TableViewerColumn(problems, SWT.LEFT);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return text.apply((Located) element);
			}
		});
	}

	private void validate() {
		List<Located> found = document.check();
		problems.setInput(found);
		long errors = found.stream().filter(l -> l.problem().severity() == HlomProblem.Severity.ERROR).count();
		title.setText(document.name() + " – " + document.type().label() + " – "
				+ (found.isEmpty() ? "valid" : errors + " errors, " + (found.size() - errors) + " warnings"));
		title.getParent().layout(true);
	}
}
