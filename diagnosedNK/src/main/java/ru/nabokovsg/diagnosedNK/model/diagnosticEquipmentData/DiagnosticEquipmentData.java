package ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData;

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
@Table(name = "diagnostic_equipment_data")
public class DiagnosticEquipmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "equipment_full")
    private Boolean full;
    @Column(name = "equipment_old")
    private Boolean old;
    @Column(name = "geodesy_locations")
    private Integer geodesyLocations;
    @OneToMany(mappedBy = "equipmentData", fetch = FetchType.LAZY)
    private Set<ElementData> objectStandardSizes;
}