CREATE TABLE IF NOT EXISTS EQUIPMENT_TYPES
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_name VARCHAR                                 NOT NULL,
    volume         INTEGER,
    orientation    VARCHAR,
    model          VARCHAR,
    CONSTRAINT pk_equipmentType PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_TYPE_ELEMENTS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    element_name      VARCHAR                                 NOT NULL,
    equipment_type_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_equipmentTypeElement PRIMARY KEY (id),
    CONSTRAINT FK_EQUIPMENT_TYPE_ELEMENTS_ON_EQUIPMENT_TYPES
        FOREIGN KEY (equipment_type_id)
            REFERENCES equipment_types (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_TYPE_PARTS_ELEMENTS
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    part_name  VARCHAR                                 NOT NULL,
    element_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_equipmentTypePartElement PRIMARY KEY (id),
    CONSTRAINT FK_PARTS_ELEMENTS_ON_ELEMENTS FOREIGN KEY (element_id) REFERENCES equipment_type_elements (id)
);

CREATE TABLE IF NOT EXISTS STANDARD_SIZES
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    design_thickness DOUBLE PRECISION,
    min_diameter     INTEGER,
    max_diameter     INTEGER,
    CONSTRAINT pk_standardSize PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_DIAGNOSED
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_type_id BIGINT                                  NOT NULL,
    building_id       BIGINT                                  NOT NULL,
    equipment_name    VARCHAR                                 NOT NULL,
    stationary_number INTEGER,
    geodesy_locations INTEGER,
    model             VARCHAR,
    room              VARCHAR,
    volume            INTEGER,
    old               BOOLEAN,
    CONSTRAINT pk_equipmentDiagnosed PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_ELEMENTS
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    element_id             BIGINT                                  NOT NULL,
    element_name           VARCHAR                                 NOT NULL,
    standard_size_id       BIGINT,
    equipment_diagnosed_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_equipmentElement PRIMARY KEY (id),
    CONSTRAINT FK_EQUIPMENT_ELEMENTS_ON_STANDARD_SIZES FOREIGN KEY (standard_size_id) REFERENCES standard_sizes (id),
    CONSTRAINT FK_EQUIPMENT_ELEMENTS_ON_EQUIPMENT_DIAGNOSED
        FOREIGN KEY (equipment_diagnosed_id) REFERENCES equipment_diagnosed (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_PARTS_ELEMENTS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    part_element_id   BIGINT                                  NOT NULL,
    part_element_name VARCHAR                                 NOT NULL,
    element_id        BIGINT                                  NOT NULL,
    standard_size_id  BIGINT,
    CONSTRAINT pk_equipmentPartElement PRIMARY KEY (id),
    CONSTRAINT FK_EQUIPMENT_PARTS_ELEMENTS_ON_STANDARD_SIZES
        FOREIGN KEY (standard_size_id) REFERENCES standard_sizes (id),
    CONSTRAINT FK_EQUIPMENT_PARTS_ELEMENTS_ON_EQUIPMENT_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES equipment_elements (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_PASSPORT
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_diagnosed_id BIGINT                                  NOT NULL,
    sequential_number      INTEGER                                 NOT NULL,
    header                 VARCHAR                                 NOT NULL,
    meaning                VARCHAR                                 NOT NULL,
    use_to_protocol        BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_equipmentPassport PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_INSPECTIONS
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_diagnosed_id BIGINT                                  NOT NULL,
    date                   VARCHAR                                 NOT NULL,
    inspection             VARCHAR                                 NOT NULL,
    document_number        VARCHAR                                 NOT NULL,
    organization           VARCHAR                                 NOT NULL,
    CONSTRAINT pk_equipmentInspection PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_REPAIRS
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_diagnosed_id BIGINT                                  NOT NULL,
    date                   VARCHAR                                 NOT NULL,
    description            VARCHAR                                 NOT NULL,
    organization           VARCHAR,
    CONSTRAINT pk_equipmentRepair PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS DEFECTS
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    defect_name             VARCHAR                                 NOT NULL,
    not_meet_requirements   BOOLEAN                                 NOT NULL,
    calculation             VARCHAR                                 NOT NULL,
    use_calculate_thickness BOOLEAN,
    CONSTRAINT pk_defect PRIMARY KEY (id),
    CONSTRAINT UQ_DEFECTS UNIQUE (defect_name)
);

CREATE TABLE IF NOT EXISTS ELEMENT_REPAIRS
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    repair_name VARCHAR                                 NOT NULL,
    calculation VARCHAR                                 NOT NULL,
    CONSTRAINT pk_elementRepair PRIMARY KEY (id),
    CONSTRAINT UQ_ELEMENT_REPAIRS UNIQUE (repair_name)
);

