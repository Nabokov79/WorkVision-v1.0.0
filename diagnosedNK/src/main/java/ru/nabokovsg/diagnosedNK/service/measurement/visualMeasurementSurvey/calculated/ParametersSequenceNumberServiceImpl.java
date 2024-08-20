package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.ParametersSequenceNumberMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParametersSequenceNumberServiceImpl implements ParametersSequenceNumberService {

    private final ConstParameterMeasurementService measurementService;
    private final ParametersSequenceNumberMapper mapper;

    @Override
    public Set<ParameterMeasurement> set(Set<ParameterMeasurement> parameters) {
        int measurementNumber = 1;
        int sequentialNumber = 1;
        int size = 0;
        Map<String, Integer> startNumbers = new HashMap<>();
        for (ParameterMeasurement parameter : parameters) {
            if (parameter.getMeasurementNumber().equals(measurementNumber)) {
                startNumbers.put(parameter.getParameterName(), parameter.getParameterNumber());
            }
            if (parameter.getMeasurementNumber() != null) {
                size++;
            }
        }
        if (startNumbers.isEmpty()) {
            setSequenceNumberForEmptyStartNumbers(measurementNumber, sequentialNumber, parameters);
        } else {
            setParametersSequenceNumber(measurementNumber, sequentialNumber, size, parameters, startNumbers);
        }
        return parameters;
    }

    private void setSequenceNumberForEmptyStartNumbers(int measurementNumber, int sequentialNumber
                                                            , Set<ParameterMeasurement> parameters) {
        String square = measurementService.get(String.valueOf(MeasuredParameterType.SQUARE));
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        int size = parameters.size();
        for (ParameterMeasurement parameter : parameters) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(square)) {
                    mapper.map(parameter,measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(quantity)) {
                    mapper.map(parameter,measurementNumber, size);
                } else {
                    mapper.map(parameter,measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
    }

    private void setParametersSequenceNumber(int measurementNumber, int sequentialNumber, int size
                                                               , Set<ParameterMeasurement> parameters
                                                               , Map<String, Integer> startNumbers) {
        for (ParameterMeasurement parameter : parameters) {
            if (parameter.getMeasurementNumber() == null && startNumbers.isEmpty()) {
                mapper.map(parameter,measurementNumber, sequentialNumber);
            } else if (!parameter.getMeasurementNumber().equals(measurementNumber)) {
                mapper.map(parameter,measurementNumber, startNumbers.get(parameter.getParameterName()) + size);
            }
        }
    }
}