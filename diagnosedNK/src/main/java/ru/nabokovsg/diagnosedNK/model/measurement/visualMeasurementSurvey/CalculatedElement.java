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
@Table(name = "calculated_elements")
public class CalculatedElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "element_name")
    private String elementName;
    @Column(name = "inspection")
    private String inspection;
    @OneToMany(mappedBy = "element", fetch = FetchType.LAZY)
    private Set<CalculatedDefect> defects;
    @OneToMany(mappedBy = "element", fetch = FetchType.LAZY)
    private Set<CalculatedRepair> repairs;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "visual_measuring_survey_id",  nullable = false)
    private CalculatedVMSurvey visualMeasuringSurvey;
}