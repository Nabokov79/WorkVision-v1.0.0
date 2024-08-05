package ru.nabokovsg.diagnosedNK.mapper.norms;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.AcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.ResponseAcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableThickness;

@Mapper(componentModel = "spring")
public interface AcceptableThicknessMapper {

    AcceptableThickness mapToAcceptableThickness(AcceptableThicknessDto thicknessDto);

    ResponseAcceptableThicknessDto mapToResponseAcceptableThicknessDto(AcceptableThickness thickness);
}