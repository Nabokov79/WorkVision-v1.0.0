package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.StandardSize;

public interface StandardSizeService {

    StandardSize save(StandardSizeDto standardSizeDto);

    StandardSize update(StandardSizeDto standardSizeDto);
}