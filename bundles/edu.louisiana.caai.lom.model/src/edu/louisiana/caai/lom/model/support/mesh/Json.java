/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.mesh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.louisiana.caai.lom.model.support.json.Json.JArray;
import edu.louisiana.caai.lom.model.support.json.Json.JBoolean;
import edu.louisiana.caai.lom.model.support.json.Json.JNull;
import edu.louisiana.caai.lom.model.support.json.Json.JNumber;
import edu.louisiana.caai.lom.model.support.json.Json.JObject;
import edu.louisiana.caai.lom.model.support.json.Json.JString;
import edu.louisiana.caai.lom.model.support.json.Json.Value;

/**
 * The MeSH responses as plain Java values (parsed by the application's JSON parser): objects become {@link Map}s,
 * arrays {@link List}s, numbers {@link BigDecimal}s, and {@code true}/{@code false}/{@code null} the matching Java
 * values; with lenient accessors for the members of JSON-LD responses.
 */
final class Json {

	private Json() {
	}

	/** Parses a JSON document; throws {@link IllegalArgumentException} if it isn't valid JSON. */
	static Object parse(String text) {
		if (text == null) {
			throw new IllegalArgumentException("No JSON content");
		}
		try {
			return java(edu.louisiana.caai.lom.model.support.json.Json.parse(text));
		} catch (edu.louisiana.caai.lom.model.support.json.Json.JsonException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	private static Object java(Value value) {
		return switch (value) {
			case JObject object -> {
				Map<String, Object> map = new LinkedHashMap<>();
				object.members().forEach((name, member) -> map.put(name, java(member.value())));
				yield map;
			}
			case JArray array -> {
				List<Object> list = new ArrayList<>();
				array.items().forEach(item -> list.add(java(item)));
				yield list;
			}
			case JString string -> string.value();
			case JNumber number -> new BigDecimal(number.literal());
			case JBoolean bool -> bool.value();
			case JNull _ -> null;
		};
	}

	/** A member of an object, or {@code null}. */
	static Object member(Object object, String name) {
		return object instanceof Map<?, ?> map ? map.get(name) : null;
	}

	/** A string member, or {@code null}; JSON-LD value objects ({@code {"@value": ...}}) are unwrapped. */
	static String string(Object object, String name) {
		return string(member(object, name));
	}

	/** A string, or the {@code @value} of a JSON-LD value object, or {@code null}. */
	static String string(Object value) {
		if (value instanceof String string) {
			return string;
		}
		if (value instanceof Map<?, ?> map && map.get("@value") instanceof String string) {
			return string;
		}
		if (value instanceof List<?> list && !list.isEmpty()) {
			return string(list.getFirst());
		}
		return null;
	}

	/** A member as list: arrays as they are, a single value as a one-element list, missing as empty list. */
	static List<?> list(Object object, String name) {
		Object value = member(object, name);
		if (value == null) {
			return Collections.emptyList();
		}
		return value instanceof List<?> list ? list : List.of(value);
	}
}
