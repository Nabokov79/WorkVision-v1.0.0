package ru.nabokovsg.diagnosedNK.model.measurement.geodesy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reference_points")
public class ReferencePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "place_number")
    private Integer placeNumber;
    @Column(name = "calculated_height")
    private Integer calculatedHeight;
    @Column(name = "deviation")
    private Integer deviation;
    @Column(name = "precipitation")
    private Integer precipitation;
    @OneToMany(mappedBy = "referencePoint", fetch = FetchType.LAZY)
    private List<DeviationYear> deviationYeas;
    @Column(name = "acceptable_precipitation")
    private Boolean acceptablePrecipitation;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "geodesic_measurement_id", nullable = false)
    private EquipmentGeodesicMeasurements geodesicMeasurements;
}