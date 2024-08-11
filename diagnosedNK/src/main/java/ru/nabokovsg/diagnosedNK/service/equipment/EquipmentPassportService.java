package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.ResponseEquipmentPassportDto;

import java.util.List;

public interface EquipmentPassportService {

    ResponseEquipmentPassportDto save(EquipmentPassportDto passportDto);

    ResponseEquipmentPassportDto update(EquipmentPassportDto passportDto);

    List<ResponseEquipmentPassportDto> getAll(Long id);

    void delete(Long id);
}