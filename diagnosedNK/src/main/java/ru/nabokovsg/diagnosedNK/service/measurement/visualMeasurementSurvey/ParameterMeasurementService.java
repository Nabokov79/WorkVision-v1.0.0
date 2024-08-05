package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

import java.util.List;
import java.util.Set;

public interface ParameterMeasurementService {

    Set<ParameterMeasurement> save(CalculationDefectOrRepair calculation
                                 , Set<MeasuredParameter> measuredParameters
                                 , Set<ParameterMeasurement> parameterMeasurements
                                 , List<ParameterMeasurementDto> parameterMeasurementsDto);
}