CREATE TABLE IF NOT EXISTS MEASUREMENTS_PARAMETERS
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    parameter_name   VARCHAR                                 NOT NULL,
    unit_measurement VARCHAR                                 NOT NULL,
    defect_id        BIGINT,
    repair_id        BIGINT,
    CONSTRAINT pk_measuredParameter PRIMARY KEY (id),
    CONSTRAINT FK_MEASUREMENTS_PARAMETERS_ON_DEFECTS FOREIGN KEY (defect_id) REFERENCES defects (id),
    CONSTRAINT FK_MEASUREMENTS_PARAMETERS_ON_ELEMENT_REPAIRS FOREIGN KEY (repair_id) REFERENCES element_repairs (id)
);

CREATE TABLE IF NOT EXISTS ACCEPTABLE_THICKNESS
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_type_id  BIGINT                                  NOT NULL,
    element_id         BIGINT                                  NOT NULL,
    part_element_id    BIGINT,
    diameter           INTEGER,
    min_thickness      DOUBLE PRECISION,
    acceptable_percent INTEGER,
    measurement_error  FLOAT,
    CONSTRAINT pk_acceptableThickness PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ACCEPTABLE_HARDNESS
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_type_id       BIGINT                                  NOT NULL,
    element_id              BIGINT                                  NOT NULL,
    part_element_id         BIGINT,
    min_allowable_diameter  INTEGER,
    min_allowable_thickness FLOAT,
    min_hardness            INTEGER                                 NOT NULL,
    max_hardness            INTEGER                                 NOT NULL,
    measurement_error       FLOAT,
    CONSTRAINT pk_acceptableHardness PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ACCEPTABLE_DEVIATIONS_GEODESY
(
    id                                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_type_id                 BIGINT                                  NOT NULL,
    fulls                             BOOLEAN                                 NOT NULL,
    old                               BOOLEAN                                 NOT NULL,
    volume                            INTEGER                                 NOT NULL,
    acceptable_precipitation          INTEGER                                 NOT NULL,
    max_difference_neighboring_points INTEGER                                 NOT NULL,
    max_difference_diametric_points   INTEGER                                 NOT NULL,
    CONSTRAINT pk_permissibleDeviationsGeodesy PRIMARY KEY (id),
    CONSTRAINT UQ_PERMISSIBLE_DEVIATIONS_GEODESY UNIQUE (equipment_type_id, fulls, old)
);

CREATE TABLE IF NOT EXISTS GEODESIC_MEASUREMENTS_POINTS
(
    id                          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id                BIGINT                                  NOT NULL,
    measurement_number          INTEGER                                 NOT NULL,
    sequential_number           INTEGER                                 NOT NULL,
    number_measurement_location INTEGER                                 NOT NULL,
    reference_point_value       INTEGER,
    control_point_value         INTEGER                                 NOT NULL,
    transition_value            INTEGER,
    CONSTRAINT pk_geodesicMeasurementsPoint PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EQUIPMENT_GEODESIC_MEASUREMENTS
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_equipmentGeodesicMeasurements PRIMARY KEY (id),
    CONSTRAINT UQ_EQUIPMENT_GEODESIC_MEASUREMENTS UNIQUE (equipment_id)
);

CREATE TABLE IF NOT EXISTS REFERENCE_POINTS
(
    id                       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    geodesic_measurement_id  BIGINT                                  NOT NULL,
    place_number             INTEGER                                 NOT NULL,
    calculated_height        INTEGER                                 NOT NULL,
    deviation                INTEGER                                 NOT NULL,
    precipitation            INTEGER,
    acceptable_precipitation BOOLEAN,
    CONSTRAINT pk_referencePoint PRIMARY KEY (id),
    CONSTRAINT FK_REFERENCE_POINTS_ON_EQUIPMENT_GEODESIC_MEASUREMENTS
        FOREIGN KEY (geodesic_measurement_id) REFERENCES equipment_geodesic_measurements (id)
);

