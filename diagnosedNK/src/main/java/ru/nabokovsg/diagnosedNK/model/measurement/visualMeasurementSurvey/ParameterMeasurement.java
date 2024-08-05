package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parameter_measurements")
public class ParameterMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "measurement_number")
    private Integer measurementNumber;
    @Column(name = "parameter_number")
    private Integer parameterNumber;
    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "min_value")
    private Double minValue;
    @Column(name = "max_value")
    private Double maxValue;
    @Column(name = "unit_measurement")
    private String unitMeasurement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_id",  nullable = false)
    private IdentifiedDefect identifiedDefect;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_id",  nullable = false)
    private CompletedRepair completedRepair;




}