package ru.nabokovsg.company.service;

import ru.nabokovsg.company.dto.heatSupplyArea.HeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ShortResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.model.HeatSupplyArea;

import java.util.List;

public interface HeatSupplyAreaService {

    ShortResponseHeatSupplyAreaDto save(HeatSupplyAreaDto areaDto);

    ShortResponseHeatSupplyAreaDto update(HeatSupplyAreaDto areaDto);

    ResponseHeatSupplyAreaDto get(Long id);

    HeatSupplyArea getById(Long id);

    List<ShortResponseHeatSupplyAreaDto> getAll(Long id);

    void delete(Long id);
}