CREATE TABLE IF NOT EXISTS DEVIATION_YEARS
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    reference_point_id BIGINT                                  NOT NULL,
    year               INTEGER                                 NOT NULL,
    deviation          INTEGER                                 NOT NULL,
    CONSTRAINT pk_deviationYear PRIMARY KEY (id),
    CONSTRAINT UQ_DEVIATION_YEARS UNIQUE (reference_point_id, year),
    CONSTRAINT FK_DEVIATION_YEARS_ON_REFERENCE_POINTS FOREIGN KEY (reference_point_id) REFERENCES reference_points (id)
);

CREATE TABLE IF NOT EXISTS CONTROL_POINTS
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    geodesic_measurement_id BIGINT                                  NOT NULL,
    place_number            INTEGER                                 NOT NULL,
    calculated_height       INTEGER                                 NOT NULL,
    deviation               INTEGER                                 NOT NULL,
    CONSTRAINT pk_controlPoint PRIMARY KEY (id),
    CONSTRAINT FK_CONTROL_POINTS_ON_EQUIPMENT_GEODESIC_MEASUREMENTS
        FOREIGN KEY (geodesic_measurement_id) REFERENCES equipment_geodesic_measurements (id)
);

CREATE TABLE IF NOT EXISTS POINTS_DIFFERENCE
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    geodesic_measurement_id BIGINT                                  NOT NULL,
    type                    VARCHAR                                 NOT NULL,
    first_place_number      INTEGER                                 NOT NULL,
    second_place_number     INTEGER                                 NOT NULL,
    difference              INTEGER                                 NOT NULL,
    acceptable_difference   BOOLEAN,
    CONSTRAINT pk_pointDifference PRIMARY KEY (id),
    CONSTRAINT FK_POINTS_DIFFERENCE_ON_EQUIPMENT_GEODESIC_MEASUREMENTS
        FOREIGN KEY (geodesic_measurement_id) REFERENCES equipment_geodesic_measurements (id)
);

CREATE TABLE IF NOT EXISTS VISUAL_MEASURING_CONTROLS
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    work_journal_id     BIGINT                                  NOT NULL,
    defect_id           BIGINT                                  NOT NULL,
    defect_name         VARCHAR                                 NOT NULL,
    standard_size       VARCHAR,
    welded_joint_number VARCHAR,
    coordinates         VARCHAR,
    estimation          VARCHAR,
    CONSTRAINT pk_visualMeasurementControl PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CALCULATED_ELEMENTS
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id BIGINT                                  NOT NULL,
    element_name VARCHAR                                 NOT NULL,
    inspection   VARCHAR                                 NOT NULL,
    CONSTRAINT pk_calculatedElement PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CALCULATED_PARTS_ELEMENTS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    part_element_name VARCHAR                                 NOT NULL,
    inspection        VARCHAR                                 NOT NULL,
    element_id        BIGINT                                  NOT NULL,
    CONSTRAINT pk_calculatedPartElement PRIMARY KEY (id),
    CONSTRAINT FK_CALCULATED_PARTS_ELEMENTS_ON_CALCULATED_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES calculated_elements (id)
);

CREATE TABLE IF NOT EXISTS CALCULATED_DEFECTS
(
    id                         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    defect_id                  BIGINT                                  NOT NULL,
    defect_name                VARCHAR                                 NOT NULL,
    not_meet_requirements      BOOLEAN                                 NOT NULL,
    welded_joint_number        INTEGER,
    use_calculate_thickness    BOOLEAN,
    element_id                 BIGINT                                  NOT NULL,
    part_element_id            BIGINT,
    visual_measuring_survey_id BIGINT,
    CONSTRAINT pk_calculatedDefect PRIMARY KEY (id),
    CONSTRAINT FK_CALCULATED_ELEMENTS_ON_CALCULATED_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES calculated_elements (id),
    CONSTRAINT FK_CALCULATED_ELEMENTS_ON_CALCULATED_PARTS_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES calculated_parts_elements (id)
);

