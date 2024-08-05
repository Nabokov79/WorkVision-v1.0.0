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
@Table(name = "acceptable_hardness")
public class AcceptableHardness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "min_allowable_diameter")
    private Integer minAllowableDiameter;
    @Column(name = "min_allowable_thickness")
    private Double minAllowableThickness;
    @Column(name = "min_hardness")
    private Integer minHardness;
    @Column(name = "max_hardness")
    private Integer maxHardness;
}