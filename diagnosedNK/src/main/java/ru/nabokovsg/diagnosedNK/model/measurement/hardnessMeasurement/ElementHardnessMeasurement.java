package ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement;

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
@Table(name = "hardness_elements")
public class ElementHardnessMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "element_name")
    private String elementName;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_id",  nullable = false)
    private HardnessMeasurement measurement;
    @OneToMany(mappedBy = "elementMeasurement", fetch = FetchType.LAZY)
    private Set<PartElementHardnessMeasurement> partElementMeasurements;
}