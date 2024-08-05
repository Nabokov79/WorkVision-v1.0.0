package ru.nabokovsg.diagnosedNK.model.norms;

import java.util.Optional;

public enum UnitMeasurementType {

    MM,
    M_2,
    MM_2,
    PIECES,
    NOT;

    public static Optional<UnitMeasurementType> from(String unit) {
        for (UnitMeasurementType type : values()) {
            if (type.name().equalsIgnoreCase(unit)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
