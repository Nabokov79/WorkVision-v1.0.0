package ru.nabokovsg.diagnosedNK.mapper.norms;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.AcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.ResponseAcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableHardness;

@Mapper(componentModel = "spring")
public interface AcceptableHardnessMapper {

    AcceptableHardness mapToAcceptableHardness(AcceptableHardnessDto hardnessDto);

    ResponseAcceptableHardnessDto mapToResponseAcceptableHardnessDto(AcceptableHardness hardness);
}