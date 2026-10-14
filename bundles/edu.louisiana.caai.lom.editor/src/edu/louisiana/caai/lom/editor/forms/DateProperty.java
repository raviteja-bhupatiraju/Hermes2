/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.editor.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;

import edu.louisiana.caai.lom.editor.binding.ModelCommands;

/**
 * The selected date of a {@link CDateTime}, and the conversion between {@link Date} and the
 * {@link XMLGregorianCalendar} values of {@code xsd:date} and {@code xsd:dateTime} attributes.
 * <p>
 * Like {@link TextProperty}, it doesn't write an unchanged date back to the widget.
 */
final class DateProperty extends WidgetValueProperty<CDateTime, Date> {

	DateProperty() {
		super(SWT.Selection);
	}

	@Override
	public Object getValueType() {
		return Date.class;
	}

	@Override
	protected Date doGetValue(CDateTime source) {
		return source.getSelection();
	}

	@Override
	protected void doSetValue(CDateTime source, Date value) {
		if (!Objects.equals(value, source.getSelection())) {
			source.setSelection(value);
		}
	}

	/** Whether an attribute holds XML Schema dates or date-times. */
	static boolean isDate(EAttribute attribute) {
		return attribute.getEAttributeType().getInstanceClass() == XMLGregorianCalendar.class;
	}

	/** Whether a date attribute is an {@code xsd:date} (no time of day). */
	static boolean isDateOnly(EAttribute attribute) {
		for (EDataType type = attribute.getEAttributeType(); type != null; type = ExtendedMetaData.INSTANCE
				.getBaseType(type)) {
			String name = ExtendedMetaData.INSTANCE.getName(type);
			if ("date".equals(name)) {
				return true;
			}
			if ("dateTime".equals(name)) {
				return false;
			}
		}
		return false;
	}

	/** The instant of a calendar value (in the local time zone if the value has none). */
	static Date toDate(Object value) {
		return value instanceof XMLGregorianCalendar calendar ? calendar.toGregorianCalendar().getTime() : null;
	}

	/** The XML lexical form of a date, without time zone. */
	static String format(Date date, boolean dateOnly) {
		return new SimpleDateFormat(dateOnly ? "yyyy-MM-dd" : "yyyy-MM-dd'T'HH:mm:ss").format(date);
	}

	/**
	 * Converts a picked date to the attribute's value. Returns {@code current} if it represents the same date,
	 * so that showing a value never changes the document.
	 */
	static Object toValue(EAttribute attribute, Date date, Object current) {
		if (date == null) {
			return null;
		}
		boolean dateOnly = isDateOnly(attribute);
		String text = format(date, dateOnly);
		Date currentDate = toDate(current);
		if (currentDate != null && format(currentDate, dateOnly).equals(text)) {
			return current;
		}
		// EMF's XML type factory creates the value through javax.xml.datatype.DatatypeFactory.
		return ModelCommands.fromString(attribute, text);
	}
}
