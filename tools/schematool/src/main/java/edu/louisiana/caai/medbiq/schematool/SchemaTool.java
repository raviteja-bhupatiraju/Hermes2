/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.schematool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;

/**
 * Converts the published MedBiquitous XML Schemas to Ecore models.
 * <ol>
 * <li>Copies the root schemas of every published standard version and all schemas they import or include
 * (closure) from the mirror {@code xsd/mirror/} to {@code bundles/edu.louisiana.caai.medbiq.model/schemas/},
 * keeping the host/path layout of their URLs (so {@code http://ns.medbiq.org/x/y.xsd} is
 * {@code schemas/ns.medbiq.org/x/y.xsd}).</li>
 * <li>Builds the Ecore packages of all of them in one run of the EMF XSD importer, so each namespace has exactly
 * one package, and writes one {@code model/<prefix>.ecore} per namespace.</li>
 * <li>The namespaces of Healthcare LOM v1 already have generated packages ({@code edu.louisiana.caai.lom.model});
 * their new packages are dropped and references to them are redirected to the generated packages'
 * {@code .ecore} models (by namespace URI, so they resolve to the generated packages at run time).</li>
 * </ol>
 */
public final class SchemaTool {

	/** The root schemas of the published standard versions (relative to ns.medbiq.org). */
	static final List<String> ROOTS = List.of(
			"activityreport/v1/activityreport.xsd",
			"activityreport/v2/activityreport.xsd",
			"competencyframework/v1/competencyframework.xsd",
			"competencyobject/v1/competencyobject.xsd",
			"curriculuminventory/v1/curriculuminventory.xsd",
			"curriculuminventory/v10/curriculuminventory.xsd",
			// Pending: Disclosure v2 imports common/v3 and Educational Achievement v1 the PESC CoreMain 1.12.0 and
			// AcademicRecord 1.7.0 schemas, none of which is published any more.
			// "financialinterest/v2/financialinterest.xsd",
			// "educationalachievement/v1/educationalachievement.xsd",
			"metrics/v1/medicaleducationmetrics.xsd",
			"surveyitems/v1/surveyitems.xsd",
			"member/v1/member.xsd",
			"performanceframework/v1/performanceframework.xsd",
			"virtualpatientdata/v1/virtualpatientdata.xsd",
			"activitymodel/v1/activitymodel.xsd",
			"dataavailabilitymodel/v1/dataavailabilitymodel.xsd");

	/** URL prefixes of the mirror folders. */
	static final Map<String, String> HOSTS = new LinkedHashMap<>();

	static {
		HOSTS.put("http://ns.medbiq.org/", "ns.medbiq.org/");
		HOSTS.put("https://ns.medbiq.org/", "ns.medbiq.org/");
		HOSTS.put("http://www.w3.org/", "www.w3.org/");
		HOSTS.put("http://www.dublincore.org/schemas/xmls/qdc/", "www.dublincore.org/schemas/xmls/qdc/");
		HOSTS.put("https://www.dublincore.org/schemas/xmls/qdc/", "www.dublincore.org/schemas/xmls/qdc/");
		HOSTS.put("http://dublincore.org/schemas/xmls/qdc/", "www.dublincore.org/schemas/xmls/qdc/");
	}

	/** The namespaces of the generated Healthcare LOM packages, with their .ecore file. */
	static final Map<String, String> HLOM = Map.of(
			"http://ltsc.ieee.org/xsd/LOM", "lom.ecore",
			"http://ns.medbiq.org/lom/extend/v1/", "healthcare.ecore",
			"http://ns.medbiq.org/lom/vocab/v1/", "healthcarevocab.ecore",
			"http://ltsc.ieee.org/xsd/LOM/vocab", "vocab.ecore",
			"http://ns.medbiq.org/address/v1/", "address.ecore");

	private static final Pattern LOCATION = Pattern.compile("schemaLocation\\s*=\\s*[\"']([^\"']+)[\"']");

	private final Path mirror;
	private final Path schemas;
	private final Path models;
	private final Path hlomModels;

	SchemaTool(Path repository) {
		mirror = repository.resolve("xsd/mirror");
		Path bundle = repository.resolve("bundles/edu.louisiana.caai.medbiq.model");
		schemas = bundle.resolve("schemas");
		models = bundle.resolve("model");
		hlomModels = repository.resolve("bundles/edu.louisiana.caai.lom.model/model");
	}

	public static void main(String[] args) throws Exception {
		new SchemaTool(Path.of(args.length > 0 ? args[0] : ".").toAbsolutePath().normalize()).run();
	}

	void run() throws Exception {
		Map<String, Path> copied = copySchemas();
		System.out.println("Copied " + copied.size() + " schemas to " + schemas);
		buildModels(copied);
	}

	// ----------------------------------------------------------------------------------------------------
	// Schemas
	// ----------------------------------------------------------------------------------------------------

