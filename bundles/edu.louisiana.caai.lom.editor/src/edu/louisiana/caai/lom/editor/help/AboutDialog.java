/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.help;

import org.eclipse.osgi.util.NLS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

import edu.louisiana.caai.lom.editor.HlomTheme;

/** The About dialog: application mark, version and build, standards, credits, links and license notes. */
public class AboutDialog extends Dialog {

	private static final int COPY_ID = IDialogConstants.CLIENT_ID + 1;
	private static final int DETAILS_ID = IDialogConstants.CLIENT_ID + 2;
	private static final String NAME = "Hermes 2: MedBiquitous Editor";

	private final Version version;
	private final String buildDate;

	/** Creates the dialog. */
	public AboutDialog(Shell parentShell) {
		super(parentShell);
		Bundle bundle = FrameworkUtil.getBundle(AboutDialog.class);
		version = bundle != null ? bundle.getVersion() : Version.emptyVersion;
		buildDate = buildDate(version.getQualifier());
	}

	/** The build date encoded in a Tycho qualifier ({@code yyyyMMddHHmm}), or {@code null}. */
	static String buildDate(String qualifier) {
		if (qualifier == null || !qualifier.matches("\\d{12}")) {
			return null;
		}
		try {
			LocalDateTime date = LocalDateTime.parse(qualifier, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
			return date.format(DateTimeFormatter.ofPattern(Messages.AboutDialog_MMMMDYyyy, Locale.ENGLISH));
		} catch (DateTimeParseException _) {
			return null;
		}
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(NLS.bind(Messages.AboutDialog_About, NAME));
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Color surface = HlomTheme.color(HlomTheme.SURFACE);
		Composite area = new Composite(parent, SWT.NONE);
		area.setBackground(surface);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(area);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(area);

		createHeader(area);
		Canvas keyline = new Canvas(area, SWT.NO_BACKGROUND);
		keyline.addPaintListener(event -> {
			Rectangle bounds = keyline.getClientArea();
			Pattern pattern = new Pattern(event.display, 0, 0, bounds.width, 0, HlomTheme.color(HlomTheme.ACCENT),
					HlomTheme.color(HlomTheme.TEAL));
			event.gc.setBackgroundPattern(pattern);
			event.gc.fillRectangle(bounds);
			pattern.dispose();
		});
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 3).applyTo(keyline);

		Composite body = new Composite(area, SWT.NONE);
		body.setBackground(surface);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(28, 18).spacing(18, 9).applyTo(body);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(body);
		row(body, Messages.AboutDialog_Standards, """
				MedBiquitous Healthcare LOM v1.1 (ANSI/MEDBIQ LO.10.1-2008, extending IEEE LOM 1484.12.1),
				Curriculum Inventory, Competency Framework and Object, Performance Framework, Activity Report,
				Medical Education Metrics, Healthcare Professional Profile, Virtual Patient""");
		row(body, Messages.AboutDialog_DevelopedBy,
				Messages.AboutDialog_RaviTejaBhupatirajuUniversityOf);
		row(body, Messages.AboutDialog_BuiltWith, Messages.AboutDialog_EclipseRCPEclipseModelingFramework);
		row(body, Messages.AboutDialog_Runtime, NLS.bind(Messages.AboutDialog_JavaOn, new Object[] { System.getProperty("java.version"), System.getProperty("java.vendor"), System.getProperty("os.name"), System.getProperty("os.arch") }));
		label(body, Messages.AboutDialog_Links, true);
		Link links = new Link(body, SWT.NONE);
		links.setBackground(surface);
		links.setText(Messages.AboutDialog_AHrefHttpsWwwMedbiq);
		links.addListener(SWT.Selection, event -> Program.launch(event.text));

		Label separator = new Label(body, SWT.NONE);
		separator.setBackground(HlomTheme.color(HlomTheme.BORDER));
		GridDataFactory.fillDefaults().span(2, 1).hint(SWT.DEFAULT, 1).indent(0, 6).applyTo(separator);
		Label license = new Label(body, SWT.WRAP);
		license.setBackground(surface);
		license.setForeground(HlomTheme.color(HlomTheme.SLATE));
		license.setText(Messages.AboutDialog_TheMedBiquitousSchemasSpecificationsAnd);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).hint(520, SWT.DEFAULT).applyTo(license);
		return area;
	}

	private void createHeader(Composite area) {
		Composite header = new Composite(area, SWT.NONE);
		header.setBackground(HlomTheme.color(HlomTheme.SURFACE));
		GridLayoutFactory.fillDefaults().numColumns(2).margins(28, 22).spacing(20, 0).applyTo(header);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(header);

		Image mark = HlomTheme.image("icons/app/hlom128.png");
		Canvas logo = new Canvas(header, SWT.DOUBLE_BUFFERED);
		logo.setBackground(header.getBackground());
		logo.addPaintListener(event -> {
			if (mark != null) {
				GC gc = event.gc;
				gc.setAntialias(SWT.ON);
				gc.setInterpolation(SWT.HIGH);
				Rectangle size = mark.getBounds();
				Rectangle client = logo.getClientArea();
				gc.drawImage(mark, 0, 0, size.width, size.height, 0, 0, client.width, client.height);
			}
		});
		GridDataFactory.swtDefaults().hint(76, 76).span(1, 3).align(SWT.BEGINNING, SWT.CENTER).applyTo(logo);

		Label title = new Label(header, SWT.NONE);
		title.setText(NAME);
		title.setFont(HlomTheme.headingFont());
		title.setForeground(HlomTheme.color(HlomTheme.INK));
		title.setBackground(header.getBackground());
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.END).grab(true, true).applyTo(title);

		Label subtitle = new Label(header, SWT.NONE);
		subtitle.setText(Messages.AboutDialog_MetadataForHealthcareEducationMade);
		subtitle.setForeground(HlomTheme.color(HlomTheme.SLATE));
		subtitle.setBackground(header.getBackground());

		Composite versionRow = new Composite(header, SWT.NONE);
		versionRow.setBackground(header.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(12, 0).applyTo(versionRow);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(true, true).indent(0, 6)
				.applyTo(versionRow);
		Label versionLabel = new Label(versionRow, SWT.NONE);
		versionLabel.setText(NLS.bind(Messages.AboutDialog_Version, new Object[] { version.getMajor(), version.getMinor(), version.getMicro() }));
		versionLabel.setFont(HlomTheme.boldFont());
		versionLabel.setForeground(HlomTheme.color(HlomTheme.ACCENT));
		versionLabel.setBackground(header.getBackground());
		Label build = new Label(versionRow, SWT.NONE);
		build.setText(buildText());
		build.setForeground(HlomTheme.color(HlomTheme.MUTED));
		build.setBackground(header.getBackground());
	}

	private String buildText() {
		String qualifier = version.getQualifier();
		if (buildDate != null) {
			return NLS.bind(Messages.AboutDialog_Build, qualifier, buildDate);
		}
		return qualifier.isEmpty() ? "" : "Build " + qualifier;
	}

	private static void row(Composite body, String key, String value) {
		label(body, key, true);
		label(body, value, false);
	}

	private static Label label(Composite body, String text, boolean key) {
		Label label = new Label(body, SWT.NONE);
		label.setText(text);
		label.setBackground(body.getBackground());
		if (key) {
			label.setFont(HlomTheme.groupFont());
			label.setForeground(HlomTheme.color(HlomTheme.MUTED));
			GridDataFactory.swtDefaults().align(SWT.END, SWT.BEGINNING).indent(0, 1).applyTo(label);
		} else {
			label.setForeground(HlomTheme.color(HlomTheme.INK));
		}
		return label;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control bar = super.createButtonBar(parent);
		bar.setBackground(HlomTheme.color(HlomTheme.BAR));
		parent.setBackground(HlomTheme.color(HlomTheme.BAR));
		return bar;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, DETAILS_ID, Messages.AboutDialog_InstallationDetails, false);
		createButton(parent, COPY_ID, Messages.AboutDialog_CopyDetails, false);
		Button close = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
		close.setFocus();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == DETAILS_ID) {
			// The workbench's dialog: the installed features and plug-ins, the configuration and the error log.
			org.eclipse.ui.handlers.IHandlerService handlers = org.eclipse.ui.PlatformUI.getWorkbench()
					.getService(org.eclipse.ui.handlers.IHandlerService.class);
			try {
				handlers.executeCommand("org.eclipse.ui.help.installationDialog", null);
			} catch (org.eclipse.core.commands.common.CommandException e) {
				edu.louisiana.caai.lom.editor.HlomEditorPlugin.INSTANCE.log(e);
			}
			return;
		}
		if (buttonId == COPY_ID) {
			String details = NAME + " " + version + System.lineSeparator() + "Java " + System.getProperty(
					"java.version") + " (" + System.getProperty("java.vendor") + "), " + System.getProperty("os.name")
					+ " " + System.getProperty("os.version") + " " + System.getProperty("os.arch");
			Clipboard clipboard = new Clipboard(getShell().getDisplay());
			clipboard.setContents(new Object[] { details }, new Transfer[] { TextTransfer.getInstance() });
			clipboard.dispose();
			return;
		}
		super.buttonPressed(buttonId);
	}
}