CREATE TABLE IF NOT EXISTS CALCULATED_REPAIRS
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    repair_id       BIGINT                                  NOT NULL,
    repair_name     VARCHAR                                 NOT NULL,
    element_id      BIGINT                                  NOT NULL,
    part_element_id BIGINT,
    CONSTRAINT pk_calculatedRepair PRIMARY KEY (id),
    CONSTRAINT FK_CALCULATED_REPAIRS_ON_CALCULATED_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES calculated_elements (id),
    CONSTRAINT FK_CALCULATED_REPAIRS_ON_CALCULATED_PARTS_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES calculated_parts_elements (id)
);

CREATE TABLE IF NOT EXISTS CALCULATED_PARAMETERS
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    measurement_number INTEGER                                 NOT NULL,
    parameter_number   INTEGER                                 NOT NULL,
    parameter_name     VARCHAR                                 NOT NULL,
    min_value          DOUBLE PRECISION                        NOT NULL,
    max_value          DOUBLE PRECISION,
    unit_measurement   VARCHAR                                 NOT NULL,
    quantity           INTEGER,
    defect_id          BIGINT,
    repair_id          BIGINT,
    control_id         BIGINT,
    CONSTRAINT pk_calculatedParameter PRIMARY KEY (id),
    CONSTRAINT FK_CALCULATED_PARAMETER_ON_CALCULATED_DEFECTS FOREIGN KEY (defect_id) REFERENCES calculated_defects (id),
    CONSTRAINT FK_CALCULATED_PARAMETER_ON_CALCULATED_REPAIRS FOREIGN KEY (repair_id) REFERENCES calculated_repairs (id),
    CONSTRAINT FK_CALCULATED_PARAMETER_ON_VISUAL_MEASURING_CONTROLS
        FOREIGN KEY (control_id) REFERENCES visual_measuring_controls (id)
);

CREATE TABLE IF NOT EXISTS IDENTIFIED_DEFECTS
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id            BIGINT                                  NOT NULL,
    defect_id               BIGINT                                  NOT NULL,
    defect_name             VARCHAR                                 NOT NULL,
    not_meet_requirements   BOOLEAN                                 NOT NULL,
    use_calculate_thickness BOOLEAN                                 NOT NULL,
    element_id              BIGINT                                  NOT NULL,
    element_name            VARCHAR                                 NOT NULL,
    part_element_id         BIGINT,
    part_element_name       VARCHAR,
    quantity                INTEGER                                 NOT NULL,
    CONSTRAINT pk_identifiedDefect PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS COMPLETED_REPAIRS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id      BIGINT                                  NOT NULL,
    repair_id         BIGINT                                  NOT NULL,
    repair_name       VARCHAR                                 NOT NULL,
    element_id        BIGINT                                  NOT NULL,
    element_name      VARCHAR                                 NOT NULL,
    quantity          INTEGER                                 NOT NULL,
    part_element_id   BIGINT,
    part_element_name VARCHAR,
    CONSTRAINT pk_completedRepair PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PARAMETER_MEASUREMENTS
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    parameter_id     BIGINT                                  NOT NULL,
    parameter_name   VARCHAR                                 NOT NULL,
    value            DOUBLE PRECISION                        NOT NULL,
    unit_measurement VARCHAR                                 NOT NULL,
    defect_id        BIGINT,
    repair_id        BIGINT,
    vm_control_id    BIGINT,
    CONSTRAINT pk_parameterMeasurement PRIMARY KEY (id),
    CONSTRAINT FK_PARAMETER_MEASUREMENTS_ON_IDENTIFIED_DEFECTS FOREIGN KEY (defect_id) REFERENCES identified_defects (id),
    CONSTRAINT FK_PARAMETER_MEASUREMENTS_ON_COMPLETED_REPAIRS FOREIGN KEY (repair_id) REFERENCES completed_repairs (id),
    CONSTRAINT FK_PARAMETER_MEASUREMENTS_ON_VISUAL_MEASURING_CONTROLS
        FOREIGN KEY (vm_control_id) REFERENCES visual_measuring_controls (id)
);

CREATE TABLE IF NOT EXISTS REMARKS_BY_EQUIPMENT
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id      BIGINT                                  NOT NULL,
    element_id        BIGINT                                  NOT NULL,
    element_name      VARCHAR                                 NOT NULL,
    part_element_id   BIGINT,
    part_element_name VARCHAR,
    inspection        VARCHAR                                 NOT NULL,
    CONSTRAINT pk_remarkByEquipment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS UT_MEASUREMENTS
