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
@Table(name = "visual_measuring_surveys")
public class VisualMeasuringSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "element_name")
    private String elementName;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<ExaminedPartElement> examinedPartElements;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<IdentifiedDefect> identifiedDefects;
    @OneToMany(mappedBy = "visualMeasuringSurvey", fetch = FetchType.LAZY)
    private Set<CompletedRepair> completedRepairs;
    @Column(name = "inspection")
    private String inspection;
}