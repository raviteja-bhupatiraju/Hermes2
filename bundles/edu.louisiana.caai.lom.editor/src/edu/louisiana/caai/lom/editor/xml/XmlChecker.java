/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.xml;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.swt.widgets.Display;

import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.type.DocumentType;

/**
 * Keeps the problem annotations of the XML page up to date. While the user types, the text is checked in the
 * background ({@value #DELAY} ms after the last change): it is parsed and validated against the schemas of the
 * document's standard, so errors show before the text is applied to the model. The problems of the model validation that the text
 * check can't find (model constraints, rules of the standard) are added at the elements they refer to.
 */
final class XmlChecker {

	/** Delay after the last change before the text is checked, in milliseconds. */
	static final int DELAY = 500;

	/** A model problem, with the element path of its object (computed in the UI thread). */
	private record ModelProblem(HlomProblem problem, HlomProblem.Severity severity, String message, List<String[]> path,
			int line, int column) {
	}

	/** A version of the text to check. */
	private record Request(long stamp, String text, DocumentType type) {
	}

	/** The outcome of a check of one version of the text. */
	private record Result(long stamp, String text, List<HlomProblem> live, XmlStructure structure) {
	}

	private final Display display;
	private final Supplier<IDocument> document;
	private final Supplier<IAnnotationModel> annotationModel;
	private final Supplier<DocumentType> type;
	private final Consumer<XmlStructure> structureListener;
	private final Runnable tick = this::check;
	/** The latest text to check; the job checks only the latest one of a burst of changes. */
	private final AtomicReference<Request> pending = new AtomicReference<>();
	private final Job job = Job.create(Messages.XmlChecker_CheckingTheXML, _ -> {
		checkPending();
		return Status.OK_STATUS;
	});

	private List<ModelProblem> modelProblems = List.of();
	private List<Annotation> annotations = new ArrayList<>();
	private Result last;
	private boolean disposed;

	/**
	 * @param type              the type of the document, whose schemas the text is validated against
	 * @param structureListener informed (in the UI thread) of the structure of each checked text version
	 */
	XmlChecker(Display display, Supplier<IDocument> document, Supplier<IAnnotationModel> annotationModel,
			Supplier<DocumentType> type, Consumer<XmlStructure> structureListener) {
		this.display = display;
		this.document = document;
		this.annotationModel = annotationModel;
		this.type = type;
		this.structureListener = structureListener;
		job.setSystem(true);
	}

	/** Checks the text {@value #DELAY} ms from now, unless it changes again in between. */
	void schedule() {
		if (!disposed && !display.isDisposed()) {
			display.timerExec(-1, tick);
			display.timerExec(DELAY, tick);
		}
	}

	/** Sets the problems found by the model validation; schema problems are ignored (the text check finds them). */
	void setProblems(List<HlomProblem> problems) {
		List<ModelProblem> result = new ArrayList<>();
		for (HlomProblem problem : problems == null ? List.<HlomProblem>of() : problems) {
			if (problem.origin() == HlomProblem.Origin.SCHEMA) {
				continue;
			}
			List<String[]> path = problem.object() == null ? null : XmlContentModel.path(problem.object());
			if (problem.line() > 0 || (path != null && !path.isEmpty())) {
				result.add(new ModelProblem(problem, problem.severity(), problem.message(), path, problem.line(),
						problem.column()));
			}
		}
		modelProblems = result;
		IDocument current = document.get();
		if (last != null && current != null && stamp(current) == last.stamp()) {
			apply(last);
		} else {
			schedule();
		}
	}

	private void check() {
		IDocument current = document.get();
		if (disposed || current == null) {
			return;
		}
		pending.set(new Request(stamp(current), current.get(), type.get()));
		job.schedule();
	}

	/** Checks the latest text (in the job). */
	private void checkPending() {
		Request request = pending.getAndSet(null);
		if (request != null) {
			List<HlomProblem> live = request.type().validateSchema(request.text().getBytes(StandardCharsets.UTF_8), null);
			Result result = new Result(request.stamp(), request.text(), live, XmlStructure.parse(request.text()));
			if (!display.isDisposed()) {
				display.asyncExec(() -> apply(result));
			}
		}
	}

	private void apply(Result result) {
		IDocument current = document.get();
		IAnnotationModel model = annotationModel.get();
		if (disposed || current == null || model == null || stamp(current) != result.stamp()) {
			return;
		}
		last = result;
		XmlProblemLocator locator = new XmlProblemLocator(result.text());
		Map<Annotation, Position> added = new HashMap<>();
		for (HlomProblem problem : result.live()) {
			added.put(new XmlAnnotations.ProblemAnnotation(problem.severity(), clean(problem.message()), null),
					locator.locate(problem.line(), problem.column()));
		}
		for (ModelProblem problem : modelProblems) {
			Position position = null;
			if (problem.path() != null) {
				XmlStructure.Element element = result.structure().find(problem.path());
				position = element == null ? null : locator.locate(element);
			}
			if (position == null && problem.line() > 0) {
				position = locator.locate(problem.line(), problem.column());
			}
			if (position != null) {
				added.put(new XmlAnnotations.ProblemAnnotation(problem.severity(), problem.message(), problem.problem()),
						position);
			}
		}
		if (model instanceof IAnnotationModelExtension extension) {
			extension.replaceAnnotations(annotations.toArray(Annotation[]::new), added);
		} else {
			annotations.forEach(model::removeAnnotation);
			added.forEach(model::addAnnotation);
		}
		annotations = new ArrayList<>(added.keySet());
		structureListener.accept(result.structure());
	}

	/**
	 * Removes namespace noise from schema messages: {@code "http://ltsc.ieee.org/xsd/LOM":title} becomes
	 * {@code title} and the wildcard {@code WC[##other:"..."]} becomes "extension elements".
	 */
	static String clean(String message) {
		if (message == null) {
			return "";
		}
		return message.replaceAll("WC\\[##other:\"[^\"]*\"\\]", "extension elements")
				.replaceAll("\"[a-z]+:[^\"\\s]*\":", "");
	}

	/** The messages of the problems shown, or {@code null} while the current text is not checked yet. */
	List<String> checkedProblems() {
		IDocument current = document.get();
		return last == null || current == null || stamp(current) != last.stamp() ? null
				: annotations.stream().map(Annotation::getText).toList();
	}

	/** The problems currently shown (for tests and status display). */
	List<Annotation> annotations() {
		return annotations;
	}

	private static long stamp(IDocument document) {
		return document instanceof IDocumentExtension4 extension ? extension.getModificationStamp()
				: document.get().hashCode();
	}

	void dispose() {
		disposed = true;
		if (!display.isDisposed()) {
			display.timerExec(-1, tick);
		}
		pending.set(null);
		job.cancel();
	}
}
