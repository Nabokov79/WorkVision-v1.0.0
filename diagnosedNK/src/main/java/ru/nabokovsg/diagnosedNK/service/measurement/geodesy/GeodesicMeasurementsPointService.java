package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.GeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseGeodesicMeasurementsPointDto;

import java.util.List;

public interface GeodesicMeasurementsPointService {

    List<ResponseGeodesicMeasurementsPointDto> save(GeodesicMeasurementsPointDto measurementDto);

    List<ResponseGeodesicMeasurementsPointDto> update(GeodesicMeasurementsPointDto measurementDto);

    List<ResponseGeodesicMeasurementsPointDto> getAll(Long equipmentId);

    void delete(Long id);
}