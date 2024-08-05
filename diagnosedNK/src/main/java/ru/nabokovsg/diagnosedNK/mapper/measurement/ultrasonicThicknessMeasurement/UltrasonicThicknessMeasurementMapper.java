package ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

@Mapper(componentModel = "spring")
public interface UltrasonicThicknessMeasurementMapper {

    UltrasonicThicknessMeasurement mapToUltrasonicThicknessMeasurement(UltrasonicThicknessMeasurementDto measurementDto);

    UltrasonicThicknessMeasurement mapToUpdateUltrasonicThicknessMeasurement(
            @MappingTarget UltrasonicThicknessMeasurement measurement
                         , UltrasonicThicknessMeasurementDto measurementDto);
}