	/** Copies the closure of the root schemas; returns the copied files by URL. */
	Map<String, Path> copySchemas() throws IOException {
		Map<String, Path> copied = new TreeMap<>();
		List<String> pending = new ArrayList<>();
		for (String root : ROOTS) {
			pending.add("http://ns.medbiq.org/" + root);
		}
		Set<String> missing = new LinkedHashSet<>();
		while (!pending.isEmpty()) {
			String url = normalize(pending.removeLast());
			if (copied.containsKey(url) || missing.contains(url)) {
				continue;
			}
			String relative = relative(url);
			Path source = relative == null ? null : mirror.resolve(relative);
			if (source == null || !Files.isRegularFile(source)) {
				missing.add(url);
				continue;
			}
			Path target = schemas.resolve(relative);
			Files.createDirectories(target.getParent());
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			copied.put(url, target);
			String text = Files.readString(source);
			Matcher matcher = LOCATION.matcher(text);
			while (matcher.find()) {
				String location = matcher.group(1).strip();
				if (location.contains(" ")) {
					continue; // xsi:schemaLocation pairs in documentation.
				}
				pending.add(java.net.URI.create(url).resolve(location).toString());
			}
		}
		missing.forEach(url -> System.out.println("MISSING " + url));
		return copied;
	}

	static String normalize(String url) {
		return url.replace("https://ns.medbiq.org/", "http://ns.medbiq.org/").replace("/./", "/");
	}

