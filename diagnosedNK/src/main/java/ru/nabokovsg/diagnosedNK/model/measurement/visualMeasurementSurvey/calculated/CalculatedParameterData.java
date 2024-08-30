package ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Set;

public class CalculatedParameterData {

    private final Set<IdentifiedDefect> defects;
    private final Set<CompletedRepair> repairs;
    private final ParameterCalculationType calculationType;
    private final CalculatedDefect defect;
    private final CalculatedRepair repair;

    public CalculatedParameterData(Builder builder) {
        this.defects = builder.defects;
        this.repairs = builder.repairs;
        this.calculationType = builder.calculationType;
        this.defect = builder.defect;
        this.repair = builder.repair;
    }

    public Set<IdentifiedDefect> getDefects() {
        return defects;
    }

    public Set<CompletedRepair> getRepairs() {
        return repairs;
    }

    public ParameterCalculationType getCalculationType() {
        return calculationType;
    }

    public CalculatedDefect getDefect() {
        return defect;
    }

    public CalculatedRepair getRepair() {
        return repair;
    }

    public static class Builder {

        private Set<IdentifiedDefect> defects;
        private Set<CompletedRepair> repairs;
        private ParameterCalculationType calculationType;
        private CalculatedDefect defect;
        private CalculatedRepair repair;

        public Builder defects(Set<IdentifiedDefect> defects) {
            this.defects = defects;
            return this;
        }

        public Builder repairs(Set<CompletedRepair> repairs) {
            this.repairs = repairs;
            return this;
        }

        public Builder calculationType(ParameterCalculationType calculationType) {
            this.calculationType = calculationType;
            return this;
        }

        public Builder defect(CalculatedDefect defect) {
            this.defect = defect;
            return this;
        }

        public Builder repair(CalculatedRepair repair) {
            this.repair = repair;
            return this;
        }

        public CalculatedParameterData build() {
            return new CalculatedParameterData(this);
        }
    }
}