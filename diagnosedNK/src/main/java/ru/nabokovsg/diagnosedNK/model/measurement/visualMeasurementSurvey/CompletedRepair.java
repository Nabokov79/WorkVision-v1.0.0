package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey;

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
@Table(name = "completed_repairs")
public class CompletedRepair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "repair_id")
    private Long repairId;
    @Column(name = "repair_name")
    private String repairName;
    @OneToMany(mappedBy = "completedRepair", fetch = FetchType.LAZY)
    private Set<ParameterMeasurement> parameterMeasurements;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "examined_part_element_id",  nullable = false)
    private ExaminedPartElement examinedPartElement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id",  nullable = false)
    private VisualMeasuringSurvey visualMeasuringSurvey;
}