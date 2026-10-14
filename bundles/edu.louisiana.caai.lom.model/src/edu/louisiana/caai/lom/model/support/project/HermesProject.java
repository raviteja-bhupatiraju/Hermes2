/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.model.support.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.json.Json;
import edu.louisiana.caai.lom.model.support.report.HlomLibrary;

/**
 * A project: a folder of HLOM documents (XML and JSON) with a project file, {@value #PROJECT_FILE}, holding the
 * project's name, description and the format of new documents. The documents of a project are all HLOM
 * documents below its folder (hidden folders and build output are skipped), so files added outside of the
 * application belong to the project as well.
 * <p>
 * OSGi-free, so that it can be used by tests and command line tools.
 */
public final class HermesProject {

	/** Name of the project file in the project folder. */
	public static final String PROJECT_FILE = "hermes-project.json";

	/** Format of new documents. */
	public enum Format {
		XML(".xml"), JSON(".json");

		private final String extension;

		Format(String extension) {
			this.extension = extension;
		}

		/** The file extension, with the dot. */
		public String extension() {
			return extension;
		}

		/** The format of a file, by its extension. */
		public static Format of(Path file) {
			String name = file.getFileName() == null ? "" : file.getFileName().toString().toLowerCase(Locale.ROOT);
			return name.endsWith(".json") ? JSON : XML;
		}
	}

	private final Path folder;
	private String name;
	private String description;
	private Format format;
	private String created;

	private HermesProject(Path folder, String name, String description, Format format, String created) {
		this.folder = folder.toAbsolutePath().normalize();
		this.name = name;
		this.description = description;
		this.format = format;
		this.created = created;
	}

	/** Whether a folder is a project folder (it has a project file). */
	public static boolean isProject(Path folder) {
		return Files.isRegularFile(folder.resolve(PROJECT_FILE));
	}

	/**
	 * Creates a project in a folder (created if needed) and writes its project file. Documents already in the
	 * folder become documents of the project.
	 */
	public static HermesProject create(Path folder, String name, String description, Format format)
			throws IOException {
		Files.createDirectories(folder);
		HermesProject project = new HermesProject(folder, name == null || name.isBlank() ? defaultName(folder) : name.strip(),
				description == null ? "" : description.strip(), format == null ? Format.XML : format,
				LocalDate.now().toString());
		project.save();
		return project;
	}

	/**
	 * Opens the project in a folder. A folder without project file is opened as a project named after the folder
	 * (the project file is written on the first {@link #save()}).
	 */
	public static HermesProject open(Path folder) throws IOException {
		if (!Files.isDirectory(folder)) {
			throw new IOException("The project folder " + folder + " does not exist.");
		}
		HermesProject project = new HermesProject(folder, defaultName(folder), "", Format.XML, null);
		Path file = folder.resolve(PROJECT_FILE);
		if (Files.isRegularFile(file)) {
			try {
				if (Json.parse(Files.readString(file, StandardCharsets.UTF_8)) instanceof Json.JObject object) {
					project.name = string(object, "name", project.name);
					project.description = string(object, "description", "");
					project.format = "json".equalsIgnoreCase(string(object, "format", "xml")) ? Format.JSON : Format.XML;
					project.created = string(object, "created", null);
				}
			} catch (Json.JsonException e) {
				throw new IOException("The project file " + file + " is not valid JSON: " + e.getMessage(), e);
			}
		}
		return project;
	}

	private static String string(Json.JObject object, String member, String fallback) {
		return object.get(member) instanceof Json.JString value && !value.value().isBlank() ? value.value() : fallback;
	}

	private static String defaultName(Path folder) {
		Path fileName = folder.toAbsolutePath().normalize().getFileName();
		return fileName == null ? folder.toString() : fileName.toString();
	}

