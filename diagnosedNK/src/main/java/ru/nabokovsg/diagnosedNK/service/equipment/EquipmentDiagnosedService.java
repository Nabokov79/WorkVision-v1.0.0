package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;

import java.util.List;

public interface EquipmentDiagnosedService {

    ResponseEquipmentDto save(EquipmentDto equipmentDto);

    ResponseEquipmentDto update(EquipmentDto equipmentDto);

    ResponseEquipmentDto get(Long id);

    List<ResponseShortEquipmentDto> getAll(Long id);

    void delete(Long id);

    EquipmentDiagnosed getById(Long id);
}