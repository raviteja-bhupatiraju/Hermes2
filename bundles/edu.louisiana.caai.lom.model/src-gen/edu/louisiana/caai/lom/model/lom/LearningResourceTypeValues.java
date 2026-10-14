/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.lom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Learning Resource Type Values</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getLearningResourceTypeValues()
 * @model extendedMetaData="name='learningResourceTypeValues'"
 * @generated
 */
public enum LearningResourceTypeValues implements Enumerator {
	/**
	 * The '<em><b>Exercise</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXERCISE_VALUE
	 * @generated
	 * @ordered
	 */
	EXERCISE(0, "exercise", "exercise"),

	/**
	 * The '<em><b>Simulation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIMULATION_VALUE
	 * @generated
	 * @ordered
	 */
	SIMULATION(1, "simulation", "simulation"),

	/**
	 * The '<em><b>Questionnaire</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUESTIONNAIRE_VALUE
	 * @generated
	 * @ordered
	 */
	QUESTIONNAIRE(2, "questionnaire", "questionnaire"),

	/**
	 * The '<em><b>Diagram</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIAGRAM_VALUE
	 * @generated
	 * @ordered
	 */
	DIAGRAM(3, "diagram", "diagram"),

	/**
	 * The '<em><b>Figure</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIGURE_VALUE
	 * @generated
	 * @ordered
	 */
	FIGURE(4, "figure", "figure"),

	/**
	 * The '<em><b>Graph</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GRAPH_VALUE
	 * @generated
	 * @ordered
	 */
	GRAPH(5, "graph", "graph"),

	/**
	 * The '<em><b>Index</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INDEX_VALUE
	 * @generated
	 * @ordered
	 */
	INDEX(6, "index", "index"),

	/**
	 * The '<em><b>Slide</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SLIDE_VALUE
	 * @generated
	 * @ordered
	 */
	SLIDE(7, "slide", "slide"),

	/**
	 * The '<em><b>Table</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	TABLE(8, "table", "table"),

	/**
	 * The '<em><b>Narrative Text</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NARRATIVE_TEXT_VALUE
	 * @generated
	 * @ordered
	 */
	NARRATIVE_TEXT(9, "narrativeText", "narrative text"),

	/**
	 * The '<em><b>Exam</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXAM_VALUE
	 * @generated
	 * @ordered
	 */
	EXAM(10, "exam", "exam"),

	/**
	 * The '<em><b>Experiment</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPERIMENT_VALUE
	 * @generated
	 * @ordered
	 */
	EXPERIMENT(11, "experiment", "experiment"),

	/**
	 * The '<em><b>Problem Statement</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROBLEM_STATEMENT_VALUE
	 * @generated
	 * @ordered
	 */
	PROBLEM_STATEMENT(12, "problemStatement", "problem statement"),

	/**
	 * The '<em><b>Self Assessment</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SELF_ASSESSMENT_VALUE
	 * @generated
	 * @ordered
	 */
	SELF_ASSESSMENT(13, "selfAssessment", "self assessment"),

	/**
	 * The '<em><b>Lecture</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LECTURE_VALUE
	 * @generated
	 * @ordered
	 */
	LECTURE(14, "lecture", "lecture");

	/**
	 * The '<em><b>Exercise</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXERCISE
	 * @model name="exercise"
	 * @generated
	 * @ordered
	 */
	public static final int EXERCISE_VALUE = 0;

	/**
	 * The '<em><b>Simulation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIMULATION
	 * @model name="simulation"
	 * @generated
	 * @ordered
	 */
	public static final int SIMULATION_VALUE = 1;

	/**
	 * The '<em><b>Questionnaire</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUESTIONNAIRE
	 * @model name="questionnaire"
	 * @generated
	 * @ordered
	 */
	public static final int QUESTIONNAIRE_VALUE = 2;

	/**
	 * The '<em><b>Diagram</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIAGRAM
	 * @model name="diagram"
	 * @generated
	 * @ordered
	 */
	public static final int DIAGRAM_VALUE = 3;

