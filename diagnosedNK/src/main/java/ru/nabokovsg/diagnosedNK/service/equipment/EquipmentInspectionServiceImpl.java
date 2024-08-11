package ru.nabokovsg.diagnosedNK.service.equipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.ResponseEquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipment.EquipmentInspectionMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentInspection;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentInspection;
import ru.nabokovsg.diagnosedNK.repository.equipment.EquipmentInspectionRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EquipmentInspectionServiceImpl implements EquipmentInspectionService {

    private final EquipmentInspectionRepository repository;
    private final EquipmentInspectionMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseEquipmentInspectionDto save(EquipmentInspectionDto inspectionDto) {
        return mapper.mapToResponseEquipmentInspectionDto(
                Objects.requireNonNullElseGet(getByPredicate(inspectionDto)
                        , () -> repository.save(mapper.mapToEquipmentInspection(inspectionDto))));
    }

    @Override
    public ResponseEquipmentInspectionDto update(EquipmentInspectionDto inspectionDto) {
        if (repository.existsById(inspectionDto.getId())) {
            return mapper.mapToResponseEquipmentInspectionDto(
                    repository.save(mapper.mapToEquipmentInspection(inspectionDto)));
        }
        throw new NotFoundException(
                String.format("Equipment inspection with id=%s not found for delete", inspectionDto.getId()));
    }

    @Override
    public List<ResponseEquipmentInspectionDto> getAll(Long id) {
        return repository.findAllByEquipmentDiagnosedId(id)
                         .stream()
                         .map(mapper::mapToResponseEquipmentInspectionDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment inspection with id=%s not found for delete", id));
    }

    private EquipmentInspection getByPredicate(EquipmentInspectionDto inspectionDto) {
        QEquipmentInspection inspection = QEquipmentInspection.equipmentInspection;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(inspection.date.eq(inspectionDto.getDate()));
        booleanBuilder.and(inspection.inspection.eq(inspectionDto.getInspection()));
        booleanBuilder.and(inspection.documentNumber.eq(inspectionDto.getDocumentNumber()));
        booleanBuilder.and(inspection.documentNumber.eq(inspectionDto.getDocumentNumber()));
        booleanBuilder.and(inspection.organization.eq(inspectionDto.getOrganization()));
        booleanBuilder.and(inspection.equipmentDiagnosedId.eq(inspectionDto.getEquipmentDiagnosedId()));
        return new JPAQueryFactory(em).from(inspection)
                                      .select(inspection)
                                      .where(booleanBuilder)
                                      .fetchOne();
    }
}