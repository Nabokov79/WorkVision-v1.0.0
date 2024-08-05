package ru.nabokovsg.company.model.enums;

import java.util.Optional;

public enum DivisionType {

    ORGANIZATION,
    BRANCH,
    DEPARTMENT,
    HEAT_SUPPLY_AREA,
    EXPLOITATION_REGION,
    BUILDING;

    public static Optional<DivisionType> from(String division) {
        for (DivisionType type : values()) {
            if (type.name().equalsIgnoreCase(division)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}