	/**
	 * The '<em><b>Figure</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIGURE
	 * @model name="figure"
	 * @generated
	 * @ordered
	 */
	public static final int FIGURE_VALUE = 4;

	/**
	 * The '<em><b>Graph</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GRAPH
	 * @model name="graph"
	 * @generated
	 * @ordered
	 */
	public static final int GRAPH_VALUE = 5;

	/**
	 * The '<em><b>Index</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INDEX
	 * @model name="index"
	 * @generated
	 * @ordered
	 */
	public static final int INDEX_VALUE = 6;

	/**
	 * The '<em><b>Slide</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SLIDE
	 * @model name="slide"
	 * @generated
	 * @ordered
	 */
	public static final int SLIDE_VALUE = 7;

	/**
	 * The '<em><b>Table</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TABLE
	 * @model name="table"
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_VALUE = 8;

	/**
	 * The '<em><b>Narrative Text</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NARRATIVE_TEXT
	 * @model name="narrativeText" literal="narrative text"
	 * @generated
	 * @ordered
	 */
	public static final int NARRATIVE_TEXT_VALUE = 9;

	/**
	 * The '<em><b>Exam</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXAM
	 * @model name="exam"
	 * @generated
	 * @ordered
	 */
	public static final int EXAM_VALUE = 10;

	/**
	 * The '<em><b>Experiment</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPERIMENT
	 * @model name="experiment"
	 * @generated
	 * @ordered
	 */
	public static final int EXPERIMENT_VALUE = 11;

	/**
	 * The '<em><b>Problem Statement</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROBLEM_STATEMENT
	 * @model name="problemStatement" literal="problem statement"
	 * @generated
	 * @ordered
	 */
	public static final int PROBLEM_STATEMENT_VALUE = 12;

	/**
	 * The '<em><b>Self Assessment</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SELF_ASSESSMENT
	 * @model name="selfAssessment" literal="self assessment"
	 * @generated
	 * @ordered
	 */
	public static final int SELF_ASSESSMENT_VALUE = 13;

	/**
	 * The '<em><b>Lecture</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LECTURE
	 * @model name="lecture"
	 * @generated
	 * @ordered
	 */
	public static final int LECTURE_VALUE = 14;

	/**
	 * An array of all the '<em><b>Learning Resource Type Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LearningResourceTypeValues[] VALUES_ARRAY = new LearningResourceTypeValues[] { EXERCISE,
			SIMULATION, QUESTIONNAIRE, DIAGRAM, FIGURE, GRAPH, INDEX, SLIDE, TABLE, NARRATIVE_TEXT, EXAM, EXPERIMENT,
			PROBLEM_STATEMENT, SELF_ASSESSMENT, LECTURE, };

	/**
	 * A public read-only list of all the '<em><b>Learning Resource Type Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LearningResourceTypeValues> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Learning Resource Type Values</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LearningResourceTypeValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LearningResourceTypeValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Learning Resource Type Values</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LearningResourceTypeValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LearningResourceTypeValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Learning Resource Type Values</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LearningResourceTypeValues get(int value) {
		switch (value) {
		case EXERCISE_VALUE:
			return EXERCISE;
		case SIMULATION_VALUE:
			return SIMULATION;
		case QUESTIONNAIRE_VALUE:
			return QUESTIONNAIRE;
		case DIAGRAM_VALUE:
			return DIAGRAM;
		case FIGURE_VALUE:
			return FIGURE;
		case GRAPH_VALUE:
			return GRAPH;
		case INDEX_VALUE:
			return INDEX;
		case SLIDE_VALUE:
			return SLIDE;
		case TABLE_VALUE:
			return TABLE;
		case NARRATIVE_TEXT_VALUE:
			return NARRATIVE_TEXT;
		case EXAM_VALUE:
			return EXAM;
		case EXPERIMENT_VALUE:
			return EXPERIMENT;
		case PROBLEM_STATEMENT_VALUE:
			return PROBLEM_STATEMENT;
		case SELF_ASSESSMENT_VALUE:
			return SELF_ASSESSMENT;
		case LECTURE_VALUE:
			return LECTURE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LearningResourceTypeValues(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //LearningResourceTypeValues
