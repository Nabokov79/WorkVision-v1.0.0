package ru.nabokovsg.diagnosedNK.service.norms;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.AcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.ResponseAcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.norms.AcceptableHardnessMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableHardness;
import ru.nabokovsg.diagnosedNK.model.norms.QAcceptableHardness;
import ru.nabokovsg.diagnosedNK.repository.norms.AcceptableHardnessRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AcceptableHardnessServiceImpl implements AcceptableHardnessService {

    private final AcceptableHardnessRepository repository;
    private final AcceptableHardnessMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseAcceptableHardnessDto save(AcceptableHardnessDto hardnessDto) {
        return mapper.mapToResponseAcceptableHardnessDto(
                Objects.requireNonNullElseGet(
                        repository.findByEquipmentTypeIdAndElementId(hardnessDto.getEquipmentTypeId()
                                                                   , hardnessDto.getElementId())
                        , () -> repository.save(mapper.mapToAcceptableHardness(hardnessDto))));
    }

    @Override
    public ResponseAcceptableHardnessDto update(AcceptableHardnessDto hardnessDto) {
        if (repository.existsById(hardnessDto.getId())) {
            return mapper.mapToResponseAcceptableHardnessDto(
                    repository.save(mapper.mapToAcceptableHardness(hardnessDto))
            );
        }
        throw new NotFoundException(
                String.format("AcceptableHardness with id=%s not found for update", hardnessDto.getId())
        );
    }

    @Override
    public List<ResponseAcceptableHardnessDto> getAll(Long equipmentTypeId) {
        return repository.findAllByEquipmentTypeId(equipmentTypeId)
                .stream()
                .map(mapper::mapToResponseAcceptableHardnessDto)
                .toList();
    }

    @Override
    public AcceptableHardness getByPredicate(Long equipmentTypeId, ElementData objectElementData) {
        QAcceptableHardness acceptableHardness = QAcceptableHardness.acceptableHardness;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(acceptableHardness.equipmentTypeId.eq(equipmentTypeId));
        builder.and(acceptableHardness.elementId.eq(objectElementData.getElementTypeId()));
        if (objectElementData.getPartElementId() != null) {
            builder.and(acceptableHardness.partElementId.eq(objectElementData.getPartElementTypeId()));
        }
        if (objectElementData.getMaxDiameter() != null) {
            builder.and(acceptableHardness.minAllowableDiameter.eq(objectElementData.getMinDiameter()));
        }
        AcceptableHardness acceptable = new JPAQueryFactory(em).from(acceptableHardness)
                .select(acceptableHardness)
                .where(builder)
                .fetchOne();
        if (acceptable == null) {
            throw new NotFoundException("AcceptableHardness with id=%s not found");
        }
        return acceptable;
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("AcceptableHardness with id=%s not found for delete", id));
    }
}