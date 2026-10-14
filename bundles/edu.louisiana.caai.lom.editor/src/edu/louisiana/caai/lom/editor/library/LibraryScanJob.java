/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.library;

import org.eclipse.osgi.util.NLS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.model.support.report.HlomLibrary;
import edu.louisiana.caai.lom.model.support.report.LibraryEntry;

/**
 * Finds, loads and validates HLOM records in the background. Results are delivered to the UI thread in batches
 * while the job runs, so that large libraries fill in progressively.
 */
final class LibraryScanJob extends Job {

	/** Receives results in the UI thread. */
	interface Listener {
		/** A batch of loaded records. */
		void loaded(List<LibraryEntry> entries);

		/** Files that are gone or are not HLOM records (only reported for explicit file lists). */
		void removed(List<Path> files);

		/** Progress text, e.g. "Validating 12 of 40". */
		void progress(String text);

		/** The job has ended. */
		void done(boolean canceled);
	}

	private static final long BATCH_MILLIS = 200;

	private final Object family;
	private final Path folder;
	private final List<Path> files;
	private final Display display;
	private final Listener listener;

	/**
	 * @param folder the folder to scan recursively ({@code files} == {@code null}), or the library root
	 * @param files  specific files to (re)load, or {@code null} to scan the folder
	 */
	LibraryScanJob(Object family, Path folder, List<Path> files, Display display, Listener listener) {
		super(files == null ? "Scanning metadata library" : "Validating library records");
		this.family = family;
		this.folder = folder;
		this.files = files;
		this.display = display;
		this.listener = listener;
		setUser(false);
	}

	@Override
	public boolean belongsTo(Object jobFamily) {
		return jobFamily == family;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, getName(), 100);
		boolean canceled = false;
		try {
			List<Path> candidates;
			if (files == null) {
				post(() -> listener.progress(NLS.bind(Messages.LibraryScanJob_LookingForRecordsIn, folder)));
				candidates = HlomLibrary.findCandidates(folder, monitor::isCanceled);
			} else {
				candidates = files;
			}
			progress.worked(5);
			SubMonitor loading = progress.split(95).setWorkRemaining(candidates.size());
			List<LibraryEntry> batch = new ArrayList<>();
			List<Path> removed = new ArrayList<>();
			long lastPost = System.currentTimeMillis();
			int index = 0;
			for (Path file : candidates) {
				index++;
				loading.split(1);
				loading.subTask(file.getFileName().toString());
				if (Files.isRegularFile(file) && HlomLibrary.isHlomDocument(file)) {
					int current = index;
					if (files == null && System.currentTimeMillis() - lastPost >= BATCH_MILLIS) {
						post(() -> listener.progress(NLS.bind(Messages.LibraryScanJob_ValidatingOf, current, candidates.size())));
					}
					batch.add(HlomLibrary.load(file));
				} else if (files != null) {
					removed.add(file);
				}
				if (System.currentTimeMillis() - lastPost >= BATCH_MILLIS) {
					flush(batch, removed);
					lastPost = System.currentTimeMillis();
				}
			}
			flush(batch, removed);
		} catch (OperationCanceledException _) {
			canceled = true;
		} catch (IOException e) {
			post(() -> listener.progress(NLS.bind(Messages.LibraryScanJob_TheFolderCouldNotBe, e.getMessage())));
		} finally {
			boolean wasCanceled = canceled || monitor.isCanceled();
			post(() -> listener.done(wasCanceled));
			monitor.done();
		}
		return canceled ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}

	private void flush(List<LibraryEntry> batch, List<Path> removed) {
		if (!batch.isEmpty()) {
			List<LibraryEntry> copy = List.copyOf(batch);
			batch.clear();
			post(() -> listener.loaded(copy));
		}
		if (!removed.isEmpty()) {
			List<Path> copy = List.copyOf(removed);
			removed.clear();
			post(() -> listener.removed(copy));
		}
	}

	private void post(Runnable runnable) {
		if (!display.isDisposed()) {
			display.asyncExec(runnable);
		}
	}
}
