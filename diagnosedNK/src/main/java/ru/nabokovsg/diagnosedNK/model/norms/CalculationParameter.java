package ru.nabokovsg.diagnosedNK.model.norms;

import java.util.Optional;

public enum CalculationParameter {

    MAX,
    MIN,
    MAX_MIN,
    NO_ACTION;

    public static Optional<CalculationParameter> from(String calculation) {
        for (CalculationParameter type : values()) {
            if (type.name().equalsIgnoreCase(calculation)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}