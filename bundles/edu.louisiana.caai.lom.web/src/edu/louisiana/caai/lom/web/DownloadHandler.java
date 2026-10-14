/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.rap.rwt.service.ServiceHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Hands a file to the browser: the text is kept in the session under a random key, and the browser opens the
 * handler's URL with that key, once.
 */
final class DownloadHandler implements ServiceHandler {

	static final String ID = "hermes-download";

	private record Download(String name, String contentType, String text) {
	}

	/** Lets the browser save a text file. */
	static void offer(String name, String contentType, String text) {
		String key = UUID.randomUUID().toString();
		RWT.getUISession().setAttribute(ID + key, new Download(name, contentType, text));
		String url = RWT.getServiceManager().getServiceHandlerUrl(ID) + "&key=" + key;
		RWT.getClient().getService(UrlLauncher.class).openURL(url);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String key = request.getParameter("key");
		Object value = key == null ? null : RWT.getUISession().getAttribute(ID + key);
		if (!(value instanceof Download download)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		RWT.getUISession().removeAttribute(ID + key);
		byte[] bytes = download.text().getBytes(StandardCharsets.UTF_8);
		response.setContentType(download.contentType() + "; charset=UTF-8");
		response.setContentLength(bytes.length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + download.name().replace("\"", "") + "\"");
		response.getOutputStream().write(bytes);
	}
}
