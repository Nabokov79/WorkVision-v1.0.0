package ru.nabokovsg.diagnosedNK.service.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipment.StandardSizeMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.repository.equipment.StandardSizeRepository;

@Service
@RequiredArgsConstructor
public class StandardSizeServiceImpl implements StandardSizeService {

    private final StandardSizeRepository repository;
    private final StandardSizeMapper mapper;

    @Override
    public StandardSize save(StandardSizeDto standardSizeDto) {
        if (standardSizeDto != null) {
            return repository.save(mapper.mapToStandardSize(standardSizeDto));
        }
        return null;
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