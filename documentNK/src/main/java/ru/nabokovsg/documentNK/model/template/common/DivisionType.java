package ru.nabokovsg.documentNK.model.template.common;

import java.util.Optional;

public enum DivisionType {

    ORGANIZATION,
    BRANCH,
    DEPARTMENT,
    HEAT_SUPPLE_AREA,
    EXPLOITATION_REGION;

    public static Optional<DivisionType> from(String division) {
        for (DivisionType type : values()) {
            if (type.name().equalsIgnoreCase(division)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}