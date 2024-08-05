package ru.nabokovsg.company.model;

import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.model.enums.BuildingType;

public class ConstantBuildingType {

    private final static String BOILER_ROOM = "котельная";
    private final static String CHP = "ЦТП";
    private final static String IHP = "ИТП";

    public String get(String buildingType) {
        switch (convertToBuildingType(buildingType)) {
            case BOILER_ROOM -> {
                return BOILER_ROOM;
            }
            case CHP -> {
                return CHP;
            }
            case IHP -> {
                return IHP;
            }
            default -> throw new BadRequestException(
                    String.format("Unknown building type=%s", buildingType));
        }
    }

    private BuildingType convertToBuildingType(String buildingType) {
        return BuildingType.from(buildingType)
                .orElseThrow(
                       () -> new BadRequestException(String.format("Unknown data format buildingType=%s", buildingType))
                );
    }
}