	/** Writes the project file. */
	public void save() throws IOException {
		Json.JObject object = new Json.JObject();
		object.put("name", name);
		object.put("description", description);
		object.put("format", format == Format.JSON ? "json" : "xml");
		if (created != null) {
			object.put("created", created);
		}
		Files.writeString(folder.resolve(PROJECT_FILE), Json.write(object), StandardCharsets.UTF_8);
	}

	public Path getFolder() {
		return folder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null || name.isBlank() ? defaultName(folder) : name.strip();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? "" : description.strip();
	}

	/** The format of new documents. */
	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format == null ? Format.XML : format;
	}

	/** Whether a path is inside the project folder. */
	public boolean contains(Path path) {
		return path.toAbsolutePath().normalize().startsWith(folder);
	}

	/** The HLOM documents of the project, sorted by path. */
	public List<Path> documents() throws IOException {
		List<Path> result = new ArrayList<>();
		for (Path file : HlomLibrary.findCandidates(folder, () -> false)) {
			if (!isProjectFile(file) && HlomLibrary.isHlomDocument(file)) {
				result.add(file);
			}
		}
		return result;
	}

	private boolean isProjectFile(Path file) {
		return file.getFileName() != null && PROJECT_FILE.equals(file.getFileName().toString())
				&& folder.equals(file.toAbsolutePath().normalize().getParent());
	}

	/**
	 * Copies documents into a folder of the project. A file whose name is taken gets a numbered name
	 * ({@code name-2.xml}).
	 *
	 * @return the copies
	 */
	public List<Path> importFiles(List<Path> files, Path targetFolder) throws IOException {
		Path target = targetFolder == null ? folder : targetFolder;
		if (!contains(target)) {
			throw new IOException(target + " is not inside the project folder.");
		}
		Files.createDirectories(target);
		List<Path> copies = new ArrayList<>();
		for (Path file : files) {
			Path copy = uniqueFile(target, file.getFileName().toString());
			Files.copy(file, copy, StandardCopyOption.COPY_ATTRIBUTES);
			copies.add(copy);
		}
		return copies;
	}

	/**
	 * Converts a document to the other format: writes {@code name.json} for an XML document and {@code name.xml}
	 * for a JSON document next to it, and deletes the original. The document must be readable.
	 *
	 * @return the converted file
	 */
	public static Path convert(Path file, Format target) throws IOException {
		if (Format.of(file) == target) {
			return file;
		}
		Resource resource = HlomResources.load(URI.createFileURI(file.toAbsolutePath().toString()));
		if (!resource.getErrors().isEmpty() || HlomResources.getDocumentRoot(resource) == null) {
			throw new IOException(file.getFileName() + " has errors and cannot be converted; open and repair it first.");
		}
		String fileName = file.getFileName().toString();
		int dot = fileName.lastIndexOf('.');
		String base = dot > 0 ? fileName.substring(0, dot) : fileName;
		Path converted = uniqueFile(file.getParent(), base + target.extension());
		resource.setURI(URI.createFileURI(converted.toAbsolutePath().toString()));
		resource.save(HlomResources.saveOptions());
		Files.delete(file);
		return converted;
	}

	/** A file in a folder with the given name, or a numbered variant of it if the name is taken. */
	public static Path uniqueFile(Path folder, String fileName) {
		Path candidate = folder.resolve(fileName);
		if (!Files.exists(candidate)) {
			return candidate;
		}
		int dot = fileName.lastIndexOf('.');
		String base = dot > 0 ? fileName.substring(0, dot) : fileName;
		String extension = dot > 0 ? fileName.substring(dot) : "";
		for (int i = 2;; i++) {
			candidate = folder.resolve(base + "-" + i + extension);
			if (!Files.exists(candidate)) {
				return candidate;
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof HermesProject project && project.folder.equals(folder);
	}

	@Override
	public int hashCode() {
		return folder.hashCode();
	}

	@Override
	public String toString() {
		return name + " (" + folder + ")";
	}
}
