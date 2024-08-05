package ru.nabokovsg.diagnosedNK.model.norms;

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
@Table(name = "defects")
public class Defect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "not_meet_requirements")
    private Boolean notMeetRequirements;
    @Column(name = "use_calculate_thickness")
    private Boolean useCalculateThickness;
    @Column(name = "calculation")
    @Enumerated(EnumType.STRING)
    private CalculationDefectOrRepair calculation;
    @OneToMany(mappedBy = "defect", fetch = FetchType.LAZY)
    private Set<MeasuredParameter> measuredParameters;
}