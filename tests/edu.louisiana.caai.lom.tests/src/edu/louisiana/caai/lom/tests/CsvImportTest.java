/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.lom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.louisiana.caai.lom.model.healthcare.ActivityDeliveryType;
import edu.louisiana.caai.lom.model.healthcare.AudienceCategoryType;
import edu.louisiana.caai.lom.model.healthcare.CreditTypeType;
import edu.louisiana.caai.lom.model.healthcare.CreditUnitType;
import edu.louisiana.caai.lom.model.healthcare.HealthcareEducationType;
import edu.louisiana.caai.lom.model.healthcare.MedicalImageTypeType;
import edu.louisiana.caai.lom.model.healthcare.OrientationType;
import edu.louisiana.caai.lom.model.healthcare.PacingType;
import edu.louisiana.caai.lom.model.healthcare.ParticipationModalityType;
import edu.louisiana.caai.lom.model.healthcare.YesNoType;
import edu.louisiana.caai.lom.model.lom.DocumentRoot;
import edu.louisiana.caai.lom.model.lom.General;
import edu.louisiana.caai.lom.model.lom.Keyword;
import edu.louisiana.caai.lom.model.lom.Lom;
import edu.louisiana.caai.lom.model.support.HlomProblem;
import edu.louisiana.caai.lom.model.support.HlomProblem.Severity;
import edu.louisiana.caai.lom.model.support.HlomResources;
import edu.louisiana.caai.lom.model.support.HlomTemplates;
import edu.louisiana.caai.lom.model.support.HlomValidator;
import edu.louisiana.caai.lom.model.support.Vocabularies;
import edu.louisiana.caai.lom.model.support.importer.ColumnMapping;
import edu.louisiana.caai.lom.model.support.importer.CsvImporter;
import edu.louisiana.caai.lom.model.support.importer.CsvReader;
import edu.louisiana.caai.lom.model.support.importer.CsvTable;
import edu.louisiana.caai.lom.model.support.importer.ImportField;
import edu.louisiana.caai.lom.model.support.importer.ImportMessage;
import edu.louisiana.caai.lom.model.support.importer.ImportedRecord;
import edu.louisiana.caai.lom.model.support.importer.ValueParsers;

/**
 * The CSV import: RFC 4180 parsing, header auto-mapping, value interpretation, record generation and writing.
 */
class CsvImportTest {

