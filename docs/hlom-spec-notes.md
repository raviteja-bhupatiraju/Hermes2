<!-- Structured extraction of the MedBiquitous Healthcare LOM Specification v1.1 (ANSI/MEDBIQ LO.10.1-2008),
     Copyright MedBiquitous Consortium, used under the MedBiquitous XML Public License.
     Source: xsd/HealthcareLOMSpecificationv1_1_30Sep2009.pdf. Maintained as reference for the editor's help texts,
     value suggestions, conformance rules and test fixtures. -->

# MedBiquitous Healthcare LOM v1.1 (30 Sep 2009) – structured extraction

Source: `xsd/HealthcareLOMSpecificationv1_1_30Sep2009.pdf` (58 pages; content pp. 9-58).
Namespaces used in the spec:

| Prefix (as used in spec) | Namespace URI | Schema file |
|---|---|---|
| (default) | `http://ltsc.ieee.org/xsd/LOM` | healthcarelom.xsd (+ common/*.xsd) |
| `hx` | `http://ns.medbiq.org/lom/extend/v1/` | healthcaremetadata.xsd |
| (none given; "vocab") | `http://ns.medbiq.org/lom/vocab/v1/` (diagram on p.14 shows `.../vocab/v1` without trailing slash) | healthcarevocabularies.xsd |
| `a` | `http://ns.medbiq.org/address/v1/` | address.xsd (MedBiquitous Professional Profile) |
| – | `http://ltsc.ieee.org/xsd/LOM/unique` | unique/strict.xsd |
| – | `http://ltsc.ieee.org/xsd/LOM/vocab` | vocab/custom.xsd |
| – | `http://ltsc.ieee.org/xsd/LOM/extend` | extend/custom.xsd |

Schemas downloadable from `http://ns.medbiq.org/lom/extend/v1/` (zip: `http://ns.medbiq.org/lom/extend/v1/healthcarelom.zip`).

General rules stated on p.18: "Datatypes not otherwise defined in the document, such as date, refer to datatypes defined within the XML 1.0 technical specification." (i.e. xsd:date, xsd:dateTime, etc.)

---

## 1. Metadata Requirements for Healthcare (pp. 10-12)

**Important:** the spec does NOT contain a required/recommended/optional classification of the standard LOM elements. The section is a mapping table "healthcare requirement -> Healthcare LOM element". All healthcare extension elements are declared Optional (see §2), with the only hard requirements being: `healthcareMetadata` must occur once if Healthcare LOM is used (p.18), and the three `metadataSchema` values (see §5 Additional Requirements). No identifier rules, and no rule mandating MeSH, are stated anywhere; MeSH/SNOMED/UMLS are only examples for `keyword/@hx:source`.

Intro text (p.10): "The following table outlines healthcare requirements for learning object metadata and corresponding elements in Healthcare LOM. Any elements within healthcareMetadata are MedBiquitous extensions to the LOM standard."

| Healthcare Requirement | Healthcare LOM Element(s) (verbatim, incl. spec typos) |
|---|---|
| Date released | lifecycle:contribute:date (use when describing the publishing organization's contributions) |
| Date reviewed | lifecycle:contribute:date (use when describing the individual reviewer's contributions) |
| Date of expiry | healthcareMetadata:healthcareEducation:expirationDate |
| Subject (from Medical Taxonomy) | general:keyword (Those wishing to include medical taxonomy references may do so using the MedBiquitous keyword attribute extensions) |
| Category | general:keyword |
| Intended audience (physician, patient, etc) | educational:context (using MedBiquitous defined vocabulary) and healthcareMetadata:healthcareEducation:targetAudience |
| Learner reading level | healthcareMetadata:healthcareEducation:targetAudience:readingLevel |
| Credits (accrediting body, credit types, units, provider, pacing, activity certification, accredited and non-accredited providers, and number of credits) | healthcareMetadata:healthcareEducation:credits |
| Date credit is released | healthcareMetadata:healthcareEducation:credits:releaseDate |
| Date credit expires | healthcareMetadata:healthcareEducation:credits:expirationDate |
| Activity location | healthcareMetadata:healthcareEducation:activityLocation |
| Start date and time | healthcareMetadata:healthcareEducation:startDateTime |
| End date and time | healthcareMetadata:healthcareEducation:endDateTime |
| Activity sponsorship | healthcareMetadata:healthcareEducation:activitySponsorship |
| Activity format | healthcareMetadata:healthcareEducation:activityFormat |
| Participation modality | healthcareMetadata:healthcareEducation:participationModality |
| Activity delivery | healthcareMetadata:healthcareEducation:activityDelivery |
| Curriculum classification | classification:taxonpath:source (using MedBiquitous defined vocabulary for classification purpose) |
| Educational Objectives | classification |
| Competencies, ACGME and others | classification |
| Learning outcomes | Classification (using MedBiquitous defined vocabulary for classification purpose) |
| Acknowledgement of commercial support | healthcareMetadata:healthcareEducation:CommercialSupport (yes/no) and healthcareMetadata:healthcareEducation:CommercialSupportAcknowledgement |
| Disclosure of relevant financial relationships | healthcareMetadata:healthcareEducation: relevantFinancialRelationship (yes/no) and healthcareMetadata:healthcareEducation: relevantfinancialRelationshipDisclosure |
| Disclosure of off label content | healthcareMetadata:healthcareEducation:offLabelUse (yes/no) and healthcareMetadata:healthcareEducation:offLableDescription |
| Mapping to guidelines | classification (using MedBiquitous defined vocabulary for purpose) |
| Mapping to formularies and drug lists | classification (using MedBiquitous defined vocabulary for purpose) |
| Level of Evidence | classification (using MedBiquitous defined vocabulary for purpose) |
| Resource Types: Animation, Audio, Case Study, Collaborative Forum, Game, Image, Reference, Tutorial, Video, Virtual Patient | educational:learningResourceType (using MedBiquitous defined vocabulary) |
| Image descriptors: Annotated, Clinical history, Magnification, Orientation, Medical image, Specimen type, File height, File width | healthcareMetadata:healthcareAsset |

Typos/case issues in this table (actual XSD names are camelCase): `CommercialSupport` -> `commercialSupport`; `CommercialSupportAcknowledgement` -> `commercialSupportAcknowledgement`; `relevantfinancialRelationshipDisclosure` -> `relevantFinancialRelationshipDisclosure`; `offLableDescription` -> `offLabelDescription`; `lifecycle` -> `lifeCycle`; `taxonpath` -> `taxonPath`.

Element-specific rules derived from the rest of the spec:
- `metaMetadata/metadataSchema`: must include `LOMv1.0`, `SCORM_CAM_v1.3` (if applicable), `HEALTHCARE_LOMv1` (see §5).
- `general/keyword`: may carry `hx:source` (terminology, e.g. UMLS, SNOMED, MeSH) and `hx:id` (term id in that terminology, e.g. D009203) (see §4).
- Vocabulary elements (`lifeCycle/contribute/role`, `educational/learningResourceType`, `educational/context`, `classification/purpose`): `source` = `LOMv1.0` for LOM values, `HEALTHCARE_LOMv1` for healthcare values (see §3).
- Classification taxonomies: no specific taxonomy is mandated; samples use `taxonPath/source` = "ACGME Core Competencies" with `purpose` = `competency` (LOMv1.0), taxon id `b`, entry "Medical Knowledge".
- Credits: repeat `credits` once per type of credit (e.g. CME and CNE).

---

## 2. Healthcare extension elements

All in namespace `http://ns.medbiq.org/lom/extend/v1/` (prefix `hx`), except address subelements of activityLocation (`a:`, `http://ns.medbiq.org/address/v1/`) and `string` inside LanguageString (LOM namespace). "Restricted" = enumerated xsd:string (whitespace is NOT collapsed – base is xsd:string).

### 2.1 healthcareMetadata (p.18)  type `healthcareMetadataType`

Text: "healthcareMetadata is the root element. It contains metadata specific to healthcare education. healthcareMetadata must occur once if Healthcare LOM is being used. healthcareMetadata has the subelements healthcareEducation, which describes healthcare metadata for educational offerings, and healthcareAsset, which describes healthcare specific metadata for images, multimedia files, and other types of assets. The uniqueElementName attribute ensures that only one instance of healthcareMetadata occurs in a Healthcare LOM document."

| Element / attribute | Obligation | Multiplicity | Datatype | Description |
|---|---|---|---|---|
| healthcareMetadata | Mandatory when Healthcare LOM is used | exactly 1 (per lom) | Container (healthcareMetadataType); sequence | Root element of the extensions; contains metadata specific to healthcare education. |
| @uniqueElementName | fixed (diagram shows "F") | – | string | Ensures only one instance of healthcareMetadata occurs in a Healthcare LOM document. (Not to be entered by user.) |
| healthcareEducation | Optional | 0 or 1 | healthcareEducationType | Describes healthcare metadata for educational offerings. |
| healthcareAsset | Optional | 0 or 1 | healthcareAssetType | Describes healthcare specific metadata for images, multimedia files, and other types of assets. |

### 2.2 healthcareEducation (pp.19-22)  type `healthcareEducationType` (sequence, in this order)

Intro: "healthcareEducation is the subelement of healthcareMetadata that contains healthcare specific metadata for educational offerings. healthcareEducation is optional within a healthcareMetadata record."

| # | Element | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|---|
| – | healthcareEducation | Optional | 0 or 1 | Container | healthcareEducation is the subelement of healthcareMetadata and defines healthcare specific metadata for educational offerings. |
| 1 | expirationDate | Optional | 0 or 1 | Date (xsd:date) | expirationDate indicates the date after which this educational offering is no longer considered valid. |
| 2 | creditsAvailable | Optional | 0 or 1 | Restricted (yesNoType: yes, no) | creditsAvailable indicates whether or not credits are offered for the educational offering. Valid values are yes and no. |
| 3 | credits | Optional | 0 or more | Container (creditsType) | credits defines the credits that may be awarded for this educational offering. For more information, see section credits. |
| 4 | targetAudience | Optional | 0 or 1 | Container (targetAudienceType) | targetAudience contains subelements that describe the target audience for this educational offering. For more information, see section targetAudience. |
| 5 | activityLocation | Optional | 0 or 1 | Container (activityLocationType) | activityLocation indicates the geographical location in which an in person activity takes place. For more information, see section activityLocation. |
| 6 | startDateTime | Optional | 0 or 1 | Date time (xsd:dateTime) | startDateTime indicates the date and time that a live activity begins. |
| 7 | endDateTime | Optional | 0 or 1 | Date time (xsd:dateTime) | endDateTime indicates the date and time that a live activity ends. |
| 8 | activitySponsorship | Optional | 0 or 1 | Restricted (direct, joint) | activitySponsorship indicates the accredited provider's role in planning and presenting the activity. Valid values are direct and joint. Direct indicates that the accredited provider planned and presented the activity directly. Joint indicates that the accredited provider planned and presented the activity together with a non-accredited provider. |
| 9 | participationModality | Optional | 0 or more | Restricted (conference/workshop, technology based, on the job, print) | participationModality defines the learner's mode of participation, dictated by the activity medium. Valid values are conference/workshop, technology based, on the job, print. |
| 10 | activityDelivery | Optional | 0 or more | Restricted (live, not live) | activityDelivery indicates the temporal nature of the activity. Valid values are live and not live. |
| 11 | activityFormat | Optional | 0 or more | LanguageString | activityFormat describes the type of learning activity described. |
| 12 | offLabelUse | Optional | 0 or 1 | Restricted (yes, no) | offLabelUse indicates whether or not this educational offering references off label usage of a drug. Valid values are yes and no. |
| 13 | offLabelDescription | Optional | 0 or 1 | LanguageString | offLabelDescription describes any off label drug use referenced in this educational offering. |
| 14 | commercialSupport | Optional | 0 or 1 | Restricted (yes, no) | commercialSupport indicates the existence of any commercial support, from a manufacturer of a commercial product. Valid values are yes and no. |
| 15 | commercialSupportAcknowledgement | Optional | 0 or 1 | LanguageString | commercialSupportAcknowledgement describes the commercial support provided from a manufacturer of a commercial product. |
| 16 | relevantFinancialRelationship | Optional | 0 or 1 | Restricted (yes, no) | relevantFinancialRelationship indicates the existence of any relevant financial interest or other relationship of a faculty member or other person in a position to influence the activity content with the manufacturer of any commercial product referenced in this activity. Valid values are yes and no. |
| 17 | relevantFinancialRelationshipDisclosure | Optional | 0 or more | LanguageString | relevantFinancialRelationshipDisclosure describes any relevant financial interest or other relationship of a faculty member or other person in a position to influence the activity content with the manufacturer of any commercial product referenced in this activity. |
| 18 | contact | Optional | 0 or 1 | CharacterString | Contact identifies the person or organization that serves as the main point of contact for questions about the learning content. |

(Order above is the schema sequence from the diagram on p.19; the table in the PDF lists them in a slightly different order – offLabelUse/offLabelDescription after commercialSupport... rows – but the diagram/XSD order is as given.)

### 2.3 credits (pp.23-27)  type `creditsType` (sequence, in this order)

Intro: "credits is the subelement of healthcareEducation that describes the continuing education credits that may be awarded for this educational offering. Note that if an activity is certified to provide more than one type of continuing education credit (i.e. CME and CNE), the credits element should be repeated for each type of credit that may be awarded." "For a glossary of acronyms used to describe credit, see Appendix 1."

| # | Element | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|---|
| – | credits | Optional | 0 or more | Container | credits is the subelement of healthcareEducation that describes continuing education credits that may be awarded for this activity. |
| 1 | accreditingBody | Optional | 0 or 1 | CharacterString | accreditingBody identifies the organization that sets the quality standards for continuing education and is the source of the accreditation process for the provider of this educational activity. Recommended values for accreditingBody include: ACCME, AACN, ACPE, ANCC, AANA, AANP, AAPA, CCME, ASHA, AAA, FCLB PACE, AGD PACE, CDR, CECBEMS, AOTA, COPE, APA, ARRT RCEEM. AARC, ACSM, BOC, EBAC, RCPath, and IACET. |
| 2 | activityCertification | Optional | 0 or 1 | CharacterString | activityCertification identifies the category of credit awarded for the activity by the accredited medical education organization. Recommended values include: AAFP Prescribed, AAFP Elective, AAP, AAP Prep, ACEP, ACOG, AMA PRA Category 1, AMA PRA Category 2, AOA, APA Category 1, CECBEMS First Responder, CECBEMS Basic, CECBEMS Advanced, CECBEMS Operational, CECBEMS Educator, CECBEMS Administrator, CPHQ, EBAC CE Credit Hours, NCHEC Category 1, NCHEC Category 2, Synergy CERPS A, Synergy CERPS B, Synergy CERPS C, RCP. |
| 3 | creditType | Optional | 0 or 1 | Restricted (CME, CE, CNE, CPE, CHES, CPD) | creditType indicates which type of credit is awarded for this educational activity. Valid values are: CME, CE, CNE, CPE, and CHES, CPD. |
| 4 | creditUnit | Optional | 0 or 1 | Restricted (CECH, CEH, CEU, Cognate, Contact Hour, Credit, Hour, Unit, Credit Hour, Point) | creditUnit indicates the unit of credit for this credit definition. Valid values are: CECH, CEH, CEU, Cognate, Contact Hour, Credit, Hour, Unit, Credit Hour, and Point. |
| 5 | pacing | Optional | 0 or 1 | Restricted (learner paced, provider paced) | pacing indicates who controls the pacing of the activity. Valid values are learner paced and provider paced. |
| 6 | accreditedProvider | Optional | 0 or 1 | CharacterString | accreditedProvider defines the entity serving as the accredited provider for this activity. |
| 7 | nonAccreditedProvider | Optional | 0 or more (table; local XSD also minOccurs=0 maxOccurs=unbounded; the p.24 diagram shows "?") | CharacterString | nonAccreditedProvider defines an educational provider for this activity that is not the accredited provider. Jointly sponsored activities have a non-accredited provider. |
| 8 | releaseDate | Optional | 0 or 1 | Date | releaseDate identifies the date this activity becomes available for credit. |
| 9 | expirationDate | Optional | 0 or 1 | Date | expirationDate identifies the date that the activity ceases to be available for credit. |
| 10 | numberOfCredits | Optional | 0 or 1 | Decimal | numberOfCredits identifies the number of continuing education credits associated with this learning activity. |

Suggestion lists (from descriptions):
- accreditingBody: ACCME, AACN, ACPE, ANCC, AANA, AANP, AAPA, CCME, ASHA, AAA, FCLB PACE, AGD PACE, CDR, CECBEMS, AOTA, COPE, APA, ARRT RCEEM, AARC, ACSM, BOC, EBAC, RCPath, IACET
- activityCertification: AAFP Prescribed, AAFP Elective, AAP, AAP Prep, ACEP, ACOG, AMA PRA Category 1, AMA PRA Category 2, AOA, APA Category 1, CECBEMS First Responder, CECBEMS Basic, CECBEMS Advanced, CECBEMS Operational, CECBEMS Educator, CECBEMS Administrator, CPHQ, EBAC CE Credit Hours, NCHEC Category 1, NCHEC Category 2, Synergy CERPS A, Synergy CERPS B, Synergy CERPS C, RCP

### 2.4 targetAudience (pp.28-29)  type `targetAudienceType` (sequence)

Intro: "targetAudience is the subelement of healthcareEducation that provides more information on the individuals for whom this learning content is intended."

| # | Element | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|---|
| – | targetAudience | Optional | 0 or 1 | Container | targetAudience is the subelement of healthcareEducation that provides more information on the learners for which this educational offering is intended. |
| 1 | audienceCategory | Optional | 0 or more | Restricted (general, patient, caregiver, professional) | audienceCategory is a subelement of targetAudience that describes the intended audience for the educational offering in broad terms. Valid values are: general, patient, caregiver, professional. |
| 2 | profession | Optional | 0 or more | LanguageString | profession is the subelement of targetAudience that describes the health profession for which this educational offering is intended. For example, physician, registered nurse, etc. See Appendix 2 for a hierarchical list of recommended values. |
| 3 | specialty | Optional | 0 or more | LanguageString | specialty is the subelement of targetAudience that describes healthcare specialties within a profession that compose part of the target audience for this educational offering. For example, cardiology. See Appendix 2 for a list of recommended values. |
| 4 | readingLevel | Optional | 0 or 1 (table); diagram and local XSD are 0..unbounded | LanguageString | readingLevel is the subelement of targetAudience that describes the primary/secondary school grade reflecting the reading level of the target audience. This is normally used for non-professional education. Recommended values are: grade 1, grade 2, grade 3, grade 4, grade 5, grade 6, grade 7, grade 8, grade 9, grade 10, grade 11, grade 12, above grade 12. |

### 2.5 activityLocation (pp.29-31)  type `activityLocationType` (extends address AddressType)

Intro: "activityLocation is the subelement of healthcareEducation that provides detailed information on the location of an in-person learning activity."

| Element | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|
| activityLocation | Optional | 0 or 1 | Container | activityLocation indicates the geographical location in which an in person activity takes place. activityLocation extends the AddressType datatype within the MedBiquitous Professional Profile. For information on the attributes and subelements associated with an address, please see MedBiquitous Address Specifications and Description Document, ver. 1.0. |
| room | Optional | 1 (as printed; diagram shows 0..1) | CharacterString | Room is a subelement of activityLocation. It defines the room in which an activity takes place. |
| building | Optional | 1 (as printed; diagram shows 0..1) | CharacterString | Building is a subelement of activityLocation. It defines the building in which an activity takes place. |

Inherited address content (from diagram p.30; namespace `a:` = `http://ns.medbiq.org/address/v1/`; no descriptions given in this spec):
- attributes: `addressCategory` (AddressCategoryType, optional), `privacy` (PrivacyType, optional)
- `a:ID` 0..1 NonNullString
- `a:Organization` 0..* NonNullString
- `a:StreetAddressLine` 0..* StreetAddressLineType
- `a:City` 0..1 NonNullString
- `a:StateOrProvince` 0..1 NonNullString
- `a:PostalCode` 0..1 NonNullString
- `a:Region` 0..1 NonNullString
- `a:District` 0..1 NonNullString
- `a:Country` 1 (no "?" in diagram -> required) CountryType (contains e.g. `a:CountryCode`)
- then `room` 0..1, `building` 0..1 (hx namespace, lom:CharacterString)

### 2.6 healthcareAsset (pp.31-34)  type `healthcareAssetType` (sequence)

Intro: "healthcareAsset is the subelement of healthcareMetadata that contains healthcare specific metadata for images, text, multimedia files, and other files that contribute to the creation of an educational offering. healthcareAsset is optional."

| # | Element | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|---|
| – | healthcareAsset | Optional | 0 or 1 | Container | healthcareAsset is the subelement of healthcareMetadata that provides more information about images and other files that contribute to the creation of an educational offering. |
| 1 | annotated | Optional | 0 or 1 | Restricted (yes, no) | annotated is a subelement of healthcareAsset that indicates whether or not this resource features an annotated or labeled image. Valid values are: yes, no. |
| 2 | clinicalHistory | Optional | 0 or 1 | LanguageString | clinicalHistory is a subelement of healthcareAsset that describes the clinical history of the patient or subject described in this resource. |
| 3 | magnification | Optional | 0 or 1 | Integer | magnification is a subelement of healthcareAsset that describes the magnification of microscopic image(s) found in this resource. For example, 100. |
| 4 | orientation | Optional | 0 or 1 | Restricted (axial, coronal, horizontal, longitudinal, sagittal, transverse) | orientation is a subelement of healthcareAsset that describes the orientation of image(s) found in this resource. Valid values are: axial, coronal, horizontal, longitudinal, sagittal, transverse. |
| 5 | medicalImageType | Optional | 0 or 1 | Restricted (angiogram, computed axial tomography scan, electrocardiogram, endoscopic image, magnetic resonance image, mammogram, micrograph, nuclear medicine scan, photograph, radiograph, ultrasound) | medicalImageType is a subelement of healthcareAsset that describes the type of image, radiological or otherwise, Featured in this resource. Valid values are: angiogram, computed axial tomography scan, electrocardiogram, endoscopic image, magnetic resonance image, mammogram, micrograph, nuclear medicine scan, photograph, radiograph, ultrasound. |
| 6 | specimenType | Optional | 0 or 1 | Restricted (cell, organ, organ system, organelle, tissue) | specimenType is a subelement of healthcareAsset that describes the type of specimen featured in this resource. Valid values are: cell, organ, organ system, organelle, tissue. |
| 7 | fileHeight | Optional | 0 or 1 | Integer | fileHeight is the subelement of healthcareAsset that describes the height of the image in pixels. |
| 8 | fileWidth | Optional | 0 or 1 | Integer | fileWidth is the subelement of healthcareAsset that describes the width of the image in pixels. |

### 2.7 LanguageString datatype (pp.34-35)  `lom:LanguageString`

Intro: "Many of the elements in Healthcare LOM use the LanguageString datatype from the IEEE LOM standard. LanguageString provides a way for LOM document creators to specify a value for an element in multiple languages. For example, document creators can specify a single keyword term in both French and English. Within the healthcare extensions, the faculty disclosure description can be encoded multiple languages, too. This functionality is important for those creating educational resources for a multilingual population. Elements using the LanguageString datatype have the subelement of string, which is described in the following table."

| Element / attribute | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|
| string | Optional | 0 or more | CharacterString | string is the subelement of any element using the LanguageString datatype. It provides a word or phrase in a human language. If the string element is repeated within a particular element, each value should be semantically equivalent, such as a translation or alternative description. |
| string/@language | (optional) | 0 or 1 | language code | language specifies the human language of the text string. If the language attribute is not present, the language will be determined by the value of the language element within the LOM metaMetadata container. Valid values are codes from the ISO-10646-1 standard. For example, en for English and fr for French. |

Note: `string` is in the LOM namespace (unprefixed in samples), even inside hx: elements. (Spec error: language codes are ISO 639 / RFC 1766-style tags, not ISO-10646-1.)

### 2.8 CharacterString datatype (pp.35-36)  `lom:CharacterString`

Verbatim: "Many of the elements and attributes in Healthcare LOM use the CharacterString data type from the IEEE LOM standard. CharacterString indicates that the data value for the element or attribute is a character string but is NOT a word or phrase in a human language. For example, a name would be encoded using the CharacterString data type. The characters represented by this data type are from the ISO/IEC 10646-1:2000 standard. This ISO standard, also known as UCS, attempts to include all characters used in all written languages in the world. CharacterString uses the XML string data type."

---

## 3. Custom Vocabularies (pp.38-40)

Verbatim intro: "Healthcare LOM custom vocabularies supplement the LOM vocabularies to provide values useful for healthcare education. The table that follows describes the LOM elements for which custom vocabularies have been created, the LOM provided values for those elements, the Healthcare LOM vocabularies that serve as supplements, and the hierarchical path to the LOM element. **Values from both the LOM vocabularies or the Healthcare LOM vocabularies are considered valid values for these elements.**"

Source strings: LOM values -> `<source>LOMv1.0</source>`; Healthcare values -> `<source>HEALTHCARE_LOMv1</source>`.

| Element | Description | LOM Vocabulary (source LOMv1.0) | Healthcare LOM Vocabulary (source HEALTHCARE_LOMv1) | Path |
|---|---|---|---|---|
| source | An indication of the source or owner of the vocabulary values. | LOMv1.0 | HEALTHCARE_LOMv1 | Occurs with value within any element that has a defined vocabulary |
| role | Defines the type of contribution made by a contributor. | author, publisher, unknown, initiator, terminator, validator, editor, graphical designer, technical implementer, content provider, technical validator, educational validator, script writer, instructional designer, subject matter expert (printed "Author" capitalized) | other, reviewer, programmer, producer, director | lifecycle:contribute:role |
| learningResourceType | Defines the type of educational content. | exercise, simulation, questionnaire, diagram, figure, graph, index, slide, table, narrative text, exam, experiment, problem statement, self assessment, lecture (printed "Exercise") | animation, audio, collaborative forum, game, case study, image, reference, tutorial, video, virtual patient | educational:learningResourceType |
| context | The educational environment for which the learning activity is intended. | school, higher education, training, other (printed "School") | patient education, caregiver education, primary education, secondary education, vocational training, undergraduate education, undergraduate professional education, graduate professional education, continuing professional development | educational:context |
| purpose | Defines a purpose for classifying the learning activity. | discipline, idea, prerequisite, educational objective, accessibility restrictions, educational level, skill level, security level, competency (printed "Discipline") | clinical guideline, drug list, level of evidence, learning outcome | classification:purpose |

Note: lower-case values are the actual LOM vocabulary tokens; capitalization of first items in the PDF table is just typographic. metaMetadata/contribute/role uses LOM values (creator, validator) – not customised.

---

## 4. Custom Elements (pp.40-41) and Custom Attributes (p.37)

### Custom Elements
- "Healthcare LOM allows organizations to add new elements to those described in this document and in the IEEE LOM documentation."
- **Adding Elements to Existing LOM Categories**: "Healthcare LOM allows extensions to general, lifecycle, metaMetadata, technical; educational, rights, relation, annotation, classification, and any of their sub-elements that are also container elements." (per IEEE 1484.12.3)
- **Adding Elements to a New Category**: "Healthcare LOM defines a sub-element of lom called customElements. The customElements container may contain any new elements or new categories of elements as long as those elements are qualified by an XML namespace. Healthcare LOM does not permit extensions to healthcareMetadata or its subelements, so any new elements specific to healthcare should be placed in the customElements container."
- `hx:customElements` is in the hx namespace, child of `lom`.

Example (p.41, verbatim; fragment – `...` are elisions in the spec and the closing `</lom>` is missing):
```xml
<lom xmlns="http://ltsc.ieee.org/xsd/LOM"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:hx="http://ns.medbiq.org/lom/extend/v1/"
xmlns:new="http://your.organization.org/lom/extend/v1/"
xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM healthcarelom.xsd">
   <general>
      ...
   </general>
   ...
   <hx:customElements>
      <new:curriculumPlacement>
         <new:curriculumRequirementSatisfied>
            Pain Management
         </new:curriculumRequirementSatisfied>
         <new:medicalSchoolYear>1</new:medicalSchoolYear>
      </new:curriculumPlacement>
   </hx:customElements>
```

### Custom Attributes
- "Healthcare LOM provides attribute extensions to some LOM elements to facilitate the use of LOM in healthcare and to promote interoperability with non-healthcare systems when possible."
- **Using Clinical Terminologies to Indicate Keyword**: "Medical terminologies such as MeSH, SNOMED, and UMLS provide a structure for describing healthcare topics and can facilitate indexing, cataloging and discovery of healthcare learning content. ... LOM provides the keyword element to describe the important concepts or topics related to learning content. To use the keyword element with terms from medical terminologies, Healthcare LOM adds attribute extension."

| Element / attribute | Required | Multiplicity | Datatype | Description (verbatim) |
|---|---|---|---|---|
| keyword | Optional | 0 or 1 (as printed; LOM allows 0..*, and samples repeat it) | LanguageString | keyword is the subelement of general. Its subelements provide common keywords (important concepts or topics) that describe the educational offering. |
| keyword/@hx:source | optional | – | (string) | source specifies the terminology or vocabulary from which the keyword is taken. For example, UMLS, SNOMED, or MeSH. |
| keyword/@hx:id | optional | – | (string) | id defines the unique identifier associated with this term in the structured terminology or vocabulary. For example, D009203. |

Example:
```xml
<keyword hx:source = "MeSH" hx:id = "D009203">
   <string language = "en">myocardial infarction</string>
</keyword>
```
(The PDF prints a typographic closing quote `"D009203”`; should be a straight `"`.)

---

## 5. Conformance and Additional Requirements (p.42) – verbatim

**Conformance**

To be a conformant instance of Healthcare LOM, an XML document:
- Shall validate against the Healthcare LOM (ANSI /MEDBIQ LO.10.1-200x) XML schema available at http://ns.medbiq.org/lom/extend/v1/healthcarelom.zip
- Shall be a conforming XML instance of LOM as defined by IEEE Standard for Learning Technology--Extensible Markup Language (XML) Schema Definition Language Binding for Learning Object Metadata (IEEE 1484.12.3)
- Shall conform to the Additional Requirements stated in this document
- May include elements not defined in this document or in the IEEE Standard for Learning Technology--Extensible Markup Language (XML) Schema Definition Language Binding for Learning Object Metadata (IEEE 1484.12.3) by using the customElements container defined in the section Custom Elements.

**Additional Requirements**

The metadata document must indicate that it is conformant with the LOM version 1.0, SCORM Content Aggregation Model version 1.3 (if applicable), and Healthcare LOM version 1 standards. To indicate this conformance, include the following XML code in the metaMetadata section of the metadata record:

```xml
<metadataSchema>LOMv1.0</metadataSchema>
<metadataSchema>SCORM_CAM_v1.3</metadataSchema>
<metadataSchema>HEALTHCARE_LOMv1</metadataSchema>
```

---

## 6. Appendix 1: Credit Related Acronyms (pp.44-45)

| Acronym | Meaning |
|---|---|
| AAA | American Academy of Audiology |
| AACN | American Association of Critical-Care Nurses |
| AAFP | American Academy of Family Physicians |
| AANA | American Association of Nurse Anesthetists |
| AANP | American Academy of Nurse Practitioners |
| AAP | American Academy of Pediatrics |
| AAPA | American Academy of Physician Assistants |
| AARC | American Association for Respiratory Care |
| ACCME | Accreditation Council of Continuing Medical Education |
| ACEP | American College of Emergency Physicians |
| ACOG | American College of Obstetrics and Gynecology |
| ACPE | Accreditation Council for Pharmacy Education |
| ACSM | American College of Sports Medicine |
| ADA CERP | American Dental Association Continuing Education Recognition Program |
| AGD PACE | Academy of General Dentistry Program Approval for Continuing Education |
| AMA PRA | American Medical Association Physician Recognition Award |
| ANCC | American Nurses Credentialing Center |
| AOA | American Osteopathic Association |
| AOTA | American Occupational Therapy Association |
| APA | American Psychological Association |
| ARRT RCEEM | American Registry of Radiologic Technologists Recognized Continuing Education Evaluation Mechanism |
| ASHA | American Speech-Language-Hearing Association |
| BOC | Board of Certification (for athletic trainers) |
| CCME | Council on Continuing Medical Education (for osteopathic professionals) |
| CDR | Commission on Dietetic Registration |
| CECBEMS | Continuing Education Coordinating Board for Emergency Medical Services |
| CE | Continuing Education |
| CECH | Continuing Education Contact Hour |
| CEH | Continuing Education Hours |
| CERP | Continuing Education Recognition Point (for critical-care nurses) |
| CEU | Continuing Education Units |
| CHES | Certified Health Education Specialists |
| CME | Continuing Medical Education |
| CNE | Continuing Nursing Education |
| COPE | Council on Optometric Practitioner Education |
| CPE | Continuing Pharmacy Education |
| CPHQ | Certified Professional in Healthcare Quality |
| EBAC | European Board of Accreditation in Cardiology |
| FCLB PACE | Federation of Chiropractic Licensing Boards Providers of Approved Continuing Education |
| IACET | International Association for Continuing Education and Training |
| NCHEC | National Commission for Health Education Credentialing |
| RCPath | Royal College of Pathologists |

(42 entries. Note: CPD, RCP, NCHEC etc. appear in value lists; CPD and RCP are not defined in this appendix.)

---

## 7. Appendix 2: Health Professions and Specialties (pp.46-48)

Intro (verbatim): "Following are recommended terms for health professions and health specialties that should capture the needs of most health professions educators. They are intended to provide guidance for those organizations seeking to describe the professional audience for an educational offering in a consistent manner. The list of professionals is adapted from a list created by the US Health Resources and Services Administration. The list of health specialties is adapted from specialties and internal medicine/pediatric subspecialties as recognized by the Accreditation Council on Graduate Medical Education and the American Board of Medical Specialties. These lists may not be adequate to meet the needs of hospitals and large healthcare organizations requiring a highly detailed hierarchical list of professions. More detailed lists of healthcare professionals have been defined by HL7, SNOMED, the US Health Resources and Services Administration, the US Office of Program Management, and the National Center of Educational Statistics (Classification of Instructional Programs). Either higher or lower level terms in the hierarchy may be used when describing health professions."

### Health Professions (two-level hierarchy; both levels are valid values)

- allied health professional
  - chiropractor
  - dietician
  - emergency medical services professional
  - laboratory professional
  - medical assistant
  - medical examiner / coroner
  - medical imaging professional
  - mental health counselor
  - occupational therapist
  - optician
  - optometrist
  - physical therapist
  - physician assistant
  - podiatrist
  - psychologist
  - rehabilitation professional
  - respiratory therapy professional
  - social worker
  - speech, language, audiology professional
  - substance abuse counselor
- dental professional
  - dentist
- medical professional
  - physician
- nursing professional
  - advanced practice nurse
  - licensed practical nurse
  - registered nurse
- pharmacy professional
  - pharmacist
- public health professional
  - environmental health professional
  - epidemiologist
  - health educator
  - occupational health and safety professional
- veterinary professional
  - veterinarian

(7 top-level + 32 second-level = 39 terms.)

### Health Specialties (flat list, 40 terms)

adolescent medicine
allergy/immunology
anesthesiology
cardiology
colon and rectal surgery
critical care medicine
dermatology
developmental-behavioral
emergency medicine
endocrinology
family practice
gastroenterology
geriatric medicine
hematology and oncology
infectious disease
internal medicine
medical genetics
neonatal-perinatal medicine
nephrology
neurological surgery
neurology
nuclear medicine
obstetrics and gynecology
ophthalmology
orthopaedic surgery
otolaryngology
pathology-anatomic and clinical
pediatrics
physical medicine and rehabilitation
plastic surgery
preventive medicine
psychiatry
pulmonology
radiation oncology
radiology-diagnostic
rheumatology
sports medicine
surgery-general
thoracic surgery
urology

---

## 8. Appendix 3: Sample XML Documents (pp.49-58)

Transcription notes:
- Text was extracted with pdftotext and cross-checked against the rendered pages. Lines that the PDF wraps to column 0 because they were too long for the page (e.g. `...Continuous` / `Subcutaneous...`) are visual wraps; they are joined here with a single space (or no space after a hyphen break). Line breaks that the PDF shows as properly indented separate lines (e.g. inside `hx:accreditedProvider`, `hx:participationModality`, `hx:relevantFinancialRelationship`, `technical/location`, `general/identifier/entry` in sample 3) are genuine and kept.
- Indentation normalised to 3 spaces per level (as in the PDF).
- `\n` inside vCard entity values is a literal backslash-n as printed.

### 8.1 Sample XML for Describing a Learning Object or Course (pp.49-52)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<lom xmlns="http://ltsc.ieee.org/xsd/LOM"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:hx="http://ns.medbiq.org/lom/extend/v1/"
xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM healthcarelom.xsd">
   <general>
      <identifier>
         <catalog>URL</catalog>
         <entry>http://www.aafp.org/x29766.xml</entry>
      </identifier>
      <title>
         <string language="en">Insulin Pump Therapy</string>
      </title>
      <description>
         <string language="en">Provides a general introduction to Continuous Subcutaneous Insulin Infusion for the family practice physician.</string>
      </description>
      <keyword hx:source="MeSH" hx:id="D007332">
         <string language="en">insulin infusion systems</string>
      </keyword>
      <keyword hx:source="MeSH" hx:id="D003920">
         <string language="en">diabetes mellitus</string>
      </keyword>
   </general>
   <lifeCycle>
      <version>
         <string language="en">1.0</string>
      </version>
      <status>
         <source>LOMv1.0</source>
         <value>final</value>
      </status>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>author</value>
         </role>
         <entity>BEGIN:VCARD\nFN:John DoeEND:VCARD</entity>
      </contribute>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>publisher</value>
         </role>
         <entity>BEGIN:VCARD\nFN:American Academy of Family PhysiciansEND:VCARD</entity>
         <date>
            <dateTime>2004-08-01</dateTime>
            <description>
               <string language="en">Publication date</string>
            </description>
         </date>
      </contribute>
   </lifeCycle>
   <metaMetadata>
      <identifier>
         <catalog>URL</catalog>
         <entry>http://www.aafp.org/metadata/001</entry>
      </identifier>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>creator</value>
         </role>
         <entity>American Academy of Family Physicians</entity>
         <date>
            <dateTime>2004-08-01</dateTime>
         </date>
      </contribute>
      <metadataSchema>LOMv1.0</metadataSchema>
      <metadataSchema>SCORM_CAM_v1.3</metadataSchema>
      <metadataSchema>HEALTHCARE_LOMv1</metadataSchema>
   </metaMetadata>
   <technical>
      <format>application/http</format>
   </technical>
   <educational>
      <context>
         <source>HEALTHCARE_LOMv1</source>
         <value>continuing professional development</value>
      </context>
   </educational>
   <rights>
      <cost>
         <source>LOMv1.0</source>
         <value>yes</value>
      </cost>
      <copyrightAndOtherRestrictions>
         <source>LOMv1.0</source>
         <value>yes</value>
      </copyrightAndOtherRestrictions>
      <description>
         <string language="en">Copyright American Academy of Family Physicians, 2004. All Rights Reserved.</string>
      </description>
   </rights>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>educational objective</value>
      </purpose>
      <description>
         <string language="en">List the indications and advantages of Continuous Subcutaneous Insulin therapy.</string>
      </description>
   </classification>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>educational objective</value>
      </purpose>
      <description>
         <string language="en">Manage the programming of the insulin pump and recognize problems when they occur.</string>
      </description>
   </classification>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>competency</value>
      </purpose>
      <taxonPath>
         <source>
            <string language="en">ACGME Core Competencies</string>
         </source>
         <taxon>
            <id>b</id>
            <entry>
               <string language="en">Medical Knowledge</string>
            </entry>
         </taxon>
      </taxonPath>
   </classification>
   <hx:healthcareMetadata>
      <hx:healthcareEducation>
         <hx:expirationDate>2007-04-30</hx:expirationDate>
         <hx:creditsAvailable>yes</hx:creditsAvailable>
         <hx:credits>
            <hx:accreditingBody>ACCME</hx:accreditingBody>
            <hx:activityCertification>AAFP Prescibed</hx:activityCertification>
            <hx:creditType>CME</hx:creditType>
            <hx:creditUnit>Credits</hx:creditUnit>
            <hx:pacing>learner paced</hx:pacing>
            <hx:accreditedProvider>
               American Academy of Family Physicians
            </hx:accreditedProvider>
            <hx:releaseDate>2006-04-30</hx:releaseDate>
            <hx:expirationDate>2007-04-30</hx:expirationDate>
            <hx:numberOfCredits>1.5</hx:numberOfCredits>
         </hx:credits>
         <hx:targetAudience>
            <hx:audienceCategory>professional</hx:audienceCategory>
            <hx:profession>
               <string language="en">physician</string>
            </hx:profession>
            <hx:specialty>
               <string language="en">family practice</string>
            </hx:specialty>
            <hx:readingLevel>
               <string language="en">above grade 12</string>
            </hx:readingLevel>
         </hx:targetAudience>
         <hx:activitySponsorship>direct</hx:activitySponsorship>
         <hx:participationModality>
            technology based
         </hx:participationModality>
         <hx:activityDelivery>not live</hx:activityDelivery>
         <hx:activityFormat>
            <string language="en">course</string>
         </hx:activityFormat>
         <hx:commercialSupport>yes</hx:commercialSupport>
         <hx:commercialSupportAcknowledgement>
            <string language="en">The Academy gratefully acknowledges a grant from the XYZ Foundation to provide funding for this course.</string>
         </hx:commercialSupportAcknowledgement>
         <hx:relevantFinancialRelationship>
            no
         </hx:relevantFinancialRelationship>
         <hx:contact>John Doe, jdoe@aafp.org</hx:contact>
      </hx:healthcareEducation>
   </hx:healthcareMetadata>
</lom>
```

### 8.2 Sample XML for Describing a Learning Object or Course with Multiple Types of Credit (pp.53-56)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<lom xmlns="http://ltsc.ieee.org/xsd/LOM"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:hx="http://ns.medbiq.org/lom/extend/v1/"
xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM healthcarelom.xsd">
   <general>
      <identifier>
         <catalog>URL</catalog>
         <entry>http://www.atsdr.cdc.gov/HEC/CSEM/arsenic/index.html</entry>
      </identifier>
      <title>
         <string language="en">Arsenic Toxicity</string>
      </title>
      <language>en</language>
      <description>
         <string language="en">Provides a general introduction to arsenic toxicity in the environment.</string>
      </description>
      <keyword hx:source="MeSH" hx:id="D001151">
         <string language="en">arsenic</string>
      </keyword>
      <keyword hx:source="MeSH" hx:id="D019550">
         <string language="en">environmental medicine</string>
      </keyword>
   </general>
   <lifeCycle>
      <version>
         <string language="en">1.0</string>
      </version>
      <status>
         <source>LOMv1.0</source>
         <value>final</value>
      </status>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>author</value>
         </role>
         <entity>BEGIN:VCARD\nFN:John DoeEND:VCARD</entity>
      </contribute>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>publisher</value>
         </role>
         <entity>BEGIN:VCARD\nFN:Centers for Disease Control and PreventionEND:VCARD</entity>
         <date>
            <dateTime>2005-10-30</dateTime>
            <description>
               <string language="en">Publication date</string>
            </description>
         </date>
      </contribute>
   </lifeCycle>
   <metaMetadata>
      <identifier>
         <catalog>URL</catalog>
         <entry>http://www.cd.gov/metadata/001</entry>
      </identifier>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>creator</value>
         </role>
         <entity>Centers for Disease Control and Prevention</entity>
         <date>
            <dateTime>2005-10-30</dateTime>
         </date>
      </contribute>
      <metadataSchema>LOMv1.0</metadataSchema>
      <metadataSchema>SCORM_CAM_v1.3</metadataSchema>
      <metadataSchema>HEALTHCARE_LOMv1</metadataSchema>
   </metaMetadata>
   <technical>
      <format>application/http</format>
      <location>http://www.atsdr.cdc.gov/HEC/CSEM/arsenic/index.html
      </location>
   </technical>
   <educational>
      <context>
         <source>HEALTHCARE_LOMv1</source>
         <value>continuing professional development</value>
      </context>
   </educational>
   <rights>
      <cost>
         <source>LOMv1.0</source>
         <value>no</value>
      </cost>
      <copyrightAndOtherRestrictions>
         <source>LOMv1.0</source>
         <value>no</value>
      </copyrightAndOtherRestrictions>
   </rights>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>educational objective</value>
      </purpose>
      <description>
         <string language="en">Discuss the major exposure route for arsenic</string>
      </description>
   </classification>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>educational objective</value>
      </purpose>
      <description>
         <string language="en">Describe two potential environmental and occupational sources of arsenic exposure</string>
      </description>
   </classification>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>educational objective</value>
      </purpose>
      <description>
         <string language="en">Identify evaluation and treatment protocols for persons exposed to arsenic</string>
      </description>
   </classification>
   <classification>
      <purpose>
         <source>LOMv1.0</source>
         <value>competency</value>
      </purpose>
      <taxonPath>
         <source>
            <string language="en">ACGME Core Competencies</string>
         </source>
         <taxon>
            <id>b</id>
            <entry>
               <string language="en">Medical Knowledge</string>
            </entry>
         </taxon>
      </taxonPath>
   </classification>
   <hx:healthcareMetadata>
      <hx:healthcareEducation>
         <hx:expirationDate>2006-10-30</hx:expirationDate>
         <hx:creditsAvailable>yes</hx:creditsAvailable>
         <hx:credits>
            <hx:accreditingBody>ACCME</hx:accreditingBody>
            <hx:activityCertification>AMA PRA category 1</hx:activityCertification>
            <hx:creditType>CME</hx:creditType>
            <hx:creditUnit>Credit</hx:creditUnit>
            <hx:accreditedProvider>Centers for Disease Control and Prevention</hx:accreditedProvider>
            <hx:releaseDate>2005-10-30</hx:releaseDate>
            <hx:expirationDate>2006-10-30</hx:expirationDate>
            <hx:numberOfCredits>1.5</hx:numberOfCredits>
         </hx:credits>
         <hx:credits>
            <hx:accreditingBody>ANCC</hx:accreditingBody>
            <hx:creditType>CNE</hx:creditType>
            <hx:creditUnit>Contact Hour</hx:creditUnit>
            <hx:pacing>learner paced</hx:pacing>
            <hx:accreditedProvider>Centers for Disease Control and Prevention</hx:accreditedProvider>
            <hx:releaseDate>2005-10-30</hx:releaseDate>
            <hx:expirationDate>2006-10-30</hx:expirationDate>
            <hx:numberOfCredits>1.7</hx:numberOfCredits>
         </hx:credits>
         <hx:targetAudience>
            <hx:audienceCategory>professional</hx:audienceCategory>
            <hx:profession>
               <string language="en">physician</string>
            </hx:profession>
            <hx:profession>
               <string language="en">registered nurse</string>
            </hx:profession>
            <hx:specialty>
               <string language="en">family practice</string>
            </hx:specialty>
            <hx:readingLevel>
               <string language="en">above grade 12</string>
            </hx:readingLevel>
         </hx:targetAudience>
         <hx:activitySponsorship>direct</hx:activitySponsorship>
         <hx:participationModality>technology based</hx:participationModality>
         <hx:activityDelivery>not live</hx:activityDelivery>
         <hx:activityFormat>
            <string language="en">course</string>
         </hx:activityFormat>
         <hx:commercialSupport>no</hx:commercialSupport>
         <hx:relevantFinancialRelationship>
            no
         </hx:relevantFinancialRelationship>
         <hx:contact>John Doe, jdoe@cdc.gov</hx:contact>
      </hx:healthcareEducation>
   </hx:healthcareMetadata>
</lom>
```

### 8.3 Sample XML for Describing an Asset (pp.57-58)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<lom xmlns="http://ltsc.ieee.org/xsd/LOM"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:hx="http://ns.medbiq.org/lom/extend/v1/"
xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM healthcarelom.xsd ">
   <general>
      <identifier>
         <catalog>URL</catalog>
         <entry>
           http://www.medicalschool.edu/images/dialysisdrainage.jpg
         </entry>
      </identifier>
      <title>
         <string language="en">Drainage of Dialysis Fluid from Peritoneal Cavity</string>
      </title>
      <description>
         <string language="en">Drainage of dialysis fluid in peritoneal cavity with catheter in pouch of Douglas.</string>
      </description>
      <keyword hx:source="MeSH" hx:id="D002404">
         <string language="en">Catheterization</string>
      </keyword>
      <keyword hx:source="MeSH" hx:id="D015314">
         <string language="en">Dialysis Solutions</string>
      </keyword>
   </general>
   <lifeCycle>
      <contribute>
         <role>
            <source>LOMv1.0</source>
            <value>publisher</value>
         </role>
         <entity>BEGIN:VCARD\nFN:Anystate Medical SchoolEND:VCARD</entity>
      </contribute>
   </lifeCycle>
   <metaMetadata>
      <identifier>
         <catalog>URL</catalog>
         <entry>http://www.medicalschool.edu/metadata/metadata010</entry>
      </identifier>
      <metadataSchema>LOMv1.0</metadataSchema>
      <metadataSchema>HEALTHCARE_LOMv1</metadataSchema>
   </metaMetadata>
   <technical>
      <format>image/jpg</format>
      <size>135090</size>
   </technical>
   <educational>
      <learningResourceType>
         <source>HEALTHCARE_LOMv1</source>
         <value>image</value>
      </learningResourceType>
   </educational>
   <rights>
      <cost>
         <source>LOMv1.0</source>
         <value>no</value>
      </cost>
      <copyrightAndOtherRestrictions>
         <source>LOMv1.0</source>
         <value>yes</value>
      </copyrightAndOtherRestrictions>
      <description>
         <string language="en">Creative Commons Attribution-Noncommercial-Sharealike license. Contact Joe Shmoe at jshmoe@medicalschool.edu for more information.</string>
      </description>
   </rights>
   <hx:healthcareMetadata>
      <hx:healthcareAsset>
         <hx:annotated>yes</hx:annotated>
         <hx:orientation>sagittal</hx:orientation>
         <hx:specimenType>organ system</hx:specimenType>
         <hx:fileHeight>736</hx:fileHeight>
         <hx:fileWidth>806</hx:fileWidth>
      </hx:healthcareAsset>
   </hx:healthcareMetadata>
</lom>
```

### 8.4 Errors / issues in the samples (relevant for test fixtures)

Checked against `xsd/healthcare/healthcaremetadata.xsd`: all restricted types are `xsd:restriction base="xsd:string"` enumerations, so leading/trailing whitespace is NOT collapsed and makes the value invalid.

Sample 8.1 (will NOT validate as printed):
- `<hx:creditUnit>Credits</hx:creditUnit>` – "Credits" is not in creditUnitType (valid: `Credit`).
- `<hx:participationModality>` with surrounding newlines/indentation – invalid against the enum (should be `technology based` with no whitespace).
- `<hx:relevantFinancialRelationship>` with surrounding whitespace – invalid against yesNoType (should be `no`).
- `AAFP Prescibed` – typo for "AAFP Prescribed" (free CharacterString, so valid but wrong).
- `hx:accreditedProvider` value has surrounding whitespace (valid, CharacterString, but should be trimmed).
- `application/http` is not a real MIME type (LOM format is CharacterString, so schema-valid).
- vCard entities lack a newline before `END:VCARD` and lack `VERSION` (not valid vCard, schema-valid).
- Same printed sample in the body text, p.23 (healthcareEducation example), uses "MedBiquitous gratefully acknowledges..." and contact `Edgar Allan Poe, eapoe@medbiq.org`, mixes prefixed/unprefixed hx elements; it's an illustrative fragment, not a fixture.

Sample 8.2:
- `hx:relevantFinancialRelationship` with surrounding whitespace – invalid against yesNoType.
- `participationModality` here is a visual wrap -> `technology based` (valid). If a raw newline were kept it would be invalid.
- `AMA PRA category 1` – recommended value is "AMA PRA Category 1" (case; free text, schema-valid).
- metaMetadata identifier `http://www.cd.gov/metadata/001` – probably a typo for cdc.gov.
- `technical/location` contains a trailing newline+spaces inside the URL value.
- `ANCC` credits block has no activityCertification (fine).

Sample 8.3:
- `image/jpg` – registered MIME type is `image/jpeg`.
- `xsi:schemaLocation` value has a trailing space (harmless).
- `general/identifier/entry` has surrounding whitespace inside the URL.
- `metadataSchema` omits SCORM_CAM_v1.3 (allowed: "if applicable").

Body-text examples (not appendix) – noted errors:
- p.31 activityLocation example uses prefix `a:` with no namespace declaration; StreetAddressLine wraps.
- p.34 healthcareAsset example: `<medicalImageType> radiograph</medicalImageType>` has a leading space (invalid vs enum), unprefixed hx elements, and typographic quotes `language=”en”`.
- p.35 LanguageString example uses `language = "sp"` for Spanish; correct ISO 639 code is `es`.
- p.37 keyword example has a curly closing quote on `"D009203”`.
- p.41 customElements example is missing `</lom>`.

Other spec inconsistencies: keyword multiplicity printed as "0 or 1" (LOM: 0..*); room/building multiplicity printed "1" while Required = Optional (local XSD: minOccurs=0, i.e. 0..1); nonAccreditedProvider "0 or more" in table vs "?" in diagram (local XSD: 0..unbounded); readingLevel "0 or 1" in table vs "*" in diagram (local XSD: 0..unbounded); LanguageString language codes attributed to "ISO-10646-1" (wrong standard); `common/rootElement` and `vocabValues` listed without `.xsd`.
