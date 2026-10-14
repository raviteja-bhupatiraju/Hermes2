/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import edu.louisiana.caai.lom.editor.HlomEditorAdvisor;
import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.pages.AbstractLomPage;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomResources;

/**
 * Base class of the UI integration tests. They run in the UI thread of the real application (UI harness), so
 * widgets are driven programmatically and the event loop is pumped explicitly; no real input is needed, which
 * makes them work on a locked session.
 * <p>
 * Every test fails if the platform log receives an error, and modal dialogs are closed automatically (and
 * recorded) so that an unexpected dialog can't block the build.
 */
abstract class AbstractUiTest {

	/** Default timeout of {@link #waitUntil}. */
	static final long TIMEOUT = 10_000;

	@TempDir
	Path tempDir;

	/** Errors written to the platform log during the test. */
	final List<IStatus> loggedErrors = new ArrayList<>();

	private final ILogListener logListener = (status, plugin) -> {
		if (status.getSeverity() == IStatus.ERROR) {
			loggedErrors.add(status);
		}
	};

	final DialogWatcher dialogs = new DialogWatcher();

	/** Model notifications recorded by {@link #recordModelChanges}. */
	final List<String> modelChanges = new ArrayList<>();

	@BeforeEach
	void setUpUi() {
		Platform.addLogListener(logListener);
		dialogs.start();
		dispatch(50);
	}

	@AfterEach
	void tearDownUi() {
		try {
			IWorkbenchPage page = page();
			if (page != null) {
				page.closeAllEditors(false);
			}
			dispatch(200);
		} finally {
			dialogs.stop();
			Platform.removeLogListener(logListener);
		}
		assertNoLoggedErrors();
	}

	// ----------------------------------------------------------------------------------------------------
	// Event loop
	// ----------------------------------------------------------------------------------------------------

	static Display display() {
		return PlatformUI.getWorkbench().getDisplay();
	}

	/** Runs the event loop for the given time. */
	static void dispatch(long millis) {
		Display display = display();
		long end = System.currentTimeMillis() + millis;
		while (System.currentTimeMillis() < end) {
			if (!display.readAndDispatch()) {
				sleep(5);
			}
		}
		// Drain pending events (bounded: a listener that keeps rescheduling itself must not hang the test).
		long drainEnd = System.currentTimeMillis() + 500;
		while (display.readAndDispatch() && System.currentTimeMillis() < drainEnd) {
			// Keep going.
		}
	}

	/** Runs the event loop until the condition holds; fails after {@code timeout} milliseconds. */
	static void waitUntil(String description, BooleanSupplier condition, long timeout) {
		Display display = display();
		long end = System.currentTimeMillis() + timeout;
		while (!condition.getAsBoolean()) {
			if (System.currentTimeMillis() > end) {
				fail("Timed out after " + timeout + " ms waiting for: " + description + "\n" + jobs());
			}
			if (!display.readAndDispatch()) {
				sleep(5);
			}
		}
	}

