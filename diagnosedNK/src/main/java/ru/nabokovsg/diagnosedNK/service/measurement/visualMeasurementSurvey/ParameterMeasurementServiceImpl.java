package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.ParameterMeasurementRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ParameterMeasurementServiceImpl implements ParameterMeasurementService {

    private final ParameterMeasurementRepository repository;
    private final CalculationVMMeasurementService calculationService;
    private final ParametersSequenceNumberService sequenceNumberService;

    @Override
    public Set<ParameterMeasurement> save(CalculationDefectOrRepair calculation
                                        , Set<MeasuredParameter> measuredParameters
                                        , Set<ParameterMeasurement> parameterMeasurements
                                        , List<ParameterMeasurementDto> parameterMeasurementsDto) {
        return new HashSet<>(repository.saveAll(
                sequenceNumberService.set(
                        calculationService.calculation(calculation
                                                     , measuredParameters
                                                     , parameterMeasurements
                                                     , parameterMeasurementsDto))
        ));
    }
}