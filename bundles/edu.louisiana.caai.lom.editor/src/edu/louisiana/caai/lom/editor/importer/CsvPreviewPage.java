/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.importer;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.importer.ImportMessage;
import edu.louisiana.caai.lom.model.support.importer.ImportedRecord;

/**
 * Page 3: the generated records with their validation status, kind and credits. Records can be excluded; the
 * notes and the XML of the selected record are shown below the table.
 */
class CsvPreviewPage extends WizardPage {

	/** A line of the notes table. */
	private record Note(Severity severity, String origin, String text) {
	}

	private static final Pattern XML_TOKENS = Pattern.compile(
			"(<!--.*?-->)|(</?)([\\w:.-]+)|([\\w:.-]+)(=)(\"[^\"]*\")|(/?>)|(&[\\w#]+;)", Pattern.DOTALL);

	private final CsvImportWizard wizard;
	private ImportStats stats;
	private CheckboxTableViewer viewer;
	private TableViewer notes;
	private StyledText xml;
	private CTabFolder tabs;
	private List<ImportedRecord> shown;

	CsvPreviewPage(CsvImportWizard wizard) {
		super("csvPreview");
		this.wizard = wizard;
		setTitle(Messages.CsvPreviewPage_ReviewTheRecords);
		setDescription(Messages.CsvPreviewPage_EveryRowWasConvertedAnd);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 6).spacing(8, 8).applyTo(composite);

