/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.start;

import org.eclipse.osgi.util.NLS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import edu.louisiana.caai.lom.editor.Errors;
import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomEditorPlugin;
import edu.louisiana.caai.lom.editor.wizards.NewHlomDocumentWizard;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType;
import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * The actions offered by the start page and the Open Recent menu.
 */
public final class StartActions {

	/** A sample document shipped in the bundle's {@code samples/} folder. */
	public record Sample(String fileName, String title, String description) {
	}

	/** The bundled samples (adapted from the HLOM specification, plus fictional demonstration records). */
	public static final List<Sample> SAMPLES = List.of(
			new Sample("insulin-pump-therapy-course.xml", "Insulin Pump Therapy",
					"CME course with credits, target audience and disclosures"),
			new Sample("course-with-multiple-credit-types.xml", "Arsenic Toxicity",
					"Course offering several credit types"),
			new Sample("medical-image-asset.xml", "Dialysis Fluid Drainage",
					"Annotated medical image asset"),
			new Sample("chest-xray-rll-pneumonia.xml", "Chest Radiograph: Pneumonia",
					"Radiograph teaching image with clinical history"),
			new Sample("sepsis-early-recognition.xml", "Sepsis Early Recognition",
					"Nursing continuing education module"),
			new Sample("pocus-workshop-lafayette.xml", "Point-of-Care Ultrasound Workshop",
					"Live hands-on workshop with location and schedule"));

	/** Folder the samples are copied to so that they can be edited and saved. */
	public static final String SAMPLES_FOLDER = "MedBiquitous Samples";

	private StartActions() {
	}

	/** Opens the new-document wizard, optionally with a template preselected (may be {@code null}). */
	public static void newDocument(IWorkbenchWindow window, String templateId) {
		NewHlomDocumentWizard.open(window, templateId);
	}

	/** Opens the new-document wizard with a standard other than Healthcare LOM preselected. */
	public static void newDocument(IWorkbenchWindow window, MedbiqDocumentType type) {
		NewHlomDocumentWizard.open(window, type);
	}

	/**
	 * A sample of another MedBiquitous standard (in the standards bundle, {@code samples/<folder>/<file>}).
	 *
	 * @param standard the standard (menu group)
	 * @param title    the title shown in the menu
	 * @param path     the path in the standards bundle
	 */
	public record StandardSample(String standard, String title, String path) {
	}

	/** The samples of the other MedBiquitous standards, by standard and file name. */
	public static List<StandardSample> standardSamples() {
		Bundle bundle = FrameworkUtil.getBundle(MedbiqStandards.class);
		if (bundle == null) {
			return List.of();
		}
		java.util.Enumeration<URL> entries = bundle.findEntries("samples", "*.xml", true);
		List<StandardSample> samples = new java.util.ArrayList<>();
		for (URL entry : entries == null ? List.<URL>of() : java.util.Collections.list(entries)) {
			String path = entry.getPath().replaceFirst("^/", "");
			String text;
			try (InputStream in = entry.openStream()) {
				text = new String(in.readNBytes(4096), java.nio.charset.StandardCharsets.UTF_8);
			} catch (IOException _) {
				continue;
			}
			var type = edu.louisiana.caai.lom.model.support.type.DocumentTypes.detect(text);
			if (type == null) {
				continue;
			}
			String name = path.substring(path.lastIndexOf('/') + 1).replaceFirst("\\.xml$", "").replace('-', ' ');
			samples.add(new StandardSample(type.standard(), Character.toUpperCase(name.charAt(0)) + name.substring(1),
					path));
		}
		samples.sort(java.util.Comparator.comparing(StandardSample::standard).thenComparing(StandardSample::title));
		return samples;
	}

	/** Copies a sample of another standard to the samples folder (once) and opens it. */
	public static void openStandardSample(IWorkbenchWindow window, StandardSample sample) {
		try {
			File target = new File(new File(samplesFolder(), sample.standard()),
					sample.path().substring(sample.path().lastIndexOf('/') + 1));
			if (!target.isFile()) {
				URL entry = FrameworkUtil.getBundle(MedbiqStandards.class).getEntry(sample.path());
				if (entry == null) {
					throw new IOException(NLS.bind(Messages.StartActions_TheSampleIsMissingFrom, sample.path()));
				}
				Files.createDirectories(target.getParentFile().toPath());
				try (InputStream in = entry.openStream()) {
					Files.copy(in, target.toPath());
				}
			}
			HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(target.getAbsolutePath()));
		} catch (IOException | RuntimeException exception) {
			Errors.show(window.getShell(), Messages.StartActions_OpenSample,
					NLS.bind(Messages.StartActions_TheSampleCouldNotBe, sample.title()), exception);
		}
	}

	/** Asks for an existing document and opens it. */
	public static void open(IWorkbenchWindow window) {
		String[] paths = HlomEditorAdvisor.openFilePathDialog(window.getShell(), SWT.OPEN, null);
		if (paths.length > 0) {
			HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(paths[0]));
		}
	}

	/** Opens a remembered document; offers to forget it if it no longer exists. */
	public static void openRecent(IWorkbenchWindow window, String path) {
		File file = new File(path);
		if (!file.isFile()) {
			if (MessageDialog.openQuestion(window.getShell(), Messages.StartActions_OpenRecent,
					NLS.bind(Messages.StartActions_TheDocumentNoLongerExists, path))) {
				RecentDocuments.remove(path);
			}
			return;
		}
		HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(file.getAbsolutePath()));
	}

	/**
	 * Copies a bundled sample to the user's samples folder (unless a copy already exists, which may contain the
	 * user's edits) and opens the copy.
	 */
	public static void openSample(IWorkbenchWindow window, Sample sample) {
		try {
			File file = copySample(sample);
			HlomEditorAdvisor.openEditor(window.getWorkbench(), URI.createFileURI(file.getAbsolutePath()));
		} catch (IOException | RuntimeException exception) {
			Errors.show(window.getShell(), Messages.StartActions_OpenSample,
					NLS.bind(Messages.StartActions_TheSampleCouldNotBe, sample.title()), exception);
		}
	}

	/** The folder the samples are copied to: {@code ~/Documents/MedBiquitous Samples} (or below the home folder). */
	public static File samplesFolder() {
		File home = new File(System.getProperty("user.home"));
		File documents = new File(home, Messages.StartActions_Documents);
		return new File(documents.isDirectory() ? documents : home, SAMPLES_FOLDER);
	}

	static File copySample(Sample sample) throws IOException {
		File folder = samplesFolder();
		File target = new File(folder, sample.fileName());
		if (target.isFile()) {
			return target;
		}
		Bundle bundle = FrameworkUtil.getBundle(StartActions.class);
		URL entry = bundle != null ? bundle.getEntry("samples/" + sample.fileName())
				: StartActions.class.getResource("/samples/" + sample.fileName());
		if (entry == null) {
			throw new IOException(NLS.bind(Messages.StartActions_TheSampleIsMissingFrom, sample.fileName()));
		}
		Files.createDirectories(folder.toPath());
		try (InputStream in = entry.openStream()) {
			Files.copy(in, target.toPath());
		}
		return target;
	}
}
