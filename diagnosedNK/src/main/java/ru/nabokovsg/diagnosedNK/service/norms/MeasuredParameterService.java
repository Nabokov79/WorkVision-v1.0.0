package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

import java.util.List;
import java.util.Set;

public interface MeasuredParameterService {

    Set<MeasuredParameter> saveForDefect(Defect defect, List<MeasuredParameterDto> parametersDto);

    Set<MeasuredParameter> saveForElementRepair(ElementRepair repair, List<MeasuredParameterDto> parametersDto);

    Set<MeasuredParameter> update(List<MeasuredParameterDto> parametersDto);
}