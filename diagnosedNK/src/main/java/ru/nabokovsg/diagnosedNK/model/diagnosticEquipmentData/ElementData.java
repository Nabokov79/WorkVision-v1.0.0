package ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData;

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
@Table(name = "equipment_element_data")
public class ElementData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "element_type_id")
    private Long elementTypeId;
    @Column(name = "part_element_type_id")
    private Long partElementTypeId;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "element_name")
    private String elementName;
    @Column(name = "part_element_name")
    private Long partElementName;
    @Column(name = "design_thickness")
    private Double designThickness;
    @Column(name = "min_diameter")
    private Integer minDiameter;
    @Column(name = "max_diameter")
    private Integer maxDiameter;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic_equipment_data_id",  nullable = false)
    private DiagnosticEquipmentData equipmentData;
}