package ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment;

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
@Table(name = "equipment_elements")
public class EquipmentElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "element_name")
    private String elementName;
    @OneToMany(mappedBy = "element", fetch = FetchType.LAZY)
    private Set<EquipmentPartElement> partsElement;
    @OneToOne
    @JoinColumn(name = "standard_size_id", referencedColumnName = "id")
    private StandardSize standardSize;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_diagnosed_id",  nullable = false)
    private EquipmentDiagnosed equipmentDiagnosed;
}