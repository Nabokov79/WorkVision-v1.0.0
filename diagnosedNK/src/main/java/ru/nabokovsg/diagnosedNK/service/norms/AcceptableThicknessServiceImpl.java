package ru.nabokovsg.diagnosedNK.service.norms;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.AcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.ResponseAcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.norms.AcceptableThicknessMapper;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableThickness;
import ru.nabokovsg.diagnosedNK.model.norms.QAcceptableThickness;
import ru.nabokovsg.diagnosedNK.repository.norms.AcceptableThicknessRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AcceptableThicknessServiceImpl implements AcceptableThicknessService {

    private final AcceptableThicknessRepository repository;
    private final AcceptableThicknessMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseAcceptableThicknessDto save(AcceptableThicknessDto thicknessDto) {
        return mapper.mapToResponseAcceptableThicknessDto(
                Objects.requireNonNullElseGet(getByPredicate(thicknessDto.getEquipmentTypeId()
                                , thicknessDto.getElementId()
                                , thicknessDto.getPartElementId()
                                , thicknessDto.getDiameter())
                        , () -> repository.save(mapper.mapToAcceptableThickness(thicknessDto))));
    }

    @Override
    public ResponseAcceptableThicknessDto update(AcceptableThicknessDto thicknessDto) {
        if (repository.existsById(thicknessDto.getId())) {
            return mapper.mapToResponseAcceptableThicknessDto(
                    repository.save(mapper.mapToAcceptableThickness(thicknessDto))
            );
        }
        throw new NotFoundException(
                String.format("AcceptableThickness with id=%s not found for update", thicknessDto.getId())
        );
    }

    @Override
    public List<ResponseAcceptableThicknessDto> getAll(Long equipmentTypeId) {
        return repository.findAllByEquipmentTypeId(equipmentTypeId)
                .stream()
                .map(mapper::mapToResponseAcceptableThicknessDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("AcceptableThickness with id=%s not found for delete", id));
    }

    @Override
    public AcceptableThickness getByPredicate(Long equipmentTypeId
                                            , Long elementId
                                            , Long partElementId
                                            , Integer diameter) {
        QAcceptableThickness thickness = QAcceptableThickness.acceptableThickness;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(thickness.equipmentTypeId.eq(equipmentTypeId));
        booleanBuilder.and(thickness.elementId.eq(elementId));
        if (partElementId != null) {
            booleanBuilder.and(thickness.partElementId.eq(partElementId));
        }
        if (diameter != null) {
            booleanBuilder.and(thickness.diameter.eq(diameter));
        }
        return new JPAQueryFactory(em).from(thickness)
                .select(thickness)
                .where(booleanBuilder)
                .fetchOne();
    }
}