package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;

import java.util.List;

public interface ValidateMeasuredParameterService {

    void validateByCalculationType(List<MeasuredParameterDto> parameters, String calculation);
}