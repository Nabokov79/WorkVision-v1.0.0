package ru.nabokovsg.diagnosedNK.model.norms;

import java.util.Optional;

public enum CalculationDefectOrRepair {

    QUANTITY,
    SQUARE,
    NO_ACTION;

    public static Optional<CalculationDefectOrRepair> from(String calculation) {
        for (CalculationDefectOrRepair type : values()) {
            if (type.name().equalsIgnoreCase(calculation)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}