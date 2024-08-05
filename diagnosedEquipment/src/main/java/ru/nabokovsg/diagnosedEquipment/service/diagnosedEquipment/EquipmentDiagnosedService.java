package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;

import java.util.List;

public interface EquipmentDiagnosedService {

    ResponseEquipmentDto save(EquipmentDto equipmentDto);

    ResponseEquipmentDto update(EquipmentDto equipmentDto);

    ResponseEquipmentDto get(Long id);

    List<ResponseShortEquipmentDto> getAll(Long id);

    void delete(Long id);

    EquipmentDiagnosed getById(Long id);
}