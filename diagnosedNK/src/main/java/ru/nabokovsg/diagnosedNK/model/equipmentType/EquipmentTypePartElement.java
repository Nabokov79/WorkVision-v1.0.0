package ru.nabokovsg.diagnosedNK.model.equipmentType;

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
@Table(name = "equipment_type_parts_elements")
public class EquipmentTypePartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_name")
    private String partElementName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id",  nullable = false)
    private EquipmentTypeElement element;
}