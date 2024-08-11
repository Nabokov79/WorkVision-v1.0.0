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
@Table(name = "equipment_types")
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_name")
    private String equipmentName;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "orientation")
    private String orientation;
    @Column(name = "model")
    private String model;
    @OneToMany(mappedBy = "equipmentType", fetch = FetchType.LAZY)
    private Set<EquipmentTypeElement> elements;
}