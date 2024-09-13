package ru.nabokovsg.diagnosedNK.model.measurement.geodesy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.GeodesicPointType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "points_difference")
public class PointDifference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private GeodesicPointType geodesicPointType;
    @Column(name = "first_place_number")
    private Integer firstPlaceNumber;
    @Column(name = "second_place_number")
    private Integer secondPlaceNumber;
    @Column(name = "difference")
    private Integer difference;
    @Column(name = "acceptable_difference")
    private Boolean acceptableDifference;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "geodesic_measurement_id", nullable = false)
    private EquipmentGeodesicMeasurements geodesicMeasurements;
}