package ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.EquipmentDto;
import ru.nabokovsg.diagnosedNK.mapper.diagnosticEquipmentData.DiagnosticEquipmentDataMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.repository.diagnosticEquipmentData.DiagnosticEquipmentDataRepository;

@Service
@RequiredArgsConstructor
public class DiagnosticEquipmentDataServiceImpl implements DiagnosticEquipmentDataService {

    private final DiagnosticEquipmentDataRepository repository;
    private final DiagnosticEquipmentDataMapper mapper;
    private final EquipmentStandardSizeService elementService;

    @Override
    public void save(EquipmentDto equipmentDto) {
        DiagnosticEquipmentData object = get(equipmentDto.getId());
        if (object != null) {
            update(object, equipmentDto);
        }
        elementService.save(repository.save(mapper.mapToDiagnosticObjectData(equipmentDto)), equipmentDto.getElements());
    }

    private void update(DiagnosticEquipmentData object, EquipmentDto equipmentDto) {
        elementService.update(mapper.mapToUpdateDiagnosticObjectData(object, equipmentDto), equipmentDto.getElements());
    }

    @Override
    public DiagnosticEquipmentData get(Long equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }
}