package ru.nabokovsg.diagnosedNK.service.constantService;

import org.springframework.stereotype.Component;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

@Component
public class ConstUnitMeasurementServiceImpl implements ConstUnitMeasurementService {

    private static final String MM = "мм";
    private static final String M_2 = "м2";
    private static final String MM_2 = "мм2";
    private static final String PIECES = "шт";

    @Override
    public String get(String unitMeasurement) {
        switch (convertToUnitMeasurement(unitMeasurement)) {
            case MM -> {
                return MM;
            }
            case M_2 -> {
                return M_2;
            }
            case MM_2 -> {
                return MM_2;
            }
            case PIECES -> {
                return PIECES;
            }
            default -> {
                return null;
            }
        }
    }

    public UnitMeasurementType convertToUnitMeasurement(String unitMeasurement) {
        return UnitMeasurementType.from(unitMeasurement).orElseThrow(
                () -> new BadRequestException(String.format("Unknown unitMeasurement=%s", unitMeasurement)));
    }
}
