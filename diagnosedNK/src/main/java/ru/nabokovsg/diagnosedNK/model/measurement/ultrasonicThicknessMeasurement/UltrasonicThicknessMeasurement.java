package ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement;

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
@Table(name = "ut_measurements")
public class UltrasonicThicknessMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diameter")
    private Integer diameter;
    @Column(name = "measurement_number")
    private Integer measurementNumber;
    @Column(name = "min_measurement_value")
    private Double minMeasurementValue;
    @Column(name = "max_measurement_value")
    private Double maxMeasurementValue;
    @Column(name = "max_corrosion")
    private Double maxCorrosion;
    @Column(name = "residual_thickness")
    private Double residualThickness;
    @Column(name = "min_acceptable_value")
    private Double minAcceptableValue;
    @Column(name = "validity")
    private String validity;
    @Column(name = "acceptable")
    private Boolean acceptable;
    @Column(name = "invalid")
    private Boolean invalid;
    @Column(name = "approaching_invalid")
    private Boolean approachingInvalid;
    @Column(name = "reached_invalid")
    private Boolean reachedInvalid;
    @Column(name = "no_standard")
    private Boolean noStandard;
}