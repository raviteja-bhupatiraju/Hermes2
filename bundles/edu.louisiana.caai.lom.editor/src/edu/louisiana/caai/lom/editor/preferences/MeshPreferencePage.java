/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.preferences;

import org.eclipse.osgi.util.NLS;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import edu.louisiana.caai.lom.editor.HlomTheme;
import edu.louisiana.caai.lom.model.support.terminology.Terminologies;

/** The "MeSH" preference page: online lookup of Medical Subject Headings descriptors. */
public class MeshPreferencePage extends AbstractHlomPreferencePage {

	/** The page id. */
	public static final String ID = "edu.louisiana.caai.lom.editor.preferences.mesh";

	private Button enabled;
	private Text url;
	private Button test;
	private Label statusIcon;
	private Label status;

	/** Creates the page. */
	public MeshPreferencePage() {
		setDescription(Messages.MeshPreferencePage_LookUpMedicalSubjectHeadings);
	}

	@Override
	protected void createSections(Composite page) {
		Composite lookup = section(page, Messages.MeshPreferencePage_OnlineFallback,
				Messages.MeshPreferencePage_KeywordsAreWrittenWithHx);
		enabled = check(lookup, Messages.MeshPreferencePage_LookUpUnknownDescriptorIDs, HlomPreferences.MESH_ENABLED);
		enabled.addListener(SWT.Selection, _ -> {
			loaded();
			validate();
		});
		url = text(lookup, Messages.MeshPreferencePage_ServiceURL, HlomPreferences.MESH_BASE_URL, HlomPreferences.DEFAULT_MESH_BASE_URL,
				MeshPreferencePage::validateUrl);
		url.addModifyListener(_ -> showStatus(null, null));

		new Label(lookup, SWT.NONE);
		Composite row = new Composite(lookup, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(8, 0).applyTo(row);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(row);
		test = new Button(row, SWT.PUSH);
		test.setText(Messages.MeshPreferencePage_TestConnection);
		test.addListener(SWT.Selection, _ -> testConnection());
		statusIcon = new Label(row, SWT.NONE);
		GridDataFactory.swtDefaults().hint(16, 16).applyTo(statusIcon);
		status = new Label(row, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(status);

		Composite about = section(page, Messages.MeshPreferencePage_AboutMeSH, null);
		Link link = new Link(about, SWT.WRAP);
		link.setText(NLS.bind(Messages.MeshPreferencePage_MeSHIsTheControlledVocabulary, Terminologies.MESH.getVersion()));
		link.addListener(SWT.Selection, event -> Program.launch(event.text));
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).hint(400, SWT.DEFAULT).applyTo(link);
	}

	@Override
	protected void loaded() {
		boolean on = enabled.getSelection();
		url.setEnabled(on);
		test.setEnabled(on);
	}

	private static String validateUrl(String value) {
		try {
			URI uri = new URI(value);
			if (("https".equals(uri.getScheme()) || "http".equals(uri.getScheme())) && uri.getHost() != null) {
				return null;
			}
		} catch (Exception _) {
			// reported below
		}
		return NLS.bind(Messages.MeshPreferencePage_TheMeSHServiceURLMust, HlomPreferences.DEFAULT_MESH_BASE_URL);
	}

	private void showStatus(String imagePath, String text) {
		if (status.isDisposed()) {
			return;
		}
		statusIcon.setImage(imagePath == null ? null : HlomTheme.image(imagePath));
		status.setText(text == null ? "" : text);
		status.getParent().layout(true);
	}

	/** Queries the descriptor "Heart" (D006321) in the background and shows the result. */
	private void testConnection() {
		String base = url.getText().strip();
		if (validateUrl(base) != null) {
			return;
		}
		if (!base.endsWith("/")) {
			base += "/";
		}
		showStatus(null, Messages.MeshPreferencePage_Connecting);
		test.setEnabled(false);
		Display display = test.getDisplay();
		long start = System.nanoTime();
		HttpRequest request = HttpRequest.newBuilder(URI.create(NLS.bind(Messages.MeshPreferencePage_LookupDescriptorLabelHeartMatch, base)))
				.timeout(Duration.ofSeconds(10)).header(Messages.MeshPreferencePage_Accept, "application/json").GET().build();
		HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).followRedirects(HttpClient.Redirect.NORMAL)
				.build().sendAsync(request, HttpResponse.BodyHandlers.ofString()).whenComplete((response, error) -> {
					long millis = (System.nanoTime() - start) / 1_000_000;
					String icon;
					String text;
					if (error != null) {
						icon = "icons/actions/error.png";
						Throwable cause = error.getCause() != null ? error.getCause() : error;
						text = NLS.bind(Messages.MeshPreferencePage_NotReachable, (cause.getMessage() != null ? cause.getMessage()
								: cause.getClass().getSimpleName()));
					} else if (response.statusCode() == 200 && response.body().contains("D006321")) {
						icon = "icons/actions/ok.png";
						text = NLS.bind(Messages.MeshPreferencePage_ConnectedMeSHAnsweredInMs, millis);
					} else {
						icon = "icons/actions/warning.png";
						text = NLS.bind(Messages.MeshPreferencePage_UnexpectedAnswerHTTPIsThis, response.statusCode());
					}
					if (!display.isDisposed()) {
						display.asyncExec(() -> {
							if (!test.isDisposed()) {
								test.setEnabled(enabled.getSelection());
								showStatus(icon, text);
							}
						});
					}
				});
	}
}
