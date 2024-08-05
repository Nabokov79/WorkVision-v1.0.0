package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.ResponseEquipmentPassportDto;

import java.util.List;

public interface EquipmentPassportService {

    ResponseEquipmentPassportDto save(EquipmentPassportDto passportDto);

    ResponseEquipmentPassportDto update(EquipmentPassportDto passportDto);

    List<ResponseEquipmentPassportDto> getAll(Long id);

    void delete(Long id);
}