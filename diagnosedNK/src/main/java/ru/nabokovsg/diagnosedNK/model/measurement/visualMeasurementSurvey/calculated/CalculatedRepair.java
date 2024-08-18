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
@Table(name = "calculated_repairs")
public class CalculatedRepair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "repair_name")
    private String repairName;
    @OneToMany(mappedBy = "repair", fetch = FetchType.LAZY)
    private Set<CalculatedParameter> parameters;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id", nullable = false)
    private CalculatedElement element;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "part_element_id", nullable = false)
    private CalculatedPartElement partElement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id",  nullable = false)
    private CalculatedVMSurvey visualMeasuringSurvey;
}