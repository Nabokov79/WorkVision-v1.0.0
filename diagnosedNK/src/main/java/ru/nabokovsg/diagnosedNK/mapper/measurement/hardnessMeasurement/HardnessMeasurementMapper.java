package ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;

@Mapper(componentModel = "spring")
public interface HardnessMeasurementMapper {

    HardnessMeasurement mapToHardnessMeasurement(HardnessMeasurementDto measurementDto);

    HardnessMeasurement mapToUpdateHardnessMeasurement(@MappingTarget HardnessMeasurement measurement
                                                                    , HardnessMeasurementDto measurementDto);

    UTPredicateData mapToUTPredicateData(HardnessMeasurementDto measurementDto);

    @Mapping(source = "validityValue", target = "validityValue")
    @Mapping(source = "validity", target = "validity")
    HardnessMeasurement mapHardnessMeasurementWithAcceptableValue(@MappingTarget HardnessMeasurement measurement
                                                                               , Boolean validityValue
                                                                               , String validity);
}