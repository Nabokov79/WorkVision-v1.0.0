package ru.nabokovsg.diagnosedNK.model.measurement.geodesy;

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
@Table(name = "geodesic_measurements_points")
public class GeodesicMeasurementsPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "measurement_number")
    private int measurementNumber;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "number_measurement_location")
    private Integer numberMeasurementLocation;
    @Column(name = "reference_point_value")
    private Integer referencePointValue;
    @Column(name = "control_point_value")
    private Integer controlPointValue;
    @Column(name = "transition_value")
    private Integer transitionValue;
}