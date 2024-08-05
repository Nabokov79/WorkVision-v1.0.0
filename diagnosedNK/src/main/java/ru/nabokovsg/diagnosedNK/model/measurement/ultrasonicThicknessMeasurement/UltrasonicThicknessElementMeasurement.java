package ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement;

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
@Table(name = "ut_elements")
public class UltrasonicThicknessElementMeasurement {

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
    private UltrasonicThicknessMeasurement measurement;
    @OneToMany(mappedBy = "elementMeasurement", fetch = FetchType.LAZY)
    private Set<UltrasonicThicknessPartElementMeasurement> partElementMeasurements;
}