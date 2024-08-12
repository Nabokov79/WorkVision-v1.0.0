package ru.nabokovsg.diagnosedNK.service.constantService;

import org.springframework.stereotype.Component;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;

@Component
public class ConstParameterMeasurementServiceImpl implements ConstParameterMeasurementService {

    private static final String LENGTH = "длина";
    private static final String WIDTH = "ширина";
    private static final String HEIGHT = "высота";
    private static final String DEPTH = "глубина";
    private static final String DIAMETER = "диаметр";
    private static final String SQUARE = "площадь";
    private static final String QUANTITY = "количество";
    private static final String DIRECTION = "направление";

    @Override
    public String get(String measuredParameter) {
        switch (convertToParameterMeasurement(measuredParameter)) {
            case LENGTH -> {
                return LENGTH;
            }
            case WIDTH -> {
                return WIDTH;
            }
            case HEIGHT -> {
                return HEIGHT;
            }
            case DEPTH -> {
                return DEPTH;
            }
            case DIAMETER -> {
                return DIAMETER;
            }
            case SQUARE -> {
                return SQUARE;
            }
            case QUANTITY -> {
                return QUANTITY;
            }
            case DIRECTION -> {
                return DIRECTION;
            }
            default -> throw new BadRequestException(
                    String.format("ParameterMeasurement=%s is not supported", measuredParameter));
        }
    }

    public MeasuredParameterType convertToParameterMeasurement(String measuredParameter) {
        return MeasuredParameterType.from(measuredParameter).orElseThrow(
                () -> new BadRequestException(String.format("Unknown parameterMeasurement=%s", measuredParameter)));
    }
}