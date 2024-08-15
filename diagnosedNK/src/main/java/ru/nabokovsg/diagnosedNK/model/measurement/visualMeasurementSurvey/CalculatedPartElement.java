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
@Table(name = "calculated_parts_elements")
public class CalculatedPartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_element_name")
    private String partElementName;
    @Column(name = "inspection")
    private String inspection;
    @OneToMany(mappedBy = "partElement", fetch = FetchType.LAZY)
    private Set<CalculatedDefect> defects;
    @OneToMany(mappedBy = "partElement", fetch = FetchType.LAZY)
    private Set<CalculatedRepair> repairs;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id",  nullable = false)
    private CalculatedVMSurvey visualMeasuringSurvey;
}