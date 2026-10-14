/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.editor.HlomMetadataEditor;
import edu.louisiana.caai.lom.editor.json.JsonSourceEditor;
import edu.louisiana.caai.lom.editor.projects.ProjectsView;
import edu.louisiana.caai.lom.editor.workspace.DocumentIndex;
import edu.louisiana.caai.lom.editor.workspace.HermesWorkspace;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.project.HermesProject;
import edu.louisiana.caai.lom.model.support.project.HermesProject.Format;

/**
 * Editing documents stored as JSON, and the project manager.
 */
class JsonAndProjectsTest extends AbstractUiTest {

	private static final String TITLE = "Insulin Pump Therapy";
	private static final String NEW_TITLE = "Insulin Pump Therapy (JSON)";

	/** Saves the XML sample in the JSON form and returns the JSON file. */
	private Path jsonSample() throws Exception {
		Path xml = copyResource("resources/samples/insulin-pump-therapy-course.xml");
		Resource resource = HlomResources.load(URI.createFileURI(xml.toString()));
		Path json = xml.resolveSibling("insulin-pump-therapy-course.json");
		resource.setURI(URI.createFileURI(json.toString()));
		resource.save(HlomResources.saveOptions());
		Files.delete(xml);
		return json;
	}

	static JsonSourceEditor jsonEditor(HlomMetadataEditor editor) {
		return (JsonSourceEditor) field(editor, "jsonEditor");
	}

	@Test
	void jsonDocumentIsEditedAndSavedAsJson() throws Exception {
		Path file = jsonSample();
		assertTrue(Files.readString(file, StandardCharsets.UTF_8).startsWith("{"));
		HlomMetadataEditor editor = open(file);
		assertEquals(0, activePage(editor), "a valid JSON document opens on the overview");
		assertFalse(editor.isDirty());
		assertEquals(TITLE, FormEditingTest.modelTitle(editor));

		editor.showPage("json");
		dispatch(100);
		JsonSourceEditor json = jsonEditor(editor);
		assertTrue(json.getText().contains("\"content\": \"" + TITLE + "\""), json.getText());
		IDocument document = ((ITextEditor) json).getDocumentProvider().getDocument(json.getEditorInput());
		int offset = document.get().indexOf("\"" + TITLE + "\"") + 1;
		document.replace(offset, TITLE.length(), NEW_TITLE);
		assertTrue(editor.isDirty(), "a JSON edit dirties the editor");

		editor.showPage("general");
		dispatch(100);
		assertEquals(pageIndex(editor, "general"), activePage(editor), "valid JSON is applied on page switch");
		assertEquals(NEW_TITLE, FormEditingTest.modelTitle(editor));

		editor.doSave(null);
		dispatch(200);
		assertFalse(editor.isDirty());
		String saved = Files.readString(file, StandardCharsets.UTF_8);
		assertTrue(saved.startsWith("{"), "saved in the JSON form");
		assertTrue(saved.contains(NEW_TITLE), saved);
		editor.validateNow();
		assertTrue(errors(editor.getProblems()).isEmpty(), describe(editor.getProblems()));
		page().closeEditor(editor, false);
		assertNoDialogs();
	}

	@Test
	void brokenJsonOpensOnTheJsonPage() throws Exception {
		Path file = jsonSample();
		String text = Files.readString(file, StandardCharsets.UTF_8);
		// Remove the comma after the "$schema" member.
		Files.writeString(file, text.replaceFirst("\\.schema\\.json\",", ".schema.json\""), StandardCharsets.UTF_8);
		HlomMetadataEditor editor = open(file);
		dispatch(300);
		assertNoDialogs();
		assertEquals(pageIndex(editor, "json"), activePage(editor), "a broken JSON file opens on the JSON page");
		assertTrue(jsonEditor(editor).getText().contains(".schema.json\"\n"), "the JSON page shows the original text");
		editor.validateNow();
		assertFalse(errors(editor.getProblems()).isEmpty(), "the syntax error is reported");
		assertTrue(errors(editor.getProblems()).get(0).line() == 3, describe(editor.getProblems()));
		page().closeEditor(editor, false);
	}

