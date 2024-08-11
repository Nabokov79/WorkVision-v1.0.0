package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;

public interface StandardSizeService {

    StandardSize save(StandardSizeDto standardSizeDto);

    StandardSize update(StandardSizeDto standardSizeDto);
}