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
public class CalculateParameterMeasurement {

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
                ", integerValue=" + integerValue +
                ", unitMeasurement='" + unitMeasurement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateParameterMeasurement that = (CalculateParameterMeasurement) o;
        return Objects.equals(parameterName, that.parameterName)
                && Objects.equals(minValue, that.minValue)
                && Objects.equals(maxValue, that.maxValue)
                && Objects.equals(integerValue, that.integerValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterName, minValue, maxValue, integerValue);
    }
}