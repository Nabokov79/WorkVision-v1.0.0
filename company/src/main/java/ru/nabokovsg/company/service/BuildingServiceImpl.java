package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.building.BuildingDto;
import ru.nabokovsg.company.dto.building.ResponseBuildingDto;
import ru.nabokovsg.company.dto.building.ShortResponseBuildingDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.BuildingMapper;
import ru.nabokovsg.company.model.Building;
import ru.nabokovsg.company.model.ConstantBuildingType;
import ru.nabokovsg.company.repository.BuildingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl extends ConstantBuildingType implements BuildingService {

    private final BuildingRepository repository;
    private final BuildingMapper mapper;
    private final ExploitationRegionService regionService;
    private final AddressService addressService;

    @Override
    public ShortResponseBuildingDto save(BuildingDto buildingDto) {
        if (repository.existsByAddressId(buildingDto.getAddressId())) {
            throw new BadRequestException(
                    String.format("Building by address with id=%s is found", buildingDto.getAddressId())
            );
        }
        return mapper.mapToShortBuildingDto(
                repository.save(mapper.mapToBuilding(buildingDto
                                                   , get(buildingDto.getBuildingType())
                                                   , addressService.get(buildingDto.getAddressId())
                                                   , regionService.getById(buildingDto.getExploitationRegionId()))));
    }

    @Override
    public ShortResponseBuildingDto update(BuildingDto buildingDto) {
        if (repository.existsById(buildingDto.getId())) {
            return mapper.mapToShortBuildingDto(
                    repository.save(mapper.mapToBuilding(buildingDto
                                                       , get(buildingDto.getBuildingType())
                                                       , addressService.get(buildingDto.getAddressId())
                                                       , regionService.getById(buildingDto.getExploitationRegionId())))
            );
        }
        throw new NotFoundException(String.format("Building with id=%s not found for update.", buildingDto.getId()));
    }

    @Override
    public ResponseBuildingDto get(Long id) {
        return mapper.mapToFullBuildingDto(getById(id));
    }

    @Override
    public Building getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Building with id=%s not found", id)));
    }

    @Override
    public List<ShortResponseBuildingDto> getAll(Long id) {
        return repository.findAllByExploitationRegionId(id)
                         .stream()
                         .map(mapper::mapToShortBuildingDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Building with id=%s not found for delete.", id));
    }
}