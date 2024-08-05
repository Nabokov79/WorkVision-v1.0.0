package ru.nabokovsg.diagnosedNK.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstUnitMeasurementService;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculationMeasurementServiceImpl implements CalculationMeasurementService {

    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;

    @Override
    public ParameterMeasurement countMin(ParameterMeasurement parameterMeasurement
            , ParameterMeasurementDto parameterMeasurementDto) {
        if (parameterMeasurementDto.getValue() == null) {
            throw new NotFoundException(
                    String.format("To calculate the minimum" +
                            ", the measured value must not be zero, measurement=%s", parameterMeasurementDto.getValue())
            );
        }
        if (parameterMeasurement.getMinValue() == null
                || parameterMeasurementDto.getValue() > parameterMeasurement.getMinValue()) {
            parameterMeasurement.setMinValue(parameterMeasurementDto.getValue());
        }
        return parameterMeasurement;
    }

    @Override
    public ParameterMeasurement countMax(ParameterMeasurement parameterMeasurement
            , ParameterMeasurementDto parameterMeasurementDto) {
        if (parameterMeasurementDto.getValue() == null) {
            throw new NotFoundException(
                    String.format("To calculate the minimum" +
                            ", the measured value must not be zero, measurement=%s", parameterMeasurementDto.getValue())
            );
        }
        if (parameterMeasurement.getMaxValue() == null
                || parameterMeasurement.getMaxValue() < parameterMeasurementDto.getValue()) {
            parameterMeasurement.setMaxValue(parameterMeasurementDto.getValue());
        }
        return parameterMeasurement;
    }

    @Override
    public ParameterMeasurement countMaxAndMin(ParameterMeasurement parameterMeasurement
                                             , ParameterMeasurementDto parameterMeasurementDto) {
        if (parameterMeasurementDto.getValue() == null) {
            throw new NotFoundException(
                    String.format("To calculate the minimum" +
                            ", the measured value must not be zero, measurement=%s", parameterMeasurementDto.getValue())
            );
        }
        countMin(parameterMeasurement, parameterMeasurementDto);
        countMax(parameterMeasurement, parameterMeasurementDto);
        return parameterMeasurement;
    }

    @Override
    public Set<ParameterMeasurement> countQuantity(Map<Long, ParameterMeasurement> parameterMeasurements
                                                 , Map<String, ParameterMeasurementDto> parameterMeasurementsDto) {
        String parameterName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.PIECES));
        Double firstValue = parameterMeasurementsDto.get(parameterName).getValue();
        ParameterMeasurement quantity = parameterMeasurements.get(parameterMeasurementsDto.get(parameterName).getParameterId());
        if (quantity == null) {
            quantity = new ParameterMeasurement(null, null, null, parameterName, null, null, unitMeasurement, null, null);
        }
        if (quantity.getMinValue() == null && firstValue == null) {
            quantity.setMinValue(1.0);
        } else {
            if (firstValue != null) {
                quantity.setMinValue(quantity.getMinValue() + firstValue);
            } else {
                quantity.setMinValue(quantity.getMinValue() + 1.0);
            }
        }
        parameterMeasurements.put(quantity.getId(), quantity);
        return new HashSet<>(parameterMeasurements.values());
    }

    @Override
    public Set<ParameterMeasurement> countSquare(Map<Long, ParameterMeasurement> parameterMeasurements
                                               , Map<String, ParameterMeasurementDto> parameterMeasurementsDto) {
        String measuredParameter = constParameter.get(String.valueOf(MeasuredParameterType.SQUARE));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.M_2));
        String length = constParameter.get(String.valueOf(MeasuredParameterType.LENGTH));
        String width = constParameter.get(String.valueOf(MeasuredParameterType.WIDTH));
        String height = constParameter.get(String.valueOf(MeasuredParameterType.HEIGHT));
        String diameter = constParameter.get(String.valueOf(MeasuredParameterType.DIAMETER));
        ParameterMeasurement parameterMeasurement
                = parameterMeasurements.get(parameterMeasurementsDto.get(measuredParameter).getParameterId());
        Double square = parameterMeasurementsDto.get(measuredParameter).getValue();
        if (square == null) {
            if (parameterMeasurementsDto.get(length) != null) {
                if (parameterMeasurementsDto.get(width) != null) {
                    square = parameterMeasurementsDto.get(length).getValue()
                            * parameterMeasurementsDto.get(width).getValue();
                }
                if (parameterMeasurementsDto.get(height) != null
                        && parameterMeasurementsDto.get(width) == null) {
                    square = parameterMeasurementsDto.get(length).getValue()
                            * parameterMeasurementsDto.get(height).getValue();
                }
            }
            if (parameterMeasurementsDto.get(diameter) != null) {
                double rad = parameterMeasurementsDto.get(diameter).getValue() / 2;
                if (parameterMeasurementsDto.get(height) != null) {
                    square = 2 * Math.PI * rad * parameterMeasurementsDto.get(height).getValue() * 100 / 100;
                } else {
                    square = Math.PI * rad * rad * 100 / 100;
                }
            }
            if (square != null && parameterMeasurement.getUnitMeasurement().equals(unitMeasurement)) {
                square /= 1000000;
            }
            parameterMeasurement.setMinValue(square);
            parameterMeasurements.put(parameterMeasurement.getId(), parameterMeasurement);
            if (Objects.equals(square, parameterMeasurement.getMinValue())) {
                return countQuantity(parameterMeasurements, parameterMeasurementsDto);
            }
        }
        return new HashSet<>(parameterMeasurements.values());
    }
}