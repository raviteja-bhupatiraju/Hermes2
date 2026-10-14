/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.healthcare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Specimen Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.louisiana.caai.lom.model.healthcare.HealthcarePackage#getSpecimenTypeType()
 * @model extendedMetaData="name='specimenTypeType'"
 * @generated
 */
public enum SpecimenTypeType implements Enumerator {
	/**
	 * The '<em><b>Cell</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CELL_VALUE
	 * @generated
	 * @ordered
	 */
	CELL(0, "cell", "cell"),

	/**
	 * The '<em><b>Organ</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGAN_VALUE
	 * @generated
	 * @ordered
	 */
	ORGAN(1, "organ", "organ"),

	/**
	 * The '<em><b>Organ System</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGAN_SYSTEM_VALUE
	 * @generated
	 * @ordered
	 */
	ORGAN_SYSTEM(2, "organSystem", "organ system"),

	/**
	 * The '<em><b>Organelle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGANELLE_VALUE
	 * @generated
	 * @ordered
	 */
	ORGANELLE(3, "organelle", "organelle"),

	/**
	 * The '<em><b>Tissue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TISSUE_VALUE
	 * @generated
	 * @ordered
	 */
	TISSUE(4, "tissue", "tissue");

	/**
	 * The '<em><b>Cell</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CELL
	 * @model name="cell"
	 * @generated
	 * @ordered
	 */
	public static final int CELL_VALUE = 0;

	/**
	 * The '<em><b>Organ</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGAN
	 * @model name="organ"
	 * @generated
	 * @ordered
	 */
	public static final int ORGAN_VALUE = 1;

	/**
	 * The '<em><b>Organ System</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGAN_SYSTEM
	 * @model name="organSystem" literal="organ system"
	 * @generated
	 * @ordered
	 */
	public static final int ORGAN_SYSTEM_VALUE = 2;

	/**
	 * The '<em><b>Organelle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORGANELLE
	 * @model name="organelle"
	 * @generated
	 * @ordered
	 */
	public static final int ORGANELLE_VALUE = 3;

	/**
	 * The '<em><b>Tissue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TISSUE
	 * @model name="tissue"
	 * @generated
	 * @ordered
	 */
	public static final int TISSUE_VALUE = 4;

	/**
	 * An array of all the '<em><b>Specimen Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SpecimenTypeType[] VALUES_ARRAY = new SpecimenTypeType[] { CELL, ORGAN, ORGAN_SYSTEM,
			ORGANELLE, TISSUE, };

	/**
	 * A public read-only list of all the '<em><b>Specimen Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SpecimenTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Specimen Type Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SpecimenTypeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SpecimenTypeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Specimen Type Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SpecimenTypeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SpecimenTypeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Specimen Type Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SpecimenTypeType get(int value) {
		switch (value) {
		case CELL_VALUE:
			return CELL;
		case ORGAN_VALUE:
			return ORGAN;
		case ORGAN_SYSTEM_VALUE:
			return ORGAN_SYSTEM;
		case ORGANELLE_VALUE:
			return ORGANELLE;
		case TISSUE_VALUE:
			return TISSUE;
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
	private SpecimenTypeType(int value, String name, String literal) {
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

} //SpecimenTypeType
