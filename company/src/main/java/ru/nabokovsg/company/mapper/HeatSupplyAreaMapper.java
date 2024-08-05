package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.heatSupplyArea.HeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ShortResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.model.Branch;
import ru.nabokovsg.company.model.HeatSupplyArea;
import ru.nabokovsg.company.model.enums.DivisionType;

@Mapper(componentModel = "spring")
public interface HeatSupplyAreaMapper {

    @Mapping(source = "areaDto.fullName", target = "fullName")
    @Mapping(source = "areaDto.shortName", target = "shortName")
    @Mapping(source = "branch", target = "branch")
    @Mapping(source = "divisionType", target = "divisionType")
    @Mapping(source = "areaDto.id", target = "id")
    HeatSupplyArea mapToHeatSupplyArea(HeatSupplyAreaDto areaDto, Branch branch, DivisionType divisionType);

    ResponseHeatSupplyAreaDto mapToFullHeatSupplyAreaDto(HeatSupplyArea heatSupplyArea);

    ShortResponseHeatSupplyAreaDto mapToShortHeatSupplyAreaDto(HeatSupplyArea heatSupplyArea);
}