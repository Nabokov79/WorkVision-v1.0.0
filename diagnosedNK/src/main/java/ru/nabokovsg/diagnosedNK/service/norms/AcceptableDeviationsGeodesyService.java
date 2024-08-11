package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.AcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;

import java.util.List;

public interface AcceptableDeviationsGeodesyService {

    ResponseAcceptableDeviationsGeodesyDto save(AcceptableDeviationsGeodesyDto geodesyDto);

    ResponseAcceptableDeviationsGeodesyDto update(AcceptableDeviationsGeodesyDto geodesyDto);

    List<ResponseAcceptableDeviationsGeodesyDto> getAll(Long id);

    void delete(Long id);

    AcceptableDeviationsGeodesy get(EquipmentDiagnosed equipment, boolean full);
}