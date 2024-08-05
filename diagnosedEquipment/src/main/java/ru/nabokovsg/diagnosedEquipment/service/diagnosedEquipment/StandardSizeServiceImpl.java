package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.StandardSizeMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.StandardSize;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.StandardSizeRepository;

@Service
@RequiredArgsConstructor
public class StandardSizeServiceImpl implements StandardSizeService {

    private final StandardSizeRepository repository;
    private final StandardSizeMapper mapper;

    @Override
    public StandardSize save(StandardSizeDto standardSizeDto) {
        return repository.save(mapper.mapToStandardSize(standardSizeDto));
    }

    @Override
    public StandardSize update(StandardSizeDto standardSizeDto) {
        if (repository.existsById(standardSizeDto.getId())) {
            return repository.save(mapper.mapToStandardSize(standardSizeDto));
        }
        throw new NotFoundException(
                String.format("StandardSize with id=%s not found for update", standardSizeDto.getId())
        );
    }
}