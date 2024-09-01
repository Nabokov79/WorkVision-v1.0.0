package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parameter_measurements")
public class ParameterMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parameter_id")
    private Long parameterId;
    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "value")
    private Double value;
    @Column(name = "unit_measurement")
    private String unitMeasurement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_id", nullable = false)
    private IdentifiedDefect identifiedDefect;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_id", nullable = false)
    private CompletedRepair completedRepair;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vm_control_id", nullable = false)
    private VisualMeasurementControl visualMeasurementControl;

    @Override
    public String toString() {
        return "ParameterMeasurement{" +
                "id=" + id +
                ", parameterId=" + parameterId +
                ", parameterName='" + parameterName + '\'' +
                ", value=" + value +
                ", unitMeasurement='" + unitMeasurement + '\'' +
                '}';
    }
}