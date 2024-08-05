package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.EquipmentElementMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentPartElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QStandardSize;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.EquipmentElementRepository;
import ru.nabokovsg.diagnosedEquipment.service.equipmentType.EquipmentTypeElementService;

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
                return mapper.mapToResponseEquipmentElementDto(mapper.mapWithStandardSize(element
                                                             , standardSizeService.save(elementDto.getStandardSize())));
            }
            element.getPartsElement().add( partElementService.save(repository.save(element), elementDto));
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
                                                 , standardSizeService.save(elementDto.getStandardSize())));
            }
            element.getPartsElement().add( partElementService.save(repository.save(element), elementDto));
            return mapper.mapToResponseEquipmentElementDto(element);
        }
        throw new NotFoundException(String.format("Element with id=%s not found for update", elementDto.getId()));
    }

    @Override
    public List<ResponseEquipmentElementDto> getAll(Long id) {
        return repository.findByEquipmentDiagnosedId(id)
                         .stream()
                         .map(mapper::mapToResponseEquipmentElementDto)
                         .toList();
    }

    private EquipmentElement getByPredicate(EquipmentElementDto elementDto) {
        QEquipmentElement element = QEquipmentElement.equipmentElement;
        QEquipmentPartElement partElement = QEquipmentPartElement.equipmentPartElement;
        QStandardSize standardSize = QStandardSize.standardSize;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(element.elementId.eq(elementDto.getElementId()));
        if (elementDto.getPartElementId() != null) {
            builder.and(partElement.partElementId.eq(elementDto.getPartElementId()));
        }
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
                                      .fetchOne();
    }
}