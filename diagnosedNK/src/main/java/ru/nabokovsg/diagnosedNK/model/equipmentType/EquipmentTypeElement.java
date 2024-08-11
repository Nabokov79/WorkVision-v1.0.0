package ru.nabokovsg.diagnosedNK.model.equipmentType;

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
@Table(name = "equipment_type_elements")
public class EquipmentTypeElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "element_name")
    private String elementName;
    @OneToMany(mappedBy = "element", fetch = FetchType.LAZY)
    private Set<EquipmentTypePartElement> partsElement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_id",  nullable = false)
    private EquipmentType equipmentType;
}