	/** The background jobs and whether the workspace builds automatically (to explain a timeout). */
	private static String jobs() {
		StringBuilder text = new StringBuilder("Auto-build: ").append(org.eclipse.core.resources.ResourcesPlugin
				.getWorkspace().isAutoBuilding()).append("; jobs:");
		for (org.eclipse.core.runtime.jobs.Job job : org.eclipse.core.runtime.jobs.Job.getJobManager().find(null)) {
			text.append("\n  ").append(job.getName()).append(" [state ").append(job.getState()).append(", rule ")
					.append(job.getRule()).append(", thread ").append(job.getThread()).append("]");
		}
		for (org.eclipse.core.resources.IProject project : org.eclipse.core.resources.ResourcesPlugin.getWorkspace()
				.getRoot().getProjects(org.eclipse.core.resources.IContainer.INCLUDE_HIDDEN)) {
			text.append("\nProject ").append(project.getName()).append(" open ").append(project.isOpen());
			try {
				if (project.isOpen()) {
					text.append(" natures ").append(List.of(project.getDescription().getNatureIds()))
							.append(" builders ").append(Arrays.stream(project.getDescription().getBuildSpec())
									.map(org.eclipse.core.resources.ICommand::getBuilderName).toList());
				}
			} catch (org.eclipse.core.runtime.CoreException e) {
				text.append(" ").append(e);
			}
		}
		return text.toString();
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Workbench and editor
	// ----------------------------------------------------------------------------------------------------

	static IWorkbenchWindow window() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null && workbench.getWorkbenchWindowCount() > 0) {
			window = workbench.getWorkbenchWindows()[0];
		}
		return window;
	}

	static IWorkbenchPage page() {
		IWorkbenchWindow window = window();
		return window == null ? null : window.getActivePage();
	}

	/** Copies a bundle resource (e.g. {@code resources/samples/x.xml}) to the test's temporary folder. */
	Path copyResource(String path) {
		Bundle bundle = FrameworkUtil.getBundle(AbstractUiTest.class);
		assertNotNull(bundle, "tests must run inside OSGi");
		URL entry = bundle.getEntry(path);
		assertNotNull(entry, "missing test resource " + path);
		Path target = tempDir.resolve(Path.of(path).getFileName().toString());
		try (InputStream in = entry.openStream()) {
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return target;
	}

	/** Saves a document to a file in the temporary folder. */
	Path save(DocumentRoot root, String name) throws IOException {
		Path file = tempDir.resolve(name);
		var resource = HlomResources.createResourceSet().createResource(URI.createFileURI(file.toString()));
		resource.getContents().add(root);
		resource.save(HlomResources.saveOptions());
		return file;
	}

	/** Opens a file like File > Open does and waits until the editor has shown its first page. */
	static HlomMetadataEditor open(Path file) {
		assertNotNull(window(), "no workbench window");
		assertNotNull(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				"no active workbench window (HlomEditorAdvisor.openEditor needs one)");
		assertTrue(HlomEditorAdvisor.openEditor(PlatformUI.getWorkbench(), URI.createFileURI(file.toString())),
				"openEditor failed for " + file);
		IEditorPart part = page().getActiveEditor();
		HlomMetadataEditor editor = assertInstanceOf(HlomMetadataEditor.class, part);
		waitUntil("editor pages ready", () -> Boolean.TRUE.equals(field(editor, "pagesReady")), TIMEOUT);
		dispatch(100);
		return editor;
	}

	/**
	 * Records every non-touch notification of the editor's resource (with the application frames of the stack
	 * that caused it) in {@link #modelChanges}, to find out who changes a document that should stay unchanged.
	 */
	void recordModelChanges(HlomMetadataEditor editor) {
		Resource resource = editor.getEditingDomain().getResourceSet().getResources().get(0);
		resource.eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				if (!notification.isTouch() && modelChanges.size() < 50) {
					String frames = Arrays.stream(new Throwable().getStackTrace())
							.filter(f -> f.getClassName().startsWith("edu.louisiana.caai.lom.editor")
									|| f.getClassName().startsWith("edu.louisiana.caai.lom.model"))
							.limit(15).map(f -> "\n      at " + f).collect(Collectors.joining());
					modelChanges.add(describe(notification) + frames);
				}
			}
		});
	}

	private static String describe(Notification notification) {
		Object feature = notification.getFeature();
		String name = feature instanceof EStructuralFeature f ? f.getName() : String.valueOf(notification.getFeatureID(null));
		Object notifier = notification.getNotifier();
		return "type=" + notification.getEventType() + " notifier=" + (notifier == null ? null : notifier.getClass().getSimpleName())
				+ " feature=" + name + " old=" + notification.getOldValue() + " new=" + notification.getNewValue();
	}

	// ----------------------------------------------------------------------------------------------------
	// Access to MultiPageEditorPart internals (protected API) and private fields
	// ----------------------------------------------------------------------------------------------------

	static int pageCount(HlomMetadataEditor editor) {
		return (Integer) invoke(editor, MultiPageEditorPart.class, "getPageCount", new Class<?>[0]);
	}

	static int activePage(HlomMetadataEditor editor) {
		return (Integer) invoke(editor, MultiPageEditorPart.class, "getActivePage", new Class<?>[0]);
	}

	static void setActivePage(HlomMetadataEditor editor, int index) {
		invoke(editor, MultiPageEditorPart.class, "setActivePage", new Class<?>[] { int.class }, index);
	}

	static Control pageControl(HlomMetadataEditor editor, int index) {
		return (Control) invoke(editor, MultiPageEditorPart.class, "getControl", new Class<?>[] { int.class }, index);
	}

	static String pageText(HlomMetadataEditor editor, int index) {
		return (String) invoke(editor, MultiPageEditorPart.class, "getPageText", new Class<?>[] { int.class }, index);
	}

	/** Index of a form page by id, or of {@code tree}/{@code xml}/{@code json}. */
	static int pageIndex(HlomMetadataEditor editor, String pageId) {
		if ("tree".equals(pageId)) {
			return (Integer) field(editor, "treePageIndex");
		}
		if ("xml".equals(pageId)) {
			return (Integer) field(editor, "xmlPageIndex");
		}
		if ("json".equals(pageId)) {
			return (Integer) field(editor, "jsonPageIndex");
		}
		@SuppressWarnings("unchecked")
		List<AbstractLomPage> pages = (List<AbstractLomPage>) field(editor, "formPages");
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getId().equals(pageId)) {
				return i;
			}
		}
		throw new AssertionError("no page " + pageId);
	}

	static Object field(Object target, String name) {
		for (Class<?> type = target.getClass(); type != null; type = type.getSuperclass()) {
			try {
				Field field = type.getDeclaredField(name);
				field.setAccessible(true);
				return field.get(target);
			} catch (NoSuchFieldException e) {
				// Try the superclass.
			} catch (IllegalAccessException e) {
				throw new AssertionError(e);
			}
		}
		throw new AssertionError("no field " + name + " in " + target.getClass());
	}

	static Object invoke(Object target, Class<?> declaringClass, String name, Class<?>[] types, Object... args) {
		try {
			Method method = declaringClass.getDeclaredMethod(name, types);
			method.setAccessible(true);
			return method.invoke(target, args);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException runtime) {
				throw runtime;
			}
			if (cause instanceof Error error) {
				throw error;
			}
			throw new AssertionError(cause);
		} catch (ReflectiveOperationException e) {
			throw new AssertionError(e);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Widgets
	// ----------------------------------------------------------------------------------------------------

	/** All controls of a type below (and including) a control that match a condition, in tree order. */
	static <T extends Control> List<T> find(Control root, Class<T> type, Predicate<? super T> condition) {
		List<T> result = new ArrayList<>();
		collect(root, type, condition, result);
		return result;
	}

	private static <T extends Control> void collect(Control control, Class<T> type, Predicate<? super T> condition,
			List<T> result) {
		if (type.isInstance(control) && condition.test(type.cast(control))) {
			result.add(type.cast(control));
		}
		if (control instanceof Composite composite) {
			for (Control child : composite.getChildren()) {
				collect(child, type, condition, result);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// Assertions
	// ----------------------------------------------------------------------------------------------------

	static List<HlomProblem> errors(List<HlomProblem> problems) {
		return problems.stream().filter(p -> p.severity() == HlomProblem.Severity.ERROR).toList();
	}

	static String describe(List<HlomProblem> problems) {
		return problems.stream().map(p -> p.severity() + " " + p.origin() + " " + p.path() + ":" + p.line() + " "
				+ p.message()).collect(Collectors.joining("\n", "\n", ""));
	}

	void assertNoLoggedErrors() {
		if (!loggedErrors.isEmpty()) {
			fail("Errors were logged:\n" + loggedErrors.stream().map(AbstractUiTest::format)
					.collect(Collectors.joining("\n")));
		}
	}

	void assertNoDialogs() {
		assertEquals(List.of(), dialogs.seen, "unexpected dialogs");
	}

	static String format(IStatus status) {
		StringBuilder text = new StringBuilder(status.getPlugin() + ": " + status.getMessage());
		if (status.getException() != null) {
			StringWriter trace = new StringWriter();
			status.getException().printStackTrace(new PrintWriter(trace));
			text.append('\n').append(trace);
		}
		for (IStatus child : status.getChildren()) {
			text.append("\n  ").append(format(child));
		}
		return text.toString();
	}

	/**
	 * Closes modal dialogs (which would otherwise block the test forever) and records their title and message.
	 * Runs from a timer, so it also works inside the nested event loop of a dialog's {@code open()}. Progress
	 * dialogs (shown while saving) are left alone.
	 */
	static final class DialogWatcher implements Runnable {
		final List<String> seen = new ArrayList<>();
		/** The buttons to press in dialogs, by dialog title (instead of closing them). */
		private final java.util.Map<String, String> answers = new java.util.HashMap<>();
		private boolean running;

		/** Presses a button (by its label without mnemonics) in the dialogs of a title, instead of closing them. */
		void answer(String title, String button) {
			answers.put(title, button);
		}

		void start() {
			running = true;
			display().timerExec(200, this);
		}

		void stop() {
			running = false;
			display().timerExec(-1, this);
		}

		@Override
		public void run() {
			if (!running) {
				return;
			}
			Display display = display();
			for (Shell shell : display.getShells()) {
				if (!shell.isDisposed() && shell.isVisible() && !(shell.getData() instanceof ProgressMonitorDialog)
						&& (shell.getStyle() & (SWT.APPLICATION_MODAL | SWT.SYSTEM_MODAL | SWT.PRIMARY_MODAL)) != 0) {
					String message = find(shell, Label.class, label -> !label.getText().isBlank()).stream()
							.map(Label::getText).collect(Collectors.joining(" | "));
					seen.add(shell.getText() + ": " + message);
					String answer = answers.get(shell.getText());
					List<org.eclipse.swt.widgets.Button> buttons = answer == null ? List.of()
							: find(shell, org.eclipse.swt.widgets.Button.class,
									button -> button.getText().replace("&", "").equals(answer));
					if (!buttons.isEmpty()) {
						System.err.println("[DialogWatcher] answering dialog \"" + shell.getText() + "\": " + answer);
						buttons.getFirst().notifyListeners(SWT.Selection, new org.eclipse.swt.widgets.Event());
						continue;
					}
					System.err.println("[DialogWatcher] closing dialog \"" + shell.getText() + "\": " + message);
					shell.close();
				}
			}
			display.timerExec(200, this);
		}
	}
}
