package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.ResponseUltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.UltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicControl.UltrasonicControlMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicControl.UltrasonicControl;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicControl.UltrasonicControlRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UltrasonicControlServiceImpl implements UltrasonicControlService {

    private final UltrasonicControlRepository repository;
    private final UltrasonicControlMapper mapper;

    @Override
    public ResponseUltrasonicMeasurementDto save(UltrasonicMeasurementDto measurementDto) {
        UltrasonicControl measurement = repository.findByWorkJournalIdAndWeldedJointNumber(
                                                                              measurementDto.getWorkJournalId()
                                                                            , measurementDto.getWeldedJointNumber());
        if (measurement != null) {
            measurement.setMeasurement(
                    String.join("; ", measurement.getMeasurement(), measurementDto.getMeasurement()));
            measurement = repository.save(measurement);
        } else {
            measurement = repository.save(mapper.mapToUltrasonicControl(measurementDto));
        }
        return mapper.mapToResponseUltrasonicMeasurement(measurement);
    }

    @Override
    public ResponseUltrasonicMeasurementDto update(UltrasonicMeasurementDto measurementDto) {
        if (repository.existsById(measurementDto.getId())) {
            return mapper.mapToResponseUltrasonicMeasurement(mapper.mapToUltrasonicControl(measurementDto));
        }
        throw new NotFoundException(
                String.format("UltrasonicMeasurement with id=%s not found for update", measurementDto.getId()));
    }

    @Override
    public List<ResponseUltrasonicMeasurementDto> getAll(Long workJournalId) {
        return repository.findAllByWorkJournalId(workJournalId)
                         .stream()
                         .map(mapper::mapToResponseUltrasonicMeasurement)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("UltrasonicMeasurement with id=%s not found for delete", id));
    }
}