	/** A copy of {@code samples/import/courses.csv}. */
	static final String SAMPLE = """
Title,Description,Keywords,Language,Course Code,Version,Status,Authors,Publisher,Published,Format,URL,Resource Type,Context,Audience,Professions,Specialties,Reading Level,Learning Time,Cost,License,Credits Available,Credit Type,Credits,Credit Unit,Accrediting Body,Certification,Accredited Provider,Pacing,Expiration Date,Start,End,Modality,Delivery,Venue,City,State,Country,Contact,Image Type,Orientation,Image Size,Learning Objectives
Sepsis Early Recognition and the Hour-1 Bundle,"Case-based online module on recognizing sepsis early and starting the Surviving Sepsis Campaign hour-1 bundle.
Includes two branching patient cases and a 10-question post-test.","Sepsis [MeSH:D018805]; Shock, Septic [MeSH:D012772]; Early Diagnosis [MeSH:D042241]; triage",English,CAAI:CME-2026-014,2.0,published,"Dr. Amelia Broussard <abroussard@example.org>; Marc Thibodeaux, RN",UL Lafayette College of Nursing & Allied Health,2026-03-01,html,https://learn.example.org/courses/sepsis-hour-1,case study; self assessment,CME,professionals,physician; registered nurse; NP,emergency medicine; critical care,,90 min,free,CC BY-NC 4.0,yes,CME; CNE,1.5; 1.5,Credit; Contact Hour,ACCME; ANCC,AMA PRA Category 1,Acadiana Office of Continuing Medical Education,self-paced,03/31/2027,,,online,enduring,,,,,cme@example.org,,,,Recognize the SIRS and qSOFA criteria; Start the hour-1 bundle within 60 minutes; Reassess lactate and fluid response
Point-of-Care Ultrasound Skills Workshop,"Hands-on, small-group workshop: cardiac, lung and FAST views on live models and simulators.",Ultrasonography [MeSH:D014463]; Point-of-Care Systems [MeSH:D019095]; POCUS,en-US,CAAI:WS-2026-07,1.0,final,Dr. Nadia Fontenot; Dr. Luis Romero,Acadiana Simulation Consortium,2026-08-15,in person,https://events.example.org/pocus-2026,exercise; simulation,CME,professional,physician; physician assistant,emergency medicine; family medicine; internal medicine,,7 hours,yes,All rights reserved,yes,CME,7,credits,ACCME,AMA PRA Category 1,Acadiana Office of Continuing Medical Education,provider paced,2026-11-14,2026-11-14 08:00,2026-11-14 5:00 PM,in-person workshop,live,Lourdes Simulation Center,Lafayette,LA,USA,"Workshop desk, (337) 555-0142",,,,Acquire the parasternal long-axis view; Identify free fluid on the FAST exam
Chest Radiograph: Right Middle Lobe Pneumonia,"PA chest radiograph of a 54-year-old with fever and productive cough, showing right middle lobe consolidation silhouetting the right heart border.","Pneumonia [MeSH:D011014]; Radiography, Thoracic [MeSH:D013902]",en,https://images.example.org/chest/rml-pneumonia-001.jpg,1.0,final,Dr. Grace Hebert,UL Lafayette Department of Radiology,2025-11,jpg,https://images.example.org/chest/rml-pneumonia-001.jpg,photo,graduate medical education,professional,medical imaging professional; physician,radiology,,5,no,Licensed for teaching use only,no,,,,,,,,,,,,,,,,,radiology-teaching@example.org,x-ray,PA,2048x2500,Recognize the silhouette sign of right middle lobe consolidation
Managing Type 2 Diabetes at Home (Spanish Edition),"Plain-language booklet for adults newly diagnosed with type 2 diabetes: checking blood sugar, healthy plates and when to call the clinic.","Diabetes Mellitus, Type 2 [MeSH:D003924]; Self Care [MeSH:D012648]; blood sugar",Spanish,ISBN 978-1-55555-012-3,3.1,revised,Acadiana Diabetes Education Team,Lafayette General Community Health,2026-01-20,PDF,https://health.example.org/handouts/diabetes-at-home-es.pdf,narrative text,patient education,patients; family,,,6th grade,20,free,Free to copy for patient use,no,,,,,,,,,,,print,not live,,,,,diabetes-ed@example.org,,,,Check blood sugar at home; Build a healthy plate
Opioid Stewardship for Pharmacists,"Self-paced activity on safe opioid prescribing support, naloxone co-dispensing and PDMP use in community pharmacy.","Analgesics, Opioid [MeSH:D000701]; Opioid-Related Disorders [MeSH:D009293]; Naloxone [MeSH:D009270]",en-US,ACPE:0000-9999-26-041-H08-P,1.0,draft,Kelsey Arceneaux <karceneaux@example.org>,Louisiana Pharmacy Education Network,2026-05-05,video; pdf,https://learn.example.org/courses/opioid-stewardship,video; self assessment,continuing education,professional,pharmacist; pharmacy technician,,,2h,$25,All rights reserved,yes,CPE,2,contact hours,ACPE,,Louisiana Pharmacy Education Network,learner-paced,2028-05-04,,,online,on demand,,,,,ce@example.org,,,,Describe the CDC opioid prescribing recommendations; Counsel patients on naloxone
Pediatric Asthma Action Plans: A Case-Based Review,"Video grand rounds with three pediatric asthma cases, from stepwise therapy to writing an action plan families can follow.",Asthma [MeSH:D001249]; Child [MeSH:D002648]; action plan,en-US,CAAI:CME-2026-022,1.0,final,Dr. Simone Guidry; Dr. Ethan Landry,Children's Hospital of Acadiana,2026-09-10,mp4,https://learn.example.org/grand-rounds/peds-asthma,lecture; video,CME,professional,physician; nurse practitioner; registered nurse,pediatrics; pulmonology; allergy,,1:00,free,CC BY-NC-ND 4.0,yes,CME; CNE,1; 1,Credit; Contact Hour,ACCME; ANCC,AMA PRA Category 1,Children's Hospital of Acadiana,self paced,2027-09-09,,,web,recorded,,,,,peds-cme@example.org,,,,Classify asthma severity in children; Write a three-zone asthma action plan
""";

