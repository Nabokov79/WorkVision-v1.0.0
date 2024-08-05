package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;

import java.util.Map;
import java.util.Set;

public interface CalculationMeasurementService {

    ParameterMeasurement countMin(ParameterMeasurement parameterMeasurement
                                , ParameterMeasurementDto parameterMeasurementDto);

    ParameterMeasurement countMax(ParameterMeasurement parameterMeasurement
                                , ParameterMeasurementDto parameterMeasurementDto);

    ParameterMeasurement countMaxAndMin(ParameterMeasurement parameterMeasurement
                                      , ParameterMeasurementDto parameterMeasurementDto);

    Set<ParameterMeasurement> countSquare(Map<Long, ParameterMeasurement> parameterMeasurements
                                        , Map<String, ParameterMeasurementDto> parameterMeasurementsDto);

   Set<ParameterMeasurement> countQuantity(Map<Long, ParameterMeasurement> parameterMeasurements
                                         , Map<String, ParameterMeasurementDto> parameterMeasurementsDto);
}