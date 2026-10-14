/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.views;

import org.eclipse.osgi.util.NLS;

/** The texts of the user interface of this package ({@code messages.properties}). */
final class Messages extends NLS {

	private static final String BUNDLE_NAME = "edu.louisiana.caai.lom.editor.views.messages";

	public static String CurriculumTimelineView_OpenACurriculumInventoryTo;
	public static String CurriculumTimelineView_ShowEvents;
	public static String CurriculumTimelineView_TheTimelineShowsTheSequence;
	public static String DocumentGraphView_ArrangeAgain;
	public static String DocumentGraphView_ArrangeTheGraphAgain;
	public static String DocumentGraphView_Layered;
	public static String DocumentGraphView_OpenADocumentToSee;
	public static String DocumentGraphView_Radial;
	public static String DocumentGraphView_Show;
	public static String DocumentGraphView_Spring;
	public static String DocumentGraphView_TheDocumentGraphShowsThe;
	public static String DocumentGraphView_ThisDocumentHasNoReferences;
	public static String DocumentGraphView_Tree;
	public static String DocumentListsView_List;
	public static String DocumentListsView_OpenADocumentToSee;
	public static String DocumentListsView_TheListsOfADocument;
	public static String DocumentListsView_ThisDocumentHasNoLists;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