	private static CsvTable sample() {
		return CsvReader.read(SAMPLE.getBytes(StandardCharsets.UTF_8), null, null);
	}

	private static List<ImportedRecord> importSample() {
		CsvTable table = sample();
		return new CsvImporter(table, ColumnMapping.auto(table.headers()), HlomTemplates.BLANK).preview();
	}

	private static String describe(ImportedRecord record) {
		return record + record.describeIssues().stream().collect(Collectors.joining("\n  ", "\n  ", ""));
	}

	// ---------------------------------------------------------------------------------------------------------
	// CSV reading

	@Test
	void parsesQuotedFieldsWithDelimitersQuotesAndLineBreaks() {
		String text = "﻿Name,Note,Count\r\n\"Smith, Jane\",\"She said \"\"hi\"\"\nand left\",3\r\n\r\n,,\r\nLast,,\n";
		CsvTable table = CsvReader.parse(text, ',');
		assertEquals(List.of("Name", "Note", "Count"), table.headers());
		assertEquals(2, table.rowCount(), "blank rows are skipped");
		assertEquals("Smith, Jane", table.cell(0, 0));
		assertEquals("She said \"hi\"\nand left", table.cell(0, 1));
		assertEquals("3", table.cell(0, 2));
		assertEquals("Last", table.cell(1, 0));
		assertEquals("", table.cell(1, 5), "missing cells are empty");
	}

	@Test
	void detectsDelimiters() {
		assertEquals(';', CsvReader.detectDelimiter("Title;Credits;Note\nA;1,5;\"x;y\"\nB;2;z\n"));
		assertEquals('\t', CsvReader.detectDelimiter("Title\tCredits\nA, B\t1\nC\t2\n"));
		assertEquals(',', CsvReader.detectDelimiter("Title,Credits\n\"A; B\",1\n"));
		assertEquals('|', CsvReader.detectDelimiter("a|b|c\n1|2|3\n"));
	}

	@Test
	void detectsCharsets() {
		byte[] ansi = "Title\nCafé au lait\n".getBytes(CsvReader.WINDOWS_1252);
		assertEquals(CsvReader.WINDOWS_1252, CsvReader.detectCharset(ansi));
		assertEquals("Café au lait", CsvReader.read(ansi, null, null).cell(0, 0));
		byte[] utf8 = "﻿Title\nCafé\n".getBytes(StandardCharsets.UTF_8);
		CsvTable table = CsvReader.read(utf8, null, null);
		assertEquals(StandardCharsets.UTF_8, table.charset());
		assertEquals("Title", table.headers().getFirst(), "the byte order mark is dropped");
		byte[] utf16 = "﻿Title;X\nA;B\n".getBytes(StandardCharsets.UTF_16LE);
		CsvTable wide = CsvReader.read(utf16, null, null);
		assertEquals(';', wide.delimiter());
		assertEquals("B", wide.cell(0, 1));
	}

	// ---------------------------------------------------------------------------------------------------------
	// Mapping

	@Test
	void mapsEverySampleHeader() {
		CsvTable table = sample();
		ColumnMapping mapping = ColumnMapping.auto(table.headers());
		for (int column = 0; column < table.columnCount(); column++) {
			assertNotNull(mapping.get(column), "unmapped header " + table.headers().get(column));
		}
		assertEquals(ImportField.IDENTIFIER, mapping.get(table.headers().indexOf("Course Code")));
		assertEquals(ImportField.NUMBER_OF_CREDITS, mapping.get(table.headers().indexOf("Credits")));
		assertEquals(ImportField.CREDITS_AVAILABLE, mapping.get(table.headers().indexOf("Credits Available")));
		assertEquals(ImportField.DATE, mapping.get(table.headers().indexOf("Published")));
		assertEquals(ImportField.LOCATION, mapping.get(table.headers().indexOf("URL")));
		assertEquals(ImportField.COPYRIGHT, mapping.get(table.headers().indexOf("License")));
		assertEquals(ImportField.IMAGE_DIMENSIONS, mapping.get(table.headers().indexOf("Image Size")));
		assertEquals(ImportField.OBJECTIVES, mapping.get(table.headers().indexOf("Learning Objectives")));
	}

