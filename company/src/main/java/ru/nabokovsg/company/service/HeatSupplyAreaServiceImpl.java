package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.heatSupplyArea.HeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ShortResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.HeatSupplyAreaMapper;
import ru.nabokovsg.company.model.HeatSupplyArea;
import ru.nabokovsg.company.model.enums.DivisionType;
import ru.nabokovsg.company.repository.HeatSupplyAreaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeatSupplyAreaServiceImpl implements HeatSupplyAreaService {

    private final HeatSupplyAreaRepository repository;
    private final HeatSupplyAreaMapper mapper;
    private final BranchService branchService;
    @Override
    public ShortResponseHeatSupplyAreaDto save(HeatSupplyAreaDto areaDto) {
        if (repository.existsByFullName(areaDto.getFullName())) {
            throw new BadRequestException(
                    String.format("HeatSupplyArea with full name=%s is found", areaDto.getFullName())
            );
        }
        return mapper.mapToShortHeatSupplyAreaDto(
                repository.save(mapper.mapToHeatSupplyArea(areaDto
                                                         , branchService.getById(areaDto.getBranchId())
                                                         , DivisionType.HEAT_SUPPLY_AREA))
        );
    }

    @Override
    public ShortResponseHeatSupplyAreaDto update(HeatSupplyAreaDto areaDto) {
        if (repository.existsById(areaDto.getId())) {
            return mapper.mapToShortHeatSupplyAreaDto(
                    repository.save(mapper.mapToHeatSupplyArea(areaDto
                                                        , branchService.getById(areaDto.getBranchId())
                                                        , DivisionType.HEAT_SUPPLY_AREA))
            );
        }
        throw new NotFoundException(
                String.format("HeatSupplyArea with name=%s not found for update.", areaDto.getFullName()));
    }

    @Override
    public ResponseHeatSupplyAreaDto get(Long id) {
        return mapper.mapToFullHeatSupplyAreaDto(getById(id));
    }

    @Override
    public HeatSupplyArea getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("HeatSupplyArea with id=%s not found.", id)));
    }

    @Override
    public List<ShortResponseHeatSupplyAreaDto> getAll(Long branchId) {
        return repository.findAllByBranchId(branchId)
                         .stream()
                         .map(mapper::mapToShortHeatSupplyAreaDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("HeatSupplyArea with id=%s not found for delete.", id));
    }
}