package ru.nabokovsg.diagnosedNK.model.measurement.geodesy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipment_geodesic_measurements")
public class EquipmentGeodesicMeasurements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @OneToMany(mappedBy = "geodesicMeasurements", fetch = FetchType.LAZY)
    private Set<ReferencePoint> referencePoints;
    @OneToMany(mappedBy = "geodesicMeasurements", fetch = FetchType.LAZY)
    private Set<ControlPoint> controlPoints;
    @OneToMany(mappedBy = "geodesicMeasurements", fetch = FetchType.LAZY)
    private Set<PointDifference> pointDifferences;
}