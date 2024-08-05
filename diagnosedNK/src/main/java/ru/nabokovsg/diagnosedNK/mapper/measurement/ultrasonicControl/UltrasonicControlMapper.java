package ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicControl;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.ResponseUltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.UltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicControl.UltrasonicControl;

@Mapper(componentModel = "spring")
public interface UltrasonicControlMapper {

    UltrasonicControl mapToUltrasonicControl(UltrasonicMeasurementDto measurementDto);

    ResponseUltrasonicMeasurementDto mapToResponseUltrasonicMeasurement(UltrasonicControl measurement);
}