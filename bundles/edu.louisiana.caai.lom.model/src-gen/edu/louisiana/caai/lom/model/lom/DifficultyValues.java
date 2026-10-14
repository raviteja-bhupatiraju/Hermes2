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
 * A representation of the literals of the enumeration '<em><b>Difficulty Values</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.lom.LomPackage#getDifficultyValues()
 * @model extendedMetaData="name='difficultyValues'"
 * @generated
 */
public enum DifficultyValues implements Enumerator {
	/**
	 * The '<em><b>Very Easy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VERY_EASY_VALUE
	 * @generated
	 * @ordered
	 */
	VERY_EASY(0, "veryEasy", "very easy"),

	/**
	 * The '<em><b>Easy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EASY_VALUE
	 * @generated
	 * @ordered
	 */
	EASY(1, "easy", "easy"),

	/**
	 * The '<em><b>Medium</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MEDIUM_VALUE
	 * @generated
	 * @ordered
	 */
	MEDIUM(2, "medium", "medium"),

	/**
	 * The '<em><b>Difficult</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIFFICULT_VALUE
	 * @generated
	 * @ordered
	 */
	DIFFICULT(3, "difficult", "difficult"),

	/**
	 * The '<em><b>Very Difficult</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VERY_DIFFICULT_VALUE
	 * @generated
	 * @ordered
	 */
	VERY_DIFFICULT(4, "veryDifficult", "very difficult");

	/**
	 * The '<em><b>Very Easy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VERY_EASY
	 * @model name="veryEasy" literal="very easy"
	 * @generated
	 * @ordered
	 */
	public static final int VERY_EASY_VALUE = 0;

	/**
	 * The '<em><b>Easy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EASY
	 * @model name="easy"
	 * @generated
	 * @ordered
	 */
	public static final int EASY_VALUE = 1;

	/**
	 * The '<em><b>Medium</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MEDIUM
	 * @model name="medium"
	 * @generated
	 * @ordered
	 */
	public static final int MEDIUM_VALUE = 2;

	/**
	 * The '<em><b>Difficult</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIFFICULT
	 * @model name="difficult"
	 * @generated
	 * @ordered
	 */
	public static final int DIFFICULT_VALUE = 3;

	/**
	 * The '<em><b>Very Difficult</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VERY_DIFFICULT
	 * @model name="veryDifficult" literal="very difficult"
	 * @generated
	 * @ordered
	 */
	public static final int VERY_DIFFICULT_VALUE = 4;

	/**
	 * An array of all the '<em><b>Difficulty Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DifficultyValues[] VALUES_ARRAY = new DifficultyValues[] { VERY_EASY, EASY, MEDIUM, DIFFICULT,
			VERY_DIFFICULT, };

	/**
	 * A public read-only list of all the '<em><b>Difficulty Values</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DifficultyValues> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Difficulty Values</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifficultyValues get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DifficultyValues result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Difficulty Values</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifficultyValues getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DifficultyValues result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Difficulty Values</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifficultyValues get(int value) {
		switch (value) {
		case VERY_EASY_VALUE:
			return VERY_EASY;
		case EASY_VALUE:
			return EASY;
		case MEDIUM_VALUE:
			return MEDIUM;
		case DIFFICULT_VALUE:
			return DIFFICULT;
		case VERY_DIFFICULT_VALUE:
			return VERY_DIFFICULT;
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
	private DifficultyValues(int value, String name, String literal) {
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

} //DifficultyValues