	@Test
	void mapsVariantHeaders() {
		List<String> headers = List.of("Course Title (required)", "lom.general.description", "hx:creditType",
				"Specialities", "MeSH Terms", "Acrediting body", "Zip", "Unrelated column");
		ColumnMapping mapping = ColumnMapping.auto(headers);
		assertEquals(ImportField.TITLE, mapping.get(0));
		assertEquals(ImportField.DESCRIPTION, mapping.get(1));
		assertEquals(ImportField.CREDIT_TYPE, mapping.get(2));
		assertEquals(ImportField.SPECIALTY, mapping.get(3));
		assertEquals(ImportField.KEYWORDS, mapping.get(4));
		assertEquals(ImportField.ACCREDITING_BODY, mapping.get(5), "small typos are tolerated");
		assertEquals(ImportField.POSTAL_CODE, mapping.get(6));
		assertNull(mapping.get(7));
	}

	// ---------------------------------------------------------------------------------------------------------
	// Values

	@Test
	void parsesDurations() {
		assertEquals("PT1H30M", ValueParsers.isoDuration("90").value());
		assertEquals("PT1H30M", ValueParsers.isoDuration("90 min").value());
		assertEquals("PT1H30M", ValueParsers.isoDuration("1h30").value());
		assertEquals("PT1H30M", ValueParsers.isoDuration("1.5 hours").value());
		assertEquals("PT1H30M", ValueParsers.isoDuration("1:30").value());
		assertEquals("PT45M", ValueParsers.isoDuration("pt45m").value());
		assertEquals("P2D", ValueParsers.isoDuration("2 days").value());
		assertEquals("PT7H", ValueParsers.isoDuration("7 hours").value());
		assertNull(ValueParsers.isoDuration("about a week or so"));
	}

	@Test
	void parsesDatesAndOtherValues() {
		assertEquals("2027-03-31", ValueParsers.date("03/31/2027").value().toString());
		assertEquals("2027-03-31", ValueParsers.date("31.03.2027").value().toString());
		assertEquals("2027-03-31", ValueParsers.date("March 31, 2027").value().toString());
		assertEquals("2027-03-31", ValueParsers.date("31 Mar 2027").value().toString());
		assertEquals("2026-01-01", ValueParsers.date("46023").value().toString(), "Excel serial date");
		assertNotNull(ValueParsers.date("04/05/2027").note(), "ambiguous dates are reported");
		assertNull(ValueParsers.date("next Tuesday"));
		assertEquals("2026-11-14T17:00",
				ValueParsers.dateTime("11/14/2026 5:00 PM", java.time.LocalTime.MIDNIGHT).value().toString());
		assertEquals("2025-11", ValueParsers.lomDate("2025-11").value());
		assertEquals("es", ValueParsers.language("Spanish"));
		assertEquals("en-US", ValueParsers.language("en_us"));
		assertEquals("fr-CA", ValueParsers.language("French (Canada)"));
		assertEquals("US", ValueParsers.country("USA"));
		assertEquals("DE", ValueParsers.country("Germany"));
		assertEquals(2_516_582L, ValueParsers.size("2.4 MB").value());
	}

	// ---------------------------------------------------------------------------------------------------------
	// Records

	@Test
	void sampleRecordsAreValid() {
		List<ImportedRecord> records = importSample();
		assertEquals(6, records.size());
		for (ImportedRecord record : records) {
			assertEquals(0, record.count(Severity.ERROR), describe(record));
			assertEquals(0, record.count(Severity.WARNING), describe(record));
		}
	}

