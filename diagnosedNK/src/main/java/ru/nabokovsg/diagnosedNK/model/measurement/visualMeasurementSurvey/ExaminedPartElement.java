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
@Table(name = "examined_parts_element")
public class ExaminedPartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "part_element_name")
    private String partElementName;
    @Column(name = "inspection")
    private String inspection;
    @OneToMany(mappedBy = "examinedPartElement", fetch = FetchType.LAZY)
    private Set<IdentifiedDefect> identifiedDefects;
    @OneToMany(mappedBy = "examinedPartElement", fetch = FetchType.LAZY)
    private Set<CompletedRepair> completedRepairs;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id",  nullable = false)
    private VisualMeasuringSurvey visualMeasuringSurvey;
}