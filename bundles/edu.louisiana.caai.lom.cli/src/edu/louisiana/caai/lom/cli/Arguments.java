/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Parsed command line arguments: options ({@code --name value}, {@code --name=value} or flags {@code --name}) and
 * operands. {@code --} ends the options.
 *
 * @param options  the options by name (without dashes); flags have the value {@code "true"}
 * @param operands the other arguments in order
 */
public record Arguments(Map<String, String> options, List<String> operands) {

	/** Thrown for invalid command lines; the message is shown with the usage. */
	public static final class UsageException extends Exception {
		private static final long serialVersionUID = 1L;

		public UsageException(String message) {
			super(message);
		}
	}

	/**
	 * Parses arguments.
	 *
	 * @param args    the arguments after the command name
	 * @param flags   the options without a value
	 * @param valued  the options with a value
	 */
	public static Arguments parse(List<String> args, Set<String> flags, Set<String> valued) throws UsageException {
		Map<String, String> options = new LinkedHashMap<>();
		List<String> operands = new ArrayList<>();
		boolean onlyOperands = false;
		for (int i = 0; i < args.size(); i++) {
			String arg = args.get(i);
			if (onlyOperands || !arg.startsWith("-") || arg.equals("-")) {
				operands.add(arg);
				continue;
			}
			if (arg.equals("--")) {
				onlyOperands = true;
				continue;
			}
			String name = arg.replaceFirst("^--?", "");
			String value = null;
			int equals = name.indexOf('=');
			if (equals >= 0) {
				value = name.substring(equals + 1);
				name = name.substring(0, equals);
			}
			if (flags.contains(name)) {
				if (value != null) {
					throw new UsageException("Option --" + name + " takes no value.");
				}
				options.put(name, "true");
			} else if (valued.contains(name)) {
				if (value == null) {
					if (i + 1 >= args.size()) {
						throw new UsageException("Option --" + name + " needs a value.");
					}
					value = args.get(++i);
				}
				options.put(name, value);
			} else {
				throw new UsageException("Unknown option " + arg + ".");
			}
		}
		return new Arguments(Map.copyOf(options), List.copyOf(operands));
	}

	/** Whether a flag is set. */
	public boolean has(String name) {
		return options.containsKey(name);
	}

	/** The value of an option, or a default. */
	public String get(String name, String defaultValue) {
		return options.getOrDefault(name, defaultValue);
	}

	/** The value of an option that must be one of the given choices (case-insensitive), or the default. */
	public String choice(String name, String defaultValue, String... choices) throws UsageException {
		String value = get(name, defaultValue);
		for (String choice : choices) {
			if (choice.equalsIgnoreCase(value)) {
				return choice;
			}
		}
		throw new UsageException("--" + name + " must be one of " + String.join(", ", choices) + " (not \"" + value
				+ "\").");
	}
}