	@Test
	void projectsViewListsAndConvertsDocuments() throws Exception {
		ProjectsView view = ProjectsView.show(window());
		assertNotNull(view);
		Path folder = tempDir.resolve("Sepsis Project");
		try {
			HermesProject project = HermesProject.create(folder, "Sepsis Project", "Test project", Format.JSON);
			Path sample = copyResource("resources/samples/insulin-pump-therapy-course.xml");
			project.importFiles(List.of(sample), folder);
			Files.createDirectories(folder.resolve("images"));
			Files.writeString(folder.resolve("notes.json"), "{\"not\": \"hlom\"}", StandardCharsets.UTF_8);

			view.addProject(project);
			Tree tree = find(view.getViewSite().getShell(), Tree.class,
					t -> t.getItemCount() > 0 && t.getItem(0).getText().startsWith("Sepsis Project")).get(0);
			waitUntil("the project lists its document", () -> labels(tree).stream()
					.anyMatch(label -> label.startsWith("insulin-pump-therapy-course.xml")), TIMEOUT);
			List<String> labels = labels(tree);
			assertTrue(labels.contains("images"), labels.toString());
			assertFalse(labels.stream().anyMatch(label -> label.startsWith("notes.json")), "not an HLOM document");
			assertFalse(labels.stream().anyMatch(label -> label.startsWith(HermesProject.PROJECT_FILE)));
			waitUntil("the document's title is shown", () -> labels(tree).stream().anyMatch(label -> label.contains(TITLE)),
					TIMEOUT);

			Path converted = HermesProject.convert(folder.resolve("insulin-pump-therapy-course.xml"), Format.JSON);
			HermesWorkspace.refresh(converted.getParent());
			waitUntil("the converted document is listed",
					() -> labels(tree).stream().anyMatch(label -> label.startsWith("insulin-pump-therapy-course.json")),
					TIMEOUT);
			assertEquals(List.of(converted), project.documents());
			assertNoDialogs();
		} finally {
			closeProject(folder);
			page().hideView(view);
		}
	}

	@Test
	void problemsOfProjectDocumentsBecomeMarkers() throws Exception {
		Path folder = tempDir.resolve("Marker Project");
		try {
			HermesProject project = HermesProject.create(folder, "Marker Project", "", Format.XML);
			Path sample = copyResource("resources/samples/insulin-pump-therapy-course.xml");
			Path document = project.importFiles(List.of(sample), folder).getFirst();
			IProject opened = HermesWorkspace.open(folder, null);
			assertTrue(opened.hasNature(HermesWorkspace.NATURE_ID));
			IFile file = HermesWorkspace.fileAt(document);
			assertNotNull(file);
			waitUntil("the document is validated", () -> DocumentIndex.get(file) != null, TIMEOUT);
			assertEquals(TITLE, DocumentIndex.get(file).title());
			assertEquals(0, errorMarkers(file).length, "the sample is valid");

			// An attribute the schema doesn't allow makes the document invalid; the builder reports it as a marker.
			String xml = Files.readString(document, StandardCharsets.UTF_8);
			Files.writeString(document, xml.replaceFirst("<lom ", "<lom bogus=\"1\" "), StandardCharsets.UTF_8);
			file.refreshLocal(IResource.DEPTH_ZERO, null);
			waitUntil("the error is reported as a marker", () -> errorMarkers(file).length > 0, TIMEOUT);

			// Documents of projects open as workspace files, so the editor can show a marker's problem.
			HlomMetadataEditor editor = open(document);
			assertInstanceOf(IFileEditorInput.class, editor.getEditorInput());
			IDE.gotoMarker(editor, errorMarkers(file)[0]);
			dispatch(300);
			assertNoDialogs();
			page().closeEditor(editor, false);
		} finally {
			closeProject(folder);
		}
	}

