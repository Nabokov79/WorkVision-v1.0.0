package ru.nabokovsg.diagnosedNK.model.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "equipment_parts_elements")
public class EquipmentPartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "part_element_name")
    private String partElementName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id",  nullable = false)
    @JsonIgnore
    private EquipmentElement element;
    @OneToOne
    @JoinColumn(name = "standard_size_id", referencedColumnName = "id")
    private StandardSize standardSize;
}