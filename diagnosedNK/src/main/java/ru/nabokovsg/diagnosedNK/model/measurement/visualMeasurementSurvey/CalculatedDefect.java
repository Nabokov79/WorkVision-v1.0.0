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
@Table(name = "calculated_defects")
public class CalculatedDefect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "not_meet_requirements")
    private Boolean notMeetRequirements;
    @Column(name = "welded_joint_number")
    private Integer weldedJointNumber;
    @OneToMany(mappedBy = "elementDefect", fetch = FetchType.LAZY)
    private Set<CalculatedParameter> parameters;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id", nullable = false)
    private CalculatedElement element;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "part_element_id", nullable = false)
    private CalculatedPartElement partElement;
}