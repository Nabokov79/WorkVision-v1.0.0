package ru.nabokovsg.diagnosedNK.service.constantService;

import org.springframework.stereotype.Component;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

@Component
public class ConstParameterMeasurementServiceImpl implements ConstParameterMeasurementService {

    @Override
    public String getParameterName(String measuredParameter) {
        MeasuredParameterType type = MeasuredParameterType.from(measuredParameter).orElseThrow(
                () -> new BadRequestException(String.format("Unknown parameterMeasurement=%s", measuredParameter)));
        switch (type) {
            case LENGTH -> {
                return "длина";
            }
            case WIDTH -> {
                return "ширина";
            }
            case HEIGHT -> {
                return "высота";
            }
            case DEPTH -> {
                return "глубина";
            }
            case DIAMETER -> {
                return "диаметр";
            }
            case SQUARE -> {
                return "площадь";
            }
            case QUANTITY -> {
                return "количество";
            }
            case DIRECTION -> {
                return "направление";
            }
            default -> throw new BadRequestException(
                                String.format("ParameterMeasurement=%s is not supported", measuredParameter));
        }
    }

    @Override
    public String getUnitMeasurement(String unitMeasurement) {
        UnitMeasurementType type = UnitMeasurementType.from(unitMeasurement).orElseThrow(
                () -> new BadRequestException(String.format("Unknown unitMeasurement=%s", unitMeasurement)));
        switch (type) {
            case MM -> {
                return "мм";
            }
            case M_2 -> {
                return "м2";
            }
            case MM_2 -> {
                return "мм2";
            }
            case PIECES -> {
                return "шт";
            }
            default -> throw new BadRequestException(
                                 String.format("UnitMeasurement=%s is not supported", unitMeasurement));
        }
    }
}