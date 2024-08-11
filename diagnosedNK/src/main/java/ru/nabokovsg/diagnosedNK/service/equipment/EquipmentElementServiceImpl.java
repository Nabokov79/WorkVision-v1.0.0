package ru.nabokovsg.diagnosedNK.service.equipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipment.EquipmentElementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.QStandardSize;
import ru.nabokovsg.diagnosedNK.repository.equipment.EquipmentElementRepository;
import ru.nabokovsg.diagnosedNK.service.equipmentType.EquipmentTypeElementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentElementServiceImpl implements EquipmentElementService {

    private final EquipmentElementRepository repository;
    private final EquipmentElementMapper mapper;
    private final EquipmentDiagnosedService equipmentService;
    private final EquipmentTypeElementService elementService;
    private final StandardSizeService standardSizeService;
    private final EquipmentPartElementService partElementService;
    private final EntityManager em;

    @Override
    public ResponseEquipmentElementDto save(EquipmentElementDto elementDto) {
        EquipmentElement element = getByPredicate(elementDto);
        if (element == null) {
            element = mapper.mapToElement(elementService.getById(elementDto.getElementId())
                    , equipmentService.getById(elementDto.getEquipmentDiagnosedId()));
            if (elementDto.getPartElementId() == null) {
                element = repository.save(mapper.mapWithStandardSize(element
                        , standardSizeService.save(elementDto.getStandardSize())));
            }
        }
        element = repository.save(element);
        if (elementDto.getPartElementId() != null) {
            partElementService.save(element, elementDto);
        }
        return mapper.mapToResponseEquipmentElementDto(element);
    }

    @Override
    public ResponseEquipmentElementDto update(EquipmentElementDto elementDto) {
        if (repository.existsById(elementDto.getId())) {
            EquipmentElement element = mapper.mapToElement(
                    elementService.getById(elementDto.getElementId())
                    , equipmentService.getById(elementDto.getEquipmentDiagnosedId()));
            if (elementDto.getPartElementId() == null) {
                return mapper.mapToResponseEquipmentElementDto(
                        mapper.mapWithStandardSize(element
                                , standardSizeService.update(elementDto.getStandardSize())));
            }
            element.getPartsElement().add(partElementService.update(element.getPartsElement(), elementDto));
            return mapper.mapToResponseEquipmentElementDto(element);
        }
        throw new NotFoundException(String.format("Element with id=%s not found for update", elementDto.getId()));
    }

    @Override
    public EquipmentElement get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Equipment element with id=%s not found", id)));
    }

    @Override
    public List<ResponseEquipmentElementDto> getAll(Long id) {
        return repository.findByEquipmentDiagnosedId(id)
                .stream()
                .map(mapper::mapToResponseEquipmentElementDto)
                .toList();
    }

    private EquipmentElement getByPredicate(EquipmentElementDto elementDto) {
        QEquipmentDiagnosed equipment = QEquipmentDiagnosed.equipmentDiagnosed;
        QEquipmentElement element = QEquipmentElement.equipmentElement;
        QStandardSize standardSize = QStandardSize.standardSize;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(equipment.id.eq(elementDto.getEquipmentDiagnosedId()));
        builder.and(element.elementId.eq(elementDto.getElementId()));
        if (elementDto.getPartElementId() == null) {
            if (elementDto.getStandardSize().getDesignThickness() != null) {
                builder.and(standardSize.designThickness.eq(elementDto.getStandardSize().getDesignThickness()));
            }
            if (elementDto.getStandardSize().getMinDiameter() != null) {
                builder.and(standardSize.minDiameter.eq(elementDto.getStandardSize().getMinDiameter()));
            }
            if (elementDto.getStandardSize().getMaxDiameter() != null) {
                builder.and(standardSize.maxDiameter.eq(elementDto.getStandardSize().getMaxDiameter()));
            }
            return new JPAQueryFactory(em).from(element)
                    .select(element)
                    .where(builder)
                    .innerJoin(element.standardSize, standardSize)
                    .fetchOne();
        }
        return new JPAQueryFactory(em).from(element)
                .select(element)
                .where(builder)
                .fetchOne();
    }
}