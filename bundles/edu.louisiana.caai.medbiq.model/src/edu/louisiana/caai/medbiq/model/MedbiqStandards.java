/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
 */
package edu.louisiana.caai.medbiq.model;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import edu.louisiana.caai.lom.model.support.type.DocumentType;
import edu.louisiana.caai.lom.model.support.type.DocumentTypes;
import edu.louisiana.caai.medbiq.model.MedbiqDocumentType.Definition;
import edu.louisiana.caai.medbiq.model.standards.ActivityReports;
import edu.louisiana.caai.medbiq.model.standards.Competencies;
import edu.louisiana.caai.medbiq.model.standards.CurriculumInventory;
import edu.louisiana.caai.medbiq.model.standards.EducationMetrics;
import edu.louisiana.caai.medbiq.model.standards.ProfessionalProfile;
import edu.louisiana.caai.medbiq.model.standards.StandardContent;
import edu.louisiana.caai.medbiq.model.standards.VirtualPatient;

/**
 * The published MedBiquitous standards besides Healthcare LOM, as {@link DocumentType document types}.
 * {@link #init()} registers them in {@link DocumentTypes}; inside Eclipse the bundle activator does.
 */
public final class MedbiqStandards {

	private static final String NS = "http://ns.medbiq.org/";
	private static final String SITE = "https://www.medbiq.org/standards/";

	private static final StandardContent CURRICULUM = new CurriculumInventory();
	private static final StandardContent COMPETENCIES = new Competencies();
	private static final StandardContent ACTIVITY_REPORTS = new ActivityReports();
	private static final StandardContent METRICS = new EducationMetrics();
	private static final StandardContent PROFILES = new ProfessionalProfile();
	private static final StandardContent VIRTUAL_PATIENT = new VirtualPatient();

	/** The document types, in the order shown to users. */
	public static final List<MedbiqDocumentType> TYPES = List.of(
			type("curriculum-inventory-v1", "Curriculum Inventory", "1.0", "curriculuminventory/v1/",
					"CurriculumInventory", "curriculuminventory/v1/curriculuminventory.xsd", "curriculum-data-exchange",
					CURRICULUM),
			type("curriculum-inventory-v10", "Curriculum Inventory", "v10 (AAMC 2022)", "curriculuminventory/v10/",
					"CurriculumInventory", "curriculuminventory/v10/curriculuminventory.xsd", "curriculum-data-exchange",
					CURRICULUM),
			type("competency-framework-v1", "Competency Framework", "1.0", "competencyframework/v1/",
					"CompetencyFramework", "competencyframework/v1/competencyframework.xsd", "competency-framework",
					COMPETENCIES),
			type("competency-object-v1", "Competency Object", "1.0", "competencyobject/v1/", "CompetencyObject",
					"competencyobject/v1/competencyobject.xsd", "competency-object", COMPETENCIES),
			type("performance-framework-v1", "Performance Framework", "1.0", "performanceframework/v1/",
					"PerformanceFramework", "performanceframework/v1/performanceframework.xsd", "performance-framework",
					COMPETENCIES),
			type("activity-report-v1", "Activity Report", "1.0", "activityreport/v1/", "ActivityReports",
					"activityreport/v1/activityreport.xsd", "activity-report", ACTIVITY_REPORTS),
			type("activity-report-v2", "Activity Report", "2.0", "activityreport/v2/", "ActivityReports",
					"activityreport/v2/activityreport.xsd", "activity-report", ACTIVITY_REPORTS),
			type("medical-education-metrics-v1", "Medical Education Metrics", "1.0", "metrics/v1/",
					"MedicalEducationMetrics", "metrics/v1/medicaleducationmetrics.xsd", "medical-education-metrics-mems",
					METRICS),
			type("survey-items-v1", "MEMS Survey Items", "1.0", "surveyitem/v1/", "SurveyItems",
					"surveyitems/v1/surveyitems.xsd", "medical-education-metrics-mems", METRICS),
			type("professional-profile-v1", "Healthcare Professional Profile", "1.0", "member/v1/", "Members",
					"member/v1/member.xsd", "professional-profile", PROFILES),
			type("virtual-patient-data-v1", "Virtual Patient Data", "1.0", "virtualpatientdata/v1/",
					"VirtualPatientData", "virtualpatientdata/v1/virtualpatientdata.xsd", "virtual-patient",
					VIRTUAL_PATIENT),
			type("virtual-patient-activity-model-v1", "Virtual Patient Activity Model", "1.0", "activitymodel/v1/",
					"ActivityModel", "activitymodel/v1/activitymodel.xsd", "virtual-patient", VIRTUAL_PATIENT),
			type("virtual-patient-data-availability-model-v1", "Virtual Patient Data Availability Model", "1.0",
					"dataavailabilitymodel/v1/", "DataAvailabilityModel",
					"dataavailabilitymodel/v1/dataavailabilitymodel.xsd", "virtual-patient", VIRTUAL_PATIENT));

	private static volatile boolean initialized;

	private MedbiqStandards() {
	}

	private static MedbiqDocumentType type(String id, String standard, String version, String namespace,
			String root, String schema, String page, StandardContent content) {
		return new MedbiqDocumentType(new Definition(id, standard, version, NS + namespace, root, NS + schema,
				SITE + page, id + ".xml", content));
	}

	/** Registers the document types (once). */
	public static synchronized void init() {
		if (initialized) {
			return;
		}
		MedbiqModels.packages();
		TYPES.forEach(DocumentTypes::register);
		initialized = true;
	}

	/** The type with an id, or {@code null}. */
	public static MedbiqDocumentType byId(String id) {
		return TYPES.stream().filter(type -> type.id().equals(id)).findFirst().orElse(null);
	}

	/** Registers the document types when the bundle starts inside Eclipse. */
	public static final class Activator implements BundleActivator {
		@Override
		public void start(BundleContext context) {
			init();
		}

		@Override
		public void stop(BundleContext context) {
			// The types stay registered.
		}
	}
}
