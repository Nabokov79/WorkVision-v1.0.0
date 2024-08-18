package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedPartElementRepository;

@Service
@RequiredArgsConstructor
public class CalculatedPartElementServiceImpl implements CalculatedPartElementService {

    private final CalculatedPartElementRepository repository;
}