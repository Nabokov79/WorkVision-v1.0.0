package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visual_measuring_controls")
public class VisualMeasurementControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_journal_id")
    private Long workJournalId;
    @Column(name = "defect_id")
    private Long defectId;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "standard_size")
    private String standardSize;
    @Column(name = "welded_joint_number")
    private Integer weldedJointNumber;
    @Column(name = "coordinates")
    private String coordinates;
    @Column(name = "estimation")
    private String estimation;
    @OneToMany(mappedBy = "visualMeasurementControl", fetch = FetchType.LAZY)
    private Set<ParameterMeasurement> parameterMeasurements;
}