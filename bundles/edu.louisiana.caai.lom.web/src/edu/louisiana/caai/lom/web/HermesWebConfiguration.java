/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.web;

import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

import edu.louisiana.caai.medbiq.model.MedbiqStandards;

/**
 * Hermes 2 Web: MedBiquitous documents edited in the browser (Eclipse RAP), served at {@code /hermes}. The documents,
 * their checks (XML Schema and the rules of the standards) and their conversions are those of the desktop
 * application and the command line tool.
 */
public final class HermesWebConfiguration implements ApplicationConfiguration {

	@Override
	public void configure(Application application) {
		MedbiqStandards.init();
		application.addEntryPoint("/hermes", HermesWebEntryPoint.class,
				Map.of(WebClient.PAGE_TITLE, "Hermes 2: MedBiquitous Editor"));
		application.addServiceHandler(DownloadHandler.ID, new DownloadHandler());
	}
}