	@Test
	void onlineCourse() {
		ImportedRecord record = importSample().getFirst();
		Lom lom = record.root().getLom();
		General general = lom.getGeneral().getFirst();
		assertEquals("Sepsis Early Recognition and the Hour-1 Bundle", record.title());
		assertTrue(general.getDescription().getFirst().getString().getFirst().getValue().contains("\nIncludes two"),
				"line breaks inside quoted cells are kept");
		assertEquals("en", general.getLanguage().getFirst().getValue());
		assertEquals("en", general.getTitle().getFirst().getString().getFirst().getLanguage());
		Keyword septicShock = general.getKeyword().get(1);
		assertEquals("Shock, Septic", septicShock.getString().getFirst().getValue());
		assertEquals("MeSH", septicShock.getSource());
		assertEquals("D012772", septicShock.getId());
		assertNull(general.getKeyword().get(3).getSource(), "plain keywords have no source");
		assertEquals("CAAI", general.getIdentifier().getFirst().getCatalog().getFirst().getValue());
		assertEquals("CME-2026-014", general.getIdentifier().getFirst().getEntry().getFirst().getValue());
		assertEquals("final", Vocabularies.getValue(lom.getLifeCycle().getFirst().getStatus().getFirst()));
		assertTrue(lom.getLifeCycle().getFirst().getContribute().getFirst().getEntity().getFirst().getValue()
				.contains("EMAIL;TYPE=INTERNET:abroussard@example.org"));
		assertTrue(lom.getLifeCycle().getFirst().getContribute().get(1).getEntity().getFirst().getValue()
				.contains("FN:Marc Thibodeaux\\, RN"), "vCard text is escaped");
		assertEquals("text/html", lom.getTechnical().getFirst().getFormat().getFirst().getValue());
		assertEquals(List.of("case study", "self assessment"), lom.getEducational().getFirst().getLearningResourceType()
				.stream().map(Vocabularies::getValue).toList());
		assertEquals("PT1H30M", lom.getEducational().getFirst().getTypicalLearningTime().getFirst().getDuration().getFirst()
				.getValue());
		assertEquals("no", Vocabularies.getValue(lom.getRights().getFirst().getCost().getFirst()));
		assertEquals(3, lom.getClassification().size(), "one classification per learning objective");

		HealthcareEducationType education = lom.getHealthcareMetadata().getFirst().getHealthcareEducation();
		assertEquals(YesNoType.YES, education.getCreditsAvailable());
		assertEquals(2, education.getCredits().size());
		assertEquals(CreditTypeType.CNE, education.getCredits().get(1).getCreditType());
		assertEquals(CreditUnitType.CONTACT_HOUR, education.getCredits().get(1).getCreditUnit());
		assertEquals("ANCC", education.getCredits().get(1).getAccreditingBody());
		assertEquals("Acadiana Office of Continuing Medical Education",
				education.getCredits().get(1).getAccreditedProvider(), "a single value applies to every credit");
		assertEquals(PacingType.LEARNER_PACED, education.getCredits().getFirst().getPacing());
		assertEquals("2027-03-31", education.getExpirationDate().toXMLFormat());
		assertEquals("2027-03-31", education.getCredits().getFirst().getExpirationDate().toXMLFormat());
		assertEquals(List.of("physician", "registered nurse", "advanced practice nurse"), education
				.getTargetAudience().getProfession().stream().map(p -> p.getString().getFirst().getValue()).toList());
		assertEquals(List.of("emergency medicine", "critical care medicine"), education.getTargetAudience()
				.getSpecialty().stream().map(p -> p.getString().getFirst().getValue()).toList());
		assertEquals(List.of(ParticipationModalityType.TECHNOLOGY_BASED), education.getParticipationModality());
		assertEquals(List.of(ActivityDeliveryType.NOT_LIVE), education.getActivityDelivery());
		assertEquals("CME/CNE course", record.kind());
		assertEquals("1.5 CME Credit · 1.5 CNE Contact Hour", record.credits());
		assertTrue(record.messages().stream().anyMatch(m -> m.describe().contains("\"NP\" was read as")),
				record.describeIssues().toString());
	}