	@Test
	void problemsViewQuickFixesRepairClosedAndOpenDocuments() throws Exception {
		Path folder = tempDir.resolve("Resolution Project");
		String healthcare = "<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>";
		try {
			HermesProject project = HermesProject.create(folder, "Resolution Project", "", Format.XML);
			Path sample = copyResource("resources/samples/insulin-pump-therapy-course.xml");
			Path document = project.importFiles(List.of(sample), folder).getFirst();
			String valid = Files.readString(document, StandardCharsets.UTF_8);
			assertTrue(valid.contains(healthcare));
			Files.writeString(document, valid.replace(healthcare, ""), StandardCharsets.UTF_8);
			HermesWorkspace.open(folder, null);
			IFile file = HermesWorkspace.fileAt(document);
			waitUntil("the missing metadata schema is reported", () -> fixableMarker(file) != null, TIMEOUT);

			// A document that isn't open is fixed and saved; the builder validates it again.
			IMarker marker = fixableMarker(file);
			WorkbenchMarkerResolution resolution = changeResolution(marker);
			assertNotNull(resolution, "a quick fix that changes the document");
			resolution.run(new IMarker[] { marker }, new NullProgressMonitor());
			waitUntil("the fix is saved", () -> text(document).contains(healthcare), TIMEOUT);
			waitUntil("the problem is gone", () -> fixableMarker(file) == null, TIMEOUT);

			// In an open editor, the fix is an undoable change of the editor (not saved).
			Files.writeString(document, valid.replace(healthcare, ""), StandardCharsets.UTF_8);
			file.refreshLocal(IResource.DEPTH_ZERO, null);
			waitUntil("the problem is reported again", () -> fixableMarker(file) != null, TIMEOUT);
			HlomMetadataEditor editor = open(document);
			dispatch(300);
			changeResolution(fixableMarker(file)).run(new IMarker[] { fixableMarker(file) }, new NullProgressMonitor());
			dispatch(300);
			assertTrue(editor.isDirty(), "the editor has the fix");
			assertFalse(text(document).contains(healthcare),
					"the file is unchanged until the editor is saved");
			assertNoDialogs();
			page().closeEditor(editor, false);
		} finally {
			closeProject(folder);
		}
	}

	private static String text(Path file) {
		try {
			return Files.readString(file, StandardCharsets.UTF_8);
		} catch (java.io.IOException _) {
			return "";
		}
	}

	private static IMarker fixableMarker(IFile file) {
		try {
			for (IMarker marker : file.findMarkers(HermesWorkspace.PROBLEM_MARKER, false, IResource.DEPTH_ZERO)) {
				if (changeResolution(marker) != null) {
					return marker;
				}
			}
		} catch (CoreException _) {
			// None.
		}
		return null;
	}

	private static WorkbenchMarkerResolution changeResolution(IMarker marker) {
		if (marker == null || !IDE.getMarkerHelpRegistry().hasResolutions(marker)) {
			return null;
		}
		for (IMarkerResolution resolution : IDE.getMarkerHelpRegistry().getResolutions(marker)) {
			if (resolution instanceof WorkbenchMarkerResolution change) {
				return change;
			}
		}
		return null;
	}

	private static IMarker[] errorMarkers(IFile file) {
		try {
			return java.util.Arrays.stream(file.findMarkers(HermesWorkspace.PROBLEM_MARKER, false, IResource.DEPTH_ZERO))
					.filter(marker -> marker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR)
					.toArray(IMarker[]::new);
		} catch (CoreException _) {
			return new IMarker[0];
		}
	}

	private static void closeProject(Path folder) throws CoreException {
		IProject project = HermesWorkspace.projectAt(folder);
		if (project != null) {
			HermesWorkspace.close(project, null);
		}
	}

	private static List<String> labels(Tree tree) {
		List<String> labels = new ArrayList<>();
		for (TreeItem item : tree.getItems()) {
			collect(item, labels);
		}
		return labels;
	}

	private static void collect(TreeItem item, List<String> labels) {
		labels.add(item.getText());
		if (item.getExpanded()) {
			for (TreeItem child : item.getItems()) {
				collect(child, labels);
			}
		}
	}
}
