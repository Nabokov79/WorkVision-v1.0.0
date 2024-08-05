package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.AcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;

import java.util.List;

public interface AcceptableDeviationsGeodesyService {

    ResponseAcceptableDeviationsGeodesyDto save(AcceptableDeviationsGeodesyDto geodesyDto);

    ResponseAcceptableDeviationsGeodesyDto update(AcceptableDeviationsGeodesyDto geodesyDto);

    List<ResponseAcceptableDeviationsGeodesyDto> getAll(Long id);

    void delete(Long id);

    AcceptableDeviationsGeodesy get(DiagnosticEquipmentData object);
}