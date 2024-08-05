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
@Table(name = "control_points")
public class ControlPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "place_number")
    private Integer placeNumber;
    @Column(name = "calculated_height")
    private Integer calculatedHeight;
    @Column(name = "deviation")
    private Integer deviation;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "geodesic_measurement_id", nullable = false)
    private EquipmentGeodesicMeasurements geodesicMeasurements;
}