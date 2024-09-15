package ru.nabokovsg.diagnosedNK.service.norms;

import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.List;

@Service
public class ValidateMeasuredParameterServiceImpl implements ValidateMeasuredParameterService {

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
            p.setParameterName(MeasuredParameterType.valueOf(p.getParameterName()).label);
            p.setUnitMeasurement(UnitMeasurementType.valueOf(p.getUnitMeasurement()).label);
        });
    }

    private void validateBySquare(List<MeasuredParameterDto> parameters) {
        boolean length = false;
        boolean width = false;
        boolean diameter = false;
        boolean quantity = true;
        for (MeasuredParameterDto parameter : parameters) {
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("SQUARE").label)) {
                throw new BadRequestException(String.format("The parameter = %s cannot be set", "SQUARE"));
            }
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("LENGTH").label)) {
                length = true;
            }
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("WIDTH").label)) {
                width = true;
            }
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("DIAMETER").label)) {
                diameter = true;
            }
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                quantity = false;
            }
        }
        if (!length && !width && !diameter) {
            throw new BadRequestException(
                    String.format("Incorrect measurement parameters have been set for calculating the area" +
                            "                       , length=%s, width=%s, diameter=%s", length, width, diameter));
        }
        if (quantity) {
            parameters.add(createParameterQuantity());
        }
    }

    private void validateQuantityParameter(List<MeasuredParameterDto> parameters) {
        boolean flag = true;
        for (MeasuredParameterDto parameter : parameters) {
            if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            parameters.add(createParameterQuantity());
        }
    }

    private MeasuredParameterDto createParameterQuantity() {
        return new MeasuredParameterDto(null
                                    , MeasuredParameterType.valueOf("QUANTITY").label
                                    , UnitMeasurementType.valueOf("PIECES").label);
    }

    private ParameterCalculationType getTypeCalculation(String calculation) {
        return ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported defect calculation type=%s", calculation)));
    }
}