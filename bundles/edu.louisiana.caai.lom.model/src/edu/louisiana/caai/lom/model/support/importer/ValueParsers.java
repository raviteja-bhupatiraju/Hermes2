/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.importer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lenient parsers for the values found in spreadsheets: dates in ISO, US and written forms (and Excel serial
 * numbers), times, durations ("90", "1h30", "1.5 hours", "PT45M"), decimal numbers with units, file sizes,
 * language names and tags, country names and codes, and URLs.
 */
public final class ValueParsers {

	/** A parsed value with an optional note on how it was interpreted. */
	public record Parsed<T>(T value, String note) {
	}

	private static final Pattern ISO_DURATION = Pattern
			.compile("P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?(T(?=\\d)(\\d+H)?(\\d+M)?(\\d+(\\.\\d+)?S)?)?",
					Pattern.CASE_INSENSITIVE);
	private static final Pattern DURATION_PART = Pattern.compile(
			"(\\d+(?:[.,]\\d+)?)\\s*(weeks?|wks?|w|days?|d|hours?|hrs?|h|minutes?|mins?|m|seconds?|secs?|s)?(?![a-z])",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern CLOCK_DURATION = Pattern.compile("(\\d{1,3}):(\\d{2})(?::(\\d{2}))?");
	private static final Pattern NUMBER = Pattern.compile("[-+]?\\d+(?:[.,]\\d+)?");
	private static final Pattern LANGUAGE_TAG = Pattern.compile("[a-zA-Z]{2,3}([-_][a-zA-Z0-9]{2,8})*|x-none");
	private static final Pattern LOM_DATE = Pattern
			.compile("\\d{4}(-\\d{2}(-\\d{2}(T\\d{2}(:\\d{2}(:\\d{2}(\\.\\d+)?)?)?(Z|[+-]\\d{2}:\\d{2})?)?)?)?");
	private static final Pattern SIZE = Pattern.compile("(\\d+(?:[.,]\\d+)?)\\s*(b|bytes?|kb|kib|k|mb|mib|m|gb|gib|g)?",
			Pattern.CASE_INSENSITIVE);

	private static final List<DateTimeFormatter> DATE_FORMATS = new ArrayList<>();
	private static final List<DateTimeFormatter> TIME_FORMATS = new ArrayList<>();
	private static final Map<String, String> LANGUAGES = new HashMap<>();
	private static final Map<String, String> COUNTRIES = new HashMap<>();

	static {
		for (String pattern : new String[] { "uuuu-M-d", "uuuu/M/d", "uuuu.M.d", "uuuuMMdd", "MMMM d, uuuu",
				"MMMM d uuuu", "MMM d, uuuu", "MMM d uuuu", "d MMMM uuuu", "d MMM uuuu", "d-MMM-uuuu", "d-MMM-uu",
				"EEEE, MMMM d, uuuu", "EEE, MMM d, uuuu" }) {
			DATE_FORMATS.add(new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(pattern)
					.toFormatter(Locale.ENGLISH).withResolverStyle(ResolverStyle.STRICT));
		}
		for (String pattern : new String[] { "H:mm", "H:mm:ss", "h:mm a", "h:mma", "h:mm:ss a", "h a", "ha",
				"HHmm" }) {
			TIME_FORMATS.add(new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(pattern)
					.toFormatter(Locale.ENGLISH).withResolverStyle(ResolverStyle.SMART));
		}
		for (String code : Locale.getISOLanguages()) {
			Locale locale = Locale.of(code);
			LANGUAGES.put(TermMatcher.normalize(locale.getDisplayLanguage(Locale.ENGLISH)), code);
			LANGUAGES.put(TermMatcher.normalize(locale.getDisplayLanguage(locale)), code);
		}
		for (String code : Locale.getISOCountries()) {
			COUNTRIES.put(TermMatcher.normalize(Locale.of("", code).getDisplayCountry(Locale.ENGLISH)), code);
		}
		String[] countrySynonyms = { "usa", "US", "u s a", "US", "u s", "US", "united states of america", "US",
				"america", "US", "uk", "GB", "great britain", "GB", "england", "GB", "scotland", "GB", "wales", "GB",
				"britain", "GB", "deutschland", "DE", "espana", "ES", "españa", "ES", "holland", "NL", "korea", "KR",
				"south korea", "KR", "russia", "RU", "uae", "AE", "czech republic", "CZ", "ivory coast", "CI",
				"vietnam", "VN", "turkey", "TR", "prc", "CN" };
		for (int i = 0; i < countrySynonyms.length; i += 2) {
			COUNTRIES.put(TermMatcher.normalize(countrySynonyms[i]), countrySynonyms[i + 1]);
		}
	}

	private ValueParsers() {
	}

	/**
	 * Parses a calendar date into {@code YYYY-MM-DD}. Accepts ISO dates, US {@code M/D/YYYY} (or
	 * {@code D/M/YYYY} when the first number exceeds 12), {@code D.M.YYYY}, written months and Excel serial
	 * numbers. Returns {@code null} if the value is not a date.
	 */
	public static Parsed<LocalDate> date(String text) {
		String value = text.strip();
		if (value.isEmpty()) {
			return null;
		}
		int t = value.indexOf('T');
		if (t == 10 && value.length() > 10 && Character.isDigit(value.charAt(0))) {
			value = value.substring(0, 10);
		}
		for (DateTimeFormatter format : DATE_FORMATS) {
			try {
				return new Parsed<>(LocalDate.parse(value, format), null);
			} catch (DateTimeParseException _) {
				// Try the next format.
			}
		}
		Matcher slash = Pattern.compile("(\\d{1,2})[/\\-.](\\d{1,2})[/\\-.](\\d{2}|\\d{4})").matcher(value);
		if (slash.matches()) {
			int first = Integer.parseInt(slash.group(1));
			int second = Integer.parseInt(slash.group(2));
			int year = Integer.parseInt(slash.group(3));
			if (year < 100) {
				year += year < 70 ? 2000 : 1900;
			}
			boolean dotted = value.contains(".");
			boolean dayFirst = first > 12 || dotted;
			try {
				LocalDate date = dayFirst ? LocalDate.of(year, second, first) : LocalDate.of(year, first, second);
				return new Parsed<>(date, first <= 12 && second <= 12 && first != second
						? "\"" + text.strip() + "\" was read as " + (dayFirst ? "day/month/year" : "month/day/year")
								+ " (" + date + ")"
						: null);
			} catch (java.time.DateTimeException _) {
				return null;
			}
		}
		if (value.matches("\\d{5}(\\.\\d+)?")) {
			double serial = Double.parseDouble(value);
			if (serial > 20000 && serial < 80000) {
				LocalDate date = LocalDate.of(1899, 12, 30).plusDays((long) serial);
				return new Parsed<>(date, "Excel date number " + value + " was read as " + date);
			}
		}
		return null;
	}

	/**
	 * Parses a date and time into {@code YYYY-MM-DDThh:mm:ss}. A date without time gets {@code defaultTime}.
	 * Returns {@code null} if the value is not a date and time.
	 */
	public static Parsed<LocalDateTime> dateTime(String text, LocalTime defaultTime) {
		String value = text.strip();
		if (value.isEmpty()) {
			return null;
		}
		try {
			return new Parsed<>(LocalDateTime.parse(value.replace(' ', 'T').replaceAll("(Z|[+-]\\d{2}:\\d{2})$", "")),
					null);
		} catch (DateTimeParseException _) {
			// Not ISO; split into date and time below.
		}
		Parsed<LocalDate> whole = date(value);
		if (whole != null) {
			String note = "No time given; " + defaultTime + " was used.";
			return new Parsed<>(whole.value().atTime(defaultTime), whole.note() != null ? whole.note() + ". " + note : note);
		}
		// Find the split between the date and the time: try every blank (or "T", "at", ",") from the right.
		String[] separators = { " at ", "T", ", ", " " };
		for (String separator : separators) {
			int index = value.lastIndexOf(separator);
			while (index > 0) {
				String datePart = value.substring(0, index).strip().replaceAll(",$", "");
				String timePart = value.substring(index + separator.length()).strip();
				Parsed<LocalDate> date = date(datePart);
				LocalTime time = time(timePart);
				if (date != null && time != null) {
					return new Parsed<>(date.value().atTime(time), date.note());
				}
				index = value.lastIndexOf(separator, index - 1);
			}
		}
		return null;
	}

	/** Parses a time of day ("8:00", "8:00 AM", "8am", "0800"), or {@code null}. */
	public static LocalTime time(String text) {
		String value = text.strip().replace(".", "").replaceAll("(?i)\\s*([ap])m$", " $1M").toUpperCase(Locale.ROOT);
		for (DateTimeFormatter format : TIME_FORMATS) {
			try {
				return LocalTime.parse(value, format);
			} catch (DateTimeParseException _) {
				try {
					return LocalTime.parse(value.replace(" ", ""), format);
				} catch (DateTimeParseException e2) {
					// Try the next format.
				}
			}
		}
		return null;
	}

	/**
	 * Parses a LOM date ({@code YYYY}, {@code YYYY-MM}, {@code YYYY-MM-DD} or with time), accepting the same
	 * forms as {@link #date(String)}. Returns {@code null} if the value is not a date.
	 */
	public static Parsed<String> lomDate(String text) {
		String value = text.strip();
		if (LOM_DATE.matcher(value).matches()) {
			return new Parsed<>(value, null);
		}
		Matcher monthYear = Pattern.compile("(\\d{1,2})/(\\d{4})").matcher(value);
		if (monthYear.matches() && Integer.parseInt(monthYear.group(1)) <= 12) {
			return new Parsed<>(monthYear.group(2) + "-" + "%02d".formatted(Integer.parseInt(monthYear.group(1))),
					null);
		}
		Parsed<LocalDate> date = date(value);
		return date == null ? null : new Parsed<>(date.value().toString(), date.note());
	}

	/**
	 * Converts a duration to ISO 8601: a plain number means minutes; also accepts {@code h:mm}, unit
	 * expressions ("1h30", "1.5 hours", "2 days", "45 min") and ISO durations. Returns {@code null} if the value
	 * is not a duration.
	 */
	public static Parsed<String> isoDuration(String text) {
		String value = text.strip();
		if (value.isEmpty()) {
			return null;
		}
		if (ISO_DURATION.matcher(value).matches()) {
			return new Parsed<>(value.toUpperCase(Locale.ROOT), null);
		}
		Matcher clock = CLOCK_DURATION.matcher(value);
		if (clock.matches()) {
			long seconds = Long.parseLong(clock.group(1)) * 3600 + Long.parseLong(clock.group(2)) * 60
					+ (clock.group(3) != null ? Long.parseLong(clock.group(3)) : 0);
			return new Parsed<>(formatDuration(seconds), null);
		}
		if (NUMBER.matcher(value).matches()) {
			BigDecimal minutes = new BigDecimal(value.replace(',', '.'));
			return new Parsed<>(formatDuration(minutes.multiply(BigDecimal.valueOf(60)).longValue()),
					"\"" + value + "\" was read as minutes");
		}
		String rest = value.toLowerCase(Locale.ROOT).replaceAll("\\b(and|approx\\.?|approximately|about|ca\\.?|~)\\b", " ")
				.replace("~", " ");
		Matcher part = DURATION_PART.matcher(rest);
		double seconds = 0;
		int end = 0;
		String previousUnit = null;
		boolean found = false;
		while (part.find()) {
			if (!rest.substring(end, part.start()).isBlank()) {
				return null;
			}
			double amount = Double.parseDouble(part.group(1).replace(',', '.'));
			String unit = part.group(2);
			if (unit == null) {
				// "1h30": a trailing number after hours means minutes.
				if (!"h".equals(previousUnit)) {
					return null;
				}
				unit = "m";
			}
			char kind = unit.startsWith("mo") ? 'X' : unit.charAt(0);
			switch (kind) {
				case 'w' -> seconds += amount * 7 * 86400;
				case 'd' -> seconds += amount * 86400;
				case 'h' -> seconds += amount * 3600;
				case 'm' -> seconds += amount * 60;
				case 's' -> seconds += amount;
				default -> {
					return null;
				}
			}
			previousUnit = String.valueOf(kind);
			end = part.end();
			found = true;
		}
		if (!found || !rest.substring(end).isBlank()) {
			return null;
		}
		return new Parsed<>(formatDuration(Math.round(seconds)), null);
	}

	/** Formats seconds as an ISO 8601 duration, e.g. {@code PT1H30M} or {@code P2D}. */
	public static String formatDuration(long totalSeconds) {
		if (totalSeconds <= 0) {
			return "PT0M";
		}
		long days = totalSeconds % 86400 == 0 ? totalSeconds / 86400 : 0;
		if (days > 0) {
			return "P" + days + "D";
		}
		long hours = totalSeconds / 3600;
		long minutes = totalSeconds % 3600 / 60;
		long seconds = totalSeconds % 60;
		StringBuilder builder = new StringBuilder("PT");
		if (hours > 0) {
			builder.append(hours).append('H');
		}
		if (minutes > 0) {
			builder.append(minutes).append('M');
		}
		if (seconds > 0) {
			builder.append(seconds).append('S');
		}
		return builder.toString();
	}

	/**
	 * Parses the first decimal number in a value ("1.5", "1,5", "2.25 credits", "$49.00"); a remainder is
	 * reported in the note. Returns {@code null} if there is no number.
	 */
	public static Parsed<BigDecimal> decimal(String text) {
		String value = text.strip();
		Matcher matcher = Pattern.compile("[-+]?\\d{1,3}(,\\d{3})+(\\.\\d+)?|[-+]?\\d+([.,]\\d+)?").matcher(value);
		if (!matcher.find()) {
			return null;
		}
		String number = matcher.group();
		number = number.matches("[-+]?\\d{1,3}(,\\d{3})+(\\.\\d+)?") ? number.replace(",", "")
				: number.replace(',', '.');
		String rest = (value.substring(0, matcher.start()) + value.substring(matcher.end())).replaceAll("[$€£\\s]", "");
		return new Parsed<>(new BigDecimal(number), rest.isEmpty() ? null : rest);
	}

	/** Parses a non-negative whole number ("40", "40x", "1,024"), or {@code null}. */
	public static Parsed<java.math.BigInteger> integer(String text) {
		Parsed<BigDecimal> decimal = decimal(text.replaceAll("(?<=\\d),(?=\\d{3}\\b)", ""));
		if (decimal == null || decimal.value().signum() < 0) {
			return null;
		}
		BigDecimal value = decimal.value();
		java.math.BigInteger integer = value.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact();
		String note = value.stripTrailingZeros().scale() > 0 ? "rounded to " + integer : null;
		return new Parsed<>(integer, note);
	}

	/** Parses a file size ("2.4 MB", "512 KB", "1048576") into bytes, or {@code null}. */
	public static Parsed<Long> size(String text) {
		Matcher matcher = SIZE.matcher(text.strip());
		if (!matcher.matches()) {
			return null;
		}
		double amount = Double.parseDouble(matcher.group(1).replace(',', '.'));
		String unit = matcher.group(2) == null ? "b" : matcher.group(2).toLowerCase(Locale.ROOT);
		double factor = switch (unit.charAt(0)) {
			case 'k' -> 1024;
			case 'm' -> 1024 * 1024;
			case 'g' -> 1024.0 * 1024 * 1024;
			default -> 1;
		};
		long bytes = Math.round(amount * factor);
		return new Parsed<>(bytes, factor == 1 ? null : "\"" + text.strip() + "\" = " + bytes + " bytes");
	}

	/** Normalizes a language tag ("en_US" → "en-US") or name ("English", "Español") to a tag, or {@code null}. */
	public static String language(String text) {
		String value = text.strip();
		if (value.isEmpty()) {
			return null;
		}
		String normalized = TermMatcher.normalize(value);
		String fromName = LANGUAGES.get(normalized);
		if (fromName != null) {
			return fromName;
		}
		// "English (US)", "English - United States"
		Matcher named = Pattern.compile("([\\p{L} ]+?)\\s*[(\\-,]\\s*([\\p{L}. ]+)\\)?").matcher(value);
		if (named.matches()) {
			String language = LANGUAGES.get(TermMatcher.normalize(named.group(1)));
			String country = country(named.group(2));
			if (language != null) {
				return country != null ? language + "-" + country : language;
			}
		}
		if (LANGUAGE_TAG.matcher(value).matches()) {
			String[] parts = value.replace('_', '-').split("-");
			StringBuilder tag = new StringBuilder(parts[0].toLowerCase(Locale.ROOT));
			for (int i = 1; i < parts.length; i++) {
				tag.append('-').append(parts[i].length() == 2 ? parts[i].toUpperCase(Locale.ROOT) : parts[i]);
			}
			return tag.toString();
		}
		return null;
	}

	/** The ISO 3166 alpha-2 code of a country name or code, or {@code null}. */
	public static String country(String text) {
		String value = text.strip();
		if (value.length() == 2 && Character.isLetter(value.charAt(0)) && Character.isLetter(value.charAt(1))) {
			String code = value.toUpperCase(Locale.ROOT);
			for (String iso : Locale.getISOCountries()) {
				if (iso.equals(code)) {
					return code;
				}
			}
			if (code.equals("UK")) {
				return "GB";
			}
		}
		if (value.length() == 3 && value.chars().allMatch(Character::isLetter)) {
			for (String iso : Locale.getISOCountries()) {
				try {
					if (Locale.of("", iso).getISO3Country().equalsIgnoreCase(value)) {
						return iso;
					}
				} catch (java.util.MissingResourceException _) {
					// No alpha-3 code.
				}
			}
		}
		return COUNTRIES.get(TermMatcher.normalize(value));
	}

	/** The English name of an ISO 3166 alpha-2 country code. */
	public static String countryName(String code) {
		return Locale.of("", code).getDisplayCountry(Locale.ENGLISH);
	}

	/**
	 * Normalizes a URL: adds {@code https://} to {@code www.} addresses and encodes blanks. Returns
	 * {@code null} if the value is not an absolute URI.
	 */
	public static Parsed<String> url(String text) {
		String value = text.strip();
		String note = null;
		if (value.regionMatches(true, 0, "www.", 0, 4)) {
			value = "https://" + value;
			note = "\"https://\" was added";
		}
		if (value.contains(" ")) {
			value = value.replace(" ", "%20");
			note = note == null ? "blanks were encoded as %20" : note + ", blanks were encoded as %20";
		}
		try {
			URI uri = new URI(value);
			if (uri.getScheme() == null || uri.getScheme().length() < 2) {
				return null;
			}
			return new Parsed<>(value, note);
		} catch (URISyntaxException _) {
			return null;
		}
	}

	/** Whether the value looks like a URL ({@code scheme://...} or {@code www.}). */
	public static boolean looksLikeUrl(String text) {
		String value = text.strip().toLowerCase(Locale.ROOT);
		return value.matches("[a-z][a-z0-9+.-]*://.+") || value.startsWith("www.") || value.startsWith("mailto:")
				|| value.startsWith("urn:");
	}
}
