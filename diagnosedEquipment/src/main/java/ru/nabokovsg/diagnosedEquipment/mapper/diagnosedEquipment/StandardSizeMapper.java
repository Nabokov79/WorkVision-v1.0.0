package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.StandardSize;

@Mapper(componentModel = "spring")
public interface StandardSizeMapper {

    StandardSize mapToStandardSize(StandardSizeDto standardSizeDto);
}