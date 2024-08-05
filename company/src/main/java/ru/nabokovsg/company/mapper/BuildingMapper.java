package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.building.BuildingDto;
import ru.nabokovsg.company.dto.building.ResponseBuildingDto;
import ru.nabokovsg.company.dto.building.ShortResponseBuildingDto;
import ru.nabokovsg.company.model.Address;
import ru.nabokovsg.company.model.Building;
import ru.nabokovsg.company.model.ExploitationRegion;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    @Mapping(source = "buildingDto.login", target = "login")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "building", target = "buildingType")
    @Mapping(source = "exploitationRegion", target = "exploitationRegion")
    @Mapping(source = "buildingDto.id", target = "id")
    Building mapToBuilding(BuildingDto buildingDto
                         , String building
                         , Address address
                         , ExploitationRegion exploitationRegion);

    ResponseBuildingDto mapToFullBuildingDto(Building building);

    ShortResponseBuildingDto mapToShortBuildingDto(Building building);
}