package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calculated_parameters")
public class CalculatedParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "measurement_number")
    private Integer measurementNumber;
    @Column(name = "parameter_number")
    private Integer parameterNumber;
    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "min_value")
    private Double minValue;
    @Column(name = "max_value")
    private Double maxValue;
    @Column(name = "integer_value")
    private Integer integerValue;
    @Column(name = "unit_measurement")
    private String unitMeasurement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_id",  nullable = false)
    private CalculatedDefect defect;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_id",  nullable = false)
    private CalculatedRepair repair;

    @Override
    public String toString() {
        return "CalculatedParameter{" +
                "id=" + id +
                ", measurementNumber=" + measurementNumber +
                ", parameterNumber=" + parameterNumber +
                ", parameterName='" + parameterName + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", unitMeasurement='" + unitMeasurement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatedParameter parameter = (CalculatedParameter) o;
        return Objects.equals(parameterName, parameter.parameterName)
                && Objects.equals(minValue, parameter.minValue)
                && Objects.equals(maxValue, parameter.maxValue)
                && Objects.equals(unitMeasurement, parameter.unitMeasurement)
                && Objects.equals(defect, parameter.defect)
                && Objects.equals(repair, parameter.repair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementNumber, parameterNumber, parameterName, minValue, maxValue, unitMeasurement, defect, repair);
    }
}