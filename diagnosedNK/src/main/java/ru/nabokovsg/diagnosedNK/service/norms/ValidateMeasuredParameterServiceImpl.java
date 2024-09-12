package ru.nabokovsg.diagnosedNK.service.norms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateMeasuredParameterServiceImpl implements ValidateMeasuredParameterService {

    private final ConstParameterMeasurementService constParameter;

    @Override
    public void validateByCalculationType(List<MeasuredParameterDto> parameters, String calculation) {
        setConstParameters(parameters);
        switch (getTypeCalculation(calculation)) {
            case SQUARE -> validateBySquare(parameters);
            case MAX, MIN, MAX_MIN, NO_ACTION -> validateQuantityParameter(parameters);
        }
    }

    private void setConstParameters(List<MeasuredParameterDto> parameters) {
        parameters.forEach(p -> {
            p.setParameterName(constParameter.getParameterName(p.getParameterName()));
            p.setUnitMeasurement(constParameter.getUnitMeasurement(p.getUnitMeasurement()));
        });
    }

    private void validateBySquare(List<MeasuredParameterDto> parameters) {
        String squareName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.SQUARE));
        String lengthName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.LENGTH));
        String widthName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.WIDTH));
        String diameterName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.DIAMETER));
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        String unit = constParameter.getUnitMeasurement(String.valueOf(UnitMeasurementType.PIECES));
        boolean length = false;
        boolean width = false;
        boolean diameter = false;
        boolean quantity = true;
        for (MeasuredParameterDto parameter : parameters) {
            if (parameter.getParameterName().equals(squareName)) {
                throw new BadRequestException(String.format("The parameter = %s cannot be set", squareName));
            }
            if (parameter.getParameterName().equals(lengthName)) {
                length = true;
            }
            if (parameter.getParameterName().equals(widthName)) {
                width = true;
            }
            if (parameter.getParameterName().equals(diameterName)) {
                diameter = true;
            }
            if (parameter.getParameterName().equals(quantityName)) {
                quantity = false;
            }
        }
        if (!length && !width && !diameter) {
            throw new BadRequestException(
                    String.format("Incorrect measurement parameters have been set for calculating the area" +
                            "                       , length=%s, width=%s, diameter=%s", length, width, diameter));
        }
        if (quantity) {
            parameters.add(new MeasuredParameterDto(null, quantityName, unit));
        }
    }

    private void validateQuantityParameter(List<MeasuredParameterDto> parameters) {
        boolean flag = true;
        String quantity = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        String unit = constParameter.getUnitMeasurement(String.valueOf(UnitMeasurementType.PIECES));
        for (MeasuredParameterDto parameter : parameters) {
            if (parameter.getParameterName().equals(quantity)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            parameters.add(new MeasuredParameterDto(null, quantity, unit));
        }
    }

    private ParameterCalculationType getTypeCalculation(String calculation) {
        return ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported defect calculation type=%s", calculation)));
    }
}