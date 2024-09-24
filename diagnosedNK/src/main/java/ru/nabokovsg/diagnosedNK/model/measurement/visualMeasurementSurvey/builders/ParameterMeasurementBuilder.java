package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders;

import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.TypeVMSData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

import java.util.List;
import java.util.Set;

public class ParameterMeasurementBuilder {

    private final TypeVMSData typeData;
    private final IdentifiedDefect defect;
    private final CompletedRepair repair;
    private final VisualMeasurementControl vmControl;
    private final Set<MeasuredParameter> measuredParameters;
    private final List<ParameterMeasurementDto> parameterMeasurements;

    public ParameterMeasurementBuilder(Builder builder) {
        this.typeData = builder.typeData;
        this.defect = builder.defect;
        this.repair = builder.repair;
        this.vmControl = builder.vmControl;
        this.measuredParameters = builder.measuredParameters;
        this.parameterMeasurements = builder.parameterMeasurements;
    }

    public TypeVMSData getTypeData() {
        return typeData;
    }

    public IdentifiedDefect getDefect() {
        return defect;
    }

    public CompletedRepair getRepair() {
        return repair;
    }

    public VisualMeasurementControl getVmControl() {
        return vmControl;
    }

    public Set<MeasuredParameter> getMeasuredParameters() {
        return measuredParameters;
    }

    public List<ParameterMeasurementDto> getParameterMeasurement() {
        return parameterMeasurements;
    }

    public static class Builder {

        private TypeVMSData typeData;
        private IdentifiedDefect defect;
        private CompletedRepair repair;
        private VisualMeasurementControl vmControl;
        private Set<MeasuredParameter> measuredParameters;
        private List<ParameterMeasurementDto> parameterMeasurements;

        public Builder typeData(TypeVMSData typeData) {
            this.typeData = typeData;
            return this;
        }

        public Builder defect(IdentifiedDefect defect) {
            this.defect = defect;
            return this;
        }

        public Builder repair(CompletedRepair repair) {
            this.repair = repair;
            return this;
        }

        public Builder vmControl(VisualMeasurementControl vmControl) {
            this.vmControl = vmControl;
            return this;
        }

        public Builder measuredParameter(Set<MeasuredParameter> measuredParameters) {
            this.measuredParameters = measuredParameters;
            return this;
        }

        public Builder parameterMeasurements(List<ParameterMeasurementDto> parameterMeasurements) {
            this.parameterMeasurements = parameterMeasurements;
            return this;
        }

        public ParameterMeasurementBuilder build() {
            return new ParameterMeasurementBuilder(this);
        }
    }
}