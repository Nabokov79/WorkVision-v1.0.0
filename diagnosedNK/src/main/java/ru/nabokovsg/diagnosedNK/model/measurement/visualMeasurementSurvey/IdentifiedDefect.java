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
@Table(name = "identified_defects")
public class IdentifiedDefect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "defect_id")
    private Long defectId;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "welded_joint_number")
    private Integer weldedJointNumber;
    @Column(name = "not_meet_requirements")
    private Boolean notMeetRequirements;
    @Column(name = "use_calculate_thickness")
    private Boolean useCalculateThickness;
    @OneToMany(mappedBy = "identifiedDefect", fetch = FetchType.LAZY)
    private Set<ParameterMeasurement> parameterMeasurements;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "examined_part_element_id", nullable = false)
    private ExaminedPartElement examinedPartElement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id", nullable = false)
    private VisualMeasuringSurvey visualMeasuringSurvey;
}