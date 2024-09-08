package ru.nabokovsg.diagnosedNK.mapper.norms;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

@Mapper(componentModel = "spring")
public interface MeasuredParameterMapper {

    @Mapping(target = "elementRepair", ignore = true)
    MeasuredParameter mapToMeasuredParameter(MeasuredParameterDto parameter);

    @Mapping(source = "defect", target = "defect")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "elementRepair", ignore = true)
    MeasuredParameter mapWithDefect(@MappingTarget MeasuredParameter parameter
                                                 , Defect defect);

    @Mapping(source = "elementRepair", target = "elementRepair")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defect", ignore = true)
    MeasuredParameter mapWithElementRepair(@MappingTarget MeasuredParameter parameter
                                                        , ElementRepair elementRepair);

    MeasuredParameter mapToUpdateMeasuredParameter(@MappingTarget MeasuredParameter parameter
                                                                , MeasuredParameterDto parameterDto);
}