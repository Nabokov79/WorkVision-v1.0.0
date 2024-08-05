package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.AcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.ResponseAcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableHardness;

import java.util.List;

public interface AcceptableHardnessService {

    ResponseAcceptableHardnessDto save(AcceptableHardnessDto hardnessDto);

    ResponseAcceptableHardnessDto update(AcceptableHardnessDto hardnessDto);

    List<ResponseAcceptableHardnessDto> getAll(Long equipmentTypeId);

    AcceptableHardness getByPredicate(Long equipmentTypeId, ElementData objectElementData);

    void delete(Long id);
}