	@Test
	void liveWorkshopImageAndPatientRecords() {
		List<ImportedRecord> records = importSample();
		HealthcareEducationType workshop = records.get(1).root().getLom().getHealthcareMetadata().getFirst()
				.getHealthcareEducation();
		assertEquals("2026-11-14T08:00:00", workshop.getStartDateTime().toXMLFormat());
		assertEquals("2026-11-14T17:00:00", workshop.getEndDateTime().toXMLFormat());
		assertEquals("US", workshop.getActivityLocation().getCountry().getCountryCode());
		assertEquals("Lourdes Simulation Center", workshop.getActivityLocation().getOrganization().getFirst());
		assertEquals(List.of(ParticipationModalityType.CONFERENCE_WORKSHOP), workshop.getParticipationModality());
		assertEquals("Live activity", records.get(1).kind());

		var asset = records.get(2).root().getLom().getHealthcareMetadata().getFirst().getHealthcareAsset();
		assertEquals(MedicalImageTypeType.RADIOGRAPH, asset.getMedicalImageType());
		assertEquals(OrientationType.CORONAL, asset.getOrientation());
		assertEquals(2048, asset.getFileWidth().intValue());
		assertEquals(2500, asset.getFileHeight().intValue());
		assertEquals("image/jpeg", records.get(2).root().getLom().getTechnical().getFirst().getFormat().getFirst().getValue());
		assertEquals("URL", records.get(2).root().getLom().getGeneral().getFirst().getIdentifier().getFirst().getCatalog()
				.getFirst().getValue());
		assertEquals("Medical image · radiograph", records.get(2).kind());

		Lom patient = records.get(3).root().getLom();
		assertEquals("es", patient.getGeneral().getFirst().getLanguage().getFirst().getValue());
		assertEquals("ISBN", patient.getGeneral().getFirst().getIdentifier().getFirst().getCatalog().getFirst().getValue());
		var audience = patient.getHealthcareMetadata().getFirst().getHealthcareEducation().getTargetAudience();
		assertEquals(List.of(AudienceCategoryType.PATIENT, AudienceCategoryType.CAREGIVER),
				audience.getAudienceCategory());
		assertEquals("grade 6", audience.getReadingLevel().getFirst().getString().getFirst().getValue());
		assertEquals("Patient education", records.get(3).kind());

		Lom pharmacy = records.get(4).root().getLom();
		assertEquals("yes", Vocabularies.getValue(pharmacy.getRights().getFirst().getCost().getFirst()), "a price means cost");
		assertEquals(List.of("video/mp4", "application/pdf"),
				pharmacy.getTechnical().getFirst().getFormat().stream().map(f -> f.getValue()).toList());
		assertEquals("2 CPE Contact Hour", records.get(4).credits());
	}

	@Test
	void invalidValuesBecomeWarnings() {
		String csv = """
				Title,Status,Credits,Start,Image Type,Cost,Language,Keywords
				Broken row,someday,lots,not a date,banana,maybe,Klingonese,Heart [MeSH:X12]
				,final,,,,,,
				""";
		CsvTable table = CsvReader.parse(csv, ',');
		List<ImportedRecord> records = CsvImporter.preview(table, ColumnMapping.auto(table.headers()),
				HlomTemplates.BLANK);
		assertEquals(2, records.size());
		List<ImportMessage> messages = records.getFirst().messages();
		for (String column : List.of("Status", "Credits", "Start", "Image Type", "Cost", "Language", "Keywords")) {
			assertTrue(messages.stream().anyMatch(m -> column.equals(m.column()) && m.severity() == Severity.WARNING),
					column + ": " + messages);
		}
		assertEquals(0, records.getFirst().count(Severity.ERROR), describe(records.getFirst()));
		assertTrue(records.get(1).messages().stream().anyMatch(m -> m.field() == ImportField.TITLE),
				"rows without a title are reported");
	}