		Composite top = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).spacing(8, 0).applyTo(top);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(top);
		stats = new ImportStats(top);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(stats);
		button(top, Messages.CsvPreviewPage_SelectAll, () -> setAllChecked(_ -> true));
		button(top, Messages.CsvPreviewPage_SelectValid, () -> setAllChecked(record -> record.count(Severity.ERROR) == 0));
		button(top, Messages.CsvPreviewPage_SelectNone, () -> setAllChecked(_ -> false));

		SashForm sash = new SashForm(composite, SWT.VERTICAL);
		GridDataFactory.fillDefaults().grab(true, true).hint(780, 420).applyTo(sash);

		viewer = CheckboxTableViewer.newCheckList(sash, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.V_SCROLL);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		ColumnViewerToolTipSupport.enableFor(viewer);
		column(viewer, Messages.CsvPreviewPage_Row, 52, new RecordLabels() {
			@Override
			public String getText(Object element) {
				return Integer.toString(record(element).row());
			}
		});
		column(viewer, Messages.CsvPreviewPage_Status, 120, new RecordLabels() {
			@Override
			public String getText(Object element) {
				ImportedRecord record = record(element);
				int errors = record.count(Severity.ERROR);
				int warnings = record.count(Severity.WARNING);
				if (errors > 0) {
					return errors + (errors == 1 ? " error" : " errors");
				}
				if (warnings > 0) {
					return warnings + (warnings == 1 ? " warning" : " warnings");
				}
				return Messages.CsvPreviewPage_Valid;
			}

			@Override
			public Image getImage(Object element) {
				return statusImage(record(element).severity());
			}

			@Override
			public Color getForeground(Object element) {
				Severity severity = record(element).severity();
				return HlomTheme.color(severity == Severity.ERROR ? HlomTheme.ACCENT
						: severity == Severity.WARNING ? HlomTheme.AMBER : HlomTheme.TEAL);
			}
		});
		column(viewer, Messages.CsvPreviewPage_Title, 280, new RecordLabels() {
			@Override
			public String getText(Object element) {
				String title = record(element).title();
				return title.isEmpty() ? "(no title)" : title;
			}
		});
		column(viewer, Messages.CsvPreviewPage_Kind, 150, new RecordLabels() {
			@Override
			public String getText(Object element) {
				return record(element).kind();
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(HlomTheme.SLATE);
			}
		});
		column(viewer, Messages.CsvPreviewPage_Credits, 170, new RecordLabels() {
			@Override
			public String getText(Object element) {
				return record(element).credits();
			}
		});
		viewer.addCheckStateListener(event -> {
			record(event.getElement()).setIncluded(event.getChecked());
			update();
		});
		viewer.addSelectionChangedListener(_ -> showDetails());

		tabs = new CTabFolder(sash, SWT.BORDER | SWT.FLAT);
		HlomTheme.decorate(tabs);
		notes = new TableViewer(tabs, SWT.FULL_SELECTION | SWT.V_SCROLL);
		notes.setContentProvider(ArrayContentProvider.getInstance());
		notes.getTable().setHeaderVisible(true);
		ColumnViewerToolTipSupport.enableFor(notes);
		column(notes, Messages.CsvPreviewPage_Note, 560, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Note) element).text();
			}

			@Override
			public Image getImage(Object element) {
				Severity severity = ((Note) element).severity();
				return severity == Severity.INFO ? HlomTheme.image("icons/actions/info.png") : statusImage(severity);
			}

			@Override
			public String getToolTipText(Object element) {
				return ((Note) element).text();
			}
		});
		column(notes, Messages.CsvPreviewPage_From, 170, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Note) element).origin();
			}

			@Override
			public Color getForeground(Object element) {
				return HlomTheme.color(HlomTheme.SLATE);
			}
		});
		CTabItem notesTab = new CTabItem(tabs, SWT.NONE);
		notesTab.setText(Messages.CsvPreviewPage_Notes);
		notesTab.setImage(HlomTheme.image("icons/actions/info.png"));
		notesTab.setControl(notes.getControl());

		xml = new StyledText(tabs, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		xml.setFont(JFaceResources.getTextFont());
		xml.setMargins(8, 6, 8, 6);
		xml.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		CTabItem xmlTab = new CTabItem(tabs, SWT.NONE);
		xmlTab.setText("XML");
		xmlTab.setImage(HlomTheme.image("icons/pages/xml.png"));
		xmlTab.setControl(xml);
		tabs.setSelection(notesTab);
		tabs.addListener(SWT.Selection, _ -> showDetails());
		sash.setWeights(50, 50);

		setControl(composite);
	}

	private static void button(Composite parent, String text, Runnable action) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		button.addListener(SWT.Selection, _ -> action.run());
		GridDataFactory.fillDefaults().align(SWT.END, SWT.END).applyTo(button);
	}

	private static void column(TableViewer tableViewer, String title, int width, ColumnLabelProvider labels) {
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		column.setLabelProvider(labels);
	}

	private static ImportedRecord record(Object element) {
		return (ImportedRecord) element;
	}

	static Image statusImage(Severity severity) {
		if (severity == null) {
			return HlomTheme.image("icons/actions/ok.png");
		}
		return HlomTheme.image(switch (severity) {
			case ERROR -> "icons/actions/error.png";
			case WARNING -> "icons/actions/warning.png";
			case INFO -> "icons/actions/ok.png";
		});
	}

	/** Shows the record's notes as tooltip; excluded records are greyed out. */
	private class RecordLabels extends ColumnLabelProvider {
		@Override
		public String getToolTipText(Object element) {
			List<String> lines = record(element).describeIssues();
			if (lines.isEmpty()) {
				return Messages.CsvPreviewPage_ValidHealthcareLOMRecord;
			}
			List<String> shownLines = lines.size() > 14 ? new ArrayList<>(lines.subList(0, 14)) : lines;
			String text = String.join("\n", shownLines);
			return lines.size() > 14 ? text + "\n… " + (lines.size() - 14) + " more" : text;
		}

		@Override
		public Color getForeground(Object element) {
			return HlomTheme.color(record(element).isIncluded() ? HlomTheme.INK : HlomTheme.MUTED);
		}
	}

	private interface Check {
		boolean test(ImportedRecord record);
	}

	private void setAllChecked(Check check) {
		for (ImportedRecord record : wizard.records()) {
			record.setIncluded(check.test(record));
			viewer.setChecked(record, record.isIncluded());
		}
		update();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			getShell().getDisplay().asyncExec(() -> {
				if (getControl().isDisposed()) {
					return;
				}
				wizard.ensurePreview();
				if (shown != wizard.records()) {
					shown = wizard.records();
					viewer.setInput(shown);
					for (ImportedRecord record : shown) {
						viewer.setChecked(record, record.isIncluded());
					}
					if (!shown.isEmpty()) {
						viewer.setSelection(new org.eclipse.jface.viewers.StructuredSelection(shown.getFirst()), true);
					}
				}
				update();
				viewer.getTable().setFocus();
			});
		}
	}

	private void update() {
		viewer.refresh();
		List<ImportedRecord> records = wizard.records();
		int included = (int) records.stream().filter(ImportedRecord::isIncluded).count();
		int valid = (int) records.stream().filter(r -> r.count(Severity.ERROR) == 0 && r.count(Severity.WARNING) == 0)
				.count();
		int warnings = (int) records.stream()
				.filter(r -> r.count(Severity.ERROR) == 0 && r.count(Severity.WARNING) > 0).count();
		int errors = (int) records.stream().filter(r -> r.count(Severity.ERROR) > 0).count();
		stats.setFigures(List.of(new ImportStats.Figure(Integer.toString(records.size()), "records", HlomTheme.INK),
				new ImportStats.Figure(Integer.toString(valid), "valid", valid > 0 ? HlomTheme.TEAL : HlomTheme.MUTED),
				new ImportStats.Figure(Integer.toString(warnings), Messages.CsvPreviewPage_WithWarnings,
						warnings > 0 ? HlomTheme.AMBER : HlomTheme.MUTED),
				new ImportStats.Figure(Integer.toString(errors), Messages.CsvPreviewPage_WithErrors,
						errors > 0 ? HlomTheme.ACCENT : HlomTheme.MUTED),
				new ImportStats.Figure(Integer.toString(included), Messages.CsvPreviewPage_ToImport, HlomTheme.INK)));
		if (included == 0) {
			setMessage(null);
			setErrorMessage(records.isEmpty() ? "No records were generated." : "Select at least one record.");
		} else {
			setErrorMessage(null);
			long includedErrors = records.stream().filter(ImportedRecord::isIncluded)
					.filter(r -> r.count(Severity.ERROR) > 0).count();
			if (includedErrors > 0) {
				setMessage(includedErrors + (includedErrors == 1 ? " record has" : " records have")
						+ " errors; they can be imported and fixed in the editor.", IMessageProvider.WARNING);
			} else {
				setMessage(Messages.CsvPreviewPage_HoverOverARecordFor,
						IMessageProvider.INFORMATION);
			}
		}
		setPageComplete(included > 0);
	}

	private void showDetails() {
		ImportedRecord record = (ImportedRecord) viewer.getStructuredSelection().getFirstElement();
		if (record == null) {
			notes.setInput(List.of());
			xml.setText("");
			return;
		}
		List<Note> lines = new ArrayList<>();
		for (Severity severity : Severity.values()) {
			for (HlomProblem problem : record.problems()) {
				if (problem.severity() == severity) {
					lines.add(new Note(severity, problem.origin().label(), problem.message()));
				}
			}
			for (ImportMessage message : record.messages()) {
				if (message.severity() == severity) {
					lines.add(new Note(severity, message.column() == null ? "Import" : "Column " + message.column(),
							message.message()));
				}
			}
		}
		if (lines.isEmpty()) {
			lines.add(new Note(null, "", Messages.CsvPreviewPage_NoNotesEveryValueWas));
		}
		notes.setInput(lines);
		tabs.getItem(0).setText(NLS.bind(Messages.CsvPreviewPage_Notes2, (lines.getFirst().severity() == null ? 0 : lines.size())));
		if (tabs.getSelectionIndex() == 1) {
			showXml(record);
		}
	}

	private void showXml(ImportedRecord record) {
		Resource resource = HlomResources.createResourceSet().createResource(URI.createURI("memory:/preview.xml"));
		resource.getContents().add(record.root());
		String text;
		try {
			text = HlomResources.toXml(resource);
		} catch (IOException e) {
			text = NLS.bind(Messages.CsvPreviewPage_TheRecordCouldNotBe, e.getMessage());
		} finally {
			resource.getContents().clear();
		}
		xml.setText(text);
		highlight(text);
	}

	private void highlight(String text) {
		List<StyleRange> ranges = new ArrayList<>();
		Matcher matcher = XML_TOKENS.matcher(text);
		while (matcher.find()) {
			if (matcher.group(1) != null) {
				ranges.add(range(matcher.start(1), matcher.end(1), HlomTheme.MUTED, SWT.ITALIC));
			} else if (matcher.group(3) != null) {
				ranges.add(range(matcher.start(2), matcher.end(2), HlomTheme.SLATE, SWT.NORMAL));
				boolean healthcare = matcher.group(3).startsWith("hx:");
				ranges.add(range(matcher.start(3), matcher.end(3), healthcare ? HlomTheme.TEAL : HlomTheme.ACCENT,
						SWT.NORMAL));
			} else if (matcher.group(4) != null) {
				ranges.add(range(matcher.start(4), matcher.end(4), HlomTheme.SLATE, SWT.NORMAL));
				ranges.add(range(matcher.start(6), matcher.end(6), HlomTheme.TEAL, SWT.NORMAL));
			} else if (matcher.group(7) != null) {
				ranges.add(range(matcher.start(7), matcher.end(7), HlomTheme.SLATE, SWT.NORMAL));
			} else if (matcher.group(8) != null) {
				ranges.add(range(matcher.start(8), matcher.end(8), HlomTheme.AMBER, SWT.NORMAL));
			}
		}
		xml.setStyleRanges(ranges.toArray(new StyleRange[0]));
	}

	private static StyleRange range(int start, int end, RGB color, int style) {
		StyleRange range = new StyleRange(start, end - start, HlomTheme.color(color), null);
		range.fontStyle = style;
		return range;
	}
}
