package ru.nabokovsg.diagnosedEquipment.service.equipmentType;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.EquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.ResponseEquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.equipmentType.EquipmentTypeMapper;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentType;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.QEquipmentType;
import ru.nabokovsg.diagnosedEquipment.repository.equipmentType.EquipmentTypeRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    private final EquipmentTypeRepository repository;
    private final EquipmentTypeMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseEquipmentTypeDto save(EquipmentTypeDto equipmentTypeDto) {
        return mapper.mapResponseEquipmentTypeDto(
                Objects.requireNonNullElseGet(getByPredicate(equipmentTypeDto),
                                            () -> repository.save(mapper.mapToEquipmentType(equipmentTypeDto))));
    }

    @Override
    public ResponseEquipmentTypeDto update(EquipmentTypeDto equipmentTypeDto) {
        if (repository.existsById(equipmentTypeDto.getId())) {
            return mapper.mapResponseEquipmentTypeDto(repository.save(mapper.mapToEquipmentType(equipmentTypeDto)));
        }
        throw new NotFoundException(
                String.format("Equipment type with id=%s not found for update", equipmentTypeDto.getId()));
    }

    @Override
    public ResponseEquipmentTypeDto get(Long id) {
        return mapper.mapResponseEquipmentTypeDto(getById(id));
    }

    @Override
    public List<ResponseEquipmentTypeDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapResponseEquipmentTypeDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment type with id=%s not found for delete", id));
    }

    @Override
    public EquipmentType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Equipment type with id=%s not found", id)));
    }

    public EquipmentType getByPredicate(EquipmentTypeDto equipmentTypeDto) {
        QEquipmentType type = QEquipmentType.equipmentType;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(type.equipmentName.eq(equipmentTypeDto.getEquipmentName()));
        if (equipmentTypeDto.getOrientation() != null) {
            builder.and(type.orientation.eq(equipmentTypeDto.getOrientation()));
        }
        if (equipmentTypeDto.getModel() != null) {
            builder.and(type.model.eq(equipmentTypeDto.getModel()));
        }
        return new JPAQueryFactory(em)
                        .from(type)
                        .select(type)
                        .where(builder)
                        .fetchOne();
    }
}