(
    id                    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    diameter              INTEGER,
    measurement_number    INTEGER,
    min_measurement_value DOUBLE PRECISION                        NOT NULL,
    max_measurement_value DOUBLE PRECISION                        NOT NULL,
    max_corrosion         DOUBLE PRECISION,
    residual_thickness    DOUBLE PRECISION                        NOT NULL,
    min_acceptable_value  DOUBLE PRECISION,
    validity              VARCHAR                                 NOT NULL,
    acceptable            BOOLEAN,
    invalid               BOOLEAN,
    approaching_invalid   BOOLEAN,
    reached_invalid       BOOLEAN,
    no_standard           BOOLEAN,
    CONSTRAINT pk_ultrasonicThicknessMeasurement PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS UT_ELEMENTS
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id   BIGINT                                  NOT NULL,
    element_id     BIGINT                                  NOT NULL,
    element_name   VARCHAR                                 NOT NULL,
    measurement_id BIGINT,
    CONSTRAINT pk_ultrasonicThicknessElementMeasurement PRIMARY KEY (id),
    CONSTRAINT FK_UT_ELEMENT_ON_UT_MEASUREMENTS FOREIGN KEY (measurement_id) REFERENCES ut_measurements (id)
);

CREATE TABLE IF NOT EXISTS UT_PARTS_ELEMENTS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    part_element_id   BIGINT                                  NOT NULL,
    part_element_name VARCHAR                                 NOT NULL,
    measurement_id    BIGINT                                  NOT NULL,
    element_id        BIGINT,
    CONSTRAINT pk_ultrasonicThicknessPartElementMeasurement PRIMARY KEY (id),
    CONSTRAINT FK_UT_PARTS_ELEMENTS_ON_UT_MEASUREMENTS FOREIGN KEY (measurement_id) REFERENCES ut_measurements (id),
    CONSTRAINT FK_UT_PARTS_ELEMENTS_ON_UT_ELEMENTS FOREIGN KEY (element_id) REFERENCES ut_elements (id)
);

CREATE TABLE IF NOT EXISTS HARDNESS_MEASUREMENTS
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    nominal_size       INTEGER,
    measurement_number INTEGER,
    measurement_value  DOUBLE PRECISION                        NOT NULL,
    validity           VARCHAR                                 NOT NULL,
    validity_value     BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_hardnessMeasurement PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS HARDNESS_ELEMENTS
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id   BIGINT                                  NOT NULL,
    element_id     BIGINT                                  NOT NULL,
    element_name   VARCHAR                                 NOT NULL,
    measurement_id BIGINT,
    CONSTRAINT pk_elementHardnessMeasurement PRIMARY KEY (id),
    CONSTRAINT FK_HARDNESS_ELEMENTS_ON_HARDNESS_MEASUREMENTS
        FOREIGN KEY (measurement_id) REFERENCES hardness_measurements (id)
);

CREATE TABLE IF NOT EXISTS HARDNESS_PARTS_ELEMENTS
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    part_element_id   BIGINT                                  NOT NULL,
    part_element_name VARCHAR                                 NOT NULL,
    measurement_id    BIGINT                                  NOT NULL,
    element_id        BIGINT,
    CONSTRAINT pk_partElementHardnessMeasurement PRIMARY KEY (id),
    CONSTRAINT FK_HARDNESS_PARTS_ELEMENTS_ON_HARDNESS_MEASUREMENTS
        FOREIGN KEY (measurement_id) REFERENCES hardness_measurements (id),
    CONSTRAINT FK_HARDNESS_PARTS_ELEMENTS_ON_HARDNESS_ELEMENTS
        FOREIGN KEY (element_id) REFERENCES hardness_elements (id)
);

CREATE TABLE IF NOT EXISTS RECOMMENDATION_EMPLOYEE
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    equipment_id   BIGINT                                  NOT NULL,
    recommendation VARCHAR                                 NOT NULL,
    CONSTRAINT pk_recommendationEmployee PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ULTRASONIC_CONTROLS
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    work_journal_id     BIGINT                                  NOT NULL,
    standard_size       VARCHAR                                 NOT NULL,
    welded_joint_number INTEGER,
    measurement         VARCHAR                                 NOT NULL,
    estimation          VARCHAR                                 NOT NULL,
    CONSTRAINT pk_ultrasonicControl PRIMARY KEY (id)
);