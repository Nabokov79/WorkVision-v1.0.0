package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;

@Mapper(componentModel = "spring")
public interface StandardSizeMapper {

    StandardSize mapToStandardSize(StandardSizeDto standardSizeDto);
}