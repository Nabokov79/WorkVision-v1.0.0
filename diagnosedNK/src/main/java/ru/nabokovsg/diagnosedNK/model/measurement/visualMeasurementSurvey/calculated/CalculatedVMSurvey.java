package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated;

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
@Table(name = "visual_measuring_surveys")
public class CalculatedVMSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "element_name")
    private String elementName;
    @Column(name = "inspection")
    private String inspection;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<CalculatedPartElement> partElements;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<CalculatedDefect> defects;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<CalculatedRepair> repairs;
}