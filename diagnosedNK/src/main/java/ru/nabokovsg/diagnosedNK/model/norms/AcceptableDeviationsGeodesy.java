package ru.nabokovsg.diagnosedNK.model.norms;

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
@Table(name = "acceptable_deviations_geodesy")
public class AcceptableDeviationsGeodesy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @Column(name = "fulls")
    private Boolean full;
    @Column(name = "old")
    private Boolean old;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "acceptable_precipitation")
    private Integer acceptablePrecipitation;
    @Column(name = "max_difference_neighboring_points")
    private Integer maxDifferenceNeighboringPoints;
    @Column(name = "max_difference_diametric_points")
    private Integer maxDifferenceDiametricPoints;
}