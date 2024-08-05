package ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement;

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
@Table(name = "hardness_parts_elements")
public class PartElementHardnessMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "part_element_name")
    private String partElementName;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_id",  nullable = false)
    private HardnessMeasurement measurement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id",  nullable = false)
    private ElementHardnessMeasurement elementMeasurement;
}