	static String relative(String url) {
		for (Map.Entry<String, String> host : HOSTS.entrySet()) {
			if (url.startsWith(host.getKey())) {
				return host.getValue() + url.substring(host.getKey().length());
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------------------------------
	// Ecore
	// ----------------------------------------------------------------------------------------------------

	void buildModels(Map<String, Path> copied) throws IOException {
		XSDPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xsd", new XSDResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		for (Map.Entry<String, String> host : HOSTS.entrySet()) {
			URIConverter.URI_MAP.put(URI.createURI(host.getKey()),
					URI.createFileURI(schemas.resolve(host.getValue()).toString() + "/"));
		}

		XSDEcoreBuilder builder = new XSDEcoreBuilder();
		List<URI> roots = new ArrayList<>();
		for (String root : ROOTS) {
			String url = "http://ns.medbiq.org/" + root;
			if (copied.containsKey(url)) {
				roots.add(URI.createURI(url));
			}
		}
		Collection<Object> generated = builder.generate(roots);
		for (Object diagnostic : builder.getDiagnostics() == null ? List.of() : builder.getDiagnostics()) {
			if (diagnostic instanceof org.eclipse.xsd.XSDDiagnostic d
					&& d.getSeverity() == org.eclipse.xsd.XSDDiagnosticSeverity.FATAL_LITERAL) {
				System.out.println("SCHEMA " + d.getSeverity() + " " + d.getMessage() + " " + d.getLocationURI());
			}
		}

		ResourceSet output = new ResourceSetImpl();
		// The generated HLOM packages, by namespace (their resources have the namespace as URI).
		Map<String, EPackage> hlom = new HashMap<>();
		for (Map.Entry<String, String> entry : HLOM.entrySet()) {
			Resource resource = new EcoreResourceFactoryImpl().createResource(URI.createURI(entry.getKey()));
			output.getResources().add(resource);
			resource.load(Files.newInputStream(hlomModels.resolve(entry.getValue())), Map.of());
			hlom.put(entry.getKey(), (EPackage) resource.getContents().getFirst());
		}

		List<EPackage> packages = new ArrayList<>();
		for (Object object : generated) {
			switch (object) {
				case EPackage ePackage -> packages.add(ePackage);
				case Resource resource -> resource.getContents().stream().filter(EPackage.class::isInstance)
						.map(EPackage.class::cast).forEach(packages::add);
				default -> System.out.println("Ignored " + object);
			}
		}
		// Redirect references into HLOM namespaces to the generated HLOM packages.
		int redirected = 0;
		List<String> unresolved = new ArrayList<>();
		for (EPackage ePackage : packages) {
			if (hlom.containsKey(ePackage.getNsURI())) {
				continue;
			}
			for (var iterator = ePackage.eAllContents(); iterator.hasNext();) {
				EObject object = iterator.next();
				for (EReference reference : object.eClass().getEAllReferences()) {
					if (reference.isContainment() || reference.isDerived() || !reference.isChangeable()) {
						continue;
					}
					if (reference.isMany()) {
						@SuppressWarnings("unchecked")
						List<Object> values = (List<Object>) object.eGet(reference);
						for (int i = 0; i < values.size(); i++) {
							Object replacement = hlomTarget(values.get(i), hlom, unresolved);
							if (replacement != null) {
								values.set(i, replacement);
								redirected++;
							}
						}
					} else {
						Object replacement = hlomTarget(object.eGet(reference), hlom, unresolved);
						if (replacement != null) {
							object.eSet(reference, replacement);
							redirected++;
						}
					}
				}
			}
		}
		System.out.println("Redirected " + redirected + " references to the generated HLOM packages");
		unresolved.stream().distinct().forEach(name -> System.out.println("UNRESOLVED HLOM " + name));

		// Unions: EMF reads a value with the first member type that accepts it and validates it against the member
		// types it is an instance of; with a pattern (string) member first, values of enumeration members would be
		// read as strings and fail the pattern. Enumeration members go first.
		int unions = 0;
		for (EPackage ePackage : packages) {
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				if (classifier instanceof org.eclipse.emf.ecore.EDataType dataType) {
					var meta = org.eclipse.emf.ecore.util.ExtendedMetaData.INSTANCE;
					List<org.eclipse.emf.ecore.EDataType> members = new ArrayList<>(meta.getMemberTypes(dataType));
					if (members.size() > 1) {
						List<org.eclipse.emf.ecore.EDataType> ordered = new ArrayList<>(members);
						ordered.sort(java.util.Comparator.comparingInt(t -> isEnumeration(t, meta) ? 0 : 1));
						if (!ordered.equals(members)) {
							meta.setMemberTypes(dataType, ordered);
							unions++;
						}
					}
				}
			}
		}
		System.out.println("Reordered the member types of " + unions + " unions");

		Files.createDirectories(models);
		Map<EPackage, String> files = new LinkedHashMap<>();
		Set<String> names = new java.util.HashSet<>();
		for (EPackage ePackage : packages) {
			if (hlom.containsKey(ePackage.getNsURI())) {
				continue;
			}
			String name = fileName(ePackage.getNsURI());
			if (!names.add(name)) {
				name = name + "-" + names.size();
			}
			files.put(ePackage, name);
			ePackage.setName(name.replaceAll("[^A-Za-z0-9]", ""));
			ePackage.setNsPrefix(prefix(ePackage.getNsURI()));
		}
		for (Map.Entry<EPackage, String> entry : files.entrySet()) {
			Resource resource = new EcoreResourceFactoryImpl()
					.createResource(URI.createFileURI(models.resolve(entry.getValue() + ".ecore").toString()));
			output.getResources().add(resource);
			resource.getContents().add(entry.getKey());
		}
		Map<Object, Object> options = new HashMap<>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		options.put(XMLResource.OPTION_LINE_WIDTH, 120);
		StringBuilder index = new StringBuilder("# Generated by tools/schematool: <model file> <namespace URI>\n");
		for (Resource resource : List.copyOf(output.getResources())) {
			if (resource.getURI().isFile()) {
				index.append(resource.getURI().lastSegment()).append(' ')
						.append(((EPackage) resource.getContents().getFirst()).getNsURI()).append('\n');
				resource.save(options);
				EPackage ePackage = (EPackage) resource.getContents().getFirst();
				System.out.println("Wrote " + resource.getURI().lastSegment() + "  " + ePackage.getNsURI() + "  ("
						+ ePackage.getEClassifiers().size() + " classifiers)");
			}
		}
		Files.writeString(models.resolve("index.txt"), index.toString());
	}

	private static boolean isEnumeration(org.eclipse.emf.ecore.EDataType type,
			org.eclipse.emf.ecore.util.ExtendedMetaData meta) {
		return type instanceof org.eclipse.emf.ecore.EEnum || !meta.getEnumerationFacet(type).isEmpty()
				|| meta.getBaseType(type) instanceof org.eclipse.emf.ecore.EEnum;
	}

	/** The classifier of a generated HLOM package replacing a classifier of a new HLOM-namespace package. */
	private static Object hlomTarget(Object value, Map<String, EPackage> hlom, List<String> unresolved) {
		if (!(value instanceof EClassifier classifier) || classifier.getEPackage() == null) {
			return null;
		}
		EPackage generated = hlom.get(classifier.getEPackage().getNsURI());
		if (generated == null || generated == classifier.getEPackage()) {
			return null;
		}
		EClassifier target = generated.getEClassifier(classifier.getName());
		if (target == null) {
			unresolved.add(classifier.getEPackage().getNsURI() + "#" + classifier.getName());
		}
		return target;
	}

	/** A file name for a namespace: {@code http://ns.medbiq.org/curriculuminventory/v10/} -> {@code curriculuminventory-v10}. */
	static String fileName(String nsUri) {
		String name = nsUri.replaceFirst("^[a-z]+:(//)?", "").replaceFirst("^ns\\.medbiq\\.org/", "")
				.replaceFirst("^www\\.", "").replaceAll("/+$", "");
		return name.replaceAll("[^A-Za-z0-9.]+", "-").toLowerCase(java.util.Locale.ROOT);
	}

	/** A conventional prefix for a namespace. */
	static String prefix(String nsUri) {
		String name = fileName(nsUri);
		String[] parts = name.split("-");
		String base = parts[0].replace("purl.org", "dc").replace(".", "");
		return switch (base) {
			case "activityreport" -> "ar";
			case "competencyframework" -> "cf";
			case "competencyobject" -> "co";
			case "curriculuminventory" -> "ci";
			case "disclosure" -> "fi";
			case "educationalachievement" -> "ea";
			case "metrics" -> "mems";
			case "surveyitem" -> "si";
			case "member" -> "m";
			case "name" -> "n";
			case "common" -> "mbq";
			case "performanceframework" -> "pf";
			case "virtualpatientdata" -> "vpd";
			case "activitymodel" -> "am";
			case "dataavailabilitymodel" -> "dam";
			default -> base.length() > 8 ? base.substring(0, 8) : base;
		};
	}
}