	@Test
	void templateDefaultsAndPlaceholders() {
		String csv = "Title,Author,Credits available\nA,Dr. A,\nB,,no\n";
		CsvTable table = CsvReader.parse(csv, ',');
		List<ImportedRecord> records = CsvImporter.preview(table, ColumnMapping.auto(table.headers()),
				HlomTemplates.CME_COURSE);
		Lom first = records.getFirst().root().getLom();
		String vcards = first.getLifeCycle().getFirst().getContribute().stream().flatMap(c -> c.getEntity().stream())
				.map(e -> e.getValue()).collect(Collectors.joining());
		assertTrue(vcards.contains("FN:Dr. A"), vcards);
		assertFalse(vcards.contains("Author Name"), "CSV values replace template placeholders");
		assertFalse(vcards.contains("Publishing Organization"), "unused placeholders are removed");
		assertTrue(first.getGeneral().getFirst().getDescription().isEmpty(), "placeholder description removed");
		HealthcareEducationType education = first.getHealthcareMetadata().getFirst().getHealthcareEducation();
		assertEquals(1, education.getCredits().size(), "the template's credit is kept");
		assertNull(education.getCredits().getFirst().getAccreditedProvider());
		assertNull(education.getContact());
		HealthcareEducationType second = records.get(1).root().getLom().getHealthcareMetadata().getFirst()
				.getHealthcareEducation();
		assertEquals(YesNoType.NO, second.getCreditsAvailable());
		assertTrue(second.getCredits().isEmpty(), "\"no credits\" removes the template's credit");
		for (ImportedRecord record : records) {
			assertEquals(0, record.count(Severity.ERROR), describe(record));
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	// Writing

	@Test
	void writesUniqueFilesThatReloadAndValidate() throws IOException {
		Path folder = Files.createTempDirectory("hlom-import");
		try {
			CsvTable table = sample();
			CsvImporter importer = new CsvImporter(table, ColumnMapping.auto(table.headers()), HlomTemplates.BLANK);
			List<ImportedRecord> records = importer.preview();
			records.get(5).setIncluded(false);
			CsvImporter.WriteResult result = importer.write(records, folder, "{title}", false, null);
			assertEquals(5, result.written().size(), result.failures().toString());
			assertTrue(Files.exists(folder.resolve("sepsis-early-recognition-and-the-hour-1-bundle.xml")));
			assertTrue(Files.exists(folder.resolve("chest-radiograph-right-middle-lobe-pneumonia.xml")));
			for (Path file : result.written()) {
				Resource resource = HlomResources.load(URI.createFileURI(file.toString()));
				DocumentRoot root = HlomResources.getDocumentRoot(resource);
				assertNotNull(root, file.toString());
				List<HlomProblem> problems = HlomValidator.validate(resource);
				assertTrue(problems.stream().noneMatch(p -> p.severity() == Severity.ERROR), file + " " + problems);
			}

			// Writing again never overwrites: names are de-duplicated against the folder.
			CsvImporter.WriteResult again = importer.write(records, folder, "{title}", false, null);
			assertTrue(again.written().stream().allMatch(p -> p.getFileName().toString().endsWith("-2.xml")),
					again.written().toString());

			// Patterns and de-duplication within a batch.
			CsvTable same = CsvReader.parse("Title,Code\nSame,X1\nSame,X2\nSame,X3\n", ',');
			ColumnMapping mapping = new ColumnMapping(2);
			mapping.set(0, ImportField.TITLE);
			CsvImporter sameImporter = new CsvImporter(same, mapping, HlomTemplates.BLANK);
			List<ImportedRecord> sameRecords = sameImporter.preview();
			assertEquals(List.of("same.xml", "same-2.xml", "same-3.xml"),
					sameImporter.planFileNames(sameRecords, null, "{title}", false));
			assertEquals(List.of("001-same.xml", "002-same.xml", "003-same.xml"),
					sameImporter.planFileNames(sameRecords, null, "{n}-{title}", false));
			assertEquals(List.of("x1.xml", "x2.xml", "x3.xml"),
					sameImporter.planFileNames(sameRecords, null, "{column:Code}.xml", false));
		} finally {
			try (Stream<Path> files = Files.walk(folder)) {
				files.sorted(Comparator.reverseOrder()).forEach(path -> path.toFile().delete());
			}
		}
	}
}
